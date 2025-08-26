package com.android.systemui.common.data.repository;

import android.content.Context;
import android.os.Handler;
import android.os.UserHandle;
import com.android.internal.content.PackageMonitor;
import com.android.systemui.common.shared.model.PackageChangeModel;
import com.android.systemui.util.time.SystemClock;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.flow.StateFlow;

/* loaded from: classes.dex */
public final class PackageUpdateMonitor extends PackageMonitor {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final SharedFlowImpl _packageChanged;
    public final Handler bgHandler;
    public final Context context;
    public boolean isActive;
    public final PackageUpdateLogger logger;
    public final SystemClock systemClock;
    public final UserHandle user;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public interface Factory {
        PackageUpdateMonitor create(UserHandle userHandle);
    }

    static {
        new Companion(null);
    }

    public PackageUpdateMonitor(UserHandle userHandle, CoroutineDispatcher coroutineDispatcher, Handler handler, Context context, CoroutineScope coroutineScope, PackageUpdateLogger packageUpdateLogger, SystemClock systemClock) {
        this.user = userHandle;
        this.bgHandler = handler;
        this.context = context;
        this.logger = packageUpdateLogger;
        this.systemClock = systemClock;
        SharedFlowImpl sharedFlowImplMutableSharedFlow$default = SharedFlowKt.MutableSharedFlow$default(0, 100, null, 4);
        final StateFlow subscriptionCount = sharedFlowImplMutableSharedFlow$default.getSubscriptionCount();
        FlowKt.launchIn(FlowKt.flowOn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.common.data.repository.PackageUpdateMonitor$_packageChanged$lambda$1$$inlined$map$1

            /* renamed from: com.android.systemui.common.data.repository.PackageUpdateMonitor$_packageChanged$lambda$1$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.common.data.repository.PackageUpdateMonitor$_packageChanged$lambda$1$$inlined$map$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
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
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        Boolean boolValueOf = Boolean.valueOf(((Number) obj).intValue() > 0);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(boolValueOf, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = subscriptionCount.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }), new PackageUpdateMonitor$_packageChanged$1$2(this, null)), coroutineDispatcher), coroutineScope);
        this._packageChanged = sharedFlowImplMutableSharedFlow$default;
    }

    public final void onPackageAdded(String str, int i) {
        super.onPackageAdded(str, i);
        this._packageChanged.tryEmit(new PackageChangeModel.Installed(str, i, this.systemClock.currentTimeMillis()));
    }

    public final boolean onPackageChanged(String str, int i, String[] strArr) {
        super.onPackageChanged(str, i, strArr);
        this._packageChanged.tryEmit(new PackageChangeModel.Changed(str, i, this.systemClock.currentTimeMillis()));
        return false;
    }

    public final void onPackageRemoved(String str, int i) {
        super.onPackageRemoved(str, i);
        this._packageChanged.tryEmit(new PackageChangeModel.Uninstalled(str, i, this.systemClock.currentTimeMillis()));
    }

    public final void onPackageUpdateFinished(String str, int i) {
        super.onPackageUpdateFinished(str, i);
        this._packageChanged.tryEmit(new PackageChangeModel.UpdateFinished(str, i, this.systemClock.currentTimeMillis()));
    }

    public final void onPackageUpdateStarted(String str, int i) {
        super.onPackageUpdateStarted(str, i);
        this._packageChanged.tryEmit(new PackageChangeModel.UpdateStarted(str, i, this.systemClock.currentTimeMillis()));
    }
}
