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
import com.android.systemui.BasicRune;
import com.android.systemui.controls.controller.ControlInfo;
import com.android.systemui.controls.controller.ControlsController;
import com.android.systemui.controls.controller.ControlsControllerImpl;
import com.android.systemui.controls.controller.CustomControlsController;
import com.android.systemui.controls.controller.Favorites;
import com.android.systemui.controls.controller.StructureInfo;
import com.android.systemui.controls.management.ControlsRequestReceiver;
import com.android.systemui.controls.ui.CustomControlsUiController;
import com.android.systemui.controls.ui.CustomControlsUiControllerImpl;
import com.android.systemui.controls.ui.fragment.MainFragment;
import com.android.systemui.util.concurrency.ExecutorImpl;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ControlsRequestReceiver extends BroadcastReceiver {
    public static final Companion Companion = new Companion(null);
    public final ControlsController controller;
    public final CustomControlsController customController;
    public final Handler handler;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public ControlsRequestReceiver() {
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, final Intent intent) {
        if (context.getPackageManager().hasSystemFeature("android.software.controls")) {
            try {
                final ComponentName componentName = (ComponentName) intent.getParcelableExtra("android.intent.extra.COMPONENT_NAME");
                try {
                    final Control control = (Control) intent.getParcelableExtra("android.service.controls.extra.CONTROL");
                    String packageName = componentName != null ? componentName.getPackageName() : null;
                    boolean z = false;
                    if (BasicRune.CONTROLS_AUTO_ADD && intent.getBooleanExtra("android.service.controls.extra.CONTROL_AUTO_ADD", false)) {
                        if (packageName == null) {
                            return;
                        }
                        Handler handler = this.handler;
                        if (handler != null) {
                            handler.post(new Runnable() { // from class: com.android.systemui.controls.management.ControlsRequestReceiver$onReceive$1
                                /* JADX WARN: Removed duplicated region for block: B:11:0x0087  */
                                /* JADX WARN: Removed duplicated region for block: B:5:0x005d  */
                                /* JADX WARN: Removed duplicated region for block: B:71:0x007d  */
                                /* JADX WARN: Removed duplicated region for block: B:8:0x0080  */
                                @Override // java.lang.Runnable
                                /*
                                    Code decompiled incorrectly, please refer to instructions dump.
                                */
                                public final void run() {
                                    ArrayList parcelableArrayListExtra;
                                    boolean z2;
                                    ControlsRequestReceiver controlsRequestReceiver = ControlsRequestReceiver.this;
                                    final ComponentName componentName2 = componentName;
                                    Control control2 = control;
                                    Intent intent2 = intent;
                                    ControlsRequestReceiver.Companion companion = ControlsRequestReceiver.Companion;
                                    controlsRequestReceiver.getClass();
                                    ArrayList arrayList = null;
                                    if (intent2.hasExtra("android.service.controls.extra.CONTROLS")) {
                                        try {
                                            parcelableArrayListExtra = intent2.getParcelableArrayListExtra("android.service.controls.extra.CONTROLS");
                                        } catch (ClassCastException e) {
                                            Log.e("ControlsRequestReceiver", "Malformed intent extra Controls", e);
                                        }
                                        if (parcelableArrayListExtra != null) {
                                            ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(parcelableArrayListExtra, 10));
                                            Iterator it = parcelableArrayListExtra.iterator();
                                            while (it.hasNext()) {
                                                arrayList2.add(((Control) it.next()).getControlId());
                                            }
                                            Log.d("ControlsRequestReceiver", "autoAddList: " + arrayList2);
                                            if (parcelableArrayListExtra == null) {
                                                arrayList = parcelableArrayListExtra;
                                            } else if (control2 != null) {
                                                Log.d("ControlsRequestReceiver", "autoAdd: " + control2.getControlId());
                                                arrayList = new ArrayList();
                                                arrayList.add(control2);
                                            }
                                            if (arrayList != null) {
                                                Log.e("ControlsRequestReceiver", "Request did not contain control(s)");
                                                return;
                                            }
                                            if (controlsRequestReceiver.controller != null) {
                                                Favorites.INSTANCE.getClass();
                                                List structuresForComponent = Favorites.getStructuresForComponent(componentName2);
                                                if (structuresForComponent != null) {
                                                    final ArrayList arrayList3 = new ArrayList();
                                                    for (Object obj : arrayList) {
                                                        Control control3 = (Control) obj;
                                                        boolean z3 = false;
                                                        if (!structuresForComponent.isEmpty()) {
                                                            Iterator it2 = structuresForComponent.iterator();
                                                            while (true) {
                                                                if (!it2.hasNext()) {
                                                                    break;
                                                                }
                                                                List list = ((StructureInfo) it2.next()).controls;
                                                                if (!(list instanceof Collection) || !list.isEmpty()) {
                                                                    Iterator it3 = list.iterator();
                                                                    while (it3.hasNext()) {
                                                                        if (Intrinsics.areEqual(((ControlInfo) it3.next()).controlId, control3.getControlId())) {
                                                                            z2 = true;
                                                                            break;
                                                                        }
                                                                    }
                                                                }
                                                                z2 = false;
                                                                if (z2) {
                                                                    z3 = true;
                                                                    break;
                                                                }
                                                            }
                                                        }
                                                        if (!z3) {
                                                            arrayList3.add(obj);
                                                        }
                                                    }
                                                    ArrayList arrayList4 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList3, 10));
                                                    Iterator it4 = arrayList3.iterator();
                                                    while (it4.hasNext()) {
                                                        arrayList4.add(((Control) it4.next()).getControlId());
                                                    }
                                                    List minus = CollectionsKt___CollectionsKt.minus((Iterable) arrayList, (Iterable) arrayList3);
                                                    ArrayList arrayList5 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(minus, 10));
                                                    Iterator it5 = minus.iterator();
                                                    while (it5.hasNext()) {
                                                        arrayList5.add(((Control) it5.next()).getControlId());
                                                    }
                                                    Log.d("ControlsRequestReceiver", "add newControls=" + arrayList4 + ", already added Controls=" + arrayList5);
                                                    CustomControlsController customControlsController = controlsRequestReceiver.customController;
                                                    if (customControlsController != null) {
                                                        final ControlsControllerImpl controlsControllerImpl = (ControlsControllerImpl) customControlsController;
                                                        if (controlsControllerImpl.confirmAvailability()) {
                                                            ((ExecutorImpl) controlsControllerImpl.executor).execute(new Runnable() { // from class: com.android.systemui.controls.controller.ControlsControllerImpl$addFavorites$1
                                                                @Override // java.lang.Runnable
                                                                public final void run() {
                                                                    Favorites favorites = Favorites.INSTANCE;
                                                                    ComponentName componentName3 = componentName2;
                                                                    ArrayList arrayList6 = arrayList3;
                                                                    favorites.getClass();
                                                                    if (Favorites.addFavorites(componentName3, arrayList6)) {
                                                                        controlsControllerImpl.persistenceWrapper.storeFavorites(Favorites.getAllStructures());
                                                                        ((ArrayList) controlsControllerImpl.autoAddList).addAll(arrayList3);
                                                                        CustomControlsUiController customControlsUiController = controlsControllerImpl.customUiController;
                                                                        final ComponentName componentName4 = componentName2;
                                                                        final CustomControlsUiControllerImpl customControlsUiControllerImpl = (CustomControlsUiControllerImpl) customControlsUiController;
                                                                        MainFragment mainFragment = customControlsUiControllerImpl.mainFragment;
                                                                        if (mainFragment != null && mainFragment.mState >= 7) {
                                                                            ((ExecutorImpl) customControlsUiControllerImpl.uiExecutor).execute(new Runnable() { // from class: com.android.systemui.controls.ui.CustomControlsUiControllerImpl$notifyToUpdateComponent$1
                                                                                @Override // java.lang.Runnable
                                                                                public final void run() {
                                                                                    CustomControlsUiControllerImpl customControlsUiControllerImpl2 = CustomControlsUiControllerImpl.this;
                                                                                    int i = CustomControlsUiControllerImpl.$r8$clinit;
                                                                                    customControlsUiControllerImpl2.loadComponentInfo();
                                                                                    CustomControlsUiControllerImpl customControlsUiControllerImpl3 = CustomControlsUiControllerImpl.this;
                                                                                    SelectedItem selectedItem = customControlsUiControllerImpl3.selectedItem;
                                                                                    ComponentName componentName5 = componentName4;
                                                                                    if (Intrinsics.areEqual(selectedItem.getComponentName(), componentName5)) {
                                                                                        CustomControlsUiControllerImpl.access$reload(customControlsUiControllerImpl3, selectedItem);
                                                                                        return;
                                                                                    }
                                                                                    Log.w("CustomControlsUiControllerImpl", "notifyToUpdateComponent-Skip reload selectedCompInfo: " + selectedItem + ", updateComp: " + componentName5);
                                                                                }
                                                                            });
                                                                        } else {
                                                                            Log.w("CustomControlsUiControllerImpl", "notifyToUpdateComponent - ignore");
                                                                        }
                                                                    }
                                                                }
                                                            });
                                                            return;
                                                        }
                                                        return;
                                                    }
                                                    return;
                                                }
                                                return;
                                            }
                                            return;
                                        }
                                    }
                                    parcelableArrayListExtra = null;
                                    if (parcelableArrayListExtra == null) {
                                    }
                                    if (arrayList != null) {
                                    }
                                }
                            });
                            return;
                        } else {
                            Log.e("ControlsRequestReceiver", "onReceive handler is null");
                            return;
                        }
                    }
                    if (packageName != null) {
                        Companion.getClass();
                        try {
                            int packageUid = context.getPackageManager().getPackageUid(packageName, 0);
                            ActivityManager activityManager = (ActivityManager) context.getSystemService(ActivityManager.class);
                            if ((activityManager != null ? activityManager.getUidImportance(packageUid) : 1000) != 100) {
                                Log.w("ControlsRequestReceiver", "Uid " + packageUid + " not in foreground");
                            } else {
                                z = true;
                            }
                        } catch (PackageManager.NameNotFoundException unused) {
                            Log.w("ControlsRequestReceiver", "Package " + packageName + " not found");
                        }
                        if (z) {
                            Intent intent2 = new Intent(context, (Class<?>) ControlsRequestDialog.class);
                            intent2.putExtra("android.intent.extra.COMPONENT_NAME", componentName);
                            intent2.putExtra("android.service.controls.extra.CONTROL", control);
                            intent2.addFlags(268566528);
                            intent2.putExtra("android.intent.extra.USER_ID", context.getUserId());
                            context.startActivityAsUser(intent2, UserHandle.SYSTEM);
                        }
                    }
                } catch (ClassCastException e) {
                    Log.e("ControlsRequestReceiver", "Malformed intent extra Control", e);
                }
            } catch (ClassCastException e2) {
                Log.e("ControlsRequestReceiver", "Malformed intent extra ComponentName", e2);
            }
        }
    }

    public ControlsRequestReceiver(ControlsController controlsController, CustomControlsController customControlsController, Handler handler) {
        this();
        this.controller = controlsController;
        this.customController = customControlsController;
        this.handler = handler;
    }
}
