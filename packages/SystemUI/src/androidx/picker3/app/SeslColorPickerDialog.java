package androidx.picker3.app;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import androidx.picker3.widget.SeslColorPicker;
import com.android.systemui.R;
import com.android.systemui.edgelighting.settings.EdgeLightingStyleActivity;

/* loaded from: classes.dex */
public class SeslColorPickerDialog extends AlertDialog implements DialogInterface.OnClickListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final SeslColorPicker mColorPicker;
    public Integer mCurrentColor;
    public final OnColorSetListener mOnColorSetListener;

    public interface OnColorSetListener {
    }

    public SeslColorPickerDialog(Context context, OnColorSetListener onColorSetListener, int i) {
        this(context, onColorSetListener);
        this.mColorPicker.mRecentColorInfo.mCurrentColor = Integer.valueOf(i);
        this.mCurrentColor = Integer.valueOf(i);
        this.mColorPicker.updateRecentColorLayout();
    }

    public static Activity scanForActivity(Context context) {
        if (context instanceof Activity) {
            return (Activity) context;
        }
        if (context instanceof ContextWrapper) {
            return scanForActivity(((ContextWrapper) context).getBaseContext());
        }
        return null;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        Integer num;
        if (i == -1) {
            getWindow().setSoftInputMode(3);
            SeslColorPicker seslColorPicker = this.mColorPicker;
            Integer num2 = seslColorPicker.mPickedColor.mColor;
            if (num2 != null) {
                seslColorPicker.mRecentColorInfo.mSelectedColor = num2;
            }
            Integer num3 = seslColorPicker.mRecentColorInfo.mSelectedColor;
            OnColorSetListener onColorSetListener = this.mOnColorSetListener;
            if (onColorSetListener != null) {
                if (!seslColorPicker.mIsInputFromUser && (num = this.mCurrentColor) != null) {
                    ((EdgeLightingStyleActivity.AnonymousClass10) onColorSetListener).onColorSet(num.intValue());
                } else if (num3 != null) {
                    ((EdgeLightingStyleActivity.AnonymousClass10) onColorSetListener).onColorSet(num3.intValue());
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

    public SeslColorPickerDialog(Context context, OnColorSetListener onColorSetListener) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.isLightTheme, typedValue, true);
        super(context, typedValue.data != 0 ? R.style.ThemeOverlay_AppCompat_Light_Dialog : 2132018967);
        this.mCurrentColor = null;
        Activity activityScanForActivity = scanForActivity(context);
        Context context2 = getContext();
        View viewInflate = LayoutInflater.from(context2).inflate(R.layout.sesl_color_picker_oneui_3_dialog, (ViewGroup) null);
        AlertController alertController = this.mAlert;
        alertController.mView = viewInflate;
        alertController.mViewLayoutResId = 0;
        alertController.mViewSpacingSpecified = false;
        setButton(-1, context2.getString(R.string.sesl_picker_done), this);
        setButton(-2, context2.getString(R.string.sesl_picker_cancel), this);
        this.mAlert.mIsBlurEnabled = true;
        requestWindowFeature(1);
        final Window window = getWindow();
        if (window != null) {
            window.setSoftInputMode(16);
            window.getDecorView().setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener(this) { // from class: androidx.picker3.app.SeslColorPickerDialog.1
                @Override // android.view.View.OnApplyWindowInsetsListener
                public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                    WindowManager.LayoutParams attributes = window.getAttributes();
                    attributes.layoutInDisplayCutoutMode = 2;
                    window.setAttributes(attributes);
                    return windowInsets.consumeSystemWindowInsets();
                }
            });
        }
        this.mOnColorSetListener = onColorSetListener;
        SeslColorPicker seslColorPicker = (SeslColorPicker) viewInflate.findViewById(R.id.sesl_color_picker_content_view);
        this.mColorPicker = seslColorPicker;
        View viewFindViewById = seslColorPicker.findViewById(R.id.sesl_last_used_color_slot);
        seslColorPicker.mEyeDropperView.setVisibility(8);
        viewFindViewById.setVisibility(0);
        seslColorPicker.mOnEyeDropperListener = new SeslColorPickerDialog$$ExternalSyntheticLambda0(this, context, activityScanForActivity);
    }
}
