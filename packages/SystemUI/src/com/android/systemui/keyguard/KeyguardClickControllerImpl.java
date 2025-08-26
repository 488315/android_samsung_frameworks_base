package com.android.systemui.keyguard;

import com.android.systemui.Dependency;
import com.android.systemui.facewidget.plugin.PluginFaceWidgetManager;
import kotlin.jvm.functions.Function2;

/* loaded from: classes2.dex */
public final class KeyguardClickControllerImpl implements KeyguardClickController {
    public final PluginFaceWidgetManager manager = (PluginFaceWidgetManager) Dependency.sDependency.getDependencyInner(PluginFaceWidgetManager.class);
    public Function2 isClickContainerArea = new KeyguardClickControllerImpl$$ExternalSyntheticLambda0();
}
