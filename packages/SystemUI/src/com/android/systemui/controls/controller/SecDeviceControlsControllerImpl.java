package com.android.systemui.controls.controller;

import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.BasicRune;
import com.android.systemui.R;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.controls.ControlsServiceInfo;
import com.android.systemui.controls.dagger.ControlsComponent;
import com.android.systemui.controls.management.ControlsListingController;
import com.android.systemui.controls.management.ControlsListingControllerImpl;
import com.android.systemui.controls.ui.util.ControlsActivityStarter;
import com.android.systemui.controls.ui.util.ControlsActivityStarterImpl;
import com.android.systemui.controls.util.SALogger;
import com.android.systemui.qs.bar.MediaDevicesBar;
import com.android.systemui.settings.UserContextProvider;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.settings.SecureSettings;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptySet;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class SecDeviceControlsControllerImpl implements SecDeviceControlsController {
    public static final /* synthetic */ int $r8$clinit = 0;
    public MediaDevicesBar.AnonymousClass1 callback;
    public final Context context;
    public final ControlsActivityStarter controlsActivityStarter;
    public final ControlsComponent controlsComponent;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final SecDeviceControlsControllerImpl$listingCallback$1 listingCallback = new ControlsListingController.ControlsListingCallback() { // from class: com.android.systemui.controls.controller.SecDeviceControlsControllerImpl$listingCallback$1
        @Override // com.android.systemui.controls.management.ControlsListingController.ControlsListingCallback
        public final void onServicesUpdated(List list) {
            Log.d("SecDeviceControlsControllerImpl", "onServicesUpdated serviceInfos = " + Integer.valueOf(((ArrayList) list).size()) + ", isNotEmpty = " + (!list.isEmpty()));
            if (!list.isEmpty()) {
                final SecDeviceControlsControllerImpl secDeviceControlsControllerImpl = SecDeviceControlsControllerImpl.this;
                String[] stringArray = secDeviceControlsControllerImpl.context.getResources().getStringArray(R.array.config_controlsPreferredPackages);
                int i = 0;
                final SharedPreferences sharedPreferences = ((UserTrackerImpl) secDeviceControlsControllerImpl.userContextProvider).getUserContext().getSharedPreferences(SystemUIAnalytics.CONTROL_PREF_NAME, 0);
                Set<String> stringSet = sharedPreferences.getStringSet("SeedingCompleted", EmptySet.INSTANCE);
                if (stringSet == null) {
                    return;
                }
                Log.d("SecDeviceControlsControllerImpl", "seedFavorites seededPackages = " + stringSet + ", preferredControlsPackages = " + ArraysKt___ArraysKt.toList(stringArray));
                ControlsController controlsController = (ControlsController) secDeviceControlsControllerImpl.controlsComponent.controlsController.get();
                ArrayList arrayList = new ArrayList();
                while (true) {
                    int length = stringArray.length;
                    if (2 <= length) {
                        length = 2;
                    }
                    if (i >= length) {
                        break;
                    }
                    String str = stringArray[i];
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        ControlsServiceInfo controlsServiceInfo = (ControlsServiceInfo) it.next();
                        Log.d("SecDeviceControlsControllerImpl", "seedFavorites service = " + controlsServiceInfo);
                        if (str.equals(controlsServiceInfo.componentName.getPackageName()) && !stringSet.contains(str)) {
                            ComponentName componentName = controlsServiceInfo.componentName;
                            ((ControlsControllerImpl) controlsController).getClass();
                            Favorites.INSTANCE.getClass();
                            if (((ArrayList) Favorites.getControlsForComponent(componentName)).size() > 0) {
                                SecDeviceControlsControllerImpl.addPackageToSeededSet(sharedPreferences, str);
                            } else if (controlsServiceInfo.panelActivity != null) {
                                SecDeviceControlsControllerImpl.addPackageToSeededSet(sharedPreferences, str);
                            } else {
                                arrayList.add(controlsServiceInfo.componentName);
                            }
                        }
                    }
                    i++;
                }
                Log.d("SecDeviceControlsControllerImpl", "seedFavorites componentsToSeed = " + arrayList);
                if (arrayList.isEmpty()) {
                    return;
                }
                ((ControlsControllerImpl) controlsController).seedFavoritesForComponents(arrayList, new Consumer() { // from class: com.android.systemui.controls.controller.SecDeviceControlsControllerImpl$seedFavorites$2
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        SeedResponse seedResponse = (SeedResponse) obj;
                        Log.d("SecDeviceControlsControllerImpl", "Controls seeded: " + seedResponse);
                        if (seedResponse.accepted) {
                            SecDeviceControlsControllerImpl secDeviceControlsControllerImpl2 = SecDeviceControlsControllerImpl.this;
                            SharedPreferences sharedPreferences2 = sharedPreferences;
                            int i2 = SecDeviceControlsControllerImpl.$r8$clinit;
                            secDeviceControlsControllerImpl2.getClass();
                            SecDeviceControlsControllerImpl.addPackageToSeededSet(sharedPreferences2, seedResponse.packageName);
                            SecDeviceControlsControllerImpl secDeviceControlsControllerImpl3 = SecDeviceControlsControllerImpl.this;
                            secDeviceControlsControllerImpl3.getClass();
                            Log.i("SecDeviceControlsControllerImpl", "fireControlsUpdate()");
                            MediaDevicesBar.AnonymousClass1 anonymousClass1 = secDeviceControlsControllerImpl3.callback;
                            if (anonymousClass1 != null) {
                                SecDeviceControlsControllerImpl secDeviceControlsControllerImpl4 = (SecDeviceControlsControllerImpl) MediaDevicesBar.this.mSecDeviceControlsController;
                                secDeviceControlsControllerImpl4.callback = null;
                                secDeviceControlsControllerImpl4.controlsComponent.controlsListingController.ifPresent(new SecDeviceControlsControllerImpl$removeCallback$1(secDeviceControlsControllerImpl4));
                            }
                            final SecDeviceControlsControllerImpl secDeviceControlsControllerImpl5 = SecDeviceControlsControllerImpl.this;
                            secDeviceControlsControllerImpl5.controlsComponent.controlsListingController.ifPresent(new Consumer() { // from class: com.android.systemui.controls.controller.SecDeviceControlsControllerImpl$seedFavorites$2.1
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj2) {
                                    ((ControlsListingControllerImpl) ((ControlsListingController) obj2)).removeCallback(SecDeviceControlsControllerImpl.this.listingCallback);
                                }
                            });
                        }
                    }
                });
            }
        }
    };
    public final SALogger saLogger;
    public final SecureSettings secureSettings;
    public final UserContextProvider userContextProvider;

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

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.controls.controller.SecDeviceControlsControllerImpl$listingCallback$1] */
    public SecDeviceControlsControllerImpl(Context context, ControlsComponent controlsComponent, UserContextProvider userContextProvider, SecureSettings secureSettings, ControlsActivityStarter controlsActivityStarter, SALogger sALogger, KeyguardUpdateMonitor keyguardUpdateMonitor) {
        this.context = context;
        this.controlsComponent = controlsComponent;
        this.userContextProvider = userContextProvider;
        this.secureSettings = secureSettings;
        this.controlsActivityStarter = controlsActivityStarter;
        this.saLogger = sALogger;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
    }

    public static Unit addPackageToSeededSet(SharedPreferences sharedPreferences, String str) {
        Set<String> stringSet = sharedPreferences.getStringSet("SeedingCompleted", EmptySet.INSTANCE);
        if (stringSet == null) {
            return null;
        }
        Set<String> mutableSet = CollectionsKt___CollectionsKt.toMutableSet(stringSet);
        mutableSet.add(str);
        sharedPreferences.edit().putStringSet("SeedingCompleted", mutableSet).apply();
        return Unit.INSTANCE;
    }

    public final void start(ActivityTransitionAnimator.Controller controller) {
        Log.d("SecDeviceControlsControllerImpl", "start controller = " + controller);
        if (BasicRune.CONTROLS_BLOCK_START_BEFORE_SECURE_BOOT_UNLOCK && !this.keyguardUpdateMonitor.isUserUnlocked()) {
            Log.i("SecDeviceControlsControllerImpl", "Not user unlocked yet");
            return;
        }
        this.saLogger.sendEvent(SALogger.Event.LaunchDevices.INSTANCE);
        ((ControlsActivityStarterImpl) this.controlsActivityStarter).startSecControlsActivity(this.context, controller);
    }
}
