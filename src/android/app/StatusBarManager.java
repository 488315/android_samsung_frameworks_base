package android.app;

import android.annotation.SystemApi;
import android.app.StatusBarManager;
import android.app.compat.CompatChanges;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.drawable.Icon;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.INearbyMediaDevicesProvider;
import android.media.INearbyMediaDevicesUpdateCallback;
import android.media.MediaRoute2Info;
import android.media.NearbyDevice;
import android.media.NearbyMediaDevicesProvider;
import android.os.Binder;
import android.os.Bundle;
import android.os.Debug;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.util.Pair;
import android.util.Slog;
import android.view.KeyEvent;
import com.android.internal.compat.IPlatformCompat;
import com.android.internal.statusbar.AppClipsServiceConnector;
import com.android.internal.statusbar.IAddTileResultCallback;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.statusbar.IUndoMediaTransferCallback;
import com.android.internal.statusbar.NotificationVisibility;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public class StatusBarManager {
    public static final String ACTION_KEYGUARD_PRIVATE_NOTIFICATIONS_CHANGED = "android.app.action.KEYGUARD_PRIVATE_NOTIFICATIONS_CHANGED";
    public static final Set<Integer> ALL_SESSIONS = Set.of(1, 2);
    public static final int CAMERA_LAUNCH_SOURCE_LIFT_TRIGGER = 2;
    public static final int CAMERA_LAUNCH_SOURCE_POWER_DOUBLE_TAP = 1;
    public static final int CAMERA_LAUNCH_SOURCE_QUICK_AFFORDANCE = 3;
    public static final int CAMERA_LAUNCH_SOURCE_WIGGLE = 0;
    public static final int DEFAULT_SETUP_DISABLE2_FLAGS = 0;
    public static final int DEFAULT_SETUP_DISABLE_FLAGS = 61145088;
    private static final int DEFAULT_SIM_LOCKED_DISABLED_FLAGS = 65536;
    public static final int DISABLE2_GLOBAL_ACTIONS = 8;
    public static final int DISABLE2_MASK = 31;
    public static final int DISABLE2_NONE = 0;
    public static final int DISABLE2_NOTIFICATION_SHADE = 4;
    public static final int DISABLE2_QUICK_SETTINGS = 1;
    public static final int DISABLE2_ROTATE_SUGGESTIONS = 16;
    public static final int DISABLE2_SYSTEM_ICONS = 2;
    public static final int DISABLE_BACK = 4194304;
    public static final int DISABLE_CLOCK = 8388608;
    public static final int DISABLE_EXPAND = 65536;
    public static final int DISABLE_HOME = 2097152;
    public static final int DISABLE_MASK = 134152192;

    @Deprecated
    public static final int DISABLE_NAVIGATION = 18874368;
    public static final int DISABLE_NONE = 0;
    public static final int DISABLE_NOTIFICATION_ALERTS = 262144;
    public static final int DISABLE_NOTIFICATION_ICONS = 131072;

    @Deprecated
    public static final int DISABLE_NOTIFICATION_TICKER = 524288;
    public static final int DISABLE_ONGOING_CALL_CHIP = 67108864;
    public static final int DISABLE_RECENT = 16777216;
    public static final int DISABLE_SEARCH = 33554432;
    public static final int DISABLE_SYSTEM_INFO = 1048576;
    public static final String EXTRA_KM_PRIVATE_NOTIFS_ALLOWED = "android.app.extra.KM_PRIVATE_NOTIFS_ALLOWED";
    private static final long MEDIA_CONTROL_BLANK_TITLE = 274775190;
    private static final long MEDIA_CONTROL_MEDIA3_ACTIONS = 360196209;
    private static final long MEDIA_CONTROL_SESSION_ACTIONS = 203800354;

    @SystemApi
    public static final int MEDIA_TRANSFER_RECEIVER_STATE_CLOSE_TO_SENDER = 0;

    @SystemApi
    public static final int MEDIA_TRANSFER_RECEIVER_STATE_FAR_FROM_SENDER = 1;

    @SystemApi
    public static final int MEDIA_TRANSFER_RECEIVER_STATE_TRANSFER_TO_RECEIVER_FAILED = 3;

    @SystemApi
    public static final int MEDIA_TRANSFER_RECEIVER_STATE_TRANSFER_TO_RECEIVER_SUCCEEDED = 2;

    @SystemApi
    public static final int MEDIA_TRANSFER_SENDER_STATE_ALMOST_CLOSE_TO_END_CAST = 1;

    @SystemApi
    public static final int MEDIA_TRANSFER_SENDER_STATE_ALMOST_CLOSE_TO_START_CAST = 0;

    @SystemApi
    public static final int MEDIA_TRANSFER_SENDER_STATE_FAR_FROM_RECEIVER = 8;

    @SystemApi
    public static final int MEDIA_TRANSFER_SENDER_STATE_TRANSFER_TO_RECEIVER_FAILED = 6;

    @SystemApi
    public static final int MEDIA_TRANSFER_SENDER_STATE_TRANSFER_TO_RECEIVER_SUCCEEDED = 4;

    @SystemApi
    public static final int MEDIA_TRANSFER_SENDER_STATE_TRANSFER_TO_RECEIVER_TRIGGERED = 2;

    @SystemApi
    public static final int MEDIA_TRANSFER_SENDER_STATE_TRANSFER_TO_THIS_DEVICE_FAILED = 7;

    @SystemApi
    public static final int MEDIA_TRANSFER_SENDER_STATE_TRANSFER_TO_THIS_DEVICE_SUCCEEDED = 5;

    @SystemApi
    public static final int MEDIA_TRANSFER_SENDER_STATE_TRANSFER_TO_THIS_DEVICE_TRIGGERED = 3;
    public static final int NAVBAR_BACK_DISMISS_IME = 1;
    public static final int NAVBAR_IME_SWITCHER_BUTTON_VISIBLE = 4;
    public static final int NAVBAR_IME_VISIBLE = 2;

    @SystemApi
    public static final int NAV_BAR_MODE_DEFAULT = 0;

    @SystemApi
    public static final int NAV_BAR_MODE_KIDS = 1;
    public static final int SESSION_BIOMETRIC_PROMPT = 2;
    public static final int SESSION_KEYGUARD = 1;
    public static final int STATUS_BAR_CARLIFE = 2;
    public static final int STATUS_BAR_DEX = 1;
    public static final int STATUS_BAR_PHONE = 0;
    private static final String TAG = "StatusBarManager";
    public static final int TILE_ADD_REQUEST_ERROR_APP_NOT_IN_FOREGROUND = 1004;
    public static final int TILE_ADD_REQUEST_ERROR_BAD_COMPONENT = 1002;
    public static final int TILE_ADD_REQUEST_ERROR_MISMATCHED_PACKAGE = 1000;
    public static final int TILE_ADD_REQUEST_ERROR_NOT_CURRENT_USER = 1003;
    public static final int TILE_ADD_REQUEST_ERROR_NO_STATUS_BAR_SERVICE = 1005;
    public static final int TILE_ADD_REQUEST_ERROR_REQUEST_IN_PROGRESS = 1001;
    private static final int TILE_ADD_REQUEST_FIRST_ERROR_CODE = 1000;
    public static final int TILE_ADD_REQUEST_RESULT_DIALOG_DISMISSED = 3;
    public static final int TILE_ADD_REQUEST_RESULT_TILE_ADDED = 2;
    public static final int TILE_ADD_REQUEST_RESULT_TILE_ALREADY_ADDED = 1;
    public static final int TILE_ADD_REQUEST_RESULT_TILE_NOT_ADDED = 0;
    public static final int WINDOW_NAVIGATION_BAR = 2;
    public static final int WINDOW_STATE_HIDDEN = 2;
    public static final int WINDOW_STATE_HIDING = 1;
    public static final int WINDOW_STATE_SHOWING = 0;
    public static final int WINDOW_STATUS_BAR = 1;
    private Context mContext;
    private IStatusBarService mService;
    private final Map<NearbyMediaDevicesProvider, NearbyMediaDevicesProviderWrapper> nearbyMediaDevicesProviderMap = new HashMap();
    private IBinder mToken = new Binder();
    private final IPlatformCompat mPlatformCompat = IPlatformCompat.Stub.asInterface(ServiceManager.getService(Context.PLATFORM_COMPAT_SERVICE));

    @Target({ElementType.TYPE_USE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Disable2Flags {
    }

    @Target({ElementType.TYPE_USE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DisableFlags {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface MediaTransferReceiverState {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface MediaTransferSenderState {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface NavBarMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface NavbarFlags {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface RequestResult {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SessionFlags {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface WindowType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface WindowVisibleState {
    }

    StatusBarManager(Context context) {
        this.mContext = context;
    }

    private synchronized IStatusBarService getService() {
        if (this.mService == null) {
            IStatusBarService iStatusBarServiceAsInterface = IStatusBarService.Stub.asInterface(ServiceManager.getService(Context.STATUS_BAR_SERVICE));
            this.mService = iStatusBarServiceAsInterface;
            if (iStatusBarServiceAsInterface == null) {
                Slog.w(TAG, "warning: no STATUS_BAR_SERVICE");
            }
        }
        return this.mService;
    }

    private int getBarTypeFromContext() {
        return this.mContext.getResources().getConfiguration().isDexMode() ? 1 : 0;
    }

    public static int getNaturalBarTypeByDisplayId(Context context, int i) {
        return i == 0 ? context.getResources().getConfiguration().isDexMode() ? 1 : 0 : i == 2 ? 1 : 0;
    }

    private String getTag() {
        String callers = Debug.getCallers(2);
        if (callers == null) {
            return null;
        }
        String[] strArrSplit = callers.split("[.]");
        for (int i = 0; i < strArrSplit.length; i++) {
        }
        if (strArrSplit.length <= 0) {
            return null;
        }
        return NavigationBarInflaterView.GRAVITY_SEPARATOR + strArrSplit[strArrSplit.length - 1];
    }

    public void disable(int i) {
        try {
            int identifier = Binder.getCallingUserHandle().getIdentifier();
            IStatusBarService service = getService();
            if (service != null) {
                service.disableForUserToType(i, this.mToken, this.mContext.getPackageName() + getTag(), identifier, getBarTypeFromContext());
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void disable2(int i) {
        try {
            int identifier = Binder.getCallingUserHandle().getIdentifier();
            IStatusBarService service = getService();
            if (service != null) {
                service.disable2ForUserToType(i, this.mToken, this.mContext.getPackageName() + getTag(), identifier, getBarTypeFromContext());
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void clickNotification(String str, int i, int i2, boolean z) {
        clickNotificationInternal(str, i, i2, z);
    }

    private void clickNotificationInternal(String str, int i, int i2, boolean z) {
        try {
            IStatusBarService service = getService();
            if (service != null) {
                service.onNotificationClick(str, NotificationVisibility.obtain(str, i, i2, z));
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void sendNotificationFeedback(String str, Bundle bundle) {
        try {
            IStatusBarService service = getService();
            if (service != null) {
                service.onNotificationFeedbackReceived(str, bundle);
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void expandNotificationsPanel() {
        try {
            IStatusBarService service = getService();
            if (service != null) {
                service.expandNotificationsPanelToType(getBarTypeFromContext());
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void collapsePanels() {
        try {
            IStatusBarService service = getService();
            if (service != null) {
                service.collapsePanelsToType(getBarTypeFromContext());
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void togglePanel() {
        try {
            IStatusBarService service = getService();
            if (service != null) {
                service.togglePanel();
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void handleSystemKey(KeyEvent keyEvent) {
        try {
            IStatusBarService service = getService();
            if (service != null) {
                service.handleSystemKey(keyEvent);
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getLastSystemKey() {
        try {
            IStatusBarService service = getService();
            if (service != null) {
                return service.getLastSystemKey();
            }
            return -1;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void expandSettingsPanel() {
        expandSettingsPanel(null);
    }

    public void expandSettingsPanel(String str) {
        try {
            IStatusBarService service = getService();
            if (service != null) {
                service.expandSettingsPanelToType(str, getBarTypeFromContext());
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void disableToType(int i, int i2) {
        try {
            int identifier = Binder.getCallingUserHandle().getIdentifier();
            IStatusBarService service = getService();
            if (service != null) {
                service.disableForUserToType(i, this.mToken, this.mContext.getPackageName() + getTag(), identifier, i2);
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void disable2ToType(int i, int i2) {
        try {
            int identifier = Binder.getCallingUserHandle().getIdentifier();
            IStatusBarService service = getService();
            if (service != null) {
                service.disable2ForUserToType(i, this.mToken, this.mContext.getPackageName() + getTag(), identifier, i2);
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setIcon(String str, int i, int i2, String str2) {
        try {
            IStatusBarService service = getService();
            if (service != null) {
                service.setIcon(str, this.mContext.getPackageName(), i, i2, str2);
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void removeIcon(String str) {
        try {
            IStatusBarService service = getService();
            if (service != null) {
                service.removeIcon(str);
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setIconVisibility(String str, boolean z) {
        try {
            IStatusBarService service = getService();
            if (service != null) {
                service.setIconVisibility(str, z);
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setDisabledForSetup(boolean z) {
        try {
            int identifier = Binder.getCallingUserHandle().getIdentifier();
            IStatusBarService service = getService();
            if (service != null) {
                int i = z ? DEFAULT_SETUP_DISABLE_FLAGS : 0;
                service.disableForUser(i, this.mToken, this.mContext.getPackageName() + getTag(), identifier);
                service.disable2ForUser(0, this.mToken, this.mContext.getPackageName() + getTag(), identifier);
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi(client = SystemApi.Client.MODULE_LIBRARIES)
    public void setExpansionDisabledForSimNetworkLock(boolean z) {
        try {
            int identifier = Binder.getCallingUserHandle().getIdentifier();
            IStatusBarService service = getService();
            if (service != null) {
                int i = z ? 65536 : 0;
                service.disableForUser(i, this.mToken, this.mContext.getPackageName() + getTag(), identifier);
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public DisableInfo getDisableInfo() {
        try {
            int identifier = Binder.getCallingUserHandle().getIdentifier();
            IStatusBarService service = getService();
            int[] disableFlags = {0, 0};
            if (service != null) {
                disableFlags = service.getDisableFlags(this.mToken, identifier);
            }
            return new DisableInfo(disableFlags[0], disableFlags[1]);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void requestTileServiceListeningState(ComponentName componentName) {
        Objects.requireNonNull(componentName);
        try {
            getService().requestTileServiceListeningState(componentName, this.mContext.getUserId());
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void requestAddTileService(ComponentName componentName, CharSequence charSequence, Icon icon, Executor executor, final Consumer<Integer> consumer) {
        Objects.requireNonNull(componentName);
        Objects.requireNonNull(charSequence);
        Objects.requireNonNull(icon);
        Objects.requireNonNull(executor);
        Objects.requireNonNull(consumer);
        if (!componentName.getPackageName().equals(this.mContext.getPackageName())) {
            executor.execute(new Runnable() { // from class: android.app.StatusBarManager$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    consumer.accept(1000);
                }
            });
            return;
        }
        try {
            getService().requestAddTile(componentName, charSequence, icon, this.mContext.getUserId(), new RequestResultCallback(executor, consumer));
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void cancelRequestAddTile(String str) {
        Objects.requireNonNull(str);
        try {
            getService().cancelRequestAddTile(str);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setNavBarMode(int i) {
        if (i != 0 && i != 1) {
            throw new IllegalArgumentException("Supplied navBarMode not supported: " + i);
        }
        try {
            IStatusBarService service = getService();
            if (service != null) {
                service.setNavBarMode(i);
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public int getNavBarMode() {
        try {
            IStatusBarService service = getService();
            if (service != null) {
                return service.getNavBarMode();
            }
            return 0;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void updateMediaTapToTransferSenderDisplay(int i, MediaRoute2Info mediaRoute2Info, Executor executor, Runnable runnable) {
        UndoCallback undoCallback;
        Objects.requireNonNull(mediaRoute2Info);
        if (i != 4 && i != 5 && runnable != null) {
            throw new IllegalArgumentException("The undoCallback should only be provided when the state is a transfer succeeded state");
        }
        if (runnable != null && executor == null) {
            throw new IllegalArgumentException("You must pass an executor when you pass an undo callback");
        }
        IStatusBarService service = getService();
        if (executor != null) {
            try {
                undoCallback = new UndoCallback(executor, runnable);
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
                return;
            }
        } else {
            undoCallback = null;
        }
        service.updateMediaTapToTransferSenderDisplay(i, mediaRoute2Info, undoCallback);
    }

    @SystemApi
    public void updateMediaTapToTransferReceiverDisplay(int i, MediaRoute2Info mediaRoute2Info, Icon icon, CharSequence charSequence) {
        Objects.requireNonNull(mediaRoute2Info);
        try {
            getService().updateMediaTapToTransferReceiverDisplay(i, mediaRoute2Info, icon, charSequence);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void registerNearbyMediaDevicesProvider(NearbyMediaDevicesProvider nearbyMediaDevicesProvider) {
        Objects.requireNonNull(nearbyMediaDevicesProvider);
        if (this.nearbyMediaDevicesProviderMap.containsKey(nearbyMediaDevicesProvider)) {
            return;
        }
        try {
            IStatusBarService service = getService();
            NearbyMediaDevicesProviderWrapper nearbyMediaDevicesProviderWrapper = new NearbyMediaDevicesProviderWrapper(nearbyMediaDevicesProvider);
            this.nearbyMediaDevicesProviderMap.put(nearbyMediaDevicesProvider, nearbyMediaDevicesProviderWrapper);
            service.registerNearbyMediaDevicesProvider(nearbyMediaDevicesProviderWrapper);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void unregisterNearbyMediaDevicesProvider(NearbyMediaDevicesProvider nearbyMediaDevicesProvider) {
        Objects.requireNonNull(nearbyMediaDevicesProvider);
        if (this.nearbyMediaDevicesProviderMap.containsKey(nearbyMediaDevicesProvider)) {
            try {
                IStatusBarService service = getService();
                NearbyMediaDevicesProviderWrapper nearbyMediaDevicesProviderWrapper = this.nearbyMediaDevicesProviderMap.get(nearbyMediaDevicesProvider);
                this.nearbyMediaDevicesProviderMap.remove(nearbyMediaDevicesProvider);
                service.unregisterNearbyMediaDevicesProvider(nearbyMediaDevicesProviderWrapper);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public static boolean useMediaSessionActionsForApp(String str, UserHandle userHandle) {
        return CompatChanges.isChangeEnabled(MEDIA_CONTROL_SESSION_ACTIONS, str, userHandle);
    }

    public void logBlankMediaTitle(String str, int i) throws RuntimeException {
        try {
            this.mPlatformCompat.reportChangeByPackageName(MEDIA_CONTROL_BLANK_TITLE, str, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static boolean useMedia3ControllerForApp(String str, UserHandle userHandle) {
        return CompatChanges.isChangeEnabled(MEDIA_CONTROL_MEDIA3_ACTIONS, str, userHandle);
    }

    public boolean canLaunchCaptureContentActivityForNote(Activity activity) {
        Objects.requireNonNull(activity);
        return new AppClipsServiceConnector(this.mContext).canLaunchCaptureContentActivityForNote(ActivityClient.getInstance().getTaskForActivity(activity.getActivityToken(), false));
    }

    public static String navbarFlagsToString(int i) {
        ArrayList arrayList = new ArrayList();
        if ((i & 1) != 0) {
            arrayList.add("NAVBAR_BACK_DISMISS_IME");
        }
        if ((i & 2) != 0) {
            arrayList.add("NAVBAR_IME_VISIBLE");
        }
        if ((i & 4) != 0) {
            arrayList.add("NAVBAR_IME_SWITCHER_BUTTON_VISIBLE");
        }
        return String.join(" | ", arrayList);
    }

    public static String windowStateToString(int i) {
        if (i == 1) {
            return "WINDOW_STATE_HIDING";
        }
        if (i == 2) {
            return "WINDOW_STATE_HIDDEN";
        }
        if (i == 0) {
            return "WINDOW_STATE_SHOWING";
        }
        return "WINDOW_STATE_UNKNOWN";
    }

    @SystemApi
    public static final class DisableInfo {
        private boolean mClock;
        private boolean mNavigateHome;
        private boolean mNotificationIcons;
        private boolean mNotificationPeeking;
        private boolean mQuickSettings;
        private boolean mRecents;
        private boolean mRotationSuggestion;
        private boolean mSearch;
        private boolean mStatusBarExpansion;
        private boolean mSystemIcons;

        public DisableInfo(int i, int i2) {
            this.mStatusBarExpansion = (65536 & i) != 0;
            this.mNavigateHome = (2097152 & i) != 0;
            this.mNotificationPeeking = (262144 & i) != 0;
            this.mRecents = (16777216 & i) != 0;
            this.mSearch = (33554432 & i) != 0;
            this.mSystemIcons = (1048576 & i) != 0;
            this.mClock = (8388608 & i) != 0;
            this.mNotificationIcons = (i & 131072) != 0;
            this.mRotationSuggestion = (i2 & 16) != 0;
            this.mQuickSettings = (i2 & 1) != 0;
        }

        public DisableInfo() {
        }

        @SystemApi
        public boolean isStatusBarExpansionDisabled() {
            return this.mStatusBarExpansion;
        }

        public void setStatusBarExpansionDisabled(boolean z) {
            this.mStatusBarExpansion = z;
        }

        @SystemApi
        public boolean isNavigateToHomeDisabled() {
            return this.mNavigateHome;
        }

        public void setNagivationHomeDisabled(boolean z) {
            this.mNavigateHome = z;
        }

        @SystemApi
        public boolean isNotificationPeekingDisabled() {
            return this.mNotificationPeeking;
        }

        public void setNotificationPeekingDisabled(boolean z) {
            this.mNotificationPeeking = z;
        }

        @SystemApi
        public boolean isRecentsDisabled() {
            return this.mRecents;
        }

        public void setRecentsDisabled(boolean z) {
            this.mRecents = z;
        }

        @SystemApi
        public boolean isSearchDisabled() {
            return this.mSearch;
        }

        public void setSearchDisabled(boolean z) {
            this.mSearch = z;
        }

        public boolean areSystemIconsDisabled() {
            return this.mSystemIcons;
        }

        public void setSystemIconsDisabled(boolean z) {
            this.mSystemIcons = z;
        }

        public boolean isClockDisabled() {
            return this.mClock;
        }

        public void setClockDisabled(boolean z) {
            this.mClock = z;
        }

        public boolean areNotificationIconsDisabled() {
            return this.mNotificationIcons;
        }

        public void setNotificationIconsDisabled(boolean z) {
            this.mNotificationIcons = z;
        }

        public boolean isRotationSuggestionDisabled() {
            return this.mRotationSuggestion;
        }

        public void setQuickSettingsDisabled(boolean z) {
            this.mQuickSettings = z;
        }

        public boolean isQuickSettingsDisabled() {
            return this.mQuickSettings;
        }

        @SystemApi
        public boolean areAllComponentsEnabled() {
            return (this.mStatusBarExpansion || this.mNavigateHome || this.mNotificationPeeking || this.mRecents || this.mSearch || this.mSystemIcons || this.mClock || this.mNotificationIcons || this.mRotationSuggestion || this.mQuickSettings) ? false : true;
        }

        public void setEnableAll() {
            this.mStatusBarExpansion = false;
            this.mNavigateHome = false;
            this.mNotificationPeeking = false;
            this.mRecents = false;
            this.mSearch = false;
            this.mSystemIcons = false;
            this.mClock = false;
            this.mNotificationIcons = false;
            this.mRotationSuggestion = false;
            this.mQuickSettings = false;
        }

        public boolean areAllComponentsDisabled() {
            return this.mStatusBarExpansion && this.mNavigateHome && this.mNotificationPeeking && this.mRecents && this.mSearch && this.mSystemIcons && this.mClock && this.mNotificationIcons && this.mRotationSuggestion && this.mQuickSettings;
        }

        public void setDisableAll() {
            this.mStatusBarExpansion = true;
            this.mNavigateHome = true;
            this.mNotificationPeeking = true;
            this.mRecents = true;
            this.mSearch = true;
            this.mSystemIcons = true;
            this.mClock = true;
            this.mNotificationIcons = true;
            this.mRotationSuggestion = true;
            this.mQuickSettings = true;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("DisableInfo:  mStatusBarExpansion=");
            sb.append(this.mStatusBarExpansion ? "disabled" : "enabled");
            sb.append(" mNavigateHome=");
            sb.append(this.mNavigateHome ? "disabled" : "enabled");
            sb.append(" mNotificationPeeking=");
            sb.append(this.mNotificationPeeking ? "disabled" : "enabled");
            sb.append(" mRecents=");
            sb.append(this.mRecents ? "disabled" : "enabled");
            sb.append(" mSearch=");
            sb.append(this.mSearch ? "disabled" : "enabled");
            sb.append(" mSystemIcons=");
            sb.append(this.mSystemIcons ? "disabled" : "enabled");
            sb.append(" mClock=");
            sb.append(this.mClock ? "disabled" : "enabled");
            sb.append(" mNotificationIcons=");
            sb.append(this.mNotificationIcons ? "disabled" : "enabled");
            sb.append(" mRotationSuggestion=");
            sb.append(this.mRotationSuggestion ? "disabled" : "enabled");
            sb.append(" mQuickSettings=");
            sb.append(this.mQuickSettings ? "disabled" : "enabled");
            return sb.toString();
        }

        public Pair<Integer, Integer> toFlags() {
            int i = this.mStatusBarExpansion ? 65536 : 0;
            if (this.mNavigateHome) {
                i |= 2097152;
            }
            if (this.mNotificationPeeking) {
                i |= 262144;
            }
            if (this.mRecents) {
                i |= 16777216;
            }
            if (this.mSearch) {
                i |= 33554432;
            }
            if (this.mSystemIcons) {
                i |= 1048576;
            }
            if (this.mClock) {
                i |= 8388608;
            }
            if (this.mNotificationIcons) {
                i |= 131072;
            }
            int i2 = this.mRotationSuggestion ? 16 : 0;
            if (this.mQuickSettings) {
                i2 |= 1;
            }
            return new Pair<>(Integer.valueOf(i), Integer.valueOf(i2));
        }
    }

    static final class RequestResultCallback extends IAddTileResultCallback.Stub {
        private final Consumer<Integer> mCallback;
        private final Executor mExecutor;

        RequestResultCallback(Executor executor, Consumer<Integer> consumer) {
            this.mExecutor = executor;
            this.mCallback = consumer;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onTileRequest$0(int i) {
            this.mCallback.accept(Integer.valueOf(i));
        }

        @Override // com.android.internal.statusbar.IAddTileResultCallback
        public void onTileRequest(final int i) {
            this.mExecutor.execute(new Runnable() { // from class: android.app.StatusBarManager$RequestResultCallback$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onTileRequest$0(i);
                }
            });
        }
    }

    static final class UndoCallback extends IUndoMediaTransferCallback.Stub {
        private final Runnable mCallback;
        private final Executor mExecutor;

        UndoCallback(Executor executor, Runnable runnable) {
            this.mExecutor = executor;
            this.mCallback = runnable;
        }

        @Override // com.android.internal.statusbar.IUndoMediaTransferCallback
        public void onUndoTriggered() {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(this.mCallback);
            } finally {
                restoreCallingIdentity(jClearCallingIdentity);
            }
        }
    }

    static final class NearbyMediaDevicesProviderWrapper extends INearbyMediaDevicesProvider.Stub {
        private final NearbyMediaDevicesProvider mProvider;
        private final Map<INearbyMediaDevicesUpdateCallback, Consumer<List<NearbyDevice>>> mRegisteredCallbacks = new HashMap();

        NearbyMediaDevicesProviderWrapper(NearbyMediaDevicesProvider nearbyMediaDevicesProvider) {
            this.mProvider = nearbyMediaDevicesProvider;
        }

        @Override // android.media.INearbyMediaDevicesProvider
        public void registerNearbyDevicesCallback(final INearbyMediaDevicesUpdateCallback iNearbyMediaDevicesUpdateCallback) {
            Consumer<List<NearbyDevice>> consumer = new Consumer() { // from class: android.app.StatusBarManager$NearbyMediaDevicesProviderWrapper$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    StatusBarManager.NearbyMediaDevicesProviderWrapper.lambda$registerNearbyDevicesCallback$0(iNearbyMediaDevicesUpdateCallback, (List) obj);
                }
            };
            this.mRegisteredCallbacks.put(iNearbyMediaDevicesUpdateCallback, consumer);
            this.mProvider.registerNearbyDevicesCallback(consumer);
        }

        static /* synthetic */ void lambda$registerNearbyDevicesCallback$0(INearbyMediaDevicesUpdateCallback iNearbyMediaDevicesUpdateCallback, List list) {
            try {
                iNearbyMediaDevicesUpdateCallback.onDevicesUpdated(list);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }

        @Override // android.media.INearbyMediaDevicesProvider
        public void unregisterNearbyDevicesCallback(INearbyMediaDevicesUpdateCallback iNearbyMediaDevicesUpdateCallback) {
            if (this.mRegisteredCallbacks.containsKey(iNearbyMediaDevicesUpdateCallback)) {
                this.mProvider.unregisterNearbyDevicesCallback(this.mRegisteredCallbacks.get(iNearbyMediaDevicesUpdateCallback));
                this.mRegisteredCallbacks.remove(iNearbyMediaDevicesUpdateCallback);
            }
        }
    }
}
