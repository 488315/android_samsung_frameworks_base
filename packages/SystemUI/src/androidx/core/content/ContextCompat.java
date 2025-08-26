package androidx.core.content;

import android.content.Context;
import android.content.res.ColorStateList;
import androidx.core.content.res.ResourcesCompat;

/* loaded from: classes.dex */
public class ContextCompat {
    public static ColorStateList getColorStateList(int i, Context context) {
        return ResourcesCompat.getColorStateList(i, context.getTheme(), context.getResources());
    }
}
