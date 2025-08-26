package android.telephony;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.telephony.TelephonyFeatures;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@SystemApi
/* loaded from: classes4.dex */
public final class PhoneCapability implements Parcelable {
    public static final Parcelable.Creator<PhoneCapability> CREATOR;
    public static final PhoneCapability DEFAULT_DSDS_CAPABILITY;
    public static final PhoneCapability DEFAULT_SSSS_CAPABILITY;

    @SystemApi
    public static final int DEVICE_NR_CAPABILITY_NSA = 1;

    @SystemApi
    public static final int DEVICE_NR_CAPABILITY_SA = 2;
    private final int[] mDeviceNrCapabilities;
    private final List<ModemInfo> mLogicalModemList;
    private final int mMaxActiveDataSubscriptions;
    private final int mMaxActiveVoiceSubscriptions;
    private final boolean mNetworkValidationBeforeSwitchSupported;

    @Retention(RetentionPolicy.SOURCE)
    public @interface DeviceNrCapability {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    static {
        ModemInfo modemInfo = new ModemInfo(0, 0, true, true);
        ModemInfo modemInfo2 = new ModemInfo(1, 0, true, true);
        ArrayList arrayList = new ArrayList();
        arrayList.add(modemInfo);
        arrayList.add(modemInfo2);
        int[] iArr = new int[0];
        DEFAULT_DSDS_CAPABILITY = new PhoneCapability(1, 1, arrayList, false, iArr);
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(modemInfo);
        DEFAULT_SSSS_CAPABILITY = new PhoneCapability(1, 1, arrayList2, false, iArr);
        CREATOR = new Parcelable.Creator() { // from class: android.telephony.PhoneCapability.1
            @Override // android.os.Parcelable.Creator
            public PhoneCapability createFromParcel(Parcel parcel) {
                return new PhoneCapability(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public PhoneCapability[] newArray(int i) {
                return new PhoneCapability[i];
            }
        };
    }

    public PhoneCapability(int i, int i2, List<ModemInfo> list, boolean z, int[] iArr) {
        this.mMaxActiveVoiceSubscriptions = i;
        this.mMaxActiveDataSubscriptions = i2;
        this.mLogicalModemList = list == null ? new ArrayList<>() : list;
        if (TelephonyFeatures.isSubOperatorSpecific(1, "XMO", "CCT") || TelephonyFeatures.isSubOperatorSpecific(0, "XMO", "CCT")) {
            this.mNetworkValidationBeforeSwitchSupported = true;
        } else {
            this.mNetworkValidationBeforeSwitchSupported = z;
        }
        this.mDeviceNrCapabilities = iArr;
    }

    private PhoneCapability(Builder builder) {
        this.mMaxActiveVoiceSubscriptions = builder.mMaxActiveVoiceSubscriptions;
        this.mMaxActiveDataSubscriptions = builder.mMaxActiveDataSubscriptions;
        this.mLogicalModemList = builder.mLogicalModemList == null ? new ArrayList<>() : builder.mLogicalModemList;
        this.mNetworkValidationBeforeSwitchSupported = builder.mNetworkValidationBeforeSwitchSupported;
        this.mDeviceNrCapabilities = builder.mDeviceNrCapabilities;
    }

    public String toString() {
        return "mMaxActiveVoiceSubscriptions=" + this.mMaxActiveVoiceSubscriptions + " mMaxActiveDataSubscriptions=" + this.mMaxActiveDataSubscriptions + " mNetworkValidationBeforeSwitchSupported=" + this.mNetworkValidationBeforeSwitchSupported + " mDeviceNrCapability " + Arrays.toString(this.mDeviceNrCapabilities);
    }

    private PhoneCapability(Parcel parcel) throws ClassNotFoundException, IOException {
        this.mMaxActiveVoiceSubscriptions = parcel.readInt();
        this.mMaxActiveDataSubscriptions = parcel.readInt();
        boolean z = parcel.readBoolean();
        if (TelephonyFeatures.isSubOperatorSpecific(1, "XMO")) {
            this.mNetworkValidationBeforeSwitchSupported = true;
        } else {
            this.mNetworkValidationBeforeSwitchSupported = z;
        }
        ArrayList arrayList = new ArrayList();
        this.mLogicalModemList = arrayList;
        parcel.readList(arrayList, ModemInfo.class.getClassLoader(), ModemInfo.class);
        this.mDeviceNrCapabilities = parcel.createIntArray();
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mMaxActiveVoiceSubscriptions), Integer.valueOf(this.mMaxActiveDataSubscriptions), this.mLogicalModemList, Boolean.valueOf(this.mNetworkValidationBeforeSwitchSupported), Integer.valueOf(Arrays.hashCode(this.mDeviceNrCapabilities)));
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof PhoneCapability) && hashCode() == obj.hashCode()) {
            if (this == obj) {
                return true;
            }
            PhoneCapability phoneCapability = (PhoneCapability) obj;
            if (this.mMaxActiveVoiceSubscriptions == phoneCapability.mMaxActiveVoiceSubscriptions && this.mMaxActiveDataSubscriptions == phoneCapability.mMaxActiveDataSubscriptions && this.mNetworkValidationBeforeSwitchSupported == phoneCapability.mNetworkValidationBeforeSwitchSupported && this.mLogicalModemList.equals(phoneCapability.mLogicalModemList) && Arrays.equals(this.mDeviceNrCapabilities, phoneCapability.mDeviceNrCapabilities)) {
                return true;
            }
        }
        return false;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mMaxActiveVoiceSubscriptions);
        parcel.writeInt(this.mMaxActiveDataSubscriptions);
        parcel.writeBoolean(this.mNetworkValidationBeforeSwitchSupported);
        parcel.writeList(this.mLogicalModemList);
        parcel.writeIntArray(this.mDeviceNrCapabilities);
    }

    @SystemApi
    public int getMaxActiveVoiceSubscriptions() {
        return this.mMaxActiveVoiceSubscriptions;
    }

    @SystemApi
    public int getMaxActiveDataSubscriptions() {
        return this.mMaxActiveDataSubscriptions;
    }

    public boolean isNetworkValidationBeforeSwitchSupported() {
        return this.mNetworkValidationBeforeSwitchSupported;
    }

    public List<ModemInfo> getLogicalModemList() {
        return this.mLogicalModemList;
    }

    @SystemApi
    public int[] getDeviceNrCapabilities() {
        int[] iArr = this.mDeviceNrCapabilities;
        return iArr == null ? new int[0] : iArr;
    }

    public static class Builder {
        private int[] mDeviceNrCapabilities;
        private List<ModemInfo> mLogicalModemList;
        private int mMaxActiveDataSubscriptions;
        private int mMaxActiveVoiceSubscriptions;
        private boolean mNetworkValidationBeforeSwitchSupported;

        public Builder() {
            this.mMaxActiveVoiceSubscriptions = 0;
            this.mMaxActiveDataSubscriptions = 0;
            this.mNetworkValidationBeforeSwitchSupported = false;
            this.mLogicalModemList = new ArrayList();
            this.mDeviceNrCapabilities = new int[0];
        }

        public Builder(PhoneCapability phoneCapability) {
            this.mMaxActiveVoiceSubscriptions = 0;
            this.mMaxActiveDataSubscriptions = 0;
            this.mNetworkValidationBeforeSwitchSupported = false;
            this.mLogicalModemList = new ArrayList();
            this.mDeviceNrCapabilities = new int[0];
            this.mMaxActiveVoiceSubscriptions = phoneCapability.mMaxActiveVoiceSubscriptions;
            this.mMaxActiveDataSubscriptions = phoneCapability.mMaxActiveDataSubscriptions;
            this.mNetworkValidationBeforeSwitchSupported = phoneCapability.mNetworkValidationBeforeSwitchSupported;
            this.mLogicalModemList = phoneCapability.mLogicalModemList;
            this.mDeviceNrCapabilities = phoneCapability.mDeviceNrCapabilities;
        }

        public Builder setMaxActiveVoiceSubscriptions(int i) {
            this.mMaxActiveVoiceSubscriptions = i;
            return this;
        }

        public Builder setMaxActiveDataSubscriptions(int i) {
            this.mMaxActiveDataSubscriptions = i;
            return this;
        }

        public Builder setNetworkValidationBeforeSwitchSupported(boolean z) {
            this.mNetworkValidationBeforeSwitchSupported = z;
            return this;
        }

        public Builder setLogicalModemList(List<ModemInfo> list) {
            this.mLogicalModemList = list;
            return this;
        }

        public Builder setDeviceNrCapabilities(int[] iArr) {
            this.mDeviceNrCapabilities = iArr;
            return this;
        }

        public PhoneCapability build() {
            return new PhoneCapability(this);
        }
    }
}
