package com.android.systemui.shade.domain.interactor;

import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.qs.bar.VolumeSeekBar;
import com.android.systemui.shade.PanelTransitionStateChangeEvent;
import com.android.systemui.shade.PanelTransitionStateListener;
import com.android.systemui.shade.SecPanelSplitHelper;
import com.android.systemui.shade.data.repository.SecQSExpansionStateRepository;
import com.android.systemui.shade.data.repository.ShadeRepository;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlinx.coroutines.CoroutineScope;

public final class SecQSExpansionStateInteractor {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Lazy repository$delegate;
    public final Lazy expansionStateListeners$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.domain.interactor.SecQSExpansionStateInteractor$expansionStateListeners$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return new CopyOnWriteArrayList();
        }
    });
    public final Lazy splitHelper$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.domain.interactor.SecQSExpansionStateInteractor$splitHelper$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return (SecPanelSplitHelper) Dependency.sDependency.getDependencyInner(SecPanelSplitHelper.class);
        }
    });
    public final SecQSExpansionStateInteractor$panelTransitionStateListener$1 panelTransitionStateListener = new PanelTransitionStateListener() { // from class: com.android.systemui.shade.domain.interactor.SecQSExpansionStateInteractor$panelTransitionStateListener$1
        @Override // com.android.systemui.shade.PanelTransitionStateListener
        public final void onPanelTransitionStateChanged(PanelTransitionStateChangeEvent panelTransitionStateChangeEvent) {
            int i = SecQSExpansionStateInteractor.$r8$clinit;
            SecQSExpansionStateRepository repository = SecQSExpansionStateInteractor.this.getRepository();
            repository._panelTransitionEnabled.updateState(null, Boolean.valueOf(panelTransitionStateChangeEvent.enabled));
            repository._panelTransitionState.updateState(null, Integer.valueOf(panelTransitionStateChangeEvent.state));
        }
    };

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

    public SecQSExpansionStateInteractor(final CoroutineScope coroutineScope, final ShadeRepository shadeRepository) {
        this.repository$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.domain.interactor.SecQSExpansionStateInteractor$repository$2

            /* renamed from: com.android.systemui.shade.domain.interactor.SecQSExpansionStateInteractor$repository$2$1, reason: invalid class name */
            final /* synthetic */ class AnonymousClass1 extends FunctionReferenceImpl implements Function1 {
                public AnonymousClass1(Object obj) {
                    super(1, obj, SecQSExpansionStateInteractor.class, "notify", "notify(Z)V", 0);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    boolean booleanValue = ((Boolean) obj).booleanValue();
                    SecQSExpansionStateInteractor secQSExpansionStateInteractor = (SecQSExpansionStateInteractor) this.receiver;
                    secQSExpansionStateInteractor.getClass();
                    SecQSExpansionStateChangeEvent secQSExpansionStateChangeEvent = new SecQSExpansionStateChangeEvent(booleanValue);
                    StringBuilder sb = new StringBuilder("notify: ");
                    boolean z = secQSExpansionStateChangeEvent.expanded;
                    ActionBarContextView$$ExternalSyntheticOutline0.m(sb, z, "SecQSExpansionStateInteractor");
                    Iterator it = ((List) secQSExpansionStateInteractor.expansionStateListeners$delegate.getValue()).iterator();
                    while (it.hasNext()) {
                        VolumeSeekBar volumeSeekBar = (VolumeSeekBar) ((SecQSExpansionStateListener) it.next());
                        volumeSeekBar.getClass();
                        volumeSeekBar.isShowing = z;
                        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("[QP volume] onQSExpansionStateChanged isShowing = ", "VolumeSeekBar", z);
                        volumeSeekBar.volumeManager.updateCurrentVolume();
                    }
                    return Unit.INSTANCE;
                }
            }

            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return new SecQSExpansionStateRepository(CoroutineScope.this, shadeRepository, new AnonymousClass1(this));
            }
        });
    }

    public final SecQSExpansionStateRepository getRepository() {
        return (SecQSExpansionStateRepository) this.repository$delegate.getValue();
    }
}
