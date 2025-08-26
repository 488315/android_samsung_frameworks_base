package androidx.compose.ui.draw;

import androidx.compose.ui.Modifier;
import androidx.compose.ui.node.LayoutNodeDrawScope;

/* loaded from: classes.dex */
public interface DrawModifier extends Modifier.Element {
    void draw(LayoutNodeDrawScope layoutNodeDrawScope);
}
