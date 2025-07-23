package androidx.compose.ui.node;

import androidx.compose.ui.autofill.AndroidAutofill$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidComposeView;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.DensityKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
