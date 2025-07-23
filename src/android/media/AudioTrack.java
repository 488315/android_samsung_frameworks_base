package android.media;

import android.annotation.SystemApi;
import android.content.AttributionSource;
import android.content.Context;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioMetadata;
import android.media.AudioRouting;
import android.media.AudioTrack;
import android.media.Utils;
import android.media.VolumeShaper;
import android.media.audiopolicy.AudioMix;
import android.media.audiopolicy.AudioMixingRule;
import android.media.audiopolicy.AudioPolicy;
import android.media.metrics.LogSessionId;
import android.opengl.GLES30;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.PersistableBundle;
import android.util.ArrayMap;
import android.util.Log;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.NioUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executor;

/* loaded from: classes2.dex */
public class AudioTrack extends PlayerBase implements AudioRouting, VolumeAutomation {
    private static final int AUDIO_OUTPUT_FLAG_DEEP_BUFFER = 8;
    private static final int AUDIO_OUTPUT_FLAG_FAST = 4;
    private static final Map<String, Integer> CHANNEL_PAIR_MAP = Map.of("front", 12, NavigationBarInflaterView.BACK, 192, "front of center", 768, "side", Integer.valueOf(GLES30.GL_COLOR), "top front", 81920, "top back", 655360, "top side", 3145728, "bottom front", 20971520, "front wide", 201326592);
    public static final int DUAL_MONO_MODE_LL = 2;
    public static final int DUAL_MONO_MODE_LR = 1;
    public static final int DUAL_MONO_MODE_OFF = 0;
    public static final int DUAL_MONO_MODE_RR = 3;
    public static final int ENCAPSULATION_METADATA_TYPE_DVB_AD_DESCRIPTOR = 2;
    public static final int ENCAPSULATION_METADATA_TYPE_FRAMEWORK_TUNER = 1;
    public static final int ENCAPSULATION_METADATA_TYPE_NONE = 0;
    public static final int ENCAPSULATION_METADATA_TYPE_SUPPLEMENTARY_AUDIO_PLACEMENT = 3;
    public static final int ENCAPSULATION_MODE_ELEMENTARY_STREAM = 1;

    @SystemApi
    public static final int ENCAPSULATION_MODE_HANDLE = 2;
    public static final int ENCAPSULATION_MODE_NONE = 0;
    public static final int ERROR = -1;
    public static final int ERROR_BAD_VALUE = -2;
    public static final int ERROR_DEAD_OBJECT = -6;
    public static final int ERROR_INVALID_OPERATION = -3;
    private static final int ERROR_NATIVESETUP_AUDIOSYSTEM = -16;
    private static final int ERROR_NATIVESETUP_INVALIDCHANNELMASK = -17;
    private static final int ERROR_NATIVESETUP_INVALIDFORMAT = -18;
    private static final int ERROR_NATIVESETUP_INVALIDSTREAMTYPE = -19;
    private static final int ERROR_NATIVESETUP_NATIVEINITFAILED = -20;
    public static final int ERROR_WOULD_BLOCK = -7;
    private static final float GAIN_MAX = 1.0f;
    private static final float GAIN_MIN = 0.0f;
    private static final float HEADER_V2_SIZE_BYTES = 20.0f;
    private static final float MAX_AUDIO_DESCRIPTION_MIX_LEVEL = 48.0f;
    public static final int MODE_STATIC = 0;
    public static final int MODE_STREAM = 1;
    private static final int NATIVE_EVENT_CAN_WRITE_MORE_DATA = 9;
    private static final int NATIVE_EVENT_CODEC_FORMAT_CHANGE = 100;
    private static final int NATIVE_EVENT_MARKER = 3;
    private static final int NATIVE_EVENT_NEW_IAUDIOTRACK = 6;
    private static final int NATIVE_EVENT_NEW_POS = 4;
    private static final int NATIVE_EVENT_STREAM_END = 7;
    public static final int PERFORMANCE_MODE_LOW_LATENCY = 1;
    public static final int PERFORMANCE_MODE_NONE = 0;
    public static final int PERFORMANCE_MODE_POWER_SAVING = 2;
    public static final int PLAYSTATE_PAUSED = 2;
    private static final int PLAYSTATE_PAUSED_STOPPING = 5;
    public static final int PLAYSTATE_PLAYING = 3;
    public static final int PLAYSTATE_STOPPED = 1;
    private static final int PLAYSTATE_STOPPING = 4;
    public static final int STATE_INITIALIZED = 1;
    public static final int STATE_NO_STATIC_DATA = 2;
    public static final int STATE_UNINITIALIZED = 0;
    public static final int SUCCESS = 0;
    public static final int SUPPLEMENTARY_AUDIO_PLACEMENT_LEFT = 1;
    public static final int SUPPLEMENTARY_AUDIO_PLACEMENT_NORMAL = 0;
    public static final int SUPPLEMENTARY_AUDIO_PLACEMENT_RIGHT = 2;
    private static final int SUPPORTED_OUT_CHANNELS = 268435452;
    private static final String TAG = "android.media.AudioTrack";
    public static final int WRITE_BLOCKING = 0;
    public static final int WRITE_NON_BLOCKING = 1;
    private int mAudioFormat;
    private AudioPolicy mAudioPolicy;
    private int mAvSyncBytesRemaining;
    private ByteBuffer mAvSyncHeader;
    private int mChannelConfiguration;
    private int mChannelCount;
    private int mChannelIndexMask;
    private int mChannelMask;
    private final Utils.ListenerList<AudioMetadataReadMap> mCodecFormatChangedListeners;
    private AudioAttributes mConfiguredAudioAttributes;
    private int mDataLoadMode;
    private boolean mEnableSelfRoutingMonitor;
    private NativePositionEventHandlerDelegate mEventHandlerDelegate;
    private final Looper mInitializationLooper;
    private long mJniData;
    private LogSessionId mLogSessionId;
    private int mNativeBufferSizeInBytes;
    private int mNativeBufferSizeInFrames;
    protected long mNativeTrackInJavaObj;
    private int mOffloadDelayFrames;
    private boolean mOffloadEosPending;
    private int mOffloadPaddingFrames;
    private boolean mOffloaded;
    private int mOffset;
    private int mPlayState;
    private final Object mPlayStateLock;
    private AudioDeviceInfo mPreferredDevice;
    private ArrayMap<AudioRouting.OnRoutingChangedListener, NativeRoutingEventHandlerDelegate> mRoutingChangeListeners;
    private int mSampleRate;
    private int mSessionId;
    private int mState;
    private LinkedList<StreamEventCbInfo> mStreamEventCbInfoList;
    private final Object mStreamEventCbLock;
    private volatile StreamEventHandler mStreamEventHandler;
    private HandlerThread mStreamEventHandlerThread;
    private int mStreamType;

    @Retention(RetentionPolicy.SOURCE)
    public @interface DualMonoMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface EncapsulationMetadataType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface EncapsulationMode {
    }

    public interface OnCodecFormatChangedListener {
        void onCodecFormatChanged(AudioTrack audioTrack, AudioMetadataReadMap audioMetadataReadMap);
    }

    public interface OnPlaybackPositionUpdateListener {
        void onMarkerReached(AudioTrack audioTrack);

        void onPeriodicNotification(AudioTrack audioTrack);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface PerformanceMode {
    }

    public static abstract class StreamEventCallback {
        public void onDataRequest(AudioTrack audioTrack, int i) {
        }

        public void onPresentationEnded(AudioTrack audioTrack) {
        }

        public void onTearDown(AudioTrack audioTrack) {
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SupplementaryAudioPlacement {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface TransferMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface WriteMode {
    }

    public static float getMaxVolume() {
        return 1.0f;
    }

    public static float getMinVolume() {
        return 0.0f;
    }

    private static boolean isValidDualMonoMode(int i) {
        return i == 0 || i == 1 || i == 2 || i == 3;
    }

    private native int native_applyVolumeShaper(VolumeShaper.Configuration configuration, VolumeShaper.Operation operation);

    private final native int native_attachAuxEffect(int i);

    private final native void native_disableDeviceCallback();

    private final native void native_enableDeviceCallback();

    private final native void native_finalize();

    private final native void native_flush();

    private native PersistableBundle native_getMetrics();

    private native int native_getPortId();

    private native int[] native_getRoutedDeviceIds();

    private native int native_getStartThresholdInFrames();

    private native VolumeShaper.State native_getVolumeShaperState(int i);

    private native int native_get_audio_description_mix_level_db(float[] fArr);

    private final native int native_get_buffer_capacity_frames();

    private final native int native_get_buffer_size_frames();

    private native int native_get_dual_mono_mode(int[] iArr);

    private final native int native_get_flags();

    private final native int native_get_latency();

    private final native int native_get_marker_pos();

    private static final native int native_get_min_buff_size(int i, int i2, int i3);

    private static final native int native_get_output_sample_rate(int i);

    private final native PlaybackParams native_get_playback_params();

    private final native int native_get_playback_rate();

    private final native int native_get_pos_update_period();

    private final native int native_get_position();

    private final native int native_get_timestamp(long[] jArr);

    private final native int native_get_underrun_count();

    private static native boolean native_is_direct_output_supported(int i, int i2, int i3, int i4, int i5, int i6, int i7);

    private final native void native_pause();

    private final native int native_reload_static();

    private final native int native_setAuxEffectSendLevel(float f);

    private native void native_setLogSessionId(String str);

    private final native boolean native_setOutputDevice(int i);

    private native void native_setPlayerIId(int i);

    private final native int native_setPresentation(int i, int i2);

    private native int native_setStartThresholdInFrames(int i);

    private final native void native_setVolume(float f, float f2);

    private native int native_set_audio_description_mix_level_db(float f);

    private final native int native_set_buffer_size_frames(int i);

    private native void native_set_delay_padding(int i, int i2);

    private native int native_set_dual_mono_mode(int i);

    private final native int native_set_loop(int i, int i2, int i3);

    private final native int native_set_marker_pos(int i);

    private final native void native_set_playback_params(PlaybackParams playbackParams);

    private final native int native_set_playback_rate(int i);

    private final native int native_set_pos_update_period(int i);

    private final native int native_set_position(int i);

    private final native int native_setup(Object obj, Object obj2, int[] iArr, int i, int i2, int i3, int i4, int i5, int[] iArr2, Parcel parcel, long j, boolean z, int i6, Object obj3, String str);

    /* JADX INFO: Access modifiers changed from: private */
    public final native void native_start();

    private final native void native_stop();

    private final native int native_write_byte(byte[] bArr, int i, int i2, int i3, boolean z);

    private final native int native_write_float(float[] fArr, int i, int i2, int i3, boolean z);

    private final native int native_write_native_bytes(ByteBuffer byteBuffer, int i, int i2, int i3, boolean z);

    private final native int native_write_short(short[] sArr, int i, int i2, int i3, boolean z);

    public final native void native_release();

    public AudioTrack(int i, int i2, int i3, int i4, int i5, int i6) throws IllegalArgumentException {
        this(i, i2, i3, i4, i5, i6, 0);
    }

    public AudioTrack(int i, int i2, int i3, int i4, int i5, int i6, int i7) throws IllegalArgumentException {
        this(new AudioAttributes.Builder().setLegacyStreamType(i).build(), new AudioFormat.Builder().setChannelMask(i3).setEncoding(i4).setSampleRate(i2).build(), i5, i6, i7);
        deprecateStreamTypeForPlayback(i, "AudioTrack", "AudioTrack()");
    }

    public AudioTrack(AudioAttributes audioAttributes, AudioFormat audioFormat, int i, int i2, int i3) throws IllegalArgumentException {
        this(null, audioAttributes, audioFormat, i, i2, i3, false, 0, null);
    }

    private AudioTrack(Context context, AudioAttributes audioAttributes, AudioFormat audioFormat, int i, int i2, int i3, boolean z, int i4, TunerConfiguration tunerConfiguration) throws IllegalArgumentException {
        super(audioAttributes, 1);
        int i5;
        int i6;
        int i7;
        int i8;
        this.mState = 0;
        this.mPlayState = 1;
        this.mOffloadEosPending = false;
        this.mPlayStateLock = new Object();
        this.mNativeBufferSizeInBytes = 0;
        this.mNativeBufferSizeInFrames = 0;
        this.mChannelCount = 1;
        this.mChannelMask = 4;
        this.mStreamType = 3;
        this.mDataLoadMode = 1;
        this.mChannelConfiguration = 4;
        this.mChannelIndexMask = 0;
        this.mSessionId = 0;
        this.mAvSyncHeader = null;
        this.mAvSyncBytesRemaining = 0;
        this.mOffset = 0;
        this.mOffloaded = false;
        this.mOffloadDelayFrames = 0;
        this.mOffloadPaddingFrames = 0;
        this.mLogSessionId = LogSessionId.LOG_SESSION_ID_NONE;
        this.mPreferredDevice = null;
        this.mRoutingChangeListeners = new ArrayMap<>();
        this.mCodecFormatChangedListeners = new Utils.ListenerList<>();
        this.mStreamEventCbLock = new Object();
        this.mStreamEventCbInfoList = new LinkedList<>();
        this.mConfiguredAudioAttributes = audioAttributes;
        if (audioFormat == null) {
            throw new IllegalArgumentException("Illegal null AudioFormat");
        }
        if (shouldEnablePowerSaving(this.mAttributes, audioFormat, i, i2)) {
            this.mAttributes = new AudioAttributes.Builder(this.mAttributes).replaceFlags((this.mAttributes.getAllFlags() | 512) & (-257)).build();
        }
        Looper myLooper = Looper.myLooper();
        Looper mainLooper = myLooper == null ? Looper.getMainLooper() : myLooper;
        int sampleRate = audioFormat.getSampleRate();
        sampleRate = sampleRate == 0 ? 0 : sampleRate;
        int channelIndexMask = (audioFormat.getPropertySetMask() & 8) != 0 ? audioFormat.getChannelIndexMask() : 0;
        if ((4 & audioFormat.getPropertySetMask()) != 0) {
            i5 = audioFormat.getChannelMask();
        } else {
            i5 = channelIndexMask == 0 ? 12 : 0;
        }
        if ((audioFormat.getPropertySetMask() & 1) != 0) {
            int i9 = channelIndexMask;
            i8 = audioFormat.getEncoding();
            i6 = i5;
            i7 = i9;
        } else {
            i6 = i5;
            i7 = channelIndexMask;
            i8 = 1;
        }
        audioParamCheck(sampleRate, i6, i7, i8, i2);
        this.mOffloaded = z;
        this.mStreamType = -1;
        audioBuffSizeCheck(i);
        this.mInitializationLooper = mainLooper;
        if (i3 < 0) {
            throw new IllegalArgumentException("Invalid audio session ID: " + i3);
        }
        int[] iArr = {this.mSampleRate};
        int[] iArr2 = {resolvePlaybackSessionId(context, i3)};
        AttributionSource.ScopedParcelState asScopedParcelState = (context == null ? AttributionSource.myAttributionSource() : context.getAttributionSource()).asScopedParcelState();
        try {
            int native_setup = native_setup(new WeakReference(this), this.mAttributes, iArr, this.mChannelMask, this.mChannelIndexMask, this.mAudioFormat, this.mNativeBufferSizeInBytes, this.mDataLoadMode, iArr2, asScopedParcelState.getParcel(), 0L, z, i4, tunerConfiguration, getCurrentOpPackageName());
            if (native_setup != 0) {
                loge("Error code " + native_setup + " when initializing AudioTrack.");
                if (asScopedParcelState != null) {
                    asScopedParcelState.close();
                    return;
                }
                return;
            }
            if (asScopedParcelState != null) {
                asScopedParcelState.close();
            }
            this.mSampleRate = iArr[0];
            this.mSessionId = iArr2[0];
            if ((this.mAttributes.getFlags() & 16) != 0) {
                this.mOffset = ((int) Math.ceil(HEADER_V2_SIZE_BYTES / r10)) * (AudioFormat.isEncodingLinearFrames(this.mAudioFormat) ? this.mChannelCount * AudioFormat.getBytesPerSample(this.mAudioFormat) : 1);
            }
            if (this.mDataLoadMode == 0) {
                this.mState = 2;
            } else {
                this.mState = 1;
            }
            baseRegisterPlayer(this.mSessionId);
            native_setPlayerIId(this.mPlayerIId);
        } finally {
        }
    }

    AudioTrack(long j) {
        super(new AudioAttributes.Builder().build(), 1);
        this.mState = 0;
        this.mPlayState = 1;
        this.mOffloadEosPending = false;
        this.mPlayStateLock = new Object();
        this.mNativeBufferSizeInBytes = 0;
        this.mNativeBufferSizeInFrames = 0;
        this.mChannelCount = 1;
        this.mChannelMask = 4;
        this.mStreamType = 3;
        this.mDataLoadMode = 1;
        this.mChannelConfiguration = 4;
        this.mChannelIndexMask = 0;
        this.mSessionId = 0;
        this.mAvSyncHeader = null;
        this.mAvSyncBytesRemaining = 0;
        this.mOffset = 0;
        this.mOffloaded = false;
        this.mOffloadDelayFrames = 0;
        this.mOffloadPaddingFrames = 0;
        this.mLogSessionId = LogSessionId.LOG_SESSION_ID_NONE;
        this.mPreferredDevice = null;
        this.mRoutingChangeListeners = new ArrayMap<>();
        this.mCodecFormatChangedListeners = new Utils.ListenerList<>();
        this.mStreamEventCbLock = new Object();
        this.mStreamEventCbInfoList = new LinkedList<>();
        this.mNativeTrackInJavaObj = 0L;
        this.mJniData = 0L;
        Looper myLooper = Looper.myLooper();
        this.mInitializationLooper = myLooper == null ? Looper.getMainLooper() : myLooper;
        if (j != 0) {
            baseRegisterPlayer(0);
            deferred_connect(j);
        } else {
            this.mState = 0;
        }
    }

    void deferred_connect(long j) {
        if (this.mState == 1) {
            return;
        }
        int[] iArr = {0};
        int[] iArr2 = {0};
        AttributionSource.ScopedParcelState asScopedParcelState = AttributionSource.myAttributionSource().asScopedParcelState();
        try {
            int native_setup = native_setup(new WeakReference(this), null, iArr2, 0, 0, 0, 0, 0, iArr, asScopedParcelState.getParcel(), j, false, 0, null, "");
            if (native_setup != 0) {
                loge("Error code " + native_setup + " when initializing AudioTrack.");
                if (asScopedParcelState != null) {
                    asScopedParcelState.close();
                    return;
                }
                return;
            }
            if (asScopedParcelState != null) {
                asScopedParcelState.close();
            }
            this.mSessionId = iArr[0];
            this.mState = 1;
        } finally {
        }
    }

    @SystemApi
    public static class TunerConfiguration {
        public static final int CONTENT_ID_NONE = 0;
        private final int mContentId;
        private final int mSyncId;

        public TunerConfiguration(int i, int i2) {
            if (i < 0) {
                throw new IllegalArgumentException("contentId " + i + " must be positive or CONTENT_ID_NONE");
            }
            if (i2 < 1) {
                throw new IllegalArgumentException("syncId " + i2 + " must be positive");
            }
            this.mContentId = i;
            this.mSyncId = i2;
        }

        public int getContentId() {
            return this.mContentId;
        }

        public int getSyncId() {
            return this.mSyncId;
        }
    }

    public static class Builder {
        private AudioAttributes mAttributes;
        private int mBufferSizeInBytes;
        private Context mContext;
        private AudioFormat mFormat;
        private TunerConfiguration mTunerConfiguration;
        private int mEncapsulationMode = 0;
        private int mSessionId = 0;
        private int mMode = 1;
        private int mPerformanceMode = 0;
        private boolean mOffload = false;
        private int mCallRedirectionMode = 0;

        public Builder setContext(Context context) {
            this.mContext = (Context) Objects.requireNonNull(context);
            return this;
        }

        public Builder setAudioAttributes(AudioAttributes audioAttributes) throws IllegalArgumentException {
            if (audioAttributes == null) {
                throw new IllegalArgumentException("Illegal null AudioAttributes argument");
            }
            this.mAttributes = audioAttributes;
            return this;
        }

        public Builder setAudioFormat(AudioFormat audioFormat) throws IllegalArgumentException {
            if (audioFormat == null) {
                throw new IllegalArgumentException("Illegal null AudioFormat argument");
            }
            this.mFormat = audioFormat;
            return this;
        }

        public Builder setBufferSizeInBytes(int i) throws IllegalArgumentException {
            if (i <= 0) {
                throw new IllegalArgumentException("Invalid buffer size " + i);
            }
            this.mBufferSizeInBytes = i;
            return this;
        }

        public Builder setEncapsulationMode(int i) {
            if (i == 0 || i == 1 || i == 2) {
                this.mEncapsulationMode = i;
                return this;
            }
            throw new IllegalArgumentException("Invalid encapsulation mode " + i);
        }

        public Builder setTransferMode(int i) throws IllegalArgumentException {
            if (i == 0 || i == 1) {
                this.mMode = i;
                return this;
            }
            throw new IllegalArgumentException("Invalid transfer mode " + i);
        }

        public Builder setSessionId(int i) throws IllegalArgumentException {
            if (i != 0 && i < 1) {
                throw new IllegalArgumentException("Invalid audio session ID " + i);
            }
            this.mSessionId = i;
            return this;
        }

        public Builder setPerformanceMode(int i) {
            if (i == 0 || i == 1 || i == 2) {
                this.mPerformanceMode = i;
                return this;
            }
            throw new IllegalArgumentException("Invalid performance mode " + i);
        }

        public Builder setOffloadedPlayback(boolean z) {
            this.mOffload = z;
            return this;
        }

        @SystemApi
        public Builder setTunerConfiguration(TunerConfiguration tunerConfiguration) {
            if (tunerConfiguration == null) {
                throw new IllegalArgumentException("tunerConfiguration is null");
            }
            this.mTunerConfiguration = tunerConfiguration;
            return this;
        }

        public Builder setCallRedirectionMode(int i) {
            if (i == 0 || i == 1 || i == 2) {
                this.mCallRedirectionMode = i;
                return this;
            }
            throw new IllegalArgumentException("Invalid call redirection mode " + i);
        }

        private AudioTrack buildCallInjectionTrack() {
            android.media.audiopolicy.AudioMix build = new AudioMix.Builder(new AudioMixingRule.Builder().addMixRule(2, new AudioAttributes.Builder().setCapturePreset(7).setForCallRedirection().build()).setTargetMixRole(1).build()).setFormat(this.mFormat).setRouteFlags(2).build();
            AudioPolicy build2 = new AudioPolicy.Builder(this.mContext).addMix(build).build();
            if (AudioManager.registerAudioPolicyStatic(build2) != 0) {
                throw new UnsupportedOperationException("Error: could not register audio policy");
            }
            AudioTrack createAudioTrackSource = build2.createAudioTrackSource(build);
            if (createAudioTrackSource == null) {
                throw new UnsupportedOperationException("Cannot create injection AudioTrack");
            }
            createAudioTrackSource.unregisterAudioPolicyOnRelease(build2);
            return createAudioTrackSource;
        }

        /* JADX WARN: Code restructure failed: missing block: B:52:0x0046, code lost:
        
            if (android.media.AudioTrack.shouldEnablePowerSaving(r12.mAttributes, r12.mFormat, r12.mBufferSizeInBytes, r12.mMode) == false) goto L15;
         */
        /* JADX WARN: Code restructure failed: missing block: B:7:0x001b, code lost:
        
            if (r0 != 2) goto L15;
         */
        /* JADX WARN: Code restructure failed: missing block: B:8:0x0049, code lost:
        
            r12.mAttributes = new android.media.AudioAttributes.Builder(r12.mAttributes).replaceFlags((r12.mAttributes.getAllFlags() | 512) & (-257)).build();
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public android.media.AudioTrack build() throws java.lang.UnsupportedOperationException {
            /*
                Method dump skipped, instructions count: 278
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: android.media.AudioTrack.Builder.build():android.media.AudioTrack");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void unregisterAudioPolicyOnRelease(AudioPolicy audioPolicy) {
        this.mAudioPolicy = audioPolicy;
    }

    public void setOffloadDelayPadding(int i, int i2) {
        if (i2 < 0) {
            throw new IllegalArgumentException("Illegal negative padding");
        }
        if (i < 0) {
            throw new IllegalArgumentException("Illegal negative delay");
        }
        if (!this.mOffloaded) {
            throw new IllegalStateException("Illegal use of delay/padding on non-offloaded track");
        }
        if (this.mState == 0) {
            throw new IllegalStateException("Uninitialized track");
        }
        this.mOffloadDelayFrames = i;
        this.mOffloadPaddingFrames = i2;
        native_set_delay_padding(i, i2);
    }

    public int getOffloadDelay() {
        if (!this.mOffloaded) {
            throw new IllegalStateException("Illegal query of delay on non-offloaded track");
        }
        if (this.mState == 0) {
            throw new IllegalStateException("Illegal query of delay on uninitialized track");
        }
        return this.mOffloadDelayFrames;
    }

    public int getOffloadPadding() {
        if (!this.mOffloaded) {
            throw new IllegalStateException("Illegal query of padding on non-offloaded track");
        }
        if (this.mState == 0) {
            throw new IllegalStateException("Illegal query of padding on uninitialized track");
        }
        return this.mOffloadPaddingFrames;
    }

    public void setOffloadEndOfStream() {
        if (!this.mOffloaded) {
            throw new IllegalStateException("EOS not supported on non-offloaded track");
        }
        if (this.mState == 0) {
            throw new IllegalStateException("Uninitialized track");
        }
        if (this.mPlayState != 3) {
            throw new IllegalStateException("EOS not supported if not playing");
        }
        synchronized (this.mStreamEventCbLock) {
            if (this.mStreamEventCbInfoList.size() == 0) {
                throw new IllegalStateException("EOS not supported without StreamEventCallback");
            }
        }
        synchronized (this.mPlayStateLock) {
            native_stop();
            this.mOffloadEosPending = true;
            this.mPlayState = 4;
        }
    }

    public boolean isOffloadedPlayback() {
        return this.mOffloaded;
    }

    @Deprecated
    public static boolean isDirectPlaybackSupported(AudioFormat audioFormat, AudioAttributes audioAttributes) {
        if (audioFormat == null) {
            throw new IllegalArgumentException("Illegal null AudioFormat argument");
        }
        if (audioAttributes == null) {
            throw new IllegalArgumentException("Illegal null AudioAttributes argument");
        }
        return native_is_direct_output_supported(audioFormat.getEncoding(), audioFormat.getSampleRate(), audioFormat.getChannelMask(), audioFormat.getChannelIndexMask(), audioAttributes.getContentType(), audioAttributes.getUsage(), audioAttributes.getFlags());
    }

    private static boolean isValidAudioDescriptionMixLevel(float f) {
        return !Float.isNaN(f) && f <= 48.0f;
    }

    public boolean setAudioDescriptionMixLeveldB(float f) {
        if (isValidAudioDescriptionMixLevel(f)) {
            return native_set_audio_description_mix_level_db(f) == 0;
        }
        throw new IllegalArgumentException("level is out of range" + f);
    }

    public float getAudioDescriptionMixLeveldB() {
        float[] fArr = {Float.NEGATIVE_INFINITY};
        try {
            if (native_get_audio_description_mix_level_db(fArr) == 0) {
                if (!Float.isNaN(fArr[0])) {
                    return fArr[0];
                }
            }
        } catch (Exception unused) {
        }
        return Float.NEGATIVE_INFINITY;
    }

    public boolean setDualMonoMode(int i) {
        if (isValidDualMonoMode(i)) {
            return native_set_dual_mono_mode(i) == 0;
        }
        throw new IllegalArgumentException("Invalid Dual Mono mode " + i);
    }

    public int getDualMonoMode() {
        int[] iArr = {0};
        try {
            if (native_get_dual_mono_mode(iArr) == 0) {
                if (isValidDualMonoMode(iArr[0])) {
                    return iArr[0];
                }
            }
        } catch (Exception unused) {
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean shouldEnablePowerSaving(AudioAttributes audioAttributes, AudioFormat audioFormat, int i, int i2) {
        int allFlags = audioAttributes.getAllFlags() & 792;
        if ((audioAttributes == null || (allFlags == 0 && audioAttributes.getUsage() == 1 && (audioAttributes.getContentType() == 0 || audioAttributes.getContentType() == 1 || audioAttributes.getContentType() == 2 || audioAttributes.getContentType() == 3))) && audioFormat != null && audioFormat.getSampleRate() != 0 && AudioFormat.isEncodingLinearPcm(audioFormat.getEncoding()) && AudioFormat.isValidEncoding(audioFormat.getEncoding()) && audioFormat.getChannelCount() >= 1 && i2 == 1) {
            return i == 0 || ((long) i) >= (((((long) audioFormat.getChannelCount()) * 100) * ((long) AudioFormat.getBytesPerSample(audioFormat.getEncoding()))) * ((long) audioFormat.getSampleRate())) / 1000;
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0084  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00c5  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00cc  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00e6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void audioParamCheck(int r6, int r7, int r8, int r9, int r10) {
        /*
            Method dump skipped, instructions count: 261
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.media.AudioTrack.audioParamCheck(int, int, int, int, int):void");
    }

    private static boolean isMultichannelConfigSupported(int i, int i2) {
        if ((SUPPORTED_OUT_CHANNELS & i) != i) {
            loge("Channel configuration features unsupported channels");
            return false;
        }
        int channelCountFromOutChannelMask = AudioFormat.channelCountFromOutChannelMask(i);
        try {
            int i3 = AudioFormat.isEncodingLinearFrames(i2) ? AudioSystem.OUT_CHANNEL_COUNT_MAX : 24;
            if (channelCountFromOutChannelMask > i3) {
                loge("Channel configuration contains too many channels for encoding " + i2 + NavigationBarInflaterView.KEY_CODE_START + channelCountFromOutChannelMask + " > " + i3 + NavigationBarInflaterView.KEY_CODE_END);
                return false;
            }
            if ((i & 12) != 12) {
                loge("Front channels must be present in multichannel configurations");
                return false;
            }
            for (Map.Entry<String, Integer> entry : CHANNEL_PAIR_MAP.entrySet()) {
                int intValue = entry.getValue().intValue();
                int i4 = i & intValue;
                if (i4 != 0 && i4 != intValue) {
                    loge("Channel pair (" + entry.getKey() + ") cannot be used independently");
                    return false;
                }
            }
            return true;
        } catch (IllegalArgumentException e) {
            loge("Unsupported encoding " + e);
            return false;
        }
    }

    private void audioBuffSizeCheck(int i) {
        int bytesPerSample = AudioFormat.isEncodingLinearFrames(this.mAudioFormat) ? this.mChannelCount * AudioFormat.getBytesPerSample(this.mAudioFormat) : 1;
        if (i % bytesPerSample != 0 || i < 1) {
            throw new IllegalArgumentException("Invalid audio buffer size.");
        }
        this.mNativeBufferSizeInBytes = i;
        this.mNativeBufferSizeInFrames = i / bytesPerSample;
    }

    public void release() {
        synchronized (this.mStreamEventCbLock) {
            endStreamEventHandling();
        }
        try {
            stop();
        } catch (IllegalStateException unused) {
        }
        AudioPolicy audioPolicy = this.mAudioPolicy;
        if (audioPolicy != null) {
            AudioManager.unregisterAudioPolicyAsyncStatic(audioPolicy);
            this.mAudioPolicy = null;
        }
        baseRelease();
        native_release();
        synchronized (this.mPlayStateLock) {
            this.mState = 0;
            this.mPlayState = 1;
            this.mPlayStateLock.notify();
        }
    }

    protected void finalize() {
        tryToDisableNativeRoutingCallback();
        baseRelease();
        native_finalize();
    }

    public int getSampleRate() {
        return this.mSampleRate;
    }

    public int getPlaybackRate() {
        return native_get_playback_rate();
    }

    public PlaybackParams getPlaybackParams() {
        return native_get_playback_params();
    }

    public AudioAttributes getAudioAttributes() {
        AudioAttributes audioAttributes;
        if (this.mState == 0 || (audioAttributes = this.mConfiguredAudioAttributes) == null) {
            throw new IllegalStateException("track not initialized");
        }
        return audioAttributes;
    }

    public int getAudioFormat() {
        return this.mAudioFormat;
    }

    public int getStreamType() {
        return this.mStreamType;
    }

    public int getChannelConfiguration() {
        return this.mChannelConfiguration;
    }

    public AudioFormat getFormat() {
        AudioFormat.Builder encoding = new AudioFormat.Builder().setSampleRate(this.mSampleRate).setEncoding(this.mAudioFormat);
        int i = this.mChannelConfiguration;
        if (i != 0) {
            encoding.setChannelMask(i);
        }
        int i2 = this.mChannelIndexMask;
        if (i2 != 0) {
            encoding.setChannelIndexMask(i2);
        }
        return encoding.build();
    }

    public int getChannelCount() {
        return this.mChannelCount;
    }

    public int getState() {
        return this.mState;
    }

    public int getPlayState() {
        synchronized (this.mPlayStateLock) {
            int i = this.mPlayState;
            if (i == 4) {
                return 3;
            }
            if (i != 5) {
                return i;
            }
            return 2;
        }
    }

    public int getBufferSizeInFrames() {
        return native_get_buffer_size_frames();
    }

    public int setBufferSizeInFrames(int i) {
        if (this.mDataLoadMode == 0 || this.mState == 0) {
            return -3;
        }
        if (i < 0) {
            return -2;
        }
        return native_set_buffer_size_frames(i);
    }

    public int getBufferCapacityInFrames() {
        return native_get_buffer_capacity_frames();
    }

    public int setStartThresholdInFrames(int i) {
        if (this.mState != 1) {
            throw new IllegalStateException("AudioTrack is not initialized");
        }
        if (this.mDataLoadMode != 1) {
            throw new IllegalStateException("AudioTrack must be a streaming track");
        }
        if (i < 1) {
            throw new IllegalArgumentException("startThresholdInFrames " + i + " must be positive");
        }
        return native_setStartThresholdInFrames(i);
    }

    public int getStartThresholdInFrames() {
        if (this.mState != 1) {
            throw new IllegalStateException("AudioTrack is not initialized");
        }
        if (this.mDataLoadMode != 1) {
            throw new IllegalStateException("AudioTrack must be a streaming track");
        }
        return native_getStartThresholdInFrames();
    }

    @Deprecated
    protected int getNativeFrameCount() {
        return native_get_buffer_capacity_frames();
    }

    public int getNotificationMarkerPosition() {
        return native_get_marker_pos();
    }

    public int getPositionNotificationPeriod() {
        return native_get_pos_update_period();
    }

    public int getPlaybackHeadPosition() {
        return native_get_position();
    }

    public int getLatency() {
        return native_get_latency();
    }

    public int getUnderrunCount() {
        return native_get_underrun_count();
    }

    public int getPerformanceMode() {
        int native_get_flags = native_get_flags();
        if ((native_get_flags & 4) != 0) {
            return 1;
        }
        return (native_get_flags & 8) != 0 ? 2 : 0;
    }

    public static int getNativeOutputSampleRate(int i) {
        return native_get_output_sample_rate(i);
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0026  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x002c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int getMinBufferSize(int r3, int r4, int r5) {
        /*
            r0 = 2
            r1 = -2
            if (r4 == r0) goto L1f
            r2 = 3
            if (r4 == r2) goto L20
            r2 = 4
            if (r4 == r2) goto L1f
            r2 = 12
            if (r4 == r2) goto L20
            boolean r0 = isMultichannelConfigSupported(r4, r5)
            if (r0 != 0) goto L1a
            java.lang.String r3 = "getMinBufferSize(): Invalid channel configuration."
            loge(r3)
            return r1
        L1a:
            int r0 = android.media.AudioFormat.channelCountFromOutChannelMask(r4)
            goto L20
        L1f:
            r0 = 1
        L20:
            boolean r4 = android.media.AudioFormat.isPublicEncoding(r5)
            if (r4 != 0) goto L2c
            java.lang.String r3 = "getMinBufferSize(): Invalid audio format."
            loge(r3)
            return r1
        L2c:
            int r4 = android.media.AudioFormat.SAMPLE_RATE_HZ_MIN
            if (r3 < r4) goto L42
            int r4 = android.media.AudioFormat.SAMPLE_RATE_HZ_MAX
            if (r3 <= r4) goto L35
            goto L42
        L35:
            int r3 = native_get_min_buff_size(r3, r0, r5)
            if (r3 > 0) goto L41
            java.lang.String r3 = "getMinBufferSize(): error querying hardware"
            loge(r3)
            r3 = -1
        L41:
            return r3
        L42:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "getMinBufferSize(): "
            r4.<init>(r5)
            r4.append(r3)
            java.lang.String r3 = " Hz is not a supported sample rate."
            r4.append(r3)
            java.lang.String r3 = r4.toString()
            loge(r3)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.media.AudioTrack.getMinBufferSize(int, int, int):int");
    }

    public int getAudioSessionId() {
        return this.mSessionId;
    }

    public boolean getTimestamp(AudioTimestamp audioTimestamp) {
        if (audioTimestamp == null) {
            throw new IllegalArgumentException();
        }
        long[] jArr = new long[2];
        if (native_get_timestamp(jArr) != 0) {
            return false;
        }
        audioTimestamp.framePosition = jArr[0];
        audioTimestamp.nanoTime = jArr[1];
        return true;
    }

    public int getTimestampWithStatus(AudioTimestamp audioTimestamp) {
        if (audioTimestamp == null) {
            throw new IllegalArgumentException();
        }
        long[] jArr = new long[2];
        int native_get_timestamp = native_get_timestamp(jArr);
        audioTimestamp.framePosition = jArr[0];
        audioTimestamp.nanoTime = jArr[1];
        return native_get_timestamp;
    }

    public PersistableBundle getMetrics() {
        return native_getMetrics();
    }

    public void setPlaybackPositionUpdateListener(OnPlaybackPositionUpdateListener onPlaybackPositionUpdateListener) {
        setPlaybackPositionUpdateListener(onPlaybackPositionUpdateListener, null);
    }

    public void setPlaybackPositionUpdateListener(OnPlaybackPositionUpdateListener onPlaybackPositionUpdateListener, Handler handler) {
        if (onPlaybackPositionUpdateListener != null) {
            this.mEventHandlerDelegate = new NativePositionEventHandlerDelegate(this, this, onPlaybackPositionUpdateListener, handler);
        } else {
            this.mEventHandlerDelegate = null;
        }
    }

    private static float clampGainOrLevel(float f) {
        if (Float.isNaN(f)) {
            throw new IllegalArgumentException();
        }
        if (f < 0.0f) {
            return 0.0f;
        }
        if (f > 1.0f) {
            return 1.0f;
        }
        return f;
    }

    @Deprecated
    public int setStereoVolume(float f, float f2) {
        if (this.mState == 0) {
            return -3;
        }
        baseSetVolume(f, f2);
        return 0;
    }

    @Override // android.media.PlayerBase
    void playerSetVolume(boolean z, float f, float f2) {
        if (z) {
            f = 0.0f;
        }
        float clampGainOrLevel = clampGainOrLevel(f);
        if (z) {
            f2 = 0.0f;
        }
        native_setVolume(clampGainOrLevel, clampGainOrLevel(f2));
    }

    public int setVolume(float f) {
        return setStereoVolume(f, f);
    }

    @Override // android.media.PlayerBase
    int playerApplyVolumeShaper(VolumeShaper.Configuration configuration, VolumeShaper.Operation operation) {
        return native_applyVolumeShaper(configuration, operation);
    }

    @Override // android.media.PlayerBase
    VolumeShaper.State playerGetVolumeShaperState(int i) {
        return native_getVolumeShaperState(i);
    }

    @Override // android.media.VolumeAutomation
    public VolumeShaper createVolumeShaper(VolumeShaper.Configuration configuration) {
        return new VolumeShaper(configuration, this);
    }

    public int setPlaybackRate(int i) {
        if (this.mState != 1) {
            return -3;
        }
        if (i <= 0) {
            return -2;
        }
        return native_set_playback_rate(i);
    }

    public void setPlaybackParams(PlaybackParams playbackParams) {
        if (playbackParams == null) {
            throw new IllegalArgumentException("params is null");
        }
        native_set_playback_params(playbackParams);
    }

    public int setNotificationMarkerPosition(int i) {
        if (this.mState == 0) {
            return -3;
        }
        return native_set_marker_pos(i);
    }

    public int setPositionNotificationPeriod(int i) {
        if (this.mState == 0) {
            return -3;
        }
        return native_set_pos_update_period(i);
    }

    public int setPlaybackHeadPosition(int i) {
        if (this.mDataLoadMode == 1 || this.mState == 0 || getPlayState() == 3) {
            return -3;
        }
        if (i < 0 || i > this.mNativeBufferSizeInFrames) {
            return -2;
        }
        return native_set_position(i);
    }

    public int setLoopPoints(int i, int i2, int i3) {
        int i4;
        if (this.mDataLoadMode == 1 || this.mState == 0 || getPlayState() == 3) {
            return -3;
        }
        if (i3 != 0 && (i < 0 || i >= (i4 = this.mNativeBufferSizeInFrames) || i >= i2 || i2 > i4)) {
            return -2;
        }
        return native_set_loop(i, i2, i3);
    }

    public int setPresentation(AudioPresentation audioPresentation) {
        if (audioPresentation == null) {
            throw new IllegalArgumentException("audio presentation is null");
        }
        return native_setPresentation(audioPresentation.getPresentationId(), audioPresentation.getProgramId());
    }

    @Deprecated
    protected void setState(int i) {
        this.mState = i;
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [android.media.AudioTrack$1] */
    public void play() throws IllegalStateException {
        if (this.mState != 1) {
            throw new IllegalStateException("play() called on uninitialized AudioTrack.");
        }
        final int startDelayMs = getStartDelayMs();
        if (startDelayMs == 0) {
            startImpl();
        } else {
            new Thread() { // from class: android.media.AudioTrack.1
                @Override // java.lang.Thread, java.lang.Runnable
                public void run() {
                    try {
                        Thread.sleep(startDelayMs);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    AudioTrack.this.baseSetStartDelayMs(0);
                    try {
                        AudioTrack.this.startImpl();
                    } catch (IllegalStateException unused) {
                    }
                }
            }.start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startImpl() {
        synchronized (this.mRoutingChangeListeners) {
            if (!this.mEnableSelfRoutingMonitor) {
                this.mEnableSelfRoutingMonitor = testEnableNativeRoutingCallbacksLocked();
            }
        }
        synchronized (this.mPlayStateLock) {
            baseStart(new int[0]);
            native_start();
            if (this.mPlayState == 5) {
                this.mPlayState = 4;
            } else {
                this.mPlayState = 3;
                this.mOffloadEosPending = false;
            }
        }
    }

    public void stop() throws IllegalStateException {
        if (this.mState != 1) {
            throw new IllegalStateException("stop() called on uninitialized AudioTrack.");
        }
        synchronized (this.mPlayStateLock) {
            native_stop();
            baseStop();
            if (this.mOffloaded && this.mPlayState != 5) {
                this.mPlayState = 4;
            } else {
                this.mPlayState = 1;
                this.mOffloadEosPending = false;
                this.mAvSyncHeader = null;
                this.mAvSyncBytesRemaining = 0;
                this.mPlayStateLock.notify();
            }
        }
        tryToDisableNativeRoutingCallback();
    }

    public void pause() throws IllegalStateException {
        if (this.mState != 1) {
            throw new IllegalStateException("pause() called on uninitialized AudioTrack.");
        }
        synchronized (this.mPlayStateLock) {
            native_pause();
            basePause();
            if (this.mPlayState == 4) {
                this.mPlayState = 5;
            } else {
                this.mPlayState = 2;
            }
        }
    }

    public void flush() {
        if (this.mState == 1) {
            native_flush();
            this.mAvSyncHeader = null;
            this.mAvSyncBytesRemaining = 0;
        }
    }

    public int write(byte[] bArr, int i, int i2) {
        return write(bArr, i, i2, 0);
    }

    public int write(byte[] bArr, int i, int i2, int i3) {
        int i4;
        if (this.mState == 0 || this.mAudioFormat == 4) {
            return -3;
        }
        if (i3 != 0 && i3 != 1) {
            Log.e(TAG, "AudioTrack.write() called with invalid blocking mode");
            return -2;
        }
        if (bArr == null || i < 0 || i2 < 0 || (i4 = i + i2) < 0 || i4 > bArr.length) {
            return -2;
        }
        if (!blockUntilOffloadDrain(i3)) {
            return 0;
        }
        int native_write_byte = native_write_byte(bArr, i, i2, this.mAudioFormat, i3 == 0);
        if (this.mDataLoadMode == 0 && this.mState == 2 && native_write_byte > 0) {
            this.mState = 1;
        }
        return native_write_byte;
    }

    public int write(short[] sArr, int i, int i2) {
        return write(sArr, i, i2, 0);
    }

    public int write(short[] sArr, int i, int i2, int i3) {
        int i4;
        int i5;
        if (this.mState == 0 || (i4 = this.mAudioFormat) == 4 || i4 > 20) {
            return -3;
        }
        if (i3 != 0 && i3 != 1) {
            Log.e(TAG, "AudioTrack.write() called with invalid blocking mode");
            return -2;
        }
        if (sArr == null || i < 0 || i2 < 0 || (i5 = i + i2) < 0 || i5 > sArr.length) {
            return -2;
        }
        if (!blockUntilOffloadDrain(i3)) {
            return 0;
        }
        int native_write_short = native_write_short(sArr, i, i2, this.mAudioFormat, i3 == 0);
        if (this.mDataLoadMode == 0 && this.mState == 2 && native_write_short > 0) {
            this.mState = 1;
        }
        return native_write_short;
    }

    public int write(float[] fArr, int i, int i2, int i3) {
        int i4;
        if (this.mState == 0) {
            Log.e(TAG, "AudioTrack.write() called in invalid state STATE_UNINITIALIZED");
            return -3;
        }
        if (this.mAudioFormat != 4) {
            Log.e(TAG, "AudioTrack.write(float[] ...) requires format ENCODING_PCM_FLOAT");
            return -3;
        }
        if (i3 != 0 && i3 != 1) {
            Log.e(TAG, "AudioTrack.write() called with invalid blocking mode");
            return -2;
        }
        if (fArr == null || i < 0 || i2 < 0 || (i4 = i + i2) < 0 || i4 > fArr.length) {
            Log.e(TAG, "AudioTrack.write() called with invalid array, offset, or size");
            return -2;
        }
        if (!blockUntilOffloadDrain(i3)) {
            return 0;
        }
        int native_write_float = native_write_float(fArr, i, i2, this.mAudioFormat, i3 == 0);
        if (this.mDataLoadMode == 0 && this.mState == 2 && native_write_float > 0) {
            this.mState = 1;
        }
        return native_write_float;
    }

    public int write(ByteBuffer byteBuffer, int i, int i2) {
        AudioTrack audioTrack;
        int native_write_byte;
        if (this.mState == 0) {
            Log.e(TAG, "AudioTrack.write() called in invalid state STATE_UNINITIALIZED");
            return -3;
        }
        if (i2 != 0 && i2 != 1) {
            Log.e(TAG, "AudioTrack.write() called with invalid blocking mode");
            return -2;
        }
        if (byteBuffer == null || i < 0 || i > byteBuffer.remaining()) {
            Log.e(TAG, "AudioTrack.write() called with invalid size (" + i + ") value");
            return -2;
        }
        if (!blockUntilOffloadDrain(i2)) {
            return 0;
        }
        if (byteBuffer.isDirect()) {
            audioTrack = this;
            native_write_byte = audioTrack.native_write_native_bytes(byteBuffer, byteBuffer.position(), i, this.mAudioFormat, i2 == 0);
            byteBuffer = byteBuffer;
        } else {
            audioTrack = this;
            native_write_byte = audioTrack.native_write_byte(NioUtils.unsafeArray(byteBuffer), NioUtils.unsafeArrayOffset(byteBuffer) + byteBuffer.position(), i, audioTrack.mAudioFormat, i2 == 0);
        }
        if (audioTrack.mDataLoadMode == 0 && audioTrack.mState == 2 && native_write_byte > 0) {
            audioTrack.mState = 1;
        }
        if (native_write_byte > 0) {
            byteBuffer.position(byteBuffer.position() + native_write_byte);
        }
        return native_write_byte;
    }

    public int write(ByteBuffer byteBuffer, int i, int i2, long j) {
        if (this.mState == 0) {
            Log.e(TAG, "AudioTrack.write() called in invalid state STATE_UNINITIALIZED");
            return -3;
        }
        if (i2 != 0 && i2 != 1) {
            Log.e(TAG, "AudioTrack.write() called with invalid blocking mode");
            return -2;
        }
        if (this.mDataLoadMode != 1) {
            Log.e(TAG, "AudioTrack.write() with timestamp called for non-streaming mode track");
            return -3;
        }
        if ((this.mAttributes.getFlags() & 16) == 0) {
            Log.d(TAG, "AudioTrack.write() called on a regular AudioTrack. Ignoring pts...");
            return write(byteBuffer, i, i2);
        }
        if (byteBuffer == null || i < 0 || i > byteBuffer.remaining()) {
            Log.e(TAG, "AudioTrack.write() called with invalid size (" + i + ") value");
            return -2;
        }
        if (!blockUntilOffloadDrain(i2)) {
            return 0;
        }
        if (this.mAvSyncHeader == null) {
            ByteBuffer allocate = ByteBuffer.allocate(this.mOffset);
            this.mAvSyncHeader = allocate;
            allocate.order(ByteOrder.BIG_ENDIAN);
            this.mAvSyncHeader.putInt(1431633922);
        }
        if (this.mAvSyncBytesRemaining == 0) {
            this.mAvSyncHeader.putInt(4, i);
            this.mAvSyncHeader.putLong(8, j);
            this.mAvSyncHeader.putInt(16, this.mOffset);
            this.mAvSyncHeader.position(0);
            this.mAvSyncBytesRemaining = i;
        }
        if (this.mAvSyncHeader.remaining() != 0) {
            ByteBuffer byteBuffer2 = this.mAvSyncHeader;
            int write = write(byteBuffer2, byteBuffer2.remaining(), i2);
            if (write < 0) {
                Log.e(TAG, "AudioTrack.write() could not write timestamp header!");
                this.mAvSyncHeader = null;
                this.mAvSyncBytesRemaining = 0;
                return write;
            }
            if (this.mAvSyncHeader.remaining() > 0) {
                Log.v(TAG, "AudioTrack.write() partial timestamp header written.");
                return 0;
            }
        }
        int write2 = write(byteBuffer, Math.min(this.mAvSyncBytesRemaining, i), i2);
        if (write2 < 0) {
            Log.e(TAG, "AudioTrack.write() could not write audio data!");
            this.mAvSyncHeader = null;
            this.mAvSyncBytesRemaining = 0;
            return write2;
        }
        this.mAvSyncBytesRemaining -= write2;
        return write2;
    }

    public int reloadStaticData() {
        if (this.mDataLoadMode == 1 || this.mState != 1) {
            return -3;
        }
        return native_reload_static();
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x0012, code lost:
    
        return false;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean blockUntilOffloadDrain(int r5) {
        /*
            r4 = this;
            java.lang.Object r0 = r4.mPlayStateLock
            monitor-enter(r0)
        L3:
            int r1 = r4.mPlayState     // Catch: java.lang.Throwable -> L1a
            r2 = 4
            r3 = 1
            if (r1 == r2) goto Lf
            r2 = 5
            if (r1 != r2) goto Ld
            goto Lf
        Ld:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L1a
            return r3
        Lf:
            if (r5 != r3) goto L14
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L1a
            r4 = 0
            return r4
        L14:
            java.lang.Object r1 = r4.mPlayStateLock     // Catch: java.lang.InterruptedException -> L3 java.lang.Throwable -> L1a
            r1.wait()     // Catch: java.lang.InterruptedException -> L3 java.lang.Throwable -> L1a
            goto L3
        L1a:
            r4 = move-exception
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L1a
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: android.media.AudioTrack.blockUntilOffloadDrain(int):boolean");
    }

    public int attachAuxEffect(int i) {
        if (this.mState == 0) {
            return -3;
        }
        return native_attachAuxEffect(i);
    }

    public int setAuxEffectSendLevel(float f) {
        if (this.mState == 0) {
            return -3;
        }
        return baseSetAuxEffectSendLevel(f);
    }

    @Override // android.media.PlayerBase
    int playerSetAuxEffectSendLevel(boolean z, float f) {
        if (z) {
            f = 0.0f;
        }
        return native_setAuxEffectSendLevel(clampGainOrLevel(f)) == 0 ? 0 : -1;
    }

    @Override // android.media.AudioRouting
    public boolean setPreferredDevice(AudioDeviceInfo audioDeviceInfo) {
        if (audioDeviceInfo != null && !audioDeviceInfo.isSink()) {
            return false;
        }
        boolean native_setOutputDevice = native_setOutputDevice(audioDeviceInfo != null ? audioDeviceInfo.getId() : 0);
        if (!native_setOutputDevice) {
            return native_setOutputDevice;
        }
        synchronized (this) {
            this.mPreferredDevice = audioDeviceInfo;
        }
        return native_setOutputDevice;
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
        int[] native_getRoutedDeviceIds = native_getRoutedDeviceIds();
        if (native_getRoutedDeviceIds != null && native_getRoutedDeviceIds.length != 0) {
            for (int i : native_getRoutedDeviceIds) {
                AudioDeviceInfo deviceForPortId = AudioManager.getDeviceForPortId(i, 2);
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

    private void tryToDisableNativeRoutingCallback() {
        synchronized (this.mRoutingChangeListeners) {
            if (this.mEnableSelfRoutingMonitor) {
                this.mEnableSelfRoutingMonitor = false;
                testDisableNativeRoutingCallbacksLocked();
            }
        }
    }

    private boolean testEnableNativeRoutingCallbacksLocked() {
        if (this.mRoutingChangeListeners.size() != 0 || this.mEnableSelfRoutingMonitor) {
            return false;
        }
        try {
            native_enableDeviceCallback();
            return true;
        } catch (IllegalStateException e) {
            if (!Log.isLoggable(TAG, 3)) {
                return false;
            }
            Log.d(TAG, "testEnableNativeRoutingCallbacks failed", e);
            return false;
        }
    }

    private void testDisableNativeRoutingCallbacksLocked() {
        if (this.mRoutingChangeListeners.size() != 0 || this.mEnableSelfRoutingMonitor) {
            return;
        }
        try {
            native_disableDeviceCallback();
        } catch (IllegalStateException unused) {
        }
    }

    @Override // android.media.AudioRouting
    public void addOnRoutingChangedListener(AudioRouting.OnRoutingChangedListener onRoutingChangedListener, Handler handler) {
        synchronized (this.mRoutingChangeListeners) {
            if (onRoutingChangedListener != null) {
                if (!this.mRoutingChangeListeners.containsKey(onRoutingChangedListener)) {
                    this.mEnableSelfRoutingMonitor = testEnableNativeRoutingCallbacksLocked();
                    ArrayMap<AudioRouting.OnRoutingChangedListener, NativeRoutingEventHandlerDelegate> arrayMap = this.mRoutingChangeListeners;
                    if (handler == null) {
                        handler = new Handler(this.mInitializationLooper);
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
            }
            testDisableNativeRoutingCallbacksLocked();
        }
    }

    @Deprecated
    public interface OnRoutingChangedListener extends AudioRouting.OnRoutingChangedListener {
        void onRoutingChanged(AudioTrack audioTrack);

        @Override // android.media.AudioRouting.OnRoutingChangedListener
        default void onRoutingChanged(AudioRouting audioRouting) {
            if (audioRouting instanceof AudioTrack) {
                onRoutingChanged((AudioTrack) audioRouting);
            }
        }
    }

    @Deprecated
    public void addOnRoutingChangedListener(OnRoutingChangedListener onRoutingChangedListener, Handler handler) {
        addOnRoutingChangedListener((AudioRouting.OnRoutingChangedListener) onRoutingChangedListener, handler);
    }

    @Deprecated
    public void removeOnRoutingChangedListener(OnRoutingChangedListener onRoutingChangedListener) {
        removeOnRoutingChangedListener((AudioRouting.OnRoutingChangedListener) onRoutingChangedListener);
    }

    private void broadcastRoutingChange() {
        AudioManager.resetAudioPortGeneration();
        baseUpdateDeviceIds(getRoutedDevicesInternal());
        synchronized (this.mRoutingChangeListeners) {
            Iterator<NativeRoutingEventHandlerDelegate> it = this.mRoutingChangeListeners.values().iterator();
            while (it.hasNext()) {
                it.next().notifyClient();
            }
        }
    }

    public void addOnCodecFormatChangedListener(Executor executor, final OnCodecFormatChangedListener onCodecFormatChangedListener) {
        this.mCodecFormatChangedListeners.add(onCodecFormatChangedListener, executor, new Utils.ListenerList.Listener() { // from class: android.media.AudioTrack$$ExternalSyntheticLambda0
            @Override // android.media.Utils.ListenerList.Listener
            public final void onEvent(int i, Object obj) {
                AudioTrack.this.lambda$addOnCodecFormatChangedListener$0(onCodecFormatChangedListener, i, (AudioMetadataReadMap) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addOnCodecFormatChangedListener$0(OnCodecFormatChangedListener onCodecFormatChangedListener, int i, AudioMetadataReadMap audioMetadataReadMap) {
        onCodecFormatChangedListener.onCodecFormatChanged(this, audioMetadataReadMap);
    }

    public void removeOnCodecFormatChangedListener(OnCodecFormatChangedListener onCodecFormatChangedListener) {
        this.mCodecFormatChangedListeners.remove(onCodecFormatChangedListener);
    }

    public void registerStreamEventCallback(Executor executor, StreamEventCallback streamEventCallback) {
        if (streamEventCallback == null) {
            throw new IllegalArgumentException("Illegal null StreamEventCallback");
        }
        if (!this.mOffloaded) {
            throw new IllegalStateException("Cannot register StreamEventCallback on non-offloaded AudioTrack");
        }
        if (executor == null) {
            throw new IllegalArgumentException("Illegal null Executor for the StreamEventCallback");
        }
        synchronized (this.mStreamEventCbLock) {
            Iterator<StreamEventCbInfo> it = this.mStreamEventCbInfoList.iterator();
            while (it.hasNext()) {
                if (it.next().mStreamEventCb == streamEventCallback) {
                    throw new IllegalArgumentException("StreamEventCallback already registered");
                }
            }
            beginStreamEventHandling();
            this.mStreamEventCbInfoList.add(new StreamEventCbInfo(executor, streamEventCallback));
        }
    }

    public void unregisterStreamEventCallback(StreamEventCallback streamEventCallback) {
        if (streamEventCallback == null) {
            throw new IllegalArgumentException("Illegal null StreamEventCallback");
        }
        if (!this.mOffloaded) {
            throw new IllegalStateException("No StreamEventCallback on non-offloaded AudioTrack");
        }
        synchronized (this.mStreamEventCbLock) {
            Iterator<StreamEventCbInfo> it = this.mStreamEventCbInfoList.iterator();
            while (it.hasNext()) {
                StreamEventCbInfo next = it.next();
                if (next.mStreamEventCb == streamEventCallback) {
                    this.mStreamEventCbInfoList.remove(next);
                    if (this.mStreamEventCbInfoList.size() == 0) {
                        endStreamEventHandling();
                    }
                }
            }
            throw new IllegalArgumentException("StreamEventCallback was not registered");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class StreamEventCbInfo {
        final StreamEventCallback mStreamEventCb;
        final Executor mStreamEventExec;

        StreamEventCbInfo(Executor executor, StreamEventCallback streamEventCallback) {
            this.mStreamEventExec = executor;
            this.mStreamEventCb = streamEventCallback;
        }
    }

    void handleStreamEventFromNative(int i, int i2) {
        if (this.mStreamEventHandler == null) {
            return;
        }
        if (i == 6) {
            this.mStreamEventHandler.sendMessage(this.mStreamEventHandler.obtainMessage(6));
            return;
        }
        if (i == 7) {
            this.mStreamEventHandler.sendMessage(this.mStreamEventHandler.obtainMessage(7));
        } else {
            if (i != 9) {
                return;
            }
            this.mStreamEventHandler.removeMessages(9);
            this.mStreamEventHandler.sendMessage(this.mStreamEventHandler.obtainMessage(9, i2, 0));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class StreamEventHandler extends Handler {
        StreamEventHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(final Message message) {
            synchronized (AudioTrack.this.mStreamEventCbLock) {
                if (message.what == 7) {
                    synchronized (AudioTrack.this.mPlayStateLock) {
                        if (AudioTrack.this.mPlayState == 4) {
                            if (AudioTrack.this.mOffloadEosPending) {
                                AudioTrack.this.native_start();
                                AudioTrack.this.mPlayState = 3;
                            } else {
                                AudioTrack.this.mAvSyncHeader = null;
                                AudioTrack.this.mAvSyncBytesRemaining = 0;
                                AudioTrack.this.mPlayState = 1;
                            }
                            AudioTrack.this.mOffloadEosPending = false;
                            AudioTrack.this.mPlayStateLock.notify();
                        }
                    }
                }
                if (AudioTrack.this.mStreamEventCbInfoList.size() == 0) {
                    return;
                }
                LinkedList linkedList = new LinkedList(AudioTrack.this.mStreamEventCbInfoList);
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    Iterator it = linkedList.iterator();
                    while (it.hasNext()) {
                        final StreamEventCbInfo streamEventCbInfo = (StreamEventCbInfo) it.next();
                        int i = message.what;
                        if (i == 6) {
                            streamEventCbInfo.mStreamEventExec.execute(new Runnable() { // from class: android.media.AudioTrack$StreamEventHandler$$ExternalSyntheticLambda1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    AudioTrack.StreamEventHandler.this.lambda$handleMessage$1(streamEventCbInfo);
                                }
                            });
                        } else if (i == 7) {
                            streamEventCbInfo.mStreamEventExec.execute(new Runnable() { // from class: android.media.AudioTrack$StreamEventHandler$$ExternalSyntheticLambda2
                                @Override // java.lang.Runnable
                                public final void run() {
                                    AudioTrack.StreamEventHandler.this.lambda$handleMessage$2(streamEventCbInfo);
                                }
                            });
                        } else if (i == 9) {
                            streamEventCbInfo.mStreamEventExec.execute(new Runnable() { // from class: android.media.AudioTrack$StreamEventHandler$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    AudioTrack.StreamEventHandler.this.lambda$handleMessage$0(streamEventCbInfo, message);
                                }
                            });
                        }
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$handleMessage$0(StreamEventCbInfo streamEventCbInfo, Message message) {
            streamEventCbInfo.mStreamEventCb.onDataRequest(AudioTrack.this, message.arg1);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$handleMessage$1(StreamEventCbInfo streamEventCbInfo) {
            streamEventCbInfo.mStreamEventCb.onTearDown(AudioTrack.this);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$handleMessage$2(StreamEventCbInfo streamEventCbInfo) {
            streamEventCbInfo.mStreamEventCb.onPresentationEnded(AudioTrack.this);
        }
    }

    private void beginStreamEventHandling() {
        if (this.mStreamEventHandlerThread == null) {
            HandlerThread handlerThread = new HandlerThread("android.media.AudioTrack.StreamEvent");
            this.mStreamEventHandlerThread = handlerThread;
            handlerThread.start();
            Looper looper = this.mStreamEventHandlerThread.getLooper();
            if (looper != null) {
                this.mStreamEventHandler = new StreamEventHandler(looper);
            }
        }
    }

    private void endStreamEventHandling() {
        HandlerThread handlerThread = this.mStreamEventHandlerThread;
        if (handlerThread != null) {
            handlerThread.quit();
            this.mStreamEventHandlerThread = null;
        }
    }

    public void setLogSessionId(LogSessionId logSessionId) {
        Objects.requireNonNull(logSessionId);
        if (this.mState == 0) {
            throw new IllegalStateException("track not initialized");
        }
        native_setLogSessionId(logSessionId.getStringId());
        this.mLogSessionId = logSessionId;
    }

    public LogSessionId getLogSessionId() {
        return this.mLogSessionId;
    }

    private class NativePositionEventHandlerDelegate {
        private final Handler mHandler;

        NativePositionEventHandlerDelegate(final AudioTrack audioTrack, final AudioTrack audioTrack2, final OnPlaybackPositionUpdateListener onPlaybackPositionUpdateListener, Handler handler) {
            Looper looper;
            if (handler != null) {
                looper = handler.getLooper();
            } else {
                looper = audioTrack.mInitializationLooper;
            }
            Looper looper2 = looper;
            if (looper2 != null) {
                this.mHandler = new Handler(this, looper2) { // from class: android.media.AudioTrack.NativePositionEventHandlerDelegate.1
                    @Override // android.os.Handler
                    public void handleMessage(Message message) {
                        if (audioTrack2 == null) {
                            return;
                        }
                        int i = message.what;
                        if (i == 3) {
                            OnPlaybackPositionUpdateListener onPlaybackPositionUpdateListener2 = onPlaybackPositionUpdateListener;
                            if (onPlaybackPositionUpdateListener2 != null) {
                                onPlaybackPositionUpdateListener2.onMarkerReached(audioTrack2);
                                return;
                            }
                            return;
                        }
                        if (i == 4) {
                            OnPlaybackPositionUpdateListener onPlaybackPositionUpdateListener3 = onPlaybackPositionUpdateListener;
                            if (onPlaybackPositionUpdateListener3 != null) {
                                onPlaybackPositionUpdateListener3.onPeriodicNotification(audioTrack2);
                                return;
                            }
                            return;
                        }
                        AudioTrack.loge("Unknown native event type: " + message.what);
                    }
                };
            } else {
                this.mHandler = null;
            }
        }

        Handler getHandler() {
            return this.mHandler;
        }
    }

    @Override // android.media.PlayerBase
    void playerStart() {
        play();
    }

    @Override // android.media.PlayerBase
    void playerPause() {
        pause();
    }

    @Override // android.media.PlayerBase
    void playerStop() {
        stop();
    }

    private static void postEventFromNative(Object obj, int i, int i2, int i3, Object obj2) {
        Handler handler;
        AudioTrack audioTrack = (AudioTrack) ((WeakReference) obj).get();
        if (audioTrack == null) {
            return;
        }
        if (i == 1000) {
            audioTrack.broadcastRoutingChange();
            return;
        }
        if (i == 100) {
            ByteBuffer byteBuffer = (ByteBuffer) obj2;
            byteBuffer.order(ByteOrder.nativeOrder());
            byteBuffer.rewind();
            AudioMetadata.BaseMap fromByteBuffer = AudioMetadata.fromByteBuffer(byteBuffer);
            if (fromByteBuffer == null) {
                Log.e(TAG, "Unable to get audio metadata from byte buffer");
                return;
            } else {
                audioTrack.mCodecFormatChangedListeners.notify(0, fromByteBuffer);
                return;
            }
        }
        if (i == 9 || i == 6 || i == 7) {
            audioTrack.handleStreamEventFromNative(i, i2);
            return;
        }
        NativePositionEventHandlerDelegate nativePositionEventHandlerDelegate = audioTrack.mEventHandlerDelegate;
        if (nativePositionEventHandlerDelegate == null || (handler = nativePositionEventHandlerDelegate.getHandler()) == null) {
            return;
        }
        handler.sendMessage(handler.obtainMessage(i, i2, i3, obj2));
    }

    private static void logd(String str) {
        Log.d(TAG, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void loge(String str) {
        Log.e(TAG, str);
    }

    public static final class MetricsConstants {
        public static final String ATTRIBUTES = "android.media.audiotrack.attributes";

        @Deprecated
        public static final String CHANNELMASK = "android.media.audiorecord.channelmask";
        public static final String CHANNEL_MASK = "android.media.audiotrack.channelMask";
        public static final String CONTENTTYPE = "android.media.audiotrack.type";
        public static final String ENCODING = "android.media.audiotrack.encoding";
        public static final String FRAME_COUNT = "android.media.audiotrack.frameCount";
        private static final String MM_PREFIX = "android.media.audiotrack.";
        public static final String PORT_ID = "android.media.audiotrack.portId";

        @Deprecated
        public static final String SAMPLERATE = "android.media.audiorecord.samplerate";
        public static final String SAMPLE_RATE = "android.media.audiotrack.sampleRate";
        public static final String STREAMTYPE = "android.media.audiotrack.streamtype";
        public static final String USAGE = "android.media.audiotrack.usage";

        private MetricsConstants() {
        }
    }
}
