package com.android.systemui.qs.pipeline.dagger;

import androidx.datastore.core.CorruptionException;
import androidx.datastore.preferences.core.MutablePreferences;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final /* synthetic */ class QSPipelineModule$Companion$$ExternalSyntheticLambda0 implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        ((CorruptionException) obj).printStackTrace();
        return new MutablePreferences(null, true, 1, null);
    }
}
