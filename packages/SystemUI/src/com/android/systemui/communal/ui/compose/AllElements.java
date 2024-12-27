package com.android.systemui.communal.ui.compose;

import com.android.compose.animation.scene.ElementKey;
import com.android.compose.animation.scene.ElementMatcher;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class AllElements implements ElementMatcher {
    public static final AllElements INSTANCE = new AllElements();

    private AllElements() {
    }

    @Override // com.android.compose.animation.scene.ElementMatcher
    public final boolean matches(ElementKey elementKey) {
        return true;
    }
}
