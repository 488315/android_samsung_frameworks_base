package kotlinx.coroutines.internal;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class Symbol {
    public final String symbol;

    public Symbol(String str) {
        this.symbol = str;
    }

    public final String toString() {
        return AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(new StringBuilder("<"), this.symbol, ">");
    }
}
