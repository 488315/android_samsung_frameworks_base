package androidx.compose.foundation.draganddrop;

import androidx.compose.ui.Modifier;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class AndroidDragAndDropSource_androidKt {
    public static final Modifier dragAndDropSource(Modifier modifier, Function2 function2) {
        return modifier.then(new LegacyDragAndDropSourceWithDefaultShadowElement(function2));
    }
}
