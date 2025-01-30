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
import androidx.core.app.AbstractC0147x487e7be7;
import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardFMMViewController$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.Dumpable;
import com.android.systemui.controls.ControlStatus;
import com.android.systemui.controls.ControlsServiceInfo;
import com.android.systemui.controls.controller.ControlsBindingController;
import com.android.systemui.controls.controller.ControlsBindingControllerImpl;
import com.android.systemui.controls.controller.ControlsBindingControllerImpl.LoadSubscriber;
import com.android.systemui.controls.controller.ControlsProviderLifecycleManager;
import com.android.systemui.controls.controller.ControlsProviderLifecycleManager.Load;
import com.android.systemui.controls.controller.ControlsProviderLifecycleManager.LoadProviderInfo;
import com.android.systemui.controls.controller.ControlsProviderLifecycleManager.Subscribe;
import com.android.systemui.controls.controller.ControlsProviderLifecycleManager.Suggest;
import com.android.systemui.controls.controller.util.BadgeProvider;
import com.android.systemui.controls.controller.util.BadgeProviderImpl;
import com.android.systemui.controls.controller.util.BadgeProviderImpl$invalidate$1;
import com.android.systemui.controls.management.ControlsListingController;
import com.android.systemui.controls.management.ControlsListingControllerImpl;
import com.android.systemui.controls.p005ui.ControlsUiController;
import com.android.systemui.controls.p005ui.CustomControlsUiController;
import com.android.systemui.controls.p005ui.CustomControlsUiControllerImpl;
import com.android.systemui.controls.p005ui.CustomControlsUiControllerImpl$updateLaunchingAppButton$1;
import com.android.systemui.controls.p005ui.util.ControlsUtil;
import com.android.systemui.controls.panels.AuthorizedPanelsRepository;
import com.android.systemui.controls.panels.AuthorizedPanelsRepositoryImpl;
import com.android.systemui.controls.panels.CustomSelectedComponentRepository;
import com.android.systemui.controls.panels.SelectedComponentRepository;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.settings.UserFileManager;
import com.android.systemui.settings.UserFileManagerImpl;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.ExecutorImpl;
import com.android.systemui.util.settings.SecureSettings;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
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
import kotlin.collections.MapsKt__MapsKt;
import kotlin.collections.SetsKt___SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ControlsControllerImpl implements Dumpable, ControlsController, CustomControlsController {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AuthorizedPanelsRepository authorizedPanelsRepository;
    public final AuxiliaryPersistenceWrapper auxiliaryPersistenceWrapper;
    public final BadgeProvider badgeProvider;
    public final ControlsBindingController bindingController;
    public final Context context;
    public final ControlsUtil controlsUtil;
    public UserHandle currentUser;
    public final CustomControlsBindingController customBindingController;
    public final CustomSelectedComponentRepository customSelectedComponentRepository;
    public final CustomControlsUiController customUiController;
    public final DelayableExecutor executor;
    public boolean isAutoRemove;
    public final ControlsListingController listingController;
    public final ControlsFavoritePersistenceWrapper persistenceWrapper;
    public final ControlsControllerImpl$restoreFinishedReceiver$1 restoreFinishedReceiver;
    public final SecureSettings secureSettings;
    public boolean seedingInProgress;
    public final SelectedComponentRepository selectedComponentRepository;
    public final ControlsUiController uiController;
    public boolean userChanging;
    public final UserFileManager userFileManager;
    public UserStructure userStructure;
    public final UserTracker userTracker;
    public final List seedingCallbacks = new ArrayList();
    public final List autoAddList = new ArrayList();

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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v1, types: [android.content.BroadcastReceiver, com.android.systemui.controls.controller.ControlsControllerImpl$restoreFinishedReceiver$1] */
    public ControlsControllerImpl(Context context, DelayableExecutor delayableExecutor, ControlsUiController controlsUiController, CustomControlsUiController customControlsUiController, SelectedComponentRepository selectedComponentRepository, CustomSelectedComponentRepository customSelectedComponentRepository, ControlsBindingController controlsBindingController, CustomControlsBindingController customControlsBindingController, ControlsListingController controlsListingController, UserFileManager userFileManager, UserTracker userTracker, AuthorizedPanelsRepository authorizedPanelsRepository, Optional<ControlsFavoritePersistenceWrapper> optional, DumpManager dumpManager, BadgeProvider badgeProvider, SecureSettings secureSettings, ControlsUtil controlsUtil) {
        this.context = context;
        this.executor = delayableExecutor;
        this.uiController = controlsUiController;
        this.customUiController = customControlsUiController;
        this.selectedComponentRepository = selectedComponentRepository;
        this.customSelectedComponentRepository = customSelectedComponentRepository;
        this.bindingController = controlsBindingController;
        this.customBindingController = customControlsBindingController;
        this.listingController = controlsListingController;
        this.userFileManager = userFileManager;
        this.userTracker = userTracker;
        this.authorizedPanelsRepository = authorizedPanelsRepository;
        this.badgeProvider = badgeProvider;
        this.secureSettings = secureSettings;
        this.controlsUtil = controlsUtil;
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
        ?? r2 = new BroadcastReceiver() { // from class: com.android.systemui.controls.controller.ControlsControllerImpl$restoreFinishedReceiver$1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                if (intent.getIntExtra("android.intent.extra.USER_ID", -10000) == ControlsControllerImpl.this.getCurrentUserId()) {
                    final ControlsControllerImpl controlsControllerImpl = ControlsControllerImpl.this;
                    ((ExecutorImpl) controlsControllerImpl.executor).execute(new Runnable() { // from class: com.android.systemui.controls.controller.ControlsControllerImpl$restoreFinishedReceiver$1$onReceive$1
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
        this.restoreFinishedReceiver = r2;
        new ContentObserver() { // from class: com.android.systemui.controls.controller.ControlsControllerImpl$settingObserver$1
            {
                super(null);
            }

            public final void onChange(boolean z, Collection collection, int i, int i2) {
                ControlsControllerImpl controlsControllerImpl = ControlsControllerImpl.this;
                if (controlsControllerImpl.userChanging || i2 != controlsControllerImpl.getCurrentUserId()) {
                    return;
                }
                ControlsControllerImpl.this.resetFavorites();
            }
        };
        ControlsListingController.ControlsListingCallback controlsListingCallback = new ControlsListingController.ControlsListingCallback() { // from class: com.android.systemui.controls.controller.ControlsControllerImpl$listingCallback$1
            @Override // com.android.systemui.controls.management.ControlsListingController.ControlsListingCallback
            public final void onServicesUpdated(final List list) {
                final ControlsControllerImpl controlsControllerImpl = ControlsControllerImpl.this;
                ((ExecutorImpl) controlsControllerImpl.executor).execute(new Runnable() { // from class: com.android.systemui.controls.controller.ControlsControllerImpl$listingCallback$1$onServicesUpdated$1
                    @Override // java.lang.Runnable
                    public final void run() {
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
                        if (BasicRune.CONTROLS_BADGE) {
                            BadgeProviderImpl badgeProviderImpl = (BadgeProviderImpl) controlsControllerImpl.badgeProvider;
                            Set set3 = badgeProviderImpl.badgeRequiredSet;
                            boolean isEmpty = set3.isEmpty();
                            BadgeProviderImpl.Companion companion = BadgeProviderImpl.Companion;
                            Set set4 = badgeProviderImpl.badgeNotRequiredSet;
                            if (isEmpty && set4.isEmpty() && (!set2.isEmpty())) {
                                companion.getClass();
                                set4.addAll(BadgeProviderImpl.Companion.toPackagesSet(set2));
                            }
                            companion.getClass();
                            Set packagesSet = BadgeProviderImpl.Companion.toPackagesSet(set);
                            Set subtract = CollectionsKt___CollectionsKt.subtract(set3, packagesSet);
                            if (subtract.isEmpty()) {
                                subtract = null;
                            }
                            if (subtract != null) {
                                set3.removeAll(subtract);
                            }
                            Set subtract2 = CollectionsKt___CollectionsKt.subtract(set4, packagesSet);
                            if (subtract2.isEmpty()) {
                                subtract2 = null;
                            }
                            if (subtract2 != null) {
                                set4.removeAll(subtract2);
                            }
                            Set subtract3 = CollectionsKt___CollectionsKt.subtract(CollectionsKt___CollectionsKt.subtract(BadgeProviderImpl.Companion.toPackagesSet(set), set3), set4);
                            Set set5 = subtract3.isEmpty() ? null : subtract3;
                            if (set5 != null) {
                                set3.addAll(set5);
                            }
                            ((ExecutorImpl) badgeProviderImpl.uiExecutor).execute(new BadgeProviderImpl$invalidate$1(badgeProviderImpl));
                            BadgeProviderImpl.onServicesUpdated$flush(badgeProviderImpl, set3, "ControlsBadgeRequired", "badgeRequiredSet");
                            BadgeProviderImpl.onServicesUpdated$flush(badgeProviderImpl, set4, "ControlsBadgeNotRequired", "badgeNotRequiredSet");
                        }
                        ControlsControllerImpl controlsControllerImpl2 = controlsControllerImpl;
                        SharedPreferences sharedPreferences = ((UserFileManagerImpl) controlsControllerImpl2.userFileManager).getSharedPreferences(((UserTrackerImpl) controlsControllerImpl2.userTracker).getUserId(), "controls_prefs");
                        Set<String> stringSet = sharedPreferences.getStringSet("SeedingCompleted", new LinkedHashSet());
                        ArrayList arrayList3 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(set, 10));
                        Iterator it3 = set.iterator();
                        while (it3.hasNext()) {
                            arrayList3.add(((ComponentName) it3.next()).getPackageName());
                        }
                        SharedPreferences.Editor edit = sharedPreferences.edit();
                        Set<String> mutableSet = CollectionsKt___CollectionsKt.toMutableSet(stringSet);
                        mutableSet.retainAll(arrayList3);
                        edit.putStringSet("SeedingCompleted", mutableSet).apply();
                        Set<ComponentName> subtract4 = CollectionsKt___CollectionsKt.subtract(set2, set);
                        ControlsControllerImpl controlsControllerImpl3 = controlsControllerImpl;
                        boolean z = false;
                        for (final ComponentName componentName : subtract4) {
                            Favorites.INSTANCE.getClass();
                            Favorites.removeStructures(componentName, false);
                            final ControlsBindingControllerImpl controlsBindingControllerImpl = (ControlsBindingControllerImpl) controlsControllerImpl3.bindingController;
                            controlsBindingControllerImpl.getClass();
                            ((ExecutorImpl) controlsBindingControllerImpl.backgroundExecutor).execute(new Runnable() { // from class: com.android.systemui.controls.controller.ControlsBindingControllerImpl$onComponentRemoved$1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    ControlsBindingControllerImpl controlsBindingControllerImpl2 = ControlsBindingControllerImpl.this;
                                    ControlsProviderLifecycleManager controlsProviderLifecycleManager = controlsBindingControllerImpl2.currentProvider;
                                    if (controlsProviderLifecycleManager != null) {
                                        if (Intrinsics.areEqual(controlsProviderLifecycleManager.componentName, componentName)) {
                                            controlsBindingControllerImpl2.unbind();
                                        }
                                    }
                                }
                            });
                            z = true;
                        }
                        if (!controlsControllerImpl.auxiliaryPersistenceWrapper.favorites.isEmpty()) {
                            Set subtract5 = CollectionsKt___CollectionsKt.subtract(set, set2);
                            ControlsControllerImpl controlsControllerImpl4 = controlsControllerImpl;
                            Iterator it4 = subtract5.iterator();
                            while (it4.hasNext()) {
                                List<StructureInfo> cachedFavoritesAndRemoveFor = controlsControllerImpl4.auxiliaryPersistenceWrapper.getCachedFavoritesAndRemoveFor((ComponentName) it4.next());
                                if (!cachedFavoritesAndRemoveFor.isEmpty()) {
                                    for (StructureInfo structureInfo : cachedFavoritesAndRemoveFor) {
                                        Favorites.INSTANCE.getClass();
                                        Favorites.replaceControls(structureInfo);
                                    }
                                    z = true;
                                }
                            }
                            Set mutableSet2 = CollectionsKt___CollectionsKt.toMutableSet(set);
                            mutableSet2.retainAll(set2);
                            ControlsControllerImpl controlsControllerImpl5 = controlsControllerImpl;
                            Iterator it5 = mutableSet2.iterator();
                            while (it5.hasNext()) {
                                controlsControllerImpl5.auxiliaryPersistenceWrapper.getCachedFavoritesAndRemoveFor((ComponentName) it5.next());
                            }
                        }
                        if (z) {
                            Log.d("ControlsControllerImpl", "Detected change in available services, storing updated favorites");
                            ControlsFavoritePersistenceWrapper controlsFavoritePersistenceWrapper = controlsControllerImpl.persistenceWrapper;
                            Favorites.INSTANCE.getClass();
                            controlsFavoritePersistenceWrapper.storeFavorites(Favorites.getAllStructures());
                        }
                    }
                });
            }
        };
        DumpManager.registerDumpable$default(dumpManager, ControlsControllerImpl.class.getName(), this);
        resetFavorites();
        this.userChanging = false;
        context.registerReceiver(r2, new IntentFilter("com.android.systemui.backup.RESTORE_FINISHED"), "com.android.systemui.permission.SELF", null, 4);
        ((ControlsListingControllerImpl) controlsListingController).addCallback(controlsListingCallback);
    }

    public static final Set access$findRemoved(ControlsControllerImpl controlsControllerImpl, Set set, List list) {
        controlsControllerImpl.getClass();
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((Control) it.next()).getControlId());
        }
        return SetsKt___SetsKt.minus(set, arrayList);
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
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m53m("  Changing users: ", this.userChanging, printWriter);
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
        List favorites = getFavorites();
        ArrayList arrayList = new ArrayList();
        for (Object obj : favorites) {
            if (((StructureInfo) obj).customStructureInfo.active) {
                arrayList.add(obj);
            }
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Object obj2 : arrayList) {
            ComponentName componentName = ((StructureInfo) obj2).componentName;
            Object obj3 = linkedHashMap.get(componentName);
            if (obj3 == null) {
                obj3 = new ArrayList();
                linkedHashMap.put(componentName, obj3);
            }
            ((List) obj3).add(obj2);
        }
        if (BasicRune.CONTROLS_AUTO_ADD) {
            ((ArrayList) this.autoAddList).clear();
        }
        Log.d("ControlsControllerImpl", "getActiveFavoritesComponent getFavorites = " + getFavorites());
        Log.d("ControlsControllerImpl", "getActiveFavoritesComponent activeFavoriteStructureInfos = " + arrayList + ", favoriteComponentInfos = " + linkedHashMap);
        ArrayList arrayList2 = new ArrayList(linkedHashMap.size());
        for (Map.Entry entry : linkedHashMap.entrySet()) {
            arrayList2.add(new ComponentInfo((ComponentName) entry.getKey(), (List) entry.getValue()));
        }
        return arrayList2;
    }

    public final boolean getActiveFlag(ComponentName componentName) {
        Favorites.INSTANCE.getClass();
        List list = (List) Favorites.favMap.get(componentName);
        if (list != null && !list.isEmpty()) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                if (((StructureInfo) it.next()).customStructureInfo.active) {
                    return true;
                }
            }
        }
        return false;
    }

    public final int getCurrentUserId() {
        return this.currentUser.getIdentifier();
    }

    public final List getFavorites() {
        Favorites.INSTANCE.getClass();
        return Favorites.getAllStructures();
    }

    public final void loadForComponent(final ComponentName componentName, final Consumer consumer, final Consumer consumer2) {
        if (!confirmAvailability()) {
            if (this.userChanging) {
                ((ExecutorImpl) this.executor).executeDelayed(new Runnable() { // from class: com.android.systemui.controls.controller.ControlsControllerImpl$loadForComponent$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        ControlsControllerImpl.this.loadForComponent(componentName, consumer, consumer2);
                    }
                }, 500L, TimeUnit.MILLISECONDS);
            }
            EmptyList emptyList = EmptyList.INSTANCE;
            consumer.accept(new ControlsControllerKt$createLoadDataObject$1(emptyList, emptyList, true));
        }
        ControlsBindingController.LoadCallback loadCallback = new ControlsBindingController.LoadCallback() { // from class: com.android.systemui.controls.controller.ControlsControllerImpl$loadForComponent$2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                final List list = (List) obj;
                final ControlsControllerImpl controlsControllerImpl = ControlsControllerImpl.this;
                DelayableExecutor delayableExecutor = controlsControllerImpl.executor;
                final ComponentName componentName2 = componentName;
                final Consumer consumer3 = consumer;
                ((ExecutorImpl) delayableExecutor).execute(new Runnable() { // from class: com.android.systemui.controls.controller.ControlsControllerImpl$loadForComponent$2$accept$1
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
                        Set access$findRemoved = ControlsControllerImpl.access$findRemoved(controlsControllerImpl, CollectionsKt___CollectionsKt.toSet(arrayList), list);
                        List<Control> list3 = list;
                        ComponentName componentName5 = componentName2;
                        ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list3, 10));
                        for (Control control : list3) {
                            arrayList2.add(new ControlStatus(control, componentName5, arrayList.contains(control.getControlId()), false, 8, null));
                        }
                        ArrayList arrayList3 = new ArrayList();
                        Favorites favorites3 = Favorites.INSTANCE;
                        ComponentName componentName6 = componentName2;
                        favorites3.getClass();
                        List<StructureInfo> structuresForComponent = Favorites.getStructuresForComponent(componentName6);
                        ControlsControllerImpl controlsControllerImpl2 = controlsControllerImpl;
                        ComponentName componentName7 = componentName2;
                        for (StructureInfo structureInfo : structuresForComponent) {
                            for (ControlInfo controlInfo : structureInfo.controls) {
                                if (access$findRemoved.contains(controlInfo.controlId)) {
                                    arrayList3.add(controlsControllerImpl2.createRemovedStatus(componentName7, controlInfo, structureInfo.structure, true));
                                }
                            }
                        }
                        consumer3.accept(new ControlsControllerKt$createLoadDataObject$1(CollectionsKt___CollectionsKt.plus((Iterable) arrayList2, (Collection) arrayList3), arrayList, false));
                    }
                });
            }

            @Override // com.android.systemui.controls.controller.ControlsBindingController.LoadCallback
            public final void error(String str) {
                final ControlsControllerImpl controlsControllerImpl = ControlsControllerImpl.this;
                DelayableExecutor delayableExecutor = controlsControllerImpl.executor;
                final ComponentName componentName2 = componentName;
                final Consumer consumer3 = consumer;
                ((ExecutorImpl) delayableExecutor).execute(new Runnable() { // from class: com.android.systemui.controls.controller.ControlsControllerImpl$loadForComponent$2$error$1
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
                                int i = ControlsControllerImpl.$r8$clinit;
                                arrayList2.add(controlsControllerImpl2.createRemovedStatus(componentName4, controlInfo, structureInfo.structure, false));
                            }
                            CollectionsKt__MutableCollectionsKt.addAll(arrayList2, arrayList);
                        }
                        ArrayList arrayList3 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList, 10));
                        Iterator it = arrayList.iterator();
                        while (it.hasNext()) {
                            arrayList3.add(((ControlStatus) it.next()).control.getControlId());
                        }
                        consumer3.accept(new ControlsControllerKt$createLoadDataObject$1(arrayList, arrayList3, true));
                    }
                });
            }
        };
        ControlsBindingControllerImpl controlsBindingControllerImpl = (ControlsBindingControllerImpl) this.bindingController;
        ControlsBindingControllerImpl.LoadSubscriber loadSubscriber = controlsBindingControllerImpl.loadSubscriber;
        if (loadSubscriber != null) {
            new ControlsBindingControllerImpl$LoadSubscriber$loadCancel$1(loadSubscriber);
        }
        final ControlsBindingControllerImpl.LoadSubscriber loadSubscriber2 = controlsBindingControllerImpl.new LoadSubscriber(loadCallback, 100000L);
        controlsBindingControllerImpl.loadSubscriber = loadSubscriber2;
        final ControlsProviderLifecycleManager retrieveLifecycleManager = controlsBindingControllerImpl.retrieveLifecycleManager(componentName);
        retrieveLifecycleManager.onLoadCanceller = ((ExecutorImpl) retrieveLifecycleManager.executor).executeDelayed(new Runnable() { // from class: com.android.systemui.controls.controller.ControlsProviderLifecycleManager$maybeBindAndLoad$1
            @Override // java.lang.Runnable
            public final void run() {
                ControlsProviderLifecycleManager controlsProviderLifecycleManager = ControlsProviderLifecycleManager.this;
                AbstractC0147x487e7be7.m27m("Timeout waiting onLoad for ", controlsProviderLifecycleManager.componentName, controlsProviderLifecycleManager.TAG);
                loadSubscriber2.onError(ControlsProviderLifecycleManager.this.token, "Timeout waiting onLoad");
                ControlsProviderLifecycleManager.this.unbindService();
            }
        }, 20L, TimeUnit.SECONDS);
        retrieveLifecycleManager.invokeOrQueue(retrieveLifecycleManager.new Load(loadSubscriber2));
        consumer2.accept(new ControlsBindingControllerImpl$LoadSubscriber$loadCancel$1(loadSubscriber2));
    }

    public final void replaceFavoritesForComponent(final ComponentInfo componentInfo, final boolean z) {
        if (confirmAvailability()) {
            ((ExecutorImpl) this.executor).execute(new Runnable() { // from class: com.android.systemui.controls.controller.ControlsControllerImpl$replaceFavoritesForComponent$1
                @Override // java.lang.Runnable
                public final void run() {
                    Favorites favorites = Favorites.INSTANCE;
                    ComponentInfo componentInfo2 = ComponentInfo.this;
                    boolean z2 = z;
                    favorites.getClass();
                    LinkedHashMap linkedHashMap = new LinkedHashMap(Favorites.favMap);
                    ComponentName componentName = componentInfo2.componentName;
                    linkedHashMap.put(componentName, componentInfo2.structureInfos);
                    Favorites.favMap = linkedHashMap;
                    if (z2 && ((ArrayList) Favorites.getControlsForComponent(componentName)).isEmpty()) {
                        Favorites.setActiveFlag(componentName, false);
                    }
                    int size = Favorites.favMap.size();
                    Map map = Favorites.favMap;
                    StringBuilder m66m = KeyguardFMMViewController$$ExternalSyntheticOutline0.m66m("replaceControls isUpdateFlag = ", z2, ", favMap.size = ", size, ", favMap = ");
                    m66m.append(map);
                    Log.d("Favorites", m66m.toString());
                    if (BasicRune.CONTROLS_AUTO_ADD && (!((ArrayList) this.autoAddList).isEmpty())) {
                        Favorites.addFavorites(ComponentInfo.this.componentName, new ArrayList(this.autoAddList));
                    }
                    this.persistenceWrapper.storeFavorites(Favorites.getAllStructures());
                }
            });
        }
    }

    public final void resetFavorites() {
        Favorites.INSTANCE.getClass();
        Log.d("Favorites", "clear");
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
        Log.d("Favorites", "load favMap.size = " + linkedHashMap.size() + ", favMap = " + Favorites.favMap);
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
            ((ExecutorImpl) this.executor).executeDelayed(new Runnable() { // from class: com.android.systemui.controls.controller.ControlsControllerImpl$seedFavoritesForComponents$1
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

    public final void startSeeding(List list, final Consumer consumer, final boolean z) {
        if (list.isEmpty()) {
            boolean z2 = !z;
            this.seedingInProgress = false;
            ArrayList arrayList = (ArrayList) this.seedingCallbacks;
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                ((Consumer) it.next()).accept(Boolean.valueOf(z2));
            }
            arrayList.clear();
            return;
        }
        final ComponentName componentName = (ComponentName) list.get(0);
        Log.d("ControlsControllerImpl", "Beginning request to seed favorites for: " + componentName);
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
                ((ExecutorImpl) delayableExecutor).execute(new Runnable() { // from class: com.android.systemui.controls.controller.ControlsControllerImpl$startSeeding$1$accept$1
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
                                ControlInfo controlInfo = new ControlInfo(control.getControlId(), control.getTitle(), control.getSubtitle(), control.getDeviceType());
                                if (BasicRune.CONTROLS_LAYOUT_TYPE) {
                                    controlInfo.customControlInfo.layoutType = control.getCustomControl().getLayoutType();
                                }
                                list4.add(controlInfo);
                                arrayMap.put(structure, list4);
                            }
                        }
                        ComponentName componentName3 = componentName2;
                        for (Map.Entry entry : arrayMap.entrySet()) {
                            CharSequence charSequence = (CharSequence) entry.getKey();
                            List list5 = (List) entry.getValue();
                            Favorites favorites = Favorites.INSTANCE;
                            StructureInfo structureInfo = new StructureInfo(componentName3, charSequence, list5);
                            if (BasicRune.CONTROLS_SAMSUNG_STYLE) {
                                structureInfo.customStructureInfo.active = true;
                            }
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
                ((ExecutorImpl) delayableExecutor).execute(new Runnable() { // from class: com.android.systemui.controls.controller.ControlsControllerImpl$startSeeding$1$error$1
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
        retrieveLifecycleManager.onLoadCanceller = ((ExecutorImpl) retrieveLifecycleManager.executor).executeDelayed(new Runnable() { // from class: com.android.systemui.controls.controller.ControlsProviderLifecycleManager$maybeBindAndLoadSuggested$1
            @Override // java.lang.Runnable
            public final void run() {
                ControlsProviderLifecycleManager controlsProviderLifecycleManager = ControlsProviderLifecycleManager.this;
                AbstractC0147x487e7be7.m27m("Timeout waiting onLoadSuggested for ", controlsProviderLifecycleManager.componentName, controlsProviderLifecycleManager.TAG);
                loadSubscriber2.onError(ControlsProviderLifecycleManager.this.token, "Timeout waiting onLoadSuggested");
                ControlsProviderLifecycleManager.this.unbindService();
            }
        }, 20L, TimeUnit.SECONDS);
        retrieveLifecycleManager.invokeOrQueue(retrieveLifecycleManager.new Suggest(loadSubscriber2));
    }

    public final void subscribeToFavorites(ComponentInfo componentInfo) {
        if (confirmAvailability()) {
            if (BasicRune.CONTROLS_AUTO_REMOVE) {
                this.isAutoRemove = false;
            }
            boolean z = BasicRune.CONTROLS_PROVIDER_INFO;
            ComponentName componentName = componentInfo.componentName;
            CustomControlsBindingController customControlsBindingController = this.customBindingController;
            if (z) {
                CustomControlsUiControllerImpl customControlsUiControllerImpl = (CustomControlsUiControllerImpl) this.customUiController;
                customControlsUiControllerImpl.getClass();
                ((ExecutorImpl) customControlsUiControllerImpl.uiExecutor).execute(new CustomControlsUiControllerImpl$updateLaunchingAppButton$1(customControlsUiControllerImpl, null));
                String packageName = componentName.getPackageName();
                this.controlsUtil.getClass();
                if (Intrinsics.areEqual("com.samsung.android.oneconnect", packageName)) {
                    final Consumer consumer = new Consumer() { // from class: com.android.systemui.controls.controller.ControlsControllerImpl$loadControlsProviderInfo$1
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            ControlsProviderInfo controlsProviderInfo = (ControlsProviderInfo) obj;
                            if (BasicRune.CONTROLS_AUTO_REMOVE) {
                                controlsProviderInfo.getAutoRemove();
                                ControlsControllerImpl.this.isAutoRemove = controlsProviderInfo.getAutoRemove();
                            }
                            if (BasicRune.CONTROLS_PROVIDER_INFO) {
                                Objects.toString(controlsProviderInfo.getAppIntent().getIntent());
                                CustomControlsUiController customControlsUiController = ControlsControllerImpl.this.customUiController;
                                PendingIntent appIntent = controlsProviderInfo.getAppIntent();
                                CustomControlsUiControllerImpl customControlsUiControllerImpl2 = (CustomControlsUiControllerImpl) customControlsUiController;
                                customControlsUiControllerImpl2.getClass();
                                ((ExecutorImpl) customControlsUiControllerImpl2.uiExecutor).execute(new CustomControlsUiControllerImpl$updateLaunchingAppButton$1(customControlsUiControllerImpl2, appIntent));
                            }
                        }
                    };
                    ControlsProviderLifecycleManager retrieveLifecycleManager = ((ControlsBindingControllerImpl) customControlsBindingController).retrieveLifecycleManager(componentName);
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
            }
            ControlsBindingControllerImpl controlsBindingControllerImpl = (ControlsBindingControllerImpl) customControlsBindingController;
            controlsBindingControllerImpl.unsubscribe();
            ControlsProviderLifecycleManager retrieveLifecycleManager2 = controlsBindingControllerImpl.retrieveLifecycleManager(componentName);
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

    public final void loadForComponent(final ComponentName componentName, final Consumer consumer, final Consumer consumer2, final Consumer consumer3) {
        if (!confirmAvailability()) {
            if (this.userChanging) {
                ((ExecutorImpl) this.executor).executeDelayed(new Runnable() { // from class: com.android.systemui.controls.controller.ControlsControllerImpl$loadForComponent$3
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
                ((ExecutorImpl) delayableExecutor).execute(new Runnable() { // from class: com.android.systemui.controls.controller.ControlsControllerImpl$loadForComponent$4$accept$1
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
                        Set access$findRemoved = ControlsControllerImpl.access$findRemoved(controlsControllerImpl, CollectionsKt___CollectionsKt.toSet(arrayList), list);
                        List<Control> list3 = list;
                        ComponentName componentName5 = componentName2;
                        ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list3, 10));
                        for (Control control : list3) {
                            arrayList2.add(new ControlStatus(control, componentName5, arrayList.contains(control.getControlId()), false, 8, null));
                        }
                        ArrayList arrayList3 = new ArrayList();
                        Favorites favorites3 = Favorites.INSTANCE;
                        ComponentName componentName6 = componentName2;
                        favorites3.getClass();
                        List<StructureInfo> structuresForComponent = Favorites.getStructuresForComponent(componentName6);
                        ControlsControllerImpl controlsControllerImpl2 = controlsControllerImpl;
                        ComponentName componentName7 = componentName2;
                        for (StructureInfo structureInfo : structuresForComponent) {
                            for (ControlInfo controlInfo : structureInfo.controls) {
                                if (access$findRemoved.contains(controlInfo.controlId)) {
                                    arrayList3.add(controlsControllerImpl2.createRemovedStatus(componentName7, controlInfo, structureInfo.structure, true));
                                }
                            }
                        }
                        consumer4.accept(new ControlsControllerKt$createLoadDataObject$1(CollectionsKt___CollectionsKt.plus((Iterable) arrayList2, (Collection) arrayList3), arrayList, false));
                    }
                });
            }

            @Override // com.android.systemui.controls.controller.ControlsBindingController.LoadCallback
            public final void error(String str) {
                final ControlsControllerImpl controlsControllerImpl = ControlsControllerImpl.this;
                DelayableExecutor delayableExecutor = controlsControllerImpl.executor;
                final ComponentName componentName2 = componentName;
                final Consumer consumer4 = consumer;
                ((ExecutorImpl) delayableExecutor).execute(new Runnable() { // from class: com.android.systemui.controls.controller.ControlsControllerImpl$loadForComponent$4$error$1
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
                                int i = ControlsControllerImpl.$r8$clinit;
                                arrayList2.add(controlsControllerImpl2.createRemovedStatus(componentName4, controlInfo, structureInfo.structure, false));
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
        ControlsBindingControllerImpl controlsBindingControllerImpl = (ControlsBindingControllerImpl) this.customBindingController;
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
        retrieveLifecycleManager.onLoadCanceller = ((ExecutorImpl) retrieveLifecycleManager.executor).executeDelayed(new Runnable() { // from class: com.android.systemui.controls.controller.ControlsProviderLifecycleManager$maybeBindAndLoad$2
            @Override // java.lang.Runnable
            public final void run() {
                ControlsProviderLifecycleManager controlsProviderLifecycleManager = ControlsProviderLifecycleManager.this;
                AbstractC0147x487e7be7.m27m("Timeout waiting onLoad for ", controlsProviderLifecycleManager.componentName, controlsProviderLifecycleManager.TAG);
                loadSubscriber2.onError(ControlsProviderLifecycleManager.this.token, "Timeout waiting onLoad");
                ControlsProviderLifecycleManager.this.unbindService();
            }
        }, 20L, TimeUnit.SECONDS);
        retrieveLifecycleManager.invokeOrQueue(new ControlsProviderLifecycleManager.Load(retrieveLifecycleManager, loadSubscriber2, stub));
        consumer2.accept(new ControlsBindingControllerImpl$LoadSubscriber$loadCancel$1(loadSubscriber2));
    }

    /* renamed from: getAuxiliaryPersistenceWrapper$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations */
    public static /* synthetic */ void m112x61774fea() {
    }

    /* renamed from: getRestoreFinishedReceiver$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations */
    public static /* synthetic */ void m113x34c469bf() {
    }

    /* renamed from: getSettingObserver$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations */
    public static /* synthetic */ void m114x3a354bd6() {
    }

    public static /* synthetic */ void isAutoRemove$annotations() {
    }
}
