package androidx.core.view;

import android.graphics.Rect;
import android.view.DisplayCutout;
import androidx.core.graphics.Insets;
import java.util.List;
import java.util.Objects;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DisplayCutoutCompat {
    public final DisplayCutout mDisplayCutout;

    public DisplayCutoutCompat(Insets insets, Rect rect, Rect rect2, Rect rect3, Rect rect4, Insets insets2) {
        this(new DisplayCutout(insets.toPlatformInsets(), rect, rect2, rect3, rect4, insets2.toPlatformInsets()));
    }

    public static DisplayCutoutCompat wrap(DisplayCutout displayCutout) {
        if (displayCutout == null) {
            return null;
        }
        return new DisplayCutoutCompat(displayCutout);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || DisplayCutoutCompat.class != obj.getClass()) {
            return false;
        }
        return Objects.equals(this.mDisplayCutout, ((DisplayCutoutCompat) obj).mDisplayCutout);
    }

    public final int hashCode() {
        DisplayCutout displayCutout = this.mDisplayCutout;
        if (displayCutout == null) {
            return 0;
        }
        return displayCutout.hashCode();
    }

    public final String toString() {
        return "DisplayCutoutCompat{" + this.mDisplayCutout + "}";
    }

    private DisplayCutoutCompat(DisplayCutout displayCutout) {
        this.mDisplayCutout = displayCutout;
    }

    public DisplayCutoutCompat(Rect rect, List<Rect> list) {
        this(new DisplayCutout(rect, list));
    }
}
