package com.android.p038wm.shell.desktopmode;

import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DesktopModeTaskRepository$setTaskCornerListener$1 implements Runnable {
    public final /* synthetic */ DesktopModeTaskRepository this$0;

    public DesktopModeTaskRepository$setTaskCornerListener$1(DesktopModeTaskRepository desktopModeTaskRepository) {
        this.this$0 = desktopModeTaskRepository;
    }

    @Override // java.lang.Runnable
    public final void run() {
        DesktopModeTaskRepository desktopModeTaskRepository = this.this$0;
        Consumer consumer = desktopModeTaskRepository.desktopGestureExclusionListener;
        if (consumer != null) {
            consumer.accept(DesktopModeTaskRepository.access$calculateDesktopExclusionRegion(desktopModeTaskRepository));
        }
    }
}
