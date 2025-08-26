package com.android.systemui.controls.controller;

import android.content.ComponentName;
import android.os.IBinder;
import android.service.controls.Control;
import android.service.controls.CustomControl;
import android.service.controls.IControlsSubscriber;
import android.service.controls.IControlsSubscription;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import androidx.compose.runtime.external.kotlinx.collections.immutable.internal.ListImplementation$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.controls.controller.ControlInfo;
import com.android.systemui.controls.management.adapter.StatefulControlAdapter;
import com.android.systemui.controls.management.model.MainControlModel;
import com.android.systemui.controls.management.model.MainModel;
import com.android.systemui.controls.ui.SecControlsUiControllerImpl;
import com.android.systemui.controls.util.ControlsUtil;
import com.android.systemui.util.concurrency.DelayableExecutor;
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
import kotlin.sequences.FilteringSequence;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlin.sequences.TransformingSequence;
import kotlin.sequences.TransformingSequence.AnonymousClass1;

/* loaded from: classes2.dex */
public final class StatefulControlSubscriber extends IControlsSubscriber.Stub {
    public final DelayableExecutor bgExecutor;
    public ControlsController controller;
    public final ControlsProviderLifecycleManager provider;
    public final long requestLimit;
    public IControlsSubscription subscription;
    public boolean subscriptionOpen;

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

    public StatefulControlSubscriber(ControlsController controlsController, ControlsProviderLifecycleManager controlsProviderLifecycleManager, DelayableExecutor delayableExecutor, long j) {
        this.controller = controlsController;
        this.provider = controlsProviderLifecycleManager;
        this.bgExecutor = delayableExecutor;
        this.requestLimit = j;
    }

    public final void onComplete(IBinder iBinder) {
        run(iBinder, new Function0() { // from class: com.android.systemui.controls.controller.StatefulControlSubscriber$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                StatefulControlSubscriber statefulControlSubscriber = this.f$0;
                EmergencyButtonController$$ExternalSyntheticOutline0.m("onComplete subscriptionOpen = ", "StatefulControlSubscriber", statefulControlSubscriber.subscriptionOpen);
                if (statefulControlSubscriber.subscriptionOpen) {
                    statefulControlSubscriber.subscriptionOpen = false;
                    Log.i("StatefulControlSubscriber", "onComplete receive from '" + statefulControlSubscriber.provider.componentName + "'");
                }
                return Unit.INSTANCE;
            }
        });
    }

    public final void onError(IBinder iBinder, String str) {
        run(iBinder, new StatefulControlSubscriber$$ExternalSyntheticLambda2(this, str, 0));
    }

    public final void onNext(final IBinder iBinder, final Control control) {
        run(iBinder, new Function0() { // from class: com.android.systemui.controls.controller.StatefulControlSubscriber$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                IBinder iBinder2 = iBinder;
                final Control control2 = control;
                StatefulControlSubscriber statefulControlSubscriber = this.f$0;
                if (statefulControlSubscriber.subscriptionOpen) {
                    ControlsController controlsController = statefulControlSubscriber.controller;
                    if (controlsController != null) {
                        final ComponentName componentName = statefulControlSubscriber.provider.componentName;
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
                                        List listSingletonList = Collections.singletonList(control2);
                                        favorites.getClass();
                                        if (Favorites.updateControls(componentName2, listSingletonList)) {
                                            controlsControllerImpl.persistenceWrapper.storeFavorites(Favorites.getAllStructures());
                                        }
                                    }
                                });
                            }
                            if (!controlsControllerImpl.isAutoRemove || control2.getStatus() != 2) {
                                final List listSingletonList = Collections.singletonList(control2);
                                final SecControlsUiControllerImpl secControlsUiControllerImpl = (SecControlsUiControllerImpl) controlsControllerImpl.uiController;
                                secControlsUiControllerImpl.getClass();
                                secControlsUiControllerImpl.uiExecutor.execute(new Runnable() { // from class: com.android.systemui.controls.ui.SecControlsUiControllerImpl$onRefreshState$1
                                    /* JADX WARN: Multi-variable type inference failed */
                                    /* JADX WARN: Removed duplicated region for block: B:102:0x0280  */
                                    /* JADX WARN: Type inference failed for: r1v20, types: [java.lang.CharSequence] */
                                    /* JADX WARN: Type inference failed for: r8v1, types: [java.lang.Object] */
                                    @Override // java.lang.Runnable
                                    /*
                                        Code decompiled incorrectly, please refer to instructions dump.
                                    */
                                    public final void run() {
                                        Object obj;
                                        ArrayList arrayList;
                                        Iterator it;
                                        String str;
                                        String string;
                                        ControlWithState controlWithState;
                                        Control control3;
                                        MainModel mainModel;
                                        Object objPrevious;
                                        String string2;
                                        ControlInfo controlInfo;
                                        secControlsUiControllerImpl.logWrapper.dp("SecControlsUiControllerImpl", "onRefreshState: app=" + componentName + "}, controls.size=" + listSingletonList.size());
                                        ArrayList arrayList2 = new ArrayList();
                                        List list = listSingletonList;
                                        SecControlsUiControllerImpl secControlsUiControllerImpl2 = secControlsUiControllerImpl;
                                        Iterator it2 = list.iterator();
                                        boolean z = false;
                                        while (true) {
                                            boolean z2 = true;
                                            if (!it2.hasNext()) {
                                                SecControlsUiControllerImpl.access$listAdjustmentIfNeeded(secControlsUiControllerImpl, CollectionsKt___CollectionsKt.distinct(arrayList2));
                                                SecControlsUiControllerImpl.access$showEmptyStructureIfNeeded(secControlsUiControllerImpl);
                                                if (z) {
                                                    SecControlsUiControllerImpl secControlsUiControllerImpl3 = secControlsUiControllerImpl;
                                                    secControlsUiControllerImpl3.isChanged = true;
                                                    secControlsUiControllerImpl3.verificationStructureInfos = secControlsUiControllerImpl3.getStructureInfosByUI(secControlsUiControllerImpl3.selectedItem.getComponentName());
                                                    return;
                                                }
                                                return;
                                            }
                                            final Control control4 = (Control) it2.next();
                                            LogWrapper logWrapper = secControlsUiControllerImpl2.logWrapper;
                                            ControlsUtil.Companion.getClass();
                                            logWrapper.dp("SecControlsUiControllerImpl", "onRefreshState: " + ((Object) ControlsUtil.Companion.dump(control4)));
                                            List list2 = secControlsUiControllerImpl2.models;
                                            ArrayList arrayList3 = new ArrayList();
                                            ArrayList arrayList4 = (ArrayList) list2;
                                            int size = arrayList4.size();
                                            int i = 0;
                                            while (i < size) {
                                                Object obj2 = arrayList4.get(i);
                                                i++;
                                                if (obj2 instanceof MainControlModel) {
                                                    arrayList3.add(obj2);
                                                }
                                            }
                                            int size2 = arrayList3.size();
                                            int i2 = 0;
                                            while (true) {
                                                if (i2 >= size2) {
                                                    obj = null;
                                                    break;
                                                }
                                                obj = arrayList3.get(i2);
                                                i2++;
                                                ControlWithState controlWithState2 = ((MainControlModel) obj).controlWithState;
                                                if (Intrinsics.areEqual((controlWithState2 == null || (controlInfo = controlWithState2.ci) == null) ? null : controlInfo.controlId, control4.getControlId())) {
                                                    break;
                                                }
                                            }
                                            MainControlModel mainControlModel = (MainControlModel) obj;
                                            if (mainControlModel != null) {
                                                CharSequence structure = control4.getStructure();
                                                str = "";
                                                if (structure == null || (string = structure.toString()) == null) {
                                                    string = "";
                                                }
                                                int status2 = control4.getStatus();
                                                LogWrapper logWrapper2 = secControlsUiControllerImpl2.logWrapper;
                                                if (status2 != 1 || Intrinsics.areEqual(mainControlModel.structure, string)) {
                                                    arrayList = arrayList2;
                                                    it = it2;
                                                    ControlWithState controlWithState3 = mainControlModel.controlWithState;
                                                    if (controlWithState3 != null) {
                                                        ComponentName componentName2 = controlWithState3.componentName;
                                                        ControlInfo.Companion.getClass();
                                                        controlWithState = new ControlWithState(componentName2, ControlInfo.Companion.fromControl(control4), control4);
                                                    } else {
                                                        controlWithState = null;
                                                    }
                                                    mainControlModel.controlWithState = controlWithState;
                                                    if (control4.getCustomControl().getLayoutType() == 1) {
                                                        final int i3 = 0;
                                                        FilteringSequence filteringSequenceFilter = SequencesKt___SequencesKt.filter(SequencesKt___SequencesKt.filter(SequencesKt___SequencesKt.filter(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(secControlsUiControllerImpl2.models), new Function1() { // from class: com.android.systemui.controls.ui.SecControlsUiControllerImpl$updateControl$isAllSmallType$$inlined$filterIsInstance$1
                                                            @Override // kotlin.jvm.functions.Function1
                                                            /* renamed from: invoke */
                                                            public final Object mo781invoke(Object obj3) {
                                                                return Boolean.valueOf(obj3 instanceof MainControlModel);
                                                            }
                                                        }), new Function1() { // from class: com.android.systemui.controls.ui.SecControlsUiControllerImpl$$ExternalSyntheticLambda0
                                                            @Override // kotlin.jvm.functions.Function1
                                                            /* renamed from: invoke */
                                                            public final Object mo781invoke(Object obj3) {
                                                                Control control5 = control4;
                                                                int i4 = SecControlsUiControllerImpl.$r8$clinit;
                                                                return Boolean.valueOf(Intrinsics.areEqual(((MainControlModel) obj3).structure, control5.getStructure()));
                                                            }
                                                        }), new Function1() { // from class: com.android.systemui.controls.ui.SecControlsUiControllerImpl$$ExternalSyntheticLambda1
                                                            @Override // kotlin.jvm.functions.Function1
                                                            /* renamed from: invoke */
                                                            public final Object mo781invoke(Object obj3) {
                                                                MainControlModel mainControlModel2 = (MainControlModel) obj3;
                                                                switch (i3) {
                                                                    case 0:
                                                                        int i4 = SecControlsUiControllerImpl.$r8$clinit;
                                                                        return Boolean.valueOf(mainControlModel2.getType() == MainModel.Type.SMALL_CONTROL);
                                                                    default:
                                                                        int i5 = SecControlsUiControllerImpl.$r8$clinit;
                                                                        ControlWithState controlWithState4 = mainControlModel2.controlWithState;
                                                                        if (controlWithState4 != null) {
                                                                            return controlWithState4.control;
                                                                        }
                                                                        return null;
                                                                }
                                                            }
                                                        });
                                                        final int i4 = 1;
                                                        TransformingSequence.AnonymousClass1 anonymousClass1 = new TransformingSequence(filteringSequenceFilter, new Function1() { // from class: com.android.systemui.controls.ui.SecControlsUiControllerImpl$$ExternalSyntheticLambda1
                                                            @Override // kotlin.jvm.functions.Function1
                                                            /* renamed from: invoke */
                                                            public final Object mo781invoke(Object obj3) {
                                                                MainControlModel mainControlModel2 = (MainControlModel) obj3;
                                                                switch (i4) {
                                                                    case 0:
                                                                        int i42 = SecControlsUiControllerImpl.$r8$clinit;
                                                                        return Boolean.valueOf(mainControlModel2.getType() == MainModel.Type.SMALL_CONTROL);
                                                                    default:
                                                                        int i5 = SecControlsUiControllerImpl.$r8$clinit;
                                                                        ControlWithState controlWithState4 = mainControlModel2.controlWithState;
                                                                        if (controlWithState4 != null) {
                                                                            return controlWithState4.control;
                                                                        }
                                                                        return null;
                                                                }
                                                            }
                                                        }).new AnonymousClass1();
                                                        int i5 = 0;
                                                        while (true) {
                                                            if (!anonymousClass1.iterator.hasNext()) {
                                                                i5 = -1;
                                                                break;
                                                            }
                                                            Object next = anonymousClass1.next();
                                                            if (i5 < 0) {
                                                                CollectionsKt__CollectionsKt.throwIndexOverflow();
                                                                throw null;
                                                            }
                                                            if (control4.equals(next)) {
                                                                break;
                                                            } else {
                                                                i5++;
                                                            }
                                                        }
                                                        boolean z3 = i5 >= 0;
                                                        if (z3) {
                                                            ControlWithState controlWithState4 = mainControlModel.controlWithState;
                                                            if (controlWithState4 != null && (control3 = controlWithState4.control) != null) {
                                                                ?? structure2 = control3.getStructure();
                                                                str = structure2 != 0 ? structure2 : "";
                                                                List list3 = secControlsUiControllerImpl2.models;
                                                                ArrayList arrayList5 = new ArrayList();
                                                                ArrayList arrayList6 = (ArrayList) list3;
                                                                int size3 = arrayList6.size();
                                                                int i6 = 0;
                                                                while (i6 < size3) {
                                                                    Object obj3 = arrayList6.get(i6);
                                                                    i6++;
                                                                    if (obj3 instanceof MainControlModel) {
                                                                        arrayList5.add(obj3);
                                                                    }
                                                                }
                                                                ArrayList arrayList7 = new ArrayList();
                                                                int size4 = arrayList5.size();
                                                                int i7 = 0;
                                                                while (i7 < size4) {
                                                                    Object obj4 = arrayList5.get(i7);
                                                                    i7++;
                                                                    if (Intrinsics.areEqual(((MainControlModel) obj4).structure, str)) {
                                                                        arrayList7.add(obj4);
                                                                    }
                                                                }
                                                                ArrayList arrayList8 = new ArrayList();
                                                                int size5 = arrayList7.size();
                                                                int i8 = 0;
                                                                while (i8 < size5) {
                                                                    Object obj5 = arrayList7.get(i8);
                                                                    i8++;
                                                                    if (((MainControlModel) obj5).getType() == MainModel.Type.SMALL_CONTROL) {
                                                                        arrayList8.add(obj5);
                                                                    }
                                                                }
                                                                final ComparisonsKt__ComparisonsKt$$ExternalSyntheticLambda0 comparisonsKt__ComparisonsKt$$ExternalSyntheticLambda0 = new ComparisonsKt__ComparisonsKt$$ExternalSyntheticLambda0(NaturalOrderComparator.INSTANCE, 1);
                                                                List listSortedWith = CollectionsKt___CollectionsKt.sortedWith(arrayList8, new Comparator() { // from class: com.android.systemui.controls.ui.SecControlsUiControllerImpl$refreshOrdering$lambda$63$$inlined$compareBy$1
                                                                    @Override // java.util.Comparator
                                                                    public final int compare(Object obj6, Object obj7) {
                                                                        Control control5;
                                                                        CustomControl customControl;
                                                                        Control control6;
                                                                        CustomControl customControl2;
                                                                        Comparator comparator = comparisonsKt__ComparisonsKt$$ExternalSyntheticLambda0;
                                                                        ControlWithState controlWithState5 = ((MainControlModel) obj6).controlWithState;
                                                                        Integer numValueOf = null;
                                                                        Integer numValueOf2 = (controlWithState5 == null || (control6 = controlWithState5.control) == null || (customControl2 = control6.getCustomControl()) == null) ? null : Integer.valueOf(customControl2.getOrder());
                                                                        ControlWithState controlWithState6 = ((MainControlModel) obj7).controlWithState;
                                                                        if (controlWithState6 != null && (control5 = controlWithState6.control) != null && (customControl = control5.getCustomControl()) != null) {
                                                                            numValueOf = Integer.valueOf(customControl.getOrder());
                                                                        }
                                                                        return comparator.compare(numValueOf2, numValueOf);
                                                                    }
                                                                });
                                                                int iIndexOf = ((ArrayList) secControlsUiControllerImpl2.models).indexOf(mainControlModel);
                                                                if (!listSortedWith.isEmpty()) {
                                                                    int size6 = arrayList7.size();
                                                                    int i9 = 0;
                                                                    while (true) {
                                                                        if (i9 >= size6) {
                                                                            mainModel = null;
                                                                            break;
                                                                        }
                                                                        ?? r8 = arrayList7.get(i9);
                                                                        i9++;
                                                                        if (((MainControlModel) r8).getType() == MainModel.Type.STRUCTURE) {
                                                                            mainModel = r8;
                                                                            break;
                                                                        }
                                                                    }
                                                                    int iIndexOf2 = listSortedWith.indexOf(mainControlModel) + ((ArrayList) secControlsUiControllerImpl2.models).indexOf(mainModel) + 1;
                                                                    secControlsUiControllerImpl2.moveElement(iIndexOf, iIndexOf2);
                                                                    StatefulControlAdapter statefulControlAdapter = secControlsUiControllerImpl2.controlAdapter;
                                                                    if (statefulControlAdapter != null) {
                                                                        statefulControlAdapter.notifyItemMoved(iIndexOf, iIndexOf2);
                                                                        statefulControlAdapter.notifyItemChanged(iIndexOf2, mainControlModel);
                                                                    }
                                                                    logWrapper2.dp("SecControlsUiControllerImpl", ListImplementation$$ExternalSyntheticOutline0.m(iIndexOf, iIndexOf2, "refreshOrdering: ", " -> "));
                                                                    iIndexOf = iIndexOf2;
                                                                }
                                                                StatefulControlAdapter statefulControlAdapter2 = secControlsUiControllerImpl2.controlAdapter;
                                                                if (statefulControlAdapter2 != null) {
                                                                    statefulControlAdapter2.notifyItemChanged(iIndexOf, mainControlModel);
                                                                }
                                                            }
                                                        } else {
                                                            secControlsUiControllerImpl2.notifyItemChanged(((ArrayList) secControlsUiControllerImpl2.models).indexOf(mainControlModel), mainControlModel);
                                                        }
                                                    }
                                                } else {
                                                    arrayList2.add(mainControlModel.structure);
                                                    arrayList2.add(string);
                                                    String str2 = mainControlModel.structure;
                                                    CharSequence structure3 = control4.getStructure();
                                                    if (structure3 != null && (string2 = structure3.toString()) != null) {
                                                        str = string2;
                                                    }
                                                    List list4 = secControlsUiControllerImpl2.models;
                                                    ArrayList arrayList9 = new ArrayList();
                                                    ArrayList arrayList10 = (ArrayList) list4;
                                                    int size7 = arrayList10.size();
                                                    int i10 = 0;
                                                    while (i10 < size7) {
                                                        Object obj6 = arrayList10.get(i10);
                                                        i10++;
                                                        boolean z4 = z2;
                                                        if (obj6 instanceof MainControlModel) {
                                                            arrayList9.add(obj6);
                                                        }
                                                        z2 = z4;
                                                    }
                                                    ArrayList arrayList11 = new ArrayList();
                                                    int size8 = arrayList9.size();
                                                    int i11 = 0;
                                                    while (i11 < size8) {
                                                        Object obj7 = arrayList9.get(i11);
                                                        i11++;
                                                        if (Intrinsics.areEqual(((MainControlModel) obj7).structure, str)) {
                                                            arrayList11.add(obj7);
                                                        }
                                                    }
                                                    int iIndexOf3 = ((ArrayList) secControlsUiControllerImpl2.models).indexOf(mainControlModel);
                                                    int size9 = ((ArrayList) secControlsUiControllerImpl2.models).size() - 1;
                                                    List list5 = secControlsUiControllerImpl2.models;
                                                    ArrayList arrayList12 = new ArrayList();
                                                    ArrayList arrayList13 = (ArrayList) list5;
                                                    int size10 = arrayList13.size();
                                                    arrayList = arrayList2;
                                                    int i12 = 0;
                                                    while (i12 < size10) {
                                                        Iterator it3 = it2;
                                                        Object obj8 = arrayList13.get(i12);
                                                        int i13 = i12 + 1;
                                                        if (obj8 instanceof MainControlModel) {
                                                            arrayList12.add(obj8);
                                                        }
                                                        it2 = it3;
                                                        i12 = i13;
                                                    }
                                                    it = it2;
                                                    ListIterator listIterator = arrayList12.listIterator(arrayList12.size());
                                                    while (true) {
                                                        if (!listIterator.hasPrevious()) {
                                                            objPrevious = null;
                                                            break;
                                                        } else {
                                                            objPrevious = listIterator.previous();
                                                            if (Intrinsics.areEqual(((MainControlModel) objPrevious).structure, str2)) {
                                                                break;
                                                            }
                                                        }
                                                    }
                                                    MainControlModel mainControlModel2 = (MainControlModel) objPrevious;
                                                    if (mainControlModel2 != null) {
                                                        size9 = ((ArrayList) secControlsUiControllerImpl2.models).lastIndexOf(mainControlModel2);
                                                    }
                                                    if (!arrayList11.isEmpty()) {
                                                        size9 = ((ArrayList) secControlsUiControllerImpl2.models).indexOf(CollectionsKt___CollectionsKt.last(arrayList11));
                                                        if (iIndexOf3 > size9) {
                                                            size9++;
                                                        }
                                                    }
                                                    mainControlModel.structure = str;
                                                    ControlWithState controlWithState5 = mainControlModel.controlWithState;
                                                    mainControlModel.controlWithState = controlWithState5 != null ? new ControlWithState(controlWithState5.componentName, controlWithState5.ci, control4) : null;
                                                    secControlsUiControllerImpl2.moveElement(iIndexOf3, size9);
                                                    StatefulControlAdapter statefulControlAdapter3 = secControlsUiControllerImpl2.controlAdapter;
                                                    if (statefulControlAdapter3 != null) {
                                                        statefulControlAdapter3.notifyItemMoved(iIndexOf3, size9);
                                                    }
                                                    secControlsUiControllerImpl2.notifyItemChanged(size9, mainControlModel);
                                                    logWrapper2.dp("SecControlsUiControllerImpl", "notifyItemMoved: from " + str2 + " to " + str + " (" + iIndexOf3 + "->" + size9 + ")");
                                                }
                                                z = true;
                                            } else {
                                                arrayList = arrayList2;
                                                it = it2;
                                            }
                                            arrayList2 = arrayList;
                                            it2 = it;
                                        }
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
                                        LogWrapper logWrapper = secControlsUiControllerImpl2.logWrapper;
                                        ComponentName componentName2 = componentName;
                                        ControlsUtil.Companion companion = ControlsUtil.Companion;
                                        Control control3 = control2;
                                        companion.getClass();
                                        logWrapper.dp("SecControlsUiControllerImpl", "onRefreshState app=" + componentName2 + ", removeControl:  " + ((Object) ControlsUtil.Companion.dump(control3)));
                                        List list = secControlsUiControllerImpl2.models;
                                        ArrayList arrayList = new ArrayList();
                                        ArrayList arrayList2 = (ArrayList) list;
                                        int size = arrayList2.size();
                                        int i = 0;
                                        int i2 = 0;
                                        while (i2 < size) {
                                            Object obj2 = arrayList2.get(i2);
                                            i2++;
                                            if (obj2 instanceof MainControlModel) {
                                                arrayList.add(obj2);
                                            }
                                        }
                                        Control control4 = control2;
                                        int size2 = arrayList.size();
                                        while (true) {
                                            obj = null;
                                            if (i >= size2) {
                                                break;
                                            }
                                            Object obj3 = arrayList.get(i);
                                            i++;
                                            ControlWithState controlWithState = ((MainControlModel) obj3).controlWithState;
                                            if (controlWithState != null && (controlInfo = controlWithState.ci) != null) {
                                                obj = controlInfo.controlId;
                                            }
                                            if (Intrinsics.areEqual(obj, control4.getControlId())) {
                                                obj = obj3;
                                                break;
                                            }
                                        }
                                        MainControlModel mainControlModel = (MainControlModel) obj;
                                        if (mainControlModel != null) {
                                            SecControlsUiControllerImpl secControlsUiControllerImpl3 = secControlsUiControllerImpl2;
                                            int iIndexOf = ((ArrayList) secControlsUiControllerImpl3.models).indexOf(mainControlModel);
                                            ((ArrayList) secControlsUiControllerImpl3.models).remove(iIndexOf);
                                            StatefulControlAdapter statefulControlAdapter = secControlsUiControllerImpl3.controlAdapter;
                                            if (statefulControlAdapter != null) {
                                                statefulControlAdapter.notifyItemRemoved(iIndexOf);
                                            }
                                            secControlsUiControllerImpl3.logWrapper.dp("SecControlsUiControllerImpl", MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(iIndexOf, "notifyItemRemoved: "));
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
                                        Object next;
                                        Favorites favorites = Favorites.INSTANCE;
                                        ComponentName componentName2 = componentName;
                                        Control control3 = control2;
                                        favorites.getClass();
                                        Iterator it = Favorites.getStructuresForComponent(componentName2).iterator();
                                        loop0: while (true) {
                                            if (!it.hasNext()) {
                                                next = null;
                                                break;
                                            }
                                            next = it.next();
                                            List list = ((StructureInfo) next).controls;
                                            if (!(list instanceof Collection) || !list.isEmpty()) {
                                                Iterator it2 = list.iterator();
                                                while (it2.hasNext()) {
                                                    if (Intrinsics.areEqual(((ControlInfo) it2.next()).controlId, control3.getControlId())) {
                                                        break loop0;
                                                    }
                                                }
                                            }
                                        }
                                        StructureInfo structureInfo = (StructureInfo) next;
                                        if (structureInfo == null) {
                                            return;
                                        }
                                        List list2 = structureInfo.controls;
                                        ArrayList arrayList = new ArrayList();
                                        for (Object obj : list2) {
                                            if (!Intrinsics.areEqual(((ControlInfo) obj).controlId, control3.getControlId())) {
                                                arrayList.add(obj);
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
                    Log.w("StatefulControlSubscriber", "Refresh outside of window for token:" + iBinder2);
                }
                return Unit.INSTANCE;
            }
        });
    }

    public final void onSubscribe(IBinder iBinder, IControlsSubscription iControlsSubscription) {
        Log.d("StatefulControlSubscriber", "onSubscribe");
        run(iBinder, new StatefulControlSubscriber$$ExternalSyntheticLambda2(this, iControlsSubscription, 1));
    }

    public final void run(IBinder iBinder, final Function0 function0) {
        if (Intrinsics.areEqual(this.provider.token, iBinder)) {
            this.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.controls.controller.StatefulControlSubscriber.run.1
                @Override // java.lang.Runnable
                public final void run() {
                    function0.invoke();
                }
            });
            return;
        }
        Log.w("StatefulControlSubscriber", "Provider token is not same, token = " + iBinder + ", provider.token = " + this.provider.token);
    }
}
