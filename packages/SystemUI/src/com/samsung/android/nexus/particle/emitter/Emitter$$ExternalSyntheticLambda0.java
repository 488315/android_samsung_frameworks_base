package com.samsung.android.nexus.particle.emitter;

import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final /* synthetic */ class Emitter$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ World f$0;

    public /* synthetic */ Emitter$$ExternalSyntheticLambda0(World world) {
        this.f$0 = world;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        World world = this.f$0;
        Emitter emitter = (Emitter) obj;
        emitter.mWorld = world;
        emitter.mEmitters.forEach(new Emitter$$ExternalSyntheticLambda0(world));
    }
}
