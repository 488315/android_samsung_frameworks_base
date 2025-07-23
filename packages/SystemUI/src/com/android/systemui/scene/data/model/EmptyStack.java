package com.android.systemui.scene.data.model;

import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import kotlin.collections.CollectionsKt___CollectionsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        String m;
        m = ContentInViewNode$Request$$ExternalSyntheticOutline0.m("SceneStack([", CollectionsKt___CollectionsKt.joinToString$default(new SceneStackKt$asIterable$$inlined$Iterable$1(this), null, null, null, new SceneStackKt$$ExternalSyntheticLambda0(), 31), "])");
        return m;
    }
}
