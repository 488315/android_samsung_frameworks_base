package androidx.core.view;

import android.view.View;
import android.view.Window;
import android.view.WindowInsetsController;
import androidx.collection.SimpleArrayMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class WindowInsetsControllerCompat {
    public final Impl30 mImpl;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class Impl {
    }

    @Deprecated
    private WindowInsetsControllerCompat(WindowInsetsController windowInsetsController) {
        this.mImpl = new Impl30(windowInsetsController, this);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Impl30 extends Impl {
        public final WindowInsetsController mInsetsController;
        public final Window mWindow;

        public Impl30(Window window, WindowInsetsControllerCompat windowInsetsControllerCompat) {
            this(window.getInsetsController(), windowInsetsControllerCompat);
            this.mWindow = window;
        }

        public Impl30(WindowInsetsController windowInsetsController, WindowInsetsControllerCompat windowInsetsControllerCompat) {
            new SimpleArrayMap();
            this.mInsetsController = windowInsetsController;
        }
    }

    public WindowInsetsControllerCompat(Window window, View view) {
        this.mImpl = new Impl30(window, this);
    }
}
