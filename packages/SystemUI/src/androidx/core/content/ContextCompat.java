package androidx.core.content;

import android.content.Context;
import android.content.res.ColorStateList;
import androidx.core.content.res.ResourcesCompat;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class ContextCompat {
    public static ColorStateList getColorStateList(int i, Context context) {
        return ResourcesCompat.getColorStateList(i, context.getTheme(), context.getResources());
    }
}
