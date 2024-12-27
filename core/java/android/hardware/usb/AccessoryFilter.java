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

    public AccessoryFilter(String manufacturer, String model, String version) {
        this.mManufacturer = manufacturer;
        this.mModel = model;
        this.mVersion = version;
    }

    public AccessoryFilter(UsbAccessory accessory) {
        this.mManufacturer = accessory.getManufacturer();
        this.mModel = accessory.getModel();
        this.mVersion = accessory.getVersion();
    }

    public AccessoryFilter(AccessoryFilter filter) {
        this.mManufacturer = filter.mManufacturer;
        this.mModel = filter.mModel;
        this.mVersion = filter.mVersion;
    }

    public static AccessoryFilter read(XmlPullParser parser) throws XmlPullParserException, IOException {
        String manufacturer = null;
        String model = null;
        String version = null;
        int count = parser.getAttributeCount();
        for (int i = 0; i < count; i++) {
            String name = parser.getAttributeName(i);
            String value = parser.getAttributeValue(i);
            if (MidiDeviceInfo.PROPERTY_MANUFACTURER.equals(name)) {
                manufacturer = value;
            } else if ("model".equals(name)) {
                model = value;
            } else if ("version".equals(name)) {
                version = value;
            }
        }
        return new AccessoryFilter(manufacturer, model, version);
    }

    public void write(XmlSerializer serializer) throws IOException {
        serializer.startTag(null, "usb-accessory");
        if (this.mManufacturer != null) {
            serializer.attribute(null, MidiDeviceInfo.PROPERTY_MANUFACTURER, this.mManufacturer);
        }
        if (this.mModel != null) {
            serializer.attribute(null, "model", this.mModel);
        }
        if (this.mVersion != null) {
            serializer.attribute(null, "version", this.mVersion);
        }
        serializer.endTag(null, "usb-accessory");
    }

    public boolean matches(UsbAccessory acc) {
        if (this.mManufacturer != null && !acc.getManufacturer().equals(this.mManufacturer)) {
            return false;
        }
        if (this.mModel == null || acc.getModel().equals(this.mModel)) {
            return this.mVersion == null || Objects.equals(acc.getVersion(), this.mVersion);
        }
        return false;
    }

    public boolean contains(AccessoryFilter accessory) {
        if (this.mManufacturer != null && !Objects.equals(accessory.mManufacturer, this.mManufacturer)) {
            return false;
        }
        if (this.mModel == null || Objects.equals(accessory.mModel, this.mModel)) {
            return this.mVersion == null || Objects.equals(accessory.mVersion, this.mVersion);
        }
        return false;
    }

    public boolean equals(Object obj) {
        if (this.mManufacturer == null || this.mModel == null || this.mVersion == null) {
            return false;
        }
        if (obj instanceof AccessoryFilter) {
            AccessoryFilter filter = (AccessoryFilter) obj;
            return this.mManufacturer.equals(filter.mManufacturer) && this.mModel.equals(filter.mModel) && this.mVersion.equals(filter.mVersion);
        }
        if (!(obj instanceof UsbAccessory)) {
            return false;
        }
        UsbAccessory accessory = (UsbAccessory) obj;
        return this.mManufacturer.equals(accessory.getManufacturer()) && this.mModel.equals(accessory.getModel()) && this.mVersion.equals(accessory.getVersion());
    }

    public int hashCode() {
        return ((this.mManufacturer == null ? 0 : this.mManufacturer.hashCode()) ^ (this.mModel == null ? 0 : this.mModel.hashCode())) ^ (this.mVersion != null ? this.mVersion.hashCode() : 0);
    }

    public String toString() {
        return "AccessoryFilter[mManufacturer=\"" + this.mManufacturer + "\", mModel=\"" + this.mModel + "\", mVersion=\"" + this.mVersion + "\"]";
    }

    public void dump(DualDumpOutputStream dump, String idName, long id) {
        long token = dump.start(idName, id);
        dump.write(MidiDeviceInfo.PROPERTY_MANUFACTURER, 1138166333441L, this.mManufacturer);
        dump.write("model", 1138166333442L, this.mModel);
        dump.write("version", 1138166333443L, this.mVersion);
        dump.end(token);
    }
}
