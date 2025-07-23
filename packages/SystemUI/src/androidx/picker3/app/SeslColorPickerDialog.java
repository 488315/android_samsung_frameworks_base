package androidx.picker3.app;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import androidx.picker3.widget.SeslColorPicker;
import com.android.systemui.edgelighting.settings.EdgeLightingStyleActivity;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class SeslColorPickerDialog extends AlertDialog implements DialogInterface.OnClickListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final SeslColorPicker mColorPicker;
    public Integer mCurrentColor;
    public final OnColorSetListener mOnColorSetListener;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public SeslColorPickerDialog(android.content.Context r8, androidx.picker3.app.SeslColorPickerDialog.OnColorSetListener r9) {
        /*
            r7 = this;
            android.util.TypedValue r0 = new android.util.TypedValue
            r0.<init>()
            android.content.res.Resources$Theme r1 = r8.getTheme()
            r2 = 2130969452(0x7f04036c, float:1.7547586E38)
            r3 = 1
            r1.resolveAttribute(r2, r0, r3)
            int r0 = r0.data
            if (r0 == 0) goto L18
            r0 = 2132018970(0x7f14071a, float:1.9676262E38)
            goto L1b
        L18:
            r0 = 2132018967(0x7f140717, float:1.9676256E38)
        L1b:
            r7.<init>(r8, r0)
            r0 = 0
            r7.mCurrentColor = r0
            android.app.Activity r1 = scanForActivity(r8)
            android.content.Context r2 = r7.getContext()
            android.view.LayoutInflater r4 = android.view.LayoutInflater.from(r2)
            r5 = 2131559447(0x7f0d0417, float:1.8744238E38)
            android.view.View r0 = r4.inflate(r5, r0)
            androidx.appcompat.app.AlertController r4 = r7.mAlert
            r4.mView = r0
            r5 = 0
            r4.mViewLayoutResId = r5
            r4.mViewSpacingSpecified = r5
            r4 = 2131956566(0x7f131356, float:1.9549691E38)
            java.lang.String r4 = r2.getString(r4)
            r6 = -1
            r7.setButton(r6, r4, r7)
            r4 = 2131956565(0x7f131355, float:1.954969E38)
            java.lang.String r2 = r2.getString(r4)
            r4 = -2
            r7.setButton(r4, r2, r7)
            androidx.appcompat.app.AlertController r2 = r7.mAlert
            r2.mIsBlurEnabled = r3
            r7.requestWindowFeature(r3)
            android.view.Window r2 = r7.getWindow()
            if (r2 == 0) goto L71
            r3 = 16
            r2.setSoftInputMode(r3)
            android.view.View r3 = r2.getDecorView()
            androidx.picker3.app.SeslColorPickerDialog$1 r4 = new androidx.picker3.app.SeslColorPickerDialog$1
            r4.<init>(r7)
            r3.setOnApplyWindowInsetsListener(r4)
        L71:
            r7.mOnColorSetListener = r9
            r9 = 2131364777(0x7f0a0ba9, float:1.83494E38)
            android.view.View r9 = r0.findViewById(r9)
            androidx.picker3.widget.SeslColorPicker r9 = (androidx.picker3.widget.SeslColorPicker) r9
            r7.mColorPicker = r9
            r0 = 2131364807(0x7f0a0bc7, float:1.8349461E38)
            android.view.View r0 = r9.findViewById(r0)
            androidx.appcompat.widget.AppCompatImageView r2 = r9.mEyeDropperView
            r3 = 8
            r2.setVisibility(r3)
            r0.setVisibility(r5)
            androidx.picker3.app.SeslColorPickerDialog$$ExternalSyntheticLambda0 r0 = new androidx.picker3.app.SeslColorPickerDialog$$ExternalSyntheticLambda0
            r0.<init>(r7, r8, r1)
            r9.mOnEyeDropperListener = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.picker3.app.SeslColorPickerDialog.<init>(android.content.Context, androidx.picker3.app.SeslColorPickerDialog$OnColorSetListener):void");
    }
}
