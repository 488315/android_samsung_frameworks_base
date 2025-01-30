package com.android.systemui.navigationbar.util;

import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ScopeTimer {
    public StandaloneCoroutine job;
    public final CoroutineScope scope;

    public ScopeTimer(CoroutineScope coroutineScope) {
        this.scope = coroutineScope;
    }

    /* JADX WARN: Code restructure failed: missing block: B:4:0x0009, code lost:
    
        if (r0.isActive() == true) goto L8;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void cancel() {
        StandaloneCoroutine standaloneCoroutine = this.job;
        boolean z = standaloneCoroutine != null;
        if (z) {
            StandaloneCoroutine standaloneCoroutine2 = this.job;
            if (standaloneCoroutine2 != null) {
                standaloneCoroutine2.cancel(null);
            }
            this.job = null;
        }
    }

    public final void start(long j, Function0 function0) {
        cancel();
        this.job = BuildersKt.launch$default(this.scope, null, null, new ScopeTimer$start$1(j, function0, this, null), 3);
    }
}
