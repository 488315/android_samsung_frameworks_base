package com.android.systemui.scene.data.model;

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
        return "EmptyStack";
    }
}
