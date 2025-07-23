package com.android.systemui.keyguard;

import com.android.systemui.Dependency;
import com.android.systemui.facewidget.plugin.PluginFaceWidgetManager;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class KeyguardClickControllerImpl implements KeyguardClickController {
    public final PluginFaceWidgetManager manager = (PluginFaceWidgetManager) Dependency.sDependency.getDependencyInner(PluginFaceWidgetManager.class);
    public Function2 isClickContainerArea = new KeyguardClickControllerImpl$$ExternalSyntheticLambda0();
}
