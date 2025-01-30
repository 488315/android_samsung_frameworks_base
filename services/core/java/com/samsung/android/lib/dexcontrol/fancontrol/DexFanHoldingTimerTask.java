package com.samsung.android.lib.dexcontrol.fancontrol;

import com.samsung.android.lib.dexcontrol.utils.SLog;
import java.util.TimerTask;

/* loaded from: classes2.dex */
public class DexFanHoldingTimerTask extends TimerTask {
    public static final String TAG = DexFanHoldingTimerTask.class.getSimpleName();
    public IDexFanModeControl mIDexFanModeControl;
    public String mSender;
    public boolean mSetUpdate = false;

    public DexFanHoldingTimerTask(IDexFanModeControl iDexFanModeControl, String str) {
        this.mIDexFanModeControl = iDexFanModeControl;
        this.mSender = str;
    }

    public String getSender() {
        return this.mSender;
    }

    public void setUpdate(boolean z) {
        SLog.m128i(TAG, "setUpdate - " + z);
        this.mSetUpdate = z;
    }

    @Override // java.util.TimerTask
    public boolean cancel() {
        SLog.m128i(TAG, "DexFanHoldingTimerTask - " + this.mSender + " is canceled");
        destroy();
        return super.cancel();
    }

    @Override // java.util.TimerTask, java.lang.Runnable
    public void run() {
        SLog.m128i(TAG, "DexFanHoldingTimerTask - " + this.mSender + " is finished");
        destroy();
    }

    public final void destroy() {
        IDexFanModeControl iDexFanModeControl = this.mIDexFanModeControl;
        if (iDexFanModeControl != null && !this.mSetUpdate) {
            iDexFanModeControl.onFinishedFanHoldingTimerTask(this);
        }
        this.mSetUpdate = false;
    }
}
