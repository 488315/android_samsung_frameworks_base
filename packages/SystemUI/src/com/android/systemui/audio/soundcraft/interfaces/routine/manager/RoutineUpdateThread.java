package com.android.systemui.audio.soundcraft.interfaces.routine.manager;

import android.os.Handler;
import android.os.HandlerThread;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;

/* loaded from: classes.dex */
public final class RoutineUpdateThread {
    public static final RoutineUpdateThread INSTANCE = new RoutineUpdateThread();
    public static final Lazy handler$delegate;
    public static final Lazy thread$delegate;

    static {
        final int i = 0;
        thread$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.audio.soundcraft.interfaces.routine.manager.RoutineUpdateThread$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i) {
                    case 0:
                        RoutineUpdateThread routineUpdateThread = RoutineUpdateThread.INSTANCE;
                        HandlerThread handlerThread = new HandlerThread("RoutineUpdateThread");
                        handlerThread.start();
                        return handlerThread;
                    default:
                        RoutineUpdateThread routineUpdateThread2 = RoutineUpdateThread.INSTANCE;
                        RoutineUpdateThread.INSTANCE.getClass();
                        return new Handler(((HandlerThread) RoutineUpdateThread.thread$delegate.getValue()).getLooper());
                }
            }
        });
        final int i2 = 1;
        handler$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.audio.soundcraft.interfaces.routine.manager.RoutineUpdateThread$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i2) {
                    case 0:
                        RoutineUpdateThread routineUpdateThread = RoutineUpdateThread.INSTANCE;
                        HandlerThread handlerThread = new HandlerThread("RoutineUpdateThread");
                        handlerThread.start();
                        return handlerThread;
                    default:
                        RoutineUpdateThread routineUpdateThread2 = RoutineUpdateThread.INSTANCE;
                        RoutineUpdateThread.INSTANCE.getClass();
                        return new Handler(((HandlerThread) RoutineUpdateThread.thread$delegate.getValue()).getLooper());
                }
            }
        });
    }

    private RoutineUpdateThread() {
    }
}
