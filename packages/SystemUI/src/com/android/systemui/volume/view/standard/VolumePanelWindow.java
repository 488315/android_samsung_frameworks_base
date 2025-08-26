package com.android.systemui.volume.view.standard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.PowerManager;
import android.util.Log;
import android.util.Pair;
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
import com.android.keyguard.ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.volume.VolumeDependency;
import com.android.systemui.volume.VolumeDependencyBase;
import com.android.systemui.volume.config.SystemConfigImpl;
import com.android.systemui.volume.config.VolumeConfigs;
import com.android.systemui.volume.purefunction.VolumePanelLayout;
import com.android.systemui.volume.store.StoreInteractor;
import com.android.systemui.volume.store.VolumePanelStore;
import com.android.systemui.volume.util.BlurEffect;
import com.android.systemui.volume.util.ColorUtils;
import com.android.systemui.volume.util.ContextUtils;
import com.android.systemui.volume.util.HandlerWrapper;
import com.android.systemui.volume.util.IDisplayManagerWrapper;
import com.android.systemui.volume.util.PluginAODManagerWrapper;
import com.android.systemui.volume.util.PowerManagerWrapper;
import com.android.systemui.volume.util.SettingsHelperExt;
import com.android.systemui.volume.util.SystemServiceExtension;
import com.android.systemui.volume.util.VibratorWrapper;
import com.android.systemui.volume.util.ViewLocationUtil;
import com.android.systemui.volume.util.ViewVisibilityUtil;
import com.android.systemui.volume.view.VolumePanelMotion;
import com.android.systemui.volume.view.expand.VolumePanelExpandView;
import com.android.systemui.volume.view.expand.VolumePanelExpandView$adjustTouchEventForOutsideTouch$1$$ExternalSyntheticOutline0;
import com.android.systemui.volume.view.expand.VolumePanelExpandWindow;
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
public final class VolumePanelWindow extends Dialog implements VolumeObserver {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final VolumeInfraMediator infraMediator;
    public final LogWrapper log;
    public final VolumePanelView panelView;
    public final Lazy store$delegate;
    public final Lazy storeInteractor$delegate;
    public final SystemConfigImpl systemConfig;
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
                iArr[VolumePanelState.StateType.STATE_SHOW.ordinal()] = 1;
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
    public VolumePanelWindow(VolumeDependencyBase volumeDependencyBase) {
        VolumeDependency volumeDependency = (VolumeDependency) volumeDependencyBase;
        super((Context) volumeDependency.get(Context.class));
        this.volDeps = volumeDependency;
        final int i = 0;
        this.store$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.volume.view.standard.VolumePanelWindow$$ExternalSyntheticLambda0
            public final /* synthetic */ VolumePanelWindow f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                VolumePanelWindow volumePanelWindow = this.f$0;
                switch (i) {
                    case 0:
                        return (VolumePanelStore) volumePanelWindow.volDeps.get(VolumePanelStore.class);
                    default:
                        int i2 = VolumePanelWindow.$r8$clinit;
                        return new StoreInteractor(volumePanelWindow, volumePanelWindow.getStore$2());
                }
            }
        });
        final int i2 = 1;
        this.storeInteractor$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.volume.view.standard.VolumePanelWindow$$ExternalSyntheticLambda0
            public final /* synthetic */ VolumePanelWindow f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                VolumePanelWindow volumePanelWindow = this.f$0;
                switch (i2) {
                    case 0:
                        return (VolumePanelStore) volumePanelWindow.volDeps.get(VolumePanelStore.class);
                    default:
                        int i22 = VolumePanelWindow.$r8$clinit;
                        return new StoreInteractor(volumePanelWindow, volumePanelWindow.getStore$2());
                }
            }
        });
        SystemConfigImpl systemConfigImpl = (SystemConfigImpl) ((VolumeConfigs) volumeDependency.get(VolumeConfigs.class)).systemConfig$delegate.getValue();
        this.systemConfig = systemConfigImpl;
        this.log = (LogWrapper) volumeDependency.get(LogWrapper.class);
        this.infraMediator = (VolumeInfraMediator) volumeDependency.get(VolumeInfraMediator.class);
        Window window = getWindow();
        if (window != null) {
            window.requestFeature(1);
            window.setBackgroundDrawable(new ColorDrawable(0));
            window.clearFlags(2);
            window.addFlags(17563944);
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.type = 2020;
            attributes.format = -3;
            attributes.setTitle("VolumePanelWindow");
            attributes.windowAnimations = -1;
            attributes.accessibilityTitle = window.getContext().getString(R.string.volume_panel_view_title);
            if (((Boolean) systemConfigImpl.hasCutout$delegate.getValue()).booleanValue()) {
                attributes.flags |= 67109888;
                attributes.layoutInDisplayCutoutMode = 0;
            }
            window.setAttributes(attributes);
        }
        setContentView(R.layout.volume_panel_view);
        final VolumePanelView volumePanelView = (VolumePanelView) requireViewById(R.id.volume_panel_view_root);
        this.panelView = volumePanelView;
        volumePanelView.getClass();
        Log.d("VolumePanelView", "VolumePanelView: bind");
        volumePanelView.dialog = this;
        volumePanelView.handlerWrapper = (HandlerWrapper) volumeDependency.get(HandlerWrapper.class);
        VolumePanelStore volumePanelStore = (VolumePanelStore) volumeDependency.get(VolumePanelStore.class);
        volumePanelView.store = volumePanelStore;
        volumePanelView.storeInteractor.store = volumePanelStore == null ? null : volumePanelStore;
        volumePanelView.volDeps = volumeDependency;
        volumePanelView.volumePanelMotion = (VolumePanelMotion) volumeDependency.get(VolumePanelMotion.class);
        volumePanelView.blurEffect = new BlurEffect(volumePanelView.getContext(), volumeDependency);
        volumePanelView.iDisplayManagerWrapper = (IDisplayManagerWrapper) volumeDependency.get(IDisplayManagerWrapper.class);
        volumePanelView.vibratorWrapper = (VibratorWrapper) volumeDependency.get(VibratorWrapper.class);
        volumePanelView.powerManagerWrapper = (PowerManagerWrapper) volumeDependency.get(PowerManagerWrapper.class);
        volumePanelView.pluginAODManagerWrapper = (PluginAODManagerWrapper) volumeDependency.get(PluginAODManagerWrapper.class);
        ViewGroup viewGroup = volumePanelView.volumePanelDualView;
        (viewGroup == null ? null : viewGroup).setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.volume.view.standard.VolumePanelView$bind$1
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                VolumePanelExpandView$adjustTouchEventForOutsideTouch$1$$ExternalSyntheticOutline0.m(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_TOUCH_OUTSIDE), true, volumePanelView.storeInteractor, false);
                return true;
            }
        });
        ViewGroup viewGroup2 = volumePanelView.volumePanelDualView;
        ((ViewGroup) (viewGroup2 == null ? null : viewGroup2).requireViewById(R.id.volume_panel_dual_view_contents)).setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.volume.view.standard.VolumePanelView$bind$2
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        VolumePanelWindow volumePanelWindow = volumePanelView.dialog;
        (volumePanelWindow == null ? null : volumePanelWindow).setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.android.systemui.volume.view.standard.VolumePanelView$bind$3
            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r10v7, types: [com.android.systemui.volume.util.PluginAODManagerWrapper] */
            @Override // android.content.DialogInterface.OnShowListener
            public final void onShow(DialogInterface dialogInterface) {
                Runnable runnable = new Runnable() { // from class: com.android.systemui.volume.view.standard.VolumePanelView$bind$3$showBlurRunnable$1
                    @Override // java.lang.Runnable
                    public final void run() {
                    }
                };
                VolumePanelView volumePanelView2 = volumePanelView;
                volumePanelView2.blurView = volumePanelView2.isDualViewEnabled ? (ImageView) volumePanelView2.findViewById(R.id.volume_panel_dual_blur) : (ImageView) volumePanelView2.findViewById(R.id.volume_panel_blur);
                final VolumePanelView volumePanelView3 = volumePanelView;
                final ImageView imageView = volumePanelView3.blurView;
                if (imageView != null) {
                    runnable = new Runnable() { // from class: com.android.systemui.volume.view.standard.VolumePanelView$bind$3$1$1
                        @Override // java.lang.Runnable
                        public final void run() throws Resources.NotFoundException {
                            BlurEffect blurEffect;
                            int i3;
                            if (!BasicRune.VOLUME_PARTIAL_BLUR) {
                                if (BasicRune.VOLUME_CAPTURED_BLUR) {
                                    VolumePanelView volumePanelView4 = volumePanelView3;
                                    BlurEffect blurEffect2 = volumePanelView4.blurEffect;
                                    BlurEffect blurEffect3 = blurEffect2 == null ? null : blurEffect2;
                                    ImageView imageView2 = imageView;
                                    blurEffect = blurEffect2 != null ? blurEffect2 : null;
                                    boolean zIsNightMode = ContextUtils.isNightMode(volumePanelView4.getContext());
                                    blurEffect.getClass();
                                    i3 = zIsNightMode ? 106 : 121;
                                    final ImageView imageView3 = imageView;
                                    final VolumePanelView volumePanelView5 = volumePanelView3;
                                    blurEffect3.setCapturedBlur(imageView2, i3, new Supplier() { // from class: com.android.systemui.volume.view.standard.VolumePanelView$bind$3$1$1.1
                                        @Override // java.util.function.Supplier
                                        public final Object get() {
                                            ViewLocationUtil viewLocationUtil = ViewLocationUtil.INSTANCE;
                                            ImageView imageView4 = imageView3;
                                            viewLocationUtil.getClass();
                                            int[] iArr = new int[2];
                                            imageView4.getLocationOnScreen(iArr);
                                            VolumePanelView volumePanelView6 = volumePanelView5;
                                            if (volumePanelView6.isDualViewEnabled) {
                                                iArr[0] = iArr[0] - ((int) (imageView3.getWidth() * 0.05d));
                                                iArr[1] = iArr[1] - ((int) (imageView3.getHeight() * 0.05d));
                                                return iArr;
                                            }
                                            int i4 = iArr[0];
                                            VolumePanelWindow volumePanelWindow2 = volumePanelView6.dialog;
                                            if (volumePanelWindow2 == null) {
                                                volumePanelWindow2 = null;
                                            }
                                            Window window2 = volumePanelWindow2.getWindow();
                                            window2.getClass();
                                            iArr[0] = i4 - (window2.getDecorView().getWidth() * (BasicRune.VOLUME_LEFT_DISPLAY_VOLUME_DIALOG ? -1 : 1));
                                            return iArr;
                                        }
                                    });
                                    return;
                                }
                                return;
                            }
                            VolumePanelView volumePanelView6 = volumePanelView3;
                            if (volumePanelView6.isDualViewEnabled) {
                                BlurEffect blurEffect4 = volumePanelView6.blurEffect;
                                blurEffect = blurEffect4 != null ? blurEffect4 : null;
                                ImageView imageView4 = imageView;
                                int color = volumePanelView6.getContext().getColor(R.color.volume_expand_panel_bg_color_blur);
                                float dimension = volumePanelView3.getContext().getResources().getDimension(R.dimen.volume_panel_expand_view_radius);
                                blurEffect.getClass();
                                BlurEffect.setRealTimeBlur(imageView4, color, dimension, 107);
                                return;
                            }
                            BlurEffect blurEffect5 = volumePanelView6.blurEffect;
                            if (blurEffect5 == null) {
                                blurEffect5 = null;
                            }
                            ImageView imageView5 = imageView;
                            int color2 = volumePanelView6.getContext().getColor(R.color.volume_seekbar_background_color_blur);
                            float dimension2 = volumePanelView3.getContext().getResources().getDimension(R.dimen.volume_panel_expand_view_radius);
                            VolumePanelView volumePanelView7 = volumePanelView3;
                            BlurEffect blurEffect6 = volumePanelView7.blurEffect;
                            blurEffect = blurEffect6 != null ? blurEffect6 : null;
                            boolean zIsNightMode2 = ContextUtils.isNightMode(volumePanelView7.getContext());
                            blurEffect.getClass();
                            i3 = zIsNightMode2 ? 106 : 121;
                            blurEffect5.getClass();
                            BlurEffect.setRealTimeBlur(imageView5, color2, dimension2, i3);
                        }
                    };
                }
                VolumePanelView volumePanelView4 = volumePanelView;
                if (volumePanelView4.isDualViewEnabled) {
                    final VolumePanelMotion volumePanelMotion = volumePanelView4.volumePanelMotion;
                    if (volumePanelMotion == null) {
                        volumePanelMotion = null;
                    }
                    VolumePanelWindow volumePanelWindow2 = volumePanelView4.dialog;
                    Window window2 = (volumePanelWindow2 != null ? volumePanelWindow2 : null).getWindow();
                    window2.getClass();
                    final View decorView = window2.getDecorView();
                    volumePanelMotion.getClass();
                    decorView.setTranslationX(0.0f);
                    ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(decorView, "alpha", decorView.getAlpha(), 1.0f);
                    objectAnimatorOfFloat.setDuration(200L);
                    objectAnimatorOfFloat.setInterpolator(new LinearInterpolator());
                    ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(decorView, "scaleX", 0.9f, 1.0f);
                    objectAnimatorOfFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.volume.view.VolumePanelMotion$startVolumeDualViewShowAnimation$scaleAnimator$1$1
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
                    animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.volume.view.VolumePanelMotion$startVolumeDualViewShowAnimation$1$1
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public final void onAnimationEnd(Animator animator) {
                            volumePanelMotion.storeInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_ANIMATION_FINISHED).build(), true);
                        }

                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public final void onAnimationStart(Animator animator) {
                            volumePanelMotion.storeInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_ANIMATION_START).build(), true);
                        }
                    });
                    animatorSet.start();
                    volumePanelMotion.dualShowAnimation = animatorSet;
                    if (BasicRune.VOLUME_CAPTURED_BLUR) {
                        runnable.run();
                        return;
                    }
                    return;
                }
                VolumePanelState volumePanelState = volumePanelView4.panelState;
                if (volumePanelState == null) {
                    volumePanelState = null;
                }
                if (!VolumePanelStateExt.isAODVolumePanel(volumePanelState)) {
                    VolumePanelView volumePanelView5 = volumePanelView;
                    VolumePanelMotion volumePanelMotion2 = volumePanelView5.volumePanelMotion;
                    if (volumePanelMotion2 == null) {
                        volumePanelMotion2 = null;
                    }
                    VolumePanelWindow volumePanelWindow3 = volumePanelView5.dialog;
                    Window window3 = (volumePanelWindow3 != null ? volumePanelWindow3 : null).getWindow();
                    window3.getClass();
                    View decorView2 = window3.getDecorView();
                    volumePanelMotion2.getClass();
                    SpringAnimation springAnimation = new SpringAnimation(decorView2, DynamicAnimation.TRANSLATION_X);
                    springAnimation.mSpring = ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(150.0f, 0.7f);
                    decorView2.setAlpha(1.0f);
                    decorView2.setScaleX(1.0f);
                    decorView2.setScaleY(1.0f);
                    decorView2.setTranslationX(BasicRune.VOLUME_LEFT_DISPLAY_VOLUME_DIALOG ? -decorView2.getWidth() : decorView2.getWidth());
                    springAnimation.mVelocity = 0.0f;
                    springAnimation.animateToFinalPosition(0.0f);
                    volumePanelMotion2.singleShowSpringAnimation = springAnimation;
                    if (BasicRune.VOLUME_CAPTURED_BLUR) {
                        runnable.run();
                        return;
                    }
                    return;
                }
                VolumePanelView volumePanelView6 = volumePanelView;
                VolumePanelMotion volumePanelMotion3 = volumePanelView6.volumePanelMotion;
                if (volumePanelMotion3 == null) {
                    volumePanelMotion3 = null;
                }
                VolumePanelWindow volumePanelWindow4 = volumePanelView6.dialog;
                if (volumePanelWindow4 == null) {
                    volumePanelWindow4 = null;
                }
                Window window4 = volumePanelWindow4.getWindow();
                window4.getClass();
                View decorView3 = window4.getDecorView();
                volumePanelMotion3.getClass();
                decorView3.setTranslationX(0.0f);
                decorView3.setScaleX(1.0f);
                decorView3.setScaleY(1.0f);
                ObjectAnimator objectAnimatorOfFloat3 = ObjectAnimator.ofFloat(decorView3, "alpha", decorView3.getAlpha(), 1.0f);
                objectAnimatorOfFloat3.setDuration(100L);
                objectAnimatorOfFloat3.setInterpolator(new LinearInterpolator());
                objectAnimatorOfFloat3.start();
                VolumePanelView volumePanelView7 = volumePanelView;
                volumePanelView7.isFirstTouch = true;
                PowerManagerWrapper powerManagerWrapper = volumePanelView7.powerManagerWrapper;
                if (powerManagerWrapper == null) {
                    powerManagerWrapper = null;
                }
                Context context = volumePanelView7.getContext();
                powerManagerWrapper.getClass();
                SystemServiceExtension.INSTANCE.getClass();
                Object systemService = context.getSystemService((Class<Object>) PowerManager.class);
                systemService.getClass();
                PowerManager.WakeLock wakeLockNewWakeLock = ((PowerManager) systemService).newWakeLock(1, "AOD_VolumePanel");
                wakeLockNewWakeLock.acquire();
                powerManagerWrapper.wakeLock = wakeLockNewWakeLock;
                ?? r10 = volumePanelView.pluginAODManagerWrapper;
                (r10 != 0 ? r10 : null).getClass();
                PluginAODManagerWrapper.requestAODVolumePanel(true);
            }
        });
        VolumePanelWindow volumePanelWindow2 = volumePanelView.dialog;
        Window window2 = (volumePanelWindow2 == null ? null : volumePanelWindow2).getWindow();
        window2.getClass();
        window2.getDecorView().setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.systemui.volume.view.standard.VolumePanelView$bind$4
            @Override // android.view.View.AccessibilityDelegate
            public final boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup3, View view, AccessibilityEvent accessibilityEvent) {
                VolumePanelExpandView$adjustTouchEventForOutsideTouch$1$$ExternalSyntheticOutline0.m(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_SEND_ACCESSIBILITY_EVENT), true, volumePanelView.storeInteractor, true);
                return super.onRequestSendAccessibilityEvent(viewGroup3, view, accessibilityEvent);
            }
        });
        VolumePanelMotion volumePanelMotion = volumePanelView.volumePanelMotion;
        volumePanelMotion = volumePanelMotion == null ? null : volumePanelMotion;
        VolumePanelWindow volumePanelWindow3 = volumePanelView.dialog;
        Window window3 = (volumePanelWindow3 == null ? null : volumePanelWindow3).getWindow();
        window3.getClass();
        View decorView = window3.getDecorView();
        volumePanelMotion.getClass();
        volumePanelView.touchUpAnimation = VolumePanelMotion.getSeekBarTouchUpAnimation(decorView);
        VolumePanelMotion volumePanelMotion2 = volumePanelView.volumePanelMotion;
        volumePanelMotion2 = volumePanelMotion2 == null ? null : volumePanelMotion2;
        VolumePanelWindow volumePanelWindow4 = volumePanelView.dialog;
        Window window4 = (volumePanelWindow4 == null ? null : volumePanelWindow4).getWindow();
        window4.getClass();
        View decorView2 = window4.getDecorView();
        volumePanelMotion2.getClass();
        volumePanelView.touchDownAnimation = VolumePanelMotion.getSeekBarTouchDownAnimation(decorView2);
        VolumePanelMotion volumePanelMotion3 = volumePanelView.volumePanelMotion;
        volumePanelMotion3 = volumePanelMotion3 == null ? null : volumePanelMotion3;
        VolumePanelWindow volumePanelWindow5 = volumePanelView.dialog;
        Window window5 = (volumePanelWindow5 == null ? null : volumePanelWindow5).getWindow();
        window5.getClass();
        View decorView3 = window5.getDecorView();
        volumePanelMotion3.getClass();
        volumePanelView.keyDownAnimation = VolumePanelMotion.getSeekBarKeyDownAnimation(decorView3);
        VolumePanelMotion volumePanelMotion4 = volumePanelView.volumePanelMotion;
        volumePanelMotion4 = volumePanelMotion4 == null ? null : volumePanelMotion4;
        VolumePanelWindow volumePanelWindow6 = volumePanelView.dialog;
        Window window6 = (volumePanelWindow6 != null ? volumePanelWindow6 : null).getWindow();
        window6.getClass();
        View decorView4 = window6.getDecorView();
        volumePanelMotion4.getClass();
        volumePanelView.keyUpAnimation = VolumePanelMotion.getSeekBarKeyUpAnimation(decorView4);
        volumePanelView.swipeDistance = ContextUtils.getDimenFloat(R.dimen.volume_panel_swipe_distance, volumePanelView.getContext());
        setCanceledOnTouchOutside(true);
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        this.panelView.dispatchTouchEvent(motionEvent);
        return true;
    }

    public final void dispose() {
        ((StoreInteractor) this.storeInteractor$delegate.getValue()).dispose();
        VolumePanelView volumePanelView = this.panelView;
        volumePanelView.storeInteractor.dispose();
        VolumePanelMotion volumePanelMotion = volumePanelView.volumePanelMotion;
        if (volumePanelMotion == null) {
            volumePanelMotion = null;
        }
        volumePanelMotion.storeInteractor.dispose();
        SpringAnimation springAnimation = volumePanelMotion.singleShowSpringAnimation;
        if (springAnimation != null) {
            springAnimation.cancel();
        }
        volumePanelMotion.singleShowSpringAnimation = null;
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x003d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int getBaseHeight$1() {
        int displayHeight = ContextUtils.getDisplayHeight(getContext());
        if (!this.systemConfig.isTablet() && !ContextUtils.isScreenWideMobileDevice(getContext())) {
            if (ContextUtils.isLandscape(getContext())) {
                SettingsHelper settingsHelper = (SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class);
                int i = SettingsHelperExt.$r8$clinit;
                if (!settingsHelper.isNavigationBarGestureHintEnabled() || !settingsHelper.isNavigationBarGestureWhileHidden()) {
                    if (ContextUtils.isLandscape(getContext())) {
                        return displayHeight;
                    }
                }
            }
        }
        return ContextUtils.getDimenInt(android.R.dimen.select_dialog_drawable_padding_start_material, getContext()) + displayHeight;
    }

    public final float getSeekBarY(int i) {
        float f;
        float dimenInt = i - ContextUtils.getDimenInt(R.dimen.volume_seekbar_height, getContext());
        if (!this.systemConfig.isTablet() && ContextUtils.isScreenWideMobileDevice(getContext()) && ContextUtils.isLandscape(getContext())) {
            return (dimenInt - (i / 2.0f)) / 2.0f;
        }
        if (this.systemConfig.isTablet() || ContextUtils.isLandscape(getContext())) {
            return dimenInt / 2.0f;
        }
        if (BasicRune.FOLDABLE_TYPE_FLIP) {
            VolumePanelLayout.INSTANCE.getClass();
            f = VolumePanelLayout.VERTICAL_PADDING_TOP_FOR_FLIP_RATIO;
        } else if (ContextUtils.isScreenWideMobileDevice(getContext())) {
            VolumePanelLayout.INSTANCE.getClass();
            f = VolumePanelLayout.VERTICAL_WIDE_SCREEN_TOP_RATIO;
        } else {
            VolumePanelLayout.INSTANCE.getClass();
            f = VolumePanelLayout.VERTICAL_PADDING_TOP_RATIO;
        }
        return dimenInt * f;
    }

    public final VolumePanelStore getStore$2() {
        return (VolumePanelStore) this.store$delegate.getValue();
    }

    public final void observeStore() {
        ((StoreInteractor) this.storeInteractor$delegate.getValue()).observeStore();
        VolumePanelView volumePanelView = this.panelView;
        volumePanelView.storeInteractor.observeStore();
        VolumePanelMotion volumePanelMotion = volumePanelView.volumePanelMotion;
        if (volumePanelMotion == null) {
            volumePanelMotion = null;
        }
        VolumePanelStore volumePanelStore = volumePanelView.store;
        VolumePanelStore volumePanelStore2 = volumePanelStore != null ? volumePanelStore : null;
        Context context = volumePanelView.getContext();
        volumePanelMotion.storeInteractor.store = volumePanelStore2;
        volumePanelMotion.context = context;
    }

    /* JADX WARN: Removed duplicated region for block: B:121:0x02c5  */
    @Override // com.samsung.systemui.splugins.volume.VolumeObserver
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onChanged(Object obj) {
        float f;
        float f2;
        boolean z;
        Pair pair;
        boolean z2;
        View decorView;
        int i;
        float f3;
        float f4;
        int paddingTop;
        VolumePanelState volumePanelState = (VolumePanelState) obj;
        switch (WhenMappings.$EnumSwitchMapping$0[volumePanelState.getStateType().ordinal()]) {
            case 1:
                Window window = getWindow();
                if (window != null) {
                    WindowManager.LayoutParams attributes = window.getAttributes();
                    if (VolumePanelStateExt.isAODVolumePanel(getStore$2().currentState)) {
                        attributes.type = 2021;
                        attributes.semAddExtensionFlags(262144);
                        attributes.y = 0;
                    } else {
                        attributes.x = 0;
                        attributes.y = getStore$2().currentState.isDualAudio() ? (int) ((getSeekBarY(getBaseHeight$1()) - ContextUtils.getDimenInt(R.dimen.volume_panel_dual_view_elevation_padding_vertical, getContext())) - ContextUtils.getDimenInt(R.dimen.volume_panel_dual_view_top_padding, getContext())) : (int) (getSeekBarY(getBaseHeight$1()) - ContextUtils.getDimenInt(R.dimen.volume_seekbar_elevation_padding, getContext()));
                        attributes.setFitInsetsTypes(WindowInsets.Type.navigationBars());
                        attributes.type = 2020;
                        attributes.semClearExtensionFlags(262144);
                    }
                    attributes.gravity = (BasicRune.VOLUME_LEFT_DISPLAY_VOLUME_DIALOG ? 3 : 5) | 48;
                    if (this.infraMediator.isSupportTvVolumeSync()) {
                        attributes.semAddExtensionFlags(Integer.MIN_VALUE);
                    } else {
                        attributes.semClearExtensionFlags(Integer.MIN_VALUE);
                    }
                    window.setAttributes(attributes);
                    Unit unit = Unit.INSTANCE;
                }
                final VolumePanelView volumePanelView = this.panelView;
                VolumePanelState volumePanelState2 = getStore$2().currentState;
                volumePanelView.getClass();
                volumePanelView.isLockscreen = volumePanelState2.isLockscreen();
                volumePanelView.isDualViewEnabled = VolumePanelStateExt.isDualViewEnabled(volumePanelState2);
                if (BasicRune.VOLUME_REFRESH_RATE_FIXED) {
                    HandlerWrapper handlerWrapper = volumePanelView.handlerWrapper;
                    if (handlerWrapper == null) {
                        handlerWrapper = null;
                    }
                    IDisplayManagerWrapper iDisplayManagerWrapper = volumePanelView.iDisplayManagerWrapper;
                    if (iDisplayManagerWrapper == null) {
                        iDisplayManagerWrapper = null;
                    }
                    handlerWrapper.post(iDisplayManagerWrapper.refreshRateLimitOnRunnable);
                }
                if (VolumePanelStateExt.isAODVolumePanel(volumePanelState2)) {
                    ViewGroup viewGroup = volumePanelView.volumePanelView;
                    if (viewGroup == null) {
                        viewGroup = null;
                    }
                    volumePanelView.rowContainer = (ViewGroup) viewGroup.requireViewById(R.id.volume_panel_row_container);
                    ViewGroup viewGroup2 = volumePanelView.volumePanelView;
                    if (viewGroup2 == null) {
                        viewGroup2 = null;
                    }
                    volumePanelView.expandButton = (ImageView) viewGroup2.requireViewById(R.id.volume_panel_expand_button);
                } else if (volumePanelView.isDualViewEnabled) {
                    ViewGroup viewGroup3 = volumePanelView.volumePanelDualView;
                    if (viewGroup3 == null) {
                        viewGroup3 = null;
                    }
                    volumePanelView.rowContainer = (ViewGroup) viewGroup3.requireViewById(R.id.volume_panel_row_container);
                    ViewGroup viewGroup4 = volumePanelView.volumePanelDualView;
                    if (viewGroup4 == null) {
                        viewGroup4 = null;
                    }
                    volumePanelView.expandButton = (ImageView) viewGroup4.requireViewById(R.id.volume_panel_expand_button);
                    ViewGroup viewGroup5 = volumePanelView.volumePanelDualView;
                    if (viewGroup5 == null) {
                        viewGroup5 = null;
                    }
                    volumePanelView.dualViewTitle = (TextView) viewGroup5.requireViewById(R.id.volume_panel_dual_view_title);
                } else {
                    ViewGroup viewGroup6 = volumePanelView.volumePanelView;
                    if (viewGroup6 == null) {
                        viewGroup6 = null;
                    }
                    volumePanelView.rowContainer = (ViewGroup) viewGroup6.requireViewById(R.id.volume_panel_row_container);
                    ViewGroup viewGroup7 = volumePanelView.volumePanelView;
                    if (viewGroup7 == null) {
                        viewGroup7 = null;
                    }
                    volumePanelView.expandButton = (ImageView) viewGroup7.requireViewById(R.id.volume_panel_expand_button);
                }
                if (VolumePanelStateExt.isAODVolumePanel(volumePanelState2)) {
                    ViewGroup viewGroup8 = volumePanelView.volumeAODPanelView;
                    if (viewGroup8 == null) {
                        viewGroup8 = null;
                    }
                    ViewGroup.LayoutParams layoutParams = viewGroup8.getLayoutParams();
                    layoutParams.width = ContextUtils.getDisplayWidth(volumePanelView.getContext());
                    layoutParams.height = ContextUtils.getDisplayHeight(volumePanelView.getContext());
                    ViewGroup viewGroup9 = volumePanelView.volumeAODPanelView;
                    if (viewGroup9 == null) {
                        viewGroup9 = null;
                    }
                    viewGroup9.setLayoutParams(layoutParams);
                    ViewGroup viewGroup10 = volumePanelView.volumePanelView;
                    if (viewGroup10 == null) {
                        viewGroup10 = null;
                    }
                    VolumePanelWindow volumePanelWindow = volumePanelView.dialog;
                    if (volumePanelWindow == null) {
                        volumePanelWindow = null;
                    }
                    Window window2 = volumePanelWindow.getWindow();
                    window2.getClass();
                    viewGroup10.setPadding(0, window2.getAttributes().y, 0, 0);
                } else {
                    ViewGroup viewGroup11 = volumePanelView.volumePanelView;
                    if (viewGroup11 == null) {
                        viewGroup11 = null;
                    }
                    viewGroup11.setPadding(0, 0, 0, 0);
                }
                volumePanelView.initViewVisibility(volumePanelState2);
                if (VolumePanelStateExt.isAODVolumePanel(volumePanelState2)) {
                    VolumePanelRow volumePanelRowFindRow = VolumePanelStateExt.INSTANCE.findRow(volumePanelState2, volumePanelState2.getActiveStream());
                    if (volumePanelRowFindRow != null) {
                        volumePanelView.currentVolume = volumePanelRowFindRow.getRealLevel();
                    }
                    VibratorWrapper vibratorWrapper = volumePanelView.vibratorWrapper;
                    if (vibratorWrapper == null) {
                        vibratorWrapper = null;
                    }
                    vibratorWrapper.vibrate();
                }
                if (volumePanelView.isDualViewEnabled) {
                    TextView textView = volumePanelView.dualViewTitle;
                    if (textView == null) {
                        textView = null;
                    }
                    textView.setText(volumePanelView.getContext().getString(R.string.volume_panel_view_title));
                    ViewGroup viewGroup12 = volumePanelView.volumePanelDualView;
                    if (viewGroup12 == null) {
                        viewGroup12 = null;
                    }
                    ((ViewGroup) viewGroup12.requireViewById(R.id.volume_panel_dual_view_background)).setBackground((((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isReduceTransparencyEnabled() || !(BasicRune.VOLUME_PARTIAL_BLUR || BasicRune.VOLUME_CAPTURED_BLUR)) ? volumePanelView.getContext().getDrawable(R.drawable.volume_panel_expand_bg) : volumePanelView.getContext().getDrawable(R.drawable.volume_panel_expand_bg_blur));
                }
                ImageView imageView = volumePanelView.expandButton;
                if (imageView == null) {
                    imageView = null;
                }
                imageView.setContentDescription(volumePanelView.getContext().getString(R.string.sec_qs_media_player_expand_content_description));
                ImageView imageView2 = volumePanelView.expandButton;
                if (imageView2 == null) {
                    imageView2 = null;
                }
                imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.volume.view.standard.VolumePanelView$initExpandButton$1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        VolumePanelExpandView$adjustTouchEventForOutsideTouch$1$$ExternalSyntheticOutline0.m(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_EXPAND_BUTTON_CLICKED), true, volumePanelView.storeInteractor, false);
                    }
                });
                ImageView imageView3 = volumePanelView.expandButton;
                if (imageView3 == null) {
                    imageView3 = null;
                }
                imageView3.setClickable(volumePanelState2.isShowA11yStream());
                volumePanelView.addVolumeRows(volumePanelState2);
                VolumePanelWindow volumePanelWindow2 = volumePanelView.dialog;
                if (volumePanelWindow2 == null) {
                    volumePanelWindow2 = null;
                }
                Window window3 = volumePanelWindow2.getWindow();
                window3.getClass();
                window3.getDecorView().setAlpha(0.0f);
                VolumePanelWindow volumePanelWindow3 = volumePanelView.dialog;
                (volumePanelWindow3 == null ? null : volumePanelWindow3).show();
                break;
            case 2:
            case 3:
            case 4:
            case 5:
                if (isShowing() && !volumePanelState.isAnimating()) {
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
                boolean z3 = BasicRune.VOLUME_SUB_DISPLAY_FULL_LAYOUT_VOLUME_DIALOG;
                if (!z3 || !volumePanelState.isFolded()) {
                    VolumePanelExpandWindow volumePanelExpandWindow = new VolumePanelExpandWindow(this.volDeps);
                    Window window4 = volumePanelExpandWindow.getWindow();
                    if (window4 != null) {
                        WindowManager.LayoutParams attributes2 = window4.getAttributes();
                        attributes2.gravity = (BasicRune.VOLUME_LEFT_DISPLAY_VOLUME_DIALOG ? 3 : 5) | 48;
                        attributes2.x = 0;
                        if (volumePanelExpandWindow.systemConfig.isTablet() || !ContextUtils.isLandscape(volumePanelExpandWindow.getContext())) {
                            f = 2.0f;
                            float displayHeight = ContextUtils.getDisplayHeight(volumePanelExpandWindow.getContext()) - ContextUtils.getDimenInt(R.dimen.volume_seekbar_height, volumePanelExpandWindow.getContext());
                            if (volumePanelExpandWindow.systemConfig.isTablet()) {
                                f4 = displayHeight / 2.0f;
                            } else {
                                if (BasicRune.FOLDABLE_TYPE_FLIP) {
                                    VolumePanelLayout.INSTANCE.getClass();
                                    f3 = VolumePanelLayout.VERTICAL_PADDING_TOP_FOR_FLIP_RATIO;
                                } else if (ContextUtils.isScreenWideMobileDevice(volumePanelExpandWindow.getContext())) {
                                    VolumePanelLayout.INSTANCE.getClass();
                                    f3 = VolumePanelLayout.VERTICAL_WIDE_SCREEN_TOP_RATIO;
                                } else {
                                    VolumePanelLayout.INSTANCE.getClass();
                                    f3 = VolumePanelLayout.VERTICAL_PADDING_TOP_RATIO;
                                }
                                f4 = displayHeight * f3;
                            }
                            f2 = 0.0f;
                            paddingTop = (int) ((f4 - volumePanelExpandWindow.panelView.getPaddingTop()) - ContextUtils.getDimenInt(R.dimen.volume_panel_expand_row_container_margin_top, volumePanelExpandWindow.getContext()));
                        } else {
                            int displayHeight2 = (((ContextUtils.isScreenWideMobileDevice(volumePanelExpandWindow.getContext()) ? ContextUtils.getDisplayHeight(volumePanelExpandWindow.getContext()) / 2 : ContextUtils.getDisplayHeight(volumePanelExpandWindow.getContext())) - ((volumePanelExpandWindow.getStore().currentState.isZenMode() || volumePanelExpandWindow.getStore().currentState.isAllSoundOff() || volumePanelExpandWindow.getStore().currentState.isLeBroadcasting()) ? ContextUtils.getDimenInt(R.dimen.volume_panel_expand_height_with_msg, volumePanelExpandWindow.getContext()) : ContextUtils.getDimenInt(R.dimen.volume_panel_expand_height, volumePanelExpandWindow.getContext()))) / 2) - ContextUtils.getDimenInt(R.dimen.volume_expand_panel_vertical_padding, volumePanelExpandWindow.getContext());
                            paddingTop = displayHeight2 < 0 ? 0 : displayHeight2;
                            f = 2.0f;
                            f2 = 0.0f;
                        }
                        attributes2.y = paddingTop;
                        attributes2.setFitInsetsTypes(WindowInsets.Type.navigationBars());
                        window4.setAttributes(attributes2);
                        Unit unit2 = Unit.INSTANCE;
                    } else {
                        f = 2.0f;
                        f2 = 0.0f;
                    }
                    VolumePanelState volumePanelState3 = volumePanelExpandWindow.getStore().currentState;
                    Window window5 = volumePanelExpandWindow.getWindow();
                    if (window5 != null) {
                        WindowManager.LayoutParams attributes3 = window5.getAttributes();
                        if (volumePanelState3.isShowA11yStream()) {
                            attributes3.flags &= -9;
                        }
                        window5.setAttributes(attributes3);
                        Unit unit3 = Unit.INSTANCE;
                    }
                    final VolumePanelExpandView volumePanelExpandView = volumePanelExpandWindow.panelView;
                    final VolumePanelState volumePanelState4 = volumePanelExpandWindow.getStore().currentState;
                    volumePanelExpandView.addRows(volumePanelState4);
                    Space space = (Space) volumePanelExpandView.requireViewById(R.id.volume_panel_expand_bottom_space);
                    ViewGroup viewGroup13 = (ViewGroup) volumePanelExpandView.requireViewById(R.id.volume_panel_status_message_layout);
                    TextView textView2 = (TextView) volumePanelExpandView.requireViewById(R.id.volume_panel_status_message_description);
                    ImageView imageView4 = (ImageView) volumePanelExpandView.requireViewById(R.id.volume_panel_status_message_icon);
                    imageView4.setImageTintList(ColorUtils.getSingleColorStateList(R.color.volume_panel_status_message_color, volumePanelExpandView.getContext()));
                    boolean z4 = volumePanelState4.isAllSoundOff() || volumePanelState4.isZenMode() || volumePanelState4.isLeBroadcasting();
                    ViewVisibilityUtil.INSTANCE.getClass();
                    if (z4) {
                        ViewVisibilityUtil.setGone(space);
                    } else {
                        space.setVisibility(0);
                    }
                    ViewVisibilityUtil.INSTANCE.getClass();
                    if (z4) {
                        viewGroup13.setVisibility(0);
                    } else {
                        ViewVisibilityUtil.setGone(viewGroup13);
                    }
                    if (z4) {
                        textView2.setText(volumePanelExpandView.getContext().getString(volumePanelState4.isAllSoundOff() ? R.string.volume_mute_all_sounds_on : volumePanelState4.isZenMode() ? R.string.volume_zen_mode_on : R.string.volume_panel_broadcasting_sound_using_auracast));
                        if (volumePanelState4.isAllSoundOff() || volumePanelState4.isZenMode()) {
                            imageView4.setImageDrawable(volumePanelExpandView.getContext().getResources().getDrawable(R.drawable.ic_volume_control_dnd, null));
                            viewGroup13.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.volume.view.expand.VolumePanelExpandView$updateStatusMsgArea$1
                                @Override // android.view.View.OnClickListener
                                public final void onClick(View view) {
                                    VolumePanelExpandView$adjustTouchEventForOutsideTouch$1$$ExternalSyntheticOutline0.m(new VolumePanelAction.Builder(volumePanelState4.isAllSoundOff() ? VolumePanelAction.ActionType.ACTION_STATUS_MESSAGE_CLICKED : VolumePanelAction.ActionType.ACTION_STATUS_DO_NOT_DISTURB_MESSAGE_CLICKED), true, volumePanelExpandView.storeInteractor, false);
                                }
                            });
                        } else if (volumePanelState4.isLeBroadcasting()) {
                            imageView4.setImageDrawable(volumePanelExpandView.getContext().getResources().getDrawable(R.drawable.ic_auracast, null));
                            viewGroup13.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.volume.view.expand.VolumePanelExpandView$updateStatusMsgArea$2
                                @Override // android.view.View.OnClickListener
                                public final void onClick(View view) {
                                    VolumePanelExpandView$adjustTouchEventForOutsideTouch$1$$ExternalSyntheticOutline0.m(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_STATUS_LE_BROADCASTING_MESSAGE_CLICKED), true, volumePanelExpandView.storeInteractor, false);
                                }
                            });
                        }
                    }
                    int displayWidth = ContextUtils.getDisplayWidth(volumePanelExpandView.getContext());
                    int displayHeight3 = ContextUtils.getDisplayHeight(volumePanelExpandView.getContext());
                    boolean z5 = BasicRune.VOLUME_FOLDABLE_WIDE_SCREEN_VOLUME_DIALOG && ((ContextUtils.isLandscape(volumePanelExpandView.getContext()) ? displayHeight3 : displayWidth) > ContextUtils.getDimenInt(R.dimen.volume_panel_screen_width_threshold, volumePanelExpandView.getContext()));
                    boolean z6 = ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isNavigationBarGestureHintEnabled() && ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isNavigationBarGestureWhileHidden();
                    SystemConfigImpl systemConfigImpl = volumePanelExpandView.systemConfig;
                    if (systemConfigImpl == null) {
                        systemConfigImpl = null;
                    }
                    if (systemConfigImpl.isTablet() || z5 || (ContextUtils.isLandscape(volumePanelExpandView.getContext()) && z6)) {
                        displayHeight3 += ContextUtils.getDimenInt(android.R.dimen.select_dialog_drawable_padding_start_material, volumePanelExpandView.getContext());
                    } else {
                        ContextUtils contextUtils = ContextUtils.INSTANCE;
                        Context context = volumePanelExpandView.getContext();
                        contextUtils.getClass();
                        if (context.getResources().getConfiguration().orientation == 1) {
                        }
                    }
                    ViewGroup viewGroup14 = volumePanelExpandView.contentsView;
                    if (viewGroup14 == null) {
                        viewGroup14 = null;
                    }
                    ViewGroup.LayoutParams layoutParams2 = viewGroup14.getLayoutParams();
                    int dimenInt = ContextUtils.getDimenInt(R.dimen.volume_expand_panel_horizontal_padding_min, volumePanelExpandView.getContext());
                    int dimenInt2 = ContextUtils.getDimenInt(R.dimen.volume_panel_expand_width, volumePanelExpandView.getContext());
                    boolean zIsLandscape = ContextUtils.isLandscape(volumePanelExpandView.getContext());
                    SystemConfigImpl systemConfigImpl2 = volumePanelExpandView.systemConfig;
                    if (systemConfigImpl2 == null) {
                        systemConfigImpl2 = null;
                    }
                    boolean zIsTablet = systemConfigImpl2.isTablet();
                    VolumePanelLayout volumePanelLayout = VolumePanelLayout.INSTANCE;
                    if (!zIsTablet && !z5) {
                        dimenInt2 = (zIsLandscape ? displayHeight3 : displayWidth) - (dimenInt * 2);
                    }
                    layoutParams2.width = dimenInt2;
                    int dimenInt3 = ContextUtils.getDimenInt(R.dimen.volume_expand_panel_horizontal_padding, volumePanelExpandView.getContext());
                    int dimenInt4 = ContextUtils.getDimenInt(R.dimen.volume_expand_panel_vertical_padding, volumePanelExpandView.getContext());
                    int dimenInt5 = ContextUtils.getDimenInt(R.dimen.volume_panel_expand_height, volumePanelExpandView.getContext());
                    int dimenInt6 = ContextUtils.getDimenInt(R.dimen.volume_panel_expand_height_with_msg, volumePanelExpandView.getContext());
                    boolean z7 = volumePanelState4.isZenMode() || volumePanelState4.isAllSoundOff() || volumePanelState4.isLeBroadcasting();
                    boolean zIsLandscape2 = ContextUtils.isLandscape(volumePanelExpandView.getContext());
                    int i2 = layoutParams2.width;
                    SystemConfigImpl systemConfigImpl3 = volumePanelExpandView.systemConfig;
                    if (systemConfigImpl3 == null) {
                        systemConfigImpl3 = null;
                    }
                    boolean zIsTablet2 = systemConfigImpl3.isTablet();
                    if (!zIsTablet2 && z5 && zIsLandscape2) {
                        z = z3;
                        float f5 = ((displayHeight3 / f) - (z7 ? dimenInt6 : dimenInt5)) / 2;
                        if (f5 < f2) {
                            f5 = f2;
                        }
                        pair = new Pair(Integer.valueOf((int) f5), Integer.valueOf(dimenInt4));
                    } else {
                        z = z3;
                        if (zIsTablet2 || z5 || !zIsLandscape2) {
                            pair = new Pair(Integer.valueOf(dimenInt4), Integer.valueOf(dimenInt4));
                        } else {
                            int i3 = (displayHeight3 - (z7 ? dimenInt6 : dimenInt5)) / 2;
                            if (i3 <= dimenInt4) {
                                dimenInt4 = i3;
                            }
                            pair = new Pair(Integer.valueOf(dimenInt4), Integer.valueOf(dimenInt4));
                        }
                    }
                    Integer num = (Integer) pair.first;
                    Integer num2 = (Integer) pair.second;
                    SystemConfigImpl systemConfigImpl4 = volumePanelExpandView.systemConfig;
                    if (systemConfigImpl4 == null) {
                        systemConfigImpl4 = null;
                    }
                    boolean zIsTablet3 = systemConfigImpl4.isTablet();
                    if (!zIsLandscape2 && !z5 && !zIsTablet3 && (i = (displayWidth - i2) / 2) <= dimenInt3) {
                        dimenInt3 = i;
                    }
                    num.getClass();
                    int iIntValue = num.intValue();
                    num2.getClass();
                    volumePanelExpandView.setPadding(dimenInt3, iIntValue, dimenInt3, num2.intValue());
                    int i4 = layoutParams2.width;
                    ViewGroup viewGroup15 = (ViewGroup) volumePanelExpandView.requireViewById(R.id.volume_panel_expand_view_background);
                    ViewGroup.LayoutParams layoutParams3 = viewGroup15.getLayoutParams();
                    layoutParams3.width = i4;
                    if (z7) {
                        dimenInt5 = dimenInt6;
                    }
                    layoutParams3.height = dimenInt5;
                    if (((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isReduceTransparencyEnabled()) {
                        viewGroup15.setBackground(volumePanelExpandView.getContext().getDrawable(R.drawable.volume_panel_expand_bg_reduce_transparency));
                    } else if (BasicRune.VOLUME_PARTIAL_BLUR || BasicRune.VOLUME_CAPTURED_BLUR) {
                        viewGroup15.setBackground(volumePanelExpandView.getContext().getDrawable(R.drawable.volume_panel_expand_bg_blur));
                    } else {
                        viewGroup15.setBackground(volumePanelExpandView.getContext().getDrawable(R.drawable.volume_panel_expand_bg));
                    }
                    volumePanelExpandView.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.volume.view.expand.VolumePanelExpandView$adjustTouchEventForOutsideTouch$1
                        @Override // android.view.View.OnTouchListener
                        public final boolean onTouch(View view, MotionEvent motionEvent) {
                            VolumePanelExpandView$adjustTouchEventForOutsideTouch$1$$ExternalSyntheticOutline0.m(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_TOUCH_OUTSIDE), true, volumePanelExpandView.storeInteractor, true);
                            return true;
                        }
                    });
                    ViewGroup viewGroup16 = volumePanelExpandView.contentsView;
                    if (viewGroup16 == null) {
                        viewGroup16 = null;
                    }
                    viewGroup16.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.volume.view.expand.VolumePanelExpandView$adjustTouchEventForOutsideTouch$2
                        @Override // android.view.View.OnTouchListener
                        public final boolean onTouch(View view, MotionEvent motionEvent) {
                            return true;
                        }
                    });
                    if (volumePanelState4.isSetupWizardComplete()) {
                        ViewVisibilityUtil viewVisibilityUtil = ViewVisibilityUtil.INSTANCE;
                        ImageButton imageButton = volumePanelExpandView.settingButton;
                        if (imageButton == null) {
                            imageButton = null;
                        }
                        viewVisibilityUtil.getClass();
                        imageButton.setVisibility(0);
                        ImageButton imageButton2 = volumePanelExpandView.settingButton;
                        if (imageButton2 == null) {
                            imageButton2 = null;
                        }
                        z2 = true;
                        imageButton2.setEnabled(true);
                    } else {
                        z2 = true;
                        ViewVisibilityUtil viewVisibilityUtil2 = ViewVisibilityUtil.INSTANCE;
                        ImageButton imageButton3 = volumePanelExpandView.settingButton;
                        if (imageButton3 == null) {
                            imageButton3 = null;
                        }
                        viewVisibilityUtil2.getClass();
                        ViewVisibilityUtil.setGone(imageButton3);
                        ImageButton imageButton4 = volumePanelExpandView.settingButton;
                        if (imageButton4 == null) {
                            imageButton4 = null;
                        }
                        imageButton4.setEnabled(false);
                    }
                    VolumePanelExpandWindow volumePanelExpandWindow2 = volumePanelExpandView.dialog;
                    if (volumePanelExpandWindow2 == null) {
                        volumePanelExpandWindow2 = null;
                    }
                    Window window6 = volumePanelExpandWindow2.getWindow();
                    if (window6 != null && (decorView = window6.getDecorView()) != null) {
                        decorView.setAlpha(f2);
                        decorView.setScaleX(0.95f);
                        decorView.setScaleY(0.95f);
                    }
                    boolean z8 = (volumePanelState4.isFolded() && z) ? z2 : false;
                    if (!volumePanelState4.isCaptionComponentEnabled() || z8) {
                        ViewVisibilityUtil viewVisibilityUtil3 = ViewVisibilityUtil.INSTANCE;
                        ImageButton imageButton5 = volumePanelExpandView.liveCaptionButton;
                        if (imageButton5 == null) {
                            imageButton5 = null;
                        }
                        viewVisibilityUtil3.getClass();
                        ViewVisibilityUtil.setGone(imageButton5);
                    } else {
                        ViewVisibilityUtil viewVisibilityUtil4 = ViewVisibilityUtil.INSTANCE;
                        ImageButton imageButton6 = volumePanelExpandView.liveCaptionButton;
                        if (imageButton6 == null) {
                            imageButton6 = null;
                        }
                        viewVisibilityUtil4.getClass();
                        imageButton6.setVisibility(0);
                        volumePanelExpandView.toggleLiveCaptionButton(volumePanelState4.isCaptionEnabled());
                    }
                    VolumePanelRow volumePanelRowFindRow2 = VolumePanelStateExt.INSTANCE.findRow(volumePanelState4, volumePanelState4.getActiveStream());
                    if (volumePanelRowFindRow2 != null) {
                        if (volumePanelRowFindRow2.getRemoteLabel().length() <= 0) {
                            volumePanelRowFindRow2 = null;
                        }
                        if (volumePanelRowFindRow2 != null) {
                            volumePanelExpandView.updateVolumeTitle(volumePanelRowFindRow2.getStreamType());
                        }
                    }
                    if (volumePanelState4.isShowA11yStream()) {
                        VolumePanelExpandWindow volumePanelExpandWindow3 = volumePanelExpandView.dialog;
                        if (volumePanelExpandWindow3 == null) {
                            volumePanelExpandWindow3 = null;
                        }
                        Window window7 = volumePanelExpandWindow3.getWindow();
                        window7.getClass();
                        WindowManager.LayoutParams attributes4 = window7.getAttributes();
                        attributes4.flags &= -9;
                        VolumePanelExpandWindow volumePanelExpandWindow4 = volumePanelExpandView.dialog;
                        if (volumePanelExpandWindow4 == null) {
                            volumePanelExpandWindow4 = null;
                        }
                        Window window8 = volumePanelExpandWindow4.getWindow();
                        window8.getClass();
                        window8.setAttributes(attributes4);
                    }
                    VolumePanelExpandWindow volumePanelExpandWindow5 = volumePanelExpandView.dialog;
                    (volumePanelExpandWindow5 == null ? null : volumePanelExpandWindow5).show();
                    break;
                }
                break;
        }
    }

    @Override // android.app.Dialog
    public final void onStart() {
        super.onStart();
        this.log.d("VolumePanelWindow", "onStart");
    }

    @Override // android.app.Dialog
    public final void onStop() {
        super.onStop();
        this.log.d("VolumePanelWindow", "onStop : panelState.isExpanded=" + getStore$2().currentState.isExpanded());
        VolumePanelView volumePanelView = this.panelView;
        volumePanelView.getClass();
        if (BasicRune.VOLUME_REFRESH_RATE_FIXED) {
            HandlerWrapper handlerWrapper = volumePanelView.handlerWrapper;
            if (handlerWrapper == null) {
                handlerWrapper = null;
            }
            IDisplayManagerWrapper iDisplayManagerWrapper = volumePanelView.iDisplayManagerWrapper;
            if (iDisplayManagerWrapper == null) {
                iDisplayManagerWrapper = null;
            }
            handlerWrapper.post(iDisplayManagerWrapper.refreshRateLimitOffRunnable);
        }
        VolumePanelState volumePanelState = volumePanelView.panelState;
        if (volumePanelState == null) {
            volumePanelState = null;
        }
        if (VolumePanelStateExt.isAODVolumePanel(volumePanelState)) {
            PowerManagerWrapper powerManagerWrapper = volumePanelView.powerManagerWrapper;
            if (powerManagerWrapper == null) {
                powerManagerWrapper = null;
            }
            PowerManager.WakeLock wakeLock = powerManagerWrapper.wakeLock;
            if (wakeLock != null) {
                wakeLock.release();
            }
            powerManagerWrapper.wakeLock = null;
            PluginAODManagerWrapper pluginAODManagerWrapper = volumePanelView.pluginAODManagerWrapper;
            if (pluginAODManagerWrapper == null) {
                pluginAODManagerWrapper = null;
            }
            pluginAODManagerWrapper.getClass();
            PluginAODManagerWrapper.requestAODVolumePanel(false);
        }
        ViewGroup viewGroup = volumePanelView.rowContainer;
        (viewGroup != null ? viewGroup : null).removeAllViews();
        if (getStore$2().currentState.isExpanded()) {
            return;
        }
        ((StoreInteractor) this.storeInteractor$delegate.getValue()).sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_DISMISS_VOLUME_PANEL).build(), true);
    }
}
