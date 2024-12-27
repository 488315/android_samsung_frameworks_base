package com.android.server.chimera;

import java.util.Comparator;

public final /* synthetic */ class PolicyHandler$$ExternalSyntheticLambda4 implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        return Float.compare(((ChimeraAppInfo) obj2).finalScore, ((ChimeraAppInfo) obj).finalScore);
    }
}
