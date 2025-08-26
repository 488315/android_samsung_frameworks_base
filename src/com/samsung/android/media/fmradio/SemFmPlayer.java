package com.samsung.android.media.fmradio;

import android.content.Context;
import android.media.AudioManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.util.Log;
import com.samsung.android.lock.LsConstants;
import com.samsung.android.media.fmradio.internal.IFMPlayer;

/* loaded from: classes6.dex */
public class SemFmPlayer {
    public static final int AUDIO_MODE_MONO = 8;
    public static final int AUDIO_MODE_STEREO = 9;
    private static final boolean DEBUG = true;
    private static final String LOG_TAG = "FmPlayer";
    public static final int OFF_AIRPLANE_MODE_SET = 3;
    public static final int OFF_BATTERY_LOW = 7;
    public static final int OFF_CALL_ACTIVE = 1;
    public static final int OFF_DEVICE_SHUTDOWN = 6;
    public static final int OFF_EAR_PHONE_DISCONNECT = 2;
    public static final int OFF_NORMAL = 0;
    public static final int OFF_PAUSE_COMMAND = 5;
    public static final int OFF_STOP_COMMAND = 4;
    private AudioManager mAudioManager;
    private Context mContext;
    private IFMPlayer mPlayer = IFMPlayer.Stub.asInterface(ServiceManager.getService(Context.SEM_FM_RADIO_SERVICE));

    public void log(String str) {
        Log.i(LOG_TAG, str);
    }

    public SemFmPlayer(Context context) {
        this.mContext = context;
        this.mAudioManager = (AudioManager) context.getSystemService("audio");
        log("Player created :" + this.mPlayer);
    }

    public boolean enableRadio() throws SemFmPlayerException {
        boolean zEqualsIgnoreCase = "factory".equalsIgnoreCase(SystemProperties.get("ro.factory.factory_binary", LsConstants.TAG_UNKNOWN));
        if (isAirPlaneMode() && !zEqualsIgnoreCase) {
            throw new SemAirPlaneModeEnabledException("AirPlane mode is on.", new Throwable("AirPlane mode is on."));
        }
        boolean zOn = false;
        if (zEqualsIgnoreCase) {
            try {
                return this.mPlayer.on_in_testmode();
            } catch (RemoteException e) {
                this.remoteError(e);
                return false;
            }
        }
        if (isTvOutPlugged()) {
            throw new SemTvOutConnectedException("TV out is on", new Throwable("TV out is on."));
        }
        if (!isHeadsetPlugged()) {
            throw new SemHeadsetNotConnectedException("Headset is not presents.", new Throwable("Headset is not presents."));
        }
        try {
            zOn = this.mPlayer.on();
        } catch (RemoteException e2) {
            remoteError(e2);
        }
        if (isBatteryLow()) {
            throw new SemLowBatteryException("Battery is low.", new Throwable("Batterys is low."));
        }
        return zOn;
    }

    public boolean isHeadsetPlugged() throws SemFmPlayerException {
        try {
            return this.mPlayer.isHeadsetPlugged();
        } catch (RemoteException e) {
            this.remoteError(e);
            return false;
        }
    }

    public boolean isTvOutPlugged() throws SemFmPlayerException {
        try {
            return this.mPlayer.isTvOutPlugged();
        } catch (RemoteException e) {
            this.remoteError(e);
            return false;
        }
    }

    public boolean isAirPlaneMode() throws SemFmPlayerException {
        try {
            return this.mPlayer.isAirPlaneMode();
        } catch (RemoteException e) {
            this.remoteError(e);
            return false;
        }
    }

    public boolean isBatteryLow() throws SemFmPlayerException {
        try {
            return this.mPlayer.isBatteryLow();
        } catch (RemoteException e) {
            this.remoteError(e);
            return false;
        }
    }

    public boolean setSpeakerEnabled(boolean z) throws SemFmPlayerException {
        log("setting speakerOn = :" + z);
        try {
            this.mPlayer.setSpeakerOn(z);
        } catch (RemoteException e) {
            remoteError(e);
        }
        if (z) {
            this.mAudioManager.semSetRadioOutputPath(2);
        } else {
            this.mAudioManager.semSetRadioOutputPath(3);
        }
        return this.mAudioManager.semGetRadioOutputPath() == 2;
    }

    public boolean disableRadio() throws SemFmPlayerException {
        try {
            return this.mPlayer.off();
        } catch (RemoteException e) {
            this.remoteError(e);
            return false;
        }
    }

    public boolean isRadioEnabled() throws SemFmPlayerException {
        try {
            return this.mPlayer.isOn();
        } catch (RemoteException e) {
            this.remoteError(e);
            return false;
        }
    }

    public void startScan() throws SemFmPlayerException {
        checkOnStatus();
        try {
            checkBusy();
            this.mPlayer.scan();
        } catch (RemoteException e) {
            remoteError(e);
        }
    }

    public long searchDown() throws SemFmPlayerException {
        checkOnStatus();
        try {
            checkBusy();
            return this.mPlayer.searchDown();
        } catch (RemoteException e) {
            remoteError(e);
            return -1L;
        }
    }

    public void setAudioMode(int i) throws SemFmPlayerException {
        checkOnStatus();
        try {
            if (i == 9) {
                this.mPlayer.setStereo();
            } else if (i == 8) {
                this.mPlayer.setMono();
            }
        } catch (RemoteException e) {
            remoteError(e);
        }
    }

    public long searchUp() throws SemFmPlayerException {
        checkOnStatus();
        try {
            checkBusy();
            return this.mPlayer.searchUp();
        } catch (RemoteException e) {
            remoteError(e);
            return -1L;
        }
    }

    public long getPlayedFreq() throws SemFmPlayerException {
        try {
            return this.mPlayer.getPlayedFreq();
        } catch (RemoteException e) {
            remoteError(e);
            return -1L;
        }
    }

    public long searchAll() throws SemFmPlayerException {
        checkOnStatus();
        try {
            checkBusy();
            return this.mPlayer.searchAll();
        } catch (RemoteException e) {
            remoteError(e);
            return -1L;
        }
    }

    public void setRadioDataSystemEnabled(boolean z) throws SemFmPlayerException {
        checkOnStatus();
        try {
            if (z) {
                this.mPlayer.enableRDS();
            } else {
                this.mPlayer.disableRDS();
            }
        } catch (RemoteException e) {
            remoteError(e);
        }
    }

    public void setAlternateFrequencyEnabled(boolean z) throws SemFmPlayerException {
        checkOnStatus();
        try {
            if (z) {
                this.mPlayer.enableAF();
            } else {
                this.mPlayer.disableAF();
            }
        } catch (RemoteException e) {
            remoteError(e);
        }
    }

    public void cancelAFSwitching() throws SemFmPlayerException {
        try {
            this.mPlayer.cancelAFSwitching();
        } catch (RemoteException e) {
            remoteError(e);
        }
    }

    public void setBand(int i) throws SemFmPlayerException {
        try {
            this.mPlayer.setBand(i);
        } catch (RemoteException e) {
            remoteError(e);
        }
    }

    public void setChannelSpacing(int i) throws SemFmPlayerException {
        try {
            this.mPlayer.setChannelSpacing(i);
        } catch (RemoteException e) {
            remoteError(e);
        }
    }

    public boolean cancelScan() throws SemFmPlayerException {
        try {
            return this.mPlayer.cancelScan();
        } catch (RemoteException e) {
            this.remoteError(e);
            return false;
        }
    }

    public boolean isScanning() throws SemFmPlayerException {
        try {
            return this.mPlayer.isScanning();
        } catch (RemoteException e) {
            this.remoteError(e);
            return false;
        }
    }

    public boolean isSeeking() throws SemFmPlayerException {
        try {
            return this.mPlayer.isSeeking();
        } catch (RemoteException e) {
            this.remoteError(e);
            return false;
        }
    }

    private void remoteError(RemoteException remoteException) throws SemFmPlayerException {
        Log.e(LOG_TAG, "RemoteException in remoteError() : " + remoteException);
        throw new SemFmPlayerNotEnabledException("Radio service is not running restart the phone.", remoteException.fillInStackTrace());
    }

    public boolean tune(long j) throws SemFmPlayerException {
        checkOnStatus();
        try {
            this.mPlayer.tune(j);
            return true;
        } catch (RemoteException e) {
            remoteError(e);
            return false;
        }
    }

    public boolean setMuteEnabled(boolean z) throws SemFmPlayerException {
        checkOnStatus();
        try {
            this.mPlayer.mute(z);
            return true;
        } catch (RemoteException e) {
            remoteError(e);
            return false;
        }
    }

    public long seekUp() throws SemFmPlayerException {
        checkOnStatus();
        try {
            checkBusy();
            return this.mPlayer.seekUp();
        } catch (RemoteException e) {
            remoteError(e);
            return -1L;
        }
    }

    public long seekDown() throws SemFmPlayerException {
        checkOnStatus();
        try {
            checkBusy();
            return this.mPlayer.seekDown();
        } catch (RemoteException e) {
            remoteError(e);
            return -1L;
        }
    }

    public void cancelSeek() throws SemFmPlayerException {
        try {
            this.mPlayer.cancelSeek();
        } catch (RemoteException e) {
            remoteError(e);
        }
    }

    public long getCurrentChannel() throws SemFmPlayerException {
        checkOnStatus();
        try {
            checkBusy();
            return this.mPlayer.getCurrentChannel();
        } catch (RemoteException e) {
            remoteError(e);
            return -1L;
        }
    }

    public void setVolume(long j) throws SemFmPlayerException {
        try {
            this.mPlayer.setVolume(j);
        } catch (RemoteException e) {
            remoteError(e);
        }
    }

    public long getVolume() throws SemFmPlayerException {
        try {
            return this.mPlayer.getVolume();
        } catch (RemoteException e) {
            remoteError(e);
            return -1L;
        }
    }

    public void setRecordMode(boolean z) throws SemFmPlayerException {
        try {
            this.mPlayer.setRecordMode(z);
        } catch (RemoteException e) {
            remoteError(e);
        }
    }

    public long getMaxVolume() throws SemFmPlayerException {
        try {
            return this.mPlayer.getMaxVolume();
        } catch (RemoteException e) {
            remoteError(e);
            return -1L;
        }
    }

    public long[] getLastScanResult() throws SemFmPlayerException {
        if (isScanning()) {
            return null;
        }
        try {
            return this.mPlayer.getLastScanResult();
        } catch (RemoteException e) {
            this.remoteError(e);
            return null;
        }
    }

    private void checkOnStatus() throws SemFmPlayerException {
        if (!isRadioEnabled()) {
            throw new SemFmPlayerNotEnabledException("Player is not ON.Call on() method to start player", new Throwable("Player is not ON. use method on() to switch on FM player"));
        }
    }

    private void checkBusy() throws SemFmPlayerException {
        int iIsBusy;
        try {
            iIsBusy = this.mPlayer.isBusy();
        } catch (RemoteException e) {
            this.remoteError(e);
            iIsBusy = 0;
        }
        if (iIsBusy == 1) {
            throw new SemFmPlayerScanningException("Player is scanning channel", new Throwable("Player is busy in scanning. Use cancelScan to stop scanning"));
        }
    }

    public boolean isRadioDataSystemEnabled() throws SemFmPlayerException {
        try {
            return this.mPlayer.isRDSEnable();
        } catch (RemoteException e) {
            this.remoteError(e);
            return false;
        }
    }

    public boolean isAlternateFrequencyEnabled() throws SemFmPlayerException {
        try {
            return this.mPlayer.isAFEnable();
        } catch (RemoteException e) {
            this.remoteError(e);
            return false;
        }
    }

    public void addListener(SemFmEventListener semFmEventListener) throws SemFmPlayerException {
        if (semFmEventListener == null) {
            return;
        }
        try {
            this.mPlayer.setListener(semFmEventListener.callback);
        } catch (RemoteException e) {
            remoteError(e);
        }
    }

    public void removeListener(SemFmEventListener semFmEventListener) throws SemFmPlayerException {
        if (semFmEventListener == null) {
            return;
        }
        try {
            this.mPlayer.removeListener(semFmEventListener.callback);
        } catch (RemoteException e) {
            remoteError(e);
        }
    }

    public void setFMIntenna(boolean z) throws SemFmPlayerException {
        checkOnStatus();
        try {
            this.mPlayer.setFMIntenna(z);
        } catch (RemoteException e) {
            remoteError(e);
        }
    }

    public void setSoftmuteEnabled(boolean z) throws SemFmPlayerException {
        try {
            this.mPlayer.setSoftmute(z);
        } catch (RemoteException e) {
            remoteError(e);
        }
    }

    public boolean isSoftmuteEnabled() throws SemFmPlayerException {
        try {
            return this.mPlayer.getSoftMuteMode();
        } catch (RemoteException e) {
            this.remoteError(e);
            return false;
        }
    }

    protected void finalize() throws Throwable {
        super.finalize();
    }

    public void setTunningParameter(String str, int i) throws SemFmPlayerException {
        checkOnStatus();
        try {
            this.mPlayer.setIntegerTunningParameter(str, i);
        } catch (RemoteException e) {
            remoteError(e);
        }
    }

    public int getTunningParameter(String str, int i) throws SemFmPlayerException {
        if (isRadioEnabled()) {
            try {
                return this.mPlayer.getIntegerTunningParameter(str, i);
            } catch (RemoteException e) {
                this.remoteError(e);
            }
        }
        return i;
    }

    public void setTunningParameter(String str, long j) throws SemFmPlayerException {
        checkOnStatus();
        try {
            this.mPlayer.setLongTunningParameter(str, j);
        } catch (RemoteException e) {
            remoteError(e);
        }
    }

    public long getTunningParameter(String str, long j) throws SemFmPlayerException {
        if (isRadioEnabled()) {
            try {
                return this.mPlayer.getLongTunningParameter(str, j);
            } catch (RemoteException e) {
                this.remoteError(e);
            }
        }
        return j;
    }

    public void setTunningParameter(String str, String str2) throws SemFmPlayerException {
        checkOnStatus();
        try {
            this.mPlayer.setStringTunningParameter(str, str2);
        } catch (RemoteException e) {
            remoteError(e);
        }
    }

    public String getTunningParameter(String str, String str2) throws SemFmPlayerException {
        if (isRadioEnabled()) {
            try {
                return this.mPlayer.getStringTunningParameter(str, str2);
            } catch (RemoteException e) {
                this.remoteError(e);
            }
        }
        return str2;
    }

    public boolean isDeviceSpeakerEnabled() throws SemFmPlayerException {
        try {
            return this.mPlayer.isDeviceSpeakerEnabled();
        } catch (RemoteException e) {
            this.remoteError(e);
            return false;
        }
    }
}
