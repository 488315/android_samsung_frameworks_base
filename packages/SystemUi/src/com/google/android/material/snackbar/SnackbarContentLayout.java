package com.google.android.material.snackbar;

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
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.lang.reflect.Method;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class SnackbarContentLayout extends LinearLayout implements ContentViewCallback {
    public Button actionView;
    public final InputMethodManager mImm;
    public boolean mIsCoordinatorLayoutParent;
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

    /* JADX WARN: Code restructure failed: missing block: B:77:0x01ed, code lost:
    
        if (updateViewsWithinLayout(1, r0, r0 - r1) != false) goto L88;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x01fa, code lost:
    
        r3 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x01f8, code lost:
    
        if (updateViewsWithinLayout(0, r0, r0) != false) goto L88;
     */
    /* JADX WARN: Removed duplicated region for block: B:36:0x014b  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x017f  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0186  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0128 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // android.widget.LinearLayout, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onMeasure(int i, int i2) {
        boolean z;
        boolean z2;
        boolean z3;
        int dimensionPixelOffset;
        ViewParent parent;
        int measuredWidth;
        int measuredWidth2 = getMeasuredWidth();
        super.onMeasure(i, i2);
        if (this.actionView.getVisibility() == 0) {
            i = View.MeasureSpec.makeMeasureSpec(this.mWidthWtihAction, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
            super.onMeasure(i, i2);
        } else if (getMeasuredWidth() == 0) {
            i = View.MeasureSpec.makeMeasureSpec(measuredWidth2, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
            super.onMeasure(i, i2);
        } else if (this.maxWidth > 0) {
            int measuredWidth3 = getMeasuredWidth();
            int i3 = this.maxWidth;
            if (measuredWidth3 > i3) {
                i = View.MeasureSpec.makeMeasureSpec(i3, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
                super.onMeasure(i, i2);
            }
        }
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.design_snackbar_padding_vertical_2lines);
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.design_snackbar_padding_vertical);
        Layout layout = this.messageView.getLayout();
        boolean z4 = false;
        boolean z5 = layout != null && layout.getLineCount() > 1;
        SnackbarContentLayout snackbarContentLayout = this.mSnackBarContentLayout;
        if (snackbarContentLayout != null) {
            float measuredWidth4 = this.actionView.getMeasuredWidth() + this.messageView.getMeasuredWidth() + this.mSnackBarContentLayout.getPaddingRight() + snackbarContentLayout.getPaddingLeft();
            if (this.maxInlineActionWidth == -1 && this.actionView.getVisibility() == 0) {
                if (measuredWidth4 > this.mWidthWtihAction || z5) {
                    this.mSnackBarContentLayout.setOrientation(1);
                    this.actionView.setPadding(getResources().getDimensionPixelSize(R.dimen.sesl_design_snackbar_action_padding_left), getResources().getDimensionPixelSize(R.dimen.sesl_design_snackbar_action_padding_top), getResources().getDimensionPixelSize(R.dimen.sesl_design_snackbar_action_padding_right), 0);
                } else {
                    this.mSnackBarContentLayout.setOrientation(0);
                    this.actionView.setPadding(getResources().getDimensionPixelSize(R.dimen.sesl_design_snackbar_action_padding_left), 0, getResources().getDimensionPixelSize(R.dimen.sesl_design_snackbar_action_padding_right), 0);
                }
                z = true;
            } else {
                z = false;
            }
            int rotation = this.mWindowManager.getDefaultDisplay().getRotation();
            boolean z6 = rotation == 1 || rotation == 3;
            if (this.mImm == null || !z6) {
                int i4 = (int) measuredWidth4;
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mSnackBarContentLayout.getLayoutParams();
                ViewParent parent2 = this.mSnackBarContentLayout.getParent();
                if (this.mIsCoordinatorLayoutParent && (parent2 instanceof ViewGroup)) {
                    ViewGroup viewGroup = (ViewGroup) parent2;
                    int measuredWidth5 = ((viewGroup.getMeasuredWidth() - Math.min(this.mWidthWtihAction, i4)) - viewGroup.getPaddingLeft()) - viewGroup.getPaddingRight();
                    if (measuredWidth5 > 0) {
                        int i5 = measuredWidth5 / 2;
                        marginLayoutParams.rightMargin = i5;
                        marginLayoutParams.leftMargin = i5;
                    } else {
                        marginLayoutParams.rightMargin = 0;
                        marginLayoutParams.leftMargin = 0;
                    }
                    this.mSnackBarContentLayout.setLayoutParams(marginLayoutParams);
                    z4 = true;
                }
                z2 = z | z4;
            } else {
                int i6 = (int) measuredWidth4;
                ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) this.mSnackBarContentLayout.getLayoutParams();
                InputMethodManager inputMethodManager = this.mImm;
                Method method = SeslBaseReflector.getMethod(SeslInputMethodManagerReflector.mClass, "semIsInputMethodShown", new Class[0]);
                if (method != null) {
                    Object invoke = SeslBaseReflector.invoke(inputMethodManager, method, new Object[0]);
                    if (invoke instanceof Boolean) {
                        z3 = ((Boolean) invoke).booleanValue();
                        if (z3) {
                            marginLayoutParams2.bottomMargin = getResources().getDimensionPixelOffset(R.dimen.sesl_design_snackbar_layout_padding_bottom);
                        } else {
                            try {
                                dimensionPixelOffset = this.mWindowManager.getCurrentWindowMetrics().getWindowInsets().getInsets(WindowInsets.Type.navigationBars()).bottom;
                            } catch (Exception unused) {
                                dimensionPixelOffset = getResources().getDimensionPixelOffset(R.dimen.sesl_design_snackbar_layout_sip_padding_bottom);
                            }
                            marginLayoutParams2.bottomMargin = dimensionPixelOffset;
                        }
                        parent = this.mSnackBarContentLayout.getParent();
                        if (this.mIsCoordinatorLayoutParent && (parent instanceof ViewGroup)) {
                            ViewGroup viewGroup2 = (ViewGroup) parent;
                            measuredWidth = ((viewGroup2.getMeasuredWidth() - Math.min(this.mWidthWtihAction, i6)) - viewGroup2.getPaddingLeft()) - viewGroup2.getPaddingRight();
                            if (measuredWidth <= 0) {
                                int i7 = measuredWidth / 2;
                                marginLayoutParams2.rightMargin = i7;
                                marginLayoutParams2.leftMargin = i7;
                            } else {
                                marginLayoutParams2.rightMargin = 0;
                                marginLayoutParams2.leftMargin = 0;
                            }
                        }
                        this.mSnackBarContentLayout.setLayoutParams(marginLayoutParams2);
                        z2 = z | true;
                    }
                }
                z3 = false;
                if (z3) {
                }
                parent = this.mSnackBarContentLayout.getParent();
                if (this.mIsCoordinatorLayoutParent) {
                    ViewGroup viewGroup22 = (ViewGroup) parent;
                    measuredWidth = ((viewGroup22.getMeasuredWidth() - Math.min(this.mWidthWtihAction, i6)) - viewGroup22.getPaddingLeft()) - viewGroup22.getPaddingRight();
                    if (measuredWidth <= 0) {
                    }
                }
                this.mSnackBarContentLayout.setLayoutParams(marginLayoutParams2);
                z2 = z | true;
            }
            z4 = z2;
        } else if (!z5 || this.maxInlineActionWidth <= 0 || this.actionView.getMeasuredWidth() <= this.maxInlineActionWidth) {
            if (!z5) {
                dimensionPixelSize = dimensionPixelSize2;
            }
        }
        if (z4) {
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
        if (ViewCompat.Api17Impl.isPaddingRelative(textView)) {
            ViewCompat.Api17Impl.setPaddingRelative(textView, ViewCompat.Api17Impl.getPaddingStart(textView), i2, ViewCompat.Api17Impl.getPaddingEnd(textView), i3);
            return true;
        }
        textView.setPadding(textView.getPaddingLeft(), i2, textView.getPaddingRight(), i3);
        return true;
    }

    public SnackbarContentLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mIsCoordinatorLayoutParent = false;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SnackbarLayout);
        this.maxWidth = obtainStyledAttributes.getDimensionPixelSize(0, -1);
        this.maxInlineActionWidth = obtainStyledAttributes.getDimensionPixelSize(7, -1);
        obtainStyledAttributes.recycle();
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
                            seslTouchTargetDelegate.addTouchDelegate(SnackbarContentLayout.this.actionView, SeslTouchTargetDelegate.ExtraInsets.m33of(measuredHeight, measuredHeight, measuredHeight, measuredHeight));
                            SnackbarContentLayout.this.mSnackBarContentLayout.setTouchDelegate(seslTouchTargetDelegate);
                        }
                    });
                }
            });
        }
    }
}
