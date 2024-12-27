package com.android.systemui.shade.domain.interactor;

import com.android.systemui.shade.data.repository.SecNotificationShadeWindowStateRepository;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class SecNotificationShadeWindowStateInteractor {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final SecNotificationShadeWindowStateRepository repository;

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

    public SecNotificationShadeWindowStateInteractor(CoroutineScope coroutineScope) {
        SecNotificationShadeWindowStateRepository secNotificationShadeWindowStateRepository = new SecNotificationShadeWindowStateRepository();
        this.repository = secNotificationShadeWindowStateRepository;
        BuildersKt.launch$default(coroutineScope, null, null, new SecNotificationShadeWindowStateInteractor$2$1(FlowKt.distinctUntilChanged(FlowKt.combine(secNotificationShadeWindowStateRepository.shadeOrQsExpanded, secNotificationShadeWindowStateRepository.statusBarState, secNotificationShadeWindowStateRepository.visibility, new SecNotificationShadeWindowStateInteractor$1$1(null))), this, null), 3);
    }
}
