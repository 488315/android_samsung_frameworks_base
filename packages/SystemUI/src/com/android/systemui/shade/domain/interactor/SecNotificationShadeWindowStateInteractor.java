package com.android.systemui.shade.domain.interactor;

import com.android.systemui.shade.data.repository.SecNotificationShadeWindowStateRepository;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes3.dex */
public final class SecNotificationShadeWindowStateInteractor {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final SecNotificationShadeWindowStateRepository repository;
    public final ReadonlyStateFlow statusBarState;

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

    public SecNotificationShadeWindowStateInteractor(CoroutineScope coroutineScope) {
        SecNotificationShadeWindowStateRepository secNotificationShadeWindowStateRepository = new SecNotificationShadeWindowStateRepository();
        this.repository = secNotificationShadeWindowStateRepository;
        ReadonlyStateFlow readonlyStateFlow = secNotificationShadeWindowStateRepository.statusBarState;
        this.statusBarState = readonlyStateFlow;
        BuildersKt.launch$default(coroutineScope, null, null, new SecNotificationShadeWindowStateInteractor$2$1(FlowKt.distinctUntilChanged(FlowKt.combine(secNotificationShadeWindowStateRepository.shadeOrQsExpanded, readonlyStateFlow, secNotificationShadeWindowStateRepository.visibility, new SecNotificationShadeWindowStateInteractor$1$1(null))), this, null), 3);
    }
}
