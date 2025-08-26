package com.android.systemui.audio.soundcraft.interfaces.routine.condition;

import kotlin.jvm.functions.Function0;

/* loaded from: classes.dex */
public final /* synthetic */ class RoutineHandlerThread$sam$java_lang_Runnable$0 implements Runnable {
    public final /* synthetic */ Function0 function;

    public RoutineHandlerThread$sam$java_lang_Runnable$0(Function0 function0) {
        this.function = function0;
    }

    @Override // java.lang.Runnable
    public final /* synthetic */ void run() {
        this.function.invoke();
    }
}
