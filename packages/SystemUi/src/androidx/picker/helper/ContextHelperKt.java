package androidx.picker.helper;

import android.content.Context;
import android.util.TypedValue;
import androidx.appcompat.widget.MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0;
import androidx.core.content.ContextCompat;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class ContextHelperKt {
    public static final int getPrimaryColor(Context context) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        int i = typedValue.resourceId;
        if (i == 0) {
            return typedValue.data;
        }
        Object obj = ContextCompat.sLock;
        return context.getColor(i);
    }

    public static final boolean isRTL(Context context) {
        return MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0.m11m(context) == 1;
    }
}
