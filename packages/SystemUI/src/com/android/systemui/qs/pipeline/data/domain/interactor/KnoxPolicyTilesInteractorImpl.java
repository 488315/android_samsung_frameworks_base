package com.android.systemui.qs.pipeline.data.domain.interactor;

import android.content.res.Resources;
import android.os.Build;
import com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepository;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class KnoxPolicyTilesInteractorImpl implements KnoxPolicyTilesInteractor {
    public final KnoxPolicyTilesRepository knoxPolicyTilesRepository;
    public final Resources resources;

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
        boolean z = Build.IS_DEBUGGABLE;
    }

    public KnoxPolicyTilesInteractorImpl(CoroutineScope coroutineScope, KnoxPolicyTilesRepository knoxPolicyTilesRepository, Resources resources) {
        this.knoxPolicyTilesRepository = knoxPolicyTilesRepository;
        this.resources = resources;
    }
}
