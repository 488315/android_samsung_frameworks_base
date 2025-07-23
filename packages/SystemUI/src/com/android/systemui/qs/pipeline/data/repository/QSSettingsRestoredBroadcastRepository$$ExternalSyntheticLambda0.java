package com.android.systemui.qs.pipeline.data.repository;

import android.content.BroadcastReceiver;
import android.content.Intent;
import com.android.systemui.qs.pipeline.data.repository.QSSettingsRestoredBroadcastRepository;
import kotlin.Pair;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class QSSettingsRestoredBroadcastRepository$$ExternalSyntheticLambda0 implements Function2 {
    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        QSSettingsRestoredBroadcastRepository.Companion companion = QSSettingsRestoredBroadcastRepository.Companion;
        return new Pair((Intent) obj, Integer.valueOf(((BroadcastReceiver) obj2).getSendingUserId()));
    }
}
