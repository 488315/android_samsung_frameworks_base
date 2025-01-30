package com.android.systemui.p016qs.footer.data.repository;

import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.p016qs.FgsManagerController;
import com.android.systemui.p016qs.FgsManagerControllerImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ForegroundServicesRepositoryImpl implements ForegroundServicesRepository {
    public final Flow foregroundServicesCount;
    public final ChannelFlowTransformLatest hasNewChanges;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        this.foregroundServicesCount = FlowKt.distinctUntilChanged(ConflatedCallbackFlow.conflatedCallbackFlow(foregroundServicesRepositoryImpl$foregroundServicesCount$1));
        this.hasNewChanges = FlowKt.transformLatest(((FgsManagerControllerImpl) fgsManagerController).showFooterDot, new C2174xa0125d7e(null, this, fgsManagerController));
    }
}
