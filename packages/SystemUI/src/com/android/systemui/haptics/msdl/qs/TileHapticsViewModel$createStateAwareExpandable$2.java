package com.android.systemui.haptics.msdl.qs;

import com.android.systemui.haptics.msdl.qs.TileHapticsViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final /* synthetic */ class TileHapticsViewModel$createStateAwareExpandable$2 extends FunctionReferenceImpl implements Function0 {
    public TileHapticsViewModel$createStateAwareExpandable$2(Object obj) {
        super(0, obj, TileHapticsViewModel.class, "onDialogDrawingEnd", "onDialogDrawingEnd()V", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        ((TileHapticsViewModel) this.receiver).tileAnimationState.setValue(TileHapticsViewModel.TileAnimationState.IDLE);
        return Unit.INSTANCE;
    }
}
