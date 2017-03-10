/*******************************************************************************************************
*   Copyright (c) 2014 CA. All rights reserved.
*
*   This software and all information contained therein is confidential and proprietary and shall
*   not be duplicated, used, disclosed or disseminated in any way except as authorized by the
*   applicable license agreement, without the express written permission of CA. All authorized
*   reproductions must be marked with this language.
*
*   EXCEPT AS SET FORTH IN THE APPLICABLE LICENSE AGREEMENT, TO THE EXTENT PERMITTED BY APPLICABLE
*   LAW, CA PROVIDES THIS SOFTWARE WITHOUT WARRANTY OF ANY KIND, INCLUDING WITHOUT LIMITATION, ANY
*   IMPLIED WARRANTIES OF MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE. IN NO EVENT WILL CA
*   BE LIABLE TO THE END USER OR ANY THIRD PARTY FOR ANY LOSS OR DAMAGE, DIRECT OR INDIRECT, FROM
*   THE USE OF THIS SOFTWARE, INCLUDING WITHOUT LIMITATION, LOST PROFITS, BUSINESS INTERRUPTION,
*   GOODWILL, OR LOST DATA, EVEN IF CA IS EXPRESSLY ADVISED OF SUCH LOSS OR DAMAGE.
*
********************************************************************************************************/

package com.mycompany.carablazemeter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.Map;
import java.util.HashMap;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.lang.IllegalArgumentException;

import com.nolio.platform.shared.api.ActionDescriptor;
import com.nolio.platform.shared.api.ActionResult;
import com.nolio.platform.shared.api.ParameterDescriptor;
import com.nolio.platform.shared.api.Password;
import com.nolio.platform.shared.datamodel.Action;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import com.jayway.jsonpath.JsonPath;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import com.ca.nolio.rdk.dto.Operations;
import com.ca.nolio.rdk.dto.VarType;
import com.ca.nolio.rdk.dto.FilterType;
import com.ca.nolio.rdk.dto.InputParam;
import com.ca.nolio.rdk.dto.OutputParam;
import com.ca.nolio.rdk.dto.Authentication;
import com.ca.nolio.rdk.dto.HttpMethod;
import com.ca.nolio.rdk.dto.QOP;
import com.ca.nolio.rdk.dto.CredentialsInfo;
import com.ca.nolio.rdk.dto.exception.ActionException;
import com.ca.nolio.rdk.model.helper.*;
import com.ca.nolio.rdk.model.RestClientManager;
import com.ca.nolio.rdk.dto.HttpRDKResponse;
import com.ca.nolio.rdk.dto.RDKHeader;

import com.ca.nolio.rdk.template.helper.ErrorHelper;
import com.ca.nolio.rdk.template.helper.ReplacementsHelper;

import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import javax.xml.xpath.XPathConstants;

import net.minidev.json.*;


/**
 *
 * @author Joe Offenberg
 */
@ActionDescriptor(
        name = "BlazeMeter - Rename Test",
        description = "List all BlazeMeter harbors (Private Locations) for an account and workspace.",
        category="BlazeMeter." )
    public class BlazeMeterRenameTest extends Action {
    private static final long serialVersionUID = 1L;
    private static final Logger log = Logger.getLogger(BlazeMeterRenameTest.class);
    private static final String SCAPE_CHAR = "\\";
    private static final String CHAR_SET = "UTF-8";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String ENCODING = "Accept-Encoding";
    private static String WEBSERVICE_OUTPUT = "rdkWebserviceOutput.txt";
    private List<InputParam> inputParameters = null;    
    private List<OutputParam> outputParameters = null;  


            
    @ParameterDescriptor(
        name = "API Key",
        description = "API Key Description",
        out = false,
        in = true,
        nullable = true, // parameter not required
        order = 10,
        defaultValueAsString = ""
        )


            private Password apikey1;
        @ParameterDescriptor(
        name = "test id",
        description = "test id Description",
        out = false,
        in = true,
        nullable = true, // parameter not required
        order = 20,
        defaultValueAsString = ""
        )


            private String testid2;
        @ParameterDescriptor(
        name = "New Name",
        description = "New Name Description",
        out = false,
        in = true,
        nullable = true, // parameter not required
        order = 30,
        defaultValueAsString = ""
        )


            private String newname3;
    
    @ParameterDescriptor(
        name = "Authentication", 
        description = "Authenthication type.", 
        out = false, 
        in = true, 
        nullable = true,
        defaultValueAsString = "Authentication.NONE",
        order = 40
    )
    private Authentication authType = Authentication.NONE;


    @ParameterDescriptor(
            name = "Execution Output",
                    description = "This output parameter holds the standard output of the command execution.",
                out = true, 
            in = false     
        )
        
    private String executionoutput1;

    @ParameterDescriptor(
            name = "Error Output",
                    description = "This output parameter holds the standard error output of the command execution.",
                out = true, 
            in = false     
        )
        
    private String erroroutput2;

    @ParameterDescriptor(
            name = "Exit Code",
                    description = "This output parameter holds the returned exit code of the command execution.",
                out = true, 
            in = false     
        )
        
    private Integer exitcode3;


    // Getters and Setters
            public Password getapikey1() {
        return apikey1;
    } 
    
    public void setapikey1(Password apikey1) {
        this.apikey1 = apikey1;
    }
                public String gettestid2() {
        return testid2;
    } 
    
    public void settestid2(String testid2) {
        this.testid2 = testid2;
    }
                public String getnewname3() {
        return newname3;
    } 
    
    public void setnewname3(String newname3) {
        this.newname3 = newname3;
    }
        // Getters and Setters
        public String getexecutionoutput1() {
        return executionoutput1;
    }
    
    public void setexecutionoutput1(String executionoutput1) {
        this.executionoutput1 = executionoutput1;
    }
            public String geterroroutput2() {
        return erroroutput2;
    }
    
    public void seterroroutput2(String erroroutput2) {
        this.erroroutput2 = erroroutput2;
    }
            public Integer getexitcode3() {
        return exitcode3;
    }
    
    public void setexitcode3(Integer exitcode3) {
        this.exitcode3 = exitcode3;
    }
    
    @Override
    public ActionResult execute() {
        HttpRDKResponse response = new HttpRDKResponse("NO RESPONSE", null, -1);
        try {
            log.debug("A call was received to execute a Restful Action: " + "BlazeMeter - Rename Test");

            String rawRequestValue = "{\"name\": \"${New Name}\"}";

            response = new RestClientManager().restCall(HttpMethod.POST, authType, getInput(),
                "https://a.blazemeter.com/api/v2/tests/${test id}", 60, "", "application/json",
                rawRequestValue, getRequestParameters(), getHeaders());

            log.debug("REST call response: " + response);

                                } catch (Exception e) {
            response = new HttpRDKResponse("There was an error trying to execute a REST Call Action: '"
                + "BlazeMeter - Rename Test" + "' for Action Pack: "   + "", null, -1);
            log.error(response.getContent(), e);
            return new ActionResult(false, response.getContent() + e.getMessage());
        }

        populateOutputParameters(response);
        return getActionResult();
    }   

    private void populateOutputParameters(HttpRDKResponse output) {
        if (StringUtils.isBlank(output.getContent())) {
            log.debug("Output is empty");
        }
        
                   
                    try {
                Pattern pattern = Pattern.compile("[\\s\\S]*");
                System.out.println("Execution Output");
                                    Matcher matcher = pattern.matcher(output.getContent());
                                String stringMatch = null;
                if (matcher.find()) {
                    stringMatch = matcher.group();
                }
        
              executionoutput1 = String.valueOf(stringMatch);
            } catch (PatternSyntaxException e) {
                log.error("Caught exception during populating output parameter: " + "Execution Output" + ". Bad pattern", e);
            } catch (Exception e) {
                log.error("Caught exception during populating output parameter: " + "Execution Output", e);
            }
                               
                    try {
                Pattern pattern = Pattern.compile("[\\s\\S]*");
                System.out.println("Error Output");
                                    Matcher matcher = pattern.matcher(output.getContent());
                                String stringMatch = null;
                if (matcher.find()) {
                    stringMatch = matcher.group();
                }
        
              erroroutput2 = String.valueOf(stringMatch);
            } catch (PatternSyntaxException e) {
                log.error("Caught exception during populating output parameter: " + "Error Output" + ". Bad pattern", e);
            } catch (Exception e) {
                log.error("Caught exception during populating output parameter: " + "Error Output", e);
            }
                               
                    try {
                Pattern pattern = Pattern.compile("");
                System.out.println("Exit Code");
                                    Matcher matcher = pattern.matcher(output.getContent());
                                String stringMatch = null;
                if (matcher.find()) {
                    stringMatch = matcher.group();
                }
        
              exitcode3 = Integer.valueOf(stringMatch);
            } catch (PatternSyntaxException e) {
                log.error("Caught exception during populating output parameter: " + "Exit Code" + ". Bad pattern", e);
            } catch (Exception e) {
                log.error("Caught exception during populating output parameter: " + "Exit Code", e);
            }
                }
    
    private ActionResult getActionResult() {
            ActionResult actionResult = null;
        try { 
            actionResult = new ActionResult(true, ReplacementsHelper.replaceOutputParameters( ReplacementsHelper.replaceInputParameters( "Execution succeeded", getInput() ), getOutput() ) );
        } catch (Exception e) {
            log.error("There was an error on the replacement of parameters", e);
        }
        

        return actionResult;
    }

    private void createOutputLocationFile(String outputLocation, String content) throws ActionException {
        File outputDirs = new File(outputLocation);
        if (!outputDirs.exists()) {
            try {
                outputDirs.mkdirs();
            } catch (SecurityException se) {
                log.error("Caught security exception while creating output location: " + outputLocation);
                throw new ActionException("Security exception while creating output location: " + se.getMessage(), se);
            }
        }
        
        String fileName = outputLocation + File.separator + WEBSERVICE_OUTPUT;
        try {
            FileWriter fw = new FileWriter(fileName);
            fw.write(content);
            fw.flush();
            fw.close();
        } catch (IOException e) {
            log.error("Caught IO exception during writing to file");
            throw new ActionException("IO exception during writing to file: " + e.getMessage(), e);
        }
    }   
    
    private List<InputParam> getInput() {
                    inputParameters = new ArrayList<InputParam>();
            InputParam p = null;
                    p = new InputParam();
            p.setName("API Key");
                                    p.setVariableName("apikey");
            p.setPrefix("");
            p.setType(VarType.Password);
                                                                                     if(apikey1 != null) {
                    p.setValue(apikey1.toString());
                }
                                             
            inputParameters.add(p);     
                    p = new InputParam();
            p.setName("test id");
                                    p.setVariableName("testid");
            p.setPrefix("");
            p.setType(VarType.String);
                                                                     p.setValue(testid2);
                                             
            inputParameters.add(p);     
                    p = new InputParam();
            p.setName("New Name");
                                    p.setVariableName("newname");
            p.setPrefix("");
            p.setType(VarType.String);
                                                                     p.setValue(newname3);
                                             
            inputParameters.add(p);     
                
        
        
        return inputParameters;
    }   

    private List<OutputParam> getOutput() {
            if (outputParameters == null) {
            outputParameters = new ArrayList<OutputParam>();
            OutputParam p = null;
                    p = new OutputParam();
            p.setName("Execution Output");
                                    p.setVariableName("executionoutput");
            p.setType(VarType.String);
            
                            p.setFilterType(FilterType.REGEX);
                        
                                                                     p.setFilterValue( executionoutput1 );
                                     
            outputParameters.add(p);                    
                    p = new OutputParam();
            p.setName("Error Output");
                                    p.setVariableName("erroroutput");
            p.setType(VarType.String);
            
                            p.setFilterType(FilterType.REGEX);
                        
                                                                     p.setFilterValue( erroroutput2 );
                                     
            outputParameters.add(p);                    
                    p = new OutputParam();
            p.setName("Exit Code");
                                    p.setVariableName("exitcode");
            p.setType(VarType.Integer);
            
                            p.setFilterType(FilterType.REGEX);
                        
                                                                                     if(exitcode3 != null) {
                    p.setFilterValue( exitcode3.toString() );
                }
                                     
            outputParameters.add(p);                    
                }
            return outputParameters;
    }

    public HashMap getRequestParameters() {
        HashMap reqParams = null;
        
        return reqParams;
    }

    public HashMap getHeaders() {
        HashMap headers = null;
                headers = new HashMap<String, String>();

                    String header1 = ReplacementsHelper.replaceInputParameters("x-api-key", getInput());
                        String headerValue1 = ReplacementsHelper.replaceInputParameters("${API Key}", getInput());
            headers.put(header1, headerValue1);
                
        return headers;
    }
}