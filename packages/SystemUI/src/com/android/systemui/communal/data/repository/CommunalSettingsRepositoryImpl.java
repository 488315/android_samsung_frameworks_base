package com.android.systemui.communal.data.repository;

import android.R;
import android.app.admin.DevicePolicyManager;
import android.content.IntentFilter;
import android.content.pm.UserInfo;
import android.content.res.Resources;
import android.os.UserHandle;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.communal.data.model.SuppressionReason;
import com.android.systemui.communal.shared.model.CommunalBackgroundType;
import com.android.systemui.flags.FeatureFlagsClassic;
import com.android.systemui.util.kotlin.FlowKt$emitOnStart$1;
import com.android.systemui.util.settings.SecureSettings;
import java.util.Collections;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class CommunalSettingsRepositoryImpl implements CommunalSettingsRepository {
    public final StateFlowImpl _suppressionReasons = StateFlowKt.MutableStateFlow(Collections.singletonList(new SuppressionReason.ReasonUnknown(7)));
    public final CoroutineDispatcher bgDispatcher;
    public final BroadcastDispatcher broadcastDispatcher;
    public final CommunalBackgroundType defaultBackgroundType;
    public final DevicePolicyManager devicePolicyManager;
    public final Lazy dreamsActivatedOnDockByDefault$delegate;
    public final Lazy dreamsActivatedOnPosturedByDefault$delegate;
    public final Lazy dreamsActivatedOnSleepByDefault$delegate;
    public final FeatureFlagsClassic featureFlagsClassic;
    public final Resources resources;
    public final SecureSettings secureSettings;
    public final Lazy whenToStartHubByDefault$delegate;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public CommunalSettingsRepositoryImpl(CoroutineDispatcher coroutineDispatcher, Resources resources, FeatureFlagsClassic featureFlagsClassic, SecureSettings secureSettings, BroadcastDispatcher broadcastDispatcher, DevicePolicyManager devicePolicyManager, CommunalBackgroundType communalBackgroundType) {
        this.bgDispatcher = coroutineDispatcher;
        this.resources = resources;
        this.featureFlagsClassic = featureFlagsClassic;
        this.secureSettings = secureSettings;
        this.broadcastDispatcher = broadcastDispatcher;
        this.devicePolicyManager = devicePolicyManager;
        this.defaultBackgroundType = communalBackgroundType;
        final int i = 0;
        this.dreamsActivatedOnSleepByDefault$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$$ExternalSyntheticLambda0
            public final /* synthetic */ CommunalSettingsRepositoryImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i) {
                    case 0:
                        return Boolean.valueOf(this.f$0.resources.getBoolean(R.bool.config_enableActivityRecognitionHardwareOverlay));
                    case 1:
                        return Boolean.valueOf(this.f$0.resources.getBoolean(R.bool.config_earcFeatureEnabled_default));
                    case 2:
                        return Boolean.valueOf(this.f$0.resources.getBoolean(R.bool.config_emergencyGestureEnabled));
                    default:
                        return Integer.valueOf(this.f$0.resources.getInteger(17695128));
                }
            }
        });
        final int i2 = 1;
        this.dreamsActivatedOnDockByDefault$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$$ExternalSyntheticLambda0
            public final /* synthetic */ CommunalSettingsRepositoryImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i2) {
                    case 0:
                        return Boolean.valueOf(this.f$0.resources.getBoolean(R.bool.config_enableActivityRecognitionHardwareOverlay));
                    case 1:
                        return Boolean.valueOf(this.f$0.resources.getBoolean(R.bool.config_earcFeatureEnabled_default));
                    case 2:
                        return Boolean.valueOf(this.f$0.resources.getBoolean(R.bool.config_emergencyGestureEnabled));
                    default:
                        return Integer.valueOf(this.f$0.resources.getInteger(17695128));
                }
            }
        });
        final int i3 = 2;
        this.dreamsActivatedOnPosturedByDefault$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$$ExternalSyntheticLambda0
            public final /* synthetic */ CommunalSettingsRepositoryImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i3) {
                    case 0:
                        return Boolean.valueOf(this.f$0.resources.getBoolean(R.bool.config_enableActivityRecognitionHardwareOverlay));
                    case 1:
                        return Boolean.valueOf(this.f$0.resources.getBoolean(R.bool.config_earcFeatureEnabled_default));
                    case 2:
                        return Boolean.valueOf(this.f$0.resources.getBoolean(R.bool.config_emergencyGestureEnabled));
                    default:
                        return Integer.valueOf(this.f$0.resources.getInteger(17695128));
                }
            }
        });
        final int i4 = 3;
        this.whenToStartHubByDefault$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$$ExternalSyntheticLambda0
            public final /* synthetic */ CommunalSettingsRepositoryImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i4) {
                    case 0:
                        return Boolean.valueOf(this.f$0.resources.getBoolean(R.bool.config_enableActivityRecognitionHardwareOverlay));
                    case 1:
                        return Boolean.valueOf(this.f$0.resources.getBoolean(R.bool.config_earcFeatureEnabled_default));
                    case 2:
                        return Boolean.valueOf(this.f$0.resources.getBoolean(R.bool.config_emergencyGestureEnabled));
                    default:
                        return Integer.valueOf(this.f$0.resources.getInteger(17695128));
                }
            }
        });
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$getAllowedByDevicePolicy$$inlined$map$1] */
    public final CommunalSettingsRepositoryImpl$getAllowedByDevicePolicy$$inlined$map$1 getAllowedByDevicePolicy(final UserInfo userInfo) {
        final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new FlowKt$emitOnStart$1(null), BroadcastDispatcher.broadcastFlow$default(this.broadcastDispatcher, new IntentFilter("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED"), UserHandle.ALL, 12));
        return new Flow() { // from class: com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$getAllowedByDevicePolicy$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$getAllowedByDevicePolicy$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ UserInfo $user$inlined;
                public final /* synthetic */ CommunalSettingsRepositoryImpl this$0;

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
}
