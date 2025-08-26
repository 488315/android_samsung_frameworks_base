package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class ImsSmsMessage {
    public int tech = 0;
    public boolean retry = false;
    public int messageRef = 0;
    public ArrayList<CdmaSmsMessage> cdmaMessage = new ArrayList<>();
    public ArrayList<GsmSmsMessage> gsmMessage = new ArrayList<>();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != ImsSmsMessage.class) {
            return false;
        }
        ImsSmsMessage imsSmsMessage = (ImsSmsMessage) obj;
        return this.tech == imsSmsMessage.tech && this.retry == imsSmsMessage.retry && this.messageRef == imsSmsMessage.messageRef && HidlSupport.deepEquals(this.cdmaMessage, imsSmsMessage.cdmaMessage) && HidlSupport.deepEquals(this.gsmMessage, imsSmsMessage.gsmMessage);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.tech))), Integer.valueOf(HidlSupport.deepHashCode(Boolean.valueOf(this.retry))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.messageRef))), Integer.valueOf(HidlSupport.deepHashCode(this.cdmaMessage)), Integer.valueOf(HidlSupport.deepHashCode(this.gsmMessage)));
    }

    public final String toString() {
        return "{.tech = " + RadioTechnologyFamily.toString(this.tech) + ", .retry = " + this.retry + ", .messageRef = " + this.messageRef + ", .cdmaMessage = " + this.cdmaMessage + ", .gsmMessage = " + this.gsmMessage + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(48L), 0L);
    }

    public static final ArrayList<ImsSmsMessage> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<ImsSmsMessage> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 48, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            ImsSmsMessage imsSmsMessage = new ImsSmsMessage();
            imsSmsMessage.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 48);
            arrayList.add(imsSmsMessage);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.tech = hwBlob.getInt32(j);
        this.retry = hwBlob.getBool(4 + j);
        this.messageRef = hwBlob.getInt32(8 + j);
        int int32 = hwBlob.getInt32(24 + j);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 88, hwBlob.handle(), j + 16, true);
        this.cdmaMessage.clear();
        for (int i = 0; i < int32; i++) {
            CdmaSmsMessage cdmaSmsMessage = new CdmaSmsMessage();
            cdmaSmsMessage.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 88);
            this.cdmaMessage.add(cdmaSmsMessage);
        }
        int int322 = hwBlob.getInt32(j + 40);
        HwBlob embeddedBuffer2 = hwParcel.readEmbeddedBuffer(int322 * 32, hwBlob.handle(), j + 32, true);
        this.gsmMessage.clear();
        for (int i2 = 0; i2 < int322; i2++) {
            GsmSmsMessage gsmSmsMessage = new GsmSmsMessage();
            gsmSmsMessage.readEmbeddedFromParcel(hwParcel, embeddedBuffer2, i2 * 32);
            this.gsmMessage.add(gsmSmsMessage);
        }
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(48);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<ImsSmsMessage> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 48);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 48);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        hwBlob.putInt32(j, this.tech);
        hwBlob.putBool(4 + j, this.retry);
        hwBlob.putInt32(8 + j, this.messageRef);
        int size = this.cdmaMessage.size();
        long j2 = 16 + j;
        hwBlob.putInt32(24 + j, size);
        hwBlob.putBool(28 + j, false);
        HwBlob hwBlob2 = new HwBlob(size * 88);
        for (int i = 0; i < size; i++) {
            this.cdmaMessage.get(i).writeEmbeddedToBlob(hwBlob2, i * 88);
        }
        hwBlob.putBlob(j2, hwBlob2);
        int size2 = this.gsmMessage.size();
        long j3 = 32 + j;
        hwBlob.putInt32(40 + j, size2);
        hwBlob.putBool(j + 44, false);
        HwBlob hwBlob3 = new HwBlob(size2 * 32);
        for (int i2 = 0; i2 < size2; i2++) {
            this.gsmMessage.get(i2).writeEmbeddedToBlob(hwBlob3, i2 * 32);
        }
        hwBlob.putBlob(j3, hwBlob3);
    }
}
