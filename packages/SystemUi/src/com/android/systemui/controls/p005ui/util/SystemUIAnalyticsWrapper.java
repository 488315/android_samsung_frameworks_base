package com.android.systemui.controls.p005ui.util;

import com.android.systemui.util.SystemUIAnalytics;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SystemUIAnalyticsWrapper {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class EventId {

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class AddDevices extends EventId {
            public static final AddDevices INSTANCE = new AddDevices();

            private AddDevices() {
                super(null);
            }

            @Override // com.android.systemui.controls.ui.util.SystemUIAnalyticsWrapper.EventId
            public final String getEventId() {
                return "Dvcs041";
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class ChooseAppsOnOff extends EventId {
            public static final ChooseAppsOnOff INSTANCE = new ChooseAppsOnOff();

            private ChooseAppsOnOff() {
                super(null);
            }

            @Override // com.android.systemui.controls.ui.util.SystemUIAnalyticsWrapper.EventId
            public final String getEventId() {
                return "Dvcs012";
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class ChooseAppsOnOffOnManageApps extends EventId {
            public static final ChooseAppsOnOffOnManageApps INSTANCE = new ChooseAppsOnOffOnManageApps();

            private ChooseAppsOnOffOnManageApps() {
                super(null);
            }

            @Override // com.android.systemui.controls.ui.util.SystemUIAnalyticsWrapper.EventId
            public final String getEventId() {
                return "Dvcs062";
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class IntroStart extends EventId {
            public static final IntroStart INSTANCE = new IntroStart();

            private IntroStart() {
                super(null);
            }

            @Override // com.android.systemui.controls.ui.util.SystemUIAnalyticsWrapper.EventId
            public final String getEventId() {
                return "Dvcs013";
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class LaunchDevices extends EventId {
            public static final LaunchDevices INSTANCE = new LaunchDevices();

            private LaunchDevices() {
                super(null);
            }

            @Override // com.android.systemui.controls.ui.util.SystemUIAnalyticsWrapper.EventId
            public final String getEventId() {
                return "Dvcs910";
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class LaunchFullController extends EventId {
            public static final LaunchFullController INSTANCE = new LaunchFullController();

            private LaunchFullController() {
                super(null);
            }

            @Override // com.android.systemui.controls.ui.util.SystemUIAnalyticsWrapper.EventId
            public final String getEventId() {
                return "Dvcs101";
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class LaunchSmartThings extends EventId {
            public static final LaunchSmartThings INSTANCE = new LaunchSmartThings();

            private LaunchSmartThings() {
                super(null);
            }

            @Override // com.android.systemui.controls.ui.util.SystemUIAnalyticsWrapper.EventId
            public final String getEventId() {
                return "Dvcs051";
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class LeftChooseDevices extends EventId {
            public static final LeftChooseDevices INSTANCE = new LeftChooseDevices();

            private LeftChooseDevices() {
                super(null);
            }

            @Override // com.android.systemui.controls.ui.util.SystemUIAnalyticsWrapper.EventId
            public final String getEventId() {
                return "Dvcs022";
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class MoreDevicesToShow extends EventId {
            public static final MoreDevicesToShow INSTANCE = new MoreDevicesToShow();

            private MoreDevicesToShow() {
                super(null);
            }

            @Override // com.android.systemui.controls.ui.util.SystemUIAnalyticsWrapper.EventId
            public final String getEventId() {
                return "Dvcs0511";
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class MoreManageApps extends EventId {
            public static final MoreManageApps INSTANCE = new MoreManageApps();

            private MoreManageApps() {
                super(null);
            }

            @Override // com.android.systemui.controls.ui.util.SystemUIAnalyticsWrapper.EventId
            public final String getEventId() {
                return "Dvcs058";
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class MoreSettings extends EventId {
            public static final MoreSettings INSTANCE = new MoreSettings();

            private MoreSettings() {
                super(null);
            }

            @Override // com.android.systemui.controls.ui.util.SystemUIAnalyticsWrapper.EventId
            public final String getEventId() {
                return "Dvcs059";
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class MoveCard extends EventId {
            public static final MoveCard INSTANCE = new MoveCard();

            private MoveCard() {
                super(null);
            }

            @Override // com.android.systemui.controls.ui.util.SystemUIAnalyticsWrapper.EventId
            public final String getEventId() {
                return "Dvcs0510";
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class OpenSpinner extends EventId {
            public static final OpenSpinner INSTANCE = new OpenSpinner();

            private OpenSpinner() {
                super(null);
            }

            @Override // com.android.systemui.controls.ui.util.SystemUIAnalyticsWrapper.EventId
            public final String getEventId() {
                return "Dvcs052";
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class QuitDevices extends EventId {
            public static final QuitDevices INSTANCE = new QuitDevices();

            private QuitDevices() {
                super(null);
            }

            @Override // com.android.systemui.controls.ui.util.SystemUIAnalyticsWrapper.EventId
            public final String getEventId() {
                return "Dvcs920";
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class Reorder extends EventId {
            public static final Reorder INSTANCE = new Reorder();

            private Reorder() {
                super(null);
            }

            @Override // com.android.systemui.controls.ui.util.SystemUIAnalyticsWrapper.EventId
            public final String getEventId() {
                return "Dvcs021";
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class SettingsControlDevicesOnOff extends EventId {
            public static final SettingsControlDevicesOnOff INSTANCE = new SettingsControlDevicesOnOff();

            private SettingsControlDevicesOnOff() {
                super(null);
            }

            @Override // com.android.systemui.controls.ui.util.SystemUIAnalyticsWrapper.EventId
            public final String getEventId() {
                return "Dvcs083";
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class SettingsShowDevicesOnOff extends EventId {
            public static final SettingsShowDevicesOnOff INSTANCE = new SettingsShowDevicesOnOff();

            private SettingsShowDevicesOnOff() {
                super(null);
            }

            @Override // com.android.systemui.controls.ui.util.SystemUIAnalyticsWrapper.EventId
            public final String getEventId() {
                return "Dvcs082";
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class TapAppList extends EventId {
            public static final TapAppList INSTANCE = new TapAppList();

            private TapAppList() {
                super(null);
            }

            @Override // com.android.systemui.controls.ui.util.SystemUIAnalyticsWrapper.EventId
            public final String getEventId() {
                return "Dvcs011";
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class TapAppListOnManageApps extends EventId {
            public static final TapAppListOnManageApps INSTANCE = new TapAppListOnManageApps();

            private TapAppListOnManageApps() {
                super(null);
            }

            @Override // com.android.systemui.controls.ui.util.SystemUIAnalyticsWrapper.EventId
            public final String getEventId() {
                return "Dvcs061";
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class TapCardWithButton extends EventId {
            public static final TapCardWithButton INSTANCE = new TapCardWithButton();

            private TapCardWithButton() {
                super(null);
            }

            @Override // com.android.systemui.controls.ui.util.SystemUIAnalyticsWrapper.EventId
            public final String getEventId() {
                return "Dvcs056";
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class TapCardWithoutButton extends EventId {
            public static final TapCardWithoutButton INSTANCE = new TapCardWithoutButton();

            private TapCardWithoutButton() {
                super(null);
            }

            @Override // com.android.systemui.controls.ui.util.SystemUIAnalyticsWrapper.EventId
            public final String getEventId() {
                return "Dvcs057";
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class TapMainActionButton extends EventId {
            public static final TapMainActionButton INSTANCE = new TapMainActionButton();

            private TapMainActionButton() {
                super(null);
            }

            @Override // com.android.systemui.controls.ui.util.SystemUIAnalyticsWrapper.EventId
            public final String getEventId() {
                return "Dvcs055";
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class TapSmallTypeCard extends EventId {
            public static final TapSmallTypeCard INSTANCE = new TapSmallTypeCard();

            private TapSmallTypeCard() {
                super(null);
            }

            @Override // com.android.systemui.controls.ui.util.SystemUIAnalyticsWrapper.EventId
            public final String getEventId() {
                return "Dvcs054";
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class TapSpinnerApp extends EventId {
            public static final TapSpinnerApp INSTANCE = new TapSpinnerApp();

            private TapSpinnerApp() {
                super(null);
            }

            @Override // com.android.systemui.controls.ui.util.SystemUIAnalyticsWrapper.EventId
            public final String getEventId() {
                return "Dvcs053";
            }
        }

        private EventId() {
        }

        public /* synthetic */ EventId(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public abstract String getEventId();
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class KeyId {

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class AllControls extends KeyId {
            public static final AllControls INSTANCE = new AllControls();

            private AllControls() {
                super(null);
            }

            @Override // com.android.systemui.controls.ui.util.SystemUIAnalyticsWrapper.KeyId
            public final String getKeyId() {
                return "AllControls";
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class AppName extends KeyId {
            public static final AppName INSTANCE = new AppName();

            private AppName() {
                super(null);
            }

            @Override // com.android.systemui.controls.ui.util.SystemUIAnalyticsWrapper.KeyId
            public final String getKeyId() {
                return "AppName";
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class DeviceName extends KeyId {
            public static final DeviceName INSTANCE = new DeviceName();

            private DeviceName() {
                super(null);
            }

            @Override // com.android.systemui.controls.ui.util.SystemUIAnalyticsWrapper.KeyId
            public final String getKeyId() {
                return "Device name";
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class DeviceType extends KeyId {
            public static final DeviceType INSTANCE = new DeviceType();

            private DeviceType() {
                super(null);
            }

            @Override // com.android.systemui.controls.ui.util.SystemUIAnalyticsWrapper.KeyId
            public final String getKeyId() {
                return "Device type";
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class NumberOfSelectedApps extends KeyId {
            public static final NumberOfSelectedApps INSTANCE = new NumberOfSelectedApps();

            private NumberOfSelectedApps() {
                super(null);
            }

            @Override // com.android.systemui.controls.ui.util.SystemUIAnalyticsWrapper.KeyId
            public final String getKeyId() {
                return "num of selected apps";
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class NumberOfTotalApps extends KeyId {
            public static final NumberOfTotalApps INSTANCE = new NumberOfTotalApps();

            private NumberOfTotalApps() {
                super(null);
            }

            @Override // com.android.systemui.controls.ui.util.SystemUIAnalyticsWrapper.KeyId
            public final String getKeyId() {
                return "num of total apps";
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class SelectedControl extends KeyId {
            public static final SelectedControl INSTANCE = new SelectedControl();

            private SelectedControl() {
                super(null);
            }

            @Override // com.android.systemui.controls.ui.util.SystemUIAnalyticsWrapper.KeyId
            public final String getKeyId() {
                return "SelectedControl";
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class Structure extends KeyId {
            public static final Structure INSTANCE = new Structure();

            private Structure() {
                super(null);
            }

            @Override // com.android.systemui.controls.ui.util.SystemUIAnalyticsWrapper.KeyId
            public final String getKeyId() {
                return "Structure";
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class Template extends KeyId {
            public static final Template INSTANCE = new Template();

            private Template() {
                super(null);
            }

            @Override // com.android.systemui.controls.ui.util.SystemUIAnalyticsWrapper.KeyId
            public final String getKeyId() {
                return "template";
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class Zone extends KeyId {
            public static final Zone INSTANCE = new Zone();

            private Zone() {
                super(null);
            }

            @Override // com.android.systemui.controls.ui.util.SystemUIAnalyticsWrapper.KeyId
            public final String getKeyId() {
                return "Zone";
            }
        }

        private KeyId() {
        }

        public /* synthetic */ KeyId(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public abstract String getKeyId();
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class ScreenId {

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class ChooseDevices extends ScreenId {
            public static final ChooseDevices INSTANCE = new ChooseDevices();

            private ChooseDevices() {
                super(null);
            }

            @Override // com.android.systemui.controls.ui.util.SystemUIAnalyticsWrapper.ScreenId
            public final String getScreenId() {
                return "Dvcs02";
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class CustomPanel extends ScreenId {
            public static final CustomPanel INSTANCE = new CustomPanel();

            private CustomPanel() {
                super(null);
            }

            @Override // com.android.systemui.controls.ui.util.SystemUIAnalyticsWrapper.ScreenId
            public final String getScreenId() {
                return "Dvcs10";
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class Intro extends ScreenId {
            public static final Intro INSTANCE = new Intro();

            private Intro() {
                super(null);
            }

            @Override // com.android.systemui.controls.ui.util.SystemUIAnalyticsWrapper.ScreenId
            public final String getScreenId() {
                return "Dvcs01";
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class IntroNoAppsToShow extends ScreenId {
            public static final IntroNoAppsToShow INSTANCE = new IntroNoAppsToShow();

            private IntroNoAppsToShow() {
                super(null);
            }

            @Override // com.android.systemui.controls.ui.util.SystemUIAnalyticsWrapper.ScreenId
            public final String getScreenId() {
                return "Dvcs03";
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class MainScreen extends ScreenId {
            public static final MainScreen INSTANCE = new MainScreen();

            private MainScreen() {
                super(null);
            }

            @Override // com.android.systemui.controls.ui.util.SystemUIAnalyticsWrapper.ScreenId
            public final String getScreenId() {
                return "Dvcs05";
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class ManageApps extends ScreenId {
            public static final ManageApps INSTANCE = new ManageApps();

            private ManageApps() {
                super(null);
            }

            @Override // com.android.systemui.controls.ui.util.SystemUIAnalyticsWrapper.ScreenId
            public final String getScreenId() {
                return "Dvcs06";
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class NoDeviceSelected extends ScreenId {
            public static final NoDeviceSelected INSTANCE = new NoDeviceSelected();

            private NoDeviceSelected() {
                super(null);
            }

            @Override // com.android.systemui.controls.ui.util.SystemUIAnalyticsWrapper.ScreenId
            public final String getScreenId() {
                return "Dvcs04";
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class Settings extends ScreenId {
            public static final Settings INSTANCE = new Settings();

            private Settings() {
                super(null);
            }

            @Override // com.android.systemui.controls.ui.util.SystemUIAnalyticsWrapper.ScreenId
            public final String getScreenId() {
                return "Dvcs08";
            }
        }

        private ScreenId() {
        }

        public /* synthetic */ ScreenId(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public abstract String getScreenId();
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class StatusEventId {

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class DevicesAppsStatus extends StatusEventId {
            public static final DevicesAppsStatus INSTANCE = new DevicesAppsStatus();

            private DevicesAppsStatus() {
                super(null);
            }

            @Override // com.android.systemui.controls.ui.util.SystemUIAnalyticsWrapper.StatusEventId
            public final String getStatusEventId() {
                return "Dvcs_Apps_Status";
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class NumberOfAppsInDevices extends StatusEventId {
            public static final NumberOfAppsInDevices INSTANCE = new NumberOfAppsInDevices();

            private NumberOfAppsInDevices() {
                super(null);
            }

            @Override // com.android.systemui.controls.ui.util.SystemUIAnalyticsWrapper.StatusEventId
            public final String getStatusEventId() {
                return "Dvcs_NumApps";
            }
        }

        private StatusEventId() {
        }

        public /* synthetic */ StatusEventId(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public abstract String getStatusEventId();
    }

    public static void sendEventLog(ScreenId screenId, EventId eventId) {
        SystemUIAnalytics.sendEventLog(screenId.getScreenId(), eventId.getEventId());
    }

    public static void sendEventLog(ScreenId screenId, EventId eventId, String str) {
        SystemUIAnalytics.sendEventLog(screenId.getScreenId(), eventId.getEventId(), str);
    }
}
