package com.android.systemui.qs;

import android.graphics.Path;
import com.android.systemui.Dependency;
import com.android.systemui.shade.SecPanelSplitHelper;
import com.android.systemui.shade.data.repository.ShadeRepository;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class SecQSContainerImpl {
    public final Path fancyClippingPath;
    public boolean keyguardShowing;
    public boolean panelSplitIntercepted;
    public final Lazy resourcePicker$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.qs.SecQSContainerImpl$resourcePicker$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
        }
    });
    public final Lazy shadeRepository$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.qs.SecQSContainerImpl$shadeRepository$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return (ShadeRepository) Dependency.sDependency.getDependencyInner(ShadeRepository.class);
        }
    });
    public final Lazy panelSplitHepler$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.qs.SecQSContainerImpl$panelSplitHepler$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return (SecPanelSplitHelper) Dependency.sDependency.getDependencyInner(SecPanelSplitHelper.class);
        }
    });

    public SecQSContainerImpl(Path path) {
        this.fancyClippingPath = path;
    }
}
