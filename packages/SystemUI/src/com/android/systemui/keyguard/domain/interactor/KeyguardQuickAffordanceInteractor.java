package com.android.systemui.keyguard.domain.interactor;

import android.app.ActivityOptions;
import android.app.ActivityTaskManager;
import android.app.AlertDialog;
import android.app.IActivityTaskManager;
import android.app.IApplicationThread;
import android.app.ProfilerInfo;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.util.StatsEvent;
import android.util.StatsLog;
import android.view.accessibility.AccessibilityManager;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.logging.KeyguardQuickAffordancesLogger;
import com.android.keyguard.logging.KeyguardQuickAffordancesLogger$$ExternalSyntheticLambda0;
import com.android.systemui.R;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.animation.Expandable;
import com.android.systemui.animation.TransitionAnimator;
import com.android.systemui.devicepolicy.DevicePolicyManagerExtKt;
import com.android.systemui.dock.DockManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsClassicRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.flags.ReleasedFlag;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceSelectionManager;
import com.android.systemui.keyguard.data.repository.BiometricSettingsRepository;
import com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryImpl;
import com.android.systemui.keyguard.data.repository.KeyguardQuickAffordanceRepository;
import com.android.systemui.keyguard.domain.model.KeyguardQuickAffordanceModel;
import com.android.systemui.keyguard.shared.model.KeyguardPickerFlag;
import com.android.systemui.keyguard.shared.model.KeyguardQuickAffordancePickerRepresentation;
import com.android.systemui.keyguard.shared.model.KeyguardSlotPickerRepresentation;
import com.android.systemui.keyguard.shared.quickaffordance.KeyguardQuickAffordancePosition;
import com.android.systemui.keyguard.shared.quickaffordance.KeyguardQuickAffordancesMetricsLogger;
import com.android.systemui.keyguard.shared.quickaffordance.KeyguardQuickAffordancesMetricsLoggerImpl;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.statusbar.KeyguardShortcutManager;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function6;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.flow.internal.CombineKt;

/* loaded from: classes2.dex */
public final class KeyguardQuickAffordanceInteractor {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final StateFlowImpl _launchingFromTriggeredResult;
    public final AccessibilityManager accessibilityManager;
    public final ActivityStarter activityStarter;
    public final Context appContext;
    public final CoroutineDispatcher backgroundDispatcher;
    public final BiometricSettingsRepository biometricSettingsRepository;
    public final DevicePolicyManager devicePolicyManager;
    public final DockManager dockManager;
    public final FeatureFlags featureFlags;
    public final KeyguardInteractor keyguardInteractor;
    public final Lazy keyguardShortcutManager;
    public final KeyguardStateController keyguardStateController;
    public final DialogTransitionAnimator launchAnimator;
    public final ReadonlyStateFlow launchingAffordance;
    public final ReadonlyStateFlow launchingFromTriggeredResult;
    public final KeyguardQuickAffordancesLogger logger;
    public final KeyguardQuickAffordancesMetricsLogger metricsLogger;
    public final Lazy repository;
    public final ShadeInteractor shadeInteractor;
    public final UserTracker userTracker;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$getPickerFlags$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        int I$0;
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return KeyguardQuickAffordanceInteractor.this.getPickerFlags(this);
        }
    }

    /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$getSelections$1, reason: invalid class name and case insensitive filesystem */
    final class C09051 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        public C09051(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return KeyguardQuickAffordanceInteractor.this.getSelections(this);
        }
    }

    /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$getSlotPickerRepresentations$1, reason: invalid class name and case insensitive filesystem */
    final class C09061 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        public C09061(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return KeyguardQuickAffordanceInteractor.this.getSlotPickerRepresentations(this);
        }
    }

    /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$isFeatureDisabledByDevicePolicy$1, reason: invalid class name and case insensitive filesystem */
    final class C09071 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        public C09071(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            KeyguardQuickAffordanceInteractor keyguardQuickAffordanceInteractor = KeyguardQuickAffordanceInteractor.this;
            int i = KeyguardQuickAffordanceInteractor.$r8$clinit;
            return keyguardQuickAffordanceInteractor.isFeatureDisabledByDevicePolicy(this);
        }
    }

    /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$isFeatureDisabledByDevicePolicy$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return KeyguardQuickAffordanceInteractor.this.new AnonymousClass2(continuation);
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
            KeyguardQuickAffordanceInteractor keyguardQuickAffordanceInteractor = KeyguardQuickAffordanceInteractor.this;
            return Boolean.valueOf(DevicePolicyManagerExtKt.areKeyguardShortcutsDisabled$default(keyguardQuickAffordanceInteractor.devicePolicyManager, ((UserTrackerImpl) keyguardQuickAffordanceInteractor.userTracker).getUserId()));
        }
    }

    /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$quickAffordance$1, reason: invalid class name and case insensitive filesystem */
    final class C09091 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        public C09091(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return KeyguardQuickAffordanceInteractor.this.quickAffordance(null, this);
        }
    }

    /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$quickAffordance$4, reason: invalid class name */
    final class AnonymousClass4 extends SuspendLambda implements Function6 {
        /* synthetic */ Object L$0;
        /* synthetic */ boolean Z$0;
        /* synthetic */ boolean Z$1;
        /* synthetic */ boolean Z$2;
        /* synthetic */ boolean Z$3;
        int label;

        public AnonymousClass4(Continuation continuation) {
            super(6, continuation);
        }

        @Override // kotlin.jvm.functions.Function6
        public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
            boolean zBooleanValue = ((Boolean) obj2).booleanValue();
            boolean zBooleanValue2 = ((Boolean) obj3).booleanValue();
            boolean zBooleanValue3 = ((Boolean) obj4).booleanValue();
            boolean zBooleanValue4 = ((Boolean) obj5).booleanValue();
            AnonymousClass4 anonymousClass4 = new AnonymousClass4((Continuation) obj6);
            anonymousClass4.L$0 = (KeyguardQuickAffordanceModel) obj;
            anonymousClass4.Z$0 = zBooleanValue;
            anonymousClass4.Z$1 = zBooleanValue2;
            anonymousClass4.Z$2 = zBooleanValue3;
            anonymousClass4.Z$3 = zBooleanValue4;
            return anonymousClass4.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return (this.Z$0 || !this.Z$1 || !this.Z$2 || this.Z$3) ? KeyguardQuickAffordanceModel.Hidden.INSTANCE : (KeyguardQuickAffordanceModel) this.L$0;
        }
    }

    /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$quickAffordanceAlwaysVisible$1, reason: invalid class name and case insensitive filesystem */
    final class C09101 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;

        public C09101(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return KeyguardQuickAffordanceInteractor.this.quickAffordanceAlwaysVisible(null, null, this);
        }
    }

    /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$select$1, reason: invalid class name and case insensitive filesystem */
    final class C09111 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;

        public C09111(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return KeyguardQuickAffordanceInteractor.this.select(null, null, this);
        }
    }

    /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$unselect$1, reason: invalid class name and case insensitive filesystem */
    final class C09121 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;

        public C09121(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return KeyguardQuickAffordanceInteractor.this.unselect(null, null, this);
        }
    }

    static {
        new Companion(null);
    }

    public KeyguardQuickAffordanceInteractor(KeyguardInteractor keyguardInteractor, ShadeInteractor shadeInteractor, LockPatternUtils lockPatternUtils, KeyguardStateController keyguardStateController, UserTracker userTracker, ActivityStarter activityStarter, FeatureFlags featureFlags, Lazy lazy, DialogTransitionAnimator dialogTransitionAnimator, KeyguardQuickAffordancesLogger keyguardQuickAffordancesLogger, KeyguardQuickAffordancesMetricsLogger keyguardQuickAffordancesMetricsLogger, DevicePolicyManager devicePolicyManager, DockManager dockManager, BiometricSettingsRepository biometricSettingsRepository, AccessibilityManager accessibilityManager, CoroutineDispatcher coroutineDispatcher, Context context, Lazy lazy2, Lazy lazy3) {
        this.keyguardInteractor = keyguardInteractor;
        this.shadeInteractor = shadeInteractor;
        this.keyguardStateController = keyguardStateController;
        this.userTracker = userTracker;
        this.activityStarter = activityStarter;
        this.featureFlags = featureFlags;
        this.repository = lazy;
        this.launchAnimator = dialogTransitionAnimator;
        this.logger = keyguardQuickAffordancesLogger;
        this.metricsLogger = keyguardQuickAffordancesMetricsLogger;
        this.devicePolicyManager = devicePolicyManager;
        this.dockManager = dockManager;
        this.biometricSettingsRepository = biometricSettingsRepository;
        this.accessibilityManager = accessibilityManager;
        this.backgroundDispatcher = coroutineDispatcher;
        this.appContext = context;
        this.keyguardShortcutManager = lazy3;
        this.launchingAffordance = FlowKt.asStateFlow(((KeyguardQuickAffordanceRepository) lazy.get()).launchingAffordance);
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(null);
        this._launchingFromTriggeredResult = stateFlowImplMutableStateFlow;
        this.launchingFromTriggeredResult = FlowKt.asStateFlow(stateFlowImplMutableStateFlow);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object getPickerFlags(ContinuationImpl continuationImpl) throws Throwable {
        AnonymousClass1 anonymousClass1;
        KeyguardPickerFlag[] keyguardPickerFlagArr;
        String str;
        KeyguardPickerFlag[] keyguardPickerFlagArr2;
        KeyguardQuickAffordanceInteractor keyguardQuickAffordanceInteractor;
        int i;
        if (continuationImpl instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuationImpl;
            int i2 = anonymousClass1.label;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i2 - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuationImpl);
            }
        }
        Object obj = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i3 = anonymousClass1.label;
        boolean z = false;
        if (i3 == 0) {
            ResultKt.throwOnFailure(obj);
            keyguardPickerFlagArr = new KeyguardPickerFlag[7];
            anonymousClass1.L$0 = this;
            anonymousClass1.L$1 = keyguardPickerFlagArr;
            anonymousClass1.L$2 = keyguardPickerFlagArr;
            anonymousClass1.L$3 = "is_custom_lock_screen_quick_affordances_feature_enabled";
            anonymousClass1.I$0 = 0;
            anonymousClass1.label = 1;
            Object objIsFeatureDisabledByDevicePolicy = isFeatureDisabledByDevicePolicy(anonymousClass1);
            if (objIsFeatureDisabledByDevicePolicy == coroutineSingletons) {
                return coroutineSingletons;
            }
            str = "is_custom_lock_screen_quick_affordances_feature_enabled";
            obj = objIsFeatureDisabledByDevicePolicy;
            keyguardPickerFlagArr2 = keyguardPickerFlagArr;
            keyguardQuickAffordanceInteractor = this;
            i = 0;
        } else {
            if (i3 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            i = anonymousClass1.I$0;
            str = (String) anonymousClass1.L$3;
            keyguardPickerFlagArr = (KeyguardPickerFlag[]) anonymousClass1.L$2;
            keyguardPickerFlagArr2 = (KeyguardPickerFlag[]) anonymousClass1.L$1;
            keyguardQuickAffordanceInteractor = (KeyguardQuickAffordanceInteractor) anonymousClass1.L$0;
            ResultKt.throwOnFailure(obj);
        }
        if (!((Boolean) obj).booleanValue() && keyguardQuickAffordanceInteractor.appContext.getResources().getBoolean(R.bool.custom_lockscreen_shortcuts_enabled)) {
            z = true;
        }
        keyguardPickerFlagArr[i] = new KeyguardPickerFlag(str, z);
        keyguardPickerFlagArr2[1] = new KeyguardPickerFlag("is_custom_clocks_feature_enabled", ((FeatureFlagsClassicRelease) keyguardQuickAffordanceInteractor.featureFlags).isEnabled(Flags.LOCKSCREEN_CUSTOM_CLOCKS));
        ReleasedFlag releasedFlag = Flags.WALLPAPER_FULLSCREEN_PREVIEW;
        FeatureFlags featureFlags = keyguardQuickAffordanceInteractor.featureFlags;
        keyguardPickerFlagArr2[2] = new KeyguardPickerFlag("wallpaper_fullscreen_preview", ((FeatureFlagsClassicRelease) featureFlags).isEnabled(releasedFlag));
        FeatureFlagsClassicRelease featureFlagsClassicRelease = (FeatureFlagsClassicRelease) featureFlags;
        keyguardPickerFlagArr2[3] = new KeyguardPickerFlag("is_monochromatic_theme_enabled", featureFlagsClassicRelease.isEnabled(Flags.MONOCHROMATIC_THEME));
        keyguardPickerFlagArr2[4] = new KeyguardPickerFlag("wallpaper_picker_ui_for_aiwp", featureFlagsClassicRelease.isEnabled(Flags.WALLPAPER_PICKER_UI_FOR_AIWP));
        keyguardPickerFlagArr2[5] = new KeyguardPickerFlag("wallpaper_picker_page_transitions", featureFlagsClassicRelease.isEnabled(Flags.WALLPAPER_PICKER_PAGE_TRANSITIONS));
        keyguardPickerFlagArr2[6] = new KeyguardPickerFlag("wallpaper_picker_preview_animation", featureFlagsClassicRelease.isEnabled(Flags.WALLPAPER_PICKER_PREVIEW_ANIMATION));
        return keyguardPickerFlagArr2.length > 0 ? Arrays.asList(keyguardPickerFlagArr2) : EmptyList.INSTANCE;
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x009b  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00ab A[LOOP:0: B:31:0x00a5->B:33:0x00ab, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00c5  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00d5  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object getSelections(ContinuationImpl continuationImpl) throws Throwable {
        C09051 c09051;
        List list;
        Map map;
        int iMapCapacity;
        Iterator it;
        if (continuationImpl instanceof C09051) {
            c09051 = (C09051) continuationImpl;
            int i = c09051.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c09051.label = i - Integer.MIN_VALUE;
            } else {
                c09051 = new C09051(continuationImpl);
            }
        }
        Object objIsFeatureDisabledByDevicePolicy = c09051.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c09051.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objIsFeatureDisabledByDevicePolicy);
            c09051.L$0 = this;
            c09051.label = 1;
            objIsFeatureDisabledByDevicePolicy = isFeatureDisabledByDevicePolicy(c09051);
            if (objIsFeatureDisabledByDevicePolicy != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            map = (Map) c09051.L$1;
            list = (List) c09051.L$0;
            ResultKt.throwOnFailure(objIsFeatureDisabledByDevicePolicy);
            Iterable iterable = (Iterable) objIsFeatureDisabledByDevicePolicy;
            iMapCapacity = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(iterable, 10));
            if (iMapCapacity < 16) {
                iMapCapacity = 16;
            }
            LinkedHashMap linkedHashMap = new LinkedHashMap(iMapCapacity);
            for (Object obj : iterable) {
                linkedHashMap.put(((KeyguardQuickAffordancePickerRepresentation) obj).id, obj);
            }
            List list2 = list;
            int iMapCapacity2 = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
            LinkedHashMap linkedHashMap2 = new LinkedHashMap(iMapCapacity2 >= 16 ? iMapCapacity2 : 16);
            it = list2.iterator();
            while (it.hasNext()) {
                String str = ((KeyguardSlotPickerRepresentation) it.next()).id;
                Iterable iterable2 = (List) map.get(str);
                if (iterable2 == null) {
                    iterable2 = EmptyList.INSTANCE;
                }
                ArrayList arrayList = new ArrayList();
                Iterator it2 = iterable2.iterator();
                while (it2.hasNext()) {
                    KeyguardQuickAffordancePickerRepresentation keyguardQuickAffordancePickerRepresentation = (KeyguardQuickAffordancePickerRepresentation) linkedHashMap.get((String) it2.next());
                    if (keyguardQuickAffordancePickerRepresentation != null) {
                        arrayList.add(keyguardQuickAffordancePickerRepresentation);
                    }
                }
                Pair pair = new Pair(str, arrayList);
                linkedHashMap2.put(pair.getFirst(), pair.getSecond());
            }
            return linkedHashMap2;
        }
        this = (KeyguardQuickAffordanceInteractor) c09051.L$0;
        ResultKt.throwOnFailure(objIsFeatureDisabledByDevicePolicy);
        if (((Boolean) objIsFeatureDisabledByDevicePolicy).booleanValue()) {
            return MapsKt__MapsKt.emptyMap();
        }
        List slotPickerRepresentations = ((KeyguardQuickAffordanceRepository) this.repository.get()).getSlotPickerRepresentations();
        Lazy lazy = this.repository;
        Map currentSelections = ((KeyguardQuickAffordanceRepository) lazy.get()).getCurrentSelections();
        c09051.L$0 = slotPickerRepresentations;
        c09051.L$1 = currentSelections;
        c09051.label = 2;
        Object affordancePickerRepresentations = ((KeyguardQuickAffordanceRepository) lazy.get()).getAffordancePickerRepresentations(c09051);
        if (affordancePickerRepresentations != coroutineSingletons) {
            list = slotPickerRepresentations;
            objIsFeatureDisabledByDevicePolicy = affordancePickerRepresentations;
            map = currentSelections;
            Iterable iterable3 = (Iterable) objIsFeatureDisabledByDevicePolicy;
            iMapCapacity = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(iterable3, 10));
            if (iMapCapacity < 16) {
            }
            LinkedHashMap linkedHashMap3 = new LinkedHashMap(iMapCapacity);
            while (r7.hasNext()) {
            }
            List list22 = list;
            int iMapCapacity22 = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(list22, 10));
            LinkedHashMap linkedHashMap22 = new LinkedHashMap(iMapCapacity22 >= 16 ? iMapCapacity22 : 16);
            it = list22.iterator();
            while (it.hasNext()) {
            }
            return linkedHashMap22;
        }
        return coroutineSingletons;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object getSlotPickerRepresentations(ContinuationImpl continuationImpl) throws Throwable {
        C09061 c09061;
        if (continuationImpl instanceof C09061) {
            c09061 = (C09061) continuationImpl;
            int i = c09061.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c09061.label = i - Integer.MIN_VALUE;
            } else {
                c09061 = new C09061(continuationImpl);
            }
        }
        Object objIsFeatureDisabledByDevicePolicy = c09061.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c09061.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objIsFeatureDisabledByDevicePolicy);
            c09061.L$0 = this;
            c09061.label = 1;
            objIsFeatureDisabledByDevicePolicy = isFeatureDisabledByDevicePolicy(c09061);
            if (objIsFeatureDisabledByDevicePolicy == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            this = (KeyguardQuickAffordanceInteractor) c09061.L$0;
            ResultKt.throwOnFailure(objIsFeatureDisabledByDevicePolicy);
        }
        return ((Boolean) objIsFeatureDisabledByDevicePolicy).booleanValue() ? EmptyList.INSTANCE : ((KeyguardQuickAffordanceRepository) this.repository.get()).getSlotPickerRepresentations();
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object isFeatureDisabledByDevicePolicy(ContinuationImpl continuationImpl) throws Throwable {
        C09071 c09071;
        if (continuationImpl instanceof C09071) {
            c09071 = (C09071) continuationImpl;
            int i = c09071.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c09071.label = i - Integer.MIN_VALUE;
            } else {
                c09071 = new C09071(continuationImpl);
            }
        }
        Object obj = c09071.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c09071.label;
        if (i2 != 0) {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return obj;
        }
        ResultKt.throwOnFailure(obj);
        AnonymousClass2 anonymousClass2 = new AnonymousClass2(null);
        c09071.label = 1;
        Object objWithContext = BuildersKt.withContext(this.backgroundDispatcher, anonymousClass2, c09071);
        return objWithContext == coroutineSingletons ? coroutineSingletons : objWithContext;
    }

    public final void launchQuickAffordance(final Intent intent, boolean z) {
        intent.putExtra("fromLockscreen", true);
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.keyguardStateController;
        if ((!(!keyguardStateControllerImpl.mCanDismissLockScreen) || !(keyguardStateControllerImpl.mSecure & (keyguardStateControllerImpl.mTrusted ^ true))) || !z) {
            this.activityStarter.startActivity(intent, false);
        } else {
            intent.putExtra("isSecure", true);
            AsyncTask.execute(new Runnable() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor.launchQuickAffordance.1
                @Override // java.lang.Runnable
                public final void run() {
                    ActivityOptions activityOptionsMakeBasic = ActivityOptions.makeBasic();
                    activityOptionsMakeBasic.setDisallowEnterPictureInPictureWhileLaunching(true);
                    intent.addFlags(872480768);
                    try {
                        IActivityTaskManager service = ActivityTaskManager.getService();
                        String basePackageName = this.appContext.getBasePackageName();
                        String attributionTag = this.appContext.getAttributionTag();
                        Intent intent2 = intent;
                        service.startActivityAsUser((IApplicationThread) null, basePackageName, attributionTag, intent2, intent2.resolveTypeIfNeeded(this.appContext.getContentResolver()), (IBinder) null, (String) null, 0, 268435456, (ProfilerInfo) null, activityOptionsMakeBasic.toBundle(), ((UserTrackerImpl) this.userTracker).getUserHandle().getIdentifier());
                    } catch (RemoteException e) {
                        Log.w("KeyguardQuickAffordanceInteractor", "Unable to start activity", e);
                    }
                }
            });
        }
    }

    public final void onQuickAffordanceTriggered(String str, Expandable expandable, String str2) {
        Object obj;
        ArrayList arrayList = (ArrayList) ((KeyguardShortcutManager) this.keyguardShortcutManager.get()).getQuickAffordanceConfigList();
        int size = arrayList.size();
        int i = 0;
        while (true) {
            if (i >= size) {
                obj = null;
                break;
            }
            obj = arrayList.get(i);
            i++;
            if (Intrinsics.areEqual(((KeyguardQuickAffordanceConfig) obj).getKey(), str)) {
                break;
            }
        }
        KeyguardQuickAffordanceConfig keyguardQuickAffordanceConfig = (KeyguardQuickAffordanceConfig) obj;
        if (keyguardQuickAffordanceConfig == null) {
            Log.e("KeyguardQuickAffordanceInteractor", "Affordance config with key of \"" + str + "\" not found!");
            return;
        }
        ((KeyguardQuickAffordancesMetricsLoggerImpl) this.metricsLogger).getClass();
        StatsEvent.Builder builderNewBuilder = StatsEvent.newBuilder();
        builderNewBuilder.setAtomId(612);
        builderNewBuilder.writeString(str2);
        builderNewBuilder.writeString(str);
        builderNewBuilder.usePooledBuffer();
        StatsLog.write(builderNewBuilder.build());
        KeyguardQuickAffordanceConfig.OnTriggeredResult onTriggeredResultOnTriggered = keyguardQuickAffordanceConfig.onTriggered(expandable);
        boolean z = onTriggeredResultOnTriggered instanceof KeyguardQuickAffordanceConfig.OnTriggeredResult.StartActivity;
        StateFlowImpl stateFlowImpl = this._launchingFromTriggeredResult;
        if (z) {
            stateFlowImpl.setValue(new KeyguardQuickAffordanceConfig.LaunchingFromTriggeredResult(true, str));
            KeyguardQuickAffordanceConfig.OnTriggeredResult.StartActivity startActivity = (KeyguardQuickAffordanceConfig.OnTriggeredResult.StartActivity) onTriggeredResultOnTriggered;
            launchQuickAffordance(startActivity.intent, startActivity.canShowWhileLocked);
            return;
        }
        if (onTriggeredResultOnTriggered instanceof KeyguardQuickAffordanceConfig.OnTriggeredResult.Handled) {
            stateFlowImpl.setValue(new KeyguardQuickAffordanceConfig.LaunchingFromTriggeredResult(((KeyguardQuickAffordanceConfig.OnTriggeredResult.Handled) onTriggeredResultOnTriggered).actionLaunched, str));
            return;
        }
        if (!(onTriggeredResultOnTriggered instanceof KeyguardQuickAffordanceConfig.OnTriggeredResult.ShowDialog)) {
            throw new NoWhenBranchMatchedException();
        }
        stateFlowImpl.setValue(new KeyguardQuickAffordanceConfig.LaunchingFromTriggeredResult(true, str));
        KeyguardQuickAffordanceConfig.OnTriggeredResult.ShowDialog showDialog = (KeyguardQuickAffordanceConfig.OnTriggeredResult.ShowDialog) onTriggeredResultOnTriggered;
        AlertDialog alertDialog = showDialog.dialog;
        Expandable expandable2 = showDialog.expandable;
        if (expandable2 != null) {
            Expandable.Companion companion = Expandable.Companion;
            DialogTransitionAnimator.Controller controllerDialogTransitionController = expandable2.dialogTransitionController(null);
            if (controllerDialogTransitionController != null) {
                SystemUIDialog.applyFlags(alertDialog, true);
                SystemUIDialog.setShowForAllUsers(alertDialog);
                SystemUIDialog.registerDismissListener(alertDialog);
                SystemUIDialog.setDialogSize(alertDialog);
                TransitionAnimator.Timings timings = DialogTransitionAnimator.TIMINGS;
                this.launchAnimator.show(alertDialog, controllerDialogTransitionController, false);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x006e, code lost:
    
        if (r14 == r1) goto L25;
     */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object quickAffordance(KeyguardQuickAffordancePosition keyguardQuickAffordancePosition, ContinuationImpl continuationImpl) throws Throwable {
        C09091 c09091;
        if (continuationImpl instanceof C09091) {
            c09091 = (C09091) continuationImpl;
            int i = c09091.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c09091.label = i - Integer.MIN_VALUE;
            } else {
                c09091 = new C09091(continuationImpl);
            }
        }
        Object objIsFeatureDisabledByDevicePolicy = c09091.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c09091.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objIsFeatureDisabledByDevicePolicy);
            c09091.L$0 = this;
            c09091.L$1 = keyguardQuickAffordancePosition;
            c09091.label = 1;
            objIsFeatureDisabledByDevicePolicy = isFeatureDisabledByDevicePolicy(c09091);
            if (objIsFeatureDisabledByDevicePolicy != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            this = (KeyguardQuickAffordanceInteractor) c09091.L$0;
            ResultKt.throwOnFailure(objIsFeatureDisabledByDevicePolicy);
            Flow flow = (Flow) objIsFeatureDisabledByDevicePolicy;
            KeyguardInteractor keyguardInteractor = this.keyguardInteractor;
            ReadonlyStateFlow readonlyStateFlow = keyguardInteractor.isDozing;
            final StateFlow anyExpansion = ((ShadeInteractorImpl) this.shadeInteractor).baseShadeInteractor.getAnyExpansion();
            return FlowKt.combine(flow, readonlyStateFlow, keyguardInteractor.isKeyguardShowing, FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$quickAffordance$$inlined$map$2

                /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$quickAffordance$$inlined$map$2$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$quickAffordance$$inlined$map$2$2$1, reason: invalid class name */
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
                            Boolean boolValueOf = Boolean.valueOf(((Number) obj).floatValue() < 1.0f);
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
                    Object objCollect = anyExpansion.collect(new AnonymousClass2(flowCollector), continuation);
                    return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                }
            }), ((BiometricSettingsRepositoryImpl) this.biometricSettingsRepository).isCurrentUserInLockdown, new AnonymousClass4(null));
        }
        keyguardQuickAffordancePosition = (KeyguardQuickAffordancePosition) c09091.L$1;
        this = (KeyguardQuickAffordanceInteractor) c09091.L$0;
        ResultKt.throwOnFailure(objIsFeatureDisabledByDevicePolicy);
        if (((Boolean) objIsFeatureDisabledByDevicePolicy).booleanValue()) {
            return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(KeyguardQuickAffordanceModel.Hidden.INSTANCE);
        }
        c09091.L$0 = this;
        c09091.L$1 = null;
        c09091.label = 2;
        objIsFeatureDisabledByDevicePolicy = this.quickAffordanceAlwaysVisible(keyguardQuickAffordancePosition, null, c09091);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object quickAffordanceAlwaysVisible(final KeyguardQuickAffordancePosition keyguardQuickAffordancePosition, String str, ContinuationImpl continuationImpl) throws Throwable {
        C09101 c09101;
        if (continuationImpl instanceof C09101) {
            c09101 = (C09101) continuationImpl;
            int i = c09101.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c09101.label = i - Integer.MIN_VALUE;
            } else {
                c09101 = new C09101(continuationImpl);
            }
        }
        Object objIsFeatureDisabledByDevicePolicy = c09101.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c09101.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objIsFeatureDisabledByDevicePolicy);
            c09101.L$0 = this;
            c09101.L$1 = keyguardQuickAffordancePosition;
            c09101.L$2 = str;
            c09101.label = 1;
            objIsFeatureDisabledByDevicePolicy = isFeatureDisabledByDevicePolicy(c09101);
            if (objIsFeatureDisabledByDevicePolicy == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            keyguardQuickAffordancePosition = (KeyguardQuickAffordancePosition) c09101.L$1;
            this = (KeyguardQuickAffordanceInteractor) c09101.L$0;
            ResultKt.throwOnFailure(objIsFeatureDisabledByDevicePolicy);
        }
        if (((Boolean) objIsFeatureDisabledByDevicePolicy).booleanValue()) {
            return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(KeyguardQuickAffordanceModel.Hidden.INSTANCE);
        }
        final List listSingletonList = Collections.singletonList((KeyguardQuickAffordanceConfig) ((ArrayList) ((KeyguardShortcutManager) this.keyguardShortcutManager.get()).getQuickAffordanceConfigList()).get(keyguardQuickAffordancePosition.ordinal()));
        if (listSingletonList.isEmpty()) {
            return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(KeyguardQuickAffordanceModel.Hidden.INSTANCE);
        }
        List list = listSingletonList;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new KeyguardQuickAffordanceInteractor$combinedConfigs$1$1(null), ((KeyguardQuickAffordanceConfig) it.next()).getLockScreenState()));
        }
        final Flow[] flowArr = (Flow[]) CollectionsKt___CollectionsKt.toList(arrayList).toArray(new Flow[0]);
        return new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$combinedConfigs$$inlined$combine$1

            /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$combinedConfigs$$inlined$combine$1$3, reason: invalid class name */
            public final class AnonymousClass3 extends SuspendLambda implements Function3 {
                final /* synthetic */ List $configs$inlined;
                final /* synthetic */ KeyguardQuickAffordancePosition $position$inlined;
                private /* synthetic */ Object L$0;
                /* synthetic */ Object L$1;
                int label;
                final /* synthetic */ KeyguardQuickAffordanceInteractor this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass3(Continuation continuation, List list, KeyguardQuickAffordanceInteractor keyguardQuickAffordanceInteractor, KeyguardQuickAffordancePosition keyguardQuickAffordancePosition) {
                    super(3, continuation);
                    this.$configs$inlined = list;
                    this.this$0 = keyguardQuickAffordanceInteractor;
                    this.$position$inlined = keyguardQuickAffordancePosition;
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    AnonymousClass3 anonymousClass3 = new AnonymousClass3((Continuation) obj3, this.$configs$inlined, this.this$0, this.$position$inlined);
                    anonymousClass3.L$0 = (FlowCollector) obj;
                    anonymousClass3.L$1 = (Object[]) obj2;
                    return anonymousClass3.invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    Object visible;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        FlowCollector flowCollector = (FlowCollector) this.L$0;
                        KeyguardQuickAffordanceConfig.LockScreenState[] lockScreenStateArr = (KeyguardQuickAffordanceConfig.LockScreenState[]) ((Object[]) this.L$1);
                        int length = lockScreenStateArr.length;
                        int i2 = 0;
                        while (true) {
                            if (i2 >= length) {
                                i2 = -1;
                                break;
                            }
                            if (lockScreenStateArr[i2] instanceof KeyguardQuickAffordanceConfig.LockScreenState.Visible) {
                                break;
                            }
                            i2++;
                        }
                        if (i2 != -1) {
                            KeyguardQuickAffordanceConfig.LockScreenState.Visible visible2 = (KeyguardQuickAffordanceConfig.LockScreenState.Visible) lockScreenStateArr[i2];
                            String key = ((KeyguardQuickAffordanceConfig) this.$configs$inlined.get(i2)).getKey();
                            KeyguardQuickAffordanceInteractor keyguardQuickAffordanceInteractor = this.this$0;
                            String slotId = this.$position$inlined.toSlotId();
                            int i3 = KeyguardQuickAffordanceInteractor.$r8$clinit;
                            keyguardQuickAffordanceInteractor.getClass();
                            visible = new KeyguardQuickAffordanceModel.Visible(slotId + "::" + key, visible2.icon, visible2.activationState);
                        } else {
                            visible = KeyguardQuickAffordanceModel.Hidden.INSTANCE;
                        }
                        this.label = 1;
                        if (flowCollector.emit(visible, this) == coroutineSingletons) {
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
                Object objCombineInternal = CombineKt.combineInternal(flowArr2, new Function0() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$combinedConfigs$$inlined$combine$1.2
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return new KeyguardQuickAffordanceConfig.LockScreenState[flowArr2.length];
                    }
                }, new AnonymousClass3(null, listSingletonList, this, keyguardQuickAffordancePosition), flowCollector, continuation);
                return objCombineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? objCombineInternal : Unit.INSTANCE;
            }
        };
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object select(String str, String str2, ContinuationImpl continuationImpl) throws Throwable {
        C09111 c09111;
        Object obj;
        if (continuationImpl instanceof C09111) {
            c09111 = (C09111) continuationImpl;
            int i = c09111.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c09111.label = i - Integer.MIN_VALUE;
            } else {
                c09111 = new C09111(continuationImpl);
            }
        }
        Object objIsFeatureDisabledByDevicePolicy = c09111.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c09111.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objIsFeatureDisabledByDevicePolicy);
            c09111.L$0 = this;
            c09111.L$1 = str;
            c09111.L$2 = str2;
            c09111.label = 1;
            objIsFeatureDisabledByDevicePolicy = isFeatureDisabledByDevicePolicy(c09111);
            if (objIsFeatureDisabledByDevicePolicy == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            str2 = (String) c09111.L$2;
            str = (String) c09111.L$1;
            this = (KeyguardQuickAffordanceInteractor) c09111.L$0;
            ResultKt.throwOnFailure(objIsFeatureDisabledByDevicePolicy);
        }
        if (((Boolean) objIsFeatureDisabledByDevicePolicy).booleanValue()) {
            return Boolean.FALSE;
        }
        ArrayList arrayList = (ArrayList) ((KeyguardQuickAffordanceRepository) this.repository.get()).getSlotPickerRepresentations();
        int size = arrayList.size();
        int i3 = 0;
        while (true) {
            if (i3 >= size) {
                obj = null;
                break;
            }
            obj = arrayList.get(i3);
            i3++;
            if (Intrinsics.areEqual(((KeyguardSlotPickerRepresentation) obj).id, str)) {
                break;
            }
        }
        KeyguardSlotPickerRepresentation keyguardSlotPickerRepresentation = (KeyguardSlotPickerRepresentation) obj;
        if (keyguardSlotPickerRepresentation == null) {
            return Boolean.FALSE;
        }
        Lazy lazy = this.repository;
        ArrayList arrayList2 = new ArrayList((Collection) ((KeyguardQuickAffordanceRepository) lazy.get()).getCurrentSelections().getOrDefault(str, EmptyList.INSTANCE));
        if (!arrayList2.remove(str2)) {
            while (arrayList2.size() > 0 && arrayList2.size() >= keyguardSlotPickerRepresentation.maxSelectedAffordances) {
                arrayList2.remove(0);
            }
        }
        arrayList2.add(str2);
        ((KeyguardQuickAffordanceSelectionManager) ((KeyguardQuickAffordanceRepository) lazy.get()).selectionManager.$$delegate_0.getValue()).setSelections(str, arrayList2);
        KeyguardQuickAffordancesLogger keyguardQuickAffordancesLogger = this.logger;
        keyguardQuickAffordancesLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        KeyguardQuickAffordancesLogger$$ExternalSyntheticLambda0 keyguardQuickAffordancesLogger$$ExternalSyntheticLambda0 = new KeyguardQuickAffordancesLogger$$ExternalSyntheticLambda0(0);
        LogBuffer logBuffer = keyguardQuickAffordancesLogger.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("KeyguardQuickAffordancesLogger", logLevel, keyguardQuickAffordancesLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.str1 = str2;
        logMessageImpl.str2 = str;
        logBuffer.commit(logMessageObtain);
        ((KeyguardQuickAffordancesMetricsLoggerImpl) this.metricsLogger).getClass();
        StatsEvent.Builder builderNewBuilder = StatsEvent.newBuilder();
        builderNewBuilder.setAtomId(611);
        builderNewBuilder.writeString(str);
        builderNewBuilder.writeString(str2);
        builderNewBuilder.usePooledBuffer();
        StatsLog.write(builderNewBuilder.build());
        return Boolean.TRUE;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object unselect(String str, String str2, ContinuationImpl continuationImpl) throws Throwable {
        C09121 c09121;
        Object obj;
        if (continuationImpl instanceof C09121) {
            c09121 = (C09121) continuationImpl;
            int i = c09121.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c09121.label = i - Integer.MIN_VALUE;
            } else {
                c09121 = new C09121(continuationImpl);
            }
        }
        Object objIsFeatureDisabledByDevicePolicy = c09121.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c09121.label;
        boolean z = true;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objIsFeatureDisabledByDevicePolicy);
            c09121.L$0 = this;
            c09121.L$1 = str;
            c09121.L$2 = str2;
            c09121.label = 1;
            objIsFeatureDisabledByDevicePolicy = isFeatureDisabledByDevicePolicy(c09121);
            if (objIsFeatureDisabledByDevicePolicy == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            str2 = (String) c09121.L$2;
            str = (String) c09121.L$1;
            this = (KeyguardQuickAffordanceInteractor) c09121.L$0;
            ResultKt.throwOnFailure(objIsFeatureDisabledByDevicePolicy);
        }
        if (((Boolean) objIsFeatureDisabledByDevicePolicy).booleanValue()) {
            return Boolean.FALSE;
        }
        ArrayList arrayList = (ArrayList) ((KeyguardQuickAffordanceRepository) this.repository.get()).getSlotPickerRepresentations();
        int size = arrayList.size();
        int i3 = 0;
        while (true) {
            if (i3 >= size) {
                obj = null;
                break;
            }
            obj = arrayList.get(i3);
            i3++;
            if (Intrinsics.areEqual(((KeyguardSlotPickerRepresentation) obj).id, str)) {
                break;
            }
        }
        if (obj == null) {
            return Boolean.FALSE;
        }
        Lazy lazy = this.repository;
        if (str2 != null && str2.length() != 0) {
            ArrayList arrayList2 = new ArrayList((Collection) ((KeyguardQuickAffordanceRepository) lazy.get()).getCurrentSelections().getOrDefault(str, EmptyList.INSTANCE));
            if (arrayList2.remove(str2)) {
                ((KeyguardQuickAffordanceSelectionManager) ((KeyguardQuickAffordanceRepository) lazy.get()).selectionManager.$$delegate_0.getValue()).setSelections(str, arrayList2);
            } else {
                z = false;
            }
            return Boolean.valueOf(z);
        }
        Map currentSelections = ((KeyguardQuickAffordanceRepository) lazy.get()).getCurrentSelections();
        EmptyList emptyList = EmptyList.INSTANCE;
        if (((List) currentSelections.getOrDefault(str, emptyList)).isEmpty()) {
            z = false;
        } else {
            ((KeyguardQuickAffordanceSelectionManager) ((KeyguardQuickAffordanceRepository) lazy.get()).selectionManager.$$delegate_0.getValue()).setSelections(str, emptyList);
        }
        return Boolean.valueOf(z);
    }
}
