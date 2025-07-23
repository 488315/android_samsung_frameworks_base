package com.android.systemui.volume.view.subscreen.full;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.Window;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.view.ViewGroupKt$children$1;
import androidx.dynamicanimation.animation.SpringAnimation;
import com.android.keyguard.KeyguardSecPatternViewController$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.R;
import com.android.systemui.volume.VolumeDependency;
import com.android.systemui.volume.VolumeDependencyBase;
import com.android.systemui.volume.store.StoreInteractor;
import com.android.systemui.volume.store.VolumePanelStore;
import com.android.systemui.volume.util.BlurEffect;
import com.android.systemui.volume.util.HandlerWrapper;
import com.android.systemui.volume.util.IDisplayManagerWrapper;
import com.android.systemui.volume.util.PluginAODManagerWrapper;
import com.android.systemui.volume.util.PowerManagerWrapper;
import com.android.systemui.volume.util.VibratorWrapper;
import com.android.systemui.volume.util.ViewUtil;
import com.android.systemui.volume.util.ViewVisibilityUtil;
import com.android.systemui.volume.view.VolumePanelViewExt;
import com.android.systemui.volume.view.VolumePanelViewExt$$ExternalSyntheticLambda0;
import com.android.systemui.volume.view.context.ViewContext;
import com.android.systemui.volume.view.expand.VolumePanelExpandView$adjustTouchEventForOutsideTouch$1$$ExternalSyntheticOutline0;
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
import kotlin.collections.ReversedListReadOnly$listIterator$1;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.math.MathKt__MathJVMKt;
import kotlin.sequences.TransformingSequence;
import kotlin.sequences.TransformingSequence$iterator$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SubFullLayoutVolumePanelView extends FrameLayout implements VolumeObserver<VolumePanelState>, ViewContext {
    public static final /* synthetic */ int $r8$clinit = 0;
    public int activeStream;
    public BlurEffect blurEffect;
    public int currentVolume;
    public SubFullLayoutVolumePanelWindow dialog;
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
    public final SubFullLayoutVolumePanelView$keyUpRunnable$1 keyUpRunnable;
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
    public VolumeDependency volDeps;
    public ViewGroup volumeAODPanelView;
    public ViewGroup volumePanelDualView;
    public SubFullLayoutVolumePanelMotion volumePanelMotion;
    public ViewGroup volumePanelView;
    public final Lazy warningDialogController$delegate;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelView$keyUpRunnable$1] */
    public SubFullLayoutVolumePanelView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.storeInteractor = new StoreInteractor(this, null);
        this.keyUpRunnable = new Runnable() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelView$keyUpRunnable$1
            @Override // java.lang.Runnable
            public final void run() {
                SubFullLayoutVolumePanelView subFullLayoutVolumePanelView = SubFullLayoutVolumePanelView.this;
                subFullLayoutVolumePanelView.isKeyDownAnimating = false;
                SubFullLayoutVolumePanelMotion subFullLayoutVolumePanelMotion = subFullLayoutVolumePanelView.volumePanelMotion;
                SpringAnimation springAnimation = null;
                if (subFullLayoutVolumePanelMotion == null) {
                    subFullLayoutVolumePanelMotion = null;
                }
                SpringAnimation springAnimation2 = subFullLayoutVolumePanelView.keyUpAnimation;
                if (springAnimation2 == null) {
                    springAnimation2 = null;
                }
                SpringAnimation springAnimation3 = subFullLayoutVolumePanelView.keyDownAnimation;
                if (springAnimation3 == null) {
                    springAnimation3 = null;
                }
                subFullLayoutVolumePanelMotion.getClass();
                if (springAnimation3 != null) {
                    if (springAnimation3.mRunning && springAnimation3.canSkipToEnd()) {
                        springAnimation = springAnimation3;
                    }
                    if (springAnimation != null) {
                        springAnimation.skipToEnd();
                    }
                }
                springAnimation2.animateToFinalPosition(1.0f);
            }
        };
        this.warningDialogController$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelView$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i = SubFullLayoutVolumePanelView.$r8$clinit;
                return new WarningDialogController(SubFullLayoutVolumePanelView.this);
            }
        });
    }

    public final void addVolumeRows$1(VolumePanelState volumePanelState) {
        ViewGroup viewGroup = this.rowContainer;
        if (viewGroup == null) {
            viewGroup = null;
        }
        viewGroup.removeAllViews();
        List<VolumePanelRow> volumeRowList = volumePanelState.getVolumeRowList();
        ArrayList arrayList = new ArrayList();
        for (Object obj : volumeRowList) {
            VolumePanelRow volumePanelRow = (VolumePanelRow) obj;
            if (VolumePanelStateExt.isActiveStream(volumePanelState, volumePanelRow.getStreamType()) || volumePanelRow.isVisible()) {
                arrayList.add(obj);
            }
        }
        ReversedListReadOnly reversedListReadOnly = new ReversedListReadOnly(arrayList);
        Log.d("SubFullLayoutVolumePanelView", "addRows: rows=" + VolumePanelRowExt.listToString(reversedListReadOnly));
        Iterator it = reversedListReadOnly.iterator();
        while (true) {
            ReversedListReadOnly$listIterator$1 reversedListReadOnly$listIterator$1 = (ReversedListReadOnly$listIterator$1) it;
            if (!reversedListReadOnly$listIterator$1.delegateIterator.hasPrevious()) {
                return;
            }
            VolumePanelRow volumePanelRow2 = (VolumePanelRow) reversedListReadOnly$listIterator$1.delegateIterator.previous();
            SubFullLayoutVolumeRowView subFullLayoutVolumeRowView = (SubFullLayoutVolumeRowView) LayoutInflater.from(getContext()).inflate(R.layout.sub_full_volume_row_view, (ViewGroup) null);
            VolumePanelStore volumePanelStore = this.store;
            VolumePanelStore volumePanelStore2 = volumePanelStore != null ? volumePanelStore : null;
            HandlerWrapper handlerWrapper = this.handlerWrapper;
            HandlerWrapper handlerWrapper2 = handlerWrapper == null ? null : handlerWrapper;
            SubFullLayoutVolumePanelMotion subFullLayoutVolumePanelMotion = this.volumePanelMotion;
            VolumePanelState volumePanelState2 = volumePanelState;
            subFullLayoutVolumeRowView.initialize(volumePanelStore2, handlerWrapper2, volumePanelRow2, volumePanelState2, subFullLayoutVolumePanelMotion == null ? null : subFullLayoutVolumePanelMotion);
            ViewGroup viewGroup2 = this.rowContainer;
            if (viewGroup2 == null) {
                viewGroup2 = null;
            }
            viewGroup2.addView(subFullLayoutVolumeRowView);
            if (VolumePanelStateExt.isActiveStream(volumePanelState2, volumePanelRow2.getStreamType())) {
                this.activeStream = volumePanelRow2.getStreamType();
            }
            if (!this.isDualViewEnabled) {
                ViewVisibilityUtil viewVisibilityUtil = ViewVisibilityUtil.INSTANCE;
                View requireViewById = subFullLayoutVolumeRowView.requireViewById(R.id.volume_seekbar_outline_stroke);
                viewVisibilityUtil.getClass();
                requireViewById.setVisibility(0);
            }
            volumePanelState = volumePanelState2;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        this.storeInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_TOUCH_PANEL).isFromOutside(true).build(), false);
        int action = motionEvent.getAction();
        if (action != 0) {
            if (action != 1) {
                if (action == 2) {
                    float x = motionEvent.getX();
                    float f = x - this.downX;
                    VolumePanelState volumePanelState = this.panelState;
                    if (volumePanelState == null) {
                        volumePanelState = null;
                    }
                    if (VolumePanelStateExt.isAODVolumePanel(volumePanelState)) {
                        float y = this.downY - motionEvent.getY();
                        if (this.isDragging) {
                            ViewUtil viewUtil = ViewUtil.INSTANCE;
                            ViewGroup viewGroup = this.rowContainer;
                            ViewGroup viewGroup2 = viewGroup != null ? viewGroup : null;
                            float rawX = motionEvent.getRawX();
                            float rawY = motionEvent.getRawY();
                            viewUtil.getClass();
                            if (!ViewUtil.isTouched(viewGroup2, rawX, rawY)) {
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
                        VolumePanelExpandView$adjustTouchEventForOutsideTouch$1$$ExternalSyntheticOutline0.m(new VolumePanelAction.Builder((!BasicRune.VOLUME_LEFT_DISPLAY_VOLUME_DIALOG ? (x > this.downX ? 1 : (x == this.downX ? 0 : -1)) > 0 : (x > this.downX ? 1 : (x == this.downX ? 0 : -1)) < 0) != false ? VolumePanelAction.ActionType.ACTION_SWIPE_COLLAPSED : VolumePanelAction.ActionType.ACTION_SWIPE_PANEL), true, this.storeInteractor, false);
                    }
                } else if (action != 3) {
                    if (action == 4) {
                        VolumePanelState volumePanelState2 = this.panelState;
                        if (VolumePanelStateExt.isAODVolumePanel(volumePanelState2 != null ? volumePanelState2 : null)) {
                            this.isTouchDown = false;
                            this.isDragging = false;
                        } else {
                            VolumePanelExpandView$adjustTouchEventForOutsideTouch$1$$ExternalSyntheticOutline0.m(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_TOUCH_OUTSIDE), true, this.storeInteractor, false);
                            this.touchDownExpandButton = false;
                        }
                        this.startProgress = false;
                        return true;
                    }
                }
            }
            int i = VolumePanelViewExt.$r8$clinit;
            TransformingSequence$iterator$1 transformingSequence$iterator$1 = new TransformingSequence$iterator$1(new TransformingSequence(new ViewGroupKt$children$1((ViewGroup) requireViewById(R.id.volume_panel_row_container)), new VolumePanelViewExt$$ExternalSyntheticLambda0(0)));
            while (true) {
                if (!transformingSequence$iterator$1.iterator.hasNext()) {
                    boolean z = this.startProgress;
                    if (!z && this.touchDownExpandButton) {
                        ViewUtil viewUtil2 = ViewUtil.INSTANCE;
                        ImageView imageView = this.expandButton;
                        ImageView imageView2 = imageView != null ? imageView : null;
                        float rawX2 = motionEvent.getRawX();
                        float rawY2 = motionEvent.getRawY();
                        viewUtil2.getClass();
                        if (ViewUtil.isTouched(imageView2, rawX2, rawY2)) {
                            VolumePanelExpandView$adjustTouchEventForOutsideTouch$1$$ExternalSyntheticOutline0.m(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_EXPAND_BUTTON_CLICKED), true, this.storeInteractor, false);
                        }
                    } else if (!this.isSwipe && !this.isSeekBarTouching && !z && !this.isDualViewEnabled) {
                        VolumePanelState volumePanelState3 = this.panelState;
                        if (!VolumePanelStateExt.isAODVolumePanel(volumePanelState3 != null ? volumePanelState3 : null)) {
                            VolumePanelExpandView$adjustTouchEventForOutsideTouch$1$$ExternalSyntheticOutline0.m(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_TOUCH_OUTSIDE), true, this.storeInteractor, false);
                            return true;
                        }
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
                } else if (((SubFullLayoutVolumeRowView) transformingSequence$iterator$1.next()).isIconClicked(motionEvent.getRawX(), motionEvent.getRawY())) {
                    break;
                }
            }
        } else {
            VolumePanelState volumePanelState4 = this.panelState;
            if (volumePanelState4 == null) {
                volumePanelState4 = null;
            }
            if (VolumePanelStateExt.isAODVolumePanel(volumePanelState4)) {
                if (!this.isFirstTouch) {
                    ViewUtil viewUtil3 = ViewUtil.INSTANCE;
                    ViewGroup viewGroup3 = this.rowContainer;
                    if (viewGroup3 == null) {
                        viewGroup3 = null;
                    }
                    float rawX3 = motionEvent.getRawX();
                    float rawY3 = motionEvent.getRawY();
                    viewUtil3.getClass();
                    if (!ViewUtil.isTouched(viewGroup3, rawX3, rawY3)) {
                        VolumePanelExpandView$adjustTouchEventForOutsideTouch$1$$ExternalSyntheticOutline0.m(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_TOUCH_OUTSIDE), true, this.storeInteractor, false);
                        this.touchDownExpandButton = false;
                        this.startProgress = false;
                        return true;
                    }
                }
                this.isDragging = true;
            }
            if (!this.isLockscreen) {
                ViewUtil viewUtil4 = ViewUtil.INSTANCE;
                ImageView imageView3 = this.expandButton;
                ImageView imageView4 = imageView3 != null ? imageView3 : null;
                float rawX4 = motionEvent.getRawX();
                float rawY4 = motionEvent.getRawY();
                viewUtil4.getClass();
                if (ViewUtil.isTouched(imageView4, rawX4, rawY4)) {
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
    public final VolumePanelState getPanelState$1() {
        VolumePanelState volumePanelState = this.panelState;
        if (volumePanelState != null) {
            return volumePanelState;
        }
        return null;
    }

    @Override // com.android.systemui.volume.view.context.ViewContext
    public final VolumePanelStore getStore$1() {
        VolumePanelStore volumePanelStore = this.store;
        if (volumePanelStore != null) {
            return volumePanelStore;
        }
        return null;
    }

    @Override // com.android.systemui.volume.view.context.ViewContext
    public final VolumeDependencyBase getVolDeps() {
        VolumeDependency volumeDependency = this.volDeps;
        if (volumeDependency != null) {
            return volumeDependency;
        }
        return null;
    }

    public final void initViewVisibility$1(VolumePanelState volumePanelState) {
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
        } else {
            ViewVisibilityUtil viewVisibilityUtil3 = ViewVisibilityUtil.INSTANCE;
            ViewGroup viewGroup7 = this.volumePanelView;
            if (viewGroup7 == null) {
                viewGroup7 = null;
            }
            viewVisibilityUtil3.getClass();
            viewGroup7.setVisibility(0);
            ViewGroup viewGroup8 = this.volumePanelDualView;
            if (viewGroup8 == null) {
                viewGroup8 = null;
            }
            ViewVisibilityUtil.setGone(viewGroup8);
            ViewGroup viewGroup9 = this.volumeAODPanelView;
            if (viewGroup9 == null) {
                viewGroup9 = null;
            }
            ViewVisibilityUtil.setGone(viewGroup9);
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
        r1 = null;
        SpringAnimation springAnimation = null;
        r1 = null;
        SpringAnimation springAnimation2 = null;
        switch (WhenMappings.$EnumSwitchMapping$0[volumePanelState2.getStateType().ordinal()]) {
            case 1:
                this.startProgress = true;
                break;
            case 2:
                this.isSeekBarTouching = true;
                if (!this.isDualViewEnabled) {
                    if (!VolumePanelStateExt.isAODVolumePanel(volumePanelState2)) {
                        SubFullLayoutVolumePanelMotion subFullLayoutVolumePanelMotion = this.volumePanelMotion;
                        if (subFullLayoutVolumePanelMotion == null) {
                            subFullLayoutVolumePanelMotion = null;
                        }
                        SpringAnimation springAnimation3 = this.touchDownAnimation;
                        if (springAnimation3 == null) {
                            springAnimation3 = null;
                        }
                        SpringAnimation springAnimation4 = this.touchUpAnimation;
                        SpringAnimation springAnimation5 = springAnimation4 != null ? springAnimation4 : null;
                        subFullLayoutVolumePanelMotion.getClass();
                        SubFullLayoutVolumePanelMotion.startSeekBarTouchDownAnimation(springAnimation3, springAnimation5, false);
                        break;
                    }
                } else {
                    updateVolumeTitleView$1(volumePanelState2, true);
                    break;
                }
                break;
            case 3:
                this.isSeekBarTouching = false;
                if (!this.isDualViewEnabled) {
                    if (!VolumePanelStateExt.isAODVolumePanel(volumePanelState2)) {
                        SubFullLayoutVolumePanelMotion subFullLayoutVolumePanelMotion2 = this.volumePanelMotion;
                        if (subFullLayoutVolumePanelMotion2 == null) {
                            subFullLayoutVolumePanelMotion2 = null;
                        }
                        SpringAnimation springAnimation6 = this.touchUpAnimation;
                        if (springAnimation6 == null) {
                            springAnimation6 = null;
                        }
                        SpringAnimation springAnimation7 = this.touchDownAnimation;
                        if (springAnimation7 == null) {
                            springAnimation7 = null;
                        }
                        subFullLayoutVolumePanelMotion2.getClass();
                        if (springAnimation7 != null) {
                            if (springAnimation7.mRunning && springAnimation7.canSkipToEnd()) {
                                springAnimation2 = springAnimation7;
                            }
                            if (springAnimation2 != null) {
                                springAnimation2.skipToEnd();
                            }
                        }
                        springAnimation6.animateToFinalPosition(1.0f);
                        break;
                    }
                } else {
                    updateVolumeTitleView$1(volumePanelState2, false);
                    break;
                }
                break;
            case 4:
                SubFullLayoutVolumePanelWindow subFullLayoutVolumePanelWindow = this.dialog;
                if ((subFullLayoutVolumePanelWindow != null ? subFullLayoutVolumePanelWindow : null).isShowing() && !this.isDualViewEnabled && !VolumePanelStateExt.isActiveStream(volumePanelState2, this.activeStream)) {
                    addVolumeRows$1(volumePanelState2);
                    initViewVisibility$1(volumePanelState2);
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
                    if (!volumePanelState2.isKeyDown()) {
                        if (this.isKeyDownAnimating) {
                            HandlerWrapper handlerWrapper = this.handlerWrapper;
                            if (handlerWrapper == null) {
                                handlerWrapper = null;
                            }
                            handlerWrapper.remove(this.keyUpRunnable);
                            HandlerWrapper handlerWrapper2 = this.handlerWrapper;
                            (handlerWrapper2 != null ? handlerWrapper2 : null).postDelayed(this.keyUpRunnable, 100L);
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
                            SubFullLayoutVolumePanelMotion subFullLayoutVolumePanelMotion3 = this.volumePanelMotion;
                            if (subFullLayoutVolumePanelMotion3 == null) {
                                subFullLayoutVolumePanelMotion3 = null;
                            }
                            SpringAnimation springAnimation8 = this.keyDownAnimation;
                            if (springAnimation8 == null) {
                                springAnimation8 = null;
                            }
                            SpringAnimation springAnimation9 = this.keyUpAnimation;
                            if (springAnimation9 == null) {
                                springAnimation9 = null;
                            }
                            subFullLayoutVolumePanelMotion3.getClass();
                            if (springAnimation9 != null) {
                                if (springAnimation9.mRunning && springAnimation9.canSkipToEnd()) {
                                    springAnimation = springAnimation9;
                                }
                                if (springAnimation != null) {
                                    springAnimation.skipToEnd();
                                }
                            }
                            springAnimation8.animateToFinalPosition(0.95f);
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
        this.volumePanelView = (ViewGroup) requireViewById(R.id.volume_panel_view);
        this.volumeAODPanelView = (ViewGroup) requireViewById(R.id.volume_aod_panel_view);
        this.volumePanelDualView = (ViewGroup) requireViewById(R.id.volume_panel_dual_view);
    }

    public final void startDismissAnimation() {
        final Runnable runnable = new Runnable() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelView$startDismissAnimation$dismissRunnable$1
            @Override // java.lang.Runnable
            public final void run() {
                SubFullLayoutVolumePanelWindow subFullLayoutVolumePanelWindow = SubFullLayoutVolumePanelView.this.dialog;
                if (subFullLayoutVolumePanelWindow == null) {
                    subFullLayoutVolumePanelWindow = null;
                }
                subFullLayoutVolumePanelWindow.dismiss();
            }
        };
        VolumePanelState volumePanelState = this.panelState;
        if (volumePanelState == null) {
            volumePanelState = null;
        }
        if (VolumePanelStateExt.isAODVolumePanel(volumePanelState)) {
            final SubFullLayoutVolumePanelMotion subFullLayoutVolumePanelMotion = this.volumePanelMotion;
            if (subFullLayoutVolumePanelMotion == null) {
                subFullLayoutVolumePanelMotion = null;
            }
            SubFullLayoutVolumePanelWindow subFullLayoutVolumePanelWindow = this.dialog;
            Window window = (subFullLayoutVolumePanelWindow != null ? subFullLayoutVolumePanelWindow : null).getWindow();
            window.getClass();
            View decorView = window.getDecorView();
            subFullLayoutVolumePanelMotion.getClass();
            decorView.animate().alpha(0.0f).setDuration(100L).setInterpolator(SubFullLayoutVolumePanelMotion.HIDE_INTERPOLATOR).setListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelMotion$startAODHideAnimation$1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    runnable.run();
                    SubFullLayoutVolumePanelMotion.this.storeInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_ANIMATION_FINISHED).build(), true);
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationStart(Animator animator) {
                    SubFullLayoutVolumePanelMotion.this.storeInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_ANIMATION_START).build(), true);
                }
            }).start();
            return;
        }
        if (!this.isDualViewEnabled) {
            final SubFullLayoutVolumePanelMotion subFullLayoutVolumePanelMotion2 = this.volumePanelMotion;
            if (subFullLayoutVolumePanelMotion2 == null) {
                subFullLayoutVolumePanelMotion2 = null;
            }
            SubFullLayoutVolumePanelWindow subFullLayoutVolumePanelWindow2 = this.dialog;
            if (subFullLayoutVolumePanelWindow2 == null) {
                subFullLayoutVolumePanelWindow2 = null;
            }
            Window window2 = subFullLayoutVolumePanelWindow2.getWindow();
            window2.getClass();
            View decorView2 = window2.getDecorView();
            SpringAnimation springAnimation = subFullLayoutVolumePanelMotion2.singleShowSpringAnimation;
            if (springAnimation != null) {
                SpringAnimation springAnimation2 = springAnimation.mRunning ? springAnimation : null;
                if (springAnimation2 != null) {
                    springAnimation2.cancel();
                }
            }
            ViewPropertyAnimator animate = decorView2.animate();
            boolean z = BasicRune.VOLUME_LEFT_DISPLAY_VOLUME_DIALOG;
            int width = decorView2.getWidth();
            if (z) {
                width = -width;
            }
            animate.translationX(width).setDuration(350L).setInterpolator(SubFullLayoutVolumePanelMotion.HIDE_INTERPOLATOR).setListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelMotion$startVolumePanelDismissAnimation$2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    runnable.run();
                    SubFullLayoutVolumePanelMotion.this.storeInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_ANIMATION_FINISHED).build(), true);
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationStart(Animator animator) {
                    SubFullLayoutVolumePanelMotion.this.storeInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_ANIMATION_START).build(), true);
                }
            }).start();
            return;
        }
        final SubFullLayoutVolumePanelMotion subFullLayoutVolumePanelMotion3 = this.volumePanelMotion;
        if (subFullLayoutVolumePanelMotion3 == null) {
            subFullLayoutVolumePanelMotion3 = null;
        }
        SubFullLayoutVolumePanelWindow subFullLayoutVolumePanelWindow3 = this.dialog;
        if (subFullLayoutVolumePanelWindow3 == null) {
            subFullLayoutVolumePanelWindow3 = null;
        }
        Window window3 = subFullLayoutVolumePanelWindow3.getWindow();
        window3.getClass();
        final View decorView3 = window3.getDecorView();
        AnimatorSet animatorSet = subFullLayoutVolumePanelMotion3.dualShowAnimation;
        if (animatorSet != null) {
            animatorSet.cancel();
        }
        subFullLayoutVolumePanelMotion3.dualShowAnimation = null;
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(decorView3, "alpha", decorView3.getAlpha(), 0.0f);
        ofFloat.setDuration(200L);
        KeyguardSecPatternViewController$$ExternalSyntheticOutline0.m(0.33f, 0.0f, 0.67f, 1.0f, ofFloat);
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(decorView3, "scaleX", decorView3.getScaleX(), 0.9f);
        ofFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelMotion$startVolumeDualViewHideAnimation$scaleAnimator$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                decorView3.setScaleY(((Float) valueAnimator.getAnimatedValue()).floatValue());
            }
        });
        ofFloat2.setDuration(200L);
        ofFloat2.setInterpolator(new PathInterpolator(0.33f, 0.0f, 0.67f, 1.0f));
        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.playTogether(ofFloat);
        animatorSet2.playTogether(ofFloat2);
        animatorSet2.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelMotion$startVolumeDualViewHideAnimation$1$1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                runnable.run();
                SubFullLayoutVolumePanelMotion.this.storeInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_ANIMATION_FINISHED).build(), true);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
                SubFullLayoutVolumePanelMotion.this.storeInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_ANIMATION_START).build(), true);
            }
        });
        animatorSet2.start();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v1, types: [android.widget.ImageView] */
    /* JADX WARN: Type inference failed for: r3v2, types: [android.widget.ImageView] */
    public final void updateVolumeTitleView$1(VolumePanelState volumePanelState, boolean z) {
        if (volumePanelState.isLockscreen()) {
            if (!z) {
                TextView textView = this.dualViewTitle;
                (textView != null ? textView : null).setText(getContext().getString(R.string.volume_panel_view_title));
                return;
            }
            VolumePanelRow findRow = VolumePanelStateExt.INSTANCE.findRow(volumePanelState, volumePanelState.getStream());
            if (findRow != null) {
                TextView textView2 = this.dualViewTitle;
                (textView2 != null ? textView2 : null).setText(VolumePanelRowExt.getStreamLabel(findRow, getContext()));
                return;
            }
            return;
        }
        if (!z) {
            ViewVisibilityUtil viewVisibilityUtil = ViewVisibilityUtil.INSTANCE;
            TextView textView3 = this.dualViewTitle;
            if (textView3 == null) {
                textView3 = null;
            }
            viewVisibilityUtil.getClass();
            ViewVisibilityUtil.setGone(textView3);
            ?? r3 = this.expandButton;
            (r3 != 0 ? r3 : null).setVisibility(0);
            return;
        }
        VolumePanelRow findRow2 = VolumePanelStateExt.INSTANCE.findRow(volumePanelState, volumePanelState.getStream());
        if (findRow2 != null) {
            TextView textView4 = this.dualViewTitle;
            if (textView4 == null) {
                textView4 = null;
            }
            textView4.setText(VolumePanelRowExt.getStreamLabel(findRow2, getContext()));
        }
        ViewVisibilityUtil viewVisibilityUtil2 = ViewVisibilityUtil.INSTANCE;
        TextView textView5 = this.dualViewTitle;
        if (textView5 == null) {
            textView5 = null;
        }
        viewVisibilityUtil2.getClass();
        textView5.setVisibility(0);
        ?? r32 = this.expandButton;
        ViewVisibilityUtil.setGone(r32 != 0 ? r32 : null);
    }
}
