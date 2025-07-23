package kotlinx.coroutines.internal;

import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
