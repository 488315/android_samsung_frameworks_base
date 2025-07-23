package android.os;

import android.app.admin.DevicePolicyResources;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.AudioAttributes;
import android.os.Parcelable;
import android.text.TextUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes3.dex */
public final class VibrationAttributes implements Parcelable {
    public static final Parcelable.Creator<VibrationAttributes> CREATOR = new Parcelable.Creator<VibrationAttributes>() { // from class: android.os.VibrationAttributes.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VibrationAttributes createFromParcel(Parcel parcel) {
            return new VibrationAttributes(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VibrationAttributes[] newArray(int i) {
            return new VibrationAttributes[i];
        }
    };
    public static final int FLAG_ALL_SUPPORTED = 15;
    public static final int FLAG_BYPASS_INTERRUPTION_POLICY = 1;
    public static final int FLAG_BYPASS_USER_VIBRATION_INTENSITY_OFF = 2;
    public static final int FLAG_INVALIDATE_SETTINGS_CACHE = 4;
    public static final int FLAG_PIPELINED_EFFECT = 8;
    public static final int FLATTEN_TAGS = 1;
    private static final String TAG = "VibrationAttributes";
    public static final int USAGE_ACCESSIBILITY = 66;
    public static final int USAGE_ALARM = 17;
    public static final int USAGE_CLASS_ALARM = 1;
    public static final int USAGE_CLASS_FEEDBACK = 2;
    public static final int USAGE_CLASS_MASK = 15;
    public static final int USAGE_CLASS_MEDIA = 3;
    public static final int USAGE_CLASS_UNKNOWN = 0;
    public static final int USAGE_COMMUNICATION_REQUEST = 65;
    public static final int USAGE_FILTER_MATCH_ALL = -1;
    public static final int USAGE_HARDWARE_FEEDBACK = 50;
    public static final int USAGE_IME_FEEDBACK = 82;
    public static final int USAGE_MEDIA = 19;
    public static final int USAGE_NOTIFICATION = 49;
    public static final int USAGE_PHYSICAL_EMULATION = 34;
    public static final int USAGE_RINGTONE = 33;
    public static final int USAGE_TOUCH = 18;
    public static final int USAGE_UNKNOWN = 0;
    private final int mFlags;
    private String mFormattedTags;
    private final int mOriginalAudioUsage;
    private HashSet<String> mTags;
    private final int mUsage;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Flag {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Usage {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface UsageClass {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static VibrationAttributes createForUsage(int i) {
        return new Builder().setUsage(i).build();
    }

    private VibrationAttributes(int i, int i2, int i3) {
        this.mUsage = i;
        this.mOriginalAudioUsage = i2;
        this.mFlags = i3 & 15;
    }

    public int getUsageClass() {
        return this.mUsage & 15;
    }

    public int getUsage() {
        return this.mUsage;
    }

    public int getOriginalAudioUsage() {
        return this.mOriginalAudioUsage;
    }

    public int getFlags() {
        return this.mFlags;
    }

    public boolean isFlagSet(int i) {
        return (this.mFlags & i) > 0;
    }

    public int getAudioUsage() {
        int i = this.mOriginalAudioUsage;
        if (i != 0) {
            return i;
        }
        int i2 = this.mUsage;
        if (i2 == 33) {
            return 6;
        }
        if (i2 == 49) {
            return 5;
        }
        if (i2 == 82) {
            return 13;
        }
        if (i2 == 65) {
            return 2;
        }
        if (i2 == 66) {
            return 11;
        }
        switch (i2) {
            case 17:
                return 4;
            case 18:
                return 13;
            case 19:
                return 1;
            default:
                return 0;
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mUsage);
        parcel.writeInt(this.mOriginalAudioUsage);
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
    }

    private VibrationAttributes(Parcel parcel) {
        this.mUsage = parcel.readInt();
        this.mOriginalAudioUsage = parcel.readInt();
        this.mFlags = parcel.readInt();
        boolean z = (parcel.readInt() & 1) == 1;
        this.mTags = new HashSet<>();
        if (z) {
            String readString = parcel.readString();
            this.mFormattedTags = readString;
            this.mTags.add(readString);
        } else {
            String[] readStringArray = parcel.readStringArray();
            for (int length = readStringArray.length - 1; length >= 0; length--) {
                this.mTags.add(readStringArray[length]);
            }
            this.mFormattedTags = TextUtils.join(NavigationBarInflaterView.GRAVITY_SEPARATOR, this.mTags);
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            VibrationAttributes vibrationAttributes = (VibrationAttributes) obj;
            if (this.mUsage == vibrationAttributes.mUsage && this.mOriginalAudioUsage == vibrationAttributes.mOriginalAudioUsage && this.mFlags == vibrationAttributes.mFlags && this.mFormattedTags.equals(vibrationAttributes.mFormattedTags)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mUsage), Integer.valueOf(this.mOriginalAudioUsage), Integer.valueOf(this.mFlags), this.mFormattedTags);
    }

    public String toString() {
        return "VibrationAttributes{mUsage=" + usageToString() + ", mAudioUsage= " + AudioAttributes.usageToString(this.mOriginalAudioUsage) + ", mFlags=" + this.mFlags + ", tags=" + this.mFormattedTags + '}';
    }

    public String usageToString() {
        return usageToString(this.mUsage);
    }

    public static String usageToString(int i) {
        if (i == 0) {
            return "UNKNOWN";
        }
        if (i == 82) {
            return "IME";
        }
        if (i == 33) {
            return "RINGTONE";
        }
        if (i == 34) {
            return "PHYSICAL_EMULATION";
        }
        if (i == 49) {
            return DevicePolicyResources.Drawables.Source.NOTIFICATION;
        }
        if (i == 50) {
            return "HARDWARE_FEEDBACK";
        }
        if (i == 65) {
            return "COMMUNICATION_REQUEST";
        }
        if (i != 66) {
            switch (i) {
                case 17:
                    return "ALARM";
                case 18:
                    return "TOUCH";
                case 19:
                    return "MEDIA";
                default:
                    return "unknown usage " + i;
            }
        }
        return "ACCESSIBILITY";
    }

    public static final class Builder {
        private int mFlags;
        private int mOriginalAudioUsage;
        private HashSet<String> mTags;
        private int mUsage;

        public Builder() {
            this.mUsage = 0;
            this.mOriginalAudioUsage = 0;
            this.mFlags = 0;
            this.mTags = new HashSet<>();
        }

        public Builder(VibrationAttributes vibrationAttributes) {
            this.mUsage = 0;
            this.mOriginalAudioUsage = 0;
            this.mFlags = 0;
            this.mTags = new HashSet<>();
            if (vibrationAttributes != null) {
                this.mUsage = vibrationAttributes.mUsage;
                this.mOriginalAudioUsage = vibrationAttributes.mOriginalAudioUsage;
                this.mFlags = vibrationAttributes.mFlags;
                this.mTags = (HashSet) vibrationAttributes.mTags.clone();
            }
        }

        public Builder(AudioAttributes audioAttributes) {
            this.mUsage = 0;
            this.mOriginalAudioUsage = 0;
            this.mFlags = 0;
            this.mTags = new HashSet<>();
            setUsage(audioAttributes);
            setFlags(audioAttributes);
            Iterator<String> it = audioAttributes.getTags().iterator();
            while (it.hasNext()) {
                semAddTag(it.next());
            }
        }

        private void setUsage(AudioAttributes audioAttributes) {
            this.mOriginalAudioUsage = audioAttributes.getUsage();
            switch (audioAttributes.getUsage()) {
                case 1:
                case 14:
                    this.mUsage = 19;
                    break;
                case 2:
                case 3:
                case 12:
                case 16:
                    this.mUsage = 65;
                    break;
                case 4:
                    this.mUsage = 17;
                    break;
                case 5:
                case 7:
                case 8:
                case 9:
                case 10:
                    this.mUsage = 49;
                    break;
                case 6:
                    this.mUsage = 33;
                    break;
                case 11:
                    this.mUsage = 66;
                    break;
                case 13:
                    this.mUsage = 18;
                    break;
                case 15:
                default:
                    this.mUsage = 0;
                    break;
            }
        }

        private void setFlags(AudioAttributes audioAttributes) {
            if ((audioAttributes.getAllFlags() & 64) != 0) {
                this.mFlags |= 1;
            }
            if ((audioAttributes.getAllFlags() & 128) != 0) {
                this.mFlags |= 2;
            }
        }

        public VibrationAttributes build() {
            VibrationAttributes vibrationAttributes = new VibrationAttributes(this.mUsage, this.mOriginalAudioUsage, this.mFlags);
            vibrationAttributes.mTags = (HashSet) this.mTags.clone();
            vibrationAttributes.mFormattedTags = TextUtils.join(NavigationBarInflaterView.GRAVITY_SEPARATOR, this.mTags);
            return vibrationAttributes;
        }

        public Builder setUsage(int i) {
            this.mOriginalAudioUsage = 0;
            this.mUsage = i;
            return this;
        }

        public Builder setFlags(int i, int i2) {
            int i3 = i2 & 15;
            this.mFlags = (i & i3) | (this.mFlags & (~i3));
            return this;
        }

        public Builder setFlags(int i) {
            return setFlags(i, 15);
        }

        public Builder semAddTag(String str) {
            this.mTags.add(str);
            return this;
        }
    }

    public Set<String> getTags() {
        return Collections.unmodifiableSet(this.mTags);
    }

    public boolean hasTag(String str) {
        return getTags().contains(str);
    }
}
