package android.telecom;

import android.annotation.SystemApi;
import android.graphics.drawable.Icon;
import android.hardware.gnss.GnssSignalType;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.ArraySet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes4.dex */
public final class PhoneAccount implements Parcelable {
    public static final int CAPABILITY_ADHOC_CONFERENCE_CALLING = 16384;
    public static final int CAPABILITY_CALL_COMPOSER = 32768;
    public static final int CAPABILITY_CALL_PROVIDER = 2;
    public static final int CAPABILITY_CALL_SUBJECT = 64;
    public static final int CAPABILITY_CONNECTION_MANAGER = 1;

    @SystemApi
    public static final int CAPABILITY_EMERGENCY_CALLS_ONLY = 128;

    @SystemApi
    public static final int CAPABILITY_EMERGENCY_PREFERRED = 8192;

    @SystemApi
    public static final int CAPABILITY_EMERGENCY_VIDEO_CALLING = 512;

    @SystemApi
    public static final int CAPABILITY_MULTI_USER = 32;
    public static final int CAPABILITY_PLACE_EMERGENCY_CALLS = 16;
    public static final int CAPABILITY_RTT = 4096;
    public static final int CAPABILITY_SELF_MANAGED = 2048;
    public static final int CAPABILITY_SIM_SUBSCRIPTION = 4;
    public static final int CAPABILITY_SUPPORTS_CALL_STREAMING = 524288;
    public static final int CAPABILITY_SUPPORTS_TRANSACTIONAL_OPERATIONS = 262144;
    public static final int CAPABILITY_SUPPORTS_VIDEO_CALLING = 1024;
    public static final int CAPABILITY_SUPPORTS_VOICE_CALLING_INDICATIONS = 65536;
    public static final int CAPABILITY_VIDEO_CALLING = 8;
    public static final int CAPABILITY_VIDEO_CALLING_RELIES_ON_PRESENCE = 256;
    public static final int CAPABILITY_VOICE_CALLING_AVAILABLE = 131072;
    public static final Parcelable.Creator<PhoneAccount> CREATOR = new Parcelable.Creator<PhoneAccount>() { // from class: android.telecom.PhoneAccount.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PhoneAccount createFromParcel(Parcel parcel) {
            return new PhoneAccount(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PhoneAccount[] newArray(int i) {
            return new PhoneAccount[i];
        }
    };
    public static final String EXTRA_ADD_SELF_MANAGED_CALLS_TO_INCALLSERVICE = "android.telecom.extra.ADD_SELF_MANAGED_CALLS_TO_INCALLSERVICE";
    public static final String EXTRA_ALWAYS_USE_VOIP_AUDIO_MODE = "android.telecom.extra.ALWAYS_USE_VOIP_AUDIO_MODE";
    public static final String EXTRA_CALL_SUBJECT_CHARACTER_ENCODING = "android.telecom.extra.CALL_SUBJECT_CHARACTER_ENCODING";
    public static final String EXTRA_CALL_SUBJECT_MAX_LENGTH = "android.telecom.extra.CALL_SUBJECT_MAX_LENGTH";
    public static final String EXTRA_LOG_SELF_MANAGED_CALLS = "android.telecom.extra.LOG_SELF_MANAGED_CALLS";

    @SystemApi
    @Deprecated
    public static final String EXTRA_PLAY_CALL_RECORDING_TONE = "android.telecom.extra.PLAY_CALL_RECORDING_TONE";

    @SystemApi
    public static final String EXTRA_SKIP_CALL_FILTERING = "android.telecom.extra.SKIP_CALL_FILTERING";

    @SystemApi
    public static final String EXTRA_SORT_ORDER = "android.telecom.extra.SORT_ORDER";
    public static final String EXTRA_SUPPORTS_HANDOVER_FROM = "android.telecom.extra.SUPPORTS_HANDOVER_FROM";
    public static final String EXTRA_SUPPORTS_HANDOVER_TO = "android.telecom.extra.SUPPORTS_HANDOVER_TO";
    public static final String EXTRA_SUPPORTS_VIDEO_CALLING_FALLBACK = "android.telecom.extra.SUPPORTS_VIDEO_CALLING_FALLBACK";
    public static final int NO_HIGHLIGHT_COLOR = 0;
    public static final int NO_ICON_TINT = 0;
    public static final int NO_RESOURCE_ID = -1;
    public static final String SCHEME_SIP = "sip";
    public static final String SCHEME_TEL = "tel";
    public static final String SCHEME_VOICEMAIL = "voicemail";
    private final PhoneAccountHandle mAccountHandle;
    private final Uri mAddress;
    private final int mCapabilities;
    private final Bundle mExtras;
    private String mGroupId;
    private final int mHighlightColor;
    private final Icon mIcon;
    private boolean mIsEnabled;
    private final CharSequence mLabel;
    private final CharSequence mShortDescription;
    private final Set<PhoneAccountHandle> mSimultaneousCallingRestriction;
    private final Uri mSubscriptionAddress;
    private final int mSupportedAudioRoutes;
    private final List<String> mSupportedUriSchemes;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            PhoneAccount phoneAccount = (PhoneAccount) obj;
            if (this.mCapabilities == phoneAccount.mCapabilities && this.mHighlightColor == phoneAccount.mHighlightColor && this.mSupportedAudioRoutes == phoneAccount.mSupportedAudioRoutes && this.mIsEnabled == phoneAccount.mIsEnabled && Objects.equals(this.mAccountHandle, phoneAccount.mAccountHandle) && Objects.equals(this.mAddress, phoneAccount.mAddress) && Objects.equals(this.mSubscriptionAddress, phoneAccount.mSubscriptionAddress) && Objects.equals(this.mLabel, phoneAccount.mLabel) && Objects.equals(this.mShortDescription, phoneAccount.mShortDescription) && Objects.equals(this.mSupportedUriSchemes, phoneAccount.mSupportedUriSchemes) && areBundlesEqual(this.mExtras, phoneAccount.mExtras) && Objects.equals(this.mGroupId, phoneAccount.mGroupId) && Objects.equals(this.mSimultaneousCallingRestriction, phoneAccount.mSimultaneousCallingRestriction)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.mAccountHandle, this.mAddress, this.mSubscriptionAddress, Integer.valueOf(this.mCapabilities), Integer.valueOf(this.mHighlightColor), this.mLabel, this.mShortDescription, this.mSupportedUriSchemes, Integer.valueOf(this.mSupportedAudioRoutes), this.mExtras, Boolean.valueOf(this.mIsEnabled), this.mGroupId, this.mSimultaneousCallingRestriction);
    }

    public static class Builder {
        private PhoneAccountHandle mAccountHandle;
        private Uri mAddress;
        private int mCapabilities;
        private Bundle mExtras;
        private String mGroupId;
        private int mHighlightColor;
        private Icon mIcon;
        private boolean mIsEnabled;
        private CharSequence mLabel;
        private CharSequence mShortDescription;
        private Set<PhoneAccountHandle> mSimultaneousCallingRestriction;
        private Uri mSubscriptionAddress;
        private int mSupportedAudioRoutes;
        private List<String> mSupportedUriSchemes;

        public Builder(PhoneAccountHandle phoneAccountHandle, CharSequence charSequence) {
            this.mSupportedAudioRoutes = 31;
            this.mHighlightColor = 0;
            this.mSupportedUriSchemes = new ArrayList();
            this.mIsEnabled = false;
            this.mGroupId = "";
            this.mSimultaneousCallingRestriction = null;
            this.mAccountHandle = phoneAccountHandle;
            this.mLabel = charSequence;
        }

        public Builder(PhoneAccount phoneAccount) {
            this.mSupportedAudioRoutes = 31;
            this.mHighlightColor = 0;
            this.mSupportedUriSchemes = new ArrayList();
            this.mIsEnabled = false;
            this.mGroupId = "";
            this.mSimultaneousCallingRestriction = null;
            this.mAccountHandle = phoneAccount.getAccountHandle();
            this.mAddress = phoneAccount.getAddress();
            this.mSubscriptionAddress = phoneAccount.getSubscriptionAddress();
            this.mCapabilities = phoneAccount.getCapabilities();
            this.mHighlightColor = phoneAccount.getHighlightColor();
            this.mLabel = phoneAccount.getLabel();
            this.mShortDescription = phoneAccount.getShortDescription();
            this.mSupportedUriSchemes.addAll(phoneAccount.getSupportedUriSchemes());
            this.mIcon = phoneAccount.getIcon();
            this.mIsEnabled = phoneAccount.isEnabled();
            this.mExtras = phoneAccount.getExtras();
            this.mGroupId = phoneAccount.getGroupId();
            this.mSupportedAudioRoutes = phoneAccount.getSupportedAudioRoutes();
            if (phoneAccount.hasSimultaneousCallingRestriction()) {
                this.mSimultaneousCallingRestriction = phoneAccount.getSimultaneousCallingRestriction();
            }
        }

        public Builder setLabel(CharSequence charSequence) {
            this.mLabel = charSequence;
            return this;
        }

        public Builder setAddress(Uri uri) {
            this.mAddress = uri;
            return this;
        }

        public Builder setSubscriptionAddress(Uri uri) {
            this.mSubscriptionAddress = uri;
            return this;
        }

        public Builder setCapabilities(int i) {
            this.mCapabilities = i;
            return this;
        }

        public Builder setIcon(Icon icon) {
            this.mIcon = icon;
            return this;
        }

        public Builder setHighlightColor(int i) {
            this.mHighlightColor = i;
            return this;
        }

        public Builder setShortDescription(CharSequence charSequence) {
            this.mShortDescription = charSequence;
            return this;
        }

        public Builder addSupportedUriScheme(String str) {
            if (!TextUtils.isEmpty(str) && !this.mSupportedUriSchemes.contains(str)) {
                this.mSupportedUriSchemes.add(str);
            }
            return this;
        }

        public Builder setSupportedUriSchemes(List<String> list) {
            this.mSupportedUriSchemes.clear();
            if (list != null && !list.isEmpty()) {
                Iterator<String> it = list.iterator();
                while (it.hasNext()) {
                    addSupportedUriScheme(it.next());
                }
            }
            return this;
        }

        public Builder setExtras(Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }

        public Builder setIsEnabled(boolean z) {
            this.mIsEnabled = z;
            return this;
        }

        @SystemApi
        public Builder setGroupId(String str) {
            if (str != null) {
                this.mGroupId = str;
                return this;
            }
            this.mGroupId = "";
            return this;
        }

        public Builder setSupportedAudioRoutes(int i) {
            this.mSupportedAudioRoutes = i;
            return this;
        }

        public Builder setSimultaneousCallingRestriction(Set<PhoneAccountHandle> set) {
            if (set == null) {
                throw new IllegalArgumentException("the Set of PhoneAccountHandles must not be null");
            }
            this.mSimultaneousCallingRestriction = set;
            return this;
        }

        public Builder clearSimultaneousCallingRestriction() {
            this.mSimultaneousCallingRestriction = null;
            return this;
        }

        public PhoneAccount build() {
            if (this.mSupportedUriSchemes.isEmpty()) {
                addSupportedUriScheme(PhoneAccount.SCHEME_TEL);
            }
            return new PhoneAccount(this.mAccountHandle, this.mAddress, this.mSubscriptionAddress, this.mCapabilities, this.mIcon, this.mHighlightColor, this.mLabel, this.mShortDescription, this.mSupportedUriSchemes, this.mExtras, this.mSupportedAudioRoutes, this.mIsEnabled, this.mGroupId, this.mSimultaneousCallingRestriction);
        }
    }

    private PhoneAccount(PhoneAccountHandle phoneAccountHandle, Uri uri, Uri uri2, int i, Icon icon, int i2, CharSequence charSequence, CharSequence charSequence2, List<String> list, Bundle bundle, int i3, boolean z, String str, Set<PhoneAccountHandle> set) {
        this.mAccountHandle = phoneAccountHandle;
        this.mAddress = uri;
        this.mSubscriptionAddress = uri2;
        this.mCapabilities = i;
        this.mIcon = icon;
        this.mHighlightColor = i2;
        this.mLabel = charSequence;
        this.mShortDescription = charSequence2;
        this.mSupportedUriSchemes = Collections.unmodifiableList(list);
        this.mExtras = bundle;
        this.mSupportedAudioRoutes = i3;
        this.mIsEnabled = z;
        this.mGroupId = str;
        this.mSimultaneousCallingRestriction = set;
    }

    public static Builder builder(PhoneAccountHandle phoneAccountHandle, CharSequence charSequence) {
        return new Builder(phoneAccountHandle, charSequence);
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public PhoneAccountHandle getAccountHandle() {
        return this.mAccountHandle;
    }

    public Uri getAddress() {
        return this.mAddress;
    }

    public Uri getSubscriptionAddress() {
        return this.mSubscriptionAddress;
    }

    public int getCapabilities() {
        return this.mCapabilities;
    }

    public boolean hasCapabilities(int i) {
        return (this.mCapabilities & i) == i;
    }

    public boolean hasAudioRoutes(int i) {
        return (this.mSupportedAudioRoutes & i) == i;
    }

    public CharSequence getLabel() {
        return this.mLabel;
    }

    public CharSequence getShortDescription() {
        return this.mShortDescription;
    }

    public List<String> getSupportedUriSchemes() {
        return this.mSupportedUriSchemes;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    public int getSupportedAudioRoutes() {
        return this.mSupportedAudioRoutes;
    }

    public Icon getIcon() {
        return this.mIcon;
    }

    public boolean isEnabled() {
        return this.mIsEnabled;
    }

    public String getGroupId() {
        return this.mGroupId;
    }

    public boolean supportsUriScheme(String str) {
        List<String> list = this.mSupportedUriSchemes;
        if (list != null && str != null) {
            for (String str2 : list) {
                if (str2 != null && str2.equals(str)) {
                    return true;
                }
            }
        }
        return false;
    }

    public int getHighlightColor() {
        return this.mHighlightColor;
    }

    public void setIsEnabled(boolean z) {
        this.mIsEnabled = z;
    }

    public boolean isSelfManaged() {
        return (this.mCapabilities & 2048) == 2048;
    }

    public Set<PhoneAccountHandle> getSimultaneousCallingRestriction() {
        Set<PhoneAccountHandle> set = this.mSimultaneousCallingRestriction;
        if (set != null) {
            return set;
        }
        throw new IllegalStateException("This method can not be called if there is no simultaneous calling restriction. See #hasSimultaneousCallingRestriction");
    }

    public boolean hasSimultaneousCallingRestriction() {
        return this.mSimultaneousCallingRestriction != null;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        if (this.mAccountHandle == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            this.mAccountHandle.writeToParcel(parcel, i);
        }
        if (this.mAddress == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            this.mAddress.writeToParcel(parcel, i);
        }
        if (this.mSubscriptionAddress == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            this.mSubscriptionAddress.writeToParcel(parcel, i);
        }
        parcel.writeInt(this.mCapabilities);
        parcel.writeInt(this.mHighlightColor);
        parcel.writeCharSequence(this.mLabel);
        parcel.writeCharSequence(this.mShortDescription);
        parcel.writeStringList(this.mSupportedUriSchemes);
        if (this.mIcon == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            this.mIcon.writeToParcel(parcel, i);
        }
        parcel.writeByte(this.mIsEnabled ? (byte) 1 : (byte) 0);
        parcel.writeBundle(this.mExtras);
        parcel.writeString(this.mGroupId);
        parcel.writeInt(this.mSupportedAudioRoutes);
        if (this.mSimultaneousCallingRestriction == null) {
            parcel.writeBoolean(false);
        } else {
            parcel.writeBoolean(true);
            parcel.writeTypedList(this.mSimultaneousCallingRestriction.stream().toList());
        }
    }

    private PhoneAccount(Parcel parcel) {
        if (parcel.readInt() > 0) {
            this.mAccountHandle = PhoneAccountHandle.CREATOR.createFromParcel(parcel);
        } else {
            this.mAccountHandle = null;
        }
        if (parcel.readInt() > 0) {
            this.mAddress = Uri.CREATOR.createFromParcel(parcel);
        } else {
            this.mAddress = null;
        }
        if (parcel.readInt() > 0) {
            this.mSubscriptionAddress = Uri.CREATOR.createFromParcel(parcel);
        } else {
            this.mSubscriptionAddress = null;
        }
        this.mCapabilities = parcel.readInt();
        this.mHighlightColor = parcel.readInt();
        this.mLabel = parcel.readCharSequence();
        this.mShortDescription = parcel.readCharSequence();
        this.mSupportedUriSchemes = Collections.unmodifiableList(parcel.createStringArrayList());
        if (parcel.readInt() > 0) {
            this.mIcon = Icon.CREATOR.createFromParcel(parcel);
        } else {
            this.mIcon = null;
        }
        this.mIsEnabled = parcel.readByte() == 1;
        this.mExtras = parcel.readBundle();
        this.mGroupId = parcel.readString();
        this.mSupportedAudioRoutes = parcel.readInt();
        if (parcel.readBoolean()) {
            ArrayList arrayList = new ArrayList();
            parcel.readTypedList(arrayList, PhoneAccountHandle.CREATOR);
            this.mSimultaneousCallingRestriction = new ArraySet(arrayList);
            return;
        }
        this.mSimultaneousCallingRestriction = null;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[[");
        sb.append(this.mIsEnabled ? 'X' : ' ');
        sb.append("] PhoneAccount: ");
        sb.append(this.mAccountHandle);
        sb.append(" Capabilities: ");
        sb.append(capabilitiesToString());
        sb.append(" Audio Routes: ");
        sb.append(audioRoutesToString());
        sb.append(" Schemes: ");
        Iterator<String> it = this.mSupportedUriSchemes.iterator();
        while (it.hasNext()) {
            sb.append(it.next());
            sb.append(" ");
        }
        sb.append(" Extras: ");
        sb.append(Log.maskPii(this.mExtras));
        sb.append(" GroupId: ");
        sb.append(Log.pii(this.mGroupId));
        sb.append(" SC Restrictions: ");
        if (hasSimultaneousCallingRestriction()) {
            sb.append("[ ");
            Iterator<PhoneAccountHandle> it2 = this.mSimultaneousCallingRestriction.iterator();
            while (it2.hasNext()) {
                sb.append(it2.next());
                sb.append(" ");
            }
            sb.append(NavigationBarInflaterView.SIZE_MOD_END);
        } else {
            sb.append("[NONE]");
        }
        sb.append(NavigationBarInflaterView.SIZE_MOD_END);
        return sb.toString();
    }

    public String capabilitiesToString() {
        StringBuilder sb = new StringBuilder();
        if (hasCapabilities(2048)) {
            sb.append("SelfManaged ");
        }
        if (hasCapabilities(1024)) {
            sb.append("SuppVideo ");
        }
        if (hasCapabilities(8)) {
            sb.append("Video ");
        }
        if (hasCapabilities(256)) {
            sb.append("Presence ");
        }
        if (hasCapabilities(2)) {
            sb.append("CallProvider ");
        }
        if (hasCapabilities(64)) {
            sb.append("CallSubject ");
        }
        if (hasCapabilities(1)) {
            sb.append("ConnectionMgr ");
        }
        if (hasCapabilities(128)) {
            sb.append("EmergOnly ");
        }
        if (hasCapabilities(32)) {
            sb.append("MultiUser ");
        }
        if (hasCapabilities(16)) {
            sb.append("PlaceEmerg ");
        }
        if (hasCapabilities(8192)) {
            sb.append("EmerPrefer ");
        }
        if (hasCapabilities(512)) {
            sb.append("EmergVideo ");
        }
        if (hasCapabilities(4)) {
            sb.append("SimSub ");
        }
        if (hasCapabilities(4096)) {
            sb.append("Rtt ");
        }
        if (hasCapabilities(16384)) {
            sb.append("AdhocConf ");
        }
        if (hasCapabilities(32768)) {
            sb.append("CallComposer ");
        }
        if (hasCapabilities(65536)) {
            sb.append("SuppVoice ");
        }
        if (hasCapabilities(131072)) {
            sb.append("Voice ");
        }
        if (hasCapabilities(262144)) {
            sb.append("TransactOps ");
        }
        if (hasCapabilities(524288)) {
            sb.append("Stream ");
        }
        return sb.toString();
    }

    private String audioRoutesToString() {
        StringBuilder sb = new StringBuilder();
        if (hasAudioRoutes(2)) {
            sb.append(GnssSignalType.CODE_TYPE_B);
        }
        if (hasAudioRoutes(1)) {
            sb.append("E");
        }
        if (hasAudioRoutes(8)) {
            sb.append(GnssSignalType.CODE_TYPE_S);
        }
        if (hasAudioRoutes(4)) {
            sb.append(GnssSignalType.CODE_TYPE_W);
        }
        return sb.toString();
    }

    private static boolean areBundlesEqual(Bundle bundle, Bundle bundle2) {
        if (bundle == null || bundle2 == null) {
            return bundle == bundle2;
        }
        if (bundle.size() != bundle2.size()) {
            return false;
        }
        for (String str : bundle.keySet()) {
            if (str != null && !Objects.equals(bundle.get(str), bundle2.get(str))) {
                return false;
            }
        }
        return true;
    }
}
