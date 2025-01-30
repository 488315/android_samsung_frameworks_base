package com.sec.ims;

import android.net.Network;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SemSystemProperties;
import android.telephony.SubscriptionManager;
import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;
import com.sec.ims.settings.ImsProfile;
import com.sec.ims.util.ImsUri;
import com.sec.ims.util.NameAddr;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class ImsRegistration implements Parcelable {
    private static final String LOG_TAG = "ImsRegistration";
    public static final int NETWORK_TYPE_MOBILE = 1;
    public static final int NETWORK_TYPE_UNKNOWN = 0;
    public static final int NETWORK_TYPE_WIFI = 2;
    private int mCurrentRat;
    private int mDeregiReason;
    private final List<NameAddr> mDeviceList;
    private final String mDomain;
    private final int mEcmpStatus;
    private boolean mEpdgOverCellularData;
    private boolean mEpdgStatus;
    private final int mHandle;
    private int mInitialRegistrationRat;
    private final String mInstanceId;
    private final Network mNetwork;
    private String mPAssociatedUri2nd;
    private final String mPcscf;
    private final int mPdnType;
    private final int mPhoneId;
    private final NameAddr mPreferredPublicUserId;
    private final String mPrivateUserId;
    private final ImsProfile mProfile;
    private boolean mProhibited;
    private final List<NameAddr> mPublicUserId;
    private final int mRegExpiryStatus;
    private final String mRegisterSipResponse;
    private final ImsUri mRegisteredPublicUserId;
    private final Set<String> mServices;
    private final int mSubscriptionId;
    private String mUuid;
    public static final boolean SHIP_BUILD = "true".equals(SemSystemProperties.get("ro.product_ship", "false"));
    public static final Parcelable.Creator<ImsRegistration> CREATOR = new Parcelable.Creator<ImsRegistration>() { // from class: com.sec.ims.ImsRegistration.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ImsRegistration createFromParcel(Parcel parcel) {
            return new ImsRegistration(parcel, 0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ImsRegistration[] newArray(int i) {
            return new ImsRegistration[i];
        }
    };

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Builder {
        protected String mDomain;
        private int mEcmpStatus;
        private boolean mEpdgOverCellularData;
        private boolean mEpdgStatus;
        protected int mHandle;
        protected String mInstanceId;
        private Network mNetwork;
        private String mPAssociatedUri2nd;
        protected String mPcscf;
        protected int mPdnType;
        protected NameAddr mPreferredPublicUserId;
        protected String mPrivateUserId;
        protected ImsProfile mProfile;
        protected int mRat;
        private int mRegExpiryStatus;
        private String mRegisterSipResponse;
        protected ImsUri mRegisteredPublicUserId;
        private String mUuid;
        Set<String> mServices = new HashSet();
        protected List<NameAddr> mPublicUserId = new ArrayList();
        protected List<NameAddr> mDeviceList = new ArrayList();
        protected int mSubscriptionId = SubscriptionManager.getDefaultDataSubscriptionId();
        protected int mPhoneId = 0;
        private boolean mProhibited = false;
        private int mDeregiReason = 14;

        public Builder addService(String str) {
            this.mServices.add(str);
            return this;
        }

        public ImsRegistration build() {
            return new ImsRegistration(this);
        }

        public Builder setDeregiReason(int i) {
            this.mDeregiReason = i;
            return this;
        }

        public Builder setDeviceList(List<NameAddr> list) {
            this.mDeviceList.addAll(list);
            return this;
        }

        public Builder setDomain(String str) {
            this.mDomain = str;
            return this;
        }

        public Builder setEcmpStatus(int i) {
            this.mEcmpStatus = i;
            return this;
        }

        public Builder setEpdgOverCellularData(boolean z) {
            this.mEpdgOverCellularData = z;
            return this;
        }

        public Builder setEpdgStatus(boolean z) {
            this.mEpdgStatus = z;
            return this;
        }

        public Builder setHandle(int i) {
            this.mHandle = i;
            return this;
        }

        public Builder setImsProfile(ImsProfile imsProfile) {
            this.mProfile = imsProfile;
            return this;
        }

        public Builder setInstanceId(String str) {
            this.mInstanceId = str;
            return this;
        }

        public Builder setNetwork(Network network) {
            this.mNetwork = network;
            return this;
        }

        public Builder setPAssociatedUri2nd(String str) {
            this.mPAssociatedUri2nd = str;
            return this;
        }

        public Builder setPcscf(String str) {
            this.mPcscf = str;
            return this;
        }

        public Builder setPdnType(int i) {
            this.mPdnType = i;
            return this;
        }

        public Builder setPhoneId(int i) {
            this.mPhoneId = i;
            return this;
        }

        public Builder setPreferredPublicUserId(NameAddr nameAddr) {
            this.mPreferredPublicUserId = nameAddr;
            return this;
        }

        public Builder setPrivateUserId(String str) {
            this.mPrivateUserId = str;
            return this;
        }

        public Builder setProhibited(boolean z) {
            this.mProhibited = z;
            return this;
        }

        public Builder setPublicUserId(List<NameAddr> list) {
            this.mPublicUserId.addAll(list);
            return this;
        }

        public Builder setRegExpiryStatus(int i) {
            this.mRegExpiryStatus = i;
            return this;
        }

        public Builder setRegiRat(int i) {
            this.mRat = i;
            return this;
        }

        public Builder setRegisterSipResponse(String str) {
            this.mRegisterSipResponse = str;
            return this;
        }

        public Builder setRegisteredPublicUserId(ImsUri imsUri) {
            this.mRegisteredPublicUserId = imsUri;
            return this;
        }

        public Builder setServices(Set<String> set) {
            this.mServices.clear();
            this.mServices.addAll(set);
            return this;
        }

        public Builder setSubscriptionId(int i) {
            this.mSubscriptionId = i;
            return this;
        }

        public Builder setUuid(String str) {
            this.mUuid = str;
            return this;
        }
    }

    public /* synthetic */ ImsRegistration(Parcel parcel, int i) {
        this(parcel);
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    private void readServices(Parcel parcel) {
        ArrayList arrayList = new ArrayList();
        parcel.readStringList(arrayList);
        this.mServices.addAll(arrayList);
    }

    private void writeServices(Parcel parcel) {
        parcel.writeStringList(new ArrayList(this.mServices));
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getCurrentRat() {
        return this.mCurrentRat;
    }

    public int getDeregiReason() {
        return this.mDeregiReason;
    }

    public List<NameAddr> getDeviceList() {
        return new ArrayList(this.mDeviceList);
    }

    public String getDomain() {
        return this.mDomain;
    }

    public int getEcmpStatus() {
        return this.mEcmpStatus;
    }

    public boolean getEpdgStatus() {
        return this.mEpdgStatus;
    }

    public int getHandle() {
        return this.mHandle;
    }

    public String getImpi() {
        return this.mPrivateUserId;
    }

    public List<NameAddr> getImpuList() {
        return new ArrayList(this.mPublicUserId);
    }

    public ImsProfile getImsProfile() {
        return this.mProfile;
    }

    public String getInstanceId() {
        return this.mInstanceId;
    }

    public Network getNetwork() {
        return this.mNetwork;
    }

    public int getNetworkType() {
        return this.mPdnType;
    }

    public String getOwnNumber() {
        String msisdn;
        if (getPreferredImpu() == null || getPreferredImpu().getUri() == null || (msisdn = getPreferredImpu().getUri().getMsisdn()) == null) {
            return null;
        }
        return msisdn;
    }

    public String getPAssociatedUri2nd() {
        return this.mPAssociatedUri2nd;
    }

    public String getPcscf() {
        return this.mPcscf;
    }

    public int getPhoneId() {
        return this.mPhoneId;
    }

    public NameAddr getPreferredImpu() {
        NameAddr nameAddr = this.mPreferredPublicUserId;
        return nameAddr != null ? nameAddr : new NameAddr("", "");
    }

    public int getRegiRat() {
        return this.mInitialRegistrationRat;
    }

    public String getRegisterSipResponse() {
        return this.mRegisterSipResponse;
    }

    public ImsUri getRegisteredImpu() {
        return this.mRegisteredPublicUserId;
    }

    public Set<String> getServices() {
        return new HashSet(this.mServices);
    }

    public int getSubscriptionId() {
        return this.mSubscriptionId;
    }

    public String getUuid() {
        return this.mUuid;
    }

    public boolean hasRcsService() {
        return new HashSet(Arrays.asList(ImsProfile.getRcsServiceList())).removeAll(this.mServices);
    }

    public boolean hasService(String str) {
        return this.mServices.contains(str);
    }

    public boolean hasVolteService() {
        HashSet hashSet = new HashSet(Arrays.asList(ImsProfile.getVoLteServiceList()));
        hashSet.remove(ImsProfile.SERVICE_CDPN);
        return hashSet.removeAll(this.mServices);
    }

    public boolean isEpdgOverCellularData() {
        return this.mEpdgOverCellularData;
    }

    public boolean isImsiBased(String str) {
        ImsUri imsUri = this.mRegisteredPublicUserId;
        if (imsUri == null || str == null) {
            return false;
        }
        return imsUri.toString().contains(str);
    }

    public boolean isProhibited() {
        return this.mProhibited;
    }

    public void setCurrentRat(int i) {
        this.mCurrentRat = i;
    }

    public void setDeregiReason(int i) {
        this.mDeregiReason = i;
    }

    public void setEpdgOverCellularData(boolean z) {
        this.mEpdgOverCellularData = z;
    }

    public void setEpdgStatus(boolean z) {
        this.mEpdgStatus = z;
    }

    public void setPAssociatedUri2nd(String str) {
        this.mPAssociatedUri2nd = str;
    }

    public void setProhibited(boolean z) {
        this.mProhibited = z;
    }

    public void setRegiRat(int i) {
        this.mInitialRegistrationRat = i;
    }

    public void setUuid(String str) {
        this.mUuid = str;
    }

    public String toString() {
        if (SHIP_BUILD) {
            StringBuilder sb = new StringBuilder("ImsRegistration [mHandle=");
            sb.append(this.mHandle);
            sb.append(", mProfile=");
            ImsProfile imsProfile = this.mProfile;
            sb.append(imsProfile != null ? imsProfile.getName() : "null");
            sb.append(", mServices=");
            sb.append(this.mServices);
            sb.append(", mDomain=");
            sb.append(this.mDomain);
            sb.append(", mDeviceList=");
            sb.append(this.mDeviceList);
            sb.append(", mSubscriptionId=");
            sb.append(this.mSubscriptionId);
            sb.append(", mPhoneId=");
            sb.append(this.mPhoneId);
            sb.append(", mPdnType=");
            sb.append(this.mPdnType);
            sb.append(", mPcscf=");
            sb.append(this.mPcscf);
            sb.append(", mEcmpStatus=");
            sb.append(this.mEcmpStatus);
            sb.append(", mRegExpiryStatus=");
            sb.append(this.mRegExpiryStatus);
            sb.append(", mEpdgStatus=");
            sb.append(this.mEpdgStatus);
            sb.append(", mEpdgOverCellularData=");
            sb.append(this.mEpdgOverCellularData);
            sb.append(", mProhibited=");
            sb.append(this.mProhibited);
            sb.append(", mNetwork=");
            sb.append(this.mNetwork);
            sb.append(", mDeregiReason =");
            return ConstraintWidget$$ExternalSyntheticOutline0.m19m(sb, this.mDeregiReason, "]");
        }
        StringBuilder sb2 = new StringBuilder("ImsRegistration [mHandle=");
        sb2.append(this.mHandle);
        sb2.append(", mProfile=");
        ImsProfile imsProfile2 = this.mProfile;
        sb2.append(imsProfile2 != null ? imsProfile2.getName() : "null");
        sb2.append(", mServices=");
        sb2.append(this.mServices);
        sb2.append(", mDomain=");
        sb2.append(this.mDomain);
        sb2.append(", mPrivateUserId=");
        sb2.append(this.mPrivateUserId);
        sb2.append(", mRegisteredPublicUserId=");
        sb2.append(this.mRegisteredPublicUserId);
        sb2.append(", mPreferredPublicUserId=");
        sb2.append(this.mPreferredPublicUserId);
        sb2.append(", mPublicUserId=");
        sb2.append(this.mPublicUserId);
        sb2.append(", mDeviceList=");
        sb2.append(this.mDeviceList);
        sb2.append(", mSubscriptionId=");
        sb2.append(this.mSubscriptionId);
        sb2.append(", mPhoneId=");
        sb2.append(this.mPhoneId);
        sb2.append(", mInstanceId=");
        sb2.append(this.mInstanceId);
        sb2.append(", mPdnType=");
        sb2.append(this.mPdnType);
        sb2.append(", mPcscf=");
        sb2.append(this.mPcscf);
        sb2.append(", mEcmpStatus=");
        sb2.append(this.mEcmpStatus);
        sb2.append(", mRegExpiryStatus=");
        sb2.append(this.mRegExpiryStatus);
        sb2.append(", mEpdgStatus=");
        sb2.append(this.mEpdgStatus);
        sb2.append(", mEpdgOverCellularData=");
        sb2.append(this.mEpdgOverCellularData);
        sb2.append(", mProhibited=");
        sb2.append(this.mProhibited);
        sb2.append(", mNetwork=");
        sb2.append(this.mNetwork);
        sb2.append(", mDeregiReason =");
        return ConstraintWidget$$ExternalSyntheticOutline0.m19m(sb2, this.mDeregiReason, "]");
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mHandle);
        parcel.writeString(this.mProfile.toJson());
        writeServices(parcel);
        parcel.writeInt(this.mInitialRegistrationRat);
        parcel.writeInt(this.mCurrentRat);
        parcel.writeInt(this.mSubscriptionId);
        parcel.writeInt(this.mPhoneId);
        parcel.writeString(this.mPrivateUserId);
        if (this.mRegisteredPublicUserId == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            parcel.writeString(this.mRegisteredPublicUserId.toString());
        }
        parcel.writeParcelable(this.mPreferredPublicUserId, i);
        parcel.writeTypedList(this.mPublicUserId);
        parcel.writeTypedList(this.mDeviceList);
        parcel.writeString(this.mDomain);
        parcel.writeString(this.mPcscf);
        parcel.writeString(this.mInstanceId);
        parcel.writeInt(this.mPdnType);
        parcel.writeInt(this.mEcmpStatus);
        parcel.writeInt(this.mRegExpiryStatus);
        parcel.writeInt(this.mEpdgStatus ? 1 : 0);
        parcel.writeInt(this.mEpdgOverCellularData ? 1 : 0);
        if (this.mRegisterSipResponse == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            parcel.writeString(this.mRegisterSipResponse);
        }
        parcel.writeParcelable(this.mNetwork, i);
        parcel.writeString(this.mPAssociatedUri2nd);
        parcel.writeString(this.mUuid);
    }

    private ImsRegistration(Parcel parcel) {
        this.mProhibited = false;
        this.mDeregiReason = 14;
        this.mPAssociatedUri2nd = "";
        this.mUuid = "";
        this.mHandle = parcel.readInt();
        this.mProfile = new ImsProfile(parcel.readString());
        this.mServices = new HashSet();
        readServices(parcel);
        this.mInitialRegistrationRat = parcel.readInt();
        this.mCurrentRat = parcel.readInt();
        this.mSubscriptionId = parcel.readInt();
        this.mPhoneId = parcel.readInt();
        this.mPrivateUserId = parcel.readString();
        if (parcel.readInt() == 1) {
            this.mRegisteredPublicUserId = ImsUri.parse(parcel.readString());
        } else {
            this.mRegisteredPublicUserId = null;
        }
        this.mPreferredPublicUserId = (NameAddr) parcel.readParcelable(NameAddr.class.getClassLoader());
        ArrayList arrayList = new ArrayList();
        this.mPublicUserId = arrayList;
        Parcelable.Creator<NameAddr> creator = NameAddr.CREATOR;
        parcel.readTypedList(arrayList, creator);
        ArrayList arrayList2 = new ArrayList();
        this.mDeviceList = arrayList2;
        parcel.readTypedList(arrayList2, creator);
        this.mDomain = parcel.readString();
        this.mPcscf = parcel.readString();
        this.mInstanceId = parcel.readString();
        this.mPdnType = parcel.readInt();
        this.mEcmpStatus = parcel.readInt();
        this.mRegExpiryStatus = parcel.readInt();
        this.mEpdgStatus = parcel.readInt() == 1;
        this.mEpdgOverCellularData = parcel.readInt() == 1;
        if (parcel.readInt() == 1) {
            this.mRegisterSipResponse = parcel.readString();
        } else {
            this.mRegisterSipResponse = null;
        }
        this.mNetwork = (Network) parcel.readParcelable(Network.class.getClassLoader());
        this.mPAssociatedUri2nd = parcel.readString();
        this.mUuid = parcel.readString();
    }

    public ImsRegistration(ImsRegistration imsRegistration) {
        this.mProhibited = false;
        this.mDeregiReason = 14;
        this.mPAssociatedUri2nd = "";
        this.mUuid = "";
        this.mHandle = imsRegistration.mHandle;
        this.mProfile = new ImsProfile(imsRegistration.mProfile);
        this.mServices = new HashSet(imsRegistration.mServices);
        this.mInitialRegistrationRat = imsRegistration.mInitialRegistrationRat;
        this.mCurrentRat = imsRegistration.mCurrentRat;
        this.mDomain = imsRegistration.mDomain;
        this.mPrivateUserId = imsRegistration.mPrivateUserId;
        this.mRegisteredPublicUserId = imsRegistration.mRegisteredPublicUserId;
        this.mPreferredPublicUserId = imsRegistration.mPreferredPublicUserId;
        this.mPublicUserId = new ArrayList(imsRegistration.mPublicUserId);
        this.mDeviceList = new ArrayList(imsRegistration.mDeviceList);
        this.mSubscriptionId = imsRegistration.mSubscriptionId;
        this.mPhoneId = imsRegistration.mPhoneId;
        this.mInstanceId = imsRegistration.mInstanceId;
        this.mPdnType = imsRegistration.mPdnType;
        this.mPcscf = imsRegistration.mPcscf;
        this.mEcmpStatus = imsRegistration.mEcmpStatus;
        this.mRegExpiryStatus = imsRegistration.mRegExpiryStatus;
        this.mEpdgStatus = imsRegistration.mEpdgStatus;
        this.mEpdgOverCellularData = imsRegistration.mEpdgOverCellularData;
        this.mProhibited = imsRegistration.mProhibited;
        this.mRegisterSipResponse = imsRegistration.mRegisterSipResponse;
        this.mNetwork = imsRegistration.mNetwork;
        this.mDeregiReason = imsRegistration.mDeregiReason;
        this.mPAssociatedUri2nd = imsRegistration.mPAssociatedUri2nd;
    }

    public ImsRegistration(Builder builder) {
        this.mProhibited = false;
        this.mDeregiReason = 14;
        this.mPAssociatedUri2nd = "";
        this.mUuid = "";
        this.mHandle = builder.mHandle;
        this.mProfile = builder.mProfile;
        this.mServices = builder.mServices;
        int i = builder.mRat;
        this.mInitialRegistrationRat = i;
        this.mCurrentRat = i;
        this.mDomain = builder.mDomain;
        this.mPrivateUserId = builder.mPrivateUserId;
        this.mRegisteredPublicUserId = builder.mRegisteredPublicUserId;
        this.mPreferredPublicUserId = builder.mPreferredPublicUserId;
        this.mPublicUserId = builder.mPublicUserId;
        this.mDeviceList = builder.mDeviceList;
        this.mSubscriptionId = builder.mSubscriptionId;
        this.mPhoneId = builder.mPhoneId;
        this.mInstanceId = builder.mInstanceId;
        this.mPdnType = builder.mPdnType;
        this.mPcscf = builder.mPcscf;
        this.mEcmpStatus = builder.mEcmpStatus;
        this.mRegExpiryStatus = builder.mRegExpiryStatus;
        this.mEpdgStatus = builder.mEpdgStatus;
        this.mEpdgOverCellularData = builder.mEpdgOverCellularData;
        this.mProhibited = builder.mProhibited;
        this.mRegisterSipResponse = builder.mRegisterSipResponse;
        this.mNetwork = builder.mNetwork;
        this.mDeregiReason = builder.mDeregiReason;
        this.mPAssociatedUri2nd = builder.mPAssociatedUri2nd;
    }

    public ImsRegistration(ImsRegistration imsRegistration, Set<String> set) {
        this.mProhibited = false;
        this.mDeregiReason = 14;
        this.mPAssociatedUri2nd = "";
        this.mUuid = "";
        this.mHandle = imsRegistration.mHandle;
        this.mProfile = new ImsProfile(imsRegistration.mProfile);
        this.mServices = set;
        this.mInitialRegistrationRat = imsRegistration.mInitialRegistrationRat;
        this.mCurrentRat = imsRegistration.mCurrentRat;
        this.mDomain = imsRegistration.mDomain;
        this.mPrivateUserId = imsRegistration.mPrivateUserId;
        this.mRegisteredPublicUserId = imsRegistration.mRegisteredPublicUserId;
        this.mPreferredPublicUserId = imsRegistration.mPreferredPublicUserId;
        this.mPublicUserId = new ArrayList(imsRegistration.mPublicUserId);
        this.mDeviceList = new ArrayList(imsRegistration.mDeviceList);
        this.mSubscriptionId = imsRegistration.mSubscriptionId;
        this.mPhoneId = imsRegistration.mPhoneId;
        this.mInstanceId = imsRegistration.mInstanceId;
        this.mPdnType = imsRegistration.mPdnType;
        this.mPcscf = imsRegistration.mPcscf;
        this.mEcmpStatus = imsRegistration.mEcmpStatus;
        this.mRegExpiryStatus = imsRegistration.mRegExpiryStatus;
        this.mEpdgStatus = imsRegistration.mEpdgStatus;
        this.mEpdgOverCellularData = imsRegistration.mEpdgOverCellularData;
        this.mProhibited = imsRegistration.mProhibited;
        this.mRegisterSipResponse = imsRegistration.mRegisterSipResponse;
        this.mNetwork = imsRegistration.mNetwork;
        this.mDeregiReason = imsRegistration.mDeregiReason;
        this.mPAssociatedUri2nd = imsRegistration.mPAssociatedUri2nd;
    }
}
