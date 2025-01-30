package io.reactivex.schedulers;

import io.reactivex.Scheduler;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.schedulers.ComputationScheduler;
import io.reactivex.internal.schedulers.IoScheduler;
import io.reactivex.internal.schedulers.NewThreadScheduler;
import io.reactivex.internal.schedulers.SingleScheduler;
import io.reactivex.internal.schedulers.TrampolineScheduler;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class Schedulers {
    public static final Scheduler COMPUTATION;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ComputationHolder {
        public static final ComputationScheduler DEFAULT = new ComputationScheduler();
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ComputationTask implements Callable {
        @Override // java.util.concurrent.Callable
        public final Object call() {
            return ComputationHolder.DEFAULT;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class IOTask implements Callable {
        @Override // java.util.concurrent.Callable
        public final Object call() {
            return IoHolder.DEFAULT;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class IoHolder {
        public static final IoScheduler DEFAULT = new IoScheduler();
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class NewThreadHolder {
        public static final NewThreadScheduler DEFAULT = new NewThreadScheduler();
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class NewThreadTask implements Callable {
        @Override // java.util.concurrent.Callable
        public final Object call() {
            return NewThreadHolder.DEFAULT;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SingleHolder {
        public static final SingleScheduler DEFAULT = new SingleScheduler();
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SingleTask implements Callable {
        @Override // java.util.concurrent.Callable
        public final Object call() {
            return SingleHolder.DEFAULT;
        }
    }

    static {
        SingleTask singleTask = new SingleTask();
        int i = ObjectHelper.$r8$clinit;
        RxJavaPlugins.callRequireNonNull(singleTask);
        COMPUTATION = RxJavaPlugins.callRequireNonNull(new ComputationTask());
        RxJavaPlugins.callRequireNonNull(new IOTask());
        int i2 = TrampolineScheduler.$r8$clinit;
        RxJavaPlugins.callRequireNonNull(new NewThreadTask());
    }

    private Schedulers() {
        throw new IllegalStateException("No instances!");
    }
}
