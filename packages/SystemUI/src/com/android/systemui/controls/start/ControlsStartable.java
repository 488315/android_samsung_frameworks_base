package com.android.systemui.controls.start;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.UserHandle;
import android.os.UserManager;
import com.android.systemui.CoreStartable;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.common.data.repository.PackageChangeRepositoryImpl;
import com.android.systemui.common.domain.interactor.PackageChangeInteractor;
import com.android.systemui.common.domain.interactor.PackageChangeInteractor$packageChanged$$inlined$flatMapLatest$1;
import com.android.systemui.common.domain.interactor.PackageChangeInteractor$packageChangedInternal$$inlined$filter$1;
import com.android.systemui.common.shared.model.PackageChangeModel;
import com.android.systemui.controls.ControlsServiceInfo;
import com.android.systemui.controls.controller.ControlsBindingControllerImpl;
import com.android.systemui.controls.controller.ControlsController;
import com.android.systemui.controls.controller.ControlsControllerImpl;
import com.android.systemui.controls.controller.ControlsProviderLifecycleManager;
import com.android.systemui.controls.controller.ControlsProviderLifecycleManager$bindService$1;
import com.android.systemui.controls.controller.SecControlsController;
import com.android.systemui.controls.dagger.ControlsComponent;
import com.android.systemui.controls.management.ControlsListingController;
import com.android.systemui.controls.management.ControlsListingControllerImpl;
import com.android.systemui.controls.panels.AuthorizedPanelsRepository;
import com.android.systemui.controls.panels.AuthorizedPanelsRepositoryImpl;
import com.android.systemui.controls.panels.SecSelectedComponentRepositoryImpl;
import com.android.systemui.controls.panels.SelectedComponentRepository;
import com.android.systemui.controls.panels.SelectedComponentRepositoryImpl;
import com.android.systemui.controls.ui.SecControlsUiControllerImpl;
import com.android.systemui.controls.ui.SelectedItem;
import com.android.systemui.controls.util.ControlsUtil;
import com.android.systemui.settings.UserFileManagerImpl;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.concurrency.DelayableExecutor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;

/* loaded from: classes2.dex */
public final class ControlsStartable implements CoreStartable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AuthorizedPanelsRepository authorizedPanelsRepository;
    public final CoroutineDispatcher bgDispatcher;
    public final BroadcastDispatcher broadcastDispatcher;
    public final ControlsComponent controlsComponent;
    public final ControlsUtil controlsUtil;
    public final DelayableExecutor executor;
    public final PackageChangeInteractor packageChangeInteractor;
    public StandaloneCoroutine packageJob;
    public final CoroutineScope scope;
    public final SelectedComponentRepository selectedComponentRepository;
    public final UserManager userManager;
    public final UserTracker userTracker;
    public final ControlsStartable$userTrackerCallback$1 userTrackerCallback = new UserTracker.Callback() { // from class: com.android.systemui.controls.start.ControlsStartable$userTrackerCallback$1
        @Override // com.android.systemui.settings.UserTracker.Callback
        public final void onUserChanged(final int i, Context context) {
            final ControlsStartable controlsStartable = this.this$0;
            controlsStartable.executor.execute(new Runnable() { // from class: com.android.systemui.controls.start.ControlsStartable$userTrackerCallback$1$onUserChanged$1
                @Override // java.lang.Runnable
                public final void run() {
                    ControlsStartable controlsStartable2 = controlsStartable;
                    int i2 = ControlsStartable.$r8$clinit;
                    ((ControlsController) controlsStartable2.controlsComponent.controlsController.get()).changeUser(UserHandle.of(i));
                }
            });
            controlsStartable.executor.executeDelayed(new Runnable() { // from class: com.android.systemui.controls.start.ControlsStartable$userTrackerCallback$1$onUserChanged$2
                @Override // java.lang.Runnable
                public final void run() {
                    ControlsStartable.access$startForUser(controlsStartable);
                }
            }, 50L, TimeUnit.MILLISECONDS);
        }
    };

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.controls.start.ControlsStartable$userTrackerCallback$1] */
    public ControlsStartable(CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, DelayableExecutor delayableExecutor, ControlsComponent controlsComponent, UserTracker userTracker, AuthorizedPanelsRepository authorizedPanelsRepository, SelectedComponentRepository selectedComponentRepository, PackageChangeInteractor packageChangeInteractor, UserManager userManager, BroadcastDispatcher broadcastDispatcher, ControlsUtil controlsUtil) {
        this.scope = coroutineScope;
        this.bgDispatcher = coroutineDispatcher;
        this.executor = delayableExecutor;
        this.controlsComponent = controlsComponent;
        this.userTracker = userTracker;
        this.authorizedPanelsRepository = authorizedPanelsRepository;
        this.selectedComponentRepository = selectedComponentRepository;
        this.packageChangeInteractor = packageChangeInteractor;
        this.userManager = userManager;
        this.broadcastDispatcher = broadcastDispatcher;
        this.controlsUtil = controlsUtil;
    }

    public static final void access$startForUser(final ControlsStartable controlsStartable) {
        ControlsServiceInfo controlsServiceInfo;
        Object obj;
        ControlsListingControllerImpl controlsListingControllerImpl = (ControlsListingControllerImpl) ((ControlsListingController) controlsStartable.controlsComponent.controlsListingController.get());
        PackageManager packageManager = controlsListingControllerImpl.context.getPackageManager();
        Intent intent = new Intent("android.service.controls.ControlsProviderService");
        UserTrackerImpl userTrackerImpl = (UserTrackerImpl) controlsListingControllerImpl.userTracker;
        List listQueryIntentServicesAsUser = packageManager.queryIntentServicesAsUser(intent, PackageManager.ResolveInfoFlags.of(786564), userTrackerImpl.getUserHandle());
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(listQueryIntentServicesAsUser, 10));
        Iterator it = listQueryIntentServicesAsUser.iterator();
        while (it.hasNext()) {
            arrayList.add(new ControlsServiceInfo(userTrackerImpl.getUserContext(), ((ResolveInfo) it.next()).serviceInfo));
        }
        controlsListingControllerImpl.updateServices(arrayList);
        SelectedComponentRepositoryImpl selectedComponentRepositoryImpl = (SelectedComponentRepositoryImpl) controlsStartable.selectedComponentRepository;
        if (((UserFileManagerImpl) selectedComponentRepositoryImpl.userFileManager).getSharedPreferences$1(((UserTrackerImpl) selectedComponentRepositoryImpl.userTracker).getUserId(), SystemUIAnalytics.CONTROL_PREF_NAME).getBoolean("should_add_default_panel", true)) {
            ControlsComponent controlsComponent = controlsStartable.controlsComponent;
            ControlsControllerImpl controlsControllerImpl = (ControlsControllerImpl) ((SecControlsController) controlsComponent.secControlsController.get());
            SelectedItem preferredComponentSelectedItem = ((SecControlsUiControllerImpl) controlsControllerImpl.secUiController).getPreferredComponentSelectedItem(controlsControllerImpl.getActiveFavoritesComponent());
            SelectedItem.Companion.getClass();
            if (Intrinsics.areEqual(preferredComponentSelectedItem, SelectedItem.EMPTY_SELECTION)) {
                List currentServices = ((ControlsListingControllerImpl) ((ControlsListingController) controlsComponent.controlsListingController.get())).getCurrentServices();
                ArrayList arrayList2 = new ArrayList();
                ArrayList arrayList3 = (ArrayList) currentServices;
                int size = arrayList3.size();
                int i = 0;
                while (i < size) {
                    Object obj2 = arrayList3.get(i);
                    i++;
                    if (((ControlsServiceInfo) obj2).panelActivity != null) {
                        arrayList2.add(obj2);
                    }
                }
                Iterator it2 = ArraysKt___ArraysKt.toSet(((AuthorizedPanelsRepositoryImpl) controlsStartable.authorizedPanelsRepository).context.getResources().getStringArray(R.array.config_controlsPreferredPackages)).iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        controlsServiceInfo = null;
                        break;
                    }
                    String str = (String) it2.next();
                    int size2 = arrayList2.size();
                    int i2 = 0;
                    while (true) {
                        if (i2 >= size2) {
                            obj = null;
                            break;
                        }
                        obj = arrayList2.get(i2);
                        i2++;
                        if (Intrinsics.areEqual(((ControlsServiceInfo) obj).componentName.getPackageName(), str)) {
                            break;
                        }
                    }
                    controlsServiceInfo = (ControlsServiceInfo) obj;
                    if (controlsServiceInfo != null) {
                        break;
                    }
                }
                if (controlsServiceInfo != null) {
                    SecControlsController secControlsController = (SecControlsController) controlsComponent.secControlsController.get();
                    SelectedItem.PanelItem panelItem = new SelectedItem.PanelItem(controlsServiceInfo.loadLabel(), controlsServiceInfo.componentName);
                    ControlsControllerImpl controlsControllerImpl2 = (ControlsControllerImpl) secControlsController;
                    controlsControllerImpl2.getClass();
                    ((SecSelectedComponentRepositoryImpl) controlsControllerImpl2.secSelectedComponentRepository).setSelectedComponent(new SelectedComponentRepository.SelectedComponent(panelItem));
                }
            }
        }
        UserManager userManager = controlsStartable.userManager;
        UserTrackerImpl userTrackerImpl2 = (UserTrackerImpl) controlsStartable.userTracker;
        if (userManager.isUserUnlocked(userTrackerImpl2.getUserId())) {
            controlsStartable.bindToPanelInternal();
        } else {
            BroadcastDispatcher.registerReceiver$default(controlsStartable.broadcastDispatcher, new BroadcastReceiver() { // from class: com.android.systemui.controls.start.ControlsStartable$bindToPanel$1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent2) {
                    ControlsStartable controlsStartable2 = this.this$0;
                    if (controlsStartable2.userManager.isUserUnlocked(((UserTrackerImpl) controlsStartable2.userTracker).getUserId())) {
                        this.this$0.bindToPanelInternal();
                        this.this$0.broadcastDispatcher.unregisterReceiver(this);
                    }
                }
            }, new IntentFilter("android.intent.action.USER_UNLOCKED"), controlsStartable.executor, userTrackerImpl2.getUserHandle(), 0, null, 48);
        }
        StandaloneCoroutine standaloneCoroutine = controlsStartable.packageJob;
        if (standaloneCoroutine != null) {
            standaloneCoroutine.cancel(null);
        }
        UserHandle userHandle = userTrackerImpl2.getUserHandle();
        PackageChangeInteractor packageChangeInteractor = controlsStartable.packageChangeInteractor;
        packageChangeInteractor.getClass();
        final Flow flowTransformLatest = Intrinsics.areEqual(userHandle, UserHandle.CURRENT) ? FlowKt.transformLatest(packageChangeInteractor.userInteractor.selectedUser, new PackageChangeInteractor$packageChanged$$inlined$flatMapLatest$1(null, packageChangeInteractor, null)) : new PackageChangeInteractor$packageChangedInternal$$inlined$filter$1(((PackageChangeRepositoryImpl) packageChangeInteractor.packageChangeRepository).packageChanged(userHandle), null);
        final Flow flow = new Flow() { // from class: com.android.systemui.controls.start.ControlsStartable$monitorPackageUninstall$$inlined$filterIsInstance$1

            /* renamed from: com.android.systemui.controls.start.ControlsStartable$monitorPackageUninstall$$inlined$filterIsInstance$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.controls.start.ControlsStartable$monitorPackageUninstall$$inlined$filterIsInstance$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    Object L$1;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        if (obj instanceof PackageChangeModel.Uninstalled) {
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flowTransformLatest.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        controlsStartable.packageJob = FlowKt.launchIn(FlowKt.flowOn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(new Flow() { // from class: com.android.systemui.controls.start.ControlsStartable$monitorPackageUninstall$$inlined$filter$1

            /* renamed from: com.android.systemui.controls.start.ControlsStartable$monitorPackageUninstall$$inlined$filter$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ ControlsStartable this$0;

                /* renamed from: com.android.systemui.controls.start.ControlsStartable$monitorPackageUninstall$$inlined$filter$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    Object L$1;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector, ControlsStartable controlsStartable) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = controlsStartable;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    SelectedComponentRepository.SelectedComponent selectedComponent;
                    ComponentName componentName;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        PackageChangeModel.Uninstalled uninstalled = (PackageChangeModel.Uninstalled) obj;
                        SelectedComponentRepository selectedComponentRepository = this.this$0.selectedComponentRepository;
                        UserHandle userHandle = UserHandle.CURRENT;
                        SelectedComponentRepositoryImpl selectedComponentRepositoryImpl = (SelectedComponentRepositoryImpl) selectedComponentRepository;
                        selectedComponentRepositoryImpl.getClass();
                        SharedPreferences sharedPreferences$1 = ((UserFileManagerImpl) selectedComponentRepositoryImpl.userFileManager).getSharedPreferences$1(Intrinsics.areEqual(userHandle, userHandle) ? ((UserTrackerImpl) selectedComponentRepositoryImpl.userTracker).getUserId() : userHandle.getIdentifier(), SystemUIAnalytics.CONTROL_PREF_NAME);
                        String packageName = null;
                        String string = sharedPreferences$1.getString("controls_component", null);
                        if (string == null) {
                            selectedComponent = null;
                        } else {
                            String string2 = sharedPreferences$1.getString("controls_structure", "");
                            string2.getClass();
                            selectedComponent = new SelectedComponentRepository.SelectedComponent(string2, ComponentName.unflattenFromString(string), sharedPreferences$1.getBoolean("controls_is_panel", false));
                        }
                        if (selectedComponent != null && (componentName = selectedComponent.componentName) != null) {
                            packageName = componentName.getPackageName();
                        }
                        if (Intrinsics.areEqual(uninstalled.packageName, packageName)) {
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flow.collect(new AnonymousClass2(flowCollector, controlsStartable), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, new ControlsStartable$monitorPackageUninstall$2(controlsStartable, null)), controlsStartable.bgDispatcher), controlsStartable.scope);
    }

    public final void bindToPanelInternal() {
        Object obj;
        ControlsComponent controlsComponent = this.controlsComponent;
        ControlsControllerImpl controlsControllerImpl = (ControlsControllerImpl) ((SecControlsController) controlsComponent.secControlsController.get());
        SelectedItem preferredComponentSelectedItem = ((SecControlsUiControllerImpl) controlsControllerImpl.secUiController).getPreferredComponentSelectedItem(controlsControllerImpl.getActiveFavoritesComponent());
        List currentServices = ((ControlsListingControllerImpl) ((ControlsListingController) controlsComponent.controlsListingController.get())).getCurrentServices();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = (ArrayList) currentServices;
        int size = arrayList2.size();
        int i = 0;
        int i2 = 0;
        while (i2 < size) {
            Object obj2 = arrayList2.get(i2);
            i2++;
            if (((ControlsServiceInfo) obj2).panelActivity != null) {
                arrayList.add(obj2);
            }
        }
        if (preferredComponentSelectedItem instanceof SelectedItem.PanelItem) {
            int size2 = arrayList.size();
            while (true) {
                if (i >= size2) {
                    obj = null;
                    break;
                }
                obj = arrayList.get(i);
                i++;
                if (Intrinsics.areEqual(((ControlsServiceInfo) obj).componentName, ((SelectedItem.PanelItem) preferredComponentSelectedItem).componentName)) {
                    break;
                }
            }
            if (obj != null) {
                SelectedItem.PanelItem panelItem = (SelectedItem.PanelItem) preferredComponentSelectedItem;
                String packageName = panelItem.componentName.getPackageName();
                this.controlsUtil.getClass();
                if ("com.samsung.android.oneconnect".equals(packageName)) {
                    return;
                }
                ControlsProviderLifecycleManager controlsProviderLifecycleManagerRetrieveLifecycleManager = ((ControlsBindingControllerImpl) ((ControlsControllerImpl) ((ControlsController) controlsComponent.controlsController.get())).bindingController).retrieveLifecycleManager(panelItem.componentName);
                controlsProviderLifecycleManagerRetrieveLifecycleManager.getClass();
                controlsProviderLifecycleManagerRetrieveLifecycleManager.executor.execute(new ControlsProviderLifecycleManager$bindService$1(controlsProviderLifecycleManagerRetrieveLifecycleManager, true, true));
            }
        }
    }

    @Override // com.android.systemui.CoreStartable
    public final void onBootCompleted() {
        if (this.controlsComponent.featureEnabled) {
            Runnable runnable = new Runnable() { // from class: com.android.systemui.controls.start.ControlsStartable.onBootCompleted.1
                @Override // java.lang.Runnable
                public final void run() {
                    ControlsStartable.access$startForUser(ControlsStartable.this);
                }
            };
            DelayableExecutor delayableExecutor = this.executor;
            delayableExecutor.execute(runnable);
            ((UserTrackerImpl) this.userTracker).addCallback(this.userTrackerCallback, delayableExecutor);
        }
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
    }
}
