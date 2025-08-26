package com.android.systemui.scene.data.model;

import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import kotlin.collections.CollectionsKt___CollectionsKt;

/* loaded from: classes2.dex */
public final class EmptyStack implements SceneStack {
    public static final EmptyStack INSTANCE = new EmptyStack();

    private EmptyStack() {
    }

    public final boolean equals(Object obj) {
        return this == obj || (obj instanceof EmptyStack);
    }

    public final int hashCode() {
        return -432229341;
    }

    public final String toString() {
        return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("SceneStack([", CollectionsKt___CollectionsKt.joinToString$default(new SceneStackKt$asIterable$$inlined$Iterable$1(this), null, null, null, new SceneStackKt$$ExternalSyntheticLambda0(), 31), "])");
    }
}
