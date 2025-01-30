package androidx.leanback.widget.picker;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.KeyEvent;
import androidx.core.view.ViewCompat;
import androidx.leanback.R$styleable;
import com.android.systemui.R;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class PinPicker extends Picker {
    public PinPicker(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.pinPickerStyle);
    }

    @Override // androidx.leanback.widget.picker.Picker, android.view.ViewGroup, android.view.View
    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        if (keyEvent.getAction() != 1 || keyCode < 7 || keyCode > 16) {
            return super.dispatchKeyEvent(keyEvent);
        }
        setColumnValue(this.mSelectedColumn, keyCode - 7, false);
        performClick();
        return true;
    }

    @Override // android.view.View
    public final boolean performClick() {
        int i = this.mSelectedColumn;
        if (i == getColumnsCount() - 1) {
            return super.performClick();
        }
        setSelectedColumn(i + 1);
        return false;
    }

    public PinPicker(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        int[] iArr = R$styleable.lbPinPicker;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, iArr, i, 0);
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api29Impl.saveAttributeDataForStyleable(this, context, iArr, attributeSet, obtainStyledAttributes, i, 0);
        try {
            List asList = Arrays.asList(" ");
            ((ArrayList) this.mSeparators).clear();
            ((ArrayList) this.mSeparators).addAll(asList);
            int i2 = obtainStyledAttributes.getInt(0, 4);
            ArrayList arrayList = new ArrayList(i2);
            for (int i3 = 0; i3 < i2; i3++) {
                PickerColumn pickerColumn = new PickerColumn();
                pickerColumn.mMinValue = 0;
                pickerColumn.mMaxValue = 9;
                pickerColumn.mLabelFormat = "%d";
                arrayList.add(pickerColumn);
            }
            setColumns(arrayList);
        } finally {
            obtainStyledAttributes.recycle();
        }
    }
}
