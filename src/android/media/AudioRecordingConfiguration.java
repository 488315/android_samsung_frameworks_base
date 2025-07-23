package android.media;

import android.annotation.SystemApi;
import android.media.audiofx.AudioEffect;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import java.io.PrintWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class AudioRecordingConfiguration implements Parcelable {
    private final AudioEffect.Descriptor[] mClientEffects;
    private final AudioFormat mClientFormat;
    private final String mClientPackageName;
    private final int mClientPortId;
    private final int mClientSessionId;
    private boolean mClientSilenced;
    private final int mClientSource;
    private final int mClientUid;
    private final AudioEffect.Descriptor[] mDeviceEffects;
    private final AudioFormat mDeviceFormat;
    private final int mDeviceSource;
    private final int mPatchHandle;
    private static final String TAG = new String("AudioRecordingConfiguration");
    public static final Parcelable.Creator<AudioRecordingConfiguration> CREATOR = new Parcelable.Creator<AudioRecordingConfiguration>() { // from class: android.media.AudioRecordingConfiguration.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioRecordingConfiguration createFromParcel(Parcel parcel) {
            return new AudioRecordingConfiguration(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioRecordingConfiguration[] newArray(int i) {
            return new AudioRecordingConfiguration[i];
        }
    };

    @Retention(RetentionPolicy.SOURCE)
    public @interface AudioSource {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public AudioRecordingConfiguration(int i, int i2, int i3, AudioFormat audioFormat, AudioFormat audioFormat2, int i4, String str, int i5, boolean z, int i6, AudioEffect.Descriptor[] descriptorArr, AudioEffect.Descriptor[] descriptorArr2) {
        this.mClientUid = i;
        this.mClientSessionId = i2;
        this.mClientSource = i3;
        this.mClientFormat = audioFormat;
        this.mDeviceFormat = audioFormat2;
        this.mPatchHandle = i4;
        this.mClientPackageName = str;
        this.mClientPortId = i5;
        this.mClientSilenced = z;
        this.mDeviceSource = i6;
        this.mClientEffects = descriptorArr;
        this.mDeviceEffects = descriptorArr2;
    }

    public AudioRecordingConfiguration(int i, int i2, int i3, AudioFormat audioFormat, AudioFormat audioFormat2, int i4, String str) {
        this(i, i2, i3, audioFormat, audioFormat2, i4, str, 0, false, 0, new AudioEffect.Descriptor[0], new AudioEffect.Descriptor[0]);
    }

    public void dump(PrintWriter printWriter) {
        printWriter.println("  " + toLogFriendlyString(this));
    }

    public static String toLogFriendlyString(AudioRecordingConfiguration audioRecordingConfiguration) {
        String str = new String();
        for (AudioEffect.Descriptor descriptor : audioRecordingConfiguration.mClientEffects) {
            str = str + "'" + descriptor.name + "' ";
        }
        String str2 = new String();
        for (AudioEffect.Descriptor descriptor2 : audioRecordingConfiguration.mDeviceEffects) {
            str2 = str2 + "'" + descriptor2.name + "' ";
        }
        return new String("session:" + audioRecordingConfiguration.mClientSessionId + " -- source client=" + MediaRecorder.toLogFriendlyAudioSource(audioRecordingConfiguration.mClientSource) + ", dev=" + audioRecordingConfiguration.mDeviceFormat.toLogFriendlyString() + " -- uid:" + audioRecordingConfiguration.mClientUid + " -- patch:" + audioRecordingConfiguration.mPatchHandle + " -- pack:" + audioRecordingConfiguration.mClientPackageName + " -- format client=" + audioRecordingConfiguration.mClientFormat.toLogFriendlyString() + ", dev=" + audioRecordingConfiguration.mDeviceFormat.toLogFriendlyString() + " -- silenced:" + audioRecordingConfiguration.mClientSilenced + " -- effects client=" + str + ", dev=" + str2);
    }

    public static AudioRecordingConfiguration anonymizedCopy(AudioRecordingConfiguration audioRecordingConfiguration) {
        return new AudioRecordingConfiguration(-1, audioRecordingConfiguration.mClientSessionId, audioRecordingConfiguration.mClientSource, audioRecordingConfiguration.mClientFormat, audioRecordingConfiguration.mDeviceFormat, audioRecordingConfiguration.mPatchHandle, "", audioRecordingConfiguration.mClientPortId, audioRecordingConfiguration.mClientSilenced, audioRecordingConfiguration.mDeviceSource, audioRecordingConfiguration.mClientEffects, audioRecordingConfiguration.mDeviceEffects);
    }

    public int getClientAudioSource() {
        return this.mClientSource;
    }

    public int getClientAudioSessionId() {
        return this.mClientSessionId;
    }

    public AudioFormat getFormat() {
        return this.mDeviceFormat;
    }

    public AudioFormat getClientFormat() {
        return this.mClientFormat;
    }

    public String getClientPackageName() {
        return this.mClientPackageName;
    }

    @SystemApi
    public int getClientUid() {
        int i = this.mClientUid;
        if (i != -1) {
            return i;
        }
        throw new SecurityException("MODIFY_AUDIO_ROUTING permission is missing");
    }

    public AudioDeviceInfo getAudioDevice() {
        ArrayList arrayList = new ArrayList();
        if (AudioManager.listAudioPatches(arrayList) != 0) {
            Log.e(TAG, "Error retrieving list of audio patches");
            return null;
        }
        int i = 0;
        while (true) {
            if (i >= arrayList.size()) {
                break;
            }
            AudioPatch audioPatch = (AudioPatch) arrayList.get(i);
            if (audioPatch.id() == this.mPatchHandle) {
                AudioPortConfig[] sources = audioPatch.sources();
                if (sources != null && sources.length > 0) {
                    return AudioManager.getDeviceForPortId(sources[0].port().id(), 1);
                }
            } else {
                i++;
            }
        }
        Log.e(TAG, "Couldn't find device for recording, did recording end already?");
        return null;
    }

    public int getClientPortId() {
        return this.mClientPortId;
    }

    public boolean isClientSilenced() {
        return this.mClientSilenced;
    }

    public int getAudioSource() {
        return this.mDeviceSource;
    }

    public List<AudioEffect.Descriptor> getClientEffects() {
        return new ArrayList(Arrays.asList(this.mClientEffects));
    }

    public List<AudioEffect.Descriptor> getEffects() {
        return new ArrayList(Arrays.asList(this.mDeviceEffects));
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mClientSessionId), Integer.valueOf(this.mClientSource));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mClientSessionId);
        parcel.writeInt(this.mClientSource);
        int i2 = 0;
        this.mClientFormat.writeToParcel(parcel, 0);
        this.mDeviceFormat.writeToParcel(parcel, 0);
        parcel.writeInt(this.mPatchHandle);
        parcel.writeString(this.mClientPackageName);
        parcel.writeInt(this.mClientUid);
        parcel.writeInt(this.mClientPortId);
        parcel.writeBoolean(this.mClientSilenced);
        parcel.writeInt(this.mDeviceSource);
        parcel.writeInt(this.mClientEffects.length);
        int i3 = 0;
        while (true) {
            AudioEffect.Descriptor[] descriptorArr = this.mClientEffects;
            if (i3 >= descriptorArr.length) {
                break;
            }
            descriptorArr[i3].writeToParcel(parcel);
            i3++;
        }
        parcel.writeInt(this.mDeviceEffects.length);
        while (true) {
            AudioEffect.Descriptor[] descriptorArr2 = this.mDeviceEffects;
            if (i2 >= descriptorArr2.length) {
                return;
            }
            descriptorArr2[i2].writeToParcel(parcel);
            i2++;
        }
    }

    private AudioRecordingConfiguration(Parcel parcel) {
        this.mClientSessionId = parcel.readInt();
        this.mClientSource = parcel.readInt();
        this.mClientFormat = AudioFormat.CREATOR.createFromParcel(parcel);
        this.mDeviceFormat = AudioFormat.CREATOR.createFromParcel(parcel);
        this.mPatchHandle = parcel.readInt();
        this.mClientPackageName = parcel.readString();
        this.mClientUid = parcel.readInt();
        this.mClientPortId = parcel.readInt();
        this.mClientSilenced = parcel.readBoolean();
        this.mDeviceSource = parcel.readInt();
        this.mClientEffects = new AudioEffect.Descriptor[parcel.readInt()];
        int i = 0;
        int i2 = 0;
        while (true) {
            AudioEffect.Descriptor[] descriptorArr = this.mClientEffects;
            if (i2 >= descriptorArr.length) {
                break;
            }
            descriptorArr[i2] = new AudioEffect.Descriptor(parcel);
            i2++;
        }
        this.mDeviceEffects = new AudioEffect.Descriptor[parcel.readInt()];
        while (true) {
            AudioEffect.Descriptor[] descriptorArr2 = this.mDeviceEffects;
            if (i >= descriptorArr2.length) {
                return;
            }
            descriptorArr2[i] = new AudioEffect.Descriptor(parcel);
            i++;
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && (obj instanceof AudioRecordingConfiguration)) {
            AudioRecordingConfiguration audioRecordingConfiguration = (AudioRecordingConfiguration) obj;
            if (this.mClientUid == audioRecordingConfiguration.mClientUid && this.mClientSessionId == audioRecordingConfiguration.mClientSessionId && this.mClientSource == audioRecordingConfiguration.mClientSource && this.mPatchHandle == audioRecordingConfiguration.mPatchHandle && this.mClientFormat.equals(audioRecordingConfiguration.mClientFormat) && this.mDeviceFormat.equals(audioRecordingConfiguration.mDeviceFormat) && this.mClientPackageName.equals(audioRecordingConfiguration.mClientPackageName) && this.mClientPortId == audioRecordingConfiguration.mClientPortId && this.mClientSilenced == audioRecordingConfiguration.mClientSilenced && this.mDeviceSource == audioRecordingConfiguration.mDeviceSource && Arrays.equals(this.mClientEffects, audioRecordingConfiguration.mClientEffects) && Arrays.equals(this.mDeviceEffects, audioRecordingConfiguration.mDeviceEffects)) {
                return true;
            }
        }
        return false;
    }
}
