package com.samsung.android.cover;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import com.samsung.android.cover.ICoverService;

/* loaded from: classes6.dex */
public class SemCoverService extends Service {
    private static final String TAG = "SemCoverService";
    private Handler mHandler;
    private CoverServiceWrapper mWrapper = null;
    private boolean mAttach = false;
    private final Object mLock = new Object();

    public Object getCoverHost() {
        return null;
    }

    public int onCoverAppCovered(boolean z) {
        return 0;
    }

    public void onCoverAttached(CoverState coverState) {
    }

    public void onCoverDetached() {
    }

    public void onCoverStateUpdated(CoverState coverState) {
    }

    public void onSystemReady() {
    }

    @Override // android.app.Service, android.content.ContextWrapper
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        this.mHandler = new MyHandler(getMainLooper());
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        if (this.mWrapper == null) {
            this.mWrapper = new CoverServiceWrapper();
        }
        return this.mWrapper;
    }

    @Override // android.app.Service
    public boolean onUnbind(Intent intent) {
        if (this.mAttach) {
            onCoverDetached();
            this.mAttach = false;
        }
        return super.onUnbind(intent);
    }

    private class CoverServiceWrapper extends ICoverService.Stub {
        private CoverServiceWrapper() {
        }

        @Override // com.samsung.android.cover.ICoverService
        public void onSystemReady() throws RemoteException {
            synchronized (SemCoverService.this.mLock) {
                SemCoverService.this.mHandler.sendEmptyMessage(1);
            }
        }

        @Override // com.samsung.android.cover.ICoverService
        public void onUpdateCoverState(CoverState coverState) throws RemoteException {
            synchronized (SemCoverService.this.mLock) {
                SemCoverService.this.mHandler.obtainMessage(2, coverState).sendToTarget();
            }
        }

        @Override // com.samsung.android.cover.ICoverService
        public int onCoverAppCovered(boolean z) throws RemoteException {
            synchronized (SemCoverService.this.mLock) {
                if (SemCoverService.this.getCoverHost() == null) {
                    return 0;
                }
                SemCoverService.this.mHandler.obtainMessage(3, z ? 1 : 0, 0).sendToTarget();
                return z ? 16 : 32;
            }
        }
    }

    private final class MyHandler extends Handler {
        static final int MSG_COVER_APP_COVERED = 3;
        static final int MSG_SYSTEM_READY = 1;
        static final int MSG_UPDATE_COVER_STATE = 2;

        public MyHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                SemCoverService.this.onSystemReady();
                return;
            }
            if (i != 2) {
                if (i != 3) {
                    return;
                }
                SemCoverService.this.onCoverAppCovered(message.arg1 == 1);
            } else {
                if (message.obj == null || !(message.obj instanceof CoverState)) {
                    return;
                }
                CoverState coverState = (CoverState) message.obj;
                if (coverState.getAttachState() && !SemCoverService.this.mAttach) {
                    SemCoverService.this.onCoverAttached(coverState);
                    SemCoverService.this.mAttach = true;
                }
                SemCoverService.this.onCoverStateUpdated(coverState);
            }
        }
    }
}
