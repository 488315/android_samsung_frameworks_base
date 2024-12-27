package android.os.vibrator.persistence;

import android.os.VibrationEffect;
import android.util.Slog;
import android.util.Xml;
import com.android.internal.vibrator.persistence.VibrationEffectXmlParser;
import com.android.internal.vibrator.persistence.XmlConstants;
import com.android.internal.vibrator.persistence.XmlParserException;
import com.android.internal.vibrator.persistence.XmlReader;
import com.android.internal.vibrator.persistence.XmlValidator;
import com.android.modules.utils.TypedXmlPullParser;
import java.io.IOException;
import java.io.Reader;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes3.dex */
public final class VibrationXmlParser {
    public static final String APPLICATION_VIBRATION_XML_MIME_TYPE = "application/vnd.android.haptics.vibration+xml";
    public static final int FLAG_ALLOW_HIDDEN_APIS = 1;
    private static final String TAG = "VibrationXmlParser";

    /* JADX INFO: Access modifiers changed from: private */
    interface ElementParser<T> {
        T parse(TypedXmlPullParser typedXmlPullParser, int i) throws IOException, XmlParserException;
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    public static boolean isSupportedMimeType(String mimeType) {
        return APPLICATION_VIBRATION_XML_MIME_TYPE.equals(mimeType);
    }

    public static VibrationEffect parseVibrationEffect(Reader reader) throws IOException {
        return parseVibrationEffect(reader, 0);
    }

    public static VibrationEffect parseVibrationEffect(Reader reader, int flags) throws IOException {
        try {
            return (VibrationEffect) parseDocumentInternal(reader, flags, new ElementParser() { // from class: android.os.vibrator.persistence.VibrationXmlParser$$ExternalSyntheticLambda1
                @Override // android.os.vibrator.persistence.VibrationXmlParser.ElementParser
                public final Object parse(TypedXmlPullParser typedXmlPullParser, int i) {
                    VibrationEffect parseVibrationEffectInternal;
                    parseVibrationEffectInternal = VibrationXmlParser.parseVibrationEffectInternal(typedXmlPullParser, i);
                    return parseVibrationEffectInternal;
                }
            });
        } catch (XmlParserException | XmlPullParserException e) {
            Slog.w(TAG, "Error parsing vibration XML", e);
            return null;
        }
    }

    public static ParsedVibration parseDocument(Reader reader) throws IOException {
        return parseDocument(reader, 0);
    }

    public static ParsedVibration parseDocument(Reader reader, int flags) throws IOException {
        try {
            return (ParsedVibration) parseDocumentInternal(reader, flags, new ElementParser() { // from class: android.os.vibrator.persistence.VibrationXmlParser$$ExternalSyntheticLambda0
                @Override // android.os.vibrator.persistence.VibrationXmlParser.ElementParser
                public final Object parse(TypedXmlPullParser typedXmlPullParser, int i) {
                    ParsedVibration parseElementInternal;
                    parseElementInternal = VibrationXmlParser.parseElementInternal(typedXmlPullParser, i);
                    return parseElementInternal;
                }
            });
        } catch (XmlParserException | XmlPullParserException e) {
            Slog.w(TAG, "Error parsing vibration/vibration-select XML", e);
            return null;
        }
    }

    public static ParsedVibration parseElement(TypedXmlPullParser parser, int flags) throws IOException, VibrationXmlParserException {
        try {
            return parseElementInternal(parser, flags);
        } catch (XmlParserException e) {
            throw new VibrationXmlParserException("Error parsing vibration-select.", e);
        }
    }

    public static final class VibrationXmlParserException extends Exception {
        private VibrationXmlParserException(String message, Throwable cause) {
            super(message, cause);
        }

        private VibrationXmlParserException(String message) {
            super(message);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static ParsedVibration parseElementInternal(TypedXmlPullParser parser, int flags) throws IOException, XmlParserException {
        char c;
        XmlValidator.checkStartTag(parser);
        String tagName = parser.getName();
        switch (tagName.hashCode()) {
            case 9931052:
                if (tagName.equals(XmlConstants.TAG_VIBRATION_EFFECT)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 409994391:
                if (tagName.equals(XmlConstants.TAG_VIBRATION_SELECT)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                return new ParsedVibration(parseVibrationEffectInternal(parser, flags));
            case 1:
                return parseVibrationSelectInternal(parser, flags);
            default:
                throw new XmlParserException("Unexpected tag name when parsing element: " + tagName);
        }
    }

    private static ParsedVibration parseVibrationSelectInternal(TypedXmlPullParser parser, int flags) throws IOException, XmlParserException {
        XmlValidator.checkStartTag(parser, XmlConstants.TAG_VIBRATION_SELECT);
        XmlValidator.checkTagHasNoUnexpectedAttributes(parser, new String[0]);
        int rootDepth = parser.getDepth();
        List<VibrationEffect> effects = new ArrayList<>();
        while (XmlReader.readNextTagWithin(parser, rootDepth)) {
            effects.add(parseVibrationEffectInternal(parser, flags));
        }
        return new ParsedVibration(effects);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static VibrationEffect parseVibrationEffectInternal(TypedXmlPullParser parser, int flags) throws IOException, XmlParserException {
        int parserFlags = 0;
        if ((flags & 1) != 0) {
            parserFlags = 0 | 1;
        }
        return VibrationEffectXmlParser.parseTag(parser, parserFlags).deserialize();
    }

    private static <T> T parseDocumentInternal(Reader reader, int flags, ElementParser<T> parseLogic) throws IOException, XmlParserException, XmlPullParserException {
        TypedXmlPullParser parser = Xml.newFastPullParser();
        parser.setFeature("http://xmlpull.org/v1/doc/features.html#process-namespaces", true);
        parser.setInput(reader);
        XmlReader.readDocumentStart(parser);
        T result = parseLogic.parse(parser, flags);
        XmlReader.readDocumentEndTag(parser);
        return result;
    }

    private VibrationXmlParser() {
    }
}
