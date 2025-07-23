package com.android.systemui.audio.soundcraft.interfaces.routine.condition;

import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
