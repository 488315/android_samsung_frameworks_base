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
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.LinearInterpolator;
import android.view.animation.PathInterpolator;
import android.widget.ImageView;
import com.android.keyguard.SecLockIconView$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.volume.VolumeDependency;
import com.android.systemui.volume.VolumeDependencyBase;
import com.android.systemui.volume.config.SystemConfigImpl;
import com.android.systemui.volume.config.VolumeConfigs;
import com.android.systemui.volume.store.StoreInteractor;
import com.android.systemui.volume.store.VolumePanelStore;
import com.android.systemui.volume.util.BlurEffect;
import com.android.systemui.volume.util.ContextUtils;
import com.android.systemui.volume.util.DisplayManagerWrapper;
import com.android.systemui.volume.util.HandlerWrapper;
import com.android.systemui.volume.util.ViewVisibilityUtil;
import com.android.systemui.volume.view.expand.AbstractC3626xb6ef341d;
import com.samsung.systemui.splugins.volume.VolumeObserver;
import com.samsung.systemui.splugins.volume.VolumePanelAction;
import com.samsung.systemui.splugins.volume.VolumePanelState;
import java.util.function.Supplier;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SubFullLayoutVolumePanelExpandWindow extends Dialog implements VolumeObserver {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final LogWrapper log;
    public final SubFullLayoutVolumePanelExpandView panelView;
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
                iArr[VolumePanelState.StateType.STATE_DUAL_PLAY_MODE_CHANGED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_STATUS_MESSAGE_CLICKED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_STATUS_LE_BROADCASTING_MESSAGE_CLICKED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_SETTINGS_BUTTON_CLICKED.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_OPEN_THEME_CHANGED.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_DISMISS_VOLUME_PANEL.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_DISMISS.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_FOLDER_STATE_CHANGED.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_ORIENTATION_CHANGED.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_COVER_STATE_CHANGED.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_DISMISS_VOLUME_PANEL_COMPLETED.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
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
    public SubFullLayoutVolumePanelExpandWindow(VolumeDependencyBase volumeDependencyBase) {
        super(r0.createWindowContext(r2, 2020, null));
        VolumeDependency volumeDependency = (VolumeDependency) volumeDependencyBase;
        Context context = (Context) volumeDependency.get(Context.class);
        Display frontSubDisplay = ((DisplayManagerWrapper) volumeDependency.get(DisplayManagerWrapper.class)).getFrontSubDisplay();
        Intrinsics.checkNotNull(frontSubDisplay);
        this.volDeps = volumeDependencyBase;
        this.store$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelExpandWindow$store$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (VolumePanelStore) ((VolumeDependency) SubFullLayoutVolumePanelExpandWindow.this.volDeps).get(VolumePanelStore.class);
            }
        });
        Lazy lazy = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelExpandWindow$storeInteractor$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                SubFullLayoutVolumePanelExpandWindow subFullLayoutVolumePanelExpandWindow = SubFullLayoutVolumePanelExpandWindow.this;
                int i = SubFullLayoutVolumePanelExpandWindow.$r8$clinit;
                return new StoreInteractor(subFullLayoutVolumePanelExpandWindow, (VolumePanelStore) subFullLayoutVolumePanelExpandWindow.store$delegate.getValue());
            }
        });
        this.storeInteractor$delegate = lazy;
        SystemConfigImpl systemConfigImpl = (SystemConfigImpl) ((VolumeConfigs) volumeDependency.get(VolumeConfigs.class)).systemConfig$delegate.getValue();
        this.log = (LogWrapper) volumeDependency.get(LogWrapper.class);
        Window window = getWindow();
        if (window != null) {
            window.requestFeature(1);
            window.setBackgroundDrawable(new ColorDrawable(0));
            window.clearFlags(2);
            window.addFlags(R.interpolator.launch_task_micro_alpha);
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.type = 2020;
            attributes.format = -3;
            attributes.setTitle("SubFullLayoutVolumePanelExpandWindow");
            attributes.width = -1;
            attributes.height = -1;
            attributes.windowAnimations = -1;
            attributes.accessibilityTitle = window.getContext().getString(com.android.systemui.R.string.volume_panel_view_title);
            if (systemConfigImpl.getHasCutout()) {
                attributes.flags |= 67109888;
                attributes.layoutInDisplayCutoutMode = 2;
            }
            window.setAttributes(attributes);
        }
        setContentView(com.android.systemui.R.layout.sub_full_volume_panel_expand_view);
        final SubFullLayoutVolumePanelExpandView subFullLayoutVolumePanelExpandView = (SubFullLayoutVolumePanelExpandView) findViewById(com.android.systemui.R.id.volume_panel_expand_view);
        this.panelView = subFullLayoutVolumePanelExpandView;
        subFullLayoutVolumePanelExpandView.dialog = this;
        subFullLayoutVolumePanelExpandView.store = (VolumePanelStore) volumeDependency.get(VolumePanelStore.class);
        subFullLayoutVolumePanelExpandView.handlerWrapper = (HandlerWrapper) volumeDependency.get(HandlerWrapper.class);
        subFullLayoutVolumePanelExpandView.logWrapper = (LogWrapper) volumeDependency.get(LogWrapper.class);
        subFullLayoutVolumePanelExpandView.volumePanelMotion = (SubFullLayoutVolumePanelMotion) volumeDependency.get(SubFullLayoutVolumePanelMotion.class);
        subFullLayoutVolumePanelExpandView.blurEffect = new BlurEffect(subFullLayoutVolumePanelExpandView.getContext(), volumeDependencyBase);
        StoreInteractor storeInteractor = subFullLayoutVolumePanelExpandView.storeInteractor;
        VolumePanelStore volumePanelStore = subFullLayoutVolumePanelExpandView.store;
        storeInteractor.store = volumePanelStore == null ? null : volumePanelStore;
        Dialog dialog = subFullLayoutVolumePanelExpandView.dialog;
        Window window2 = (dialog == null ? null : dialog).getWindow();
        Intrinsics.checkNotNull(window2);
        window2.getDecorView().setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelExpandView$bind$1
            @Override // android.view.View.AccessibilityDelegate
            public final boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
                AbstractC3626xb6ef341d.m227m(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_SEND_ACCESSIBILITY_EVENT), true, SubFullLayoutVolumePanelExpandView.this.storeInteractor, true);
                return super.onRequestSendAccessibilityEvent(viewGroup, view, accessibilityEvent);
            }
        });
        Dialog dialog2 = subFullLayoutVolumePanelExpandView.dialog;
        (dialog2 != null ? dialog2 : null).setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelExpandView$bind$2
            @Override // android.content.DialogInterface.OnShowListener
            public final void onShow(DialogInterface dialogInterface) {
                final Runnable runnable;
                boolean z = BasicRune.VOLUME_PARTIAL_BLUR;
                if (z) {
                    final SubFullLayoutVolumePanelExpandView subFullLayoutVolumePanelExpandView2 = SubFullLayoutVolumePanelExpandView.this;
                    runnable = new Runnable() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelExpandView$bind$2$showBlurRunnable$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            SubFullLayoutVolumePanelExpandView subFullLayoutVolumePanelExpandView3 = SubFullLayoutVolumePanelExpandView.this;
                            BlurEffect blurEffect = subFullLayoutVolumePanelExpandView3.blurEffect;
                            if (blurEffect == null) {
                                blurEffect = null;
                            }
                            ImageView imageView = subFullLayoutVolumePanelExpandView3.blurView;
                            ImageView imageView2 = imageView != null ? imageView : null;
                            int color = subFullLayoutVolumePanelExpandView3.getContext().getColor(com.android.systemui.R.color.volume_expand_panel_bg_color_blur);
                            float dimension = SubFullLayoutVolumePanelExpandView.this.getContext().getResources().getDimension(com.android.systemui.R.dimen.sub_full_volume_panel_expand_view_radius);
                            blurEffect.getClass();
                            BlurEffect.setRealTimeBlur(dimension, color, imageView2);
                        }
                    };
                } else if (BasicRune.VOLUME_CAPTURED_BLUR) {
                    final SubFullLayoutVolumePanelExpandView subFullLayoutVolumePanelExpandView3 = SubFullLayoutVolumePanelExpandView.this;
                    runnable = new Runnable() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelExpandView$bind$2$showBlurRunnable$2
                        @Override // java.lang.Runnable
                        public final void run() {
                            final SubFullLayoutVolumePanelExpandView subFullLayoutVolumePanelExpandView4 = SubFullLayoutVolumePanelExpandView.this;
                            BlurEffect blurEffect = subFullLayoutVolumePanelExpandView4.blurEffect;
                            if (blurEffect == null) {
                                blurEffect = null;
                            }
                            ImageView imageView = subFullLayoutVolumePanelExpandView4.blurView;
                            blurEffect.setCapturedBlur(imageView != null ? imageView : null, new Supplier() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelExpandView$bind$2$showBlurRunnable$2.1
                                @Override // java.util.function.Supplier
                                public final Object get() {
                                    int[] iArr = new int[2];
                                    ImageView imageView2 = SubFullLayoutVolumePanelExpandView.this.blurView;
                                    if (imageView2 == null) {
                                        imageView2 = null;
                                    }
                                    imageView2.getLocationOnScreen(iArr);
                                    int i = iArr[0];
                                    ImageView imageView3 = SubFullLayoutVolumePanelExpandView.this.blurView;
                                    if (imageView3 == null) {
                                        imageView3 = null;
                                    }
                                    iArr[0] = i - ((int) (imageView3.getWidth() * 0.025d));
                                    iArr[1] = iArr[1] - ((int) ((SubFullLayoutVolumePanelExpandView.this.blurView != null ? r8 : null).getHeight() * 0.025d));
                                    return iArr;
                                }
                            });
                        }
                    };
                } else {
                    runnable = new Runnable() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelExpandView$bind$2$showBlurRunnable$3
                        @Override // java.lang.Runnable
                        public final void run() {
                        }
                    };
                }
                SubFullLayoutVolumePanelExpandView subFullLayoutVolumePanelExpandView4 = SubFullLayoutVolumePanelExpandView.this;
                final SubFullLayoutVolumePanelMotion subFullLayoutVolumePanelMotion = subFullLayoutVolumePanelExpandView4.volumePanelMotion;
                if (subFullLayoutVolumePanelMotion == null) {
                    subFullLayoutVolumePanelMotion = null;
                }
                Dialog dialog3 = subFullLayoutVolumePanelExpandView4.dialog;
                if (dialog3 == null) {
                    dialog3 = null;
                }
                Window window3 = dialog3.getWindow();
                Intrinsics.checkNotNull(window3);
                final View decorView = window3.getDecorView();
                subFullLayoutVolumePanelMotion.getClass();
                ObjectAnimator ofFloat = ObjectAnimator.ofFloat(decorView, "alpha", decorView.getAlpha(), 1.0f);
                ofFloat.setDuration(200L);
                ofFloat.setInterpolator(new LinearInterpolator());
                ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelMotion$startShowVolumeExpandAnimation$alphaAnimator$1$1
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        if (decorView.getAlpha() <= 0.2f || !BasicRune.VOLUME_PARTIAL_BLUR) {
                            return;
                        }
                        runnable.run();
                    }
                });
                ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(decorView, "scaleX", decorView.getScaleX(), 1.0f);
                ofFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelMotion$startShowVolumeExpandAnimation$scaleAnimator$1$1
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        decorView.setScaleY(((Float) valueAnimator.getAnimatedValue()).floatValue());
                    }
                });
                ofFloat2.setDuration(400L);
                SecLockIconView$$ExternalSyntheticOutline0.m82m(0.22f, 0.25f, 0.0f, 1.0f, ofFloat2);
                View findViewById = decorView.findViewById(com.android.systemui.R.id.volume_title);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(ofFloat);
                animatorSet.playTogether(ofFloat2);
                Animator[] animatorArr = new Animator[1];
                float[] fArr = new float[2];
                Context context2 = subFullLayoutVolumePanelMotion.context;
                if (context2 == null) {
                    context2 = null;
                }
                fArr[0] = ContextUtils.getDimenFloat(com.android.systemui.R.dimen.sub_full_volume_panel_expand_title_translation_y, context2);
                fArr[1] = 0.0f;
                ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(findViewById, "translationY", fArr);
                ofFloat3.setDuration(400L);
                ofFloat3.setInterpolator(SubFullLayoutVolumePanelMotion.TITLE_TRANSLATION_INTERPOLATOR);
                ObjectAnimator ofFloat4 = ObjectAnimator.ofFloat(findViewById, "alpha", 0.0f, 1.0f);
                ofFloat4.setDuration(200L);
                ofFloat4.setInterpolator(new LinearInterpolator());
                AnimatorSet animatorSet2 = new AnimatorSet();
                animatorSet2.playTogether(ofFloat3);
                animatorSet2.playTogether(ofFloat4);
                animatorSet2.setStartDelay(50L);
                animatorArr[0] = animatorSet2;
                animatorSet.playTogether(animatorArr);
                animatorSet.setStartDelay(250L);
                animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelMotion$startShowVolumeExpandAnimation$1$1
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        if (BasicRune.VOLUME_PARTIAL_BLUR) {
                            runnable.run();
                        }
                        SubFullLayoutVolumePanelMotion.this.storeInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_ANIMATION_FINISHED).build(), true);
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationStart(Animator animator) {
                        SubFullLayoutVolumePanelMotion.this.storeInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_ANIMATION_START).build(), true);
                    }
                });
                animatorSet.start();
                if (z || !BasicRune.VOLUME_CAPTURED_BLUR) {
                    return;
                }
                runnable.run();
            }
        });
        setCanceledOnTouchOutside(true);
        ((StoreInteractor) lazy.getValue()).observeStore();
        subFullLayoutVolumePanelExpandView.storeInteractor.observeStore();
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        this.panelView.dispatchTouchEvent(motionEvent);
        return true;
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeObserver
    public final void onChanged(Object obj) {
        VolumePanelState volumePanelState = (VolumePanelState) obj;
        switch (WhenMappings.$EnumSwitchMapping$0[volumePanelState.getStateType().ordinal()]) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                if (isShowing() && !volumePanelState.isAnimating()) {
                    final SubFullLayoutVolumePanelExpandView subFullLayoutVolumePanelExpandView = this.panelView;
                    final SubFullLayoutVolumePanelMotion subFullLayoutVolumePanelMotion = subFullLayoutVolumePanelExpandView.volumePanelMotion;
                    if (subFullLayoutVolumePanelMotion == null) {
                        subFullLayoutVolumePanelMotion = null;
                    }
                    Dialog dialog = subFullLayoutVolumePanelExpandView.dialog;
                    Window window = (dialog != null ? dialog : null).getWindow();
                    Intrinsics.checkNotNull(window);
                    final View decorView = window.getDecorView();
                    final Runnable runnable = new Runnable() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelExpandView$startDismissAnimation$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            if (BasicRune.VOLUME_PARTIAL_BLUR) {
                                SubFullLayoutVolumePanelExpandView subFullLayoutVolumePanelExpandView2 = SubFullLayoutVolumePanelExpandView.this;
                                BlurEffect blurEffect = subFullLayoutVolumePanelExpandView2.blurEffect;
                                if (blurEffect == null) {
                                    blurEffect = null;
                                }
                                ImageView imageView = subFullLayoutVolumePanelExpandView2.blurView;
                                if (imageView == null) {
                                    imageView = null;
                                }
                                blurEffect.getClass();
                                ViewVisibilityUtil.INSTANCE.getClass();
                                imageView.setVisibility(4);
                                imageView.semSetBlurInfo(null);
                            }
                        }
                    };
                    final Runnable runnable2 = new Runnable() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelExpandView$startDismissAnimation$2
                        @Override // java.lang.Runnable
                        public final void run() {
                            Dialog dialog2 = SubFullLayoutVolumePanelExpandView.this.dialog;
                            if (dialog2 == null) {
                                dialog2 = null;
                            }
                            dialog2.dismiss();
                        }
                    };
                    subFullLayoutVolumePanelMotion.getClass();
                    ObjectAnimator ofFloat = ObjectAnimator.ofFloat(decorView, "alpha", decorView.getAlpha(), 0.0f);
                    ofFloat.setDuration(200L);
                    ofFloat.setInterpolator(new PathInterpolator(0.33f, 0.0f, 0.67f, 1.0f));
                    ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelMotion$startHideVolumeExpandAnimation$alphaAnimator$1$1
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                            if (decorView.getAlpha() >= 0.4f || !BasicRune.VOLUME_PARTIAL_BLUR) {
                                return;
                            }
                            runnable.run();
                        }
                    });
                    ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(decorView, "scaleX", decorView.getScaleX(), 0.9f);
                    ofFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelMotion$startHideVolumeExpandAnimation$scaleAnimator$1$1
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                            decorView.setScaleY(((Float) valueAnimator.getAnimatedValue()).floatValue());
                        }
                    });
                    ofFloat2.setDuration(200L);
                    ofFloat2.setInterpolator(new PathInterpolator(0.33f, 0.0f, 0.67f, 1.0f));
                    AnimatorSet animatorSet = new AnimatorSet();
                    animatorSet.playTogether(ofFloat);
                    animatorSet.playTogether(ofFloat2);
                    animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelMotion$startHideVolumeExpandAnimation$1$1
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public final void onAnimationEnd(Animator animator) {
                            runnable2.run();
                            SubFullLayoutVolumePanelMotion.this.storeInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_ANIMATION_FINISHED).build(), true);
                        }

                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public final void onAnimationStart(Animator animator) {
                            SubFullLayoutVolumePanelMotion.this.storeInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_ANIMATION_START).build(), true);
                        }
                    });
                    animatorSet.start();
                    break;
                }
                break;
            case 8:
            case 9:
            case 10:
                if (isShowing()) {
                    dismiss();
                    break;
                }
                break;
            case 11:
                if (isShowing()) {
                    dismiss();
                }
                ((StoreInteractor) this.storeInteractor$delegate.getValue()).dispose();
                this.panelView.storeInteractor.dispose();
                break;
        }
    }

    @Override // android.app.Dialog
    public final void onStart() {
        super.onStart();
        this.log.m98d("SubFullLayoutVolumePanelExpandWindow", "onStart");
    }

    @Override // android.app.Dialog
    public final void onStop() {
        super.onStop();
        this.log.m98d("SubFullLayoutVolumePanelExpandWindow", "onStop : panelState.isExpanded=" + ((VolumePanelStore) this.store$delegate.getValue()).currentState.isExpanded());
        ViewGroup viewGroup = this.panelView.rowContainer;
        if (viewGroup == null) {
            viewGroup = null;
        }
        viewGroup.removeAllViews();
        ((StoreInteractor) this.storeInteractor$delegate.getValue()).sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_DISMISS_SUB_DISPLAY_VOLUME_PANEL).build(), true);
    }
}
