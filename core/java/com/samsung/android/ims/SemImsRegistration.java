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
    public static final Parcelable.Creator<SemImsRegistration> CREATOR =
            new Parcelable.Creator<
                    SemImsRegistration>() { // from class:
                                            // com.samsung.android.ims.SemImsRegistration.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SemImsRegistration createFromParcel(Parcel in) {
                    return new SemImsRegistration(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SemImsRegistration[] newArray(int size) {
                    return new SemImsRegistration[size];
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

    public boolean hasService(String service) {
        return this.mServices.contains(service);
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
        Set<String> rcsServices =
                new HashSet<>(Arrays.asList(SemImsProfile.ImsFeature.getRcsServiceList()));
        return rcsServices.removeAll(this.mServices);
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

    public void setEpdgStatus(boolean isEpdgConnected) {
        this.mEpdgStatus = isEpdgConnected;
    }

    public boolean isEpdgOverCellularData() {
        return this.mEpdgOverCellularData;
    }

    public void setEpdgOverCellularData(boolean isEpdgOverCellularData) {
        this.mEpdgOverCellularData = isEpdgOverCellularData;
    }

    public int getNetworkType() {
        return this.mPdnType;
    }

    public void setProhibited(boolean prohibited) {
        this.mProhibited = prohibited;
    }

    public boolean isProhibited() {
        return this.mProhibited;
    }

    public boolean isImsiBased(String imsi) {
        return this.mRegisteredPublicUserId.toString().contains(imsi);
    }

    public Network getNetwork() {
        return this.mNetwork;
    }

    public void setDeregiReason(int reason) {
        this.mDeregiReason = reason;
    }

    public int getDeregiReason() {
        return this.mDeregiReason;
    }

    public void setRegiRat(int rat) {
        this.mRat = rat;
    }

    public int getRegisteredRat() {
        return this.mRat;
    }

    public void setSecondPAssociatedUri(String pAssociatedUri) {
        this.mPAssociatedUri2nd = pAssociatedUri;
    }

    public String getSecondPAssociatedUri() {
        return this.mPAssociatedUri2nd;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
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

    private SemImsRegistration(Parcel in) {
        this.mProhibited = false;
        this.mDeregiReason = 14;
        this.mPAssociatedUri2nd = "";
        this.mHandle = in.readInt();
        this.mServices = new HashSet();
        readServices(in);
        this.mRat = in.readInt();
        this.mSubscriptionId = in.readInt();
        this.mPhoneId = in.readInt();
        this.mPrivateUserId = in.readString();
        if (in.readInt() == 1) {
            this.mRegisteredPublicUserId = in.readString();
        } else {
            this.mRegisteredPublicUserId = null;
        }
        this.mPreferredPublicUserId = in.readString();
        this.mPublicUserId = new ArrayList();
        in.readStringList(this.mPublicUserId);
        this.mDeviceList = new ArrayList();
        in.readStringList(this.mDeviceList);
        this.mDomain = in.readString();
        this.mPcscf = in.readString();
        this.mInstanceId = in.readString();
        this.mPdnType = in.readInt();
        this.mEcmpStatus = in.readInt();
        this.mRegExpiryStatus = in.readInt();
        this.mEpdgStatus = in.readInt() == 1;
        this.mEpdgOverCellularData = in.readInt() == 1;
        if (in.readInt() == 1) {
            this.mRegisterSipResponse = in.readString();
        } else {
            this.mRegisterSipResponse = null;
        }
        this.mNetwork = (Network) in.readParcelable(Network.class.getClassLoader());
        this.mPAssociatedUri2nd = in.readString();
        this.mOwnNumber = in.readString();
    }

    private void writeServices(Parcel out) {
        out.writeStringList(new ArrayList(this.mServices));
    }

    private void readServices(Parcel in) {
        List<String> services = new ArrayList<>();
        in.readStringList(services);
        this.mServices.addAll(services);
    }

    public SemImsRegistration(SemImsRegistration src) {
        this.mProhibited = false;
        this.mDeregiReason = 14;
        this.mPAssociatedUri2nd = "";
        this.mHandle = src.mHandle;
        this.mServices = new HashSet(src.mServices);
        this.mRat = src.mRat;
        this.mDomain = src.mDomain;
        this.mPrivateUserId = src.mPrivateUserId;
        this.mRegisteredPublicUserId = src.mRegisteredPublicUserId;
        this.mPreferredPublicUserId = src.mPreferredPublicUserId;
        this.mPublicUserId = new ArrayList(src.mPublicUserId);
        this.mDeviceList = new ArrayList(src.mDeviceList);
        this.mSubscriptionId = src.mSubscriptionId;
        this.mPhoneId = src.mPhoneId;
        this.mInstanceId = src.mInstanceId;
        this.mPdnType = src.mPdnType;
        this.mPcscf = src.mPcscf;
        this.mEcmpStatus = src.mEcmpStatus;
        this.mRegExpiryStatus = src.mRegExpiryStatus;
        this.mEpdgStatus = src.mEpdgStatus;
        this.mEpdgOverCellularData = src.mEpdgOverCellularData;
        this.mProhibited = src.mProhibited;
        this.mRegisterSipResponse = src.mRegisterSipResponse;
        this.mNetwork = src.mNetwork;
        this.mDeregiReason = src.mDeregiReason;
        this.mPAssociatedUri2nd = src.mPAssociatedUri2nd;
        this.mOwnNumber = src.mOwnNumber;
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

    public SemImsRegistration(SemImsRegistration src, Set<String> services) {
        this.mProhibited = false;
        this.mDeregiReason = 14;
        this.mPAssociatedUri2nd = "";
        this.mHandle = src.mHandle;
        this.mServices = services;
        this.mRat = src.mRat;
        this.mDomain = src.mDomain;
        this.mPrivateUserId = src.mPrivateUserId;
        this.mRegisteredPublicUserId = src.mRegisteredPublicUserId;
        this.mPreferredPublicUserId = src.mPreferredPublicUserId;
        this.mPublicUserId = new ArrayList(src.mPublicUserId);
        this.mDeviceList = new ArrayList(src.mDeviceList);
        this.mSubscriptionId = src.mSubscriptionId;
        this.mPhoneId = src.mPhoneId;
        this.mInstanceId = src.mInstanceId;
        this.mPdnType = src.mPdnType;
        this.mPcscf = src.mPcscf;
        this.mEcmpStatus = src.mEcmpStatus;
        this.mRegExpiryStatus = src.mRegExpiryStatus;
        this.mEpdgStatus = src.mEpdgStatus;
        this.mEpdgOverCellularData = src.mEpdgOverCellularData;
        this.mProhibited = src.mProhibited;
        this.mRegisterSipResponse = src.mRegisterSipResponse;
        this.mNetwork = src.mNetwork;
        this.mDeregiReason = src.mDeregiReason;
        this.mPAssociatedUri2nd = src.mPAssociatedUri2nd;
        this.mOwnNumber = src.mOwnNumber;
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

        public Builder setHandle(int handle) {
            this.mHandle = handle;
            return this;
        }

        public Builder setServices(Set<String> services) {
            this.mServices.clear();
            this.mServices.addAll(services);
            return this;
        }

        public Builder setRegiRat(int rat) {
            this.mRat = rat;
            return this;
        }

        public Builder addService(String service) {
            this.mServices.add(service);
            return this;
        }

        public Builder setDomain(String domain) {
            this.mDomain = domain;
            return this;
        }

        public Builder setPrivateUserId(String privateId) {
            this.mPrivateUserId = privateId;
            return this;
        }

        public Builder setRegisteredPublicUserId(String publicId) {
            this.mRegisteredPublicUserId = publicId;
            return this;
        }

        public Builder setPreferredPublicUserId(String impu) {
            this.mPreferredPublicUserId = impu;
            return this;
        }

        public Builder setPublicUserId(List<String> impus) {
            this.mPublicUserId.addAll(impus);
            return this;
        }

        public Builder setDeviceList(List<String> devices) {
            this.mDeviceList.addAll(devices);
            return this;
        }

        public Builder setSubscriptionId(int subscriptionId) {
            this.mSubscriptionId = subscriptionId;
            return this;
        }

        public Builder setPhoneId(int phoneId) {
            this.mPhoneId = phoneId;
            return this;
        }

        public Builder setInstanceId(String instanceId) {
            this.mInstanceId = instanceId;
            return this;
        }

        public Builder setPdnType(int pdn) {
            this.mPdnType = pdn;
            return this;
        }

        public Builder setPcscf(String pcscf) {
            this.mPcscf = pcscf;
            return this;
        }

        public Builder setEcmpStatus(int ecmp) {
            this.mEcmpStatus = ecmp;
            return this;
        }

        public Builder setRegExpiryStatus(int regExpiry) {
            this.mRegExpiryStatus = regExpiry;
            return this;
        }

        public Builder setEpdgStatus(boolean epdgStatus) {
            this.mEpdgStatus = epdgStatus;
            return this;
        }

        public Builder setEpdgOverCellularData(boolean epdgOverCellularData) {
            this.mEpdgOverCellularData = epdgOverCellularData;
            return this;
        }

        public Builder setProhibited(boolean prohibit) {
            this.mProhibited = prohibit;
            return this;
        }

        public Builder setDeregiReason(int reason) {
            this.mDeregiReason = reason;
            return this;
        }

        public Builder setRegisterSipResponse(String sip) {
            this.mRegisterSipResponse = sip;
            return this;
        }

        public Builder setNetwork(Network network) {
            this.mNetwork = network;
            return this;
        }

        public Builder setPAssociatedUri2nd(String uri) {
            this.mPAssociatedUri2nd = uri;
            return this;
        }

        public Builder setOwnNumber(String ownNumber) {
            this.mOwnNumber = ownNumber;
            return this;
        }
    }
}
