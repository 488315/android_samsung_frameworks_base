package com.android.systemui.qs;

import android.graphics.Path;
import com.android.systemui.Dependency;
import com.android.systemui.shade.SecPanelSplitHelper;
import com.android.systemui.shade.data.repository.ShadeRepository;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SecQSContainerImpl {
    public final Path fancyClippingPath;
    public boolean keyguardShowing;
    public final Lazy panelSplitHepler$delegate;
    public boolean panelSplitIntercepted;
    public final Lazy resourcePicker$delegate;
    public final Lazy shadeRepository$delegate;
    public final Lazy uiDisplayModeInteractor$delegate;

    public SecQSContainerImpl(Path path) {
        this.fancyClippingPath = path;
        final int i = 0;
        this.resourcePicker$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.qs.SecQSContainerImpl$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i) {
                    case 0:
                        return (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
                    case 1:
                        return (ShadeRepository) Dependency.sDependency.getDependencyInner(ShadeRepository.class);
                    case 2:
                        return (SecPanelSplitHelper) Dependency.sDependency.getDependencyInner(SecPanelSplitHelper.class);
                    default:
                        return (SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class);
                }
            }
        });
        final int i2 = 1;
        this.shadeRepository$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.qs.SecQSContainerImpl$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i2) {
                    case 0:
                        return (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
                    case 1:
                        return (ShadeRepository) Dependency.sDependency.getDependencyInner(ShadeRepository.class);
                    case 2:
                        return (SecPanelSplitHelper) Dependency.sDependency.getDependencyInner(SecPanelSplitHelper.class);
                    default:
                        return (SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class);
                }
            }
        });
        final int i3 = 2;
        this.panelSplitHepler$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.qs.SecQSContainerImpl$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i3) {
                    case 0:
                        return (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
                    case 1:
                        return (ShadeRepository) Dependency.sDependency.getDependencyInner(ShadeRepository.class);
                    case 2:
                        return (SecPanelSplitHelper) Dependency.sDependency.getDependencyInner(SecPanelSplitHelper.class);
                    default:
                        return (SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class);
                }
            }
        });
        final int i4 = 3;
        this.uiDisplayModeInteractor$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.qs.SecQSContainerImpl$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i4) {
                    case 0:
                        return (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
                    case 1:
                        return (ShadeRepository) Dependency.sDependency.getDependencyInner(ShadeRepository.class);
                    case 2:
                        return (SecPanelSplitHelper) Dependency.sDependency.getDependencyInner(SecPanelSplitHelper.class);
                    default:
                        return (SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class);
                }
            }
        });
    }
}
