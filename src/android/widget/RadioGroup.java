package android.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStructure;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.autofill.AutofillManager;
import android.view.autofill.AutofillValue;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import com.android.internal.R;

@RemoteViews.RemoteView
/* loaded from: classes5.dex */
public class RadioGroup extends LinearLayout {
    private static final String LOG_TAG = "RadioGroup";
    private int mCheckedId;
    private CompoundButton.OnCheckedChangeListener mChildOnCheckedChangeListener;
    private int mInitialCheckedId;
    private OnCheckedChangeListener mOnCheckedChangeListener;
    private PassThroughHierarchyChangeListener mPassThroughListener;
    private boolean mProtectFromCheckedChange;

    public interface OnCheckedChangeListener {
        void onCheckedChanged(RadioGroup radioGroup, int i);
    }

    public RadioGroup(Context context) {
        super(context);
        this.mCheckedId = -1;
        this.mProtectFromCheckedChange = false;
        this.mInitialCheckedId = -1;
        setOrientation(1);
        init();
    }

    public RadioGroup(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mCheckedId = -1;
        this.mProtectFromCheckedChange = false;
        this.mInitialCheckedId = -1;
        if (getImportantForAutofill() == 0) {
            setImportantForAutofill(1);
        }
        setImportantForAccessibility(1);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.RadioGroup, 16842878, 0);
        saveAttributeDataForStyleable(context, R.styleable.RadioGroup, attributeSet, obtainStyledAttributes, 16842878, 0);
        int resourceId = obtainStyledAttributes.getResourceId(1, -1);
        if (resourceId != -1) {
            this.mCheckedId = resourceId;
            this.mInitialCheckedId = resourceId;
        }
        setOrientation(obtainStyledAttributes.getInt(0, 1));
        obtainStyledAttributes.recycle();
        init();
    }

    private void init() {
        this.mChildOnCheckedChangeListener = new CheckedStateTracker();
        PassThroughHierarchyChangeListener passThroughHierarchyChangeListener = new PassThroughHierarchyChangeListener();
        this.mPassThroughListener = passThroughHierarchyChangeListener;
        super.setOnHierarchyChangeListener(passThroughHierarchyChangeListener);
    }

    @Override // android.view.ViewGroup
    public void setOnHierarchyChangeListener(ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener) {
        this.mPassThroughListener.mOnHierarchyChangeListener = onHierarchyChangeListener;
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        int i = this.mCheckedId;
        if (i != -1) {
            this.mProtectFromCheckedChange = true;
            setCheckedStateForView(i, true);
            this.mProtectFromCheckedChange = false;
            setCheckedId(this.mCheckedId);
        }
    }

    @Override // android.view.ViewGroup
    public void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        if (view instanceof RadioButton) {
            RadioButton radioButton = (RadioButton) view;
            if (radioButton.isChecked()) {
                this.mProtectFromCheckedChange = true;
                int i2 = this.mCheckedId;
                if (i2 != -1) {
                    setCheckedStateForView(i2, false);
                }
                this.mProtectFromCheckedChange = false;
                setCheckedId(radioButton.getId());
            }
        }
        super.addView(view, i, layoutParams);
    }

    public void check(int i) {
        if (i == -1 || i != this.mCheckedId) {
            int i2 = this.mCheckedId;
            if (i2 != -1) {
                setCheckedStateForView(i2, false);
            }
            if (i != -1) {
                setCheckedStateForView(i, true);
            }
            setCheckedId(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setCheckedId(int i) {
        AutofillManager autofillManager;
        boolean z = i != this.mCheckedId;
        this.mCheckedId = i;
        OnCheckedChangeListener onCheckedChangeListener = this.mOnCheckedChangeListener;
        if (onCheckedChangeListener != null) {
            onCheckedChangeListener.onCheckedChanged(this, i);
        }
        if (!z || (autofillManager = (AutofillManager) this.mContext.getSystemService(AutofillManager.class)) == null) {
            return;
        }
        autofillManager.notifyValueChanged(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setCheckedStateForView(int i, boolean z) {
        View findViewById = findViewById(i);
        if (findViewById == null || !(findViewById instanceof RadioButton)) {
            return;
        }
        ((RadioButton) findViewById).setChecked(z);
    }

    public int getCheckedRadioButtonId() {
        return this.mCheckedId;
    }

    public void clearCheck() {
        check(-1);
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.mOnCheckedChangeListener = onCheckedChangeListener;
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup
    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup
    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.widget.LinearLayout, android.view.ViewGroup
    public LinearLayout.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public CharSequence getAccessibilityClassName() {
        return RadioGroup.class.getName();
    }

    public static class LayoutParams extends LinearLayout.LayoutParams {
        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
        }

        public LayoutParams(int i, int i2, float f) {
            super(i, i2, f);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        @Override // android.view.ViewGroup.LayoutParams
        protected void setBaseAttributes(TypedArray typedArray, int i, int i2) {
            if (typedArray.hasValue(i)) {
                this.width = typedArray.getLayoutDimension(i, "layout_width");
            } else {
                this.width = -2;
            }
            if (typedArray.hasValue(i2)) {
                this.height = typedArray.getLayoutDimension(i2, "layout_height");
            } else {
                this.height = -2;
            }
        }
    }

    private class CheckedStateTracker implements CompoundButton.OnCheckedChangeListener {
        private CheckedStateTracker() {
        }

        @Override // android.widget.CompoundButton.OnCheckedChangeListener
        public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
            if (RadioGroup.this.mProtectFromCheckedChange) {
                return;
            }
            RadioGroup.this.mProtectFromCheckedChange = true;
            if (RadioGroup.this.mCheckedId != -1) {
                RadioGroup radioGroup = RadioGroup.this;
                radioGroup.setCheckedStateForView(radioGroup.mCheckedId, false);
            }
            RadioGroup.this.mProtectFromCheckedChange = false;
            RadioGroup.this.setCheckedId(compoundButton.getId());
        }
    }

    private class PassThroughHierarchyChangeListener implements ViewGroup.OnHierarchyChangeListener {
        private ViewGroup.OnHierarchyChangeListener mOnHierarchyChangeListener;

        private PassThroughHierarchyChangeListener() {
        }

        @Override // android.view.ViewGroup.OnHierarchyChangeListener
        public void onChildViewAdded(View view, View view2) {
            if (view == RadioGroup.this && (view2 instanceof RadioButton)) {
                if (view2.getId() == -1) {
                    view2.setId(View.generateViewId());
                }
                ((RadioButton) view2).setOnCheckedChangeWidgetListener(RadioGroup.this.mChildOnCheckedChangeListener);
            }
            ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener = this.mOnHierarchyChangeListener;
            if (onHierarchyChangeListener != null) {
                onHierarchyChangeListener.onChildViewAdded(view, view2);
            }
        }

        @Override // android.view.ViewGroup.OnHierarchyChangeListener
        public void onChildViewRemoved(View view, View view2) {
            if (view == RadioGroup.this && (view2 instanceof RadioButton)) {
                ((RadioButton) view2).setOnCheckedChangeWidgetListener(null);
            }
            ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener = this.mOnHierarchyChangeListener;
            if (onHierarchyChangeListener != null) {
                onHierarchyChangeListener.onChildViewRemoved(view, view2);
            }
        }
    }

    @Override // android.view.View
    protected void onProvideStructure(ViewStructure viewStructure, int i, int i2) {
        super.onProvideStructure(viewStructure, i, i2);
        if (i == 1) {
            viewStructure.setDataIsSensitive(this.mCheckedId != this.mInitialCheckedId);
        }
    }

    @Override // android.view.View
    public void autofill(AutofillValue autofillValue) {
        if (isEnabled()) {
            if (!autofillValue.isList()) {
                Log.w(LOG_TAG, autofillValue + " could not be autofilled into " + this);
                return;
            }
            int listValue = autofillValue.getListValue();
            View childAt = getChildAt(listValue);
            if (childAt == null) {
                Log.w("View", "RadioGroup.autoFill(): no child with index " + listValue);
                return;
            }
            check(childAt.getId());
        }
    }

    @Override // android.view.View
    public int getAutofillType() {
        return isEnabled() ? 3 : 0;
    }

    @Override // android.view.View
    public AutofillValue getAutofillValue() {
        if (!isEnabled()) {
            return null;
        }
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (getChildAt(i).getId() == this.mCheckedId) {
                return AutofillValue.forList(i);
            }
        }
        return null;
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        if (getOrientation() == 0) {
            accessibilityNodeInfo.setCollectionInfo(AccessibilityNodeInfo.CollectionInfo.obtain(1, getVisibleChildWithTextCount(), false, 1));
        } else {
            accessibilityNodeInfo.setCollectionInfo(AccessibilityNodeInfo.CollectionInfo.obtain(getVisibleChildWithTextCount(), 1, false, 1));
        }
    }

    private int getVisibleChildWithTextCount() {
        int i = 0;
        for (int i2 = 0; i2 < getChildCount(); i2++) {
            if ((getChildAt(i2) instanceof RadioButton) && isVisibleWithText((RadioButton) getChildAt(i2))) {
                i++;
            }
        }
        return i;
    }

    int getIndexWithinVisibleButtons(View view) {
        if (!(view instanceof RadioButton)) {
            return -1;
        }
        int i = 0;
        for (int i2 = 0; i2 < getChildCount(); i2++) {
            if (getChildAt(i2) instanceof RadioButton) {
                RadioButton radioButton = (RadioButton) getChildAt(i2);
                if (radioButton == view) {
                    return i;
                }
                if (isVisibleWithText(radioButton)) {
                    i++;
                }
            }
        }
        return -1;
    }

    private boolean isVisibleWithText(RadioButton radioButton) {
        return radioButton.getVisibility() == 0 && !TextUtils.isEmpty(radioButton.getText());
    }
}
