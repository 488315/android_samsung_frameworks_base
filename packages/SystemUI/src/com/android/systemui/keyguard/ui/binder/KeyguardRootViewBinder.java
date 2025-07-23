package com.android.systemui.keyguard.ui.binder;

import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import com.android.keyguard.AuthInteractionProperties;
import com.android.systemui.R;
import com.android.systemui.common.shared.model.NotificationContainerBounds;
import com.android.systemui.common.ui.ConfigurationState;
import com.android.systemui.common.ui.view.ViewExtKt$onApplyWindowInsets$1;
import com.android.systemui.common.ui.view.ViewExtKt$onLayoutChanged$2;
import com.android.systemui.common.ui.view.ViewExtKt$onTouchListener$1;
import com.android.systemui.customization.R$id;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.ui.view.layout.sections.AodPromotedNotificationSection;
import com.android.systemui.keyguard.ui.viewmodel.BurnInParameters;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardBlueprintViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardRootViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardSmartspaceViewModel;
import com.android.systemui.keyguard.ui.viewmodel.OccludingAppDeviceEntryMessageViewModel;
import com.android.systemui.keyguard.ui.viewmodel.TransitionData;
import com.android.systemui.keyguard.ui.viewmodel.ViewStateAccessor;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.Logger;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.temporarydisplay.chipbar.ChipbarCoordinator;
import com.android.systemui.util.kotlin.DisposableHandles;
import com.android.systemui.wallpapers.ui.viewmodel.WallpaperFocalAreaViewModel;
import com.google.android.msdl.domain.MSDLPlayer;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class KeyguardRootViewBinder {
    public static final int aodNotificationIconContainerId;
    public static final int aodPromotedNotificationId;
    public static final int bcSmartspaceId;
    public static final int deviceEntryIcon;
    public static final int endButton;
    public static final int indicationArea;
    public static final int largeClockId;
    public static final int nsslPlaceholderId;
    public static final int startButton;
    public static final KeyguardRootViewBinder INSTANCE = new KeyguardRootViewBinder();
    public static final int burnInLayerId = R.id.burn_in_layer;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class OnLayoutChange implements View.OnLayoutChangeListener {
        public final KeyguardBlueprintViewModel blueprintViewModel;
        public final MutableStateFlow burnInParams;
        public final Map childViews;
        public final Logger logger;
        public TransitionData prevTransition;
        public final KeyguardSmartspaceViewModel smartspaceViewModel;
        public final KeyguardRootViewModel viewModel;

        public OnLayoutChange(KeyguardRootViewModel keyguardRootViewModel, KeyguardBlueprintViewModel keyguardBlueprintViewModel, KeyguardSmartspaceViewModel keyguardSmartspaceViewModel, Map<Integer, ? extends View> map, MutableStateFlow mutableStateFlow, Logger logger) {
            this.viewModel = keyguardRootViewModel;
            this.blueprintViewModel = keyguardBlueprintViewModel;
            this.smartspaceViewModel = keyguardSmartspaceViewModel;
            this.childViews = map;
            this.burnInParams = mutableStateFlow;
            this.logger = logger;
        }

        @Override // android.view.View.OnLayoutChangeListener
        public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            Object value;
            BurnInParameters burnInParameters;
            int i9;
            int intValue = ((Number) this.smartspaceViewModel.bcSmartspaceVisibility.$$delegate_0.getValue()).intValue();
            View view2 = (View) this.childViews.get(Integer.valueOf(KeyguardRootViewBinder.bcSmartspaceId));
            boolean z = intValue != (view2 != null ? view2.getVisibility() : 8);
            View view3 = (View) this.childViews.get(Integer.valueOf(KeyguardRootViewBinder.nsslPlaceholderId));
            if (view3 != null) {
                TransitionData transitionData = (TransitionData) this.blueprintViewModel.currentTransition.$$delegate_0.getValue();
                boolean z2 = transitionData != null && transitionData.config.type.getAnimateNotifChanges();
                if (Intrinsics.areEqual(this.prevTransition, transitionData) && z2 && !z) {
                    Logger.w$default(this.logger, "Skipping onNotificationContainerBoundsChanged during transition", null, 2, null);
                    return;
                }
                this.prevTransition = transitionData;
                KeyguardRootViewModel keyguardRootViewModel = this.viewModel;
                float top = view3.getTop();
                float bottom = view3.getBottom();
                boolean z3 = z2 || z;
                keyguardRootViewModel.getClass();
                NotificationContainerBounds notificationContainerBounds = new NotificationContainerBounds(top, bottom, z3);
                KeyguardInteractor keyguardInteractor = keyguardRootViewModel.keyguardInteractor;
                keyguardInteractor.getClass();
                RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                int i10 = SceneContainerFlag.$r8$clinit;
                keyguardInteractor._notificationPlaceholderBounds.updateState(null, notificationContainerBounds);
            }
            MutableStateFlow mutableStateFlow = this.burnInParams;
            do {
                value = mutableStateFlow.getValue();
                burnInParameters = (BurnInParameters) value;
                i9 = Integer.MAX_VALUE;
                for (Map.Entry entry : this.childViews.entrySet()) {
                    ((Number) entry.getKey()).intValue();
                    View view4 = (View) entry.getValue();
                    i9 = Math.min(i9, (view4.getId() == KeyguardRootViewBinder.burnInLayerId || view4.getVisibility() != 0 || view4.getWidth() <= 0 || view4.getHeight() <= 0) ? Integer.MAX_VALUE : view4.getTop());
                }
            } while (!mutableStateFlow.compareAndSet(value, BurnInParameters.copy$default(burnInParameters, 0, i9, null, null, 13)));
        }
    }

    static {
        AodPromotedNotificationSection.Companion.getClass();
        aodPromotedNotificationId = AodPromotedNotificationSection.viewId;
        aodNotificationIconContainerId = R.id.aod_notification_icon_container;
        largeClockId = R$id.lockscreen_clock_view_large;
        bcSmartspaceId = R.id.bc_smartspace_view;
        indicationArea = R.id.keyguard_indication_area;
        startButton = R.id.start_button;
        endButton = R.id.end_button;
        deviceEntryIcon = R.id.device_entry_icon_view;
        nsslPlaceholderId = R.id.nssl_placeholder;
        new AuthInteractionProperties(null, 1, null);
    }

    private KeyguardRootViewBinder() {
    }

    /* JADX WARN: Type inference failed for: r9v2, types: [com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder$$ExternalSyntheticLambda1] */
    public static final DisposableHandles bind(final ViewGroup viewGroup, final KeyguardRootViewModel keyguardRootViewModel, KeyguardBlueprintViewModel keyguardBlueprintViewModel, ConfigurationState configurationState, OccludingAppDeviceEntryMessageViewModel occludingAppDeviceEntryMessageViewModel, ChipbarCoordinator chipbarCoordinator, ShadeInteractor shadeInteractor, KeyguardSmartspaceViewModel keyguardSmartspaceViewModel, DeviceEntryHapticsInteractor deviceEntryHapticsInteractor, VibratorHelper vibratorHelper, final FalsingManager falsingManager, CoroutineDispatcher coroutineDispatcher, MSDLPlayer mSDLPlayer, LogBuffer logBuffer, WallpaperFocalAreaViewModel wallpaperFocalAreaViewModel) {
        Object value;
        Function0 function0;
        final int i;
        DisposableHandles disposableHandles = new DisposableHandles();
        final LinkedHashMap linkedHashMap = new LinkedHashMap();
        viewGroup.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder$bind$1
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                FalsingManager falsingManager2 = FalsingManager.this;
                if (falsingManager2 != null && !falsingManager2.isFalseTap(1)) {
                    KeyguardRootViewModel keyguardRootViewModel2 = keyguardRootViewModel;
                    ((KeyguardRepositoryImpl) keyguardRootViewModel2.keyguardInteractor.repository).lastRootViewTapPosition.updateState(null, new Point((int) motionEvent.getX(), (int) motionEvent.getY()));
                }
                return false;
            }
        });
        disposableHandles.plusAssign(new ViewExtKt$onTouchListener$1(viewGroup));
        final StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(new BurnInParameters(0, 0, null, null, 15, null));
        final int i2 = 2;
        disposableHandles.plusAssign(RepeatWhenAttachedKt.repeatWhenAttached(viewGroup, coroutineDispatcher, new KeyguardRootViewBinder$bind$2(keyguardRootViewModel, viewGroup, new ViewStateAccessor(new Function0() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Object obj = viewGroup;
                switch (i2) {
                    case 0:
                        View view = (View) ((Map) obj).get(Integer.valueOf(KeyguardRootViewBinder.burnInLayerId));
                        if (view != null) {
                            return Float.valueOf(view.getTranslationY());
                        }
                        return null;
                    case 1:
                        View view2 = (View) ((Map) obj).get(Integer.valueOf(KeyguardRootViewBinder.burnInLayerId));
                        if (view2 != null) {
                            return Float.valueOf(view2.getTranslationX());
                        }
                        return null;
                    default:
                        KeyguardRootViewBinder keyguardRootViewBinder = KeyguardRootViewBinder.INSTANCE;
                        return Float.valueOf(((ViewGroup) obj).getAlpha());
                }
            }
        }, null, null, 6, null), linkedHashMap, null)));
        disposableHandles.plusAssign(RepeatWhenAttachedKt.repeatWhenAttached(viewGroup, EmptyCoroutineContext.INSTANCE, new KeyguardRootViewBinder$bind$3(viewGroup, deviceEntryHapticsInteractor, vibratorHelper, occludingAppDeviceEntryMessageViewModel, chipbarCoordinator, keyguardRootViewModel, linkedHashMap, keyguardBlueprintViewModel, configurationState, shadeInteractor, MutableStateFlow, mSDLPlayer, null)));
        do {
            value = MutableStateFlow.getValue();
            final int i3 = 0;
            function0 = new Function0() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Object obj = linkedHashMap;
                    switch (i3) {
                        case 0:
                            View view = (View) ((Map) obj).get(Integer.valueOf(KeyguardRootViewBinder.burnInLayerId));
                            if (view != null) {
                                return Float.valueOf(view.getTranslationY());
                            }
                            return null;
                        case 1:
                            View view2 = (View) ((Map) obj).get(Integer.valueOf(KeyguardRootViewBinder.burnInLayerId));
                            if (view2 != null) {
                                return Float.valueOf(view2.getTranslationX());
                            }
                            return null;
                        default:
                            KeyguardRootViewBinder keyguardRootViewBinder = KeyguardRootViewBinder.INSTANCE;
                            return Float.valueOf(((ViewGroup) obj).getAlpha());
                    }
                }
            };
            i = 1;
        } while (!MutableStateFlow.compareAndSet(value, BurnInParameters.copy$default((BurnInParameters) value, 0, 0, function0, new Function0() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Object obj = linkedHashMap;
                switch (i) {
                    case 0:
                        View view = (View) ((Map) obj).get(Integer.valueOf(KeyguardRootViewBinder.burnInLayerId));
                        if (view != null) {
                            return Float.valueOf(view.getTranslationY());
                        }
                        return null;
                    case 1:
                        View view2 = (View) ((Map) obj).get(Integer.valueOf(KeyguardRootViewBinder.burnInLayerId));
                        if (view2 != null) {
                            return Float.valueOf(view2.getTranslationX());
                        }
                        return null;
                    default:
                        KeyguardRootViewBinder keyguardRootViewBinder = KeyguardRootViewBinder.INSTANCE;
                        return Float.valueOf(((ViewGroup) obj).getAlpha());
                }
            }
        }, 3)));
        disposableHandles.plusAssign(RepeatWhenAttachedKt.repeatWhenAttached(viewGroup, EmptyCoroutineContext.INSTANCE, new KeyguardRootViewBinder$bind$5(wallpaperFocalAreaViewModel, null)));
        OnLayoutChange onLayoutChange = new OnLayoutChange(keyguardRootViewModel, keyguardBlueprintViewModel, keyguardSmartspaceViewModel, linkedHashMap, MutableStateFlow, new Logger(logBuffer, "KeyguardRootViewBinder"));
        viewGroup.addOnLayoutChangeListener(onLayoutChange);
        disposableHandles.plusAssign(new ViewExtKt$onLayoutChanged$2(viewGroup, onLayoutChange));
        viewGroup.setOnHierarchyChangeListener(new ViewGroup.OnHierarchyChangeListener() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder$bind$6
            @Override // android.view.ViewGroup.OnHierarchyChangeListener
            public final void onChildViewAdded(View view, View view2) {
                linkedHashMap.put(Integer.valueOf(view2.getId()), view2);
            }

            @Override // android.view.ViewGroup.OnHierarchyChangeListener
            public final void onChildViewRemoved(View view, View view2) {
                linkedHashMap.remove(Integer.valueOf(view2.getId()));
            }
        });
        disposableHandles.plusAssign(new DisposableHandle() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder$bind$7
            @Override // kotlinx.coroutines.DisposableHandle
            public final void dispose() {
                viewGroup.setOnHierarchyChangeListener(null);
                linkedHashMap.clear();
            }
        });
        viewGroup.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardRootViewBinder$bind$8
            @Override // android.view.View.OnApplyWindowInsetsListener
            public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                Object value2;
                int systemBars = WindowInsets.Type.systemBars() | WindowInsets.Type.displayCutout();
                MutableStateFlow mutableStateFlow = MutableStateFlow.this;
                do {
                    value2 = mutableStateFlow.getValue();
                } while (!mutableStateFlow.compareAndSet(value2, BurnInParameters.copy$default((BurnInParameters) value2, windowInsets.getInsetsIgnoringVisibility(systemBars).top, 0, null, null, 14)));
                return windowInsets;
            }
        });
        disposableHandles.plusAssign(new ViewExtKt$onApplyWindowInsets$1(viewGroup));
        return disposableHandles;
    }
}
