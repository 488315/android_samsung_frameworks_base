package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.keyguard.shared.model.TransitionModeOnCanceled;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class TransitionInteractor$listenForSleepTransition$2 extends Lambda implements Function1 {
    public static final TransitionInteractor$listenForSleepTransition$2 INSTANCE = new TransitionInteractor$listenForSleepTransition$2();

    public TransitionInteractor$listenForSleepTransition$2() {
        super(1);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        return TransitionModeOnCanceled.LAST_VALUE;
    }
}
