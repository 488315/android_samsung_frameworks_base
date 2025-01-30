package com.samsung.systemui.splugins.navigationbar;

import com.samsung.systemui.splugins.SPlugin;
import com.samsung.systemui.splugins.annotations.ProvidesInterface;
import java.io.PrintWriter;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@ProvidesInterface(action = "com.samsung.systemui.navigationbar.PLUGIN", version = 9000)
/* loaded from: classes3.dex */
public interface PluginNavigationBar extends SPlugin {
    public static final String ACTION = "com.samsung.systemui.navigationbar.PLUGIN";
    public static final Companion Companion = Companion.$$INSTANCE;
    public static final int MAJOR_VERSION = 9;
    public static final int MINOR_VERSION = 0;
    public static final int VERSION = 9000;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();
        public static final String ACTION = "com.samsung.systemui.navigationbar.PLUGIN";
        public static final int MAJOR_VERSION = 9;
        public static final int MINOR_VERSION = 0;
        public static final int VERSION = 9000;

        private Companion() {
        }
    }

    void connect();

    void disconnect();

    void dump(PrintWriter printWriter);

    @Override // com.samsung.systemui.splugins.SPlugin
    int getVersion();

    void onAttachedToWindow(ExtendableBar extendableBar);

    void onDetachedFromWindow(ExtendableBar extendableBar);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static final class DefaultImpls {
        public static int getVersion(PluginNavigationBar pluginNavigationBar) {
            return 9000;
        }

        public static void connect(PluginNavigationBar pluginNavigationBar) {
        }

        public static void dump(PluginNavigationBar pluginNavigationBar, PrintWriter printWriter) {
        }

        public static void onAttachedToWindow(PluginNavigationBar pluginNavigationBar, ExtendableBar extendableBar) {
        }

        public static void onDetachedFromWindow(PluginNavigationBar pluginNavigationBar, ExtendableBar extendableBar) {
        }
    }
}
