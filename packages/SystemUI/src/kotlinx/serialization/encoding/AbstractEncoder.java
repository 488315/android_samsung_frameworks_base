package kotlinx.serialization.encoding;

import kotlin.jvm.internal.Reflection;
import kotlinx.serialization.KSerializer;
import kotlinx.serialization.SerializationException;
import kotlinx.serialization.descriptors.SerialDescriptor;
import kotlinx.serialization.modules.SerialModuleImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class AbstractEncoder {
    public void encodeBoolean(boolean z) {
        encodeValue(Boolean.valueOf(z));
    }

    public void encodeByte(byte b) {
        encodeValue(Byte.valueOf(b));
    }

    public void encodeChar(char c) {
        encodeValue(Character.valueOf(c));
    }

    public void encodeDouble(double d) {
        encodeValue(Double.valueOf(d));
    }

    public void encodeFloat(float f) {
        encodeValue(Float.valueOf(f));
    }

    public void encodeInt(int i) {
        encodeValue(Integer.valueOf(i));
    }

    public void encodeLong(long j) {
        encodeValue(Long.valueOf(j));
    }

    public void encodeNull() {
        throw new SerializationException("'null' is not supported by default");
    }

    public final void encodeSerializableElement(SerialDescriptor serialDescriptor, int i, KSerializer kSerializer, Object obj) {
        encodeElement(i);
        encodeSerializableValue(kSerializer, obj);
    }

    public void encodeSerializableValue(KSerializer kSerializer, Object obj) {
        kSerializer.serialize(this, obj);
    }

    public void encodeShort(short s) {
        encodeValue(Short.valueOf(s));
    }

    public void encodeString(String str) {
        encodeValue(str);
    }

    public void encodeValue(Object obj) {
        throw new SerializationException("Non-serializable " + Reflection.getOrCreateKotlinClass(obj.getClass()) + " is not supported by " + Reflection.getOrCreateKotlinClass(getClass()) + " encoder");
    }

    public abstract SerialModuleImpl getSerializersModule();

    public void encodeElement(int i) {
    }
}
