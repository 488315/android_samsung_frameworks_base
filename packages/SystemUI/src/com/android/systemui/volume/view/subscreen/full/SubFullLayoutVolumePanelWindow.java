package com.android.systemui.volume.view.subscreen.full;

import android.R;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.PowerManager;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.LinearInterpolator;
import android.view.animation.PathInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Space;
import android.widget.TextView;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import com.android.keyguard.ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.volume.VolumeDependency;
import com.android.systemui.volume.VolumeDependencyBase;
import com.android.systemui.volume.config.SystemConfigImpl;
import com.android.systemui.volume.config.VolumeConfigs;
import com.android.systemui.volume.store.StoreInteractor;
import com.android.systemui.volume.store.VolumePanelStore;
import com.android.systemui.volume.util.BlurEffect;
import com.android.systemui.volume.util.ColorUtils;
import com.android.systemui.volume.util.ContextUtils;
import com.android.systemui.volume.util.DisplayManagerWrapper;
import com.android.systemui.volume.util.HandlerWrapper;
import com.android.systemui.volume.util.IDisplayManagerWrapper;
import com.android.systemui.volume.util.PluginAODManagerWrapper;
import com.android.systemui.volume.util.PowerManagerWrapper;
import com.android.systemui.volume.util.SystemServiceExtension;
import com.android.systemui.volume.util.VibratorWrapper;
import com.android.systemui.volume.util.ViewLocationUtil;
import com.android.systemui.volume.util.ViewVisibilityUtil;
import com.android.systemui.volume.view.expand.VolumePanelExpandView$adjustTouchEventForOutsideTouch$1$$ExternalSyntheticOutline0;
import com.samsung.systemui.splugins.extensions.VolumePanelStateExt;
import com.samsung.systemui.splugins.volume.VolumeInfraMediator;
import com.samsung.systemui.splugins.volume.VolumeObserver;
import com.samsung.systemui.splugins.volume.VolumePanelAction;
import com.samsung.systemui.splugins.volume.VolumePanelRow;
import com.samsung.systemui.splugins.volume.VolumePanelState;
import java.util.function.Supplier;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class SubFullLayoutVolumePanelWindow extends Dialog implements VolumeObserver {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final VolumeInfraMediator infraMediator;
    public final LogWrapper log;
    public final SubFullLayoutVolumePanelView panelView;
    public final Lazy store$delegate;
    public final Lazy storeInteractor$delegate;
    public final VolumeDependency volDeps;

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
            int[] iArr = new int[VolumePanelState.StateType.values().length];
            try {
                iArr[VolumePanelState.StateType.STATE_SHOW_SUB_DISPLAY_VOLUME_PANEL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_DUAL_PLAY_MODE_CHANGED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_OPEN_THEME_CHANGED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_DISMISS_VOLUME_PANEL.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_DISMISS.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_FOLDER_STATE_CHANGED.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_ORIENTATION_CHANGED.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_COVER_STATE_CHANGED.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_EXPAND_STATE_CHANGED.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public SubFullLayoutVolumePanelWindow(VolumeDependencyBase volumeDependencyBase) {
        VolumeDependency volumeDependency = (VolumeDependency) volumeDependencyBase;
        Context context = (Context) volumeDependency.get(Context.class);
        Display frontSubDisplay = ((DisplayManagerWrapper) volumeDependency.get(DisplayManagerWrapper.class)).getFrontSubDisplay();
        frontSubDisplay.getClass();
        super(context.createWindowContext(frontSubDisplay, 2020, null));
        this.volDeps = volumeDependency;
        final int i = 0;
        this.store$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelWindow$$ExternalSyntheticLambda0
            public final /* synthetic */ SubFullLayoutVolumePanelWindow f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                SubFullLayoutVolumePanelWindow subFullLayoutVolumePanelWindow = this.f$0;
                switch (i) {
                    case 0:
                        return (VolumePanelStore) subFullLayoutVolumePanelWindow.volDeps.get(VolumePanelStore.class);
                    default:
                        int i2 = SubFullLayoutVolumePanelWindow.$r8$clinit;
                        return new StoreInteractor(subFullLayoutVolumePanelWindow, (VolumePanelStore) subFullLayoutVolumePanelWindow.store$delegate.getValue());
                }
            }
        });
        final int i2 = 1;
        this.storeInteractor$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelWindow$$ExternalSyntheticLambda0
            public final /* synthetic */ SubFullLayoutVolumePanelWindow f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                SubFullLayoutVolumePanelWindow subFullLayoutVolumePanelWindow = this.f$0;
                switch (i2) {
                    case 0:
                        return (VolumePanelStore) subFullLayoutVolumePanelWindow.volDeps.get(VolumePanelStore.class);
                    default:
                        int i22 = SubFullLayoutVolumePanelWindow.$r8$clinit;
                        return new StoreInteractor(subFullLayoutVolumePanelWindow, (VolumePanelStore) subFullLayoutVolumePanelWindow.store$delegate.getValue());
                }
            }
        });
        SystemConfigImpl systemConfigImpl = (SystemConfigImpl) ((VolumeConfigs) volumeDependency.get(VolumeConfigs.class)).systemConfig$delegate.getValue();
        this.log = (LogWrapper) volumeDependency.get(LogWrapper.class);
        this.infraMediator = (VolumeInfraMediator) volumeDependency.get(VolumeInfraMediator.class);
        Window window = getWindow();
        if (window != null) {
            window.requestFeature(1);
            window.setBackgroundDrawable(new ColorDrawable(0));
            window.clearFlags(2);
            window.addFlags(R.interpolator.launch_task_micro_alpha);
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.type = 2020;
            attributes.format = -3;
            attributes.setTitle("SubFullLayoutVolumePanelWindow");
            attributes.width = -2;
            attributes.height = -1;
            attributes.gravity = 21;
            attributes.windowAnimations = -1;
            attributes.accessibilityTitle = window.getContext().getString(com.android.systemui.R.string.volume_panel_view_title);
            if (((Boolean) systemConfigImpl.hasCutout$delegate.getValue()).booleanValue()) {
                attributes.flags |= 67109888;
                attributes.layoutInDisplayCutoutMode = 2;
            }
            window.setAttributes(attributes);
        }
        setContentView(com.android.systemui.R.layout.sub_full_volume_panel_view);
        final SubFullLayoutVolumePanelView subFullLayoutVolumePanelView = (SubFullLayoutVolumePanelView) requireViewById(com.android.systemui.R.id.volume_panel_view_root);
        this.panelView = subFullLayoutVolumePanelView;
        subFullLayoutVolumePanelView.getClass();
        Log.d("SubFullLayoutVolumePanelView", "SubFullLayoutVolumePanelView: bind");
        subFullLayoutVolumePanelView.dialog = this;
        subFullLayoutVolumePanelView.handlerWrapper = (HandlerWrapper) volumeDependency.get(HandlerWrapper.class);
        VolumePanelStore volumePanelStore = (VolumePanelStore) volumeDependency.get(VolumePanelStore.class);
        subFullLayoutVolumePanelView.store = volumePanelStore;
        subFullLayoutVolumePanelView.storeInteractor.store = volumePanelStore == null ? null : volumePanelStore;
        subFullLayoutVolumePanelView.volDeps = volumeDependency;
        subFullLayoutVolumePanelView.volumePanelMotion = (SubFullLayoutVolumePanelMotion) volumeDependency.get(SubFullLayoutVolumePanelMotion.class);
        subFullLayoutVolumePanelView.blurEffect = new BlurEffect(subFullLayoutVolumePanelView.getContext(), volumeDependency);
        subFullLayoutVolumePanelView.iDisplayManagerWrapper = (IDisplayManagerWrapper) volumeDependency.get(IDisplayManagerWrapper.class);
        subFullLayoutVolumePanelView.vibratorWrapper = (VibratorWrapper) volumeDependency.get(VibratorWrapper.class);
        subFullLayoutVolumePanelView.powerManagerWrapper = (PowerManagerWrapper) volumeDependency.get(PowerManagerWrapper.class);
        subFullLayoutVolumePanelView.pluginAODManagerWrapper = (PluginAODManagerWrapper) volumeDependency.get(PluginAODManagerWrapper.class);
        ViewGroup viewGroup = subFullLayoutVolumePanelView.volumePanelDualView;
        (viewGroup == null ? null : viewGroup).setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelView$bind$1
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                VolumePanelExpandView$adjustTouchEventForOutsideTouch$1$$ExternalSyntheticOutline0.m(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_TOUCH_OUTSIDE), true, subFullLayoutVolumePanelView.storeInteractor, false);
                return true;
            }
        });
        ViewGroup viewGroup2 = subFullLayoutVolumePanelView.volumePanelDualView;
        ((ViewGroup) (viewGroup2 == null ? null : viewGroup2).requireViewById(com.android.systemui.R.id.volume_panel_dual_view_contents)).setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelView$bind$2
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        SubFullLayoutVolumePanelWindow subFullLayoutVolumePanelWindow = subFullLayoutVolumePanelView.dialog;
        (subFullLayoutVolumePanelWindow == null ? null : subFullLayoutVolumePanelWindow).setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelView$bind$3
            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r11v6, types: [com.android.systemui.volume.util.PluginAODManagerWrapper] */
            /* JADX WARN: Type inference failed for: r5v3, types: [com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelView$bind$3$1$1] */
            @Override // android.content.DialogInterface.OnShowListener
            public final void onShow(DialogInterface dialogInterface) {
                SubFullLayoutVolumePanelView$bind$3$showBlurRunnable$1 subFullLayoutVolumePanelView$bind$3$showBlurRunnable$1 = new Runnable() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelView$bind$3$showBlurRunnable$1
                    @Override // java.lang.Runnable
                    public final void run() {
                    }
                };
                SubFullLayoutVolumePanelView subFullLayoutVolumePanelView2 = subFullLayoutVolumePanelView;
                final ImageView imageView = subFullLayoutVolumePanelView2.isDualViewEnabled ? (ImageView) subFullLayoutVolumePanelView2.findViewById(com.android.systemui.R.id.volume_panel_dual_blur) : (ImageView) subFullLayoutVolumePanelView2.findViewById(com.android.systemui.R.id.volume_panel_blur);
                boolean z = BasicRune.VOLUME_CAPTURED_BLUR;
                if (z && imageView != null) {
                    final SubFullLayoutVolumePanelView subFullLayoutVolumePanelView3 = subFullLayoutVolumePanelView;
                    subFullLayoutVolumePanelView$bind$3$showBlurRunnable$1 = new Runnable() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelView$bind$3$1$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            SubFullLayoutVolumePanelView subFullLayoutVolumePanelView4 = subFullLayoutVolumePanelView3;
                            BlurEffect blurEffect = subFullLayoutVolumePanelView4.blurEffect;
                            BlurEffect blurEffect2 = blurEffect == null ? null : blurEffect;
                            ImageView imageView2 = imageView;
                            if (blurEffect == null) {
                                blurEffect = null;
                            }
                            boolean zIsNightMode = ContextUtils.isNightMode(subFullLayoutVolumePanelView4.getContext());
                            blurEffect.getClass();
                            int i3 = zIsNightMode ? 106 : 121;
                            final ImageView imageView3 = imageView;
                            final SubFullLayoutVolumePanelView subFullLayoutVolumePanelView5 = subFullLayoutVolumePanelView3;
                            blurEffect2.setCapturedBlur(imageView2, i3, new Supplier() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelView$bind$3$1$1.1
                                @Override // java.util.function.Supplier
                                public final Object get() {
                                    ViewLocationUtil viewLocationUtil = ViewLocationUtil.INSTANCE;
                                    ImageView imageView4 = imageView3;
                                    viewLocationUtil.getClass();
                                    int[] iArr = new int[2];
                                    imageView4.getLocationOnScreen(iArr);
                                    SubFullLayoutVolumePanelView subFullLayoutVolumePanelView6 = subFullLayoutVolumePanelView5;
                                    if (subFullLayoutVolumePanelView6.isDualViewEnabled) {
                                        iArr[0] = iArr[0] - ((int) (imageView3.getWidth() * 0.05d));
                                        iArr[1] = iArr[1] - ((int) (imageView3.getHeight() * 0.05d));
                                        return iArr;
                                    }
                                    int i4 = iArr[0];
                                    SubFullLayoutVolumePanelWindow subFullLayoutVolumePanelWindow2 = subFullLayoutVolumePanelView6.dialog;
                                    if (subFullLayoutVolumePanelWindow2 == null) {
                                        subFullLayoutVolumePanelWindow2 = null;
                                    }
                                    Window window2 = subFullLayoutVolumePanelWindow2.getWindow();
                                    window2.getClass();
                                    iArr[0] = i4 - (window2.getDecorView().getWidth() * (BasicRune.VOLUME_LEFT_DISPLAY_VOLUME_DIALOG ? -1 : 1));
                                    return iArr;
                                }
                            });
                        }
                    };
                }
                SubFullLayoutVolumePanelView subFullLayoutVolumePanelView4 = subFullLayoutVolumePanelView;
                if (subFullLayoutVolumePanelView4.isDualViewEnabled) {
                    final SubFullLayoutVolumePanelMotion subFullLayoutVolumePanelMotion = subFullLayoutVolumePanelView4.volumePanelMotion;
                    if (subFullLayoutVolumePanelMotion == null) {
                        subFullLayoutVolumePanelMotion = null;
                    }
                    SubFullLayoutVolumePanelWindow subFullLayoutVolumePanelWindow2 = subFullLayoutVolumePanelView4.dialog;
                    Window window2 = (subFullLayoutVolumePanelWindow2 != null ? subFullLayoutVolumePanelWindow2 : null).getWindow();
                    window2.getClass();
                    final View decorView = window2.getDecorView();
                    subFullLayoutVolumePanelMotion.getClass();
                    decorView.setTranslationX(0.0f);
                    ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(decorView, "alpha", decorView.getAlpha(), 1.0f);
                    objectAnimatorOfFloat.setDuration(200L);
                    objectAnimatorOfFloat.setInterpolator(new LinearInterpolator());
                    ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(decorView, "scaleX", 0.9f, 1.0f);
                    objectAnimatorOfFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelMotion$startVolumeDualViewShowAnimation$scaleAnimator$1$1
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                            decorView.setScaleY(((Float) valueAnimator.getAnimatedValue()).floatValue());
                        }
                    });
                    objectAnimatorOfFloat2.setDuration(400L);
                    objectAnimatorOfFloat2.setInterpolator(new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f));
                    AnimatorSet animatorSet = new AnimatorSet();
                    animatorSet.playTogether(objectAnimatorOfFloat);
                    animatorSet.playTogether(objectAnimatorOfFloat2);
                    animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelMotion$startVolumeDualViewShowAnimation$1$1
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public final void onAnimationEnd(Animator animator) {
                            subFullLayoutVolumePanelMotion.storeInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_ANIMATION_FINISHED).build(), true);
                        }

                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public final void onAnimationStart(Animator animator) {
                            subFullLayoutVolumePanelMotion.storeInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_ANIMATION_START).build(), true);
                        }
                    });
                    animatorSet.start();
                    subFullLayoutVolumePanelMotion.dualShowAnimation = animatorSet;
                    if (z) {
                        subFullLayoutVolumePanelView$bind$3$showBlurRunnable$1.run();
                        return;
                    }
                    return;
                }
                VolumePanelState volumePanelState = subFullLayoutVolumePanelView4.panelState;
                if (volumePanelState == null) {
                    volumePanelState = null;
                }
                if (!VolumePanelStateExt.isAODVolumePanel(volumePanelState)) {
                    SubFullLayoutVolumePanelView subFullLayoutVolumePanelView5 = subFullLayoutVolumePanelView;
                    SubFullLayoutVolumePanelMotion subFullLayoutVolumePanelMotion2 = subFullLayoutVolumePanelView5.volumePanelMotion;
                    if (subFullLayoutVolumePanelMotion2 == null) {
                        subFullLayoutVolumePanelMotion2 = null;
                    }
                    SubFullLayoutVolumePanelWindow subFullLayoutVolumePanelWindow3 = subFullLayoutVolumePanelView5.dialog;
                    Window window3 = (subFullLayoutVolumePanelWindow3 != null ? subFullLayoutVolumePanelWindow3 : null).getWindow();
                    window3.getClass();
                    View decorView2 = window3.getDecorView();
                    subFullLayoutVolumePanelMotion2.getClass();
                    SpringAnimation springAnimation = new SpringAnimation(decorView2, DynamicAnimation.TRANSLATION_X);
                    springAnimation.mSpring = ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(150.0f, 0.7f);
                    decorView2.setAlpha(1.0f);
                    decorView2.setScaleX(1.0f);
                    decorView2.setScaleY(1.0f);
                    decorView2.setTranslationX(BasicRune.VOLUME_LEFT_DISPLAY_VOLUME_DIALOG ? -decorView2.getWidth() : decorView2.getWidth());
                    springAnimation.mVelocity = 0.0f;
                    springAnimation.animateToFinalPosition(0.0f);
                    if (z) {
                        subFullLayoutVolumePanelView$bind$3$showBlurRunnable$1.run();
                    }
                    subFullLayoutVolumePanelMotion2.singleShowSpringAnimation = springAnimation;
                    return;
                }
                SubFullLayoutVolumePanelView subFullLayoutVolumePanelView6 = subFullLayoutVolumePanelView;
                SubFullLayoutVolumePanelMotion subFullLayoutVolumePanelMotion3 = subFullLayoutVolumePanelView6.volumePanelMotion;
                if (subFullLayoutVolumePanelMotion3 == null) {
                    subFullLayoutVolumePanelMotion3 = null;
                }
                SubFullLayoutVolumePanelWindow subFullLayoutVolumePanelWindow4 = subFullLayoutVolumePanelView6.dialog;
                if (subFullLayoutVolumePanelWindow4 == null) {
                    subFullLayoutVolumePanelWindow4 = null;
                }
                Window window4 = subFullLayoutVolumePanelWindow4.getWindow();
                window4.getClass();
                View decorView3 = window4.getDecorView();
                subFullLayoutVolumePanelMotion3.getClass();
                decorView3.setTranslationX(0.0f);
                decorView3.setScaleX(1.0f);
                decorView3.setScaleY(1.0f);
                ObjectAnimator objectAnimatorOfFloat3 = ObjectAnimator.ofFloat(decorView3, "alpha", decorView3.getAlpha(), 1.0f);
                objectAnimatorOfFloat3.setDuration(100L);
                objectAnimatorOfFloat3.setInterpolator(new LinearInterpolator());
                objectAnimatorOfFloat3.start();
                SubFullLayoutVolumePanelView subFullLayoutVolumePanelView7 = subFullLayoutVolumePanelView;
                subFullLayoutVolumePanelView7.isFirstTouch = true;
                PowerManagerWrapper powerManagerWrapper = subFullLayoutVolumePanelView7.powerManagerWrapper;
                if (powerManagerWrapper == null) {
                    powerManagerWrapper = null;
                }
                Context context2 = subFullLayoutVolumePanelView7.getContext();
                powerManagerWrapper.getClass();
                SystemServiceExtension.INSTANCE.getClass();
                Object systemService = context2.getSystemService((Class<Object>) PowerManager.class);
                systemService.getClass();
                PowerManager.WakeLock wakeLockNewWakeLock = ((PowerManager) systemService).newWakeLock(1, "AOD_VolumePanel");
                wakeLockNewWakeLock.acquire();
                powerManagerWrapper.wakeLock = wakeLockNewWakeLock;
                ?? r11 = subFullLayoutVolumePanelView.pluginAODManagerWrapper;
                (r11 != 0 ? r11 : null).getClass();
                PluginAODManagerWrapper.requestAODVolumePanel(true);
            }
        });
        SubFullLayoutVolumePanelWindow subFullLayoutVolumePanelWindow2 = subFullLayoutVolumePanelView.dialog;
        Window window2 = (subFullLayoutVolumePanelWindow2 == null ? null : subFullLayoutVolumePanelWindow2).getWindow();
        window2.getClass();
        window2.getDecorView().setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelView$bind$4
            @Override // android.view.View.AccessibilityDelegate
            public final boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup3, View view, AccessibilityEvent accessibilityEvent) {
                VolumePanelExpandView$adjustTouchEventForOutsideTouch$1$$ExternalSyntheticOutline0.m(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_SEND_ACCESSIBILITY_EVENT), true, subFullLayoutVolumePanelView.storeInteractor, true);
                return super.onRequestSendAccessibilityEvent(viewGroup3, view, accessibilityEvent);
            }
        });
        SubFullLayoutVolumePanelMotion subFullLayoutVolumePanelMotion = subFullLayoutVolumePanelView.volumePanelMotion;
        subFullLayoutVolumePanelMotion = subFullLayoutVolumePanelMotion == null ? null : subFullLayoutVolumePanelMotion;
        SubFullLayoutVolumePanelWindow subFullLayoutVolumePanelWindow3 = subFullLayoutVolumePanelView.dialog;
        Window window3 = (subFullLayoutVolumePanelWindow3 == null ? null : subFullLayoutVolumePanelWindow3).getWindow();
        window3.getClass();
        View decorView = window3.getDecorView();
        subFullLayoutVolumePanelMotion.getClass();
        subFullLayoutVolumePanelView.touchUpAnimation = SubFullLayoutVolumePanelMotion.getSeekBarTouchUpAnimation(decorView);
        SubFullLayoutVolumePanelMotion subFullLayoutVolumePanelMotion2 = subFullLayoutVolumePanelView.volumePanelMotion;
        subFullLayoutVolumePanelMotion2 = subFullLayoutVolumePanelMotion2 == null ? null : subFullLayoutVolumePanelMotion2;
        SubFullLayoutVolumePanelWindow subFullLayoutVolumePanelWindow4 = subFullLayoutVolumePanelView.dialog;
        Window window4 = (subFullLayoutVolumePanelWindow4 == null ? null : subFullLayoutVolumePanelWindow4).getWindow();
        window4.getClass();
        View decorView2 = window4.getDecorView();
        subFullLayoutVolumePanelMotion2.getClass();
        subFullLayoutVolumePanelView.touchDownAnimation = SubFullLayoutVolumePanelMotion.getSeekBarTouchDownAnimation(decorView2);
        SubFullLayoutVolumePanelMotion subFullLayoutVolumePanelMotion3 = subFullLayoutVolumePanelView.volumePanelMotion;
        subFullLayoutVolumePanelMotion3 = subFullLayoutVolumePanelMotion3 == null ? null : subFullLayoutVolumePanelMotion3;
        SubFullLayoutVolumePanelWindow subFullLayoutVolumePanelWindow5 = subFullLayoutVolumePanelView.dialog;
        Window window5 = (subFullLayoutVolumePanelWindow5 == null ? null : subFullLayoutVolumePanelWindow5).getWindow();
        window5.getClass();
        final View decorView3 = window5.getDecorView();
        subFullLayoutVolumePanelMotion3.getClass();
        DynamicAnimation.AnonymousClass4 anonymousClass4 = DynamicAnimation.SCALE_X;
        SpringAnimation springAnimation = new SpringAnimation(decorView3, anonymousClass4);
        springAnimation.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelMotion$getSeekBarKeyDownAnimation$1$1
            @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationUpdateListener
            public final void onAnimationUpdate(DynamicAnimation dynamicAnimation, float f, float f2) {
                decorView3.setScaleY(f);
            }
        });
        SpringForce springForce = new SpringForce();
        springForce.setStiffness(500.0f);
        springForce.setDampingRatio(1.0f);
        springAnimation.mSpring = springForce;
        subFullLayoutVolumePanelView.keyDownAnimation = springAnimation;
        SubFullLayoutVolumePanelMotion subFullLayoutVolumePanelMotion4 = subFullLayoutVolumePanelView.volumePanelMotion;
        subFullLayoutVolumePanelMotion4 = subFullLayoutVolumePanelMotion4 == null ? null : subFullLayoutVolumePanelMotion4;
        SubFullLayoutVolumePanelWindow subFullLayoutVolumePanelWindow6 = subFullLayoutVolumePanelView.dialog;
        Window window6 = (subFullLayoutVolumePanelWindow6 != null ? subFullLayoutVolumePanelWindow6 : null).getWindow();
        window6.getClass();
        final View decorView4 = window6.getDecorView();
        subFullLayoutVolumePanelMotion4.getClass();
        SpringAnimation springAnimation2 = new SpringAnimation(decorView4, anonymousClass4);
        springAnimation2.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelMotion$getSeekBarKeyUpAnimation$1$1
            @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationUpdateListener
            public final void onAnimationUpdate(DynamicAnimation dynamicAnimation, float f, float f2) {
                decorView4.setScaleY(f);
            }
        });
        SpringForce springForce2 = new SpringForce();
        springForce2.setStiffness(450.0f);
        springForce2.setDampingRatio(1.0f);
        springAnimation2.mSpring = springForce2;
        subFullLayoutVolumePanelView.keyUpAnimation = springAnimation2;
        subFullLayoutVolumePanelView.swipeDistance = ContextUtils.getDimenFloat(com.android.systemui.R.dimen.sub_full_volume_panel_swipe_distance, subFullLayoutVolumePanelView.getContext());
        setCanceledOnTouchOutside(true);
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        this.panelView.dispatchTouchEvent(motionEvent);
        return true;
    }

    public final void observeStore() {
        ((StoreInteractor) this.storeInteractor$delegate.getValue()).observeStore();
        SubFullLayoutVolumePanelView subFullLayoutVolumePanelView = this.panelView;
        subFullLayoutVolumePanelView.storeInteractor.observeStore();
        SubFullLayoutVolumePanelMotion subFullLayoutVolumePanelMotion = subFullLayoutVolumePanelView.volumePanelMotion;
        if (subFullLayoutVolumePanelMotion == null) {
            subFullLayoutVolumePanelMotion = null;
        }
        VolumePanelStore volumePanelStore = subFullLayoutVolumePanelView.store;
        VolumePanelStore volumePanelStore2 = volumePanelStore != null ? volumePanelStore : null;
        Context context = subFullLayoutVolumePanelView.getContext();
        subFullLayoutVolumePanelMotion.storeInteractor.store = volumePanelStore2;
        subFullLayoutVolumePanelMotion.context = context;
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeObserver
    public final void onChanged(Object obj) {
        VolumePanelState volumePanelState = (VolumePanelState) obj;
        switch (WhenMappings.$EnumSwitchMapping$0[volumePanelState.getStateType().ordinal()]) {
            case 1:
                Window window = getWindow();
                if (window != null) {
                    WindowManager.LayoutParams attributes = window.getAttributes();
                    if (this.infraMediator.isSupportTvVolumeSync()) {
                        attributes.semAddExtensionFlags(Integer.MIN_VALUE);
                    } else {
                        attributes.semClearExtensionFlags(Integer.MIN_VALUE);
                    }
                    window.setAttributes(attributes);
                    Unit unit = Unit.INSTANCE;
                }
                final SubFullLayoutVolumePanelView subFullLayoutVolumePanelView = this.panelView;
                VolumePanelState volumePanelState2 = ((VolumePanelStore) this.store$delegate.getValue()).currentState;
                subFullLayoutVolumePanelView.getClass();
                subFullLayoutVolumePanelView.isLockscreen = volumePanelState2.isLockscreen();
                subFullLayoutVolumePanelView.isDualViewEnabled = VolumePanelStateExt.isDualViewEnabled(volumePanelState2);
                if (BasicRune.VOLUME_REFRESH_RATE_FIXED) {
                    HandlerWrapper handlerWrapper = subFullLayoutVolumePanelView.handlerWrapper;
                    if (handlerWrapper == null) {
                        handlerWrapper = null;
                    }
                    IDisplayManagerWrapper iDisplayManagerWrapper = subFullLayoutVolumePanelView.iDisplayManagerWrapper;
                    if (iDisplayManagerWrapper == null) {
                        iDisplayManagerWrapper = null;
                    }
                    handlerWrapper.post(iDisplayManagerWrapper.refreshRateLimitOnRunnable);
                }
                if (VolumePanelStateExt.isAODVolumePanel(volumePanelState2)) {
                    ViewGroup viewGroup = subFullLayoutVolumePanelView.volumePanelView;
                    if (viewGroup == null) {
                        viewGroup = null;
                    }
                    subFullLayoutVolumePanelView.rowContainer = (ViewGroup) viewGroup.requireViewById(com.android.systemui.R.id.volume_panel_row_container);
                    ViewGroup viewGroup2 = subFullLayoutVolumePanelView.volumePanelView;
                    if (viewGroup2 == null) {
                        viewGroup2 = null;
                    }
                    subFullLayoutVolumePanelView.expandButton = (ImageView) viewGroup2.requireViewById(com.android.systemui.R.id.volume_panel_expand_button);
                } else if (subFullLayoutVolumePanelView.isDualViewEnabled) {
                    ViewGroup viewGroup3 = subFullLayoutVolumePanelView.volumePanelDualView;
                    if (viewGroup3 == null) {
                        viewGroup3 = null;
                    }
                    subFullLayoutVolumePanelView.rowContainer = (ViewGroup) viewGroup3.requireViewById(com.android.systemui.R.id.volume_panel_row_container);
                    ViewGroup viewGroup4 = subFullLayoutVolumePanelView.volumePanelDualView;
                    if (viewGroup4 == null) {
                        viewGroup4 = null;
                    }
                    subFullLayoutVolumePanelView.expandButton = (ImageView) viewGroup4.requireViewById(com.android.systemui.R.id.volume_panel_expand_button);
                    ViewGroup viewGroup5 = subFullLayoutVolumePanelView.volumePanelDualView;
                    if (viewGroup5 == null) {
                        viewGroup5 = null;
                    }
                    subFullLayoutVolumePanelView.dualViewTitle = (TextView) viewGroup5.requireViewById(com.android.systemui.R.id.volume_panel_dual_view_title);
                } else {
                    ViewGroup viewGroup6 = subFullLayoutVolumePanelView.volumePanelView;
                    if (viewGroup6 == null) {
                        viewGroup6 = null;
                    }
                    subFullLayoutVolumePanelView.rowContainer = (ViewGroup) viewGroup6.requireViewById(com.android.systemui.R.id.volume_panel_row_container);
                    ViewGroup viewGroup7 = subFullLayoutVolumePanelView.volumePanelView;
                    if (viewGroup7 == null) {
                        viewGroup7 = null;
                    }
                    subFullLayoutVolumePanelView.expandButton = (ImageView) viewGroup7.requireViewById(com.android.systemui.R.id.volume_panel_expand_button);
                }
                if (VolumePanelStateExt.isAODVolumePanel(volumePanelState2)) {
                    ViewGroup viewGroup8 = subFullLayoutVolumePanelView.volumeAODPanelView;
                    if (viewGroup8 == null) {
                        viewGroup8 = null;
                    }
                    ViewGroup.LayoutParams layoutParams = viewGroup8.getLayoutParams();
                    layoutParams.width = ContextUtils.getDisplayWidth(subFullLayoutVolumePanelView.getContext());
                    layoutParams.height = ContextUtils.getDisplayHeight(subFullLayoutVolumePanelView.getContext());
                    ViewGroup viewGroup9 = subFullLayoutVolumePanelView.volumeAODPanelView;
                    if (viewGroup9 == null) {
                        viewGroup9 = null;
                    }
                    viewGroup9.setLayoutParams(layoutParams);
                    ViewGroup viewGroup10 = subFullLayoutVolumePanelView.volumePanelView;
                    if (viewGroup10 == null) {
                        viewGroup10 = null;
                    }
                    SubFullLayoutVolumePanelWindow subFullLayoutVolumePanelWindow = subFullLayoutVolumePanelView.dialog;
                    if (subFullLayoutVolumePanelWindow == null) {
                        subFullLayoutVolumePanelWindow = null;
                    }
                    Window window2 = subFullLayoutVolumePanelWindow.getWindow();
                    window2.getClass();
                    viewGroup10.setPadding(0, window2.getAttributes().y, 0, 0);
                } else {
                    ViewGroup viewGroup11 = subFullLayoutVolumePanelView.volumePanelView;
                    if (viewGroup11 == null) {
                        viewGroup11 = null;
                    }
                    viewGroup11.setPadding(0, 0, 0, 0);
                }
                subFullLayoutVolumePanelView.initViewVisibility$1(volumePanelState2);
                if (VolumePanelStateExt.isAODVolumePanel(volumePanelState2)) {
                    VolumePanelRow volumePanelRowFindRow = VolumePanelStateExt.INSTANCE.findRow(volumePanelState2, volumePanelState2.getActiveStream());
                    if (volumePanelRowFindRow != null) {
                        subFullLayoutVolumePanelView.currentVolume = volumePanelRowFindRow.getRealLevel();
                    }
                    VibratorWrapper vibratorWrapper = subFullLayoutVolumePanelView.vibratorWrapper;
                    if (vibratorWrapper == null) {
                        vibratorWrapper = null;
                    }
                    vibratorWrapper.vibrate();
                }
                if (subFullLayoutVolumePanelView.isDualViewEnabled) {
                    TextView textView = subFullLayoutVolumePanelView.dualViewTitle;
                    if (textView == null) {
                        textView = null;
                    }
                    textView.setText(subFullLayoutVolumePanelView.getContext().getString(com.android.systemui.R.string.volume_panel_view_title));
                    ViewGroup viewGroup12 = subFullLayoutVolumePanelView.volumePanelDualView;
                    if (viewGroup12 == null) {
                        viewGroup12 = null;
                    }
                    ((ViewGroup) viewGroup12.requireViewById(com.android.systemui.R.id.volume_panel_dual_view_background)).setBackground((((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isReduceTransparencyEnabled() || !(BasicRune.VOLUME_PARTIAL_BLUR || BasicRune.VOLUME_CAPTURED_BLUR)) ? subFullLayoutVolumePanelView.getContext().getDrawable(com.android.systemui.R.drawable.volume_panel_expand_bg) : subFullLayoutVolumePanelView.getContext().getDrawable(com.android.systemui.R.drawable.sub_full_volume_panel_expand_bg_blur));
                }
                ImageView imageView = subFullLayoutVolumePanelView.expandButton;
                if (imageView == null) {
                    imageView = null;
                }
                imageView.setContentDescription(subFullLayoutVolumePanelView.getContext().getString(com.android.systemui.R.string.sec_qs_media_player_expand_content_description));
                ImageView imageView2 = subFullLayoutVolumePanelView.expandButton;
                if (imageView2 == null) {
                    imageView2 = null;
                }
                imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelView$initExpandButton$1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        VolumePanelExpandView$adjustTouchEventForOutsideTouch$1$$ExternalSyntheticOutline0.m(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_EXPAND_BUTTON_CLICKED), true, subFullLayoutVolumePanelView.storeInteractor, false);
                    }
                });
                ImageView imageView3 = subFullLayoutVolumePanelView.expandButton;
                if (imageView3 == null) {
                    imageView3 = null;
                }
                imageView3.setClickable(volumePanelState2.isShowA11yStream());
                subFullLayoutVolumePanelView.addVolumeRows$1(volumePanelState2);
                SubFullLayoutVolumePanelWindow subFullLayoutVolumePanelWindow2 = subFullLayoutVolumePanelView.dialog;
                if (subFullLayoutVolumePanelWindow2 == null) {
                    subFullLayoutVolumePanelWindow2 = null;
                }
                Window window3 = subFullLayoutVolumePanelWindow2.getWindow();
                window3.getClass();
                window3.getDecorView().setAlpha(0.0f);
                SubFullLayoutVolumePanelWindow subFullLayoutVolumePanelWindow3 = subFullLayoutVolumePanelView.dialog;
                (subFullLayoutVolumePanelWindow3 != null ? subFullLayoutVolumePanelWindow3 : null).show();
                break;
            case 2:
            case 3:
            case 4:
            case 5:
                if (volumePanelState.isShowingSubDisplayVolumePanel() && isShowing()) {
                    this.panelView.startDismissAnimation();
                    break;
                }
                break;
            case 6:
            case 7:
            case 8:
                if (isShowing()) {
                    dismiss();
                    break;
                }
                break;
            case 9:
                this.panelView.startDismissAnimation();
                SubFullLayoutVolumePanelExpandWindow subFullLayoutVolumePanelExpandWindow = new SubFullLayoutVolumePanelExpandWindow(this.volDeps);
                Window window4 = subFullLayoutVolumePanelExpandWindow.getWindow();
                if (window4 != null) {
                    WindowManager.LayoutParams attributes2 = window4.getAttributes();
                    attributes2.setFitInsetsTypes(WindowInsets.Type.navigationBars());
                    window4.setAttributes(attributes2);
                    Unit unit2 = Unit.INSTANCE;
                }
                final SubFullLayoutVolumePanelExpandView subFullLayoutVolumePanelExpandView = subFullLayoutVolumePanelExpandWindow.panelView;
                final VolumePanelState volumePanelState3 = ((VolumePanelStore) subFullLayoutVolumePanelExpandWindow.store$delegate.getValue()).currentState;
                subFullLayoutVolumePanelExpandView.rowContainer = (ViewGroup) ((ViewGroup) subFullLayoutVolumePanelExpandView.requireViewById(com.android.systemui.R.id.volume_panel_expand_view)).requireViewById(com.android.systemui.R.id.volume_row_container);
                subFullLayoutVolumePanelExpandView.addRows$1(volumePanelState3);
                Space space = (Space) subFullLayoutVolumePanelExpandView.requireViewById(com.android.systemui.R.id.volume_panel_expand_bottom_space);
                ViewGroup viewGroup13 = (ViewGroup) subFullLayoutVolumePanelExpandView.requireViewById(com.android.systemui.R.id.volume_panel_status_message_layout);
                TextView textView2 = (TextView) subFullLayoutVolumePanelExpandView.requireViewById(com.android.systemui.R.id.volume_panel_status_message_description);
                ImageView imageView4 = (ImageView) subFullLayoutVolumePanelExpandView.requireViewById(com.android.systemui.R.id.volume_panel_status_message_icon);
                imageView4.setImageTintList(ColorUtils.getSingleColorStateList(com.android.systemui.R.color.volume_panel_status_message_color, subFullLayoutVolumePanelExpandView.getContext()));
                boolean z = volumePanelState3.isAllSoundOff() || volumePanelState3.isZenMode() || volumePanelState3.isLeBroadcasting();
                ViewVisibilityUtil.INSTANCE.getClass();
                if (z) {
                    ViewVisibilityUtil.setGone(space);
                } else {
                    space.setVisibility(0);
                }
                ViewVisibilityUtil.INSTANCE.getClass();
                if (z) {
                    viewGroup13.setVisibility(0);
                } else {
                    ViewVisibilityUtil.setGone(viewGroup13);
                }
                if (z) {
                    textView2.setText(subFullLayoutVolumePanelExpandView.getContext().getString(volumePanelState3.isAllSoundOff() ? com.android.systemui.R.string.volume_mute_all_sounds_on : volumePanelState3.isZenMode() ? com.android.systemui.R.string.volume_zen_mode_on : com.android.systemui.R.string.volume_panel_broadcasting_sound_using_auracast));
                    if (volumePanelState3.isAllSoundOff() || volumePanelState3.isZenMode()) {
                        imageView4.setImageDrawable(subFullLayoutVolumePanelExpandView.getContext().getResources().getDrawable(com.android.systemui.R.drawable.ic_volume_control_dnd, null));
                        viewGroup13.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelExpandView$updateStatusMsgArea$1
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                VolumePanelExpandView$adjustTouchEventForOutsideTouch$1$$ExternalSyntheticOutline0.m(new VolumePanelAction.Builder(volumePanelState3.isAllSoundOff() ? VolumePanelAction.ActionType.ACTION_STATUS_MESSAGE_CLICKED : VolumePanelAction.ActionType.ACTION_STATUS_DO_NOT_DISTURB_MESSAGE_CLICKED), true, subFullLayoutVolumePanelExpandView.storeInteractor, false);
                            }
                        });
                    } else if (volumePanelState3.isLeBroadcasting()) {
                        imageView4.setImageDrawable(subFullLayoutVolumePanelExpandView.getContext().getResources().getDrawable(com.android.systemui.R.drawable.ic_auracast, null));
                        viewGroup13.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelExpandView$updateStatusMsgArea$2
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                VolumePanelExpandView$adjustTouchEventForOutsideTouch$1$$ExternalSyntheticOutline0.m(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_STATUS_LE_BROADCASTING_MESSAGE_CLICKED), true, subFullLayoutVolumePanelExpandView.storeInteractor, false);
                            }
                        });
                    }
                }
                int dimenInt = ContextUtils.getDimenInt(com.android.systemui.R.dimen.sub_full_volume_panel_expand_width, subFullLayoutVolumePanelExpandView.getContext());
                int dimenInt2 = ContextUtils.getDimenInt(com.android.systemui.R.dimen.sub_full_volume_panel_expand_height, subFullLayoutVolumePanelExpandView.getContext());
                int dimenInt3 = ContextUtils.getDimenInt(com.android.systemui.R.dimen.sub_full_volume_panel_expand_height_with_msg, subFullLayoutVolumePanelExpandView.getContext());
                boolean z2 = volumePanelState3.isZenMode() || volumePanelState3.isAllSoundOff();
                ViewGroup viewGroup14 = (ViewGroup) subFullLayoutVolumePanelExpandView.requireViewById(com.android.systemui.R.id.volume_panel_expand_view_background);
                ViewGroup.LayoutParams layoutParams2 = viewGroup14.getLayoutParams();
                layoutParams2.width = dimenInt;
                if (z2) {
                    dimenInt2 = dimenInt3;
                }
                layoutParams2.height = dimenInt2;
                viewGroup14.setLayoutParams(layoutParams2);
                if (((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isReduceTransparencyEnabled()) {
                    viewGroup14.setBackground(subFullLayoutVolumePanelExpandView.getContext().getDrawable(com.android.systemui.R.drawable.volume_panel_expand_bg_reduce_transparency));
                } else if (BasicRune.VOLUME_PARTIAL_BLUR || BasicRune.VOLUME_CAPTURED_BLUR) {
                    viewGroup14.setBackground(subFullLayoutVolumePanelExpandView.getContext().getDrawable(com.android.systemui.R.drawable.sub_full_volume_panel_expand_bg_blur));
                } else {
                    viewGroup14.setBackground(subFullLayoutVolumePanelExpandView.getContext().getDrawable(com.android.systemui.R.drawable.volume_panel_expand_bg));
                }
                subFullLayoutVolumePanelExpandView.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelExpandView$adjustTouchEventForOutsideTouch$1
                    @Override // android.view.View.OnTouchListener
                    public final boolean onTouch(View view, MotionEvent motionEvent) {
                        VolumePanelExpandView$adjustTouchEventForOutsideTouch$1$$ExternalSyntheticOutline0.m(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_TOUCH_OUTSIDE), true, subFullLayoutVolumePanelExpandView.storeInteractor, true);
                        return true;
                    }
                });
                ViewGroup viewGroup15 = subFullLayoutVolumePanelExpandView.contentsView;
                if (viewGroup15 == null) {
                    viewGroup15 = null;
                }
                viewGroup15.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelExpandView$adjustTouchEventForOutsideTouch$2
                    @Override // android.view.View.OnTouchListener
                    public final boolean onTouch(View view, MotionEvent motionEvent) {
                        return true;
                    }
                });
                SubFullLayoutVolumePanelExpandWindow subFullLayoutVolumePanelExpandWindow2 = subFullLayoutVolumePanelExpandView.dialog;
                if (subFullLayoutVolumePanelExpandWindow2 == null) {
                    subFullLayoutVolumePanelExpandWindow2 = null;
                }
                Window window5 = subFullLayoutVolumePanelExpandWindow2.getWindow();
                window5.getClass();
                window5.getDecorView().setAlpha(0.0f);
                SubFullLayoutVolumePanelExpandWindow subFullLayoutVolumePanelExpandWindow3 = subFullLayoutVolumePanelExpandView.dialog;
                if (subFullLayoutVolumePanelExpandWindow3 == null) {
                    subFullLayoutVolumePanelExpandWindow3 = null;
                }
                Window window6 = subFullLayoutVolumePanelExpandWindow3.getWindow();
                window6.getClass();
                window6.getDecorView().setScaleX(0.95f);
                SubFullLayoutVolumePanelExpandWindow subFullLayoutVolumePanelExpandWindow4 = subFullLayoutVolumePanelExpandView.dialog;
                if (subFullLayoutVolumePanelExpandWindow4 == null) {
                    subFullLayoutVolumePanelExpandWindow4 = null;
                }
                Window window7 = subFullLayoutVolumePanelExpandWindow4.getWindow();
                window7.getClass();
                window7.getDecorView().setScaleY(0.95f);
                ViewVisibilityUtil viewVisibilityUtil = ViewVisibilityUtil.INSTANCE;
                ImageButton imageButton = subFullLayoutVolumePanelExpandView.liveCaptionButton;
                if (imageButton == null) {
                    imageButton = null;
                }
                viewVisibilityUtil.getClass();
                ViewVisibilityUtil.setGone(imageButton);
                VolumePanelRow volumePanelRowFindRow2 = VolumePanelStateExt.INSTANCE.findRow(volumePanelState3, volumePanelState3.getActiveStream());
                if (volumePanelRowFindRow2 != null) {
                    subFullLayoutVolumePanelExpandView.updateVolumeTitle$1(volumePanelRowFindRow2.getStreamType());
                }
                if (volumePanelState3.isShowA11yStream()) {
                    SubFullLayoutVolumePanelExpandWindow subFullLayoutVolumePanelExpandWindow5 = subFullLayoutVolumePanelExpandView.dialog;
                    if (subFullLayoutVolumePanelExpandWindow5 == null) {
                        subFullLayoutVolumePanelExpandWindow5 = null;
                    }
                    Window window8 = subFullLayoutVolumePanelExpandWindow5.getWindow();
                    window8.getClass();
                    window8.clearFlags(8);
                }
                SubFullLayoutVolumePanelExpandWindow subFullLayoutVolumePanelExpandWindow6 = subFullLayoutVolumePanelExpandView.dialog;
                (subFullLayoutVolumePanelExpandWindow6 != null ? subFullLayoutVolumePanelExpandWindow6 : null).show();
                break;
        }
    }

    @Override // android.app.Dialog
    public final void onStart() {
        super.onStart();
        this.log.d("SubFullLayoutVolumePanelWindow", "onStart");
    }

    @Override // android.app.Dialog
    public final void onStop() {
        super.onStop();
        this.log.d("SubFullLayoutVolumePanelWindow", "onStop : panelState.isExpanded=" + ((VolumePanelStore) this.store$delegate.getValue()).currentState.isExpanded());
        SubFullLayoutVolumePanelView subFullLayoutVolumePanelView = this.panelView;
        subFullLayoutVolumePanelView.getClass();
        if (BasicRune.VOLUME_REFRESH_RATE_FIXED) {
            HandlerWrapper handlerWrapper = subFullLayoutVolumePanelView.handlerWrapper;
            if (handlerWrapper == null) {
                handlerWrapper = null;
            }
            IDisplayManagerWrapper iDisplayManagerWrapper = subFullLayoutVolumePanelView.iDisplayManagerWrapper;
            if (iDisplayManagerWrapper == null) {
                iDisplayManagerWrapper = null;
            }
            handlerWrapper.post(iDisplayManagerWrapper.refreshRateLimitOffRunnable);
        }
        VolumePanelState volumePanelState = subFullLayoutVolumePanelView.panelState;
        if (volumePanelState == null) {
            volumePanelState = null;
        }
        if (VolumePanelStateExt.isAODVolumePanel(volumePanelState)) {
            PowerManagerWrapper powerManagerWrapper = subFullLayoutVolumePanelView.powerManagerWrapper;
            if (powerManagerWrapper == null) {
                powerManagerWrapper = null;
            }
            PowerManager.WakeLock wakeLock = powerManagerWrapper.wakeLock;
            if (wakeLock != null) {
                wakeLock.release();
            }
            powerManagerWrapper.wakeLock = null;
            PluginAODManagerWrapper pluginAODManagerWrapper = subFullLayoutVolumePanelView.pluginAODManagerWrapper;
            if (pluginAODManagerWrapper == null) {
                pluginAODManagerWrapper = null;
            }
            pluginAODManagerWrapper.getClass();
            PluginAODManagerWrapper.requestAODVolumePanel(false);
        }
        ViewGroup viewGroup = subFullLayoutVolumePanelView.rowContainer;
        (viewGroup != null ? viewGroup : null).removeAllViews();
        if (((VolumePanelStore) this.store$delegate.getValue()).currentState.isExpanded()) {
            return;
        }
        ((StoreInteractor) this.storeInteractor$delegate.getValue()).sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_DISMISS_SUB_DISPLAY_VOLUME_PANEL).build(), true);
    }
}
