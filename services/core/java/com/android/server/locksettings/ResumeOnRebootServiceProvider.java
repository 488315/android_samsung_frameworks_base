package com.android.server.locksettings;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.os.UserHandle;
import android.provider.DeviceConfig;
import android.service.resumeonreboot.IResumeOnRebootService;
import android.util.Slog;
import com.android.internal.os.BackgroundThread;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ResumeOnRebootServiceProvider {
    public static final String PROVIDER_PACKAGE = DeviceConfig.getString("ota", "resume_on_reboot_service_package", "");
    public final Context mContext;
    public final PackageManager mPackageManager;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ResumeOnRebootServiceCallback implements RemoteCallback.OnResultListener {
        public Bundle mResult;
        public final CountDownLatch mResultLatch;

        public ResumeOnRebootServiceCallback(CountDownLatch countDownLatch) {
            this.mResultLatch = countDownLatch;
        }

        public final void onResult(Bundle bundle) {
            this.mResult = bundle;
            this.mResultLatch.countDown();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ResumeOnRebootServiceConnection {
        public IResumeOnRebootService mBinder;
        public final ComponentName mComponentName;
        public final Context mContext;
        public AnonymousClass1 mServiceConnection;

        public ResumeOnRebootServiceConnection(Context context, ComponentName componentName) {
            this.mContext = context;
            this.mComponentName = componentName;
        }

        public static void waitForLatch(CountDownLatch countDownLatch, long j, String str) {
            try {
                if (countDownLatch.await(j, TimeUnit.SECONDS)) {
                    return;
                }
                throw new TimeoutException("Latch wait for " + str + " elapsed");
            } catch (InterruptedException unused) {
                Thread.currentThread().interrupt();
                throw new RemoteException(XmlUtils$$ExternalSyntheticOutline0.m("Latch wait for ", str, " interrupted"));
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r4v0, types: [android.content.ServiceConnection, com.android.server.locksettings.ResumeOnRebootServiceProvider$ResumeOnRebootServiceConnection$1] */
        public final void bindToService(long j) {
            IResumeOnRebootService iResumeOnRebootService = this.mBinder;
            if (iResumeOnRebootService == null || !iResumeOnRebootService.asBinder().isBinderAlive()) {
                final CountDownLatch countDownLatch = new CountDownLatch(1);
                Intent intent = new Intent();
                intent.setComponent(this.mComponentName);
                ?? r4 = new ServiceConnection() { // from class: com.android.server.locksettings.ResumeOnRebootServiceProvider.ResumeOnRebootServiceConnection.1
                    @Override // android.content.ServiceConnection
                    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                        ResumeOnRebootServiceConnection.this.mBinder = IResumeOnRebootService.Stub.asInterface(iBinder);
                        countDownLatch.countDown();
                    }

                    @Override // android.content.ServiceConnection
                    public final void onServiceDisconnected(ComponentName componentName) {
                        ResumeOnRebootServiceConnection.this.mBinder = null;
                    }
                };
                this.mServiceConnection = r4;
                Context context = this.mContext;
                Handler handler = BackgroundThread.getHandler();
                UserHandle userHandle = UserHandle.SYSTEM;
                if (context.bindServiceAsUser(intent, r4, 67108865, handler, userHandle)) {
                    waitForLatch(countDownLatch, j, "serviceConnection");
                    return;
                }
                Slog.e("ResumeOnRebootServiceConnection", "Binding: " + this.mComponentName + " u" + userHandle + " failed.");
            }
        }
    }

    public ResumeOnRebootServiceProvider(Context context, PackageManager packageManager) {
        this.mContext = context;
        this.mPackageManager = packageManager;
    }
}
