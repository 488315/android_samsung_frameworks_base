package com.android.systemui.util;

import android.os.TraceNameSupplier;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class TraceUtils$Companion$namedRunnable$1 implements Runnable, TraceNameSupplier {
    final /* synthetic */ Function0 $block;
    final /* synthetic */ String $tag;

    public TraceUtils$Companion$namedRunnable$1(String str, Function0 function0) {
        this.$tag = str;
        this.$block = function0;
    }

    public String getTraceName() {
        return this.$tag;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.$block.invoke();
    }
}
