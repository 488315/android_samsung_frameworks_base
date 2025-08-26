package com.android.systemui.qs;

import com.android.systemui.Dependency;
import com.android.systemui.shade.PanelTransitionStateChangeEvent;
import com.android.systemui.shade.PanelTransitionStateListener;
import com.android.systemui.shade.SecPanelSplitHelper;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;

/* loaded from: classes2.dex */
public final class SecNonInterceptingScrollView {
    public final SecNonInterceptingScrollView$$ExternalSyntheticLambda0 heightAnimating;
    public final Lazy panelSplitHelper$delegate;
    public final SecNonInterceptingScrollView$panelTransitionStateListener$1 panelTransitionStateListener;
    public Function0 qsExpanded;
    public final Function0 scrollRange;

    /* JADX WARN: Type inference failed for: r2v1, types: [com.android.systemui.qs.SecNonInterceptingScrollView$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r3v3, types: [com.android.systemui.qs.SecNonInterceptingScrollView$panelTransitionStateListener$1] */
    public SecNonInterceptingScrollView(final Runnable runnable, Function0 function0) {
        this.scrollRange = function0;
        final int i = 0;
        this.panelSplitHelper$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.qs.SecNonInterceptingScrollView$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i) {
                    case 0:
                        return (SecPanelSplitHelper) Dependency.sDependency.getDependencyInner(SecPanelSplitHelper.class);
                    default:
                        return Boolean.FALSE;
                }
            }
        });
        this.panelTransitionStateListener = new PanelTransitionStateListener() { // from class: com.android.systemui.qs.SecNonInterceptingScrollView$panelTransitionStateListener$1
            @Override // com.android.systemui.shade.PanelTransitionStateListener
            public final void onPanelTransitionStateChanged(PanelTransitionStateChangeEvent panelTransitionStateChangeEvent) {
                if (panelTransitionStateChangeEvent.state == 1) {
                    runnable.run();
                }
            }
        };
        final int i2 = 1;
        this.heightAnimating = new Function0() { // from class: com.android.systemui.qs.SecNonInterceptingScrollView$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i2) {
                    case 0:
                        return (SecPanelSplitHelper) Dependency.sDependency.getDependencyInner(SecPanelSplitHelper.class);
                    default:
                        return Boolean.FALSE;
                }
            }
        };
        final int i3 = 1;
        this.qsExpanded = new Function0() { // from class: com.android.systemui.qs.SecNonInterceptingScrollView$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i3) {
                    case 0:
                        return (SecPanelSplitHelper) Dependency.sDependency.getDependencyInner(SecPanelSplitHelper.class);
                    default:
                        return Boolean.FALSE;
                }
            }
        };
    }

    public final boolean canScroll() {
        if (!((Boolean) this.qsExpanded.invoke()).booleanValue()) {
            return false;
        }
        getClass();
        return !Boolean.FALSE.booleanValue();
    }
}
