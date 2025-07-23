package androidx.compose.ui.draw;

import androidx.compose.ui.Modifier;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class DrawModifierKt {
    public static final CacheDrawModifierNode CacheDrawModifierNode(Function1 function1) {
        return new CacheDrawModifierNodeImpl(new CacheDrawScope(), function1);
    }

    public static final Modifier drawBehind(Modifier modifier, Function1 function1) {
        return modifier.then(new DrawBehindElement(function1));
    }

    public static final Modifier drawWithCache(Modifier modifier, Function1 function1) {
        return modifier.then(new DrawWithCacheElement(function1));
    }

    public static final Modifier drawWithContent(Modifier modifier, Function1 function1) {
        return modifier.then(new DrawWithContentElement(function1));
    }
}
