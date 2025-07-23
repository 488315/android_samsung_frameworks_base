package com.android.systemui.qs.animator;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.Log;
import android.view.View;
import androidx.appcompat.widget.MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.systemui.plugins.qs.QS;
import com.android.systemui.qs.QSImpl;
import com.android.systemui.qs.TouchAnimator;
import com.android.systemui.qs.animator.QSAnimViewProvider;
import com.android.systemui.samsung.quicksetting.SecQSPanelComposeAdapter;
import com.android.systemui.shade.PanelSlideEventHandler;
import com.android.systemui.shade.PanelTransitionState;
import com.android.systemui.shade.PanelTransitionStateChangeEvent;
import com.android.systemui.shade.SecPanelSplitHelper;
import com.android.systemui.statusbar.StatusBarState;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManager;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManagerImpl;
import com.android.systemui.statusbar.notification.headsup.OnHeadsUpChangedListener;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class PanelTransitionAnimator extends SecQSImplAnimatorBase {
    public static final /* synthetic */ int $r8$clinit = 0;
    public ValueAnimator blurAnimator;
    public QSAnimView blurParent;
    public QSAnimView blurView;
    public QSAnimView buttonContainer;
    public TouchAnimator buttonFadeInDownAnimator;
    public TouchAnimator buttonFadeInLeftAnimator;
    public TouchAnimator buttonFadeInRightAnimator;
    public TouchAnimator buttonFadeOutDownAnimator;
    public TouchAnimator buttonFadeOutLeftAnimator;
    public TouchAnimator buttonFadeOutRightAnimator;
    public TouchAnimator buttonLockScreenFadeInAnimator;
    public TouchAnimator carrierFadeInLeftAnimator;
    public TouchAnimator carrierFadeInRightAnimator;
    public TouchAnimator carrierFadeOutLeftAnimator;
    public TouchAnimator carrierFadeOutRightAnimator;
    public QSAnimView clockDateContainer;
    public TouchAnimator clockDateContainerFadeInDownAnimator;
    public TouchAnimator clockDateContainerFadeInLeftAnimator;
    public TouchAnimator clockDateContainerFadeInRightAnimator;
    public TouchAnimator clockDateContainerFadeOutDownAnimator;
    public TouchAnimator clockDateContainerFadeOutLeftAnimator;
    public TouchAnimator clockDateContainerFadeOutRightAnimator;
    public final Context context;
    public QSAnimView header;
    public TouchAnimator headerAnimator;
    public final HeadsUpManager headsUpManager;
    public boolean inPinnedMode;
    public QSAnimView largeShadowView;
    public QSAnimView lessBlurView;
    public QSAnimView plmn;
    public TouchAnimator qsFadeInDown;
    public TouchAnimator qsFadeInLeft;
    public TouchAnimator qsFadeInRight;
    public TouchAnimator qsFadeOutDown;
    public TouchAnimator qsFadeOutLeft;
    public TouchAnimator qsFadeOutRight;
    public TouchAnimator qsLockScreenFadeInDown;
    public TouchAnimator qsLockScreenTranslattionDown;
    public QSAnimView qsRootView;
    public final SecQsUiDisplayModeInteractor secQsUiDisplayModeInteractor;
    public TouchAnimator shadeFadeInDown;
    public TouchAnimator shadeFadeInLeft;
    public TouchAnimator shadeFadeInRight;
    public TouchAnimator shadeFadeOutDown;
    public TouchAnimator shadeFadeOutLeft;
    public TouchAnimator shadeFadeOutRight;
    public TouchAnimator shadeLockScreenFadeOutDown;
    public QSAnimView shadeRootView;
    public QSAnimView smallShadowView;
    public boolean splitEnabled;
    public final QSAnimViewProvider viewProvider;
    public final Lazy secPanelSplitHelper$delegate = LazyKt__LazyJVMKt.lazy(new PanelTransitionAnimator$$ExternalSyntheticLambda0());
    public final PanelTransitionAnimator$onHeadsUpChangedListener$1 onHeadsUpChangedListener = new OnHeadsUpChangedListener() { // from class: com.android.systemui.qs.animator.PanelTransitionAnimator$onHeadsUpChangedListener$1
        @Override // com.android.systemui.statusbar.notification.headsup.OnHeadsUpChangedListener
        public final void onHeadsUpPinnedModeChanged(boolean z) {
            EmergencyButtonController$$ExternalSyntheticOutline0.m("onHeadsUpPinnedModeChanged: ", "PanelTransitionAnimator", z);
            PanelTransitionAnimator.this.inPinnedMode = z;
        }
    };
    public final float xDiff = 50.0f;
    public final float barYDiff = 330.0f;
    public final float buttonYDiff = 100.0f;
    public int panelTransitionState = 1;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.qs.animator.PanelTransitionAnimator$onHeadsUpChangedListener$1] */
    public PanelTransitionAnimator(Context context, HeadsUpManager headsUpManager, QSAnimViewProvider qSAnimViewProvider, SecQSPanelComposeAdapter secQSPanelComposeAdapter, SecQsUiDisplayModeInteractor secQsUiDisplayModeInteractor) {
        this.context = context;
        this.headsUpManager = headsUpManager;
        this.viewProvider = qSAnimViewProvider;
        this.secQsUiDisplayModeInteractor = secQsUiDisplayModeInteractor;
        this.inPinnedMode = ((HeadsUpManagerImpl) headsUpManager).mHasPinnedNotification;
    }

    public static void updateVisibility(QSAnimView qSAnimView, boolean z) {
        if (z && qSAnimView.getVisibility() == 8) {
            qSAnimView.setVisibility(0);
        } else {
            if (z || qSAnimView.getVisibility() != 0) {
                return;
            }
            qSAnimView.setVisibility(8);
        }
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void clearAnimationState() {
        QSAnimView qSAnimView;
        boolean z = this.mUserChanged;
        this.mUserChanged = false;
        if (!isThereNoView() || z) {
            int i = this.panelTransitionState;
            if (i != 2 || z) {
                Log.d("PanelTransitionAnimator", "clearAnimationState ".concat(PanelTransitionState.toString(i)));
                QSAnimView qSAnimView2 = this.qsRootView;
                if (qSAnimView2 != null) {
                    int i2 = this.panelTransitionState;
                    if (i2 == 0) {
                        qSAnimView2.setAlpha(1.0f);
                    } else if (i2 == 1) {
                        qSAnimView2.setAlpha(0.0f);
                    }
                    qSAnimView2.setTranslationX(0.0f);
                    qSAnimView2.setTranslationY(0.0f);
                    qSAnimView2.setScaleX(1.0f);
                    qSAnimView2.setScaleY(1.0f);
                }
                QSAnimView qSAnimView3 = this.shadeRootView;
                if (qSAnimView3 != null) {
                    int i3 = this.panelTransitionState;
                    if (i3 == 0) {
                        qSAnimView3.setAlpha(0.0f);
                    } else if (i3 == 1) {
                        qSAnimView3.setAlpha(1.0f);
                    }
                    if (!this.secQsUiDisplayModeInteractor.isTablet()) {
                        qSAnimView3.setTranslationX(0.0f);
                    }
                    qSAnimView3.setTranslationY(0.0f);
                    qSAnimView3.setScaleX(1.0f);
                    qSAnimView3.setScaleY(1.0f);
                }
                QSAnimView qSAnimView4 = this.buttonContainer;
                if (qSAnimView4 != null) {
                    int i4 = this.panelTransitionState;
                    if (i4 == 0) {
                        qSAnimView4.setAlpha(1.0f);
                        if (QsAnimatorState.state == 1) {
                            qSAnimView4.setScaleX(1.0f);
                            qSAnimView4.setScaleY(1.0f);
                        }
                    } else if (i4 == 1) {
                        qSAnimView4.setAlpha(0.0f);
                    }
                    qSAnimView4.setTranslationX(0.0f);
                    qSAnimView4.setTranslationY(0.0f);
                }
                QSAnimView qSAnimView5 = this.clockDateContainer;
                if (qSAnimView5 != null) {
                    int i5 = this.panelTransitionState;
                    if (i5 == 0) {
                        qSAnimView5.setAlpha(0.0f);
                    } else if (i5 == 1) {
                        qSAnimView5.setAlpha(1.0f);
                    }
                    qSAnimView5.setTranslationX(0.0f);
                    qSAnimView5.setTranslationY(0.0f);
                }
                if (this.panelTransitionState == 0 && QsAnimatorState.state == 1 && (qSAnimView = this.header) != null) {
                    qSAnimView.setAlpha(1.0f);
                    qSAnimView.setTranslationY(0.0f);
                }
                if (QsAnimatorState.state != 1) {
                    QSAnimView qSAnimView6 = this.blurView;
                    if (qSAnimView6 != null) {
                        qSAnimView6.setAlpha(1.0f);
                    }
                    QSAnimView qSAnimView7 = this.largeShadowView;
                    if (qSAnimView7 != null) {
                        qSAnimView7.setAlpha(1.0f);
                    }
                    QSAnimView qSAnimView8 = this.smallShadowView;
                    if (qSAnimView8 != null) {
                        qSAnimView8.setAlpha(1.0f);
                    }
                    QSAnimView qSAnimView9 = this.lessBlurView;
                    if (qSAnimView9 != null) {
                        qSAnimView9.setAlpha(1.0f);
                    }
                    QSAnimView qSAnimView10 = this.blurParent;
                    if (qSAnimView10 != null) {
                        qSAnimView10.setTranslationY(0.0f);
                    }
                    QSAnimView qSAnimView11 = this.largeShadowView;
                    if (qSAnimView11 != null) {
                        qSAnimView11.setTranslationY(0.0f);
                    }
                    QSAnimView qSAnimView12 = this.smallShadowView;
                    if (qSAnimView12 != null) {
                        qSAnimView12.setTranslationY(0.0f);
                    }
                    QSAnimView qSAnimView13 = this.blurParent;
                    if (qSAnimView13 != null) {
                        qSAnimView13.setScaleX(1.0f);
                    }
                    QSAnimView qSAnimView14 = this.largeShadowView;
                    if (qSAnimView14 != null) {
                        qSAnimView14.setScaleX(1.0f);
                    }
                    QSAnimView qSAnimView15 = this.smallShadowView;
                    if (qSAnimView15 != null) {
                        qSAnimView15.setScaleX(1.0f);
                    }
                    QSAnimView qSAnimView16 = this.blurParent;
                    if (qSAnimView16 != null) {
                        qSAnimView16.setScaleY(1.0f);
                    }
                    QSAnimView qSAnimView17 = this.largeShadowView;
                    if (qSAnimView17 != null) {
                        qSAnimView17.setScaleY(1.0f);
                    }
                    QSAnimView qSAnimView18 = this.smallShadowView;
                    if (qSAnimView18 != null) {
                        qSAnimView18.setScaleY(1.0f);
                    }
                }
            }
        }
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void destroyQSViews() {
        this.header = null;
        this.qsRootView = null;
        this.shadeRootView = null;
        this.buttonContainer = null;
        this.plmn = null;
        this.clockDateContainer = null;
        ((HeadsUpManagerImpl) this.headsUpManager).removeListener(this.onHeadsUpChangedListener);
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final boolean isThereNoView() {
        if (super.isThereNoView()) {
            return true;
        }
        SecPanelSplitHelper.Companion.getClass();
        return !SecPanelSplitHelper.isEnabled || this.inPinnedMode || QsAnimatorState.isDetailShowing;
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void onPanelClosed$1() {
        if (this.splitEnabled) {
            updateAnimators();
        }
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void onPanelTransitionStateChanged(PanelTransitionStateChangeEvent panelTransitionStateChangeEvent) {
        if (!isThereNoView() || this.mUserChanged) {
            int i = panelTransitionStateChangeEvent.state;
            if (i == 0) {
                QSAnimView qSAnimView = this.buttonContainer;
                if (qSAnimView != null) {
                    updateVisibility(qSAnimView, true);
                }
                QSAnimView qSAnimView2 = this.clockDateContainer;
                if (qSAnimView2 != null) {
                    updateVisibility(qSAnimView2, false);
                }
            } else if (i == 1) {
                QSAnimView qSAnimView3 = this.buttonContainer;
                if (qSAnimView3 != null) {
                    updateVisibility(qSAnimView3, false);
                }
                QSAnimView qSAnimView4 = this.clockDateContainer;
                if (qSAnimView4 != null) {
                    updateVisibility(qSAnimView4, true);
                }
            } else if (i == 2) {
                QSAnimView qSAnimView5 = this.buttonContainer;
                if (qSAnimView5 != null) {
                    updateVisibility(qSAnimView5, true);
                }
                QSAnimView qSAnimView6 = this.clockDateContainer;
                if (qSAnimView6 != null) {
                    updateVisibility(qSAnimView6, true);
                }
            }
            this.panelTransitionState = i;
            boolean z = this.splitEnabled;
            boolean z2 = panelTransitionStateChangeEvent.enabled;
            if (z != z2) {
                if (z2) {
                    updateAnimators();
                } else {
                    clearAnimationState();
                }
                this.splitEnabled = z2;
            }
            if (panelTransitionStateChangeEvent.fraction == 1.0f) {
                clearAnimationState();
            }
        }
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void onStateChanged(int i) {
        clearAnimationState();
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void onUserSwitched(int i) {
        this.mUserChanged = true;
        SecPanelSplitHelper.Companion.getClass();
        if (SecPanelSplitHelper.isEnabled) {
            onPanelTransitionStateChanged(new PanelTransitionStateChangeEvent(true, 1.0f, 0));
            QSAnimView qSAnimView = this.clockDateContainer;
            if (qSAnimView != null) {
                updateVisibility(qSAnimView, true);
            }
            QSAnimView qSAnimView2 = this.header;
            if (qSAnimView2 != null) {
                qSAnimView2.setAlpha(0.0f);
            }
        }
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void setQs(QS qs) {
        if (qs == null) {
            destroyQSViews();
            return;
        }
        this.mQs = (QSImpl) qs;
        QSAnimViewProvider.ViewType viewType = QSAnimViewProvider.ViewType.ROOT_VIEW;
        QSAnimViewProvider qSAnimViewProvider = this.viewProvider;
        this.qsRootView = qSAnimViewProvider.get(viewType);
        this.blurView = qSAnimViewProvider.get(QSAnimViewProvider.ViewType.BLUR_VIEW);
        this.blurParent = qSAnimViewProvider.get(QSAnimViewProvider.ViewType.BLUR_PARENT);
        this.lessBlurView = qSAnimViewProvider.get(QSAnimViewProvider.ViewType.LESS_BLUR_VIEW);
        this.largeShadowView = qSAnimViewProvider.get(QSAnimViewProvider.ViewType.LARGE_SHADOW_VIEW);
        this.smallShadowView = qSAnimViewProvider.get(QSAnimViewProvider.ViewType.SMALL_SHADOW_VIEW);
        this.buttonContainer = qSAnimViewProvider.get(QSAnimViewProvider.ViewType.QS_HEADER_BUTTON_CONTAINER);
        this.clockDateContainer = qSAnimViewProvider.get(QSAnimViewProvider.ViewType.QS_HEADER_CLOCK_DATE_PARENT);
        this.header = qSAnimViewProvider.get(QSAnimViewProvider.ViewType.SHADE_HEADER);
        qSAnimViewProvider.get(QSAnimViewProvider.ViewType.SHADE_HEADER_NETWORK_SPEED);
        qSAnimViewProvider.get(QSAnimViewProvider.ViewType.SHADE_HEADER_SYSTEM_ICONS);
        qSAnimViewProvider.get(QSAnimViewProvider.ViewType.SHADE_HEADER_PRIVACY_CONTAINER);
        this.plmn = qSAnimViewProvider.get(QSAnimViewProvider.ViewType.SHADE_HEADER_PLMN);
        this.shadeRootView = qSAnimViewProvider.get(QSAnimViewProvider.ViewType.NSSL);
        ((HeadsUpManagerImpl) this.headsUpManager).addListener(this.onHeadsUpChangedListener);
        SecPanelSplitHelper.Companion.getClass();
        if (SecPanelSplitHelper.isEnabled && this.inPinnedMode) {
            QSAnimView qSAnimView = this.clockDateContainer;
            if (qSAnimView != null) {
                qSAnimView.setAlpha(0.0f);
            }
            QSAnimView qSAnimView2 = this.buttonContainer;
            if (qSAnimView2 != null) {
                qSAnimView2.setAlpha(0.0f);
            }
            QSAnimView qSAnimView3 = this.buttonContainer;
            if (qSAnimView3 != null) {
                updateVisibility(qSAnimView3, false);
            }
        }
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void slide(float f, float f2, PanelSlideEventHandler.Direction direction, int i) {
        QSAnimView qSAnimView;
        TouchAnimator touchAnimator;
        TouchAnimator touchAnimator2;
        TouchAnimator touchAnimator3;
        TouchAnimator touchAnimator4;
        TouchAnimator touchAnimator5;
        QSImpl qSImpl;
        View view;
        View view2;
        TouchAnimator touchAnimator6;
        if (isThereNoView()) {
            return;
        }
        String panelTransitionState = PanelTransitionState.toString(i);
        String statusBarState = StatusBarState.toString(QsAnimatorState.state);
        String panelTransitionState2 = PanelTransitionState.toString(this.panelTransitionState);
        StringBuilder sb = new StringBuilder("fraction = ");
        sb.append(f);
        sb.append(" direction = ");
        sb.append(direction);
        sb.append(" stateToChange = ");
        MoveResult$$ExternalSyntheticOutline0.m(sb, panelTransitionState, " StatusBar = ", statusBarState, " panelTransitionState = ");
        ExifInterface$$ExternalSyntheticOutline0.m(sb, panelTransitionState2, "PanelTransitionAnimator");
        if (f2 != 0.0f) {
            float pow = (float) (((1 / Math.pow(1.0045d, Math.abs(f2))) * (-100.0f)) + 100.0f);
            Log.d("PanelTransitionAnimator", "overScrollX = " + f2 + " overScrollAmount = " + pow);
            if (f2 > 0.0f) {
                QSAnimView qSAnimView2 = this.shadeRootView;
                if (qSAnimView2 != null) {
                    qSAnimView2.setTranslationX(pow);
                    return;
                }
                return;
            }
            if (f2 >= 0.0f || (qSAnimView = this.qsRootView) == null) {
                return;
            }
            qSAnimView.setTranslationX(-pow);
            return;
        }
        int i2 = this.panelTransitionState;
        SecQsUiDisplayModeInteractor secQsUiDisplayModeInteractor = this.secQsUiDisplayModeInteractor;
        if (i2 == 1) {
            Log.d("PanelTransitionAnimator", "SHADE_STATE");
            if (secQsUiDisplayModeInteractor.isTablet()) {
                updateAnimators();
                return;
            } else {
                clearAnimationState();
                return;
            }
        }
        if (i2 == 0) {
            Log.d("PanelTransitionAnimator", "QS_STATE");
            if (secQsUiDisplayModeInteractor.isTablet()) {
                updateAnimators();
                return;
            } else {
                clearAnimationState();
                return;
            }
        }
        boolean isReversed = ((SecPanelSplitHelper) this.secPanelSplitHelper$delegate.getValue()).isReversed();
        if (direction == PanelSlideEventHandler.Direction.DOWN) {
            Log.d("PanelTransitionAnimator", "DOWN animation");
            if (!secQsUiDisplayModeInteractor.isTablet()) {
                QSAnimView qSAnimView3 = this.qsRootView;
                if (qSAnimView3 != null) {
                    qSAnimView3.setTranslationX(0.0f);
                }
                QSAnimView qSAnimView4 = this.shadeRootView;
                if (qSAnimView4 != null) {
                    qSAnimView4.setTranslationX(0.0f);
                }
            }
            if (QsAnimatorState.state == 1) {
                if (i == 0 || isReversed) {
                    QSAnimView qSAnimView5 = this.buttonContainer;
                    if (qSAnimView5 != null) {
                        updateVisibility(qSAnimView5, true);
                    }
                    QSAnimView qSAnimView6 = this.clockDateContainer;
                    if (qSAnimView6 != null) {
                        updateVisibility(qSAnimView6, false);
                    }
                    TouchAnimator touchAnimator7 = this.headerAnimator;
                    if (touchAnimator7 != null) {
                        touchAnimator7.setPosition(f);
                    }
                    TouchAnimator touchAnimator8 = this.buttonLockScreenFadeInAnimator;
                    if (touchAnimator8 != null) {
                        touchAnimator8.setPosition(f);
                    }
                    TouchAnimator touchAnimator9 = this.shadeLockScreenFadeOutDown;
                    if (touchAnimator9 != null) {
                        touchAnimator9.setPosition(f);
                    }
                    TouchAnimator touchAnimator10 = this.qsLockScreenFadeInDown;
                    if (touchAnimator10 != null) {
                        touchAnimator10.setPosition(f);
                    }
                    TouchAnimator touchAnimator11 = this.qsLockScreenTranslattionDown;
                    if (touchAnimator11 != null) {
                        touchAnimator11.setPosition(f);
                        return;
                    }
                    return;
                }
                return;
            }
            if (i != 0) {
                if (i != 1) {
                    return;
                }
                TouchAnimator touchAnimator12 = this.qsFadeOutDown;
                if (touchAnimator12 != null) {
                    touchAnimator12.setPosition(f);
                }
                TouchAnimator touchAnimator13 = this.shadeFadeInDown;
                if (touchAnimator13 != null) {
                    touchAnimator13.setPosition(f);
                }
                TouchAnimator touchAnimator14 = this.buttonFadeOutDownAnimator;
                if (touchAnimator14 != null) {
                    touchAnimator14.setPosition(f);
                }
                if (QsAnimatorState.isNotificationImmersiceScrolling && (touchAnimator6 = this.carrierFadeOutRightAnimator) != null) {
                    touchAnimator6.setPosition(f);
                }
                TouchAnimator touchAnimator15 = this.clockDateContainerFadeInDownAnimator;
                if (touchAnimator15 != null) {
                    touchAnimator15.setPosition(f);
                }
                ValueAnimator valueAnimator = this.blurAnimator;
                if (valueAnimator != null) {
                    valueAnimator.setCurrentFraction(f);
                    return;
                }
                return;
            }
            QSImpl qSImpl2 = this.mQs;
            if (!Intrinsics.areEqual((qSImpl2 == null || (view2 = qSImpl2.getView()) == null) ? null : Float.valueOf(view2.getAlpha()), 1.0f) && (qSImpl = this.mQs) != null && (view = qSImpl.getView()) != null) {
                view.setAlpha(1.0f);
            }
            TouchAnimator touchAnimator16 = this.shadeFadeOutDown;
            if (touchAnimator16 != null) {
                touchAnimator16.setPosition(f);
            }
            TouchAnimator touchAnimator17 = this.qsFadeInDown;
            if (touchAnimator17 != null) {
                touchAnimator17.setPosition(f);
            }
            TouchAnimator touchAnimator18 = this.buttonFadeInDownAnimator;
            if (touchAnimator18 != null) {
                touchAnimator18.setPosition(f);
            }
            if (QsAnimatorState.isNotificationImmersiceScrolling && (touchAnimator5 = this.carrierFadeInLeftAnimator) != null) {
                touchAnimator5.setPosition(f);
            }
            TouchAnimator touchAnimator19 = this.clockDateContainerFadeOutDownAnimator;
            if (touchAnimator19 != null) {
                touchAnimator19.setPosition(f);
            }
            ValueAnimator valueAnimator2 = this.blurAnimator;
            if (valueAnimator2 != null) {
                valueAnimator2.setCurrentFraction(f);
                return;
            }
            return;
        }
        if (direction == PanelSlideEventHandler.Direction.LEFT) {
            Log.d("PanelTransitionAnimator", "LEFT animation");
            QSAnimView qSAnimView7 = this.qsRootView;
            if (qSAnimView7 != null) {
                qSAnimView7.setTranslationX(0.0f);
            }
            QSAnimView qSAnimView8 = this.shadeRootView;
            if (qSAnimView8 != null) {
                qSAnimView8.setTranslationX(0.0f);
            }
            if (isReversed) {
                TouchAnimator touchAnimator20 = this.shadeFadeInLeft;
                if (touchAnimator20 != null) {
                    touchAnimator20.setPosition(f);
                }
                TouchAnimator touchAnimator21 = this.qsFadeOutLeft;
                if (touchAnimator21 != null) {
                    touchAnimator21.setPosition(f);
                }
                TouchAnimator touchAnimator22 = this.buttonFadeOutLeftAnimator;
                if (touchAnimator22 != null) {
                    touchAnimator22.setPosition(f);
                }
                if (QsAnimatorState.isNotificationImmersiceScrolling && (touchAnimator4 = this.carrierFadeOutLeftAnimator) != null) {
                    touchAnimator4.setPosition(f);
                }
                TouchAnimator touchAnimator23 = this.clockDateContainerFadeInLeftAnimator;
                if (touchAnimator23 != null) {
                    touchAnimator23.setPosition(f);
                    return;
                }
                return;
            }
            TouchAnimator touchAnimator24 = this.qsFadeInLeft;
            if (touchAnimator24 != null) {
                touchAnimator24.setPosition(f);
            }
            TouchAnimator touchAnimator25 = this.shadeFadeOutLeft;
            if (touchAnimator25 != null) {
                touchAnimator25.setPosition(f);
            }
            TouchAnimator touchAnimator26 = this.buttonFadeInLeftAnimator;
            if (touchAnimator26 != null) {
                touchAnimator26.setPosition(f);
            }
            if (QsAnimatorState.isNotificationImmersiceScrolling && (touchAnimator3 = this.carrierFadeInLeftAnimator) != null) {
                touchAnimator3.setPosition(f);
            }
            TouchAnimator touchAnimator27 = this.clockDateContainerFadeOutLeftAnimator;
            if (touchAnimator27 != null) {
                touchAnimator27.setPosition(f);
                return;
            }
            return;
        }
        if (direction == PanelSlideEventHandler.Direction.RIGHT) {
            Log.d("PanelTransitionAnimator", "RIGHT animation");
            QSAnimView qSAnimView9 = this.qsRootView;
            if (qSAnimView9 != null) {
                qSAnimView9.setTranslationX(0.0f);
            }
            QSAnimView qSAnimView10 = this.shadeRootView;
            if (qSAnimView10 != null) {
                qSAnimView10.setTranslationX(0.0f);
            }
            if (isReversed) {
                TouchAnimator touchAnimator28 = this.shadeFadeOutRight;
                if (touchAnimator28 != null) {
                    touchAnimator28.setPosition(f);
                }
                TouchAnimator touchAnimator29 = this.qsFadeInRight;
                if (touchAnimator29 != null) {
                    touchAnimator29.setPosition(f);
                }
                TouchAnimator touchAnimator30 = this.buttonFadeInRightAnimator;
                if (touchAnimator30 != null) {
                    touchAnimator30.setPosition(f);
                }
                if (QsAnimatorState.isNotificationImmersiceScrolling && (touchAnimator2 = this.carrierFadeInRightAnimator) != null) {
                    touchAnimator2.setPosition(f);
                }
                TouchAnimator touchAnimator31 = this.clockDateContainerFadeOutRightAnimator;
                if (touchAnimator31 != null) {
                    touchAnimator31.setPosition(f);
                    return;
                }
                return;
            }
            TouchAnimator touchAnimator32 = this.qsFadeOutRight;
            if (touchAnimator32 != null) {
                touchAnimator32.setPosition(f);
            }
            TouchAnimator touchAnimator33 = this.shadeFadeInRight;
            if (touchAnimator33 != null) {
                touchAnimator33.setPosition(f);
            }
            TouchAnimator touchAnimator34 = this.buttonFadeOutRightAnimator;
            if (touchAnimator34 != null) {
                touchAnimator34.setPosition(f);
            }
            if (QsAnimatorState.isNotificationImmersiceScrolling && (touchAnimator = this.carrierFadeOutRightAnimator) != null) {
                touchAnimator.setPosition(f);
            }
            TouchAnimator touchAnimator35 = this.clockDateContainerFadeInRightAnimator;
            if (touchAnimator35 != null) {
                touchAnimator35.setPosition(f);
            }
        }
    }

    @Override // com.android.systemui.qs.animator.SecQSImplAnimatorBase
    public final void updateAnimators() {
        float f;
        float f2;
        char c;
        Log.d("PanelTransitionAnimator", "updateAnimators");
        clearAnimationState();
        final QSAnimView qSAnimView = this.blurView;
        SecQsUiDisplayModeInteractor secQsUiDisplayModeInteractor = this.secQsUiDisplayModeInteractor;
        if (qSAnimView != null) {
            if (secQsUiDisplayModeInteractor.isTablet()) {
                qSAnimView.setPivotX((qSAnimView.getView() != null ? r9.getWidth() : 2) / 2.0f);
                qSAnimView.setPivotY(0.0f);
                QSAnimView qSAnimView2 = this.largeShadowView;
                if (qSAnimView2 != null) {
                    qSAnimView2.setPivotX((qSAnimView2.getView() != null ? r10.getWidth() : 2) / 2.0f);
                }
                QSAnimView qSAnimView3 = this.largeShadowView;
                if (qSAnimView3 != null) {
                    qSAnimView3.setPivotY(0.0f);
                }
                QSAnimView qSAnimView4 = this.smallShadowView;
                if (qSAnimView4 != null) {
                    qSAnimView4.setPivotX((qSAnimView4.getView() != null ? r10.getWidth() : 2) / 2.0f);
                }
                QSAnimView qSAnimView5 = this.smallShadowView;
                if (qSAnimView5 != null) {
                    qSAnimView5.setPivotY(0.0f);
                }
            }
            ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
            this.blurAnimator = ofFloat;
            if (ofFloat != null) {
                ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.qs.animator.PanelTransitionAnimator$updateAnimators$1$1
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                        float f3 = floatValue <= 0.5f ? 1.0f - (floatValue / 0.5f) : (floatValue - 0.5f) / 0.5f;
                        QSAnimView.this.setAlpha(f3);
                        QSAnimView qSAnimView6 = this.largeShadowView;
                        if (qSAnimView6 != null) {
                            qSAnimView6.setAlpha(f3);
                        }
                        QSAnimView qSAnimView7 = this.smallShadowView;
                        if (qSAnimView7 != null) {
                            qSAnimView7.setAlpha(f3);
                        }
                        QSAnimView qSAnimView8 = this.lessBlurView;
                        if (qSAnimView8 != null) {
                            qSAnimView8.setAlpha(f3);
                        }
                        QSAnimView qSAnimView9 = this.blurParent;
                        if (qSAnimView9 != null) {
                            qSAnimView9.setTranslationY(150 - (f3 * 150.0f));
                        }
                        QSAnimView qSAnimView10 = this.largeShadowView;
                        if (qSAnimView10 != null) {
                            qSAnimView10.setTranslationY(150 - (f3 * 150.0f));
                        }
                        QSAnimView qSAnimView11 = this.smallShadowView;
                        if (qSAnimView11 != null) {
                            qSAnimView11.setTranslationY(150 - (150.0f * f3));
                        }
                        PanelTransitionAnimator panelTransitionAnimator = this;
                        QSAnimView qSAnimView12 = panelTransitionAnimator.blurParent;
                        if (qSAnimView12 != null) {
                            qSAnimView12.setScaleX((f3 / 10) + panelTransitionAnimator.SCALE_DOWN_RATIO);
                        }
                        PanelTransitionAnimator panelTransitionAnimator2 = this;
                        QSAnimView qSAnimView13 = panelTransitionAnimator2.largeShadowView;
                        if (qSAnimView13 != null) {
                            qSAnimView13.setScaleX((f3 / 10) + panelTransitionAnimator2.SCALE_DOWN_RATIO);
                        }
                        PanelTransitionAnimator panelTransitionAnimator3 = this;
                        QSAnimView qSAnimView14 = panelTransitionAnimator3.smallShadowView;
                        if (qSAnimView14 != null) {
                            qSAnimView14.setScaleX((f3 / 10) + panelTransitionAnimator3.SCALE_DOWN_RATIO);
                        }
                        PanelTransitionAnimator panelTransitionAnimator4 = this;
                        QSAnimView qSAnimView15 = panelTransitionAnimator4.blurParent;
                        if (qSAnimView15 != null) {
                            qSAnimView15.setScaleY((f3 / 10) + panelTransitionAnimator4.SCALE_DOWN_RATIO);
                        }
                        PanelTransitionAnimator panelTransitionAnimator5 = this;
                        QSAnimView qSAnimView16 = panelTransitionAnimator5.largeShadowView;
                        if (qSAnimView16 != null) {
                            qSAnimView16.setScaleY((f3 / 10) + panelTransitionAnimator5.SCALE_DOWN_RATIO);
                        }
                        PanelTransitionAnimator panelTransitionAnimator6 = this;
                        QSAnimView qSAnimView17 = panelTransitionAnimator6.smallShadowView;
                        if (qSAnimView17 != null) {
                            qSAnimView17.setScaleY((f3 / 10) + panelTransitionAnimator6.SCALE_DOWN_RATIO);
                        }
                    }
                });
            }
        }
        QSAnimView qSAnimView6 = this.qsRootView;
        float f3 = this.SCALE_DOWN_RATIO;
        if (qSAnimView6 != null) {
            c = 0;
            TouchAnimator.Builder builder = new TouchAnimator.Builder();
            f = 2.0f;
            builder.addFloat(qSAnimView6, "alpha", 0.0f, 1.0f);
            builder.addFloat(qSAnimView6, "translationY", -150.0f, 0.0f);
            builder.addFloat(qSAnimView6, "scaleX", f3, 1.0f);
            builder.addFloat(qSAnimView6, "scaleY", f3, 1.0f);
            builder.mStartDelay = 0.5f;
            this.qsFadeInDown = builder.build();
            TouchAnimator.Builder builder2 = new TouchAnimator.Builder();
            builder2.addFloat(qSAnimView6, "alpha", 0.0f, 1.0f);
            builder2.addFloat(qSAnimView6, "scaleX", f3, 1.0f);
            builder2.addFloat(qSAnimView6, "scaleY", f3, 1.0f);
            builder2.mStartDelay = 0.5f;
            this.qsLockScreenFadeInDown = builder2.build();
            TouchAnimator.Builder builder3 = new TouchAnimator.Builder();
            f2 = 1.0f;
            builder3.addFloat(qSAnimView6, "translationY", -this.barYDiff, 0.0f);
            this.qsLockScreenTranslattionDown = builder3.build();
            TouchAnimator.Builder builder4 = new TouchAnimator.Builder();
            builder4.addFloat(qSAnimView6, "alpha", 1.0f, 0.0f);
            builder4.addFloat(qSAnimView6, "translationY", 0.0f, 150.0f);
            builder4.addFloat(qSAnimView6, "scaleX", 1.0f, f3);
            builder4.addFloat(qSAnimView6, "scaleY", 1.0f, f3);
            builder4.mEndDelay = 0.5f;
            this.qsFadeOutDown = builder4.build();
            TouchAnimator.Builder builder5 = new TouchAnimator.Builder();
            builder5.addFloat(qSAnimView6, "alpha", 0.0f, 1.0f);
            builder5.addFloat(qSAnimView6, "translationX", 300.0f, 0.0f);
            builder5.mStartDelay = 0.5f;
            this.qsFadeInLeft = builder5.build();
            TouchAnimator.Builder builder6 = new TouchAnimator.Builder();
            builder6.addFloat(qSAnimView6, "alpha", 1.0f, 0.0f);
            builder6.addFloat(qSAnimView6, "translationX", 0.0f, 300.0f);
            builder6.mEndDelay = 0.5f;
            this.qsFadeOutRight = builder6.build();
            TouchAnimator.Builder builder7 = new TouchAnimator.Builder();
            builder7.addFloat(qSAnimView6, "alpha", 0.0f, 1.0f);
            builder7.addFloat(qSAnimView6, "translationX", -300.0f, 0.0f);
            builder7.mStartDelay = 0.5f;
            this.qsFadeInRight = builder7.build();
            TouchAnimator.Builder builder8 = new TouchAnimator.Builder();
            builder8.addFloat(qSAnimView6, "alpha", 1.0f, 0.0f);
            builder8.addFloat(qSAnimView6, "translationX", 0.0f, -300.0f);
            builder8.mEndDelay = 0.5f;
            this.qsFadeOutLeft = builder8.build();
        } else {
            f = 2.0f;
            f2 = 1.0f;
            c = 0;
        }
        QSAnimView qSAnimView7 = this.header;
        if (qSAnimView7 != null) {
            TouchAnimator.Builder builder9 = new TouchAnimator.Builder();
            builder9.addFloat(qSAnimView7, "alpha", 0.0f, 1.0f);
            builder9.addFloat(qSAnimView7, "translationY", -50.0f, 0.0f);
            this.headerAnimator = builder9.build();
        }
        QSAnimView qSAnimView8 = this.shadeRootView;
        if (qSAnimView8 != null) {
            if (secQsUiDisplayModeInteractor.isTablet()) {
                qSAnimView8.setPivotX((qSAnimView8.getView() != null ? r3.getWidth() : 2) / f);
            }
            TouchAnimator.Builder builder10 = new TouchAnimator.Builder();
            builder10.addFloat(qSAnimView8, "alpha", 0.0f, 1.0f);
            builder10.addFloat(qSAnimView8, "translationY", -150.0f, 0.0f);
            float[] fArr = new float[2];
            fArr[c] = f3;
            fArr[1] = f2;
            builder10.addFloat(qSAnimView8, "scaleX", fArr);
            float[] fArr2 = new float[2];
            fArr2[c] = f3;
            fArr2[1] = f2;
            builder10.addFloat(qSAnimView8, "scaleY", fArr2);
            builder10.mStartDelay = 0.5f;
            this.shadeFadeInDown = builder10.build();
            TouchAnimator.Builder builder11 = new TouchAnimator.Builder();
            builder11.addFloat(qSAnimView8, "alpha", 1.0f, 0.0f);
            builder11.addFloat(qSAnimView8, "translationY", 0.0f, 150.0f);
            float[] fArr3 = new float[2];
            fArr3[c] = f2;
            fArr3[1] = f3;
            builder11.addFloat(qSAnimView8, "scaleX", fArr3);
            float[] fArr4 = new float[2];
            fArr4[c] = f2;
            fArr4[1] = f3;
            builder11.addFloat(qSAnimView8, "scaleY", fArr4);
            builder11.mEndDelay = 0.5f;
            this.shadeFadeOutDown = builder11.build();
            TouchAnimator.Builder builder12 = new TouchAnimator.Builder();
            builder12.addFloat(qSAnimView8, "alpha", 1.0f, 0.0f);
            builder12.addFloat(qSAnimView8, "translationY", 0.0f, 150.0f);
            builder12.mEndDelay = 0.5f;
            this.shadeLockScreenFadeOutDown = builder12.build();
            TouchAnimator.Builder builder13 = new TouchAnimator.Builder();
            builder13.addFloat(qSAnimView8, "alpha", 1.0f, 0.0f);
            builder13.addFloat(qSAnimView8, "translationX", 0.0f, -300.0f);
            builder13.mEndDelay = 0.5f;
            this.shadeFadeOutLeft = builder13.build();
            TouchAnimator.Builder builder14 = new TouchAnimator.Builder();
            builder14.addFloat(qSAnimView8, "alpha", 0.0f, 1.0f);
            builder14.addFloat(qSAnimView8, "translationX", -300.0f, 0.0f);
            builder14.mStartDelay = 0.5f;
            this.shadeFadeInRight = builder14.build();
            TouchAnimator.Builder builder15 = new TouchAnimator.Builder();
            builder15.addFloat(qSAnimView8, "alpha", 1.0f, 0.0f);
            builder15.addFloat(qSAnimView8, "translationX", 0.0f, 300.0f);
            builder15.mEndDelay = 0.5f;
            this.shadeFadeOutRight = builder15.build();
            TouchAnimator.Builder builder16 = new TouchAnimator.Builder();
            builder16.addFloat(qSAnimView8, "alpha", 0.0f, 1.0f);
            builder16.addFloat(qSAnimView8, "translationX", 300.0f, 0.0f);
            builder16.mStartDelay = 0.5f;
            this.shadeFadeInLeft = builder16.build();
        }
        final QSAnimView qSAnimView9 = this.buttonContainer;
        if (qSAnimView9 != null) {
            TouchAnimator.Builder builder17 = new TouchAnimator.Builder();
            builder17.addFloat(qSAnimView9, "alpha", 0.0f, 1.0f);
            builder17.addFloat(qSAnimView9, "translationX", 15.0f, 0.0f);
            builder17.mListener = new TouchAnimator.Listener() { // from class: com.android.systemui.qs.animator.PanelTransitionAnimator$updateAnimators$5$1
                @Override // com.android.systemui.qs.TouchAnimator.Listener
                public final void onAnimationStarted() {
                    int i = PanelTransitionAnimator.$r8$clinit;
                    PanelTransitionAnimator.this.getClass();
                    PanelTransitionAnimator.updateVisibility(qSAnimView9, true);
                }

                @Override // com.android.systemui.qs.TouchAnimator.Listener
                public final void onAnimationAtEnd() {
                }

                @Override // com.android.systemui.qs.TouchAnimator.Listener
                public final void onAnimationAtStart() {
                }
            };
            this.buttonFadeInLeftAnimator = builder17.build();
            TouchAnimator.Builder builder18 = new TouchAnimator.Builder();
            builder18.addFloat(qSAnimView9, "alpha", 0.0f, 1.0f);
            builder18.addFloat(qSAnimView9, "translationX", -15.0f, 0.0f);
            builder18.mListener = new TouchAnimator.Listener() { // from class: com.android.systemui.qs.animator.PanelTransitionAnimator$updateAnimators$5$2
                @Override // com.android.systemui.qs.TouchAnimator.Listener
                public final void onAnimationStarted() {
                    int i = PanelTransitionAnimator.$r8$clinit;
                    PanelTransitionAnimator.this.getClass();
                    PanelTransitionAnimator.updateVisibility(qSAnimView9, true);
                }

                @Override // com.android.systemui.qs.TouchAnimator.Listener
                public final void onAnimationAtEnd() {
                }

                @Override // com.android.systemui.qs.TouchAnimator.Listener
                public final void onAnimationAtStart() {
                }
            };
            this.buttonFadeInRightAnimator = builder18.build();
            TouchAnimator.Builder builder19 = new TouchAnimator.Builder();
            builder19.addFloat(qSAnimView9, "alpha", 1.0f, 0.0f);
            builder19.addFloat(qSAnimView9, "translationX", 0.0f, 15.0f);
            this.buttonFadeOutRightAnimator = builder19.build();
            TouchAnimator.Builder builder20 = new TouchAnimator.Builder();
            builder20.addFloat(qSAnimView9, "alpha", 1.0f, 0.0f);
            builder20.addFloat(qSAnimView9, "translationX", 0.0f, -15.0f);
            this.buttonFadeOutLeftAnimator = builder20.build();
            TouchAnimator.Builder builder21 = new TouchAnimator.Builder();
            builder21.addFloat(qSAnimView9, "alpha", 0.0f, 1.0f);
            builder21.addFloat(qSAnimView9, "translationY", -30.0f, 0.0f);
            builder21.mStartDelay = secQsUiDisplayModeInteractor.isTablet() ? 0.5f : 0.0f;
            builder21.mListener = new TouchAnimator.Listener() { // from class: com.android.systemui.qs.animator.PanelTransitionAnimator$updateAnimators$5$3
                @Override // com.android.systemui.qs.TouchAnimator.Listener
                public final void onAnimationStarted() {
                    int i = PanelTransitionAnimator.$r8$clinit;
                    PanelTransitionAnimator.this.getClass();
                    PanelTransitionAnimator.updateVisibility(qSAnimView9, true);
                }

                @Override // com.android.systemui.qs.TouchAnimator.Listener
                public final void onAnimationAtEnd() {
                }

                @Override // com.android.systemui.qs.TouchAnimator.Listener
                public final void onAnimationAtStart() {
                }
            };
            this.buttonFadeInDownAnimator = builder21.build();
            TouchAnimator.Builder builder22 = new TouchAnimator.Builder();
            builder22.addFloat(qSAnimView9, "alpha", 1.0f, 0.0f);
            builder22.mEndDelay = secQsUiDisplayModeInteractor.isTablet() ? 0.5f : 0.0f;
            this.buttonFadeOutDownAnimator = builder22.build();
            TouchAnimator.Builder builder23 = new TouchAnimator.Builder();
            builder23.addFloat(qSAnimView9, "alpha", 0.0f, 1.0f);
            int m = MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0.m(this.context);
            float f4 = this.xDiff;
            if (m == 1) {
                f4 = -f4;
            }
            float[] fArr5 = new float[2];
            fArr5[c] = f4;
            fArr5[1] = 0.0f;
            builder23.addFloat(qSAnimView9, "translationX", fArr5);
            float[] fArr6 = new float[2];
            fArr6[c] = -this.buttonYDiff;
            fArr6[1] = 0.0f;
            builder23.addFloat(qSAnimView9, "translationY", fArr6);
            builder23.addFloat(qSAnimView9, "scaleX", 0.8f, 1.0f);
            builder23.addFloat(qSAnimView9, "scaleY", 0.8f, 1.0f);
            builder23.mStartDelay = secQsUiDisplayModeInteractor.isTablet() ? 0.5f : 0.0f;
            this.buttonLockScreenFadeInAnimator = builder23.build();
        }
        QSAnimView qSAnimView10 = this.plmn;
        if (qSAnimView10 != null) {
            TouchAnimator.Builder builder24 = new TouchAnimator.Builder();
            builder24.addFloat(qSAnimView10, "alpha", 0.0f, 1.0f);
            this.carrierFadeInLeftAnimator = builder24.build();
            TouchAnimator.Builder builder25 = new TouchAnimator.Builder();
            builder25.addFloat(qSAnimView10, "alpha", 0.0f, 1.0f);
            this.carrierFadeInRightAnimator = builder25.build();
            TouchAnimator.Builder builder26 = new TouchAnimator.Builder();
            builder26.addFloat(qSAnimView10, "alpha", 1.0f, 0.0f);
            this.carrierFadeOutRightAnimator = builder26.build();
            TouchAnimator.Builder builder27 = new TouchAnimator.Builder();
            builder27.addFloat(qSAnimView10, "alpha", 1.0f, 0.0f);
            this.carrierFadeOutLeftAnimator = builder27.build();
        }
        final QSAnimView qSAnimView11 = this.clockDateContainer;
        if (qSAnimView11 != null) {
            TouchAnimator.Builder builder28 = new TouchAnimator.Builder();
            builder28.addFloat(qSAnimView11, "alpha", 0.0f, 1.0f);
            builder28.addFloat(qSAnimView11, "translationX", -15.0f, 0.0f);
            builder28.mListener = new TouchAnimator.Listener() { // from class: com.android.systemui.qs.animator.PanelTransitionAnimator$updateAnimators$7$1
                @Override // com.android.systemui.qs.TouchAnimator.Listener
                public final void onAnimationStarted() {
                    int i = PanelTransitionAnimator.$r8$clinit;
                    PanelTransitionAnimator.this.getClass();
                    PanelTransitionAnimator.updateVisibility(qSAnimView11, true);
                }

                @Override // com.android.systemui.qs.TouchAnimator.Listener
                public final void onAnimationAtEnd() {
                }

                @Override // com.android.systemui.qs.TouchAnimator.Listener
                public final void onAnimationAtStart() {
                }
            };
            this.clockDateContainerFadeInRightAnimator = builder28.build();
            TouchAnimator.Builder builder29 = new TouchAnimator.Builder();
            builder29.addFloat(qSAnimView11, "alpha", 0.0f, 1.0f);
            builder29.addFloat(qSAnimView11, "translationX", 15.0f, 0.0f);
            builder29.mListener = new TouchAnimator.Listener() { // from class: com.android.systemui.qs.animator.PanelTransitionAnimator$updateAnimators$7$2
                @Override // com.android.systemui.qs.TouchAnimator.Listener
                public final void onAnimationStarted() {
                    int i = PanelTransitionAnimator.$r8$clinit;
                    PanelTransitionAnimator.this.getClass();
                    PanelTransitionAnimator.updateVisibility(qSAnimView11, true);
                }

                @Override // com.android.systemui.qs.TouchAnimator.Listener
                public final void onAnimationAtEnd() {
                }

                @Override // com.android.systemui.qs.TouchAnimator.Listener
                public final void onAnimationAtStart() {
                }
            };
            this.clockDateContainerFadeInLeftAnimator = builder29.build();
            TouchAnimator.Builder builder30 = new TouchAnimator.Builder();
            builder30.addFloat(qSAnimView11, "alpha", 1.0f, 0.0f);
            builder30.addFloat(qSAnimView11, "translationX", 0.0f, -15.0f);
            this.clockDateContainerFadeOutLeftAnimator = builder30.build();
            TouchAnimator.Builder builder31 = new TouchAnimator.Builder();
            builder31.addFloat(qSAnimView11, "alpha", 1.0f, 0.0f);
            builder31.addFloat(qSAnimView11, "translationX", 0.0f, 15.0f);
            this.clockDateContainerFadeOutRightAnimator = builder31.build();
            TouchAnimator.Builder builder32 = new TouchAnimator.Builder();
            builder32.addFloat(qSAnimView11, "alpha", 0.0f, 1.0f);
            builder32.addFloat(qSAnimView11, "translationY", -30.0f, 0.0f);
            builder32.mStartDelay = secQsUiDisplayModeInteractor.isTablet() ? 0.5f : 0.0f;
            builder32.mListener = new TouchAnimator.Listener() { // from class: com.android.systemui.qs.animator.PanelTransitionAnimator$updateAnimators$7$3
                @Override // com.android.systemui.qs.TouchAnimator.Listener
                public final void onAnimationStarted() {
                    int i = PanelTransitionAnimator.$r8$clinit;
                    PanelTransitionAnimator.this.getClass();
                    PanelTransitionAnimator.updateVisibility(qSAnimView11, true);
                }

                @Override // com.android.systemui.qs.TouchAnimator.Listener
                public final void onAnimationAtEnd() {
                }

                @Override // com.android.systemui.qs.TouchAnimator.Listener
                public final void onAnimationAtStart() {
                }
            };
            this.clockDateContainerFadeInDownAnimator = builder32.build();
            TouchAnimator.Builder builder33 = new TouchAnimator.Builder();
            builder33.addFloat(qSAnimView11, "alpha", 1.0f, 0.0f);
            builder33.mEndDelay = secQsUiDisplayModeInteractor.isTablet() ? 0.5f : 0.0f;
            this.clockDateContainerFadeOutDownAnimator = builder33.build();
        }
    }
}
