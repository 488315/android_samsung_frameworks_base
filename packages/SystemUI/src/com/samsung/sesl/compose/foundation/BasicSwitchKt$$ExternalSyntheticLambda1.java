package com.samsung.sesl.compose.foundation;

import androidx.compose.animation.core.Animatable;
import androidx.compose.animation.core.AnimationState;
import androidx.compose.runtime.MutableFloatState;
import androidx.compose.runtime.MutableIntState;
import androidx.compose.runtime.SnapshotMutableFloatStateImpl;
import androidx.compose.runtime.SnapshotMutableIntStateImpl;
import androidx.compose.runtime.State;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes4.dex */
public final /* synthetic */ class BasicSwitchKt$$ExternalSyntheticLambda1 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ State f$2;

    public /* synthetic */ BasicSwitchKt$$ExternalSyntheticLambda1(Object obj, Object obj2, State state, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
        this.f$2 = state;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return BuildersKt.launch$default((CoroutineScope) this.f$0, null, null, new BasicSwitchKt$SeslBasicSwitch$animateFraction$2$1$1(((Float) obj).floatValue(), (Animatable) this.f$1, (AnimationState) this.f$2, null), 3);
            default:
                ((SnapshotMutableFloatStateImpl) ((MutableFloatState) this.f$2)).setFloatValue(((Number) ((State) this.f$0).getValue()).floatValue() * ((SnapshotMutableIntStateImpl) ((MutableIntState) this.f$1)).getIntValue());
                return Unit.INSTANCE;
        }
    }
}
