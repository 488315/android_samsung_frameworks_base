package com.android.systemui.facewidget.plugin;

import com.android.systemui.Dependency;
import java.util.function.Supplier;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class FaceWidgetWallpaperUtilsWrapper$$ExternalSyntheticLambda0 implements Supplier {
    @Override // java.util.function.Supplier
    public final Object get() {
        return Integer.valueOf(((PluginFaceWidgetManager) Dependency.get(PluginFaceWidgetManager.class)).mAppPluginVersion);
    }
}
