package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import androidx.appcompat.util.SeslMisc;
import androidx.core.content.res.ResourcesCompat;
import com.android.systemui.R;
import com.sec.ims.volte2.data.VolteConstants;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class SeslDropDownItemTextView extends SeslCheckedTextView {
    public SeslDropDownItemTextView(Context context) {
        this(context, null);
    }

    @Override // androidx.appcompat.widget.SeslCheckedTextView, android.widget.Checkable
    public final void setChecked(boolean z) {
        Context context;
        super.setChecked(z);
        setTypeface(Typeface.create(Typeface.create("sec", 0), z ? VolteConstants.ErrorCode.BUSY_EVERYWHERE : 400, false));
        if (z && (context = getContext()) != null && getCurrentTextColor() == -65281) {
            Log.w("SeslDropDownItemTextView", "text color reload!");
            ColorStateList colorStateList = ResourcesCompat.getColorStateList(context.getResources(), SeslMisc.isLightTheme(context) ? R.color.sesl_spinner_dropdown_text_color_light : R.color.sesl_spinner_dropdown_text_color_dark, context.getTheme());
            if (colorStateList != null) {
                setTextColor(colorStateList);
            } else {
                Log.w("SeslDropDownItemTextView", "Didn't set SeslDropDownItemTextView text color!!");
            }
        }
    }

    public SeslDropDownItemTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, android.R.attr.textViewStyle);
    }

    public SeslDropDownItemTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }
}
