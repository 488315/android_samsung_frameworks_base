package com.android.compose.animation.scene;

import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class TransitionKey extends Key {
    public static final Companion Companion;
    public static final TransitionKey PredictiveBack;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        DefaultConstructorMarker defaultConstructorMarker = null;
        Companion = new Companion(defaultConstructorMarker);
        PredictiveBack = new TransitionKey("PredictiveBack", defaultConstructorMarker, 2, defaultConstructorMarker);
    }

    public TransitionKey(String str, Object obj) {
        super(str, obj, null);
    }

    @Override // com.android.compose.animation.scene.Key
    public final String toString() {
        return TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder("TransitionKey(debugName="), this.debugName, ")");
    }

    public /* synthetic */ TransitionKey(String str, Object obj, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? new Object() : obj);
    }
}
