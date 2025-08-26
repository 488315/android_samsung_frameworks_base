package com.android.compose.animation.scene;

import androidx.compose.ui.unit.LayoutDirection;
import com.android.compose.animation.scene.UserAction;

/* loaded from: classes.dex */
public final class Back extends UserAction {
    public static final Back INSTANCE = new Back();

    public final class Resolved extends UserAction.Resolved {
        public static final Resolved INSTANCE = new Resolved();

        private Resolved() {
            super(null);
        }
    }

    private Back() {
        super(null);
    }

    public final boolean equals(Object obj) {
        return this == obj || (obj instanceof Back);
    }

    public final int hashCode() {
        return -1278983141;
    }

    @Override // com.android.compose.animation.scene.UserAction
    public final UserAction.Resolved resolve$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(LayoutDirection layoutDirection) {
        return Resolved.INSTANCE;
    }

    public final String toString() {
        return "Back";
    }
}
