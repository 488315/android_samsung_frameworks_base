package androidx.compose.ui.node;

import androidx.compose.ui.autofill.AndroidAutofill$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidComposeView;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.DensityKt;

/* loaded from: classes.dex */
public abstract class LayoutNodeKt {
    public static final Density DefaultDensity = DensityKt.Density$default(1.0f);

    public static final Owner requireOwner(LayoutNode layoutNode) {
        AndroidComposeView androidComposeView = layoutNode.owner;
        if (androidComposeView != null) {
            return androidComposeView;
        }
        throw AndroidAutofill$$ExternalSyntheticOutline0.m("LayoutNode should be attached to an owner");
    }
}
