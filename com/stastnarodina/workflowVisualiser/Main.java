package com.stastnarodina.workflowVisualiser;

import jargs.gnu.CmdLineParser;
import jargs.gnu.CmdLineParser.Option;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.Normalizer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;





public class Main {

	public static String NL = System.getProperty("line.separator");
	
	public static final String VERSION = "2010-04-09.2";
	
	private static String filename;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		parseConfiguration(args);
		
		File outputDir = new File(Configuration.getOutputDir());
		
		
		try {
			InputStream is = new BufferedInputStream(new FileInputStream(filename));
			Parser parser = new Parser();
			for(Workflow workflow : parser.parse(is)) {
				String dotSource = getDotSource(workflow);
				
				if(Configuration.isSaveDotSource()) {
					saveSource(dotSource, workflow);
				}
				
				File outputFile = new File(outputDir, workflow.getId() + "." + Configuration.getOutputFormat());
				
				String[] command = {
						Configuration.getDotLocation(), 
						"-T" + Configuration.getOutputFormat(),
						"-o" + outputFile.getAbsolutePath()
					};
				
				
				Process process = Runtime.getRuntime().exec(command);
				
				process.getOutputStream().write(dotSource.getBytes());
				process.getOutputStream().close();
				InputStream stderr = new BufferedInputStream(process.getErrorStream());
				InputStream stdout = new BufferedInputStream(process.getInputStream());
				
				int exitCode = process.waitFor();
				
				if(exitCode == 0) {
					System.out.println("Successfully created " + outputFile.getAbsolutePath());
				}
				
				String line = null;

				BufferedReader outReader = new BufferedReader(new InputStreamReader(stdout));
				line = null;
				while((line = outReader.readLine()) != null) {
					System.out.println(line);
				}
				stdout.close();
				
				BufferedReader errReader = new BufferedReader(new InputStreamReader(stderr));
				line = null;
				while((line = errReader.readLine()) != null) {
					System.err.println(line);
				}
				stderr.close();
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
	
	
	private static void parseConfiguration(String[] args) {
		
		CmdLineParser parser = new CmdLineParser();
		Option help = parser.addBooleanOption('h', "help");
		Option version = parser.addBooleanOption("version");
		Option dot = parser.addStringOption('d', "dot");
		Option out = parser.addStringOption('o', "out");
		Option format = parser.addStringOption('f', "format");
		Option source = parser.addBooleanOption('s', "source");
		Option hideResolution = parser.addBooleanOption("hide-resolutions");
		
		 try {
            parser.parse(args);
            Boolean helpVal = (Boolean) parser.getOptionValue(help, Boolean.FALSE);
            if(helpVal) {
            	System.out.println(getUsage());
            	System.exit(3);
            }
            
            Boolean versionVal = (Boolean) parser.getOptionValue(version, Boolean.FALSE);
            if(versionVal) {
            	System.out.println("Workflow Visualiser, Version " + VERSION);
            	System.exit(4);
            }
            
            
            String dotVal = (String) parser.getOptionValue(dot);
            if(dotVal != null) {
            	Configuration.setDotLocation(dotVal);
            }
            
            String outVal = (String) parser.getOptionValue(out);
            if(outVal != null) {
            	Configuration.setOutputDir(outVal);
            }
            
            String formatVal = (String) parser.getOptionValue(format);
            if(formatVal != null) {
            	Configuration.setOutputFormat(formatVal);
            }
            
            Boolean sourceVal = (Boolean) parser.getOptionValue(source, Boolean.FALSE);
            if(sourceVal) {
            	Configuration.setSaveDotSource(true);
            }
            
            Boolean hideResolVal = (Boolean) parser.getOptionValue(hideResolution, Boolean.FALSE);
            if(hideResolVal != null) {
                Configuration.setShowResolution(!hideResolVal);
            }
            
            String[] remaining = parser.getRemainingArgs();
            if(remaining.length == 0) {
            	throw new IllegalArgumentException("The input file has to be set");
            }
            
            filename = remaining[0];
            
		 }
	        catch ( Exception e ) {
	            System.err.println(e.getMessage());
	            System.out.println(getUsage());
	            System.exit(2);
	        }
		
	}
	
	private static String getUsage() {
		return "Usage: Main [options] source-xml-file\n" +
				" Options: \n" +
				"  -h|--help Print help and exit\n" +
				"  --version Print program version and exit\n" +
				"  -d|--dot Location of dot executable\n" +
				"  -o|--out Output directory\n" +
				"  -f|--format Output format (see the output formats of dot)\n" +
				"  -s|--source Save the DOT source file in the output directory\n" +
				"  --hide-resolutions Set it to avoid displaying resolutions";
	}
	
	private static void saveSource(String source, Workflow workflow) {
		
		OutputStreamWriter writer = null;
		try {
			File outputFile = new File(new File(Configuration.getOutputDir()), workflow.getId() +  ".dot");
			writer = new OutputStreamWriter(new BufferedOutputStream(new FileOutputStream(outputFile)));
			writer.write(source);
			System.out.println("Source successfully saved to " + outputFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	
	private static String utf2ascii(String input) {
		String temp = Normalizer.normalize(input, Normalizer.Form.NFD);
		return temp.replaceAll("[^\\p{ASCII}]", "");
	}
	

	/**
	 * Get the dot source
	 * @return Dot source
	 */
	public static String getDotSource(Workflow workflow) {
		StringBuilder out = new StringBuilder();
		out.append("digraph \"" + workflow.getId() + "\" {").append(NL);
		out.append("label=\"" + utf2ascii(workflow.getName()) + "\" ");
		out.append(NL);
		out.append("fontsize=20");
		out.append(NL);
		out.append("labelloc=t");
		out.append(NL);
		
		
		// So that there are no duplicate edges for resolutions
		Map<Resolution, HashSet<State>> drawnResolutions = new HashMap<Resolution, HashSet<State>>();
		
		
		// All the states
		for(State state: workflow.getStates()) {
			out.append("\"" + state.getId() + "\" ");
			out.append("[");
			switch(state.getGroup()) {
			case OPEN:
				out.append("color=green,");
				break;
			case IN_PROGRESS:
				out.append("color=blue,");
				break;
			case CLOSED:
				out.append("color=red,");
				break;
			}
			
			if(workflow.getStartAction().getTargetState().equals(state)) {
				out.append("shape=doublecircle,");
			}
			// Not solving the reopen and resolve states here
			
			out.append("label=\""+ utf2ascii(state.getName()) +"\"");
			
			out.append("]");
			out.append(NL);

			
			
			// Iterate over the actions
			for(Action action: state.getActions()) {
				out.append("\"" + state.getId() + "\" -> " + "\"" + action.getTargetState().getId() + "\" ");
				out.append("[");
				out.append("label=\"" + utf2ascii(action.getName()) + "\", fontsize=11");
				
				out.append("]");
				out.append(NL);
				
				if((action.getTargetState().isShowResolution() && (Configuration.showResolution()))) {
					for(Resolution resolution: action.getResolutions()) {
						
						if(!drawnResolutions.containsKey(resolution)) {
						    drawnResolutions.put(resolution, new HashSet<State>());
							out.append("\"" +resolution.getId() + "\" [label=\"" + utf2ascii(resolution.getName()) + "\", shape=box, fontsize=10]");
							out.append(NL);
						}
						
						if (!drawnResolutions.get(resolution).contains(action.getTargetState())){
						    drawnResolutions.get(resolution).add(action.getTargetState());
						    out.append("\"" + action.getTargetState().getId() + "\" -> " + "\"" + resolution.getId() + "\" ");
	                        out.append("[");
	                        out.append("style=dotted");
	                        out.append("]");
	                        out.append(NL);
						}
						
					}
						
				}
				
			}
			
		}
		
//		// All closed on the bottom of the graph
//		out.append("{rank=max; ");
//		for(State state: workflow.getStatesByGroup(Group.CLOSED)) {
//			out.append("\"" + state.getId() + "\"; ");
//		}
//		out.append("}");
//		out.append(NL);
		
		// All open on the top of the graph
//		out.append("{rank=min; ");
//		for(State state: workflow.getStatesByGroup(Group.OPEN)) {
//			out.append("\"" + state.getId() + "\"; ");
//		}
//		out.append("}");
//		out.append(NL);
//		
		
		// initial state on the top
		
		if(workflow.getStartAction() != null) {
			out.append("{rank=min; \"" + workflow.getStartAction().getTargetState().getId() + "\" }");
		}

		
		
		out.append("}").append(NL);
		
		
		return out.toString();
		
		
		//throw new UnsupportedOperationException();
	}
	
	

}
