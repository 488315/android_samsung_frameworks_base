package com.android.systemui.statusbar.notification.shelf.ui.viewmodel;

import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class NotificationShelfViewModel$$ExternalSyntheticLambda0 implements Function0 {
    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i = SceneContainerFlag.$r8$clinit;
        refactorFlagUtils.getClass();
        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
        return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE);
    }
}
