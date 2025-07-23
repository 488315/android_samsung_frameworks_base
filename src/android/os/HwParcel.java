package android.os;

import android.annotation.SystemApi;
import dalvik.annotation.optimization.FastNative;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import libcore.util.NativeAllocationRegistry;

@SystemApi
/* loaded from: classes3.dex */
public class HwParcel {
    public static final int STATUS_SUCCESS = 0;
    private static final String TAG = "HwParcel";
    private static final NativeAllocationRegistry sNativeRegistry = new NativeAllocationRegistry(HwParcel.class.getClassLoader(), native_init(), 128);
    private long mNativeContext;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Status {
    }

    private static final native long native_init();

    @FastNative
    private final native void native_setup(boolean z);

    @FastNative
    private final native boolean[] readBoolVectorAsArray();

    @FastNative
    private final native double[] readDoubleVectorAsArray();

    @FastNative
    private final native float[] readFloatVectorAsArray();

    @FastNative
    private final native short[] readInt16VectorAsArray();

    @FastNative
    private final native int[] readInt32VectorAsArray();

    @FastNative
    private final native long[] readInt64VectorAsArray();

    @FastNative
    private final native byte[] readInt8VectorAsArray();

    @FastNative
    private final native NativeHandle[] readNativeHandleAsArray();

    @FastNative
    private final native String[] readStringVectorAsArray();

    @FastNative
    private final native void writeBoolVector(boolean[] zArr);

    @FastNative
    private final native void writeDoubleVector(double[] dArr);

    @FastNative
    private final native void writeFloatVector(float[] fArr);

    @FastNative
    private final native void writeInt16Vector(short[] sArr);

    @FastNative
    private final native void writeInt32Vector(int[] iArr);

    @FastNative
    private final native void writeInt64Vector(long[] jArr);

    @FastNative
    private final native void writeInt8Vector(byte[] bArr);

    @FastNative
    private final native void writeNativeHandleVector(NativeHandle[] nativeHandleArr);

    @FastNative
    private final native void writeStringVector(String[] strArr);

    public final native void enforceInterface(String str);

    @FastNative
    public final native boolean readBool();

    @FastNative
    public final native HwBlob readBuffer(long j);

    @FastNative
    public final native double readDouble();

    @FastNative
    public final native HwBlob readEmbeddedBuffer(long j, long j2, long j3, boolean z);

    @FastNative
    public final native HidlMemory readEmbeddedHidlMemory(long j, long j2, long j3);

    @FastNative
    public final native NativeHandle readEmbeddedNativeHandle(long j, long j2);

    @FastNative
    public final native float readFloat();

    @FastNative
    public final native HidlMemory readHidlMemory();

    @FastNative
    public final native short readInt16();

    @FastNative
    public final native int readInt32();

    @FastNative
    public final native long readInt64();

    @FastNative
    public final native byte readInt8();

    @FastNative
    public final native NativeHandle readNativeHandle();

    @FastNative
    public final native String readString();

    @FastNative
    public final native IHwBinder readStrongBinder();

    @FastNative
    public final native void release();

    @FastNative
    public final native void releaseTemporaryStorage();

    public final native void send();

    @FastNative
    public final native void verifySuccess();

    @FastNative
    public final native void writeBool(boolean z);

    @FastNative
    public final native void writeBuffer(HwBlob hwBlob);

    @FastNative
    public final native void writeDouble(double d);

    @FastNative
    public final native void writeFloat(float f);

    @FastNative
    public final native void writeHidlMemory(HidlMemory hidlMemory);

    @FastNative
    public final native void writeInt16(short s);

    @FastNative
    public final native void writeInt32(int i);

    @FastNative
    public final native void writeInt64(long j);

    @FastNative
    public final native void writeInt8(byte b);

    @FastNative
    public final native void writeInterfaceToken(String str);

    @FastNative
    public final native void writeNativeHandle(NativeHandle nativeHandle);

    @FastNative
    public final native void writeStatus(int i);

    @FastNative
    public final native void writeString(String str);

    @FastNative
    public final native void writeStrongBinder(IHwBinder iHwBinder);

    private HwParcel(boolean z) {
        native_setup(z);
        sNativeRegistry.registerNativeAllocation(this, this.mNativeContext);
    }

    public HwParcel() {
        native_setup(true);
        sNativeRegistry.registerNativeAllocation(this, this.mNativeContext);
    }

    public final void writeBoolVector(ArrayList<Boolean> arrayList) {
        int size = arrayList.size();
        boolean[] zArr = new boolean[size];
        for (int i = 0; i < size; i++) {
            zArr[i] = arrayList.get(i).booleanValue();
        }
        writeBoolVector(zArr);
    }

    public final void writeInt8Vector(ArrayList<Byte> arrayList) {
        int size = arrayList.size();
        byte[] bArr = new byte[size];
        for (int i = 0; i < size; i++) {
            bArr[i] = arrayList.get(i).byteValue();
        }
        writeInt8Vector(bArr);
    }

    public final void writeInt16Vector(ArrayList<Short> arrayList) {
        int size = arrayList.size();
        short[] sArr = new short[size];
        for (int i = 0; i < size; i++) {
            sArr[i] = arrayList.get(i).shortValue();
        }
        writeInt16Vector(sArr);
    }

    public final void writeInt32Vector(ArrayList<Integer> arrayList) {
        int size = arrayList.size();
        int[] iArr = new int[size];
        for (int i = 0; i < size; i++) {
            iArr[i] = arrayList.get(i).intValue();
        }
        writeInt32Vector(iArr);
    }

    public final void writeInt64Vector(ArrayList<Long> arrayList) {
        int size = arrayList.size();
        long[] jArr = new long[size];
        for (int i = 0; i < size; i++) {
            jArr[i] = arrayList.get(i).longValue();
        }
        writeInt64Vector(jArr);
    }

    public final void writeFloatVector(ArrayList<Float> arrayList) {
        int size = arrayList.size();
        float[] fArr = new float[size];
        for (int i = 0; i < size; i++) {
            fArr[i] = arrayList.get(i).floatValue();
        }
        writeFloatVector(fArr);
    }

    public final void writeDoubleVector(ArrayList<Double> arrayList) {
        int size = arrayList.size();
        double[] dArr = new double[size];
        for (int i = 0; i < size; i++) {
            dArr[i] = arrayList.get(i).doubleValue();
        }
        writeDoubleVector(dArr);
    }

    public final void writeStringVector(ArrayList<String> arrayList) {
        writeStringVector((String[]) arrayList.toArray(new String[arrayList.size()]));
    }

    public final void writeNativeHandleVector(ArrayList<NativeHandle> arrayList) {
        writeNativeHandleVector((NativeHandle[]) arrayList.toArray(new NativeHandle[arrayList.size()]));
    }

    public final ArrayList<Boolean> readBoolVector() {
        return new ArrayList<>(Arrays.asList(HwBlob.wrapArray(readBoolVectorAsArray())));
    }

    public final ArrayList<Byte> readInt8Vector() {
        return new ArrayList<>(Arrays.asList(HwBlob.wrapArray(readInt8VectorAsArray())));
    }

    public final ArrayList<Short> readInt16Vector() {
        return new ArrayList<>(Arrays.asList(HwBlob.wrapArray(readInt16VectorAsArray())));
    }

    public final ArrayList<Integer> readInt32Vector() {
        return new ArrayList<>(Arrays.asList(HwBlob.wrapArray(readInt32VectorAsArray())));
    }

    public final ArrayList<Long> readInt64Vector() {
        return new ArrayList<>(Arrays.asList(HwBlob.wrapArray(readInt64VectorAsArray())));
    }

    public final ArrayList<Float> readFloatVector() {
        return new ArrayList<>(Arrays.asList(HwBlob.wrapArray(readFloatVectorAsArray())));
    }

    public final ArrayList<Double> readDoubleVector() {
        return new ArrayList<>(Arrays.asList(HwBlob.wrapArray(readDoubleVectorAsArray())));
    }

    public final ArrayList<String> readStringVector() {
        return new ArrayList<>(Arrays.asList(readStringVectorAsArray()));
    }

    public final ArrayList<NativeHandle> readNativeHandleVector() {
        return new ArrayList<>(Arrays.asList(readNativeHandleAsArray()));
    }
}
