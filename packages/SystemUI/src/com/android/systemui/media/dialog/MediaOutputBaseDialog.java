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
import android.view.ViewTreeObserver;
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
import com.android.internal.graphics.ColorUtils;
import com.android.settingslib.bluetooth.LocalBluetoothLeBroadcast;
import com.android.settingslib.media.flags.Flags;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastSender;
import com.android.systemui.media.dialog.MediaOutputController;
import com.android.systemui.media.nearby.NearbyMediaDevicesManager;
import com.android.systemui.monet.ColorScheme;
import com.android.systemui.monet.TonalPalette;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public abstract class MediaOutputBaseDialog extends SystemUIDialog implements MediaOutputController.Callback, Window.Callback {
    public MediaOutputAdapter mAdapter;
    public Button mAppButton;
    public ImageView mAppResourceIcon;
    public final AnonymousClass1 mBroadcastCallback;
    public ImageView mBroadcastIcon;
    public final BroadcastSender mBroadcastSender;
    public LinearLayout mCastAppLayout;
    public final Context mContext;
    public LinearLayout mDeviceListLayout;
    public final MediaOutputBaseDialog$$ExternalSyntheticLambda4 mDeviceListLayoutListener;
    public RecyclerView mDevicesRecyclerView;
    View mDialogView;
    public boolean mDismissing;
    public Button mDoneButton;
    public final Executor mExecutor;
    public ImageView mHeaderIcon;
    public TextView mHeaderSubtitle;
    public TextView mHeaderTitle;
    public final boolean mIncludePlaybackAndAppMetadata;
    public boolean mIsLeBroadcastCallbackRegistered;
    public final int mItemHeight;
    public final LayoutManagerWrapper mLayoutManager;
    public final int mListMaxHeight;
    public final int mListPaddingTop;
    public final Handler mMainThreadHandler;
    public LinearLayout mMediaMetadataSectionLayout;
    public final MediaOutputController mMediaOutputController;
    public boolean mShouldLaunchLeBroadcastDialog;
    public Button mStopButton;

    public final class LayoutManagerWrapper extends LinearLayoutManager {
        public LayoutManagerWrapper(Context context) {
            super(context);
        }

        @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
        public final void onLayoutCompleted(RecyclerView.State state) {
            super.onLayoutCompleted(state);
            MediaOutputController mediaOutputController = MediaOutputBaseDialog.this.mMediaOutputController;
            mediaOutputController.mIsRefreshing = false;
            if (mediaOutputController.mNeedRefresh) {
                mediaOutputController.buildMediaItems(mediaOutputController.mCachedMediaDevices);
                MediaOutputBaseDialog mediaOutputBaseDialog = (MediaOutputBaseDialog) mediaOutputController.mCallback;
                mediaOutputBaseDialog.mMainThreadHandler.post(new MediaOutputBaseDialog$$ExternalSyntheticLambda2(mediaOutputBaseDialog, 2));
                mediaOutputController.mNeedRefresh = false;
            }
        }
    }

    public MediaOutputBaseDialog(Context context, BroadcastSender broadcastSender, MediaOutputController mediaOutputController, boolean z) {
        super(context, R.style.Theme_SystemUI_Dialog_Media);
        this.mMainThreadHandler = new Handler(Looper.getMainLooper());
        this.mDeviceListLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.android.systemui.media.dialog.MediaOutputBaseDialog$$ExternalSyntheticLambda4
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public final void onGlobalLayout() {
                MediaOutputBaseDialog mediaOutputBaseDialog = MediaOutputBaseDialog.this;
                ViewGroup.LayoutParams layoutParams = mediaOutputBaseDialog.mDeviceListLayout.getLayoutParams();
                int min = Math.min((mediaOutputBaseDialog.mAdapter.getItemCount() * mediaOutputBaseDialog.mItemHeight) + mediaOutputBaseDialog.mListPaddingTop, mediaOutputBaseDialog.mListMaxHeight);
                if (min != layoutParams.height) {
                    layoutParams.height = min;
                    mediaOutputBaseDialog.mDeviceListLayout.setLayoutParams(layoutParams);
                }
            }
        };
        this.mBroadcastCallback = new AnonymousClass1();
        Context context2 = getContext();
        this.mContext = context2;
        this.mBroadcastSender = broadcastSender;
        this.mMediaOutputController = mediaOutputController;
        this.mLayoutManager = new LayoutManagerWrapper(context2);
        this.mListMaxHeight = context.getResources().getDimensionPixelSize(R.dimen.media_output_dialog_list_max_height);
        this.mItemHeight = context.getResources().getDimensionPixelSize(R.dimen.media_output_dialog_list_item_height);
        this.mListPaddingTop = context2.getResources().getDimensionPixelSize(R.dimen.media_output_dialog_list_padding_top);
        this.mExecutor = Executors.newSingleThreadExecutor();
        this.mIncludePlaybackAndAppMetadata = z;
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public final void dismiss() {
        this.mDismissing = true;
        super.dismiss();
    }

    public abstract IconCompat getAppSourceIcon();

    public abstract IconCompat getHeaderIcon();

    public abstract int getHeaderIconSize();

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
        this.mDevicesRecyclerView = (RecyclerView) this.mDialogView.requireViewById(R.id.list_result);
        this.mMediaMetadataSectionLayout = (LinearLayout) this.mDialogView.requireViewById(R.id.media_metadata_section);
        this.mDeviceListLayout = (LinearLayout) this.mDialogView.requireViewById(R.id.device_list);
        this.mDoneButton = (Button) this.mDialogView.requireViewById(R.id.done);
        this.mStopButton = (Button) this.mDialogView.requireViewById(R.id.stop);
        this.mAppButton = (Button) this.mDialogView.requireViewById(R.id.launch_app_button);
        this.mAppResourceIcon = (ImageView) this.mDialogView.requireViewById(R.id.app_source_icon);
        this.mCastAppLayout = (LinearLayout) this.mDialogView.requireViewById(R.id.cast_app_section);
        this.mBroadcastIcon = (ImageView) this.mDialogView.requireViewById(R.id.broadcast_icon);
        this.mDeviceListLayout.getViewTreeObserver().addOnGlobalLayoutListener(this.mDeviceListLayoutListener);
        LayoutManagerWrapper layoutManagerWrapper = this.mLayoutManager;
        layoutManagerWrapper.mAutoMeasure = true;
        this.mDevicesRecyclerView.setLayoutManager(layoutManagerWrapper);
        this.mDevicesRecyclerView.setAdapter(this.mAdapter);
        this.mDevicesRecyclerView.mHasFixedSize = false;
        this.mDoneButton.setOnClickListener(new MediaOutputBaseDialog$$ExternalSyntheticLambda0(this, 2));
        this.mStopButton.setOnClickListener(new MediaOutputBaseDialog$$ExternalSyntheticLambda0(this, 3));
        Button button = this.mAppButton;
        MediaOutputController mediaOutputController = this.mMediaOutputController;
        Objects.requireNonNull(mediaOutputController);
        int i = 4;
        button.setOnClickListener(new MediaOutputBaseDialog$$ExternalSyntheticLambda0(mediaOutputController, i));
        LinearLayout linearLayout = this.mMediaMetadataSectionLayout;
        MediaOutputController mediaOutputController2 = this.mMediaOutputController;
        Objects.requireNonNull(mediaOutputController2);
        linearLayout.setOnClickListener(new MediaOutputBaseDialog$$ExternalSyntheticLambda0(mediaOutputController2, i));
        this.mDismissing = false;
    }

    public void onStopButtonClick() {
        this.mMediaOutputController.releaseSession();
        dismiss();
    }

    public void refresh() {
        refresh(false);
    }

    @Override // com.android.systemui.statusbar.phone.SystemUIDialog
    public void start() {
        MediaController mediaController;
        MediaOutputController mediaOutputController = this.mMediaOutputController;
        synchronized (mediaOutputController.mMediaDevicesLock) {
            ((CopyOnWriteArrayList) mediaOutputController.mCachedMediaDevices).clear();
            ((CopyOnWriteArrayList) mediaOutputController.mMediaItemList).clear();
        }
        ((ConcurrentHashMap) mediaOutputController.mNearbyDeviceInfoMap).clear();
        NearbyMediaDevicesManager nearbyMediaDevicesManager = mediaOutputController.mNearbyMediaDevicesManager;
        if (nearbyMediaDevicesManager != null) {
            Iterator it = ((ArrayList) nearbyMediaDevicesManager.providers).iterator();
            while (it.hasNext()) {
                ((INearbyMediaDevicesProvider) it.next()).registerNearbyDevicesCallback(mediaOutputController);
            }
            ((ArrayList) nearbyMediaDevicesManager.activeCallbacks).add(mediaOutputController);
        }
        if (!TextUtils.isEmpty(mediaOutputController.mPackageName)) {
            if (mediaOutputController.mToken != null) {
                Flags.FEATURE_FLAGS.getClass();
                mediaController = new MediaController(mediaOutputController.mContext, mediaOutputController.mToken);
            } else {
                Iterator it2 = ((NotifPipeline) mediaOutputController.mNotifCollection).getAllNotifs().iterator();
                while (true) {
                    if (it2.hasNext()) {
                        NotificationEntry notificationEntry = (NotificationEntry) it2.next();
                        Notification notification2 = notificationEntry.mSbn.getNotification();
                        if (notification2.isMediaNotification() && TextUtils.equals(notificationEntry.mSbn.getPackageName(), mediaOutputController.mPackageName)) {
                            r2 = new MediaController(mediaOutputController.mContext, (MediaSession.Token) notification2.extras.getParcelable("android.mediaSession", MediaSession.Token.class));
                            break;
                        }
                    } else {
                        for (MediaController mediaController2 : mediaOutputController.mMediaSessionManager.getActiveSessionsForUser(null, ((UserTrackerImpl) mediaOutputController.mUserTracker).getUserHandle())) {
                            if (TextUtils.equals(mediaController2.getPackageName(), mediaOutputController.mPackageName)) {
                            }
                        }
                        mediaController = null;
                    }
                }
                mediaController = mediaController2;
            }
            mediaOutputController.mMediaController = mediaController;
            if (mediaController != null) {
                mediaController.unregisterCallback(mediaOutputController.mCb);
                if (mediaOutputController.mMediaController.getPlaybackState() != null) {
                    mediaOutputController.mCurrentState = mediaOutputController.mMediaController.getPlaybackState().getState();
                }
                mediaOutputController.mMediaController.registerCallback(mediaOutputController.mCb);
            }
        }
        if (mediaOutputController.mMediaController == null && MediaOutputController.DEBUG) {
            ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder("No media controller for "), mediaOutputController.mPackageName, "MediaOutputController");
        }
        mediaOutputController.mCallback = this;
        mediaOutputController.mLocalMediaManager.registerCallback(mediaOutputController);
        mediaOutputController.mLocalMediaManager.mInfoMediaManager.startScanOnRouter();
        isBroadcastSupported();
    }

    public final void startLeBroadcastDialog() {
        MediaOutputController mediaOutputController = this.mMediaOutputController;
        BroadcastSender broadcastSender = this.mBroadcastSender;
        mediaOutputController.getClass();
        new MediaOutputBroadcastDialog(mediaOutputController.mContext, true, broadcastSender, new MediaOutputController(mediaOutputController.mContext, mediaOutputController.mPackageName, mediaOutputController.mUserHandle, mediaOutputController.mToken, mediaOutputController.mMediaSessionManager, mediaOutputController.mLocalBluetoothManager, mediaOutputController.mActivityStarter, mediaOutputController.mNotifCollection, mediaOutputController.mDialogTransitionAnimator, mediaOutputController.mNearbyMediaDevicesManager, mediaOutputController.mAudioManager, mediaOutputController.mPowerExemptionManager, mediaOutputController.mKeyGuardManager, mediaOutputController.mFeatureFlags, mediaOutputController.mUserTracker)).show();
        refresh();
    }

    @Override // com.android.systemui.statusbar.phone.SystemUIDialog
    public void stop() {
        LocalBluetoothLeBroadcast localBluetoothLeBroadcast = this.mMediaOutputController.mLocalBluetoothManager.mProfileManager.mLeAudioBroadcast;
        if (localBluetoothLeBroadcast != null && this.mIsLeBroadcastCallbackRegistered) {
            AnonymousClass1 anonymousClass1 = this.mBroadcastCallback;
            if (localBluetoothLeBroadcast == null) {
                Log.d("MediaOutputController", "The broadcast profile is null");
            } else {
                Log.d("MediaOutputController", "Unregister LE broadcast callback");
                localBluetoothLeBroadcast.unregisterServiceCallBack(anonymousClass1);
            }
            this.mIsLeBroadcastCallbackRegistered = false;
        }
        MediaOutputController mediaOutputController = this.mMediaOutputController;
        MediaController mediaController = mediaOutputController.mMediaController;
        if (mediaController != null) {
            mediaController.unregisterCallback(mediaOutputController.mCb);
        }
        mediaOutputController.mLocalMediaManager.unregisterCallback(mediaOutputController);
        mediaOutputController.mLocalMediaManager.mInfoMediaManager.stopScanOnRouter();
        synchronized (mediaOutputController.mMediaDevicesLock) {
            ((CopyOnWriteArrayList) mediaOutputController.mCachedMediaDevices).clear();
            ((CopyOnWriteArrayList) mediaOutputController.mMediaItemList).clear();
        }
        NearbyMediaDevicesManager nearbyMediaDevicesManager = mediaOutputController.mNearbyMediaDevicesManager;
        if (nearbyMediaDevicesManager != null) {
            ((ArrayList) nearbyMediaDevicesManager.activeCallbacks).remove(mediaOutputController);
            Iterator it = ((ArrayList) nearbyMediaDevicesManager.providers).iterator();
            while (it.hasNext()) {
                ((INearbyMediaDevicesProvider) it.next()).unregisterNearbyDevicesCallback(mediaOutputController);
            }
        }
        ((ConcurrentHashMap) mediaOutputController.mNearbyDeviceInfoMap).clear();
    }

    public final void updateButtonBackgroundColorFilter() {
        PorterDuffColorFilter porterDuffColorFilter = new PorterDuffColorFilter(this.mMediaOutputController.mColorButtonBackground, PorterDuff.Mode.SRC_IN);
        this.mDoneButton.getBackground().setColorFilter(porterDuffColorFilter);
        this.mStopButton.getBackground().setColorFilter(porterDuffColorFilter);
        this.mDoneButton.setTextColor(this.mMediaOutputController.mColorPositiveButtonText);
    }

    public final void updateDialogBackgroundColor() {
        this.mDialogView.getBackground().setTint(this.mMediaOutputController.mColorDialogBackground);
        this.mDeviceListLayout.setBackgroundColor(this.mMediaOutputController.mColorDialogBackground);
    }

    public final void refresh(boolean z) {
        boolean z2;
        if (this.mDismissing) {
            return;
        }
        MediaOutputController mediaOutputController = this.mMediaOutputController;
        if (mediaOutputController.mIsRefreshing) {
            return;
        }
        mediaOutputController.mIsRefreshing = true;
        IconCompat headerIcon = getHeaderIcon();
        IconCompat appSourceIcon = getAppSourceIcon();
        LinearLayout linearLayout = this.mCastAppLayout;
        this.mMediaOutputController.getClass();
        linearLayout.setVisibility(8);
        Drawable drawable = null;
        if (headerIcon != null) {
            Icon icon$1 = headerIcon.toIcon$1();
            if (icon$1.getType() == 1 || icon$1.getType() == 5) {
                boolean z3 = (this.mContext.getResources().getConfiguration().uiMode & 48) == 32;
                WallpaperColors fromBitmap = WallpaperColors.fromBitmap(icon$1.getBitmap());
                z2 = !fromBitmap.equals(null);
                if (z2) {
                    MediaOutputController mediaOutputController2 = this.mAdapter.mController;
                    mediaOutputController2.getClass();
                    ColorScheme colorScheme = new ColorScheme(fromBitmap, z3);
                    TonalPalette tonalPalette = colorScheme.mNeutral1;
                    TonalPalette tonalPalette2 = colorScheme.mAccent2;
                    TonalPalette tonalPalette3 = colorScheme.mAccent1;
                    if (z3) {
                        mediaOutputController2.mColorItemContent = tonalPalette3.getS100();
                        mediaOutputController2.mColorSeekbarProgress = ((Integer) tonalPalette2.allShades.get(8)).intValue();
                        mediaOutputController2.mColorButtonBackground = ((Integer) tonalPalette3.allShades.get(5)).intValue();
                        mediaOutputController2.mColorItemBackground = colorScheme.mNeutral2.getS800();
                        mediaOutputController2.mColorConnectedItemBackground = tonalPalette2.getS800();
                        mediaOutputController2.mColorPositiveButtonText = tonalPalette2.getS800();
                        mediaOutputController2.mColorDialogBackground = ((Integer) tonalPalette.allShades.get(11)).intValue();
                    } else {
                        mediaOutputController2.mColorItemContent = tonalPalette3.getS800();
                        mediaOutputController2.mColorSeekbarProgress = ((Integer) tonalPalette3.allShades.get(5)).intValue();
                        mediaOutputController2.mColorButtonBackground = ((Integer) tonalPalette3.allShades.get(8)).intValue();
                        mediaOutputController2.mColorItemBackground = tonalPalette2.getS50();
                        mediaOutputController2.mColorConnectedItemBackground = tonalPalette3.getS100();
                        mediaOutputController2.mColorPositiveButtonText = tonalPalette.getS50();
                        mediaOutputController2.mColorDialogBackground = ColorUtils.setAlphaComponent(colorScheme.mIsDark ? tonalPalette.getS700() : ((Integer) tonalPalette.allShades.get(1)).intValue(), 255);
                    }
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
            this.mAppResourceIcon.setColorFilter(this.mMediaOutputController.mColorItemContent);
            this.mAppResourceIcon.setImageIcon(icon$12);
        } else {
            MediaOutputController mediaOutputController3 = this.mMediaOutputController;
            if (!TextUtils.isEmpty(mediaOutputController3.mPackageName)) {
                try {
                    Log.d("MediaOutputController", "try to get app icon");
                    drawable = mediaOutputController3.mContext.getPackageManager().getApplicationIcon(mediaOutputController3.mPackageName);
                } catch (PackageManager.NameNotFoundException unused) {
                    Log.d("MediaOutputController", "icon not found");
                }
            }
            if (drawable != null) {
                this.mAppResourceIcon.setImageDrawable(drawable);
            } else {
                this.mAppResourceIcon.setVisibility(8);
            }
        }
        if (this.mHeaderIcon.getVisibility() == 0) {
            int headerIconSize = getHeaderIconSize();
            this.mHeaderIcon.setLayoutParams(new LinearLayout.LayoutParams(this.mContext.getResources().getDimensionPixelSize(R.dimen.media_output_dialog_header_icon_padding) + headerIconSize, headerIconSize));
        }
        this.mAppButton.setText(this.mMediaOutputController.getAppSourceName());
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
        this.mStopButton.setOnClickListener(new MediaOutputBaseDialog$$ExternalSyntheticLambda0(this, 0));
        ImageView imageView = this.mBroadcastIcon;
        getBroadcastIconVisibility();
        imageView.setVisibility(8);
        this.mBroadcastIcon.setOnClickListener(new MediaOutputBaseDialog$$ExternalSyntheticLambda0(this, 1));
        MediaOutputAdapter mediaOutputAdapter = this.mAdapter;
        if (mediaOutputAdapter.mIsDragging) {
            MediaOutputController mediaOutputController4 = this.mMediaOutputController;
            mediaOutputController4.mIsRefreshing = false;
            if (mediaOutputController4.mNeedRefresh) {
                mediaOutputController4.buildMediaItems(mediaOutputController4.mCachedMediaDevices);
                MediaOutputBaseDialog mediaOutputBaseDialog = (MediaOutputBaseDialog) mediaOutputController4.mCallback;
                mediaOutputBaseDialog.mMainThreadHandler.post(new MediaOutputBaseDialog$$ExternalSyntheticLambda2(mediaOutputBaseDialog, 2));
                mediaOutputController4.mNeedRefresh = false;
                return;
            }
            return;
        }
        int i = mediaOutputAdapter.mCurrentActivePosition;
        if (!z2 && !z && i >= 0 && i < mediaOutputAdapter.getItemCount()) {
            this.mAdapter.notifyItemChanged(i);
            return;
        }
        MediaOutputAdapter mediaOutputAdapter2 = this.mAdapter;
        ((CopyOnWriteArrayList) mediaOutputAdapter2.mMediaItemList).clear();
        ((CopyOnWriteArrayList) mediaOutputAdapter2.mMediaItemList).addAll(mediaOutputAdapter2.mController.mMediaItemList);
        mediaOutputAdapter2.notifyDataSetChanged();
    }

    public void getBroadcastIconVisibility() {
    }

    public void isBroadcastSupported() {
    }

    public void onBroadcastIconClick() {
    }

    /* renamed from: com.android.systemui.media.dialog.MediaOutputBaseDialog$1, reason: invalid class name */
    public final class AnonymousClass1 implements BluetoothLeBroadcast.Callback {
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
