package android.media;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.database.Cursor;
import android.media.audiofx.HapticGenerator;
import android.net.Uri;
import android.p009os.Binder;
import android.p009os.RemoteException;
import android.p009os.Trace;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.C4337R;
import com.samsung.android.audio.Rune;
import com.samsung.android.audio.SoundTheme;
import com.samsung.android.media.AudioTag;
import java.io.IOException;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public class Ringtone {
  private static final boolean LOGD = true;
  private static final String MEDIA_SELECTION =
      "mime_type LIKE 'audio/%' OR mime_type IN ('application/ogg', 'application/x-flac')";
  private static final String TAG = "Ringtone";
  private final boolean mAllowRemote;
  private final AudioManager mAudioManager;
  private final Context mContext;
  private VolumeShaper mCustomShaper;
  private HapticGenerator mHapticGenerator;
  private boolean mIsTelecomPackage;
  private MediaPlayer mLocalPlayer;
  private boolean mPreferBuiltinDevice;
  private final IRingtonePlayer mRemotePlayer;
  private final Binder mRemoteToken;
  private String mTitle;
  private Uri mUri;
  private boolean mUriStatus;
  private VolumeShaper mVolumeShaper;
  private VolumeShaper.Configuration mVolumeShaperConfig;
  private static final String[] MEDIA_COLUMNS = {"_id", "title"};
  private static final ArrayList<Ringtone> sActiveRingtones = new ArrayList<>();
  private final MyOnCompletionListener mCompletionListener = new MyOnCompletionListener();
  private AudioAttributes mAudioAttributes =
      new AudioAttributes.Builder().setUsage(6).setContentType(4).build();
  private boolean mIsLooping = false;
  private float mVolume = 1.0f;
  private boolean mHapticGeneratorEnabled = false;
  private final Object mPlaybackSettingsLock = new Object();
  private boolean mExternalRingtone = false;
  private boolean mNeedFadeIn = true;
  private int mStartPosition = 0;

  public Ringtone(Context context, boolean allowRemote) {
    this.mContext = context;
    AudioManager audioManager = (AudioManager) context.getSystemService("audio");
    this.mAudioManager = audioManager;
    this.mAllowRemote = allowRemote;
    this.mRemotePlayer = allowRemote ? audioManager.getRingtonePlayer() : null;
    this.mRemoteToken = allowRemote ? new Binder() : null;
    setupCustomRoutine();
  }

  @Deprecated
  public void setStreamType(int streamType) {
    PlayerBase.deprecateStreamTypeForPlayback(streamType, "Ringtone", "setStreamType()");
    setAudioAttributes(
        new AudioAttributes.Builder().setInternalLegacyStreamType(streamType).build());
  }

  @Deprecated
  public int getStreamType() {
    return AudioAttributes.toLegacyStreamType(this.mAudioAttributes);
  }

  public void setAudioAttributes(AudioAttributes attributes) throws IllegalArgumentException {
    setAudioAttributesField(attributes);
    setUri(this.mUri, this.mVolumeShaperConfig);
    createLocalMediaPlayer();
  }

  public void setAudioAttributesField(AudioAttributes attributes) {
    if (attributes == null) {
      throw new IllegalArgumentException("Invalid null AudioAttributes for Ringtone");
    }
    this.mAudioAttributes = attributes;
  }

  private AudioDeviceInfo getBuiltinDevice(AudioManager audioManager) {
    AudioDeviceInfo[] deviceList = audioManager.getDevices(2);
    for (AudioDeviceInfo device : deviceList) {
      if (device.getType() == 2) {
        return device;
      }
    }
    return null;
  }

  public boolean preferBuiltinDevice(boolean enable) {
    this.mPreferBuiltinDevice = enable;
    MediaPlayer mediaPlayer = this.mLocalPlayer;
    if (mediaPlayer == null) {
      return true;
    }
    return mediaPlayer.setPreferredDevice(getBuiltinDevice(this.mAudioManager));
  }

  public boolean createLocalMediaPlayer() {
    Trace.beginSection("createLocalMediaPlayer");
    if (this.mUri == null) {
      Log.m96e("Ringtone", "Could not create media player as no URI was provided.");
      return this.mAllowRemote && this.mRemotePlayer != null;
    }
    destroyLocalPlayer();
    if (this.mIsTelecomPackage && this.mExternalRingtone) {
      if (this.mAllowRemote && this.mRemotePlayer != null && isValidUri(this.mUri)) {
        this.mUriStatus = true;
      } else {
        this.mUriStatus = false;
      }
      Log.m102w("Ringtone", "[Telecom] Remote playback allowed: " + this.mUriStatus);
    } else {
      MediaPlayer mediaPlayer = new MediaPlayer();
      this.mLocalPlayer = mediaPlayer;
      try {
        mediaPlayer.setDataSource(this.mContext, this.mUri);
        this.mLocalPlayer.setAudioAttributes(this.mAudioAttributes);
        this.mLocalPlayer.setPreferredDevice(
            this.mPreferBuiltinDevice ? getBuiltinDevice(this.mAudioManager) : null);
        synchronized (this.mPlaybackSettingsLock) {
          applyPlaybackProperties_sync();
        }
        VolumeShaper.Configuration configuration = this.mVolumeShaperConfig;
        if (configuration != null) {
          this.mVolumeShaper = this.mLocalPlayer.createVolumeShaper(configuration);
        }
        this.mLocalPlayer.prepare();
        this.mUriStatus = true;
      } catch (IOException | SecurityException e) {
        destroyLocalPlayer();
        if (!this.mAllowRemote) {
          Log.m102w("Ringtone", "Remote playback not allowed: " + e);
        }
        if (this.mIsTelecomPackage
            && this.mAllowRemote
            && this.mRemotePlayer != null
            && isValidUri(this.mUri)) {
          this.mUriStatus = true;
        } else {
          this.mUriStatus = false;
        }
      }
      if (this.mLocalPlayer != null) {
        Log.m94d("Ringtone", "Successfully created local player");
      } else {
        Log.m94d("Ringtone", "Problem opening; delegating to remote player");
      }
    }
    Trace.endSection();
    return this.mLocalPlayer != null || (this.mAllowRemote && this.mRemotePlayer != null);
  }

  public boolean hasHapticChannels() {
    try {
      Trace.beginSection("Ringtone.hasHapticChannels");
      MediaPlayer mediaPlayer = this.mLocalPlayer;
      if (mediaPlayer != null) {
        for (MediaPlayer.TrackInfo trackInfo : mediaPlayer.getTrackInfo()) {
          if (trackInfo.hasHapticChannels()) {
            Trace.endSection();
            return true;
          }
        }
      }
      return false;
    } finally {
      Trace.endSection();
    }
  }

  public boolean hasLocalPlayer() {
    return this.mLocalPlayer != null;
  }

  public boolean hasRemotePlayer() {
    return this.mAllowRemote && this.mRemotePlayer != null;
  }

  public AudioAttributes getAudioAttributes() {
    return this.mAudioAttributes;
  }

  public void setLooping(boolean looping) {
    synchronized (this.mPlaybackSettingsLock) {
      this.mIsLooping = looping;
      applyPlaybackProperties_sync();
    }
  }

  public boolean isLooping() {
    boolean z;
    synchronized (this.mPlaybackSettingsLock) {
      z = this.mIsLooping;
    }
    return z;
  }

  public void setVolume(float volume) {
    synchronized (this.mPlaybackSettingsLock) {
      if (volume < 0.0f) {
        volume = 0.0f;
      }
      if (volume > 1.0f) {
        volume = 1.0f;
      }
      this.mVolume = volume;
      applyPlaybackProperties_sync();
    }
  }

  public float getVolume() {
    float f;
    synchronized (this.mPlaybackSettingsLock) {
      f = this.mVolume;
    }
    return f;
  }

  public boolean setHapticGeneratorEnabled(boolean enabled) {
    if (!HapticGenerator.isAvailable()) {
      return false;
    }
    synchronized (this.mPlaybackSettingsLock) {
      this.mHapticGeneratorEnabled = enabled;
      applyPlaybackProperties_sync();
    }
    return true;
  }

  public boolean isHapticGeneratorEnabled() {
    boolean z;
    synchronized (this.mPlaybackSettingsLock) {
      z = this.mHapticGeneratorEnabled;
    }
    return z;
  }

  private void applyPlaybackProperties_sync() {
    IRingtonePlayer iRingtonePlayer;
    MediaPlayer mediaPlayer = this.mLocalPlayer;
    if (mediaPlayer != null) {
      mediaPlayer.setVolume(this.mVolume);
      this.mLocalPlayer.setLooping(this.mIsLooping);
      if (this.mHapticGenerator == null && this.mHapticGeneratorEnabled) {
        this.mHapticGenerator = HapticGenerator.create(this.mLocalPlayer.getAudioSessionId());
      }
      HapticGenerator hapticGenerator = this.mHapticGenerator;
      if (hapticGenerator != null) {
        hapticGenerator.setEnabled(this.mHapticGeneratorEnabled);
        return;
      }
      return;
    }
    if (this.mAllowRemote && (iRingtonePlayer = this.mRemotePlayer) != null) {
      try {
        iRingtonePlayer.setPlaybackProperties(
            this.mRemoteToken, this.mVolume, this.mIsLooping, this.mHapticGeneratorEnabled);
        return;
      } catch (RemoteException e) {
        Log.m103w("Ringtone", "Problem setting playback properties: ", e);
        return;
      }
    }
    Log.m102w(
        "Ringtone", "Neither local nor remote player available when applying playback properties");
  }

  public String getTitle(Context context) {
    String str = this.mTitle;
    if (str != null) {
      return str;
    }
    String title = getTitle(context, this.mUri, true, this.mAllowRemote);
    this.mTitle = title;
    return title;
  }

  /* JADX WARN: Code restructure failed: missing block: B:41:0x0089, code lost:

     if (r10 != null) goto L35;
  */
  /* JADX WARN: Code restructure failed: missing block: B:61:0x00bf, code lost:

     if (0 == 0) goto L36;
  */
  /* JADX WARN: Removed duplicated region for block: B:13:? A[RETURN, SYNTHETIC] */
  /* JADX WARN: Removed duplicated region for block: B:8:0x00d2  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public static String getTitle(
      Context context,
      Uri uri,
      boolean followSettingsUri,
      boolean allowRemote,
      boolean withThemeTitle) {
    ContentResolver res = context.getContentResolver();
    String title = null;
    if (uri != null) {
      String authority = ContentProvider.getAuthorityWithoutUserId(uri.getAuthority());
      if (!"settings".equals(authority)) {
        Cursor cursor = null;
        try {
          try {
            if ("media".equals(authority)) {
              String mediaSelection = allowRemote ? null : MEDIA_SELECTION;
              cursor =
                  res.query(
                      uri,
                      Rune.SEC_AUDIO_SUPPORT_SOUND_THEME
                          ? SoundTheme.SOUND_THEME_MEDIA_COLUMNS
                          : MEDIA_COLUMNS,
                      mediaSelection,
                      null,
                      null);
              if (cursor != null && cursor.getCount() == 1) {
                cursor.moveToFirst();
                if (isOpenThemeRingtone(cursor)) {
                  title = changeThemeTitle(context, uri);
                } else {
                  if (!Rune.SEC_AUDIO_SUPPORT_SOUND_THEME || !withThemeTitle) {
                    return cursor.getString(1);
                  }
                  title = SoundTheme.getTitleIncludingTheme(context, cursor);
                }
              }
            }
          } finally {
            if (0 != 0) {
              cursor.close();
            }
          }
        } catch (IllegalArgumentException
            | IllegalStateException
            | UnsupportedOperationException e) {
          title = context.getString(C4337R.string.ringtone_unknown);
          e.printStackTrace();
          if (0 != 0) {
            cursor.close();
          }
          if (title == null) {
            title = uri.getLastPathSegment();
          }
          if (title == null) {}
        } catch (SecurityException e2) {
          IRingtonePlayer mRemotePlayer = null;
          if (allowRemote) {
            AudioManager audioManager = (AudioManager) context.getSystemService("audio");
            mRemotePlayer = audioManager.getRingtonePlayer();
          }
          if (mRemotePlayer != null) {
            try {
              title = mRemotePlayer.getTitle(uri);
            } catch (RemoteException e3) {
            }
          }
        }
      } else if (followSettingsUri) {
        Uri actualUri =
            RingtoneManager.getActualDefaultRingtoneUri(
                context, RingtoneManager.getDefaultType(uri));
        String actualTitle = getTitle(context, actualUri, false, allowRemote, withThemeTitle);
        title = context.getString(C4337R.string.ringtone_default_with_actual, actualTitle);
      }
    } else {
      title = context.getString(C4337R.string.sec_ringtone_silent);
    }
    if (title == null) {
      return title;
    }
    String title2 = context.getString(C4337R.string.ringtone_unknown);
    return title2 == null ? "" : title2;
  }

  public void setUri(Uri uri) {
    setUri(uri, null);
  }

  public void setVolumeShaperConfig(VolumeShaper.Configuration volumeShaperConfig) {
    this.mVolumeShaperConfig = volumeShaperConfig;
  }

  public void setUri(Uri uri, VolumeShaper.Configuration volumeShaperConfig) {
    this.mVolumeShaperConfig = volumeShaperConfig;
    this.mUri = uri;
    if (uri == null) {
      destroyLocalPlayer();
    } else if (uri.toString().startsWith(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI.toString())) {
      this.mExternalRingtone = true;
      Log.m94d("Ringtone", "setUri( mExternalRingtone =" + this.mExternalRingtone + " )");
    }
    int highlightOffset = getHighlightOffset(this.mUri);
    if (highlightOffset != -1) {
      this.mStartPosition = highlightOffset;
    }
    if (this.mIsTelecomPackage) {
      addTag(AudioTag.AUDIO_STREAM_RING);
    }
  }

  public Uri getUri() {
    return this.mUri;
  }

  public void play() {
    Uri uri;
    boolean looping;
    float volume;
    if (this.mLocalPlayer != null) {
      if (this.mStartPosition > 0) {
        Log.m94d("Ringtone", "Play from highlight " + this.mStartPosition + " mSec");
        this.mLocalPlayer.seekTo(this.mStartPosition);
        this.mStartPosition = 0;
      }
      if (this.mAudioManager.getStreamVolume(
              AudioAttributes.toLegacyStreamType(this.mAudioAttributes))
          != 0) {
        startLocalPlayer();
        return;
      }
      if (!this.mAudioAttributes.areHapticChannelsMuted() && hasHapticChannels()) {
        startLocalPlayer();
        return;
      }
      if (Rune.SEC_AUDIO_SUPPORT_ACH_RINGTONE
          && this.mAudioAttributes.getTags().contains(AudioTag.RINGTONE_HAPTIC)) {
        Log.m94d("Ringtone", "Play haptic tag ringtone");
        startLocalPlayer();
        return;
      } else {
        if (getStreamType() == 3) {
          Log.m94d("Ringtone", "Play music ringtone");
          startLocalPlayer();
          return;
        }
        return;
      }
    }
    if (this.mAllowRemote && this.mRemotePlayer != null && (uri = this.mUri) != null) {
      Uri canonicalUri = uri.getCanonicalUri();
      synchronized (this.mPlaybackSettingsLock) {
        looping = this.mIsLooping;
        volume = this.mVolume;
      }
      try {
        this.mRemotePlayer.playWithVolumeShaping(
            this.mRemoteToken,
            canonicalUri,
            this.mAudioAttributes,
            volume,
            looping,
            this.mVolumeShaperConfig);
        return;
      } catch (RemoteException e) {
        if (!playFallbackRingtone()) {
          Log.m102w("Ringtone", "Problem playing ringtone: " + e);
          return;
        }
        return;
      }
    }
    if (!playFallbackRingtone()) {
      Log.m102w("Ringtone", "Neither local nor remote playback available");
    }
  }

  public void stop() {
    IRingtonePlayer iRingtonePlayer;
    if (this.mLocalPlayer != null) {
      destroyLocalPlayer();
      return;
    }
    if (this.mAllowRemote && (iRingtonePlayer = this.mRemotePlayer) != null) {
      try {
        iRingtonePlayer.stop(this.mRemoteToken);
      } catch (RemoteException e) {
        Log.m102w("Ringtone", "Problem stopping ringtone: " + e);
      }
    }
  }

  private void destroyLocalPlayer() {
    if (this.mLocalPlayer != null) {
      HapticGenerator hapticGenerator = this.mHapticGenerator;
      if (hapticGenerator != null) {
        hapticGenerator.release();
        this.mHapticGenerator = null;
      }
      this.mLocalPlayer.setVolume(0.0f, 0.0f);
      this.mLocalPlayer.setOnCompletionListener(null);
      this.mLocalPlayer.reset();
      this.mLocalPlayer.release();
      this.mLocalPlayer = null;
      this.mVolumeShaper = null;
      ArrayList<Ringtone> arrayList = sActiveRingtones;
      synchronized (arrayList) {
        arrayList.remove(this);
      }
    }
  }

  private void startLocalPlayer() {
    if (this.mLocalPlayer == null) {
      return;
    }
    ArrayList<Ringtone> arrayList = sActiveRingtones;
    synchronized (arrayList) {
      arrayList.add(this);
    }
    this.mLocalPlayer.setOnCompletionListener(this.mCompletionListener);
    this.mLocalPlayer.start();
    try {
      VolumeShaper volumeShaper = this.mVolumeShaper;
      if (volumeShaper != null) {
        volumeShaper.apply(VolumeShaper.Operation.PLAY);
      }
    } catch (IllegalArgumentException | IllegalStateException e) {
      Log.m103w("Ringtone", "mLocalPlayer :: startLocalPlayer error", e);
    }
  }

  public boolean isPlaying() {
    IRingtonePlayer iRingtonePlayer;
    MediaPlayer mediaPlayer = this.mLocalPlayer;
    if (mediaPlayer != null) {
      return mediaPlayer.isPlaying();
    }
    if (this.mAllowRemote && (iRingtonePlayer = this.mRemotePlayer) != null) {
      try {
        return iRingtonePlayer.isPlaying(this.mRemoteToken);
      } catch (RemoteException e) {
        Log.m102w("Ringtone", "Problem checking ringtone: " + e);
        return false;
      }
    }
    Log.m102w("Ringtone", "Neither local nor remote playback available");
    return false;
  }

  private boolean playFallbackRingtone() {
    int streamType = AudioAttributes.toLegacyStreamType(this.mAudioAttributes);
    if (this.mAudioManager.getStreamVolume(streamType) == 0) {
      return false;
    }
    int ringtoneType = RingtoneManager.getDefaultType(this.mUri);
    if (ringtoneType != -1
        && RingtoneManager.getActualDefaultRingtoneUri(this.mContext, ringtoneType) == null) {
      Log.m102w("Ringtone", "not playing fallback for " + this.mUri);
      return false;
    }
    try {
      AssetFileDescriptor afd =
          this.mContext.getResources().openRawResourceFd(C4337R.raw.fallbackring);
      if (this.mAudioAttributes.getUsage() != 6) {
        Log.m94d("Ringtone", "play playFallbackRingtone: fallbacknoti");
        afd = this.mContext.getResources().openRawResourceFd(C4337R.raw.fallbacknoti);
      }
      if (afd == null) {
        Log.m96e("Ringtone", "Could not load fallback ringtone");
        return false;
      }
      this.mLocalPlayer = new MediaPlayer();
      if (afd.getDeclaredLength() < 0) {
        this.mLocalPlayer.setDataSource(afd.getFileDescriptor());
      } else {
        this.mLocalPlayer.setDataSource(
            afd.getFileDescriptor(), afd.getStartOffset(), afd.getDeclaredLength());
      }
      this.mLocalPlayer.setAudioAttributes(this.mAudioAttributes);
      synchronized (this.mPlaybackSettingsLock) {
        applyPlaybackProperties_sync();
      }
      VolumeShaper.Configuration configuration = this.mVolumeShaperConfig;
      if (configuration != null) {
        this.mVolumeShaper = this.mLocalPlayer.createVolumeShaper(configuration);
      }
      this.mLocalPlayer.prepare();
      startLocalPlayer();
      afd.close();
      return true;
    } catch (Resources.NotFoundException e) {
      Log.m96e("Ringtone", "Fallback ringtone does not exist");
      return false;
    } catch (IOException e2) {
      destroyLocalPlayer();
      Log.m96e("Ringtone", "Failed to open fallback ringtone");
      return false;
    }
  }

  void setTitle(String title) {
    this.mTitle = title;
  }

  protected void finalize() {
    MediaPlayer mediaPlayer = this.mLocalPlayer;
    if (mediaPlayer != null) {
      mediaPlayer.release();
    }
  }

  class MyOnCompletionListener implements MediaPlayer.OnCompletionListener {
    MyOnCompletionListener() {}

    @Override // android.media.MediaPlayer.OnCompletionListener
    public void onCompletion(MediaPlayer mp) {
      synchronized (Ringtone.sActiveRingtones) {
        Ringtone.sActiveRingtones.remove(Ringtone.this);
      }
      mp.setOnCompletionListener(null);
    }
  }

  private void setupCustomRoutine() {
    String packageName = this.mContext.getPackageName();
    this.mIsTelecomPackage = "com.android.server.telecom".equals(packageName);
    this.mUriStatus = false;
  }

  public void setSecForSeek(int seek) {
    this.mStartPosition = seek;
  }

  public void setVolume(float leftVol, float rightVol) {
    MediaPlayer mediaPlayer = this.mLocalPlayer;
    if (mediaPlayer != null) {
      mediaPlayer.setVolume(leftVol, rightVol);
    }
  }

  private boolean isValidUri(Uri uri) {
    if (uri == null) {
      return false;
    }
    int type = RingtoneManager.getDefaultType(uri);
    if (type != -1) {
      uri = RingtoneManager.getActualDefaultRingtoneUri(this.mContext, type);
    }
    if (uri.toString().startsWith(MediaStore.Audio.Media.INTERNAL_CONTENT_URI.toString())) {
      return true;
    }
    try {
      Cursor cs =
          this.mContext.getContentResolver().query(uri, new String[] {"_id"}, null, null, null);
      if (cs != null) {
        try {
          if (cs.getCount() != 0) {
            if (cs != null) {
              cs.close();
            }
            return true;
          }
        } finally {
        }
      }
      if (cs != null) {
        cs.close();
      }
    } catch (Exception e) {
    }
    return false;
  }

  public boolean semIsUriValid() {
    return this.mUriStatus;
  }

  private int getHighlightOffset(Uri uri) {
    int type = RingtoneManager.getDefaultType(uri);
    if (type != -1
        && (uri = RingtoneManager.getActualDefaultRingtoneUri(this.mContext, type)) == null) {
      return -1;
    }
    try {
      String highlightOffset = uri.getQueryParameter("highlight_offset");
      Log.m94d("Ringtone", "highlight offset is : " + highlightOffset);
      if (highlightOffset != null && !highlightOffset.isEmpty()) {
        return Integer.parseInt(highlightOffset);
      }
    } catch (Exception e) {
    }
    return -1;
  }

  public void turnOffFadeIn() {
    this.mNeedFadeIn = false;
    setUri(this.mUri);
  }

  private void addTag(String tag) {
    AudioAttributes audioAttributes = this.mAudioAttributes;
    if (audioAttributes == null || audioAttributes.getTags().contains(tag)) {
      return;
    }
    this.mAudioAttributes = new AudioAttributes.Builder(this.mAudioAttributes).addTag(tag).build();
  }

  public static String getTitle(
      Context context, Uri uri, boolean followSettingsUri, boolean allowRemote) {
    return getTitle(context, uri, followSettingsUri, allowRemote, false);
  }

  public static String getTitleWithSoundTheme(
      Context context, Uri uri, boolean followSettingsUri, boolean allowRemote) {
    return getTitle(context, uri, followSettingsUri, allowRemote, true);
  }

  private static String changeThemeTitle(Context context, Uri uri) {
    if (!RingtoneManager.isInternalRingtoneUri(uri)) {
      return null;
    }
    String themeTitle = context.getString(C4337R.string.sec_ringtone_category_open_theme);
    return themeTitle;
  }

  private static boolean isOpenThemeRingtone(Cursor cursor) {
    String title = cursor.getString(1);
    String displayName = cursor.getString(cursor.getColumnIndex("_display_name"));
    if (TextUtils.isEmpty(title) || !title.startsWith(RingtoneManager.PREFIX_OPEN_THEME)) {
      return !TextUtils.isEmpty(displayName)
          && displayName.startsWith(RingtoneManager.PREFIX_OPEN_THEME);
    }
    return true;
  }

  public void fadeoutRingtone(int delay, float minVolume) {
    IRingtonePlayer iRingtonePlayer;
    VolumeShaper.Configuration FADEOUT_VSHAPE =
        new VolumeShaper.Configuration.Builder()
            .setCurve(new float[] {0.0f, 1.0f}, new float[] {1.0f, minVolume})
            .setInterpolatorType(1)
            .setOptionFlags(2)
            .setDuration(delay)
            .build();
    VolumeShaper.Operation PLAY_CREATE_IF_NEEDED =
        new VolumeShaper.Operation.Builder(VolumeShaper.Operation.PLAY).createIfNeeded().build();
    if (this.mLocalPlayer != null) {
      VolumeShaper volumeShaper = this.mCustomShaper;
      if (volumeShaper != null) {
        volumeShaper.close();
      }
      try {
        VolumeShaper createVolumeShaper = this.mLocalPlayer.createVolumeShaper(FADEOUT_VSHAPE);
        this.mCustomShaper = createVolumeShaper;
        createVolumeShaper.apply(PLAY_CREATE_IF_NEEDED);
        return;
      } catch (IllegalArgumentException | IllegalStateException e) {
        Log.m103w("Ringtone", "mLocalPlayer :: fadeout error", e);
        return;
      }
    }
    if (this.mAllowRemote && (iRingtonePlayer = this.mRemotePlayer) != null) {
      try {
        iRingtonePlayer.fadeoutRingtone(this.mRemoteToken, delay, minVolume);
      } catch (RemoteException e2) {
        Log.m103w("Ringtone", "mRemotePlayer :: fadeout error", e2);
      }
    }
  }

  public void fadeoutRingtone(int delay) {
    fadeoutRingtone(delay, 0.0f);
  }

  public void fadeinRingtone() {
    IRingtonePlayer iRingtonePlayer;
    if (this.mLocalPlayer != null) {
      try {
        VolumeShaper volumeShaper = this.mCustomShaper;
        if (volumeShaper != null) {
          volumeShaper.apply(VolumeShaper.Operation.REVERSE);
          return;
        }
        return;
      } catch (IllegalArgumentException | IllegalStateException e) {
        Log.m103w("Ringtone", "mLocalPlayer :: fadein error", e);
        return;
      }
    }
    if (this.mAllowRemote && (iRingtonePlayer = this.mRemotePlayer) != null) {
      try {
        iRingtonePlayer.fadeinRingtone(this.mRemoteToken);
      } catch (RemoteException e2) {
        Log.m103w("Ringtone", "mRemotePlayer :: fadein error", e2);
      }
    }
  }
}
