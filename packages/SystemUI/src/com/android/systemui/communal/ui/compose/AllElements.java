package com.android.systemui.communal.ui.compose;

import com.android.compose.animation.scene.ContentKey;
import com.android.compose.animation.scene.ElementKey;
import com.android.compose.animation.scene.ElementMatcher;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class AllElements implements ElementMatcher {
    public static final AllElements INSTANCE = new AllElements();

    private AllElements() {
    }

    @Override // com.android.compose.animation.scene.ElementMatcher
    public final boolean matches(ContentKey contentKey, ElementKey elementKey) {
        return true;
    }
}
