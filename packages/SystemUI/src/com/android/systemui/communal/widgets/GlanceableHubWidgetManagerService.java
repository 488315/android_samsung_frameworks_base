package com.android.systemui.communal.widgets;

import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteCallbackList;
import android.os.UserHandle;
import androidx.lifecycle.LifecycleKt;
import androidx.lifecycle.LifecycleService;
import com.android.systemui.communal.data.repository.CommunalWidgetRepository;
import com.android.systemui.communal.shared.model.GlanceableHubMultiUserHelper;
import com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.log.core.Logger;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;

/* loaded from: classes2.dex */
public final class GlanceableHubWidgetManagerService extends LifecycleService {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final CommunalAppWidgetHost appWidgetHost;
    public final CommunalWidgetHost communalWidgetHost;
    public final Logger logger;
    public final WidgetListenerRegistry widgetListenersRegistry;
    public final CommunalWidgetRepository widgetRepository;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final class WidgetListenerRegistry extends RemoteCallbackList {
        public final Map jobs = new LinkedHashMap();

        @Override // android.os.RemoteCallbackList
        public final void kill() {
            synchronized (this.jobs) {
                try {
                    Iterator it = ((LinkedHashMap) this.jobs).values().iterator();
                    while (it.hasNext()) {
                        ((Job) it.next()).cancel(null);
                    }
                    ((LinkedHashMap) this.jobs).clear();
                    Unit unit = Unit.INSTANCE;
                } catch (Throwable th) {
                    throw th;
                }
            }
            super.kill();
        }

        @Override // android.os.RemoteCallbackList
        public final void onCallbackDied(IInterface iInterface) {
            IGlanceableHubWidgetManagerService.IGlanceableHubWidgetsListener iGlanceableHubWidgetsListener = (IGlanceableHubWidgetManagerService.IGlanceableHubWidgetsListener) iInterface;
            synchronized (this.jobs) {
                Job job = (Job) TypeIntrinsics.asMutableMap(this.jobs).remove(iGlanceableHubWidgetsListener);
                if (job != null) {
                    job.cancel(null);
                    Unit unit = Unit.INSTANCE;
                }
            }
            super.onCallbackDied(iGlanceableHubWidgetsListener);
        }

        @Override // android.os.RemoteCallbackList
        public final boolean unregister(IGlanceableHubWidgetManagerService.IGlanceableHubWidgetsListener iGlanceableHubWidgetsListener) {
            synchronized (this.jobs) {
                Job job = (Job) TypeIntrinsics.asMutableMap(this.jobs).remove(iGlanceableHubWidgetsListener);
                if (job != null) {
                    job.cancel(null);
                    Unit unit = Unit.INSTANCE;
                }
            }
            return super.unregister((WidgetListenerRegistry) iGlanceableHubWidgetsListener);
        }
    }

    public final class WidgetManagerServiceBinder extends IGlanceableHubWidgetManagerService.Stub {
        public WidgetManagerServiceBinder() {
        }

        @Override // com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService
        public final void addWidget(ComponentName componentName, UserHandle userHandle, int i, IGlanceableHubWidgetManagerService.IConfigureWidgetCallback iConfigureWidgetCallback) {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                GlanceableHubWidgetManagerService.access$addWidgetInternal(GlanceableHubWidgetManagerService.this, componentName, userHandle, i, iConfigureWidgetCallback);
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        @Override // com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService
        public final void addWidgetsListener(IGlanceableHubWidgetManagerService.IGlanceableHubWidgetsListener iGlanceableHubWidgetsListener) {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                GlanceableHubWidgetManagerService.access$addWidgetsListenerInternal(GlanceableHubWidgetManagerService.this, iGlanceableHubWidgetsListener);
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        @Override // com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService
        public final void deleteWidget(int i) {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                GlanceableHubWidgetManagerService.this.widgetRepository.deleteWidget(i);
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        @Override // com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService
        public final void removeWidgetsListener(IGlanceableHubWidgetManagerService.IGlanceableHubWidgetsListener iGlanceableHubWidgetsListener) {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                GlanceableHubWidgetManagerService glanceableHubWidgetManagerService = GlanceableHubWidgetManagerService.this;
                if (iGlanceableHubWidgetsListener != null) {
                    glanceableHubWidgetManagerService.widgetListenersRegistry.unregister(iGlanceableHubWidgetsListener);
                } else {
                    int i = GlanceableHubWidgetManagerService.$r8$clinit;
                    glanceableHubWidgetManagerService.getClass();
                    throw new IllegalStateException("Listener cannot be null");
                }
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        @Override // com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService
        public final void resizeWidget(int i, int i2, int[] iArr, int[] iArr2) {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                GlanceableHubWidgetManagerService.access$resizeWidgetInternal(GlanceableHubWidgetManagerService.this, i, i2, iArr, iArr2);
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        @Override // com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService
        public final void updateWidgetOrder(int[] iArr, int[] iArr2) {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                GlanceableHubWidgetManagerService.access$updateWidgetOrderInternal(GlanceableHubWidgetManagerService.this, iArr, iArr2);
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }
    }

    static {
        new Companion(null);
    }

    public GlanceableHubWidgetManagerService(CommunalWidgetRepository communalWidgetRepository, CommunalAppWidgetHost communalAppWidgetHost, CommunalWidgetHost communalWidgetHost, GlanceableHubMultiUserHelper glanceableHubMultiUserHelper, LogBuffer logBuffer) {
        this.widgetRepository = communalWidgetRepository;
        this.appWidgetHost = communalAppWidgetHost;
        this.communalWidgetHost = communalWidgetHost;
        glanceableHubMultiUserHelper.getClass();
        this.logger = new Logger(logBuffer, "GlanceableHubWidgetManagerService");
        this.widgetListenersRegistry = new WidgetListenerRegistry();
    }

    public static final void access$addWidgetInternal(GlanceableHubWidgetManagerService glanceableHubWidgetManagerService, ComponentName componentName, UserHandle userHandle, int i, IGlanceableHubWidgetManagerService.IConfigureWidgetCallback iConfigureWidgetCallback) {
        glanceableHubWidgetManagerService.getClass();
        if (componentName == null) {
            throw new IllegalStateException("Provider cannot be null");
        }
        if (userHandle == null) {
            throw new IllegalStateException("User cannot be null");
        }
        glanceableHubWidgetManagerService.widgetRepository.addWidget(componentName, userHandle, Integer.valueOf(i), iConfigureWidgetCallback != null ? new GlanceableHubWidgetManagerService$addWidgetInternal$configurator$1$1(iConfigureWidgetCallback, glanceableHubWidgetManagerService) : null);
    }

    public static final void access$addWidgetsListenerInternal(GlanceableHubWidgetManagerService glanceableHubWidgetManagerService, IGlanceableHubWidgetManagerService.IGlanceableHubWidgetsListener iGlanceableHubWidgetsListener) {
        glanceableHubWidgetManagerService.getClass();
        if (iGlanceableHubWidgetsListener == null) {
            throw new IllegalStateException("Listener cannot be null");
        }
        if (!iGlanceableHubWidgetsListener.asBinder().isBinderAlive()) {
            throw new IllegalStateException("Listener binder is dead");
        }
        StandaloneCoroutine standaloneCoroutineLaunchIn = FlowKt.launchIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(glanceableHubWidgetManagerService.widgetRepository.getCommunalWidgets(), new GlanceableHubWidgetManagerService$addWidgetsListenerInternal$job$1(iGlanceableHubWidgetsListener, glanceableHubWidgetManagerService, null)), LifecycleKt.getCoroutineScope(glanceableHubWidgetManagerService.dispatcher.registry));
        WidgetListenerRegistry widgetListenerRegistry = glanceableHubWidgetManagerService.widgetListenersRegistry;
        if (!widgetListenerRegistry.register(iGlanceableHubWidgetsListener)) {
            standaloneCoroutineLaunchIn.cancel(null);
            return;
        }
        synchronized (widgetListenerRegistry.jobs) {
            widgetListenerRegistry.jobs.put(iGlanceableHubWidgetsListener, standaloneCoroutineLaunchIn);
            Unit unit = Unit.INSTANCE;
        }
    }

    public static final IntentSender access$getIntentSenderForConfigureActivityInternal(GlanceableHubWidgetManagerService glanceableHubWidgetManagerService, int i) {
        glanceableHubWidgetManagerService.getClass();
        try {
            return glanceableHubWidgetManagerService.appWidgetHost.getIntentSenderForConfigureActivity(i, 0);
        } catch (IntentSender.SendIntentException e) {
            Logger logger = glanceableHubWidgetManagerService.logger;
            GlanceableHubWidgetManagerService$$ExternalSyntheticLambda0 glanceableHubWidgetManagerService$$ExternalSyntheticLambda0 = new GlanceableHubWidgetManagerService$$ExternalSyntheticLambda0(0);
            LogMessage logMessageObtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.ERROR, glanceableHubWidgetManagerService$$ExternalSyntheticLambda0, null);
            logMessageObtain.setStr1(e.getLocalizedMessage());
            logger.getBuffer().commit(logMessageObtain);
            return null;
        }
    }

    public static final void access$resizeWidgetInternal(GlanceableHubWidgetManagerService glanceableHubWidgetManagerService, int i, int i2, int[] iArr, int[] iArr2) {
        glanceableHubWidgetManagerService.getClass();
        if (iArr == null || iArr2 == null) {
            throw new IllegalStateException("appWidgetIds and ranks cannot be null");
        }
        if (iArr.length != iArr2.length) {
            throw new IllegalStateException("appWidgetIds and ranks must be the same size");
        }
        glanceableHubWidgetManagerService.widgetRepository.resizeWidget(MapsKt__MapsKt.toMap(ArraysKt___ArraysKt.zip(iArr, iArr2)), i, i2);
    }

    public static final void access$updateWidgetOrderInternal(GlanceableHubWidgetManagerService glanceableHubWidgetManagerService, int[] iArr, int[] iArr2) {
        glanceableHubWidgetManagerService.getClass();
        if (iArr == null || iArr2 == null) {
            throw new IllegalStateException("appWidgetIds and ranks cannot be null");
        }
        if (iArr.length != iArr2.length) {
            throw new IllegalStateException("appWidgetIds and ranks must be the same size");
        }
        glanceableHubWidgetManagerService.widgetRepository.updateWidgetOrder(MapsKt__MapsKt.toMap(ArraysKt___ArraysKt.zip(iArr, iArr2)));
    }

    @Override // androidx.lifecycle.LifecycleService, android.app.Service
    public final IBinder onBind(Intent intent) {
        super.onBind(intent);
        return new WidgetManagerServiceBinder();
    }

    @Override // androidx.lifecycle.LifecycleService, android.app.Service
    public final void onCreate() {
        super.onCreate();
        Logger.i$default(this.logger, "Service created", null, 2, null);
        CommunalWidgetHost communalWidgetHost = this.communalWidgetHost;
        CommunalAppWidgetHost communalAppWidgetHost = communalWidgetHost.appWidgetHost;
        synchronized (communalAppWidgetHost.observers) {
            communalAppWidgetHost.observers.add(communalWidgetHost);
        }
        this.appWidgetHost.startListening();
    }

    @Override // androidx.lifecycle.LifecycleService, android.app.Service
    public final void onDestroy() {
        super.onDestroy();
        Logger.i$default(this.logger, "Service destroyed", null, 2, null);
        this.appWidgetHost.stopListening();
        CommunalWidgetHost communalWidgetHost = this.communalWidgetHost;
        CommunalAppWidgetHost communalAppWidgetHost = communalWidgetHost.appWidgetHost;
        synchronized (communalAppWidgetHost.observers) {
            communalAppWidgetHost.observers.remove(communalWidgetHost);
        }
        this.widgetListenersRegistry.kill();
    }
}
