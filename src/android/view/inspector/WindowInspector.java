package android.view.inspector;

import android.view.View;
import android.view.WindowManagerGlobal;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* loaded from: classes4.dex */
public final class WindowInspector {
    private WindowInspector() {
    }

    public static List<View> getGlobalWindowViews() {
        return WindowManagerGlobal.getInstance().getWindowViews();
    }

    public static void addGlobalWindowViewsListener(Executor executor, Consumer<List<View>> consumer) {
        WindowManagerGlobal.getInstance().addWindowViewsListener(executor, consumer);
    }

    public static void removeGlobalWindowViewsListener(Consumer<List<View>> consumer) {
        WindowManagerGlobal.getInstance().removeWindowViewsListener(consumer);
    }
}
