package androidx.compose.ui.graphics;

import java.util.List;

/* loaded from: classes.dex */
public abstract class AndroidShader_androidKt {
    public static final void validateColorStops(List list, List list2) {
        if (list2 == null) {
            if (list.size() < 2) {
                throw new IllegalArgumentException("colors must have length of at least 2 if colorStops is omitted.");
            }
        } else if (list.size() != list2.size()) {
            throw new IllegalArgumentException("colors and colorStops arguments must have equal length.");
        }
    }
}
