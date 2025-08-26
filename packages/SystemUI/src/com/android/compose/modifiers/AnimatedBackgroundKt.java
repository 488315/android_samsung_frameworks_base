package com.android.compose.modifiers;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Shape;
import androidx.compose.ui.platform.InspectableValueKt;
import kotlin.jvm.functions.Function0;

/* loaded from: classes.dex */
public abstract class AnimatedBackgroundKt {
    public static final AnimatedBackgroundKt$$ExternalSyntheticLambda0 DefaultAlpha = new AnimatedBackgroundKt$$ExternalSyntheticLambda0();

    public static final Modifier animatedBackground(Modifier modifier, Function0 function0, Function0 function02, Shape shape) {
        return modifier.then(new BackgroundElement(function0, function02, shape, InspectableValueKt.NoInspectorInfo));
    }
}
