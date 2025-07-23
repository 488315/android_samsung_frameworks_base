package com.android.compose.animation.scene;

import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class ElementKey extends Key implements ElementMatcher {
    public static final Companion Companion = new Companion(null);
    public final ElementContentPicker contentPicker;
    public final boolean placeAllCopies;
    public final String testTag;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public ElementKey(String str, Object obj, ElementContentPicker elementContentPicker, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? new Object() : obj, (i & 4) != 0 ? TransitionDslKt.DefaultElementContentPicker : elementContentPicker, (i & 8) != 0 ? false : z);
    }

    @Override // com.android.compose.animation.scene.ElementMatcher
    public final boolean matches(ContentKey contentKey, ElementKey elementKey) {
        return Intrinsics.areEqual(elementKey, this);
    }

    @Override // com.android.compose.animation.scene.Key
    public String toString() {
        return TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder("ElementKey(debugName="), this.debugName, ")");
    }

    public ElementKey(String str, Object obj, ElementContentPicker elementContentPicker, boolean z) {
        super(str, obj, null);
        this.contentPicker = elementContentPicker;
        this.placeAllCopies = z;
        this.testTag = AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("element:", str);
    }

    public static /* synthetic */ void getTestTag$annotations() {
    }
}
