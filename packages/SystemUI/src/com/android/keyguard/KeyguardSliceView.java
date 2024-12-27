package com.android.keyguard;

import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.net.Uri;
import android.os.Trace;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.graphics.drawable.IconCompat;
import androidx.slice.SliceItem;
import androidx.slice.core.SliceQuery;
import androidx.slice.widget.RowContent;
import com.android.app.animation.Interpolators;
import com.android.internal.graphics.ColorUtils;
import com.android.settingslib.Utils;
import com.android.systemui.R;
import com.android.systemui.util.wakelock.KeepAwakeAnimationListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class KeyguardSliceView extends LinearLayout {
    public float mDarkAmount;
    public boolean mHasHeader;
    public int mIconSize;
    public int mIconSizeWithHeader;
    public final LayoutTransition mLayoutTransition;
    public View.OnClickListener mOnClickListener;
    public Row mRow;
    public int mTextColor;
    TextView mTitle;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    class KeyguardSliceTextView extends TextView {
        public KeyguardSliceTextView(Context context) {
            super(context, null, 0, R.style.TextAppearance_Keyguard_Secondary);
            updatePadding();
            setEllipsize(TextUtils.TruncateAt.END);
        }

        @Override // android.widget.TextView
        public final void setCompoundDrawablesRelative(Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
            super.setCompoundDrawablesRelative(drawable, drawable2, drawable3, drawable4);
            int currentTextColor = getCurrentTextColor();
            for (Drawable drawable5 : getCompoundDrawables()) {
                if (drawable5 != null) {
                    drawable5.setTint(currentTextColor);
                }
            }
            updatePadding();
        }

        @Override // android.widget.TextView
        public final void setText(CharSequence charSequence, TextView.BufferType bufferType) {
            super.setText(charSequence, bufferType);
            updatePadding();
        }

        @Override // android.widget.TextView
        public final void setTextColor(int i) {
            super.setTextColor(i);
            int currentTextColor = getCurrentTextColor();
            for (Drawable drawable : getCompoundDrawables()) {
                if (drawable != null) {
                    drawable.setTint(currentTextColor);
                }
            }
        }

        public final void updatePadding() {
            boolean z = !TextUtils.isEmpty(getText());
            int dimension = ((int) getContext().getResources().getDimension(R.dimen.widget_horizontal_padding)) / 2;
            setPadding(0, dimension, 0, z ? dimension : 0);
            setCompoundDrawablePadding((int) ((TextView) this).mContext.getResources().getDimension(R.dimen.widget_icon_padding));
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public class Row extends LinearLayout {
        public float mDarkAmount;
        public final KeepAwakeAnimationListener mKeepAwakeListener;
        public final Set mKeyguardSliceTextViewSet;
        public LayoutTransition mLayoutTransition;

        public Row(Context context) {
            this(context, null);
        }

        @Override // android.view.ViewGroup
        public final void addView(View view, int i) {
            super.addView(view, i);
            if (view instanceof KeyguardSliceTextView) {
                ((HashSet) this.mKeyguardSliceTextViewSet).add((KeyguardSliceTextView) view);
            }
        }

        @Override // android.view.View
        public final boolean hasOverlappingRendering() {
            return false;
        }

        @Override // android.view.View
        public final void onFinishInflate() {
            LayoutTransition layoutTransition = new LayoutTransition();
            this.mLayoutTransition = layoutTransition;
            layoutTransition.setDuration(550L);
            ObjectAnimator ofPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(null, PropertyValuesHolder.ofInt("left", 0, 1), PropertyValuesHolder.ofInt("right", 0, 1));
            this.mLayoutTransition.setAnimator(0, ofPropertyValuesHolder);
            this.mLayoutTransition.setAnimator(1, ofPropertyValuesHolder);
            LayoutTransition layoutTransition2 = this.mLayoutTransition;
            Interpolator interpolator = Interpolators.ACCELERATE_DECELERATE;
            layoutTransition2.setInterpolator(0, interpolator);
            this.mLayoutTransition.setInterpolator(1, interpolator);
            this.mLayoutTransition.setStartDelay(0, 550L);
            this.mLayoutTransition.setStartDelay(1, 550L);
            this.mLayoutTransition.setAnimator(2, ObjectAnimator.ofFloat((Object) null, "alpha", 0.0f, 1.0f));
            this.mLayoutTransition.setInterpolator(2, Interpolators.ALPHA_IN);
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat((Object) null, "alpha", 1.0f, 0.0f);
            this.mLayoutTransition.setInterpolator(3, Interpolators.ALPHA_OUT);
            this.mLayoutTransition.setDuration(3, 137L);
            this.mLayoutTransition.setAnimator(3, ofFloat);
            this.mLayoutTransition.setAnimateParentHierarchy(false);
        }

        @Override // android.widget.LinearLayout, android.view.View
        public final void onMeasure(int i, int i2) {
            View.MeasureSpec.getSize(i);
            int childCount = getChildCount();
            for (int i3 = 0; i3 < childCount; i3++) {
                View childAt = getChildAt(i3);
                if (childAt instanceof KeyguardSliceTextView) {
                    ((KeyguardSliceTextView) childAt).setMaxWidth(Integer.MAX_VALUE);
                }
            }
            super.onMeasure(i, i2);
        }

        @Override // android.view.View
        public final void onVisibilityAggregated(boolean z) {
            super.onVisibilityAggregated(z);
            setLayoutTransition(z ? this.mLayoutTransition : null);
        }

        @Override // android.view.ViewGroup, android.view.ViewManager
        public final void removeView(View view) {
            super.removeView(view);
            if (view instanceof KeyguardSliceTextView) {
                ((HashSet) this.mKeyguardSliceTextViewSet).remove((KeyguardSliceTextView) view);
            }
        }

        public Row(Context context, AttributeSet attributeSet) {
            this(context, attributeSet, 0);
        }

        public Row(Context context, AttributeSet attributeSet, int i) {
            this(context, attributeSet, i, 0);
        }

        public Row(Context context, AttributeSet attributeSet, int i, int i2) {
            super(context, attributeSet, i, i2);
            this.mKeyguardSliceTextViewSet = new HashSet();
            this.mKeepAwakeListener = new KeepAwakeAnimationListener(((LinearLayout) this).mContext);
        }
    }

    public KeyguardSliceView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mDarkAmount = 0.0f;
        context.getResources();
        LayoutTransition layoutTransition = new LayoutTransition();
        this.mLayoutTransition = layoutTransition;
        layoutTransition.setStagger(0, 275L);
        layoutTransition.setDuration(2, 550L);
        layoutTransition.setDuration(3, 275L);
        layoutTransition.disableTransitionType(0);
        layoutTransition.disableTransitionType(1);
        layoutTransition.setInterpolator(2, Interpolators.FAST_OUT_SLOW_IN);
        layoutTransition.setInterpolator(3, Interpolators.ALPHA_OUT);
        layoutTransition.setAnimateParentHierarchy(false);
    }

    public int getTextColor() {
        return ColorUtils.blendARGB(this.mTextColor, -1, this.mDarkAmount);
    }

    public final void onDensityOrFontScaleChanged() {
        this.mIconSize = ((LinearLayout) this).mContext.getResources().getDimensionPixelSize(R.dimen.widget_icon_size);
        this.mIconSizeWithHeader = (int) ((LinearLayout) this).mContext.getResources().getDimension(R.dimen.header_icon_size);
        for (int i = 0; i < this.mRow.getChildCount(); i++) {
            View childAt = this.mRow.getChildAt(i);
            if (childAt instanceof KeyguardSliceTextView) {
                ((KeyguardSliceTextView) childAt).updatePadding();
            }
        }
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mTitle = (TextView) findViewById(R.id.title);
        this.mRow = (Row) findViewById(R.id.row);
        this.mTextColor = Utils.getColorAttrDefaultColor(((LinearLayout) this).mContext, R.attr.wallpaperTextColor, 0);
        this.mIconSize = (int) ((LinearLayout) this).mContext.getResources().getDimension(R.dimen.widget_icon_size);
        this.mIconSizeWithHeader = (int) ((LinearLayout) this).mContext.getResources().getDimension(R.dimen.header_icon_size);
        this.mTitle.setBreakStrategy(2);
    }

    @Override // android.view.View
    public final void onVisibilityAggregated(boolean z) {
        super.onVisibilityAggregated(z);
        setLayoutTransition(z ? this.mLayoutTransition : null);
    }

    @Override // android.view.View
    public final void setOnClickListener(View.OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
        this.mTitle.setOnClickListener(onClickListener);
    }

    public void setTextColor(int i) {
        this.mTextColor = i;
        updateTextColors();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final Map showSlice(RowContent rowContent, List list) {
        Drawable drawable;
        Trace.beginSection("KeyguardSliceView#showSlice");
        int i = 0;
        this.mHasHeader = rowContent != null;
        HashMap hashMap = new HashMap();
        if (this.mHasHeader) {
            this.mTitle.setVisibility(0);
            SliceItem sliceItem = rowContent.mTitleItem;
            this.mTitle.setText(sliceItem != null ? (CharSequence) sliceItem.mObj : null);
            SliceItem sliceItem2 = rowContent.mPrimaryAction;
            if (sliceItem2 != null && sliceItem2.getAction() != null) {
                hashMap.put(this.mTitle, rowContent.mPrimaryAction.getAction());
            }
        } else {
            this.mTitle.setVisibility(8);
        }
        int size = list.size();
        int textColor = getTextColor();
        boolean z = this.mHasHeader;
        this.mRow.setVisibility(size > 0 ? 0 : 8);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mRow.getLayoutParams();
        layoutParams.gravity = 8388611;
        this.mRow.setLayoutParams(layoutParams);
        for (int i2 = z; i2 < size; i2++) {
            RowContent rowContent2 = (RowContent) list.get(i2);
            SliceItem sliceItem3 = rowContent2.mSliceItem;
            Uri parse = Uri.parse(sliceItem3.getSlice().mUri);
            KeyguardSliceTextView keyguardSliceTextView = (KeyguardSliceTextView) this.mRow.findViewWithTag(parse);
            if (keyguardSliceTextView == null) {
                keyguardSliceTextView = new KeyguardSliceTextView(((LinearLayout) this).mContext);
                keyguardSliceTextView.setTextColor(textColor);
                keyguardSliceTextView.setTag(parse);
                this.mRow.addView(keyguardSliceTextView, i2 - (this.mHasHeader ? 1 : 0));
            }
            SliceItem sliceItem4 = rowContent2.mPrimaryAction;
            PendingIntent action = sliceItem4 != null ? sliceItem4.getAction() : null;
            hashMap.put(keyguardSliceTextView, action);
            SliceItem sliceItem5 = rowContent2.mTitleItem;
            keyguardSliceTextView.setText(sliceItem5 == null ? null : (CharSequence) sliceItem5.mObj);
            SliceItem sliceItem6 = rowContent2.mContentDescr;
            keyguardSliceTextView.setContentDescription(sliceItem6 != null ? (CharSequence) sliceItem6.mObj : null);
            SliceItem find = SliceQuery.find(sliceItem3.getSlice(), "image", (String[]) null, (String[]) null);
            if (find != null) {
                int i3 = this.mHasHeader ? this.mIconSizeWithHeader : this.mIconSize;
                drawable = ((IconCompat) find.mObj).loadDrawable(((LinearLayout) this).mContext);
                if (drawable != null) {
                    if (drawable instanceof InsetDrawable) {
                        drawable = ((InsetDrawable) drawable).getDrawable();
                    }
                    drawable.setBounds(0, 0, Math.max((int) ((drawable.getIntrinsicWidth() / drawable.getIntrinsicHeight()) * i3), 1), i3);
                }
            } else {
                drawable = null;
            }
            keyguardSliceTextView.setCompoundDrawablesRelative(drawable, null, null, null);
            keyguardSliceTextView.setOnClickListener(this.mOnClickListener);
            keyguardSliceTextView.setClickable(action != null);
        }
        while (i < this.mRow.getChildCount()) {
            View childAt = this.mRow.getChildAt(i);
            if (!hashMap.containsKey(childAt)) {
                this.mRow.removeView(childAt);
                i--;
            }
            i++;
        }
        Trace.endSection();
        return hashMap;
    }

    public final void updateTextColors() {
        int textColor = getTextColor();
        this.mTitle.setTextColor(textColor);
        int childCount = this.mRow.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = this.mRow.getChildAt(i);
            if (childAt instanceof TextView) {
                ((TextView) childAt).setTextColor(textColor);
            }
        }
    }
}
