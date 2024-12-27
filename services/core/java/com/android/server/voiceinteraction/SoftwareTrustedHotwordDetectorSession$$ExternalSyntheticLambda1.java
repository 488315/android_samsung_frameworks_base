package com.android.server.voiceinteraction;

import android.media.AudioFormat;
import android.os.ParcelFileDescriptor;
import android.os.PersistableBundle;
import android.service.voice.IDspHotwordDetectionCallback;
import android.service.voice.ISandboxedDetectionService;

import com.android.internal.infra.ServiceConnector;

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
