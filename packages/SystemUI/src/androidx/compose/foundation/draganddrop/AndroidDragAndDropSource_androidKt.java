package androidx.compose.foundation.draganddrop;

import androidx.compose.ui.Modifier;
import kotlin.jvm.functions.Function2;

/* loaded from: classes.dex */
public abstract class AndroidDragAndDropSource_androidKt {
    public static final Modifier dragAndDropSource(Modifier modifier, Function2 function2) {
        return modifier.then(new LegacyDragAndDropSourceWithDefaultShadowElement(function2));
    }
}
