package com.android.server.hdmi;

import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.hardware.hdmi.IHdmiControlCallback;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Pair;
import android.util.Slog;

import com.android.server.HeapdumpWatcher$$ExternalSyntheticOutline0;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

public abstract class HdmiCecFeatureAction {
    public ActionTimer mActionTimer;
    public final List mCallbacks;
    public ArrayList mOnFinishedCallbacks;
    public final HdmiControlService mService;
    public final HdmiCecLocalDevice mSource;
    public int mState;

    public interface ActionTimer {}

    public final class ActionTimerHandler extends Handler implements ActionTimer {
        public ActionTimerHandler(Looper looper) {
            super(looper);
        }

        public final void clearTimerMessage() {
            removeMessages(100);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            if (message.what != 100) {
                HeapdumpWatcher$$ExternalSyntheticOutline0.m(
                        new StringBuilder("Unsupported message:"),
                        message.what,
                        "HdmiCecFeatureAction");
            } else {
                HdmiCecFeatureAction.this.handleTimerEvent(message.arg1);
            }
        }
    }

    public HdmiCecFeatureAction(HdmiCecLocalDevice hdmiCecLocalDevice) {
        this(hdmiCecLocalDevice, new ArrayList());
    }

    public HdmiCecFeatureAction(
            HdmiCecLocalDevice hdmiCecLocalDevice, IHdmiControlCallback iHdmiControlCallback) {
        this(hdmiCecLocalDevice, Arrays.asList(iHdmiControlCallback));
    }

    public HdmiCecFeatureAction(HdmiCecLocalDevice hdmiCecLocalDevice, List list) {
        this.mState = 0;
        this.mCallbacks = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ((ArrayList) this.mCallbacks).add((IHdmiControlCallback) it.next());
        }
        this.mSource = hdmiCecLocalDevice;
        HdmiControlService hdmiControlService = hdmiCecLocalDevice.mService;
        this.mService = hdmiControlService;
        this.mActionTimer = new ActionTimerHandler(hdmiControlService.mHandler.getLooper());
    }

    public final void addTimer(int i, int i2) {
        ActionTimerHandler actionTimerHandler = (ActionTimerHandler) this.mActionTimer;
        actionTimerHandler.sendMessageDelayed(actionTimerHandler.obtainMessage(100, i, 0), i2);
    }

    public void clear() {
        this.mState = 0;
        ((ActionTimerHandler) this.mActionTimer).clearTimerMessage();
    }

    public final void finish(boolean z) {
        clear();
        if (z) {
            HdmiCecLocalDevice hdmiCecLocalDevice = this.mSource;
            hdmiCecLocalDevice.assertRunOnServiceThread();
            finish(false);
            hdmiCecLocalDevice.mActions.remove(this);
            hdmiCecLocalDevice.checkIfPendingActionsCleared();
        }
        ArrayList arrayList = this.mOnFinishedCallbacks;
        if (arrayList != null) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                Pair pair = (Pair) it.next();
                if (((HdmiCecFeatureAction) pair.first).mState != 0) {
                    ((Runnable) pair.second).run();
                }
            }
            this.mOnFinishedCallbacks = null;
        }
    }

    public final void finishWithCallback(int i) {
        try {
            Iterator it = ((ArrayList) this.mCallbacks).iterator();
            while (it.hasNext()) {
                IHdmiControlCallback iHdmiControlCallback = (IHdmiControlCallback) it.next();
                if (iHdmiControlCallback != null) {
                    iHdmiControlCallback.onComplete(i);
                }
            }
        } catch (RemoteException e) {
            Slog.e("HdmiCecFeatureAction", "Callback failed:" + e);
        }
        finish(true);
    }

    public final int getSourceAddress() {
        return this.mSource.getDeviceInfo().getLogicalAddress();
    }

    public abstract void handleTimerEvent(int i);

    public final void pollDevices(
            final HdmiControlService.DevicePollingCallback devicePollingCallback,
            int i,
            final long j) {
        final int sourceAddress = getSourceAddress();
        HdmiControlService hdmiControlService = this.mService;
        hdmiControlService.assertRunOnServiceThread();
        final HdmiCecController hdmiCecController = hdmiControlService.mCecController;
        int i2 = i & 3;
        if (i2 == 0) {
            throw new IllegalArgumentException(
                    VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Invalid poll strategy:"));
        }
        int i3 = 196608 & i;
        if (i3 == 0) {
            throw new IllegalArgumentException(
                    VibrationParam$1$$ExternalSyntheticOutline0.m(
                            i, "Invalid iteration strategy:"));
        }
        hdmiCecController.assertRunOnServiceThread();
        Predicate predicate =
                i2 != 2
                        ? hdmiCecController.mRemoteDeviceAddressPredicate
                        : hdmiCecController.mSystemAudioAddressPredicate;
        final ArrayList arrayList = new ArrayList();
        if (i3 != 65536) {
            for (int i4 = 14; i4 >= 0; i4--) {
                if (predicate.test(Integer.valueOf(i4))) {
                    arrayList.add(Integer.valueOf(i4));
                }
            }
        } else {
            for (int i5 = 0; i5 <= 14; i5++) {
                if (predicate.test(Integer.valueOf(i5))) {
                    arrayList.add(Integer.valueOf(i5));
                }
            }
        }
        final ArrayList arrayList2 = new ArrayList();
        hdmiCecController.mControlHandler.postDelayed(
                new Runnable() { // from class:
                                 // com.android.server.hdmi.HdmiCecController$$ExternalSyntheticLambda0
                    public final /* synthetic */ int f$3 = 1;

                    @Override // java.lang.Runnable
                    public final void run() {
                        HdmiCecController.this.runDevicePolling(
                                sourceAddress,
                                arrayList,
                                this.f$3,
                                devicePollingCallback,
                                arrayList2,
                                j,
                                false);
                    }
                },
                hdmiCecController.mPollDevicesDelay);
    }

    public abstract boolean processCommand(HdmiCecMessage hdmiCecMessage);

    public final void sendCommand(HdmiCecMessage hdmiCecMessage) {
        this.mService.sendCecCommand(hdmiCecMessage, null);
    }

    public final void sendCommand(
            HdmiCecMessage hdmiCecMessage,
            HdmiControlService.SendMessageCallback sendMessageCallback) {
        this.mService.sendCecCommand(hdmiCecMessage, sendMessageCallback);
    }

    public void setActionTimer(ActionTimer actionTimer) {
        this.mActionTimer = actionTimer;
    }

    public abstract void start();
}
