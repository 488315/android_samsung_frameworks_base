package com.android.systemui.shade;

import android.os.Handler;
import com.android.systemui.Dependency;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.shade.data.repository.ShadeRepository;
import kotlin.jvm.functions.Function0;

/* loaded from: classes3.dex */
public final /* synthetic */ class SecPanelFoldHelper$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                int i = SecPanelFoldHelper.$r8$clinit;
                return (ShadeRepository) Dependency.sDependency.getDependencyInner(ShadeRepository.class);
            case 1:
                int i2 = SecPanelFoldHelper$screenRatioListener$1.$r8$clinit;
                return (BroadcastDispatcher) Dependency.sDependency.getDependencyInner(BroadcastDispatcher.class);
            default:
                int i3 = SecPanelFoldHelper$screenRatioListener$1.$r8$clinit;
                return (Handler) Dependency.sDependency.getDependencyInner(Dependency.MAIN_HANDLER);
        }
    }
}
