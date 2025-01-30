package com.android.systemui.controls.p005ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.service.controls.Control;
import android.service.controls.actions.FloatAction;
import android.service.controls.templates.ControlTemplate;
import android.service.controls.templates.RangeTemplate;
import android.service.controls.templates.TemperatureControlTemplate;
import android.service.controls.templates.ToggleRangeTemplate;
import android.util.Log;
import android.util.MathUtils;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import com.android.app.animation.Interpolators;
import com.android.systemui.BasicRune;
import com.android.systemui.R;
import com.android.systemui.controls.ControlsMetricsLoggerImpl;
import com.android.systemui.controls.p005ui.ToggleRangeBehavior;
import com.android.systemui.controls.p005ui.view.ActionIconView;
import com.android.systemui.statusbar.VibratorHelper;
import java.util.Arrays;
import java.util.IllegalFormatException;
import java.util.Set;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ToggleRangeBehavior implements Behavior, CustomBehavior, CustomButtonBehavior {
    public static final Companion Companion = new Companion(null);
    public static boolean inProgress;
    public Drawable clipLayer;
    public int colorOffset;
    public Context context;
    public Control control;
    public ControlViewHolder cvh;
    public boolean isChecked;
    public boolean isToggleable;
    public ValueAnimator rangeAnimator;
    public RangeTemplate rangeTemplate;
    public String templateId;
    public ToggleRangeTemplate toggleRangeTemplate;
    public CharSequence currentStatusText = "";
    public String currentRangeValue = "";

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ToggleRangeGestureListener extends GestureDetector.SimpleOnGestureListener {
        public boolean isDragging;

        /* renamed from: v */
        public final View f252v;

        public ToggleRangeGestureListener(View view) {
            this.f252v = view;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public final boolean onDown(MotionEvent motionEvent) {
            return true;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public final void onLongPress(MotionEvent motionEvent) {
            if (this.isDragging) {
                return;
            }
            ((ControlActionCoordinatorImpl) ToggleRangeBehavior.this.getCvh().controlActionCoordinator).longPress(ToggleRangeBehavior.this.getCvh());
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public final boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            if (!this.isDragging) {
                this.f252v.getParent().requestDisallowInterceptTouchEvent(true);
                ToggleRangeBehavior.this.beginUpdateRange();
                this.isDragging = true;
            }
            int width = (int) (10000 * ((-f) / this.f252v.getWidth()));
            ToggleRangeBehavior toggleRangeBehavior = ToggleRangeBehavior.this;
            toggleRangeBehavior.updateRange(toggleRangeBehavior.getClipLayer().getLevel() + width, true, true);
            return true;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public final boolean onSingleTapUp(MotionEvent motionEvent) {
            ToggleRangeBehavior toggleRangeBehavior = ToggleRangeBehavior.this;
            if (!toggleRangeBehavior.isToggleable) {
                return false;
            }
            ControlActionCoordinator controlActionCoordinator = toggleRangeBehavior.getCvh().controlActionCoordinator;
            ControlViewHolder cvh = ToggleRangeBehavior.this.getCvh();
            ToggleRangeBehavior toggleRangeBehavior2 = ToggleRangeBehavior.this;
            String str = toggleRangeBehavior2.templateId;
            if (str == null) {
                str = null;
            }
            ((ControlActionCoordinatorImpl) controlActionCoordinator).toggle(cvh, str, toggleRangeBehavior2.isChecked);
            return true;
        }
    }

    public final void beginUpdateRange() {
        getCvh().userInteractionInProgress = true;
        if (BasicRune.CONTROLS_SAMSUNG_STYLE) {
            return;
        }
        ControlViewHolder cvh = getCvh();
        Context context = this.context;
        if (context == null) {
            context = null;
        }
        cvh.status.setTextSize(0, context.getResources().getDimensionPixelSize(R.dimen.control_status_expanded));
    }

    @Override // com.android.systemui.controls.p005ui.Behavior
    public final void bind(ControlWithState controlWithState, int i) {
        ActionIconView actionIconView;
        Control control = controlWithState.control;
        Intrinsics.checkNotNull(control);
        this.control = control;
        this.colorOffset = i;
        this.currentStatusText = control.getStatusText();
        getCvh().layout.setOnLongClickListener(null);
        this.clipLayer = ((LayerDrawable) getCvh().layout.getBackground()).findDrawableByLayerId(R.id.clip_layer);
        Control control2 = this.control;
        ControlTemplate controlTemplate = (control2 != null ? control2 : null).getControlTemplate();
        if (setupTemplate(controlTemplate)) {
            if (BasicRune.CONTROLS_CUSTOM_MAIN_ACTION_ICON && this.isToggleable && (actionIconView = getCvh().getCustomControlViewHolder().actionIcon) != null) {
                actionIconView.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.controls.ui.ToggleRangeBehavior$bind$1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        CustomControlActionCoordinator customControlActionCoordinator = ToggleRangeBehavior.this.getCvh().getCustomControlViewHolder().customControlActionCoordinator;
                        if (customControlActionCoordinator != null) {
                            ControlViewHolder cvh = ToggleRangeBehavior.this.getCvh();
                            ToggleRangeBehavior toggleRangeBehavior = ToggleRangeBehavior.this;
                            String str = toggleRangeBehavior.templateId;
                            if (str == null) {
                                str = null;
                            }
                            ((ControlActionCoordinatorImpl) customControlActionCoordinator).toggleMainAction(cvh, str, toggleRangeBehavior.isChecked);
                        }
                    }
                });
            }
            this.templateId = controlTemplate.getTemplateId();
            updateRange((int) MathUtils.constrainedMap(0.0f, 10000.0f, getRangeTemplate().getMinValue(), getRangeTemplate().getMaxValue(), getRangeTemplate().getCurrentValue()), this.isChecked, false);
            getCvh().m119x3918d5b8(i, this.isChecked, true);
            getCvh().layout.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.systemui.controls.ui.ToggleRangeBehavior$bind$2
                @Override // android.view.View.AccessibilityDelegate
                public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                    super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                    ToggleRangeBehavior toggleRangeBehavior = ToggleRangeBehavior.this;
                    ToggleRangeBehavior.Companion companion = ToggleRangeBehavior.Companion;
                    float levelToRangeValue = toggleRangeBehavior.levelToRangeValue(0);
                    ToggleRangeBehavior toggleRangeBehavior2 = ToggleRangeBehavior.this;
                    float levelToRangeValue2 = toggleRangeBehavior2.levelToRangeValue(toggleRangeBehavior2.getClipLayer().getLevel());
                    float levelToRangeValue3 = ToggleRangeBehavior.this.levelToRangeValue(10000);
                    double stepValue = ToggleRangeBehavior.this.getRangeTemplate().getStepValue();
                    int i2 = (stepValue == Math.floor(stepValue) ? 1 : 0) ^ 1;
                    if (ToggleRangeBehavior.this.isChecked) {
                        accessibilityNodeInfo.setRangeInfo(AccessibilityNodeInfo.RangeInfo.obtain(i2, levelToRangeValue, levelToRangeValue3, levelToRangeValue2));
                    }
                    accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SET_PROGRESS);
                }

                @Override // android.view.View.AccessibilityDelegate
                public final boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
                    return true;
                }

                /* JADX WARN: Multi-variable type inference failed */
                @Override // android.view.View.AccessibilityDelegate
                public final boolean performAccessibilityAction(View view, int i2, Bundle bundle) {
                    Object[] objArr;
                    if (i2 == 16) {
                        if (BasicRune.CONTROLS_CUSTOM_MAIN_ACTION_ICON) {
                            CustomControlActionCoordinator customControlActionCoordinator = ToggleRangeBehavior.this.getCvh().getCustomControlViewHolder().customControlActionCoordinator;
                            if (customControlActionCoordinator != null) {
                                ControlViewHolder cvh = ToggleRangeBehavior.this.getCvh();
                                ToggleRangeBehavior toggleRangeBehavior = ToggleRangeBehavior.this;
                                String str = toggleRangeBehavior.templateId;
                                if (str == null) {
                                    str = null;
                                }
                                Control control3 = toggleRangeBehavior.control;
                                ((ControlActionCoordinatorImpl) customControlActionCoordinator).customTouch(cvh, str, control3 != null ? control3 : null);
                            }
                        } else {
                            ToggleRangeBehavior toggleRangeBehavior2 = ToggleRangeBehavior.this;
                            if (toggleRangeBehavior2.isToggleable) {
                                ControlActionCoordinator controlActionCoordinator = toggleRangeBehavior2.getCvh().controlActionCoordinator;
                                ControlViewHolder cvh2 = ToggleRangeBehavior.this.getCvh();
                                ToggleRangeBehavior toggleRangeBehavior3 = ToggleRangeBehavior.this;
                                String str2 = toggleRangeBehavior3.templateId;
                                ((ControlActionCoordinatorImpl) controlActionCoordinator).toggle(cvh2, str2 != null ? str2 : null, toggleRangeBehavior3.isChecked);
                            }
                            objArr = false;
                        }
                        objArr = true;
                    } else if (i2 == 32) {
                        if (!BasicRune.CONTROLS_CUSTOM_MAIN_ACTION_ICON) {
                            ((ControlActionCoordinatorImpl) ToggleRangeBehavior.this.getCvh().controlActionCoordinator).longPress(ToggleRangeBehavior.this.getCvh());
                            objArr = true;
                        }
                        objArr = false;
                    } else {
                        if (i2 == AccessibilityNodeInfo.AccessibilityAction.ACTION_SET_PROGRESS.getId() && bundle != null && bundle.containsKey("android.view.accessibility.action.ARGUMENT_PROGRESS_VALUE")) {
                            float f = bundle.getFloat("android.view.accessibility.action.ARGUMENT_PROGRESS_VALUE");
                            ToggleRangeBehavior toggleRangeBehavior4 = ToggleRangeBehavior.this;
                            ToggleRangeBehavior.Companion companion = ToggleRangeBehavior.Companion;
                            int constrainedMap = (int) MathUtils.constrainedMap(0.0f, 10000.0f, toggleRangeBehavior4.getRangeTemplate().getMinValue(), toggleRangeBehavior4.getRangeTemplate().getMaxValue(), f);
                            ToggleRangeBehavior toggleRangeBehavior5 = ToggleRangeBehavior.this;
                            toggleRangeBehavior5.updateRange(constrainedMap, toggleRangeBehavior5.isChecked, true);
                            ToggleRangeBehavior.this.endUpdateRange();
                            objArr = true;
                        }
                        objArr = false;
                    }
                    return objArr == true || super.performAccessibilityAction(view, i2, bundle);
                }
            });
        }
    }

    @Override // com.android.systemui.controls.p005ui.CustomBehavior
    public final void dispose() {
        ActionIconView actionIconView;
        getCvh().layout.setOnTouchListener(null);
        if (BasicRune.CONTROLS_CUSTOM_MAIN_ACTION_ICON && (actionIconView = getCvh().getCustomControlViewHolder().actionIcon) != null) {
            actionIconView.setOnClickListener(null);
        }
        ValueAnimator valueAnimator = this.rangeAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
    }

    public final void endUpdateRange() {
        boolean z = BasicRune.CONTROLS_SAMSUNG_STYLE;
        if (!z) {
            ControlViewHolder cvh = getCvh();
            Context context = this.context;
            if (context == null) {
                context = null;
            }
            cvh.status.setTextSize(0, context.getResources().getDimensionPixelSize(R.dimen.control_status_normal));
        }
        ControlViewHolder cvh2 = getCvh();
        CharSequence charSequence = this.currentStatusText;
        cvh2.setStatusText(((Object) charSequence) + " " + this.currentRangeValue, true);
        ControlActionCoordinator controlActionCoordinator = getCvh().controlActionCoordinator;
        final ControlViewHolder cvh3 = getCvh();
        final String templateId = getRangeTemplate().getTemplateId();
        final float findNearestStep = findNearestStep(levelToRangeValue(getClipLayer().getLevel()));
        ControlActionCoordinatorImpl controlActionCoordinatorImpl = (ControlActionCoordinatorImpl) controlActionCoordinator;
        controlActionCoordinatorImpl.getClass();
        if (z) {
            Log.d("ControlsUiController", "setValue: [" + templateId + "]: " + findNearestStep);
        }
        ((ControlsMetricsLoggerImpl) controlActionCoordinatorImpl.controlsMetricsLogger).drag(cvh3, controlActionCoordinatorImpl.isLocked());
        String str = cvh3.getCws().f249ci.controlId;
        Function0 function0 = new Function0() { // from class: com.android.systemui.controls.ui.ControlActionCoordinatorImpl$setValue$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                ControlViewHolder.this.action(new FloatAction(templateId, findNearestStep));
                return Unit.INSTANCE;
            }
        };
        Control control = cvh3.getCws().control;
        controlActionCoordinatorImpl.bouncerOrRun(controlActionCoordinatorImpl.createAction(str, function0, false, control != null ? control.isAuthRequired() : true));
        getCvh().userInteractionInProgress = false;
    }

    public final float findNearestStep(float f) {
        float minValue = getRangeTemplate().getMinValue();
        float f2 = Float.MAX_VALUE;
        while (minValue <= getRangeTemplate().getMaxValue()) {
            float abs = Math.abs(f - minValue);
            if (abs >= f2) {
                return minValue - getRangeTemplate().getStepValue();
            }
            minValue += getRangeTemplate().getStepValue();
            f2 = abs;
        }
        return getRangeTemplate().getMaxValue();
    }

    public final String format(String str, String str2, float f) {
        try {
            int i = StringCompanionObject.$r8$clinit;
            return String.format(str, Arrays.copyOf(new Object[]{Float.valueOf(findNearestStep(f))}, 1));
        } catch (IllegalFormatException e) {
            Log.w("ControlsUiController", "Illegal format in range template", e);
            return Intrinsics.areEqual(str2, "") ? "" : this.format(str2, "", f);
        }
    }

    public final Drawable getClipLayer() {
        Drawable drawable = this.clipLayer;
        if (drawable != null) {
            return drawable;
        }
        return null;
    }

    @Override // com.android.systemui.controls.p005ui.CustomButtonBehavior
    public final CharSequence getContentDescription() {
        ToggleRangeTemplate toggleRangeTemplate = this.toggleRangeTemplate;
        CharSequence actionDescription = toggleRangeTemplate != null ? toggleRangeTemplate.getActionDescription() : null;
        return actionDescription == null ? "" : actionDescription;
    }

    public final ControlViewHolder getCvh() {
        ControlViewHolder controlViewHolder = this.cvh;
        if (controlViewHolder != null) {
            return controlViewHolder;
        }
        return null;
    }

    public final RangeTemplate getRangeTemplate() {
        RangeTemplate rangeTemplate = this.rangeTemplate;
        if (rangeTemplate != null) {
            return rangeTemplate;
        }
        return null;
    }

    @Override // com.android.systemui.controls.p005ui.Behavior
    public final void initialize(ControlViewHolder controlViewHolder) {
        this.cvh = controlViewHolder;
        this.context = controlViewHolder.context;
        boolean z = BasicRune.CONTROLS_SAMSUNG_STYLE;
        ViewGroup viewGroup = controlViewHolder.layout;
        if (z) {
            final CustomToggleRangeGestureListener customToggleRangeGestureListener = new CustomToggleRangeGestureListener(viewGroup);
            Context context = this.context;
            final GestureDetector gestureDetector = new GestureDetector(context != null ? context : null, customToggleRangeGestureListener);
            viewGroup.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.controls.ui.ToggleRangeBehavior$initialize$1
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    ToggleRangeBehavior.Companion companion = ToggleRangeBehavior.Companion;
                    ToggleRangeBehavior.inProgress = motionEvent.getAction() == 0 || motionEvent.getAction() == 2;
                    try {
                        if (!gestureDetector.onTouchEvent(motionEvent) && motionEvent.getAction() == 1 && customToggleRangeGestureListener.isDragging) {
                            view.getParent().requestDisallowInterceptTouchEvent(false);
                            customToggleRangeGestureListener.isDragging = false;
                            this.endUpdateRange();
                            return false;
                        }
                    } catch (IllegalArgumentException unused) {
                        Log.w("ControlsUiController", "Touch action occurred during view updating.");
                    }
                    return false;
                }
            });
            return;
        }
        final ToggleRangeGestureListener toggleRangeGestureListener = new ToggleRangeGestureListener(viewGroup);
        Context context2 = this.context;
        final GestureDetector gestureDetector2 = new GestureDetector(context2 != null ? context2 : null, toggleRangeGestureListener);
        viewGroup.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.controls.ui.ToggleRangeBehavior$initialize$2
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                if (!gestureDetector2.onTouchEvent(motionEvent) && motionEvent.getAction() == 1 && toggleRangeGestureListener.isDragging) {
                    view.getParent().requestDisallowInterceptTouchEvent(false);
                    toggleRangeGestureListener.isDragging = false;
                    this.endUpdateRange();
                }
                return false;
            }
        });
    }

    public final float levelToRangeValue(int i) {
        return MathUtils.constrainedMap(getRangeTemplate().getMinValue(), getRangeTemplate().getMaxValue(), 0.0f, 10000.0f, i);
    }

    public final boolean setupTemplate(ControlTemplate controlTemplate) {
        if (controlTemplate instanceof ToggleRangeTemplate) {
            ToggleRangeTemplate toggleRangeTemplate = (ToggleRangeTemplate) controlTemplate;
            if (BasicRune.CONTROLS_SAMSUNG_STYLE) {
                this.toggleRangeTemplate = toggleRangeTemplate;
            }
            this.rangeTemplate = toggleRangeTemplate.getRange();
            this.isToggleable = true;
            this.isChecked = toggleRangeTemplate.isChecked();
            return true;
        }
        if (controlTemplate instanceof RangeTemplate) {
            this.rangeTemplate = (RangeTemplate) controlTemplate;
            this.isChecked = !(getRangeTemplate().getCurrentValue() == getRangeTemplate().getMinValue());
            return true;
        }
        if (controlTemplate instanceof TemperatureControlTemplate) {
            return setupTemplate(((TemperatureControlTemplate) controlTemplate).getTemplate());
        }
        Log.e("ControlsUiController", "Unsupported template type: " + controlTemplate);
        return false;
    }

    public final void updateRange(int i, boolean z, boolean z2) {
        int max = Math.max(0, Math.min(10000, i));
        if (getClipLayer().getLevel() == 0 && max > 0) {
            getCvh().m119x3918d5b8(this.colorOffset, z, false);
        }
        ValueAnimator valueAnimator = this.rangeAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        if (z2) {
            boolean z3 = max == 0 || max == 10000;
            if (getClipLayer().getLevel() != max) {
                ControlActionCoordinatorImpl controlActionCoordinatorImpl = (ControlActionCoordinatorImpl) getCvh().controlActionCoordinator;
                controlActionCoordinatorImpl.getClass();
                VibratorHelper vibratorHelper = controlActionCoordinatorImpl.vibrator;
                if (z3) {
                    Vibrations.INSTANCE.getClass();
                    vibratorHelper.vibrate(Vibrations.rangeEdgeEffect);
                } else {
                    Vibrations.INSTANCE.getClass();
                    vibratorHelper.vibrate(Vibrations.rangeMiddleEffect);
                }
                getClipLayer().setLevel(max);
            }
        } else if (max != getClipLayer().getLevel()) {
            ValueAnimator ofInt = ValueAnimator.ofInt(getCvh().clipLayer.getLevel(), max);
            ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.controls.ui.ToggleRangeBehavior$updateRange$1$1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                    ToggleRangeBehavior.this.getCvh().clipLayer.setLevel(((Integer) valueAnimator2.getAnimatedValue()).intValue());
                }
            });
            ofInt.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.controls.ui.ToggleRangeBehavior$updateRange$1$2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    ToggleRangeBehavior.this.rangeAnimator = null;
                }
            });
            ofInt.setDuration(700L);
            ofInt.setInterpolator(Interpolators.CONTROL_STATE);
            ofInt.start();
            this.rangeAnimator = ofInt;
        }
        if (!z) {
            ControlViewHolder cvh = getCvh();
            CharSequence charSequence = this.currentStatusText;
            Set set = ControlViewHolder.FORCE_PANEL_DEVICES;
            cvh.setStatusText(charSequence, false);
            return;
        }
        this.currentRangeValue = format(getRangeTemplate().getFormatString().toString(), "%.1f", levelToRangeValue(max));
        if (z2) {
            getCvh().setStatusText(this.currentRangeValue, true);
            return;
        }
        ControlViewHolder cvh2 = getCvh();
        CharSequence charSequence2 = this.currentStatusText;
        String str = ((Object) charSequence2) + " " + this.currentRangeValue;
        Set set2 = ControlViewHolder.FORCE_PANEL_DEVICES;
        cvh2.setStatusText(str, false);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class CustomToggleRangeGestureListener extends GestureDetector.SimpleOnGestureListener {
        public boolean isDragging;

        /* renamed from: v */
        public final View f251v;

        public CustomToggleRangeGestureListener(View view) {
            this.f251v = view;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public final boolean onDown(MotionEvent motionEvent) {
            return true;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public final boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            if (!this.isDragging) {
                this.f251v.getParent().requestDisallowInterceptTouchEvent(true);
                ToggleRangeBehavior.this.beginUpdateRange();
                this.isDragging = true;
            }
            int width = (int) (10000 * ((-f) / this.f251v.getWidth()));
            ToggleRangeBehavior toggleRangeBehavior = ToggleRangeBehavior.this;
            toggleRangeBehavior.updateRange(toggleRangeBehavior.getClipLayer().getLevel() + width, true, true);
            return true;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public final boolean onSingleTapUp(MotionEvent motionEvent) {
            CustomControlActionCoordinator customControlActionCoordinator = ToggleRangeBehavior.this.getCvh().getCustomControlViewHolder().customControlActionCoordinator;
            if (customControlActionCoordinator == null) {
                return true;
            }
            ControlViewHolder cvh = ToggleRangeBehavior.this.getCvh();
            ToggleRangeBehavior toggleRangeBehavior = ToggleRangeBehavior.this;
            String str = toggleRangeBehavior.templateId;
            if (str == null) {
                str = null;
            }
            Control control = toggleRangeBehavior.control;
            ((ControlActionCoordinatorImpl) customControlActionCoordinator).customTouch(cvh, str, control != null ? control : null);
            return true;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public final void onLongPress(MotionEvent motionEvent) {
        }
    }
}
