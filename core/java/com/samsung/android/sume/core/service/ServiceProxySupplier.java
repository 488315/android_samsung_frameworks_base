package com.samsung.android.sume.core.service;

import android.app.Activity;
import android.content.Context;

import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.functional.PlaceHolder;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/* loaded from: classes6.dex */
public class ServiceProxySupplier implements Supplier<ServiceProxy> {
    private static final String TAG = Def.tagOf((Class<?>) ServiceProxySupplier.class);
    protected final Map<Integer, Object> options;
    private final Supplier<ServiceProxy> supplier;

    ServiceProxySupplier() {
        this.options = new HashMap();
        this.supplier = null;
    }

    public ServiceProxySupplier(final Context context, final Class<?> clazz) {
        this.options = new HashMap();
        this.supplier =
                new Supplier() { // from class:
                                 // com.samsung.android.sume.core.service.ServiceProxySupplier$$ExternalSyntheticLambda0
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return ServiceProxySupplier.this.m9212x1848a78a(clazz, context);
                    }
                };
    }

    /* renamed from: lambda$new$0$com-samsung-android-sume-core-service-ServiceProxySupplier, reason: not valid java name */
    /* synthetic */ ServiceProxy m9212x1848a78a(Class clazz, Context context) {
        if (LocalService.class.isAssignableFrom(clazz)) {
            return new LocalServiceProxy(context, clazz, this.options);
        }
        return new RemoteServiceProxy(context, clazz, this.options);
    }

    public ServiceProxySupplier(ServiceProxySupplier supplier) {
        this.options = supplier.options;
        this.supplier = supplier;
    }

    public ServiceProxySupplier(
            final Context context, final String packageName, final String serviceName) {
        this.options = new HashMap();
        this.supplier =
                new Supplier() { // from class:
                                 // com.samsung.android.sume.core.service.ServiceProxySupplier$$ExternalSyntheticLambda1
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return ServiceProxySupplier.this.m9213x1e4c72e9(
                                context, packageName, serviceName);
                    }
                };
    }

    /* renamed from: lambda$new$1$com-samsung-android-sume-core-service-ServiceProxySupplier, reason: not valid java name */
    /* synthetic */ ServiceProxy m9213x1e4c72e9(
            Context context, String packageName, String serviceName) {
        return new RemoteServiceProxy(context, packageName, serviceName, this.options);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.function.Supplier
    public ServiceProxy get() {
        return this.supplier.get();
    }

    private ServiceProxySupplier addOption(int option) {
        this.options.put(Integer.valueOf(option), null);
        return this;
    }

    private ServiceProxySupplier addOption(int option, Object data) {
        this.options.put(Integer.valueOf(option), data);
        return this;
    }

    public ServiceProxySupplier asForeground(Class<?> activityClass) {
        ServiceProxySupplier serviceProxySupplier;
        Def.require(Activity.class.isAssignableFrom(activityClass));
        if (this instanceof PlaceHolderImpl) {
            serviceProxySupplier = new PlaceHolderImpl((PlaceHolderImpl) this);
        } else {
            serviceProxySupplier = new ServiceProxySupplier(this);
        }
        return serviceProxySupplier.addOption(0, activityClass);
    }

    public ServiceProxySupplier asDaemon() {
        ServiceProxySupplier serviceProxySupplier;
        if (this instanceof PlaceHolderImpl) {
            serviceProxySupplier = new PlaceHolderImpl((PlaceHolderImpl) this);
        } else {
            serviceProxySupplier = new ServiceProxySupplier(this);
        }
        return serviceProxySupplier.addOption(1);
    }

    static class PlaceHolderImpl extends ServiceProxySupplier
            implements PlaceHolder<ServiceProxySupplier> {
        private Context context;
        private String packageName;
        private String serviceName;

        @Override // com.samsung.android.sume.core.service.ServiceProxySupplier,
                  // java.util.function.Supplier
        public /* bridge */ /* synthetic */ ServiceProxy get() {
            return super.get();
        }

        PlaceHolderImpl(Context context) {
            this.context = context;
        }

        PlaceHolderImpl(PlaceHolderImpl other) {
            super(other);
            this.context = other.context;
        }

        @Override // com.samsung.android.sume.core.functional.PlaceHolder
        public void put(ServiceProxySupplier instance) {
            throw new UnsupportedOperationException();
        }

        @Override // com.samsung.android.sume.core.functional.PlaceHolder
        public PlaceHolder<ServiceProxySupplier> setParameters(Object... args) {
            Def.require(args.length == 2);
            this.packageName = (String) args[0];
            this.serviceName = (String) args[1];
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.samsung.android.sume.core.functional.PlaceHolder
        public ServiceProxySupplier reset() {
            Def.require((this.packageName == null || this.serviceName == null) ? false : true);
            ServiceProxySupplier serviceProxySupplier =
                    ServiceProxy.of(this.context, this.packageName, this.serviceName);
            serviceProxySupplier.options.putAll(this.options);
            this.context = null;
            return serviceProxySupplier;
        }

        @Override // com.samsung.android.sume.core.functional.PlaceHolder
        public boolean isEmpty() {
            return false;
        }

        @Override // com.samsung.android.sume.core.functional.PlaceHolder
        public boolean isNotEmpty() {
            return false;
        }
    }
}
