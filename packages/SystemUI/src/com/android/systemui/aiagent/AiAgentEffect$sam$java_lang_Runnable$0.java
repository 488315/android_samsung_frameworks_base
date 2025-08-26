package com.android.systemui.aiagent;

import kotlin.jvm.functions.Function0;

/* loaded from: classes.dex */
public final /* synthetic */ class AiAgentEffect$sam$java_lang_Runnable$0 implements Runnable {
    public final /* synthetic */ Function0 function;

    public AiAgentEffect$sam$java_lang_Runnable$0(Function0 function0) {
        this.function = function0;
    }

    @Override // java.lang.Runnable
    public final /* synthetic */ void run() {
        this.function.invoke();
    }
}
