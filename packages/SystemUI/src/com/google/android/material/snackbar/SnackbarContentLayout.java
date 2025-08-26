package com.google.android.material.snackbar;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.view.SeslTouchTargetDelegate;
import androidx.core.view.ViewCompat;
import androidx.reflect.SeslBaseReflector;
import androidx.reflect.view.inputmethod.SeslInputMethodManagerReflector;
import com.android.systemui.R;
import com.google.android.material.R$styleable;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.motion.MotionUtils;
import java.lang.reflect.Method;
import java.util.WeakHashMap;

/* loaded from: classes4.dex */
public class SnackbarContentLayout extends LinearLayout implements ContentViewCallback {
    public Button actionView;
    public final TimeInterpolator contentInterpolator;
    public final InputMethodManager mImm;
    public boolean mIsCoordinatorLayoutParent;
    public boolean mIsSuggestMultiLine;
    public final SnackbarContentLayout mSnackBarContentLayout;
    public int mWidthWtihAction;
    public final WindowManager mWindowManager;
    public final int maxInlineActionWidth;
    public int maxWidth;
    public TextView messageView;

    public SnackbarContentLayout(Context context) {
        this(context, null);
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        Resources resources = getContext().getResources();
        int fraction = (int) resources.getFraction(R.dimen.sesl_config_prefSnackWidth, resources.getDisplayMetrics().widthPixels, resources.getDisplayMetrics().widthPixels);
        this.mWidthWtihAction = fraction;
        this.maxWidth = fraction;
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.messageView = (TextView) findViewById(R.id.snackbar_text);
        this.actionView = (Button) findViewById(R.id.snackbar_action);
    }

    /* JADX WARN: Removed duplicated region for block: B:55:0x016b  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x023d  */
    @Override // android.widget.LinearLayout, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onMeasure(int i, int i2) throws Resources.NotFoundException {
        boolean z;
        int dimensionPixelOffset;
        int measuredWidth = getMeasuredWidth();
        super.onMeasure(i, i2);
        if (this.actionView.getVisibility() == 0 && this.mIsSuggestMultiLine) {
            i = View.MeasureSpec.makeMeasureSpec(this.mWidthWtihAction, 1073741824);
            super.onMeasure(i, i2);
        } else if (getMeasuredWidth() == 0) {
            i = View.MeasureSpec.makeMeasureSpec(measuredWidth, 1073741824);
            super.onMeasure(i, i2);
        } else if (this.maxWidth > 0) {
            int measuredWidth2 = getMeasuredWidth();
            int i3 = this.maxWidth;
            if (measuredWidth2 > i3) {
                i = View.MeasureSpec.makeMeasureSpec(i3, 1073741824);
                super.onMeasure(i, i2);
            }
        }
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.design_snackbar_padding_vertical_2lines);
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.design_snackbar_padding_vertical);
        Layout layout = this.messageView.getLayout();
        boolean z2 = false;
        boolean z3 = layout != null && layout.getLineCount() > 1;
        if (z3) {
            this.mIsSuggestMultiLine = true;
        }
        SnackbarContentLayout snackbarContentLayout = this.mSnackBarContentLayout;
        if (snackbarContentLayout != null) {
            float measuredWidth3 = this.actionView.getMeasuredWidth() + this.messageView.getMeasuredWidth() + this.mSnackBarContentLayout.getPaddingRight() + snackbarContentLayout.getPaddingLeft();
            if (this.maxInlineActionWidth == -1 && this.actionView.getVisibility() == 0) {
                if (measuredWidth3 > this.mWidthWtihAction || z3 || this.mIsSuggestMultiLine) {
                    this.mSnackBarContentLayout.setOrientation(1);
                    this.messageView.setPadding(getResources().getDimensionPixelSize(R.dimen.sesl_design_snackbar_text_padding_left), getResources().getDimensionPixelSize(R.dimen.sesl_design_snackbar_text_padding_top), getResources().getDimensionPixelSize(R.dimen.sesl_design_snackbar_text_padding_right), getResources().getDimensionPixelSize(R.dimen.sesl_design_snackbar_text_padding_bottom));
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.actionView.getLayoutParams();
                    layoutParams.setMargins(0, 0, 0, getResources().getDimensionPixelSize(R.dimen.sesl_design_snackbar_action_margin_bottom));
                    layoutParams.setMarginEnd(getResources().getDimensionPixelSize(R.dimen.sesl_design_snackbar_action_margin_end));
                    this.actionView.setLayoutParams(layoutParams);
                } else {
                    this.mSnackBarContentLayout.setOrientation(0);
                    this.actionView.setPadding(getResources().getDimensionPixelSize(R.dimen.sesl_design_snackbar_action_padding_left), 0, getResources().getDimensionPixelSize(R.dimen.sesl_design_snackbar_action_padding_right), 0);
                }
                z = true;
            } else {
                z = false;
            }
            int rotation = this.mWindowManager.getDefaultDisplay().getRotation();
            boolean z4 = rotation == 1 || rotation == 3;
            if (this.mImm == null || !z4) {
                int i4 = (int) measuredWidth3;
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mSnackBarContentLayout.getLayoutParams();
                ViewParent parent = this.mSnackBarContentLayout.getParent();
                if (this.mIsCoordinatorLayoutParent && (parent instanceof ViewGroup)) {
                    ViewGroup viewGroup = (ViewGroup) parent;
                    int measuredWidth4 = ((viewGroup.getMeasuredWidth() - Math.min(this.mWidthWtihAction, i4)) - viewGroup.getPaddingLeft()) - viewGroup.getPaddingRight();
                    if (measuredWidth4 > 0) {
                        int i5 = measuredWidth4 / 2;
                        marginLayoutParams.rightMargin = i5;
                        marginLayoutParams.leftMargin = i5;
                    } else {
                        marginLayoutParams.rightMargin = 0;
                        marginLayoutParams.leftMargin = 0;
                    }
                    this.mSnackBarContentLayout.setLayoutParams(marginLayoutParams);
                    z2 = true;
                }
                z2 = z | z2;
            } else {
                int i6 = (int) measuredWidth3;
                ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) this.mSnackBarContentLayout.getLayoutParams();
                InputMethodManager inputMethodManager = this.mImm;
                Method method = SeslBaseReflector.getMethod(SeslInputMethodManagerReflector.mClass, "semIsInputMethodShown", new Class[0]);
                if (method != null) {
                    Object objInvoke = SeslBaseReflector.invoke(inputMethodManager, method, new Object[0]);
                    boolean zBooleanValue = objInvoke instanceof Boolean ? ((Boolean) objInvoke).booleanValue() : false;
                    if (zBooleanValue) {
                        try {
                            dimensionPixelOffset = this.mWindowManager.getCurrentWindowMetrics().getWindowInsets().getInsets(WindowInsets.Type.navigationBars()).bottom;
                            if (dimensionPixelOffset == 0) {
                                dimensionPixelOffset = getResources().getDimensionPixelOffset(R.dimen.sesl_design_snackbar_layout_sip_padding_bottom);
                            }
                        } catch (Exception unused) {
                            dimensionPixelOffset = getResources().getDimensionPixelOffset(R.dimen.sesl_design_snackbar_layout_sip_padding_bottom);
                        }
                        marginLayoutParams2.bottomMargin = dimensionPixelOffset;
                    } else {
                        marginLayoutParams2.bottomMargin = getResources().getDimensionPixelOffset(R.dimen.sesl_design_snackbar_layout_padding_bottom);
                    }
                    ViewParent parent2 = this.mSnackBarContentLayout.getParent();
                    if (this.mIsCoordinatorLayoutParent && (parent2 instanceof ViewGroup)) {
                        ViewGroup viewGroup2 = (ViewGroup) parent2;
                        int measuredWidth5 = ((viewGroup2.getMeasuredWidth() - Math.min(this.mWidthWtihAction, i6)) - viewGroup2.getPaddingLeft()) - viewGroup2.getPaddingRight();
                        if (measuredWidth5 > 0) {
                            int i7 = measuredWidth5 / 2;
                            marginLayoutParams2.rightMargin = i7;
                            marginLayoutParams2.leftMargin = i7;
                        } else {
                            marginLayoutParams2.rightMargin = 0;
                            marginLayoutParams2.leftMargin = 0;
                        }
                    }
                    this.mSnackBarContentLayout.setLayoutParams(marginLayoutParams2);
                    z2 = true;
                }
            }
        } else if (!z3 || this.maxInlineActionWidth <= 0 || this.actionView.getMeasuredWidth() <= this.maxInlineActionWidth) {
            if (!z3) {
                dimensionPixelSize = dimensionPixelSize2;
            }
            if (updateViewsWithinLayout(0, dimensionPixelSize, dimensionPixelSize)) {
                z2 = true;
            }
        } else if (updateViewsWithinLayout(1, dimensionPixelSize, dimensionPixelSize - dimensionPixelSize2)) {
        }
        if (z2) {
            super.onMeasure(i, i2);
        }
    }

    public final boolean updateViewsWithinLayout(int i, int i2, int i3) {
        boolean z;
        if (i != getOrientation()) {
            setOrientation(i);
            z = true;
        } else {
            z = false;
        }
        if (this.messageView.getPaddingTop() == i2 && this.messageView.getPaddingBottom() == i3) {
            return z;
        }
        TextView textView = this.messageView;
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        if (textView.isPaddingRelative()) {
            textView.setPaddingRelative(textView.getPaddingStart(), i2, textView.getPaddingEnd(), i3);
            return true;
        }
        textView.setPadding(textView.getPaddingLeft(), i2, textView.getPaddingRight(), i3);
        return true;
    }

    public SnackbarContentLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mIsCoordinatorLayoutParent = false;
        this.mIsSuggestMultiLine = false;
        this.contentInterpolator = MotionUtils.resolveThemeInterpolator(context, R.attr.motionEasingEmphasizedInterpolator, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SnackbarLayout);
        this.maxWidth = typedArrayObtainStyledAttributes.getDimensionPixelSize(0, -1);
        this.maxInlineActionWidth = typedArrayObtainStyledAttributes.getDimensionPixelSize(7, -1);
        typedArrayObtainStyledAttributes.recycle();
        Resources resources = context.getResources();
        int fraction = (int) resources.getFraction(R.dimen.sesl_config_prefSnackWidth, resources.getDisplayMetrics().widthPixels, resources.getDisplayMetrics().widthPixels);
        this.mWidthWtihAction = fraction;
        this.maxWidth = fraction;
        this.mSnackBarContentLayout = (SnackbarContentLayout) findViewById(R.id.snackbar_content_layout);
        this.mImm = (InputMethodManager) context.getSystemService(InputMethodManager.class);
        this.mWindowManager = (WindowManager) context.getSystemService("window");
        ViewTreeObserver viewTreeObserver = getViewTreeObserver();
        if (viewTreeObserver != null) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.google.android.material.snackbar.SnackbarContentLayout.1
                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                public final void onGlobalLayout() {
                    Button button;
                    SnackbarContentLayout.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    SnackbarContentLayout snackbarContentLayout = SnackbarContentLayout.this;
                    if (snackbarContentLayout.mSnackBarContentLayout == null || (button = snackbarContentLayout.actionView) == null || button.getVisibility() != 0) {
                        return;
                    }
                    SnackbarContentLayout.this.mSnackBarContentLayout.post(new Runnable() { // from class: com.google.android.material.snackbar.SnackbarContentLayout.1.1
                        @Override // java.lang.Runnable
                        public final void run() {
                            SeslTouchTargetDelegate seslTouchTargetDelegate = new SeslTouchTargetDelegate(SnackbarContentLayout.this.mSnackBarContentLayout);
                            int measuredHeight = SnackbarContentLayout.this.actionView.getMeasuredHeight() / 2;
                            seslTouchTargetDelegate.addTouchDelegate(SnackbarContentLayout.this.actionView, SeslTouchTargetDelegate.ExtraInsets.of(measuredHeight, measuredHeight, measuredHeight, measuredHeight));
                            SnackbarContentLayout.this.mSnackBarContentLayout.setTouchDelegate(seslTouchTargetDelegate);
                        }
                    });
                }
            });
        }
    }
}
