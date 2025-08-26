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
import com.android.systemui.util.kotlin.FlowKt;
import com.android.systemui.util.settings.SecureSettings;
import java.util.Collections;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.ResultKt;
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
        final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new FlowKt.AnonymousClass1(null), BroadcastDispatcher.broadcastFlow$default(this.broadcastDispatcher, new IntentFilter("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED"), UserHandle.ALL, 12));
        return new Flow() { // from class: com.android.systemui.communal.data.repository.CommunalSettingsRepositoryImpl$getAllowedByDevicePolicy$$inlined$map$1

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
                        Boolean boolValueOf = Boolean.valueOf((this.this$0.devicePolicyManager.getKeyguardDisabledFeatures(null, this.$user$inlined.id) & 1) == 0);
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
                Object objCollect = flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1.collect(new AnonymousClass2(flowCollector, this, userInfo), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
    }
}
