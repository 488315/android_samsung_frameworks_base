package com.android.systemui.media.dialog;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.media.RouteListingPreference;
import android.media.RoutingSessionInfo;
import android.os.PowerExemptionManager;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView;
import com.android.settingslib.media.InfoMediaManager;
import com.android.settingslib.media.LocalMediaManager;
import com.android.settingslib.media.MediaDevice;
import com.android.settingslib.utils.ThreadUtils;
import com.android.systemui.R;
import com.android.systemui.media.dialog.MediaOutputAdapter;
import com.android.systemui.media.dialog.MediaOutputBaseAdapter;
import com.android.systemui.shared.system.SysUiStatsLog;
import com.android.systemui.util.wakelock.WakeLock;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class MediaOutputAdapter extends MediaOutputBaseAdapter {
    public static final boolean DEBUG = Log.isLoggable("MediaOutputAdapter", 3);
    public final List mMediaItemList;

    public final class MediaDeviceViewHolder extends MediaOutputBaseAdapter.MediaDeviceBaseViewHolder {
        public MediaDeviceViewHolder(View view) {
            super(view);
        }

        public final void onItemClick(MediaDevice mediaDevice) {
            MediaDevice currentConnectedDevice = MediaOutputAdapter.this.mController.mLocalMediaManager.getCurrentConnectedDevice();
            if (currentConnectedDevice == null || !currentConnectedDevice.isHostForOngoingSession()) {
                transferOutput(mediaDevice);
            } else {
                showCustomEndSessionDialog(mediaDevice);
            }
        }

        public final void setUpContentDescriptionForView(View view, MediaDevice mediaDevice) {
            view.setContentDescription(MediaOutputAdapter.this.mContext.getString(mediaDevice.getDeviceType() == 5 ? R.string.accessibility_bluetooth_name : R.string.accessibility_cast_name, mediaDevice.getName()));
            view.setAccessibilityDelegate(new View.AccessibilityDelegate(this) { // from class: com.android.systemui.media.dialog.MediaOutputAdapter.MediaDeviceViewHolder.1
                @Override // android.view.View.AccessibilityDelegate
                public final void onInitializeAccessibilityNodeInfo(View view2, AccessibilityNodeInfo accessibilityNodeInfo) {
                    super.onInitializeAccessibilityNodeInfo(view2, accessibilityNodeInfo);
                    view2.setOnClickListener(null);
                }
            });
        }

        public void showCustomEndSessionDialog(final MediaDevice mediaDevice) {
            MediaOutputAdapter mediaOutputAdapter = MediaOutputAdapter.this;
            Context context = mediaOutputAdapter.mContext;
            Runnable runnable = new Runnable() { // from class: com.android.systemui.media.dialog.MediaOutputAdapter$MediaDeviceViewHolder$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    MediaOutputAdapter.MediaDeviceViewHolder.this.transferOutput(mediaDevice);
                }
            };
            MediaOutputController mediaOutputController = mediaOutputAdapter.mController;
            new MediaSessionReleaseDialog(context, runnable, mediaOutputController.mColorButtonBackground, mediaOutputController.mColorItemContent).show();
        }

        public final void transferOutput(final MediaDevice mediaDevice) {
            String str;
            MediaOutputAdapter mediaOutputAdapter = MediaOutputAdapter.this;
            if (mediaOutputAdapter.mController.isAnyDeviceTransferring()) {
                return;
            }
            if (mediaOutputAdapter.isCurrentlyConnected(mediaDevice)) {
                Log.d("MediaOutputAdapter", "This device is already connected! : " + mediaDevice.getName());
                return;
            }
            MediaOutputController mediaOutputController = mediaOutputAdapter.mController;
            PowerExemptionManager powerExemptionManager = mediaOutputController.mPowerExemptionManager;
            if (powerExemptionManager == null || (str = mediaOutputController.mPackageName) == null) {
                Log.w("MediaOutputController", "powerExemptionManager or package name is null");
            } else {
                powerExemptionManager.addToTemporaryAllowList(str, 325, "mediaoutput:remote_transfer", WakeLock.DEFAULT_MAX_TIMEOUT);
            }
            mediaOutputAdapter.mCurrentActivePosition = -1;
            final MediaOutputController mediaOutputController2 = mediaOutputAdapter.mController;
            MediaOutputMetricLogger mediaOutputMetricLogger = mediaOutputController2.mMetricLogger;
            mediaOutputMetricLogger.mSourceDevice = mediaOutputController2.mLocalMediaManager.getCurrentConnectedDevice();
            mediaOutputMetricLogger.mTargetDevice = mediaDevice;
            if (MediaOutputMetricLogger.DEBUG) {
                Log.d("MediaOutputMetricLogger", "updateOutputEndPoints - source:" + mediaOutputMetricLogger.mSourceDevice.toString() + " target:" + mediaOutputMetricLogger.mTargetDevice.toString());
            }
            ThreadUtils.postOnBackgroundThread(new Runnable() { // from class: com.android.systemui.media.dialog.MediaOutputController$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    MediaOutputController mediaOutputController3 = MediaOutputController.this;
                    mediaOutputController3.mLocalMediaManager.connectDevice(mediaDevice);
                }
            });
            mediaDevice.mState = 1;
            mediaOutputAdapter.notifyDataSetChanged();
        }

        public final boolean updateClickActionBasedOnSelectionBehavior(MediaDevice mediaDevice) {
            MediaOutputController mediaOutputController = MediaOutputAdapter.this.mController;
            MediaOutputAdapter$Api34Impl$$ExternalSyntheticLambda0 mediaOutputAdapter$Api34Impl$$ExternalSyntheticLambda0 = new MediaOutputAdapter$Api34Impl$$ExternalSyntheticLambda0(this, mediaDevice, 1);
            int selectionBehavior = mediaDevice.getSelectionBehavior();
            if (selectionBehavior == 0) {
                mediaOutputAdapter$Api34Impl$$ExternalSyntheticLambda0 = null;
            } else if (selectionBehavior == 2) {
                mediaOutputAdapter$Api34Impl$$ExternalSyntheticLambda0 = new MediaOutputAdapter$Api34Impl$$ExternalSyntheticLambda0(mediaOutputController, mediaDevice, 0);
            }
            this.mContainerLayout.setOnClickListener(mediaOutputAdapter$Api34Impl$$ExternalSyntheticLambda0);
            this.mIconAreaLayout.setOnClickListener(mediaOutputAdapter$Api34Impl$$ExternalSyntheticLambda0);
            return mediaOutputAdapter$Api34Impl$$ExternalSyntheticLambda0 != null;
        }

        public final void updateDeviceStatusIcon(Drawable drawable) {
            this.mStatusIcon.setImageDrawable(drawable);
            this.mStatusIcon.setImageTintList(ColorStateList.valueOf(MediaOutputAdapter.this.mController.mColorItemContent));
            if (drawable instanceof AnimatedVectorDrawable) {
                ((AnimatedVectorDrawable) drawable).start();
            }
        }

        public final void updateEndClickArea(MediaDevice mediaDevice, boolean z) {
            this.mEndTouchArea.setOnClickListener(null);
            this.mEndTouchArea.setOnClickListener(z ? new MediaOutputAdapter$MediaDeviceViewHolder$$ExternalSyntheticLambda3(this, 1) : null);
            this.mEndTouchArea.setImportantForAccessibility(1);
            this.mEndTouchArea.setBackgroundTintList(ColorStateList.valueOf(MediaOutputAdapter.this.mController.mColorItemBackground));
            setUpContentDescriptionForView(this.mEndTouchArea, mediaDevice);
        }

        public final void updateEndClickAreaAsSessionEditing(MediaDevice mediaDevice, int i) {
            this.mEndClickIcon.setOnClickListener(null);
            this.mEndTouchArea.setOnClickListener(null);
            MediaOutputAdapter mediaOutputAdapter = MediaOutputAdapter.this;
            this.mEndTouchArea.setBackgroundTintList(ColorStateList.valueOf(mediaOutputAdapter.mController.mColorSeekbarProgress));
            this.mEndClickIcon.setImageTintList(ColorStateList.valueOf(mediaOutputAdapter.mController.mColorItemContent));
            this.mEndClickIcon.setOnClickListener(new MediaOutputAdapter$Api34Impl$$ExternalSyntheticLambda0(this, mediaDevice, 5));
            this.mEndTouchArea.setOnClickListener(new MediaOutputAdapter$MediaDeviceViewHolder$$ExternalSyntheticLambda3(this, 2));
            Drawable drawable = mediaOutputAdapter.mContext.getDrawable(i);
            this.mEndClickIcon.setImageDrawable(drawable);
            if (drawable instanceof AnimatedVectorDrawable) {
                ((AnimatedVectorDrawable) drawable).start();
            }
        }

        public final void updateGroupableCheckBox(final boolean z, boolean z2, final MediaDevice mediaDevice) {
            this.mCheckBox.setOnCheckedChangeListener(null);
            this.mCheckBox.setChecked(z);
            this.mCheckBox.setOnCheckedChangeListener(z2 ? new CompoundButton.OnCheckedChangeListener() { // from class: com.android.systemui.media.dialog.MediaOutputAdapter$MediaDeviceViewHolder$$ExternalSyntheticLambda7
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z3) {
                    MediaOutputAdapter.MediaDeviceViewHolder mediaDeviceViewHolder = MediaOutputAdapter.MediaDeviceViewHolder.this;
                    boolean z4 = z;
                    MediaDevice mediaDevice2 = mediaDevice;
                    boolean z5 = !z4;
                    mediaDeviceViewHolder.disableSeekBar();
                    MediaOutputAdapter mediaOutputAdapter = MediaOutputAdapter.this;
                    if (!z5 || !MediaOutputBaseAdapter.isDeviceIncluded(mediaOutputAdapter.mController.getSelectableMediaDevice(), mediaDevice2)) {
                        if (z5 || !MediaOutputBaseAdapter.isDeviceIncluded(mediaOutputAdapter.mController.getDeselectableMediaDevice(), mediaDevice2)) {
                            return;
                        }
                        LocalMediaManager localMediaManager = mediaOutputAdapter.mController.mLocalMediaManager;
                        localMediaManager.getClass();
                        mediaDevice2.mState = 5;
                        InfoMediaManager infoMediaManager = localMediaManager.mInfoMediaManager;
                        RoutingSessionInfo activeRoutingSession = infoMediaManager.getActiveRoutingSession();
                        if (activeRoutingSession.getSelectedRoutes().contains(mediaDevice2.mRouteInfo.getId())) {
                            infoMediaManager.deselectRoute(mediaDevice2.mRouteInfo, activeRoutingSession);
                            return;
                        }
                        Log.w("InfoMediaManager", "removeDeviceFromMedia() Ignoring deselecting a non-deselectable device : " + mediaDevice2.getName());
                        return;
                    }
                    MediaOutputController mediaOutputController = mediaOutputAdapter.mController;
                    MediaOutputMetricLogger mediaOutputMetricLogger = mediaOutputController.mMetricLogger;
                    if (MediaOutputMetricLogger.DEBUG) {
                        mediaOutputMetricLogger.getClass();
                        Log.d("MediaOutputMetricLogger", "logInteraction - Expansion");
                    }
                    mediaOutputMetricLogger.getClass();
                    SysUiStatsLog.write(mediaOutputMetricLogger.getLoggingPackageName(), 0, MediaOutputMetricLogger.getInteractionDeviceType(mediaDevice2), mediaDevice2.isSuggestedDevice());
                    LocalMediaManager localMediaManager2 = mediaOutputController.mLocalMediaManager;
                    localMediaManager2.getClass();
                    mediaDevice2.mState = 5;
                    InfoMediaManager infoMediaManager2 = localMediaManager2.mInfoMediaManager;
                    RoutingSessionInfo activeRoutingSession2 = infoMediaManager2.getActiveRoutingSession();
                    if (activeRoutingSession2.getSelectableRoutes().contains(mediaDevice2.mRouteInfo.getId())) {
                        infoMediaManager2.selectRoute(mediaDevice2.mRouteInfo, activeRoutingSession2);
                        return;
                    }
                    Log.w("InfoMediaManager", "addDeviceToPlayMedia() Ignoring selecting a non-selectable device : " + mediaDevice2.getName());
                }
            } : null);
            this.mCheckBox.setEnabled(z2);
            CheckBox checkBox = this.mCheckBox;
            int i = MediaOutputAdapter.this.mController.mColorItemContent;
            checkBox.setButtonTintList(new ColorStateList(new int[][]{new int[]{android.R.attr.state_checked}, new int[0]}, new int[]{i, i}));
        }
    }

    public final class MediaGroupDividerViewHolder extends RecyclerView.ViewHolder {
        public final TextView mTitleText;

        public MediaGroupDividerViewHolder(View view) {
            super(view);
            this.mTitleText = (TextView) view.requireViewById(R.id.title);
        }
    }

    public MediaOutputAdapter(MediaOutputController mediaOutputController) {
        super(mediaOutputController);
        this.mMediaItemList = new CopyOnWriteArrayList();
        setHasStableIds(true);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        return ((CopyOnWriteArrayList) this.mMediaItemList).size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final long getItemId(int i) {
        if (i >= ((CopyOnWriteArrayList) this.mMediaItemList).size()) {
            ListPopupWindow$$ExternalSyntheticOutline0.m(i, "Incorrect position for item id: ", "MediaOutputAdapter");
            return i;
        }
        return ((MediaItem) ((CopyOnWriteArrayList) this.mMediaItemList).get(i)).mMediaDeviceOptional.isPresent() ? ((MediaDevice) r1.mMediaDeviceOptional.get()).getId().hashCode() : i;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemViewType(int i) {
        if (i < ((CopyOnWriteArrayList) this.mMediaItemList).size()) {
            return ((MediaItem) ((CopyOnWriteArrayList) this.mMediaItemList).get(i)).mMediaItemType;
        }
        ListPopupWindow$$ExternalSyntheticOutline0.m(i, "Incorrect position for item type: ", "MediaOutputAdapter");
        return 1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        Drawable drawable;
        Drawable drawable2;
        if (i >= ((CopyOnWriteArrayList) this.mMediaItemList).size()) {
            if (DEBUG) {
                StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Incorrect position: ", " list size: ");
                m.append(((CopyOnWriteArrayList) this.mMediaItemList).size());
                Log.d("MediaOutputAdapter", m.toString());
                return;
            }
            return;
        }
        MediaItem mediaItem = (MediaItem) ((CopyOnWriteArrayList) this.mMediaItemList).get(i);
        int i2 = mediaItem.mMediaItemType;
        if (i2 != 0) {
            if (i2 == 1) {
                MediaGroupDividerViewHolder mediaGroupDividerViewHolder = (MediaGroupDividerViewHolder) viewHolder;
                mediaGroupDividerViewHolder.mTitleText.setTextColor(MediaOutputAdapter.this.mController.mColorItemContent);
                mediaGroupDividerViewHolder.mTitleText.setText(mediaItem.mTitle);
                return;
            }
            if (i2 != 2) {
                ListPopupWindow$$ExternalSyntheticOutline0.m(i, "Incorrect position: ", "MediaOutputAdapter");
                return;
            }
            MediaDeviceViewHolder mediaDeviceViewHolder = (MediaDeviceViewHolder) viewHolder;
            TextView textView = mediaDeviceViewHolder.mTitleText;
            MediaOutputAdapter mediaOutputAdapter = MediaOutputAdapter.this;
            textView.setTextColor(mediaOutputAdapter.mController.mColorItemContent);
            mediaDeviceViewHolder.mCheckBox.setVisibility(8);
            mediaDeviceViewHolder.setSingleLineLayout(mediaOutputAdapter.mContext.getText(R.string.media_output_dialog_pairing_new), false, false, false, false);
            mediaDeviceViewHolder.mTitleIcon.setImageDrawable(mediaOutputAdapter.mContext.getDrawable(R.drawable.ic_add));
            ImageView imageView = mediaDeviceViewHolder.mTitleIcon;
            MediaOutputController mediaOutputController = mediaOutputAdapter.mController;
            imageView.setImageTintList(ColorStateList.valueOf(mediaOutputController.mColorItemContent));
            mediaDeviceViewHolder.mIconAreaLayout.setBackgroundTintList(ColorStateList.valueOf(mediaOutputController.mColorItemBackground));
            mediaDeviceViewHolder.mContainerLayout.setOnClickListener(new MediaOutputAdapter$MediaDeviceViewHolder$$ExternalSyntheticLambda3(mediaOutputController, 3));
            return;
        }
        MediaDeviceViewHolder mediaDeviceViewHolder2 = (MediaDeviceViewHolder) viewHolder;
        MediaDevice mediaDevice = (MediaDevice) mediaItem.mMediaDeviceOptional.get();
        mediaDeviceViewHolder2.mDeviceId = mediaDevice.getId();
        mediaDeviceViewHolder2.mCheckBox.setVisibility(8);
        mediaDeviceViewHolder2.mStatusIcon.setVisibility(8);
        mediaDeviceViewHolder2.mEndTouchArea.setVisibility(8);
        mediaDeviceViewHolder2.mEndTouchArea.setImportantForAccessibility(2);
        mediaDeviceViewHolder2.mContainerLayout.setOnClickListener(null);
        mediaDeviceViewHolder2.mContainerLayout.setContentDescription(null);
        TextView textView2 = mediaDeviceViewHolder2.mTitleText;
        MediaOutputBaseAdapter mediaOutputBaseAdapter = MediaOutputBaseAdapter.this;
        textView2.setTextColor(mediaOutputBaseAdapter.mController.mColorItemContent);
        TextView textView3 = mediaDeviceViewHolder2.mSubTitleText;
        MediaOutputController mediaOutputController2 = mediaOutputBaseAdapter.mController;
        textView3.setTextColor(mediaOutputController2.mColorItemContent);
        mediaDeviceViewHolder2.mSubTitleText.setSelected(true);
        mediaDeviceViewHolder2.mTwoLineTitleText.setTextColor(mediaOutputController2.mColorItemContent);
        mediaDeviceViewHolder2.mVolumeValueText.setTextColor(mediaOutputController2.mColorItemContent);
        mediaDeviceViewHolder2.mSeekBar.setProgressTintList(ColorStateList.valueOf(mediaOutputController2.mColorSeekbarProgress));
        MediaOutputAdapter mediaOutputAdapter2 = MediaOutputAdapter.this;
        boolean z = mediaOutputAdapter2.mController.mAudioManager.getMutingExpectedDevice() != null;
        boolean isCurrentlyConnected = mediaOutputAdapter2.isCurrentlyConnected(mediaDevice);
        boolean z2 = mediaDeviceViewHolder2.mSeekBar.getVisibility() == 8;
        if (mediaOutputAdapter2.mCurrentActivePosition == i) {
            mediaOutputAdapter2.mCurrentActivePosition = -1;
        }
        mediaDeviceViewHolder2.mStatusIcon.setVisibility(8);
        ViewGroup viewGroup = mediaDeviceViewHolder2.mContainerLayout;
        viewGroup.setFocusable(true);
        viewGroup.setImportantForAccessibility(0);
        MediaOutputController mediaOutputController3 = mediaOutputAdapter2.mController;
        if (mediaOutputController3.isAnyDeviceTransferring()) {
            if (mediaDevice.mState != 1 || mediaOutputController3.hasAdjustVolumeUserRestriction()) {
                mediaDeviceViewHolder2.setUpDeviceIcon(mediaDevice);
                mediaDeviceViewHolder2.setSingleLineLayout(mediaDevice.getName(), false, false, false, false);
                return;
            } else {
                mediaDeviceViewHolder2.setUpDeviceIcon(mediaDevice);
                mediaDeviceViewHolder2.mProgressBar.getIndeterminateDrawable().setTintList(ColorStateList.valueOf(mediaOutputAdapter2.mController.mColorItemContent));
                mediaDeviceViewHolder2.setSingleLineLayout(mediaDevice.getName(), false, true, false, false);
                return;
            }
        }
        if (mediaDevice.isMutingExpectedDevice() && !mediaOutputController3.isCurrentConnectedDeviceRemote()) {
            mediaDeviceViewHolder2.updateTitleIcon(R.drawable.media_output_icon_volume, mediaOutputController3.mColorItemContent);
            mediaOutputAdapter2.mCurrentActivePosition = i;
            MediaOutputAdapter$Api34Impl$$ExternalSyntheticLambda0 mediaOutputAdapter$Api34Impl$$ExternalSyntheticLambda0 = new MediaOutputAdapter$Api34Impl$$ExternalSyntheticLambda0(mediaDeviceViewHolder2, mediaDevice, 2);
            mediaDeviceViewHolder2.mContainerLayout.setOnClickListener(mediaOutputAdapter$Api34Impl$$ExternalSyntheticLambda0);
            mediaDeviceViewHolder2.mIconAreaLayout.setOnClickListener(mediaOutputAdapter$Api34Impl$$ExternalSyntheticLambda0);
            mediaDeviceViewHolder2.setSingleLineLayout(mediaDevice.getName(), false, false, false, false);
            mediaDeviceViewHolder2.disableSeekBar();
            mediaDeviceViewHolder2.updateTitleIcon(R.drawable.media_output_icon_volume, mediaOutputController2.mColorItemContent);
            mediaDeviceViewHolder2.mItemLayout.setBackground(mediaOutputBaseAdapter.mContext.getDrawable(R.drawable.media_output_item_background_active).mutate());
            mediaDeviceViewHolder2.mItemLayout.setBackgroundTintList(ColorStateList.valueOf(mediaOutputController2.mColorConnectedItemBackground));
            mediaDeviceViewHolder2.mIconAreaLayout.setBackgroundTintList(ColorStateList.valueOf(mediaOutputController2.mColorConnectedItemBackground));
            return;
        }
        RouteListingPreference.Item item = mediaDevice.mItem;
        int i3 = R.drawable.ic_sound_bars_anim;
        if (item != null && item.getSubText() != 0) {
            boolean z3 = mediaDevice.hasOngoingSession() && (isCurrentlyConnected || MediaOutputBaseAdapter.isDeviceIncluded(mediaOutputController3.getSelectedMediaDevice(), mediaDevice));
            boolean z4 = mediaDevice.isHostForOngoingSession() && z3;
            if (z3) {
                mediaOutputAdapter2.mCurrentActivePosition = i;
                mediaDeviceViewHolder2.updateTitleIcon(R.drawable.media_output_icon_volume, mediaOutputController3.mColorItemContent);
                mediaDeviceViewHolder2.mSubTitleText.setText(mediaDevice.getSubtextString());
                mediaDeviceViewHolder2.mSubTitleText.setAlpha(1.0f);
                mediaDeviceViewHolder2.mTitleIcon.setAlpha(1.0f);
                mediaDeviceViewHolder2.mTwoLineTitleText.setAlpha(1.0f);
                mediaDeviceViewHolder2.mStatusIcon.setAlpha(1.0f);
                if (z4) {
                    i3 = R.drawable.media_output_status_edit_session;
                }
                mediaDeviceViewHolder2.updateEndClickAreaAsSessionEditing(mediaDevice, i3);
                mediaDeviceViewHolder2.setTwoLineLayout(mediaDevice, true, true, false, true);
                mediaDeviceViewHolder2.initSeekbar(mediaDevice, z2);
                return;
            }
            if (isCurrentlyConnected) {
                mediaOutputAdapter2.mCurrentActivePosition = i;
                mediaDeviceViewHolder2.updateTitleIcon(R.drawable.media_output_icon_volume, mediaOutputController3.mColorItemContent);
                mediaDeviceViewHolder2.initSeekbar(mediaDevice, z2);
            } else {
                mediaDeviceViewHolder2.setUpDeviceIcon(mediaDevice);
            }
            mediaDeviceViewHolder2.mSubTitleText.setText(mediaDevice.getSubtextString());
            if (mediaDevice.hasOngoingSession()) {
                drawable2 = mediaOutputAdapter2.mContext.getDrawable(R.drawable.ic_sound_bars_anim);
            } else {
                Context context = mediaOutputAdapter2.mContext;
                int selectionBehavior = mediaDevice.getSelectionBehavior();
                drawable2 = selectionBehavior != 0 ? selectionBehavior != 2 ? null : context.getDrawable(R.drawable.media_output_status_help) : context.getDrawable(R.drawable.media_output_status_failed);
            }
            if (drawable2 != null) {
                mediaDeviceViewHolder2.updateDeviceStatusIcon(drawable2);
            }
            float f = mediaDeviceViewHolder2.updateClickActionBasedOnSelectionBehavior(mediaDevice) ? 1.0f : 0.5f;
            mediaDeviceViewHolder2.mSubTitleText.setAlpha(f);
            mediaDeviceViewHolder2.mTitleIcon.setAlpha(f);
            mediaDeviceViewHolder2.mTwoLineTitleText.setAlpha(f);
            mediaDeviceViewHolder2.mStatusIcon.setAlpha(f);
            mediaDeviceViewHolder2.setTwoLineLayout(mediaDevice, isCurrentlyConnected, isCurrentlyConnected, drawable2 != null, false);
            return;
        }
        int i4 = mediaDevice.mState;
        if (i4 == 3) {
            mediaDeviceViewHolder2.setUpDeviceIcon(mediaDevice);
            mediaDeviceViewHolder2.mStatusIcon.setImageDrawable(mediaOutputAdapter2.mContext.getDrawable(R.drawable.media_output_status_failed));
            mediaDeviceViewHolder2.mStatusIcon.setImageTintList(ColorStateList.valueOf(mediaOutputController3.mColorItemContent));
            mediaDeviceViewHolder2.mSubTitleText.setText(R.string.media_output_dialog_connect_failed);
            MediaOutputAdapter$Api34Impl$$ExternalSyntheticLambda0 mediaOutputAdapter$Api34Impl$$ExternalSyntheticLambda02 = new MediaOutputAdapter$Api34Impl$$ExternalSyntheticLambda0(mediaDeviceViewHolder2, mediaDevice, 3);
            mediaDeviceViewHolder2.mContainerLayout.setOnClickListener(mediaOutputAdapter$Api34Impl$$ExternalSyntheticLambda02);
            mediaDeviceViewHolder2.mIconAreaLayout.setOnClickListener(mediaOutputAdapter$Api34Impl$$ExternalSyntheticLambda02);
            mediaDeviceViewHolder2.setTwoLineLayout(mediaDevice, false, false, true, false);
            return;
        }
        if (i4 == 5) {
            mediaDeviceViewHolder2.setUpDeviceIcon(mediaDevice);
            mediaDeviceViewHolder2.mProgressBar.getIndeterminateDrawable().setTintList(ColorStateList.valueOf(mediaOutputAdapter2.mController.mColorItemContent));
            mediaDeviceViewHolder2.setSingleLineLayout(mediaDevice.getName(), false, true, false, false);
            return;
        }
        if (((ArrayList) mediaOutputController3.getSelectedMediaDevice()).size() > 1 && MediaOutputBaseAdapter.isDeviceIncluded(mediaOutputController3.getSelectedMediaDevice(), mediaDevice)) {
            boolean isDeviceIncluded = MediaOutputBaseAdapter.isDeviceIncluded(mediaOutputController3.getDeselectableMediaDevice(), mediaDevice);
            mediaDeviceViewHolder2.updateTitleIcon(R.drawable.media_output_icon_volume, mediaOutputController3.mColorItemContent);
            mediaDeviceViewHolder2.updateGroupableCheckBox(true, isDeviceIncluded, mediaDevice);
            mediaDeviceViewHolder2.updateEndClickArea(mediaDevice, isDeviceIncluded);
            ViewGroup viewGroup2 = mediaDeviceViewHolder2.mContainerLayout;
            viewGroup2.setFocusable(false);
            viewGroup2.setImportantForAccessibility(2);
            mediaDeviceViewHolder2.setUpContentDescriptionForView(mediaDeviceViewHolder2.mSeekBar, mediaDevice);
            mediaDeviceViewHolder2.setSingleLineLayout(mediaDevice.getName(), true, false, true, true);
            mediaDeviceViewHolder2.initSeekbar(mediaDevice, z2);
            return;
        }
        if (mediaOutputController3.hasAdjustVolumeUserRestriction() || !isCurrentlyConnected) {
            if (MediaOutputBaseAdapter.isDeviceIncluded(mediaOutputController3.getSelectableMediaDevice(), mediaDevice)) {
                mediaDeviceViewHolder2.setUpDeviceIcon(mediaDevice);
                mediaDeviceViewHolder2.updateGroupableCheckBox(false, true, mediaDevice);
                mediaDeviceViewHolder2.updateEndClickArea(mediaDevice, true);
                MediaOutputAdapter$Api34Impl$$ExternalSyntheticLambda0 mediaOutputAdapter$Api34Impl$$ExternalSyntheticLambda03 = new MediaOutputAdapter$Api34Impl$$ExternalSyntheticLambda0(mediaDeviceViewHolder2, mediaDevice, 4);
                mediaDeviceViewHolder2.mContainerLayout.setOnClickListener(mediaOutputAdapter$Api34Impl$$ExternalSyntheticLambda03);
                mediaDeviceViewHolder2.mIconAreaLayout.setOnClickListener(mediaOutputAdapter$Api34Impl$$ExternalSyntheticLambda03);
                mediaDeviceViewHolder2.setSingleLineLayout(mediaDevice.getName(), false, false, true, true);
                return;
            }
            mediaDeviceViewHolder2.setUpDeviceIcon(mediaDevice);
            mediaDeviceViewHolder2.setSingleLineLayout(mediaDevice.getName(), false, false, false, false);
            if (mediaDevice.hasOngoingSession()) {
                drawable = mediaOutputAdapter2.mContext.getDrawable(R.drawable.ic_sound_bars_anim);
            } else {
                Context context2 = mediaOutputAdapter2.mContext;
                int selectionBehavior2 = mediaDevice.getSelectionBehavior();
                drawable = selectionBehavior2 != 0 ? selectionBehavior2 != 2 ? null : context2.getDrawable(R.drawable.media_output_status_help) : context2.getDrawable(R.drawable.media_output_status_failed);
            }
            if (drawable != null) {
                mediaDeviceViewHolder2.updateDeviceStatusIcon(drawable);
                mediaDeviceViewHolder2.mStatusIcon.setVisibility(0);
            }
            float f2 = mediaDeviceViewHolder2.updateClickActionBasedOnSelectionBehavior(mediaDevice) ? 1.0f : 0.5f;
            mediaDeviceViewHolder2.mTitleIcon.setAlpha(f2);
            mediaDeviceViewHolder2.mTitleText.setAlpha(f2);
            mediaDeviceViewHolder2.mStatusIcon.setAlpha(f2);
            return;
        }
        if (z && !mediaOutputController3.isCurrentConnectedDeviceRemote()) {
            mediaDeviceViewHolder2.setUpDeviceIcon(mediaDevice);
            MediaOutputAdapter$MediaDeviceViewHolder$$ExternalSyntheticLambda3 mediaOutputAdapter$MediaDeviceViewHolder$$ExternalSyntheticLambda3 = new MediaOutputAdapter$MediaDeviceViewHolder$$ExternalSyntheticLambda3(mediaDeviceViewHolder2, 0);
            mediaDeviceViewHolder2.mContainerLayout.setOnClickListener(mediaOutputAdapter$MediaDeviceViewHolder$$ExternalSyntheticLambda3);
            mediaDeviceViewHolder2.mIconAreaLayout.setOnClickListener(mediaOutputAdapter$MediaDeviceViewHolder$$ExternalSyntheticLambda3);
            mediaDeviceViewHolder2.setSingleLineLayout(mediaDevice.getName(), false, false, false, false);
            return;
        }
        if (mediaDevice.hasOngoingSession()) {
            mediaOutputAdapter2.mCurrentActivePosition = i;
            mediaDeviceViewHolder2.updateTitleIcon(R.drawable.media_output_icon_volume, mediaOutputController3.mColorItemContent);
            if (mediaDevice.isHostForOngoingSession()) {
                i3 = R.drawable.media_output_status_edit_session;
            }
            mediaDeviceViewHolder2.updateEndClickAreaAsSessionEditing(mediaDevice, i3);
            mediaDeviceViewHolder2.mEndClickIcon.setVisibility(0);
            mediaDeviceViewHolder2.setSingleLineLayout(mediaDevice.getName(), true, false, false, true);
            mediaDeviceViewHolder2.initSeekbar(mediaDevice, z2);
            return;
        }
        if (!mediaOutputController3.isCurrentConnectedDeviceRemote() || ((ArrayList) mediaOutputController3.getSelectableMediaDevice()).isEmpty()) {
            mediaDeviceViewHolder2.updateTitleIcon(R.drawable.media_output_icon_volume, mediaOutputController3.mColorItemContent);
            ViewGroup viewGroup3 = mediaDeviceViewHolder2.mContainerLayout;
            viewGroup3.setFocusable(false);
            viewGroup3.setImportantForAccessibility(2);
            mediaDeviceViewHolder2.setUpContentDescriptionForView(mediaDeviceViewHolder2.mSeekBar, mediaDevice);
            mediaOutputAdapter2.mCurrentActivePosition = i;
            mediaDeviceViewHolder2.setSingleLineLayout(mediaDevice.getName(), true, false, false, false);
            mediaDeviceViewHolder2.initSeekbar(mediaDevice, z2);
            return;
        }
        mediaDeviceViewHolder2.updateTitleIcon(R.drawable.media_output_icon_volume, mediaOutputController3.mColorItemContent);
        boolean isDeviceIncluded2 = MediaOutputBaseAdapter.isDeviceIncluded(mediaOutputController3.getDeselectableMediaDevice(), mediaDevice);
        mediaDeviceViewHolder2.updateGroupableCheckBox(true, isDeviceIncluded2, mediaDevice);
        mediaDeviceViewHolder2.updateEndClickArea(mediaDevice, isDeviceIncluded2);
        ViewGroup viewGroup4 = mediaDeviceViewHolder2.mContainerLayout;
        viewGroup4.setFocusable(false);
        viewGroup4.setImportantForAccessibility(2);
        mediaDeviceViewHolder2.setUpContentDescriptionForView(mediaDeviceViewHolder2.mSeekBar, mediaDevice);
        mediaDeviceViewHolder2.setSingleLineLayout(mediaDevice.getName(), true, false, true, true);
        mediaDeviceViewHolder2.initSeekbar(mediaDevice, z2);
    }

    @Override // com.android.systemui.media.dialog.MediaOutputBaseAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(int i, ViewGroup viewGroup) {
        super.onCreateViewHolder(i, viewGroup);
        return i != 1 ? new MediaDeviceViewHolder(this.mHolderView) : new MediaGroupDividerViewHolder(this.mHolderView);
    }
}
