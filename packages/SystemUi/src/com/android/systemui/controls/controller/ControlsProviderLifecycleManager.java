package com.android.systemui.controls.controller;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.UserHandle;
import android.service.controls.IControlsActionCallback;
import android.service.controls.IControlsProvider;
import android.service.controls.IControlsProviderInfoSubscriber;
import android.service.controls.IControlsSubscriber;
import android.service.controls.IControlsSubscription;
import android.service.controls.actions.ControlAction;
import android.service.controls.actions.ControlActionWrapper;
import android.util.ArraySet;
import android.util.Log;
import androidx.core.app.AbstractC0147x487e7be7;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.controls.controller.PackageUpdateMonitor;
import com.android.systemui.controls.ui.util.ControlsUtil;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.ExecutorImpl;
import java.util.List;
import java.util.Set;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ControlsProviderLifecycleManager implements IBinder.DeathRecipient {
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
    public ExecutorImpl.ExecutionToken onLoadCanceller;
    public final PackageUpdateMonitor packageUpdateMonitor;
    public final Set queuedServiceMethods;
    public boolean requiresBound;
    public final ControlsProviderLifecycleManager$serviceConnection$1 serviceConnection;
    public final IBinder token;
    public final UserHandle user;
    public ServiceWrapper wrapper;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Action extends ServiceMethod {
        public final ControlAction action;

        /* renamed from: id */
        public final String f246id;

        public Action(String str, ControlAction controlAction) {
            super();
            this.f246id = str;
            this.action = controlAction;
        }

        @Override // com.android.systemui.controls.controller.ControlsProviderLifecycleManager.ServiceMethod
        /* renamed from: callWrapper$frameworks__base__packages__SystemUI__android_common__SystemUI_core */
        public final boolean mo115x93b7231b() {
            ControlsProviderLifecycleManager controlsProviderLifecycleManager = ControlsProviderLifecycleManager.this;
            String str = controlsProviderLifecycleManager.TAG;
            ComponentName componentName = controlsProviderLifecycleManager.componentName;
            StringBuilder sb = new StringBuilder("onAction ");
            sb.append(componentName);
            sb.append(" - ");
            String str2 = this.f246id;
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Load extends ServiceMethod {
        public final IControlsProviderInfoSubscriber providerInfoSubscriber;
        public final IControlsSubscriber.Stub subscriber;

        public Load(IControlsSubscriber.Stub stub) {
            super();
            this.subscriber = stub;
        }

        @Override // com.android.systemui.controls.controller.ControlsProviderLifecycleManager.ServiceMethod
        /* renamed from: callWrapper$frameworks__base__packages__SystemUI__android_common__SystemUI_core */
        public final boolean mo115x93b7231b() {
            boolean z;
            boolean z2;
            ControlsProviderLifecycleManager controlsProviderLifecycleManager = ControlsProviderLifecycleManager.this;
            AbstractC0147x487e7be7.m27m("load ", controlsProviderLifecycleManager.componentName, controlsProviderLifecycleManager.TAG);
            boolean z3 = BasicRune.CONTROLS_PROVIDER_INFO;
            IControlsSubscriber.Stub stub = this.subscriber;
            if (z3) {
                ServiceWrapper serviceWrapper = controlsProviderLifecycleManager.wrapper;
                if (serviceWrapper != null) {
                    IControlsProvider iControlsProvider = serviceWrapper.service;
                    try {
                        iControlsProvider.load(stub);
                        z = true;
                    } catch (Exception e) {
                        Log.e("ServiceWrapper", "Caught exception from ControlsProviderService", e);
                        z = false;
                    }
                    if (z) {
                        IControlsProviderInfoSubscriber iControlsProviderInfoSubscriber = this.providerInfoSubscriber;
                        if (iControlsProviderInfoSubscriber != null) {
                            try {
                                iControlsProvider.loadControlsProviderInfo(iControlsProviderInfoSubscriber);
                            } catch (Exception e2) {
                                Log.e("ServiceWrapper", "Caught exception from ControlsProviderService", e2);
                                z2 = false;
                            }
                        }
                        z2 = true;
                        if (z2) {
                            return true;
                        }
                    }
                }
            } else {
                ServiceWrapper serviceWrapper2 = controlsProviderLifecycleManager.wrapper;
                if (serviceWrapper2 != null) {
                    try {
                        serviceWrapper2.service.load(stub);
                        return true;
                    } catch (Exception e3) {
                        Log.e("ServiceWrapper", "Caught exception from ControlsProviderService", e3);
                    }
                }
            }
            return false;
        }

        public Load(ControlsProviderLifecycleManager controlsProviderLifecycleManager, IControlsSubscriber.Stub stub, IControlsProviderInfoSubscriber iControlsProviderInfoSubscriber) {
            this(stub);
            this.providerInfoSubscriber = iControlsProviderInfoSubscriber;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class LoadProviderInfo extends ServiceMethod {
        public final IControlsProviderInfoSubscriber providerInfoSubscriber;

        public LoadProviderInfo(IControlsProviderInfoSubscriber iControlsProviderInfoSubscriber) {
            super();
            this.providerInfoSubscriber = iControlsProviderInfoSubscriber;
        }

        @Override // com.android.systemui.controls.controller.ControlsProviderLifecycleManager.ServiceMethod
        /* renamed from: callWrapper$frameworks__base__packages__SystemUI__android_common__SystemUI_core */
        public final boolean mo115x93b7231b() {
            ControlsProviderLifecycleManager controlsProviderLifecycleManager = ControlsProviderLifecycleManager.this;
            AbstractC0147x487e7be7.m27m("LoadProviderInfo ", controlsProviderLifecycleManager.componentName, controlsProviderLifecycleManager.TAG);
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class ServiceMethod {
        public ServiceMethod() {
        }

        /* renamed from: callWrapper$frameworks__base__packages__SystemUI__android_common__SystemUI_core */
        public abstract boolean mo115x93b7231b();

        public final void run() {
            if (mo115x93b7231b()) {
                return;
            }
            boolean z = BasicRune.CONTROLS_SAMSUNG_STYLE;
            final ControlsProviderLifecycleManager controlsProviderLifecycleManager = ControlsProviderLifecycleManager.this;
            if (z) {
                Log.w(controlsProviderLifecycleManager.TAG, "ServiceMethod callWrapper return false");
            }
            int i = ControlsProviderLifecycleManager.BIND_FLAGS;
            synchronized (controlsProviderLifecycleManager.queuedServiceMethods) {
                ((ArraySet) controlsProviderLifecycleManager.queuedServiceMethods).add(this);
            }
            ((ExecutorImpl) controlsProviderLifecycleManager.executor).execute(new Runnable() { // from class: com.android.systemui.controls.controller.ControlsProviderLifecycleManager$ServiceMethod$run$1
                @Override // java.lang.Runnable
                public final void run() {
                    ControlsProviderLifecycleManager controlsProviderLifecycleManager2 = ControlsProviderLifecycleManager.this;
                    int i2 = ControlsProviderLifecycleManager.BIND_FLAGS;
                    controlsProviderLifecycleManager2.unbindAndCleanup("couldn't call through binder");
                }
            });
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Subscribe extends ServiceMethod {
        public final List list;
        public final IControlsSubscriber subscriber;

        public Subscribe(List<String> list, IControlsSubscriber iControlsSubscriber) {
            super();
            this.list = list;
            this.subscriber = iControlsSubscriber;
        }

        @Override // com.android.systemui.controls.controller.ControlsProviderLifecycleManager.ServiceMethod
        /* renamed from: callWrapper$frameworks__base__packages__SystemUI__android_common__SystemUI_core */
        public final boolean mo115x93b7231b() {
            ControlsProviderLifecycleManager controlsProviderLifecycleManager = ControlsProviderLifecycleManager.this;
            String str = controlsProviderLifecycleManager.TAG;
            ComponentName componentName = controlsProviderLifecycleManager.componentName;
            StringBuilder sb = new StringBuilder("subscribe ");
            sb.append(componentName);
            sb.append(" - ");
            List list = this.list;
            sb.append(list);
            Log.d(str, sb.toString());
            ServiceWrapper serviceWrapper = controlsProviderLifecycleManager.wrapper;
            if (serviceWrapper == null) {
                return false;
            }
            try {
                serviceWrapper.service.subscribe(list, this.subscriber);
                return true;
            } catch (Exception e) {
                Log.e("ServiceWrapper", "Caught exception from ControlsProviderService", e);
                return false;
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Suggest extends ServiceMethod {
        public final IControlsSubscriber.Stub subscriber;

        public Suggest(IControlsSubscriber.Stub stub) {
            super();
            this.subscriber = stub;
        }

        @Override // com.android.systemui.controls.controller.ControlsProviderLifecycleManager.ServiceMethod
        /* renamed from: callWrapper$frameworks__base__packages__SystemUI__android_common__SystemUI_core */
        public final boolean mo115x93b7231b() {
            ControlsProviderLifecycleManager controlsProviderLifecycleManager = ControlsProviderLifecycleManager.this;
            AbstractC0147x487e7be7.m27m("suggest ", controlsProviderLifecycleManager.componentName, controlsProviderLifecycleManager.TAG);
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
        if (BasicRune.CONTROLS_SAMSUNG_STYLE) {
            Log.d("ControlsProviderLifecycleManager", "intent putToken=" + binder);
        }
        this.intent = intent;
        this.packageUpdateMonitor = factory.create(userHandle, componentName.getPackageName(), new Runnable() { // from class: com.android.systemui.controls.controller.ControlsProviderLifecycleManager$packageUpdateMonitor$1
            @Override // java.lang.Runnable
            public final void run() {
                final ControlsProviderLifecycleManager controlsProviderLifecycleManager = ControlsProviderLifecycleManager.this;
                if (controlsProviderLifecycleManager.requiresBound) {
                    ((ExecutorImpl) controlsProviderLifecycleManager.executor).execute(new Runnable() { // from class: com.android.systemui.controls.controller.ControlsProviderLifecycleManager$packageUpdateMonitor$1.1
                        @Override // java.lang.Runnable
                        public final void run() {
                            ControlsProviderLifecycleManager controlsProviderLifecycleManager2 = ControlsProviderLifecycleManager.this;
                            int i = ControlsProviderLifecycleManager.BIND_FLAGS;
                            controlsProviderLifecycleManager2.unbindAndCleanup("package updated");
                            ControlsProviderLifecycleManager controlsProviderLifecycleManager3 = ControlsProviderLifecycleManager.this;
                            controlsProviderLifecycleManager3.bindService(true, controlsProviderLifecycleManager3.lastForPanel);
                        }
                    });
                }
            }
        });
        this.serviceConnection = new ControlsProviderLifecycleManager$serviceConnection$1(this);
    }

    public final void bindService(final boolean z, final boolean z2) {
        ((ExecutorImpl) this.executor).execute(new Runnable() { // from class: com.android.systemui.controls.controller.ControlsProviderLifecycleManager$bindService$1
            @Override // java.lang.Runnable
            public final void run() {
                boolean z3;
                ControlsProviderLifecycleManager controlsProviderLifecycleManager = ControlsProviderLifecycleManager.this;
                boolean z4 = z;
                boolean z5 = z2;
                controlsProviderLifecycleManager.requiresBound = z4;
                if (!z4) {
                    controlsProviderLifecycleManager.unbindAndCleanup("unbind requested");
                    PackageUpdateMonitor packageUpdateMonitor = controlsProviderLifecycleManager.packageUpdateMonitor;
                    if (packageUpdateMonitor.monitoring.compareAndSet(true, false)) {
                        packageUpdateMonitor.unregister();
                        return;
                    }
                    return;
                }
                if (controlsProviderLifecycleManager.wrapper == null) {
                    Log.d(controlsProviderLifecycleManager.TAG, "Binding service " + controlsProviderLifecycleManager.intent);
                    try {
                        controlsProviderLifecycleManager.lastForPanel = z5;
                        int i = z5 ? ControlsProviderLifecycleManager.BIND_FLAGS_PANEL : ControlsProviderLifecycleManager.BIND_FLAGS;
                        if (controlsProviderLifecycleManager.serviceConnection.connected.compareAndSet(false, true)) {
                            z3 = controlsProviderLifecycleManager.context.bindServiceAsUser(controlsProviderLifecycleManager.intent, controlsProviderLifecycleManager.serviceConnection, i, controlsProviderLifecycleManager.user);
                        } else {
                            if (BasicRune.CONTROLS_SAMSUNG_STYLE && controlsProviderLifecycleManager.serviceConnection.connected.get()) {
                                ControlsUtil controlsUtil = controlsProviderLifecycleManager.controlsUtil;
                                String packageName = controlsProviderLifecycleManager.componentName.getPackageName();
                                controlsUtil.getClass();
                                if (Intrinsics.areEqual("com.samsung.android.oneconnect", packageName)) {
                                    z3 = controlsProviderLifecycleManager.context.bindServiceAsUser(controlsProviderLifecycleManager.intent, controlsProviderLifecycleManager.serviceConnection, i, controlsProviderLifecycleManager.user);
                                }
                            }
                            z3 = false;
                        }
                        if (z3) {
                            return;
                        }
                        Log.d(controlsProviderLifecycleManager.TAG, "Couldn't bind to " + controlsProviderLifecycleManager.intent);
                        if (controlsProviderLifecycleManager.serviceConnection.connected.compareAndSet(true, false)) {
                            controlsProviderLifecycleManager.context.unbindService(controlsProviderLifecycleManager.serviceConnection);
                        }
                    } catch (SecurityException e) {
                        Log.e(controlsProviderLifecycleManager.TAG, "Failed to bind to service", e);
                        controlsProviderLifecycleManager.serviceConnection.connected.set(false);
                    }
                }
            }
        });
    }

    @Override // android.os.IBinder.DeathRecipient
    public final void binderDied() {
        if (this.wrapper == null) {
            return;
        }
        this.wrapper = null;
        if (BasicRune.CONTROLS_SAMSUNG_STYLE) {
            KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("binderDied requiresBound=", this.requiresBound, this.TAG);
        }
        if (this.requiresBound) {
            Log.d(this.TAG, "binderDied");
        }
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
            if (BasicRune.CONTROLS_SAMSUNG_STYLE) {
                Log.d(this.TAG, "wrapper is null. request rebindService");
            }
            synchronized (this.queuedServiceMethods) {
                ((ArraySet) this.queuedServiceMethods).add(serviceMethod);
            }
            bindService(true, false);
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
        Log.d(this.TAG, "Unbinding service " + this.intent + ". Reason: " + str);
        this.wrapper = null;
        if (BasicRune.CONTROLS_SAMSUNG_STYLE) {
            KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("binderDied requiresBound=", this.requiresBound, this.TAG);
        }
        try {
            if (this.serviceConnection.connected.compareAndSet(true, false)) {
                this.context.unbindService(this.serviceConnection);
            }
        } catch (IllegalArgumentException e) {
            Log.e(this.TAG, "Failed to unbind service", e);
        }
    }

    public final void unbindService() {
        if (BasicRune.CONTROLS_SAMSUNG_STYLE) {
            Log.d(this.TAG, "unbindService");
        }
        ExecutorImpl.ExecutionToken executionToken = this.onLoadCanceller;
        if (executionToken != null) {
            executionToken.run();
        }
        this.onLoadCanceller = null;
        bindService(false, false);
    }
}
