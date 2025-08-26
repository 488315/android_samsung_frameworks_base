package com.android.systemui.media.controls.domain.pipeline;

import com.android.systemui.media.controls.domain.pipeline.MediaDeviceManager;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* loaded from: classes2.dex */
public final /* synthetic */ class MediaDeviceManager$intentReceiver$1$$ExternalSyntheticLambda0 implements Function2 {
    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        MediaDeviceManager.Entry entry = (MediaDeviceManager.Entry) obj2;
        int i = MediaDeviceManager$intentReceiver$1.$r8$clinit;
        entry.stop();
        MediaDeviceManager mediaDeviceManager = MediaDeviceManager.this;
        mediaDeviceManager.bgExecutor.execute(new MediaDeviceManager$Entry$start$1(entry, mediaDeviceManager));
        return Unit.INSTANCE;
    }
}
