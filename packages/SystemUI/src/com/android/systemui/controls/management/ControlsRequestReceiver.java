package com.android.systemui.controls.management;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.UserHandle;
import android.service.controls.Control;
import android.util.Log;
import com.android.systemui.controls.controller.ControlInfo;
import com.android.systemui.controls.controller.ControlsController;
import com.android.systemui.controls.controller.ControlsControllerImpl;
import com.android.systemui.controls.controller.Favorites;
import com.android.systemui.controls.controller.SecControlsController;
import com.android.systemui.controls.controller.StructureInfo;
import com.android.systemui.controls.ui.SecControlsUiController;
import com.android.systemui.controls.ui.SecControlsUiControllerImpl;
import com.android.systemui.controls.ui.fragment.MainFragment;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class ControlsRequestReceiver extends BroadcastReceiver {
    public static final Companion Companion = new Companion(null);
    public final ControlsController controller;
    public final Handler handler;
    public final SecControlsController secController;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public ControlsRequestReceiver() {
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x008f  */
    /* JADX WARN: Removed duplicated region for block: B:50:? A[RETURN, SYNTHETIC] */
    @Override // android.content.BroadcastReceiver
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onReceive(Context context, final Intent intent) throws PackageManager.NameNotFoundException {
        boolean z;
        int packageUid;
        ActivityManager activityManager;
        if (!context.getPackageManager().hasSystemFeature("android.software.controls")) {
            return;
        }
        try {
            final ComponentName componentName = (ComponentName) intent.getParcelableExtra("android.intent.extra.COMPONENT_NAME", ComponentName.class);
            if (componentName == null) {
                Log.e("ControlsRequestReceiver", "Null target component");
                return;
            }
            try {
                final Control control = (Control) intent.getParcelableExtra("android.service.controls.extra.CONTROL", Control.class);
                if (control == null) {
                    Log.e("ControlsRequestReceiver", "Null control");
                    return;
                }
                String packageName = componentName.getPackageName();
                Companion.getClass();
                try {
                    packageUid = context.getPackageManager().getPackageUid(packageName, 0);
                    activityManager = (ActivityManager) context.getSystemService(ActivityManager.class);
                } catch (PackageManager.NameNotFoundException unused) {
                    Log.w("ControlsRequestReceiver", "Package " + packageName + " not found");
                }
                if ((activityManager != null ? activityManager.getUidImportance(packageUid) : 1000) != 100) {
                    Log.w("ControlsRequestReceiver", "Uid " + packageUid + " not in foreground");
                    z = false;
                    if (z) {
                        return;
                    }
                    if (intent.getBooleanExtra("android.service.controls.extra.CONTROL_AUTO_ADD", false)) {
                        Handler handler = this.handler;
                        if (handler != null) {
                            handler.post(new Runnable() { // from class: com.android.systemui.controls.management.ControlsRequestReceiver.onReceive.1
                                /* JADX WARN: Removed duplicated region for block: B:4:0x001b  */
                                @Override // java.lang.Runnable
                                /*
                                    Code decompiled incorrectly, please refer to instructions dump.
                                */
                                public final void run() {
                                    ArrayList parcelableArrayListExtra;
                                    ControlsRequestReceiver controlsRequestReceiver = ControlsRequestReceiver.this;
                                    final ComponentName componentName2 = componentName;
                                    Control control2 = control;
                                    Intent intent2 = intent;
                                    Companion companion = ControlsRequestReceiver.Companion;
                                    controlsRequestReceiver.getClass();
                                    ArrayList arrayList = null;
                                    int i = 0;
                                    if (intent2.hasExtra("android.service.controls.extra.CONTROLS")) {
                                        try {
                                            parcelableArrayListExtra = intent2.getParcelableArrayListExtra("android.service.controls.extra.CONTROLS");
                                            if (parcelableArrayListExtra != null) {
                                                ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(parcelableArrayListExtra, 10));
                                                int size = parcelableArrayListExtra.size();
                                                int i2 = 0;
                                                while (i2 < size) {
                                                    Object obj = parcelableArrayListExtra.get(i2);
                                                    i2++;
                                                    arrayList2.add(((Control) obj).getControlId());
                                                }
                                                Log.d("ControlsRequestReceiver", "autoAddList: " + arrayList2);
                                            }
                                        } catch (ClassCastException e) {
                                            Log.e("ControlsRequestReceiver", "Malformed intent extra Controls", e);
                                        }
                                    } else {
                                        parcelableArrayListExtra = null;
                                    }
                                    if (parcelableArrayListExtra != null) {
                                        arrayList = parcelableArrayListExtra;
                                    } else if (control2 != null) {
                                        Log.d("ControlsRequestReceiver", "autoAdd: " + control2.getControlId());
                                        arrayList = new ArrayList();
                                        arrayList.add(control2);
                                    }
                                    if (arrayList == null) {
                                        Log.e("ControlsRequestReceiver", "Request did not contain control(s)");
                                        return;
                                    }
                                    if (controlsRequestReceiver.controller != null) {
                                        Favorites.INSTANCE.getClass();
                                        List structuresForComponent = Favorites.getStructuresForComponent(componentName2);
                                        if (structuresForComponent != null) {
                                            final ArrayList arrayList3 = new ArrayList();
                                            int size2 = arrayList.size();
                                            int i3 = 0;
                                            while (i3 < size2) {
                                                Object obj2 = arrayList.get(i3);
                                                i3++;
                                                Control control3 = (Control) obj2;
                                                List list = structuresForComponent;
                                                if (!(list instanceof Collection) || !list.isEmpty()) {
                                                    Iterator it = list.iterator();
                                                    while (it.hasNext()) {
                                                        List list2 = ((StructureInfo) it.next()).controls;
                                                        if (!(list2 instanceof Collection) || !list2.isEmpty()) {
                                                            Iterator it2 = list2.iterator();
                                                            while (it2.hasNext()) {
                                                                if (Intrinsics.areEqual(((ControlInfo) it2.next()).controlId, control3.getControlId())) {
                                                                    break;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                arrayList3.add(obj2);
                                            }
                                            ArrayList arrayList4 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList3, 10));
                                            int size3 = arrayList3.size();
                                            while (i < size3) {
                                                Object obj3 = arrayList3.get(i);
                                                i++;
                                                arrayList4.add(((Control) obj3).getControlId());
                                            }
                                            List listMinus = CollectionsKt___CollectionsKt.minus((Iterable) arrayList, (Iterable) arrayList3);
                                            ArrayList arrayList5 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(listMinus, 10));
                                            Iterator it3 = listMinus.iterator();
                                            while (it3.hasNext()) {
                                                arrayList5.add(((Control) it3.next()).getControlId());
                                            }
                                            Log.d("ControlsRequestReceiver", "add newControls = " + arrayList4 + ", already added Controls = " + arrayList5);
                                            SecControlsController secControlsController = controlsRequestReceiver.secController;
                                            if (secControlsController != null) {
                                                final ControlsControllerImpl controlsControllerImpl = (ControlsControllerImpl) secControlsController;
                                                if (controlsControllerImpl.confirmAvailability()) {
                                                    controlsControllerImpl.executor.execute(new Runnable() { // from class: com.android.systemui.controls.controller.ControlsControllerImpl$addFavorites$1
                                                        @Override // java.lang.Runnable
                                                        public final void run() {
                                                            Favorites favorites = Favorites.INSTANCE;
                                                            ComponentName componentName3 = componentName2;
                                                            ArrayList arrayList6 = arrayList3;
                                                            favorites.getClass();
                                                            if (Favorites.addFavorites(componentName3, arrayList6)) {
                                                                controlsControllerImpl.persistenceWrapper.storeFavorites(Favorites.getAllStructures());
                                                                ((ArrayList) controlsControllerImpl.autoAddList).addAll(arrayList3);
                                                                SecControlsUiController secControlsUiController = controlsControllerImpl.secUiController;
                                                                final ComponentName componentName4 = componentName2;
                                                                final SecControlsUiControllerImpl secControlsUiControllerImpl = (SecControlsUiControllerImpl) secControlsUiController;
                                                                MainFragment mainFragment = secControlsUiControllerImpl.mainFragment;
                                                                if (mainFragment == null || mainFragment.mState < 7) {
                                                                    Log.w("SecControlsUiControllerImpl", "notifyToUpdateComponent - ignore");
                                                                } else {
                                                                    secControlsUiControllerImpl.uiExecutor.execute(new Runnable() { // from class: com.android.systemui.controls.ui.SecControlsUiControllerImpl$notifyToUpdateComponent$1
                                                                        @Override // java.lang.Runnable
                                                                        public final void run() {
                                                                            SecControlsUiControllerImpl secControlsUiControllerImpl2 = secControlsUiControllerImpl;
                                                                            int i4 = SecControlsUiControllerImpl.$r8$clinit;
                                                                            secControlsUiControllerImpl2.loadComponentInfo();
                                                                            SecControlsUiControllerImpl secControlsUiControllerImpl3 = secControlsUiControllerImpl;
                                                                            SelectedItem selectedItem = secControlsUiControllerImpl3.selectedItem;
                                                                            ComponentName componentName5 = componentName4;
                                                                            if (Intrinsics.areEqual(selectedItem.getComponentName(), componentName5)) {
                                                                                SecControlsUiControllerImpl.access$reload(secControlsUiControllerImpl3, selectedItem);
                                                                                return;
                                                                            }
                                                                            Log.w("SecControlsUiControllerImpl", "notifyToUpdateComponent-Skip reload selectedCompInfo: " + selectedItem + ", updateComp: " + componentName5);
                                                                        }
                                                                    });
                                                                }
                                                            }
                                                        }
                                                    });
                                                }
                                            }
                                        }
                                    }
                                }
                            });
                            return;
                        } else {
                            Log.e("ControlsRequestReceiver", "onReceive handler is null");
                            return;
                        }
                    }
                    Intent intent2 = new Intent(context, (Class<?>) ControlsRequestDialog.class);
                    intent2.putExtra("android.intent.extra.COMPONENT_NAME", componentName);
                    intent2.putExtra("android.service.controls.extra.CONTROL", control);
                    intent2.addFlags(268566528);
                    intent2.putExtra("android.intent.extra.USER_ID", context.getUserId());
                    context.startActivityAsUser(intent2, UserHandle.SYSTEM);
                    return;
                }
                z = true;
                if (z) {
                }
            } catch (Exception e) {
                Log.e("ControlsRequestReceiver", "Malformed intent extra Control", e);
            }
        } catch (Exception e2) {
            Log.e("ControlsRequestReceiver", "Malformed intent extra ComponentName", e2);
        }
    }

    public ControlsRequestReceiver(ControlsController controlsController, SecControlsController secControlsController, Handler handler) {
        this();
        this.controller = controlsController;
        this.secController = secControlsController;
        this.handler = handler;
    }
}
