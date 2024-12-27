package com.android.systemui.audio.soundcraft.interfaces.routine.manager;

import kotlin.jvm.functions.Function0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
