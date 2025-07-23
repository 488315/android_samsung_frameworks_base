package android.hardware.radio.V1_1;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class RadioAccessSpecifier {
    public int radioAccessNetwork = 0;
    public ArrayList<Integer> geranBands = new ArrayList<>();
    public ArrayList<Integer> utranBands = new ArrayList<>();
    public ArrayList<Integer> eutranBands = new ArrayList<>();
    public ArrayList<Integer> channels = new ArrayList<>();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != RadioAccessSpecifier.class) {
            return false;
        }
        RadioAccessSpecifier radioAccessSpecifier = (RadioAccessSpecifier) obj;
        return this.radioAccessNetwork == radioAccessSpecifier.radioAccessNetwork && HidlSupport.deepEquals(this.geranBands, radioAccessSpecifier.geranBands) && HidlSupport.deepEquals(this.utranBands, radioAccessSpecifier.utranBands) && HidlSupport.deepEquals(this.eutranBands, radioAccessSpecifier.eutranBands) && HidlSupport.deepEquals(this.channels, radioAccessSpecifier.channels);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.radioAccessNetwork))), Integer.valueOf(HidlSupport.deepHashCode(this.geranBands)), Integer.valueOf(HidlSupport.deepHashCode(this.utranBands)), Integer.valueOf(HidlSupport.deepHashCode(this.eutranBands)), Integer.valueOf(HidlSupport.deepHashCode(this.channels)));
    }

    public final String toString() {
        return "{.radioAccessNetwork = " + RadioAccessNetworks.toString(this.radioAccessNetwork) + ", .geranBands = " + this.geranBands + ", .utranBands = " + this.utranBands + ", .eutranBands = " + this.eutranBands + ", .channels = " + this.channels + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(72L), 0L);
    }

    public static final ArrayList<RadioAccessSpecifier> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<RadioAccessSpecifier> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 72, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            RadioAccessSpecifier radioAccessSpecifier = new RadioAccessSpecifier();
            radioAccessSpecifier.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 72);
            arrayList.add(radioAccessSpecifier);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.radioAccessNetwork = hwBlob.getInt32(j);
        int int32 = hwBlob.getInt32(16 + j);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 4, hwBlob.handle(), j + 8, true);
        this.geranBands.clear();
        for (int i = 0; i < int32; i++) {
            this.geranBands.add(Integer.valueOf(readEmbeddedBuffer.getInt32(i * 4)));
        }
        int int322 = hwBlob.getInt32(32 + j);
        HwBlob readEmbeddedBuffer2 = hwParcel.readEmbeddedBuffer(int322 * 4, hwBlob.handle(), j + 24, true);
        this.utranBands.clear();
        for (int i2 = 0; i2 < int322; i2++) {
            this.utranBands.add(Integer.valueOf(readEmbeddedBuffer2.getInt32(i2 * 4)));
        }
        int int323 = hwBlob.getInt32(48 + j);
        HwBlob readEmbeddedBuffer3 = hwParcel.readEmbeddedBuffer(int323 * 4, hwBlob.handle(), j + 40, true);
        this.eutranBands.clear();
        for (int i3 = 0; i3 < int323; i3++) {
            this.eutranBands.add(Integer.valueOf(readEmbeddedBuffer3.getInt32(i3 * 4)));
        }
        int int324 = hwBlob.getInt32(j + 64);
        HwBlob readEmbeddedBuffer4 = hwParcel.readEmbeddedBuffer(int324 * 4, hwBlob.handle(), j + 56, true);
        this.channels.clear();
        for (int i4 = 0; i4 < int324; i4++) {
            this.channels.add(Integer.valueOf(readEmbeddedBuffer4.getInt32(i4 * 4)));
        }
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(72);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<RadioAccessSpecifier> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 72);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 72);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        hwBlob.putInt32(j, this.radioAccessNetwork);
        int size = this.geranBands.size();
        long j2 = 8 + j;
        hwBlob.putInt32(16 + j, size);
        hwBlob.putBool(20 + j, false);
        HwBlob hwBlob2 = new HwBlob(size * 4);
        for (int i = 0; i < size; i++) {
            hwBlob2.putInt32(i * 4, this.geranBands.get(i).intValue());
        }
        hwBlob.putBlob(j2, hwBlob2);
        int size2 = this.utranBands.size();
        long j3 = 24 + j;
        hwBlob.putInt32(32 + j, size2);
        hwBlob.putBool(36 + j, false);
        HwBlob hwBlob3 = new HwBlob(size2 * 4);
        for (int i2 = 0; i2 < size2; i2++) {
            hwBlob3.putInt32(i2 * 4, this.utranBands.get(i2).intValue());
        }
        hwBlob.putBlob(j3, hwBlob3);
        int size3 = this.eutranBands.size();
        long j4 = 40 + j;
        hwBlob.putInt32(48 + j, size3);
        hwBlob.putBool(52 + j, false);
        HwBlob hwBlob4 = new HwBlob(size3 * 4);
        for (int i3 = 0; i3 < size3; i3++) {
            hwBlob4.putInt32(i3 * 4, this.eutranBands.get(i3).intValue());
        }
        hwBlob.putBlob(j4, hwBlob4);
        int size4 = this.channels.size();
        long j5 = 56 + j;
        hwBlob.putInt32(64 + j, size4);
        hwBlob.putBool(j + 68, false);
        HwBlob hwBlob5 = new HwBlob(size4 * 4);
        for (int i4 = 0; i4 < size4; i4++) {
            hwBlob5.putInt32(i4 * 4, this.channels.get(i4).intValue());
        }
        hwBlob.putBlob(j5, hwBlob5);
    }
}
