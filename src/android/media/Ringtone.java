package android.media;

import android.app.jank.AppJankStats;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.database.Cursor;
import android.media.AudioAttributes;
import android.media.MediaMetrics;
import android.media.MediaPlayer;
import android.media.VolumeShaper;
import android.media.audiofx.HapticGenerator;
import android.net.Uri;
import android.os.Binder;
import android.os.RemoteException;
import android.os.Trace;
import android.os.VibrationAttributes;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.R;
import com.android.internal.hidden_from_bootclasspath.android.media.audio.Flags;
import com.samsung.android.audio.Rune;
import com.samsung.android.audio.SoundTheme;
import com.samsung.android.common.AsPackageName;
import com.samsung.android.media.AudioTag;
import java.io.IOException;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public class Ringtone {
    private static final boolean LOGD = true;
    private static final String MEDIA_SELECTION = "mime_type LIKE 'audio/%' OR mime_type IN ('application/ogg', 'application/x-flac')";
    private static final String TAG = "Ringtone";
    private static final int VIBRATION_LOOP_DELAY_MS = 200;
    private static final String mMetricsId = "audio.service.";
    private final boolean mAllowRemote;
    private final AudioManager mAudioManager;
    private final Context mContext;
    private VolumeShaper mCustomShaper;
    private HapticGenerator mHapticGenerator;
    private boolean mIsTelecomPackage;
    private boolean mIsVibrating;
    private MediaPlayer mLocalPlayer;
    private boolean mPreferBuiltinDevice;
    private final IRingtonePlayer mRemotePlayer;
    private final Binder mRemoteToken;
    private final boolean mRingtoneVibrationSupported;
    private String mTitle;
    private Uri mUri;
    private boolean mUriStatus;
    private VibrationEffect mVibrationEffect;
    private final Vibrator mVibrator;
    private VolumeShaper mVolumeShaper;
    private VolumeShaper.Configuration mVolumeShaperConfig;
    private static final String[] MEDIA_COLUMNS = {"_id", "title"};
    private static final ArrayList<Ringtone> sActiveRingtones = new ArrayList<>();
    private static final VibrationAttributes VIBRATION_ATTRIBUTES = new VibrationAttributes.Builder().setUsage(33).build();
    private final MyOnCompletionListener mCompletionListener = new MyOnCompletionListener();
    private AudioAttributes mAudioAttributes = new AudioAttributes.Builder().setUsage(6).setContentType(4).build();
    private boolean mIsLooping = false;
    private float mVolume = 1.0f;
    private boolean mHapticGeneratorEnabled = false;
    private final Object mPlaybackSettingsLock = new Object();
    private boolean mNeedFadeIn = true;
    private int mStartPosition = 0;

    public Ringtone(Context context, boolean z) {
        this.mContext = context;
        AudioManager audioManager = (AudioManager) context.getSystemService("audio");
        this.mAudioManager = audioManager;
        this.mAllowRemote = z;
        this.mRemotePlayer = z ? audioManager.getRingtonePlayer() : null;
        this.mRemoteToken = z ? new Binder() : null;
        this.mVibrator = (Vibrator) context.getSystemService(Vibrator.class);
        this.mRingtoneVibrationSupported = Utils.isRingtoneVibrationSettingsSupported(context);
        setupCustomRoutine();
    }

    @Deprecated
    public void setStreamType(int i) {
        PlayerBase.deprecateStreamTypeForPlayback(i, "Ringtone", "setStreamType()");
        setAudioAttributes(new AudioAttributes.Builder().setInternalLegacyStreamType(i).build());
    }

    @Deprecated
    public int getStreamType() {
        return AudioAttributes.toLegacyStreamType(this.mAudioAttributes);
    }

    public void setAudioAttributes(AudioAttributes audioAttributes) throws IllegalArgumentException {
        setAudioAttributesField(audioAttributes);
        setUri(this.mUri, this.mVolumeShaperConfig);
        createLocalMediaPlayer();
    }

    public void setAudioAttributesField(AudioAttributes audioAttributes) {
        if (audioAttributes == null) {
            throw new IllegalArgumentException("Invalid null AudioAttributes for Ringtone");
        }
        this.mAudioAttributes = audioAttributes;
    }

    private AudioDeviceInfo getBuiltinDevice(AudioManager audioManager) {
        for (AudioDeviceInfo audioDeviceInfo : audioManager.getDevices(2)) {
            if (audioDeviceInfo.getType() == 2) {
                return audioDeviceInfo;
            }
        }
        return null;
    }

    public boolean preferBuiltinDevice(boolean z) {
        this.mPreferBuiltinDevice = z;
        MediaPlayer mediaPlayer = this.mLocalPlayer;
        if (mediaPlayer == null) {
            return true;
        }
        return mediaPlayer.setPreferredDevice(getBuiltinDevice(this.mAudioManager));
    }

    public boolean createLocalMediaPlayer() {
        Trace.beginSection("createLocalMediaPlayer");
        if (this.mUri == null) {
            Log.e("Ringtone", "Could not create media player as no URI was provided.");
            return this.mAllowRemote && this.mRemotePlayer != null;
        }
        destroyLocalPlayer();
        MediaPlayer mediaPlayer = new MediaPlayer();
        this.mLocalPlayer = mediaPlayer;
        try {
            mediaPlayer.setDataSource(this.mContext, this.mUri);
            this.mLocalPlayer.setAudioAttributes(this.mAudioAttributes);
            this.mLocalPlayer.setPreferredDevice(this.mPreferBuiltinDevice ? getBuiltinDevice(this.mAudioManager) : null);
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
                Log.w("Ringtone", "Remote playback not allowed: " + e);
            }
            if (this.mIsTelecomPackage && this.mAllowRemote && this.mRemotePlayer != null && isValidUri(this.mUri)) {
                this.mUriStatus = true;
            } else {
                this.mUriStatus = false;
            }
        }
        if (this.mLocalPlayer != null) {
            Log.d("Ringtone", "Successfully created local player");
        } else {
            Log.d("Ringtone", "Problem opening; delegating to remote player");
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

    public AudioAttributes getAudioAttributes() {
        return this.mAudioAttributes;
    }

    public void setLooping(boolean z) {
        synchronized (this.mPlaybackSettingsLock) {
            this.mIsLooping = z;
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

    public void setVolume(float f) {
        synchronized (this.mPlaybackSettingsLock) {
            if (f < 0.0f) {
                f = 0.0f;
            }
            if (f > 1.0f) {
                f = 1.0f;
            }
            this.mVolume = f;
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

    public boolean setHapticGeneratorEnabled(boolean z) {
        if (!HapticGenerator.isAvailable()) {
            return false;
        }
        synchronized (this.mPlaybackSettingsLock) {
            this.mHapticGeneratorEnabled = z;
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
                iRingtonePlayer.setPlaybackProperties(this.mRemoteToken, this.mVolume, this.mIsLooping, this.mHapticGeneratorEnabled);
                return;
            } catch (RemoteException e) {
                Log.w("Ringtone", "Problem setting playback properties: ", e);
                return;
            }
        }
        Log.w("Ringtone", "Neither local nor remote player available when applying playback properties");
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0095 A[PHI: r1 r7 r10
      0x0095: PHI (r1v10 android.net.Uri) = (r1v7 android.net.Uri), (r1v12 android.net.Uri) binds: [B:51:0x00a7, B:42:0x0093] A[DONT_GENERATE, DONT_INLINE]
      0x0095: PHI (r7v14 android.database.Cursor) = (r7v10 android.database.Cursor), (r7v15 android.database.Cursor) binds: [B:51:0x00a7, B:42:0x0093] A[DONT_GENERATE, DONT_INLINE]
      0x0095: PHI (r10v6 java.lang.String) = (r10v4 java.lang.String), (r10v8 java.lang.String) binds: [B:51:0x00a7, B:42:0x0093] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00ae A[Catch: all -> 0x00bb, TRY_ENTER, TRY_LEAVE, TryCatch #5 {all -> 0x00bb, blocks: (B:23:0x005d, B:25:0x0064, B:27:0x006d, B:28:0x0072, B:31:0x0078, B:32:0x007d, B:56:0x00ae, B:62:0x00c2), top: B:83:0x0018 }] */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00bf  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x00c9  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x00cf  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00d4  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x00e2  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x00c2 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:90:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:91:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r7v4 */
    /* JADX WARN: Type inference failed for: r7v5 */
    /* JADX WARN: Type inference failed for: r7v6, types: [android.database.Cursor] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String getTitle(Context context, Uri uri, boolean z, boolean z2, boolean z3) throws Throwable {
        String string;
        Throwable th;
        Uri uri2;
        Cursor cursorQuery;
        RuntimeException runtimeException;
        IRingtonePlayer ringtonePlayer;
        String string2;
        String[] strArr;
        ContentResolver contentResolver = context.getContentResolver();
        if (uri != null) {
            String authorityWithoutUserId = ContentProvider.getAuthorityWithoutUserId(uri.getAuthority());
            ?? r7 = 0;
            string = null;
            string = null;
            titleIncludingTheme = null;
            String titleIncludingTheme = null;
            cursor = null;
            cursor = null;
            string = null;
            Cursor cursor = null;
            try {
                try {
                    if (!"settings".equals(authorityWithoutUserId)) {
                        try {
                            try {
                                if (AppJankStats.WIDGET_CATEGORY_MEDIA.equals(authorityWithoutUserId)) {
                                    String str = z2 ? null : MEDIA_SELECTION;
                                    if (Rune.SEC_AUDIO_SUPPORT_SOUND_THEME) {
                                        try {
                                            strArr = SoundTheme.SOUND_THEME_MEDIA_COLUMNS;
                                        } catch (IllegalArgumentException | IllegalStateException | UnsupportedOperationException e) {
                                            runtimeException = e;
                                            uri2 = uri;
                                            string2 = context.getString(R.string.ringtone_unknown);
                                            runtimeException.printStackTrace();
                                            if (cursor != null) {
                                            }
                                            string = string2;
                                            if (string == null) {
                                            }
                                            if (string == null) {
                                            }
                                        }
                                    } else {
                                        strArr = MEDIA_COLUMNS;
                                    }
                                    uri2 = uri;
                                    try {
                                        cursorQuery = contentResolver.query(uri2, strArr, str, null, null);
                                        if (cursorQuery != null) {
                                            try {
                                                if (cursorQuery.getCount() == 1) {
                                                    cursorQuery.moveToFirst();
                                                    if (isOpenThemeRingtone(cursorQuery)) {
                                                        titleIncludingTheme = changeThemeTitle(context, uri2);
                                                    } else if (Rune.SEC_AUDIO_SUPPORT_SOUND_THEME && z3) {
                                                        titleIncludingTheme = SoundTheme.getTitleIncludingTheme(context, cursorQuery);
                                                    } else {
                                                        String string3 = cursorQuery.getString(1);
                                                        if (cursorQuery != null) {
                                                            cursorQuery.close();
                                                        }
                                                        return string3;
                                                    }
                                                }
                                            } catch (IllegalArgumentException | IllegalStateException | UnsupportedOperationException e2) {
                                                runtimeException = e2;
                                                cursor = cursorQuery;
                                                string2 = context.getString(R.string.ringtone_unknown);
                                                runtimeException.printStackTrace();
                                                if (cursor != null) {
                                                }
                                                string = string2;
                                                if (string == null) {
                                                }
                                                if (string == null) {
                                                }
                                            } catch (SecurityException unused) {
                                                if (!z2) {
                                                }
                                                if (ringtonePlayer != null) {
                                                }
                                                if (cursorQuery != null) {
                                                }
                                                if (string == null) {
                                                }
                                                if (string == null) {
                                                }
                                            }
                                        }
                                        String str2 = titleIncludingTheme;
                                        cursor = cursorQuery;
                                        string2 = str2;
                                    } catch (IllegalArgumentException | IllegalStateException | UnsupportedOperationException e3) {
                                        e = e3;
                                        runtimeException = e;
                                        string2 = context.getString(R.string.ringtone_unknown);
                                        runtimeException.printStackTrace();
                                        if (cursor != null) {
                                        }
                                        string = string2;
                                        if (string == null) {
                                        }
                                        if (string == null) {
                                        }
                                    } catch (SecurityException unused2) {
                                        cursorQuery = null;
                                        ringtonePlayer = !z2 ? ((AudioManager) context.getSystemService("audio")).getRingtonePlayer() : null;
                                        if (ringtonePlayer != null) {
                                            try {
                                                string = ringtonePlayer.getTitle(uri2);
                                            } catch (RemoteException unused3) {
                                            }
                                        }
                                        if (cursorQuery != null) {
                                            cursorQuery.close();
                                        }
                                        if (string == null) {
                                        }
                                        if (string == null) {
                                        }
                                    }
                                } else {
                                    uri2 = uri;
                                    string2 = null;
                                }
                            } catch (SecurityException unused4) {
                                uri2 = uri;
                            }
                        } catch (IllegalArgumentException | IllegalStateException | UnsupportedOperationException e4) {
                            e = e4;
                            uri2 = uri;
                        }
                        if (cursor != null) {
                            cursor.close();
                        }
                        string = string2;
                        if (string == null) {
                            string = uri2.getLastPathSegment();
                        }
                    } else if (z) {
                        string = context.getString(R.string.ringtone_default_with_actual, getTitle(context, RingtoneManager.getActualDefaultRingtoneUri(context, RingtoneManager.getDefaultType(uri)), false, z2, z3));
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (r7 == 0) {
                        r7.close();
                        throw th;
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                r7 = uri;
                if (r7 == 0) {
                }
            }
        } else {
            string = context.getString(R.string.sec_ringtone_silent);
        }
        if (string == null) {
            return string;
        }
        String string4 = context.getString(R.string.ringtone_unknown);
        return string4 == null ? "" : string4;
    }

    public void setUri(Uri uri) {
        setUri(uri, null);
    }

    public void setVolumeShaperConfig(VolumeShaper.Configuration configuration) {
        this.mVolumeShaperConfig = configuration;
    }

    public void setUri(Uri uri, VolumeShaper.Configuration configuration) {
        Uri uri2;
        this.mVolumeShaperConfig = configuration;
        this.mUri = uri;
        if (uri == null) {
            destroyLocalPlayer();
        }
        if (Flags.enableRingtoneHapticsCustomization() && this.mRingtoneVibrationSupported && (uri2 = this.mUri) != null) {
            VibrationEffect vibrationEffect = Utils.parseVibrationEffect(this.mVibrator, Utils.getVibrationUri(uri2));
            this.mVibrationEffect = vibrationEffect;
            if (vibrationEffect != null) {
                this.mVibrationEffect = vibrationEffect.applyRepeatingIndefinitely(true, 200);
            }
        }
        int highlightOffset = getHighlightOffset(this.mUri);
        if (highlightOffset != -1) {
            this.mStartPosition = highlightOffset;
        }
        if (this.mIsTelecomPackage) {
            addTag(AudioTag.AUDIO_STREAM_RING);
        }
        if (this.mNeedFadeIn) {
            return;
        }
        addTag(AudioTag.AUDIO_NO_FADE);
    }

    public VibrationEffect getVibrationEffect() {
        return this.mVibrationEffect;
    }

    public Uri getUri() {
        return this.mUri;
    }

    public void play() {
        Uri uri;
        boolean z;
        float f;
        if (this.mLocalPlayer != null) {
            if (this.mStartPosition > 0) {
                Log.d("Ringtone", "Play from highlight " + this.mStartPosition + " mSec");
                this.mLocalPlayer.seekTo(this.mStartPosition);
                this.mStartPosition = 0;
            }
            if (this.mAudioManager.getStreamVolume(AudioAttributes.toLegacyStreamType(this.mAudioAttributes)) != 0) {
                startLocalPlayer();
            } else if (!this.mAudioAttributes.areHapticChannelsMuted() && hasHapticChannels()) {
                startLocalPlayer();
            } else if (Rune.SEC_AUDIO_SUPPORT_ACH_RINGTONE && this.mAudioAttributes.getTags().contains(AudioTag.RINGTONE_HAPTIC)) {
                Log.d("Ringtone", "Play haptic tag ringtone");
                startLocalPlayer();
            } else if (getStreamType() == 3) {
                Log.d("Ringtone", "Play music ringtone");
                startLocalPlayer();
            }
            new MediaMetrics.Item("audio.service.ringtone").set(MediaMetrics.Property.CALLING_PACKAGE, this.mContext.getPackageName() + " / play() ").record();
        } else if (this.mAllowRemote && this.mRemotePlayer != null && (uri = this.mUri) != null) {
            Uri canonicalUri = uri.getCanonicalUri();
            synchronized (this.mPlaybackSettingsLock) {
                z = this.mIsLooping;
                f = this.mVolume;
            }
            try {
                this.mRemotePlayer.playWithVolumeShaping(this.mRemoteToken, canonicalUri, this.mAudioAttributes, f, z, this.mVolumeShaperConfig);
                new MediaMetrics.Item("audio.service.ringtone").set(MediaMetrics.Property.CALLING_PACKAGE, this.mContext.getPackageName() + " / playWithVolumeShaping() ").record();
            } catch (RemoteException e) {
                if (!playFallbackRingtone()) {
                    Log.w("Ringtone", "Problem playing ringtone: " + e);
                }
            }
        } else if (!playFallbackRingtone()) {
            Log.w("Ringtone", "Neither local nor remote playback available");
        }
        if (Flags.enableRingtoneHapticsCustomization() && this.mRingtoneVibrationSupported) {
            playVibration();
        }
    }

    private void playVibration() {
        VibrationEffect vibrationEffect = this.mVibrationEffect;
        if (vibrationEffect == null) {
            return;
        }
        this.mIsVibrating = true;
        this.mVibrator.vibrate(vibrationEffect, VIBRATION_ATTRIBUTES);
    }

    public void stop() {
        IRingtonePlayer iRingtonePlayer;
        if (this.mLocalPlayer != null) {
            destroyLocalPlayer();
        } else if (this.mAllowRemote && (iRingtonePlayer = this.mRemotePlayer) != null) {
            try {
                iRingtonePlayer.stop(this.mRemoteToken);
            } catch (RemoteException e) {
                Log.w("Ringtone", "Problem stopping ringtone: " + e);
            }
        }
        if (Flags.enableRingtoneHapticsCustomization() && this.mRingtoneVibrationSupported && this.mIsVibrating) {
            this.mVibrator.cancel();
            this.mIsVibrating = false;
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

    private void startLocalPlayer() throws IllegalStateException {
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
            Log.w("Ringtone", "mLocalPlayer :: startLocalPlayer error", e);
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
                Log.w("Ringtone", "Problem checking ringtone: " + e);
                return false;
            }
        }
        Log.w("Ringtone", "Neither local nor remote playback available");
        return false;
    }

    private boolean playFallbackRingtone() throws IllegalStateException, IllegalArgumentException {
        if (this.mAudioManager.getStreamVolume(AudioAttributes.toLegacyStreamType(this.mAudioAttributes)) == 0) {
            return false;
        }
        int defaultType = RingtoneManager.getDefaultType(this.mUri);
        if (defaultType != -1 && RingtoneManager.getActualDefaultRingtoneUri(this.mContext, defaultType) == null) {
            Log.w("Ringtone", "not playing fallback for " + this.mUri);
            return false;
        }
        try {
            AssetFileDescriptor assetFileDescriptorOpenRawResourceFd = this.mContext.getResources().openRawResourceFd(R.raw.fallbackring);
            if (this.mAudioAttributes.getUsage() != 6) {
                Log.d("Ringtone", "play playFallbackRingtone: fallbacknoti");
                assetFileDescriptorOpenRawResourceFd = this.mContext.getResources().openRawResourceFd(R.raw.fallbacknoti);
            }
            if (assetFileDescriptorOpenRawResourceFd == null) {
                Log.e("Ringtone", "Could not load fallback ringtone");
                return false;
            }
            this.mLocalPlayer = new MediaPlayer();
            if (assetFileDescriptorOpenRawResourceFd.getDeclaredLength() < 0) {
                this.mLocalPlayer.setDataSource(assetFileDescriptorOpenRawResourceFd.getFileDescriptor());
            } else {
                this.mLocalPlayer.setDataSource(assetFileDescriptorOpenRawResourceFd.getFileDescriptor(), assetFileDescriptorOpenRawResourceFd.getStartOffset(), assetFileDescriptorOpenRawResourceFd.getDeclaredLength());
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
            assetFileDescriptorOpenRawResourceFd.close();
            return true;
        } catch (Resources.NotFoundException unused) {
            Log.e("Ringtone", "Fallback ringtone does not exist");
            return false;
        } catch (IOException unused2) {
            destroyLocalPlayer();
            Log.e("Ringtone", "Failed to open fallback ringtone");
            return false;
        }
    }

    void setTitle(String str) {
        this.mTitle = str;
    }

    protected void finalize() {
        MediaPlayer mediaPlayer = this.mLocalPlayer;
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }

    class MyOnCompletionListener implements MediaPlayer.OnCompletionListener {
        MyOnCompletionListener() {
        }

        @Override // android.media.MediaPlayer.OnCompletionListener
        public void onCompletion(MediaPlayer mediaPlayer) {
            synchronized (Ringtone.sActiveRingtones) {
                Ringtone.sActiveRingtones.remove(Ringtone.this);
            }
            mediaPlayer.setOnCompletionListener(null);
        }
    }

    private void setupCustomRoutine() {
        String packageName = this.mContext.getPackageName();
        this.mIsTelecomPackage = "com.android.server.telecom".equals(packageName);
        this.mUriStatus = false;
        if (AsPackageName.RINGTONE_PICKER.equals(packageName) || "com.android.settings".equals(packageName)) {
            this.mNeedFadeIn = false;
        }
    }

    public void setSecForSeek(int i) {
        this.mStartPosition = i;
    }

    public void setVolume(float f, float f2) {
        MediaPlayer mediaPlayer = this.mLocalPlayer;
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(f, f2);
        }
    }

    private boolean isValidUri(Uri uri) throws Throwable {
        if (uri == null) {
            return false;
        }
        int defaultType = RingtoneManager.getDefaultType(uri);
        if (defaultType != -1) {
            uri = RingtoneManager.getActualDefaultRingtoneUri(this.mContext, defaultType);
        }
        Uri uri2 = uri;
        if (uri2.toString().startsWith(MediaStore.Audio.Media.INTERNAL_CONTENT_URI.toString())) {
            return true;
        }
        try {
            Cursor cursorQuery = this.mContext.getContentResolver().query(uri2, new String[]{"_id"}, null, null, null);
            if (cursorQuery != null) {
                try {
                    if (cursorQuery.getCount() != 0) {
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                        return true;
                    }
                } finally {
                }
            }
            if (cursorQuery != null) {
                cursorQuery.close();
            }
        } catch (Exception unused) {
        }
        return false;
    }

    public boolean semIsUriValid() {
        return this.mUriStatus;
    }

    private int getHighlightOffset(Uri uri) {
        int defaultType = RingtoneManager.getDefaultType(uri);
        if (defaultType != -1 && (uri = RingtoneManager.getActualDefaultRingtoneUri(this.mContext, defaultType)) == null) {
            return -1;
        }
        try {
            String queryParameter = uri.getQueryParameter("highlight_offset");
            Log.d("Ringtone", "highlight offset is : " + queryParameter);
            if (queryParameter != null && !queryParameter.isEmpty()) {
                return Integer.parseInt(queryParameter);
            }
        } catch (Exception unused) {
        }
        return -1;
    }

    public void turnOffFadeIn() {
        this.mNeedFadeIn = false;
        setUri(this.mUri);
    }

    private void addTag(String str) {
        AudioAttributes audioAttributes = this.mAudioAttributes;
        if (audioAttributes == null || audioAttributes.getTags().contains(str)) {
            return;
        }
        this.mAudioAttributes = new AudioAttributes.Builder(this.mAudioAttributes).addTag(str).build();
    }

    public static String getTitle(Context context, Uri uri, boolean z, boolean z2) {
        return getTitle(context, uri, z, z2, false);
    }

    public static String getTitleWithSoundTheme(Context context, Uri uri, boolean z, boolean z2) {
        return getTitle(context, uri, z, z2, true);
    }

    private static String changeThemeTitle(Context context, Uri uri) {
        if (RingtoneManager.isInternalRingtoneUri(uri)) {
            return context.getString(R.string.sec_ringtone_category_open_theme);
        }
        return null;
    }

    private static boolean isOpenThemeRingtone(Cursor cursor) {
        String string = cursor.getString(1);
        String string2 = cursor.getString(cursor.getColumnIndex("_display_name"));
        return (!TextUtils.isEmpty(string) && string.startsWith(RingtoneManager.PREFIX_OPEN_THEME)) || (!TextUtils.isEmpty(string2) && string2.startsWith(RingtoneManager.PREFIX_OPEN_THEME));
    }

    public void fadeoutRingtone(int i, float f) {
        IRingtonePlayer iRingtonePlayer;
        VolumeShaper.Configuration configurationBuild = new VolumeShaper.Configuration.Builder().setCurve(new float[]{0.0f, 1.0f}, new float[]{1.0f, f}).setInterpolatorType(1).setOptionFlags(2).setDuration(i).build();
        VolumeShaper.Operation operationBuild = new VolumeShaper.Operation.Builder(VolumeShaper.Operation.PLAY).createIfNeeded().build();
        if (this.mLocalPlayer != null) {
            VolumeShaper volumeShaper = this.mCustomShaper;
            if (volumeShaper != null) {
                volumeShaper.close();
            }
            try {
                VolumeShaper volumeShaperCreateVolumeShaper = this.mLocalPlayer.createVolumeShaper(configurationBuild);
                this.mCustomShaper = volumeShaperCreateVolumeShaper;
                volumeShaperCreateVolumeShaper.apply(operationBuild);
                return;
            } catch (IllegalArgumentException | IllegalStateException e) {
                Log.w("Ringtone", "mLocalPlayer :: fadeout error", e);
                return;
            }
        }
        if (!this.mAllowRemote || (iRingtonePlayer = this.mRemotePlayer) == null) {
            return;
        }
        try {
            iRingtonePlayer.fadeoutRingtone(this.mRemoteToken, i, f);
        } catch (RemoteException e2) {
            Log.w("Ringtone", "mRemotePlayer :: fadeout error", e2);
        }
    }

    public void fadeoutRingtone(int i) {
        fadeoutRingtone(i, 0.0f);
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
                Log.w("Ringtone", "mLocalPlayer :: fadein error", e);
                return;
            }
        }
        if (!this.mAllowRemote || (iRingtonePlayer = this.mRemotePlayer) == null) {
            return;
        }
        try {
            iRingtonePlayer.fadeinRingtone(this.mRemoteToken);
        } catch (RemoteException e2) {
            Log.w("Ringtone", "mRemotePlayer :: fadein error", e2);
        }
    }
}
