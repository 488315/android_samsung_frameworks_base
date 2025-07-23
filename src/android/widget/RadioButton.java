package android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.RemoteViews;
import com.android.internal.R;

@RemoteViews.RemoteView
/* loaded from: classes5.dex */
public class RadioButton extends CompoundButton {
    public RadioButton(Context context) {
        this(context, null);
    }

    public RadioButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842878);
    }

    public RadioButton(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public RadioButton(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    @Override // android.widget.CompoundButton, android.widget.Checkable
    public void toggle() {
        if (isChecked()) {
            return;
        }
        super.toggle();
    }

    @Override // android.widget.CompoundButton, android.widget.Button, android.widget.TextView, android.view.View
    public CharSequence getAccessibilityClassName() {
        return RadioButton.class.getName();
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        if (getParent() instanceof RadioGroup) {
            RadioGroup radioGroup = (RadioGroup) getParent();
            if (radioGroup.getOrientation() == 0) {
                accessibilityNodeInfo.setCollectionItemInfo(AccessibilityNodeInfo.CollectionItemInfo.obtain(0, 1, radioGroup.getIndexWithinVisibleButtons(this), 1, false, isChecked()));
            } else {
                accessibilityNodeInfo.setCollectionItemInfo(AccessibilityNodeInfo.CollectionItemInfo.obtain(radioGroup.getIndexWithinVisibleButtons(this), 1, 0, 1, false, isChecked()));
            }
        }
    }

    @Override // android.widget.CompoundButton
    protected CharSequence getButtonStateDescription() {
        if (isChecked()) {
            return getResources().getString(R.string.selected);
        }
        return getResources().getString(R.string.not_selected);
    }
}
