package com.android.systemui.volume.util;

import android.os.HandlerExecutor;
import android.os.HandlerThread;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class VolumeExecutor {
    public static final VolumeExecutor INSTANCE = new VolumeExecutor();
    public static final Lazy sExecutor$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.volume.util.VolumeExecutor$sExecutor$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            HandlerThread handlerThread = new HandlerThread("VolumeExecutor");
            handlerThread.start();
            return new HandlerExecutor(handlerThread.getThreadHandler());
        }
    });

    private VolumeExecutor() {
    }
}
