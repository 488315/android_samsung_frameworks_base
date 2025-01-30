package com.samsung.android.nexus.particle.emitter;

import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
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
