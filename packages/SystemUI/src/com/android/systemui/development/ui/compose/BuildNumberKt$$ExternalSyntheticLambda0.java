package com.android.systemui.development.ui.compose;

import com.android.systemui.development.ui.viewmodel.BuildNumberViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* loaded from: classes2.dex */
public final /* synthetic */ class BuildNumberKt$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ BuildNumberKt$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                return ((BuildNumberViewModel.Factory) this.f$0).create();
            default:
                ((BuildNumberViewModel) this.f$0).copyRequests.mo3475trySendJP2dKIU(Unit.INSTANCE);
                return Boolean.TRUE;
        }
    }
}
