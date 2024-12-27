package com.android.systemui.keyguard.ui.viewmodel;

import android.os.Handler;
import android.transition.Transition;
import android.transition.TransitionManager;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.android.systemui.keyguard.domain.interactor.KeyguardBlueprintInteractor;
import com.android.systemui.keyguard.ui.view.layout.blueprints.transitions.IntraBlueprintTransition;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

public final class KeyguardBlueprintViewModel {
    public final StateFlowImpl _currentTransition;
    public final StateFlowImpl blueprint;
    public final ReadonlyStateFlow currentTransition;
    public final Handler handler;
    public final SharedFlowImpl refreshTransition;
    public final Set runningTransitions;
    public final KeyguardBlueprintViewModel$transitionListener$1 transitionListener;

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

    /* JADX WARN: Type inference failed for: r1v7, types: [com.android.systemui.keyguard.ui.viewmodel.KeyguardBlueprintViewModel$transitionListener$1] */
    public KeyguardBlueprintViewModel(Handler handler, KeyguardBlueprintInteractor keyguardBlueprintInteractor) {
        this.handler = handler;
        this.blueprint = keyguardBlueprintInteractor.blueprint;
        this.refreshTransition = keyguardBlueprintInteractor.refreshTransition;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(null);
        this._currentTransition = MutableStateFlow;
        this.currentTransition = FlowKt.asStateFlow(MutableStateFlow);
        this.runningTransitions = new LinkedHashSet();
        this.transitionListener = new Transition.TransitionListener() { // from class: com.android.systemui.keyguard.ui.viewmodel.KeyguardBlueprintViewModel$transitionListener$1
            @Override // android.transition.Transition.TransitionListener
            public final void onTransitionCancel(final Transition transition) {
                KeyguardBlueprintViewModel.this.updateTransitions(null, new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.KeyguardBlueprintViewModel$transitionListener$1$onTransitionCancel$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        ((Set) obj).remove(transition);
                        return Unit.INSTANCE;
                    }
                });
            }

            @Override // android.transition.Transition.TransitionListener
            public final void onTransitionEnd(final Transition transition) {
                KeyguardBlueprintViewModel.this.updateTransitions(null, new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.KeyguardBlueprintViewModel$transitionListener$1$onTransitionEnd$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        ((Set) obj).remove(transition);
                        return Unit.INSTANCE;
                    }
                });
            }

            @Override // android.transition.Transition.TransitionListener
            public final void onTransitionPause(final Transition transition) {
                KeyguardBlueprintViewModel.this.updateTransitions(null, new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.KeyguardBlueprintViewModel$transitionListener$1$onTransitionPause$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        ((Set) obj).remove(transition);
                        return Unit.INSTANCE;
                    }
                });
            }

            @Override // android.transition.Transition.TransitionListener
            public final void onTransitionResume(final Transition transition) {
                KeyguardBlueprintViewModel.this.updateTransitions(null, new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.KeyguardBlueprintViewModel$transitionListener$1$onTransitionResume$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        ((Set) obj).add(transition);
                        return Unit.INSTANCE;
                    }
                });
            }

            @Override // android.transition.Transition.TransitionListener
            public final void onTransitionStart(final Transition transition) {
                KeyguardBlueprintViewModel.this.updateTransitions(null, new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.KeyguardBlueprintViewModel$transitionListener$1$onTransitionStart$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        ((Set) obj).add(transition);
                        return Unit.INSTANCE;
                    }
                });
            }
        };
    }

    public final void runTransition(final ConstraintLayout constraintLayout, final Transition transition, final IntraBlueprintTransition.Config config, final Function0 function0) {
        TransitionData transitionData = (TransitionData) this.currentTransition.$$delegate_0.getValue();
        int priority = transitionData != null ? transitionData.config.type.getPriority() : -1;
        if (config.checkPriority && config.type.getPriority() < priority) {
            function0.invoke();
            return;
        }
        updateTransitions(new TransitionData(config, 0L, 2, null), new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.KeyguardBlueprintViewModel$runTransition$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                ((Set) obj).add(transition);
                return Unit.INSTANCE;
            }
        });
        transition.addListener(this.transitionListener);
        this.handler.post(new Runnable() { // from class: com.android.systemui.keyguard.ui.viewmodel.KeyguardBlueprintViewModel$runTransition$2
            @Override // java.lang.Runnable
            public final void run() {
                if (IntraBlueprintTransition.Config.this.terminatePrevious) {
                    TransitionManager.endTransitions(constraintLayout);
                }
                TransitionManager.beginDelayedTransition(constraintLayout, transition);
                function0.invoke();
                final KeyguardBlueprintViewModel keyguardBlueprintViewModel = this;
                Handler handler = keyguardBlueprintViewModel.handler;
                final Transition transition2 = transition;
                handler.post(new Runnable() { // from class: com.android.systemui.keyguard.ui.viewmodel.KeyguardBlueprintViewModel$runTransition$2.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        KeyguardBlueprintViewModel keyguardBlueprintViewModel2 = KeyguardBlueprintViewModel.this;
                        final Transition transition3 = transition2;
                        keyguardBlueprintViewModel2.updateTransitions(null, new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.KeyguardBlueprintViewModel.runTransition.2.1.1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj) {
                                ((Set) obj).remove(transition3);
                                return Unit.INSTANCE;
                            }
                        });
                    }
                });
            }
        });
    }

    public final void updateTransitions(TransitionData transitionData, Function1 function1) {
        function1.invoke(this.runningTransitions);
        int size = this.runningTransitions.size();
        StateFlowImpl stateFlowImpl = this._currentTransition;
        if (size <= 0) {
            stateFlowImpl.setValue(null);
        } else if (transitionData != null) {
            stateFlowImpl.updateState(null, transitionData);
        }
    }
}
