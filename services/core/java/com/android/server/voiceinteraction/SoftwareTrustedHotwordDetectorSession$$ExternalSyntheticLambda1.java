package com.android.server.voiceinteraction;

import android.media.AudioFormat;
import android.os.ParcelFileDescriptor;
import android.os.PersistableBundle;
import android.service.voice.IDspHotwordDetectionCallback;
import android.service.voice.ISandboxedDetectionService;

import com.android.internal.infra.ServiceConnector;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class SoftwareTrustedHotwordDetectorSession$$ExternalSyntheticLambda1
        implements ServiceConnector.VoidJob {
    public final /* synthetic */ IDspHotwordDetectionCallback f$0;

    public final void runNoResult(Object obj) {
        ((ISandboxedDetectionService) obj)
                .detectFromMicrophoneSource(
                        (ParcelFileDescriptor) null,
                        1,
                        (AudioFormat) null,
                        (PersistableBundle) null,
                        this.f$0);
    }
}
