package android.os;

import android.annotation.SystemApi;
import android.app.ActivityManager;
import android.content.Context;
import android.widget.Toast;

import com.android.internal.R;
import com.android.internal.util.Preconditions;

import libcore.io.IoUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.Executor;

public final class BugreportManager {
    private static final String TAG = "BugreportManager";
    private final IDumpstate mBinder;
    private final Context mContext;

    public BugreportManager(Context context, IDumpstate binder) {
        this.mContext = context;
        this.mBinder = binder;
    }

    public abstract static class BugreportCallback {
        public static final int BUGREPORT_ERROR_ANOTHER_REPORT_IN_PROGRESS = 5;
        public static final int BUGREPORT_ERROR_INVALID_INPUT = 1;
        public static final int BUGREPORT_ERROR_NO_BUGREPORT_TO_RETRIEVE = 6;
        public static final int BUGREPORT_ERROR_RUNTIME = 2;
        public static final int BUGREPORT_ERROR_USER_CONSENT_TIMED_OUT = 4;
        public static final int BUGREPORT_ERROR_USER_DENIED_CONSENT = 3;

        @Retention(RetentionPolicy.SOURCE)
        public @interface BugreportErrorCode {}

        public void onProgress(float progress) {}

        public void onError(int errorCode) {}

        public void onFinished() {}

        @SystemApi
        public void onFinished(String bugreportFile) {}

        public void onEarlyReportFinished() {}
    }

    @SystemApi
    public void preDumpUiData() {
        try {
            this.mBinder.preDumpUiData(this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(12:2|3|(1:5)(1:33)|(8:9|(2:11|12)(1:31)|13|14|15|(1:20)|17|18)|32|(0)(0)|13|14|15|(0)|17|18) */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0070, code lost:

       r0 = e;
    */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x008f, code lost:

       throw r0.rethrowFromSystemServer();
    */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x006e, code lost:

       r0 = e;
    */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0079, code lost:

       android.util.Log.wtf(android.os.BugreportManager.TAG, "Not able to find /dev/null file: ", r0);
    */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0081, code lost:

       libcore.io.IoUtils.closeQuietly(r20);
    */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0084, code lost:

       if (r8 == null) goto L47;
    */
    /* JADX WARN: Code restructure failed: missing block: B:29:?, code lost:

       return;
    */
    /* JADX WARN: Removed duplicated region for block: B:11:0x0026 A[Catch: all -> 0x0072, FileNotFoundException -> 0x0076, RemoteException -> 0x0088, TRY_LEAVE, TryCatch #4 {RemoteException -> 0x0088, FileNotFoundException -> 0x0076, all -> 0x0072, blocks: (B:3:0x0002, B:11:0x0026), top: B:2:0x0002 }] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0087 A[ORIG_RETURN, RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0035  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0096  */
    @android.annotation.SystemApi
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void startBugreport(
            android.os.ParcelFileDescriptor r20,
            android.os.ParcelFileDescriptor r21,
            android.os.BugreportParams r22,
            java.util.concurrent.Executor r23,
            android.os.BugreportManager.BugreportCallback r24) {
        /*
            r19 = this;
            r7 = r19
            com.android.internal.util.Preconditions.checkNotNull(r20)     // Catch: java.lang.Throwable -> L72 java.io.FileNotFoundException -> L76 android.os.RemoteException -> L88
            com.android.internal.util.Preconditions.checkNotNull(r22)     // Catch: java.lang.Throwable -> L72 java.io.FileNotFoundException -> L76 android.os.RemoteException -> L88
            com.android.internal.util.Preconditions.checkNotNull(r23)     // Catch: java.lang.Throwable -> L72 java.io.FileNotFoundException -> L76 android.os.RemoteException -> L88
            com.android.internal.util.Preconditions.checkNotNull(r24)     // Catch: java.lang.Throwable -> L72 java.io.FileNotFoundException -> L76 android.os.RemoteException -> L88
            int r0 = r22.getFlags()     // Catch: java.lang.Throwable -> L72 java.io.FileNotFoundException -> L76 android.os.RemoteException -> L88
            r0 = r0 & 2
            r1 = 1
            r2 = 0
            if (r0 == 0) goto L1b
            r0 = r1
            goto L1c
        L1b:
            r0 = r2
        L1c:
            if (r21 != 0) goto L23
            if (r0 == 0) goto L21
            goto L23
        L21:
            r5 = r2
            goto L24
        L23:
            r5 = r1
        L24:
            if (r21 != 0) goto L35
            java.io.File r1 = new java.io.File     // Catch: java.lang.Throwable -> L72 java.io.FileNotFoundException -> L76 android.os.RemoteException -> L88
            java.lang.String r2 = "/dev/null"
            r1.<init>(r2)     // Catch: java.lang.Throwable -> L72 java.io.FileNotFoundException -> L76 android.os.RemoteException -> L88
            r2 = 268435456(0x10000000, float:2.5243549E-29)
            android.os.ParcelFileDescriptor r1 = android.os.ParcelFileDescriptor.open(r1, r2)     // Catch: java.lang.Throwable -> L72 java.io.FileNotFoundException -> L76 android.os.RemoteException -> L88
            r8 = r1
            goto L37
        L35:
            r8 = r21
        L37:
            android.os.BugreportManager$DumpstateListener r16 = new android.os.BugreportManager$DumpstateListener     // Catch: java.io.FileNotFoundException -> L6e android.os.RemoteException -> L70 java.lang.Throwable -> L90
            r1 = r16
            r2 = r19
            r3 = r23
            r4 = r24
            r6 = r0
            r1.<init>(r3, r4, r5, r6)     // Catch: java.io.FileNotFoundException -> L6e android.os.RemoteException -> L70 java.lang.Throwable -> L90
            android.os.IDumpstate r9 = r7.mBinder     // Catch: java.io.FileNotFoundException -> L6e android.os.RemoteException -> L70 java.lang.Throwable -> L90
            android.content.Context r1 = r7.mContext     // Catch: java.io.FileNotFoundException -> L6e android.os.RemoteException -> L70 java.lang.Throwable -> L90
            java.lang.String r11 = r1.getOpPackageName()     // Catch: java.io.FileNotFoundException -> L6e android.os.RemoteException -> L70 java.lang.Throwable -> L90
            java.io.FileDescriptor r12 = r20.getFileDescriptor()     // Catch: java.io.FileNotFoundException -> L6e android.os.RemoteException -> L70 java.lang.Throwable -> L90
            java.io.FileDescriptor r13 = r8.getFileDescriptor()     // Catch: java.io.FileNotFoundException -> L6e android.os.RemoteException -> L70 java.lang.Throwable -> L90
            int r14 = r22.getMode()     // Catch: java.io.FileNotFoundException -> L6e android.os.RemoteException -> L70 java.lang.Throwable -> L90
            int r15 = r22.getFlags()     // Catch: java.io.FileNotFoundException -> L6e android.os.RemoteException -> L70 java.lang.Throwable -> L90
            r10 = -1
            r18 = 0
            r17 = r5
            r9.startBugreport(r10, r11, r12, r13, r14, r15, r16, r17, r18)     // Catch: java.io.FileNotFoundException -> L6e android.os.RemoteException -> L70 java.lang.Throwable -> L90
            libcore.io.IoUtils.closeQuietly(r20)
            if (r8 == 0) goto L87
        L6a:
            libcore.io.IoUtils.closeQuietly(r8)
            goto L87
        L6e:
            r0 = move-exception
            goto L79
        L70:
            r0 = move-exception
            goto L8b
        L72:
            r0 = move-exception
            r8 = r21
            goto L91
        L76:
            r0 = move-exception
            r8 = r21
        L79:
            java.lang.String r1 = "BugreportManager"
            java.lang.String r2 = "Not able to find /dev/null file: "
            android.util.Log.wtf(r1, r2, r0)     // Catch: java.lang.Throwable -> L90
            libcore.io.IoUtils.closeQuietly(r20)
            if (r8 == 0) goto L87
            goto L6a
        L87:
            return
        L88:
            r0 = move-exception
            r8 = r21
        L8b:
            java.lang.RuntimeException r1 = r0.rethrowFromSystemServer()     // Catch: java.lang.Throwable -> L90
            throw r1     // Catch: java.lang.Throwable -> L90
        L90:
            r0 = move-exception
        L91:
            libcore.io.IoUtils.closeQuietly(r20)
            if (r8 == 0) goto L99
            libcore.io.IoUtils.closeQuietly(r8)
        L99:
            throw r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " android.os.BugreportManager.startBugreport(android.os.ParcelFileDescriptor,"
                    + " android.os.ParcelFileDescriptor, android.os.BugreportParams,"
                    + " java.util.concurrent.Executor,"
                    + " android.os.BugreportManager$BugreportCallback):void");
    }

    @SystemApi
    public void retrieveBugreport(
            String bugreportFile,
            ParcelFileDescriptor bugreportFd,
            Executor executor,
            BugreportCallback callback) {
        try {
            try {
                Preconditions.checkNotNull(bugreportFile);
                Preconditions.checkNotNull(bugreportFd);
                Preconditions.checkNotNull(executor);
                Preconditions.checkNotNull(callback);
                DumpstateListener dsListener =
                        new DumpstateListener(executor, callback, false, false);
                this.mBinder.retrieveBugreport(
                        Binder.getCallingUid(),
                        this.mContext.getOpPackageName(),
                        this.mContext.getUserId(),
                        bugreportFd.getFileDescriptor(),
                        bugreportFile,
                        false,
                        false,
                        dsListener);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        } finally {
            IoUtils.closeQuietly(bugreportFd);
        }
    }

    public void startConnectivityBugreport(
            ParcelFileDescriptor bugreportFd, Executor executor, BugreportCallback callback) {
        startBugreport(bugreportFd, null, new BugreportParams(4), executor, callback);
    }

    public void cancelBugreport() {
        try {
            this.mBinder.cancelBugreport(-1, this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void requestBugreport(
            BugreportParams params, CharSequence shareTitle, CharSequence shareDescription) {
        String title;
        String description = null;
        if (shareTitle == null) {
            title = null;
        } else {
            try {
                title = shareTitle.toString();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        if (shareDescription != null) {
            description = shareDescription.toString();
        }
        ActivityManager.getService()
                .requestBugReportWithDescription(title, description, params.getMode());
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class DumpstateListener extends IDumpstateListener.Stub {
        private final BugreportCallback mCallback;
        private final Executor mExecutor;
        private final boolean mIsConsentDeferred;
        private final boolean mIsScreenshotRequested;

        DumpstateListener(
                Executor executor,
                BugreportCallback callback,
                boolean isScreenshotRequested,
                boolean isConsentDeferred) {
            this.mExecutor = executor;
            this.mCallback = callback;
            this.mIsScreenshotRequested = isScreenshotRequested;
            this.mIsConsentDeferred = isConsentDeferred;
        }

        @Override // android.os.IDumpstateListener
        public void onProgress(final int progress) throws RemoteException {
            long identity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(
                        new Runnable() { // from class:
                                         // android.os.BugreportManager$DumpstateListener$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                BugreportManager.DumpstateListener.this.lambda$onProgress$0(
                                        progress);
                            }
                        });
            } finally {
                Binder.restoreCallingIdentity(identity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onProgress$0(int progress) {
            this.mCallback.onProgress(progress);
        }

        @Override // android.os.IDumpstateListener
        public void onError(final int errorCode) throws RemoteException {
            long identity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(
                        new Runnable() { // from class:
                                         // android.os.BugreportManager$DumpstateListener$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                BugreportManager.DumpstateListener.this.lambda$onError$1(errorCode);
                            }
                        });
            } finally {
                Binder.restoreCallingIdentity(identity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onError$1(int errorCode) {
            this.mCallback.onError(errorCode);
        }

        @Override // android.os.IDumpstateListener
        public void onFinished(final String bugreportFile) throws RemoteException {
            long identity = Binder.clearCallingIdentity();
            try {
                if (this.mIsConsentDeferred) {
                    this.mExecutor.execute(
                            new Runnable() { // from class:
                                             // android.os.BugreportManager$DumpstateListener$$ExternalSyntheticLambda2
                                @Override // java.lang.Runnable
                                public final void run() {
                                    BugreportManager.DumpstateListener.this.lambda$onFinished$2(
                                            bugreportFile);
                                }
                            });
                } else {
                    this.mExecutor.execute(
                            new Runnable() { // from class:
                                             // android.os.BugreportManager$DumpstateListener$$ExternalSyntheticLambda3
                                @Override // java.lang.Runnable
                                public final void run() {
                                    BugreportManager.DumpstateListener.this.lambda$onFinished$3();
                                }
                            });
                }
            } finally {
                Binder.restoreCallingIdentity(identity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onFinished$2(String bugreportFile) {
            this.mCallback.onFinished(bugreportFile);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onFinished$3() {
            this.mCallback.onFinished();
        }

        @Override // android.os.IDumpstateListener
        public void onScreenshotTaken(final boolean success) throws RemoteException {
            if (!this.mIsScreenshotRequested) {
                return;
            }
            Handler mainThreadHandler = new Handler(Looper.getMainLooper());
            mainThreadHandler.post(
                    new Runnable() { // from class:
                                     // android.os.BugreportManager$DumpstateListener$$ExternalSyntheticLambda5
                        @Override // java.lang.Runnable
                        public final void run() {
                            BugreportManager.DumpstateListener.this.lambda$onScreenshotTaken$4(
                                    success);
                        }
                    });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onScreenshotTaken$4(boolean success) {
            int message;
            if (success) {
                message = R.string.bugreport_screenshot_success_toast;
            } else {
                message = R.string.bugreport_screenshot_failure_toast;
            }
            Toast.makeText(BugreportManager.this.mContext, message, 1).show();
        }

        @Override // android.os.IDumpstateListener
        public void onUiIntensiveBugreportDumpsFinished() throws RemoteException {
            long identity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(
                        new Runnable() { // from class:
                                         // android.os.BugreportManager$DumpstateListener$$ExternalSyntheticLambda4
                            @Override // java.lang.Runnable
                            public final void run() {
                                BugreportManager.DumpstateListener.this
                                        .lambda$onUiIntensiveBugreportDumpsFinished$5();
                            }
                        });
            } finally {
                Binder.restoreCallingIdentity(identity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onUiIntensiveBugreportDumpsFinished$5() {
            this.mCallback.onEarlyReportFinished();
        }
    }
}
