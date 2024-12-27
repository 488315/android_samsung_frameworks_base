package com.android.systemui.scene.data.model;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
