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

    public ServiceProxySupplier(final Context context, final Class<?> cls) {
        this.options = new HashMap();
        this.supplier = new Supplier() { // from class: com.samsung.android.sume.core.service.ServiceProxySupplier$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return ServiceProxySupplier.this.m9613x1848a78a(cls, context);
            }
        };
    }

    /* renamed from: lambda$new$0$com-samsung-android-sume-core-service-ServiceProxySupplier, reason: not valid java name */
    /* synthetic */ ServiceProxy m9613x1848a78a(Class cls, Context context) {
        if (LocalService.class.isAssignableFrom(cls)) {
            return new LocalServiceProxy(context, cls, this.options);
        }
        return new RemoteServiceProxy(context, cls, this.options);
    }

    public ServiceProxySupplier(ServiceProxySupplier serviceProxySupplier) {
        this.options = serviceProxySupplier.options;
        this.supplier = serviceProxySupplier;
    }

    public ServiceProxySupplier(final Context context, final String str, final String str2) {
        this.options = new HashMap();
        this.supplier = new Supplier() { // from class: com.samsung.android.sume.core.service.ServiceProxySupplier$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                return ServiceProxySupplier.this.m9614x1e4c72e9(context, str, str2);
            }
        };
    }

    /* renamed from: lambda$new$1$com-samsung-android-sume-core-service-ServiceProxySupplier, reason: not valid java name */
    /* synthetic */ ServiceProxy m9614x1e4c72e9(Context context, String str, String str2) {
        return new RemoteServiceProxy(context, str, str2, this.options);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.function.Supplier
    public ServiceProxy get() {
        return this.supplier.get();
    }

    private ServiceProxySupplier addOption(int i) {
        this.options.put(Integer.valueOf(i), null);
        return this;
    }

    private ServiceProxySupplier addOption(int i, Object obj) {
        this.options.put(Integer.valueOf(i), obj);
        return this;
    }

    public ServiceProxySupplier asForeground(Class<?> cls) {
        ServiceProxySupplier serviceProxySupplier;
        Def.require(Activity.class.isAssignableFrom(cls));
        if (this instanceof PlaceHolderImpl) {
            serviceProxySupplier = new PlaceHolderImpl((PlaceHolderImpl) this);
        } else {
            serviceProxySupplier = new ServiceProxySupplier(this);
        }
        return serviceProxySupplier.addOption(0, cls);
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

    static class PlaceHolderImpl extends ServiceProxySupplier implements PlaceHolder<ServiceProxySupplier> {
        private Context context;
        private String packageName;
        private String serviceName;

        @Override // com.samsung.android.sume.core.functional.PlaceHolder
        public boolean isEmpty() {
            return false;
        }

        @Override // com.samsung.android.sume.core.functional.PlaceHolder
        public boolean isNotEmpty() {
            return false;
        }

        @Override // com.samsung.android.sume.core.service.ServiceProxySupplier, java.util.function.Supplier
        public /* bridge */ /* synthetic */ ServiceProxy get() {
            return super.get();
        }

        PlaceHolderImpl(Context context) {
            this.context = context;
        }

        PlaceHolderImpl(PlaceHolderImpl placeHolderImpl) {
            super(placeHolderImpl);
            this.context = placeHolderImpl.context;
        }

        @Override // com.samsung.android.sume.core.functional.PlaceHolder
        public void put(ServiceProxySupplier serviceProxySupplier) {
            throw new UnsupportedOperationException();
        }

        @Override // com.samsung.android.sume.core.functional.PlaceHolder
        public PlaceHolder<ServiceProxySupplier> setParameters(Object... objArr) {
            Def.require(objArr.length == 2);
            this.packageName = (String) objArr[0];
            this.serviceName = (String) objArr[1];
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.samsung.android.sume.core.functional.PlaceHolder
        public ServiceProxySupplier reset() {
            Def.require((this.packageName == null || this.serviceName == null) ? false : true);
            ServiceProxySupplier of = ServiceProxy.of(this.context, this.packageName, this.serviceName);
            of.options.putAll(this.options);
            this.context = null;
            return of;
        }
    }
}
