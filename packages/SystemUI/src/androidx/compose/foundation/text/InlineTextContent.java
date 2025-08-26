package androidx.compose.foundation.text;

import androidx.compose.ui.text.Placeholder;
import kotlin.jvm.functions.Function3;

/* loaded from: classes.dex */
public final class InlineTextContent {
    public final Function3 children;
    public final Placeholder placeholder;

    public InlineTextContent(Placeholder placeholder, Function3 function3) {
        this.placeholder = placeholder;
        this.children = function3;
    }
}
