package android.media.audio.common;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class AudioVolumeGroupChangeEvent implements Parcelable {
    public static final Parcelable.Creator<AudioVolumeGroupChangeEvent> CREATOR = new Parcelable.Creator<AudioVolumeGroupChangeEvent>() { // from class: android.media.audio.common.AudioVolumeGroupChangeEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioVolumeGroupChangeEvent createFromParcel(Parcel parcel) {
            AudioVolumeGroupChangeEvent audioVolumeGroupChangeEvent = new AudioVolumeGroupChangeEvent();
            audioVolumeGroupChangeEvent.readFromParcel(parcel);
            return audioVolumeGroupChangeEvent;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioVolumeGroupChangeEvent[] newArray(int i) {
            return new AudioVolumeGroupChangeEvent[i];
        }
    };
    public static final int VOLUME_FLAG_ABSOLUTE_VOLUME = 8192;
    public static final int VOLUME_FLAG_ACTIVE_MEDIA_ONLY = 512;
    public static final int VOLUME_FLAG_ALLOW_RINGER_MODES = 2;
    public static final int VOLUME_FLAG_BLUETOOTH_ABS_VOLUME = 64;
    public static final int VOLUME_FLAG_FIXED_VOLUME = 32;
    public static final int VOLUME_FLAG_FROM_KEY = 4096;
    public static final int VOLUME_FLAG_HDMI_SYSTEM_AUDIO_VOLUME = 256;
    public static final int VOLUME_FLAG_PLAY_SOUND = 4;
    public static final int VOLUME_FLAG_REMOVE_SOUND_AND_VIBRATE = 8;
    public static final int VOLUME_FLAG_SHOW_SILENT_HINT = 128;
    public static final int VOLUME_FLAG_SHOW_UI = 1;
    public static final int VOLUME_FLAG_SHOW_UI_WARNINGS = 1024;
    public static final int VOLUME_FLAG_SHOW_VIBRATE_HINT = 2048;
    public static final int VOLUME_FLAG_VIBRATE = 16;
    public int groupId = 0;
    public int volumeIndex = 0;
    public boolean muted = false;
    public int flags = 0;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.groupId);
        parcel.writeInt(this.volumeIndex);
        parcel.writeBoolean(this.muted);
        parcel.writeInt(this.flags);
        int iDataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(iDataPosition);
        parcel.writeInt(iDataPosition2 - iDataPosition);
        parcel.setDataPosition(iDataPosition2);
    }

    public final void readFromParcel(Parcel parcel) {
        int iDataPosition = parcel.dataPosition();
        int i = parcel.readInt();
        try {
            if (i < 4) {
                throw new BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - iDataPosition < i) {
                this.groupId = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.volumeIndex = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.muted = parcel.readBoolean();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.flags = parcel.readInt();
                            if (iDataPosition > Integer.MAX_VALUE - i) {
                                throw new BadParcelableException("Overflow in the size of parcelable");
                            }
                        } else if (iDataPosition > Integer.MAX_VALUE - i) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (iDataPosition > Integer.MAX_VALUE - i) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (iDataPosition > Integer.MAX_VALUE - i) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
            } else if (iDataPosition > Integer.MAX_VALUE - i) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(iDataPosition + i);
        } catch (Throwable th) {
            if (iDataPosition > Integer.MAX_VALUE - i) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(iDataPosition + i);
            throw th;
        }
    }

    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
        stringJoiner.add("groupId: " + this.groupId);
        stringJoiner.add("volumeIndex: " + this.volumeIndex);
        stringJoiner.add("muted: " + this.muted);
        stringJoiner.add("flags: " + this.flags);
        return "AudioVolumeGroupChangeEvent" + stringJoiner.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof AudioVolumeGroupChangeEvent)) {
            return false;
        }
        AudioVolumeGroupChangeEvent audioVolumeGroupChangeEvent = (AudioVolumeGroupChangeEvent) obj;
        return Objects.deepEquals(Integer.valueOf(this.groupId), Integer.valueOf(audioVolumeGroupChangeEvent.groupId)) && Objects.deepEquals(Integer.valueOf(this.volumeIndex), Integer.valueOf(audioVolumeGroupChangeEvent.volumeIndex)) && Objects.deepEquals(java.lang.Boolean.valueOf(this.muted), java.lang.Boolean.valueOf(audioVolumeGroupChangeEvent.muted)) && Objects.deepEquals(Integer.valueOf(this.flags), Integer.valueOf(audioVolumeGroupChangeEvent.flags));
    }

    public int hashCode() {
        return Arrays.deepHashCode(Arrays.asList(Integer.valueOf(this.groupId), Integer.valueOf(this.volumeIndex), java.lang.Boolean.valueOf(this.muted), Integer.valueOf(this.flags)).toArray());
    }
}
