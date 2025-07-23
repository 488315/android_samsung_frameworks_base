package android.os;

import android.annotation.SystemApi;
import android.app.ActivityManager;
import android.content.Context;
import android.os.BugreportManager;
import android.os.IDumpstateListener;
import android.widget.Toast;
import com.android.internal.R;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.Executor;
import libcore.io.IoUtils;

/* loaded from: classes3.dex */
public final class BugreportManager {
    private static final String TAG = "BugreportManager";
    private final IDumpstate mBinder;
    private final Context mContext;

    public static abstract class BugreportCallback {
        public static final int BUGREPORT_ERROR_ANOTHER_REPORT_IN_PROGRESS = 5;
        public static final int BUGREPORT_ERROR_INVALID_INPUT = 1;
        public static final int BUGREPORT_ERROR_NO_BUGREPORT_TO_RETRIEVE = 6;
        public static final int BUGREPORT_ERROR_RUNTIME = 2;
        public static final int BUGREPORT_ERROR_USER_CONSENT_TIMED_OUT = 4;
        public static final int BUGREPORT_ERROR_USER_DENIED_CONSENT = 3;

        @Retention(RetentionPolicy.SOURCE)
        public @interface BugreportErrorCode {
        }

        public void onEarlyReportFinished() {
        }

        public void onError(int i) {
        }

        public void onFinished() {
        }

        @SystemApi
        public void onFinished(String str) {
        }

        public void onProgress(float f) {
        }
    }

    public BugreportManager(Context context, IDumpstate iDumpstate) {
        this.mContext = context;
        this.mBinder = iDumpstate;
    }

    @SystemApi
    public void preDumpUiData() {
        try {
            this.mBinder.preDumpUiData(this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0023 A[Catch: all -> 0x006e, FileNotFoundException -> 0x0072, RemoteException -> 0x0085, TRY_LEAVE, TryCatch #4 {RemoteException -> 0x0085, FileNotFoundException -> 0x0072, all -> 0x006e, blocks: (B:2:0x0000, B:10:0x0023), top: B:1:0x0000 }] */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0066  */
    /* JADX WARN: Removed duplicated region for block: B:19:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0093  */
    @android.annotation.SystemApi
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void startBugreport(android.os.ParcelFileDescriptor r18, android.os.ParcelFileDescriptor r19, android.os.BugreportParams r20, java.util.concurrent.Executor r21, android.os.BugreportManager.BugreportCallback r22) {
        /*
            r17 = this;
            com.android.internal.util.Preconditions.checkNotNull(r18)     // Catch: java.lang.Throwable -> L6e java.io.FileNotFoundException -> L72 android.os.RemoteException -> L85
            com.android.internal.util.Preconditions.checkNotNull(r20)     // Catch: java.lang.Throwable -> L6e java.io.FileNotFoundException -> L72 android.os.RemoteException -> L85
            com.android.internal.util.Preconditions.checkNotNull(r21)     // Catch: java.lang.Throwable -> L6e java.io.FileNotFoundException -> L72 android.os.RemoteException -> L85
            com.android.internal.util.Preconditions.checkNotNull(r22)     // Catch: java.lang.Throwable -> L6e java.io.FileNotFoundException -> L72 android.os.RemoteException -> L85
            int r0 = r20.getFlags()     // Catch: java.lang.Throwable -> L6e java.io.FileNotFoundException -> L72 android.os.RemoteException -> L85
            r0 = r0 & 2
            r1 = 1
            r2 = 0
            if (r0 == 0) goto L18
            r5 = r1
            goto L19
        L18:
            r5 = r2
        L19:
            if (r19 != 0) goto L20
            if (r5 == 0) goto L1e
            goto L20
        L1e:
            r14 = r2
            goto L21
        L20:
            r14 = r1
        L21:
            if (r19 != 0) goto L33
            java.io.File r0 = new java.io.File     // Catch: java.lang.Throwable -> L6e java.io.FileNotFoundException -> L72 android.os.RemoteException -> L85
            java.lang.String r1 = "/dev/null"
            r0.<init>(r1)     // Catch: java.lang.Throwable -> L6e java.io.FileNotFoundException -> L72 android.os.RemoteException -> L85
            r1 = 268435456(0x10000000, float:2.524355E-29)
            android.os.ParcelFileDescriptor r0 = android.os.ParcelFileDescriptor.open(r0, r1)     // Catch: java.lang.Throwable -> L6e java.io.FileNotFoundException -> L72 android.os.RemoteException -> L85
            r16 = r0
            goto L35
        L33:
            r16 = r19
        L35:
            android.os.BugreportManager$DumpstateListener r13 = new android.os.BugreportManager$DumpstateListener     // Catch: java.io.FileNotFoundException -> L6a android.os.RemoteException -> L6c java.lang.Throwable -> L8d
            r1 = r17
            r2 = r21
            r3 = r22
            r0 = r13
            r4 = r14
            r0.<init>(r2, r3, r4, r5)     // Catch: java.io.FileNotFoundException -> L6a android.os.RemoteException -> L6c java.lang.Throwable -> L8d
            r13 = r0
            r14 = r4
            android.os.IDumpstate r6 = r1.mBinder     // Catch: java.io.FileNotFoundException -> L6a android.os.RemoteException -> L6c java.lang.Throwable -> L8d
            android.content.Context r0 = r1.mContext     // Catch: java.io.FileNotFoundException -> L6a android.os.RemoteException -> L6c java.lang.Throwable -> L8d
            java.lang.String r8 = r0.getOpPackageName()     // Catch: java.io.FileNotFoundException -> L6a android.os.RemoteException -> L6c java.lang.Throwable -> L8d
            java.io.FileDescriptor r9 = r18.getFileDescriptor()     // Catch: java.io.FileNotFoundException -> L6a android.os.RemoteException -> L6c java.lang.Throwable -> L8d
            java.io.FileDescriptor r10 = r16.getFileDescriptor()     // Catch: java.io.FileNotFoundException -> L6a android.os.RemoteException -> L6c java.lang.Throwable -> L8d
            int r11 = r20.getMode()     // Catch: java.io.FileNotFoundException -> L6a android.os.RemoteException -> L6c java.lang.Throwable -> L8d
            int r12 = r20.getFlags()     // Catch: java.io.FileNotFoundException -> L6a android.os.RemoteException -> L6c java.lang.Throwable -> L8d
            r15 = 0
            r7 = -1
            r6.startBugreport(r7, r8, r9, r10, r11, r12, r13, r14, r15)     // Catch: java.io.FileNotFoundException -> L6a android.os.RemoteException -> L6c java.lang.Throwable -> L8d
            libcore.io.IoUtils.closeQuietly(r18)
            if (r16 == 0) goto L84
            libcore.io.IoUtils.closeQuietly(r16)
            return
        L6a:
            r0 = move-exception
            goto L75
        L6c:
            r0 = move-exception
            goto L88
        L6e:
            r0 = move-exception
            r16 = r19
            goto L8e
        L72:
            r0 = move-exception
            r16 = r19
        L75:
            java.lang.String r1 = "BugreportManager"
            java.lang.String r2 = "Not able to find /dev/null file: "
            android.util.Log.wtf(r1, r2, r0)     // Catch: java.lang.Throwable -> L8d
            libcore.io.IoUtils.closeQuietly(r18)
            if (r16 == 0) goto L84
            libcore.io.IoUtils.closeQuietly(r16)
        L84:
            return
        L85:
            r0 = move-exception
            r16 = r19
        L88:
            java.lang.RuntimeException r0 = r0.rethrowFromSystemServer()     // Catch: java.lang.Throwable -> L8d
            throw r0     // Catch: java.lang.Throwable -> L8d
        L8d:
            r0 = move-exception
        L8e:
            libcore.io.IoUtils.closeQuietly(r18)
            if (r16 == 0) goto L96
            libcore.io.IoUtils.closeQuietly(r16)
        L96:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.os.BugreportManager.startBugreport(android.os.ParcelFileDescriptor, android.os.ParcelFileDescriptor, android.os.BugreportParams, java.util.concurrent.Executor, android.os.BugreportManager$BugreportCallback):void");
    }

    @SystemApi
    public void retrieveBugreport(String str, ParcelFileDescriptor parcelFileDescriptor, Executor executor, BugreportCallback bugreportCallback) {
        try {
            try {
                Preconditions.checkNotNull(str);
                Preconditions.checkNotNull(parcelFileDescriptor);
                Preconditions.checkNotNull(executor);
                Preconditions.checkNotNull(bugreportCallback);
                this.mBinder.retrieveBugreport(Binder.getCallingUid(), this.mContext.getOpPackageName(), this.mContext.getUserId(), parcelFileDescriptor.getFileDescriptor(), str, false, false, new DumpstateListener(executor, bugreportCallback, false, false));
                IoUtils.closeQuietly(parcelFileDescriptor);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        } catch (Throwable th) {
            IoUtils.closeQuietly(parcelFileDescriptor);
            throw th;
        }
    }

    public void startConnectivityBugreport(ParcelFileDescriptor parcelFileDescriptor, Executor executor, BugreportCallback bugreportCallback) {
        startBugreport(parcelFileDescriptor, null, new BugreportParams(4), executor, bugreportCallback);
    }

    public void cancelBugreport() {
        try {
            this.mBinder.cancelBugreport(-1, this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void requestBugreport(BugreportParams bugreportParams, CharSequence charSequence, CharSequence charSequence2) {
        String charSequence3;
        String str = null;
        if (charSequence == null) {
            charSequence3 = null;
        } else {
            try {
                charSequence3 = charSequence.toString();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        if (charSequence2 != null) {
            str = charSequence2.toString();
        }
        ActivityManager.getService().requestBugReportWithDescription(charSequence3, str, bugreportParams.getMode());
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class DumpstateListener extends IDumpstateListener.Stub {
        private final BugreportCallback mCallback;
        private final Executor mExecutor;
        private final boolean mIsConsentDeferred;
        private final boolean mIsScreenshotRequested;

        DumpstateListener(Executor executor, BugreportCallback bugreportCallback, boolean z, boolean z2) {
            this.mExecutor = executor;
            this.mCallback = bugreportCallback;
            this.mIsScreenshotRequested = z;
            this.mIsConsentDeferred = z2;
        }

        @Override // android.os.IDumpstateListener
        public void onProgress(final int i) throws RemoteException {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new Runnable() { // from class: android.os.BugreportManager$DumpstateListener$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        BugreportManager.DumpstateListener.this.lambda$onProgress$0(i);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onProgress$0(int i) {
            this.mCallback.onProgress(i);
        }

        @Override // android.os.IDumpstateListener
        public void onError(final int i) throws RemoteException {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new Runnable() { // from class: android.os.BugreportManager$DumpstateListener$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        BugreportManager.DumpstateListener.this.lambda$onError$1(i);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onError$1(int i) {
            this.mCallback.onError(i);
        }

        @Override // android.os.IDumpstateListener
        public void onFinished(final String str) throws RemoteException {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                if (this.mIsConsentDeferred) {
                    this.mExecutor.execute(new Runnable() { // from class: android.os.BugreportManager$DumpstateListener$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            BugreportManager.DumpstateListener.this.lambda$onFinished$2(str);
                        }
                    });
                } else {
                    this.mExecutor.execute(new Runnable() { // from class: android.os.BugreportManager$DumpstateListener$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            BugreportManager.DumpstateListener.this.lambda$onFinished$3();
                        }
                    });
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onFinished$2(String str) {
            this.mCallback.onFinished(str);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onFinished$3() {
            this.mCallback.onFinished();
        }

        @Override // android.os.IDumpstateListener
        public void onScreenshotTaken(final boolean z) throws RemoteException {
            if (this.mIsScreenshotRequested) {
                new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: android.os.BugreportManager$DumpstateListener$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        BugreportManager.DumpstateListener.this.lambda$onScreenshotTaken$4(z);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onScreenshotTaken$4(boolean z) {
            Toast.makeText(BugreportManager.this.mContext, z ? R.string.bugreport_screenshot_success_toast : R.string.bugreport_screenshot_failure_toast, 1).show();
        }

        @Override // android.os.IDumpstateListener
        public void onUiIntensiveBugreportDumpsFinished() throws RemoteException {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new Runnable() { // from class: android.os.BugreportManager$DumpstateListener$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        BugreportManager.DumpstateListener.this.lambda$onUiIntensiveBugreportDumpsFinished$5();
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onUiIntensiveBugreportDumpsFinished$5() {
            this.mCallback.onEarlyReportFinished();
        }
    }
}
