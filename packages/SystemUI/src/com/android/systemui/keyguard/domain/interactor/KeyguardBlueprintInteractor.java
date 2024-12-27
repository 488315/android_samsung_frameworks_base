package com.android.systemui.keyguard.domain.interactor;

import android.content.Context;
import android.util.Log;
import com.android.systemui.CoreStartable;
import com.android.systemui.biometrics.domain.interactor.FingerprintPropertyInteractor;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractor;
import com.android.systemui.keyguard.data.repository.KeyguardBlueprintRepository;
import com.android.systemui.keyguard.ui.view.layout.blueprints.transitions.IntraBlueprintTransition;
import com.android.systemui.keyguard.ui.view.layout.sections.ClockSection;
import com.android.systemui.keyguard.ui.view.layout.sections.SmartspaceSection;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class KeyguardBlueprintInteractor implements CoreStartable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final CoroutineScope applicationScope;
    public final StateFlowImpl blueprint;
    public final KeyguardBlueprintInteractor$special$$inlined$map$1 blueprintId;
    public final ConfigurationInteractor configurationInteractor;
    public final FingerprintPropertyInteractor fingerprintPropertyInteractor;
    public final KeyguardBlueprintRepository keyguardBlueprintRepository;
    public final SharedFlowImpl refreshTransition;
    public final SmartspaceSection smartspaceSection;

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

    public KeyguardBlueprintInteractor(KeyguardBlueprintRepository keyguardBlueprintRepository, CoroutineScope coroutineScope, Context context, ShadeInteractor shadeInteractor, KeyguardClockInteractor keyguardClockInteractor, ConfigurationInteractor configurationInteractor, FingerprintPropertyInteractor fingerprintPropertyInteractor, SmartspaceSection smartspaceSection, ClockSection clockSection) {
        this.keyguardBlueprintRepository = keyguardBlueprintRepository;
        this.applicationScope = coroutineScope;
        this.configurationInteractor = configurationInteractor;
        this.fingerprintPropertyInteractor = fingerprintPropertyInteractor;
        this.smartspaceSection = smartspaceSection;
        this.blueprint = keyguardBlueprintRepository.blueprint;
        this.refreshTransition = keyguardBlueprintRepository.refreshTransition;
        this.blueprintId = new KeyguardBlueprintInteractor$special$$inlined$map$1(((ShadeInteractorImpl) shadeInteractor).baseShadeInteractor.getShadeMode());
    }

    public final void refreshBlueprint(IntraBlueprintTransition.Config config) {
        IntraBlueprintTransition.Type type;
        final KeyguardBlueprintRepository keyguardBlueprintRepository = this.keyguardBlueprintRepository;
        keyguardBlueprintRepository.f44assert.isMainThread();
        IntraBlueprintTransition.Config config2 = keyguardBlueprintRepository.targetTransitionConfig;
        if (((config2 == null || (type = config2.type) == null) ? Integer.MIN_VALUE : type.getPriority()) < config.type.getPriority()) {
            if (keyguardBlueprintRepository.targetTransitionConfig == null) {
                keyguardBlueprintRepository.handler.post(new Runnable() { // from class: com.android.systemui.keyguard.data.repository.KeyguardBlueprintRepository$refreshBlueprint$scheduleCallback$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        KeyguardBlueprintRepository.this.f44assert.isMainThread();
                        KeyguardBlueprintRepository keyguardBlueprintRepository2 = KeyguardBlueprintRepository.this;
                        IntraBlueprintTransition.Config config3 = keyguardBlueprintRepository2.targetTransitionConfig;
                        if (config3 != null && !keyguardBlueprintRepository2.refreshTransition.tryEmit(config3)) {
                            Log.e("KeyguardBlueprintRepository", "refreshBlueprint: Failed to emit blueprint refresh: " + config3);
                        }
                        KeyguardBlueprintRepository.this.targetTransitionConfig = null;
                    }
                });
            }
            keyguardBlueprintRepository.targetTransitionConfig = config;
        }
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        KeyguardBlueprintInteractor$start$1 keyguardBlueprintInteractor$start$1 = new KeyguardBlueprintInteractor$start$1(this, null);
        CoroutineScope coroutineScope = this.applicationScope;
        BuildersKt.launch$default(coroutineScope, null, null, keyguardBlueprintInteractor$start$1, 3);
        BuildersKt.launch$default(coroutineScope, null, null, new KeyguardBlueprintInteractor$start$2(this, null), 3);
        BuildersKt.launch$default(coroutineScope, null, null, new KeyguardBlueprintInteractor$start$3(this, null), 3);
    }

    public final void refreshBlueprint(IntraBlueprintTransition.Type type) {
        refreshBlueprint(new IntraBlueprintTransition.Config(type, false, false, null, 14, null));
    }
}
