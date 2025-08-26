package android.hardware.radio.V1_4;

import android.hardware.radio.V1_0.Carrier;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class CarrierRestrictionsWithPriority {
    public ArrayList<Carrier> allowedCarriers = new ArrayList<>();
    public ArrayList<Carrier> excludedCarriers = new ArrayList<>();
    public boolean allowedCarriersPrioritized = false;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != CarrierRestrictionsWithPriority.class) {
            return false;
        }
        CarrierRestrictionsWithPriority carrierRestrictionsWithPriority = (CarrierRestrictionsWithPriority) obj;
        return HidlSupport.deepEquals(this.allowedCarriers, carrierRestrictionsWithPriority.allowedCarriers) && HidlSupport.deepEquals(this.excludedCarriers, carrierRestrictionsWithPriority.excludedCarriers) && this.allowedCarriersPrioritized == carrierRestrictionsWithPriority.allowedCarriersPrioritized;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.allowedCarriers)), Integer.valueOf(HidlSupport.deepHashCode(this.excludedCarriers)), Integer.valueOf(HidlSupport.deepHashCode(Boolean.valueOf(this.allowedCarriersPrioritized))));
    }

    public final String toString() {
        return "{.allowedCarriers = " + this.allowedCarriers + ", .excludedCarriers = " + this.excludedCarriers + ", .allowedCarriersPrioritized = " + this.allowedCarriersPrioritized + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(40L), 0L);
    }

    public static final ArrayList<CarrierRestrictionsWithPriority> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CarrierRestrictionsWithPriority> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 40, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            CarrierRestrictionsWithPriority carrierRestrictionsWithPriority = new CarrierRestrictionsWithPriority();
            carrierRestrictionsWithPriority.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 40);
            arrayList.add(carrierRestrictionsWithPriority);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        int int32 = hwBlob.getInt32(8 + j);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 56, hwBlob.handle(), j, true);
        this.allowedCarriers.clear();
        for (int i = 0; i < int32; i++) {
            Carrier carrier = new Carrier();
            carrier.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 56);
            this.allowedCarriers.add(carrier);
        }
        int int322 = hwBlob.getInt32(24 + j);
        HwBlob embeddedBuffer2 = hwParcel.readEmbeddedBuffer(int322 * 56, hwBlob.handle(), j + 16, true);
        this.excludedCarriers.clear();
        for (int i2 = 0; i2 < int322; i2++) {
            Carrier carrier2 = new Carrier();
            carrier2.readEmbeddedFromParcel(hwParcel, embeddedBuffer2, i2 * 56);
            this.excludedCarriers.add(carrier2);
        }
        this.allowedCarriersPrioritized = hwBlob.getBool(j + 32);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(40);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CarrierRestrictionsWithPriority> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 40);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 40);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        int size = this.allowedCarriers.size();
        hwBlob.putInt32(8 + j, size);
        hwBlob.putBool(12 + j, false);
        HwBlob hwBlob2 = new HwBlob(size * 56);
        for (int i = 0; i < size; i++) {
            this.allowedCarriers.get(i).writeEmbeddedToBlob(hwBlob2, i * 56);
        }
        hwBlob.putBlob(j, hwBlob2);
        int size2 = this.excludedCarriers.size();
        long j2 = 16 + j;
        hwBlob.putInt32(24 + j, size2);
        hwBlob.putBool(28 + j, false);
        HwBlob hwBlob3 = new HwBlob(size2 * 56);
        for (int i2 = 0; i2 < size2; i2++) {
            this.excludedCarriers.get(i2).writeEmbeddedToBlob(hwBlob3, i2 * 56);
        }
        hwBlob.putBlob(j2, hwBlob3);
        hwBlob.putBool(j + 32, this.allowedCarriersPrioritized);
    }
}
