package com.android.systemui.controls.start;

import android.app.backup.BackupManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;
import com.android.settingslib.applications.ServiceListing;
import com.android.systemui.BasicRune;
import com.android.systemui.CoreStartable;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.controls.ControlsServiceInfo;
import com.android.systemui.controls.controller.AuxiliaryPersistenceWrapper;
import com.android.systemui.controls.controller.ControlsBindingControllerImpl;
import com.android.systemui.controls.controller.ControlsController;
import com.android.systemui.controls.controller.ControlsControllerImpl;
import com.android.systemui.controls.controller.ControlsFavoritePersistenceWrapper;
import com.android.systemui.controls.controller.CustomControlsController;
import com.android.systemui.controls.controller.UserStructure;
import com.android.systemui.controls.dagger.ControlsComponent;
import com.android.systemui.controls.management.ControlsListingController;
import com.android.systemui.controls.management.ControlsListingControllerImpl;
import com.android.systemui.controls.p005ui.CustomControlsUiControllerImpl;
import com.android.systemui.controls.p005ui.SelectedItem;
import com.android.systemui.controls.panels.AuthorizedPanelsRepository;
import com.android.systemui.controls.panels.AuthorizedPanelsRepositoryImpl;
import com.android.systemui.controls.panels.CustomSelectedComponentRepository;
import com.android.systemui.controls.panels.CustomSelectedComponentRepositoryImpl;
import com.android.systemui.controls.panels.SelectedComponentRepository;
import com.android.systemui.controls.panels.SelectedComponentRepositoryImpl;
import com.android.systemui.flags.FeatureFlagsRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.settings.UserFileManagerImpl;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executor;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ControlsStartable implements CoreStartable {
    public final AuthorizedPanelsRepository authorizedPanelsRepository;
    public final BroadcastDispatcher broadcastDispatcher;
    public final ControlsComponent controlsComponent;
    public final Executor executor;
    public final SelectedComponentRepository selectedComponentRepository;
    public final UserManager userManager;
    public final UserTracker userTracker;
    public final ControlsStartable$userTrackerCallback$1 userTrackerCallback = new UserTracker.Callback() { // from class: com.android.systemui.controls.start.ControlsStartable$userTrackerCallback$1
        @Override // com.android.systemui.settings.UserTracker.Callback
        public final void onUserChanged(int i, Context context) {
            ControlsStartable controlsStartable = ControlsStartable.this;
            ControlsController controlsController = controlsStartable.getControlsController();
            final UserHandle of = UserHandle.of(i);
            ControlsControllerImpl controlsControllerImpl = (ControlsControllerImpl) controlsController;
            controlsControllerImpl.userChanging = true;
            if (Intrinsics.areEqual(controlsControllerImpl.currentUser, of)) {
                controlsControllerImpl.userChanging = false;
            } else {
                Log.d("ControlsControllerImpl", "Changing to user: " + of);
                controlsControllerImpl.currentUser = of;
                UserStructure userStructure = new UserStructure(controlsControllerImpl.context, of, controlsControllerImpl.userFileManager);
                controlsControllerImpl.userStructure = userStructure;
                File file = userStructure.file;
                BackupManager backupManager = new BackupManager(controlsControllerImpl.userStructure.userContext);
                ControlsFavoritePersistenceWrapper controlsFavoritePersistenceWrapper = controlsControllerImpl.persistenceWrapper;
                controlsFavoritePersistenceWrapper.file = file;
                controlsFavoritePersistenceWrapper.backupManager = backupManager;
                File file2 = controlsControllerImpl.userStructure.auxiliaryFile;
                AuxiliaryPersistenceWrapper auxiliaryPersistenceWrapper = controlsControllerImpl.auxiliaryPersistenceWrapper;
                ControlsFavoritePersistenceWrapper controlsFavoritePersistenceWrapper2 = auxiliaryPersistenceWrapper.persistenceWrapper;
                controlsFavoritePersistenceWrapper2.file = file2;
                controlsFavoritePersistenceWrapper2.backupManager = null;
                auxiliaryPersistenceWrapper.initialize();
                controlsControllerImpl.resetFavorites();
                ControlsBindingControllerImpl controlsBindingControllerImpl = (ControlsBindingControllerImpl) controlsControllerImpl.bindingController;
                if (!Intrinsics.areEqual(of, controlsBindingControllerImpl.currentUser)) {
                    controlsBindingControllerImpl.unbind();
                    controlsBindingControllerImpl.currentUser = of;
                }
                final ControlsListingControllerImpl controlsListingControllerImpl = (ControlsListingControllerImpl) controlsControllerImpl.listingController;
                controlsListingControllerImpl.userChangeInProgress.incrementAndGet();
                controlsListingControllerImpl.serviceListing.setListening(false);
                controlsListingControllerImpl.backgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.controls.management.ControlsListingControllerImpl$changeUser$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        if (ControlsListingControllerImpl.this.userChangeInProgress.decrementAndGet() == 0) {
                            ControlsListingControllerImpl.this.currentUserId = of.getIdentifier();
                            Context createContextAsUser = ControlsListingControllerImpl.this.context.createContextAsUser(of, 0);
                            ControlsListingControllerImpl controlsListingControllerImpl2 = ControlsListingControllerImpl.this;
                            controlsListingControllerImpl2.serviceListing = (ServiceListing) controlsListingControllerImpl2.serviceListingBuilder.invoke(createContextAsUser);
                            ControlsListingControllerImpl controlsListingControllerImpl3 = ControlsListingControllerImpl.this;
                            ((ArrayList) controlsListingControllerImpl3.serviceListing.mCallbacks).add(controlsListingControllerImpl3.serviceListingCallback);
                            ControlsListingControllerImpl.this.serviceListing.setListening(true);
                            ControlsListingControllerImpl.this.serviceListing.reload();
                        }
                    }
                });
                controlsControllerImpl.userChanging = false;
            }
            ControlsStartable.access$startForUser(controlsStartable);
        }
    };

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.controls.start.ControlsStartable$userTrackerCallback$1] */
    public ControlsStartable(Executor executor, ControlsComponent controlsComponent, UserTracker userTracker, AuthorizedPanelsRepository authorizedPanelsRepository, SelectedComponentRepository selectedComponentRepository, UserManager userManager, BroadcastDispatcher broadcastDispatcher) {
        this.executor = executor;
        this.controlsComponent = controlsComponent;
        this.userTracker = userTracker;
        this.authorizedPanelsRepository = authorizedPanelsRepository;
        this.selectedComponentRepository = selectedComponentRepository;
        this.userManager = userManager;
        this.broadcastDispatcher = broadcastDispatcher;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final void access$startForUser(final ControlsStartable controlsStartable) {
        SelectedItem selectedItem;
        ControlsServiceInfo controlsServiceInfo;
        ControlsListingControllerImpl controlsListingControllerImpl = (ControlsListingControllerImpl) ((ControlsListingController) controlsStartable.controlsComponent.getControlsListingController().get());
        PackageManager packageManager = controlsListingControllerImpl.context.getPackageManager();
        Intent intent = new Intent("android.service.controls.ControlsProviderService");
        UserTrackerImpl userTrackerImpl = (UserTrackerImpl) controlsListingControllerImpl.userTracker;
        List queryIntentServicesAsUser = packageManager.queryIntentServicesAsUser(intent, PackageManager.ResolveInfoFlags.of(786564), userTrackerImpl.getUserHandle());
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(queryIntentServicesAsUser, 10));
        Iterator it = queryIntentServicesAsUser.iterator();
        while (it.hasNext()) {
            arrayList.add(new ControlsServiceInfo(userTrackerImpl.getUserContext(), ((ResolveInfo) it.next()).serviceInfo));
        }
        controlsListingControllerImpl.updateServices(arrayList);
        SelectedComponentRepositoryImpl selectedComponentRepositoryImpl = (SelectedComponentRepositoryImpl) controlsStartable.selectedComponentRepository;
        selectedComponentRepositoryImpl.getClass();
        if (((FeatureFlagsRelease) selectedComponentRepositoryImpl.featureFlags).isEnabled(Flags.APP_PANELS_REMOVE_APPS_ALLOWED) ? ((UserFileManagerImpl) selectedComponentRepositoryImpl.userFileManager).getSharedPreferences(((UserTrackerImpl) selectedComponentRepositoryImpl.userTracker).getUserId(), "controls_prefs").getBoolean("should_add_default_panel", true) : true) {
            if (BasicRune.CONTROLS_SAMSUNG_STYLE) {
                ControlsControllerImpl controlsControllerImpl = (ControlsControllerImpl) controlsStartable.getCustomControlsController();
                selectedItem = ((CustomControlsUiControllerImpl) controlsControllerImpl.customUiController).getPreferredComponentSelectedItem(controlsControllerImpl.getActiveFavoritesComponent());
            } else {
                ControlsControllerImpl controlsControllerImpl2 = (ControlsControllerImpl) controlsStartable.getControlsController();
                controlsControllerImpl2.getFavorites();
                ((CustomControlsUiControllerImpl) controlsControllerImpl2.uiController).getClass();
                SelectedItem.Companion.getClass();
                selectedItem = SelectedItem.EMPTY_SELECTION_COMPONENT;
            }
            SelectedItem.Companion.getClass();
            if (Intrinsics.areEqual(selectedItem, SelectedItem.EMPTY_SELECTION)) {
                List currentServices = ((ControlsListingControllerImpl) ((ControlsListingController) controlsStartable.controlsComponent.getControlsListingController().get())).getCurrentServices();
                ArrayList arrayList2 = new ArrayList();
                Iterator it2 = ((ArrayList) currentServices).iterator();
                while (it2.hasNext()) {
                    Object next = it2.next();
                    if (((ControlsServiceInfo) next).panelActivity != null) {
                        arrayList2.add(next);
                    }
                }
                Iterator it3 = ArraysKt___ArraysKt.toSet(((AuthorizedPanelsRepositoryImpl) controlsStartable.authorizedPanelsRepository).context.getResources().getStringArray(R.array.config_controlsPreferredPackages)).iterator();
                do {
                    controlsServiceInfo = null;
                    if (!it3.hasNext()) {
                        break;
                    }
                    String str = (String) it3.next();
                    Iterator it4 = arrayList2.iterator();
                    while (true) {
                        if (!it4.hasNext()) {
                            break;
                        }
                        Object next2 = it4.next();
                        if (Intrinsics.areEqual(((ControlsServiceInfo) next2).componentName.getPackageName(), str)) {
                            controlsServiceInfo = next2;
                            break;
                        }
                    }
                    controlsServiceInfo = controlsServiceInfo;
                } while (controlsServiceInfo == null);
                if (controlsServiceInfo != null) {
                    boolean z = BasicRune.CONTROLS_SAMSUNG_STYLE;
                    ComponentName componentName = controlsServiceInfo.componentName;
                    if (z) {
                        ((CustomSelectedComponentRepositoryImpl) ((ControlsControllerImpl) controlsStartable.getCustomControlsController()).customSelectedComponentRepository).setSelectedComponent(new CustomSelectedComponentRepository.CustomSelectedComponent(new SelectedItem.PanelItem(controlsServiceInfo.loadLabel(), componentName)));
                    } else {
                        ((SelectedComponentRepositoryImpl) ((ControlsControllerImpl) controlsStartable.getControlsController()).selectedComponentRepository).setSelectedComponent(new SelectedComponentRepository.SelectedComponent(new SelectedItem.PanelItem(controlsServiceInfo.loadLabel(), componentName)));
                    }
                }
            }
        }
        UserTrackerImpl userTrackerImpl2 = (UserTrackerImpl) controlsStartable.userTracker;
        if (controlsStartable.userManager.isUserUnlocked(userTrackerImpl2.getUserId())) {
            controlsStartable.bindToPanelInternal();
        } else {
            BroadcastDispatcher.registerReceiver$default(controlsStartable.broadcastDispatcher, new BroadcastReceiver() { // from class: com.android.systemui.controls.start.ControlsStartable$bindToPanel$1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent2) {
                    ControlsStartable controlsStartable2 = ControlsStartable.this;
                    if (controlsStartable2.userManager.isUserUnlocked(((UserTrackerImpl) controlsStartable2.userTracker).getUserId())) {
                        ControlsStartable.this.bindToPanelInternal();
                        ControlsStartable.this.broadcastDispatcher.unregisterReceiver(this);
                    }
                }
            }, new IntentFilter("android.intent.action.USER_UNLOCKED"), controlsStartable.executor, userTrackerImpl2.getUserHandle(), 0, null, 48);
        }
    }

    public final void bindToPanelInternal() {
        SelectedItem selectedItem;
        Object obj;
        if (BasicRune.CONTROLS_SAMSUNG_STYLE) {
            ControlsControllerImpl controlsControllerImpl = (ControlsControllerImpl) getCustomControlsController();
            selectedItem = ((CustomControlsUiControllerImpl) controlsControllerImpl.customUiController).getPreferredComponentSelectedItem(controlsControllerImpl.getActiveFavoritesComponent());
        } else {
            ControlsControllerImpl controlsControllerImpl2 = (ControlsControllerImpl) getControlsController();
            controlsControllerImpl2.getFavorites();
            ((CustomControlsUiControllerImpl) controlsControllerImpl2.uiController).getClass();
            SelectedItem.Companion.getClass();
            selectedItem = SelectedItem.EMPTY_SELECTION_COMPONENT;
        }
        List currentServices = ((ControlsListingControllerImpl) ((ControlsListingController) this.controlsComponent.getControlsListingController().get())).getCurrentServices();
        ArrayList arrayList = new ArrayList();
        Iterator it = ((ArrayList) currentServices).iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Object next = it.next();
            if (((ControlsServiceInfo) next).panelActivity != null) {
                arrayList.add(next);
            }
        }
        if (selectedItem instanceof SelectedItem.PanelItem) {
            Iterator it2 = arrayList.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    obj = null;
                    break;
                } else {
                    obj = it2.next();
                    if (Intrinsics.areEqual(((ControlsServiceInfo) obj).componentName, selectedItem.getComponentName())) {
                        break;
                    }
                }
            }
            if (obj != null) {
                ((ControlsBindingControllerImpl) ((ControlsControllerImpl) getControlsController()).bindingController).retrieveLifecycleManager(selectedItem.getComponentName()).bindService(true, true);
            }
        }
    }

    public final ControlsController getControlsController() {
        return (ControlsController) this.controlsComponent.getControlsController().get();
    }

    public final CustomControlsController getCustomControlsController() {
        ControlsComponent controlsComponent = this.controlsComponent;
        return (CustomControlsController) (controlsComponent.featureEnabled ? Optional.of(controlsComponent.lazyCustomControlsController.get()) : Optional.empty()).get();
    }

    @Override // com.android.systemui.CoreStartable
    public final void onBootCompleted() {
        if (this.controlsComponent.featureEnabled) {
            Runnable runnable = new Runnable() { // from class: com.android.systemui.controls.start.ControlsStartable$onBootCompleted$1
                @Override // java.lang.Runnable
                public final void run() {
                    ControlsStartable.access$startForUser(ControlsStartable.this);
                }
            };
            Executor executor = this.executor;
            executor.execute(runnable);
            ((UserTrackerImpl) this.userTracker).addCallback(this.userTrackerCallback, executor);
        }
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
    }
}
