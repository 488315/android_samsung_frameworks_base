package com.android.systemui.controls.controller;

import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.android.systemui.BasicRune;
import com.android.systemui.R;
import com.android.systemui.controls.ControlsServiceInfo;
import com.android.systemui.controls.dagger.ControlsComponent;
import com.android.systemui.controls.management.ControlsListingController;
import com.android.systemui.controls.management.ControlsListingControllerImpl;
import com.android.systemui.controls.ui.util.ControlsActivityStarter;
import com.android.systemui.controls.ui.util.ControlsActivityStarterImpl;
import com.android.systemui.controls.ui.util.SALogger;
import com.android.systemui.qs.bar.MediaDevicesBar$$ExternalSyntheticLambda1;
import com.android.systemui.settings.UserContextProvider;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.util.settings.SecureSettings;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import kotlin.Unit;
import kotlin.collections.EmptySet;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class CustomDeviceControlsControllerImpl implements CustomDeviceControlsController {
    public static final /* synthetic */ int $r8$clinit = 0;
    public MediaDevicesBar$$ExternalSyntheticLambda1 callback;
    public final Context context;
    public final ControlsActivityStarter controlsActivityStarter;
    public final ControlsComponent controlsComponent;
    public final CustomDeviceControlsControllerImpl$listingCallback$1 listingCallback = new ControlsListingController.ControlsListingCallback() { // from class: com.android.systemui.controls.controller.CustomDeviceControlsControllerImpl$listingCallback$1
        @Override // com.android.systemui.controls.management.ControlsListingController.ControlsListingCallback
        public final void onServicesUpdated(List list) {
            ArrayList arrayList = (ArrayList) list;
            Log.d("CustomDeviceControlsControllerImpl", "onServicesUpdated serviceInfos = " + Integer.valueOf(arrayList.size()));
            if (!arrayList.isEmpty()) {
                final CustomDeviceControlsControllerImpl customDeviceControlsControllerImpl = CustomDeviceControlsControllerImpl.this;
                String[] stringArray = customDeviceControlsControllerImpl.context.getResources().getStringArray(R.array.config_controlsPreferredPackages);
                int i = 0;
                final SharedPreferences sharedPreferences = ((UserTrackerImpl) customDeviceControlsControllerImpl.userContextProvider).getUserContext().getSharedPreferences("controls_prefs", 0);
                Set<String> stringSet = sharedPreferences.getStringSet("SeedingCompleted", EmptySet.INSTANCE);
                if (stringSet == null) {
                    return;
                }
                Log.d("CustomDeviceControlsControllerImpl", "seedFavorites()");
                ControlsController controlsController = (ControlsController) customDeviceControlsControllerImpl.controlsComponent.getControlsController().get();
                ArrayList arrayList2 = new ArrayList();
                while (true) {
                    int length = stringArray.length;
                    if (2 <= length) {
                        length = 2;
                    }
                    if (i >= length) {
                        break;
                    }
                    String str = stringArray[i];
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        ControlsServiceInfo controlsServiceInfo = (ControlsServiceInfo) it.next();
                        if (str.equals(controlsServiceInfo.componentName.getPackageName()) && !stringSet.contains(str)) {
                            ((ControlsControllerImpl) controlsController).getClass();
                            Favorites.INSTANCE.getClass();
                            ComponentName componentName = controlsServiceInfo.componentName;
                            if (((ArrayList) Favorites.getControlsForComponent(componentName)).size() > 0) {
                                CustomDeviceControlsControllerImpl.addPackageToSeededSet(sharedPreferences, str);
                            } else if (controlsServiceInfo.panelActivity != null) {
                                CustomDeviceControlsControllerImpl.addPackageToSeededSet(sharedPreferences, str);
                            } else {
                                arrayList2.add(componentName);
                            }
                        }
                    }
                    i++;
                }
                if (arrayList2.isEmpty()) {
                    return;
                }
                ((ControlsControllerImpl) controlsController).seedFavoritesForComponents(arrayList2, new Consumer() { // from class: com.android.systemui.controls.controller.CustomDeviceControlsControllerImpl$seedFavorites$2
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        SeedResponse seedResponse = (SeedResponse) obj;
                        Log.d("CustomDeviceControlsControllerImpl", "Controls seeded: " + seedResponse);
                        if (seedResponse.accepted) {
                            CustomDeviceControlsControllerImpl customDeviceControlsControllerImpl2 = CustomDeviceControlsControllerImpl.this;
                            SharedPreferences sharedPreferences2 = sharedPreferences;
                            int i2 = CustomDeviceControlsControllerImpl.$r8$clinit;
                            customDeviceControlsControllerImpl2.getClass();
                            CustomDeviceControlsControllerImpl.addPackageToSeededSet(sharedPreferences2, seedResponse.packageName);
                            CustomDeviceControlsControllerImpl customDeviceControlsControllerImpl3 = CustomDeviceControlsControllerImpl.this;
                            customDeviceControlsControllerImpl3.getClass();
                            Log.i("CustomDeviceControlsControllerImpl", "fireControlsUpdate()");
                            MediaDevicesBar$$ExternalSyntheticLambda1 mediaDevicesBar$$ExternalSyntheticLambda1 = customDeviceControlsControllerImpl3.callback;
                            if (mediaDevicesBar$$ExternalSyntheticLambda1 != null) {
                                CustomDeviceControlsControllerImpl customDeviceControlsControllerImpl4 = (CustomDeviceControlsControllerImpl) mediaDevicesBar$$ExternalSyntheticLambda1.f$0;
                                customDeviceControlsControllerImpl4.getClass();
                                Log.i("CustomDeviceControlsControllerImpl", "removeCallback()");
                                customDeviceControlsControllerImpl4.callback = null;
                                customDeviceControlsControllerImpl4.controlsComponent.getControlsListingController().ifPresent(new CustomDeviceControlsControllerImpl$removeCallback$1(customDeviceControlsControllerImpl4));
                            }
                            Optional controlsListingController = CustomDeviceControlsControllerImpl.this.controlsComponent.getControlsListingController();
                            final CustomDeviceControlsControllerImpl customDeviceControlsControllerImpl5 = CustomDeviceControlsControllerImpl.this;
                            controlsListingController.ifPresent(new Consumer() { // from class: com.android.systemui.controls.controller.CustomDeviceControlsControllerImpl$seedFavorites$2.1
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj2) {
                                    ((ControlsListingControllerImpl) ((ControlsListingController) obj2)).removeCallback(CustomDeviceControlsControllerImpl.this.listingCallback);
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

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.controls.controller.CustomDeviceControlsControllerImpl$listingCallback$1] */
    public CustomDeviceControlsControllerImpl(Context context, ControlsActivityStarter controlsActivityStarter, ControlsComponent controlsComponent, SecureSettings secureSettings, UserContextProvider userContextProvider, SALogger sALogger) {
        this.context = context;
        this.controlsActivityStarter = controlsActivityStarter;
        this.controlsComponent = controlsComponent;
        this.secureSettings = secureSettings;
        this.userContextProvider = userContextProvider;
        this.saLogger = sALogger;
    }

    public static Unit addPackageToSeededSet(SharedPreferences sharedPreferences, String str) {
        Set<String> stringSet = sharedPreferences.getStringSet("SeedingCompleted", EmptySet.INSTANCE);
        if (stringSet == null) {
            return null;
        }
        stringSet.add(str);
        sharedPreferences.edit().putStringSet("SeedingCompleted", stringSet).apply();
        return Unit.INSTANCE;
    }

    public final void start() {
        Log.d("CustomDeviceControlsControllerImpl", "start()");
        if (BasicRune.CONTROLS_SAMSUNG_ANALYTICS) {
            this.saLogger.sendEvent(SALogger.Event.LaunchDevices.INSTANCE);
        }
        ((ControlsActivityStarterImpl) this.controlsActivityStarter).startCustomControlsActivity(this.context);
    }
}
