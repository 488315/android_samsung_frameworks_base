package com.android.systemui.qs.footer.data.repository;

import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.qs.FgsManagerController;
import com.android.systemui.qs.FgsManagerControllerImpl;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class ForegroundServicesRepositoryImpl implements ForegroundServicesRepository {
    public final Flow foregroundServicesCount;

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

    public ForegroundServicesRepositoryImpl(FgsManagerController fgsManagerController) {
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        ForegroundServicesRepositoryImpl$foregroundServicesCount$1 foregroundServicesRepositoryImpl$foregroundServicesCount$1 = new ForegroundServicesRepositoryImpl$foregroundServicesCount$1(fgsManagerController, null);
        conflatedCallbackFlow.getClass();
        this.foregroundServicesCount = FlowKt.distinctUntilChanged(FlowConflatedKt.conflatedCallbackFlow(foregroundServicesRepositoryImpl$foregroundServicesCount$1));
        FlowKt.transformLatest(((FgsManagerControllerImpl) fgsManagerController).showFooterDot, new ForegroundServicesRepositoryImpl$special$$inlined$flatMapLatest$1(null, this, fgsManagerController));
    }
}
