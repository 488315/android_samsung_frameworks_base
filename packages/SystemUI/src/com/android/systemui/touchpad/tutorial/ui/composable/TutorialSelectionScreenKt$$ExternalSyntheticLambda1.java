package com.android.systemui.touchpad.tutorial.ui.composable;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.ui.Modifier;
import com.android.systemui.touchpad.tutorial.ui.viewmodel.Screen;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class TutorialSelectionScreenKt$$ExternalSyntheticLambda1 implements Function2 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Function0 f$0;
    public final /* synthetic */ Function0 f$1;
    public final /* synthetic */ Function0 f$2;
    public final /* synthetic */ Function0 f$3;
    public final /* synthetic */ Screen f$4;
    public final /* synthetic */ Object f$5;
    public final /* synthetic */ int f$6;

    public /* synthetic */ TutorialSelectionScreenKt$$ExternalSyntheticLambda1(Function0 function0, Function0 function02, Function0 function03, Function0 function04, Screen screen, Modifier modifier, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = function0;
        this.f$1 = function02;
        this.f$2 = function03;
        this.f$3 = function04;
        this.f$4 = screen;
        this.f$5 = modifier;
        this.f$6 = i;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        switch (this.$r8$classId) {
            case 0:
                ((Integer) obj2).getClass();
                int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(this.f$6 | 1);
                Screen screen = this.f$4;
                Modifier modifier = (Modifier) this.f$5;
                TutorialSelectionScreenKt.HorizontalCompactSelectionButtons(this.f$0, this.f$1, this.f$2, this.f$3, screen, modifier, (Composer) obj, updateChangedFlags);
                break;
            case 1:
                ((Integer) obj2).getClass();
                int updateChangedFlags2 = RecomposeScopeImplKt.updateChangedFlags(this.f$6 | 1);
                Screen screen2 = this.f$4;
                Modifier modifier2 = (Modifier) this.f$5;
                TutorialSelectionScreenKt.VerticalSelectionButtons(this.f$0, this.f$1, this.f$2, this.f$3, screen2, modifier2, (Composer) obj, updateChangedFlags2);
                break;
            case 2:
                ((Integer) obj2).getClass();
                int updateChangedFlags3 = RecomposeScopeImplKt.updateChangedFlags(this.f$6 | 1);
                Screen screen3 = this.f$4;
                Modifier modifier3 = (Modifier) this.f$5;
                TutorialSelectionScreenKt.HorizontalSelectionButtons(this.f$0, this.f$1, this.f$2, this.f$3, screen3, modifier3, (Composer) obj, updateChangedFlags3);
                break;
            case 3:
                ((Integer) obj2).getClass();
                int updateChangedFlags4 = RecomposeScopeImplKt.updateChangedFlags(this.f$6 | 1);
                Screen screen4 = this.f$4;
                Modifier modifier4 = (Modifier) this.f$5;
                TutorialSelectionScreenKt.FourTutorialButtons(this.f$0, this.f$1, this.f$2, this.f$3, screen4, modifier4, (Composer) obj, updateChangedFlags4);
                break;
            case 4:
                ((Integer) obj2).getClass();
                int updateChangedFlags5 = RecomposeScopeImplKt.updateChangedFlags(this.f$6 | 1);
                Screen screen5 = this.f$4;
                Modifier modifier5 = (Modifier) this.f$5;
                TutorialSelectionScreenKt.TwoByTwoTutorialButtons(this.f$0, this.f$1, this.f$2, this.f$3, screen5, modifier5, (Composer) obj, updateChangedFlags5);
                break;
            default:
                ((Integer) obj2).intValue();
                TutorialSelectionScreenKt.TutorialSelectionScreen(this.f$0, this.f$1, this.f$2, this.f$3, (Function0) this.f$5, this.f$4, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(this.f$6 | 1));
                break;
        }
        return Unit.INSTANCE;
    }

    public /* synthetic */ TutorialSelectionScreenKt$$ExternalSyntheticLambda1(Function0 function0, Function0 function02, Function0 function03, Function0 function04, Function0 function05, Screen screen, int i) {
        this.$r8$classId = 5;
        this.f$0 = function0;
        this.f$1 = function02;
        this.f$2 = function03;
        this.f$3 = function04;
        this.f$5 = function05;
        this.f$4 = screen;
        this.f$6 = i;
    }
}
