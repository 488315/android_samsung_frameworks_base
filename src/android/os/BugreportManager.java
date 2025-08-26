package android.os;

import android.annotation.SystemApi;
import android.app.ActivityManager;
import android.content.Context;
import android.os.IDumpstateListener;
import android.util.Log;
import android.widget.Toast;
import com.android.internal.R;
import com.android.internal.util.Preconditions;
import java.io.File;
import java.io.FileNotFoundException;
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

    /* JADX WARN: Removed duplicated region for block: B:40:0x0093  */
    @SystemApi
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void startBugreport(ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2, BugreportParams bugreportParams, Executor executor, BugreportCallback bugreportCallback) throws Throwable {
        ParcelFileDescriptor parcelFileDescriptorOpen;
        try {
            try {
                Preconditions.checkNotNull(parcelFileDescriptor);
                Preconditions.checkNotNull(bugreportParams);
                Preconditions.checkNotNull(executor);
                Preconditions.checkNotNull(bugreportCallback);
                boolean z = (bugreportParams.getFlags() & 2) != 0;
                boolean z2 = parcelFileDescriptor2 != null || z;
                parcelFileDescriptorOpen = parcelFileDescriptor2 == null ? ParcelFileDescriptor.open(new File("/dev/null"), 268435456) : parcelFileDescriptor2;
                try {
                    boolean z3 = z2;
                    this.mBinder.startBugreport(-1, this.mContext.getOpPackageName(), parcelFileDescriptor.getFileDescriptor(), parcelFileDescriptorOpen.getFileDescriptor(), bugreportParams.getMode(), bugreportParams.getFlags(), new DumpstateListener(executor, bugreportCallback, z3, z), z3, false);
                    IoUtils.closeQuietly(parcelFileDescriptor);
                    if (parcelFileDescriptorOpen != null) {
                        IoUtils.closeQuietly(parcelFileDescriptorOpen);
                    }
                } catch (RemoteException e) {
                    e = e;
                    throw e.rethrowFromSystemServer();
                } catch (FileNotFoundException e2) {
                    e = e2;
                    Log.wtf(TAG, "Not able to find /dev/null file: ", e);
                    IoUtils.closeQuietly(parcelFileDescriptor);
                    if (parcelFileDescriptorOpen != null) {
                        IoUtils.closeQuietly(parcelFileDescriptorOpen);
                    }
                }
            } catch (Throwable th) {
                th = th;
                IoUtils.closeQuietly(parcelFileDescriptor);
                if (parcelFileDescriptor2 != null) {
                    IoUtils.closeQuietly(parcelFileDescriptor2);
                }
                throw th;
            }
        } catch (RemoteException e3) {
            e = e3;
        } catch (FileNotFoundException e4) {
            e = e4;
            parcelFileDescriptorOpen = parcelFileDescriptor2;
        } catch (Throwable th2) {
            th = th2;
            IoUtils.closeQuietly(parcelFileDescriptor);
            if (parcelFileDescriptor2 != null) {
            }
            throw th;
        }
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

    public void startConnectivityBugreport(ParcelFileDescriptor parcelFileDescriptor, Executor executor, BugreportCallback bugreportCallback) throws Throwable {
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
        String string;
        String string2 = null;
        if (charSequence == null) {
            string = null;
        } else {
            try {
                string = charSequence.toString();
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        if (charSequence2 != null) {
            string2 = charSequence2.toString();
        }
        ActivityManager.getService().requestBugReportWithDescription(string, string2, bugreportParams.getMode());
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
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new Runnable() { // from class: android.os.BugreportManager$DumpstateListener$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onProgress$0(i);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onProgress$0(int i) {
            this.mCallback.onProgress(i);
        }

        @Override // android.os.IDumpstateListener
        public void onError(final int i) throws RemoteException {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new Runnable() { // from class: android.os.BugreportManager$DumpstateListener$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onError$1(i);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onError$1(int i) {
            this.mCallback.onError(i);
        }

        @Override // android.os.IDumpstateListener
        public void onFinished(final String str) throws RemoteException {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                if (this.mIsConsentDeferred) {
                    this.mExecutor.execute(new Runnable() { // from class: android.os.BugreportManager$DumpstateListener$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$onFinished$2(str);
                        }
                    });
                } else {
                    this.mExecutor.execute(new Runnable() { // from class: android.os.BugreportManager$DumpstateListener$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$onFinished$3();
                        }
                    });
                }
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
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
                        this.f$0.lambda$onScreenshotTaken$4(z);
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
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new Runnable() { // from class: android.os.BugreportManager$DumpstateListener$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onUiIntensiveBugreportDumpsFinished$5();
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onUiIntensiveBugreportDumpsFinished$5() {
            this.mCallback.onEarlyReportFinished();
        }
    }
}
