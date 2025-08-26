package android.os.vibrator.persistence;

import android.os.VibrationEffect;
import android.util.Xml;
import com.android.internal.vibrator.persistence.LegacyVibrationEffectXmlSerializer;
import com.android.internal.vibrator.persistence.VibrationEffectSerializer;
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

    public static void serialize(VibrationEffect vibrationEffect, Writer writer) throws IOException {
        serialize(vibrationEffect, writer, 0);
    }

    public static void serialize(VibrationEffect vibrationEffect, Writer writer, int i) throws IOException {
        XmlSerializedVibration<? extends VibrationEffect> serializedVibration = toSerializedVibration(vibrationEffect, i);
        TypedXmlSerializer typedXmlSerializerNewFastSerializer = Xml.newFastSerializer();
        typedXmlSerializerNewFastSerializer.setFeature(XML_FEATURE_INDENT_OUTPUT, (i & 2) != 0);
        typedXmlSerializerNewFastSerializer.setOutput(writer);
        typedXmlSerializerNewFastSerializer.startDocument(XML_ENCODING, false);
        serializedVibration.write(typedXmlSerializerNewFastSerializer);
        typedXmlSerializerNewFastSerializer.endDocument();
    }

    private static XmlSerializedVibration<? extends VibrationEffect> toSerializedVibration(VibrationEffect vibrationEffect, int i) throws SerializationFailedException {
        XmlSerializedVibration<? extends VibrationEffect> xmlSerializedVibrationSerialize;
        int i2 = (i & 1) == 0 ? 0 : 1;
        try {
            if (android.os.vibrator.Flags.normalizedPwleEffects()) {
                xmlSerializedVibrationSerialize = VibrationEffectSerializer.serialize(vibrationEffect, i2);
            } else {
                xmlSerializedVibrationSerialize = LegacyVibrationEffectXmlSerializer.serialize(vibrationEffect, i2);
            }
            XmlValidator.checkSerializedVibration(xmlSerializedVibrationSerialize, vibrationEffect);
            return xmlSerializedVibrationSerialize;
        } catch (XmlSerializerException e) {
            throw new SerializationFailedException(vibrationEffect, e);
        }
    }

    public static final class SerializationFailedException extends IOException {
        private SerializationFailedException(VibrationEffect vibrationEffect, Throwable th) {
            super("Serialization failed for vibration effect " + vibrationEffect, th);
        }
    }

    private VibrationXmlSerializer() {
    }
}
