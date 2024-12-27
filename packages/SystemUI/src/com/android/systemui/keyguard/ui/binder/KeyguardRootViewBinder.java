package com.android.systemui.keyguard.ui.binder;

import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.systemui.Flags;
import com.android.systemui.R;
import com.android.systemui.common.shared.model.NotificationContainerBounds;
import com.android.systemui.common.ui.ConfigurationState;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.keyguard.KeyguardViewMediator;
import com.android.systemui.keyguard.domain.interactor.KeyguardClockInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.ui.viewmodel.BurnInParameters;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardBlueprintViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardClockViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel;
import com.android.systemui.keyguard.ui.viewmodel.OccludingAppDeviceEntryMessageViewModel;
import com.android.systemui.keyguard.ui.viewmodel.TransitionData;
import com.android.systemui.keyguard.ui.viewmodel.ViewStateAccessor;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.phone.ScreenOffAnimationController;
import com.android.systemui.temporarydisplay.chipbar.ChipbarCoordinator;
import com.android.systemui.util.kotlin.DisposableHandles;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class KeyguardRootViewBinder {
    public static final KeyguardRootViewBinder INSTANCE = null;
    public static final int aodNotificationIconContainerId = 0;
    public static final int burnInLayerId = 0;
    public static final int deviceEntryIcon = 0;
    public static final int endButton = 0;
    public static final int indicationArea = 0;
    public static final int largeClockId = 0;
    public static final int lockIcon = 0;
    public static final int nsslPlaceholderId;
    public static final int startButton = 0;
    public static final int statusViewId;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class OnLayoutChange implements View.OnLayoutChangeListener {
        public final KeyguardBlueprintViewModel blueprintViewModel;
        public final MutableStateFlow burnInParams;
        public final Map childViews;
        public TransitionData prevTransition;
        public final KeyguardRootViewModel viewModel;

        public OnLayoutChange(KeyguardRootViewModel keyguardRootViewModel, KeyguardBlueprintViewModel keyguardBlueprintViewModel, KeyguardClockViewModel keyguardClockViewModel, Map<Integer, ? extends View> map, MutableStateFlow mutableStateFlow) {
            this.viewModel = keyguardRootViewModel;
            this.blueprintViewModel = keyguardBlueprintViewModel;
            this.childViews = map;
            this.burnInParams = mutableStateFlow;
        }

        @Override // android.view.View.OnLayoutChangeListener
        public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            StateFlowImpl stateFlowImpl;
            Object value;
            BurnInParameters burnInParameters;
            View view2;
            View view3 = (View) this.childViews.get(Integer.valueOf(KeyguardRootViewBinder.nsslPlaceholderId));
            if (view3 != null) {
                TransitionData transitionData = (TransitionData) this.blueprintViewModel.currentTransition.$$delegate_0.getValue();
                boolean z = transitionData != null && transitionData.config.type.getAnimateNotifChanges();
                if (Intrinsics.areEqual(this.prevTransition, transitionData) && z) {
                    return;
                }
                this.prevTransition = transitionData;
                KeyguardRootViewModel keyguardRootViewModel = this.viewModel;
                float top = view3.getTop();
                float bottom = view3.getBottom();
                keyguardRootViewModel.getClass();
                NotificationContainerBounds notificationContainerBounds = new NotificationContainerBounds(top, bottom, z);
                KeyguardInteractor keyguardInteractor = keyguardRootViewModel.keyguardInteractor;
                keyguardInteractor.getClass();
                RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                int i9 = SceneContainerFlag.$r8$clinit;
                Flags.sceneContainer();
                keyguardInteractor._notificationPlaceholderBounds.updateState(null, notificationContainerBounds);
            }
            MutableStateFlow mutableStateFlow = this.burnInParams;
            do {
                stateFlowImpl = (StateFlowImpl) mutableStateFlow;
                value = stateFlowImpl.getValue();
                burnInParameters = (BurnInParameters) value;
                Flags.migrateClocksToBlueprint();
                view2 = (View) this.childViews.get(Integer.valueOf(KeyguardRootViewBinder.statusViewId));
            } while (!stateFlowImpl.compareAndSet(value, BurnInParameters.copy$default(burnInParameters, 0, view2 != null ? view2.getTop() : 0, null, 5)));
        }
    }

    static {
        new KeyguardRootViewBinder();
        statusViewId = R.id.keyguard_status_view;
        nsslPlaceholderId = R.id.nssl_placeholder;
    }

    private KeyguardRootViewBinder() {
    }

    public static final DisposableHandles bind(final ViewGroup viewGroup, KeyguardRootViewModel keyguardRootViewModel, KeyguardBlueprintViewModel keyguardBlueprintViewModel, ConfigurationState configurationState, OccludingAppDeviceEntryMessageViewModel occludingAppDeviceEntryMessageViewModel, ChipbarCoordinator chipbarCoordinator, ScreenOffAnimationController screenOffAnimationController, ShadeInteractor shadeInteractor, KeyguardClockInteractor keyguardClockInteractor, KeyguardClockViewModel keyguardClockViewModel, InteractionJankMonitor interactionJankMonitor, DeviceEntryHapticsInteractor deviceEntryHapticsInteractor, VibratorHelper vibratorHelper, KeyguardViewMediator keyguardViewMediator) {
        DisposableHandles disposableHandles = new DisposableHandles();
        final LinkedHashMap linkedHashMap = new LinkedHashMap();
        Flags.keyguardBottomAreaRefactor();
        final StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(new BurnInParameters(0, 0, null, 7, null));
        KeyguardRootViewBinder$bind$2 keyguardRootViewBinder$bind$2 = new KeyguardRootViewBinder$bind$2(viewGroup, interactionJankMonitor, deviceEntryHapticsInteractor, vibratorHelper, occludingAppDeviceEntryMessageViewModel, chipbarCoordinator, keyguardRootViewModel, new ViewStateAccessor(new Function0() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder$bind$viewState$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Float.valueOf(viewGroup.getAlpha());
            }
        }, null, null, 6, null), linkedHashMap, configurationState, screenOffAnimationController, keyguardClockInteractor, keyguardViewMediator, shadeInteractor, MutableStateFlow, null);
        CoroutineContext coroutineContext = RepeatWhenAttachedKt.MAIN_DISPATCHER_SINGLETON;
        disposableHandles.plusAssign(RepeatWhenAttachedKt.repeatWhenAttached(viewGroup, EmptyCoroutineContext.INSTANCE, keyguardRootViewBinder$bind$2));
        Flags.migrateClocksToBlueprint();
        final OnLayoutChange onLayoutChange = new OnLayoutChange(keyguardRootViewModel, keyguardBlueprintViewModel, keyguardClockViewModel, linkedHashMap, MutableStateFlow);
        viewGroup.addOnLayoutChangeListener(onLayoutChange);
        disposableHandles.plusAssign(new DisposableHandle() { // from class: com.android.systemui.common.ui.view.ViewExtKt$onLayoutChanged$2
            @Override // kotlinx.coroutines.DisposableHandle
            public final void dispose() {
                viewGroup.removeOnLayoutChangeListener(onLayoutChange);
            }
        });
        viewGroup.setOnHierarchyChangeListener(new ViewGroup.OnHierarchyChangeListener() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder$bind$4
            @Override // android.view.ViewGroup.OnHierarchyChangeListener
            public final void onChildViewAdded(View view, View view2) {
                linkedHashMap.put(Integer.valueOf(view2.getId()), view2);
            }

            @Override // android.view.ViewGroup.OnHierarchyChangeListener
            public final void onChildViewRemoved(View view, View view2) {
                linkedHashMap.remove(Integer.valueOf(view2.getId()));
            }
        });
        disposableHandles.plusAssign(new DisposableHandle() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder$bind$5
            @Override // kotlinx.coroutines.DisposableHandle
            public final void dispose() {
                viewGroup.setOnHierarchyChangeListener(null);
                linkedHashMap.clear();
            }
        });
        viewGroup.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder$bind$6
            @Override // android.view.View.OnApplyWindowInsetsListener
            public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                StateFlowImpl stateFlowImpl;
                Object value;
                int systemBars = WindowInsets.Type.systemBars() | WindowInsets.Type.displayCutout();
                MutableStateFlow mutableStateFlow = MutableStateFlow.this;
                do {
                    stateFlowImpl = (StateFlowImpl) mutableStateFlow;
                    value = stateFlowImpl.getValue();
                } while (!stateFlowImpl.compareAndSet(value, BurnInParameters.copy$default((BurnInParameters) value, windowInsets.getInsetsIgnoringVisibility(systemBars).top, 0, null, 6)));
                return windowInsets;
            }
        });
        disposableHandles.plusAssign(new DisposableHandle() { // from class: com.android.systemui.common.ui.view.ViewExtKt$onApplyWindowInsets$1
            @Override // kotlinx.coroutines.DisposableHandle
            public final void dispose() {
                viewGroup.setOnApplyWindowInsetsListener(null);
            }
        });
        return disposableHandles;
    }
}
