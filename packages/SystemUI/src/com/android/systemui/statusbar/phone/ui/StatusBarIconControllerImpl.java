package com.android.systemui.statusbar.phone.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.view.ViewGroup;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.internal.statusbar.StatusBarIcon;
import com.android.systemui.BasicRune;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.demomode.DemoMode;
import com.android.systemui.demomode.DemoModeController;
import com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.StatusIconDisplayable;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.IndicatorScaleGardener;
import com.android.systemui.statusbar.phone.StatusBarIconHolder;
import com.android.systemui.statusbar.phone.StatusBarLocation;
import com.android.systemui.statusbar.phone.ui.StatusBarIconList;
import com.android.systemui.statusbar.pipeline.StatusBarPipelineFlags;
import com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator;
import com.android.systemui.statusbar.pipeline.icons.shared.BindableIconsRegistry;
import com.android.systemui.statusbar.pipeline.icons.shared.BindableIconsRegistryImpl;
import com.android.systemui.statusbar.pipeline.icons.shared.model.BindableIcon;
import com.android.systemui.statusbar.pipeline.mobile.data.model.SubscriptionsOrder;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.tuner.TunerService;
import com.sec.ims.IMSParameter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class StatusBarIconControllerImpl implements TunerService.Tunable, ConfigurationController.ConfigurationListener, Dumpable, StatusBarIconController, DemoMode {
    protected static final String EXTERNAL_SLOT_SUFFIX = "__external";
    public final CarrierInfraMediator mCarrierInfraMediator;
    public final AnonymousClass2 mCommandQueueCallbacks;
    public final Context mContext;
    public final ArrayList mIconGroups = new ArrayList();
    public final ArraySet mIconHideList = new ArraySet();
    public final IndicatorScaleGardener mIndicatorScaleGardener;
    public final StatusBarIconList mStatusBarIconList;
    public final StatusBarPipelineFlags mStatusBarPipelineFlags;
    public final SubscriptionsOrder mSubscriptionsOrder;
    public final ArrayList mSystemIconsAllowList;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.systemui.statusbar.CommandQueue$Callbacks, com.android.systemui.statusbar.phone.ui.StatusBarIconControllerImpl$2] */
    public StatusBarIconControllerImpl(Context context, CommandQueue commandQueue, DemoModeController demoModeController, ConfigurationController configurationController, TunerService tunerService, DumpManager dumpManager, StatusBarIconList statusBarIconList, StatusBarPipelineFlags statusBarPipelineFlags, BindableIconsRegistry bindableIconsRegistry, BroadcastDispatcher broadcastDispatcher, CarrierInfraMediator carrierInfraMediator, IndicatorScaleGardener indicatorScaleGardener, SubscriptionsOrder subscriptionsOrder) {
        ArrayList arrayList = new ArrayList();
        this.mSystemIconsAllowList = arrayList;
        ?? r1 = new CommandQueue.Callbacks() { // from class: com.android.systemui.statusbar.phone.ui.StatusBarIconControllerImpl.2
            @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
            public final void removeIcon(String str) {
                String str2 = StatusBarIconControllerImpl.EXTERNAL_SLOT_SUFFIX;
                StatusBarIconControllerImpl.this.removeAllIconsForSlot(str);
            }

            @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
            public final void setIcon(String str, StatusBarIcon statusBarIcon) {
                String str2 = StatusBarIconControllerImpl.EXTERNAL_SLOT_SUFFIX;
                StatusBarIconControllerImpl statusBarIconControllerImpl = StatusBarIconControllerImpl.this;
                if (statusBarIcon == null) {
                    statusBarIconControllerImpl.removeAllIconsForSlot(str);
                    return;
                }
                statusBarIconControllerImpl.getClass();
                StatusBarIconHolder.Companion.getClass();
                StatusBarIconHolder statusBarIconHolder = new StatusBarIconHolder(null);
                statusBarIconHolder.icon = statusBarIcon;
                statusBarIconControllerImpl.setIcon(str, statusBarIconHolder);
            }
        };
        this.mCommandQueueCallbacks = r1;
        this.mStatusBarIconList = statusBarIconList;
        this.mContext = context;
        this.mStatusBarPipelineFlags = statusBarPipelineFlags;
        this.mCarrierInfraMediator = carrierInfraMediator;
        arrayList.addAll(Arrays.asList(context.getResources().getStringArray(R.array.config_status_bar_system_icon_allowlist)));
        updateSystemIconsAllowList();
        broadcastDispatcher.registerReceiver(new IntentFilter("com.samsung.carrier.action.CARRIER_CHANGED"), new BroadcastReceiver() { // from class: com.android.systemui.statusbar.phone.ui.StatusBarIconControllerImpl.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                StatusBarIconControllerImpl statusBarIconControllerImpl = StatusBarIconControllerImpl.this;
                String str = StatusBarIconControllerImpl.EXTERNAL_SLOT_SUFFIX;
                boolean updateSystemIconsAllowList = statusBarIconControllerImpl.updateSystemIconsAllowList();
                StringBuilder m = RowView$$ExternalSyntheticOutline0.m("ACTION_CARRIER_CHANGED received. updateSystemIconsAllowList()?", " mSystemIconsAllowList:", updateSystemIconsAllowList);
                m.append(StatusBarIconControllerImpl.this.mSystemIconsAllowList);
                Log.d("StatusBarIconController", m.toString());
                if (updateSystemIconsAllowList) {
                    StatusBarIconControllerImpl.this.refreshIconGroups();
                }
            }
        });
        this.mIndicatorScaleGardener = indicatorScaleGardener;
        this.mSubscriptionsOrder = subscriptionsOrder;
        ((ConfigurationControllerImpl) configurationController).addCallback(this);
        commandQueue.addCallback((CommandQueue.Callbacks) r1);
        tunerService.addTunable(this, "icon_blacklist");
        demoModeController.addCallback((DemoMode) this);
        String simpleName = getClass().getSimpleName();
        dumpManager.getClass();
        DumpManager.registerDumpable$default(dumpManager, simpleName, this);
        for (BindableIcon bindableIcon : ((BindableIconsRegistryImpl) bindableIconsRegistry).bindableIcons) {
            if (bindableIcon.getShouldBindIcon()) {
                if (this.mStatusBarIconList.getIconHolder(0, bindableIcon.getSlot()) == null) {
                    setIcon(bindableIcon.getSlot(), new StatusBarIconHolder.BindableIconHolder(bindableIcon.getInitializer(), bindableIcon.getSlot()));
                } else {
                    Log.e("StatusBarIconController", "addBindableIcon called, but icon has already been added. Ignoring");
                }
            }
        }
    }

    public final void addIconGroup(IconManager iconManager) {
        ArrayList arrayList = this.mIconGroups;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            if (((IconManager) obj).mGroup == iconManager.mGroup) {
                Log.e("StatusBarIconController", "Adding new IconManager for the same ViewGroup. This could cause unexpected results.");
            }
        }
        IndicatorScaleGardener.ScaleModel latestScaleModel = this.mIndicatorScaleGardener.getLatestScaleModel(this.mContext);
        iconManager.mRatio = latestScaleModel.ratio;
        iconManager.mIconSize = latestScaleModel.iconSize;
        iconManager.mController = this;
        this.mIconGroups.add(iconManager);
        StatusBarIconList statusBarIconList = this.mStatusBarIconList;
        List list = statusBarIconList.mViewOnlySlots;
        for (int i2 = 0; i2 < list.size(); i2++) {
            StatusBarIconList.Slot slot = (StatusBarIconList.Slot) list.get(i2);
            List holderListInViewOrder = slot.getHolderListInViewOrder();
            ArraySet arraySet = this.mIconHideList;
            String str = slot.mName;
            boolean contains = arraySet.contains(str);
            if (!contains) {
                contains = hideBySimplification(iconManager, str);
            }
            ArrayList arrayList2 = (ArrayList) holderListInViewOrder;
            int size2 = arrayList2.size();
            int i3 = 0;
            while (i3 < size2) {
                Object obj2 = arrayList2.get(i3);
                i3++;
                StatusBarIconHolder statusBarIconHolder = (StatusBarIconHolder) obj2;
                iconManager.onIconAdded(statusBarIconList.getViewIndex(statusBarIconHolder.tag, str), str, contains, statusBarIconHolder);
            }
        }
    }

    @Override // com.android.systemui.demomode.DemoMode
    public final List demoCommands() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(IMSParameter.CALL.STATUS);
        return arrayList;
    }

    @Override // com.android.systemui.demomode.DemoModeCommandReceiver
    public final void dispatchDemoCommand(Bundle bundle, String str) {
        ArrayList arrayList = this.mIconGroups;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            IconManager iconManager = (IconManager) obj;
            if (iconManager.mDemoable) {
                iconManager.dispatchDemoCommand(bundle, str);
            }
        }
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("StatusBarIconController state:");
        ArrayList arrayList = this.mIconGroups;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            IconManager iconManager = (IconManager) obj;
            if (iconManager.mShouldLog) {
                ViewGroup viewGroup = iconManager.mGroup;
                int childCount = viewGroup.getChildCount();
                DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("  icon views: ", childCount, printWriter);
                for (int i2 = 0; i2 < childCount; i2++) {
                    printWriter.println("    [" + i2 + "] icon=" + ((StatusIconDisplayable) viewGroup.getChildAt(i2)));
                }
            }
        }
        StatusBarIconList statusBarIconList = this.mStatusBarIconList;
        statusBarIconList.getClass();
        printWriter.println("StatusBarIconList state:");
        int size2 = statusBarIconList.mSlots.size();
        DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("  icon slots: ", size2, printWriter);
        for (int i3 = 0; i3 < size2; i3++) {
            printWriter.printf("    %2d:%s\n", Integer.valueOf(i3), ((StatusBarIconList.Slot) statusBarIconList.mSlots.get(i3)).toString());
        }
    }

    public final void handleSet(String str, final StatusBarIconHolder statusBarIconHolder) {
        final int viewIndex = this.mStatusBarIconList.getViewIndex(statusBarIconHolder.tag, str);
        this.mIconGroups.forEach(new Consumer() { // from class: com.android.systemui.statusbar.phone.ui.StatusBarIconControllerImpl$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                int i = viewIndex;
                StatusBarIconHolder statusBarIconHolder2 = statusBarIconHolder;
                String str2 = StatusBarIconControllerImpl.EXTERNAL_SLOT_SUFFIX;
                ((IconManager) obj).onSetIconHolder(i, statusBarIconHolder2);
            }
        });
    }

    public final boolean hideBySimplification(IconManager iconManager, String str) {
        CarrierInfraMediator carrierInfraMediator;
        iconManager.getClass();
        StatusBarLocation statusBarLocation = StatusBarLocation.HOME;
        StatusBarLocation statusBarLocation2 = iconManager.mLocation;
        if ((statusBarLocation2 == statusBarLocation || statusBarLocation2 == StatusBarLocation.KEYGUARD) && (carrierInfraMediator = this.mCarrierInfraMediator) != null) {
            List list = (List) carrierInfraMediator.get(CarrierInfraMediator.Values.EXTRA_SYSTEM_ICON_LIST, 0, new Object[0]);
            if (!this.mSystemIconsAllowList.contains(str) && !list.contains(str)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.systemui.demomode.DemoModeCommandReceiver
    public final void onDemoModeFinished() {
        ArrayList arrayList = this.mIconGroups;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            IconManager iconManager = (IconManager) obj;
            if (iconManager.mDemoable) {
                iconManager.onDemoModeFinished();
            }
        }
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onDensityOrFontScaleChanged() {
        refreshIconGroups();
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onDisplayDeviceTypeChanged() {
        if (BasicRune.BASIC_FOLDABLE_TYPE_FOLD) {
            refreshIconGroups();
        }
    }

    @Override // com.android.systemui.tuner.TunerService.Tunable
    public final void onTuningChanged(String str, String str2) {
        if ("icon_blacklist".equals(str)) {
            this.mIconHideList.clear();
            this.mIconHideList.addAll(StatusBarIconController.getIconHideList(this.mContext, str2));
            List list = this.mStatusBarIconList.mViewOnlySlots;
            ArrayMap arrayMap = new ArrayMap();
            for (int size = list.size() - 1; size >= 0; size--) {
                StatusBarIconList.Slot slot = (StatusBarIconList.Slot) list.get(size);
                slot.getClass();
                ArrayList arrayList = new ArrayList();
                StatusBarIconHolder statusBarIconHolder = slot.mHolder;
                if (statusBarIconHolder != null) {
                    arrayList.add(statusBarIconHolder);
                }
                ArrayList arrayList2 = slot.mSubSlots;
                if (arrayList2 != null) {
                    arrayList.addAll(arrayList2);
                }
                arrayMap.put(slot, arrayList);
                removeAllIconsForSlot(slot.mName);
            }
            for (int i = 0; i < list.size(); i++) {
                StatusBarIconList.Slot slot2 = (StatusBarIconList.Slot) list.get(i);
                List list2 = (List) arrayMap.get(slot2);
                if (list2 != null) {
                    Iterator it = list2.iterator();
                    while (it.hasNext()) {
                        setIcon(slot2.mName, (StatusBarIconHolder) it.next());
                    }
                }
            }
        }
    }

    public final void refreshIconGroups() {
        for (int size = this.mIconGroups.size() - 1; size >= 0; size--) {
            IconManager iconManager = (IconManager) this.mIconGroups.get(size);
            removeIconGroup(iconManager);
            addIconGroup(iconManager);
        }
    }

    public final void removeAllIconsForSlot(String str) {
        if (this.mStatusBarPipelineFlags.isIconControlledByFlags(str)) {
            Log.i("StatusBarIconController", "Ignoring removal of (" + str + "). It should be controlled elsewhere");
            return;
        }
        StatusBarIconList statusBarIconList = this.mStatusBarIconList;
        StatusBarIconList.Slot slot = (StatusBarIconList.Slot) statusBarIconList.mSlots.get(statusBarIconList.findOrInsertSlot(str));
        if (slot.hasIconsInSlot()) {
            ArrayList arrayList = (ArrayList) slot.getHolderListInViewOrder();
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                StatusBarIconHolder statusBarIconHolder = (StatusBarIconHolder) obj;
                int viewIndex = statusBarIconList.getViewIndex(statusBarIconHolder.tag, str);
                slot.removeForTag(statusBarIconHolder.tag);
                this.mIconGroups.forEach(new StatusBarIconControllerImpl$$ExternalSyntheticLambda2(viewIndex, 0));
            }
        }
    }

    public final void removeIconGroup(IconManager iconManager) {
        iconManager.destroy();
        this.mIconGroups.remove(iconManager);
    }

    public final void removeUnusedIconsInSlot(String str, List list) {
        StatusBarIconList statusBarIconList = this.mStatusBarIconList;
        StatusBarIconList.Slot slot = (StatusBarIconList.Slot) statusBarIconList.mSlots.get(statusBarIconList.findOrInsertSlot(str));
        if (slot.hasIconsInSlot()) {
            ArrayList arrayList = new ArrayList();
            StatusBarIconHolder statusBarIconHolder = slot.mHolder;
            if (statusBarIconHolder != null) {
                arrayList.add(statusBarIconHolder);
            }
            ArrayList arrayList2 = slot.mSubSlots;
            if (arrayList2 != null) {
                arrayList.addAll(arrayList2);
            }
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                StatusBarIconHolder statusBarIconHolder2 = (StatusBarIconHolder) obj;
                int i2 = statusBarIconHolder2.tag;
                int viewIndex = statusBarIconList.getViewIndex(i2, str);
                if (!list.isEmpty() && list.contains(Integer.valueOf(i2))) {
                    if (list.contains(Integer.valueOf(i2))) {
                        if (!str.equals(this.mSubscriptionsOrder.getSimOrderByIds(i2, list) == 0 ? this.mContext.getString(17043283) : this.mContext.getString(17043284))) {
                        }
                    }
                }
                slot.removeForTag(statusBarIconHolder2.tag);
                this.mIconGroups.forEach(new StatusBarIconControllerImpl$$ExternalSyntheticLambda2(viewIndex, 1));
            }
        }
    }

    public final void setIcon(final String str, final StatusBarIconHolder statusBarIconHolder) {
        int i = statusBarIconHolder.tag;
        StatusBarIconList statusBarIconList = this.mStatusBarIconList;
        boolean z = statusBarIconList.getIconHolder(i, str) == null;
        StatusBarIconList.Slot slot = (StatusBarIconList.Slot) statusBarIconList.mSlots.get(statusBarIconList.findOrInsertSlot(str));
        slot.getClass();
        int i2 = statusBarIconHolder.tag;
        if (i2 == 0) {
            slot.mHolder = statusBarIconHolder;
        } else if (slot.mSubSlots == null) {
            ArrayList arrayList = new ArrayList();
            slot.mSubSlots = arrayList;
            arrayList.add(statusBarIconHolder);
        } else if (slot.getIndexForTag(i2) == -1) {
            slot.mSubSlots.add(statusBarIconHolder);
        }
        if (!z) {
            handleSet(str, statusBarIconHolder);
            return;
        }
        final int viewIndex = statusBarIconList.getViewIndex(statusBarIconHolder.tag, str);
        final boolean contains = this.mIconHideList.contains(str);
        this.mIconGroups.forEach(new Consumer() { // from class: com.android.systemui.statusbar.phone.ui.StatusBarIconControllerImpl$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                boolean z2;
                StatusBarIconControllerImpl statusBarIconControllerImpl = StatusBarIconControllerImpl.this;
                int i3 = viewIndex;
                String str2 = str;
                boolean z3 = contains;
                StatusBarIconHolder statusBarIconHolder2 = statusBarIconHolder;
                IconManager iconManager = (IconManager) obj;
                String str3 = StatusBarIconControllerImpl.EXTERNAL_SLOT_SUFFIX;
                if (z3) {
                    statusBarIconControllerImpl.getClass();
                } else if (!statusBarIconControllerImpl.hideBySimplification(iconManager, str2)) {
                    z2 = false;
                    iconManager.onIconAdded(i3, str2, z2, statusBarIconHolder2);
                }
                z2 = true;
                iconManager.onIconAdded(i3, str2, z2, statusBarIconHolder2);
            }
        });
    }

    public final void setIconContentDescription(CharSequence charSequence, String str) {
        StatusBarIconHolder iconHolder = this.mStatusBarIconList.getIconHolder(0, str);
        if (iconHolder != null) {
            iconHolder.icon.contentDescription = charSequence;
            handleSet(str, iconHolder);
        }
    }

    public final void setIconVisibility(String str, boolean z) {
        StatusBarIconHolder iconHolder = this.mStatusBarIconList.getIconHolder(0, str);
        if (iconHolder == null || iconHolder.isVisible() == z) {
            return;
        }
        iconHolder.setVisible(z);
        handleSet(str, iconHolder);
    }

    public final void setNewMobileIconSubIds(List list) {
        String string = this.mContext.getString(17043283);
        StatusBarIconList statusBarIconList = this.mStatusBarIconList;
        StatusBarIconList.Slot slot = (StatusBarIconList.Slot) statusBarIconList.mSlots.get(statusBarIconList.findOrInsertSlot(string));
        removeUnusedIconsInSlot(string, list);
        String string2 = this.mContext.getString(17043284);
        StatusBarIconList.Slot slot2 = (StatusBarIconList.Slot) statusBarIconList.mSlots.get(statusBarIconList.findOrInsertSlot(string2));
        if (BasicRune.STATUS_NETWORK_MULTI_SIM) {
            removeUnusedIconsInSlot(string2, list);
        }
        Collections.reverse(list);
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Integer num = (Integer) it.next();
            if (BasicRune.STATUS_NETWORK_MULTI_SIM) {
                int simOrderByIds = this.mSubscriptionsOrder.getSimOrderByIds(num.intValue(), list);
                if (num.intValue() == Integer.MAX_VALUE) {
                    simOrderByIds = 0;
                }
                Log.d("StatusBarIconController", "setNewMobileIconSubIds - subId: " + num + ", mobileslotId: " + simOrderByIds);
                if ((simOrderByIds == 0 ? slot.getHolderForTag(num.intValue()) : slot2.getHolderForTag(num.intValue())) == null) {
                    Log.d("StatusBarIconController", "add NewMobileIconSubIds - subId: " + num + ", mobileslotId: " + simOrderByIds);
                    int intValue = num.intValue();
                    StatusBarIconHolder.Companion.getClass();
                    StatusBarIconHolder statusBarIconHolder = new StatusBarIconHolder(null);
                    statusBarIconHolder.type = 3;
                    statusBarIconHolder.tag = intValue;
                    setIcon(simOrderByIds == 0 ? string : string2, statusBarIconHolder);
                }
            } else if (slot.getHolderForTag(num.intValue()) == null) {
                int intValue2 = num.intValue();
                StatusBarIconHolder.Companion.getClass();
                StatusBarIconHolder statusBarIconHolder2 = new StatusBarIconHolder(null);
                statusBarIconHolder2.type = 3;
                statusBarIconHolder2.tag = intValue2;
                setIcon(string, statusBarIconHolder2);
            }
        }
    }

    public final boolean updateSystemIconsAllowList() {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(this.mSystemIconsAllowList);
        List asList = Arrays.asList("SKT", "KTT", "LGT");
        CarrierInfraMediator.Values values = CarrierInfraMediator.Values.ICON_BRANDING;
        CarrierInfraMediator carrierInfraMediator = this.mCarrierInfraMediator;
        if (asList.contains(carrierInfraMediator.get(values, 0, new Object[0])) || "ORANGE".equals(carrierInfraMediator.get(values, 0, new Object[0])) || "ORANGE".equals(carrierInfraMediator.get(values, 1, new Object[0]))) {
            if (!this.mSystemIconsAllowList.contains(this.mContext.getString(17043278))) {
                this.mSystemIconsAllowList.add(this.mContext.getString(17043278));
            }
            if (!this.mSystemIconsAllowList.contains(this.mContext.getString(17043279))) {
                this.mSystemIconsAllowList.add(this.mContext.getString(17043279));
            }
        } else {
            this.mSystemIconsAllowList.remove(this.mContext.getString(17043278));
            this.mSystemIconsAllowList.remove(this.mContext.getString(17043279));
        }
        return arrayList.size() != this.mSystemIconsAllowList.size();
    }

    public final void setIcon(CharSequence charSequence, String str, int i) {
        Icon createWithResource = Icon.createWithResource(this.mContext, i);
        StatusBarIcon.Type type = StatusBarIcon.Type.SystemIcon;
        StatusBarIcon.Shape shape = StatusBarIcon.Shape.WRAP_CONTENT;
        boolean z = createWithResource.getType() == 2;
        String str2 = "Expected Icon of TYPE_RESOURCE, but got " + createWithResource.getType();
        if (z) {
            String resPackage = createWithResource.getResPackage();
            if (TextUtils.isEmpty(resPackage)) {
                resPackage = this.mContext.getPackageName();
            }
            String str3 = resPackage;
            StatusBarIconHolder iconHolder = this.mStatusBarIconList.getIconHolder(0, str);
            if (iconHolder == null) {
                StatusBarIcon statusBarIcon = new StatusBarIcon(UserHandle.SYSTEM, str3, createWithResource, 0, 0, charSequence, type, shape);
                statusBarIcon.preloadedIcon = null;
                StatusBarIconHolder.Companion.getClass();
                StatusBarIconHolder statusBarIconHolder = new StatusBarIconHolder(null);
                statusBarIconHolder.icon = statusBarIcon;
                setIcon(str, statusBarIconHolder);
                return;
            }
            StatusBarIcon statusBarIcon2 = iconHolder.icon;
            statusBarIcon2.pkg = str3;
            statusBarIcon2.icon = createWithResource;
            statusBarIcon2.contentDescription = charSequence;
            statusBarIcon2.type = type;
            statusBarIcon2.shape = shape;
            statusBarIcon2.preloadedIcon = null;
            handleSet(str, iconHolder);
            return;
        }
        throw new IllegalArgumentException(String.valueOf(str2));
    }
}
