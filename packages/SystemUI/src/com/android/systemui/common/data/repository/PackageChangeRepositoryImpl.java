package com.android.systemui.common.data.repository;

import android.os.UserHandle;
import com.android.systemui.common.data.repository.PackageUpdateMonitor;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

public final class PackageChangeRepositoryImpl implements PackageChangeRepository {
    public final Lazy monitor$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.common.data.repository.PackageChangeRepositoryImpl$monitor$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return PackageChangeRepositoryImpl.this.monitorFactory.create(UserHandle.ALL);
        }
    });
    public final PackageUpdateMonitor.Factory monitorFactory;
    public final ReadonlyStateFlow packageInstallSessionsForPrimaryUser;

    public PackageChangeRepositoryImpl(PackageInstallerMonitor packageInstallerMonitor, PackageUpdateMonitor.Factory factory) {
        this.monitorFactory = factory;
        this.packageInstallSessionsForPrimaryUser = packageInstallerMonitor.installSessionsForPrimaryUser;
    }

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

                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r6, kotlin.coroutines.Continuation r7) {
                    /*
                        r5 = this;
                        boolean r0 = r7 instanceof com.android.systemui.common.data.repository.PackageChangeRepositoryImpl$packageChanged$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r7
                        com.android.systemui.common.data.repository.PackageChangeRepositoryImpl$packageChanged$$inlined$filter$1$2$1 r0 = (com.android.systemui.common.data.repository.PackageChangeRepositoryImpl$packageChanged$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.common.data.repository.PackageChangeRepositoryImpl$packageChanged$$inlined$filter$1$2$1 r0 = new com.android.systemui.common.data.repository.PackageChangeRepositoryImpl$packageChanged$$inlined$filter$1$2$1
                        r0.<init>(r7)
                    L18:
                        java.lang.Object r7 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r7)
                        goto L5a
                    L27:
                        java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                        java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                        r5.<init>(r6)
                        throw r5
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r7)
                        r7 = r6
                        com.android.systemui.common.shared.model.PackageChangeModel r7 = (com.android.systemui.common.shared.model.PackageChangeModel) r7
                        android.os.UserHandle r2 = r5.$user$inlined
                        android.os.UserHandle r4 = android.os.UserHandle.ALL
                        boolean r2 = kotlin.jvm.internal.Intrinsics.areEqual(r2, r4)
                        if (r2 != 0) goto L4f
                        android.os.UserHandle r2 = r5.$user$inlined
                        int r7 = r7.getPackageUid()
                        android.os.UserHandle r7 = android.os.UserHandle.getUserHandleForUid(r7)
                        boolean r7 = kotlin.jvm.internal.Intrinsics.areEqual(r2, r7)
                        if (r7 == 0) goto L5a
                    L4f:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r5 = r5.$this_unsafeFlow
                        java.lang.Object r5 = r5.emit(r6, r0)
                        if (r5 != r1) goto L5a
                        return r1
                    L5a:
                        kotlin.Unit r5 = kotlin.Unit.INSTANCE
                        return r5
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.common.data.repository.PackageChangeRepositoryImpl$packageChanged$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, userHandle), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
    }
}
