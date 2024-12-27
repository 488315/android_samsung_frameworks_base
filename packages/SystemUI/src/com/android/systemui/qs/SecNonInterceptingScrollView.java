package com.android.systemui.qs;

import com.android.systemui.Dependency;
import com.android.systemui.shade.PanelTransitionStateChangeEvent;
import com.android.systemui.shade.PanelTransitionStateListener;
import com.android.systemui.shade.SecPanelSplitHelper;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;

public final class SecNonInterceptingScrollView {
    public final SecNonInterceptingScrollView$panelTransitionStateListener$1 panelTransitionStateListener;
    public final Function0 scrollRange;
    public final Lazy panelSplitHelper$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.qs.SecNonInterceptingScrollView$panelSplitHelper$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return (SecPanelSplitHelper) Dependency.sDependency.getDependencyInner(SecPanelSplitHelper.class);
        }
    });
    public final Function0 heightAnimating = new Function0() { // from class: com.android.systemui.qs.SecNonInterceptingScrollView$heightAnimating$1
        @Override // kotlin.jvm.functions.Function0
        public final /* bridge */ /* synthetic */ Object invoke() {
            return Boolean.FALSE;
        }
    };
    public Function0 qsExpanded = new Function0() { // from class: com.android.systemui.qs.SecNonInterceptingScrollView$qsExpanded$1
        @Override // kotlin.jvm.functions.Function0
        public final /* bridge */ /* synthetic */ Object invoke() {
            return Boolean.FALSE;
        }
    };

    public SecNonInterceptingScrollView(final Runnable runnable, Function0 function0) {
        this.scrollRange = function0;
        this.panelTransitionStateListener = new PanelTransitionStateListener() { // from class: com.android.systemui.qs.SecNonInterceptingScrollView$panelTransitionStateListener$1
            @Override // com.android.systemui.shade.PanelTransitionStateListener
            public final void onPanelTransitionStateChanged(PanelTransitionStateChangeEvent panelTransitionStateChangeEvent) {
                if (panelTransitionStateChangeEvent.state == 1) {
                    runnable.run();
                }
            }
        };
    }

    public final boolean canScroll() {
        return ((Boolean) this.qsExpanded.invoke()).booleanValue() && !((Boolean) this.heightAnimating.invoke()).booleanValue();
    }
}
