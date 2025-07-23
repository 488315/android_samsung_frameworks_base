package com.google.android.material.snackbar;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.TypedArray;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.FrameLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.reflect.widget.SeslTextViewReflector;
import com.android.systemui.R;
import com.android.systemui.util.SettingsHelper;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.SnackbarManager;
import com.google.android.material.util.MaxFontScaleRatio;
import com.google.android.material.util.SeslTextViewHelperKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class Snackbar extends BaseTransientBottomBar {
    public static final int[] SNACKBAR_CONTENT_STYLE_ATTRS = {R.attr.snackbarButtonStyle, R.attr.snackbarTextViewStyle};
    public static boolean mIsCoordinatorLayoutParent = false;
    public final AccessibilityManager accessibilityManager;
    public boolean hasAction;
    public int mType;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Callback extends BaseTransientBottomBar.BaseCallback {
        @Override // com.google.android.material.snackbar.BaseTransientBottomBar.BaseCallback
        public /* bridge */ /* synthetic */ void onDismissed(BaseTransientBottomBar baseTransientBottomBar, int i) {
            onDismissed();
        }

        @Override // com.google.android.material.snackbar.BaseTransientBottomBar.BaseCallback
        public final /* bridge */ /* synthetic */ void onShown(BaseTransientBottomBar baseTransientBottomBar) {
        }

        public void onDismissed() {
        }
    }

    private Snackbar(Context context, ViewGroup viewGroup, View view, ContentViewCallback contentViewCallback) {
        super(context, viewGroup, view, contentViewCallback);
        this.mType = -1;
        this.accessibilityManager = (AccessibilityManager) viewGroup.getContext().getSystemService("accessibility");
    }

    public static Snackbar makeInternal(Context context, View view, CharSequence charSequence, int i, int i2) {
        ViewGroup viewGroup;
        int i3;
        mIsCoordinatorLayoutParent = false;
        ViewGroup viewGroup2 = null;
        while (true) {
            if (view instanceof CoordinatorLayout) {
                mIsCoordinatorLayoutParent = true;
                viewGroup = (ViewGroup) view;
                break;
            }
            if (view instanceof FrameLayout) {
                if (view.getId() == 16908290) {
                    viewGroup = (ViewGroup) view;
                    break;
                }
                viewGroup2 = (ViewGroup) view;
            }
            if (view != null) {
                Object parent = view.getParent();
                view = parent instanceof View ? (View) parent : null;
            }
            if (view == null) {
                viewGroup = viewGroup2;
                break;
            }
        }
        if (viewGroup == null) {
            throw new IllegalArgumentException("No suitable parent found from the given view. Please provide a valid view.");
        }
        if (context == null) {
            context = viewGroup.getContext();
        }
        LayoutInflater from = LayoutInflater.from(context);
        if (i2 == 0) {
            i3 = R.layout.sesl_layout_snackbar_suggest_include;
        } else {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(SNACKBAR_CONTENT_STYLE_ATTRS);
            int resourceId = obtainStyledAttributes.getResourceId(0, -1);
            int resourceId2 = obtainStyledAttributes.getResourceId(1, -1);
            obtainStyledAttributes.recycle();
            i3 = (resourceId == -1 || resourceId2 == -1) ? R.layout.design_layout_snackbar_include : R.layout.mtrl_layout_snackbar_include;
        }
        SnackbarContentLayout snackbarContentLayout = (SnackbarContentLayout) from.inflate(i3, viewGroup, false);
        snackbarContentLayout.mIsCoordinatorLayoutParent = mIsCoordinatorLayoutParent;
        Snackbar snackbar = new Snackbar(context, viewGroup, snackbarContentLayout, snackbarContentLayout);
        snackbar.mType = i2;
        snackbar.getContentLayout().messageView.setText(charSequence);
        SeslTextViewHelperKt.checkMaxFontScale(snackbar.getContentLayout().messageView, snackbar.mType == 0 ? R.dimen.sesl_design_snackbar_suggest_text_size : R.dimen.design_snackbar_text_size, MaxFontScaleRatio.LARGE);
        snackbar.duration = i;
        if (i2 == 0) {
            snackbar.view.animationMode = 2;
        }
        return snackbar;
    }

    @Override // com.google.android.material.snackbar.BaseTransientBottomBar
    public final void dismiss() {
        dispatchDismiss(3);
    }

    public final SnackbarContentLayout getContentLayout() {
        return (SnackbarContentLayout) this.view.getChildAt(0);
    }

    public final void setAction(CharSequence charSequence, final View.OnClickListener onClickListener) {
        Button button = getContentLayout().actionView;
        getContentLayout().setBackground(this.view.getResources().getDrawable(this.mType == 0 ? R.drawable.sesl_snackbar_suggest_action_frame_mtrl : R.drawable.sem_snackbar_action_frame_mtrl));
        boolean z = false;
        if (TextUtils.isEmpty(charSequence)) {
            button.setVisibility(8);
            button.setOnClickListener(null);
            this.hasAction = false;
            return;
        }
        this.hasAction = true;
        if (this.mType != 0) {
            button.setVisibility(0);
        }
        button.setText(charSequence);
        button.setOnClickListener(new View.OnClickListener() { // from class: com.google.android.material.snackbar.Snackbar$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Snackbar snackbar = Snackbar.this;
                View.OnClickListener onClickListener2 = onClickListener;
                int[] iArr = Snackbar.SNACKBAR_CONTENT_STYLE_ATTRS;
                snackbar.getClass();
                onClickListener2.onClick(view);
                snackbar.dispatchDismiss(1);
            }
        });
        SeslTextViewHelperKt.checkMaxFontScale(getContentLayout().messageView, this.mType == 0 ? R.dimen.sesl_design_snackbar_suggest_action_text_size : R.dimen.sesl_design_snackbar_action_text_size, MaxFontScaleRatio.LARGE);
        ContentResolver contentResolver = this.context.getContentResolver();
        if (contentResolver != null && Settings.System.getInt(contentResolver, SettingsHelper.INDEX_SHOW_BUTTON_BACKGROUND, 0) == 1) {
            z = true;
        }
        SeslTextViewReflector.semSetButtonShapeEnabled(button, z);
    }

    public final void show() {
        SnackbarManager snackbarManager = SnackbarManager.getInstance();
        int i = this.duration;
        boolean z = false;
        int i2 = -2;
        if (i != -2) {
            i2 = this.accessibilityManager.getRecommendedTimeoutMillis(i, (this.hasAction ? 4 : 0) | 3);
        }
        BaseTransientBottomBar.AnonymousClass5 anonymousClass5 = this.managerCallback;
        synchronized (snackbarManager.lock) {
            try {
                if (snackbarManager.isCurrentSnackbarLocked(anonymousClass5)) {
                    SnackbarManager.SnackbarRecord snackbarRecord = snackbarManager.currentSnackbar;
                    snackbarRecord.duration = i2;
                    snackbarManager.handler.removeCallbacksAndMessages(snackbarRecord);
                    snackbarManager.scheduleTimeoutLocked(snackbarManager.currentSnackbar);
                    return;
                }
                SnackbarManager.SnackbarRecord snackbarRecord2 = snackbarManager.nextSnackbar;
                if (snackbarRecord2 != null && anonymousClass5 != null && snackbarRecord2.callback.get() == anonymousClass5) {
                    z = true;
                }
                if (z) {
                    snackbarManager.nextSnackbar.duration = i2;
                } else {
                    snackbarManager.nextSnackbar = new SnackbarManager.SnackbarRecord(i2, anonymousClass5);
                }
                SnackbarManager.SnackbarRecord snackbarRecord3 = snackbarManager.currentSnackbar;
                if (snackbarRecord3 == null || !snackbarManager.cancelSnackbarLocked(snackbarRecord3, 4)) {
                    snackbarManager.currentSnackbar = null;
                    snackbarManager.showNextSnackbarLocked();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class SnackbarLayout extends BaseTransientBottomBar.SnackbarBaseLayout {
        public SnackbarLayout(Context context) {
            super(context);
            setBackgroundColor(android.R.color.transparent);
        }

        @Override // com.google.android.material.snackbar.BaseTransientBottomBar.SnackbarBaseLayout, android.widget.FrameLayout, android.view.View
        public final void onMeasure(int i, int i2) {
            super.onMeasure(i, i2);
            int childCount = getChildCount();
            int measuredWidth = (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight();
            for (int i3 = 0; i3 < childCount; i3++) {
                View childAt = getChildAt(i3);
                if (childAt.getLayoutParams().width == -1) {
                    childAt.measure(View.MeasureSpec.makeMeasureSpec(measuredWidth, 1073741824), View.MeasureSpec.makeMeasureSpec(childAt.getMeasuredHeight(), 1073741824));
                }
                if (childAt.getLayoutParams().height == -2) {
                    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) childAt.getLayoutParams();
                    if (childAt.getHeight() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin > (getMeasuredHeight() - getPaddingBottom()) - getPaddingTop()) {
                        super.onMeasure(i, i2);
                    }
                }
            }
        }

        public SnackbarLayout(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            setBackgroundColor(android.R.color.transparent);
        }
    }
}
