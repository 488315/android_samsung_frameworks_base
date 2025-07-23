package androidx.compose.ui.semantics;

import androidx.compose.ui.Modifier;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class SemanticsModifierKt {
    public static final AtomicInteger lastIdentifier = new AtomicInteger(0);

    public static final Modifier clearAndSetSemantics(Modifier modifier, Function1 function1) {
        return modifier.then(new ClearAndSetSemanticsElement(function1));
    }

    public static final Modifier semantics(Modifier modifier, boolean z, Function1 function1) {
        return modifier.then(new AppendedSemanticsElement(z, function1));
    }
}
