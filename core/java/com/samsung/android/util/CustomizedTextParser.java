package com.samsung.android.util;

import android.media.MediaMetrics;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.secutil.Log;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/* loaded from: classes6.dex */
public class CustomizedTextParser {
    private static final String CSC_XML_FILE_NAME = "unique_text.xml";
    private static final String CSC_XML_FILE_PATH = "/system/csc/";
    static final String PATH_CUSTOM_INFO = "CustomizedText";
    public static final String REPLACE_TAG = "cst";
    private static final String TAG = "CustomizedTextParser";
    static final String TAG_RULE_INFO = "Rule";
    static final String TAG_SOURCE_STRING = "source";
    static final String TAG_TARGET_STRING = "target";
    private static CustomizedTextParser sInstance;
    private Document mDoc;
    private Node mRoot;
    private HashMap<String, String> mRuleMap;

    private CustomizedTextParser() {
        try {
            String omcEtcPath = SystemProperties.get("persist.sys.omc_etcpath");
            String pathName = TextUtils.isEmpty(omcEtcPath) ? "/system/csc/unique_text.xml" : omcEtcPath + "/" + CSC_XML_FILE_NAME;
            Log.secD(TAG, "path name : " + pathName);
            update(pathName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.secD(TAG, "feature state : true");
    }

    public static CustomizedTextParser getInstance() {
        synchronized (CustomizedTextParser.class) {
            if (sInstance == null) {
                sInstance = new CustomizedTextParser();
                sInstance.initialize();
            }
        }
        return sInstance;
    }

    private void initialize() {
        Log.d(TAG, "Initialzed");
        this.mRuleMap = new HashMap<>();
        Node node = search();
        if (node == null) {
            return;
        }
        NodeList nodeList = searchList(node);
        if (nodeList == null) {
            Log.i(TAG, "createCscRuleMap:No Rule info");
            return;
        }
        int stringSize = nodeList.getLength();
        for (int i = 0; i < stringSize; i++) {
            Node currentNode = nodeList.item(i);
            Node srcTemp = search(currentNode, "source");
            Node targetTemp = search(currentNode, TAG_TARGET_STRING);
            if (srcTemp != null && targetTemp != null) {
                this.mRuleMap.put(getValue(srcTemp), getValue(targetTemp));
            } else {
                Log.e(TAG, "createCscRuleMap:src or target is null. srcTemp =" + srcTemp + ",target=" + targetTemp);
            }
        }
        Log.d(TAG, "Initialzed: Finished. size=" + sInstance.mRuleMap.size());
    }

    public String getCustomizedText(String string) {
        if (this.mRuleMap == null || this.mRuleMap.size() <= 0) {
            Log.e(TAG, "getCustomizedText Rule is empty. mRuleMap=" + this.mRuleMap);
            return string;
        }
        String replaceText = this.mRuleMap.get(string);
        if (replaceText == null) {
            String trimText = string.trim();
            String trimReplaceText = this.mRuleMap.get(trimText);
            if (trimReplaceText == null) {
                Log.e(TAG, "convertString replaceText is null. preString= " + string);
                return string;
            }
            return string.replace(trimText, trimReplaceText);
        }
        return replaceText;
    }

    private void update(String fileName) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        File fe = new File(fileName);
        if (fe.exists()) {
            this.mDoc = builder.parse(fe);
            this.mRoot = this.mDoc.getDocumentElement();
        } else {
            Log.secE(TAG, "update : XML file doesn't exist");
        }
    }

    private String getValue(Node node) {
        if (node == null) {
            return null;
        }
        StringBuffer buf = new StringBuffer();
        if (node.getChildNodes().getLength() > 1) {
            for (int idx = 0; idx < node.getChildNodes().getLength(); idx++) {
                buf.append(node.getChildNodes().item(idx).getNodeValue());
            }
            return buf.toString();
        }
        Node firstChild = node.getFirstChild();
        if (firstChild != null) {
            return firstChild.getNodeValue();
        }
        return null;
    }

    private Node search() {
        Node node = this.mRoot;
        StringTokenizer tokenizer = new StringTokenizer(PATH_CUSTOM_INFO, MediaMetrics.SEPARATOR);
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            if (node == null) {
                return null;
            }
            node = search(node, token);
        }
        return node;
    }

    private Node search(Node parent, String name) {
        NodeList children;
        if (parent != null && (children = parent.getChildNodes()) != null) {
            int n = children.getLength();
            for (int i = 0; i < n; i++) {
                Node child = children.item(i);
                if (child.getNodeName().equals(name)) {
                    return child;
                }
            }
        }
        return null;
    }

    private NodeList searchList(Node parent) {
        if (parent == null) {
            return null;
        }
        try {
            CscNodeList list = new CscNodeList();
            NodeList children = parent.getChildNodes();
            if (children != null) {
                int n = children.getLength();
                for (int i = 0; i < n; i++) {
                    Node child = children.item(i);
                    if (child.getNodeName().equals(TAG_RULE_INFO)) {
                        try {
                            list.appendChild(child);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            return list;
        } catch (Exception e2) {
            return null;
        }
    }

    private static final class CscNodeList implements NodeList {
        private ArrayList<Node> children;

        private CscNodeList() {
            this.children = new ArrayList<>();
        }

        void appendChild(Node newChild) {
            this.children.add(newChild);
        }

        @Override // org.w3c.dom.NodeList
        public int getLength() {
            return this.children.size();
        }

        @Override // org.w3c.dom.NodeList
        public Node item(int index) {
            return this.children.get(index);
        }
    }
}
