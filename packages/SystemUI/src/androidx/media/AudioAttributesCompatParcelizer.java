package androidx.media;

import androidx.versionedparcelable.VersionedParcel;
import androidx.versionedparcelable.VersionedParcelable;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class AudioAttributesCompatParcelizer {
    public static AudioAttributesCompat read(VersionedParcel versionedParcel) {
        AudioAttributesCompat audioAttributesCompat = new AudioAttributesCompat();
        VersionedParcelable versionedParcelable = audioAttributesCompat.mImpl;
        if (versionedParcel.readField(1)) {
            versionedParcelable = versionedParcel.readVersionedParcelable();
        }
        audioAttributesCompat.mImpl = (AudioAttributesImpl) versionedParcelable;
        return audioAttributesCompat;
    }

    public static void write(AudioAttributesCompat audioAttributesCompat, VersionedParcel versionedParcel) {
        versionedParcel.getClass();
        AudioAttributesImpl audioAttributesImpl = audioAttributesCompat.mImpl;
        versionedParcel.setOutputField(1);
        versionedParcel.writeVersionedParcelable(audioAttributesImpl);
    }
}
