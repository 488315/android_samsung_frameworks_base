package com.android.systemui.audio.soundcraft.interfaces.volume;

import com.android.systemui.Dependency;
import com.android.systemui.plugins.VolumeDialogController;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class VolumeManager$$ExternalSyntheticLambda0 implements Function0 {
    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        int i = VolumeManager.$r8$clinit;
        return (VolumeDialogController) Dependency.sDependency.getDependencyInner(VolumeDialogController.class);
    }
}
