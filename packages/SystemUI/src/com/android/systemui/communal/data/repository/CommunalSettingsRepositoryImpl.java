package com.android.systemui.communal.data.repository;

import android.app.admin.DevicePolicyManager;
import android.content.IntentFilter;
import android.content.pm.UserInfo;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.communal.data.model.CommunalEnabledState;
import com.android.systemui.communal.data.model.DisabledReason;
import com.android.systemui.flags.FeatureFlagsClassic;
import com.android.systemui.flags.FeatureFlagsClassicRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.util.kotlin.FlowKt$emitOnStart$1;
import com.android.systemui.util.settings.SecureSettings;
import com.android.systemui.util.settings.SettingsProxyExt;
import java.util.EnumSet;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.internal.CombineKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class CommunalSettingsRepositoryImpl implements CommunalSettingsRepository {
    public final CoroutineDispatcher bgDispatcher;
    public final BroadcastDispatcher broadcastDispatcher;
    public final DevicePolicyManager devicePolicyManager;
    public final FeatureFlagsClassic featureFlagsClassic;
    public final Lazy flagEnabled$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$flagEnabled$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            if (((FeatureFlagsClassicRelease) CommunalSettingsRepositoryImpl.this.featureFlagsClassic).isEnabled(Flags.COMMUNAL_SERVICE_ENABLED)) {
                com.android.systemui.Flags.communalHub();
            }
            return Boolean.FALSE;
        }
    });
    public final SecureSettings secureSettings;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public CommunalSettingsRepositoryImpl(CoroutineDispatcher coroutineDispatcher, FeatureFlagsClassic featureFlagsClassic, SecureSettings secureSettings, BroadcastDispatcher broadcastDispatcher, DevicePolicyManager devicePolicyManager) {
        this.bgDispatcher = coroutineDispatcher;
        this.featureFlagsClassic = featureFlagsClassic;
        this.secureSettings = secureSettings;
        this.broadcastDispatcher = broadcastDispatcher;
        this.devicePolicyManager = devicePolicyManager;
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$getAllowedByDevicePolicy$$inlined$map$1] */
    public final CommunalSettingsRepositoryImpl$getAllowedByDevicePolicy$$inlined$map$1 getAllowedByDevicePolicy(final UserInfo userInfo) {
        final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new FlowKt$emitOnStart$1(null), BroadcastDispatcher.broadcastFlow$default(this.broadcastDispatcher, new IntentFilter("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED"), userInfo.getUserHandle(), 0, null, 12));
        return new Flow() { // from class: com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$getAllowedByDevicePolicy$$inlined$map$1

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$getAllowedByDevicePolicy$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ UserInfo $user$inlined;
                public final /* synthetic */ CommunalSettingsRepositoryImpl this$0;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$getAllowedByDevicePolicy$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, CommunalSettingsRepositoryImpl communalSettingsRepositoryImpl, UserInfo userInfo) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = communalSettingsRepositoryImpl;
                    this.$user$inlined = userInfo;
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
                        boolean r0 = r6 instanceof com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$getAllowedByDevicePolicy$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$getAllowedByDevicePolicy$$inlined$map$1$2$1 r0 = (com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$getAllowedByDevicePolicy$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$getAllowedByDevicePolicy$$inlined$map$1$2$1 r0 = new com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$getAllowedByDevicePolicy$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L56
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        kotlin.Unit r5 = (kotlin.Unit) r5
                        com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl r5 = r4.this$0
                        android.app.admin.DevicePolicyManager r5 = r5.devicePolicyManager
                        android.content.pm.UserInfo r6 = r4.$user$inlined
                        int r6 = r6.id
                        r2 = 0
                        int r5 = r5.getKeyguardDisabledFeatures(r2, r6)
                        r5 = r5 & r3
                        if (r5 != 0) goto L46
                        r5 = r3
                        goto L47
                    L46:
                        r5 = 0
                    L47:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L56
                        return r1
                    L56:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$getAllowedByDevicePolicy$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this, userInfo), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
    }

    public final Flow getEnabledState(final UserInfo userInfo) {
        if (!userInfo.isMain()) {
            return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(CommunalEnabledState.m939boximpl(EnumSet.of(DisabledReason.DISABLED_REASON_INVALID_USER)));
        }
        if (!((Boolean) this.flagEnabled$delegate.getValue()).booleanValue()) {
            return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(CommunalEnabledState.m939boximpl(EnumSet.of(DisabledReason.DISABLED_REASON_FLAG)));
        }
        final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new CommunalSettingsRepositoryImpl$getEnabledByUser$1(null), SettingsProxyExt.INSTANCE.observerFlow(this.secureSettings, userInfo.id, "glanceable_hub_enabled"));
        final Flow flow = new Flow() { // from class: com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$getEnabledByUser$$inlined$map$1

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$getEnabledByUser$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ UserInfo $user$inlined;
                public final /* synthetic */ CommunalSettingsRepositoryImpl this$0;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$getEnabledByUser$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, CommunalSettingsRepositoryImpl communalSettingsRepositoryImpl, UserInfo userInfo) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = communalSettingsRepositoryImpl;
                    this.$user$inlined = userInfo;
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
                        boolean r0 = r6 instanceof com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$getEnabledByUser$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$getEnabledByUser$$inlined$map$1$2$1 r0 = (com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$getEnabledByUser$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$getEnabledByUser$$inlined$map$1$2$1 r0 = new com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$getEnabledByUser$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L56
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        kotlin.Unit r5 = (kotlin.Unit) r5
                        com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl r5 = r4.this$0
                        com.android.systemui.util.settings.SecureSettings r5 = r5.secureSettings
                        android.content.pm.UserInfo r6 = r4.$user$inlined
                        int r6 = r6.id
                        java.lang.String r2 = "glanceable_hub_enabled"
                        int r5 = r5.getIntForUser(r2, r3, r6)
                        if (r5 != r3) goto L46
                        r5 = r3
                        goto L47
                    L46:
                        r5 = 0
                    L47:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L56
                        return r1
                    L56:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$getEnabledByUser$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this, userInfo), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        final DisabledReason disabledReason = DisabledReason.DISABLED_REASON_USER_SETTING;
        Flow flow2 = new Flow() { // from class: com.android.systemui.communal.data.repository.CommunalSettingsRepositoryKt$mapToReason$$inlined$map$1

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.communal.data.repository.CommunalSettingsRepositoryKt$mapToReason$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ DisabledReason $reason$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.communal.data.repository.CommunalSettingsRepositoryKt$mapToReason$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, DisabledReason disabledReason) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$reason$inlined = disabledReason;
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
                        boolean r0 = r6 instanceof com.android.systemui.communal.data.repository.CommunalSettingsRepositoryKt$mapToReason$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.communal.data.repository.CommunalSettingsRepositoryKt$mapToReason$$inlined$map$1$2$1 r0 = (com.android.systemui.communal.data.repository.CommunalSettingsRepositoryKt$mapToReason$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.communal.data.repository.CommunalSettingsRepositoryKt$mapToReason$$inlined$map$1$2$1 r0 = new com.android.systemui.communal.data.repository.CommunalSettingsRepositoryKt$mapToReason$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L49
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.lang.Boolean r5 = (java.lang.Boolean) r5
                        boolean r5 = r5.booleanValue()
                        if (r5 == 0) goto L3c
                        r5 = 0
                        goto L3e
                    L3c:
                        com.android.systemui.communal.data.model.DisabledReason r5 = r4.$reason$inlined
                    L3e:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L49
                        return r1
                    L49:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.data.repository.CommunalSettingsRepositoryKt$mapToReason$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, disabledReason), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        final CommunalSettingsRepositoryImpl$getAllowedByDevicePolicy$$inlined$map$1 allowedByDevicePolicy = getAllowedByDevicePolicy(userInfo);
        final DisabledReason disabledReason2 = DisabledReason.DISABLED_REASON_DEVICE_POLICY;
        final Flow[] flowArr = {flow2, new Flow() { // from class: com.android.systemui.communal.data.repository.CommunalSettingsRepositoryKt$mapToReason$$inlined$map$1

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.communal.data.repository.CommunalSettingsRepositoryKt$mapToReason$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ DisabledReason $reason$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.communal.data.repository.CommunalSettingsRepositoryKt$mapToReason$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, DisabledReason disabledReason) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$reason$inlined = disabledReason;
                }

                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj, Continuation continuation) {
                    /*
                        this = this;
                        boolean r0 = r6 instanceof com.android.systemui.communal.data.repository.CommunalSettingsRepositoryKt$mapToReason$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.communal.data.repository.CommunalSettingsRepositoryKt$mapToReason$$inlined$map$1$2$1 r0 = (com.android.systemui.communal.data.repository.CommunalSettingsRepositoryKt$mapToReason$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.communal.data.repository.CommunalSettingsRepositoryKt$mapToReason$$inlined$map$1$2$1 r0 = new com.android.systemui.communal.data.repository.CommunalSettingsRepositoryKt$mapToReason$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L49
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.lang.Boolean r5 = (java.lang.Boolean) r5
                        boolean r5 = r5.booleanValue()
                        if (r5 == 0) goto L3c
                        r5 = 0
                        goto L3e
                    L3c:
                        com.android.systemui.communal.data.model.DisabledReason r5 = r4.$reason$inlined
                    L3e:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L49
                        return r1
                    L49:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.data.repository.CommunalSettingsRepositoryKt$mapToReason$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, disabledReason2), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }};
        final Flow flow3 = new Flow() { // from class: com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$getEnabledState$$inlined$combine$1

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$getEnabledState$$inlined$combine$1$3, reason: invalid class name */
            public final class AnonymousClass3 extends SuspendLambda implements Function3 {
                private /* synthetic */ Object L$0;
                /* synthetic */ Object L$1;
                int label;

                public AnonymousClass3(Continuation continuation) {
                    super(3, continuation);
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    AnonymousClass3 anonymousClass3 = new AnonymousClass3((Continuation) obj3);
                    anonymousClass3.L$0 = (FlowCollector) obj;
                    anonymousClass3.L$1 = (Object[]) obj2;
                    return anonymousClass3.invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        FlowCollector flowCollector = (FlowCollector) this.L$0;
                        List filterNotNull = ArraysKt___ArraysKt.filterNotNull((DisabledReason[]) ((Object[]) this.L$1));
                        this.label = 1;
                        if (flowCollector.emit(filterNotNull, this) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                final Flow[] flowArr2 = flowArr;
                Object combineInternal = CombineKt.combineInternal(flowArr2, new Function0() { // from class: com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$getEnabledState$$inlined$combine$1.2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return new DisabledReason[flowArr2.length];
                    }
                }, new AnonymousClass3(null), flowCollector, continuation);
                return combineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? combineInternal : Unit.INSTANCE;
            }
        };
        final Flow flow4 = new Flow() { // from class: com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$getEnabledState$$inlined$map$1

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$getEnabledState$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$getEnabledState$$inlined$map$1$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$getEnabledState$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$getEnabledState$$inlined$map$1$2$1 r0 = (com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$getEnabledState$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$getEnabledState$$inlined$map$1$2$1 r0 = new com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$getEnabledState$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L52
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.util.List r5 = (java.util.List) r5
                        boolean r6 = r5.isEmpty()
                        if (r6 == 0) goto L41
                        java.lang.Class<com.android.systemui.communal.data.model.DisabledReason> r5 = com.android.systemui.communal.data.model.DisabledReason.class
                        java.util.EnumSet r5 = java.util.EnumSet.noneOf(r5)
                        goto L47
                    L41:
                        java.util.Collection r5 = (java.util.Collection) r5
                        java.util.EnumSet r5 = java.util.EnumSet.copyOf(r5)
                    L47:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L52
                        return r1
                    L52:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$getEnabledState$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        return FlowKt.flowOn(new Flow() { // from class: com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$getEnabledState$$inlined$map$2

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$getEnabledState$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$getEnabledState$$inlined$map$2$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$getEnabledState$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$getEnabledState$$inlined$map$2$2$1 r0 = (com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$getEnabledState$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$getEnabledState$$inlined$map$2$2$1 r0 = new com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$getEnabledState$$inlined$map$2$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L46
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.util.EnumSet r5 = (java.util.EnumSet) r5
                        kotlin.jvm.internal.Intrinsics.checkNotNull(r5)
                        com.android.systemui.communal.data.model.CommunalEnabledState r5 = com.android.systemui.communal.data.model.CommunalEnabledState.m939boximpl(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L46
                        return r1
                    L46:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$getEnabledState$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, this.bgDispatcher);
    }

    public final Flow getWidgetCategories(final UserInfo userInfo) {
        final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new CommunalSettingsRepositoryImpl$getWidgetCategories$1(null), SettingsProxyExt.INSTANCE.observerFlow(this.secureSettings, userInfo.id, "glanceable_hub_content_setting"));
        return FlowKt.flowOn(new Flow() { // from class: com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$getWidgetCategories$$inlined$map$1

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$getWidgetCategories$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ UserInfo $user$inlined;
                public final /* synthetic */ CommunalSettingsRepositoryImpl this$0;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$getWidgetCategories$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, CommunalSettingsRepositoryImpl communalSettingsRepositoryImpl, UserInfo userInfo) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = communalSettingsRepositoryImpl;
                    this.$user$inlined = userInfo;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r6, kotlin.coroutines.Continuation r7) {
                    /*
                        r5 = this;
                        boolean r0 = r7 instanceof com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$getWidgetCategories$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r7
                        com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$getWidgetCategories$$inlined$map$1$2$1 r0 = (com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$getWidgetCategories$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$getWidgetCategories$$inlined$map$1$2$1 r0 = new com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$getWidgetCategories$$inlined$map$1$2$1
                        r0.<init>(r7)
                    L18:
                        java.lang.Object r7 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r7)
                        goto L5c
                    L27:
                        java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                        java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                        r5.<init>(r6)
                        throw r5
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r7)
                        kotlin.Unit r6 = (kotlin.Unit) r6
                        com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl r6 = r5.this$0
                        com.android.systemui.util.settings.SecureSettings r6 = r6.secureSettings
                        com.android.systemui.communal.data.model.CommunalWidgetCategories$Companion r7 = com.android.systemui.communal.data.model.CommunalWidgetCategories.Companion
                        r7.getClass()
                        com.android.settingslib.flags.FeatureFlagsImpl r7 = com.android.settingslib.flags.Flags.FEATURE_FLAGS
                        r7.getClass()
                        android.content.pm.UserInfo r7 = r5.$user$inlined
                        int r7 = r7.id
                        java.lang.String r2 = "glanceable_hub_content_setting"
                        r4 = 2
                        int r6 = r6.getIntForUser(r2, r4, r7)
                        com.android.systemui.communal.data.model.CommunalWidgetCategories r6 = com.android.systemui.communal.data.model.CommunalWidgetCategories.m940boximpl(r6)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r5 = r5.$this_unsafeFlow
                        java.lang.Object r5 = r5.emit(r6, r0)
                        if (r5 != r1) goto L5c
                        return r1
                    L5c:
                        kotlin.Unit r5 = kotlin.Unit.INSTANCE
                        return r5
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$getWidgetCategories$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this, userInfo), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, this.bgDispatcher);
    }
}
