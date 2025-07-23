package com.samsung.android.ims;

import android.net.Network;
import android.os.Parcel;
import android.os.Parcelable;
import com.samsung.android.ims.settings.SemImsProfile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* loaded from: classes6.dex */
public class SemImsRegistration implements Parcelable {
    public static final Parcelable.Creator<SemImsRegistration> CREATOR = new Parcelable.Creator<SemImsRegistration>() { // from class: com.samsung.android.ims.SemImsRegistration.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemImsRegistration createFromParcel(Parcel parcel) {
            return new SemImsRegistration(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemImsRegistration[] newArray(int i) {
            return new SemImsRegistration[i];
        }
    };
    private static final String LOG_TAG = "SemImsRegistration";
    private static final int NETWORK_TYPE_MOBILE = 1;
    private static final int NETWORK_TYPE_UNKNOWN = 0;
    private static final int NETWORK_TYPE_WIFI = 2;
    private int mDeregiReason;
    private final List<String> mDeviceList;
    private final String mDomain;
    private final int mEcmpStatus;
    private boolean mEpdgOverCellularData;
    private boolean mEpdgStatus;
    private final int mHandle;
    private final String mInstanceId;
    private final Network mNetwork;
    private final String mOwnNumber;
    private String mPAssociatedUri2nd;
    private final String mPcscf;
    private final int mPdnType;
    private final int mPhoneId;
    private final String mPreferredPublicUserId;
    private final String mPrivateUserId;
    private boolean mProhibited;
    private final List<String> mPublicUserId;
    private int mRat;
    private final int mRegExpiryStatus;
    private final String mRegisterSipResponse;
    private final String mRegisteredPublicUserId;
    private final Set<String> mServices;
    private final int mSubscriptionId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getHandle() {
        return this.mHandle;
    }

    public String getDomain() {
        return this.mDomain;
    }

    public String getPcscf() {
        return this.mPcscf;
    }

    public int getSubscriptionId() {
        return this.mSubscriptionId;
    }

    public int getPhoneId() {
        return this.mPhoneId;
    }

    public Set<String> getRegisteredFeatures() {
        return new HashSet(this.mServices);
    }

    public Set<String> getServices() {
        return new HashSet(this.mServices);
    }

    public String getInstanceId() {
        return this.mInstanceId;
    }

    public boolean hasService(String str) {
        return this.mServices.contains(str);
    }

    public List<String> getImpuList() {
        return new ArrayList(this.mPublicUserId);
    }

    public List<String> getDeviceList() {
        return new ArrayList(this.mDeviceList);
    }

    public String getImpi() {
        return this.mPrivateUserId;
    }

    public boolean hasRcsService() {
        return new HashSet(Arrays.asList(SemImsProfile.ImsFeature.getRcsServiceList())).removeAll(this.mServices);
    }

    public String getPreferredImpu() {
        return this.mPreferredPublicUserId;
    }

    public String getRegisteredImpu() {
        return this.mRegisteredPublicUserId;
    }

    public String getOwnNumber() {
        return this.mOwnNumber;
    }

    public int getEcmpStatus() {
        return this.mEcmpStatus;
    }

    public String getRegisterSipResponse() {
        return this.mRegisterSipResponse;
    }

    public boolean getEpdgStatus() {
        return this.mEpdgStatus;
    }

    public void setEpdgStatus(boolean z) {
        this.mEpdgStatus = z;
    }

    public boolean isEpdgOverCellularData() {
        return this.mEpdgOverCellularData;
    }

    public void setEpdgOverCellularData(boolean z) {
        this.mEpdgOverCellularData = z;
    }

    public int getNetworkType() {
        return this.mPdnType;
    }

    public void setProhibited(boolean z) {
        this.mProhibited = z;
    }

    public boolean isProhibited() {
        return this.mProhibited;
    }

    public boolean isImsiBased(String str) {
        return this.mRegisteredPublicUserId.toString().contains(str);
    }

    public Network getNetwork() {
        return this.mNetwork;
    }

    public void setDeregiReason(int i) {
        this.mDeregiReason = i;
    }

    public int getDeregiReason() {
        return this.mDeregiReason;
    }

    public void setRegiRat(int i) {
        this.mRat = i;
    }

    public int getRegisteredRat() {
        return this.mRat;
    }

    public void setSecondPAssociatedUri(String str) {
        this.mPAssociatedUri2nd = str;
    }

    public String getSecondPAssociatedUri() {
        return this.mPAssociatedUri2nd;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mHandle);
        writeServices(parcel);
        parcel.writeInt(this.mRat);
        parcel.writeInt(this.mSubscriptionId);
        parcel.writeInt(this.mPhoneId);
        parcel.writeString(this.mPrivateUserId);
        if (this.mRegisteredPublicUserId == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            parcel.writeString(this.mRegisteredPublicUserId.toString());
        }
        parcel.writeString(this.mPreferredPublicUserId);
        parcel.writeStringList(this.mPublicUserId);
        parcel.writeStringList(this.mDeviceList);
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
        parcel.writeString(this.mOwnNumber);
    }

    private SemImsRegistration(Parcel parcel) {
        this.mProhibited = false;
        this.mDeregiReason = 14;
        this.mPAssociatedUri2nd = "";
        this.mHandle = parcel.readInt();
        this.mServices = new HashSet();
        readServices(parcel);
        this.mRat = parcel.readInt();
        this.mSubscriptionId = parcel.readInt();
        this.mPhoneId = parcel.readInt();
        this.mPrivateUserId = parcel.readString();
        if (parcel.readInt() == 1) {
            this.mRegisteredPublicUserId = parcel.readString();
        } else {
            this.mRegisteredPublicUserId = null;
        }
        this.mPreferredPublicUserId = parcel.readString();
        ArrayList arrayList = new ArrayList();
        this.mPublicUserId = arrayList;
        parcel.readStringList(arrayList);
        ArrayList arrayList2 = new ArrayList();
        this.mDeviceList = arrayList2;
        parcel.readStringList(arrayList2);
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
        this.mOwnNumber = parcel.readString();
    }

    private void writeServices(Parcel parcel) {
        parcel.writeStringList(new ArrayList(this.mServices));
    }

    private void readServices(Parcel parcel) {
        ArrayList arrayList = new ArrayList();
        parcel.readStringList(arrayList);
        this.mServices.addAll(arrayList);
    }

    public SemImsRegistration(SemImsRegistration semImsRegistration) {
        this.mProhibited = false;
        this.mDeregiReason = 14;
        this.mPAssociatedUri2nd = "";
        this.mHandle = semImsRegistration.mHandle;
        this.mServices = new HashSet(semImsRegistration.mServices);
        this.mRat = semImsRegistration.mRat;
        this.mDomain = semImsRegistration.mDomain;
        this.mPrivateUserId = semImsRegistration.mPrivateUserId;
        this.mRegisteredPublicUserId = semImsRegistration.mRegisteredPublicUserId;
        this.mPreferredPublicUserId = semImsRegistration.mPreferredPublicUserId;
        this.mPublicUserId = new ArrayList(semImsRegistration.mPublicUserId);
        this.mDeviceList = new ArrayList(semImsRegistration.mDeviceList);
        this.mSubscriptionId = semImsRegistration.mSubscriptionId;
        this.mPhoneId = semImsRegistration.mPhoneId;
        this.mInstanceId = semImsRegistration.mInstanceId;
        this.mPdnType = semImsRegistration.mPdnType;
        this.mPcscf = semImsRegistration.mPcscf;
        this.mEcmpStatus = semImsRegistration.mEcmpStatus;
        this.mRegExpiryStatus = semImsRegistration.mRegExpiryStatus;
        this.mEpdgStatus = semImsRegistration.mEpdgStatus;
        this.mEpdgOverCellularData = semImsRegistration.mEpdgOverCellularData;
        this.mProhibited = semImsRegistration.mProhibited;
        this.mRegisterSipResponse = semImsRegistration.mRegisterSipResponse;
        this.mNetwork = semImsRegistration.mNetwork;
        this.mDeregiReason = semImsRegistration.mDeregiReason;
        this.mPAssociatedUri2nd = semImsRegistration.mPAssociatedUri2nd;
        this.mOwnNumber = semImsRegistration.mOwnNumber;
    }

    public SemImsRegistration(Builder builder) {
        this.mProhibited = false;
        this.mDeregiReason = 14;
        this.mPAssociatedUri2nd = "";
        this.mHandle = builder.mHandle;
        this.mServices = builder.mServices;
        this.mRat = builder.mRat;
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
        this.mOwnNumber = builder.mOwnNumber;
    }

    public SemImsRegistration(SemImsRegistration semImsRegistration, Set<String> set) {
        this.mProhibited = false;
        this.mDeregiReason = 14;
        this.mPAssociatedUri2nd = "";
        this.mHandle = semImsRegistration.mHandle;
        this.mServices = set;
        this.mRat = semImsRegistration.mRat;
        this.mDomain = semImsRegistration.mDomain;
        this.mPrivateUserId = semImsRegistration.mPrivateUserId;
        this.mRegisteredPublicUserId = semImsRegistration.mRegisteredPublicUserId;
        this.mPreferredPublicUserId = semImsRegistration.mPreferredPublicUserId;
        this.mPublicUserId = new ArrayList(semImsRegistration.mPublicUserId);
        this.mDeviceList = new ArrayList(semImsRegistration.mDeviceList);
        this.mSubscriptionId = semImsRegistration.mSubscriptionId;
        this.mPhoneId = semImsRegistration.mPhoneId;
        this.mInstanceId = semImsRegistration.mInstanceId;
        this.mPdnType = semImsRegistration.mPdnType;
        this.mPcscf = semImsRegistration.mPcscf;
        this.mEcmpStatus = semImsRegistration.mEcmpStatus;
        this.mRegExpiryStatus = semImsRegistration.mRegExpiryStatus;
        this.mEpdgStatus = semImsRegistration.mEpdgStatus;
        this.mEpdgOverCellularData = semImsRegistration.mEpdgOverCellularData;
        this.mProhibited = semImsRegistration.mProhibited;
        this.mRegisterSipResponse = semImsRegistration.mRegisterSipResponse;
        this.mNetwork = semImsRegistration.mNetwork;
        this.mDeregiReason = semImsRegistration.mDeregiReason;
        this.mPAssociatedUri2nd = semImsRegistration.mPAssociatedUri2nd;
        this.mOwnNumber = semImsRegistration.mOwnNumber;
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public static class Builder {
        protected String mDomain;
        private int mEcmpStatus;
        private boolean mEpdgOverCellularData;
        private boolean mEpdgStatus;
        protected int mHandle;
        protected String mInstanceId;
        private Network mNetwork;
        protected String mOwnNumber;
        private String mPAssociatedUri2nd;
        protected String mPcscf;
        protected int mPdnType;
        protected String mPreferredPublicUserId;
        protected String mPrivateUserId;
        protected int mRat;
        private int mRegExpiryStatus;
        private String mRegisterSipResponse;
        protected String mRegisteredPublicUserId;
        Set<String> mServices = new HashSet();
        protected List<String> mPublicUserId = new ArrayList();
        protected List<String> mDeviceList = new ArrayList();
        protected int mSubscriptionId = 0;
        protected int mPhoneId = 0;
        private boolean mProhibited = false;
        private int mDeregiReason = 14;

        public SemImsRegistration build() {
            return new SemImsRegistration(this);
        }

        public Builder setHandle(int i) {
            this.mHandle = i;
            return this;
        }

        public Builder setServices(Set<String> set) {
            this.mServices.clear();
            this.mServices.addAll(set);
            return this;
        }

        public Builder setRegiRat(int i) {
            this.mRat = i;
            return this;
        }

        public Builder addService(String str) {
            this.mServices.add(str);
            return this;
        }

        public Builder setDomain(String str) {
            this.mDomain = str;
            return this;
        }

        public Builder setPrivateUserId(String str) {
            this.mPrivateUserId = str;
            return this;
        }

        public Builder setRegisteredPublicUserId(String str) {
            this.mRegisteredPublicUserId = str;
            return this;
        }

        public Builder setPreferredPublicUserId(String str) {
            this.mPreferredPublicUserId = str;
            return this;
        }

        public Builder setPublicUserId(List<String> list) {
            this.mPublicUserId.addAll(list);
            return this;
        }

        public Builder setDeviceList(List<String> list) {
            this.mDeviceList.addAll(list);
            return this;
        }

        public Builder setSubscriptionId(int i) {
            this.mSubscriptionId = i;
            return this;
        }

        public Builder setPhoneId(int i) {
            this.mPhoneId = i;
            return this;
        }

        public Builder setInstanceId(String str) {
            this.mInstanceId = str;
            return this;
        }

        public Builder setPdnType(int i) {
            this.mPdnType = i;
            return this;
        }

        public Builder setPcscf(String str) {
            this.mPcscf = str;
            return this;
        }

        public Builder setEcmpStatus(int i) {
            this.mEcmpStatus = i;
            return this;
        }

        public Builder setRegExpiryStatus(int i) {
            this.mRegExpiryStatus = i;
            return this;
        }

        public Builder setEpdgStatus(boolean z) {
            this.mEpdgStatus = z;
            return this;
        }

        public Builder setEpdgOverCellularData(boolean z) {
            this.mEpdgOverCellularData = z;
            return this;
        }

        public Builder setProhibited(boolean z) {
            this.mProhibited = z;
            return this;
        }

        public Builder setDeregiReason(int i) {
            this.mDeregiReason = i;
            return this;
        }

        public Builder setRegisterSipResponse(String str) {
            this.mRegisterSipResponse = str;
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

        public Builder setOwnNumber(String str) {
            this.mOwnNumber = str;
            return this;
        }
    }
}
