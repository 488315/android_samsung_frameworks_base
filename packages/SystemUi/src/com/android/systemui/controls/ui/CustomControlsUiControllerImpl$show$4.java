package com.android.systemui.controls.ui;

import android.content.ComponentName;
import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import androidx.fragment.app.BackStackRecord;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.Prefs;
import com.android.systemui.R;
import com.android.systemui.controls.ControlsMetricsLogger;
import com.android.systemui.controls.ControlsMetricsLoggerImpl;
import com.android.systemui.controls.ControlsServiceInfo;
import com.android.systemui.controls.controller.ComponentInfo;
import com.android.systemui.controls.controller.ControlsController;
import com.android.systemui.controls.controller.ControlsControllerImpl;
import com.android.systemui.controls.management.ControlsListingController;
import com.android.systemui.controls.management.adapter.MainControlAdapter;
import com.android.systemui.controls.ui.SelectedItem;
import com.android.systemui.controls.ui.fragment.MainFragment;
import com.android.systemui.controls.panels.SelectedComponentRepository;
import com.android.systemui.controls.panels.SelectedComponentRepositoryImpl;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.google.android.material.appbar.AppBarLayout;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__MutableCollectionsJVMKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
final /* synthetic */ class CustomControlsUiControllerImpl$show$4 extends FunctionReferenceImpl implements Function2 {
    public CustomControlsUiControllerImpl$show$4(Object obj) {
        super(2, obj, CustomControlsUiControllerImpl.class, "showControlsView", "showControlsView(Ljava/util/List;Ljava/util/List;)V", 0);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        Object obj3;
        Object obj4;
        SelectedItem componentItem;
        String str;
        boolean z;
        List list = (List) obj;
        List<? extends ControlsServiceInfo> list2 = (List) obj2;
        CustomControlsUiControllerImpl customControlsUiControllerImpl = (CustomControlsUiControllerImpl) this.receiver;
        int i = CustomControlsUiControllerImpl.$r8$clinit;
        customControlsUiControllerImpl.getClass();
        Log.d("CustomControlsUiControllerImpl", "showControlsView");
        customControlsUiControllerImpl.serviceInfos = list2;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (Object obj5 : list) {
            if (((ControlsSelectionItem) obj5).isPanel) {
                arrayList.add(obj5);
            } else {
                arrayList2.add(obj5);
            }
        }
        Pair pair = new Pair(arrayList, arrayList2);
        List list3 = (List) pair.component1();
        List list4 = (List) pair.component2();
        ArrayList arrayList3 = new ArrayList();
        arrayList3.addAll(list3);
        arrayList3.addAll(list4);
        CollectionsKt__MutableCollectionsJVMKt.sortWith(arrayList3, customControlsUiControllerImpl.localeComparator);
        SelectedItem selectedItem = customControlsUiControllerImpl.selectedItem;
        Iterator it = arrayList3.iterator();
        while (true) {
            if (!it.hasNext()) {
                obj3 = null;
                break;
            }
            obj3 = it.next();
            ControlsSelectionItem controlsSelectionItem = (ControlsSelectionItem) obj3;
            controlsSelectionItem.getClass();
            if (Intrinsics.areEqual(controlsSelectionItem.componentName, selectedItem.getComponentName())) {
                if (!controlsSelectionItem.isPanel) {
                    boolean z2 = selectedItem instanceof SelectedItem.PanelItem;
                }
                z = true;
            } else {
                z = false;
            }
            if (z) {
                break;
            }
        }
        ControlsSelectionItem controlsSelectionItem2 = (ControlsSelectionItem) obj3;
        if (controlsSelectionItem2 == null) {
            controlsSelectionItem2 = list3.isEmpty() ^ true ? (ControlsSelectionItem) CollectionsKt___CollectionsKt.firstOrNull(list3) : (ControlsSelectionItem) CollectionsKt___CollectionsKt.firstOrNull(list);
        }
        if (controlsSelectionItem2 != null) {
            boolean z3 = controlsSelectionItem2.isPanel;
            ComponentName componentName = controlsSelectionItem2.componentName;
            CharSequence charSequence = controlsSelectionItem2.appName;
            if (z3) {
                componentItem = new SelectedItem.PanelItem(charSequence, componentName);
            } else {
                Iterator it2 = customControlsUiControllerImpl.getAllComponentInfo().iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        obj4 = null;
                        break;
                    }
                    obj4 = it2.next();
                    if (Intrinsics.areEqual(((ComponentInfo) obj4).componentName, componentName)) {
                        break;
                    }
                }
                ComponentInfo componentInfo = (ComponentInfo) obj4;
                if (componentInfo == null) {
                    ComponentInfo.Companion.getClass();
                    componentInfo = ComponentInfo.EMPTY_COMPONENT_INFO;
                }
                componentItem = new SelectedItem.ComponentItem(charSequence, componentInfo);
            }
            if (!Intrinsics.areEqual(componentItem, customControlsUiControllerImpl.selectedItem)) {
                customControlsUiControllerImpl.selectedItem = componentItem;
                ((SelectedComponentRepositoryImpl) customControlsUiControllerImpl.selectedComponentRepository).setSelectedComponent(new SelectedComponentRepository.SelectedComponent(componentItem));
            }
            if (!(customControlsUiControllerImpl.taskViewFactory.isPresent() && z3) && z3) {
                Log.w("CustomControlsUiControllerImpl", "Not TaskViewFactory to display panel " + controlsSelectionItem2);
            } else {
                ((ControlsMetricsLoggerImpl) customControlsUiControllerImpl.controlsMetricsLogger).refreshBegin(controlsSelectionItem2.uid, !customControlsUiControllerImpl.keyguardStateController.isUnlocked());
                Log.d("CustomControlsUiControllerImpl", "showMainView()");
                customControlsUiControllerImpl.controlsUtil.getClass();
                Context context = customControlsUiControllerImpl.context;
                if (!Prefs.getBoolean(context, "ControlsOOBEManageAppsCompleted", false)) {
                    Prefs.putBoolean(context, "ControlsOOBEManageAppsCompleted", true);
                }
                String name = MainFragment.class.getName();
                if (customControlsUiControllerImpl.mainFragment == null) {
                    FragmentManager fragmentManager = customControlsUiControllerImpl.fragmentManager;
                    LifecycleOwner findFragmentByTag = fragmentManager != null ? fragmentManager.findFragmentByTag(name) : null;
                    MainFragment mainFragment = findFragmentByTag instanceof MainFragment ? (MainFragment) findFragmentByTag : null;
                    if (mainFragment == null) {
                        mainFragment = new MainFragment(customControlsUiControllerImpl.controlsActivityStarter, customControlsUiControllerImpl.layoutUtil, customControlsUiControllerImpl.saLogger, customControlsUiControllerImpl.badgeSubject, (ControlsListingController) customControlsUiControllerImpl.controlsListingController.get(), customControlsUiControllerImpl);
                    }
                    customControlsUiControllerImpl.mainFragment = mainFragment;
                }
                MainFragment mainFragment2 = customControlsUiControllerImpl.mainFragment;
                Intrinsics.checkNotNull(mainFragment2);
                if (mainFragment2.controlAdapter != null || customControlsUiControllerImpl.activityContext == null || customControlsUiControllerImpl.parent == null) {
                    str = name;
                } else {
                    Context context2 = customControlsUiControllerImpl.activityContext;
                    Intrinsics.checkNotNull(context2);
                    Lazy lazy = customControlsUiControllerImpl.controlsController;
                    ControlsController controlsController = (ControlsController) lazy.get();
                    DelayableExecutor delayableExecutor = customControlsUiControllerImpl.uiExecutor;
                    DelayableExecutor delayableExecutor2 = customControlsUiControllerImpl.bgExecutor;
                    ControlActionCoordinator controlActionCoordinator = customControlsUiControllerImpl.controlActionCoordinator;
                    CustomControlActionCoordinator customControlActionCoordinator = customControlsUiControllerImpl.customControlActionCoordinator;
                    ControlsMetricsLogger controlsMetricsLogger = customControlsUiControllerImpl.controlsMetricsLogger;
                    ViewGroup viewGroup = customControlsUiControllerImpl.parent;
                    Intrinsics.checkNotNull(viewGroup);
                    str = name;
                    MainControlAdapter mainControlAdapter = new MainControlAdapter(context2, controlsController, delayableExecutor, delayableExecutor2, controlActionCoordinator, customControlActionCoordinator, controlsMetricsLogger, (AppBarLayout) viewGroup.getRootView().requireViewById(R.id.app_bar), customControlsUiControllerImpl.layoutUtil, customControlsUiControllerImpl.controlsUtil, customControlsUiControllerImpl.controlsPositionChangedCallback, customControlsUiControllerImpl.controlsPanelCallback, customControlsUiControllerImpl.spinnerTouchCallback, customControlsUiControllerImpl.spinnerItemSelectionChangedCallback, customControlsUiControllerImpl.openAppButtonClickListener, customControlsUiControllerImpl.auiFacade, customControlsUiControllerImpl.saLogger, customControlsUiControllerImpl.badgeProvider, customControlsUiControllerImpl.controlsRuneWrapper, ((ControlsControllerImpl) ((ControlsController) lazy.get())).getCurrentUserId());
                    RecyclerView recyclerView = mainFragment2.listView;
                    if (recyclerView != null) {
                        recyclerView.setAdapter(mainControlAdapter);
                    }
                    mainFragment2.controlAdapter = mainControlAdapter;
                    customControlsUiControllerImpl.controlAdapter = mainControlAdapter;
                }
                customControlsUiControllerImpl.update(list2, customControlsUiControllerImpl.getAllComponentInfo(), customControlsUiControllerImpl.selectedItem);
                MainControlAdapter mainControlAdapter2 = customControlsUiControllerImpl.controlAdapter;
                if (mainControlAdapter2 != null) {
                    mainControlAdapter2.models = customControlsUiControllerImpl.models;
                    mainControlAdapter2.notifyDataSetChanged();
                }
                FragmentManager fragmentManager2 = customControlsUiControllerImpl.fragmentManager;
                if (fragmentManager2 != null) {
                    BackStackRecord backStackRecord = new BackStackRecord(fragmentManager2);
                    MainFragment mainFragment3 = customControlsUiControllerImpl.mainFragment;
                    Intrinsics.checkNotNull(mainFragment3);
                    backStackRecord.replace(R.id.frame_layout, mainFragment3, str);
                    backStackRecord.commitAllowingStateLoss();
                }
            }
        } else {
            Log.w("CustomControlsUiControllerImpl", "showControlsView selectionItem is null");
        }
        return Unit.INSTANCE;
    }
}
