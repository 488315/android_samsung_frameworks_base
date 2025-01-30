package com.android.systemui.media.dialog;

import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.app.Notification;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Icon;
import android.media.AudioManager;
import android.media.INearbyMediaDevicesUpdateCallback;
import android.media.MediaMetadata;
import android.media.MediaRoute2Info;
import android.media.NearbyDevice;
import android.media.RouteListingPreference;
import android.media.session.MediaController;
import android.media.session.MediaSessionManager;
import android.media.session.PlaybackState;
import android.os.IBinder;
import android.os.PowerExemptionManager;
import android.os.UserHandle;
import android.os.UserManager;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import androidx.core.graphics.drawable.IconCompat;
import androidx.core.graphics.drawable.RoundedBitmapDrawable21;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.Utils;
import com.android.settingslib.bluetooth.LocalBluetoothLeBroadcast;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.media.InfoMediaManager;
import com.android.settingslib.media.LocalMediaManager;
import com.android.settingslib.media.MediaDevice;
import com.android.systemui.R;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.animation.DialogLaunchAnimator$createActivityLaunchController$1;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.media.nearby.NearbyMediaDevicesManager;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.shared.system.SysUiStatsLog;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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
    public final DialogLaunchAnimator mDialogLaunchAnimator;
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
            mediaOutputBaseDialog.mMainThreadHandler.post(new MediaOutputBaseDialog$$ExternalSyntheticLambda1(mediaOutputBaseDialog, 0));
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.media.dialog.MediaOutputController$2 */
    public abstract /* synthetic */ class AbstractC18252 {

        /* renamed from: $SwitchMap$com$android$systemui$media$dialog$MediaOutputController$BroadcastNotifyDialog */
        public static final /* synthetic */ int[] f310x2ffb14dc;

        static {
            int[] iArr = new int[BroadcastNotifyDialog.values().length];
            f310x2ffb14dc = iArr;
            try {
                iArr[BroadcastNotifyDialog.ACTION_FIRST_LAUNCH.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f310x2ffb14dc[BroadcastNotifyDialog.ACTION_BROADCAST_INFO_ICON.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum BroadcastNotifyDialog {
        ACTION_FIRST_LAUNCH,
        ACTION_BROADCAST_INFO_ICON
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Callback {
    }

    public MediaOutputController(Context context, String str, MediaSessionManager mediaSessionManager, LocalBluetoothManager localBluetoothManager, ActivityStarter activityStarter, CommonNotifCollection commonNotifCollection, DialogLaunchAnimator dialogLaunchAnimator, Optional<NearbyMediaDevicesManager> optional, AudioManager audioManager, PowerExemptionManager powerExemptionManager, KeyguardManager keyguardManager, FeatureFlags featureFlags, UserTracker userTracker) {
        this.mContext = context;
        this.mPackageName = str;
        this.mMediaSessionManager = mediaSessionManager;
        this.mLocalBluetoothManager = localBluetoothManager;
        this.mActivityStarter = activityStarter;
        this.mNotifCollection = commonNotifCollection;
        this.mAudioManager = audioManager;
        this.mPowerExemptionManager = powerExemptionManager;
        this.mKeyGuardManager = keyguardManager;
        this.mFeatureFlags = featureFlags;
        this.mUserTracker = userTracker;
        this.mLocalMediaManager = new LocalMediaManager(context, localBluetoothManager, new InfoMediaManager(context, str, null, localBluetoothManager), str);
        this.mMetricLogger = new MediaOutputMetricLogger(context, str);
        this.mDialogLaunchAnimator = dialogLaunchAnimator;
        this.mNearbyMediaDevicesManager = optional.orElse(null);
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
            if (!this.mLocalMediaManager.isPreferenceRouteListingExist()) {
                attachRangeInfo(list);
                Collections.sort(list, Comparator.naturalOrder());
            }
            boolean z = (this.mAudioManager.getMutingExpectedDevice() != null) && !isCurrentConnectedDeviceRemote();
            MediaDevice currentConnectedMediaDevice = z ? null : getCurrentConnectedMediaDevice();
            if (((CopyOnWriteArrayList) this.mMediaItemList).isEmpty()) {
                if (currentConnectedMediaDevice != null) {
                    categorizeMediaItems(currentConnectedMediaDevice, list, false);
                    return;
                }
                if (DEBUG) {
                    Log.d("MediaOutputController", "No connected media device or muting expected device exist.");
                }
                categorizeMediaItems(null, list, z);
                return;
            }
            ArrayList arrayList = new ArrayList();
            HashMap hashMap = new HashMap();
            Iterator it = ((CopyOnWriteArrayList) this.mMediaItemList).iterator();
            while (it.hasNext()) {
                MediaItem mediaItem = (MediaItem) it.next();
                Iterator it2 = list.iterator();
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
                    hashMap.put(Integer.valueOf(((CopyOnWriteArrayList) this.mMediaItemList).indexOf(mediaItem)), mediaItem);
                }
            }
            if (arrayList.size() != list.size()) {
                list.removeAll(arrayList);
                arrayList.addAll(list);
            }
            final List list2 = (List) arrayList.stream().map(new MediaOutputController$$ExternalSyntheticLambda2(0)).collect(Collectors.toList());
            hashMap.forEach(new BiConsumer() { // from class: com.android.systemui.media.dialog.MediaOutputController$$ExternalSyntheticLambda3
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    list2.add(((Integer) obj).intValue(), (MediaItem) obj2);
                }
            });
            if (!isCurrentConnectedDeviceRemote() && ((ArrayList) getSelectedMediaDevice()).size() == 1) {
                list2.add(new MediaItem());
            }
            ((CopyOnWriteArrayList) this.mMediaItemList).clear();
            ((CopyOnWriteArrayList) this.mMediaItemList).addAll(list2);
        }
    }

    public final void categorizeMediaItems(MediaDevice mediaDevice, List list, boolean z) {
        synchronized (this.mMediaDevicesLock) {
            ArrayList arrayList = new ArrayList();
            Set set = (Set) getSelectedMediaDevice().stream().map(new MediaOutputController$$ExternalSyntheticLambda2(1)).collect(Collectors.toSet());
            if (mediaDevice != null) {
                set.add(mediaDevice.getId());
            }
            Iterator it = list.iterator();
            boolean z2 = false;
            boolean z3 = false;
            while (it.hasNext()) {
                MediaDevice mediaDevice2 = (MediaDevice) it.next();
                if (z && mediaDevice2.isMutingExpectedDevice()) {
                    arrayList.add(0, new MediaItem(mediaDevice2));
                } else if (z || !set.contains(mediaDevice2.getId())) {
                    RouteListingPreference.Item item = mediaDevice2.mItem;
                    if (!((item == null || (item.getFlags() & 4) == 0) ? false : true) || z2) {
                        RouteListingPreference.Item item2 = mediaDevice2.mItem;
                        if (!((item2 == null || (item2.getFlags() & 4) == 0) ? false : true) && !z3) {
                            arrayList.add(new MediaItem(this.mContext.getString(R.string.media_output_group_title_speakers_and_displays), 1));
                            z3 = true;
                        }
                    } else {
                        arrayList.add(new MediaItem(this.mContext.getString(R.string.media_output_group_title_suggested_device), 1));
                        z2 = true;
                    }
                    arrayList.add(new MediaItem(mediaDevice2));
                } else {
                    arrayList.add(0, new MediaItem(mediaDevice2));
                }
            }
            if (!isCurrentConnectedDeviceRemote() && ((ArrayList) getSelectedMediaDevice()).size() == 1) {
                arrayList.add(new MediaItem());
            }
            ((CopyOnWriteArrayList) this.mMediaItemList).clear();
            ((CopyOnWriteArrayList) this.mMediaItemList).addAll(arrayList);
        }
    }

    public final String getAppSourceName() {
        ApplicationInfo applicationInfo = null;
        if (this.mPackageName.isEmpty()) {
            return null;
        }
        PackageManager packageManager = this.mContext.getPackageManager();
        try {
            applicationInfo = packageManager.getApplicationInfo(this.mPackageName, PackageManager.ApplicationInfoFlags.of(0L));
        } catch (PackageManager.NameNotFoundException unused) {
        }
        return (String) (applicationInfo != null ? packageManager.getApplicationLabel(applicationInfo) : this.mContext.getString(R.string.media_output_dialog_unknown_launch_app_name));
    }

    public final MediaDevice getCurrentConnectedMediaDevice() {
        return this.mLocalMediaManager.getCurrentConnectedDevice();
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
                return IconCompat.createFromIcon(largeIcon);
            }
        }
        return null;
    }

    public final CharSequence getHeaderTitle() {
        MediaMetadata metadata;
        MediaController mediaController = this.mMediaController;
        return (mediaController == null || (metadata = mediaController.getMetadata()) == null) ? this.mContext.getText(R.string.controls_media_title) : metadata.getDescription().getTitle();
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
                    return IconCompat.createFromIcon(smallIcon);
                }
            }
        }
        return null;
    }

    public final List getSelectedMediaDevice() {
        return this.mLocalMediaManager.getSelectedMediaDevice();
    }

    public final boolean hasAdjustVolumeUserRestriction() {
        if (RestrictedLockUtilsInternal.checkIfRestrictionEnforced(this.mContext, "no_adjust_volume", UserHandle.myUserId()) != null) {
            return true;
        }
        return ((UserManager) this.mContext.getSystemService(UserManager.class)).hasBaseUserRestriction("no_adjust_volume", UserHandle.of(UserHandle.myUserId()));
    }

    public final boolean isAnyDeviceTransferring() {
        synchronized (this.mMediaDevicesLock) {
            Iterator it = ((CopyOnWriteArrayList) this.mMediaItemList).iterator();
            while (it.hasNext()) {
                MediaItem mediaItem = (MediaItem) it.next();
                if (mediaItem.mMediaDeviceOptional.isPresent() && ((MediaDevice) mediaItem.mMediaDeviceOptional.get()).mState == 1) {
                    return true;
                }
            }
            return false;
        }
    }

    public final boolean isCurrentConnectedDeviceRemote() {
        MediaDevice currentConnectedMediaDevice = getCurrentConnectedMediaDevice();
        return currentConnectedMediaDevice != null && isActiveRemoteDevice(currentConnectedMediaDevice);
    }

    public final boolean isPlaying() {
        PlaybackState playbackState;
        MediaController mediaController = this.mMediaController;
        return (mediaController == null || (playbackState = mediaController.getPlaybackState()) == null || playbackState.getState() != 3) ? false : true;
    }

    public final void launchLeBroadcastNotifyDialog(BroadcastNotifyDialog broadcastNotifyDialog, MediaOutputBaseDialog$$ExternalSyntheticLambda4 mediaOutputBaseDialog$$ExternalSyntheticLambda4) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
        int i = AbstractC18252.f310x2ffb14dc[broadcastNotifyDialog.ordinal()];
        if (i == 1) {
            builder.setTitle(R.string.media_output_first_broadcast_title);
            builder.setMessage(R.string.media_output_first_notify_broadcast_message);
            builder.setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null);
            builder.setPositiveButton(R.string.media_output_broadcast, mediaOutputBaseDialog$$ExternalSyntheticLambda4);
        } else if (i == 2) {
            builder.setTitle(R.string.media_output_broadcast);
            builder.setMessage(R.string.media_output_broadcasting_message);
            builder.setPositiveButton(android.R.string.ok, (DialogInterface.OnClickListener) null);
        }
        AlertDialog create = builder.create();
        create.getWindow().setType(2009);
        SystemUIDialog.setShowForAllUsers(create);
        SystemUIDialog.registerDismissListener(create, null);
        create.show();
    }

    @Override // com.android.settingslib.media.LocalMediaManager.DeviceCallback
    public final void onDeviceAttributesChanged() {
        MediaOutputBaseDialog mediaOutputBaseDialog = (MediaOutputBaseDialog) this.mCallback;
        mediaOutputBaseDialog.mMainThreadHandler.post(new MediaOutputBaseDialog$$ExternalSyntheticLambda1(mediaOutputBaseDialog, 2));
    }

    @Override // com.android.settingslib.media.LocalMediaManager.DeviceCallback
    public final void onDeviceListUpdate(List list) {
        if (((CopyOnWriteArrayList) this.mMediaItemList).isEmpty() || !this.mIsRefreshing) {
            buildMediaItems(list);
            MediaOutputBaseDialog mediaOutputBaseDialog = (MediaOutputBaseDialog) this.mCallback;
            mediaOutputBaseDialog.mMainThreadHandler.post(new MediaOutputBaseDialog$$ExternalSyntheticLambda1(mediaOutputBaseDialog, 1));
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
        this.mNearbyMediaDevicesManager.unregisterNearbyDevicesCallback(this);
    }

    @Override // com.android.settingslib.media.LocalMediaManager.DeviceCallback
    public final void onRequestFailed(int i) {
        int i2;
        MediaOutputBaseDialog mediaOutputBaseDialog = (MediaOutputBaseDialog) this.mCallback;
        int i3 = 2;
        mediaOutputBaseDialog.mMainThreadHandler.post(new MediaOutputBaseDialog$$ExternalSyntheticLambda1(mediaOutputBaseDialog, 2));
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
        if (i != 1) {
            if (i == 2) {
                i2 = 3;
                SysUiStatsLog.write(loggingDeviceType, loggingDeviceType2, 0, i2, mediaOutputMetricLogger.getLoggingPackageName(), mediaOutputMetricLogger.mWiredDeviceCount, mediaOutputMetricLogger.mConnectedBluetoothDeviceCount, mediaOutputMetricLogger.mRemoteDeviceCount);
            } else {
                i3 = 4;
                if (i != 3) {
                    i3 = i != 4 ? 0 : 5;
                }
            }
        }
        i2 = i3;
        SysUiStatsLog.write(loggingDeviceType, loggingDeviceType2, 0, i2, mediaOutputMetricLogger.getLoggingPackageName(), mediaOutputMetricLogger.mWiredDeviceCount, mediaOutputMetricLogger.mConnectedBluetoothDeviceCount, mediaOutputMetricLogger.mRemoteDeviceCount);
    }

    @Override // com.android.settingslib.media.LocalMediaManager.DeviceCallback
    public final void onSelectedDeviceStateChanged(MediaDevice mediaDevice) {
        MediaOutputBaseDialog mediaOutputBaseDialog = (MediaOutputBaseDialog) this.mCallback;
        mediaOutputBaseDialog.mMainThreadHandler.post(new MediaOutputBaseDialog$$ExternalSyntheticLambda1(mediaOutputBaseDialog, 2));
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
        SysUiStatsLog.write(MediaOutputMetricLogger.getLoggingDeviceType(mediaOutputMetricLogger.mSourceDevice), MediaOutputMetricLogger.getLoggingDeviceType(mediaOutputMetricLogger.mTargetDevice), 1, 1, mediaOutputMetricLogger.getLoggingPackageName(), mediaOutputMetricLogger.mWiredDeviceCount, mediaOutputMetricLogger.mConnectedBluetoothDeviceCount, mediaOutputMetricLogger.mRemoteDeviceCount);
    }

    public final void releaseSession() {
        MediaOutputMetricLogger mediaOutputMetricLogger = this.mMetricLogger;
        if (MediaOutputMetricLogger.DEBUG) {
            mediaOutputMetricLogger.getClass();
            Log.d("MediaOutputMetricLogger", "logInteraction - Stop casting");
        }
        SysUiStatsLog.write(2, 0, mediaOutputMetricLogger.getLoggingPackageName());
        this.mLocalMediaManager.releaseSession();
    }

    public final void setBroadcastCode(String str) {
        LocalBluetoothLeBroadcast localBluetoothLeBroadcast = this.mLocalBluetoothManager.mProfileManager.mLeAudioBroadcast;
        if (localBluetoothLeBroadcast == null) {
            Log.d("MediaOutputController", "setBroadcastCode: LE Audio Broadcast is null");
        } else {
            localBluetoothLeBroadcast.setBroadcastCode(true, str.getBytes(StandardCharsets.UTF_8));
        }
    }

    public final boolean startBluetoothLeBroadcast() {
        LocalBluetoothLeBroadcast localBluetoothLeBroadcast = this.mLocalBluetoothManager.mProfileManager.mLeAudioBroadcast;
        if (localBluetoothLeBroadcast == null) {
            Log.d("MediaOutputController", "The broadcast profile is null");
            return false;
        }
        localBluetoothLeBroadcast.mNewAppSourceName = getAppSourceName();
        if (localBluetoothLeBroadcast.mService == null) {
            Log.d("LocalBluetoothLeBroadcast", "The BluetoothLeBroadcast is null when starting the broadcast.");
            return true;
        }
        String str = localBluetoothLeBroadcast.mProgramInfo;
        AbstractC0000x2c234b15.m3m("startBroadcast: language = null ,programInfo = ", str, "LocalBluetoothLeBroadcast");
        localBluetoothLeBroadcast.mService.startBroadcast(localBluetoothLeBroadcast.mBuilder.setLanguage((String) null).setProgramInfo(str).build(), localBluetoothLeBroadcast.mBroadcastCode);
        return true;
    }

    public final boolean stopBluetoothLeBroadcast() {
        LocalBluetoothLeBroadcast localBluetoothLeBroadcast = this.mLocalBluetoothManager.mProfileManager.mLeAudioBroadcast;
        if (localBluetoothLeBroadcast == null) {
            Log.d("MediaOutputController", "The broadcast profile is null");
            return false;
        }
        int i = localBluetoothLeBroadcast.mBroadcastId;
        if (localBluetoothLeBroadcast.mService == null) {
            Log.d("LocalBluetoothLeBroadcast", "The BluetoothLeBroadcast is null when stopping the broadcast.");
            return true;
        }
        Log.d("LocalBluetoothLeBroadcast", "stopBroadcast()");
        localBluetoothLeBroadcast.mService.stopBroadcast(i);
        return true;
    }

    public final void tryToLaunchInAppRoutingIntent(View view, String str) {
        ComponentName linkedItemComponentName = this.mLocalMediaManager.getLinkedItemComponentName();
        if (linkedItemComponentName != null) {
            DialogLaunchAnimator dialogLaunchAnimator = this.mDialogLaunchAnimator;
            dialogLaunchAnimator.getClass();
            DialogLaunchAnimator$createActivityLaunchController$1 createActivityLaunchController$default = DialogLaunchAnimator.createActivityLaunchController$default(dialogLaunchAnimator, view);
            Intent intent = new Intent("android.media.action.TRANSFER_MEDIA");
            intent.setComponent(linkedItemComponentName);
            intent.putExtra("android.media.extra.ROUTE_ID", str);
            intent.addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
            ((MediaOutputBaseDialog) this.mCallback).mBroadcastSender.closeSystemDialogs();
            this.mActivityStarter.startActivity(intent, true, (ActivityLaunchAnimator.Controller) createActivityLaunchController$default);
        }
    }

    public final void tryToLaunchMediaApplication(View view) {
        DialogLaunchAnimator dialogLaunchAnimator = this.mDialogLaunchAnimator;
        dialogLaunchAnimator.getClass();
        DialogLaunchAnimator$createActivityLaunchController$1 createActivityLaunchController$default = DialogLaunchAnimator.createActivityLaunchController$default(dialogLaunchAnimator, view);
        Intent launchIntentForPackage = this.mPackageName.isEmpty() ? null : this.mContext.getPackageManager().getLaunchIntentForPackage(this.mPackageName);
        if (launchIntentForPackage != null) {
            launchIntentForPackage.addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
            ((MediaOutputBaseDialog) this.mCallback).mBroadcastSender.closeSystemDialogs();
            this.mActivityStarter.startActivity(launchIntentForPackage, true, (ActivityLaunchAnimator.Controller) createActivityLaunchController$default);
        }
    }
}
