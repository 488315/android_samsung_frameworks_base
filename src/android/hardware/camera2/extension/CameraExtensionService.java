package android.hardware.camera2.extension;

import android.annotation.SystemApi;
import android.app.AppOpsManager;
import android.app.Service;
import android.content.Intent;
import android.hardware.camera2.extension.ICameraExtensionsProxyService;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

@SystemApi
/* loaded from: classes2.dex */
public abstract class CameraExtensionService extends Service {
    private static final String TAG = "CameraExtensionService";
    private static IInitializeSessionCallback mInitializeCb;
    private static Object mLock = new Object();
    private CameraUsageTracker mCameraUsageTracker;
    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() { // from class: android.hardware.camera2.extension.CameraExtensionService.1
        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            synchronized (CameraExtensionService.mLock) {
                CameraExtensionService.mInitializeCb = null;
            }
            if (CameraExtensionService.this.mCameraUsageTracker != null) {
                CameraExtensionService.this.mCameraUsageTracker.finishCameraOperation();
            }
        }
    };

    public abstract AdvancedExtender onInitializeAdvancedExtension(int i);

    public abstract boolean onRegisterClient(IBinder iBinder);

    public abstract void onUnregisterClient(IBinder iBinder);

    private final class CameraTracker implements CameraUsageTracker {
        private final AppOpsManager mAppOpsService;
        private final String mAttributionTag;
        private final String mPackageName;
        private int mUid;

        private CameraTracker() {
            this.mAppOpsService = (AppOpsManager) CameraExtensionService.this.getApplicationContext().getSystemService(AppOpsManager.class);
            this.mPackageName = CameraExtensionService.this.getPackageName();
            this.mAttributionTag = CameraExtensionService.this.getAttributionTag();
            this.mUid = CameraExtensionService.this.getApplicationInfo().uid;
        }

        @Override // android.hardware.camera2.extension.CameraUsageTracker
        public void startCameraOperation() {
            AppOpsManager appOpsManager = this.mAppOpsService;
            if (appOpsManager != null) {
                appOpsManager.startOp(AppOpsManager.OPSTR_CAMERA, this.mUid, this.mPackageName, this.mAttributionTag, "Camera extensions");
            }
        }

        @Override // android.hardware.camera2.extension.CameraUsageTracker
        public void finishCameraOperation() {
            AppOpsManager appOpsManager = this.mAppOpsService;
            if (appOpsManager != null) {
                appOpsManager.finishOp(AppOpsManager.OPSTR_CAMERA, this.mUid, this.mPackageName, this.mAttributionTag);
            }
        }
    }

    protected CameraExtensionService() {
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        if (this.mCameraUsageTracker == null) {
            this.mCameraUsageTracker = new CameraTracker();
        }
        return new CameraExtensionServiceImpl();
    }

    private class CameraExtensionServiceImpl extends ICameraExtensionsProxyService.Stub {
        @Override // android.hardware.camera2.extension.ICameraExtensionsProxyService
        public boolean advancedExtensionsSupported() throws RemoteException {
            return true;
        }

        @Override // android.hardware.camera2.extension.ICameraExtensionsProxyService
        public IImageCaptureExtenderImpl initializeImageExtension(int i) throws RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.ICameraExtensionsProxyService
        public IPreviewExtenderImpl initializePreviewExtension(int i) throws RemoteException {
            return null;
        }

        private CameraExtensionServiceImpl() {
        }

        @Override // android.hardware.camera2.extension.ICameraExtensionsProxyService
        public boolean registerClient(IBinder iBinder) throws RemoteException {
            return CameraExtensionService.this.onRegisterClient(iBinder);
        }

        @Override // android.hardware.camera2.extension.ICameraExtensionsProxyService
        public void unregisterClient(IBinder iBinder) throws RemoteException {
            CameraExtensionService.this.onUnregisterClient(iBinder);
        }

        @Override // android.hardware.camera2.extension.ICameraExtensionsProxyService
        public void initializeSession(IInitializeSessionCallback iInitializeSessionCallback) {
            boolean z;
            synchronized (CameraExtensionService.mLock) {
                z = false;
                if (CameraExtensionService.mInitializeCb == null) {
                    CameraExtensionService.mInitializeCb = iInitializeSessionCallback;
                    try {
                        CameraExtensionService.mInitializeCb.asBinder().linkToDeath(CameraExtensionService.this.mDeathRecipient, 0);
                    } catch (RemoteException unused) {
                        Log.e(CameraExtensionService.TAG, "Failure to register binder death notifier!");
                    }
                    z = true;
                }
            }
            try {
                if (z) {
                    iInitializeSessionCallback.onSuccess();
                } else {
                    iInitializeSessionCallback.onFailure();
                }
            } catch (RemoteException unused2) {
                Log.e(CameraExtensionService.TAG, "Client doesn't respond!");
            }
        }

        @Override // android.hardware.camera2.extension.ICameraExtensionsProxyService
        public void releaseSession() {
            synchronized (CameraExtensionService.mLock) {
                if (CameraExtensionService.mInitializeCb != null) {
                    CameraExtensionService.mInitializeCb.asBinder().unlinkToDeath(CameraExtensionService.this.mDeathRecipient, 0);
                    CameraExtensionService.mInitializeCb = null;
                }
            }
        }

        @Override // android.hardware.camera2.extension.ICameraExtensionsProxyService
        public IAdvancedExtenderImpl initializeAdvancedExtension(int i) throws RemoteException {
            AdvancedExtender advancedExtenderOnInitializeAdvancedExtension = CameraExtensionService.this.onInitializeAdvancedExtension(i);
            advancedExtenderOnInitializeAdvancedExtension.setCameraUsageTracker(CameraExtensionService.this.mCameraUsageTracker);
            return advancedExtenderOnInitializeAdvancedExtension.getAdvancedExtenderBinder();
        }
    }
}
