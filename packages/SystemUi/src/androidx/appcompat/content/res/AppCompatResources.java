package androidx.appcompat.content.res;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.appcompat.widget.ResourceManagerInternal;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AppCompatResources {
    private AppCompatResources() {
    }

    public static Drawable getDrawable(int i, Context context) {
        return ResourceManagerInternal.get().getDrawable(i, context);
    }
}
