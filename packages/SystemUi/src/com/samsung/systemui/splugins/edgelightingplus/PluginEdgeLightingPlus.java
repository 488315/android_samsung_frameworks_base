package com.samsung.systemui.splugins.edgelightingplus;

import android.os.Bundle;
import com.samsung.systemui.splugins.SPlugin;
import com.samsung.systemui.splugins.annotations.ProvidesInterface;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@ProvidesInterface(action = PluginEdgeLightingPlus.ACTION, version = PluginEdgeLightingPlus.VERSION)
/* loaded from: classes3.dex */
public interface PluginEdgeLightingPlus extends SPlugin {
    public static final String ACTION = "com.samsung.systemui.action.PLUGIN_EDGELIGHTING_PLUS";
    public static final int MAJOR_VERSION = 6;
    public static final int MINOR_VERSION = 0;
    public static final int VERSION = 6000;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Callback {
        void sendEffectInfo(Bundle bundle);

        void showPreview(Bundle bundle);
    }

    void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr);

    void setCallback(Callback callback);
}
