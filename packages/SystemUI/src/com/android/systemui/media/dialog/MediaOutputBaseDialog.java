package com.android.systemui.media.dialog;

import android.app.Notification;
import android.app.WallpaperColors;
import android.bluetooth.BluetoothLeBroadcast;
import android.bluetooth.BluetoothLeBroadcastMetadata;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.media.INearbyMediaDevicesProvider;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import androidx.core.graphics.drawable.IconCompat;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.settingslib.bluetooth.LocalBluetoothLeBroadcast;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastSender;
import com.android.systemui.broadcast.BroadcastSender$$ExternalSyntheticLambda1;
import com.android.systemui.media.dialog.MediaSwitchingController;
import com.android.systemui.media.nearby.NearbyMediaDevicesManager;
import com.android.systemui.monet.ColorScheme;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class MediaOutputBaseDialog extends SystemUIDialog implements MediaSwitchingController.Callback, Window.Callback {
    public static final /* synthetic */ int $r8$clinit = 0;
    public MediaOutputAdapterLegacy mAdapter;
    public ImageView mAppResourceIcon;
    public final AnonymousClass1 mBroadcastCallback;
    public ImageView mBroadcastIcon;
    public final BroadcastSender mBroadcastSender;
    public final Context mContext;
    public ViewGroup mDeviceListLayout;
    public RecyclerView mDevicesRecyclerView;
    public ViewGroup mDialogFooter;
    View mDialogView;
    public boolean mDismissing;
    public Button mDoneButton;
    public final Executor mExecutor;
    public View mFooterSpacer;
    public ImageView mHeaderIcon;
    public TextView mHeaderSubtitle;
    public TextView mHeaderTitle;
    public final boolean mIncludePlaybackAndAppMetadata;
    public boolean mIsLeBroadcastCallbackRegistered;
    public final LayoutManagerWrapper mLayoutManager;
    public final Handler mMainThreadHandler;
    public LinearLayout mMediaMetadataSectionLayout;
    public final MediaSwitchingController mMediaSwitchingController;
    public ViewGroup mQuickAccessShelf;
    public boolean mShouldLaunchLeBroadcastDialog;
    public Button mStopButton;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class LayoutManagerWrapper extends LinearLayoutManager {
        public LayoutManagerWrapper(Context context) {
            super(context);
        }

        @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
        public final void onLayoutCompleted(RecyclerView.State state) {
            super.onLayoutCompleted(state);
            MediaSwitchingController mediaSwitchingController = MediaOutputBaseDialog.this.mMediaSwitchingController;
            mediaSwitchingController.mIsRefreshing = false;
            if (mediaSwitchingController.mNeedRefresh) {
                mediaSwitchingController.buildMediaItems(mediaSwitchingController.mCachedMediaDevices);
                MediaOutputBaseDialog mediaOutputBaseDialog = (MediaOutputBaseDialog) mediaSwitchingController.mCallback;
                mediaOutputBaseDialog.mMainThreadHandler.post(new MediaOutputBaseDialog$$ExternalSyntheticLambda0(mediaOutputBaseDialog, 0));
                mediaSwitchingController.mNeedRefresh = false;
            }
        }
    }

    public MediaOutputBaseDialog(Context context, BroadcastSender broadcastSender, MediaSwitchingController mediaSwitchingController, boolean z) {
        super(context, R.style.Theme_SystemUI_Dialog_Media);
        this.mMainThreadHandler = new Handler(Looper.getMainLooper());
        this.mBroadcastCallback = new AnonymousClass1();
        Context context2 = getContext();
        this.mContext = context2;
        this.mBroadcastSender = broadcastSender;
        this.mMediaSwitchingController = mediaSwitchingController;
        this.mLayoutManager = new LayoutManagerWrapper(context2);
        this.mExecutor = Executors.newSingleThreadExecutor();
        this.mIncludePlaybackAndAppMetadata = z;
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public final void dismiss() {
        this.mDismissing = true;
        super.dismiss();
    }

    @Override // com.android.systemui.media.dialog.MediaSwitchingController.Callback
    public final void dismissDialog() {
        BroadcastSender broadcastSender = this.mBroadcastSender;
        broadcastSender.getClass();
        broadcastSender.sendInBackground("closeSystemDialogs", new BroadcastSender$$ExternalSyntheticLambda1(broadcastSender));
    }

    public abstract IconCompat getAppSourceIcon();

    public abstract IconCompat getHeaderIcon();

    public abstract CharSequence getHeaderSubtitle();

    public abstract CharSequence getHeaderText();

    public CharSequence getStopButtonText() {
        return this.mContext.getText(R.string.keyboard_key_media_stop);
    }

    public abstract int getStopButtonVisibility();

    public void handleLeBroadcastMetadataChanged() {
        if (this.mShouldLaunchLeBroadcastDialog) {
            startLeBroadcastDialog();
            this.mShouldLaunchLeBroadcastDialog = false;
        }
        refresh();
    }

    public void handleLeBroadcastStartFailed() {
        this.mStopButton.setText(R.string.media_output_broadcast_start_failed);
        this.mStopButton.setEnabled(false);
        refresh();
    }

    public void handleLeBroadcastStarted() {
        this.mShouldLaunchLeBroadcastDialog = true;
    }

    public void handleLeBroadcastStopFailed() {
        refresh();
    }

    public void handleLeBroadcastStopped() {
        this.mShouldLaunchLeBroadcastDialog = false;
        refresh();
    }

    public void handleLeBroadcastUpdateFailed() {
        refresh();
    }

    public void handleLeBroadcastUpdated() {
        refresh();
    }

    @Override // com.android.systemui.statusbar.phone.SystemUIDialog, android.app.AlertDialog, android.app.Dialog
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mDialogView = LayoutInflater.from(this.mContext).inflate(R.layout.media_output_dialog, (ViewGroup) null);
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.gravity = 17;
        attributes.setFitInsetsTypes(WindowInsets.Type.statusBars() | WindowInsets.Type.navigationBars());
        attributes.setFitInsetsSides(WindowInsets.Side.all());
        attributes.setFitInsetsIgnoringVisibility(true);
        window.setAttributes(attributes);
        window.setContentView(this.mDialogView);
        window.setTitle(this.mContext.getString(R.string.media_output_dialog_accessibility_title));
        window.setType(2017);
        this.mHeaderTitle = (TextView) this.mDialogView.requireViewById(R.id.header_title);
        this.mHeaderSubtitle = (TextView) this.mDialogView.requireViewById(R.id.header_subtitle);
        this.mHeaderIcon = (ImageView) this.mDialogView.requireViewById(R.id.header_icon);
        this.mQuickAccessShelf = (ViewGroup) this.mDialogView.requireViewById(R.id.quick_access_shelf);
        this.mDevicesRecyclerView = (RecyclerView) this.mDialogView.requireViewById(R.id.list_result);
        this.mDialogFooter = (ViewGroup) this.mDialogView.requireViewById(R.id.dialog_footer);
        this.mFooterSpacer = this.mDialogView.requireViewById(R.id.footer_spacer);
        this.mMediaMetadataSectionLayout = (LinearLayout) this.mDialogView.requireViewById(R.id.media_metadata_section);
        this.mDeviceListLayout = (ViewGroup) this.mDialogView.requireViewById(R.id.device_list);
        this.mDoneButton = (Button) this.mDialogView.requireViewById(R.id.done);
        this.mStopButton = (Button) this.mDialogView.requireViewById(R.id.stop);
        this.mAppResourceIcon = (ImageView) this.mDialogView.requireViewById(R.id.app_source_icon);
        this.mBroadcastIcon = (ImageView) this.mDialogView.requireViewById(R.id.broadcast_icon);
        LayoutManagerWrapper layoutManagerWrapper = this.mLayoutManager;
        layoutManagerWrapper.mAutoMeasure = true;
        this.mDevicesRecyclerView.setLayoutManager(layoutManagerWrapper);
        this.mDevicesRecyclerView.setAdapter(this.mAdapter);
        this.mDevicesRecyclerView.mHasFixedSize = false;
        this.mDoneButton.setOnClickListener(new MediaOutputBaseDialog$$ExternalSyntheticLambda1(this, 2));
        this.mStopButton.setOnClickListener(new MediaOutputBaseDialog$$ExternalSyntheticLambda1(this, 3));
        MediaSwitchingController mediaSwitchingController = this.mMediaSwitchingController;
        if ((TextUtils.isEmpty(mediaSwitchingController.mPackageName) ? null : mediaSwitchingController.mContext.getPackageManager().getLaunchIntentForPackage(mediaSwitchingController.mPackageName)) != null) {
            LinearLayout linearLayout = this.mMediaMetadataSectionLayout;
            MediaSwitchingController mediaSwitchingController2 = this.mMediaSwitchingController;
            Objects.requireNonNull(mediaSwitchingController2);
            linearLayout.setOnClickListener(new MediaOutputBaseDialog$$ExternalSyntheticLambda1(mediaSwitchingController2, 4));
        }
        this.mDismissing = false;
    }

    public void onStopButtonClick() {
        this.mMediaSwitchingController.releaseSession();
        dismiss();
    }

    public void refresh() {
        refresh(false);
    }

    @Override // com.android.systemui.statusbar.phone.SystemUIDialog
    public void start() {
        MediaController mediaController;
        MediaSwitchingController mediaSwitchingController = this.mMediaSwitchingController;
        synchronized (mediaSwitchingController.mMediaDevicesLock) {
            ((CopyOnWriteArrayList) mediaSwitchingController.mCachedMediaDevices).clear();
            mediaSwitchingController.mOutputMediaItemListProxy.clear();
        }
        ((ConcurrentHashMap) mediaSwitchingController.mNearbyDeviceInfoMap).clear();
        NearbyMediaDevicesManager nearbyMediaDevicesManager = mediaSwitchingController.mNearbyMediaDevicesManager;
        if (nearbyMediaDevicesManager != null) {
            ArrayList arrayList = (ArrayList) nearbyMediaDevicesManager.providers;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                ((INearbyMediaDevicesProvider) obj).registerNearbyDevicesCallback(mediaSwitchingController);
            }
            ((ArrayList) nearbyMediaDevicesManager.activeCallbacks).add(mediaSwitchingController);
        }
        if (!TextUtils.isEmpty(mediaSwitchingController.mPackageName)) {
            if (mediaSwitchingController.mToken != null) {
                mediaController = new MediaController(mediaSwitchingController.mContext, mediaSwitchingController.mToken);
            } else {
                Iterator it = ((NotifPipeline) mediaSwitchingController.mNotifCollection).getAllNotifs().iterator();
                while (true) {
                    if (it.hasNext()) {
                        NotificationEntry notificationEntry = (NotificationEntry) it.next();
                        Notification notification2 = notificationEntry.mSbn.getNotification();
                        if (notification2.isMediaNotification() && TextUtils.equals(notificationEntry.mSbn.getPackageName(), mediaSwitchingController.mPackageName)) {
                            r2 = new MediaController(mediaSwitchingController.mContext, (MediaSession.Token) notification2.extras.getParcelable("android.mediaSession", MediaSession.Token.class));
                            break;
                        }
                    } else {
                        for (MediaController mediaController2 : mediaSwitchingController.mMediaSessionManager.getActiveSessionsForUser(null, ((UserTrackerImpl) mediaSwitchingController.mUserTracker).getUserHandle())) {
                            if (TextUtils.equals(mediaController2.getPackageName(), mediaSwitchingController.mPackageName)) {
                            }
                        }
                        mediaController = null;
                    }
                }
                mediaController = mediaController2;
            }
            mediaSwitchingController.mMediaController = mediaController;
            if (mediaController != null) {
                mediaController.unregisterCallback(mediaSwitchingController.mCb);
                if (mediaSwitchingController.mMediaController.getPlaybackState() != null) {
                    mediaSwitchingController.mCurrentState = mediaSwitchingController.mMediaController.getPlaybackState().getState();
                }
                mediaSwitchingController.mMediaController.registerCallback(mediaSwitchingController.mCb);
            }
        }
        if (mediaSwitchingController.mMediaController == null && MediaSwitchingController.DEBUG) {
            ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder("No media controller for "), mediaSwitchingController.mPackageName, "MediaSwitchingController");
        }
        mediaSwitchingController.mCallback = this;
        mediaSwitchingController.mLocalMediaManager.registerCallback(mediaSwitchingController);
        mediaSwitchingController.mLocalMediaManager.mInfoMediaManager.startScanOnRouter();
    }

    public final void startLeBroadcastDialog() {
        MediaSwitchingController mediaSwitchingController = this.mMediaSwitchingController;
        BroadcastSender broadcastSender = this.mBroadcastSender;
        mediaSwitchingController.getClass();
        new MediaOutputBroadcastDialog(mediaSwitchingController.mContext, true, broadcastSender, new MediaSwitchingController(mediaSwitchingController.mContext, mediaSwitchingController.mPackageName, mediaSwitchingController.mUserHandle, mediaSwitchingController.mToken, mediaSwitchingController.mMediaSessionManager, mediaSwitchingController.mLocalBluetoothManager, mediaSwitchingController.mActivityStarter, mediaSwitchingController.mNotifCollection, mediaSwitchingController.mDialogTransitionAnimator, mediaSwitchingController.mNearbyMediaDevicesManager, mediaSwitchingController.mAudioManager, mediaSwitchingController.mPowerExemptionManager, mediaSwitchingController.mKeyGuardManager, mediaSwitchingController.mFeatureFlags, mediaSwitchingController.mVolumePanelGlobalStateInteractor, mediaSwitchingController.mUserTracker), mediaSwitchingController.mMainExecutor, mediaSwitchingController.mBackgroundExecutor).show();
        refresh();
    }

    @Override // com.android.systemui.statusbar.phone.SystemUIDialog
    public void stop() {
        LocalBluetoothLeBroadcast localBluetoothLeBroadcast = this.mMediaSwitchingController.mLocalBluetoothManager.mProfileManager.mLeAudioBroadcast;
        int i = 0;
        if (localBluetoothLeBroadcast != null && this.mIsLeBroadcastCallbackRegistered) {
            AnonymousClass1 anonymousClass1 = this.mBroadcastCallback;
            if (localBluetoothLeBroadcast == null) {
                Log.d("MediaSwitchingController", "The broadcast profile is null");
            } else {
                Log.d("MediaSwitchingController", "Unregister LE broadcast callback");
                localBluetoothLeBroadcast.unregisterServiceCallBack(anonymousClass1);
            }
            this.mIsLeBroadcastCallbackRegistered = false;
        }
        MediaSwitchingController mediaSwitchingController = this.mMediaSwitchingController;
        MediaController mediaController = mediaSwitchingController.mMediaController;
        if (mediaController != null) {
            mediaController.unregisterCallback(mediaSwitchingController.mCb);
        }
        mediaSwitchingController.mLocalMediaManager.unregisterCallback(mediaSwitchingController);
        mediaSwitchingController.mLocalMediaManager.mInfoMediaManager.stopScanOnRouter();
        synchronized (mediaSwitchingController.mMediaDevicesLock) {
            ((CopyOnWriteArrayList) mediaSwitchingController.mCachedMediaDevices).clear();
            mediaSwitchingController.mOutputMediaItemListProxy.clear();
        }
        NearbyMediaDevicesManager nearbyMediaDevicesManager = mediaSwitchingController.mNearbyMediaDevicesManager;
        if (nearbyMediaDevicesManager != null) {
            ((ArrayList) nearbyMediaDevicesManager.activeCallbacks).remove(mediaSwitchingController);
            ArrayList arrayList = (ArrayList) nearbyMediaDevicesManager.providers;
            int size = arrayList.size();
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                ((INearbyMediaDevicesProvider) obj).unregisterNearbyDevicesCallback(mediaSwitchingController);
            }
        }
        ((ConcurrentHashMap) mediaSwitchingController.mNearbyDeviceInfoMap).clear();
    }

    public final void updateButtonBackgroundColorFilter() {
        PorterDuffColorFilter porterDuffColorFilter = new PorterDuffColorFilter(this.mMediaSwitchingController.mMediaOutputColorSchemeLegacy.getColorButtonBackground(), PorterDuff.Mode.SRC_IN);
        this.mDoneButton.getBackground().setColorFilter(porterDuffColorFilter);
        this.mStopButton.getBackground().setColorFilter(porterDuffColorFilter);
        this.mDoneButton.setTextColor(this.mMediaSwitchingController.mMediaOutputColorSchemeLegacy.getColorPositiveButtonText());
    }

    public final void updateDialogBackgroundColor() {
        int colorDialogBackground = this.mMediaSwitchingController.mMediaOutputColorSchemeLegacy.getColorDialogBackground();
        this.mDialogView.getBackground().setTint(colorDialogBackground);
        this.mDeviceListLayout.setBackgroundColor(colorDialogBackground);
    }

    public final void refresh(boolean z) {
        boolean z2;
        if (this.mDismissing) {
            return;
        }
        MediaSwitchingController mediaSwitchingController = this.mMediaSwitchingController;
        if (mediaSwitchingController.mIsRefreshing) {
            return;
        }
        mediaSwitchingController.mIsRefreshing = true;
        IconCompat headerIcon = getHeaderIcon();
        IconCompat appSourceIcon = getAppSourceIcon();
        Drawable drawable = null;
        if (headerIcon != null) {
            Icon icon$1 = headerIcon.toIcon$1();
            if (icon$1.getType() == 1 || icon$1.getType() == 5) {
                boolean z3 = (this.mContext.getResources().getConfiguration().uiMode & 48) == 32;
                WallpaperColors fromBitmap = WallpaperColors.fromBitmap(icon$1.getBitmap());
                boolean equals = fromBitmap.equals(null);
                z2 = !equals;
                if (!equals) {
                    MediaSwitchingController mediaSwitchingController2 = this.mMediaSwitchingController;
                    mediaSwitchingController2.getClass();
                    ColorScheme colorScheme = new ColorScheme(fromBitmap, z3);
                    MediaOutputColorScheme.Factory.getClass();
                    mediaSwitchingController2.mMediaOutputColorScheme = new MediaOutputColorSchemeDynamic(colorScheme);
                    MediaOutputColorSchemeLegacy.Factory.getClass();
                    mediaSwitchingController2.mMediaOutputColorSchemeLegacy = new MediaOutputColorSchemeLegacyDynamic(colorScheme, z3);
                    updateButtonBackgroundColorFilter();
                    updateDialogBackgroundColor();
                }
            } else {
                updateButtonBackgroundColorFilter();
                updateDialogBackgroundColor();
                z2 = false;
            }
            this.mHeaderIcon.setVisibility(0);
            this.mHeaderIcon.setImageIcon(icon$1);
        } else {
            updateButtonBackgroundColorFilter();
            updateDialogBackgroundColor();
            this.mHeaderIcon.setVisibility(8);
            z2 = false;
        }
        if (!this.mIncludePlaybackAndAppMetadata) {
            this.mAppResourceIcon.setVisibility(8);
        } else if (appSourceIcon != null) {
            Icon icon$12 = appSourceIcon.toIcon$1();
            this.mAppResourceIcon.setColorFilter(this.mMediaSwitchingController.mMediaOutputColorSchemeLegacy.getColorItemContent());
            this.mAppResourceIcon.setImageIcon(icon$12);
        } else {
            MediaSwitchingController mediaSwitchingController3 = this.mMediaSwitchingController;
            if (!TextUtils.isEmpty(mediaSwitchingController3.mPackageName)) {
                try {
                    Log.d("MediaSwitchingController", "try to get app icon");
                    drawable = mediaSwitchingController3.mContext.getPackageManager().getApplicationIcon(mediaSwitchingController3.mPackageName);
                } catch (PackageManager.NameNotFoundException unused) {
                    Log.d("MediaSwitchingController", "icon not found");
                }
            }
            if (drawable != null) {
                this.mAppResourceIcon.setImageDrawable(drawable);
            } else {
                this.mAppResourceIcon.setVisibility(8);
            }
        }
        if (this.mIncludePlaybackAndAppMetadata) {
            this.mHeaderTitle.setText(getHeaderText());
            CharSequence headerSubtitle = getHeaderSubtitle();
            if (TextUtils.isEmpty(headerSubtitle)) {
                this.mHeaderSubtitle.setVisibility(8);
                this.mHeaderTitle.setGravity(8388627);
            } else {
                this.mHeaderSubtitle.setVisibility(0);
                this.mHeaderSubtitle.setText(headerSubtitle);
                this.mHeaderTitle.setGravity(0);
            }
        } else {
            this.mHeaderTitle.setVisibility(8);
            this.mHeaderSubtitle.setVisibility(8);
        }
        this.mStopButton.setVisibility(getStopButtonVisibility());
        this.mStopButton.setEnabled(true);
        this.mStopButton.setText(getStopButtonText());
        this.mStopButton.setOnClickListener(new MediaOutputBaseDialog$$ExternalSyntheticLambda1(this, 0));
        this.mBroadcastIcon.setVisibility(8);
        this.mBroadcastIcon.setOnClickListener(new MediaOutputBaseDialog$$ExternalSyntheticLambda1(this, 1));
        MediaOutputAdapterLegacy mediaOutputAdapterLegacy = this.mAdapter;
        if (mediaOutputAdapterLegacy.mIsDragging) {
            MediaSwitchingController mediaSwitchingController4 = this.mMediaSwitchingController;
            mediaSwitchingController4.mIsRefreshing = false;
            if (mediaSwitchingController4.mNeedRefresh) {
                mediaSwitchingController4.buildMediaItems(mediaSwitchingController4.mCachedMediaDevices);
                MediaOutputBaseDialog mediaOutputBaseDialog = (MediaOutputBaseDialog) mediaSwitchingController4.mCallback;
                mediaOutputBaseDialog.mMainThreadHandler.post(new MediaOutputBaseDialog$$ExternalSyntheticLambda0(mediaOutputBaseDialog, 0));
                mediaSwitchingController4.mNeedRefresh = false;
                return;
            }
            return;
        }
        int i = mediaOutputAdapterLegacy.mCurrentActivePosition;
        if (!z2 && !z && i >= 0 && i < mediaOutputAdapterLegacy.getItemCount()) {
            this.mAdapter.notifyItemChanged(i);
            return;
        }
        MediaOutputAdapterLegacy mediaOutputAdapterLegacy2 = this.mAdapter;
        ((CopyOnWriteArrayList) mediaOutputAdapterLegacy2.mMediaItemList).clear();
        List list = mediaOutputAdapterLegacy2.mMediaItemList;
        MediaSwitchingController mediaSwitchingController5 = mediaOutputAdapterLegacy2.mController;
        mediaSwitchingController5.getClass();
        ArrayList arrayList = new ArrayList(mediaSwitchingController5.mOutputMediaItemListProxy.getOutputMediaItemList());
        MediaItem createPairNewDeviceMediaItem = (mediaSwitchingController5.isCurrentConnectedDeviceRemote() || !(((ArrayList) mediaSwitchingController5.mLocalMediaManager.getSelectedMediaDevice()).size() == 1)) ? null : MediaItem.createPairNewDeviceMediaItem();
        if (createPairNewDeviceMediaItem != null) {
            arrayList.add(createPairNewDeviceMediaItem);
        }
        ((CopyOnWriteArrayList) list).addAll(arrayList);
        if (mediaOutputAdapterLegacy2.mShouldGroupSelectedMediaItems && ((ArrayList) mediaSwitchingController5.mLocalMediaManager.getSelectedMediaDevice()).size() == 1) {
            mediaOutputAdapterLegacy2.mShouldGroupSelectedMediaItems = false;
        }
        mediaOutputAdapterLegacy2.notifyDataSetChanged();
    }

    public void onBroadcastIconClick() {
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.media.dialog.MediaOutputBaseDialog$1, reason: invalid class name */
    public class AnonymousClass1 implements BluetoothLeBroadcast.Callback {
        public AnonymousClass1() {
        }

        public final void onBroadcastMetadataChanged(int i, BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata) {
            Log.d("MediaOutputDialog", "onBroadcastMetadataChanged(), broadcastId = " + i + ", metadata = " + bluetoothLeBroadcastMetadata);
            MediaOutputBaseDialog.this.mMainThreadHandler.post(new MediaOutputBaseDialog$1$$ExternalSyntheticLambda0(this, 5));
        }

        public final void onBroadcastStartFailed(int i) {
            ListPopupWindow$$ExternalSyntheticOutline0.m(i, "onBroadcastStartFailed(), reason = ", "MediaOutputDialog");
            MediaOutputBaseDialog.this.mMainThreadHandler.postDelayed(new MediaOutputBaseDialog$1$$ExternalSyntheticLambda0(this, 4), 3000L);
        }

        public final void onBroadcastStarted(int i, int i2) {
            SuggestionsAdapter$$ExternalSyntheticOutline0.m(i, i2, "onBroadcastStarted(), reason = ", ", broadcastId = ", "MediaOutputDialog");
            MediaOutputBaseDialog.this.mMainThreadHandler.post(new MediaOutputBaseDialog$1$$ExternalSyntheticLambda0(this, 1));
        }

        public final void onBroadcastStopFailed(int i) {
            ListPopupWindow$$ExternalSyntheticOutline0.m(i, "onBroadcastStopFailed(), reason = ", "MediaOutputDialog");
            MediaOutputBaseDialog.this.mMainThreadHandler.post(new MediaOutputBaseDialog$1$$ExternalSyntheticLambda0(this, 6));
        }

        public final void onBroadcastStopped(int i, int i2) {
            SuggestionsAdapter$$ExternalSyntheticOutline0.m(i, i2, "onBroadcastStopped(), reason = ", ", broadcastId = ", "MediaOutputDialog");
            MediaOutputBaseDialog.this.mMainThreadHandler.post(new MediaOutputBaseDialog$1$$ExternalSyntheticLambda0(this, 2));
        }

        public final void onBroadcastUpdateFailed(int i, int i2) {
            SuggestionsAdapter$$ExternalSyntheticOutline0.m(i, i2, "onBroadcastUpdateFailed(), reason = ", ", broadcastId = ", "MediaOutputDialog");
            MediaOutputBaseDialog.this.mMainThreadHandler.post(new MediaOutputBaseDialog$1$$ExternalSyntheticLambda0(this, 3));
        }

        public final void onBroadcastUpdated(int i, int i2) {
            SuggestionsAdapter$$ExternalSyntheticOutline0.m(i, i2, "onBroadcastUpdated(), reason = ", ", broadcastId = ", "MediaOutputDialog");
            MediaOutputBaseDialog.this.mMainThreadHandler.post(new MediaOutputBaseDialog$1$$ExternalSyntheticLambda0(this, 0));
        }

        public final void onPlaybackStarted(int i, int i2) {
        }

        public final void onPlaybackStopped(int i, int i2) {
        }
    }
}
