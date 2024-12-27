package com.android.systemui.volume;

import android.R;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import kotlin.Unit;
import kotlin.ranges.RangesKt___RangesKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class VolumeDialogSeekBarAccessibilityDelegate extends View.AccessibilityDelegate {
    public final int accessibilityStep;

    public VolumeDialogSeekBarAccessibilityDelegate(int i) {
        this.accessibilityStep = i;
    }

    @Override // android.view.View.AccessibilityDelegate
    public final boolean performAccessibilityAction(View view, int i, Bundle bundle) {
        if (!(view instanceof SeekBar)) {
            throw new IllegalArgumentException("This class only works with the SeekBar".toString());
        }
        SeekBar seekBar = (SeekBar) view;
        if (i != 4096 && i != 8192) {
            return super.performAccessibilityAction(view, i, bundle);
        }
        int i2 = this.accessibilityStep;
        if (i == 8192) {
            i2 = -i2;
        }
        Bundle bundle2 = new Bundle();
        bundle2.putFloat("android.view.accessibility.action.ARGUMENT_PROGRESS_VALUE", RangesKt___RangesKt.coerceIn(seekBar.getProgress() + i2, seekBar.getMin(), seekBar.getMax()));
        Unit unit = Unit.INSTANCE;
        return super.performAccessibilityAction(view, R.id.accessibilityActionSetProgress, bundle2);
    }
}
