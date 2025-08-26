package com.android.systemui.bixby2.controller.mediacontrol;

import android.media.session.MediaController;
import com.android.systemui.bixby2.controller.mediacontrol.MediaCommandType;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public final /* synthetic */ class MediaCommandType$Companion$$ExternalSyntheticLambda0 implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        return Boolean.valueOf(MediaCommandType.Companion.isMediaControlActive$lambda$0((MediaController) obj));
    }
}
