package com.android.systemui.facewidget.plugin;

import com.android.systemui.Dependency;
import java.util.function.Supplier;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class FaceWidgetWallpaperUtilsWrapper$$ExternalSyntheticLambda0 implements Supplier {
    @Override // java.util.function.Supplier
    public final Object get() {
        return Integer.valueOf(((PluginFaceWidgetManager) Dependency.sDependency.getDependencyInner(PluginFaceWidgetManager.class)).mAppPluginVersion);
    }
}
