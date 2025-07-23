package com.android.systemui.keyguard;

import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.CoreStartable;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.utils.GlobalWindowManager;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ResourceTrimmer implements CoreStartable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final CoroutineScope applicationScope;
    public final CoroutineDispatcher bgDispatcher;
    public final GlobalWindowManager globalWindowManager;
    public final KeyguardTransitionInteractor keyguardTransitionInteractor;

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

    public ResourceTrimmer(KeyguardTransitionInteractor keyguardTransitionInteractor, GlobalWindowManager globalWindowManager, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher) {
        this.keyguardTransitionInteractor = keyguardTransitionInteractor;
        this.globalWindowManager = globalWindowManager;
        this.applicationScope = coroutineScope;
        this.bgDispatcher = coroutineDispatcher;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        android.util.Log.d("ResourceTrimmer", "Resource trimmer registered.");
        CoroutineTracingKt.launchTraced$default(this.applicationScope, this.bgDispatcher, null, new ResourceTrimmer$start$1(this, null), 5);
    }
}
