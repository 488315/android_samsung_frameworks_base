package com.android.systemui.controls.ui;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.fragment.app.FragmentManagerImpl;
import com.android.systemui.Dumpable;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.controls.ControlsMetricsLogger;
import com.android.systemui.controls.ControlsServiceInfo;
import com.android.systemui.controls.controller.ComponentInfo;
import com.android.systemui.controls.controller.ControlInfo;
import com.android.systemui.controls.controller.ControlsBindingControllerImpl;
import com.android.systemui.controls.controller.ControlsController;
import com.android.systemui.controls.controller.ControlsControllerImpl;
import com.android.systemui.controls.controller.ControlsProviderLifecycleManager;
import com.android.systemui.controls.controller.ControlsProviderLifecycleManager$bindService$1;
import com.android.systemui.controls.controller.Favorites;
import com.android.systemui.controls.controller.SecControlsController;
import com.android.systemui.controls.controller.StructureInfo;
import com.android.systemui.controls.controller.util.BadgeProvider;
import com.android.systemui.controls.controller.util.BadgeSubject;
import com.android.systemui.controls.management.ControlsListingController;
import com.android.systemui.controls.management.ControlsListingControllerImpl;
import com.android.systemui.controls.management.adapter.StatefulControlAdapter;
import com.android.systemui.controls.management.model.MainComponentModel;
import com.android.systemui.controls.management.model.MainControlModel;
import com.android.systemui.controls.management.model.MainModel;
import com.android.systemui.controls.panels.AuthorizedPanelsRepository;
import com.android.systemui.controls.panels.SecSelectedComponentRepository;
import com.android.systemui.controls.panels.SecSelectedComponentRepositoryImpl;
import com.android.systemui.controls.panels.SelectedComponentRepository;
import com.android.systemui.controls.settings.ControlsSettingsRepository;
import com.android.systemui.controls.settings.ControlsSettingsRepositoryImpl;
import com.android.systemui.controls.ui.RenderInfo;
import com.android.systemui.controls.ui.SelectedItem;
import com.android.systemui.controls.ui.fragment.MainFragment;
import com.android.systemui.controls.ui.fragment.NoAppFragment;
import com.android.systemui.controls.ui.fragment.NoFavoriteFragment;
import com.android.systemui.controls.ui.util.AUIFacade;
import com.android.systemui.controls.ui.util.LayoutUtil;
import com.android.systemui.controls.util.ControlsUtil;
import com.android.systemui.controls.util.SALogger;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.DumpUtilsKt;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.utils.SafeIconLoader;
import com.android.wm.shell.taskview.TaskView;
import com.android.wm.shell.taskview.TaskViewFactoryController;
import dagger.Lazy;
import java.io.PrintWriter;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SecControlsUiControllerImpl implements ControlsUiController, SecControlsUiController, Dumpable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public Context activityContext;
    public final ActivityStarter activityStarter;
    public boolean adapterNeedToUpdateDataSet;
    public List allComponentInfo;
    public final AUIFacade auiFacade;
    public final AuthorizedPanelsRepository authorizedPanelsRepository;
    public final BadgeProvider badgeProvider;
    public final BadgeSubject badgeSubject;
    public final DelayableExecutor bgExecutor;
    public final MainComponentModel componentModel;
    public final Context context;
    public final ControlActionCoordinator controlActionCoordinator;
    public StatefulControlAdapter controlAdapter;
    public final Lazy controlsActivityStarter;
    public final Lazy controlsController;
    public final Lazy controlsListingController;
    public final ControlsMetricsLogger controlsMetricsLogger;
    public final SecControlsUiControllerImpl$controlsPanelCallback$1 controlsPanelCallback;
    public final SecControlsUiControllerImpl$controlsPositionChangedCallback$1 controlsPositionChangedCallback;
    public final ControlsSettingsRepository controlsSettingsRepository;
    public final ControlsUtil controlsUtil;
    public FragmentManagerImpl fragmentManager;
    public boolean hidden;
    public boolean isAddedTaskView;
    public boolean isChanged;
    public boolean isShowOverLockscreenWhenLocked;
    public final KeyguardStateController keyguardStateController;
    public PendingIntent launchingPendingIntent;
    public final LayoutUtil layoutUtil;
    public SecControlsUiControllerImpl$createCallback$1 listingCallback;
    public final SecControlsUiControllerImpl$special$$inlined$compareBy$1 localeComparator;
    public final LogWrapper logWrapper;
    public MainFragment mainFragment;
    public final List models;
    public NoAppFragment noAppFragment;
    public NoFavoriteFragment noFavoriteFragment;
    public SecControlsActivity$onStart$1$1 onDismiss;
    public final SecControlsUiControllerImpl$openAppButtonClickListener$1 openAppButtonClickListener;
    public PendingIntent panelPendingIntent;
    public FrameLayout panelView;
    public ViewGroup parent;
    public final SALogger saLogger;
    public final SafeIconLoader.Factory safeIconLoaderFactory;
    public final SecControlActionCoordinator secControlActionCoordinator;
    public final Lazy secControlsController;
    public final SecSelectedComponentRepository secSelectedComponentRepository;
    public final SelectedComponentRepository selectedComponentRepository;
    public SelectedItem selectedItem;
    public List serviceInfos;
    public final SecControlsUiControllerImpl$spinnerItemSelectionChangedCallback$1 spinnerItemSelectionChangedCallback;
    public final SecControlsUiControllerImpl$spinnerTouchCallback$1 spinnerTouchCallback;
    public PanelTaskViewController taskViewController;
    public final Optional taskViewFactory;
    public final DelayableExecutor uiExecutor;
    public final UserTracker userTracker;
    public List verificationStructureInfos;

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

    /* JADX WARN: Type inference failed for: r1v28, types: [com.android.systemui.controls.ui.SecControlsUiControllerImpl$openAppButtonClickListener$1] */
    /* JADX WARN: Type inference failed for: r3v1, types: [com.android.systemui.controls.ui.SecControlsUiControllerImpl$special$$inlined$compareBy$1] */
    public SecControlsUiControllerImpl(Lazy lazy, Context context, DelayableExecutor delayableExecutor, DelayableExecutor delayableExecutor2, Lazy lazy2, ControlActionCoordinator controlActionCoordinator, ActivityStarter activityStarter, ControlsMetricsLogger controlsMetricsLogger, KeyguardStateController keyguardStateController, UserTracker userTracker, Optional<TaskViewFactoryController.TaskViewFactoryImpl> optional, ControlsSettingsRepository controlsSettingsRepository, AuthorizedPanelsRepository authorizedPanelsRepository, SelectedComponentRepository selectedComponentRepository, SafeIconLoader.Factory factory, DumpManager dumpManager, LogWrapper logWrapper, Lazy lazy3, SecControlActionCoordinator secControlActionCoordinator, Lazy lazy4, SecSelectedComponentRepository secSelectedComponentRepository, SharedPreferences sharedPreferences, LayoutUtil layoutUtil, ControlsUtil controlsUtil, AUIFacade aUIFacade, SALogger sALogger, BadgeSubject badgeSubject, BadgeProvider badgeProvider) {
        this.controlsController = lazy;
        this.context = context;
        this.uiExecutor = delayableExecutor;
        this.bgExecutor = delayableExecutor2;
        this.controlsListingController = lazy2;
        this.controlActionCoordinator = controlActionCoordinator;
        this.activityStarter = activityStarter;
        this.controlsMetricsLogger = controlsMetricsLogger;
        this.keyguardStateController = keyguardStateController;
        this.userTracker = userTracker;
        this.taskViewFactory = optional;
        this.controlsSettingsRepository = controlsSettingsRepository;
        this.authorizedPanelsRepository = authorizedPanelsRepository;
        this.selectedComponentRepository = selectedComponentRepository;
        this.safeIconLoaderFactory = factory;
        this.logWrapper = logWrapper;
        this.secControlsController = lazy3;
        this.secControlActionCoordinator = secControlActionCoordinator;
        this.controlsActivityStarter = lazy4;
        this.secSelectedComponentRepository = secSelectedComponentRepository;
        this.layoutUtil = layoutUtil;
        this.controlsUtil = controlsUtil;
        this.auiFacade = aUIFacade;
        this.saLogger = sALogger;
        this.badgeSubject = badgeSubject;
        this.badgeProvider = badgeProvider;
        SelectedItem.Companion.getClass();
        this.selectedItem = SelectedItem.EMPTY_SELECTION_COMPONENT;
        final Collator collator = Collator.getInstance(context.getResources().getConfiguration().getLocales().get(0));
        this.localeComparator = new Comparator() { // from class: com.android.systemui.controls.ui.SecControlsUiControllerImpl$special$$inlined$compareBy$1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return collator.compare(((SecSelectionItem) obj).getAppName(), ((SecSelectionItem) obj2).getAppName());
            }
        };
        this.hidden = true;
        this.models = new ArrayList();
        ArrayList arrayList = new ArrayList();
        ComponentInfo.Companion.getClass();
        this.componentModel = new MainComponentModel(arrayList, ComponentInfo.EMPTY_COMPONENT, false);
        this.verificationStructureInfos = new ArrayList();
        this.serviceInfos = new ArrayList();
        DumpManager.registerDumpable$default(dumpManager, SecControlsUiControllerImpl.class.getName(), this);
        this.controlsPositionChangedCallback = new SecControlsUiControllerImpl$controlsPositionChangedCallback$1(this);
        this.controlsPanelCallback = new SecControlsUiControllerImpl$controlsPanelCallback$1(this);
        this.openAppButtonClickListener = new View.OnClickListener() { // from class: com.android.systemui.controls.ui.SecControlsUiControllerImpl$openAppButtonClickListener$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SecControlsUiControllerImpl.this.saLogger.sendEvent(SALogger.Event.LaunchSmartThings.INSTANCE);
                try {
                    if (SecControlsUiControllerImpl.this.controlsUtil.isSecureLocked()) {
                        final SecControlsUiControllerImpl secControlsUiControllerImpl = SecControlsUiControllerImpl.this;
                        secControlsUiControllerImpl.activityStarter.dismissKeyguardThenExecute(new ActivityStarter.OnDismissAction() { // from class: com.android.systemui.controls.ui.SecControlsUiControllerImpl$openAppButtonClickListener$1.1
                            @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
                            public final boolean onDismiss() {
                                PendingIntent pendingIntent = SecControlsUiControllerImpl.this.launchingPendingIntent;
                                if (pendingIntent == null) {
                                    return true;
                                }
                                pendingIntent.send();
                                return true;
                            }
                        }, new Runnable() { // from class: com.android.systemui.controls.ui.SecControlsUiControllerImpl$openAppButtonClickListener$1.2
                            @Override // java.lang.Runnable
                            public final void run() {
                            }
                        }, true);
                    } else {
                        PendingIntent pendingIntent = SecControlsUiControllerImpl.this.launchingPendingIntent;
                        if (pendingIntent != null) {
                            pendingIntent.send();
                        }
                    }
                } catch (PendingIntent.CanceledException unused) {
                    Log.e("SecControlsUiControllerImpl", "openAppButtonClickListener CanceledException");
                }
            }
        };
        this.spinnerTouchCallback = new SecControlsUiControllerImpl$spinnerTouchCallback$1(this);
        this.spinnerItemSelectionChangedCallback = new SecControlsUiControllerImpl$spinnerItemSelectionChangedCallback$1(this);
    }

    public static final ControlsServiceInfo access$getComponent(SecControlsUiControllerImpl secControlsUiControllerImpl, ComponentInfo componentInfo) {
        Object obj;
        Iterator it = secControlsUiControllerImpl.serviceInfos.iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            obj = it.next();
            if (Intrinsics.areEqual(((ControlsServiceInfo) obj).serviceInfo.applicationInfo.packageName, componentInfo.componentName.getPackageName())) {
                break;
            }
        }
        return (ControlsServiceInfo) obj;
    }

    public static final void access$listAdjustmentIfNeeded(SecControlsUiControllerImpl secControlsUiControllerImpl, List list) {
        Object obj;
        ControlInfo controlInfo;
        secControlsUiControllerImpl.getClass();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            CharSequence charSequence = (CharSequence) it.next();
            List list2 = secControlsUiControllerImpl.models;
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = (ArrayList) list2;
            int size = arrayList2.size();
            int i = 0;
            while (i < size) {
                Object obj2 = arrayList2.get(i);
                i++;
                if (obj2 instanceof MainControlModel) {
                    arrayList.add(obj2);
                }
            }
            ArrayList arrayList3 = new ArrayList();
            int size2 = arrayList.size();
            int i2 = 0;
            while (i2 < size2) {
                Object obj3 = arrayList.get(i2);
                i2++;
                if (Intrinsics.areEqual(((MainControlModel) obj3).structure, charSequence)) {
                    arrayList3.add(obj3);
                }
            }
            int size3 = arrayList3.size();
            int i3 = 0;
            while (true) {
                if (i3 >= size3) {
                    obj = null;
                    break;
                }
                obj = arrayList3.get(i3);
                i3++;
                if (((MainControlModel) obj).getType() == MainModel.Type.STRUCTURE) {
                    break;
                }
            }
            MainControlModel mainControlModel = (MainControlModel) obj;
            ArrayList arrayList4 = new ArrayList();
            int size4 = arrayList3.size();
            int i4 = 0;
            while (i4 < size4) {
                Object obj4 = arrayList3.get(i4);
                i4++;
                if (((MainControlModel) obj4).getType() == MainModel.Type.CONTROL) {
                    arrayList4.add(obj4);
                }
            }
            ArrayList arrayList5 = new ArrayList();
            int size5 = arrayList3.size();
            int i5 = 0;
            while (i5 < size5) {
                Object obj5 = arrayList3.get(i5);
                i5++;
                if (((MainControlModel) obj5).getType() == MainModel.Type.SMALL_CONTROL) {
                    arrayList5.add(obj5);
                }
            }
            LogWrapper logWrapper = secControlsUiControllerImpl.logWrapper;
            if (mainControlModel == null && (!arrayList4.isEmpty() || !arrayList5.isEmpty())) {
                int indexOf = !arrayList4.isEmpty() ? ((ArrayList) secControlsUiControllerImpl.models).indexOf(arrayList4.get(0)) : ((ArrayList) secControlsUiControllerImpl.models).indexOf(arrayList5.get(0));
                ((ArrayList) secControlsUiControllerImpl.models).add(indexOf, new MainControlModel(String.valueOf(charSequence), null, false, 4, null));
                StatefulControlAdapter statefulControlAdapter = secControlsUiControllerImpl.controlAdapter;
                if (statefulControlAdapter != null) {
                    statefulControlAdapter.notifyItemInserted(indexOf);
                }
                List list3 = secControlsUiControllerImpl.models;
                ArrayList arrayList6 = new ArrayList();
                ArrayList arrayList7 = (ArrayList) list3;
                int size6 = arrayList7.size();
                int i6 = 0;
                while (i6 < size6) {
                    Object obj6 = arrayList7.get(i6);
                    i6++;
                    if (obj6 instanceof MainControlModel) {
                        arrayList6.add(obj6);
                    }
                }
                ArrayList arrayList8 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList6, 10));
                int size7 = arrayList6.size();
                int i7 = 0;
                while (i7 < size7) {
                    Object obj7 = arrayList6.get(i7);
                    i7++;
                    ControlWithState controlWithState = ((MainControlModel) obj7).controlWithState;
                    arrayList8.add((controlWithState == null || (controlInfo = controlWithState.ci) == null) ? null : controlInfo.controlTitle);
                }
                logWrapper.dp("SecControlsUiControllerImpl", "listAdjustmentIfNeeded-notifyItemInserted: structureName=" + ((Object) charSequence) + ", index=" + indexOf + ", models=" + arrayList8);
            }
            if (mainControlModel != null && arrayList4.isEmpty() && arrayList5.isEmpty()) {
                int indexOf2 = ((ArrayList) secControlsUiControllerImpl.models).indexOf(mainControlModel);
                ((ArrayList) secControlsUiControllerImpl.models).remove(indexOf2);
                StatefulControlAdapter statefulControlAdapter2 = secControlsUiControllerImpl.controlAdapter;
                if (statefulControlAdapter2 != null) {
                    statefulControlAdapter2.notifyItemRemoved(indexOf2);
                }
                logWrapper.dp("SecControlsUiControllerImpl", "listAdjustmentIfNeeded-notifyItemRemoved: structureName=" + ((Object) charSequence));
            }
        }
    }

    public static final void access$reload(SecControlsUiControllerImpl secControlsUiControllerImpl, SelectedItem selectedItem) {
        if (secControlsUiControllerImpl.hidden) {
            return;
        }
        ControlsControllerImpl controlsControllerImpl = (ControlsControllerImpl) ((ControlsController) secControlsUiControllerImpl.controlsController.get());
        if (controlsControllerImpl.confirmAvailability()) {
            ((ControlsBindingControllerImpl) controlsControllerImpl.bindingController).unsubscribe();
        }
        PanelTaskViewController panelTaskViewController = secControlsUiControllerImpl.taskViewController;
        if (panelTaskViewController != null) {
            TaskView taskView = panelTaskViewController.taskView;
            taskView.mTaskViewController.removeTaskView(taskView.mTaskViewTaskController, null);
        }
        secControlsUiControllerImpl.taskViewController = null;
        FrameLayout frameLayout = secControlsUiControllerImpl.panelView;
        if (frameLayout != null) {
            frameLayout.removeAllViews();
        }
        FrameLayout frameLayout2 = secControlsUiControllerImpl.panelView;
        if (frameLayout2 != null) {
            frameLayout2.setVisibility(8);
        }
        secControlsUiControllerImpl.panelPendingIntent = null;
        MainFragment mainFragment = secControlsUiControllerImpl.mainFragment;
        if (mainFragment != null) {
            mainFragment.panelPendingIntent = null;
        }
        secControlsUiControllerImpl.isAddedTaskView = false;
        List activeFavoritesComponent = ((ControlsControllerImpl) ((SecControlsController) secControlsUiControllerImpl.secControlsController.get())).getActiveFavoritesComponent();
        secControlsUiControllerImpl.allComponentInfo = activeFavoritesComponent;
        secControlsUiControllerImpl.update(secControlsUiControllerImpl.serviceInfos, activeFavoritesComponent, selectedItem);
        List list = secControlsUiControllerImpl.allComponentInfo;
        Log.d("SecControlsUiControllerImpl", "reload selected = " + selectedItem + ", allComponentInfo = " + (list != null ? list : null));
        if (secControlsUiControllerImpl.adapterNeedToUpdateDataSet) {
            StatefulControlAdapter statefulControlAdapter = secControlsUiControllerImpl.controlAdapter;
            if (statefulControlAdapter != null) {
                statefulControlAdapter.notifyDataSetChanged();
            }
            secControlsUiControllerImpl.adapterNeedToUpdateDataSet = false;
        }
    }

    public static final void access$showEmptyStructureIfNeeded(SecControlsUiControllerImpl secControlsUiControllerImpl) {
        Object obj;
        List list = secControlsUiControllerImpl.models;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = (ArrayList) list;
        int size = arrayList2.size();
        int i = 0;
        while (i < size) {
            Object obj2 = arrayList2.get(i);
            i++;
            if (obj2 instanceof MainControlModel) {
                arrayList.add(obj2);
            }
        }
        ArrayList arrayList3 = new ArrayList();
        int size2 = arrayList.size();
        int i2 = 0;
        while (i2 < size2) {
            Object obj3 = arrayList.get(i2);
            i2++;
            if (((MainControlModel) obj3).getType() == MainModel.Type.STRUCTURE) {
                arrayList3.add(obj3);
            }
        }
        int size3 = arrayList3.size();
        int i3 = 0;
        while (true) {
            if (i3 >= size3) {
                obj = null;
                break;
            }
            obj = arrayList3.get(i3);
            i3++;
            if (TextUtils.isEmpty(((MainControlModel) obj).structure)) {
                break;
            }
        }
        MainControlModel mainControlModel = (MainControlModel) obj;
        if (mainControlModel != null) {
            boolean z = arrayList3.size() == 1;
            if (mainControlModel.needToHide != z) {
                mainControlModel.needToHide = z;
                StatefulControlAdapter statefulControlAdapter = secControlsUiControllerImpl.controlAdapter;
                if (statefulControlAdapter != null) {
                    statefulControlAdapter.notifyItemChanged(((ArrayList) secControlsUiControllerImpl.models).indexOf(mainControlModel));
                }
            }
        }
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("SecControlsUiControllerImpl:");
        IndentingPrintWriter asIndenting = DumpUtilsKt.asIndenting(printWriter);
        if (asIndenting != null) {
            asIndenting.increaseIndent();
        }
        asIndenting.println("hidden: " + this.hidden);
        asIndenting.println("selectedItem: " + this.selectedItem);
        asIndenting.println("setting: " + ((ControlsSettingsRepositoryImpl) this.controlsSettingsRepository).allowActionOnTrivialControlsInLockscreen.$$delegate_0.getValue());
        asIndenting.decreaseIndent();
    }

    public final List getPanelServiceInfos() {
        List list = this.serviceInfos;
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            if (((ControlsServiceInfo) obj).panelActivity != null) {
                arrayList.add(obj);
            }
        }
        return new ArrayList(arrayList);
    }

    public final SelectedItem getPreferredComponentSelectedItem(List list) {
        SelectedComponentRepository.SelectedComponent selectedComponent;
        ComponentName componentName;
        Object obj;
        Object obj2;
        if (((ArrayList) list).isEmpty()) {
            SelectedItem.Companion.getClass();
            return SelectedItem.EMPTY_SELECTION_COMPONENT;
        }
        SecSelectedComponentRepository secSelectedComponentRepository = this.secSelectedComponentRepository;
        selectedComponent = ((SecSelectedComponentRepositoryImpl) secSelectedComponentRepository).getSelectedComponent(UserHandle.CURRENT);
        if (selectedComponent == null || (componentName = selectedComponent.componentName) == null) {
            ComponentInfo.Companion.getClass();
            componentName = ComponentInfo.EMPTY_COMPONENT;
        }
        Iterator it = list.iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            obj = it.next();
            if (Intrinsics.areEqual(componentName, ((ComponentInfo) obj).componentName)) {
                break;
            }
        }
        ComponentInfo componentInfo = (ComponentInfo) obj;
        if (componentInfo == null && (componentInfo = (ComponentInfo) CollectionsKt___CollectionsKt.firstOrNull(list)) == null) {
            SelectedItem.Companion.getClass();
            return SelectedItem.EMPTY_SELECTION_COMPONENT;
        }
        if (Intrinsics.areEqual(componentInfo.componentName, selectedComponent != null ? selectedComponent.componentName : null) && selectedComponent != null && selectedComponent.isPanel) {
            SelectedItem.PanelItem panelItem = new SelectedItem.PanelItem(selectedComponent.name, componentInfo.componentName);
            ((SecSelectedComponentRepositoryImpl) secSelectedComponentRepository).setSelectedComponent(new SelectedComponentRepository.SelectedComponent(panelItem));
            return panelItem;
        }
        ArrayList arrayList = (ArrayList) ((ControlsListingControllerImpl) ((ControlsListingController) this.controlsListingController.get())).getCurrentServices();
        int size = arrayList.size();
        int i = 0;
        while (true) {
            if (i >= size) {
                obj2 = null;
                break;
            }
            obj2 = arrayList.get(i);
            i++;
            if (Intrinsics.areEqual(componentInfo.componentName, ((ControlsServiceInfo) obj2).componentName)) {
                break;
            }
        }
        ControlsServiceInfo controlsServiceInfo = (ControlsServiceInfo) obj2;
        if ((controlsServiceInfo != null ? controlsServiceInfo.panelActivity : null) != null) {
            SelectedItem.PanelItem panelItem2 = new SelectedItem.PanelItem(controlsServiceInfo.loadLabel(), componentInfo.componentName);
            ((SecSelectedComponentRepositoryImpl) secSelectedComponentRepository).setSelectedComponent(new SelectedComponentRepository.SelectedComponent(panelItem2));
            return panelItem2;
        }
        SelectedItem.ComponentItem componentItem = new SelectedItem.ComponentItem(controlsServiceInfo != null ? controlsServiceInfo.loadLabel() : "", componentInfo);
        ((SecSelectedComponentRepositoryImpl) secSelectedComponentRepository).setSelectedComponent(new SelectedComponentRepository.SelectedComponent(componentItem));
        return componentItem;
    }

    public final List<StructureInfo> getStructureInfosByUI(ComponentName componentName) {
        Object obj;
        Iterator it = getPanelServiceInfos().iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            obj = it.next();
            if (Intrinsics.areEqual(((ControlsServiceInfo) obj).serviceInfo.applicationInfo.packageName, componentName.getPackageName())) {
                break;
            }
        }
        if (((ControlsServiceInfo) obj) != null) {
            StructureInfo structureInfo = new StructureInfo(componentName, "", EmptyList.INSTANCE, false, 8, null);
            structureInfo.active = true;
            Unit unit = Unit.INSTANCE;
            return CollectionsKt__CollectionsKt.mutableListOf(structureInfo);
        }
        List list = this.models;
        ArrayList arrayList = new ArrayList();
        for (Object obj2 : list) {
            if (obj2 instanceof MainControlModel) {
                arrayList.add(obj2);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        int size = arrayList.size();
        int i = 0;
        int i2 = 0;
        while (i2 < size) {
            Object obj3 = arrayList.get(i2);
            i2++;
            MainControlModel mainControlModel = (MainControlModel) obj3;
            if (mainControlModel.getType() == MainModel.Type.CONTROL || mainControlModel.getType() == MainModel.Type.SMALL_CONTROL) {
                arrayList2.add(obj3);
            }
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        int size2 = arrayList2.size();
        while (i < size2) {
            Object obj4 = arrayList2.get(i);
            i++;
            String str = ((MainControlModel) obj4).structure;
            Object obj5 = linkedHashMap.get(str);
            if (obj5 == null) {
                obj5 = new ArrayList();
                linkedHashMap.put(str, obj5);
            }
            ((List) obj5).add(obj4);
        }
        ArrayList arrayList3 = new ArrayList();
        for (Map.Entry entry : linkedHashMap.entrySet()) {
            String str2 = (String) entry.getKey();
            List list2 = (List) entry.getValue();
            ArrayList arrayList4 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
            Iterator it2 = list2.iterator();
            while (it2.hasNext()) {
                ControlWithState controlWithState = ((MainControlModel) it2.next()).controlWithState;
                controlWithState.getClass();
                arrayList4.add(controlWithState.ci);
            }
            StructureInfo structureInfo2 = new StructureInfo(componentName, str2, new ArrayList(arrayList4), false, 8, null);
            structureInfo2.active = true;
            arrayList3.add(structureInfo2);
        }
        return arrayList3;
    }

    public final void hide(ViewGroup viewGroup) {
        Boolean bool;
        if (!Intrinsics.areEqual(viewGroup, this.parent)) {
            Log.d("SecControlsUiControllerImpl", "hide parent is diff");
            return;
        }
        Log.d("SecControlsUiControllerImpl", "hide");
        this.hidden = true;
        if (this.controlAdapter != null) {
            Iterator it = ((LinkedHashMap) StatefulControlAdapter.controlViewHolders).entrySet().iterator();
            while (it.hasNext()) {
                ControlViewHolder controlViewHolder = (ControlViewHolder) ((Map.Entry) it.next()).getValue();
                Dialog dialog = controlViewHolder.lastChallengeDialog;
                if (dialog != null) {
                    dialog.dismiss();
                }
                controlViewHolder.lastChallengeDialog = null;
                Dialog dialog2 = controlViewHolder.visibleDialog;
                if (dialog2 != null) {
                    dialog2.dismiss();
                }
                controlViewHolder.visibleDialog = null;
            }
        }
        ControlActionCoordinatorImpl controlActionCoordinatorImpl = (ControlActionCoordinatorImpl) this.controlActionCoordinator;
        Context context = controlActionCoordinatorImpl.activityContext;
        Activity activity = context instanceof Activity ? (Activity) context : null;
        if (activity != null) {
            bool = Boolean.valueOf(activity.isFinishing() || activity.isDestroyed());
        } else {
            bool = null;
        }
        if (Intrinsics.areEqual(bool, Boolean.TRUE)) {
            controlActionCoordinatorImpl.dialog = null;
        } else {
            DetailDialog detailDialog = controlActionCoordinatorImpl.dialog;
            if (detailDialog != null && detailDialog.isShowing()) {
                DetailDialog detailDialog2 = controlActionCoordinatorImpl.dialog;
                if (detailDialog2 != null) {
                    detailDialog2.dismiss();
                }
                controlActionCoordinatorImpl.dialog = null;
            }
        }
        FrameLayout frameLayout = this.panelView;
        if (frameLayout != null) {
            frameLayout.removeAllViews();
        }
        PanelTaskViewController panelTaskViewController = this.taskViewController;
        if (panelTaskViewController != null) {
            TaskView taskView = panelTaskViewController.taskView;
            taskView.mTaskViewController.removeTaskView(taskView.mTaskViewTaskController, null);
        }
        this.taskViewController = null;
        this.panelPendingIntent = null;
        MainFragment mainFragment = this.mainFragment;
        if (mainFragment != null) {
            mainFragment.panelPendingIntent = null;
        }
        this.isAddedTaskView = false;
        saveFavorites(this.selectedItem.getComponentName());
        ControlsControllerImpl controlsControllerImpl = (ControlsControllerImpl) ((ControlsController) this.controlsController.get());
        if (controlsControllerImpl.confirmAvailability()) {
            ((ControlsBindingControllerImpl) controlsControllerImpl.bindingController).unsubscribe();
        }
        ControlsListingController controlsListingController = (ControlsListingController) this.controlsListingController.get();
        SecControlsUiControllerImpl$createCallback$1 secControlsUiControllerImpl$createCallback$1 = this.listingCallback;
        ((ControlsListingControllerImpl) controlsListingController).removeCallback(secControlsUiControllerImpl$createCallback$1 != null ? secControlsUiControllerImpl$createCallback$1 : null);
    }

    public final ControlsServiceInfo isPanelComponent(ComponentInfo componentInfo) {
        Object obj;
        ArrayList arrayList = (ArrayList) getPanelServiceInfos();
        int size = arrayList.size();
        int i = 0;
        while (true) {
            if (i >= size) {
                obj = null;
                break;
            }
            obj = arrayList.get(i);
            i++;
            if (Intrinsics.areEqual(((ControlsServiceInfo) obj).serviceInfo.applicationInfo.packageName, componentInfo.componentName.getPackageName())) {
                break;
            }
        }
        return (ControlsServiceInfo) obj;
    }

    public final void loadComponentInfo() {
        List activeFavoritesComponent = ((ControlsControllerImpl) ((SecControlsController) this.secControlsController.get())).getActiveFavoritesComponent();
        this.allComponentInfo = activeFavoritesComponent;
        SelectedItem preferredComponentSelectedItem = getPreferredComponentSelectedItem(activeFavoritesComponent);
        this.selectedItem = preferredComponentSelectedItem;
        List list = this.allComponentInfo;
        if (list == null) {
            list = null;
        }
        Log.d("SecControlsUiControllerImpl", "loadComponentInfo() selectedItem = " + preferredComponentSelectedItem + ", allComponentInfo = " + list);
    }

    public final void moveElement(int i, int i2) {
        if (i < i2) {
            while (i < i2) {
                int i3 = i + 1;
                Collections.swap(this.models, i, i3);
                i = i3;
            }
            return;
        }
        int i4 = i2 + 1;
        if (i4 > i) {
            return;
        }
        while (true) {
            Collections.swap(this.models, i, i - 1);
            if (i == i4) {
                return;
            } else {
                i--;
            }
        }
    }

    public final void notifyItemChanged(final int i, final MainControlModel mainControlModel) {
        ToggleRangeBehavior.Companion.getClass();
        if (ToggleRangeBehavior.inProgress) {
            this.uiExecutor.executeDelayed(new Runnable() { // from class: com.android.systemui.controls.ui.SecControlsUiControllerImpl$notifyItemChanged$1
                @Override // java.lang.Runnable
                public final void run() {
                    SecControlsUiControllerImpl secControlsUiControllerImpl = SecControlsUiControllerImpl.this;
                    int i2 = i;
                    MainControlModel mainControlModel2 = mainControlModel;
                    int i3 = SecControlsUiControllerImpl.$r8$clinit;
                    secControlsUiControllerImpl.notifyItemChanged(i2, mainControlModel2);
                }
            }, 200L);
            return;
        }
        this.logWrapper.dp("SecControlsUiControllerImpl", "notifyItemChanged: " + i);
        StatefulControlAdapter statefulControlAdapter = this.controlAdapter;
        if (statefulControlAdapter != null) {
            statefulControlAdapter.notifyItemChanged(i, mainControlModel);
        }
    }

    public final boolean saveFavorites(ComponentName componentName) {
        Lazy lazy = this.secControlsController;
        ((ControlsControllerImpl) ((SecControlsController) lazy.get())).getClass();
        Favorites.INSTANCE.getClass();
        if (!Favorites.getActiveFlag(componentName)) {
            Log.w("SecControlsUiControllerImpl", "Skip saveFavorites component: " + componentName);
            return false;
        }
        List<StructureInfo> structureInfosByUI = getStructureInfosByUI(componentName);
        if (!this.isChanged || !Intrinsics.areEqual(structureInfosByUI, this.verificationStructureInfos)) {
            return false;
        }
        Log.d("SecControlsUiControllerImpl", "saveFavorites component " + componentName + ", structureInfos:" + structureInfosByUI);
        ((ControlsControllerImpl) ((SecControlsController) lazy.get())).replaceFavoritesForComponent(new ComponentInfo(componentName, structureInfosByUI), false);
        return true;
    }

    public final void unsubscribeAndUnbindIfNecessary() {
        String packageName = this.selectedItem.getComponentName().getPackageName();
        this.controlsUtil.getClass();
        if ("com.samsung.android.oneconnect".equals(packageName)) {
            Log.d("SecControlsUiControllerImpl", "unsubscribeAndUnbindIfNecessary");
            ControlsControllerImpl controlsControllerImpl = (ControlsControllerImpl) ((SecControlsController) this.secControlsController.get());
            if (controlsControllerImpl.confirmAvailability()) {
                ((ControlsBindingControllerImpl) controlsControllerImpl.secBindingController).unbind();
            }
        }
    }

    public final void update(List<? extends ControlsServiceInfo> list, List<ComponentInfo> list2, SelectedItem selectedItem) {
        Object obj;
        MainFragment mainFragment;
        ControlInfo controlInfo;
        ControlInfo controlInfo2;
        int i = 1;
        List<ComponentInfo> list3 = list2;
        ArrayList arrayList = new ArrayList();
        for (Object obj2 : list3) {
            List list4 = ((ComponentInfo) obj2).structureInfos;
            if (!(list4 instanceof Collection) || !list4.isEmpty()) {
                Iterator it = list4.iterator();
                while (true) {
                    if (it.hasNext()) {
                        if (((StructureInfo) it.next()).active) {
                            arrayList.add(obj2);
                            break;
                        }
                    } else {
                        break;
                    }
                }
            }
        }
        ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList, 10));
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size) {
            Object obj3 = arrayList.get(i2);
            i2++;
            arrayList2.add(((ComponentInfo) obj3).componentName);
        }
        ArrayList arrayList3 = new ArrayList();
        for (Object obj4 : list) {
            if (arrayList2.contains(((ControlsServiceInfo) obj4).componentName)) {
                arrayList3.add(obj4);
            }
        }
        ArrayList arrayList4 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList3, 10));
        int size2 = arrayList3.size();
        int i3 = 0;
        while (i3 < size2) {
            Object obj5 = arrayList3.get(i3);
            i3++;
            ControlsServiceInfo controlsServiceInfo = (ControlsServiceInfo) obj5;
            SecSelectionItem secSelectionItem = new SecSelectionItem(controlsServiceInfo.loadLabel(), controlsServiceInfo.loadIcon(), controlsServiceInfo.componentName, controlsServiceInfo.serviceInfo.applicationInfo.uid, controlsServiceInfo.panelActivity);
            RenderInfo.Companion companion = RenderInfo.Companion;
            ComponentName componentName = secSelectionItem.componentName;
            Drawable drawable = secSelectionItem.icon;
            companion.getClass();
            RenderInfo.appIconMap.put(componentName, drawable);
            arrayList4.add(secSelectionItem);
        }
        if (arrayList4.isEmpty()) {
            Log.d("SecControlsUiControllerImpl", "filteredList is Empty");
            return;
        }
        int size3 = arrayList4.size();
        int i4 = 0;
        while (true) {
            if (i4 >= size3) {
                obj = null;
                break;
            }
            obj = arrayList4.get(i4);
            i4++;
            if (Intrinsics.areEqual(((SecSelectionItem) obj).componentName, selectedItem.getComponentName())) {
                break;
            }
        }
        SecSelectionItem secSelectionItem2 = (SecSelectionItem) obj;
        if (secSelectionItem2 == null) {
            secSelectionItem2 = (SecSelectionItem) arrayList4.get(0);
        }
        MainComponentModel mainComponentModel = this.componentModel;
        boolean z = (Intrinsics.areEqual(mainComponentModel.controlsSpinnerInfo, arrayList4) && Intrinsics.areEqual(mainComponentModel.selected, secSelectionItem2.componentName)) ? false : true;
        mainComponentModel.controlsSpinnerInfo = arrayList4;
        mainComponentModel.selected = secSelectionItem2.componentName;
        StatefulControlAdapter statefulControlAdapter = this.controlAdapter;
        if (statefulControlAdapter != null) {
            statefulControlAdapter.uid = secSelectionItem2.uid;
            statefulControlAdapter.notifyItemChanged(statefulControlAdapter.models.indexOf(mainComponentModel));
        }
        Log.d("SecControlsUiControllerImpl", "updateComponentModel isDirty = " + z);
        for (ComponentInfo componentInfo : list3) {
            if (Intrinsics.areEqual(componentInfo.componentName, secSelectionItem2.componentName)) {
                List list5 = this.models;
                ArrayList arrayList5 = new ArrayList();
                List list6 = componentInfo.structureInfos;
                ArrayList arrayList6 = new ArrayList();
                for (Object obj6 : list6) {
                    if (!((StructureInfo) obj6).controls.isEmpty()) {
                        arrayList6.add(obj6);
                    }
                }
                int size4 = arrayList6.size();
                int i5 = 0;
                while (i5 < size4) {
                    Object obj7 = arrayList6.get(i5);
                    i5 += i;
                    StructureInfo structureInfo = (StructureInfo) obj7;
                    String obj8 = structureInfo.structure.toString();
                    List list7 = componentInfo.structureInfos;
                    List list8 = list5;
                    StructureInfo structureInfo2 = (StructureInfo) (list7.size() == i ? list7.get(0) : null);
                    arrayList5.add(new MainControlModel(obj8, null, structureInfo2 != null ? TextUtils.isEmpty(structureInfo2.structure) : false));
                    for (Iterator it2 = structureInfo.controls.iterator(); it2.hasNext(); it2 = it2) {
                        arrayList5.add(new MainControlModel(obj8, new ControlWithState(structureInfo.componentName, (ControlInfo) it2.next(), null), false, 4, null));
                        i = i;
                    }
                    list5 = list8;
                }
                int i6 = i;
                ArrayList arrayList7 = new ArrayList();
                ArrayList arrayList8 = (ArrayList) list5;
                int size5 = arrayList8.size();
                int i7 = 0;
                while (i7 < size5) {
                    Object obj9 = arrayList8.get(i7);
                    i7++;
                    if (obj9 instanceof MainControlModel) {
                        arrayList7.add(obj9);
                    }
                }
                Log.d("SecControlsUiControllerImpl", "updateModels forcedUpdate = " + z + ", " + arrayList7 + " > " + arrayList5);
                ArrayList arrayList9 = new ArrayList();
                int size6 = arrayList7.size();
                int i8 = 0;
                while (i8 < size6) {
                    Object obj10 = arrayList7.get(i8);
                    i8++;
                    ControlWithState controlWithState = ((MainControlModel) obj10).controlWithState;
                    String str = (controlWithState == null || (controlInfo2 = controlWithState.ci) == null) ? null : controlInfo2.controlId;
                    if (str != null) {
                        arrayList9.add(str);
                    }
                }
                ArrayList arrayList10 = new ArrayList();
                int size7 = arrayList5.size();
                int i9 = 0;
                while (i9 < size7) {
                    Object obj11 = arrayList5.get(i9);
                    i9++;
                    ControlWithState controlWithState2 = ((MainControlModel) obj11).controlWithState;
                    String str2 = (controlWithState2 == null || (controlInfo = controlWithState2.ci) == null) ? null : controlInfo.controlId;
                    if (str2 != null) {
                        arrayList10.add(str2);
                    }
                }
                if (!arrayList9.equals(arrayList10) || z || !Intrinsics.areEqual(componentInfo.componentName, this.selectedItem.getComponentName()) || ((ArrayList) this.models).isEmpty()) {
                    arrayList8.clear();
                    this.panelPendingIntent = null;
                    arrayList8.add(mainComponentModel);
                    ControlsServiceInfo isPanelComponent = isPanelComponent(componentInfo);
                    if ((isPanelComponent != null ? isPanelComponent.panelActivity : null) != null) {
                        boolean booleanValue = ((Boolean) ((ControlsSettingsRepositoryImpl) this.controlsSettingsRepository).allowActionOnTrivialControlsInLockscreen.$$delegate_0.getValue()).booleanValue();
                        Context context = this.context;
                        Intent intent = new Intent();
                        intent.setComponent(isPanelComponent.panelActivity);
                        intent.putExtra("android.service.controls.extra.LOCKSCREEN_ALLOW_TRIVIAL_CONTROLS", booleanValue);
                        intent.putExtra("android.service.controls.extra.CONTROLS_SURFACE", 0);
                        Unit unit = Unit.INSTANCE;
                        PendingIntent activityAsUser = PendingIntent.getActivityAsUser(context, 0, intent, 201326592, ActivityOptions.makeBasic().setPendingIntentCreatorBackgroundActivityStartMode(i6).toBundle(), ((UserTrackerImpl) this.userTracker).getUserHandle());
                        this.panelPendingIntent = activityAsUser;
                        if (activityAsUser != null && (mainFragment = this.mainFragment) != null) {
                            mainFragment.panelPendingIntent = activityAsUser;
                            SecControlsUiControllerImpl$controlsPanelCallback$1 secControlsUiControllerImpl$controlsPanelCallback$1 = this.controlsPanelCallback;
                            mainFragment.controlsPanelUpdatedCallback = secControlsUiControllerImpl$controlsPanelCallback$1;
                            FrameLayout frameLayout = mainFragment.panelView;
                            if (frameLayout != null) {
                                secControlsUiControllerImpl$controlsPanelCallback$1.onPanelUpdated(frameLayout, activityAsUser);
                            }
                        }
                    } else {
                        arrayList8.addAll(arrayList5);
                    }
                    this.verificationStructureInfos = getStructureInfosByUI(componentInfo.componentName);
                    this.adapterNeedToUpdateDataSet = true;
                }
                ((ArrayList) this.models).size();
                boolean z2 = selectedItem instanceof SelectedItem.ComponentItem;
                Lazy lazy = this.secControlsController;
                if (z2) {
                    SelectedItem.ComponentItem componentItem = (SelectedItem.ComponentItem) selectedItem;
                    ((ControlsControllerImpl) ((SecControlsController) lazy.get())).subscribeToFavorites(componentItem.componentInfo);
                    this.selectedItem = new SelectedItem.ComponentItem(componentItem.name, componentInfo);
                } else {
                    SecControlsController secControlsController = (SecControlsController) lazy.get();
                    ComponentName componentName2 = selectedItem.getComponentName();
                    StructureInfo structureInfo3 = new StructureInfo(selectedItem.getComponentName(), "", EmptyList.INSTANCE, false, 8, null);
                    structureInfo3.active = true;
                    Unit unit2 = Unit.INSTANCE;
                    ((ControlsControllerImpl) secControlsController).subscribeToFavorites(new ComponentInfo(componentName2, CollectionsKt__CollectionsKt.mutableListOf(structureInfo3)));
                    ControlsProviderLifecycleManager retrieveLifecycleManager = ((ControlsBindingControllerImpl) ((ControlsControllerImpl) ((ControlsController) this.controlsController.get())).bindingController).retrieveLifecycleManager(selectedItem.getComponentName());
                    retrieveLifecycleManager.getClass();
                    retrieveLifecycleManager.executor.execute(new ControlsProviderLifecycleManager$bindService$1(retrieveLifecycleManager, true, true));
                    this.selectedItem = new SelectedItem.PanelItem(selectedItem.getName(), componentInfo.componentName);
                }
                Log.d("SecControlsUiControllerImpl", "update selectedItem = " + this.selectedItem);
                return;
            }
        }
        throw new NoSuchElementException("Collection contains no element matching the predicate.");
    }

    public static /* synthetic */ void getAllComponentInfo$annotations() {
    }

    private static /* synthetic */ void getFragmentManager$annotations() {
    }

    public static /* synthetic */ void getListingCallback$annotations() {
    }
}
