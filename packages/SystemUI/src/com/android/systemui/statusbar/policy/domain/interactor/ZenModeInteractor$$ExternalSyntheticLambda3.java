package com.android.systemui.statusbar.policy.domain.interactor;

import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.statusbar.notification.emptyshade.shared.ModesEmptyShadeFix;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;

/* loaded from: classes3.dex */
public final /* synthetic */ class ZenModeInteractor$$ExternalSyntheticLambda3 implements Function0 {
    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        int i = ZenModeInteractor.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i2 = ModesEmptyShadeFix.$r8$clinit;
        refactorFlagUtils.getClass();
        RefactorFlagUtils.assertOnEngBuild("New code path expects android.app.modes_ui_empty_shade to be enabled.");
        return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(EmptyList.INSTANCE);
    }
}
