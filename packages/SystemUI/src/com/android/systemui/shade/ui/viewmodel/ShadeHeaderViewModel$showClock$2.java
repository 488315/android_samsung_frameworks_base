package com.android.systemui.shade.ui.viewmodel;

import com.android.systemui.scene.shared.model.Overlays;
import java.util.Set;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;

/* loaded from: classes3.dex */
final /* synthetic */ class ShadeHeaderViewModel$showClock$2 extends AdaptedFunctionReference implements Function3 {
    public ShadeHeaderViewModel$showClock$2(Object obj) {
        super(3, obj, ShadeHeaderViewModel.class, "shouldShowClock", "shouldShowClock(ZLjava/util/Set;)Z", 4);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean zBooleanValue = ((Boolean) obj).booleanValue();
        Set set = (Set) obj2;
        ((ShadeHeaderViewModel) this.receiver).getClass();
        return Boolean.valueOf(zBooleanValue || !set.contains(Overlays.NotificationsShade));
    }
}
