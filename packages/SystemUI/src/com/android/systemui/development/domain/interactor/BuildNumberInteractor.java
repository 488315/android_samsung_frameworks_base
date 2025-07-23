package com.android.systemui.development.domain.interactor;

import android.R;
import android.content.res.Resources;
import android.os.Build;
import com.android.systemui.development.data.repository.DevelopmentSettingRepository;
import com.android.systemui.user.data.repository.UserRepository;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import com.android.systemui.user.data.repository.UserRepositoryImpl$special$$inlined$map$2;
import com.android.systemui.user.utils.UserScopedService;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__MergeKt;
import kotlinx.coroutines.flow.FlowKt__MergeKt$flatMapConcat$$inlined$map$1;
import kotlinx.coroutines.flow.FlowKt__MergeKt$flattenConcat$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class BuildNumberInteractor {
    public final CoroutineDispatcher backgroundDispatcher;
    public final ReadonlyStateFlow buildNumber;
    public final String buildText;
    public final String clipLabel;
    public final UserScopedService clipboardManagerProvider;
    public final UserRepository userRepository;

    public BuildNumberInteractor(DevelopmentSettingRepository developmentSettingRepository, Resources resources, UserRepository userRepository, UserScopedService userScopedService, CoroutineDispatcher coroutineDispatcher, CoroutineScope coroutineScope) {
        this.userRepository = userRepository;
        this.clipboardManagerProvider = userScopedService;
        this.backgroundDispatcher = coroutineDispatcher;
        UserRepositoryImpl$special$$inlined$map$2 userRepositoryImpl$special$$inlined$map$2 = ((UserRepositoryImpl) userRepository).selectedUserInfo;
        BuildNumberInteractor$buildNumber$1 buildNumberInteractor$buildNumber$1 = new BuildNumberInteractor$buildNumber$1(developmentSettingRepository, null);
        int i = FlowKt__MergeKt.$r8$clinit;
        final FlowKt__MergeKt$flattenConcat$$inlined$unsafeFlow$1 flowKt__MergeKt$flattenConcat$$inlined$unsafeFlow$1 = new FlowKt__MergeKt$flattenConcat$$inlined$unsafeFlow$1(new FlowKt__MergeKt$flatMapConcat$$inlined$map$1(userRepositoryImpl$special$$inlined$map$2, buildNumberInteractor$buildNumber$1));
        this.buildNumber = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.development.domain.interactor.BuildNumberInteractor$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.development.domain.interactor.BuildNumberInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ BuildNumberInteractor this$0;

                /* renamed from: com.android.systemui.development.domain.interactor.BuildNumberInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, BuildNumberInteractor buildNumberInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = buildNumberInteractor;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.development.domain.interactor.BuildNumberInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.development.domain.interactor.BuildNumberInteractor$special$$inlined$map$1$2$1 r0 = (com.android.systemui.development.domain.interactor.BuildNumberInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.development.domain.interactor.BuildNumberInteractor$special$$inlined$map$1$2$1 r0 = new com.android.systemui.development.domain.interactor.BuildNumberInteractor$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L5c
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.lang.Boolean r5 = (java.lang.Boolean) r5
                        boolean r5 = r5.booleanValue()
                        com.android.systemui.development.domain.interactor.BuildNumberInteractor r6 = r4.this$0
                        java.lang.String r6 = r6.buildText
                        com.android.systemui.development.shared.model.BuildNumber r6 = com.android.systemui.development.shared.model.BuildNumber.m2549boximpl(r6)
                        r2 = 0
                        if (r5 == 0) goto L44
                        goto L45
                    L44:
                        r6 = r2
                    L45:
                        if (r6 == 0) goto L4a
                        java.lang.String r5 = r6.value
                        goto L4b
                    L4a:
                        r5 = r2
                    L4b:
                        if (r5 == 0) goto L51
                        com.android.systemui.development.shared.model.BuildNumber r2 = com.android.systemui.development.shared.model.BuildNumber.m2549boximpl(r5)
                    L51:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r2, r0)
                        if (r4 != r1) goto L5c
                        return r1
                    L5c:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.development.domain.interactor.BuildNumberInteractor$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), null);
        this.buildText = resources.getString(R.string.config_managed_provisioning_package, Build.VERSION.RELEASE_OR_CODENAME, Build.ID);
        this.clipLabel = resources.getString(com.android.systemui.R.string.build_number_clip_data_label);
    }

    public final Object copyBuildNumber(Continuation continuation) {
        Object withContext = BuildersKt.withContext(this.backgroundDispatcher, new BuildNumberInteractor$copyBuildNumber$2(this, null), continuation);
        return withContext == CoroutineSingletons.COROUTINE_SUSPENDED ? withContext : Unit.INSTANCE;
    }
}
