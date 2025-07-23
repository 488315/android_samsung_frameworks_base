package com.android.systemui.audio.soundcraft.interfaces.routine.condition;

import android.os.Handler;
import android.os.HandlerThread;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class RoutineHandlerThread {
    public static final RoutineHandlerThread INSTANCE = new RoutineHandlerThread();
    public static final Lazy handler$delegate;
    public static final Lazy thread$delegate;

    static {
        final int i = 0;
        thread$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.audio.soundcraft.interfaces.routine.condition.RoutineHandlerThread$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i) {
                    case 0:
                        RoutineHandlerThread routineHandlerThread = RoutineHandlerThread.INSTANCE;
                        HandlerThread handlerThread = new HandlerThread("RoutineHandlerThread");
                        handlerThread.start();
                        return handlerThread;
                    default:
                        RoutineHandlerThread routineHandlerThread2 = RoutineHandlerThread.INSTANCE;
                        RoutineHandlerThread.INSTANCE.getClass();
                        return new Handler(((HandlerThread) RoutineHandlerThread.thread$delegate.getValue()).getLooper());
                }
            }
        });
        final int i2 = 1;
        handler$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.audio.soundcraft.interfaces.routine.condition.RoutineHandlerThread$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i2) {
                    case 0:
                        RoutineHandlerThread routineHandlerThread = RoutineHandlerThread.INSTANCE;
                        HandlerThread handlerThread = new HandlerThread("RoutineHandlerThread");
                        handlerThread.start();
                        return handlerThread;
                    default:
                        RoutineHandlerThread routineHandlerThread2 = RoutineHandlerThread.INSTANCE;
                        RoutineHandlerThread.INSTANCE.getClass();
                        return new Handler(((HandlerThread) RoutineHandlerThread.thread$delegate.getValue()).getLooper());
                }
            }
        });
    }

    private RoutineHandlerThread() {
    }
}
