package com.android.systemui.controls.ui;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.FrameLayout;
import androidx.fragment.app.BackStackRecord;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManagerImpl;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.R;
import com.android.systemui.controls.ControlsMetricsLogger;
import com.android.systemui.controls.ControlsServiceInfo;
import com.android.systemui.controls.controller.ComponentInfo;
import com.android.systemui.controls.controller.ControlsController;
import com.android.systemui.controls.management.ControlsListingController;
import com.android.systemui.controls.management.adapter.StatefulControlAdapter;
import com.android.systemui.controls.panels.SelectedComponentRepository;
import com.android.systemui.controls.panels.SelectedComponentRepositoryImpl;
import com.android.systemui.controls.ui.SelectedItem;
import com.android.systemui.controls.ui.fragment.MainFragment;
import com.android.systemui.controls.ui.util.ControlsActivityStarter;
import com.android.systemui.settings.UserFileManagerImpl;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.utils.SafeIconLoader;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final /* synthetic */ class SecControlsUiControllerImpl$show$4 extends FunctionReferenceImpl implements Function2 {
    public SecControlsUiControllerImpl$show$4(Object obj) {
        super(2, obj, SecControlsUiControllerImpl.class, "showControlsView", "showControlsView(Ljava/util/List;Ljava/util/List;)V", 0);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        Object obj3;
        Object obj4;
        SelectedItem componentItem;
        PendingIntent pendingIntent;
        List list = (List) obj;
        List<? extends ControlsServiceInfo> list2 = (List) obj2;
        SecControlsUiControllerImpl secControlsUiControllerImpl = (SecControlsUiControllerImpl) this.receiver;
        int i = SecControlsUiControllerImpl.$r8$clinit;
        secControlsUiControllerImpl.getClass();
        Log.d("SecControlsUiControllerImpl", "showControlsView");
        secControlsUiControllerImpl.serviceInfos = list2;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (Object obj5 : list) {
            if (((SecSelectionItem) obj5).isPanel) {
                arrayList.add(obj5);
            } else {
                arrayList2.add(obj5);
            }
        }
        Pair pair = new Pair(arrayList, arrayList2);
        List list3 = (List) pair.component1();
        List list4 = (List) pair.component2();
        ArrayList arrayList3 = new ArrayList();
        List list5 = list3;
        arrayList3.addAll(list5);
        arrayList3.addAll(list4);
        CollectionsKt__MutableCollectionsJVMKt.sortWith(arrayList3, secControlsUiControllerImpl.localeComparator);
        SelectedItem selectedItem = secControlsUiControllerImpl.selectedItem;
        int size = arrayList3.size();
        int i2 = 0;
        while (true) {
            if (i2 >= size) {
                obj3 = null;
                break;
            }
            obj3 = arrayList3.get(i2);
            i2++;
            SecSelectionItem secSelectionItem = (SecSelectionItem) obj3;
            if (Intrinsics.areEqual(secSelectionItem.componentName, selectedItem.getComponentName())) {
                if (!secSelectionItem.isPanel) {
                    boolean z = selectedItem instanceof SelectedItem.PanelItem;
                }
            }
        }
        SecSelectionItem secSelectionItem2 = (SecSelectionItem) obj3;
        if (secSelectionItem2 == null) {
            secSelectionItem2 = !list5.isEmpty() ? (SecSelectionItem) CollectionsKt___CollectionsKt.firstOrNull(list3) : (SecSelectionItem) CollectionsKt___CollectionsKt.firstOrNull(list);
        }
        if (secSelectionItem2 != null) {
            boolean z2 = secSelectionItem2.isPanel;
            if (z2) {
                componentItem = new SelectedItem.PanelItem(secSelectionItem2.appName, secSelectionItem2.componentName);
            } else {
                CharSequence charSequence = secSelectionItem2.appName;
                List list6 = secControlsUiControllerImpl.allComponentInfo;
                if (list6 == null) {
                    list6 = null;
                }
                Iterator it = list6.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        obj4 = null;
                        break;
                    }
                    obj4 = it.next();
                    if (Intrinsics.areEqual(((ComponentInfo) obj4).componentName, secSelectionItem2.componentName)) {
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
            if (!componentItem.equals(secControlsUiControllerImpl.selectedItem)) {
                secControlsUiControllerImpl.selectedItem = componentItem;
                SelectedComponentRepository.SelectedComponent selectedComponent = new SelectedComponentRepository.SelectedComponent(componentItem);
                SelectedComponentRepositoryImpl selectedComponentRepositoryImpl = (SelectedComponentRepositoryImpl) secControlsUiControllerImpl.selectedComponentRepository;
                SharedPreferences.Editor edit = ((UserFileManagerImpl) selectedComponentRepositoryImpl.userFileManager).getSharedPreferences$1(((UserTrackerImpl) selectedComponentRepositoryImpl.userTracker).getUserId(), SystemUIAnalytics.CONTROL_PREF_NAME).edit();
                ComponentName componentName = selectedComponent.componentName;
                edit.putString("controls_component", componentName != null ? componentName.flattenToString() : null).putString("controls_structure", selectedComponent.name).putBoolean("controls_is_panel", selectedComponent.isPanel).apply();
            }
            if (!(secControlsUiControllerImpl.taskViewFactory.isPresent() && z2) && z2) {
                Log.w("SecControlsUiControllerImpl", "Not TaskViewFactory to display panel " + secSelectionItem2);
            } else {
                boolean z3 = !secControlsUiControllerImpl.keyguardStateController.isUnlocked();
                ControlsMetricsLogger controlsMetricsLogger = secControlsUiControllerImpl.controlsMetricsLogger;
                int i3 = secSelectionItem2.uid;
                controlsMetricsLogger.refreshBegin(i3, z3);
                Log.d("SecControlsUiControllerImpl", "showMainView");
                String name = MainFragment.class.getName();
                String packageName = secSelectionItem2.componentName.getPackageName();
                Lazy lazy = secControlsUiControllerImpl.controlsController;
                SafeIconLoader create = secControlsUiControllerImpl.safeIconLoaderFactory.create(i3, packageName, ((ControlsController) lazy.get()).getCurrentUserId());
                if (secControlsUiControllerImpl.mainFragment == null) {
                    FragmentManagerImpl fragmentManagerImpl = secControlsUiControllerImpl.fragmentManager;
                    Fragment findFragmentByTag = fragmentManagerImpl != null ? fragmentManagerImpl.findFragmentByTag(name) : null;
                    MainFragment mainFragment = findFragmentByTag instanceof MainFragment ? (MainFragment) findFragmentByTag : null;
                    if (mainFragment == null) {
                        mainFragment = new MainFragment((ControlsActivityStarter) secControlsUiControllerImpl.controlsActivityStarter.get(), secControlsUiControllerImpl.layoutUtil, secControlsUiControllerImpl.saLogger, secControlsUiControllerImpl.badgeSubject, (ControlsListingController) secControlsUiControllerImpl.controlsListingController.get(), secControlsUiControllerImpl);
                    }
                    secControlsUiControllerImpl.mainFragment = mainFragment;
                }
                MainFragment mainFragment2 = secControlsUiControllerImpl.mainFragment;
                mainFragment2.getClass();
                if (mainFragment2.controlAdapter == null && secControlsUiControllerImpl.activityContext != null && secControlsUiControllerImpl.parent != null) {
                    Context context = secControlsUiControllerImpl.activityContext;
                    context.getClass();
                    StatefulControlAdapter statefulControlAdapter = new StatefulControlAdapter(context, (ControlsController) lazy.get(), secControlsUiControllerImpl.uiExecutor, secControlsUiControllerImpl.bgExecutor, secControlsUiControllerImpl.controlActionCoordinator, secControlsUiControllerImpl.secControlActionCoordinator, secControlsUiControllerImpl.controlsMetricsLogger, secControlsUiControllerImpl.layoutUtil, secControlsUiControllerImpl.controlsUtil, secControlsUiControllerImpl.controlsPositionChangedCallback, secControlsUiControllerImpl.spinnerTouchCallback, secControlsUiControllerImpl.spinnerItemSelectionChangedCallback, create, secControlsUiControllerImpl.openAppButtonClickListener, secControlsUiControllerImpl.auiFacade, secControlsUiControllerImpl.saLogger, secControlsUiControllerImpl.badgeProvider, ((ControlsController) lazy.get()).getCurrentUserId());
                    MainFragment mainFragment3 = secControlsUiControllerImpl.mainFragment;
                    if (mainFragment3 != null) {
                        RecyclerView recyclerView = mainFragment3.listView;
                        if (recyclerView != null) {
                            recyclerView.setAdapter(statefulControlAdapter);
                        }
                        mainFragment3.controlAdapter = statefulControlAdapter;
                    }
                    secControlsUiControllerImpl.controlAdapter = statefulControlAdapter;
                }
                FrameLayout frameLayout = mainFragment2.panelView;
                if (frameLayout != null && (pendingIntent = secControlsUiControllerImpl.panelPendingIntent) != null) {
                    secControlsUiControllerImpl.controlsPanelCallback.onPanelUpdated(frameLayout, pendingIntent);
                }
                List<ComponentInfo> list7 = secControlsUiControllerImpl.allComponentInfo;
                secControlsUiControllerImpl.update(list2, list7 != null ? list7 : null, secControlsUiControllerImpl.selectedItem);
                StatefulControlAdapter statefulControlAdapter2 = secControlsUiControllerImpl.controlAdapter;
                if (statefulControlAdapter2 != null) {
                    statefulControlAdapter2.models = secControlsUiControllerImpl.models;
                    statefulControlAdapter2.notifyDataSetChanged();
                }
                FragmentManagerImpl fragmentManagerImpl2 = secControlsUiControllerImpl.fragmentManager;
                if (fragmentManagerImpl2 != null) {
                    BackStackRecord backStackRecord = new BackStackRecord(fragmentManagerImpl2);
                    MainFragment mainFragment4 = secControlsUiControllerImpl.mainFragment;
                    mainFragment4.getClass();
                    backStackRecord.replace(R.id.frame_layout, mainFragment4, name);
                    backStackRecord.commitInternal(true, true);
                }
            }
        } else {
            Log.w("SecControlsUiControllerImpl", "showControlsView selectionItem is null");
        }
        return Unit.INSTANCE;
    }
}
