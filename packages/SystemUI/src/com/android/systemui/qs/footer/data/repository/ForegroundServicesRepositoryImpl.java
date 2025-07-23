package com.android.systemui.qs.footer.data.repository;

import com.android.systemui.qs.FgsManagerController;
import com.android.systemui.qs.FgsManagerControllerImpl;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ForegroundServicesRepositoryImpl implements ForegroundServicesRepository {
    public final Flow foregroundServicesCount;

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

    public ForegroundServicesRepositoryImpl(FgsManagerController fgsManagerController) {
        this.foregroundServicesCount = FlowKt.distinctUntilChanged(FlowConflatedKt.conflatedCallbackFlow(new ForegroundServicesRepositoryImpl$foregroundServicesCount$1(fgsManagerController, null)));
        FlowKt.transformLatest(((FgsManagerControllerImpl) fgsManagerController).showFooterDot, new ForegroundServicesRepositoryImpl$special$$inlined$flatMapLatest$1(null, this, fgsManagerController));
    }
}
