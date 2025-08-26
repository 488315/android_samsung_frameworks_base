package androidx.compose.foundation;

import androidx.compose.ui.Modifier;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public abstract class SystemGestureExclusionKt {
    public static final Modifier systemGestureExclusion(Modifier modifier, Function1 function1) {
        return modifier.then(new ExcludeFromSystemGestureElement(function1));
    }
}
