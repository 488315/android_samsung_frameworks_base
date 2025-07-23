package com.android.systemui.volume.dialog.ringer.data.repository;

import android.content.Context;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class VolumeDialogRingerFeedbackRepositoryImpl implements VolumeDialogRingerFeedbackRepository {
    public final Context applicationContext;
    public final CoroutineDispatcher backgroundDispatcher;

    public VolumeDialogRingerFeedbackRepositoryImpl(Context context, CoroutineDispatcher coroutineDispatcher) {
        this.applicationContext = context;
        this.backgroundDispatcher = coroutineDispatcher;
    }

    public final Object getToastCount(Continuation continuation) {
        return BuildersKt.withContext(this.backgroundDispatcher, new VolumeDialogRingerFeedbackRepositoryImpl$getToastCount$2(this, null), continuation);
    }

    public final Object updateToastCount(int i, Continuation continuation) {
        Object withContext = BuildersKt.withContext(this.backgroundDispatcher, new VolumeDialogRingerFeedbackRepositoryImpl$updateToastCount$2(this, i, null), continuation);
        return withContext == CoroutineSingletons.COROUTINE_SUSPENDED ? withContext : Unit.INSTANCE;
    }
}
