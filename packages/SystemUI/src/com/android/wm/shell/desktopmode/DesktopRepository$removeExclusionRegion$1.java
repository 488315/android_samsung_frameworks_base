package com.android.wm.shell.desktopmode;

import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DesktopRepository$removeExclusionRegion$1 implements Runnable {
    public final /* synthetic */ DesktopRepository this$0;

    public DesktopRepository$removeExclusionRegion$1(DesktopRepository desktopRepository) {
        this.this$0 = desktopRepository;
    }

    @Override // java.lang.Runnable
    public final void run() {
        DesktopRepository desktopRepository = this.this$0;
        Consumer consumer = desktopRepository.desktopGestureExclusionListener;
        if (consumer != null) {
            consumer.accept(DesktopRepository.access$calculateDesktopExclusionRegion(desktopRepository));
        }
    }
}
