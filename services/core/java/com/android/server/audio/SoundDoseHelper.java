package com.android.server.audio;

import android.R;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioSystem;
import android.media.ISoundDose;
import android.media.ISoundDoseCallback;
import android.media.SoundDoseRecord;
import android.os.Binder;
import android.os.HandlerExecutor;
import android.os.Message;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.provider.DeviceConfig;
import android.text.TextUtils;
import android.util.Log;
import android.util.MathUtils;
import android.util.SparseIntArray;
import com.android.server.audio.AudioService;
import com.android.server.display.DisplayPowerController2;
import com.android.server.utils.EventLogger;
import com.samsung.android.audio.Rune;
import com.samsung.android.server.audio.FactoryUtils;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* loaded from: classes.dex */
public class SoundDoseHelper {
    public final AlarmManager mAlarmManager;
    public final AudioService.AudioHandler mAudioHandler;
    public final AudioService mAudioService;
    public final Context mContext;
    public float mCurrentCsd;
    public final List mDoseRecords;
    public final EventLogger mEarShockLogger;
    public long mGlobalTimeOffsetInSecs;
    public boolean mIsVolumeEffectOn;
    public long mLastMomentaryExposureTimeMs;
    public int mMusicActiveMs;
    public float mNextCsdWarning;
    public StreamVolumeCommand mPendingVolumeCommand;
    public float mSafeMediaVolumeDbfs;
    public int mSafeMediaVolumeIndex;
    public int mSafeMediaVolumeState;
    public int mSafeMediaVolumeStateForBlueTooth;
    public final SettingsAdapter mSettings;
    public final AtomicReference mSoundDose;
    public final ISoundDoseCallback.Stub mSoundDoseCallback;
    public AudioService.ISafeHearingVolumeController mVolumeController;
    public final EventLogger mLogger = new EventLogger(30, "CSD updates");
    public int mMcc = 0;
    public final Object mSafeMediaVolumeStateLock = new Object();
    public final SparseIntArray mSafeMediaVolumeDevices = new SparseIntArray();
    public final SparseIntArray mSafeMediaVolumeBTDevices = new SparseIntArray();
    public long mLastMusicActiveTimeMs = 0;
    public PendingIntent mMusicActiveIntent = null;
    public final AtomicBoolean mEnableCsd = new AtomicBoolean(false);
    public final Object mCsdStateLock = new Object();

    public static long convertToBootTime(long j, long j2) {
        return j - j2;
    }

    public static long convertToGlobalTime(long j, long j2) {
        return j + j2;
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

    public SoundDoseHelper(AudioService audioService, Context context, AudioService.AudioHandler audioHandler, SettingsAdapter settingsAdapter, AudioService.ISafeHearingVolumeController iSafeHearingVolumeController) {
        this.mSafeMediaVolumeStateForBlueTooth = 1;
        AtomicReference atomicReference = new AtomicReference();
        this.mSoundDose = atomicReference;
        this.mCurrentCsd = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
        this.mLastMomentaryExposureTimeMs = -1L;
        this.mNextCsdWarning = 1.0f;
        this.mDoseRecords = new ArrayList();
        this.mGlobalTimeOffsetInSecs = -1L;
        ISoundDoseCallback.Stub stub = new ISoundDoseCallback.Stub() { // from class: com.android.server.audio.SoundDoseHelper.1
            public void onMomentaryExposure(float f, int i) {
                boolean z;
                if (!SoundDoseHelper.this.mEnableCsd.get()) {
                    Log.w("AS.SoundDoseHelper", "onMomentaryExposure: csd not supported, ignoring callback");
                    return;
                }
                Log.w("AS.SoundDoseHelper", "DeviceId " + i + " triggered momentary exposure with value: " + f);
                SoundDoseHelper.this.mLogger.enqueue(AudioServiceEvents$SoundDoseEvent.getMomentaryExposureEvent(f));
                synchronized (SoundDoseHelper.this.mCsdStateLock) {
                    z = SoundDoseHelper.this.mLastMomentaryExposureTimeMs < 0 || System.currentTimeMillis() - SoundDoseHelper.this.mLastMomentaryExposureTimeMs >= 72000000;
                    SoundDoseHelper.this.mLastMomentaryExposureTimeMs = System.currentTimeMillis();
                }
                if (z) {
                    SoundDoseHelper.this.mVolumeController.postDisplayCsdWarning(3, SoundDoseHelper.this.getTimeoutMsForWarning(3));
                }
            }

            public void onNewCsdValue(float f, SoundDoseRecord[] soundDoseRecordArr) {
                if (!SoundDoseHelper.this.mEnableCsd.get()) {
                    Log.w("AS.SoundDoseHelper", "onNewCsdValue: csd not supported, ignoring value");
                    return;
                }
                Log.i("AS.SoundDoseHelper", "onNewCsdValue: " + f);
                synchronized (SoundDoseHelper.this.mCsdStateLock) {
                    if (SoundDoseHelper.this.mCurrentCsd < f) {
                        if (SoundDoseHelper.this.mCurrentCsd < SoundDoseHelper.this.mNextCsdWarning && f >= SoundDoseHelper.this.mNextCsdWarning) {
                            if (SoundDoseHelper.this.mNextCsdWarning == 5.0f) {
                                SoundDoseHelper.this.mVolumeController.postDisplayCsdWarning(2, SoundDoseHelper.this.getTimeoutMsForWarning(2));
                                SoundDoseHelper.this.mAudioService.postLowerVolumeToRs1();
                            } else {
                                SoundDoseHelper.this.mVolumeController.postDisplayCsdWarning(1, SoundDoseHelper.this.getTimeoutMsForWarning(1));
                            }
                            SoundDoseHelper.this.mNextCsdWarning += 1.0f;
                        }
                    } else if (f < SoundDoseHelper.this.mNextCsdWarning - 1.0f && SoundDoseHelper.this.mNextCsdWarning >= 2.0f) {
                        SoundDoseHelper.this.mNextCsdWarning -= 1.0f;
                    }
                    SoundDoseHelper.this.mCurrentCsd = f;
                    SoundDoseHelper.this.updateSoundDoseRecords_l(soundDoseRecordArr, f);
                }
            }
        };
        this.mSoundDoseCallback = stub;
        this.mEarShockLogger = new EventLogger(30, "Ear shock level history");
        this.mAudioService = audioService;
        this.mAudioHandler = audioHandler;
        this.mSettings = settingsAdapter;
        this.mVolumeController = iSafeHearingVolumeController;
        this.mContext = context;
        initSafeVolumes();
        this.mSafeMediaVolumeState = settingsAdapter.getGlobalInt(audioService.getContentResolver(), "audio_safe_volume_state", 0);
        if (Rune.SEC_AUDIO_SAFE_VOLUME_COUNTRY) {
            this.mSafeMediaVolumeStateForBlueTooth = 3;
        }
        this.mSafeMediaVolumeIndex = getSafeMediaVolumeIndex();
        atomicReference.set(AudioSystem.getSoundDoseInterface(stub));
        initCsd();
        this.mAlarmManager = (AlarmManager) context.getSystemService("alarm");
        DeviceConfig.addOnPropertiesChangedListener("media", new HandlerExecutor(audioHandler), new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.server.audio.SoundDoseHelper$$ExternalSyntheticLambda0
            public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                SoundDoseHelper.this.lambda$new$0(properties);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(DeviceConfig.Properties properties) {
        updateCsdEnabled("onPropertiesChanged");
    }

    public void initSafeVolumes() {
        this.mSafeMediaVolumeDevices.append(4, -1);
        this.mSafeMediaVolumeDevices.append(8, -1);
        this.mSafeMediaVolumeDevices.append(67108864, -1);
        this.mSafeMediaVolumeDevices.append(536870912, -1);
        this.mSafeMediaVolumeDevices.append(536870914, -1);
        this.mSafeMediaVolumeDevices.append(256, -1);
        boolean z = Rune.SEC_AUDIO_SAFE_MEDIA_VOLUME;
        this.mSafeMediaVolumeDevices.append(128, -1);
        this.mSafeMediaVolumeBTDevices.append(128, -1);
        this.mSafeMediaVolumeBTDevices.append(256, -1);
        this.mSafeMediaVolumeBTDevices.append(536870912, -1);
    }

    public float getOutputRs2UpperBound() {
        if (!this.mEnableCsd.get()) {
            return DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
        }
        ISoundDose iSoundDose = (ISoundDose) this.mSoundDose.get();
        if (iSoundDose == null) {
            Log.w("AS.SoundDoseHelper", "Sound dose interface not initialized");
            return DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
        }
        try {
            return iSoundDose.getOutputRs2UpperBound();
        } catch (RemoteException e) {
            Log.e("AS.SoundDoseHelper", "Exception while getting the RS2 exposure value", e);
            return DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
        }
    }

    public void setOutputRs2UpperBound(float f) {
        if (this.mEnableCsd.get()) {
            ISoundDose iSoundDose = (ISoundDose) this.mSoundDose.get();
            if (iSoundDose == null) {
                Log.w("AS.SoundDoseHelper", "Sound dose interface not initialized");
                return;
            }
            try {
                iSoundDose.setOutputRs2UpperBound(f);
            } catch (RemoteException e) {
                Log.e("AS.SoundDoseHelper", "Exception while setting the RS2 exposure value", e);
            }
        }
    }

    public float getCsd() {
        if (!this.mEnableCsd.get()) {
            return -1.0f;
        }
        ISoundDose iSoundDose = (ISoundDose) this.mSoundDose.get();
        if (iSoundDose == null) {
            Log.w("AS.SoundDoseHelper", "Sound dose interface not initialized");
            return -1.0f;
        }
        try {
            return iSoundDose.getCsd();
        } catch (RemoteException e) {
            Log.e("AS.SoundDoseHelper", "Exception while getting the CSD value", e);
            return -1.0f;
        }
    }

    public void setCsd(float f) {
        SoundDoseRecord[] soundDoseRecordArr;
        if (this.mEnableCsd.get()) {
            synchronized (this.mCsdStateLock) {
                this.mCurrentCsd = f;
                this.mNextCsdWarning = (float) Math.floor(f + 1.0d);
                this.mDoseRecords.clear();
                if (this.mCurrentCsd > DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
                    SoundDoseRecord soundDoseRecord = new SoundDoseRecord();
                    soundDoseRecord.timestamp = SystemClock.elapsedRealtime() / 1000;
                    soundDoseRecord.value = f;
                    this.mDoseRecords.add(soundDoseRecord);
                }
                soundDoseRecordArr = (SoundDoseRecord[]) this.mDoseRecords.toArray(new SoundDoseRecord[0]);
            }
            ISoundDose iSoundDose = (ISoundDose) this.mSoundDose.get();
            if (iSoundDose == null) {
                Log.w("AS.SoundDoseHelper", "Sound dose interface not initialized");
                return;
            }
            try {
                iSoundDose.resetCsd(f, soundDoseRecordArr);
            } catch (RemoteException e) {
                Log.e("AS.SoundDoseHelper", "Exception while setting the CSD value", e);
            }
        }
    }

    public void resetCsdTimeouts() {
        if (this.mEnableCsd.get()) {
            synchronized (this.mCsdStateLock) {
                this.mLastMomentaryExposureTimeMs = -1L;
            }
        }
    }

    public void forceUseFrameworkMel(boolean z) {
        if (this.mEnableCsd.get()) {
            ISoundDose iSoundDose = (ISoundDose) this.mSoundDose.get();
            if (iSoundDose == null) {
                Log.w("AS.SoundDoseHelper", "Sound dose interface not initialized");
                return;
            }
            try {
                iSoundDose.forceUseFrameworkMel(z);
            } catch (RemoteException e) {
                Log.e("AS.SoundDoseHelper", "Exception while forcing the internal MEL computation", e);
            }
        }
    }

    public void forceComputeCsdOnAllDevices(boolean z) {
        if (this.mEnableCsd.get()) {
            ISoundDose iSoundDose = (ISoundDose) this.mSoundDose.get();
            if (iSoundDose == null) {
                Log.w("AS.SoundDoseHelper", "Sound dose interface not initialized");
                return;
            }
            try {
                iSoundDose.forceComputeCsdOnAllDevices(z);
            } catch (RemoteException e) {
                Log.e("AS.SoundDoseHelper", "Exception while forcing CSD computation on all devices", e);
            }
        }
    }

    public boolean isCsdEnabled() {
        if (!this.mEnableCsd.get()) {
            return false;
        }
        ISoundDose iSoundDose = (ISoundDose) this.mSoundDose.get();
        if (iSoundDose == null) {
            Log.w("AS.SoundDoseHelper", "Sound dose interface not initialized");
            return false;
        }
        try {
            return iSoundDose.isSoundDoseHalSupported();
        } catch (RemoteException e) {
            Log.e("AS.SoundDoseHelper", "Exception while forcing CSD computation on all devices", e);
            return false;
        }
    }

    public int safeMediaVolumeIndex(int i) {
        int i2 = this.mSafeMediaVolumeDevices.get(i);
        return i2 == -1 ? AudioService.MAX_STREAM_VOLUME[3] : i2;
    }

    public void restoreMusicActiveMs() {
        synchronized (this.mSafeMediaVolumeStateLock) {
            this.mMusicActiveMs = MathUtils.constrain(this.mSettings.getSecureIntForUser(this.mAudioService.getContentResolver(), "unsafe_volume_music_active_ms", 0, -2), 0, 72000000);
        }
    }

    public void enforceSafeMediaVolumeIfActive(String str) {
        synchronized (this.mSafeMediaVolumeStateLock) {
            if (this.mSafeMediaVolumeState == 3) {
                enforceSafeMediaVolume(str);
            }
        }
    }

    public void enforceSafeMediaVolume(String str) {
        AudioService.VolumeStreamState vssVolumeForStream = this.mAudioService.getVssVolumeForStream(3);
        for (int i = 0; i < this.mSafeMediaVolumeDevices.size(); i++) {
            int keyAt = this.mSafeMediaVolumeDevices.keyAt(i);
            int index = vssVolumeForStream.getIndex(keyAt);
            int safeMediaVolumeIndex = safeMediaVolumeIndex(keyAt);
            if (index > safeMediaVolumeIndex) {
                vssVolumeForStream.setIndex(safeMediaVolumeIndex, keyAt, str, true);
                AudioService.AudioHandler audioHandler = this.mAudioHandler;
                audioHandler.sendMessageAtTime(audioHandler.obtainMessage(0, keyAt, 0, vssVolumeForStream), 0L);
            }
        }
    }

    public boolean checkSafeMediaVolume(int i, int i2, int i3) {
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
        return !(Rune.SEC_AUDIO_BIKE_MODE && this.mAudioService.isBikeMode()) && this.mSafeMediaVolumeState == 3 && AudioService.mStreamVolumeAlias[i] == 3 && safeDevicesContains(i3) && i2 > safeMediaVolumeIndex(i3) && !isSafeBTDevice(i3);
    }

    public boolean isSafeBTDevice(int i) {
        return this.mSafeMediaVolumeBTDevices.indexOfKey(i) >= 0;
    }

    public boolean willDisplayWarningAfterCheckVolume(int i, int i2, int i3, int i4) {
        synchronized (this.mSafeMediaVolumeStateLock) {
            if (!checkSafeMediaVolume_l(i, i2, i3)) {
                return false;
            }
            this.mVolumeController.postDisplaySafeVolumeWarning(i4);
            this.mPendingVolumeCommand = new StreamVolumeCommand(i, i2, i4, i3);
            return true;
        }
    }

    public void disableSafeMediaVolume(String str) {
        synchronized (this.mSafeMediaVolumeStateLock) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            setSafeMediaVolumeEnabled(false, str);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            StreamVolumeCommand streamVolumeCommand = this.mPendingVolumeCommand;
            if (streamVolumeCommand != null) {
                if (streamVolumeCommand.mStreamType == 3 && AudioSystem.DEVICE_OUT_ALL_A2DP_SET.contains(Integer.valueOf(streamVolumeCommand.mDevice))) {
                    this.mAudioService.checkAndPostSetAvrcpAbsoluteVolumeIndex(this.mPendingVolumeCommand.mIndex);
                }
                AudioService audioService = this.mAudioService;
                StreamVolumeCommand streamVolumeCommand2 = this.mPendingVolumeCommand;
                audioService.onSetStreamVolume(streamVolumeCommand2.mStreamType, streamVolumeCommand2.mIndex, streamVolumeCommand2.mFlags, streamVolumeCommand2.mDevice, str, true, true);
                Intent intent = new Intent("android.media.VOLUME_CHANGED_ACTION");
                intent.putExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", this.mPendingVolumeCommand.mStreamType);
                intent.putExtra("android.media.EXTRA_VOLUME_STREAM_VALUE", this.mPendingVolumeCommand.mIndex / 10);
                intent.putExtra("android.media.EXTRA_VOLUME_SHOW_UI", (this.mPendingVolumeCommand.mFlags & 1) != 0);
                this.mAudioService.sendBroadcastToAll(intent, null);
                AudioService.VolumeController volumeController = this.mAudioService.mVolumeController;
                StreamVolumeCommand streamVolumeCommand3 = this.mPendingVolumeCommand;
                volumeController.postVolumeChanged(streamVolumeCommand3.mStreamType, streamVolumeCommand3.mFlags);
                this.mPendingVolumeCommand = null;
            }
        }
    }

    public void scheduleMusicActiveCheck() {
        synchronized (this.mSafeMediaVolumeStateLock) {
            cancelMusicActiveCheck();
            this.mMusicActiveIntent = PendingIntent.getBroadcast(this.mContext, 1, new Intent("com.android.server.audio.action.CHECK_MUSIC_ACTIVE"), 201326592);
            this.mAlarmManager.setExactAndAllowWhileIdle(2, SystemClock.elapsedRealtime() + 60000, this.mMusicActiveIntent);
        }
    }

    public void onCheckMusicActive(String str, boolean z) {
        synchronized (this.mSafeMediaVolumeStateLock) {
            if (this.mSafeMediaVolumeState == 2) {
                int deviceForStream = this.mAudioService.getDeviceForStream(3);
                if (safeDevicesContains(deviceForStream) && z) {
                    scheduleMusicActiveCheck();
                    if (this.mAudioService.getVssVolumeForDevice(3, deviceForStream) > safeMediaVolumeIndex(deviceForStream)) {
                        long elapsedRealtime = SystemClock.elapsedRealtime();
                        long j = this.mLastMusicActiveTimeMs;
                        if (j != 0) {
                            this.mMusicActiveMs += (int) (elapsedRealtime - j);
                        }
                        this.mLastMusicActiveTimeMs = elapsedRealtime;
                        Log.i("AS.SoundDoseHelper", "onCheckMusicActive() mMusicActiveMs: " + this.mMusicActiveMs);
                        if (this.mMusicActiveMs > 72000000) {
                            setSafeMediaVolumeEnabled(true, str);
                            this.mMusicActiveMs = 0;
                        }
                        saveMusicActiveMs();
                    }
                } else {
                    cancelMusicActiveCheck();
                    this.mLastMusicActiveTimeMs = 0L;
                }
            }
        }
    }

    public void configureSafeMedia(boolean z, String str) {
        long j;
        int i = z ? 1002 : 1001;
        this.mAudioHandler.removeMessages(i);
        if (z) {
            j = SystemClock.uptimeMillis() + (SystemProperties.getBoolean("audio.safemedia.bypass", Rune.SEC_AUDIO_SAFE_VOLUME_COUNTRY ^ true) ? 0 : 30000);
        } else {
            j = 0;
        }
        AudioService.AudioHandler audioHandler = this.mAudioHandler;
        audioHandler.sendMessageAtTime(audioHandler.obtainMessage(i, 0, 0, str), j);
    }

    public void initSafeMediaVolumeIndex() {
        for (int i = 0; i < this.mSafeMediaVolumeDevices.size(); i++) {
            int keyAt = this.mSafeMediaVolumeDevices.keyAt(i);
            this.mSafeMediaVolumeDevices.put(keyAt, getSafeDeviceMediaVolumeIndex(keyAt));
        }
    }

    public int getSafeMediaVolumeIndex(int i) {
        if (this.mSafeMediaVolumeState == 3 && safeDevicesContains(i)) {
            return safeMediaVolumeIndex(i);
        }
        return -1;
    }

    public boolean raiseVolumeDisplaySafeMediaVolume(int i, int i2, int i3, int i4) {
        if (!checkSafeMediaVolume(i, i2, i3)) {
            return false;
        }
        this.mVolumeController.postDisplaySafeVolumeWarning(i4);
        return true;
    }

    public boolean safeDevicesContains(int i) {
        return this.mSafeMediaVolumeDevices.indexOfKey(i) >= 0;
    }

    public void invalidatPendingVolumeCommand() {
        synchronized (this.mSafeMediaVolumeStateLock) {
            this.mPendingVolumeCommand = null;
        }
    }

    public void handleMessage(Message message) {
        int i = message.what;
        switch (i) {
            case 1001:
            case 1002:
                onConfigureSafeMedia(i == 1002, (String) message.obj);
                break;
            case 1003:
                onPersistSafeVolumeState(message.arg1);
                break;
            case 1004:
                this.mSettings.putSecureIntForUser(this.mAudioService.getContentResolver(), "unsafe_volume_music_active_ms", message.arg1, -2);
                break;
            case 1005:
                onPersistSoundDoseRecords();
                break;
            case 1006:
                int i2 = message.arg1;
                boolean z = message.arg2 == 1;
                AudioService.VolumeStreamState volumeStreamState = (AudioService.VolumeStreamState) message.obj;
                updateDoseAttenuation(volumeStreamState.getIndex(i2), i2, volumeStreamState.getStreamType(), z);
                break;
            default:
                Log.e("AS.SoundDoseHelper", "Unexpected msg to handle: " + message.what);
                break;
        }
    }

    public void dump(PrintWriter printWriter) {
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
        printWriter.print("  mSafeMediaVolumeStateForBlueTooth=");
        printWriter.println(safeMediaVolumeStateToString(this.mSafeMediaVolumeStateForBlueTooth));
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
        customDump(printWriter);
        printWriter.println();
    }

    public void reset() {
        Log.d("AS.SoundDoseHelper", "Reset the sound dose helper");
        this.mSoundDose.compareAndExchange(null, AudioSystem.getSoundDoseInterface(this.mSoundDoseCallback));
        synchronized (this.mCsdStateLock) {
            try {
                ISoundDose iSoundDose = (ISoundDose) this.mSoundDose.get();
                if (iSoundDose != null && iSoundDose.asBinder().isBinderAlive() && this.mCurrentCsd != DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
                    Log.d("AS.SoundDoseHelper", "Resetting the saved sound dose value " + this.mCurrentCsd);
                    iSoundDose.resetCsd(this.mCurrentCsd, (SoundDoseRecord[]) this.mDoseRecords.toArray(new SoundDoseRecord[0]));
                }
            } catch (RemoteException unused) {
            }
        }
    }

    public final void updateDoseAttenuation(int i, int i2, int i3, boolean z) {
        if (this.mEnableCsd.get()) {
            ISoundDose iSoundDose = (ISoundDose) this.mSoundDose.get();
            if (iSoundDose == null) {
                Log.w("AS.SoundDoseHelper", "Can not apply attenuation. ISoundDose itf is null.");
                return;
            }
            try {
                if (!z) {
                    iSoundDose.updateAttenuation(DisplayPowerController2.RATE_FROM_DOZE_TO_ON, i2);
                } else if (AudioService.mStreamVolumeAlias[i3] == 3 && safeDevicesContains(i2)) {
                    iSoundDose.updateAttenuation(AudioSystem.getStreamVolumeDB(3, (i + 5) / 10, i2), i2);
                }
            } catch (RemoteException e) {
                Log.e("AS.SoundDoseHelper", "Could not apply the attenuation for MEL calculation with volume index " + i, e);
            }
        }
    }

    public final void initCsd() {
        ISoundDose iSoundDose = (ISoundDose) this.mSoundDose.get();
        if (iSoundDose == null) {
            Log.w("AS.SoundDoseHelper", "ISoundDose instance is null.");
            return;
        }
        try {
            iSoundDose.setCsdEnabled(this.mEnableCsd.get());
        } catch (RemoteException e) {
            Log.e("AS.SoundDoseHelper", "Cannot disable CSD", e);
        }
        if (this.mEnableCsd.get()) {
            Log.v("AS.SoundDoseHelper", "Initializing sound dose");
            synchronized (this.mCsdStateLock) {
                if (this.mGlobalTimeOffsetInSecs == -1) {
                    this.mGlobalTimeOffsetInSecs = System.currentTimeMillis() / 1000;
                }
                float f = this.mCurrentCsd;
                float parseGlobalSettingFloat = parseGlobalSettingFloat("audio_safe_csd_current_value", DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
                this.mCurrentCsd = parseGlobalSettingFloat;
                if (parseGlobalSettingFloat != f) {
                    this.mNextCsdWarning = parseGlobalSettingFloat("audio_safe_csd_next_warning", 1.0f);
                    List persistedStringToRecordList = persistedStringToRecordList(this.mSettings.getGlobalString(this.mAudioService.getContentResolver(), "audio_safe_csd_dose_records"), this.mGlobalTimeOffsetInSecs);
                    if (persistedStringToRecordList != null) {
                        this.mDoseRecords.addAll(persistedStringToRecordList);
                    }
                }
            }
            reset();
        }
    }

    public final void onConfigureSafeMedia(boolean z, String str) {
        updateCsdEnabled(str);
        synchronized (this.mSafeMediaVolumeStateLock) {
            int i = this.mContext.getResources().getConfiguration().mcc;
            int i2 = this.mMcc;
            if (i2 != i || (i2 == 0 && z)) {
                boolean z2 = Rune.SEC_AUDIO_VOLUME_MONITOR_PHASE_3;
                this.mSafeMediaVolumeIndex = getSafeMediaVolumeIndex();
                initSafeMediaVolumeIndex();
                updateSafeMediaVolume_l(str);
                this.mMcc = i;
            }
        }
    }

    public final void updateSafeMediaVolume_l(String str) {
        int i = 1;
        boolean z = SystemProperties.getBoolean("audio.safemedia.force", false) || Rune.SEC_AUDIO_SAFE_MEDIA_VOLUME;
        boolean z2 = SystemProperties.getBoolean("audio.safemedia.bypass", false);
        if (!Rune.SEC_AUDIO_SAFE_VOLUME_COUNTRY) {
            z2 = true;
        }
        if (Rune.SEC_AUDIO_VOLUME_MONITOR_PHASE_3) {
            int i2 = this.mSafeMediaVolumeState;
            if (i2 == 1) {
                z2 = true;
            } else if (i2 == 3) {
                z2 = false;
                z = true;
            }
        }
        if (FactoryUtils.isFactoryMode()) {
            z2 = true;
        }
        if (Rune.SEC_AUDIO_BIKE_MODE && this.mAudioService.isBikeMode()) {
            z2 = true;
        }
        if (z && !z2) {
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
        if (Rune.SEC_AUDIO_VOLUME_MONITOR_PHASE_3) {
            return;
        }
        AudioService.AudioHandler audioHandler = this.mAudioHandler;
        audioHandler.sendMessageAtTime(audioHandler.obtainMessage(1003, i, 0, null), 0L);
    }

    public final void updateCsdEnabled(String str) {
        boolean z = SystemProperties.getBoolean("audio.safemedia.csd.force", false);
        if (!z) {
            String property = DeviceConfig.getProperty("media", "enable_csd");
            if (property != null) {
                z = Boolean.parseBoolean(property);
            } else {
                z = this.mContext.getResources().getBoolean(R.bool.config_offsetWallpaperToCenterOfLargestDisplay);
            }
        }
        if (this.mEnableCsd.compareAndSet(!z, z)) {
            Log.i("AS.SoundDoseHelper", str + ": enable CSD " + z);
            initCsd();
            synchronized (this.mSafeMediaVolumeStateLock) {
                updateSafeMediaVolume_l(str);
            }
        }
    }

    public final int getTimeoutMsForWarning(int i) {
        if (i == 1) {
            return 7000;
        }
        int i2 = 5000;
        if (i != 2 && i != 3) {
            i2 = -1;
            if (i != 4) {
                Log.e("AS.SoundDoseHelper", "Invalid CSD warning " + i, new Exception());
            }
        }
        return i2;
    }

    public final void setSafeMediaVolumeEnabled(boolean z, String str) {
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
        if (Rune.SEC_AUDIO_VOLUME_MONITOR_PHASE_3) {
            setSafeMediaVolumePersist(1);
            return;
        }
        this.mSafeMediaVolumeState = 2;
        this.mMusicActiveMs = 1;
        this.mLastMusicActiveTimeMs = 0L;
        saveMusicActiveMs();
        scheduleMusicActiveCheck();
    }

    public final void cancelMusicActiveCheck() {
        PendingIntent pendingIntent = this.mMusicActiveIntent;
        if (pendingIntent != null) {
            this.mAlarmManager.cancel(pendingIntent);
            this.mMusicActiveIntent = null;
        }
    }

    public final void saveMusicActiveMs() {
        this.mAudioHandler.obtainMessage(1004, this.mMusicActiveMs, 0).sendToTarget();
    }

    public int getSafeDeviceMediaVolumeIndex(int i) {
        int integer;
        if ((i == 8 || i == 4) && !this.mEnableCsd.get()) {
            return this.mSafeMediaVolumeIndex;
        }
        int i2 = AudioService.MIN_STREAM_VOLUME[3];
        int i3 = AudioService.MAX_STREAM_VOLUME[3];
        this.mSafeMediaVolumeDbfs = this.mContext.getResources().getInteger(R.integer.config_screenBrightnessDim) / 100.0f;
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
        if (Rune.SEC_AUDIO_VOLUME_MONITOR_PHASE_3) {
            integer = 7;
        } else {
            integer = Rune.SEC_AUDIO_SAFE_MEDIA_VOLUME ? this.mContext.getResources().getInteger(R.integer.config_screenBrightnessDark) : 9;
        }
        return integer * 10;
    }

    public final void onPersistSafeVolumeState(int i) {
        this.mSettings.putGlobalInt(this.mAudioService.getContentResolver(), "audio_safe_volume_state", i);
    }

    public final void updateSoundDoseRecords_l(SoundDoseRecord[] soundDoseRecordArr, float f) {
        long j = 0;
        for (final SoundDoseRecord soundDoseRecord : soundDoseRecordArr) {
            Log.i("AS.SoundDoseHelper", "  new record: " + soundDoseRecord);
            j += (long) soundDoseRecord.duration;
            if (soundDoseRecord.value < DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
                if (!this.mDoseRecords.removeIf(new Predicate() { // from class: com.android.server.audio.SoundDoseHelper$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        boolean lambda$updateSoundDoseRecords_l$1;
                        lambda$updateSoundDoseRecords_l$1 = SoundDoseHelper.lambda$updateSoundDoseRecords_l$1(soundDoseRecord, (SoundDoseRecord) obj);
                        return lambda$updateSoundDoseRecords_l$1;
                    }
                })) {
                    Log.w("AS.SoundDoseHelper", "Could not find cached record to remove: " + soundDoseRecord);
                }
            } else {
                this.mDoseRecords.add(soundDoseRecord);
            }
        }
        AudioService.AudioHandler audioHandler = this.mAudioHandler;
        audioHandler.sendMessageAtTime(audioHandler.obtainMessage(1005, 0, 0, null), 0L);
        this.mLogger.enqueue(AudioServiceEvents$SoundDoseEvent.getDoseUpdateEvent(f, j));
    }

    public static /* synthetic */ boolean lambda$updateSoundDoseRecords_l$1(SoundDoseRecord soundDoseRecord, SoundDoseRecord soundDoseRecord2) {
        return soundDoseRecord2.value == (-soundDoseRecord.value) && soundDoseRecord2.timestamp == soundDoseRecord.timestamp && soundDoseRecord2.averageMel == soundDoseRecord.averageMel && soundDoseRecord2.duration == soundDoseRecord.duration;
    }

    public final void onPersistSoundDoseRecords() {
        synchronized (this.mCsdStateLock) {
            if (this.mGlobalTimeOffsetInSecs == -1) {
                this.mGlobalTimeOffsetInSecs = System.currentTimeMillis() / 1000;
            }
            this.mSettings.putGlobalString(this.mAudioService.getContentResolver(), "audio_safe_csd_current_value", Float.toString(this.mCurrentCsd));
            this.mSettings.putGlobalString(this.mAudioService.getContentResolver(), "audio_safe_csd_next_warning", Float.toString(this.mNextCsdWarning));
            this.mSettings.putGlobalString(this.mAudioService.getContentResolver(), "audio_safe_csd_dose_records", (String) this.mDoseRecords.stream().map(new Function() { // from class: com.android.server.audio.SoundDoseHelper$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    String lambda$onPersistSoundDoseRecords$2;
                    lambda$onPersistSoundDoseRecords$2 = SoundDoseHelper.this.lambda$onPersistSoundDoseRecords$2((SoundDoseRecord) obj);
                    return lambda$onPersistSoundDoseRecords$2;
                }
            }).collect(Collectors.joining("|")));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ String lambda$onPersistSoundDoseRecords$2(SoundDoseRecord soundDoseRecord) {
        return recordToPersistedString(soundDoseRecord, this.mGlobalTimeOffsetInSecs);
    }

    public static String recordToPersistedString(SoundDoseRecord soundDoseRecord, long j) {
        return convertToGlobalTime(soundDoseRecord.timestamp, j) + "," + soundDoseRecord.duration + "," + soundDoseRecord.value + "," + soundDoseRecord.averageMel;
    }

    public static List persistedStringToRecordList(String str, final long j) {
        if (str == null || str.isEmpty()) {
            return null;
        }
        return (List) Arrays.stream(TextUtils.split(str, "\\|")).map(new Function() { // from class: com.android.server.audio.SoundDoseHelper$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                SoundDoseRecord lambda$persistedStringToRecordList$3;
                lambda$persistedStringToRecordList$3 = SoundDoseHelper.lambda$persistedStringToRecordList$3(j, (String) obj);
                return lambda$persistedStringToRecordList$3;
            }
        }).filter(new Predicate() { // from class: com.android.server.audio.SoundDoseHelper$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Objects.nonNull((SoundDoseRecord) obj);
            }
        }).collect(Collectors.toList());
    }

    public static /* synthetic */ SoundDoseRecord lambda$persistedStringToRecordList$3(long j, String str) {
        return persistedStringToRecord(str, j);
    }

    public static SoundDoseRecord persistedStringToRecord(String str, long j) {
        if (str != null && !str.isEmpty()) {
            String[] split = TextUtils.split(str, ",");
            if (split.length != 4) {
                Log.w("AS.SoundDoseHelper", "Expecting 4 fields for a SoundDoseRecord, parsed " + split.length);
                return null;
            }
            SoundDoseRecord soundDoseRecord = new SoundDoseRecord();
            try {
                soundDoseRecord.timestamp = convertToBootTime(Long.parseLong(split[0]), j);
                soundDoseRecord.duration = Integer.parseInt(split[1]);
                soundDoseRecord.value = Float.parseFloat(split[2]);
                soundDoseRecord.averageMel = Float.parseFloat(split[3]);
                return soundDoseRecord;
            } catch (NumberFormatException e) {
                Log.e("AS.SoundDoseHelper", "Unable to parse persisted SoundDoseRecord: " + str, e);
            }
        }
        return null;
    }

    public final float parseGlobalSettingFloat(String str, float f) {
        String globalString = this.mSettings.getGlobalString(this.mAudioService.getContentResolver(), str);
        if (globalString == null || globalString.isEmpty()) {
            Log.v("AS.SoundDoseHelper", "No value stored in settings " + str);
            return f;
        }
        try {
            return Float.parseFloat(globalString);
        } catch (NumberFormatException e) {
            Log.e("AS.SoundDoseHelper", "Error parsing float from settings " + str, e);
            return f;
        }
    }

    public class StreamVolumeCommand {
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

        public String toString() {
            return "{streamType=" + this.mStreamType + ",index=" + this.mIndex + ",flags=" + this.mFlags + ",device=" + this.mDevice + '}';
        }
    }

    public boolean isSafeMediaVolumeStateActive() {
        return Rune.SEC_AUDIO_SAFE_MEDIA_VOLUME && this.mSafeMediaVolumeStateForBlueTooth == 3;
    }

    public int getSafeMediaVolumeState() {
        return this.mSafeMediaVolumeState;
    }

    public void setSafeMediaVolumeState(int i) {
        synchronized (this.mSafeMediaVolumeStateLock) {
            if (i != -1) {
                this.mSafeMediaVolumeState = i;
            }
        }
    }

    public void setSafeMediaVolumeStateForBlueTooth(int i) {
        synchronized (this.mSafeMediaVolumeStateLock) {
            if (i != -1) {
                this.mSafeMediaVolumeStateForBlueTooth = i;
            }
        }
    }

    public void setSafeMediaVolumePersist(int i) {
        synchronized (this.mSafeMediaVolumeStateLock) {
            this.mSafeMediaVolumeState = i;
            this.mSafeMediaVolumeStateForBlueTooth = i;
            AudioService.AudioHandler audioHandler = this.mAudioHandler;
            audioHandler.sendMessageAtTime(audioHandler.obtainMessage(1003, i, 0, null), 0L);
            this.mMusicActiveMs = 0;
            saveMusicActiveMs();
        }
    }

    public final int getSafeMediaVolumeIndex() {
        if (this.mIsVolumeEffectOn) {
            return 60;
        }
        if (Rune.SEC_AUDIO_VOLUME_MONITOR_PHASE_3) {
            return 70;
        }
        return this.mContext.getResources().getInteger(R.integer.config_screenBrightnessDark) * 10;
    }

    public void setSafeVolumeController(AudioService.ISafeHearingVolumeController iSafeHearingVolumeController) {
        this.mVolumeController = iSafeHearingVolumeController;
    }

    public void initSafeMediaVolumeIndex(boolean z) {
        this.mIsVolumeEffectOn = z;
        this.mEarShockLogger.enqueueAndLog("initSafeMediaVolumeIndex isVolumeEffectOn is " + z, 0, "AS.SoundDoseHelper");
        for (int i = 0; i < this.mSafeMediaVolumeDevices.size(); i++) {
            int keyAt = this.mSafeMediaVolumeDevices.keyAt(i);
            this.mSafeMediaVolumeDevices.put(keyAt, getSafeDeviceMediaVolumeIndex(keyAt, z));
        }
    }

    public int getSafeDeviceMediaVolumeIndex(int i, boolean z) {
        if (z) {
            return 60;
        }
        return getSafeDeviceMediaVolumeIndex(i);
    }

    public void customDump(PrintWriter printWriter) {
        printWriter.println();
        printWriter.print("  SEC_AUDIO_SAFE_MEDIA_VOLUME=");
        printWriter.println(Rune.SEC_AUDIO_SAFE_MEDIA_VOLUME);
        printWriter.print("  SEC_AUDIO_SAFE_VOLUME_COUNTRY=");
        printWriter.println(Rune.SEC_AUDIO_SAFE_VOLUME_COUNTRY);
        printWriter.print("  mIsVolumeEffectOn=");
        printWriter.println(this.mIsVolumeEffectOn);
        printWriter.println();
        this.mEarShockLogger.dump(printWriter);
    }
}
