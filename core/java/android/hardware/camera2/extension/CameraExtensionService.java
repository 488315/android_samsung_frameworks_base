package android.hardware.camera2.extension;

import android.annotation.SystemApi;
import android.app.AppOpsManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

@SystemApi
/* loaded from: classes2.dex */
public abstract class CameraExtensionService extends Service {
    private static final String TAG = "CameraExtensionService";
    private CameraUsageTracker mCameraUsageTracker;
    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() { // from class:
                // android.hardware.camera2.extension.CameraExtensionService.1
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
    private static Object mLock = new Object();
    private static IInitializeSessionCallback mInitializeCb = null;

    public abstract AdvancedExtender onInitializeAdvancedExtension(int i);

    public abstract boolean onRegisterClient(IBinder iBinder);

    public abstract void onUnregisterClient(IBinder iBinder);

    private final class CameraTracker implements CameraUsageTracker {
        private final AppOpsManager mAppOpsService;
        private final String mAttributionTag;
        private final String mPackageName;
        private int mUid;

        private CameraTracker() {
            this.mAppOpsService =
                    (AppOpsManager)
                            CameraExtensionService.this
                                    .getApplicationContext()
                                    .getSystemService(AppOpsManager.class);
            this.mPackageName = CameraExtensionService.this.getPackageName();
            this.mAttributionTag = CameraExtensionService.this.getAttributionTag();
            this.mUid = CameraExtensionService.this.getApplicationInfo().uid;
        }

        @Override // android.hardware.camera2.extension.CameraUsageTracker
        public void startCameraOperation() {
            if (this.mAppOpsService != null) {
                this.mAppOpsService.startOp(
                        AppOpsManager.OPSTR_CAMERA,
                        this.mUid,
                        this.mPackageName,
                        this.mAttributionTag,
                        "Camera extensions");
            }
        }

        @Override // android.hardware.camera2.extension.CameraUsageTracker
        public void finishCameraOperation() {
            if (this.mAppOpsService != null) {
                this.mAppOpsService.finishOp(
                        AppOpsManager.OPSTR_CAMERA,
                        this.mUid,
                        this.mPackageName,
                        this.mAttributionTag);
            }
        }
    }

    protected CameraExtensionService() {}

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        byte b = 0;
        if (this.mCameraUsageTracker == null) {
            this.mCameraUsageTracker = new CameraTracker();
        }
        return new CameraExtensionServiceImpl();
    }

    private class CameraExtensionServiceImpl extends ICameraExtensionsProxyService.Stub {
        private CameraExtensionServiceImpl() {}

        @Override // android.hardware.camera2.extension.ICameraExtensionsProxyService
        public boolean registerClient(IBinder token) throws RemoteException {
            return CameraExtensionService.this.onRegisterClient(token);
        }

        @Override // android.hardware.camera2.extension.ICameraExtensionsProxyService
        public void unregisterClient(IBinder token) throws RemoteException {
            CameraExtensionService.this.onUnregisterClient(token);
        }

        @Override // android.hardware.camera2.extension.ICameraExtensionsProxyService
        public boolean advancedExtensionsSupported() throws RemoteException {
            return true;
        }

        @Override // android.hardware.camera2.extension.ICameraExtensionsProxyService
        public void initializeSession(IInitializeSessionCallback cb) {
            boolean ret = false;
            synchronized (CameraExtensionService.mLock) {
                if (CameraExtensionService.mInitializeCb == null) {
                    CameraExtensionService.mInitializeCb = cb;
                    try {
                        CameraExtensionService.mInitializeCb
                                .asBinder()
                                .linkToDeath(CameraExtensionService.this.mDeathRecipient, 0);
                    } catch (RemoteException e) {
                        Log.e(
                                CameraExtensionService.TAG,
                                "Failure to register binder death notifier!");
                    }
                    ret = true;
                }
            }
            try {
                if (ret) {
                    cb.onSuccess();
                } else {
                    cb.onFailure();
                }
            } catch (RemoteException e2) {
                Log.e(CameraExtensionService.TAG, "Client doesn't respond!");
            }
        }

        @Override // android.hardware.camera2.extension.ICameraExtensionsProxyService
        public void releaseSession() {
            synchronized (CameraExtensionService.mLock) {
                if (CameraExtensionService.mInitializeCb != null) {
                    CameraExtensionService.mInitializeCb
                            .asBinder()
                            .unlinkToDeath(CameraExtensionService.this.mDeathRecipient, 0);
                    CameraExtensionService.mInitializeCb = null;
                }
            }
        }

        @Override // android.hardware.camera2.extension.ICameraExtensionsProxyService
        public IPreviewExtenderImpl initializePreviewExtension(int extensionType)
                throws RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.ICameraExtensionsProxyService
        public IImageCaptureExtenderImpl initializeImageExtension(int extensionType)
                throws RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.ICameraExtensionsProxyService
        public IAdvancedExtenderImpl initializeAdvancedExtension(int extensionType)
                throws RemoteException {
            AdvancedExtender extender =
                    CameraExtensionService.this.onInitializeAdvancedExtension(extensionType);
            extender.setCameraUsageTracker(CameraExtensionService.this.mCameraUsageTracker);
            return extender.getAdvancedExtenderBinder();
        }
    }
}
