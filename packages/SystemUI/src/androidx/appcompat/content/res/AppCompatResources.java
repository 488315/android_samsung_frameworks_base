package androidx.appcompat.content.res;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.appcompat.widget.ResourceManagerInternal;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class AppCompatResources {
    private AppCompatResources() {
    }

    public static Drawable getDrawable(int i, Context context) {
        return ResourceManagerInternal.get().getDrawable(i, context);
    }
}
