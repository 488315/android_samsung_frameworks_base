package com.android.systemui.facewidget.plugin;

import com.android.systemui.Dependency;
import java.util.function.Supplier;

public final /* synthetic */ class FaceWidgetWallpaperUtilsWrapper$$ExternalSyntheticLambda0 implements Supplier {
    @Override // java.util.function.Supplier
    public final Object get() {
        return Integer.valueOf(((PluginFaceWidgetManager) Dependency.sDependency.getDependencyInner(PluginFaceWidgetManager.class)).mAppPluginVersion);
    }
}
