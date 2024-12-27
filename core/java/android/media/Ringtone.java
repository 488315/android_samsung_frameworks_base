package android.media;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.database.Cursor;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.VolumeShaper;
import android.media.audiofx.HapticGenerator;
import android.net.Uri;
import android.os.Binder;
import android.os.RemoteException;
import android.os.Trace;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.R;
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
    private AudioAttributes mAudioAttributes = new AudioAttributes.Builder().setUsage(6).setContentType(4).build();
    private boolean mIsLooping = false;
    private float mVolume = 1.0f;
    private boolean mHapticGeneratorEnabled = false;
    private final Object mPlaybackSettingsLock = new Object();
    private boolean mNeedFadeIn = true;
    private int mStartPosition = 0;

    public Ringtone(Context context, boolean allowRemote) {
        this.mContext = context;
        this.mAudioManager = (AudioManager) this.mContext.getSystemService("audio");
        this.mAllowRemote = allowRemote;
        this.mRemotePlayer = allowRemote ? this.mAudioManager.getRingtonePlayer() : null;
        this.mRemoteToken = allowRemote ? new Binder() : null;
        setupCustomRoutine();
    }

    @Deprecated
    public void setStreamType(int streamType) {
        PlayerBase.deprecateStreamTypeForPlayback(streamType, "Ringtone", "setStreamType()");
        setAudioAttributes(new AudioAttributes.Builder().setInternalLegacyStreamType(streamType).build());
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
        if (this.mLocalPlayer == null) {
            return true;
        }
        return this.mLocalPlayer.setPreferredDevice(getBuiltinDevice(this.mAudioManager));
    }

    public boolean createLocalMediaPlayer() {
        Trace.beginSection("createLocalMediaPlayer");
        if (this.mUri == null) {
            Log.e("Ringtone", "Could not create media player as no URI was provided.");
            return this.mAllowRemote && this.mRemotePlayer != null;
        }
        destroyLocalPlayer();
        this.mLocalPlayer = new MediaPlayer();
        try {
            this.mLocalPlayer.setDataSource(this.mContext, this.mUri);
            this.mLocalPlayer.setAudioAttributes(this.mAudioAttributes);
            this.mLocalPlayer.setPreferredDevice(this.mPreferBuiltinDevice ? getBuiltinDevice(this.mAudioManager) : null);
            synchronized (this.mPlaybackSettingsLock) {
                applyPlaybackProperties_sync();
            }
            if (this.mVolumeShaperConfig != null) {
                this.mVolumeShaper = this.mLocalPlayer.createVolumeShaper(this.mVolumeShaperConfig);
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
            if (this.mLocalPlayer != null) {
                for (MediaPlayer.TrackInfo trackInfo : this.mLocalPlayer.getTrackInfo()) {
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
        if (this.mLocalPlayer != null) {
            this.mLocalPlayer.setVolume(this.mVolume);
            this.mLocalPlayer.setLooping(this.mIsLooping);
            if (this.mHapticGenerator == null && this.mHapticGeneratorEnabled) {
                this.mHapticGenerator = HapticGenerator.create(this.mLocalPlayer.getAudioSessionId());
            }
            if (this.mHapticGenerator != null) {
                this.mHapticGenerator.setEnabled(this.mHapticGeneratorEnabled);
                return;
            }
            return;
        }
        if (this.mAllowRemote && this.mRemotePlayer != null) {
            try {
                this.mRemotePlayer.setPlaybackProperties(this.mRemoteToken, this.mVolume, this.mIsLooping, this.mHapticGeneratorEnabled);
                return;
            } catch (RemoteException e) {
                Log.w("Ringtone", "Problem setting playback properties: ", e);
                return;
            }
        }
        Log.w("Ringtone", "Neither local nor remote player available when applying playback properties");
    }

    public String getTitle(Context context) {
        if (this.mTitle != null) {
            return this.mTitle;
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
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String getTitle(android.content.Context r11, android.net.Uri r12, boolean r13, boolean r14, boolean r15) {
        /*
            Method dump skipped, instructions count: 219
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.media.Ringtone.getTitle(android.content.Context, android.net.Uri, boolean, boolean, boolean):java.lang.String");
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
        if (this.mUri == null) {
            destroyLocalPlayer();
        }
        int highlightOffset = getHighlightOffset(this.mUri);
        if (highlightOffset != -1) {
            this.mStartPosition = highlightOffset;
        }
        if (this.mIsTelecomPackage) {
            addTag(AudioTag.AUDIO_STREAM_RING);
        }
        if (!this.mNeedFadeIn) {
            addTag(AudioTag.AUDIO_NO_FADE);
        }
    }

    public Uri getUri() {
        return this.mUri;
    }

    public void play() {
        boolean looping;
        float volume;
        if (this.mLocalPlayer != null) {
            if (this.mStartPosition > 0) {
                Log.d("Ringtone", "Play from highlight " + this.mStartPosition + " mSec");
                this.mLocalPlayer.seekTo(this.mStartPosition);
                this.mStartPosition = 0;
            }
            if (this.mAudioManager.getStreamVolume(AudioAttributes.toLegacyStreamType(this.mAudioAttributes)) != 0) {
                startLocalPlayer();
                return;
            }
            if (!this.mAudioAttributes.areHapticChannelsMuted() && hasHapticChannels()) {
                startLocalPlayer();
                return;
            }
            if (Rune.SEC_AUDIO_SUPPORT_ACH_RINGTONE && this.mAudioAttributes.getTags().contains(AudioTag.RINGTONE_HAPTIC)) {
                Log.d("Ringtone", "Play haptic tag ringtone");
                startLocalPlayer();
                return;
            } else {
                if (getStreamType() == 3) {
                    Log.d("Ringtone", "Play music ringtone");
                    startLocalPlayer();
                    return;
                }
                return;
            }
        }
        if (this.mAllowRemote && this.mRemotePlayer != null && this.mUri != null) {
            Uri canonicalUri = this.mUri.getCanonicalUri();
            synchronized (this.mPlaybackSettingsLock) {
                looping = this.mIsLooping;
                volume = this.mVolume;
            }
            try {
                this.mRemotePlayer.playWithVolumeShaping(this.mRemoteToken, canonicalUri, this.mAudioAttributes, volume, looping, this.mVolumeShaperConfig);
                return;
            } catch (RemoteException e) {
                if (!playFallbackRingtone()) {
                    Log.w("Ringtone", "Problem playing ringtone: " + e);
                    return;
                }
                return;
            }
        }
        if (!playFallbackRingtone()) {
            Log.w("Ringtone", "Neither local nor remote playback available");
        }
    }

    public void stop() {
        if (this.mLocalPlayer != null) {
            destroyLocalPlayer();
            return;
        }
        if (this.mAllowRemote && this.mRemotePlayer != null) {
            try {
                this.mRemotePlayer.stop(this.mRemoteToken);
            } catch (RemoteException e) {
                Log.w("Ringtone", "Problem stopping ringtone: " + e);
            }
        }
    }

    private void destroyLocalPlayer() {
        if (this.mLocalPlayer != null) {
            if (this.mHapticGenerator != null) {
                this.mHapticGenerator.release();
                this.mHapticGenerator = null;
            }
            this.mLocalPlayer.setVolume(0.0f, 0.0f);
            this.mLocalPlayer.setOnCompletionListener(null);
            this.mLocalPlayer.reset();
            this.mLocalPlayer.release();
            this.mLocalPlayer = null;
            this.mVolumeShaper = null;
            synchronized (sActiveRingtones) {
                sActiveRingtones.remove(this);
            }
        }
    }

    private void startLocalPlayer() {
        if (this.mLocalPlayer == null) {
            return;
        }
        synchronized (sActiveRingtones) {
            sActiveRingtones.add(this);
        }
        this.mLocalPlayer.setOnCompletionListener(this.mCompletionListener);
        this.mLocalPlayer.start();
        try {
            if (this.mVolumeShaper != null) {
                this.mVolumeShaper.apply(VolumeShaper.Operation.PLAY);
            }
        } catch (IllegalArgumentException | IllegalStateException e) {
            Log.w("Ringtone", "mLocalPlayer :: startLocalPlayer error", e);
        }
    }

    public boolean isPlaying() {
        if (this.mLocalPlayer != null) {
            return this.mLocalPlayer.isPlaying();
        }
        if (this.mAllowRemote && this.mRemotePlayer != null) {
            try {
                return this.mRemotePlayer.isPlaying(this.mRemoteToken);
            } catch (RemoteException e) {
                Log.w("Ringtone", "Problem checking ringtone: " + e);
                return false;
            }
        }
        Log.w("Ringtone", "Neither local nor remote playback available");
        return false;
    }

    private boolean playFallbackRingtone() {
        int streamType = AudioAttributes.toLegacyStreamType(this.mAudioAttributes);
        if (this.mAudioManager.getStreamVolume(streamType) == 0) {
            return false;
        }
        int ringtoneType = RingtoneManager.getDefaultType(this.mUri);
        if (ringtoneType != -1 && RingtoneManager.getActualDefaultRingtoneUri(this.mContext, ringtoneType) == null) {
            Log.w("Ringtone", "not playing fallback for " + this.mUri);
            return false;
        }
        try {
            AssetFileDescriptor afd = this.mContext.getResources().openRawResourceFd(R.raw.fallbackring);
            if (this.mAudioAttributes.getUsage() != 6) {
                Log.d("Ringtone", "play playFallbackRingtone: fallbacknoti");
                afd = this.mContext.getResources().openRawResourceFd(R.raw.fallbacknoti);
            }
            if (afd == null) {
                Log.e("Ringtone", "Could not load fallback ringtone");
                return false;
            }
            this.mLocalPlayer = new MediaPlayer();
            if (afd.getDeclaredLength() < 0) {
                this.mLocalPlayer.setDataSource(afd.getFileDescriptor());
            } else {
                this.mLocalPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getDeclaredLength());
            }
            this.mLocalPlayer.setAudioAttributes(this.mAudioAttributes);
            synchronized (this.mPlaybackSettingsLock) {
                applyPlaybackProperties_sync();
            }
            if (this.mVolumeShaperConfig != null) {
                this.mVolumeShaper = this.mLocalPlayer.createVolumeShaper(this.mVolumeShaperConfig);
            }
            this.mLocalPlayer.prepare();
            startLocalPlayer();
            afd.close();
            return true;
        } catch (Resources.NotFoundException e) {
            Log.e("Ringtone", "Fallback ringtone does not exist");
            return false;
        } catch (IOException e2) {
            destroyLocalPlayer();
            Log.e("Ringtone", "Failed to open fallback ringtone");
            return false;
        }
    }

    void setTitle(String title) {
        this.mTitle = title;
    }

    protected void finalize() {
        if (this.mLocalPlayer != null) {
            this.mLocalPlayer.release();
        }
    }

    class MyOnCompletionListener implements MediaPlayer.OnCompletionListener {
        MyOnCompletionListener() {
        }

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
        if (AsPackageName.RINGTONE_PICKER.equals(packageName) || "com.android.settings".equals(packageName)) {
            this.mNeedFadeIn = false;
        }
    }

    public void setSecForSeek(int seek) {
        this.mStartPosition = seek;
    }

    public void setVolume(float leftVol, float rightVol) {
        if (this.mLocalPlayer != null) {
            this.mLocalPlayer.setVolume(leftVol, rightVol);
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
            Cursor cs = this.mContext.getContentResolver().query(uri, new String[]{"_id"}, null, null, null);
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
        if (type != -1 && (uri = RingtoneManager.getActualDefaultRingtoneUri(this.mContext, type)) == null) {
            return -1;
        }
        try {
            String highlightOffset = uri.getQueryParameter("highlight_offset");
            Log.d("Ringtone", "highlight offset is : " + highlightOffset);
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
        if (this.mAudioAttributes == null || this.mAudioAttributes.getTags().contains(tag)) {
            return;
        }
        this.mAudioAttributes = new AudioAttributes.Builder(this.mAudioAttributes).addTag(tag).build();
    }

    public static String getTitle(Context context, Uri uri, boolean followSettingsUri, boolean allowRemote) {
        return getTitle(context, uri, followSettingsUri, allowRemote, false);
    }

    public static String getTitleWithSoundTheme(Context context, Uri uri, boolean followSettingsUri, boolean allowRemote) {
        return getTitle(context, uri, followSettingsUri, allowRemote, true);
    }

    private static String changeThemeTitle(Context context, Uri uri) {
        if (!RingtoneManager.isInternalRingtoneUri(uri)) {
            return null;
        }
        String themeTitle = context.getString(R.string.sec_ringtone_category_open_theme);
        return themeTitle;
    }

    private static boolean isOpenThemeRingtone(Cursor cursor) {
        String title = cursor.getString(1);
        String displayName = cursor.getString(cursor.getColumnIndex("_display_name"));
        if (TextUtils.isEmpty(title) || !title.startsWith(RingtoneManager.PREFIX_OPEN_THEME)) {
            return !TextUtils.isEmpty(displayName) && displayName.startsWith(RingtoneManager.PREFIX_OPEN_THEME);
        }
        return true;
    }

    public void fadeoutRingtone(int delay, float minVolume) {
        VolumeShaper.Configuration FADEOUT_VSHAPE = new VolumeShaper.Configuration.Builder().setCurve(new float[]{0.0f, 1.0f}, new float[]{1.0f, minVolume}).setInterpolatorType(1).setOptionFlags(2).setDuration(delay).build();
        VolumeShaper.Operation PLAY_CREATE_IF_NEEDED = new VolumeShaper.Operation.Builder(VolumeShaper.Operation.PLAY).createIfNeeded().build();
        if (this.mLocalPlayer != null) {
            if (this.mCustomShaper != null) {
                this.mCustomShaper.close();
            }
            try {
                this.mCustomShaper = this.mLocalPlayer.createVolumeShaper(FADEOUT_VSHAPE);
                this.mCustomShaper.apply(PLAY_CREATE_IF_NEEDED);
                return;
            } catch (IllegalArgumentException | IllegalStateException e) {
                Log.w("Ringtone", "mLocalPlayer :: fadeout error", e);
                return;
            }
        }
        if (this.mAllowRemote && this.mRemotePlayer != null) {
            try {
                this.mRemotePlayer.fadeoutRingtone(this.mRemoteToken, delay, minVolume);
            } catch (RemoteException e2) {
                Log.w("Ringtone", "mRemotePlayer :: fadeout error", e2);
            }
        }
    }

    public void fadeoutRingtone(int delay) {
        fadeoutRingtone(delay, 0.0f);
    }

    public void fadeinRingtone() {
        if (this.mLocalPlayer != null) {
            try {
                if (this.mCustomShaper != null) {
                    this.mCustomShaper.apply(VolumeShaper.Operation.REVERSE);
                    return;
                }
                return;
            } catch (IllegalArgumentException | IllegalStateException e) {
                Log.w("Ringtone", "mLocalPlayer :: fadein error", e);
                return;
            }
        }
        if (this.mAllowRemote && this.mRemotePlayer != null) {
            try {
                this.mRemotePlayer.fadeinRingtone(this.mRemoteToken);
            } catch (RemoteException e2) {
                Log.w("Ringtone", "mRemotePlayer :: fadein error", e2);
            }
        }
    }
}
