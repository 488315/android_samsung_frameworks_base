package com.android.systemui;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import androidx.core.app.AppComponentFactory;
import com.android.systemui.dagger.ContextComponentHelper;
import com.android.systemui.dagger.ContextComponentResolver;
import com.android.systemui.dagger.SysUIComponent;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutionException;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public abstract class SystemUIAppComponentFactoryBase extends AppComponentFactory {
    public static final Companion Companion = new Companion(null);
    public static SystemUIInitializer systemUIInitializer;
    public ContextComponentHelper componentHelper;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public interface ContextAvailableCallback {
        SystemUIInitializer onContextAvailable(Context context);
    }

    public interface ContextInitializer {
        void setContextAvailableCallback(ContextAvailableCallback contextAvailableCallback);
    }

    public static final SystemUIInitializer access$createSystemUIInitializerInternal(SystemUIAppComponentFactoryBase systemUIAppComponentFactoryBase, Context context) {
        systemUIAppComponentFactoryBase.getClass();
        SystemUIInitializer systemUIInitializer2 = systemUIInitializer;
        if (systemUIInitializer2 != null) {
            return systemUIInitializer2;
        }
        SystemUIInitializerImpl systemUIInitializerImplCreateSystemUIInitializer = systemUIAppComponentFactoryBase.createSystemUIInitializer(context.getApplicationContext());
        try {
            systemUIInitializerImplCreateSystemUIInitializer.init(false);
            systemUIInitializerImplCreateSystemUIInitializer.getSysUIComponent().inject(systemUIAppComponentFactoryBase);
            systemUIInitializer = systemUIInitializerImplCreateSystemUIInitializer;
            return systemUIInitializerImplCreateSystemUIInitializer;
        } catch (InterruptedException e) {
            throw new RuntimeException("Failed to initialize SysUI", e);
        } catch (ExecutionException e2) {
            throw new RuntimeException("Failed to initialize SysUI", e2);
        }
    }

    public abstract SystemUIInitializerImpl createSystemUIInitializer(Context context);

    @Override // androidx.core.app.AppComponentFactory
    public final Activity instantiateActivityCompat(ClassLoader classLoader, String str, Intent intent) {
        SystemUIInitializer systemUIInitializer2;
        SysUIComponent sysUIComponent;
        if (this.componentHelper == null && (systemUIInitializer2 = systemUIInitializer) != null && (sysUIComponent = systemUIInitializer2.getSysUIComponent()) != null) {
            sysUIComponent.inject(this);
        }
        ContextComponentHelper contextComponentHelper = this.componentHelper;
        if (contextComponentHelper == null) {
            contextComponentHelper = null;
        }
        Activity activity = (Activity) ContextComponentResolver.resolve(str, ((ContextComponentResolver) contextComponentHelper).mActivityCreators);
        return activity == null ? super.instantiateActivityCompat(classLoader, str, intent) : activity;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // androidx.core.app.AppComponentFactory
    public final Application instantiateApplicationCompat(ClassLoader classLoader, String str) {
        Application applicationInstantiateApplicationCompat = super.instantiateApplicationCompat(classLoader, str);
        if (!(applicationInstantiateApplicationCompat instanceof ContextInitializer)) {
            throw new RuntimeException("App must implement ContextInitializer");
        }
        ((ContextInitializer) applicationInstantiateApplicationCompat).setContextAvailableCallback(new ContextAvailableCallback() { // from class: com.android.systemui.SystemUIAppComponentFactoryBase.instantiateApplicationCompat.1
            @Override // com.android.systemui.SystemUIAppComponentFactoryBase.ContextAvailableCallback
            public final SystemUIInitializer onContextAvailable(Context context) {
                return SystemUIAppComponentFactoryBase.access$createSystemUIInitializerInternal(SystemUIAppComponentFactoryBase.this, context);
            }
        });
        return applicationInstantiateApplicationCompat;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // androidx.core.app.AppComponentFactory
    public final ContentProvider instantiateProviderCompat(ClassLoader classLoader, String str) {
        final ContentProvider contentProviderInstantiateProviderCompat = super.instantiateProviderCompat(classLoader, str);
        if (contentProviderInstantiateProviderCompat instanceof ContextInitializer) {
            ((ContextInitializer) contentProviderInstantiateProviderCompat).setContextAvailableCallback(new ContextAvailableCallback() { // from class: com.android.systemui.SystemUIAppComponentFactoryBase.instantiateProviderCompat.1
                @Override // com.android.systemui.SystemUIAppComponentFactoryBase.ContextAvailableCallback
                public final SystemUIInitializer onContextAvailable(Context context) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    SystemUIInitializer systemUIInitializerAccess$createSystemUIInitializerInternal = SystemUIAppComponentFactoryBase.access$createSystemUIInitializerInternal(SystemUIAppComponentFactoryBase.this, context);
                    SysUIComponent sysUIComponent = systemUIInitializerAccess$createSystemUIInitializerInternal.getSysUIComponent();
                    try {
                        sysUIComponent.getClass().getMethod("inject", contentProviderInstantiateProviderCompat.getClass()).invoke(sysUIComponent, contentProviderInstantiateProviderCompat);
                        return systemUIInitializerAccess$createSystemUIInitializerInternal;
                    } catch (IllegalAccessException e) {
                        Log.w("AppComponentFactory", "No injector for class: " + contentProviderInstantiateProviderCompat.getClass(), e);
                        return systemUIInitializerAccess$createSystemUIInitializerInternal;
                    } catch (NoSuchMethodException e2) {
                        Log.w("AppComponentFactory", "No injector for class: " + contentProviderInstantiateProviderCompat.getClass(), e2);
                        return systemUIInitializerAccess$createSystemUIInitializerInternal;
                    } catch (InvocationTargetException e3) {
                        Log.w("AppComponentFactory", "No injector for class: " + contentProviderInstantiateProviderCompat.getClass(), e3);
                        return systemUIInitializerAccess$createSystemUIInitializerInternal;
                    }
                }
            });
        }
        return contentProviderInstantiateProviderCompat;
    }

    @Override // androidx.core.app.AppComponentFactory
    public final BroadcastReceiver instantiateReceiverCompat(ClassLoader classLoader, String str, Intent intent) {
        SystemUIInitializer systemUIInitializer2;
        SysUIComponent sysUIComponent;
        if (this.componentHelper == null && (systemUIInitializer2 = systemUIInitializer) != null && (sysUIComponent = systemUIInitializer2.getSysUIComponent()) != null) {
            sysUIComponent.inject(this);
        }
        ContextComponentHelper contextComponentHelper = this.componentHelper;
        if (contextComponentHelper == null) {
            contextComponentHelper = null;
        }
        BroadcastReceiver broadcastReceiver = (BroadcastReceiver) ContextComponentResolver.resolve(str, ((ContextComponentResolver) contextComponentHelper).mBroadcastReceiverCreators);
        return broadcastReceiver == null ? super.instantiateReceiverCompat(classLoader, str, intent) : broadcastReceiver;
    }

    @Override // androidx.core.app.AppComponentFactory
    public final Service instantiateServiceCompat(ClassLoader classLoader, String str, Intent intent) {
        SystemUIInitializer systemUIInitializer2;
        SysUIComponent sysUIComponent;
        if (this.componentHelper == null && (systemUIInitializer2 = systemUIInitializer) != null && (sysUIComponent = systemUIInitializer2.getSysUIComponent()) != null) {
            sysUIComponent.inject(this);
        }
        ContextComponentHelper contextComponentHelper = this.componentHelper;
        if (contextComponentHelper == null) {
            contextComponentHelper = null;
        }
        Service service = (Service) ContextComponentResolver.resolve(str, ((ContextComponentResolver) contextComponentHelper).mServiceCreators);
        return service == null ? super.instantiateServiceCompat(classLoader, str, intent) : service;
    }
}
