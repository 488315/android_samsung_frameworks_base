package com.android.compose.ui.graphics;

import androidx.compose.ui.Modifier;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class DrawInContainerKt {
    public static Modifier drawInContainer$default(Modifier.Companion companion, ContainerState containerState, Function0 function0) {
        DrawInContainerElement drawInContainerElement = new DrawInContainerElement(containerState, function0, 0.0f, new Function2() { // from class: com.android.compose.ui.graphics.DrawInContainerKt$drawInContainer$2
            @Override // kotlin.jvm.functions.Function2
            public final /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
                return null;
            }
        });
        companion.getClass();
        return drawInContainerElement;
    }
}
