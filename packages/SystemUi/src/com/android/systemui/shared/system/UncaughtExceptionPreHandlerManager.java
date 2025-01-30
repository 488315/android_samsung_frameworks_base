package com.android.systemui.shared.system;

import android.util.Log;
import java.lang.Thread;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class UncaughtExceptionPreHandlerManager {
    public final List handlers = new CopyOnWriteArrayList();
    public final GlobalUncaughtExceptionHandler globalUncaughtExceptionPreHandler = new GlobalUncaughtExceptionHandler();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        boolean areEqual = Intrinsics.areEqual(uncaughtExceptionPreHandler, globalUncaughtExceptionHandler);
        List list = this.handlers;
        if (!areEqual) {
            if (uncaughtExceptionPreHandler instanceof GlobalUncaughtExceptionHandler) {
                throw new IllegalStateException("Two UncaughtExceptionPreHandlerManagers created");
            }
            if (uncaughtExceptionPreHandler != null) {
                CopyOnWriteArrayList copyOnWriteArrayList = (CopyOnWriteArrayList) list;
                if (!copyOnWriteArrayList.contains(uncaughtExceptionPreHandler)) {
                    copyOnWriteArrayList.add(uncaughtExceptionPreHandler);
                }
            }
            Thread.setUncaughtExceptionPreHandler(globalUncaughtExceptionHandler);
        }
        CopyOnWriteArrayList copyOnWriteArrayList2 = (CopyOnWriteArrayList) list;
        if (copyOnWriteArrayList2.contains(uncaughtExceptionHandler)) {
            return;
        }
        copyOnWriteArrayList2.add(uncaughtExceptionHandler);
    }
}
