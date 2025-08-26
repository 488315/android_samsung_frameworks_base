package com.android.systemui.audio.soundcraft.interfaces.volume;

import com.android.systemui.Dependency;
import com.android.systemui.plugins.VolumeDialogController;
import kotlin.jvm.functions.Function0;

/* loaded from: classes.dex */
public final /* synthetic */ class VolumeManager$$ExternalSyntheticLambda0 implements Function0 {
    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        int i = VolumeManager.$r8$clinit;
        return (VolumeDialogController) Dependency.sDependency.getDependencyInner(VolumeDialogController.class);
    }
}
