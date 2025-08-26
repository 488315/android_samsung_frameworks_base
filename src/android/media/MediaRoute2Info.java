package android.media;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.NtpTrustedTime;
import com.android.internal.util.Preconditions;
import java.io.PrintWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/* loaded from: classes2.dex */
public final class MediaRoute2Info implements Parcelable {
    public static final int CONNECTION_STATE_CONNECTED = 2;
    public static final int CONNECTION_STATE_CONNECTING = 1;
    public static final int CONNECTION_STATE_DISCONNECTED = 0;
    public static final Parcelable.Creator<MediaRoute2Info> CREATOR = new Parcelable.Creator<MediaRoute2Info>() { // from class: android.media.MediaRoute2Info.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MediaRoute2Info createFromParcel(Parcel parcel) {
            return new MediaRoute2Info(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MediaRoute2Info[] newArray(int i) {
            return new MediaRoute2Info[i];
        }
    };
    public static final String FEATURE_LIVE_AUDIO = "android.media.route.feature.LIVE_AUDIO";
    public static final String FEATURE_LIVE_VIDEO = "android.media.route.feature.LIVE_VIDEO";
    public static final String FEATURE_LOCAL_PLAYBACK = "android.media.route.feature.LOCAL_PLAYBACK";
    public static final String FEATURE_REMOTE_AUDIO_PLAYBACK = "android.media.route.feature.REMOTE_AUDIO_PLAYBACK";
    public static final String FEATURE_REMOTE_GROUP_PLAYBACK = "android.media.route.feature.REMOTE_GROUP_PLAYBACK";
    public static final String FEATURE_REMOTE_PLAYBACK = "android.media.route.feature.REMOTE_PLAYBACK";
    public static final String FEATURE_REMOTE_VIDEO_PLAYBACK = "android.media.route.feature.REMOTE_VIDEO_PLAYBACK";
    private static final int FLAG_ROUTING_TYPE_ALL = 7;
    public static final int FLAG_ROUTING_TYPE_REMOTE = 4;
    public static final int FLAG_ROUTING_TYPE_SYSTEM_AUDIO = 1;
    public static final int FLAG_ROUTING_TYPE_SYSTEM_VIDEO = 2;
    public static final int PLAYBACK_VOLUME_FIXED = 0;
    public static final int PLAYBACK_VOLUME_VARIABLE = 1;
    public static final String ROUTE_ID_DEFAULT = "DEFAULT_ROUTE";
    public static final String ROUTE_ID_DEVICE = "DEVICE_ROUTE";
    public static final int SUITABILITY_STATUS_NOT_SUITABLE_FOR_TRANSFER = 2;
    public static final int SUITABILITY_STATUS_SUITABLE_FOR_DEFAULT_TRANSFER = 0;
    public static final int SUITABILITY_STATUS_SUITABLE_FOR_MANUAL_TRANSFER = 1;
    public static final int TYPE_AUX_LINE = 19;
    public static final int TYPE_BLE_HEADSET = 26;
    public static final int TYPE_BLUETOOTH_A2DP = 8;
    public static final int TYPE_BUILTIN_SPEAKER = 2;
    public static final int TYPE_DOCK = 13;
    public static final int TYPE_GROUP = 2000;
    public static final int TYPE_HDMI = 9;
    public static final int TYPE_HDMI_ARC = 10;
    public static final int TYPE_HDMI_EARC = 29;
    public static final int TYPE_HEARING_AID = 23;
    public static final int TYPE_LINE_ANALOG = 5;
    public static final int TYPE_LINE_DIGITAL = 6;
    public static final int TYPE_MULTICHANNEL_SPEAKER_GROUP = 32;
    public static final int TYPE_REMOTE_AUDIO_VIDEO_RECEIVER = 1003;
    public static final int TYPE_REMOTE_CAR = 1008;
    public static final int TYPE_REMOTE_COMPUTER = 1006;
    public static final int TYPE_REMOTE_GAME_CONSOLE = 1007;
    public static final int TYPE_REMOTE_SMARTPHONE = 1010;
    public static final int TYPE_REMOTE_SMARTWATCH = 1009;
    public static final int TYPE_REMOTE_SPEAKER = 1002;
    public static final int TYPE_REMOTE_SUBMIX = 25;
    public static final int TYPE_REMOTE_TABLET = 1004;
    public static final int TYPE_REMOTE_TABLET_DOCKED = 1005;
    public static final int TYPE_REMOTE_TV = 1001;
    public static final int TYPE_UNKNOWN = 0;
    public static final int TYPE_USB_ACCESSORY = 12;
    public static final int TYPE_USB_DEVICE = 11;
    public static final int TYPE_USB_HEADSET = 22;
    public static final int TYPE_WIRED_HEADPHONES = 4;
    public static final int TYPE_WIRED_HEADSET = 3;
    private final String mAddress;
    private final Set<String> mAllowedPackages;
    private final String mClientPackageName;
    private final int mConnectionState;
    private final Set<String> mDeduplicationIds;
    private final CharSequence mDescription;
    private final Bundle mExtras;
    private final List<String> mFeatures;
    private final Uri mIconUri;
    private final String mId;
    private final boolean mIsSystem;
    private final boolean mIsVisibilityRestricted;
    private final CharSequence mName;
    private final String mProviderId;
    private final String mProviderPackageName;
    private final List<Set<String>> mRequiredPermissions;
    private final int mRoutingTypeFlags;
    private final int mSuitabilityStatus;
    private final int mType;
    private final int mVolume;
    private final int mVolumeHandling;
    private final int mVolumeMax;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ConnectionState {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface PlaybackVolume {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface RoutingType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SuitabilityStatus {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    MediaRoute2Info(Builder builder) {
        this.mId = builder.mId;
        this.mName = builder.mName;
        this.mFeatures = builder.mFeatures;
        this.mType = builder.mType;
        this.mRoutingTypeFlags = builder.mRoutingTypeFlags;
        this.mIsSystem = builder.mIsSystem;
        this.mIconUri = builder.mIconUri;
        this.mDescription = builder.mDescription;
        this.mConnectionState = builder.mConnectionState;
        this.mClientPackageName = builder.mClientPackageName;
        this.mProviderPackageName = builder.mProviderPackageName;
        this.mVolumeHandling = builder.mVolumeHandling;
        this.mVolumeMax = builder.mVolumeMax;
        this.mVolume = builder.mVolume;
        this.mAddress = builder.mAddress;
        this.mDeduplicationIds = builder.mDeduplicationIds;
        this.mExtras = builder.mExtras;
        this.mProviderId = builder.mProviderId;
        this.mIsVisibilityRestricted = builder.mIsVisibilityRestricted;
        this.mAllowedPackages = builder.mAllowedPackages;
        this.mSuitabilityStatus = builder.mSuitabilityStatus;
        this.mRequiredPermissions = List.copyOf(builder.mRequiredPermissions);
    }

    MediaRoute2Info(Parcel parcel) {
        this.mId = parcel.readString();
        Preconditions.checkArgument(!TextUtils.isEmpty(r0));
        this.mName = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mFeatures = parcel.createStringArrayList();
        this.mType = parcel.readInt();
        this.mRoutingTypeFlags = validateRoutingTypeFlags(parcel.readInt());
        this.mIsSystem = parcel.readBoolean();
        this.mIconUri = (Uri) parcel.readParcelable(null, Uri.class);
        this.mDescription = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mConnectionState = parcel.readInt();
        this.mClientPackageName = parcel.readString();
        this.mProviderPackageName = parcel.readString();
        this.mVolumeHandling = parcel.readInt();
        this.mVolumeMax = parcel.readInt();
        this.mVolume = parcel.readInt();
        this.mAddress = parcel.readString();
        this.mDeduplicationIds = Set.of((Object[]) parcel.readStringArray());
        this.mExtras = parcel.readBundle();
        this.mProviderId = parcel.readString();
        this.mIsVisibilityRestricted = parcel.readBoolean();
        this.mAllowedPackages = Set.of((Object[]) parcel.createString8Array());
        ArrayList arrayList = new ArrayList();
        int i = parcel.readInt();
        for (int i2 = 0; i2 < i; i2++) {
            arrayList.add(Set.of((Object[]) parcel.createString8Array()));
        }
        this.mRequiredPermissions = List.copyOf(arrayList);
        this.mSuitabilityStatus = parcel.readInt();
    }

    public String getId() {
        if (!TextUtils.isEmpty(this.mProviderId)) {
            return MediaRouter2Utils.toUniqueId(this.mProviderId, this.mId);
        }
        return this.mId;
    }

    public CharSequence getName() {
        return this.mName;
    }

    public List<String> getFeatures() {
        return this.mFeatures;
    }

    public int getType() {
        return this.mType;
    }

    public int getSupportedRoutingTypes() {
        return this.mRoutingTypeFlags;
    }

    public boolean isSystemRoute() {
        return this.mIsSystem;
    }

    public Uri getIconUri() {
        return this.mIconUri;
    }

    public CharSequence getDescription() {
        return this.mDescription;
    }

    public int getConnectionState() {
        return this.mConnectionState;
    }

    public String getClientPackageName() {
        return this.mClientPackageName;
    }

    public String getProviderPackageName() {
        return this.mProviderPackageName;
    }

    public int getVolumeHandling() {
        return this.mVolumeHandling;
    }

    public int getVolumeMax() {
        return this.mVolumeMax;
    }

    public int getVolume() {
        return this.mVolume;
    }

    public String getAddress() {
        return this.mAddress;
    }

    public Set<String> getDeduplicationIds() {
        return this.mDeduplicationIds;
    }

    public Bundle getExtras() {
        if (this.mExtras == null) {
            return null;
        }
        return new Bundle(this.mExtras);
    }

    public String getOriginalId() {
        return this.mId;
    }

    public String getProviderId() {
        return this.mProviderId;
    }

    public boolean hasAnyFeatures(Collection<String> collection) {
        Objects.requireNonNull(collection, "features must not be null");
        Iterator<String> it = collection.iterator();
        while (it.hasNext()) {
            if (getFeatures().contains(it.next())) {
                return true;
            }
        }
        return false;
    }

    public boolean hasAllFeatures(Collection<String> collection) {
        Objects.requireNonNull(collection, "features must not be null");
        Iterator<String> it = collection.iterator();
        while (it.hasNext()) {
            if (!getFeatures().contains(it.next())) {
                return false;
            }
        }
        return true;
    }

    public boolean supportsSystemMediaRouting() {
        return (this.mRoutingTypeFlags & 3) != 0;
    }

    public boolean supportsRemoteRouting() {
        return (this.mRoutingTypeFlags & 4) != 0;
    }

    public boolean isValid() {
        return (TextUtils.isEmpty(getId()) || TextUtils.isEmpty(getName()) || TextUtils.isEmpty(getProviderId())) ? false : true;
    }

    public boolean isVisibleTo(String str) {
        return !this.mIsVisibilityRestricted || TextUtils.equals(getProviderPackageName(), str) || this.mAllowedPackages.contains(str);
    }

    public List<Set<String>> getRequiredPermissions() {
        return this.mRequiredPermissions;
    }

    public boolean isSystemRouteType() {
        int i = this.mType;
        if (i == 2 || i == 3 || i == 4 || i == 5 || i == 6 || i == 19 || i == 26 || i == 29 || i == 22 || i == 23) {
            return true;
        }
        switch (i) {
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
                return true;
            default:
                return false;
        }
    }

    public int getSuitabilityStatus() {
        return this.mSuitabilityStatus;
    }

    public void dump(PrintWriter printWriter, String str) {
        printWriter.println(str + "MediaRoute2Info");
        String str2 = str + "  ";
        printWriter.println(str2 + "mId=" + this.mId);
        printWriter.println(str2 + "mName=" + ((Object) this.mName));
        printWriter.println(str2 + "mFeatures=" + this.mFeatures);
        printWriter.println(str2 + "mType=" + getDeviceTypeString(this.mType));
        printWriter.println(str2 + "mRoutingTypeFlags=" + getRoutingTypeFlagsString(this.mRoutingTypeFlags));
        printWriter.println(str2 + "mIsSystem=" + this.mIsSystem);
        printWriter.println(str2 + "mIconUri=" + this.mIconUri);
        printWriter.println(str2 + "mDescription=" + ((Object) this.mDescription));
        printWriter.println(str2 + "mConnectionState=" + this.mConnectionState);
        printWriter.println(str2 + "mClientPackageName=" + this.mClientPackageName);
        printWriter.println(str2 + "mProviderPackageName=" + this.mProviderPackageName);
        dumpVolume(printWriter, str2);
        printWriter.println(str2 + "mAddress=" + this.mAddress);
        printWriter.println(str2 + "mDeduplicationIds=" + this.mDeduplicationIds);
        printWriter.println(str2 + "mExtras=" + this.mExtras);
        printWriter.println(str2 + "mProviderId=" + this.mProviderId);
        printWriter.println(str2 + "mIsVisibilityRestricted=" + this.mIsVisibilityRestricted);
        printWriter.println(str2 + "mAllowedPackages=" + this.mAllowedPackages);
        printWriter.println(str2 + "mSuitabilityStatus=" + this.mSuitabilityStatus);
        printWriter.println(str2 + "mRequiredPermissions=" + this.mRequiredPermissions);
    }

    private void dumpVolume(PrintWriter printWriter, String str) {
        printWriter.println(str + getVolumeString(this.mVolume, this.mVolumeMax, this.mVolumeHandling));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MediaRoute2Info)) {
            return false;
        }
        MediaRoute2Info mediaRoute2Info = (MediaRoute2Info) obj;
        return Objects.equals(this.mId, mediaRoute2Info.mId) && Objects.equals(this.mName, mediaRoute2Info.mName) && Objects.equals(this.mFeatures, mediaRoute2Info.mFeatures) && this.mType == mediaRoute2Info.mType && this.mRoutingTypeFlags == mediaRoute2Info.mRoutingTypeFlags && this.mIsSystem == mediaRoute2Info.mIsSystem && Objects.equals(this.mIconUri, mediaRoute2Info.mIconUri) && Objects.equals(this.mDescription, mediaRoute2Info.mDescription) && this.mConnectionState == mediaRoute2Info.mConnectionState && Objects.equals(this.mClientPackageName, mediaRoute2Info.mClientPackageName) && Objects.equals(this.mProviderPackageName, mediaRoute2Info.mProviderPackageName) && this.mVolumeHandling == mediaRoute2Info.mVolumeHandling && this.mVolumeMax == mediaRoute2Info.mVolumeMax && this.mVolume == mediaRoute2Info.mVolume && Objects.equals(this.mAddress, mediaRoute2Info.mAddress) && Objects.equals(this.mDeduplicationIds, mediaRoute2Info.mDeduplicationIds) && Objects.equals(this.mProviderId, mediaRoute2Info.mProviderId) && this.mIsVisibilityRestricted == mediaRoute2Info.mIsVisibilityRestricted && Objects.equals(this.mAllowedPackages, mediaRoute2Info.mAllowedPackages) && Objects.equals(this.mRequiredPermissions, mediaRoute2Info.mRequiredPermissions) && this.mSuitabilityStatus == mediaRoute2Info.mSuitabilityStatus;
    }

    public int hashCode() {
        return Objects.hash(this.mId, this.mName, this.mFeatures, Integer.valueOf(this.mType), Integer.valueOf(this.mRoutingTypeFlags), Boolean.valueOf(this.mIsSystem), this.mIconUri, this.mDescription, Integer.valueOf(this.mConnectionState), this.mClientPackageName, this.mProviderPackageName, Integer.valueOf(this.mVolumeHandling), Integer.valueOf(this.mVolumeMax), Integer.valueOf(this.mVolume), this.mAddress, this.mDeduplicationIds, this.mProviderId, Boolean.valueOf(this.mIsVisibilityRestricted), this.mAllowedPackages, this.mRequiredPermissions, Integer.valueOf(this.mSuitabilityStatus));
    }

    public String toString() {
        return "MediaRoute2Info{ id=" + getId() + ", name=" + getName() + ", type=" + getDeviceTypeString(getType()) + ", routingTypes=" + getRoutingTypeFlagsString(getSupportedRoutingTypes()) + ", isSystem=" + isSystemRoute() + ", features=" + getFeatures() + ", iconUri=" + getIconUri() + ", description=" + getDescription() + ", connectionState=" + getConnectionState() + ", clientPackageName=" + getClientPackageName() + ", " + getVolumeString(this.mVolume, this.mVolumeMax, this.mVolumeHandling) + ", address=" + getAddress() + ", deduplicationIds=" + String.join(",", getDeduplicationIds()) + ", providerId=" + getProviderId() + ", isVisibilityRestricted=" + this.mIsVisibilityRestricted + ", allowedPackages=" + String.join(",", this.mAllowedPackages) + ", mRequiredPermissions=" + ((String) this.mRequiredPermissions.stream().map(new Function() { // from class: android.media.MediaRoute2Info$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return String.join(",", (Set) obj);
            }
        }).collect(Collectors.joining("),(", NavigationBarInflaterView.KEY_CODE_START, NavigationBarInflaterView.KEY_CODE_END))) + ", suitabilityStatus=" + this.mSuitabilityStatus + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mId);
        TextUtils.writeToParcel(this.mName, parcel, i);
        parcel.writeStringList(this.mFeatures);
        parcel.writeInt(this.mType);
        parcel.writeInt(this.mRoutingTypeFlags);
        parcel.writeBoolean(this.mIsSystem);
        parcel.writeParcelable(this.mIconUri, i);
        TextUtils.writeToParcel(this.mDescription, parcel, i);
        parcel.writeInt(this.mConnectionState);
        parcel.writeString(this.mClientPackageName);
        parcel.writeString(this.mProviderPackageName);
        parcel.writeInt(this.mVolumeHandling);
        parcel.writeInt(this.mVolumeMax);
        parcel.writeInt(this.mVolume);
        parcel.writeString(this.mAddress);
        Set<String> set = this.mDeduplicationIds;
        parcel.writeStringArray((String[]) set.toArray(new String[set.size()]));
        parcel.writeBundle(this.mExtras);
        parcel.writeString(this.mProviderId);
        parcel.writeBoolean(this.mIsVisibilityRestricted);
        parcel.writeString8Array((String[]) this.mAllowedPackages.toArray(new String[0]));
        parcel.writeInt(this.mRequiredPermissions.size());
        Iterator<Set<String>> it = this.mRequiredPermissions.iterator();
        while (it.hasNext()) {
            parcel.writeString8Array((String[]) it.next().toArray(new String[0]));
        }
        parcel.writeInt(this.mSuitabilityStatus);
    }

    static String getVolumeString(int i, int i2, int i3) {
        String str;
        if (i3 == 0) {
            str = "FIXED";
        } else if (i3 == 1) {
            str = "VARIABLE";
        } else {
            str = "UNKNOWN";
        }
        return String.format(Locale.US, "volume(current=%d, max=%d, handling=%s(%d))", Integer.valueOf(i), Integer.valueOf(i2), str, Integer.valueOf(i3));
    }

    private static String getDeviceTypeString(int i) {
        if (i == 2) {
            return "BUILTIN_SPEAKER";
        }
        if (i == 3) {
            return "WIRED_HEADSET";
        }
        if (i == 4) {
            return "WIRED_HEADPHONES";
        }
        if (i == 5) {
            return "LINE_ANALOG";
        }
        if (i == 6) {
            return "LINE_DIGITAL";
        }
        if (i == 19) {
            return "AUX_LINE";
        }
        if (i == 29) {
            return "HDMI_EARC";
        }
        if (i == 2000) {
            return "GROUP";
        }
        if (i == 22) {
            return "USB_HEADSET";
        }
        if (i != 23) {
            switch (i) {
                case 8:
                    return "BLUETOOTH_A2DP";
                case 9:
                    return "HDMI";
                case 10:
                    return "HDMI_ARC";
                case 11:
                    return "USB_DEVICE";
                case 12:
                    return "USB_ACCESSORY";
                case 13:
                    return "DOCK";
                default:
                    switch (i) {
                        case 1001:
                            return "REMOTE_TV";
                        case 1002:
                            return "REMOTE_SPEAKER";
                        case 1003:
                            return "REMOTE_AUDIO_VIDEO_RECEIVER";
                        case 1004:
                            return "REMOTE_TABLET";
                        case 1005:
                            return "REMOTE_TABLET_DOCKED";
                        case 1006:
                            return "REMOTE_COMPUTER";
                        case 1007:
                            return "REMOTE_GAME_CONSOLE";
                        case 1008:
                            return "REMOTE_CAR";
                        case 1009:
                            return "REMOTE_SMARTWATCH";
                        case 1010:
                            return "REMOTE_SMARTPHONE";
                        default:
                            return TextUtils.formatSimple("UNKNOWN(%d)", Integer.valueOf(i));
                    }
            }
        }
        return "HEARING_AID";
    }

    private static String getRoutingTypeFlagsString(int i) {
        ArrayList arrayList = new ArrayList();
        if ((i & 1) != 0) {
            arrayList.add("SYSTEM_AUDIO");
        }
        if ((i & 2) != 0) {
            arrayList.add("SYSTEM_VIDEO");
        }
        if ((i & 4) != 0) {
            arrayList.add("REMOTE");
        }
        return String.join(NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER, arrayList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int validateRoutingTypeFlags(int i) {
        if (i != 0 && (i & (-8)) == 0) {
            return i;
        }
        throw new IllegalArgumentException("Invalid routing type flags: " + Integer.toHexString(i));
    }

    public static final class Builder {
        private String mAddress;
        private Set<String> mAllowedPackages;
        private String mClientPackageName;
        private int mConnectionState;
        private Set<String> mDeduplicationIds;
        private CharSequence mDescription;
        private Bundle mExtras;
        private final List<String> mFeatures;
        private Uri mIconUri;
        private final String mId;
        private boolean mIsSystem;
        private boolean mIsVisibilityRestricted;
        private final CharSequence mName;
        private String mProviderId;
        private String mProviderPackageName;
        private List<Set<String>> mRequiredPermissions;
        private int mRoutingTypeFlags;
        private int mSuitabilityStatus;
        private int mType;
        private int mVolume;
        private int mVolumeHandling;
        private int mVolumeMax;

        public Builder(String str, CharSequence charSequence) {
            this.mType = 0;
            this.mRoutingTypeFlags = 4;
            this.mVolumeHandling = 0;
            if (TextUtils.isEmpty(str)) {
                throw new IllegalArgumentException("id must not be empty");
            }
            if (TextUtils.isEmpty(charSequence)) {
                throw new IllegalArgumentException("name must not be empty");
            }
            this.mId = str;
            this.mName = charSequence;
            this.mFeatures = new ArrayList();
            this.mDeduplicationIds = Collections.EMPTY_SET;
            this.mAllowedPackages = Collections.EMPTY_SET;
            this.mSuitabilityStatus = 0;
            this.mRequiredPermissions = Collections.EMPTY_LIST;
        }

        public Builder(MediaRoute2Info mediaRoute2Info) {
            this(mediaRoute2Info.mId, mediaRoute2Info);
        }

        public Builder(String str, MediaRoute2Info mediaRoute2Info) {
            this.mType = 0;
            this.mRoutingTypeFlags = 4;
            this.mVolumeHandling = 0;
            if (TextUtils.isEmpty(str)) {
                throw new IllegalArgumentException("id must not be empty");
            }
            Objects.requireNonNull(mediaRoute2Info, "routeInfo must not be null");
            this.mId = str;
            this.mName = mediaRoute2Info.mName;
            this.mFeatures = new ArrayList(mediaRoute2Info.mFeatures);
            this.mType = mediaRoute2Info.mType;
            this.mRoutingTypeFlags = mediaRoute2Info.mRoutingTypeFlags;
            this.mIsSystem = mediaRoute2Info.mIsSystem;
            this.mIconUri = mediaRoute2Info.mIconUri;
            this.mDescription = mediaRoute2Info.mDescription;
            this.mConnectionState = mediaRoute2Info.mConnectionState;
            this.mClientPackageName = mediaRoute2Info.mClientPackageName;
            this.mProviderPackageName = mediaRoute2Info.mProviderPackageName;
            this.mVolumeHandling = mediaRoute2Info.mVolumeHandling;
            this.mVolumeMax = mediaRoute2Info.mVolumeMax;
            this.mVolume = mediaRoute2Info.mVolume;
            this.mAddress = mediaRoute2Info.mAddress;
            this.mDeduplicationIds = Set.copyOf(mediaRoute2Info.mDeduplicationIds);
            if (mediaRoute2Info.mExtras != null) {
                this.mExtras = new Bundle(mediaRoute2Info.mExtras);
            }
            this.mProviderId = mediaRoute2Info.mProviderId;
            this.mIsVisibilityRestricted = mediaRoute2Info.mIsVisibilityRestricted;
            this.mAllowedPackages = mediaRoute2Info.mAllowedPackages;
            this.mSuitabilityStatus = mediaRoute2Info.mSuitabilityStatus;
            this.mRequiredPermissions = mediaRoute2Info.mRequiredPermissions;
        }

        public Builder addFeature(String str) {
            if (TextUtils.isEmpty(str)) {
                throw new IllegalArgumentException("feature must not be null or empty");
            }
            this.mFeatures.add(str);
            return this;
        }

        public Builder addFeatures(Collection<String> collection) {
            Objects.requireNonNull(collection, "features must not be null");
            Iterator<String> it = collection.iterator();
            while (it.hasNext()) {
                addFeature(it.next());
            }
            return this;
        }

        public Builder clearFeatures() {
            this.mFeatures.clear();
            return this;
        }

        public Builder setType(int i) {
            this.mType = i;
            return this;
        }

        public Builder setSupportedRoutingTypes(int i) {
            this.mRoutingTypeFlags = MediaRoute2Info.validateRoutingTypeFlags(i);
            return this;
        }

        public Builder setSystemRoute(boolean z) {
            this.mIsSystem = z;
            return this;
        }

        public Builder setIconUri(Uri uri) {
            this.mIconUri = uri;
            return this;
        }

        public Builder setDescription(CharSequence charSequence) {
            this.mDescription = charSequence;
            return this;
        }

        public Builder setConnectionState(int i) {
            this.mConnectionState = i;
            return this;
        }

        public Builder setClientPackageName(String str) {
            this.mClientPackageName = str;
            return this;
        }

        public Builder setProviderPackageName(String str) {
            this.mProviderPackageName = str;
            return this;
        }

        public Builder setVolumeHandling(int i) {
            this.mVolumeHandling = i;
            return this;
        }

        public Builder setVolumeMax(int i) {
            this.mVolumeMax = i;
            return this;
        }

        public Builder setVolume(int i) {
            this.mVolume = i;
            return this;
        }

        public Builder setAddress(String str) {
            this.mAddress = str;
            return this;
        }

        public Builder setDeduplicationIds(Set<String> set) {
            this.mDeduplicationIds = Set.copyOf(set);
            return this;
        }

        public Builder setExtras(Bundle bundle) {
            if (bundle == null) {
                this.mExtras = null;
                return this;
            }
            this.mExtras = new Bundle(bundle);
            return this;
        }

        public Builder setProviderId(String str) {
            if (TextUtils.isEmpty(str)) {
                throw new IllegalArgumentException("providerId must not be null or empty");
            }
            this.mProviderId = str;
            return this;
        }

        public Builder setVisibilityPublic() {
            this.mIsVisibilityRestricted = false;
            this.mAllowedPackages = Collections.EMPTY_SET;
            this.mRequiredPermissions = Collections.EMPTY_LIST;
            return this;
        }

        public Builder setVisibilityRestricted(Set<String> set) {
            this.mIsVisibilityRestricted = true;
            this.mAllowedPackages = Set.copyOf(set);
            return this;
        }

        public Builder setRequiredPermissions(Set<String> set) {
            return setRequiredPermissions(List.of(set));
        }

        public Builder setRequiredPermissions(List<Set<String>> list) {
            this.mRequiredPermissions = List.copyOf(list);
            return this;
        }

        public Builder setSuitabilityStatus(int i) {
            this.mSuitabilityStatus = i;
            return this;
        }

        public MediaRoute2Info build() {
            if (this.mFeatures.isEmpty()) {
                throw new IllegalArgumentException("features must not be empty!");
            }
            return new MediaRoute2Info(this);
        }
    }
}
