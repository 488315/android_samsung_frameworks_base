package com.android.systemui.qs.bar.soundcraft.interfaces.routine.manager;

import android.os.Handler;
import android.os.HandlerThread;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class RoutineUpdateThread {
    public static final RoutineUpdateThread INSTANCE = new RoutineUpdateThread();
    public static final Lazy thread$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.qs.bar.soundcraft.interfaces.routine.manager.RoutineUpdateThread$thread$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            HandlerThread handlerThread = new HandlerThread("RoutineUpdateThread");
            handlerThread.start();
            return handlerThread;
        }
    });
    public static final Lazy handler$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.qs.bar.soundcraft.interfaces.routine.manager.RoutineUpdateThread$handler$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            RoutineUpdateThread.INSTANCE.getClass();
            return new Handler(((HandlerThread) RoutineUpdateThread.thread$delegate.getValue()).getLooper());
        }
    });

    private RoutineUpdateThread() {
    }
}
