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
import com.android.systemui.controls.management.model.SecElementWrapper;
import com.android.systemui.controls.management.model.SecStructureNameWrapper;
import com.android.systemui.controls.util.ControlsUtil;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SecStructureHolder extends SecHolder {
    public CharSequence displayName;
    public final Function2 favoriteCallback;
    public final String selectAllItems;
    public final CheckBox structureAll;
    public final FrameLayout structureAllLayout;
    public CharSequence structureName;
    public final TextView title;

    public SecStructureHolder(final View view, Function2 function2) {
        super(view, null);
        this.favoriteCallback = function2;
        TextView textView = (TextView) view.requireViewById(R.id.header_title);
        ControlsUtil.Companion.updateFontSize$default(ControlsUtil.Companion, textView, R.dimen.control_structure_header_text_size);
        this.title = textView;
        this.structureAll = (CheckBox) view.requireViewById(R.id.check_all_structure);
        FrameLayout frameLayout = (FrameLayout) view.requireViewById(R.id.check_all_structure_layout);
        this.structureAllLayout = frameLayout;
        this.selectAllItems = view.getContext().getString(R.string.controls_select_all_items);
        AccessibilityDelegateCompat accessibilityDelegateCompat = new AccessibilityDelegateCompat() { // from class: com.android.systemui.controls.management.adapter.SecStructureHolder$accessibilityDelegate$1
            @Override // androidx.core.view.AccessibilityDelegateCompat
            public final void onInitializeAccessibilityNodeInfo(View view2, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                if (view2.equals(view)) {
                    view.setClickable(true);
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

    @Override // com.android.systemui.controls.management.adapter.SecHolder
    public final void bindData(SecElementWrapper secElementWrapper) {
        SecStructureNameWrapper secStructureNameWrapper = (SecStructureNameWrapper) secElementWrapper;
        this.structureName = secStructureNameWrapper.structureName;
        this.displayName = secStructureNameWrapper.displayName;
        View view = this.itemView;
        if (secStructureNameWrapper.needStructureName) {
            view.setVisibility(0);
            view.getLayoutParams().height = view.getResources().getDimensionPixelOffset(R.dimen.control_structure_height);
        } else {
            view.setVisibility(8);
            view.getLayoutParams().height = 0;
        }
        TextView textView = this.title;
        CharSequence charSequence = this.displayName;
        if (charSequence == null) {
            charSequence = null;
        }
        textView.setText(charSequence);
        this.structureAll.setChecked(secStructureNameWrapper.favorite);
        CheckBox checkBox = this.structureAll;
        CharSequence charSequence2 = this.displayName;
        checkBox.setContentDescription(this.selectAllItems + ", " + ((Object) (charSequence2 != null ? charSequence2 : null)));
        this.structureAllLayout.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.controls.management.adapter.SecStructureHolder$bindData$2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                boolean z = !SecStructureHolder.this.structureAll.isChecked();
                SecStructureHolder secStructureHolder = SecStructureHolder.this;
                secStructureHolder.structureAll.setChecked(z);
                CharSequence charSequence3 = secStructureHolder.structureName;
                if (charSequence3 == null) {
                    charSequence3 = null;
                }
                secStructureHolder.favoriteCallback.invoke(charSequence3.toString(), Boolean.valueOf(z));
            }
        });
    }

    @Override // com.android.systemui.controls.management.adapter.SecHolder
    public final void updateFavorite(boolean z) {
        this.structureAll.setChecked(z);
    }
}
