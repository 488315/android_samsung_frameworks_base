package com.google.android.material.slider;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.SeekBar;
import androidx.appcompat.app.AlertController$$ExternalSyntheticOutline0;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.CubicBezierEasing$$ExternalSyntheticOutline0;
import androidx.compose.foundation.shape.DpCornerSize$$ExternalSyntheticOutline0;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.math.MathUtils;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.customview.widget.ExploreByTouchHelper;
import com.android.systemui.R;
import com.google.android.material.R$styleable;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.internal.DescendantOffsetUtils;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewOverlayApi18;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.motion.MotionUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.CornerTreatment;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.shape.RelativeCornerSize;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.slider.Slider;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import com.google.android.material.tooltip.TooltipDrawable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.WeakHashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class BaseSlider extends View {
    public static final /* synthetic */ int $r8$clinit = 0;
    public AccessibilityEventSender accessibilityEventSender;
    public final AccessibilityHelper accessibilityHelper;
    public final AccessibilityManager accessibilityManager;
    public int activeThumbIdx;
    public final Paint activeTicksPaint;
    public final Paint activeTrackPaint;
    public final List changeListeners;
    public final RectF cornerRect;
    public final List customThumbDrawablesForValues;
    public final MaterialShapeDrawable defaultThumbDrawable;
    public final int defaultThumbRadius;
    public int defaultThumbTrackGapSize;
    public int defaultThumbWidth;
    public final int defaultTickActiveRadius;
    public final int defaultTickInactiveRadius;
    public final int defaultTrackHeight;
    public boolean dirtyConfig;
    public int focusedThumbIdx;
    public boolean forceDrawCompatHalo;
    public ColorStateList haloColor;
    public final Paint haloPaint;
    public final int haloRadius;
    public final Paint inactiveTicksPaint;
    public final Paint inactiveTrackPaint;
    public boolean isLongPress;
    public int labelBehavior;
    public final int labelPadding;
    public final int labelStyle;
    public final List labels;
    public boolean labelsAreAnimatedIn;
    public ValueAnimator labelsInAnimator;
    public ValueAnimator labelsOutAnimator;
    public MotionEvent lastEvent;
    public final int minTickSpacing;
    public final int minTouchTargetSize;
    public final int minTrackSidePadding;
    public final int minWidgetHeight;
    public final BaseSlider$$ExternalSyntheticLambda0 onScrollChangedListener;
    public final int scaledTouchSlop;
    public float stepSize;
    public final Paint stopIndicatorPaint;
    public final int thumbHeight;
    public boolean thumbIsPressed;
    public final Paint thumbPaint;
    public int thumbTrackGapSize;
    public int thumbWidth;
    public int tickActiveRadius;
    public ColorStateList tickColorActive;
    public ColorStateList tickColorInactive;
    public int tickInactiveRadius;
    public boolean tickVisible;
    public float[] ticksCoordinates;
    public float touchDownX;
    public final List touchListeners;
    public float touchPosition;
    public ColorStateList trackColorActive;
    public ColorStateList trackColorInactive;
    public int trackHeight;
    public final int trackInsideCornerSize;
    public final Path trackPath;
    public final RectF trackRect;
    public int trackSidePadding;
    public final int trackStopIndicatorSize;
    public int trackWidth;
    public float valueFrom;
    public float valueTo;
    public ArrayList values;
    public int widgetHeight;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class AccessibilityEventSender implements Runnable {
        public int virtualViewId;

        private AccessibilityEventSender() {
            this.virtualViewId = -1;
        }

        @Override // java.lang.Runnable
        public final void run() {
            BaseSlider.this.accessibilityHelper.sendEventForVirtualView(this.virtualViewId, 4);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class AccessibilityHelper extends ExploreByTouchHelper {
        public final BaseSlider slider;
        public final Rect virtualViewBounds;

        public AccessibilityHelper(BaseSlider baseSlider) {
            super(baseSlider);
            this.virtualViewBounds = new Rect();
            this.slider = baseSlider;
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public final int getVirtualViewAt(float f, float f2) {
            int i = 0;
            while (true) {
                BaseSlider baseSlider = this.slider;
                if (i >= ((ArrayList) baseSlider.getValues()).size()) {
                    return -1;
                }
                baseSlider.updateBoundsForVirtualViewId(i, this.virtualViewBounds);
                if (this.virtualViewBounds.contains((int) f, (int) f2)) {
                    return i;
                }
                i++;
            }
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public final void getVisibleVirtualViews(List list) {
            for (int i = 0; i < ((ArrayList) this.slider.getValues()).size(); i++) {
                ((ArrayList) list).add(Integer.valueOf(i));
            }
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public final boolean onPerformActionForVirtualView(int i, int i2, Bundle bundle) {
            BaseSlider baseSlider = this.slider;
            if (!baseSlider.isEnabled()) {
                return false;
            }
            if (i2 != 4096 && i2 != 8192) {
                if (i2 != 16908349 || bundle == null || !bundle.containsKey("android.view.accessibility.action.ARGUMENT_PROGRESS_VALUE")) {
                    return false;
                }
                float f = bundle.getFloat("android.view.accessibility.action.ARGUMENT_PROGRESS_VALUE");
                int i3 = BaseSlider.$r8$clinit;
                if (!baseSlider.snapThumbToValue(f, i)) {
                    return false;
                }
                baseSlider.updateHaloHotspot();
                baseSlider.postInvalidate();
                invalidateVirtualView(i);
                return true;
            }
            int i4 = BaseSlider.$r8$clinit;
            float f2 = baseSlider.stepSize;
            if (f2 == 0.0f) {
                f2 = 1.0f;
            }
            if ((baseSlider.valueTo - baseSlider.valueFrom) / f2 > 20) {
                f2 *= Math.round(r1 / r4);
            }
            if (i2 == 8192) {
                f2 = -f2;
            }
            if (baseSlider.isRtl()) {
                f2 = -f2;
            }
            if (!baseSlider.snapThumbToValue(MathUtils.clamp(((Float) ((ArrayList) baseSlider.getValues()).get(i)).floatValue() + f2, baseSlider.valueFrom, baseSlider.valueTo), i)) {
                return false;
            }
            baseSlider.updateHaloHotspot();
            baseSlider.postInvalidate();
            invalidateVirtualView(i);
            return true;
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public final void onPopulateNodeForVirtualView(int i, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            accessibilityNodeInfoCompat.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SET_PROGRESS);
            BaseSlider baseSlider = this.slider;
            ArrayList arrayList = (ArrayList) baseSlider.getValues();
            Float f = (Float) arrayList.get(i);
            float floatValue = f.floatValue();
            float f2 = baseSlider.valueFrom;
            float f3 = baseSlider.valueTo;
            if (baseSlider.isEnabled()) {
                if (floatValue > f2) {
                    accessibilityNodeInfoCompat.addAction(8192);
                }
                if (floatValue < f3) {
                    accessibilityNodeInfoCompat.addAction(4096);
                }
            }
            accessibilityNodeInfoCompat.mInfo.setRangeInfo((AccessibilityNodeInfo.RangeInfo) new AccessibilityNodeInfoCompat.RangeInfoCompat(AccessibilityNodeInfo.RangeInfo.obtain(1, f2, f3, floatValue)).mInfo);
            accessibilityNodeInfoCompat.setClassName(SeekBar.class.getName());
            StringBuilder sb = new StringBuilder();
            if (baseSlider.getContentDescription() != null) {
                sb.append(baseSlider.getContentDescription());
                sb.append(",");
            }
            String format = String.format(((float) ((int) floatValue)) == floatValue ? "%.0f" : "%.2f", f);
            String string = baseSlider.getContext().getString(R.string.material_slider_value);
            if (arrayList.size() > 1) {
                string = i == ((ArrayList) baseSlider.getValues()).size() - 1 ? baseSlider.getContext().getString(R.string.material_slider_range_end) : i == 0 ? baseSlider.getContext().getString(R.string.material_slider_range_start) : "";
            }
            Locale locale = Locale.US;
            sb.append(string + ", " + format);
            accessibilityNodeInfoCompat.setContentDescription(sb.toString());
            baseSlider.updateBoundsForVirtualViewId(i, this.virtualViewBounds);
            accessibilityNodeInfoCompat.setBoundsInParent(this.virtualViewBounds);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    enum FullCornerDirection {
        /* JADX INFO: Fake field, exist only in values array */
        BOTH,
        LEFT,
        RIGHT,
        NONE
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class SliderState extends View.BaseSavedState {
        public static final Parcelable.Creator<SliderState> CREATOR = new Parcelable.Creator() { // from class: com.google.android.material.slider.BaseSlider.SliderState.1
            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel parcel) {
                return new SliderState(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i) {
                return new SliderState[i];
            }
        };
        public boolean hasFocus;
        public float stepSize;
        public float valueFrom;
        public float valueTo;
        public ArrayList values;

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeFloat(this.valueFrom);
            parcel.writeFloat(this.valueTo);
            parcel.writeList(this.values);
            parcel.writeFloat(this.stepSize);
            parcel.writeBooleanArray(new boolean[]{this.hasFocus});
        }

        public SliderState(Parcelable parcelable) {
            super(parcelable);
        }

        private SliderState(Parcel parcel) {
            super(parcel);
            this.valueFrom = parcel.readFloat();
            this.valueTo = parcel.readFloat();
            ArrayList arrayList = new ArrayList();
            this.values = arrayList;
            parcel.readList(arrayList, Float.class.getClassLoader());
            this.stepSize = parcel.readFloat();
            this.hasFocus = parcel.createBooleanArray()[0];
        }
    }

    public BaseSlider(Context context) {
        this(context, null);
    }

    public final void adjustCustomThumbDrawableBounds(Drawable drawable) {
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        if (intrinsicWidth == -1 && intrinsicHeight == -1) {
            drawable.setBounds(0, 0, this.thumbWidth, this.thumbHeight);
        } else {
            float max = Math.max(this.thumbWidth, this.thumbHeight) / Math.max(intrinsicWidth, intrinsicHeight);
            drawable.setBounds(0, 0, (int) (intrinsicWidth * max), (int) (intrinsicHeight * max));
        }
    }

    public final int calculateTrackCenter() {
        int i = this.widgetHeight / 2;
        int i2 = this.labelBehavior;
        return i + ((i2 == 1 || i2 == 3) ? ((TooltipDrawable) ((ArrayList) this.labels).get(0)).getIntrinsicHeight() : 0);
    }

    public final ValueAnimator createLabelAnimator(boolean z) {
        int resolveThemeDuration;
        TimeInterpolator resolveThemeInterpolator;
        float f = z ? 0.0f : 1.0f;
        ValueAnimator valueAnimator = z ? this.labelsOutAnimator : this.labelsInAnimator;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            f = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            valueAnimator.cancel();
        }
        ValueAnimator ofFloat = ValueAnimator.ofFloat(f, z ? 1.0f : 0.0f);
        if (z) {
            resolveThemeDuration = MotionUtils.resolveThemeDuration(getContext(), R.attr.motionDurationMedium4, 83);
            resolveThemeInterpolator = MotionUtils.resolveThemeInterpolator(getContext(), R.attr.motionEasingEmphasizedInterpolator, AnimationUtils.DECELERATE_INTERPOLATOR);
        } else {
            resolveThemeDuration = MotionUtils.resolveThemeDuration(getContext(), R.attr.motionDurationShort3, 117);
            resolveThemeInterpolator = MotionUtils.resolveThemeInterpolator(getContext(), R.attr.motionEasingEmphasizedAccelerateInterpolator, AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR);
        }
        ofFloat.setDuration(resolveThemeDuration);
        ofFloat.setInterpolator(resolveThemeInterpolator);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.slider.BaseSlider.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                float floatValue = ((Float) valueAnimator2.getAnimatedValue()).floatValue();
                ArrayList arrayList = (ArrayList) BaseSlider.this.labels;
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    TooltipDrawable tooltipDrawable = (TooltipDrawable) obj;
                    tooltipDrawable.tooltipPivotY = 1.2f;
                    tooltipDrawable.tooltipScaleX = floatValue;
                    tooltipDrawable.tooltipScaleY = floatValue;
                    tooltipDrawable.labelOpacity = AnimationUtils.lerp(0.0f, 1.0f, 0.19f, 1.0f, floatValue);
                    tooltipDrawable.invalidateSelf();
                }
                BaseSlider baseSlider = BaseSlider.this;
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                baseSlider.postInvalidateOnAnimation();
            }
        });
        return ofFloat;
    }

    @Override // android.view.View
    public final boolean dispatchHoverEvent(MotionEvent motionEvent) {
        return this.accessibilityHelper.dispatchHoverEvent(motionEvent) || super.dispatchHoverEvent(motionEvent);
    }

    public final void drawThumbDrawable(Canvas canvas, int i, int i2, float f, Drawable drawable) {
        canvas.save();
        canvas.translate((this.trackSidePadding + ((int) (normalizeValue(f) * i))) - (drawable.getBounds().width() / 2.0f), i2 - (drawable.getBounds().height() / 2.0f));
        drawable.draw(canvas);
        canvas.restore();
    }

    @Override // android.view.View
    public final void drawableStateChanged() {
        super.drawableStateChanged();
        this.inactiveTrackPaint.setColor(getColorForState(this.trackColorInactive));
        this.activeTrackPaint.setColor(getColorForState(this.trackColorActive));
        this.inactiveTicksPaint.setColor(getColorForState(this.tickColorInactive));
        this.activeTicksPaint.setColor(getColorForState(this.tickColorActive));
        this.stopIndicatorPaint.setColor(getColorForState(this.trackColorActive));
        ArrayList arrayList = (ArrayList) this.labels;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            TooltipDrawable tooltipDrawable = (TooltipDrawable) obj;
            if (tooltipDrawable.isStateful()) {
                tooltipDrawable.setState(getDrawableState());
            }
        }
        if (this.defaultThumbDrawable.isStateful()) {
            this.defaultThumbDrawable.setState(getDrawableState());
        }
        this.haloPaint.setColor(getColorForState(this.haloColor));
        this.haloPaint.setAlpha(63);
    }

    public final void ensureLabelsAdded() {
        if (!this.labelsAreAnimatedIn) {
            this.labelsAreAnimatedIn = true;
            ValueAnimator createLabelAnimator = createLabelAnimator(true);
            this.labelsInAnimator = createLabelAnimator;
            this.labelsOutAnimator = null;
            createLabelAnimator.start();
        }
        Iterator it = ((ArrayList) this.labels).iterator();
        for (int i = 0; i < this.values.size() && it.hasNext(); i++) {
            if (i != this.focusedThumbIdx) {
                setValueForLabel((TooltipDrawable) it.next(), ((Float) this.values.get(i)).floatValue());
            }
        }
        if (!it.hasNext()) {
            throw new IllegalStateException(String.format("Not enough labels(%d) to display all the values(%d)", Integer.valueOf(((ArrayList) this.labels).size()), Integer.valueOf(this.values.size())));
        }
        setValueForLabel((TooltipDrawable) it.next(), ((Float) this.values.get(this.focusedThumbIdx)).floatValue());
    }

    public final void ensureLabelsRemoved() {
        if (this.labelsAreAnimatedIn) {
            this.labelsAreAnimatedIn = false;
            ValueAnimator createLabelAnimator = createLabelAnimator(false);
            this.labelsOutAnimator = createLabelAnimator;
            this.labelsInAnimator = null;
            createLabelAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.slider.BaseSlider.2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    ViewGroup contentView = ViewUtils.getContentView(BaseSlider.this);
                    ViewOverlayApi18 viewOverlayApi18 = contentView == null ? null : new ViewOverlayApi18(contentView);
                    ArrayList arrayList = (ArrayList) BaseSlider.this.labels;
                    int size = arrayList.size();
                    int i = 0;
                    while (i < size) {
                        Object obj = arrayList.get(i);
                        i++;
                        viewOverlayApi18.viewOverlay.remove((TooltipDrawable) obj);
                    }
                }
            });
            this.labelsOutAnimator.start();
        }
    }

    public void forceDrawCompatHalo(boolean z) {
        this.forceDrawCompatHalo = z;
    }

    @Override // android.view.View
    public final CharSequence getAccessibilityClassName() {
        return SeekBar.class.getName();
    }

    public final int getAccessibilityFocusedVirtualViewId() {
        return this.accessibilityHelper.mAccessibilityFocusedVirtualViewId;
    }

    public final float[] getActiveRange() {
        float floatValue = ((Float) this.values.get(0)).floatValue();
        float floatValue2 = ((Float) AlertController$$ExternalSyntheticOutline0.m(this.values, 1)).floatValue();
        if (this.values.size() == 1) {
            floatValue = this.valueFrom;
        }
        float normalizeValue = normalizeValue(floatValue);
        float normalizeValue2 = normalizeValue(floatValue2);
        return isRtl() ? new float[]{normalizeValue2, normalizeValue} : new float[]{normalizeValue, normalizeValue2};
    }

    public final int getColorForState(ColorStateList colorStateList) {
        return colorStateList.getColorForState(getDrawableState(), colorStateList.getDefaultColor());
    }

    public final List getValues() {
        return new ArrayList(this.values);
    }

    public final boolean isPotentialVerticalScroll(MotionEvent motionEvent) {
        if (motionEvent.getToolType(0) != 3) {
            for (ViewParent parent = getParent(); parent instanceof ViewGroup; parent = parent.getParent()) {
                ViewGroup viewGroup = (ViewGroup) parent;
                if ((viewGroup.canScrollVertically(1) || viewGroup.canScrollVertically(-1)) && viewGroup.shouldDelayChildPressedState()) {
                    return true;
                }
            }
        }
        return false;
    }

    public final boolean isRtl() {
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        return getLayoutDirection() == 1;
    }

    public final void maybeCalculateTicksCoordinates() {
        if (this.stepSize <= 0.0f) {
            return;
        }
        validateConfigurationIfDirty();
        int min = Math.min((int) (((this.valueTo - this.valueFrom) / this.stepSize) + 1.0f), (this.trackWidth / this.minTickSpacing) + 1);
        float[] fArr = this.ticksCoordinates;
        if (fArr == null || fArr.length != min * 2) {
            this.ticksCoordinates = new float[min * 2];
        }
        float f = this.trackWidth / (min - 1);
        for (int i = 0; i < min * 2; i += 2) {
            float[] fArr2 = this.ticksCoordinates;
            fArr2[i] = ((i / 2.0f) * f) + this.trackSidePadding;
            fArr2[i + 1] = calculateTrackCenter();
        }
    }

    public final boolean moveFocus(int i) {
        int i2 = this.focusedThumbIdx;
        long j = i2 + i;
        long size = this.values.size() - 1;
        if (j < 0) {
            j = 0;
        } else if (j > size) {
            j = size;
        }
        int i3 = (int) j;
        this.focusedThumbIdx = i3;
        if (i3 == i2) {
            return false;
        }
        if (this.activeThumbIdx != -1) {
            this.activeThumbIdx = i3;
        }
        updateHaloHotspot();
        postInvalidate();
        return true;
    }

    public final void moveFocusInAbsoluteDirection(int i) {
        if (isRtl()) {
            i = i == Integer.MIN_VALUE ? Integer.MAX_VALUE : -i;
        }
        moveFocus(i);
    }

    public final float normalizeValue(float f) {
        float f2 = this.valueFrom;
        float f3 = (f - f2) / (this.valueTo - f2);
        return isRtl() ? 1.0f - f3 : f3;
    }

    @Override // android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnScrollChangedListener(this.onScrollChangedListener);
        ArrayList arrayList = (ArrayList) this.labels;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            TooltipDrawable tooltipDrawable = (TooltipDrawable) obj;
            ViewGroup contentView = ViewUtils.getContentView(this);
            if (contentView == null) {
                tooltipDrawable.getClass();
            } else {
                tooltipDrawable.getClass();
                int[] iArr = new int[2];
                contentView.getLocationOnScreen(iArr);
                tooltipDrawable.locationOnScreenX = iArr[0];
                contentView.getWindowVisibleDisplayFrame(tooltipDrawable.displayFrame);
                contentView.addOnLayoutChangeListener(tooltipDrawable.attachedViewLayoutChangeListener);
            }
        }
    }

    @Override // android.view.View
    public final void onDetachedFromWindow() {
        AccessibilityEventSender accessibilityEventSender = this.accessibilityEventSender;
        if (accessibilityEventSender != null) {
            removeCallbacks(accessibilityEventSender);
        }
        int i = 0;
        this.labelsAreAnimatedIn = false;
        ArrayList arrayList = (ArrayList) this.labels;
        int size = arrayList.size();
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            TooltipDrawable tooltipDrawable = (TooltipDrawable) obj;
            ViewGroup contentView = ViewUtils.getContentView(this);
            ViewOverlayApi18 viewOverlayApi18 = contentView == null ? null : new ViewOverlayApi18(contentView);
            if (viewOverlayApi18 != null) {
                viewOverlayApi18.viewOverlay.remove(tooltipDrawable);
                ViewGroup contentView2 = ViewUtils.getContentView(this);
                if (contentView2 == null) {
                    tooltipDrawable.getClass();
                } else {
                    contentView2.removeOnLayoutChangeListener(tooltipDrawable.attachedViewLayoutChangeListener);
                }
            }
        }
        getViewTreeObserver().removeOnScrollChangedListener(this.onScrollChangedListener);
        super.onDetachedFromWindow();
    }

    /* JADX WARN: Removed duplicated region for block: B:39:0x0174  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0188 A[SYNTHETIC] */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onDraw(android.graphics.Canvas r18) {
        /*
            Method dump skipped, instructions count: 730
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.slider.BaseSlider.onDraw(android.graphics.Canvas):void");
    }

    @Override // android.view.View
    public final void onFocusChanged(boolean z, int i, Rect rect) {
        super.onFocusChanged(z, i, rect);
        if (!z) {
            this.activeThumbIdx = -1;
            this.accessibilityHelper.clearKeyboardFocusForVirtualView(this.focusedThumbIdx);
            return;
        }
        if (i == 1) {
            moveFocus(Integer.MAX_VALUE);
        } else if (i == 2) {
            moveFocus(Integer.MIN_VALUE);
        } else if (i == 17) {
            moveFocusInAbsoluteDirection(Integer.MAX_VALUE);
        } else if (i == 66) {
            moveFocusInAbsoluteDirection(Integer.MIN_VALUE);
        }
        this.accessibilityHelper.requestKeyboardFocusForVirtualView(this.focusedThumbIdx);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (!isEnabled()) {
            return super.onKeyDown(i, keyEvent);
        }
        if (this.values.size() == 1) {
            this.activeThumbIdx = 0;
        }
        Float f = null;
        Boolean valueOf = null;
        if (this.activeThumbIdx == -1) {
            if (i != 61) {
                if (i != 66) {
                    if (i != 81) {
                        if (i == 69) {
                            moveFocus(-1);
                            valueOf = Boolean.TRUE;
                        } else if (i != 70) {
                            switch (i) {
                                case 21:
                                    moveFocusInAbsoluteDirection(-1);
                                    valueOf = Boolean.TRUE;
                                    break;
                                case 22:
                                    moveFocusInAbsoluteDirection(1);
                                    valueOf = Boolean.TRUE;
                                    break;
                            }
                        }
                    }
                    moveFocus(1);
                    valueOf = Boolean.TRUE;
                }
                this.activeThumbIdx = this.focusedThumbIdx;
                postInvalidate();
                valueOf = Boolean.TRUE;
            } else {
                valueOf = keyEvent.hasNoModifiers() ? Boolean.valueOf(moveFocus(1)) : keyEvent.isShiftPressed() ? Boolean.valueOf(moveFocus(-1)) : Boolean.FALSE;
            }
            return valueOf != null ? valueOf.booleanValue() : super.onKeyDown(i, keyEvent);
        }
        boolean isLongPress = this.isLongPress | keyEvent.isLongPress();
        this.isLongPress = isLongPress;
        if (isLongPress) {
            float f2 = this.stepSize;
            r10 = f2 != 0.0f ? f2 : 1.0f;
            if ((this.valueTo - this.valueFrom) / r10 > 20) {
                r10 *= Math.round(r0 / r11);
            }
        } else {
            float f3 = this.stepSize;
            if (f3 != 0.0f) {
                r10 = f3;
            }
        }
        if (i == 21) {
            if (!isRtl()) {
                r10 = -r10;
            }
            f = Float.valueOf(r10);
        } else if (i == 22) {
            if (isRtl()) {
                r10 = -r10;
            }
            f = Float.valueOf(r10);
        } else if (i == 69) {
            f = Float.valueOf(-r10);
        } else if (i == 70 || i == 81) {
            f = Float.valueOf(r10);
        }
        if (f != null) {
            if (snapThumbToValue(f.floatValue() + ((Float) this.values.get(this.activeThumbIdx)).floatValue(), this.activeThumbIdx)) {
                updateHaloHotspot();
                postInvalidate();
            }
            return true;
        }
        if (i != 23) {
            if (i == 61) {
                if (keyEvent.hasNoModifiers()) {
                    return moveFocus(1);
                }
                if (keyEvent.isShiftPressed()) {
                    return moveFocus(-1);
                }
                return false;
            }
            if (i != 66) {
                return super.onKeyDown(i, keyEvent);
            }
        }
        this.activeThumbIdx = -1;
        postInvalidate();
        return true;
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public final boolean onKeyUp(int i, KeyEvent keyEvent) {
        this.isLongPress = false;
        return super.onKeyUp(i, keyEvent);
    }

    @Override // android.view.View
    public final void onMeasure(int i, int i2) {
        int i3 = this.widgetHeight;
        int i4 = this.labelBehavior;
        super.onMeasure(i, View.MeasureSpec.makeMeasureSpec(i3 + ((i4 == 1 || i4 == 3) ? ((TooltipDrawable) ((ArrayList) this.labels).get(0)).getIntrinsicHeight() : 0), 1073741824));
    }

    @Override // android.view.View
    public final void onRestoreInstanceState(Parcelable parcelable) {
        SliderState sliderState = (SliderState) parcelable;
        super.onRestoreInstanceState(sliderState.getSuperState());
        this.valueFrom = sliderState.valueFrom;
        this.valueTo = sliderState.valueTo;
        setValuesInternal(sliderState.values);
        this.stepSize = sliderState.stepSize;
        if (sliderState.hasFocus) {
            requestFocus();
        }
    }

    @Override // android.view.View
    public final Parcelable onSaveInstanceState() {
        SliderState sliderState = new SliderState(super.onSaveInstanceState());
        sliderState.valueFrom = this.valueFrom;
        sliderState.valueTo = this.valueTo;
        sliderState.values = new ArrayList(this.values);
        sliderState.stepSize = this.stepSize;
        sliderState.hasFocus = hasFocus();
        return sliderState;
    }

    @Override // android.view.View
    public final void onSizeChanged(int i, int i2, int i3, int i4) {
        this.trackWidth = Math.max(i - (this.trackSidePadding * 2), 0);
        maybeCalculateTicksCoordinates();
        updateHaloHotspot();
    }

    public final void onStartTrackingTouch() {
        ArrayList arrayList = (ArrayList) this.touchListeners;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((Slider.OnSliderTouchListener) obj).onStartTrackingTouch(this);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:8:0x0033, code lost:
    
        if (r2 != 3) goto L64;
     */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean onTouchEvent(android.view.MotionEvent r6) {
        /*
            Method dump skipped, instructions count: 331
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.slider.BaseSlider.onTouchEvent(android.view.MotionEvent):boolean");
    }

    @Override // android.view.View
    public final void onVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
        if (i != 0) {
            ViewGroup contentView = ViewUtils.getContentView(this);
            ViewOverlayApi18 viewOverlayApi18 = contentView == null ? null : new ViewOverlayApi18(contentView);
            if (viewOverlayApi18 == null) {
                return;
            }
            ArrayList arrayList = (ArrayList) this.labels;
            int size = arrayList.size();
            int i2 = 0;
            while (i2 < size) {
                Object obj = arrayList.get(i2);
                i2++;
                viewOverlayApi18.viewOverlay.remove((TooltipDrawable) obj);
            }
        }
    }

    public boolean pickActiveThumb() {
        if (this.activeThumbIdx != -1) {
            return true;
        }
        float f = this.touchPosition;
        if (isRtl()) {
            f = 1.0f - f;
        }
        float f2 = this.valueTo;
        float f3 = this.valueFrom;
        float m$1 = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(f2, f3, f, f3);
        float valueToX = valueToX(m$1);
        this.activeThumbIdx = 0;
        float abs = Math.abs(((Float) this.values.get(0)).floatValue() - m$1);
        for (int i = 1; i < this.values.size(); i++) {
            float abs2 = Math.abs(((Float) this.values.get(i)).floatValue() - m$1);
            float valueToX2 = valueToX(((Float) this.values.get(i)).floatValue());
            if (Float.compare(abs2, abs) > 0) {
                break;
            }
            boolean z = !isRtl() ? valueToX2 - valueToX >= 0.0f : valueToX2 - valueToX <= 0.0f;
            if (Float.compare(abs2, abs) < 0) {
                this.activeThumbIdx = i;
            } else {
                if (Float.compare(abs2, abs) != 0) {
                    continue;
                } else {
                    if (Math.abs(valueToX2 - valueToX) < this.scaledTouchSlop) {
                        this.activeThumbIdx = -1;
                        return false;
                    }
                    if (z) {
                        this.activeThumbIdx = i;
                    }
                }
            }
            abs = abs2;
        }
        return this.activeThumbIdx != -1;
    }

    @Override // android.view.View
    public final void setEnabled(boolean z) {
        super.setEnabled(z);
        setLayerType(z ? 0 : 2, null);
    }

    public final void setHaloTintList(ColorStateList colorStateList) {
        if (colorStateList.equals(this.haloColor)) {
            return;
        }
        this.haloColor = colorStateList;
        Drawable background = getBackground();
        if (!shouldDrawCompatHalo() && (background instanceof RippleDrawable)) {
            ((RippleDrawable) background).setColor(colorStateList);
            return;
        }
        this.haloPaint.setColor(getColorForState(colorStateList));
        this.haloPaint.setAlpha(63);
        invalidate();
    }

    public final void setThumbWidth(int i) {
        if (i == this.thumbWidth) {
            return;
        }
        this.thumbWidth = i;
        MaterialShapeDrawable materialShapeDrawable = this.defaultThumbDrawable;
        RelativeCornerSize relativeCornerSize = ShapeAppearanceModel.PILL;
        ShapeAppearanceModel.Builder builder = new ShapeAppearanceModel.Builder();
        float f = this.thumbWidth / 2.0f;
        CornerTreatment createCornerTreatment = MaterialShapeUtils.createCornerTreatment(0);
        builder.topLeftCorner = createCornerTreatment;
        float compatCornerTreatmentSize = ShapeAppearanceModel.Builder.compatCornerTreatmentSize(createCornerTreatment);
        if (compatCornerTreatmentSize != -1.0f) {
            builder.setTopLeftCornerSize(compatCornerTreatmentSize);
        }
        builder.topRightCorner = createCornerTreatment;
        float compatCornerTreatmentSize2 = ShapeAppearanceModel.Builder.compatCornerTreatmentSize(createCornerTreatment);
        if (compatCornerTreatmentSize2 != -1.0f) {
            builder.setTopRightCornerSize(compatCornerTreatmentSize2);
        }
        builder.bottomRightCorner = createCornerTreatment;
        float compatCornerTreatmentSize3 = ShapeAppearanceModel.Builder.compatCornerTreatmentSize(createCornerTreatment);
        if (compatCornerTreatmentSize3 != -1.0f) {
            builder.setBottomRightCornerSize(compatCornerTreatmentSize3);
        }
        builder.bottomLeftCorner = createCornerTreatment;
        float compatCornerTreatmentSize4 = ShapeAppearanceModel.Builder.compatCornerTreatmentSize(createCornerTreatment);
        if (compatCornerTreatmentSize4 != -1.0f) {
            builder.setBottomLeftCornerSize(compatCornerTreatmentSize4);
        }
        builder.setAllCornerSizes(f);
        materialShapeDrawable.setShapeAppearanceModel(builder.build());
        this.defaultThumbDrawable.setBounds(0, 0, this.thumbWidth, this.thumbHeight);
        Iterator it = this.customThumbDrawablesForValues.iterator();
        while (it.hasNext()) {
            adjustCustomThumbDrawableBounds((Drawable) it.next());
        }
        updateWidgetLayout();
    }

    public final void setTrackActiveTintList(ColorStateList colorStateList) {
        if (colorStateList.equals(this.trackColorActive)) {
            return;
        }
        this.trackColorActive = colorStateList;
        this.activeTrackPaint.setColor(getColorForState(colorStateList));
        this.stopIndicatorPaint.setColor(getColorForState(this.trackColorActive));
        invalidate();
    }

    public final void setTrackInactiveTintList(ColorStateList colorStateList) {
        if (colorStateList.equals(this.trackColorInactive)) {
            return;
        }
        this.trackColorInactive = colorStateList;
        this.inactiveTrackPaint.setColor(getColorForState(colorStateList));
        invalidate();
    }

    public final void setValueForLabel(TooltipDrawable tooltipDrawable, float f) {
        String format = String.format(((float) ((int) f)) == f ? "%.0f" : "%.2f", Float.valueOf(f));
        if (!TextUtils.equals(tooltipDrawable.text, format)) {
            tooltipDrawable.text = format;
            tooltipDrawable.textDrawableHelper.textSizeDirty = true;
            tooltipDrawable.invalidateSelf();
        }
        int normalizeValue = (this.trackSidePadding + ((int) (normalizeValue(f) * this.trackWidth))) - (tooltipDrawable.getIntrinsicWidth() / 2);
        int calculateTrackCenter = calculateTrackCenter() - ((this.thumbHeight / 2) + this.labelPadding);
        tooltipDrawable.setBounds(normalizeValue, calculateTrackCenter - tooltipDrawable.getIntrinsicHeight(), tooltipDrawable.getIntrinsicWidth() + normalizeValue, calculateTrackCenter);
        Rect rect = new Rect(tooltipDrawable.getBounds());
        DescendantOffsetUtils.offsetDescendantRect(ViewUtils.getContentView(this), this, rect);
        tooltipDrawable.setBounds(rect);
        ViewGroup contentView = ViewUtils.getContentView(this);
        (contentView == null ? null : new ViewOverlayApi18(contentView)).viewOverlay.add(tooltipDrawable);
    }

    public final void setValues(Float... fArr) {
        ArrayList arrayList = new ArrayList();
        Collections.addAll(arrayList, fArr);
        setValuesInternal(arrayList);
    }

    public final void setValuesInternal(ArrayList arrayList) {
        ViewGroup contentView;
        if (arrayList.isEmpty()) {
            throw new IllegalArgumentException("At least one value must be set");
        }
        Collections.sort(arrayList);
        if (this.values.size() == arrayList.size() && this.values.equals(arrayList)) {
            return;
        }
        this.values = arrayList;
        this.dirtyConfig = true;
        this.focusedThumbIdx = 0;
        updateHaloHotspot();
        if (((ArrayList) this.labels).size() > this.values.size()) {
            List<TooltipDrawable> subList = ((ArrayList) this.labels).subList(this.values.size(), ((ArrayList) this.labels).size());
            for (TooltipDrawable tooltipDrawable : subList) {
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                if (isAttachedToWindow()) {
                    ViewGroup contentView2 = ViewUtils.getContentView(this);
                    ViewOverlayApi18 viewOverlayApi18 = contentView2 == null ? null : new ViewOverlayApi18(contentView2);
                    if (viewOverlayApi18 != null) {
                        viewOverlayApi18.viewOverlay.remove(tooltipDrawable);
                        ViewGroup contentView3 = ViewUtils.getContentView(this);
                        if (contentView3 == null) {
                            tooltipDrawable.getClass();
                        } else {
                            contentView3.removeOnLayoutChangeListener(tooltipDrawable.attachedViewLayoutChangeListener);
                        }
                    }
                }
            }
            subList.clear();
        }
        while (((ArrayList) this.labels).size() < this.values.size()) {
            TooltipDrawable createFromAttributes = TooltipDrawable.createFromAttributes(this.labelStyle, getContext());
            ((ArrayList) this.labels).add(createFromAttributes);
            WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
            if (isAttachedToWindow() && (contentView = ViewUtils.getContentView(this)) != null) {
                int[] iArr = new int[2];
                contentView.getLocationOnScreen(iArr);
                createFromAttributes.locationOnScreenX = iArr[0];
                contentView.getWindowVisibleDisplayFrame(createFromAttributes.displayFrame);
                contentView.addOnLayoutChangeListener(createFromAttributes.attachedViewLayoutChangeListener);
            }
        }
        int i = ((ArrayList) this.labels).size() == 1 ? 0 : 1;
        ArrayList arrayList2 = (ArrayList) this.labels;
        int size = arrayList2.size();
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList2.get(i2);
            i2++;
            TooltipDrawable tooltipDrawable2 = (TooltipDrawable) obj;
            tooltipDrawable2.drawableState.strokeWidth = i;
            tooltipDrawable2.invalidateSelf();
        }
        ArrayList arrayList3 = (ArrayList) this.changeListeners;
        int size2 = arrayList3.size();
        int i3 = 0;
        while (i3 < size2) {
            Object obj2 = arrayList3.get(i3);
            i3++;
            Slider.OnChangeListener onChangeListener = (Slider.OnChangeListener) obj2;
            ArrayList arrayList4 = this.values;
            int size3 = arrayList4.size();
            int i4 = 0;
            while (i4 < size3) {
                Object obj3 = arrayList4.get(i4);
                i4++;
                onChangeListener.onValueChange(this, ((Float) obj3).floatValue(), false);
            }
        }
        postInvalidate();
    }

    public final boolean shouldDrawCompatHalo() {
        return this.forceDrawCompatHalo || !(getBackground() instanceof RippleDrawable);
    }

    public final boolean snapThumbToValue(float f, int i) {
        this.focusedThumbIdx = i;
        int i2 = 0;
        if (Math.abs(f - ((Float) this.values.get(i)).floatValue()) < 1.0E-4d) {
            return false;
        }
        float f2 = isRtl() ? -0.0f : 0.0f;
        int i3 = i + 1;
        int i4 = i - 1;
        this.values.set(i, Float.valueOf(MathUtils.clamp(f, i4 < 0 ? this.valueFrom : f2 + ((Float) this.values.get(i4)).floatValue(), i3 >= this.values.size() ? this.valueTo : ((Float) this.values.get(i3)).floatValue() - f2)));
        ArrayList arrayList = (ArrayList) this.changeListeners;
        int size = arrayList.size();
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            ((Slider.OnChangeListener) obj).onValueChange(this, ((Float) this.values.get(i)).floatValue(), true);
        }
        AccessibilityManager accessibilityManager = this.accessibilityManager;
        if (accessibilityManager != null && accessibilityManager.isEnabled()) {
            Runnable runnable = this.accessibilityEventSender;
            if (runnable == null) {
                this.accessibilityEventSender = new AccessibilityEventSender();
            } else {
                removeCallbacks(runnable);
            }
            AccessibilityEventSender accessibilityEventSender = this.accessibilityEventSender;
            accessibilityEventSender.virtualViewId = i;
            postDelayed(accessibilityEventSender, 200L);
        }
        return true;
    }

    public final void snapTouchPosition() {
        double d;
        float f = this.touchPosition;
        float f2 = this.stepSize;
        if (f2 > 0.0f) {
            d = Math.round(f * r1) / ((int) ((this.valueTo - this.valueFrom) / f2));
        } else {
            d = f;
        }
        if (isRtl()) {
            d = 1.0d - d;
        }
        float f3 = this.valueTo;
        snapThumbToValue((float) ((d * (f3 - r1)) + this.valueFrom), this.activeThumbIdx);
    }

    public final void updateBoundsForVirtualViewId(int i, Rect rect) {
        int normalizeValue = this.trackSidePadding + ((int) (normalizeValue(((Float) ((ArrayList) getValues()).get(i)).floatValue()) * this.trackWidth));
        int calculateTrackCenter = calculateTrackCenter();
        int max = Math.max(this.thumbWidth / 2, this.minTouchTargetSize / 2);
        int max2 = Math.max(this.thumbHeight / 2, this.minTouchTargetSize / 2);
        rect.set(normalizeValue - max, calculateTrackCenter - max2, normalizeValue + max, calculateTrackCenter + max2);
    }

    public final void updateHaloHotspot() {
        if (shouldDrawCompatHalo() || getMeasuredWidth() <= 0) {
            return;
        }
        Drawable background = getBackground();
        if (background instanceof RippleDrawable) {
            int normalizeValue = (int) ((normalizeValue(((Float) this.values.get(this.focusedThumbIdx)).floatValue()) * this.trackWidth) + this.trackSidePadding);
            int calculateTrackCenter = calculateTrackCenter();
            int i = this.haloRadius;
            background.setHotspotBounds(normalizeValue - i, calculateTrackCenter - i, normalizeValue + i, calculateTrackCenter + i);
        }
    }

    public final void updateLabels() {
        int i = this.labelBehavior;
        if (i == 0 || i == 1) {
            if (this.activeThumbIdx == -1 || !isEnabled()) {
                ensureLabelsRemoved();
                return;
            } else {
                ensureLabelsAdded();
                return;
            }
        }
        if (i == 2) {
            ensureLabelsRemoved();
            return;
        }
        if (i != 3) {
            throw new IllegalArgumentException("Unexpected labelBehavior: " + this.labelBehavior);
        }
        if (isEnabled()) {
            Rect rect = new Rect();
            ViewUtils.getContentView(this).getHitRect(rect);
            if (getLocalVisibleRect(rect)) {
                ensureLabelsAdded();
                return;
            }
        }
        ensureLabelsRemoved();
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x003e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateTrack(android.graphics.Canvas r9, android.graphics.Paint r10, android.graphics.RectF r11, com.google.android.material.slider.BaseSlider.FullCornerDirection r12) {
        /*
            r8 = this;
            r0 = 3
            r1 = 1
            r2 = 2
            int r3 = r8.trackHeight
            float r4 = (float) r3
            r5 = 1073741824(0x40000000, float:2.0)
            float r4 = r4 / r5
            float r3 = (float) r3
            float r3 = r3 / r5
            int r6 = r12.ordinal()
            if (r6 == r1) goto L1f
            if (r6 == r2) goto L1b
            if (r6 == r0) goto L16
            goto L22
        L16:
            int r3 = r8.trackInsideCornerSize
            float r4 = (float) r3
        L19:
            float r3 = (float) r3
            goto L22
        L1b:
            int r4 = r8.trackInsideCornerSize
            float r4 = (float) r4
            goto L22
        L1f:
            int r3 = r8.trackInsideCornerSize
            goto L19
        L22:
            android.graphics.Paint$Style r6 = android.graphics.Paint.Style.FILL
            r10.setStyle(r6)
            android.graphics.Paint$Cap r6 = android.graphics.Paint.Cap.BUTT
            r10.setStrokeCap(r6)
            r10.setAntiAlias(r1)
            android.graphics.Path r6 = r8.trackPath
            r6.reset()
            float r6 = r11.width()
            float r7 = r4 + r3
            int r6 = (r6 > r7 ? 1 : (r6 == r7 ? 0 : -1))
            if (r6 < 0) goto L64
            android.graphics.Path r12 = r8.trackPath
            r5 = 8
            float[] r5 = new float[r5]
            r6 = 0
            r5[r6] = r4
            r5[r1] = r4
            r5[r2] = r3
            r5[r0] = r3
            r0 = 4
            r5[r0] = r3
            r0 = 5
            r5[r0] = r3
            r0 = 6
            r5[r0] = r4
            r0 = 7
            r5[r0] = r4
            android.graphics.Path$Direction r0 = android.graphics.Path.Direction.CW
            r12.addRoundRect(r11, r5, r0)
            android.graphics.Path r8 = r8.trackPath
            r9.drawPath(r8, r10)
            return
        L64:
            float r0 = java.lang.Math.min(r4, r3)
            float r3 = java.lang.Math.max(r4, r3)
            r9.save()
            android.graphics.Path r4 = r8.trackPath
            android.graphics.Path$Direction r6 = android.graphics.Path.Direction.CW
            r4.addRoundRect(r11, r0, r0, r6)
            android.graphics.Path r0 = r8.trackPath
            r9.clipPath(r0)
            int r12 = r12.ordinal()
            if (r12 == r1) goto La6
            if (r12 == r2) goto L97
            android.graphics.RectF r12 = r8.cornerRect
            float r0 = r11.centerX()
            float r0 = r0 - r3
            float r1 = r11.top
            float r2 = r11.centerX()
            float r2 = r2 + r3
            float r11 = r11.bottom
            r12.set(r0, r1, r2, r11)
            goto Lb3
        L97:
            android.graphics.RectF r12 = r8.cornerRect
            float r0 = r11.right
            float r5 = r5 * r3
            float r1 = r0 - r5
            float r2 = r11.top
            float r11 = r11.bottom
            r12.set(r1, r2, r0, r11)
            goto Lb3
        La6:
            android.graphics.RectF r12 = r8.cornerRect
            float r0 = r11.left
            float r1 = r11.top
            float r5 = r5 * r3
            float r5 = r5 + r0
            float r11 = r11.bottom
            r12.set(r0, r1, r5, r11)
        Lb3:
            android.graphics.RectF r8 = r8.cornerRect
            r9.drawRoundRect(r8, r3, r3, r10)
            r9.restore()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.slider.BaseSlider.updateTrack(android.graphics.Canvas, android.graphics.Paint, android.graphics.RectF, com.google.android.material.slider.BaseSlider$FullCornerDirection):void");
    }

    public final void updateWidgetLayout() {
        boolean z;
        int max = Math.max(this.minWidgetHeight, Math.max(this.trackHeight + getPaddingBottom() + getPaddingTop(), getPaddingBottom() + getPaddingTop() + this.thumbHeight));
        boolean z2 = false;
        if (max == this.widgetHeight) {
            z = false;
        } else {
            this.widgetHeight = max;
            z = true;
        }
        int max2 = Math.max(Math.max(Math.max((this.thumbWidth / 2) - this.defaultThumbRadius, 0), Math.max((this.trackHeight - this.defaultTrackHeight) / 2, 0)), Math.max(Math.max(this.tickActiveRadius - this.defaultTickActiveRadius, 0), Math.max(this.tickInactiveRadius - this.defaultTickInactiveRadius, 0))) + this.minTrackSidePadding;
        if (this.trackSidePadding != max2) {
            this.trackSidePadding = max2;
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            if (isLaidOut()) {
                this.trackWidth = Math.max(getWidth() - (this.trackSidePadding * 2), 0);
                maybeCalculateTicksCoordinates();
            }
            z2 = true;
        }
        if (z) {
            requestLayout();
        } else if (z2) {
            postInvalidate();
        }
    }

    public final void validateConfigurationIfDirty() {
        if (this.dirtyConfig) {
            float f = this.valueFrom;
            float f2 = this.valueTo;
            if (f >= f2) {
                throw new IllegalStateException("valueFrom(" + this.valueFrom + ") must be smaller than valueTo(" + this.valueTo + ")");
            }
            if (f2 <= f) {
                throw new IllegalStateException("valueTo(" + this.valueTo + ") must be greater than valueFrom(" + this.valueFrom + ")");
            }
            if (this.stepSize > 0.0f && !valueLandsOnTick(f2)) {
                throw new IllegalStateException(DpCornerSize$$ExternalSyntheticOutline0.m(this.valueTo, ") range", CubicBezierEasing$$ExternalSyntheticOutline0.m("The stepSize(", this.stepSize, ") must be 0, or a factor of the valueFrom(", this.valueFrom, ")-valueTo(")));
            }
            ArrayList arrayList = this.values;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                Float f3 = (Float) obj;
                if (f3.floatValue() < this.valueFrom || f3.floatValue() > this.valueTo) {
                    float f4 = this.valueFrom;
                    float f5 = this.valueTo;
                    StringBuilder sb = new StringBuilder("Slider value(");
                    sb.append(f3);
                    sb.append(") must be greater or equal to valueFrom(");
                    sb.append(f4);
                    sb.append("), and lower or equal to valueTo(");
                    throw new IllegalStateException(DpCornerSize$$ExternalSyntheticOutline0.m(f5, ")", sb));
                }
                if (this.stepSize > 0.0f && !valueLandsOnTick(f3.floatValue())) {
                    float f6 = this.valueFrom;
                    float f7 = this.stepSize;
                    throw new IllegalStateException("Value(" + f3 + ") must be equal to valueFrom(" + f6 + ") plus a multiple of stepSize(" + f7 + ") when using stepSize(" + f7 + ")");
                }
            }
            float f8 = this.stepSize;
            if (f8 != 0.0f) {
                if (((int) f8) != f8) {
                    Log.w("BaseSlider", "Floating point value used for stepSize(" + f8 + "). Using floats can have rounding errors which may result in incorrect values. Instead, consider using integers with a custom LabelFormatter to display the value correctly.");
                }
                float f9 = this.valueFrom;
                if (((int) f9) != f9) {
                    Log.w("BaseSlider", "Floating point value used for valueFrom(" + f9 + "). Using floats can have rounding errors which may result in incorrect values. Instead, consider using integers with a custom LabelFormatter to display the value correctly.");
                }
                float f10 = this.valueTo;
                if (((int) f10) != f10) {
                    Log.w("BaseSlider", "Floating point value used for valueTo(" + f10 + "). Using floats can have rounding errors which may result in incorrect values. Instead, consider using integers with a custom LabelFormatter to display the value correctly.");
                }
            }
            this.dirtyConfig = false;
        }
    }

    public final boolean valueLandsOnTick(float f) {
        BigDecimal bigDecimal = new BigDecimal(Float.toString(f));
        BigDecimal bigDecimal2 = new BigDecimal(Float.toString(this.valueFrom));
        MathContext mathContext = MathContext.DECIMAL64;
        double doubleValue = new BigDecimal(Double.toString(bigDecimal.subtract(bigDecimal2, mathContext).doubleValue())).divide(new BigDecimal(Float.toString(this.stepSize)), mathContext).doubleValue();
        return Math.abs(((double) Math.round(doubleValue)) - doubleValue) < 1.0E-4d;
    }

    public final float valueToX(float f) {
        return (normalizeValue(f) * this.trackWidth) + this.trackSidePadding;
    }

    public BaseSlider(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.sliderStyle);
    }

    /* JADX WARN: Type inference failed for: r1v9, types: [com.google.android.material.slider.BaseSlider$$ExternalSyntheticLambda0] */
    public BaseSlider(Context context, AttributeSet attributeSet, int i) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, i, R.style.Widget_MaterialComponents_Slider), attributeSet, i);
        this.labels = new ArrayList();
        this.changeListeners = new ArrayList();
        this.touchListeners = new ArrayList();
        this.labelsAreAnimatedIn = false;
        this.defaultThumbWidth = -1;
        this.defaultThumbTrackGapSize = -1;
        this.thumbIsPressed = false;
        this.values = new ArrayList();
        this.activeThumbIdx = -1;
        this.focusedThumbIdx = -1;
        this.stepSize = 0.0f;
        this.tickVisible = true;
        this.isLongPress = false;
        this.trackPath = new Path();
        this.trackRect = new RectF();
        this.cornerRect = new RectF();
        MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable();
        this.defaultThumbDrawable = materialShapeDrawable;
        List list = Collections.EMPTY_LIST;
        this.customThumbDrawablesForValues = list;
        this.onScrollChangedListener = new ViewTreeObserver.OnScrollChangedListener() { // from class: com.google.android.material.slider.BaseSlider$$ExternalSyntheticLambda0
            @Override // android.view.ViewTreeObserver.OnScrollChangedListener
            public final void onScrollChanged() {
                BaseSlider baseSlider = BaseSlider.this;
                int i2 = BaseSlider.$r8$clinit;
                baseSlider.updateLabels();
            }
        };
        Context context2 = getContext();
        this.inactiveTrackPaint = new Paint();
        this.activeTrackPaint = new Paint();
        Paint paint = new Paint(1);
        this.thumbPaint = paint;
        Paint.Style style = Paint.Style.FILL;
        paint.setStyle(style);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        Paint paint2 = new Paint(1);
        this.haloPaint = paint2;
        paint2.setStyle(style);
        Paint paint3 = new Paint();
        this.inactiveTicksPaint = paint3;
        Paint.Style style2 = Paint.Style.STROKE;
        paint3.setStyle(style2);
        Paint.Cap cap = Paint.Cap.ROUND;
        paint3.setStrokeCap(cap);
        Paint paint4 = new Paint();
        this.activeTicksPaint = paint4;
        paint4.setStyle(style2);
        paint4.setStrokeCap(cap);
        Paint paint5 = new Paint();
        this.stopIndicatorPaint = paint5;
        paint5.setStyle(style);
        paint5.setStrokeCap(cap);
        Resources resources = context2.getResources();
        this.minWidgetHeight = resources.getDimensionPixelSize(R.dimen.mtrl_slider_widget_height);
        int dimensionPixelOffset = resources.getDimensionPixelOffset(R.dimen.mtrl_slider_track_side_padding);
        this.minTrackSidePadding = dimensionPixelOffset;
        this.trackSidePadding = dimensionPixelOffset;
        this.defaultThumbRadius = resources.getDimensionPixelSize(R.dimen.mtrl_slider_thumb_radius);
        this.defaultTrackHeight = resources.getDimensionPixelSize(R.dimen.mtrl_slider_track_height);
        this.defaultTickActiveRadius = resources.getDimensionPixelSize(R.dimen.mtrl_slider_tick_radius);
        this.defaultTickInactiveRadius = resources.getDimensionPixelSize(R.dimen.mtrl_slider_tick_radius);
        this.minTickSpacing = resources.getDimensionPixelSize(R.dimen.mtrl_slider_tick_min_spacing);
        this.labelPadding = resources.getDimensionPixelSize(R.dimen.mtrl_slider_label_padding);
        int[] iArr = R$styleable.Slider;
        ThemeEnforcement.checkCompatibleTheme(context2, attributeSet, i, R.style.Widget_MaterialComponents_Slider);
        ThemeEnforcement.checkTextAppearance(context2, attributeSet, iArr, i, R.style.Widget_MaterialComponents_Slider, new int[0]);
        TypedArray obtainStyledAttributes = context2.obtainStyledAttributes(attributeSet, iArr, i, R.style.Widget_MaterialComponents_Slider);
        this.labelStyle = obtainStyledAttributes.getResourceId(8, R.style.Widget_MaterialComponents_Tooltip);
        this.valueFrom = obtainStyledAttributes.getFloat(3, 0.0f);
        this.valueTo = obtainStyledAttributes.getFloat(4, 1.0f);
        setValues(Float.valueOf(this.valueFrom));
        this.stepSize = obtainStyledAttributes.getFloat(2, 0.0f);
        this.minTouchTargetSize = (int) Math.ceil(obtainStyledAttributes.getDimension(9, (float) Math.ceil(ViewUtils.dpToPx(48, getContext()))));
        boolean hasValue = obtainStyledAttributes.hasValue(24);
        int i2 = hasValue ? 24 : 26;
        int i3 = hasValue ? 24 : 25;
        ColorStateList colorStateList = MaterialResources.getColorStateList(context2, obtainStyledAttributes, i2);
        setTrackInactiveTintList(colorStateList == null ? ResourcesCompat.getColorStateList(R.color.material_slider_inactive_track_color, context2.getTheme(), context2.getResources()) : colorStateList);
        ColorStateList colorStateList2 = MaterialResources.getColorStateList(context2, obtainStyledAttributes, i3);
        setTrackActiveTintList(colorStateList2 == null ? ResourcesCompat.getColorStateList(R.color.material_slider_active_track_color, context2.getTheme(), context2.getResources()) : colorStateList2);
        materialShapeDrawable.setFillColor(MaterialResources.getColorStateList(context2, obtainStyledAttributes, 10));
        if (obtainStyledAttributes.hasValue(14)) {
            materialShapeDrawable.setStrokeColor(MaterialResources.getColorStateList(context2, obtainStyledAttributes, 14));
            postInvalidate();
        }
        materialShapeDrawable.drawableState.strokeWidth = obtainStyledAttributes.getDimension(15, 0.0f);
        materialShapeDrawable.invalidateSelf();
        postInvalidate();
        ColorStateList colorStateList3 = MaterialResources.getColorStateList(context2, obtainStyledAttributes, 5);
        setHaloTintList(colorStateList3 == null ? ResourcesCompat.getColorStateList(R.color.material_slider_halo_color, context2.getTheme(), context2.getResources()) : colorStateList3);
        this.tickVisible = obtainStyledAttributes.getBoolean(23, true);
        boolean hasValue2 = obtainStyledAttributes.hasValue(18);
        int i4 = hasValue2 ? 18 : 20;
        int i5 = hasValue2 ? 18 : 19;
        ColorStateList colorStateList4 = MaterialResources.getColorStateList(context2, obtainStyledAttributes, i4);
        colorStateList4 = colorStateList4 == null ? ResourcesCompat.getColorStateList(R.color.material_slider_inactive_tick_marks_color, context2.getTheme(), context2.getResources()) : colorStateList4;
        if (!colorStateList4.equals(this.tickColorInactive)) {
            this.tickColorInactive = colorStateList4;
            paint3.setColor(getColorForState(colorStateList4));
            invalidate();
        }
        ColorStateList colorStateList5 = MaterialResources.getColorStateList(context2, obtainStyledAttributes, i5);
        colorStateList5 = colorStateList5 == null ? ResourcesCompat.getColorStateList(R.color.material_slider_active_tick_marks_color, context2.getTheme(), context2.getResources()) : colorStateList5;
        if (!colorStateList5.equals(this.tickColorActive)) {
            this.tickColorActive = colorStateList5;
            paint4.setColor(getColorForState(colorStateList5));
            invalidate();
        }
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(16, 0);
        if (this.thumbTrackGapSize != dimensionPixelSize) {
            this.thumbTrackGapSize = dimensionPixelSize;
            invalidate();
        }
        int dimensionPixelSize2 = obtainStyledAttributes.getDimensionPixelSize(29, 0);
        if (this.trackStopIndicatorSize != dimensionPixelSize2) {
            this.trackStopIndicatorSize = dimensionPixelSize2;
            paint5.setStrokeWidth(dimensionPixelSize2);
            invalidate();
        }
        int dimensionPixelSize3 = obtainStyledAttributes.getDimensionPixelSize(28, 0);
        if (this.trackInsideCornerSize != dimensionPixelSize3) {
            this.trackInsideCornerSize = dimensionPixelSize3;
            invalidate();
        }
        int dimensionPixelSize4 = obtainStyledAttributes.getDimensionPixelSize(13, 0) * 2;
        int dimensionPixelSize5 = obtainStyledAttributes.getDimensionPixelSize(17, dimensionPixelSize4);
        int dimensionPixelSize6 = obtainStyledAttributes.getDimensionPixelSize(12, dimensionPixelSize4);
        setThumbWidth(dimensionPixelSize5);
        if (dimensionPixelSize6 != this.thumbHeight) {
            this.thumbHeight = dimensionPixelSize6;
            materialShapeDrawable.setBounds(0, 0, this.thumbWidth, dimensionPixelSize6);
            Iterator it = list.iterator();
            while (it.hasNext()) {
                adjustCustomThumbDrawableBounds((Drawable) it.next());
            }
            updateWidgetLayout();
        }
        int dimensionPixelSize7 = obtainStyledAttributes.getDimensionPixelSize(6, 0);
        if (dimensionPixelSize7 != this.haloRadius) {
            this.haloRadius = dimensionPixelSize7;
            Drawable background = getBackground();
            if (!shouldDrawCompatHalo() && (background instanceof RippleDrawable)) {
                ((RippleDrawable) background).setRadius(this.haloRadius);
            } else {
                postInvalidate();
            }
        }
        this.defaultThumbDrawable.setElevation(obtainStyledAttributes.getDimension(11, 0.0f));
        int dimensionPixelSize8 = obtainStyledAttributes.getDimensionPixelSize(27, 0);
        if (this.trackHeight != dimensionPixelSize8) {
            this.trackHeight = dimensionPixelSize8;
            this.inactiveTrackPaint.setStrokeWidth(dimensionPixelSize8);
            this.activeTrackPaint.setStrokeWidth(this.trackHeight);
            updateWidgetLayout();
        }
        int dimensionPixelSize9 = obtainStyledAttributes.getDimensionPixelSize(21, this.trackStopIndicatorSize / 2);
        if (this.tickActiveRadius != dimensionPixelSize9) {
            this.tickActiveRadius = dimensionPixelSize9;
            this.activeTicksPaint.setStrokeWidth(dimensionPixelSize9 * 2);
            updateWidgetLayout();
        }
        int dimensionPixelSize10 = obtainStyledAttributes.getDimensionPixelSize(22, this.trackStopIndicatorSize / 2);
        if (this.tickInactiveRadius != dimensionPixelSize10) {
            this.tickInactiveRadius = dimensionPixelSize10;
            this.inactiveTicksPaint.setStrokeWidth(dimensionPixelSize10 * 2);
            updateWidgetLayout();
        }
        int i6 = obtainStyledAttributes.getInt(7, 0);
        if (this.labelBehavior != i6) {
            this.labelBehavior = i6;
            requestLayout();
        }
        if (!obtainStyledAttributes.getBoolean(0, true)) {
            setEnabled(false);
        }
        obtainStyledAttributes.recycle();
        setFocusable(true);
        setClickable(true);
        this.defaultThumbDrawable.setShadowCompatibilityMode(2);
        this.scaledTouchSlop = ViewConfiguration.get(context2).getScaledTouchSlop();
        AccessibilityHelper accessibilityHelper = new AccessibilityHelper(this);
        this.accessibilityHelper = accessibilityHelper;
        ViewCompat.setAccessibilityDelegate(this, accessibilityHelper);
        this.accessibilityManager = (AccessibilityManager) getContext().getSystemService("accessibility");
    }
}
