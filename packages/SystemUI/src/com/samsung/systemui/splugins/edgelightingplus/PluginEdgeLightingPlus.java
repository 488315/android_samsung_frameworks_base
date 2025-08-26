package com.samsung.systemui.splugins.edgelightingplus;

import android.os.Bundle;
import com.samsung.systemui.splugins.SPlugin;
import com.samsung.systemui.splugins.annotations.ProvidesInterface;
import java.io.FileDescriptor;
import java.io.PrintWriter;

@ProvidesInterface(action = PluginEdgeLightingPlus.ACTION, version = PluginEdgeLightingPlus.VERSION)
/* loaded from: classes4.dex */
public interface PluginEdgeLightingPlus extends SPlugin {
    public static final String ACTION = "com.samsung.systemui.action.PLUGIN_EDGELIGHTING_PLUS";
    public static final int MAJOR_VERSION = 6;
    public static final int MINOR_VERSION = 0;
    public static final int VERSION = 6000;

    public interface Callback {
        void sendEffectInfo(Bundle bundle);

        void showPreview(Bundle bundle);
    }

    void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr);

    void setCallback(Callback callback);
}
