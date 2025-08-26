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
            String str = SystemProperties.get("persist.sys.omc_etcpath");
            String str2 = "/system/csc/unique_text.xml";
            if (!TextUtils.isEmpty(str)) {
                str2 = str + "/unique_text.xml";
            }
            Log.secD(TAG, "path name : " + str2);
            update(str2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.secD(TAG, "feature state : true");
    }

    public static CustomizedTextParser getInstance() {
        synchronized (CustomizedTextParser.class) {
            if (sInstance == null) {
                CustomizedTextParser customizedTextParser = new CustomizedTextParser();
                sInstance = customizedTextParser;
                customizedTextParser.initialize();
            }
        }
        return sInstance;
    }

    private void initialize() {
        Log.d(TAG, "Initialzed");
        this.mRuleMap = new HashMap<>();
        Node nodeSearch = search();
        if (nodeSearch == null) {
            return;
        }
        NodeList nodeListSearchList = searchList(nodeSearch);
        if (nodeListSearchList == null) {
            Log.i(TAG, "createCscRuleMap:No Rule info");
            return;
        }
        int length = nodeListSearchList.getLength();
        for (int i = 0; i < length; i++) {
            Node nodeItem = nodeListSearchList.item(i);
            Node nodeSearch2 = search(nodeItem, "source");
            Node nodeSearch3 = search(nodeItem, TAG_TARGET_STRING);
            if (nodeSearch2 == null || nodeSearch3 == null) {
                Log.e(TAG, "createCscRuleMap:src or target is null. srcTemp =" + nodeSearch2 + ",target=" + nodeSearch3);
            } else {
                this.mRuleMap.put(getValue(nodeSearch2), getValue(nodeSearch3));
            }
        }
        Log.d(TAG, "Initialzed: Finished. size=" + sInstance.mRuleMap.size());
    }

    public String getCustomizedText(String str) {
        HashMap<String, String> map = this.mRuleMap;
        if (map == null || map.size() <= 0) {
            Log.e(TAG, "getCustomizedText Rule is empty. mRuleMap=" + this.mRuleMap);
            return str;
        }
        String str2 = this.mRuleMap.get(str);
        if (str2 != null) {
            return str2;
        }
        String strTrim = str.trim();
        String str3 = this.mRuleMap.get(strTrim);
        if (str3 == null) {
            Log.e(TAG, "convertString replaceText is null. preString= " + str);
            return str;
        }
        return str.replace(strTrim, str3);
    }

    private void update(String str) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilder documentBuilderNewDocumentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        File file = new File(str);
        if (file.exists()) {
            Document document = documentBuilderNewDocumentBuilder.parse(file);
            this.mDoc = document;
            this.mRoot = document.getDocumentElement();
            return;
        }
        Log.secE(TAG, "update : XML file doesn't exist");
    }

    private String getValue(Node node) {
        if (node == null) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        if (node.getChildNodes().getLength() > 1) {
            for (int i = 0; i < node.getChildNodes().getLength(); i++) {
                stringBuffer.append(node.getChildNodes().item(i).getNodeValue());
            }
            return stringBuffer.toString();
        }
        Node firstChild = node.getFirstChild();
        if (firstChild != null) {
            return firstChild.getNodeValue();
        }
        return null;
    }

    private Node search() {
        Node nodeSearch = this.mRoot;
        StringTokenizer stringTokenizer = new StringTokenizer(PATH_CUSTOM_INFO, MediaMetrics.SEPARATOR);
        while (stringTokenizer.hasMoreTokens()) {
            String strNextToken = stringTokenizer.nextToken();
            if (nodeSearch == null) {
                return null;
            }
            nodeSearch = search(nodeSearch, strNextToken);
        }
        return nodeSearch;
    }

    private Node search(Node node, String str) {
        NodeList childNodes;
        if (node != null && (childNodes = node.getChildNodes()) != null) {
            int length = childNodes.getLength();
            for (int i = 0; i < length; i++) {
                Node nodeItem = childNodes.item(i);
                if (nodeItem.getNodeName().equals(str)) {
                    return nodeItem;
                }
            }
        }
        return null;
    }

    private NodeList searchList(Node node) {
        if (node == null) {
            return null;
        }
        try {
            CscNodeList cscNodeList = new CscNodeList();
            NodeList childNodes = node.getChildNodes();
            if (childNodes != null) {
                int length = childNodes.getLength();
                for (int i = 0; i < length; i++) {
                    Node nodeItem = childNodes.item(i);
                    if (nodeItem.getNodeName().equals(TAG_RULE_INFO)) {
                        try {
                            cscNodeList.appendChild(nodeItem);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            return cscNodeList;
        } catch (Exception unused) {
            return null;
        }
    }

    private static final class CscNodeList implements NodeList {
        private ArrayList<Node> children;

        private CscNodeList() {
            this.children = new ArrayList<>();
        }

        void appendChild(Node node) {
            this.children.add(node);
        }

        @Override // org.w3c.dom.NodeList
        public int getLength() {
            return this.children.size();
        }

        @Override // org.w3c.dom.NodeList
        public Node item(int i) {
            return this.children.get(i);
        }
    }
}
