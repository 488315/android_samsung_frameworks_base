package com.android.systemui.controls.controller;

import android.app.PendingIntent;
import android.app.backup.BackupManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.ContentObserver;
import android.os.IBinder;
import android.os.UserHandle;
import android.service.controls.Control;
import android.service.controls.ControlsProviderInfo;
import android.service.controls.IControlsProviderInfoSubscriber;
import android.util.ArrayMap;
import android.util.Log;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import androidx.core.app.NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0;
import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardFMMViewController$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.controls.ControlStatus;
import com.android.systemui.controls.ControlsServiceInfo;
import com.android.systemui.controls.controller.ControlsBindingController;
import com.android.systemui.controls.controller.ControlsBindingControllerImpl;
import com.android.systemui.controls.controller.ControlsBindingControllerImpl.LoadSubscriber;
import com.android.systemui.controls.controller.ControlsProviderLifecycleManager;
import com.android.systemui.controls.controller.ControlsProviderLifecycleManager.LoadProviderInfo;
import com.android.systemui.controls.controller.ControlsProviderLifecycleManager.Subscribe;
import com.android.systemui.controls.controller.ControlsProviderLifecycleManager.Suggest;
import com.android.systemui.controls.controller.util.BadgeProvider;
import com.android.systemui.controls.controller.util.BadgeProviderImpl;
import com.android.systemui.controls.controller.util.BadgeProviderImpl$invalidate$1;
import com.android.systemui.controls.management.ControlsListingController;
import com.android.systemui.controls.management.ControlsListingControllerImpl;
import com.android.systemui.controls.panels.AuthorizedPanelsRepository;
import com.android.systemui.controls.panels.AuthorizedPanelsRepositoryImpl;
import com.android.systemui.controls.panels.SecSelectedComponentRepository;
import com.android.systemui.controls.panels.SecSelectedComponentRepositoryImpl;
import com.android.systemui.controls.panels.SelectedComponentRepository;
import com.android.systemui.controls.ui.ControlsUiController;
import com.android.systemui.controls.ui.SecControlsUiController;
import com.android.systemui.controls.ui.SecControlsUiControllerImpl;
import com.android.systemui.controls.ui.SecControlsUiControllerImpl$updateLaunchingAppButton$1;
import com.android.systemui.controls.ui.SelectedItem;
import com.android.systemui.controls.util.ControlsUtil;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.settings.UserFileManager;
import com.android.systemui.settings.UserFileManagerImpl;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.settings.SecureSettings;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.collections.EmptySet;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.collections.SetsKt___SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class ControlsControllerImpl implements Dumpable, ControlsController, SecControlsController {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AuthorizedPanelsRepository authorizedPanelsRepository;
    public final AuxiliaryPersistenceWrapper auxiliaryPersistenceWrapper;
    public final BadgeProvider badgeProvider;
    public final ControlsBindingController bindingController;
    public final Context context;
    public final ControlsUtil controlsUtil;
    public UserHandle currentUser;
    public final DelayableExecutor executor;
    public boolean isAutoRemove;
    public final ControlsListingController listingController;
    public final ControlsFavoritePersistenceWrapper persistenceWrapper;
    public final ControlsControllerImpl$restoreFinishedReceiver$1 restoreFinishedReceiver;
    public final SecControlsBindingController secBindingController;
    public final SecSelectedComponentRepository secSelectedComponentRepository;
    public final SecControlsUiController secUiController;
    public final SecureSettings secureSettings;
    public boolean seedingInProgress;
    public final ControlsUiController uiController;
    public boolean userChanging;
    public final UserFileManager userFileManager;
    public UserStructure userStructure;
    public final UserTracker userTracker;
    public final List seedingCallbacks = new ArrayList();
    public final List autoAddList = new ArrayList();

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    public ControlsControllerImpl(Context context, DelayableExecutor delayableExecutor, ControlsUiController controlsUiController, SelectedComponentRepository selectedComponentRepository, ControlsBindingController controlsBindingController, ControlsListingController controlsListingController, UserFileManager userFileManager, UserTracker userTracker, AuthorizedPanelsRepository authorizedPanelsRepository, Optional<ControlsFavoritePersistenceWrapper> optional, DumpManager dumpManager, SecControlsBindingController secControlsBindingController, SecControlsUiController secControlsUiController, SecSelectedComponentRepository secSelectedComponentRepository, ControlsUtil controlsUtil, BadgeProvider badgeProvider, SecureSettings secureSettings) {
        this.context = context;
        this.executor = delayableExecutor;
        this.uiController = controlsUiController;
        this.bindingController = controlsBindingController;
        this.listingController = controlsListingController;
        this.userFileManager = userFileManager;
        this.userTracker = userTracker;
        this.authorizedPanelsRepository = authorizedPanelsRepository;
        this.secBindingController = secControlsBindingController;
        this.secUiController = secControlsUiController;
        this.secSelectedComponentRepository = secSelectedComponentRepository;
        this.controlsUtil = controlsUtil;
        this.badgeProvider = badgeProvider;
        this.secureSettings = secureSettings;
        this.userChanging = true;
        this.currentUser = ((UserTrackerImpl) userTracker).getUserHandle();
        this.userStructure = new UserStructure(context, this.currentUser, userFileManager);
        this.persistenceWrapper = optional.orElseGet(new Supplier() { // from class: com.android.systemui.controls.controller.ControlsControllerImpl.1
            @Override // java.util.function.Supplier
            public final Object get() {
                ControlsControllerImpl controlsControllerImpl = ControlsControllerImpl.this;
                return new ControlsFavoritePersistenceWrapper(controlsControllerImpl.userStructure.file, controlsControllerImpl.executor, new BackupManager(ControlsControllerImpl.this.userStructure.userContext), ControlsControllerImpl.this.secureSettings);
            }
        });
        this.auxiliaryPersistenceWrapper = new AuxiliaryPersistenceWrapper(this.userStructure.auxiliaryFile, delayableExecutor, secureSettings);
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.controls.controller.ControlsControllerImpl$restoreFinishedReceiver$1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                if (intent.getIntExtra("android.intent.extra.USER_ID", -10000) == ControlsControllerImpl.this.currentUser.getIdentifier()) {
                    final ControlsControllerImpl controlsControllerImpl = ControlsControllerImpl.this;
                    controlsControllerImpl.executor.execute(new Runnable() { // from class: com.android.systemui.controls.controller.ControlsControllerImpl$restoreFinishedReceiver$1$onReceive$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            Log.d("ControlsControllerImpl", "Restore finished, storing auxiliary favorites");
                            ControlsControllerImpl.this.auxiliaryPersistenceWrapper.initialize();
                            ControlsControllerImpl controlsControllerImpl2 = ControlsControllerImpl.this;
                            controlsControllerImpl2.persistenceWrapper.storeFavorites(controlsControllerImpl2.auxiliaryPersistenceWrapper.favorites);
                            ControlsControllerImpl.this.resetFavorites();
                        }
                    });
                }
            }
        };
        new ContentObserver() { // from class: com.android.systemui.controls.controller.ControlsControllerImpl$settingObserver$1
            {
                super(null);
            }

            public final void onChange(boolean z, Collection collection, int i, int i2) {
                ControlsControllerImpl controlsControllerImpl = ControlsControllerImpl.this;
                if (controlsControllerImpl.userChanging || i2 != controlsControllerImpl.currentUser.getIdentifier()) {
                    return;
                }
                ControlsControllerImpl.this.resetFavorites();
            }
        };
        ControlsListingController.ControlsListingCallback controlsListingCallback = new ControlsListingController.ControlsListingCallback() { // from class: com.android.systemui.controls.controller.ControlsControllerImpl$listingCallback$1
            @Override // com.android.systemui.controls.management.ControlsListingController.ControlsListingCallback
            public final void onServicesUpdated(final List list) {
                int size = ((ArrayList) list).size();
                final ControlsControllerImpl controlsControllerImpl = ControlsControllerImpl.this;
                int identifier = controlsControllerImpl.currentUser.getIdentifier();
                int userId = ((UserTrackerImpl) controlsControllerImpl.userTracker).getUserId();
                StringBuilder m = RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(size, identifier, "onServicesUpdated serviceInfos.size = ", ", currentUserId = ", ", userTracker = ");
                m.append(userId);
                Log.d("ControlsControllerImpl", m.toString());
                controlsControllerImpl.executor.execute(new Runnable() { // from class: com.android.systemui.controls.controller.ControlsControllerImpl$listingCallback$1$onServicesUpdated$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        SelectedComponentRepository.SelectedComponent selectedComponent;
                        if (ControlsControllerImpl.this.currentUser.getIdentifier() != ((UserTrackerImpl) ControlsControllerImpl.this.userTracker).getUserId()) {
                            Log.d("ControlsControllerImpl", "onServiceUpdate user diff, update skipped");
                            return;
                        }
                        List list2 = list;
                        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
                        Iterator it = list2.iterator();
                        while (it.hasNext()) {
                            arrayList.add(((ControlsServiceInfo) it.next()).componentName);
                        }
                        Set set = CollectionsKt___CollectionsKt.toSet(arrayList);
                        Favorites.INSTANCE.getClass();
                        List allStructures = Favorites.getAllStructures();
                        ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(allStructures, 10));
                        Iterator it2 = ((ArrayList) allStructures).iterator();
                        while (it2.hasNext()) {
                            arrayList2.add(((StructureInfo) it2.next()).componentName);
                        }
                        Set set2 = CollectionsKt___CollectionsKt.toSet(arrayList2);
                        BadgeProviderImpl badgeProviderImpl = (BadgeProviderImpl) ControlsControllerImpl.this.badgeProvider;
                        boolean isEmpty = badgeProviderImpl.badgeRequiredSet.isEmpty();
                        BadgeProviderImpl.Companion companion = BadgeProviderImpl.Companion;
                        if (isEmpty && badgeProviderImpl.badgeNotRequiredSet.isEmpty() && (!set2.isEmpty())) {
                            Set set3 = badgeProviderImpl.badgeNotRequiredSet;
                            companion.getClass();
                            set3.addAll(BadgeProviderImpl.Companion.toPackagesSet(set2));
                        }
                        companion.getClass();
                        Set packagesSet = BadgeProviderImpl.Companion.toPackagesSet(set);
                        Set set4 = badgeProviderImpl.badgeRequiredSet;
                        Set set5 = packagesSet;
                        Set subtract = CollectionsKt___CollectionsKt.subtract(set4, set5);
                        if (subtract.isEmpty()) {
                            subtract = null;
                        }
                        if (subtract != null) {
                            set4.removeAll(subtract);
                        }
                        Set set6 = badgeProviderImpl.badgeNotRequiredSet;
                        Set subtract2 = CollectionsKt___CollectionsKt.subtract(set6, set5);
                        if (subtract2.isEmpty()) {
                            subtract2 = null;
                        }
                        if (subtract2 != null) {
                            set6.removeAll(subtract2);
                        }
                        Set subtract3 = CollectionsKt___CollectionsKt.subtract(CollectionsKt___CollectionsKt.subtract(BadgeProviderImpl.Companion.toPackagesSet(set), badgeProviderImpl.badgeRequiredSet), badgeProviderImpl.badgeNotRequiredSet);
                        Set set7 = subtract3.isEmpty() ? null : subtract3;
                        if (set7 != null) {
                            badgeProviderImpl.badgeRequiredSet.addAll(set7);
                        }
                        badgeProviderImpl.uiExecutor.execute(new BadgeProviderImpl$invalidate$1(badgeProviderImpl));
                        BadgeProviderImpl.onServicesUpdated$flush(badgeProviderImpl, badgeProviderImpl.badgeRequiredSet, "ControlsBadgeRequired", "badgeRequiredSet");
                        BadgeProviderImpl.onServicesUpdated$flush(badgeProviderImpl, badgeProviderImpl.badgeNotRequiredSet, "ControlsBadgeNotRequired", "badgeNotRequiredSet");
                        List<ControlsServiceInfo> list3 = list;
                        ControlsControllerImpl controlsControllerImpl2 = ControlsControllerImpl.this;
                        for (ControlsServiceInfo controlsServiceInfo : list3) {
                            Favorites favorites = Favorites.INSTANCE;
                            ComponentName componentName = controlsServiceInfo.componentName;
                            favorites.getClass();
                            if (!Favorites.getActiveFlag(componentName)) {
                                ControlsUtil controlsUtil2 = controlsControllerImpl2.controlsUtil;
                                String packageName = controlsServiceInfo.componentName.getPackageName();
                                controlsUtil2.getClass();
                                if ("com.google.android.apps.chromecast.app".equals(packageName)) {
                                    if (((BadgeProviderImpl) controlsControllerImpl2.badgeProvider).badgeRequiredSet.contains(controlsServiceInfo.componentName.getPackageName())) {
                                        SecSelectedComponentRepository secSelectedComponentRepository2 = controlsControllerImpl2.secSelectedComponentRepository;
                                        selectedComponent = ((SecSelectedComponentRepositoryImpl) secSelectedComponentRepository2).getSelectedComponent(UserHandle.CURRENT);
                                        if (selectedComponent == null) {
                                            ((SecSelectedComponentRepositoryImpl) secSelectedComponentRepository2).setSelectedComponent(new SelectedComponentRepository.SelectedComponent(new SelectedItem.PanelItem(controlsServiceInfo.loadLabel(), controlsServiceInfo.componentName)));
                                        }
                                        controlsControllerImpl2.setActivePanelFlag(controlsServiceInfo.componentName, true);
                                        ((AuthorizedPanelsRepositoryImpl) controlsControllerImpl2.authorizedPanelsRepository).addAuthorizedPanels(Collections.singleton(controlsServiceInfo.componentName.getPackageName()));
                                        if (controlsControllerImpl2.confirmAvailability()) {
                                            controlsControllerImpl2.executor.execute(new ControlsControllerImpl$saveCurrentFavorites$1(controlsControllerImpl2));
                                        }
                                    }
                                }
                            }
                        }
                        ControlsControllerImpl controlsControllerImpl3 = ControlsControllerImpl.this;
                        SharedPreferences sharedPreferences$1 = ((UserFileManagerImpl) controlsControllerImpl3.userFileManager).getSharedPreferences$1(((UserTrackerImpl) controlsControllerImpl3.userTracker).getUserId(), SystemUIAnalytics.CONTROL_PREF_NAME);
                        Set<String> stringSet = sharedPreferences$1.getStringSet("SeedingCompleted", new LinkedHashSet());
                        Set set8 = set;
                        ArrayList arrayList3 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(set8, 10));
                        Iterator it3 = set8.iterator();
                        while (it3.hasNext()) {
                            arrayList3.add(((ComponentName) it3.next()).getPackageName());
                        }
                        sharedPreferences$1.edit().putStringSet("SeedingCompleted", stringSet != null ? CollectionsKt___CollectionsKt.intersect(stringSet, CollectionsKt___CollectionsKt.toSet(arrayList3)) : EmptySet.INSTANCE).apply();
                        Set set9 = set2;
                        Set<ComponentName> subtract4 = CollectionsKt___CollectionsKt.subtract(set9, set8);
                        ControlsControllerImpl controlsControllerImpl4 = ControlsControllerImpl.this;
                        boolean z = false;
                        for (final ComponentName componentName2 : subtract4) {
                            Favorites favorites2 = Favorites.INSTANCE;
                            Intrinsics.checkNotNull(componentName2);
                            favorites2.getClass();
                            Favorites.removeStructures(componentName2, false);
                            final ControlsBindingControllerImpl controlsBindingControllerImpl = (ControlsBindingControllerImpl) controlsControllerImpl4.bindingController;
                            controlsBindingControllerImpl.getClass();
                            controlsBindingControllerImpl.backgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.controls.controller.ControlsBindingControllerImpl$onComponentRemoved$1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    ControlsBindingControllerImpl controlsBindingControllerImpl2 = ControlsBindingControllerImpl.this;
                                    ControlsProviderLifecycleManager controlsProviderLifecycleManager = controlsBindingControllerImpl2.currentProvider;
                                    if (controlsProviderLifecycleManager != null) {
                                        if (Intrinsics.areEqual(controlsProviderLifecycleManager.componentName, componentName2)) {
                                            controlsBindingControllerImpl2.unbind();
                                        }
                                    }
                                }
                            });
                            z = true;
                        }
                        if (!ControlsControllerImpl.this.auxiliaryPersistenceWrapper.favorites.isEmpty()) {
                            Set<ComponentName> subtract5 = CollectionsKt___CollectionsKt.subtract(set8, set9);
                            ControlsControllerImpl controlsControllerImpl5 = ControlsControllerImpl.this;
                            for (ComponentName componentName3 : subtract5) {
                                AuxiliaryPersistenceWrapper auxiliaryPersistenceWrapper = controlsControllerImpl5.auxiliaryPersistenceWrapper;
                                Intrinsics.checkNotNull(componentName3);
                                List<StructureInfo> cachedFavoritesAndRemoveFor = auxiliaryPersistenceWrapper.getCachedFavoritesAndRemoveFor(componentName3);
                                if (!cachedFavoritesAndRemoveFor.isEmpty()) {
                                    for (StructureInfo structureInfo : cachedFavoritesAndRemoveFor) {
                                        Favorites.INSTANCE.getClass();
                                        Favorites.replaceControls(structureInfo);
                                    }
                                    z = true;
                                }
                            }
                            Set<ComponentName> intersect = CollectionsKt___CollectionsKt.intersect(set8, set9);
                            ControlsControllerImpl controlsControllerImpl6 = ControlsControllerImpl.this;
                            for (ComponentName componentName4 : intersect) {
                                AuxiliaryPersistenceWrapper auxiliaryPersistenceWrapper2 = controlsControllerImpl6.auxiliaryPersistenceWrapper;
                                Intrinsics.checkNotNull(componentName4);
                                auxiliaryPersistenceWrapper2.getCachedFavoritesAndRemoveFor(componentName4);
                            }
                        }
                        if (z) {
                            Log.d("ControlsControllerImpl", "Detected change in available services, storing updated favorites");
                            ControlsFavoritePersistenceWrapper controlsFavoritePersistenceWrapper = ControlsControllerImpl.this.persistenceWrapper;
                            Favorites.INSTANCE.getClass();
                            controlsFavoritePersistenceWrapper.storeFavorites(Favorites.getAllStructures());
                        }
                    }
                });
            }
        };
        dumpManager.registerDumpable(this);
        resetFavorites();
        this.userChanging = false;
        context.registerReceiver(broadcastReceiver, new IntentFilter("com.android.systemui.backup.RESTORE_FINISHED"), "com.android.systemui.permission.SELF", null, 4);
        ((ControlsListingControllerImpl) controlsListingController).addCallback(controlsListingCallback);
    }

    @Override // com.android.systemui.util.UserAwareController
    public final void changeUser(final UserHandle userHandle) {
        this.userChanging = true;
        if (Intrinsics.areEqual(this.currentUser, userHandle)) {
            this.userChanging = false;
            return;
        }
        this.executor.executeDelayed(new Runnable() { // from class: com.android.systemui.controls.controller.ControlsControllerImpl$changeUser$1
            @Override // java.lang.Runnable
            public final void run() {
                ControlsControllerImpl controlsControllerImpl = ControlsControllerImpl.this;
                UserHandle userHandle2 = userHandle;
                int i = ControlsControllerImpl.$r8$clinit;
                controlsControllerImpl.getClass();
                Log.d("ControlsControllerImpl", "Changing to user: " + userHandle2);
                controlsControllerImpl.currentUser = userHandle2;
                UserStructure userStructure = new UserStructure(controlsControllerImpl.context, userHandle2, controlsControllerImpl.userFileManager);
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
                controlsControllerImpl.bindingController.changeUser(userHandle2);
                ((ControlsListingControllerImpl) controlsControllerImpl.listingController).changeUser(userHandle2);
                controlsControllerImpl.userChanging = false;
            }
        }, 30L, TimeUnit.MILLISECONDS);
    }

    public final boolean confirmAvailability() {
        if (!this.userChanging) {
            return true;
        }
        Log.w("ControlsControllerImpl", "Controls not available while user is changing");
        return false;
    }

    public final ControlStatus createRemovedStatus(ComponentName componentName, ControlInfo controlInfo, CharSequence charSequence, boolean z) {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");
        intent.setPackage(componentName.getPackageName());
        return new ControlStatus(new Control.StatelessBuilder(controlInfo.controlId, PendingIntent.getActivity(this.context, componentName.hashCode(), intent, QuickStepContract.SYSUI_STATE_REQUESTED_RECENT_KEY)).setTitle(controlInfo.controlTitle).setSubtitle(controlInfo.controlSubtitle).setStructure(charSequence).setDeviceType(controlInfo.deviceType).build(), componentName, true, z);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("ControlsController state:");
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("  Changing users: ", this.userChanging, printWriter);
        printWriter.println("  Current user: " + this.currentUser.getIdentifier());
        printWriter.println("  Favorites:");
        Favorites.INSTANCE.getClass();
        Iterator it = ((ArrayList) Favorites.getAllStructures()).iterator();
        while (it.hasNext()) {
            StructureInfo structureInfo = (StructureInfo) it.next();
            printWriter.println("    " + structureInfo);
            Iterator it2 = structureInfo.controls.iterator();
            while (it2.hasNext()) {
                printWriter.println("      " + ((ControlInfo) it2.next()));
            }
        }
        printWriter.println(this.bindingController.toString());
    }

    public final List getActiveFavoritesComponent() {
        Favorites.INSTANCE.getClass();
        List allStructures = Favorites.getAllStructures();
        ArrayList arrayList = new ArrayList();
        Iterator it = ((ArrayList) allStructures).iterator();
        while (it.hasNext()) {
            Object next = it.next();
            if (((StructureInfo) next).active) {
                arrayList.add(next);
            }
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            Object next2 = it2.next();
            ComponentName componentName = ((StructureInfo) next2).componentName;
            Object obj = linkedHashMap.get(componentName);
            if (obj == null) {
                obj = new ArrayList();
                linkedHashMap.put(componentName, obj);
            }
            ((List) obj).add(next2);
        }
        ((ArrayList) this.autoAddList).clear();
        Favorites.INSTANCE.getClass();
        Log.d("ControlsControllerImpl", "getActiveFavoritesComponent getFavorites = " + Favorites.getAllStructures());
        Log.d("ControlsControllerImpl", "getActiveFavoritesComponent activeFavoriteStructureInfos = " + arrayList + ", favoriteComponentInfos = " + linkedHashMap);
        ArrayList arrayList2 = new ArrayList(linkedHashMap.size());
        for (Map.Entry entry : linkedHashMap.entrySet()) {
            arrayList2.add(new ComponentInfo((ComponentName) entry.getKey(), (List) entry.getValue()));
        }
        return arrayList2;
    }

    @Override // com.android.systemui.util.UserAwareController
    public final int getCurrentUserId() {
        return this.currentUser.getIdentifier();
    }

    public final void loadForComponent(final ComponentName componentName, final Consumer consumer, final Consumer consumer2, final Consumer consumer3) {
        if (!confirmAvailability()) {
            if (this.userChanging) {
                this.executor.executeDelayed(new Runnable() { // from class: com.android.systemui.controls.controller.ControlsControllerImpl$loadForComponent$3
                    @Override // java.lang.Runnable
                    public final void run() {
                        ControlsControllerImpl.this.loadForComponent(componentName, consumer, consumer2, consumer3);
                    }
                }, 500L, TimeUnit.MILLISECONDS);
            }
            EmptyList emptyList = EmptyList.INSTANCE;
            consumer.accept(new ControlsControllerKt$createLoadDataObject$1(emptyList, emptyList, true));
        }
        ControlsBindingController.LoadCallback loadCallback = new ControlsBindingController.LoadCallback() { // from class: com.android.systemui.controls.controller.ControlsControllerImpl$loadForComponent$4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                final List list = (List) obj;
                final ControlsControllerImpl controlsControllerImpl = ControlsControllerImpl.this;
                DelayableExecutor delayableExecutor = controlsControllerImpl.executor;
                final ComponentName componentName2 = componentName;
                final Consumer consumer4 = consumer;
                delayableExecutor.execute(new Runnable() { // from class: com.android.systemui.controls.controller.ControlsControllerImpl$loadForComponent$4$accept$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        Favorites favorites = Favorites.INSTANCE;
                        ComponentName componentName3 = componentName2;
                        favorites.getClass();
                        List controlsForComponent = Favorites.getControlsForComponent(componentName3);
                        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(controlsForComponent, 10));
                        Iterator it = ((ArrayList) controlsForComponent).iterator();
                        while (it.hasNext()) {
                            arrayList.add(((ControlInfo) it.next()).controlId);
                        }
                        Favorites favorites2 = Favorites.INSTANCE;
                        ComponentName componentName4 = componentName2;
                        List list2 = list;
                        favorites2.getClass();
                        if (Favorites.updateControls(componentName4, list2)) {
                            controlsControllerImpl.persistenceWrapper.storeFavorites(Favorites.getAllStructures());
                        }
                        ControlsControllerImpl controlsControllerImpl2 = controlsControllerImpl;
                        Set set = CollectionsKt___CollectionsKt.toSet(arrayList);
                        List list3 = list;
                        int i = ControlsControllerImpl.$r8$clinit;
                        controlsControllerImpl2.getClass();
                        List list4 = list3;
                        ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list4, 10));
                        Iterator it2 = list4.iterator();
                        while (it2.hasNext()) {
                            arrayList2.add(((Control) it2.next()).getControlId());
                        }
                        Set minus = SetsKt___SetsKt.minus(set, (Iterable) arrayList2);
                        List<Control> list5 = list;
                        ComponentName componentName5 = componentName2;
                        ArrayList arrayList3 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list5, 10));
                        for (Control control : list5) {
                            arrayList3.add(new ControlStatus(control, componentName5, arrayList.contains(control.getControlId()), false, 8, null));
                        }
                        ArrayList arrayList4 = new ArrayList();
                        Favorites favorites3 = Favorites.INSTANCE;
                        ComponentName componentName6 = componentName2;
                        favorites3.getClass();
                        List<StructureInfo> structuresForComponent = Favorites.getStructuresForComponent(componentName6);
                        ControlsControllerImpl controlsControllerImpl3 = controlsControllerImpl;
                        ComponentName componentName7 = componentName2;
                        for (StructureInfo structureInfo : structuresForComponent) {
                            for (ControlInfo controlInfo : structureInfo.controls) {
                                if (minus.contains(controlInfo.controlId)) {
                                    arrayList4.add(controlsControllerImpl3.createRemovedStatus(componentName7, controlInfo, structureInfo.structure, true));
                                }
                            }
                        }
                        consumer4.accept(new ControlsControllerKt$createLoadDataObject$1(CollectionsKt___CollectionsKt.plus((Iterable) arrayList3, (Collection) arrayList4), arrayList, false));
                    }
                });
            }

            @Override // com.android.systemui.controls.controller.ControlsBindingController.LoadCallback
            public final void error(String str) {
                final ControlsControllerImpl controlsControllerImpl = ControlsControllerImpl.this;
                DelayableExecutor delayableExecutor = controlsControllerImpl.executor;
                final ComponentName componentName2 = componentName;
                final Consumer consumer4 = consumer;
                delayableExecutor.execute(new Runnable() { // from class: com.android.systemui.controls.controller.ControlsControllerImpl$loadForComponent$4$error$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        Favorites favorites = Favorites.INSTANCE;
                        ComponentName componentName3 = componentName2;
                        favorites.getClass();
                        List<StructureInfo> structuresForComponent = Favorites.getStructuresForComponent(componentName3);
                        ControlsControllerImpl controlsControllerImpl2 = controlsControllerImpl;
                        ComponentName componentName4 = componentName2;
                        ArrayList arrayList = new ArrayList();
                        for (StructureInfo structureInfo : structuresForComponent) {
                            List<ControlInfo> list = structureInfo.controls;
                            ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
                            for (ControlInfo controlInfo : list) {
                                CharSequence charSequence = structureInfo.structure;
                                int i = ControlsControllerImpl.$r8$clinit;
                                arrayList2.add(controlsControllerImpl2.createRemovedStatus(componentName4, controlInfo, charSequence, false));
                            }
                            CollectionsKt__MutableCollectionsKt.addAll(arrayList2, arrayList);
                        }
                        ArrayList arrayList3 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList, 10));
                        Iterator it = arrayList.iterator();
                        while (it.hasNext()) {
                            arrayList3.add(((ControlStatus) it.next()).control.getControlId());
                        }
                        consumer4.accept(new ControlsControllerKt$createLoadDataObject$1(arrayList, arrayList3, true));
                    }
                });
            }
        };
        final Consumer consumer4 = new Consumer() { // from class: com.android.systemui.controls.controller.ControlsControllerImpl$loadForComponent$5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                consumer3.accept((ControlsProviderInfo) obj);
            }
        };
        ControlsBindingControllerImpl controlsBindingControllerImpl = (ControlsBindingControllerImpl) this.secBindingController;
        ControlsBindingControllerImpl.LoadSubscriber loadSubscriber = controlsBindingControllerImpl.loadSubscriber;
        if (loadSubscriber != null) {
            new ControlsBindingControllerImpl$LoadSubscriber$loadCancel$1(loadSubscriber);
        }
        final ControlsBindingControllerImpl.LoadSubscriber loadSubscriber2 = controlsBindingControllerImpl.new LoadSubscriber(loadCallback, 100000L);
        controlsBindingControllerImpl.loadSubscriber = loadSubscriber2;
        IControlsProviderInfoSubscriber.Stub stub = new IControlsProviderInfoSubscriber.Stub() { // from class: com.android.systemui.controls.controller.ControlsBindingControllerImpl$bindAndLoad$ps$1
            public final void onNext(IBinder iBinder, ControlsProviderInfo controlsProviderInfo) {
                if (controlsProviderInfo != null) {
                    consumer4.accept(controlsProviderInfo);
                }
            }
        };
        final ControlsProviderLifecycleManager retrieveLifecycleManager = controlsBindingControllerImpl.retrieveLifecycleManager(componentName);
        retrieveLifecycleManager.getClass();
        retrieveLifecycleManager.onLoadCanceller = retrieveLifecycleManager.executor.executeDelayed(new Runnable() { // from class: com.android.systemui.controls.controller.ControlsProviderLifecycleManager$maybeBindAndLoad$2
            @Override // java.lang.Runnable
            public final void run() {
                ControlsProviderLifecycleManager controlsProviderLifecycleManager = ControlsProviderLifecycleManager.this;
                NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0.m("Timeout waiting onLoad for ", controlsProviderLifecycleManager.componentName, controlsProviderLifecycleManager.TAG);
                loadSubscriber2.onError(ControlsProviderLifecycleManager.this.token, "Timeout waiting onLoad");
                ControlsProviderLifecycleManager.this.unbindService();
            }
        }, 20L, TimeUnit.SECONDS);
        retrieveLifecycleManager.invokeOrQueue(new ControlsProviderLifecycleManager.Load(retrieveLifecycleManager, loadSubscriber2, stub));
        consumer2.accept(new ControlsBindingControllerImpl$LoadSubscriber$loadCancel$1(loadSubscriber2));
    }

    public final void replaceFavoritesForComponent(final ComponentInfo componentInfo, final boolean z) {
        if (confirmAvailability()) {
            this.executor.execute(new Runnable() { // from class: com.android.systemui.controls.controller.ControlsControllerImpl$replaceFavoritesForComponent$1
                @Override // java.lang.Runnable
                public final void run() {
                    Favorites favorites = Favorites.INSTANCE;
                    ComponentInfo componentInfo2 = ComponentInfo.this;
                    boolean z2 = z;
                    favorites.getClass();
                    LinkedHashMap linkedHashMap = new LinkedHashMap(Favorites.favMap);
                    linkedHashMap.put(componentInfo2.componentName, componentInfo2.structureInfos);
                    Favorites.favMap = linkedHashMap;
                    if (z2 && ((ArrayList) Favorites.getControlsForComponent(componentInfo2.componentName)).isEmpty()) {
                        Favorites.setActiveFlag(componentInfo2.componentName, false);
                    }
                    int size = Favorites.favMap.size();
                    Map map = Favorites.favMap;
                    StringBuilder m = KeyguardFMMViewController$$ExternalSyntheticOutline0.m("replaceControls isUpdateFlag = ", size, ", favMap.size = ", z2, ", favMap = ");
                    m.append(map);
                    Log.d("Favorites", m.toString());
                    if (!((ArrayList) this.autoAddList).isEmpty()) {
                        Favorites.addFavorites(ComponentInfo.this.componentName, new ArrayList(this.autoAddList));
                    }
                    this.persistenceWrapper.storeFavorites(Favorites.getAllStructures());
                }
            });
        }
    }

    public final void resetFavorites() {
        Favorites.INSTANCE.getClass();
        Favorites.favMap = MapsKt__MapsKt.emptyMap();
        List readFavorites = this.persistenceWrapper.readFavorites();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Object obj : readFavorites) {
            ComponentName componentName = ((StructureInfo) obj).componentName;
            Object obj2 = linkedHashMap.get(componentName);
            if (obj2 == null) {
                obj2 = new ArrayList();
                linkedHashMap.put(componentName, obj2);
            }
            ((List) obj2).add(obj);
        }
        Favorites.favMap = linkedHashMap;
        Favorites.INSTANCE.getClass();
        List allStructures = Favorites.getAllStructures();
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(allStructures, 10));
        Iterator it = ((ArrayList) allStructures).iterator();
        while (it.hasNext()) {
            arrayList.add(((StructureInfo) it.next()).componentName.getPackageName());
        }
        ((AuthorizedPanelsRepositoryImpl) this.authorizedPanelsRepository).addAuthorizedPanels(CollectionsKt___CollectionsKt.toSet(arrayList));
    }

    public final void seedFavoritesForComponents(final List list, final Consumer consumer) {
        if (this.seedingInProgress || list.isEmpty()) {
            return;
        }
        if (confirmAvailability()) {
            this.seedingInProgress = true;
            startSeeding(list, consumer, false);
        } else if (this.userChanging) {
            this.executor.executeDelayed(new Runnable() { // from class: com.android.systemui.controls.controller.ControlsControllerImpl$seedFavoritesForComponents$1
                @Override // java.lang.Runnable
                public final void run() {
                    ControlsControllerImpl.this.seedFavoritesForComponents(list, consumer);
                }
            }, 500L, TimeUnit.MILLISECONDS);
        } else {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                consumer.accept(new SeedResponse(((ComponentName) it.next()).getPackageName(), false));
            }
        }
    }

    public final void setActivePanelFlag(ComponentName componentName, boolean z) {
        Favorites.INSTANCE.getClass();
        LinkedHashMap linkedHashMap = new LinkedHashMap(Favorites.favMap);
        List list = (List) linkedHashMap.get(componentName);
        if (list != null) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                ((StructureInfo) it.next()).active = z;
            }
        } else {
            StructureInfo structureInfo = new StructureInfo(componentName, "", EmptyList.INSTANCE, false, 8, null);
            structureInfo.active = z;
            list = Collections.singletonList(structureInfo);
        }
        linkedHashMap.put(componentName, list);
        Favorites.favMap = linkedHashMap;
        Log.d("Favorites", "setActivePanelFlag = " + linkedHashMap.get(componentName) + ", active = " + z);
    }

    public final void startSeeding(List list, final Consumer consumer, final boolean z) {
        if (list.isEmpty()) {
            boolean z2 = !z;
            this.seedingInProgress = false;
            Iterator it = ((ArrayList) this.seedingCallbacks).iterator();
            while (it.hasNext()) {
                ((Consumer) it.next()).accept(Boolean.valueOf(z2));
            }
            ((ArrayList) this.seedingCallbacks).clear();
            return;
        }
        Log.d("ControlsControllerImpl", "startSeeding remainingComponentNames = " + list);
        Log.d("ControlsControllerImpl", Log.getStackTraceString(new Exception("get stacks")));
        final ComponentName componentName = (ComponentName) list.get(0);
        NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0.m("Beginning request to seed favorites for: ", componentName, "ControlsControllerImpl");
        final List drop = CollectionsKt___CollectionsKt.drop(list, 1);
        ControlsBindingController.LoadCallback loadCallback = new ControlsBindingController.LoadCallback() { // from class: com.android.systemui.controls.controller.ControlsControllerImpl$startSeeding$1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                final List list2 = (List) obj;
                final ControlsControllerImpl controlsControllerImpl = ControlsControllerImpl.this;
                DelayableExecutor delayableExecutor = controlsControllerImpl.executor;
                final Consumer consumer2 = consumer;
                final ComponentName componentName2 = componentName;
                final List list3 = drop;
                final boolean z3 = z;
                delayableExecutor.execute(new Runnable() { // from class: com.android.systemui.controls.controller.ControlsControllerImpl$startSeeding$1$accept$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        ArrayMap arrayMap = new ArrayMap();
                        for (Control control : list2) {
                            CharSequence structure = control.getStructure();
                            if (structure == null) {
                                structure = "";
                            }
                            List list4 = (List) arrayMap.get(structure);
                            if (list4 == null) {
                                list4 = new ArrayList();
                            }
                            if (list4.size() < 6) {
                                list4.add(new ControlInfo(control.getControlId(), control.getTitle(), control.getSubtitle(), control.getDeviceType(), 0, 16, null));
                                arrayMap.put(structure, list4);
                            }
                        }
                        ComponentName componentName3 = componentName2;
                        for (Map.Entry entry : arrayMap.entrySet()) {
                            CharSequence charSequence = (CharSequence) entry.getKey();
                            List list5 = (List) entry.getValue();
                            Favorites favorites = Favorites.INSTANCE;
                            Intrinsics.checkNotNull(charSequence);
                            Intrinsics.checkNotNull(list5);
                            StructureInfo structureInfo = new StructureInfo(componentName3, charSequence, list5, false, 8, null);
                            favorites.getClass();
                            Favorites.replaceControls(structureInfo);
                        }
                        ControlsFavoritePersistenceWrapper controlsFavoritePersistenceWrapper = controlsControllerImpl.persistenceWrapper;
                        Favorites.INSTANCE.getClass();
                        controlsFavoritePersistenceWrapper.storeFavorites(Favorites.getAllStructures());
                        consumer2.accept(new SeedResponse(componentName2.getPackageName(), true));
                        controlsControllerImpl.startSeeding(list3, consumer2, z3);
                    }
                });
            }

            @Override // com.android.systemui.controls.controller.ControlsBindingController.LoadCallback
            public final void error(String str) {
                Log.e("ControlsControllerImpl", "Unable to seed favorites: ".concat(str));
                final ControlsControllerImpl controlsControllerImpl = ControlsControllerImpl.this;
                DelayableExecutor delayableExecutor = controlsControllerImpl.executor;
                final Consumer consumer2 = consumer;
                final ComponentName componentName2 = componentName;
                final List list2 = drop;
                delayableExecutor.execute(new Runnable() { // from class: com.android.systemui.controls.controller.ControlsControllerImpl$startSeeding$1$error$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        consumer2.accept(new SeedResponse(componentName2.getPackageName(), false));
                        ControlsControllerImpl controlsControllerImpl2 = controlsControllerImpl;
                        List list3 = list2;
                        Consumer consumer3 = consumer2;
                        int i = ControlsControllerImpl.$r8$clinit;
                        controlsControllerImpl2.startSeeding(list3, consumer3, true);
                    }
                });
            }
        };
        ControlsBindingControllerImpl controlsBindingControllerImpl = (ControlsBindingControllerImpl) this.bindingController;
        ControlsBindingControllerImpl.LoadSubscriber loadSubscriber = controlsBindingControllerImpl.loadSubscriber;
        if (loadSubscriber != null) {
            new ControlsBindingControllerImpl$LoadSubscriber$loadCancel$1(loadSubscriber);
        }
        final ControlsBindingControllerImpl.LoadSubscriber loadSubscriber2 = controlsBindingControllerImpl.new LoadSubscriber(loadCallback, 36L);
        controlsBindingControllerImpl.loadSubscriber = loadSubscriber2;
        final ControlsProviderLifecycleManager retrieveLifecycleManager = controlsBindingControllerImpl.retrieveLifecycleManager(componentName);
        retrieveLifecycleManager.getClass();
        retrieveLifecycleManager.onLoadCanceller = retrieveLifecycleManager.executor.executeDelayed(new Runnable() { // from class: com.android.systemui.controls.controller.ControlsProviderLifecycleManager$maybeBindAndLoadSuggested$1
            @Override // java.lang.Runnable
            public final void run() {
                ControlsProviderLifecycleManager controlsProviderLifecycleManager = ControlsProviderLifecycleManager.this;
                NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0.m("Timeout waiting onLoadSuggested for ", controlsProviderLifecycleManager.componentName, controlsProviderLifecycleManager.TAG);
                loadSubscriber2.onError(ControlsProviderLifecycleManager.this.token, "Timeout waiting onLoadSuggested");
                ControlsProviderLifecycleManager.this.unbindService();
            }
        }, 20L, TimeUnit.SECONDS);
        retrieveLifecycleManager.invokeOrQueue(retrieveLifecycleManager.new Suggest(loadSubscriber2));
    }

    public final void subscribeToFavorites(ComponentInfo componentInfo) {
        if (confirmAvailability()) {
            this.isAutoRemove = false;
            ComponentName componentName = componentInfo.componentName;
            SecControlsUiControllerImpl secControlsUiControllerImpl = (SecControlsUiControllerImpl) this.secUiController;
            secControlsUiControllerImpl.getClass();
            secControlsUiControllerImpl.uiExecutor.execute(new SecControlsUiControllerImpl$updateLaunchingAppButton$1(secControlsUiControllerImpl, null));
            String packageName = componentName.getPackageName();
            this.controlsUtil.getClass();
            boolean equals = "com.samsung.android.oneconnect".equals(packageName);
            SecControlsBindingController secControlsBindingController = this.secBindingController;
            if (equals) {
                final Consumer consumer = new Consumer() { // from class: com.android.systemui.controls.controller.ControlsControllerImpl$loadControlsProviderInfo$1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ControlsProviderInfo controlsProviderInfo = (ControlsProviderInfo) obj;
                        controlsProviderInfo.getAutoRemove();
                        ControlsControllerImpl.this.isAutoRemove = controlsProviderInfo.getAutoRemove();
                        Objects.toString(controlsProviderInfo.getAppIntent().getIntent());
                        SecControlsUiController secControlsUiController = ControlsControllerImpl.this.secUiController;
                        PendingIntent appIntent = controlsProviderInfo.getAppIntent();
                        SecControlsUiControllerImpl secControlsUiControllerImpl2 = (SecControlsUiControllerImpl) secControlsUiController;
                        secControlsUiControllerImpl2.getClass();
                        secControlsUiControllerImpl2.uiExecutor.execute(new SecControlsUiControllerImpl$updateLaunchingAppButton$1(secControlsUiControllerImpl2, appIntent));
                    }
                };
                ControlsProviderLifecycleManager retrieveLifecycleManager = ((ControlsBindingControllerImpl) secControlsBindingController).retrieveLifecycleManager(componentName);
                IControlsProviderInfoSubscriber.Stub stub = new IControlsProviderInfoSubscriber.Stub() { // from class: com.android.systemui.controls.controller.ControlsBindingControllerImpl$loadControlsProviderInfo$ps$1
                    public final void onNext(IBinder iBinder, ControlsProviderInfo controlsProviderInfo) {
                        if (controlsProviderInfo != null) {
                            consumer.accept(controlsProviderInfo);
                        }
                    }
                };
                retrieveLifecycleManager.getClass();
                retrieveLifecycleManager.invokeOrQueue(retrieveLifecycleManager.new LoadProviderInfo(stub));
            }
            ControlsBindingControllerImpl controlsBindingControllerImpl = (ControlsBindingControllerImpl) secControlsBindingController;
            controlsBindingControllerImpl.unsubscribe();
            ControlsProviderLifecycleManager retrieveLifecycleManager2 = controlsBindingControllerImpl.retrieveLifecycleManager(componentInfo.componentName);
            StatefulControlSubscriber statefulControlSubscriber = new StatefulControlSubscriber((ControlsController) controlsBindingControllerImpl.lazyController.get(), retrieveLifecycleManager2, controlsBindingControllerImpl.backgroundExecutor, 100000L);
            controlsBindingControllerImpl.statefulControlSubscriber = statefulControlSubscriber;
            List list = componentInfo.structureInfos;
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
            Iterator it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(((StructureInfo) it.next()).controls);
            }
            List flatten = CollectionsKt__IterablesKt.flatten(arrayList);
            ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(flatten, 10));
            Iterator it2 = ((ArrayList) flatten).iterator();
            while (it2.hasNext()) {
                arrayList2.add(((ControlInfo) it2.next()).controlId);
            }
            retrieveLifecycleManager2.getClass();
            retrieveLifecycleManager2.invokeOrQueue(retrieveLifecycleManager2.new Subscribe(arrayList2, statefulControlSubscriber));
        }
    }

    public static /* synthetic */ void getAuxiliaryPersistenceWrapper$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations() {
    }

    public static /* synthetic */ void getRestoreFinishedReceiver$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations() {
    }

    public static /* synthetic */ void getSettingObserver$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations() {
    }

    public static /* synthetic */ void isAutoRemove$annotations() {
    }
}
