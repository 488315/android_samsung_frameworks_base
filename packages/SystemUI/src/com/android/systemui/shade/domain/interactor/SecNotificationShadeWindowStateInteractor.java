package com.android.systemui.shade.domain.interactor;

import com.android.systemui.shade.data.repository.SecNotificationShadeWindowStateRepository;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;

public final class SecNotificationShadeWindowStateInteractor {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final SecNotificationShadeWindowStateRepository repository;

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
