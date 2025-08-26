package com.samsung.android.media;

import android.app.ActivityThread;
import android.app.Application;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioAttributes;
import android.media.AudioDeviceInfo;
import android.media.Cea708CaptionRenderer;
import android.media.ClosedCaptionRenderer;
import android.media.MediaFormat;
import android.media.MediaHTTPService;
import android.media.MediaTimeProvider;
import android.media.PlaybackParams;
import android.media.RingtoneManager;
import android.media.SubtitleController;
import android.media.SubtitleData;
import android.media.SubtitleTrack;
import android.media.TimedText;
import android.media.TtmlRenderer;
import android.media.WebVttRenderer;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.PowerManager;
import android.os.SystemProperties;
import android.util.Log;
import android.util.Pair;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.widget.VideoView;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;

/* loaded from: classes6.dex */
public class SemMediaPlayer implements SubtitleController.Listener {
    public static final int AUDIO_VOLUME_FADE_IN = 1;
    public static final int AUDIO_VOLUME_FADE_INOUT = 3;
    public static final int AUDIO_VOLUME_FADE_NONE = 0;
    public static final int AUDIO_VOLUME_FADE_OUT = 2;
    public static final AudioEraserConfig AUTO_CONFIG;
    public static final int FRAME_OPTION_SUPER_HDR = 1;
    private static final int INVOKE_ID_ADD_EXTERNAL_SOURCE = 2;
    private static final int INVOKE_ID_ADD_EXTERNAL_SOURCE_FD = 3;
    private static final int INVOKE_ID_DESELECT_TRACK = 5;
    private static final int INVOKE_ID_GET_SELECTED_TRACK = 7;
    private static final int INVOKE_ID_GET_TRACK_INFO = 1;
    private static final int INVOKE_ID_REMOVE_EXTERNAL_SOURCE = 8;
    private static final int INVOKE_ID_SELECT_TRACK = 4;
    public static final int KEY_PARAMETER_ADAPTIVE_ACCURATE_SEEK_THRESHOLD = 35005;
    private static final int KEY_PARAMETER_DYNAMIC_VIEW_CONFIGURATION = 38001;
    private static final int KEY_PARAMETER_DYNAMIC_VIEW_DELEGATE_CONFIGURATION = 38002;
    public static final int KEY_PARAMETER_ENABLE_ALL_SUPER_SLOW_REGION = 36000;
    public static final int KEY_PARAMETER_ENABLE_AUDIO_ERASER = 36001;
    public static final int KEY_PARAMETER_EXCLUDE_AUDIO_TRACK = 35004;
    public static final int KEY_PARAMETER_HOVERING_TYPE = 31950;
    public static final int KEY_PARAMETER_TIMED_TEXT_TRACK_TIME_SYNC = 31501;
    public static final int KEY_PARAMETER_USE_SKIP_SILENCE_VAD = 33001;
    public static final int KEY_PARAMETER_USE_SW_AUDIO_DECODER = 33000;
    private static final int MEDIA_BUFFERING_UPDATE = 3;
    private static final int MEDIA_CHANGED_VIDEO_SIZE = 5;
    private static final int MEDIA_ERROR = 100;
    public static final int MEDIA_ERROR_IO = -1004;
    public static final int MEDIA_ERROR_MALFORMED = -1007;
    public static final int MEDIA_ERROR_RESOURCE_OVERSPEC = -5001;
    public static final int MEDIA_ERROR_TIMED_OUT = -110;
    public static final int MEDIA_ERROR_UNKNOWN = 1;
    public static final int MEDIA_ERROR_UNSUPPORTED = -1010;
    private static final int MEDIA_INFO = 200;
    public static final int MEDIA_INFO_AUDIO_ERASER_INIT_COMPLETED = 10982;
    public static final int MEDIA_INFO_BUFFERING_END = 702;
    public static final int MEDIA_INFO_BUFFERING_START = 701;
    public static final int MEDIA_INFO_EXTERNAL_METADATA_UPDATE = 803;
    public static final int MEDIA_INFO_METADATA_UPDATE = 802;
    public static final int MEDIA_INFO_NO_AUDIO = 10972;
    public static final int MEDIA_INFO_NO_VIDEO = 10973;
    public static final int MEDIA_INFO_SUBTITLE_TIMED_OUT = 902;
    public static final int MEDIA_INFO_SUPERSLOW_REGION = 10974;
    public static final int MEDIA_INFO_TIMED_TEXT_ERROR = 900;
    public static final int MEDIA_INFO_UNKNOWN = 1;
    public static final int MEDIA_INFO_UNSUPPORTED_AUDIO = 10950;
    public static final int MEDIA_INFO_UNSUPPORTED_SUBTITLE = 901;
    public static final int MEDIA_INFO_UNSUPPORTED_TICKPLAY = 10953;
    public static final int MEDIA_INFO_UNSUPPORTED_VIDEO = 10951;
    public static final int MEDIA_INFO_VIDEO_RENDERING_START = 3;
    private static final int MEDIA_INIT_COMPLETE = 1;
    private static final int MEDIA_NOTIFY_TIME = 98;
    private static final int MEDIA_PAUSED = 7;
    private static final int MEDIA_PLAYBACK_COMPLETE = 2;
    private static final int MEDIA_SEEK_COMPLETE = 4;
    private static final int MEDIA_SUBTITLE_DATA = 201;
    private static final int MEDIA_TIMED_TEXT = 99;
    public static final int PLAYBACK_DIRECTION_BACKWARD = 1;
    public static final int PLAYBACK_DIRECTION_FORWARD = 0;
    public static final int PLAYBACK_EFFECT_BACKWARD = 2;
    public static final int PLAYBACK_EFFECT_FORWARD = 1;
    public static final int PLAYBACK_EFFECT_NONE = 0;
    public static final int PLAYBACK_EFFECT_SWING = 3;
    public static final int PLAYBACK_RATE_AUDIO_MODE_DEFAULT = 0;
    public static final int PLAYBACK_RATE_AUDIO_MODE_RESAMPLE = 2;
    public static final int PLAYBACK_RATE_AUDIO_MODE_STRETCH = 1;
    public static final int SEEK_TYPE_ACCURATE_FRAME = 1;
    public static final int SEEK_TYPE_ADAPTIVE_ACCURATE_FRAME = 5;
    public static final int SEEK_TYPE_CLOSEST_SYNC_FRAME = 4;
    public static final int SEEK_TYPE_ONE_FRAME_BACKWARD = 2;
    public static final int SEEK_TYPE_ONE_FRAME_FORWARD = 3;
    public static final int SEEK_TYPE_VIDEO_PREVIEW = 0;
    private static final String TAG = "SemMediaPlayer";
    public static final int VIDEO_SCALING_MODE_SCALE_TO_FIT = 1;
    public static final int VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING = 2;
    public static final AudioEraserConfig VOICE_PREFERRED_CONFIG;
    private AudioAttributes mAttributes;
    private EventHandler mEventHandler;
    private Handler mExtSubtitleDataHandler;
    private OnSubtitleDataListener mExtSubtitleDataListener;
    private long mNativeContext;
    private long mNativeSurfaceTexture;
    private OnBufferingUpdateListener mOnBufferingUpdateListener;
    private OnErrorListener mOnErrorListener;
    private OnInfoListener mOnInfoListener;
    private OnInitCompleteListener mOnInitCompleteListener;
    private OnPlaybackCompleteListener mOnPlaybackCompleteListener;
    private OnSeekCompleteListener mOnSeekCompleteListener;
    private OnTimedTextListener mOnTimedTextListener;
    private OnVideoSizeChangedListener mOnVideoSizeChangedListener;
    private Vector<InputStream> mOpenSubtitleSources;
    private boolean mScreenOnWhilePlaying;
    private boolean mStayAwake;
    private SubtitleController mSubtitleController;
    private boolean mSubtitleDataListenerDisabled;
    private SurfaceHolder mSurfaceHolder;
    private TimeProvider mTimeProvider;
    private PowerManager.WakeLock mWakeLock = null;
    private Vector<Pair<Integer, SubtitleTrack>> mIndexTrackPairs = new Vector<>();
    private BitSet mInbandTrackIndices = new BitSet();
    private final Object mTimeProviderLock = new Object();
    private ArrayList<SpeedRegion> mSpeedRegions = new ArrayList<>();
    private SuperSlowRegion[] mSuperSlowInfo = null;
    private int mSelectedSubtitleTrackIndex = -1;
    private final OnSubtitleDataListener mIntSubtitleDataListener = new OnSubtitleDataListener() { // from class: com.samsung.android.media.SemMediaPlayer.2
        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.samsung.android.media.SemMediaPlayer.OnSubtitleDataListener
        public void onSubtitleData(SemMediaPlayer semMediaPlayer, SubtitleData subtitleData) {
            int trackIndex = subtitleData.getTrackIndex();
            synchronized (SemMediaPlayer.this.mIndexTrackPairs) {
                Iterator it = SemMediaPlayer.this.mIndexTrackPairs.iterator();
                while (it.hasNext()) {
                    Pair pair = (Pair) it.next();
                    if (pair.first != 0 && ((Integer) pair.first).intValue() == trackIndex && pair.second != 0) {
                        SubtitleTrack subtitleTrack = (SubtitleTrack) pair.second;
                        long startTimeUs = subtitleData.getStartTimeUs() + 1;
                        subtitleTrack.onData(subtitleData.getData(), true, startTimeUs);
                        subtitleTrack.setRunDiscardTimeMs(startTimeUs, (subtitleData.getStartTimeUs() + subtitleData.getDurationUs()) / 1000);
                    }
                }
            }
        }
    };

    @Retention(RetentionPolicy.SOURCE)
    public @interface FrameOption {
    }

    public interface OnBufferingUpdateListener {
        void onBufferingUpdate(SemMediaPlayer semMediaPlayer, int i);
    }

    public interface OnErrorListener {
        boolean onError(SemMediaPlayer semMediaPlayer, int i, int i2);
    }

    public interface OnInfoListener {
        boolean onInfo(SemMediaPlayer semMediaPlayer, int i, int i2);
    }

    public interface OnInitCompleteListener {
        void onInitComplete(SemMediaPlayer semMediaPlayer, TrackInfo[] trackInfoArr);
    }

    public interface OnPlaybackCompleteListener {
        void onPlaybackComplete(SemMediaPlayer semMediaPlayer);
    }

    public interface OnSeekCompleteListener {
        void onSeekComplete(SemMediaPlayer semMediaPlayer);
    }

    public interface OnSubtitleDataListener {
        void onSubtitleData(SemMediaPlayer semMediaPlayer, SubtitleData subtitleData);
    }

    public interface OnTimedTextListener {
        void onTimedText(SemMediaPlayer semMediaPlayer, TimedText timedText);
    }

    public interface OnVideoSizeChangedListener {
        void onVideoSizeChanged(SemMediaPlayer semMediaPlayer, int i, int i2);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface PlaybackDirection {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface PlaybackEffect {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface PlaybackRateAudioMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SeekType {
    }

    private native Bitmap _getCurrentFrame(int i, int i2) throws IllegalStateException;

    private native Bitmap _getCurrentFrame(int i, int i2, int i3) throws IllegalStateException;

    private native void _init(IBinder iBinder, String str, String[] strArr, String[] strArr2, String str2) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException;

    private native void _init(FileDescriptor fileDescriptor, long j, long j2) throws IllegalStateException, IOException, IllegalArgumentException;

    private native void _notifyAt(long j);

    private native void _pause() throws IllegalStateException;

    private native void _release();

    private native void _reset() throws IllegalStateException;

    private native void _seekTo(int i, int i2) throws IllegalStateException;

    private native void _setAudioAttributes(AudioAttributes audioAttributes);

    private native void _setAudioEraserConfig(Parcel parcel);

    private native void _setBackgroundMusic(Parcel parcel) throws IllegalStateException;

    private native boolean _setPreferredDevice(AudioDeviceInfo audioDeviceInfo);

    private native void _setTemporalZoom(int i);

    private native void _setVideoFilter(String str, int i) throws IllegalStateException, UnsupportedOperationException;

    private native void _setVideoFrc(int i, float f, boolean z);

    private native void _setVideoScalingMode(int i) throws IllegalStateException;

    private native void _setVideoSurface(Surface surface) throws IllegalStateException, IllegalArgumentException;

    private native void _setVolume(float f, float f2) throws IllegalStateException;

    private native void _start() throws IllegalStateException;

    private native boolean _updateRegionSEFData(int i, Parcel parcel) throws IllegalStateException;

    private boolean isVideoScalingModeSupported(int i) {
        return i == 1 || i == 2;
    }

    private final native void native_finalize();

    private static final native void native_init();

    private final native int native_invoke(Parcel parcel, Parcel parcel2);

    private final native void native_setup(Object obj, AudioAttributes audioAttributes);

    public native int getCurrentPosition();

    public native int getDuration();

    public native int getLastRenderedVideoPosition() throws IllegalStateException;

    public native int getPlaybackDirection();

    public native int getPlaybackEffect();

    public native PlaybackParams getPlaybackParams();

    public native boolean isLooping();

    public native boolean isPlaying();

    public native boolean isVideoDeflickerSupported() throws IllegalStateException;

    public native boolean isVideoSuperResolutionSupported() throws IllegalStateException;

    public native void setAudioVolumeFade(int i, int i2, int i3, int i4, int i5) throws IllegalStateException, IllegalArgumentException;

    public native void setLooping(boolean z);

    public native boolean setParameter(int i, Parcel parcel);

    public native void setPlaybackDirection(int i);

    public native void setPlaybackEffect(int i, int i2) throws IllegalStateException, IllegalArgumentException;

    public native void setPlaybackParams(PlaybackParams playbackParams);

    public native void setPlaybackRange(int i, int i2) throws IllegalStateException, IllegalArgumentException;

    public native void setVideoDeflickerEnabled(boolean z) throws IllegalStateException, UnsupportedOperationException;

    public native void setVideoSuperResolutionEnabled(boolean z) throws IllegalStateException, UnsupportedOperationException;

    static {
        System.loadLibrary("semmediaplayer_jni");
        native_init();
        AUTO_CONFIG = new AudioEraserConfig.Builder().setVoiceRate(1.0f).setMusicRate(1.0f).setWindRate(0.1f).setOthersRate(0.1f).build();
        VOICE_PREFERRED_CONFIG = new AudioEraserConfig.Builder().setVoiceRate(1.0f).setMusicRate(0.1f).setWindRate(0.1f).setOthersRate(0.1f).build();
    }

    public SemMediaPlayer() {
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
        this.mTimeProvider = new TimeProvider(this);
        this.mOpenSubtitleSources = new Vector<>();
        this.mAttributes = new AudioAttributes.Builder().setUsage(1).build();
        native_setup(new WeakReference(this), this.mAttributes);
    }

    public void init(FileDescriptor fileDescriptor) throws IllegalStateException, IOException, IllegalArgumentException {
        init(fileDescriptor, 0L, 576460752303423487L);
    }

    public void init(FileDescriptor fileDescriptor, long j, long j2) throws IllegalStateException, IOException, IllegalArgumentException {
        _init(fileDescriptor, j, j2);
    }

    public void init(AssetFileDescriptor assetFileDescriptor) throws IllegalStateException, IOException, IllegalArgumentException {
        assetFileDescriptor.getClass();
        if (assetFileDescriptor.getDeclaredLength() < 0) {
            init(assetFileDescriptor.getFileDescriptor());
        } else {
            init(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getDeclaredLength());
        }
    }

    private boolean attemptInit(ContentResolver contentResolver, Uri uri) {
        if (uri == null) {
            Log.e(TAG, "Uri is null, cannot attempt init");
            return false;
        }
        try {
            AssetFileDescriptor assetFileDescriptorOpenAssetFileDescriptor = contentResolver.openAssetFileDescriptor(uri, "r");
            try {
                init(assetFileDescriptorOpenAssetFileDescriptor);
                if (assetFileDescriptorOpenAssetFileDescriptor != null) {
                    assetFileDescriptorOpenAssetFileDescriptor.close();
                }
                return true;
            } catch (Throwable th) {
                if (assetFileDescriptorOpenAssetFileDescriptor != null) {
                    try {
                        assetFileDescriptorOpenAssetFileDescriptor.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (IOException | NullPointerException | SecurityException e) {
            Log.w(TAG, "Couldn't open " + uri.toSafeString(), e);
            return false;
        }
    }

    public void init(Context context, Uri uri, Map<String, String> map, List<HttpCookie> list) throws Throwable {
        CookieHandler cookieHandler;
        if (context == null) {
            throw new NullPointerException("context param can not be null.");
        }
        if (uri == null) {
            throw new NullPointerException("uri param can not be null.");
        }
        if (list != null && (cookieHandler = CookieHandler.getDefault()) != null && !(cookieHandler instanceof CookieManager)) {
            throw new IllegalArgumentException("The cookie handler has to be of CookieManager type when cookies are provided");
        }
        ContentResolver contentResolver = context.getContentResolver();
        String scheme = uri.getScheme();
        String authorityWithoutUserId = ContentProvider.getAuthorityWithoutUserId(uri.getAuthority());
        if ("file".equals(scheme)) {
            init(uri.getPath());
            return;
        }
        if ("content".equals(scheme) && "settings".equals(authorityWithoutUserId)) {
            int defaultType = RingtoneManager.getDefaultType(uri);
            Uri cacheForType = RingtoneManager.getCacheForType(defaultType, context.getUserId());
            Uri actualDefaultRingtoneUri = RingtoneManager.getActualDefaultRingtoneUri(context, defaultType);
            if (attemptInit(contentResolver, cacheForType) || attemptInit(contentResolver, actualDefaultRingtoneUri)) {
                return;
            }
            init(uri.toString(), map, list, getCacheDir(context));
            return;
        }
        if (attemptInit(contentResolver, uri)) {
            return;
        }
        init(uri.toString(), map, list, getCacheDir(context));
    }

    private String getCacheDir(Context context) throws IOException {
        if (context == null) {
            throw new NullPointerException("context param can not be null.");
        }
        File cacheDir = context.getCacheDir();
        if (cacheDir == null) {
            Log.i(TAG, "cache directory doesn't exist");
            return null;
        }
        if (!cacheDir.canWrite()) {
            Log.i(TAG, "no permission to write cache directory" + cacheDir.getCanonicalPath());
            return null;
        }
        return cacheDir.getCanonicalPath() + "/";
    }

    public void init(Context context, Uri uri, Map<String, String> map) throws Throwable {
        init(context, uri, map, (List<HttpCookie>) null);
    }

    public void init(Context context, Uri uri) throws Throwable {
        init(context, uri, (Map<String, String>) null, (List<HttpCookie>) null);
    }

    public void init(String str) throws Throwable {
        init(str, (Map<String, String>) null, (List<HttpCookie>) null, (String) null);
    }

    private void init(String str, Map<String, String> map, List<HttpCookie> list, String str2) throws Throwable {
        String[] strArr;
        String[] strArr2;
        SemMediaPlayer semMediaPlayer;
        String str3;
        List<HttpCookie> list2;
        String str4;
        if (map != null) {
            String[] strArr3 = new String[map.size()];
            String[] strArr4 = new String[map.size()];
            int i = 0;
            for (Map.Entry<String, String> entry : map.entrySet()) {
                strArr3[i] = entry.getKey();
                strArr4[i] = entry.getValue();
                i++;
            }
            strArr = strArr3;
            strArr2 = strArr4;
            str3 = str;
            list2 = list;
            str4 = str2;
            semMediaPlayer = this;
        } else {
            strArr = null;
            strArr2 = null;
            semMediaPlayer = this;
            str3 = str;
            list2 = list;
            str4 = str2;
        }
        semMediaPlayer.init(str3, strArr, strArr2, list2, str4);
    }

    private void init(String str, String[] strArr, String[] strArr2, List<HttpCookie> list, String str2) throws Throwable {
        Throwable th;
        Uri uri = Uri.parse(str);
        String scheme = uri.getScheme();
        if ("file".equals(scheme)) {
            str = uri.getPath();
        } else {
            if ("content".equals(scheme)) {
                throw new IOException("init failed with content scheme");
            }
            if (scheme != null) {
                _init(createHttpServiceBinderIfNecessary(str, list), str, strArr, strArr2, str2);
                return;
            }
        }
        File file = new File(str);
        if (file.exists()) {
            FileInputStream fileInputStream = null;
            try {
                FileInputStream fileInputStream2 = new FileInputStream(file);
                try {
                    init(fileInputStream2.getFD());
                    fileInputStream2.close();
                } catch (Throwable th2) {
                    th = th2;
                    fileInputStream = fileInputStream2;
                    if (fileInputStream != null) {
                        fileInputStream.close();
                        throw th;
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
            }
        } else {
            throw new IOException("init failed with file scheme");
        }
    }

    private IBinder createHttpServiceBinderIfNecessary(String str, List<HttpCookie> list) {
        if (str.startsWith("http://") || str.startsWith("https://")) {
            return new MediaHTTPService(list).asBinder();
        }
        if (!str.startsWith("widevine://")) {
            return null;
        }
        Log.d(TAG, "Widevine classic is no longer supported");
        return null;
    }

    public void setDisplay(SurfaceHolder surfaceHolder) throws IllegalStateException, IllegalArgumentException {
        this.mSurfaceHolder = surfaceHolder;
        _setVideoSurface(surfaceHolder != null ? surfaceHolder.getSurface() : null);
        updateSurfaceScreenOn();
    }

    public void setSurface(Surface surface) throws IllegalStateException, IllegalArgumentException {
        if (this.mScreenOnWhilePlaying && surface != null) {
            Log.w(TAG, "setScreenOnWhilePlaying(true) is ineffective for Surface");
        }
        this.mSurfaceHolder = null;
        _setVideoSurface(surface);
        updateSurfaceScreenOn();
    }

    public void setVideoScalingMode(int i) throws IllegalStateException, IllegalArgumentException {
        if (!isVideoScalingModeSupported(i)) {
            throw new IllegalArgumentException("Scaling mode " + i + " is not supported");
        }
        _setVideoScalingMode(i);
    }

    public void start() throws IllegalStateException {
        stayAwake(true);
        try {
            _start();
        } catch (IllegalStateException e) {
            stayAwake(false);
            throw e;
        }
    }

    public void pause() throws IllegalStateException {
        stayAwake(false);
        _pause();
    }

    public void reset() throws IllegalStateException {
        this.mSelectedSubtitleTrackIndex = -1;
        synchronized (this.mOpenSubtitleSources) {
            Iterator<InputStream> it = this.mOpenSubtitleSources.iterator();
            while (it.hasNext()) {
                try {
                    it.next().close();
                } catch (IOException unused) {
                }
            }
            this.mOpenSubtitleSources.clear();
        }
        SubtitleController subtitleController = this.mSubtitleController;
        if (subtitleController != null) {
            subtitleController.reset();
        }
        synchronized (this.mTimeProviderLock) {
            TimeProvider timeProvider = this.mTimeProvider;
            if (timeProvider != null) {
                timeProvider.close();
                this.mTimeProvider = null;
            }
        }
        stayAwake(false);
        _reset();
        EventHandler eventHandler = this.mEventHandler;
        if (eventHandler != null) {
            eventHandler.removeCallbacksAndMessages(null);
        }
        synchronized (this.mIndexTrackPairs) {
            this.mIndexTrackPairs.clear();
            this.mInbandTrackIndices.clear();
        }
    }

    public void release() {
        stayAwake(false);
        this.mOnInitCompleteListener = null;
        this.mOnPlaybackCompleteListener = null;
        this.mOnSeekCompleteListener = null;
        this.mOnErrorListener = null;
        this.mOnInfoListener = null;
        this.mOnVideoSizeChangedListener = null;
        this.mOnTimedTextListener = null;
        synchronized (this.mTimeProviderLock) {
            TimeProvider timeProvider = this.mTimeProvider;
            if (timeProvider != null) {
                timeProvider.close();
                this.mTimeProvider = null;
            }
        }
        synchronized (this) {
            this.mSubtitleDataListenerDisabled = false;
            this.mExtSubtitleDataListener = null;
            this.mExtSubtitleDataHandler = null;
        }
        _release();
    }

    public void seekTo(int i) throws IllegalStateException {
        seekTo(i, 4);
    }

    public void seekTo(int i, int i2) throws IllegalStateException {
        _seekTo(i, i2);
    }

    public void setVolume(float f, float f2) throws IllegalStateException {
        if (f < 0.0f || f2 < 0.0f) {
            throw new IllegalArgumentException("leftVolume(" + f + ") or rightVolume(" + f2 + ") is smaller than 0.0");
        }
        if (f > 1.0f || f2 > 1.0f) {
            throw new IllegalArgumentException("leftVolume(" + f + ") or rightVolume(" + f2 + ") is greater than 1.0");
        }
        playerSetVolume(f, f2);
    }

    public void playerSetVolume(float f, float f2) throws IllegalStateException {
        _setVolume(f, f2);
    }

    public Bitmap getCurrentFrame() throws IllegalStateException {
        return _getCurrentFrame(-1, -1);
    }

    public Bitmap getCurrentFrame(int i, int i2) throws IllegalStateException {
        return _getCurrentFrame(i, i2);
    }

    public Bitmap getCurrentFrame(int i) throws IllegalStateException {
        return _getCurrentFrame(-1, -1, i);
    }

    public Bitmap getCurrentFrame(int i, int i2, int i3) throws IllegalStateException {
        return _getCurrentFrame(i, i2, i3);
    }

    public void invoke(Parcel parcel, Parcel parcel2) throws IllegalStateException {
        int iNative_invoke = native_invoke(parcel, parcel2);
        parcel2.setDataPosition(0);
        if (iNative_invoke == 0) {
            return;
        }
        throw new RuntimeException("failure code: " + iNative_invoke);
    }

    public void setWakeMode(Context context, int i) {
        boolean z = true;
        if (SystemProperties.getBoolean("audio.offload.ignore_setawake", false)) {
            Log.w(TAG, "IGNORING setWakeMode " + i);
            return;
        }
        PowerManager.WakeLock wakeLock = this.mWakeLock;
        if (wakeLock != null) {
            if (wakeLock.isHeld()) {
                this.mWakeLock.release();
            } else {
                z = false;
            }
            this.mWakeLock = null;
        } else {
            z = false;
        }
        PowerManager.WakeLock wakeLockNewWakeLock = ((PowerManager) context.getSystemService("power")).newWakeLock(i | 536870912, SemMediaPlayer.class.getName());
        this.mWakeLock = wakeLockNewWakeLock;
        wakeLockNewWakeLock.setReferenceCounted(false);
        if (z) {
            this.mWakeLock.acquire();
        }
    }

    public void setScreenOnWhilePlaying(boolean z) {
        if (this.mScreenOnWhilePlaying != z) {
            if (z && this.mSurfaceHolder == null) {
                Log.w(TAG, "setScreenOnWhilePlaying(true) is ineffective without a SurfaceHolder");
            }
            this.mScreenOnWhilePlaying = z;
            updateSurfaceScreenOn();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stayAwake(boolean z) {
        PowerManager.WakeLock wakeLock = this.mWakeLock;
        if (wakeLock != null) {
            if (z && !wakeLock.isHeld()) {
                this.mWakeLock.acquire();
            } else if (!z && this.mWakeLock.isHeld()) {
                this.mWakeLock.release();
            }
        }
        this.mStayAwake = z;
        updateSurfaceScreenOn();
    }

    private void updateSurfaceScreenOn() {
        SurfaceHolder surfaceHolder = this.mSurfaceHolder;
        if (surfaceHolder != null) {
            surfaceHolder.setKeepScreenOn(this.mScreenOnWhilePlaying && this.mStayAwake);
        }
    }

    public void notifyAt(long j) {
        _notifyAt(j);
    }

    public boolean setParameter(int i, String str) {
        Parcel parcelObtain = Parcel.obtain();
        parcelObtain.writeString(str);
        boolean parameter = setParameter(i, parcelObtain);
        parcelObtain.recycle();
        return parameter;
    }

    public boolean setParameter(int i, int i2) {
        Parcel parcelObtain = Parcel.obtain();
        parcelObtain.writeInt(i2);
        boolean parameter = setParameter(i, parcelObtain);
        parcelObtain.recycle();
        return parameter;
    }

    protected void finalize() {
        native_finalize();
    }

    public PlaybackParams easyPlaybackParams(float f, int i) {
        PlaybackParams playbackParams = new PlaybackParams();
        playbackParams.allowDefaults();
        if (i == 0) {
            playbackParams.setSpeed(f).setPitch(1.0f);
            return playbackParams;
        }
        if (i == 1) {
            playbackParams.setSpeed(f).setPitch(1.0f).setAudioFallbackMode(2);
            return playbackParams;
        }
        if (i == 2) {
            playbackParams.setSpeed(f).setPitch(f);
            return playbackParams;
        }
        throw new IllegalArgumentException("Audio playback mode " + i + " is not supported");
    }

    public static class TrackInfo implements Parcelable {
        static final Parcelable.Creator<TrackInfo> CREATOR = new Parcelable.Creator<TrackInfo>() { // from class: com.samsung.android.media.SemMediaPlayer.TrackInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public TrackInfo createFromParcel(Parcel parcel) {
                return new TrackInfo(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public TrackInfo[] newArray(int i) {
                return new TrackInfo[i];
            }
        };
        public static final int MEDIA_TRACK_TYPE_AUDIO = 2;
        public static final int MEDIA_TRACK_TYPE_SUBTITLE = 4;
        public static final int MEDIA_TRACK_TYPE_SUBTITLE_OUTBAND = 6;
        public static final int MEDIA_TRACK_TYPE_TIMEDTEXT = 3;
        public static final int MEDIA_TRACK_TYPE_TIMEDTEXT_OUTBAND = 5;
        public static final int MEDIA_TRACK_TYPE_UNKNOWN = 0;
        public static final int MEDIA_TRACK_TYPE_VIDEO = 1;
        private static final String TAG = "TrackInfo";
        private int mChannel;
        final MediaFormat mFormat;
        private int mFrameRate;
        private String mLanguage;
        private String mMime;
        private int mRotationDegrees;
        private int mSampleRate;
        String mTrackName;
        final int mTrackType;
        private int mVideoHeight;
        private int mVideoWidth;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        TrackInfo(Parcel parcel) {
            this.mTrackName = "";
            int i = parcel.readInt();
            this.mTrackType = i;
            this.mMime = parcel.readString();
            String string = parcel.readString();
            this.mLanguage = string;
            this.mRotationDegrees = -1;
            this.mVideoWidth = -1;
            this.mVideoHeight = -1;
            this.mFrameRate = -1;
            this.mSampleRate = -1;
            this.mChannel = -1;
            MediaFormat mediaFormatCreateSubtitleFormat = MediaFormat.createSubtitleFormat(this.mMime, string);
            this.mFormat = mediaFormatCreateSubtitleFormat;
            if (i == 1) {
                this.mRotationDegrees = parcel.readInt();
                this.mVideoWidth = parcel.readInt();
                this.mVideoHeight = parcel.readInt();
                this.mFrameRate = parcel.readInt();
                Log.i(TAG, "videotype mime : " + this.mMime + ", language : " + this.mLanguage + ", rotation : " + this.mRotationDegrees + ", width : " + this.mVideoWidth + ", height : " + this.mVideoHeight + ", fps : " + this.mFrameRate);
            } else if (i == 2) {
                this.mSampleRate = parcel.readInt();
                this.mChannel = parcel.readInt();
                Log.i(TAG, "audiotype mime : " + this.mMime + ", language : " + this.mLanguage + ", samplingrate : " + this.mSampleRate + ", channel : " + this.mChannel);
            } else if (i == 4 || i == 6) {
                mediaFormatCreateSubtitleFormat.setInteger(MediaFormat.KEY_IS_AUTOSELECT, parcel.readInt());
                mediaFormatCreateSubtitleFormat.setInteger(MediaFormat.KEY_IS_DEFAULT, parcel.readInt());
                mediaFormatCreateSubtitleFormat.setInteger(MediaFormat.KEY_IS_FORCED_SUBTITLE, parcel.readInt());
            }
            this.mTrackName = parcel.readString();
        }

        TrackInfo(int i, MediaFormat mediaFormat) {
            this.mTrackName = "";
            this.mTrackType = i;
            this.mFormat = mediaFormat;
        }

        public MediaFormat getFormat() {
            int i = this.mTrackType;
            if (i == 4 || i == 6) {
                return this.mFormat;
            }
            return null;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mTrackType);
            parcel.writeString(this.mMime);
            parcel.writeString(this.mLanguage);
            int i2 = this.mTrackType;
            if (i2 == 1) {
                parcel.writeInt(this.mRotationDegrees);
                parcel.writeInt(this.mVideoWidth);
                parcel.writeInt(this.mVideoHeight);
                parcel.writeInt(this.mFrameRate);
            } else if (i2 == 2) {
                parcel.writeInt(this.mSampleRate);
                parcel.writeInt(this.mChannel);
            } else if (i2 == 4 || i2 == 6) {
                parcel.writeInt(this.mFormat.getInteger(MediaFormat.KEY_IS_AUTOSELECT));
                parcel.writeInt(this.mFormat.getInteger(MediaFormat.KEY_IS_DEFAULT));
                parcel.writeInt(this.mFormat.getInteger(MediaFormat.KEY_IS_FORCED_SUBTITLE));
            }
            parcel.writeString(getName());
        }

        public int getTrackType() {
            return this.mTrackType;
        }

        public String getMimeType() {
            return this.mMime;
        }

        public String getLanguage() {
            String str = this.mLanguage;
            return str == null ? "und" : str;
        }

        public int getVideoRotation() {
            return this.mRotationDegrees;
        }

        public int getVideoWidth() {
            return this.mVideoWidth;
        }

        public int getVideoHeight() {
            return this.mVideoHeight;
        }

        public int getFrameRate() {
            return this.mFrameRate;
        }

        public int getSampleRate() {
            return this.mSampleRate;
        }

        public int getChannel() {
            return this.mChannel;
        }

        public String getName() {
            return this.mTrackName;
        }
    }

    public TrackInfo[] getTrackInfo() throws IllegalStateException {
        TrackInfo[] trackInfoArr;
        TrackInfo[] inbandTrackInfo = getInbandTrackInfo();
        synchronized (this.mIndexTrackPairs) {
            int size = this.mIndexTrackPairs.size();
            trackInfoArr = new TrackInfo[size];
            for (int i = 0; i < size; i++) {
                Pair<Integer, SubtitleTrack> pair = this.mIndexTrackPairs.get(i);
                if (pair.first != null) {
                    trackInfoArr[i] = inbandTrackInfo[pair.first.intValue()];
                } else {
                    trackInfoArr[i] = new TrackInfo(6, pair.second.getFormat());
                }
            }
        }
        return trackInfoArr;
    }

    private TrackInfo[] getInbandTrackInfo() throws IllegalStateException {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInt(1);
            invoke(parcelObtain, parcelObtain2);
            return (TrackInfo[]) parcelObtain2.createTypedArray(TrackInfo.CREATOR);
        } finally {
            parcelObtain.recycle();
            parcelObtain2.recycle();
        }
    }

    public int getSelectedTrack(int i) throws IllegalStateException {
        SubtitleTrack selectedTrack;
        SubtitleController subtitleController = this.mSubtitleController;
        if (subtitleController != null && i == 6 && (selectedTrack = subtitleController.getSelectedTrack()) != null) {
            synchronized (this.mIndexTrackPairs) {
                for (int i2 = 0; i2 < this.mIndexTrackPairs.size(); i2++) {
                    if (this.mIndexTrackPairs.get(i2).second == selectedTrack) {
                        return i2;
                    }
                }
            }
        }
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInt(7);
            parcelObtain.writeInt(i);
            invoke(parcelObtain, parcelObtain2);
            int i3 = parcelObtain2.readInt();
            synchronized (this.mIndexTrackPairs) {
                for (int i4 = 0; i4 < this.mIndexTrackPairs.size(); i4++) {
                    Pair<Integer, SubtitleTrack> pair = this.mIndexTrackPairs.get(i4);
                    if (pair.first != null && pair.first.intValue() == i3) {
                        return i4;
                    }
                }
                parcelObtain.recycle();
                parcelObtain2.recycle();
                return -1;
            }
        } finally {
            parcelObtain.recycle();
            parcelObtain2.recycle();
        }
    }

    public void selectTrack(int i) throws IllegalStateException {
        selectOrDeselectTrack(i, true);
    }

    public void deselectTrack(int i) throws IllegalStateException {
        selectOrDeselectTrack(i, false);
    }

    private void selectOrDeselectTrack(int i, boolean z) throws IllegalStateException {
        Pair<Integer, SubtitleTrack> pair;
        populateInbandTracks();
        synchronized (this.mIndexTrackPairs) {
            try {
                pair = this.mIndexTrackPairs.get(i);
            } catch (ArrayIndexOutOfBoundsException unused) {
            }
        }
        SubtitleTrack subtitleTrack = pair.second;
        if (subtitleTrack == null) {
            selectOrDeselectInbandTrack(pair.first.intValue(), z);
            return;
        }
        SubtitleController subtitleController = this.mSubtitleController;
        if (subtitleController != null) {
            if (!z) {
                if (subtitleController.getSelectedTrack() == subtitleTrack) {
                    this.mSubtitleController.selectTrack(null);
                    return;
                } else {
                    Log.w(TAG, "trying to deselect track that was not selected");
                    return;
                }
            }
            subtitleController.selectTrack(subtitleTrack);
        }
    }

    private void selectOrDeselectInbandTrack(int i, boolean z) throws IllegalStateException {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInt(z ? 4 : 5);
            parcelObtain.writeInt(i);
            invoke(parcelObtain, parcelObtain2);
        } finally {
            parcelObtain.recycle();
            parcelObtain2.recycle();
        }
    }

    public void setOnInitCompleteListener(OnInitCompleteListener onInitCompleteListener) {
        this.mOnInitCompleteListener = onInitCompleteListener;
    }

    public void setOnPlaybackCompleteListener(OnPlaybackCompleteListener onPlaybackCompleteListener) {
        this.mOnPlaybackCompleteListener = onPlaybackCompleteListener;
    }

    public void setOnSeekCompleteListener(OnSeekCompleteListener onSeekCompleteListener) {
        this.mOnSeekCompleteListener = onSeekCompleteListener;
    }

    public void setOnErrorListener(OnErrorListener onErrorListener) {
        this.mOnErrorListener = onErrorListener;
    }

    public void setOnInfoListener(OnInfoListener onInfoListener) {
        this.mOnInfoListener = onInfoListener;
    }

    public void setOnVideoSizeChangedListener(OnVideoSizeChangedListener onVideoSizeChangedListener) {
        this.mOnVideoSizeChangedListener = onVideoSizeChangedListener;
    }

    public void setOnBufferingUpdateListener(OnBufferingUpdateListener onBufferingUpdateListener) {
        this.mOnBufferingUpdateListener = onBufferingUpdateListener;
    }

    public void setOnTimedTextListener(OnTimedTextListener onTimedTextListener) {
        this.mOnTimedTextListener = onTimedTextListener;
    }

    public void setOnSubtitleDataListener(OnSubtitleDataListener onSubtitleDataListener, Handler handler) {
        if (onSubtitleDataListener == null) {
            throw new IllegalArgumentException("Illegal null listener");
        }
        if (handler == null) {
            throw new IllegalArgumentException("Illegal null handler");
        }
        setOnSubtitleDataListenerInt(onSubtitleDataListener, handler);
    }

    public void setOnSubtitleDataListener(OnSubtitleDataListener onSubtitleDataListener) {
        if (onSubtitleDataListener == null) {
            throw new IllegalArgumentException("Illegal null listener");
        }
        setOnSubtitleDataListenerInt(onSubtitleDataListener, null);
    }

    public void clearOnSubtitleDataListener() {
        setOnSubtitleDataListenerInt(null, null);
    }

    private void setOnSubtitleDataListenerInt(OnSubtitleDataListener onSubtitleDataListener, Handler handler) {
        synchronized (this) {
            this.mExtSubtitleDataListener = onSubtitleDataListener;
            this.mExtSubtitleDataHandler = handler;
        }
    }

    public MediaTimeProvider getMediaTimeProvider() {
        TimeProvider timeProvider;
        synchronized (this.mTimeProviderLock) {
            if (this.mTimeProvider == null) {
                this.mTimeProvider = new TimeProvider(this);
            }
            timeProvider = this.mTimeProvider;
        }
        return timeProvider;
    }

    static class TimeProvider implements OnSeekCompleteListener, MediaTimeProvider {
        private static final long MAX_EARLY_CALLBACK_US = 1000;
        private static final long MAX_NS_WITHOUT_POSITION_CHECK = 5000000000L;
        private static final int NOTIFY = 1;
        private static final int NOTIFY_DATA = 2;
        private static final int NOTIFY_SEEK = 3;
        private static final int NOTIFY_STOP = 2;
        private static final int NOTIFY_TIME = 0;
        private static final int NOTIFY_TRACK_DATA = 4;
        private static final String TAG = "MTP";
        private static final long TIME_ADJUSTMENT_RATE = 2;
        private boolean mBuffering;
        private Handler mEventHandler;
        private HandlerThread mHandlerThread;
        private long mLastReportedTime;
        private long mLastTimeUs;
        private MediaTimeProvider.OnMediaTimeListener[] mListeners;
        private SemMediaPlayer mPlayer;
        private boolean mRefresh;
        private long[] mTimes;
        private boolean mPaused = true;
        private boolean mPausing = false;
        private boolean mSeeking = false;
        public boolean DEBUG = false;

        public TimeProvider(SemMediaPlayer semMediaPlayer) {
            this.mLastTimeUs = 0L;
            this.mRefresh = false;
            this.mPlayer = semMediaPlayer;
            try {
                getCurrentTimeUs(true, false);
            } catch (IllegalStateException unused) {
                this.mRefresh = true;
            }
            Looper looperMyLooper = Looper.myLooper();
            if (looperMyLooper == null && (looperMyLooper = Looper.getMainLooper()) == null) {
                HandlerThread handlerThread = new HandlerThread("SemMediaPlayerMTPEventThread", -2);
                this.mHandlerThread = handlerThread;
                handlerThread.start();
                looperMyLooper = this.mHandlerThread.getLooper();
            }
            this.mEventHandler = new EventHandler(looperMyLooper);
            this.mListeners = new MediaTimeProvider.OnMediaTimeListener[0];
            this.mTimes = new long[0];
            this.mLastTimeUs = 0L;
        }

        private void scheduleNotification(int i, long j) {
            if (this.mSeeking && i == 0) {
                return;
            }
            if (this.DEBUG) {
                Log.v(TAG, "scheduleNotification " + i + " in " + j);
            }
            this.mEventHandler.removeMessages(1);
            this.mEventHandler.sendMessageDelayed(this.mEventHandler.obtainMessage(1, i, 0), (int) (j / 1000));
        }

        public void close() {
            this.mEventHandler.removeMessages(1);
            this.mEventHandler.removeMessages(2);
            HandlerThread handlerThread = this.mHandlerThread;
            if (handlerThread != null) {
                handlerThread.quitSafely();
                this.mHandlerThread = null;
            }
        }

        protected void finalize() {
            HandlerThread handlerThread = this.mHandlerThread;
            if (handlerThread != null) {
                handlerThread.quitSafely();
            }
        }

        public void onNotifyTime() {
            synchronized (this) {
                if (this.DEBUG) {
                    Log.d(TAG, "onNotifyTime: ");
                }
                scheduleNotification(0, 0L);
            }
        }

        public void onPaused(boolean z) {
            synchronized (this) {
                if (this.DEBUG) {
                    Log.d(TAG, "onPaused: " + z);
                }
                this.mPausing = z;
                this.mSeeking = false;
                scheduleNotification(0, 0L);
            }
        }

        public void onBuffering(boolean z) {
            synchronized (this) {
                if (this.DEBUG) {
                    Log.d(TAG, "onBuffering: " + z);
                }
                this.mBuffering = z;
                scheduleNotification(0, 0L);
            }
        }

        @Override // com.samsung.android.media.SemMediaPlayer.OnSeekCompleteListener
        public void onSeekComplete(SemMediaPlayer semMediaPlayer) {
            synchronized (this) {
                this.mSeeking = true;
                scheduleNotification(3, 0L);
            }
        }

        public void onNewPlayer() {
            if (this.mRefresh) {
                synchronized (this) {
                    this.mSeeking = true;
                    this.mBuffering = false;
                    scheduleNotification(3, 0L);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public synchronized void notifySeek() {
            this.mSeeking = false;
            try {
                long currentTimeUs = getCurrentTimeUs(true, false);
                if (this.DEBUG) {
                    Log.d(TAG, "onSeekComplete at " + currentTimeUs);
                }
                for (MediaTimeProvider.OnMediaTimeListener onMediaTimeListener : this.mListeners) {
                    if (onMediaTimeListener == null) {
                        break;
                    }
                    onMediaTimeListener.onSeek(currentTimeUs);
                }
            } catch (IllegalStateException unused) {
                if (this.DEBUG) {
                    Log.d(TAG, "onSeekComplete but no player");
                }
                this.mPausing = true;
                notifyTimedEvent(false);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public synchronized void notifyTrackData(Pair<SubtitleTrack, byte[]> pair) {
            pair.first.onData(pair.second, true, -1L);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public synchronized void notifyStop() {
            for (MediaTimeProvider.OnMediaTimeListener onMediaTimeListener : this.mListeners) {
                if (onMediaTimeListener == null) {
                    break;
                }
                onMediaTimeListener.onStop();
            }
        }

        private int registerListener(MediaTimeProvider.OnMediaTimeListener onMediaTimeListener) {
            MediaTimeProvider.OnMediaTimeListener[] onMediaTimeListenerArr;
            MediaTimeProvider.OnMediaTimeListener onMediaTimeListener2;
            int i = 0;
            while (true) {
                onMediaTimeListenerArr = this.mListeners;
                if (i >= onMediaTimeListenerArr.length || (onMediaTimeListener2 = onMediaTimeListenerArr[i]) == onMediaTimeListener || onMediaTimeListener2 == null) {
                    break;
                }
                i++;
            }
            if (i >= onMediaTimeListenerArr.length) {
                int i2 = i + 1;
                MediaTimeProvider.OnMediaTimeListener[] onMediaTimeListenerArr2 = new MediaTimeProvider.OnMediaTimeListener[i2];
                long[] jArr = new long[i2];
                System.arraycopy(onMediaTimeListenerArr, 0, onMediaTimeListenerArr2, 0, onMediaTimeListenerArr.length);
                long[] jArr2 = this.mTimes;
                System.arraycopy(jArr2, 0, jArr, 0, jArr2.length);
                this.mListeners = onMediaTimeListenerArr2;
                this.mTimes = jArr;
            }
            MediaTimeProvider.OnMediaTimeListener[] onMediaTimeListenerArr3 = this.mListeners;
            if (onMediaTimeListenerArr3[i] == null) {
                onMediaTimeListenerArr3[i] = onMediaTimeListener;
                this.mTimes[i] = -1;
            }
            return i;
        }

        @Override // android.media.MediaTimeProvider
        public void notifyAt(long j, MediaTimeProvider.OnMediaTimeListener onMediaTimeListener) {
            synchronized (this) {
                if (this.DEBUG) {
                    Log.d(TAG, "notifyAt " + j);
                }
                this.mTimes[registerListener(onMediaTimeListener)] = j;
                scheduleNotification(0, 0L);
            }
        }

        @Override // android.media.MediaTimeProvider
        public void scheduleUpdate(MediaTimeProvider.OnMediaTimeListener onMediaTimeListener) {
            synchronized (this) {
                if (this.DEBUG) {
                    Log.d(TAG, "scheduleUpdate");
                }
                this.mTimes[registerListener(onMediaTimeListener)] = 0;
                scheduleNotification(0, 0L);
            }
        }

        @Override // android.media.MediaTimeProvider
        public void cancelNotifications(MediaTimeProvider.OnMediaTimeListener onMediaTimeListener) {
            synchronized (this) {
                int i = 0;
                while (true) {
                    MediaTimeProvider.OnMediaTimeListener[] onMediaTimeListenerArr = this.mListeners;
                    if (i >= onMediaTimeListenerArr.length) {
                        break;
                    }
                    MediaTimeProvider.OnMediaTimeListener onMediaTimeListener2 = onMediaTimeListenerArr[i];
                    if (onMediaTimeListener2 == onMediaTimeListener) {
                        int i2 = i + 1;
                        System.arraycopy(onMediaTimeListenerArr, i2, onMediaTimeListenerArr, i, (onMediaTimeListenerArr.length - i) - 1);
                        long[] jArr = this.mTimes;
                        System.arraycopy(jArr, i2, jArr, i, (jArr.length - i) - 1);
                        this.mListeners[r5.length - 1] = null;
                        this.mTimes[r5.length - 1] = -1;
                        break;
                    }
                    if (onMediaTimeListener2 == null) {
                        break;
                    } else {
                        i++;
                    }
                }
                scheduleNotification(0, 0L);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public synchronized void notifyTimedEvent(boolean z) {
            long currentTimeUs;
            MediaTimeProvider.OnMediaTimeListener onMediaTimeListener;
            try {
                currentTimeUs = getCurrentTimeUs(z, true);
            } catch (IllegalStateException unused) {
                this.mRefresh = true;
                this.mPausing = true;
                currentTimeUs = getCurrentTimeUs(z, true);
            }
            if (this.mSeeking) {
                return;
            }
            int i = 0;
            if (this.DEBUG) {
                StringBuilder sb = new StringBuilder();
                sb.append("notifyTimedEvent(");
                sb.append(this.mLastTimeUs);
                sb.append(" -> ");
                sb.append(currentTimeUs);
                sb.append(") from {");
                boolean z2 = true;
                for (long j : this.mTimes) {
                    if (j != -1) {
                        if (!z2) {
                            sb.append(", ");
                        }
                        sb.append(j);
                        z2 = false;
                    }
                }
                sb.append("}");
                Log.d(TAG, sb.toString());
            }
            Vector vector = new Vector();
            long j2 = currentTimeUs;
            while (true) {
                long[] jArr = this.mTimes;
                if (i >= jArr.length || (onMediaTimeListener = this.mListeners[i]) == null) {
                    break;
                }
                long j3 = jArr[i];
                if (j3 > -1) {
                    if (j3 <= 1000 + currentTimeUs) {
                        vector.add(onMediaTimeListener);
                        if (this.DEBUG) {
                            Log.d(TAG, Environment.MEDIA_REMOVED);
                        }
                        this.mTimes[i] = -1;
                    } else if (j2 == currentTimeUs || j3 < j2) {
                        j2 = j3;
                    }
                }
                i++;
            }
            if (j2 > currentTimeUs && !this.mPaused) {
                if (this.DEBUG) {
                    Log.d(TAG, "scheduling for " + j2 + " and " + currentTimeUs);
                }
                this.mPlayer.notifyAt(j2);
            } else {
                this.mEventHandler.removeMessages(1);
            }
            Iterator it = vector.iterator();
            while (it.hasNext()) {
                ((MediaTimeProvider.OnMediaTimeListener) it.next()).onTimedEvent(currentTimeUs);
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:31:0x0072 A[Catch: all -> 0x00ad, TryCatch #1 {, blocks: (B:4:0x0003, B:7:0x0009, B:8:0x000b, B:11:0x000f, B:13:0x0023, B:18:0x002b, B:20:0x0031, B:24:0x0043, B:26:0x0059, B:28:0x0061, B:30:0x0069, B:32:0x0076, B:33:0x0078, B:31:0x0072, B:36:0x007b, B:38:0x007f, B:40:0x0083, B:43:0x008f, B:45:0x0095, B:46:0x00a8, B:47:0x00aa, B:42:0x008b, B:49:0x00ac), top: B:55:0x0003, inners: #0 }] */
        @Override // android.media.MediaTimeProvider
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public long getCurrentTimeUs(boolean z, boolean z2) throws IllegalStateException {
            synchronized (this) {
                if (this.mPaused && !z) {
                    return this.mLastReportedTime;
                }
                try {
                    this.mLastTimeUs = this.mPlayer.getCurrentPosition() * 1000;
                    this.mPaused = !this.mPlayer.isPlaying() || this.mBuffering;
                    if (this.DEBUG) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(this.mPaused ? "paused" : "playing");
                        sb.append(" at ");
                        sb.append(this.mLastTimeUs);
                        Log.v(TAG, sb.toString());
                    }
                    if (z2) {
                        long j = this.mLastTimeUs;
                        long j2 = this.mLastReportedTime;
                        if (j >= j2) {
                            this.mLastReportedTime = this.mLastTimeUs;
                        } else if (j2 - j > 1000000) {
                            this.mSeeking = true;
                            scheduleNotification(3, 0L);
                        }
                    }
                    return this.mLastReportedTime;
                } catch (IllegalStateException e) {
                    if (this.mPausing) {
                        this.mPausing = false;
                        if (!z2 || this.mLastReportedTime < this.mLastTimeUs) {
                            this.mLastReportedTime = this.mLastTimeUs;
                        }
                        this.mPaused = true;
                        if (this.DEBUG) {
                            Log.d(TAG, "illegal state, but pausing: estimating at " + this.mLastReportedTime);
                        }
                        return this.mLastReportedTime;
                    }
                    throw e;
                }
            }
        }

        private class EventHandler extends Handler {
            public EventHandler(Looper looper) {
                super(looper);
            }

            @Override // android.os.Handler
            public void handleMessage(Message message) {
                if (message.what == 1) {
                    int i = message.arg1;
                    if (i == 0) {
                        TimeProvider.this.notifyTimedEvent(true);
                        return;
                    } else if (i == 2) {
                        TimeProvider.this.notifyStop();
                        return;
                    } else {
                        if (i != 3) {
                            return;
                        }
                        TimeProvider.this.notifySeek();
                        return;
                    }
                }
                if (message.what == 2 && message.arg1 == 4 && (message.obj instanceof Pair) && (((Pair) message.obj).first instanceof SubtitleTrack) && (((Pair) message.obj).second instanceof byte[])) {
                    TimeProvider.this.notifyTrackData((Pair) message.obj);
                }
            }
        }
    }

    private class EventHandler extends Handler {
        private SemMediaPlayer mSemMediaPlayer;

        public EventHandler(SemMediaPlayer semMediaPlayer, Looper looper) {
            super(looper);
            this.mSemMediaPlayer = semMediaPlayer;
        }

        /* JADX WARN: Removed duplicated region for block: B:138:? A[RETURN, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:82:0x012f  */
        @Override // android.os.Handler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void handleMessage(Message message) throws IllegalStateException {
            OnInfoListener onInfoListener;
            OnPlaybackCompleteListener onPlaybackCompleteListener;
            if (this.mSemMediaPlayer.mNativeContext == 0) {
                Log.w(SemMediaPlayer.TAG, "semmediaplayer went away with unhandled events");
                return;
            }
            int i = message.what;
            TrackInfo[] trackInfoArr = null;
            boolean z = true;
            if (i == 1) {
                if (message.obj instanceof Parcel) {
                    Parcel parcel = (Parcel) message.obj;
                    trackInfoArr = (TrackInfo[]) parcel.createTypedArray(TrackInfo.CREATOR);
                    parcel.recycle();
                }
                SemMediaPlayer.this.populateInbandTracks(trackInfoArr);
                OnInitCompleteListener onInitCompleteListener = SemMediaPlayer.this.mOnInitCompleteListener;
                if (onInitCompleteListener != null) {
                    onInitCompleteListener.onInitComplete(this.mSemMediaPlayer, trackInfoArr);
                    return;
                }
                return;
            }
            if (i == 2) {
                OnPlaybackCompleteListener onPlaybackCompleteListener2 = SemMediaPlayer.this.mOnPlaybackCompleteListener;
                if (onPlaybackCompleteListener2 != null) {
                    onPlaybackCompleteListener2.onPlaybackComplete(this.mSemMediaPlayer);
                }
                SemMediaPlayer.this.stayAwake(false);
                return;
            }
            if (i == 3) {
                OnBufferingUpdateListener onBufferingUpdateListener = SemMediaPlayer.this.mOnBufferingUpdateListener;
                if (onBufferingUpdateListener != null) {
                    onBufferingUpdateListener.onBufferingUpdate(this.mSemMediaPlayer, message.arg1);
                    return;
                }
                return;
            }
            if (i == 4) {
                OnSeekCompleteListener onSeekCompleteListener = SemMediaPlayer.this.mOnSeekCompleteListener;
                if (onSeekCompleteListener != null) {
                    onSeekCompleteListener.onSeekComplete(this.mSemMediaPlayer);
                }
                try {
                    TimeProvider timeProvider = SemMediaPlayer.this.mTimeProvider;
                    if (timeProvider != null) {
                        timeProvider.onSeekComplete(this.mSemMediaPlayer);
                        return;
                    }
                    return;
                } catch (NullPointerException e) {
                    Log.d(SemMediaPlayer.TAG, "handleMessage MEDIA_SEEK_COMPLETE e : ", e);
                    return;
                }
            }
            if (i == 5) {
                OnVideoSizeChangedListener onVideoSizeChangedListener = SemMediaPlayer.this.mOnVideoSizeChangedListener;
                if (onVideoSizeChangedListener != null) {
                    onVideoSizeChangedListener.onVideoSizeChanged(this.mSemMediaPlayer, message.arg1, message.arg2);
                    return;
                }
                return;
            }
            if (i == 7) {
                try {
                    TimeProvider timeProvider2 = SemMediaPlayer.this.mTimeProvider;
                    if (timeProvider2 != null) {
                        if (message.what != 7) {
                            z = false;
                        }
                        timeProvider2.onPaused(z);
                        return;
                    }
                    return;
                } catch (NullPointerException e2) {
                    Log.d(SemMediaPlayer.TAG, "handleMessage MEDIA_PAUSED e : ", e2);
                    return;
                }
            }
            if (i != 200) {
                if (i != 201) {
                    switch (i) {
                        case 98:
                            TimeProvider timeProvider3 = SemMediaPlayer.this.mTimeProvider;
                            if (timeProvider3 != null) {
                                timeProvider3.onNotifyTime();
                                return;
                            }
                            return;
                        case 99:
                            OnTimedTextListener onTimedTextListener = SemMediaPlayer.this.mOnTimedTextListener;
                            if (onTimedTextListener == null) {
                                return;
                            }
                            if (message.obj == null) {
                                onTimedTextListener.onTimedText(this.mSemMediaPlayer, null);
                                return;
                            } else {
                                if (message.obj instanceof Parcel) {
                                    Parcel parcel2 = (Parcel) message.obj;
                                    TimedText timedText = new TimedText(parcel2);
                                    parcel2.recycle();
                                    onTimedTextListener.onTimedText(this.mSemMediaPlayer, timedText);
                                    return;
                                }
                                return;
                            }
                        case 100:
                            OnErrorListener onErrorListener = SemMediaPlayer.this.mOnErrorListener;
                            if (!(onErrorListener != null ? onErrorListener.onError(this.mSemMediaPlayer, message.arg1, message.arg2) : false) && (onPlaybackCompleteListener = SemMediaPlayer.this.mOnPlaybackCompleteListener) != null) {
                                onPlaybackCompleteListener.onPlaybackComplete(this.mSemMediaPlayer);
                            }
                            SemMediaPlayer.this.stayAwake(false);
                            return;
                        default:
                            Log.e(SemMediaPlayer.TAG, "Unknown message type " + message.what);
                            return;
                    }
                }
                synchronized (this) {
                    if (SemMediaPlayer.this.mSubtitleDataListenerDisabled) {
                        return;
                    }
                    final OnSubtitleDataListener onSubtitleDataListener = SemMediaPlayer.this.mExtSubtitleDataListener;
                    Handler handler = SemMediaPlayer.this.mExtSubtitleDataHandler;
                    if (message.obj instanceof Parcel) {
                        Parcel parcel3 = (Parcel) message.obj;
                        final SubtitleData subtitleData = new SubtitleData(parcel3);
                        parcel3.recycle();
                        SemMediaPlayer.this.mIntSubtitleDataListener.onSubtitleData(this.mSemMediaPlayer, subtitleData);
                        if (onSubtitleDataListener != null) {
                            if (handler == null) {
                                onSubtitleDataListener.onSubtitleData(this.mSemMediaPlayer, subtitleData);
                                return;
                            } else {
                                handler.post(new Runnable() { // from class: com.samsung.android.media.SemMediaPlayer.EventHandler.1
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        onSubtitleDataListener.onSubtitleData(EventHandler.this.mSemMediaPlayer, subtitleData);
                                    }
                                });
                                return;
                            }
                        }
                        return;
                    }
                    return;
                }
            }
            int i2 = message.arg1;
            if (i2 == 802) {
                try {
                    SemMediaPlayer.this.scanInternalSubtitleTracks();
                } catch (RuntimeException unused) {
                    sendMessage(obtainMessage(100, 1, -1010, null));
                }
            } else {
                if (i2 == 803) {
                }
                onInfoListener = SemMediaPlayer.this.mOnInfoListener;
                if (onInfoListener == null) {
                    if (message.arg1 == 10974 && (message.obj instanceof Parcel)) {
                        Parcel parcel4 = (Parcel) message.obj;
                        SemMediaPlayer.this.mSuperSlowInfo = (SuperSlowRegion[]) parcel4.createTypedArray(SuperSlowRegion.CREATOR);
                        parcel4.recycle();
                    }
                    onInfoListener.onInfo(this.mSemMediaPlayer, message.arg1, message.arg2);
                    return;
                }
                return;
            }
            message.arg1 = 802;
            if (SemMediaPlayer.this.mSubtitleController != null) {
                SemMediaPlayer.this.mSubtitleController.selectDefaultTrack();
            }
            onInfoListener = SemMediaPlayer.this.mOnInfoListener;
            if (onInfoListener == null) {
            }
        }
    }

    private static void postEventFromNative(Object obj, int i, int i2, int i3, Object obj2) {
        EventHandler eventHandler;
        SemMediaPlayer semMediaPlayer = (SemMediaPlayer) ((WeakReference) obj).get();
        if (semMediaPlayer == null || (eventHandler = semMediaPlayer.mEventHandler) == null) {
            return;
        }
        semMediaPlayer.mEventHandler.sendMessage(eventHandler.obtainMessage(i, i2, i3, obj2));
    }

    private static class SpeedRegion {
        int audioEnd;
        int speedRate;
        int videoEnd;
        int videoStart;

        private SpeedRegion() {
        }
    }

    public void addRegion(int i, int i2, int i3, int i4) {
        SpeedRegion speedRegion = new SpeedRegion();
        speedRegion.speedRate = i;
        speedRegion.videoStart = i2;
        speedRegion.videoEnd = i3;
        speedRegion.audioEnd = i4;
        this.mSpeedRegions.add(speedRegion);
    }

    public boolean applyRegion(int i, int i2) throws IllegalStateException {
        StringBuilder sb = new StringBuilder();
        for (int i3 = 0; i3 < this.mSpeedRegions.size(); i3++) {
            if (i3 > 0) {
                sb.append("*");
            }
            sb.append(this.mSpeedRegions.get(i3).videoStart);
            sb.append(":");
            sb.append(this.mSpeedRegions.get(i3).videoEnd);
            sb.append(":");
            if (this.mSpeedRegions.get(i3).audioEnd != -1 && i2 != -1) {
                sb.append(this.mSpeedRegions.get(i3).audioEnd);
                sb.append(":");
            } else {
                if (this.mSpeedRegions.get(i3).audioEnd != -1 && i2 == -1) {
                    Log.e(TAG, "Mismatched input of data.");
                    this.mSpeedRegions.clear();
                    return false;
                }
                if (this.mSpeedRegions.get(i3).audioEnd == -1 && i2 != -1) {
                    Log.e(TAG, "Mismatched input of data.");
                    this.mSpeedRegions.clear();
                    return false;
                }
            }
            sb.append(this.mSpeedRegions.get(i3).speedRate);
        }
        if (i2 != -1) {
            sb.append("!");
            sb.append(i2);
        }
        String string = sb.toString();
        Parcel parcelObtain = Parcel.obtain();
        parcelObtain.writeString(string);
        boolean z_updateRegionSEFData = _updateRegionSEFData(i, parcelObtain);
        parcelObtain.recycle();
        this.mSpeedRegions.clear();
        return z_updateRegionSEFData;
    }

    public static class SuperSlowRegion implements Parcelable {
        public static final int CANCELED_REGION = 0;
        static final Parcelable.Creator<SuperSlowRegion> CREATOR = new Parcelable.Creator<SuperSlowRegion>() { // from class: com.samsung.android.media.SemMediaPlayer.SuperSlowRegion.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SuperSlowRegion createFromParcel(Parcel parcel) {
                return new SuperSlowRegion(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SuperSlowRegion[] newArray(int i) {
                return new SuperSlowRegion[i];
            }
        };
        public static final int NORMAL_REGION = 2;
        public static final int TITLE_REGION = 1;
        int endTime;
        int startTime;
        int type;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        SuperSlowRegion(Parcel parcel) {
            this.type = parcel.readInt();
            this.startTime = parcel.readInt();
            this.endTime = parcel.readInt();
        }

        public int getRegionType() {
            return this.type;
        }

        public int getStartTime() {
            return this.startTime;
        }

        public int getEndTime() {
            return this.endTime;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.type);
            parcel.writeInt(this.startTime);
            parcel.writeInt(this.endTime);
        }
    }

    public SuperSlowRegion[] getSuperSlowRegions() {
        return this.mSuperSlowInfo;
    }

    public void setBackgroundMusic(SemBackgroundMusic semBackgroundMusic) throws IllegalStateException {
        if (semBackgroundMusic == null) {
            throw new NullPointerException("SemBackgroundMusic param can not be null.");
        }
        Parcel parcelWriteToParcel = semBackgroundMusic.writeToParcel(null);
        _setBackgroundMusic(parcelWriteToParcel);
        parcelWriteToParcel.recycle();
    }

    public void setVideoFilter(String str, int i) throws IllegalStateException, UnsupportedOperationException {
        if (i < 0 || 100 < i) {
            throw new IllegalArgumentException("filterLevel(" + i + ") is not in acceptable range");
        }
        _setVideoFilter(str, i);
    }

    public static final class DynamicViewingConfiguration {
        private int mEndTimeMs;
        private float mSpeedRate;
        private int mStartTimeMs;

        public DynamicViewingConfiguration(int i, int i2, float f) {
            if (i < 0) {
                throw new IllegalArgumentException("DynamicViewingConfiguration startTimeMs is less than zero");
            }
            if (i2 < 0) {
                throw new IllegalArgumentException("DynamicViewingConfiguration endTimeMs is less than zero");
            }
            if (f <= 0.0f) {
                throw new IllegalArgumentException("DynamicViewingConfiguration speedRate is less or equal than zero");
            }
            this.mStartTimeMs = i;
            this.mEndTimeMs = i2;
            this.mSpeedRate = f;
        }

        public int getStartTime() {
            return this.mStartTimeMs;
        }

        public int getEndTime() {
            return this.mEndTimeMs;
        }

        public float getSpeedRate() {
            return this.mSpeedRate;
        }
    }

    public void setDynamicViewingConfigurations(List<DynamicViewingConfiguration> list, boolean z, int i) throws IllegalStateException, IllegalArgumentException {
        if (i < 0 || 100 < i) {
            throw new IllegalArgumentException("repeatCount(" + i + ") is not in acceptable range");
        }
        internalSetDynamicViewingConfigurations(list, z, i);
    }

    public void setDynamicViewingConfigurations(List<DynamicViewingConfiguration> list, boolean z) throws IllegalStateException, IllegalArgumentException {
        internalSetDynamicViewingConfigurations(list, z, 0);
    }

    public void setDynamicViewingConfigurations(List<DynamicViewingConfiguration> list) throws IllegalStateException, IllegalArgumentException {
        internalSetDynamicViewingConfigurations(list, false, 0);
    }

    private void internalSetDynamicViewingConfigurations(List<DynamicViewingConfiguration> list, boolean z, int i) throws IllegalStateException, IllegalArgumentException {
        if (list == null) {
            throw new NullPointerException("dynamicViewingConfigs can not be null.");
        }
        if (list.isEmpty()) {
            throw new IllegalArgumentException("dynamicViewingConfigs is empty.");
        }
        Parcel parcelObtain = Parcel.obtain();
        try {
            parcelObtain.writeInt(list.size());
            for (DynamicViewingConfiguration dynamicViewingConfiguration : list) {
                int startTime = dynamicViewingConfiguration.getStartTime();
                int endTime = dynamicViewingConfiguration.getEndTime();
                if (!z && startTime >= endTime) {
                    throw new IllegalArgumentException("DynamicViewingConfiguration startTimeMs is equal or greater than endTimeMs in not delegated");
                }
                parcelObtain.writeInt(startTime);
                parcelObtain.writeInt(endTime);
                parcelObtain.writeFloat(dynamicViewingConfiguration.getSpeedRate());
            }
            parcelObtain.writeInt(i);
            if (!z) {
                if (!setParameter(KEY_PARAMETER_DYNAMIC_VIEW_CONFIGURATION, parcelObtain)) {
                    throw new IllegalStateException("setDynamicViewingConfigurations is called after init().");
                }
            } else if (!setParameter(KEY_PARAMETER_DYNAMIC_VIEW_DELEGATE_CONFIGURATION, parcelObtain)) {
                throw new IllegalStateException("setDynamicViewingConfigurations delegatePlaybackControl failed");
            }
        } finally {
            parcelObtain.recycle();
        }
    }

    public void setAudioAttributes(AudioAttributes audioAttributes) throws IllegalStateException {
        _setAudioAttributes(audioAttributes);
        this.mAttributes = audioAttributes;
    }

    public boolean setPreferredDevice(AudioDeviceInfo audioDeviceInfo) throws IllegalStateException {
        return _setPreferredDevice(audioDeviceInfo);
    }

    private static boolean availableMimeTypeForExternalSource(String str) {
        return "application/x-subrip".equals(str);
    }

    public static final class AudioEraserConfig {
        private float mMusic;
        private float mOthers;
        private float mVoice;
        private float mWind;

        AudioEraserConfig(float f, float f2, float f3, float f4) {
            this.mVoice = f;
            this.mMusic = f2;
            this.mWind = f3;
            this.mOthers = f4;
        }

        public float getVoiceRate() {
            return this.mVoice;
        }

        public float getMusicRate() {
            return this.mMusic;
        }

        public float getWindRate() {
            return this.mWind;
        }

        public float getOthersRate() {
            return this.mOthers;
        }

        public static final class Builder {
            private float mVoice = 1.0f;
            private float mMusic = 1.0f;
            private float mWind = 1.0f;
            private float mOthers = 1.0f;

            public Builder setVoiceRate(float f) {
                this.mVoice = f;
                return this;
            }

            public Builder setMusicRate(float f) {
                this.mMusic = f;
                return this;
            }

            public Builder setWindRate(float f) {
                this.mWind = f;
                return this;
            }

            public Builder setOthersRate(float f) {
                this.mOthers = f;
                return this;
            }

            public AudioEraserConfig build() {
                return new AudioEraserConfig(this.mVoice, this.mMusic, this.mWind, this.mOthers);
            }
        }
    }

    public void setAudioEraserConfig(AudioEraserConfig audioEraserConfig) throws IllegalStateException, IllegalArgumentException {
        float[] fArr = {audioEraserConfig.getVoiceRate(), audioEraserConfig.getMusicRate(), audioEraserConfig.getWindRate(), audioEraserConfig.getOthersRate()};
        Parcel parcelObtain = Parcel.obtain();
        try {
            parcelObtain.writeInt(4);
            for (int i = 0; i < 4; i++) {
                float f = fArr[i];
                if (f < 0.0f || f > 1.0f) {
                    throw new IllegalArgumentException("Illegal value");
                }
                parcelObtain.writeFloat(f);
            }
            _setAudioEraserConfig(parcelObtain);
        } finally {
            parcelObtain.recycle();
        }
    }

    public void addTimedTextSource(String str, String str2) throws IllegalStateException, IOException, IllegalArgumentException {
        if (!availableMimeTypeForExternalSource(str2)) {
            throw new IllegalArgumentException("Illegal mimeType for timed text source: " + str2);
        }
        if (str == null) {
            throw new IllegalArgumentException("Illegal path");
        }
        File file = new File(str);
        if (file.exists()) {
            FileInputStream fileInputStream = new FileInputStream(file);
            try {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                ParcelFileDescriptor parcelFileDescriptorDup = ParcelFileDescriptor.dup(fileInputStream.getFD());
                try {
                    Log.d(TAG, "send invoke key : INVOKE_ID_ADD_EXTERNAL_SOURCE_FD");
                    parcelObtain.writeInt(3);
                    parcelObtain.writeInt(parcelFileDescriptorDup.detachFd());
                    parcelObtain.writeLong(0L);
                    parcelObtain.writeLong(576460752303423487L);
                    parcelObtain.writeString(str2);
                    invoke(parcelObtain, parcelObtain2);
                    fileInputStream.close();
                    populateInbandTracks();
                    return;
                } finally {
                    parcelObtain.recycle();
                    parcelObtain2.recycle();
                }
            } catch (Throwable th) {
                fileInputStream.close();
                throw th;
            }
        }
        throw new IOException(str);
    }

    public void removeOutbandTimedTextSources() throws IllegalStateException {
        int size;
        TrackInfo[] inbandTrackInfo = getInbandTrackInfo();
        synchronized (this.mIndexTrackPairs) {
            size = this.mIndexTrackPairs.size() - inbandTrackInfo.length;
            Parcel parcelObtain = Parcel.obtain();
            Parcel parcelObtain2 = Parcel.obtain();
            try {
                parcelObtain.writeInt(8);
                invoke(parcelObtain, parcelObtain2);
                parcelObtain.recycle();
                parcelObtain2.recycle();
                this.mIndexTrackPairs.clear();
                this.mInbandTrackIndices.clear();
            } catch (Throwable th) {
                parcelObtain.recycle();
                parcelObtain2.recycle();
                throw th;
            }
        }
        populateInbandTracks();
        synchronized (this.mIndexTrackPairs) {
            SubtitleController subtitleController = this.mSubtitleController;
            if (subtitleController != null) {
                SubtitleTrack[] tracks = subtitleController.getTracks();
                for (int length = tracks.length - size; length < tracks.length && length >= 0; length++) {
                    this.mIndexTrackPairs.add(Pair.create(null, tracks[length]));
                }
            }
        }
    }

    public void setSubtitleControllerAndAnchor(Context context, VideoView videoView) {
        SubtitleController subtitleController = new SubtitleController(context, getMediaTimeProvider(), this);
        subtitleController.registerRenderer(new WebVttRenderer(context));
        subtitleController.registerRenderer(new TtmlRenderer(context));
        subtitleController.registerRenderer(new ClosedCaptionRenderer(context));
        subtitleController.registerRenderer(new Cea708CaptionRenderer(context));
        this.mSubtitleController = subtitleController;
        subtitleController.setAnchor(videoView);
    }

    private synchronized void setSubtitleAnchor() {
        if (this.mSubtitleController == null && ActivityThread.currentApplication() != null) {
            final TimeProvider timeProvider = (TimeProvider) getMediaTimeProvider();
            final HandlerThread handlerThread = new HandlerThread("SetSubtitleAnchorThread");
            handlerThread.start();
            new Handler(handlerThread.getLooper()).post(new Runnable() { // from class: com.samsung.android.media.SemMediaPlayer.1
                @Override // java.lang.Runnable
                public void run() {
                    Application applicationCurrentApplication = ActivityThread.currentApplication();
                    SemMediaPlayer.this.mSubtitleController = new SubtitleController(applicationCurrentApplication, timeProvider, SemMediaPlayer.this);
                    SemMediaPlayer.this.mSubtitleController.setAnchor(new SubtitleController.Anchor() { // from class: com.samsung.android.media.SemMediaPlayer.1.1
                        @Override // android.media.SubtitleController.Anchor
                        public void setSubtitleWidget(SubtitleTrack.RenderingWidget renderingWidget) {
                        }

                        @Override // android.media.SubtitleController.Anchor
                        public Looper getSubtitleLooper() {
                            return timeProvider.mEventHandler.getLooper();
                        }
                    });
                    handlerThread.getLooper().quitSafely();
                }
            });
            try {
                handlerThread.join();
            } catch (InterruptedException unused) {
                Thread.currentThread().interrupt();
                Log.w(TAG, "failed to join SetSubtitleAnchorThread");
            }
        }
    }

    @Override // android.media.SubtitleController.Listener
    public void onSubtitleTrackSelected(SubtitleTrack subtitleTrack) {
        int i = this.mSelectedSubtitleTrackIndex;
        if (i >= 0) {
            try {
                selectOrDeselectInbandTrack(i, false);
            } catch (IllegalStateException unused) {
            }
            this.mSelectedSubtitleTrackIndex = -1;
        }
        synchronized (this) {
            this.mSubtitleDataListenerDisabled = true;
        }
        if (subtitleTrack == null) {
            return;
        }
        synchronized (this.mIndexTrackPairs) {
            Iterator<Pair<Integer, SubtitleTrack>> it = this.mIndexTrackPairs.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Pair<Integer, SubtitleTrack> next = it.next();
                if (next.first != null && next.second == subtitleTrack) {
                    this.mSelectedSubtitleTrackIndex = next.first.intValue();
                    break;
                }
            }
        }
        int i2 = this.mSelectedSubtitleTrackIndex;
        if (i2 >= 0) {
            try {
                selectOrDeselectInbandTrack(i2, true);
            } catch (IllegalStateException unused2) {
            }
            synchronized (this) {
                this.mSubtitleDataListenerDisabled = false;
            }
        }
    }

    public void addSubtitleSource(final InputStream inputStream, final MediaFormat mediaFormat) throws IllegalStateException, IllegalArgumentException {
        if (mediaFormat == null) {
            throw new IllegalArgumentException("Illegal null format");
        }
        if (inputStream != null) {
            synchronized (this.mOpenSubtitleSources) {
                this.mOpenSubtitleSources.add(inputStream);
            }
        } else {
            Log.w(TAG, "addSubtitleSource called with null InputStream");
        }
        getMediaTimeProvider();
        final HandlerThread handlerThread = new HandlerThread("SubtitleReadThread", -5);
        handlerThread.start();
        new Handler(handlerThread.getLooper()).post(new Runnable() { // from class: com.samsung.android.media.SemMediaPlayer.3
            /* JADX WARN: Removed duplicated region for block: B:77:0x00e3 A[EXC_TOP_SPLITTER, SYNTHETIC] */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            private int addTrack() throws Throwable {
                SubtitleTrack subtitleTrackAddTrack;
                Scanner scanner;
                Throwable th;
                Exception e;
                if (inputStream == null || SemMediaPlayer.this.mSubtitleController == null || (subtitleTrackAddTrack = SemMediaPlayer.this.mSubtitleController.addTrack(mediaFormat)) == null) {
                    return 901;
                }
                try {
                    int iAvailable = inputStream.available();
                    if (iAvailable > 20971520) {
                        Log.e(SemMediaPlayer.TAG, "addTrack() unsupported size : " + iAvailable);
                        return 901;
                    }
                    try {
                        scanner = new Scanner(inputStream, "UTF-8");
                        try {
                            try {
                                String next = scanner.useDelimiter("\\A").next();
                                synchronized (SemMediaPlayer.this.mOpenSubtitleSources) {
                                    SemMediaPlayer.this.mOpenSubtitleSources.remove(inputStream);
                                }
                                scanner.close();
                                if (next == null) {
                                    return 901;
                                }
                                synchronized (SemMediaPlayer.this.mIndexTrackPairs) {
                                    SemMediaPlayer.this.mIndexTrackPairs.add(Pair.create(null, subtitleTrackAddTrack));
                                }
                                try {
                                    Handler handler = SemMediaPlayer.this.mTimeProvider.mEventHandler;
                                    handler.sendMessage(handler.obtainMessage(2, 4, 0, Pair.create(subtitleTrackAddTrack, next.getBytes())));
                                    return 803;
                                } catch (NullPointerException e2) {
                                    Log.e(SemMediaPlayer.TAG, "handleMessage is NullPointerException e : ", e2);
                                    return 901;
                                }
                            } catch (Exception e3) {
                                e = e3;
                                Log.e(SemMediaPlayer.TAG, e.getMessage(), e);
                                synchronized (SemMediaPlayer.this.mOpenSubtitleSources) {
                                    SemMediaPlayer.this.mOpenSubtitleSources.remove(inputStream);
                                }
                                if (scanner != null) {
                                    scanner.close();
                                }
                                return 901;
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            synchronized (SemMediaPlayer.this.mOpenSubtitleSources) {
                                SemMediaPlayer.this.mOpenSubtitleSources.remove(inputStream);
                            }
                            if (scanner != null) {
                                scanner.close();
                            }
                            throw th;
                        }
                    } catch (Exception e4) {
                        scanner = null;
                        e = e4;
                    } catch (Throwable th3) {
                        scanner = null;
                        th = th3;
                        synchronized (SemMediaPlayer.this.mOpenSubtitleSources) {
                        }
                    }
                } catch (IOException e5) {
                    Log.e(SemMediaPlayer.TAG, e5.getMessage(), e5);
                    return 901;
                }
            }

            @Override // java.lang.Runnable
            public void run() throws Throwable {
                int iAddTrack = addTrack();
                if (SemMediaPlayer.this.mEventHandler != null) {
                    SemMediaPlayer.this.mEventHandler.sendMessage(SemMediaPlayer.this.mEventHandler.obtainMessage(200, iAddTrack, 0, null));
                }
                handlerThread.getLooper().quitSafely();
            }
        });
    }

    public void removeOutbandSubtitleSources() throws IllegalStateException {
        Log.d(TAG, "removeOutbandSubtitleSources");
        if (this.mSubtitleController == null) {
            Log.e(TAG, "Should have subtitle controller already set");
            return;
        }
        this.mSelectedSubtitleTrackIndex = -1;
        synchronized (this.mOpenSubtitleSources) {
            if (!this.mOpenSubtitleSources.isEmpty()) {
                Iterator<InputStream> it = this.mOpenSubtitleSources.iterator();
                while (it.hasNext()) {
                    try {
                        it.next().close();
                    } catch (IOException unused) {
                    }
                }
                this.mOpenSubtitleSources.clear();
            }
        }
        SubtitleController subtitleController = this.mSubtitleController;
        if (subtitleController != null) {
            subtitleController.resetTracks();
        }
        synchronized (this.mIndexTrackPairs) {
            this.mIndexTrackPairs.clear();
            this.mInbandTrackIndices.clear();
        }
        populateInbandTracks();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void scanInternalSubtitleTracks() throws IllegalStateException {
        setSubtitleAnchor();
        populateInbandTracks();
        SubtitleController subtitleController = this.mSubtitleController;
        if (subtitleController != null) {
            subtitleController.selectDefaultTrack();
        }
    }

    private void populateInbandTracks() throws IllegalStateException {
        populateInbandTracks(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void populateInbandTracks(TrackInfo[] trackInfoArr) throws IllegalStateException {
        if (trackInfoArr == null) {
            trackInfoArr = getInbandTrackInfo();
        }
        synchronized (this.mIndexTrackPairs) {
            for (int i = 0; i < trackInfoArr.length; i++) {
                if (!this.mInbandTrackIndices.get(i)) {
                    this.mInbandTrackIndices.set(i);
                    if (trackInfoArr[i] == null) {
                        Log.w(TAG, "unexpected NULL track at index " + i);
                    }
                    TrackInfo trackInfo = trackInfoArr[i];
                    if (trackInfo != null && trackInfo.getTrackType() == 4) {
                        this.mIndexTrackPairs.add(Pair.create(Integer.valueOf(i), this.mSubtitleController.addTrack(trackInfoArr[i].getFormat())));
                    } else {
                        this.mIndexTrackPairs.add(Pair.create(Integer.valueOf(i), null));
                    }
                }
            }
        }
    }

    @Deprecated(forRemoval = true, since = "17.0")
    public void setTemporalZoom(int i) throws IllegalStateException, UnsupportedOperationException, IllegalArgumentException {
        _setTemporalZoom(i);
    }

    public void setVideoFrc(int i, float f, boolean z) throws IllegalStateException, UnsupportedOperationException, IllegalArgumentException {
        _setVideoFrc(i, f, z);
    }
}
