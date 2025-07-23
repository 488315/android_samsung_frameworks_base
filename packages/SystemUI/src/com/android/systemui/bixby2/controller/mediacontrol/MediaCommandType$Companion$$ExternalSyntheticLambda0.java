package com.android.systemui.bixby2.controller.mediacontrol;

import android.media.session.MediaController;
import com.android.systemui.bixby2.controller.mediacontrol.MediaCommandType;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class MediaCommandType$Companion$$ExternalSyntheticLambda0 implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        boolean isMediaControlActive$lambda$0;
        isMediaControlActive$lambda$0 = MediaCommandType.Companion.isMediaControlActive$lambda$0((MediaController) obj);
        return Boolean.valueOf(isMediaControlActive$lambda$0);
    }
}
