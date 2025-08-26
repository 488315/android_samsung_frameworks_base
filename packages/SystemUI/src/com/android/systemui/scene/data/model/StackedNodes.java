package com.android.systemui.scene.data.model;

import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import com.android.compose.animation.scene.SceneKey;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

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
        return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("SceneStack([", CollectionsKt___CollectionsKt.joinToString$default(new SceneStackKt$asIterable$$inlined$Iterable$1(this), null, null, null, new SceneStackKt$$ExternalSyntheticLambda0(), 31), "])");
    }
}
