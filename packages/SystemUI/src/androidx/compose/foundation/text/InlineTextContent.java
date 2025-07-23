package androidx.compose.foundation.text;

import androidx.compose.ui.text.Placeholder;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class InlineTextContent {
    public final Function3 children;
    public final Placeholder placeholder;

    public InlineTextContent(Placeholder placeholder, Function3 function3) {
        this.placeholder = placeholder;
        this.children = function3;
    }
}
