package com.android.server.wm;

import android.app.IAssistDataReceiver;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.android.server.am.AssistDataRequester;

public final class AssistDataReceiverProxy
        implements AssistDataRequester.AssistDataRequesterCallbacks, IBinder.DeathRecipient {
    public String mCallerPackage;
    public IAssistDataReceiver mReceiver;

    @Override // android.os.IBinder.DeathRecipient
    public final void binderDied() {
        IAssistDataReceiver iAssistDataReceiver = this.mReceiver;
        if (iAssistDataReceiver != null) {
            iAssistDataReceiver.asBinder().unlinkToDeath(this, 0);
        }
        this.mReceiver = null;
    }

    @Override // com.android.server.am.AssistDataRequester.AssistDataRequesterCallbacks
    public final boolean canHandleReceivedAssistDataLocked() {
        return true;
    }

    @Override // com.android.server.am.AssistDataRequester.AssistDataRequesterCallbacks
    public final void onAssistDataReceivedLocked(int i, int i2, Bundle bundle) {
        IAssistDataReceiver iAssistDataReceiver = this.mReceiver;
        if (iAssistDataReceiver != null) {
            try {
                iAssistDataReceiver.onHandleAssistData(bundle);
            } catch (RemoteException e) {
                Log.w(
                        "ActivityTaskManager",
                        "Failed to proxy assist data to receiver in package=" + this.mCallerPackage,
                        e);
            }
        }
    }

    @Override // com.android.server.am.AssistDataRequester.AssistDataRequesterCallbacks
    public final void onAssistRequestCompleted() {
        IAssistDataReceiver iAssistDataReceiver = this.mReceiver;
        if (iAssistDataReceiver != null) {
            iAssistDataReceiver.asBinder().unlinkToDeath(this, 0);
        }
        this.mReceiver = null;
    }

    @Override // com.android.server.am.AssistDataRequester.AssistDataRequesterCallbacks
    public final void onAssistScreenshotReceivedLocked(Bitmap bitmap) {
        IAssistDataReceiver iAssistDataReceiver = this.mReceiver;
        if (iAssistDataReceiver != null) {
            try {
                iAssistDataReceiver.onHandleAssistScreenshot(bitmap);
            } catch (RemoteException e) {
                Log.w(
                        "ActivityTaskManager",
                        "Failed to proxy assist screenshot to receiver in package="
                                + this.mCallerPackage,
                        e);
            }
        }
    }
}
