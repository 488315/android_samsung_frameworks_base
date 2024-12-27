package com.android.server.soundtrigger_middleware;

import android.media.soundtrigger.Properties;
import android.media.soundtrigger_middleware.SoundTriggerModuleDescriptor;
import com.android.server.soundtrigger_middleware.SoundTriggerMiddlewareLogging;
import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class SoundTriggerMiddlewareLogging$$ExternalSyntheticLambda1 implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        SoundTriggerModuleDescriptor soundTriggerModuleDescriptor = (SoundTriggerModuleDescriptor) obj;
        int i = soundTriggerModuleDescriptor.handle;
        Properties properties = soundTriggerModuleDescriptor.properties;
        String str = properties.implementor;
        int i2 = properties.version;
        SoundTriggerMiddlewareLogging.ModulePropertySummary modulePropertySummary = new SoundTriggerMiddlewareLogging.ModulePropertySummary();
        modulePropertySummary.mId = i;
        modulePropertySummary.mImplementor = str;
        modulePropertySummary.mVersion = i2;
        return modulePropertySummary;
    }
}
