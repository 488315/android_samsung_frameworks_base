package com.android.server.soundtrigger;

import android.hardware.soundtrigger.ConversionUtil;
import android.media.soundtrigger_middleware.SoundTriggerModuleDescriptor;

import java.util.function.Function;

public final /* synthetic */ class SoundTriggerService$$ExternalSyntheticLambda0
        implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return ConversionUtil.aidl2apiModuleDescriptor((SoundTriggerModuleDescriptor) obj);
    }
}
