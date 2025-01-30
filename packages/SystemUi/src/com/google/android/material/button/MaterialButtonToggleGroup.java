package com.google.android.material.button;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.LinearLayout;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.widget.NestedScrollView$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.google.android.material.R$styleable;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.shape.AbsoluteCornerSize;
import com.google.android.material.shape.CornerSize;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import com.google.android.material.timepicker.TimePickerView$$ExternalSyntheticLambda0;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class MaterialButtonToggleGroup extends LinearLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public Set checkedIds;
    public Integer[] childOrder;
    public final C42411 childOrderComparator;
    public final int defaultCheckId;
    public final LinkedHashSet onButtonCheckedListeners;
    public final List originalCornerData;
    public final PressedStateTracker pressedStateTracker;
    public final boolean selectionRequired;
    public boolean singleSelection;
    public boolean skipCheckedStateTracker;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class CornerData {
        public static final AbsoluteCornerSize noCorner = new AbsoluteCornerSize(0.0f);
        public final CornerSize bottomLeft;
        public final CornerSize bottomRight;
        public final CornerSize topLeft;
        public final CornerSize topRight;

        public CornerData(CornerSize cornerSize, CornerSize cornerSize2, CornerSize cornerSize3, CornerSize cornerSize4) {
            this.topLeft = cornerSize;
            this.topRight = cornerSize3;
            this.bottomRight = cornerSize4;
            this.bottomLeft = cornerSize2;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class PressedStateTracker {
        private PressedStateTracker() {
        }
    }

    public MaterialButtonToggleGroup(Context context) {
        this(context, null);
    }

    @Override // android.view.ViewGroup
    public final void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        if (!(view instanceof MaterialButton)) {
            Log.e("MaterialButtonToggleGroup", "Child views must be of type MaterialButton.");
            return;
        }
        super.addView(view, i, layoutParams);
        MaterialButton materialButton = (MaterialButton) view;
        if (materialButton.getId() == -1) {
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            materialButton.setId(ViewCompat.Api17Impl.generateViewId());
        }
        materialButton.setMaxLines(1);
        materialButton.setEllipsize(TextUtils.TruncateAt.END);
        if (materialButton.isUsingOriginalBackground()) {
            materialButton.materialButtonHelper.checkable = true;
        }
        materialButton.onPressedChangeListenerInternal = this.pressedStateTracker;
        if (materialButton.isUsingOriginalBackground()) {
            MaterialButtonHelper materialButtonHelper = materialButton.materialButtonHelper;
            materialButtonHelper.shouldDrawSurfaceColorStroke = true;
            MaterialShapeDrawable materialShapeDrawable = materialButtonHelper.getMaterialShapeDrawable(false);
            MaterialShapeDrawable materialShapeDrawable2 = materialButtonHelper.getMaterialShapeDrawable(true);
            if (materialShapeDrawable != null) {
                float f = materialButtonHelper.strokeWidth;
                ColorStateList colorStateList = materialButtonHelper.strokeColor;
                materialShapeDrawable.drawableState.strokeWidth = f;
                materialShapeDrawable.invalidateSelf();
                materialShapeDrawable.setStrokeColor(colorStateList);
                if (materialShapeDrawable2 != null) {
                    float f2 = materialButtonHelper.strokeWidth;
                    int color = materialButtonHelper.shouldDrawSurfaceColorStroke ? MaterialColors.getColor(materialButtonHelper.materialButton, R.attr.colorSurface) : 0;
                    materialShapeDrawable2.drawableState.strokeWidth = f2;
                    materialShapeDrawable2.invalidateSelf();
                    materialShapeDrawable2.setStrokeColor(ColorStateList.valueOf(color));
                }
            }
        }
        checkInternal(materialButton.getId(), materialButton.isChecked());
        if (!materialButton.isUsingOriginalBackground()) {
            throw new IllegalStateException("Attempted to get ShapeAppearanceModel from a MaterialButton which has an overwritten background.");
        }
        ShapeAppearanceModel shapeAppearanceModel = materialButton.materialButtonHelper.shapeAppearanceModel;
        ((ArrayList) this.originalCornerData).add(new CornerData(shapeAppearanceModel.topLeftCornerSize, shapeAppearanceModel.bottomLeftCornerSize, shapeAppearanceModel.topRightCornerSize, shapeAppearanceModel.bottomRightCornerSize));
        ViewCompat.setAccessibilityDelegate(materialButton, new AccessibilityDelegateCompat() { // from class: com.google.android.material.button.MaterialButtonToggleGroup.2
            @Override // androidx.core.view.AccessibilityDelegateCompat
            public final void onInitializeAccessibilityNodeInfo(View view2, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                int i2;
                this.mOriginalDelegate.onInitializeAccessibilityNodeInfo(view2, accessibilityNodeInfoCompat.mInfo);
                int i3 = MaterialButtonToggleGroup.$r8$clinit;
                MaterialButtonToggleGroup materialButtonToggleGroup = MaterialButtonToggleGroup.this;
                materialButtonToggleGroup.getClass();
                if (view2 instanceof MaterialButton) {
                    int i4 = 0;
                    for (int i5 = 0; i5 < materialButtonToggleGroup.getChildCount(); i5++) {
                        if (materialButtonToggleGroup.getChildAt(i5) == view2) {
                            i2 = i4;
                            break;
                        }
                        if ((materialButtonToggleGroup.getChildAt(i5) instanceof MaterialButton) && materialButtonToggleGroup.isChildVisible(i5)) {
                            i4++;
                        }
                    }
                }
                i2 = -1;
                accessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(false, 0, 1, i2, 1, ((MaterialButton) view2).isChecked()));
            }
        });
    }

    public final void adjustChildMarginsAndUpdateLayout() {
        int childCount = getChildCount();
        int i = 0;
        while (true) {
            if (i >= childCount) {
                i = -1;
                break;
            } else if (isChildVisible(i)) {
                break;
            } else {
                i++;
            }
        }
        if (i == -1) {
            return;
        }
        for (int i2 = i + 1; i2 < getChildCount(); i2++) {
            MaterialButton childButton = getChildButton(i2);
            MaterialButton childButton2 = getChildButton(i2 - 1);
            int min = Math.min(childButton.isUsingOriginalBackground() ? childButton.materialButtonHelper.strokeWidth : 0, childButton2.isUsingOriginalBackground() ? childButton2.materialButtonHelper.strokeWidth : 0);
            ViewGroup.LayoutParams layoutParams = childButton.getLayoutParams();
            LinearLayout.LayoutParams layoutParams2 = layoutParams instanceof LinearLayout.LayoutParams ? (LinearLayout.LayoutParams) layoutParams : new LinearLayout.LayoutParams(layoutParams.width, layoutParams.height);
            if (getOrientation() == 0) {
                layoutParams2.setMarginEnd(0);
                layoutParams2.setMarginStart(-min);
                layoutParams2.topMargin = 0;
            } else {
                layoutParams2.bottomMargin = 0;
                layoutParams2.topMargin = -min;
                layoutParams2.setMarginStart(0);
            }
            childButton.setLayoutParams(layoutParams2);
        }
        if (getChildCount() == 0 || i == -1) {
            return;
        }
        LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) getChildButton(i).getLayoutParams();
        if (getOrientation() == 1) {
            layoutParams3.topMargin = 0;
            layoutParams3.bottomMargin = 0;
        } else {
            layoutParams3.setMarginEnd(0);
            layoutParams3.setMarginStart(0);
            layoutParams3.leftMargin = 0;
            layoutParams3.rightMargin = 0;
        }
    }

    public final void checkInternal(int i, boolean z) {
        if (i == -1) {
            NestedScrollView$$ExternalSyntheticOutline0.m34m("Button ID is not valid: ", i, "MaterialButtonToggleGroup");
            return;
        }
        HashSet hashSet = new HashSet(this.checkedIds);
        if (z && !hashSet.contains(Integer.valueOf(i))) {
            if (this.singleSelection && !hashSet.isEmpty()) {
                hashSet.clear();
            }
            hashSet.add(Integer.valueOf(i));
        } else {
            if (z || !hashSet.contains(Integer.valueOf(i))) {
                return;
            }
            if (!this.selectionRequired || hashSet.size() > 1) {
                hashSet.remove(Integer.valueOf(i));
            }
        }
        updateCheckedIds(hashSet);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void dispatchDraw(Canvas canvas) {
        TreeMap treeMap = new TreeMap(this.childOrderComparator);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            treeMap.put(getChildButton(i), Integer.valueOf(i));
        }
        this.childOrder = (Integer[]) treeMap.values().toArray(new Integer[0]);
        super.dispatchDraw(canvas);
    }

    public final MaterialButton getChildButton(int i) {
        return (MaterialButton) getChildAt(i);
    }

    @Override // android.view.ViewGroup
    public final int getChildDrawingOrder(int i, int i2) {
        Integer[] numArr = this.childOrder;
        if (numArr != null && i2 < numArr.length) {
            return numArr[i2].intValue();
        }
        Log.w("MaterialButtonToggleGroup", "Child order wasn't updated");
        return i2;
    }

    public final boolean isChildVisible(int i) {
        return getChildAt(i).getVisibility() != 8;
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        int i = this.defaultCheckId;
        if (i != -1) {
            updateCheckedIds(Collections.singleton(Integer.valueOf(i)));
        }
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        AccessibilityNodeInfoCompat wrap = AccessibilityNodeInfoCompat.wrap(accessibilityNodeInfo);
        int i = 0;
        for (int i2 = 0; i2 < getChildCount(); i2++) {
            if ((getChildAt(i2) instanceof MaterialButton) && isChildVisible(i2)) {
                i++;
            }
        }
        wrap.setCollectionInfo(AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(1, i, this.singleSelection ? 1 : 2));
    }

    @Override // android.widget.LinearLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        updateChildShapes();
        adjustChildMarginsAndUpdateLayout();
        super.onMeasure(i, i2);
    }

    @Override // android.view.ViewGroup
    public final void onViewRemoved(View view) {
        super.onViewRemoved(view);
        if (view instanceof MaterialButton) {
            ((MaterialButton) view).onPressedChangeListenerInternal = null;
        }
        int indexOfChild = indexOfChild(view);
        if (indexOfChild >= 0) {
            ((ArrayList) this.originalCornerData).remove(indexOfChild);
        }
        updateChildShapes();
        adjustChildMarginsAndUpdateLayout();
    }

    public final void updateCheckedIds(Set set) {
        Set set2 = this.checkedIds;
        this.checkedIds = new HashSet(set);
        for (int i = 0; i < getChildCount(); i++) {
            int id = getChildButton(i).getId();
            boolean contains = set.contains(Integer.valueOf(id));
            View findViewById = findViewById(id);
            if (findViewById instanceof MaterialButton) {
                this.skipCheckedStateTracker = true;
                ((MaterialButton) findViewById).setChecked(contains);
                this.skipCheckedStateTracker = false;
            }
            if (((HashSet) set2).contains(Integer.valueOf(id)) != set.contains(Integer.valueOf(id))) {
                set.contains(Integer.valueOf(id));
                Iterator it = this.onButtonCheckedListeners.iterator();
                while (it.hasNext()) {
                    ((TimePickerView$$ExternalSyntheticLambda0) it.next()).onButtonChecked();
                }
            }
        }
        invalidate();
    }

    public void updateChildShapes() {
        int i;
        CornerData cornerData;
        int childCount = getChildCount();
        int childCount2 = getChildCount();
        int i2 = 0;
        while (true) {
            i = -1;
            if (i2 >= childCount2) {
                i2 = -1;
                break;
            } else if (isChildVisible(i2)) {
                break;
            } else {
                i2++;
            }
        }
        int childCount3 = getChildCount() - 1;
        while (true) {
            if (childCount3 < 0) {
                break;
            }
            if (isChildVisible(childCount3)) {
                i = childCount3;
                break;
            }
            childCount3--;
        }
        for (int i3 = 0; i3 < childCount; i3++) {
            MaterialButton childButton = getChildButton(i3);
            if (childButton.getVisibility() != 8) {
                if (!childButton.isUsingOriginalBackground()) {
                    throw new IllegalStateException("Attempted to get ShapeAppearanceModel from a MaterialButton which has an overwritten background.");
                }
                ShapeAppearanceModel shapeAppearanceModel = childButton.materialButtonHelper.shapeAppearanceModel;
                shapeAppearanceModel.getClass();
                ShapeAppearanceModel.Builder builder = new ShapeAppearanceModel.Builder(shapeAppearanceModel);
                CornerData cornerData2 = (CornerData) ((ArrayList) this.originalCornerData).get(i3);
                if (i2 != i) {
                    boolean z = getOrientation() == 0;
                    if (i3 == i2) {
                        if (z) {
                            AbsoluteCornerSize absoluteCornerSize = CornerData.noCorner;
                            if (ViewUtils.isLayoutRtl(this)) {
                                CornerSize cornerSize = cornerData2.topRight;
                                CornerSize cornerSize2 = cornerData2.bottomRight;
                                AbsoluteCornerSize absoluteCornerSize2 = CornerData.noCorner;
                                cornerData = new CornerData(absoluteCornerSize2, absoluteCornerSize2, cornerSize, cornerSize2);
                            } else {
                                CornerSize cornerSize3 = cornerData2.topLeft;
                                AbsoluteCornerSize absoluteCornerSize3 = CornerData.noCorner;
                                cornerData = new CornerData(cornerSize3, cornerData2.bottomLeft, absoluteCornerSize3, absoluteCornerSize3);
                            }
                        } else {
                            CornerSize cornerSize4 = cornerData2.topLeft;
                            AbsoluteCornerSize absoluteCornerSize4 = CornerData.noCorner;
                            cornerData = new CornerData(cornerSize4, absoluteCornerSize4, cornerData2.topRight, absoluteCornerSize4);
                        }
                    } else if (i3 != i) {
                        cornerData2 = null;
                    } else if (z) {
                        AbsoluteCornerSize absoluteCornerSize5 = CornerData.noCorner;
                        if (ViewUtils.isLayoutRtl(this)) {
                            CornerSize cornerSize5 = cornerData2.topLeft;
                            AbsoluteCornerSize absoluteCornerSize6 = CornerData.noCorner;
                            cornerData = new CornerData(cornerSize5, cornerData2.bottomLeft, absoluteCornerSize6, absoluteCornerSize6);
                        } else {
                            CornerSize cornerSize6 = cornerData2.topRight;
                            CornerSize cornerSize7 = cornerData2.bottomRight;
                            AbsoluteCornerSize absoluteCornerSize7 = CornerData.noCorner;
                            cornerData = new CornerData(absoluteCornerSize7, absoluteCornerSize7, cornerSize6, cornerSize7);
                        }
                    } else {
                        AbsoluteCornerSize absoluteCornerSize8 = CornerData.noCorner;
                        cornerData = new CornerData(absoluteCornerSize8, cornerData2.bottomLeft, absoluteCornerSize8, cornerData2.bottomRight);
                    }
                    cornerData2 = cornerData;
                }
                if (cornerData2 == null) {
                    builder.setAllCornerSizes(0.0f);
                } else {
                    builder.topLeftCornerSize = cornerData2.topLeft;
                    builder.bottomLeftCornerSize = cornerData2.bottomLeft;
                    builder.topRightCornerSize = cornerData2.topRight;
                    builder.bottomRightCornerSize = cornerData2.bottomRight;
                }
                childButton.setShapeAppearanceModel(builder.build());
            }
        }
    }

    public MaterialButtonToggleGroup(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.materialButtonToggleGroupStyle);
    }

    /* JADX WARN: Type inference failed for: r8v5, types: [com.google.android.material.button.MaterialButtonToggleGroup$1] */
    public MaterialButtonToggleGroup(Context context, AttributeSet attributeSet, int i) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, i, 2132019116), attributeSet, i);
        this.originalCornerData = new ArrayList();
        this.pressedStateTracker = new PressedStateTracker();
        this.onButtonCheckedListeners = new LinkedHashSet();
        this.childOrderComparator = new Comparator() { // from class: com.google.android.material.button.MaterialButtonToggleGroup.1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                MaterialButton materialButton = (MaterialButton) obj;
                MaterialButton materialButton2 = (MaterialButton) obj2;
                int compareTo = Boolean.valueOf(materialButton.isChecked()).compareTo(Boolean.valueOf(materialButton2.isChecked()));
                if (compareTo != 0) {
                    return compareTo;
                }
                int compareTo2 = Boolean.valueOf(materialButton.isPressed()).compareTo(Boolean.valueOf(materialButton2.isPressed()));
                return compareTo2 != 0 ? compareTo2 : Integer.valueOf(MaterialButtonToggleGroup.this.indexOfChild(materialButton)).compareTo(Integer.valueOf(MaterialButtonToggleGroup.this.indexOfChild(materialButton2)));
            }
        };
        this.skipCheckedStateTracker = false;
        this.checkedIds = new HashSet();
        TypedArray obtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(getContext(), attributeSet, R$styleable.MaterialButtonToggleGroup, i, 2132019116, new int[0]);
        boolean z = obtainStyledAttributes.getBoolean(2, false);
        if (this.singleSelection != z) {
            this.singleSelection = z;
            updateCheckedIds(new HashSet());
        }
        this.defaultCheckId = obtainStyledAttributes.getResourceId(0, -1);
        this.selectionRequired = obtainStyledAttributes.getBoolean(1, false);
        setChildrenDrawingOrderEnabled(true);
        obtainStyledAttributes.recycle();
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api16Impl.setImportantForAccessibility(this, 1);
    }
}
