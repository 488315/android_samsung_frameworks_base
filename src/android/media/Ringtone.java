package android.media;

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

    /* JADX WARN: Code restructure failed: missing block: B:28:0x0093, code lost:
    
        if (r7 != null) goto L43;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0095, code lost:
    
        r7.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0098, code lost:
    
        r7 = r10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00a7, code lost:
    
        if (r7 == null) goto L44;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:14:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00d4  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x00ae A[Catch: all -> 0x00bb, TRY_ENTER, TRY_LEAVE, TryCatch #5 {all -> 0x00bb, blocks: (B:34:0x005d, B:36:0x0064, B:38:0x006d, B:39:0x0072, B:42:0x0078, B:43:0x007d, B:53:0x00ae, B:58:0x00c2), top: B:4:0x0018 }] */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00cf  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00c2 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00bf  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x00c9  */
    /* JADX WARN: Removed duplicated region for block: B:83:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x00e2  */
    /* JADX WARN: Type inference failed for: r7v4 */
    /* JADX WARN: Type inference failed for: r7v5 */
    /* JADX WARN: Type inference failed for: r7v6, types: [android.database.Cursor] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String getTitle(android.content.Context r9, android.net.Uri r10, boolean r11, boolean r12, boolean r13) {
        /*
            Method dump skipped, instructions count: 235
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.media.Ringtone.getTitle(android.content.Context, android.net.Uri, boolean, boolean, boolean):java.lang.String");
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
            VibrationEffect parseVibrationEffect = Utils.parseVibrationEffect(this.mVibrator, Utils.getVibrationUri(uri2));
            this.mVibrationEffect = parseVibrationEffect;
            if (parseVibrationEffect != null) {
                this.mVibrationEffect = parseVibrationEffect.applyRepeatingIndefinitely(true, 200);
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

    private boolean playFallbackRingtone() {
        if (this.mAudioManager.getStreamVolume(AudioAttributes.toLegacyStreamType(this.mAudioAttributes)) == 0) {
            return false;
        }
        int defaultType = RingtoneManager.getDefaultType(this.mUri);
        if (defaultType != -1 && RingtoneManager.getActualDefaultRingtoneUri(this.mContext, defaultType) == null) {
            Log.w("Ringtone", "not playing fallback for " + this.mUri);
            return false;
        }
        try {
            AssetFileDescriptor openRawResourceFd = this.mContext.getResources().openRawResourceFd(R.raw.fallbackring);
            if (this.mAudioAttributes.getUsage() != 6) {
                Log.d("Ringtone", "play playFallbackRingtone: fallbacknoti");
                openRawResourceFd = this.mContext.getResources().openRawResourceFd(R.raw.fallbacknoti);
            }
            if (openRawResourceFd == null) {
                Log.e("Ringtone", "Could not load fallback ringtone");
                return false;
            }
            this.mLocalPlayer = new MediaPlayer();
            if (openRawResourceFd.getDeclaredLength() < 0) {
                this.mLocalPlayer.setDataSource(openRawResourceFd.getFileDescriptor());
            } else {
                this.mLocalPlayer.setDataSource(openRawResourceFd.getFileDescriptor(), openRawResourceFd.getStartOffset(), openRawResourceFd.getDeclaredLength());
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
            openRawResourceFd.close();
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

    private boolean isValidUri(Uri uri) {
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
            Cursor query = this.mContext.getContentResolver().query(uri2, new String[]{"_id"}, null, null, null);
            if (query != null) {
                try {
                    if (query.getCount() != 0) {
                        if (query != null) {
                            query.close();
                        }
                        return true;
                    }
                } finally {
                }
            }
            if (query != null) {
                query.close();
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
        VolumeShaper.Configuration build = new VolumeShaper.Configuration.Builder().setCurve(new float[]{0.0f, 1.0f}, new float[]{1.0f, f}).setInterpolatorType(1).setOptionFlags(2).setDuration(i).build();
        VolumeShaper.Operation build2 = new VolumeShaper.Operation.Builder(VolumeShaper.Operation.PLAY).createIfNeeded().build();
        if (this.mLocalPlayer != null) {
            VolumeShaper volumeShaper = this.mCustomShaper;
            if (volumeShaper != null) {
                volumeShaper.close();
            }
            try {
                VolumeShaper createVolumeShaper = this.mLocalPlayer.createVolumeShaper(build);
                this.mCustomShaper = createVolumeShaper;
                createVolumeShaper.apply(build2);
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
