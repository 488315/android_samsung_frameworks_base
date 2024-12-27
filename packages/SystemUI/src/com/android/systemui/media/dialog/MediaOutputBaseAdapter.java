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
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.core.graphics.drawable.IconCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.media.MediaDevice;
import com.android.settingslib.utils.ThreadUtils;
import com.android.systemui.R;
import com.android.systemui.media.dialog.MediaOutputBaseAdapter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class MediaOutputBaseAdapter extends RecyclerView.Adapter {
    public Context mContext;
    public final MediaOutputController mController;
    public View mHolderView;
    public boolean mIsDragging = false;
    public int mCurrentActivePosition = -1;
    public boolean mIsInitVolumeFirstTime = true;

    public abstract class MediaDeviceBaseViewHolder extends RecyclerView.ViewHolder {
        public static final /* synthetic */ int $r8$clinit = 0;
        public final CheckBox mCheckBox;
        public final ViewGroup mContainerLayout;
        public final ValueAnimator mCornerAnimator;
        public String mDeviceId;
        public final ImageView mEndClickIcon;
        public final ViewGroup mEndTouchArea;
        public final FrameLayout mIconAreaLayout;
        public final FrameLayout mItemLayout;
        public int mLatestUpdateVolume;
        public final ProgressBar mProgressBar;
        MediaOutputSeekbar mSeekBar;
        public final ImageView mStatusIcon;
        public final TextView mSubTitleText;
        public final ImageView mTitleIcon;
        public final TextView mTitleText;
        public final LinearLayout mTwoLineLayout;
        public final TextView mTwoLineTitleText;
        public final ValueAnimator mVolumeAnimator;
        public final TextView mVolumeValueText;

        public MediaDeviceBaseViewHolder(View view) {
            super(view);
            this.mLatestUpdateVolume = -1;
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
            this.mSeekBar.setOnTouchListener(new MediaOutputBaseAdapter$MediaDeviceBaseViewHolder$$ExternalSyntheticLambda2(0));
            this.mIconAreaLayout.setOnClickListener(null);
        }

        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void initSeekbar(final com.android.settingslib.media.MediaDevice r11, boolean r12) {
            /*
                Method dump skipped, instructions count: 230
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.dialog.MediaOutputBaseAdapter.MediaDeviceBaseViewHolder.initSeekbar(com.android.settingslib.media.MediaDevice, boolean):void");
        }

        public final void setSingleLineLayout(CharSequence charSequence, boolean z, boolean z2, boolean z3, boolean z4) {
            this.mTwoLineLayout.setVisibility(8);
            boolean z5 = z || z2;
            boolean isRunning = this.mCornerAnimator.isRunning();
            MediaOutputBaseAdapter mediaOutputBaseAdapter = MediaOutputBaseAdapter.this;
            if (!isRunning) {
                this.mItemLayout.setBackground(z ? mediaOutputBaseAdapter.mContext.getDrawable(R.drawable.media_output_item_background_active).mutate() : mediaOutputBaseAdapter.mContext.getDrawable(R.drawable.media_output_item_background).mutate());
                if (z) {
                    updateSeekbarProgressBackground();
                }
            }
            this.mItemLayout.setBackgroundTintList(ColorStateList.valueOf(z5 ? mediaOutputBaseAdapter.mController.mColorConnectedItemBackground : mediaOutputBaseAdapter.mController.mColorItemBackground));
            this.mIconAreaLayout.setBackgroundTintList(ColorStateList.valueOf(z ? mediaOutputBaseAdapter.mController.mColorSeekbarProgress : z2 ? mediaOutputBaseAdapter.mController.mColorConnectedItemBackground : mediaOutputBaseAdapter.mController.mColorItemBackground));
            this.mProgressBar.setVisibility(z2 ? 0 : 8);
            this.mSeekBar.setAlpha(1.0f);
            this.mSeekBar.setVisibility(z ? 0 : 8);
            if (!z) {
                MediaOutputSeekbar mediaOutputSeekbar = this.mSeekBar;
                mediaOutputSeekbar.setProgress(mediaOutputSeekbar.getMin());
            }
            this.mTitleText.setText(charSequence);
            this.mTitleText.setVisibility(0);
            this.mCheckBox.setVisibility(z3 ? 0 : 8);
            this.mEndTouchArea.setVisibility(z4 ? 0 : 8);
            ((ViewGroup.MarginLayoutParams) this.mItemLayout.getLayoutParams()).rightMargin = z4 ? mediaOutputBaseAdapter.mController.mItemMarginEndSelectable : mediaOutputBaseAdapter.mController.mItemMarginEndDefault;
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
            FrameLayout frameLayout = this.mItemLayout;
            MediaOutputController mediaOutputController = mediaOutputBaseAdapter.mController;
            frameLayout.setBackgroundTintList(ColorStateList.valueOf(!z2 ? mediaOutputController.mColorItemBackground : mediaOutputController.mColorConnectedItemBackground));
            this.mIconAreaLayout.setBackgroundTintList(ColorStateList.valueOf(z2 ? mediaOutputController.mColorSeekbarProgress : mediaOutputController.mColorItemBackground));
            if (z2) {
                updateSeekbarProgressBackground();
            }
            this.mEndTouchArea.setVisibility(z4 ? 0 : 8);
            this.mEndClickIcon.setVisibility(z4 ? 0 : 8);
            ((ViewGroup.MarginLayoutParams) this.mItemLayout.getLayoutParams()).rightMargin = z4 ? mediaOutputController.mItemMarginEndSelectable : mediaOutputController.mItemMarginEndDefault;
            this.mItemLayout.setBackground(mutate);
            this.mProgressBar.setVisibility(8);
            this.mSubTitleText.setVisibility(0);
            this.mTwoLineTitleText.setTranslationY(0.0f);
            this.mTwoLineTitleText.setText(mediaDevice.getName());
            this.mTwoLineTitleText.setTypeface(Typeface.create(mediaOutputBaseAdapter.mContext.getString(z ? android.R.string.dynamic_mode_notification_title : android.R.string.dynamic_mode_notification_summary_v2), 0));
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
                        icon = mediaOutputController.mContext.getDrawable(android.R.drawable.ic_clear_holo_light);
                    }
                    boolean z = icon instanceof BitmapDrawable;
                    if (!z) {
                        boolean equals = mediaOutputController.mLocalMediaManager.getCurrentConnectedDevice().getId().equals(mediaDevice2.getId());
                        if (((ArrayList) mediaOutputController.getSelectedMediaDevice()).size() <= 1 || !((ArrayList) mediaOutputController.getSelectedMediaDevice()).contains(mediaDevice2)) {
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
                    ThreadUtils.postOnMainThread(new Runnable() { // from class: com.android.systemui.media.dialog.MediaOutputBaseAdapter$MediaDeviceBaseViewHolder$$ExternalSyntheticLambda5
                        @Override // java.lang.Runnable
                        public final void run() {
                            MediaOutputBaseAdapter.MediaDeviceBaseViewHolder mediaDeviceBaseViewHolder2 = MediaOutputBaseAdapter.MediaDeviceBaseViewHolder.this;
                            MediaDevice mediaDevice3 = mediaDevice2;
                            Icon icon2 = icon$1;
                            if (TextUtils.equals(mediaDeviceBaseViewHolder2.mDeviceId, mediaDevice3.getId())) {
                                mediaDeviceBaseViewHolder2.mTitleIcon.setImageIcon(icon2);
                                mediaDeviceBaseViewHolder2.mTitleIcon.setImageTintList(ColorStateList.valueOf(MediaOutputBaseAdapter.this.mController.mColorItemContent));
                            }
                        }
                    });
                }
            });
        }

        public final void updateMutedVolumeIcon() {
            FrameLayout frameLayout = this.mIconAreaLayout;
            MediaOutputBaseAdapter mediaOutputBaseAdapter = MediaOutputBaseAdapter.this;
            frameLayout.setBackground(mediaOutputBaseAdapter.mContext.getDrawable(R.drawable.media_output_item_background_active));
            updateTitleIcon(R.drawable.media_output_icon_volume_off, mediaOutputBaseAdapter.mController.mColorItemContent);
        }

        public final void updateSeekbarProgressBackground() {
            GradientDrawable gradientDrawable = (GradientDrawable) ((ClipDrawable) ((LayerDrawable) this.mSeekBar.getProgressDrawable()).findDrawableByLayerId(android.R.id.progress)).getDrawable();
            float f = MediaOutputBaseAdapter.this.mController.mActiveRadius;
            gradientDrawable.setCornerRadii(new float[]{0.0f, 0.0f, f, f, f, f, 0.0f, 0.0f});
        }

        public final void updateTitleIcon(int i, int i2) {
            ImageView imageView = this.mTitleIcon;
            MediaOutputBaseAdapter mediaOutputBaseAdapter = MediaOutputBaseAdapter.this;
            imageView.setImageDrawable(mediaOutputBaseAdapter.mContext.getDrawable(i));
            this.mTitleIcon.setImageTintList(ColorStateList.valueOf(i2));
            this.mIconAreaLayout.setBackgroundTintList(ColorStateList.valueOf(mediaOutputBaseAdapter.mController.mColorSeekbarProgress));
        }

        public final void updateUnmutedVolumeIcon() {
            FrameLayout frameLayout = this.mIconAreaLayout;
            MediaOutputBaseAdapter mediaOutputBaseAdapter = MediaOutputBaseAdapter.this;
            frameLayout.setBackground(mediaOutputBaseAdapter.mContext.getDrawable(R.drawable.media_output_title_icon_area));
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
        if (TextUtils.equals(id, mediaOutputController.mLocalMediaManager.getCurrentConnectedDevice().getId())) {
            return true;
        }
        return ((ArrayList) mediaOutputController.getSelectedMediaDevice()).size() == 1 && isDeviceIncluded(mediaOutputController.getSelectedMediaDevice(), mediaDevice);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public RecyclerView.ViewHolder onCreateViewHolder(int i, ViewGroup viewGroup) {
        Context context = viewGroup.getContext();
        this.mContext = context;
        this.mHolderView = LayoutInflater.from(context).inflate((i == 0 || i == 2) ? R.layout.media_output_list_item_advanced : R.layout.media_output_list_group_divider, viewGroup, false);
        return null;
    }
}
