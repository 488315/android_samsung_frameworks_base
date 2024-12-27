package com.android.server.soundtrigger_middleware;

import android.media.soundtrigger.Properties;
import android.media.soundtrigger_middleware.SoundTriggerModuleDescriptor;

import java.util.function.Function;

public final /* synthetic */ class SoundTriggerMiddlewareLogging$$ExternalSyntheticLambda1
        implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        SoundTriggerModuleDescriptor soundTriggerModuleDescriptor =
                (SoundTriggerModuleDescriptor) obj;
        int i = soundTriggerModuleDescriptor.handle;
        Properties properties = soundTriggerModuleDescriptor.properties;
        String str = properties.implementor;
        int i2 = properties.version;
        SoundTriggerMiddlewareLogging.ModulePropertySummary modulePropertySummary =
                new SoundTriggerMiddlewareLogging.ModulePropertySummary();
        modulePropertySummary.mId = i;
        modulePropertySummary.mImplementor = str;
        modulePropertySummary.mVersion = i2;
        return modulePropertySummary;
    }
}
