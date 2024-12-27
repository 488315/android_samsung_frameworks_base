package com.android.systemui.controls.start;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ControlsStartable implements CoreStartable {
    public final AuthorizedPanelsRepository authorizedPanelsRepository;
    public final CoroutineDispatcher bgDispatcher;
    public final BroadcastDispatcher broadcastDispatcher;
    public final ControlsComponent controlsComponent;
    public final ControlsUtil controlsUtil;
    public final DelayableExecutor executor;
    public final PackageChangeInteractor packageChangeInteractor;
    public Job packageJob;
    public final CoroutineScope scope;
    public final SelectedComponentRepository selectedComponentRepository;
    public final UserManager userManager;
    public final UserTracker userTracker;
    public final ControlsStartable$userTrackerCallback$1 userTrackerCallback = new UserTracker.Callback() { // from class: com.android.systemui.controls.start.ControlsStartable$userTrackerCallback$1
        @Override // com.android.systemui.settings.UserTracker.Callback
        public final void onUserChanged(final int i, Context context) {
            final ControlsStartable controlsStartable = ControlsStartable.this;
            controlsStartable.executor.execute(new Runnable() { // from class: com.android.systemui.controls.start.ControlsStartable$userTrackerCallback$1$onUserChanged$1
                @Override // java.lang.Runnable
                public final void run() {
                    ((ControlsControllerImpl) ((ControlsController) ControlsStartable.this.controlsComponent.controlsController.get())).changeUser(UserHandle.of(i));
                }
            });
            controlsStartable.executor.executeDelayed(new Runnable() { // from class: com.android.systemui.controls.start.ControlsStartable$userTrackerCallback$1$onUserChanged$2
                @Override // java.lang.Runnable
                public final void run() {
                    ControlsStartable.access$startForUser(ControlsStartable.this);
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
        List queryIntentServicesAsUser = packageManager.queryIntentServicesAsUser(intent, PackageManager.ResolveInfoFlags.of(786564), userTrackerImpl.getUserHandle());
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(queryIntentServicesAsUser, 10));
        Iterator it = queryIntentServicesAsUser.iterator();
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
                Iterator it2 = ((ArrayList) currentServices).iterator();
                while (it2.hasNext()) {
                    Object next = it2.next();
                    if (((ControlsServiceInfo) next).panelActivity != null) {
                        arrayList2.add(next);
                    }
                }
                Iterator it3 = ArraysKt___ArraysKt.toSet(((AuthorizedPanelsRepositoryImpl) controlsStartable.authorizedPanelsRepository).context.getResources().getStringArray(R.array.config_controlsPreferredPackages)).iterator();
                while (true) {
                    if (!it3.hasNext()) {
                        controlsServiceInfo = null;
                        break;
                    }
                    String str = (String) it3.next();
                    Iterator it4 = arrayList2.iterator();
                    while (true) {
                        if (!it4.hasNext()) {
                            obj = null;
                            break;
                        } else {
                            obj = it4.next();
                            if (Intrinsics.areEqual(((ControlsServiceInfo) obj).componentName.getPackageName(), str)) {
                                break;
                            }
                        }
                    }
                    controlsServiceInfo = (ControlsServiceInfo) obj;
                    if (controlsServiceInfo != null) {
                        break;
                    }
                }
                if (controlsServiceInfo != null) {
                    ((SecSelectedComponentRepositoryImpl) ((ControlsControllerImpl) ((SecControlsController) controlsComponent.secControlsController.get())).secSelectedComponentRepository).setSelectedComponent(new SelectedComponentRepository.SelectedComponent(new SelectedItem.PanelItem(controlsServiceInfo.loadLabel(), controlsServiceInfo.componentName)));
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
                    ControlsStartable controlsStartable2 = ControlsStartable.this;
                    if (controlsStartable2.userManager.isUserUnlocked(((UserTrackerImpl) controlsStartable2.userTracker).getUserId())) {
                        ControlsStartable.this.bindToPanelInternal();
                        ControlsStartable.this.broadcastDispatcher.unregisterReceiver(this);
                    }
                }
            }, new IntentFilter("android.intent.action.USER_UNLOCKED"), controlsStartable.executor, userTrackerImpl2.getUserHandle(), 0, null, 48);
        }
        Job job = controlsStartable.packageJob;
        if (job != null) {
            job.cancel(null);
        }
        UserHandle userHandle = userTrackerImpl2.getUserHandle();
        PackageChangeInteractor packageChangeInteractor = controlsStartable.packageChangeInteractor;
        packageChangeInteractor.getClass();
        final Flow transformLatest = userHandle.equals(UserHandle.CURRENT) ? FlowKt.transformLatest(packageChangeInteractor.userInteractor.selectedUser, new PackageChangeInteractor$packageChanged$$inlined$flatMapLatest$1(null, packageChangeInteractor, null)) : new PackageChangeInteractor$packageChangedInternal$$inlined$filter$1(((PackageChangeRepositoryImpl) packageChangeInteractor.packageChangeRepository).packageChanged(userHandle), null);
        final Flow flow = new Flow() { // from class: com.android.systemui.controls.start.ControlsStartable$monitorPackageUninstall$$inlined$filterIsInstance$1

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.controls.start.ControlsStartable$monitorPackageUninstall$$inlined$filterIsInstance$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.controls.start.ControlsStartable$monitorPackageUninstall$$inlined$filterIsInstance$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.controls.start.ControlsStartable$monitorPackageUninstall$$inlined$filterIsInstance$1$2$1 r0 = (com.android.systemui.controls.start.ControlsStartable$monitorPackageUninstall$$inlined$filterIsInstance$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.controls.start.ControlsStartable$monitorPackageUninstall$$inlined$filterIsInstance$1$2$1 r0 = new com.android.systemui.controls.start.ControlsStartable$monitorPackageUninstall$$inlined$filterIsInstance$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L41
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        boolean r6 = r5 instanceof com.android.systemui.common.shared.model.PackageChangeModel.Uninstalled
                        if (r6 == 0) goto L41
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L41
                        return r1
                    L41:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.controls.start.ControlsStartable$monitorPackageUninstall$$inlined$filterIsInstance$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        controlsStartable.packageJob = FlowKt.launchIn(FlowKt.flowOn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(new Flow() { // from class: com.android.systemui.controls.start.ControlsStartable$monitorPackageUninstall$$inlined$filter$1

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.controls.start.ControlsStartable$monitorPackageUninstall$$inlined$filter$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ ControlsStartable this$0;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

                /* JADX WARN: Removed duplicated region for block: B:15:0x0030  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r11, kotlin.coroutines.Continuation r12) {
                    /*
                        r10 = this;
                        boolean r0 = r12 instanceof com.android.systemui.controls.start.ControlsStartable$monitorPackageUninstall$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r12
                        com.android.systemui.controls.start.ControlsStartable$monitorPackageUninstall$$inlined$filter$1$2$1 r0 = (com.android.systemui.controls.start.ControlsStartable$monitorPackageUninstall$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.controls.start.ControlsStartable$monitorPackageUninstall$$inlined$filter$1$2$1 r0 = new com.android.systemui.controls.start.ControlsStartable$monitorPackageUninstall$$inlined$filter$1$2$1
                        r0.<init>(r12)
                    L18:
                        java.lang.Object r12 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L30
                        if (r2 != r3) goto L28
                        kotlin.ResultKt.throwOnFailure(r12)
                        goto La1
                    L28:
                        java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
                        java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
                        r10.<init>(r11)
                        throw r10
                    L30:
                        kotlin.ResultKt.throwOnFailure(r12)
                        r12 = r11
                        com.android.systemui.common.shared.model.PackageChangeModel$Uninstalled r12 = (com.android.systemui.common.shared.model.PackageChangeModel.Uninstalled) r12
                        com.android.systemui.controls.start.ControlsStartable r2 = r10.this$0
                        com.android.systemui.controls.panels.SelectedComponentRepository r2 = r2.selectedComponentRepository
                        android.os.UserHandle r4 = android.os.UserHandle.CURRENT
                        com.android.systemui.controls.panels.SelectedComponentRepositoryImpl r2 = (com.android.systemui.controls.panels.SelectedComponentRepositoryImpl) r2
                        r2.getClass()
                        boolean r5 = r4.equals(r4)
                        if (r5 == 0) goto L50
                        com.android.systemui.settings.UserTracker r4 = r2.userTracker
                        com.android.systemui.settings.UserTrackerImpl r4 = (com.android.systemui.settings.UserTrackerImpl) r4
                        int r4 = r4.getUserId()
                        goto L54
                    L50:
                        int r4 = r4.getIdentifier()
                    L54:
                        com.android.systemui.settings.UserFileManager r2 = r2.userFileManager
                        com.android.systemui.settings.UserFileManagerImpl r2 = (com.android.systemui.settings.UserFileManagerImpl) r2
                        java.lang.String r5 = "controls_prefs"
                        android.content.SharedPreferences r2 = r2.getSharedPreferences$1(r4, r5)
                        java.lang.String r4 = "controls_component"
                        r5 = 0
                        java.lang.String r4 = r2.getString(r4, r5)
                        if (r4 != 0) goto L69
                        r6 = r5
                        goto L84
                    L69:
                        com.android.systemui.controls.panels.SelectedComponentRepository$SelectedComponent r6 = new com.android.systemui.controls.panels.SelectedComponentRepository$SelectedComponent
                        java.lang.String r7 = "controls_structure"
                        java.lang.String r8 = ""
                        java.lang.String r7 = r2.getString(r7, r8)
                        kotlin.jvm.internal.Intrinsics.checkNotNull(r7)
                        android.content.ComponentName r4 = android.content.ComponentName.unflattenFromString(r4)
                        java.lang.String r8 = "controls_is_panel"
                        r9 = 0
                        boolean r2 = r2.getBoolean(r8, r9)
                        r6.<init>(r7, r4, r2)
                    L84:
                        if (r6 == 0) goto L8e
                        android.content.ComponentName r2 = r6.componentName
                        if (r2 == 0) goto L8e
                        java.lang.String r5 = r2.getPackageName()
                    L8e:
                        java.lang.String r12 = r12.packageName
                        boolean r12 = kotlin.jvm.internal.Intrinsics.areEqual(r12, r5)
                        if (r12 == 0) goto La1
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r10 = r10.$this_unsafeFlow
                        java.lang.Object r10 = r10.emit(r11, r0)
                        if (r10 != r1) goto La1
                        return r1
                    La1:
                        kotlin.Unit r10 = kotlin.Unit.INSTANCE
                        return r10
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.controls.start.ControlsStartable$monitorPackageUninstall$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, controlsStartable), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
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
        Iterator it = ((ArrayList) currentServices).iterator();
        while (it.hasNext()) {
            Object next = it.next();
            if (((ControlsServiceInfo) next).panelActivity != null) {
                arrayList.add(next);
            }
        }
        if (preferredComponentSelectedItem instanceof SelectedItem.PanelItem) {
            Iterator it2 = arrayList.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    obj = null;
                    break;
                } else {
                    obj = it2.next();
                    if (Intrinsics.areEqual(((ControlsServiceInfo) obj).componentName, ((SelectedItem.PanelItem) preferredComponentSelectedItem).componentName)) {
                        break;
                    }
                }
            }
            if (obj != null) {
                SelectedItem.PanelItem panelItem = (SelectedItem.PanelItem) preferredComponentSelectedItem;
                String packageName = panelItem.componentName.getPackageName();
                this.controlsUtil.getClass();
                if ("com.samsung.android.oneconnect".equals(packageName)) {
                    return;
                }
                ControlsController controlsController = (ControlsController) controlsComponent.controlsController.get();
                ControlsProviderLifecycleManager retrieveLifecycleManager = ((ControlsBindingControllerImpl) ((ControlsControllerImpl) controlsController).bindingController).retrieveLifecycleManager(panelItem.componentName);
                retrieveLifecycleManager.getClass();
                retrieveLifecycleManager.executor.execute(new ControlsProviderLifecycleManager$bindService$1(retrieveLifecycleManager, true, true));
            }
        }
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
            DelayableExecutor delayableExecutor = this.executor;
            delayableExecutor.execute(runnable);
            ((UserTrackerImpl) this.userTracker).addCallback(this.userTrackerCallback, delayableExecutor);
        }
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
    }
}
