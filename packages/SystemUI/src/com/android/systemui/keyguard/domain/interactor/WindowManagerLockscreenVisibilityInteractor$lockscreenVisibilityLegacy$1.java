package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.keyguard.domain.interactor.WindowManagerLockscreenVisibilityInteractor;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.util.kotlin.Utils;
import com.android.systemui.util.kotlin.WithPrev;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.internal.AdaptedFunctionReference;

/* loaded from: classes2.dex */
final /* synthetic */ class WindowManagerLockscreenVisibilityInteractor$lockscreenVisibilityLegacy$1 extends AdaptedFunctionReference implements Function5 {
    public WindowManagerLockscreenVisibilityInteractor$lockscreenVisibilityLegacy$1(Object obj) {
        super(5, obj, Utils.Companion.class, "toQuad", "toQuad(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Lcom/android/systemui/util/kotlin/Quad;", 4);
    }

    @Override // kotlin.jvm.functions.Function5
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        Boolean bool = (Boolean) obj3;
        bool.booleanValue();
        Boolean bool2 = (Boolean) obj4;
        bool2.booleanValue();
        Utils.Companion companion = (Utils.Companion) this.receiver;
        WindowManagerLockscreenVisibilityInteractor.Companion companion2 = WindowManagerLockscreenVisibilityInteractor.Companion;
        return companion.toQuad((KeyguardState) obj, (WithPrev) obj2, bool, bool2);
    }
}
