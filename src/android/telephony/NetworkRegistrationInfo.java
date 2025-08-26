package android.telephony;

import android.annotation.SystemApi;
import android.app.compat.CompatChanges;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.security.keystore.KeyProperties;
import android.telephony.DataSpecificRegistrationInfo;
import android.text.TextUtils;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/* loaded from: classes4.dex */
public final class NetworkRegistrationInfo implements Parcelable {
    public static final Parcelable.Creator<NetworkRegistrationInfo> CREATOR = new Parcelable.Creator<NetworkRegistrationInfo>() { // from class: android.telephony.NetworkRegistrationInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NetworkRegistrationInfo createFromParcel(Parcel parcel) {
            return new NetworkRegistrationInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NetworkRegistrationInfo[] newArray(int i) {
            return new NetworkRegistrationInfo[i];
        }
    };
    public static final int DOMAIN_CS = 1;
    public static final int DOMAIN_CS_PS = 3;
    public static final int DOMAIN_PS = 2;
    public static final int DOMAIN_UNKNOWN = 0;
    public static final int FIRST_SERVICE_TYPE = 1;
    public static final int LAST_SERVICE_TYPE = 6;
    public static final int NR_STATE_CONNECTED = 3;
    public static final int NR_STATE_NONE = 0;
    public static final int NR_STATE_NOT_RESTRICTED = 2;
    public static final int NR_STATE_RESTRICTED = 1;

    @SystemApi
    public static final int REGISTRATION_STATE_DENIED = 3;

    @SystemApi
    public static final int REGISTRATION_STATE_EMERGENCY = 6;

    @SystemApi
    public static final int REGISTRATION_STATE_HOME = 1;

    @SystemApi
    public static final int REGISTRATION_STATE_NOT_REGISTERED_OR_SEARCHING = 0;

    @SystemApi
    public static final int REGISTRATION_STATE_NOT_REGISTERED_SEARCHING = 2;

    @SystemApi
    public static final int REGISTRATION_STATE_ROAMING = 5;

    @SystemApi
    public static final int REGISTRATION_STATE_UNKNOWN = 4;
    public static final long RETURN_REGISTRATION_STATE_EMERGENCY = 255938466;
    public static final int SERVICE_TYPE_DATA = 2;
    public static final int SERVICE_TYPE_EMERGENCY = 5;
    public static final int SERVICE_TYPE_MMS = 6;
    public static final int SERVICE_TYPE_SMS = 3;
    public static final int SERVICE_TYPE_UNKNOWN = 0;
    public static final int SERVICE_TYPE_VIDEO = 4;
    public static final int SERVICE_TYPE_VOICE = 1;
    private int mAccessNetworkTechnology;
    private ArrayList<Integer> mAvailableServices;
    private CellIdentity mCellIdentity;
    private DataSpecificRegistrationInfo mDataSpecificInfo;
    private final int mDomain;
    private final boolean mEmergencyOnly;
    private boolean mIsNonTerrestrialNetwork;
    private boolean mIsUsingCarrierAggregation;
    private final int mNetworkRegistrationState;
    private int mNrState;
    private int mRegistrationState;
    private final int mRejectCause;
    private int mRoamingType;
    private String mRplmn;
    private final int mTransportType;
    private VoiceSpecificRegistrationInfo mVoiceSpecificInfo;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Domain {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface NRState {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface RegistrationState {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ServiceType {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private NetworkRegistrationInfo(int i, int i2, int i3, int i4, int i5, boolean z, List<Integer> list, CellIdentity cellIdentity, String str, VoiceSpecificRegistrationInfo voiceSpecificRegistrationInfo, DataSpecificRegistrationInfo dataSpecificRegistrationInfo, boolean z2) {
        this.mDomain = i;
        this.mTransportType = i2;
        this.mRegistrationState = i3;
        this.mNetworkRegistrationState = i3;
        this.mRoamingType = i3 == 5 ? 1 : 0;
        setAccessNetworkTechnology(i4);
        this.mRejectCause = i5;
        this.mAvailableServices = list != null ? new ArrayList<>(list) : new ArrayList<>();
        this.mCellIdentity = cellIdentity;
        this.mEmergencyOnly = z;
        this.mNrState = 0;
        this.mRplmn = str;
        this.mVoiceSpecificInfo = voiceSpecificRegistrationInfo;
        this.mDataSpecificInfo = dataSpecificRegistrationInfo;
        this.mIsNonTerrestrialNetwork = z2;
        updateNrState();
    }

    public NetworkRegistrationInfo(int i, int i2, int i3, int i4, int i5, boolean z, List<Integer> list, CellIdentity cellIdentity, String str, boolean z2, int i6, int i7, int i8) {
        this(i, i2, i3, i4, i5, z, list, cellIdentity, str, new VoiceSpecificRegistrationInfo(z2, i6, i7, i8), null, false);
    }

    public NetworkRegistrationInfo(int i, int i2, int i3, int i4, int i5, boolean z, List<Integer> list, CellIdentity cellIdentity, String str, int i6, boolean z2, boolean z3, boolean z4, VopsSupportInfo vopsSupportInfo) {
        this(i, i2, i3, i4, i5, z, list, cellIdentity, str, null, new DataSpecificRegistrationInfo.Builder(i6).setDcNrRestricted(z2).setNrAvailable(z3).setEnDcAvailable(z4).setVopsSupportInfo(vopsSupportInfo).build(), false);
    }

    private NetworkRegistrationInfo(Parcel parcel) throws ClassNotFoundException, IOException {
        this.mDomain = parcel.readInt();
        this.mTransportType = parcel.readInt();
        this.mRegistrationState = parcel.readInt();
        this.mNetworkRegistrationState = parcel.readInt();
        this.mRoamingType = parcel.readInt();
        this.mAccessNetworkTechnology = parcel.readInt();
        this.mRejectCause = parcel.readInt();
        this.mEmergencyOnly = parcel.readBoolean();
        ArrayList<Integer> arrayList = new ArrayList<>();
        this.mAvailableServices = arrayList;
        parcel.readList(arrayList, Integer.class.getClassLoader(), Integer.class);
        this.mCellIdentity = (CellIdentity) parcel.readParcelable(CellIdentity.class.getClassLoader(), CellIdentity.class);
        this.mVoiceSpecificInfo = (VoiceSpecificRegistrationInfo) parcel.readParcelable(VoiceSpecificRegistrationInfo.class.getClassLoader(), VoiceSpecificRegistrationInfo.class);
        this.mDataSpecificInfo = (DataSpecificRegistrationInfo) parcel.readParcelable(DataSpecificRegistrationInfo.class.getClassLoader(), DataSpecificRegistrationInfo.class);
        this.mNrState = parcel.readInt();
        this.mRplmn = parcel.readString();
        this.mIsUsingCarrierAggregation = parcel.readBoolean();
        this.mIsNonTerrestrialNetwork = parcel.readBoolean();
    }

    public NetworkRegistrationInfo(NetworkRegistrationInfo networkRegistrationInfo) {
        this.mDomain = networkRegistrationInfo.mDomain;
        this.mTransportType = networkRegistrationInfo.mTransportType;
        this.mRegistrationState = networkRegistrationInfo.mRegistrationState;
        this.mNetworkRegistrationState = networkRegistrationInfo.mNetworkRegistrationState;
        this.mRoamingType = networkRegistrationInfo.mRoamingType;
        this.mAccessNetworkTechnology = networkRegistrationInfo.mAccessNetworkTechnology;
        this.mIsUsingCarrierAggregation = networkRegistrationInfo.mIsUsingCarrierAggregation;
        this.mIsNonTerrestrialNetwork = networkRegistrationInfo.mIsNonTerrestrialNetwork;
        this.mRejectCause = networkRegistrationInfo.mRejectCause;
        this.mEmergencyOnly = networkRegistrationInfo.mEmergencyOnly;
        this.mAvailableServices = new ArrayList<>(networkRegistrationInfo.mAvailableServices);
        if (networkRegistrationInfo.mCellIdentity != null) {
            Parcel parcelObtain = Parcel.obtain();
            networkRegistrationInfo.mCellIdentity.writeToParcel(parcelObtain, 0);
            parcelObtain.setDataPosition(0);
            this.mCellIdentity = CellIdentity.CREATOR.createFromParcel(parcelObtain);
            parcelObtain.recycle();
        }
        if (networkRegistrationInfo.mVoiceSpecificInfo != null) {
            this.mVoiceSpecificInfo = new VoiceSpecificRegistrationInfo(networkRegistrationInfo.mVoiceSpecificInfo);
        }
        if (networkRegistrationInfo.mDataSpecificInfo != null) {
            this.mDataSpecificInfo = new DataSpecificRegistrationInfo(networkRegistrationInfo.mDataSpecificInfo);
        }
        this.mNrState = networkRegistrationInfo.mNrState;
        this.mRplmn = networkRegistrationInfo.mRplmn;
    }

    public int getTransportType() {
        return this.mTransportType;
    }

    public int getDomain() {
        return this.mDomain;
    }

    public int getNrState() {
        return this.mNrState;
    }

    public void setNrState(int i) {
        this.mNrState = i;
    }

    @SystemApi
    @Deprecated
    public int getRegistrationState() {
        if (this.mRegistrationState == 6 && !CompatChanges.isChangeEnabled(RETURN_REGISTRATION_STATE_EMERGENCY)) {
            int i = this.mAccessNetworkTechnology;
            if (i == 13) {
                return 3;
            }
            if (i == 20) {
                return 0;
            }
        }
        return this.mRegistrationState;
    }

    @SystemApi
    public int getNetworkRegistrationState() {
        return this.mNetworkRegistrationState;
    }

    @Deprecated
    public boolean isRegistered() {
        int i = this.mRegistrationState;
        return i == 1 || i == 5;
    }

    public boolean isNetworkRegistered() {
        int i = this.mNetworkRegistrationState;
        return i == 1 || i == 5;
    }

    @Deprecated
    public boolean isSearching() {
        return this.mRegistrationState == 2;
    }

    public boolean isNetworkSearching() {
        return this.mNetworkRegistrationState == 2;
    }

    public String getRegisteredPlmn() {
        return this.mRplmn;
    }

    @Deprecated
    public boolean isRoaming() {
        return this.mRoamingType != 0;
    }

    public boolean isNetworkRoaming() {
        return this.mNetworkRegistrationState == 5;
    }

    public boolean isInService() {
        int i = this.mRegistrationState;
        return i == 1 || i == 5;
    }

    public void setRoamingType(int i) {
        this.mRoamingType = i;
        if (isRoaming()) {
            if (this.mRegistrationState == 1) {
                this.mRegistrationState = 5;
            }
        } else if (this.mRegistrationState == 5) {
            this.mRegistrationState = 1;
        }
    }

    @SystemApi
    public int getRoamingType() {
        return this.mRoamingType;
    }

    @SystemApi
    public boolean isEmergencyEnabled() {
        return this.mEmergencyOnly;
    }

    public List<Integer> getAvailableServices() {
        return Collections.unmodifiableList(this.mAvailableServices);
    }

    public void setAvailableServices(List<Integer> list) {
        this.mAvailableServices = new ArrayList<>(list);
    }

    public int getAccessNetworkTechnology() {
        return this.mAccessNetworkTechnology;
    }

    public void setAccessNetworkTechnology(int i) {
        if (i == 19) {
            this.mIsUsingCarrierAggregation = true;
            i = 13;
        }
        this.mAccessNetworkTechnology = i;
    }

    public int getRejectCause() {
        return this.mRejectCause;
    }

    public CellIdentity getCellIdentity() {
        return this.mCellIdentity;
    }

    public void setIsUsingCarrierAggregation(boolean z) {
        this.mIsUsingCarrierAggregation = z;
    }

    public boolean isUsingCarrierAggregation() {
        return this.mIsUsingCarrierAggregation;
    }

    public void setIsNonTerrestrialNetwork(boolean z) {
        this.mIsNonTerrestrialNetwork = z;
    }

    public boolean isNonTerrestrialNetwork() {
        return this.mIsNonTerrestrialNetwork;
    }

    public VoiceSpecificRegistrationInfo getVoiceSpecificInfo() {
        return this.mVoiceSpecificInfo;
    }

    @SystemApi
    public DataSpecificRegistrationInfo getDataSpecificInfo() {
        return this.mDataSpecificInfo;
    }

    public static String serviceTypeToString(int i) {
        switch (i) {
            case 1:
                return "VOICE";
            case 2:
                return "DATA";
            case 3:
                return "SMS";
            case 4:
                return "VIDEO";
            case 5:
                return "EMERGENCY";
            case 6:
                return "MMS";
            default:
                return "Unknown service type " + i;
        }
    }

    public static String registrationStateToString(int i) {
        switch (i) {
            case 0:
                return "NOT_REG_OR_SEARCHING";
            case 1:
                return "HOME";
            case 2:
                return "NOT_REG_SEARCHING";
            case 3:
                return "DENIED";
            case 4:
                return "UNKNOWN";
            case 5:
                return "ROAMING";
            case 6:
                return "EMERGENCY";
            default:
                return "Unknown reg state " + i;
        }
    }

    public static String nrStateToString(int i) {
        if (i == 1) {
            return "RESTRICTED";
        }
        if (i == 2) {
            return "NOT_RESTRICTED";
        }
        if (i == 3) {
            return "CONNECTED";
        }
        return KeyProperties.DIGEST_NONE;
    }

    static String domainToString(int i) {
        if (i == 1) {
            return "CS";
        }
        if (i == 2) {
            return "PS";
        }
        if (i == 3) {
            return "CS_PS";
        }
        return "UNKNOWN";
    }

    public static String isNonTerrestrialNetworkToString(boolean z) {
        return z ? "NON-TERRESTRIAL" : "TERRESTRIAL";
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("NetworkRegistrationInfo{ domain=");
        sb.append(domainToString(this.mDomain));
        sb.append(" transportType=");
        sb.append(AccessNetworkConstants.transportTypeToString(this.mTransportType));
        sb.append(" registrationState=");
        sb.append(registrationStateToString(this.mRegistrationState));
        sb.append(" networkRegistrationState=");
        sb.append(registrationStateToString(this.mNetworkRegistrationState));
        sb.append(" roamingType=");
        sb.append(ServiceState.roamingTypeToString(this.mRoamingType));
        sb.append(" accessNetworkTechnology=");
        sb.append(TelephonyManager.getNetworkTypeName(this.mAccessNetworkTechnology));
        sb.append(" rejectCause=");
        sb.append(this.mRejectCause);
        sb.append(" emergencyEnabled=");
        sb.append(this.mEmergencyOnly);
        sb.append(" availableServices=");
        StringBuilder sb2 = new StringBuilder(NavigationBarInflaterView.SIZE_MOD_START);
        ArrayList<Integer> arrayList = this.mAvailableServices;
        sb2.append(arrayList != null ? (String) arrayList.stream().map(new Function() { // from class: android.telephony.NetworkRegistrationInfo$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return NetworkRegistrationInfo.serviceTypeToString(((Integer) obj).intValue());
            }
        }).collect(Collectors.joining(",")) : null);
        sb2.append(NavigationBarInflaterView.SIZE_MOD_END);
        sb.append(sb2.toString());
        sb.append(" cellIdentity=");
        sb.append(this.mCellIdentity);
        sb.append(" voiceSpecificInfo=");
        sb.append(this.mVoiceSpecificInfo);
        sb.append(" dataSpecificInfo=");
        sb.append(this.mDataSpecificInfo);
        sb.append(" nrState=");
        sb.append(Build.IS_DEBUGGABLE ? nrStateToString(this.mNrState) : "****");
        sb.append(" rRplmn=");
        sb.append(this.mRplmn);
        sb.append(" isUsingCarrierAggregation=");
        sb.append(this.mIsUsingCarrierAggregation);
        sb.append(" isNonTerrestrialNetwork=");
        sb.append(isNonTerrestrialNetworkToString(this.mIsNonTerrestrialNetwork));
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mDomain), Integer.valueOf(this.mTransportType), Integer.valueOf(this.mRegistrationState), Integer.valueOf(this.mNetworkRegistrationState), Integer.valueOf(this.mRoamingType), Integer.valueOf(this.mAccessNetworkTechnology), Integer.valueOf(this.mRejectCause), Boolean.valueOf(this.mEmergencyOnly), this.mAvailableServices, this.mCellIdentity, this.mVoiceSpecificInfo, this.mDataSpecificInfo, Integer.valueOf(this.mNrState), this.mRplmn, Boolean.valueOf(this.mIsUsingCarrierAggregation), Boolean.valueOf(this.mIsNonTerrestrialNetwork));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof NetworkRegistrationInfo)) {
            return false;
        }
        NetworkRegistrationInfo networkRegistrationInfo = (NetworkRegistrationInfo) obj;
        return this.mDomain == networkRegistrationInfo.mDomain && this.mTransportType == networkRegistrationInfo.mTransportType && this.mRegistrationState == networkRegistrationInfo.mRegistrationState && this.mNetworkRegistrationState == networkRegistrationInfo.mNetworkRegistrationState && this.mRoamingType == networkRegistrationInfo.mRoamingType && this.mAccessNetworkTechnology == networkRegistrationInfo.mAccessNetworkTechnology && this.mRejectCause == networkRegistrationInfo.mRejectCause && this.mEmergencyOnly == networkRegistrationInfo.mEmergencyOnly && this.mAvailableServices.equals(networkRegistrationInfo.mAvailableServices) && this.mIsUsingCarrierAggregation == networkRegistrationInfo.mIsUsingCarrierAggregation && Objects.equals(this.mCellIdentity, networkRegistrationInfo.mCellIdentity) && Objects.equals(this.mVoiceSpecificInfo, networkRegistrationInfo.mVoiceSpecificInfo) && Objects.equals(this.mDataSpecificInfo, networkRegistrationInfo.mDataSpecificInfo) && TextUtils.equals(this.mRplmn, networkRegistrationInfo.mRplmn) && this.mNrState == networkRegistrationInfo.mNrState && this.mIsNonTerrestrialNetwork == networkRegistrationInfo.mIsNonTerrestrialNetwork;
    }

    @Override // android.os.Parcelable
    @SystemApi
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mDomain);
        parcel.writeInt(this.mTransportType);
        parcel.writeInt(this.mRegistrationState);
        parcel.writeInt(this.mNetworkRegistrationState);
        parcel.writeInt(this.mRoamingType);
        parcel.writeInt(this.mAccessNetworkTechnology);
        parcel.writeInt(this.mRejectCause);
        parcel.writeBoolean(this.mEmergencyOnly);
        parcel.writeList(this.mAvailableServices);
        parcel.writeParcelable(this.mCellIdentity, 0);
        parcel.writeParcelable(this.mVoiceSpecificInfo, 0);
        parcel.writeParcelable(this.mDataSpecificInfo, 0);
        parcel.writeInt(this.mNrState);
        parcel.writeString(this.mRplmn);
        parcel.writeBoolean(this.mIsUsingCarrierAggregation);
        parcel.writeBoolean(this.mIsNonTerrestrialNetwork);
    }

    public void updateNrState() {
        this.mNrState = 0;
        DataSpecificRegistrationInfo dataSpecificRegistrationInfo = this.mDataSpecificInfo;
        if (dataSpecificRegistrationInfo == null || !dataSpecificRegistrationInfo.isEnDcAvailable) {
            return;
        }
        if (!this.mDataSpecificInfo.isDcNrRestricted && this.mDataSpecificInfo.isNrAvailable) {
            this.mNrState = 2;
        } else {
            this.mNrState = 1;
        }
    }

    public NetworkRegistrationInfo sanitizeLocationInfo() {
        NetworkRegistrationInfo networkRegistrationInfoCopy = copy();
        networkRegistrationInfoCopy.mCellIdentity = null;
        return networkRegistrationInfoCopy;
    }

    private NetworkRegistrationInfo copy() {
        Parcel parcelObtain = Parcel.obtain();
        writeToParcel(parcelObtain, 0);
        parcelObtain.setDataPosition(0);
        NetworkRegistrationInfo networkRegistrationInfo = new NetworkRegistrationInfo(parcelObtain);
        parcelObtain.recycle();
        return networkRegistrationInfo;
    }

    @SystemApi
    public static final class Builder {
        private int mAccessNetworkTechnology;
        private List<Integer> mAvailableServices;
        private CellIdentity mCellIdentity;
        private DataSpecificRegistrationInfo mDataSpecificRegistrationInfo;
        private int mDomain;
        private boolean mEmergencyOnly;
        private boolean mIsNonTerrestrialNetwork;
        private int mNetworkRegistrationState;
        private int mRejectCause;
        private String mRplmn = "";
        private int mTransportType;
        private VoiceSpecificRegistrationInfo mVoiceSpecificRegistrationInfo;

        public Builder() {
        }

        public Builder(NetworkRegistrationInfo networkRegistrationInfo) {
            this.mDomain = networkRegistrationInfo.mDomain;
            this.mTransportType = networkRegistrationInfo.mTransportType;
            this.mNetworkRegistrationState = networkRegistrationInfo.mNetworkRegistrationState;
            this.mAccessNetworkTechnology = networkRegistrationInfo.mAccessNetworkTechnology;
            this.mRejectCause = networkRegistrationInfo.mRejectCause;
            this.mEmergencyOnly = networkRegistrationInfo.mEmergencyOnly;
            this.mAvailableServices = new ArrayList(networkRegistrationInfo.mAvailableServices);
            this.mCellIdentity = networkRegistrationInfo.mCellIdentity;
            if (networkRegistrationInfo.mDataSpecificInfo != null) {
                this.mDataSpecificRegistrationInfo = new DataSpecificRegistrationInfo(networkRegistrationInfo.mDataSpecificInfo);
            }
            if (networkRegistrationInfo.mVoiceSpecificInfo != null) {
                this.mVoiceSpecificRegistrationInfo = new VoiceSpecificRegistrationInfo(networkRegistrationInfo.mVoiceSpecificInfo);
            }
            this.mIsNonTerrestrialNetwork = networkRegistrationInfo.mIsNonTerrestrialNetwork;
        }

        public Builder setDomain(int i) {
            this.mDomain = i;
            return this;
        }

        public Builder setTransportType(int i) {
            this.mTransportType = i;
            return this;
        }

        public Builder setRegistrationState(int i) {
            this.mNetworkRegistrationState = i;
            return this;
        }

        public Builder setAccessNetworkTechnology(int i) {
            this.mAccessNetworkTechnology = i;
            return this;
        }

        public Builder setRejectCause(int i) {
            this.mRejectCause = i;
            return this;
        }

        @SystemApi
        public Builder setEmergencyOnly(boolean z) {
            this.mEmergencyOnly = z;
            return this;
        }

        @SystemApi
        public Builder setAvailableServices(List<Integer> list) {
            this.mAvailableServices = list;
            return this;
        }

        @SystemApi
        public Builder setCellIdentity(CellIdentity cellIdentity) {
            this.mCellIdentity = cellIdentity;
            return this;
        }

        public Builder setRegisteredPlmn(String str) {
            this.mRplmn = str;
            return this;
        }

        public Builder setVoiceSpecificInfo(VoiceSpecificRegistrationInfo voiceSpecificRegistrationInfo) {
            this.mVoiceSpecificRegistrationInfo = voiceSpecificRegistrationInfo;
            return this;
        }

        public Builder setDataSpecificInfo(DataSpecificRegistrationInfo dataSpecificRegistrationInfo) {
            this.mDataSpecificRegistrationInfo = dataSpecificRegistrationInfo;
            return this;
        }

        public Builder setIsNonTerrestrialNetwork(boolean z) {
            this.mIsNonTerrestrialNetwork = z;
            return this;
        }

        @SystemApi
        public NetworkRegistrationInfo build() {
            return new NetworkRegistrationInfo(this.mDomain, this.mTransportType, this.mNetworkRegistrationState, this.mAccessNetworkTechnology, this.mRejectCause, this.mEmergencyOnly, this.mAvailableServices, this.mCellIdentity, this.mRplmn, this.mVoiceSpecificRegistrationInfo, this.mDataSpecificRegistrationInfo, this.mIsNonTerrestrialNetwork);
        }
    }
}
