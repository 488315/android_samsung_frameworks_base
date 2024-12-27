package com.android.systemui.controls.controller;

import android.content.ComponentName;
import android.os.IBinder;
import android.service.controls.Control;
import android.service.controls.IControlsSubscriber;
import android.service.controls.IControlsSubscription;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.controls.controller.ControlInfo;
import com.android.systemui.controls.management.adapter.StatefulControlAdapter;
import com.android.systemui.controls.management.model.MainControlModel;
import com.android.systemui.controls.ui.SecControlsUiControllerImpl;
import com.android.systemui.controls.util.ControlsUtil;
import com.android.systemui.util.concurrency.DelayableExecutor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class StatefulControlSubscriber extends IControlsSubscriber.Stub {
    public final DelayableExecutor bgExecutor;
    public ControlsController controller;
    public final ControlsProviderLifecycleManager provider;
    public final long requestLimit;
    public IControlsSubscription subscription;
    public boolean subscriptionOpen;

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

    public StatefulControlSubscriber(ControlsController controlsController, ControlsProviderLifecycleManager controlsProviderLifecycleManager, DelayableExecutor delayableExecutor, long j) {
        this.controller = controlsController;
        this.provider = controlsProviderLifecycleManager;
        this.bgExecutor = delayableExecutor;
        this.requestLimit = j;
    }

    public final void onComplete(IBinder iBinder) {
        run(iBinder, new Function0() { // from class: com.android.systemui.controls.controller.StatefulControlSubscriber$onComplete$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("onComplete subscriptionOpen = ", "StatefulControlSubscriber", StatefulControlSubscriber.this.subscriptionOpen);
                StatefulControlSubscriber statefulControlSubscriber = StatefulControlSubscriber.this;
                if (statefulControlSubscriber.subscriptionOpen) {
                    statefulControlSubscriber.subscriptionOpen = false;
                    Log.i("StatefulControlSubscriber", "onComplete receive from '" + statefulControlSubscriber.provider.componentName + "'");
                }
                return Unit.INSTANCE;
            }
        });
    }

    public final void onError(IBinder iBinder, final String str) {
        run(iBinder, new Function0() { // from class: com.android.systemui.controls.controller.StatefulControlSubscriber$onError$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("onError subscriptionOpen = ", "StatefulControlSubscriber", StatefulControlSubscriber.this.subscriptionOpen);
                StatefulControlSubscriber statefulControlSubscriber = StatefulControlSubscriber.this;
                if (statefulControlSubscriber.subscriptionOpen) {
                    statefulControlSubscriber.subscriptionOpen = false;
                    Log.e("StatefulControlSubscriber", "onError receive from '" + statefulControlSubscriber.provider.componentName + "': " + str);
                }
                return Unit.INSTANCE;
            }
        });
    }

    public final void onNext(final IBinder iBinder, final Control control) {
        run(iBinder, new Function0() { // from class: com.android.systemui.controls.controller.StatefulControlSubscriber$onNext$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                StatefulControlSubscriber statefulControlSubscriber = StatefulControlSubscriber.this;
                if (statefulControlSubscriber.subscriptionOpen) {
                    ControlsController controlsController = statefulControlSubscriber.controller;
                    if (controlsController != null) {
                        final ComponentName componentName = statefulControlSubscriber.provider.componentName;
                        final Control control2 = control;
                        final ControlsControllerImpl controlsControllerImpl = (ControlsControllerImpl) controlsController;
                        if (controlsControllerImpl.confirmAvailability()) {
                            int status = control2.getStatus();
                            DelayableExecutor delayableExecutor = controlsControllerImpl.executor;
                            if (status == 1) {
                                delayableExecutor.execute(new Runnable() { // from class: com.android.systemui.controls.controller.ControlsControllerImpl$refreshStatus$1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        Favorites favorites = Favorites.INSTANCE;
                                        ComponentName componentName2 = componentName;
                                        List singletonList = Collections.singletonList(control2);
                                        favorites.getClass();
                                        if (Favorites.updateControls(componentName2, singletonList)) {
                                            controlsControllerImpl.persistenceWrapper.storeFavorites(Favorites.getAllStructures());
                                        }
                                    }
                                });
                            }
                            if (!controlsControllerImpl.isAutoRemove || control2.getStatus() != 2) {
                                final List singletonList = Collections.singletonList(control2);
                                final SecControlsUiControllerImpl secControlsUiControllerImpl = (SecControlsUiControllerImpl) controlsControllerImpl.uiController;
                                secControlsUiControllerImpl.getClass();
                                secControlsUiControllerImpl.uiExecutor.execute(new Runnable() { // from class: com.android.systemui.controls.ui.SecControlsUiControllerImpl$onRefreshState$1
                                    /* JADX WARN: Code restructure failed: missing block: B:116:0x0260, code lost:
                                    
                                        if (r5 < 0) goto L103;
                                     */
                                    /* JADX WARN: Code restructure failed: missing block: B:117:0x0262, code lost:
                                    
                                        r5 = true;
                                     */
                                    /* JADX WARN: Code restructure failed: missing block: B:118:0x0265, code lost:
                                    
                                        if (r5 == false) goto L107;
                                     */
                                    /* JADX WARN: Code restructure failed: missing block: B:119:0x0267, code lost:
                                    
                                        r5 = true;
                                     */
                                    /* JADX WARN: Code restructure failed: missing block: B:182:0x0264, code lost:
                                    
                                        r5 = false;
                                     */
                                    @Override // java.lang.Runnable
                                    /*
                                        Code decompiled incorrectly, please refer to instructions dump.
                                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                                    */
                                    public final void run() {
                                        /*
                                            Method dump skipped, instructions count: 903
                                            To view this dump change 'Code comments level' option to 'DEBUG'
                                        */
                                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.controls.ui.SecControlsUiControllerImpl$onRefreshState$1.run():void");
                                    }
                                });
                            } else if (controlsControllerImpl.confirmAvailability()) {
                                final SecControlsUiControllerImpl secControlsUiControllerImpl2 = (SecControlsUiControllerImpl) controlsControllerImpl.secUiController;
                                secControlsUiControllerImpl2.getClass();
                                secControlsUiControllerImpl2.uiExecutor.execute(new Runnable() { // from class: com.android.systemui.controls.ui.SecControlsUiControllerImpl$removeControl$1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        Object obj;
                                        ControlInfo controlInfo;
                                        LogWrapper logWrapper = SecControlsUiControllerImpl.this.logWrapper;
                                        ComponentName componentName2 = componentName;
                                        ControlsUtil.Companion companion = ControlsUtil.Companion;
                                        Control control3 = control2;
                                        companion.getClass();
                                        logWrapper.dp("SecControlsUiControllerImpl", "onRefreshState app=" + componentName2 + ", removeControl:  " + ((Object) ControlsUtil.Companion.dump(control3)));
                                        List list = SecControlsUiControllerImpl.this.models;
                                        ArrayList arrayList = new ArrayList();
                                        Iterator it = ((ArrayList) list).iterator();
                                        while (it.hasNext()) {
                                            Object next = it.next();
                                            if (next instanceof MainControlModel) {
                                                arrayList.add(next);
                                            }
                                        }
                                        Control control4 = control2;
                                        Iterator it2 = arrayList.iterator();
                                        while (true) {
                                            obj = null;
                                            if (!it2.hasNext()) {
                                                break;
                                            }
                                            Object next2 = it2.next();
                                            ControlWithState controlWithState = ((MainControlModel) next2).controlWithState;
                                            if (controlWithState != null && (controlInfo = controlWithState.ci) != null) {
                                                obj = controlInfo.controlId;
                                            }
                                            if (Intrinsics.areEqual(obj, control4.getControlId())) {
                                                obj = next2;
                                                break;
                                            }
                                        }
                                        MainControlModel mainControlModel = (MainControlModel) obj;
                                        if (mainControlModel != null) {
                                            SecControlsUiControllerImpl secControlsUiControllerImpl3 = SecControlsUiControllerImpl.this;
                                            int indexOf = ((ArrayList) secControlsUiControllerImpl3.models).indexOf(mainControlModel);
                                            ((ArrayList) secControlsUiControllerImpl3.models).remove(indexOf);
                                            StatefulControlAdapter statefulControlAdapter = secControlsUiControllerImpl3.controlAdapter;
                                            if (statefulControlAdapter != null) {
                                                statefulControlAdapter.notifyItemRemoved(indexOf);
                                            }
                                            secControlsUiControllerImpl3.logWrapper.dp("SecControlsUiControllerImpl", MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(indexOf, "notifyItemRemoved: "));
                                            SecControlsUiControllerImpl.access$listAdjustmentIfNeeded(secControlsUiControllerImpl3, Collections.singletonList(mainControlModel.structure));
                                            SecControlsUiControllerImpl.access$showEmptyStructureIfNeeded(secControlsUiControllerImpl3);
                                            secControlsUiControllerImpl3.isChanged = true;
                                            secControlsUiControllerImpl3.verificationStructureInfos = secControlsUiControllerImpl3.getStructureInfosByUI(secControlsUiControllerImpl3.selectedItem.getComponentName());
                                        }
                                    }
                                });
                                delayableExecutor.execute(new Runnable() { // from class: com.android.systemui.controls.controller.ControlsControllerImpl$removeFavorite$1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        Object obj;
                                        Favorites favorites = Favorites.INSTANCE;
                                        ComponentName componentName2 = componentName;
                                        Control control3 = control2;
                                        favorites.getClass();
                                        Iterator it = Favorites.getStructuresForComponent(componentName2).iterator();
                                        loop0: while (true) {
                                            if (!it.hasNext()) {
                                                obj = null;
                                                break;
                                            }
                                            obj = it.next();
                                            List list = ((StructureInfo) obj).controls;
                                            if (!(list instanceof Collection) || !list.isEmpty()) {
                                                Iterator it2 = list.iterator();
                                                while (it2.hasNext()) {
                                                    if (Intrinsics.areEqual(((ControlInfo) it2.next()).controlId, control3.getControlId())) {
                                                        break loop0;
                                                    }
                                                }
                                            }
                                        }
                                        StructureInfo structureInfo = (StructureInfo) obj;
                                        if (structureInfo == null) {
                                            return;
                                        }
                                        List list2 = structureInfo.controls;
                                        ArrayList arrayList = new ArrayList();
                                        for (Object obj2 : list2) {
                                            if (!Intrinsics.areEqual(((ControlInfo) obj2).controlId, control3.getControlId())) {
                                                arrayList.add(obj2);
                                            }
                                        }
                                        Favorites.replaceControls(StructureInfo.copy$default(structureInfo, arrayList));
                                        ControlsFavoritePersistenceWrapper controlsFavoritePersistenceWrapper = controlsControllerImpl.persistenceWrapper;
                                        Favorites.INSTANCE.getClass();
                                        controlsFavoritePersistenceWrapper.storeFavorites(Favorites.getAllStructures());
                                    }
                                });
                            }
                        } else {
                            Log.d("ControlsControllerImpl", "Controls not available");
                        }
                    }
                } else {
                    Log.w("StatefulControlSubscriber", "Refresh outside of window for token:" + iBinder);
                }
                return Unit.INSTANCE;
            }
        });
    }

    public final void onSubscribe(IBinder iBinder, final IControlsSubscription iControlsSubscription) {
        Log.d("StatefulControlSubscriber", "onSubscribe");
        run(iBinder, new Function0() { // from class: com.android.systemui.controls.controller.StatefulControlSubscriber$onSubscribe$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                StatefulControlSubscriber statefulControlSubscriber = StatefulControlSubscriber.this;
                statefulControlSubscriber.subscriptionOpen = true;
                IControlsSubscription iControlsSubscription2 = iControlsSubscription;
                statefulControlSubscriber.subscription = iControlsSubscription2;
                statefulControlSubscriber.provider.startSubscription(iControlsSubscription2, statefulControlSubscriber.requestLimit);
                return Unit.INSTANCE;
            }
        });
    }

    public final void run(IBinder iBinder, final Function0 function0) {
        if (Intrinsics.areEqual(this.provider.token, iBinder)) {
            this.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.controls.controller.StatefulControlSubscriber$run$1
                @Override // java.lang.Runnable
                public final void run() {
                    Function0.this.invoke();
                }
            });
            return;
        }
        Log.w("StatefulControlSubscriber", "Provider token is not same, token = " + iBinder + ", provider.token = " + this.provider.token);
    }
}
