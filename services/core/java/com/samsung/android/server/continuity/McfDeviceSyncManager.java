package com.samsung.android.server.continuity;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.UserHandle;
import android.util.Log;
import com.samsung.android.mcfds.lib.AbstractDeviceSyncManager;
import com.samsung.android.mcfds.lib.DeviceSyncManager;
import com.samsung.android.server.continuity.AbstractPreconditionObserver;

/* loaded from: classes2.dex */
public class McfDeviceSyncManager {
    static final int MSG_AVAILABLE_MCF = 5;
    static final int MSG_BIND_MCF = 2;
    static final int MSG_REPLACED_PKG = 4;
    static final int MSG_START_USER = 0;
    static final int MSG_STOP_USER = 1;
    static final int MSG_UNBIND_MCF = 3;
    public UserHandle mCurrentUserHandle;
    public final DeviceSyncManager mDsManager;
    public final Handler mHandler = new Handler(Looper.getMainLooper()) { // from class: com.samsung.android.server.continuity.McfDeviceSyncManager.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            McfDeviceSyncManager.this.handleMessageWhat(message.what, message.arg1);
        }
    };
    public boolean mIsValidState;
    public final PreconditionObserver mPreconditionObserver;

    public McfDeviceSyncManager(PreconditionObserver preconditionObserver, DeviceSyncManager deviceSyncManager) {
        this.mPreconditionObserver = preconditionObserver;
        this.mDsManager = deviceSyncManager;
    }

    public int getCurrentUserId() {
        UserHandle userHandle = this.mCurrentUserHandle;
        if (userHandle == null) {
            return -10000;
        }
        return userHandle.semGetIdentifier();
    }

    public void startUser(UserHandle userHandle) {
        if (getCurrentUserId() != -10000) {
            Log.e("[MCF_DS_SYS]_McfDsManager", "startUser - invalid request!");
            return;
        }
        Log.d("[MCF_DS_SYS]_McfDsManager", "startUser : " + userHandle.semGetIdentifier());
        this.mCurrentUserHandle = userHandle;
        removeAndSendMessageDelayed(0, 0L);
    }

    public void stopUser() {
        this.mCurrentUserHandle = null;
        this.mPreconditionObserver.stop();
        unbindMcf();
    }

    public final void start() {
        int currentUserId = getCurrentUserId();
        if (currentUserId == -10000) {
            Log.w("[MCF_DS_SYS]_McfDsManager", "start : userId is USER_NULL");
            return;
        }
        this.mPreconditionObserver.start(currentUserId, new AbstractPreconditionObserver.StateChangedListener() { // from class: com.samsung.android.server.continuity.McfDeviceSyncManager.2
            @Override // com.samsung.android.server.continuity.AbstractPreconditionObserver.StateChangedListener
            public void onChanged(int i, int i2) {
                boolean meetConditions = McfDeviceSyncManager.this.mPreconditionObserver.meetConditions();
                if (McfDeviceSyncManager.this.mIsValidState == meetConditions) {
                    return;
                }
                Log.d("[MCF_DS_SYS]_McfDsManager", "onChanged - " + McfDeviceSyncManager.this.mIsValidState + ", " + meetConditions + ", " + i2);
                McfDeviceSyncManager.this.mIsValidState = meetConditions;
                if (McfDeviceSyncManager.this.mIsValidState) {
                    McfDeviceSyncManager.this.removeMessage(3);
                    McfDeviceSyncManager.this.sendMessageDelayed(2, i2, 0L);
                    return;
                }
                McfDeviceSyncManager.this.removeMessage(2);
                if ((i & 3840) > 0) {
                    McfDeviceSyncManager.this.removeAndSendMessageDelayed(3, 3000L);
                } else {
                    McfDeviceSyncManager.this.unbindMcf();
                }
            }

            @Override // com.samsung.android.server.continuity.AbstractPreconditionObserver.StateChangedListener
            public void onPackageReplaced(boolean z) {
                boolean meetConditions = McfDeviceSyncManager.this.mPreconditionObserver.meetConditions();
                Log.d("[MCF_DS_SYS]_McfDsManager", "onPackageReplaced - " + McfDeviceSyncManager.this.mIsValidState + ", " + meetConditions);
                McfDeviceSyncManager.this.mIsValidState = meetConditions;
                if (McfDeviceSyncManager.this.mIsValidState) {
                    McfDeviceSyncManager.this.removeMessage(4);
                    if (z) {
                        McfDeviceSyncManager.this.removeAndSendMessageDelayed(4, 1000L);
                    } else {
                        McfDeviceSyncManager.this.removeAndSendMessageDelayed(4, 500L);
                    }
                }
            }

            @Override // com.samsung.android.server.continuity.AbstractPreconditionObserver.StateChangedListener
            public void onPackageRestarted() {
                boolean meetConditions = McfDeviceSyncManager.this.mPreconditionObserver.meetConditions();
                Log.d("[MCF_DS_SYS]_McfDsManager", "onPackageRestarted - " + McfDeviceSyncManager.this.mIsValidState + ", " + meetConditions);
                McfDeviceSyncManager.this.mIsValidState = meetConditions;
                if (McfDeviceSyncManager.this.mIsValidState) {
                    McfDeviceSyncManager.this.removeMessage(3);
                    McfDeviceSyncManager.this.sendMessageDelayed(2, 6, 1000L);
                }
            }
        });
        boolean meetConditions = this.mPreconditionObserver.meetConditions();
        this.mIsValidState = meetConditions;
        if (meetConditions) {
            removeAndSendMessageDelayed(2, 1, 0L);
        }
    }

    public int getNearbyDeviceCount(int i) {
        Log.d("[MCF_DS_SYS]_McfDsManager", "getNearbyDeviceCount");
        int i2 = 1;
        if (i != 1) {
            i2 = 2;
            if (i != 2) {
                return 0;
            }
        }
        return Math.max(this.mDsManager.getNearbyDeviceCount(i2), 0);
    }

    public final void bindMcf(int i) {
        if (!this.mIsValidState) {
            Log.d("[MCF_DS_SYS]_McfDsManager", "bindMcf - invalid state");
            return;
        }
        int serviceState = this.mDsManager.getServiceState();
        if (serviceState != 0 && (serviceState != 1 || i != 6)) {
            Log.d("[MCF_DS_SYS]_McfDsManager", "bindMcf - invalid state " + serviceState + ", reason " + i);
            if (serviceState == 3) {
                initMcfDeviceSyncMainController(i);
                return;
            }
            return;
        }
        if (this.mCurrentUserHandle == null) {
            Log.w("[MCF_DS_SYS]_McfDsManager", "bindMcf - null current user handle");
            return;
        }
        if (!this.mDsManager.connectServiceAsUser(new AbstractDeviceSyncManager.StateListener(i) { // from class: com.samsung.android.server.continuity.McfDeviceSyncManager.3
            public int mBindReason;
            public final /* synthetic */ int val$bindReason;

            {
                this.val$bindReason = i;
                this.mBindReason = i;
            }

            @Override // com.samsung.android.mcfds.lib.AbstractDeviceSyncManager.StateListener
            public void onStateChanged(int i2) {
                if (i2 == 1) {
                    Log.e("[MCF_DS_SYS]_McfDsManager", "bindMcf : SERVICE_STATE_DISCONNECTED");
                } else if (i2 == 3) {
                    Log.d("[MCF_DS_SYS]_McfDsManager", "bindMcf : SERVICE_STATE_CONNECTED (bindReason: " + this.mBindReason + ")");
                    McfDeviceSyncManager.this.initMcfDeviceSyncMainController(this.mBindReason);
                } else if (i2 == 4) {
                    Log.i("[MCF_DS_SYS]_McfDsManager", "bindMcf : SERVICE_STATE_UNAVAILABLE");
                } else if (i2 == 5) {
                    Log.i("[MCF_DS_SYS]_McfDsManager", "bindMcf : SERVICE_STATE_AVAILABLE");
                    if (AbstractPreconditionObserver.isSupported(8)) {
                        McfDeviceSyncManager.this.mHandler.sendEmptyMessage(5);
                    }
                }
                this.mBindReason = 0;
            }
        }, this.mCurrentUserHandle)) {
            Log.w("[MCF_DS_SYS]_McfDsManager", "bindMcf - failed with reason: " + i);
            removeAndSendMessageDelayed(2, i, 3000L);
            return;
        }
        Log.i("[MCF_DS_SYS]_McfDsManager", "bindMcf - success with reason: " + i);
    }

    public final void initMcfDeviceSyncMainController(int i) {
        if (this.mDsManager.initMcfDeviceSyncMainController(i, i == 4 ? this.mPreconditionObserver.getAutoSwitchDeviceAddress() : null) == 3) {
            this.mPreconditionObserver.setAutoSwitchOff();
        }
    }

    public final void unbindMcf() {
        if (this.mHandler.hasMessages(2)) {
            Log.d("[MCF_DS_SYS]_McfDsManager", "unbindMcf - remove bind message");
            removeMessage(2);
        }
        int serviceState = this.mDsManager.getServiceState();
        if (serviceState == 0) {
            Log.w("[MCF_DS_SYS]_McfDsManager", "unbindMcf - invalid state " + serviceState);
            return;
        }
        Log.i("[MCF_DS_SYS]_McfDsManager", "unbindMcf");
        this.mDsManager.disconnectService();
    }

    public final void replacedPackage() {
        if (this.mHandler.hasMessages(3)) {
            Log.i("[MCF_DS_SYS]_McfDsManager", "replacedPackage - remove unbind message");
            removeMessage(3);
        }
        if (this.mHandler.hasMessages(2)) {
            Log.i("[MCF_DS_SYS]_McfDsManager", "replacedPackage - has bind message");
        } else {
            Log.i("[MCF_DS_SYS]_McfDsManager", "replacedPackage");
            removeAndSendMessageDelayed(2, 5, 0L);
        }
    }

    public final void removeMessage(int i) {
        if (this.mHandler.hasMessages(i)) {
            this.mHandler.removeMessages(i);
        }
    }

    public final void sendMessageDelayed(int i, int i2, long j) {
        Message obtain = Message.obtain();
        obtain.what = i;
        obtain.arg1 = i2;
        this.mHandler.sendMessageDelayed(obtain, j);
    }

    public void removeAndSendMessageDelayed(int i, int i2, long j) {
        removeMessage(i);
        sendMessageDelayed(i, i2, j);
    }

    public void removeAndSendMessageDelayed(int i, long j) {
        removeMessage(i);
        this.mHandler.sendEmptyMessageDelayed(i, j);
    }

    public final void handleMessageWhat(int i, int i2) {
        if (i == 0) {
            start();
            return;
        }
        if (i == 1) {
            stopUser();
            return;
        }
        if (i == 2) {
            bindMcf(i2);
        } else if (i == 3) {
            unbindMcf();
        } else {
            if (i != 4) {
                return;
            }
            replacedPackage();
        }
    }

    public Handler getHandler() {
        return this.mHandler;
    }
}
