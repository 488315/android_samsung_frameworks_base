package com.android.systemui.audio.soundcraft.interfaces.routine.manager;

import kotlin.jvm.functions.Function0;

public final /* synthetic */ class RoutineUpdateThread$sam$java_lang_Runnable$0 implements Runnable {
    public final /* synthetic */ Function0 function;

    public RoutineUpdateThread$sam$java_lang_Runnable$0(Function0 function0) {
        this.function = function0;
    }

    @Override // java.lang.Runnable
    public final /* synthetic */ void run() {
        this.function.invoke();
    }
}
