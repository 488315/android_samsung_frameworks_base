package kotlinx.coroutines.internal;

import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;

/* loaded from: classes4.dex */
public final class Symbol {
    public final String symbol;

    public Symbol(String str) {
        this.symbol = str;
    }

    public final String toString() {
        return TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder("<"), this.symbol, ">");
    }
}
