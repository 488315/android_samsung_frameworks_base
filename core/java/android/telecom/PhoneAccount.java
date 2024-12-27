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
import java.util.List;
import java.util.Objects;
import java.util.Set;

public final class PhoneAccount implements Parcelable {
    public static final int CAPABILITY_ADHOC_CONFERENCE_CALLING = 16384;
    public static final int CAPABILITY_CALL_COMPOSER = 32768;
    public static final int CAPABILITY_CALL_PROVIDER = 2;
    public static final int CAPABILITY_CALL_SUBJECT = 64;
    public static final int CAPABILITY_CONNECTION_MANAGER = 1;

    @SystemApi public static final int CAPABILITY_EMERGENCY_CALLS_ONLY = 128;

    @SystemApi public static final int CAPABILITY_EMERGENCY_PREFERRED = 8192;

    @SystemApi public static final int CAPABILITY_EMERGENCY_VIDEO_CALLING = 512;

    @SystemApi public static final int CAPABILITY_MULTI_USER = 32;
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
    public static final Parcelable.Creator<PhoneAccount> CREATOR =
            new Parcelable.Creator<PhoneAccount>() { // from class: android.telecom.PhoneAccount.1
                @Override // android.os.Parcelable.Creator
                public PhoneAccount createFromParcel(Parcel in) {
                    return new PhoneAccount(in);
                }

                @Override // android.os.Parcelable.Creator
                public PhoneAccount[] newArray(int size) {
                    return new PhoneAccount[size];
                }
            };
    public static final String EXTRA_ADD_SELF_MANAGED_CALLS_TO_INCALLSERVICE =
            "android.telecom.extra.ADD_SELF_MANAGED_CALLS_TO_INCALLSERVICE";
    public static final String EXTRA_ALWAYS_USE_VOIP_AUDIO_MODE =
            "android.telecom.extra.ALWAYS_USE_VOIP_AUDIO_MODE";
    public static final String EXTRA_CALL_SUBJECT_CHARACTER_ENCODING =
            "android.telecom.extra.CALL_SUBJECT_CHARACTER_ENCODING";
    public static final String EXTRA_CALL_SUBJECT_MAX_LENGTH =
            "android.telecom.extra.CALL_SUBJECT_MAX_LENGTH";
    public static final String EXTRA_LOG_SELF_MANAGED_CALLS =
            "android.telecom.extra.LOG_SELF_MANAGED_CALLS";

    @SystemApi
    public static final String EXTRA_PLAY_CALL_RECORDING_TONE =
            "android.telecom.extra.PLAY_CALL_RECORDING_TONE";

    @SystemApi
    public static final String EXTRA_SKIP_CALL_FILTERING =
            "android.telecom.extra.SKIP_CALL_FILTERING";

    @SystemApi public static final String EXTRA_SORT_ORDER = "android.telecom.extra.SORT_ORDER";
    public static final String EXTRA_SUPPORTS_HANDOVER_FROM =
            "android.telecom.extra.SUPPORTS_HANDOVER_FROM";
    public static final String EXTRA_SUPPORTS_HANDOVER_TO =
            "android.telecom.extra.SUPPORTS_HANDOVER_TO";
    public static final String EXTRA_SUPPORTS_VIDEO_CALLING_FALLBACK =
            "android.telecom.extra.SUPPORTS_VIDEO_CALLING_FALLBACK";
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

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PhoneAccount that = (PhoneAccount) o;
        if (this.mCapabilities == that.mCapabilities
                && this.mHighlightColor == that.mHighlightColor
                && this.mSupportedAudioRoutes == that.mSupportedAudioRoutes
                && this.mIsEnabled == that.mIsEnabled
                && Objects.equals(this.mAccountHandle, that.mAccountHandle)
                && Objects.equals(this.mAddress, that.mAddress)
                && Objects.equals(this.mSubscriptionAddress, that.mSubscriptionAddress)
                && Objects.equals(this.mLabel, that.mLabel)
                && Objects.equals(this.mShortDescription, that.mShortDescription)
                && Objects.equals(this.mSupportedUriSchemes, that.mSupportedUriSchemes)
                && areBundlesEqual(this.mExtras, that.mExtras)
                && Objects.equals(this.mGroupId, that.mGroupId)
                && Objects.equals(
                        this.mSimultaneousCallingRestriction,
                        that.mSimultaneousCallingRestriction)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(
                this.mAccountHandle,
                this.mAddress,
                this.mSubscriptionAddress,
                Integer.valueOf(this.mCapabilities),
                Integer.valueOf(this.mHighlightColor),
                this.mLabel,
                this.mShortDescription,
                this.mSupportedUriSchemes,
                Integer.valueOf(this.mSupportedAudioRoutes),
                this.mExtras,
                Boolean.valueOf(this.mIsEnabled),
                this.mGroupId,
                this.mSimultaneousCallingRestriction);
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

        public Builder(PhoneAccountHandle accountHandle, CharSequence label) {
            this.mSupportedAudioRoutes = 31;
            this.mHighlightColor = 0;
            this.mSupportedUriSchemes = new ArrayList();
            this.mIsEnabled = false;
            this.mGroupId = "";
            this.mSimultaneousCallingRestriction = null;
            this.mAccountHandle = accountHandle;
            this.mLabel = label;
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
                this.mSimultaneousCallingRestriction =
                        phoneAccount.getSimultaneousCallingRestriction();
            }
        }

        public Builder setLabel(CharSequence label) {
            this.mLabel = label;
            return this;
        }

        public Builder setAddress(Uri value) {
            this.mAddress = value;
            return this;
        }

        public Builder setSubscriptionAddress(Uri value) {
            this.mSubscriptionAddress = value;
            return this;
        }

        public Builder setCapabilities(int value) {
            this.mCapabilities = value;
            return this;
        }

        public Builder setIcon(Icon icon) {
            this.mIcon = icon;
            return this;
        }

        public Builder setHighlightColor(int value) {
            this.mHighlightColor = value;
            return this;
        }

        public Builder setShortDescription(CharSequence value) {
            this.mShortDescription = value;
            return this;
        }

        public Builder addSupportedUriScheme(String uriScheme) {
            if (!TextUtils.isEmpty(uriScheme) && !this.mSupportedUriSchemes.contains(uriScheme)) {
                this.mSupportedUriSchemes.add(uriScheme);
            }
            return this;
        }

        public Builder setSupportedUriSchemes(List<String> uriSchemes) {
            this.mSupportedUriSchemes.clear();
            if (uriSchemes != null && !uriSchemes.isEmpty()) {
                for (String uriScheme : uriSchemes) {
                    addSupportedUriScheme(uriScheme);
                }
            }
            return this;
        }

        public Builder setExtras(Bundle extras) {
            this.mExtras = extras;
            return this;
        }

        public Builder setIsEnabled(boolean isEnabled) {
            this.mIsEnabled = isEnabled;
            return this;
        }

        @SystemApi
        public Builder setGroupId(String groupId) {
            if (groupId != null) {
                this.mGroupId = groupId;
            } else {
                this.mGroupId = "";
            }
            return this;
        }

        public Builder setSupportedAudioRoutes(int routes) {
            this.mSupportedAudioRoutes = routes;
            return this;
        }

        public Builder setSimultaneousCallingRestriction(Set<PhoneAccountHandle> handles) {
            if (handles == null) {
                throw new IllegalArgumentException(
                        "the Set of PhoneAccountHandles must not be null");
            }
            this.mSimultaneousCallingRestriction = handles;
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
            return new PhoneAccount(
                    this.mAccountHandle,
                    this.mAddress,
                    this.mSubscriptionAddress,
                    this.mCapabilities,
                    this.mIcon,
                    this.mHighlightColor,
                    this.mLabel,
                    this.mShortDescription,
                    this.mSupportedUriSchemes,
                    this.mExtras,
                    this.mSupportedAudioRoutes,
                    this.mIsEnabled,
                    this.mGroupId,
                    this.mSimultaneousCallingRestriction);
        }
    }

    private PhoneAccount(
            PhoneAccountHandle account,
            Uri address,
            Uri subscriptionAddress,
            int capabilities,
            Icon icon,
            int highlightColor,
            CharSequence label,
            CharSequence shortDescription,
            List<String> supportedUriSchemes,
            Bundle extras,
            int supportedAudioRoutes,
            boolean isEnabled,
            String groupId,
            Set<PhoneAccountHandle> simultaneousCallingRestriction) {
        this.mAccountHandle = account;
        this.mAddress = address;
        this.mSubscriptionAddress = subscriptionAddress;
        this.mCapabilities = capabilities;
        this.mIcon = icon;
        this.mHighlightColor = highlightColor;
        this.mLabel = label;
        this.mShortDescription = shortDescription;
        this.mSupportedUriSchemes = Collections.unmodifiableList(supportedUriSchemes);
        this.mExtras = extras;
        this.mSupportedAudioRoutes = supportedAudioRoutes;
        this.mIsEnabled = isEnabled;
        this.mGroupId = groupId;
        this.mSimultaneousCallingRestriction = simultaneousCallingRestriction;
    }

    public static Builder builder(PhoneAccountHandle accountHandle, CharSequence label) {
        return new Builder(accountHandle, label);
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

    public boolean hasCapabilities(int capability) {
        return (this.mCapabilities & capability) == capability;
    }

    public boolean hasAudioRoutes(int routes) {
        return (this.mSupportedAudioRoutes & routes) == routes;
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

    public boolean supportsUriScheme(String uriScheme) {
        if (this.mSupportedUriSchemes == null || uriScheme == null) {
            return false;
        }
        for (String scheme : this.mSupportedUriSchemes) {
            if (scheme != null && scheme.equals(uriScheme)) {
                return true;
            }
        }
        return false;
    }

    public int getHighlightColor() {
        return this.mHighlightColor;
    }

    public void setIsEnabled(boolean isEnabled) {
        this.mIsEnabled = isEnabled;
    }

    public boolean isSelfManaged() {
        return (this.mCapabilities & 2048) == 2048;
    }

    public Set<PhoneAccountHandle> getSimultaneousCallingRestriction() {
        if (this.mSimultaneousCallingRestriction == null) {
            throw new IllegalStateException(
                    "This method can not be called if there is no simultaneous calling restriction."
                        + " See #hasSimultaneousCallingRestriction");
        }
        return this.mSimultaneousCallingRestriction;
    }

    public boolean hasSimultaneousCallingRestriction() {
        return this.mSimultaneousCallingRestriction != null;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
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

    private PhoneAccount(Parcel in) {
        if (in.readInt() > 0) {
            this.mAccountHandle = PhoneAccountHandle.CREATOR.createFromParcel(in);
        } else {
            this.mAccountHandle = null;
        }
        if (in.readInt() > 0) {
            this.mAddress = Uri.CREATOR.createFromParcel(in);
        } else {
            this.mAddress = null;
        }
        if (in.readInt() > 0) {
            this.mSubscriptionAddress = Uri.CREATOR.createFromParcel(in);
        } else {
            this.mSubscriptionAddress = null;
        }
        this.mCapabilities = in.readInt();
        this.mHighlightColor = in.readInt();
        this.mLabel = in.readCharSequence();
        this.mShortDescription = in.readCharSequence();
        this.mSupportedUriSchemes = Collections.unmodifiableList(in.createStringArrayList());
        if (in.readInt() > 0) {
            this.mIcon = Icon.CREATOR.createFromParcel(in);
        } else {
            this.mIcon = null;
        }
        this.mIsEnabled = in.readByte() == 1;
        this.mExtras = in.readBundle();
        this.mGroupId = in.readString();
        this.mSupportedAudioRoutes = in.readInt();
        if (in.readBoolean()) {
            ArrayList arrayList = new ArrayList();
            in.readTypedList(arrayList, PhoneAccountHandle.CREATOR);
            this.mSimultaneousCallingRestriction = new ArraySet(arrayList);
            return;
        }
        this.mSimultaneousCallingRestriction = null;
    }

    public String toString() {
        StringBuilder sb =
                new StringBuilder()
                        .append("[[")
                        .append(this.mIsEnabled ? 'X' : ' ')
                        .append("] PhoneAccount: ")
                        .append(this.mAccountHandle)
                        .append(" Capabilities: ")
                        .append(capabilitiesToString())
                        .append(" Audio Routes: ")
                        .append(audioRoutesToString())
                        .append(" Schemes: ");
        for (String scheme : this.mSupportedUriSchemes) {
            sb.append(scheme).append(" ");
        }
        sb.append(" Extras: ");
        sb.append(Log.maskPii(this.mExtras));
        sb.append(" GroupId: ");
        sb.append(Log.pii(this.mGroupId));
        sb.append(" SC Restrictions: ");
        if (hasSimultaneousCallingRestriction()) {
            sb.append("[ ");
            for (PhoneAccountHandle handle : this.mSimultaneousCallingRestriction) {
                sb.append(handle);
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

    private static boolean areBundlesEqual(Bundle extras, Bundle newExtras) {
        if (extras == null || newExtras == null) {
            return extras == newExtras;
        }
        if (extras.size() != newExtras.size()) {
            return false;
        }
        for (String key : extras.keySet()) {
            if (key != null) {
                Object value = extras.get(key);
                Object newValue = newExtras.get(key);
                if (!Objects.equals(value, newValue)) {
                    return false;
                }
            }
        }
        return true;
    }
}
