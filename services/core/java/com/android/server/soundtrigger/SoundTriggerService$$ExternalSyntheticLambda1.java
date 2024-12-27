package com.android.server.soundtrigger;

import android.hardware.soundtrigger.SoundTrigger;
import android.hardware.soundtrigger.SoundTriggerModule;
import android.media.permission.Identity;
import android.os.Looper;

import java.util.function.Function;

public final /* synthetic */ class SoundTriggerService$$ExternalSyntheticLambda1
        implements Function {
    public final /* synthetic */ SoundTriggerService f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ Identity f$2;
    public final /* synthetic */ Identity f$3;
    public final /* synthetic */ boolean f$4;

    public /* synthetic */ SoundTriggerService$$ExternalSyntheticLambda1(
            SoundTriggerService soundTriggerService,
            int i,
            Identity identity,
            Identity identity2,
            boolean z) {
        this.f$0 = soundTriggerService;
        this.f$1 = i;
        this.f$2 = identity;
        this.f$3 = identity2;
        this.f$4 = z;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        SoundTriggerService soundTriggerService = this.f$0;
        int i = this.f$1;
        Identity identity = this.f$2;
        Identity identity2 = this.f$3;
        boolean z = this.f$4;
        soundTriggerService.getClass();
        return new SoundTriggerModule(
                soundTriggerService.mMiddlewareService,
                i,
                (SoundTrigger.StatusListener) obj,
                Looper.getMainLooper(),
                identity,
                identity2,
                z);
    }
}
