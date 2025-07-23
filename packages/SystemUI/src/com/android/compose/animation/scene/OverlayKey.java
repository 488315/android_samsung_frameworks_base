package com.android.compose.animation.scene;

import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
