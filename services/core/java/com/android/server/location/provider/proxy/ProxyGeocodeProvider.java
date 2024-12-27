package com.android.server.location.provider.proxy;

import android.R;
import android.content.Context;
import android.location.provider.ForwardGeocodeRequest;
import android.location.provider.IGeocodeCallback;
import android.location.provider.IGeocodeProvider;
import android.location.provider.ReverseGeocodeRequest;
import android.os.IBinder;
import android.os.RemoteException;

import com.android.server.FgThread;
import com.android.server.location.injector.Injector;
import com.android.server.servicewatcher.CurrentUserServiceSupplier;
import com.android.server.servicewatcher.ServiceWatcher$BinderOperation;
import com.android.server.servicewatcher.ServiceWatcherImpl;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ProxyGeocodeProvider {
    public final ServiceWatcherImpl mServiceWatcher;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.location.provider.proxy.ProxyGeocodeProvider$1, reason: invalid class name */
    public final class AnonymousClass1 implements ServiceWatcher$BinderOperation {
        public final /* synthetic */ int $r8$classId = 1;
        public final /* synthetic */ IGeocodeCallback val$callback;
        public final /* synthetic */ Object val$request;

        public AnonymousClass1(
                ForwardGeocodeRequest forwardGeocodeRequest, IGeocodeCallback iGeocodeCallback) {
            this.val$request = forwardGeocodeRequest;
            this.val$callback = iGeocodeCallback;
        }

        public AnonymousClass1(
                ReverseGeocodeRequest reverseGeocodeRequest, IGeocodeCallback iGeocodeCallback) {
            this.val$request = reverseGeocodeRequest;
            this.val$callback = iGeocodeCallback;
        }

        @Override // com.android.server.servicewatcher.ServiceWatcher$BinderOperation
        public final void onError(Throwable th) {
            switch (this.$r8$classId) {
                case 0:
                    try {
                        this.val$callback.onError(th.toString());
                        break;
                    } catch (RemoteException unused) {
                        return;
                    }
                default:
                    try {
                        this.val$callback.onError(th.toString());
                        break;
                    } catch (RemoteException unused2) {
                        return;
                    }
            }
        }

        @Override // com.android.server.servicewatcher.ServiceWatcher$BinderOperation
        public final void run(IBinder iBinder) {
            switch (this.$r8$classId) {
                case 0:
                    IGeocodeProvider.Stub.asInterface(iBinder)
                            .reverseGeocode(
                                    (ReverseGeocodeRequest) this.val$request, this.val$callback);
                    break;
                default:
                    IGeocodeProvider.Stub.asInterface(iBinder)
                            .forwardGeocode(
                                    (ForwardGeocodeRequest) this.val$request, this.val$callback);
                    break;
            }
        }
    }

    public ProxyGeocodeProvider(Context context, Injector injector) {
        this.mServiceWatcher =
                new ServiceWatcherImpl(
                        context,
                        FgThread.getHandler(),
                        "GeocoderProxy",
                        CurrentUserServiceSupplier.createFromConfig(
                                context,
                                "com.android.location.service.GeocodeProvider",
                                R.bool.config_enableMotionPrediction,
                                R.string.duration_minutes_relative_future),
                        null,
                        injector);
    }

    public ProxyGeocodeProvider(Context context, Injector injector, int i) {
        this.mServiceWatcher =
                new ServiceWatcherImpl(
                        context,
                        FgThread.getHandler(),
                        "GeocoderProxy",
                        CurrentUserServiceSupplier.createFromConfig(
                                context,
                                "com.android.location.service.GeocodeProvider",
                                R.bool.config_enableStylusPointerIcon,
                                R.string.duration_minutes_shortest),
                        null,
                        injector);
    }
}
