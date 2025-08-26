package kotlinx.serialization.internal;

import kotlin.UByte;
import kotlin.UByteArray;
import kotlin.UInt;
import kotlin.UIntArray;
import kotlin.ULong;
import kotlin.ULongArray;
import kotlin.UShort;
import kotlin.UShortArray;
import kotlin.Unit;
import kotlin.collections.builders.MapBuilder;
import kotlin.jvm.internal.BooleanCompanionObject;
import kotlin.jvm.internal.ByteCompanionObject;
import kotlin.jvm.internal.CharCompanionObject;
import kotlin.jvm.internal.ClassReference;
import kotlin.jvm.internal.DoubleCompanionObject;
import kotlin.jvm.internal.FloatCompanionObject;
import kotlin.jvm.internal.IntCompanionObject;
import kotlin.jvm.internal.LongCompanionObject;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.ShortCompanionObject;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.time.Duration;
import kotlin.uuid.Uuid;

/* loaded from: classes4.dex */
public abstract class PrimitivesKt {
    public static final MapBuilder BUILTIN_SERIALIZERS;

    static {
        MapBuilder mapBuilder = new MapBuilder();
        ClassReference orCreateKotlinClass = Reflection.getOrCreateKotlinClass(String.class);
        int i = StringCompanionObject.$r8$clinit;
        mapBuilder.put(orCreateKotlinClass, StringSerializer.INSTANCE);
        ClassReference orCreateKotlinClass2 = Reflection.getOrCreateKotlinClass(Character.TYPE);
        int i2 = CharCompanionObject.$r8$clinit;
        mapBuilder.put(orCreateKotlinClass2, CharSerializer.INSTANCE);
        mapBuilder.put(Reflection.getOrCreateKotlinClass(char[].class), CharArraySerializer.INSTANCE);
        ClassReference orCreateKotlinClass3 = Reflection.getOrCreateKotlinClass(Double.TYPE);
        int i3 = DoubleCompanionObject.$r8$clinit;
        mapBuilder.put(orCreateKotlinClass3, DoubleSerializer.INSTANCE);
        mapBuilder.put(Reflection.getOrCreateKotlinClass(double[].class), DoubleArraySerializer.INSTANCE);
        ClassReference orCreateKotlinClass4 = Reflection.getOrCreateKotlinClass(Float.TYPE);
        FloatCompanionObject floatCompanionObject = FloatCompanionObject.INSTANCE;
        mapBuilder.put(orCreateKotlinClass4, FloatSerializer.INSTANCE);
        mapBuilder.put(Reflection.getOrCreateKotlinClass(float[].class), FloatArraySerializer.INSTANCE);
        ClassReference orCreateKotlinClass5 = Reflection.getOrCreateKotlinClass(Long.TYPE);
        int i4 = LongCompanionObject.$r8$clinit;
        mapBuilder.put(orCreateKotlinClass5, LongSerializer.INSTANCE);
        mapBuilder.put(Reflection.getOrCreateKotlinClass(long[].class), LongArraySerializer.INSTANCE);
        ClassReference orCreateKotlinClass6 = Reflection.getOrCreateKotlinClass(ULong.class);
        int i5 = ULong.$r8$clinit;
        mapBuilder.put(orCreateKotlinClass6, ULongSerializer.INSTANCE);
        ClassReference orCreateKotlinClass7 = Reflection.getOrCreateKotlinClass(Integer.TYPE);
        int i6 = IntCompanionObject.$r8$clinit;
        mapBuilder.put(orCreateKotlinClass7, IntSerializer.INSTANCE);
        mapBuilder.put(Reflection.getOrCreateKotlinClass(int[].class), IntArraySerializer.INSTANCE);
        ClassReference orCreateKotlinClass8 = Reflection.getOrCreateKotlinClass(UInt.class);
        int i7 = UInt.$r8$clinit;
        mapBuilder.put(orCreateKotlinClass8, UIntSerializer.INSTANCE);
        ClassReference orCreateKotlinClass9 = Reflection.getOrCreateKotlinClass(Short.TYPE);
        int i8 = ShortCompanionObject.$r8$clinit;
        mapBuilder.put(orCreateKotlinClass9, ShortSerializer.INSTANCE);
        mapBuilder.put(Reflection.getOrCreateKotlinClass(short[].class), ShortArraySerializer.INSTANCE);
        ClassReference orCreateKotlinClass10 = Reflection.getOrCreateKotlinClass(UShort.class);
        int i9 = UShort.$r8$clinit;
        mapBuilder.put(orCreateKotlinClass10, UShortSerializer.INSTANCE);
        ClassReference orCreateKotlinClass11 = Reflection.getOrCreateKotlinClass(Byte.TYPE);
        int i10 = ByteCompanionObject.$r8$clinit;
        mapBuilder.put(orCreateKotlinClass11, ByteSerializer.INSTANCE);
        mapBuilder.put(Reflection.getOrCreateKotlinClass(byte[].class), ByteArraySerializer.INSTANCE);
        ClassReference orCreateKotlinClass12 = Reflection.getOrCreateKotlinClass(UByte.class);
        int i11 = UByte.$r8$clinit;
        mapBuilder.put(orCreateKotlinClass12, UByteSerializer.INSTANCE);
        ClassReference orCreateKotlinClass13 = Reflection.getOrCreateKotlinClass(Boolean.TYPE);
        int i12 = BooleanCompanionObject.$r8$clinit;
        mapBuilder.put(orCreateKotlinClass13, BooleanSerializer.INSTANCE);
        mapBuilder.put(Reflection.getOrCreateKotlinClass(boolean[].class), BooleanArraySerializer.INSTANCE);
        ClassReference orCreateKotlinClass14 = Reflection.getOrCreateKotlinClass(Unit.class);
        Unit unit = Unit.INSTANCE;
        mapBuilder.put(orCreateKotlinClass14, UnitSerializer.INSTANCE);
        mapBuilder.put(Reflection.getOrCreateKotlinClass(Void.class), NothingSerializer.INSTANCE);
        try {
            ClassReference orCreateKotlinClass15 = Reflection.getOrCreateKotlinClass(Duration.class);
            Duration.Companion companion = Duration.Companion;
            mapBuilder.put(orCreateKotlinClass15, DurationSerializer.INSTANCE);
        } catch (ClassNotFoundException | NoClassDefFoundError unused) {
        }
        try {
            mapBuilder.put(Reflection.getOrCreateKotlinClass(ULongArray.class), ULongArraySerializer.INSTANCE);
        } catch (ClassNotFoundException | NoClassDefFoundError unused2) {
        }
        try {
            mapBuilder.put(Reflection.getOrCreateKotlinClass(UIntArray.class), UIntArraySerializer.INSTANCE);
        } catch (ClassNotFoundException | NoClassDefFoundError unused3) {
        }
        try {
            mapBuilder.put(Reflection.getOrCreateKotlinClass(UShortArray.class), UShortArraySerializer.INSTANCE);
        } catch (ClassNotFoundException | NoClassDefFoundError unused4) {
        }
        try {
            mapBuilder.put(Reflection.getOrCreateKotlinClass(UByteArray.class), UByteArraySerializer.INSTANCE);
        } catch (ClassNotFoundException | NoClassDefFoundError unused5) {
        }
        try {
            ClassReference orCreateKotlinClass16 = Reflection.getOrCreateKotlinClass(Uuid.class);
            Uuid.Companion companion2 = Uuid.Companion;
            mapBuilder.put(orCreateKotlinClass16, UuidSerializer.INSTANCE);
        } catch (ClassNotFoundException | NoClassDefFoundError unused6) {
        }
        BUILTIN_SERIALIZERS = mapBuilder.build();
    }
}
