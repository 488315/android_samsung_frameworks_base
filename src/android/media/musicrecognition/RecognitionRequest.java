package android.media.musicrecognition;

import android.annotation.SystemApi;
import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

@SystemApi
/* loaded from: classes3.dex */
public final class RecognitionRequest implements Parcelable {
    public static final Parcelable.Creator<RecognitionRequest> CREATOR = new Parcelable.Creator<RecognitionRequest>() { // from class: android.media.musicrecognition.RecognitionRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RecognitionRequest createFromParcel(Parcel parcel) {
            return new RecognitionRequest(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RecognitionRequest[] newArray(int i) {
            return new RecognitionRequest[i];
        }
    };
    private final AudioAttributes mAudioAttributes;
    private final AudioFormat mAudioFormat;
    private final int mCaptureSession;
    private final int mIgnoreBeginningFrames;
    private final int mMaxAudioLengthSeconds;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private RecognitionRequest(Builder builder) {
        this.mAudioAttributes = (AudioAttributes) Objects.requireNonNull(builder.mAudioAttributes);
        this.mAudioFormat = (AudioFormat) Objects.requireNonNull(builder.mAudioFormat);
        this.mCaptureSession = builder.mCaptureSession;
        this.mMaxAudioLengthSeconds = builder.mMaxAudioLengthSeconds;
        this.mIgnoreBeginningFrames = builder.mIgnoreBeginningFrames;
    }

    public AudioAttributes getAudioAttributes() {
        return this.mAudioAttributes;
    }

    public AudioFormat getAudioFormat() {
        return this.mAudioFormat;
    }

    public int getCaptureSession() {
        return this.mCaptureSession;
    }

    public int getMaxAudioLengthSeconds() {
        return this.mMaxAudioLengthSeconds;
    }

    public int getIgnoreBeginningFrames() {
        return this.mIgnoreBeginningFrames;
    }

    @SystemApi
    public static final class Builder {
        private AudioFormat mAudioFormat = new AudioFormat.Builder().setSampleRate(16000).setEncoding(2).build();
        private AudioAttributes mAudioAttributes = new AudioAttributes.Builder().setContentType(2).build();
        private int mCaptureSession = 1;
        private int mMaxAudioLengthSeconds = 24;
        private int mIgnoreBeginningFrames = 0;

        public Builder setAudioAttributes(AudioAttributes audioAttributes) {
            this.mAudioAttributes = audioAttributes;
            return this;
        }

        public Builder setAudioFormat(AudioFormat audioFormat) {
            this.mAudioFormat = audioFormat;
            return this;
        }

        public Builder setCaptureSession(int i) {
            this.mCaptureSession = i;
            return this;
        }

        public Builder setMaxAudioLengthSeconds(int i) {
            this.mMaxAudioLengthSeconds = i;
            return this;
        }

        public Builder setIgnoreBeginningFrames(int i) {
            this.mIgnoreBeginningFrames = i;
            return this;
        }

        public RecognitionRequest build() {
            return new RecognitionRequest(this);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.mAudioFormat, i);
        parcel.writeParcelable(this.mAudioAttributes, i);
        parcel.writeInt(this.mCaptureSession);
        parcel.writeInt(this.mMaxAudioLengthSeconds);
        parcel.writeInt(this.mIgnoreBeginningFrames);
    }

    private RecognitionRequest(Parcel parcel) {
        this.mAudioFormat = (AudioFormat) parcel.readParcelable(AudioFormat.class.getClassLoader(), AudioFormat.class);
        this.mAudioAttributes = (AudioAttributes) parcel.readParcelable(AudioAttributes.class.getClassLoader(), AudioAttributes.class);
        this.mCaptureSession = parcel.readInt();
        this.mMaxAudioLengthSeconds = parcel.readInt();
        this.mIgnoreBeginningFrames = parcel.readInt();
    }
}
