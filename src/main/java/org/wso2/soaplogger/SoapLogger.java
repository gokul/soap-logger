package org.wso2.soaplogger;


import org.apache.axiom.soap.SOAPBody;
import org.apache.axiom.soap.SOAPHeader;
import org.apache.axis2.AxisFault;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.description.AxisService;
import org.apache.axis2.description.WSDL2Constants;
import org.apache.axis2.handlers.AbstractHandler;

import org.apache.axiom.soap.SOAPEnvelope;

import org.wso2.carbon.core.util.SystemFilter;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.*;


public class SoapLogger extends AbstractHandler{
    private static Logger logger = Logger.getLogger(SoapLogger.class);

    private static final String FILE_PATH = "repository"+File.separator+"conf"+File.separator+"log4j.properties";

    public InvocationResponse invoke(MessageContext messageContext) throws AxisFault {
        AxisService service = messageContext.getAxisService();
        PropertyConfigurator.configure(FILE_PATH);

        if (service != null) {
            if(service.isClientSide() || SystemFilter.isAdminService(service) ||SystemFilter.isFilteredOutService(service.getAxisServiceGroup())){
                return InvocationResponse.CONTINUE;
            }
        }
        SOAPEnvelope soapEnvelope = messageContext.getEnvelope();
        SOAPEnvelope inSoapEnvelope = messageContext.getOperationContext().getMessageContext(WSDL2Constants.MESSAGE_LABEL_IN).getEnvelope();

        String uid = getUniqueId();

        logger.info("[Request id: "+uid+"] - "+ inSoapEnvelope.toString().replace("\n",""));
        logger.info("[Response id: "+uid+"] - "+ soapEnvelope.toString().replace("\n",""));


        SOAPBody soapBody = soapEnvelope.getBody();

        SOAPHeader soapHeader = soapEnvelope.getHeader();

        return InvocationResponse.CONTINUE;

    }

    private String getUniqueId() {
        return String.valueOf(System.nanoTime() + Thread.currentThread().getId());
    }

}
