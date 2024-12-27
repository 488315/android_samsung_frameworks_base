package com.android.server.voiceinteraction;

import android.service.voice.ISandboxedDetectionService;

import com.android.internal.infra.ServiceConnector;

public final /* synthetic */ class SoftwareTrustedHotwordDetectorSession$$ExternalSyntheticLambda0
        implements ServiceConnector.VoidJob {
    public final void runNoResult(Object obj) {
        ((ISandboxedDetectionService) obj).stopDetection();
    }
}
