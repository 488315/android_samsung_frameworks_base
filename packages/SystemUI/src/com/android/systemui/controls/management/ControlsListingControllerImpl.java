package com.android.systemui.controls.management;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.UserHandle;
import android.util.IndentingPrintWriter;
import android.util.Log;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import com.android.settingslib.applications.ServiceListing;
import com.android.systemui.Dumpable;
import com.android.systemui.controls.ControlsServiceInfo;
import com.android.systemui.controls.management.ControlsListingController;
import com.android.systemui.controls.panels.SecSelectedComponentRepository;
import com.android.systemui.controls.panels.SecSelectedComponentRepositoryImpl;
import com.android.systemui.controls.panels.SelectedComponentRepository;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.util.ActivityTaskManagerProxy;
import com.android.systemui.util.DumpUtilsKt;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class ControlsListingControllerImpl implements ControlsListingController, Dumpable {
    public final ActivityTaskManagerProxy activityTaskManagerProxy;
    public List availableServices;
    public final Executor backgroundExecutor;
    public final CopyOnWriteArraySet callbacks;
    public final Context context;
    public int currentUserId;
    public final SecSelectedComponentRepository secSelectedComponentRepository;
    public ServiceListing serviceListing;
    public final Function1 serviceListingBuilder;
    public final ControlsListingControllerImpl$serviceListingCallback$1 serviceListingCallback;
    public final AtomicInteger userChangeInProgress;
    public final UserTracker userTracker;

    /* renamed from: com.android.systemui.controls.management.ControlsListingControllerImpl$1, reason: invalid class name */
    final /* synthetic */ class AnonymousClass1 extends FunctionReferenceImpl implements Function1 {
        public static final AnonymousClass1 INSTANCE = new AnonymousClass1();

        public AnonymousClass1() {
            super(1, ControlsListingControllerImplKt.class, "createServiceListing", "createServiceListing(Landroid/content/Context;)Lcom/android/settingslib/applications/ServiceListing;", 1);
        }

        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo781invoke(Object obj) {
            return new ServiceListing(new ServiceListing.Builder((Context) obj).mContext);
        }
    }

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

    public ControlsListingControllerImpl(Context context, Executor executor, Function1 function1, UserTracker userTracker, ActivityTaskManagerProxy activityTaskManagerProxy, DumpManager dumpManager, FeatureFlags featureFlags, SecSelectedComponentRepository secSelectedComponentRepository) {
        this.context = context;
        this.backgroundExecutor = executor;
        this.serviceListingBuilder = function1;
        this.userTracker = userTracker;
        this.activityTaskManagerProxy = activityTaskManagerProxy;
        this.secSelectedComponentRepository = secSelectedComponentRepository;
        this.serviceListing = (ServiceListing) function1.mo781invoke(context);
        this.callbacks = new CopyOnWriteArraySet();
        this.availableServices = EmptyList.INSTANCE;
        this.userChangeInProgress = new AtomicInteger(0);
        this.currentUserId = ((UserTrackerImpl) userTracker).getUserId();
        ControlsListingControllerImpl$serviceListingCallback$1 controlsListingControllerImpl$serviceListingCallback$1 = new ControlsListingControllerImpl$serviceListingCallback$1(this);
        this.serviceListingCallback = controlsListingControllerImpl$serviceListingCallback$1;
        Log.d("ControlsListingControllerImpl", "Initializing");
        dumpManager.registerNormalDumpable("ControlsListingControllerImpl", this);
        ((ArrayList) this.serviceListing.mCallbacks).add(controlsListingControllerImpl$serviceListingCallback$1);
        this.serviceListing.setListening(true);
        this.serviceListing.reload();
    }

    @Override // com.android.systemui.util.UserAwareController
    public final void changeUser(final UserHandle userHandle) {
        this.userChangeInProgress.incrementAndGet();
        this.serviceListing.setListening(false);
        this.backgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.controls.management.ControlsListingControllerImpl.changeUser.1
            @Override // java.lang.Runnable
            public final void run() {
                if (ControlsListingControllerImpl.this.userChangeInProgress.decrementAndGet() == 0) {
                    ControlsListingControllerImpl.this.currentUserId = userHandle.getIdentifier();
                    Context contextCreateContextAsUser = ControlsListingControllerImpl.this.context.createContextAsUser(userHandle, 0);
                    ControlsListingControllerImpl controlsListingControllerImpl = ControlsListingControllerImpl.this;
                    controlsListingControllerImpl.serviceListing = (ServiceListing) controlsListingControllerImpl.serviceListingBuilder.mo781invoke(contextCreateContextAsUser);
                    ControlsListingControllerImpl controlsListingControllerImpl2 = ControlsListingControllerImpl.this;
                    ((ArrayList) controlsListingControllerImpl2.serviceListing.mCallbacks).add(controlsListingControllerImpl2.serviceListingCallback);
                    ControlsListingControllerImpl.this.serviceListing.setListening(true);
                    ControlsListingControllerImpl.this.serviceListing.reload();
                }
            }
        });
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("ControlsListingController:");
        IndentingPrintWriter indentingPrintWriterAsIndenting = DumpUtilsKt.asIndenting(printWriter);
        if (indentingPrintWriterAsIndenting != null) {
            indentingPrintWriterAsIndenting.increaseIndent();
        }
        indentingPrintWriterAsIndenting.println("Callbacks: " + this.callbacks);
        indentingPrintWriterAsIndenting.println("Services: " + getCurrentServices());
        indentingPrintWriterAsIndenting.decreaseIndent();
    }

    public final CharSequence getAppLabel(ComponentName componentName) {
        Object next;
        Iterator it = this.availableServices.iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            if (Intrinsics.areEqual(((ControlsServiceInfo) next).componentName, componentName)) {
                break;
            }
        }
        ControlsServiceInfo controlsServiceInfo = (ControlsServiceInfo) next;
        if (controlsServiceInfo != null) {
            return controlsServiceInfo.loadLabel();
        }
        return null;
    }

    public final List getCurrentServices() {
        List<ControlsServiceInfo> list = this.availableServices;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
        for (ControlsServiceInfo controlsServiceInfo : list) {
            ControlsServiceInfo controlsServiceInfo2 = new ControlsServiceInfo(controlsServiceInfo.context, controlsServiceInfo.serviceInfo);
            controlsServiceInfo2.panelActivity = controlsServiceInfo.panelActivity;
            arrayList.add(controlsServiceInfo2);
        }
        ArrayList arrayList2 = new ArrayList();
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            int i2 = ((ControlsServiceInfo) obj).userId;
            if (i2 == this.currentUserId && i2 == ((UserTrackerImpl) this.userTracker).getUserId()) {
                arrayList2.add(obj);
            }
        }
        return arrayList2;
    }

    @Override // com.android.systemui.util.UserAwareController
    public final int getCurrentUserId() {
        return this.currentUserId;
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(Object obj) {
        final ControlsListingController.ControlsListingCallback controlsListingCallback = (ControlsListingController.ControlsListingCallback) obj;
        this.backgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.controls.management.ControlsListingControllerImpl.removeCallback.1
            @Override // java.lang.Runnable
            public final void run() {
                Log.d("ControlsListingControllerImpl", "Unsubscribing callback");
                ControlsListingControllerImpl.this.callbacks.remove(controlsListingCallback);
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x0087  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateServices(List list) {
        ComponentName componentName;
        String packageName;
        if (this.activityTaskManagerProxy.supportsMultiWindow(this.context)) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                ControlsServiceInfo controlsServiceInfo = (ControlsServiceInfo) it.next();
                if (!controlsServiceInfo.resolved) {
                    controlsServiceInfo.resolved = true;
                    ComponentName componentName2 = controlsServiceInfo._panelActivity;
                    ComponentName componentName3 = null;
                    if (componentName2 != null) {
                        List listQueryIntentActivitiesAsUser = controlsServiceInfo.mPm.queryIntentActivitiesAsUser(new Intent().setComponent(componentName2), PackageManager.ResolveInfoFlags.of(786432L), UserHandle.of(controlsServiceInfo.userId));
                        if (!listQueryIntentActivitiesAsUser.isEmpty()) {
                            boolean z = false;
                            ActivityInfo activityInfo = ((ResolveInfo) listQueryIntentActivitiesAsUser.get(0)).activityInfo;
                            if (activityInfo != null && Intrinsics.areEqual(activityInfo.permission, "android.permission.BIND_CONTROLS") && activityInfo.exported) {
                                int componentEnabledSetting = controlsServiceInfo.mPm.getComponentEnabledSetting(activityInfo.getComponentName());
                                if (componentEnabledSetting != 0 ? componentEnabledSetting == 1 : activityInfo.enabled) {
                                    z = true;
                                }
                            }
                            if (!z) {
                                componentName2 = null;
                            }
                            componentName3 = componentName2;
                        }
                    }
                    controlsServiceInfo.panelActivity = componentName3;
                }
            }
        }
        if (Intrinsics.areEqual(list, this.availableServices)) {
            return;
        }
        this.availableServices = list;
        SecSelectedComponentRepository secSelectedComponentRepository = this.secSelectedComponentRepository;
        SelectedComponentRepository.SelectedComponent selectedComponent = ((SecSelectedComponentRepositoryImpl) secSelectedComponentRepository).getSelectedComponent(UserHandle.CURRENT);
        if (selectedComponent != null && (componentName = selectedComponent.componentName) != null && (packageName = componentName.getPackageName()) != null) {
            try {
                if (this.context.getPackageManager().getApplicationEnabledSetting(packageName) > 1) {
                    Log.d("ControlsListingControllerImpl", "updateServices " + packageName + " is disabled, so removeSelectedComponent");
                    ((SecSelectedComponentRepositoryImpl) secSelectedComponentRepository).removeSelectedComponent();
                }
            } catch (IllegalArgumentException e) {
                Log.e("ControlsListingControllerImpl", MotionLayout$$ExternalSyntheticOutline0.m("updateServices ", packageName, " : IllegalArgumentException = ", e.getMessage(), ", so removeSelectedComponent"));
                ((SecSelectedComponentRepositoryImpl) secSelectedComponentRepository).removeSelectedComponent();
            }
        }
        Iterator it2 = this.callbacks.iterator();
        while (it2.hasNext()) {
            ((ControlsListingController.ControlsListingCallback) it2.next()).onServicesUpdated(getCurrentServices());
        }
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(final ControlsListingController.ControlsListingCallback controlsListingCallback) {
        this.backgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.controls.management.ControlsListingControllerImpl.addCallback.1
            @Override // java.lang.Runnable
            public final void run() {
                if (ControlsListingControllerImpl.this.userChangeInProgress.get() > 0) {
                    ControlsListingControllerImpl.this.addCallback(controlsListingCallback);
                    return;
                }
                List currentServices = ControlsListingControllerImpl.this.getCurrentServices();
                ListPopupWindow$$ExternalSyntheticOutline0.m(((ArrayList) currentServices).size(), "Subscribing callback, service count: ", "ControlsListingControllerImpl");
                ControlsListingControllerImpl.this.callbacks.add(controlsListingCallback);
                controlsListingCallback.onServicesUpdated(currentServices);
            }
        });
    }

    public ControlsListingControllerImpl(Context context, Executor executor, UserTracker userTracker, ActivityTaskManagerProxy activityTaskManagerProxy, DumpManager dumpManager, FeatureFlags featureFlags, SecSelectedComponentRepository secSelectedComponentRepository) {
        this(context, executor, AnonymousClass1.INSTANCE, userTracker, activityTaskManagerProxy, dumpManager, featureFlags, secSelectedComponentRepository);
    }
}
