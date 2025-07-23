package com.android.compose.modifiers;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.platform.InspectableValueKt;
import com.android.systemui.volume.panel.component.volume.ui.composable.ColumnVolumeSlidersKt$ColumnVolumeSliders$1$1$1$1;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class PaddingKt {
    public static final PaddingKt$$ExternalSyntheticLambda0 PaddingUnspecified = new PaddingKt$$ExternalSyntheticLambda0();

    public static Modifier padding$default(Modifier modifier, Function1 function1, ColumnVolumeSlidersKt$ColumnVolumeSliders$1$1$1$1 columnVolumeSlidersKt$ColumnVolumeSliders$1$1$1$1, Function1 function12, int i) {
        PaddingKt$$ExternalSyntheticLambda0 paddingKt$$ExternalSyntheticLambda0 = PaddingUnspecified;
        return modifier.then(new PaddingModifier(paddingKt$$ExternalSyntheticLambda0, (i & 2) != 0 ? paddingKt$$ExternalSyntheticLambda0 : function1, (i & 4) != 0 ? paddingKt$$ExternalSyntheticLambda0 : columnVolumeSlidersKt$ColumnVolumeSliders$1$1$1$1, (i & 8) != 0 ? paddingKt$$ExternalSyntheticLambda0 : function12, true, InspectableValueKt.NoInspectorInfo));
    }

    public static Modifier padding$default(Modifier modifier, Function1 function1, Function1 function12, int i) {
        int i2 = i & 1;
        PaddingKt$$ExternalSyntheticLambda0 paddingKt$$ExternalSyntheticLambda0 = PaddingUnspecified;
        Function1 function13 = i2 != 0 ? paddingKt$$ExternalSyntheticLambda0 : function1;
        Function1 function14 = (i & 2) != 0 ? paddingKt$$ExternalSyntheticLambda0 : function12;
        return modifier.then(new PaddingModifier(function13, function14, function13, function14, true, InspectableValueKt.NoInspectorInfo));
    }
}
