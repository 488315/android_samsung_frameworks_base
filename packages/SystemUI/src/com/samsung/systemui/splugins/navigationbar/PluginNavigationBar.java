package com.samsung.systemui.splugins.navigationbar;

import com.samsung.systemui.splugins.SPlugin;
import com.samsung.systemui.splugins.annotations.ProvidesInterface;
import java.io.PrintWriter;

@ProvidesInterface(action = "com.samsung.systemui.navigationbar.PLUGIN", version = 11000)
/* loaded from: classes4.dex */
public interface PluginNavigationBar extends SPlugin {
    public static final String ACTION = "com.samsung.systemui.navigationbar.PLUGIN";
    public static final Companion Companion = Companion.$$INSTANCE;
    public static final int MAJOR_VERSION = 11;
    public static final int MINOR_VERSION = 0;
    public static final int VERSION = 11000;

    public final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();
        public static final String ACTION = "com.samsung.systemui.navigationbar.PLUGIN";
        public static final int MAJOR_VERSION = 11;
        public static final int MINOR_VERSION = 0;
        public static final int VERSION = 11000;

        private Companion() {
        }
    }

    void disconnect();

    @Override // com.samsung.systemui.splugins.SPlugin
    default int getVersion() {
        return 11000;
    }

    default void connect() {
    }

    default void dump(PrintWriter printWriter) {
    }

    default void onAttachedToWindow(ExtendableBar extendableBar) {
    }

    default void onDetachedFromWindow(ExtendableBar extendableBar) {
    }
}
