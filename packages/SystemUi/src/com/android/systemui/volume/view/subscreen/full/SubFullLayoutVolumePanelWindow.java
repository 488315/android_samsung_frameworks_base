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
import android.os.Handler;
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
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.keyguard.animator.ActionUpOrCancelHandler$$ExternalSyntheticOutline0;
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
import com.android.systemui.volume.view.expand.AbstractC3626xb6ef341d;
import com.samsung.android.nexus.video.VideoPlayer;
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
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SubFullLayoutVolumePanelWindow extends Dialog implements VolumeObserver {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final VolumeInfraMediator infraMediator;
    public final LogWrapper log;
    public final SubFullLayoutVolumePanelView panelView;
    public final Lazy store$delegate;
    public final Lazy storeInteractor$delegate;
    public final VolumeDependencyBase volDeps;

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
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public SubFullLayoutVolumePanelWindow(VolumeDependencyBase volumeDependencyBase) {
        super(r0.createWindowContext(r3, 2020, null));
        VolumeDependency volumeDependency = (VolumeDependency) volumeDependencyBase;
        Context context = (Context) volumeDependency.get(Context.class);
        Display frontSubDisplay = ((DisplayManagerWrapper) volumeDependency.get(DisplayManagerWrapper.class)).getFrontSubDisplay();
        Intrinsics.checkNotNull(frontSubDisplay);
        this.volDeps = volumeDependencyBase;
        this.store$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelWindow$store$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (VolumePanelStore) ((VolumeDependency) SubFullLayoutVolumePanelWindow.this.volDeps).get(VolumePanelStore.class);
            }
        });
        this.storeInteractor$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelWindow$storeInteractor$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                SubFullLayoutVolumePanelWindow subFullLayoutVolumePanelWindow = SubFullLayoutVolumePanelWindow.this;
                int i = SubFullLayoutVolumePanelWindow.$r8$clinit;
                return new StoreInteractor(subFullLayoutVolumePanelWindow, (VolumePanelStore) subFullLayoutVolumePanelWindow.store$delegate.getValue());
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
            if (systemConfigImpl.getHasCutout()) {
                attributes.flags |= 67109888;
                attributes.layoutInDisplayCutoutMode = 2;
            }
            window.setAttributes(attributes);
        }
        setContentView(com.android.systemui.R.layout.sub_full_volume_panel_view);
        final SubFullLayoutVolumePanelView subFullLayoutVolumePanelView = (SubFullLayoutVolumePanelView) findViewById(com.android.systemui.R.id.volume_panel_view_root);
        this.panelView = subFullLayoutVolumePanelView;
        Log.d("SubFullLayoutVolumePanelView", "SubFullLayoutVolumePanelView: bind");
        subFullLayoutVolumePanelView.dialog = this;
        subFullLayoutVolumePanelView.handlerWrapper = (HandlerWrapper) volumeDependency.get(HandlerWrapper.class);
        subFullLayoutVolumePanelView.store = (VolumePanelStore) volumeDependency.get(VolumePanelStore.class);
        subFullLayoutVolumePanelView.storeInteractor.store = subFullLayoutVolumePanelView.getStore();
        subFullLayoutVolumePanelView.volDeps = volumeDependencyBase;
        subFullLayoutVolumePanelView.volumePanelMotion = (SubFullLayoutVolumePanelMotion) volumeDependency.get(SubFullLayoutVolumePanelMotion.class);
        subFullLayoutVolumePanelView.blurEffect = new BlurEffect(subFullLayoutVolumePanelView.getContext(), volumeDependencyBase);
        subFullLayoutVolumePanelView.iDisplayManagerWrapper = (IDisplayManagerWrapper) volumeDependency.get(IDisplayManagerWrapper.class);
        subFullLayoutVolumePanelView.vibratorWrapper = (VibratorWrapper) volumeDependency.get(VibratorWrapper.class);
        subFullLayoutVolumePanelView.powerManagerWrapper = (PowerManagerWrapper) volumeDependency.get(PowerManagerWrapper.class);
        subFullLayoutVolumePanelView.pluginAODManagerWrapper = (PluginAODManagerWrapper) volumeDependency.get(PluginAODManagerWrapper.class);
        ViewGroup viewGroup = subFullLayoutVolumePanelView.volumePanelDualView;
        (viewGroup == null ? null : viewGroup).setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelView$bind$1
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                AbstractC3626xb6ef341d.m227m(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_TOUCH_OUTSIDE), true, SubFullLayoutVolumePanelView.this.storeInteractor, false);
                return true;
            }
        });
        ViewGroup viewGroup2 = subFullLayoutVolumePanelView.volumePanelDualView;
        ((ViewGroup) (viewGroup2 == null ? null : viewGroup2).findViewById(com.android.systemui.R.id.volume_panel_dual_view_contents)).setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelView$bind$2
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        Dialog dialog = subFullLayoutVolumePanelView.dialog;
        (dialog == null ? null : dialog).setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelView$bind$3
            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r10v6, types: [com.android.systemui.volume.util.PluginAODManagerWrapper] */
            /* JADX WARN: Type inference failed for: r2v7, types: [com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelView$bind$3$1$1] */
            @Override // android.content.DialogInterface.OnShowListener
            public final void onShow(DialogInterface dialogInterface) {
                SubFullLayoutVolumePanelView$bind$3$showBlurRunnable$1 subFullLayoutVolumePanelView$bind$3$showBlurRunnable$1 = new Runnable() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelView$bind$3$showBlurRunnable$1
                    @Override // java.lang.Runnable
                    public final void run() {
                    }
                };
                SubFullLayoutVolumePanelView subFullLayoutVolumePanelView2 = SubFullLayoutVolumePanelView.this;
                final ImageView imageView = subFullLayoutVolumePanelView2.isDualViewEnabled ? (ImageView) subFullLayoutVolumePanelView2.findViewById(com.android.systemui.R.id.volume_panel_dual_blur) : (ImageView) subFullLayoutVolumePanelView2.findViewById(com.android.systemui.R.id.volume_panel_blur);
                boolean z = BasicRune.VOLUME_CAPTURED_BLUR;
                if (z && imageView != null) {
                    final SubFullLayoutVolumePanelView subFullLayoutVolumePanelView3 = SubFullLayoutVolumePanelView.this;
                    subFullLayoutVolumePanelView$bind$3$showBlurRunnable$1 = new Runnable() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelView$bind$3$1$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            final SubFullLayoutVolumePanelView subFullLayoutVolumePanelView4 = SubFullLayoutVolumePanelView.this;
                            BlurEffect blurEffect = subFullLayoutVolumePanelView4.blurEffect;
                            if (blurEffect == null) {
                                blurEffect = null;
                            }
                            final ImageView imageView2 = imageView;
                            blurEffect.setCapturedBlur(imageView2, new Supplier() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelView$bind$3$1$1.1
                                @Override // java.util.function.Supplier
                                public final Object get() {
                                    ViewLocationUtil viewLocationUtil = ViewLocationUtil.INSTANCE;
                                    ImageView imageView3 = imageView2;
                                    viewLocationUtil.getClass();
                                    int[] iArr = new int[2];
                                    imageView3.getLocationOnScreen(iArr);
                                    SubFullLayoutVolumePanelView subFullLayoutVolumePanelView5 = subFullLayoutVolumePanelView4;
                                    if (subFullLayoutVolumePanelView5.isDualViewEnabled) {
                                        iArr[0] = iArr[0] - ((int) (imageView2.getWidth() * 0.05d));
                                        iArr[1] = iArr[1] - ((int) (imageView2.getHeight() * 0.05d));
                                    } else {
                                        int i = iArr[0];
                                        Dialog dialog2 = subFullLayoutVolumePanelView5.dialog;
                                        if (dialog2 == null) {
                                            dialog2 = null;
                                        }
                                        Window window2 = dialog2.getWindow();
                                        Intrinsics.checkNotNull(window2);
                                        iArr[0] = i - (window2.getDecorView().getWidth() * (BasicRune.VOLUME_LEFT_DISPLAY_VOLUME_DIALOG ? -1 : 1));
                                    }
                                    return iArr;
                                }
                            });
                        }
                    };
                }
                SubFullLayoutVolumePanelView subFullLayoutVolumePanelView4 = SubFullLayoutVolumePanelView.this;
                if (subFullLayoutVolumePanelView4.isDualViewEnabled) {
                    final SubFullLayoutVolumePanelMotion subFullLayoutVolumePanelMotion = subFullLayoutVolumePanelView4.volumePanelMotion;
                    if (subFullLayoutVolumePanelMotion == null) {
                        subFullLayoutVolumePanelMotion = null;
                    }
                    Dialog dialog2 = subFullLayoutVolumePanelView4.dialog;
                    Window window2 = (dialog2 != null ? dialog2 : null).getWindow();
                    Intrinsics.checkNotNull(window2);
                    final View decorView = window2.getDecorView();
                    subFullLayoutVolumePanelMotion.getClass();
                    decorView.setTranslationX(0.0f);
                    ObjectAnimator ofFloat = ObjectAnimator.ofFloat(decorView, "alpha", decorView.getAlpha(), 1.0f);
                    ofFloat.setDuration(200L);
                    ofFloat.setInterpolator(new LinearInterpolator());
                    ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(decorView, "scaleX", 0.9f, 1.0f);
                    ofFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelMotion$startVolumeDualViewShowAnimation$scaleAnimator$1$1
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                            decorView.setScaleY(((Float) valueAnimator.getAnimatedValue()).floatValue());
                        }
                    });
                    ofFloat2.setDuration(400L);
                    ofFloat2.setInterpolator(new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f));
                    AnimatorSet animatorSet = new AnimatorSet();
                    animatorSet.playTogether(ofFloat);
                    animatorSet.playTogether(ofFloat2);
                    animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelMotion$startVolumeDualViewShowAnimation$1$1
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public final void onAnimationEnd(Animator animator) {
                            SubFullLayoutVolumePanelMotion.this.storeInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_ANIMATION_FINISHED).build(), true);
                        }

                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public final void onAnimationStart(Animator animator) {
                            SubFullLayoutVolumePanelMotion.this.storeInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_ANIMATION_START).build(), true);
                        }
                    });
                    animatorSet.start();
                    if (z) {
                        subFullLayoutVolumePanelView$bind$3$showBlurRunnable$1.run();
                        return;
                    }
                    return;
                }
                if (!VolumePanelStateExt.isAODVolumePanel(subFullLayoutVolumePanelView4.getPanelState())) {
                    SubFullLayoutVolumePanelView subFullLayoutVolumePanelView5 = SubFullLayoutVolumePanelView.this;
                    SubFullLayoutVolumePanelMotion subFullLayoutVolumePanelMotion2 = subFullLayoutVolumePanelView5.volumePanelMotion;
                    if (subFullLayoutVolumePanelMotion2 == null) {
                        subFullLayoutVolumePanelMotion2 = null;
                    }
                    Dialog dialog3 = subFullLayoutVolumePanelView5.dialog;
                    Window window3 = (dialog3 != null ? dialog3 : null).getWindow();
                    Intrinsics.checkNotNull(window3);
                    View decorView2 = window3.getDecorView();
                    subFullLayoutVolumePanelMotion2.getClass();
                    SpringAnimation springAnimation = new SpringAnimation(decorView2, DynamicAnimation.TRANSLATION_X);
                    springAnimation.mSpring = ActionUpOrCancelHandler$$ExternalSyntheticOutline0.m145m(150.0f, 0.7f);
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
                SubFullLayoutVolumePanelView subFullLayoutVolumePanelView6 = SubFullLayoutVolumePanelView.this;
                SubFullLayoutVolumePanelMotion subFullLayoutVolumePanelMotion3 = subFullLayoutVolumePanelView6.volumePanelMotion;
                if (subFullLayoutVolumePanelMotion3 == null) {
                    subFullLayoutVolumePanelMotion3 = null;
                }
                Dialog dialog4 = subFullLayoutVolumePanelView6.dialog;
                if (dialog4 == null) {
                    dialog4 = null;
                }
                Window window4 = dialog4.getWindow();
                Intrinsics.checkNotNull(window4);
                View decorView3 = window4.getDecorView();
                subFullLayoutVolumePanelMotion3.getClass();
                decorView3.setTranslationX(0.0f);
                decorView3.setScaleX(1.0f);
                decorView3.setScaleY(1.0f);
                ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(decorView3, "alpha", decorView3.getAlpha(), 1.0f);
                ofFloat3.setDuration(100L);
                ofFloat3.setInterpolator(new LinearInterpolator());
                ofFloat3.start();
                SubFullLayoutVolumePanelView subFullLayoutVolumePanelView7 = SubFullLayoutVolumePanelView.this;
                subFullLayoutVolumePanelView7.isFirstTouch = true;
                PowerManagerWrapper powerManagerWrapper = subFullLayoutVolumePanelView7.powerManagerWrapper;
                if (powerManagerWrapper == null) {
                    powerManagerWrapper = null;
                }
                Context context2 = subFullLayoutVolumePanelView7.getContext();
                powerManagerWrapper.getClass();
                SystemServiceExtension.INSTANCE.getClass();
                PowerManager.WakeLock newWakeLock = ((PowerManager) context2.getSystemService(PowerManager.class)).newWakeLock(1, "AOD_VolumePanel");
                newWakeLock.acquire();
                powerManagerWrapper.wakeLock = newWakeLock;
                ?? r10 = SubFullLayoutVolumePanelView.this.pluginAODManagerWrapper;
                (r10 != 0 ? r10 : null).getClass();
                PluginAODManagerWrapper.requestAODVolumePanel(true);
            }
        });
        Dialog dialog2 = subFullLayoutVolumePanelView.dialog;
        Window window2 = (dialog2 == null ? null : dialog2).getWindow();
        Intrinsics.checkNotNull(window2);
        window2.getDecorView().setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelView$bind$4
            @Override // android.view.View.AccessibilityDelegate
            public final boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup3, View view, AccessibilityEvent accessibilityEvent) {
                AbstractC3626xb6ef341d.m227m(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_SEND_ACCESSIBILITY_EVENT), true, SubFullLayoutVolumePanelView.this.storeInteractor, true);
                return super.onRequestSendAccessibilityEvent(viewGroup3, view, accessibilityEvent);
            }
        });
        SubFullLayoutVolumePanelMotion subFullLayoutVolumePanelMotion = subFullLayoutVolumePanelView.volumePanelMotion;
        subFullLayoutVolumePanelMotion = subFullLayoutVolumePanelMotion == null ? null : subFullLayoutVolumePanelMotion;
        Dialog dialog3 = subFullLayoutVolumePanelView.dialog;
        Window window3 = (dialog3 == null ? null : dialog3).getWindow();
        Intrinsics.checkNotNull(window3);
        View decorView = window3.getDecorView();
        subFullLayoutVolumePanelMotion.getClass();
        subFullLayoutVolumePanelView.touchUpAnimation = SubFullLayoutVolumePanelMotion.getSeekBarTouchUpAnimation(decorView);
        SubFullLayoutVolumePanelMotion subFullLayoutVolumePanelMotion2 = subFullLayoutVolumePanelView.volumePanelMotion;
        subFullLayoutVolumePanelMotion2 = subFullLayoutVolumePanelMotion2 == null ? null : subFullLayoutVolumePanelMotion2;
        Dialog dialog4 = subFullLayoutVolumePanelView.dialog;
        Window window4 = (dialog4 == null ? null : dialog4).getWindow();
        Intrinsics.checkNotNull(window4);
        View decorView2 = window4.getDecorView();
        subFullLayoutVolumePanelMotion2.getClass();
        subFullLayoutVolumePanelView.touchDownAnimation = SubFullLayoutVolumePanelMotion.getSeekBarTouchDownAnimation(decorView2);
        SubFullLayoutVolumePanelMotion subFullLayoutVolumePanelMotion3 = subFullLayoutVolumePanelView.volumePanelMotion;
        subFullLayoutVolumePanelMotion3 = subFullLayoutVolumePanelMotion3 == null ? null : subFullLayoutVolumePanelMotion3;
        Dialog dialog5 = subFullLayoutVolumePanelView.dialog;
        Window window5 = (dialog5 == null ? null : dialog5).getWindow();
        Intrinsics.checkNotNull(window5);
        final View decorView3 = window5.getDecorView();
        subFullLayoutVolumePanelMotion3.getClass();
        DynamicAnimation.C01934 c01934 = DynamicAnimation.SCALE_X;
        SpringAnimation springAnimation = new SpringAnimation(decorView3, c01934);
        springAnimation.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelMotion$getSeekBarKeyDownAnimation$1$1
            @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationUpdateListener
            public final void onAnimationUpdate(float f, float f2) {
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
        Dialog dialog6 = subFullLayoutVolumePanelView.dialog;
        Window window6 = (dialog6 != null ? dialog6 : null).getWindow();
        Intrinsics.checkNotNull(window6);
        final View decorView4 = window6.getDecorView();
        subFullLayoutVolumePanelMotion4.getClass();
        SpringAnimation springAnimation2 = new SpringAnimation(decorView4, c01934);
        springAnimation2.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelMotion$getSeekBarKeyUpAnimation$1$1
            @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationUpdateListener
            public final void onAnimationUpdate(float f, float f2) {
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
        VolumePanelStore store = subFullLayoutVolumePanelView.getStore();
        Context context = subFullLayoutVolumePanelView.getContext();
        subFullLayoutVolumePanelMotion.storeInteractor.store = store;
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
                        attributes.semAddExtensionFlags(VideoPlayer.MEDIA_ERROR_SYSTEM);
                    } else {
                        attributes.semClearExtensionFlags(VideoPlayer.MEDIA_ERROR_SYSTEM);
                    }
                    window.setAttributes(attributes);
                    Unit unit = Unit.INSTANCE;
                }
                final SubFullLayoutVolumePanelView subFullLayoutVolumePanelView = this.panelView;
                VolumePanelState volumePanelState2 = ((VolumePanelStore) this.store$delegate.getValue()).currentState;
                subFullLayoutVolumePanelView.getClass();
                subFullLayoutVolumePanelView.isLockscreen = volumePanelState2.isLockscreen();
                subFullLayoutVolumePanelView.isDualViewEnabled = VolumePanelStateExt.isDualViewEnabled(volumePanelState2);
                HandlerWrapper handlerWrapper = subFullLayoutVolumePanelView.handlerWrapper;
                if (handlerWrapper == null) {
                    handlerWrapper = null;
                }
                IDisplayManagerWrapper iDisplayManagerWrapper = subFullLayoutVolumePanelView.iDisplayManagerWrapper;
                if (iDisplayManagerWrapper == null) {
                    iDisplayManagerWrapper = null;
                }
                ((Handler) handlerWrapper.mainThreadHandler$delegate.getValue()).post(iDisplayManagerWrapper.refreshRateLimitOnRunnable);
                if (VolumePanelStateExt.isAODVolumePanel(volumePanelState2)) {
                    ViewGroup viewGroup = subFullLayoutVolumePanelView.volumePanelView;
                    if (viewGroup == null) {
                        viewGroup = null;
                    }
                    subFullLayoutVolumePanelView.rowContainer = (ViewGroup) viewGroup.findViewById(com.android.systemui.R.id.volume_panel_row_container);
                    ViewGroup viewGroup2 = subFullLayoutVolumePanelView.volumePanelView;
                    if (viewGroup2 == null) {
                        viewGroup2 = null;
                    }
                    subFullLayoutVolumePanelView.expandButton = (ImageView) viewGroup2.findViewById(com.android.systemui.R.id.volume_panel_expand_button);
                } else if (subFullLayoutVolumePanelView.isDualViewEnabled) {
                    ViewGroup viewGroup3 = subFullLayoutVolumePanelView.volumePanelDualView;
                    if (viewGroup3 == null) {
                        viewGroup3 = null;
                    }
                    subFullLayoutVolumePanelView.rowContainer = (ViewGroup) viewGroup3.findViewById(com.android.systemui.R.id.volume_panel_row_container);
                    ViewGroup viewGroup4 = subFullLayoutVolumePanelView.volumePanelDualView;
                    if (viewGroup4 == null) {
                        viewGroup4 = null;
                    }
                    subFullLayoutVolumePanelView.expandButton = (ImageView) viewGroup4.findViewById(com.android.systemui.R.id.volume_panel_expand_button);
                    ViewGroup viewGroup5 = subFullLayoutVolumePanelView.volumePanelDualView;
                    if (viewGroup5 == null) {
                        viewGroup5 = null;
                    }
                    subFullLayoutVolumePanelView.dualViewTitle = (TextView) viewGroup5.findViewById(com.android.systemui.R.id.volume_panel_dual_view_title);
                } else {
                    ViewGroup viewGroup6 = subFullLayoutVolumePanelView.volumePanelView;
                    if (viewGroup6 == null) {
                        viewGroup6 = null;
                    }
                    subFullLayoutVolumePanelView.rowContainer = (ViewGroup) viewGroup6.findViewById(com.android.systemui.R.id.volume_panel_row_container);
                    ViewGroup viewGroup7 = subFullLayoutVolumePanelView.volumePanelView;
                    if (viewGroup7 == null) {
                        viewGroup7 = null;
                    }
                    subFullLayoutVolumePanelView.expandButton = (ImageView) viewGroup7.findViewById(com.android.systemui.R.id.volume_panel_expand_button);
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
                    Dialog dialog = subFullLayoutVolumePanelView.dialog;
                    if (dialog == null) {
                        dialog = null;
                    }
                    Window window2 = dialog.getWindow();
                    Intrinsics.checkNotNull(window2);
                    viewGroup10.setPadding(0, window2.getAttributes().y, 0, 0);
                } else {
                    ViewGroup viewGroup11 = subFullLayoutVolumePanelView.volumePanelView;
                    if (viewGroup11 == null) {
                        viewGroup11 = null;
                    }
                    viewGroup11.setPadding(0, 0, 0, 0);
                }
                subFullLayoutVolumePanelView.initViewVisibility(volumePanelState2);
                if (VolumePanelStateExt.isAODVolumePanel(volumePanelState2)) {
                    VolumePanelRow findRow = VolumePanelStateExt.INSTANCE.findRow(volumePanelState2, volumePanelState2.getActiveStream());
                    if (findRow != null) {
                        subFullLayoutVolumePanelView.currentVolume = findRow.getRealLevel();
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
                    ((ViewGroup) viewGroup12.findViewById(com.android.systemui.R.id.volume_panel_dual_view_background)).setBackground(((SettingsHelper) Dependency.get(SettingsHelper.class)).isReduceTransparencyEnabled() ? subFullLayoutVolumePanelView.getContext().getDrawable(com.android.systemui.R.drawable.sub_full_volume_panel_expand_bg_reduce_transparency) : subFullLayoutVolumePanelView.getContext().getDrawable(com.android.systemui.R.drawable.sub_full_volume_panel_expand_bg_blur));
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
                        AbstractC3626xb6ef341d.m227m(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_EXPAND_BUTTON_CLICKED), true, SubFullLayoutVolumePanelView.this.storeInteractor, false);
                    }
                });
                ImageView imageView3 = subFullLayoutVolumePanelView.expandButton;
                if (imageView3 == null) {
                    imageView3 = null;
                }
                imageView3.setClickable(volumePanelState2.isShowA11yStream());
                subFullLayoutVolumePanelView.addVolumeRows(volumePanelState2);
                Dialog dialog2 = subFullLayoutVolumePanelView.dialog;
                if (dialog2 == null) {
                    dialog2 = null;
                }
                Window window3 = dialog2.getWindow();
                Intrinsics.checkNotNull(window3);
                window3.getDecorView().setAlpha(0.0f);
                Dialog dialog3 = subFullLayoutVolumePanelView.dialog;
                (dialog3 != null ? dialog3 : null).show();
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
                VolumePanelState volumePanelState3 = ((VolumePanelStore) subFullLayoutVolumePanelExpandWindow.store$delegate.getValue()).currentState;
                subFullLayoutVolumePanelExpandView.rowContainer = (ViewGroup) ((ViewGroup) subFullLayoutVolumePanelExpandView.findViewById(com.android.systemui.R.id.volume_panel_expand_view)).findViewById(com.android.systemui.R.id.volume_row_container);
                subFullLayoutVolumePanelExpandView.addRows(volumePanelState3);
                Space space = (Space) subFullLayoutVolumePanelExpandView.findViewById(com.android.systemui.R.id.volume_panel_expand_bottom_space);
                ViewGroup viewGroup13 = (ViewGroup) subFullLayoutVolumePanelExpandView.findViewById(com.android.systemui.R.id.volume_panel_status_message_layout);
                TextView textView2 = (TextView) subFullLayoutVolumePanelExpandView.findViewById(com.android.systemui.R.id.volume_panel_status_message_description);
                ImageView imageView4 = (ImageView) subFullLayoutVolumePanelExpandView.findViewById(com.android.systemui.R.id.volume_panel_status_message_icon);
                imageView4.setImageTintList(ColorUtils.getSingleColorStateList(com.android.systemui.R.color.volume_panel_status_message_color, subFullLayoutVolumePanelExpandView.getContext()));
                boolean z = true;
                boolean z2 = volumePanelState3.isAllSoundOff() || volumePanelState3.isZenMode() || volumePanelState3.isLeBroadcasting();
                ViewVisibilityUtil viewVisibilityUtil = ViewVisibilityUtil.INSTANCE;
                viewVisibilityUtil.getClass();
                if (z2) {
                    ViewVisibilityUtil.setGone(space);
                } else {
                    space.setVisibility(0);
                }
                if (z2) {
                    viewVisibilityUtil.getClass();
                    viewGroup13.setVisibility(0);
                } else {
                    viewVisibilityUtil.getClass();
                    ViewVisibilityUtil.setGone(viewGroup13);
                }
                if (z2) {
                    textView2.setText(subFullLayoutVolumePanelExpandView.getContext().getString(volumePanelState3.isAllSoundOff() ? com.android.systemui.R.string.volume_mute_all_sounds_on : volumePanelState3.isZenMode() ? com.android.systemui.R.string.volume_zen_mode_on : com.android.systemui.R.string.volume_panel_broadcasting_sound_using_auracast));
                    if (volumePanelState3.isAllSoundOff() || volumePanelState3.isZenMode()) {
                        imageView4.setImageDrawable(subFullLayoutVolumePanelExpandView.getContext().getResources().getDrawable(com.android.systemui.R.drawable.ic_volume_control_dnd, null));
                        viewGroup13.setOnClickListener(volumePanelState3.isAllSoundOff() ? new View.OnClickListener() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelExpandView$updateStatusMsgArea$1
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                AbstractC3626xb6ef341d.m227m(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_STATUS_MESSAGE_CLICKED), true, SubFullLayoutVolumePanelExpandView.this.storeInteractor, false);
                            }
                        } : null);
                    } else if (volumePanelState3.isLeBroadcasting()) {
                        imageView4.setImageDrawable(subFullLayoutVolumePanelExpandView.getContext().getResources().getDrawable(com.android.systemui.R.drawable.ic_auracast, null));
                        viewGroup13.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelExpandView$updateStatusMsgArea$2
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                AbstractC3626xb6ef341d.m227m(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_STATUS_LE_BROADCASTING_MESSAGE_CLICKED), true, SubFullLayoutVolumePanelExpandView.this.storeInteractor, false);
                            }
                        });
                    }
                }
                int dimenInt = ContextUtils.getDimenInt(com.android.systemui.R.dimen.sub_full_volume_panel_expand_width, subFullLayoutVolumePanelExpandView.getContext());
                int dimenInt2 = ContextUtils.getDimenInt(com.android.systemui.R.dimen.sub_full_volume_panel_expand_height, subFullLayoutVolumePanelExpandView.getContext());
                int dimenInt3 = ContextUtils.getDimenInt(com.android.systemui.R.dimen.sub_full_volume_panel_expand_height_with_msg, subFullLayoutVolumePanelExpandView.getContext());
                if (!volumePanelState3.isZenMode() && !volumePanelState3.isAllSoundOff()) {
                    z = false;
                }
                ViewGroup viewGroup14 = (ViewGroup) subFullLayoutVolumePanelExpandView.findViewById(com.android.systemui.R.id.volume_panel_expand_view_background);
                ViewGroup viewGroup15 = (ViewGroup) subFullLayoutVolumePanelExpandView.findViewById(com.android.systemui.R.id.volume_panel_expand_view_background_stroke);
                ViewGroup.LayoutParams layoutParams2 = viewGroup14.getLayoutParams();
                layoutParams2.width = dimenInt;
                if (z) {
                    dimenInt2 = dimenInt3;
                }
                layoutParams2.height = dimenInt2;
                viewGroup14.setLayoutParams(layoutParams2);
                if (((SettingsHelper) Dependency.get(SettingsHelper.class)).isReduceTransparencyEnabled()) {
                    viewGroup14.setBackground(subFullLayoutVolumePanelExpandView.getContext().getDrawable(com.android.systemui.R.drawable.sub_full_volume_panel_expand_bg_reduce_transparency));
                    viewVisibilityUtil.getClass();
                    ViewVisibilityUtil.setGone(viewGroup15);
                } else {
                    viewGroup14.setBackground(subFullLayoutVolumePanelExpandView.getContext().getDrawable(com.android.systemui.R.drawable.sub_full_volume_panel_expand_bg_blur));
                    boolean isNightMode = ContextUtils.isNightMode(subFullLayoutVolumePanelExpandView.getContext());
                    viewVisibilityUtil.getClass();
                    if (isNightMode) {
                        ViewVisibilityUtil.setGone(viewGroup15);
                    } else {
                        viewGroup15.setVisibility(0);
                    }
                }
                subFullLayoutVolumePanelExpandView.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelExpandView$adjustTouchEventForOutsideTouch$1
                    @Override // android.view.View.OnTouchListener
                    public final boolean onTouch(View view, MotionEvent motionEvent) {
                        AbstractC3626xb6ef341d.m227m(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_TOUCH_OUTSIDE), true, SubFullLayoutVolumePanelExpandView.this.storeInteractor, true);
                        return true;
                    }
                });
                ViewGroup viewGroup16 = subFullLayoutVolumePanelExpandView.contentsView;
                if (viewGroup16 == null) {
                    viewGroup16 = null;
                }
                viewGroup16.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelExpandView$adjustTouchEventForOutsideTouch$2
                    @Override // android.view.View.OnTouchListener
                    public final boolean onTouch(View view, MotionEvent motionEvent) {
                        return true;
                    }
                });
                Dialog dialog4 = subFullLayoutVolumePanelExpandView.dialog;
                if (dialog4 == null) {
                    dialog4 = null;
                }
                Window window5 = dialog4.getWindow();
                Intrinsics.checkNotNull(window5);
                window5.getDecorView().setAlpha(0.0f);
                Dialog dialog5 = subFullLayoutVolumePanelExpandView.dialog;
                if (dialog5 == null) {
                    dialog5 = null;
                }
                Window window6 = dialog5.getWindow();
                Intrinsics.checkNotNull(window6);
                window6.getDecorView().setScaleX(0.95f);
                Dialog dialog6 = subFullLayoutVolumePanelExpandView.dialog;
                if (dialog6 == null) {
                    dialog6 = null;
                }
                Window window7 = dialog6.getWindow();
                Intrinsics.checkNotNull(window7);
                window7.getDecorView().setScaleY(0.95f);
                ImageButton imageButton = subFullLayoutVolumePanelExpandView.liveCaptionButton;
                if (imageButton == null) {
                    imageButton = null;
                }
                viewVisibilityUtil.getClass();
                ViewVisibilityUtil.setGone(imageButton);
                VolumePanelRow findRow2 = VolumePanelStateExt.INSTANCE.findRow(volumePanelState3, volumePanelState3.getActiveStream());
                if (findRow2 != null) {
                    subFullLayoutVolumePanelExpandView.updateVolumeTitle(findRow2.getStreamType());
                }
                if (volumePanelState3.isShowA11yStream()) {
                    Dialog dialog7 = subFullLayoutVolumePanelExpandView.dialog;
                    if (dialog7 == null) {
                        dialog7 = null;
                    }
                    Window window8 = dialog7.getWindow();
                    Intrinsics.checkNotNull(window8);
                    window8.clearFlags(8);
                }
                Dialog dialog8 = subFullLayoutVolumePanelExpandView.dialog;
                (dialog8 != null ? dialog8 : null).show();
                break;
        }
    }

    @Override // android.app.Dialog
    public final void onStart() {
        super.onStart();
        this.log.m98d("SubFullLayoutVolumePanelWindow", "onStart");
    }

    @Override // android.app.Dialog
    public final void onStop() {
        super.onStop();
        this.log.m98d("SubFullLayoutVolumePanelWindow", "onStop : panelState.isExpanded=" + ((VolumePanelStore) this.store$delegate.getValue()).currentState.isExpanded());
        SubFullLayoutVolumePanelView subFullLayoutVolumePanelView = this.panelView;
        HandlerWrapper handlerWrapper = subFullLayoutVolumePanelView.handlerWrapper;
        if (handlerWrapper == null) {
            handlerWrapper = null;
        }
        IDisplayManagerWrapper iDisplayManagerWrapper = subFullLayoutVolumePanelView.iDisplayManagerWrapper;
        if (iDisplayManagerWrapper == null) {
            iDisplayManagerWrapper = null;
        }
        ((Handler) handlerWrapper.mainThreadHandler$delegate.getValue()).post(iDisplayManagerWrapper.refreshRateLimitOffRunnable);
        if (VolumePanelStateExt.isAODVolumePanel(subFullLayoutVolumePanelView.getPanelState())) {
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
