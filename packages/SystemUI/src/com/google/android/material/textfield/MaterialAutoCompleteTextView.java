package com.google.android.material.textfield;

import android.R;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.ListPopupWindow;
import androidx.core.graphics.ColorUtils;
import androidx.core.view.ViewCompat;
import com.google.android.material.R$styleable;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.util.List;
import java.util.Locale;
import java.util.WeakHashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class MaterialAutoCompleteTextView extends AppCompatAutoCompleteTextView {
    public final AccessibilityManager accessibilityManager;
    public final ColorStateList dropDownBackgroundTint;
    public final ListPopupWindow modalListPopup;
    public final float popupElevation;
    public final int simpleItemSelectedColor;
    public final ColorStateList simpleItemSelectedRippleColor;
    public final Rect tempRect;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class MaterialArrayAdapter extends ArrayAdapter {
        public final ColorStateList pressedRippleColor;
        public final ColorStateList selectedItemRippleOverlaidColor;

        public MaterialArrayAdapter(Context context, int i, String[] strArr) {
            super(context, i, strArr);
            ColorStateList colorStateList;
            ColorStateList colorStateList2;
            ColorStateList colorStateList3 = MaterialAutoCompleteTextView.this.simpleItemSelectedRippleColor;
            ColorStateList colorStateList4 = null;
            if (colorStateList3 != null) {
                int[] iArr = {R.attr.state_pressed};
                colorStateList = new ColorStateList(new int[][]{iArr, new int[0]}, new int[]{colorStateList3.getColorForState(iArr, 0), 0});
            } else {
                colorStateList = null;
            }
            this.pressedRippleColor = colorStateList;
            if (MaterialAutoCompleteTextView.this.simpleItemSelectedColor != 0 && (colorStateList2 = MaterialAutoCompleteTextView.this.simpleItemSelectedRippleColor) != null) {
                int[] iArr2 = {R.attr.state_hovered, -16842919};
                int[] iArr3 = {R.attr.state_selected, -16842919};
                colorStateList4 = new ColorStateList(new int[][]{iArr3, iArr2, new int[0]}, new int[]{ColorUtils.compositeColors(colorStateList2.getColorForState(iArr3, 0), MaterialAutoCompleteTextView.this.simpleItemSelectedColor), ColorUtils.compositeColors(MaterialAutoCompleteTextView.this.simpleItemSelectedRippleColor.getColorForState(iArr2, 0), MaterialAutoCompleteTextView.this.simpleItemSelectedColor), MaterialAutoCompleteTextView.this.simpleItemSelectedColor});
            }
            this.selectedItemRippleOverlaidColor = colorStateList4;
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public final View getView(int i, View view, ViewGroup viewGroup) {
            View view2 = super.getView(i, view, viewGroup);
            if (view2 instanceof TextView) {
                TextView textView = (TextView) view2;
                Drawable drawable = null;
                if (MaterialAutoCompleteTextView.this.getText().toString().contentEquals(textView.getText()) && MaterialAutoCompleteTextView.this.simpleItemSelectedColor != 0) {
                    ColorDrawable colorDrawable = new ColorDrawable(MaterialAutoCompleteTextView.this.simpleItemSelectedColor);
                    if (this.pressedRippleColor != null) {
                        colorDrawable.setTintList(this.selectedItemRippleOverlaidColor);
                        drawable = new RippleDrawable(this.pressedRippleColor, colorDrawable, null);
                    } else {
                        drawable = colorDrawable;
                    }
                }
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                textView.setBackground(drawable);
            }
            return view2;
        }
    }

    public MaterialAutoCompleteTextView(Context context) {
        this(context, null);
    }

    public static void access$100(MaterialAutoCompleteTextView materialAutoCompleteTextView, Object obj) {
        materialAutoCompleteTextView.setText(materialAutoCompleteTextView.convertSelectionToString(obj), false);
    }

    @Override // android.widget.AutoCompleteTextView
    public final void dismissDropDown() {
        if (isPopupRequired()) {
            this.modalListPopup.dismiss();
        } else {
            super.dismissDropDown();
        }
    }

    public final TextInputLayout findTextInputLayoutAncestor() {
        for (ViewParent parent = getParent(); parent != null; parent = parent.getParent()) {
            if (parent instanceof TextInputLayout) {
                return (TextInputLayout) parent;
            }
        }
        return null;
    }

    @Override // android.widget.TextView
    public final CharSequence getHint() {
        TextInputLayout findTextInputLayoutAncestor = findTextInputLayoutAncestor();
        if (findTextInputLayoutAncestor == null || !findTextInputLayoutAncestor.isProvidingHint) {
            return super.getHint();
        }
        if (findTextInputLayoutAncestor.hintEnabled) {
            return findTextInputLayoutAncestor.hint;
        }
        return null;
    }

    public final boolean isPopupRequired() {
        List<AccessibilityServiceInfo> enabledAccessibilityServiceList;
        AccessibilityManager accessibilityManager = this.accessibilityManager;
        if (accessibilityManager != null && accessibilityManager.isTouchExplorationEnabled()) {
            return true;
        }
        AccessibilityManager accessibilityManager2 = this.accessibilityManager;
        if (accessibilityManager2 == null || !accessibilityManager2.isEnabled() || (enabledAccessibilityServiceList = this.accessibilityManager.getEnabledAccessibilityServiceList(16)) == null) {
            return false;
        }
        for (AccessibilityServiceInfo accessibilityServiceInfo : enabledAccessibilityServiceList) {
            if (accessibilityServiceInfo.getSettingsActivityName() != null && accessibilityServiceInfo.getSettingsActivityName().contains("SwitchAccess")) {
                return true;
            }
        }
        return false;
    }

    @Override // android.widget.AutoCompleteTextView, android.widget.TextView, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        TextInputLayout findTextInputLayoutAncestor = findTextInputLayoutAncestor();
        if (findTextInputLayoutAncestor != null && findTextInputLayoutAncestor.isProvidingHint && super.getHint() == null) {
            String str = Build.MANUFACTURER;
            if ((str != null ? str.toLowerCase(Locale.ENGLISH) : "").equals("meizu")) {
                setHint("");
            }
        }
    }

    @Override // android.widget.AutoCompleteTextView, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.modalListPopup.dismiss();
    }

    @Override // android.widget.TextView, android.view.View
    public final void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (View.MeasureSpec.getMode(i) == Integer.MIN_VALUE) {
            int measuredWidth = getMeasuredWidth();
            ListAdapter adapter = getAdapter();
            TextInputLayout findTextInputLayoutAncestor = findTextInputLayoutAncestor();
            int i3 = 0;
            if (adapter != null && findTextInputLayoutAncestor != null) {
                int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 0);
                int makeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 0);
                ListPopupWindow listPopupWindow = this.modalListPopup;
                int min = Math.min(adapter.getCount(), Math.max(0, !listPopupWindow.mPopup.isShowing() ? -1 : listPopupWindow.mDropDownList.getSelectedItemPosition()) + 15);
                View view = null;
                int i4 = 0;
                for (int max = Math.max(0, min - 15); max < min; max++) {
                    int itemViewType = adapter.getItemViewType(max);
                    if (itemViewType != i3) {
                        view = null;
                        i3 = itemViewType;
                    }
                    view = adapter.getView(max, view, findTextInputLayoutAncestor);
                    if (view.getLayoutParams() == null) {
                        view.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
                    }
                    view.measure(makeMeasureSpec, makeMeasureSpec2);
                    i4 = Math.max(i4, view.getMeasuredWidth());
                }
                Drawable background = this.modalListPopup.mPopup.getBackground();
                if (background != null) {
                    background.getPadding(this.tempRect);
                    Rect rect = this.tempRect;
                    i4 += rect.left + rect.right;
                }
                i3 = findTextInputLayoutAncestor.endLayout.endIconView.getMeasuredWidth() + i4;
            }
            setMeasuredDimension(Math.min(Math.max(measuredWidth, i3), View.MeasureSpec.getSize(i)), getMeasuredHeight());
        }
    }

    @Override // android.widget.AutoCompleteTextView, android.widget.TextView, android.view.View
    public final void onWindowFocusChanged(boolean z) {
        if (isPopupRequired()) {
            return;
        }
        super.onWindowFocusChanged(z);
    }

    @Override // android.widget.AutoCompleteTextView
    public final void setAdapter(ListAdapter listAdapter) {
        super.setAdapter(listAdapter);
        this.modalListPopup.setAdapter(getAdapter());
    }

    @Override // android.widget.AutoCompleteTextView
    public final void setDropDownBackgroundDrawable(Drawable drawable) {
        super.setDropDownBackgroundDrawable(drawable);
        ListPopupWindow listPopupWindow = this.modalListPopup;
        if (listPopupWindow != null) {
            listPopupWindow.setBackgroundDrawable(drawable);
        }
    }

    @Override // android.widget.AutoCompleteTextView
    public final void setOnItemSelectedListener(AdapterView.OnItemSelectedListener onItemSelectedListener) {
        super.setOnItemSelectedListener(onItemSelectedListener);
        this.modalListPopup.mItemSelectedListener = getOnItemSelectedListener();
    }

    @Override // android.widget.TextView
    public final void setRawInputType(int i) {
        super.setRawInputType(i);
        TextInputLayout findTextInputLayoutAncestor = findTextInputLayoutAncestor();
        if (findTextInputLayoutAncestor != null) {
            findTextInputLayoutAncestor.updateEditTextBoxBackgroundIfNeeded();
        }
    }

    @Override // android.widget.AutoCompleteTextView
    public final void showDropDown() {
        if (isPopupRequired()) {
            this.modalListPopup.show();
        } else {
            super.showDropDown();
        }
    }

    public MaterialAutoCompleteTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, com.android.systemui.R.attr.autoCompleteTextViewStyle);
    }

    public MaterialAutoCompleteTextView(Context context, AttributeSet attributeSet, int i) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, i, 0), attributeSet, i);
        this.tempRect = new Rect();
        Context context2 = getContext();
        TypedArray obtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(context2, attributeSet, R$styleable.MaterialAutoCompleteTextView, i, 2132019140, new int[0]);
        if (obtainStyledAttributes.hasValue(0) && obtainStyledAttributes.getInt(0, 0) == 0) {
            setKeyListener(null);
        }
        int resourceId = obtainStyledAttributes.getResourceId(3, com.android.systemui.R.layout.mtrl_auto_complete_simple_item);
        this.popupElevation = obtainStyledAttributes.getDimensionPixelOffset(1, com.android.systemui.R.dimen.mtrl_exposed_dropdown_menu_popup_elevation);
        if (obtainStyledAttributes.hasValue(2)) {
            this.dropDownBackgroundTint = ColorStateList.valueOf(obtainStyledAttributes.getColor(2, 0));
        }
        this.simpleItemSelectedColor = obtainStyledAttributes.getColor(4, 0);
        this.simpleItemSelectedRippleColor = MaterialResources.getColorStateList(context2, obtainStyledAttributes, 5);
        this.accessibilityManager = (AccessibilityManager) context2.getSystemService("accessibility");
        ListPopupWindow listPopupWindow = new ListPopupWindow(context2);
        this.modalListPopup = listPopupWindow;
        listPopupWindow.mModal = true;
        listPopupWindow.mPopup.setFocusable(true);
        listPopupWindow.mDropDownAnchorView = this;
        listPopupWindow.mPopup.setInputMethodMode(2);
        listPopupWindow.setAdapter(getAdapter());
        listPopupWindow.mItemClickListener = new AdapterView.OnItemClickListener() { // from class: com.google.android.material.textfield.MaterialAutoCompleteTextView.1
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(AdapterView adapterView, View view, int i2, long j) {
                Object item;
                MaterialAutoCompleteTextView materialAutoCompleteTextView = MaterialAutoCompleteTextView.this;
                if (i2 < 0) {
                    ListPopupWindow listPopupWindow2 = materialAutoCompleteTextView.modalListPopup;
                    item = !listPopupWindow2.mPopup.isShowing() ? null : listPopupWindow2.mDropDownList.getSelectedItem();
                } else {
                    item = materialAutoCompleteTextView.getAdapter().getItem(i2);
                }
                MaterialAutoCompleteTextView.access$100(MaterialAutoCompleteTextView.this, item);
                AdapterView.OnItemClickListener onItemClickListener = MaterialAutoCompleteTextView.this.getOnItemClickListener();
                if (onItemClickListener != null) {
                    if (view == null || i2 < 0) {
                        ListPopupWindow listPopupWindow3 = MaterialAutoCompleteTextView.this.modalListPopup;
                        view = listPopupWindow3.mPopup.isShowing() ? listPopupWindow3.mDropDownList.getSelectedView() : null;
                        ListPopupWindow listPopupWindow4 = MaterialAutoCompleteTextView.this.modalListPopup;
                        i2 = !listPopupWindow4.mPopup.isShowing() ? -1 : listPopupWindow4.mDropDownList.getSelectedItemPosition();
                        ListPopupWindow listPopupWindow5 = MaterialAutoCompleteTextView.this.modalListPopup;
                        j = !listPopupWindow5.mPopup.isShowing() ? Long.MIN_VALUE : listPopupWindow5.mDropDownList.getSelectedItemId();
                    }
                    onItemClickListener.onItemClick(MaterialAutoCompleteTextView.this.modalListPopup.mDropDownList, view, i2, j);
                }
                MaterialAutoCompleteTextView.this.modalListPopup.dismiss();
            }
        };
        if (obtainStyledAttributes.hasValue(6)) {
            setAdapter(new MaterialArrayAdapter(getContext(), resourceId, getResources().getStringArray(obtainStyledAttributes.getResourceId(6, 0))));
        }
        obtainStyledAttributes.recycle();
    }
}
