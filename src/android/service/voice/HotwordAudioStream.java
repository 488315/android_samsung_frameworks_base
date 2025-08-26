package android.service.voice;

import android.annotation.NonNull;
import android.annotation.SystemApi;
import android.media.AudioFormat;
import android.media.AudioTimestamp;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.PersistableBundle;
import com.android.internal.util.AnnotationValidations;
import java.util.Arrays;
import java.util.Objects;

@SystemApi
/* loaded from: classes3.dex */
public final class HotwordAudioStream implements Parcelable {
    public static final String KEY_AUDIO_STREAM_COPY_BUFFER_LENGTH_BYTES = "android.service.voice.key.AUDIO_STREAM_COPY_BUFFER_LENGTH_BYTES";
    private final AudioFormat mAudioFormat;
    private final ParcelFileDescriptor mAudioStreamParcelFileDescriptor;
    private final byte[] mInitialAudio;
    private final PersistableBundle mMetadata;
    private final AudioTimestamp mTimestamp;
    private static final byte[] DEFAULT_INITIAL_EMPTY_AUDIO = new byte[0];
    public static final Parcelable.Creator<HotwordAudioStream> CREATOR = new Parcelable.Creator<HotwordAudioStream>() { // from class: android.service.voice.HotwordAudioStream.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HotwordAudioStream[] newArray(int i) {
            return new HotwordAudioStream[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HotwordAudioStream createFromParcel(Parcel parcel) {
            return new HotwordAudioStream(parcel);
        }
    };

    @Deprecated
    private void __metadata() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static AudioTimestamp defaultTimestamp() {
        return null;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static PersistableBundle defaultMetadata() {
        return new PersistableBundle();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static byte[] defaultInitialAudio() {
        return DEFAULT_INITIAL_EMPTY_AUDIO;
    }

    private String initialAudioToString() {
        return "length=" + this.mInitialAudio.length;
    }

    public Builder buildUpon() {
        return new Builder(this.mAudioFormat, this.mAudioStreamParcelFileDescriptor).setTimestamp(this.mTimestamp).setMetadata(this.mMetadata).setInitialAudio(this.mInitialAudio);
    }

    static abstract class BaseBuilder {
        BaseBuilder() {
        }

        public Builder setInitialAudio(byte[] bArr) {
            Objects.requireNonNull(bArr, "value should not be null");
            Builder builder = (Builder) this;
            builder.mBuilderFieldsSet |= 16;
            builder.mInitialAudio = bArr;
            return builder;
        }
    }

    HotwordAudioStream(AudioFormat audioFormat, ParcelFileDescriptor parcelFileDescriptor, AudioTimestamp audioTimestamp, PersistableBundle persistableBundle, byte[] bArr) {
        this.mAudioFormat = audioFormat;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) audioFormat);
        this.mAudioStreamParcelFileDescriptor = parcelFileDescriptor;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) parcelFileDescriptor);
        this.mTimestamp = audioTimestamp;
        this.mMetadata = persistableBundle;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) persistableBundle);
        this.mInitialAudio = bArr;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) bArr);
    }

    public AudioFormat getAudioFormat() {
        return this.mAudioFormat;
    }

    public ParcelFileDescriptor getAudioStreamParcelFileDescriptor() {
        return this.mAudioStreamParcelFileDescriptor;
    }

    public AudioTimestamp getTimestamp() {
        return this.mTimestamp;
    }

    public PersistableBundle getMetadata() {
        return this.mMetadata;
    }

    public byte[] getInitialAudio() {
        return this.mInitialAudio;
    }

    public String toString() {
        return "HotwordAudioStream { audioFormat = " + this.mAudioFormat + ", audioStreamParcelFileDescriptor = " + this.mAudioStreamParcelFileDescriptor + ", timestamp = " + this.mTimestamp + ", metadata = " + this.mMetadata + ", initialAudio = " + initialAudioToString() + " }";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            HotwordAudioStream hotwordAudioStream = (HotwordAudioStream) obj;
            if (Objects.equals(this.mAudioFormat, hotwordAudioStream.mAudioFormat) && Objects.equals(this.mAudioStreamParcelFileDescriptor, hotwordAudioStream.mAudioStreamParcelFileDescriptor) && Objects.equals(this.mTimestamp, hotwordAudioStream.mTimestamp) && Objects.equals(this.mMetadata, hotwordAudioStream.mMetadata) && Arrays.equals(this.mInitialAudio, hotwordAudioStream.mInitialAudio)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return ((((((((Objects.hashCode(this.mAudioFormat) + 31) * 31) + Objects.hashCode(this.mAudioStreamParcelFileDescriptor)) * 31) + Objects.hashCode(this.mTimestamp)) * 31) + Objects.hashCode(this.mMetadata)) * 31) + Arrays.hashCode(this.mInitialAudio);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte(this.mTimestamp != null ? (byte) 4 : (byte) 0);
        parcel.writeTypedObject(this.mAudioFormat, i);
        parcel.writeTypedObject(this.mAudioStreamParcelFileDescriptor, i);
        AudioTimestamp audioTimestamp = this.mTimestamp;
        if (audioTimestamp != null) {
            parcel.writeTypedObject(audioTimestamp, i);
        }
        parcel.writeTypedObject(this.mMetadata, i);
        parcel.writeByteArray(this.mInitialAudio);
    }

    HotwordAudioStream(Parcel parcel) {
        byte b = parcel.readByte();
        AudioFormat audioFormat = (AudioFormat) parcel.readTypedObject(AudioFormat.CREATOR);
        ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
        AudioTimestamp audioTimestamp = (b & 4) == 0 ? null : (AudioTimestamp) parcel.readTypedObject(AudioTimestamp.CREATOR);
        PersistableBundle persistableBundle = (PersistableBundle) parcel.readTypedObject(PersistableBundle.CREATOR);
        byte[] bArrCreateByteArray = parcel.createByteArray();
        this.mAudioFormat = audioFormat;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) audioFormat);
        this.mAudioStreamParcelFileDescriptor = parcelFileDescriptor;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) parcelFileDescriptor);
        this.mTimestamp = audioTimestamp;
        this.mMetadata = persistableBundle;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) persistableBundle);
        this.mInitialAudio = bArrCreateByteArray;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) bArrCreateByteArray);
    }

    public static final class Builder extends BaseBuilder {
        private AudioFormat mAudioFormat;
        private ParcelFileDescriptor mAudioStreamParcelFileDescriptor;
        private long mBuilderFieldsSet = 0;
        private byte[] mInitialAudio;
        private PersistableBundle mMetadata;
        private AudioTimestamp mTimestamp;

        @Override // android.service.voice.HotwordAudioStream.BaseBuilder
        public /* bridge */ /* synthetic */ Builder setInitialAudio(byte[] bArr) {
            return super.setInitialAudio(bArr);
        }

        public Builder(AudioFormat audioFormat, ParcelFileDescriptor parcelFileDescriptor) {
            this.mAudioFormat = audioFormat;
            AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) audioFormat);
            this.mAudioStreamParcelFileDescriptor = parcelFileDescriptor;
            AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) parcelFileDescriptor);
        }

        public Builder setAudioFormat(AudioFormat audioFormat) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 1;
            this.mAudioFormat = audioFormat;
            return this;
        }

        public Builder setAudioStreamParcelFileDescriptor(ParcelFileDescriptor parcelFileDescriptor) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 2;
            this.mAudioStreamParcelFileDescriptor = parcelFileDescriptor;
            return this;
        }

        public Builder setTimestamp(AudioTimestamp audioTimestamp) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4;
            this.mTimestamp = audioTimestamp;
            return this;
        }

        public Builder setMetadata(PersistableBundle persistableBundle) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 8;
            this.mMetadata = persistableBundle;
            return this;
        }

        public HotwordAudioStream build() {
            checkNotUsed();
            long j = this.mBuilderFieldsSet | 32;
            this.mBuilderFieldsSet = j;
            if ((j & 4) == 0) {
                this.mTimestamp = HotwordAudioStream.defaultTimestamp();
            }
            if ((this.mBuilderFieldsSet & 8) == 0) {
                this.mMetadata = HotwordAudioStream.defaultMetadata();
            }
            if ((this.mBuilderFieldsSet & 16) == 0) {
                this.mInitialAudio = HotwordAudioStream.defaultInitialAudio();
            }
            return new HotwordAudioStream(this.mAudioFormat, this.mAudioStreamParcelFileDescriptor, this.mTimestamp, this.mMetadata, this.mInitialAudio);
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 32) != 0) {
                throw new IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }
}
