package android.hardware.usb;

import android.media.midi.MidiDeviceInfo;
import com.android.internal.util.dump.DualDumpOutputStream;
import java.io.IOException;
import java.util.Objects;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* loaded from: classes2.dex */
public class AccessoryFilter {
    public final String mManufacturer;
    public final String mModel;
    public final String mVersion;

    public AccessoryFilter(String str, String str2, String str3) {
        this.mManufacturer = str;
        this.mModel = str2;
        this.mVersion = str3;
    }

    public AccessoryFilter(UsbAccessory usbAccessory) {
        this.mManufacturer = usbAccessory.getManufacturer();
        this.mModel = usbAccessory.getModel();
        this.mVersion = usbAccessory.getVersion();
    }

    public AccessoryFilter(AccessoryFilter accessoryFilter) {
        this.mManufacturer = accessoryFilter.mManufacturer;
        this.mModel = accessoryFilter.mModel;
        this.mVersion = accessoryFilter.mVersion;
    }

    public static AccessoryFilter read(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        int attributeCount = xmlPullParser.getAttributeCount();
        String str = null;
        String str2 = null;
        String str3 = null;
        for (int i = 0; i < attributeCount; i++) {
            String attributeName = xmlPullParser.getAttributeName(i);
            String attributeValue = xmlPullParser.getAttributeValue(i);
            if (MidiDeviceInfo.PROPERTY_MANUFACTURER.equals(attributeName)) {
                str = attributeValue;
            } else if ("model".equals(attributeName)) {
                str3 = attributeValue;
            } else if ("version".equals(attributeName)) {
                str2 = attributeValue;
            }
        }
        return new AccessoryFilter(str, str3, str2);
    }

    public void write(XmlSerializer xmlSerializer) throws IllegalStateException, IOException, IllegalArgumentException {
        xmlSerializer.startTag(null, "usb-accessory");
        String str = this.mManufacturer;
        if (str != null) {
            xmlSerializer.attribute(null, MidiDeviceInfo.PROPERTY_MANUFACTURER, str);
        }
        String str2 = this.mModel;
        if (str2 != null) {
            xmlSerializer.attribute(null, "model", str2);
        }
        String str3 = this.mVersion;
        if (str3 != null) {
            xmlSerializer.attribute(null, "version", str3);
        }
        xmlSerializer.endTag(null, "usb-accessory");
    }

    public boolean matches(UsbAccessory usbAccessory) {
        if (this.mManufacturer != null && !usbAccessory.getManufacturer().equals(this.mManufacturer)) {
            return false;
        }
        if (this.mModel == null || usbAccessory.getModel().equals(this.mModel)) {
            return this.mVersion == null || Objects.equals(usbAccessory.getVersion(), this.mVersion);
        }
        return false;
    }

    public boolean contains(AccessoryFilter accessoryFilter) {
        String str = this.mManufacturer;
        if (str != null && !Objects.equals(accessoryFilter.mManufacturer, str)) {
            return false;
        }
        String str2 = this.mModel;
        if (str2 != null && !Objects.equals(accessoryFilter.mModel, str2)) {
            return false;
        }
        String str3 = this.mVersion;
        return str3 == null || Objects.equals(accessoryFilter.mVersion, str3);
    }

    public boolean equals(Object obj) {
        String str = this.mManufacturer;
        if (str != null && this.mModel != null && this.mVersion != null) {
            if (obj instanceof AccessoryFilter) {
                AccessoryFilter accessoryFilter = (AccessoryFilter) obj;
                return str.equals(accessoryFilter.mManufacturer) && this.mModel.equals(accessoryFilter.mModel) && this.mVersion.equals(accessoryFilter.mVersion);
            }
            if (obj instanceof UsbAccessory) {
                UsbAccessory usbAccessory = (UsbAccessory) obj;
                if (str.equals(usbAccessory.getManufacturer()) && this.mModel.equals(usbAccessory.getModel()) && this.mVersion.equals(usbAccessory.getVersion())) {
                    return true;
                }
            }
        }
        return false;
    }

    public int hashCode() {
        String str = this.mManufacturer;
        int iHashCode = str == null ? 0 : str.hashCode();
        String str2 = this.mModel;
        int iHashCode2 = iHashCode ^ (str2 == null ? 0 : str2.hashCode());
        String str3 = this.mVersion;
        return iHashCode2 ^ (str3 != null ? str3.hashCode() : 0);
    }

    public String toString() {
        return "AccessoryFilter[mManufacturer=\"" + this.mManufacturer + "\", mModel=\"" + this.mModel + "\", mVersion=\"" + this.mVersion + "\"]";
    }

    public void dump(DualDumpOutputStream dualDumpOutputStream, String str, long j) {
        long jStart = dualDumpOutputStream.start(str, j);
        dualDumpOutputStream.write(MidiDeviceInfo.PROPERTY_MANUFACTURER, 1138166333441L, this.mManufacturer);
        dualDumpOutputStream.write("model", 1138166333442L, this.mModel);
        dualDumpOutputStream.write("version", 1138166333443L, this.mVersion);
        dualDumpOutputStream.end(jStart);
    }
}
