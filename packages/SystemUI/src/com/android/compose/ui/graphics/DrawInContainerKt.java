package com.android.compose.ui.graphics;

import androidx.compose.ui.Modifier;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;

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
