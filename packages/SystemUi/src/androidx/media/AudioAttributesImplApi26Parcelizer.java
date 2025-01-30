package androidx.media;

import android.media.AudioAttributes;
import androidx.versionedparcelable.VersionedParcel;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class AudioAttributesImplApi26Parcelizer {
    public static AudioAttributesImplApi26 read(VersionedParcel versionedParcel) {
        AudioAttributesImplApi26 audioAttributesImplApi26 = new AudioAttributesImplApi26();
        audioAttributesImplApi26.mAudioAttributes = (AudioAttributes) versionedParcel.readParcelable(audioAttributesImplApi26.mAudioAttributes, 1);
        audioAttributesImplApi26.mLegacyStreamType = versionedParcel.readInt(audioAttributesImplApi26.mLegacyStreamType, 2);
        return audioAttributesImplApi26;
    }

    public static void write(AudioAttributesImplApi26 audioAttributesImplApi26, VersionedParcel versionedParcel) {
        versionedParcel.getClass();
        versionedParcel.writeParcelable(audioAttributesImplApi26.mAudioAttributes, 1);
        versionedParcel.writeInt(audioAttributesImplApi26.mLegacyStreamType, 2);
    }
}
