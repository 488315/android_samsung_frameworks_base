package com.android.systemui.controls.management.adapter;

import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.android.systemui.R;
import com.android.systemui.controls.management.model.CustomElementWrapper;
import com.android.systemui.controls.management.model.CustomStructureNameWrapper;
import com.android.systemui.controls.ui.util.ControlsUtil;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class StructureCustomHolder extends CustomHolder {
    public CharSequence displayName;
    public final Function2 favoriteCallback;
    public final String selectAllItems;
    public final CheckBox structureAll;
    public final FrameLayout structureAllLayout;
    public CharSequence structureName;
    public final TextView title;

    public StructureCustomHolder(final View view, Function2 function2) {
        super(view, null);
        this.favoriteCallback = function2;
        TextView textView = (TextView) view.requireViewById(R.id.header_title);
        ControlsUtil.Companion.updateFontSize$default(ControlsUtil.Companion, textView, R.dimen.control_structure_header_text_size);
        this.title = textView;
        this.structureAll = (CheckBox) view.requireViewById(R.id.check_all_structure);
        FrameLayout frameLayout = (FrameLayout) view.requireViewById(R.id.check_all_structure_layout);
        this.structureAllLayout = frameLayout;
        this.selectAllItems = view.getContext().getString(R.string.controls_select_all_items);
        AccessibilityDelegateCompat accessibilityDelegateCompat = new AccessibilityDelegateCompat() { // from class: com.android.systemui.controls.management.adapter.StructureCustomHolder$accessibilityDelegate$1
            @Override // androidx.core.view.AccessibilityDelegateCompat
            public final void onInitializeAccessibilityNodeInfo(View view2, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                View view3 = view;
                if (Intrinsics.areEqual(view2, view3)) {
                    view3.setClickable(true);
                }
                this.mOriginalDelegate.onInitializeAccessibilityNodeInfo(view2, accessibilityNodeInfoCompat.mInfo);
                accessibilityNodeInfoCompat.setCollectionItemInfo(null);
            }

            @Override // androidx.core.view.AccessibilityDelegateCompat
            public final void onPopulateAccessibilityEvent(View view2, AccessibilityEvent accessibilityEvent) {
                super.onPopulateAccessibilityEvent(view2, accessibilityEvent);
                if (accessibilityEvent.getEventType() == 1) {
                    this.structureAllLayout.callOnClick();
                }
            }
        };
        view.setImportantForAccessibility(1);
        frameLayout.setImportantForAccessibility(2);
        ViewCompat.setAccessibilityDelegate(this.itemView, accessibilityDelegateCompat);
    }

    @Override // com.android.systemui.controls.management.adapter.CustomHolder
    public final void bindData(CustomElementWrapper customElementWrapper) {
        CustomStructureNameWrapper customStructureNameWrapper = (CustomStructureNameWrapper) customElementWrapper;
        this.structureName = customStructureNameWrapper.structureName;
        this.displayName = customStructureNameWrapper.displayName;
        View view = this.itemView;
        if (customStructureNameWrapper.needStructureName) {
            view.setVisibility(0);
            view.getLayoutParams().height = view.getResources().getDimensionPixelOffset(R.dimen.control_structure_height);
        } else {
            view.setVisibility(8);
            view.getLayoutParams().height = 0;
        }
        CharSequence charSequence = this.displayName;
        if (charSequence == null) {
            charSequence = null;
        }
        this.title.setText(charSequence);
        boolean z = customStructureNameWrapper.favorite;
        CheckBox checkBox = this.structureAll;
        checkBox.setChecked(z);
        CharSequence charSequence2 = this.displayName;
        checkBox.setContentDescription(this.selectAllItems + ", " + ((Object) (charSequence2 != null ? charSequence2 : null)));
        this.structureAllLayout.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.controls.management.adapter.StructureCustomHolder$bindData$2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                boolean z2 = !StructureCustomHolder.this.structureAll.isChecked();
                StructureCustomHolder structureCustomHolder = StructureCustomHolder.this;
                structureCustomHolder.structureAll.setChecked(z2);
                CharSequence charSequence3 = structureCustomHolder.structureName;
                if (charSequence3 == null) {
                    charSequence3 = null;
                }
                structureCustomHolder.favoriteCallback.invoke(charSequence3.toString(), Boolean.valueOf(z2));
            }
        });
    }

    @Override // com.android.systemui.controls.management.adapter.CustomHolder
    public final void updateFavorite(boolean z) {
        this.structureAll.setChecked(z);
    }
}
