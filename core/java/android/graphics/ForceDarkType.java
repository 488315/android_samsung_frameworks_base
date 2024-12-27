package android.graphics;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public class ForceDarkType {
    public static final int FORCE_DARK = 1;
    public static final int FORCE_INVERT_COLOR_DARK = 2;
    public static final int NONE = 0;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ForceDarkTypeDef {
    }
}
