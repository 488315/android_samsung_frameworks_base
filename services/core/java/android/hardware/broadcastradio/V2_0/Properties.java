package android.hardware.broadcastradio.V2_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;

import java.util.ArrayList;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class Properties {
    public String maker = new String();
    public String product = new String();
    public String version = new String();
    public String serial = new String();
    public final ArrayList supportedIdentifierTypes = new ArrayList();
    public final ArrayList vendorInfo = new ArrayList();

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != Properties.class) {
            return false;
        }
        Properties properties = (Properties) obj;
        return HidlSupport.deepEquals(this.maker, properties.maker)
                && HidlSupport.deepEquals(this.product, properties.product)
                && HidlSupport.deepEquals(this.version, properties.version)
                && HidlSupport.deepEquals(this.serial, properties.serial)
                && HidlSupport.deepEquals(
                        this.supportedIdentifierTypes, properties.supportedIdentifierTypes)
                && HidlSupport.deepEquals(this.vendorInfo, properties.vendorInfo);
    }

    public final int hashCode() {
        return Objects.hash(
                Integer.valueOf(HidlSupport.deepHashCode(this.maker)),
                Integer.valueOf(HidlSupport.deepHashCode(this.product)),
                Integer.valueOf(HidlSupport.deepHashCode(this.version)),
                Integer.valueOf(HidlSupport.deepHashCode(this.serial)),
                Integer.valueOf(HidlSupport.deepHashCode(this.supportedIdentifierTypes)),
                Integer.valueOf(HidlSupport.deepHashCode(this.vendorInfo)));
    }

    public final void readFromParcel(HwParcel hwParcel) {
        HwBlob readBuffer = hwParcel.readBuffer(96L);
        this.maker = readBuffer.getString(0L);
        hwParcel.readEmbeddedBuffer(r0.getBytes().length + 1, readBuffer.handle(), 0L, false);
        this.product = readBuffer.getString(16L);
        hwParcel.readEmbeddedBuffer(r0.getBytes().length + 1, readBuffer.handle(), 16L, false);
        this.version = readBuffer.getString(32L);
        hwParcel.readEmbeddedBuffer(r0.getBytes().length + 1, readBuffer.handle(), 32L, false);
        this.serial = readBuffer.getString(48L);
        hwParcel.readEmbeddedBuffer(r0.getBytes().length + 1, readBuffer.handle(), 48L, false);
        int int32 = readBuffer.getInt32(72L);
        HwBlob readEmbeddedBuffer =
                hwParcel.readEmbeddedBuffer(int32 * 4, readBuffer.handle(), 64L, true);
        this.supportedIdentifierTypes.clear();
        for (int i = 0; i < int32; i++) {
            this.supportedIdentifierTypes.add(Integer.valueOf(readEmbeddedBuffer.getInt32(i * 4)));
        }
        int int322 = readBuffer.getInt32(88L);
        HwBlob readEmbeddedBuffer2 =
                hwParcel.readEmbeddedBuffer(int322 * 32, readBuffer.handle(), 80L, true);
        this.vendorInfo.clear();
        for (int i2 = 0; i2 < int322; i2++) {
            VendorKeyValue vendorKeyValue = new VendorKeyValue();
            vendorKeyValue.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer2, i2 * 32);
            this.vendorInfo.add(vendorKeyValue);
        }
    }

    public final String toString() {
        return "{.maker = "
                + this.maker
                + ", .product = "
                + this.product
                + ", .version = "
                + this.version
                + ", .serial = "
                + this.serial
                + ", .supportedIdentifierTypes = "
                + this.supportedIdentifierTypes
                + ", .vendorInfo = "
                + this.vendorInfo
                + "}";
    }
}
