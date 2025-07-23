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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class Schedulers {
    public static final Scheduler COMPUTATION;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ComputationHolder {
        public static final ComputationScheduler DEFAULT = new ComputationScheduler();
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ComputationTask implements Callable {
        @Override // java.util.concurrent.Callable
        public final Object call() {
            return ComputationHolder.DEFAULT;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class IOTask implements Callable {
        @Override // java.util.concurrent.Callable
        public final Object call() {
            return IoHolder.DEFAULT;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class IoHolder {
        public static final IoScheduler DEFAULT = new IoScheduler();
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class NewThreadHolder {
        public static final NewThreadScheduler DEFAULT = new NewThreadScheduler();
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class NewThreadTask implements Callable {
        @Override // java.util.concurrent.Callable
        public final Object call() {
            return NewThreadHolder.DEFAULT;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class SingleHolder {
        public static final SingleScheduler DEFAULT = new SingleScheduler();
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
