package android.os.vibrator.persistence;

import android.os.VibrationEffect;
import android.util.Xml;
import com.android.internal.vibrator.persistence.VibrationEffectXmlSerializer;
import com.android.internal.vibrator.persistence.XmlSerializedVibration;
import com.android.internal.vibrator.persistence.XmlSerializerException;
import com.android.internal.vibrator.persistence.XmlValidator;
import com.android.modules.utils.TypedXmlSerializer;
import java.io.IOException;
import java.io.Writer;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes3.dex */
public final class VibrationXmlSerializer {
    public static final int FLAG_ALLOW_HIDDEN_APIS = 1;
    public static final int FLAG_PRETTY_PRINT = 2;
    private static final String XML_ENCODING = Xml.Encoding.UTF_8.name();
    private static final String XML_FEATURE_INDENT_OUTPUT = "http://xmlpull.org/v1/doc/features.html#indent-output";

    @Retention(RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    public static void serialize(VibrationEffect effect, Writer writer) throws SerializationFailedException, IOException {
        serialize(effect, writer, 0);
    }

    public static void serialize(VibrationEffect effect, Writer writer, int flags) throws SerializationFailedException, IOException {
        XmlSerializedVibration<VibrationEffect> serializedVibration = toSerializedVibration(effect, flags);
        TypedXmlSerializer xmlSerializer = Xml.newFastSerializer();
        xmlSerializer.setFeature(XML_FEATURE_INDENT_OUTPUT, (flags & 2) != 0);
        xmlSerializer.setOutput(writer);
        xmlSerializer.startDocument(XML_ENCODING, false);
        serializedVibration.write(xmlSerializer);
        xmlSerializer.endDocument();
    }

    private static XmlSerializedVibration<VibrationEffect> toSerializedVibration(VibrationEffect effect, int flags) throws SerializationFailedException {
        int serializerFlags = 0;
        if ((flags & 1) != 0) {
            serializerFlags = 0 | 1;
        }
        try {
            XmlSerializedVibration<VibrationEffect> serializedVibration = VibrationEffectXmlSerializer.serialize(effect, serializerFlags);
            XmlValidator.checkSerializedVibration(serializedVibration, effect);
            return serializedVibration;
        } catch (XmlSerializerException e) {
            throw new SerializationFailedException(effect, e);
        }
    }

    public static final class SerializationFailedException extends RuntimeException {
        SerializationFailedException(VibrationEffect effect, Throwable cause) {
            super("Serialization failed for vibration effect " + effect, cause);
        }
    }

    private VibrationXmlSerializer() {
    }
}
