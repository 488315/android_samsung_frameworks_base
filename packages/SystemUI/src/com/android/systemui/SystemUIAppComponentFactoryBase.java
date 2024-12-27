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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public abstract class SystemUIAppComponentFactoryBase extends AppComponentFactory {
    public static final Companion Companion = new Companion(null);
    public static SystemUIInitializer systemUIInitializer;
    public ContextComponentHelper componentHelper;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface ContextAvailableCallback {
        SystemUIInitializer onContextAvailable(Context context);
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface ContextInitializer {
        void setContextAvailableCallback(ContextAvailableCallback contextAvailableCallback);
    }

    public static final SystemUIInitializer access$createSystemUIInitializerInternal(SystemUIAppComponentFactoryBase systemUIAppComponentFactoryBase, Context context) {
        systemUIAppComponentFactoryBase.getClass();
        SystemUIInitializer systemUIInitializer2 = systemUIInitializer;
        if (systemUIInitializer2 == null) {
            systemUIInitializer2 = systemUIAppComponentFactoryBase.createSystemUIInitializer(context.getApplicationContext());
            try {
                systemUIInitializer2.init(false);
                systemUIInitializer2.getSysUIComponent().inject(systemUIAppComponentFactoryBase);
                systemUIInitializer = systemUIInitializer2;
            } catch (InterruptedException e) {
                throw new RuntimeException("Failed to initialize SysUI", e);
            } catch (ExecutionException e2) {
                throw new RuntimeException("Failed to initialize SysUI", e2);
            }
        }
        return systemUIInitializer2;
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
        Application instantiateApplicationCompat = super.instantiateApplicationCompat(classLoader, str);
        if (!(instantiateApplicationCompat instanceof ContextInitializer)) {
            throw new RuntimeException("App must implement ContextInitializer");
        }
        ((ContextInitializer) instantiateApplicationCompat).setContextAvailableCallback(new ContextAvailableCallback() { // from class: com.android.systemui.SystemUIAppComponentFactoryBase$instantiateApplicationCompat$1
            @Override // com.android.systemui.SystemUIAppComponentFactoryBase.ContextAvailableCallback
            public final SystemUIInitializer onContextAvailable(Context context) {
                return SystemUIAppComponentFactoryBase.access$createSystemUIInitializerInternal(SystemUIAppComponentFactoryBase.this, context);
            }
        });
        return instantiateApplicationCompat;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // androidx.core.app.AppComponentFactory
    public final ContentProvider instantiateProviderCompat(ClassLoader classLoader, String str) {
        final ContentProvider instantiateProviderCompat = super.instantiateProviderCompat(classLoader, str);
        if (instantiateProviderCompat instanceof ContextInitializer) {
            ((ContextInitializer) instantiateProviderCompat).setContextAvailableCallback(new ContextAvailableCallback() { // from class: com.android.systemui.SystemUIAppComponentFactoryBase$instantiateProviderCompat$1
                @Override // com.android.systemui.SystemUIAppComponentFactoryBase.ContextAvailableCallback
                public final SystemUIInitializer onContextAvailable(Context context) {
                    SystemUIInitializer access$createSystemUIInitializerInternal = SystemUIAppComponentFactoryBase.access$createSystemUIInitializerInternal(SystemUIAppComponentFactoryBase.this, context);
                    SysUIComponent sysUIComponent = access$createSystemUIInitializerInternal.getSysUIComponent();
                    try {
                        sysUIComponent.getClass().getMethod("inject", instantiateProviderCompat.getClass()).invoke(sysUIComponent, instantiateProviderCompat);
                    } catch (IllegalAccessException e) {
                        Log.w("AppComponentFactory", "No injector for class: " + instantiateProviderCompat.getClass(), e);
                    } catch (NoSuchMethodException e2) {
                        Log.w("AppComponentFactory", "No injector for class: " + instantiateProviderCompat.getClass(), e2);
                    } catch (InvocationTargetException e3) {
                        Log.w("AppComponentFactory", "No injector for class: " + instantiateProviderCompat.getClass(), e3);
                    }
                    return access$createSystemUIInitializerInternal;
                }
            });
        }
        return instantiateProviderCompat;
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
