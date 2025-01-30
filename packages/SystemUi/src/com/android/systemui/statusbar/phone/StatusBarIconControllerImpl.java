package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.view.ViewGroup;
import com.android.internal.statusbar.StatusBarIcon;
import com.android.systemui.BasicRune;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.biometrics.SideFpsController$$ExternalSyntheticOutline0;
import com.android.systemui.demomode.DemoMode;
import com.android.systemui.demomode.DemoModeController;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.StatusIconDisplayable;
import com.android.systemui.statusbar.phone.IndicatorScaleGardener;
import com.android.systemui.statusbar.phone.StatusBarIconController;
import com.android.systemui.statusbar.phone.StatusBarIconList;
import com.android.systemui.statusbar.pipeline.StatusBarPipelineFlags;
import com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator;
import com.android.systemui.statusbar.pipeline.icons.shared.BindableIconsRegistry;
import com.android.systemui.statusbar.pipeline.icons.shared.BindableIconsRegistryImpl;
import com.android.systemui.statusbar.pipeline.mobile.data.model.SubscriptionsOrder;
import com.android.systemui.statusbar.pipeline.satellite.ui.DeviceBasedSatelliteBindableIcon;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.tuner.TunerService;
import com.sec.ims.IMSParameter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class StatusBarIconControllerImpl implements TunerService.Tunable, ConfigurationController.ConfigurationListener, Dumpable, StatusBarIconController, DemoMode {
    protected static final String EXTERNAL_SLOT_SUFFIX = "__external";
    public final CarrierInfraMediator mCarrierInfraMediator;
    public final C31371 mCommandQueueCallbacks;
    public final Context mContext;
    public final ArrayList mIconGroups = new ArrayList();
    public final ArraySet mIconHideList = new ArraySet();
    public final IndicatorScaleGardener mIndicatorScaleGardener;
    public final StatusBarIconList mStatusBarIconList;
    public final StatusBarPipelineFlags mStatusBarPipelineFlags;
    public final SubscriptionsOrder mSubscriptionsOrder;
    public final ArrayList mSystemIconsAllowList;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.systemui.statusbar.CommandQueue$Callbacks, com.android.systemui.statusbar.phone.StatusBarIconControllerImpl$1] */
    public StatusBarIconControllerImpl(Context context, CommandQueue commandQueue, DemoModeController demoModeController, ConfigurationController configurationController, TunerService tunerService, DumpManager dumpManager, StatusBarIconList statusBarIconList, StatusBarPipelineFlags statusBarPipelineFlags, BindableIconsRegistry bindableIconsRegistry, CarrierInfraMediator carrierInfraMediator, IndicatorScaleGardener indicatorScaleGardener, SubscriptionsOrder subscriptionsOrder) {
        ArrayList arrayList = new ArrayList();
        this.mSystemIconsAllowList = arrayList;
        ?? r1 = new CommandQueue.Callbacks() { // from class: com.android.systemui.statusbar.phone.StatusBarIconControllerImpl.1
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
        this.mIndicatorScaleGardener = indicatorScaleGardener;
        this.mSubscriptionsOrder = subscriptionsOrder;
        ((ConfigurationControllerImpl) configurationController).addCallback(this);
        commandQueue.addCallback((CommandQueue.Callbacks) r1);
        tunerService.addTunable(this, "icon_blacklist");
        demoModeController.addCallback((DemoMode) this);
        dumpManager.getClass();
        DumpManager.registerDumpable$default(dumpManager, "StatusBarIconControllerImpl", this);
        Iterator it = ((BindableIconsRegistryImpl) bindableIconsRegistry).bindableIcons.iterator();
        while (it.hasNext()) {
            ((DeviceBasedSatelliteBindableIcon) it.next()).getClass();
        }
    }

    public final void addIconGroup(StatusBarIconController.IconManager iconManager) {
        ArrayList arrayList = this.mIconGroups;
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            if (((StatusBarIconController.IconManager) it.next()).mGroup == iconManager.mGroup) {
                Log.e("StatusBarIconController", "Adding new IconManager for the same ViewGroup. This could cause unexpected results.");
            }
        }
        IndicatorScaleGardener.ScaleModel latestScaleModel = this.mIndicatorScaleGardener.getLatestScaleModel(this.mContext);
        iconManager.mRatio = latestScaleModel.ratio;
        iconManager.mIconSize = latestScaleModel.iconSize;
        iconManager.mController = this;
        arrayList.add(iconManager);
        StatusBarIconList statusBarIconList = this.mStatusBarIconList;
        List list = statusBarIconList.mViewOnlySlots;
        for (int i = 0; i < list.size(); i++) {
            StatusBarIconList.Slot slot = (StatusBarIconList.Slot) list.get(i);
            List holderListInViewOrder = slot.getHolderListInViewOrder();
            ArraySet arraySet = this.mIconHideList;
            String str = slot.mName;
            boolean z = arraySet.contains(str) || hideBySimplification(iconManager, str);
            Iterator it2 = ((ArrayList) holderListInViewOrder).iterator();
            while (it2.hasNext()) {
                StatusBarIconHolder statusBarIconHolder = (StatusBarIconHolder) it2.next();
                iconManager.onIconAdded(statusBarIconList.getViewIndex(statusBarIconHolder.tag, str), str, z, statusBarIconHolder);
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
        Iterator it = this.mIconGroups.iterator();
        while (it.hasNext()) {
            StatusBarIconController.IconManager iconManager = (StatusBarIconController.IconManager) it.next();
            if (iconManager.mDemoable) {
                iconManager.dispatchDemoCommand(bundle, str);
            }
        }
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        int i;
        printWriter.println("StatusBarIconController state:");
        Iterator it = this.mIconGroups.iterator();
        while (true) {
            i = 0;
            if (!it.hasNext()) {
                break;
            }
            StatusBarIconController.IconManager iconManager = (StatusBarIconController.IconManager) it.next();
            if (iconManager.mShouldLog) {
                ViewGroup viewGroup = iconManager.mGroup;
                int childCount = viewGroup.getChildCount();
                SideFpsController$$ExternalSyntheticOutline0.m105m("  icon views: ", childCount, printWriter);
                while (i < childCount) {
                    printWriter.println("    [" + i + "] icon=" + ((StatusIconDisplayable) viewGroup.getChildAt(i)));
                    i++;
                }
            }
        }
        StatusBarIconList statusBarIconList = this.mStatusBarIconList;
        statusBarIconList.getClass();
        printWriter.println("StatusBarIconList state:");
        ArrayList arrayList = statusBarIconList.mSlots;
        int size = arrayList.size();
        SideFpsController$$ExternalSyntheticOutline0.m105m("  icon slots: ", size, printWriter);
        while (i < size) {
            printWriter.printf("    %2d:%s\n", Integer.valueOf(i), ((StatusBarIconList.Slot) arrayList.get(i)).toString());
            i++;
        }
    }

    public final void handleSet(String str, final StatusBarIconHolder statusBarIconHolder) {
        final int viewIndex = this.mStatusBarIconList.getViewIndex(statusBarIconHolder.tag, str);
        this.mIconGroups.forEach(new Consumer() { // from class: com.android.systemui.statusbar.phone.StatusBarIconControllerImpl$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((StatusBarIconController.IconManager) obj).onSetIconHolder(viewIndex, statusBarIconHolder);
            }
        });
    }

    public final boolean hideBySimplification(StatusBarIconController.IconManager iconManager, String str) {
        iconManager.getClass();
        StatusBarLocation statusBarLocation = StatusBarLocation.HOME;
        StatusBarLocation statusBarLocation2 = iconManager.mLocation;
        if (statusBarLocation2 == statusBarLocation || statusBarLocation2 == StatusBarLocation.KEYGUARD) {
            return (this.mSystemIconsAllowList.contains(str) || ((List) this.mCarrierInfraMediator.get(CarrierInfraMediator.Values.EXTRA_SYSTEM_ICON_LIST, 0, new Object[0])).contains(str)) ? false : true;
        }
        return false;
    }

    @Override // com.android.systemui.demomode.DemoModeCommandReceiver
    public final void onDemoModeFinished() {
        Iterator it = this.mIconGroups.iterator();
        while (it.hasNext()) {
            StatusBarIconController.IconManager iconManager = (StatusBarIconController.IconManager) it.next();
            if (iconManager.mDemoable) {
                iconManager.onDemoModeFinished();
            }
        }
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onDensityOrFontScaleChanged() {
        ArrayList arrayList = this.mIconGroups;
        int size = arrayList.size();
        while (true) {
            size--;
            if (size < 0) {
                return;
            }
            StatusBarIconController.IconManager iconManager = (StatusBarIconController.IconManager) arrayList.get(size);
            removeIconGroup(iconManager);
            addIconGroup(iconManager);
        }
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onDisplayDeviceTypeChanged() {
        if (BasicRune.BASIC_FOLDABLE_TYPE_FOLD) {
            onDensityOrFontScaleChanged();
        }
    }

    @Override // com.android.systemui.tuner.TunerService.Tunable
    public final void onTuningChanged(String str, String str2) {
        if ("icon_blacklist".equals(str)) {
            ArraySet arraySet = this.mIconHideList;
            arraySet.clear();
            arraySet.addAll(StatusBarIconController.getIconHideList(this.mContext, str2));
            List list = this.mStatusBarIconList.mViewOnlySlots;
            ArrayMap arrayMap = new ArrayMap();
            int size = list.size();
            while (true) {
                size--;
                if (size < 0) {
                    break;
                }
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

    public final void removeAllIconsForSlot(String str) {
        if (this.mStatusBarPipelineFlags.isIconControlledByFlags(str)) {
            Log.i("StatusBarIconController", "Ignoring removal of (" + str + "). It should be controlled elsewhere");
            return;
        }
        StatusBarIconList statusBarIconList = this.mStatusBarIconList;
        StatusBarIconList.Slot slot = statusBarIconList.getSlot(str);
        if (slot.hasIconsInSlot()) {
            Iterator it = ((ArrayList) slot.getHolderListInViewOrder()).iterator();
            while (it.hasNext()) {
                StatusBarIconHolder statusBarIconHolder = (StatusBarIconHolder) it.next();
                int viewIndex = statusBarIconList.getViewIndex(statusBarIconHolder.tag, str);
                slot.removeForTag(statusBarIconHolder.tag);
                this.mIconGroups.forEach(new StatusBarIconControllerImpl$$ExternalSyntheticLambda1(viewIndex, 1));
            }
        }
    }

    public final void removeIconGroup(StatusBarIconController.IconManager iconManager) {
        iconManager.destroy();
        this.mIconGroups.remove(iconManager);
    }

    public final void removeUnusedIconsInSlot(String str, List list) {
        StatusBarIconList statusBarIconList = this.mStatusBarIconList;
        StatusBarIconList.Slot slot = statusBarIconList.getSlot(str);
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
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                StatusBarIconHolder statusBarIconHolder2 = (StatusBarIconHolder) it.next();
                int i = statusBarIconHolder2.tag;
                int viewIndex = statusBarIconList.getViewIndex(i, str);
                if (!list.isEmpty() && list.contains(Integer.valueOf(i))) {
                    if (list.contains(Integer.valueOf(i))) {
                        int simOrderByIds = this.mSubscriptionsOrder.getSimOrderByIds(Integer.valueOf(i).intValue(), list);
                        Context context = this.mContext;
                        if (!str.equals(simOrderByIds == 0 ? context.getString(17042930) : context.getString(17042931))) {
                        }
                    }
                }
                slot.removeForTag(statusBarIconHolder2.tag);
                this.mIconGroups.forEach(new StatusBarIconControllerImpl$$ExternalSyntheticLambda1(viewIndex, 2));
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
        this.mIconGroups.forEach(new Consumer() { // from class: com.android.systemui.statusbar.phone.StatusBarIconControllerImpl$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                boolean z2;
                StatusBarIconControllerImpl statusBarIconControllerImpl = StatusBarIconControllerImpl.this;
                int i3 = viewIndex;
                String str2 = str;
                boolean z3 = contains;
                StatusBarIconHolder statusBarIconHolder2 = statusBarIconHolder;
                StatusBarIconController.IconManager iconManager = (StatusBarIconController.IconManager) obj;
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

    public final void setIconAccessibilityLiveRegion(final int i, String str) {
        StatusBarIconList statusBarIconList = this.mStatusBarIconList;
        StatusBarIconList.Slot slot = statusBarIconList.getSlot(str);
        if (slot.hasIconsInSlot()) {
            Iterator it = ((ArrayList) slot.getHolderListInViewOrder()).iterator();
            while (it.hasNext()) {
                final int viewIndex = statusBarIconList.getViewIndex(((StatusBarIconHolder) it.next()).tag, str);
                this.mIconGroups.forEach(new Consumer() { // from class: com.android.systemui.statusbar.phone.StatusBarIconControllerImpl$$ExternalSyntheticLambda4
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        int i2 = viewIndex;
                        ((StatusBarIconController.IconManager) obj).mGroup.getChildAt(i2).setAccessibilityLiveRegion(i);
                    }
                });
            }
        }
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

    public final void setIcon(CharSequence charSequence, String str, int i) {
        StatusBarIconHolder iconHolder = this.mStatusBarIconList.getIconHolder(0, str);
        Context context = this.mContext;
        if (iconHolder == null) {
            StatusBarIcon statusBarIcon = new StatusBarIcon(UserHandle.SYSTEM, context.getPackageName(), Icon.createWithResource(context, i), 0, 0, charSequence);
            StatusBarIconHolder.Companion.getClass();
            StatusBarIconHolder statusBarIconHolder = new StatusBarIconHolder(null);
            statusBarIconHolder.icon = statusBarIcon;
            setIcon(str, statusBarIconHolder);
            return;
        }
        iconHolder.icon.icon = Icon.createWithResource(context, i);
        iconHolder.icon.contentDescription = charSequence;
        handleSet(str, iconHolder);
    }
}
