package com.android.systemui.shade.domain.interactor;

import com.android.systemui.Dependency;
import com.android.systemui.qs.animator.PanelAffordanceAnimator$secQSStateListener$1;
import com.android.systemui.shade.PanelTransitionStateChangeEvent;
import com.android.systemui.shade.PanelTransitionStateListener;
import com.android.systemui.shade.SecPanelSplitHelper;
import com.android.systemui.shade.data.repository.SecQSExpansionStateRepository;
import com.android.systemui.shade.data.repository.ShadeRepository;
import java.util.concurrent.CopyOnWriteArrayList;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SecQSExpansionStateInteractor {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Lazy expansionStateListeners$delegate;
    public final SecQSExpansionStateInteractor$panelTransitionStateListener$1 panelTransitionStateListener = new PanelTransitionStateListener() { // from class: com.android.systemui.shade.domain.interactor.SecQSExpansionStateInteractor$panelTransitionStateListener$1
        @Override // com.android.systemui.shade.PanelTransitionStateListener
        public final void onPanelTransitionStateChanged(PanelTransitionStateChangeEvent panelTransitionStateChangeEvent) {
            SecQSExpansionStateRepository repository = SecQSExpansionStateInteractor.this.getRepository();
            repository._panelTransitionEnabled.updateState(null, Boolean.valueOf(panelTransitionStateChangeEvent.enabled));
            repository._panelTransitionState.updateState(null, Integer.valueOf(panelTransitionStateChangeEvent.state));
        }
    };
    public PanelAffordanceAnimator$secQSStateListener$1 qsStateListener;
    public final Lazy repository$delegate;
    public final Lazy splitHelper$delegate;

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

    /* JADX WARN: Type inference failed for: r3v4, types: [com.android.systemui.shade.domain.interactor.SecQSExpansionStateInteractor$panelTransitionStateListener$1] */
    public SecQSExpansionStateInteractor(final CoroutineScope coroutineScope, final ShadeRepository shadeRepository) {
        final int i = 0;
        this.expansionStateListeners$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.domain.interactor.SecQSExpansionStateInteractor$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i) {
                    case 0:
                        int i2 = SecQSExpansionStateInteractor.$r8$clinit;
                        return new CopyOnWriteArrayList();
                    default:
                        int i3 = SecQSExpansionStateInteractor.$r8$clinit;
                        return (SecPanelSplitHelper) Dependency.sDependency.getDependencyInner(SecPanelSplitHelper.class);
                }
            }
        });
        this.repository$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.domain.interactor.SecQSExpansionStateInteractor$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i2 = SecQSExpansionStateInteractor.$r8$clinit;
                return new SecQSExpansionStateRepository(CoroutineScope.this, shadeRepository, new SecQSExpansionStateInteractor$repository$2$1(this));
            }
        });
        final int i2 = 1;
        this.splitHelper$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.domain.interactor.SecQSExpansionStateInteractor$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i2) {
                    case 0:
                        int i22 = SecQSExpansionStateInteractor.$r8$clinit;
                        return new CopyOnWriteArrayList();
                    default:
                        int i3 = SecQSExpansionStateInteractor.$r8$clinit;
                        return (SecPanelSplitHelper) Dependency.sDependency.getDependencyInner(SecPanelSplitHelper.class);
                }
            }
        });
    }

    public final SecQSExpansionStateRepository getRepository() {
        return (SecQSExpansionStateRepository) this.repository$delegate.getValue();
    }
}
