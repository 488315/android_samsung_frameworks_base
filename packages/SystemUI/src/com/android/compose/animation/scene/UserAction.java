package com.android.compose.animation.scene;

import androidx.compose.ui.unit.LayoutDirection;
import com.android.compose.animation.scene.UserActionResult;
import kotlin.Pair;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public abstract class UserAction {

    public abstract class Resolved {
        public /* synthetic */ Resolved(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Resolved() {
        }
    }

    public /* synthetic */ UserAction(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract Resolved resolve$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(LayoutDirection layoutDirection);

    public final Pair to(SceneKey sceneKey) {
        return new Pair(this, UserActionResult.Companion.invoke$default(UserActionResult.Companion, sceneKey, null, 6));
    }

    private UserAction() {
    }

    public final Pair to(OverlayKey overlayKey) {
        return new Pair(this, new UserActionResult.ShowOverlay(overlayKey, null, false, null, 14, null));
    }
}
