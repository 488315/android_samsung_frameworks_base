package com.android.systemui.controls.p005ui.util;

import android.content.Context;
import android.service.controls.templates.StatelessTemplate;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import com.android.systemui.controls.p005ui.Behavior;
import com.android.systemui.controls.p005ui.ControlViewHolder;
import com.android.systemui.controls.p005ui.StatusBehavior;
import com.android.systemui.controls.p005ui.TemperatureControlBehavior;
import com.android.systemui.controls.p005ui.ToggleBehavior;
import com.android.systemui.controls.p005ui.ToggleRangeBehavior;
import com.android.systemui.controls.p005ui.TouchBehavior;
import com.android.systemui.controls.p005ui.util.ControlsPreference;
import com.android.systemui.controls.p005ui.util.SystemUIAnalyticsWrapper;
import com.android.systemui.controls.p005ui.view.ActionIconView;
import com.android.systemui.util.SystemUIAnalytics;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SALogger {
    public static final Companion Companion = new Companion(null);
    public final SystemUIAnalyticsWrapper systemUIAnalyticsWrapper;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class AppStatus {

        @SerializedName("AppName")
        private final String appName;

        @SerializedName("Value")
        private final String value;

        public AppStatus(String str, String str2) {
            this.appName = str;
            this.value = str2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof AppStatus)) {
                return false;
            }
            AppStatus appStatus = (AppStatus) obj;
            return Intrinsics.areEqual(this.appName, appStatus.appName) && Intrinsics.areEqual(this.value, appStatus.value);
        }

        public final int hashCode() {
            return this.value.hashCode() + (this.appName.hashCode() * 31);
        }

        public final String toString() {
            return MotionLayout$$ExternalSyntheticOutline0.m22m("AppStatus(appName=", this.appName, ", value=", this.value, ")");
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class AppStatusList {

        @SerializedName("AppList")
        private final List<AppStatus> appList;

        public AppStatusList(List<AppStatus> list) {
            this.appList = list;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof AppStatusList) && Intrinsics.areEqual(this.appList, ((AppStatusList) obj).appList);
        }

        public final int hashCode() {
            return this.appList.hashCode();
        }

        public final String toString() {
            return "AppStatusList(appList=" + this.appList + ")";
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class Event {

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class AddDevices extends Event {
            public static final AddDevices INSTANCE = new AddDevices();

            private AddDevices() {
                super(null);
            }

            @Override // com.android.systemui.controls.ui.util.SALogger.Event
            public final void sendEvent(SystemUIAnalyticsWrapper systemUIAnalyticsWrapper) {
                SystemUIAnalyticsWrapper.ScreenId.NoDeviceSelected noDeviceSelected = SystemUIAnalyticsWrapper.ScreenId.NoDeviceSelected.INSTANCE;
                SystemUIAnalyticsWrapper.EventId.AddDevices addDevices = SystemUIAnalyticsWrapper.EventId.AddDevices.INSTANCE;
                systemUIAnalyticsWrapper.getClass();
                SystemUIAnalyticsWrapper.sendEventLog(noDeviceSelected, addDevices);
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class ChooseAppOnOff extends Event {
            public final boolean checked;

            public ChooseAppOnOff(boolean z) {
                super(null);
                this.checked = z;
            }

            @Override // com.android.systemui.controls.ui.util.SALogger.Event
            public final void sendEvent(SystemUIAnalyticsWrapper systemUIAnalyticsWrapper) {
                SystemUIAnalyticsWrapper.ScreenId.Intro intro = SystemUIAnalyticsWrapper.ScreenId.Intro.INSTANCE;
                SystemUIAnalyticsWrapper.EventId.ChooseAppsOnOff chooseAppsOnOff = SystemUIAnalyticsWrapper.EventId.ChooseAppsOnOff.INSTANCE;
                SALogger.Companion.getClass();
                String valueOf = String.valueOf(Boolean.compare(this.checked, false));
                systemUIAnalyticsWrapper.getClass();
                SystemUIAnalyticsWrapper.sendEventLog(intro, chooseAppsOnOff, valueOf);
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class ChooseAppOnOffOnManageApps extends Event {
            public final boolean checked;

            public ChooseAppOnOffOnManageApps(boolean z) {
                super(null);
                this.checked = z;
            }

            @Override // com.android.systemui.controls.ui.util.SALogger.Event
            public final void sendEvent(SystemUIAnalyticsWrapper systemUIAnalyticsWrapper) {
                SystemUIAnalyticsWrapper.ScreenId.ManageApps manageApps = SystemUIAnalyticsWrapper.ScreenId.ManageApps.INSTANCE;
                SystemUIAnalyticsWrapper.EventId.ChooseAppsOnOffOnManageApps chooseAppsOnOffOnManageApps = SystemUIAnalyticsWrapper.EventId.ChooseAppsOnOffOnManageApps.INSTANCE;
                SALogger.Companion.getClass();
                String valueOf = String.valueOf(Boolean.compare(this.checked, false));
                systemUIAnalyticsWrapper.getClass();
                SystemUIAnalyticsWrapper.sendEventLog(manageApps, chooseAppsOnOffOnManageApps, valueOf);
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class IntroStart extends Event {
            public final int selectedApps;
            public final int totalApps;

            public IntroStart(int i, int i2) {
                super(null);
                this.selectedApps = i;
                this.totalApps = i2;
            }

            @Override // com.android.systemui.controls.ui.util.SALogger.Event
            public final void sendEvent(SystemUIAnalyticsWrapper systemUIAnalyticsWrapper) {
                SystemUIAnalyticsWrapper.ScreenId.Intro intro = SystemUIAnalyticsWrapper.ScreenId.Intro.INSTANCE;
                SystemUIAnalyticsWrapper.EventId.IntroStart introStart = SystemUIAnalyticsWrapper.EventId.IntroStart.INSTANCE;
                SystemUIAnalyticsWrapper.KeyId.NumberOfSelectedApps numberOfSelectedApps = SystemUIAnalyticsWrapper.KeyId.NumberOfSelectedApps.INSTANCE;
                String valueOf = String.valueOf(this.selectedApps);
                SystemUIAnalyticsWrapper.KeyId.NumberOfTotalApps numberOfTotalApps = SystemUIAnalyticsWrapper.KeyId.NumberOfTotalApps.INSTANCE;
                String valueOf2 = String.valueOf(this.totalApps);
                systemUIAnalyticsWrapper.getClass();
                intro.getClass();
                introStart.getClass();
                numberOfSelectedApps.getClass();
                numberOfTotalApps.getClass();
                SystemUIAnalytics.sendEventCDLog("Dvcs01", "Dvcs013", "num of selected apps", valueOf, "num of total apps", valueOf2);
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class LaunchDevices extends Event {
            public static final LaunchDevices INSTANCE = new LaunchDevices();

            private LaunchDevices() {
                super(null);
            }

            @Override // com.android.systemui.controls.ui.util.SALogger.Event
            public final void sendEvent(SystemUIAnalyticsWrapper systemUIAnalyticsWrapper) {
                SystemUIAnalytics.sendEventLog(SystemUIAnalytics.sCurrentScreenID, SystemUIAnalyticsWrapper.EventId.LaunchDevices.INSTANCE.getEventId());
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class LaunchFullController extends Event {
            public static final LaunchFullController INSTANCE = new LaunchFullController();

            private LaunchFullController() {
                super(null);
            }

            @Override // com.android.systemui.controls.ui.util.SALogger.Event
            public final void sendEvent(SystemUIAnalyticsWrapper systemUIAnalyticsWrapper) {
                SystemUIAnalyticsWrapper.ScreenId.CustomPanel customPanel = SystemUIAnalyticsWrapper.ScreenId.CustomPanel.INSTANCE;
                SystemUIAnalyticsWrapper.EventId.LaunchFullController launchFullController = SystemUIAnalyticsWrapper.EventId.LaunchFullController.INSTANCE;
                systemUIAnalyticsWrapper.getClass();
                SystemUIAnalyticsWrapper.sendEventLog(customPanel, launchFullController);
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class LaunchSmartThings extends Event {
            public static final LaunchSmartThings INSTANCE = new LaunchSmartThings();

            private LaunchSmartThings() {
                super(null);
            }

            @Override // com.android.systemui.controls.ui.util.SALogger.Event
            public final void sendEvent(SystemUIAnalyticsWrapper systemUIAnalyticsWrapper) {
                SystemUIAnalyticsWrapper.ScreenId.MainScreen mainScreen = SystemUIAnalyticsWrapper.ScreenId.MainScreen.INSTANCE;
                SystemUIAnalyticsWrapper.EventId.LaunchSmartThings launchSmartThings = SystemUIAnalyticsWrapper.EventId.LaunchSmartThings.INSTANCE;
                systemUIAnalyticsWrapper.getClass();
                SystemUIAnalyticsWrapper.sendEventLog(mainScreen, launchSmartThings);
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class LeftChooseDevices extends Event {
            public final String appName;
            public final int numberOfSelectedControls;
            public final int numberOfStructures;
            public final int numberOfTotalControls;
            public final int numberOfZones;

            public LeftChooseDevices(String str, int i, int i2, int i3, int i4) {
                super(null);
                this.appName = str;
                this.numberOfSelectedControls = i;
                this.numberOfTotalControls = i2;
                this.numberOfStructures = i3;
                this.numberOfZones = i4;
            }

            @Override // com.android.systemui.controls.ui.util.SALogger.Event
            public final void sendEvent(SystemUIAnalyticsWrapper systemUIAnalyticsWrapper) {
                SystemUIAnalyticsWrapper.ScreenId.ChooseDevices chooseDevices = SystemUIAnalyticsWrapper.ScreenId.ChooseDevices.INSTANCE;
                SystemUIAnalyticsWrapper.EventId.LeftChooseDevices leftChooseDevices = SystemUIAnalyticsWrapper.EventId.LeftChooseDevices.INSTANCE;
                HashMap hashMap = new HashMap();
                hashMap.put(SystemUIAnalyticsWrapper.KeyId.AppName.INSTANCE, this.appName);
                hashMap.put(SystemUIAnalyticsWrapper.KeyId.SelectedControl.INSTANCE, String.valueOf(this.numberOfSelectedControls));
                hashMap.put(SystemUIAnalyticsWrapper.KeyId.AllControls.INSTANCE, String.valueOf(this.numberOfTotalControls));
                hashMap.put(SystemUIAnalyticsWrapper.KeyId.Structure.INSTANCE, String.valueOf(this.numberOfStructures));
                hashMap.put(SystemUIAnalyticsWrapper.KeyId.Zone.INSTANCE, String.valueOf(this.numberOfZones));
                Unit unit = Unit.INSTANCE;
                String screenId = chooseDevices.getScreenId();
                String eventId = leftChooseDevices.getEventId();
                Set<Map.Entry> entrySet = hashMap.entrySet();
                int mapCapacity = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(entrySet, 10));
                if (mapCapacity < 16) {
                    mapCapacity = 16;
                }
                LinkedHashMap linkedHashMap = new LinkedHashMap(mapCapacity);
                for (Map.Entry entry : entrySet) {
                    Pair pair = new Pair(((SystemUIAnalyticsWrapper.KeyId) entry.getKey()).getKeyId(), entry.getValue());
                    linkedHashMap.put(pair.getFirst(), pair.getSecond());
                }
                SystemUIAnalytics.sendEventCDLog(screenId, eventId, linkedHashMap);
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class MoveCard extends Event {
            public static final MoveCard INSTANCE = new MoveCard();

            private MoveCard() {
                super(null);
            }

            @Override // com.android.systemui.controls.ui.util.SALogger.Event
            public final void sendEvent(SystemUIAnalyticsWrapper systemUIAnalyticsWrapper) {
                SystemUIAnalyticsWrapper.ScreenId.MainScreen mainScreen = SystemUIAnalyticsWrapper.ScreenId.MainScreen.INSTANCE;
                SystemUIAnalyticsWrapper.EventId.MoveCard moveCard = SystemUIAnalyticsWrapper.EventId.MoveCard.INSTANCE;
                systemUIAnalyticsWrapper.getClass();
                SystemUIAnalyticsWrapper.sendEventLog(mainScreen, moveCard);
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class OpenSpinner extends Event {
            public static final OpenSpinner INSTANCE = new OpenSpinner();

            private OpenSpinner() {
                super(null);
            }

            @Override // com.android.systemui.controls.ui.util.SALogger.Event
            public final void sendEvent(SystemUIAnalyticsWrapper systemUIAnalyticsWrapper) {
                SystemUIAnalyticsWrapper.ScreenId.MainScreen mainScreen = SystemUIAnalyticsWrapper.ScreenId.MainScreen.INSTANCE;
                SystemUIAnalyticsWrapper.EventId.OpenSpinner openSpinner = SystemUIAnalyticsWrapper.EventId.OpenSpinner.INSTANCE;
                systemUIAnalyticsWrapper.getClass();
                SystemUIAnalyticsWrapper.sendEventLog(mainScreen, openSpinner);
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class QuitDevices extends Event {
            public final Screen screen;

            public QuitDevices(Screen screen) {
                super(null);
                this.screen = screen;
            }

            @Override // com.android.systemui.controls.ui.util.SALogger.Event
            public final void sendEvent(SystemUIAnalyticsWrapper systemUIAnalyticsWrapper) {
                SystemUIAnalyticsWrapper.ScreenId screenId = this.screen.getScreenId();
                SystemUIAnalyticsWrapper.EventId.QuitDevices quitDevices = SystemUIAnalyticsWrapper.EventId.QuitDevices.INSTANCE;
                systemUIAnalyticsWrapper.getClass();
                SystemUIAnalyticsWrapper.sendEventLog(screenId, quitDevices);
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class Reorder extends Event {
            public static final Reorder INSTANCE = new Reorder();

            private Reorder() {
                super(null);
            }

            @Override // com.android.systemui.controls.ui.util.SALogger.Event
            public final void sendEvent(SystemUIAnalyticsWrapper systemUIAnalyticsWrapper) {
                SystemUIAnalyticsWrapper.ScreenId.ChooseDevices chooseDevices = SystemUIAnalyticsWrapper.ScreenId.ChooseDevices.INSTANCE;
                SystemUIAnalyticsWrapper.EventId.Reorder reorder = SystemUIAnalyticsWrapper.EventId.Reorder.INSTANCE;
                systemUIAnalyticsWrapper.getClass();
                SystemUIAnalyticsWrapper.sendEventLog(chooseDevices, reorder);
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class SettingsControlDevicesOnOff extends Event {
            public final boolean checked;

            public SettingsControlDevicesOnOff(boolean z) {
                super(null);
                this.checked = z;
            }

            @Override // com.android.systemui.controls.ui.util.SALogger.Event
            public final void sendEvent(SystemUIAnalyticsWrapper systemUIAnalyticsWrapper) {
                SystemUIAnalyticsWrapper.ScreenId.Settings settings = SystemUIAnalyticsWrapper.ScreenId.Settings.INSTANCE;
                SystemUIAnalyticsWrapper.EventId.SettingsControlDevicesOnOff settingsControlDevicesOnOff = SystemUIAnalyticsWrapper.EventId.SettingsControlDevicesOnOff.INSTANCE;
                SALogger.Companion.getClass();
                String valueOf = String.valueOf(Boolean.compare(this.checked, false));
                systemUIAnalyticsWrapper.getClass();
                SystemUIAnalyticsWrapper.sendEventLog(settings, settingsControlDevicesOnOff, valueOf);
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class SettingsShowDevicesOnOff extends Event {
            public final boolean checked;

            public SettingsShowDevicesOnOff(boolean z) {
                super(null);
                this.checked = z;
            }

            @Override // com.android.systemui.controls.ui.util.SALogger.Event
            public final void sendEvent(SystemUIAnalyticsWrapper systemUIAnalyticsWrapper) {
                SystemUIAnalyticsWrapper.ScreenId.Settings settings = SystemUIAnalyticsWrapper.ScreenId.Settings.INSTANCE;
                SystemUIAnalyticsWrapper.EventId.SettingsShowDevicesOnOff settingsShowDevicesOnOff = SystemUIAnalyticsWrapper.EventId.SettingsShowDevicesOnOff.INSTANCE;
                SALogger.Companion.getClass();
                String valueOf = String.valueOf(Boolean.compare(this.checked, false));
                systemUIAnalyticsWrapper.getClass();
                SystemUIAnalyticsWrapper.sendEventLog(settings, settingsShowDevicesOnOff, valueOf);
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class TapAppList extends Event {
            public static final TapAppList INSTANCE = new TapAppList();

            private TapAppList() {
                super(null);
            }

            @Override // com.android.systemui.controls.ui.util.SALogger.Event
            public final void sendEvent(SystemUIAnalyticsWrapper systemUIAnalyticsWrapper) {
                SystemUIAnalyticsWrapper.ScreenId.Intro intro = SystemUIAnalyticsWrapper.ScreenId.Intro.INSTANCE;
                SystemUIAnalyticsWrapper.EventId.TapAppList tapAppList = SystemUIAnalyticsWrapper.EventId.TapAppList.INSTANCE;
                systemUIAnalyticsWrapper.getClass();
                SystemUIAnalyticsWrapper.sendEventLog(intro, tapAppList);
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class TapAppListOnManageApps extends Event {
            public static final TapAppListOnManageApps INSTANCE = new TapAppListOnManageApps();

            private TapAppListOnManageApps() {
                super(null);
            }

            @Override // com.android.systemui.controls.ui.util.SALogger.Event
            public final void sendEvent(SystemUIAnalyticsWrapper systemUIAnalyticsWrapper) {
                SystemUIAnalyticsWrapper.ScreenId.ManageApps manageApps = SystemUIAnalyticsWrapper.ScreenId.ManageApps.INSTANCE;
                SystemUIAnalyticsWrapper.EventId.TapAppListOnManageApps tapAppListOnManageApps = SystemUIAnalyticsWrapper.EventId.TapAppListOnManageApps.INSTANCE;
                systemUIAnalyticsWrapper.getClass();
                SystemUIAnalyticsWrapper.sendEventLog(manageApps, tapAppListOnManageApps);
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class TapCardLayout extends Event {
            public final ControlViewHolder cvh;

            public TapCardLayout(ControlViewHolder controlViewHolder) {
                super(null);
                this.cvh = controlViewHolder;
            }

            @Override // com.android.systemui.controls.ui.util.SALogger.Event
            public final void sendEvent(SystemUIAnalyticsWrapper systemUIAnalyticsWrapper) {
                SystemUIAnalyticsWrapper.ScreenId.MainScreen mainScreen = SystemUIAnalyticsWrapper.ScreenId.MainScreen.INSTANCE;
                ControlViewHolder controlViewHolder = this.cvh;
                ActionIconView actionIconView = controlViewHolder.getCustomControlViewHolder().actionIcon;
                boolean z = false;
                if (actionIconView != null) {
                    if (actionIconView.actionIcon.getVisibility() == 0) {
                        z = true;
                    }
                }
                SystemUIAnalyticsWrapper.EventId eventId = z ? SystemUIAnalyticsWrapper.EventId.TapCardWithButton.INSTANCE : SystemUIAnalyticsWrapper.EventId.TapCardWithoutButton.INSTANCE;
                SystemUIAnalyticsWrapper.KeyId.Template template = SystemUIAnalyticsWrapper.KeyId.Template.INSTANCE;
                Behavior behavior = controlViewHolder.behavior;
                String templateType = behavior != null ? Event.getTemplateType(behavior) : "";
                SystemUIAnalyticsWrapper.KeyId.DeviceName deviceName = SystemUIAnalyticsWrapper.KeyId.DeviceName.INSTANCE;
                String obj = controlViewHolder.title.getText().toString();
                SystemUIAnalyticsWrapper.KeyId.DeviceType deviceType = SystemUIAnalyticsWrapper.KeyId.DeviceType.INSTANCE;
                String valueOf = String.valueOf(controlViewHolder.getDeviceType());
                systemUIAnalyticsWrapper.getClass();
                mainScreen.getClass();
                String eventId2 = eventId.getEventId();
                template.getClass();
                deviceName.getClass();
                deviceType.getClass();
                SystemUIAnalytics.sendEventCDLog("Dvcs05", eventId2, "template", templateType, "Device name", obj, "Device type", valueOf);
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class TapMainActionButton extends Event {
            public final ControlViewHolder cvh;

            public TapMainActionButton(ControlViewHolder controlViewHolder) {
                super(null);
                this.cvh = controlViewHolder;
            }

            @Override // com.android.systemui.controls.ui.util.SALogger.Event
            public final void sendEvent(SystemUIAnalyticsWrapper systemUIAnalyticsWrapper) {
                SystemUIAnalyticsWrapper.ScreenId.MainScreen mainScreen = SystemUIAnalyticsWrapper.ScreenId.MainScreen.INSTANCE;
                SystemUIAnalyticsWrapper.EventId.TapMainActionButton tapMainActionButton = SystemUIAnalyticsWrapper.EventId.TapMainActionButton.INSTANCE;
                SystemUIAnalyticsWrapper.KeyId.Template template = SystemUIAnalyticsWrapper.KeyId.Template.INSTANCE;
                ControlViewHolder controlViewHolder = this.cvh;
                Behavior behavior = controlViewHolder.behavior;
                String templateType = behavior != null ? Event.getTemplateType(behavior) : "";
                SystemUIAnalyticsWrapper.KeyId.DeviceName deviceName = SystemUIAnalyticsWrapper.KeyId.DeviceName.INSTANCE;
                String obj = controlViewHolder.title.getText().toString();
                SystemUIAnalyticsWrapper.KeyId.DeviceType deviceType = SystemUIAnalyticsWrapper.KeyId.DeviceType.INSTANCE;
                String valueOf = String.valueOf(controlViewHolder.getDeviceType());
                systemUIAnalyticsWrapper.getClass();
                mainScreen.getClass();
                tapMainActionButton.getClass();
                template.getClass();
                deviceName.getClass();
                deviceType.getClass();
                SystemUIAnalytics.sendEventCDLog("Dvcs05", "Dvcs055", "template", templateType, "Device name", obj, "Device type", valueOf);
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class TapMenuDevicesToShow extends Event {
            public final Screen screen;

            public TapMenuDevicesToShow(Screen screen) {
                super(null);
                this.screen = screen;
            }

            @Override // com.android.systemui.controls.ui.util.SALogger.Event
            public final void sendEvent(SystemUIAnalyticsWrapper systemUIAnalyticsWrapper) {
                SystemUIAnalyticsWrapper.ScreenId screenId = this.screen.getScreenId();
                SystemUIAnalyticsWrapper.EventId.MoreDevicesToShow moreDevicesToShow = SystemUIAnalyticsWrapper.EventId.MoreDevicesToShow.INSTANCE;
                systemUIAnalyticsWrapper.getClass();
                SystemUIAnalyticsWrapper.sendEventLog(screenId, moreDevicesToShow);
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class TapMenuManageApp extends Event {
            public final Screen screen;

            public TapMenuManageApp(Screen screen) {
                super(null);
                this.screen = screen;
            }

            @Override // com.android.systemui.controls.ui.util.SALogger.Event
            public final void sendEvent(SystemUIAnalyticsWrapper systemUIAnalyticsWrapper) {
                SystemUIAnalyticsWrapper.ScreenId screenId = this.screen.getScreenId();
                SystemUIAnalyticsWrapper.EventId.MoreManageApps moreManageApps = SystemUIAnalyticsWrapper.EventId.MoreManageApps.INSTANCE;
                systemUIAnalyticsWrapper.getClass();
                SystemUIAnalyticsWrapper.sendEventLog(screenId, moreManageApps);
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class TapMenuSetting extends Event {
            public final Screen screen;

            public TapMenuSetting(Screen screen) {
                super(null);
                this.screen = screen;
            }

            @Override // com.android.systemui.controls.ui.util.SALogger.Event
            public final void sendEvent(SystemUIAnalyticsWrapper systemUIAnalyticsWrapper) {
                SystemUIAnalyticsWrapper.ScreenId screenId = this.screen.getScreenId();
                SystemUIAnalyticsWrapper.EventId.MoreSettings moreSettings = SystemUIAnalyticsWrapper.EventId.MoreSettings.INSTANCE;
                systemUIAnalyticsWrapper.getClass();
                SystemUIAnalyticsWrapper.sendEventLog(screenId, moreSettings);
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class TapSmallTypeCard extends Event {
            public final String deviceName;
            public final String deviceType;

            public TapSmallTypeCard(String str, String str2) {
                super(null);
                this.deviceName = str;
                this.deviceType = str2;
            }

            @Override // com.android.systemui.controls.ui.util.SALogger.Event
            public final void sendEvent(SystemUIAnalyticsWrapper systemUIAnalyticsWrapper) {
                SystemUIAnalyticsWrapper.ScreenId.MainScreen mainScreen = SystemUIAnalyticsWrapper.ScreenId.MainScreen.INSTANCE;
                SystemUIAnalyticsWrapper.EventId.TapSmallTypeCard tapSmallTypeCard = SystemUIAnalyticsWrapper.EventId.TapSmallTypeCard.INSTANCE;
                SystemUIAnalyticsWrapper.KeyId.DeviceName deviceName = SystemUIAnalyticsWrapper.KeyId.DeviceName.INSTANCE;
                String str = this.deviceName;
                SystemUIAnalyticsWrapper.KeyId.DeviceType deviceType = SystemUIAnalyticsWrapper.KeyId.DeviceType.INSTANCE;
                String str2 = this.deviceType;
                systemUIAnalyticsWrapper.getClass();
                mainScreen.getClass();
                tapSmallTypeCard.getClass();
                deviceName.getClass();
                deviceType.getClass();
                SystemUIAnalytics.sendEventCDLog("Dvcs05", "Dvcs054", "Device name", str, "Device type", str2);
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class TapSpinnerApp extends Event {
            public final String selectedApp;

            public TapSpinnerApp(String str) {
                super(null);
                this.selectedApp = str;
            }

            @Override // com.android.systemui.controls.ui.util.SALogger.Event
            public final void sendEvent(SystemUIAnalyticsWrapper systemUIAnalyticsWrapper) {
                SystemUIAnalyticsWrapper.ScreenId.MainScreen mainScreen = SystemUIAnalyticsWrapper.ScreenId.MainScreen.INSTANCE;
                SystemUIAnalyticsWrapper.EventId.TapSpinnerApp tapSpinnerApp = SystemUIAnalyticsWrapper.EventId.TapSpinnerApp.INSTANCE;
                systemUIAnalyticsWrapper.getClass();
                SystemUIAnalyticsWrapper.sendEventLog(mainScreen, tapSpinnerApp, this.selectedApp);
            }
        }

        private Event() {
        }

        public /* synthetic */ Event(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static String getTemplateType(Behavior behavior) {
            Behavior behavior2;
            if (!(behavior instanceof StatusBehavior)) {
                if (behavior instanceof TouchBehavior) {
                    if (((TouchBehavior) behavior).getTemplate() instanceof StatelessTemplate) {
                        return "Stateless toggle";
                    }
                } else {
                    if (behavior instanceof ToggleBehavior) {
                        return "Toggle";
                    }
                    if (behavior instanceof ToggleRangeBehavior) {
                        return ((ToggleRangeBehavior) behavior).isToggleable ? "Toggle with slider" : "Range";
                    }
                    if ((behavior instanceof TemperatureControlBehavior) && (behavior2 = ((TemperatureControlBehavior) behavior).subBehavior) != null) {
                        return getTemplateType(behavior2);
                    }
                }
            }
            return "Tap to open";
        }

        public abstract void sendEvent(SystemUIAnalyticsWrapper systemUIAnalyticsWrapper);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class Screen {

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class ChooseDevices extends Screen {
            public static final ChooseDevices INSTANCE = new ChooseDevices();

            private ChooseDevices() {
                super(null);
            }

            @Override // com.android.systemui.controls.ui.util.SALogger.Screen
            public final SystemUIAnalyticsWrapper.ScreenId getScreenId() {
                return SystemUIAnalyticsWrapper.ScreenId.ChooseDevices.INSTANCE;
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class CustomPanel extends Screen {
            public static final CustomPanel INSTANCE = new CustomPanel();

            private CustomPanel() {
                super(null);
            }

            @Override // com.android.systemui.controls.ui.util.SALogger.Screen
            public final SystemUIAnalyticsWrapper.ScreenId getScreenId() {
                return SystemUIAnalyticsWrapper.ScreenId.CustomPanel.INSTANCE;
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class Intro extends Screen {
            public static final Intro INSTANCE = new Intro();

            private Intro() {
                super(null);
            }

            @Override // com.android.systemui.controls.ui.util.SALogger.Screen
            public final SystemUIAnalyticsWrapper.ScreenId getScreenId() {
                return SystemUIAnalyticsWrapper.ScreenId.Intro.INSTANCE;
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class IntroNoAppsToShow extends Screen {
            public static final IntroNoAppsToShow INSTANCE = new IntroNoAppsToShow();

            private IntroNoAppsToShow() {
                super(null);
            }

            @Override // com.android.systemui.controls.ui.util.SALogger.Screen
            public final SystemUIAnalyticsWrapper.ScreenId getScreenId() {
                return SystemUIAnalyticsWrapper.ScreenId.IntroNoAppsToShow.INSTANCE;
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class MainScreen extends Screen {
            public static final MainScreen INSTANCE = new MainScreen();

            private MainScreen() {
                super(null);
            }

            @Override // com.android.systemui.controls.ui.util.SALogger.Screen
            public final SystemUIAnalyticsWrapper.ScreenId getScreenId() {
                return SystemUIAnalyticsWrapper.ScreenId.MainScreen.INSTANCE;
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class ManageApps extends Screen {
            public static final ManageApps INSTANCE = new ManageApps();

            private ManageApps() {
                super(null);
            }

            @Override // com.android.systemui.controls.ui.util.SALogger.Screen
            public final SystemUIAnalyticsWrapper.ScreenId getScreenId() {
                return SystemUIAnalyticsWrapper.ScreenId.ManageApps.INSTANCE;
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class NoDeviceSelected extends Screen {
            public static final NoDeviceSelected INSTANCE = new NoDeviceSelected();

            private NoDeviceSelected() {
                super(null);
            }

            @Override // com.android.systemui.controls.ui.util.SALogger.Screen
            public final SystemUIAnalyticsWrapper.ScreenId getScreenId() {
                return SystemUIAnalyticsWrapper.ScreenId.NoDeviceSelected.INSTANCE;
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class Settings extends Screen {
            public static final Settings INSTANCE = new Settings();

            private Settings() {
                super(null);
            }

            @Override // com.android.systemui.controls.ui.util.SALogger.Screen
            public final SystemUIAnalyticsWrapper.ScreenId getScreenId() {
                return SystemUIAnalyticsWrapper.ScreenId.Settings.INSTANCE;
            }
        }

        private Screen() {
        }

        public /* synthetic */ Screen(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public abstract SystemUIAnalyticsWrapper.ScreenId getScreenId();
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class StatusEvent {

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class DeviceAppStatus extends StatusEvent {
            public final AppStatusList appList;

            public DeviceAppStatus(AppStatusList appStatusList) {
                super(null);
                this.appList = appStatusList;
            }

            @Override // com.android.systemui.controls.ui.util.SALogger.StatusEvent
            public final SystemUIAnalyticsWrapper.StatusEventId getKey() {
                return SystemUIAnalyticsWrapper.StatusEventId.DevicesAppsStatus.INSTANCE;
            }

            @Override // com.android.systemui.controls.ui.util.SALogger.StatusEvent
            public final String getValue() {
                return new Gson().toJson(this.appList);
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class NumberOfApps extends StatusEvent {
            public final int selectedApps;
            public final int totalApps;

            public NumberOfApps(int i, int i2) {
                super(null);
                this.selectedApps = i;
                this.totalApps = i2;
            }

            @Override // com.android.systemui.controls.ui.util.SALogger.StatusEvent
            public final SystemUIAnalyticsWrapper.StatusEventId getKey() {
                return SystemUIAnalyticsWrapper.StatusEventId.NumberOfAppsInDevices.INSTANCE;
            }

            @Override // com.android.systemui.controls.ui.util.SALogger.StatusEvent
            public final String getValue() {
                return this.selectedApps + "/" + this.totalApps;
            }
        }

        private StatusEvent() {
        }

        public /* synthetic */ StatusEvent(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public abstract SystemUIAnalyticsWrapper.StatusEventId getKey();

        public abstract String getValue();
    }

    public SALogger(SystemUIAnalyticsWrapper systemUIAnalyticsWrapper) {
        this.systemUIAnalyticsWrapper = systemUIAnalyticsWrapper;
    }

    public final void sendEvent(Event event) {
        event.sendEvent(this.systemUIAnalyticsWrapper);
    }

    public final void sendScreenView(Screen screen) {
        SystemUIAnalyticsWrapper.ScreenId screenId = screen.getScreenId();
        this.systemUIAnalyticsWrapper.getClass();
        SystemUIAnalytics.sendScreenViewLog(screenId.getScreenId());
    }

    public final void sendStatusEvent(Context context, StatusEvent statusEvent) {
        ControlsPreference.Companion companion = ControlsPreference.Companion;
        SystemUIAnalyticsWrapper.StatusEventId key = statusEvent.getKey();
        this.systemUIAnalyticsWrapper.getClass();
        String statusEventId = key.getStatusEventId();
        String value = statusEvent.getValue();
        companion.getClass();
        context.getSharedPreferences("controls_prefs", 0).edit().putString(statusEventId, value).apply();
    }
}
