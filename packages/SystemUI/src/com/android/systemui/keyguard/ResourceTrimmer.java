package com.android.systemui.keyguard;

import com.android.systemui.CoreStartable;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.scene.domain.interactor.SceneInteractor;
import com.android.systemui.utils.GlobalWindowManager;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class ResourceTrimmer implements CoreStartable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final CoroutineScope applicationScope;
    public final CoroutineDispatcher bgDispatcher;
    public final GlobalWindowManager globalWindowManager;
    public final KeyguardTransitionInteractor keyguardTransitionInteractor;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    public ResourceTrimmer(KeyguardTransitionInteractor keyguardTransitionInteractor, GlobalWindowManager globalWindowManager, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, SceneInteractor sceneInteractor) {
        this.keyguardTransitionInteractor = keyguardTransitionInteractor;
        this.globalWindowManager = globalWindowManager;
        this.applicationScope = coroutineScope;
        this.bgDispatcher = coroutineDispatcher;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        android.util.Log.d("ResourceTrimmer", "Resource trimmer registered.");
        BuildersKt.launch$default(this.applicationScope, this.bgDispatcher, null, new ResourceTrimmer$start$1(this, null), 2);
    }
}
