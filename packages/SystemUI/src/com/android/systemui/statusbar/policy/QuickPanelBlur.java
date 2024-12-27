package com.android.systemui.statusbar.policy;

import com.android.systemui.Dependency;
import com.android.systemui.blur.SecQpBlurController;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;

public final class QuickPanelBlur {
    public final Lazy blurController$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.policy.QuickPanelBlur$blurController$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return (SecQpBlurController) Dependency.sDependency.getDependencyInner(SecQpBlurController.class);
        }
    });
}
