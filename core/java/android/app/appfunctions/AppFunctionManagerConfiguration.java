package android.app.appfunctions;

import android.content.Context;
import com.android.internal.hidden_from_bootclasspath.android.app.appfunctions.flags.Flags;

/* loaded from: classes.dex */
public class AppFunctionManagerConfiguration {
    public AppFunctionManagerConfiguration(Context context) {
    }

    public boolean isSupported() {
        return Flags.enableAppFunctionManager();
    }

    public static boolean isSupported(Context context) {
        return new AppFunctionManagerConfiguration(context).isSupported();
    }
}
