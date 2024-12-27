package com.android.server.timezonedetector.location;

import android.content.Context;
import android.os.Handler;
import android.os.IBinder;
import android.service.timezone.ITimeZoneProviderManager;
import android.service.timezone.TimeZoneProviderEvent;
import android.util.IndentingPrintWriter;

import com.android.server.servicewatcher.CurrentUserServiceSupplier;
import com.android.server.servicewatcher.ServiceWatcher$ServiceListener;
import com.android.server.servicewatcher.ServiceWatcherImpl;
import com.android.server.timezonedetector.Dumpable;

import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class RealLocationTimeZoneProviderProxy
        implements ServiceWatcher$ServiceListener, Dumpable {
    public BinderLocationTimeZoneProvider.AnonymousClass1 mListener;
    public ManagerProxy mManagerProxy;
    public TimeZoneProviderRequest mRequest;
    public final ServiceWatcherImpl mServiceWatcher;
    public final Object mSharedLock;
    public final HandlerThreadingDomain mThreadingDomain;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ManagerProxy extends ITimeZoneProviderManager.Stub {
        public ManagerProxy() {}

        public final void onTimeZoneProviderEvent(
                final TimeZoneProviderEvent timeZoneProviderEvent) {
            synchronized (RealLocationTimeZoneProviderProxy.this.mSharedLock) {
                try {
                    final RealLocationTimeZoneProviderProxy realLocationTimeZoneProviderProxy =
                            RealLocationTimeZoneProviderProxy.this;
                    if (realLocationTimeZoneProviderProxy.mManagerProxy != this) {
                        return;
                    }
                    realLocationTimeZoneProviderProxy.mThreadingDomain.mHandler.post(
                            new Runnable() { // from class:
                                // com.android.server.timezonedetector.location.LocationTimeZoneProviderProxy$$ExternalSyntheticLambda0
                                /* JADX WARN: Code restructure failed: missing block: B:36:0x010c, code lost:

                                   r14 = 3;
                                */
                                @Override // java.lang.Runnable
                                /*
                                    Code decompiled incorrectly, please refer to instructions dump.
                                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                                */
                                public final void run() {
                                    /*
                                        Method dump skipped, instructions count: 360
                                        To view this dump change 'Code comments level' option to 'DEBUG'
                                    */
                                    throw new UnsupportedOperationException(
                                            "Method not decompiled:"
                                                + " com.android.server.timezonedetector.location.LocationTimeZoneProviderProxy$$ExternalSyntheticLambda0.run():void");
                                }
                            });
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public RealLocationTimeZoneProviderProxy(
            Context context,
            Handler handler,
            HandlerThreadingDomain handlerThreadingDomain,
            String str,
            String str2,
            boolean z) {
        Objects.requireNonNull(context);
        Objects.requireNonNull(handlerThreadingDomain);
        this.mThreadingDomain = handlerThreadingDomain;
        this.mSharedLock = handlerThreadingDomain.mLockObject;
        this.mManagerProxy = null;
        this.mRequest = TimeZoneProviderRequest.STOP_UPDATES;
        Objects.requireNonNull(str2);
        this.mServiceWatcher =
                new ServiceWatcherImpl(
                        context,
                        handler,
                        "RealLocationTimeZoneProviderProxy",
                        z
                                ? new CurrentUserServiceSupplier(
                                        context,
                                        str,
                                        str2,
                                        "android.permission.BIND_TIME_ZONE_PROVIDER_SERVICE",
                                        null,
                                        false)
                                : new CurrentUserServiceSupplier(
                                        context,
                                        str,
                                        str2,
                                        "android.permission.BIND_TIME_ZONE_PROVIDER_SERVICE",
                                        "android.permission.INSTALL_LOCATION_TIME_ZONE_PROVIDER_SERVICE",
                                        true),
                        this,
                        null);
    }

    @Override // com.android.server.timezonedetector.Dumpable
    public final void dump(IndentingPrintWriter indentingPrintWriter, String[] strArr) {
        synchronized (this.mSharedLock) {
            indentingPrintWriter.println("{RealLocationTimeZoneProviderProxy}");
            indentingPrintWriter.println("mRequest=" + this.mRequest);
            this.mServiceWatcher.dump(indentingPrintWriter);
        }
    }

    @Override // com.android.server.servicewatcher.ServiceWatcher$ServiceListener
    public final void onBind(
            IBinder iBinder, CurrentUserServiceSupplier.BoundServiceInfo boundServiceInfo) {
        this.mThreadingDomain.assertCurrentThread();
        synchronized (this.mSharedLock) {
            this.mManagerProxy = new ManagerProxy();
            this.mListener.onProviderBound();
            this.mServiceWatcher.runOnBinder(
                    new RealLocationTimeZoneProviderProxy$$ExternalSyntheticLambda0(
                            this.mRequest, this.mManagerProxy));
        }
    }

    @Override // com.android.server.servicewatcher.ServiceWatcher$ServiceListener
    public final void onUnbind() {
        this.mThreadingDomain.assertCurrentThread();
        synchronized (this.mSharedLock) {
            this.mManagerProxy = null;
            this.mListener.onProviderUnbound();
        }
    }
}
