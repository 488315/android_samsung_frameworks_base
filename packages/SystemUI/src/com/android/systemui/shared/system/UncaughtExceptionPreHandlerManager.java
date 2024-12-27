package com.android.systemui.shared.system;

import android.util.Log;
import java.lang.Thread;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class UncaughtExceptionPreHandlerManager {
    public final List handlers = new CopyOnWriteArrayList();
    public final GlobalUncaughtExceptionHandler globalUncaughtExceptionPreHandler = new GlobalUncaughtExceptionHandler();

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class GlobalUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
        public GlobalUncaughtExceptionHandler() {
        }

        @Override // java.lang.Thread.UncaughtExceptionHandler
        public final void uncaughtException(Thread thread, Throwable th) {
            Iterator it = ((CopyOnWriteArrayList) UncaughtExceptionPreHandlerManager.this.handlers).iterator();
            while (it.hasNext()) {
                try {
                    ((Thread.UncaughtExceptionHandler) it.next()).uncaughtException(thread, th);
                } catch (Exception e) {
                    Log.wtf("Uncaught exception pre-handler error", e);
                }
            }
        }
    }

    public final void registerHandler(Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
        Thread.UncaughtExceptionHandler uncaughtExceptionPreHandler = Thread.getUncaughtExceptionPreHandler();
        GlobalUncaughtExceptionHandler globalUncaughtExceptionHandler = this.globalUncaughtExceptionPreHandler;
        if (!Intrinsics.areEqual(uncaughtExceptionPreHandler, globalUncaughtExceptionHandler)) {
            if (uncaughtExceptionPreHandler instanceof GlobalUncaughtExceptionHandler) {
                throw new IllegalStateException("Two UncaughtExceptionPreHandlerManagers created");
            }
            if (uncaughtExceptionPreHandler != null && !((CopyOnWriteArrayList) this.handlers).contains(uncaughtExceptionPreHandler)) {
                ((CopyOnWriteArrayList) this.handlers).add(uncaughtExceptionPreHandler);
            }
            Thread.setUncaughtExceptionPreHandler(globalUncaughtExceptionHandler);
        }
        if (((CopyOnWriteArrayList) this.handlers).contains(uncaughtExceptionHandler)) {
            return;
        }
        ((CopyOnWriteArrayList) this.handlers).add(uncaughtExceptionHandler);
    }
}
