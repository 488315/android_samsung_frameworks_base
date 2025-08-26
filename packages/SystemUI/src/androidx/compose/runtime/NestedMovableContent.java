package androidx.compose.runtime;

/* loaded from: classes.dex */
final class NestedMovableContent {
    public final MovableContentStateReference container;
    public final MovableContentStateReference content;

    public NestedMovableContent(MovableContentStateReference movableContentStateReference, MovableContentStateReference movableContentStateReference2) {
        this.content = movableContentStateReference;
        this.container = movableContentStateReference2;
    }
}
