package com.android.systemui.shade;

import com.android.systemui.Dependency;
import com.android.systemui.shade.domain.interactor.SecPanelTouchProximityInteractor;
import com.android.systemui.shade.domain.interactor.SecQuickSettingsAffordanceInteractor;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class SecShadeControllerImpl {
    public final Lazy panelTouchProximityInteractor$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.SecShadeControllerImpl$panelTouchProximityInteractor$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return (SecPanelTouchProximityInteractor) Dependency.sDependency.getDependencyInner(SecPanelTouchProximityInteractor.class);
        }
    });
    public final Lazy secQuickSettingsAffordanceInteractor$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.SecShadeControllerImpl$secQuickSettingsAffordanceInteractor$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return (SecQuickSettingsAffordanceInteractor) Dependency.sDependency.getDependencyInner(SecQuickSettingsAffordanceInteractor.class);
        }
    });

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
}
