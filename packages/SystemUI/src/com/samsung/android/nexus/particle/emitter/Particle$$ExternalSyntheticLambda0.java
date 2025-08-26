package com.samsung.android.nexus.particle.emitter;

import com.samsung.android.nexus.particle.emitter.Particle;
import java.util.Comparator;

/* loaded from: classes4.dex */
public final /* synthetic */ class Particle$$ExternalSyntheticLambda0 implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        Particle$$ExternalSyntheticLambda0 particle$$ExternalSyntheticLambda0 = Particle.mEmitterScheduleComparator;
        return Long.compare(((Particle.EmitterSchedule) obj2).nextTime, ((Particle.EmitterSchedule) obj).nextTime);
    }
}
