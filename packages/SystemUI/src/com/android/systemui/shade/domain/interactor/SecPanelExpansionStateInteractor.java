package com.android.systemui.shade.domain.interactor;

import android.app.SemStatusBarManager;
import android.content.Context;
import com.android.systemui.Dependency;
import com.android.systemui.UiOffloadThread;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.ShadeExpansionChangeEvent;
import com.android.systemui.shade.ShadeExpansionListener;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.shade.data.repository.SecPanelExpansionStateRepository;
import java.util.concurrent.CopyOnWriteArrayList;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SecPanelExpansionStateInteractor {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Context context;
    public final Lazy expansionStateListeners$delegate;
    public final Lazy repository$delegate;
    public final ShadeExpansionStateManager shadeExpansionStateManager;
    public final StatusBarStateController statusBarStateController;
    public final Lazy uiOffloadThread$delegate;
    public final WakefulnessLifecycle wakefulnessLifecycle;
    public final Lazy statusBarManager$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.domain.interactor.SecPanelExpansionStateInteractor$$ExternalSyntheticLambda2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            Object systemService = SecPanelExpansionStateInteractor.this.context.getSystemService("sem_statusbar");
            if (systemService instanceof SemStatusBarManager) {
                return (SemStatusBarManager) systemService;
            }
            return null;
        }
    });
    public final SecPanelExpansionStateInteractor$observer$1 observer = new WakefulnessLifecycle.Observer() { // from class: com.android.systemui.shade.domain.interactor.SecPanelExpansionStateInteractor$observer$1
        @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
        public final void onFinishedGoingToSleep() {
            int i = SecPanelExpansionStateInteractor.$r8$clinit;
            SecPanelExpansionStateInteractor.this.getRepository()._screenOffState.updateState(null, Boolean.TRUE);
        }

        @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
        public final void onFinishedWakingUp() {
            int i = SecPanelExpansionStateInteractor.$r8$clinit;
            SecPanelExpansionStateInteractor.this.getRepository()._screenOffState.updateState(null, Boolean.FALSE);
        }

        @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
        public final void onStartedGoingToSleep() {
            int i = SecPanelExpansionStateInteractor.$r8$clinit;
            SecPanelExpansionStateInteractor.this.getRepository()._screenOffState.updateState(null, Boolean.TRUE);
        }

        @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
        public final void onStartedWakingUp() {
            int i = SecPanelExpansionStateInteractor.$r8$clinit;
            SecPanelExpansionStateInteractor.this.getRepository()._screenOffState.updateState(null, Boolean.FALSE);
        }
    };
    public final SecPanelExpansionStateInteractor$shadeExpansionListener$1 shadeExpansionListener = new ShadeExpansionListener() { // from class: com.android.systemui.shade.domain.interactor.SecPanelExpansionStateInteractor$shadeExpansionListener$1
        @Override // com.android.systemui.shade.ShadeExpansionListener
        public final void onPanelExpansionChanged(ShadeExpansionChangeEvent shadeExpansionChangeEvent) {
            int i = SecPanelExpansionStateInteractor.$r8$clinit;
            SecPanelExpansionStateInteractor.this.getRepository()._shadeFraction.updateState(null, Float.valueOf(shadeExpansionChangeEvent.fraction));
        }
    };
    public final SecPanelExpansionStateInteractor$stateListener$1 stateListener = new SecPanelExpansionStateInteractor$stateListener$1(this);
    public final StateFlowImpl lockscreenShadeFraction = getRepository()._lockscreenShadeFraction;
    public final StateFlowImpl shadeFraction = getRepository()._shadeFraction;
    public final StateFlowImpl statusBarState = getRepository()._statusBarState;

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

    /* JADX WARN: Type inference failed for: r1v8, types: [com.android.systemui.shade.domain.interactor.SecPanelExpansionStateInteractor$observer$1] */
    /* JADX WARN: Type inference failed for: r1v9, types: [com.android.systemui.shade.domain.interactor.SecPanelExpansionStateInteractor$shadeExpansionListener$1] */
    public SecPanelExpansionStateInteractor(final CoroutineScope coroutineScope, Context context, ShadeExpansionStateManager shadeExpansionStateManager, StatusBarStateController statusBarStateController, WakefulnessLifecycle wakefulnessLifecycle) {
        this.context = context;
        this.shadeExpansionStateManager = shadeExpansionStateManager;
        this.statusBarStateController = statusBarStateController;
        this.wakefulnessLifecycle = wakefulnessLifecycle;
        this.repository$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.domain.interactor.SecPanelExpansionStateInteractor$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i = SecPanelExpansionStateInteractor.$r8$clinit;
                return new SecPanelExpansionStateRepository(CoroutineScope.this, new SecPanelExpansionStateInteractor$repository$2$1(this));
            }
        });
        final int i = 0;
        this.uiOffloadThread$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.domain.interactor.SecPanelExpansionStateInteractor$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i) {
                    case 0:
                        int i2 = SecPanelExpansionStateInteractor.$r8$clinit;
                        return (UiOffloadThread) Dependency.sDependency.getDependencyInner(UiOffloadThread.class);
                    default:
                        int i3 = SecPanelExpansionStateInteractor.$r8$clinit;
                        return new CopyOnWriteArrayList();
                }
            }
        });
        final int i2 = 1;
        this.expansionStateListeners$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.domain.interactor.SecPanelExpansionStateInteractor$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i2) {
                    case 0:
                        int i22 = SecPanelExpansionStateInteractor.$r8$clinit;
                        return (UiOffloadThread) Dependency.sDependency.getDependencyInner(UiOffloadThread.class);
                    default:
                        int i3 = SecPanelExpansionStateInteractor.$r8$clinit;
                        return new CopyOnWriteArrayList();
                }
            }
        });
    }

    public final SecPanelExpansionStateRepository getRepository() {
        return (SecPanelExpansionStateRepository) this.repository$delegate.getValue();
    }

    public final int getstatusBarState() {
        return ((Number) getRepository()._statusBarState.getValue()).intValue();
    }

    public final void registerListener(SecPanelExpansionStateListener secPanelExpansionStateListener) {
        ((CopyOnWriteArrayList) this.expansionStateListeners$delegate.getValue()).add(secPanelExpansionStateListener);
        new SecPanelExpansionStateChangeEvent(((Number) getRepository().panelState.$$delegate_0.getValue()).intValue());
    }
}
