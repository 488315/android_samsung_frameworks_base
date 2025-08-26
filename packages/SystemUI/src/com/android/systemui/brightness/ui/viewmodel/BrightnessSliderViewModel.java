package com.android.systemui.brightness.ui.viewmodel;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import androidx.compose.runtime.State;
import com.android.systemui.R;
import com.android.systemui.brightness.domain.interactor.BrightnessPolicyEnforcementInteractor;
import com.android.systemui.brightness.domain.interactor.ScreenBrightnessInteractor;
import com.android.systemui.brightness.shared.model.GammaBrightness;
import com.android.systemui.brightness.ui.viewmodel.Drag;
import com.android.systemui.classifier.domain.interactor.FalsingInteractor;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.graphics.ImageLoader;
import com.android.systemui.haptics.slider.compose.ui.SliderHapticsViewModel;
import com.android.systemui.lifecycle.ExclusiveActivatable;
import com.android.systemui.lifecycle.Hydrator;
import com.android.systemui.settings.brightness.domain.interactor.BrightnessMirrorShowingInteractor;
import com.android.systemui.settings.brightness.ui.BrightnessWarningToast;
import com.samsung.android.knox.custom.CustomDeviceManager;
import kotlin.KotlinNothingValueException;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

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

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public interface Factory {
        BrightnessSliderViewModel create(boolean z);
    }

    /* renamed from: com.android.systemui.brightness.ui.viewmodel.BrightnessSliderViewModel$loadImage$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        int I$0;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return BrightnessSliderViewModel.this.loadImage(0, null, this);
        }
    }

    /* renamed from: com.android.systemui.brightness.ui.viewmodel.BrightnessSliderViewModel$onActivated$1, reason: invalid class name and case insensitive filesystem */
    final class C08231 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        public C08231(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return BrightnessSliderViewModel.this.onActivated(this);
        }
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
        this.currentBrightness$delegate = hydrator.hydratedStateOf("currentBrightness", GammaBrightness.m1066boximpl(initialValue), screenBrightnessInteractor.gammaBrightness);
        this.maxBrightness = CustomDeviceManager.QUICK_PANEL_ALL;
        this.policyRestriction = brightnessPolicyEnforcementInteractor.brightnessPolicyRestriction;
        this.brightnessOverriddenByWindow = screenBrightnessInteractor.brightnessOverriddenByWindow;
        this.showMirror$delegate = hydrator.hydratedStateOf(brightnessMirrorShowingInteractor.isShowing(), "showMirror");
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object loadImage(int i, Context context, Continuation continuation) {
        AnonymousClass1 anonymousClass1;
        if (continuation instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuation;
            int i2 = anonymousClass1.label;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i2 - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuation);
            }
        }
        Object objLoadDrawable$default = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i3 = anonymousClass1.label;
        if (i3 == 0) {
            ResultKt.throwOnFailure(objLoadDrawable$default);
            Icon iconCreateWithResource = Icon.createWithResource(context, i);
            anonymousClass1.I$0 = i;
            anonymousClass1.label = 1;
            objLoadDrawable$default = ImageLoader.loadDrawable$default(this.imageLoader, iconCreateWithResource, anonymousClass1);
            if (objLoadDrawable$default == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i3 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            i = anonymousClass1.I$0;
            ResultKt.throwOnFailure(objLoadDrawable$default);
        }
        objLoadDrawable$default.getClass();
        return new Icon.Loaded((Drawable) objLoadDrawable$default, null, new Integer(i));
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // com.android.systemui.lifecycle.ExclusiveActivatable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object onActivated(Continuation continuation) {
        C08231 c08231;
        if (continuation instanceof C08231) {
            c08231 = (C08231) continuation;
            int i = c08231.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c08231.label = i - Integer.MIN_VALUE;
            } else {
                c08231 = new C08231(continuation);
            }
        }
        Object obj = c08231.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c08231.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            c08231.label = 1;
            if (this.hydrator.activate(c08231) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        throw new KotlinNothingValueException();
    }

    public final Object onDrag(Drag drag, SuspendLambda suspendLambda) throws Throwable {
        boolean z = drag instanceof Drag.Dragging;
        ScreenBrightnessInteractor screenBrightnessInteractor = this.screenBrightnessInteractor;
        if (z) {
            Object objM1064setTemporaryBrightnesssaDbZGg = screenBrightnessInteractor.m1064setTemporaryBrightnesssaDbZGg(((Drag.Dragging) drag).brightness, suspendLambda);
            return objM1064setTemporaryBrightnesssaDbZGg == CoroutineSingletons.COROUTINE_SUSPENDED ? objM1064setTemporaryBrightnesssaDbZGg : Unit.INSTANCE;
        }
        if (!(drag instanceof Drag.Stopped)) {
            throw new NoWhenBranchMatchedException();
        }
        Object objM1063setBrightnesssaDbZGg = screenBrightnessInteractor.m1063setBrightnesssaDbZGg(((Drag.Stopped) drag).brightness, suspendLambda);
        return objM1063setBrightnesssaDbZGg == CoroutineSingletons.COROUTINE_SUSPENDED ? objM1063setBrightnesssaDbZGg : Unit.INSTANCE;
    }

    public final void setIsDragging(boolean z) {
        this.brightnessMirrorShowingInteractor.setMirrorShowing(z && this.supportsMirroring);
    }
}
