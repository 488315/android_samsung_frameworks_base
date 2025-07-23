package com.android.systemui.shade;

import com.android.systemui.Dependency;
import com.android.systemui.shade.domain.interactor.SecPanelTouchProximityInteractor;
import com.android.systemui.shade.domain.interactor.SecQuickSettingsAffordanceInteractor;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SecShadeControllerImpl {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Lazy panelTouchProximityInteractor$delegate;
    public final Lazy secQuickSettingsAffordanceInteractor$delegate;

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

    public SecShadeControllerImpl() {
        final int i = 0;
        this.panelTouchProximityInteractor$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.SecShadeControllerImpl$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i) {
                    case 0:
                        int i2 = SecShadeControllerImpl.$r8$clinit;
                        return (SecPanelTouchProximityInteractor) Dependency.sDependency.getDependencyInner(SecPanelTouchProximityInteractor.class);
                    default:
                        int i3 = SecShadeControllerImpl.$r8$clinit;
                        return (SecQuickSettingsAffordanceInteractor) Dependency.sDependency.getDependencyInner(SecQuickSettingsAffordanceInteractor.class);
                }
            }
        });
        final int i2 = 1;
        this.secQuickSettingsAffordanceInteractor$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.SecShadeControllerImpl$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i2) {
                    case 0:
                        int i22 = SecShadeControllerImpl.$r8$clinit;
                        return (SecPanelTouchProximityInteractor) Dependency.sDependency.getDependencyInner(SecPanelTouchProximityInteractor.class);
                    default:
                        int i3 = SecShadeControllerImpl.$r8$clinit;
                        return (SecQuickSettingsAffordanceInteractor) Dependency.sDependency.getDependencyInner(SecQuickSettingsAffordanceInteractor.class);
                }
            }
        });
    }
}
