package com.android.systemui.activity.data.repository;

import android.app.ActivityManager;
import com.android.systemui.activity.data.model.AppVisibilityModel;
import com.android.systemui.log.core.Logger;
import com.android.systemui.util.time.SystemClock;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$runningFold$$inlined$unsafeFlow$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ActivityManagerRepositoryImpl implements ActivityManagerRepository {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ActivityManager activityManager;
    public final CoroutineContext backgroundContext;
    public final SystemClock systemClock;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public ActivityManagerRepositoryImpl(CoroutineContext coroutineContext, SystemClock systemClock, ActivityManager activityManager) {
        this.backgroundContext = coroutineContext;
        this.systemClock = systemClock;
        this.activityManager = activityManager;
    }

    public final Flow createAppVisibilityFlow(int i, Logger logger, String str) {
        return FlowKt.distinctUntilChanged(new FlowKt__TransformKt$runningFold$$inlined$unsafeFlow$1(new AppVisibilityModel(false, null, 3, null), FlowKt.distinctUntilChanged(createIsAppVisibleFlow(i, logger, str)), new ActivityManagerRepositoryImpl$createAppVisibilityFlow$1(this, logger, str, null)));
    }

    public final Flow createIsAppVisibleFlow(int i, Logger logger, String str) {
        return FlowKt.flowOn(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new ActivityManagerRepositoryImpl$createIsAppVisibleFlow$2(this, i, logger, str, null), FlowKt.distinctUntilChanged(FlowConflatedKt.conflatedCallbackFlow(new ActivityManagerRepositoryImpl$createIsAppVisibleFlow$1(this, logger, i, str, null)))), this.backgroundContext);
    }
}
