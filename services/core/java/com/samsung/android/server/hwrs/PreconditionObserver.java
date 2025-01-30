package com.samsung.android.server.hwrs;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.SemSystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import com.samsung.android.server.hwrs.AbstractPreconditionObserver;

/* loaded from: classes2.dex */
public class PreconditionObserver extends AbstractPreconditionObserver {
    public boolean mBoundCameraShare;
    public ServiceConnection mConn;
    public UserHandle mCurrentUserHandle;
    public final Handler mHandler;
    public boolean mIsTablet;
    public boolean mIsValidState;

    public PreconditionObserver(Context context) {
        super(context);
        this.mConn = new ServiceConnection() { // from class: com.samsung.android.server.hwrs.PreconditionObserver.3
            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                Log.d("[HWRS_SYS]PreconditionObserver", "onServiceConnected - binding completed");
                PreconditionObserver.this.mBoundCameraShare = true;
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName componentName) {
                Log.d("[HWRS_SYS]PreconditionObserver", "onServiceDisconnected");
                PreconditionObserver.this.mBoundCameraShare = false;
                PreconditionObserver.this.sendMessageDelayed(2, 4, 3000L);
            }
        };
        Log.d("[HWRS_SYS]PreconditionObserver", "PreconditionObserver entered");
        this.mHandler = new Handler(Looper.getMainLooper()) { // from class: com.samsung.android.server.hwrs.PreconditionObserver.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                PreconditionObserver.this.handleMessageWhat(message.what, message.arg1);
            }
        };
        this.mIsTablet = isTablet();
    }

    public int getCurrentUserId() {
        UserHandle userHandle = this.mCurrentUserHandle;
        if (userHandle == null) {
            return -10000;
        }
        return userHandle.semGetIdentifier();
    }

    public void startUser(UserHandle userHandle) {
        Log.d("[HWRS_SYS]PreconditionObserver", "startUser entered");
        if (getCurrentUserId() != -10000) {
            Log.e("[HWRS_SYS]PreconditionObserver", "startUser - invalid request!");
            return;
        }
        Log.d("[HWRS_SYS]PreconditionObserver", "startUser : " + userHandle.semGetIdentifier());
        this.mCurrentUserHandle = userHandle;
        removeAndSendMessageDelayed(0, 0L);
    }

    public void stopUser() {
        this.mCurrentUserHandle = null;
        stop();
    }

    public final boolean isTablet() {
        String str = SemSystemProperties.get("ro.build.characteristics");
        return str != null && str.contains("tablet");
    }

    public final void startPreconditionObserver() {
        Log.d("[HWRS_SYS]PreconditionObserver", "start entered");
        start(getCurrentUserId(), new AbstractPreconditionObserver.StateChangedListener() { // from class: com.samsung.android.server.hwrs.PreconditionObserver.2
            @Override // com.samsung.android.server.hwrs.AbstractPreconditionObserver.StateChangedListener
            public void onChanged(int i, int i2) {
                boolean meetConditions = PreconditionObserver.this.meetConditions();
                if (PreconditionObserver.this.mIsValidState == meetConditions) {
                    return;
                }
                Log.d("[HWRS_SYS]PreconditionObserver", "onChanged - isValid : " + meetConditions + ", reason" + i2);
                PreconditionObserver.this.mIsValidState = meetConditions;
                if (PreconditionObserver.this.mIsValidState) {
                    PreconditionObserver.this.setValues("hwrs_service", 1);
                    if (PreconditionObserver.this.mIsTablet) {
                        return;
                    }
                    PreconditionObserver.this.removeMessage(3);
                    PreconditionObserver.this.removeAndSendMessageDelayed(2, 0L);
                    return;
                }
                PreconditionObserver.this.setValues("hwrs_service", 0);
                if (PreconditionObserver.this.mIsTablet) {
                    return;
                }
                PreconditionObserver.this.removeMessage(2);
                PreconditionObserver.this.removeAndSendMessageDelayed(3, 0L);
            }
        });
        boolean meetConditions = meetConditions();
        this.mIsValidState = meetConditions;
        if (meetConditions) {
            Log.d("[HWRS_SYS]PreconditionObserver", "mIsValidState : " + this.mIsValidState);
            removeAndSendMessageDelayed(2, 1, 0L);
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
            startPreconditionObserver();
            return;
        }
        if (i == 1) {
            stopUser();
        } else if (i == 2) {
            bindCS(i2);
        } else {
            if (i != 3) {
                return;
            }
            unbindCS();
        }
    }

    public void setValues(String str, int i) {
        Log.d("[HWRS_SYS]PreconditionObserver", "setValues ID : " + str + ", value : " + i);
        if (Settings.System.semGetIntForUser(this.mContext.getContentResolver(), str, -1, this.mUserId) != i) {
            Settings.System.semPutIntForUser(this.mContext.getContentResolver(), str, i, this.mUserId);
            return;
        }
        Log.d("[HWRS_SYS]PreconditionObserver", "setValues ID : " + str + ", same value");
    }

    public final void bindCS(int i) {
        if (!this.mIsValidState) {
            Log.d("[HWRS_SYS]PreconditionObserver", "bindCS - invalid state");
            return;
        }
        if (!this.mBoundCameraShare) {
            Log.d("[HWRS_SYS]PreconditionObserver", "bind Camera Share");
            Intent intent = new Intent("com.samsung.android.hwresourceshare.CAMERA_SHARE_REQUEST");
            intent.setPackage("com.samsung.android.hwresourceshare");
            this.mContext.bindService(intent, this.mConn, 1);
            return;
        }
        Log.d("[HWRS_SYS]PreconditionObserver", "already camera share was bound");
    }

    public final void unbindCS() {
        if (this.mHandler.hasMessages(2)) {
            removeMessage(2);
        }
        if (this.mBoundCameraShare) {
            Log.d("[HWRS_SYS]PreconditionObserver", "unbindCS");
            this.mContext.unbindService(this.mConn);
            this.mBoundCameraShare = false;
            return;
        }
        Log.d("[HWRS_SYS]PreconditionObserver", "already camera share was unbound");
    }
}
