package com.android.systemui.controls.management;

import android.app.ActivityTaskManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.UserHandle;
import android.util.IndentingPrintWriter;
import android.util.Log;
import com.android.settingslib.applications.ServiceListing;
import com.android.systemui.BasicRune;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.controls.ControlsServiceInfo;
import com.android.systemui.controls.management.ControlsListingController;
import com.android.systemui.controls.panels.CustomSelectedComponentRepository;
import com.android.systemui.controls.panels.CustomSelectedComponentRepositoryImpl;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.flags.ReleasedFlag;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.util.ActivityTaskManagerProxy;
import com.android.systemui.util.DumpUtilsKt;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ControlsListingControllerImpl implements ControlsListingController, Dumpable {
    public final ActivityTaskManagerProxy activityTaskManagerProxy;
    public List availableServices;
    public final Executor backgroundExecutor;
    public final Set callbacks;
    public final Context context;
    public int currentUserId;
    public final CustomSelectedComponentRepository customSelectedComponentRepository;
    public final FeatureFlags featureFlags;
    public ServiceListing serviceListing;
    public final Function1 serviceListingBuilder;
    public final ControlsListingControllerImpl$serviceListingCallback$1 serviceListingCallback;
    public final AtomicInteger userChangeInProgress;
    public final UserTracker userTracker;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.controls.management.ControlsListingControllerImpl$1 */
    public /* synthetic */ class C11781 extends FunctionReferenceImpl implements Function1 {
        public static final C11781 INSTANCE = new C11781();

        public C11781() {
            super(1, ControlsListingControllerImplKt.class, "createServiceListing", "createServiceListing(Landroid/content/Context;)Lcom/android/settingslib/applications/ServiceListing;", 1);
        }

        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Object obj) {
            String str = "controls_providers";
            String str2 = "controls_providers";
            return new ServiceListing(new ServiceListing.Builder((Context) obj).mContext, str2, str, "android.service.controls.ControlsProviderService", "android.permission.BIND_CONTROLS", "Controls Provider", true);
        }
    }

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

    public ControlsListingControllerImpl(Context context, Executor executor, Function1 function1, UserTracker userTracker, ActivityTaskManagerProxy activityTaskManagerProxy, DumpManager dumpManager, FeatureFlags featureFlags, CustomSelectedComponentRepository customSelectedComponentRepository) {
        this.context = context;
        this.backgroundExecutor = executor;
        this.serviceListingBuilder = function1;
        this.userTracker = userTracker;
        this.activityTaskManagerProxy = activityTaskManagerProxy;
        this.featureFlags = featureFlags;
        this.customSelectedComponentRepository = customSelectedComponentRepository;
        this.serviceListing = (ServiceListing) function1.invoke(context);
        this.callbacks = new LinkedHashSet();
        this.availableServices = EmptyList.INSTANCE;
        this.userChangeInProgress = new AtomicInteger(0);
        this.currentUserId = ((UserTrackerImpl) userTracker).getUserId();
        ControlsListingControllerImpl$serviceListingCallback$1 controlsListingControllerImpl$serviceListingCallback$1 = new ControlsListingControllerImpl$serviceListingCallback$1(this);
        this.serviceListingCallback = controlsListingControllerImpl$serviceListingCallback$1;
        Log.d("ControlsListingControllerImpl", "Initializing");
        DumpManager.registerDumpable$default(dumpManager, "ControlsListingControllerImpl", this);
        ((ArrayList) this.serviceListing.mCallbacks).add(controlsListingControllerImpl$serviceListingCallback$1);
        this.serviceListing.setListening(true);
        this.serviceListing.reload();
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(Object obj) {
        this.backgroundExecutor.execute(new ControlsListingControllerImpl$addCallback$1(this, (ControlsListingController.ControlsListingCallback) obj));
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("ControlsListingController:");
        IndentingPrintWriter asIndenting = DumpUtilsKt.asIndenting(printWriter);
        asIndenting.increaseIndent();
        asIndenting.println("Callbacks: " + this.callbacks);
        asIndenting.println("Services: " + getCurrentServices());
        asIndenting.decreaseIndent();
    }

    public final CharSequence getAppLabel(ComponentName componentName) {
        Object obj;
        Iterator it = this.availableServices.iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            obj = it.next();
            if (Intrinsics.areEqual(((ControlsServiceInfo) obj).componentName, componentName)) {
                break;
            }
        }
        ControlsServiceInfo controlsServiceInfo = (ControlsServiceInfo) obj;
        if (controlsServiceInfo != null) {
            return controlsServiceInfo.loadLabel();
        }
        return null;
    }

    public final List getCurrentServices() {
        int i;
        List<ControlsServiceInfo> list = this.availableServices;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
        for (ControlsServiceInfo controlsServiceInfo : list) {
            ControlsServiceInfo controlsServiceInfo2 = new ControlsServiceInfo(controlsServiceInfo.context, controlsServiceInfo.serviceInfo);
            controlsServiceInfo2.panelActivity = controlsServiceInfo.panelActivity;
            arrayList.add(controlsServiceInfo2);
        }
        ArrayList arrayList2 = new ArrayList();
        for (Object obj : arrayList) {
            ControlsServiceInfo controlsServiceInfo3 = (ControlsServiceInfo) obj;
            boolean z = BasicRune.CONTROLS_SAMSUNG_STYLE;
            if ((z && (i = controlsServiceInfo3.userId) == this.currentUserId && i == ((UserTrackerImpl) this.userTracker).getUserId()) || !z) {
                arrayList2.add(obj);
            }
        }
        return arrayList2;
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(Object obj) {
        final ControlsListingController.ControlsListingCallback controlsListingCallback = (ControlsListingController.ControlsListingCallback) obj;
        this.backgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.controls.management.ControlsListingControllerImpl$removeCallback$1
            @Override // java.lang.Runnable
            public final void run() {
                Log.d("ControlsListingControllerImpl", "Unsubscribing callback");
                ControlsListingControllerImpl.this.callbacks.remove(controlsListingCallback);
            }
        });
    }

    /* JADX WARN: Code restructure failed: missing block: B:32:0x00b5, code lost:
    
        if ((r8 != 0 ? r8 == 1 : r6.enabled) != false) goto L38;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x00b9, code lost:
    
        if (r4 != false) goto L41;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateServices(List list) {
        ReleasedFlag releasedFlag = Flags.USE_APP_PANELS;
        FeatureFlags featureFlags = this.featureFlags;
        if (((FeatureFlagsRelease) featureFlags).isEnabled(releasedFlag)) {
            this.activityTaskManagerProxy.getClass();
            if (ActivityTaskManager.supportsMultiWindow(this.context)) {
                boolean isEnabled = ((FeatureFlagsRelease) featureFlags).isEnabled(Flags.APP_PANELS_ALL_APPS_ALLOWED);
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    ControlsServiceInfo controlsServiceInfo = (ControlsServiceInfo) it.next();
                    if (!controlsServiceInfo.resolved) {
                        boolean z = true;
                        controlsServiceInfo.resolved = true;
                        String[] stringArray = controlsServiceInfo.context.getResources().getStringArray(R.array.config_controlsPreferredPackages);
                        if (BasicRune.CONTROLS_SAMSUNG_STYLE) {
                            ControlsListingControllerImpl$$ExternalSyntheticOutline0.m117m("resolvePanelActivity allowAllApps = ", isEnabled, "ControlsServiceInfo");
                        }
                        if (ArraysKt___ArraysKt.contains(stringArray, controlsServiceInfo.componentName.getPackageName()) || isEnabled) {
                            ComponentName componentName = controlsServiceInfo._panelActivity;
                            if (componentName != null) {
                                Intent component = new Intent().setComponent(componentName);
                                PackageManager.ResolveInfoFlags of = PackageManager.ResolveInfoFlags.of(786432L);
                                UserHandle of2 = UserHandle.of(controlsServiceInfo.userId);
                                PackageManager packageManager = controlsServiceInfo.mPm;
                                List queryIntentActivitiesAsUser = packageManager.queryIntentActivitiesAsUser(component, of, of2);
                                if (!queryIntentActivitiesAsUser.isEmpty()) {
                                    ActivityInfo activityInfo = ((ResolveInfo) queryIntentActivitiesAsUser.get(0)).activityInfo;
                                    if (activityInfo != null && Intrinsics.areEqual(activityInfo.permission, "android.permission.BIND_CONTROLS") && activityInfo.exported) {
                                        int componentEnabledSetting = packageManager.getComponentEnabledSetting(activityInfo.getComponentName());
                                    }
                                    z = false;
                                }
                            }
                            componentName = null;
                            controlsServiceInfo.panelActivity = componentName;
                        }
                    }
                }
            }
        }
        if (Intrinsics.areEqual(list, this.availableServices)) {
            return;
        }
        this.availableServices = list;
        if (BasicRune.CONTROLS_SAMSUNG_STYLE) {
            CustomSelectedComponentRepository customSelectedComponentRepository = this.customSelectedComponentRepository;
            CustomSelectedComponentRepository.CustomSelectedComponent selectedComponent = ((CustomSelectedComponentRepositoryImpl) customSelectedComponentRepository).getSelectedComponent();
            if (selectedComponent != null) {
                ComponentName componentName2 = selectedComponent.componentName;
                final String packageName = componentName2 != null ? componentName2.getPackageName() : null;
                if (((Set) this.availableServices.stream().filter(new Predicate() { // from class: com.android.systemui.controls.management.ControlsListingControllerImpl$updateServices$2$1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((ControlsServiceInfo) obj).serviceInfo.packageName.equals(packageName);
                    }
                }).collect(Collectors.toSet())).size() == 0) {
                    ((CustomSelectedComponentRepositoryImpl) customSelectedComponentRepository).sharedPreferences.edit().remove("controls_custom_component").remove("controls_custom_is_panel").remove("controls_custom_structure").apply();
                }
            }
        }
        Iterator it2 = this.callbacks.iterator();
        while (it2.hasNext()) {
            ((ControlsListingController.ControlsListingCallback) it2.next()).onServicesUpdated(getCurrentServices());
        }
    }

    public ControlsListingControllerImpl(Context context, Executor executor, UserTracker userTracker, ActivityTaskManagerProxy activityTaskManagerProxy, DumpManager dumpManager, FeatureFlags featureFlags, CustomSelectedComponentRepository customSelectedComponentRepository) {
        this(context, executor, C11781.INSTANCE, userTracker, activityTaskManagerProxy, dumpManager, featureFlags, customSelectedComponentRepository);
    }
}
