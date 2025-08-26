package com.android.systemui.common.data.repository;

import android.os.UserHandle;
import com.android.systemui.common.data.repository.PackageUpdateMonitor;
import com.android.systemui.common.shared.model.PackageChangeModel;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes.dex */
public final class PackageChangeRepositoryImpl implements PackageChangeRepository {
    public final Lazy monitor$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.common.data.repository.PackageChangeRepositoryImpl$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return this.f$0.monitorFactory.create(UserHandle.ALL);
        }
    });
    public final PackageUpdateMonitor.Factory monitorFactory;
    public final ReadonlyStateFlow packageInstallSessionsForPrimaryUser;

    public PackageChangeRepositoryImpl(PackageInstallerMonitor packageInstallerMonitor, PackageUpdateMonitor.Factory factory) {
        this.monitorFactory = factory;
        this.packageInstallSessionsForPrimaryUser = packageInstallerMonitor.installSessionsForPrimaryUser;
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.common.data.repository.PackageChangeRepositoryImpl$packageChanged$$inlined$filter$1] */
    public final PackageChangeRepositoryImpl$packageChanged$$inlined$filter$1 packageChanged(final UserHandle userHandle) {
        PackageUpdateMonitor packageUpdateMonitor = (PackageUpdateMonitor) this.monitor$delegate.getValue();
        final FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(packageUpdateMonitor._packageChanged, new PackageUpdateMonitor$packageChanged$1(packageUpdateMonitor.logger));
        return new Flow() { // from class: com.android.systemui.common.data.repository.PackageChangeRepositoryImpl$packageChanged$$inlined$filter$1

            /* renamed from: com.android.systemui.common.data.repository.PackageChangeRepositoryImpl$packageChanged$$inlined$filter$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ UserHandle $user$inlined;

                /* renamed from: com.android.systemui.common.data.repository.PackageChangeRepositoryImpl$packageChanged$$inlined$filter$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    Object L$1;
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

                public AnonymousClass2(FlowCollector flowCollector, UserHandle userHandle) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$user$inlined = userHandle;
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
                        PackageChangeModel packageChangeModel = (PackageChangeModel) obj;
                        if (Intrinsics.areEqual(this.$user$inlined, UserHandle.ALL) || Intrinsics.areEqual(this.$user$inlined, UserHandle.getUserHandleForUid(packageChangeModel.getPackageUid()))) {
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
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
                Object objCollect = flowKt__TransformKt$onEach$$inlined$unsafeTransform$1.collect(new AnonymousClass2(flowCollector, userHandle), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
    }
}
