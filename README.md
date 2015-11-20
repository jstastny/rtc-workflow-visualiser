# Rational Team Concert Workflow Visualiser

## Overview
Visualiser is a tool that takes the RTC process configuration XML and exports graphical representation of the workflows included in the configuration.

## Motivation
The most typical task I'm facing at new RTC deployments is analysis of their workflow and its implementation in RTC. The usual procedure is:

1. Meet with the customer to try to understand the workflow (states, transitions)
2. Draw the workflow in Microsoft Visio and discuss the Visio files with customer
3. Implement the workflow in RTC

This would be all great it there wasn't number 4 – implement the changes the customer finds after the implementation in RTC, which usually repeats several times. In this moment the Visio diagrams and the RTC implementation get out of sync and it is not easy to keep them synchronized. Workflow Visualiser helps in this step as I can export the real diagram and either send it to the customer for review or redraw it in Visio.

Another case in which this tool comes handy are big workflows (with 10< states). Although the transition table in RTC workflow designer is straightforward, checking such a workflow is not an easy task. Seeing a graphical representation of the workflow makes it clear.


## Running the program
The program has to be invoked from command line and the command line arguments are:
```
Usage: java -jar visualiser.jar [options] source-xml-file
 Options:
  -h|--help Print help and exit
  --version Print help and exit
  -d|--dot Location of dot executable (defaults to /usr/bin/dot)
  -o|--out Output directory (defaults to /tmp/)
  -f|--format Output format (see the output formats of dot) (defaults to pdf)
  -s|--source Save the DOT source file in the output directory
```

## Examples

### Windows example PDF

- JAR stored in `C:\visualiser.jar`
- Process XML configuration file saved in `process.xml`
- Graphviz installed in standard location
- Export diagrams in PDF format to `C:\temp`
```
java -jar "C:\visualiser.jar" -o "C:\temp" -d "C:\Program Files\Graphviz2.26.3\bin\dot.exe" process.xml
```

### Windows example PNG

- JAR stored in `C:\visualiser.jar`
- Process XML configuration file saved in `process.xml`
- Graphviz installed in standard location
- Export diagrams in PNG format to `C:\temp`
```
java -jar "C:\visualiser.jar" -o "C:\temp" -d "C:\Program Files\Graphviz2.26.3\bin\dot.exe" -f png process.xml
```

### Linux example

- JAR stored in `~/visualiser.jar`
- Process XML configuration file saved in `process.xml`
- Graphviz installed in standard location (`/usr/bin/dot`)
- Export diagrams in PDF format to `~/diagrams`
```
java -jar ~/visualiser.jar -o ~/diagrams process.xml
```

## Download

### Prerequisites

The program uses Graphviz for diagram drawing. You must have Graphviz installed on your computer to be able to run the program. It can be freely downloaded from http://www.graphviz.org/Download.php

### Binary
Binaries are available with the releases at https://github.com/jstastny/rtc-workflow-visualiser/releases

## License

    Copyright 2010 Jan Stastny

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

## Build status
[![Build Status](https://travis-ci.org/jstastny/rtc-workflow-visualiser.svg?branch=master)](https://travis-ci.org/jstastny/rtc-workflow-visualiser)
