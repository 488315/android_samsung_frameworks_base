package com.android.systemui.dreams.homecontrols;

import android.os.PowerManager;
import android.service.dreams.DreamService;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleKt;
import androidx.lifecycle.LifecycleOwner;
import com.android.systemui.dreams.DreamLogger;
import com.android.systemui.dreams.homecontrols.service.TaskFragmentComponent;
import com.android.systemui.dreams.homecontrols.shared.model.HomeControlsDataSource;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.util.time.SystemClock;
import com.android.systemui.util.wakelock.WakeLock;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.BuildersKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class HomeControlsDreamServiceImpl implements LifecycleOwner {
    public static final long ACTIVITY_RESTART_DELAY;
    public static final Companion Companion = new Companion(null);
    public final /* synthetic */ LifecycleOwner $$delegate_0;
    public final HomeControlsDataSource dataSource;
    public final DreamLogger logger;
    public final PowerManager powerManager;
    public final DreamService service;
    public final SystemClock systemClock;
    public TaskFragmentComponent taskFragmentComponent;
    public final TaskFragmentComponent.Factory taskFragmentFactory;
    public final Lazy wakeLock$delegate = LazyKt__LazyJVMKt.lazy(new HomeControlsDreamServiceImpl$$ExternalSyntheticLambda1(this, 1));
    public final WakeLock.Builder wakeLockBuilder;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
        HomeControlsDreamServiceImpl create(DreamService dreamService, LifecycleOwner lifecycleOwner);
    }

    static {
        Duration.Companion companion = Duration.Companion;
        ACTIVITY_RESTART_DELAY = DurationKt.toDuration(334, DurationUnit.MILLISECONDS);
    }

    public HomeControlsDreamServiceImpl(TaskFragmentComponent.Factory factory, WakeLock.Builder builder, PowerManager powerManager, SystemClock systemClock, HomeControlsDataSource homeControlsDataSource, LogBuffer logBuffer, DreamService dreamService, LifecycleOwner lifecycleOwner) {
        this.$$delegate_0 = lifecycleOwner;
        this.taskFragmentFactory = factory;
        this.wakeLockBuilder = builder;
        this.powerManager = powerManager;
        this.systemClock = systemClock;
        this.dataSource = homeControlsDataSource;
        this.service = dreamService;
        this.logger = new DreamLogger(logBuffer, "HomeControlsDreamServiceImpl");
    }

    public final void endDream(boolean z) {
        this.powerManager.userActivity(this.systemClock.uptimeMillis(), 0, 1);
        if (!z || !this.service.getRedirectWake()) {
            this.service.finish();
        } else {
            this.service.wakeUp();
            BuildersKt.launch$default(LifecycleKt.getCoroutineScope(this.$$delegate_0.getLifecycle()), null, null, new HomeControlsDreamServiceImpl$endDream$1(this, null), 3);
        }
    }

    @Override // androidx.lifecycle.LifecycleOwner
    public final Lifecycle getLifecycle() {
        return this.$$delegate_0.getLifecycle();
    }
}
