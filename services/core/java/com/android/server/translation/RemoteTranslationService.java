package com.android.server.translation;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.IInterface;
import android.service.translation.ITranslationService;
import android.util.Slog;

import com.android.internal.infra.ServiceConnector;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
final class RemoteTranslationService extends ServiceConnector.Impl {
    public static final /* synthetic */ int $r8$clinit = 0;
    private final ComponentName mComponentName;
    private final long mIdleUnbindTimeoutMs;
    private final IBinder mRemoteCallback;
    private final int mRequestTimeoutMs;

    public RemoteTranslationService(
            Context context, ComponentName componentName, int i, IBinder iBinder) {
        super(
                context,
                new Intent("android.service.translation.TranslationService")
                        .setComponent(componentName),
                0,
                i,
                new RemoteTranslationService$$ExternalSyntheticLambda0());
        this.mIdleUnbindTimeoutMs = 0L;
        this.mRequestTimeoutMs = 5000;
        this.mComponentName = componentName;
        this.mRemoteCallback = iBinder;
        connect();
    }

    public final long getAutoDisconnectTimeoutMs() {
        return this.mIdleUnbindTimeoutMs;
    }

    public final void onServiceConnectionStatusChanged(IInterface iInterface, boolean z) {
        ITranslationService iTranslationService = (ITranslationService) iInterface;
        try {
            if (z) {
                iTranslationService.onConnected(this.mRemoteCallback);
            } else {
                iTranslationService.onDisconnected();
            }
        } catch (Exception e) {
            Slog.w(
                    "RemoteTranslationService",
                    "Exception calling onServiceConnectionStatusChanged(" + z + "): ",
                    e);
        }
    }
}
