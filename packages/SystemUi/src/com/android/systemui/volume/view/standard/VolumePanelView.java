package com.android.systemui.volume.view.standard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.dynamicanimation.animation.SpringAnimation;
import com.android.keyguard.SecLockIconView$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.volume.VolumeDependencyBase;
import com.android.systemui.volume.store.StoreInteractor;
import com.android.systemui.volume.store.VolumePanelStore;
import com.android.systemui.volume.util.BlurEffect;
import com.android.systemui.volume.util.ContextUtils;
import com.android.systemui.volume.util.HandlerWrapper;
import com.android.systemui.volume.util.IDisplayManagerWrapper;
import com.android.systemui.volume.util.PluginAODManagerWrapper;
import com.android.systemui.volume.util.PowerManagerWrapper;
import com.android.systemui.volume.util.VibratorWrapper;
import com.android.systemui.volume.util.ViewUtil;
import com.android.systemui.volume.util.ViewVisibilityUtil;
import com.android.systemui.volume.view.VolumePanelMotion;
import com.android.systemui.volume.view.VolumePanelViewExt;
import com.android.systemui.volume.view.VolumeRowView;
import com.android.systemui.volume.view.context.ViewContext;
import com.android.systemui.volume.view.expand.AbstractC3626xb6ef341d;
import com.android.systemui.volume.view.warnings.WarningDialogController;
import com.samsung.systemui.splugins.extensions.VolumePanelRowExt;
import com.samsung.systemui.splugins.extensions.VolumePanelStateExt;
import com.samsung.systemui.splugins.volume.VolumeObserver;
import com.samsung.systemui.splugins.volume.VolumePanelAction;
import com.samsung.systemui.splugins.volume.VolumePanelRow;
import com.samsung.systemui.splugins.volume.VolumePanelState;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.collections.ReversedListReadOnly;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt__MathJVMKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class VolumePanelView extends FrameLayout implements ViewContext, VolumeObserver<VolumePanelState> {
    public int activeStream;
    public BlurEffect blurEffect;
    public int currentVolume;
    public Dialog dialog;
    public float downX;
    public float downY;
    public TextView dualViewTitle;
    public ImageView expandButton;
    public HandlerWrapper handlerWrapper;
    public IDisplayManagerWrapper iDisplayManagerWrapper;
    public boolean isDragging;
    public boolean isDualViewEnabled;
    public boolean isFirstTouch;
    public boolean isKeyDownAnimating;
    public boolean isLockscreen;
    public boolean isSeekBarTouching;
    public boolean isSwipe;
    public boolean isTouchDown;
    public SpringAnimation keyDownAnimation;
    public SpringAnimation keyUpAnimation;
    public final VolumePanelView$keyUpRunnable$1 keyUpRunnable;
    public VolumePanelState panelState;
    public PluginAODManagerWrapper pluginAODManagerWrapper;
    public PowerManagerWrapper powerManagerWrapper;
    public ViewGroup rowContainer;
    public boolean startProgress;
    public VolumePanelStore store;
    public final StoreInteractor storeInteractor;
    public float swipeDistance;
    public SpringAnimation touchDownAnimation;
    public boolean touchDownExpandButton;
    public SpringAnimation touchUpAnimation;
    public VibratorWrapper vibratorWrapper;
    public VolumeDependencyBase volDeps;
    public ViewGroup volumeAODPanelView;
    public ViewGroup volumePanelDualView;
    public VolumePanelMotion volumePanelMotion;
    public ViewGroup volumePanelView;
    public final Lazy warningDialogController$delegate;

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
                iArr[VolumePanelState.StateType.STATE_SEEKBAR_START_PROGRESS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_SEEKBAR_TOUCH_DOWN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_SEEKBAR_TOUCH_UP.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_UPDATE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_SHOW_VOLUME_SAFETY_WARNING_DIALOG.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_SHOW_VOLUME_LIMITER_DIALOG.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_SHOW_VOLUME_CSD_100_WARNING_DIALOG.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_UPDATE_PROGRESS_BAR.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_KEY_EVENT.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.volume.view.standard.VolumePanelView$keyUpRunnable$1] */
    public VolumePanelView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.storeInteractor = new StoreInteractor(this, null);
        this.keyUpRunnable = new Runnable() { // from class: com.android.systemui.volume.view.standard.VolumePanelView$keyUpRunnable$1
            @Override // java.lang.Runnable
            public final void run() {
                VolumePanelView volumePanelView = VolumePanelView.this;
                volumePanelView.isKeyDownAnimating = false;
                VolumePanelMotion volumePanelMotion = volumePanelView.volumePanelMotion;
                if (volumePanelMotion == null) {
                    volumePanelMotion = null;
                }
                SpringAnimation springAnimation = volumePanelView.keyUpAnimation;
                if (springAnimation == null) {
                    springAnimation = null;
                }
                SpringAnimation springAnimation2 = volumePanelView.keyDownAnimation;
                SpringAnimation springAnimation3 = springAnimation2 != null ? springAnimation2 : null;
                volumePanelMotion.getClass();
                VolumePanelMotion.startSeekBarKeyUpAnimation(springAnimation, springAnimation3);
            }
        };
        this.warningDialogController$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.volume.view.standard.VolumePanelView$warningDialogController$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return new WarningDialogController(VolumePanelView.this);
            }
        });
    }

    public final void addVolumeRows(VolumePanelState volumePanelState) {
        ViewGroup viewGroup = this.rowContainer;
        if (viewGroup == null) {
            viewGroup = null;
        }
        viewGroup.removeAllViews();
        List<VolumePanelRow> volumeRowList = volumePanelState.getVolumeRowList();
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = volumeRowList.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Object next = it.next();
            VolumePanelRow volumePanelRow = (VolumePanelRow) next;
            if (VolumePanelStateExt.isActiveStream(volumePanelState, volumePanelRow.getStreamType()) || volumePanelRow.isVisible()) {
                arrayList.add(next);
            }
        }
        ReversedListReadOnly<VolumePanelRow> reversedListReadOnly = new ReversedListReadOnly(arrayList);
        Log.d("VolumePanelView", "addRows: rows=" + VolumePanelRowExt.listToString(reversedListReadOnly));
        for (VolumePanelRow volumePanelRow2 : reversedListReadOnly) {
            VolumeRowView volumeRowView = (VolumeRowView) LayoutInflater.from(getContext()).inflate(R.layout.volume_row_view, (ViewGroup) null);
            VolumePanelStore store = getStore();
            HandlerWrapper handlerWrapper = this.handlerWrapper;
            HandlerWrapper handlerWrapper2 = handlerWrapper == null ? null : handlerWrapper;
            VolumePanelMotion volumePanelMotion = this.volumePanelMotion;
            volumeRowView.initialize(store, handlerWrapper2, volumePanelRow2, volumePanelState, volumePanelMotion == null ? null : volumePanelMotion);
            ViewGroup viewGroup2 = this.rowContainer;
            if (viewGroup2 == null) {
                viewGroup2 = null;
            }
            viewGroup2.addView(volumeRowView);
            if (VolumePanelStateExt.isActiveStream(volumePanelState, volumePanelRow2.getStreamType())) {
                this.activeStream = volumePanelRow2.getStreamType();
            }
            if (!this.isDualViewEnabled) {
                ViewVisibilityUtil viewVisibilityUtil = ViewVisibilityUtil.INSTANCE;
                View findViewById = volumeRowView.findViewById(R.id.volume_seekbar_outline_stroke);
                viewVisibilityUtil.getClass();
                findViewById.setVisibility(0);
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        View view;
        this.storeInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_TOUCH_PANEL).isFromOutside(true).build(), false);
        int action = motionEvent.getAction();
        if (action != 0) {
            if (action != 1) {
                if (action == 2) {
                    float x = motionEvent.getX();
                    float f = x - this.downX;
                    if (VolumePanelStateExt.isAODVolumePanel(getPanelState())) {
                        float y = this.downY - motionEvent.getY();
                        if (this.isDragging) {
                            ViewUtil viewUtil = ViewUtil.INSTANCE;
                            ViewGroup viewGroup = this.rowContainer;
                            view = viewGroup != null ? viewGroup : null;
                            float rawX = motionEvent.getRawX();
                            float rawY = motionEvent.getRawY();
                            viewUtil.getClass();
                            if (!ViewUtil.isTouched(view, rawX, rawY)) {
                                float f2 = ((y / 300.0f) * 1500.0f) + this.currentVolume;
                                if (f2 < 0.0f) {
                                    f2 = 0.0f;
                                }
                                int roundToInt = MathKt__MathJVMKt.roundToInt(f2 <= 1500.0f ? f2 : 1500.0f);
                                if (this.isDragging) {
                                    this.storeInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_UPDATE_PROGRESS_BAR).stream(this.activeStream).progress(roundToInt).isFromOutside(true).build(), false);
                                }
                            }
                        }
                    } else if (this.isTouchDown && Math.abs(f) > this.swipeDistance && !this.startProgress && !this.isLockscreen) {
                        this.isTouchDown = false;
                        this.isSwipe = true;
                        AbstractC3626xb6ef341d.m227m(new VolumePanelAction.Builder(!BasicRune.VOLUME_LEFT_DISPLAY_VOLUME_DIALOG ? (x > this.downX ? 1 : (x == this.downX ? 0 : -1)) > 0 : (x > this.downX ? 1 : (x == this.downX ? 0 : -1)) < 0 ? VolumePanelAction.ActionType.ACTION_SWIPE_COLLAPSED : VolumePanelAction.ActionType.ACTION_SWIPE_PANEL), true, this.storeInteractor, false);
                    }
                } else if (action != 3) {
                    if (action == 4) {
                        if (VolumePanelStateExt.isAODVolumePanel(getPanelState())) {
                            this.isTouchDown = false;
                            this.isDragging = false;
                        } else {
                            AbstractC3626xb6ef341d.m227m(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_TOUCH_OUTSIDE), true, this.storeInteractor, false);
                            this.touchDownExpandButton = false;
                        }
                        this.startProgress = false;
                        return true;
                    }
                }
            }
            if (!VolumePanelViewExt.isIconClickWillConsume(this, motionEvent)) {
                boolean z = this.startProgress;
                if (!z && this.touchDownExpandButton) {
                    ViewUtil viewUtil2 = ViewUtil.INSTANCE;
                    ImageView imageView = this.expandButton;
                    view = imageView != null ? imageView : null;
                    float rawX2 = motionEvent.getRawX();
                    float rawY2 = motionEvent.getRawY();
                    viewUtil2.getClass();
                    if (ViewUtil.isTouched(view, rawX2, rawY2)) {
                        AbstractC3626xb6ef341d.m227m(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_EXPAND_BUTTON_CLICKED), true, this.storeInteractor, false);
                    }
                } else if (!this.isSwipe && !this.isSeekBarTouching && !z && !this.isDualViewEnabled && !VolumePanelStateExt.isAODVolumePanel(getPanelState())) {
                    AbstractC3626xb6ef341d.m227m(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_TOUCH_OUTSIDE), true, this.storeInteractor, false);
                    return true;
                }
                this.isDragging = false;
                this.isFirstTouch = false;
                this.touchDownExpandButton = false;
                this.startProgress = false;
                this.isSeekBarTouching = false;
                if (this.isSwipe) {
                    this.isSwipe = false;
                    return true;
                }
            }
        } else {
            if (VolumePanelStateExt.isAODVolumePanel(getPanelState())) {
                if (!this.isFirstTouch) {
                    ViewUtil viewUtil3 = ViewUtil.INSTANCE;
                    ViewGroup viewGroup2 = this.rowContainer;
                    if (viewGroup2 == null) {
                        viewGroup2 = null;
                    }
                    float rawX3 = motionEvent.getRawX();
                    float rawY3 = motionEvent.getRawY();
                    viewUtil3.getClass();
                    if (!ViewUtil.isTouched(viewGroup2, rawX3, rawY3)) {
                        AbstractC3626xb6ef341d.m227m(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_TOUCH_OUTSIDE), true, this.storeInteractor, false);
                        this.touchDownExpandButton = false;
                        this.startProgress = false;
                        return true;
                    }
                }
                this.isDragging = true;
            }
            if (!this.isLockscreen) {
                ViewUtil viewUtil4 = ViewUtil.INSTANCE;
                ImageView imageView2 = this.expandButton;
                view = imageView2 != null ? imageView2 : null;
                float rawX4 = motionEvent.getRawX();
                float rawY4 = motionEvent.getRawY();
                viewUtil4.getClass();
                if (ViewUtil.isTouched(view, rawX4, rawY4)) {
                    this.touchDownExpandButton = true;
                }
            }
            this.downY = motionEvent.getY();
            this.downX = motionEvent.getX();
            this.isSwipe = false;
            this.isTouchDown = true;
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    @Override // com.android.systemui.volume.view.context.ViewContext
    public final VolumePanelState getPanelState() {
        VolumePanelState volumePanelState = this.panelState;
        if (volumePanelState != null) {
            return volumePanelState;
        }
        return null;
    }

    @Override // com.android.systemui.volume.view.context.ViewContext
    public final VolumePanelStore getStore() {
        VolumePanelStore volumePanelStore = this.store;
        if (volumePanelStore != null) {
            return volumePanelStore;
        }
        return null;
    }

    @Override // com.android.systemui.volume.view.context.ViewContext
    public final VolumeDependencyBase getVolDeps() {
        VolumeDependencyBase volumeDependencyBase = this.volDeps;
        if (volumeDependencyBase != null) {
            return volumeDependencyBase;
        }
        return null;
    }

    public final void initViewVisibility(VolumePanelState volumePanelState) {
        ImageView imageView;
        if (VolumePanelStateExt.isAODVolumePanel(volumePanelState)) {
            ViewVisibilityUtil viewVisibilityUtil = ViewVisibilityUtil.INSTANCE;
            ViewGroup viewGroup = this.volumePanelView;
            if (viewGroup == null) {
                viewGroup = null;
            }
            viewVisibilityUtil.getClass();
            viewGroup.setVisibility(0);
            ViewGroup viewGroup2 = this.volumePanelDualView;
            if (viewGroup2 == null) {
                viewGroup2 = null;
            }
            ViewVisibilityUtil.setGone(viewGroup2);
            ViewGroup viewGroup3 = this.volumeAODPanelView;
            if (viewGroup3 == null) {
                viewGroup3 = null;
            }
            viewGroup3.setVisibility(0);
        } else if (this.isDualViewEnabled) {
            ViewVisibilityUtil viewVisibilityUtil2 = ViewVisibilityUtil.INSTANCE;
            ViewGroup viewGroup4 = this.volumePanelView;
            if (viewGroup4 == null) {
                viewGroup4 = null;
            }
            viewVisibilityUtil2.getClass();
            ViewVisibilityUtil.setGone(viewGroup4);
            ViewGroup viewGroup5 = this.volumePanelDualView;
            if (viewGroup5 == null) {
                viewGroup5 = null;
            }
            viewGroup5.setVisibility(0);
            ViewGroup viewGroup6 = this.volumeAODPanelView;
            if (viewGroup6 == null) {
                viewGroup6 = null;
            }
            ViewVisibilityUtil.setGone(viewGroup6);
            if (volumePanelState.isLockscreen()) {
                TextView textView = this.dualViewTitle;
                if (textView == null) {
                    textView = null;
                }
                textView.setVisibility(0);
            } else {
                TextView textView2 = this.dualViewTitle;
                if (textView2 == null) {
                    textView2 = null;
                }
                ViewVisibilityUtil.setGone(textView2);
            }
            ViewGroup viewGroup7 = this.volumePanelDualView;
            if (viewGroup7 == null) {
                viewGroup7 = null;
            }
            ViewGroup viewGroup8 = (ViewGroup) viewGroup7.findViewById(R.id.volume_panel_expand_view_background_stroke);
            if (((SettingsHelper) Dependency.get(SettingsHelper.class)).isReduceTransparencyEnabled()) {
                ViewVisibilityUtil.setGone(viewGroup8);
            } else if (ContextUtils.isNightMode(getContext())) {
                ViewVisibilityUtil.setGone(viewGroup8);
            } else {
                viewGroup8.setVisibility(0);
            }
        } else {
            ViewVisibilityUtil viewVisibilityUtil3 = ViewVisibilityUtil.INSTANCE;
            ViewGroup viewGroup9 = this.volumePanelView;
            if (viewGroup9 == null) {
                viewGroup9 = null;
            }
            viewVisibilityUtil3.getClass();
            viewGroup9.setVisibility(0);
            ViewGroup viewGroup10 = this.volumePanelDualView;
            if (viewGroup10 == null) {
                viewGroup10 = null;
            }
            ViewVisibilityUtil.setGone(viewGroup10);
            ViewGroup viewGroup11 = this.volumeAODPanelView;
            if (viewGroup11 == null) {
                viewGroup11 = null;
            }
            ViewVisibilityUtil.setGone(viewGroup11);
        }
        if (volumePanelState.isLockscreen()) {
            ViewVisibilityUtil viewVisibilityUtil4 = ViewVisibilityUtil.INSTANCE;
            ImageView imageView2 = this.expandButton;
            imageView = imageView2 != null ? imageView2 : null;
            viewVisibilityUtil4.getClass();
            ViewVisibilityUtil.setGone(imageView);
            return;
        }
        ViewVisibilityUtil viewVisibilityUtil5 = ViewVisibilityUtil.INSTANCE;
        ImageView imageView3 = this.expandButton;
        imageView = imageView3 != null ? imageView3 : null;
        viewVisibilityUtil5.getClass();
        imageView.setVisibility(0);
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeObserver
    public final void onChanged(VolumePanelState volumePanelState) {
        VolumePanelRow findRow;
        VolumePanelState volumePanelState2 = volumePanelState;
        this.panelState = volumePanelState2;
        if (volumePanelState2.isShowingSubDisplayVolumePanel()) {
            return;
        }
        boolean z = false;
        switch (WhenMappings.$EnumSwitchMapping$0[volumePanelState2.getStateType().ordinal()]) {
            case 1:
                this.startProgress = true;
                break;
            case 2:
                this.isSeekBarTouching = true;
                if (!this.isDualViewEnabled) {
                    if (!VolumePanelStateExt.isAODVolumePanel(volumePanelState2)) {
                        VolumePanelMotion volumePanelMotion = this.volumePanelMotion;
                        if (volumePanelMotion == null) {
                            volumePanelMotion = null;
                        }
                        SpringAnimation springAnimation = this.touchDownAnimation;
                        if (springAnimation == null) {
                            springAnimation = null;
                        }
                        SpringAnimation springAnimation2 = this.touchUpAnimation;
                        SpringAnimation springAnimation3 = springAnimation2 != null ? springAnimation2 : null;
                        volumePanelMotion.getClass();
                        if (springAnimation3 != null && springAnimation3.mRunning && springAnimation3.canSkipToEnd()) {
                            springAnimation3.skipToEnd();
                        }
                        springAnimation.animateToFinalPosition(1.07f);
                        break;
                    }
                } else {
                    updateVolumeTitleView(volumePanelState2, true);
                    break;
                }
                break;
            case 3:
                this.isSeekBarTouching = false;
                if (!this.isDualViewEnabled) {
                    if (!VolumePanelStateExt.isAODVolumePanel(volumePanelState2)) {
                        VolumePanelMotion volumePanelMotion2 = this.volumePanelMotion;
                        if (volumePanelMotion2 == null) {
                            volumePanelMotion2 = null;
                        }
                        SpringAnimation springAnimation4 = this.touchUpAnimation;
                        if (springAnimation4 == null) {
                            springAnimation4 = null;
                        }
                        SpringAnimation springAnimation5 = this.touchDownAnimation;
                        SpringAnimation springAnimation6 = springAnimation5 != null ? springAnimation5 : null;
                        volumePanelMotion2.getClass();
                        VolumePanelMotion.startSeekBarTouchUpAnimation(springAnimation4, springAnimation6);
                        break;
                    }
                } else {
                    updateVolumeTitleView(volumePanelState2, false);
                    break;
                }
                break;
            case 4:
                Dialog dialog = this.dialog;
                if ((dialog != null ? dialog : null).isShowing() && !this.isDualViewEnabled && !VolumePanelStateExt.isActiveStream(volumePanelState2, this.activeStream)) {
                    addVolumeRows(volumePanelState2);
                    initViewVisibility(volumePanelState2);
                    break;
                }
                break;
            case 5:
                ((WarningDialogController) this.warningDialogController$delegate.getValue()).showVolumeSafetyWarningDialog();
                break;
            case 6:
                ((WarningDialogController) this.warningDialogController$delegate.getValue()).showVolumeLimiterDialog();
                break;
            case 7:
                ((WarningDialogController) this.warningDialogController$delegate.getValue()).showVolumeCSD100WarningDialog();
                break;
            case 8:
                if (VolumePanelStateExt.isAODVolumePanel(volumePanelState2) && (findRow = VolumePanelStateExt.INSTANCE.findRow(volumePanelState2, volumePanelState2.getActiveStream())) != null) {
                    this.currentVolume = findRow.getRealLevel();
                    break;
                }
                break;
            case 9:
                VolumePanelRow findRow2 = VolumePanelStateExt.INSTANCE.findRow(volumePanelState2, volumePanelState2.getActiveStream());
                boolean isSliderEnabled = findRow2 != null ? findRow2.isSliderEnabled() : false;
                if (!this.isDualViewEnabled && !volumePanelState2.isExpanded() && isSliderEnabled) {
                    z = true;
                }
                if (z) {
                    if (!volumePanelState2.isKeyDown()) {
                        if (this.isKeyDownAnimating) {
                            HandlerWrapper handlerWrapper = this.handlerWrapper;
                            if (handlerWrapper == null) {
                                handlerWrapper = null;
                            }
                            handlerWrapper.remove(this.keyUpRunnable);
                            HandlerWrapper handlerWrapper2 = this.handlerWrapper;
                            (handlerWrapper2 != null ? handlerWrapper2 : null).postDelayed(100L, this.keyUpRunnable);
                            break;
                        }
                    } else {
                        HandlerWrapper handlerWrapper3 = this.handlerWrapper;
                        if (handlerWrapper3 == null) {
                            handlerWrapper3 = null;
                        }
                        handlerWrapper3.remove(this.keyUpRunnable);
                        if (!this.isKeyDownAnimating) {
                            if (!volumePanelState2.isVibrating()) {
                                VibratorWrapper vibratorWrapper = this.vibratorWrapper;
                                if (vibratorWrapper == null) {
                                    vibratorWrapper = null;
                                }
                                vibratorWrapper.startKeyHaptic();
                            }
                            VolumePanelMotion volumePanelMotion3 = this.volumePanelMotion;
                            if (volumePanelMotion3 == null) {
                                volumePanelMotion3 = null;
                            }
                            SpringAnimation springAnimation7 = this.keyDownAnimation;
                            if (springAnimation7 == null) {
                                springAnimation7 = null;
                            }
                            SpringAnimation springAnimation8 = this.keyUpAnimation;
                            SpringAnimation springAnimation9 = springAnimation8 != null ? springAnimation8 : null;
                            volumePanelMotion3.getClass();
                            VolumePanelMotion.startSeekBarKeyDownAnimation(springAnimation7, springAnimation9);
                        }
                        this.isKeyDownAnimating = true;
                        break;
                    }
                }
                break;
        }
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.volumePanelView = (ViewGroup) findViewById(R.id.volume_panel_view);
        this.volumeAODPanelView = (ViewGroup) findViewById(R.id.volume_aod_panel_view);
        this.volumePanelDualView = (ViewGroup) findViewById(R.id.volume_panel_dual_view);
    }

    public final void startDismissAnimation() {
        final Runnable runnable = new Runnable() { // from class: com.android.systemui.volume.view.standard.VolumePanelView$startDismissAnimation$dismissRunnable$1
            @Override // java.lang.Runnable
            public final void run() {
                Dialog dialog = VolumePanelView.this.dialog;
                if (dialog == null) {
                    dialog = null;
                }
                dialog.dismiss();
            }
        };
        if (VolumePanelStateExt.isAODVolumePanel(getPanelState())) {
            final VolumePanelMotion volumePanelMotion = this.volumePanelMotion;
            if (volumePanelMotion == null) {
                volumePanelMotion = null;
            }
            Dialog dialog = this.dialog;
            Window window = (dialog != null ? dialog : null).getWindow();
            Intrinsics.checkNotNull(window);
            View decorView = window.getDecorView();
            volumePanelMotion.getClass();
            decorView.animate().alpha(0.0f).setDuration(100L).setInterpolator(VolumePanelMotion.HIDE_INTERPOLATOR).setListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.volume.view.VolumePanelMotion$startAODHideAnimation$1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    runnable.run();
                    VolumePanelMotion.this.storeInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_ANIMATION_FINISHED).build(), true);
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationStart(Animator animator) {
                    VolumePanelMotion.this.storeInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_ANIMATION_START).build(), true);
                }
            }).start();
            return;
        }
        if (!this.isDualViewEnabled) {
            final VolumePanelMotion volumePanelMotion2 = this.volumePanelMotion;
            if (volumePanelMotion2 == null) {
                volumePanelMotion2 = null;
            }
            Dialog dialog2 = this.dialog;
            if (dialog2 == null) {
                dialog2 = null;
            }
            Window window2 = dialog2.getWindow();
            Intrinsics.checkNotNull(window2);
            View decorView2 = window2.getDecorView();
            SpringAnimation springAnimation = volumePanelMotion2.singleShowSpringAnimation;
            if (springAnimation != null) {
                SpringAnimation springAnimation2 = springAnimation.mRunning ? springAnimation : null;
                if (springAnimation2 != null) {
                    springAnimation2.cancel();
                }
            }
            decorView2.animate().translationX(BasicRune.VOLUME_LEFT_DISPLAY_VOLUME_DIALOG ? -decorView2.getWidth() : decorView2.getWidth()).setDuration(350L).setInterpolator(VolumePanelMotion.HIDE_INTERPOLATOR).setListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.volume.view.VolumePanelMotion$startVolumePanelDismissAnimation$2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    runnable.run();
                    VolumePanelMotion.this.storeInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_ANIMATION_FINISHED).build(), true);
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationStart(Animator animator) {
                    VolumePanelMotion.this.storeInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_ANIMATION_START).build(), true);
                }
            }).start();
            return;
        }
        final VolumePanelMotion volumePanelMotion3 = this.volumePanelMotion;
        if (volumePanelMotion3 == null) {
            volumePanelMotion3 = null;
        }
        Dialog dialog3 = this.dialog;
        Window window3 = (dialog3 != null ? dialog3 : null).getWindow();
        Intrinsics.checkNotNull(window3);
        final View decorView3 = window3.getDecorView();
        volumePanelMotion3.getClass();
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(decorView3, "alpha", decorView3.getAlpha(), 0.0f);
        ofFloat.setDuration(200L);
        SecLockIconView$$ExternalSyntheticOutline0.m82m(0.33f, 0.0f, 0.67f, 1.0f, ofFloat);
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(decorView3, "scaleX", decorView3.getScaleX(), 0.9f);
        ofFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.volume.view.VolumePanelMotion$startVolumeDualViewHideAnimation$scaleAnimator$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                decorView3.setScaleY(((Float) valueAnimator.getAnimatedValue()).floatValue());
            }
        });
        ofFloat2.setDuration(200L);
        ofFloat2.setInterpolator(new PathInterpolator(0.33f, 0.0f, 0.67f, 1.0f));
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ofFloat);
        animatorSet.playTogether(ofFloat2);
        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.volume.view.VolumePanelMotion$startVolumeDualViewHideAnimation$1$1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                runnable.run();
                VolumePanelMotion.this.storeInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_ANIMATION_FINISHED).build(), true);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
                VolumePanelMotion.this.storeInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_ANIMATION_START).build(), true);
            }
        });
        animatorSet.start();
    }

    public final void updateVolumeTitle(VolumePanelState volumePanelState) {
        VolumePanelRow findRow = VolumePanelStateExt.INSTANCE.findRow(volumePanelState, volumePanelState.getStream());
        if (findRow != null) {
            TextView textView = this.dualViewTitle;
            if (textView == null) {
                textView = null;
            }
            textView.setText(VolumePanelRowExt.getStreamLabel(findRow, getContext()));
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v1, types: [android.widget.ImageView] */
    /* JADX WARN: Type inference failed for: r2v2, types: [android.widget.ImageView] */
    public final void updateVolumeTitleView(VolumePanelState volumePanelState, boolean z) {
        if (volumePanelState.isLockscreen()) {
            if (z) {
                updateVolumeTitle(volumePanelState);
                return;
            } else {
                TextView textView = this.dualViewTitle;
                (textView != null ? textView : null).setText(getContext().getString(R.string.volume_panel_view_title));
                return;
            }
        }
        if (!z) {
            ViewVisibilityUtil viewVisibilityUtil = ViewVisibilityUtil.INSTANCE;
            TextView textView2 = this.dualViewTitle;
            if (textView2 == null) {
                textView2 = null;
            }
            viewVisibilityUtil.getClass();
            ViewVisibilityUtil.setGone(textView2);
            ?? r2 = this.expandButton;
            (r2 != 0 ? r2 : null).setVisibility(0);
            return;
        }
        updateVolumeTitle(volumePanelState);
        ViewVisibilityUtil viewVisibilityUtil2 = ViewVisibilityUtil.INSTANCE;
        TextView textView3 = this.dualViewTitle;
        if (textView3 == null) {
            textView3 = null;
        }
        viewVisibilityUtil2.getClass();
        textView3.setVisibility(0);
        ?? r22 = this.expandButton;
        ViewVisibilityUtil.setGone(r22 != 0 ? r22 : null);
    }
}
