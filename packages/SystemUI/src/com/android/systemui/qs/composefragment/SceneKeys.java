package com.android.systemui.qs.composefragment;

import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import com.android.compose.animation.scene.ContentKey;
import com.android.compose.animation.scene.ElementKey;
import com.android.compose.animation.scene.ElementMatcher;
import com.android.compose.animation.scene.SceneKey;
import com.android.compose.animation.scene.content.state.TransitionState;
import com.android.systemui.qs.shared.ui.ElementKeys;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class SceneKeys {
    public static final SceneKeys INSTANCE = new SceneKeys();
    public static final SceneKey QuickQuickSettings = new SceneKey("QuickQuickSettingsScene", null, 2, null);
    public static final SceneKey QuickSettings = new SceneKey("QuickSettingsScene", null, 2, null);
    public static final SceneKey EditMode = new SceneKey("EditModeScene", null, 2, null);
    public static final SceneKeys$QqsTileElementMatcher$1 QqsTileElementMatcher = new ElementMatcher() { // from class: com.android.systemui.qs.composefragment.SceneKeys$QqsTileElementMatcher$1
        @Override // com.android.compose.animation.scene.ElementMatcher
        public final boolean matches(ContentKey contentKey, ElementKey elementKey) {
            SceneKeys.INSTANCE.getClass();
            if (!Intrinsics.areEqual(contentKey, SceneKeys.QuickQuickSettings)) {
                return false;
            }
            ElementKeys.INSTANCE.getClass();
            return ElementKeys.TileElementMatcher.matches(contentKey, elementKey);
        }
    };

    private SceneKeys() {
    }

    public static String getDebugName(TransitionState.Transition transition) {
        return MotionLayout$$ExternalSyntheticOutline0.m("[from=", transition.fromContent.debugName, ", to=", transition.toContent.debugName, "]");
    }
}
