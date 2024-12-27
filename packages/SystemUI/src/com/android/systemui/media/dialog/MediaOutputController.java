package com.android.systemui.media.dialog;

import android.app.KeyguardManager;
import android.app.Notification;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.Icon;
import android.media.AudioManager;
import android.media.INearbyMediaDevicesProvider;
import android.media.INearbyMediaDevicesUpdateCallback;
import android.media.MediaMetadata;
import android.media.MediaRoute2Info;
import android.media.NearbyDevice;
import android.media.RouteListingPreference;
import android.media.RoutingSessionInfo;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.MediaSessionManager;
import android.media.session.PlaybackState;
import android.os.IBinder;
import android.os.PowerExemptionManager;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import androidx.core.graphics.drawable.IconCompat;
import androidx.core.graphics.drawable.RoundedBitmapDrawable21;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.Utils;
import com.android.settingslib.bluetooth.LocalBluetoothLeBroadcast;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.media.InfoMediaDevice;
import com.android.settingslib.media.InfoMediaManager;
import com.android.settingslib.media.LocalMediaManager;
import com.android.settingslib.media.MediaDevice;
import com.android.systemui.R;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.animation.DialogTransitionAnimator$createActivityTransitionController$1;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.media.nearby.NearbyMediaDevicesManager;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.shared.system.SysUiStatsLog;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class MediaOutputController implements LocalMediaManager.DeviceCallback, INearbyMediaDevicesUpdateCallback {
    public static final boolean DEBUG = Log.isLoggable("MediaOutputController", 3);
    public final float mActiveRadius;
    public final ActivityStarter mActivityStarter;
    public final AudioManager mAudioManager;
    Callback mCallback;
    public int mColorButtonBackground;
    public int mColorConnectedItemBackground;
    public int mColorDialogBackground;
    public int mColorItemBackground;
    public int mColorItemContent;
    public int mColorPositiveButtonText;
    public int mColorSeekbarProgress;
    public final Context mContext;
    public int mCurrentState;
    public final DialogTransitionAnimator mDialogTransitionAnimator;
    public final FeatureFlags mFeatureFlags;
    public final float mInactiveRadius;
    public final int mItemMarginEndDefault;
    public final int mItemMarginEndSelectable;
    public final KeyguardManager mKeyGuardManager;
    public final LocalBluetoothManager mLocalBluetoothManager;
    LocalMediaManager mLocalMediaManager;
    public MediaController mMediaController;
    public final MediaSessionManager mMediaSessionManager;
    MediaOutputMetricLogger mMetricLogger;
    public final NearbyMediaDevicesManager mNearbyMediaDevicesManager;
    public final CommonNotifCollection mNotifCollection;
    public final String mPackageName;
    public final PowerExemptionManager mPowerExemptionManager;
    public final MediaSession.Token mToken;
    public final UserHandle mUserHandle;
    public final UserTracker mUserTracker;
    public final Object mMediaDevicesLock = new Object();
    final List<MediaDevice> mGroupMediaDevices = new CopyOnWriteArrayList();
    public final List mCachedMediaDevices = new CopyOnWriteArrayList();
    public final List mMediaItemList = new CopyOnWriteArrayList();
    public final Map mNearbyDeviceInfoMap = new ConcurrentHashMap();
    boolean mIsRefreshing = false;
    boolean mNeedRefresh = false;
    final MediaController.Callback mCb = new MediaController.Callback() { // from class: com.android.systemui.media.dialog.MediaOutputController.1
        @Override // android.media.session.MediaController.Callback
        public final void onMetadataChanged(MediaMetadata mediaMetadata) {
            MediaOutputBaseDialog mediaOutputBaseDialog = (MediaOutputBaseDialog) MediaOutputController.this.mCallback;
            mediaOutputBaseDialog.mMainThreadHandler.post(new MediaOutputBaseDialog$$ExternalSyntheticLambda2(mediaOutputBaseDialog, 0));
        }

        @Override // android.media.session.MediaController.Callback
        public final void onPlaybackStateChanged(PlaybackState playbackState) {
            int state = playbackState == null ? 1 : playbackState.getState();
            MediaOutputController mediaOutputController = MediaOutputController.this;
            if (mediaOutputController.mCurrentState == state) {
                return;
            }
            if (state == 1) {
                MediaOutputBaseDialog mediaOutputBaseDialog = (MediaOutputBaseDialog) mediaOutputController.mCallback;
                if (mediaOutputBaseDialog.isShowing()) {
                    mediaOutputBaseDialog.dismiss();
                }
            }
            MediaOutputController.this.mCurrentState = state;
        }
    };

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public enum BroadcastNotifyDialog {
        /* JADX INFO: Fake field, exist only in values array */
        ACTION_FIRST_LAUNCH,
        ACTION_BROADCAST_INFO_ICON
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Callback {
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Factory {
        MediaOutputController create(String str, UserHandle userHandle, MediaSession.Token token);
    }

    public MediaOutputController(Context context, String str, UserHandle userHandle, MediaSession.Token token, MediaSessionManager mediaSessionManager, LocalBluetoothManager localBluetoothManager, ActivityStarter activityStarter, CommonNotifCollection commonNotifCollection, DialogTransitionAnimator dialogTransitionAnimator, NearbyMediaDevicesManager nearbyMediaDevicesManager, AudioManager audioManager, PowerExemptionManager powerExemptionManager, KeyguardManager keyguardManager, FeatureFlags featureFlags, UserTracker userTracker) {
        this.mContext = context;
        this.mPackageName = str;
        this.mUserHandle = userHandle;
        this.mMediaSessionManager = mediaSessionManager;
        this.mLocalBluetoothManager = localBluetoothManager;
        this.mActivityStarter = activityStarter;
        this.mNotifCollection = commonNotifCollection;
        this.mAudioManager = audioManager;
        this.mPowerExemptionManager = powerExemptionManager;
        this.mKeyGuardManager = keyguardManager;
        this.mFeatureFlags = featureFlags;
        this.mUserTracker = userTracker;
        this.mToken = token;
        this.mLocalMediaManager = new LocalMediaManager(context, localBluetoothManager, InfoMediaManager.createInstance(context, str, userHandle, localBluetoothManager, token), str);
        this.mMetricLogger = new MediaOutputMetricLogger(context, str);
        this.mDialogTransitionAnimator = dialogTransitionAnimator;
        this.mNearbyMediaDevicesManager = nearbyMediaDevicesManager;
        this.mColorItemContent = Utils.getColorStateListDefaultColor(R.color.media_dialog_item_main_content, context);
        this.mColorSeekbarProgress = Utils.getColorStateListDefaultColor(R.color.media_dialog_seekbar_progress, context);
        this.mColorButtonBackground = Utils.getColorStateListDefaultColor(R.color.media_dialog_button_background, context);
        this.mColorItemBackground = Utils.getColorStateListDefaultColor(R.color.media_dialog_item_background, context);
        this.mColorConnectedItemBackground = Utils.getColorStateListDefaultColor(R.color.media_dialog_connected_item_background, context);
        this.mColorPositiveButtonText = Utils.getColorStateListDefaultColor(R.color.media_dialog_solid_button_text, context);
        this.mInactiveRadius = context.getResources().getDimension(R.dimen.media_output_dialog_background_radius);
        this.mActiveRadius = context.getResources().getDimension(R.dimen.media_output_dialog_active_background_radius);
        this.mColorDialogBackground = Utils.getColorStateListDefaultColor(R.color.media_dialog_background, context);
        this.mItemMarginEndDefault = (int) context.getResources().getDimension(R.dimen.media_output_dialog_default_margin_end);
        this.mItemMarginEndSelectable = (int) context.getResources().getDimension(R.dimen.media_output_dialog_selectable_margin_end);
    }

    public static boolean isActiveRemoteDevice(MediaDevice mediaDevice) {
        List<String> features;
        MediaRoute2Info mediaRoute2Info = mediaDevice.mRouteInfo;
        if (mediaRoute2Info == null) {
            Log.w("MediaDevice", "Unable to get features. RouteInfo is empty");
            features = new ArrayList<>();
        } else {
            features = mediaRoute2Info.getFeatures();
        }
        return features.contains("android.media.route.feature.REMOTE_PLAYBACK") || features.contains("android.media.route.feature.REMOTE_AUDIO_PLAYBACK") || features.contains("android.media.route.feature.REMOTE_VIDEO_PLAYBACK") || features.contains("android.media.route.feature.REMOTE_GROUP_PLAYBACK");
    }

    public final IBinder asBinder() {
        return null;
    }

    public final void attachRangeInfo(List list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            MediaDevice mediaDevice = (MediaDevice) it.next();
            if (((ConcurrentHashMap) this.mNearbyDeviceInfoMap).containsKey(mediaDevice.getId())) {
                mediaDevice.mRangeZone = ((Integer) ((ConcurrentHashMap) this.mNearbyDeviceInfoMap).get(mediaDevice.getId())).intValue();
            }
        }
    }

    public final void buildMediaItems(List list) {
        synchronized (this.mMediaDevicesLock) {
            List buildMediaItems = buildMediaItems(this.mMediaItemList, list);
            ((CopyOnWriteArrayList) this.mMediaItemList).clear();
            ((CopyOnWriteArrayList) this.mMediaItemList).addAll(buildMediaItems);
        }
    }

    public final List categorizeMediaItemsLocked(MediaDevice mediaDevice, List list, boolean z) {
        ArrayList arrayList = new ArrayList();
        Set set = (Set) getSelectedMediaDevice().stream().map(new MediaOutputController$$ExternalSyntheticLambda1(1)).collect(Collectors.toSet());
        if (mediaDevice != null) {
            set.add(mediaDevice.getId());
        }
        Iterator it = list.iterator();
        boolean z2 = false;
        boolean z3 = false;
        while (it.hasNext()) {
            MediaDevice mediaDevice2 = (MediaDevice) it.next();
            if (z && mediaDevice2.isMutingExpectedDevice()) {
                arrayList.add(0, MediaItem.createDeviceMediaItem(mediaDevice2));
            } else if (z || !set.contains(mediaDevice2.getId())) {
                if (mediaDevice2.isSuggestedDevice() && !z2) {
                    arrayList.add(MediaItem.createGroupDividerMediaItem(this.mContext.getString(R.string.media_output_group_title_suggested_device)));
                    z2 = true;
                } else if (!mediaDevice2.isSuggestedDevice() && !z3) {
                    arrayList.add(MediaItem.createGroupDividerMediaItem(this.mContext.getString(R.string.media_output_group_title_speakers_and_displays)));
                    z3 = true;
                }
                arrayList.add(MediaItem.createDeviceMediaItem(mediaDevice2));
            } else {
                arrayList.add(0, MediaItem.createDeviceMediaItem(mediaDevice2));
            }
        }
        if (!isCurrentConnectedDeviceRemote() && ((ArrayList) getSelectedMediaDevice()).size() == 1) {
            arrayList.add(MediaItem.createPairNewDeviceMediaItem());
        }
        return arrayList;
    }

    public final String getAppSourceName() {
        ApplicationInfo applicationInfo = null;
        if (TextUtils.isEmpty(this.mPackageName)) {
            return null;
        }
        PackageManager packageManager = this.mContext.getPackageManager();
        try {
            applicationInfo = packageManager.getApplicationInfo(this.mPackageName, PackageManager.ApplicationInfoFlags.of(0L));
        } catch (PackageManager.NameNotFoundException unused) {
        }
        return (String) (applicationInfo != null ? packageManager.getApplicationLabel(applicationInfo) : this.mContext.getString(R.string.media_output_dialog_unknown_launch_app_name));
    }

    public final List getDeselectableMediaDevice() {
        InfoMediaManager infoMediaManager = this.mLocalMediaManager.mInfoMediaManager;
        RoutingSessionInfo activeRoutingSession = infoMediaManager.getActiveRoutingSession();
        ArrayList arrayList = new ArrayList();
        for (MediaRoute2Info mediaRoute2Info : infoMediaManager.getDeselectableRoutes(activeRoutingSession)) {
            arrayList.add(new InfoMediaDevice(infoMediaManager.mContext, mediaRoute2Info, (RouteListingPreference.Item) ((ConcurrentHashMap) infoMediaManager.mPreferenceItemMap).get(mediaRoute2Info.getId())));
            StringBuilder sb = new StringBuilder();
            sb.append((Object) mediaRoute2Info.getName());
            sb.append(" is deselectable for ");
            ExifInterface$$ExternalSyntheticOutline0.m(sb, infoMediaManager.mPackageName, "InfoMediaManager");
        }
        return arrayList;
    }

    public final IconCompat getHeaderIcon() {
        Bitmap iconBitmap;
        MediaController mediaController = this.mMediaController;
        if (mediaController == null) {
            return null;
        }
        MediaMetadata metadata = mediaController.getMetadata();
        if (metadata != null && (iconBitmap = metadata.getDescription().getIconBitmap()) != null) {
            Context context = this.mContext;
            float dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.media_output_dialog_icon_corner_radius);
            Bitmap createBitmap = Bitmap.createBitmap(iconBitmap.getWidth(), iconBitmap.getHeight(), Bitmap.Config.ARGB_8888);
            RoundedBitmapDrawable21 roundedBitmapDrawable21 = new RoundedBitmapDrawable21(context.getResources(), iconBitmap);
            roundedBitmapDrawable21.mPaint.setAntiAlias(true);
            roundedBitmapDrawable21.invalidateSelf();
            roundedBitmapDrawable21.setCornerRadius(dimensionPixelSize);
            Canvas canvas = new Canvas(createBitmap);
            roundedBitmapDrawable21.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            roundedBitmapDrawable21.draw(canvas);
            return IconCompat.createWithBitmap(createBitmap);
        }
        if (DEBUG) {
            Log.d("MediaOutputController", "Media meta data does not contain icon information");
        }
        if (TextUtils.isEmpty(this.mPackageName)) {
            return null;
        }
        for (NotificationEntry notificationEntry : ((NotifPipeline) this.mNotifCollection).getAllNotifs()) {
            Notification notification2 = notificationEntry.mSbn.getNotification();
            if (notification2.isMediaNotification() && TextUtils.equals(notificationEntry.mSbn.getPackageName(), this.mPackageName)) {
                Icon largeIcon = notification2.getLargeIcon();
                if (largeIcon == null) {
                    return null;
                }
                PorterDuff.Mode mode = IconCompat.DEFAULT_TINT_MODE;
                return IconCompat.Api23Impl.createFromIconInner(largeIcon);
            }
        }
        return null;
    }

    public final IconCompat getNotificationSmallIcon() {
        if (TextUtils.isEmpty(this.mPackageName)) {
            return null;
        }
        Iterator it = ((NotifPipeline) this.mNotifCollection).getAllNotifs().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            NotificationEntry notificationEntry = (NotificationEntry) it.next();
            Notification notification2 = notificationEntry.mSbn.getNotification();
            if (notification2.isMediaNotification() && TextUtils.equals(notificationEntry.mSbn.getPackageName(), this.mPackageName)) {
                Icon smallIcon = notification2.getSmallIcon();
                if (smallIcon != null) {
                    PorterDuff.Mode mode = IconCompat.DEFAULT_TINT_MODE;
                    return IconCompat.Api23Impl.createFromIconInner(smallIcon);
                }
            }
        }
        return null;
    }

    public final List getSelectableMediaDevice() {
        InfoMediaManager infoMediaManager = this.mLocalMediaManager.mInfoMediaManager;
        RoutingSessionInfo activeRoutingSession = infoMediaManager.getActiveRoutingSession();
        ArrayList arrayList = new ArrayList();
        for (MediaRoute2Info mediaRoute2Info : infoMediaManager.getSelectableRoutes(activeRoutingSession)) {
            arrayList.add(new InfoMediaDevice(infoMediaManager.mContext, mediaRoute2Info, (RouteListingPreference.Item) ((ConcurrentHashMap) infoMediaManager.mPreferenceItemMap).get(mediaRoute2Info.getId())));
        }
        return arrayList;
    }

    public final List getSelectedMediaDevice() {
        InfoMediaManager infoMediaManager = this.mLocalMediaManager.mInfoMediaManager;
        RoutingSessionInfo activeRoutingSession = infoMediaManager.getActiveRoutingSession();
        ArrayList arrayList = new ArrayList();
        for (MediaRoute2Info mediaRoute2Info : infoMediaManager.getSelectedRoutes(activeRoutingSession)) {
            arrayList.add(new InfoMediaDevice(infoMediaManager.mContext, mediaRoute2Info, (RouteListingPreference.Item) ((ConcurrentHashMap) infoMediaManager.mPreferenceItemMap).get(mediaRoute2Info.getId())));
        }
        return arrayList;
    }

    public final boolean hasAdjustVolumeUserRestriction() {
        if (RestrictedLockUtilsInternal.checkIfRestrictionEnforced(this.mContext, "no_adjust_volume", UserHandle.myUserId()) != null) {
            return true;
        }
        return ((UserManager) this.mContext.getSystemService(UserManager.class)).hasBaseUserRestriction("no_adjust_volume", UserHandle.of(UserHandle.myUserId()));
    }

    public final boolean isAnyDeviceTransferring() {
        synchronized (this.mMediaDevicesLock) {
            try {
                Iterator it = ((CopyOnWriteArrayList) this.mMediaItemList).iterator();
                while (it.hasNext()) {
                    MediaItem mediaItem = (MediaItem) it.next();
                    if (mediaItem.mMediaDeviceOptional.isPresent() && ((MediaDevice) mediaItem.mMediaDeviceOptional.get()).mState == 1) {
                        return true;
                    }
                }
                return false;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean isCurrentConnectedDeviceRemote() {
        MediaDevice currentConnectedDevice = this.mLocalMediaManager.getCurrentConnectedDevice();
        return currentConnectedDevice != null && isActiveRemoteDevice(currentConnectedDevice);
    }

    @Override // com.android.settingslib.media.LocalMediaManager.DeviceCallback
    public final void onDeviceAttributesChanged() {
        MediaOutputBaseDialog mediaOutputBaseDialog = (MediaOutputBaseDialog) this.mCallback;
        mediaOutputBaseDialog.mMainThreadHandler.post(new MediaOutputBaseDialog$$ExternalSyntheticLambda2(mediaOutputBaseDialog, 1));
    }

    @Override // com.android.settingslib.media.LocalMediaManager.DeviceCallback
    public final void onDeviceListUpdate(List list) {
        if (((CopyOnWriteArrayList) this.mMediaItemList).isEmpty() || !this.mIsRefreshing) {
            buildMediaItems(list);
            MediaOutputBaseDialog mediaOutputBaseDialog = (MediaOutputBaseDialog) this.mCallback;
            mediaOutputBaseDialog.mMainThreadHandler.post(new MediaOutputBaseDialog$$ExternalSyntheticLambda2(mediaOutputBaseDialog, 2));
        } else {
            synchronized (this.mMediaDevicesLock) {
                this.mNeedRefresh = true;
                ((CopyOnWriteArrayList) this.mCachedMediaDevices).clear();
                ((CopyOnWriteArrayList) this.mCachedMediaDevices).addAll(list);
            }
        }
    }

    public final void onDevicesUpdated(List list) {
        ((ConcurrentHashMap) this.mNearbyDeviceInfoMap).clear();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            NearbyDevice nearbyDevice = (NearbyDevice) it.next();
            ((ConcurrentHashMap) this.mNearbyDeviceInfoMap).put(nearbyDevice.getMediaRoute2Id(), Integer.valueOf(nearbyDevice.getRangeZone()));
        }
        NearbyMediaDevicesManager nearbyMediaDevicesManager = this.mNearbyMediaDevicesManager;
        ((ArrayList) nearbyMediaDevicesManager.activeCallbacks).remove(this);
        Iterator it2 = ((ArrayList) nearbyMediaDevicesManager.providers).iterator();
        while (it2.hasNext()) {
            ((INearbyMediaDevicesProvider) it2.next()).unregisterNearbyDevicesCallback(this);
        }
    }

    @Override // com.android.settingslib.media.LocalMediaManager.DeviceCallback
    public final void onRequestFailed(int i) {
        int i2;
        int i3;
        MediaOutputBaseDialog mediaOutputBaseDialog = (MediaOutputBaseDialog) this.mCallback;
        mediaOutputBaseDialog.mMainThreadHandler.post(new MediaOutputBaseDialog$$ExternalSyntheticLambda2(mediaOutputBaseDialog, 1));
        MediaOutputMetricLogger mediaOutputMetricLogger = this.mMetricLogger;
        ArrayList arrayList = new ArrayList(this.mMediaItemList);
        if (MediaOutputMetricLogger.DEBUG) {
            mediaOutputMetricLogger.getClass();
            Log.e("MediaOutputMetricLogger", "logRequestFailed - " + i);
        }
        if (mediaOutputMetricLogger.mSourceDevice == null && mediaOutputMetricLogger.mTargetDevice == null) {
            return;
        }
        mediaOutputMetricLogger.updateLoggingMediaItemCount(arrayList);
        int loggingDeviceType = MediaOutputMetricLogger.getLoggingDeviceType(mediaOutputMetricLogger.mSourceDevice);
        int loggingDeviceType2 = MediaOutputMetricLogger.getLoggingDeviceType(mediaOutputMetricLogger.mTargetDevice);
        int i4 = 2;
        if (i != 1) {
            if (i != 2) {
                i4 = 4;
                i3 = i != 3 ? i != 4 ? 0 : 5 : 3;
            }
            i2 = i3;
            SysUiStatsLog.write(loggingDeviceType, loggingDeviceType2, 0, i2, mediaOutputMetricLogger.getLoggingPackageName(), mediaOutputMetricLogger.mWiredDeviceCount, mediaOutputMetricLogger.mConnectedBluetoothDeviceCount, mediaOutputMetricLogger.mRemoteDeviceCount, mediaOutputMetricLogger.mTargetDevice.isSuggestedDevice(), mediaOutputMetricLogger.mTargetDevice.hasOngoingSession());
        }
        i2 = i4;
        SysUiStatsLog.write(loggingDeviceType, loggingDeviceType2, 0, i2, mediaOutputMetricLogger.getLoggingPackageName(), mediaOutputMetricLogger.mWiredDeviceCount, mediaOutputMetricLogger.mConnectedBluetoothDeviceCount, mediaOutputMetricLogger.mRemoteDeviceCount, mediaOutputMetricLogger.mTargetDevice.isSuggestedDevice(), mediaOutputMetricLogger.mTargetDevice.hasOngoingSession());
    }

    @Override // com.android.settingslib.media.LocalMediaManager.DeviceCallback
    public final void onSelectedDeviceStateChanged(MediaDevice mediaDevice) {
        MediaOutputBaseDialog mediaOutputBaseDialog = (MediaOutputBaseDialog) this.mCallback;
        mediaOutputBaseDialog.mMainThreadHandler.post(new MediaOutputBaseDialog$$ExternalSyntheticLambda2(mediaOutputBaseDialog, 1));
        MediaOutputMetricLogger mediaOutputMetricLogger = this.mMetricLogger;
        String obj = mediaDevice.toString();
        ArrayList arrayList = new ArrayList(this.mMediaItemList);
        if (MediaOutputMetricLogger.DEBUG) {
            mediaOutputMetricLogger.getClass();
            Log.d("MediaOutputMetricLogger", "logOutputSuccess - selected device: " + obj);
        }
        if (mediaOutputMetricLogger.mSourceDevice == null && mediaOutputMetricLogger.mTargetDevice == null) {
            return;
        }
        mediaOutputMetricLogger.updateLoggingMediaItemCount(arrayList);
        SysUiStatsLog.write(MediaOutputMetricLogger.getLoggingDeviceType(mediaOutputMetricLogger.mSourceDevice), MediaOutputMetricLogger.getLoggingDeviceType(mediaOutputMetricLogger.mTargetDevice), 1, 1, mediaOutputMetricLogger.getLoggingPackageName(), mediaOutputMetricLogger.mWiredDeviceCount, mediaOutputMetricLogger.mConnectedBluetoothDeviceCount, mediaOutputMetricLogger.mRemoteDeviceCount, mediaOutputMetricLogger.mTargetDevice.isSuggestedDevice(), mediaOutputMetricLogger.mTargetDevice.hasOngoingSession());
    }

    public final void releaseSession() {
        MediaOutputMetricLogger mediaOutputMetricLogger = this.mMetricLogger;
        if (MediaOutputMetricLogger.DEBUG) {
            mediaOutputMetricLogger.getClass();
            Log.d("MediaOutputMetricLogger", "logInteraction - Stop casting");
        }
        SysUiStatsLog.write(mediaOutputMetricLogger.getLoggingPackageName(), 2, 0, false);
        InfoMediaManager infoMediaManager = this.mLocalMediaManager.mInfoMediaManager;
        infoMediaManager.releaseSession(infoMediaManager.getActiveRoutingSession());
    }

    public final void setBroadcastCode(String str) {
        LocalBluetoothLeBroadcast localBluetoothLeBroadcast = this.mLocalBluetoothManager.mProfileManager.mLeAudioBroadcast;
        if (localBluetoothLeBroadcast == null) {
            Log.d("MediaOutputController", "setBroadcastCode: LE Audio Broadcast is null");
        } else {
            localBluetoothLeBroadcast.setBroadcastCode(true, str.getBytes(StandardCharsets.UTF_8));
        }
    }

    public final void tryToLaunchInAppRoutingIntent(View view, String str) {
        RouteListingPreference routeListingPreference = this.mLocalMediaManager.mInfoMediaManager.getRouteListingPreference();
        ComponentName linkedItemComponentName = routeListingPreference == null ? null : routeListingPreference.getLinkedItemComponentName();
        if (linkedItemComponentName != null) {
            DialogTransitionAnimator dialogTransitionAnimator = this.mDialogTransitionAnimator;
            dialogTransitionAnimator.getClass();
            DialogTransitionAnimator$createActivityTransitionController$1 createActivityTransitionController$default = DialogTransitionAnimator.createActivityTransitionController$default(dialogTransitionAnimator, view);
            Intent intent = new Intent("android.media.action.TRANSFER_MEDIA");
            intent.setComponent(linkedItemComponentName);
            intent.putExtra("android.media.extra.ROUTE_ID", str);
            intent.addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
            ((MediaOutputBaseDialog) this.mCallback).mBroadcastSender.closeSystemDialogs();
            this.mActivityStarter.startActivity(intent, true, (ActivityTransitionAnimator.Controller) createActivityTransitionController$default);
        }
    }

    public final List buildMediaItems(List list, List list2) {
        synchronized (this.mMediaDevicesLock) {
            try {
                RouteListingPreference routeListingPreference = this.mLocalMediaManager.mInfoMediaManager.getRouteListingPreference();
                if (routeListingPreference == null || routeListingPreference.getUseSystemOrdering()) {
                    attachRangeInfo(list2);
                    Collections.sort(list2, Comparator.naturalOrder());
                }
                boolean z = (this.mAudioManager.getMutingExpectedDevice() != null) && !isCurrentConnectedDeviceRemote();
                MediaDevice currentConnectedDevice = z ? null : this.mLocalMediaManager.getCurrentConnectedDevice();
                if (list.isEmpty()) {
                    if (currentConnectedDevice == null) {
                        if (DEBUG) {
                            Log.d("MediaOutputController", "No connected media device or muting expected device exist.");
                        }
                        return categorizeMediaItemsLocked(null, list2, z);
                    }
                    return categorizeMediaItemsLocked(currentConnectedDevice, list2, false);
                }
                ArrayList arrayList = new ArrayList();
                HashMap hashMap = new HashMap();
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    MediaItem mediaItem = (MediaItem) it.next();
                    Iterator it2 = list2.iterator();
                    while (true) {
                        if (!it2.hasNext()) {
                            break;
                        }
                        MediaDevice mediaDevice = (MediaDevice) it2.next();
                        if (mediaItem.mMediaDeviceOptional.isPresent() && TextUtils.equals(((MediaDevice) mediaItem.mMediaDeviceOptional.get()).getId(), mediaDevice.getId())) {
                            arrayList.add(mediaDevice);
                            break;
                        }
                    }
                    if (mediaItem.mMediaItemType == 1) {
                        hashMap.put(Integer.valueOf(list.indexOf(mediaItem)), mediaItem);
                    }
                }
                if (arrayList.size() != list2.size()) {
                    list2.removeAll(arrayList);
                    arrayList.addAll(list2);
                }
                final List list3 = (List) arrayList.stream().map(new MediaOutputController$$ExternalSyntheticLambda1(0)).collect(Collectors.toList());
                Objects.requireNonNull(list3);
                hashMap.forEach(new BiConsumer() { // from class: com.android.systemui.media.dialog.MediaOutputController$$ExternalSyntheticLambda2
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        list3.add(((Integer) obj).intValue(), (MediaItem) obj2);
                    }
                });
                if (!isCurrentConnectedDeviceRemote() && ((ArrayList) getSelectedMediaDevice()).size() == 1) {
                    list3.add(MediaItem.createPairNewDeviceMediaItem());
                }
                return list3;
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
