package com.android.systemui.qs.composefragment.ui;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.ui.Modifier;
import com.android.compose.animation.scene.ContentScope;
import com.android.compose.animation.scene.ElementKey;
import com.android.systemui.qs.shared.ui.ElementKeys;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class GridAnchorKt {
    public static final void GridAnchor(ContentScope contentScope, final Modifier.Companion companion, Composer composer, final int i) {
        int i2;
        final ContentScope contentScope2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(548119371);
        if ((i & 6) == 0) {
            i2 = (composerImpl.changed(contentScope) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        int i3 = i2 | 48;
        if ((i3 & 19) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
            contentScope2 = contentScope;
        } else {
            Modifier.Companion companion2 = Modifier.Companion;
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.qs.composefragment.ui.GridAnchor (GridAnchor.kt:28)");
            }
            ElementKeys.INSTANCE.getClass();
            ElementKey elementKey = ElementKeys.GridAnchor;
            ComposableSingletons$GridAnchorKt.INSTANCE.getClass();
            contentScope2 = contentScope;
            contentScope2.Element(elementKey, companion2, ComposableSingletons$GridAnchorKt.f92lambda1, composerImpl, (i3 & 112) | 384 | ((i3 << 9) & 7168));
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            companion = companion2;
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.qs.composefragment.ui.GridAnchorKt$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int updateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(i | 1);
                    GridAnchorKt.GridAnchor(ContentScope.this, companion, (Composer) obj, updateChangedFlags);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
