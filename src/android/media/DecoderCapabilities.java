package android.media;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class DecoderCapabilities {

    public enum AudioDecoder {
        AUDIO_DECODER_WMA
    }

    public enum VideoDecoder {
        VIDEO_DECODER_WMV
    }

    private static final native int native_get_audio_decoder_type(int i);

    private static final native int native_get_num_audio_decoders();

    private static final native int native_get_num_video_decoders();

    private static final native int native_get_video_decoder_type(int i);

    private static final native void native_init();

    static {
        System.loadLibrary("media_jni");
        native_init();
    }

    public static List<VideoDecoder> getVideoDecoders() {
        ArrayList arrayList = new ArrayList();
        int iNative_get_num_video_decoders = native_get_num_video_decoders();
        for (int i = 0; i < iNative_get_num_video_decoders; i++) {
            arrayList.add(VideoDecoder.values()[native_get_video_decoder_type(i)]);
        }
        return arrayList;
    }

    public static List<AudioDecoder> getAudioDecoders() {
        ArrayList arrayList = new ArrayList();
        int iNative_get_num_audio_decoders = native_get_num_audio_decoders();
        for (int i = 0; i < iNative_get_num_audio_decoders; i++) {
            arrayList.add(AudioDecoder.values()[native_get_audio_decoder_type(i)]);
        }
        return arrayList;
    }

    private DecoderCapabilities() {
    }
}
