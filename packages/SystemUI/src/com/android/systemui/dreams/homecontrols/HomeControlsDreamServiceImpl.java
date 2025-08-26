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
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;

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

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public interface Factory {
        HomeControlsDreamServiceImpl create(DreamService dreamService, LifecycleOwner lifecycleOwner);
    }

    /* renamed from: com.android.systemui.dreams.homecontrols.HomeControlsDreamServiceImpl$endDream$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return HomeControlsDreamServiceImpl.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                HomeControlsDreamServiceImpl.Companion.getClass();
                long j = HomeControlsDreamServiceImpl.ACTIVITY_RESTART_DELAY;
                this.label = 1;
                if (DelayKt.m3469delayVtjQ1oo(j, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            HomeControlsDreamServiceImpl homeControlsDreamServiceImpl = HomeControlsDreamServiceImpl.this;
            Companion companion = HomeControlsDreamServiceImpl.Companion;
            BuildersKt.launch$default(LifecycleKt.getCoroutineScope(homeControlsDreamServiceImpl.$$delegate_0.getLifecycle()), null, null, new HomeControlsDreamServiceImpl$launchActivity$1(homeControlsDreamServiceImpl, null), 3);
            return Unit.INSTANCE;
        }
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
            BuildersKt.launch$default(LifecycleKt.getCoroutineScope(this.$$delegate_0.getLifecycle()), null, null, new AnonymousClass1(null), 3);
        }
    }

    @Override // androidx.lifecycle.LifecycleOwner
    public final Lifecycle getLifecycle() {
        return this.$$delegate_0.getLifecycle();
    }
}
