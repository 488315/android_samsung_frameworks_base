package android.util;

import android.os.SystemProperties;
import android.system.ErrnoException;
import android.system.Os;
import com.android.internal.util.ArtBinaryXmlPullParser;
import com.android.internal.util.ArtBinaryXmlSerializer;
import com.android.internal.util.FastXmlSerializer;
import com.android.internal.util.XmlUtils;
import com.android.modules.utils.BinaryXmlPullParser;
import com.android.modules.utils.BinaryXmlSerializer;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import javax.xml.parsers.SAXParserFactory;
import libcore.util.XmlObjectFactory;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

/* loaded from: classes4.dex */
public class Xml {
    public static final boolean ENABLE_BINARY_DEFAULT = shouldEnableBinaryDefault();
    public static final boolean ENABLE_RESOLVE_OPTIMIZATIONS = shouldEnableResolveOptimizations();
    public static String FEATURE_RELAXED = "http://xmlpull.org/v1/doc/features.html#relaxed";

    private static boolean shouldEnableBinaryDefault$ravenwood() {
        return true;
    }

    private static boolean shouldEnableResolveOptimizations() {
        return true;
    }

    private static boolean shouldEnableResolveOptimizations$ravenwood() {
        return false;
    }

    private Xml() {
    }

    private static boolean shouldEnableBinaryDefault() {
        return SystemProperties.getBoolean("persist.sys.binary_xml", true);
    }

    public static void parse(String str, ContentHandler contentHandler) throws SAXException {
        try {
            XMLReader newXMLReader = newXMLReader();
            newXMLReader.setContentHandler(contentHandler);
            newXMLReader.parse(new InputSource(new StringReader(str)));
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    public static void parse(Reader reader, ContentHandler contentHandler) throws IOException, SAXException {
        XMLReader newXMLReader = newXMLReader();
        newXMLReader.setContentHandler(contentHandler);
        newXMLReader.parse(new InputSource(reader));
    }

    public static void parse(InputStream inputStream, Encoding encoding, ContentHandler contentHandler) throws IOException, SAXException {
        XMLReader newXMLReader = newXMLReader();
        newXMLReader.setContentHandler(contentHandler);
        InputSource inputSource = new InputSource(inputStream);
        inputSource.setEncoding(encoding.expatName);
        newXMLReader.parse(inputSource);
    }

    public static XmlPullParser newPullParser() {
        try {
            XmlPullParser newXmlPullParser = newXmlPullParser();
            newXmlPullParser.setFeature("http://xmlpull.org/v1/doc/features.html#process-docdecl", true);
            newXmlPullParser.setFeature("http://xmlpull.org/v1/doc/features.html#process-namespaces", true);
            return newXmlPullParser;
        } catch (XmlPullParserException e) {
            throw new AssertionError(e);
        }
    }

    public static XmlPullParser newPullParser$ravenwood() {
        try {
            XmlPullParser newXmlPullParser = newXmlPullParser();
            newXmlPullParser.setFeature("http://xmlpull.org/v1/doc/features.html#process-namespaces", true);
            return newXmlPullParser;
        } catch (XmlPullParserException e) {
            throw new AssertionError(e);
        }
    }

    public static TypedXmlPullParser newFastPullParser() {
        return XmlUtils.makeTyped(newPullParser());
    }

    public static TypedXmlPullParser newBinaryPullParser() {
        return new ArtBinaryXmlPullParser();
    }

    public static TypedXmlPullParser newBinaryPullParser$ravenwood() {
        return new BinaryXmlPullParser();
    }

    public static TypedXmlPullParser resolvePullParser(InputStream inputStream) throws IOException {
        TypedXmlPullParser newFastPullParser;
        byte[] bArr = new byte[4];
        if (ENABLE_RESOLVE_OPTIMIZATIONS && (inputStream instanceof FileInputStream)) {
            try {
                Os.pread(((FileInputStream) inputStream).getFD(), bArr, 0, 4, 0L);
            } catch (ErrnoException e) {
                throw e.rethrowAsIOException();
            }
        } else {
            if (!inputStream.markSupported()) {
                inputStream = new BufferedInputStream(inputStream);
            }
            inputStream.mark(8);
            inputStream.read(bArr);
            inputStream.reset();
        }
        if (Arrays.equals(bArr, BinaryXmlSerializer.PROTOCOL_MAGIC_VERSION_0)) {
            newFastPullParser = newBinaryPullParser();
        } else {
            newFastPullParser = newFastPullParser();
        }
        try {
            newFastPullParser.setInput(inputStream, StandardCharsets.UTF_8.name());
            return newFastPullParser;
        } catch (XmlPullParserException e2) {
            throw new IOException(e2);
        }
    }

    public static XmlSerializer newSerializer() {
        return newXmlSerializer();
    }

    public static TypedXmlSerializer newFastSerializer() {
        return XmlUtils.makeTyped(new FastXmlSerializer());
    }

    public static TypedXmlSerializer newBinarySerializer() {
        return new ArtBinaryXmlSerializer();
    }

    public static TypedXmlSerializer newBinarySerializer$ravenwood() {
        return new BinaryXmlSerializer();
    }

    public static TypedXmlSerializer resolveSerializer(OutputStream outputStream) throws IOException {
        TypedXmlSerializer newFastSerializer;
        if (ENABLE_BINARY_DEFAULT) {
            newFastSerializer = newBinarySerializer();
        } else {
            newFastSerializer = newFastSerializer();
        }
        newFastSerializer.setOutput(outputStream, StandardCharsets.UTF_8.name());
        return newFastSerializer;
    }

    public static TypedXmlSerializer resolveSerializer$ravenwood(OutputStream outputStream) throws IOException {
        BinaryXmlSerializer binaryXmlSerializer = new BinaryXmlSerializer();
        binaryXmlSerializer.setOutput(outputStream, StandardCharsets.UTF_8.name());
        return binaryXmlSerializer;
    }

    public static void copy(XmlPullParser xmlPullParser, XmlSerializer xmlSerializer) throws XmlPullParserException, IOException {
        if (xmlPullParser.getEventType() == 0) {
            xmlSerializer.startDocument(xmlPullParser.getInputEncoding(), true);
        }
        while (true) {
            int nextToken = xmlPullParser.nextToken();
            switch (nextToken) {
                case 0:
                    xmlSerializer.startDocument(xmlPullParser.getInputEncoding(), true);
                    break;
                case 1:
                    xmlSerializer.endDocument();
                    return;
                case 2:
                    xmlSerializer.startTag(normalizeNamespace(xmlPullParser.getNamespace()), xmlPullParser.getName());
                    for (int i = 0; i < xmlPullParser.getAttributeCount(); i++) {
                        xmlSerializer.attribute(normalizeNamespace(xmlPullParser.getAttributeNamespace(i)), xmlPullParser.getAttributeName(i), xmlPullParser.getAttributeValue(i));
                    }
                    break;
                case 3:
                    xmlSerializer.endTag(normalizeNamespace(xmlPullParser.getNamespace()), xmlPullParser.getName());
                    break;
                case 4:
                    xmlSerializer.text(xmlPullParser.getText());
                    break;
                case 5:
                    xmlSerializer.cdsect(xmlPullParser.getText());
                    break;
                case 6:
                    xmlSerializer.entityRef(xmlPullParser.getName());
                    break;
                case 7:
                    xmlSerializer.ignorableWhitespace(xmlPullParser.getText());
                    break;
                case 8:
                    xmlSerializer.processingInstruction(xmlPullParser.getText());
                    break;
                case 9:
                    xmlSerializer.comment(xmlPullParser.getText());
                    break;
                case 10:
                    xmlSerializer.docdecl(xmlPullParser.getText());
                    break;
                default:
                    throw new IllegalStateException("Unknown token " + nextToken);
            }
        }
    }

    private static String normalizeNamespace(String str) {
        if (str == null || str.isEmpty()) {
            return null;
        }
        return str;
    }

    public enum Encoding {
        US_ASCII("US-ASCII"),
        UTF_8("UTF-8"),
        UTF_16("UTF-16"),
        ISO_8859_1("ISO-8859-1");

        final String expatName;

        Encoding(String str) {
            this.expatName = str;
        }
    }

    public static Encoding findEncodingByName(String str) throws UnsupportedEncodingException {
        if (str == null) {
            return Encoding.UTF_8;
        }
        for (Encoding encoding : Encoding.values()) {
            if (encoding.expatName.equalsIgnoreCase(str)) {
                return encoding;
            }
        }
        throw new UnsupportedEncodingException(str);
    }

    public static AttributeSet asAttributeSet(XmlPullParser xmlPullParser) {
        if (xmlPullParser instanceof AttributeSet) {
            return (AttributeSet) xmlPullParser;
        }
        return new XmlPullAttributes(xmlPullParser);
    }

    private static XmlSerializer newXmlSerializer() {
        return XmlObjectFactory.newXmlSerializer();
    }

    private static XmlSerializer newXmlSerializer$ravenwood() {
        try {
            return XmlPullParserFactory.newInstance().newSerializer();
        } catch (XmlPullParserException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    private static XmlPullParser newXmlPullParser() {
        return XmlObjectFactory.newXmlPullParser();
    }

    private static XmlPullParser newXmlPullParser$ravenwood() {
        try {
            return XmlPullParserFactory.newInstance().newPullParser();
        } catch (XmlPullParserException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    private static XMLReader newXMLReader() {
        return XmlObjectFactory.newXMLReader();
    }

    private static XMLReader newXMLReader$ravenwood() {
        try {
            SAXParserFactory newInstance = SAXParserFactory.newInstance();
            newInstance.setNamespaceAware(true);
            return newInstance.newSAXParser().getXMLReader();
        } catch (Exception e) {
            throw new UnsupportedOperationException(e);
        }
    }
}
