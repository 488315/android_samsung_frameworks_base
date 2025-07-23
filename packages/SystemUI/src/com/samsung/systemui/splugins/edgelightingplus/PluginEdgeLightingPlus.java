package com.samsung.systemui.splugins.edgelightingplus;

import android.os.Bundle;
import com.samsung.systemui.splugins.SPlugin;
import com.samsung.systemui.splugins.annotations.ProvidesInterface;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
@ProvidesInterface(action = PluginEdgeLightingPlus.ACTION, version = PluginEdgeLightingPlus.VERSION)
/* loaded from: classes4.dex */
public interface PluginEdgeLightingPlus extends SPlugin {
    public static final String ACTION = "com.samsung.systemui.action.PLUGIN_EDGELIGHTING_PLUS";
    public static final int MAJOR_VERSION = 6;
    public static final int MINOR_VERSION = 0;
    public static final int VERSION = 6000;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Callback {
        void sendEffectInfo(Bundle bundle);

        void showPreview(Bundle bundle);
    }

    void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr);

    void setCallback(Callback callback);
}
