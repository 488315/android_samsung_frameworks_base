package com.android.systemui.volume.view.standard;

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
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.keyguard.animator.ActionUpOrCancelHandler$$ExternalSyntheticOutline0;
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
import com.android.systemui.volume.view.expand.AbstractC3626xb6ef341d;
import com.android.systemui.volume.view.expand.VolumePanelExpandView;
import com.android.systemui.volume.view.expand.VolumePanelExpandWindow;
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
public final class VolumePanelWindow extends Dialog implements VolumeObserver {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final VolumeInfraMediator infraMediator;
    public final LogWrapper log;
    public final VolumePanelView panelView;
    public final Lazy store$delegate;
    public final Lazy storeInteractor$delegate;
    public final SystemConfigImpl systemConfig;
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
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public VolumePanelWindow(VolumeDependencyBase volumeDependencyBase) {
        super((Context) r1.get(Context.class));
        VolumeDependency volumeDependency = (VolumeDependency) volumeDependencyBase;
        this.volDeps = volumeDependencyBase;
        this.store$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.volume.view.standard.VolumePanelWindow$store$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (VolumePanelStore) ((VolumeDependency) VolumePanelWindow.this.volDeps).get(VolumePanelStore.class);
            }
        });
        this.storeInteractor$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.volume.view.standard.VolumePanelWindow$storeInteractor$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                VolumePanelWindow volumePanelWindow = VolumePanelWindow.this;
                int i = VolumePanelWindow.$r8$clinit;
                return new StoreInteractor(volumePanelWindow, (VolumePanelStore) volumePanelWindow.store$delegate.getValue());
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
            if (systemConfigImpl.getHasCutout()) {
                attributes.flags |= 67109888;
                attributes.layoutInDisplayCutoutMode = 0;
            }
            window.setAttributes(attributes);
        }
        setContentView(R.layout.volume_panel_view);
        final VolumePanelView volumePanelView = (VolumePanelView) findViewById(R.id.volume_panel_view_root);
        this.panelView = volumePanelView;
        Log.d("VolumePanelView", "VolumePanelView: bind");
        volumePanelView.dialog = this;
        volumePanelView.handlerWrapper = (HandlerWrapper) volumeDependency.get(HandlerWrapper.class);
        volumePanelView.store = (VolumePanelStore) volumeDependency.get(VolumePanelStore.class);
        volumePanelView.storeInteractor.store = volumePanelView.getStore();
        volumePanelView.volDeps = volumeDependencyBase;
        volumePanelView.volumePanelMotion = (VolumePanelMotion) volumeDependency.get(VolumePanelMotion.class);
        volumePanelView.blurEffect = new BlurEffect(volumePanelView.getContext(), volumeDependencyBase);
        volumePanelView.iDisplayManagerWrapper = (IDisplayManagerWrapper) volumeDependency.get(IDisplayManagerWrapper.class);
        volumePanelView.vibratorWrapper = (VibratorWrapper) volumeDependency.get(VibratorWrapper.class);
        volumePanelView.powerManagerWrapper = (PowerManagerWrapper) volumeDependency.get(PowerManagerWrapper.class);
        volumePanelView.pluginAODManagerWrapper = (PluginAODManagerWrapper) volumeDependency.get(PluginAODManagerWrapper.class);
        ViewGroup viewGroup = volumePanelView.volumePanelDualView;
        (viewGroup == null ? null : viewGroup).setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.volume.view.standard.VolumePanelView$bind$1
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                AbstractC3626xb6ef341d.m227m(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_TOUCH_OUTSIDE), true, VolumePanelView.this.storeInteractor, false);
                return true;
            }
        });
        ViewGroup viewGroup2 = volumePanelView.volumePanelDualView;
        ((ViewGroup) (viewGroup2 == null ? null : viewGroup2).findViewById(R.id.volume_panel_dual_view_contents)).setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.volume.view.standard.VolumePanelView$bind$2
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        Dialog dialog = volumePanelView.dialog;
        (dialog == null ? null : dialog).setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.android.systemui.volume.view.standard.VolumePanelView$bind$3
            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r10v6, types: [com.android.systemui.volume.util.PluginAODManagerWrapper] */
            /* JADX WARN: Type inference failed for: r2v7, types: [com.android.systemui.volume.view.standard.VolumePanelView$bind$3$1$1] */
            @Override // android.content.DialogInterface.OnShowListener
            public final void onShow(DialogInterface dialogInterface) {
                VolumePanelView$bind$3$showBlurRunnable$1 volumePanelView$bind$3$showBlurRunnable$1 = new Runnable() { // from class: com.android.systemui.volume.view.standard.VolumePanelView$bind$3$showBlurRunnable$1
                    @Override // java.lang.Runnable
                    public final void run() {
                    }
                };
                VolumePanelView volumePanelView2 = VolumePanelView.this;
                final ImageView imageView = volumePanelView2.isDualViewEnabled ? (ImageView) volumePanelView2.findViewById(R.id.volume_panel_dual_blur) : (ImageView) volumePanelView2.findViewById(R.id.volume_panel_blur);
                boolean z = BasicRune.VOLUME_CAPTURED_BLUR;
                if (z && imageView != null) {
                    final VolumePanelView volumePanelView3 = VolumePanelView.this;
                    volumePanelView$bind$3$showBlurRunnable$1 = new Runnable() { // from class: com.android.systemui.volume.view.standard.VolumePanelView$bind$3$1$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            final VolumePanelView volumePanelView4 = VolumePanelView.this;
                            BlurEffect blurEffect = volumePanelView4.blurEffect;
                            if (blurEffect == null) {
                                blurEffect = null;
                            }
                            final ImageView imageView2 = imageView;
                            blurEffect.setCapturedBlur(imageView2, new Supplier() { // from class: com.android.systemui.volume.view.standard.VolumePanelView$bind$3$1$1.1
                                @Override // java.util.function.Supplier
                                public final Object get() {
                                    ViewLocationUtil viewLocationUtil = ViewLocationUtil.INSTANCE;
                                    ImageView imageView3 = imageView2;
                                    viewLocationUtil.getClass();
                                    int[] iArr = new int[2];
                                    imageView3.getLocationOnScreen(iArr);
                                    VolumePanelView volumePanelView5 = volumePanelView4;
                                    if (volumePanelView5.isDualViewEnabled) {
                                        iArr[0] = iArr[0] - ((int) (imageView2.getWidth() * 0.05d));
                                        iArr[1] = iArr[1] - ((int) (imageView2.getHeight() * 0.05d));
                                    } else {
                                        int i = iArr[0];
                                        Dialog dialog2 = volumePanelView5.dialog;
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
                VolumePanelView volumePanelView4 = VolumePanelView.this;
                if (volumePanelView4.isDualViewEnabled) {
                    final VolumePanelMotion volumePanelMotion = volumePanelView4.volumePanelMotion;
                    if (volumePanelMotion == null) {
                        volumePanelMotion = null;
                    }
                    Dialog dialog2 = volumePanelView4.dialog;
                    Window window2 = (dialog2 != null ? dialog2 : null).getWindow();
                    Intrinsics.checkNotNull(window2);
                    final View decorView = window2.getDecorView();
                    volumePanelMotion.getClass();
                    decorView.setTranslationX(0.0f);
                    ObjectAnimator ofFloat = ObjectAnimator.ofFloat(decorView, "alpha", decorView.getAlpha(), 1.0f);
                    ofFloat.setDuration(200L);
                    ofFloat.setInterpolator(new LinearInterpolator());
                    ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(decorView, "scaleX", 0.9f, 1.0f);
                    ofFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.volume.view.VolumePanelMotion$startVolumeDualViewShowAnimation$scaleAnimator$1$1
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
                    animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.volume.view.VolumePanelMotion$startVolumeDualViewShowAnimation$1$1
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public final void onAnimationEnd(Animator animator) {
                            VolumePanelMotion.this.storeInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_ANIMATION_FINISHED).build(), true);
                        }

                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public final void onAnimationStart(Animator animator) {
                            VolumePanelMotion.this.storeInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_ANIMATION_START).build(), true);
                        }
                    });
                    animatorSet.start();
                    if (z) {
                        volumePanelView$bind$3$showBlurRunnable$1.run();
                        return;
                    }
                    return;
                }
                if (!VolumePanelStateExt.isAODVolumePanel(volumePanelView4.getPanelState())) {
                    VolumePanelView volumePanelView5 = VolumePanelView.this;
                    VolumePanelMotion volumePanelMotion2 = volumePanelView5.volumePanelMotion;
                    if (volumePanelMotion2 == null) {
                        volumePanelMotion2 = null;
                    }
                    Dialog dialog3 = volumePanelView5.dialog;
                    Window window3 = (dialog3 != null ? dialog3 : null).getWindow();
                    Intrinsics.checkNotNull(window3);
                    View decorView2 = window3.getDecorView();
                    volumePanelMotion2.getClass();
                    SpringAnimation springAnimation = new SpringAnimation(decorView2, DynamicAnimation.TRANSLATION_X);
                    springAnimation.mSpring = ActionUpOrCancelHandler$$ExternalSyntheticOutline0.m145m(150.0f, 0.7f);
                    decorView2.setAlpha(1.0f);
                    decorView2.setScaleX(1.0f);
                    decorView2.setScaleY(1.0f);
                    decorView2.setTranslationX(BasicRune.VOLUME_LEFT_DISPLAY_VOLUME_DIALOG ? -decorView2.getWidth() : decorView2.getWidth());
                    springAnimation.mVelocity = 0.0f;
                    springAnimation.animateToFinalPosition(0.0f);
                    volumePanelMotion2.singleShowSpringAnimation = springAnimation;
                    if (z) {
                        volumePanelView$bind$3$showBlurRunnable$1.run();
                        return;
                    }
                    return;
                }
                VolumePanelView volumePanelView6 = VolumePanelView.this;
                VolumePanelMotion volumePanelMotion3 = volumePanelView6.volumePanelMotion;
                if (volumePanelMotion3 == null) {
                    volumePanelMotion3 = null;
                }
                Dialog dialog4 = volumePanelView6.dialog;
                if (dialog4 == null) {
                    dialog4 = null;
                }
                Window window4 = dialog4.getWindow();
                Intrinsics.checkNotNull(window4);
                View decorView3 = window4.getDecorView();
                volumePanelMotion3.getClass();
                decorView3.setTranslationX(0.0f);
                decorView3.setScaleX(1.0f);
                decorView3.setScaleY(1.0f);
                ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(decorView3, "alpha", decorView3.getAlpha(), 1.0f);
                ofFloat3.setDuration(100L);
                ofFloat3.setInterpolator(new LinearInterpolator());
                ofFloat3.start();
                VolumePanelView volumePanelView7 = VolumePanelView.this;
                volumePanelView7.isFirstTouch = true;
                PowerManagerWrapper powerManagerWrapper = volumePanelView7.powerManagerWrapper;
                if (powerManagerWrapper == null) {
                    powerManagerWrapper = null;
                }
                Context context = volumePanelView7.getContext();
                powerManagerWrapper.getClass();
                SystemServiceExtension.INSTANCE.getClass();
                PowerManager.WakeLock newWakeLock = ((PowerManager) context.getSystemService(PowerManager.class)).newWakeLock(1, "AOD_VolumePanel");
                newWakeLock.acquire();
                powerManagerWrapper.wakeLock = newWakeLock;
                ?? r10 = VolumePanelView.this.pluginAODManagerWrapper;
                (r10 != 0 ? r10 : null).getClass();
                PluginAODManagerWrapper.requestAODVolumePanel(true);
            }
        });
        Dialog dialog2 = volumePanelView.dialog;
        Window window2 = (dialog2 == null ? null : dialog2).getWindow();
        Intrinsics.checkNotNull(window2);
        window2.getDecorView().setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.systemui.volume.view.standard.VolumePanelView$bind$4
            @Override // android.view.View.AccessibilityDelegate
            public final boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup3, View view, AccessibilityEvent accessibilityEvent) {
                AbstractC3626xb6ef341d.m227m(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_SEND_ACCESSIBILITY_EVENT), true, VolumePanelView.this.storeInteractor, true);
                return super.onRequestSendAccessibilityEvent(viewGroup3, view, accessibilityEvent);
            }
        });
        VolumePanelMotion volumePanelMotion = volumePanelView.volumePanelMotion;
        volumePanelMotion = volumePanelMotion == null ? null : volumePanelMotion;
        Dialog dialog3 = volumePanelView.dialog;
        Window window3 = (dialog3 == null ? null : dialog3).getWindow();
        Intrinsics.checkNotNull(window3);
        View decorView = window3.getDecorView();
        volumePanelMotion.getClass();
        volumePanelView.touchUpAnimation = VolumePanelMotion.getSeekBarTouchUpAnimation(decorView);
        VolumePanelMotion volumePanelMotion2 = volumePanelView.volumePanelMotion;
        volumePanelMotion2 = volumePanelMotion2 == null ? null : volumePanelMotion2;
        Dialog dialog4 = volumePanelView.dialog;
        Window window4 = (dialog4 == null ? null : dialog4).getWindow();
        Intrinsics.checkNotNull(window4);
        View decorView2 = window4.getDecorView();
        volumePanelMotion2.getClass();
        volumePanelView.touchDownAnimation = VolumePanelMotion.getSeekBarTouchDownAnimation(decorView2);
        VolumePanelMotion volumePanelMotion3 = volumePanelView.volumePanelMotion;
        volumePanelMotion3 = volumePanelMotion3 == null ? null : volumePanelMotion3;
        Dialog dialog5 = volumePanelView.dialog;
        Window window5 = (dialog5 == null ? null : dialog5).getWindow();
        Intrinsics.checkNotNull(window5);
        View decorView3 = window5.getDecorView();
        volumePanelMotion3.getClass();
        volumePanelView.keyDownAnimation = VolumePanelMotion.getSeekBarKeyDownAnimation(decorView3);
        VolumePanelMotion volumePanelMotion4 = volumePanelView.volumePanelMotion;
        volumePanelMotion4 = volumePanelMotion4 == null ? null : volumePanelMotion4;
        Dialog dialog6 = volumePanelView.dialog;
        Window window6 = (dialog6 != null ? dialog6 : null).getWindow();
        Intrinsics.checkNotNull(window6);
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
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0040, code lost:
    
        if ((r1.isNavigationBarGestureHintEnabled() || r1.isNavigationBarGestureWhileHidden()) == false) goto L16;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int getBaseHeight$1() {
        int displayHeight = ContextUtils.getDisplayHeight(getContext());
        boolean z = true;
        if (!this.systemConfig.isTablet() && !ContextUtils.isScreenWideMobileDevice(getContext())) {
            if (ContextUtils.isLandscape(getContext())) {
                SettingsHelper settingsHelper = (SettingsHelper) Dependency.get(SettingsHelper.class);
                int i = SettingsHelperExt.$r8$clinit;
            }
            if (ContextUtils.isLandscape(getContext())) {
                z = false;
            }
        }
        return z ? displayHeight + ContextUtils.getDimenInt(R.dimen.navigation_bar_height, getContext()) : displayHeight;
    }

    public final float getSeekBarY(int i) {
        float f;
        float dimenInt = i - ContextUtils.getDimenInt(R.dimen.volume_seekbar_height, getContext());
        if (!this.systemConfig.isTablet() && ContextUtils.isScreenWideMobileDevice(getContext()) && ContextUtils.isLandscape(getContext())) {
            dimenInt -= i / 2.0f;
        } else if (!this.systemConfig.isTablet() && !ContextUtils.isLandscape(getContext())) {
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
        return dimenInt / 2.0f;
    }

    public final void observeStore() {
        ((StoreInteractor) this.storeInteractor$delegate.getValue()).observeStore();
        VolumePanelView volumePanelView = this.panelView;
        volumePanelView.storeInteractor.observeStore();
        VolumePanelMotion volumePanelMotion = volumePanelView.volumePanelMotion;
        if (volumePanelMotion == null) {
            volumePanelMotion = null;
        }
        VolumePanelStore store = volumePanelView.getStore();
        Context context = volumePanelView.getContext();
        volumePanelMotion.storeInteractor.store = store;
        volumePanelMotion.context = context;
    }

    /* JADX WARN: Removed duplicated region for block: B:118:0x02c4  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x02d4  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x02fb  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x0309  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x030b  */
    /* JADX WARN: Removed duplicated region for block: B:141:0x035d  */
    /* JADX WARN: Removed duplicated region for block: B:155:0x03cc  */
    /* JADX WARN: Removed duplicated region for block: B:165:0x0406  */
    /* JADX WARN: Removed duplicated region for block: B:168:0x0419  */
    /* JADX WARN: Removed duplicated region for block: B:171:0x047b  */
    /* JADX WARN: Removed duplicated region for block: B:174:0x0487  */
    /* JADX WARN: Removed duplicated region for block: B:183:0x04b6  */
    /* JADX WARN: Removed duplicated region for block: B:202:0x050e  */
    /* JADX WARN: Removed duplicated region for block: B:212:0x052e  */
    /* JADX WARN: Removed duplicated region for block: B:221:0x0557  */
    /* JADX WARN: Removed duplicated region for block: B:225:0x0559  */
    /* JADX WARN: Removed duplicated region for block: B:228:0x04fb  */
    /* JADX WARN: Removed duplicated region for block: B:231:0x049d  */
    /* JADX WARN: Removed duplicated region for block: B:238:0x042e  */
    /* JADX WARN: Removed duplicated region for block: B:247:0x0409  */
    @Override // com.samsung.systemui.splugins.volume.VolumeObserver
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onChanged(Object obj) {
        boolean z;
        ViewGroup viewGroup;
        int dimenInt;
        int dimenInt2;
        boolean isLandscape;
        SystemConfigImpl systemConfigImpl;
        boolean isTablet;
        int dimenInt3;
        int dimenInt4;
        int dimenInt5;
        boolean isLandscape2;
        SystemConfigImpl systemConfigImpl2;
        boolean isTablet2;
        int i;
        Pair pair;
        SystemConfigImpl systemConfigImpl3;
        ViewGroup viewGroup2;
        boolean z2;
        Dialog dialog;
        Window window;
        ImageButton imageButton;
        VolumePanelRow findRow;
        View decorView;
        int i2;
        float f;
        float f2;
        int paddingTop;
        VolumePanelState volumePanelState = (VolumePanelState) obj;
        switch (WhenMappings.$EnumSwitchMapping$0[volumePanelState.getStateType().ordinal()]) {
            case 1:
                Window window2 = getWindow();
                if (window2 != null) {
                    WindowManager.LayoutParams attributes = window2.getAttributes();
                    if (VolumePanelStateExt.isAODVolumePanel(((VolumePanelStore) this.store$delegate.getValue()).currentState)) {
                        attributes.type = 2021;
                        attributes.semAddExtensionFlags(262144);
                        attributes.y = 0;
                    } else {
                        attributes.x = 0;
                        attributes.y = ((VolumePanelStore) this.store$delegate.getValue()).currentState.isDualAudio() ? (int) ((getSeekBarY(getBaseHeight$1()) - ContextUtils.getDimenInt(R.dimen.volume_panel_dual_view_elevation_padding_vertical, getContext())) - ContextUtils.getDimenInt(R.dimen.volume_panel_dual_view_top_padding, getContext())) : (int) (getSeekBarY(getBaseHeight$1()) - ContextUtils.getDimenInt(R.dimen.volume_seekbar_elevation_padding, getContext()));
                        attributes.setFitInsetsTypes(WindowInsets.Type.navigationBars());
                        attributes.type = 2020;
                        attributes.semClearExtensionFlags(262144);
                    }
                    attributes.gravity = (BasicRune.VOLUME_LEFT_DISPLAY_VOLUME_DIALOG ? 3 : 5) | 48;
                    if (this.infraMediator.isSupportTvVolumeSync()) {
                        attributes.semAddExtensionFlags(VideoPlayer.MEDIA_ERROR_SYSTEM);
                    } else {
                        attributes.semClearExtensionFlags(VideoPlayer.MEDIA_ERROR_SYSTEM);
                    }
                    window2.setAttributes(attributes);
                    Unit unit = Unit.INSTANCE;
                }
                final VolumePanelView volumePanelView = this.panelView;
                VolumePanelState volumePanelState2 = ((VolumePanelStore) this.store$delegate.getValue()).currentState;
                volumePanelView.getClass();
                volumePanelView.isLockscreen = volumePanelState2.isLockscreen();
                volumePanelView.isDualViewEnabled = VolumePanelStateExt.isDualViewEnabled(volumePanelState2);
                HandlerWrapper handlerWrapper = volumePanelView.handlerWrapper;
                if (handlerWrapper == null) {
                    handlerWrapper = null;
                }
                IDisplayManagerWrapper iDisplayManagerWrapper = volumePanelView.iDisplayManagerWrapper;
                if (iDisplayManagerWrapper == null) {
                    iDisplayManagerWrapper = null;
                }
                ((Handler) handlerWrapper.mainThreadHandler$delegate.getValue()).post(iDisplayManagerWrapper.refreshRateLimitOnRunnable);
                if (VolumePanelStateExt.isAODVolumePanel(volumePanelState2)) {
                    ViewGroup viewGroup3 = volumePanelView.volumePanelView;
                    if (viewGroup3 == null) {
                        viewGroup3 = null;
                    }
                    volumePanelView.rowContainer = (ViewGroup) viewGroup3.findViewById(R.id.volume_panel_row_container);
                    ViewGroup viewGroup4 = volumePanelView.volumePanelView;
                    if (viewGroup4 == null) {
                        viewGroup4 = null;
                    }
                    volumePanelView.expandButton = (ImageView) viewGroup4.findViewById(R.id.volume_panel_expand_button);
                } else if (volumePanelView.isDualViewEnabled) {
                    ViewGroup viewGroup5 = volumePanelView.volumePanelDualView;
                    if (viewGroup5 == null) {
                        viewGroup5 = null;
                    }
                    volumePanelView.rowContainer = (ViewGroup) viewGroup5.findViewById(R.id.volume_panel_row_container);
                    ViewGroup viewGroup6 = volumePanelView.volumePanelDualView;
                    if (viewGroup6 == null) {
                        viewGroup6 = null;
                    }
                    volumePanelView.expandButton = (ImageView) viewGroup6.findViewById(R.id.volume_panel_expand_button);
                    ViewGroup viewGroup7 = volumePanelView.volumePanelDualView;
                    if (viewGroup7 == null) {
                        viewGroup7 = null;
                    }
                    volumePanelView.dualViewTitle = (TextView) viewGroup7.findViewById(R.id.volume_panel_dual_view_title);
                } else {
                    ViewGroup viewGroup8 = volumePanelView.volumePanelView;
                    if (viewGroup8 == null) {
                        viewGroup8 = null;
                    }
                    volumePanelView.rowContainer = (ViewGroup) viewGroup8.findViewById(R.id.volume_panel_row_container);
                    ViewGroup viewGroup9 = volumePanelView.volumePanelView;
                    if (viewGroup9 == null) {
                        viewGroup9 = null;
                    }
                    volumePanelView.expandButton = (ImageView) viewGroup9.findViewById(R.id.volume_panel_expand_button);
                }
                if (VolumePanelStateExt.isAODVolumePanel(volumePanelState2)) {
                    ViewGroup viewGroup10 = volumePanelView.volumeAODPanelView;
                    if (viewGroup10 == null) {
                        viewGroup10 = null;
                    }
                    ViewGroup.LayoutParams layoutParams = viewGroup10.getLayoutParams();
                    layoutParams.width = ContextUtils.getDisplayWidth(volumePanelView.getContext());
                    layoutParams.height = ContextUtils.getDisplayHeight(volumePanelView.getContext());
                    ViewGroup viewGroup11 = volumePanelView.volumeAODPanelView;
                    if (viewGroup11 == null) {
                        viewGroup11 = null;
                    }
                    viewGroup11.setLayoutParams(layoutParams);
                    ViewGroup viewGroup12 = volumePanelView.volumePanelView;
                    if (viewGroup12 == null) {
                        viewGroup12 = null;
                    }
                    Dialog dialog2 = volumePanelView.dialog;
                    if (dialog2 == null) {
                        dialog2 = null;
                    }
                    Window window3 = dialog2.getWindow();
                    Intrinsics.checkNotNull(window3);
                    viewGroup12.setPadding(0, window3.getAttributes().y, 0, 0);
                } else {
                    ViewGroup viewGroup13 = volumePanelView.volumePanelView;
                    if (viewGroup13 == null) {
                        viewGroup13 = null;
                    }
                    viewGroup13.setPadding(0, 0, 0, 0);
                }
                volumePanelView.initViewVisibility(volumePanelState2);
                if (VolumePanelStateExt.isAODVolumePanel(volumePanelState2)) {
                    VolumePanelRow findRow2 = VolumePanelStateExt.INSTANCE.findRow(volumePanelState2, volumePanelState2.getActiveStream());
                    if (findRow2 != null) {
                        volumePanelView.currentVolume = findRow2.getRealLevel();
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
                    ViewGroup viewGroup14 = volumePanelView.volumePanelDualView;
                    if (viewGroup14 == null) {
                        viewGroup14 = null;
                    }
                    ((ViewGroup) viewGroup14.findViewById(R.id.volume_panel_dual_view_background)).setBackground(((SettingsHelper) Dependency.get(SettingsHelper.class)).isReduceTransparencyEnabled() ? volumePanelView.getContext().getDrawable(R.drawable.volume_panel_expand_bg_reduce_transparency) : (BasicRune.VOLUME_PARTIAL_BLUR || BasicRune.VOLUME_CAPTURED_BLUR) ? volumePanelView.getContext().getDrawable(R.drawable.volume_panel_expand_bg_blur) : volumePanelView.getContext().getDrawable(R.drawable.volume_panel_expand_bg));
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
                        AbstractC3626xb6ef341d.m227m(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_EXPAND_BUTTON_CLICKED), true, VolumePanelView.this.storeInteractor, false);
                    }
                });
                ImageView imageView3 = volumePanelView.expandButton;
                if (imageView3 == null) {
                    imageView3 = null;
                }
                imageView3.setClickable(volumePanelState2.isShowA11yStream());
                volumePanelView.addVolumeRows(volumePanelState2);
                Dialog dialog3 = volumePanelView.dialog;
                if (dialog3 == null) {
                    dialog3 = null;
                }
                Window window4 = dialog3.getWindow();
                Intrinsics.checkNotNull(window4);
                window4.getDecorView().setAlpha(0.0f);
                Dialog dialog4 = volumePanelView.dialog;
                (dialog4 == null ? null : dialog4).show();
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
                if (!(z3 && volumePanelState.isFolded())) {
                    VolumePanelExpandWindow volumePanelExpandWindow = new VolumePanelExpandWindow(this.volDeps);
                    Window window5 = volumePanelExpandWindow.getWindow();
                    if (window5 != null) {
                        WindowManager.LayoutParams attributes2 = window5.getAttributes();
                        attributes2.gravity = (BasicRune.VOLUME_LEFT_DISPLAY_VOLUME_DIALOG ? 3 : 5) | 48;
                        attributes2.x = 0;
                        if (volumePanelExpandWindow.systemConfig.isTablet() || !ContextUtils.isLandscape(volumePanelExpandWindow.getContext())) {
                            float displayHeight = ContextUtils.getDisplayHeight(volumePanelExpandWindow.getContext()) - ContextUtils.getDimenInt(R.dimen.volume_seekbar_height, volumePanelExpandWindow.getContext());
                            if (volumePanelExpandWindow.systemConfig.isTablet()) {
                                f2 = displayHeight / 2.0f;
                            } else {
                                if (BasicRune.FOLDABLE_TYPE_FLIP) {
                                    VolumePanelLayout.INSTANCE.getClass();
                                    f = VolumePanelLayout.VERTICAL_PADDING_TOP_FOR_FLIP_RATIO;
                                } else if (ContextUtils.isScreenWideMobileDevice(volumePanelExpandWindow.getContext())) {
                                    VolumePanelLayout.INSTANCE.getClass();
                                    f = VolumePanelLayout.VERTICAL_WIDE_SCREEN_TOP_RATIO;
                                } else {
                                    VolumePanelLayout.INSTANCE.getClass();
                                    f = VolumePanelLayout.VERTICAL_PADDING_TOP_RATIO;
                                }
                                f2 = displayHeight * f;
                            }
                            paddingTop = (int) ((f2 - volumePanelExpandWindow.panelView.getPaddingTop()) - ContextUtils.getDimenInt(R.dimen.volume_panel_expand_row_container_margin_top, volumePanelExpandWindow.getContext()));
                        } else {
                            int displayHeight2 = (((ContextUtils.isScreenWideMobileDevice(volumePanelExpandWindow.getContext()) ? ContextUtils.getDisplayHeight(volumePanelExpandWindow.getContext()) / 2 : ContextUtils.getDisplayHeight(volumePanelExpandWindow.getContext())) - (volumePanelExpandWindow.getPanelState().isZenMode() || volumePanelExpandWindow.getPanelState().isAllSoundOff() || volumePanelExpandWindow.getPanelState().isLeBroadcasting() ? ContextUtils.getDimenInt(R.dimen.volume_panel_expand_height_with_msg, volumePanelExpandWindow.getContext()) : ContextUtils.getDimenInt(R.dimen.volume_panel_expand_height, volumePanelExpandWindow.getContext()))) / 2) - volumePanelExpandWindow.panelView.getPaddingTop();
                            paddingTop = displayHeight2 < 0 ? 0 : displayHeight2;
                        }
                        attributes2.y = paddingTop;
                        attributes2.setFitInsetsTypes(WindowInsets.Type.navigationBars());
                        window5.setAttributes(attributes2);
                        Unit unit2 = Unit.INSTANCE;
                    }
                    VolumePanelState panelState = volumePanelExpandWindow.getPanelState();
                    Window window6 = volumePanelExpandWindow.getWindow();
                    if (window6 != null) {
                        WindowManager.LayoutParams attributes3 = window6.getAttributes();
                        if (panelState.isShowA11yStream()) {
                            attributes3.flags &= -9;
                        }
                        window6.setAttributes(attributes3);
                        Unit unit3 = Unit.INSTANCE;
                    }
                    final VolumePanelExpandView volumePanelExpandView = volumePanelExpandWindow.panelView;
                    VolumePanelState panelState2 = volumePanelExpandWindow.getPanelState();
                    volumePanelExpandView.addRows(panelState2);
                    Space space = (Space) volumePanelExpandView.findViewById(R.id.volume_panel_expand_bottom_space);
                    ViewGroup viewGroup15 = (ViewGroup) volumePanelExpandView.findViewById(R.id.volume_panel_status_message_layout);
                    TextView textView2 = (TextView) volumePanelExpandView.findViewById(R.id.volume_panel_status_message_description);
                    ImageView imageView4 = (ImageView) volumePanelExpandView.findViewById(R.id.volume_panel_status_message_icon);
                    imageView4.setImageTintList(ColorUtils.getSingleColorStateList(R.color.volume_panel_status_message_color, volumePanelExpandView.getContext()));
                    boolean z4 = panelState2.isAllSoundOff() || panelState2.isZenMode() || panelState2.isLeBroadcasting();
                    ViewVisibilityUtil viewVisibilityUtil = ViewVisibilityUtil.INSTANCE;
                    viewVisibilityUtil.getClass();
                    if (z4) {
                        ViewVisibilityUtil.setGone(space);
                    } else {
                        space.setVisibility(0);
                    }
                    if (z4) {
                        viewVisibilityUtil.getClass();
                        viewGroup15.setVisibility(0);
                    } else {
                        viewVisibilityUtil.getClass();
                        ViewVisibilityUtil.setGone(viewGroup15);
                    }
                    if (z4) {
                        textView2.setText(volumePanelExpandView.getContext().getString(panelState2.isAllSoundOff() ? R.string.volume_mute_all_sounds_on : panelState2.isZenMode() ? R.string.volume_zen_mode_on : R.string.volume_panel_broadcasting_sound_using_auracast));
                        if (panelState2.isAllSoundOff() || panelState2.isZenMode()) {
                            imageView4.setImageDrawable(volumePanelExpandView.getContext().getResources().getDrawable(R.drawable.ic_volume_control_dnd, null));
                            viewGroup15.setOnClickListener(panelState2.isAllSoundOff() ? new View.OnClickListener() { // from class: com.android.systemui.volume.view.expand.VolumePanelExpandView$updateStatusMsgArea$1
                                @Override // android.view.View.OnClickListener
                                public final void onClick(View view) {
                                    AbstractC3626xb6ef341d.m227m(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_STATUS_MESSAGE_CLICKED), true, VolumePanelExpandView.this.storeInteractor, false);
                                }
                            } : null);
                        } else if (panelState2.isLeBroadcasting()) {
                            imageView4.setImageDrawable(volumePanelExpandView.getContext().getResources().getDrawable(R.drawable.ic_auracast, null));
                            viewGroup15.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.volume.view.expand.VolumePanelExpandView$updateStatusMsgArea$2
                                @Override // android.view.View.OnClickListener
                                public final void onClick(View view) {
                                    AbstractC3626xb6ef341d.m227m(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_STATUS_LE_BROADCASTING_MESSAGE_CLICKED), true, VolumePanelExpandView.this.storeInteractor, false);
                                }
                            });
                        }
                    }
                    int displayWidth = ContextUtils.getDisplayWidth(volumePanelExpandView.getContext());
                    int displayHeight3 = ContextUtils.getDisplayHeight(volumePanelExpandView.getContext());
                    boolean z5 = BasicRune.VOLUME_FOLDABLE_WIDE_SCREEN_VOLUME_DIALOG && ((ContextUtils.isLandscape(volumePanelExpandView.getContext()) ? displayHeight3 : displayWidth) > ContextUtils.getDimenInt(R.dimen.volume_panel_screen_width_threshold, volumePanelExpandView.getContext()));
                    boolean z6 = ((SettingsHelper) Dependency.get(SettingsHelper.class)).isNavigationBarGestureHintEnabled() && ((SettingsHelper) Dependency.get(SettingsHelper.class)).isNavigationBarGestureWhileHidden();
                    SystemConfigImpl systemConfigImpl4 = volumePanelExpandView.systemConfig;
                    if (systemConfigImpl4 == null) {
                        systemConfigImpl4 = null;
                    }
                    if (!systemConfigImpl4.isTablet() && !z5 && (!ContextUtils.isLandscape(volumePanelExpandView.getContext()) || !z6)) {
                        ContextUtils contextUtils = ContextUtils.INSTANCE;
                        Context context = volumePanelExpandView.getContext();
                        contextUtils.getClass();
                        if (!(context.getResources().getConfiguration().orientation == 1)) {
                            z = false;
                            if (z) {
                                displayHeight3 += ContextUtils.getDimenInt(android.R.dimen.notification_custom_view_max_image_height_low_ram, volumePanelExpandView.getContext());
                            }
                            viewGroup = volumePanelExpandView.contentsView;
                            if (viewGroup == null) {
                                viewGroup = null;
                            }
                            ViewGroup.LayoutParams layoutParams2 = viewGroup.getLayoutParams();
                            dimenInt = ContextUtils.getDimenInt(R.dimen.volume_expand_panel_horizontal_padding_min, volumePanelExpandView.getContext());
                            dimenInt2 = ContextUtils.getDimenInt(R.dimen.volume_panel_expand_width, volumePanelExpandView.getContext());
                            isLandscape = ContextUtils.isLandscape(volumePanelExpandView.getContext());
                            systemConfigImpl = volumePanelExpandView.systemConfig;
                            if (systemConfigImpl == null) {
                                systemConfigImpl = null;
                            }
                            isTablet = systemConfigImpl.isTablet();
                            VolumePanelLayout volumePanelLayout = VolumePanelLayout.INSTANCE;
                            if (!isTablet && !z5) {
                                dimenInt2 = (!isLandscape ? displayHeight3 : displayWidth) - (dimenInt * 2);
                            }
                            layoutParams2.width = dimenInt2;
                            dimenInt3 = ContextUtils.getDimenInt(R.dimen.volume_expand_panel_horizontal_padding, volumePanelExpandView.getContext());
                            dimenInt4 = ContextUtils.getDimenInt(R.dimen.volume_expand_panel_vertical_padding, volumePanelExpandView.getContext());
                            int dimenInt6 = ContextUtils.getDimenInt(R.dimen.volume_panel_expand_height, volumePanelExpandView.getContext());
                            dimenInt5 = ContextUtils.getDimenInt(R.dimen.volume_panel_expand_height_with_msg, volumePanelExpandView.getContext());
                            boolean z7 = !panelState2.isZenMode() || panelState2.isAllSoundOff() || panelState2.isLeBroadcasting();
                            isLandscape2 = ContextUtils.isLandscape(volumePanelExpandView.getContext());
                            int i3 = layoutParams2.width;
                            systemConfigImpl2 = volumePanelExpandView.systemConfig;
                            if (systemConfigImpl2 == null) {
                                systemConfigImpl2 = null;
                            }
                            isTablet2 = systemConfigImpl2.isTablet();
                            if (isTablet2 && z5 && isLandscape2) {
                                i = dimenInt5;
                                float f3 = ((displayHeight3 / 2.0f) - (z7 ? dimenInt5 : dimenInt6)) / 2;
                                if (f3 < 0.0f) {
                                    f3 = 0.0f;
                                }
                                pair = new Pair(Integer.valueOf((int) f3), Integer.valueOf(dimenInt4));
                            } else {
                                i = dimenInt5;
                                if (isTablet2 && !z5 && isLandscape2) {
                                    int i4 = (displayHeight3 - (z7 ? i : dimenInt6)) / 2;
                                    if (i4 <= dimenInt4) {
                                        dimenInt4 = i4;
                                    }
                                    pair = new Pair(Integer.valueOf(dimenInt4), Integer.valueOf(dimenInt4));
                                } else {
                                    pair = new Pair(Integer.valueOf(dimenInt4), Integer.valueOf(dimenInt4));
                                }
                            }
                            Integer num = (Integer) pair.first;
                            Integer num2 = (Integer) pair.second;
                            systemConfigImpl3 = volumePanelExpandView.systemConfig;
                            if (systemConfigImpl3 == null) {
                                systemConfigImpl3 = null;
                            }
                            boolean isTablet3 = systemConfigImpl3.isTablet();
                            if (!isLandscape2 && !z5 && !isTablet3 && (i2 = (displayWidth - i3) / 2) <= dimenInt3) {
                                dimenInt3 = i2;
                            }
                            volumePanelExpandView.setPadding(dimenInt3, num.intValue(), dimenInt3, num2.intValue());
                            int i5 = layoutParams2.width;
                            ViewGroup viewGroup16 = (ViewGroup) volumePanelExpandView.findViewById(R.id.volume_panel_expand_view_background);
                            ViewGroup viewGroup17 = (ViewGroup) volumePanelExpandView.findViewById(R.id.volume_panel_expand_view_background_stroke);
                            ViewGroup.LayoutParams layoutParams3 = viewGroup16.getLayoutParams();
                            layoutParams3.width = i5;
                            layoutParams3.height = !z7 ? i : dimenInt6;
                            if (!((SettingsHelper) Dependency.get(SettingsHelper.class)).isReduceTransparencyEnabled()) {
                                viewGroup16.setBackground(volumePanelExpandView.getContext().getDrawable(R.drawable.volume_panel_expand_bg_reduce_transparency));
                                viewVisibilityUtil.getClass();
                                ViewVisibilityUtil.setGone(viewGroup17);
                            } else if (BasicRune.VOLUME_PARTIAL_BLUR || BasicRune.VOLUME_CAPTURED_BLUR) {
                                viewGroup16.setBackground(volumePanelExpandView.getContext().getDrawable(R.drawable.volume_panel_expand_bg_blur));
                                boolean isNightMode = ContextUtils.isNightMode(volumePanelExpandView.getContext());
                                viewVisibilityUtil.getClass();
                                if (isNightMode) {
                                    ViewVisibilityUtil.setGone(viewGroup17);
                                } else {
                                    viewGroup17.setVisibility(0);
                                }
                            } else {
                                viewGroup16.setBackground(volumePanelExpandView.getContext().getDrawable(R.drawable.volume_panel_expand_bg));
                                viewVisibilityUtil.getClass();
                                ViewVisibilityUtil.setGone(viewGroup17);
                            }
                            volumePanelExpandView.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.volume.view.expand.VolumePanelExpandView$adjustTouchEventForOutsideTouch$1
                                @Override // android.view.View.OnTouchListener
                                public final boolean onTouch(View view, MotionEvent motionEvent) {
                                    AbstractC3626xb6ef341d.m227m(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_TOUCH_OUTSIDE), true, VolumePanelExpandView.this.storeInteractor, true);
                                    return true;
                                }
                            });
                            viewGroup2 = volumePanelExpandView.contentsView;
                            if (viewGroup2 == null) {
                                viewGroup2 = null;
                            }
                            viewGroup2.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.volume.view.expand.VolumePanelExpandView$adjustTouchEventForOutsideTouch$2
                                @Override // android.view.View.OnTouchListener
                                public final boolean onTouch(View view, MotionEvent motionEvent) {
                                    return true;
                                }
                            });
                            if (panelState2.isSetupWizardComplete()) {
                                z2 = true;
                                ImageButton imageButton2 = volumePanelExpandView.settingButton;
                                if (imageButton2 == null) {
                                    imageButton2 = null;
                                }
                                viewVisibilityUtil.getClass();
                                ViewVisibilityUtil.setGone(imageButton2);
                                ImageButton imageButton3 = volumePanelExpandView.settingButton;
                                if (imageButton3 == null) {
                                    imageButton3 = null;
                                }
                                imageButton3.setEnabled(false);
                            } else {
                                ImageButton imageButton4 = volumePanelExpandView.settingButton;
                                if (imageButton4 == null) {
                                    imageButton4 = null;
                                }
                                viewVisibilityUtil.getClass();
                                imageButton4.setVisibility(0);
                                ImageButton imageButton5 = volumePanelExpandView.settingButton;
                                if (imageButton5 == null) {
                                    imageButton5 = null;
                                }
                                z2 = true;
                                imageButton5.setEnabled(true);
                            }
                            dialog = volumePanelExpandView.dialog;
                            if (dialog == null) {
                                dialog = null;
                            }
                            window = dialog.getWindow();
                            if (window != null && (decorView = window.getDecorView()) != null) {
                                decorView.setAlpha(0.0f);
                                decorView.setScaleX(0.95f);
                                decorView.setScaleY(0.95f);
                            }
                            boolean z8 = (panelState2.isFolded() || !z3) ? false : z2;
                            if (panelState2.isCaptionComponentEnabled() || z8) {
                                imageButton = volumePanelExpandView.liveCaptionButton;
                                if (imageButton == null) {
                                    imageButton = null;
                                }
                                viewVisibilityUtil.getClass();
                                ViewVisibilityUtil.setGone(imageButton);
                            } else {
                                ImageButton imageButton6 = volumePanelExpandView.liveCaptionButton;
                                if (imageButton6 == null) {
                                    imageButton6 = null;
                                }
                                viewVisibilityUtil.getClass();
                                imageButton6.setVisibility(0);
                                volumePanelExpandView.toggleLiveCaptionButton(panelState2.isCaptionEnabled());
                            }
                            findRow = VolumePanelStateExt.INSTANCE.findRow(panelState2, panelState2.getActiveStream());
                            if (findRow != null) {
                                if (!(findRow.getRemoteLabel().length() > 0 ? z2 : false)) {
                                    findRow = null;
                                }
                                if (findRow != null) {
                                    volumePanelExpandView.updateVolumeTitle(findRow.getStreamType());
                                }
                            }
                            if (panelState2.isShowA11yStream()) {
                                Dialog dialog5 = volumePanelExpandView.dialog;
                                if (dialog5 == null) {
                                    dialog5 = null;
                                }
                                Window window7 = dialog5.getWindow();
                                Intrinsics.checkNotNull(window7);
                                WindowManager.LayoutParams attributes4 = window7.getAttributes();
                                attributes4.flags &= -9;
                                Dialog dialog6 = volumePanelExpandView.dialog;
                                if (dialog6 == null) {
                                    dialog6 = null;
                                }
                                Window window8 = dialog6.getWindow();
                                Intrinsics.checkNotNull(window8);
                                window8.setAttributes(attributes4);
                            }
                            Dialog dialog7 = volumePanelExpandView.dialog;
                            (dialog7 != null ? null : dialog7).show();
                            break;
                        }
                    }
                    z = true;
                    if (z) {
                    }
                    viewGroup = volumePanelExpandView.contentsView;
                    if (viewGroup == null) {
                    }
                    ViewGroup.LayoutParams layoutParams22 = viewGroup.getLayoutParams();
                    dimenInt = ContextUtils.getDimenInt(R.dimen.volume_expand_panel_horizontal_padding_min, volumePanelExpandView.getContext());
                    dimenInt2 = ContextUtils.getDimenInt(R.dimen.volume_panel_expand_width, volumePanelExpandView.getContext());
                    isLandscape = ContextUtils.isLandscape(volumePanelExpandView.getContext());
                    systemConfigImpl = volumePanelExpandView.systemConfig;
                    if (systemConfigImpl == null) {
                    }
                    isTablet = systemConfigImpl.isTablet();
                    VolumePanelLayout volumePanelLayout2 = VolumePanelLayout.INSTANCE;
                    if (!isTablet) {
                        dimenInt2 = (!isLandscape ? displayHeight3 : displayWidth) - (dimenInt * 2);
                    }
                    layoutParams22.width = dimenInt2;
                    dimenInt3 = ContextUtils.getDimenInt(R.dimen.volume_expand_panel_horizontal_padding, volumePanelExpandView.getContext());
                    dimenInt4 = ContextUtils.getDimenInt(R.dimen.volume_expand_panel_vertical_padding, volumePanelExpandView.getContext());
                    int dimenInt62 = ContextUtils.getDimenInt(R.dimen.volume_panel_expand_height, volumePanelExpandView.getContext());
                    dimenInt5 = ContextUtils.getDimenInt(R.dimen.volume_panel_expand_height_with_msg, volumePanelExpandView.getContext());
                    if (panelState2.isZenMode()) {
                    }
                    isLandscape2 = ContextUtils.isLandscape(volumePanelExpandView.getContext());
                    int i32 = layoutParams22.width;
                    systemConfigImpl2 = volumePanelExpandView.systemConfig;
                    if (systemConfigImpl2 == null) {
                    }
                    isTablet2 = systemConfigImpl2.isTablet();
                    if (isTablet2) {
                    }
                    i = dimenInt5;
                    if (isTablet2) {
                    }
                    pair = new Pair(Integer.valueOf(dimenInt4), Integer.valueOf(dimenInt4));
                    Integer num3 = (Integer) pair.first;
                    Integer num22 = (Integer) pair.second;
                    systemConfigImpl3 = volumePanelExpandView.systemConfig;
                    if (systemConfigImpl3 == null) {
                    }
                    boolean isTablet32 = systemConfigImpl3.isTablet();
                    if (!isLandscape2) {
                        dimenInt3 = i2;
                    }
                    volumePanelExpandView.setPadding(dimenInt3, num3.intValue(), dimenInt3, num22.intValue());
                    int i52 = layoutParams22.width;
                    ViewGroup viewGroup162 = (ViewGroup) volumePanelExpandView.findViewById(R.id.volume_panel_expand_view_background);
                    ViewGroup viewGroup172 = (ViewGroup) volumePanelExpandView.findViewById(R.id.volume_panel_expand_view_background_stroke);
                    ViewGroup.LayoutParams layoutParams32 = viewGroup162.getLayoutParams();
                    layoutParams32.width = i52;
                    layoutParams32.height = !z7 ? i : dimenInt62;
                    if (!((SettingsHelper) Dependency.get(SettingsHelper.class)).isReduceTransparencyEnabled()) {
                    }
                    volumePanelExpandView.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.volume.view.expand.VolumePanelExpandView$adjustTouchEventForOutsideTouch$1
                        @Override // android.view.View.OnTouchListener
                        public final boolean onTouch(View view, MotionEvent motionEvent) {
                            AbstractC3626xb6ef341d.m227m(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_TOUCH_OUTSIDE), true, VolumePanelExpandView.this.storeInteractor, true);
                            return true;
                        }
                    });
                    viewGroup2 = volumePanelExpandView.contentsView;
                    if (viewGroup2 == null) {
                    }
                    viewGroup2.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.volume.view.expand.VolumePanelExpandView$adjustTouchEventForOutsideTouch$2
                        @Override // android.view.View.OnTouchListener
                        public final boolean onTouch(View view, MotionEvent motionEvent) {
                            return true;
                        }
                    });
                    if (panelState2.isSetupWizardComplete()) {
                    }
                    dialog = volumePanelExpandView.dialog;
                    if (dialog == null) {
                    }
                    window = dialog.getWindow();
                    if (window != null) {
                        decorView.setAlpha(0.0f);
                        decorView.setScaleX(0.95f);
                        decorView.setScaleY(0.95f);
                    }
                    if (panelState2.isFolded()) {
                    }
                    if (panelState2.isCaptionComponentEnabled()) {
                    }
                    imageButton = volumePanelExpandView.liveCaptionButton;
                    if (imageButton == null) {
                    }
                    viewVisibilityUtil.getClass();
                    ViewVisibilityUtil.setGone(imageButton);
                    findRow = VolumePanelStateExt.INSTANCE.findRow(panelState2, panelState2.getActiveStream());
                    if (findRow != null) {
                    }
                    if (panelState2.isShowA11yStream()) {
                    }
                    Dialog dialog72 = volumePanelExpandView.dialog;
                    (dialog72 != null ? null : dialog72).show();
                }
                break;
        }
    }

    @Override // android.app.Dialog
    public final void onStart() {
        super.onStart();
        this.log.m98d("VolumePanelWindow", "onStart");
    }

    @Override // android.app.Dialog
    public final void onStop() {
        super.onStop();
        this.log.m98d("VolumePanelWindow", "onStop : panelState.isExpanded=" + ((VolumePanelStore) this.store$delegate.getValue()).currentState.isExpanded());
        VolumePanelView volumePanelView = this.panelView;
        HandlerWrapper handlerWrapper = volumePanelView.handlerWrapper;
        if (handlerWrapper == null) {
            handlerWrapper = null;
        }
        IDisplayManagerWrapper iDisplayManagerWrapper = volumePanelView.iDisplayManagerWrapper;
        if (iDisplayManagerWrapper == null) {
            iDisplayManagerWrapper = null;
        }
        ((Handler) handlerWrapper.mainThreadHandler$delegate.getValue()).post(iDisplayManagerWrapper.refreshRateLimitOffRunnable);
        if (VolumePanelStateExt.isAODVolumePanel(volumePanelView.getPanelState())) {
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
        if (((VolumePanelStore) this.store$delegate.getValue()).currentState.isExpanded()) {
            return;
        }
        ((StoreInteractor) this.storeInteractor$delegate.getValue()).sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_DISMISS_VOLUME_PANEL).build(), true);
    }
}
