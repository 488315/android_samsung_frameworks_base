package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.keyguard.domain.interactor.WindowManagerLockscreenVisibilityInteractor;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.util.kotlin.WithPrev;
import kotlin.Pair;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final /* synthetic */ class WindowManagerLockscreenVisibilityInteractor$lockscreenVisibility$2 extends AdaptedFunctionReference implements Function3 {
    public static final WindowManagerLockscreenVisibilityInteractor$lockscreenVisibility$2 INSTANCE = new WindowManagerLockscreenVisibilityInteractor$lockscreenVisibility$2();

    public WindowManagerLockscreenVisibilityInteractor$lockscreenVisibility$2() {
        super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        WindowManagerLockscreenVisibilityInteractor.Companion companion = WindowManagerLockscreenVisibilityInteractor.Companion;
        return new Pair((KeyguardState) obj, (WithPrev) obj2);
    }
}
