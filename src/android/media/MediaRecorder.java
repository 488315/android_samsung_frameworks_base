package android.media;

import android.annotation.SystemApi;
import android.app.ActivityThread;
import android.content.AttributionSource;
import android.content.Context;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.AudioRouting;
import android.media.EncoderProfiles;
import android.media.MediaCodec;
import android.media.metrics.LogSessionId;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.PersistableBundle;
import android.os.SystemClock;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Pair;
import android.view.Surface;
import com.android.internal.util.Preconditions;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executor;

/* loaded from: classes2.dex */
public class MediaRecorder implements AudioRouting, AudioRecordingMonitor, AudioRecordingMonitorClient, MicrophoneDirection {
    public static final int MEDIA_ERROR_SERVER_DIED = 100;
    public static final int MEDIA_RECORDER_ERROR_UNKNOWN = 1;
    public static final int MEDIA_RECORDER_INFO_MAX_DURATION_REACHED = 800;
    public static final int MEDIA_RECORDER_INFO_MAX_FILESIZE_APPROACHING = 802;
    public static final int MEDIA_RECORDER_INFO_MAX_FILESIZE_REACHED = 801;
    public static final int MEDIA_RECORDER_INFO_NEXT_OUTPUT_FILE_STARTED = 803;
    public static final int MEDIA_RECORDER_INFO_UNKNOWN = 1;
    public static final int MEDIA_RECORDER_TRACK_INFO_COMPLETION_STATUS = 1000;
    public static final int MEDIA_RECORDER_TRACK_INFO_DATA_KBYTES = 1009;
    public static final int MEDIA_RECORDER_TRACK_INFO_DURATION_MS = 1003;
    public static final int MEDIA_RECORDER_TRACK_INFO_ENCODED_FRAMES = 1005;
    public static final int MEDIA_RECORDER_TRACK_INFO_INITIAL_DELAY_MS = 1007;
    public static final int MEDIA_RECORDER_TRACK_INFO_LIST_END = 2000;
    public static final int MEDIA_RECORDER_TRACK_INFO_LIST_START = 1000;
    public static final int MEDIA_RECORDER_TRACK_INFO_MAX_CHUNK_DUR_MS = 1004;
    public static final int MEDIA_RECORDER_TRACK_INFO_PROGRESS_IN_TIME = 1001;
    public static final int MEDIA_RECORDER_TRACK_INFO_START_OFFSET_MS = 1008;
    public static final int MEDIA_RECORDER_TRACK_INFO_TYPE = 1002;
    public static final int MEDIA_RECORDER_TRACK_INTER_CHUNK_TIME_MS = 1006;
    public static final int SEM_CAMERA_LENS_TYPE_FRONT_STANDARD = 21;
    public static final int SEM_CAMERA_LENS_TYPE_FRONT_WIDE = 22;
    public static final int SEM_CAMERA_LENS_TYPE_MACRO = 6;
    public static final int SEM_CAMERA_LENS_TYPE_STANDARD = 1;
    public static final int SEM_CAMERA_LENS_TYPE_TELE = 4;
    public static final int SEM_CAMERA_LENS_TYPE_UNKNOWN = 0;
    public static final int SEM_CAMERA_LENS_TYPE_UTELE = 5;
    public static final int SEM_CAMERA_LENS_TYPE_UW = 3;
    public static final int SEM_CAMERA_LENS_TYPE_WIDE = 2;
    public static final int SEM_FILESIZE_INTERVAL_UNIT_BYTE = 903;
    public static final int SEM_FILESIZE_INTERVAL_UNIT_KILOBYTE = 904;
    public static final int SEM_MEDIA_RECORDER_INFO_DURATION_IN_PROGRESS = 901;
    public static final int SEM_MEDIA_RECORDER_INFO_FILESIZE_IN_PROGRESS = 900;
    public static final int SEM_MEDIA_RECORDER_INFO_FILESIZE_IN_PROGRESS_KILOBYTE = 902;
    public static final int SEM_MEDIA_RECORDER_TRACK_INFO_CURRENT_CHUNKS = 906;
    public static final int SEM_MEDIA_RECORDER_TRACK_INFO_STARTED = 905;
    public static final int SEM_VIDEO_FLIP_AXIS_BOTH = 3;
    public static final int SEM_VIDEO_FLIP_AXIS_HORIZONTAL = 2;
    public static final int SEM_VIDEO_FLIP_AXIS_NONE = 0;
    public static final int SEM_VIDEO_FLIP_AXIS_VERTICAL = 1;
    private static final String TAG = "MediaRecorder";
    private int mChannelCount;
    private EventHandler mEventHandler;
    private FileDescriptor mFd;
    private File mFile;
    private LogSessionId mLogSessionId;
    private long mNativeContext;
    private OnErrorListener mOnErrorListener;
    private OnInfoListener mOnInfoListener;
    private String mPath;
    private AudioDeviceInfo mPreferredDevice;
    AudioRecordingMonitorImpl mRecordingInfoImpl;
    private ArrayMap<AudioRouting.OnRoutingChangedListener, NativeRoutingEventHandlerDelegate> mRoutingChangeListeners;
    private Surface mSurface;

    @Retention(RetentionPolicy.SOURCE)
    public @interface AudioEncoderValues {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface CameraLensType {
    }

    public interface OnErrorListener {
        void onError(MediaRecorder mediaRecorder, int i, int i2);
    }

    public interface OnInfoListener {
        void onInfo(MediaRecorder mediaRecorder, int i, int i2);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface OutputFormatValues {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Source {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SystemSource {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface VideoEncoderValues {
    }

    private native void _prepare() throws IllegalStateException, IOException;

    private native void _setNextOutputFile(FileDescriptor fileDescriptor) throws IllegalStateException, IOException;

    private native void _setOutputFile(FileDescriptor fileDescriptor) throws IllegalStateException, IOException;

    public static final int getAudioSourceMax() {
        return 19;
    }

    public static boolean isSemSystemOnlyAudioSource(int i) {
        switch (i) {
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
                return false;
            default:
                return true;
        }
    }

    public static boolean isSystemOnlyAudioSource(int i) {
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 9:
            case 10:
                return false;
            case 8:
            default:
                return true;
        }
    }

    public static boolean isValidAudioSource(int i) {
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                return true;
            default:
                switch (i) {
                    case 1997:
                    case 1998:
                    case 1999:
                    case 2000:
                        return true;
                    default:
                        return false;
                }
        }
    }

    public static boolean isValidAudioSourceForSem(int i) {
        switch (i) {
            case 11:
            case 12:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
                return true;
            case 13:
            default:
                return false;
        }
    }

    private static final native SemPersistentSurface native_createSemPersistentSurface();

    private final native void native_enableDeviceCallback(boolean z);

    private native void native_finalize();

    private final native int native_getActiveMicrophones(ArrayList<MicrophoneInfo> arrayList);

    private native PersistableBundle native_getMetrics();

    private native int native_getPortId();

    private native int[] native_getRoutedDeviceIds();

    private static final native void native_init();

    /* JADX INFO: Access modifiers changed from: private */
    public static final native void native_releaseSemPersistentSurface(Surface surface);

    private native void native_reset();

    private native void native_semCreatePersistentSurfaceTrack(String str, String[] strArr, Object[] objArr, Surface surface);

    private native Surface native_semCreateSurfaceTrack(String str, String[] strArr, Object[] objArr);

    private final native boolean native_setInputDevice(int i);

    private final native void native_setInputSurface(Surface surface);

    private native int native_setPreferredMicrophoneDirection(int i);

    private native int native_setPreferredMicrophoneFieldDimension(float f);

    private native void native_setup(Object obj, String str, Parcel parcel) throws IllegalStateException;

    public static int semGetInputSource(int i) {
        if (i == 1) {
            return 11;
        }
        if (i == 12) {
            return 19;
        }
        switch (i) {
            case 3:
                return 12;
            case 4:
                return 7;
            case 5:
                return 14;
            case 6:
                return 15;
            case 7:
                return 16;
            case 8:
                return 17;
            case 9:
                return 18;
            default:
                return 0;
        }
    }

    private static native boolean semNativeIsRecording();

    private final native void semNativeSendcommand(int i, int i2, int i3);

    private final native void semNativeSetCameraLensInfo(int i, int i2, int i3);

    private native void semNativeSetDurationInterval(int i) throws IllegalArgumentException;

    private native void semNativeSetFileSizeInterval(long j, int i) throws IllegalArgumentException;

    private final native void semNativeSetStereoCameraInfo(float f, float f2, float f3);

    private native void setParameter(String str);

    public native int getMaxAmplitude() throws IllegalStateException;

    public native Surface getSurface();

    public native boolean isPrivacySensitive();

    public native void pause() throws IllegalStateException;

    public native void release();

    public native void resume() throws IllegalStateException;

    public native void setAudioEncoder(int i) throws IllegalStateException;

    public native void setAudioSource(int i) throws IllegalStateException;

    @Deprecated
    public native void setCamera(Camera camera);

    public native void setMaxDuration(int i) throws IllegalArgumentException;

    public native void setMaxFileSize(long j) throws IllegalArgumentException;

    public native void setOutputFormat(int i) throws IllegalStateException;

    public native void setPrivacySensitive(boolean z);

    public native void setVideoEncoder(int i) throws IllegalStateException;

    public native void setVideoFrameRate(int i) throws IllegalStateException;

    public native void setVideoSize(int i, int i2) throws IllegalStateException;

    public native void setVideoSource(int i) throws IllegalStateException;

    public native void start() throws IllegalStateException;

    public native void stop() throws IllegalStateException;

    static {
        System.loadLibrary("media_jni");
        native_init();
    }

    @Deprecated
    public MediaRecorder() {
        this(ActivityThread.currentApplication());
    }

    public MediaRecorder(Context context) {
        this.mLogSessionId = LogSessionId.LOG_SESSION_ID_NONE;
        this.mPreferredDevice = null;
        this.mRoutingChangeListeners = new ArrayMap<>();
        this.mRecordingInfoImpl = new AudioRecordingMonitorImpl(this);
        Objects.requireNonNull(context);
        Log.d(TAG, "Constructor MediaRecorder");
        Looper looperMyLooper = Looper.myLooper();
        if (looperMyLooper != null) {
            this.mEventHandler = new EventHandler(this, looperMyLooper);
        } else {
            Looper mainLooper = Looper.getMainLooper();
            if (mainLooper != null) {
                this.mEventHandler = new EventHandler(this, mainLooper);
            } else {
                this.mEventHandler = null;
            }
        }
        this.mChannelCount = 1;
        AttributionSource.ScopedParcelState scopedParcelStateAsScopedParcelState = context.getAttributionSource().asScopedParcelState();
        try {
            native_setup(new WeakReference(this), ActivityThread.currentPackageName(), scopedParcelStateAsScopedParcelState.getParcel());
            if (scopedParcelStateAsScopedParcelState != null) {
                scopedParcelStateAsScopedParcelState.close();
            }
        } catch (Throwable th) {
            if (scopedParcelStateAsScopedParcelState != null) {
                try {
                    scopedParcelStateAsScopedParcelState.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public void setLogSessionId(LogSessionId logSessionId) {
        Objects.requireNonNull(logSessionId);
        this.mLogSessionId = logSessionId;
        setParameter("log-session-id=" + logSessionId.getStringId());
    }

    public LogSessionId getLogSessionId() {
        return this.mLogSessionId;
    }

    public void setInputSurface(Surface surface) {
        if (!(surface instanceof MediaCodec.PersistentSurface)) {
            throw new IllegalArgumentException("not a PersistentSurface");
        }
        native_setInputSurface(surface);
    }

    public void setPreviewDisplay(Surface surface) {
        this.mSurface = surface;
    }

    public final class AudioSource {
        private static final int AUDIOSOURCE_OFFSET = 10;
        public static final int AUDIO_SOURCE_INVALID = -1;
        public static final int CAMCORDER = 5;
        public static final int DEFAULT = 0;

        @SystemApi
        public static final int ECHO_REFERENCE = 1997;

        @SystemApi
        public static final int HOTWORD = 1999;
        public static final int MIC = 1;

        @SystemApi
        public static final int RADIO_TUNER = 1998;
        public static final int REMOTE_SUBMIX = 8;
        public static final int SEC_2MIC_SVOICE_DRIVING = 14;
        public static final int SEC_2MIC_SVOICE_NORMAL = 15;
        public static final int SEC_BARGEIN_DRIVING = 16;
        public static final int SEC_BEAMFORMING = 18;
        public static final int SEC_CAMCORDER = 17;
        public static final int SEC_FM_RX = 11;
        public static final int SEC_PLAYBACK_RECORD = 19;
        public static final int SEC_VOICE_COMMUNICATION = 13;
        public static final int SEC_VOICE_RECOGNITION = 12;
        public static final int SEM_2MIC_SVOICE_DRIVING = 5;
        public static final int SEM_AUDIOSOURCE_MAX = 19;
        public static final int SEM_BARGE_IN_DRIVING = 7;
        public static final int SEM_BEAMFORMING = 9;
        public static final int SEM_CAMCORDER = 8;
        public static final int SEM_DUAL_MICROPHONE_VOICE_RECOGNITION = 6;
        public static final int SEM_FM_RX = 1;
        public static final int SEM_HOTWORD = 1999;
        public static final int SEM_PLAYBACK_RECORD = 12;
        public static final int SEM_VOICE_COMMUNICATION = 4;
        public static final int SEM_VOICE_RECOGNITION = 3;

        @SystemApi
        public static final int ULTRASOUND = 2000;
        public static final int UNPROCESSED = 9;
        public static final int VOICE_CALL = 4;
        public static final int VOICE_COMMUNICATION = 7;
        public static final int VOICE_DOWNLINK = 3;
        public static final int VOICE_PERFORMANCE = 10;
        public static final int VOICE_RECOGNITION = 6;
        public static final int VOICE_UPLINK = 2;

        private AudioSource(MediaRecorder mediaRecorder) {
        }
    }

    public static final String toLogFriendlyAudioSource(int i) {
        switch (i) {
            case -1:
                return "AUDIO_SOURCE_INVALID";
            case 0:
                return "DEFAULT";
            case 1:
                return "MIC";
            case 2:
                return "VOICE_UPLINK";
            case 3:
                return "VOICE_DOWNLINK";
            case 4:
                return "VOICE_CALL";
            case 5:
                return "CAMCORDER";
            case 6:
                return "VOICE_RECOGNITION";
            case 7:
                return "VOICE_COMMUNICATION";
            case 8:
                return "REMOTE_SUBMIX";
            case 9:
                return "UNPROCESSED";
            case 10:
                return "VOICE_PERFORMANCE";
            default:
                switch (i) {
                    case 12:
                        return "SEC_VOICE_RECOGNITION";
                    case 13:
                        return "SEC_VOICE_COMMUNICATION";
                    case 14:
                        return "SEC_2MIC_SVOICE_DRIVING";
                    case 15:
                        return "SEC_2MIC_SVOICE_NORMAL";
                    case 16:
                        return "SEC_BARGEIN_DRIVING";
                    case 17:
                        return "SEC_CAMCORDER";
                    case 18:
                        return "SEC_BEAMFORMING";
                    default:
                        switch (i) {
                            case 1997:
                                return "ECHO_REFERENCE";
                            case 1998:
                                return "RADIO_TUNER";
                            case 1999:
                                return "HOTWORD";
                            case 2000:
                                return "ULTRASOUND";
                            default:
                                return "unknown source " + i;
                        }
                }
        }
    }

    public final class VideoSource {
        public static final int CAMERA = 1;
        public static final int DEFAULT = 0;
        public static final int SURFACE = 2;

        private VideoSource(MediaRecorder mediaRecorder) {
        }
    }

    public final class OutputFormat {
        public static final int AAC_ADIF = 5;
        public static final int AAC_ADTS = 6;
        public static final int AMR_NB = 3;
        public static final int AMR_WB = 4;
        public static final int DEFAULT = 0;
        public static final int HEIF = 10;
        public static final int MPEG_2_TS = 8;
        public static final int MPEG_4 = 2;
        public static final int OGG = 11;
        public static final int OUTPUT_FORMAT_RTP_AVP = 7;
        public static final int RAW_AMR = 3;
        public static final int THREE_GPP = 1;
        public static final int WEBM = 9;

        private OutputFormat(MediaRecorder mediaRecorder) {
        }
    }

    public final class AudioEncoder {
        public static final int AAC = 3;
        public static final int AAC_ELD = 5;
        public static final int AMR_NB = 1;
        public static final int AMR_WB = 2;
        public static final int DEFAULT = 0;
        public static final int HE_AAC = 4;
        public static final int OPUS = 7;
        public static final int VORBIS = 6;

        private AudioEncoder(MediaRecorder mediaRecorder) {
        }
    }

    public final class VideoEncoder {
        public static final int AV1 = 8;
        public static final int DEFAULT = 0;
        public static final int DOLBY_VISION = 7;
        public static final int H263 = 1;
        public static final int H264 = 2;
        public static final int HEVC = 5;
        public static final int MPEG_4_SP = 3;
        public static final int VP8 = 4;
        public static final int VP9 = 6;

        private VideoEncoder(MediaRecorder mediaRecorder) {
        }
    }

    public void setProfile(CamcorderProfile camcorderProfile) throws IllegalStateException {
        setOutputFormat(camcorderProfile.fileFormat);
        setVideoFrameRate(camcorderProfile.videoFrameRate);
        setVideoSize(camcorderProfile.videoFrameWidth, camcorderProfile.videoFrameHeight);
        setVideoEncodingBitRate(camcorderProfile.videoBitRate);
        setVideoEncoder(camcorderProfile.videoCodec);
        if (camcorderProfile.quality < 1000 || camcorderProfile.quality > 1007) {
            setAudioEncodingBitRate(camcorderProfile.audioBitRate);
            setAudioChannels(camcorderProfile.audioChannels);
            setAudioSamplingRate(camcorderProfile.audioSampleRate);
            setAudioEncoder(camcorderProfile.audioCodec);
        }
    }

    public void setAudioProfile(EncoderProfiles.AudioProfile audioProfile) throws IllegalStateException {
        setAudioEncodingBitRate(audioProfile.getBitrate());
        setAudioChannels(audioProfile.getChannels());
        setAudioSamplingRate(audioProfile.getSampleRate());
        setAudioEncoder(audioProfile.getCodec());
    }

    public void setVideoProfile(EncoderProfiles.VideoProfile videoProfile) throws IllegalStateException {
        setVideoFrameRate(videoProfile.getFrameRate());
        setVideoSize(videoProfile.getWidth(), videoProfile.getHeight());
        setVideoEncodingBitRate(videoProfile.getBitrate());
        setVideoEncoder(videoProfile.getCodec());
        if (videoProfile.getProfile() >= 0) {
            setVideoEncodingProfileLevel(videoProfile.getProfile(), 0);
        }
    }

    public void setCaptureRate(double d) {
        setParameter("time-lapse-enable=1");
        setParameter("time-lapse-fps=" + d);
    }

    public void setOrientationHint(int i) {
        if (i != 0 && i != 90 && i != 180 && i != 270) {
            throw new IllegalArgumentException("Unsupported angle: " + i);
        }
        setParameter("video-param-rotation-angle-degrees=" + i);
    }

    public void setLocation(float f, float f2) {
        int i = (int) ((f * 10000.0f) + 0.5d);
        int i2 = (int) ((10000.0f * f2) + 0.5d);
        if (i > 900000 || i < -900000) {
            throw new IllegalArgumentException("Latitude: " + f + " out of range.");
        }
        if (i2 > 1800000 || i2 < -1800000) {
            throw new IllegalArgumentException("Longitude: " + f2 + " out of range");
        }
        setParameter("param-geotag-latitude=" + i);
        setParameter("param-geotag-longitude=" + i2);
    }

    public void semSetDurationInterval(int i) throws IllegalArgumentException {
        semNativeSetDurationInterval(i);
    }

    public void semSetFileSizeInterval(long j) throws IllegalArgumentException {
        semNativeSetFileSizeInterval(j, 903);
    }

    public void semSetFileSizeInterval(long j, int i) throws IllegalArgumentException {
        semNativeSetFileSizeInterval(j, i);
    }

    public void setAudioSamplingRate(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("Audio sampling rate is not positive");
        }
        setParameter("audio-param-sampling-rate=" + i);
    }

    public void setAudioChannels(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("Number of channels is not positive");
        }
        this.mChannelCount = i;
        setParameter("audio-param-number-of-channels=" + i);
    }

    public void setAudioEncodingBitRate(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("Audio encoding bit rate is not positive");
        }
        setParameter("audio-param-encoding-bitrate=" + i);
    }

    public void setVideoEncodingBitRate(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("Video encoding bit rate is not positive");
        }
        setParameter("video-param-encoding-bitrate=" + i);
    }

    public void setVideoEncodingProfileLevel(int i, int i2) {
        if (i < 0) {
            throw new IllegalArgumentException("Video encoding profile is not positive");
        }
        if (i2 < 0) {
            throw new IllegalArgumentException("Video encoding level is not positive");
        }
        setParameter("video-param-encoder-profile=" + i);
        setParameter("video-param-encoder-level=" + i2);
    }

    public void setAuxiliaryOutputFile(FileDescriptor fileDescriptor) {
        Log.w(TAG, "setAuxiliaryOutputFile(FileDescriptor) is no longer supported.");
    }

    public void setAuxiliaryOutputFile(String str) {
        Log.w(TAG, "setAuxiliaryOutputFile(String) is no longer supported.");
    }

    public void setOutputFile(FileDescriptor fileDescriptor) throws IllegalStateException {
        this.mPath = null;
        this.mFile = null;
        this.mFd = fileDescriptor;
    }

    public void setOutputFile(File file) {
        this.mPath = null;
        this.mFd = null;
        this.mFile = file;
    }

    public void setNextOutputFile(FileDescriptor fileDescriptor) throws IllegalStateException, IOException {
        _setNextOutputFile(fileDescriptor);
    }

    public void setOutputFile(String str) throws IllegalStateException {
        this.mFd = null;
        this.mFile = null;
        this.mPath = str;
    }

    public void setNextOutputFile(File file) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
        try {
            _setNextOutputFile(randomAccessFile.getFD());
        } finally {
            randomAccessFile.close();
        }
    }

    public void prepare() throws IllegalStateException, IOException {
        Log.i(TAG, "prepare");
        long jUptimeMillis = SystemClock.uptimeMillis();
        if (this.mPath != null) {
            RandomAccessFile randomAccessFile = new RandomAccessFile(this.mPath, "rw");
            try {
                _setOutputFile(randomAccessFile.getFD());
            } finally {
                randomAccessFile.close();
            }
        } else {
            FileDescriptor fileDescriptor = this.mFd;
            if (fileDescriptor != null) {
                _setOutputFile(fileDescriptor);
            } else if (this.mFile != null) {
                RandomAccessFile randomAccessFile2 = new RandomAccessFile(this.mFile, "rw");
                try {
                    _setOutputFile(randomAccessFile2.getFD());
                } finally {
                    randomAccessFile2.close();
                }
            } else {
                throw new IOException("No valid output file");
            }
        }
        _prepare();
        Log.i(TAG, "prepare elapsed time : " + (SystemClock.uptimeMillis() - jUptimeMillis) + " ms");
    }

    public void reset() {
        native_reset();
        this.mEventHandler.removeCallbacksAndMessages(null);
    }

    public void setOnErrorListener(OnErrorListener onErrorListener) {
        this.mOnErrorListener = onErrorListener;
    }

    public void setOnInfoListener(OnInfoListener onInfoListener) {
        this.mOnInfoListener = onInfoListener;
    }

    private class EventHandler extends Handler {
        private static final int MEDIA_RECORDER_AUDIO_ROUTING_CHANGED = 10000;
        private static final int MEDIA_RECORDER_EVENT_ERROR = 1;
        private static final int MEDIA_RECORDER_EVENT_INFO = 2;
        private static final int MEDIA_RECORDER_EVENT_LIST_END = 99;
        private static final int MEDIA_RECORDER_EVENT_LIST_START = 1;
        private static final int MEDIA_RECORDER_TRACK_EVENT_ERROR = 100;
        private static final int MEDIA_RECORDER_TRACK_EVENT_INFO = 101;
        private static final int MEDIA_RECORDER_TRACK_EVENT_LIST_END = 1000;
        private static final int MEDIA_RECORDER_TRACK_EVENT_LIST_START = 100;
        private MediaRecorder mMediaRecorder;

        public EventHandler(MediaRecorder mediaRecorder, Looper looper) {
            super(looper);
            this.mMediaRecorder = mediaRecorder;
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (this.mMediaRecorder.mNativeContext == 0) {
                Log.w(MediaRecorder.TAG, "mediarecorder went away with unhandled events");
                return;
            }
            int i = message.what;
            if (i != 1) {
                if (i != 2) {
                    if (i != 100) {
                        if (i != 101) {
                            if (i == 10000) {
                                AudioManager.resetAudioPortGeneration();
                                synchronized (MediaRecorder.this.mRoutingChangeListeners) {
                                    Iterator it = MediaRecorder.this.mRoutingChangeListeners.values().iterator();
                                    while (it.hasNext()) {
                                        ((NativeRoutingEventHandlerDelegate) it.next()).notifyClient();
                                    }
                                }
                                return;
                            }
                            Log.e(MediaRecorder.TAG, "Unknown message type " + message.what);
                            return;
                        }
                    }
                }
                if (MediaRecorder.this.mOnInfoListener != null) {
                    MediaRecorder.this.mOnInfoListener.onInfo(this.mMediaRecorder, message.arg1, message.arg2);
                    return;
                }
                return;
            }
            if (MediaRecorder.this.mOnErrorListener != null) {
                MediaRecorder.this.mOnErrorListener.onError(this.mMediaRecorder, message.arg1, message.arg2);
            }
        }
    }

    @Override // android.media.AudioRouting
    public boolean setPreferredDevice(AudioDeviceInfo audioDeviceInfo) {
        if (audioDeviceInfo != null && !audioDeviceInfo.isSource()) {
            return false;
        }
        int id = audioDeviceInfo != null ? audioDeviceInfo.getId() : 0;
        setParameter("param-meta-audio-devicetype=" + (audioDeviceInfo != null ? audioDeviceInfo.getType() : 0));
        boolean zNative_setInputDevice = native_setInputDevice(id);
        if (!zNative_setInputDevice) {
            return zNative_setInputDevice;
        }
        synchronized (this) {
            this.mPreferredDevice = audioDeviceInfo;
        }
        return zNative_setInputDevice;
    }

    @Override // android.media.AudioRouting
    public AudioDeviceInfo getPreferredDevice() {
        AudioDeviceInfo audioDeviceInfo;
        synchronized (this) {
            audioDeviceInfo = this.mPreferredDevice;
        }
        return audioDeviceInfo;
    }

    private List<AudioDeviceInfo> getRoutedDevicesInternal() {
        ArrayList arrayList = new ArrayList();
        int[] iArrNative_getRoutedDeviceIds = native_getRoutedDeviceIds();
        if (iArrNative_getRoutedDeviceIds != null && iArrNative_getRoutedDeviceIds.length != 0) {
            for (int i : iArrNative_getRoutedDeviceIds) {
                AudioDeviceInfo deviceForPortId = AudioManager.getDeviceForPortId(i, 1);
                if (deviceForPortId != null) {
                    arrayList.add(deviceForPortId);
                }
            }
        }
        return arrayList;
    }

    @Override // android.media.AudioRouting
    public AudioDeviceInfo getRoutedDevice() {
        List<AudioDeviceInfo> routedDevicesInternal = getRoutedDevicesInternal();
        if (routedDevicesInternal.isEmpty()) {
            return null;
        }
        return routedDevicesInternal.get(0);
    }

    @Override // android.media.AudioRouting
    public List<AudioDeviceInfo> getRoutedDevices() {
        return getRoutedDevicesInternal();
    }

    private void enableNativeRoutingCallbacksLocked(boolean z) {
        if (this.mRoutingChangeListeners.size() == 0) {
            native_enableDeviceCallback(z);
        }
    }

    @Override // android.media.AudioRouting
    public void addOnRoutingChangedListener(AudioRouting.OnRoutingChangedListener onRoutingChangedListener, Handler handler) {
        synchronized (this.mRoutingChangeListeners) {
            if (onRoutingChangedListener != null) {
                if (!this.mRoutingChangeListeners.containsKey(onRoutingChangedListener)) {
                    enableNativeRoutingCallbacksLocked(true);
                    ArrayMap<AudioRouting.OnRoutingChangedListener, NativeRoutingEventHandlerDelegate> arrayMap = this.mRoutingChangeListeners;
                    if (handler == null) {
                        handler = this.mEventHandler;
                    }
                    arrayMap.put(onRoutingChangedListener, new NativeRoutingEventHandlerDelegate(this, onRoutingChangedListener, handler));
                }
            }
        }
    }

    @Override // android.media.AudioRouting
    public void removeOnRoutingChangedListener(AudioRouting.OnRoutingChangedListener onRoutingChangedListener) {
        synchronized (this.mRoutingChangeListeners) {
            if (this.mRoutingChangeListeners.containsKey(onRoutingChangedListener)) {
                this.mRoutingChangeListeners.remove(onRoutingChangedListener);
                enableNativeRoutingCallbacksLocked(false);
            }
        }
    }

    public List<MicrophoneInfo> getActiveMicrophones() throws IOException {
        AudioDeviceInfo routedDevice;
        ArrayList<MicrophoneInfo> arrayList = new ArrayList<>();
        int iNative_getActiveMicrophones = native_getActiveMicrophones(arrayList);
        if (iNative_getActiveMicrophones != 0) {
            if (iNative_getActiveMicrophones != -3) {
                Log.e(TAG, "getActiveMicrophones failed:" + iNative_getActiveMicrophones);
            }
            Log.i(TAG, "getActiveMicrophones failed, fallback on routed device info");
        }
        AudioManager.setPortIdForMicrophones(arrayList);
        if (arrayList.size() == 0 && (routedDevice = getRoutedDevice()) != null) {
            MicrophoneInfo microphoneInfoMicrophoneInfoFromAudioDeviceInfo = AudioManager.microphoneInfoFromAudioDeviceInfo(routedDevice);
            ArrayList arrayList2 = new ArrayList();
            for (int i = 0; i < this.mChannelCount; i++) {
                arrayList2.add(new Pair(Integer.valueOf(i), 1));
            }
            microphoneInfoMicrophoneInfoFromAudioDeviceInfo.setChannelMapping(arrayList2);
            arrayList.add(microphoneInfoMicrophoneInfoFromAudioDeviceInfo);
        }
        return arrayList;
    }

    @Override // android.media.MicrophoneDirection
    public boolean setPreferredMicrophoneDirection(int i) {
        return native_setPreferredMicrophoneDirection(i) == 0;
    }

    @Override // android.media.MicrophoneDirection
    public boolean setPreferredMicrophoneFieldDimension(float f) {
        Preconditions.checkArgument(f >= -1.0f && f <= 1.0f, "Argument must fall between -1 & 1 (inclusive)");
        return native_setPreferredMicrophoneFieldDimension(f) == 0;
    }

    @Override // android.media.AudioRecordingMonitor
    public void registerAudioRecordingCallback(Executor executor, AudioManager.AudioRecordingCallback audioRecordingCallback) {
        this.mRecordingInfoImpl.registerAudioRecordingCallback(executor, audioRecordingCallback);
    }

    @Override // android.media.AudioRecordingMonitor
    public void unregisterAudioRecordingCallback(AudioManager.AudioRecordingCallback audioRecordingCallback) {
        this.mRecordingInfoImpl.unregisterAudioRecordingCallback(audioRecordingCallback);
    }

    @Override // android.media.AudioRecordingMonitor
    public AudioRecordingConfiguration getActiveRecordingConfiguration() {
        return this.mRecordingInfoImpl.getActiveRecordingConfiguration();
    }

    @Override // android.media.AudioRecordingMonitorClient
    public int getPortId() {
        if (this.mNativeContext == 0) {
            return 0;
        }
        return native_getPortId();
    }

    private static void postEventFromNative(Object obj, int i, int i2, int i3, Object obj2) {
        EventHandler eventHandler;
        MediaRecorder mediaRecorder = (MediaRecorder) ((WeakReference) obj).get();
        if (mediaRecorder == null || (eventHandler = mediaRecorder.mEventHandler) == null) {
            return;
        }
        mediaRecorder.mEventHandler.sendMessage(eventHandler.obtainMessage(i, i2, i3, obj2));
    }

    private void native_setup(Object obj, String str, String str2) throws IllegalStateException {
        AttributionSource.ScopedParcelState scopedParcelStateAsScopedParcelState = AttributionSource.myAttributionSource().withPackageName(str2).asScopedParcelState();
        try {
            native_setup(obj, str, scopedParcelStateAsScopedParcelState.getParcel());
            if (scopedParcelStateAsScopedParcelState != null) {
                scopedParcelStateAsScopedParcelState.close();
            }
        } catch (Throwable th) {
            if (scopedParcelStateAsScopedParcelState != null) {
                try {
                    scopedParcelStateAsScopedParcelState.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public PersistableBundle getMetrics() {
        return native_getMetrics();
    }

    protected void finalize() {
        native_finalize();
    }

    public static final class MetricsConstants {
        public static final String AUDIO_BITRATE = "android.media.mediarecorder.audio-bitrate";
        public static final String AUDIO_CHANNELS = "android.media.mediarecorder.audio-channels";
        public static final String AUDIO_SAMPLERATE = "android.media.mediarecorder.audio-samplerate";
        public static final String AUDIO_TIMESCALE = "android.media.mediarecorder.audio-timescale";
        public static final String CAPTURE_FPS = "android.media.mediarecorder.capture-fps";
        public static final String CAPTURE_FPS_ENABLE = "android.media.mediarecorder.capture-fpsenable";
        public static final String FRAMERATE = "android.media.mediarecorder.frame-rate";
        public static final String HEIGHT = "android.media.mediarecorder.height";
        public static final String MOVIE_TIMESCALE = "android.media.mediarecorder.movie-timescale";
        public static final String ROTATION = "android.media.mediarecorder.rotation";
        public static final String VIDEO_BITRATE = "android.media.mediarecorder.video-bitrate";
        public static final String VIDEO_IFRAME_INTERVAL = "android.media.mediarecorder.video-iframe-interval";
        public static final String VIDEO_LEVEL = "android.media.mediarecorder.video-encoder-level";
        public static final String VIDEO_PROFILE = "android.media.mediarecorder.video-encoder-profile";
        public static final String VIDEO_TIMESCALE = "android.media.mediarecorder.video-timescale";
        public static final String WIDTH = "android.media.mediarecorder.width";

        private MetricsConstants() {
        }
    }

    public void semSetAuthor(int i) {
        setParameter("param-meta-author=" + i);
    }

    public void semSetRecordingMode(int i) {
        setParameter("param-meta-recording-mode=" + i);
    }

    public void semSetIframeInterval(int i) {
        setParameter("video-param-i-frames-interval=" + i);
    }

    public static boolean semIsRecording() {
        return semNativeIsRecording();
    }

    public void semSetVideoFlip(int i) {
        if (i < 0 || i > 3) {
            throw new IllegalArgumentException("Unsupported axis: " + i);
        }
        setParameter("video-param-mirror-flip=" + i);
    }

    public void semSetShutterSoundEnabled(boolean z) {
        setParameter("param-meta-shuttersound-enabled=" + z);
    }

    public void semSetCameraInfo(String str) {
        setParameter("param-meta-camera-information=" + str);
    }

    public void semSetCameraLensInfo(int i, int i2, int i3) {
        if (i != 21 && i != 22) {
            switch (i) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                    break;
                default:
                    throw new IllegalArgumentException("Invalid lens type : " + i);
            }
        }
        semNativeSetCameraLensInfo(i, i2, i3);
    }

    public void semSetStereoCameraInfo(float f, float f2, float f3) {
        semNativeSetStereoCameraInfo(f, f2, f3);
    }

    public static Surface semCreateSemPersistentSurface() {
        return native_createSemPersistentSurface();
    }

    static class SemPersistentSurface extends Surface {
        private long mPersistentObject;

        SemPersistentSurface() {
        }

        @Override // android.view.Surface
        public void release() {
            Log.i(MediaRecorder.TAG, "SemPersistentSurface::release()");
            MediaRecorder.native_releaseSemPersistentSurface(this);
            super.release();
        }

        @Override // android.view.Surface
        protected void finalize() throws Throwable {
            Log.i(MediaRecorder.TAG, "SemPersistentSurface::finalize()");
            MediaRecorder.native_releaseSemPersistentSurface(this);
            super.finalize();
        }
    }

    public void semCreatePersistentSurfaceTrack(String str, MediaFormat mediaFormat, Surface surface) throws IllegalStateException, IllegalArgumentException {
        String[] strArr;
        Object[] objArr;
        Log.d(TAG, "semCreatePersistentSurfaceTrack");
        if (mediaFormat != null) {
            Map<String, Object> map = mediaFormat.getMap();
            strArr = new String[map.size()];
            objArr = new Object[map.size()];
            int i = 0;
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                strArr[i] = entry.getKey();
                objArr[i] = entry.getValue();
                i++;
            }
        } else {
            strArr = null;
            objArr = null;
        }
        native_semCreatePersistentSurfaceTrack(str, strArr, objArr, surface);
    }

    public Surface semCreateSurfaceTrack(String str, MediaFormat mediaFormat) throws IllegalStateException, IllegalArgumentException {
        String[] strArr;
        Object[] objArr;
        Log.d(TAG, "semCreateSurfaceTrack");
        if (mediaFormat != null) {
            Map<String, Object> map = mediaFormat.getMap();
            strArr = new String[map.size()];
            objArr = new Object[map.size()];
            int i = 0;
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                strArr[i] = entry.getKey();
                objArr[i] = entry.getValue();
                i++;
            }
        } else {
            strArr = null;
            objArr = null;
        }
        return native_semCreateSurfaceTrack(str, strArr, objArr);
    }

    public SemTrack semCreateTrack(String str, MediaFormat mediaFormat) throws IllegalStateException, IllegalArgumentException {
        Log.d(TAG, "semCreateTrack");
        return new SemTrack(this, str, mediaFormat);
    }

    public class SemTrack implements AutoCloseable {
        private String mMime;
        private long mNativeContext;
        private MediaRecorder mRecorder;

        private native void nativeWriteSampleData(long j, MediaRecorder mediaRecorder, ByteBuffer byteBuffer, int i, int i2, long j2, int i3);

        private native void native_release();

        private native void native_setup(MediaRecorder mediaRecorder, String[] strArr, Object[] objArr);

        private SemTrack(MediaRecorder mediaRecorder, MediaRecorder mediaRecorder2, String str, MediaFormat mediaFormat) {
            String[] strArr;
            Object[] objArr;
            this.mRecorder = mediaRecorder2;
            this.mMime = str;
            if (mediaFormat != null) {
                Map<String, Object> map = mediaFormat.getMap();
                strArr = new String[map.size()];
                objArr = new Object[map.size()];
                int i = 0;
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    strArr[i] = entry.getKey();
                    objArr[i] = entry.getValue();
                    i++;
                }
            } else {
                strArr = null;
                objArr = null;
            }
            native_setup(this.mRecorder, strArr, objArr);
        }

        public void writeSampleData(ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo) {
            if (bufferInfo.size < 0 || bufferInfo.offset < 0 || bufferInfo.offset + bufferInfo.size > byteBuffer.capacity()) {
                throw new IllegalArgumentException("bufferInfo must specify a valid buffer offset and size");
            }
            long j = this.mNativeContext;
            if (j == 0) {
                throw new IllegalStateException("source has been released!");
            }
            nativeWriteSampleData(j, this.mRecorder, byteBuffer, bufferInfo.offset, bufferInfo.size, bufferInfo.presentationTimeUs, bufferInfo.flags);
        }

        protected void finalize() {
            release();
        }

        @Override // java.lang.AutoCloseable
        public void close() {
            release();
        }

        public void release() {
            native_release();
        }
    }
}
