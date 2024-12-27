package com.android.systemui.volume.view.expand;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.PathInterpolator;
import android.widget.ImageView;
import com.android.systemui.BasicRune;
import com.android.systemui.R;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.volume.VolumeDependencyBase;
import com.android.systemui.volume.config.SystemConfigImpl;
import com.android.systemui.volume.store.StoreInteractor;
import com.android.systemui.volume.store.VolumePanelStore;
import com.android.systemui.volume.util.BlurEffect;
import com.android.systemui.volume.view.VolumePanelMotion;
import com.samsung.systemui.splugins.volume.VolumeObserver;
import com.samsung.systemui.splugins.volume.VolumePanelAction;
import com.samsung.systemui.splugins.volume.VolumePanelState;
import kotlin.Lazy;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class VolumePanelExpandWindow extends Dialog implements VolumeObserver {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final LogWrapper log;
    public final VolumePanelExpandView panelView;
    public final Lazy store$delegate;
    public final Lazy storeInteractor$delegate;
    public final SystemConfigImpl systemConfig;
    public final VolumeDependencyBase volDeps;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
                iArr[VolumePanelState.StateType.STATE_STATUS_DO_NOT_DISTURB_MESSAGE_CLICKED.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_SETTINGS_BUTTON_CLICKED.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_OPEN_THEME_CHANGED.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_DISMISS_VOLUME_PANEL.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_DISMISS.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_FOLDER_STATE_CHANGED.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_ORIENTATION_CHANGED.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_COVER_STATE_CHANGED.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_DISMISS_VOLUME_PANEL_COMPLETED.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
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
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public VolumePanelExpandWindow(com.android.systemui.volume.VolumeDependencyBase r12) {
        /*
            Method dump skipped, instructions count: 304
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.view.expand.VolumePanelExpandWindow.<init>(com.android.systemui.volume.VolumeDependencyBase):void");
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        this.panelView.dispatchTouchEvent(motionEvent);
        return true;
    }

    public final VolumePanelStore getStore() {
        return (VolumePanelStore) this.store$delegate.getValue();
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
            case 8:
                if (isShowing() && !volumePanelState.isAnimating()) {
                    final VolumePanelExpandView volumePanelExpandView = this.panelView;
                    final VolumePanelMotion volumePanelMotion = volumePanelExpandView.volumePanelMotion;
                    if (volumePanelMotion == null) {
                        volumePanelMotion = null;
                    }
                    Dialog dialog = volumePanelExpandView.dialog;
                    Window window = (dialog != null ? dialog : null).getWindow();
                    Intrinsics.checkNotNull(window);
                    final View decorView = window.getDecorView();
                    final Runnable runnable = new Runnable() { // from class: com.android.systemui.volume.view.expand.VolumePanelExpandView$startDismissAnimation$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            if (BasicRune.VOLUME_PARTIAL_BLUR) {
                                VolumePanelExpandView volumePanelExpandView2 = VolumePanelExpandView.this;
                                BlurEffect blurEffect = volumePanelExpandView2.blurEffect;
                                if (blurEffect == null) {
                                    blurEffect = null;
                                }
                                ImageView imageView = volumePanelExpandView2.blurView;
                                ImageView imageView2 = imageView != null ? imageView : null;
                                blurEffect.getClass();
                                BlurEffect.hideBlur(imageView2);
                            }
                        }
                    };
                    final Runnable runnable2 = new Runnable() { // from class: com.android.systemui.volume.view.expand.VolumePanelExpandView$startDismissAnimation$2
                        @Override // java.lang.Runnable
                        public final void run() {
                            Dialog dialog2 = VolumePanelExpandView.this.dialog;
                            if (dialog2 == null) {
                                dialog2 = null;
                            }
                            dialog2.dismiss();
                        }
                    };
                    volumePanelMotion.getClass();
                    View requireViewById = decorView.requireViewById(R.id.volume_setting_button);
                    ObjectAnimator ofFloat = ObjectAnimator.ofFloat(decorView, "alpha", decorView.getAlpha(), 0.0f);
                    ofFloat.setDuration(200L);
                    ofFloat.setInterpolator(new PathInterpolator(0.33f, 0.0f, 0.67f, 1.0f));
                    ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.volume.view.VolumePanelMotion$startHideVolumeExpandAnimation$alphaAnimator$1$1
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                            if (decorView.getAlpha() >= 0.4f || !BasicRune.VOLUME_PARTIAL_BLUR) {
                                return;
                            }
                            runnable.run();
                        }
                    });
                    ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(decorView, "scaleX", decorView.getScaleX(), 0.9f);
                    ofFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.volume.view.VolumePanelMotion$startHideVolumeExpandAnimation$scaleAnimator$1$1
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
                    animatorSet.playTogether(VolumePanelMotion.getSettingButtonRotateAnimation(requireViewById, false));
                    animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.volume.view.VolumePanelMotion$startHideVolumeExpandAnimation$1$1
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public final void onAnimationEnd(Animator animator) {
                            runnable2.run();
                            VolumePanelMotion.this.storeInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_ANIMATION_FINISHED).build(), true);
                        }

                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public final void onAnimationStart(Animator animator) {
                            VolumePanelMotion.this.storeInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_ANIMATION_START).build(), true);
                        }
                    });
                    animatorSet.start();
                    break;
                }
                break;
            case 9:
            case 10:
            case 11:
                if (isShowing()) {
                    dismiss();
                    break;
                }
                break;
            case 12:
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
        this.log.d("VolumePanelExpandWindow", "onStart");
    }

    @Override // android.app.Dialog
    public final void onStop() {
        super.onStop();
        this.log.d("VolumePanelExpandWindow", "onStop : panelState.isExpanded=" + getStore().currentState.isExpanded());
        ViewGroup viewGroup = this.panelView.rowContainer;
        if (viewGroup == null) {
            viewGroup = null;
        }
        viewGroup.removeAllViews();
        ((StoreInteractor) this.storeInteractor$delegate.getValue()).sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_DISMISS_VOLUME_PANEL).build(), true);
    }
}
