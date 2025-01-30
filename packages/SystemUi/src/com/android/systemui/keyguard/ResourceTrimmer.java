package com.android.systemui.keyguard;

import com.android.systemui.CoreStartable;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.utils.GlobalWindowManager;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ResourceTrimmer implements CoreStartable, WakefulnessLifecycle.Observer {
    public final FeatureFlags featureFlags;
    public final KeyguardInteractor keyguardInteractor;
    public final KeyguardTransitionInteractor keyguardTransitionInteractor;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public ResourceTrimmer(KeyguardInteractor keyguardInteractor, KeyguardTransitionInteractor keyguardTransitionInteractor, GlobalWindowManager globalWindowManager, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, FeatureFlags featureFlags) {
        this.keyguardInteractor = keyguardInteractor;
        this.keyguardTransitionInteractor = keyguardTransitionInteractor;
        this.featureFlags = featureFlags;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        android.util.Log.d("ResourceTrimmer", "Resource trimmer registered.");
        Flags flags = Flags.INSTANCE;
        FeatureFlags featureFlags = this.featureFlags;
        featureFlags.getClass();
        Flags flags2 = Flags.INSTANCE;
        featureFlags.getClass();
    }
}
