package com.android.systemui.communal.smartspace;

import android.app.smartspace.SmartspaceManager;
import android.app.smartspace.SmartspaceSession;
import android.content.Context;
import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class CommunalSmartspaceController {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Context context;
    public final Execution execution;
    public final Set listeners;
    public final BcSmartspaceDataPlugin plugin;
    public final SmartspacePrecondition precondition;
    public SmartspaceSession session;
    public final CommunalSmartspaceController$sessionListener$1 sessionListener;
    public final SmartspaceManager smartspaceManager;
    public final Executor uiExecutor;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r1v8, types: [com.android.systemui.communal.smartspace.CommunalSmartspaceController$sessionListener$1] */
    public CommunalSmartspaceController(Context context, SmartspaceManager smartspaceManager, Execution execution, Executor executor, SmartspacePrecondition smartspacePrecondition, Optional<Object> optional, Optional<BcSmartspaceDataPlugin> optional2) {
        this.context = context;
        this.smartspaceManager = smartspaceManager;
        this.execution = execution;
        this.uiExecutor = executor;
        this.precondition = smartspacePrecondition;
        this.plugin = optional2.orElse(null);
        ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(optional.orElse(null));
        this.listeners = new LinkedHashSet();
        CommunalSmartspaceController$preconditionListener$1 communalSmartspaceController$preconditionListener$1 = new CommunalSmartspaceController$preconditionListener$1(this);
        LockscreenPrecondition lockscreenPrecondition = (LockscreenPrecondition) smartspacePrecondition;
        synchronized (lockscreenPrecondition.listeners) {
            lockscreenPrecondition.listeners.add(communalSmartspaceController$preconditionListener$1);
            Unit unit = Unit.INSTANCE;
        }
        SmartspaceSession smartspaceSession = communalSmartspaceController$preconditionListener$1.this$0.session;
        if (smartspaceSession != null) {
            smartspaceSession.requestSmartspaceUpdate();
        }
        new Object(this) { // from class: com.android.systemui.communal.smartspace.CommunalSmartspaceController$filterListener$1
        };
        this.sessionListener = new SmartspaceSession.OnTargetsAvailableListener() { // from class: com.android.systemui.communal.smartspace.CommunalSmartspaceController$sessionListener$1
            public final void onTargetsAvailable(List list) {
                CommunalSmartspaceController.this.execution.assertIsMainThread();
                CommunalSmartspaceController communalSmartspaceController = CommunalSmartspaceController.this;
                ArrayList arrayList = new ArrayList();
                for (Object obj : list) {
                    communalSmartspaceController.getClass();
                    arrayList.add(obj);
                }
                BcSmartspaceDataPlugin bcSmartspaceDataPlugin = CommunalSmartspaceController.this.plugin;
                if (bcSmartspaceDataPlugin != null) {
                    bcSmartspaceDataPlugin.onTargetsAvailable(arrayList);
                }
            }
        };
    }
}
