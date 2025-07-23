package android.window;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes5.dex */
public interface SystemOverrideOnBackInvokedCallback extends OnBackInvokedCallback {
    public static final int OVERRIDE_FINISH_AND_REMOVE_TASK = 2;
    public static final int OVERRIDE_MOVE_TASK_TO_BACK = 1;
    public static final int OVERRIDE_UNDEFINED = 0;

    @Retention(RetentionPolicy.SOURCE)
    public @interface OverrideBehavior {
    }

    default int overrideBehavior() {
        return 0;
    }
}
