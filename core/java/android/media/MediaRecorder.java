package android.media;

import android.annotation.SystemApi;
import android.app.ActivityThread;
import android.content.AttributionSource;
import android.content.Context;
import android.hardware.Camera;
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
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executor;

public class MediaRecorder
        implements AudioRouting,
                AudioRecordingMonitor,
                AudioRecordingMonitorClient,
                MicrophoneDirection {
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
    private ArrayMap<AudioRouting.OnRoutingChangedListener, NativeRoutingEventHandlerDelegate>
            mRoutingChangeListeners;
    private Surface mSurface;

    @Retention(RetentionPolicy.SOURCE)
    public @interface AudioEncoderValues {}

    public interface OnErrorListener {
        void onError(MediaRecorder mediaRecorder, int i, int i2);
    }

    public interface OnInfoListener {
        void onInfo(MediaRecorder mediaRecorder, int i, int i2);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface OutputFormatValues {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface Source {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface SystemSource {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface VideoEncoderValues {}

    private native void _prepare() throws IllegalStateException, IOException;

    private native void _setNextOutputFile(FileDescriptor fileDescriptor)
            throws IllegalStateException, IOException;

    private native void _setOutputFile(FileDescriptor fileDescriptor)
            throws IllegalStateException, IOException;

    private static final native SemPersistentSurface native_createSemPersistentSurface();

    private final native void native_enableDeviceCallback(boolean z);

    private native void native_finalize();

    private final native int native_getActiveMicrophones(ArrayList<MicrophoneInfo> arrayList);

    private native PersistableBundle native_getMetrics();

    private native int native_getPortId();

    private final native int native_getRoutedDeviceId();

    private static final native void native_init();

    public static final native void native_releaseSemPersistentSurface(Surface surface);

    private native void native_reset();

    private native void native_semCreatePersistentSurfaceTrack(
            String str, String[] strArr, Object[] objArr, Surface surface);

    private native Surface native_semCreateSurfaceTrack(
            String str, String[] strArr, Object[] objArr);

    private final native boolean native_setInputDevice(int i);

    private final native void native_setInputSurface(Surface surface);

    private native int native_setPreferredMicrophoneDirection(int i);

    private native int native_setPreferredMicrophoneFieldDimension(float f);

    private native void native_setup(Object obj, String str, Parcel parcel)
            throws IllegalStateException;

    private static native boolean semNativeIsRecording();

    private final native void semNativeSendcommand(int i, int i2, int i3);

    private native void semNativeSetDurationInterval(int i) throws IllegalArgumentException;

    private native void semNativeSetFileSizeInterval(long j, int i) throws IllegalArgumentException;

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
        Looper looper = Looper.myLooper();
        if (looper != null) {
            this.mEventHandler = new EventHandler(this, looper);
        } else {
            Looper looper2 = Looper.getMainLooper();
            if (looper2 != null) {
                this.mEventHandler = new EventHandler(this, looper2);
            } else {
                this.mEventHandler = null;
            }
        }
        this.mChannelCount = 1;
        AttributionSource.ScopedParcelState attributionSourceState =
                context.getAttributionSource().asScopedParcelState();
        try {
            native_setup(
                    new WeakReference(this),
                    ActivityThread.currentPackageName(),
                    attributionSourceState.getParcel());
            if (attributionSourceState != null) {
                attributionSourceState.close();
            }
        } catch (Throwable th) {
            if (attributionSourceState != null) {
                try {
                    attributionSourceState.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public void setLogSessionId(LogSessionId id) {
        Objects.requireNonNull(id);
        this.mLogSessionId = id;
        setParameter("log-session-id=" + id.getStringId());
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

    public void setPreviewDisplay(Surface sv) {
        this.mSurface = sv;
    }

    public final class AudioSource {
        private static final int AUDIOSOURCE_OFFSET = 10;
        public static final int AUDIO_SOURCE_INVALID = -1;
        public static final int CAMCORDER = 5;
        public static final int DEFAULT = 0;

        @SystemApi public static final int ECHO_REFERENCE = 1997;

        @SystemApi public static final int HOTWORD = 1999;
        public static final int MIC = 1;

        @SystemApi public static final int RADIO_TUNER = 1998;
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

        @SystemApi public static final int ULTRASOUND = 2000;
        public static final int UNPROCESSED = 9;
        public static final int VOICE_CALL = 4;
        public static final int VOICE_COMMUNICATION = 7;
        public static final int VOICE_DOWNLINK = 3;
        public static final int VOICE_PERFORMANCE = 10;
        public static final int VOICE_RECOGNITION = 6;
        public static final int VOICE_UPLINK = 2;

        private AudioSource() {}
    }

    public static boolean isSystemOnlyAudioSource(int source) {
        switch (source) {
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

    public static boolean isValidAudioSource(int source) {
        switch (source) {
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
            case 1997:
            case 1998:
            case 1999:
            case 2000:
                return true;
            default:
                return false;
        }
    }

    public static final String toLogFriendlyAudioSource(int source) {
        switch (source) {
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
            case 1997:
                return "ECHO_REFERENCE";
            case 1998:
                return "RADIO_TUNER";
            case 1999:
                return "HOTWORD";
            case 2000:
                return "ULTRASOUND";
            default:
                return "unknown source " + source;
        }
    }

    public final class VideoSource {
        public static final int CAMERA = 1;
        public static final int DEFAULT = 0;
        public static final int SURFACE = 2;

        private VideoSource() {}
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

        private OutputFormat() {}
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

        private AudioEncoder() {}
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

        private VideoEncoder() {}
    }

    public static final int getAudioSourceMax() {
        return 19;
    }

    public void setProfile(CamcorderProfile profile) {
        setOutputFormat(profile.fileFormat);
        setVideoFrameRate(profile.videoFrameRate);
        setVideoSize(profile.videoFrameWidth, profile.videoFrameHeight);
        setVideoEncodingBitRate(profile.videoBitRate);
        setVideoEncoder(profile.videoCodec);
        if (profile.quality < 1000 || profile.quality > 1007) {
            setAudioEncodingBitRate(profile.audioBitRate);
            setAudioChannels(profile.audioChannels);
            setAudioSamplingRate(profile.audioSampleRate);
            setAudioEncoder(profile.audioCodec);
        }
    }

    public void setAudioProfile(EncoderProfiles.AudioProfile profile) {
        setAudioEncodingBitRate(profile.getBitrate());
        setAudioChannels(profile.getChannels());
        setAudioSamplingRate(profile.getSampleRate());
        setAudioEncoder(profile.getCodec());
    }

    public void setVideoProfile(EncoderProfiles.VideoProfile profile) {
        setVideoFrameRate(profile.getFrameRate());
        setVideoSize(profile.getWidth(), profile.getHeight());
        setVideoEncodingBitRate(profile.getBitrate());
        setVideoEncoder(profile.getCodec());
        if (profile.getProfile() >= 0) {
            setVideoEncodingProfileLevel(profile.getProfile(), 0);
        }
    }

    public void setCaptureRate(double fps) {
        setParameter("time-lapse-enable=1");
        setParameter("time-lapse-fps=" + fps);
    }

    public void setOrientationHint(int degrees) {
        if (degrees != 0 && degrees != 90 && degrees != 180 && degrees != 270) {
            throw new IllegalArgumentException("Unsupported angle: " + degrees);
        }
        setParameter("video-param-rotation-angle-degrees=" + degrees);
    }

    public void setLocation(float latitude, float longitude) {
        int latitudex10000 = (int) ((latitude * 10000.0f) + 0.5d);
        int longitudex10000 = (int) ((10000.0f * longitude) + 0.5d);
        if (latitudex10000 > 900000 || latitudex10000 < -900000) {
            String msg = "Latitude: " + latitude + " out of range.";
            throw new IllegalArgumentException(msg);
        }
        if (longitudex10000 > 1800000 || longitudex10000 < -1800000) {
            String msg2 = "Longitude: " + longitude + " out of range";
            throw new IllegalArgumentException(msg2);
        }
        setParameter("param-geotag-latitude=" + latitudex10000);
        setParameter("param-geotag-longitude=" + longitudex10000);
    }

    public void semSetDurationInterval(int max_duration_ms) throws IllegalArgumentException {
        semNativeSetDurationInterval(max_duration_ms);
    }

    public void semSetFileSizeInterval(long max_filesize_bytes) throws IllegalArgumentException {
        semNativeSetFileSizeInterval(max_filesize_bytes, 903);
    }

    public void semSetFileSizeInterval(long interval, int unit) throws IllegalArgumentException {
        semNativeSetFileSizeInterval(interval, unit);
    }

    public void setAudioSamplingRate(int samplingRate) {
        if (samplingRate <= 0) {
            throw new IllegalArgumentException("Audio sampling rate is not positive");
        }
        setParameter("audio-param-sampling-rate=" + samplingRate);
    }

    public void setAudioChannels(int numChannels) {
        if (numChannels <= 0) {
            throw new IllegalArgumentException("Number of channels is not positive");
        }
        this.mChannelCount = numChannels;
        setParameter("audio-param-number-of-channels=" + numChannels);
    }

    public void setAudioEncodingBitRate(int bitRate) {
        if (bitRate <= 0) {
            throw new IllegalArgumentException("Audio encoding bit rate is not positive");
        }
        setParameter("audio-param-encoding-bitrate=" + bitRate);
    }

    public void setVideoEncodingBitRate(int bitRate) {
        if (bitRate <= 0) {
            throw new IllegalArgumentException("Video encoding bit rate is not positive");
        }
        setParameter("video-param-encoding-bitrate=" + bitRate);
    }

    public void setVideoEncodingProfileLevel(int profile, int level) {
        if (profile < 0) {
            throw new IllegalArgumentException("Video encoding profile is not positive");
        }
        if (level < 0) {
            throw new IllegalArgumentException("Video encoding level is not positive");
        }
        setParameter("video-param-encoder-profile=" + profile);
        setParameter("video-param-encoder-level=" + level);
    }

    public void setAuxiliaryOutputFile(FileDescriptor fd) {
        Log.w(TAG, "setAuxiliaryOutputFile(FileDescriptor) is no longer supported.");
    }

    public void setAuxiliaryOutputFile(String path) {
        Log.w(TAG, "setAuxiliaryOutputFile(String) is no longer supported.");
    }

    public void setOutputFile(FileDescriptor fd) throws IllegalStateException {
        this.mPath = null;
        this.mFile = null;
        this.mFd = fd;
    }

    public void setOutputFile(File file) {
        this.mPath = null;
        this.mFd = null;
        this.mFile = file;
    }

    public void setNextOutputFile(FileDescriptor fd) throws IOException {
        _setNextOutputFile(fd);
    }

    public void setOutputFile(String path) throws IllegalStateException {
        this.mFd = null;
        this.mFile = null;
        this.mPath = path;
    }

    public void setNextOutputFile(File file) throws IOException {
        RandomAccessFile f = new RandomAccessFile(file, "rw");
        try {
            _setNextOutputFile(f.getFD());
        } finally {
            f.close();
        }
    }

    public void prepare() throws IllegalStateException, IOException {
        RandomAccessFile file;
        Log.i(TAG, "prepare");
        long startuptimeMillis = SystemClock.uptimeMillis();
        if (this.mPath != null) {
            file = new RandomAccessFile(this.mPath, "rw");
            try {
                _setOutputFile(file.getFD());
                file.close();
            } finally {
            }
        } else if (this.mFd != null) {
            _setOutputFile(this.mFd);
        } else if (this.mFile != null) {
            file = new RandomAccessFile(this.mFile, "rw");
            try {
                _setOutputFile(file.getFD());
            } finally {
            }
        } else {
            throw new IOException("No valid output file");
        }
        _prepare();
        Log.i(
                TAG,
                "prepare elapsed time : "
                        + (SystemClock.uptimeMillis() - startuptimeMillis)
                        + " ms");
    }

    public void reset() {
        native_reset();
        this.mEventHandler.removeCallbacksAndMessages(null);
    }

    public void setOnErrorListener(OnErrorListener l) {
        this.mOnErrorListener = l;
    }

    public void setOnInfoListener(OnInfoListener listener) {
        this.mOnInfoListener = listener;
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

        public EventHandler(MediaRecorder mr, Looper looper) {
            super(looper);
            this.mMediaRecorder = mr;
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            if (this.mMediaRecorder.mNativeContext == 0) {
                Log.w(MediaRecorder.TAG, "mediarecorder went away with unhandled events");
                return;
            }
            switch (msg.what) {
                case 1:
                case 100:
                    if (MediaRecorder.this.mOnErrorListener != null) {
                        MediaRecorder.this.mOnErrorListener.onError(
                                this.mMediaRecorder, msg.arg1, msg.arg2);
                        return;
                    }
                    return;
                case 2:
                case 101:
                    if (MediaRecorder.this.mOnInfoListener != null) {
                        MediaRecorder.this.mOnInfoListener.onInfo(
                                this.mMediaRecorder, msg.arg1, msg.arg2);
                        return;
                    }
                    return;
                case 10000:
                    AudioManager.resetAudioPortGeneration();
                    synchronized (MediaRecorder.this.mRoutingChangeListeners) {
                        for (NativeRoutingEventHandlerDelegate delegate :
                                MediaRecorder.this.mRoutingChangeListeners.values()) {
                            delegate.notifyClient();
                        }
                    }
                    return;
                default:
                    Log.e(MediaRecorder.TAG, "Unknown message type " + msg.what);
                    return;
            }
        }
    }

    @Override // android.media.AudioRouting
    public boolean setPreferredDevice(AudioDeviceInfo deviceInfo) {
        if (deviceInfo != null && !deviceInfo.isSource()) {
            return false;
        }
        int preferredDeviceId = deviceInfo != null ? deviceInfo.getId() : 0;
        int preferredDeviceType = deviceInfo != null ? deviceInfo.getType() : 0;
        setParameter("param-meta-audio-devicetype=" + preferredDeviceType);
        boolean status = native_setInputDevice(preferredDeviceId);
        if (status) {
            synchronized (this) {
                this.mPreferredDevice = deviceInfo;
            }
        }
        return status;
    }

    @Override // android.media.AudioRouting
    public AudioDeviceInfo getPreferredDevice() {
        AudioDeviceInfo audioDeviceInfo;
        synchronized (this) {
            audioDeviceInfo = this.mPreferredDevice;
        }
        return audioDeviceInfo;
    }

    @Override // android.media.AudioRouting
    public AudioDeviceInfo getRoutedDevice() {
        int deviceId = native_getRoutedDeviceId();
        if (deviceId == 0) {
            return null;
        }
        return AudioManager.getDeviceForPortId(deviceId, 1);
    }

    private void enableNativeRoutingCallbacksLocked(boolean enabled) {
        if (this.mRoutingChangeListeners.size() == 0) {
            native_enableDeviceCallback(enabled);
        }
    }

    @Override // android.media.AudioRouting
    public void addOnRoutingChangedListener(
            AudioRouting.OnRoutingChangedListener listener, Handler handler) {
        synchronized (this.mRoutingChangeListeners) {
            if (listener != null) {
                if (!this.mRoutingChangeListeners.containsKey(listener)) {
                    enableNativeRoutingCallbacksLocked(true);
                    this.mRoutingChangeListeners.put(
                            listener,
                            new NativeRoutingEventHandlerDelegate(
                                    this,
                                    listener,
                                    handler != null ? handler : this.mEventHandler));
                }
            }
        }
    }

    @Override // android.media.AudioRouting
    public void removeOnRoutingChangedListener(AudioRouting.OnRoutingChangedListener listener) {
        synchronized (this.mRoutingChangeListeners) {
            if (this.mRoutingChangeListeners.containsKey(listener)) {
                this.mRoutingChangeListeners.remove(listener);
                enableNativeRoutingCallbacksLocked(false);
            }
        }
    }

    public List<MicrophoneInfo> getActiveMicrophones() throws IOException {
        AudioDeviceInfo device;
        ArrayList<MicrophoneInfo> activeMicrophones = new ArrayList<>();
        int status = native_getActiveMicrophones(activeMicrophones);
        if (status != 0) {
            if (status != -3) {
                Log.e(TAG, "getActiveMicrophones failed:" + status);
            }
            Log.i(TAG, "getActiveMicrophones failed, fallback on routed device info");
        }
        AudioManager.setPortIdForMicrophones(activeMicrophones);
        if (activeMicrophones.size() == 0 && (device = getRoutedDevice()) != null) {
            MicrophoneInfo microphone = AudioManager.microphoneInfoFromAudioDeviceInfo(device);
            ArrayList<Pair<Integer, Integer>> channelMapping = new ArrayList<>();
            for (int i = 0; i < this.mChannelCount; i++) {
                channelMapping.add(new Pair<>(Integer.valueOf(i), 1));
            }
            microphone.setChannelMapping(channelMapping);
            activeMicrophones.add(microphone);
        }
        return activeMicrophones;
    }

    @Override // android.media.MicrophoneDirection
    public boolean setPreferredMicrophoneDirection(int direction) {
        return native_setPreferredMicrophoneDirection(direction) == 0;
    }

    @Override // android.media.MicrophoneDirection
    public boolean setPreferredMicrophoneFieldDimension(float zoom) {
        Preconditions.checkArgument(
                zoom >= -1.0f && zoom <= 1.0f, "Argument must fall between -1 & 1 (inclusive)");
        return native_setPreferredMicrophoneFieldDimension(zoom) == 0;
    }

    @Override // android.media.AudioRecordingMonitor
    public void registerAudioRecordingCallback(
            Executor executor, AudioManager.AudioRecordingCallback cb) {
        this.mRecordingInfoImpl.registerAudioRecordingCallback(executor, cb);
    }

    @Override // android.media.AudioRecordingMonitor
    public void unregisterAudioRecordingCallback(AudioManager.AudioRecordingCallback cb) {
        this.mRecordingInfoImpl.unregisterAudioRecordingCallback(cb);
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

    private static void postEventFromNative(
            Object mediarecorder_ref, int what, int arg1, int arg2, Object obj) {
        MediaRecorder mr = (MediaRecorder) ((WeakReference) mediarecorder_ref).get();
        if (mr != null && mr.mEventHandler != null) {
            Message m = mr.mEventHandler.obtainMessage(what, arg1, arg2, obj);
            mr.mEventHandler.sendMessage(m);
        }
    }

    private void native_setup(Object mediarecorderThis, String clientName, String opPackageName)
            throws IllegalStateException {
        AttributionSource attributionSource =
                AttributionSource.myAttributionSource().withPackageName(opPackageName);
        AttributionSource.ScopedParcelState attributionSourceState =
                attributionSource.asScopedParcelState();
        try {
            native_setup(mediarecorderThis, clientName, attributionSourceState.getParcel());
            if (attributionSourceState != null) {
                attributionSourceState.close();
            }
        } catch (Throwable th) {
            if (attributionSourceState != null) {
                try {
                    attributionSourceState.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public PersistableBundle getMetrics() {
        PersistableBundle bundle = native_getMetrics();
        return bundle;
    }

    protected void finalize() {
        native_finalize();
    }

    public static final class MetricsConstants {
        public static final String AUDIO_BITRATE = "android.media.mediarecorder.audio-bitrate";
        public static final String AUDIO_CHANNELS = "android.media.mediarecorder.audio-channels";
        public static final String AUDIO_SAMPLERATE =
                "android.media.mediarecorder.audio-samplerate";
        public static final String AUDIO_TIMESCALE = "android.media.mediarecorder.audio-timescale";
        public static final String CAPTURE_FPS = "android.media.mediarecorder.capture-fps";
        public static final String CAPTURE_FPS_ENABLE =
                "android.media.mediarecorder.capture-fpsenable";
        public static final String FRAMERATE = "android.media.mediarecorder.frame-rate";
        public static final String HEIGHT = "android.media.mediarecorder.height";
        public static final String MOVIE_TIMESCALE = "android.media.mediarecorder.movie-timescale";
        public static final String ROTATION = "android.media.mediarecorder.rotation";
        public static final String VIDEO_BITRATE = "android.media.mediarecorder.video-bitrate";
        public static final String VIDEO_IFRAME_INTERVAL =
                "android.media.mediarecorder.video-iframe-interval";
        public static final String VIDEO_LEVEL = "android.media.mediarecorder.video-encoder-level";
        public static final String VIDEO_PROFILE =
                "android.media.mediarecorder.video-encoder-profile";
        public static final String VIDEO_TIMESCALE = "android.media.mediarecorder.video-timescale";
        public static final String WIDTH = "android.media.mediarecorder.width";

        private MetricsConstants() {}
    }

    public static int semGetInputSource(int samsung_source) {
        switch (samsung_source) {
            case 1:
                return 11;
            case 2:
            case 10:
            case 11:
            default:
                return 0;
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
            case 12:
                return 19;
        }
    }

    public static boolean isValidAudioSourceForSem(int source) {
        switch (source) {
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

    public void semSetAuthor(int author) {
        setParameter("param-meta-author=" + author);
    }

    public void semSetRecordingMode(int mode) {
        setParameter("param-meta-recording-mode=" + mode);
    }

    public void semSetIframeInterval(int seconds) {
        setParameter("video-param-i-frames-interval=" + seconds);
    }

    public static boolean semIsRecording() {
        return semNativeIsRecording();
    }

    public void semSetVideoFlip(int axis) {
        if (axis < 0 || axis > 3) {
            throw new IllegalArgumentException("Unsupported axis: " + axis);
        }
        setParameter("video-param-mirror-flip=" + axis);
    }

    public void semSetShutterSoundEnabled(boolean enabled) {
        setParameter("param-meta-shuttersound-enabled=" + enabled);
    }

    public static boolean isSemSystemOnlyAudioSource(int source) {
        switch (source) {
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

    public void semSetCameraInfo(String info) {
        setParameter("param-meta-camera-information=" + info);
    }

    public static Surface semCreateSemPersistentSurface() {
        return native_createSemPersistentSurface();
    }

    static class SemPersistentSurface extends Surface {
        private long mPersistentObject;

        SemPersistentSurface() {}

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

    public void semCreatePersistentSurfaceTrack(
            String trackMime, MediaFormat format, Surface surface)
            throws IllegalArgumentException, IllegalStateException {
        Log.d(TAG, "semCreatePersistentSurfaceTrack");
        String[] keys = null;
        Object[] values = null;
        if (format != null) {
            Map<String, Object> formatMap = format.getMap();
            keys = new String[formatMap.size()];
            values = new Object[formatMap.size()];
            int i = 0;
            for (Map.Entry<String, Object> entry : formatMap.entrySet()) {
                keys[i] = entry.getKey();
                values[i] = entry.getValue();
                i++;
            }
        }
        native_semCreatePersistentSurfaceTrack(trackMime, keys, values, surface);
    }

    public Surface semCreateSurfaceTrack(String trackMime, MediaFormat format)
            throws IllegalArgumentException, IllegalStateException {
        Log.d(TAG, "semCreateSurfaceTrack");
        String[] keys = null;
        Object[] values = null;
        if (format != null) {
            Map<String, Object> formatMap = format.getMap();
            keys = new String[formatMap.size()];
            values = new Object[formatMap.size()];
            int i = 0;
            for (Map.Entry<String, Object> entry : formatMap.entrySet()) {
                keys[i] = entry.getKey();
                values[i] = entry.getValue();
                i++;
            }
        }
        return native_semCreateSurfaceTrack(trackMime, keys, values);
    }

    public SemTrack semCreateTrack(String trackMime, MediaFormat format)
            throws IllegalArgumentException, IllegalStateException {
        Log.d(TAG, "semCreateTrack");
        return new SemTrack(this, trackMime, format);
    }

    public class SemTrack implements AutoCloseable {
        private String mMime;
        private long mNativeContext;
        private MediaRecorder mRecorder;

        private native void nativeWriteSampleData(
                long j,
                MediaRecorder mediaRecorder,
                ByteBuffer byteBuffer,
                int i,
                int i2,
                long j2,
                int i3);

        private native void native_release();

        private native void native_setup(
                MediaRecorder mediaRecorder, String[] strArr, Object[] objArr);

        private SemTrack(MediaRecorder recorder, String trackMime, MediaFormat format) {
            this.mRecorder = recorder;
            this.mMime = trackMime;
            String[] keys = null;
            Object[] values = null;
            if (format != null) {
                Map<String, Object> formatMap = format.getMap();
                keys = new String[formatMap.size()];
                values = new Object[formatMap.size()];
                int i = 0;
                for (Map.Entry<String, Object> entry : formatMap.entrySet()) {
                    keys[i] = entry.getKey();
                    values[i] = entry.getValue();
                    i++;
                }
            }
            native_setup(this.mRecorder, keys, values);
        }

        public void writeSampleData(ByteBuffer byteBuf, MediaCodec.BufferInfo bufferInfo) {
            if (bufferInfo.size < 0
                    || bufferInfo.offset < 0
                    || bufferInfo.offset + bufferInfo.size > byteBuf.capacity()) {
                throw new IllegalArgumentException(
                        "bufferInfo must specify a valid buffer offset and size");
            }
            if (this.mNativeContext == 0) {
                throw new IllegalStateException("source has been released!");
            }
            nativeWriteSampleData(
                    this.mNativeContext,
                    this.mRecorder,
                    byteBuf,
                    bufferInfo.offset,
                    bufferInfo.size,
                    bufferInfo.presentationTimeUs,
                    bufferInfo.flags);
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
