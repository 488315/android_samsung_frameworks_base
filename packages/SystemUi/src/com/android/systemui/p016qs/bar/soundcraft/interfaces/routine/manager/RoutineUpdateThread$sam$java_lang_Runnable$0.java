package com.android.systemui.p016qs.bar.soundcraft.interfaces.routine.manager;

import kotlin.jvm.functions.Function0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
