package com.android.compose.animation.scene;

import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class OverlayKey extends ContentKey {
    public final String testTag;

    public OverlayKey(String str, Object obj) {
        super(str, obj, null);
        this.testTag = AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("overlay:", str);
    }

    @Override // com.android.compose.animation.scene.ContentKey
    public final String getTestTag() {
        return this.testTag;
    }

    @Override // com.android.compose.animation.scene.Key
    public final String toString() {
        return TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder("OverlayKey(debugName="), this.debugName, ")");
    }

    public /* synthetic */ OverlayKey(String str, Object obj, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? new Object() : obj);
    }
}
