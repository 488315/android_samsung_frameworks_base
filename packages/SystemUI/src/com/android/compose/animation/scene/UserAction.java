package com.android.compose.animation.scene;

import androidx.compose.ui.unit.LayoutDirection;
import com.android.compose.animation.scene.UserActionResult;
import kotlin.Pair;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class UserAction {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
