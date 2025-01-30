package com.android.systemui.controls.controller;

import android.content.ComponentName;
import android.os.IBinder;
import android.service.controls.Control;
import android.service.controls.CustomControl;
import android.service.controls.IControlsSubscriber;
import android.service.controls.IControlsSubscription;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.Log;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.AbstractC0970x34f4116a;
import com.android.systemui.BasicRune;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.controls.controller.ControlInfo;
import com.android.systemui.controls.management.adapter.MainControlAdapter;
import com.android.systemui.controls.management.model.MainControlModel;
import com.android.systemui.controls.management.model.MainModel;
import com.android.systemui.controls.ui.CustomControlsUiControllerImpl;
import com.android.systemui.controls.ui.util.ControlExtension;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.ExecutorImpl;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt$$ExternalSyntheticLambda0;
import kotlin.comparisons.NaturalOrderComparator;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlin.sequences.TransformingSequence;
import kotlin.sequences.TransformingSequence$iterator$1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class StatefulControlSubscriber extends IControlsSubscriber.Stub {
    public final DelayableExecutor bgExecutor;
    public ControlsController controller;
    public final ControlsProviderLifecycleManager provider;
    public final long requestLimit;
    public IControlsSubscription subscription;
    public boolean subscriptionOpen;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        if (BasicRune.CONTROLS_SAMSUNG_STYLE) {
            Log.d("StatefulControlSubscriber", "onComplete");
        }
        run(iBinder, new Function0() { // from class: com.android.systemui.controls.controller.StatefulControlSubscriber$onComplete$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                StatefulControlSubscriber statefulControlSubscriber = StatefulControlSubscriber.this;
                boolean z = statefulControlSubscriber.subscriptionOpen;
                if (z) {
                    statefulControlSubscriber.subscriptionOpen = false;
                    Log.i("StatefulControlSubscriber", "onComplete receive from '" + statefulControlSubscriber.provider.componentName + "'");
                } else if (BasicRune.CONTROLS_SAMSUNG_STYLE) {
                    KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("onComplete subscriptionOpen=", z, "StatefulControlSubscriber");
                }
                return Unit.INSTANCE;
            }
        });
    }

    public final void onError(IBinder iBinder, final String str) {
        if (BasicRune.CONTROLS_SAMSUNG_STYLE) {
            Log.d("StatefulControlSubscriber", "onError");
        }
        run(iBinder, new Function0() { // from class: com.android.systemui.controls.controller.StatefulControlSubscriber$onError$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                StatefulControlSubscriber statefulControlSubscriber = StatefulControlSubscriber.this;
                boolean z = statefulControlSubscriber.subscriptionOpen;
                if (z) {
                    statefulControlSubscriber.subscriptionOpen = false;
                    Log.e("StatefulControlSubscriber", "onError receive from '" + statefulControlSubscriber.provider.componentName + "': " + str);
                } else if (BasicRune.CONTROLS_SAMSUNG_STYLE) {
                    KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("onError subscriptionOpen=", z, "StatefulControlSubscriber");
                }
                return Unit.INSTANCE;
            }
        });
    }

    public final void onNext(final IBinder iBinder, final Control control) {
        run(iBinder, new Function0() { // from class: com.android.systemui.controls.controller.StatefulControlSubscriber$onNext$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
                                ((ExecutorImpl) delayableExecutor).execute(new Runnable() { // from class: com.android.systemui.controls.controller.ControlsControllerImpl$refreshStatus$1
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
                            if (!BasicRune.CONTROLS_AUTO_REMOVE || !controlsControllerImpl.isAutoRemove || control2.getStatus() != 2) {
                                final List singletonList = Collections.singletonList(control2);
                                final CustomControlsUiControllerImpl customControlsUiControllerImpl = (CustomControlsUiControllerImpl) controlsControllerImpl.uiController;
                                customControlsUiControllerImpl.getClass();
                                ((ExecutorImpl) customControlsUiControllerImpl.uiExecutor).execute(new Runnable() { // from class: com.android.systemui.controls.ui.CustomControlsUiControllerImpl$onRefreshState$1
                                    /* JADX WARN: Multi-variable type inference failed */
                                    /* JADX WARN: Removed duplicated region for block: B:138:0x0277  */
                                    /* JADX WARN: Type inference failed for: r2v14, types: [java.lang.CharSequence] */
                                    @Override // java.lang.Runnable
                                    /*
                                        Code decompiled incorrectly, please refer to instructions dump.
                                    */
                                    public final void run() {
                                        Object obj;
                                        Iterator it;
                                        String str;
                                        String str2;
                                        ControlWithState controlWithState;
                                        Object obj2;
                                        boolean z;
                                        Control control3;
                                        Object obj3;
                                        String obj4;
                                        ControlInfo controlInfo;
                                        CustomControlsUiControllerImpl.this.logWrapper.m99dp("CustomControlsUiControllerImpl", "onRefreshState: app=" + componentName + "}, controls.size=" + singletonList.size());
                                        ArrayList arrayList = new ArrayList();
                                        List list = singletonList;
                                        CustomControlsUiControllerImpl customControlsUiControllerImpl2 = CustomControlsUiControllerImpl.this;
                                        Iterator it2 = list.iterator();
                                        boolean z2 = false;
                                        while (it2.hasNext()) {
                                            final Control control4 = (Control) it2.next();
                                            LogWrapper logWrapper = customControlsUiControllerImpl2.logWrapper;
                                            ControlExtension.Companion.getClass();
                                            logWrapper.m99dp("CustomControlsUiControllerImpl", "onRefreshState: " + ((Object) ControlExtension.Companion.dump(control4)));
                                            List list2 = customControlsUiControllerImpl2.models;
                                            ArrayList arrayList2 = new ArrayList();
                                            ArrayList arrayList3 = (ArrayList) list2;
                                            Iterator it3 = arrayList3.iterator();
                                            while (it3.hasNext()) {
                                                Object next = it3.next();
                                                if (next instanceof MainControlModel) {
                                                    arrayList2.add(next);
                                                }
                                            }
                                            Iterator it4 = arrayList2.iterator();
                                            while (true) {
                                                if (!it4.hasNext()) {
                                                    obj = null;
                                                    break;
                                                }
                                                obj = it4.next();
                                                ControlWithState controlWithState2 = ((MainControlModel) obj).controlWithState;
                                                if (Intrinsics.areEqual((controlWithState2 == null || (controlInfo = controlWithState2.f249ci) == null) ? null : controlInfo.controlId, control4.getControlId())) {
                                                    break;
                                                }
                                            }
                                            MainControlModel mainControlModel = (MainControlModel) obj;
                                            if (mainControlModel != null) {
                                                CharSequence structure = control4.getStructure();
                                                str = "";
                                                if (structure == null || (str2 = structure.toString()) == null) {
                                                    str2 = "";
                                                }
                                                int status2 = control4.getStatus();
                                                LogWrapper logWrapper2 = customControlsUiControllerImpl2.logWrapper;
                                                if (status2 == 1 && (true ^ Intrinsics.areEqual(mainControlModel.structure, str2))) {
                                                    arrayList.add(mainControlModel.structure);
                                                    arrayList.add(str2);
                                                    String str3 = mainControlModel.structure;
                                                    CharSequence structure2 = control4.getStructure();
                                                    if (structure2 != null && (obj4 = structure2.toString()) != null) {
                                                        str = obj4;
                                                    }
                                                    ArrayList arrayList4 = new ArrayList();
                                                    Iterator it5 = arrayList3.iterator();
                                                    while (it5.hasNext()) {
                                                        Object next2 = it5.next();
                                                        if (next2 instanceof MainControlModel) {
                                                            arrayList4.add(next2);
                                                        }
                                                    }
                                                    ArrayList arrayList5 = new ArrayList();
                                                    Iterator it6 = arrayList4.iterator();
                                                    while (it6.hasNext()) {
                                                        Object next3 = it6.next();
                                                        if (Intrinsics.areEqual(((MainControlModel) next3).structure, str)) {
                                                            arrayList5.add(next3);
                                                        }
                                                    }
                                                    int indexOf = arrayList3.indexOf(mainControlModel);
                                                    int size = arrayList3.size() - 1;
                                                    ArrayList arrayList6 = new ArrayList();
                                                    for (Object obj5 : arrayList3) {
                                                        Iterator it7 = it2;
                                                        int i = size;
                                                        if (obj5 instanceof MainControlModel) {
                                                            arrayList6.add(obj5);
                                                        }
                                                        it2 = it7;
                                                        size = i;
                                                    }
                                                    it = it2;
                                                    int i2 = size;
                                                    ListIterator listIterator = arrayList6.listIterator(arrayList6.size());
                                                    while (true) {
                                                        if (listIterator.hasPrevious()) {
                                                            obj3 = listIterator.previous();
                                                            if (Intrinsics.areEqual(((MainControlModel) obj3).structure, str3)) {
                                                                break;
                                                            }
                                                        } else {
                                                            obj3 = null;
                                                            break;
                                                        }
                                                    }
                                                    MainControlModel mainControlModel2 = (MainControlModel) obj3;
                                                    int lastIndexOf = mainControlModel2 != null ? arrayList3.lastIndexOf(mainControlModel2) : i2;
                                                    if ((!arrayList5.isEmpty()) && indexOf > (lastIndexOf = arrayList3.indexOf(CollectionsKt___CollectionsKt.last(arrayList5)))) {
                                                        lastIndexOf++;
                                                    }
                                                    mainControlModel.structure = str;
                                                    ControlWithState controlWithState3 = mainControlModel.controlWithState;
                                                    mainControlModel.controlWithState = controlWithState3 != null ? new ControlWithState(controlWithState3.componentName, controlWithState3.f249ci, control4) : null;
                                                    if (indexOf < lastIndexOf) {
                                                        int i3 = indexOf;
                                                        while (i3 < lastIndexOf) {
                                                            int i4 = i3 + 1;
                                                            Collections.swap(list2, i3, i4);
                                                            i3 = i4;
                                                        }
                                                    } else {
                                                        int i5 = lastIndexOf + 1;
                                                        if (i5 <= indexOf) {
                                                            int i6 = indexOf;
                                                            while (true) {
                                                                int i7 = i6 - 1;
                                                                Collections.swap(list2, i6, i7);
                                                                if (i6 == i5) {
                                                                    break;
                                                                } else {
                                                                    i6 = i7;
                                                                }
                                                            }
                                                        }
                                                    }
                                                    MainControlAdapter mainControlAdapter = customControlsUiControllerImpl2.controlAdapter;
                                                    if (mainControlAdapter != null) {
                                                        mainControlAdapter.notifyItemMoved(indexOf, lastIndexOf);
                                                    }
                                                    customControlsUiControllerImpl2.notifyItemChanged(lastIndexOf, mainControlModel);
                                                    logWrapper2.m99dp("CustomControlsUiControllerImpl", "notifyItemMoved: from " + str3 + " to " + str + " (" + indexOf + "->" + lastIndexOf + ")");
                                                } else {
                                                    it = it2;
                                                    int i8 = -1;
                                                    ControlWithState controlWithState4 = mainControlModel.controlWithState;
                                                    if (controlWithState4 != null) {
                                                        ControlInfo.Companion.getClass();
                                                        controlWithState = new ControlWithState(controlWithState4.componentName, ControlInfo.Companion.fromControl(control4), control4);
                                                    } else {
                                                        controlWithState = null;
                                                    }
                                                    mainControlModel.controlWithState = controlWithState;
                                                    if (BasicRune.CONTROLS_DYNAMIC_ORDERING) {
                                                        if (control4.getCustomControl().getLayoutType() == 1) {
                                                            TransformingSequence$iterator$1 transformingSequence$iterator$1 = new TransformingSequence$iterator$1(new TransformingSequence(SequencesKt___SequencesKt.filter(SequencesKt___SequencesKt.filter(SequencesKt___SequencesKt.filter(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(arrayList3), new Function1() { // from class: com.android.systemui.controls.ui.CustomControlsUiControllerImpl$updateControl$isAllSmallType$$inlined$filterIsInstance$1
                                                                @Override // kotlin.jvm.functions.Function1
                                                                public final Object invoke(Object obj6) {
                                                                    return Boolean.valueOf(obj6 instanceof MainControlModel);
                                                                }
                                                            }), new Function1() { // from class: com.android.systemui.controls.ui.CustomControlsUiControllerImpl$updateControl$isAllSmallType$1
                                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                {
                                                                    super(1);
                                                                }

                                                                @Override // kotlin.jvm.functions.Function1
                                                                public final Object invoke(Object obj6) {
                                                                    return Boolean.valueOf(Intrinsics.areEqual(((MainControlModel) obj6).structure, control4.getStructure()));
                                                                }
                                                            }), new Function1() { // from class: com.android.systemui.controls.ui.CustomControlsUiControllerImpl$updateControl$isAllSmallType$2
                                                                @Override // kotlin.jvm.functions.Function1
                                                                public final Object invoke(Object obj6) {
                                                                    return Boolean.valueOf(((MainControlModel) obj6).getType() == MainModel.Type.SMALL_CONTROL);
                                                                }
                                                            }), new Function1() { // from class: com.android.systemui.controls.ui.CustomControlsUiControllerImpl$updateControl$isAllSmallType$3
                                                                @Override // kotlin.jvm.functions.Function1
                                                                public final Object invoke(Object obj6) {
                                                                    ControlWithState controlWithState5 = ((MainControlModel) obj6).controlWithState;
                                                                    if (controlWithState5 != null) {
                                                                        return controlWithState5.control;
                                                                    }
                                                                    return null;
                                                                }
                                                            }));
                                                            int i9 = 0;
                                                            while (true) {
                                                                if (!transformingSequence$iterator$1.hasNext()) {
                                                                    break;
                                                                }
                                                                Object next4 = transformingSequence$iterator$1.next();
                                                                if (i9 < 0) {
                                                                    CollectionsKt__CollectionsKt.throwIndexOverflow();
                                                                    throw null;
                                                                }
                                                                if (Intrinsics.areEqual(control4, next4)) {
                                                                    i8 = i9;
                                                                    break;
                                                                }
                                                                i9++;
                                                            }
                                                            obj2 = null;
                                                            if (i8 >= 0) {
                                                                z = true;
                                                                if (z) {
                                                                    ControlWithState controlWithState5 = mainControlModel.controlWithState;
                                                                    if (controlWithState5 != null && (control3 = controlWithState5.control) != null) {
                                                                        ?? structure3 = control3.getStructure();
                                                                        str = structure3 != 0 ? structure3 : "";
                                                                        ArrayList arrayList7 = new ArrayList();
                                                                        Iterator it8 = arrayList3.iterator();
                                                                        while (it8.hasNext()) {
                                                                            Object next5 = it8.next();
                                                                            if (next5 instanceof MainControlModel) {
                                                                                arrayList7.add(next5);
                                                                            }
                                                                        }
                                                                        ArrayList arrayList8 = new ArrayList();
                                                                        Iterator it9 = arrayList7.iterator();
                                                                        while (it9.hasNext()) {
                                                                            Object next6 = it9.next();
                                                                            if (Intrinsics.areEqual(((MainControlModel) next6).structure, str)) {
                                                                                arrayList8.add(next6);
                                                                            }
                                                                        }
                                                                        ArrayList arrayList9 = new ArrayList();
                                                                        Iterator it10 = arrayList8.iterator();
                                                                        while (it10.hasNext()) {
                                                                            Object next7 = it10.next();
                                                                            if (((MainControlModel) next7).getType() == MainModel.Type.SMALL_CONTROL) {
                                                                                arrayList9.add(next7);
                                                                            }
                                                                        }
                                                                        final ComparisonsKt__ComparisonsKt$$ExternalSyntheticLambda0 comparisonsKt__ComparisonsKt$$ExternalSyntheticLambda0 = new ComparisonsKt__ComparisonsKt$$ExternalSyntheticLambda0(NaturalOrderComparator.INSTANCE, 1);
                                                                        List sortedWith = CollectionsKt___CollectionsKt.sortedWith(arrayList9, new Comparator() { // from class: com.android.systemui.controls.ui.CustomControlsUiControllerImpl$refreshOrdering$lambda$57$$inlined$compareBy$1
                                                                            @Override // java.util.Comparator
                                                                            public final int compare(Object obj6, Object obj7) {
                                                                                Control control5;
                                                                                CustomControl customControl;
                                                                                Control control6;
                                                                                CustomControl customControl2;
                                                                                Comparator comparator = comparisonsKt__ComparisonsKt$$ExternalSyntheticLambda0;
                                                                                ControlWithState controlWithState6 = ((MainControlModel) obj6).controlWithState;
                                                                                Integer num = null;
                                                                                Integer valueOf = (controlWithState6 == null || (control6 = controlWithState6.control) == null || (customControl2 = control6.getCustomControl()) == null) ? null : Integer.valueOf(customControl2.getOrder());
                                                                                ControlWithState controlWithState7 = ((MainControlModel) obj7).controlWithState;
                                                                                if (controlWithState7 != null && (control5 = controlWithState7.control) != null && (customControl = control5.getCustomControl()) != null) {
                                                                                    num = Integer.valueOf(customControl.getOrder());
                                                                                }
                                                                                return comparator.compare(valueOf, num);
                                                                            }
                                                                        });
                                                                        int indexOf2 = arrayList3.indexOf(mainControlModel);
                                                                        if (!sortedWith.isEmpty()) {
                                                                            Iterator it11 = arrayList8.iterator();
                                                                            while (true) {
                                                                                if (!it11.hasNext()) {
                                                                                    break;
                                                                                }
                                                                                Object next8 = it11.next();
                                                                                if (((MainControlModel) next8).getType() == MainModel.Type.STRUCTURE) {
                                                                                    obj2 = next8;
                                                                                    break;
                                                                                }
                                                                            }
                                                                            int indexOf3 = sortedWith.indexOf(mainControlModel) + arrayList3.indexOf((MainModel) obj2) + 1;
                                                                            if (indexOf2 < indexOf3) {
                                                                                int i10 = indexOf2;
                                                                                while (i10 < indexOf3) {
                                                                                    int i11 = i10 + 1;
                                                                                    Collections.swap(list2, i10, i11);
                                                                                    i10 = i11;
                                                                                }
                                                                            } else {
                                                                                int i12 = indexOf3 + 1;
                                                                                if (i12 <= indexOf2) {
                                                                                    int i13 = indexOf2;
                                                                                    while (true) {
                                                                                        int i14 = i13 - 1;
                                                                                        Collections.swap(list2, i13, i14);
                                                                                        if (i13 == i12) {
                                                                                            break;
                                                                                        } else {
                                                                                            i13 = i14;
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                            MainControlAdapter mainControlAdapter2 = customControlsUiControllerImpl2.controlAdapter;
                                                                            if (mainControlAdapter2 != null) {
                                                                                mainControlAdapter2.notifyItemMoved(indexOf2, indexOf3);
                                                                                mainControlAdapter2.notifyItemChanged(indexOf3, mainControlModel);
                                                                            }
                                                                            logWrapper2.m99dp("CustomControlsUiControllerImpl", AbstractC0970x34f4116a.m94m("refreshOrdering: ", indexOf2, " -> ", indexOf3));
                                                                            indexOf2 = indexOf3;
                                                                        }
                                                                        MainControlAdapter mainControlAdapter3 = customControlsUiControllerImpl2.controlAdapter;
                                                                        if (mainControlAdapter3 != null) {
                                                                            mainControlAdapter3.notifyItemChanged(indexOf2, mainControlModel);
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else {
                                                            obj2 = null;
                                                        }
                                                        z = false;
                                                        if (z) {
                                                        }
                                                    }
                                                    customControlsUiControllerImpl2.notifyItemChanged(arrayList3.indexOf(mainControlModel), mainControlModel);
                                                }
                                                z2 = true;
                                            } else {
                                                it = it2;
                                            }
                                            it2 = it;
                                        }
                                        CustomControlsUiControllerImpl.access$listAdjustmentIfNeeded(CustomControlsUiControllerImpl.this, CollectionsKt___CollectionsKt.distinct(arrayList));
                                        CustomControlsUiControllerImpl.access$showEmptyStructureIfNeeded(CustomControlsUiControllerImpl.this);
                                        if (z2) {
                                            CustomControlsUiControllerImpl customControlsUiControllerImpl3 = CustomControlsUiControllerImpl.this;
                                            customControlsUiControllerImpl3.isChanged = true;
                                            customControlsUiControllerImpl3.verificationStructureInfos = customControlsUiControllerImpl3.getStructureInfosByUI(customControlsUiControllerImpl3.selectedItem.getComponentName());
                                        }
                                    }
                                });
                            } else if (controlsControllerImpl.confirmAvailability()) {
                                final CustomControlsUiControllerImpl customControlsUiControllerImpl2 = (CustomControlsUiControllerImpl) controlsControllerImpl.customUiController;
                                customControlsUiControllerImpl2.getClass();
                                ((ExecutorImpl) customControlsUiControllerImpl2.uiExecutor).execute(new Runnable() { // from class: com.android.systemui.controls.ui.CustomControlsUiControllerImpl$removeControl$1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        Object obj;
                                        ControlInfo controlInfo;
                                        LogWrapper logWrapper = CustomControlsUiControllerImpl.this.logWrapper;
                                        ComponentName componentName2 = componentName;
                                        ControlExtension.Companion companion = ControlExtension.Companion;
                                        Control control3 = control2;
                                        companion.getClass();
                                        logWrapper.m99dp("CustomControlsUiControllerImpl", "onRefreshState app=" + componentName2 + ", removeControl:  " + ((Object) ControlExtension.Companion.dump(control3)));
                                        List list = CustomControlsUiControllerImpl.this.models;
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
                                            if (controlWithState != null && (controlInfo = controlWithState.f249ci) != null) {
                                                obj = controlInfo.controlId;
                                            }
                                            if (Intrinsics.areEqual(obj, control4.getControlId())) {
                                                obj = next2;
                                                break;
                                            }
                                        }
                                        MainControlModel mainControlModel = (MainControlModel) obj;
                                        if (mainControlModel != null) {
                                            CustomControlsUiControllerImpl customControlsUiControllerImpl3 = CustomControlsUiControllerImpl.this;
                                            int indexOf = ((ArrayList) customControlsUiControllerImpl3.models).indexOf(mainControlModel);
                                            ((ArrayList) customControlsUiControllerImpl3.models).remove(indexOf);
                                            MainControlAdapter mainControlAdapter = customControlsUiControllerImpl3.controlAdapter;
                                            if (mainControlAdapter != null) {
                                                mainControlAdapter.notifyItemRemoved(indexOf);
                                            }
                                            customControlsUiControllerImpl3.logWrapper.m99dp("CustomControlsUiControllerImpl", AbstractC0000x2c234b15.m0m("notifyItemRemoved: ", indexOf));
                                            CustomControlsUiControllerImpl.access$listAdjustmentIfNeeded(customControlsUiControllerImpl3, Collections.singletonList(mainControlModel.structure));
                                            CustomControlsUiControllerImpl.access$showEmptyStructureIfNeeded(customControlsUiControllerImpl3);
                                            customControlsUiControllerImpl3.isChanged = true;
                                            customControlsUiControllerImpl3.verificationStructureInfos = customControlsUiControllerImpl3.getStructureInfosByUI(customControlsUiControllerImpl3.selectedItem.getComponentName());
                                        }
                                    }
                                });
                                ((ExecutorImpl) delayableExecutor).execute(new Runnable() { // from class: com.android.systemui.controls.controller.ControlsControllerImpl$removeFavorite$1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        boolean z;
                                        Object obj;
                                        boolean z2;
                                        Favorites favorites = Favorites.INSTANCE;
                                        ComponentName componentName2 = componentName;
                                        Control control3 = control2;
                                        favorites.getClass();
                                        Iterator it = Favorites.getStructuresForComponent(componentName2).iterator();
                                        while (true) {
                                            z = true;
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
                                                        z2 = true;
                                                        break;
                                                    }
                                                }
                                            }
                                            z2 = false;
                                            if (z2) {
                                                break;
                                            }
                                        }
                                        StructureInfo structureInfo = (StructureInfo) obj;
                                        if (structureInfo == null) {
                                            z = false;
                                        } else {
                                            ArrayList arrayList = new ArrayList();
                                            for (Object obj2 : structureInfo.controls) {
                                                if (!Intrinsics.areEqual(((ControlInfo) obj2).controlId, control3.getControlId())) {
                                                    arrayList.add(obj2);
                                                }
                                            }
                                            Favorites.replaceControls(StructureInfo.copy$default(structureInfo, arrayList));
                                        }
                                        if (z) {
                                            ControlsFavoritePersistenceWrapper controlsFavoritePersistenceWrapper = controlsControllerImpl.persistenceWrapper;
                                            Favorites.INSTANCE.getClass();
                                            controlsFavoritePersistenceWrapper.storeFavorites(Favorites.getAllStructures());
                                        }
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
        if (BasicRune.CONTROLS_SAMSUNG_STYLE) {
            Log.d("StatefulControlSubscriber", "onSubscribe");
        }
        run(iBinder, new Function0() { // from class: com.android.systemui.controls.controller.StatefulControlSubscriber$onSubscribe$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
            ((ExecutorImpl) this.bgExecutor).execute(new Runnable() { // from class: com.android.systemui.controls.controller.StatefulControlSubscriber$run$1
                @Override // java.lang.Runnable
                public final void run() {
                    Function0.this.invoke();
                }
            });
        } else if (BasicRune.CONTROLS_SAMSUNG_STYLE) {
            Log.w("StatefulControlSubscriber", "Provider token is not same:" + iBinder + ", " + this.provider.token);
        }
    }
}
