package com.samsung.android.media;

import android.app.ActivityThread;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioAttributes;
import android.media.AudioTrack;
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
import java.util.concurrent.Executor;

/* loaded from: classes5.dex */
public class SemMediaPlayer implements SubtitleController.Listener {
  public static final int AUDIO_VOLUME_FADE_IN = 1;
  public static final int AUDIO_VOLUME_FADE_INOUT = 3;
  public static final int AUDIO_VOLUME_FADE_NONE = 0;
  public static final int AUDIO_VOLUME_FADE_OUT = 2;
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
  public static final int KEY_PARAMETER_EXCLUDE_AUDIO_TRACK = 35004;
  public static final int KEY_PARAMETER_HOVERING_TYPE = 31950;
  public static final int KEY_PARAMETER_TIMED_TEXT_TRACK_TIME_SYNC = 31501;
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
  private final OnSubtitleDataListener mIntSubtitleDataListener =
      new OnSubtitleDataListener() { // from class: com.samsung.android.media.SemMediaPlayer.2
        @Override // com.samsung.android.media.SemMediaPlayer.OnSubtitleDataListener
        public void onSubtitleData(SemMediaPlayer ep, SubtitleData data) {
          int index = data.getTrackIndex();
          synchronized (SemMediaPlayer.this.mIndexTrackPairs) {
            Iterator it = SemMediaPlayer.this.mIndexTrackPairs.iterator();
            while (it.hasNext()) {
              Pair<Integer, SubtitleTrack> p = (Pair) it.next();
              if (p.first != null && p.first.intValue() == index && p.second != null) {
                SubtitleTrack track = p.second;
                long runID = data.getStartTimeUs() + 1;
                track.onData(data.getData(), true, runID);
                track.setRunDiscardTimeMs(
                    runID, (data.getStartTimeUs() + data.getDurationUs()) / 1000);
              }
            }
          }
        }
      };

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
  public @interface PlaybackDirection {}

  @Retention(RetentionPolicy.SOURCE)
  public @interface PlaybackEffect {}

  @Retention(RetentionPolicy.SOURCE)
  public @interface PlaybackRateAudioMode {}

  @Retention(RetentionPolicy.SOURCE)
  public @interface SeekType {}

  private native void _crop(int i, int i2, int i3, int i4) throws IllegalStateException;

  private native Bitmap _getCurrentFrame(int i, int i2) throws IllegalStateException;

  private native void _init(
      IBinder iBinder, String str, String[] strArr, String[] strArr2, String str2)
      throws IOException, IllegalArgumentException, SecurityException, IllegalStateException;

  private native void _init(FileDescriptor fileDescriptor, long j, long j2)
      throws IOException, IllegalArgumentException, IllegalStateException;

  private native void _notifyAt(long j);

  private native void _pause() throws IllegalStateException;

  private native void _release();

  private native void _reset() throws IllegalStateException;

  private native void _seekTo(int i, int i2) throws IllegalStateException;

  private native void _setAudioAttributes(AudioAttributes audioAttributes);

  private native void _setBackgroundMusic(Parcel parcel) throws IllegalStateException;

  private native void _setTemporalZoom(int i);

  private native void _setVideoFilterLevel(int i)
      throws IllegalStateException, UnsupportedOperationException;

  private native void _setVideoScalingMode(int i) throws IllegalStateException;

  private native void _setVideoSurface(Surface surface)
      throws IllegalStateException, IllegalArgumentException;

  private native void _setVolume(float f, float f2) throws IllegalStateException;

  private native void _start() throws IllegalStateException;

  private native boolean _updateRegionSEFData(int i, Parcel parcel) throws IllegalStateException;

  private final native void native_finalize();

  private static final native void native_init();

  private final native int native_invoke(Parcel parcel, Parcel parcel2);

  private final native void native_setup(Object obj, AudioAttributes audioAttributes);

  /* JADX INFO: Access modifiers changed from: private */
  public static native void native_stream_event_onStreamDataRequest(
      long j, long j2, long j3, int i);

  /* JADX INFO: Access modifiers changed from: private */
  public static native void native_stream_event_onStreamPresentationEnd(long j, long j2);

  /* JADX INFO: Access modifiers changed from: private */
  public static native void native_stream_event_onTearDown(long j, long j2);

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

  public native void setAudioVolumeFade(int i, int i2, int i3, int i4, int i5)
      throws IllegalStateException, IllegalArgumentException;

  public native void setLooping(boolean z);

  public native boolean setParameter(int i, Parcel parcel);

  public native void setPlaybackDirection(int i);

  public native void setPlaybackEffect(int i, int i2)
      throws IllegalArgumentException, IllegalStateException;

  public native void setPlaybackParams(PlaybackParams playbackParams);

  public native void setPlaybackRange(int i, int i2)
      throws IllegalArgumentException, IllegalStateException;

  public native void setVideoDeflickerEnabled(boolean z)
      throws IllegalStateException, UnsupportedOperationException;

  public native void setVideoFilterName(String str)
      throws IllegalStateException, UnsupportedOperationException;

  public native void setVideoSuperResolutionEnabled(boolean z)
      throws IllegalStateException, UnsupportedOperationException;

  static {
    System.loadLibrary("semmediaplayer_jni");
    native_init();
  }

  public SemMediaPlayer() {
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
    this.mTimeProvider = new TimeProvider(this);
    this.mOpenSubtitleSources = new Vector<>();
    this.mAttributes = new AudioAttributes.Builder().setUsage(1).build();
    native_setup(new WeakReference(this), this.mAttributes);
  }

  public void init(FileDescriptor fd)
      throws IOException, IllegalArgumentException, IllegalStateException {
    init(fd, 0L, 576460752303423487L);
  }

  public void init(FileDescriptor fd, long offset, long length)
      throws IOException, IllegalArgumentException, IllegalStateException {
    _init(fd, offset, length);
  }

  public void init(AssetFileDescriptor afd)
      throws IOException, IllegalArgumentException, IllegalStateException {
    if (afd == null) {
      throw new NullPointerException();
    }
    if (afd.getDeclaredLength() < 0) {
      init(afd.getFileDescriptor());
    } else {
      init(afd.getFileDescriptor(), afd.getStartOffset(), afd.getDeclaredLength());
    }
  }

  private boolean attemptInit(ContentResolver resolver, Uri uri) {
    try {
      AssetFileDescriptor afd = resolver.openAssetFileDescriptor(uri, "r");
      try {
        init(afd);
        if (afd != null) {
          afd.close();
          return true;
        }
        return true;
      } catch (Throwable th) {
        if (afd != null) {
          try {
            afd.close();
          } catch (Throwable th2) {
            th.addSuppressed(th2);
          }
        }
        throw th;
      }
    } catch (IOException | NullPointerException | SecurityException ex) {
      Log.m103w(TAG, "Couldn't open " + (uri == null ? "null uri" : uri.toSafeString()), ex);
      return false;
    }
  }

  public void init(Context context, Uri uri, Map<String, String> headers, List<HttpCookie> cookies)
      throws IOException {
    CookieHandler cookieHandler;
    if (context == null) {
      throw new NullPointerException("context param can not be null.");
    }
    if (uri == null) {
      throw new NullPointerException("uri param can not be null.");
    }
    if (cookies != null
        && (cookieHandler = CookieHandler.getDefault()) != null
        && !(cookieHandler instanceof CookieManager)) {
      throw new IllegalArgumentException(
          "The cookie handler has to be of CookieManager type when cookies are provided");
    }
    ContentResolver resolver = context.getContentResolver();
    String scheme = uri.getScheme();
    String authority = ContentProvider.getAuthorityWithoutUserId(uri.getAuthority());
    if ("file".equals(scheme)) {
      init(uri.getPath());
      return;
    }
    if ("content".equals(scheme) && "settings".equals(authority)) {
      int type = RingtoneManager.getDefaultType(uri);
      Uri cacheUri = RingtoneManager.getCacheForType(type, context.getUserId());
      Uri actualUri = RingtoneManager.getActualDefaultRingtoneUri(context, type);
      if (attemptInit(resolver, cacheUri) || attemptInit(resolver, actualUri)) {
        return;
      }
      init(uri.toString(), headers, cookies, getCacheDir(context));
      return;
    }
    if (attemptInit(resolver, uri)) {
      return;
    }
    init(uri.toString(), headers, cookies, getCacheDir(context));
  }

  private String getCacheDir(Context context) throws IOException {
    if (context == null) {
      throw new NullPointerException("context param can not be null.");
    }
    File cacheDir = context.getCacheDir();
    if (cacheDir == null) {
      Log.m98i(TAG, "cache directory doesn't exist");
      return null;
    }
    if (!cacheDir.canWrite()) {
      Log.m98i(TAG, "no permission to write cache directory" + cacheDir.getCanonicalPath());
      return null;
    }
    return cacheDir.getCanonicalPath() + "/";
  }

  public void init(Context context, Uri uri, Map<String, String> headers)
      throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
    init(context, uri, headers, (List<HttpCookie>) null);
  }

  public void init(Context context, Uri uri)
      throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
    init(context, uri, (Map<String, String>) null, (List<HttpCookie>) null);
  }

  public void init(String path)
      throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
    init(path, (Map<String, String>) null, (List<HttpCookie>) null, (String) null);
  }

  private void init(
      String path, Map<String, String> headers, List<HttpCookie> cookies, String cacheDir)
      throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
    String[] keys = null;
    String[] values = null;
    if (headers != null) {
      keys = new String[headers.size()];
      values = new String[headers.size()];
      int i = 0;
      for (Map.Entry<String, String> entry : headers.entrySet()) {
        keys[i] = entry.getKey();
        values[i] = entry.getValue();
        i++;
      }
    }
    init(path, keys, values, cookies, cacheDir);
  }

  private void init(
      String path, String[] keys, String[] values, List<HttpCookie> cookies, String cacheDir)
      throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
    Uri uri = Uri.parse(path);
    String scheme = uri.getScheme();
    if ("file".equals(scheme)) {
      path = uri.getPath();
    } else {
      if ("content".equals(scheme)) {
        throw new IOException("init failed with content scheme");
      }
      if (scheme != null) {
        _init(createHttpServiceBinderIfNecessary(path, cookies), path, keys, values, cacheDir);
        return;
      }
    }
    File file = new File(path);
    if (file.exists()) {
      FileInputStream is = null;
      try {
        is = new FileInputStream(file);
        FileDescriptor fd = is.getFD();
        init(fd);
        is.close();
        return;
      } catch (Throwable th) {
        if (is != null) {
          is.close();
        }
        throw th;
      }
    }
    throw new IOException("init failed with file scheme");
  }

  private IBinder createHttpServiceBinderIfNecessary(String path, List<HttpCookie> cookies) {
    if (path.startsWith("http://") || path.startsWith("https://")) {
      return new MediaHTTPService(cookies).asBinder();
    }
    if (path.startsWith("widevine://")) {
      Log.m94d(TAG, "Widevine classic is no longer supported");
      return null;
    }
    return null;
  }

  public void setDisplay(SurfaceHolder sh) throws IllegalStateException, IllegalArgumentException {
    Surface surface;
    this.mSurfaceHolder = sh;
    if (sh != null) {
      surface = sh.getSurface();
    } else {
      surface = null;
    }
    _setVideoSurface(surface);
    updateSurfaceScreenOn();
  }

  public void setSurface(Surface surface) throws IllegalStateException, IllegalArgumentException {
    if (this.mScreenOnWhilePlaying && surface != null) {
      Log.m102w(TAG, "setScreenOnWhilePlaying(true) is ineffective for Surface");
    }
    this.mSurfaceHolder = null;
    _setVideoSurface(surface);
    updateSurfaceScreenOn();
  }

  private boolean isVideoScalingModeSupported(int mode) {
    return mode == 1 || mode == 2;
  }

  public void setVideoScalingMode(int mode) throws IllegalStateException, IllegalArgumentException {
    if (!isVideoScalingModeSupported(mode)) {
      String msg = "Scaling mode " + mode + " is not supported";
      throw new IllegalArgumentException(msg);
    }
    _setVideoScalingMode(mode);
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
        InputStream is = it.next();
        try {
          is.close();
        } catch (IOException e) {
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

  public void seekTo(int msec) throws IllegalStateException {
    seekTo(msec, 4);
  }

  public void seekTo(int msec, int type) throws IllegalStateException {
    _seekTo(msec, type);
  }

  @Deprecated(forRemoval = true, since = "13.0")
  public void crop(int left, int top, int right, int bottom) throws IllegalStateException {
    _crop(left, top, right, bottom);
  }

  public void setVolume(float leftVolume, float rightVolume) throws IllegalStateException {
    if (leftVolume < 0.0f || rightVolume < 0.0f) {
      throw new IllegalArgumentException(
          "leftVolume(" + leftVolume + ") or rightVolume(" + rightVolume + ") is smaller than 0.0");
    }
    if (leftVolume > 1.0f || rightVolume > 1.0f) {
      throw new IllegalArgumentException(
          "leftVolume(" + leftVolume + ") or rightVolume(" + rightVolume + ") is greater than 1.0");
    }
    playerSetVolume(leftVolume, rightVolume);
  }

  public void playerSetVolume(float leftVolume, float rightVolume) {
    _setVolume(leftVolume, rightVolume);
  }

  public Bitmap getCurrentFrame() throws IllegalStateException {
    return _getCurrentFrame(-1, -1);
  }

  public Bitmap getCurrentFrame(int width, int height) throws IllegalStateException {
    return _getCurrentFrame(width, height);
  }

  public void invoke(Parcel request, Parcel reply) throws IllegalStateException {
    int retcode = native_invoke(request, reply);
    reply.setDataPosition(0);
    if (retcode != 0) {
      throw new RuntimeException("failure code: " + retcode);
    }
  }

  public void setWakeMode(Context context, int mode) {
    boolean washeld = false;
    if (SystemProperties.getBoolean("audio.offload.ignore_setawake", false)) {
      Log.m102w(TAG, "IGNORING setWakeMode " + mode);
      return;
    }
    PowerManager.WakeLock wakeLock = this.mWakeLock;
    if (wakeLock != null) {
      if (wakeLock.isHeld()) {
        washeld = true;
        this.mWakeLock.release();
      }
      this.mWakeLock = null;
    }
    PowerManager pm = (PowerManager) context.getSystemService("power");
    PowerManager.WakeLock newWakeLock =
        pm.newWakeLock(536870912 | mode, SemMediaPlayer.class.getName());
    this.mWakeLock = newWakeLock;
    newWakeLock.setReferenceCounted(false);
    if (washeld) {
      this.mWakeLock.acquire();
    }
  }

  public void setScreenOnWhilePlaying(boolean screenOn) {
    if (this.mScreenOnWhilePlaying != screenOn) {
      if (screenOn && this.mSurfaceHolder == null) {
        Log.m102w(TAG, "setScreenOnWhilePlaying(true) is ineffective without a SurfaceHolder");
      }
      this.mScreenOnWhilePlaying = screenOn;
      updateSurfaceScreenOn();
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void stayAwake(boolean awake) {
    PowerManager.WakeLock wakeLock = this.mWakeLock;
    if (wakeLock != null) {
      if (awake && !wakeLock.isHeld()) {
        this.mWakeLock.acquire();
      } else if (!awake && this.mWakeLock.isHeld()) {
        this.mWakeLock.release();
      }
    }
    this.mStayAwake = awake;
    updateSurfaceScreenOn();
  }

  private void updateSurfaceScreenOn() {
    SurfaceHolder surfaceHolder = this.mSurfaceHolder;
    if (surfaceHolder != null) {
      surfaceHolder.setKeepScreenOn(this.mScreenOnWhilePlaying && this.mStayAwake);
    }
  }

  public void notifyAt(long mediaTimeUs) {
    _notifyAt(mediaTimeUs);
  }

  public boolean setParameter(int key, String value) {
    Parcel p = Parcel.obtain();
    p.writeString(value);
    boolean ret = setParameter(key, p);
    p.recycle();
    return ret;
  }

  public boolean setParameter(int key, int value) {
    Parcel p = Parcel.obtain();
    p.writeInt(value);
    boolean ret = setParameter(key, p);
    p.recycle();
    return ret;
  }

  protected void finalize() {
    native_finalize();
  }

  public PlaybackParams easyPlaybackParams(float rate, int audioMode) {
    PlaybackParams params = new PlaybackParams();
    params.allowDefaults();
    switch (audioMode) {
      case 0:
        params.setSpeed(rate).setPitch(1.0f);
        return params;
      case 1:
        params.setSpeed(rate).setPitch(1.0f).setAudioFallbackMode(2);
        return params;
      case 2:
        params.setSpeed(rate).setPitch(rate);
        return params;
      default:
        String msg = "Audio playback mode " + audioMode + " is not supported";
        throw new IllegalArgumentException(msg);
    }
  }

  public static class TrackInfo implements Parcelable {
    static final Parcelable.Creator<TrackInfo> CREATOR =
        new Parcelable.Creator<
            TrackInfo>() { // from class: com.samsung.android.media.SemMediaPlayer.TrackInfo.1
          /* JADX WARN: Can't rename method to resolve collision */
          @Override // android.os.Parcelable.Creator
          public TrackInfo createFromParcel(Parcel in) {
            return new TrackInfo(in);
          }

          /* JADX WARN: Can't rename method to resolve collision */
          @Override // android.os.Parcelable.Creator
          public TrackInfo[] newArray(int size) {
            return new TrackInfo[size];
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

    TrackInfo(Parcel in) {
      byte[] text;
      this.mTrackName = "";
      int readInt = in.readInt();
      this.mTrackType = readInt;
      this.mMime = in.readString();
      String readString = in.readString();
      this.mLanguage = readString;
      this.mRotationDegrees = -1;
      this.mVideoWidth = -1;
      this.mVideoHeight = -1;
      this.mFrameRate = -1;
      this.mSampleRate = -1;
      this.mChannel = -1;
      MediaFormat createSubtitleFormat = MediaFormat.createSubtitleFormat(this.mMime, readString);
      this.mFormat = createSubtitleFormat;
      if (readInt == 1) {
        this.mRotationDegrees = in.readInt();
        this.mVideoWidth = in.readInt();
        this.mVideoHeight = in.readInt();
        this.mFrameRate = in.readInt();
        Log.m98i(
            TAG,
            "videotype mime : "
                + this.mMime
                + ", language : "
                + this.mLanguage
                + ", rotation : "
                + this.mRotationDegrees
                + ", width : "
                + this.mVideoWidth
                + ", height : "
                + this.mVideoHeight
                + ", fps : "
                + this.mFrameRate);
      } else if (readInt == 2) {
        this.mSampleRate = in.readInt();
        this.mChannel = in.readInt();
        Log.m98i(
            TAG,
            "audiotype mime : "
                + this.mMime
                + ", language : "
                + this.mLanguage
                + ", samplingrate : "
                + this.mSampleRate
                + ", channel : "
                + this.mChannel);
      } else if (readInt == 4 || readInt == 6) {
        createSubtitleFormat.setInteger(MediaFormat.KEY_IS_AUTOSELECT, in.readInt());
        createSubtitleFormat.setInteger(MediaFormat.KEY_IS_DEFAULT, in.readInt());
        createSubtitleFormat.setInteger(MediaFormat.KEY_IS_FORCED_SUBTITLE, in.readInt());
      }
      int textLen = in.readInt();
      if (textLen > 0 && (text = in.createByteArray()) != null && text.length > 0) {
        this.mTrackName = new String(text);
      }
    }

    TrackInfo(int type, MediaFormat format) {
      this.mTrackName = "";
      this.mTrackType = type;
      this.mFormat = format;
    }

    public MediaFormat getFormat() {
      int i = this.mTrackType;
      if (i == 4 || i == 6) {
        return this.mFormat;
      }
      return null;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
      return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
      dest.writeInt(this.mTrackType);
      dest.writeString(this.mMime);
      dest.writeString(this.mLanguage);
      int i = this.mTrackType;
      if (i == 1) {
        dest.writeInt(this.mRotationDegrees);
        dest.writeInt(this.mVideoWidth);
        dest.writeInt(this.mVideoHeight);
        dest.writeInt(this.mFrameRate);
      } else if (i == 2) {
        dest.writeInt(this.mSampleRate);
        dest.writeInt(this.mChannel);
      } else if (i == 4 || i == 6) {
        dest.writeInt(this.mFormat.getInteger(MediaFormat.KEY_IS_AUTOSELECT));
        dest.writeInt(this.mFormat.getInteger(MediaFormat.KEY_IS_DEFAULT));
        dest.writeInt(this.mFormat.getInteger(MediaFormat.KEY_IS_FORCED_SUBTITLE));
      }
      dest.writeString(getName());
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
    TrackInfo[] allTrackInfo;
    TrackInfo[] trackInfo = getInbandTrackInfo();
    synchronized (this.mIndexTrackPairs) {
      allTrackInfo = new TrackInfo[this.mIndexTrackPairs.size()];
      for (int i = 0; i < allTrackInfo.length; i++) {
        Pair<Integer, SubtitleTrack> p = this.mIndexTrackPairs.get(i);
        if (p.first != null) {
          allTrackInfo[i] = trackInfo[p.first.intValue()];
        } else {
          SubtitleTrack track = p.second;
          allTrackInfo[i] = new TrackInfo(6, track.getFormat());
        }
      }
    }
    return allTrackInfo;
  }

  private TrackInfo[] getInbandTrackInfo() throws IllegalStateException {
    Parcel request = Parcel.obtain();
    Parcel reply = Parcel.obtain();
    try {
      request.writeInt(1);
      invoke(request, reply);
      TrackInfo[] trackInfo = (TrackInfo[]) reply.createTypedArray(TrackInfo.CREATOR);
      return trackInfo;
    } finally {
      request.recycle();
      reply.recycle();
    }
  }

  public int getSelectedTrack(int trackType) throws IllegalStateException {
    SubtitleTrack subtitleTrack;
    SubtitleController subtitleController = this.mSubtitleController;
    if (subtitleController != null
        && trackType == 6
        && (subtitleTrack = subtitleController.getSelectedTrack()) != null) {
      synchronized (this.mIndexTrackPairs) {
        for (int i = 0; i < this.mIndexTrackPairs.size(); i++) {
          if (this.mIndexTrackPairs.get(i).second == subtitleTrack) {
            return i;
          }
        }
      }
    }
    Parcel request = Parcel.obtain();
    Parcel reply = Parcel.obtain();
    try {
      request.writeInt(7);
      request.writeInt(trackType);
      invoke(request, reply);
      int inbandTrackIndex = reply.readInt();
      synchronized (this.mIndexTrackPairs) {
        for (int i2 = 0; i2 < this.mIndexTrackPairs.size(); i2++) {
          Pair<Integer, SubtitleTrack> p = this.mIndexTrackPairs.get(i2);
          if (p.first != null && p.first.intValue() == inbandTrackIndex) {
            return i2;
          }
        }
        request.recycle();
        reply.recycle();
        return -1;
      }
    } finally {
      request.recycle();
      reply.recycle();
    }
  }

  public void selectTrack(int index) throws IllegalStateException {
    selectOrDeselectTrack(index, true);
  }

  public void deselectTrack(int index) throws IllegalStateException {
    selectOrDeselectTrack(index, false);
  }

  private void selectOrDeselectTrack(int index, boolean select) throws IllegalStateException {
    populateInbandTracks();
    try {
      Pair<Integer, SubtitleTrack> p = this.mIndexTrackPairs.get(index);
      SubtitleTrack track = p.second;
      if (track == null) {
        selectOrDeselectInbandTrack(p.first.intValue(), select);
        return;
      }
      SubtitleController subtitleController = this.mSubtitleController;
      if (subtitleController == null) {
        return;
      }
      if (!select) {
        if (subtitleController.getSelectedTrack() == track) {
          this.mSubtitleController.selectTrack(null);
          return;
        } else {
          Log.m102w(TAG, "trying to deselect track that was not selected");
          return;
        }
      }
      subtitleController.selectTrack(track);
    } catch (ArrayIndexOutOfBoundsException e) {
    }
  }

  private void selectOrDeselectInbandTrack(int index, boolean select) throws IllegalStateException {
    Parcel request = Parcel.obtain();
    Parcel reply = Parcel.obtain();
    try {
      request.writeInt(select ? 4 : 5);
      request.writeInt(index);
      invoke(request, reply);
    } finally {
      request.recycle();
      reply.recycle();
    }
  }

  public void setOnInitCompleteListener(OnInitCompleteListener listener) {
    this.mOnInitCompleteListener = listener;
  }

  public void setOnPlaybackCompleteListener(OnPlaybackCompleteListener listener) {
    this.mOnPlaybackCompleteListener = listener;
  }

  public void setOnSeekCompleteListener(OnSeekCompleteListener listener) {
    this.mOnSeekCompleteListener = listener;
  }

  public void setOnErrorListener(OnErrorListener listener) {
    this.mOnErrorListener = listener;
  }

  public void setOnInfoListener(OnInfoListener listener) {
    this.mOnInfoListener = listener;
  }

  public void setOnVideoSizeChangedListener(OnVideoSizeChangedListener listener) {
    this.mOnVideoSizeChangedListener = listener;
  }

  public void setOnBufferingUpdateListener(OnBufferingUpdateListener listener) {
    this.mOnBufferingUpdateListener = listener;
  }

  public void setOnTimedTextListener(OnTimedTextListener listener) {
    this.mOnTimedTextListener = listener;
  }

  public void setOnSubtitleDataListener(OnSubtitleDataListener listener, Handler handler) {
    if (listener == null) {
      throw new IllegalArgumentException("Illegal null listener");
    }
    if (handler == null) {
      throw new IllegalArgumentException("Illegal null handler");
    }
    setOnSubtitleDataListenerInt(listener, handler);
  }

  public void setOnSubtitleDataListener(OnSubtitleDataListener listener) {
    if (listener == null) {
      throw new IllegalArgumentException("Illegal null listener");
    }
    setOnSubtitleDataListenerInt(listener, null);
  }

  public void clearOnSubtitleDataListener() {
    setOnSubtitleDataListenerInt(null, null);
  }

  private void setOnSubtitleDataListenerInt(OnSubtitleDataListener listener, Handler handler) {
    synchronized (this) {
      this.mExtSubtitleDataListener = listener;
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

    public TimeProvider(SemMediaPlayer mp) {
      this.mLastTimeUs = 0L;
      this.mRefresh = false;
      this.mPlayer = mp;
      try {
        getCurrentTimeUs(true, false);
      } catch (IllegalStateException e) {
        this.mRefresh = true;
      }
      Looper myLooper = Looper.myLooper();
      Looper looper = myLooper;
      if (myLooper == null) {
        Looper mainLooper = Looper.getMainLooper();
        looper = mainLooper;
        if (mainLooper == null) {
          HandlerThread handlerThread = new HandlerThread("SemMediaPlayerMTPEventThread", -2);
          this.mHandlerThread = handlerThread;
          handlerThread.start();
          looper = this.mHandlerThread.getLooper();
        }
      }
      this.mEventHandler = new EventHandler(looper);
      this.mListeners = new MediaTimeProvider.OnMediaTimeListener[0];
      this.mTimes = new long[0];
      this.mLastTimeUs = 0L;
    }

    private void scheduleNotification(int type, long delayUs) {
      if (this.mSeeking && type == 0) {
        return;
      }
      if (this.DEBUG) {
        Log.m100v(TAG, "scheduleNotification " + type + " in " + delayUs);
      }
      this.mEventHandler.removeMessages(1);
      Message msg = this.mEventHandler.obtainMessage(1, type, 0);
      this.mEventHandler.sendMessageDelayed(msg, (int) (delayUs / 1000));
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
          Log.m94d(TAG, "onNotifyTime: ");
        }
        scheduleNotification(0, 0L);
      }
    }

    public void onPaused(boolean paused) {
      synchronized (this) {
        if (this.DEBUG) {
          Log.m94d(TAG, "onPaused: " + paused);
        }
        this.mPausing = paused;
        this.mSeeking = false;
        scheduleNotification(0, 0L);
      }
    }

    public void onBuffering(boolean buffering) {
      synchronized (this) {
        if (this.DEBUG) {
          Log.m94d(TAG, "onBuffering: " + buffering);
        }
        this.mBuffering = buffering;
        scheduleNotification(0, 0L);
      }
    }

    @Override // com.samsung.android.media.SemMediaPlayer.OnSeekCompleteListener
    public void onSeekComplete(SemMediaPlayer mp) {
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
        long timeUs = getCurrentTimeUs(true, false);
        if (this.DEBUG) {
          Log.m94d(TAG, "onSeekComplete at " + timeUs);
        }
        for (MediaTimeProvider.OnMediaTimeListener listener : this.mListeners) {
          if (listener == null) {
            break;
          }
          listener.onSeek(timeUs);
        }
      } catch (IllegalStateException e) {
        if (this.DEBUG) {
          Log.m94d(TAG, "onSeekComplete but no player");
        }
        this.mPausing = true;
        notifyTimedEvent(false);
      }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void notifyTrackData(Pair<SubtitleTrack, byte[]> trackData) {
      SubtitleTrack track = trackData.first;
      byte[] data = trackData.second;
      track.onData(data, true, -1L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void notifyStop() {
      for (MediaTimeProvider.OnMediaTimeListener listener : this.mListeners) {
        if (listener == null) {
          break;
        }
        listener.onStop();
      }
    }

    private int registerListener(MediaTimeProvider.OnMediaTimeListener listener) {
      MediaTimeProvider.OnMediaTimeListener[] onMediaTimeListenerArr;
      MediaTimeProvider.OnMediaTimeListener onMediaTimeListener;
      int i = 0;
      while (true) {
        onMediaTimeListenerArr = this.mListeners;
        if (i >= onMediaTimeListenerArr.length
            || (onMediaTimeListener = onMediaTimeListenerArr[i]) == listener
            || onMediaTimeListener == null) {
          break;
        }
        i++;
      }
      if (i >= onMediaTimeListenerArr.length) {
        MediaTimeProvider.OnMediaTimeListener[] newListeners =
            new MediaTimeProvider.OnMediaTimeListener[i + 1];
        long[] newTimes = new long[i + 1];
        System.arraycopy(onMediaTimeListenerArr, 0, newListeners, 0, onMediaTimeListenerArr.length);
        long[] jArr = this.mTimes;
        System.arraycopy(jArr, 0, newTimes, 0, jArr.length);
        this.mListeners = newListeners;
        this.mTimes = newTimes;
      }
      MediaTimeProvider.OnMediaTimeListener[] onMediaTimeListenerArr2 = this.mListeners;
      if (onMediaTimeListenerArr2[i] == null) {
        onMediaTimeListenerArr2[i] = listener;
        this.mTimes[i] = -1;
      }
      return i;
    }

    @Override // android.media.MediaTimeProvider
    public void notifyAt(long timeUs, MediaTimeProvider.OnMediaTimeListener listener) {
      synchronized (this) {
        if (this.DEBUG) {
          Log.m94d(TAG, "notifyAt " + timeUs);
        }
        this.mTimes[registerListener(listener)] = timeUs;
        scheduleNotification(0, 0L);
      }
    }

    @Override // android.media.MediaTimeProvider
    public void scheduleUpdate(MediaTimeProvider.OnMediaTimeListener listener) {
      synchronized (this) {
        if (this.DEBUG) {
          Log.m94d(TAG, "scheduleUpdate");
        }
        int i = registerListener(listener);
        this.mTimes[i] = 0;
        scheduleNotification(0, 0L);
      }
    }

    @Override // android.media.MediaTimeProvider
    public void cancelNotifications(MediaTimeProvider.OnMediaTimeListener listener) {
      synchronized (this) {
        int i = 0;
        while (true) {
          MediaTimeProvider.OnMediaTimeListener[] onMediaTimeListenerArr = this.mListeners;
          if (i >= onMediaTimeListenerArr.length) {
            break;
          }
          MediaTimeProvider.OnMediaTimeListener onMediaTimeListener = onMediaTimeListenerArr[i];
          if (onMediaTimeListener == listener) {
            System.arraycopy(
                onMediaTimeListenerArr,
                i + 1,
                onMediaTimeListenerArr,
                i,
                (onMediaTimeListenerArr.length - i) - 1);
            long[] jArr = this.mTimes;
            System.arraycopy(jArr, i + 1, jArr, i, (jArr.length - i) - 1);
            this.mListeners[r1.length - 1] = null;
            this.mTimes[r1.length - 1] = -1;
            break;
          }
          if (onMediaTimeListener == null) {
            break;
          } else {
            i++;
          }
        }
        scheduleNotification(0, 0L);
      }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void notifyTimedEvent(boolean refreshTime) {
      long nowUs;
      MediaTimeProvider.OnMediaTimeListener onMediaTimeListener;
      try {
        nowUs = getCurrentTimeUs(refreshTime, true);
      } catch (IllegalStateException e) {
        this.mRefresh = true;
        this.mPausing = true;
        nowUs = getCurrentTimeUs(refreshTime, true);
      }
      long nextTimeUs = nowUs;
      if (this.mSeeking) {
        return;
      }
      if (this.DEBUG) {
        StringBuilder sb = new StringBuilder();
        sb.append("notifyTimedEvent(")
            .append(this.mLastTimeUs)
            .append(" -> ")
            .append(nowUs)
            .append(") from {");
        boolean first = true;
        for (long time : this.mTimes) {
          if (time != -1) {
            if (!first) {
              sb.append(", ");
            }
            sb.append(time);
            first = false;
          }
        }
        sb.append("}");
        Log.m94d(TAG, sb.toString());
      }
      Vector<MediaTimeProvider.OnMediaTimeListener> activatedListeners = new Vector<>();
      int ix = 0;
      while (true) {
        long[] jArr = this.mTimes;
        if (ix >= jArr.length || (onMediaTimeListener = this.mListeners[ix]) == null) {
          break;
        }
        long j = jArr[ix];
        if (j > -1) {
          if (j <= 1000 + nowUs) {
            activatedListeners.add(onMediaTimeListener);
            if (this.DEBUG) {
              Log.m94d(TAG, Environment.MEDIA_REMOVED);
            }
            this.mTimes[ix] = -1;
          } else if (nextTimeUs == nowUs || j < nextTimeUs) {
            nextTimeUs = j;
          }
        }
        ix++;
      }
      if (nextTimeUs > nowUs && !this.mPaused) {
        if (this.DEBUG) {
          Log.m94d(TAG, "scheduling for " + nextTimeUs + " and " + nowUs);
        }
        this.mPlayer.notifyAt(nextTimeUs);
      } else {
        this.mEventHandler.removeMessages(1);
      }
      Iterator<MediaTimeProvider.OnMediaTimeListener> it = activatedListeners.iterator();
      while (it.hasNext()) {
        MediaTimeProvider.OnMediaTimeListener listener = it.next();
        listener.onTimedEvent(nowUs);
      }
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x002f A[Catch: IllegalStateException -> 0x007c, all -> 0x00b8, TryCatch #1 {IllegalStateException -> 0x007c, blocks: (B:12:0x000d, B:14:0x0021, B:18:0x0029, B:20:0x002f, B:23:0x0041), top: B:11:0x000d, outer: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x005b A[Catch: all -> 0x00b8, TRY_ENTER, TryCatch #0 {, blocks: (B:3:0x0001, B:6:0x0007, B:7:0x0009, B:12:0x000d, B:14:0x0021, B:18:0x0029, B:20:0x002f, B:23:0x0041, B:27:0x005b, B:29:0x0063, B:31:0x006b, B:32:0x0078, B:33:0x007a, B:35:0x0074, B:38:0x007d, B:40:0x0081, B:42:0x0085, B:44:0x0091, B:46:0x0097, B:47:0x00b2, B:48:0x00b4, B:50:0x008d, B:51:0x00b7), top: B:2:0x0001, inners: #1 }] */
    @Override // android.media.MediaTimeProvider
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public long getCurrentTimeUs(boolean refreshTime, boolean monotonic)
        throws IllegalStateException {
      boolean z;
      synchronized (this) {
        if (this.mPaused && !refreshTime) {
          return this.mLastReportedTime;
        }
        try {
          this.mLastTimeUs = this.mPlayer.getCurrentPosition() * 1000;
          if (this.mPlayer.isPlaying() && !this.mBuffering) {
            z = false;
            this.mPaused = z;
            if (this.DEBUG) {
              Log.m100v(TAG, (this.mPaused ? "paused" : "playing") + " at " + this.mLastTimeUs);
            }
            if (monotonic) {
              long j = this.mLastTimeUs;
              long j2 = this.mLastReportedTime;
              if (j < j2) {
                if (j2 - j > 1000000) {
                  this.mSeeking = true;
                  scheduleNotification(3, 0L);
                }
                return this.mLastReportedTime;
              }
            }
            this.mLastReportedTime = this.mLastTimeUs;
            return this.mLastReportedTime;
          }
          z = true;
          this.mPaused = z;
          if (this.DEBUG) {}
          if (monotonic) {}
          this.mLastReportedTime = this.mLastTimeUs;
          return this.mLastReportedTime;
        } catch (IllegalStateException e) {
          if (!this.mPausing) {
            throw e;
          }
          this.mPausing = false;
          if (!monotonic || this.mLastReportedTime < this.mLastTimeUs) {
            this.mLastReportedTime = this.mLastTimeUs;
          }
          this.mPaused = true;
          if (this.DEBUG) {
            Log.m94d(TAG, "illegal state, but pausing: estimating at " + this.mLastReportedTime);
          }
          return this.mLastReportedTime;
        }
      }
    }

    private class EventHandler extends Handler {
      public EventHandler(Looper looper) {
        super(looper);
      }

      @Override // android.os.Handler
      public void handleMessage(Message msg) {
        if (msg.what == 1) {
          switch (msg.arg1) {
            case 0:
              TimeProvider.this.notifyTimedEvent(true);
              break;
            case 2:
              TimeProvider.this.notifyStop();
              break;
            case 3:
              TimeProvider.this.notifySeek();
              break;
          }
        }
        if (msg.what == 2) {
          switch (msg.arg1) {
            case 4:
              TimeProvider.this.notifyTrackData((Pair) msg.obj);
              break;
          }
        }
      }
    }
  }

  private class EventHandler extends Handler {
    private SemMediaPlayer mSemMediaPlayer;

    public EventHandler(SemMediaPlayer ep, Looper looper) {
      super(looper);
      this.mSemMediaPlayer = ep;
    }

    @Override // android.os.Handler
    public void handleMessage(Message msg) {
      OnPlaybackCompleteListener onPlaybackCompleteListener;
      if (this.mSemMediaPlayer.mNativeContext == 0) {
        Log.m102w(SemMediaPlayer.TAG, "semmediaplayer went away with unhandled events");
        return;
      }
      boolean z = true;
      switch (msg.what) {
        case 1:
          TrackInfo[] trackInfo = null;
          if (msg.obj instanceof Parcel) {
            Parcel parcel = (Parcel) msg.obj;
            trackInfo = (TrackInfo[]) parcel.createTypedArray(TrackInfo.CREATOR);
            parcel.recycle();
          }
          SemMediaPlayer.this.populateInbandTracks(trackInfo);
          OnInitCompleteListener onInitCompleteListener =
              SemMediaPlayer.this.mOnInitCompleteListener;
          if (onInitCompleteListener != null) {
            onInitCompleteListener.onInitComplete(this.mSemMediaPlayer, trackInfo);
            return;
          }
          return;
        case 2:
          OnPlaybackCompleteListener onPlaybackCompleteListener2 =
              SemMediaPlayer.this.mOnPlaybackCompleteListener;
          if (onPlaybackCompleteListener2 != null) {
            onPlaybackCompleteListener2.onPlaybackComplete(this.mSemMediaPlayer);
          }
          SemMediaPlayer.this.stayAwake(false);
          return;
        case 3:
          OnBufferingUpdateListener onBufferingUpdateListener =
              SemMediaPlayer.this.mOnBufferingUpdateListener;
          if (onBufferingUpdateListener != null) {
            onBufferingUpdateListener.onBufferingUpdate(this.mSemMediaPlayer, msg.arg1);
            return;
          }
          return;
        case 4:
          OnSeekCompleteListener onSeekCompleteListener =
              SemMediaPlayer.this.mOnSeekCompleteListener;
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
            Log.m95d(SemMediaPlayer.TAG, "handleMessage MEDIA_SEEK_COMPLETE e : ", e);
            return;
          }
        case 5:
          OnVideoSizeChangedListener onVideoSizeChangedListener =
              SemMediaPlayer.this.mOnVideoSizeChangedListener;
          if (onVideoSizeChangedListener != null) {
            onVideoSizeChangedListener.onVideoSizeChanged(this.mSemMediaPlayer, msg.arg1, msg.arg2);
            return;
          }
          return;
        case 7:
          try {
            TimeProvider timeProvider2 = SemMediaPlayer.this.mTimeProvider;
            if (timeProvider2 != null) {
              if (msg.what != 7) {
                z = false;
              }
              timeProvider2.onPaused(z);
              return;
            }
            return;
          } catch (NullPointerException e2) {
            Log.m95d(SemMediaPlayer.TAG, "handleMessage MEDIA_PAUSED e : ", e2);
            return;
          }
        case 98:
          TimeProvider timeProvider3 = SemMediaPlayer.this.mTimeProvider;
          if (timeProvider3 != null) {
            timeProvider3.onNotifyTime();
            return;
          }
          return;
        case 99:
          OnTimedTextListener onTimedTextListener = SemMediaPlayer.this.mOnTimedTextListener;
          if (onTimedTextListener != null) {
            if (msg.obj == null) {
              onTimedTextListener.onTimedText(this.mSemMediaPlayer, null);
              return;
            } else {
              if (msg.obj instanceof Parcel) {
                Parcel parcel2 = (Parcel) msg.obj;
                TimedText text = new TimedText(parcel2);
                parcel2.recycle();
                onTimedTextListener.onTimedText(this.mSemMediaPlayer, text);
                return;
              }
              return;
            }
          }
          return;
        case 100:
          boolean error_was_handled = false;
          OnErrorListener onErrorListener = SemMediaPlayer.this.mOnErrorListener;
          if (onErrorListener != null) {
            error_was_handled = onErrorListener.onError(this.mSemMediaPlayer, msg.arg1, msg.arg2);
          }
          if (!error_was_handled
              && (onPlaybackCompleteListener = SemMediaPlayer.this.mOnPlaybackCompleteListener)
                  != null) {
            onPlaybackCompleteListener.onPlaybackComplete(this.mSemMediaPlayer);
          }
          SemMediaPlayer.this.stayAwake(false);
          return;
        case 200:
          switch (msg.arg1) {
            case 802:
              try {
                SemMediaPlayer.this.scanInternalSubtitleTracks();
              } catch (RuntimeException e3) {
                Message msg2 = obtainMessage(100, 1, -1010, null);
                sendMessage(msg2);
              }
            case 803:
              msg.arg1 = 802;
              if (SemMediaPlayer.this.mSubtitleController != null) {
                SemMediaPlayer.this.mSubtitleController.selectDefaultTrack();
                break;
              }
              break;
          }
          OnInfoListener onInfoListener = SemMediaPlayer.this.mOnInfoListener;
          if (onInfoListener != null) {
            if (msg.arg1 == 10974 && (msg.obj instanceof Parcel)) {
              Parcel info = (Parcel) msg.obj;
              SemMediaPlayer.this.mSuperSlowInfo =
                  (SuperSlowRegion[]) info.createTypedArray(SuperSlowRegion.CREATOR);
              info.recycle();
            }
            onInfoListener.onInfo(this.mSemMediaPlayer, msg.arg1, msg.arg2);
            return;
          }
          return;
        case 201:
          synchronized (this) {
            if (SemMediaPlayer.this.mSubtitleDataListenerDisabled) {
              return;
            }
            final OnSubtitleDataListener extSubtitleListener =
                SemMediaPlayer.this.mExtSubtitleDataListener;
            Handler extSubtitleHandler = SemMediaPlayer.this.mExtSubtitleDataHandler;
            if (msg.obj instanceof Parcel) {
              Parcel parcel3 = (Parcel) msg.obj;
              final SubtitleData data = new SubtitleData(parcel3);
              parcel3.recycle();
              SemMediaPlayer.this.mIntSubtitleDataListener.onSubtitleData(
                  this.mSemMediaPlayer, data);
              if (extSubtitleListener != null) {
                if (extSubtitleHandler == null) {
                  extSubtitleListener.onSubtitleData(this.mSemMediaPlayer, data);
                  return;
                } else {
                  extSubtitleHandler.post(
                      new Runnable() { // from class:
                                       // com.samsung.android.media.SemMediaPlayer.EventHandler.1
                        @Override // java.lang.Runnable
                        public void run() {
                          extSubtitleListener.onSubtitleData(
                              EventHandler.this.mSemMediaPlayer, data);
                        }
                      });
                  return;
                }
              }
              return;
            }
            return;
          }
        default:
          Log.m96e(SemMediaPlayer.TAG, "Unknown message type " + msg.what);
          return;
      }
    }
  }

  private static void postEventFromNative(Object ref, int what, int arg1, int arg2, Object obj) {
    EventHandler eventHandler;
    SemMediaPlayer player = (SemMediaPlayer) ((WeakReference) ref).get();
    if (player != null && (eventHandler = player.mEventHandler) != null) {
      Message m = eventHandler.obtainMessage(what, arg1, arg2, obj);
      player.mEventHandler.sendMessage(m);
    }
  }

  private class SpeedRegion {
    int audioEnd;
    int speedRate;
    int videoEnd;
    int videoStart;

    private SpeedRegion() {}
  }

  public void addRegion(
      int playbackRate, int videoStartTimeMs, int videoEndTimeMs, int audioEndTimeMs) {
    SpeedRegion speedRegion = new SpeedRegion();
    speedRegion.speedRate = playbackRate;
    speedRegion.videoStart = videoStartTimeMs;
    speedRegion.videoEnd = videoEndTimeMs;
    speedRegion.audioEnd = audioEndTimeMs;
    this.mSpeedRegions.add(speedRegion);
  }

  public boolean applyRegion(int updatePositionMs, int representativeRegionIndex)
      throws IllegalStateException {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < this.mSpeedRegions.size(); i++) {
      if (i > 0) {
        sb.append("*");
      }
      sb.append(this.mSpeedRegions.get(i).videoStart);
      sb.append(":");
      sb.append(this.mSpeedRegions.get(i).videoEnd);
      sb.append(":");
      if (this.mSpeedRegions.get(i).audioEnd != -1 && representativeRegionIndex != -1) {
        sb.append(this.mSpeedRegions.get(i).audioEnd);
        sb.append(":");
      } else {
        if (this.mSpeedRegions.get(i).audioEnd != -1 && representativeRegionIndex == -1) {
          Log.m96e(TAG, "Mismatched input of data.");
          this.mSpeedRegions.clear();
          return false;
        }
        if (this.mSpeedRegions.get(i).audioEnd == -1 && representativeRegionIndex != -1) {
          Log.m96e(TAG, "Mismatched input of data.");
          this.mSpeedRegions.clear();
          return false;
        }
      }
      sb.append(this.mSpeedRegions.get(i).speedRate);
    }
    if (representativeRegionIndex != -1) {
      sb.append("!");
      sb.append(representativeRegionIndex);
    }
    String s = sb.toString();
    Parcel p = Parcel.obtain();
    p.writeString(s);
    boolean ret = _updateRegionSEFData(updatePositionMs, p);
    p.recycle();
    this.mSpeedRegions.clear();
    return ret;
  }

  public static class SuperSlowRegion implements Parcelable {
    static final Parcelable.Creator<SuperSlowRegion> CREATOR =
        new Parcelable.Creator<
            SuperSlowRegion>() { // from class:
                                 // com.samsung.android.media.SemMediaPlayer.SuperSlowRegion.1
          /* JADX WARN: Can't rename method to resolve collision */
          @Override // android.os.Parcelable.Creator
          public SuperSlowRegion createFromParcel(Parcel in) {
            return new SuperSlowRegion(in);
          }

          /* JADX WARN: Can't rename method to resolve collision */
          @Override // android.os.Parcelable.Creator
          public SuperSlowRegion[] newArray(int size) {
            return new SuperSlowRegion[size];
          }
        };
    int endTime;
    int startTime;
    int type;
    public final int CANCELED_REGION = 0;
    public final int TITLE_REGION = 1;
    public final int NORMAL_REGION = 2;

    SuperSlowRegion(Parcel in) {
      this.type = in.readInt();
      this.startTime = in.readInt();
      this.endTime = in.readInt();
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
    public int describeContents() {
      return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
      dest.writeInt(this.type);
      dest.writeInt(this.startTime);
      dest.writeInt(this.endTime);
    }
  }

  public SuperSlowRegion[] getSuperSlowRegions() {
    return this.mSuperSlowInfo;
  }

  public void setBackgroundMusic(SemBackgroundMusic semBgm) throws IllegalStateException {
    if (semBgm == null) {
      throw new NullPointerException("SemBackgroundMusic param can not be null.");
    }
    Parcel p = semBgm.writeToParcel(null);
    _setBackgroundMusic(p);
    p.recycle();
  }

  public void setVideoFilterLevel(int filterLevel)
      throws IllegalStateException, UnsupportedOperationException {
    if (filterLevel < 0) {
      throw new IllegalArgumentException("filterLevel(" + filterLevel + ") is smaller than 0");
    }
    if (filterLevel > 100) {
      throw new IllegalArgumentException("filterLevel(" + filterLevel + ") is greater than 100");
    }
    _setVideoFilterLevel(filterLevel);
  }

  private static class StreamEventCallback extends AudioTrack.StreamEventCallback {
    public long mAJavaAudioTrackPtr;
    public long mNativeCallbackPtr;
    public long mUserDataPtr;

    StreamEventCallback(long jAudioTrackPtr, long nativeCallbackPtr, long userDataPtr) {
      this.mAJavaAudioTrackPtr = jAudioTrackPtr;
      this.mNativeCallbackPtr = nativeCallbackPtr;
      this.mUserDataPtr = userDataPtr;
    }

    @Override // android.media.AudioTrack.StreamEventCallback
    public void onTearDown(AudioTrack track) {
      SemMediaPlayer.native_stream_event_onTearDown(this.mNativeCallbackPtr, this.mUserDataPtr);
    }

    @Override // android.media.AudioTrack.StreamEventCallback
    public void onPresentationEnded(AudioTrack track) {
      SemMediaPlayer.native_stream_event_onStreamPresentationEnd(
          this.mNativeCallbackPtr, this.mUserDataPtr);
    }

    @Override // android.media.AudioTrack.StreamEventCallback
    public void onDataRequest(AudioTrack track, int size) {
      SemMediaPlayer.native_stream_event_onStreamDataRequest(
          this.mAJavaAudioTrackPtr, this.mNativeCallbackPtr, this.mUserDataPtr, size);
    }
  }

  private static class StreamEventExecutor implements Executor {
    private StreamEventExecutor() {}

    @Override // java.util.concurrent.Executor
    public void execute(Runnable command) {
      command.run();
    }
  }

  public static final class DynamicViewingConfiguration {
    private int mEndTimeMs;
    private float mSpeedRate;
    private int mStartTimeMs;

    public DynamicViewingConfiguration(int startTimeMs, int endTimeMs, float speedRate) {
      if (startTimeMs < 0) {
        throw new IllegalArgumentException(
            "DynamicViewingConfiguration startTimeMs is less than zero");
      }
      if (endTimeMs < 0) {
        throw new IllegalArgumentException(
            "DynamicViewingConfiguration endTimeMs is less than zero");
      }
      if (speedRate <= 0.0f) {
        throw new IllegalArgumentException(
            "DynamicViewingConfiguration speedRate is less or equal than zero");
      }
      this.mStartTimeMs = startTimeMs;
      this.mEndTimeMs = endTimeMs;
      this.mSpeedRate = speedRate;
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

  public void setDynamicViewingConfigurations(
      List<DynamicViewingConfiguration> dynamicViewingConfigs, boolean delegatePlaybackControl)
      throws IllegalStateException, IllegalArgumentException {
    internalSetDynamicViewingConfigurations(dynamicViewingConfigs, delegatePlaybackControl);
  }

  public void setDynamicViewingConfigurations(
      List<DynamicViewingConfiguration> dynamicViewingConfigs)
      throws IllegalStateException, IllegalArgumentException {
    internalSetDynamicViewingConfigurations(dynamicViewingConfigs, false);
  }

  private void internalSetDynamicViewingConfigurations(
      List<DynamicViewingConfiguration> dynamicViewingConfigs, boolean delegatePlaybackControl)
      throws IllegalStateException, IllegalArgumentException {
    if (dynamicViewingConfigs == null) {
      throw new NullPointerException("dynamicViewingConfigs can not be null.");
    }
    if (dynamicViewingConfigs.isEmpty()) {
      throw new IllegalArgumentException("dynamicViewingConfigs is empty.");
    }
    Parcel request = Parcel.obtain();
    try {
      request.writeInt(dynamicViewingConfigs.size());
      for (DynamicViewingConfiguration config : dynamicViewingConfigs) {
        int startTimeMs = config.getStartTime();
        int endTimeMs = config.getEndTime();
        if (!delegatePlaybackControl && startTimeMs >= endTimeMs) {
          throw new IllegalArgumentException(
              "DynamicViewingConfiguration startTimeMs is equal or greater than endTimeMs in not"
                  + " delegated");
        }
        request.writeInt(startTimeMs);
        request.writeInt(endTimeMs);
        request.writeFloat(config.getSpeedRate());
      }
      if (!delegatePlaybackControl) {
        if (!setParameter(KEY_PARAMETER_DYNAMIC_VIEW_CONFIGURATION, request)) {
          throw new IllegalStateException(
              "setDynamicViewingConfigurations is called after init().");
        }
      } else if (!setParameter(KEY_PARAMETER_DYNAMIC_VIEW_DELEGATE_CONFIGURATION, request)) {
        throw new IllegalStateException(
            "setDynamicViewingConfigurations delegatePlaybackControl failed");
      }
    } finally {
      request.recycle();
    }
  }

  public void setAudioAttributes(AudioAttributes attributes) throws IllegalStateException {
    _setAudioAttributes(attributes);
    this.mAttributes = attributes;
  }

  private static boolean availableMimeTypeForExternalSource(String mimeType) {
    if ("application/x-subrip".equals(mimeType)) {
      return true;
    }
    return false;
  }

  public void addTimedTextSource(String path, String mimeType)
      throws IOException, IllegalArgumentException, IllegalStateException {
    if (!availableMimeTypeForExternalSource(mimeType)) {
      String msg = "Illegal mimeType for timed text source: " + mimeType;
      throw new IllegalArgumentException(msg);
    }
    if (path == null) {
      throw new IllegalArgumentException("Illegal path");
    }
    File file = new File(path);
    if (file.exists()) {
      FileInputStream is = new FileInputStream(file);
      try {
        Parcel request = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        ParcelFileDescriptor pfd = ParcelFileDescriptor.dup(is.getFD());
        try {
          Log.m94d(TAG, "send invoke key : INVOKE_ID_ADD_EXTERNAL_SOURCE_FD");
          request.writeInt(3);
          request.writeInt(pfd.detachFd());
          request.writeLong(0L);
          request.writeLong(576460752303423487L);
          request.writeString(mimeType);
          invoke(request, reply);
          is.close();
          populateInbandTracks();
          return;
        } finally {
          request.recycle();
          reply.recycle();
        }
      } catch (Throwable th) {
        is.close();
        throw th;
      }
    }
    throw new IOException(path);
  }

  public void removeOutbandTimedTextSources() throws IllegalStateException {
    int outOfBandSubtitleTrackNum;
    TrackInfo[] inbandtrackInfo = getInbandTrackInfo();
    synchronized (this.mIndexTrackPairs) {
      outOfBandSubtitleTrackNum = this.mIndexTrackPairs.size() - inbandtrackInfo.length;
      Parcel request = Parcel.obtain();
      Parcel reply = Parcel.obtain();
      try {
        request.writeInt(8);
        invoke(request, reply);
        request.recycle();
        reply.recycle();
        this.mIndexTrackPairs.clear();
        this.mInbandTrackIndices.clear();
      } catch (Throwable th) {
        request.recycle();
        reply.recycle();
        throw th;
      }
    }
    populateInbandTracks();
    synchronized (this.mIndexTrackPairs) {
      SubtitleController subtitleController = this.mSubtitleController;
      if (subtitleController != null) {
        SubtitleTrack[] subtitiletracks = subtitleController.getTracks();
        for (int i = subtitiletracks.length - outOfBandSubtitleTrackNum;
            i < subtitiletracks.length && i >= 0;
            i++) {
          this.mIndexTrackPairs.add(Pair.create(null, subtitiletracks[i]));
        }
      }
    }
  }

  public void setSubtitleControllerAndAnchor(Context context, VideoView anchor) {
    SubtitleController controller = new SubtitleController(context, getMediaTimeProvider(), this);
    controller.registerRenderer(new WebVttRenderer(context));
    controller.registerRenderer(new TtmlRenderer(context));
    controller.registerRenderer(new ClosedCaptionRenderer(context));
    controller.registerRenderer(new Cea708CaptionRenderer(context));
    this.mSubtitleController = controller;
    controller.setAnchor(anchor);
  }

  private synchronized void setSubtitleAnchor() {
    if (this.mSubtitleController == null && ActivityThread.currentApplication() != null) {
      final TimeProvider timeProvider = (TimeProvider) getMediaTimeProvider();
      final HandlerThread thread = new HandlerThread("SetSubtitleAnchorThread");
      thread.start();
      Handler handler = new Handler(thread.getLooper());
      handler.post(
          new Runnable() { // from class: com.samsung.android.media.SemMediaPlayer.1
            @Override // java.lang.Runnable
            public void run() {
              Context context = ActivityThread.currentApplication();
              SemMediaPlayer.this.mSubtitleController =
                  new SubtitleController(context, timeProvider, SemMediaPlayer.this);
              SemMediaPlayer.this.mSubtitleController.setAnchor(
                  new SubtitleController
                      .Anchor() { // from class: com.samsung.android.media.SemMediaPlayer.1.1
                    @Override // android.media.SubtitleController.Anchor
                    public void setSubtitleWidget(SubtitleTrack.RenderingWidget subtitleWidget) {}

                    @Override // android.media.SubtitleController.Anchor
                    public Looper getSubtitleLooper() {
                      return timeProvider.mEventHandler.getLooper();
                    }
                  });
              thread.getLooper().quitSafely();
            }
          });
      try {
        thread.join();
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        Log.m102w(TAG, "failed to join SetSubtitleAnchorThread");
      }
    }
  }

  @Override // android.media.SubtitleController.Listener
  public void onSubtitleTrackSelected(SubtitleTrack track) {
    int i = this.mSelectedSubtitleTrackIndex;
    if (i >= 0) {
      try {
        selectOrDeselectInbandTrack(i, false);
      } catch (IllegalStateException e) {
      }
      this.mSelectedSubtitleTrackIndex = -1;
    }
    synchronized (this) {
      this.mSubtitleDataListenerDisabled = true;
    }
    if (track == null) {
      return;
    }
    synchronized (this.mIndexTrackPairs) {
      Iterator<Pair<Integer, SubtitleTrack>> it = this.mIndexTrackPairs.iterator();
      while (true) {
        if (!it.hasNext()) {
          break;
        }
        Pair<Integer, SubtitleTrack> p = it.next();
        if (p.first != null && p.second == track) {
          this.mSelectedSubtitleTrackIndex = p.first.intValue();
          break;
        }
      }
    }
    int i2 = this.mSelectedSubtitleTrackIndex;
    if (i2 >= 0) {
      try {
        selectOrDeselectInbandTrack(i2, true);
      } catch (IllegalStateException e2) {
      }
      synchronized (this) {
        this.mSubtitleDataListenerDisabled = false;
      }
    }
  }

  public void addSubtitleSource(final InputStream is, final MediaFormat format)
      throws IllegalStateException, IllegalArgumentException {
    if (format == null) {
      throw new IllegalArgumentException("Illegal null format");
    }
    if (is != null) {
      synchronized (this.mOpenSubtitleSources) {
        this.mOpenSubtitleSources.add(is);
      }
    } else {
      Log.m102w(TAG, "addSubtitleSource called with null InputStream");
    }
    getMediaTimeProvider();
    final HandlerThread thread = new HandlerThread("SubtitleReadThread", -5);
    thread.start();
    Handler handler = new Handler(thread.getLooper());
    handler.post(
        new Runnable() { // from class: com.samsung.android.media.SemMediaPlayer.3
          private int addTrack() {
            SubtitleTrack track;
            if (is == null
                || SemMediaPlayer.this.mSubtitleController == null
                || (track = SemMediaPlayer.this.mSubtitleController.addTrack(format)) == null) {
              return 901;
            }
            try {
              int availableSize = is.available();
              if (availableSize > 20971520) {
                Log.m96e(SemMediaPlayer.TAG, "addTrack() unsupported size : " + availableSize);
                return 901;
              }
              Scanner scanner = null;
              try {
                try {
                  scanner = new Scanner(is, "UTF-8");
                  String contents = scanner.useDelimiter("\\A").next();
                  synchronized (SemMediaPlayer.this.mOpenSubtitleSources) {
                    SemMediaPlayer.this.mOpenSubtitleSources.remove(is);
                  }
                  scanner.close();
                  if (contents == null) {
                    return 901;
                  }
                  synchronized (SemMediaPlayer.this.mIndexTrackPairs) {
                    SemMediaPlayer.this.mIndexTrackPairs.add(Pair.create(null, track));
                  }
                  try {
                    Handler h = SemMediaPlayer.this.mTimeProvider.mEventHandler;
                    Pair<SubtitleTrack, byte[]> trackData = Pair.create(track, contents.getBytes());
                    Message m = h.obtainMessage(2, 4, 0, trackData);
                    h.sendMessage(m);
                    return 803;
                  } catch (NullPointerException e) {
                    Log.m97e(SemMediaPlayer.TAG, "handleMessage is NullPointerException e : ", e);
                    return 901;
                  }
                } catch (Exception e2) {
                  Log.m97e(SemMediaPlayer.TAG, e2.getMessage(), e2);
                  synchronized (SemMediaPlayer.this.mOpenSubtitleSources) {
                    SemMediaPlayer.this.mOpenSubtitleSources.remove(is);
                    if (scanner != null) {
                      scanner.close();
                    }
                    return 901;
                  }
                }
              } catch (Throwable th) {
                synchronized (SemMediaPlayer.this.mOpenSubtitleSources) {
                  SemMediaPlayer.this.mOpenSubtitleSources.remove(is);
                  if (scanner != null) {
                    scanner.close();
                  }
                  throw th;
                }
              }
            } catch (IOException e3) {
              Log.m97e(SemMediaPlayer.TAG, e3.getMessage(), e3);
              return 901;
            }
          }

          @Override // java.lang.Runnable
          public void run() {
            int res = addTrack();
            if (SemMediaPlayer.this.mEventHandler != null) {
              Message m = SemMediaPlayer.this.mEventHandler.obtainMessage(200, res, 0, null);
              SemMediaPlayer.this.mEventHandler.sendMessage(m);
            }
            thread.getLooper().quitSafely();
          }
        });
  }

  public void removeOutbandSubtitleSources() throws IllegalStateException {
    Log.m94d(TAG, "removeOutbandSubtitleSources");
    if (this.mSubtitleController == null) {
      Log.m96e(TAG, "Should have subtitle controller already set");
      return;
    }
    this.mSelectedSubtitleTrackIndex = -1;
    synchronized (this.mOpenSubtitleSources) {
      if (!this.mOpenSubtitleSources.isEmpty()) {
        Iterator<InputStream> it = this.mOpenSubtitleSources.iterator();
        while (it.hasNext()) {
          InputStream is = it.next();
          try {
            is.close();
          } catch (IOException e) {
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
  public void scanInternalSubtitleTracks() {
    setSubtitleAnchor();
    populateInbandTracks();
    SubtitleController subtitleController = this.mSubtitleController;
    if (subtitleController != null) {
      subtitleController.selectDefaultTrack();
    }
  }

  private void populateInbandTracks() {
    populateInbandTracks(null);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void populateInbandTracks(TrackInfo[] trackInfo) {
    TrackInfo[] tracks;
    if (trackInfo == null) {
      tracks = getInbandTrackInfo();
    } else {
      tracks = trackInfo;
    }
    synchronized (this.mIndexTrackPairs) {
      for (int i = 0; i < tracks.length; i++) {
        if (!this.mInbandTrackIndices.get(i)) {
          this.mInbandTrackIndices.set(i);
          if (tracks[i] == null) {
            Log.m102w(TAG, "unexpected NULL track at index " + i);
          }
          if (tracks[i] != null && tracks[i].getTrackType() == 4) {
            SubtitleTrack track = this.mSubtitleController.addTrack(tracks[i].getFormat());
            this.mIndexTrackPairs.add(Pair.create(Integer.valueOf(i), track));
          } else {
            this.mIndexTrackPairs.add(Pair.create(Integer.valueOf(i), null));
          }
        }
      }
    }
  }

  public void setTemporalZoom(int scaleUp)
      throws IllegalStateException, IllegalArgumentException, UnsupportedOperationException {
    _setTemporalZoom(scaleUp);
  }
}
