package com.android.systemui.controls.controller;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.UserHandle;
import android.service.controls.IControlsActionCallback;
import android.service.controls.IControlsProviderInfoSubscriber;
import android.service.controls.IControlsSubscriber;
import android.service.controls.IControlsSubscription;
import android.service.controls.actions.ControlAction;
import android.service.controls.actions.ControlActionWrapper;
import android.util.ArraySet;
import android.util.Log;
import androidx.core.app.NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0;
import com.android.systemui.controls.controller.PackageUpdateMonitor;
import com.android.systemui.controls.util.ControlsUtil;
import com.android.systemui.util.concurrency.DelayableExecutor;
import java.util.List;
import java.util.Set;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class ControlsProviderLifecycleManager {
    public static final int BIND_FLAGS;
    public static final int BIND_FLAGS_PANEL;
    public final String TAG;
    public final IControlsActionCallback.Stub actionCallbackService;
    public final ComponentName componentName;
    public final Context context;
    public final ControlsUtil controlsUtil;
    public final DelayableExecutor executor;
    public final Intent intent;
    public boolean lastForPanel;
    public Runnable onLoadCanceller;
    public final PackageUpdateMonitor packageUpdateMonitor;
    public final Set queuedServiceMethods;
    public boolean requiresBound;
    public final ControlsProviderLifecycleManager$serviceConnection$1 serviceConnection;
    public final IBinder token;
    public final UserHandle user;
    public ServiceWrapper wrapper;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Action extends ServiceMethod {
        public final ControlAction action;
        public final String id;

        public Action(String str, ControlAction controlAction) {
            super();
            this.id = str;
            this.action = controlAction;
        }

        @Override // com.android.systemui.controls.controller.ControlsProviderLifecycleManager.ServiceMethod
        public final boolean callWrapper$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
            ControlsProviderLifecycleManager controlsProviderLifecycleManager = ControlsProviderLifecycleManager.this;
            String str = controlsProviderLifecycleManager.TAG;
            ComponentName componentName = controlsProviderLifecycleManager.componentName;
            StringBuilder sb = new StringBuilder("onAction ");
            sb.append(componentName);
            sb.append(" - ");
            String str2 = this.id;
            sb.append(str2);
            Log.d(str, sb.toString());
            ServiceWrapper serviceWrapper = controlsProviderLifecycleManager.wrapper;
            if (serviceWrapper == null) {
                return false;
            }
            ControlAction controlAction = this.action;
            try {
                serviceWrapper.service.action(str2, new ControlActionWrapper(controlAction), controlsProviderLifecycleManager.actionCallbackService);
                return true;
            } catch (Exception e) {
                Log.e("ServiceWrapper", "Caught exception from ControlsProviderService", e);
                return false;
            }
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Load extends ServiceMethod {
        public final IControlsProviderInfoSubscriber providerInfoSubscriber;
        public final IControlsSubscriber.Stub subscriber;

        public Load(IControlsSubscriber.Stub stub) {
            super();
            this.subscriber = stub;
        }

        @Override // com.android.systemui.controls.controller.ControlsProviderLifecycleManager.ServiceMethod
        public final boolean callWrapper$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
            ControlsProviderLifecycleManager controlsProviderLifecycleManager = ControlsProviderLifecycleManager.this;
            NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0.m("load ", controlsProviderLifecycleManager.componentName, controlsProviderLifecycleManager.TAG);
            ServiceWrapper serviceWrapper = controlsProviderLifecycleManager.wrapper;
            if (serviceWrapper == null) {
                return false;
            }
            try {
                serviceWrapper.service.load(this.subscriber);
                IControlsProviderInfoSubscriber iControlsProviderInfoSubscriber = this.providerInfoSubscriber;
                if (iControlsProviderInfoSubscriber != null) {
                    try {
                        serviceWrapper.service.loadControlsProviderInfo(iControlsProviderInfoSubscriber);
                    } catch (Exception e) {
                        Log.e("ServiceWrapper", "Caught exception from ControlsProviderService", e);
                        return false;
                    }
                }
                return true;
            } catch (Exception e2) {
                Log.e("ServiceWrapper", "Caught exception from ControlsProviderService", e2);
                return false;
            }
        }

        public Load(ControlsProviderLifecycleManager controlsProviderLifecycleManager, IControlsSubscriber.Stub stub, IControlsProviderInfoSubscriber iControlsProviderInfoSubscriber) {
            this(stub);
            this.providerInfoSubscriber = iControlsProviderInfoSubscriber;
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class LoadProviderInfo extends ServiceMethod {
        public final IControlsProviderInfoSubscriber providerInfoSubscriber;

        public LoadProviderInfo(IControlsProviderInfoSubscriber iControlsProviderInfoSubscriber) {
            super();
            this.providerInfoSubscriber = iControlsProviderInfoSubscriber;
        }

        @Override // com.android.systemui.controls.controller.ControlsProviderLifecycleManager.ServiceMethod
        public final boolean callWrapper$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
            ControlsProviderLifecycleManager controlsProviderLifecycleManager = ControlsProviderLifecycleManager.this;
            NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0.m("LoadProviderInfo ", controlsProviderLifecycleManager.componentName, controlsProviderLifecycleManager.TAG);
            ServiceWrapper serviceWrapper = controlsProviderLifecycleManager.wrapper;
            if (serviceWrapper == null) {
                return false;
            }
            try {
                serviceWrapper.service.loadControlsProviderInfo(this.providerInfoSubscriber);
                return true;
            } catch (Exception e) {
                Log.e("ServiceWrapper", "Caught exception from ControlsProviderService", e);
                return false;
            }
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public abstract class ServiceMethod {
        public ServiceMethod() {
        }

        public abstract boolean callWrapper$frameworks__base__packages__SystemUI__android_common__SystemUI_core();

        public final void run() {
            if (callWrapper$frameworks__base__packages__SystemUI__android_common__SystemUI_core()) {
                return;
            }
            final ControlsProviderLifecycleManager controlsProviderLifecycleManager = ControlsProviderLifecycleManager.this;
            Log.w(controlsProviderLifecycleManager.TAG, "ServiceMethod callWrapper return false");
            synchronized (controlsProviderLifecycleManager.queuedServiceMethods) {
                ((ArraySet) controlsProviderLifecycleManager.queuedServiceMethods).add(this);
            }
            controlsProviderLifecycleManager.executor.execute(new Runnable() { // from class: com.android.systemui.controls.controller.ControlsProviderLifecycleManager$ServiceMethod$run$1
                @Override // java.lang.Runnable
                public final void run() {
                    ControlsProviderLifecycleManager controlsProviderLifecycleManager2 = ControlsProviderLifecycleManager.this;
                    int i = ControlsProviderLifecycleManager.BIND_FLAGS;
                    controlsProviderLifecycleManager2.unbindAndCleanup("couldn't call through binder");
                }
            });
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Subscribe extends ServiceMethod {
        public final List list;
        public final IControlsSubscriber subscriber;

        public Subscribe(List<String> list, IControlsSubscriber iControlsSubscriber) {
            super();
            this.list = list;
            this.subscriber = iControlsSubscriber;
        }

        @Override // com.android.systemui.controls.controller.ControlsProviderLifecycleManager.ServiceMethod
        public final boolean callWrapper$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
            ControlsProviderLifecycleManager controlsProviderLifecycleManager = ControlsProviderLifecycleManager.this;
            Log.d(controlsProviderLifecycleManager.TAG, "subscribe " + controlsProviderLifecycleManager.componentName + " - " + this.list);
            ServiceWrapper serviceWrapper = controlsProviderLifecycleManager.wrapper;
            if (serviceWrapper == null) {
                return false;
            }
            try {
                serviceWrapper.service.subscribe(this.list, this.subscriber);
                return true;
            } catch (Exception e) {
                Log.e("ServiceWrapper", "Caught exception from ControlsProviderService", e);
                return false;
            }
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Suggest extends ServiceMethod {
        public final IControlsSubscriber.Stub subscriber;

        public Suggest(IControlsSubscriber.Stub stub) {
            super();
            this.subscriber = stub;
        }

        @Override // com.android.systemui.controls.controller.ControlsProviderLifecycleManager.ServiceMethod
        public final boolean callWrapper$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
            ControlsProviderLifecycleManager controlsProviderLifecycleManager = ControlsProviderLifecycleManager.this;
            NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0.m("suggest ", controlsProviderLifecycleManager.componentName, controlsProviderLifecycleManager.TAG);
            ServiceWrapper serviceWrapper = controlsProviderLifecycleManager.wrapper;
            if (serviceWrapper == null) {
                return false;
            }
            try {
                serviceWrapper.service.loadSuggested(this.subscriber);
                return true;
            } catch (Exception e) {
                Log.e("ServiceWrapper", "Caught exception from ControlsProviderService", e);
                return false;
            }
        }
    }

    static {
        new Companion(null);
        BIND_FLAGS = 67109121;
        BIND_FLAGS_PANEL = 257;
    }

    public ControlsProviderLifecycleManager(Context context, DelayableExecutor delayableExecutor, IControlsActionCallback.Stub stub, UserHandle userHandle, ComponentName componentName, PackageUpdateMonitor.Factory factory, ControlsUtil controlsUtil) {
        this.context = context;
        this.executor = delayableExecutor;
        this.actionCallbackService = stub;
        this.user = userHandle;
        this.componentName = componentName;
        this.controlsUtil = controlsUtil;
        Binder binder = new Binder();
        this.token = binder;
        this.queuedServiceMethods = new ArraySet();
        this.TAG = "ControlsProviderLifecycleManager";
        Intent intent = new Intent("android.service.controls.ControlsProviderService");
        intent.setComponent(componentName);
        Bundle bundle = new Bundle();
        bundle.putBinder("CALLBACK_TOKEN", binder);
        Unit unit = Unit.INSTANCE;
        intent.putExtra("CALLBACK_BUNDLE", bundle);
        Log.d("ControlsProviderLifecycleManager", "intent putToken = " + binder);
        this.intent = intent;
        this.packageUpdateMonitor = factory.create(userHandle, componentName.getPackageName(), new Runnable() { // from class: com.android.systemui.controls.controller.ControlsProviderLifecycleManager$packageUpdateMonitor$1
            @Override // java.lang.Runnable
            public final void run() {
                final ControlsProviderLifecycleManager controlsProviderLifecycleManager = ControlsProviderLifecycleManager.this;
                if (controlsProviderLifecycleManager.requiresBound) {
                    controlsProviderLifecycleManager.executor.execute(new Runnable() { // from class: com.android.systemui.controls.controller.ControlsProviderLifecycleManager$packageUpdateMonitor$1.1
                        @Override // java.lang.Runnable
                        public final void run() {
                            ControlsProviderLifecycleManager controlsProviderLifecycleManager2 = ControlsProviderLifecycleManager.this;
                            int i = ControlsProviderLifecycleManager.BIND_FLAGS;
                            controlsProviderLifecycleManager2.unbindAndCleanup("package updated");
                            ControlsProviderLifecycleManager controlsProviderLifecycleManager3 = ControlsProviderLifecycleManager.this;
                            controlsProviderLifecycleManager3.executor.execute(new ControlsProviderLifecycleManager$bindService$1(controlsProviderLifecycleManager3, true, controlsProviderLifecycleManager3.lastForPanel));
                        }
                    });
                }
            }
        });
        this.serviceConnection = new ControlsProviderLifecycleManager$serviceConnection$1(this);
    }

    public final void cancelSubscription(IControlsSubscription iControlsSubscription) {
        Log.d(this.TAG, "cancelSubscription: " + iControlsSubscription);
        if (this.wrapper != null) {
            try {
                iControlsSubscription.cancel();
            } catch (Exception e) {
                Log.e("ServiceWrapper", "Caught exception from ControlsProviderService", e);
            }
        }
    }

    public final void invokeOrQueue(ServiceMethod serviceMethod) {
        Unit unit;
        if (this.wrapper != null) {
            serviceMethod.run();
            unit = Unit.INSTANCE;
        } else {
            unit = null;
        }
        if (unit == null) {
            Log.d(this.TAG, "wrapper is null. request rebindService");
            synchronized (this.queuedServiceMethods) {
                ((ArraySet) this.queuedServiceMethods).add(serviceMethod);
            }
            this.executor.execute(new ControlsProviderLifecycleManager$bindService$1(this, true, false));
        }
    }

    public final void startSubscription(IControlsSubscription iControlsSubscription, long j) {
        Log.d(this.TAG, "startSubscription: " + iControlsSubscription);
        if (this.wrapper != null) {
            try {
                iControlsSubscription.request(j);
            } catch (Exception e) {
                Log.e("ServiceWrapper", "Caught exception from ControlsProviderService", e);
            }
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("ControlsProviderLifecycleManager(");
        sb.append("component=" + this.componentName);
        sb.append(", user=" + this.user);
        sb.append(")");
        return sb.toString();
    }

    public final void unbindAndCleanup(String str) {
        String m = KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0.m("unbindAndCleanup requiresBound = ", this.requiresBound);
        String str2 = this.TAG;
        Log.d(str2, m);
        Log.d(str2, "Unbinding service " + this.intent + ". Reason: " + str);
        this.wrapper = null;
        try {
            ControlsProviderLifecycleManager$serviceConnection$1 controlsProviderLifecycleManager$serviceConnection$1 = this.serviceConnection;
            if (controlsProviderLifecycleManager$serviceConnection$1.connected.compareAndSet(true, false)) {
                this.context.unbindService(controlsProviderLifecycleManager$serviceConnection$1);
            }
        } catch (IllegalArgumentException e) {
            Log.e(str2, "Failed to unbind service", e);
        }
    }

    public final void unbindService() {
        Log.d(this.TAG, "unbindService");
        Runnable runnable = this.onLoadCanceller;
        if (runnable != null) {
            runnable.run();
        }
        this.onLoadCanceller = null;
        this.executor.execute(new ControlsProviderLifecycleManager$bindService$1(this, false, false));
    }
}
