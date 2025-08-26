package com.android.compose.animation.scene;

import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class SceneKey extends ContentKey {
    public final ElementKey rootElementKey;
    public final String testTag;

    public SceneKey(String str, Object obj) {
        super(str, obj, null);
        this.testTag = AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("scene:", str);
        this.rootElementKey = new ElementKey(str, obj, null, false, 12, null);
    }

    @Override // com.android.compose.animation.scene.ContentKey
    public final String getTestTag() {
        return this.testTag;
    }

    @Override // com.android.compose.animation.scene.Key
    public final String toString() {
        return TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder("SceneKey(debugName="), this.debugName, ")");
    }

    public /* synthetic */ SceneKey(String str, Object obj, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? new Object() : obj);
    }
}
