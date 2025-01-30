package androidx.media;

import android.util.SparseIntArray;
import androidx.media.AudioAttributesImplApi26;
import androidx.versionedparcelable.VersionedParcelable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class AudioAttributesCompat implements VersionedParcelable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public AudioAttributesImpl mImpl;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        sparseIntArray.put(5, 1);
        sparseIntArray.put(6, 2);
        sparseIntArray.put(7, 2);
        sparseIntArray.put(8, 1);
        sparseIntArray.put(9, 1);
        sparseIntArray.put(10, 1);
    }

    public AudioAttributesCompat() {
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof AudioAttributesCompat)) {
            return false;
        }
        AudioAttributesCompat audioAttributesCompat = (AudioAttributesCompat) obj;
        AudioAttributesImpl audioAttributesImpl = this.mImpl;
        return audioAttributesImpl == null ? audioAttributesCompat.mImpl == null : audioAttributesImpl.equals(audioAttributesCompat.mImpl);
    }

    public final int hashCode() {
        return this.mImpl.hashCode();
    }

    public final String toString() {
        return this.mImpl.toString();
    }

    public AudioAttributesCompat(AudioAttributesImpl audioAttributesImpl) {
        this.mImpl = audioAttributesImpl;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Builder {
        public final AudioAttributesImplApi26.Builder mBuilderImpl;

        public Builder() {
            int i = AudioAttributesCompat.$r8$clinit;
            this.mBuilderImpl = new AudioAttributesImplApi26.Builder();
        }

        public Builder(AudioAttributesCompat audioAttributesCompat) {
            int i = AudioAttributesCompat.$r8$clinit;
            this.mBuilderImpl = new AudioAttributesImplApi26.Builder(audioAttributesCompat.mImpl.getAudioAttributes());
        }
    }
}
