package com.samsung.android.jdsms;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;
import com.samsung.android.dsms.aidl.IDsmsInfoService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* loaded from: classes6.dex */
public final class DsmsInfoCache {
    private static final String SUBTAG = "DsmsInfoCache";
    private static final long TIMEOUT = TimeUnit.MILLISECONDS.convert(2, TimeUnit.SECONDS);
    private static DsmsInfoCache sInstance;
    private Context mContext;
    private boolean mIsCommercializedDevice;
    private boolean mIsCommercializedDeviceCached;

    public static synchronized DsmsInfoCache getInstance() {
        if (sInstance == null) {
            sInstance = new DsmsInfoCache();
        }
        return sInstance;
    }

    private DsmsInfoCache() {
    }

    public void setContext(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context is null");
        }
        this.mContext = context;
    }

    public boolean isCommercializedDeviceCached() {
        return this.mIsCommercializedDeviceCached;
    }

    public void clearCommercializedDeviceCache() {
        this.mIsCommercializedDeviceCached = false;
    }

    public boolean isCommercializedDevice() {
        if (this.mIsCommercializedDeviceCached) {
            return this.mIsCommercializedDevice;
        }
        updateCommercializedDeviceCache();
        return this.mIsCommercializedDevice;
    }

    public void updateCommercializedDeviceCache() {
        DsmsInfoServiceClient dsmsInfoServiceClient = new DsmsInfoServiceClient();
        try {
            try {
                dsmsInfoServiceClient.bind();
                if (dsmsInfoServiceClient.isBound()) {
                    dsmsInfoServiceClient.waitConnection(TIMEOUT);
                    if (dsmsInfoServiceClient.isConnected()) {
                        this.mIsCommercializedDevice = dsmsInfoServiceClient.isCommercializedDevice();
                        this.mIsCommercializedDeviceCached = true;
                        DsmsLog.d(SUBTAG, "Updated commercialized device cache");
                    }
                }
                if (dsmsInfoServiceClient.isBound()) {
                    dsmsInfoServiceClient.unbind();
                }
            } catch (RemoteException | IllegalStateException | SecurityException | TimeoutException e) {
                DsmsLog.e(SUBTAG, e.getMessage());
                if (dsmsInfoServiceClient.isBound()) {
                    dsmsInfoServiceClient.unbind();
                }
            }
        } catch (Throwable th) {
            if (dsmsInfoServiceClient.isBound()) {
                dsmsInfoServiceClient.unbind();
            }
            throw th;
        }
    }

    private final class DsmsInfoServiceClient {
        private static final String ACTION_INFO = "com.samsung.android.dsms.action.INFO";
        private static final String DSMS_PACKAGE = "com.samsung.android.dsms";
        private static final String SUBTAG = "DsmsInfoServiceClient";
        private final ServiceConnection mConnection;
        private IDsmsInfoService mIDsmsInfoService;
        private boolean mIsBound;
        private Object mLock;

        private DsmsInfoServiceClient() {
            this.mLock = new Object();
            this.mIsBound = false;
            this.mIDsmsInfoService = null;
            this.mConnection = new ServiceConnection() { // from class: com.samsung.android.jdsms.DsmsInfoCache.DsmsInfoServiceClient.1
                @Override // android.content.ServiceConnection
                public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                    synchronized (DsmsInfoServiceClient.this.mLock) {
                        DsmsInfoServiceClient.this.mIDsmsInfoService = IDsmsInfoService.Stub.asInterface(iBinder);
                        DsmsInfoServiceClient.this.mLock.notifyAll();
                    }
                }

                @Override // android.content.ServiceConnection
                public void onServiceDisconnected(ComponentName componentName) {
                    synchronized (DsmsInfoServiceClient.this.mLock) {
                        DsmsInfoServiceClient.this.mIDsmsInfoService = null;
                    }
                }
            };
        }

        public boolean isBound() {
            return this.mIsBound;
        }

        public boolean isConnected() {
            boolean z;
            synchronized (this.mLock) {
                z = this.mIDsmsInfoService != null;
            }
            return z;
        }

        public boolean bind() {
            if (!this.mIsBound) {
                DsmsLog.d(SUBTAG, "Binding to service");
                Intent intent = new Intent();
                intent.setPackage(DSMS_PACKAGE);
                intent.setAction(ACTION_INFO);
                boolean zBindServiceAsUser = DsmsInfoCache.this.mContext.bindServiceAsUser(intent, this.mConnection, 1, UserHandle.SYSTEM);
                this.mIsBound = zBindServiceAsUser;
                if (zBindServiceAsUser) {
                    DsmsLog.d(SUBTAG, "Service is bound");
                } else {
                    DsmsLog.e(SUBTAG, "Could not bind to service");
                }
            }
            return this.mIsBound;
        }

        public void waitConnection(long j) throws TimeoutException {
            DsmsLog.d(SUBTAG, "Wait service connection");
            if (j < 0) {
                throw new IllegalArgumentException("Timeout is invalid");
            }
            long jCurrentTimeMillis = System.currentTimeMillis() + j;
            while (true) {
                long jCurrentTimeMillis2 = jCurrentTimeMillis - System.currentTimeMillis();
                if (jCurrentTimeMillis2 <= 0) {
                    throw new TimeoutException("Time waiting connection is over");
                }
                synchronized (this.mLock) {
                    if (this.mIDsmsInfoService != null) {
                        DsmsLog.d(SUBTAG, "Service is connected");
                        return;
                    } else {
                        try {
                            this.mLock.wait(jCurrentTimeMillis2);
                        } catch (InterruptedException unused) {
                            DsmsLog.d(SUBTAG, "Interrupted while waiting remaining time");
                        }
                    }
                }
            }
        }

        public void unbind() {
            if (this.mIsBound) {
                DsmsInfoCache.this.mContext.unbindService(this.mConnection);
                this.mIsBound = false;
                DsmsLog.d(SUBTAG, "Service unbound");
            }
        }

        public boolean isCommercializedDevice() throws RemoteException {
            boolean zIsCommercializedDevice;
            synchronized (this.mLock) {
                IDsmsInfoService iDsmsInfoService = this.mIDsmsInfoService;
                if (iDsmsInfoService == null) {
                    throw new IllegalStateException("Service is not connected");
                }
                zIsCommercializedDevice = iDsmsInfoService.isCommercializedDevice();
            }
            return zIsCommercializedDevice;
        }
    }
}
