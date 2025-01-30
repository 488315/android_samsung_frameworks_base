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
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.internal.widget.LockPatternUtils;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.animation.Expandable;
import com.android.systemui.animation.Expandable$Companion$fromView$1;
import com.android.systemui.animation.LaunchAnimator;
import com.android.systemui.animation.ViewDialogLaunchAnimatorController;
import com.android.systemui.dock.DockManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.flags.ReleasedFlag;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceSelectionManager;
import com.android.systemui.keyguard.data.repository.BiometricSettingsRepository;
import com.android.systemui.keyguard.data.repository.BiometricSettingsRepositoryImpl;
import com.android.systemui.keyguard.data.repository.KeyguardQuickAffordanceRepository;
import com.android.systemui.keyguard.domain.model.KeyguardQuickAffordanceModel;
import com.android.systemui.keyguard.domain.quickaffordance.KeyguardQuickAffordanceRegistry;
import com.android.systemui.keyguard.domain.quickaffordance.KeyguardQuickAffordanceRegistryImpl;
import com.android.systemui.keyguard.shared.model.KeyguardPickerFlag;
import com.android.systemui.keyguard.shared.model.KeyguardQuickAffordancePickerRepresentation;
import com.android.systemui.keyguard.shared.model.KeyguardSlotPickerRepresentation;
import com.android.systemui.keyguard.shared.quickaffordance.KeyguardQuickAffordancePosition;
import com.android.systemui.keyguard.shared.quickaffordance.KeyguardQuickAffordancesMetricsLogger;
import com.android.systemui.keyguard.shared.quickaffordance.KeyguardQuickAffordancesMetricsLoggerImpl;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.KeyguardShortcutManager;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.nexus.video.VideoPlayer;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.internal.CombineKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardQuickAffordanceInteractor {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ActivityStarter activityStarter;
    public final Context appContext;
    public final CoroutineDispatcher backgroundDispatcher;
    public final BiometricSettingsRepository biometricSettingsRepository;
    public final DevicePolicyManager devicePolicyManager;
    public final DockManager dockManager;
    public final FeatureFlags featureFlags;
    public final boolean isUsingProperty = true;
    public final KeyguardInteractor keyguardInteractor;
    public final KeyguardShortcutManager keyguardShortcutManager;
    public final KeyguardStateController keyguardStateController;
    public final DialogLaunchAnimator launchAnimator;
    public final KeyguardQuickAffordancesMetricsLogger logger;
    public final KeyguardQuickAffordanceRegistry registry;
    public final Lazy repository;
    public final UserTracker userTracker;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    public KeyguardQuickAffordanceInteractor(KeyguardInteractor keyguardInteractor, KeyguardQuickAffordanceRegistry keyguardQuickAffordanceRegistry, LockPatternUtils lockPatternUtils, KeyguardStateController keyguardStateController, UserTracker userTracker, ActivityStarter activityStarter, FeatureFlags featureFlags, Lazy lazy, DialogLaunchAnimator dialogLaunchAnimator, KeyguardQuickAffordancesMetricsLogger keyguardQuickAffordancesMetricsLogger, DevicePolicyManager devicePolicyManager, DockManager dockManager, BiometricSettingsRepository biometricSettingsRepository, CoroutineDispatcher coroutineDispatcher, Context context, KeyguardShortcutManager keyguardShortcutManager) {
        this.keyguardInteractor = keyguardInteractor;
        this.registry = keyguardQuickAffordanceRegistry;
        this.keyguardStateController = keyguardStateController;
        this.userTracker = userTracker;
        this.activityStarter = activityStarter;
        this.featureFlags = featureFlags;
        this.repository = lazy;
        this.launchAnimator = dialogLaunchAnimator;
        this.logger = keyguardQuickAffordancesMetricsLogger;
        this.devicePolicyManager = devicePolicyManager;
        this.dockManager = dockManager;
        this.biometricSettingsRepository = biometricSettingsRepository;
        this.backgroundDispatcher = coroutineDispatcher;
        this.appContext = context;
        this.keyguardShortcutManager = keyguardShortcutManager;
    }

    public final Flow combinedConfigs(final KeyguardQuickAffordancePosition keyguardQuickAffordancePosition, final List list) {
        if (list.isEmpty()) {
            return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(KeyguardQuickAffordanceModel.Hidden.INSTANCE);
        }
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new KeyguardQuickAffordanceInteractor$combinedConfigs$1$1(null), ((KeyguardQuickAffordanceConfig) it.next()).getLockScreenState()));
        }
        final Flow[] flowArr = (Flow[]) CollectionsKt___CollectionsKt.toList(arrayList).toArray(new Flow[0]);
        return new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$combinedConfigs$$inlined$combine$1

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            @DebugMetadata(m276c = "com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$combinedConfigs$$inlined$combine$1$3", m277f = "KeyguardQuickAffordanceInteractor.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_startTcpDump}, m279m = "invokeSuspend")
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
                    Object obj2;
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
                            KeyguardQuickAffordanceConfig.LockScreenState.Visible visible = (KeyguardQuickAffordanceConfig.LockScreenState.Visible) lockScreenStateArr[i2];
                            String key = ((KeyguardQuickAffordanceConfig) this.$configs$inlined.get(i2)).getKey();
                            KeyguardQuickAffordanceInteractor keyguardQuickAffordanceInteractor = this.this$0;
                            int i3 = KeyguardQuickAffordanceInteractor.$r8$clinit;
                            keyguardQuickAffordanceInteractor.getClass();
                            obj2 = new KeyguardQuickAffordanceModel.Visible(key, visible.icon, visible.activationState);
                        } else {
                            obj2 = KeyguardQuickAffordanceModel.Hidden.INSTANCE;
                        }
                        this.label = 1;
                        if (flowCollector.emit(obj2, this) == coroutineSingletons) {
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
                Object combineInternal = CombineKt.combineInternal(flowArr2, new Function0() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$combinedConfigs$$inlined$combine$1.2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return new KeyguardQuickAffordanceConfig.LockScreenState[flowArr2.length];
                    }
                }, new AnonymousClass3(null, list, this, keyguardQuickAffordancePosition), flowCollector, continuation);
                return combineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? combineInternal : Unit.INSTANCE;
            }
        };
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x00a1, code lost:
    
        if (r8.appContext.getResources().getBoolean(com.android.systemui.R.bool.custom_lockscreen_shortcuts_enabled) != false) goto L26;
     */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0088  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0046  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object getPickerFlags(Continuation continuation) {
        KeyguardQuickAffordanceInteractor$getPickerFlags$1 keyguardQuickAffordanceInteractor$getPickerFlags$1;
        int i;
        KeyguardPickerFlag[] keyguardPickerFlagArr;
        Object withContext;
        String str;
        KeyguardPickerFlag[] keyguardPickerFlagArr2;
        int i2;
        if (continuation instanceof KeyguardQuickAffordanceInteractor$getPickerFlags$1) {
            keyguardQuickAffordanceInteractor$getPickerFlags$1 = (KeyguardQuickAffordanceInteractor$getPickerFlags$1) continuation;
            int i3 = keyguardQuickAffordanceInteractor$getPickerFlags$1.label;
            if ((i3 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                keyguardQuickAffordanceInteractor$getPickerFlags$1.label = i3 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                Object obj = keyguardQuickAffordanceInteractor$getPickerFlags$1.result;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                i = keyguardQuickAffordanceInteractor$getPickerFlags$1.label;
                boolean z = true;
                if (i != 0) {
                    ResultKt.throwOnFailure(obj);
                    keyguardPickerFlagArr = new KeyguardPickerFlag[6];
                    keyguardPickerFlagArr[0] = new KeyguardPickerFlag("revamped_wallpaper_ui", ((FeatureFlagsRelease) this.featureFlags).isEnabled(Flags.REVAMPED_WALLPAPER_UI));
                    keyguardQuickAffordanceInteractor$getPickerFlags$1.L$0 = this;
                    keyguardQuickAffordanceInteractor$getPickerFlags$1.L$1 = keyguardPickerFlagArr;
                    keyguardQuickAffordanceInteractor$getPickerFlags$1.L$2 = keyguardPickerFlagArr;
                    keyguardQuickAffordanceInteractor$getPickerFlags$1.L$3 = "is_custom_lock_screen_quick_affordances_feature_enabled";
                    keyguardQuickAffordanceInteractor$getPickerFlags$1.I$0 = 1;
                    keyguardQuickAffordanceInteractor$getPickerFlags$1.label = 1;
                    withContext = BuildersKt.withContext(this.backgroundDispatcher, new C1703xb9d0fc0(this, null), keyguardQuickAffordanceInteractor$getPickerFlags$1);
                    if (withContext == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    str = "is_custom_lock_screen_quick_affordances_feature_enabled";
                    keyguardPickerFlagArr2 = keyguardPickerFlagArr;
                    i2 = 1;
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    int i4 = keyguardQuickAffordanceInteractor$getPickerFlags$1.I$0;
                    str = (String) keyguardQuickAffordanceInteractor$getPickerFlags$1.L$3;
                    keyguardPickerFlagArr = (KeyguardPickerFlag[]) keyguardQuickAffordanceInteractor$getPickerFlags$1.L$2;
                    keyguardPickerFlagArr2 = (KeyguardPickerFlag[]) keyguardQuickAffordanceInteractor$getPickerFlags$1.L$1;
                    KeyguardQuickAffordanceInteractor keyguardQuickAffordanceInteractor = (KeyguardQuickAffordanceInteractor) keyguardQuickAffordanceInteractor$getPickerFlags$1.L$0;
                    ResultKt.throwOnFailure(obj);
                    i2 = i4;
                    this = keyguardQuickAffordanceInteractor;
                    withContext = obj;
                }
                if (!((Boolean) withContext).booleanValue()) {
                    if (((FeatureFlagsRelease) this.featureFlags).isEnabled(Flags.CUSTOMIZABLE_LOCK_SCREEN_QUICK_AFFORDANCES)) {
                    }
                }
                z = false;
                keyguardPickerFlagArr[i2] = new KeyguardPickerFlag(str, z);
                keyguardPickerFlagArr2[2] = new KeyguardPickerFlag("is_custom_clocks_feature_enabled", ((FeatureFlagsRelease) this.featureFlags).isEnabled(Flags.LOCKSCREEN_CUSTOM_CLOCKS));
                ReleasedFlag releasedFlag = Flags.WALLPAPER_FULLSCREEN_PREVIEW;
                FeatureFlags featureFlags = this.featureFlags;
                FeatureFlagsRelease featureFlagsRelease = (FeatureFlagsRelease) featureFlags;
                keyguardPickerFlagArr2[3] = new KeyguardPickerFlag("wallpaper_fullscreen_preview", featureFlagsRelease.isEnabled(releasedFlag));
                keyguardPickerFlagArr2[4] = new KeyguardPickerFlag("is_monochromatic_theme_enabled", featureFlagsRelease.isEnabled(Flags.MONOCHROMATIC_THEME));
                featureFlags.getClass();
                keyguardPickerFlagArr2[5] = new KeyguardPickerFlag("wallpaper_picker_ui_for_aiwp", false);
                return CollectionsKt__CollectionsKt.listOf(keyguardPickerFlagArr2);
            }
        }
        keyguardQuickAffordanceInteractor$getPickerFlags$1 = new KeyguardQuickAffordanceInteractor$getPickerFlags$1(this, continuation);
        Object obj2 = keyguardQuickAffordanceInteractor$getPickerFlags$1.result;
        CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        i = keyguardQuickAffordanceInteractor$getPickerFlags$1.label;
        boolean z2 = true;
        if (i != 0) {
        }
        if (!((Boolean) withContext).booleanValue()) {
        }
        z2 = false;
        keyguardPickerFlagArr[i2] = new KeyguardPickerFlag(str, z2);
        keyguardPickerFlagArr2[2] = new KeyguardPickerFlag("is_custom_clocks_feature_enabled", ((FeatureFlagsRelease) this.featureFlags).isEnabled(Flags.LOCKSCREEN_CUSTOM_CLOCKS));
        ReleasedFlag releasedFlag2 = Flags.WALLPAPER_FULLSCREEN_PREVIEW;
        FeatureFlags featureFlags2 = this.featureFlags;
        FeatureFlagsRelease featureFlagsRelease2 = (FeatureFlagsRelease) featureFlags2;
        keyguardPickerFlagArr2[3] = new KeyguardPickerFlag("wallpaper_fullscreen_preview", featureFlagsRelease2.isEnabled(releasedFlag2));
        keyguardPickerFlagArr2[4] = new KeyguardPickerFlag("is_monochromatic_theme_enabled", featureFlagsRelease2.isEnabled(Flags.MONOCHROMATIC_THEME));
        featureFlags2.getClass();
        keyguardPickerFlagArr2[5] = new KeyguardPickerFlag("wallpaper_picker_ui_for_aiwp", false);
        return CollectionsKt__CollectionsKt.listOf(keyguardPickerFlagArr2);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x00a3  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x00b3 A[LOOP:0: B:15:0x00ad->B:17:0x00b3, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:21:0x00cb  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00db  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0060  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0065  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0042  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object getSelections(Continuation continuation) {
        KeyguardQuickAffordanceInteractor$getSelections$1 keyguardQuickAffordanceInteractor$getSelections$1;
        Object obj;
        int i;
        List list;
        Map map;
        int mapCapacity;
        Iterator it;
        if (continuation instanceof KeyguardQuickAffordanceInteractor$getSelections$1) {
            keyguardQuickAffordanceInteractor$getSelections$1 = (KeyguardQuickAffordanceInteractor$getSelections$1) continuation;
            int i2 = keyguardQuickAffordanceInteractor$getSelections$1.label;
            if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                keyguardQuickAffordanceInteractor$getSelections$1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                obj = keyguardQuickAffordanceInteractor$getSelections$1.result;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                i = keyguardQuickAffordanceInteractor$getSelections$1.label;
                if (i != 0) {
                    ResultKt.throwOnFailure(obj);
                    keyguardQuickAffordanceInteractor$getSelections$1.L$0 = this;
                    keyguardQuickAffordanceInteractor$getSelections$1.label = 1;
                    obj = BuildersKt.withContext(this.backgroundDispatcher, new C1703xb9d0fc0(this, null), keyguardQuickAffordanceInteractor$getSelections$1);
                    if (obj == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i != 1) {
                        if (i != 2) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        map = (Map) keyguardQuickAffordanceInteractor$getSelections$1.L$1;
                        list = (List) keyguardQuickAffordanceInteractor$getSelections$1.L$0;
                        ResultKt.throwOnFailure(obj);
                        Iterable iterable = (Iterable) obj;
                        mapCapacity = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(iterable, 10));
                        if (mapCapacity < 16) {
                            mapCapacity = 16;
                        }
                        LinkedHashMap linkedHashMap = new LinkedHashMap(mapCapacity);
                        for (Object obj2 : iterable) {
                            linkedHashMap.put(((KeyguardQuickAffordancePickerRepresentation) obj2).f301id, obj2);
                        }
                        int mapCapacity2 = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
                        LinkedHashMap linkedHashMap2 = new LinkedHashMap(mapCapacity2 >= 16 ? mapCapacity2 : 16);
                        it = list.iterator();
                        while (it.hasNext()) {
                            String str = ((KeyguardSlotPickerRepresentation) it.next()).f302id;
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
                    this = (KeyguardQuickAffordanceInteractor) keyguardQuickAffordanceInteractor$getSelections$1.L$0;
                    ResultKt.throwOnFailure(obj);
                }
                if (!((Boolean) obj).booleanValue()) {
                    return MapsKt__MapsKt.emptyMap();
                }
                List slotPickerRepresentations = ((KeyguardQuickAffordanceRepository) this.repository.get()).getSlotPickerRepresentations();
                Lazy lazy = this.repository;
                Map currentSelections = ((KeyguardQuickAffordanceRepository) lazy.get()).getCurrentSelections();
                keyguardQuickAffordanceInteractor$getSelections$1.L$0 = slotPickerRepresentations;
                keyguardQuickAffordanceInteractor$getSelections$1.L$1 = currentSelections;
                keyguardQuickAffordanceInteractor$getSelections$1.label = 2;
                Object affordancePickerRepresentations = ((KeyguardQuickAffordanceRepository) lazy.get()).getAffordancePickerRepresentations(keyguardQuickAffordanceInteractor$getSelections$1);
                if (affordancePickerRepresentations == coroutineSingletons) {
                    return coroutineSingletons;
                }
                list = slotPickerRepresentations;
                obj = affordancePickerRepresentations;
                map = currentSelections;
                Iterable iterable3 = (Iterable) obj;
                mapCapacity = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(iterable3, 10));
                if (mapCapacity < 16) {
                }
                LinkedHashMap linkedHashMap3 = new LinkedHashMap(mapCapacity);
                while (r7.hasNext()) {
                }
                int mapCapacity22 = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
                LinkedHashMap linkedHashMap22 = new LinkedHashMap(mapCapacity22 >= 16 ? mapCapacity22 : 16);
                it = list.iterator();
                while (it.hasNext()) {
                }
                return linkedHashMap22;
            }
        }
        keyguardQuickAffordanceInteractor$getSelections$1 = new KeyguardQuickAffordanceInteractor$getSelections$1(this, continuation);
        obj = keyguardQuickAffordanceInteractor$getSelections$1.result;
        CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        i = keyguardQuickAffordanceInteractor$getSelections$1.label;
        if (i != 0) {
        }
        if (!((Boolean) obj).booleanValue()) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x004a  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0020  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object getSlotPickerRepresentations(Continuation continuation) {
        KeyguardQuickAffordanceInteractor$getSlotPickerRepresentations$1 keyguardQuickAffordanceInteractor$getSlotPickerRepresentations$1;
        int i;
        if (continuation instanceof KeyguardQuickAffordanceInteractor$getSlotPickerRepresentations$1) {
            keyguardQuickAffordanceInteractor$getSlotPickerRepresentations$1 = (KeyguardQuickAffordanceInteractor$getSlotPickerRepresentations$1) continuation;
            int i2 = keyguardQuickAffordanceInteractor$getSlotPickerRepresentations$1.label;
            if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                keyguardQuickAffordanceInteractor$getSlotPickerRepresentations$1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                Object obj = keyguardQuickAffordanceInteractor$getSlotPickerRepresentations$1.result;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                i = keyguardQuickAffordanceInteractor$getSlotPickerRepresentations$1.label;
                if (i != 0) {
                    ResultKt.throwOnFailure(obj);
                    throw new IllegalStateException("Check failed.".toString());
                }
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                KeyguardQuickAffordanceInteractor keyguardQuickAffordanceInteractor = (KeyguardQuickAffordanceInteractor) keyguardQuickAffordanceInteractor$getSlotPickerRepresentations$1.L$0;
                ResultKt.throwOnFailure(obj);
                return ((Boolean) obj).booleanValue() ? EmptyList.INSTANCE : ((KeyguardQuickAffordanceRepository) keyguardQuickAffordanceInteractor.repository.get()).getSlotPickerRepresentations();
            }
        }
        keyguardQuickAffordanceInteractor$getSlotPickerRepresentations$1 = new KeyguardQuickAffordanceInteractor$getSlotPickerRepresentations$1(this, continuation);
        Object obj2 = keyguardQuickAffordanceInteractor$getSlotPickerRepresentations$1.result;
        CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        i = keyguardQuickAffordanceInteractor$getSlotPickerRepresentations$1.label;
        if (i != 0) {
        }
    }

    public final void launchQuickAffordance(final Intent intent, boolean z) {
        intent.putExtra("fromLockscreen", true);
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.keyguardStateController;
        if ((!(!keyguardStateControllerImpl.mCanDismissLockScreen) || !(keyguardStateControllerImpl.mSecure & (keyguardStateControllerImpl.mTrusted ^ true))) || !z) {
            this.activityStarter.startActivity(intent, false);
        } else {
            intent.putExtra("isSecure", true);
            AsyncTask.execute(new Runnable() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$launchQuickAffordance$1
                @Override // java.lang.Runnable
                public final void run() {
                    ActivityOptions makeBasic = ActivityOptions.makeBasic();
                    makeBasic.setDisallowEnterPictureInPictureWhileLaunching(true);
                    makeBasic.setRotationAnimationHint(3);
                    intent.addFlags(872480768);
                    try {
                        IActivityTaskManager service = ActivityTaskManager.getService();
                        String basePackageName = this.appContext.getBasePackageName();
                        String attributionTag = this.appContext.getAttributionTag();
                        Intent intent2 = intent;
                        service.startActivityAsUser((IApplicationThread) null, basePackageName, attributionTag, intent2, intent2.resolveTypeIfNeeded(this.appContext.getContentResolver()), (IBinder) null, (String) null, 0, QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE, (ProfilerInfo) null, makeBasic.toBundle(), ((UserTrackerImpl) this.userTracker).getUserHandle().getIdentifier());
                    } catch (RemoteException e) {
                        Log.w("KeyguardQuickAffordanceInteractor", "Unable to start activity", e);
                    }
                }
            });
        }
    }

    public final void onQuickAffordanceTriggered(String str, Expandable expandable, String str2) {
        KeyguardQuickAffordanceConfig keyguardQuickAffordanceConfig;
        Object obj;
        if (this.isUsingProperty) {
            Iterator it = ((ArrayList) this.keyguardShortcutManager.getQuickAffordanceConfigList()).iterator();
            while (true) {
                if (!it.hasNext()) {
                    obj = null;
                    break;
                } else {
                    obj = it.next();
                    if (Intrinsics.areEqual(((KeyguardQuickAffordanceConfig) obj).getKey(), str)) {
                        break;
                    }
                }
            }
            keyguardQuickAffordanceConfig = (KeyguardQuickAffordanceConfig) obj;
        } else {
            keyguardQuickAffordanceConfig = (KeyguardQuickAffordanceConfig) MapsKt__MapsKt.getValue(((KeyguardQuickAffordanceRegistryImpl) this.registry).configByKey, str);
        }
        if (keyguardQuickAffordanceConfig == null) {
            Log.e("KeyguardQuickAffordanceInteractor", "Affordance config with key of \"" + str + "\" not found!");
            return;
        }
        ((KeyguardQuickAffordancesMetricsLoggerImpl) this.logger).getClass();
        StatsEvent.Builder newBuilder = StatsEvent.newBuilder();
        newBuilder.setAtomId(612);
        newBuilder.writeString(str2);
        newBuilder.writeString(str);
        newBuilder.usePooledBuffer();
        StatsLog.write(newBuilder.build());
        KeyguardQuickAffordanceConfig.OnTriggeredResult onTriggered = keyguardQuickAffordanceConfig.onTriggered(expandable);
        if (onTriggered instanceof KeyguardQuickAffordanceConfig.OnTriggeredResult.StartActivity) {
            KeyguardQuickAffordanceConfig.OnTriggeredResult.StartActivity startActivity = (KeyguardQuickAffordanceConfig.OnTriggeredResult.StartActivity) onTriggered;
            launchQuickAffordance(startActivity.intent, startActivity.canShowWhileLocked);
            return;
        }
        if ((onTriggered instanceof KeyguardQuickAffordanceConfig.OnTriggeredResult.Handled) || !(onTriggered instanceof KeyguardQuickAffordanceConfig.OnTriggeredResult.ShowDialog)) {
            return;
        }
        KeyguardQuickAffordanceConfig.OnTriggeredResult.ShowDialog showDialog = (KeyguardQuickAffordanceConfig.OnTriggeredResult.ShowDialog) onTriggered;
        AlertDialog alertDialog = showDialog.dialog;
        Expandable expandable2 = showDialog.expandable;
        if (expandable2 != null) {
            Expandable.Companion companion = Expandable.Companion;
            DialogLaunchAnimator.Controller.Companion.getClass();
            ViewDialogLaunchAnimatorController fromView = DialogLaunchAnimator.Controller.Companion.fromView(((Expandable$Companion$fromView$1) expandable2).$view, null);
            if (fromView != null) {
                SystemUIDialog.applyFlags(alertDialog);
                SystemUIDialog.setShowForAllUsers(alertDialog);
                SystemUIDialog.registerDismissListener(alertDialog, null);
                SystemUIDialog.setDialogSize(alertDialog);
                LaunchAnimator.Timings timings = DialogLaunchAnimator.TIMINGS;
                this.launchAnimator.show(alertDialog, fromView, false);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0058  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0060  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0039  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object quickAffordance(KeyguardQuickAffordancePosition keyguardQuickAffordancePosition, Continuation continuation) {
        KeyguardQuickAffordanceInteractor$quickAffordance$1 keyguardQuickAffordanceInteractor$quickAffordance$1;
        Object obj;
        int i;
        if (continuation instanceof KeyguardQuickAffordanceInteractor$quickAffordance$1) {
            keyguardQuickAffordanceInteractor$quickAffordance$1 = (KeyguardQuickAffordanceInteractor$quickAffordance$1) continuation;
            int i2 = keyguardQuickAffordanceInteractor$quickAffordance$1.label;
            if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                keyguardQuickAffordanceInteractor$quickAffordance$1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                obj = keyguardQuickAffordanceInteractor$quickAffordance$1.result;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                i = keyguardQuickAffordanceInteractor$quickAffordance$1.label;
                if (i != 0) {
                    ResultKt.throwOnFailure(obj);
                    keyguardQuickAffordanceInteractor$quickAffordance$1.L$0 = this;
                    keyguardQuickAffordanceInteractor$quickAffordance$1.L$1 = keyguardQuickAffordancePosition;
                    keyguardQuickAffordanceInteractor$quickAffordance$1.label = 1;
                    obj = BuildersKt.withContext(this.backgroundDispatcher, new C1703xb9d0fc0(this, null), keyguardQuickAffordanceInteractor$quickAffordance$1);
                    if (obj == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    keyguardQuickAffordancePosition = (KeyguardQuickAffordancePosition) keyguardQuickAffordanceInteractor$quickAffordance$1.L$1;
                    this = (KeyguardQuickAffordanceInteractor) keyguardQuickAffordanceInteractor$quickAffordance$1.L$0;
                    ResultKt.throwOnFailure(obj);
                }
                if (!((Boolean) obj).booleanValue()) {
                    return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(KeyguardQuickAffordanceModel.Hidden.INSTANCE);
                }
                Flow quickAffordanceAlwaysVisible = this.quickAffordanceAlwaysVisible(keyguardQuickAffordancePosition);
                KeyguardInteractor keyguardInteractor = this.keyguardInteractor;
                return FlowKt.combine(quickAffordanceAlwaysVisible, keyguardInteractor.isDozing, keyguardInteractor.isKeyguardShowing, keyguardInteractor.isQuickSettingsVisible, ((BiometricSettingsRepositoryImpl) this.biometricSettingsRepository).isCurrentUserInLockdown, new KeyguardQuickAffordanceInteractor$quickAffordance$2(null));
            }
        }
        keyguardQuickAffordanceInteractor$quickAffordance$1 = new KeyguardQuickAffordanceInteractor$quickAffordance$1(this, continuation);
        obj = keyguardQuickAffordanceInteractor$quickAffordance$1.result;
        CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        i = keyguardQuickAffordanceInteractor$quickAffordance$1.label;
        if (i != 0) {
        }
        if (!((Boolean) obj).booleanValue()) {
        }
    }

    public final Flow quickAffordanceAlwaysVisible(KeyguardQuickAffordancePosition keyguardQuickAffordancePosition) {
        if (!this.isUsingProperty) {
            return combinedConfigs(keyguardQuickAffordancePosition, (List) MapsKt__MapsKt.getValue(((KeyguardQuickAffordanceRegistryImpl) this.registry).configsByPosition, keyguardQuickAffordancePosition));
        }
        final List singletonList = Collections.singletonList((KeyguardQuickAffordanceConfig) ((ArrayList) this.keyguardShortcutManager.getQuickAffordanceConfigList()).get(keyguardQuickAffordancePosition.ordinal()));
        Log.d("KeyguardQuickAffordanceInteractor", "combinedConfigs: " + singletonList.size());
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(singletonList, 10));
        Iterator it = singletonList.iterator();
        while (it.hasNext()) {
            arrayList.add(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new KeyguardQuickAffordanceInteractor$combinedConfigs$3$1$1(null), ((KeyguardQuickAffordanceConfig) it.next()).getLockScreenState()));
        }
        final Flow[] flowArr = (Flow[]) CollectionsKt___CollectionsKt.toList(arrayList).toArray(new Flow[0]);
        return new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$combinedConfigs$lambda$15$$inlined$combine$1

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            @DebugMetadata(m276c = "com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$combinedConfigs$lambda$15$$inlined$combine$1$3", m277f = "KeyguardQuickAffordanceInteractor.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_startTcpDump}, m279m = "invokeSuspend")
            /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$combinedConfigs$lambda$15$$inlined$combine$1$3, reason: invalid class name */
            public final class AnonymousClass3 extends SuspendLambda implements Function3 {
                final /* synthetic */ List $this_with$inlined;
                private /* synthetic */ Object L$0;
                /* synthetic */ Object L$1;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass3(Continuation continuation, List list) {
                    super(3, continuation);
                    this.$this_with$inlined = list;
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    AnonymousClass3 anonymousClass3 = new AnonymousClass3((Continuation) obj3, this.$this_with$inlined);
                    anonymousClass3.L$0 = (FlowCollector) obj;
                    anonymousClass3.L$1 = (Object[]) obj2;
                    return anonymousClass3.invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    Object obj2;
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
                            KeyguardQuickAffordanceConfig.LockScreenState.Visible visible = (KeyguardQuickAffordanceConfig.LockScreenState.Visible) lockScreenStateArr[i2];
                            String key = ((KeyguardQuickAffordanceConfig) this.$this_with$inlined.get(i2)).getKey();
                            Log.d("KeyguardQuickAffordanceInteractor", "combinedConfigs: Visible State was provided " + key);
                            obj2 = new KeyguardQuickAffordanceModel.Visible(key, visible.icon, visible.activationState);
                        } else {
                            ListPopupWindow$$ExternalSyntheticOutline0.m10m("combinedConfigs: Hidden State was provided ", this.$this_with$inlined.size(), "KeyguardQuickAffordanceInteractor");
                            obj2 = KeyguardQuickAffordanceModel.Hidden.INSTANCE;
                        }
                        this.label = 1;
                        if (flowCollector.emit(obj2, this) == coroutineSingletons) {
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
                Object combineInternal = CombineKt.combineInternal(flowArr2, new Function0() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$combinedConfigs$lambda$15$$inlined$combine$1.2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return new KeyguardQuickAffordanceConfig.LockScreenState[flowArr2.length];
                    }
                }, new AnonymousClass3(null, singletonList), flowCollector, continuation);
                return combineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? combineInternal : Unit.INSTANCE;
            }
        };
    }

    /* JADX WARN: Removed duplicated region for block: B:41:0x00de  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0020  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object select(Continuation continuation) {
        KeyguardQuickAffordanceInteractor$select$1 keyguardQuickAffordanceInteractor$select$1;
        int i;
        Object obj;
        if (continuation instanceof KeyguardQuickAffordanceInteractor$select$1) {
            keyguardQuickAffordanceInteractor$select$1 = (KeyguardQuickAffordanceInteractor$select$1) continuation;
            int i2 = keyguardQuickAffordanceInteractor$select$1.label;
            if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                keyguardQuickAffordanceInteractor$select$1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                Object obj2 = keyguardQuickAffordanceInteractor$select$1.result;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                i = keyguardQuickAffordanceInteractor$select$1.label;
                if (i != 0) {
                    ResultKt.throwOnFailure(obj2);
                    throw new IllegalStateException("Check failed.".toString());
                }
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                String str = (String) keyguardQuickAffordanceInteractor$select$1.L$2;
                String str2 = (String) keyguardQuickAffordanceInteractor$select$1.L$1;
                KeyguardQuickAffordanceInteractor keyguardQuickAffordanceInteractor = (KeyguardQuickAffordanceInteractor) keyguardQuickAffordanceInteractor$select$1.L$0;
                ResultKt.throwOnFailure(obj2);
                if (((Boolean) obj2).booleanValue()) {
                    return Boolean.FALSE;
                }
                Iterator it = ((ArrayList) ((KeyguardQuickAffordanceRepository) keyguardQuickAffordanceInteractor.repository.get()).getSlotPickerRepresentations()).iterator();
                while (true) {
                    if (!it.hasNext()) {
                        obj = null;
                        break;
                    }
                    obj = it.next();
                    if (Intrinsics.areEqual(((KeyguardSlotPickerRepresentation) obj).f302id, str2)) {
                        break;
                    }
                }
                KeyguardSlotPickerRepresentation keyguardSlotPickerRepresentation = (KeyguardSlotPickerRepresentation) obj;
                if (keyguardSlotPickerRepresentation == null) {
                    return Boolean.FALSE;
                }
                Lazy lazy = keyguardQuickAffordanceInteractor.repository;
                ArrayList arrayList = new ArrayList((Collection) ((KeyguardQuickAffordanceRepository) lazy.get()).getCurrentSelections().getOrDefault(str2, EmptyList.INSTANCE));
                if (!arrayList.remove(str)) {
                    while (arrayList.size() > 0 && arrayList.size() >= keyguardSlotPickerRepresentation.maxSelectedAffordances) {
                        arrayList.remove(0);
                    }
                }
                arrayList.add(str);
                ((KeyguardQuickAffordanceSelectionManager) ((KeyguardQuickAffordanceRepository) lazy.get()).selectionManager.getValue()).setSelections(str2, arrayList);
                ((KeyguardQuickAffordancesMetricsLoggerImpl) keyguardQuickAffordanceInteractor.logger).getClass();
                StatsEvent.Builder newBuilder = StatsEvent.newBuilder();
                newBuilder.setAtomId(611);
                newBuilder.writeString(str2);
                newBuilder.writeString(str);
                newBuilder.usePooledBuffer();
                StatsLog.write(newBuilder.build());
                return Boolean.TRUE;
            }
        }
        keyguardQuickAffordanceInteractor$select$1 = new KeyguardQuickAffordanceInteractor$select$1(this, continuation);
        Object obj22 = keyguardQuickAffordanceInteractor$select$1.result;
        CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        i = keyguardQuickAffordanceInteractor$select$1.label;
        if (i != 0) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:49:0x00e9  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0020  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object unselect(Continuation continuation) {
        KeyguardQuickAffordanceInteractor$unselect$1 keyguardQuickAffordanceInteractor$unselect$1;
        int i;
        Object obj;
        if (continuation instanceof KeyguardQuickAffordanceInteractor$unselect$1) {
            keyguardQuickAffordanceInteractor$unselect$1 = (KeyguardQuickAffordanceInteractor$unselect$1) continuation;
            int i2 = keyguardQuickAffordanceInteractor$unselect$1.label;
            if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                keyguardQuickAffordanceInteractor$unselect$1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                Object obj2 = keyguardQuickAffordanceInteractor$unselect$1.result;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                i = keyguardQuickAffordanceInteractor$unselect$1.label;
                if (i != 0) {
                    ResultKt.throwOnFailure(obj2);
                    throw new IllegalStateException("Check failed.".toString());
                }
                boolean z = true;
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                String str = (String) keyguardQuickAffordanceInteractor$unselect$1.L$2;
                String str2 = (String) keyguardQuickAffordanceInteractor$unselect$1.L$1;
                KeyguardQuickAffordanceInteractor keyguardQuickAffordanceInteractor = (KeyguardQuickAffordanceInteractor) keyguardQuickAffordanceInteractor$unselect$1.L$0;
                ResultKt.throwOnFailure(obj2);
                if (((Boolean) obj2).booleanValue()) {
                    return Boolean.FALSE;
                }
                Iterator it = ((ArrayList) ((KeyguardQuickAffordanceRepository) keyguardQuickAffordanceInteractor.repository.get()).getSlotPickerRepresentations()).iterator();
                while (true) {
                    if (!it.hasNext()) {
                        obj = null;
                        break;
                    }
                    obj = it.next();
                    if (Intrinsics.areEqual(((KeyguardSlotPickerRepresentation) obj).f302id, str2)) {
                        break;
                    }
                }
                if (obj == null) {
                    return Boolean.FALSE;
                }
                boolean z2 = str == null || str.length() == 0;
                Lazy lazy = keyguardQuickAffordanceInteractor.repository;
                if (!z2) {
                    ArrayList arrayList = new ArrayList((Collection) ((KeyguardQuickAffordanceRepository) lazy.get()).getCurrentSelections().getOrDefault(str2, EmptyList.INSTANCE));
                    if (arrayList.remove(str)) {
                        ((KeyguardQuickAffordanceSelectionManager) ((KeyguardQuickAffordanceRepository) lazy.get()).selectionManager.getValue()).setSelections(str2, arrayList);
                    } else {
                        z = false;
                    }
                    return Boolean.valueOf(z);
                }
                Map currentSelections = ((KeyguardQuickAffordanceRepository) lazy.get()).getCurrentSelections();
                EmptyList emptyList = EmptyList.INSTANCE;
                if (((List) currentSelections.getOrDefault(str2, emptyList)).isEmpty()) {
                    z = false;
                } else {
                    ((KeyguardQuickAffordanceSelectionManager) ((KeyguardQuickAffordanceRepository) lazy.get()).selectionManager.getValue()).setSelections(str2, emptyList);
                }
                return Boolean.valueOf(z);
            }
        }
        keyguardQuickAffordanceInteractor$unselect$1 = new KeyguardQuickAffordanceInteractor$unselect$1(this, continuation);
        Object obj22 = keyguardQuickAffordanceInteractor$unselect$1.result;
        CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        i = keyguardQuickAffordanceInteractor$unselect$1.label;
        if (i != 0) {
        }
    }
}
