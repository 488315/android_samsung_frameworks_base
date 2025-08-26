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
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import androidx.core.graphics.drawable.IconCompat;
import androidx.core.graphics.drawable.RoundedBitmapDrawable21;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.settingslib.bluetooth.LocalBluetoothLeBroadcast;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.media.InfoMediaDevice;
import com.android.settingslib.media.InfoMediaManager;
import com.android.settingslib.media.InputRouteManager;
import com.android.settingslib.media.LocalMediaManager;
import com.android.settingslib.media.MediaDevice;
import com.android.systemui.R;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.media.nearby.NearbyMediaDevicesManager;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.shared.system.SysUiStatsLog;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.volume.panel.domain.interactor.VolumePanelGlobalStateInteractor;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

/* loaded from: classes2.dex */
public class MediaSwitchingController implements LocalMediaManager.DeviceCallback, INearbyMediaDevicesUpdateCallback {
    public static final boolean DEBUG = Log.isLoggable("MediaSwitchingController", 3);
    public final ActivityStarter mActivityStarter;
    public final AudioManager mAudioManager;
    public Executor mBackgroundExecutor;
    Callback mCallback;
    public final Context mContext;
    public int mCurrentState;
    public final DialogTransitionAnimator mDialogTransitionAnimator;
    public final FeatureFlags mFeatureFlags;
    InputRouteManager mInputRouteManager;
    public final KeyguardManager mKeyGuardManager;
    public final LocalBluetoothManager mLocalBluetoothManager;
    LocalMediaManager mLocalMediaManager;
    public Executor mMainExecutor;
    public MediaController mMediaController;
    public MediaOutputColorScheme mMediaOutputColorScheme;
    public MediaOutputColorSchemeLegacy mMediaOutputColorSchemeLegacy;
    public final MediaSessionManager mMediaSessionManager;
    MediaOutputMetricLogger mMetricLogger;
    public final NearbyMediaDevicesManager mNearbyMediaDevicesManager;
    public final CommonNotifCollection mNotifCollection;
    public final OutputMediaItemListProxy mOutputMediaItemListProxy;
    public final String mPackageName;
    public final PowerExemptionManager mPowerExemptionManager;
    public final MediaSession.Token mToken;
    public final UserHandle mUserHandle;
    public UserTracker mUserTracker;
    public final VolumePanelGlobalStateInteractor mVolumePanelGlobalStateInteractor;
    public final Object mMediaDevicesLock = new Object();
    public final Object mInputMediaDevicesLock = new Object();
    final List<MediaDevice> mGroupMediaDevices = new CopyOnWriteArrayList();
    public final List mCachedMediaDevices = new CopyOnWriteArrayList();
    public final List mInputMediaItemList = new CopyOnWriteArrayList();
    public final Map mNearbyDeviceInfoMap = new ConcurrentHashMap();
    boolean mIsRefreshing = false;
    boolean mNeedRefresh = false;
    final InputRouteManager.InputDeviceCallback mInputDeviceCallback = new AnonymousClass1();
    final MediaController.Callback mCb = new MediaController.Callback() { // from class: com.android.systemui.media.dialog.MediaSwitchingController.2
        @Override // android.media.session.MediaController.Callback
        public final void onMetadataChanged(MediaMetadata mediaMetadata) {
            MediaOutputBaseDialog mediaOutputBaseDialog = (MediaOutputBaseDialog) MediaSwitchingController.this.mCallback;
            mediaOutputBaseDialog.mMainThreadHandler.post(new MediaOutputBaseDialog$$ExternalSyntheticLambda0(mediaOutputBaseDialog, 1));
        }

        @Override // android.media.session.MediaController.Callback
        public final void onPlaybackStateChanged(PlaybackState playbackState) {
            int state = playbackState == null ? 1 : playbackState.getState();
            MediaSwitchingController mediaSwitchingController = MediaSwitchingController.this;
            if (mediaSwitchingController.mCurrentState == state) {
                return;
            }
            if (state == 1) {
                MediaOutputBaseDialog mediaOutputBaseDialog = (MediaOutputBaseDialog) mediaSwitchingController.mCallback;
                if (mediaOutputBaseDialog.isShowing()) {
                    mediaOutputBaseDialog.dismiss();
                }
            }
            MediaSwitchingController.this.mCurrentState = state;
        }
    };

    /* renamed from: com.android.systemui.media.dialog.MediaSwitchingController$1, reason: invalid class name */
    public class AnonymousClass1 implements InputRouteManager.InputDeviceCallback {
        public AnonymousClass1() {
        }

        public final void onInputDeviceListUpdated(List list) {
            synchronized (MediaSwitchingController.this.mInputMediaDevicesLock) {
                MediaSwitchingController.m2630$$Nest$mbuildInputMediaItems(MediaSwitchingController.this, list);
                MediaOutputBaseDialog mediaOutputBaseDialog = (MediaOutputBaseDialog) MediaSwitchingController.this.mCallback;
                mediaOutputBaseDialog.mMainThreadHandler.post(new MediaOutputBaseDialog$$ExternalSyntheticLambda0(mediaOutputBaseDialog, 0));
            }
        }
    }

    public enum BroadcastNotifyDialog {
        /* JADX INFO: Fake field, exist only in values array */
        ACTION_FIRST_LAUNCH,
        ACTION_BROADCAST_INFO_ICON
    }

    public interface Callback {
        void dismissDialog();
    }

    public interface Factory {
        MediaSwitchingController create(String str, UserHandle userHandle, MediaSession.Token token);
    }

    /* renamed from: -$$Nest$mbuildInputMediaItems, reason: not valid java name */
    public static void m2630$$Nest$mbuildInputMediaItems(MediaSwitchingController mediaSwitchingController, List list) {
        synchronized (mediaSwitchingController.mInputMediaDevicesLock) {
            List list2 = list.stream().map(new MediaSwitchingController$$ExternalSyntheticLambda0(1)).toList();
            ((CopyOnWriteArrayList) mediaSwitchingController.mInputMediaItemList).clear();
            ((CopyOnWriteArrayList) mediaSwitchingController.mInputMediaItemList).addAll(list2);
        }
    }

    public MediaSwitchingController(Context context, String str, UserHandle userHandle, MediaSession.Token token, MediaSessionManager mediaSessionManager, LocalBluetoothManager localBluetoothManager, ActivityStarter activityStarter, CommonNotifCollection commonNotifCollection, DialogTransitionAnimator dialogTransitionAnimator, NearbyMediaDevicesManager nearbyMediaDevicesManager, AudioManager audioManager, PowerExemptionManager powerExemptionManager, KeyguardManager keyguardManager, FeatureFlags featureFlags, VolumePanelGlobalStateInteractor volumePanelGlobalStateInteractor, UserTracker userTracker) {
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
        this.mVolumePanelGlobalStateInteractor = volumePanelGlobalStateInteractor;
        this.mLocalMediaManager = new LocalMediaManager(context, localBluetoothManager, InfoMediaManager.createInstance(context, str, userHandle, localBluetoothManager, token), str);
        this.mMetricLogger = new MediaOutputMetricLogger(context, str);
        this.mOutputMediaItemListProxy = new OutputMediaItemListProxy(context);
        this.mDialogTransitionAnimator = dialogTransitionAnimator;
        this.mNearbyMediaDevicesManager = nearbyMediaDevicesManager;
        MediaOutputColorScheme.Factory.getClass();
        this.mMediaOutputColorScheme = new MediaOutputColorSchemeSystem(context);
        MediaOutputColorSchemeLegacy.Factory.getClass();
        this.mMediaOutputColorSchemeLegacy = new MediaOutputColorSchemeLegacySystem(context);
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
            try {
                boolean z = false;
                if (!InfoMediaManager.Api34Impl.preferRouteListingOrdering(this.mLocalMediaManager.mInfoMediaManager.getRouteListingPreference())) {
                    attachRangeInfo(list);
                    ArrayList arrayList = new ArrayList();
                    Set set = (Set) this.mLocalMediaManager.getSelectedMediaDevice().stream().map(new MediaSwitchingController$$ExternalSyntheticLambda0(0)).collect(Collectors.toSet());
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        MediaDevice mediaDevice = (MediaDevice) it.next();
                        if (set.contains(mediaDevice.getId())) {
                            arrayList.add(mediaDevice);
                        }
                    }
                    list.removeAll(arrayList);
                    Collections.sort(list, Comparator.naturalOrder());
                    list.addAll(0, arrayList);
                }
                if ((this.mAudioManager.getMutingExpectedDevice() != null) && !isCurrentConnectedDeviceRemote()) {
                    z = true;
                }
                this.mOutputMediaItemListProxy.updateMediaDevices(list, this.mLocalMediaManager.getSelectedMediaDevice(), z ? null : this.mLocalMediaManager.getCurrentConnectedDevice(), z);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void clearMediaItemList() {
        this.mOutputMediaItemListProxy.clear();
    }

    public final String getAppSourceName() throws PackageManager.NameNotFoundException {
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
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(iconBitmap.getWidth(), iconBitmap.getHeight(), Bitmap.Config.ARGB_8888);
            RoundedBitmapDrawable21 roundedBitmapDrawable21 = new RoundedBitmapDrawable21(context.getResources(), iconBitmap);
            roundedBitmapDrawable21.mPaint.setAntiAlias(true);
            roundedBitmapDrawable21.invalidateSelf();
            roundedBitmapDrawable21.setCornerRadius(dimensionPixelSize);
            Canvas canvas = new Canvas(bitmapCreateBitmap);
            roundedBitmapDrawable21.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            roundedBitmapDrawable21.draw(canvas);
            return IconCompat.createWithBitmap(bitmapCreateBitmap);
        }
        if (DEBUG) {
            Log.d("MediaSwitchingController", "Media meta data does not contain icon information");
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

    public final boolean isAnyDeviceTransferring() {
        synchronized (this.mMediaDevicesLock) {
            try {
                Iterator it = ((CopyOnWriteArrayList) this.mOutputMediaItemListProxy.getOutputMediaItemList()).iterator();
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
        mediaOutputBaseDialog.mMainThreadHandler.post(new MediaOutputBaseDialog$$ExternalSyntheticLambda0(mediaOutputBaseDialog, 2));
    }

    @Override // com.android.settingslib.media.LocalMediaManager.DeviceCallback
    public final void onDeviceListUpdate(List list) {
        if (this.mOutputMediaItemListProxy.isEmpty() || !this.mIsRefreshing) {
            buildMediaItems(list);
            MediaOutputBaseDialog mediaOutputBaseDialog = (MediaOutputBaseDialog) this.mCallback;
            mediaOutputBaseDialog.mMainThreadHandler.post(new MediaOutputBaseDialog$$ExternalSyntheticLambda0(mediaOutputBaseDialog, 0));
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
        ArrayList arrayList = (ArrayList) nearbyMediaDevicesManager.providers;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((INearbyMediaDevicesProvider) obj).unregisterNearbyDevicesCallback(this);
        }
    }

    @Override // com.android.settingslib.media.LocalMediaManager.DeviceCallback
    public final void onRequestFailed(int i) {
        int i2;
        MediaOutputBaseDialog mediaOutputBaseDialog = (MediaOutputBaseDialog) this.mCallback;
        mediaOutputBaseDialog.mMainThreadHandler.post(new MediaOutputBaseDialog$$ExternalSyntheticLambda0(mediaOutputBaseDialog, 2));
        MediaOutputMetricLogger mediaOutputMetricLogger = this.mMetricLogger;
        ArrayList arrayList = new ArrayList(this.mOutputMediaItemListProxy.getOutputMediaItemList());
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
        int i3 = 2;
        if (i == 1) {
            i2 = i3;
        } else if (i != 2) {
            i3 = 4;
            if (i != 3) {
                i3 = i != 4 ? 0 : 5;
            }
            i2 = i3;
        } else {
            i2 = 3;
        }
        SysUiStatsLog.write(loggingDeviceType, loggingDeviceType2, 0, i2, mediaOutputMetricLogger.getLoggingPackageName(), mediaOutputMetricLogger.mWiredDeviceCount, mediaOutputMetricLogger.mConnectedBluetoothDeviceCount, mediaOutputMetricLogger.mRemoteDeviceCount, mediaOutputMetricLogger.mTargetDevice.isSuggestedDevice(), MediaDevice.Api34Impl.hasOngoingSession(mediaOutputMetricLogger.mTargetDevice.mItem));
    }

    @Override // com.android.settingslib.media.LocalMediaManager.DeviceCallback
    public final void onSelectedDeviceStateChanged(MediaDevice mediaDevice) {
        MediaOutputBaseDialog mediaOutputBaseDialog = (MediaOutputBaseDialog) this.mCallback;
        mediaOutputBaseDialog.mMainThreadHandler.post(new MediaOutputBaseDialog$$ExternalSyntheticLambda0(mediaOutputBaseDialog, 2));
        MediaOutputMetricLogger mediaOutputMetricLogger = this.mMetricLogger;
        String string = mediaDevice.toString();
        ArrayList arrayList = new ArrayList(this.mOutputMediaItemListProxy.getOutputMediaItemList());
        if (MediaOutputMetricLogger.DEBUG) {
            mediaOutputMetricLogger.getClass();
            Log.d("MediaOutputMetricLogger", "logOutputSuccess - selected device: " + string);
        }
        if (mediaOutputMetricLogger.mSourceDevice == null && mediaOutputMetricLogger.mTargetDevice == null) {
            return;
        }
        mediaOutputMetricLogger.updateLoggingMediaItemCount(arrayList);
        SysUiStatsLog.write(MediaOutputMetricLogger.getLoggingDeviceType(mediaOutputMetricLogger.mSourceDevice), MediaOutputMetricLogger.getLoggingDeviceType(mediaOutputMetricLogger.mTargetDevice), 1, 1, mediaOutputMetricLogger.getLoggingPackageName(), mediaOutputMetricLogger.mWiredDeviceCount, mediaOutputMetricLogger.mConnectedBluetoothDeviceCount, mediaOutputMetricLogger.mRemoteDeviceCount, mediaOutputMetricLogger.mTargetDevice.isSuggestedDevice(), MediaDevice.Api34Impl.hasOngoingSession(mediaOutputMetricLogger.mTargetDevice.mItem));
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
            Log.d("MediaSwitchingController", "setBroadcastCode: LE Audio Broadcast is null");
        } else {
            localBluetoothLeBroadcast.setBroadcastCode(true, str.getBytes(StandardCharsets.UTF_8));
        }
    }

    public final void startActivity(Intent intent, DialogTransitionAnimator.AnonymousClass1 anonymousClass1) {
        this.mVolumePanelGlobalStateInteractor.setVisible(false);
        this.mActivityStarter.startActivity(intent, true, (ActivityTransitionAnimator.Controller) anonymousClass1);
    }

    public final void tryToLaunchInAppRoutingIntent(View view, String str) {
        ComponentName linkedItemComponentName = InfoMediaManager.Api34Impl.getLinkedItemComponentName(this.mLocalMediaManager.mInfoMediaManager.getRouteListingPreference());
        if (linkedItemComponentName != null) {
            DialogTransitionAnimator dialogTransitionAnimator = this.mDialogTransitionAnimator;
            dialogTransitionAnimator.getClass();
            DialogTransitionAnimator.AnonymousClass1 anonymousClass1CreateActivityTransitionController$default = DialogTransitionAnimator.createActivityTransitionController$default(dialogTransitionAnimator, view);
            Intent intent = new Intent("android.media.action.TRANSFER_MEDIA");
            intent.setComponent(linkedItemComponentName);
            intent.putExtra("android.media.extra.ROUTE_ID", str);
            intent.addFlags(268435456);
            this.mCallback.dismissDialog();
            startActivity(intent, anonymousClass1CreateActivityTransitionController$default);
        }
    }
}
