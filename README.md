soap-logger
===========

Simple Axis2 module for logging SOAP requests and responses

###Usage (WSO2 Carbon-specific)

- Build the project as follows:
	```
  $ mvn clean install
  ```

- Add the resultant jar archive to the ```<CARBON_HOME>/repository/components/lib``` directory

- Add a line to axis2.xml to invoke the module at some phase, in any phase order (i.e. inFlow/outFaultFlow etc.)
	e.g.
  ```
	<phase name="MsgOutObservation"> 
		<handler name="SoapLogger" class="org.wso2.soaplogger.SoapLogger"/> 
	</phase>
	```

- Add the following to the log4j.properties file:
  ```
	log4j.logger.org.wso2.soaplogger=,SOAP_LOGGER 
	log4j.appender.SOAP_LOGGER=org.apache.log4j.DailyRollingFileAppender 
	log4j.appender.SOAP_LOGGER.File=${carbon.home}/repository/logs/soap-log.log 
	log4j.appender.SOAP_LOGGER.Append=true 
	log4j.appender.SOAP_LOGGER.layout=org.wso2.carbon.utils.logging.TenantAwarePatternLayout 
	log4j.appender.SOAP_LOGGER.layout.ConversionPattern=TID: [%T] [%S] [%d] %P%5p {%c} - %x %m %n 
	log4j.appender.SOAP_LOGGER.layout.TenantPattern=%U%@%D [%T] [%S] 
  ```

- Optionally, add the following to log4j.properties if you would like the response logs not to be printed on the console and be written to the log file only:
  ```	
	log4j.additivity.org.wso2.soaplogger=false 
  ```
