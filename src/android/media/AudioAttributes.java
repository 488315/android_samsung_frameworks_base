package android.media;

import android.annotation.SystemApi;
import android.audio.policy.configuration.V7_0.AudioUsage;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.IntArray;
import android.util.Log;
import android.util.SparseIntArray;
import android.util.proto.ProtoOutputStream;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import com.samsung.android.media.AudioTag;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes2.dex */
public final class AudioAttributes implements Parcelable {
    public static final int ALLOW_CAPTURE_BY_ALL = 1;
    public static final int ALLOW_CAPTURE_BY_NONE = 3;
    public static final int ALLOW_CAPTURE_BY_SYSTEM = 2;
    private static final int ALL_PARCEL_FLAGS = 1;
    private static final int ATTR_PARCEL_IS_NULL_BUNDLE = -1977;
    private static final int ATTR_PARCEL_IS_VALID_BUNDLE = 1980;
    private static final IntArray CONTENT_TYPES;
    public static final int CONTENT_TYPE_MOVIE = 3;
    public static final int CONTENT_TYPE_MUSIC = 2;
    public static final int CONTENT_TYPE_SONIFICATION = 4;
    public static final int CONTENT_TYPE_SPEECH = 1;

    @SystemApi
    public static final int CONTENT_TYPE_ULTRASOUND = 1997;
    public static final int CONTENT_TYPE_UNKNOWN = 0;
    public static final Parcelable.Creator<AudioAttributes> CREATOR;
    private static final int FLAG_ALL = 129023;
    private static final int FLAG_ALL_API_SET = 465;
    private static final int FLAG_ALL_PUBLIC = 273;
    public static final int FLAG_AUDIBILITY_ENFORCED = 1;

    @SystemApi
    public static final int FLAG_BEACON = 8;

    @SystemApi
    public static final int FLAG_BYPASS_INTERRUPTION_POLICY = 64;

    @SystemApi
    public static final int FLAG_BYPASS_MUTE = 128;
    public static final int FLAG_CALL_REDIRECTION = 65536;
    public static final int FLAG_CAPTURE_PRIVATE = 8192;
    public static final int FLAG_CONTENT_SPATIALIZED = 16384;
    public static final int FLAG_DEEP_BUFFER = 512;
    public static final int FLAG_HW_AV_SYNC = 16;

    @SystemApi
    public static final int FLAG_HW_HOTWORD = 32;
    public static final int FLAG_LOW_LATENCY = 256;
    public static final int FLAG_MUTE_HAPTIC = 2048;
    public static final int FLAG_NEVER_SPATIALIZE = 32768;
    public static final int FLAG_NO_MEDIA_PROJECTION = 1024;
    public static final int FLAG_NO_SYSTEM_CAPTURE = 4096;
    public static final int FLAG_SCO = 4;
    public static final int FLAG_SECURE = 2;
    public static final int FLATTEN_TAGS = 1;
    public static final IntArray SDK_USAGES;
    public static final int SPATIALIZATION_BEHAVIOR_AUTO = 0;
    public static final int SPATIALIZATION_BEHAVIOR_NEVER = 1;
    public static final int SUPPRESSIBLE_ALARM = 4;
    public static final int SUPPRESSIBLE_CALL = 2;
    public static final int SUPPRESSIBLE_MEDIA = 5;
    public static final int SUPPRESSIBLE_NEVER = 3;
    public static final int SUPPRESSIBLE_NOTIFICATION = 1;
    public static final int SUPPRESSIBLE_SYSTEM = 6;
    public static final SparseIntArray SUPPRESSIBLE_USAGES;
    private static final int SYSTEM_USAGE_OFFSET = 1000;
    private static final String TAG = "AudioAttributes";
    public static final int USAGE_ALARM = 4;

    @SystemApi
    public static final int USAGE_ANNOUNCEMENT = 1003;
    public static final int USAGE_ASSISTANCE_ACCESSIBILITY = 11;
    public static final int USAGE_ASSISTANCE_NAVIGATION_GUIDANCE = 12;
    public static final int USAGE_ASSISTANCE_SONIFICATION = 13;
    public static final int USAGE_ASSISTANT = 16;

    @SystemApi
    public static final int USAGE_CALL_ASSISTANT = 17;

    @SystemApi
    public static final int USAGE_EMERGENCY = 1000;
    public static final int USAGE_GAME = 14;
    private static final int USAGE_INVALID = -1;
    public static final int USAGE_MEDIA = 1;
    public static final int USAGE_NOTIFICATION = 5;

    @Deprecated
    public static final int USAGE_NOTIFICATION_COMMUNICATION_DELAYED = 9;

    @Deprecated
    public static final int USAGE_NOTIFICATION_COMMUNICATION_INSTANT = 8;

    @Deprecated
    public static final int USAGE_NOTIFICATION_COMMUNICATION_REQUEST = 7;
    public static final int USAGE_NOTIFICATION_EVENT = 10;
    public static final int USAGE_NOTIFICATION_RINGTONE = 6;

    @SystemApi
    public static final int USAGE_SAFETY = 1001;

    @SystemApi
    public static final int USAGE_SPEAKER_CLEANUP = 1004;
    public static final int USAGE_UNKNOWN = 0;

    @SystemApi
    public static final int USAGE_VEHICLE_STATUS = 1002;
    public static final int USAGE_VIRTUAL_SOURCE = 15;
    public static final int USAGE_VOICE_COMMUNICATION = 2;
    public static final int USAGE_VOICE_COMMUNICATION_SIGNALLING = 3;
    private static final Map<String, Integer> sXsdStringToUsage;
    private Bundle mBundle;
    private int mContentType;
    private int mFlags;
    private String mFormattedTags;
    private int mSource;
    private HashSet<String> mTags;
    private int mUsage;

    @Retention(RetentionPolicy.SOURCE)
    public @interface AttrInternalContentType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface AttributeContentType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface AttributeSdkUsage {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface AttributeSystemUsage {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface AttributeUsage {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface CapturePolicy {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SpatializationBehavior {
    }

    private static final int hidden_FLAG_BYPASS_INTERRUPTION_POLICY() {
        return 64;
    }

    public static boolean isHiddenUsage(int i) {
        return i == 15;
    }

    @SystemApi
    public static boolean isSystemUsage(int i) {
        return i == 17 || i == 1000 || i == 1001 || i == 1002 || i == 1003 || i == 1004;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int usageForStreamType(int i) {
        switch (i) {
        }
        return 2;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        SUPPRESSIBLE_USAGES = sparseIntArray;
        sparseIntArray.put(5, 1);
        sparseIntArray.put(6, 2);
        sparseIntArray.put(7, 2);
        sparseIntArray.put(8, 1);
        sparseIntArray.put(9, 1);
        sparseIntArray.put(10, 1);
        sparseIntArray.put(11, 3);
        sparseIntArray.put(2, 3);
        sparseIntArray.put(3, 3);
        sparseIntArray.put(4, 4);
        sparseIntArray.put(1, 5);
        sparseIntArray.put(12, 5);
        sparseIntArray.put(14, 5);
        sparseIntArray.put(16, 5);
        sparseIntArray.put(17, 3);
        sparseIntArray.put(0, 5);
        sparseIntArray.put(13, 6);
        SDK_USAGES = IntArray.wrap(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 16});
        CONTENT_TYPES = IntArray.wrap(new int[]{0, 1, 2, 3, 4});
        CREATOR = new Parcelable.Creator<AudioAttributes>() { // from class: android.media.AudioAttributes.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public AudioAttributes createFromParcel(Parcel parcel) {
                return new AudioAttributes(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public AudioAttributes[] newArray(int i) {
                return new AudioAttributes[i];
            }
        };
        HashMap map = new HashMap();
        sXsdStringToUsage = map;
        map.put(AudioUsage.AUDIO_USAGE_UNKNOWN.toString(), 0);
        map.put(AudioUsage.AUDIO_USAGE_UNKNOWN.toString(), 0);
        map.put(AudioUsage.AUDIO_USAGE_MEDIA.toString(), 1);
        map.put(AudioUsage.AUDIO_USAGE_VOICE_COMMUNICATION.toString(), 2);
        map.put(AudioUsage.AUDIO_USAGE_VOICE_COMMUNICATION_SIGNALLING.toString(), 3);
        map.put(AudioUsage.AUDIO_USAGE_ALARM.toString(), 4);
        map.put(AudioUsage.AUDIO_USAGE_NOTIFICATION.toString(), 5);
        map.put(AudioUsage.AUDIO_USAGE_NOTIFICATION_TELEPHONY_RINGTONE.toString(), 6);
        map.put(AudioUsage.AUDIO_USAGE_ASSISTANCE_ACCESSIBILITY.toString(), 11);
        map.put(AudioUsage.AUDIO_USAGE_ASSISTANCE_NAVIGATION_GUIDANCE.toString(), 12);
        map.put(AudioUsage.AUDIO_USAGE_ASSISTANCE_SONIFICATION.toString(), 13);
        map.put(AudioUsage.AUDIO_USAGE_GAME.toString(), 14);
        map.put(AudioUsage.AUDIO_USAGE_VIRTUAL_SOURCE.toString(), 15);
        map.put(AudioUsage.AUDIO_USAGE_ASSISTANT.toString(), 16);
        map.put(AudioUsage.AUDIO_USAGE_CALL_ASSISTANT.toString(), 17);
        map.put(AudioUsage.AUDIO_USAGE_EMERGENCY.toString(), 1000);
        map.put(AudioUsage.AUDIO_USAGE_SAFETY.toString(), 1001);
        map.put(AudioUsage.AUDIO_USAGE_VEHICLE_STATUS.toString(), 1002);
        map.put(AudioUsage.AUDIO_USAGE_ANNOUNCEMENT.toString(), 1003);
    }

    public static int[] getSdkUsages() {
        return SDK_USAGES.toArray();
    }

    private AudioAttributes() {
        this.mUsage = 0;
        this.mContentType = 0;
        this.mSource = -1;
        this.mFlags = 0;
        this.mBundle = null;
        this.mFormattedTags = "";
    }

    public int getContentType() {
        return this.mContentType;
    }

    public int getUsage() {
        if (isSystemUsage(this.mUsage)) {
            return 0;
        }
        return this.mUsage;
    }

    @SystemApi
    public int getSystemUsage() {
        return this.mUsage;
    }

    @SystemApi
    public int getCapturePreset() {
        return this.mSource;
    }

    public int getFlags() {
        return this.mFlags & 273;
    }

    @SystemApi
    public int getAllFlags() {
        return this.mFlags & FLAG_ALL;
    }

    @SystemApi
    public Bundle getBundle() {
        Bundle bundle = this.mBundle;
        return bundle == null ? bundle : new Bundle(this.mBundle);
    }

    public Set<String> getTags() {
        return Collections.unmodifiableSet(this.mTags);
    }

    public boolean areHapticChannelsMuted() {
        return (this.mFlags & 2048) != 0;
    }

    public boolean isContentSpatialized() {
        return (this.mFlags & 16384) != 0;
    }

    public int getSpatializationBehavior() {
        return (this.mFlags & 32768) != 0 ? 1 : 0;
    }

    public int getAllowedCapturePolicy() {
        int i = this.mFlags;
        if ((i & 4096) == 4096) {
            return 3;
        }
        return (i & 1024) == 1024 ? 2 : 1;
    }

    public boolean isForCallRedirection() {
        return (this.mFlags & 65536) == 65536;
    }

    public static class Builder {
        private static final int PRIVACY_SENSITIVE_DEFAULT = -1;
        private static final int PRIVACY_SENSITIVE_DISABLED = 0;
        private static final int PRIVACY_SENSITIVE_ENABLED = 1;
        private Bundle mBundle;
        private int mContentType;
        private int mFlags;
        private boolean mIsContentSpatialized;
        private boolean mMuteHapticChannels;
        private int mPrivacySensitive;
        private int mSource;
        private int mSpatializationBehavior;
        private int mSystemUsage;
        private HashSet<String> mTags;
        private int mUsage;

        public Builder() {
            this.mUsage = -1;
            this.mSystemUsage = -1;
            this.mContentType = 0;
            this.mSource = -1;
            this.mFlags = 0;
            this.mMuteHapticChannels = true;
            this.mIsContentSpatialized = false;
            this.mSpatializationBehavior = 0;
            this.mTags = new HashSet<>();
            this.mPrivacySensitive = -1;
        }

        public Builder(AudioAttributes audioAttributes) {
            this.mUsage = -1;
            this.mSystemUsage = -1;
            this.mContentType = 0;
            this.mSource = -1;
            this.mFlags = 0;
            this.mMuteHapticChannels = true;
            this.mIsContentSpatialized = false;
            this.mSpatializationBehavior = 0;
            this.mTags = new HashSet<>();
            this.mPrivacySensitive = -1;
            this.mUsage = audioAttributes.mUsage;
            this.mContentType = audioAttributes.mContentType;
            this.mSource = audioAttributes.mSource;
            this.mFlags = audioAttributes.getAllFlags();
            this.mTags = (HashSet) audioAttributes.mTags.clone();
            this.mMuteHapticChannels = audioAttributes.areHapticChannelsMuted();
            this.mIsContentSpatialized = audioAttributes.isContentSpatialized();
            this.mSpatializationBehavior = audioAttributes.getSpatializationBehavior();
            if ((this.mFlags & 8192) != 0) {
                this.mPrivacySensitive = 1;
            }
        }

        public AudioAttributes build() {
            AudioAttributes audioAttributes = new AudioAttributes();
            audioAttributes.mContentType = this.mContentType;
            int i = this.mUsage;
            if (i == -1) {
                int i2 = this.mSystemUsage;
                if (i2 == -1) {
                    audioAttributes.mUsage = 0;
                } else {
                    audioAttributes.mUsage = i2;
                }
            } else if (this.mSystemUsage == -1) {
                audioAttributes.mUsage = i;
            } else {
                throw new IllegalArgumentException("Cannot set both usage and system usage on same builder");
            }
            int i3 = audioAttributes.mUsage;
            if (i3 == 7 || i3 == 8 || i3 == 9) {
                audioAttributes.mUsage = 5;
            }
            audioAttributes.mSource = this.mSource;
            audioAttributes.mFlags = this.mFlags;
            if (this.mMuteHapticChannels) {
                audioAttributes.mFlags |= 2048;
            }
            if (this.mIsContentSpatialized) {
                audioAttributes.mFlags |= 16384;
            }
            if (this.mSpatializationBehavior == 1) {
                audioAttributes.mFlags |= 32768;
            }
            int i4 = this.mPrivacySensitive;
            if (i4 == -1) {
                int i5 = this.mSource;
                if (i5 == 7 || i5 == 5) {
                    audioAttributes.mFlags |= 8192;
                } else {
                    audioAttributes.mFlags &= -8193;
                }
            } else if (i4 == 1) {
                audioAttributes.mFlags |= 8192;
            } else {
                audioAttributes.mFlags &= -8193;
            }
            audioAttributes.mTags = (HashSet) this.mTags.clone();
            audioAttributes.mFormattedTags = TextUtils.join(NavigationBarInflaterView.GRAVITY_SEPARATOR, this.mTags);
            if (this.mBundle != null) {
                audioAttributes.mBundle = new Bundle(this.mBundle);
            }
            if (this.mSource != 6 && (this.mFlags & 32) == 32) {
                audioAttributes.mFlags &= -33;
            }
            return audioAttributes;
        }

        public Builder setUsage(int i) {
            switch (i) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                    this.mUsage = i;
                    return this;
                default:
                    throw new IllegalArgumentException("Invalid usage " + i);
            }
        }

        @SystemApi
        public Builder setSystemUsage(int i) {
            if (AudioAttributes.isSystemUsage(i)) {
                this.mSystemUsage = i;
                return this;
            }
            throw new IllegalArgumentException("Invalid system usage " + i);
        }

        public Builder setContentType(int i) {
            if (i == 0 || i == 1 || i == 2 || i == 3 || i == 4) {
                this.mContentType = i;
                return this;
            }
            throw new IllegalArgumentException("Invalid content type " + i);
        }

        @SystemApi
        public Builder setInternalContentType(int i) {
            if (i == 1997) {
                this.mContentType = i;
                return this;
            }
            setContentType(i);
            return this;
        }

        public Builder setFlags(int i) {
            this.mFlags = (i & 465) | this.mFlags;
            return this;
        }

        @SystemApi
        public Builder setHotwordModeEnabled(boolean z) {
            if (z) {
                this.mFlags |= 32;
                return this;
            }
            this.mFlags &= -33;
            return this;
        }

        public Builder setAllowedCapturePolicy(int i) {
            this.mFlags = AudioAttributes.capturePolicyToFlags(i, this.mFlags);
            return this;
        }

        public Builder setIsContentSpatialized(boolean z) {
            this.mIsContentSpatialized = z;
            return this;
        }

        public Builder setSpatializationBehavior(int i) {
            if (i != 0 && i != 1) {
                throw new IllegalArgumentException("Invalid spatialization behavior " + i);
            }
            this.mSpatializationBehavior = i;
            return this;
        }

        public Builder replaceFlags(int i) {
            this.mFlags = i & AudioAttributes.FLAG_ALL;
            return this;
        }

        @SystemApi
        public Builder addBundle(Bundle bundle) {
            if (bundle == null) {
                throw new IllegalArgumentException("Illegal null bundle");
            }
            Bundle bundle2 = this.mBundle;
            if (bundle2 == null) {
                this.mBundle = new Bundle(bundle);
                return this;
            }
            bundle2.putAll(bundle);
            return this;
        }

        public Builder addTag(String str) {
            this.mTags.add(str);
            return this;
        }

        public Builder replaceTags(HashSet<String> hashSet) {
            this.mTags = (HashSet) hashSet.clone();
            return this;
        }

        public Builder setLegacyStreamType(int i) {
            if (i == 10) {
                throw new IllegalArgumentException("STREAM_ACCESSIBILITY is not a legacy stream type that was used for audio playback");
            }
            setInternalLegacyStreamType(i);
            return this;
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        public Builder setInternalLegacyStreamType(int i) {
            AudioAttributes audioAttributesForStrategyWithLegacyStreamType;
            this.mContentType = 0;
            this.mUsage = 0;
            if (android.media.audiopolicy.AudioProductStrategy.getAudioProductStrategies().size() > 0 && (audioAttributesForStrategyWithLegacyStreamType = android.media.audiopolicy.AudioProductStrategy.getAudioAttributesForStrategyWithLegacyStreamType(i)) != null) {
                this.mUsage = audioAttributesForStrategyWithLegacyStreamType.mUsage;
                this.mFlags = audioAttributesForStrategyWithLegacyStreamType.getAllFlags();
                this.mMuteHapticChannels = audioAttributesForStrategyWithLegacyStreamType.areHapticChannelsMuted();
                this.mIsContentSpatialized = audioAttributesForStrategyWithLegacyStreamType.isContentSpatialized();
                this.mSpatializationBehavior = audioAttributesForStrategyWithLegacyStreamType.getSpatializationBehavior();
                this.mTags = audioAttributesForStrategyWithLegacyStreamType.mTags;
                this.mBundle = audioAttributesForStrategyWithLegacyStreamType.mBundle;
                this.mSource = audioAttributesForStrategyWithLegacyStreamType.mSource;
            }
            switch (i) {
                case 0:
                    this.mContentType = 1;
                    break;
                case 1:
                    this.mContentType = 4;
                    break;
                case 2:
                    this.mContentType = 4;
                    break;
                case 3:
                    break;
                case 4:
                    this.mContentType = 4;
                    break;
                case 5:
                    this.mContentType = 4;
                    break;
                case 6:
                    this.mContentType = 1;
                    break;
                case 7:
                    this.mFlags = 1 | this.mFlags;
                    this.mContentType = 4;
                    break;
                case 8:
                    this.mContentType = 4;
                    break;
                case 9:
                    this.mContentType = 4;
                    this.mFlags |= 8;
                    break;
                case 10:
                    this.mContentType = 1;
                    break;
                case 11:
                    this.mContentType = 1;
                    break;
                default:
                    Log.e(AudioAttributes.TAG, "Invalid stream type " + i + " for AudioAttributes");
                    break;
            }
            if (this.mUsage == 0) {
                this.mUsage = AudioAttributes.usageForStreamType(i);
            }
            return this;
        }

        @SystemApi
        public Builder setCapturePreset(int i) {
            switch (i) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                case 17:
                case 18:
                case 19:
                    this.mSource = i;
                    break;
                case 8:
                default:
                    Log.e(AudioAttributes.TAG, "Invalid capture preset " + i + " for AudioAttributes");
                    break;
            }
            return this;
        }

        @SystemApi
        public Builder setInternalCapturePreset(int i) {
            if (i == 1999 || i == 8 || i == 1998 || i == 3 || i == 2 || i == 4 || i == 1997 || i == 2000 || i == -1) {
                this.mSource = i;
                return this;
            }
            setCapturePreset(i);
            return this;
        }

        public Builder setHapticChannelsMuted(boolean z) {
            this.mMuteHapticChannels = z;
            return this;
        }

        public Builder setPrivacySensitive(boolean z) {
            this.mPrivacySensitive = z ? 1 : 0;
            return this;
        }

        public Builder setForCallRedirection() {
            this.mFlags |= 65536;
            return this;
        }

        public Builder semAddAudioTag(String str) {
            return addTag(str);
        }

        public Builder allowConcurrentCapture() {
            int i = this.mSource;
            if (i == -1) {
                Log.e(AudioAttributes.TAG, "Current source is invalid");
                return this;
            }
            if (i == 1999) {
                addTag(AudioTag.TAG_CONCURRENT_CAPTURE_FOR_BIXBY);
                return this;
            }
            addTag(AudioTag.TAG_CONCURRENT_CAPTURE);
            return this;
        }

        public Builder addTags(HashSet<String> hashSet) {
            Iterator<String> it = hashSet.iterator();
            while (it.hasNext()) {
                this.mTags.add(it.next());
            }
            return this;
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mUsage);
        parcel.writeInt(this.mContentType);
        parcel.writeInt(this.mSource);
        parcel.writeInt(this.mFlags);
        int i2 = i & 1;
        parcel.writeInt(i2);
        if (i2 == 0) {
            String[] strArr = new String[this.mTags.size()];
            this.mTags.toArray(strArr);
            parcel.writeStringArray(strArr);
        } else if (i2 == 1) {
            parcel.writeString(this.mFormattedTags);
        }
        if (this.mBundle == null) {
            parcel.writeInt(ATTR_PARCEL_IS_NULL_BUNDLE);
        } else {
            parcel.writeInt(1980);
            parcel.writeBundle(this.mBundle);
        }
    }

    private AudioAttributes(Parcel parcel) {
        this.mUsage = 0;
        this.mContentType = 0;
        this.mSource = -1;
        this.mFlags = 0;
        this.mUsage = parcel.readInt();
        this.mContentType = parcel.readInt();
        this.mSource = parcel.readInt();
        this.mFlags = parcel.readInt();
        boolean z = (parcel.readInt() & 1) == 1;
        this.mTags = new HashSet<>();
        if (z) {
            String str = new String(parcel.readString());
            this.mFormattedTags = str;
            this.mTags.add(str);
        } else {
            String[] stringArray = parcel.readStringArray();
            for (int length = stringArray.length - 1; length >= 0; length--) {
                this.mTags.add(stringArray[length]);
            }
            this.mFormattedTags = TextUtils.join(NavigationBarInflaterView.GRAVITY_SEPARATOR, this.mTags);
        }
        int i = parcel.readInt();
        if (i == ATTR_PARCEL_IS_NULL_BUNDLE) {
            this.mBundle = null;
        } else if (i == 1980) {
            this.mBundle = new Bundle(parcel.readBundle());
        } else {
            Log.e(TAG, "Illegal value unmarshalling AudioAttributes, can't initialize bundle");
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            AudioAttributes audioAttributes = (AudioAttributes) obj;
            if (this.mContentType == audioAttributes.mContentType && this.mFlags == audioAttributes.mFlags && this.mSource == audioAttributes.mSource && this.mUsage == audioAttributes.mUsage && this.mFormattedTags.equals(audioAttributes.mFormattedTags)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mContentType), Integer.valueOf(this.mFlags), Integer.valueOf(this.mSource), Integer.valueOf(this.mUsage), this.mFormattedTags, this.mBundle);
    }

    public String toString() {
        String str;
        StringBuilder sb = new StringBuilder("AudioAttributes: usage=");
        sb.append(usageToString());
        sb.append(" content=");
        sb.append(contentTypeToString());
        if (this.mSource != -1) {
            str = " source=" + MediaRecorder.toLogFriendlyAudioSource(this.mSource);
        } else {
            str = "";
        }
        sb.append(str);
        sb.append(" flags=0x");
        sb.append(Integer.toHexString(this.mFlags).toUpperCase());
        sb.append(" tags=");
        sb.append(this.mFormattedTags);
        sb.append(" bundle=");
        Bundle bundle = this.mBundle;
        sb.append(bundle == null ? PerfettoProtoLogImpl.NULL_STRING : bundle.toString());
        return new String(sb.toString());
    }

    public void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
        long jStart = protoOutputStream.start(j);
        protoOutputStream.write(1159641169921L, this.mUsage);
        protoOutputStream.write(1159641169922L, this.mContentType);
        protoOutputStream.write(1120986464259L, this.mFlags);
        for (String str : this.mFormattedTags.split(NavigationBarInflaterView.GRAVITY_SEPARATOR)) {
            String strTrim = str.trim();
            if (strTrim != "") {
                protoOutputStream.write(2237677961220L, strTrim);
            }
        }
        protoOutputStream.end(jStart);
    }

    public String usageToString() {
        return usageToString(this.mUsage);
    }

    public static String usageToString(int i) {
        if (i == 16) {
            return "USAGE_ASSISTANT";
        }
        if (i != 17) {
            switch (i) {
                case 0:
                    return "USAGE_UNKNOWN";
                case 1:
                    return "USAGE_MEDIA";
                case 2:
                    return "USAGE_VOICE_COMMUNICATION";
                case 3:
                    return "USAGE_VOICE_COMMUNICATION_SIGNALLING";
                case 4:
                    return "USAGE_ALARM";
                case 5:
                    return "USAGE_NOTIFICATION";
                case 6:
                    return "USAGE_NOTIFICATION_RINGTONE";
                case 7:
                    return "USAGE_NOTIFICATION_COMMUNICATION_REQUEST";
                case 8:
                    return "USAGE_NOTIFICATION_COMMUNICATION_INSTANT";
                case 9:
                    return "USAGE_NOTIFICATION_COMMUNICATION_DELAYED";
                case 10:
                    return "USAGE_NOTIFICATION_EVENT";
                case 11:
                    return "USAGE_ASSISTANCE_ACCESSIBILITY";
                case 12:
                    return "USAGE_ASSISTANCE_NAVIGATION_GUIDANCE";
                case 13:
                    return "USAGE_ASSISTANCE_SONIFICATION";
                case 14:
                    return "USAGE_GAME";
                default:
                    switch (i) {
                        case 1000:
                            return "USAGE_EMERGENCY";
                        case 1001:
                            return "USAGE_SAFETY";
                        case 1002:
                            return "USAGE_VEHICLE_STATUS";
                        case 1003:
                            return "USAGE_ANNOUNCEMENT";
                        case 1004:
                            return "USAGE_SPEAKER_CLEANUP";
                        default:
                            return "unknown usage " + i;
                    }
            }
        }
        return "USAGE_CALL_ASSISTANT";
    }

    public static String usageToXsdString(int i) {
        switch (i) {
            case 0:
                return AudioUsage.AUDIO_USAGE_UNKNOWN.toString();
            case 1:
                return AudioUsage.AUDIO_USAGE_MEDIA.toString();
            case 2:
                return AudioUsage.AUDIO_USAGE_VOICE_COMMUNICATION.toString();
            case 3:
                return AudioUsage.AUDIO_USAGE_VOICE_COMMUNICATION_SIGNALLING.toString();
            case 4:
                return AudioUsage.AUDIO_USAGE_ALARM.toString();
            case 5:
                return AudioUsage.AUDIO_USAGE_NOTIFICATION.toString();
            case 6:
                return AudioUsage.AUDIO_USAGE_NOTIFICATION_TELEPHONY_RINGTONE.toString();
            default:
                switch (i) {
                    case 11:
                        return AudioUsage.AUDIO_USAGE_ASSISTANCE_ACCESSIBILITY.toString();
                    case 12:
                        return AudioUsage.AUDIO_USAGE_ASSISTANCE_NAVIGATION_GUIDANCE.toString();
                    case 13:
                        return AudioUsage.AUDIO_USAGE_ASSISTANCE_SONIFICATION.toString();
                    case 14:
                        return AudioUsage.AUDIO_USAGE_GAME.toString();
                    case 15:
                        return AudioUsage.AUDIO_USAGE_VIRTUAL_SOURCE.toString();
                    case 16:
                        return AudioUsage.AUDIO_USAGE_ASSISTANT.toString();
                    case 17:
                        return AudioUsage.AUDIO_USAGE_CALL_ASSISTANT.toString();
                    default:
                        switch (i) {
                            case 1000:
                                return AudioUsage.AUDIO_USAGE_EMERGENCY.toString();
                            case 1001:
                                return AudioUsage.AUDIO_USAGE_SAFETY.toString();
                            case 1002:
                                return AudioUsage.AUDIO_USAGE_VEHICLE_STATUS.toString();
                            case 1003:
                                return AudioUsage.AUDIO_USAGE_ANNOUNCEMENT.toString();
                            default:
                                Log.w(TAG, "Unknown usage value " + i);
                                return AudioUsage.AUDIO_USAGE_UNKNOWN.toString();
                        }
                }
        }
    }

    public static int xsdStringToUsage(String str) {
        Map<String, Integer> map = sXsdStringToUsage;
        if (map.containsKey(str)) {
            return map.get(str).intValue();
        }
        Log.w(TAG, "Usage name not found in AudioUsage enum: " + str);
        return 0;
    }

    public String contentTypeToString() {
        int i = this.mContentType;
        if (i == 0) {
            return new String("CONTENT_TYPE_UNKNOWN");
        }
        if (i == 1) {
            return new String("CONTENT_TYPE_SPEECH");
        }
        if (i == 2) {
            return new String("CONTENT_TYPE_MUSIC");
        }
        if (i == 3) {
            return new String("CONTENT_TYPE_MOVIE");
        }
        if (i == 4) {
            return new String("CONTENT_TYPE_SONIFICATION");
        }
        if (i == 1997) {
            return new String("CONTENT_TYPE_ULTRASOUND");
        }
        return new String("unknown content type " + this.mContentType);
    }

    public static boolean isSdkUsage(int i) {
        return SDK_USAGES.contains(i);
    }

    public static boolean isSdkContentType(int i) {
        return CONTENT_TYPES.contains(i);
    }

    public int getVolumeControlStream() {
        return toVolumeStreamType(true, this);
    }

    public static int toLegacyStreamType(AudioAttributes audioAttributes) {
        return toVolumeStreamType(false, audioAttributes);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0075 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static int toVolumeStreamType(boolean z, AudioAttributes audioAttributes) {
        if ((audioAttributes.getFlags() & 1) == 1) {
            return z ? 1 : 7;
        }
        if ((audioAttributes.getAllFlags() & 4) == 4) {
            return 0;
        }
        if ((audioAttributes.getAllFlags() & 8) == 8) {
            return z ? 3 : 9;
        }
        if (android.media.audiopolicy.AudioProductStrategy.getAudioProductStrategies().size() > 0) {
            return android.media.audiopolicy.AudioProductStrategy.getLegacyStreamTypeForStrategyWithAudioAttributes(audioAttributes);
        }
        int usage = audioAttributes.getUsage();
        if (usage != 16) {
            if (usage != 17) {
                switch (usage) {
                    case 0:
                        return 3;
                    case 1:
                    case 12:
                    case 14:
                        break;
                    case 2:
                        break;
                    case 3:
                        return z ? 0 : 8;
                    case 4:
                        return 4;
                    case 5:
                    case 7:
                    case 8:
                    case 9:
                    case 10:
                        return 5;
                    case 6:
                        return 2;
                    case 11:
                        return 10;
                    case 13:
                        return 1;
                    default:
                        switch (usage) {
                            case 1000:
                            case 1001:
                            case 1002:
                            case 1003:
                            case 1004:
                                break;
                            default:
                                if (!z) {
                                    return 3;
                                }
                                throw new IllegalArgumentException("Unknown usage value " + audioAttributes.getUsage() + " in audio attributes");
                        }
                }
            }
            return 0;
        }
        return 3;
    }

    public static int capturePolicyToFlags(int i, int i2) {
        if (i == 1) {
            return i2 & (-5121);
        }
        if (i == 2) {
            return (i2 | 1024) & (-4097);
        }
        if (i == 3) {
            return i2 | 5120;
        }
        throw new IllegalArgumentException("Unknown allow playback capture policy");
    }
}
