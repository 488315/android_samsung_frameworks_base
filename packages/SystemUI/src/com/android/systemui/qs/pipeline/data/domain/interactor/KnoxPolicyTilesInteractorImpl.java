package com.android.systemui.qs.pipeline.data.domain.interactor;

import android.content.res.Resources;
import android.os.Build;
import com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepository;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
public final class KnoxPolicyTilesInteractorImpl implements KnoxPolicyTilesInteractor {
    public final KnoxPolicyTilesRepository knoxPolicyTilesRepository;
    public final Resources resources;

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
