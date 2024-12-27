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

    @Override // android.app.Service, android.content.ContextWrapper
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
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

    public void onSystemReady() {
    }

    public void onCoverAttached(CoverState coverState) {
    }

    public void onCoverDetached() {
    }

    public void onCoverStateUpdated(CoverState coverState) {
    }

    public Object getCoverHost() {
        return null;
    }

    public int onCoverAppCovered(boolean covered) {
        return 0;
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
        public void onUpdateCoverState(CoverState state) throws RemoteException {
            synchronized (SemCoverService.this.mLock) {
                SemCoverService.this.mHandler.obtainMessage(2, state).sendToTarget();
            }
        }

        @Override // com.samsung.android.cover.ICoverService
        public int onCoverAppCovered(boolean covered) throws RemoteException {
            synchronized (SemCoverService.this.mLock) {
                if (SemCoverService.this.getCoverHost() == null) {
                    return 0;
                }
                SemCoverService.this.mHandler.obtainMessage(3, covered ? 1 : 0, 0).sendToTarget();
                return covered ? 16 : 32;
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
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    SemCoverService.this.onSystemReady();
                    break;
                case 2:
                    if (msg.obj != null && (msg.obj instanceof CoverState)) {
                        CoverState cs = (CoverState) msg.obj;
                        if (cs.getAttachState() && !SemCoverService.this.mAttach) {
                            SemCoverService.this.onCoverAttached(cs);
                            SemCoverService.this.mAttach = true;
                        }
                        SemCoverService.this.onCoverStateUpdated(cs);
                        break;
                    }
                    break;
                case 3:
                    SemCoverService.this.onCoverAppCovered(msg.arg1 == 1);
                    break;
            }
        }
    }
}
