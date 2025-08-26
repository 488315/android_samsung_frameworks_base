package com.android.systemui.communal.ui.compose;

import com.android.compose.animation.scene.ContentKey;
import com.android.compose.animation.scene.ElementKey;
import com.android.compose.animation.scene.ElementMatcher;

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
