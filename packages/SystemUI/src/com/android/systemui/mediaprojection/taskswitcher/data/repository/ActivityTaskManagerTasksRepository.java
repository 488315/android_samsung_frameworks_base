package com.android.systemui.mediaprojection.taskswitcher.data.repository;

import android.app.ActivityManager;
import android.app.IActivityTaskManager;
import android.os.IBinder;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* loaded from: classes2.dex */
public final class ActivityTaskManagerTasksRepository implements TasksRepository {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final IActivityTaskManager activityTaskManager;
    public final CoroutineDispatcher backgroundDispatcher;
    public final ReadonlySharedFlow foregroundTask;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.mediaprojection.taskswitcher.data.repository.ActivityTaskManagerTasksRepository$findRunningTaskFromWindowContainerToken$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return ActivityTaskManagerTasksRepository.this.findRunningTaskFromWindowContainerToken(null, this);
        }
    }

    /* renamed from: com.android.systemui.mediaprojection.taskswitcher.data.repository.ActivityTaskManagerTasksRepository$getRunningTasks$1, reason: invalid class name and case insensitive filesystem */
    final class C09671 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        public C09671(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            ActivityTaskManagerTasksRepository activityTaskManagerTasksRepository = ActivityTaskManagerTasksRepository.this;
            int i = ActivityTaskManagerTasksRepository.$r8$clinit;
            return activityTaskManagerTasksRepository.getRunningTasks(this);
        }
    }

    /* renamed from: com.android.systemui.mediaprojection.taskswitcher.data.repository.ActivityTaskManagerTasksRepository$getRunningTasks$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return ActivityTaskManagerTasksRepository.this.new AnonymousClass2(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return ActivityTaskManagerTasksRepository.this.activityTaskManager.getTasks(Integer.MAX_VALUE, false, false, -1);
        }
    }

    static {
        new Companion(null);
    }

    public ActivityTaskManagerTasksRepository(IActivityTaskManager iActivityTaskManager, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher) {
        this.activityTaskManager = iActivityTaskManager;
        this.backgroundDispatcher = coroutineDispatcher;
        Flow flowConflatedCallbackFlow = FlowConflatedKt.conflatedCallbackFlow(new ActivityTaskManagerTasksRepository$foregroundTask$1(this, null));
        SharingStarted.Companion.getClass();
        this.foregroundTask = FlowKt.shareIn(flowConflatedCallbackFlow, coroutineScope, SharingStarted.Companion.Lazily, 1);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object findRunningTaskFromWindowContainerToken(IBinder iBinder, ContinuationImpl continuationImpl) throws Throwable {
        AnonymousClass1 anonymousClass1;
        if (continuationImpl instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuationImpl;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuationImpl);
            }
        }
        Object runningTasks = anonymousClass1.result;
        Object obj = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(runningTasks);
            anonymousClass1.L$0 = iBinder;
            anonymousClass1.label = 1;
            runningTasks = getRunningTasks(anonymousClass1);
            if (runningTasks == obj) {
                return obj;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            iBinder = (IBinder) anonymousClass1.L$0;
            ResultKt.throwOnFailure(runningTasks);
        }
        for (Object obj2 : (Iterable) runningTasks) {
            if (Intrinsics.areEqual(((ActivityManager.RunningTaskInfo) obj2).token.asBinder(), iBinder)) {
                return obj2;
            }
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object getRunningTasks(ContinuationImpl continuationImpl) throws Throwable {
        C09671 c09671;
        if (continuationImpl instanceof C09671) {
            c09671 = (C09671) continuationImpl;
            int i = c09671.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c09671.label = i - Integer.MIN_VALUE;
            } else {
                c09671 = new C09671(continuationImpl);
            }
        }
        Object obj = c09671.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c09671.label;
        if (i2 != 0) {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return obj;
        }
        ResultKt.throwOnFailure(obj);
        AnonymousClass2 anonymousClass2 = new AnonymousClass2(null);
        c09671.label = 1;
        Object objWithContext = BuildersKt.withContext(this.backgroundDispatcher, anonymousClass2, c09671);
        return objWithContext == coroutineSingletons ? coroutineSingletons : objWithContext;
    }
}
