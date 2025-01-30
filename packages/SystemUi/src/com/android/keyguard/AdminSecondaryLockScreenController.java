package com.android.keyguard;

import android.app.admin.IKeyguardCallback;
import android.app.admin.IKeyguardClient;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.SurfaceControlViewHost;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import com.android.keyguard.AdminSecondaryLockScreenController;
import com.android.keyguard.KeyguardSecurityModel;
import java.util.HashMap;
import java.util.NoSuchElementException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AdminSecondaryLockScreenController {
    public final IKeyguardCallbackStubC06302 mCallback;
    public IKeyguardClient mClient;
    public final ServiceConnectionC06291 mConnection;
    public final Context mContext;
    public final Handler mHandler;
    public final KeyguardSecurityCallback mKeyguardCallback;
    public final AdminSecondaryLockScreenController$$ExternalSyntheticLambda0 mKeyguardClientDeathRecipient;
    public final KeyguardSecurityContainer mParent;
    protected SurfaceHolder.Callback mSurfaceHolderCallback;
    public final KeyguardUpdateMonitorCallback mUpdateCallback;
    public final KeyguardUpdateMonitor mUpdateMonitor;
    public final AdminSecurityView mView;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.keyguard.AdminSecondaryLockScreenController$2 */
    public final class IKeyguardCallbackStubC06302 extends IKeyguardCallback.Stub {
        public IKeyguardCallbackStubC06302() {
        }

        public final void onDismiss() {
            AdminSecondaryLockScreenController.this.mHandler.post(new AdminSecondaryLockScreenController$2$$ExternalSyntheticLambda0(this, 0));
        }

        public final void onRemoteContentReady(SurfaceControlViewHost.SurfacePackage surfacePackage) {
            Handler handler = AdminSecondaryLockScreenController.this.mHandler;
            if (handler != null) {
                handler.removeCallbacksAndMessages(null);
                if (surfacePackage != null) {
                    AdminSecondaryLockScreenController.this.mView.setChildSurfacePackage(surfacePackage);
                } else {
                    AdminSecondaryLockScreenController.this.mHandler.post(new AdminSecondaryLockScreenController$2$$ExternalSyntheticLambda0(this, 1));
                }
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class AdminSecurityView extends SurfaceView {
        public final SurfaceHolder.Callback mSurfaceHolderCallback;

        public AdminSecurityView(AdminSecondaryLockScreenController adminSecondaryLockScreenController, Context context, SurfaceHolder.Callback callback) {
            super(context);
            this.mSurfaceHolderCallback = callback;
            setZOrderOnTop(true);
        }

        @Override // android.view.SurfaceView, android.view.View
        public final void onAttachedToWindow() {
            super.onAttachedToWindow();
            getHolder().addCallback(this.mSurfaceHolderCallback);
        }

        @Override // android.view.SurfaceView, android.view.View
        public final void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            getHolder().removeCallback(this.mSurfaceHolderCallback);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Factory {
        public final Context mContext;
        public final Handler mHandler;
        public final KeyguardSecurityContainer mParent;
        public final KeyguardUpdateMonitor mUpdateMonitor;

        public Factory(Context context, KeyguardSecurityContainer keyguardSecurityContainer, KeyguardUpdateMonitor keyguardUpdateMonitor, Handler handler) {
            this.mContext = context;
            this.mParent = keyguardSecurityContainer;
            this.mUpdateMonitor = keyguardUpdateMonitor;
            this.mHandler = handler;
        }
    }

    /* renamed from: -$$Nest$monSurfaceReady, reason: not valid java name */
    public static void m349$$Nest$monSurfaceReady(AdminSecondaryLockScreenController adminSecondaryLockScreenController) {
        adminSecondaryLockScreenController.getClass();
        try {
            IBinder hostToken = adminSecondaryLockScreenController.mView.getHostToken();
            if (hostToken != null) {
                adminSecondaryLockScreenController.mClient.onCreateKeyguardSurface(hostToken, adminSecondaryLockScreenController.mCallback);
            } else {
                adminSecondaryLockScreenController.hide();
            }
        } catch (RemoteException e) {
            Log.e("AdminSecondaryLockScreenController", "Error in onCreateKeyguardSurface", e);
            adminSecondaryLockScreenController.dismiss(KeyguardUpdateMonitor.getCurrentUser());
        }
    }

    public /* synthetic */ AdminSecondaryLockScreenController(Context context, KeyguardSecurityContainer keyguardSecurityContainer, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardSecurityCallback keyguardSecurityCallback, Handler handler, int i) {
        this(context, keyguardSecurityContainer, keyguardUpdateMonitor, keyguardSecurityCallback, handler);
    }

    public final void dismiss(int i) {
        this.mHandler.removeCallbacksAndMessages(null);
        if (this.mView.isAttachedToWindow() && i == KeyguardUpdateMonitor.getCurrentUser()) {
            hide();
            KeyguardSecurityCallback keyguardSecurityCallback = this.mKeyguardCallback;
            if (keyguardSecurityCallback != null) {
                keyguardSecurityCallback.dismiss(true, i, true, KeyguardSecurityModel.SecurityMode.Invalid);
            }
        }
    }

    public final void hide() {
        AdminSecurityView adminSecurityView = this.mView;
        if (adminSecurityView.isAttachedToWindow()) {
            this.mParent.removeView(adminSecurityView);
        }
        IKeyguardClient iKeyguardClient = this.mClient;
        if (iKeyguardClient != null) {
            try {
                iKeyguardClient.asBinder().unlinkToDeath(this.mKeyguardClientDeathRecipient, 0);
            } catch (NoSuchElementException unused) {
                Log.w("AdminSecondaryLockScreenController", "IKeyguardClient death recipient already released");
            }
            this.mContext.unbindService(this.mConnection);
            this.mClient = null;
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.keyguard.AdminSecondaryLockScreenController$1] */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.keyguard.AdminSecondaryLockScreenController$$ExternalSyntheticLambda0] */
    private AdminSecondaryLockScreenController(Context context, KeyguardSecurityContainer keyguardSecurityContainer, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardSecurityCallback keyguardSecurityCallback, Handler handler) {
        this.mConnection = new ServiceConnection() { // from class: com.android.keyguard.AdminSecondaryLockScreenController.1
            @Override // android.content.ServiceConnection
            public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                AdminSecondaryLockScreenController.this.mClient = IKeyguardClient.Stub.asInterface(iBinder);
                if (AdminSecondaryLockScreenController.this.mView.isAttachedToWindow()) {
                    AdminSecondaryLockScreenController adminSecondaryLockScreenController = AdminSecondaryLockScreenController.this;
                    if (adminSecondaryLockScreenController.mClient != null) {
                        AdminSecondaryLockScreenController.m349$$Nest$monSurfaceReady(adminSecondaryLockScreenController);
                        try {
                            iBinder.linkToDeath(AdminSecondaryLockScreenController.this.mKeyguardClientDeathRecipient, 0);
                        } catch (RemoteException e) {
                            Log.e("AdminSecondaryLockScreenController", "Lost connection to secondary lockscreen service", e);
                            AdminSecondaryLockScreenController.this.dismiss(KeyguardUpdateMonitor.getCurrentUser());
                        }
                    }
                }
            }

            @Override // android.content.ServiceConnection
            public final void onServiceDisconnected(ComponentName componentName) {
                AdminSecondaryLockScreenController.this.mClient = null;
            }
        };
        this.mKeyguardClientDeathRecipient = new IBinder.DeathRecipient() { // from class: com.android.keyguard.AdminSecondaryLockScreenController$$ExternalSyntheticLambda0
            @Override // android.os.IBinder.DeathRecipient
            public final void binderDied() {
                AdminSecondaryLockScreenController.this.hide();
                Log.d("AdminSecondaryLockScreenController", "KeyguardClient service died");
            }
        };
        this.mCallback = new IKeyguardCallbackStubC06302();
        this.mUpdateCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.keyguard.AdminSecondaryLockScreenController.3
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onSecondaryLockscreenRequirementChanged(int i) {
                AdminSecondaryLockScreenController adminSecondaryLockScreenController = AdminSecondaryLockScreenController.this;
                if (((Intent) ((HashMap) adminSecondaryLockScreenController.mUpdateMonitor.mSecondaryLockscreenRequirement).get(Integer.valueOf(i))) == null) {
                    adminSecondaryLockScreenController.dismiss(i);
                }
            }
        };
        this.mSurfaceHolderCallback = new SurfaceHolderCallbackC06324();
        this.mContext = context;
        this.mHandler = handler;
        this.mParent = keyguardSecurityContainer;
        this.mUpdateMonitor = keyguardUpdateMonitor;
        this.mKeyguardCallback = keyguardSecurityCallback;
        AdminSecurityView adminSecurityView = new AdminSecurityView(this, context, this.mSurfaceHolderCallback);
        this.mView = adminSecurityView;
        adminSecurityView.setId(View.generateViewId());
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.keyguard.AdminSecondaryLockScreenController$4 */
    public final class SurfaceHolderCallbackC06324 implements SurfaceHolder.Callback {
        public SurfaceHolderCallbackC06324() {
        }

        @Override // android.view.SurfaceHolder.Callback
        public final void surfaceCreated(SurfaceHolder surfaceHolder) {
            final int currentUser = KeyguardUpdateMonitor.getCurrentUser();
            AdminSecondaryLockScreenController adminSecondaryLockScreenController = AdminSecondaryLockScreenController.this;
            adminSecondaryLockScreenController.mUpdateMonitor.registerCallback(adminSecondaryLockScreenController.mUpdateCallback);
            AdminSecondaryLockScreenController adminSecondaryLockScreenController2 = AdminSecondaryLockScreenController.this;
            if (adminSecondaryLockScreenController2.mClient != null) {
                AdminSecondaryLockScreenController.m349$$Nest$monSurfaceReady(adminSecondaryLockScreenController2);
            }
            AdminSecondaryLockScreenController.this.mHandler.postDelayed(new Runnable() { // from class: com.android.keyguard.AdminSecondaryLockScreenController$4$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    AdminSecondaryLockScreenController.SurfaceHolderCallbackC06324 surfaceHolderCallbackC06324 = AdminSecondaryLockScreenController.SurfaceHolderCallbackC06324.this;
                    AdminSecondaryLockScreenController.this.dismiss(currentUser);
                    Log.w("AdminSecondaryLockScreenController", "Timed out waiting for secondary lockscreen content.");
                }
            }, 500L);
        }

        @Override // android.view.SurfaceHolder.Callback
        public final void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            AdminSecondaryLockScreenController adminSecondaryLockScreenController = AdminSecondaryLockScreenController.this;
            adminSecondaryLockScreenController.mUpdateMonitor.removeCallback(adminSecondaryLockScreenController.mUpdateCallback);
        }

        @Override // android.view.SurfaceHolder.Callback
        public final void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        }
    }
}
