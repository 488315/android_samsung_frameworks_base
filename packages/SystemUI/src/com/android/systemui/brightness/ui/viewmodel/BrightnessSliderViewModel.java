package com.android.systemui.brightness.ui.viewmodel;

import androidx.compose.runtime.State;
import com.android.systemui.R;
import com.android.systemui.brightness.domain.interactor.BrightnessPolicyEnforcementInteractor;
import com.android.systemui.brightness.domain.interactor.ScreenBrightnessInteractor;
import com.android.systemui.brightness.shared.model.GammaBrightness;
import com.android.systemui.brightness.ui.viewmodel.Drag;
import com.android.systemui.classifier.domain.interactor.FalsingInteractor;
import com.android.systemui.graphics.ImageLoader;
import com.android.systemui.haptics.slider.compose.ui.SliderHapticsViewModel;
import com.android.systemui.lifecycle.ExclusiveActivatable;
import com.android.systemui.lifecycle.Hydrator;
import com.android.systemui.settings.brightness.domain.interactor.BrightnessMirrorShowingInteractor;
import com.android.systemui.settings.brightness.ui.BrightnessWarningToast;
import com.samsung.android.knox.custom.CustomDeviceManager;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class BrightnessSliderViewModel extends ExclusiveActivatable {
    public final BrightnessMirrorShowingInteractor brightnessMirrorShowingInteractor;
    public final ReadonlyStateFlow brightnessOverriddenByWindow;
    public final BrightnessPolicyEnforcementInteractor brightnessPolicyEnforcementInteractor;
    public final BrightnessWarningToast brightnessWarningToast;
    public final State currentBrightness$delegate;
    public final FalsingInteractor falsingInteractor;
    public final SliderHapticsViewModel.Factory hapticsViewModelFactory;
    public final Hydrator hydrator;
    public final ImageLoader imageLoader;
    public final int maxBrightness;
    public final Flow policyRestriction;
    public final ScreenBrightnessInteractor screenBrightnessInteractor;
    public final State showMirror$delegate;
    public final boolean supportsMirroring;
    public static final Companion Companion = new Companion(null);
    public static final int initialValue = -1;
    public static final BrightnessIcons icons = new BrightnessIcons(R.drawable.ic_brightness_low, R.drawable.ic_brightness_medium, R.drawable.ic_brightness_full);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
        BrightnessSliderViewModel create(boolean z);
    }

    public BrightnessSliderViewModel(ScreenBrightnessInteractor screenBrightnessInteractor, BrightnessPolicyEnforcementInteractor brightnessPolicyEnforcementInteractor, SliderHapticsViewModel.Factory factory, BrightnessMirrorShowingInteractor brightnessMirrorShowingInteractor, FalsingInteractor falsingInteractor, boolean z, BrightnessWarningToast brightnessWarningToast, ImageLoader imageLoader) {
        this.screenBrightnessInteractor = screenBrightnessInteractor;
        this.brightnessPolicyEnforcementInteractor = brightnessPolicyEnforcementInteractor;
        this.hapticsViewModelFactory = factory;
        this.brightnessMirrorShowingInteractor = brightnessMirrorShowingInteractor;
        this.falsingInteractor = falsingInteractor;
        this.supportsMirroring = z;
        this.brightnessWarningToast = brightnessWarningToast;
        this.imageLoader = imageLoader;
        Hydrator hydrator = new Hydrator("BrightnessSliderViewModel.hydrator", null, 2, null);
        this.hydrator = hydrator;
        this.currentBrightness$delegate = hydrator.hydratedStateOf("currentBrightness", GammaBrightness.m1064boximpl(initialValue), screenBrightnessInteractor.gammaBrightness);
        this.maxBrightness = CustomDeviceManager.QUICK_PANEL_ALL;
        this.policyRestriction = brightnessPolicyEnforcementInteractor.brightnessPolicyRestriction;
        this.brightnessOverriddenByWindow = screenBrightnessInteractor.brightnessOverriddenByWindow;
        this.showMirror$delegate = hydrator.hydratedStateOf(brightnessMirrorShowingInteractor.isShowing(), "showMirror");
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0031  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object loadImage(int r5, android.content.Context r6, kotlin.coroutines.Continuation r7) {
        /*
            r4 = this;
            boolean r0 = r7 instanceof com.android.systemui.brightness.ui.viewmodel.BrightnessSliderViewModel$loadImage$1
            if (r0 == 0) goto L13
            r0 = r7
            com.android.systemui.brightness.ui.viewmodel.BrightnessSliderViewModel$loadImage$1 r0 = (com.android.systemui.brightness.ui.viewmodel.BrightnessSliderViewModel$loadImage$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.brightness.ui.viewmodel.BrightnessSliderViewModel$loadImage$1 r0 = new com.android.systemui.brightness.ui.viewmodel.BrightnessSliderViewModel$loadImage$1
            r0.<init>(r4, r7)
        L18:
            java.lang.Object r7 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L31
            if (r2 != r3) goto L29
            int r5 = r0.I$0
            kotlin.ResultKt.throwOnFailure(r7)
            goto L45
        L29:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L31:
            kotlin.ResultKt.throwOnFailure(r7)
            android.graphics.drawable.Icon r6 = android.graphics.drawable.Icon.createWithResource(r6, r5)
            r0.I$0 = r5
            r0.label = r3
            com.android.systemui.graphics.ImageLoader r4 = r4.imageLoader
            java.lang.Object r7 = com.android.systemui.graphics.ImageLoader.loadDrawable$default(r4, r6, r0)
            if (r7 != r1) goto L45
            return r1
        L45:
            r7.getClass()
            android.graphics.drawable.Drawable r7 = (android.graphics.drawable.Drawable) r7
            java.lang.Integer r4 = new java.lang.Integer
            r4.<init>(r5)
            com.android.systemui.common.shared.model.Icon$Loaded r5 = new com.android.systemui.common.shared.model.Icon$Loaded
            r6 = 0
            r5.<init>(r7, r6, r4)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.brightness.ui.viewmodel.BrightnessSliderViewModel.loadImage(int, android.content.Context, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    @Override // com.android.systemui.lifecycle.ExclusiveActivatable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object onActivated(kotlin.coroutines.Continuation r5) {
        /*
            r4 = this;
            boolean r0 = r5 instanceof com.android.systemui.brightness.ui.viewmodel.BrightnessSliderViewModel$onActivated$1
            if (r0 == 0) goto L13
            r0 = r5
            com.android.systemui.brightness.ui.viewmodel.BrightnessSliderViewModel$onActivated$1 r0 = (com.android.systemui.brightness.ui.viewmodel.BrightnessSliderViewModel$onActivated$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.brightness.ui.viewmodel.BrightnessSliderViewModel$onActivated$1 r0 = new com.android.systemui.brightness.ui.viewmodel.BrightnessSliderViewModel$onActivated$1
            r0.<init>(r4, r5)
        L18:
            java.lang.Object r5 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L2f
            if (r2 == r3) goto L2b
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L2b:
            kotlin.ResultKt.throwOnFailure(r5)
            goto L3d
        L2f:
            kotlin.ResultKt.throwOnFailure(r5)
            r0.label = r3
            com.android.systemui.lifecycle.Hydrator r4 = r4.hydrator
            java.lang.Object r4 = r4.activate(r0)
            if (r4 != r1) goto L3d
            return r1
        L3d:
            kotlin.KotlinNothingValueException r4 = new kotlin.KotlinNothingValueException
            r4.<init>()
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.brightness.ui.viewmodel.BrightnessSliderViewModel.onActivated(kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final Object onDrag(Drag drag, SuspendLambda suspendLambda) {
        boolean z = drag instanceof Drag.Dragging;
        ScreenBrightnessInteractor screenBrightnessInteractor = this.screenBrightnessInteractor;
        if (z) {
            Object m1062setTemporaryBrightnesssaDbZGg = screenBrightnessInteractor.m1062setTemporaryBrightnesssaDbZGg(((Drag.Dragging) drag).brightness, suspendLambda);
            return m1062setTemporaryBrightnesssaDbZGg == CoroutineSingletons.COROUTINE_SUSPENDED ? m1062setTemporaryBrightnesssaDbZGg : Unit.INSTANCE;
        }
        if (!(drag instanceof Drag.Stopped)) {
            throw new NoWhenBranchMatchedException();
        }
        Object m1061setBrightnesssaDbZGg = screenBrightnessInteractor.m1061setBrightnesssaDbZGg(((Drag.Stopped) drag).brightness, suspendLambda);
        return m1061setBrightnesssaDbZGg == CoroutineSingletons.COROUTINE_SUSPENDED ? m1061setBrightnesssaDbZGg : Unit.INSTANCE;
    }

    public final void setIsDragging(boolean z) {
        this.brightnessMirrorShowingInteractor.setMirrorShowing(z && this.supportsMirroring);
    }
}
