package androidx.core.content;

import android.content.Context;
import android.content.res.ColorStateList;
import androidx.core.content.res.ResourcesCompat;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class ContextCompat {
    public static final Object sLock = new Object();

    public static ColorStateList getColorStateList(int i, Context context) {
        return ResourcesCompat.getColorStateList(context.getResources(), i, context.getTheme());
    }
}
