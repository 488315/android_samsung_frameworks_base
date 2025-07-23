package android.media;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class EncoderCapabilities {
    private static final String TAG = "EncoderCapabilities";

    private static final native AudioEncoderCap native_get_audio_encoder_cap(int i);

    private static final native int native_get_file_format(int i);

    private static final native int native_get_num_audio_encoders();

    private static final native int native_get_num_file_formats();

    private static final native int native_get_num_video_encoders();

    private static final native VideoEncoderCap native_get_video_encoder_cap(int i);

    private static final native void native_init();

    public static class VideoEncoderCap {
        public final int mCodec;
        public final int mMaxBitRate;
        public final int mMaxFrameHeight;
        public final int mMaxFrameRate;
        public final int mMaxFrameWidth;
        public final int mMinBitRate;
        public final int mMinFrameHeight;
        public final int mMinFrameRate;
        public final int mMinFrameWidth;

        private VideoEncoderCap(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
            this.mCodec = i;
            this.mMinBitRate = i2;
            this.mMaxBitRate = i3;
            this.mMinFrameRate = i4;
            this.mMaxFrameRate = i5;
            this.mMinFrameWidth = i6;
            this.mMaxFrameWidth = i7;
            this.mMinFrameHeight = i8;
            this.mMaxFrameHeight = i9;
        }
    }

    public static class AudioEncoderCap {
        public final int mCodec;
        public final int mMaxBitRate;
        public final int mMaxChannels;
        public final int mMaxSampleRate;
        public final int mMinBitRate;
        public final int mMinChannels;
        public final int mMinSampleRate;

        private AudioEncoderCap(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
            this.mCodec = i;
            this.mMinBitRate = i2;
            this.mMaxBitRate = i3;
            this.mMinSampleRate = i4;
            this.mMaxSampleRate = i5;
            this.mMinChannels = i6;
            this.mMaxChannels = i7;
        }
    }

    static {
        System.loadLibrary("media_jni");
        native_init();
    }

    public static int[] getOutputFileFormats() {
        int native_get_num_file_formats = native_get_num_file_formats();
        if (native_get_num_file_formats == 0) {
            return null;
        }
        int[] iArr = new int[native_get_num_file_formats];
        for (int i = 0; i < native_get_num_file_formats; i++) {
            iArr[i] = native_get_file_format(i);
        }
        return iArr;
    }

    public static List<VideoEncoderCap> getVideoEncoders() {
        int native_get_num_video_encoders = native_get_num_video_encoders();
        if (native_get_num_video_encoders == 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < native_get_num_video_encoders; i++) {
            arrayList.add(native_get_video_encoder_cap(i));
        }
        return arrayList;
    }

    public static List<AudioEncoderCap> getAudioEncoders() {
        int native_get_num_audio_encoders = native_get_num_audio_encoders();
        if (native_get_num_audio_encoders == 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < native_get_num_audio_encoders; i++) {
            arrayList.add(native_get_audio_encoder_cap(i));
        }
        return arrayList;
    }

    private EncoderCapabilities() {
    }
}
