package com.android.compose.modifiers;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.platform.InspectableValueKt;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public abstract class SizeKt {
    public static final PaddingKt$$ExternalSyntheticLambda0 SizeUnspecified = new PaddingKt$$ExternalSyntheticLambda0();

    public static final Modifier height(Modifier modifier, Function1 function1) {
        return modifier.then(new SizeModifier(null, function1, null, function1, true, InspectableValueKt.NoInspectorInfo, 5, null));
    }
}
