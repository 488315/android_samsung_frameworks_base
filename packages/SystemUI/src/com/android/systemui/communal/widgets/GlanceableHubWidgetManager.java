package com.android.systemui.communal.widgets;

import android.os.IBinder;
import android.os.IInterface;
import com.android.server.servicewatcher.ServiceWatcher;
import com.android.systemui.communal.shared.model.GlanceableHubMultiUserHelper;
import com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.Logger;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import java.util.concurrent.Executor;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class GlanceableHubWidgetManager implements ServiceWatcher.ServiceListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Executor bgExecutor;
    public final CoroutineScope bgScope;
    public final Logger logger;
    public final Lazy serviceWatcher$delegate;
    public final Flow widgets;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public GlanceableHubWidgetManager(Executor executor, CoroutineScope coroutineScope, GlanceableHubMultiUserHelper glanceableHubMultiUserHelper, LogBuffer logBuffer, ServiceWatcherFactory serviceWatcherFactory) {
        this.bgExecutor = executor;
        this.bgScope = coroutineScope;
        glanceableHubMultiUserHelper.getClass();
        this.logger = new Logger(logBuffer, "GlanceableHubWidgetManager");
        this.serviceWatcher$delegate = LazyKt__LazyJVMKt.lazy(new GlanceableHubWidgetManager$$ExternalSyntheticLambda2(serviceWatcherFactory, this));
        this.widgets = FlowConflatedKt.conflatedCallbackFlow(new GlanceableHubWidgetManager$widgets$1(this, null));
    }

    public final void onBind(IBinder iBinder, ServiceWatcher.BoundServiceInfo boundServiceInfo) {
        Logger.i$default(this.logger, "Service bound", null, 2, null);
    }

    public final void onUnbind() {
        Logger.i$default(this.logger, "Service unbound", null, 2, null);
    }

    public final void runOnService(final Function1 function1) {
        this.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.communal.widgets.GlanceableHubWidgetManager$runOnService$1
            @Override // java.lang.Runnable
            public final void run() {
                GlanceableHubWidgetManager glanceableHubWidgetManager = GlanceableHubWidgetManager.this;
                int i = GlanceableHubWidgetManager.$r8$clinit;
                ServiceWatcher serviceWatcher = (ServiceWatcher) glanceableHubWidgetManager.serviceWatcher$delegate.getValue();
                final Function1 function12 = function1;
                serviceWatcher.runOnBinder(new ServiceWatcher.BinderOperation() { // from class: com.android.systemui.communal.widgets.GlanceableHubWidgetManager$runOnService$1.1
                    public final void run(IBinder iBinder) {
                        IGlanceableHubWidgetManagerService proxy;
                        Function1 function13 = Function1.this;
                        int i2 = IGlanceableHubWidgetManagerService.Stub.$r8$clinit;
                        if (iBinder == null) {
                            proxy = null;
                        } else {
                            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.android.systemui.communal.widgets.IGlanceableHubWidgetManagerService");
                            proxy = (queryLocalInterface == null || !(queryLocalInterface instanceof IGlanceableHubWidgetManagerService)) ? new IGlanceableHubWidgetManagerService.Stub.Proxy(iBinder) : (IGlanceableHubWidgetManagerService) queryLocalInterface;
                        }
                        function13.mo779invoke(proxy);
                    }

                    public final void onError(Throwable th) {
                    }
                });
            }
        });
    }
}
