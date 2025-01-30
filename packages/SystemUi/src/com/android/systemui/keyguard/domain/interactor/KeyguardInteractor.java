package com.android.systemui.keyguard.domain.interactor;

import android.support.v4.media.AbstractC0000x2c234b15;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.keyguard.data.repository.KeyguardBouncerRepository;
import com.android.systemui.keyguard.data.repository.KeyguardBouncerRepositoryImpl;
import com.android.systemui.keyguard.data.repository.KeyguardRepository;
import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl;
import com.android.systemui.keyguard.shared.model.CameraLaunchSourceModel;
import com.android.systemui.statusbar.CommandQueue;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardInteractor {
    public final StateFlow alternateBouncerShowing;
    public final Flow biometricUnlockState;
    public final CommandQueue commandQueue;
    public final Flow dozeAmount;
    public final Flow dozeTransitionModel;
    public final Flow isAbleToDream;
    public final Flow isAodAvailable;
    public final StateFlow isDozing;
    public final Flow isDreaming;
    public final Flow isKeyguardGoingAway;
    public final Flow isKeyguardOccluded;
    public final Flow isKeyguardShowing;
    public final Flow isKeyguardUnlocked;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 isKeyguardVisible;
    public final Flow isQuickSettingsVisible;
    public final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 isSecureCameraActive;
    public final Flow onCameraLaunchDetected;
    public final StateFlow primaryBouncerShowing;
    public final KeyguardRepository repository;
    public final Flow statusBarState;
    public final Flow wakefulnessModel;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[CameraLaunchSourceModel.values().length];
            try {
                iArr[CameraLaunchSourceModel.WIGGLE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[CameraLaunchSourceModel.POWER_DOUBLE_TAP.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[CameraLaunchSourceModel.LIFT_TRIGGER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[CameraLaunchSourceModel.QUICK_AFFORDANCE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        new Companion(null);
    }

    public KeyguardInteractor(KeyguardRepository keyguardRepository, CommandQueue commandQueue, FeatureFlags featureFlags, KeyguardBouncerRepository keyguardBouncerRepository) {
        this.repository = keyguardRepository;
        this.commandQueue = commandQueue;
        KeyguardRepositoryImpl keyguardRepositoryImpl = (KeyguardRepositoryImpl) keyguardRepository;
        this.dozeAmount = keyguardRepositoryImpl.linearDozeAmount;
        this.isDozing = keyguardRepositoryImpl.isDozing;
        this.isAodAvailable = keyguardRepositoryImpl.isAodAvailable;
        Flow flow = keyguardRepositoryImpl.dozeTransitionModel;
        this.dozeTransitionModel = flow;
        Flow flow2 = keyguardRepositoryImpl.isDreaming;
        this.isDreaming = flow2;
        Flow flow3 = keyguardRepositoryImpl.isDreamingWithOverlay;
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        KeyguardInteractor$onCameraLaunchDetected$1 keyguardInteractor$onCameraLaunchDetected$1 = new KeyguardInteractor$onCameraLaunchDetected$1(this, null);
        conflatedCallbackFlow.getClass();
        this.onCameraLaunchDetected = ConflatedCallbackFlow.conflatedCallbackFlow(keyguardInteractor$onCameraLaunchDetected$1);
        Flow flow4 = keyguardRepositoryImpl.wakefulness;
        this.wakefulnessModel = flow4;
        this.isAbleToDream = FlowKt.distinctUntilChanged(FlowKt.transformLatest(com.android.systemui.util.kotlin.FlowKt.sample(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(FlowKt.merge(flow2, flow3), flow, new KeyguardInteractor$isAbleToDream$1(null)), flow4, new KeyguardInteractor$isAbleToDream$2(null)), new KeyguardInteractor$special$$inlined$flatMapLatest$1(null)));
        Flow flow5 = keyguardRepositoryImpl.isKeyguardShowing;
        this.isKeyguardShowing = flow5;
        this.isKeyguardUnlocked = keyguardRepositoryImpl.isKeyguardUnlocked;
        Flow flow6 = keyguardRepositoryImpl.isKeyguardOccluded;
        this.isKeyguardOccluded = flow6;
        this.isKeyguardGoingAway = keyguardRepositoryImpl.isKeyguardGoingAway;
        KeyguardBouncerRepositoryImpl keyguardBouncerRepositoryImpl = (KeyguardBouncerRepositoryImpl) keyguardBouncerRepository;
        this.primaryBouncerShowing = keyguardBouncerRepositoryImpl.primaryBouncerShow;
        this.alternateBouncerShowing = keyguardBouncerRepositoryImpl.alternateBouncerVisible;
        this.statusBarState = keyguardRepositoryImpl.statusBarState;
        this.isQuickSettingsVisible = keyguardRepositoryImpl.isQuickSettingsVisible;
        this.biometricUnlockState = keyguardRepositoryImpl.biometricUnlockState;
        this.isKeyguardVisible = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(flow5, flow6, new KeyguardInteractor$isKeyguardVisible$1(null));
        Flags flags = Flags.INSTANCE;
        this.isSecureCameraActive = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE);
    }

    public static CameraLaunchSourceModel cameraLaunchSourceIntToModel(int i) {
        if (i == 0) {
            return CameraLaunchSourceModel.WIGGLE;
        }
        if (i == 1) {
            return CameraLaunchSourceModel.POWER_DOUBLE_TAP;
        }
        if (i == 2) {
            return CameraLaunchSourceModel.LIFT_TRIGGER;
        }
        if (i == 3) {
            return CameraLaunchSourceModel.QUICK_AFFORDANCE;
        }
        throw new IllegalArgumentException(AbstractC0000x2c234b15.m0m("Invalid CameraLaunchSourceModel int value: ", i));
    }

    public static int cameraLaunchSourceModelToInt(CameraLaunchSourceModel cameraLaunchSourceModel) {
        int i = WhenMappings.$EnumSwitchMapping$0[cameraLaunchSourceModel.ordinal()];
        if (i == 1) {
            return 0;
        }
        if (i == 2) {
            return 1;
        }
        if (i == 3) {
            return 2;
        }
        if (i == 4) {
            return 3;
        }
        throw new IllegalArgumentException("Invalid CameraLaunchSourceModel model: " + cameraLaunchSourceModel);
    }
}
