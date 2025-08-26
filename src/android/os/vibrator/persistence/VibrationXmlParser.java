package android.os.vibrator.persistence;

import android.annotation.SystemApi;
import android.os.VibrationEffect;
import android.util.Xml;
import com.android.internal.vibrator.persistence.VibrationEffectXmlParser;
import com.android.internal.vibrator.persistence.XmlConstants;
import com.android.internal.vibrator.persistence.XmlParserException;
import com.android.internal.vibrator.persistence.XmlReader;
import com.android.internal.vibrator.persistence.XmlValidator;
import com.android.modules.utils.TypedXmlPullParser;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParserException;

@SystemApi
/* loaded from: classes3.dex */
public final class VibrationXmlParser {
    public static final String APPLICATION_VIBRATION_XML_MIME_TYPE = "application/vnd.android.haptics.vibration+xml";
    public static final int FLAG_ALLOW_HIDDEN_APIS = 1;

    /* JADX INFO: Access modifiers changed from: private */
    interface ElementParser<T> {
        T parse(TypedXmlPullParser typedXmlPullParser, int i) throws IOException, XmlParserException;
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    public static boolean isSupportedMimeType(String str) {
        return APPLICATION_VIBRATION_XML_MIME_TYPE.equals(str);
    }

    @SystemApi
    public static ParsedVibration parse(InputStream inputStream) throws IOException {
        return parseDocument(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
    }

    @SystemApi
    public static VibrationEffect parseVibrationEffect(InputStream inputStream) throws IOException {
        return parseVibrationEffect(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
    }

    public static VibrationEffect parseVibrationEffect(Reader reader) throws IOException {
        return parseVibrationEffect(reader, 0);
    }

    public static VibrationEffect parseVibrationEffect(Reader reader, int i) throws IOException {
        return (VibrationEffect) parseDocumentInternal(reader, i, new ElementParser() { // from class: android.os.vibrator.persistence.VibrationXmlParser$$ExternalSyntheticLambda1
            @Override // android.os.vibrator.persistence.VibrationXmlParser.ElementParser
            public final Object parse(TypedXmlPullParser typedXmlPullParser, int i2) {
                return VibrationXmlParser.parseVibrationEffectInternal(typedXmlPullParser, i2);
            }
        });
    }

    public static ParsedVibration parseDocument(Reader reader) throws IOException {
        return parseDocument(reader, 0);
    }

    public static ParsedVibration parseDocument(Reader reader, int i) throws IOException {
        return (ParsedVibration) parseDocumentInternal(reader, i, new ElementParser() { // from class: android.os.vibrator.persistence.VibrationXmlParser$$ExternalSyntheticLambda0
            @Override // android.os.vibrator.persistence.VibrationXmlParser.ElementParser
            public final Object parse(TypedXmlPullParser typedXmlPullParser, int i2) {
                return VibrationXmlParser.parseElementInternal(typedXmlPullParser, i2);
            }
        });
    }

    public static ParsedVibration parseElement(TypedXmlPullParser typedXmlPullParser, int i) throws IOException {
        try {
            return parseElementInternal(typedXmlPullParser, i);
        } catch (XmlParserException e) {
            throw new ParseFailedException(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static ParsedVibration parseElementInternal(TypedXmlPullParser typedXmlPullParser, int i) throws IOException, XmlParserException {
        XmlValidator.checkStartTag(typedXmlPullParser);
        String name = typedXmlPullParser.getName();
        name.hashCode();
        if (name.equals(XmlConstants.TAG_VIBRATION_EFFECT)) {
            return new ParsedVibration(parseVibrationEffectInternal(typedXmlPullParser, i));
        }
        if (name.equals(XmlConstants.TAG_VIBRATION_SELECT)) {
            return parseVibrationSelectInternal(typedXmlPullParser, i);
        }
        throw new ParseFailedException("Unexpected tag " + name + " when parsing a vibration");
    }

    private static ParsedVibration parseVibrationSelectInternal(TypedXmlPullParser typedXmlPullParser, int i) throws IOException, XmlParserException {
        XmlValidator.checkStartTag(typedXmlPullParser, XmlConstants.TAG_VIBRATION_SELECT);
        XmlValidator.checkTagHasNoUnexpectedAttributes(typedXmlPullParser, new String[0]);
        int depth = typedXmlPullParser.getDepth();
        ArrayList arrayList = new ArrayList();
        while (XmlReader.readNextTagWithin(typedXmlPullParser, depth)) {
            arrayList.add(parseVibrationEffectInternal(typedXmlPullParser, i));
        }
        return new ParsedVibration(arrayList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static VibrationEffect parseVibrationEffectInternal(TypedXmlPullParser typedXmlPullParser, int i) throws IOException, XmlParserException {
        return VibrationEffectXmlParser.parseTag(typedXmlPullParser, (i & 1) == 0 ? 0 : 1).deserialize();
    }

    private static <T> T parseDocumentInternal(Reader reader, int i, ElementParser<T> elementParser) throws IOException {
        try {
            TypedXmlPullParser typedXmlPullParserNewFastPullParser = Xml.newFastPullParser();
            typedXmlPullParserNewFastPullParser.setFeature("http://xmlpull.org/v1/doc/features.html#process-namespaces", true);
            typedXmlPullParserNewFastPullParser.setInput(reader);
            XmlReader.readDocumentStart(typedXmlPullParserNewFastPullParser);
            T t = elementParser.parse(typedXmlPullParserNewFastPullParser, i);
            XmlReader.readDocumentEndTag(typedXmlPullParserNewFastPullParser);
            return t;
        } catch (XmlParserException e) {
            throw new ParseFailedException(e);
        } catch (XmlPullParserException e2) {
            throw new ParseFailedException("Error initializing XMLPullParser", e2);
        }
    }

    public static final class ParseFailedException extends IOException {
        private ParseFailedException(String str) {
            super(str);
        }

        private ParseFailedException(XmlParserException xmlParserException) {
            this(xmlParserException.getMessage(), xmlParserException);
        }

        private ParseFailedException(String str, Throwable th) {
            super(str, th);
        }
    }

    private VibrationXmlParser() {
    }
}
