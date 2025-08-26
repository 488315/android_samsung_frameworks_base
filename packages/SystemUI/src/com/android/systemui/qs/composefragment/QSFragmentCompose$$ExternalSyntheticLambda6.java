package com.android.systemui.qs.composefragment;

import android.os.Trace;
import androidx.compose.runtime.MutableState;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.compose.animation.scene.content.state.TransitionState;
import java.util.Map;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final /* synthetic */ class QSFragmentCompose$$ExternalSyntheticLambda6 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ QSFragmentCompose$$ExternalSyntheticLambda6(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        Object obj2 = this.f$0;
        switch (this.$r8$classId) {
            case 0:
                TransitionState.Transition transition = (TransitionState.Transition) obj;
                int i = QSFragmentCompose.$r8$clinit;
                SceneKeys.INSTANCE.getClass();
                String strM = AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("CollapsableQuickSettingsSTL ", SceneKeys.getDebugName(transition));
                Integer num = (Integer) ((Map) obj2).remove(transition);
                Trace.endAsyncSection(strM, num != null ? num.intValue() : -1);
                return Unit.INSTANCE;
            case 1:
                int i2 = QSFragmentCompose.$r8$clinit;
                return Boolean.valueOf(((Number) ((MutableState) obj2).getValue()).floatValue() < 1.0f);
            default:
                int i3 = QSFragmentCompose.$r8$clinit;
                ((Placeable.PlacementScope) obj).place((Placeable) obj2, 0, 0, 0.0f);
                return Unit.INSTANCE;
        }
    }
}
