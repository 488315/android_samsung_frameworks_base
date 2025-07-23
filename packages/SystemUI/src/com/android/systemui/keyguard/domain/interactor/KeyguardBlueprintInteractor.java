package com.android.systemui.keyguard.domain.interactor;

import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.CoreStartable;
import com.android.systemui.biometrics.domain.interactor.FingerprintPropertyInteractor;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractor;
import com.android.systemui.keyguard.data.repository.KeyguardBlueprintRepository;
import com.android.systemui.keyguard.data.repository.KeyguardBlueprintRepository$$ExternalSyntheticLambda0;
import com.android.systemui.keyguard.ui.view.layout.blueprints.transitions.IntraBlueprintTransition;
import com.android.systemui.keyguard.ui.view.layout.sections.SmartspaceSection;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.log.core.Logger;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractorImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    public KeyguardBlueprintInteractor(KeyguardBlueprintRepository keyguardBlueprintRepository, CoroutineScope coroutineScope, ShadeModeInteractor shadeModeInteractor, ConfigurationInteractor configurationInteractor, FingerprintPropertyInteractor fingerprintPropertyInteractor, SmartspaceSection smartspaceSection) {
        this.keyguardBlueprintRepository = keyguardBlueprintRepository;
        this.applicationScope = coroutineScope;
        this.configurationInteractor = configurationInteractor;
        this.fingerprintPropertyInteractor = fingerprintPropertyInteractor;
        this.smartspaceSection = smartspaceSection;
        this.blueprint = keyguardBlueprintRepository.blueprint;
        this.refreshTransition = keyguardBlueprintRepository.refreshTransition;
        this.blueprintId = new KeyguardBlueprintInteractor$special$$inlined$map$1(((ShadeModeInteractorImpl) shadeModeInteractor).isShadeLayoutWide);
    }

    public final void refreshBlueprint(IntraBlueprintTransition.Type type) {
        refreshBlueprint(new IntraBlueprintTransition.Config(type, false, false, null, 14, null));
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        KeyguardBlueprintInteractor$start$1 keyguardBlueprintInteractor$start$1 = new KeyguardBlueprintInteractor$start$1(this, null);
        CoroutineScope coroutineScope = this.applicationScope;
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, keyguardBlueprintInteractor$start$1, 7);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new KeyguardBlueprintInteractor$start$2(this, null), 7);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new KeyguardBlueprintInteractor$start$3(this, null), 7);
    }

    public final void refreshBlueprint(IntraBlueprintTransition.Config config) {
        IntraBlueprintTransition.Type type;
        final KeyguardBlueprintRepository keyguardBlueprintRepository = this.keyguardBlueprintRepository;
        keyguardBlueprintRepository.f48assert.isMainThread();
        IntraBlueprintTransition.Config config2 = keyguardBlueprintRepository.targetTransitionConfig;
        if (((config2 == null || (type = config2.type) == null) ? Integer.MIN_VALUE : type.getPriority()) < config.type.getPriority()) {
            if (keyguardBlueprintRepository.targetTransitionConfig == null) {
                keyguardBlueprintRepository.handler.post(new Runnable() { // from class: com.android.systemui.keyguard.data.repository.KeyguardBlueprintRepository$refreshBlueprint$scheduleCallback$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        KeyguardBlueprintRepository.this.f48assert.isMainThread();
                        KeyguardBlueprintRepository keyguardBlueprintRepository2 = KeyguardBlueprintRepository.this;
                        IntraBlueprintTransition.Config config3 = keyguardBlueprintRepository2.targetTransitionConfig;
                        if (config3 != null && !keyguardBlueprintRepository2.refreshTransition.tryEmit(config3)) {
                            Logger logger = keyguardBlueprintRepository2.logger;
                            KeyguardBlueprintRepository$$ExternalSyntheticLambda0 keyguardBlueprintRepository$$ExternalSyntheticLambda0 = new KeyguardBlueprintRepository$$ExternalSyntheticLambda0(2);
                            LogMessage obtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.ERROR, keyguardBlueprintRepository$$ExternalSyntheticLambda0, null);
                            obtain.setStr1(String.valueOf(config3));
                            logger.getBuffer().commit(obtain);
                        }
                        KeyguardBlueprintRepository.this.targetTransitionConfig = null;
                    }
                });
            }
            keyguardBlueprintRepository.targetTransitionConfig = config;
        } else {
            Logger logger = keyguardBlueprintRepository.logger;
            LogMessage obtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.INFO, new KeyguardBlueprintRepository$$ExternalSyntheticLambda0(1), null);
            obtain.setStr1(String.valueOf(config));
            logger.getBuffer().commit(obtain);
        }
    }
}
