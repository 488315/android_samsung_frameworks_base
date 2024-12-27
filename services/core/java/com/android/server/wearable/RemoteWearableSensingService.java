package com.android.server.wearable;

import android.app.wearable.IWearableSensingCallback;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.ParcelFileDescriptor;
import android.os.RemoteCallback;
import android.util.Slog;
import com.android.internal.hidden_from_bootclasspath.android.app.wearable.Flags;
import com.android.internal.infra.ServiceConnector;
import com.android.server.wearable.WearableSensingManagerPerUserService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
final class RemoteWearableSensingService extends ServiceConnector.Impl {
    public static final /* synthetic */ int $r8$clinit = 0;
    private SecureWearableConnectionContext mNextSecureConnectionContext;
    private final Object mSecureConnectionLock;
    private boolean mSecureConnectionProvided;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SecureWearableConnectionContext {
        public final ParcelFileDescriptor mSecureConnection;
        public final RemoteCallback mStatusCallback;
        public final IWearableSensingCallback mWearableSensingCallback;

        public SecureWearableConnectionContext(ParcelFileDescriptor parcelFileDescriptor, WearableSensingManagerPerUserService.AnonymousClass3 anonymousClass3, RemoteCallback remoteCallback) {
            this.mSecureConnection = parcelFileDescriptor;
            this.mWearableSensingCallback = anonymousClass3;
            this.mStatusCallback = remoteCallback;
        }
    }

    public RemoteWearableSensingService(Context context, ComponentName componentName, int i) {
        super(context, new Intent("android.service.wearable.WearableSensingService").setComponent(componentName), 67112960, i, new com.android.server.ambientcontext.RemoteWearableSensingService$$ExternalSyntheticLambda0());
        this.mSecureConnectionLock = new Object();
        this.mSecureConnectionProvided = false;
        connect();
    }

    public final void binderDied() {
        super.binderDied();
        synchronized (this.mSecureConnectionLock) {
            try {
                SecureWearableConnectionContext secureWearableConnectionContext = this.mNextSecureConnectionContext;
                if (secureWearableConnectionContext != null) {
                    ParcelFileDescriptor parcelFileDescriptor = secureWearableConnectionContext.mSecureConnection;
                    WearableSensingManagerPerUserService.AnonymousClass3 anonymousClass3 = secureWearableConnectionContext.mWearableSensingCallback;
                    RemoteCallback remoteCallback = secureWearableConnectionContext.mStatusCallback;
                    Slog.d("RemoteWearableSensingService", "Providing secure wearable connection.");
                    post(new RemoteWearableSensingService$$ExternalSyntheticLambda0(parcelFileDescriptor, anonymousClass3, remoteCallback, 1));
                    this.mNextSecureConnectionContext = null;
                } else {
                    this.mSecureConnectionProvided = false;
                    Slog.w("RemoteWearableSensingService", "Binder died but there is no secure wearable connection to provide.");
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final long getAutoDisconnectTimeoutMs() {
        return -1L;
    }

    public final void provideSecureConnection(ParcelFileDescriptor parcelFileDescriptor, WearableSensingManagerPerUserService.AnonymousClass3 anonymousClass3, RemoteCallback remoteCallback) {
        if (!Flags.enableRestartWssProcess()) {
            Slog.d("RemoteWearableSensingService", "FLAG_ENABLE_RESTART_WSS_PROCESS is disabled. Do not attempt to restart the WearableSensingService process");
            Slog.d("RemoteWearableSensingService", "Providing secure wearable connection.");
            post(new RemoteWearableSensingService$$ExternalSyntheticLambda0(parcelFileDescriptor, anonymousClass3, remoteCallback, 1));
            return;
        }
        synchronized (this.mSecureConnectionLock) {
            try {
                if (this.mNextSecureConnectionContext != null) {
                    Slog.i("RemoteWearableSensingService", "A new wearable connection is provided before the process restart triggered by the previous connection is complete. Discarding the previous connection.");
                    if (Flags.enableProvideWearableConnectionApi()) {
                        WearableSensingManagerPerUserService.notifyStatusCallback(7, this.mNextSecureConnectionContext.mStatusCallback);
                    }
                    this.mNextSecureConnectionContext = new SecureWearableConnectionContext(parcelFileDescriptor, anonymousClass3, remoteCallback);
                    return;
                }
                if (this.mSecureConnectionProvided) {
                    this.mNextSecureConnectionContext = new SecureWearableConnectionContext(parcelFileDescriptor, anonymousClass3, remoteCallback);
                    post(new RemoteWearableSensingService$$ExternalSyntheticLambda7(0));
                } else {
                    Slog.d("RemoteWearableSensingService", "Providing secure wearable connection.");
                    post(new RemoteWearableSensingService$$ExternalSyntheticLambda0(parcelFileDescriptor, anonymousClass3, remoteCallback, 1));
                    this.mSecureConnectionProvided = true;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
