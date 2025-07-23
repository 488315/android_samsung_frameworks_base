package com.android.compose.animation.scene;

import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class Key {
    public final String debugName;
    public final Object identity;

    public /* synthetic */ Key(String str, Object obj, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, obj);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!getClass().equals(obj != null ? obj.getClass() : null)) {
            return false;
        }
        Key key = obj instanceof Key ? (Key) obj : null;
        return Intrinsics.areEqual(this.identity, key != null ? key.identity : null);
    }

    public final int hashCode() {
        return this.identity.hashCode();
    }

    public String toString() {
        return TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder("Key(debugName="), this.debugName, ")");
    }

    private Key(String str, Object obj) {
        this.debugName = str;
        this.identity = obj;
    }
}
