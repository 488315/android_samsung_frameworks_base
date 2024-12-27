package com.android.systemui.controls.util;

import android.content.Context;
import android.service.controls.templates.ControlTemplate;
import android.service.controls.templates.StatelessTemplate;
import android.widget.ImageView;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import com.android.systemui.controls.ui.Behavior;
import com.android.systemui.controls.ui.ControlViewHolder;
import com.android.systemui.controls.ui.StatusBehavior;
import com.android.systemui.controls.ui.TemperatureControlBehavior;
import com.android.systemui.controls.ui.ToggleBehavior;
import com.android.systemui.controls.ui.ToggleRangeBehavior;
import com.android.systemui.controls.ui.TouchBehavior;
import com.android.systemui.controls.ui.view.ControlsActionButton;
import com.android.systemui.controls.util.ControlsPreference;
import com.android.systemui.controls.util.SystemUIAnalyticsWrapper;
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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SALogger {
    public static final Companion Companion = new Companion(null);
    public final SystemUIAnalyticsWrapper systemUIAnalyticsWrapper;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class AppStatus {

        @SerializedName(SystemUIAnalytics.CONTROL_KEY_APP_NAME)
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
            return MotionLayout$$ExternalSyntheticOutline0.m("AppStatus(appName=", this.appName, ", value=", this.value, ")");
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public abstract class Event {

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public final class AddDevices extends Event {
            public static final AddDevices INSTANCE = new AddDevices();

            private AddDevices() {
                super(null);
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof AddDevices);
            }

            public final int hashCode() {
                return 1202118221;
            }

            @Override // com.android.systemui.controls.util.SALogger.Event
            public final void sendEvent(SystemUIAnalyticsWrapper systemUIAnalyticsWrapper) {
                SystemUIAnalyticsWrapper.ScreenId.NoDeviceSelected noDeviceSelected = SystemUIAnalyticsWrapper.ScreenId.NoDeviceSelected.INSTANCE;
                SystemUIAnalyticsWrapper.EventId.AddDevices addDevices = SystemUIAnalyticsWrapper.EventId.AddDevices.INSTANCE;
                systemUIAnalyticsWrapper.getClass();
                SystemUIAnalyticsWrapper.sendEventLog(noDeviceSelected, addDevices);
            }

            public final String toString() {
                return "AddDevices";
            }
        }

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public final class ChooseAppOnOff extends Event {
            public final boolean checked;

            public ChooseAppOnOff(boolean z) {
                super(null);
                this.checked = z;
            }

            @Override // com.android.systemui.controls.util.SALogger.Event
            public final void sendEvent(SystemUIAnalyticsWrapper systemUIAnalyticsWrapper) {
                SystemUIAnalyticsWrapper.ScreenId.Intro intro = SystemUIAnalyticsWrapper.ScreenId.Intro.INSTANCE;
                SystemUIAnalyticsWrapper.EventId.ChooseAppsOnOff chooseAppsOnOff = SystemUIAnalyticsWrapper.EventId.ChooseAppsOnOff.INSTANCE;
                SALogger.Companion.getClass();
                String valueOf = String.valueOf(Boolean.compare(this.checked, false));
                systemUIAnalyticsWrapper.getClass();
                intro.getClass();
                chooseAppsOnOff.getClass();
                SystemUIAnalytics.sendEventLog(SystemUIAnalytics.SID_CONTROL_INTRO, SystemUIAnalytics.EID_CONTROL_CHOOSE_APPS_ON_OFF, valueOf);
            }
        }

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public final class ChooseAppOnOffOnManageApps extends Event {
            public final boolean checked;

            public ChooseAppOnOffOnManageApps(boolean z) {
                super(null);
                this.checked = z;
            }

            @Override // com.android.systemui.controls.util.SALogger.Event
            public final void sendEvent(SystemUIAnalyticsWrapper systemUIAnalyticsWrapper) {
                SystemUIAnalyticsWrapper.ScreenId.ManageApps manageApps = SystemUIAnalyticsWrapper.ScreenId.ManageApps.INSTANCE;
                SystemUIAnalyticsWrapper.EventId.ChooseAppsOnOffOnManageApps chooseAppsOnOffOnManageApps = SystemUIAnalyticsWrapper.EventId.ChooseAppsOnOffOnManageApps.INSTANCE;
                SALogger.Companion.getClass();
                String valueOf = String.valueOf(Boolean.compare(this.checked, false));
                systemUIAnalyticsWrapper.getClass();
                manageApps.getClass();
                chooseAppsOnOffOnManageApps.getClass();
                SystemUIAnalytics.sendEventLog(SystemUIAnalytics.SID_CONTROL_MANAGE_APPS, SystemUIAnalytics.EID_CONTROL_CHOOSE_APPS_ON_OFF_ON_MANAGE_APPS, valueOf);
            }
        }

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public final class IntroStart extends Event {
            public final int selectedApps;
            public final int totalApps;

            public IntroStart(int i, int i2) {
                super(null);
                this.selectedApps = i;
                this.totalApps = i2;
            }

            @Override // com.android.systemui.controls.util.SALogger.Event
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
                SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.SID_CONTROL_INTRO, SystemUIAnalytics.EID_CONTROL_INTRO_START, SystemUIAnalytics.CONTROL_KEY_NUM_OF_SELECTED_APPS, valueOf, SystemUIAnalytics.CONTROL_KEY_NUM_OF_TOTAL_APPS, valueOf2);
            }
        }

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public final class LaunchDevices extends Event {
            public static final LaunchDevices INSTANCE = new LaunchDevices();

            private LaunchDevices() {
                super(null);
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof LaunchDevices);
            }

            public final int hashCode() {
                return -609846599;
            }

            @Override // com.android.systemui.controls.util.SALogger.Event
            public final void sendEvent(SystemUIAnalyticsWrapper systemUIAnalyticsWrapper) {
                SystemUIAnalyticsWrapper.EventId.LaunchDevices launchDevices = SystemUIAnalyticsWrapper.EventId.LaunchDevices.INSTANCE;
                String currentScreenID = SystemUIAnalytics.getCurrentScreenID();
                launchDevices.getClass();
                SystemUIAnalytics.sendEventLog(currentScreenID, SystemUIAnalytics.EID_CONTROL_LAUNCH_DEVICES);
            }

            public final String toString() {
                return "LaunchDevices";
            }
        }

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public final class LaunchFullController extends Event {
            public static final LaunchFullController INSTANCE = new LaunchFullController();

            private LaunchFullController() {
                super(null);
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof LaunchFullController);
            }

            public final int hashCode() {
                return 1470225967;
            }

            @Override // com.android.systemui.controls.util.SALogger.Event
            public final void sendEvent(SystemUIAnalyticsWrapper systemUIAnalyticsWrapper) {
                SystemUIAnalyticsWrapper.ScreenId.CustomPanel customPanel = SystemUIAnalyticsWrapper.ScreenId.CustomPanel.INSTANCE;
                SystemUIAnalyticsWrapper.EventId.LaunchFullController launchFullController = SystemUIAnalyticsWrapper.EventId.LaunchFullController.INSTANCE;
                systemUIAnalyticsWrapper.getClass();
                SystemUIAnalyticsWrapper.sendEventLog(customPanel, launchFullController);
            }

            public final String toString() {
                return "LaunchFullController";
            }
        }

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public final class LaunchSmartThings extends Event {
            public static final LaunchSmartThings INSTANCE = new LaunchSmartThings();

            private LaunchSmartThings() {
                super(null);
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof LaunchSmartThings);
            }

            public final int hashCode() {
                return -1729765302;
            }

            @Override // com.android.systemui.controls.util.SALogger.Event
            public final void sendEvent(SystemUIAnalyticsWrapper systemUIAnalyticsWrapper) {
                SystemUIAnalyticsWrapper.ScreenId.MainScreen mainScreen = SystemUIAnalyticsWrapper.ScreenId.MainScreen.INSTANCE;
                SystemUIAnalyticsWrapper.EventId.LaunchSmartThings launchSmartThings = SystemUIAnalyticsWrapper.EventId.LaunchSmartThings.INSTANCE;
                systemUIAnalyticsWrapper.getClass();
                SystemUIAnalyticsWrapper.sendEventLog(mainScreen, launchSmartThings);
            }

            public final String toString() {
                return "LaunchSmartThings";
            }
        }

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

            @Override // com.android.systemui.controls.util.SALogger.Event
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
                chooseDevices.getClass();
                leftChooseDevices.getClass();
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
                SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.SID_CONTROL_CHOOSE_DEVICES, SystemUIAnalytics.EID_CONTROL_LEFT_CHOOSE_DEVICES, linkedHashMap);
            }
        }

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public final class MoveCard extends Event {
            public static final MoveCard INSTANCE = new MoveCard();

            private MoveCard() {
                super(null);
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof MoveCard);
            }

            public final int hashCode() {
                return 554161170;
            }

            @Override // com.android.systemui.controls.util.SALogger.Event
            public final void sendEvent(SystemUIAnalyticsWrapper systemUIAnalyticsWrapper) {
                SystemUIAnalyticsWrapper.ScreenId.MainScreen mainScreen = SystemUIAnalyticsWrapper.ScreenId.MainScreen.INSTANCE;
                SystemUIAnalyticsWrapper.EventId.MoveCard moveCard = SystemUIAnalyticsWrapper.EventId.MoveCard.INSTANCE;
                systemUIAnalyticsWrapper.getClass();
                SystemUIAnalyticsWrapper.sendEventLog(mainScreen, moveCard);
            }

            public final String toString() {
                return "MoveCard";
            }
        }

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public final class OpenSpinner extends Event {
            public static final OpenSpinner INSTANCE = new OpenSpinner();

            private OpenSpinner() {
                super(null);
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof OpenSpinner);
            }

            public final int hashCode() {
                return 569748798;
            }

            @Override // com.android.systemui.controls.util.SALogger.Event
            public final void sendEvent(SystemUIAnalyticsWrapper systemUIAnalyticsWrapper) {
                SystemUIAnalyticsWrapper.ScreenId.MainScreen mainScreen = SystemUIAnalyticsWrapper.ScreenId.MainScreen.INSTANCE;
                SystemUIAnalyticsWrapper.EventId.OpenSpinner openSpinner = SystemUIAnalyticsWrapper.EventId.OpenSpinner.INSTANCE;
                systemUIAnalyticsWrapper.getClass();
                SystemUIAnalyticsWrapper.sendEventLog(mainScreen, openSpinner);
            }

            public final String toString() {
                return "OpenSpinner";
            }
        }

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public final class QuitDevices extends Event {
            public final Screen screen;

            public QuitDevices(Screen screen) {
                super(null);
                this.screen = screen;
            }

            @Override // com.android.systemui.controls.util.SALogger.Event
            public final void sendEvent(SystemUIAnalyticsWrapper systemUIAnalyticsWrapper) {
                SystemUIAnalyticsWrapper.ScreenId screenId = this.screen.getScreenId();
                SystemUIAnalyticsWrapper.EventId.QuitDevices quitDevices = SystemUIAnalyticsWrapper.EventId.QuitDevices.INSTANCE;
                systemUIAnalyticsWrapper.getClass();
                SystemUIAnalyticsWrapper.sendEventLog(screenId, quitDevices);
            }
        }

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public final class Reorder extends Event {
            public static final Reorder INSTANCE = new Reorder();

            private Reorder() {
                super(null);
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof Reorder);
            }

            public final int hashCode() {
                return -1794477750;
            }

            @Override // com.android.systemui.controls.util.SALogger.Event
            public final void sendEvent(SystemUIAnalyticsWrapper systemUIAnalyticsWrapper) {
                SystemUIAnalyticsWrapper.ScreenId.ChooseDevices chooseDevices = SystemUIAnalyticsWrapper.ScreenId.ChooseDevices.INSTANCE;
                SystemUIAnalyticsWrapper.EventId.Reorder reorder = SystemUIAnalyticsWrapper.EventId.Reorder.INSTANCE;
                systemUIAnalyticsWrapper.getClass();
                SystemUIAnalyticsWrapper.sendEventLog(chooseDevices, reorder);
            }

            public final String toString() {
                return "Reorder";
            }
        }

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public final class SettingsControlDevicesOnOff extends Event {
            public final boolean checked;

            public SettingsControlDevicesOnOff(boolean z) {
                super(null);
                this.checked = z;
            }

            @Override // com.android.systemui.controls.util.SALogger.Event
            public final void sendEvent(SystemUIAnalyticsWrapper systemUIAnalyticsWrapper) {
                SystemUIAnalyticsWrapper.ScreenId.Settings settings = SystemUIAnalyticsWrapper.ScreenId.Settings.INSTANCE;
                SystemUIAnalyticsWrapper.EventId.SettingsControlDevicesOnOff settingsControlDevicesOnOff = SystemUIAnalyticsWrapper.EventId.SettingsControlDevicesOnOff.INSTANCE;
                SALogger.Companion.getClass();
                String valueOf = String.valueOf(Boolean.compare(this.checked, false));
                systemUIAnalyticsWrapper.getClass();
                settings.getClass();
                settingsControlDevicesOnOff.getClass();
                SystemUIAnalytics.sendEventLog(SystemUIAnalytics.SID_CONTROL_SETTINGS, SystemUIAnalytics.EID_CONTROL_CONTROL_DEVICES_ON_OFF, valueOf);
            }
        }

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public final class SettingsShowDevicesOnOff extends Event {
            public final boolean checked;

            public SettingsShowDevicesOnOff(boolean z) {
                super(null);
                this.checked = z;
            }

            @Override // com.android.systemui.controls.util.SALogger.Event
            public final void sendEvent(SystemUIAnalyticsWrapper systemUIAnalyticsWrapper) {
                SystemUIAnalyticsWrapper.ScreenId.Settings settings = SystemUIAnalyticsWrapper.ScreenId.Settings.INSTANCE;
                SystemUIAnalyticsWrapper.EventId.SettingsShowDevicesOnOff settingsShowDevicesOnOff = SystemUIAnalyticsWrapper.EventId.SettingsShowDevicesOnOff.INSTANCE;
                SALogger.Companion.getClass();
                String valueOf = String.valueOf(Boolean.compare(this.checked, false));
                systemUIAnalyticsWrapper.getClass();
                settings.getClass();
                settingsShowDevicesOnOff.getClass();
                SystemUIAnalytics.sendEventLog(SystemUIAnalytics.SID_CONTROL_SETTINGS, SystemUIAnalytics.EID_CONTROL_SHOW_DEVICES_ON_OFF, valueOf);
            }
        }

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public final class TapAppList extends Event {
            public static final TapAppList INSTANCE = new TapAppList();

            private TapAppList() {
                super(null);
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof TapAppList);
            }

            public final int hashCode() {
                return -24376691;
            }

            @Override // com.android.systemui.controls.util.SALogger.Event
            public final void sendEvent(SystemUIAnalyticsWrapper systemUIAnalyticsWrapper) {
                SystemUIAnalyticsWrapper.ScreenId.Intro intro = SystemUIAnalyticsWrapper.ScreenId.Intro.INSTANCE;
                SystemUIAnalyticsWrapper.EventId.TapAppList tapAppList = SystemUIAnalyticsWrapper.EventId.TapAppList.INSTANCE;
                systemUIAnalyticsWrapper.getClass();
                SystemUIAnalyticsWrapper.sendEventLog(intro, tapAppList);
            }

            public final String toString() {
                return "TapAppList";
            }
        }

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public final class TapAppListOnManageApps extends Event {
            public static final TapAppListOnManageApps INSTANCE = new TapAppListOnManageApps();

            private TapAppListOnManageApps() {
                super(null);
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof TapAppListOnManageApps);
            }

            public final int hashCode() {
                return 1854075875;
            }

            @Override // com.android.systemui.controls.util.SALogger.Event
            public final void sendEvent(SystemUIAnalyticsWrapper systemUIAnalyticsWrapper) {
                SystemUIAnalyticsWrapper.ScreenId.ManageApps manageApps = SystemUIAnalyticsWrapper.ScreenId.ManageApps.INSTANCE;
                SystemUIAnalyticsWrapper.EventId.TapAppListOnManageApps tapAppListOnManageApps = SystemUIAnalyticsWrapper.EventId.TapAppListOnManageApps.INSTANCE;
                systemUIAnalyticsWrapper.getClass();
                SystemUIAnalyticsWrapper.sendEventLog(manageApps, tapAppListOnManageApps);
            }

            public final String toString() {
                return "TapAppListOnManageApps";
            }
        }

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public final class TapCardLayout extends Event {
            public final ControlViewHolder cvh;

            public TapCardLayout(ControlViewHolder controlViewHolder) {
                super(null);
                this.cvh = controlViewHolder;
            }

            @Override // com.android.systemui.controls.util.SALogger.Event
            public final void sendEvent(SystemUIAnalyticsWrapper systemUIAnalyticsWrapper) {
                ImageView imageView;
                SystemUIAnalyticsWrapper.ScreenId.MainScreen mainScreen = SystemUIAnalyticsWrapper.ScreenId.MainScreen.INSTANCE;
                ControlViewHolder controlViewHolder = this.cvh;
                ControlsActionButton controlsActionButton = controlViewHolder.getSecControlViewHolder().actionIcon;
                SystemUIAnalyticsWrapper.EventId eventId = (controlsActionButton == null || (imageView = controlsActionButton.actionIcon) == null || imageView.getVisibility() != 0) ? SystemUIAnalyticsWrapper.EventId.TapCardWithoutButton.INSTANCE : SystemUIAnalyticsWrapper.EventId.TapCardWithButton.INSTANCE;
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
                SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.SID_CONTROL_MAIN_SCREEN, eventId2, SystemUIAnalytics.CONTROL_KEY_TEMPLATE, templateType, SystemUIAnalytics.CONTROL_KEY_DEVICE_NAME, obj, SystemUIAnalytics.CONTROL_KEY_DEVICE_TYPE, valueOf);
            }
        }

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public final class TapMainActionButton extends Event {
            public final ControlViewHolder cvh;

            public TapMainActionButton(ControlViewHolder controlViewHolder) {
                super(null);
                this.cvh = controlViewHolder;
            }

            @Override // com.android.systemui.controls.util.SALogger.Event
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
                SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.SID_CONTROL_MAIN_SCREEN, SystemUIAnalytics.EID_CONTROL_TAP_MAIN_ACTION_BTN, SystemUIAnalytics.CONTROL_KEY_TEMPLATE, templateType, SystemUIAnalytics.CONTROL_KEY_DEVICE_NAME, obj, SystemUIAnalytics.CONTROL_KEY_DEVICE_TYPE, valueOf);
            }
        }

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public final class TapMenuDevicesToShow extends Event {
            public final Screen screen;

            public TapMenuDevicesToShow(Screen screen) {
                super(null);
                this.screen = screen;
            }

            @Override // com.android.systemui.controls.util.SALogger.Event
            public final void sendEvent(SystemUIAnalyticsWrapper systemUIAnalyticsWrapper) {
                SystemUIAnalyticsWrapper.ScreenId screenId = this.screen.getScreenId();
                SystemUIAnalyticsWrapper.EventId.MoreDevicesToShow moreDevicesToShow = SystemUIAnalyticsWrapper.EventId.MoreDevicesToShow.INSTANCE;
                systemUIAnalyticsWrapper.getClass();
                SystemUIAnalyticsWrapper.sendEventLog(screenId, moreDevicesToShow);
            }
        }

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public final class TapMenuManageApp extends Event {
            public final Screen screen;

            public TapMenuManageApp(Screen screen) {
                super(null);
                this.screen = screen;
            }

            @Override // com.android.systemui.controls.util.SALogger.Event
            public final void sendEvent(SystemUIAnalyticsWrapper systemUIAnalyticsWrapper) {
                SystemUIAnalyticsWrapper.ScreenId screenId = this.screen.getScreenId();
                SystemUIAnalyticsWrapper.EventId.MoreManageApps moreManageApps = SystemUIAnalyticsWrapper.EventId.MoreManageApps.INSTANCE;
                systemUIAnalyticsWrapper.getClass();
                SystemUIAnalyticsWrapper.sendEventLog(screenId, moreManageApps);
            }
        }

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public final class TapMenuSetting extends Event {
            public final Screen screen;

            public TapMenuSetting(Screen screen) {
                super(null);
                this.screen = screen;
            }

            @Override // com.android.systemui.controls.util.SALogger.Event
            public final void sendEvent(SystemUIAnalyticsWrapper systemUIAnalyticsWrapper) {
                SystemUIAnalyticsWrapper.ScreenId screenId = this.screen.getScreenId();
                SystemUIAnalyticsWrapper.EventId.MoreSettings moreSettings = SystemUIAnalyticsWrapper.EventId.MoreSettings.INSTANCE;
                systemUIAnalyticsWrapper.getClass();
                SystemUIAnalyticsWrapper.sendEventLog(screenId, moreSettings);
            }
        }

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public final class TapSmallTypeCard extends Event {
            public final String deviceName;
            public final String deviceType;

            public TapSmallTypeCard(String str, String str2) {
                super(null);
                this.deviceName = str;
                this.deviceType = str2;
            }

            @Override // com.android.systemui.controls.util.SALogger.Event
            public final void sendEvent(SystemUIAnalyticsWrapper systemUIAnalyticsWrapper) {
                SystemUIAnalyticsWrapper.ScreenId.MainScreen mainScreen = SystemUIAnalyticsWrapper.ScreenId.MainScreen.INSTANCE;
                SystemUIAnalyticsWrapper.EventId.TapSmallTypeCard tapSmallTypeCard = SystemUIAnalyticsWrapper.EventId.TapSmallTypeCard.INSTANCE;
                SystemUIAnalyticsWrapper.KeyId.DeviceName deviceName = SystemUIAnalyticsWrapper.KeyId.DeviceName.INSTANCE;
                SystemUIAnalyticsWrapper.KeyId.DeviceType deviceType = SystemUIAnalyticsWrapper.KeyId.DeviceType.INSTANCE;
                systemUIAnalyticsWrapper.getClass();
                mainScreen.getClass();
                tapSmallTypeCard.getClass();
                deviceName.getClass();
                deviceType.getClass();
                SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.SID_CONTROL_MAIN_SCREEN, SystemUIAnalytics.EID_CONTROL_TAP_SMALL_TYPE_CARD, SystemUIAnalytics.CONTROL_KEY_DEVICE_NAME, this.deviceName, SystemUIAnalytics.CONTROL_KEY_DEVICE_TYPE, this.deviceType);
            }
        }

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public final class TapSpinnerApp extends Event {
            public final String selectedApp;

            public TapSpinnerApp(String str) {
                super(null);
                this.selectedApp = str;
            }

            @Override // com.android.systemui.controls.util.SALogger.Event
            public final void sendEvent(SystemUIAnalyticsWrapper systemUIAnalyticsWrapper) {
                SystemUIAnalyticsWrapper.ScreenId.MainScreen mainScreen = SystemUIAnalyticsWrapper.ScreenId.MainScreen.INSTANCE;
                SystemUIAnalyticsWrapper.EventId.TapSpinnerApp tapSpinnerApp = SystemUIAnalyticsWrapper.EventId.TapSpinnerApp.INSTANCE;
                systemUIAnalyticsWrapper.getClass();
                mainScreen.getClass();
                tapSpinnerApp.getClass();
                SystemUIAnalytics.sendEventLog(SystemUIAnalytics.SID_CONTROL_MAIN_SCREEN, SystemUIAnalytics.EID_CONTROL_TAP_SPINNER_APP, this.selectedApp);
            }
        }

        private Event() {
        }

        public static String getTemplateType(Behavior behavior) {
            Behavior behavior2;
            if (behavior instanceof StatusBehavior) {
                return "Tap to open";
            }
            if (!(behavior instanceof TouchBehavior)) {
                return behavior instanceof ToggleBehavior ? "Toggle" : behavior instanceof ToggleRangeBehavior ? ((ToggleRangeBehavior) behavior).isToggleable ? "Toggle with slider" : "Range" : (!(behavior instanceof TemperatureControlBehavior) || (behavior2 = ((TemperatureControlBehavior) behavior).subBehavior) == null) ? "Tap to open" : getTemplateType(behavior2);
            }
            ControlTemplate controlTemplate = ((TouchBehavior) behavior).template;
            if (controlTemplate == null) {
                controlTemplate = null;
            }
            return controlTemplate instanceof StatelessTemplate ? "Stateless toggle" : "Tap to open";
        }

        public abstract void sendEvent(SystemUIAnalyticsWrapper systemUIAnalyticsWrapper);

        public /* synthetic */ Event(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public abstract class Screen {

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public final class ChooseDevices extends Screen {
            public static final ChooseDevices INSTANCE = new ChooseDevices();

            private ChooseDevices() {
                super(null);
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof ChooseDevices);
            }

            @Override // com.android.systemui.controls.util.SALogger.Screen
            public final SystemUIAnalyticsWrapper.ScreenId getScreenId() {
                return SystemUIAnalyticsWrapper.ScreenId.ChooseDevices.INSTANCE;
            }

            public final int hashCode() {
                return 1930769281;
            }

            public final String toString() {
                return "ChooseDevices";
            }
        }

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public final class CustomPanel extends Screen {
            public static final CustomPanel INSTANCE = new CustomPanel();

            private CustomPanel() {
                super(null);
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof CustomPanel);
            }

            @Override // com.android.systemui.controls.util.SALogger.Screen
            public final SystemUIAnalyticsWrapper.ScreenId getScreenId() {
                return SystemUIAnalyticsWrapper.ScreenId.CustomPanel.INSTANCE;
            }

            public final int hashCode() {
                return -995222386;
            }

            public final String toString() {
                return "CustomPanel";
            }
        }

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public final class Intro extends Screen {
            public static final Intro INSTANCE = new Intro();

            private Intro() {
                super(null);
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof Intro);
            }

            @Override // com.android.systemui.controls.util.SALogger.Screen
            public final SystemUIAnalyticsWrapper.ScreenId getScreenId() {
                return SystemUIAnalyticsWrapper.ScreenId.Intro.INSTANCE;
            }

            public final int hashCode() {
                return 510422567;
            }

            public final String toString() {
                return "Intro";
            }
        }

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public final class IntroNoAppsToShow extends Screen {
            public static final IntroNoAppsToShow INSTANCE = new IntroNoAppsToShow();

            private IntroNoAppsToShow() {
                super(null);
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof IntroNoAppsToShow);
            }

            @Override // com.android.systemui.controls.util.SALogger.Screen
            public final SystemUIAnalyticsWrapper.ScreenId getScreenId() {
                return SystemUIAnalyticsWrapper.ScreenId.IntroNoAppsToShow.INSTANCE;
            }

            public final int hashCode() {
                return 379088274;
            }

            public final String toString() {
                return "IntroNoAppsToShow";
            }
        }

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public final class MainScreen extends Screen {
            public static final MainScreen INSTANCE = new MainScreen();

            private MainScreen() {
                super(null);
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof MainScreen);
            }

            @Override // com.android.systemui.controls.util.SALogger.Screen
            public final SystemUIAnalyticsWrapper.ScreenId getScreenId() {
                return SystemUIAnalyticsWrapper.ScreenId.MainScreen.INSTANCE;
            }

            public final int hashCode() {
                return -771991382;
            }

            public final String toString() {
                return "MainScreen";
            }
        }

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public final class ManageApps extends Screen {
            public static final ManageApps INSTANCE = new ManageApps();

            private ManageApps() {
                super(null);
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof ManageApps);
            }

            @Override // com.android.systemui.controls.util.SALogger.Screen
            public final SystemUIAnalyticsWrapper.ScreenId getScreenId() {
                return SystemUIAnalyticsWrapper.ScreenId.ManageApps.INSTANCE;
            }

            public final int hashCode() {
                return 1272460956;
            }

            public final String toString() {
                return "ManageApps";
            }
        }

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public final class NoDeviceSelected extends Screen {
            public static final NoDeviceSelected INSTANCE = new NoDeviceSelected();

            private NoDeviceSelected() {
                super(null);
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof NoDeviceSelected);
            }

            @Override // com.android.systemui.controls.util.SALogger.Screen
            public final SystemUIAnalyticsWrapper.ScreenId getScreenId() {
                return SystemUIAnalyticsWrapper.ScreenId.NoDeviceSelected.INSTANCE;
            }

            public final int hashCode() {
                return 1016748183;
            }

            public final String toString() {
                return "NoDeviceSelected";
            }
        }

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public final class Settings extends Screen {
            public static final Settings INSTANCE = new Settings();

            private Settings() {
                super(null);
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof Settings);
            }

            @Override // com.android.systemui.controls.util.SALogger.Screen
            public final SystemUIAnalyticsWrapper.ScreenId getScreenId() {
                return SystemUIAnalyticsWrapper.ScreenId.Settings.INSTANCE;
            }

            public final int hashCode() {
                return -1628088696;
            }

            public final String toString() {
                return "Settings";
            }
        }

        private Screen() {
        }

        public abstract SystemUIAnalyticsWrapper.ScreenId getScreenId();

        public /* synthetic */ Screen(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public abstract class StatusEvent {

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public final class DeviceAppStatus extends StatusEvent {
            public final AppStatusList appList;

            public DeviceAppStatus(AppStatusList appStatusList) {
                super(null);
                this.appList = appStatusList;
            }

            @Override // com.android.systemui.controls.util.SALogger.StatusEvent
            public final SystemUIAnalyticsWrapper.StatusEventId getKey() {
                return SystemUIAnalyticsWrapper.StatusEventId.DevicesAppsStatus.INSTANCE;
            }

            @Override // com.android.systemui.controls.util.SALogger.StatusEvent
            public final String getValue() {
                return new Gson().toJson(this.appList);
            }
        }

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public final class NumberOfApps extends StatusEvent {
            public final int selectedApps;
            public final int totalApps;

            public NumberOfApps(int i, int i2) {
                super(null);
                this.selectedApps = i;
                this.totalApps = i2;
            }

            @Override // com.android.systemui.controls.util.SALogger.StatusEvent
            public final SystemUIAnalyticsWrapper.StatusEventId getKey() {
                return SystemUIAnalyticsWrapper.StatusEventId.NumberOfAppsInDevices.INSTANCE;
            }

            @Override // com.android.systemui.controls.util.SALogger.StatusEvent
            public final String getValue() {
                return this.selectedApps + "/" + this.totalApps;
            }
        }

        private StatusEvent() {
        }

        public abstract SystemUIAnalyticsWrapper.StatusEventId getKey();

        public abstract String getValue();

        public /* synthetic */ StatusEvent(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
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
        context.getSharedPreferences(SystemUIAnalytics.CONTROL_PREF_NAME, 0).edit().putString(statusEventId, value).apply();
    }
}
