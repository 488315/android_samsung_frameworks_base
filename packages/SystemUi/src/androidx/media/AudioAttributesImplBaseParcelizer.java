package androidx.media;

import androidx.versionedparcelable.VersionedParcel;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class AudioAttributesImplBaseParcelizer {
    public static AudioAttributesImplBase read(VersionedParcel versionedParcel) {
        AudioAttributesImplBase audioAttributesImplBase = new AudioAttributesImplBase();
        audioAttributesImplBase.mUsage = versionedParcel.readInt(audioAttributesImplBase.mUsage, 1);
        audioAttributesImplBase.mContentType = versionedParcel.readInt(audioAttributesImplBase.mContentType, 2);
        audioAttributesImplBase.mFlags = versionedParcel.readInt(audioAttributesImplBase.mFlags, 3);
        audioAttributesImplBase.mLegacyStream = versionedParcel.readInt(audioAttributesImplBase.mLegacyStream, 4);
        return audioAttributesImplBase;
    }

    public static void write(AudioAttributesImplBase audioAttributesImplBase, VersionedParcel versionedParcel) {
        versionedParcel.getClass();
        versionedParcel.writeInt(audioAttributesImplBase.mUsage, 1);
        versionedParcel.writeInt(audioAttributesImplBase.mContentType, 2);
        versionedParcel.writeInt(audioAttributesImplBase.mFlags, 3);
        versionedParcel.writeInt(audioAttributesImplBase.mLegacyStream, 4);
    }
}
