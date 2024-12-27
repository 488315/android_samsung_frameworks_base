package com.android.systemui.controls.util;

import com.android.systemui.util.SystemUIAnalytics;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class SystemUIAnalyticsWrapper {

    public abstract class EventId {

        public final class AddDevices extends EventId {
            public static final AddDevices INSTANCE = new AddDevices();

            private AddDevices() {
                super(null);
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof AddDevices);
            }

            @Override // com.android.systemui.controls.util.SystemUIAnalyticsWrapper.EventId
            public final String getEventId() {
                return SystemUIAnalytics.EID_CONTROL_ADD_DEVICES;
            }

            public final int hashCode() {
                return -146672064;
            }

            public final String toString() {
                return "AddDevices";
            }
        }

        public final class ChooseAppsOnOff extends EventId {
            public static final ChooseAppsOnOff INSTANCE = new ChooseAppsOnOff();

            private ChooseAppsOnOff() {
                super(null);
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof ChooseAppsOnOff);
            }

            @Override // com.android.systemui.controls.util.SystemUIAnalyticsWrapper.EventId
            public final String getEventId() {
                return SystemUIAnalytics.EID_CONTROL_CHOOSE_APPS_ON_OFF;
            }

            public final int hashCode() {
                return -1238093661;
            }

            public final String toString() {
                return "ChooseAppsOnOff";
            }
        }

        public final class ChooseAppsOnOffOnManageApps extends EventId {
            public static final ChooseAppsOnOffOnManageApps INSTANCE = new ChooseAppsOnOffOnManageApps();

            private ChooseAppsOnOffOnManageApps() {
                super(null);
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof ChooseAppsOnOffOnManageApps);
            }

            @Override // com.android.systemui.controls.util.SystemUIAnalyticsWrapper.EventId
            public final String getEventId() {
                return SystemUIAnalytics.EID_CONTROL_CHOOSE_APPS_ON_OFF_ON_MANAGE_APPS;
            }

            public final int hashCode() {
                return -597382919;
            }

            public final String toString() {
                return "ChooseAppsOnOffOnManageApps";
            }
        }

        public final class IntroStart extends EventId {
            public static final IntroStart INSTANCE = new IntroStart();

            private IntroStart() {
                super(null);
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof IntroStart);
            }

            @Override // com.android.systemui.controls.util.SystemUIAnalyticsWrapper.EventId
            public final String getEventId() {
                return SystemUIAnalytics.EID_CONTROL_INTRO_START;
            }

            public final int hashCode() {
                return 1926464154;
            }

            public final String toString() {
                return "IntroStart";
            }
        }

        public final class LaunchDevices extends EventId {
            public static final LaunchDevices INSTANCE = new LaunchDevices();

            private LaunchDevices() {
                super(null);
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof LaunchDevices);
            }

            @Override // com.android.systemui.controls.util.SystemUIAnalyticsWrapper.EventId
            public final String getEventId() {
                return SystemUIAnalytics.EID_CONTROL_LAUNCH_DEVICES;
            }

            public final int hashCode() {
                return 1292794342;
            }

            public final String toString() {
                return "LaunchDevices";
            }
        }

        public final class LaunchFullController extends EventId {
            public static final LaunchFullController INSTANCE = new LaunchFullController();

            private LaunchFullController() {
                super(null);
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof LaunchFullController);
            }

            @Override // com.android.systemui.controls.util.SystemUIAnalyticsWrapper.EventId
            public final String getEventId() {
                return SystemUIAnalytics.EID_CONTROL_LAUNCH_FULL_CONTROLLER;
            }

            public final int hashCode() {
                return 1654776418;
            }

            public final String toString() {
                return "LaunchFullController";
            }
        }

        public final class LaunchSmartThings extends EventId {
            public static final LaunchSmartThings INSTANCE = new LaunchSmartThings();

            private LaunchSmartThings() {
                super(null);
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof LaunchSmartThings);
            }

            @Override // com.android.systemui.controls.util.SystemUIAnalyticsWrapper.EventId
            public final String getEventId() {
                return SystemUIAnalytics.EID_CONTROL_LAUNCH_SMART_THINGS;
            }

            public final int hashCode() {
                return 179339511;
            }

            public final String toString() {
                return "LaunchSmartThings";
            }
        }

        public final class LeftChooseDevices extends EventId {
            public static final LeftChooseDevices INSTANCE = new LeftChooseDevices();

            private LeftChooseDevices() {
                super(null);
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof LeftChooseDevices);
            }

            @Override // com.android.systemui.controls.util.SystemUIAnalyticsWrapper.EventId
            public final String getEventId() {
                return SystemUIAnalytics.EID_CONTROL_LEFT_CHOOSE_DEVICES;
            }

            public final int hashCode() {
                return 1384252475;
            }

            public final String toString() {
                return "LeftChooseDevices";
            }
        }

        public final class MoreDevicesToShow extends EventId {
            public static final MoreDevicesToShow INSTANCE = new MoreDevicesToShow();

            private MoreDevicesToShow() {
                super(null);
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof MoreDevicesToShow);
            }

            @Override // com.android.systemui.controls.util.SystemUIAnalyticsWrapper.EventId
            public final String getEventId() {
                return SystemUIAnalytics.EID_CONTROL_MORE_DEVICES_TO_SHOW;
            }

            public final int hashCode() {
                return -240252868;
            }

            public final String toString() {
                return "MoreDevicesToShow";
            }
        }

        public final class MoreManageApps extends EventId {
            public static final MoreManageApps INSTANCE = new MoreManageApps();

            private MoreManageApps() {
                super(null);
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof MoreManageApps);
            }

            @Override // com.android.systemui.controls.util.SystemUIAnalyticsWrapper.EventId
            public final String getEventId() {
                return SystemUIAnalytics.EID_CONTROL_MORE_MANAGE_APPS;
            }

            public final int hashCode() {
                return -851472;
            }

            public final String toString() {
                return "MoreManageApps";
            }
        }

        public final class MoreSettings extends EventId {
            public static final MoreSettings INSTANCE = new MoreSettings();

            private MoreSettings() {
                super(null);
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof MoreSettings);
            }

            @Override // com.android.systemui.controls.util.SystemUIAnalyticsWrapper.EventId
            public final String getEventId() {
                return SystemUIAnalytics.EID_CONTROL_MORE_SETTINGS;
            }

            public final int hashCode() {
                return -1405950244;
            }

            public final String toString() {
                return "MoreSettings";
            }
        }

        public final class MoveCard extends EventId {
            public static final MoveCard INSTANCE = new MoveCard();

            private MoveCard() {
                super(null);
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof MoveCard);
            }

            @Override // com.android.systemui.controls.util.SystemUIAnalyticsWrapper.EventId
            public final String getEventId() {
                return SystemUIAnalytics.EID_CONTROL_MOVE_CARD;
            }

            public final int hashCode() {
                return 1281248453;
            }

            public final String toString() {
                return "MoveCard";
            }
        }

        public final class OpenSpinner extends EventId {
            public static final OpenSpinner INSTANCE = new OpenSpinner();

            private OpenSpinner() {
                super(null);
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof OpenSpinner);
            }

            @Override // com.android.systemui.controls.util.SystemUIAnalyticsWrapper.EventId
            public final String getEventId() {
                return SystemUIAnalytics.EID_CONTROL_SPINNER_OPEN;
            }

            public final int hashCode() {
                return 1706922923;
            }

            public final String toString() {
                return "OpenSpinner";
            }
        }

        public final class QuitDevices extends EventId {
            public static final QuitDevices INSTANCE = new QuitDevices();

            private QuitDevices() {
                super(null);
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof QuitDevices);
            }

            @Override // com.android.systemui.controls.util.SystemUIAnalyticsWrapper.EventId
            public final String getEventId() {
                return SystemUIAnalytics.EID_CONTROL_QUIT_DEVICES;
            }

            public final int hashCode() {
                return -373230902;
            }

            public final String toString() {
                return "QuitDevices";
            }
        }

        public final class Reorder extends EventId {
            public static final Reorder INSTANCE = new Reorder();

            private Reorder() {
                super(null);
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof Reorder);
            }

            @Override // com.android.systemui.controls.util.SystemUIAnalyticsWrapper.EventId
            public final String getEventId() {
                return SystemUIAnalytics.EID_CONTROL_REORDER;
            }

            public final int hashCode() {
                return -1216833993;
            }

            public final String toString() {
                return "Reorder";
            }
        }

        public final class SettingsControlDevicesOnOff extends EventId {
            public static final SettingsControlDevicesOnOff INSTANCE = new SettingsControlDevicesOnOff();

            private SettingsControlDevicesOnOff() {
                super(null);
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof SettingsControlDevicesOnOff);
            }

            @Override // com.android.systemui.controls.util.SystemUIAnalyticsWrapper.EventId
            public final String getEventId() {
                return SystemUIAnalytics.EID_CONTROL_CONTROL_DEVICES_ON_OFF;
            }

            public final int hashCode() {
                return -1637942199;
            }

            public final String toString() {
                return "SettingsControlDevicesOnOff";
            }
        }

        public final class SettingsShowDevicesOnOff extends EventId {
            public static final SettingsShowDevicesOnOff INSTANCE = new SettingsShowDevicesOnOff();

            private SettingsShowDevicesOnOff() {
                super(null);
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof SettingsShowDevicesOnOff);
            }

            @Override // com.android.systemui.controls.util.SystemUIAnalyticsWrapper.EventId
            public final String getEventId() {
                return SystemUIAnalytics.EID_CONTROL_SHOW_DEVICES_ON_OFF;
            }

            public final int hashCode() {
                return -1763136425;
            }

            public final String toString() {
                return "SettingsShowDevicesOnOff";
            }
        }

        public final class TapAppList extends EventId {
            public static final TapAppList INSTANCE = new TapAppList();

            private TapAppList() {
                super(null);
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof TapAppList);
            }

            @Override // com.android.systemui.controls.util.SystemUIAnalyticsWrapper.EventId
            public final String getEventId() {
                return SystemUIAnalytics.EID_CONTROL_TAP_APP_LIST;
            }

            public final int hashCode() {
                return -1373166976;
            }

            public final String toString() {
                return "TapAppList";
            }
        }

        public final class TapAppListOnManageApps extends EventId {
            public static final TapAppListOnManageApps INSTANCE = new TapAppListOnManageApps();

            private TapAppListOnManageApps() {
                super(null);
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof TapAppListOnManageApps);
            }

            @Override // com.android.systemui.controls.util.SystemUIAnalyticsWrapper.EventId
            public final String getEventId() {
                return SystemUIAnalytics.EID_CONTROL_TAP_APP_LIST_ON_MANAGE_APPS;
            }

            public final int hashCode() {
                return -1181567146;
            }

            public final String toString() {
                return "TapAppListOnManageApps";
            }
        }

        public final class TapCardWithButton extends EventId {
            public static final TapCardWithButton INSTANCE = new TapCardWithButton();

            private TapCardWithButton() {
                super(null);
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof TapCardWithButton);
            }

            @Override // com.android.systemui.controls.util.SystemUIAnalyticsWrapper.EventId
            public final String getEventId() {
                return SystemUIAnalytics.EID_CONTROL_TAP_CARD_WITH_BTN;
            }

            public final int hashCode() {
                return 207628167;
            }

            public final String toString() {
                return "TapCardWithButton";
            }
        }

        public final class TapCardWithoutButton extends EventId {
            public static final TapCardWithoutButton INSTANCE = new TapCardWithoutButton();

            private TapCardWithoutButton() {
                super(null);
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof TapCardWithoutButton);
            }

            @Override // com.android.systemui.controls.util.SystemUIAnalyticsWrapper.EventId
            public final String getEventId() {
                return SystemUIAnalytics.EID_CONTROL_TAP_CARD_WITHOUT_BTN;
            }

            public final int hashCode() {
                return -458878005;
            }

            public final String toString() {
                return "TapCardWithoutButton";
            }
        }

        public final class TapMainActionButton extends EventId {
            public static final TapMainActionButton INSTANCE = new TapMainActionButton();

            private TapMainActionButton() {
                super(null);
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof TapMainActionButton);
            }

            @Override // com.android.systemui.controls.util.SystemUIAnalyticsWrapper.EventId
            public final String getEventId() {
                return SystemUIAnalytics.EID_CONTROL_TAP_MAIN_ACTION_BTN;
            }

            public final int hashCode() {
                return -1918585248;
            }

            public final String toString() {
                return "TapMainActionButton";
            }
        }

        public final class TapSmallTypeCard extends EventId {
            public static final TapSmallTypeCard INSTANCE = new TapSmallTypeCard();

            private TapSmallTypeCard() {
                super(null);
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof TapSmallTypeCard);
            }

            @Override // com.android.systemui.controls.util.SystemUIAnalyticsWrapper.EventId
            public final String getEventId() {
                return SystemUIAnalytics.EID_CONTROL_TAP_SMALL_TYPE_CARD;
            }

            public final int hashCode() {
                return -1348308238;
            }

            public final String toString() {
                return "TapSmallTypeCard";
            }
        }

        public final class TapSpinnerApp extends EventId {
            public static final TapSpinnerApp INSTANCE = new TapSpinnerApp();

            private TapSpinnerApp() {
                super(null);
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof TapSpinnerApp);
            }

            @Override // com.android.systemui.controls.util.SystemUIAnalyticsWrapper.EventId
            public final String getEventId() {
                return SystemUIAnalytics.EID_CONTROL_TAP_SPINNER_APP;
            }

            public final int hashCode() {
                return -1065324153;
            }

            public final String toString() {
                return "TapSpinnerApp";
            }
        }

        private EventId() {
        }

        public abstract String getEventId();

        public /* synthetic */ EventId(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public abstract class KeyId {

        public final class AllControls extends KeyId {
            public static final AllControls INSTANCE = new AllControls();

            private AllControls() {
                super(null);
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof AllControls);
            }

            @Override // com.android.systemui.controls.util.SystemUIAnalyticsWrapper.KeyId
            public final String getKeyId() {
                return SystemUIAnalytics.CONTROL_KEY_ALL_CONTROLS;
            }

            public final int hashCode() {
                return 1489302264;
            }

            public final String toString() {
                return SystemUIAnalytics.CONTROL_KEY_ALL_CONTROLS;
            }
        }

        public final class AppName extends KeyId {
            public static final AppName INSTANCE = new AppName();

            private AppName() {
                super(null);
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof AppName);
            }

            @Override // com.android.systemui.controls.util.SystemUIAnalyticsWrapper.KeyId
            public final String getKeyId() {
                return SystemUIAnalytics.CONTROL_KEY_APP_NAME;
            }

            public final int hashCode() {
                return -1484564403;
            }

            public final String toString() {
                return SystemUIAnalytics.CONTROL_KEY_APP_NAME;
            }
        }

        public final class DeviceName extends KeyId {
            public static final DeviceName INSTANCE = new DeviceName();

            private DeviceName() {
                super(null);
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof DeviceName);
            }

            @Override // com.android.systemui.controls.util.SystemUIAnalyticsWrapper.KeyId
            public final String getKeyId() {
                return SystemUIAnalytics.CONTROL_KEY_DEVICE_NAME;
            }

            public final int hashCode() {
                return 841711200;
            }

            public final String toString() {
                return "DeviceName";
            }
        }

        public final class DeviceType extends KeyId {
            public static final DeviceType INSTANCE = new DeviceType();

            private DeviceType() {
                super(null);
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof DeviceType);
            }

            @Override // com.android.systemui.controls.util.SystemUIAnalyticsWrapper.KeyId
            public final String getKeyId() {
                return SystemUIAnalytics.CONTROL_KEY_DEVICE_TYPE;
            }

            public final int hashCode() {
                return 841913103;
            }

            public final String toString() {
                return "DeviceType";
            }
        }

        public final class NumberOfSelectedApps extends KeyId {
            public static final NumberOfSelectedApps INSTANCE = new NumberOfSelectedApps();

            private NumberOfSelectedApps() {
                super(null);
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof NumberOfSelectedApps);
            }

            @Override // com.android.systemui.controls.util.SystemUIAnalyticsWrapper.KeyId
            public final String getKeyId() {
                return SystemUIAnalytics.CONTROL_KEY_NUM_OF_SELECTED_APPS;
            }

            public final int hashCode() {
                return 1633835116;
            }

            public final String toString() {
                return "NumberOfSelectedApps";
            }
        }

        public final class NumberOfTotalApps extends KeyId {
            public static final NumberOfTotalApps INSTANCE = new NumberOfTotalApps();

            private NumberOfTotalApps() {
                super(null);
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof NumberOfTotalApps);
            }

            @Override // com.android.systemui.controls.util.SystemUIAnalyticsWrapper.KeyId
            public final String getKeyId() {
                return SystemUIAnalytics.CONTROL_KEY_NUM_OF_TOTAL_APPS;
            }

            public final int hashCode() {
                return -1654965385;
            }

            public final String toString() {
                return "NumberOfTotalApps";
            }
        }

        public final class SelectedControl extends KeyId {
            public static final SelectedControl INSTANCE = new SelectedControl();

            private SelectedControl() {
                super(null);
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof SelectedControl);
            }

            @Override // com.android.systemui.controls.util.SystemUIAnalyticsWrapper.KeyId
            public final String getKeyId() {
                return SystemUIAnalytics.CONTROL_KEY_SELECTED_CONTROL;
            }

            public final int hashCode() {
                return 784420163;
            }

            public final String toString() {
                return SystemUIAnalytics.CONTROL_KEY_SELECTED_CONTROL;
            }
        }

        public final class Structure extends KeyId {
            public static final Structure INSTANCE = new Structure();

            private Structure() {
                super(null);
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof Structure);
            }

            @Override // com.android.systemui.controls.util.SystemUIAnalyticsWrapper.KeyId
            public final String getKeyId() {
                return SystemUIAnalytics.CONTROL_KEY_STRUCTURE;
            }

            public final int hashCode() {
                return -1931732684;
            }

            public final String toString() {
                return SystemUIAnalytics.CONTROL_KEY_STRUCTURE;
            }
        }

        public final class Template extends KeyId {
            public static final Template INSTANCE = new Template();

            private Template() {
                super(null);
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof Template);
            }

            @Override // com.android.systemui.controls.util.SystemUIAnalyticsWrapper.KeyId
            public final String getKeyId() {
                return SystemUIAnalytics.CONTROL_KEY_TEMPLATE;
            }

            public final int hashCode() {
                return -1249975143;
            }

            public final String toString() {
                return "Template";
            }
        }

        public final class Zone extends KeyId {
            public static final Zone INSTANCE = new Zone();

            private Zone() {
                super(null);
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof Zone);
            }

            @Override // com.android.systemui.controls.util.SystemUIAnalyticsWrapper.KeyId
            public final String getKeyId() {
                return SystemUIAnalytics.CONTROL_KEY_ZONE;
            }

            public final int hashCode() {
                return 257172299;
            }

            public final String toString() {
                return SystemUIAnalytics.CONTROL_KEY_ZONE;
            }
        }

        private KeyId() {
        }

        public abstract String getKeyId();

        public /* synthetic */ KeyId(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public abstract class ScreenId {

        public final class ChooseDevices extends ScreenId {
            public static final ChooseDevices INSTANCE = new ChooseDevices();

            private ChooseDevices() {
                super(null);
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof ChooseDevices);
            }

            @Override // com.android.systemui.controls.util.SystemUIAnalyticsWrapper.ScreenId
            public final String getScreenId() {
                return SystemUIAnalytics.SID_CONTROL_CHOOSE_DEVICES;
            }

            public final int hashCode() {
                return -1529762550;
            }

            public final String toString() {
                return "ChooseDevices";
            }
        }

        public final class CustomPanel extends ScreenId {
            public static final CustomPanel INSTANCE = new CustomPanel();

            private CustomPanel() {
                super(null);
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof CustomPanel);
            }

            @Override // com.android.systemui.controls.util.SystemUIAnalyticsWrapper.ScreenId
            public final String getScreenId() {
                return SystemUIAnalytics.SID_CONTROL_CUSTOM_PANEL;
            }

            public final int hashCode() {
                return 1427989591;
            }

            public final String toString() {
                return "CustomPanel";
            }
        }

        public final class Intro extends ScreenId {
            public static final Intro INSTANCE = new Intro();

            private Intro() {
                super(null);
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof Intro);
            }

            @Override // com.android.systemui.controls.util.SystemUIAnalyticsWrapper.ScreenId
            public final String getScreenId() {
                return SystemUIAnalytics.SID_CONTROL_INTRO;
            }

            public final int hashCode() {
                return -423919440;
            }

            public final String toString() {
                return "Intro";
            }
        }

        public final class IntroNoAppsToShow extends ScreenId {
            public static final IntroNoAppsToShow INSTANCE = new IntroNoAppsToShow();

            private IntroNoAppsToShow() {
                super(null);
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof IntroNoAppsToShow);
            }

            @Override // com.android.systemui.controls.util.SystemUIAnalyticsWrapper.ScreenId
            public final String getScreenId() {
                return SystemUIAnalytics.SID_CONTROL_INTRO_NO_APPS_TO_SHOW;
            }

            public final int hashCode() {
                return -1157956965;
            }

            public final String toString() {
                return "IntroNoAppsToShow";
            }
        }

        public final class MainScreen extends ScreenId {
            public static final MainScreen INSTANCE = new MainScreen();

            private MainScreen() {
                super(null);
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof MainScreen);
            }

            @Override // com.android.systemui.controls.util.SystemUIAnalyticsWrapper.ScreenId
            public final String getScreenId() {
                return SystemUIAnalytics.SID_CONTROL_MAIN_SCREEN;
            }

            public final int hashCode() {
                return -2079296575;
            }

            public final String toString() {
                return "MainScreen";
            }
        }

        public final class ManageApps extends ScreenId {
            public static final ManageApps INSTANCE = new ManageApps();

            private ManageApps() {
                super(null);
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof ManageApps);
            }

            @Override // com.android.systemui.controls.util.SystemUIAnalyticsWrapper.ScreenId
            public final String getScreenId() {
                return SystemUIAnalytics.SID_CONTROL_MANAGE_APPS;
            }

            public final int hashCode() {
                return -34844237;
            }

            public final String toString() {
                return "ManageApps";
            }
        }

        public final class NoDeviceSelected extends ScreenId {
            public static final NoDeviceSelected INSTANCE = new NoDeviceSelected();

            private NoDeviceSelected() {
                super(null);
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof NoDeviceSelected);
            }

            @Override // com.android.systemui.controls.util.SystemUIAnalyticsWrapper.ScreenId
            public final String getScreenId() {
                return SystemUIAnalytics.SID_CONTROL_NO_DEVICE_SELECTED;
            }

            public final int hashCode() {
                return 412976750;
            }

            public final String toString() {
                return "NoDeviceSelected";
            }
        }

        public final class Settings extends ScreenId {
            public static final Settings INSTANCE = new Settings();

            private Settings() {
                super(null);
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof Settings);
            }

            @Override // com.android.systemui.controls.util.SystemUIAnalyticsWrapper.ScreenId
            public final String getScreenId() {
                return SystemUIAnalytics.SID_CONTROL_SETTINGS;
            }

            public final int hashCode() {
                return -927773857;
            }

            public final String toString() {
                return "Settings";
            }
        }

        private ScreenId() {
        }

        public abstract String getScreenId();

        public /* synthetic */ ScreenId(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public abstract class StatusEventId {

        public final class DevicesAppsStatus extends StatusEventId {
            public static final DevicesAppsStatus INSTANCE = new DevicesAppsStatus();

            private DevicesAppsStatus() {
                super(null);
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof DevicesAppsStatus);
            }

            @Override // com.android.systemui.controls.util.SystemUIAnalyticsWrapper.StatusEventId
            public final String getStatusEventId() {
                return SystemUIAnalytics.STID_CONTROL_DEVICES_APPS_STATUS;
            }

            public final int hashCode() {
                return -1172863893;
            }

            public final String toString() {
                return "DevicesAppsStatus";
            }
        }

        public final class NumberOfAppsInDevices extends StatusEventId {
            public static final NumberOfAppsInDevices INSTANCE = new NumberOfAppsInDevices();

            private NumberOfAppsInDevices() {
                super(null);
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof NumberOfAppsInDevices);
            }

            @Override // com.android.systemui.controls.util.SystemUIAnalyticsWrapper.StatusEventId
            public final String getStatusEventId() {
                return SystemUIAnalytics.STID_CONTROL_NUMBER_OF_APPS_IN_DEVICES;
            }

            public final int hashCode() {
                return -286638032;
            }

            public final String toString() {
                return "NumberOfAppsInDevices";
            }
        }

        private StatusEventId() {
        }

        public abstract String getStatusEventId();

        public /* synthetic */ StatusEventId(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public static void sendEventLog(ScreenId screenId, EventId eventId) {
        SystemUIAnalytics.sendEventLog(screenId.getScreenId(), eventId.getEventId());
    }
}
