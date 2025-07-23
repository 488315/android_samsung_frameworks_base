package androidx.compose.ui;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.platform.InspectorValueInfo;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
class ComposedModifier extends InspectorValueInfo implements Modifier.Element {
    public final Function3 factory;

    public ComposedModifier(Function1 function1, Function3 function3) {
        super(function1);
        this.factory = function3;
    }
}
