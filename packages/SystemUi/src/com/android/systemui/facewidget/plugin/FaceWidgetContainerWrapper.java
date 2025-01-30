package com.android.systemui.facewidget.plugin;

import android.content.Context;
import android.view.View;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.plugins.keyguardstatusview.PluginKeyguardStatusView;
import java.io.PrintWriter;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class FaceWidgetContainerWrapper implements Dumpable {
    public View mClockContainer;
    public List mContentsContainerList;
    public final Context mContext;
    public View mFaceWidgetContainer;
    public PluginKeyguardStatusView mPluginKeyguardStatusView;

    public FaceWidgetContainerWrapper(Context context) {
        this.mContext = context;
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        PluginKeyguardStatusView pluginKeyguardStatusView;
        PluginFaceWidgetManager pluginFaceWidgetManager = (PluginFaceWidgetManager) Dependency.get(PluginFaceWidgetManager.class);
        if (pluginFaceWidgetManager == null || (pluginKeyguardStatusView = pluginFaceWidgetManager.mFaceWidgetPlugin) == null) {
            return;
        }
        pluginKeyguardStatusView.dump(null, printWriter, strArr);
    }

    public final void initPlugin(PluginKeyguardStatusView pluginKeyguardStatusView, View view, List list) {
        this.mPluginKeyguardStatusView = pluginKeyguardStatusView;
        this.mFaceWidgetContainer = view;
        this.mContentsContainerList = list;
        if (list == null || list.size() <= 0) {
            this.mClockContainer = null;
        } else {
            this.mClockContainer = (View) this.mContentsContainerList.get(0);
        }
    }
}
