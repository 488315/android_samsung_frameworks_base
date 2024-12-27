package com.android.server.location.provider.proxy;

import android.content.Context;
import android.location.Location;
import android.location.LocationResult;
import android.location.provider.ILocationProvider;
import android.location.provider.ILocationProviderManager;
import android.location.provider.ProviderProperties;
import android.location.provider.ProviderRequest;
import android.location.util.identity.CallerIdentity;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import com.android.internal.util.ConcurrentUtils;
import com.android.server.FgThread;
import com.android.server.location.LocationServiceThread;
import com.android.server.location.injector.Injector;
import com.android.server.location.provider.AbstractLocationProvider;
import com.android.server.location.provider.AbstractLocationProvider$$ExternalSyntheticLambda0;
import com.android.server.location.provider.AbstractLocationProvider$$ExternalSyntheticLambda1;
import com.android.server.location.provider.LocationProviderManager$Registration$$ExternalSyntheticLambda0;
import com.android.server.servicewatcher.CurrentUserServiceSupplier;
import com.android.server.servicewatcher.ServiceWatcher$BinderOperation;
import com.android.server.servicewatcher.ServiceWatcher$ServiceListener;
import com.android.server.servicewatcher.ServiceWatcherImpl;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.UnaryOperator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ProxyLocationProvider extends AbstractLocationProvider implements ServiceWatcher$ServiceListener {
    public CurrentUserServiceSupplier.BoundServiceInfo mBoundServiceInfo;
    public final ArrayList mFlushListeners;
    public final Object mLock;
    public final String mName;
    public Proxy mProxy;
    public volatile ProviderRequest mRequest;
    public AnonymousClass1 mResetter;
    public final ServiceWatcherImpl mServiceWatcher;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Proxy extends ILocationProviderManager.Stub {
        public Proxy() {
        }

        public final void onFlushComplete() {
            synchronized (ProxyLocationProvider.this.mLock) {
                try {
                    ProxyLocationProvider proxyLocationProvider = ProxyLocationProvider.this;
                    if (proxyLocationProvider.mProxy != this) {
                        return;
                    }
                    Runnable runnable = !proxyLocationProvider.mFlushListeners.isEmpty() ? (Runnable) ProxyLocationProvider.this.mFlushListeners.remove(0) : null;
                    if (runnable != null) {
                        runnable.run();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void onInitialize(final boolean z, final ProviderProperties providerProperties, String str) {
            synchronized (ProxyLocationProvider.this.mLock) {
                try {
                    ProxyLocationProvider proxyLocationProvider = ProxyLocationProvider.this;
                    if (proxyLocationProvider.mProxy != this) {
                        return;
                    }
                    if (proxyLocationProvider.mResetter != null) {
                        LocationServiceThread.getHandler().removeCallbacks(ProxyLocationProvider.this.mResetter);
                        ProxyLocationProvider.this.mResetter = null;
                    }
                    String[] strArr = new String[0];
                    Bundle bundle = ProxyLocationProvider.this.mBoundServiceInfo.mMetadata;
                    if (bundle != null) {
                        String string = bundle.getString("android:location_allow_listed_tags");
                        if (!TextUtils.isEmpty(string)) {
                            strArr = string.split(";");
                            Log.i("LocationManagerService", ProxyLocationProvider.this.mName + " provider loaded extra attribution tags: " + Arrays.toString(strArr));
                        }
                    }
                    final ArraySet arraySet = new ArraySet(strArr);
                    final CallerIdentity fromBinderUnsafe = CallerIdentity.fromBinderUnsafe(ProxyLocationProvider.this.mBoundServiceInfo.mComponentName.getPackageName(), str);
                    ProxyLocationProvider.this.setState(new UnaryOperator() { // from class: com.android.server.location.provider.proxy.ProxyLocationProvider$Proxy$$ExternalSyntheticLambda0
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            boolean z2 = z;
                            ProviderProperties providerProperties2 = providerProperties;
                            CallerIdentity callerIdentity = fromBinderUnsafe;
                            ArraySet arraySet2 = arraySet;
                            AbstractLocationProvider.State state = AbstractLocationProvider.State.EMPTY_STATE;
                            if (z2 != state.allowed) {
                                state = new AbstractLocationProvider.State(z2, state.properties, state.identity, state.extraAttributionTags);
                            }
                            AbstractLocationProvider.State withProperties = state.withProperties(providerProperties2);
                            if (!Objects.equals(callerIdentity, withProperties.identity)) {
                                withProperties = new AbstractLocationProvider.State(withProperties.allowed, withProperties.properties, callerIdentity, withProperties.extraAttributionTags);
                            }
                            if (arraySet2.equals(withProperties.extraAttributionTags)) {
                                return withProperties;
                            }
                            return new AbstractLocationProvider.State(withProperties.allowed, withProperties.properties, withProperties.identity, arraySet2);
                        }
                    });
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void onReportLocation(Location location) {
            synchronized (ProxyLocationProvider.this.mLock) {
                try {
                    ProxyLocationProvider proxyLocationProvider = ProxyLocationProvider.this;
                    if (proxyLocationProvider.mProxy != this) {
                        return;
                    }
                    proxyLocationProvider.reportLocation(LocationResult.wrap(new Location[]{location}));
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void onReportLocations(List list) {
            synchronized (ProxyLocationProvider.this.mLock) {
                try {
                    ProxyLocationProvider proxyLocationProvider = ProxyLocationProvider.this;
                    if (proxyLocationProvider.mProxy != this) {
                        return;
                    }
                    proxyLocationProvider.reportLocation(LocationResult.wrap(list));
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void onSetAllowed(boolean z) {
            synchronized (ProxyLocationProvider.this.mLock) {
                try {
                    ProxyLocationProvider proxyLocationProvider = ProxyLocationProvider.this;
                    if (proxyLocationProvider.mProxy != this) {
                        return;
                    }
                    proxyLocationProvider.setState(new AbstractLocationProvider$$ExternalSyntheticLambda0(z));
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void onSetProperties(ProviderProperties providerProperties) {
            synchronized (ProxyLocationProvider.this.mLock) {
                try {
                    ProxyLocationProvider proxyLocationProvider = ProxyLocationProvider.this;
                    if (proxyLocationProvider.mProxy != this) {
                        return;
                    }
                    proxyLocationProvider.getClass();
                    proxyLocationProvider.setState(new AbstractLocationProvider$$ExternalSyntheticLambda1(0, providerProperties));
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public ProxyLocationProvider(Context context, String str, String str2, int i, int i2, Injector injector) {
        super(ConcurrentUtils.DIRECT_EXECUTOR, null, null, Collections.emptySet());
        this.mLock = new Object();
        this.mFlushListeners = new ArrayList(0);
        this.mServiceWatcher = new ServiceWatcherImpl(context, FgThread.getHandler(), str, CurrentUserServiceSupplier.createFromConfig(context, str2, i, i2), this, injector);
        this.mName = str;
        this.mProxy = null;
        this.mRequest = ProviderRequest.EMPTY_REQUEST;
    }

    public static ProxyLocationProvider create(Context context, String str, String str2, int i, int i2, Injector injector) {
        ProxyLocationProvider proxyLocationProvider = new ProxyLocationProvider(context, str, str2, i, i2, injector);
        if (proxyLocationProvider.mServiceWatcher.checkServiceResolves()) {
            return proxyLocationProvider;
        }
        return null;
    }

    @Override // com.android.server.location.provider.AbstractLocationProvider
    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        this.mServiceWatcher.dump(printWriter);
    }

    @Override // com.android.server.servicewatcher.ServiceWatcher$ServiceListener
    public final void onBind(IBinder iBinder, CurrentUserServiceSupplier.BoundServiceInfo boundServiceInfo) {
        ILocationProvider asInterface = ILocationProvider.Stub.asInterface(iBinder);
        synchronized (this.mLock) {
            try {
                Proxy proxy = new Proxy();
                this.mProxy = proxy;
                this.mBoundServiceInfo = boundServiceInfo;
                asInterface.setLocationProviderManager(proxy);
                ProviderRequest providerRequest = this.mRequest;
                if (!providerRequest.equals(ProviderRequest.EMPTY_REQUEST)) {
                    asInterface.setRequest(providerRequest);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.location.provider.AbstractLocationProvider
    public final void onExtraCommand(int i, final String str, final Bundle bundle, int i2) {
        this.mServiceWatcher.runOnBinder(new ServiceWatcher$BinderOperation() { // from class: com.android.server.location.provider.proxy.ProxyLocationProvider$$ExternalSyntheticLambda0
            @Override // com.android.server.servicewatcher.ServiceWatcher$BinderOperation
            public final void run(IBinder iBinder) {
                ILocationProvider.Stub.asInterface(iBinder).sendExtraCommand(str, bundle);
            }
        });
    }

    @Override // com.android.server.location.provider.AbstractLocationProvider
    public final void onFlush(final LocationProviderManager$Registration$$ExternalSyntheticLambda0 locationProviderManager$Registration$$ExternalSyntheticLambda0) {
        this.mServiceWatcher.runOnBinder(new ServiceWatcher$BinderOperation() { // from class: com.android.server.location.provider.proxy.ProxyLocationProvider.2
            @Override // com.android.server.servicewatcher.ServiceWatcher$BinderOperation
            public final void onError(Throwable th) {
                synchronized (ProxyLocationProvider.this.mLock) {
                    ProxyLocationProvider.this.mFlushListeners.remove(locationProviderManager$Registration$$ExternalSyntheticLambda0);
                }
                locationProviderManager$Registration$$ExternalSyntheticLambda0.run();
            }

            @Override // com.android.server.servicewatcher.ServiceWatcher$BinderOperation
            public final void run(IBinder iBinder) {
                ILocationProvider asInterface = ILocationProvider.Stub.asInterface(iBinder);
                synchronized (ProxyLocationProvider.this.mLock) {
                    ProxyLocationProvider.this.mFlushListeners.add(locationProviderManager$Registration$$ExternalSyntheticLambda0);
                }
                asInterface.flush();
            }
        });
    }

    @Override // com.android.server.location.provider.AbstractLocationProvider
    public final void onSetRequest(final ProviderRequest providerRequest) {
        this.mRequest = providerRequest;
        this.mServiceWatcher.runOnBinder(new ServiceWatcher$BinderOperation() { // from class: com.android.server.location.provider.proxy.ProxyLocationProvider$$ExternalSyntheticLambda1
            @Override // com.android.server.servicewatcher.ServiceWatcher$BinderOperation
            public final void run(IBinder iBinder) {
                ILocationProvider.Stub.asInterface(iBinder).setRequest(providerRequest);
            }
        });
    }

    @Override // com.android.server.location.provider.AbstractLocationProvider
    public final void onStart() {
        this.mServiceWatcher.register();
    }

    @Override // com.android.server.location.provider.AbstractLocationProvider
    public final void onStop() {
        this.mServiceWatcher.unregister();
    }

    /* JADX WARN: Type inference failed for: r1v5, types: [com.android.server.location.provider.proxy.ProxyLocationProvider$1] */
    @Override // com.android.server.servicewatcher.ServiceWatcher$ServiceListener
    public final void onUnbind() {
        int i;
        Runnable[] runnableArr;
        synchronized (this.mLock) {
            try {
                this.mProxy = null;
                this.mBoundServiceInfo = null;
                if (this.mResetter == null) {
                    this.mResetter = new Runnable() { // from class: com.android.server.location.provider.proxy.ProxyLocationProvider.1
                        @Override // java.lang.Runnable
                        public final void run() {
                            synchronized (ProxyLocationProvider.this.mLock) {
                                try {
                                    ProxyLocationProvider proxyLocationProvider = ProxyLocationProvider.this;
                                    if (proxyLocationProvider.mResetter == this) {
                                        proxyLocationProvider.setState(new ProxyLocationProvider$1$$ExternalSyntheticLambda0());
                                    }
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                        }
                    };
                    LocationServiceThread.getHandler().postDelayed(this.mResetter, 10000L);
                }
                runnableArr = (Runnable[]) this.mFlushListeners.toArray(new Runnable[0]);
                this.mFlushListeners.clear();
            } catch (Throwable th) {
                throw th;
            }
        }
        for (Runnable runnable : runnableArr) {
            runnable.run();
        }
    }
}
