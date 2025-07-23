package android.media;

import android.Manifest;
import android.annotation.SystemApi;
import android.app.ActivityThread;
import android.app.Application;
import android.content.AttributionSource;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioAttributes;
import android.media.AudioRouting;
import android.media.MediaDrm;
import android.media.MediaTimeProvider;
import android.media.SubtitleController;
import android.media.SubtitleTrack;
import android.media.VolumeShaper;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.os.PowerManager;
import android.os.SystemProperties;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Pair;
import android.view.Surface;
import android.view.SurfaceHolder;
import com.android.internal.content.NativeLibraryHelper;
import com.android.internal.util.Preconditions;
import com.samsung.android.audio.AudioManagerHelper;
import com.samsung.android.common.AsPackageName;
import com.samsung.android.media.AudioTag;
import com.samsung.android.rune.CoreRune;
import java.io.ByteArrayOutputStream;
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
import java.net.InetSocketAddress;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.Vector;
import java.util.concurrent.Executor;
import libcore.io.IoBridge;

/* loaded from: classes2.dex */
public class MediaPlayer extends PlayerBase implements SubtitleController.Listener, VolumeAutomation, AudioRouting {
    public static final boolean APPLY_METADATA_FILTER = true;
    public static final boolean BYPASS_METADATA_FILTER = false;
    private static final String IMEDIA_PLAYER = "android.media.IMediaPlayer";
    private static final int INVOKE_ID_ADD_EXTERNAL_SOURCE = 2;
    private static final int INVOKE_ID_ADD_EXTERNAL_SOURCE_FD = 3;
    private static final int INVOKE_ID_DESELECT_TRACK = 5;
    private static final int INVOKE_ID_GET_SELECTED_TRACK = 7;
    private static final int INVOKE_ID_GET_TRACK_INFO = 1;
    private static final int INVOKE_ID_SELECT_TRACK = 4;
    private static final int INVOKE_ID_SET_PLAYER_IID = 8;
    private static final int INVOKE_ID_SET_VIDEO_SCALE_MODE = 6;
    private static final int KEY_PARAMETER_AUDIO_ATTRIBUTES = 1400;
    private static final int KEY_PARAMETER_RTP_ATTRIBUTES = 2000;
    private static final int MEDIA_AUDIO_ROUTING_CHANGED = 10000;
    private static final int MEDIA_BUFFERING_UPDATE = 3;
    private static final int MEDIA_DRM_INFO = 210;
    private static final int MEDIA_ERROR = 100;
    public static final int MEDIA_ERROR_IO = -1004;
    public static final int MEDIA_ERROR_MALFORMED = -1007;
    public static final int MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK = 200;
    public static final int MEDIA_ERROR_SERVER_DIED = 100;
    public static final int MEDIA_ERROR_SYSTEM = Integer.MIN_VALUE;
    public static final int MEDIA_ERROR_TIMED_OUT = -110;
    public static final int MEDIA_ERROR_UNKNOWN = 1;
    public static final int MEDIA_ERROR_UNSUPPORTED = -1010;
    public static final int MEDIA_ErrDrmDevCertRevoked = -59;
    public static final int MEDIA_ErrDrmLicenseExpired = 301;
    public static final int MEDIA_ErrDrmLicenseNotFound = 300;
    public static final int MEDIA_ErrDrmLicenseNotValidYet = 302;
    public static final int MEDIA_ErrDrmRightsAcquisitionFailed = -49;
    public static final int MEDIA_ErrDrmServerDeviceLimitReached = -64;
    public static final int MEDIA_ErrDrmServerDomainRequired = -60;
    public static final int MEDIA_ErrDrmServerInternalError = -58;
    public static final int MEDIA_ErrDrmServerNotAMember = -61;
    public static final int MEDIA_ErrDrmServerProtocolVersionMismatch = -63;
    public static final int MEDIA_ErrDrmServerUnknownAccountId = -62;
    private static final int MEDIA_INFO = 200;
    public static final int MEDIA_INFO_AUDIO_NOT_PLAYING = 804;
    public static final int MEDIA_INFO_BAD_INTERLEAVING = 800;
    public static final int MEDIA_INFO_BUFFERING_END = 702;
    public static final int MEDIA_INFO_BUFFERING_START = 701;
    public static final int MEDIA_INFO_EXTERNAL_METADATA_UPDATE = 803;
    public static final int MEDIA_INFO_METADATA_UPDATE = 802;
    public static final int MEDIA_INFO_NETWORK_BANDWIDTH = 703;
    public static final int MEDIA_INFO_NOT_SEEKABLE = 801;
    public static final int MEDIA_INFO_STARTED_AS_NEXT = 2;
    public static final int MEDIA_INFO_SUBTITLE_TIMED_OUT = 902;
    public static final int MEDIA_INFO_TIMED_TEXT_ERROR = 900;
    public static final int MEDIA_INFO_UNKNOWN = 1;
    public static final int MEDIA_INFO_UNSUPPORTED_SUBTITLE = 901;
    public static final int MEDIA_INFO_VIDEO_NOT_PLAYING = 805;
    public static final int MEDIA_INFO_VIDEO_RENDERING_START = 3;
    public static final int MEDIA_INFO_VIDEO_TRACK_LAGGING = 700;
    private static final int MEDIA_META_DATA = 202;
    public static final String MEDIA_MIMETYPE_TEXT_CEA_608 = "text/cea-608";
    public static final String MEDIA_MIMETYPE_TEXT_CEA_708 = "text/cea-708";
    public static final String MEDIA_MIMETYPE_TEXT_SUBRIP = "application/x-subrip";
    public static final String MEDIA_MIMETYPE_TEXT_VTT = "text/vtt";
    private static final int MEDIA_NOP = 0;
    private static final int MEDIA_NOTIFY_TIME = 98;
    private static final int MEDIA_PAUSED = 7;
    private static final int MEDIA_PLAYBACK_COMPLETE = 2;
    private static final int MEDIA_PREPARED = 1;
    public static final int MEDIA_PREPARED_MIRACAST_SINK = 711;
    private static final int MEDIA_RTP_RX_NOTICE = 300;
    private static final int MEDIA_SEEK_COMPLETE = 4;
    private static final int MEDIA_SET_VIDEO_SIZE = 5;
    private static final int MEDIA_SKIPPED = 9;
    private static final int MEDIA_STARTED = 6;
    private static final int MEDIA_STOPPED = 8;
    private static final int MEDIA_SUBTITLE_DATA = 201;
    private static final int MEDIA_TIMED_TEXT = 99;
    private static final int MEDIA_TIME_DISCONTINUITY = 211;
    public static final boolean METADATA_ALL = false;
    public static final boolean METADATA_UPDATE_ONLY = true;
    public static final int PLAYBACK_RATE_AUDIO_MODE_DEFAULT = 0;
    public static final int PLAYBACK_RATE_AUDIO_MODE_RESAMPLE = 2;
    public static final int PLAYBACK_RATE_AUDIO_MODE_STRETCH = 1;
    public static final int PREPARE_DRM_STATUS_PREPARATION_ERROR = 3;
    public static final int PREPARE_DRM_STATUS_PROVISIONING_NETWORK_ERROR = 1;
    public static final int PREPARE_DRM_STATUS_PROVISIONING_SERVER_ERROR = 2;
    public static final int PREPARE_DRM_STATUS_SUCCESS = 0;
    public static final int SEEK_CLOSEST = 3;
    public static final int SEEK_CLOSEST_SYNC = 2;
    public static final int SEEK_NEXT_SYNC = 1;
    public static final int SEEK_PREVIOUS_SYNC = 0;
    public static final int SEM_KEY_PARAMETER_ADAPTIVE_ACCURATE_SEEK_THRESHOLD = 35005;
    public static final int SEM_KEY_PARAMETER_EXCLUDE_AUDIO_TRACK = 35004;

    @Deprecated(forRemoval = true, since = "13.0")
    public static final int SEM_KEY_PARAMETER_HOVERING_TYPE = 31950;
    public static final int SEM_KEY_PARAMETER_USE_SKIP_SILENCE = 35002;
    public static final int SEM_KEY_PARAMETER_USE_SW_DECODER = 33000;

    @Deprecated(forRemoval = true, since = "13.0")
    public static final int SEM_KEY_PARAMETER_VIDEO_FPS = 31505;
    public static final int SEM_MEDIA_ERROR_RESOURCE_OVERSPEC = -5001;
    public static final int SEM_MEDIA_INFO_NO_AUDIO = 10972;
    public static final int SEM_MEDIA_INFO_NO_VIDEO = 10973;
    public static final int SEM_MEDIA_INFO_UNSUPPORTED_AUDIO = 10950;
    public static final int SEM_MEDIA_INFO_UNSUPPORTED_TICKPLAY = 10953;
    public static final int SEM_MEDIA_INFO_UNSUPPORTED_VIDEO = 10951;
    public static final int SEM_SEEK_TYPE_ACCURATE_FRAME = 1;
    public static final int SEM_SEEK_TYPE_ADAPTIVE_ACCURATE_FRAME = 5;
    public static final int SEM_SEEK_TYPE_CLOSEST_SYNC_FRAME = 4;
    public static final int SEM_SEEK_TYPE_ONE_FRAME_BACKWARD = 2;
    public static final int SEM_SEEK_TYPE_ONE_FRAME_FORWARD = 3;
    public static final int SEM_SEEK_TYPE_VIDEO_PREVIEW = 0;
    private static final String TAG = "MediaPlayer";
    public static final int VIDEO_SCALING_MODE_SCALE_TO_FIT = 1;
    public static final int VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING = 2;
    private String gvsTarget;
    private boolean mActiveDrmScheme;
    private boolean mDrmConfigAllowed;
    private DrmInfo mDrmInfo;
    private boolean mDrmInfoResolved;
    private final Object mDrmLock;
    private MediaDrm mDrmObj;
    private boolean mDrmProvisioningInProgress;
    private ProvisioningThread mDrmProvisioningThread;
    private byte[] mDrmSessionId;
    private UUID mDrmUUID;
    private boolean mEnableSelfRoutingMonitor;
    private EventHandler mEventHandler;
    private Handler mExtSubtitleDataHandler;
    private OnSubtitleDataListener mExtSubtitleDataListener;
    private BitSet mInbandTrackIndices;
    private Vector<Pair<Integer, SubtitleTrack>> mIndexTrackPairs;
    private final OnSubtitleDataListener mIntSubtitleDataListener;
    private int mListenerContext;
    private long mNativeContext;
    private long mNativeSurfaceTexture;
    private OnBufferingUpdateListener mOnBufferingUpdateListener;
    private final OnCompletionListener mOnCompletionInternalListener;
    private OnCompletionListener mOnCompletionListener;
    private OnDrmConfigHelper mOnDrmConfigHelper;
    private OnDrmInfoHandlerDelegate mOnDrmInfoHandlerDelegate;
    private OnDrmPreparedHandlerDelegate mOnDrmPreparedHandlerDelegate;
    private OnErrorListener mOnErrorListener;
    private OnInfoListener mOnInfoListener;
    private Handler mOnMediaTimeDiscontinuityHandler;
    private OnMediaTimeDiscontinuityListener mOnMediaTimeDiscontinuityListener;
    private OnPlayReadyErrorListener mOnPlayReadyErrorListener;
    private OnPreparedListener mOnPreparedListener;
    private Executor mOnRtpRxNoticeExecutor;
    private OnRtpRxNoticeListener mOnRtpRxNoticeListener;
    private OnSeekCompleteListener mOnSeekCompleteListener;
    private OnTimedMetaDataAvailableListener mOnTimedMetaDataAvailableListener;
    private OnTimedTextListener mOnTimedTextListener;
    private OnVideoSizeChangedListener mOnVideoSizeChangedListener;
    private Vector<InputStream> mOpenSubtitleSources;
    private AudioDeviceInfo mPreferredDevice;
    private boolean mPrepareDrmInProgress;
    private ArrayMap<AudioRouting.OnRoutingChangedListener, NativeRoutingEventHandlerDelegate> mRoutingChangeListeners;
    private boolean mScreenOnWhilePlaying;
    private int mSelectedSubtitleTrackIndex;
    private boolean mStayAwake;
    private int mStreamType;
    private SubtitleController mSubtitleController;
    private boolean mSubtitleDataListenerDisabled;
    private SurfaceHolder mSurfaceHolder;
    private TimeProvider mTimeProvider;
    private final Object mTimeProviderLock;
    private PowerManager.WakeLock mWakeLock;
    private String packageName;

    public interface OnBufferingUpdateListener {
        void onBufferingUpdate(MediaPlayer mediaPlayer, int i);
    }

    public interface OnCompletionListener {
        void onCompletion(MediaPlayer mediaPlayer);
    }

    public interface OnDrmConfigHelper {
        void onDrmConfig(MediaPlayer mediaPlayer);
    }

    public interface OnDrmInfoListener {
        void onDrmInfo(MediaPlayer mediaPlayer, DrmInfo drmInfo);
    }

    public interface OnDrmPreparedListener {
        void onDrmPrepared(MediaPlayer mediaPlayer, int i);
    }

    public interface OnErrorListener {
        boolean onError(MediaPlayer mediaPlayer, int i, int i2);
    }

    public interface OnInfoListener {
        boolean onInfo(MediaPlayer mediaPlayer, int i, int i2);
    }

    public interface OnMediaTimeDiscontinuityListener {
        void onMediaTimeDiscontinuity(MediaPlayer mediaPlayer, MediaTimestamp mediaTimestamp);
    }

    public interface OnPlayReadyErrorListener {
        boolean onPlayReadyError(MediaPlayer mediaPlayer, int i, int i2, String str);
    }

    public interface OnPreparedListener {
        void onPrepared(MediaPlayer mediaPlayer);
    }

    @SystemApi
    public interface OnRtpRxNoticeListener {
        void onRtpRxNotice(MediaPlayer mediaPlayer, int i, int[] iArr);
    }

    public interface OnSeekCompleteListener {
        void onSeekComplete(MediaPlayer mediaPlayer);
    }

    public interface OnSubtitleDataListener {
        void onSubtitleData(MediaPlayer mediaPlayer, SubtitleData subtitleData);
    }

    public interface OnTimedMetaDataAvailableListener {
        void onTimedMetaDataAvailable(MediaPlayer mediaPlayer, TimedMetaData timedMetaData);
    }

    public interface OnTimedTextListener {
        void onTimedText(MediaPlayer mediaPlayer, TimedText timedText);
    }

    public interface OnVideoSizeChangedListener {
        void onVideoSizeChanged(MediaPlayer mediaPlayer, int i, int i2);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface PlaybackRateAudioMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface PrepareDrmStatusCode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SeekMode {
    }

    private native int _getAudioStreamType() throws IllegalStateException;

    private native void _notifyAt(long j);

    private native void _pause() throws IllegalStateException;

    private native int _prepare(Parcel parcel) throws IOException, IllegalStateException;

    private native int _prepareAsync(Parcel parcel) throws IllegalStateException;

    private native void _prepareDrm(byte[] bArr, byte[] bArr2);

    private native void _release();

    private native void _releaseDrm();

    private native void _reset();

    private final native void _seekTo(long j, int i);

    private native Bitmap _semGetCurrentFrame(int i, int i2) throws IllegalStateException;

    private native void _setAudioStreamType(int i);

    private native void _setAuxEffectSendLevel(float f);

    private native void _setDataSource(MediaDataSource mediaDataSource) throws IllegalArgumentException, IllegalStateException;

    private native void _setDataSource(FileDescriptor fileDescriptor, long j, long j2) throws IOException, IllegalArgumentException, IllegalStateException;

    private native void _setVideoSurface(Surface surface);

    private native void _setVolume(float f, float f2);

    private native void _start() throws IllegalStateException;

    private native void _stop() throws IllegalStateException;

    private native void getParameter(int i, Parcel parcel);

    private boolean isVideoScalingModeSupported(int i) {
        return i == 1 || i == 2;
    }

    private native void nativeSetDataSource(IBinder iBinder, String str, String[] strArr, String[] strArr2) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException;

    private native int native_applyVolumeShaper(VolumeShaper.Configuration configuration, VolumeShaper.Operation operation);

    private final native void native_enableDeviceCallback(boolean z);

    private final native void native_finalize();

    private final native boolean native_getMetadata(boolean z, boolean z2, Parcel parcel);

    private native PersistableBundle native_getMetrics();

    private native int[] native_getRoutedDeviceIds();

    private native VolumeShaper.State native_getVolumeShaperState(int i);

    private static final native void native_init();

    private final native int native_invoke(Parcel parcel, Parcel parcel2);

    public static native int native_pullBatteryData(Parcel parcel);

    private native void native_setAudioSessionId(int i);

    private final native int native_setMetadataFilter(Parcel parcel);

    private final native boolean native_setOutputDevice(int i);

    private final native int native_setRetransmitEndpoint(String str, int i);

    private native void native_setup(Object obj, Parcel parcel, int i);

    private native boolean setParameter(int i, Parcel parcel);

    public native void _semSeekTo(int i, int i2) throws IllegalStateException;

    public native void attachAuxEffect(int i);

    public native int getAudioSessionId();

    public native int getCurrentPosition();

    public native int getDuration();

    public native PlaybackParams getPlaybackParams();

    public native SyncParams getSyncParams();

    public native int getVideoHeight();

    public native int getVideoWidth();

    public native boolean isLooping();

    public native boolean isPlaying();

    public native int semGetLastRenderedVideoPosition() throws IllegalStateException;

    public native void setLooping(boolean z);

    public native void setNextMediaPlayer(MediaPlayer mediaPlayer);

    public native void setPlaybackParams(PlaybackParams playbackParams);

    public native void setSyncParams(SyncParams syncParams);

    static {
        System.loadLibrary("media_jni");
        native_init();
    }

    public MediaPlayer() {
        this(null, 0);
    }

    public MediaPlayer(Context context) {
        this((Context) Objects.requireNonNull(context), 0);
    }

    private MediaPlayer(Context context, int i) {
        super(new AudioAttributes.Builder().build(), 2);
        AttributionSource attributionSource;
        this.mWakeLock = null;
        this.mStreamType = Integer.MIN_VALUE;
        this.mDrmLock = new Object();
        this.mPreferredDevice = null;
        this.mRoutingChangeListeners = new ArrayMap<>();
        this.mIndexTrackPairs = new Vector<>();
        this.mInbandTrackIndices = new BitSet();
        this.mSelectedSubtitleTrackIndex = -1;
        this.mIntSubtitleDataListener = new OnSubtitleDataListener() { // from class: android.media.MediaPlayer.3
            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.media.MediaPlayer.OnSubtitleDataListener
            public void onSubtitleData(MediaPlayer mediaPlayer, SubtitleData subtitleData) {
                int trackIndex = subtitleData.getTrackIndex();
                synchronized (MediaPlayer.this.mIndexTrackPairs) {
                    Iterator it = MediaPlayer.this.mIndexTrackPairs.iterator();
                    while (it.hasNext()) {
                        Pair pair = (Pair) it.next();
                        if (pair.first != 0 && ((Integer) pair.first).intValue() == trackIndex && pair.second != 0) {
                            ((SubtitleTrack) pair.second).onData(subtitleData);
                        }
                    }
                }
            }
        };
        this.mTimeProviderLock = new Object();
        this.mOnCompletionInternalListener = new OnCompletionListener() { // from class: android.media.MediaPlayer.7
            @Override // android.media.MediaPlayer.OnCompletionListener
            public void onCompletion(MediaPlayer mediaPlayer) {
                MediaPlayer.this.tryToDisableNativeRoutingCallback();
                MediaPlayer.this.baseStop();
            }
        };
        Looper myLooper = Looper.myLooper();
        if (myLooper != null) {
            this.mEventHandler = new EventHandler(this, myLooper);
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
        if (context == null) {
            attributionSource = AttributionSource.myAttributionSource();
        } else {
            attributionSource = context.getAttributionSource();
        }
        String packageName = attributionSource.getPackageName();
        this.packageName = packageName;
        if (packageName == null) {
            attributionSource = attributionSource.withPackageName("");
            this.packageName = "";
        }
        AttributionSource.ScopedParcelState asScopedParcelState = attributionSource.asScopedParcelState();
        try {
            native_setup(new WeakReference(this), asScopedParcelState.getParcel(), resolvePlaybackSessionId(context, i));
            if (asScopedParcelState != null) {
                asScopedParcelState.close();
            }
            baseRegisterPlayer(getAudioSessionId());
        } catch (Throwable th) {
            if (asScopedParcelState != null) {
                try {
                    asScopedParcelState.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    private Parcel createPlayerIIdParcel() {
        Parcel newRequest = newRequest();
        newRequest.writeInt(8);
        newRequest.writeInt(this.mPlayerIId);
        return newRequest;
    }

    public Parcel newRequest() {
        Parcel obtain = Parcel.obtain();
        obtain.writeInterfaceToken(IMEDIA_PLAYER);
        return obtain;
    }

    public void invoke(Parcel parcel, Parcel parcel2) {
        int native_invoke = native_invoke(parcel, parcel2);
        parcel2.setDataPosition(0);
        if (native_invoke == 0) {
            return;
        }
        throw new RuntimeException("failure code: " + native_invoke);
    }

    public void setDisplay(SurfaceHolder surfaceHolder) {
        this.mSurfaceHolder = surfaceHolder;
        _setVideoSurface(surfaceHolder != null ? surfaceHolder.getSurface() : null);
        updateSurfaceScreenOn();
    }

    public void setSurface(Surface surface) {
        if (this.mScreenOnWhilePlaying && surface != null) {
            Log.w(TAG, "setScreenOnWhilePlaying(true) is ineffective for Surface");
        }
        this.mSurfaceHolder = null;
        _setVideoSurface(surface);
        updateSurfaceScreenOn();
    }

    public void setVideoScalingMode(int i) {
        if (!isVideoScalingModeSupported(i)) {
            throw new IllegalArgumentException("Scaling mode " + i + " is not supported");
        }
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken(IMEDIA_PLAYER);
            obtain.writeInt(6);
            obtain.writeInt(i);
            invoke(obtain, obtain2);
        } finally {
            obtain.recycle();
            obtain2.recycle();
        }
    }

    public static MediaPlayer create(Context context, Uri uri) {
        return create(context, uri, null);
    }

    public static MediaPlayer create(Context context, Uri uri, SurfaceHolder surfaceHolder) {
        return create(context, uri, surfaceHolder, null, 0);
    }

    public static MediaPlayer create(Context context, Uri uri, SurfaceHolder surfaceHolder, AudioAttributes audioAttributes, int i) {
        try {
            MediaPlayer mediaPlayer = new MediaPlayer(context, i);
            if (audioAttributes == null) {
                audioAttributes = new AudioAttributes.Builder().build();
            }
            mediaPlayer.setAudioAttributes(audioAttributes);
            mediaPlayer.setDataSource(context, uri);
            if (surfaceHolder != null) {
                mediaPlayer.setDisplay(surfaceHolder);
            }
            mediaPlayer.prepare();
            return mediaPlayer;
        } catch (IOException e) {
            Log.d(TAG, "create failed:", e);
            return null;
        } catch (IllegalArgumentException e2) {
            Log.d(TAG, "create failed:", e2);
            return null;
        } catch (SecurityException e3) {
            Log.d(TAG, "create failed:", e3);
            return null;
        }
    }

    public static MediaPlayer create(Context context, int i) {
        return create(context, i, null, 0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:52:0x008c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:59:? A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.media.MediaPlayer create(android.content.Context r10, int r11, android.media.AudioAttributes r12, int r13) {
        /*
            java.lang.String r1 = "create failed:"
            java.lang.String r2 = "MediaPlayer"
            r3 = 0
            android.content.res.Resources r0 = r10.getResources()     // Catch: java.lang.Throwable -> L5d java.lang.SecurityException -> L60 java.lang.IllegalArgumentException -> L6c java.io.IOException -> L78
            android.content.res.AssetFileDescriptor r11 = r0.openRawResourceFd(r11)     // Catch: java.lang.Throwable -> L5d java.lang.SecurityException -> L60 java.lang.IllegalArgumentException -> L6c java.io.IOException -> L78
            if (r11 != 0) goto L1b
            if (r11 == 0) goto L1a
            r11.close()     // Catch: java.io.IOException -> L15
            return r3
        L15:
            r0 = move-exception
            r10 = r0
            android.util.Log.d(r2, r1, r10)
        L1a:
            return r3
        L1b:
            android.media.MediaPlayer r4 = new android.media.MediaPlayer     // Catch: java.lang.Throwable -> L50 java.lang.SecurityException -> L54 java.lang.IllegalArgumentException -> L57 java.io.IOException -> L5a
            r4.<init>(r10, r13)     // Catch: java.lang.Throwable -> L50 java.lang.SecurityException -> L54 java.lang.IllegalArgumentException -> L57 java.io.IOException -> L5a
            if (r12 == 0) goto L23
            goto L2c
        L23:
            android.media.AudioAttributes$Builder r10 = new android.media.AudioAttributes$Builder     // Catch: java.lang.Throwable -> L50 java.lang.SecurityException -> L54 java.lang.IllegalArgumentException -> L57 java.io.IOException -> L5a
            r10.<init>()     // Catch: java.lang.Throwable -> L50 java.lang.SecurityException -> L54 java.lang.IllegalArgumentException -> L57 java.io.IOException -> L5a
            android.media.AudioAttributes r12 = r10.build()     // Catch: java.lang.Throwable -> L50 java.lang.SecurityException -> L54 java.lang.IllegalArgumentException -> L57 java.io.IOException -> L5a
        L2c:
            r4.setAudioAttributes(r12)     // Catch: java.lang.Throwable -> L50 java.lang.SecurityException -> L54 java.lang.IllegalArgumentException -> L57 java.io.IOException -> L5a
            java.io.FileDescriptor r5 = r11.getFileDescriptor()     // Catch: java.lang.Throwable -> L50 java.lang.SecurityException -> L54 java.lang.IllegalArgumentException -> L57 java.io.IOException -> L5a
            long r6 = r11.getStartOffset()     // Catch: java.lang.Throwable -> L50 java.lang.SecurityException -> L54 java.lang.IllegalArgumentException -> L57 java.io.IOException -> L5a
            long r8 = r11.getLength()     // Catch: java.lang.Throwable -> L50 java.lang.SecurityException -> L54 java.lang.IllegalArgumentException -> L57 java.io.IOException -> L5a
            r4.setDataSource(r5, r6, r8)     // Catch: java.lang.Throwable -> L50 java.lang.SecurityException -> L54 java.lang.IllegalArgumentException -> L57 java.io.IOException -> L5a
            r11.close()     // Catch: java.lang.Throwable -> L50 java.lang.SecurityException -> L54 java.lang.IllegalArgumentException -> L57 java.io.IOException -> L5a
            r4.prepare()     // Catch: java.lang.Throwable -> L50 java.lang.SecurityException -> L54 java.lang.IllegalArgumentException -> L57 java.io.IOException -> L5a
            if (r11 == 0) goto L4f
            r11.close()     // Catch: java.io.IOException -> L4a
            return r4
        L4a:
            r0 = move-exception
            r10 = r0
            android.util.Log.d(r2, r1, r10)
        L4f:
            return r4
        L50:
            r0 = move-exception
            r10 = r0
            r3 = r11
            goto L8a
        L54:
            r0 = move-exception
            r10 = r0
            goto L63
        L57:
            r0 = move-exception
            r10 = r0
            goto L6f
        L5a:
            r0 = move-exception
            r10 = r0
            goto L7b
        L5d:
            r0 = move-exception
            r10 = r0
            goto L8a
        L60:
            r0 = move-exception
            r10 = r0
            r11 = r3
        L63:
            android.util.Log.d(r2, r1, r10)     // Catch: java.lang.Throwable -> L50
            if (r11 == 0) goto L89
            r11.close()     // Catch: java.io.IOException -> L84
            goto L89
        L6c:
            r0 = move-exception
            r10 = r0
            r11 = r3
        L6f:
            android.util.Log.d(r2, r1, r10)     // Catch: java.lang.Throwable -> L50
            if (r11 == 0) goto L89
            r11.close()     // Catch: java.io.IOException -> L84
            goto L89
        L78:
            r0 = move-exception
            r10 = r0
            r11 = r3
        L7b:
            android.util.Log.d(r2, r1, r10)     // Catch: java.lang.Throwable -> L50
            if (r11 == 0) goto L89
            r11.close()     // Catch: java.io.IOException -> L84
            goto L89
        L84:
            r0 = move-exception
            r10 = r0
            android.util.Log.d(r2, r1, r10)
        L89:
            return r3
        L8a:
            if (r3 == 0) goto L95
            r3.close()     // Catch: java.io.IOException -> L90
            goto L95
        L90:
            r0 = move-exception
            r11 = r0
            android.util.Log.d(r2, r1, r11)
        L95:
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: android.media.MediaPlayer.create(android.content.Context, int, android.media.AudioAttributes, int):android.media.MediaPlayer");
    }

    public void setDataSource(Context context, Uri uri) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        setDataSource(context, uri, (Map<String, String>) null, (List<HttpCookie>) null);
    }

    public void setDataSource(Context context, Uri uri, Map<String, String> map, List<HttpCookie> list) throws IOException {
        CookieHandler cookieHandler;
        if (context == null) {
            throw new NullPointerException("context param can not be null.");
        }
        if (uri == null) {
            throw new NullPointerException("uri param can not be null.");
        }
        if (list != null && (cookieHandler = CookieHandler.getDefault()) != null && !(cookieHandler instanceof CookieManager)) {
            throw new IllegalArgumentException("The cookie handler has to be of CookieManager type when cookies are provided.");
        }
        ContentResolver contentResolver = context.getContentResolver();
        String scheme = uri.getScheme();
        String authorityWithoutUserId = ContentProvider.getAuthorityWithoutUserId(uri.getAuthority());
        if ("file".equals(scheme)) {
            setDataSource(uri.getPath());
            return;
        }
        if ("content".equals(scheme) && "settings".equals(authorityWithoutUserId)) {
            int defaultType = RingtoneManager.getDefaultType(uri);
            Uri cacheForType = RingtoneManager.getCacheForType(defaultType, context.getUserId());
            Uri actualDefaultRingtoneUri = RingtoneManager.getActualDefaultRingtoneUri(context, defaultType);
            if (attemptDataSource(contentResolver, cacheForType) || attemptDataSource(contentResolver, actualDefaultRingtoneUri)) {
                return;
            }
            setDataSource(uri.toString(), map, list);
            return;
        }
        if (attemptDataSource(contentResolver, uri)) {
            return;
        }
        setDataSource(uri.toString(), map, list);
    }

    public void setDataSource(Context context, Uri uri, Map<String, String> map) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        setDataSource(context, uri, map, (List<HttpCookie>) null);
    }

    private boolean attemptDataSource(ContentResolver contentResolver, Uri uri) {
        AssetFileDescriptor openAssetFileDescriptor;
        boolean z = SystemProperties.getBoolean("fuse.sys.transcode_player_optimize", false);
        Bundle bundle = new Bundle();
        bundle.putBoolean("android.provider.extra.ACCEPT_ORIGINAL_MEDIA_FORMAT", true);
        try {
            if (z) {
                openAssetFileDescriptor = contentResolver.openTypedAssetFileDescriptor(uri, "*/*", bundle);
            } else {
                openAssetFileDescriptor = contentResolver.openAssetFileDescriptor(uri, "r");
            }
            try {
                setDataSource(openAssetFileDescriptor);
                if (openAssetFileDescriptor != null) {
                    openAssetFileDescriptor.close();
                }
                return true;
            } catch (Throwable th) {
                if (openAssetFileDescriptor != null) {
                    try {
                        openAssetFileDescriptor.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (IOException | NullPointerException | SecurityException e) {
            Log.w(TAG, "Error setting data source via ContentResolver", e);
            return false;
        }
    }

    public void setDataSource(String str) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        setDataSource(str, (Map<String, String>) null, (List<HttpCookie>) null);
    }

    public void setDataSource(String str, Map<String, String> map) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        setDataSource(str, map, (List<HttpCookie>) null);
    }

    private void setDataSource(String str, Map<String, String> map, List<HttpCookie> list) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        String[] strArr;
        String[] strArr2;
        if (map != null) {
            strArr = new String[map.size()];
            strArr2 = new String[map.size()];
            int i = 0;
            for (Map.Entry<String, String> entry : map.entrySet()) {
                strArr[i] = entry.getKey();
                strArr2[i] = entry.getValue();
                i++;
            }
        } else {
            strArr = null;
            strArr2 = null;
        }
        setDataSource(str, strArr, strArr2, list);
    }

    private void setDataSource(String str, String[] strArr, String[] strArr2, List<HttpCookie> list) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        Uri parse = Uri.parse(str);
        String scheme = parse.getScheme();
        if ("file".equals(scheme)) {
            str = parse.getPath();
        } else if (scheme != null) {
            nativeSetDataSource(MediaHTTPService.createHttpServiceBinderIfNecessary(str, list), str, strArr, strArr2);
            return;
        }
        if (str.startsWith("file://")) {
            str = str.replaceFirst("file://", "");
        }
        FileInputStream fileInputStream = new FileInputStream(new File(AudioManagerHelper.convertStartingPathToSystem(str)));
        try {
            try {
                setDataSource(fileInputStream.getFD());
                fileInputStream.close();
            } finally {
                fileInputStream.close();
            }
        } catch (Throwable th) {
            try {
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public void setDataSource(AssetFileDescriptor assetFileDescriptor) throws IOException, IllegalArgumentException, IllegalStateException {
        Preconditions.checkNotNull(assetFileDescriptor);
        if (assetFileDescriptor.getDeclaredLength() < 0) {
            setDataSource(assetFileDescriptor.getFileDescriptor());
        } else {
            setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getDeclaredLength());
        }
    }

    public void setDataSource(FileDescriptor fileDescriptor) throws IOException, IllegalArgumentException, IllegalStateException {
        setDataSource(fileDescriptor, 0L, 576460752303423487L);
    }

    public void setDataSource(FileDescriptor fileDescriptor, long j, long j2) throws IOException, IllegalArgumentException, IllegalStateException {
        try {
            ParcelFileDescriptor convertToModernFd = FileUtils.convertToModernFd(fileDescriptor);
            try {
                if (convertToModernFd == null) {
                    _setDataSource(fileDescriptor, j, j2);
                } else {
                    _setDataSource(convertToModernFd.getFileDescriptor(), j, j2);
                }
                if (convertToModernFd != null) {
                    convertToModernFd.close();
                }
            } finally {
            }
        } catch (IOException e) {
            Log.w(TAG, "Ignoring IO error while setting data source", e);
        }
    }

    public void setDataSource(MediaDataSource mediaDataSource) throws IllegalArgumentException, IllegalStateException {
        _setDataSource(mediaDataSource);
    }

    public Bitmap semGetCurrentFrame() throws IllegalStateException {
        return _semGetCurrentFrame(-1, -1);
    }

    public Bitmap semGetCurrentFrame(int i, int i2) throws IllegalStateException {
        return _semGetCurrentFrame(i, i2);
    }

    public void prepare() throws IOException, IllegalStateException {
        Parcel createPlayerIIdParcel = createPlayerIIdParcel();
        try {
            if (_prepare(createPlayerIIdParcel) != 0) {
                Log.w(TAG, "prepare(): could not set piid " + this.mPlayerIId);
            }
            createPlayerIIdParcel.recycle();
            scanInternalSubtitleTracks();
            synchronized (this.mDrmLock) {
                this.mDrmInfoResolved = true;
            }
        } catch (Throwable th) {
            createPlayerIIdParcel.recycle();
            throw th;
        }
    }

    public void prepareAsync() throws IllegalStateException {
        Parcel createPlayerIIdParcel = createPlayerIIdParcel();
        try {
            if (_prepareAsync(createPlayerIIdParcel) != 0) {
                Log.w(TAG, "prepareAsync(): could not set piid " + this.mPlayerIId);
            }
        } finally {
            createPlayerIIdParcel.recycle();
        }
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [android.media.MediaPlayer$1] */
    public void start() throws IllegalStateException {
        if (CoreRune.SYSPERF_ACTIVE_APP_GVS_ENABLE) {
            setGameVideoSpeed();
        }
        final int startDelayMs = getStartDelayMs();
        if (startDelayMs == 0) {
            try {
                startImpl();
                return;
            } catch (IllegalStateException e) {
                stayAwake(false);
                throw e;
            }
        }
        new Thread() { // from class: android.media.MediaPlayer.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                try {
                    Thread.sleep(startDelayMs);
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                }
                MediaPlayer.this.baseSetStartDelayMs(0);
                try {
                    MediaPlayer.this.startImpl();
                } catch (IllegalStateException unused) {
                    MediaPlayer.this.stayAwake(false);
                }
            }
        }.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startImpl() {
        baseStart(new int[0]);
        stayAwake(true);
        tryToEnableNativeRoutingCallback();
        _start();
    }

    private int getAudioStreamType() {
        if (this.mStreamType == Integer.MIN_VALUE) {
            this.mStreamType = _getAudioStreamType();
        }
        return this.mStreamType;
    }

    public void stop() throws IllegalStateException {
        stayAwake(false);
        _stop();
        baseStop();
        tryToDisableNativeRoutingCallback();
    }

    public void pause() throws IllegalStateException {
        stayAwake(false);
        _pause();
        basePause();
    }

    @Override // android.media.PlayerBase
    void playerStart() {
        start();
    }

    @Override // android.media.PlayerBase
    void playerPause() {
        pause();
    }

    @Override // android.media.PlayerBase
    void playerStop() {
        stop();
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

    /* JADX INFO: Access modifiers changed from: private */
    public void broadcastRoutingChange() {
        AudioManager.resetAudioPortGeneration();
        synchronized (this.mRoutingChangeListeners) {
            if (this.mEnableSelfRoutingMonitor) {
                baseUpdateDeviceIds(getRoutedDevicesInternal());
            }
            Iterator<NativeRoutingEventHandlerDelegate> it = this.mRoutingChangeListeners.values().iterator();
            while (it.hasNext()) {
                it.next().notifyClient();
            }
        }
    }

    private boolean testEnableNativeRoutingCallbacksLocked() {
        if (this.mRoutingChangeListeners.size() != 0 || this.mEnableSelfRoutingMonitor) {
            return false;
        }
        try {
            native_enableDeviceCallback(true);
            return true;
        } catch (IllegalStateException e) {
            if (!Log.isLoggable(TAG, 3)) {
                return false;
            }
            Log.d(TAG, "testEnableNativeRoutingCallbacks failed", e);
            return false;
        }
    }

    private void tryToEnableNativeRoutingCallback() {
        synchronized (this.mRoutingChangeListeners) {
            if (!this.mEnableSelfRoutingMonitor) {
                this.mEnableSelfRoutingMonitor = testEnableNativeRoutingCallbacksLocked();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void tryToDisableNativeRoutingCallback() {
        synchronized (this.mRoutingChangeListeners) {
            if (this.mEnableSelfRoutingMonitor) {
                this.mEnableSelfRoutingMonitor = false;
                testDisableNativeRoutingCallbacksLocked();
            }
        }
    }

    private void testDisableNativeRoutingCallbacksLocked() {
        if (this.mRoutingChangeListeners.size() != 0 || this.mEnableSelfRoutingMonitor) {
            return;
        }
        try {
            native_enableDeviceCallback(false);
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
            }
            testDisableNativeRoutingCallbacksLocked();
        }
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
        PowerManager.WakeLock newWakeLock = ((PowerManager) context.getSystemService("power")).newWakeLock(i | 536870912, MediaPlayer.class.getName());
        this.mWakeLock = newWakeLock;
        newWakeLock.setReferenceCounted(false);
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

    public PersistableBundle getMetrics() {
        return native_getMetrics();
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

    public void seekTo(long j, int i) {
        if (i < 0 || i > 3) {
            throw new IllegalArgumentException("Illegal seek mode: " + i);
        }
        long j2 = 2147483647L;
        if (j > 2147483647L) {
            Log.w(TAG, "seekTo offset " + j + " is too large, cap to 2147483647");
        } else {
            j2 = -2147483648L;
            if (j < -2147483648L) {
                Log.w(TAG, "seekTo offset " + j + " is too small, cap to -2147483648");
            }
            _seekTo(j, i);
        }
        j = j2;
        _seekTo(j, i);
    }

    public void seekTo(int i) throws IllegalStateException {
        seekTo(i, 0);
    }

    public MediaTimestamp getTimestamp() {
        try {
            return new MediaTimestamp(getCurrentPosition() * 1000, System.nanoTime(), isPlaying() ? getPlaybackParams().getSpeed() : 0.0f);
        } catch (IllegalStateException unused) {
            return null;
        }
    }

    public void semSeekTo(int i, int i2) throws IllegalStateException {
        _semSeekTo(i, i2);
    }

    public Metadata getMetadata(boolean z, boolean z2) {
        Parcel obtain = Parcel.obtain();
        Metadata metadata = new Metadata();
        if (!native_getMetadata(z, z2, obtain)) {
            obtain.recycle();
            return null;
        }
        if (metadata.parse(obtain)) {
            return metadata;
        }
        obtain.recycle();
        return null;
    }

    public int setMetadataFilter(Set<Integer> set, Set<Integer> set2) {
        Parcel newRequest = newRequest();
        int dataSize = newRequest.dataSize() + ((set.size() + 2 + set2.size()) * 4);
        if (newRequest.dataCapacity() < dataSize) {
            newRequest.setDataCapacity(dataSize);
        }
        newRequest.writeInt(set.size());
        Iterator<Integer> it = set.iterator();
        while (it.hasNext()) {
            newRequest.writeInt(it.next().intValue());
        }
        newRequest.writeInt(set2.size());
        Iterator<Integer> it2 = set2.iterator();
        while (it2.hasNext()) {
            newRequest.writeInt(it2.next().intValue());
        }
        return native_setMetadataFilter(newRequest);
    }

    public void release() {
        baseRelease();
        stayAwake(false);
        updateSurfaceScreenOn();
        this.mOnPreparedListener = null;
        this.mOnBufferingUpdateListener = null;
        this.mOnCompletionListener = null;
        this.mOnSeekCompleteListener = null;
        this.mOnErrorListener = null;
        this.mOnInfoListener = null;
        this.mOnVideoSizeChangedListener = null;
        this.mOnTimedTextListener = null;
        this.mOnRtpRxNoticeListener = null;
        this.mOnRtpRxNoticeExecutor = null;
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
            this.mOnMediaTimeDiscontinuityListener = null;
            this.mOnMediaTimeDiscontinuityHandler = null;
        }
        this.mOnDrmConfigHelper = null;
        this.mOnDrmInfoHandlerDelegate = null;
        this.mOnDrmPreparedHandlerDelegate = null;
        resetDrmState();
        _release();
    }

    public void reset() {
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
        resetDrmState();
    }

    public void notifyAt(long j) {
        _notifyAt(j);
    }

    public void setAudioStreamType(int i) {
        deprecateStreamTypeForPlayback(i, TAG, "setAudioStreamType()");
        baseUpdateAudioAttributes(new AudioAttributes.Builder().setInternalLegacyStreamType(i).build());
        _setAudioStreamType(i);
        this.mStreamType = i;
    }

    public boolean semSetParameter(int i, String str) {
        Parcel obtain = Parcel.obtain();
        obtain.writeString(str);
        boolean parameter = setParameter(i, obtain);
        obtain.recycle();
        return parameter;
    }

    public boolean semSetParameter(int i, int i2) {
        Parcel obtain = Parcel.obtain();
        obtain.writeInt(i2);
        boolean parameter = setParameter(i, obtain);
        obtain.recycle();
        return parameter;
    }

    @Deprecated(forRemoval = true, since = "13.0")
    public int semGetIntParameter(int i) {
        Parcel obtain = Parcel.obtain();
        getParameter(i, obtain);
        int readInt = obtain.readInt();
        obtain.recycle();
        return readInt;
    }

    public void setAudioAttributes(AudioAttributes audioAttributes) throws IllegalArgumentException {
        if (audioAttributes == null) {
            throw new IllegalArgumentException("Cannot set AudioAttributes to null");
        }
        if (audioAttributes.getUsage() == 4) {
            String currentOpPackageName = ActivityThread.currentOpPackageName();
            if (AsPackageName.CELL_RECEIVER.equals(currentOpPackageName) || "com.android.cellbroadcastreceiver".equals(currentOpPackageName)) {
                audioAttributes = new AudioAttributes.Builder(audioAttributes).addTag(AudioTag.AUDIO_NO_FADE).build();
                Log.d(TAG, "attributes, add nofade tag");
            }
        }
        baseUpdateAudioAttributes(audioAttributes);
        Parcel obtain = Parcel.obtain();
        audioAttributes.writeToParcel(obtain, 1);
        setParameter(1400, obtain);
        obtain.recycle();
    }

    public void setRTPBitrate(int i) {
        Parcel obtain = Parcel.obtain();
        obtain.writeInt(i);
        setParameter(2000, obtain);
    }

    public void setVolume(float f, float f2) {
        baseSetVolume(f, f2);
    }

    @Override // android.media.PlayerBase
    void playerSetVolume(boolean z, float f, float f2) {
        if (z) {
            f = 0.0f;
        }
        if (z) {
            f2 = 0.0f;
        }
        _setVolume(f, f2);
    }

    public void setVolume(float f) {
        setVolume(f, f);
    }

    public void setAudioSessionId(int i) throws IllegalArgumentException, IllegalStateException {
        native_setAudioSessionId(i);
        baseUpdateSessionId(i);
    }

    public void setAuxEffectSendLevel(float f) {
        baseSetAuxEffectSendLevel(f);
    }

    @Override // android.media.PlayerBase
    int playerSetAuxEffectSendLevel(boolean z, float f) {
        if (z) {
            f = 0.0f;
        }
        _setAuxEffectSendLevel(f);
        return 0;
    }

    public static class TrackInfo implements Parcelable {
        static final Parcelable.Creator<TrackInfo> CREATOR = new Parcelable.Creator<TrackInfo>() { // from class: android.media.MediaPlayer.TrackInfo.1
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
        public static final int MEDIA_TRACK_TYPE_METADATA = 5;
        public static final int MEDIA_TRACK_TYPE_SUBTITLE = 4;
        public static final int MEDIA_TRACK_TYPE_TIMEDTEXT = 3;
        public static final int MEDIA_TRACK_TYPE_UNKNOWN = 0;
        public static final int MEDIA_TRACK_TYPE_VIDEO = 1;
        final MediaFormat mFormat;
        final int mTrackType;

        @Retention(RetentionPolicy.SOURCE)
        public @interface TrackType {
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public int getTrackType() {
            return this.mTrackType;
        }

        public String getLanguage() {
            String string = this.mFormat.getString("language");
            return string == null ? "und" : string;
        }

        public boolean hasHapticChannels() {
            MediaFormat mediaFormat = this.mFormat;
            return mediaFormat != null && mediaFormat.containsKey(MediaFormat.KEY_HAPTIC_CHANNEL_COUNT) && this.mFormat.getInteger(MediaFormat.KEY_HAPTIC_CHANNEL_COUNT) > 0;
        }

        public MediaFormat getFormat() {
            int i = this.mTrackType;
            if (i == 3 || i == 4) {
                return this.mFormat;
            }
            return null;
        }

        TrackInfo(Parcel parcel) {
            int readInt = parcel.readInt();
            this.mTrackType = readInt;
            MediaFormat createSubtitleFormat = MediaFormat.createSubtitleFormat(parcel.readString(), parcel.readString());
            this.mFormat = createSubtitleFormat;
            if (readInt == 4) {
                createSubtitleFormat.setInteger(MediaFormat.KEY_IS_AUTOSELECT, parcel.readInt());
                createSubtitleFormat.setInteger(MediaFormat.KEY_IS_DEFAULT, parcel.readInt());
                createSubtitleFormat.setInteger(MediaFormat.KEY_IS_FORCED_SUBTITLE, parcel.readInt());
            } else if (readInt == 2 && parcel.readBoolean()) {
                createSubtitleFormat.setInteger(MediaFormat.KEY_HAPTIC_CHANNEL_COUNT, parcel.readInt());
            }
        }

        TrackInfo(int i, MediaFormat mediaFormat) {
            this.mTrackType = i;
            this.mFormat = mediaFormat;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mTrackType);
            parcel.writeString(this.mFormat.getString("mime"));
            parcel.writeString(getLanguage());
            int i2 = this.mTrackType;
            if (i2 == 4) {
                parcel.writeInt(this.mFormat.getInteger(MediaFormat.KEY_IS_AUTOSELECT));
                parcel.writeInt(this.mFormat.getInteger(MediaFormat.KEY_IS_DEFAULT));
                parcel.writeInt(this.mFormat.getInteger(MediaFormat.KEY_IS_FORCED_SUBTITLE));
            } else if (i2 == 2) {
                boolean containsKey = this.mFormat.containsKey(MediaFormat.KEY_HAPTIC_CHANNEL_COUNT);
                parcel.writeBoolean(containsKey);
                if (containsKey) {
                    parcel.writeInt(this.mFormat.getInteger(MediaFormat.KEY_HAPTIC_CHANNEL_COUNT));
                }
            }
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(128);
            sb.append(getClass().getName());
            sb.append('{');
            int i = this.mTrackType;
            if (i == 1) {
                sb.append("VIDEO");
            } else if (i == 2) {
                sb.append("AUDIO");
            } else if (i == 3) {
                sb.append("TIMEDTEXT");
            } else if (i == 4) {
                sb.append("SUBTITLE");
            } else {
                sb.append("UNKNOWN");
            }
            sb.append(", " + this.mFormat.toString());
            sb.append("}");
            return sb.toString();
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
                    SubtitleTrack subtitleTrack = pair.second;
                    trackInfoArr[i] = new TrackInfo(subtitleTrack.getTrackType(), subtitleTrack.getFormat());
                }
            }
        }
        return trackInfoArr;
    }

    private TrackInfo[] getInbandTrackInfo() throws IllegalStateException {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken(IMEDIA_PLAYER);
            obtain.writeInt(1);
            invoke(obtain, obtain2);
            return (TrackInfo[]) obtain2.createTypedArray(TrackInfo.CREATOR);
        } finally {
            obtain.recycle();
            obtain2.recycle();
        }
    }

    private static boolean availableMimeTypeForExternalSource(String str) {
        return "application/x-subrip".equals(str);
    }

    public void setSubtitleAnchor(SubtitleController subtitleController, SubtitleController.Anchor anchor) {
        this.mSubtitleController = subtitleController;
        subtitleController.setAnchor(anchor);
    }

    private synchronized void setSubtitleAnchor() {
        if (this.mSubtitleController == null && ActivityThread.currentApplication() != null) {
            final TimeProvider timeProvider = (TimeProvider) getMediaTimeProvider();
            final HandlerThread handlerThread = new HandlerThread("SetSubtitleAnchorThread");
            handlerThread.start();
            new Handler(handlerThread.getLooper()).post(new Runnable() { // from class: android.media.MediaPlayer.2
                @Override // java.lang.Runnable
                public void run() {
                    Application currentApplication = ActivityThread.currentApplication();
                    MediaPlayer.this.mSubtitleController = new SubtitleController(currentApplication, timeProvider, MediaPlayer.this);
                    MediaPlayer.this.mSubtitleController.setAnchor(new SubtitleController.Anchor() { // from class: android.media.MediaPlayer.2.1
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

    public void addSubtitleSource(final InputStream inputStream, final MediaFormat mediaFormat) throws IllegalStateException {
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
        new Handler(handlerThread.getLooper()).post(new Runnable() { // from class: android.media.MediaPlayer.4
            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Removed duplicated region for block: B:69:0x00ea A[EXC_TOP_SPLITTER, SYNTHETIC] */
            /* JADX WARN: Type inference failed for: r3v2, types: [int] */
            /* JADX WARN: Type inference failed for: r3v3 */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            private int addTrack() {
                /*
                    Method dump skipped, instructions count: 266
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: android.media.MediaPlayer.AnonymousClass4.addTrack():int");
            }

            @Override // java.lang.Runnable
            public void run() {
                int addTrack = addTrack();
                if (MediaPlayer.this.mEventHandler != null) {
                    MediaPlayer.this.mEventHandler.sendMessage(MediaPlayer.this.mEventHandler.obtainMessage(200, addTrack, 0, null));
                }
                handlerThread.getLooper().quitSafely();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void scanInternalSubtitleTracks() {
        setSubtitleAnchor();
        populateInbandTracks();
        SubtitleController subtitleController = this.mSubtitleController;
        if (subtitleController != null) {
            subtitleController.selectDefaultTrack();
        }
    }

    private void populateInbandTracks() {
        TrackInfo[] inbandTrackInfo = getInbandTrackInfo();
        synchronized (this.mIndexTrackPairs) {
            for (int i = 0; i < inbandTrackInfo.length; i++) {
                if (!this.mInbandTrackIndices.get(i)) {
                    this.mInbandTrackIndices.set(i);
                    if (inbandTrackInfo[i] == null) {
                        Log.w(TAG, "unexpected NULL track at index " + i);
                    }
                    TrackInfo trackInfo = inbandTrackInfo[i];
                    if (trackInfo != null && trackInfo.getTrackType() == 4) {
                        this.mIndexTrackPairs.add(Pair.create(Integer.valueOf(i), this.mSubtitleController.addTrack(inbandTrackInfo[i].getFormat())));
                    } else {
                        this.mIndexTrackPairs.add(Pair.create(Integer.valueOf(i), null));
                    }
                }
            }
        }
    }

    public void addTimedTextSource(String str, String str2) throws IOException, IllegalArgumentException, IllegalStateException {
        if (!availableMimeTypeForExternalSource(str2)) {
            throw new IllegalArgumentException("Illegal mimeType for timed text source: " + str2);
        }
        FileInputStream fileInputStream = new FileInputStream(new File(str));
        try {
            try {
                addTimedTextSource(fileInputStream.getFD(), str2);
                fileInputStream.close();
            } catch (Throwable th) {
                try {
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } finally {
            fileInputStream.close();
        }
    }

    public void addTimedTextSource(Context context, Uri uri, String str) throws IOException, IllegalArgumentException, IllegalStateException {
        AssetFileDescriptor openAssetFileDescriptor;
        String scheme = uri.getScheme();
        if (scheme == null || scheme.equals("file")) {
            addTimedTextSource(uri.getPath(), str);
            return;
        }
        AssetFileDescriptor assetFileDescriptor = null;
        try {
            try {
                boolean z = SystemProperties.getBoolean("fuse.sys.transcode_player_optimize", false);
                ContentResolver contentResolver = context.getContentResolver();
                Bundle bundle = new Bundle();
                bundle.putBoolean("android.provider.extra.ACCEPT_ORIGINAL_MEDIA_FORMAT", true);
                if (z) {
                    openAssetFileDescriptor = contentResolver.openTypedAssetFileDescriptor(uri, "*/*", bundle);
                } else {
                    openAssetFileDescriptor = contentResolver.openAssetFileDescriptor(uri, "r");
                }
                assetFileDescriptor = openAssetFileDescriptor;
                if (assetFileDescriptor == null) {
                    if (assetFileDescriptor != null) {
                        assetFileDescriptor.close();
                    }
                } else {
                    addTimedTextSource(assetFileDescriptor.getFileDescriptor(), str);
                    if (assetFileDescriptor != null) {
                        assetFileDescriptor.close();
                    }
                }
            } catch (IOException e) {
                Log.d(TAG, "addTimedTextSource IOException happend : ", e);
                if (assetFileDescriptor != null) {
                    assetFileDescriptor.close();
                }
            } catch (SecurityException e2) {
                Log.d(TAG, "addTimedTextSource SecurityException happend : ", e2);
                if (assetFileDescriptor == null) {
                    return;
                }
                assetFileDescriptor.close();
            }
        } catch (Throwable th) {
            if (assetFileDescriptor != null) {
                assetFileDescriptor.close();
            }
            throw th;
        }
    }

    public void addTimedTextSource(FileDescriptor fileDescriptor, String str) throws IllegalArgumentException, IllegalStateException {
        addTimedTextSource(fileDescriptor, 0L, 576460752303423487L, str);
    }

    public void addTimedTextSource(FileDescriptor fileDescriptor, final long j, final long j2, String str) throws IllegalArgumentException, IllegalStateException {
        if (!availableMimeTypeForExternalSource(str)) {
            throw new IllegalArgumentException("Illegal mimeType for timed text source: " + str);
        }
        try {
            final FileDescriptor dup = Os.dup(fileDescriptor);
            MediaFormat mediaFormat = new MediaFormat();
            mediaFormat.setString("mime", str);
            mediaFormat.setInteger(MediaFormat.KEY_IS_TIMED_TEXT, 1);
            if (this.mSubtitleController == null) {
                setSubtitleAnchor();
            }
            if (!this.mSubtitleController.hasRendererFor(mediaFormat)) {
                this.mSubtitleController.registerRenderer(new SRTRenderer(ActivityThread.currentApplication(), this.mEventHandler));
            }
            final SubtitleTrack addTrack = this.mSubtitleController.addTrack(mediaFormat);
            synchronized (this.mIndexTrackPairs) {
                this.mIndexTrackPairs.add(Pair.create(null, addTrack));
            }
            getMediaTimeProvider();
            final HandlerThread handlerThread = new HandlerThread("TimedTextReadThread", 9);
            handlerThread.start();
            new Handler(handlerThread.getLooper()).post(new Runnable() { // from class: android.media.MediaPlayer.5
                private int addTrack() {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    try {
                        try {
                            Os.lseek(dup, j, OsConstants.SEEK_SET);
                            byte[] bArr = new byte[4096];
                            long j3 = 0;
                            while (true) {
                                long j4 = j2;
                                if (j3 >= j4) {
                                    break;
                                }
                                int read = IoBridge.read(dup, bArr, 0, (int) Math.min(4096, j4 - j3));
                                if (read < 0) {
                                    break;
                                }
                                byteArrayOutputStream.write(bArr, 0, read);
                                j3 += read;
                            }
                            synchronized (MediaPlayer.this.mTimeProviderLock) {
                                if (MediaPlayer.this.mTimeProvider != null) {
                                    Handler handler = MediaPlayer.this.mTimeProvider.mEventHandler;
                                    handler.sendMessage(handler.obtainMessage(2, 4, 0, Pair.create(addTrack, byteArrayOutputStream.toByteArray())));
                                }
                            }
                            try {
                                return 803;
                            } catch (ErrnoException e) {
                                return 803;
                            }
                        } catch (Exception e2) {
                            Log.e(MediaPlayer.TAG, e2.getMessage(), e2);
                            try {
                                Os.close(dup);
                                return 900;
                            } catch (ErrnoException e3) {
                                Log.e(MediaPlayer.TAG, e3.getMessage(), e3);
                                return 900;
                            }
                        }
                    } finally {
                        try {
                            Os.close(dup);
                        } catch (ErrnoException e4) {
                            Log.e(MediaPlayer.TAG, e4.getMessage(), e4);
                        }
                    }
                }

                @Override // java.lang.Runnable
                public void run() {
                    int addTrack2 = addTrack();
                    if (MediaPlayer.this.mEventHandler != null) {
                        MediaPlayer.this.mEventHandler.sendMessage(MediaPlayer.this.mEventHandler.obtainMessage(200, addTrack2, 0, null));
                    }
                    handlerThread.getLooper().quitSafely();
                }
            });
        } catch (ErrnoException e) {
            Log.e(TAG, e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public int getSelectedTrack(int i) throws IllegalStateException {
        SubtitleTrack selectedTrack;
        SubtitleController subtitleController = this.mSubtitleController;
        if (subtitleController != null && ((i == 4 || i == 3) && (selectedTrack = subtitleController.getSelectedTrack()) != null)) {
            synchronized (this.mIndexTrackPairs) {
                for (int i2 = 0; i2 < this.mIndexTrackPairs.size(); i2++) {
                    if (this.mIndexTrackPairs.get(i2).second == selectedTrack && selectedTrack.getTrackType() == i) {
                        return i2;
                    }
                }
            }
        }
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken(IMEDIA_PLAYER);
            obtain.writeInt(7);
            obtain.writeInt(i);
            invoke(obtain, obtain2);
            int readInt = obtain2.readInt();
            synchronized (this.mIndexTrackPairs) {
                for (int i3 = 0; i3 < this.mIndexTrackPairs.size(); i3++) {
                    Pair<Integer, SubtitleTrack> pair = this.mIndexTrackPairs.get(i3);
                    if (pair.first != null && pair.first.intValue() == readInt) {
                        return i3;
                    }
                }
                obtain.recycle();
                obtain2.recycle();
                return -1;
            }
        } finally {
            obtain.recycle();
            obtain2.recycle();
        }
    }

    public void selectTrack(int i) throws IllegalStateException {
        selectOrDeselectTrack(i, true);
    }

    public void deselectTrack(int i) throws IllegalStateException {
        selectOrDeselectTrack(i, false);
    }

    private void selectOrDeselectTrack(int i, boolean z) throws IllegalStateException {
        populateInbandTracks();
        try {
            Pair<Integer, SubtitleTrack> pair = this.mIndexTrackPairs.get(i);
            SubtitleTrack subtitleTrack = pair.second;
            if (subtitleTrack == null) {
                selectOrDeselectInbandTrack(pair.first.intValue(), z);
                return;
            }
            SubtitleController subtitleController = this.mSubtitleController;
            if (subtitleController == null) {
                return;
            }
            if (!z) {
                if (subtitleController.getSelectedTrack() == subtitleTrack) {
                    this.mSubtitleController.selectTrack(null);
                    return;
                } else {
                    Log.w(TAG, "trying to deselect track that was not selected");
                    return;
                }
            }
            if (subtitleTrack.getTrackType() == 3) {
                int selectedTrack = getSelectedTrack(3);
                synchronized (this.mIndexTrackPairs) {
                    if (selectedTrack >= 0) {
                        if (selectedTrack < this.mIndexTrackPairs.size()) {
                            Pair<Integer, SubtitleTrack> pair2 = this.mIndexTrackPairs.get(selectedTrack);
                            if (pair2.first != null && pair2.second == null) {
                                selectOrDeselectInbandTrack(pair2.first.intValue(), false);
                            }
                        }
                    }
                }
            }
            this.mSubtitleController.selectTrack(subtitleTrack);
        } catch (ArrayIndexOutOfBoundsException unused) {
        }
    }

    private void selectOrDeselectInbandTrack(int i, boolean z) throws IllegalStateException {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken(IMEDIA_PLAYER);
            obtain.writeInt(z ? 4 : 5);
            obtain.writeInt(i);
            invoke(obtain, obtain2);
        } finally {
            obtain.recycle();
            obtain2.recycle();
        }
    }

    public void setRetransmitEndpoint(InetSocketAddress inetSocketAddress) throws IllegalStateException, IllegalArgumentException {
        String str;
        int i;
        if (inetSocketAddress != null) {
            str = inetSocketAddress.getAddress().getHostAddress();
            i = inetSocketAddress.getPort();
        } else {
            str = null;
            i = 0;
        }
        int native_setRetransmitEndpoint = native_setRetransmitEndpoint(str, i);
        if (native_setRetransmitEndpoint == 0) {
            return;
        }
        throw new IllegalArgumentException("Illegal re-transmit endpoint; native ret " + native_setRetransmitEndpoint);
    }

    protected void finalize() {
        tryToDisableNativeRoutingCallback();
        baseRelease();
        native_finalize();
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

    /* JADX INFO: Access modifiers changed from: private */
    class EventHandler extends Handler {
        private MediaPlayer mMediaPlayer;

        public EventHandler(MediaPlayer mediaPlayer, Looper looper) {
            super(looper);
            this.mMediaPlayer = mediaPlayer;
        }

        /* JADX WARN: Finally extract failed */
        /* JADX WARN: Removed duplicated region for block: B:65:0x0127  */
        /* JADX WARN: Removed duplicated region for block: B:67:? A[RETURN, SYNTHETIC] */
        @Override // android.os.Handler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void handleMessage(android.os.Message r11) {
            /*
                Method dump skipped, instructions count: 1128
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: android.media.MediaPlayer.EventHandler.handleMessage(android.os.Message):void");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$handleMessage$0(OnRtpRxNoticeListener onRtpRxNoticeListener, int i, int[] iArr) {
            onRtpRxNoticeListener.onRtpRxNotice(this.mMediaPlayer, i, iArr);
        }
    }

    private static void postEventFromNative(Object obj, int i, int i2, int i3, Object obj2) {
        MediaPlayer mediaPlayer = (MediaPlayer) ((WeakReference) obj).get();
        if (mediaPlayer == null) {
            return;
        }
        if (i == 1) {
            synchronized (mediaPlayer.mDrmLock) {
                mediaPlayer.mDrmInfoResolved = true;
            }
        } else if (i != 200) {
            if (i == 210) {
                Log.v(TAG, "postEventFromNative MEDIA_DRM_INFO");
                if (obj2 instanceof Parcel) {
                    DrmInfo drmInfo = new DrmInfo((Parcel) obj2);
                    synchronized (mediaPlayer.mDrmLock) {
                        mediaPlayer.mDrmInfo = drmInfo;
                    }
                } else {
                    Log.w(TAG, "MEDIA_DRM_INFO msg.obj of unexpected type " + obj2);
                }
            }
        } else if (i2 == 2) {
            new Thread(new Runnable() { // from class: android.media.MediaPlayer.6
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        MediaPlayer.this.start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            Thread.yield();
        }
        EventHandler eventHandler = mediaPlayer.mEventHandler;
        if (eventHandler != null) {
            mediaPlayer.mEventHandler.sendMessage(eventHandler.obtainMessage(i, i2, i3, obj2));
        }
    }

    public void setOnPreparedListener(OnPreparedListener onPreparedListener) {
        this.mOnPreparedListener = onPreparedListener;
    }

    public void setOnCompletionListener(OnCompletionListener onCompletionListener) {
        this.mOnCompletionListener = onCompletionListener;
    }

    public void setOnBufferingUpdateListener(OnBufferingUpdateListener onBufferingUpdateListener) {
        this.mOnBufferingUpdateListener = onBufferingUpdateListener;
    }

    public void setOnSeekCompleteListener(OnSeekCompleteListener onSeekCompleteListener) {
        this.mOnSeekCompleteListener = onSeekCompleteListener;
    }

    public void setOnVideoSizeChangedListener(OnVideoSizeChangedListener onVideoSizeChangedListener) {
        this.mOnVideoSizeChangedListener = onVideoSizeChangedListener;
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

    public void setOnMediaTimeDiscontinuityListener(OnMediaTimeDiscontinuityListener onMediaTimeDiscontinuityListener, Handler handler) {
        if (onMediaTimeDiscontinuityListener == null) {
            throw new IllegalArgumentException("Illegal null listener");
        }
        if (handler == null) {
            throw new IllegalArgumentException("Illegal null handler");
        }
        setOnMediaTimeDiscontinuityListenerInt(onMediaTimeDiscontinuityListener, handler);
    }

    public void setOnMediaTimeDiscontinuityListener(OnMediaTimeDiscontinuityListener onMediaTimeDiscontinuityListener) {
        if (onMediaTimeDiscontinuityListener == null) {
            throw new IllegalArgumentException("Illegal null listener");
        }
        setOnMediaTimeDiscontinuityListenerInt(onMediaTimeDiscontinuityListener, null);
    }

    public void clearOnMediaTimeDiscontinuityListener() {
        setOnMediaTimeDiscontinuityListenerInt(null, null);
    }

    private void setOnMediaTimeDiscontinuityListenerInt(OnMediaTimeDiscontinuityListener onMediaTimeDiscontinuityListener, Handler handler) {
        synchronized (this) {
            this.mOnMediaTimeDiscontinuityListener = onMediaTimeDiscontinuityListener;
            this.mOnMediaTimeDiscontinuityHandler = handler;
        }
    }

    @SystemApi
    public void setOnRtpRxNoticeListener(Context context, Executor executor, OnRtpRxNoticeListener onRtpRxNoticeListener) {
        Objects.requireNonNull(context);
        Preconditions.checkArgument(context.checkSelfPermission(Manifest.permission.BIND_IMS_SERVICE) == 0, "android.permission.BIND_IMS_SERVICE permission not granted.");
        this.mOnRtpRxNoticeListener = (OnRtpRxNoticeListener) Objects.requireNonNull(onRtpRxNoticeListener);
        this.mOnRtpRxNoticeExecutor = (Executor) Objects.requireNonNull(executor);
    }

    public void setOnTimedMetaDataAvailableListener(OnTimedMetaDataAvailableListener onTimedMetaDataAvailableListener) {
        this.mOnTimedMetaDataAvailableListener = onTimedMetaDataAvailableListener;
    }

    public void setOnPlayReadyErrorListener(OnPlayReadyErrorListener onPlayReadyErrorListener) {
        this.mOnPlayReadyErrorListener = onPlayReadyErrorListener;
    }

    public void setOnErrorListener(OnErrorListener onErrorListener) {
        this.mOnErrorListener = onErrorListener;
    }

    public void setOnInfoListener(OnInfoListener onInfoListener) {
        this.mOnInfoListener = onInfoListener;
    }

    public void setOnDrmConfigHelper(OnDrmConfigHelper onDrmConfigHelper) {
        synchronized (this.mDrmLock) {
            this.mOnDrmConfigHelper = onDrmConfigHelper;
        }
    }

    public void setOnDrmInfoListener(OnDrmInfoListener onDrmInfoListener) {
        setOnDrmInfoListener(onDrmInfoListener, null);
    }

    public void setOnDrmInfoListener(OnDrmInfoListener onDrmInfoListener, Handler handler) {
        synchronized (this.mDrmLock) {
            if (onDrmInfoListener != null) {
                this.mOnDrmInfoHandlerDelegate = new OnDrmInfoHandlerDelegate(this, this, onDrmInfoListener, handler);
            } else {
                this.mOnDrmInfoHandlerDelegate = null;
            }
        }
    }

    public void setOnDrmPreparedListener(OnDrmPreparedListener onDrmPreparedListener) {
        setOnDrmPreparedListener(onDrmPreparedListener, null);
    }

    public void setOnDrmPreparedListener(OnDrmPreparedListener onDrmPreparedListener, Handler handler) {
        synchronized (this.mDrmLock) {
            if (onDrmPreparedListener != null) {
                this.mOnDrmPreparedHandlerDelegate = new OnDrmPreparedHandlerDelegate(this, this, onDrmPreparedListener, handler);
            } else {
                this.mOnDrmPreparedHandlerDelegate = null;
            }
        }
    }

    private class OnDrmInfoHandlerDelegate {
        private Handler mHandler;
        private MediaPlayer mMediaPlayer;
        private OnDrmInfoListener mOnDrmInfoListener;

        OnDrmInfoHandlerDelegate(MediaPlayer mediaPlayer, MediaPlayer mediaPlayer2, OnDrmInfoListener onDrmInfoListener, Handler handler) {
            this.mMediaPlayer = mediaPlayer2;
            this.mOnDrmInfoListener = onDrmInfoListener;
            if (handler != null) {
                this.mHandler = handler;
            }
        }

        void notifyClient(final DrmInfo drmInfo) {
            Handler handler = this.mHandler;
            if (handler != null) {
                handler.post(new Runnable() { // from class: android.media.MediaPlayer.OnDrmInfoHandlerDelegate.1
                    @Override // java.lang.Runnable
                    public void run() {
                        OnDrmInfoHandlerDelegate.this.mOnDrmInfoListener.onDrmInfo(OnDrmInfoHandlerDelegate.this.mMediaPlayer, drmInfo);
                    }
                });
            } else {
                this.mOnDrmInfoListener.onDrmInfo(this.mMediaPlayer, drmInfo);
            }
        }
    }

    private class OnDrmPreparedHandlerDelegate {
        private Handler mHandler;
        private MediaPlayer mMediaPlayer;
        private OnDrmPreparedListener mOnDrmPreparedListener;

        OnDrmPreparedHandlerDelegate(MediaPlayer mediaPlayer, MediaPlayer mediaPlayer2, OnDrmPreparedListener onDrmPreparedListener, Handler handler) {
            this.mMediaPlayer = mediaPlayer2;
            this.mOnDrmPreparedListener = onDrmPreparedListener;
            if (handler != null) {
                this.mHandler = handler;
            } else if (mediaPlayer.mEventHandler != null) {
                this.mHandler = mediaPlayer.mEventHandler;
            } else {
                Log.e(MediaPlayer.TAG, "OnDrmPreparedHandlerDelegate: Unexpected null mEventHandler");
            }
        }

        void notifyClient(final int i) {
            Handler handler = this.mHandler;
            if (handler != null) {
                handler.post(new Runnable() { // from class: android.media.MediaPlayer.OnDrmPreparedHandlerDelegate.1
                    @Override // java.lang.Runnable
                    public void run() {
                        OnDrmPreparedHandlerDelegate.this.mOnDrmPreparedListener.onDrmPrepared(OnDrmPreparedHandlerDelegate.this.mMediaPlayer, i);
                    }
                });
            } else {
                Log.e(MediaPlayer.TAG, "OnDrmPreparedHandlerDelegate:notifyClient: Unexpected null mHandler");
            }
        }
    }

    public DrmInfo getDrmInfo() {
        DrmInfo makeCopy;
        synchronized (this.mDrmLock) {
            if (!this.mDrmInfoResolved && this.mDrmInfo == null) {
                Log.v(TAG, "The Player has not been prepared yet");
                throw new IllegalStateException("The Player has not been prepared yet");
            }
            DrmInfo drmInfo = this.mDrmInfo;
            makeCopy = drmInfo != null ? drmInfo.makeCopy() : null;
        }
        return makeCopy;
    }

    public void prepareDrm(UUID uuid) throws UnsupportedSchemeException, ResourceBusyException, ProvisioningNetworkErrorException, ProvisioningServerErrorException {
        boolean z;
        OnDrmPreparedHandlerDelegate onDrmPreparedHandlerDelegate;
        Log.v(TAG, "prepareDrm: uuid: " + uuid + " mOnDrmConfigHelper: " + this.mOnDrmConfigHelper);
        synchronized (this.mDrmLock) {
            if (this.mDrmInfo == null) {
                Log.e(TAG, "prepareDrm(): Wrong usage: The player must be prepared and DRM info be retrieved before this call.");
                throw new IllegalStateException("prepareDrm(): Wrong usage: The player must be prepared and DRM info be retrieved before this call.");
            }
            if (this.mActiveDrmScheme) {
                String str = "prepareDrm(): Wrong usage: There is already an active DRM scheme with " + this.mDrmUUID;
                Log.e(TAG, str);
                throw new IllegalStateException(str);
            }
            if (this.mPrepareDrmInProgress) {
                Log.e(TAG, "prepareDrm(): Wrong usage: There is already a pending prepareDrm call.");
                throw new IllegalStateException("prepareDrm(): Wrong usage: There is already a pending prepareDrm call.");
            }
            if (this.mDrmProvisioningInProgress) {
                Log.e(TAG, "prepareDrm(): Unexpectd: Provisioning is already in progress.");
                throw new IllegalStateException("prepareDrm(): Unexpectd: Provisioning is already in progress.");
            }
            cleanDrmObj();
            z = true;
            this.mPrepareDrmInProgress = true;
            onDrmPreparedHandlerDelegate = this.mOnDrmPreparedHandlerDelegate;
            try {
                prepareDrm_createDrmStep(uuid);
                this.mDrmConfigAllowed = true;
            } catch (Exception e) {
                Log.w(TAG, "prepareDrm(): Exception ", e);
                this.mPrepareDrmInProgress = false;
                throw e;
            }
        }
        OnDrmConfigHelper onDrmConfigHelper = this.mOnDrmConfigHelper;
        if (onDrmConfigHelper != null) {
            onDrmConfigHelper.onDrmConfig(this);
        }
        synchronized (this.mDrmLock) {
            try {
                this.mDrmConfigAllowed = false;
            } catch (Throwable th) {
                th = th;
                z = false;
            }
            try {
                try {
                    try {
                        prepareDrm_openSessionStep(uuid);
                        this.mDrmUUID = uuid;
                        this.mActiveDrmScheme = true;
                        if (!this.mDrmProvisioningInProgress) {
                            this.mPrepareDrmInProgress = false;
                        }
                    } catch (NotProvisionedException unused) {
                        Log.w(TAG, "prepareDrm: NotProvisionedException");
                        int HandleProvisioninig = HandleProvisioninig(uuid);
                        if (HandleProvisioninig != 0) {
                            if (HandleProvisioninig == 1) {
                                Log.e(TAG, "prepareDrm: Provisioning was required but failed due to a network error.");
                                throw new ProvisioningNetworkErrorException("prepareDrm: Provisioning was required but failed due to a network error.");
                            }
                            if (HandleProvisioninig == 2) {
                                Log.e(TAG, "prepareDrm: Provisioning was required but the request was denied by the server.");
                                throw new ProvisioningServerErrorException("prepareDrm: Provisioning was required but the request was denied by the server.");
                            }
                            Log.e(TAG, "prepareDrm: Post-provisioning preparation failed.");
                            throw new IllegalStateException("prepareDrm: Post-provisioning preparation failed.");
                        }
                        if (!this.mDrmProvisioningInProgress) {
                            this.mPrepareDrmInProgress = false;
                        }
                        z = false;
                    } catch (Exception e2) {
                        Log.e(TAG, "prepareDrm: Exception " + e2);
                        throw e2;
                    }
                } catch (IllegalStateException unused2) {
                    Log.e(TAG, "prepareDrm(): Wrong usage: The player must be in the prepared state to call prepareDrm().");
                    throw new IllegalStateException("prepareDrm(): Wrong usage: The player must be in the prepared state to call prepareDrm().");
                }
            } catch (Throwable th2) {
                th = th2;
                if (!this.mDrmProvisioningInProgress) {
                    this.mPrepareDrmInProgress = false;
                }
                if (z) {
                    cleanDrmObj();
                }
                throw th;
            }
        }
        if (!z || onDrmPreparedHandlerDelegate == null) {
            return;
        }
        onDrmPreparedHandlerDelegate.notifyClient(0);
    }

    public void releaseDrm() throws NoDrmSchemeException {
        Log.v(TAG, "releaseDrm:");
        synchronized (this.mDrmLock) {
            if (!this.mActiveDrmScheme) {
                Log.e(TAG, "releaseDrm(): No active DRM scheme to release.");
                throw new NoDrmSchemeException("releaseDrm: No active DRM scheme to release.");
            }
            try {
                try {
                    _releaseDrm();
                    cleanDrmObj();
                    this.mActiveDrmScheme = false;
                } catch (Exception e) {
                    Log.e(TAG, "releaseDrm: Exception ", e);
                }
            } catch (IllegalStateException e2) {
                Log.w(TAG, "releaseDrm: Exception ", e2);
                throw new IllegalStateException("releaseDrm: The player is not in a valid state.");
            }
        }
    }

    public MediaDrm.KeyRequest getKeyRequest(byte[] bArr, byte[] bArr2, String str, int i, Map<String, String> map) throws NoDrmSchemeException {
        MediaDrm.KeyRequest keyRequest;
        Log.v(TAG, "getKeyRequest:  keySetId: " + Arrays.toString(bArr) + " initData:" + Arrays.toString(bArr2) + " mimeType: " + str + " keyType: " + i + " optionalParameters: " + map);
        synchronized (this.mDrmLock) {
            if (!this.mActiveDrmScheme) {
                Log.e(TAG, "getKeyRequest NoDrmSchemeException");
                throw new NoDrmSchemeException("getKeyRequest: Has to set a DRM scheme first.");
            }
            if (i != 3) {
                try {
                    bArr = this.mDrmSessionId;
                } catch (NotProvisionedException unused) {
                    Log.w(TAG, "getKeyRequest NotProvisionedException: Unexpected. Shouldn't have reached here.");
                    throw new IllegalStateException("getKeyRequest: Unexpected provisioning error.");
                } catch (Exception e) {
                    Log.w(TAG, "getKeyRequest Exception " + e);
                    throw e;
                }
            }
            keyRequest = this.mDrmObj.getKeyRequest(bArr, bArr2, str, i, map != null ? new HashMap<>(map) : null);
            Log.v(TAG, "getKeyRequest:   --> request: " + keyRequest);
        }
        return keyRequest;
    }

    public byte[] provideKeyResponse(byte[] bArr, byte[] bArr2) throws NoDrmSchemeException, DeniedByServerException {
        byte[] bArr3;
        byte[] provideKeyResponse;
        Log.v(TAG, "provideKeyResponse: keySetId: " + Arrays.toString(bArr) + " response: " + Arrays.toString(bArr2));
        synchronized (this.mDrmLock) {
            if (!this.mActiveDrmScheme) {
                Log.e(TAG, "getKeyRequest NoDrmSchemeException");
                throw new NoDrmSchemeException("getKeyRequest: Has to set a DRM scheme first.");
            }
            if (bArr == null) {
                try {
                    bArr3 = this.mDrmSessionId;
                } catch (NotProvisionedException unused) {
                    Log.w(TAG, "provideKeyResponse NotProvisionedException: Unexpected. Shouldn't have reached here.");
                    throw new IllegalStateException("provideKeyResponse: Unexpected provisioning error.");
                } catch (Exception e) {
                    Log.w(TAG, "provideKeyResponse Exception " + e);
                    throw e;
                }
            } else {
                bArr3 = bArr;
            }
            provideKeyResponse = this.mDrmObj.provideKeyResponse(bArr3, bArr2);
            Log.v(TAG, "provideKeyResponse: keySetId: " + Arrays.toString(bArr) + " response: " + Arrays.toString(bArr2) + " --> " + Arrays.toString(provideKeyResponse));
        }
        return provideKeyResponse;
    }

    public void restoreKeys(byte[] bArr) throws NoDrmSchemeException {
        Log.v(TAG, "restoreKeys: keySetId: " + Arrays.toString(bArr));
        synchronized (this.mDrmLock) {
            if (!this.mActiveDrmScheme) {
                Log.w(TAG, "restoreKeys NoDrmSchemeException");
                throw new NoDrmSchemeException("restoreKeys: Has to set a DRM scheme first.");
            }
            try {
                this.mDrmObj.restoreKeys(this.mDrmSessionId, bArr);
            } catch (Exception e) {
                Log.w(TAG, "restoreKeys Exception " + e);
                throw e;
            }
        }
    }

    public String getDrmPropertyString(String str) throws NoDrmSchemeException {
        String propertyString;
        Log.v(TAG, "getDrmPropertyString: propertyName: " + str);
        synchronized (this.mDrmLock) {
            if (!this.mActiveDrmScheme && !this.mDrmConfigAllowed) {
                Log.w(TAG, "getDrmPropertyString NoDrmSchemeException");
                throw new NoDrmSchemeException("getDrmPropertyString: Has to prepareDrm() first.");
            }
            try {
                propertyString = this.mDrmObj.getPropertyString(str);
            } catch (Exception e) {
                Log.w(TAG, "getDrmPropertyString Exception " + e);
                throw e;
            }
        }
        Log.v(TAG, "getDrmPropertyString: propertyName: " + str + " --> value: " + propertyString);
        return propertyString;
    }

    public void setDrmPropertyString(String str, String str2) throws NoDrmSchemeException {
        Log.v(TAG, "setDrmPropertyString: propertyName: " + str + " value: " + str2);
        synchronized (this.mDrmLock) {
            if (!this.mActiveDrmScheme && !this.mDrmConfigAllowed) {
                Log.w(TAG, "setDrmPropertyString NoDrmSchemeException");
                throw new NoDrmSchemeException("setDrmPropertyString: Has to prepareDrm() first.");
            }
            try {
                this.mDrmObj.setPropertyString(str, str2);
            } catch (Exception e) {
                Log.w(TAG, "setDrmPropertyString Exception " + e);
                throw e;
            }
        }
    }

    public static final class DrmInfo {
        private Map<UUID, byte[]> mapPssh;
        private UUID[] supportedSchemes;

        public Map<UUID, byte[]> getPssh() {
            return this.mapPssh;
        }

        public UUID[] getSupportedSchemes() {
            return this.supportedSchemes;
        }

        private DrmInfo(Map<UUID, byte[]> map, UUID[] uuidArr) {
            this.mapPssh = map;
            this.supportedSchemes = uuidArr;
        }

        private DrmInfo(Parcel parcel) {
            Log.v(MediaPlayer.TAG, "DrmInfo(" + parcel + ") size " + parcel.dataSize());
            int readInt = parcel.readInt();
            byte[] bArr = new byte[readInt];
            parcel.readByteArray(bArr);
            Log.v(MediaPlayer.TAG, "DrmInfo() PSSH: " + arrToHex(bArr));
            this.mapPssh = parsePSSH(bArr, readInt);
            Log.v(MediaPlayer.TAG, "DrmInfo() PSSH: " + this.mapPssh);
            int readInt2 = parcel.readInt();
            this.supportedSchemes = new UUID[readInt2];
            for (int i = 0; i < readInt2; i++) {
                byte[] bArr2 = new byte[16];
                parcel.readByteArray(bArr2);
                this.supportedSchemes[i] = bytesToUUID(bArr2);
                Log.v(MediaPlayer.TAG, "DrmInfo() supportedScheme[" + i + "]: " + this.supportedSchemes[i]);
            }
            Log.v(MediaPlayer.TAG, "DrmInfo() Parcel psshsize: " + readInt + " supportedDRMsCount: " + readInt2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public DrmInfo makeCopy() {
            return new DrmInfo(this.mapPssh, this.supportedSchemes);
        }

        private String arrToHex(byte[] bArr) {
            String str = "0x";
            for (byte b : bArr) {
                str = str + String.format("%02x", Byte.valueOf(b));
            }
            return str;
        }

        private UUID bytesToUUID(byte[] bArr) {
            long j = 0;
            long j2 = 0;
            for (int i = 0; i < 8; i++) {
                int i2 = (7 - i) * 8;
                j |= (bArr[i] & 255) << i2;
                j2 |= (bArr[i + 8] & 255) << i2;
            }
            return new UUID(j, j2);
        }

        private Map<UUID, byte[]> parsePSSH(byte[] bArr, int i) {
            int i2;
            byte b;
            HashMap hashMap = new HashMap();
            int i3 = i;
            int i4 = 0;
            int i5 = 0;
            while (i3 > 0) {
                if (i3 < 16) {
                    Log.w(MediaPlayer.TAG, String.format("parsePSSH: len is too short to parse UUID: (%d < 16) pssh: %d", Integer.valueOf(i3), Integer.valueOf(i)));
                    return null;
                }
                int i6 = i4 + 16;
                UUID bytesToUUID = bytesToUUID(Arrays.copyOfRange(bArr, i4, i6));
                int i7 = i3 - 16;
                if (i7 < 4) {
                    Log.w(MediaPlayer.TAG, String.format("parsePSSH: len is too short to parse datalen: (%d < 4) pssh: %d", Integer.valueOf(i7), Integer.valueOf(i)));
                    return null;
                }
                int i8 = i4 + 20;
                byte[] copyOfRange = Arrays.copyOfRange(bArr, i6, i8);
                if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
                    i2 = ((copyOfRange[2] & 255) << 16) | ((copyOfRange[3] & 255) << 24) | ((copyOfRange[1] & 255) << 8);
                    b = copyOfRange[0];
                } else {
                    i2 = ((copyOfRange[1] & 255) << 16) | ((copyOfRange[0] & 255) << 24) | ((copyOfRange[2] & 255) << 8);
                    b = copyOfRange[3];
                }
                int i9 = i2 | (b & 255);
                int i10 = i3 - 20;
                if (i10 < i9) {
                    Log.w(MediaPlayer.TAG, String.format("parsePSSH: len is too short to parse data: (%d < %d) pssh: %d", Integer.valueOf(i10), Integer.valueOf(i9), Integer.valueOf(i)));
                    return null;
                }
                int i11 = i8 + i9;
                byte[] copyOfRange2 = Arrays.copyOfRange(bArr, i8, i11);
                i3 = i10 - i9;
                Log.v(MediaPlayer.TAG, String.format("parsePSSH[%d]: <%s, %s> pssh: %d", Integer.valueOf(i5), bytesToUUID, arrToHex(copyOfRange2), Integer.valueOf(i)));
                i5++;
                hashMap.put(bytesToUUID, copyOfRange2);
                i4 = i11;
            }
            return hashMap;
        }
    }

    public static final class NoDrmSchemeException extends MediaDrmException {
        public NoDrmSchemeException(String str) {
            super(str);
        }
    }

    public static final class ProvisioningNetworkErrorException extends MediaDrmException {
        public ProvisioningNetworkErrorException(String str) {
            super(str);
        }
    }

    public static final class ProvisioningServerErrorException extends MediaDrmException {
        public ProvisioningServerErrorException(String str) {
            super(str);
        }
    }

    private void prepareDrm_createDrmStep(UUID uuid) throws UnsupportedSchemeException {
        Log.v(TAG, "prepareDrm_createDrmStep: UUID: " + uuid);
        try {
            this.mDrmObj = new MediaDrm(uuid);
            Log.v(TAG, "prepareDrm_createDrmStep: Created mDrmObj=" + this.mDrmObj);
        } catch (Exception e) {
            Log.e(TAG, "prepareDrm_createDrmStep: MediaDrm failed with " + e);
            throw e;
        }
    }

    private void prepareDrm_openSessionStep(UUID uuid) throws NotProvisionedException, ResourceBusyException {
        Log.v(TAG, "prepareDrm_openSessionStep: uuid: " + uuid);
        try {
            this.mDrmSessionId = this.mDrmObj.openSession();
            Log.v(TAG, "prepareDrm_openSessionStep: mDrmSessionId=" + Arrays.toString(this.mDrmSessionId));
            _prepareDrm(getByteArrayFromUUID(uuid), this.mDrmSessionId);
            Log.v(TAG, "prepareDrm_openSessionStep: _prepareDrm/Crypto succeeded");
        } catch (Exception e) {
            Log.e(TAG, "prepareDrm_openSessionStep: open/crypto failed with " + e);
            throw e;
        }
    }

    private class ProvisioningThread extends Thread {
        public static final int TIMEOUT_MS = 60000;
        private Object drmLock;
        private boolean finished;
        private MediaPlayer mediaPlayer;
        private OnDrmPreparedHandlerDelegate onDrmPreparedHandlerDelegate;
        private int status;
        private String urlStr;
        private UUID uuid;

        private ProvisioningThread() {
        }

        public int status() {
            return this.status;
        }

        public ProvisioningThread initialize(MediaDrm.ProvisionRequest provisionRequest, UUID uuid, MediaPlayer mediaPlayer) {
            this.drmLock = mediaPlayer.mDrmLock;
            this.onDrmPreparedHandlerDelegate = mediaPlayer.mOnDrmPreparedHandlerDelegate;
            this.mediaPlayer = mediaPlayer;
            this.urlStr = provisionRequest.getDefaultUrl() + "&signedRequest=" + new String(provisionRequest.getData());
            this.uuid = uuid;
            this.status = 3;
            Log.v(MediaPlayer.TAG, "HandleProvisioninig: Thread is initialised url: " + this.urlStr);
            return this;
        }

        /* JADX WARN: Removed duplicated region for block: B:11:0x00bf  */
        /* JADX WARN: Removed duplicated region for block: B:32:0x00f0  */
        @Override // java.lang.Thread, java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void run() {
            /*
                Method dump skipped, instructions count: 277
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: android.media.MediaPlayer.ProvisioningThread.run():void");
        }
    }

    private int HandleProvisioninig(UUID uuid) {
        if (this.mDrmProvisioningInProgress) {
            Log.e(TAG, "HandleProvisioninig: Unexpected mDrmProvisioningInProgress");
            return 3;
        }
        MediaDrm.ProvisionRequest provisionRequest = this.mDrmObj.getProvisionRequest();
        if (provisionRequest == null) {
            Log.e(TAG, "HandleProvisioninig: getProvisionRequest returned null.");
            return 3;
        }
        Log.v(TAG, "HandleProvisioninig provReq  data: " + Arrays.toString(provisionRequest.getData()) + " url: " + provisionRequest.getDefaultUrl());
        this.mDrmProvisioningInProgress = true;
        ProvisioningThread initialize = new ProvisioningThread().initialize(provisionRequest, uuid, this);
        this.mDrmProvisioningThread = initialize;
        initialize.start();
        if (this.mOnDrmPreparedHandlerDelegate != null) {
            return 0;
        }
        try {
            this.mDrmProvisioningThread.join();
        } catch (Exception e) {
            Log.w(TAG, "HandleProvisioninig: Thread.join Exception " + e);
        }
        int status = this.mDrmProvisioningThread.status();
        this.mDrmProvisioningThread = null;
        return status;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean resumePrepareDrm(UUID uuid) {
        Log.v(TAG, "resumePrepareDrm: uuid: " + uuid);
        try {
            prepareDrm_openSessionStep(uuid);
            this.mDrmUUID = uuid;
            this.mActiveDrmScheme = true;
            return true;
        } catch (Exception e) {
            Log.w(TAG, "HandleProvisioninig: Thread run _prepareDrm resume failed with " + e);
            return false;
        }
    }

    private void resetDrmState() {
        synchronized (this.mDrmLock) {
            Log.v(TAG, "resetDrmState:  mDrmInfo=" + this.mDrmInfo + " mDrmProvisioningThread=" + this.mDrmProvisioningThread + " mPrepareDrmInProgress=" + this.mPrepareDrmInProgress + " mActiveDrmScheme=" + this.mActiveDrmScheme);
            this.mDrmInfoResolved = false;
            this.mDrmInfo = null;
            ProvisioningThread provisioningThread = this.mDrmProvisioningThread;
            if (provisioningThread != null) {
                try {
                    provisioningThread.join();
                } catch (InterruptedException e) {
                    Log.w(TAG, "resetDrmState: ProvThread.join Exception " + e);
                }
                this.mDrmProvisioningThread = null;
            }
            this.mPrepareDrmInProgress = false;
            this.mActiveDrmScheme = false;
            cleanDrmObj();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cleanDrmObj() {
        Log.v(TAG, "cleanDrmObj: mDrmObj=" + this.mDrmObj + " mDrmSessionId=" + Arrays.toString(this.mDrmSessionId));
        byte[] bArr = this.mDrmSessionId;
        if (bArr != null) {
            this.mDrmObj.closeSession(bArr);
            this.mDrmSessionId = null;
        }
        MediaDrm mediaDrm = this.mDrmObj;
        if (mediaDrm != null) {
            mediaDrm.release();
            this.mDrmObj = null;
        }
    }

    private static final byte[] getByteArrayFromUUID(UUID uuid) {
        long mostSignificantBits = uuid.getMostSignificantBits();
        long leastSignificantBits = uuid.getLeastSignificantBits();
        byte[] bArr = new byte[16];
        for (int i = 0; i < 8; i++) {
            int i2 = (7 - i) * 8;
            bArr[i] = (byte) (mostSignificantBits >>> i2);
            bArr[i + 8] = (byte) (leastSignificantBits >>> i2);
        }
        return bArr;
    }

    private void setGameVideoSpeed() {
        int parseInt;
        int duration;
        String str = SystemProperties.get("persist.sys.gvs.target", "");
        this.gvsTarget = str;
        if (!str.startsWith(this.packageName) || (duration = getDuration()) <= (parseInt = Integer.parseInt(this.gvsTarget.split(NativeLibraryHelper.CLEAR_ABI_OVERRIDE)[1])) || parseInt <= 0) {
            return;
        }
        float f = duration / parseInt;
        if (f >= 3.0f) {
            f = 3.0f;
        }
        Log.d(TAG, "speed: " + f);
        setPlaybackParams(getPlaybackParams().setSpeed(f));
        if (isPlaying()) {
            return;
        }
        pause();
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
        private MediaPlayer mPlayer;
        private boolean mRefresh;
        private long[] mTimes;
        private boolean mPaused = true;
        private boolean mStopped = true;
        private boolean mPausing = false;
        private boolean mSeeking = false;
        public boolean DEBUG = false;

        public TimeProvider(MediaPlayer mediaPlayer) {
            this.mLastTimeUs = 0L;
            this.mRefresh = false;
            this.mPlayer = mediaPlayer;
            try {
                getCurrentTimeUs(true, false);
            } catch (IllegalStateException unused) {
                this.mRefresh = true;
            }
            Looper myLooper = Looper.myLooper();
            if (myLooper == null && (myLooper = Looper.getMainLooper()) == null) {
                HandlerThread handlerThread = new HandlerThread("MediaPlayerMTPEventThread", -2);
                this.mHandlerThread = handlerThread;
                handlerThread.start();
                myLooper = this.mHandlerThread.getLooper();
            }
            this.mEventHandler = new EventHandler(myLooper);
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
                if (this.mStopped) {
                    this.mStopped = false;
                    this.mSeeking = true;
                    scheduleNotification(3, 0L);
                } else {
                    this.mPausing = z;
                    this.mSeeking = false;
                    scheduleNotification(0, 0L);
                }
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

        public void onStopped() {
            synchronized (this) {
                if (this.DEBUG) {
                    Log.d(TAG, "onStopped");
                }
                this.mPaused = true;
                this.mStopped = true;
                this.mSeeking = false;
                this.mBuffering = false;
                scheduleNotification(2, 0L);
            }
        }

        @Override // android.media.MediaPlayer.OnSeekCompleteListener
        public void onSeekComplete(MediaPlayer mediaPlayer) {
            synchronized (this) {
                this.mStopped = false;
                this.mSeeking = true;
                scheduleNotification(3, 0L);
            }
        }

        public void onNewPlayer() {
            if (this.mRefresh) {
                synchronized (this) {
                    this.mStopped = false;
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
                int registerListener = registerListener(onMediaTimeListener);
                if (!this.mStopped) {
                    this.mTimes[registerListener] = 0;
                    scheduleNotification(0, 0L);
                }
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

        /* JADX WARN: Removed duplicated region for block: B:21:0x0031 A[Catch: IllegalStateException -> 0x007a, all -> 0x00ad, TryCatch #0 {IllegalStateException -> 0x007a, blocks: (B:13:0x000f, B:15:0x0023, B:19:0x002b, B:21:0x0031, B:24:0x0041), top: B:12:0x000f, outer: #1 }] */
        /* JADX WARN: Removed duplicated region for block: B:28:0x0057 A[Catch: all -> 0x00ad, TRY_ENTER, TryCatch #1 {, blocks: (B:4:0x0003, B:7:0x0009, B:8:0x000b, B:13:0x000f, B:15:0x0023, B:19:0x002b, B:21:0x0031, B:24:0x0041, B:28:0x0057, B:30:0x005f, B:32:0x0067, B:33:0x0076, B:34:0x0078, B:36:0x0072, B:39:0x007b, B:41:0x007f, B:43:0x0083, B:45:0x008f, B:47:0x0095, B:48:0x00a8, B:49:0x00aa, B:51:0x008b, B:52:0x00ac), top: B:3:0x0003, inners: #0 }] */
        @Override // android.media.MediaTimeProvider
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public long getCurrentTimeUs(boolean r7, boolean r8) throws java.lang.IllegalStateException {
            /*
                r6 = this;
                java.lang.String r0 = "illegal state, but pausing: estimating at "
                monitor-enter(r6)
                boolean r1 = r6.mPaused     // Catch: java.lang.Throwable -> Lad
                if (r1 == 0) goto Ld
                if (r7 != 0) goto Ld
                long r7 = r6.mLastReportedTime     // Catch: java.lang.Throwable -> Lad
                monitor-exit(r6)     // Catch: java.lang.Throwable -> Lad
                return r7
            Ld:
                r7 = 1
                r1 = 0
                android.media.MediaPlayer r2 = r6.mPlayer     // Catch: java.lang.IllegalStateException -> L7a java.lang.Throwable -> Lad
                int r2 = r2.getCurrentPosition()     // Catch: java.lang.IllegalStateException -> L7a java.lang.Throwable -> Lad
                long r2 = (long) r2     // Catch: java.lang.IllegalStateException -> L7a java.lang.Throwable -> Lad
                r4 = 1000(0x3e8, double:4.94E-321)
                long r2 = r2 * r4
                r6.mLastTimeUs = r2     // Catch: java.lang.IllegalStateException -> L7a java.lang.Throwable -> Lad
                android.media.MediaPlayer r2 = r6.mPlayer     // Catch: java.lang.IllegalStateException -> L7a java.lang.Throwable -> Lad
                boolean r2 = r2.isPlaying()     // Catch: java.lang.IllegalStateException -> L7a java.lang.Throwable -> Lad
                if (r2 == 0) goto L2a
                boolean r2 = r6.mBuffering     // Catch: java.lang.IllegalStateException -> L7a java.lang.Throwable -> Lad
                if (r2 == 0) goto L28
                goto L2a
            L28:
                r2 = r1
                goto L2b
            L2a:
                r2 = r7
            L2b:
                r6.mPaused = r2     // Catch: java.lang.IllegalStateException -> L7a java.lang.Throwable -> Lad
                boolean r2 = r6.DEBUG     // Catch: java.lang.IllegalStateException -> L7a java.lang.Throwable -> Lad
                if (r2 == 0) goto L55
                java.lang.String r2 = "MTP"
                java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.IllegalStateException -> L7a java.lang.Throwable -> Lad
                r3.<init>()     // Catch: java.lang.IllegalStateException -> L7a java.lang.Throwable -> Lad
                boolean r4 = r6.mPaused     // Catch: java.lang.IllegalStateException -> L7a java.lang.Throwable -> Lad
                if (r4 == 0) goto L3f
                java.lang.String r4 = "paused"
                goto L41
            L3f:
                java.lang.String r4 = "playing"
            L41:
                r3.append(r4)     // Catch: java.lang.IllegalStateException -> L7a java.lang.Throwable -> Lad
                java.lang.String r4 = " at "
                r3.append(r4)     // Catch: java.lang.IllegalStateException -> L7a java.lang.Throwable -> Lad
                long r4 = r6.mLastTimeUs     // Catch: java.lang.IllegalStateException -> L7a java.lang.Throwable -> Lad
                r3.append(r4)     // Catch: java.lang.IllegalStateException -> L7a java.lang.Throwable -> Lad
                java.lang.String r3 = r3.toString()     // Catch: java.lang.IllegalStateException -> L7a java.lang.Throwable -> Lad
                android.util.Log.v(r2, r3)     // Catch: java.lang.IllegalStateException -> L7a java.lang.Throwable -> Lad
            L55:
                if (r8 == 0) goto L72
                long r2 = r6.mLastTimeUs     // Catch: java.lang.Throwable -> Lad
                long r4 = r6.mLastReportedTime     // Catch: java.lang.Throwable -> Lad
                int r8 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
                if (r8 >= 0) goto L72
                long r4 = r4 - r2
                r2 = 1000000(0xf4240, double:4.940656E-318)
                int r8 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
                if (r8 <= 0) goto L76
                r6.mStopped = r1     // Catch: java.lang.Throwable -> Lad
                r6.mSeeking = r7     // Catch: java.lang.Throwable -> Lad
                r7 = 3
                r0 = 0
                r6.scheduleNotification(r7, r0)     // Catch: java.lang.Throwable -> Lad
                goto L76
            L72:
                long r7 = r6.mLastTimeUs     // Catch: java.lang.Throwable -> Lad
                r6.mLastReportedTime = r7     // Catch: java.lang.Throwable -> Lad
            L76:
                long r7 = r6.mLastReportedTime     // Catch: java.lang.Throwable -> Lad
                monitor-exit(r6)     // Catch: java.lang.Throwable -> Lad
                return r7
            L7a:
                r2 = move-exception
                boolean r3 = r6.mPausing     // Catch: java.lang.Throwable -> Lad
                if (r3 == 0) goto Lac
                r6.mPausing = r1     // Catch: java.lang.Throwable -> Lad
                if (r8 == 0) goto L8b
                long r1 = r6.mLastReportedTime     // Catch: java.lang.Throwable -> Lad
                long r3 = r6.mLastTimeUs     // Catch: java.lang.Throwable -> Lad
                int r8 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
                if (r8 >= 0) goto L8f
            L8b:
                long r1 = r6.mLastTimeUs     // Catch: java.lang.Throwable -> Lad
                r6.mLastReportedTime = r1     // Catch: java.lang.Throwable -> Lad
            L8f:
                r6.mPaused = r7     // Catch: java.lang.Throwable -> Lad
                boolean r7 = r6.DEBUG     // Catch: java.lang.Throwable -> Lad
                if (r7 == 0) goto La8
                java.lang.String r7 = "MTP"
                java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lad
                r8.<init>(r0)     // Catch: java.lang.Throwable -> Lad
                long r0 = r6.mLastReportedTime     // Catch: java.lang.Throwable -> Lad
                r8.append(r0)     // Catch: java.lang.Throwable -> Lad
                java.lang.String r8 = r8.toString()     // Catch: java.lang.Throwable -> Lad
                android.util.Log.d(r7, r8)     // Catch: java.lang.Throwable -> Lad
            La8:
                long r7 = r6.mLastReportedTime     // Catch: java.lang.Throwable -> Lad
                monitor-exit(r6)     // Catch: java.lang.Throwable -> Lad
                return r7
            Lac:
                throw r2     // Catch: java.lang.Throwable -> Lad
            Lad:
                r7 = move-exception
                monitor-exit(r6)     // Catch: java.lang.Throwable -> Lad
                throw r7
            */
            throw new UnsupportedOperationException("Method not decompiled: android.media.MediaPlayer.TimeProvider.getCurrentTimeUs(boolean, boolean):long");
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
                if (message.what == 2 && message.arg1 == 4) {
                    TimeProvider.this.notifyTrackData((Pair) message.obj);
                }
            }
        }
    }

    public static final class MetricsConstants {
        public static final String CODEC_AUDIO = "android.media.mediaplayer.audio.codec";
        public static final String CODEC_VIDEO = "android.media.mediaplayer.video.codec";
        public static final String DURATION = "android.media.mediaplayer.durationMs";
        public static final String ERRORS = "android.media.mediaplayer.err";
        public static final String ERROR_CODE = "android.media.mediaplayer.errcode";
        public static final String FRAMES = "android.media.mediaplayer.frames";
        public static final String FRAMES_DROPPED = "android.media.mediaplayer.dropped";
        public static final String HEIGHT = "android.media.mediaplayer.height";
        public static final String MIME_TYPE_AUDIO = "android.media.mediaplayer.audio.mime";
        public static final String MIME_TYPE_VIDEO = "android.media.mediaplayer.video.mime";
        public static final String PLAYING = "android.media.mediaplayer.playingMs";
        public static final String WIDTH = "android.media.mediaplayer.width";

        private MetricsConstants() {
        }
    }
}
