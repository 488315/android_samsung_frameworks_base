package com.android.systemui.communal.smartspace;

import android.app.smartspace.SmartspaceConfig;
import android.app.smartspace.SmartspaceManager;
import android.app.smartspace.SmartspaceSession;
import android.app.smartspace.SmartspaceTargetEvent;
import android.util.Log;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.smartspace.SmartspacePrecondition;
import com.android.systemui.smartspace.preconditions.LockscreenPrecondition;
import com.android.systemui.util.concurrency.Execution;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class CommunalSmartspaceController {
    public final Execution execution;
    public final Set listeners;
    public final BcSmartspaceDataPlugin plugin;
    public final SmartspacePrecondition precondition;
    public SmartspaceSession session;
    public final CommunalSmartspaceController$sessionListener$1 sessionListener;
    public final Executor uiExecutor;
    public SmartspaceManager userSmartspaceManager;
    public final UserTracker userTracker;

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

    /* JADX WARN: Type inference failed for: r1v8, types: [com.android.systemui.communal.smartspace.CommunalSmartspaceController$sessionListener$1] */
    public CommunalSmartspaceController(UserTracker userTracker, Execution execution, Executor executor, SmartspacePrecondition smartspacePrecondition, Optional<Object> optional, Optional<BcSmartspaceDataPlugin> optional2) {
        this.userTracker = userTracker;
        this.execution = execution;
        this.uiExecutor = executor;
        this.precondition = smartspacePrecondition;
        this.plugin = optional2.orElse(null);
        if (optional.orElse(null) != null) {
            throw new ClassCastException();
        }
        this.listeners = new LinkedHashSet();
        CommunalSmartspaceController$preconditionListener$1 communalSmartspaceController$preconditionListener$1 = new CommunalSmartspaceController$preconditionListener$1(this);
        LockscreenPrecondition lockscreenPrecondition = (LockscreenPrecondition) smartspacePrecondition;
        synchronized (lockscreenPrecondition.listeners) {
            lockscreenPrecondition.listeners.add(communalSmartspaceController$preconditionListener$1);
            Unit unit = Unit.INSTANCE;
        }
        CommunalSmartspaceController communalSmartspaceController = communalSmartspaceController$preconditionListener$1.this$0;
        if (communalSmartspaceController.session != null || communalSmartspaceController.listeners.isEmpty()) {
            SmartspaceSession smartspaceSession = communalSmartspaceController.session;
            if (smartspaceSession != null) {
                smartspaceSession.requestSmartspaceUpdate();
            }
        } else {
            Log.d("CommunalSmartspaceCtrlr", "Precondition criteria changed. Attempting to connect session.");
            communalSmartspaceController.connectSession();
        }
        new Object(this) { // from class: com.android.systemui.communal.smartspace.CommunalSmartspaceController$filterListener$1
        };
        this.sessionListener = new SmartspaceSession.OnTargetsAvailableListener() { // from class: com.android.systemui.communal.smartspace.CommunalSmartspaceController$sessionListener$1
            public final void onTargetsAvailable(List list) {
                CommunalSmartspaceController.this.execution.assertIsMainThread();
                CommunalSmartspaceController communalSmartspaceController2 = CommunalSmartspaceController.this;
                ArrayList arrayList = new ArrayList();
                for (Object obj : list) {
                    communalSmartspaceController2.getClass();
                    arrayList.add(obj);
                }
                BcSmartspaceDataPlugin bcSmartspaceDataPlugin = CommunalSmartspaceController.this.plugin;
                if (bcSmartspaceDataPlugin != null) {
                    bcSmartspaceDataPlugin.onTargetsAvailable(arrayList);
                }
            }
        };
    }

    public final void connectSession() {
        BcSmartspaceDataPlugin bcSmartspaceDataPlugin;
        SmartspaceManager smartspaceManager = this.userSmartspaceManager;
        UserTracker userTracker = this.userTracker;
        if (smartspaceManager == null) {
            this.userSmartspaceManager = (SmartspaceManager) ((UserTrackerImpl) userTracker).getUserContext().getSystemService(SmartspaceManager.class);
        }
        if (this.userSmartspaceManager == null || (bcSmartspaceDataPlugin = this.plugin) == null || this.session != null || this.listeners.isEmpty()) {
            return;
        }
        LockscreenPrecondition lockscreenPrecondition = (LockscreenPrecondition) this.precondition;
        lockscreenPrecondition.execution.assertIsMainThread();
        if (lockscreenPrecondition.deviceReady) {
            SmartspaceManager smartspaceManager2 = this.userSmartspaceManager;
            SmartspaceSession createSmartspaceSession = smartspaceManager2 != null ? smartspaceManager2.createSmartspaceSession(new SmartspaceConfig.Builder(((UserTrackerImpl) userTracker).getUserContext(), BcSmartspaceDataPlugin.UI_SURFACE_GLANCEABLE_HUB).build()) : null;
            Log.d("CommunalSmartspaceCtrlr", "Starting smartspace session for communal");
            if (createSmartspaceSession != null) {
                createSmartspaceSession.addOnTargetsAvailableListener(this.uiExecutor, this.sessionListener);
            }
            this.session = createSmartspaceSession;
            bcSmartspaceDataPlugin.registerSmartspaceEventNotifier(new BcSmartspaceDataPlugin.SmartspaceEventNotifier() { // from class: com.android.systemui.communal.smartspace.CommunalSmartspaceController$connectSession$1
                @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin.SmartspaceEventNotifier
                public final void notifySmartspaceEvent(SmartspaceTargetEvent smartspaceTargetEvent) {
                    SmartspaceSession smartspaceSession = CommunalSmartspaceController.this.session;
                    if (smartspaceSession != null) {
                        smartspaceSession.notifySmartspaceEvent(smartspaceTargetEvent);
                    }
                }
            });
            SmartspaceSession smartspaceSession = this.session;
            if (smartspaceSession != null) {
                smartspaceSession.requestSmartspaceUpdate();
            }
        }
    }
}
