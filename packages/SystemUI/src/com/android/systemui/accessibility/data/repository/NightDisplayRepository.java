package com.android.systemui.accessibility.data.repository;

import android.hardware.display.ColorDisplayManager;
import android.os.UserHandle;
import com.android.systemui.accessibility.data.model.NightDisplayChangeEvent;
import com.android.systemui.accessibility.data.model.NightDisplayState;
import com.android.systemui.dagger.NightDisplayListenerModule$Builder;
import com.android.systemui.statusbar.policy.LocationController;
import com.android.systemui.statusbar.policy.LocationControllerImpl;
import com.android.systemui.user.utils.UserScopedService;
import com.android.systemui.user.utils.UserScopedServiceImpl;
import com.android.systemui.util.kotlin.LocationControllerExtKt;
import com.android.systemui.util.settings.GlobalSettings;
import com.android.systemui.util.settings.SecureSettings;
import com.android.systemui.util.settings.SettingsProxyExt;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.CallbackFlowBuilder;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$runningFold$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.SharingStarted;

/* loaded from: classes.dex */
public final class NightDisplayRepository {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final CoroutineContext bgCoroutineContext;
    public final UserScopedService colorDisplayManagerUserScopedService;
    public final GlobalSettings globalSettings;
    public final Flow isForceAutoModeAvailable;
    public final LocationController locationController;
    public final NightDisplayListenerModule$Builder nightDisplayListenerBuilder;
    public final CoroutineScope scope;
    public final SecureSettings secureSettings;
    public final Map stateFlowUserMap = new LinkedHashMap();

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.accessibility.data.repository.NightDisplayRepository$setNightDisplayActivated$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ boolean $activated;
        final /* synthetic */ UserHandle $user;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(UserHandle userHandle, boolean z, Continuation continuation) {
            super(2, continuation);
            this.$user = userHandle;
            this.$activated = z;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return NightDisplayRepository.this.new AnonymousClass2(this.$user, this.$activated, continuation);
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
            ((ColorDisplayManager) ((UserScopedServiceImpl) NightDisplayRepository.this.colorDisplayManagerUserScopedService).forUser(this.$user)).setNightDisplayActivated(this.$activated);
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.accessibility.data.repository.NightDisplayRepository$setNightDisplayAutoMode$2, reason: invalid class name and case insensitive filesystem */
    final class C07842 extends SuspendLambda implements Function2 {
        final /* synthetic */ int $autoMode;
        final /* synthetic */ UserHandle $user;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C07842(UserHandle userHandle, int i, Continuation continuation) {
            super(2, continuation);
            this.$user = userHandle;
            this.$autoMode = i;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return NightDisplayRepository.this.new C07842(this.$user, this.$autoMode, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C07842) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            ((ColorDisplayManager) ((UserScopedServiceImpl) NightDisplayRepository.this.colorDisplayManagerUserScopedService).forUser(this.$user)).setNightDisplayAutoMode(this.$autoMode);
            return Unit.INSTANCE;
        }
    }

    static {
        new Companion(null);
    }

    public NightDisplayRepository(CoroutineContext coroutineContext, CoroutineScope coroutineScope, GlobalSettings globalSettings, SecureSettings secureSettings, NightDisplayListenerModule$Builder nightDisplayListenerModule$Builder, UserScopedService userScopedService, LocationController locationController) {
        this.bgCoroutineContext = coroutineContext;
        this.scope = coroutineScope;
        this.globalSettings = globalSettings;
        this.secureSettings = secureSettings;
        this.nightDisplayListenerBuilder = nightDisplayListenerModule$Builder;
        this.colorDisplayManagerUserScopedService = userScopedService;
        this.locationController = locationController;
        final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new NightDisplayRepository$isForceAutoModeAvailable$1(null), SettingsProxyExt.INSTANCE.observerFlow(globalSettings, "night_display_forced_auto_mode_available"));
        this.isForceAutoModeAvailable = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.accessibility.data.repository.NightDisplayRepository$special$$inlined$map$1

            /* renamed from: com.android.systemui.accessibility.data.repository.NightDisplayRepository$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ NightDisplayRepository this$0;

                /* renamed from: com.android.systemui.accessibility.data.repository.NightDisplayRepository$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, NightDisplayRepository nightDisplayRepository) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = nightDisplayRepository;
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
                        Boolean boolValueOf = Boolean.valueOf(Intrinsics.areEqual(this.this$0.globalSettings.getString("night_display_forced_auto_mode_available"), "1"));
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
                Object objCollect = flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x003e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final NightDisplayState initialState(UserHandle userHandle) {
        boolean z;
        ColorDisplayManager colorDisplayManager = (ColorDisplayManager) ((UserScopedServiceImpl) this.colorDisplayManagerUserScopedService).forUser(userHandle);
        int nightDisplayAutoMode = colorDisplayManager.getNightDisplayAutoMode();
        boolean zIsNightDisplayActivated = colorDisplayManager.isNightDisplayActivated();
        LocalTime nightDisplayCustomStartTime = colorDisplayManager.getNightDisplayCustomStartTime();
        LocalTime nightDisplayCustomEndTime = colorDisplayManager.getNightDisplayCustomEndTime();
        if (Intrinsics.areEqual(this.globalSettings.getString("night_display_forced_auto_mode_available"), "1")) {
            z = this.secureSettings.getIntForUser("night_display_auto_mode", -1, userHandle.getIdentifier()) == -1;
        }
        return new NightDisplayState(nightDisplayAutoMode, zIsNightDisplayActivated, nightDisplayCustomStartTime, nightDisplayCustomEndTime, z, ((LocationControllerImpl) this.locationController).isLocationEnabled$1());
    }

    public final Flow nightDisplayState(final UserHandle userHandle) {
        Flow flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
        Object obj = ((LinkedHashMap) this.stateFlowUserMap).get(Integer.valueOf(userHandle.getIdentifier()));
        if (obj != null) {
            return (Flow) obj;
        }
        CallbackFlowBuilder callbackFlowBuilderCallbackFlow = FlowKt.callbackFlow(new NightDisplayRepository$colorDisplayManagerChangeEventFlow$1(this, userHandle, null));
        if (userHandle.getIdentifier() == -10000) {
            flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.TRUE);
        } else {
            final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new NightDisplayRepository$isDisplayAutoModeRawNotSet$1(null), SettingsProxyExt.INSTANCE.observerFlow(this.secureSettings, userHandle.getIdentifier(), "night_display_auto_mode"));
            flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new Flow() { // from class: com.android.systemui.accessibility.data.repository.NightDisplayRepository$isDisplayAutoModeRawNotSet$$inlined$map$1

                /* renamed from: com.android.systemui.accessibility.data.repository.NightDisplayRepository$isDisplayAutoModeRawNotSet$$inlined$map$1$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;
                    public final /* synthetic */ UserHandle $userHandle$inlined;
                    public final /* synthetic */ NightDisplayRepository this$0;

                    /* renamed from: com.android.systemui.accessibility.data.repository.NightDisplayRepository$isDisplayAutoModeRawNotSet$$inlined$map$1$2$1, reason: invalid class name */
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

                    public AnonymousClass2(FlowCollector flowCollector, NightDisplayRepository nightDisplayRepository, UserHandle userHandle) {
                        this.$this_unsafeFlow = flowCollector;
                        this.this$0 = nightDisplayRepository;
                        this.$userHandle$inlined = userHandle;
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
                            int identifier = this.$userHandle$inlined.getIdentifier();
                            int i3 = NightDisplayRepository.$r8$clinit;
                            Boolean boolValueOf = Boolean.valueOf(this.this$0.secureSettings.getIntForUser("night_display_auto_mode", -1, identifier) == -1);
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
                    Object objCollect = flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1.collect(new AnonymousClass2(flowCollector, this, userHandle), continuation);
                    return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                }
            };
        }
        final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(this.isForceAutoModeAvailable, FlowKt.distinctUntilChanged(flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2), new NightDisplayRepository$shouldForceAutoMode$1(null));
        Flow flow = new Flow() { // from class: com.android.systemui.accessibility.data.repository.NightDisplayRepository$nightDisplayState$lambda$2$$inlined$map$1

            /* renamed from: com.android.systemui.accessibility.data.repository.NightDisplayRepository$nightDisplayState$lambda$2$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.accessibility.data.repository.NightDisplayRepository$nightDisplayState$lambda$2$$inlined$map$1$2$1, reason: invalid class name */
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
                        NightDisplayChangeEvent.OnForceAutoModeChanged onForceAutoModeChanged = new NightDisplayChangeEvent.OnForceAutoModeChanged(((Boolean) obj).booleanValue());
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(onForceAutoModeChanged, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = flowKt__ZipKt$combine$$inlined$unsafeFlow$1.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        final Flow flowIsLocationEnabledFlow = LocationControllerExtKt.isLocationEnabledFlow(this.locationController);
        return FlowKt.stateIn(FlowKt.flowOn(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new NightDisplayRepository$nightDisplayState$1$4(this, userHandle, null), FlowKt.buffer$default(new FlowKt__TransformKt$runningFold$$inlined$unsafeFlow$1(initialState(userHandle), FlowKt.merge(callbackFlowBuilderCallbackFlow, flow, new Flow() { // from class: com.android.systemui.accessibility.data.repository.NightDisplayRepository$nightDisplayState$lambda$2$$inlined$map$2

            /* renamed from: com.android.systemui.accessibility.data.repository.NightDisplayRepository$nightDisplayState$lambda$2$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.accessibility.data.repository.NightDisplayRepository$nightDisplayState$lambda$2$$inlined$map$2$2$1, reason: invalid class name */
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
                        NightDisplayChangeEvent.OnLocationEnabledChanged onLocationEnabledChanged = new NightDisplayChangeEvent.OnLocationEnabledChanged(((Boolean) obj).booleanValue());
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(onLocationEnabledChanged, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = flowIsLocationEnabledFlow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }), new NightDisplayRepository$nightDisplayState$1$3(null)), -1, 2)), this.bgCoroutineContext), this.scope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), new NightDisplayState(0, false, null, null, false, false, 63, null));
    }

    public final Object setNightDisplayActivated(UserHandle userHandle, Continuation continuation, boolean z) throws Throwable {
        Object objWithContext = BuildersKt.withContext(this.bgCoroutineContext, new AnonymousClass2(userHandle, z, null), continuation);
        return objWithContext == CoroutineSingletons.COROUTINE_SUSPENDED ? objWithContext : Unit.INSTANCE;
    }

    public final Object setNightDisplayAutoMode(UserHandle userHandle, Continuation continuation) throws Throwable {
        Object objWithContext = BuildersKt.withContext(this.bgCoroutineContext, new C07842(userHandle, 1, null), continuation);
        return objWithContext == CoroutineSingletons.COROUTINE_SUSPENDED ? objWithContext : Unit.INSTANCE;
    }
}
