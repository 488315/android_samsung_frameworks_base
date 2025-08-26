package androidx.appcompat.content.res;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.appcompat.widget.ResourceManagerInternal;

/* loaded from: classes.dex */
public final class AppCompatResources {
    private AppCompatResources() {
    }

    public static Drawable getDrawable(int i, Context context) {
        return ResourceManagerInternal.get().getDrawable(i, context);
    }
}
