package com.android.systemui.keyguard.data.repository;

import android.content.res.Resources;
import com.android.systemui.R;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.shared.model.DevicePosture;
import com.android.systemui.util.kotlin.FlowDumperImpl;
import com.android.systemui.util.settings.repository.UserAwareSecureSettingsRepository;
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
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;

/* loaded from: classes2.dex */
public final class KeyguardBypassRepository extends FlowDumperImpl {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Lazy configFaceAuthSupportedPosture$delegate;
    public final Flow isBypassAvailable;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[DevicePosture.values().length];
            try {
                iArr[DevicePosture.UNKNOWN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x006b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public KeyguardBypassRepository(final Resources resources, BiometricSettingsRepository biometricSettingsRepository, DevicePostureRepository devicePostureRepository, DumpManager dumpManager, UserAwareSecureSettingsRepository userAwareSecureSettingsRepository, CoroutineDispatcher coroutineDispatcher) {
        FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
        Flow flowDistinctUntilChanged;
        super(dumpManager, null, 2, null);
        final int i = 0;
        Lazy lazy = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.data.repository.KeyguardBypassRepository$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() throws Resources.NotFoundException {
                int i2 = i;
                Resources resources2 = resources;
                switch (i2) {
                    case 0:
                        int i3 = KeyguardBypassRepository.$r8$clinit;
                        return Integer.valueOf(resources2.getInteger(R.integer.config_face_unlock_bypass_override));
                    default:
                        int i4 = KeyguardBypassRepository.$r8$clinit;
                        DevicePosture.Companion companion = DevicePosture.Companion;
                        int integer = resources2.getInteger(R.integer.config_face_auth_supported_posture);
                        companion.getClass();
                        return DevicePosture.Companion.toPosture(integer);
                }
            }
        });
        final int i2 = 1;
        Lazy lazy2 = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.data.repository.KeyguardBypassRepository$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() throws Resources.NotFoundException {
                int i22 = i2;
                Resources resources2 = resources;
                switch (i22) {
                    case 0:
                        int i3 = KeyguardBypassRepository.$r8$clinit;
                        return Integer.valueOf(resources2.getInteger(R.integer.config_face_unlock_bypass_override));
                    default:
                        int i4 = KeyguardBypassRepository.$r8$clinit;
                        DevicePosture.Companion companion = DevicePosture.Companion;
                        int integer = resources2.getInteger(R.integer.config_face_auth_supported_posture);
                        companion.getClass();
                        return DevicePosture.Companion.toPosture(integer);
                }
            }
        });
        this.configFaceAuthSupportedPosture$delegate = lazy2;
        Flow flowDumpWhileCollecting = dumpWhileCollecting(FlowKt.flowOn(userAwareSecureSettingsRepository.boolSetting("face_unlock_dismisses_keyguard", resources.getBoolean(android.R.bool.config_isPreApprovalRequestAvailable)), coroutineDispatcher), "bypassEnabledSetting");
        int iIntValue = ((Number) lazy.getValue()).intValue();
        if (iIntValue != 1) {
            flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = iIntValue == 2 ? new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE) : flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
            if (WhenMappings.$EnumSwitchMapping$0[((DevicePosture) lazy2.getValue()).ordinal()] != 1) {
                flowDistinctUntilChanged = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.TRUE);
            } else {
                final Flow currentDevicePosture = ((DevicePostureRepositoryImpl) devicePostureRepository).getCurrentDevicePosture();
                flowDistinctUntilChanged = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.keyguard.data.repository.KeyguardBypassRepository$special$$inlined$map$1

                    /* renamed from: com.android.systemui.keyguard.data.repository.KeyguardBypassRepository$special$$inlined$map$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;
                        public final /* synthetic */ KeyguardBypassRepository this$0;

                        /* renamed from: com.android.systemui.keyguard.data.repository.KeyguardBypassRepository$special$$inlined$map$1$2$1, reason: invalid class name */
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

                        public AnonymousClass2(FlowCollector flowCollector, KeyguardBypassRepository keyguardBypassRepository) {
                            this.$this_unsafeFlow = flowCollector;
                            this.this$0 = keyguardBypassRepository;
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
                                DevicePosture devicePosture = (DevicePosture) obj;
                                int i3 = KeyguardBypassRepository.$r8$clinit;
                                Boolean boolValueOf = Boolean.valueOf(devicePosture == ((DevicePosture) this.this$0.configFaceAuthSupportedPosture$delegate.getValue()));
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
                        Object objCollect = currentDevicePosture.collect(new AnonymousClass2(flowCollector, this), continuation);
                        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                    }
                });
            }
            this.isBypassAvailable = dumpWhileCollecting(FlowKt.distinctUntilChanged(FlowKt.combine(flowDumpWhileCollecting, ((BiometricSettingsRepositoryImpl) biometricSettingsRepository).isFaceAuthEnrolledAndEnabled, flowDistinctUntilChanged, new KeyguardBypassRepository$isBypassAvailable$1(null))), "isBypassAvailable");
        }
        flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.TRUE);
        flowDumpWhileCollecting = flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
        if (WhenMappings.$EnumSwitchMapping$0[((DevicePosture) lazy2.getValue()).ordinal()] != 1) {
        }
        this.isBypassAvailable = dumpWhileCollecting(FlowKt.distinctUntilChanged(FlowKt.combine(flowDumpWhileCollecting, ((BiometricSettingsRepositoryImpl) biometricSettingsRepository).isFaceAuthEnrolledAndEnabled, flowDistinctUntilChanged, new KeyguardBypassRepository$isBypassAvailable$1(null))), "isBypassAvailable");
    }
}
