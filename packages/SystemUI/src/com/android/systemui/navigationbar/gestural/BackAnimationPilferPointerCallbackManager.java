package com.android.systemui.navigationbar.gestural;

import com.android.wm.shell.back.BackAnimationController;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class BackAnimationPilferPointerCallbackManager {
    public final Optional backAnimation;
    public final CompositeRunnable callbacks = new CompositeRunnable();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class CompositeRunnable implements Runnable {
        public final Map runnables = new LinkedHashMap();

        @Override // java.lang.Runnable
        public final void run() {
            Iterator it = ((LinkedHashMap) this.runnables).values().iterator();
            while (it.hasNext()) {
                ((Runnable) it.next()).run();
            }
        }
    }

    public BackAnimationPilferPointerCallbackManager(Optional<BackAnimationController.BackAnimationImpl> optional) {
        this.backAnimation = optional;
    }
}
