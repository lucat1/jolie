/*
 * This class generates a fixed wsdl1.1 document using WSDL4J API;
 * The main purpose is to test WSDL4J API.
 *
 */
package joliex.wsdl;

import joliex.wsdl.*;
import com.ibm.wsdl.BindingImpl;
import com.ibm.wsdl.BindingInputImpl;
import com.ibm.wsdl.BindingOperationImpl;
import com.ibm.wsdl.BindingOutputImpl;
import com.ibm.wsdl.DefinitionImpl;
import com.ibm.wsdl.InputImpl;
import com.ibm.wsdl.MessageImpl;
import com.ibm.wsdl.OutputImpl;
import com.ibm.wsdl.PartImpl;
import com.ibm.wsdl.PortTypeImpl;
import com.ibm.wsdl.ServiceImpl;
import com.ibm.wsdl.TypesImpl;
import com.ibm.wsdl.extensions.schema.SchemaImpl;
import com.ibm.wsdl.extensions.soap.SOAPBindingImpl;
import com.ibm.wsdl.extensions.soap.SOAPBodyImpl;
import com.ibm.wsdl.extensions.soap.SOAPOperationImpl;
import com.ibm.wsdl.util.xml.QNameUtils;
import com.sun.org.apache.xerces.internal.dom.DocumentTypeImpl;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.wsdl.Binding;
import javax.wsdl.BindingInput;
import javax.wsdl.BindingOperation;
import javax.wsdl.BindingOutput;
import javax.wsdl.Definition;
import javax.wsdl.Input;
import javax.wsdl.Message;
import javax.wsdl.Operation;
import javax.wsdl.OperationType;
import javax.wsdl.Output;
import javax.wsdl.Part;
import javax.wsdl.Port;
import javax.wsdl.PortType;
import javax.wsdl.Service;
import javax.wsdl.Types;
import javax.wsdl.WSDLException;
import javax.wsdl.extensions.ExtensibilityElement;
import javax.wsdl.extensions.ExtensionRegistry;
import javax.wsdl.extensions.schema.Schema;
import javax.wsdl.extensions.soap.SOAPAddress;
import javax.wsdl.extensions.soap.SOAPBinding;
import javax.wsdl.extensions.soap.SOAPBody;
import javax.wsdl.extensions.soap.SOAPOperation;
import javax.wsdl.factory.WSDLFactory;
import javax.wsdl.xml.WSDLReader;
import javax.wsdl.xml.WSDLWriter;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author Francesco
 * see also http://forums.sun.com/thread.jspa?threadID=670586
 */
public class WsdlEffectorImplWsdl4J {

    static String tns = "http://www.italianasoftware.com/wsdl/FirstServiceByWSDL4J.wsdl";
    static String xsd = "http://www.w3.org/2001/XMLSchema";
    static String soap = "http://schemas.xmlsoap.org/wsdl/soap/";
    static String soapOverHttp = "http://schemas.xmlsoap.org/wsdl/soap/http";
    static ExtensionRegistry extensionRegistry;
    private static WSDLFactory wsdlFactory;
    private static Definition def;
/*
    public static Definition init() {
        try {
            wsdlFactory = WSDLFactory.newInstance();
            def = wsdlFactory.newDefinition();
            extensionRegistry = wsdlFactory.newPopulatedExtensionRegistry();
            WSDLWriter writer = wsdlFactory.newWSDLWriter();

            //Veder se prendere def.createBlaBla
            //TODO ...spostare quete vars
            String serviceName = "TwiceService";
            String opName = "twice";
            QName servDefQN = new QName(serviceName + "QN_TODO_rendereParametrico");
            def.setQName(servDefQN);

            String targetNS = tns;

            def.addNamespace("soap", soap);
            def.addNamespace("tns", tns);
            def.addNamespace("xsd", xsd);
            def.setTargetNamespace(targetNS);
        } catch (WSDLException ex) {
            Logger.getLogger(WsdlEffectorImplWsdl4J.class.getName()).log(Level.SEVERE, null, ex);
        }
        return def;
    }

    public static void addMessage(String inputPartName, String inputPartType) {
        Input input = def.createInput();
        Message inputMessage = def.createMessage();
        Part inputPart = def.createPart();
        inputPart.setName(inputPartName);
        inputPart.setTypeName(new QName("http://www.w3.org/2001/XMLSchema", inputPartType));
        inputMessage.addPart(inputPart);
        input.setMessage(inputMessage);

        Output output = def.createOutput();
        Message outputMessage = def.createMessage();

        output.setMessage(outputMessage);
    }

    public static void addPortType(String opName) {
        Operation operation = def.createOperation();
        operation.setName(opName);
        operation.setStyle(OperationType.REQUEST_RESPONSE);

        Input input = def.createInput();
        Message inputMessage = def.createMessage();
        Part inputPart = def.createPart();
        inputPart.setName("string");
        inputPart.setTypeName(new QName("http://www.w3.org/2001/XMLSchema", "string"));
        inputMessage.addPart(inputPart);
        operation.setInput(input);
        input.setMessage(inputMessage);
        Output output = def.createOutput();
        Message outputMessage = def.createMessage();
        operation.setOutput(output);
        output.setMessage(outputMessage);

    }

    public static void addBinding(PortType pt, Operation wsdlOp) {
        Binding b = def.createBinding();
        //def.a
        b.setUndefined(false);
        QName binding_QN = new QName("Binding_QN");
        b.setQName(binding_QN);
        b.setPortType(pt);

        BindingOperation bOp = new BindingOperationImpl();

        bOp.setOperation(wsdlOp);
        //SOAPOperation soapOperation = (SOAPOperation) extensionRegistry.createExtension(BindingOperation.class, new QName("http://schemas.xmlsoap.org/wsdl/soap/", "operation"));
        SOAPOperation soapOperation = new SOAPOperationImpl();
        soapOperation.setStyle("document");
        // soapOperation.setStyle("rpc");
        //QName soapOpQN=new QName("")

        BindingInput bi = new BindingInputImpl();
        bi.setName("bindingInput_name_str");
        bOp.setBindingInput(bi);
        BindingOutput bo = new BindingOutputImpl();
        bo.setName("bindingOutput_name_str");
        bOp.setBindingOutput(bo);
        //bOp.setBindingInput(null)
        //bOp.setBindingOutput(null)
        b.addBindingOperation(bOp);
        def.addBinding(b);
    }

    public static void addBindingSOAP(PortType pt, Operation wsdlOp) {
        //TODO
        Binding b = def.createBinding();
        //def.a
        b.setUndefined(false);
        QName binding_QN = new QName("Binding_QN");
        b.setQName(binding_QN);
        b.setPortType(pt);

        BindingOperation bOp = new BindingOperationImpl();

        bOp.setOperation(wsdlOp);
        //SOAPOperation soapOperation = (SOAPOperation) extensionRegistry.createExtension(BindingOperation.class, new QName("http://schemas.xmlsoap.org/wsdl/soap/", "operation"));
        SOAPOperation soapOperation = new SOAPOperationImpl();
        soapOperation.setStyle("document");
        // soapOperation.setStyle("rpc");
        //QName soapOpQN=new QName("")

        BindingInput bi = new BindingInputImpl();
        bi.setName("bindingInput_name_str");
        bOp.setBindingInput(bi);
        BindingOutput bo = new BindingOutputImpl();
        bo.setName("bindingOutput_name_str");
        bOp.setBindingOutput(bo);
        //bOp.setBindingInput(null)
        //bOp.setBindingOutput(null)
        b.addBindingOperation(bOp);
        def.addBinding(b);
    }

    public static void addService(Port p) {
        Service s = new ServiceImpl();
        QName qn0 = new QName("Service_QN");
        // javax.xml.namespace.QName qn=new QNameUtils().newQName( );
        s.setQName(qn0);
        s.addPort(p);
    }
*/
    /*
    public static void test01() {
        String serviceName = "order_service";
        String opName = "create_order";

        Definition def = init();

        // try {

//xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/"

        Operation operation = def.createOperation();
        operation.setName(opName);
        operation.setStyle(OperationType.REQUEST_RESPONSE);

        Input input = def.createInput();
        Message inputMessage = def.createMessage();
        Part inputPart = def.createPart();
        inputPart.setName("string");
        inputPart.setTypeName(new QName(xsd, "string"));
        inputMessage.addPart(inputPart);
        operation.setInput(input);
        input.setMessage(inputMessage);
        Output output = def.createOutput();


        Message outputMessage = def.createMessage();
        operation.setOutput(output);
        output.setMessage(outputMessage);

        BindingOperation bindOp = def.createBindingOperation();
        bindOp.setName(opName);

        //SOAPOperation soapOperation = (SOAPOperation) extensionRegistry.createExtension(BindingOperation.class, new QName("http://schemas.xmlsoap.org/wsdl/soap/", "operation"));
        SOAPOperation soapOperation = new SOAPOperationImpl();
        soapOperation.setStyle("document");
        // soapOperation.setStyle("rpc");
        //soapOperation.set
        soapOperation.setSoapActionURI("");
        bindOp.addExtensibilityElement(soapOperation);
        bindOp.setOperation(operation);

        BindingInput bindingInput = def.createBindingInput();

        //SOAPBody inputBody = (SOAPBody) extensionRegistry.createExtension(BindingInput.class, new QName("http://schemas.xmlsoap.org/wsdl/soap/", "body"));
        SOAPBody inputBody = new SOAPBodyImpl();
        inputBody.setUse("literal");
        //inputBody.setEncodingStyles(null);
        //inputBody.setUse("encoded");
        bindingInput.addExtensibilityElement(inputBody);
        bindOp.setBindingInput(bindingInput);
        BindingOutput bindingOutput = def.createBindingOutput();
        bindingOutput.addExtensibilityElement(inputBody);
        bindOp.setBindingOutput(bindingOutput);
//        } catch (WSDLException ex) {
//            Logger.getLogger(WsdlEffectorImplWsdl4J.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
*/
    public static void test00(String fileName) {
        //if (fileName.equals("")) {fileName="./src/test/resources/prova_out.wsdl";}
        Definition rr;
        Schema sch;
        //Definition def=init();
        {
            Writer fw = null;
            try {
                WSDLFactory f = WSDLFactory.newInstance();
                WSDLReader r = f.newWSDLReader();
                //r.setExtensionRegistry(null);
                r.setFeature("javax.wsdl.verbose", true);
                r.setFeature("javax.wsdl.importDocuments", true);
                //r.readWSDL("");
                WSDLWriter ww = f.newWSDLWriter();

                fw = new FileWriter(fileName);
                //def = new DefinitionImpl();

                wsdlFactory = WSDLFactory.newInstance();
                def = wsdlFactory.newDefinition();
                extensionRegistry = wsdlFactory.newPopulatedExtensionRegistry();
                //extensionRegistry.
                //TODO ...spostare quete vars
                String serviceName = "TwiceService";
                String opName = "twice";

                QName servDefQN = new QName(serviceName);
                def.setQName(servDefQN);

                String targetNS = tns;

                def.addNamespace("soap", soap);
                def.addNamespace("tns", tns);
                def.addNamespace("xsd", xsd);
                def.setTargetNamespace(targetNS);

                //def.setDocumentBaseURI("http://docBaseURI");
//============================= TYPES =============================
                //Types types=new TypesImpl();
                Types types = def.createTypes();
                //TODO Come aggiungere schema (o riferimento a xsd)  per i tipi?
                
                sch = new SchemaImpl();
                /*TODO DeCommentare la seguente parte di codice e vedere perchè non genera l'elemento wsdl:types
                //TODO Da qui in poi devo solo riempire lo schema con i giusti elementi...;
                //TODO Come creare elementi da settare nelle istanze della classe SchemaImpl
                Document document,document02 = null;
                Element rootElement = null;
                DocumentBuilder db = null;
                try {

                    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                    System.out.println(" dbf=" + dbf);
                    db = dbf.newDocumentBuilder();
                    System.out.println(" db=" + db);

                    //rootElement.setAttribute("caption", caption);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                //FIXME: document è = null
                document = db.newDocument();
                System.out.println(" document=" + document);
                //DOMImplementation di=db.getDOMImplementation();
                 //System.out.println(" di=" + di);
                //DocumentType dt=DocumentType();
                //document02=di.createDocument(fileName, serviceName, dt);

                rootElement = document.createElement("schema");
                Element element = document.createElement("complexType");
                element.setNodeValue("nodeValue");
                element.setTextContent("textContent");

                sch.setElement(element);
                //sch.setElementType(new QName());
                //Node n = new NodeImpl();
                System.out.println(" document=" + document);
                rootElement.appendChild(element);
                //element.setNodeValue("nodeValue");
                //element.setTextContent("textContent");
                //element.setAttribute("name", "contact");

                System.out.println(" element=" + element);
                sch.setElement(rootElement);


                 
                System.out.println(" sch=" + sch);
                ExtensibilityElement extElement = sch;
                System.out.println(" extElement=" + extElement);
                //Element schemaElement = new Element();
                //Element schemaElement = ((SchemaImpl) element).getElement();
                //new   org.w3c.dom.Element();
                
                //NOTA-BENE Punto di aggancio wsdl:type - schema
                types.addExtensibilityElement(extElement);

                //types.setDocumentationElement(null);
                 * *
                 */
                
                System.out.println(" types=" + types);
                def.setTypes(types);
//============================= MESSAGE =============================
                Message msg_req = new MessageImpl();
                msg_req.setUndefined(false);
                QName msg_req_QN = new QName(serviceName + "Request");
                msg_req.setQName(msg_req_QN);
                //msg_req.
                //TODO msg_req.setQName(null);
                Part part01a = new PartImpl();
                //--------------------
                part01a.setName("parameters");
                part01a.setElementName(new QName(opName));
                //----------------

                QName partType01a = new QName(xsd, "string");
                //part01a.setTypeName(partType01a);
                msg_req.addPart(part01a);
                def.addMessage(msg_req);
//------------------------- Resp mesg --------------------
                Message msg_resp = new MessageImpl();
                msg_resp.setUndefined(false);
                QName msg_resp_QN = new QName(serviceName + "Response");
                msg_resp.setQName(msg_resp_QN);
                QName qn02a = new QName(opName + "Response");
                //msg_resp.setQName("");
                //QName qn02a=new QName("xsd:String");
                Part p02a = new PartImpl();
                //--------------------
                //DOCUMENT
                p02a.setName("parameters");
                p02a.setElementName(qn02a);
                //----------------
                //rpc
                //p02a.setName(serviceName + "ResponsePartName");
                //p02a.setTypeName(qn02a);
                //----------------
                //Node.TEXT_NODE;
                //node= new NodeImpl();
                //QName qn=QNameUtils.newQName(node);
                msg_resp.addPart(p02a);
                def.addMessage(msg_resp);
                System.out.println(" -----------  fine aggiunta messaggi --------------------- ");
//=================================== PORTTYPES (interfaces) ===================================
                //NOTA-BENE i 4 tipi di primitive dicomm sono distitne dalla presenzao meno di elementi input output in questa sezione dentro le operazioni
                Operation wsdlOp = def.createOperation();
                //wsdlOp.setName(serviceName + "FirstOp");
                wsdlOp.setName(opName);
                wsdlOp.setStyle(OperationType.REQUEST_RESPONSE);
                wsdlOp.setUndefined(false);
                //List<> lp=new ArrayList()<Param>;
                //wsdlOp.setParameterOrdering(null);
                Input in = new InputImpl();
                in.setName("inputInName");
                in.setMessage(msg_req);
                wsdlOp.setInput(in);
                Output out = new OutputImpl();
                out.setName("outputOutName");
                out.setMessage(msg_req);
                wsdlOp.setOutput(out);
                /*Input in=new InputImpl();
                //in.
                wsdlOp.setInput(in); */
//-----------------------------------------------------
                PortType pt = new PortTypeImpl();
                pt.setUndefined(false);
                pt.addOperation(wsdlOp);

                QName pt_QN = new QName("PortTypeName");

                pt.setQName(pt_QN);
                def.addPortType(pt);
                //def.addPortType();
                //----------------------------------

                Service s = new ServiceImpl();
                QName qn0 = new QName(serviceName);
                // javax.xml.namespace.QName qn=new QNameUtils().newQName( );
                s.setQName(qn0);

                System.out.println(" -------------------------------- ");
                //Binding b = new BindingImpl();
                Binding b = def.createBinding();
                //def.a
                b.setUndefined(false);
                QName binding_QN = new QName("SOAPBinding");
                b.setQName(binding_QN);
                b.setPortType(pt);
                //---------------------- BINDINGS -----------------------------
                BindingOperation bindOp = def.createBindingOperation();
                bindOp.setName(opName);
                SOAPOperation soapOperation = (SOAPOperation) extensionRegistry.createExtension(BindingOperation.class, new QName(soap, "operation"));
                soapOperation.setStyle("document");
                //NOTA-BENE: Come settare SOAPACTION? jolie usa SOAP1.1 o 1.2? COme usa la SoapAction?
                soapOperation.setSoapActionURI(opName);
                bindOp.addExtensibilityElement(soapOperation);
                bindOp.setOperation(wsdlOp);
                BindingInput bindingInput = def.createBindingInput();
                SOAPBody inputBody = (SOAPBody) extensionRegistry.createExtension(BindingInput.class, new QName(soap, "body"));
                inputBody.setUse("literal");
                bindingInput.addExtensibilityElement(inputBody);
                bindOp.setBindingInput(bindingInput);
                BindingOutput bindingOutput = def.createBindingOutput();
                bindingOutput.addExtensibilityElement(inputBody);
                bindOp.setBindingOutput(bindingOutput);
                PortType portType = def.createPortType();
                portType.setQName(new QName("", serviceName + "PortType"));
                portType.addOperation(wsdlOp);
                Binding bind = def.createBinding();
                bind.setQName(new QName("", serviceName + "Binding"));
                bind.setPortType(portType);
                bind.setUndefined(false);
                SOAPBinding soapBinding = (SOAPBinding) extensionRegistry.createExtension(Binding.class, new QName(soap, "binding"));
                soapBinding.setTransportURI(soapOverHttp);
                soapBinding.setStyle("document");
                bind.addExtensibilityElement(soapBinding);
                bind.addBindingOperation(bindOp);


                Port port = def.createPort();
                port.setName(serviceName + "Port");
                SOAPAddress soapAddress = (SOAPAddress) extensionRegistry.createExtension(Port.class, new QName(soap, "address"));
                soapAddress.setLocationURI("http://127.0.0.1:8080/foo");
                port.addExtensibilityElement(soapAddress);
                port.setBinding(bind);
                def.addBinding(bind);
                /*
                BindingOperation bOp = new BindingOperationImpl();

                bOp.setOperation(wsdlOp);

                SOAPOperation soapOperationER = (SOAPOperation) extensionRegistry.createExtension(BindingOperation.class, new QName("http://schemas.xmlsoap.org/wsdl/soap/", "operation"));

                SOAPOperation soapOperation = new SOAPOperationImpl();
                soapOperation.setStyle("document");
                // soapOperation.setStyle("document");
                //QName soapOpQN=new QName("");
                //soapOperation.setElementType(qn0);
                soapOperation.setSoapActionURI("");
                //bOp.addExtensibilityElement(soapOperation);
                SOAPBinding soapBinding = (SOAPBinding)extensionRegistry.createExtension(Binding.class,
                new QName(soap,"binding"));
                SOAPBinding soapOpBind = new SOAPBindingImpl();
                soapOpBind.setStyle("document"); //"rpc"
                soapOpBind.setTransportURI("http://schemas.xmlsoap.org/soap/http");
                
                BindingInput bi = new BindingInputImpl();
                bi.setName("bindingInput_name_str");
                bOp.setBindingInput(bi);
                BindingOutput bo = new BindingOutputImpl();
                bo.setName("bindingOutput_name_str");
                bOp.setBindingOutput(bo);
                //bOp.setBindingInput(null)
                //bOp.setBindingOutput(null)
                b.addBindingOperation(bOp);
                def.addBinding(b);

                 */
//========================= SERVICE ===============================
                //Port p = def.createPort();
//                port.setName("SOAPPort");
//                port.setBinding(b);
                s.addPort(port);

                def.addService(s);
                ww.writeWSDL(def, fw);

                //-----------------------------------
                System.out.println(" -------------------------------- ");
                rr = r.readWSDL(fileName);
                //rr.
                System.out.println(rr.toString());
            } catch (IOException ex) {
                Logger.getLogger(WsdlEffectorImplWsdl4J.class.getName()).log(Level.SEVERE, null, ex);
            } catch (WSDLException ex) {
                Logger.getLogger(WsdlEffectorImplWsdl4J.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    fw.close();
                } catch (IOException ex) {
                    Logger.getLogger(WsdlEffectorImplWsdl4J.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static void main(String[] arg) {
        //test00("./src/test/resources/provaInputPortsTrial00.wsdl");
        test00("./provaInputPortsTrial00.wsdl");
        //test01();
    }
}
