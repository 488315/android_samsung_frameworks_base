package android.media.audiopolicy;

import android.annotation.SystemApi;
import android.media.AudioAttributes;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.android.internal.util.Preconditions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@SystemApi
/* loaded from: classes3.dex */
public final class AudioVolumeGroup implements Parcelable {
    public static final int DEFAULT_VOLUME_GROUP = -1;
    private static final String TAG = "AudioVolumeGroup";
    private static List<AudioVolumeGroup> sAudioVolumeGroups;
    private final AudioAttributes[] mAudioAttributes;
    private int mId;
    private int[] mLegacyStreamTypes;
    private final String mName;
    private static final Object sLock = new Object();
    public static final Parcelable.Creator<AudioVolumeGroup> CREATOR = new Parcelable.Creator<AudioVolumeGroup>() { // from class: android.media.audiopolicy.AudioVolumeGroup.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioVolumeGroup createFromParcel(Parcel parcel) {
            Preconditions.checkNotNull(parcel, "in Parcel must not be null");
            String string = parcel.readString();
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            AudioAttributes[] audioAttributesArr = new AudioAttributes[i2];
            for (int i3 = 0; i3 < i2; i3++) {
                audioAttributesArr[i3] = AudioAttributes.CREATOR.createFromParcel(parcel);
            }
            int i4 = parcel.readInt();
            int[] iArr = new int[i4];
            for (int i5 = 0; i5 < i4; i5++) {
                iArr[i5] = parcel.readInt();
            }
            return new AudioVolumeGroup(string, i, audioAttributesArr, iArr);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioVolumeGroup[] newArray(int i) {
            return new AudioVolumeGroup[i];
        }
    };

    private static native int native_list_audio_volume_groups(ArrayList<AudioVolumeGroup> arrayList);

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static List<AudioVolumeGroup> getAudioVolumeGroups() {
        if (sAudioVolumeGroups == null) {
            synchronized (sLock) {
                if (sAudioVolumeGroups == null) {
                    sAudioVolumeGroups = initializeAudioVolumeGroups();
                }
            }
        }
        return sAudioVolumeGroups;
    }

    private static List<AudioVolumeGroup> initializeAudioVolumeGroups() {
        ArrayList arrayList = new ArrayList();
        if (native_list_audio_volume_groups(arrayList) != 0) {
            Log.w(TAG, ": listAudioVolumeGroups failed");
        }
        return arrayList;
    }

    AudioVolumeGroup(String str, int i, AudioAttributes[] audioAttributesArr, int[] iArr) {
        Preconditions.checkNotNull(str, "name must not be null");
        Preconditions.checkNotNull(audioAttributesArr, "audioAttributes must not be null");
        Preconditions.checkNotNull(iArr, "legacyStreamTypes must not be null");
        this.mName = str;
        this.mId = i;
        this.mAudioAttributes = audioAttributesArr;
        this.mLegacyStreamTypes = iArr;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            AudioVolumeGroup audioVolumeGroup = (AudioVolumeGroup) obj;
            if (this.mName.equals(audioVolumeGroup.mName) && this.mId == audioVolumeGroup.mId && Arrays.equals(this.mAudioAttributes, audioVolumeGroup.mAudioAttributes)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.mName, Integer.valueOf(this.mId), Integer.valueOf(Arrays.hashCode(this.mAudioAttributes)), Integer.valueOf(Arrays.hashCode(this.mLegacyStreamTypes)));
    }

    public List<AudioAttributes> getAudioAttributes() {
        return Arrays.asList(this.mAudioAttributes);
    }

    public int[] getLegacyStreamTypes() {
        return this.mLegacyStreamTypes;
    }

    public String name() {
        return this.mName;
    }

    public int getId() {
        return this.mId;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mName);
        parcel.writeInt(this.mId);
        parcel.writeInt(this.mAudioAttributes.length);
        for (AudioAttributes audioAttributes : this.mAudioAttributes) {
            audioAttributes.writeToParcel(parcel, i | 1);
        }
        parcel.writeInt(this.mLegacyStreamTypes.length);
        for (int i2 : this.mLegacyStreamTypes) {
            parcel.writeInt(i2);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("\n Name: ");
        sb.append(this.mName);
        sb.append(" Id: ");
        sb.append(Integer.toString(this.mId));
        sb.append("\n     Supported Audio Attributes:");
        for (AudioAttributes audioAttributes : this.mAudioAttributes) {
            sb.append("\n       -");
            sb.append(audioAttributes.toString());
        }
        sb.append("\n     Supported Legacy Stream Types: { ");
        for (int i : this.mLegacyStreamTypes) {
            sb.append(Integer.toString(i));
            sb.append(" ");
        }
        sb.append("}");
        return sb.toString();
    }
}
