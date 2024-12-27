package com.android.server.enterprise.plm;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.android.server.enterprise.plm.common.PlmMessage;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class LockDetectionTracker {
    public static volatile LockDetectionTracker sInstance;
    public final List mLockDetectionEventCallbacks;

    public LockDetectionTracker() {
        new Handler(
                Looper
                        .getMainLooper()) { // from class:
                                            // com.android.server.enterprise.plm.LockDetectionTracker.1
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                boolean z = true;
                if (message.what != 1) {
                    super.handleMessage(message);
                    return;
                }
                int i = message.arg1;
                int i2 = message.arg2;
                LockDetectionTracker lockDetectionTracker = LockDetectionTracker.this;
                lockDetectionTracker.getClass();
                String localTime = LocalTime.now().toString();
                if (i == 0) {
                    Log.d("LockDetection:", "verify credential success");
                } else {
                    if (i != -1) {
                        return;
                    }
                    Log.d("LockDetection:", "verify credential failure");
                    z = false;
                }
                Iterator it =
                        ((ArrayList) lockDetectionTracker.mLockDetectionEventCallbacks).iterator();
                while (it.hasNext()) {
                    SystemStateTracker.LockStateListener lockStateListener =
                            (SystemStateTracker.LockStateListener) it.next();
                    Boolean valueOf = Boolean.valueOf(z);
                    Integer valueOf2 = Integer.valueOf(i2);
                    SystemStateTracker systemStateTracker = SystemStateTracker.this;
                    PlmMessage plmMessage = new PlmMessage();
                    plmMessage.obj1 = "com.samsung.android.knox.intent.action.LOCK_STATE_CHANGE";
                    plmMessage.obj2 = valueOf;
                    plmMessage.obj3 = valueOf2;
                    plmMessage.obj4 = localTime;
                    Message obtain = Message.obtain(systemStateTracker);
                    obtain.what = 7;
                    obtain.obj = plmMessage;
                    systemStateTracker.sendMessage(obtain);
                }
            }
        };
        this.mLockDetectionEventCallbacks = new ArrayList();
    }
}
