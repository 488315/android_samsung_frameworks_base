package com.android.server.audio;

import android.R;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.media.AudioAttributes;
import android.media.AudioDeviceAttributes;
import android.media.AudioSystem;
import android.media.ISoundDose;
import android.media.ISoundDoseCallback;
import android.media.SoundDoseRecord;
import android.os.Message;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.provider.Settings;
import android.util.Log;
import android.util.SparseIntArray;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.accessibility.magnification.WindowMagnificationGestureHandler$$ExternalSyntheticOutline0;
import com.android.server.utils.EventLogger;

import com.samsung.android.audio.Rune;
import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;
import com.samsung.android.server.audio.FactoryUtils;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class SoundDoseHelper {
    public final AlarmManager mAlarmManager;
    public final AudioService.AudioHandler mAudioHandler;
    public final AudioService mAudioService;
    public final ArrayList mCachedAudioDeviceCategories;
    public final Context mContext;
    public final Object mCsdAsAFeatureLock;
    public final Object mCsdStateLock;
    public float mCurrentCsd;
    public final List mDoseRecords;
    public final EventLogger mEarShockLogger;
    public final AtomicBoolean mEnableCsd;
    public final AtomicBoolean mForceCsdProperty;
    public long mGlobalTimeOffsetInSecs;
    public boolean mIsCsdAsAFeatureAvailable;
    public boolean mIsCsdAsAFeatureEnabled;
    public boolean mIsVolumeEffectOn;
    public long mLastMomentaryExposureTimeMs;
    public long mLastMusicActiveTimeMs;
    public PendingIntent mMusicActiveIntent;
    public int mMusicActiveMs;
    public float mNextCsdWarning;
    public StreamVolumeCommand mPendingVolumeCommand;
    public final SparseIntArray mSafeMediaVolumeBTDevices;
    public float mSafeMediaVolumeDbfs;
    public final SparseIntArray mSafeMediaVolumeDevices;
    public int mSafeMediaVolumeIndex;
    public int mSafeMediaVolumeState;
    public int mSafeMediaVolumeStateForBlueTooth;
    public final SettingsAdapter mSettings;
    public final AtomicReference mSoundDose;
    public final AnonymousClass1 mSoundDoseCallback;
    public AudioService.VolumeController mVolumeController;
    public final EventLogger mLogger = new EventLogger(30, "CSD updates");
    public int mMcc = 0;
    public final Object mSafeMediaVolumeStateLock = new Object();

    public final class StreamVolumeCommand {
        public final int mDevice;
        public final int mFlags;
        public final int mIndex;
        public final int mStreamType;

        public StreamVolumeCommand(int i, int i2, int i3, int i4) {
            this.mStreamType = i;
            this.mIndex = i2;
            this.mFlags = i3;
            this.mDevice = i4;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("{streamType=");
            sb.append(this.mStreamType);
            sb.append(",index=");
            sb.append(this.mIndex);
            sb.append(",flags=");
            sb.append(this.mFlags);
            sb.append(",device=");
            return WindowMagnificationGestureHandler$$ExternalSyntheticOutline0.m(
                    sb, this.mDevice, '}');
        }
    }

    /* renamed from: -$$Nest$mgetTimeoutMsForWarning, reason: not valid java name */
    public static int m286$$Nest$mgetTimeoutMsForWarning(SoundDoseHelper soundDoseHelper, int i) {
        soundDoseHelper.getClass();
        if (i == 1) {
            return 7000;
        }
        if (i == 2 || i == 3) {
            return 5000;
        }
        if (i == 4) {
            return -1;
        }
        Log.e(
                "AS.SoundDoseHelper",
                VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Invalid CSD warning "),
                new Exception());
        return -1;
    }

    /* renamed from: -$$Nest$mupdateSoundDoseRecords_l, reason: not valid java name */
    public static void m287$$Nest$mupdateSoundDoseRecords_l(
            SoundDoseHelper soundDoseHelper, SoundDoseRecord[] soundDoseRecordArr, float f) {
        soundDoseHelper.getClass();
        long j = 0;
        for (final SoundDoseRecord soundDoseRecord : soundDoseRecordArr) {
            Log.i("AS.SoundDoseHelper", "  new record: " + soundDoseRecord);
            j += (long) soundDoseRecord.duration;
            float f2 = soundDoseRecord.value;
            if (f2 < FullScreenMagnificationGestureHandler.MAX_SCALE) {
                if (!((ArrayList) soundDoseHelper.mDoseRecords)
                        .removeIf(
                                new Predicate() { // from class:
                                                  // com.android.server.audio.SoundDoseHelper$$ExternalSyntheticLambda3
                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj) {
                                        SoundDoseRecord soundDoseRecord2 = soundDoseRecord;
                                        SoundDoseRecord soundDoseRecord3 = (SoundDoseRecord) obj;
                                        return soundDoseRecord3.value == (-soundDoseRecord2.value)
                                                && soundDoseRecord3.timestamp
                                                        == soundDoseRecord2.timestamp
                                                && soundDoseRecord3.averageMel
                                                        == soundDoseRecord2.averageMel
                                                && soundDoseRecord3.duration
                                                        == soundDoseRecord2.duration;
                                    }
                                })) {
                    Log.w(
                            "AS.SoundDoseHelper",
                            "Could not find cached record to remove: " + soundDoseRecord);
                }
            } else if (f2 > FullScreenMagnificationGestureHandler.MAX_SCALE) {
                ((ArrayList) soundDoseHelper.mDoseRecords).add(soundDoseRecord);
            }
        }
        soundDoseHelper.sanitizeDoseRecords_l();
        AudioService.AudioHandler audioHandler = soundDoseHelper.mAudioHandler;
        audioHandler.sendMessageAtTime(audioHandler.obtainMessage(1005, 0, 0, null), 0L);
        soundDoseHelper.mLogger.enqueue(new AudioServiceEvents$SoundDoseEvent(1, j, f));
    }

    public SoundDoseHelper(
            AudioService audioService,
            Context context,
            AudioService.AudioHandler audioHandler,
            SettingsAdapter settingsAdapter,
            AudioService.VolumeController volumeController) {
        SparseIntArray sparseIntArray = new SparseIntArray();
        this.mSafeMediaVolumeDevices = sparseIntArray;
        this.mLastMusicActiveTimeMs = 0L;
        this.mMusicActiveIntent = null;
        this.mEnableCsd = new AtomicBoolean(false);
        this.mForceCsdProperty = new AtomicBoolean(false);
        this.mCsdAsAFeatureLock = new Object();
        this.mIsCsdAsAFeatureAvailable = false;
        this.mIsCsdAsAFeatureEnabled = false;
        this.mCachedAudioDeviceCategories = new ArrayList();
        this.mCsdStateLock = new Object();
        AtomicReference atomicReference = new AtomicReference();
        this.mSoundDose = atomicReference;
        this.mCurrentCsd = FullScreenMagnificationGestureHandler.MAX_SCALE;
        this.mLastMomentaryExposureTimeMs = -1L;
        this.mNextCsdWarning = 1.0f;
        this.mDoseRecords = new ArrayList();
        this.mGlobalTimeOffsetInSecs = -1L;
        ISoundDoseCallback iSoundDoseCallback =
                new ISoundDoseCallback
                        .Stub() { // from class: com.android.server.audio.SoundDoseHelper.1
                    public final void onMomentaryExposure(float f, int i) {
                        if (!SoundDoseHelper.this.mEnableCsd.get()) {
                            Log.w(
                                    "AS.SoundDoseHelper",
                                    "onMomentaryExposure: csd not supported, ignoring callback");
                            return;
                        }
                        Log.w(
                                "AS.SoundDoseHelper",
                                "DeviceId " + i + " triggered momentary exposure with value: " + f);
                        boolean z = false;
                        SoundDoseHelper.this.mLogger.enqueue(
                                new AudioServiceEvents$SoundDoseEvent(0, 0L, f));
                        synchronized (SoundDoseHelper.this.mCsdStateLock) {
                            try {
                                if (SoundDoseHelper.this.mLastMomentaryExposureTimeMs >= 0) {
                                    if (System.currentTimeMillis()
                                                    - SoundDoseHelper.this
                                                            .mLastMomentaryExposureTimeMs
                                            >= 72000000) {}
                                }
                                SoundDoseHelper.this.mLastMomentaryExposureTimeMs =
                                        System.currentTimeMillis();
                                z = true;
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        if (z) {
                            SoundDoseHelper soundDoseHelper = SoundDoseHelper.this;
                            soundDoseHelper.mVolumeController.postDisplayCsdWarning(
                                    3,
                                    SoundDoseHelper.m286$$Nest$mgetTimeoutMsForWarning(
                                            soundDoseHelper, 3));
                        }
                    }

                    public final void onNewCsdValue(float f, SoundDoseRecord[] soundDoseRecordArr) {
                        if (!SoundDoseHelper.this.mEnableCsd.get()) {
                            Log.w(
                                    "AS.SoundDoseHelper",
                                    "onNewCsdValue: csd not supported, ignoring value");
                            return;
                        }
                        Log.i("AS.SoundDoseHelper", "onNewCsdValue: " + f);
                        synchronized (SoundDoseHelper.this.mCsdStateLock) {
                            try {
                                SoundDoseHelper soundDoseHelper = SoundDoseHelper.this;
                                float f2 = soundDoseHelper.mCurrentCsd;
                                if (f2 < f) {
                                    float f3 = soundDoseHelper.mNextCsdWarning;
                                    if (f2 < f3 && f >= f3) {
                                        if (f3 == 5.0f) {
                                            soundDoseHelper.mVolumeController.postDisplayCsdWarning(
                                                    2,
                                                    SoundDoseHelper
                                                            .m286$$Nest$mgetTimeoutMsForWarning(
                                                                    soundDoseHelper, 2));
                                            AudioService.sendMsg(
                                                    SoundDoseHelper.this
                                                            .mAudioService
                                                            .mAudioHandler,
                                                    1007,
                                                    2,
                                                    0,
                                                    0,
                                                    null,
                                                    0);
                                        } else {
                                            soundDoseHelper.mVolumeController.postDisplayCsdWarning(
                                                    1, 7000);
                                        }
                                        SoundDoseHelper.this.mNextCsdWarning += 1.0f;
                                    }
                                } else {
                                    float f4 = soundDoseHelper.mNextCsdWarning;
                                    float f5 = f4 - 1.0f;
                                    if (f < f5 && f4 >= 2.0f) {
                                        soundDoseHelper.mNextCsdWarning = f5;
                                    }
                                }
                                SoundDoseHelper soundDoseHelper2 = SoundDoseHelper.this;
                                soundDoseHelper2.mCurrentCsd = f;
                                SoundDoseHelper.m287$$Nest$mupdateSoundDoseRecords_l(
                                        soundDoseHelper2, soundDoseRecordArr, f);
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                };
        this.mSoundDoseCallback = iSoundDoseCallback;
        SparseIntArray sparseIntArray2 = new SparseIntArray();
        this.mSafeMediaVolumeBTDevices = sparseIntArray2;
        this.mSafeMediaVolumeStateForBlueTooth = 1;
        this.mEarShockLogger = new EventLogger(30, "Ear shock level history");
        this.mAudioService = audioService;
        this.mAudioHandler = audioHandler;
        this.mSettings = settingsAdapter;
        this.mVolumeController = volumeController;
        this.mContext = context;
        sparseIntArray.append(4, -1);
        sparseIntArray.append(8, -1);
        sparseIntArray.append(67108864, -1);
        sparseIntArray.append(536870912, -1);
        sparseIntArray.append(536870914, -1);
        sparseIntArray.append(256, -1);
        boolean z = Rune.SEC_AUDIO_SAFE_MEDIA_VOLUME;
        sparseIntArray.append(128, -1);
        sparseIntArray.append(134217728, -1);
        sparseIntArray.append(
                EndpointMonitorConst.FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION, -1);
        sparseIntArray2.append(128, -1);
        sparseIntArray2.append(256, -1);
        sparseIntArray2.append(536870912, -1);
        sparseIntArray2.append(536870914, -1);
        sparseIntArray2.append(134217728, -1);
        ContentResolver contentResolver = audioService.mContentResolver;
        settingsAdapter.getClass();
        this.mSafeMediaVolumeState =
                Settings.Global.getInt(contentResolver, "audio_safe_volume_state", 0);
        if (Rune.SEC_AUDIO_SAFE_VOLUME_COUNTRY) {
            this.mSafeMediaVolumeStateForBlueTooth = 3;
        }
        this.mSafeMediaVolumeIndex =
                context.getResources().getInteger(R.integer.config_stableDeviceDisplayHeight) * 10;
        atomicReference.set(AudioSystem.getSoundDoseInterface(iSoundDoseCallback));
        initCsd();
        this.mAlarmManager = (AlarmManager) context.getSystemService("alarm");
    }

    public static String safeMediaVolumeStateToString(int i) {
        if (i == 0) {
            return "SAFE_MEDIA_VOLUME_NOT_CONFIGURED";
        }
        if (i == 1) {
            return "SAFE_MEDIA_VOLUME_DISABLED";
        }
        if (i == 2) {
            return "SAFE_MEDIA_VOLUME_INACTIVE";
        }
        if (i != 3) {
            return null;
        }
        return "SAFE_MEDIA_VOLUME_ACTIVE";
    }

    public final boolean checkSafeMediaVolume(int i, int i2, int i3) {
        boolean checkSafeMediaVolume_l;
        synchronized (this.mSafeMediaVolumeStateLock) {
            checkSafeMediaVolume_l = checkSafeMediaVolume_l(i, i2, i3);
        }
        return checkSafeMediaVolume_l;
    }

    public final boolean checkSafeMediaVolume_l(int i, int i2, int i3) {
        if (FactoryUtils.isFactoryMode()) {
            return false;
        }
        return !(Rune.SEC_AUDIO_BIKE_MODE && this.mAudioService.mExt.mIsBikeMode)
                && this.mSafeMediaVolumeState == 3
                && AudioService.mStreamVolumeAlias[i] == 3
                && safeDevicesContains(i3)
                && i2 > safeMediaVolumeIndex(i3)
                && this.mSafeMediaVolumeBTDevices.indexOfKey(i3) < 0;
    }

    public final void configureSafeMedia(boolean z) {
        long j;
        int i = z ? 1002 : 1001;
        AudioService.AudioHandler audioHandler = this.mAudioHandler;
        audioHandler.removeMessages(i);
        if (z) {
            j =
                    SystemClock.uptimeMillis()
                            + (SystemProperties.getBoolean(
                                            "audio.safemedia.bypass",
                                            Rune.SEC_AUDIO_SAFE_VOLUME_COUNTRY ^ true)
                                    ? 0
                                    : 30000);
        } else {
            j = 0;
        }
        audioHandler.sendMessageAtTime(audioHandler.obtainMessage(i, 0, 0, "AS.AudioService"), j);
    }

    public final void dump(PrintWriter printWriter) {
        printWriter.print("  mEnableCsd=");
        printWriter.println(this.mEnableCsd.get());
        if (this.mEnableCsd.get()) {
            synchronized (this.mCsdStateLock) {
                printWriter.print("  mCurrentCsd=");
                printWriter.println(this.mCurrentCsd);
            }
        }
        printWriter.print("  mSafeMediaVolumeState=");
        printWriter.println(safeMediaVolumeStateToString(this.mSafeMediaVolumeState));
        printWriter.print("  mSafeMediaVolumeIndex=");
        printWriter.println(this.mSafeMediaVolumeIndex);
        for (int i = 0; i < this.mSafeMediaVolumeDevices.size(); i++) {
            printWriter.print("  mSafeMediaVolumeIndex[");
            printWriter.print(this.mSafeMediaVolumeDevices.keyAt(i));
            printWriter.print("]=");
            printWriter.println(this.mSafeMediaVolumeDevices.valueAt(i));
        }
        printWriter.print("  mSafeMediaVolumeDbfs=");
        printWriter.println(this.mSafeMediaVolumeDbfs);
        printWriter.print("  mMusicActiveMs=");
        printWriter.println(this.mMusicActiveMs);
        printWriter.print("  mMcc=");
        printWriter.println(this.mMcc);
        printWriter.print("  mPendingVolumeCommand=");
        printWriter.println(this.mPendingVolumeCommand);
        printWriter.println();
        this.mLogger.dump(printWriter);
        printWriter.println();
        printWriter.println();
        printWriter.print("  SEC_AUDIO_SAFE_MEDIA_VOLUME=");
        printWriter.println(Rune.SEC_AUDIO_SAFE_MEDIA_VOLUME);
        printWriter.print("  SEC_AUDIO_SAFE_VOLUME_COUNTRY=");
        printWriter.println(Rune.SEC_AUDIO_SAFE_VOLUME_COUNTRY);
        printWriter.print("  mIsVolumeEffectOn=");
        printWriter.println(this.mIsVolumeEffectOn);
        printWriter.println();
        this.mEarShockLogger.dump(printWriter);
        printWriter.println();
    }

    public final void enforceSafeMediaVolume(String str) {
        AudioService.VolumeStreamState volumeStreamState = this.mAudioService.mStreamStates[3];
        for (int i = 0; i < this.mSafeMediaVolumeDevices.size(); i++) {
            int keyAt = this.mSafeMediaVolumeDevices.keyAt(i);
            int index = volumeStreamState.getIndex(keyAt);
            int safeMediaVolumeIndex = safeMediaVolumeIndex(keyAt);
            if (index > safeMediaVolumeIndex) {
                volumeStreamState.setIndex(safeMediaVolumeIndex, keyAt, str, true);
                AudioService.AudioHandler audioHandler = this.mAudioHandler;
                audioHandler.sendMessageAtTime(
                        audioHandler.obtainMessage(0, keyAt, 0, volumeStreamState), 0L);
            }
        }
    }

    public final int getSafeDeviceMediaVolumeIndex(int i) {
        if (!this.mEnableCsd.get()) {
            if (i == 8 || i == 4) {
                return this.mSafeMediaVolumeIndex;
            }
            if (i != 67108864) {
                return this.mSafeMediaVolumeIndex;
            }
        }
        int i2 = AudioService.MIN_STREAM_VOLUME[3];
        int i3 = AudioService.MAX_STREAM_VOLUME[3];
        this.mSafeMediaVolumeDbfs =
                this.mContext.getResources().getInteger(R.integer.config_stableDeviceDisplayWidth)
                        / 100.0f;
        while (Math.abs(i3 - i2) > 1) {
            int i4 = (i3 + i2) / 2;
            float streamVolumeDB = AudioSystem.getStreamVolumeDB(3, i4, i);
            if (Float.isNaN(streamVolumeDB)) {
                break;
            }
            float f = this.mSafeMediaVolumeDbfs;
            if (streamVolumeDB == f) {
                break;
            }
            if (streamVolumeDB < f) {
                i2 = i4;
            } else {
                i3 = i4;
            }
        }
        return (Rune.SEC_AUDIO_VOLUME_MONITOR_PHASE_3
                        ? 7
                        : Rune.SEC_AUDIO_SAFE_MEDIA_VOLUME
                                ? this.mContext
                                        .getResources()
                                        .getInteger(R.integer.config_stableDeviceDisplayHeight)
                                : 9)
                * 10;
    }

    public final void handleMessage(Message message) {
        boolean z;
        AudioDeviceAttributes audioDeviceAttributes;
        int i;
        int i2 = message.what;
        switch (i2) {
            case 1001:
            case 1002:
                z = i2 == 1002;
                String str = (String) message.obj;
                updateCsdEnabled(str);
                synchronized (this.mSafeMediaVolumeStateLock) {
                    int i3 = this.mContext.getResources().getConfiguration().mcc;
                    int i4 = this.mMcc;
                    if (i4 != i3 || (i4 == 0 && z)) {
                        this.mSafeMediaVolumeIndex =
                                this.mIsVolumeEffectOn
                                        ? 60
                                        : Rune.SEC_AUDIO_VOLUME_MONITOR_PHASE_3
                                                ? 70
                                                : this.mContext
                                                                .getResources()
                                                                .getInteger(
                                                                        R.integer
                                                                                .config_stableDeviceDisplayHeight)
                                                        * 10;
                        initSafeMediaVolumeIndex();
                        updateSafeMediaVolume_l(str);
                        this.mMcc = i3;
                    }
                }
                return;
            case 1003:
                int i5 = message.arg1;
                ContentResolver contentResolver = this.mAudioService.mContentResolver;
                this.mSettings.getClass();
                Settings.Global.putInt(contentResolver, "audio_safe_volume_state", i5);
                return;
            case 1004:
                int i6 = message.arg1;
                SettingsAdapter settingsAdapter = this.mSettings;
                ContentResolver contentResolver2 = this.mAudioService.mContentResolver;
                settingsAdapter.getClass();
                Settings.Secure.putIntForUser(
                        contentResolver2, "unsafe_volume_music_active_ms", i6, -2);
                return;
            case 1005:
                synchronized (this.mCsdStateLock) {
                    try {
                        if (this.mGlobalTimeOffsetInSecs == -1) {
                            this.mGlobalTimeOffsetInSecs = System.currentTimeMillis() / 1000;
                        }
                        SettingsAdapter settingsAdapter2 = this.mSettings;
                        ContentResolver contentResolver3 = this.mAudioService.mContentResolver;
                        String f = Float.toString(this.mCurrentCsd);
                        settingsAdapter2.getClass();
                        Settings.Global.putString(
                                contentResolver3, "audio_safe_csd_current_value", f);
                        SettingsAdapter settingsAdapter3 = this.mSettings;
                        ContentResolver contentResolver4 = this.mAudioService.mContentResolver;
                        String f2 = Float.toString(this.mNextCsdWarning);
                        settingsAdapter3.getClass();
                        Settings.Global.putString(
                                contentResolver4, "audio_safe_csd_next_warning", f2);
                        SettingsAdapter settingsAdapter4 = this.mSettings;
                        ContentResolver contentResolver5 = this.mAudioService.mContentResolver;
                        String str2 =
                                (String)
                                        this.mDoseRecords.stream()
                                                .map(
                                                        new Function() { // from class:
                                                                         // com.android.server.audio.SoundDoseHelper$$ExternalSyntheticLambda2
                                                            @Override // java.util.function.Function
                                                            public final Object apply(Object obj) {
                                                                SoundDoseRecord soundDoseRecord =
                                                                        (SoundDoseRecord) obj;
                                                                return (soundDoseRecord.timestamp
                                                                                + SoundDoseHelper
                                                                                        .this
                                                                                        .mGlobalTimeOffsetInSecs)
                                                                        + ","
                                                                        + soundDoseRecord.duration
                                                                        + ","
                                                                        + String.format(
                                                                                "%.3f",
                                                                                Float.valueOf(
                                                                                        soundDoseRecord
                                                                                                .value))
                                                                        + ","
                                                                        + String.format(
                                                                                "%.3f",
                                                                                Float.valueOf(
                                                                                        soundDoseRecord
                                                                                                .averageMel));
                                                            }
                                                        })
                                                .collect(Collectors.joining("|"));
                        settingsAdapter4.getClass();
                        Settings.Global.putString(
                                contentResolver5, "audio_safe_csd_dose_records", str2);
                    } finally {
                    }
                }
                return;
            case 1006:
                int i7 = message.arg1;
                z = message.arg2 == 1;
                AudioService.VolumeStreamState volumeStreamState =
                        (AudioService.VolumeStreamState) message.obj;
                int index = volumeStreamState.getIndex(i7);
                int i8 = volumeStreamState.mStreamType;
                if (this.mEnableCsd.get()) {
                    ISoundDose iSoundDose = (ISoundDose) this.mSoundDose.get();
                    if (iSoundDose == null) {
                        Log.w(
                                "AS.SoundDoseHelper",
                                "Can not apply attenuation. ISoundDose itf is null.");
                        return;
                    }
                    try {
                        if (!z) {
                            iSoundDose.updateAttenuation(
                                    FullScreenMagnificationGestureHandler.MAX_SCALE, i7);
                        } else if (AudioService.mStreamVolumeAlias[i8] == 3
                                && safeDevicesContains(i7)) {
                            iSoundDose.updateAttenuation(
                                    -AudioSystem.getStreamVolumeDB(3, (index + 5) / 10, i7), i7);
                        }
                        return;
                    } catch (RemoteException e) {
                        Log.e(
                                "AS.SoundDoseHelper",
                                "Could not apply the attenuation for MEL calculation with volume"
                                    + " index "
                                        + index,
                                e);
                        return;
                    }
                }
                return;
            case 1007:
                this.mLogger.enqueue(
                        new AudioServiceEvents$SoundDoseEvent(
                                4, 0L, FullScreenMagnificationGestureHandler.MAX_SCALE));
                ArrayList devicesForAttributesInt =
                        this.mAudioService.getDevicesForAttributesInt(
                                new AudioAttributes.Builder().setUsage(1).build(), true);
                if (devicesForAttributesInt.isEmpty()) {
                    audioDeviceAttributes = new AudioDeviceAttributes(67108864, "");
                    i = 67108864;
                } else {
                    AudioDeviceAttributes audioDeviceAttributes2 =
                            (AudioDeviceAttributes) devicesForAttributesInt.get(0);
                    i = audioDeviceAttributes2.getInternalType();
                    audioDeviceAttributes = audioDeviceAttributes2;
                }
                this.mAudioService.setStreamVolumeWithAttributionInt(
                        3,
                        safeMediaVolumeIndex(i) / 10,
                        0,
                        audioDeviceAttributes,
                        this.mContext.getOpPackageName(),
                        null,
                        true);
                return;
            default:
                Log.e("AS.SoundDoseHelper", "Unexpected msg to handle: " + message.what);
                return;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x00eb A[Catch: all -> 0x0089, TryCatch #2 {all -> 0x0089, blocks: (B:28:0x0077, B:30:0x007f, B:31:0x008b, B:33:0x009b, B:35:0x00ba, B:38:0x00c1, B:40:0x00eb, B:42:0x00f3), top: B:27:0x0077 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void initCsd() {
        /*
            Method dump skipped, instructions count: 253
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: com.android.server.audio.SoundDoseHelper.initCsd():void");
    }

    public final void initSafeMediaVolumeIndex() {
        for (int i = 0; i < this.mSafeMediaVolumeDevices.size(); i++) {
            int keyAt = this.mSafeMediaVolumeDevices.keyAt(i);
            if (this.mSafeMediaVolumeDevices.valueAt(i) == -1) {
                this.mSafeMediaVolumeDevices.put(keyAt, getSafeDeviceMediaVolumeIndex(keyAt));
            }
        }
    }

    public final void initSafeMediaVolumeIndex(boolean z) {
        this.mIsVolumeEffectOn = z;
        this.mEarShockLogger.enqueueAndLog(
                0, "initSafeMediaVolumeIndex isVolumeEffectOn is " + z, "AS.SoundDoseHelper");
        for (int i = 0; i < this.mSafeMediaVolumeDevices.size(); i++) {
            int keyAt = this.mSafeMediaVolumeDevices.keyAt(i);
            this.mSafeMediaVolumeDevices.put(keyAt, z ? 60 : getSafeDeviceMediaVolumeIndex(keyAt));
        }
    }

    public final float parseGlobalSettingFloat(float f, String str) {
        ContentResolver contentResolver = this.mAudioService.mContentResolver;
        this.mSettings.getClass();
        String string = Settings.Global.getString(contentResolver, str);
        if (string == null || string.isEmpty()) {
            Log.v("AS.SoundDoseHelper", "No value stored in settings ".concat(str));
            return f;
        }
        try {
            return Float.parseFloat(string);
        } catch (NumberFormatException e) {
            Log.e("AS.SoundDoseHelper", "Error parsing float from settings ".concat(str), e);
            return f;
        }
    }

    public final void reset(boolean z) {
        Log.d("AS.SoundDoseHelper", "Reset the sound dose helper");
        if (z) {
            this.mSoundDose.set(AudioSystem.getSoundDoseInterface(this.mSoundDoseCallback));
        }
        synchronized (this.mCsdStateLock) {
            try {
                ISoundDose iSoundDose = (ISoundDose) this.mSoundDose.get();
                if (iSoundDose != null
                        && iSoundDose.asBinder().isBinderAlive()
                        && this.mCurrentCsd != FullScreenMagnificationGestureHandler.MAX_SCALE) {
                    Log.d(
                            "AS.SoundDoseHelper",
                            "Resetting the saved sound dose value " + this.mCurrentCsd);
                    iSoundDose.resetCsd(
                            this.mCurrentCsd,
                            (SoundDoseRecord[])
                                    ((ArrayList) this.mDoseRecords)
                                            .toArray(new SoundDoseRecord[0]));
                }
            } catch (RemoteException unused) {
            }
        }
    }

    public final boolean safeDevicesContains(int i) {
        return this.mSafeMediaVolumeDevices.get(i, -1) >= 0;
    }

    public final int safeMediaVolumeIndex(int i) {
        int i2 = this.mSafeMediaVolumeDevices.get(i);
        return i2 == -1 ? AudioService.MAX_STREAM_VOLUME[3] : i2;
    }

    public final void sanitizeDoseRecords_l() {
        if (this.mDoseRecords.size() > 655) {
            int size = this.mDoseRecords.size() - 655;
            StringBuilder m =
                    BatteryService$$ExternalSyntheticOutline0.m(
                            size, "Removing ", " records from the total of ");
            m.append(this.mDoseRecords.size());
            Log.w("AS.SoundDoseHelper", m.toString());
            Iterator it = this.mDoseRecords.iterator();
            while (it.hasNext() && size > 0) {
                it.next();
                it.remove();
                size--;
            }
        }
    }

    public final void scheduleMusicActiveCheck() {
        synchronized (this.mSafeMediaVolumeStateLock) {
            PendingIntent pendingIntent = this.mMusicActiveIntent;
            if (pendingIntent != null) {
                this.mAlarmManager.cancel(pendingIntent);
                this.mMusicActiveIntent = null;
            }
            this.mMusicActiveIntent =
                    PendingIntent.getBroadcast(
                            this.mContext,
                            1,
                            new Intent("com.android.server.audio.action.CHECK_MUSIC_ACTIVE"),
                            201326592);
            this.mAlarmManager.setExactAndAllowWhileIdle(
                    2, SystemClock.elapsedRealtime() + 60000, this.mMusicActiveIntent);
        }
    }

    public final void setAudioDeviceCategory(int i, String str, boolean z) {
        if (this.mEnableCsd.get()) {
            ISoundDose iSoundDose = (ISoundDose) this.mSoundDose.get();
            if (iSoundDose == null) {
                Log.w("AS.SoundDoseHelper", "Sound dose interface not initialized");
                return;
            }
            try {
                ISoundDose.AudioDeviceCategory audioDeviceCategory =
                        new ISoundDose.AudioDeviceCategory();
                audioDeviceCategory.address = str;
                audioDeviceCategory.internalAudioType = i;
                audioDeviceCategory.csdCompatible = z;
                iSoundDose.setAudioDeviceCategory(audioDeviceCategory);
            } catch (RemoteException e) {
                Log.e("AS.SoundDoseHelper", "Exception while setting the audio device category", e);
            }
        }
    }

    public final void setSafeMediaVolumeEnabled(String str, boolean z) {
        int i = this.mSafeMediaVolumeState;
        if (i == 0 || i == 1) {
            return;
        }
        if (z && i == 2) {
            this.mSafeMediaVolumeState = 3;
            enforceSafeMediaVolume(str);
            return;
        }
        if (z || i != 3) {
            return;
        }
        if (!Rune.SEC_AUDIO_VOLUME_MONITOR_PHASE_3) {
            this.mSafeMediaVolumeState = 2;
            this.mMusicActiveMs = 1;
            this.mLastMusicActiveTimeMs = 0L;
            this.mAudioHandler.obtainMessage(1004, 1, 0).sendToTarget();
            scheduleMusicActiveCheck();
            return;
        }
        synchronized (this.mSafeMediaVolumeStateLock) {
            this.mSafeMediaVolumeState = 1;
            this.mSafeMediaVolumeStateForBlueTooth = 1;
            AudioService.AudioHandler audioHandler = this.mAudioHandler;
            audioHandler.sendMessageAtTime(audioHandler.obtainMessage(1003, 1, 0, null), 0L);
            this.mMusicActiveMs = 0;
            this.mAudioHandler.obtainMessage(1004, 0, 0).sendToTarget();
        }
    }

    public final void updateCsdEnabled(String str) {
        boolean z = false;
        this.mForceCsdProperty.set(SystemProperties.getBoolean("audio.safemedia.csd.force", false));
        boolean z2 =
                this.mContext
                        .getResources()
                        .getBoolean(R.bool.config_showBuiltinWirelessChargingAnim);
        boolean z3 =
                this.mContext.getResources().getBoolean(R.bool.config_showGesturalNavigationHints);
        boolean z4 = (z2 && z3) || this.mForceCsdProperty.get();
        synchronized (this.mCsdAsAFeatureLock) {
            try {
                if (z2 || !z3) {
                    this.mIsCsdAsAFeatureAvailable = false;
                } else {
                    this.mIsCsdAsAFeatureAvailable = true;
                    if (!this.mIsCsdAsAFeatureEnabled) {
                        if (this.mForceCsdProperty.get()) {}
                        Log.v(
                                "AS.SoundDoseHelper",
                                str + ": CSD as a feature is not enforced and enabled: " + z);
                        z4 = z;
                    }
                    z = true;
                    Log.v(
                            "AS.SoundDoseHelper",
                            str + ": CSD as a feature is not enforced and enabled: " + z);
                    z4 = z;
                }
            } finally {
            }
        }
        if (this.mEnableCsd.compareAndSet(!z4, z4)) {
            Log.i("AS.SoundDoseHelper", str + ": enabled CSD " + z4);
            initCsd();
            synchronized (this.mSafeMediaVolumeStateLock) {
                initSafeMediaVolumeIndex();
                updateSafeMediaVolume_l(str);
            }
        }
    }

    public final boolean updateCsdForTestApi() {
        if (this.mForceCsdProperty.get()
                != SystemProperties.getBoolean("audio.safemedia.csd.force", false)) {
            updateCsdEnabled("SystemPropertiesChangeCallback");
        }
        return this.mEnableCsd.get();
    }

    public final void updateSafeMediaVolume_l(String str) {
        int i = 1;
        boolean z =
                SystemProperties.getBoolean("audio.safemedia.bypass", false)
                        || this.mEnableCsd.get();
        boolean z2 = SystemProperties.getBoolean("audio.safemedia.force", false);
        if (!Rune.SEC_AUDIO_SAFE_VOLUME_COUNTRY) {
            z = true;
        }
        boolean z3 =
                (this.mContext
                                        .getResources()
                                        .getBoolean(R.bool.config_showBuiltinWirelessChargingAnim)
                                || z2
                                || Rune.SEC_AUDIO_SAFE_MEDIA_VOLUME)
                        && !z;
        boolean z4 = Rune.SEC_AUDIO_VOLUME_MONITOR_PHASE_3;
        if (z4) {
            int i2 = this.mSafeMediaVolumeState;
            if (i2 == 1) {
                z3 = false;
            } else if (i2 == 3) {
                z3 = true;
            }
        }
        FactoryUtils.isFactoryMode();
        if (Rune.SEC_AUDIO_BIKE_MODE) {
            boolean z5 = this.mAudioService.mExt.mIsBikeMode;
        }
        if (z3) {
            if (this.mSafeMediaVolumeState != 2) {
                if (this.mMusicActiveMs == 0) {
                    this.mSafeMediaVolumeState = 3;
                    enforceSafeMediaVolume(str);
                } else {
                    this.mSafeMediaVolumeState = 2;
                    this.mLastMusicActiveTimeMs = 0L;
                }
            }
            i = 3;
        } else {
            this.mSafeMediaVolumeState = 1;
        }
        if (z4) {
            return;
        }
        AudioService.AudioHandler audioHandler = this.mAudioHandler;
        audioHandler.sendMessageAtTime(audioHandler.obtainMessage(1003, i, 0, null), 0L);
    }
}
