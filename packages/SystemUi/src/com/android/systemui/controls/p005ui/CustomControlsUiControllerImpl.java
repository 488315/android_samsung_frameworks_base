package com.android.systemui.controls.p005ui;

import android.app.Activity;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.text.TextUtils;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.util.Slog;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentManager;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardFMMViewController$$ExternalSyntheticOutline0;
import com.android.p038wm.shell.taskview.TaskViewFactoryController;
import com.android.p038wm.shell.taskview.TaskViewTaskController;
import com.android.p038wm.shell.taskview.TaskViewTaskController$$ExternalSyntheticLambda0;
import com.android.systemui.BasicRune;
import com.android.systemui.Dumpable;
import com.android.systemui.Prefs;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.controls.ControlsMetricsLogger;
import com.android.systemui.controls.ControlsServiceInfo;
import com.android.systemui.controls.controller.ComponentInfo;
import com.android.systemui.controls.controller.ControlInfo;
import com.android.systemui.controls.controller.ControlsBindingControllerImpl;
import com.android.systemui.controls.controller.ControlsController;
import com.android.systemui.controls.controller.ControlsControllerImpl;
import com.android.systemui.controls.controller.CustomControlsController;
import com.android.systemui.controls.controller.StructureInfo;
import com.android.systemui.controls.controller.util.BadgeProvider;
import com.android.systemui.controls.controller.util.BadgeSubject;
import com.android.systemui.controls.management.ControlsListingController;
import com.android.systemui.controls.management.ControlsListingControllerImpl;
import com.android.systemui.controls.management.CustomControlsProviderSelectorActivity;
import com.android.systemui.controls.management.adapter.MainControlAdapter;
import com.android.systemui.controls.management.model.MainComponentModel;
import com.android.systemui.controls.management.model.MainControlModel;
import com.android.systemui.controls.management.model.MainModel;
import com.android.systemui.controls.management.model.MainPanelModel;
import com.android.systemui.controls.p005ui.SelectedItem;
import com.android.systemui.controls.p005ui.fragment.MainFragment;
import com.android.systemui.controls.p005ui.fragment.NoAppFragment;
import com.android.systemui.controls.p005ui.fragment.NoFavoriteFragment;
import com.android.systemui.controls.p005ui.util.AUIFacade;
import com.android.systemui.controls.p005ui.util.ControlsActivityStarter;
import com.android.systemui.controls.p005ui.util.ControlsUtil;
import com.android.systemui.controls.p005ui.util.LayoutUtil;
import com.android.systemui.controls.p005ui.util.SALogger;
import com.android.systemui.controls.p005ui.view.ControlsSpinner;
import com.android.systemui.controls.panels.AuthorizedPanelsRepository;
import com.android.systemui.controls.panels.CustomSelectedComponentRepository;
import com.android.systemui.controls.panels.CustomSelectedComponentRepositoryImpl;
import com.android.systemui.controls.panels.SelectedComponentRepository;
import com.android.systemui.controls.settings.ControlsSettingsDialogManagerImpl;
import com.android.systemui.controls.settings.ControlsSettingsRepository;
import com.android.systemui.controls.settings.ControlsSettingsRepositoryImpl;
import com.android.systemui.controls.util.ControlsRuneWrapper;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlagsRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.DumpUtilsKt;
import com.android.systemui.util.concurrency.DelayableExecutor;
import dagger.Lazy;
import java.io.PrintWriter;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collection;
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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class CustomControlsUiControllerImpl implements ControlsUiController, CustomControlsUiController, Dumpable {
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
    public MainControlAdapter controlAdapter;
    public final ControlsActivityStarter controlsActivityStarter;
    public final Lazy controlsController;
    public final Lazy controlsListingController;
    public final ControlsMetricsLogger controlsMetricsLogger;
    public final CustomControlsUiControllerImpl$controlsPanelCallback$1 controlsPanelCallback;
    public final CustomControlsUiControllerImpl$controlsPositionChangedCallback$1 controlsPositionChangedCallback;
    public final ControlsRuneWrapper controlsRuneWrapper;
    public final ControlsSettingsRepository controlsSettingsRepository;
    public final ControlsUtil controlsUtil;
    public final CustomControlActionCoordinator customControlActionCoordinator;
    public final Lazy customControlsController;
    public final CustomSelectedComponentRepository customSelectedComponentRepository;
    public FragmentManager fragmentManager;
    public boolean hidden;
    public boolean isChanged;
    public boolean isShowOverLockscreenWhenLocked;
    public final KeyguardStateController keyguardStateController;
    public PendingIntent launchingPendingIntent;
    public final LayoutUtil layoutUtil;
    public ControlsListingController.ControlsListingCallback listingCallback;
    public final CustomControlsUiControllerImpl$special$$inlined$compareBy$1 localeComparator;
    public final LogWrapper logWrapper;
    public MainFragment mainFragment;
    public final List models;
    public NoAppFragment noAppFragment;
    public NoFavoriteFragment noFavoriteFragment;
    public Runnable onDismiss;
    public final CustomControlsUiControllerImpl$openAppButtonClickListener$1 openAppButtonClickListener;
    public ViewGroup parent;
    public final SALogger saLogger;
    public final SelectedComponentRepository selectedComponentRepository;
    public SelectedItem selectedItem;
    public List serviceInfos;
    public final SharedPreferences sharedPreferences;
    public final C1200x335d7759 spinnerItemSelectionChangedCallback;
    public final CustomControlsUiControllerImpl$spinnerTouchCallback$1 spinnerTouchCallback;
    public PanelTaskViewController taskViewController;
    public final Optional taskViewFactory;
    public final DelayableExecutor uiExecutor;
    public final UserTracker userTracker;
    public List verificationStructureInfos;

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

    /* JADX WARN: Type inference failed for: r1v17, types: [com.android.systemui.controls.ui.CustomControlsUiControllerImpl$openAppButtonClickListener$1] */
    /* JADX WARN: Type inference failed for: r3v0, types: [com.android.systemui.controls.ui.CustomControlsUiControllerImpl$special$$inlined$compareBy$1] */
    public CustomControlsUiControllerImpl(Lazy lazy, Lazy lazy2, Context context, DelayableExecutor delayableExecutor, DelayableExecutor delayableExecutor2, Lazy lazy3, SharedPreferences sharedPreferences, ControlActionCoordinator controlActionCoordinator, CustomControlActionCoordinator customControlActionCoordinator, ControlsMetricsLogger controlsMetricsLogger, LogWrapper logWrapper, LayoutUtil layoutUtil, ControlsUtil controlsUtil, ControlsActivityStarter controlsActivityStarter, AUIFacade aUIFacade, SALogger sALogger, BadgeSubject badgeSubject, BadgeProvider badgeProvider, ControlsRuneWrapper controlsRuneWrapper, ActivityStarter activityStarter, KeyguardStateController keyguardStateController, UserTracker userTracker, Optional<TaskViewFactoryController.TaskViewFactoryImpl> optional, ControlsSettingsRepository controlsSettingsRepository, AuthorizedPanelsRepository authorizedPanelsRepository, SelectedComponentRepository selectedComponentRepository, CustomSelectedComponentRepository customSelectedComponentRepository, DumpManager dumpManager) {
        this.controlsController = lazy;
        this.customControlsController = lazy2;
        this.context = context;
        this.uiExecutor = delayableExecutor;
        this.bgExecutor = delayableExecutor2;
        this.controlsListingController = lazy3;
        this.sharedPreferences = sharedPreferences;
        this.controlActionCoordinator = controlActionCoordinator;
        this.customControlActionCoordinator = customControlActionCoordinator;
        this.controlsMetricsLogger = controlsMetricsLogger;
        this.logWrapper = logWrapper;
        this.layoutUtil = layoutUtil;
        this.controlsUtil = controlsUtil;
        this.controlsActivityStarter = controlsActivityStarter;
        this.auiFacade = aUIFacade;
        this.saLogger = sALogger;
        this.badgeSubject = badgeSubject;
        this.badgeProvider = badgeProvider;
        this.controlsRuneWrapper = controlsRuneWrapper;
        this.activityStarter = activityStarter;
        this.keyguardStateController = keyguardStateController;
        this.userTracker = userTracker;
        this.taskViewFactory = optional;
        this.controlsSettingsRepository = controlsSettingsRepository;
        this.authorizedPanelsRepository = authorizedPanelsRepository;
        this.selectedComponentRepository = selectedComponentRepository;
        this.customSelectedComponentRepository = customSelectedComponentRepository;
        SelectedItem.Companion.getClass();
        this.selectedItem = SelectedItem.EMPTY_SELECTION_COMPONENT;
        final Collator collator = Collator.getInstance(context.getResources().getConfiguration().getLocales().get(0));
        this.localeComparator = new Comparator() { // from class: com.android.systemui.controls.ui.CustomControlsUiControllerImpl$special$$inlined$compareBy$1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return collator.compare(((ControlsSelectionItem) obj).getAppName(), ((ControlsSelectionItem) obj2).getAppName());
            }
        };
        this.hidden = true;
        this.models = new ArrayList();
        ArrayList arrayList = new ArrayList();
        ComponentInfo.Companion.getClass();
        this.componentModel = new MainComponentModel(arrayList, ComponentInfo.EMPTY_COMPONENT, false);
        this.verificationStructureInfos = new ArrayList();
        this.serviceInfos = new ArrayList();
        DumpManager.registerDumpable$default(dumpManager, CustomControlsUiControllerImpl.class.getName(), this);
        this.controlsPositionChangedCallback = new CustomControlsUiControllerImpl$controlsPositionChangedCallback$1(this);
        this.controlsPanelCallback = new CustomControlsUiControllerImpl$controlsPanelCallback$1(this);
        this.openAppButtonClickListener = new View.OnClickListener() { // from class: com.android.systemui.controls.ui.CustomControlsUiControllerImpl$openAppButtonClickListener$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                if (BasicRune.CONTROLS_SAMSUNG_ANALYTICS) {
                    CustomControlsUiControllerImpl.this.saLogger.sendEvent(SALogger.Event.LaunchSmartThings.INSTANCE);
                }
                if (CustomControlsUiControllerImpl.this.controlsUtil.isSecureLocked()) {
                    final CustomControlsUiControllerImpl customControlsUiControllerImpl = CustomControlsUiControllerImpl.this;
                    customControlsUiControllerImpl.activityStarter.dismissKeyguardThenExecute(new ActivityStarter.OnDismissAction() { // from class: com.android.systemui.controls.ui.CustomControlsUiControllerImpl$openAppButtonClickListener$1.1
                        @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
                        public final boolean onDismiss() {
                            PendingIntent pendingIntent = CustomControlsUiControllerImpl.this.launchingPendingIntent;
                            if (pendingIntent == null) {
                                return true;
                            }
                            pendingIntent.send();
                            return true;
                        }
                    }, new Runnable() { // from class: com.android.systemui.controls.ui.CustomControlsUiControllerImpl$openAppButtonClickListener$1.2
                        @Override // java.lang.Runnable
                        public final void run() {
                        }
                    }, true);
                } else {
                    PendingIntent pendingIntent = CustomControlsUiControllerImpl.this.launchingPendingIntent;
                    if (pendingIntent != null) {
                        pendingIntent.send();
                    }
                }
            }
        };
        this.spinnerTouchCallback = new CustomControlsUiControllerImpl$spinnerTouchCallback$1(this);
        this.spinnerItemSelectionChangedCallback = new C1200x335d7759(this);
    }

    public static final ControlsServiceInfo access$getComponent(CustomControlsUiControllerImpl customControlsUiControllerImpl, ComponentInfo componentInfo) {
        Object obj;
        Iterator it = customControlsUiControllerImpl.serviceInfos.iterator();
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

    public static final void access$listAdjustmentIfNeeded(CustomControlsUiControllerImpl customControlsUiControllerImpl, List list) {
        Object obj;
        ControlInfo controlInfo;
        customControlsUiControllerImpl.getClass();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            CharSequence charSequence = (CharSequence) it.next();
            List list2 = customControlsUiControllerImpl.models;
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = (ArrayList) list2;
            Iterator it2 = arrayList2.iterator();
            while (it2.hasNext()) {
                Object next = it2.next();
                if (next instanceof MainControlModel) {
                    arrayList.add(next);
                }
            }
            ArrayList arrayList3 = new ArrayList();
            Iterator it3 = arrayList.iterator();
            while (it3.hasNext()) {
                Object next2 = it3.next();
                if (Intrinsics.areEqual(((MainControlModel) next2).structure, charSequence)) {
                    arrayList3.add(next2);
                }
            }
            Iterator it4 = arrayList3.iterator();
            while (true) {
                if (it4.hasNext()) {
                    obj = it4.next();
                    if (((MainControlModel) obj).getType() == MainModel.Type.STRUCTURE) {
                        break;
                    }
                } else {
                    obj = null;
                    break;
                }
            }
            MainControlModel mainControlModel = (MainControlModel) obj;
            ArrayList arrayList4 = new ArrayList();
            Iterator it5 = arrayList3.iterator();
            while (it5.hasNext()) {
                Object next3 = it5.next();
                if (((MainControlModel) next3).getType() == MainModel.Type.CONTROL) {
                    arrayList4.add(next3);
                }
            }
            ArrayList arrayList5 = new ArrayList();
            Iterator it6 = arrayList3.iterator();
            while (it6.hasNext()) {
                Object next4 = it6.next();
                if (((MainControlModel) next4).getType() == MainModel.Type.SMALL_CONTROL) {
                    arrayList5.add(next4);
                }
            }
            LogWrapper logWrapper = customControlsUiControllerImpl.logWrapper;
            if (mainControlModel == null && ((!arrayList4.isEmpty()) || (BasicRune.CONTROLS_LAYOUT_TYPE && (!arrayList5.isEmpty())))) {
                int indexOf = true ^ arrayList4.isEmpty() ? arrayList2.indexOf(arrayList4.get(0)) : arrayList2.indexOf(arrayList5.get(0));
                arrayList2.add(indexOf, new MainControlModel(String.valueOf(charSequence), null, false, 4, null));
                MainControlAdapter mainControlAdapter = customControlsUiControllerImpl.controlAdapter;
                if (mainControlAdapter != null) {
                    mainControlAdapter.notifyItemInserted(indexOf);
                }
                ArrayList arrayList6 = new ArrayList();
                for (Object obj2 : arrayList2) {
                    if (obj2 instanceof MainControlModel) {
                        arrayList6.add(obj2);
                    }
                }
                ArrayList arrayList7 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList6, 10));
                Iterator it7 = arrayList6.iterator();
                while (it7.hasNext()) {
                    ControlWithState controlWithState = ((MainControlModel) it7.next()).controlWithState;
                    arrayList7.add((controlWithState == null || (controlInfo = controlWithState.f249ci) == null) ? null : controlInfo.controlTitle);
                }
                logWrapper.m99dp("CustomControlsUiControllerImpl", "listAdjustmentIfNeeded-notifyItemInserted: structureName=" + ((Object) charSequence) + ", index=" + indexOf + ", models=" + arrayList7);
            }
            if (mainControlModel != null && arrayList4.isEmpty() && BasicRune.CONTROLS_LAYOUT_TYPE && arrayList5.isEmpty()) {
                int indexOf2 = arrayList2.indexOf(mainControlModel);
                arrayList2.remove(indexOf2);
                MainControlAdapter mainControlAdapter2 = customControlsUiControllerImpl.controlAdapter;
                if (mainControlAdapter2 != null) {
                    mainControlAdapter2.notifyItemRemoved(indexOf2);
                }
                logWrapper.m99dp("CustomControlsUiControllerImpl", "listAdjustmentIfNeeded-notifyItemRemoved: structureName=" + ((Object) charSequence));
            }
        }
    }

    public static final void access$reload(CustomControlsUiControllerImpl customControlsUiControllerImpl, SelectedItem selectedItem) {
        if (customControlsUiControllerImpl.hidden) {
            return;
        }
        ControlsControllerImpl controlsControllerImpl = (ControlsControllerImpl) ((ControlsController) customControlsUiControllerImpl.controlsController.get());
        if (controlsControllerImpl.confirmAvailability()) {
            ((ControlsBindingControllerImpl) controlsControllerImpl.bindingController).unsubscribe();
        }
        PanelTaskViewController panelTaskViewController = customControlsUiControllerImpl.taskViewController;
        if (panelTaskViewController != null) {
            TaskViewTaskController taskViewTaskController = panelTaskViewController.taskView.mTaskViewTaskController;
            if (taskViewTaskController.mTaskToken == null) {
                Slog.w("TaskViewTaskController", "Trying to remove a task that was never added? (no taskToken)");
            } else {
                taskViewTaskController.mShellExecutor.execute(new TaskViewTaskController$$ExternalSyntheticLambda0(taskViewTaskController, 4));
            }
        }
        customControlsUiControllerImpl.taskViewController = null;
        customControlsUiControllerImpl.allComponentInfo = ((ControlsControllerImpl) ((CustomControlsController) customControlsUiControllerImpl.customControlsController.get())).getActiveFavoritesComponent();
        customControlsUiControllerImpl.update(customControlsUiControllerImpl.serviceInfos, customControlsUiControllerImpl.getAllComponentInfo(), selectedItem);
        Log.d("CustomControlsUiControllerImpl", "reload selected = " + selectedItem + ", allComponentInfo = " + customControlsUiControllerImpl.getAllComponentInfo());
        if (customControlsUiControllerImpl.adapterNeedToUpdateDataSet) {
            MainControlAdapter mainControlAdapter = customControlsUiControllerImpl.controlAdapter;
            if (mainControlAdapter != null) {
                mainControlAdapter.notifyDataSetChanged();
            }
            customControlsUiControllerImpl.adapterNeedToUpdateDataSet = false;
        }
    }

    public static final void access$showEmptyStructureIfNeeded(CustomControlsUiControllerImpl customControlsUiControllerImpl) {
        Object obj;
        List list = customControlsUiControllerImpl.models;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = (ArrayList) list;
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            Object next = it.next();
            if (next instanceof MainControlModel) {
                arrayList.add(next);
            }
        }
        ArrayList arrayList3 = new ArrayList();
        Iterator it2 = arrayList.iterator();
        while (true) {
            if (!it2.hasNext()) {
                break;
            }
            Object next2 = it2.next();
            if (((MainControlModel) next2).getType() == MainModel.Type.STRUCTURE) {
                arrayList3.add(next2);
            }
        }
        Iterator it3 = arrayList3.iterator();
        while (true) {
            if (!it3.hasNext()) {
                obj = null;
                break;
            } else {
                obj = it3.next();
                if (TextUtils.isEmpty(((MainControlModel) obj).structure)) {
                    break;
                }
            }
        }
        MainControlModel mainControlModel = (MainControlModel) obj;
        if (mainControlModel != null) {
            boolean z = arrayList3.size() == 1;
            if (mainControlModel.needToHide != z) {
                mainControlModel.needToHide = z;
                MainControlAdapter mainControlAdapter = customControlsUiControllerImpl.controlAdapter;
                if (mainControlAdapter != null) {
                    mainControlAdapter.notifyItemChanged(arrayList2.indexOf(mainControlModel));
                }
            }
        }
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("CustomControlsUiControllerImpl:");
        IndentingPrintWriter asIndenting = DumpUtilsKt.asIndenting(printWriter);
        asIndenting.increaseIndent();
        asIndenting.println("hidden: " + this.hidden);
        asIndenting.println("selectedItem: " + this.selectedItem);
        asIndenting.println("setting: " + ((ControlsSettingsRepositoryImpl) this.controlsSettingsRepository).allowActionOnTrivialControlsInLockscreen.getValue());
        asIndenting.decreaseIndent();
    }

    public final List getAllComponentInfo() {
        List list = this.allComponentInfo;
        if (list != null) {
            return list;
        }
        return null;
    }

    public final List<MainModel> getModels() {
        return CollectionsKt___CollectionsKt.toList(this.models);
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

    /* JADX WARN: Code restructure failed: missing block: B:28:0x0067, code lost:
    
        if (r1.isPanel == true) goto L34;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final SelectedItem getPreferredComponentSelectedItem(List list) {
        ComponentName componentName;
        Object obj;
        Object obj2;
        if (list.isEmpty()) {
            SelectedItem.Companion.getClass();
            return SelectedItem.EMPTY_SELECTION_COMPONENT;
        }
        CustomSelectedComponentRepositoryImpl customSelectedComponentRepositoryImpl = (CustomSelectedComponentRepositoryImpl) this.customSelectedComponentRepository;
        CustomSelectedComponentRepository.CustomSelectedComponent selectedComponent = customSelectedComponentRepositoryImpl.getSelectedComponent();
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
        if (componentInfo == null) {
            componentInfo = (ComponentInfo) CollectionsKt___CollectionsKt.firstOrNull(list);
        }
        if (componentInfo == null) {
            SelectedItem.Companion.getClass();
            return SelectedItem.EMPTY_SELECTION_COMPONENT;
        }
        ComponentName componentName2 = selectedComponent != null ? selectedComponent.componentName : null;
        ComponentName componentName3 = componentInfo.componentName;
        if (Intrinsics.areEqual(componentName3, componentName2)) {
            boolean z = selectedComponent != null;
            if (z) {
                SelectedItem.PanelItem panelItem = new SelectedItem.PanelItem(selectedComponent.name, componentName3);
                customSelectedComponentRepositoryImpl.setSelectedComponent(new CustomSelectedComponentRepository.CustomSelectedComponent(panelItem));
                return panelItem;
            }
        }
        Iterator it2 = ((ArrayList) ((ControlsListingControllerImpl) ((ControlsListingController) this.controlsListingController.get())).getCurrentServices()).iterator();
        while (true) {
            if (!it2.hasNext()) {
                obj2 = null;
                break;
            }
            obj2 = it2.next();
            if (Intrinsics.areEqual(componentName3, ((ControlsServiceInfo) obj2).componentName)) {
                break;
            }
        }
        ControlsServiceInfo controlsServiceInfo = (ControlsServiceInfo) obj2;
        if ((controlsServiceInfo != null ? controlsServiceInfo.panelActivity : null) != null) {
            SelectedItem.PanelItem panelItem2 = new SelectedItem.PanelItem(controlsServiceInfo.loadLabel(), componentName3);
            customSelectedComponentRepositoryImpl.setSelectedComponent(new CustomSelectedComponentRepository.CustomSelectedComponent(panelItem2));
            return panelItem2;
        }
        SelectedItem.ComponentItem componentItem = new SelectedItem.ComponentItem(controlsServiceInfo != null ? controlsServiceInfo.loadLabel() : "", componentInfo);
        customSelectedComponentRepositoryImpl.setSelectedComponent(new CustomSelectedComponentRepository.CustomSelectedComponent(componentItem));
        return componentItem;
    }

    public final ControlsSpinner.SpinnerItemSelectionChangedCallback getSpinnerItemSelectionChangedCallback() {
        return this.spinnerItemSelectionChangedCallback;
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
            StructureInfo structureInfo = new StructureInfo(componentName, "", EmptyList.INSTANCE);
            structureInfo.customStructureInfo.active = true;
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
        for (Object obj3 : arrayList) {
            MainControlModel mainControlModel = (MainControlModel) obj3;
            if (mainControlModel.getType() == MainModel.Type.CONTROL || (BasicRune.CONTROLS_LAYOUT_TYPE && mainControlModel.getType() == MainModel.Type.SMALL_CONTROL)) {
                arrayList2.add(obj3);
            }
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Object obj4 : arrayList2) {
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
                Intrinsics.checkNotNull(controlWithState);
                arrayList4.add(controlWithState.f249ci);
            }
            StructureInfo structureInfo2 = new StructureInfo(componentName, str2, new ArrayList(arrayList4));
            structureInfo2.customStructureInfo.active = true;
            arrayList3.add(structureInfo2);
        }
        return arrayList3;
    }

    public final void hide(ViewGroup viewGroup) {
        Boolean bool;
        if (Intrinsics.areEqual(viewGroup, this.parent)) {
            Log.d("CustomControlsUiControllerImpl", "hide()");
            this.hidden = true;
            if (this.controlAdapter != null) {
                Iterator it = ((LinkedHashMap) MainControlAdapter.controlViewHolders).entrySet().iterator();
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
            controlActionCoordinatorImpl.getClass();
            if (!((FeatureFlagsRelease) controlActionCoordinatorImpl.featureFlags).isEnabled(Flags.USE_APP_PANELS)) {
                ((ControlsSettingsDialogManagerImpl) controlActionCoordinatorImpl.controlsSettingsDialogManager).closeDialog();
            }
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
                Dialog dialog3 = controlActionCoordinatorImpl.dialog;
                if (dialog3 != null && dialog3.isShowing()) {
                    Dialog dialog4 = controlActionCoordinatorImpl.dialog;
                    if (dialog4 != null) {
                        dialog4.dismiss();
                    }
                    controlActionCoordinatorImpl.dialog = null;
                }
            }
            PanelTaskViewController panelTaskViewController = this.taskViewController;
            if (panelTaskViewController != null) {
                TaskViewTaskController taskViewTaskController = panelTaskViewController.taskView.mTaskViewTaskController;
                if (taskViewTaskController.mTaskToken == null) {
                    Slog.w("TaskViewTaskController", "Trying to remove a task that was never added? (no taskToken)");
                } else {
                    taskViewTaskController.mShellExecutor.execute(new TaskViewTaskController$$ExternalSyntheticLambda0(taskViewTaskController, 4));
                }
            }
            this.taskViewController = null;
            saveFavorites(this.selectedItem.getComponentName());
            ControlsControllerImpl controlsControllerImpl = (ControlsControllerImpl) ((ControlsController) this.controlsController.get());
            if (controlsControllerImpl.confirmAvailability()) {
                ((ControlsBindingControllerImpl) controlsControllerImpl.bindingController).unsubscribe();
            }
            ControlsListingController controlsListingController = (ControlsListingController) this.controlsListingController.get();
            ControlsListingController.ControlsListingCallback controlsListingCallback = this.listingCallback;
            ((ControlsListingControllerImpl) controlsListingController).removeCallback(controlsListingCallback != null ? controlsListingCallback : null);
        }
    }

    public final ControlsServiceInfo isPanelComponent(ComponentInfo componentInfo) {
        Object obj;
        Iterator it = ((ArrayList) getPanelServiceInfos()).iterator();
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

    public final void loadComponentInfo() {
        this.allComponentInfo = ((ControlsControllerImpl) ((CustomControlsController) this.customControlsController.get())).getActiveFavoritesComponent();
        SelectedItem preferredComponentSelectedItem = getPreferredComponentSelectedItem(getAllComponentInfo());
        this.selectedItem = preferredComponentSelectedItem;
        Log.d("CustomControlsUiControllerImpl", "loadComponentInfo() selectedItem = " + preferredComponentSelectedItem + ", allComponentInfo = " + getAllComponentInfo());
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x003c, code lost:
    
        if (((com.android.systemui.controls.controller.ControlsControllerImpl) ((com.android.systemui.controls.controller.CustomControlsController) r2.get())).getActiveFlag(r7.selectedItem.getComponentName()) == false) goto L14;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean needToShowNonMainView() {
        boolean z;
        Lazy lazy = this.controlsListingController;
        int size = ((ArrayList) ((ControlsListingControllerImpl) ((ControlsListingController) lazy.get())).getCurrentServices()).size();
        Lazy lazy2 = this.customControlsController;
        if (size != 0) {
            SelectedItem selectedItem = this.selectedItem;
            if (!(selectedItem instanceof SelectedItem.ComponentItem) || selectedItem.getHasControls()) {
                if (this.selectedItem instanceof SelectedItem.PanelItem) {
                }
                z = false;
                int size2 = ((ArrayList) ((ControlsListingControllerImpl) ((ControlsListingController) lazy.get())).getCurrentServices()).size();
                SelectedItem selectedItem2 = this.selectedItem;
                boolean activeFlag = ((ControlsControllerImpl) ((CustomControlsController) lazy2.get())).getActiveFlag(this.selectedItem.getComponentName());
                ComponentName componentName = this.selectedItem.getComponentName();
                StringBuilder m66m = KeyguardFMMViewController$$ExternalSyntheticOutline0.m66m("needToShowNonMainView ", z, ", service.size = ", size2, ", selectedItem = ");
                m66m.append(selectedItem2);
                m66m.append(", activeFlag = ");
                m66m.append(activeFlag);
                m66m.append(", componentName = ");
                m66m.append(componentName);
                Log.d("CustomControlsUiControllerImpl", m66m.toString());
                return z;
            }
        }
        z = true;
        int size22 = ((ArrayList) ((ControlsListingControllerImpl) ((ControlsListingController) lazy.get())).getCurrentServices()).size();
        SelectedItem selectedItem22 = this.selectedItem;
        boolean activeFlag2 = ((ControlsControllerImpl) ((CustomControlsController) lazy2.get())).getActiveFlag(this.selectedItem.getComponentName());
        ComponentName componentName2 = this.selectedItem.getComponentName();
        StringBuilder m66m2 = KeyguardFMMViewController$$ExternalSyntheticOutline0.m66m("needToShowNonMainView ", z, ", service.size = ", size22, ", selectedItem = ");
        m66m2.append(selectedItem22);
        m66m2.append(", activeFlag = ");
        m66m2.append(activeFlag2);
        m66m2.append(", componentName = ");
        m66m2.append(componentName2);
        Log.d("CustomControlsUiControllerImpl", m66m2.toString());
        return z;
    }

    public final void notifyItemChanged(final int i, final MainControlModel mainControlModel) {
        ToggleRangeBehavior.Companion.getClass();
        if (ToggleRangeBehavior.inProgress) {
            this.uiExecutor.executeDelayed(200L, new Runnable() { // from class: com.android.systemui.controls.ui.CustomControlsUiControllerImpl$notifyItemChanged$1
                @Override // java.lang.Runnable
                public final void run() {
                    CustomControlsUiControllerImpl customControlsUiControllerImpl = CustomControlsUiControllerImpl.this;
                    int i2 = i;
                    MainControlModel mainControlModel2 = mainControlModel;
                    int i3 = CustomControlsUiControllerImpl.$r8$clinit;
                    customControlsUiControllerImpl.notifyItemChanged(i2, mainControlModel2);
                }
            });
            return;
        }
        this.logWrapper.m99dp("CustomControlsUiControllerImpl", AbstractC0000x2c234b15.m0m("notifyItemChanged: ", i));
        MainControlAdapter mainControlAdapter = this.controlAdapter;
        if (mainControlAdapter != null) {
            mainControlAdapter.notifyItemChanged(i, mainControlModel);
        }
    }

    public final Class resolveActivity() {
        loadComponentInfo();
        int size = ((ArrayList) ((ControlsListingControllerImpl) ((ControlsListingController) this.controlsListingController.get())).getCurrentServices()).size();
        Context context = this.context;
        boolean z = false;
        ControlsUtil controlsUtil = this.controlsUtil;
        if (size != 0 && needToShowNonMainView()) {
            controlsUtil.getClass();
            if (!Prefs.getBoolean(context, "ControlsOOBEManageAppsCompleted", false)) {
                this.isShowOverLockscreenWhenLocked = false;
                Log.d("CustomControlsUiControllerImpl", "resolveActivity CustomControlsProviderSelectorActivity");
                return CustomControlsProviderSelectorActivity.class;
            }
        }
        if (controlsUtil.isSecureLocked()) {
            if (Settings.Secure.getInt(context.getContentResolver(), "lockscreen_show_controls", 0) != 0) {
                z = true;
            }
        }
        this.isShowOverLockscreenWhenLocked = z;
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("resolveActivity CustomControlsActivity isShowOverLockscreenWhenLocked = ", z, "CustomControlsUiControllerImpl");
        return CustomControlsActivity.class;
    }

    public final boolean saveFavorites(ComponentName componentName) {
        Lazy lazy = this.customControlsController;
        if (!((ControlsControllerImpl) ((CustomControlsController) lazy.get())).getActiveFlag(componentName)) {
            Log.w("CustomControlsUiControllerImpl", "Skip saveFavorites component: " + componentName);
            return false;
        }
        List<StructureInfo> structureInfosByUI = getStructureInfosByUI(componentName);
        if (!this.isChanged || !Intrinsics.areEqual(structureInfosByUI, this.verificationStructureInfos)) {
            return false;
        }
        Log.d("CustomControlsUiControllerImpl", "saveFavorites component " + componentName + ", structureInfos:" + structureInfosByUI);
        ((ControlsControllerImpl) ((CustomControlsController) lazy.get())).replaceFavoritesForComponent(new ComponentInfo(componentName, structureInfosByUI), false);
        return true;
    }

    public final void unsubscribeAndUnbindIfNecessary() {
        String packageName = this.selectedItem.getComponentName().getPackageName();
        this.controlsUtil.getClass();
        if (Intrinsics.areEqual("com.samsung.android.oneconnect", packageName)) {
            Log.d("CustomControlsUiControllerImpl", "unsubscribeAndUnbindIfNecessary");
            ControlsControllerImpl controlsControllerImpl = (ControlsControllerImpl) ((CustomControlsController) this.customControlsController.get());
            if (controlsControllerImpl.confirmAvailability()) {
                ((ControlsBindingControllerImpl) controlsControllerImpl.customBindingController).unbind();
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void update(List<? extends ControlsServiceInfo> list, List<ComponentInfo> list2, SelectedItem selectedItem) {
        int i;
        int i2;
        Object obj;
        ControlInfo controlInfo;
        ControlInfo controlInfo2;
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = list2.iterator();
        while (true) {
            i = 0;
            i2 = 1;
            if (!it.hasNext()) {
                break;
            }
            Object next = it.next();
            List list3 = ((ComponentInfo) next).structureInfos;
            if (!(list3 instanceof Collection) || !list3.isEmpty()) {
                Iterator it2 = list3.iterator();
                while (true) {
                    if (it2.hasNext()) {
                        if (((StructureInfo) it2.next()).customStructureInfo.active) {
                            i = 1;
                            break;
                        }
                    } else {
                        break;
                    }
                }
            }
            if (i != 0) {
                arrayList.add(next);
            }
        }
        ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList, 10));
        Iterator it3 = arrayList.iterator();
        while (it3.hasNext()) {
            arrayList2.add(((ComponentInfo) it3.next()).componentName);
        }
        ArrayList arrayList3 = new ArrayList();
        for (Object obj2 : list) {
            if (arrayList2.contains(((ControlsServiceInfo) obj2).componentName)) {
                arrayList3.add(obj2);
            }
        }
        ArrayList arrayList4 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList3, 10));
        Iterator it4 = arrayList3.iterator();
        while (it4.hasNext()) {
            ControlsServiceInfo controlsServiceInfo = (ControlsServiceInfo) it4.next();
            ControlsSelectionItem controlsSelectionItem = new ControlsSelectionItem(controlsServiceInfo.loadLabel(), controlsServiceInfo.loadIcon(), controlsServiceInfo.componentName, controlsServiceInfo.serviceInfo.applicationInfo.uid, controlsServiceInfo.panelActivity);
            RenderInfo.Companion.getClass();
            RenderInfo.appIconMap.put(controlsSelectionItem.componentName, controlsSelectionItem.icon);
            arrayList4.add(controlsSelectionItem);
        }
        if (arrayList4.isEmpty()) {
            Log.d("CustomControlsUiControllerImpl", "filteredList is Empty");
            return;
        }
        Iterator it5 = arrayList4.iterator();
        while (true) {
            if (it5.hasNext()) {
                obj = it5.next();
                if (Intrinsics.areEqual(((ControlsSelectionItem) obj).componentName, selectedItem.getComponentName())) {
                    break;
                }
            } else {
                obj = null;
                break;
            }
        }
        ControlsSelectionItem controlsSelectionItem2 = (ControlsSelectionItem) obj;
        if (controlsSelectionItem2 == null) {
            controlsSelectionItem2 = (ControlsSelectionItem) arrayList4.get(0);
        }
        MainComponentModel mainComponentModel = this.componentModel;
        mainComponentModel.controlsSpinnerInfo = arrayList4;
        mainComponentModel.selected = controlsSelectionItem2.componentName;
        MainControlAdapter mainControlAdapter = this.controlAdapter;
        if (mainControlAdapter != null) {
            mainControlAdapter.uid = controlsSelectionItem2.uid;
            mainControlAdapter.notifyItemChanged(mainControlAdapter.models.indexOf(mainComponentModel));
        }
        for (ComponentInfo componentInfo : list2) {
            if (Intrinsics.areEqual(componentInfo.componentName, controlsSelectionItem2.componentName)) {
                List list4 = this.models;
                ArrayList arrayList5 = new ArrayList();
                ArrayList arrayList6 = new ArrayList();
                List list5 = componentInfo.structureInfos;
                for (Object obj3 : list5) {
                    if (!((StructureInfo) obj3).controls.isEmpty()) {
                        arrayList6.add(obj3);
                    }
                }
                Iterator it6 = arrayList6.iterator();
                while (it6.hasNext()) {
                    StructureInfo structureInfo = (StructureInfo) it6.next();
                    String obj4 = structureInfo.structure.toString();
                    StructureInfo structureInfo2 = (StructureInfo) (list5.size() == i2 ? list5.get(i) : null);
                    arrayList5.add(new MainControlModel(obj4, null, structureInfo2 != null ? TextUtils.isEmpty(structureInfo2.structure) : i));
                    Iterator it7 = structureInfo.controls.iterator();
                    while (it7.hasNext()) {
                        arrayList5.add(new MainControlModel(obj4, new ControlWithState(structureInfo.componentName, (ControlInfo) it7.next(), null), false, 4, null));
                        i = 0;
                        i2 = 1;
                    }
                }
                ArrayList arrayList7 = new ArrayList();
                ArrayList arrayList8 = (ArrayList) list4;
                Iterator it8 = arrayList8.iterator();
                while (it8.hasNext()) {
                    Object next2 = it8.next();
                    if (next2 instanceof MainControlModel) {
                        arrayList7.add(next2);
                    }
                }
                ArrayList arrayList9 = new ArrayList();
                Iterator it9 = arrayList7.iterator();
                while (it9.hasNext()) {
                    ControlWithState controlWithState = ((MainControlModel) it9.next()).controlWithState;
                    String str = (controlWithState == null || (controlInfo2 = controlWithState.f249ci) == null) ? null : controlInfo2.controlId;
                    if (str != null) {
                        arrayList9.add(str);
                    }
                }
                ArrayList arrayList10 = new ArrayList();
                Iterator it10 = arrayList5.iterator();
                while (it10.hasNext()) {
                    ControlWithState controlWithState2 = ((MainControlModel) it10.next()).controlWithState;
                    String str2 = (controlWithState2 == null || (controlInfo = controlWithState2.f249ci) == null) ? null : controlInfo.controlId;
                    if (str2 != null) {
                        arrayList10.add(str2);
                    }
                }
                boolean z = !Intrinsics.areEqual(arrayList9, arrayList10);
                ComponentName componentName = componentInfo.componentName;
                if (z || !Intrinsics.areEqual(componentName, this.selectedItem.getComponentName()) || arrayList8.isEmpty()) {
                    arrayList8.clear();
                    arrayList8.add(mainComponentModel);
                    ControlsServiceInfo isPanelComponent = isPanelComponent(componentInfo);
                    if (isPanelComponent == null || isPanelComponent.panelActivity == null) {
                        arrayList8.addAll(arrayList5);
                    } else {
                        boolean booleanValue = ((Boolean) ((ControlsSettingsRepositoryImpl) this.controlsSettingsRepository).allowActionOnTrivialControlsInLockscreen.getValue()).booleanValue();
                        arrayList8.add(new MainPanelModel(PendingIntent.getActivityAsUser(this.context, 0, new Intent().setComponent(isPanelComponent.panelActivity).putExtra("android.service.controls.extra.LOCKSCREEN_ALLOW_TRIVIAL_CONTROLS", booleanValue), 201326592, null, ((UserTrackerImpl) this.userTracker).getUserHandle()), isPanelComponent.panelActivity, booleanValue));
                    }
                    this.verificationStructureInfos = getStructureInfosByUI(componentName);
                    this.adapterNeedToUpdateDataSet = true;
                }
                arrayList8.size();
                boolean z2 = selectedItem instanceof SelectedItem.ComponentItem;
                Lazy lazy = this.customControlsController;
                if (z2) {
                    ((ControlsControllerImpl) ((CustomControlsController) lazy.get())).subscribeToFavorites(((SelectedItem.ComponentItem) selectedItem).componentInfo);
                    this.selectedItem = new SelectedItem.ComponentItem(selectedItem.getName(), componentInfo);
                } else {
                    CustomControlsController customControlsController = (CustomControlsController) lazy.get();
                    ComponentName componentName2 = selectedItem.getComponentName();
                    StructureInfo structureInfo3 = new StructureInfo(selectedItem.getComponentName(), "", EmptyList.INSTANCE);
                    structureInfo3.customStructureInfo.active = true;
                    Unit unit = Unit.INSTANCE;
                    ((ControlsControllerImpl) customControlsController).subscribeToFavorites(new ComponentInfo(componentName2, CollectionsKt__CollectionsKt.mutableListOf(structureInfo3)));
                    ((ControlsBindingControllerImpl) ((ControlsControllerImpl) ((ControlsController) this.controlsController.get())).bindingController).retrieveLifecycleManager(selectedItem.getComponentName()).bindService(true, true);
                    this.selectedItem = new SelectedItem.PanelItem(selectedItem.getName(), componentName);
                }
                Log.d("CustomControlsUiControllerImpl", "update selectedItem = " + this.selectedItem);
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
