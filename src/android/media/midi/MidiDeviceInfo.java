package android.media.midi;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes3.dex */
public final class MidiDeviceInfo implements Parcelable {
    public static final Parcelable.Creator<MidiDeviceInfo> CREATOR = new Parcelable.Creator<MidiDeviceInfo>() { // from class: android.media.midi.MidiDeviceInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MidiDeviceInfo createFromParcel(Parcel parcel) {
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            int i3 = parcel.readInt();
            int i4 = parcel.readInt();
            String[] strArrCreateStringArray = parcel.createStringArray();
            String[] strArrCreateStringArray2 = parcel.createStringArray();
            boolean z = parcel.readInt() == 1;
            int i5 = parcel.readInt();
            parcel.readBundle();
            return new MidiDeviceInfo(i, i2, i3, i4, strArrCreateStringArray, strArrCreateStringArray2, parcel.readBundle(), z, i5);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MidiDeviceInfo[] newArray(int i) {
            return new MidiDeviceInfo[i];
        }
    };
    public static final String PROPERTY_ALSA_CARD = "alsa_card";
    public static final String PROPERTY_ALSA_DEVICE = "alsa_device";
    public static final String PROPERTY_BLUETOOTH_DEVICE = "bluetooth_device";
    public static final String PROPERTY_MANUFACTURER = "manufacturer";
    public static final String PROPERTY_NAME = "name";
    public static final String PROPERTY_PRODUCT = "product";
    public static final String PROPERTY_SERIAL_NUMBER = "serial_number";
    public static final String PROPERTY_SERVICE_INFO = "service_info";
    public static final String PROPERTY_USB_DEVICE = "usb_device";
    public static final String PROPERTY_VERSION = "version";
    public static final int PROTOCOL_UMP_MIDI_1_0_UP_TO_128_BITS = 3;
    public static final int PROTOCOL_UMP_MIDI_1_0_UP_TO_128_BITS_AND_JRTS = 4;
    public static final int PROTOCOL_UMP_MIDI_1_0_UP_TO_64_BITS = 1;
    public static final int PROTOCOL_UMP_MIDI_1_0_UP_TO_64_BITS_AND_JRTS = 2;
    public static final int PROTOCOL_UMP_MIDI_2_0 = 17;
    public static final int PROTOCOL_UMP_MIDI_2_0_AND_JRTS = 18;
    public static final int PROTOCOL_UMP_USE_MIDI_CI = 0;
    public static final int PROTOCOL_UNKNOWN = -1;
    private static final String TAG = "MidiDeviceInfo";
    public static final int TYPE_BLUETOOTH = 3;
    public static final int TYPE_USB = 1;
    public static final int TYPE_VIRTUAL = 2;
    private final int mDefaultProtocol;
    private final int mId;
    private final int mInputPortCount;
    private final String[] mInputPortNames;
    private final boolean mIsPrivate;
    private final int mOutputPortCount;
    private final String[] mOutputPortNames;
    private final Bundle mProperties;
    private final int mType;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Protocol {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static final class PortInfo {
        public static final int TYPE_INPUT = 1;
        public static final int TYPE_OUTPUT = 2;
        private final String mName;
        private final int mPortNumber;
        private final int mPortType;

        PortInfo(int i, int i2, String str) {
            this.mPortType = i;
            this.mPortNumber = i2;
            this.mName = str == null ? "" : str;
        }

        public int getType() {
            return this.mPortType;
        }

        public int getPortNumber() {
            return this.mPortNumber;
        }

        public String getName() {
            return this.mName;
        }
    }

    public MidiDeviceInfo(int i, int i2, int i3, int i4, String[] strArr, String[] strArr2, Bundle bundle, boolean z, int i5) {
        if (i3 < 0 || i3 > 256) {
            throw new IllegalArgumentException("numInputPorts out of range = " + i3);
        }
        if (i4 < 0 || i4 > 256) {
            throw new IllegalArgumentException("numOutputPorts out of range = " + i4);
        }
        this.mType = i;
        this.mId = i2;
        this.mInputPortCount = i3;
        this.mOutputPortCount = i4;
        if (strArr == null) {
            this.mInputPortNames = new String[i3];
        } else {
            this.mInputPortNames = strArr;
        }
        if (strArr2 == null) {
            this.mOutputPortNames = new String[i4];
        } else {
            this.mOutputPortNames = strArr2;
        }
        this.mProperties = bundle;
        this.mIsPrivate = z;
        this.mDefaultProtocol = i5;
    }

    public int getType() {
        return this.mType;
    }

    public int getId() {
        return this.mId;
    }

    public int getInputPortCount() {
        return this.mInputPortCount;
    }

    public int getOutputPortCount() {
        return this.mOutputPortCount;
    }

    public PortInfo[] getPorts() {
        PortInfo[] portInfoArr = new PortInfo[this.mInputPortCount + this.mOutputPortCount];
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (i2 < this.mInputPortCount) {
            portInfoArr[i3] = new PortInfo(1, i2, this.mInputPortNames[i2]);
            i2++;
            i3++;
        }
        while (i < this.mOutputPortCount) {
            portInfoArr[i3] = new PortInfo(2, i, this.mOutputPortNames[i]);
            i++;
            i3++;
        }
        return portInfoArr;
    }

    public Bundle getProperties() {
        return this.mProperties;
    }

    public boolean isPrivate() {
        return this.mIsPrivate;
    }

    public int getDefaultProtocol() {
        return this.mDefaultProtocol;
    }

    public boolean equals(Object obj) {
        return (obj instanceof MidiDeviceInfo) && ((MidiDeviceInfo) obj).mId == this.mId;
    }

    public int hashCode() {
        return this.mId;
    }

    public String toString() {
        this.mProperties.getString("name");
        return "MidiDeviceInfo[mType=" + this.mType + ",mInputPortCount=" + this.mInputPortCount + ",mOutputPortCount=" + this.mOutputPortCount + ",mProperties=" + this.mProperties + ",mIsPrivate=" + this.mIsPrivate + ",mDefaultProtocol=" + this.mDefaultProtocol;
    }

    private Bundle getBasicProperties(String[] strArr) {
        Bundle bundle = new Bundle();
        for (String str : strArr) {
            Object obj = this.mProperties.get(str);
            if (obj != null) {
                if (obj instanceof String) {
                    bundle.putString(str, (String) obj);
                } else if (obj instanceof Integer) {
                    bundle.putInt(str, ((Integer) obj).intValue());
                } else {
                    Log.w(TAG, "Unsupported property type: " + obj.getClass().getName());
                }
            }
        }
        return bundle;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mType);
        parcel.writeInt(this.mId);
        parcel.writeInt(this.mInputPortCount);
        parcel.writeInt(this.mOutputPortCount);
        parcel.writeStringArray(this.mInputPortNames);
        parcel.writeStringArray(this.mOutputPortNames);
        parcel.writeInt(this.mIsPrivate ? 1 : 0);
        parcel.writeInt(this.mDefaultProtocol);
        parcel.writeBundle(getBasicProperties(new String[]{"name", PROPERTY_MANUFACTURER, "product", "version", "serial_number", PROPERTY_ALSA_CARD, PROPERTY_ALSA_DEVICE}));
        parcel.writeBundle(this.mProperties);
    }
}
