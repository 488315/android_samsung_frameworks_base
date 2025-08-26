package android.telephony.ims;

import android.annotation.SystemApi;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.telecom.VideoProfile;
import android.telephony.emergency.EmergencyNumber;
import android.util.ArraySet;
import android.util.Log;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import com.android.internal.telephony.util.TelephonyUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@SystemApi
/* loaded from: classes4.dex */
public final class ImsCallProfile implements Parcelable {
    public static final int CALL_RESTRICT_CAUSE_DISABLED = 2;
    public static final int CALL_RESTRICT_CAUSE_HD = 3;
    public static final int CALL_RESTRICT_CAUSE_NONE = 0;
    public static final int CALL_RESTRICT_CAUSE_RAT = 1;
    public static final int CALL_TYPE_NONE = 0;
    public static final int CALL_TYPE_VIDEO_N_VOICE = 3;
    public static final int CALL_TYPE_VOICE = 2;
    public static final int CALL_TYPE_VOICE_N_VIDEO = 1;
    public static final int CALL_TYPE_VS = 8;
    public static final int CALL_TYPE_VS_RX = 10;
    public static final int CALL_TYPE_VS_TX = 9;
    public static final int CALL_TYPE_VT = 4;
    public static final int CALL_TYPE_VT_NODIR = 7;
    public static final int CALL_TYPE_VT_RX = 6;
    public static final int CALL_TYPE_VT_TX = 5;
    public static final int CMC_PD_STATE_CONFERENCE = 1;
    public static final int CMC_PD_STATE_EMERGENCY = 2;
    public static final int CMC_PD_STATE_NONE = 0;
    public static final int CMC_TYPE_NONE = 0;
    public static final int CMC_TYPE_PD = 1;
    public static final int CMC_TYPE_SD = 2;
    public static final Parcelable.Creator<ImsCallProfile> CREATOR = new Parcelable.Creator<ImsCallProfile>() { // from class: android.telephony.ims.ImsCallProfile.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ImsCallProfile createFromParcel(Parcel parcel) {
            return new ImsCallProfile(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ImsCallProfile[] newArray(int i) {
            return new ImsCallProfile[i];
        }
    };
    public static final int DIALSTRING_NORMAL = 0;
    public static final int DIALSTRING_SS_CONF = 1;
    public static final int DIALSTRING_USSD = 2;
    public static final String EMERGENCY_CALL_RAT_IWLAN = "VoWIFI";
    public static final String EMERGENCY_CALL_RAT_LTE = "VoLTE";
    public static final String EMERGENCY_CALL_RAT_NR = "VoLTE";
    public static final String EVENT_IMSDC_UPDATE_TELECOM_CALLID = "IMSDC_UPDATE-TELECOM-CALLID";
    public static final String EXTRA_ADDITIONAL_CALL_INFO = "AdditionalCallInfo";
    public static final String EXTRA_ADDITIONAL_SIP_INVITE_FIELDS = "android.telephony.ims.extra.ADDITIONAL_SIP_INVITE_FIELDS";
    public static final String EXTRA_ASSERTED_DISPLAY_NAME = "android.telephony.ims.extra.ASSERTED_DISPLAY_NAME";
    public static final String EXTRA_CALL_DISCONNECT_CAUSE = "android.telephony.ims.extra.CALL_DISCONNECT_CAUSE";
    public static final String EXTRA_CALL_MODE_CHANGEABLE = "call_mode_changeable";
    public static final String EXTRA_CALL_NETWORK_TYPE = "android.telephony.ims.extra.CALL_NETWORK_TYPE";

    @Deprecated
    public static final String EXTRA_CALL_RAT_TYPE = "CallRadioTech";

    @Deprecated
    public static final String EXTRA_CALL_RAT_TYPE_ALT = "callRadioTech";
    public static final String EXTRA_CALL_SUBJECT = "android.telephony.ims.extra.CALL_SUBJECT";
    public static final String EXTRA_CHILD_NUMBER = "ChildNum";
    public static final String EXTRA_CNA = "cna";
    public static final String EXTRA_CNAP = "cnap";
    public static final String EXTRA_CODEC = "Codec";

    @SystemApi
    public static final String EXTRA_CONFERENCE = "android.telephony.ims.extra.CONFERENCE";
    public static final String EXTRA_CONFERENCE_AUDIO_QUALITY = "audioQuality";
    public static final String EXTRA_CONFERENCE_AVAIL = "conference_avail";
    public static final String EXTRA_CONFERENCE_DEPRECATED = "conference";
    public static final String EXTRA_CONFERENCE_KEY = "key";
    public static final String EXTRA_CONFERENCE_SIP_ERROR = "sipError";
    public static final String EXTRA_CONFERENCE_VERSTAT = "com.samsung.telephony.extra.ims.VERSTAT";
    public static final String EXTRA_DIALSTRING = "dialstring";
    public static final String EXTRA_DISPLAY_TEXT = "DisplayText";
    public static final String EXTRA_EMERGENCY_CALL = "e_call";

    @SystemApi
    public static final String EXTRA_EXTENDING_TO_CONFERENCE_SUPPORTED = "android.telephony.ims.extra.EXTENDING_TO_CONFERENCE_SUPPORTED";
    public static final String EXTRA_FEATURE_CAPABILITY = "feature_caps";
    public static final String EXTRA_FORWARDED_NUMBER = "android.telephony.ims.extra.FORWARDED_NUMBER";
    public static final String EXTRA_IMS_ECM_SUPPORT = "imsEcmSupport";
    public static final String EXTRA_IMS_EMERGENCY_CALL_RAT = "imsEmergencyRat";
    public static final String EXTRA_IS_BUSINESS_CALL = "android.telephony.ims.extra.IS_BUSINESS_CALL";
    public static final String EXTRA_IS_CALL_PULL = "CallPull";
    public static final String EXTRA_IS_CROSS_SIM_CALL = "android.telephony.ims.extra.IS_CROSS_SIM_CALL";
    public static final String EXTRA_IS_ECALL_CONVERTED_TO_NORMAL = "isECallConvertedToNormal";
    public static final String EXTRA_LOCATION = "android.telephony.ims.extra.LOCATION";
    public static final String EXTRA_OEM_EXTRAS = "android.telephony.ims.extra.OEM_EXTRAS";
    public static final String EXTRA_OI = "oi";
    public static final String EXTRA_OIR = "oir";
    public static final String EXTRA_PICTURE_URL = "android.telephony.ims.extra.PICTURE_URL";
    public static final String EXTRA_PRIORITY = "android.telephony.ims.extra.PRIORITY";
    public static final String EXTRA_REMOTE_URI = "remote_uri";
    public static final String EXTRA_RESUME_HOST_AND_MERGE = "ResumeHostAndMerge";
    public static final String EXTRA_RETRY_CALL_FAIL_NETWORKTYPE = "android.telephony.ims.extra.RETRY_CALL_FAIL_NETWORKTYPE";
    public static final String EXTRA_RETRY_CALL_FAIL_REASON = "android.telephony.ims.extra.RETRY_CALL_FAIL_REASON";
    public static final String EXTRA_USSD = "ussd";
    public static final String EXTRA_VMS = "vms";
    public static final int OIR_DEFAULT = 0;
    public static final int OIR_PRESENTATION_NOT_RESTRICTED = 2;
    public static final int OIR_PRESENTATION_PAYPHONE = 4;
    public static final int OIR_PRESENTATION_RESTRICTED = 1;
    public static final int OIR_PRESENTATION_UNAVAILABLE = 5;
    public static final int OIR_PRESENTATION_UNKNOWN = 3;
    public static final int PRIORITY_NORMAL = 0;
    public static final int PRIORITY_URGENT = 1;
    public static final String PROPERTY_RAW_INVITE_MESSAGE = "RawInviteMessage";
    public static final int SERVICE_TYPE_EMERGENCY = 2;
    public static final int SERVICE_TYPE_NONE = 0;
    public static final int SERVICE_TYPE_NORMAL = 1;
    private static final String TAG = "ImsCallProfile";
    public static final int VERIFICATION_STATUS_FAILED = 2;
    public static final int VERIFICATION_STATUS_NOT_VERIFIED = 0;
    public static final int VERIFICATION_STATUS_PASSED = 1;
    private Set<RtpHeaderExtensionType> mAcceptedRtpHeaderExtensionTypes;
    public Bundle mCallExtras;
    public int mCallType;
    private int mCallerNumberVerificationStatus;
    private int mEmergencyCallRouting;
    private boolean mEmergencyCallTesting;
    private int mEmergencyServiceCategories;
    private List<String> mEmergencyUrns;
    private boolean mHasKnownUserIntentEmergency;
    public ImsStreamMediaProfile mMediaProfile;
    public int mRestrictCause;
    public int mServiceType;

    @Retention(RetentionPolicy.SOURCE)
    public @interface CallRestrictCause {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface VerificationStatus {
    }

    public static int OIRToPresentation(int i) {
        if (i == 1) {
            return 2;
        }
        if (i == 2) {
            return 1;
        }
        int i2 = 4;
        if (i != 4) {
            i2 = 5;
            if (i != 5) {
                return 3;
            }
        }
        return i2;
    }

    public static int getVideoStateFromCallType(int i) {
        if (i == 2) {
            return 0;
        }
        if (i == 4) {
            return 3;
        }
        if (i != 5) {
            return i != 6 ? 0 : 2;
        }
        return 1;
    }

    private static boolean isVideoStateSet(int i, int i2) {
        return (i & i2) == i2;
    }

    public static int presentationToOIR(int i) {
        if (i == 1) {
            return 2;
        }
        if (i == 2) {
            return 1;
        }
        int i2 = 3;
        if (i != 3) {
            i2 = 4;
            if (i != 4) {
                i2 = 5;
                if (i != 5) {
                    return 0;
                }
            }
        }
        return i2;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ImsCallProfile(Parcel parcel) {
        this.mRestrictCause = 0;
        this.mEmergencyServiceCategories = 0;
        this.mEmergencyUrns = new ArrayList();
        this.mEmergencyCallRouting = 0;
        this.mEmergencyCallTesting = false;
        this.mHasKnownUserIntentEmergency = false;
        this.mAcceptedRtpHeaderExtensionTypes = new ArraySet();
        readFromParcel(parcel);
    }

    public ImsCallProfile() {
        this.mRestrictCause = 0;
        this.mEmergencyServiceCategories = 0;
        this.mEmergencyUrns = new ArrayList();
        this.mEmergencyCallRouting = 0;
        this.mEmergencyCallTesting = false;
        this.mHasKnownUserIntentEmergency = false;
        this.mAcceptedRtpHeaderExtensionTypes = new ArraySet();
        this.mServiceType = 1;
        this.mCallType = 1;
        this.mCallExtras = new Bundle();
        this.mMediaProfile = new ImsStreamMediaProfile();
    }

    public ImsCallProfile(int i, int i2) {
        this.mRestrictCause = 0;
        this.mEmergencyServiceCategories = 0;
        this.mEmergencyUrns = new ArrayList();
        this.mEmergencyCallRouting = 0;
        this.mEmergencyCallTesting = false;
        this.mHasKnownUserIntentEmergency = false;
        this.mAcceptedRtpHeaderExtensionTypes = new ArraySet();
        this.mServiceType = i;
        this.mCallType = i2;
        this.mCallExtras = new Bundle();
        this.mMediaProfile = new ImsStreamMediaProfile();
    }

    public ImsCallProfile(int i, int i2, Bundle bundle, ImsStreamMediaProfile imsStreamMediaProfile) {
        this.mRestrictCause = 0;
        this.mEmergencyServiceCategories = 0;
        this.mEmergencyUrns = new ArrayList();
        this.mEmergencyCallRouting = 0;
        this.mEmergencyCallTesting = false;
        this.mHasKnownUserIntentEmergency = false;
        this.mAcceptedRtpHeaderExtensionTypes = new ArraySet();
        this.mServiceType = i;
        this.mCallType = i2;
        this.mCallExtras = bundle;
        this.mMediaProfile = imsStreamMediaProfile;
    }

    public String getCallExtra(String str) {
        return getCallExtra(str, "");
    }

    public String getCallExtra(String str, String str2) {
        Bundle bundle = this.mCallExtras;
        return bundle == null ? str2 : bundle.getString(str, str2);
    }

    public boolean getCallExtraBoolean(String str) {
        return getCallExtraBoolean(str, false);
    }

    public boolean getCallExtraBoolean(String str, boolean z) {
        Bundle bundle = this.mCallExtras;
        return bundle == null ? z : bundle.getBoolean(str, z);
    }

    public int getCallExtraInt(String str) {
        return getCallExtraInt(str, -1);
    }

    public int getCallExtraInt(String str, int i) {
        Bundle bundle = this.mCallExtras;
        return bundle == null ? i : bundle.getInt(str, i);
    }

    public <T extends Parcelable> T getCallExtraParcelable(String str) {
        Bundle bundle = this.mCallExtras;
        if (bundle != null) {
            return (T) bundle.getParcelable(str);
        }
        return null;
    }

    public void setCallExtra(String str, String str2) {
        Bundle bundle = this.mCallExtras;
        if (bundle != null) {
            bundle.putString(str, str2);
        }
    }

    public void setCallExtraBoolean(String str, boolean z) {
        Bundle bundle = this.mCallExtras;
        if (bundle != null) {
            bundle.putBoolean(str, z);
        }
    }

    public void setCallExtraInt(String str, int i) {
        Bundle bundle = this.mCallExtras;
        if (bundle != null) {
            bundle.putInt(str, i);
        }
    }

    public void setCallExtraStringArray(String str, String[] strArr) {
        Bundle bundle = this.mCallExtras;
        if (bundle != null) {
            bundle.putStringArray(str, strArr);
        }
    }

    public void setCallExtraStringArrayList(String str, List<String> list) {
        Bundle bundle = this.mCallExtras;
        if (bundle != null) {
            bundle.putStringArrayList(str, (ArrayList) list);
        }
    }

    public void setCallExtraParcelable(String str, Parcelable parcelable) {
        Bundle bundle = this.mCallExtras;
        if (bundle != null) {
            bundle.putParcelable(str, parcelable);
        }
    }

    public void setCallRestrictCause(int i) {
        this.mRestrictCause = i;
    }

    public void updateCallType(ImsCallProfile imsCallProfile) {
        this.mCallType = imsCallProfile.mCallType;
    }

    public void updateCallExtras(ImsCallProfile imsCallProfile) {
        this.mCallExtras.clear();
        this.mCallExtras = (Bundle) imsCallProfile.mCallExtras.clone();
    }

    public void updateMediaProfile(ImsCallProfile imsCallProfile) {
        this.mMediaProfile = imsCallProfile.mMediaProfile;
    }

    public void setCallerNumberVerificationStatus(int i) {
        this.mCallerNumberVerificationStatus = i;
    }

    public int getCallerNumberVerificationStatus() {
        return this.mCallerNumberVerificationStatus;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{ serviceType=");
        sb.append(this.mServiceType);
        sb.append(", callType=");
        sb.append(this.mCallType);
        sb.append(", restrictCause=");
        sb.append(this.mRestrictCause);
        sb.append(", mediaProfile=");
        ImsStreamMediaProfile imsStreamMediaProfile = this.mMediaProfile;
        sb.append(imsStreamMediaProfile != null ? imsStreamMediaProfile.toString() : PerfettoProtoLogImpl.NULL_STRING);
        sb.append(", emergencyServiceCategories=");
        sb.append(this.mEmergencyServiceCategories);
        sb.append(", emergencyUrns=");
        sb.append(this.mEmergencyUrns);
        sb.append(", emergencyCallRouting=");
        sb.append(this.mEmergencyCallRouting);
        sb.append(", emergencyCallTesting=");
        sb.append(this.mEmergencyCallTesting);
        sb.append(", hasKnownUserIntentEmergency=");
        sb.append(this.mHasKnownUserIntentEmergency);
        sb.append(", mRestrictCause=");
        sb.append(this.mRestrictCause);
        sb.append(", mCallerNumberVerstat= ");
        sb.append(this.mCallerNumberVerificationStatus);
        sb.append(", mAcceptedRtpHeaderExtensions= ");
        sb.append(this.mAcceptedRtpHeaderExtensionTypes);
        sb.append(" }");
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        Bundle bundleMaybeCleanseExtras = maybeCleanseExtras(this.mCallExtras);
        parcel.writeInt(this.mServiceType);
        parcel.writeInt(this.mCallType);
        parcel.writeBundle(bundleMaybeCleanseExtras);
        parcel.writeParcelable(this.mMediaProfile, 0);
        parcel.writeInt(this.mEmergencyServiceCategories);
        parcel.writeStringList(this.mEmergencyUrns);
        parcel.writeInt(this.mEmergencyCallRouting);
        parcel.writeBoolean(this.mEmergencyCallTesting);
        parcel.writeBoolean(this.mHasKnownUserIntentEmergency);
        parcel.writeInt(this.mRestrictCause);
        parcel.writeInt(this.mCallerNumberVerificationStatus);
        parcel.writeArray(this.mAcceptedRtpHeaderExtensionTypes.toArray());
    }

    private void readFromParcel(Parcel parcel) {
        this.mServiceType = parcel.readInt();
        this.mCallType = parcel.readInt();
        this.mCallExtras = parcel.readBundle();
        this.mMediaProfile = (ImsStreamMediaProfile) parcel.readParcelable(ImsStreamMediaProfile.class.getClassLoader(), ImsStreamMediaProfile.class);
        this.mEmergencyServiceCategories = parcel.readInt();
        this.mEmergencyUrns = parcel.createStringArrayList();
        this.mEmergencyCallRouting = parcel.readInt();
        this.mEmergencyCallTesting = parcel.readBoolean();
        this.mHasKnownUserIntentEmergency = parcel.readBoolean();
        this.mRestrictCause = parcel.readInt();
        this.mCallerNumberVerificationStatus = parcel.readInt();
        this.mAcceptedRtpHeaderExtensionTypes = (Set) Arrays.stream(parcel.readArray(RtpHeaderExtensionType.class.getClassLoader(), RtpHeaderExtensionType.class)).map(new Function() { // from class: android.telephony.ims.ImsCallProfile$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ImsCallProfile.lambda$readFromParcel$0(obj);
            }
        }).collect(Collectors.toSet());
    }

    static /* synthetic */ RtpHeaderExtensionType lambda$readFromParcel$0(Object obj) {
        return (RtpHeaderExtensionType) obj;
    }

    public int getServiceType() {
        return this.mServiceType;
    }

    public int getCallType() {
        return this.mCallType;
    }

    public int getRestrictCause() {
        return this.mRestrictCause;
    }

    public Bundle getCallExtras() {
        return this.mCallExtras;
    }

    public Bundle getProprietaryCallExtras() {
        Bundle bundle = this.mCallExtras;
        if (bundle == null) {
            return new Bundle();
        }
        Bundle bundle2 = bundle.getBundle(EXTRA_OEM_EXTRAS);
        if (bundle2 == null) {
            return new Bundle();
        }
        return new Bundle(bundle2);
    }

    public ImsStreamMediaProfile getMediaProfile() {
        return this.mMediaProfile;
    }

    public static int getVideoStateFromImsCallProfile(ImsCallProfile imsCallProfile) {
        int videoStateFromCallType = getVideoStateFromCallType(imsCallProfile.mCallType);
        return (!imsCallProfile.isVideoPaused() || VideoProfile.isAudioOnly(videoStateFromCallType)) ? videoStateFromCallType & (-5) : videoStateFromCallType | 4;
    }

    public static int getCallTypeFromVideoState(int i) {
        boolean zIsVideoStateSet = isVideoStateSet(i, 1);
        boolean zIsVideoStateSet2 = isVideoStateSet(i, 2);
        if (isVideoStateSet(i, 4)) {
            return 7;
        }
        if (zIsVideoStateSet && !zIsVideoStateSet2) {
            return 5;
        }
        if (zIsVideoStateSet || !zIsVideoStateSet2) {
            return (zIsVideoStateSet && zIsVideoStateSet2) ? 4 : 2;
        }
        return 6;
    }

    public static int presentationToOir(int i) {
        return presentationToOIR(i);
    }

    public boolean isVideoPaused() {
        return this.mMediaProfile.mVideoDirection == 0;
    }

    public boolean isVideoCall() {
        return VideoProfile.isVideo(getVideoStateFromCallType(this.mCallType));
    }

    private Bundle maybeCleanseExtras(Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        int size = bundle.size();
        Bundle bundleFilterValues = TelephonyUtils.filterValues(bundle);
        int size2 = bundleFilterValues.size();
        if (size != size2) {
            Log.i(TAG, "maybeCleanseExtras: " + (size - size2) + " extra values were removed - only primitive types and system parcelables are permitted.");
        }
        return bundleFilterValues;
    }

    public void setEmergencyCallInfo(EmergencyNumber emergencyNumber, boolean z) {
        setEmergencyServiceCategories(emergencyNumber.getEmergencyServiceCategoryBitmaskInternalDial());
        setEmergencyUrns(emergencyNumber.getEmergencyUrns());
        setEmergencyCallRouting(emergencyNumber.getEmergencyCallRouting());
        setEmergencyCallTesting(emergencyNumber.getEmergencyNumberSourceBitmask() == 32);
        setHasKnownUserIntentEmergency(z);
    }

    public void setEmergencyServiceCategories(int i) {
        this.mEmergencyServiceCategories = i;
    }

    public void setEmergencyUrns(List<String> list) {
        this.mEmergencyUrns = list;
    }

    public void setEmergencyCallRouting(int i) {
        this.mEmergencyCallRouting = i;
    }

    public void setEmergencyCallTesting(boolean z) {
        this.mEmergencyCallTesting = z;
    }

    public void setHasKnownUserIntentEmergency(boolean z) {
        this.mHasKnownUserIntentEmergency = z;
    }

    public int getEmergencyServiceCategories() {
        return this.mEmergencyServiceCategories;
    }

    public List<String> getEmergencyUrns() {
        return this.mEmergencyUrns;
    }

    public int getEmergencyCallRouting() {
        return this.mEmergencyCallRouting;
    }

    public boolean isEmergencyCallTesting() {
        return this.mEmergencyCallTesting;
    }

    public boolean hasKnownUserIntentEmergency() {
        return this.mHasKnownUserIntentEmergency;
    }

    public Set<RtpHeaderExtensionType> getAcceptedRtpHeaderExtensionTypes() {
        return this.mAcceptedRtpHeaderExtensionTypes;
    }

    public void setAcceptedRtpHeaderExtensionTypes(Set<RtpHeaderExtensionType> set) {
        this.mAcceptedRtpHeaderExtensionTypes.clear();
        this.mAcceptedRtpHeaderExtensionTypes.addAll(set);
    }
}
