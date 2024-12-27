package com.android.systemui.util;

import android.os.TraceNameSupplier;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
