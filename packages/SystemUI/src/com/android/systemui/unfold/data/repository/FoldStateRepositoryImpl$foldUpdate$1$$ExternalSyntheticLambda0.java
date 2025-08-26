package com.android.systemui.unfold.data.repository;

import com.android.systemui.unfold.updates.DeviceFoldStateProvider;
import com.android.systemui.unfold.updates.FoldStateProvider;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* loaded from: classes3.dex */
public final /* synthetic */ class FoldStateRepositoryImpl$foldUpdate$1$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ FoldStateRepositoryImpl f$0;
    public final /* synthetic */ FoldStateProvider.FoldUpdatesListener f$1;

    public /* synthetic */ FoldStateRepositoryImpl$foldUpdate$1$$ExternalSyntheticLambda0(FoldStateRepositoryImpl foldStateRepositoryImpl, FoldStateProvider.FoldUpdatesListener foldUpdatesListener, int i) {
        this.$r8$classId = i;
        this.f$0 = foldStateRepositoryImpl;
        this.f$1 = foldUpdatesListener;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                ((DeviceFoldStateProvider) this.f$0.foldStateProvider).removeCallback((FoldStateRepositoryImpl$foldUpdate$1$callback$1) this.f$1);
                break;
            default:
                ((DeviceFoldStateProvider) this.f$0.foldStateProvider).removeCallback((FoldStateRepositoryImpl$hingeAngle$1$callback$1) this.f$1);
                break;
        }
        return Unit.INSTANCE;
    }
}
