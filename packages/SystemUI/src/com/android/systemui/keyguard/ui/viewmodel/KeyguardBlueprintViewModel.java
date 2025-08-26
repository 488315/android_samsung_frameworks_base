package com.android.systemui.keyguard.ui.viewmodel;

import android.os.Handler;
import android.transition.Transition;
import android.transition.TransitionManager;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.android.systemui.biometrics.domain.interactor.DisplayStateInteractor;
import com.android.systemui.biometrics.domain.interactor.DisplayStateInteractorImpl;
import com.android.systemui.keyguard.domain.interactor.KeyguardBlueprintInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.ui.view.layout.blueprints.transitions.IntraBlueprintTransition;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.log.core.Logger;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Reflection;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes2.dex */
public final class KeyguardBlueprintViewModel {
    public final StateFlowImpl _currentTransition;
    public final StateFlowImpl blueprint;
    public final LogBuffer blueprintLog;
    public final ReadonlyStateFlow currentTransition;
    public final ReadonlyStateFlow displayRotation;
    public final Handler handler;
    public final KeyguardBlueprintInteractor keyguardBlueprintInteractor;
    public final KeyguardTransitionInteractor keyguardTransitionInteractor;
    public final Logger logger;
    public final SharedFlowImpl refreshTransition;
    public final Set runningTransitions;
    public final KeyguardBlueprintViewModel$transitionListener$1 transitionListener;

    /* JADX WARN: Type inference failed for: r1v10, types: [com.android.systemui.keyguard.ui.viewmodel.KeyguardBlueprintViewModel$transitionListener$1] */
    public KeyguardBlueprintViewModel(DisplayStateInteractor displayStateInteractor, Handler handler, KeyguardBlueprintInteractor keyguardBlueprintInteractor, KeyguardTransitionInteractor keyguardTransitionInteractor, LogBuffer logBuffer) {
        this.handler = handler;
        this.keyguardBlueprintInteractor = keyguardBlueprintInteractor;
        this.keyguardTransitionInteractor = keyguardTransitionInteractor;
        this.blueprintLog = logBuffer;
        this.displayRotation = ((DisplayStateInteractorImpl) displayStateInteractor).currentRotation;
        this.logger = new Logger(logBuffer, "KeyguardBlueprintViewModel");
        this.blueprint = keyguardBlueprintInteractor.blueprint;
        this.refreshTransition = keyguardBlueprintInteractor.refreshTransition;
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(null);
        this._currentTransition = stateFlowImplMutableStateFlow;
        this.currentTransition = FlowKt.asStateFlow(stateFlowImplMutableStateFlow);
        this.runningTransitions = new LinkedHashSet();
        this.transitionListener = new Transition.TransitionListener() { // from class: com.android.systemui.keyguard.ui.viewmodel.KeyguardBlueprintViewModel$transitionListener$1
            @Override // android.transition.Transition.TransitionListener
            public final void onTransitionCancel(Transition transition) {
                Logger logger = this.this$0.logger;
                KeyguardBlueprintViewModel$$ExternalSyntheticLambda0 keyguardBlueprintViewModel$$ExternalSyntheticLambda0 = new KeyguardBlueprintViewModel$$ExternalSyntheticLambda0(6);
                LogMessage logMessageObtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.WARNING, keyguardBlueprintViewModel$$ExternalSyntheticLambda0, null);
                logMessageObtain.setStr1(Reflection.getOrCreateKotlinClass(transition.getClass()).getSimpleName());
                logger.getBuffer().commit(logMessageObtain);
                KeyguardBlueprintViewModel keyguardBlueprintViewModel = this.this$0;
                keyguardBlueprintViewModel.runningTransitions.remove(transition);
                Unit unit = Unit.INSTANCE;
                if (keyguardBlueprintViewModel.runningTransitions.size() <= 0) {
                    keyguardBlueprintViewModel._currentTransition.setValue(null);
                }
            }

            @Override // android.transition.Transition.TransitionListener
            public final void onTransitionEnd(Transition transition) {
                Logger logger = this.this$0.logger;
                KeyguardBlueprintViewModel$$ExternalSyntheticLambda0 keyguardBlueprintViewModel$$ExternalSyntheticLambda0 = new KeyguardBlueprintViewModel$$ExternalSyntheticLambda0(3);
                LogMessage logMessageObtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.INFO, keyguardBlueprintViewModel$$ExternalSyntheticLambda0, null);
                logMessageObtain.setStr1(Reflection.getOrCreateKotlinClass(transition.getClass()).getSimpleName());
                logger.getBuffer().commit(logMessageObtain);
                KeyguardBlueprintViewModel keyguardBlueprintViewModel = this.this$0;
                keyguardBlueprintViewModel.runningTransitions.remove(transition);
                Unit unit = Unit.INSTANCE;
                if (keyguardBlueprintViewModel.runningTransitions.size() <= 0) {
                    keyguardBlueprintViewModel._currentTransition.setValue(null);
                }
            }

            @Override // android.transition.Transition.TransitionListener
            public final void onTransitionPause(Transition transition) {
                Logger logger = this.this$0.logger;
                KeyguardBlueprintViewModel$$ExternalSyntheticLambda0 keyguardBlueprintViewModel$$ExternalSyntheticLambda0 = new KeyguardBlueprintViewModel$$ExternalSyntheticLambda0(2);
                LogMessage logMessageObtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.INFO, keyguardBlueprintViewModel$$ExternalSyntheticLambda0, null);
                logMessageObtain.setStr1(Reflection.getOrCreateKotlinClass(transition.getClass()).getSimpleName());
                logger.getBuffer().commit(logMessageObtain);
                KeyguardBlueprintViewModel keyguardBlueprintViewModel = this.this$0;
                keyguardBlueprintViewModel.runningTransitions.remove(transition);
                Unit unit = Unit.INSTANCE;
                if (keyguardBlueprintViewModel.runningTransitions.size() <= 0) {
                    keyguardBlueprintViewModel._currentTransition.setValue(null);
                }
            }

            @Override // android.transition.Transition.TransitionListener
            public final void onTransitionResume(Transition transition) {
                Logger logger = this.this$0.logger;
                KeyguardBlueprintViewModel$$ExternalSyntheticLambda0 keyguardBlueprintViewModel$$ExternalSyntheticLambda0 = new KeyguardBlueprintViewModel$$ExternalSyntheticLambda0(4);
                LogMessage logMessageObtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.INFO, keyguardBlueprintViewModel$$ExternalSyntheticLambda0, null);
                logMessageObtain.setStr1(Reflection.getOrCreateKotlinClass(transition.getClass()).getSimpleName());
                logger.getBuffer().commit(logMessageObtain);
                KeyguardBlueprintViewModel keyguardBlueprintViewModel = this.this$0;
                keyguardBlueprintViewModel.runningTransitions.add(transition);
                Unit unit = Unit.INSTANCE;
                if (keyguardBlueprintViewModel.runningTransitions.size() <= 0) {
                    keyguardBlueprintViewModel._currentTransition.setValue(null);
                }
            }

            @Override // android.transition.Transition.TransitionListener
            public final void onTransitionStart(Transition transition) {
                Logger logger = this.this$0.logger;
                KeyguardBlueprintViewModel$$ExternalSyntheticLambda0 keyguardBlueprintViewModel$$ExternalSyntheticLambda0 = new KeyguardBlueprintViewModel$$ExternalSyntheticLambda0(5);
                LogMessage logMessageObtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.INFO, keyguardBlueprintViewModel$$ExternalSyntheticLambda0, null);
                logMessageObtain.setStr1(Reflection.getOrCreateKotlinClass(transition.getClass()).getSimpleName());
                logger.getBuffer().commit(logMessageObtain);
                KeyguardBlueprintViewModel keyguardBlueprintViewModel = this.this$0;
                keyguardBlueprintViewModel.runningTransitions.add(transition);
                Unit unit = Unit.INSTANCE;
                if (keyguardBlueprintViewModel.runningTransitions.size() <= 0) {
                    keyguardBlueprintViewModel._currentTransition.setValue(null);
                }
            }
        };
    }

    public final void runTransition(final ConstraintLayout constraintLayout, final IntraBlueprintTransition intraBlueprintTransition, IntraBlueprintTransition.Config config, final Function0 function0) {
        TransitionData transitionData = (TransitionData) this.currentTransition.$$delegate_0.getValue();
        int priority = transitionData != null ? transitionData.config.type.getPriority() : -1;
        boolean z = config.checkPriority;
        Logger logger = this.logger;
        if (z && config.type.getPriority() < priority) {
            LogMessage logMessageObtain = logger.getBuffer().obtain(logger.getTag(), LogLevel.WARNING, new KeyguardBlueprintViewModel$$ExternalSyntheticLambda0(0), null);
            logMessageObtain.setStr1(Reflection.getOrCreateKotlinClass(IntraBlueprintTransition.class).getSimpleName());
            logMessageObtain.setInt1(priority);
            logMessageObtain.setStr2(String.valueOf(config));
            logger.getBuffer().commit(logMessageObtain);
            function0.invoke();
            return;
        }
        if (this.keyguardTransitionInteractor.getCurrentState() == KeyguardState.OFF) {
            config = IntraBlueprintTransition.Config.copy$default(config, IntraBlueprintTransition.Type.Init);
        }
        final IntraBlueprintTransition.Config config2 = config;
        LogMessage logMessageObtain2 = logger.getBuffer().obtain(logger.getTag(), LogLevel.INFO, new KeyguardBlueprintViewModel$$ExternalSyntheticLambda0(1), null);
        logMessageObtain2.setStr1(Reflection.getOrCreateKotlinClass(IntraBlueprintTransition.class).getSimpleName());
        logMessageObtain2.setInt1(priority);
        logMessageObtain2.setStr2(String.valueOf(config2));
        logger.getBuffer().commit(logMessageObtain2);
        TransitionData transitionData2 = new TransitionData(config2, 0L, 2, null);
        this.runningTransitions.add(intraBlueprintTransition);
        Unit unit = Unit.INSTANCE;
        int size = this.runningTransitions.size();
        StateFlowImpl stateFlowImpl = this._currentTransition;
        if (size <= 0) {
            stateFlowImpl.setValue(null);
        } else {
            stateFlowImpl.updateState(null, transitionData2);
        }
        intraBlueprintTransition.addListener((Transition.TransitionListener) this.transitionListener);
        this.handler.post(new Runnable() { // from class: com.android.systemui.keyguard.ui.viewmodel.KeyguardBlueprintViewModel.runTransition.6
            @Override // java.lang.Runnable
            public final void run() {
                if (config2.terminatePrevious) {
                    TransitionManager.endTransitions(constraintLayout);
                }
                TransitionManager.beginDelayedTransition(constraintLayout, intraBlueprintTransition);
                function0.invoke();
                final KeyguardBlueprintViewModel keyguardBlueprintViewModel = this;
                Handler handler = keyguardBlueprintViewModel.handler;
                final Transition transition = intraBlueprintTransition;
                handler.post(new Runnable() { // from class: com.android.systemui.keyguard.ui.viewmodel.KeyguardBlueprintViewModel.runTransition.6.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        KeyguardBlueprintViewModel keyguardBlueprintViewModel2 = keyguardBlueprintViewModel;
                        keyguardBlueprintViewModel2.runningTransitions.remove(transition);
                        Unit unit2 = Unit.INSTANCE;
                        if (keyguardBlueprintViewModel2.runningTransitions.size() <= 0) {
                            keyguardBlueprintViewModel2._currentTransition.setValue(null);
                        }
                    }
                });
            }
        });
    }
}
