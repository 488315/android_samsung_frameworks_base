package com.android.systemui.scene.data.model;

import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import com.android.compose.animation.scene.SceneKey;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class StackedNodes implements SceneStack {
    public final SceneKey head;
    public final SceneStack tail;

    public StackedNodes(SceneKey sceneKey, SceneStack sceneStack) {
        this.head = sceneKey;
        this.tail = sceneStack;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof StackedNodes)) {
            return false;
        }
        StackedNodes stackedNodes = (StackedNodes) obj;
        return Intrinsics.areEqual(this.head, stackedNodes.head) && Intrinsics.areEqual(this.tail, stackedNodes.tail);
    }

    public final int hashCode() {
        return this.tail.hashCode() + (this.head.identity.hashCode() * 31);
    }

    public final String toString() {
        String m;
        m = ContentInViewNode$Request$$ExternalSyntheticOutline0.m("SceneStack([", CollectionsKt___CollectionsKt.joinToString$default(new SceneStackKt$asIterable$$inlined$Iterable$1(this), null, null, null, new SceneStackKt$$ExternalSyntheticLambda0(), 31), "])");
        return m;
    }
}
