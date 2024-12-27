package android.hardware.usb;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.util.Slog;

import com.android.internal.hidden_from_bootclasspath.android.hardware.usb.flags.Flags;
import com.android.internal.util.dump.DualDumpOutputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.util.Objects;

public class DeviceFilter {
    private static final String TAG = DeviceFilter.class.getSimpleName();
    public final int mClass;
    public final String mInterfaceName;
    public final String mManufacturerName;
    public final int mProductId;
    public final String mProductName;
    public final int mProtocol;
    public final String mSerialNumber;
    public final int mSubclass;
    public final int mVendorId;

    public DeviceFilter(
            int vid,
            int pid,
            int clasz,
            int subclass,
            int protocol,
            String manufacturer,
            String product,
            String serialnum,
            String interfaceName) {
        this.mVendorId = vid;
        this.mProductId = pid;
        this.mClass = clasz;
        this.mSubclass = subclass;
        this.mProtocol = protocol;
        this.mManufacturerName = manufacturer;
        this.mProductName = product;
        this.mSerialNumber = serialnum;
        this.mInterfaceName = interfaceName;
    }

    public DeviceFilter(UsbDevice device) {
        this.mVendorId = device.getVendorId();
        this.mProductId = device.getProductId();
        this.mClass = device.getDeviceClass();
        this.mSubclass = device.getDeviceSubclass();
        this.mProtocol = device.getDeviceProtocol();
        this.mManufacturerName = device.getManufacturerName();
        this.mProductName = device.getProductName();
        this.mSerialNumber = device.getSerialNumber();
        this.mInterfaceName = null;
    }

    public DeviceFilter(DeviceFilter filter) {
        this.mVendorId = filter.mVendorId;
        this.mProductId = filter.mProductId;
        this.mClass = filter.mClass;
        this.mSubclass = filter.mSubclass;
        this.mProtocol = filter.mProtocol;
        this.mManufacturerName = filter.mManufacturerName;
        this.mProductName = filter.mProductName;
        this.mSerialNumber = filter.mSerialNumber;
        this.mInterfaceName = filter.mInterfaceName;
    }

    public static DeviceFilter read(XmlPullParser parser)
            throws XmlPullParserException, IOException {
        String value;
        int count;
        XmlPullParser xmlPullParser = parser;
        int count2 = parser.getAttributeCount();
        int i = 0;
        String interfaceName = null;
        String interfaceName2 = null;
        String serialNumber = null;
        String manufacturerName = null;
        int deviceProtocol = -1;
        int deviceProtocol2 = -1;
        int deviceSubclass = -1;
        int deviceClass = -1;
        int productId = -1;
        while (i < count2) {
            String name = xmlPullParser.getAttributeName(i);
            String value2 = xmlPullParser.getAttributeValue(i);
            if ("manufacturer-name".equals(name)) {
                manufacturerName = value2;
                count = count2;
            } else if ("product-name".equals(name)) {
                serialNumber = value2;
                count = count2;
            } else if ("serial-number".equals(name)) {
                interfaceName2 = value2;
                count = count2;
            } else if ("interface-name".equals(name)) {
                interfaceName = value2;
                count = count2;
            } else {
                int radix = 10;
                if (value2 != null
                        && value2.length() > 2
                        && value2.charAt(0) == '0'
                        && (value2.charAt(1) == 'x' || value2.charAt(1) == 'X')) {
                    radix = 16;
                    value = value2.substring(2);
                } else {
                    value = value2;
                }
                try {
                    int intValue = Integer.parseInt(value, radix);
                    if ("vendor-id".equals(name)) {
                        productId = intValue;
                        count = count2;
                    } else if ("product-id".equals(name)) {
                        deviceClass = intValue;
                        count = count2;
                    } else if ("class".equals(name)) {
                        deviceSubclass = intValue;
                        count = count2;
                    } else if ("subclass".equals(name)) {
                        deviceProtocol2 = intValue;
                        count = count2;
                    } else if (!"protocol".equals(name)) {
                        count = count2;
                    } else {
                        deviceProtocol = intValue;
                        count = count2;
                    }
                } catch (NumberFormatException e) {
                    count = count2;
                    Slog.e(TAG, "invalid number for field " + name, e);
                }
            }
            i++;
            xmlPullParser = parser;
            count2 = count;
        }
        return new DeviceFilter(
                productId,
                deviceClass,
                deviceSubclass,
                deviceProtocol2,
                deviceProtocol,
                manufacturerName,
                serialNumber,
                interfaceName2,
                interfaceName);
    }

    public void write(XmlSerializer serializer) throws IOException {
        serializer.startTag(null, "usb-device");
        if (this.mVendorId != -1) {
            serializer.attribute(null, "vendor-id", Integer.toString(this.mVendorId));
        }
        if (this.mProductId != -1) {
            serializer.attribute(null, "product-id", Integer.toString(this.mProductId));
        }
        if (this.mClass != -1) {
            serializer.attribute(null, "class", Integer.toString(this.mClass));
        }
        if (this.mSubclass != -1) {
            serializer.attribute(null, "subclass", Integer.toString(this.mSubclass));
        }
        if (this.mProtocol != -1) {
            serializer.attribute(null, "protocol", Integer.toString(this.mProtocol));
        }
        if (this.mManufacturerName != null) {
            serializer.attribute(null, "manufacturer-name", this.mManufacturerName);
        }
        if (this.mProductName != null) {
            serializer.attribute(null, "product-name", this.mProductName);
        }
        if (this.mSerialNumber != null) {
            serializer.attribute(null, "serial-number", this.mSerialNumber);
        }
        if (this.mInterfaceName != null) {
            serializer.attribute(null, "interface-name", this.mInterfaceName);
        }
        serializer.endTag(null, "usb-device");
    }

    private boolean matches(int usbClass, int subclass, int protocol) {
        return (this.mClass == -1 || usbClass == this.mClass)
                && (this.mSubclass == -1 || subclass == this.mSubclass)
                && (this.mProtocol == -1 || protocol == this.mProtocol);
    }

    private boolean matches(int usbClass, int subclass, int protocol, String interfaceName) {
        if (Flags.enableInterfaceNameDeviceFilter()) {
            return matches(usbClass, subclass, protocol)
                    && (this.mInterfaceName == null || this.mInterfaceName.equals(interfaceName));
        }
        return matches(usbClass, subclass, protocol);
    }

    public boolean matches(UsbDevice device) {
        if (this.mVendorId != -1 && device.getVendorId() != this.mVendorId) {
            return false;
        }
        if (this.mProductId != -1 && device.getProductId() != this.mProductId) {
            return false;
        }
        if (this.mManufacturerName != null && device.getManufacturerName() == null) {
            return false;
        }
        if (this.mProductName != null && device.getProductName() == null) {
            return false;
        }
        if (this.mSerialNumber != null && device.getSerialNumber() == null) {
            return false;
        }
        if (this.mManufacturerName != null
                && device.getManufacturerName() != null
                && !this.mManufacturerName.equals(device.getManufacturerName())) {
            return false;
        }
        if (this.mProductName != null
                && device.getProductName() != null
                && !this.mProductName.equals(device.getProductName())) {
            return false;
        }
        if (this.mSerialNumber != null
                && device.getSerialNumber() != null
                && !this.mSerialNumber.equals(device.getSerialNumber())) {
            return false;
        }
        if (matches(
                device.getDeviceClass(), device.getDeviceSubclass(), device.getDeviceProtocol())) {
            return true;
        }
        int count = device.getInterfaceCount();
        int intfNum = 0;
        UsbInterface intfToCheck = null;
        for (int intfNum2 = 0; intfNum2 < count; intfNum2++) {
            try {
                UsbInterface intf = device.getInterface(intfNum2);
                intfToCheck = intf;
                intfNum = intfNum2;
                Slog.d(TAG, "matches Interface intfNum=" + intfNum);
                if (intf != null) {
                    if (matches(
                            intf.getInterfaceClass(),
                            intf.getInterfaceSubclass(),
                            intf.getInterfaceProtocol(),
                            intf.getName())) {
                        return true;
                    }
                } else {
                    Slog.d(TAG, "matches delivered UsbDevice=" + device);
                    Slog.d(TAG, "matches Interface Count=" + count);
                    Slog.d(TAG, "matches interface(" + intfNum + ") -> [null]");
                    throw new NullPointerException("DeviceFilter's matches met interface null");
                }
            } catch (NullPointerException npe) {
                Slog.e(TAG, "matches got NPE ", npe);
                Slog.d(TAG, "matches delivered UsbDevice=" + device);
                Slog.d(TAG, "matches Interface Count=" + count);
                if (intfToCheck != null) {
                    Slog.d(
                            TAG,
                            "matches interface("
                                    + intfNum
                                    + ") -> ["
                                    + intfToCheck.toString()
                                    + NavigationBarInflaterView.SIZE_MOD_END);
                    return false;
                }
                return false;
            } catch (Exception e) {
                Slog.w(TAG, "matches got Exception ", e);
                Slog.d(TAG, "matches delivered UsbDevice=" + device);
                Slog.d(TAG, "matches Interface Count=" + count);
                if (intfToCheck != null) {
                    Slog.d(
                            TAG,
                            "matches interface("
                                    + intfNum
                                    + ") -> ["
                                    + intfToCheck.toString()
                                    + NavigationBarInflaterView.SIZE_MOD_END);
                    return false;
                }
                return false;
            }
        }
        return false;
    }

    public boolean contains(DeviceFilter device) {
        if (this.mVendorId != -1 && device.mVendorId != this.mVendorId) {
            return false;
        }
        if (this.mProductId != -1 && device.mProductId != this.mProductId) {
            return false;
        }
        if (this.mManufacturerName != null
                && !Objects.equals(this.mManufacturerName, device.mManufacturerName)) {
            return false;
        }
        if (this.mProductName != null && !Objects.equals(this.mProductName, device.mProductName)) {
            return false;
        }
        if (this.mSerialNumber == null
                || Objects.equals(this.mSerialNumber, device.mSerialNumber)) {
            return matches(device.mClass, device.mSubclass, device.mProtocol);
        }
        return false;
    }

    public boolean equals(Object obj) {
        if (this.mVendorId == -1
                || this.mProductId == -1
                || this.mClass == -1
                || this.mSubclass == -1
                || this.mProtocol == -1) {
            return false;
        }
        if (obj instanceof DeviceFilter) {
            DeviceFilter filter = (DeviceFilter) obj;
            if (filter.mVendorId != this.mVendorId
                    || filter.mProductId != this.mProductId
                    || filter.mClass != this.mClass
                    || filter.mSubclass != this.mSubclass
                    || filter.mProtocol != this.mProtocol) {
                return false;
            }
            if ((filter.mManufacturerName == null || this.mManufacturerName != null)
                    && ((filter.mManufacturerName != null || this.mManufacturerName == null)
                            && ((filter.mProductName == null || this.mProductName != null)
                                    && ((filter.mProductName != null || this.mProductName == null)
                                            && ((filter.mSerialNumber == null
                                                            || this.mSerialNumber != null)
                                                    && (filter.mSerialNumber != null
                                                            || this.mSerialNumber == null)))))) {
                return (filter.mManufacturerName == null
                                || this.mManufacturerName == null
                                || this.mManufacturerName.equals(filter.mManufacturerName))
                        && (filter.mProductName == null
                                || this.mProductName == null
                                || this.mProductName.equals(filter.mProductName))
                        && (filter.mSerialNumber == null
                                || this.mSerialNumber == null
                                || this.mSerialNumber.equals(filter.mSerialNumber));
            }
            return false;
        }
        if (!(obj instanceof UsbDevice)) {
            return false;
        }
        UsbDevice device = (UsbDevice) obj;
        if (device.getVendorId() != this.mVendorId
                || device.getProductId() != this.mProductId
                || device.getDeviceClass() != this.mClass
                || device.getDeviceSubclass() != this.mSubclass
                || device.getDeviceProtocol() != this.mProtocol) {
            return false;
        }
        if ((this.mManufacturerName == null || device.getManufacturerName() != null)
                && ((this.mManufacturerName != null || device.getManufacturerName() == null)
                        && ((this.mProductName == null || device.getProductName() != null)
                                && ((this.mProductName != null || device.getProductName() == null)
                                        && ((this.mSerialNumber == null
                                                        || device.getSerialNumber() != null)
                                                && (this.mSerialNumber != null
                                                        || device.getSerialNumber() == null)))))) {
            return (device.getManufacturerName() == null
                            || this.mManufacturerName.equals(device.getManufacturerName()))
                    && (device.getProductName() == null
                            || this.mProductName.equals(device.getProductName()))
                    && (device.getSerialNumber() == null
                            || this.mSerialNumber.equals(device.getSerialNumber()));
        }
        return false;
    }

    public int hashCode() {
        return ((this.mVendorId << 16) | this.mProductId)
                ^ (((this.mClass << 16) | (this.mSubclass << 8)) | this.mProtocol);
    }

    public String toString() {
        return "DeviceFilter[mVendorId="
                + this.mVendorId
                + ",mProductId="
                + this.mProductId
                + ",mClass="
                + this.mClass
                + ",mSubclass="
                + this.mSubclass
                + ",mProtocol="
                + this.mProtocol
                + ",mManufacturerName="
                + this.mManufacturerName
                + ",mProductName="
                + this.mProductName
                + ",mSerialNumber="
                + this.mSerialNumber
                + ",mInterfaceName="
                + this.mInterfaceName
                + NavigationBarInflaterView.SIZE_MOD_END;
    }

    public void dump(DualDumpOutputStream dump, String idName, long id) {
        long token = dump.start(idName, id);
        dump.write("vendor_id", 1120986464257L, this.mVendorId);
        dump.write("product_id", 1120986464258L, this.mProductId);
        dump.write("class", 1120986464259L, this.mClass);
        dump.write("subclass", 1120986464260L, this.mSubclass);
        dump.write("protocol", 1120986464261L, this.mProtocol);
        dump.write("manufacturer_name", 1138166333446L, this.mManufacturerName);
        dump.write("product_name", 1138166333447L, this.mProductName);
        dump.write("serial_number", 1138166333448L, this.mSerialNumber);
        dump.end(token);
    }
}
