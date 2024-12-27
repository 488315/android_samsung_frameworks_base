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
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class SecPanelExpansionStateInteractor {
    public final Context context;
    public final Lazy repository$delegate;
    public final ShadeExpansionStateManager shadeExpansionStateManager;
    public final StatusBarStateController statusBarStateController;
    public final WakefulnessLifecycle wakefulnessLifecycle;
    public final Lazy uiOffloadThread$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.domain.interactor.SecPanelExpansionStateInteractor$uiOffloadThread$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return (UiOffloadThread) Dependency.sDependency.getDependencyInner(UiOffloadThread.class);
        }
    });
    public final Lazy statusBarManager$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.domain.interactor.SecPanelExpansionStateInteractor$statusBarManager$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            Object systemService = SecPanelExpansionStateInteractor.this.context.getSystemService("sem_statusbar");
            if (systemService instanceof SemStatusBarManager) {
                return (SemStatusBarManager) systemService;
            }
            return null;
        }
    });
    public final Lazy expansionStateListeners$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.domain.interactor.SecPanelExpansionStateInteractor$expansionStateListeners$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return new CopyOnWriteArrayList();
        }
    });
    public final SecPanelExpansionStateInteractor$observer$1 observer = new WakefulnessLifecycle.Observer() { // from class: com.android.systemui.shade.domain.interactor.SecPanelExpansionStateInteractor$observer$1
        @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
        public final void onFinishedGoingToSleep() {
            SecPanelExpansionStateInteractor.this.getRepository()._screenOffState.updateState(null, Boolean.TRUE);
        }

        @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
        public final void onFinishedWakingUp() {
            SecPanelExpansionStateInteractor.this.getRepository()._screenOffState.updateState(null, Boolean.FALSE);
        }

        @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
        public final void onStartedGoingToSleep() {
            SecPanelExpansionStateInteractor.this.getRepository()._screenOffState.updateState(null, Boolean.TRUE);
        }

        @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
        public final void onStartedWakingUp() {
            SecPanelExpansionStateInteractor.this.getRepository()._screenOffState.updateState(null, Boolean.FALSE);
        }
    };
    public final SecPanelExpansionStateInteractor$shadeExpansionListener$1 shadeExpansionListener = new ShadeExpansionListener() { // from class: com.android.systemui.shade.domain.interactor.SecPanelExpansionStateInteractor$shadeExpansionListener$1
        @Override // com.android.systemui.shade.ShadeExpansionListener
        public final void onPanelExpansionChanged(ShadeExpansionChangeEvent shadeExpansionChangeEvent) {
            SecPanelExpansionStateInteractor.this.getRepository()._shadeFraction.updateState(null, Float.valueOf(shadeExpansionChangeEvent.fraction));
        }
    };
    public final SecPanelExpansionStateInteractor$stateListener$1 stateListener = new StatusBarStateController.StateListener() { // from class: com.android.systemui.shade.domain.interactor.SecPanelExpansionStateInteractor$stateListener$1
        @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
        public final void onStateChanged(int i) {
            super.onStateChanged(i);
            SecPanelExpansionStateInteractor.this.getRepository()._statusBarState.updateState(null, Integer.valueOf(i));
        }
    };

    /* JADX WARN: Type inference failed for: r1v10, types: [com.android.systemui.shade.domain.interactor.SecPanelExpansionStateInteractor$stateListener$1] */
    /* JADX WARN: Type inference failed for: r1v8, types: [com.android.systemui.shade.domain.interactor.SecPanelExpansionStateInteractor$observer$1] */
    /* JADX WARN: Type inference failed for: r1v9, types: [com.android.systemui.shade.domain.interactor.SecPanelExpansionStateInteractor$shadeExpansionListener$1] */
    public SecPanelExpansionStateInteractor(final CoroutineScope coroutineScope, Context context, ShadeExpansionStateManager shadeExpansionStateManager, StatusBarStateController statusBarStateController, WakefulnessLifecycle wakefulnessLifecycle) {
        this.context = context;
        this.shadeExpansionStateManager = shadeExpansionStateManager;
        this.statusBarStateController = statusBarStateController;
        this.wakefulnessLifecycle = wakefulnessLifecycle;
        this.repository$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.domain.interactor.SecPanelExpansionStateInteractor$repository$2

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.shade.domain.interactor.SecPanelExpansionStateInteractor$repository$2$1, reason: invalid class name */
            final /* synthetic */ class AnonymousClass1 extends FunctionReferenceImpl implements Function1 {
                public AnonymousClass1(Object obj) {
                    super(1, obj, SecPanelExpansionStateInteractor.class, "notify", "notify(I)V", 0);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    final int intValue = ((Number) obj).intValue();
                    final SecPanelExpansionStateInteractor secPanelExpansionStateInteractor = (SecPanelExpansionStateInteractor) this.receiver;
                    secPanelExpansionStateInteractor.getClass();
                    SecPanelExpansionStateChangeEvent secPanelExpansionStateChangeEvent = new SecPanelExpansionStateChangeEvent(intValue);
                    Iterator it = ((CopyOnWriteArrayList) secPanelExpansionStateInteractor.expansionStateListeners$delegate.getValue()).iterator();
                    while (it.hasNext()) {
                        ((SecPanelExpansionStateListener) it.next()).onPanelExpansionStateChanged(secPanelExpansionStateChangeEvent);
                    }
                    ((UiOffloadThread) secPanelExpansionStateInteractor.uiOffloadThread$delegate.getValue()).execute(
                    /*  JADX ERROR: Method code generation error
                        jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x003b: INVOKE 
                          (wrap:com.android.systemui.UiOffloadThread:0x0034: CHECK_CAST (com.android.systemui.UiOffloadThread) (wrap:java.lang.Object:0x0030: INVOKE 
                          (wrap:kotlin.Lazy:0x002e: IGET (r3v2 'secPanelExpansionStateInteractor' com.android.systemui.shade.domain.interactor.SecPanelExpansionStateInteractor) A[WRAPPED] (LINE:47) com.android.systemui.shade.domain.interactor.SecPanelExpansionStateInteractor.uiOffloadThread$delegate kotlin.Lazy)
                         INTERFACE call: kotlin.Lazy.getValue():java.lang.Object A[MD:():java.lang.Object (m), WRAPPED] (LINE:49)))
                          (wrap:java.lang.Runnable:0x0038: CONSTRUCTOR 
                          (r3v2 'secPanelExpansionStateInteractor' com.android.systemui.shade.domain.interactor.SecPanelExpansionStateInteractor A[DONT_INLINE])
                          (r4v2 'intValue' int A[DONT_INLINE])
                         A[MD:(com.android.systemui.shade.domain.interactor.SecPanelExpansionStateInteractor, int):void (m), WRAPPED] (LINE:57) call: com.android.systemui.shade.domain.interactor.SecPanelExpansionStateInteractor$notify$2.<init>(com.android.systemui.shade.domain.interactor.SecPanelExpansionStateInteractor, int):void type: CONSTRUCTOR)
                         VIRTUAL call: com.android.systemui.UiOffloadThread.execute(java.lang.Runnable):void A[MD:(java.lang.Runnable):void (m)] (LINE:60) in method: com.android.systemui.shade.domain.interactor.SecPanelExpansionStateInteractor$repository$2.1.invoke(java.lang.Object):java.lang.Object, file: classes2.dex
                        	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:310)
                        	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:273)
                        	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:94)
                        	at jadx.core.dex.nodes.IBlock.generate(IBlock.java:15)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                        	at jadx.core.dex.regions.Region.generate(Region.java:35)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                        	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:297)
                        	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:276)
                        	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:406)
                        	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:335)
                        	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:301)
                        	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
                        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
                        	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                        	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
                        Caused by: jadx.core.utils.exceptions.JadxRuntimeException: Expected class to be processed at this point, class: com.android.systemui.shade.domain.interactor.SecPanelExpansionStateInteractor$notify$2, state: NOT_LOADED
                        	at jadx.core.dex.nodes.ClassNode.ensureProcessed(ClassNode.java:305)
                        	at jadx.core.codegen.InsnGen.inlineAnonymousConstructor(InsnGen.java:807)
                        	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:730)
                        	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:418)
                        	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:145)
                        	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:121)
                        	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:108)
                        	at jadx.core.codegen.InsnGen.generateMethodArguments(InsnGen.java:1143)
                        	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:910)
                        	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:422)
                        	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:303)
                        	... 15 more
                        */
                    /*
                        this = this;
                        java.lang.Number r4 = (java.lang.Number) r4
                        int r4 = r4.intValue()
                        java.lang.Object r3 = r3.receiver
                        com.android.systemui.shade.domain.interactor.SecPanelExpansionStateInteractor r3 = (com.android.systemui.shade.domain.interactor.SecPanelExpansionStateInteractor) r3
                        r3.getClass()
                        com.android.systemui.shade.domain.interactor.SecPanelExpansionStateChangeEvent r0 = new com.android.systemui.shade.domain.interactor.SecPanelExpansionStateChangeEvent
                        r0.<init>(r4)
                        kotlin.Lazy r1 = r3.expansionStateListeners$delegate
                        java.lang.Object r1 = r1.getValue()
                        java.util.concurrent.CopyOnWriteArrayList r1 = (java.util.concurrent.CopyOnWriteArrayList) r1
                        java.util.Iterator r1 = r1.iterator()
                    L1e:
                        boolean r2 = r1.hasNext()
                        if (r2 == 0) goto L2e
                        java.lang.Object r2 = r1.next()
                        com.android.systemui.shade.domain.interactor.SecPanelExpansionStateListener r2 = (com.android.systemui.shade.domain.interactor.SecPanelExpansionStateListener) r2
                        r2.onPanelExpansionStateChanged(r0)
                        goto L1e
                    L2e:
                        kotlin.Lazy r0 = r3.uiOffloadThread$delegate
                        java.lang.Object r0 = r0.getValue()
                        com.android.systemui.UiOffloadThread r0 = (com.android.systemui.UiOffloadThread) r0
                        com.android.systemui.shade.domain.interactor.SecPanelExpansionStateInteractor$notify$2 r1 = new com.android.systemui.shade.domain.interactor.SecPanelExpansionStateInteractor$notify$2
                        r1.<init>(r3, r4)
                        r0.execute(r1)
                        kotlin.Unit r3 = kotlin.Unit.INSTANCE
                        return r3
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shade.domain.interactor.SecPanelExpansionStateInteractor$repository$2.AnonymousClass1.invoke(java.lang.Object):java.lang.Object");
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return new SecPanelExpansionStateRepository(CoroutineScope.this, new AnonymousClass1(this));
            }
        });
    }

    public final SecPanelExpansionStateRepository getRepository() {
        return (SecPanelExpansionStateRepository) this.repository$delegate.getValue();
    }
}
