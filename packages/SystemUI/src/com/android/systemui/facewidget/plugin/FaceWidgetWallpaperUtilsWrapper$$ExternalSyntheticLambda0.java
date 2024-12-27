package com.android.systemui.facewidget.plugin;

import com.android.systemui.Dependency;
import java.util.function.Supplier;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class FaceWidgetWallpaperUtilsWrapper$$ExternalSyntheticLambda0 implements Supplier {
    @Override // java.util.function.Supplier
    public final Object get() {
        return Integer.valueOf(((PluginFaceWidgetManager) Dependency.sDependency.getDependencyInner(PluginFaceWidgetManager.class)).mAppPluginVersion);
    }
}
