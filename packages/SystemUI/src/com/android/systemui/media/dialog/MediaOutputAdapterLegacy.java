package com.android.systemui.media.dialog;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.Icon;
import android.graphics.drawable.LayerDrawable;
import android.media.MediaRoute2Info;
import android.media.RoutingSessionInfo;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.core.graphics.drawable.IconCompat;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.media.InfoMediaManager;
import com.android.settingslib.media.InputMediaDevice;
import com.android.settingslib.media.LocalMediaManager;
import com.android.settingslib.media.MediaDevice;
import com.android.settingslib.utils.ThreadUtils;
import com.android.systemui.R;
import com.android.systemui.media.dialog.MediaOutputAdapterBase;
import com.android.systemui.media.dialog.MediaOutputAdapterLegacy;
import com.android.systemui.shared.system.SysUiStatsLog;
import java.util.Iterator;
import java.util.concurrent.Executor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class MediaOutputAdapterLegacy extends MediaOutputAdapterBase {
    public static final boolean DEBUG = Log.isLoggable("MediaOutputAdapterL", 3);
    static final float DEVICE_ACTIVE_ALPHA = 1.0f;
    static final float DEVICE_DISABLED_ALPHA = 0.5f;
    public final Executor mBackgroundExecutor;
    public View mHolderView;
    public final Executor mMainExecutor;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class MediaDeviceViewHolderLegacy extends MediaOutputAdapterBase.MediaDeviceViewHolderBase {
        public static final /* synthetic */ int $r8$clinit = 0;
        public final float mActiveRadius;
        public final CheckBox mCheckBox;
        public final ViewGroup mContainerLayout;
        public final ValueAnimator mCornerAnimator;
        public String mDeviceId;
        public final ImageButton mEndClickIcon;
        public final ViewGroup mEndTouchArea;
        public final FrameLayout mIconAreaLayout;
        public final FrameLayout mItemLayout;
        public int mLatestUpdateVolume;
        public final ProgressBar mProgressBar;
        MediaOutputSeekbar mSeekBar;
        public final ImageView mStatusIcon;
        public final TextView mSubTitleText;
        public final ViewGroup mTextContent;
        public final ImageView mTitleIcon;
        public final TextView mTitleText;
        public final ValueAnimator mVolumeAnimator;
        public final TextView mVolumeValueText;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public abstract class MediaSeekBarChangedListener implements SeekBar.OnSeekBarChangeListener {
            public final MediaDevice mMediaDevice;
            public boolean mStartFromMute = false;
            public final SeekBarVolumeControl mVolumeControl;

            public MediaSeekBarChangedListener(MediaDevice mediaDevice, SeekBarVolumeControl seekBarVolumeControl) {
                this.mMediaDevice = mediaDevice;
                this.mVolumeControl = seekBarVolumeControl;
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public final void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                if (shouldHandleProgressChanged() && z) {
                    Resources resources = MediaDeviceViewHolderLegacy.this.mContext.getResources();
                    MediaOutputSeekbar mediaOutputSeekbar = MediaDeviceViewHolderLegacy.this.mSeekBar;
                    MediaDeviceViewHolderLegacy.this.mVolumeValueText.setText(resources.getString(R.string.media_output_dialog_volume_percentage, Integer.valueOf((int) ((((mediaOutputSeekbar.getProgress() / 1000) * 1000) * 100.0d) / mediaOutputSeekbar.getMax()))));
                    if (this.mStartFromMute) {
                        MediaDeviceViewHolderLegacy.this.updateUnmutedVolumeIcon(this.mMediaDevice);
                        this.mStartFromMute = false;
                    }
                    int i2 = i / 1000;
                    if (i2 != this.mVolumeControl.getVolume()) {
                        MediaDeviceViewHolderLegacy.this.mLatestUpdateVolume = i2;
                        this.mVolumeControl.setVolume(i2);
                    }
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public final void onStartTrackingTouch(SeekBar seekBar) {
                MediaDeviceViewHolderLegacy.this.mTitleIcon.setVisibility(4);
                MediaDeviceViewHolderLegacy.this.mVolumeValueText.setVisibility(0);
                int progress = seekBar.getProgress();
                int i = MediaOutputSeekbar.$r8$clinit;
                this.mStartFromMute = progress / 1000 == 0;
                MediaOutputAdapterLegacy.this.mIsDragging = true;
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress();
                int i = MediaOutputSeekbar.$r8$clinit;
                if (progress / 1000 == 0) {
                    seekBar.setProgress(0);
                    MediaDeviceViewHolderLegacy.this.updateMutedVolumeIcon(this.mMediaDevice);
                } else {
                    MediaDeviceViewHolderLegacy.this.updateUnmutedVolumeIcon(this.mMediaDevice);
                }
                MediaDeviceViewHolderLegacy.this.mTitleIcon.setVisibility(0);
                MediaDeviceViewHolderLegacy.this.mVolumeValueText.setVisibility(8);
                MediaOutputAdapterLegacy.this.mIsDragging = false;
            }

            public boolean shouldHandleProgressChanged() {
                return this.mMediaDevice != null;
            }
        }

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public interface SeekBarVolumeControl {
            int getVolume();

            void onMute();

            void onUnmute();

            void setVolume(int i);
        }

        public MediaDeviceViewHolderLegacy(View view, Context context) {
            super(view, context);
            this.mLatestUpdateVolume = -1;
            this.mContainerLayout = (ViewGroup) view.requireViewById(R.id.device_container);
            this.mItemLayout = (FrameLayout) view.requireViewById(R.id.item_layout);
            this.mTextContent = (ViewGroup) view.requireViewById(R.id.text_content);
            this.mTitleText = (TextView) view.requireViewById(R.id.title);
            this.mSubTitleText = (TextView) view.requireViewById(R.id.subtitle);
            this.mTitleIcon = (ImageView) view.requireViewById(R.id.title_icon);
            this.mProgressBar = (ProgressBar) view.requireViewById(R.id.volume_indeterminate_progress);
            this.mSeekBar = (MediaOutputSeekbar) view.requireViewById(R.id.volume_seekbar);
            this.mStatusIcon = (ImageView) view.requireViewById(R.id.media_output_item_status);
            this.mCheckBox = (CheckBox) view.requireViewById(R.id.check_box);
            this.mEndTouchArea = (ViewGroup) view.requireViewById(R.id.end_action_area);
            this.mEndClickIcon = (ImageButton) view.requireViewById(R.id.end_area_image_button);
            this.mVolumeValueText = (TextView) view.requireViewById(R.id.volume_value);
            this.mIconAreaLayout = (FrameLayout) view.requireViewById(R.id.icon_area);
            float dimension = this.mContext.getResources().getDimension(R.dimen.media_output_dialog_background_radius);
            float dimension2 = this.mContext.getResources().getDimension(R.dimen.media_output_dialog_active_background_radius);
            this.mActiveRadius = dimension2;
            ValueAnimator ofFloat = ValueAnimator.ofFloat(dimension, dimension2);
            this.mCornerAnimator = ofFloat;
            ofFloat.setDuration(500L);
            this.mCornerAnimator.setInterpolator(new LinearInterpolator());
            ValueAnimator ofInt = ValueAnimator.ofInt(new int[0]);
            this.mVolumeAnimator = ofInt;
            ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.media.dialog.MediaOutputAdapterLegacy$MediaDeviceViewHolderLegacy$$ExternalSyntheticLambda0
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    MediaOutputAdapterLegacy.MediaDeviceViewHolderLegacy mediaDeviceViewHolderLegacy = MediaOutputAdapterLegacy.MediaDeviceViewHolderLegacy.this;
                    int i = MediaOutputAdapterLegacy.MediaDeviceViewHolderLegacy.$r8$clinit;
                    mediaDeviceViewHolderLegacy.getClass();
                    mediaDeviceViewHolderLegacy.mSeekBar.setProgress(((Integer) valueAnimator.getAnimatedValue()).intValue());
                }
            });
            this.mVolumeAnimator.setDuration(500L);
            this.mVolumeAnimator.setInterpolator(new LinearInterpolator());
            this.mVolumeAnimator.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.media.dialog.MediaOutputAdapterLegacy.MediaDeviceViewHolderLegacy.5
                @Override // android.animation.Animator.AnimatorListener
                public final void onAnimationCancel(Animator animator) {
                    MediaDeviceViewHolderLegacy.this.mSeekBar.setEnabled(true);
                }

                @Override // android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    MediaDeviceViewHolderLegacy.this.mSeekBar.setEnabled(true);
                }

                @Override // android.animation.Animator.AnimatorListener
                public final void onAnimationStart(Animator animator) {
                    MediaDeviceViewHolderLegacy.this.mSeekBar.setEnabled(false);
                }

                @Override // android.animation.Animator.AnimatorListener
                public final void onAnimationRepeat(Animator animator) {
                }
            });
        }

        @Override // com.android.systemui.media.dialog.MediaOutputAdapterBase.MediaDeviceViewHolderBase
        public final void disableSeekBar() {
            this.mSeekBar.setEnabled(false);
            this.mSeekBar.setOnTouchListener(new MediaOutputAdapterLegacy$MediaDeviceViewHolderLegacy$$ExternalSyntheticLambda3(1));
            this.mIconAreaLayout.setOnClickListener(null);
            this.mIconAreaLayout.setClickable(false);
        }

        public int getDrawableId(boolean z, boolean z2) {
            return z2 ? R.drawable.media_output_icon_volume_off : R.drawable.media_output_icon_volume;
        }

        public final void initializeSeekbarVolume(MediaDevice mediaDevice, int i) {
            int i2;
            if (MediaOutputAdapterLegacy.this.mIsDragging) {
                return;
            }
            if (this.mSeekBar.getProgress() / 1000 == i || !((i2 = this.mLatestUpdateVolume) == -1 || i == i2)) {
                if (i == 0) {
                    MediaOutputSeekbar mediaOutputSeekbar = this.mSeekBar;
                    mediaOutputSeekbar.setProgress(mediaOutputSeekbar.getMin());
                    updateMutedVolumeIcon(mediaDevice);
                }
            } else if (!this.mVolumeAnimator.isStarted()) {
                if (i == 0) {
                    updateMutedVolumeIcon(mediaDevice);
                } else {
                    updateUnmutedVolumeIcon(mediaDevice);
                }
                this.mSeekBar.setProgress(i * 1000, true);
                this.mLatestUpdateVolume = -1;
            }
            if (i == this.mLatestUpdateVolume) {
                this.mLatestUpdateVolume = -1;
            }
        }

        @Override // com.android.systemui.media.dialog.MediaOutputAdapterBase.MediaDeviceViewHolderBase
        public final void renderDeviceGroupItem() {
            boolean z;
            MediaOutputAdapterLegacy mediaOutputAdapterLegacy = MediaOutputAdapterLegacy.this;
            CharSequence name = mediaOutputAdapterLegacy.mController.mLocalMediaManager.mInfoMediaManager.getActiveRoutingSession().getName();
            MediaSwitchingController mediaSwitchingController = mediaOutputAdapterLegacy.mController;
            String charSequence = name == null ? "" : mediaSwitchingController.mLocalMediaManager.mInfoMediaManager.getActiveRoutingSession().getName().toString();
            this.mTitleText.setText(charSequence);
            MediaDevice mediaDevice = null;
            updateUnmutedVolumeIcon(null);
            String string = this.mContext.getString(R.string.accessibility_cast_name, charSequence);
            updateSeekbarProgressBackground();
            this.mSeekBar.setVisibility(0);
            SeekBarVolumeControl seekBarVolumeControl = new SeekBarVolumeControl() { // from class: com.android.systemui.media.dialog.MediaOutputAdapterLegacy.MediaDeviceViewHolderLegacy.3
                @Override // com.android.systemui.media.dialog.MediaOutputAdapterLegacy.MediaDeviceViewHolderLegacy.SeekBarVolumeControl
                public final int getVolume() {
                    return MediaOutputAdapterLegacy.this.mController.mLocalMediaManager.mInfoMediaManager.getActiveRoutingSession().getVolume();
                }

                @Override // com.android.systemui.media.dialog.MediaOutputAdapterLegacy.MediaDeviceViewHolderLegacy.SeekBarVolumeControl
                public final void setVolume(int i) {
                    InfoMediaManager infoMediaManager = MediaOutputAdapterLegacy.this.mController.mLocalMediaManager.mInfoMediaManager;
                    infoMediaManager.getClass();
                    Log.d("InfoMediaManager", "adjustSessionVolume() adjust volume: " + i + ", with : " + infoMediaManager.mPackageName);
                    infoMediaManager.setSessionVolume(infoMediaManager.getActiveRoutingSession(), i);
                }

                @Override // com.android.systemui.media.dialog.MediaOutputAdapterLegacy.MediaDeviceViewHolderLegacy.SeekBarVolumeControl
                public final void onMute() {
                }

                @Override // com.android.systemui.media.dialog.MediaOutputAdapterLegacy.MediaDeviceViewHolderLegacy.SeekBarVolumeControl
                public final void onUnmute() {
                }
            };
            InfoMediaManager infoMediaManager = mediaSwitchingController.mLocalMediaManager.mInfoMediaManager;
            Iterator it = infoMediaManager.getRoutingSessionsForPackage().iterator();
            while (true) {
                if (!it.hasNext()) {
                    ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder("No routing session for "), infoMediaManager.mPackageName, "InfoMediaManager");
                    z = false;
                    break;
                } else {
                    RoutingSessionInfo routingSessionInfo = (RoutingSessionInfo) it.next();
                    if (!routingSessionInfo.isSystemSession() && routingSessionInfo.getVolumeHandling() != 0) {
                        z = true;
                        break;
                    }
                }
            }
            if (z) {
                this.mSeekBar.setEnabled(true);
                this.mSeekBar.setOnTouchListener(new MediaOutputAdapterLegacy$MediaDeviceViewHolderLegacy$$ExternalSyntheticLambda3(0));
                this.mIconAreaLayout.setOnClickListener(new MediaOutputAdapterLegacy$MediaDeviceViewHolderLegacy$$ExternalSyntheticLambda4(this, seekBarVolumeControl, 0));
            } else {
                disableSeekBar();
            }
            this.mSeekBar.setMax(mediaSwitchingController.mLocalMediaManager.mInfoMediaManager.getActiveRoutingSession().getVolumeMax() * 1000);
            initializeSeekbarVolume(null, mediaSwitchingController.mLocalMediaManager.mInfoMediaManager.getActiveRoutingSession().getVolume());
            this.mSeekBar.mOnSeekBarChangeListener = new MediaSeekBarChangedListener(this, mediaDevice, seekBarVolumeControl) { // from class: com.android.systemui.media.dialog.MediaOutputAdapterLegacy.MediaDeviceViewHolderLegacy.4
                @Override // com.android.systemui.media.dialog.MediaOutputAdapterLegacy.MediaDeviceViewHolderLegacy.MediaSeekBarChangedListener
                public final boolean shouldHandleProgressChanged() {
                    return true;
                }
            };
            updateContainerContentA11yImportance(false);
            this.mSeekBar.setContentDescription(string);
            updateEndAreaWithIcon(R.drawable.media_output_item_expand_group, R.string.accessibility_expand_group, new MediaOutputAdapterLegacy$MediaDeviceViewHolderLegacy$$ExternalSyntheticLambda1(this, 1));
            updateEndAreaVisibility(true, false);
            updateItemBackground(MediaOutputAdapterBase.ConnectionState.CONNECTED);
        }

        @Override // com.android.systemui.media.dialog.MediaOutputAdapterBase.MediaDeviceViewHolderBase
        public final void renderDeviceItem(boolean z, final MediaDevice mediaDevice, MediaOutputAdapterBase.ConnectionState connectionState, boolean z2, final MediaOutputAdapterBase.GroupStatus groupStatus, MediaOutputAdapterBase.OngoingSessionStatus ongoingSessionStatus, View.OnClickListener onClickListener, boolean z3, String str, Drawable drawable) {
            boolean z4;
            boolean z5;
            if (z) {
                this.mItemLayout.setVisibility(8);
                return;
            }
            this.mTitleText.setText(mediaDevice.getName());
            MediaOutputAdapterBase.ConnectionState connectionState2 = MediaOutputAdapterBase.ConnectionState.CONNECTED;
            MediaOutputAdapterLegacy mediaOutputAdapterLegacy = MediaOutputAdapterLegacy.this;
            if (connectionState != connectionState2) {
                mediaOutputAdapterLegacy.mBackgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.media.dialog.MediaOutputAdapterLegacy$MediaDeviceViewHolderLegacy$$ExternalSyntheticLambda8
                    @Override // java.lang.Runnable
                    public final void run() {
                        Bitmap createBitmap;
                        final MediaOutputAdapterLegacy.MediaDeviceViewHolderLegacy mediaDeviceViewHolderLegacy = MediaOutputAdapterLegacy.MediaDeviceViewHolderLegacy.this;
                        final MediaDevice mediaDevice2 = mediaDevice;
                        MediaOutputAdapterLegacy mediaOutputAdapterLegacy2 = MediaOutputAdapterLegacy.this;
                        MediaSwitchingController mediaSwitchingController = mediaOutputAdapterLegacy2.mController;
                        mediaSwitchingController.getClass();
                        Drawable icon = mediaDevice2.getIcon();
                        if (icon == null) {
                            if (MediaSwitchingController.DEBUG) {
                                Log.d("MediaSwitchingController", "getDeviceIconCompat() device : " + mediaDevice2.getName() + ", drawable is null");
                            }
                            icon = mediaSwitchingController.mContext.getDrawable(android.R.drawable.ic_doc_image);
                        }
                        boolean z6 = BluetoothUtils.DEBUG;
                        if (icon instanceof BitmapDrawable) {
                            createBitmap = ((BitmapDrawable) icon).getBitmap();
                        } else {
                            int intrinsicWidth = icon.getIntrinsicWidth();
                            int intrinsicHeight = icon.getIntrinsicHeight();
                            if (intrinsicWidth <= 0) {
                                intrinsicWidth = 1;
                            }
                            if (intrinsicHeight <= 0) {
                                intrinsicHeight = 1;
                            }
                            createBitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888);
                            Canvas canvas = new Canvas(createBitmap);
                            icon.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                            icon.draw(canvas);
                        }
                        final Icon icon$1 = IconCompat.createWithBitmap(createBitmap).toIcon$1();
                        mediaOutputAdapterLegacy2.mMainExecutor.execute(new Runnable() { // from class: com.android.systemui.media.dialog.MediaOutputAdapterLegacy$MediaDeviceViewHolderLegacy$$ExternalSyntheticLambda10
                            @Override // java.lang.Runnable
                            public final void run() {
                                MediaOutputAdapterLegacy.MediaDeviceViewHolderLegacy mediaDeviceViewHolderLegacy2 = MediaOutputAdapterLegacy.MediaDeviceViewHolderLegacy.this;
                                MediaDevice mediaDevice3 = mediaDevice2;
                                Icon icon2 = icon$1;
                                if (TextUtils.equals(mediaDeviceViewHolderLegacy2.mDeviceId, mediaDevice3.getId())) {
                                    mediaDeviceViewHolderLegacy2.mTitleIcon.setImageIcon(icon2);
                                    mediaDeviceViewHolderLegacy2.mTitleIcon.setImageTintList(ColorStateList.valueOf(MediaOutputAdapterLegacy.this.mController.mMediaOutputColorSchemeLegacy.getColorItemContent()));
                                }
                            }
                        });
                    }
                });
            } else if (z2) {
                updateVolumeIcon(mediaDevice, false);
            } else {
                updateUnmutedVolumeIcon(mediaDevice);
            }
            String string = this.mContext.getString(mediaDevice.getDeviceType() == 5 ? R.string.accessibility_bluetooth_name : R.string.accessibility_cast_name, mediaDevice.getName());
            boolean z6 = connectionState == connectionState2 && !z2;
            if (!this.mCornerAnimator.isRunning() && z6) {
                updateSeekbarProgressBackground();
            }
            this.mSeekBar.setVisibility(z6 ? 0 : 8);
            if (z6) {
                SeekBarVolumeControl seekBarVolumeControl = new SeekBarVolumeControl() { // from class: com.android.systemui.media.dialog.MediaOutputAdapterLegacy.MediaDeviceViewHolderLegacy.1
                    @Override // com.android.systemui.media.dialog.MediaOutputAdapterLegacy.MediaDeviceViewHolderLegacy.SeekBarVolumeControl
                    public final int getVolume() {
                        return mediaDevice.getCurrentVolume();
                    }

                    @Override // com.android.systemui.media.dialog.MediaOutputAdapterLegacy.MediaDeviceViewHolderLegacy.SeekBarVolumeControl
                    public final void onMute() {
                        MediaOutputMetricLogger mediaOutputMetricLogger = MediaOutputAdapterLegacy.this.mController.mMetricLogger;
                        if (MediaOutputMetricLogger.DEBUG) {
                            mediaOutputMetricLogger.getClass();
                            Log.d("MediaOutputMetricLogger", "logInteraction - Mute");
                        }
                        mediaOutputMetricLogger.getClass();
                        MediaDevice mediaDevice2 = mediaDevice;
                        SysUiStatsLog.write(mediaOutputMetricLogger.getLoggingPackageName(), 3, MediaOutputMetricLogger.getInteractionDeviceType(mediaDevice2), mediaDevice2.isSuggestedDevice());
                    }

                    @Override // com.android.systemui.media.dialog.MediaOutputAdapterLegacy.MediaDeviceViewHolderLegacy.SeekBarVolumeControl
                    public final void onUnmute() {
                        MediaOutputMetricLogger mediaOutputMetricLogger = MediaOutputAdapterLegacy.this.mController.mMetricLogger;
                        if (MediaOutputMetricLogger.DEBUG) {
                            mediaOutputMetricLogger.getClass();
                            Log.d("MediaOutputMetricLogger", "logInteraction - Unmute");
                        }
                        mediaOutputMetricLogger.getClass();
                        MediaDevice mediaDevice2 = mediaDevice;
                        SysUiStatsLog.write(mediaOutputMetricLogger.getLoggingPackageName(), 4, MediaOutputMetricLogger.getInteractionDeviceType(mediaDevice2), mediaDevice2.isSuggestedDevice());
                    }

                    @Override // com.android.systemui.media.dialog.MediaOutputAdapterLegacy.MediaDeviceViewHolderLegacy.SeekBarVolumeControl
                    public final void setVolume(final int i) {
                        final MediaSwitchingController mediaSwitchingController = MediaOutputAdapterLegacy.this.mController;
                        mediaSwitchingController.getClass();
                        final MediaDevice mediaDevice2 = mediaDevice;
                        ThreadUtils.postOnBackgroundThread(new Runnable() { // from class: com.android.systemui.media.dialog.MediaSwitchingController$$ExternalSyntheticLambda3
                            @Override // java.lang.Runnable
                            public final void run() {
                                MediaSwitchingController mediaSwitchingController2 = MediaSwitchingController.this;
                                MediaDevice mediaDevice3 = mediaDevice2;
                                int i2 = i;
                                InfoMediaManager infoMediaManager = mediaSwitchingController2.mLocalMediaManager.mInfoMediaManager;
                                infoMediaManager.getClass();
                                MediaRoute2Info mediaRoute2Info = mediaDevice3.mRouteInfo;
                                if (mediaRoute2Info == null) {
                                    Log.w("InfoMediaManager", "Unable to set volume. RouteInfo is empty");
                                } else {
                                    infoMediaManager.setRouteVolume(mediaRoute2Info, i2);
                                }
                            }
                        });
                    }
                };
                mediaOutputAdapterLegacy.mController.getClass();
                if (mediaDevice.isVolumeFixed()) {
                    disableSeekBar();
                } else {
                    this.mSeekBar.setEnabled(true);
                    this.mSeekBar.setOnTouchListener(new MediaOutputAdapterLegacy$MediaDeviceViewHolderLegacy$$ExternalSyntheticLambda3(0));
                    this.mIconAreaLayout.setOnClickListener(new MediaOutputAdapterLegacy$MediaDeviceViewHolderLegacy$$ExternalSyntheticLambda4(this, seekBarVolumeControl, 0));
                }
                this.mSeekBar.setMax(mediaDevice.getMaxVolume() * 1000);
                initializeSeekbarVolume(mediaDevice, mediaDevice.getCurrentVolume());
                this.mSeekBar.mOnSeekBarChangeListener = new MediaSeekBarChangedListener(mediaDevice, seekBarVolumeControl) { // from class: com.android.systemui.media.dialog.MediaOutputAdapterLegacy.MediaDeviceViewHolderLegacy.2
                    @Override // com.android.systemui.media.dialog.MediaOutputAdapterLegacy.MediaDeviceViewHolderLegacy.MediaSeekBarChangedListener, android.widget.SeekBar.OnSeekBarChangeListener
                    public final void onStopTrackingTouch(SeekBar seekBar) {
                        super.onStopTrackingTouch(seekBar);
                        MediaSwitchingController mediaSwitchingController = MediaOutputAdapterLegacy.this.mController;
                        MediaDevice mediaDevice2 = mediaDevice;
                        MediaOutputMetricLogger mediaOutputMetricLogger = mediaSwitchingController.mMetricLogger;
                        if (MediaOutputMetricLogger.DEBUG) {
                            mediaOutputMetricLogger.getClass();
                            Log.d("MediaOutputMetricLogger", "logInteraction - AdjustVolume");
                        }
                        mediaOutputMetricLogger.getClass();
                        SysUiStatsLog.write(mediaOutputMetricLogger.getLoggingPackageName(), 1, MediaOutputMetricLogger.getInteractionDeviceType(mediaDevice2), mediaDevice2.isSuggestedDevice());
                    }
                };
                updateContainerContentA11yImportance(false);
                this.mSeekBar.setContentDescription(string);
            } else {
                updateContainerContentA11yImportance(true);
            }
            if (ongoingSessionStatus == null || connectionState != connectionState2) {
                if (groupStatus != null) {
                    if (!(groupStatus.selected.booleanValue() && !groupStatus.deselectable.booleanValue())) {
                        boolean z7 = groupStatus.selected.booleanValue() && !groupStatus.deselectable.booleanValue();
                        boolean z8 = !z7;
                        this.mEndTouchArea.setBackgroundTintList(ColorStateList.valueOf(groupStatus.selected.booleanValue() ? mediaOutputAdapterLegacy.mController.mMediaOutputColorSchemeLegacy.getColorSeekbarProgress() : mediaOutputAdapterLegacy.mController.mMediaOutputColorSchemeLegacy.getColorItemBackground()));
                        this.mCheckBox.setContentDescription(this.mContext.getString(groupStatus.selected.booleanValue() ? R.string.accessibility_remove_device_from_group : R.string.accessibility_add_device_to_group));
                        this.mCheckBox.setOnCheckedChangeListener(null);
                        this.mCheckBox.setChecked(groupStatus.selected.booleanValue());
                        this.mCheckBox.setOnCheckedChangeListener(z7 ? null : new CompoundButton.OnCheckedChangeListener() { // from class: com.android.systemui.media.dialog.MediaOutputAdapterLegacy$MediaDeviceViewHolderLegacy$$ExternalSyntheticLambda7
                            @Override // android.widget.CompoundButton.OnCheckedChangeListener
                            public final void onCheckedChanged(CompoundButton compoundButton, boolean z9) {
                                MediaOutputAdapterLegacy.MediaDeviceViewHolderLegacy mediaDeviceViewHolderLegacy = MediaOutputAdapterLegacy.MediaDeviceViewHolderLegacy.this;
                                MediaOutputAdapterBase.GroupStatus groupStatus2 = groupStatus;
                                MediaDevice mediaDevice2 = mediaDevice;
                                int i = MediaOutputAdapterLegacy.MediaDeviceViewHolderLegacy.$r8$clinit;
                                mediaDeviceViewHolderLegacy.getClass();
                                boolean booleanValue = groupStatus2.selected.booleanValue();
                                mediaDeviceViewHolderLegacy.disableSeekBar();
                                MediaOutputAdapterBase mediaOutputAdapterBase = MediaOutputAdapterBase.this;
                                if (booleanValue || !MediaOutputAdapterBase.isDeviceIncluded(mediaOutputAdapterBase.mController.getSelectableMediaDevice(), mediaDevice2)) {
                                    if (booleanValue && MediaOutputAdapterBase.isDeviceIncluded(mediaOutputAdapterBase.mController.getDeselectableMediaDevice(), mediaDevice2)) {
                                        LocalMediaManager localMediaManager = mediaOutputAdapterBase.mController.mLocalMediaManager;
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
                                    return;
                                }
                                MediaSwitchingController mediaSwitchingController = mediaOutputAdapterBase.mController;
                                MediaOutputMetricLogger mediaOutputMetricLogger = mediaSwitchingController.mMetricLogger;
                                if (MediaOutputMetricLogger.DEBUG) {
                                    mediaOutputMetricLogger.getClass();
                                    Log.d("MediaOutputMetricLogger", "logInteraction - Expansion");
                                }
                                mediaOutputMetricLogger.getClass();
                                SysUiStatsLog.write(mediaOutputMetricLogger.getLoggingPackageName(), 0, MediaOutputMetricLogger.getInteractionDeviceType(mediaDevice2), mediaDevice2.isSuggestedDevice());
                                LocalMediaManager localMediaManager2 = mediaSwitchingController.mLocalMediaManager;
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
                        });
                        this.mCheckBox.setEnabled(z8);
                        this.mCheckBox.setForegroundTintList(ColorStateList.valueOf(mediaOutputAdapterLegacy.mController.mMediaOutputColorSchemeLegacy.getColorItemContent()));
                        z4 = true;
                        z5 = z4;
                    }
                }
                z4 = false;
                z5 = z4;
            } else {
                updateEndAreaWithIcon(ongoingSessionStatus.host ? R.drawable.media_output_status_edit_session : R.drawable.ic_sound_bars_anim, R.string.accessibility_open_application, new MediaOutputAdapterLegacy$MediaDeviceViewHolderLegacy$$ExternalSyntheticLambda4(this, mediaDevice, 1));
                z5 = false;
                z4 = true;
            }
            updateEndAreaVisibility(z4, z5);
            if (connectionState == MediaOutputAdapterBase.ConnectionState.CONNECTING) {
                this.mProgressBar.setVisibility(0);
                this.mProgressBar.getIndeterminateDrawable().setTintList(ColorStateList.valueOf(mediaOutputAdapterLegacy.mController.mMediaOutputColorSchemeLegacy.getColorItemContent()));
            } else {
                this.mProgressBar.setVisibility(8);
            }
            this.mContainerLayout.setOnClickListener(onClickListener);
            float f = z3 ? 0.5f : 1.0f;
            this.mTitleIcon.setAlpha(f);
            this.mTitleText.setAlpha(f);
            this.mSubTitleText.setAlpha(f);
            this.mStatusIcon.setAlpha(f);
            if (str == null) {
                this.mSubTitleText.setVisibility(8);
            } else {
                this.mSubTitleText.setText(str);
                this.mSubTitleText.setVisibility(0);
            }
            boolean z9 = ongoingSessionStatus != null && connectionState == MediaOutputAdapterBase.ConnectionState.DISCONNECTED;
            if (drawable != null || z9) {
                if (z9) {
                    this.mStatusIcon.setImageDrawable(this.mContext.getDrawable(R.drawable.ic_sound_bars_anim));
                } else {
                    this.mStatusIcon.setImageDrawable(drawable);
                }
                this.mStatusIcon.setImageTintList(ColorStateList.valueOf(mediaOutputAdapterLegacy.mController.mMediaOutputColorSchemeLegacy.getColorItemContent()));
                if (drawable instanceof AnimatedVectorDrawable) {
                    ((AnimatedVectorDrawable) drawable).start();
                }
                this.mStatusIcon.setVisibility(0);
            } else {
                this.mStatusIcon.setVisibility(8);
            }
            updateItemBackground(connectionState);
        }

        public final void updateContainerContentA11yImportance(boolean z) {
            this.mContainerLayout.setFocusable(z);
            this.mContainerLayout.setImportantForAccessibility(z ? 1 : 2);
            this.mTextContent.setImportantForAccessibility(z ? 1 : 4);
        }

        public final void updateEndAreaVisibility(boolean z, boolean z2) {
            this.mEndTouchArea.setVisibility(z ? 0 : 8);
            if (z) {
                this.mCheckBox.setVisibility(z2 ? 0 : 8);
                this.mEndClickIcon.setVisibility(z2 ? 8 : 0);
            }
        }

        public final void updateEndAreaWithIcon(int i, int i2, View.OnClickListener onClickListener) {
            MediaOutputAdapterLegacy mediaOutputAdapterLegacy = MediaOutputAdapterLegacy.this;
            this.mEndTouchArea.setBackgroundTintList(ColorStateList.valueOf(mediaOutputAdapterLegacy.mController.mMediaOutputColorSchemeLegacy.getColorSeekbarProgress()));
            this.mEndClickIcon.setImageTintList(ColorStateList.valueOf(mediaOutputAdapterLegacy.mController.mMediaOutputColorSchemeLegacy.getColorItemContent()));
            this.mEndClickIcon.setOnClickListener(onClickListener);
            Drawable drawable = this.mContext.getDrawable(i);
            this.mEndClickIcon.setImageDrawable(drawable);
            if (drawable instanceof AnimatedVectorDrawable) {
                ((AnimatedVectorDrawable) drawable).start();
            }
            this.mEndClickIcon.setContentDescription(this.mContext.getString(i2));
        }

        public final void updateItemBackground(MediaOutputAdapterBase.ConnectionState connectionState) {
            boolean z = connectionState == MediaOutputAdapterBase.ConnectionState.CONNECTED;
            boolean z2 = connectionState == MediaOutputAdapterBase.ConnectionState.CONNECTING;
            if (!this.mCornerAnimator.isRunning()) {
                this.mItemLayout.setBackground(this.mContext.getDrawable(z ? R.drawable.media_output_item_background_active : R.drawable.media_output_item_background).mutate());
            }
            MediaOutputAdapterLegacy mediaOutputAdapterLegacy = MediaOutputAdapterLegacy.this;
            this.mItemLayout.setBackgroundTintList(ColorStateList.valueOf((z || z2) ? mediaOutputAdapterLegacy.mController.mMediaOutputColorSchemeLegacy.getColorConnectedItemBackground() : mediaOutputAdapterLegacy.mController.mMediaOutputColorSchemeLegacy.getColorItemBackground()));
        }

        public final void updateMutedVolumeIcon(MediaDevice mediaDevice) {
            this.mIconAreaLayout.setBackground(this.mContext.getDrawable(R.drawable.media_output_item_background_active));
            updateVolumeIcon(mediaDevice, true);
        }

        public final void updateSeekbarProgressBackground() {
            GradientDrawable gradientDrawable = (GradientDrawable) ((ClipDrawable) ((LayerDrawable) this.mSeekBar.getProgressDrawable()).findDrawableByLayerId(android.R.id.progress)).getDrawable();
            float f = this.mActiveRadius;
            gradientDrawable.setCornerRadii(new float[]{0.0f, 0.0f, f, f, f, f, 0.0f, 0.0f});
        }

        public final void updateUnmutedVolumeIcon(MediaDevice mediaDevice) {
            this.mIconAreaLayout.setBackground(this.mContext.getDrawable(R.drawable.media_output_title_icon_area));
            updateVolumeIcon(mediaDevice, false);
        }

        public final void updateVolumeIcon(MediaDevice mediaDevice, boolean z) {
            this.mTitleIcon.setImageDrawable(this.mContext.getDrawable(getDrawableId(mediaDevice instanceof InputMediaDevice, z)));
            ImageView imageView = this.mTitleIcon;
            MediaOutputAdapterLegacy mediaOutputAdapterLegacy = MediaOutputAdapterLegacy.this;
            imageView.setImageTintList(ColorStateList.valueOf(mediaOutputAdapterLegacy.mController.mMediaOutputColorSchemeLegacy.getColorItemContent()));
            this.mIconAreaLayout.setBackgroundTintList(ColorStateList.valueOf(mediaOutputAdapterLegacy.mController.mMediaOutputColorSchemeLegacy.getColorSeekbarProgress()));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class MediaGroupDividerViewHolderLegacy extends RecyclerView.ViewHolder {
        public final TextView mTitleText;

        public MediaGroupDividerViewHolderLegacy(View view) {
            super(view);
            this.mTitleText = (TextView) view.requireViewById(R.id.title);
        }
    }

    public MediaOutputAdapterLegacy(MediaSwitchingController mediaSwitchingController, Executor executor, Executor executor2) {
        super(mediaSwitchingController);
        this.mMainExecutor = executor;
        this.mBackgroundExecutor = executor2;
    }

    /* JADX WARN: Removed duplicated region for block: B:121:0x0346  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x0369  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x03b5 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:72:0x03bb  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x03bf  */
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onBindViewHolder(androidx.recyclerview.widget.RecyclerView.ViewHolder r22, int r23) {
        /*
            Method dump skipped, instructions count: 966
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.dialog.MediaOutputAdapterLegacy.onBindViewHolder(androidx.recyclerview.widget.RecyclerView$ViewHolder, int):void");
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        this.mHolderView = LayoutInflater.from(viewGroup.getContext()).inflate((i == 0 || i == 2) ? R.layout.media_output_list_item_advanced : R.layout.media_output_list_group_divider, viewGroup, false);
        return i != 1 ? new MediaDeviceViewHolderLegacy(this.mHolderView, context) : new MediaGroupDividerViewHolderLegacy(this.mHolderView);
    }
}
