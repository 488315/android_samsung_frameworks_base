package com.android.server.companion.virtual.camera;

import android.companion.virtual.camera.VirtualCameraStreamConfig;
import android.companion.virtualcamera.SupportedStreamConfiguration;

import java.util.function.Function;

public final /* synthetic */ class VirtualCameraConversionUtil$$ExternalSyntheticLambda0
        implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        VirtualCameraStreamConfig virtualCameraStreamConfig = (VirtualCameraStreamConfig) obj;
        SupportedStreamConfiguration supportedStreamConfiguration =
                new SupportedStreamConfiguration();
        supportedStreamConfiguration.height = virtualCameraStreamConfig.getHeight();
        supportedStreamConfiguration.width = virtualCameraStreamConfig.getWidth();
        int format = virtualCameraStreamConfig.getFormat();
        int i = 1;
        if (format != 1) {
            i = 35;
            if (format != 35) {
                i = 0;
            }
        }
        supportedStreamConfiguration.pixelFormat = i;
        supportedStreamConfiguration.maxFps = virtualCameraStreamConfig.getMaximumFramesPerSecond();
        return supportedStreamConfiguration;
    }
}
