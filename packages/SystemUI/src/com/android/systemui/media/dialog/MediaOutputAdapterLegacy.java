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
import android.media.AudioManager;
import android.media.MediaRoute2Info;
import android.media.RouteListingPreference;
import android.media.RoutingSessionInfo;
import android.os.UserHandle;
import android.os.UserManager;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
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
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.core.graphics.drawable.IconCompat;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.media.InfoMediaDevice;
import com.android.settingslib.media.InfoMediaManager;
import com.android.settingslib.media.InputMediaDevice;
import com.android.settingslib.media.LocalMediaManager;
import com.android.settingslib.media.MediaDevice;
import com.android.settingslib.utils.ThreadUtils;
import com.android.systemui.R;
import com.android.systemui.media.dialog.MediaOutputAdapterBase;
import com.android.systemui.media.dialog.MediaOutputAdapterLegacy;
import com.android.systemui.shared.system.SysUiStatsLog;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;

/* loaded from: classes2.dex */
public class MediaOutputAdapterLegacy extends MediaOutputAdapterBase {
    public static final boolean DEBUG = Log.isLoggable("MediaOutputAdapterL", 3);
    static final float DEVICE_ACTIVE_ALPHA = 1.0f;
    static final float DEVICE_DISABLED_ALPHA = 0.5f;
    public final Executor mBackgroundExecutor;
    public View mHolderView;
    public final Executor mMainExecutor;

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

        public abstract class MediaSeekBarChangedListener implements SeekBar.OnSeekBarChangeListener {
            public final MediaDevice mMediaDevice;
            public boolean mStartFromMute = false;
            public final SeekBarVolumeControl mVolumeControl;

            public MediaSeekBarChangedListener(MediaDevice mediaDevice, SeekBarVolumeControl seekBarVolumeControl) {
                this.mMediaDevice = mediaDevice;
                this.mVolumeControl = seekBarVolumeControl;
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public final void onProgressChanged(SeekBar seekBar, int i, boolean z) throws Resources.NotFoundException {
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

        public interface SeekBarVolumeControl {
            int getVolume();

            void onMute();

            void onUnmute();

            void setVolume(int i);
        }

        public MediaDeviceViewHolderLegacy(View view, Context context) throws Resources.NotFoundException {
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
            ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(dimension, dimension2);
            this.mCornerAnimator = valueAnimatorOfFloat;
            valueAnimatorOfFloat.setDuration(500L);
            this.mCornerAnimator.setInterpolator(new LinearInterpolator());
            ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(new int[0]);
            this.mVolumeAnimator = valueAnimatorOfInt;
            valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.media.dialog.MediaOutputAdapterLegacy$MediaDeviceViewHolderLegacy$$ExternalSyntheticLambda0
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    MediaOutputAdapterLegacy.MediaDeviceViewHolderLegacy mediaDeviceViewHolderLegacy = this.f$0;
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
            String string = name == null ? "" : mediaSwitchingController.mLocalMediaManager.mInfoMediaManager.getActiveRoutingSession().getName().toString();
            this.mTitleText.setText(string);
            MediaDevice mediaDevice = null;
            updateUnmutedVolumeIcon(null);
            String string2 = this.mContext.getString(R.string.accessibility_cast_name, string);
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
            this.mSeekBar.setContentDescription(string2);
            updateEndAreaWithIcon(R.drawable.media_output_item_expand_group, R.string.accessibility_expand_group, new MediaOutputAdapterLegacy$MediaDeviceViewHolderLegacy$$ExternalSyntheticLambda1(this, 1));
            updateEndAreaVisibility(true, false);
            updateItemBackground(MediaOutputAdapterBase.ConnectionState.CONNECTED);
        }

        /* JADX WARN: Removed duplicated region for block: B:72:0x018d  */
        @Override // com.android.systemui.media.dialog.MediaOutputAdapterBase.MediaDeviceViewHolderBase
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
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
                        Bitmap bitmapCreateBitmap;
                        final MediaOutputAdapterLegacy.MediaDeviceViewHolderLegacy mediaDeviceViewHolderLegacy = this.f$0;
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
                            bitmapCreateBitmap = ((BitmapDrawable) icon).getBitmap();
                        } else {
                            int intrinsicWidth = icon.getIntrinsicWidth();
                            int intrinsicHeight = icon.getIntrinsicHeight();
                            if (intrinsicWidth <= 0) {
                                intrinsicWidth = 1;
                            }
                            if (intrinsicHeight <= 0) {
                                intrinsicHeight = 1;
                            }
                            bitmapCreateBitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888);
                            Canvas canvas = new Canvas(bitmapCreateBitmap);
                            icon.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                            icon.draw(canvas);
                        }
                        final Icon icon$1 = IconCompat.createWithBitmap(bitmapCreateBitmap).toIcon$1();
                        mediaOutputAdapterLegacy2.mMainExecutor.execute(new Runnable() { // from class: com.android.systemui.media.dialog.MediaOutputAdapterLegacy$MediaDeviceViewHolderLegacy$$ExternalSyntheticLambda10
                            @Override // java.lang.Runnable
                            public final void run() {
                                MediaOutputAdapterLegacy.MediaDeviceViewHolderLegacy mediaDeviceViewHolderLegacy2 = mediaDeviceViewHolderLegacy;
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
                                MediaSwitchingController mediaSwitchingController2 = mediaSwitchingController;
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
            if (ongoingSessionStatus != null && connectionState == connectionState2) {
                updateEndAreaWithIcon(ongoingSessionStatus.host ? R.drawable.media_output_status_edit_session : R.drawable.ic_sound_bars_anim, R.string.accessibility_open_application, new MediaOutputAdapterLegacy$MediaDeviceViewHolderLegacy$$ExternalSyntheticLambda4(this, mediaDevice, 1));
                z5 = false;
                z4 = true;
            } else if (groupStatus == null) {
                z4 = false;
                z5 = z4;
            } else {
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
                            MediaOutputAdapterLegacy.MediaDeviceViewHolderLegacy mediaDeviceViewHolderLegacy = this.f$0;
                            MediaOutputAdapterBase.GroupStatus groupStatus2 = groupStatus;
                            MediaDevice mediaDevice2 = mediaDevice;
                            int i = MediaOutputAdapterLegacy.MediaDeviceViewHolderLegacy.$r8$clinit;
                            mediaDeviceViewHolderLegacy.getClass();
                            boolean zBooleanValue = groupStatus2.selected.booleanValue();
                            mediaDeviceViewHolderLegacy.disableSeekBar();
                            MediaOutputAdapterBase mediaOutputAdapterBase = MediaOutputAdapterBase.this;
                            if (zBooleanValue || !MediaOutputAdapterBase.isDeviceIncluded(mediaOutputAdapterBase.mController.getSelectableMediaDevice(), mediaDevice2)) {
                                if (zBooleanValue && MediaOutputAdapterBase.isDeviceIncluded(mediaOutputAdapterBase.mController.getDeselectableMediaDevice(), mediaDevice2)) {
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
                }
                z5 = z4;
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

    /* JADX WARN: Removed duplicated region for block: B:117:0x0334  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x0346  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x0369  */
    /* JADX WARN: Removed duplicated region for block: B:138:0x03b5 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:139:0x03b7  */
    /* JADX WARN: Removed duplicated region for block: B:141:0x03bb  */
    /* JADX WARN: Removed duplicated region for block: B:142:0x03bf  */
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        boolean z;
        boolean z2;
        String string;
        MediaOutputAdapterBase.ConnectionState connectionState;
        boolean z3;
        MediaOutputAdapterBase.OngoingSessionStatus ongoingSessionStatus;
        MediaOutputAdapterBase.GroupStatus groupStatus;
        boolean z4;
        boolean z5;
        View.OnClickListener mediaOutputAdapterBase$Api34Impl$$ExternalSyntheticLambda0;
        boolean z6;
        Drawable drawable;
        View.OnClickListener clickListenerBasedOnSelectionBehavior;
        Drawable drawable2;
        RouteListingPreference.Item item;
        if (i >= getItemCount()) {
            if (DEBUG) {
                StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Incorrect position: ", " list size: ");
                sbM.append(getItemCount());
                Log.d("MediaOutputAdapterL", sbM.toString());
                return;
            }
            return;
        }
        MediaItem mediaItem = (MediaItem) ((CopyOnWriteArrayList) this.mMediaItemList).get(i);
        int i2 = mediaItem.mMediaItemType;
        if (i2 != 0) {
            if (i2 == 1) {
                MediaGroupDividerViewHolderLegacy mediaGroupDividerViewHolderLegacy = (MediaGroupDividerViewHolderLegacy) viewHolder;
                mediaGroupDividerViewHolderLegacy.mTitleText.setTextColor(MediaOutputAdapterLegacy.this.mController.mMediaOutputColorSchemeLegacy.getColorItemContent());
                mediaGroupDividerViewHolderLegacy.mTitleText.setText(mediaItem.mTitle);
                return;
            }
            if (i2 != 2) {
                ListPopupWindow$$ExternalSyntheticOutline0.m(i, "Incorrect position: ", "MediaOutputAdapterL");
                return;
            }
            MediaDeviceViewHolderLegacy mediaDeviceViewHolderLegacy = (MediaDeviceViewHolderLegacy) viewHolder;
            TextView textView = mediaDeviceViewHolderLegacy.mTitleText;
            MediaOutputAdapterLegacy mediaOutputAdapterLegacy = MediaOutputAdapterLegacy.this;
            textView.setTextColor(mediaOutputAdapterLegacy.mController.mMediaOutputColorSchemeLegacy.getColorItemContent());
            mediaDeviceViewHolderLegacy.mCheckBox.setVisibility(8);
            mediaDeviceViewHolderLegacy.mTitleText.setText(mediaDeviceViewHolderLegacy.mContext.getText(R.string.media_output_dialog_pairing_new));
            mediaDeviceViewHolderLegacy.updateItemBackground(MediaOutputAdapterBase.ConnectionState.DISCONNECTED);
            mediaDeviceViewHolderLegacy.mTitleIcon.setImageDrawable(mediaDeviceViewHolderLegacy.mContext.getDrawable(R.drawable.ic_add));
            ImageView imageView = mediaDeviceViewHolderLegacy.mTitleIcon;
            MediaSwitchingController mediaSwitchingController = mediaOutputAdapterLegacy.mController;
            imageView.setImageTintList(ColorStateList.valueOf(mediaSwitchingController.mMediaOutputColorSchemeLegacy.getColorItemContent()));
            mediaDeviceViewHolderLegacy.mContainerLayout.setOnClickListener(new MediaOutputAdapterLegacy$MediaDeviceViewHolderLegacy$$ExternalSyntheticLambda1(mediaSwitchingController, 0));
            return;
        }
        final MediaDeviceViewHolderLegacy mediaDeviceViewHolderLegacy2 = (MediaDeviceViewHolderLegacy) viewHolder;
        mediaDeviceViewHolderLegacy2.mDeviceId = ((MediaDevice) mediaItem.mMediaDeviceOptional.get()).getId();
        mediaDeviceViewHolderLegacy2.mItemLayout.setVisibility(0);
        mediaDeviceViewHolderLegacy2.mCheckBox.setVisibility(8);
        mediaDeviceViewHolderLegacy2.mStatusIcon.setVisibility(8);
        mediaDeviceViewHolderLegacy2.mEndTouchArea.setVisibility(8);
        mediaDeviceViewHolderLegacy2.mEndClickIcon.setVisibility(8);
        mediaDeviceViewHolderLegacy2.mContainerLayout.setOnClickListener(null);
        TextView textView2 = mediaDeviceViewHolderLegacy2.mTitleText;
        MediaOutputAdapterLegacy mediaOutputAdapterLegacy2 = MediaOutputAdapterLegacy.this;
        textView2.setTextColor(mediaOutputAdapterLegacy2.mController.mMediaOutputColorSchemeLegacy.getColorItemContent());
        TextView textView3 = mediaDeviceViewHolderLegacy2.mSubTitleText;
        MediaSwitchingController mediaSwitchingController2 = mediaOutputAdapterLegacy2.mController;
        textView3.setTextColor(mediaSwitchingController2.mMediaOutputColorSchemeLegacy.getColorItemContent());
        mediaDeviceViewHolderLegacy2.mVolumeValueText.setTextColor(mediaSwitchingController2.mMediaOutputColorSchemeLegacy.getColorItemContent());
        mediaDeviceViewHolderLegacy2.mIconAreaLayout.setBackground(null);
        mediaDeviceViewHolderLegacy2.mIconAreaLayout.setOnClickListener(null);
        mediaDeviceViewHolderLegacy2.mIconAreaLayout.setClickable(false);
        mediaDeviceViewHolderLegacy2.mSeekBar.setProgressTintList(ColorStateList.valueOf(mediaSwitchingController2.mMediaOutputColorSchemeLegacy.getColorSeekbarProgress()));
        ((LayerDrawable) mediaDeviceViewHolderLegacy2.mSeekBar.getProgressDrawable()).findDrawableByLayerId(R.id.contrast_dot).setTintList(ColorStateList.valueOf(mediaSwitchingController2.mMediaOutputColorSchemeLegacy.getColorItemContent()));
        mediaDeviceViewHolderLegacy2.updateContainerContentA11yImportance(true);
        mediaDeviceViewHolderLegacy2.getClass();
        MediaDevice mediaDevice = (MediaDevice) mediaItem.mMediaDeviceOptional.get();
        MediaOutputAdapterBase mediaOutputAdapterBase = MediaOutputAdapterBase.this;
        boolean z7 = mediaOutputAdapterBase.mController.mAudioManager.getMutingExpectedDevice() != null;
        boolean zIsCurrentlyConnected = mediaOutputAdapterBase.isCurrentlyConnected(mediaDevice);
        MediaSwitchingController mediaSwitchingController3 = mediaOutputAdapterBase.mController;
        boolean zIsDeviceIncluded = MediaOutputAdapterBase.isDeviceIncluded(mediaSwitchingController3.mLocalMediaManager.getSelectedMediaDevice(), mediaDevice);
        boolean zIsDeviceIncluded2 = MediaOutputAdapterBase.isDeviceIncluded(mediaSwitchingController3.getDeselectableMediaDevice(), mediaDevice);
        boolean zIsDeviceIncluded3 = MediaOutputAdapterBase.isDeviceIncluded(mediaSwitchingController3.getSelectableMediaDevice(), mediaDevice);
        InfoMediaManager infoMediaManager = mediaSwitchingController3.mLocalMediaManager.mInfoMediaManager;
        RoutingSessionInfo activeRoutingSession = infoMediaManager.getActiveRoutingSession();
        ArrayList arrayList = new ArrayList();
        for (MediaRoute2Info mediaRoute2Info : infoMediaManager.getTransferableRoutes(activeRoutingSession)) {
            arrayList.add(new InfoMediaDevice(infoMediaManager.mContext, mediaRoute2Info, (RouteListingPreference.Item) ((ConcurrentHashMap) infoMediaManager.mPreferenceItemMap).get(mediaRoute2Info.getId())));
            z7 = z7;
            zIsCurrentlyConnected = zIsCurrentlyConnected;
            zIsDeviceIncluded = zIsDeviceIncluded;
        }
        boolean z8 = z7;
        boolean z9 = zIsCurrentlyConnected;
        boolean z10 = zIsDeviceIncluded;
        boolean zIsDeviceIncluded4 = MediaOutputAdapterBase.isDeviceIncluded(arrayList, mediaDevice);
        boolean z11 = mediaDevice.mItem != null;
        if (MediaOutputAdapterBase.DEBUG) {
            StringBuilder sbM2 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "[", "] ");
            sbM2.append(mediaDevice.getName());
            sbM2.append(" [");
            sbM2.append(zIsDeviceIncluded2 ? "deselectable" : "");
            sbM2.append("] [");
            sbM2.append(z10 ? "selected" : "");
            sbM2.append("] [");
            sbM2.append(zIsDeviceIncluded3 ? "selectable" : "");
            sbM2.append("] [");
            sbM2.append(zIsDeviceIncluded4 ? "transferable" : "");
            sbM2.append("] [");
            ExifInterface$$ExternalSyntheticOutline0.m(sbM2, z11 ? "hasListingPreference" : "", "]", "MediaOutputAdapterBase");
        }
        MediaOutputAdapterBase.ConnectionState connectionState2 = MediaOutputAdapterBase.ConnectionState.DISCONNECTED;
        boolean zHasBaseUserRestriction = RestrictedLockUtilsInternal.checkIfRestrictionEnforced(mediaSwitchingController3.mContext, "no_adjust_volume", UserHandle.myUserId()) != null ? true : ((UserManager) mediaSwitchingController3.mContext.getSystemService(UserManager.class)).hasBaseUserRestriction("no_adjust_volume", UserHandle.of(UserHandle.myUserId()));
        if (mediaOutputAdapterBase.mCurrentActivePosition == i) {
            mediaOutputAdapterBase.mCurrentActivePosition = -1;
        }
        if (!mediaSwitchingController3.isAnyDeviceTransferring()) {
            if (mediaDevice.isMutingExpectedDevice() && !mediaSwitchingController3.isCurrentConnectedDeviceRemote()) {
                MediaOutputAdapterBase.ConnectionState connectionState3 = MediaOutputAdapterBase.ConnectionState.CONNECTED;
                mediaOutputAdapterBase$Api34Impl$$ExternalSyntheticLambda0 = new MediaOutputAdapterBase$Api34Impl$$ExternalSyntheticLambda0(mediaDeviceViewHolderLegacy2, mediaDevice, 1);
                connectionState = connectionState3;
                z4 = false;
                z5 = false;
                z3 = true;
            } else if (z9 && z8 && !mediaSwitchingController3.isCurrentConnectedDeviceRemote()) {
                mediaOutputAdapterBase$Api34Impl$$ExternalSyntheticLambda0 = new View.OnClickListener() { // from class: com.android.systemui.media.dialog.MediaOutputAdapterBase$MediaDeviceViewHolderBase$$ExternalSyntheticLambda2
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        MediaOutputAdapterBase.MediaDeviceViewHolderBase mediaDeviceViewHolderBase = mediaDeviceViewHolderLegacy2;
                        MediaSwitchingController mediaSwitchingController4 = MediaOutputAdapterBase.this.mController;
                        if (mediaSwitchingController4.mAudioManager.getMutingExpectedDevice() != null) {
                            try {
                                synchronized (mediaSwitchingController4.mMediaDevicesLock) {
                                    mediaSwitchingController4.mOutputMediaItemListProxy.removeMutingExpectedDevices();
                                }
                                AudioManager audioManager = mediaSwitchingController4.mAudioManager;
                                audioManager.cancelMuteAwaitConnection(audioManager.getMutingExpectedDevice());
                            } catch (Exception unused) {
                                Log.d("MediaSwitchingController", "Unable to cancel mute await connection");
                            }
                        }
                        MediaOutputAdapterBase.this.notifyDataSetChanged();
                    }
                };
                connectionState = connectionState2;
                z3 = zHasBaseUserRestriction;
                z4 = false;
                z5 = false;
            } else if (mediaDevice.mState == 5) {
                connectionState2 = MediaOutputAdapterBase.ConnectionState.CONNECTING;
            } else if (!mediaOutputAdapterBase.mShouldGroupSelectedMediaItems || ((ArrayList) mediaOutputAdapterBase.mController.mLocalMediaManager.getSelectedMediaDevice()).size() <= 1 || !z10) {
                RouteListingPreference.Item item2 = mediaDevice.mItem;
                String strComposeSubtext = (item2 == null || item2.getSubText() == 0 || (item = mediaDevice.mItem) == null) ? null : MediaDevice.Api34Impl.composeSubtext(item, mediaDevice.mContext);
                MediaOutputAdapterBase.OngoingSessionStatus ongoingSessionStatus2 = MediaDevice.Api34Impl.hasOngoingSession(mediaDevice.mItem) ? new MediaOutputAdapterBase.OngoingSessionStatus(MediaDevice.Api34Impl.isHostForOngoingSession(mediaDevice.mItem)) : null;
                if (z10) {
                    z = true;
                    if (((ArrayList) mediaOutputAdapterBase.mController.mLocalMediaManager.getSelectedMediaDevice()).size() > 1 || !((ArrayList) mediaSwitchingController3.getSelectableMediaDevice()).isEmpty()) {
                        z2 = true;
                    }
                    MediaOutputAdapterBase.GroupStatus groupStatus2 = (!zIsDeviceIncluded3 || z2) ? new MediaOutputAdapterBase.GroupStatus(Boolean.valueOf(z10), Boolean.valueOf(zIsDeviceIncluded2)) : null;
                    if (mediaDevice.mState != 3) {
                        Drawable drawable3 = mediaDeviceViewHolderLegacy2.mContext.getDrawable(R.drawable.media_output_status_failed);
                        string = mediaDeviceViewHolderLegacy2.mContext.getString(R.string.media_output_dialog_connect_failed);
                        mediaOutputAdapterBase$Api34Impl$$ExternalSyntheticLambda0 = new MediaOutputAdapterBase$Api34Impl$$ExternalSyntheticLambda0(mediaDeviceViewHolderLegacy2, mediaDevice, 2);
                        connectionState = connectionState2;
                        z3 = zHasBaseUserRestriction;
                        ongoingSessionStatus = ongoingSessionStatus2;
                        drawable = drawable3;
                        groupStatus = groupStatus2;
                        z4 = false;
                        z5 = false;
                        z6 = false;
                    } else if (z9 || z10) {
                        string = strComposeSubtext;
                        connectionState = MediaOutputAdapterBase.ConnectionState.CONNECTED;
                        z3 = zHasBaseUserRestriction;
                        ongoingSessionStatus = ongoingSessionStatus2;
                        groupStatus = groupStatus2;
                        z4 = false;
                        z5 = false;
                        mediaOutputAdapterBase$Api34Impl$$ExternalSyntheticLambda0 = null;
                        z6 = false;
                        drawable = null;
                    } else {
                        if (!zIsDeviceIncluded3) {
                            Drawable deviceStatusIconBasedOnSelectionBehavior = MediaOutputAdapterBase.Api34Impl.getDeviceStatusIconBasedOnSelectionBehavior(mediaDevice, mediaDeviceViewHolderLegacy2.mContext);
                            clickListenerBasedOnSelectionBehavior = MediaOutputAdapterBase.Api34Impl.getClickListenerBasedOnSelectionBehavior(mediaDevice, mediaSwitchingController3, new MediaOutputAdapterBase$Api34Impl$$ExternalSyntheticLambda0(mediaDeviceViewHolderLegacy2, mediaDevice, 4));
                            drawable2 = deviceStatusIconBasedOnSelectionBehavior;
                        } else if (zIsDeviceIncluded4 || z11) {
                            clickListenerBasedOnSelectionBehavior = new MediaOutputAdapterBase$Api34Impl$$ExternalSyntheticLambda0(mediaDeviceViewHolderLegacy2, mediaDevice, 3);
                            drawable2 = null;
                        } else {
                            drawable2 = null;
                            clickListenerBasedOnSelectionBehavior = null;
                        }
                        if (clickListenerBasedOnSelectionBehavior != null) {
                            z = false;
                        }
                        string = strComposeSubtext;
                        drawable = drawable2;
                        connectionState = connectionState2;
                        z3 = zHasBaseUserRestriction;
                        ongoingSessionStatus = ongoingSessionStatus2;
                        mediaOutputAdapterBase$Api34Impl$$ExternalSyntheticLambda0 = clickListenerBasedOnSelectionBehavior;
                        groupStatus = groupStatus2;
                        z6 = z;
                        z4 = false;
                        z5 = false;
                    }
                    if (connectionState != MediaOutputAdapterBase.ConnectionState.CONNECTED || z5) {
                        mediaOutputAdapterBase.mCurrentActivePosition = i;
                    }
                    if (z5) {
                        mediaDeviceViewHolderLegacy2.renderDeviceItem(z4, mediaDevice, connectionState, z3, groupStatus, ongoingSessionStatus, mediaOutputAdapterBase$Api34Impl$$ExternalSyntheticLambda0, z6, string, drawable);
                        return;
                    } else {
                        mediaDeviceViewHolderLegacy2.renderDeviceGroupItem();
                        return;
                    }
                }
                z = true;
                z2 = false;
                if (zIsDeviceIncluded3) {
                    if (mediaDevice.mState != 3) {
                    }
                }
                if (connectionState != MediaOutputAdapterBase.ConnectionState.CONNECTED) {
                    mediaOutputAdapterBase.mCurrentActivePosition = i;
                }
                if (z5) {
                }
            } else if (mediaItem.mIsFirstDeviceInGroup) {
                connectionState = connectionState2;
                z3 = zHasBaseUserRestriction;
                z4 = false;
                z5 = true;
                ongoingSessionStatus = null;
                mediaOutputAdapterBase$Api34Impl$$ExternalSyntheticLambda0 = null;
                z6 = false;
                string = null;
                drawable = null;
                groupStatus = null;
                if (connectionState != MediaOutputAdapterBase.ConnectionState.CONNECTED) {
                }
                if (z5) {
                }
            } else {
                connectionState = connectionState2;
                z3 = zHasBaseUserRestriction;
                z4 = true;
                z5 = false;
                ongoingSessionStatus = null;
                mediaOutputAdapterBase$Api34Impl$$ExternalSyntheticLambda0 = null;
                z6 = false;
                string = null;
                drawable = null;
                groupStatus = null;
                if (connectionState != MediaOutputAdapterBase.ConnectionState.CONNECTED) {
                }
                if (z5) {
                }
            }
            ongoingSessionStatus = null;
            z6 = false;
            string = null;
            drawable = null;
            groupStatus = null;
            if (connectionState != MediaOutputAdapterBase.ConnectionState.CONNECTED) {
            }
            if (z5) {
            }
        } else if (mediaDevice.mState == 1) {
            connectionState2 = MediaOutputAdapterBase.ConnectionState.CONNECTING;
        }
        connectionState = connectionState2;
        z3 = zHasBaseUserRestriction;
        z4 = false;
        z5 = false;
        ongoingSessionStatus = null;
        mediaOutputAdapterBase$Api34Impl$$ExternalSyntheticLambda0 = null;
        z6 = false;
        string = null;
        drawable = null;
        groupStatus = null;
        if (connectionState != MediaOutputAdapterBase.ConnectionState.CONNECTED) {
        }
        if (z5) {
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        this.mHolderView = LayoutInflater.from(viewGroup.getContext()).inflate((i == 0 || i == 2) ? R.layout.media_output_list_item_advanced : R.layout.media_output_list_group_divider, viewGroup, false);
        return i != 1 ? new MediaDeviceViewHolderLegacy(this.mHolderView, context) : new MediaGroupDividerViewHolderLegacy(this.mHolderView);
    }
}
