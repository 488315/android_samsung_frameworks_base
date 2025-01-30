package com.android.systemui.media.dialog;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.Icon;
import android.graphics.drawable.LayerDrawable;
import android.media.MediaRoute2Info;
import android.media.session.MediaController;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.core.graphics.drawable.IconCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.media.MediaDevice;
import com.android.settingslib.utils.ThreadUtils;
import com.android.systemui.R;
import com.android.systemui.media.dialog.MediaOutputAdapter;
import com.android.systemui.media.dialog.MediaOutputBaseAdapter;
import com.android.systemui.shared.system.SysUiStatsLog;
import com.samsung.android.knox.ex.knoxAI.KnoxAiManagerInternal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class MediaOutputBaseAdapter extends RecyclerView.Adapter {
    public Context mContext;
    public final MediaOutputController mController;
    public View mHolderView;
    public boolean mIsDragging = false;
    public int mCurrentActivePosition = -1;
    public boolean mIsInitVolumeFirstTime = true;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class MediaDeviceBaseViewHolder extends RecyclerView.ViewHolder {
        public static final /* synthetic */ int $r8$clinit = 0;
        public final CheckBox mCheckBox;
        public final ViewGroup mContainerLayout;
        public ValueAnimator mCornerAnimator;
        public String mDeviceId;
        public final ImageView mEndClickIcon;
        public final ViewGroup mEndTouchArea;
        public final FrameLayout mIconAreaLayout;
        public final FrameLayout mItemLayout;
        public final ProgressBar mProgressBar;
        MediaOutputSeekbar mSeekBar;
        public final ImageView mStatusIcon;
        public final TextView mSubTitleText;
        public final ImageView mTitleIcon;
        public final TextView mTitleText;
        public final LinearLayout mTwoLineLayout;
        public final TextView mTwoLineTitleText;
        public ValueAnimator mVolumeAnimator;
        public final TextView mVolumeValueText;

        public MediaDeviceBaseViewHolder(View view) {
            super(view);
            this.mContainerLayout = (ViewGroup) view.requireViewById(R.id.device_container);
            this.mItemLayout = (FrameLayout) view.requireViewById(R.id.item_layout);
            this.mTitleText = (TextView) view.requireViewById(R.id.title);
            this.mSubTitleText = (TextView) view.requireViewById(R.id.subtitle);
            this.mTwoLineLayout = (LinearLayout) view.requireViewById(R.id.two_line_layout);
            this.mTwoLineTitleText = (TextView) view.requireViewById(R.id.two_line_title);
            this.mTitleIcon = (ImageView) view.requireViewById(R.id.title_icon);
            this.mProgressBar = (ProgressBar) view.requireViewById(R.id.volume_indeterminate_progress);
            this.mSeekBar = (MediaOutputSeekbar) view.requireViewById(R.id.volume_seekbar);
            this.mStatusIcon = (ImageView) view.requireViewById(R.id.media_output_item_status);
            this.mCheckBox = (CheckBox) view.requireViewById(R.id.check_box);
            this.mEndTouchArea = (ViewGroup) view.requireViewById(R.id.end_action_area);
            this.mEndClickIcon = (ImageView) view.requireViewById(R.id.media_output_item_end_click_icon);
            this.mVolumeValueText = (TextView) view.requireViewById(R.id.volume_value);
            this.mIconAreaLayout = (FrameLayout) view.requireViewById(R.id.icon_area);
            MediaOutputController mediaOutputController = MediaOutputBaseAdapter.this.mController;
            ValueAnimator ofFloat = ValueAnimator.ofFloat(mediaOutputController.mInactiveRadius, mediaOutputController.mActiveRadius);
            this.mCornerAnimator = ofFloat;
            ofFloat.setDuration(500L);
            this.mCornerAnimator.setInterpolator(new LinearInterpolator());
            ValueAnimator ofInt = ValueAnimator.ofInt(new int[0]);
            this.mVolumeAnimator = ofInt;
            ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.media.dialog.MediaOutputBaseAdapter$MediaDeviceBaseViewHolder$$ExternalSyntheticLambda0
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    MediaOutputBaseAdapter.MediaDeviceBaseViewHolder mediaDeviceBaseViewHolder = MediaOutputBaseAdapter.MediaDeviceBaseViewHolder.this;
                    mediaDeviceBaseViewHolder.getClass();
                    mediaDeviceBaseViewHolder.mSeekBar.setProgress(((Integer) valueAnimator.getAnimatedValue()).intValue());
                }
            });
            this.mVolumeAnimator.setDuration(500L);
            this.mVolumeAnimator.setInterpolator(new LinearInterpolator());
            this.mVolumeAnimator.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.media.dialog.MediaOutputBaseAdapter.MediaDeviceBaseViewHolder.2
                @Override // android.animation.Animator.AnimatorListener
                public final void onAnimationCancel(Animator animator) {
                    MediaDeviceBaseViewHolder.this.mSeekBar.setEnabled(true);
                }

                @Override // android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    MediaDeviceBaseViewHolder.this.mSeekBar.setEnabled(true);
                }

                @Override // android.animation.Animator.AnimatorListener
                public final void onAnimationStart(Animator animator) {
                    MediaDeviceBaseViewHolder.this.mSeekBar.setEnabled(false);
                }

                @Override // android.animation.Animator.AnimatorListener
                public final void onAnimationRepeat(Animator animator) {
                }
            });
        }

        public final void disableSeekBar() {
            this.mSeekBar.setEnabled(false);
            this.mSeekBar.setOnTouchListener(new ViewOnTouchListenerC1814x9ea0902(0));
            this.mIconAreaLayout.setOnClickListener(null);
        }

        /* JADX WARN: Removed duplicated region for block: B:14:0x0044  */
        /* JADX WARN: Removed duplicated region for block: B:17:0x006a  */
        /* JADX WARN: Removed duplicated region for block: B:20:0x0087  */
        /* JADX WARN: Removed duplicated region for block: B:44:0x00ec  */
        /* JADX WARN: Removed duplicated region for block: B:48:0x0071  */
        /* JADX WARN: Removed duplicated region for block: B:49:0x0048  */
        /* JADX WARN: Removed duplicated region for block: B:55:0x003f  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void initSeekbar(final MediaDevice mediaDevice, boolean z) {
            boolean z2;
            boolean z3;
            int streamMaxVolume;
            MediaOutputBaseAdapter mediaOutputBaseAdapter = MediaOutputBaseAdapter.this;
            MediaOutputController mediaOutputController = mediaOutputBaseAdapter.mController;
            MediaController mediaController = mediaOutputController.mMediaController;
            if (((mediaController == null || mediaController.getPlaybackInfo() == null || mediaOutputController.mMediaController.getPlaybackInfo().getPlaybackType() != 1) ? false : true) || mediaDevice.getDeviceType() != 7) {
                MediaRoute2Info mediaRoute2Info = mediaDevice.mRouteInfo;
                if (mediaRoute2Info == null) {
                    Log.w("MediaDevice", "RouteInfo is empty, regarded as volume fixed.");
                } else if (mediaRoute2Info.getVolumeHandling() != 0) {
                    z2 = false;
                    if (!z2) {
                        z3 = true;
                        if (z3) {
                            disableSeekBar();
                        } else {
                            this.mSeekBar.setEnabled(true);
                            this.mSeekBar.setOnTouchListener(new ViewOnTouchListenerC1814x9ea0902(1));
                            final MediaOutputAdapter.MediaDeviceViewHolder mediaDeviceViewHolder = (MediaOutputAdapter.MediaDeviceViewHolder) this;
                            this.mIconAreaLayout.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.media.dialog.MediaOutputBaseAdapter$MediaDeviceBaseViewHolder$$ExternalSyntheticLambda3
                                @Override // android.view.View.OnClickListener
                                public final void onClick(View view) {
                                    final MediaOutputBaseAdapter.MediaDeviceBaseViewHolder mediaDeviceBaseViewHolder = mediaDeviceViewHolder;
                                    MediaDevice mediaDevice2 = mediaDevice;
                                    mediaDeviceBaseViewHolder.getClass();
                                    int currentVolume = mediaDevice2.getCurrentVolume();
                                    FrameLayout frameLayout = mediaDeviceBaseViewHolder.mIconAreaLayout;
                                    MediaOutputBaseAdapter mediaOutputBaseAdapter2 = MediaOutputBaseAdapter.this;
                                    if (currentVolume != 0) {
                                        MediaOutputSeekbar mediaOutputSeekbar = mediaDeviceBaseViewHolder.mSeekBar;
                                        mediaOutputSeekbar.setProgress(mediaOutputSeekbar.getMin());
                                        mediaOutputBaseAdapter2.mController.getClass();
                                        ThreadUtils.postOnBackgroundThread(new MediaOutputController$$ExternalSyntheticLambda4(mediaDevice2, 0));
                                        mediaDeviceBaseViewHolder.updateMutedVolumeIcon();
                                        frameLayout.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.media.dialog.MediaOutputBaseAdapter$MediaDeviceBaseViewHolder$$ExternalSyntheticLambda5
                                            @Override // android.view.View.OnTouchListener
                                            public final boolean onTouch(View view2, MotionEvent motionEvent) {
                                                MediaOutputBaseAdapter.MediaDeviceBaseViewHolder.this.mSeekBar.dispatchTouchEvent(motionEvent);
                                                return false;
                                            }
                                        });
                                        return;
                                    }
                                    MediaOutputSeekbar mediaOutputSeekbar2 = mediaDeviceBaseViewHolder.mSeekBar;
                                    mediaOutputSeekbar2.getClass();
                                    mediaOutputSeekbar2.setProgress(KnoxAiManagerInternal.CONN_MAX_WAIT_TIME, true);
                                    mediaOutputBaseAdapter2.mController.getClass();
                                    ThreadUtils.postOnBackgroundThread(new MediaOutputController$$ExternalSyntheticLambda4(mediaDevice2, 2));
                                    mediaDeviceBaseViewHolder.updateUnmutedVolumeIcon();
                                    frameLayout.setOnTouchListener(new ViewOnTouchListenerC1814x9ea0902(2));
                                }
                            });
                        }
                        MediaOutputSeekbar mediaOutputSeekbar = this.mSeekBar;
                        if (mediaDevice.mRouteInfo != null) {
                            Log.w("MediaDevice", "Unable to get max volume. RouteInfo is empty");
                            streamMaxVolume = 0;
                        } else {
                            streamMaxVolume = mediaDevice.mAudioManager.getStreamMaxVolume(3) * 10;
                        }
                        mediaOutputSeekbar.setMax(streamMaxVolume * 1000);
                        int currentVolume = mediaDevice.getCurrentVolume();
                        if (!mediaOutputBaseAdapter.mIsDragging) {
                            if (this.mSeekBar.getProgress() / 1000 != currentVolume) {
                                if (z && !mediaOutputBaseAdapter.mIsInitVolumeFirstTime) {
                                    updateTitleIcon(currentVolume == 0 ? R.drawable.media_output_icon_volume_off : R.drawable.media_output_icon_volume, mediaOutputBaseAdapter.mController.mColorItemContent);
                                } else if (!this.mVolumeAnimator.isStarted()) {
                                    if (((int) ((currentVolume * 100000.0d) / this.mSeekBar.getMax())) == 0) {
                                        updateMutedVolumeIcon();
                                    } else {
                                        updateUnmutedVolumeIcon();
                                    }
                                    MediaOutputSeekbar mediaOutputSeekbar2 = this.mSeekBar;
                                    mediaOutputSeekbar2.getClass();
                                    mediaOutputSeekbar2.setProgress(currentVolume == 0 ? 0 : (currentVolume * 1000) + 500, true);
                                }
                            } else if (currentVolume == 0) {
                                MediaOutputSeekbar mediaOutputSeekbar3 = this.mSeekBar;
                                mediaOutputSeekbar3.setProgress(mediaOutputSeekbar3.getMin());
                                updateMutedVolumeIcon();
                            }
                        }
                        if (mediaOutputBaseAdapter.mIsInitVolumeFirstTime) {
                            mediaOutputBaseAdapter.mIsInitVolumeFirstTime = false;
                        }
                        this.mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.android.systemui.media.dialog.MediaOutputBaseAdapter.MediaDeviceBaseViewHolder.1
                            public boolean mStartFromMute = false;

                            @Override // android.widget.SeekBar.OnSeekBarChangeListener
                            public final void onProgressChanged(SeekBar seekBar, int i, boolean z4) {
                                MediaDevice mediaDevice2 = mediaDevice;
                                if (mediaDevice2 == null || !z4) {
                                    return;
                                }
                                int i2 = MediaOutputSeekbar.$r8$clinit;
                                int i3 = i / 1000;
                                int currentVolume2 = mediaDevice2.getCurrentVolume();
                                MediaDeviceBaseViewHolder mediaDeviceBaseViewHolder = MediaDeviceBaseViewHolder.this;
                                mediaDeviceBaseViewHolder.mVolumeValueText.setText(MediaOutputBaseAdapter.this.mContext.getResources().getString(R.string.media_output_dialog_volume_percentage, Integer.valueOf((int) ((i3 * 100000.0d) / seekBar.getMax()))));
                                MediaDeviceBaseViewHolder.this.mVolumeValueText.setVisibility(0);
                                if (this.mStartFromMute) {
                                    MediaDeviceBaseViewHolder.this.updateUnmutedVolumeIcon();
                                    this.mStartFromMute = false;
                                }
                                if (i3 != currentVolume2) {
                                    MediaOutputController mediaOutputController2 = MediaOutputBaseAdapter.this.mController;
                                    MediaDevice mediaDevice3 = mediaDevice;
                                    mediaOutputController2.getClass();
                                    ThreadUtils.postOnBackgroundThread(new MediaOutputController$$ExternalSyntheticLambda4(mediaDevice3, i3));
                                }
                            }

                            @Override // android.widget.SeekBar.OnSeekBarChangeListener
                            public final void onStartTrackingTouch(SeekBar seekBar) {
                                MediaDeviceBaseViewHolder.this.mTitleIcon.setVisibility(4);
                                MediaDeviceBaseViewHolder.this.mVolumeValueText.setVisibility(0);
                                int progress = seekBar.getProgress();
                                int i = MediaOutputSeekbar.$r8$clinit;
                                this.mStartFromMute = progress / 1000 == 0;
                                MediaOutputBaseAdapter.this.mIsDragging = true;
                            }

                            @Override // android.widget.SeekBar.OnSeekBarChangeListener
                            public final void onStopTrackingTouch(SeekBar seekBar) {
                                int progress = seekBar.getProgress();
                                int i = MediaOutputSeekbar.$r8$clinit;
                                if (progress / 1000 == 0) {
                                    seekBar.setProgress(0);
                                    MediaDeviceBaseViewHolder.this.updateMutedVolumeIcon();
                                } else {
                                    MediaDeviceBaseViewHolder.this.updateUnmutedVolumeIcon();
                                }
                                MediaDeviceBaseViewHolder.this.mTitleIcon.setVisibility(0);
                                MediaDeviceBaseViewHolder.this.mVolumeValueText.setVisibility(8);
                                MediaOutputController mediaOutputController2 = MediaOutputBaseAdapter.this.mController;
                                MediaDevice mediaDevice2 = mediaDevice;
                                MediaOutputMetricLogger mediaOutputMetricLogger = mediaOutputController2.mMetricLogger;
                                if (MediaOutputMetricLogger.DEBUG) {
                                    mediaOutputMetricLogger.getClass();
                                    Log.d("MediaOutputMetricLogger", "logInteraction - AdjustVolume");
                                }
                                mediaOutputMetricLogger.getClass();
                                SysUiStatsLog.write(1, MediaOutputMetricLogger.getInteractionDeviceType(mediaDevice2), mediaOutputMetricLogger.getLoggingPackageName());
                                MediaOutputBaseAdapter.this.mIsDragging = false;
                            }
                        });
                    }
                }
                z2 = true;
                if (!z2) {
                }
            }
            z3 = false;
            if (z3) {
            }
            MediaOutputSeekbar mediaOutputSeekbar4 = this.mSeekBar;
            if (mediaDevice.mRouteInfo != null) {
            }
            mediaOutputSeekbar4.setMax(streamMaxVolume * 1000);
            int currentVolume2 = mediaDevice.getCurrentVolume();
            if (!mediaOutputBaseAdapter.mIsDragging) {
            }
            if (mediaOutputBaseAdapter.mIsInitVolumeFirstTime) {
            }
            this.mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.android.systemui.media.dialog.MediaOutputBaseAdapter.MediaDeviceBaseViewHolder.1
                public boolean mStartFromMute = false;

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public final void onProgressChanged(SeekBar seekBar, int i, boolean z4) {
                    MediaDevice mediaDevice2 = mediaDevice;
                    if (mediaDevice2 == null || !z4) {
                        return;
                    }
                    int i2 = MediaOutputSeekbar.$r8$clinit;
                    int i3 = i / 1000;
                    int currentVolume22 = mediaDevice2.getCurrentVolume();
                    MediaDeviceBaseViewHolder mediaDeviceBaseViewHolder = MediaDeviceBaseViewHolder.this;
                    mediaDeviceBaseViewHolder.mVolumeValueText.setText(MediaOutputBaseAdapter.this.mContext.getResources().getString(R.string.media_output_dialog_volume_percentage, Integer.valueOf((int) ((i3 * 100000.0d) / seekBar.getMax()))));
                    MediaDeviceBaseViewHolder.this.mVolumeValueText.setVisibility(0);
                    if (this.mStartFromMute) {
                        MediaDeviceBaseViewHolder.this.updateUnmutedVolumeIcon();
                        this.mStartFromMute = false;
                    }
                    if (i3 != currentVolume22) {
                        MediaOutputController mediaOutputController2 = MediaOutputBaseAdapter.this.mController;
                        MediaDevice mediaDevice3 = mediaDevice;
                        mediaOutputController2.getClass();
                        ThreadUtils.postOnBackgroundThread(new MediaOutputController$$ExternalSyntheticLambda4(mediaDevice3, i3));
                    }
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public final void onStartTrackingTouch(SeekBar seekBar) {
                    MediaDeviceBaseViewHolder.this.mTitleIcon.setVisibility(4);
                    MediaDeviceBaseViewHolder.this.mVolumeValueText.setVisibility(0);
                    int progress = seekBar.getProgress();
                    int i = MediaOutputSeekbar.$r8$clinit;
                    this.mStartFromMute = progress / 1000 == 0;
                    MediaOutputBaseAdapter.this.mIsDragging = true;
                }

                @Override // android.widget.SeekBar.OnSeekBarChangeListener
                public final void onStopTrackingTouch(SeekBar seekBar) {
                    int progress = seekBar.getProgress();
                    int i = MediaOutputSeekbar.$r8$clinit;
                    if (progress / 1000 == 0) {
                        seekBar.setProgress(0);
                        MediaDeviceBaseViewHolder.this.updateMutedVolumeIcon();
                    } else {
                        MediaDeviceBaseViewHolder.this.updateUnmutedVolumeIcon();
                    }
                    MediaDeviceBaseViewHolder.this.mTitleIcon.setVisibility(0);
                    MediaDeviceBaseViewHolder.this.mVolumeValueText.setVisibility(8);
                    MediaOutputController mediaOutputController2 = MediaOutputBaseAdapter.this.mController;
                    MediaDevice mediaDevice2 = mediaDevice;
                    MediaOutputMetricLogger mediaOutputMetricLogger = mediaOutputController2.mMetricLogger;
                    if (MediaOutputMetricLogger.DEBUG) {
                        mediaOutputMetricLogger.getClass();
                        Log.d("MediaOutputMetricLogger", "logInteraction - AdjustVolume");
                    }
                    mediaOutputMetricLogger.getClass();
                    SysUiStatsLog.write(1, MediaOutputMetricLogger.getInteractionDeviceType(mediaDevice2), mediaOutputMetricLogger.getLoggingPackageName());
                    MediaOutputBaseAdapter.this.mIsDragging = false;
                }
            });
        }

        public final void setSingleLineLayout(CharSequence charSequence, boolean z, boolean z2, boolean z3, boolean z4) {
            this.mTwoLineLayout.setVisibility(8);
            boolean z5 = z || z2;
            boolean isRunning = this.mCornerAnimator.isRunning();
            FrameLayout frameLayout = this.mItemLayout;
            MediaOutputBaseAdapter mediaOutputBaseAdapter = MediaOutputBaseAdapter.this;
            if (!isRunning) {
                frameLayout.setBackground(z ? mediaOutputBaseAdapter.mContext.getDrawable(R.drawable.media_output_item_background_active).mutate() : mediaOutputBaseAdapter.mContext.getDrawable(R.drawable.media_output_item_background).mutate());
                if (z) {
                    updateSeekbarProgressBackground();
                }
            }
            frameLayout.setBackgroundTintList(ColorStateList.valueOf(z5 ? mediaOutputBaseAdapter.mController.mColorConnectedItemBackground : mediaOutputBaseAdapter.mController.mColorItemBackground));
            this.mIconAreaLayout.setBackgroundTintList(ColorStateList.valueOf(z ? mediaOutputBaseAdapter.mController.mColorSeekbarProgress : z2 ? mediaOutputBaseAdapter.mController.mColorConnectedItemBackground : mediaOutputBaseAdapter.mController.mColorItemBackground));
            this.mProgressBar.setVisibility(z2 ? 0 : 8);
            this.mSeekBar.setAlpha(1.0f);
            this.mSeekBar.setVisibility(z ? 0 : 8);
            if (!z) {
                MediaOutputSeekbar mediaOutputSeekbar = this.mSeekBar;
                mediaOutputSeekbar.setProgress(mediaOutputSeekbar.getMin());
            }
            TextView textView = this.mTitleText;
            textView.setText(charSequence);
            textView.setVisibility(0);
            this.mCheckBox.setVisibility(z3 ? 0 : 8);
            this.mEndTouchArea.setVisibility(z4 ? 0 : 8);
            ((ViewGroup.MarginLayoutParams) frameLayout.getLayoutParams()).rightMargin = z4 ? mediaOutputBaseAdapter.mController.mItemMarginEndSelectable : mediaOutputBaseAdapter.mController.mItemMarginEndDefault;
            this.mTitleIcon.setBackgroundTintList(ColorStateList.valueOf(mediaOutputBaseAdapter.mController.mColorItemContent));
        }

        public final void setTwoLineLayout(MediaDevice mediaDevice, boolean z, boolean z2, boolean z3, boolean z4) {
            this.mTitleText.setVisibility(8);
            this.mTwoLineLayout.setVisibility(0);
            this.mStatusIcon.setVisibility(z3 ? 0 : 8);
            this.mSeekBar.setAlpha(1.0f);
            this.mSeekBar.setVisibility(z2 ? 0 : 8);
            MediaOutputBaseAdapter mediaOutputBaseAdapter = MediaOutputBaseAdapter.this;
            Drawable mutate = mediaOutputBaseAdapter.mContext.getDrawable(!z2 ? R.drawable.media_output_item_background : R.drawable.media_output_item_background_active).mutate();
            MediaOutputController mediaOutputController = mediaOutputBaseAdapter.mController;
            ColorStateList valueOf = ColorStateList.valueOf(!z2 ? mediaOutputController.mColorItemBackground : mediaOutputController.mColorConnectedItemBackground);
            FrameLayout frameLayout = this.mItemLayout;
            frameLayout.setBackgroundTintList(valueOf);
            this.mIconAreaLayout.setBackgroundTintList(ColorStateList.valueOf(z2 ? mediaOutputController.mColorSeekbarProgress : mediaOutputController.mColorItemBackground));
            if (z2) {
                updateSeekbarProgressBackground();
            }
            this.mEndTouchArea.setVisibility(z4 ? 0 : 8);
            this.mEndClickIcon.setVisibility(z4 ? 0 : 8);
            ((ViewGroup.MarginLayoutParams) frameLayout.getLayoutParams()).rightMargin = z4 ? mediaOutputController.mItemMarginEndSelectable : mediaOutputController.mItemMarginEndDefault;
            frameLayout.setBackground(mutate);
            this.mProgressBar.setVisibility(8);
            this.mSubTitleText.setVisibility(0);
            TextView textView = this.mTwoLineTitleText;
            textView.setTranslationY(0.0f);
            textView.setText(mediaDevice.getName());
            textView.setTypeface(Typeface.create(mediaOutputBaseAdapter.mContext.getString(z ? android.R.string.dream_preview_title : android.R.string.dream_accessibility_action_click), 0));
        }

        public final void setUpDeviceIcon(final MediaDevice mediaDevice) {
            ThreadUtils.postOnBackgroundThread(new Runnable() { // from class: com.android.systemui.media.dialog.MediaOutputBaseAdapter$MediaDeviceBaseViewHolder$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    Bitmap createBitmap;
                    final MediaOutputBaseAdapter.MediaDeviceBaseViewHolder mediaDeviceBaseViewHolder = MediaOutputBaseAdapter.MediaDeviceBaseViewHolder.this;
                    final MediaDevice mediaDevice2 = mediaDevice;
                    MediaOutputController mediaOutputController = MediaOutputBaseAdapter.this.mController;
                    mediaOutputController.getClass();
                    Drawable icon = mediaDevice2.getIcon();
                    if (icon == null) {
                        if (MediaOutputController.DEBUG) {
                            Log.d("MediaOutputController", "getDeviceIconCompat() device : " + mediaDevice2.getName() + ", drawable is null");
                        }
                        icon = mediaOutputController.mContext.getDrawable(android.R.drawable.ic_action_open);
                    }
                    boolean z = icon instanceof BitmapDrawable;
                    if (!z) {
                        boolean equals = mediaOutputController.mLocalMediaManager.getCurrentConnectedDevice().getId().equals(mediaDevice2.getId());
                        if (((ArrayList) mediaOutputController.getSelectedMediaDevice()).size() > 1) {
                            ((ArrayList) mediaOutputController.getSelectedMediaDevice()).contains(mediaDevice2);
                        }
                        if (!mediaOutputController.hasAdjustVolumeUserRestriction() && equals) {
                            mediaOutputController.isAnyDeviceTransferring();
                        }
                        icon.setColorFilter(new PorterDuffColorFilter(mediaOutputController.mColorItemContent, PorterDuff.Mode.SRC_IN));
                    }
                    boolean z2 = BluetoothUtils.DEBUG;
                    if (z) {
                        createBitmap = ((BitmapDrawable) icon).getBitmap();
                    } else {
                        int intrinsicWidth = icon.getIntrinsicWidth();
                        int intrinsicHeight = icon.getIntrinsicHeight();
                        if (intrinsicWidth <= 0) {
                            intrinsicWidth = 1;
                        }
                        createBitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight > 0 ? intrinsicHeight : 1, Bitmap.Config.ARGB_8888);
                        Canvas canvas = new Canvas(createBitmap);
                        icon.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                        icon.draw(canvas);
                    }
                    final Icon icon$1 = IconCompat.createWithBitmap(createBitmap).toIcon$1();
                    ThreadUtils.postOnMainThread(new Runnable() { // from class: com.android.systemui.media.dialog.MediaOutputBaseAdapter$MediaDeviceBaseViewHolder$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            MediaOutputBaseAdapter.MediaDeviceBaseViewHolder mediaDeviceBaseViewHolder2 = MediaOutputBaseAdapter.MediaDeviceBaseViewHolder.this;
                            MediaDevice mediaDevice3 = mediaDevice2;
                            Icon icon2 = icon$1;
                            if (TextUtils.equals(mediaDeviceBaseViewHolder2.mDeviceId, mediaDevice3.getId())) {
                                ImageView imageView = mediaDeviceBaseViewHolder2.mTitleIcon;
                                imageView.setImageIcon(icon2);
                                imageView.setImageTintList(ColorStateList.valueOf(MediaOutputBaseAdapter.this.mController.mColorItemContent));
                            }
                        }
                    });
                }
            });
        }

        public final void updateMutedVolumeIcon() {
            MediaOutputBaseAdapter mediaOutputBaseAdapter = MediaOutputBaseAdapter.this;
            this.mIconAreaLayout.setBackground(mediaOutputBaseAdapter.mContext.getDrawable(R.drawable.media_output_item_background_active));
            updateTitleIcon(R.drawable.media_output_icon_volume_off, mediaOutputBaseAdapter.mController.mColorItemContent);
        }

        public final void updateSeekbarProgressBackground() {
            GradientDrawable gradientDrawable = (GradientDrawable) ((ClipDrawable) ((LayerDrawable) this.mSeekBar.getProgressDrawable()).findDrawableByLayerId(android.R.id.progress)).getDrawable();
            float f = MediaOutputBaseAdapter.this.mController.mActiveRadius;
            gradientDrawable.setCornerRadii(new float[]{0.0f, 0.0f, f, f, f, f, 0.0f, 0.0f});
        }

        public final void updateTitleIcon(int i, int i2) {
            MediaOutputBaseAdapter mediaOutputBaseAdapter = MediaOutputBaseAdapter.this;
            Drawable drawable = mediaOutputBaseAdapter.mContext.getDrawable(i);
            ImageView imageView = this.mTitleIcon;
            imageView.setImageDrawable(drawable);
            imageView.setImageTintList(ColorStateList.valueOf(i2));
            this.mIconAreaLayout.setBackgroundTintList(ColorStateList.valueOf(mediaOutputBaseAdapter.mController.mColorSeekbarProgress));
        }

        public final void updateUnmutedVolumeIcon() {
            MediaOutputBaseAdapter mediaOutputBaseAdapter = MediaOutputBaseAdapter.this;
            this.mIconAreaLayout.setBackground(mediaOutputBaseAdapter.mContext.getDrawable(R.drawable.media_output_title_icon_area));
            updateTitleIcon(R.drawable.media_output_icon_volume, mediaOutputBaseAdapter.mController.mColorItemContent);
        }
    }

    public MediaOutputBaseAdapter(MediaOutputController mediaOutputController) {
        this.mController = mediaOutputController;
    }

    public static boolean isDeviceIncluded(List list, MediaDevice mediaDevice) {
        Iterator it = ((ArrayList) list).iterator();
        while (it.hasNext()) {
            if (TextUtils.equals(((MediaDevice) it.next()).getId(), mediaDevice.getId())) {
                return true;
            }
        }
        return false;
    }

    public final boolean isCurrentlyConnected(MediaDevice mediaDevice) {
        String id = mediaDevice.getId();
        MediaOutputController mediaOutputController = this.mController;
        if (TextUtils.equals(id, mediaOutputController.getCurrentConnectedMediaDevice().getId())) {
            return true;
        }
        return ((ArrayList) mediaOutputController.getSelectedMediaDevice()).size() == 1 && isDeviceIncluded(mediaOutputController.getSelectedMediaDevice(), mediaDevice);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public RecyclerView.ViewHolder onCreateViewHolder(RecyclerView recyclerView, int i) {
        Context context = recyclerView.getContext();
        this.mContext = context;
        this.mHolderView = LayoutInflater.from(context).inflate((i == 0 || i == 2) ? R.layout.media_output_list_item_advanced : R.layout.media_output_list_group_divider, (ViewGroup) recyclerView, false);
        return null;
    }
}
