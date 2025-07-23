package com.android.systemui.qs.pipeline.dagger;

import androidx.datastore.core.CorruptionException;
import androidx.datastore.preferences.core.MutablePreferences;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class QSPipelineModule$Companion$$ExternalSyntheticLambda0 implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        ((CorruptionException) obj).printStackTrace();
        return new MutablePreferences(null, true, 1, null);
    }
}
