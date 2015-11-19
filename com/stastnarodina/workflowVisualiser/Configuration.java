package com.stastnarodina.workflowVisualiser;


public class Configuration {
	private static String outputFormat = "pdf";
	private static String outputDir = "/tmp/";
	private static String dotLocation = "/usr/bin/dot";
	//private static String tempDir = "System.getProperty(\"java.io.tmpdir\"))"
	private static boolean saveDotSource = false;
	private static boolean showResolution = true;

	
	
	/**
	 * All methods are static
	 */
	private Configuration() {}
	
	
	/**
	 * @return the outputFormat
	 */
	public static String getOutputFormat() {
		return outputFormat;
	}
	/**
	 * Sets the output format.<br />
	 * <b>NOTE:</b> Does not check whether the output format is supported!
	 * @param outputFormat the outputFormat to set
	 */
	public static void setOutputFormat(String outputFormat) {
		Configuration.outputFormat = outputFormat;
	}
	/**
	 * @return the outputDir
	 */
	public static String getOutputDir() {
		return outputDir;
	}
	/**
	 * @param outputDir the outputDir to set
	 */
	public static void setOutputDir(String outputDir) {
		Configuration.outputDir = outputDir;
	}


	/**
	 * @return the dotLocation
	 */
	public static String getDotLocation() {
		return dotLocation;
	}


	/**
	 * @param dotLocation the dotLocation to set
	 */
	public static void setDotLocation(String dotLocation) {
		Configuration.dotLocation = dotLocation;
	}


	public static boolean isSaveDotSource() {
		return saveDotSource;
	}


	public static void setSaveDotSource(boolean saveDotSource) {
		Configuration.saveDotSource = saveDotSource;
	}
	
	/**
	 * @return True to show resolution values
	 */
	public static boolean showResolution() {
		return showResolution;
	}

	/**
	 * @param dotLocation Set if we should show resolution values
	 */
	public static void setShowResolution(boolean showResolution) {
		Configuration.showResolution = showResolution;
	}
	
}
