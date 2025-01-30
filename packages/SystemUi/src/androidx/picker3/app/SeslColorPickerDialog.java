package androidx.picker3.app;

import android.content.Context;
import android.content.DialogInterface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import androidx.picker3.widget.SeslColorPicker;
import com.android.systemui.R;
import com.android.systemui.edgelighting.settings.EdgeLightingStyleActivity;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SeslColorPickerDialog extends AlertDialog implements DialogInterface.OnClickListener {
    public final SeslColorPicker mColorPicker;
    public final Integer mCurrentColor;
    public final OnColorSetListener mOnColorSetListener;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface OnColorSetListener {
    }

    public SeslColorPickerDialog(Context context, OnColorSetListener onColorSetListener, int i) {
        this(context, onColorSetListener);
        this.mColorPicker.mRecentColorInfo.mCurrentColor = Integer.valueOf(i);
        this.mCurrentColor = Integer.valueOf(i);
        this.mColorPicker.updateRecentColorLayout();
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        Integer num;
        if (i == -1) {
            getWindow().setSoftInputMode(3);
            SeslColorPicker seslColorPicker = this.mColorPicker;
            Integer num2 = seslColorPicker.mPickedColor.mColor;
            if (num2 != null) {
                seslColorPicker.mRecentColorInfo.mSelectedColor = Integer.valueOf(num2.intValue());
            }
            OnColorSetListener onColorSetListener = this.mOnColorSetListener;
            if (onColorSetListener != null) {
                SeslColorPicker seslColorPicker2 = this.mColorPicker;
                if (seslColorPicker2.mIsInputFromUser || (num = this.mCurrentColor) == null) {
                    ((EdgeLightingStyleActivity.C136210) onColorSetListener).onColorSet(seslColorPicker2.mRecentColorInfo.mSelectedColor.intValue());
                } else {
                    ((EdgeLightingStyleActivity.C136210) onColorSetListener).onColorSet(num.intValue());
                }
            }
        }
    }

    public SeslColorPickerDialog(Context context, OnColorSetListener onColorSetListener, int[] iArr) {
        this(context, onColorSetListener);
        this.mColorPicker.mRecentColorInfo.initRecentColorInfo(iArr);
        this.mColorPicker.updateRecentColorLayout();
    }

    public SeslColorPickerDialog(Context context, OnColorSetListener onColorSetListener, int i, int[] iArr, boolean z) {
        this(context, onColorSetListener);
        this.mColorPicker.mRecentColorInfo.initRecentColorInfo(iArr);
        this.mColorPicker.mRecentColorInfo.mCurrentColor = Integer.valueOf(i);
        this.mCurrentColor = Integer.valueOf(i);
        this.mColorPicker.updateRecentColorLayout();
        this.mColorPicker.initOpacitySeekBar(z);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public SeslColorPickerDialog(Context context, OnColorSetListener onColorSetListener) {
        super(context, r0.data != 0 ? 2132018557 : 2132018554);
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.isLightTheme, typedValue, true);
        this.mCurrentColor = null;
        Context context2 = getContext();
        View inflate = LayoutInflater.from(context2).inflate(R.layout.sesl_color_picker_oneui_3_dialog, (ViewGroup) null);
        AlertController alertController = this.mAlert;
        alertController.mView = inflate;
        alertController.mViewLayoutResId = 0;
        alertController.mViewSpacingSpecified = false;
        setButton(-1, context2.getString(R.string.sesl_picker_done), this);
        setButton(-2, context2.getString(R.string.sesl_picker_cancel), this);
        requestWindowFeature(1);
        getWindow().setSoftInputMode(16);
        this.mOnColorSetListener = onColorSetListener;
        this.mColorPicker = (SeslColorPicker) inflate.findViewById(R.id.sesl_color_picker_content_view);
    }
}
