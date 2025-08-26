package android.media.audiopolicy;

import android.annotation.SystemApi;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.AudioAttributes;
import android.media.AudioSystem;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@SystemApi
/* loaded from: classes3.dex */
public final class AudioProductStrategy implements Parcelable {
    private static final int AUDIO_FLAGS_AFFECT_STRATEGY_SELECTION = 13;
    public static final int DEFAULT_GROUP = -1;
    private static final String TAG = "AudioProductStrategy";
    private static List<AudioProductStrategy> sAudioProductStrategies;
    private final AudioAttributesGroup[] mAudioAttributesGroups;
    private int mId;
    private final String mName;
    private static final Object sLock = new Object();
    public static final Parcelable.Creator<AudioProductStrategy> CREATOR = new Parcelable.Creator<AudioProductStrategy>() { // from class: android.media.audiopolicy.AudioProductStrategy.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioProductStrategy createFromParcel(Parcel parcel) {
            String string = parcel.readString();
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            AudioAttributesGroup[] audioAttributesGroupArr = new AudioAttributesGroup[i2];
            for (int i3 = 0; i3 < i2; i3++) {
                audioAttributesGroupArr[i3] = AudioAttributesGroup.CREATOR.createFromParcel(parcel);
            }
            return new AudioProductStrategy(string, i, audioAttributesGroupArr);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioProductStrategy[] newArray(int i) {
            return new AudioProductStrategy[i];
        }
    };
    private static final AudioAttributes DEFAULT_ATTRIBUTES = new AudioAttributes.Builder().build();

    private static native int native_list_audio_product_strategies(ArrayList<AudioProductStrategy> arrayList);

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static List<AudioProductStrategy> getAudioProductStrategies() {
        if (sAudioProductStrategies == null) {
            synchronized (sLock) {
                if (sAudioProductStrategies == null) {
                    sAudioProductStrategies = initializeAudioProductStrategies();
                }
            }
        }
        return sAudioProductStrategies;
    }

    public static AudioProductStrategy getAudioProductStrategyWithId(int i) {
        synchronized (sLock) {
            if (sAudioProductStrategies == null) {
                sAudioProductStrategies = initializeAudioProductStrategies();
            }
            for (AudioProductStrategy audioProductStrategy : sAudioProductStrategies) {
                if (audioProductStrategy.getId() == i) {
                    return audioProductStrategy;
                }
            }
            return null;
        }
    }

    @SystemApi
    public static AudioProductStrategy createInvalidAudioProductStrategy(int i) {
        return new AudioProductStrategy("dummy strategy", i, new AudioAttributesGroup[0]);
    }

    public static AudioAttributes getAudioAttributesForStrategyWithLegacyStreamType(int i) {
        Iterator<AudioProductStrategy> it = getAudioProductStrategies().iterator();
        while (it.hasNext()) {
            AudioAttributes audioAttributesForLegacyStreamType = it.next().getAudioAttributesForLegacyStreamType(i);
            if (audioAttributesForLegacyStreamType != null) {
                return audioAttributesForLegacyStreamType;
            }
        }
        return DEFAULT_ATTRIBUTES;
    }

    public static int getLegacyStreamTypeForStrategyWithAudioAttributes(AudioAttributes audioAttributes) {
        Objects.requireNonNull(audioAttributes, "AudioAttributes must not be null");
        for (AudioProductStrategy audioProductStrategy : getAudioProductStrategies()) {
            if (audioProductStrategy.supportsAudioAttributes(audioAttributes)) {
                int legacyStreamTypeForAudioAttributes = audioProductStrategy.getLegacyStreamTypeForAudioAttributes(audioAttributes);
                if (legacyStreamTypeForAudioAttributes == -1) {
                    Log.w(TAG, "Attributes " + audioAttributes + " supported by strategy " + audioProductStrategy.getId() + " have no associated stream type, therefore falling back to STREAM_MUSIC");
                    return 3;
                }
                if (legacyStreamTypeForAudioAttributes < AudioSystem.getNumStreamTypes()) {
                    return legacyStreamTypeForAudioAttributes;
                }
            }
        }
        return 3;
    }

    public static int getVolumeGroupIdForAudioAttributes(AudioAttributes audioAttributes, boolean z) {
        Objects.requireNonNull(audioAttributes, "attributes must not be null");
        int volumeGroupIdForAudioAttributesInt = getVolumeGroupIdForAudioAttributesInt(audioAttributes);
        if (volumeGroupIdForAudioAttributesInt != -1) {
            return volumeGroupIdForAudioAttributesInt;
        }
        if (z) {
            return getVolumeGroupIdForAudioAttributesInt(getDefaultAttributes());
        }
        return -1;
    }

    private static List<AudioProductStrategy> initializeAudioProductStrategies() {
        ArrayList arrayList = new ArrayList();
        if (native_list_audio_product_strategies(arrayList) != 0) {
            Log.w(TAG, ": initializeAudioProductStrategies failed");
        }
        return arrayList;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            AudioProductStrategy audioProductStrategy = (AudioProductStrategy) obj;
            if (this.mId == audioProductStrategy.mId && Objects.equals(this.mName, audioProductStrategy.mName) && Arrays.equals(this.mAudioAttributesGroups, audioProductStrategy.mAudioAttributesGroups)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mId), this.mName, Integer.valueOf(Arrays.hashCode(this.mAudioAttributesGroups)));
    }

    private AudioProductStrategy(String str, int i, AudioAttributesGroup[] audioAttributesGroupArr) {
        Objects.requireNonNull(str, "name must not be null");
        Objects.requireNonNull(audioAttributesGroupArr, "AudioAttributesGroups must not be null");
        this.mName = str;
        this.mId = i;
        this.mAudioAttributesGroups = audioAttributesGroupArr;
    }

    @SystemApi
    public int getId() {
        return this.mId;
    }

    @SystemApi
    public String getName() {
        return this.mName;
    }

    @SystemApi
    public AudioAttributes getAudioAttributes() {
        AudioAttributesGroup[] audioAttributesGroupArr = this.mAudioAttributesGroups;
        return audioAttributesGroupArr.length == 0 ? DEFAULT_ATTRIBUTES : audioAttributesGroupArr[0].getAudioAttributes();
    }

    public AudioAttributes getAudioAttributesForLegacyStreamType(int i) {
        for (AudioAttributesGroup audioAttributesGroup : this.mAudioAttributesGroups) {
            if (audioAttributesGroup.supportsStreamType(i)) {
                return audioAttributesGroup.getAudioAttributes();
            }
        }
        return null;
    }

    public int getLegacyStreamTypeForAudioAttributes(AudioAttributes audioAttributes) {
        Objects.requireNonNull(audioAttributes, "AudioAttributes must not be null");
        for (AudioAttributesGroup audioAttributesGroup : this.mAudioAttributesGroups) {
            if (audioAttributesGroup.supportsAttributes(audioAttributes)) {
                return audioAttributesGroup.getStreamType();
            }
        }
        return -1;
    }

    @SystemApi
    public boolean supportsAudioAttributes(AudioAttributes audioAttributes) {
        Objects.requireNonNull(audioAttributes, "AudioAttributes must not be null");
        for (AudioAttributesGroup audioAttributesGroup : this.mAudioAttributesGroups) {
            if (audioAttributesGroup.supportsAttributes(audioAttributes)) {
                return true;
            }
        }
        return false;
    }

    public int getVolumeGroupIdForLegacyStreamType(int i) {
        for (AudioAttributesGroup audioAttributesGroup : this.mAudioAttributesGroups) {
            if (audioAttributesGroup.supportsStreamType(i)) {
                return audioAttributesGroup.getVolumeGroupId();
            }
        }
        return -1;
    }

    public int getVolumeGroupIdForAudioAttributes(AudioAttributes audioAttributes) {
        Objects.requireNonNull(audioAttributes, "AudioAttributes must not be null");
        for (AudioAttributesGroup audioAttributesGroup : this.mAudioAttributesGroups) {
            if (audioAttributesGroup.supportsAttributes(audioAttributes)) {
                return audioAttributesGroup.getVolumeGroupId();
            }
        }
        return -1;
    }

    private static int getVolumeGroupIdForAudioAttributesInt(AudioAttributes audioAttributes) {
        Objects.requireNonNull(audioAttributes, "attributes must not be null");
        Iterator<AudioProductStrategy> it = getAudioProductStrategies().iterator();
        while (it.hasNext()) {
            int volumeGroupIdForAudioAttributes = it.next().getVolumeGroupIdForAudioAttributes(audioAttributes);
            if (volumeGroupIdForAudioAttributes != -1) {
                return volumeGroupIdForAudioAttributes;
            }
        }
        return -1;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mName);
        parcel.writeInt(this.mId);
        parcel.writeInt(this.mAudioAttributesGroups.length);
        for (AudioAttributesGroup audioAttributesGroup : this.mAudioAttributesGroups) {
            audioAttributesGroup.writeToParcel(parcel, i);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("\n Name: ");
        sb.append(this.mName);
        sb.append(" Id: ");
        sb.append(Integer.toString(this.mId));
        for (AudioAttributesGroup audioAttributesGroup : this.mAudioAttributesGroups) {
            sb.append(audioAttributesGroup.toString());
        }
        return sb.toString();
    }

    public static AudioAttributes getDefaultAttributes() {
        return DEFAULT_ATTRIBUTES;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean attributesMatches(AudioAttributes audioAttributes, AudioAttributes audioAttributes2) {
        Objects.requireNonNull(audioAttributes, "reference AudioAttributes must not be null");
        Objects.requireNonNull(audioAttributes2, "requester's AudioAttributes must not be null");
        String strJoin = TextUtils.join(NavigationBarInflaterView.GRAVITY_SEPARATOR, audioAttributes.getTags());
        String strJoin2 = TextUtils.join(NavigationBarInflaterView.GRAVITY_SEPARATOR, audioAttributes2.getTags());
        if (audioAttributes.equals(DEFAULT_ATTRIBUTES)) {
            return false;
        }
        return (audioAttributes.getSystemUsage() == 0 || audioAttributes2.getSystemUsage() == audioAttributes.getSystemUsage()) && (audioAttributes.getContentType() == 0 || audioAttributes2.getContentType() == audioAttributes.getContentType()) && (((audioAttributes.getAllFlags() & 13) == 0 || ((audioAttributes2.getAllFlags() & 13) != 0 && (audioAttributes2.getAllFlags() & audioAttributes.getAllFlags()) == audioAttributes.getAllFlags())) && (strJoin.length() == 0 || strJoin.equals(strJoin2)));
    }

    private static final class AudioAttributesGroup implements Parcelable {
        public static final Parcelable.Creator<AudioAttributesGroup> CREATOR = new Parcelable.Creator<AudioAttributesGroup>() { // from class: android.media.audiopolicy.AudioProductStrategy.AudioAttributesGroup.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public AudioAttributesGroup createFromParcel(Parcel parcel) {
                int i = parcel.readInt();
                int i2 = parcel.readInt();
                int i3 = parcel.readInt();
                AudioAttributes[] audioAttributesArr = new AudioAttributes[i3];
                for (int i4 = 0; i4 < i3; i4++) {
                    audioAttributesArr[i4] = AudioAttributes.CREATOR.createFromParcel(parcel);
                }
                return new AudioAttributesGroup(i, i2, audioAttributesArr);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public AudioAttributesGroup[] newArray(int i) {
                return new AudioAttributesGroup[i];
            }
        };
        private final AudioAttributes[] mAudioAttributes;
        private int mLegacyStreamType;
        private int mVolumeGroupId;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        AudioAttributesGroup(int i, int i2, AudioAttributes[] audioAttributesArr) {
            this.mVolumeGroupId = i;
            this.mLegacyStreamType = i2;
            this.mAudioAttributes = audioAttributesArr;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && getClass() == obj.getClass()) {
                AudioAttributesGroup audioAttributesGroup = (AudioAttributesGroup) obj;
                if (this.mVolumeGroupId == audioAttributesGroup.mVolumeGroupId && this.mLegacyStreamType == audioAttributesGroup.mLegacyStreamType && Arrays.equals(this.mAudioAttributes, audioAttributesGroup.mAudioAttributes)) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(Integer.valueOf(this.mVolumeGroupId), Integer.valueOf(this.mLegacyStreamType), Integer.valueOf(Arrays.hashCode(this.mAudioAttributes)));
        }

        public int getStreamType() {
            return this.mLegacyStreamType;
        }

        public int getVolumeGroupId() {
            return this.mVolumeGroupId;
        }

        public AudioAttributes getAudioAttributes() {
            AudioAttributes[] audioAttributesArr = this.mAudioAttributes;
            return audioAttributesArr.length == 0 ? AudioProductStrategy.DEFAULT_ATTRIBUTES : audioAttributesArr[0];
        }

        public boolean supportsAttributes(AudioAttributes audioAttributes) {
            for (AudioAttributes audioAttributes2 : this.mAudioAttributes) {
                if (audioAttributes2.equals(audioAttributes) || AudioProductStrategy.attributesMatches(audioAttributes2, audioAttributes)) {
                    return true;
                }
            }
            return false;
        }

        public boolean supportsStreamType(int i) {
            return this.mLegacyStreamType == i;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mVolumeGroupId);
            parcel.writeInt(this.mLegacyStreamType);
            parcel.writeInt(this.mAudioAttributes.length);
            for (AudioAttributes audioAttributes : this.mAudioAttributes) {
                audioAttributes.writeToParcel(parcel, i | 1);
            }
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("\n    Legacy Stream Type: ");
            sb.append(Integer.toString(this.mLegacyStreamType));
            sb.append(" Volume Group Id: ");
            sb.append(Integer.toString(this.mVolumeGroupId));
            for (AudioAttributes audioAttributes : this.mAudioAttributes) {
                sb.append("\n    -");
                sb.append(audioAttributes.toString());
            }
            return sb.toString();
        }
    }
}
