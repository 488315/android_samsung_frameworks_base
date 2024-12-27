package com.android.systemui.controls.ui;

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
import com.android.systemui.R;
import com.android.systemui.controls.ui.ToggleRangeBehavior;
import com.android.systemui.controls.ui.view.ControlsActionButton;
import java.util.Arrays;
import java.util.IllegalFormatException;
import java.util.Set;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;

public final class ToggleRangeBehavior implements Behavior, SecBehavior, SecActionButtonBehavior {
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

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    @Override // com.android.systemui.controls.ui.Behavior
    public final void bind(ControlWithState controlWithState, int i) {
        Control control = controlWithState.control;
        Intrinsics.checkNotNull(control);
        this.control = control;
        this.colorOffset = i;
        this.currentStatusText = control.getStatusText();
        ControlViewHolder controlViewHolder = this.cvh;
        if (controlViewHolder == null) {
            controlViewHolder = null;
        }
        controlViewHolder.layout.setOnLongClickListener(null);
        ControlViewHolder controlViewHolder2 = this.cvh;
        if (controlViewHolder2 == null) {
            controlViewHolder2 = null;
        }
        this.clipLayer = ((LayerDrawable) controlViewHolder2.layout.getBackground()).findDrawableByLayerId(R.id.clip_layer);
        Control control2 = this.control;
        if (control2 == null) {
            control2 = null;
        }
        ControlTemplate controlTemplate = control2.getControlTemplate();
        if (setupTemplate(controlTemplate)) {
            if (this.isToggleable) {
                ControlViewHolder controlViewHolder3 = this.cvh;
                if (controlViewHolder3 == null) {
                    controlViewHolder3 = null;
                }
                ControlsActionButton controlsActionButton = controlViewHolder3.getSecControlViewHolder().actionIcon;
                if (controlsActionButton != null) {
                    controlsActionButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.controls.ui.ToggleRangeBehavior$bind$1
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            ControlViewHolder controlViewHolder4 = ToggleRangeBehavior.this.cvh;
                            if (controlViewHolder4 == null) {
                                controlViewHolder4 = null;
                            }
                            SecControlActionCoordinator secControlActionCoordinator = controlViewHolder4.getSecControlViewHolder().secControlActionCoordinator;
                            if (secControlActionCoordinator != null) {
                                ToggleRangeBehavior toggleRangeBehavior = ToggleRangeBehavior.this;
                                ControlViewHolder controlViewHolder5 = toggleRangeBehavior.cvh;
                                if (controlViewHolder5 == null) {
                                    controlViewHolder5 = null;
                                }
                                String str = toggleRangeBehavior.templateId;
                                ((ControlActionCoordinatorImpl) secControlActionCoordinator).toggleActionButton(controlViewHolder5, str != null ? str : null, toggleRangeBehavior.isChecked);
                            }
                        }
                    });
                }
            }
            this.templateId = controlTemplate.getTemplateId();
            RangeTemplate rangeTemplate = this.rangeTemplate;
            if (rangeTemplate == null) {
                rangeTemplate = null;
            }
            float currentValue = rangeTemplate.getCurrentValue();
            RangeTemplate rangeTemplate2 = this.rangeTemplate;
            if (rangeTemplate2 == null) {
                rangeTemplate2 = null;
            }
            float minValue = rangeTemplate2.getMinValue();
            RangeTemplate rangeTemplate3 = this.rangeTemplate;
            if (rangeTemplate3 == null) {
                rangeTemplate3 = null;
            }
            updateRange((int) MathUtils.constrainedMap(0.0f, 10000.0f, minValue, rangeTemplate3.getMaxValue(), currentValue), this.isChecked, false);
            ControlViewHolder controlViewHolder4 = this.cvh;
            if (controlViewHolder4 == null) {
                controlViewHolder4 = null;
            }
            controlViewHolder4.applyRenderInfo$frameworks__base__packages__SystemUI__android_common__SystemUI_core(i, this.isChecked, true);
            ControlViewHolder controlViewHolder5 = this.cvh;
            (controlViewHolder5 != null ? controlViewHolder5 : null).layout.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.systemui.controls.ui.ToggleRangeBehavior$bind$2
                @Override // android.view.View.AccessibilityDelegate
                public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                    super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                    ToggleRangeBehavior toggleRangeBehavior = ToggleRangeBehavior.this;
                    ToggleRangeBehavior.Companion companion = ToggleRangeBehavior.Companion;
                    float levelToRangeValue = toggleRangeBehavior.levelToRangeValue(0);
                    ToggleRangeBehavior toggleRangeBehavior2 = ToggleRangeBehavior.this;
                    Drawable drawable = toggleRangeBehavior2.clipLayer;
                    if (drawable == null) {
                        drawable = null;
                    }
                    float levelToRangeValue2 = toggleRangeBehavior2.levelToRangeValue(drawable.getLevel());
                    float levelToRangeValue3 = ToggleRangeBehavior.this.levelToRangeValue(10000);
                    RangeTemplate rangeTemplate4 = ToggleRangeBehavior.this.rangeTemplate;
                    double stepValue = (rangeTemplate4 != null ? rangeTemplate4 : null).getStepValue();
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

                @Override // android.view.View.AccessibilityDelegate
                public final boolean performAccessibilityAction(View view, int i2, Bundle bundle) {
                    if (i2 == 16) {
                        ControlViewHolder controlViewHolder6 = ToggleRangeBehavior.this.cvh;
                        if (controlViewHolder6 == null) {
                            controlViewHolder6 = null;
                        }
                        SecControlActionCoordinator secControlActionCoordinator = controlViewHolder6.getSecControlViewHolder().secControlActionCoordinator;
                        if (secControlActionCoordinator == null) {
                            return true;
                        }
                        ToggleRangeBehavior toggleRangeBehavior = ToggleRangeBehavior.this;
                        ControlViewHolder controlViewHolder7 = toggleRangeBehavior.cvh;
                        if (controlViewHolder7 == null) {
                            controlViewHolder7 = null;
                        }
                        String str = toggleRangeBehavior.templateId;
                        if (str == null) {
                            str = null;
                        }
                        Control control3 = toggleRangeBehavior.control;
                        ((ControlActionCoordinatorImpl) secControlActionCoordinator).touchCard(controlViewHolder7, str, control3 != null ? control3 : null);
                        return true;
                    }
                    if (i2 == 32 || i2 != AccessibilityNodeInfo.AccessibilityAction.ACTION_SET_PROGRESS.getId() || bundle == null || !bundle.containsKey("android.view.accessibility.action.ARGUMENT_PROGRESS_VALUE")) {
                        return super.performAccessibilityAction(view, i2, bundle);
                    }
                    float f = bundle.getFloat("android.view.accessibility.action.ARGUMENT_PROGRESS_VALUE");
                    ToggleRangeBehavior toggleRangeBehavior2 = ToggleRangeBehavior.this;
                    ToggleRangeBehavior.Companion companion = ToggleRangeBehavior.Companion;
                    RangeTemplate rangeTemplate4 = toggleRangeBehavior2.rangeTemplate;
                    if (rangeTemplate4 == null) {
                        rangeTemplate4 = null;
                    }
                    float minValue2 = rangeTemplate4.getMinValue();
                    RangeTemplate rangeTemplate5 = toggleRangeBehavior2.rangeTemplate;
                    int constrainedMap = (int) MathUtils.constrainedMap(0.0f, 10000.0f, minValue2, (rangeTemplate5 != null ? rangeTemplate5 : null).getMaxValue(), f);
                    ToggleRangeBehavior toggleRangeBehavior3 = ToggleRangeBehavior.this;
                    toggleRangeBehavior3.updateRange(constrainedMap, toggleRangeBehavior3.isChecked, true);
                    ToggleRangeBehavior.this.endUpdateRange();
                    return true;
                }
            });
        }
    }

    @Override // com.android.systemui.controls.ui.SecBehavior
    public final void dispose() {
        ControlViewHolder controlViewHolder = this.cvh;
        if (controlViewHolder == null) {
            controlViewHolder = null;
        }
        controlViewHolder.layout.setOnTouchListener(null);
        ControlViewHolder controlViewHolder2 = this.cvh;
        if (controlViewHolder2 == null) {
            controlViewHolder2 = null;
        }
        ControlsActionButton controlsActionButton = controlViewHolder2.getSecControlViewHolder().actionIcon;
        if (controlsActionButton != null) {
            controlsActionButton.setOnClickListener(null);
        }
        ValueAnimator valueAnimator = this.rangeAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
    }

    public final void endUpdateRange() {
        ControlViewHolder controlViewHolder = this.cvh;
        if (controlViewHolder == null) {
            controlViewHolder = null;
        }
        CharSequence charSequence = this.currentStatusText;
        controlViewHolder.setStatusText(((Object) charSequence) + " " + this.currentRangeValue, true);
        final ControlViewHolder controlViewHolder2 = this.cvh;
        ControlActionCoordinator controlActionCoordinator = (controlViewHolder2 != null ? controlViewHolder2 : null).controlActionCoordinator;
        if (controlViewHolder2 == null) {
            controlViewHolder2 = null;
        }
        RangeTemplate rangeTemplate = this.rangeTemplate;
        if (rangeTemplate == null) {
            rangeTemplate = null;
        }
        final String templateId = rangeTemplate.getTemplateId();
        Drawable drawable = this.clipLayer;
        if (drawable == null) {
            drawable = null;
        }
        final float findNearestStep = findNearestStep(levelToRangeValue(drawable.getLevel()));
        ControlActionCoordinatorImpl controlActionCoordinatorImpl = (ControlActionCoordinatorImpl) controlActionCoordinator;
        controlActionCoordinatorImpl.controlsMetricsLogger.drag(controlViewHolder2, controlActionCoordinatorImpl.isLocked());
        ControlWithState controlWithState = controlViewHolder2.cws;
        if (controlWithState == null) {
            controlWithState = null;
        }
        String str = controlWithState.ci.controlId;
        Function0 function0 = new Function0() { // from class: com.android.systemui.controls.ui.ControlActionCoordinatorImpl$setValue$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                ControlViewHolder.this.action(new FloatAction(templateId, findNearestStep));
                return Unit.INSTANCE;
            }
        };
        ControlWithState controlWithState2 = controlViewHolder2.cws;
        if (controlWithState2 == null) {
            controlWithState2 = null;
        }
        Control control = controlWithState2.control;
        controlActionCoordinatorImpl.bouncerOrRun(controlActionCoordinatorImpl.createAction(str, function0, false, control != null ? control.isAuthRequired() : true));
        ControlViewHolder controlViewHolder3 = this.cvh;
        (controlViewHolder3 != null ? controlViewHolder3 : null).getClass();
    }

    public final float findNearestStep(float f) {
        RangeTemplate rangeTemplate = this.rangeTemplate;
        if (rangeTemplate == null) {
            rangeTemplate = null;
        }
        float minValue = rangeTemplate.getMinValue();
        float f2 = Float.MAX_VALUE;
        while (true) {
            RangeTemplate rangeTemplate2 = this.rangeTemplate;
            if (rangeTemplate2 == null) {
                rangeTemplate2 = null;
            }
            if (minValue > rangeTemplate2.getMaxValue()) {
                RangeTemplate rangeTemplate3 = this.rangeTemplate;
                return (rangeTemplate3 != null ? rangeTemplate3 : null).getMaxValue();
            }
            float abs = Math.abs(f - minValue);
            if (abs >= f2) {
                RangeTemplate rangeTemplate4 = this.rangeTemplate;
                return minValue - (rangeTemplate4 != null ? rangeTemplate4 : null).getStepValue();
            }
            RangeTemplate rangeTemplate5 = this.rangeTemplate;
            if (rangeTemplate5 == null) {
                rangeTemplate5 = null;
            }
            minValue += rangeTemplate5.getStepValue();
            f2 = abs;
        }
    }

    public final String format(String str, String str2, float f) {
        try {
            int i = StringCompanionObject.$r8$clinit;
            return String.format(str, Arrays.copyOf(new Object[]{Float.valueOf(findNearestStep(f))}, 1));
        } catch (IllegalFormatException e) {
            Log.w("ControlsUiController", "Illegal format in range template", e);
            return str2.equals("") ? "" : this.format(str2, "", f);
        }
    }

    @Override // com.android.systemui.controls.ui.SecActionButtonBehavior
    public final CharSequence getContentDescription() {
        CharSequence actionDescription;
        ToggleRangeTemplate toggleRangeTemplate = this.toggleRangeTemplate;
        return (toggleRangeTemplate == null || (actionDescription = toggleRangeTemplate.getActionDescription()) == null) ? "" : actionDescription;
    }

    @Override // com.android.systemui.controls.ui.Behavior
    public final void initialize(ControlViewHolder controlViewHolder) {
        this.cvh = controlViewHolder;
        this.context = controlViewHolder.context;
        final SecToggleRangeGestureListener secToggleRangeGestureListener = new SecToggleRangeGestureListener(controlViewHolder.layout);
        Context context = this.context;
        if (context == null) {
            context = null;
        }
        final GestureDetector gestureDetector = new GestureDetector(context, secToggleRangeGestureListener);
        controlViewHolder.layout.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.controls.ui.ToggleRangeBehavior$initialize$1
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                ToggleRangeBehavior.Companion companion = ToggleRangeBehavior.Companion;
                ToggleRangeBehavior.inProgress = motionEvent.getAction() == 0 || motionEvent.getAction() == 2;
                try {
                    if (!gestureDetector.onTouchEvent(motionEvent) && motionEvent.getAction() == 1 && secToggleRangeGestureListener.isDragging) {
                        view.getParent().requestDisallowInterceptTouchEvent(false);
                        secToggleRangeGestureListener.isDragging = false;
                        this.endUpdateRange();
                        return false;
                    }
                } catch (IllegalArgumentException unused) {
                    Log.w("ControlsUiController", "Touch action occurred during view updating.");
                }
                return false;
            }
        });
    }

    public final float levelToRangeValue(int i) {
        RangeTemplate rangeTemplate = this.rangeTemplate;
        if (rangeTemplate == null) {
            rangeTemplate = null;
        }
        float minValue = rangeTemplate.getMinValue();
        RangeTemplate rangeTemplate2 = this.rangeTemplate;
        return MathUtils.constrainedMap(minValue, (rangeTemplate2 != null ? rangeTemplate2 : null).getMaxValue(), 0.0f, 10000.0f, i);
    }

    public final boolean setupTemplate(ControlTemplate controlTemplate) {
        if (controlTemplate instanceof ToggleRangeTemplate) {
            ToggleRangeTemplate toggleRangeTemplate = (ToggleRangeTemplate) controlTemplate;
            this.toggleRangeTemplate = toggleRangeTemplate;
            this.rangeTemplate = toggleRangeTemplate.getRange();
            this.isToggleable = true;
            this.isChecked = toggleRangeTemplate.isChecked();
            return true;
        }
        if (!(controlTemplate instanceof RangeTemplate)) {
            if (controlTemplate instanceof TemperatureControlTemplate) {
                return setupTemplate(((TemperatureControlTemplate) controlTemplate).getTemplate());
            }
            Log.e("ControlsUiController", "Unsupported template type: " + controlTemplate);
            return false;
        }
        RangeTemplate rangeTemplate = (RangeTemplate) controlTemplate;
        this.rangeTemplate = rangeTemplate;
        float currentValue = rangeTemplate.getCurrentValue();
        RangeTemplate rangeTemplate2 = this.rangeTemplate;
        if (rangeTemplate2 == null) {
            rangeTemplate2 = null;
        }
        this.isChecked = !(currentValue == rangeTemplate2.getMinValue());
        return true;
    }

    public final void updateRange(int i, boolean z, boolean z2) {
        ControlViewHolder controlViewHolder;
        int max = Math.max(0, Math.min(10000, i));
        Drawable drawable = this.clipLayer;
        if (drawable == null) {
            drawable = null;
        }
        if (drawable.getLevel() == 0 && max > 0) {
            ControlViewHolder controlViewHolder2 = this.cvh;
            if (controlViewHolder2 == null) {
                controlViewHolder2 = null;
            }
            controlViewHolder2.applyRenderInfo$frameworks__base__packages__SystemUI__android_common__SystemUI_core(this.colorOffset, z, false);
        }
        ValueAnimator valueAnimator = this.rangeAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        if (z2) {
            boolean z3 = max == 0 || max == 10000;
            Drawable drawable2 = this.clipLayer;
            if (drawable2 == null) {
                drawable2 = null;
            }
            if (drawable2.getLevel() != max) {
                ControlViewHolder controlViewHolder3 = this.cvh;
                ControlActionCoordinator controlActionCoordinator = (controlViewHolder3 != null ? controlViewHolder3 : null).controlActionCoordinator;
                if (controlViewHolder3 == null) {
                    controlViewHolder3 = null;
                }
                ControlActionCoordinatorImpl controlActionCoordinatorImpl = (ControlActionCoordinatorImpl) controlActionCoordinator;
                controlActionCoordinatorImpl.getClass();
                int i2 = z3 ? 26 : 27;
                ViewGroup viewGroup = controlViewHolder3.layout;
                controlActionCoordinatorImpl.vibrator.getClass();
                viewGroup.performHapticFeedback(i2);
                Drawable drawable3 = this.clipLayer;
                if (drawable3 == null) {
                    drawable3 = null;
                }
                drawable3.setLevel(max);
            }
        } else {
            Drawable drawable4 = this.clipLayer;
            if (drawable4 == null) {
                drawable4 = null;
            }
            if (max != drawable4.getLevel()) {
                ControlViewHolder controlViewHolder4 = this.cvh;
                if (controlViewHolder4 == null) {
                    controlViewHolder4 = null;
                }
                ValueAnimator ofInt = ValueAnimator.ofInt(controlViewHolder4.clipLayer.getLevel(), max);
                ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.controls.ui.ToggleRangeBehavior$updateRange$1$1
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                        ControlViewHolder controlViewHolder5 = ToggleRangeBehavior.this.cvh;
                        if (controlViewHolder5 == null) {
                            controlViewHolder5 = null;
                        }
                        controlViewHolder5.clipLayer.setLevel(((Integer) valueAnimator2.getAnimatedValue()).intValue());
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
        }
        if (!z) {
            ControlViewHolder controlViewHolder5 = this.cvh;
            controlViewHolder = controlViewHolder5 != null ? controlViewHolder5 : null;
            CharSequence charSequence = this.currentStatusText;
            Set set = ControlViewHolder.FORCE_PANEL_DEVICES;
            controlViewHolder.setStatusText(charSequence, false);
            return;
        }
        float levelToRangeValue = levelToRangeValue(max);
        RangeTemplate rangeTemplate = this.rangeTemplate;
        if (rangeTemplate == null) {
            rangeTemplate = null;
        }
        String format = format(rangeTemplate.getFormatString().toString(), "%.1f", levelToRangeValue);
        this.currentRangeValue = format;
        if (z2) {
            ControlViewHolder controlViewHolder6 = this.cvh;
            (controlViewHolder6 != null ? controlViewHolder6 : null).setStatusText(format, true);
            return;
        }
        ControlViewHolder controlViewHolder7 = this.cvh;
        controlViewHolder = controlViewHolder7 != null ? controlViewHolder7 : null;
        String str = ((Object) this.currentStatusText) + " " + format;
        Set set2 = ControlViewHolder.FORCE_PANEL_DEVICES;
        controlViewHolder.setStatusText(str, false);
    }

    public final class SecToggleRangeGestureListener extends GestureDetector.SimpleOnGestureListener {
        public boolean isDragging;
        public final View v;

        public SecToggleRangeGestureListener(View view) {
            this.v = view;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public final boolean onDown(MotionEvent motionEvent) {
            return true;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public final boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            if (!this.isDragging) {
                this.v.getParent().requestDisallowInterceptTouchEvent(true);
                ControlViewHolder controlViewHolder = ToggleRangeBehavior.this.cvh;
                if (controlViewHolder == null) {
                    controlViewHolder = null;
                }
                controlViewHolder.getClass();
                this.isDragging = true;
            }
            int width = (int) (10000 * ((-f) / this.v.getWidth()));
            ToggleRangeBehavior toggleRangeBehavior = ToggleRangeBehavior.this;
            Drawable drawable = toggleRangeBehavior.clipLayer;
            toggleRangeBehavior.updateRange((drawable != null ? drawable : null).getLevel() + width, true, true);
            return true;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public final boolean onSingleTapUp(MotionEvent motionEvent) {
            ControlViewHolder controlViewHolder = ToggleRangeBehavior.this.cvh;
            if (controlViewHolder == null) {
                controlViewHolder = null;
            }
            SecControlActionCoordinator secControlActionCoordinator = controlViewHolder.getSecControlViewHolder().secControlActionCoordinator;
            if (secControlActionCoordinator == null) {
                return true;
            }
            ToggleRangeBehavior toggleRangeBehavior = ToggleRangeBehavior.this;
            ControlViewHolder controlViewHolder2 = toggleRangeBehavior.cvh;
            if (controlViewHolder2 == null) {
                controlViewHolder2 = null;
            }
            String str = toggleRangeBehavior.templateId;
            if (str == null) {
                str = null;
            }
            Control control = toggleRangeBehavior.control;
            ((ControlActionCoordinatorImpl) secControlActionCoordinator).touchCard(controlViewHolder2, str, control != null ? control : null);
            return true;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public final void onLongPress(MotionEvent motionEvent) {
        }
    }
}
