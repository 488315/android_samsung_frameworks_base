package android.hardware.hdmi;

import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;

/* loaded from: classes2.dex */
public final class HdmiAudioSystemClient extends HdmiClient {
    private static final int REPORT_AUDIO_STATUS_INTERVAL_MS = 500;
    private static final String TAG = "HdmiAudioSystemClient";
    private boolean mCanSendAudioStatus;
    private final Handler mHandler;
    private boolean mLastIsMute;
    private int mLastMaxVolume;
    private int mLastVolume;
    private boolean mPendingReportAudioStatus;

    public interface SetSystemAudioModeCallback {
        void onComplete(int i);
    }

    @Override // android.hardware.hdmi.HdmiClient
    public int getDeviceType() {
        return 5;
    }

    public void setSystemAudioMode(boolean z, SetSystemAudioModeCallback setSystemAudioModeCallback) {
    }

    public HdmiAudioSystemClient(IHdmiControlService iHdmiControlService) {
        this(iHdmiControlService, null);
    }

    public HdmiAudioSystemClient(IHdmiControlService iHdmiControlService, Handler handler) {
        super(iHdmiControlService);
        this.mCanSendAudioStatus = true;
        this.mHandler = handler == null ? new Handler(Looper.getMainLooper()) : handler;
    }

    public void sendReportAudioStatusCecCommand(boolean z, int i, int i2, boolean z2) {
        try {
            if (z) {
                this.mService.reportAudioStatus(getDeviceType(), i, i2, z2);
                return;
            }
            this.mLastVolume = i;
            this.mLastMaxVolume = i2;
            this.mLastIsMute = z2;
            if (this.mCanSendAudioStatus) {
                this.mService.reportAudioStatus(getDeviceType(), i, i2, z2);
                this.mCanSendAudioStatus = false;
                this.mHandler.postDelayed(new Runnable() { // from class: android.hardware.hdmi.HdmiAudioSystemClient.1
                    /* JADX WARN: Multi-variable type inference failed */
                    /* JADX WARN: Type inference failed for: r7v4, types: [android.hardware.hdmi.HdmiAudioSystemClient] */
                    @Override // java.lang.Runnable
                    public void run() {
                        if (HdmiAudioSystemClient.this.mPendingReportAudioStatus) {
                            try {
                                try {
                                    HdmiAudioSystemClient.this.mService.reportAudioStatus(HdmiAudioSystemClient.this.getDeviceType(), HdmiAudioSystemClient.this.mLastVolume, HdmiAudioSystemClient.this.mLastMaxVolume, HdmiAudioSystemClient.this.mLastIsMute);
                                    HdmiAudioSystemClient.this.mHandler.postDelayed(this, 500L);
                                } catch (RemoteException unused) {
                                    HdmiAudioSystemClient.this.mCanSendAudioStatus = true;
                                }
                                return;
                            } finally {
                                HdmiAudioSystemClient.this.mPendingReportAudioStatus = false;
                            }
                        }
                        HdmiAudioSystemClient.this.mCanSendAudioStatus = true;
                    }
                }, 500L);
                return;
            }
            this.mPendingReportAudioStatus = true;
        } catch (RemoteException unused) {
        }
    }

    public void setSystemAudioModeOnForAudioOnlySource() {
        try {
            this.mService.setSystemAudioModeOnForAudioOnlySource();
        } catch (RemoteException unused) {
            Log.d(TAG, "Failed to set System Audio Mode on for Audio Only source");
        }
    }
}
