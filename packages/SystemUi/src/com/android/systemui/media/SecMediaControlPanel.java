package com.android.systemui.media;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.Notification;
import android.app.WallpaperColors;
import android.bluetooth.BluetoothA2dp;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.Icon;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.media.MediaMetadata;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.net.Uri;
import android.os.Bundle;
import android.os.Trace;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.systemui.FontSizeUtils;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.facewidget.plugin.FaceWidgetColorSchemeControllerWrapper;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.log.MediaLogger;
import com.android.systemui.log.MediaLoggerImpl;
import com.android.systemui.media.SecMediaHost;
import com.android.systemui.media.SecSeekBarViewModel;
import com.android.systemui.media.controls.models.player.MediaAction;
import com.android.systemui.media.controls.models.player.MediaData;
import com.android.systemui.media.controls.models.player.MediaDeviceData;
import com.android.systemui.media.controls.ui.SecAnimatingColorTransition;
import com.android.systemui.media.controls.ui.SecColorSchemeTransition;
import com.android.systemui.monet.ColorScheme;
import com.android.systemui.monet.Style;
import com.android.systemui.monet.TonalPalette;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.TouchAnimator;
import com.android.systemui.qs.bar.BarController;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.keyguardstatusview.PluginFaceWidgetColorScheme;
import com.android.systemui.plugins.keyguardstatusview.PluginFaceWidgetColorSchemeCallback;
import com.android.systemui.statusbar.NotificationMediaManager;
import com.android.systemui.subscreen.SubScreenManager;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.ConfigurationState;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.ExecutorImpl;
import com.android.systemui.util.concurrency.RepeatableExecutorImpl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import kotlin.Unit;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SecMediaControlPanel {
    public static final int[] ACTION_IDS = {R.id.sec_action0, R.id.sec_action1, R.id.sec_action2, R.id.sec_action3, R.id.sec_action4};
    public TouchAnimator mActionButtonsAnimator;
    public final ActivityStarter mActivityStarter;
    public TouchAnimator mAlbumArtAnimator;
    public int mAlbumArtRadius;
    public int mAlbumArtSize;
    public int mBackgroundColor;
    public final Executor mBackgroundExecutor;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public ImageButton mBudsButtonCollapsed;
    public ImageButton mBudsButtonExpanded;
    public Runnable mBudsDetailCloseRunnable;
    public Runnable mBudsDetailOpenRunnable;
    public SecColorSchemeTransition mColorSchemeTransition;
    public final Context mContext;
    public MediaController mController;
    public CoverMusicCapsuleController mCoverMusicCapsuleController;
    public CharSequence mDeviceName;
    public boolean mDualPlayModeEnabled;
    public ValueAnimator mExpandButtonColorAnimator;
    public TouchAnimator mExpandContentsAnimator;
    public TouchAnimator mExpandGutsAnimator;
    public TouchAnimator mExpandIconRotationAnimator;
    public final FaceWidgetColorSchemeControllerWrapper mFaceWidgetColorSchemeController;
    public boolean mFullyExpanded;
    public int mGutsBackgroundColor;
    public final MediaLogger mLogger;
    public final DelayableExecutor mMainExecutor;
    public final MediaOutputHelper mMediaOutputHelper;
    public final C17844 mObserver;
    public TouchAnimator mOutputSwitcherAlphaAnimator;
    public String mPlayerKey;
    public ValueAnimator mPrimaryTextColorAnimator;
    public SecMediaHost.C17871 mQSMediaPlayerBarCallback;
    public ValueAnimator mRemoveButtonColorAnimator;
    public final SecQSPanelResourcePicker mResourcePicker;
    public ValueAnimator mSecondaryTextColorAnimator;
    public SecSeekBarObserver mSeekBarObserver;
    public final SecSeekBarViewModel mSeekBarViewModel;
    public final SettingsHelper mSettingsHelper;
    public final SecMediaControlPanel$$ExternalSyntheticLambda2 mSettingsListener;
    public final SubScreenManager mSubScreenManager;
    public ValueAnimator mTertiaryTextColorAnimator;
    public MediaPlayerBarExpandHelper$expandCallback$1 mToggleCallback;
    public MediaSession.Token mToken;
    public final SecMediaControlPanel$$ExternalSyntheticLambda3 mTunable;
    public final TunerService mTunerService;
    public MediaType mType;
    public SecPlayerViewHolder mViewHolder;
    public final C17833 mViewOutlineProvider;
    public final WakefulnessLifecycle mWakefulnessLifecycle;
    public final Rect mPlayerRect = new Rect();
    public final ArrayList mActionXListAnimator = new ArrayList();
    public final ConfigurationState mLastConfigurationState = new ConfigurationState(Arrays.asList(ConfigurationState.ConfigurationField.ORIENTATION, ConfigurationState.ConfigurationField.SCREEN_HEIGHT_DP));
    public Bitmap mPrevBitmap = null;
    public Drawable mPrevArtwork = null;
    public boolean mIsArtworkBound = false;
    public int mArtworkBoundId = 0;
    public int mArtworkNextBindRequestId = 0;
    public int mActionButtonNumCollapsed = 0;
    public int mActionButtonNumExpand = 0;
    public int mRemainWidthCollapsed = 0;
    public int mRemainWidthExpand = 0;
    public int mRemoveButtonCollapsed = 0;
    public int mRemoveButtonExpanded = 0;
    public int mOptionButtonCollapsed = 0;
    public int mOptionButtonExpanded = 0;
    public boolean mExpanded = true;
    public final ViewOnClickListenerC17811 mExpandClickListener = new View.OnClickListener() { // from class: com.android.systemui.media.SecMediaControlPanel.1
        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            if (!SecMediaControlPanel.this.isDisabledPlayer() && SecMediaControlPanel.this.expandIconNeedToShow()) {
                SecMediaControlPanel secMediaControlPanel = SecMediaControlPanel.this;
                if (secMediaControlPanel.mToggleCallback != null) {
                    if (!secMediaControlPanel.mExpanded) {
                        SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.sCurrentScreenID, "QPNE0021", "type", "button");
                    }
                    MediaPlayerBarExpandHelper$expandCallback$1 mediaPlayerBarExpandHelper$expandCallback$1 = SecMediaControlPanel.this.mToggleCallback;
                    mediaPlayerBarExpandHelper$expandCallback$1.getClass();
                    int i = MediaPlayerBarExpandHelper.$r8$clinit;
                    MediaPlayerBarExpandHelper mediaPlayerBarExpandHelper = mediaPlayerBarExpandHelper$expandCallback$1.this$0;
                    mediaPlayerBarExpandHelper.setHeight(mediaPlayerBarExpandHelper.shouldPlayerExpand() ? mediaPlayerBarExpandHelper.getExpandedHeight() : mediaPlayerBarExpandHelper.getCollapsedHeight());
                }
            }
        }
    };
    public final C17822 mDualPlayModeReceiver = new BroadcastReceiver() { // from class: com.android.systemui.media.SecMediaControlPanel.2
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            boolean booleanExtra = intent.getBooleanExtra("enable", false);
            StringBuilder m49m = RowView$$ExternalSyntheticOutline0.m49m("DualPlayModeReceiver.onReceive() = ", booleanExtra, ",  mViewHolder.getAppName() = ");
            TextView textView = SecMediaControlPanel.this.mViewHolder.appName;
            if (textView == null) {
                textView = null;
            }
            m49m.append((Object) textView.getText());
            Log.d("MediaControlPanel", m49m.toString());
            SecMediaControlPanel secMediaControlPanel = SecMediaControlPanel.this;
            secMediaControlPanel.mDualPlayModeEnabled = booleanExtra;
            secMediaControlPanel.updateDeviceName();
        }
    };
    public int mHeight = 0;
    public int mWidth = 0;
    public float mFraction = 0.0f;
    public boolean mIsPlayerCoverPlayed = false;
    public boolean mMediaDevicesAvailableOnTop = false;
    public boolean mBudsEnabled = false;
    public final SecMediaControlPanel$$ExternalSyntheticLambda1 mSessionDestroyCallback = new SecMediaControlPanel$$ExternalSyntheticLambda1(this);
    public boolean mIsEmptyPlayer = false;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v5, types: [com.android.systemui.media.SecMediaControlPanel$$ExternalSyntheticLambda2, com.android.systemui.util.SettingsHelper$OnChangedCallback] */
    /* JADX WARN: Type inference failed for: r3v12, types: [com.android.systemui.media.SecMediaControlPanel$3] */
    /* JADX WARN: Type inference failed for: r3v13, types: [com.android.systemui.media.SecMediaControlPanel$$ExternalSyntheticLambda3] */
    /* JADX WARN: Type inference failed for: r3v4, types: [com.android.systemui.media.SecMediaControlPanel$1] */
    /* JADX WARN: Type inference failed for: r3v5, types: [com.android.systemui.media.SecMediaControlPanel$2] */
    /* JADX WARN: Type inference failed for: r3v8, types: [com.android.systemui.media.SecMediaControlPanel$4] */
    public SecMediaControlPanel(Context context, Executor executor, DelayableExecutor delayableExecutor, ActivityStarter activityStarter, SecSeekBarViewModel secSeekBarViewModel, MediaOutputHelper mediaOutputHelper, BroadcastDispatcher broadcastDispatcher, SubScreenManager subScreenManager, FaceWidgetColorSchemeControllerWrapper faceWidgetColorSchemeControllerWrapper, MediaLogger mediaLogger, SecQSPanelResourcePicker secQSPanelResourcePicker, TunerService tunerService, SettingsHelper settingsHelper, WakefulnessLifecycle wakefulnessLifecycle) {
        ?? r2 = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.media.SecMediaControlPanel$$ExternalSyntheticLambda2
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                SecMediaControlPanel secMediaControlPanel = SecMediaControlPanel.this;
                if (uri == null) {
                    secMediaControlPanel.getClass();
                } else if (secMediaControlPanel.mType.getSupportBudsButton() && uri.equals(Settings.System.getUriFor("buds_enable"))) {
                    secMediaControlPanel.updateBudsButton();
                    secMediaControlPanel.setFraction(secMediaControlPanel.getPlayerExpandedFraction());
                }
            }
        };
        this.mSettingsListener = r2;
        this.mObserver = new WakefulnessLifecycle.Observer() { // from class: com.android.systemui.media.SecMediaControlPanel.4
            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onFinishedGoingToSleep() {
                SecSeekBarViewModel secSeekBarViewModel2 = SecMediaControlPanel.this.mSeekBarViewModel;
                secSeekBarViewModel2.getClass();
                ((RepeatableExecutorImpl) secSeekBarViewModel2.bgExecutor).execute(new SecSeekBarViewModel$listening$1(secSeekBarViewModel2, false));
            }

            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onFinishedWakingUp() {
                SecSeekBarViewModel secSeekBarViewModel2 = SecMediaControlPanel.this.mSeekBarViewModel;
                secSeekBarViewModel2.getClass();
                ((RepeatableExecutorImpl) secSeekBarViewModel2.bgExecutor).execute(new SecSeekBarViewModel$listening$1(secSeekBarViewModel2, true));
            }
        };
        this.mContext = context;
        this.mBackgroundExecutor = executor;
        this.mMainExecutor = delayableExecutor;
        this.mActivityStarter = activityStarter;
        this.mSeekBarViewModel = secSeekBarViewModel;
        this.mMediaOutputHelper = mediaOutputHelper;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mLogger = mediaLogger;
        this.mType = MediaType.QS;
        this.mSubScreenManager = subScreenManager;
        this.mFaceWidgetColorSchemeController = faceWidgetColorSchemeControllerWrapper;
        this.mResourcePicker = secQSPanelResourcePicker;
        this.mTunerService = tunerService;
        this.mSettingsHelper = settingsHelper;
        this.mWakefulnessLifecycle = wakefulnessLifecycle;
        this.mAlbumArtRadius = context.getResources().getDimensionPixelSize(R.dimen.sec_media_player_album_round);
        this.mAlbumArtSize = context.getResources().getDimensionPixelSize(R.dimen.sec_qs_media_album_size);
        this.mViewOutlineProvider = new ViewOutlineProvider() { // from class: com.android.systemui.media.SecMediaControlPanel.3
            @Override // android.view.ViewOutlineProvider
            public final void getOutline(View view, Outline outline) {
                int i = SecMediaControlPanel.this.mAlbumArtSize;
                outline.setRoundRect(0, 0, i, i, r6.mAlbumArtRadius);
            }
        };
        this.mTunable = new TunerService.Tunable() { // from class: com.android.systemui.media.SecMediaControlPanel$$ExternalSyntheticLambda3
            @Override // com.android.systemui.tuner.TunerService.Tunable
            public final void onTuningChanged(String str, String str2) {
                SecMediaControlPanel secMediaControlPanel = SecMediaControlPanel.this;
                secMediaControlPanel.getClass();
                if (!"qspanel_media_quickcontrol_bar_available_on_top".equals(str) || str2 == null) {
                    return;
                }
                secMediaControlPanel.mMediaDevicesAvailableOnTop = Integer.parseInt(str2) != 0;
            }
        };
        settingsHelper.registerCallback(r2, Settings.System.getUriFor("buds_enable"));
    }

    public static void scaleTransitionDrawableLayer(TransitionDrawable transitionDrawable, int i, int i2, int i3) {
        Drawable drawable = transitionDrawable.getDrawable(i);
        if (drawable == null) {
            return;
        }
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        if (intrinsicWidth == 0 || intrinsicHeight == 0 || i2 == 0 || i3 == 0) {
            return;
        }
        float f = intrinsicWidth;
        float f2 = intrinsicHeight;
        float f3 = i2;
        float f4 = i3;
        float f5 = f / f2 > f3 / f4 ? f4 / f2 : f3 / f;
        transitionDrawable.setLayerSize(i, (int) (f * f5), (int) (f5 * f2));
    }

    public final void attach(SecPlayerViewHolder secPlayerViewHolder) {
        ImageView imageView;
        this.mViewHolder = secPlayerViewHolder;
        boolean z = this.mIsEmptyPlayer;
        SecSeekBarViewModel secSeekBarViewModel = this.mSeekBarViewModel;
        if (!z) {
            if (this.mType.getSupportExpandable() && (imageView = secPlayerViewHolder.albumThumbnail) != null) {
                imageView.setOutlineProvider(this.mViewOutlineProvider);
                imageView.setClipToOutline(true);
            }
            SecSeekBarObserver secSeekBarObserver = new SecSeekBarObserver(this.mViewHolder);
            this.mSeekBarObserver = secSeekBarObserver;
            secSeekBarViewModel._progress.observeForever(secSeekBarObserver);
            SeekBar seekBar = secPlayerViewHolder.getSeekBar();
            seekBar.setOnSeekBarChangeListener(new SecSeekBarViewModel.SeekBarChangeListener(secSeekBarViewModel));
            seekBar.setOnTouchListener(new SecSeekBarViewModel.SeekBarTouchListener(secSeekBarViewModel, seekBar));
            secSeekBarViewModel.sessionDestroyCallback = this.mSessionDestroyCallback;
            this.mBroadcastDispatcher.registerReceiver(AppCompatDelegateImpl$$ExternalSyntheticOutline0.m5m("com.samsung.bluetooth.a2dp.intent.action.DUAL_PLAY_MODE_ENABLED"), this.mDualPlayModeReceiver);
            TunerService tunerService = this.mTunerService;
            tunerService.addTunable(this.mTunable, "qspanel_media_quickcontrol_bar_available_on_top");
            this.mMediaDevicesAvailableOnTop = tunerService.getValue(-1, "qspanel_media_quickcontrol_bar_available_on_top") != 0;
        }
        updateWidth();
        boolean supportColorSchemeTransition = this.mType.getSupportColorSchemeTransition();
        Context context = this.mContext;
        if (supportColorSchemeTransition) {
            this.mColorSchemeTransition = new SecColorSchemeTransition(context, this.mViewHolder);
        }
        if (this.mType.getSupportCapsule()) {
            CoverMusicCapsuleController coverMusicCapsuleController = new CoverMusicCapsuleController(context, this.mSubScreenManager, new BooleanSupplier() { // from class: com.android.systemui.media.SecMediaControlPanel$$ExternalSyntheticLambda4
                @Override // java.util.function.BooleanSupplier
                public final boolean getAsBoolean() {
                    return SecMediaControlPanel.this.mIsPlayerCoverPlayed;
                }
            });
            this.mCoverMusicCapsuleController = coverMusicCapsuleController;
            secSeekBarViewModel.coverMusicCapsuleController = coverMusicCapsuleController;
        }
        if (this.mType.getSupportWidgetTimer()) {
            this.mWakefulnessLifecycle.addObserver(this.mObserver);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r14v2, types: [java.lang.Object, java.util.List] */
    /* JADX WARN: Type inference failed for: r1v14, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r4v14 */
    /* JADX WARN: Type inference failed for: r4v15, types: [int] */
    /* JADX WARN: Type inference failed for: r4v35 */
    public final void bind(final MediaData mediaData, String str) {
        String str2;
        boolean z;
        String str3;
        Drawable loadDrawable;
        Rect rect;
        boolean z2;
        SecMediaHost.C17871 c17871;
        if (this.mViewHolder == null) {
            return;
        }
        StringBuilder sb = new StringBuilder("MediaControlPanel#bindPlayer<");
        sb.append(str);
        String str4 = ">";
        sb.append(">");
        Trace.beginSection(sb.toString());
        updateWidth();
        MediaSession.Token token = mediaData.token;
        MediaSession.Token token2 = this.mToken;
        if (token2 == null || !token2.equals(token)) {
            this.mToken = token;
        }
        MediaSession.Token token3 = this.mToken;
        Context context = this.mContext;
        if (token3 != null) {
            this.mController = new MediaController(context, this.mToken);
        } else {
            this.mController = null;
        }
        LinearLayout linearLayout = this.mViewHolder.header;
        if (linearLayout == null) {
            linearLayout = null;
        }
        ViewOnClickListenerC17811 viewOnClickListenerC17811 = this.mExpandClickListener;
        linearLayout.setOnClickListener(viewOnClickListenerC17811);
        ImageView imageView = this.mViewHolder.appIcon;
        if (imageView == null) {
            imageView = null;
        }
        int i = mediaData.userId;
        Icon icon = mediaData.appIcon;
        if (icon != null) {
            imageView.setImageDrawable(icon.loadDrawableAsUser(context, i));
        } else {
            imageView.setImageResource(R.drawable.ic_music_note);
        }
        SecPlayerViewHolder secPlayerViewHolder = this.mViewHolder;
        TextView textView = secPlayerViewHolder.titleText;
        if (textView == null) {
            textView = null;
        }
        TextView textView2 = secPlayerViewHolder.artistText;
        if (textView2 == null) {
            textView2 = null;
        }
        CharSequence text = textView.getText();
        CharSequence charSequence = mediaData.song;
        boolean equals = text.equals(charSequence);
        boolean z3 = false;
        CharSequence charSequence2 = mediaData.artist;
        boolean z4 = (equals && textView2.getText().equals(charSequence2)) ? false : true;
        textView.setText(Notification.safeCharSequence(charSequence));
        textView2.setText(Notification.safeCharSequence(charSequence2));
        TextView textView3 = this.mViewHolder.appName;
        if (textView3 == null) {
            textView3 = null;
        }
        String str5 = mediaData.app;
        textView3.setText(str5);
        int i2 = 5;
        MediaDeviceData mediaDeviceData = mediaData.device;
        if (mediaDeviceData != null) {
            this.mDeviceName = mediaDeviceData.name;
            Integer num = mediaDeviceData.customMediaDeviceData.deviceType;
            if (num != null && num.intValue() == 5 && (c17871 = this.mQSMediaPlayerBarCallback) != null) {
                BluetoothA2dp bluetoothA2dp = SecMediaHost.this.mMediaBluetoothHelper.a2dp;
                if (bluetoothA2dp != null ? bluetoothA2dp.semIsDualPlayMode() : false) {
                    z2 = true;
                    this.mDualPlayModeEnabled = z2;
                }
            }
            z2 = false;
            this.mDualPlayModeEnabled = z2;
        } else {
            Log.w("MediaControlPanel", "device is null");
            this.mDeviceName = context.getString(R.string.sec_media_output_device);
        }
        TextView textView4 = this.mViewHolder.mediaOutputText;
        if (textView4 == null) {
            textView4 = null;
        }
        textView4.setOnClickListener(new SecMediaControlPanel$$ExternalSyntheticLambda7(this, mediaData, textView4));
        updateDeviceName();
        updateFontSize(R.dimen.sec_qs_media_player_output_switch_text_size, textView4);
        Log.d("MediaControlPanel", "bindActionButtons semanticActions=" + mediaData.semanticActions);
        ?? sb2 = new StringBuilder("bindActionButtons actionsWhenCollapsed=");
        List list = mediaData.actionsToShowInCompact;
        sb2.append(list);
        sb2.append(", actionIcons=");
        ?? r14 = mediaData.actions;
        sb2.append(r14);
        Log.d("MediaControlPanel", sb2.toString());
        this.mActionButtonNumCollapsed = list.size();
        this.mActionButtonNumExpand = r14.size();
        boolean supportExpandable = this.mType.getSupportExpandable();
        int[] iArr = ACTION_IDS;
        Icon icon2 = icon;
        if (supportExpandable) {
            int i3 = 0;
            while (i3 < i2) {
                int i4 = iArr[i3];
                ImageButton actionButton = this.mViewHolder.getActionButton(i4, z3);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) actionButton.getLayoutParams();
                if (QpRune.QUICK_TABLET) {
                    layoutParams.width = context.getResources().getDimensionPixelSize(R.dimen.sec_qs_media_player_action_button_size_collapsed_tablet);
                    layoutParams.height = context.getResources().getDimensionPixelSize(R.dimen.sec_qs_media_player_action_button_size_collapsed_tablet);
                    if (i3 != 0) {
                        str3 = str4;
                        layoutParams.leftMargin = (int) (context.getResources().getFloat(R.dimen.sec_qs_media_player_action_button_collapsed_margin_size_tablet) * this.mWidth);
                    } else {
                        str3 = str4;
                    }
                } else {
                    str3 = str4;
                    layoutParams.width = context.getResources().getDimensionPixelSize(R.dimen.sec_qs_media_player_action_button_size_collapsed);
                    layoutParams.height = context.getResources().getDimensionPixelSize(R.dimen.sec_qs_media_player_action_button_size_collapsed);
                    if (i3 != 0) {
                        layoutParams.leftMargin = context.getResources().getDimensionPixelSize(R.dimen.sec_qs_media_player_action_button_collapsed_margin_size);
                    }
                }
                actionButton.setLayoutParams(layoutParams);
                if (this.mType.getSupportBudsButton()) {
                    this.mBudsButtonCollapsed.setLayoutParams(layoutParams);
                }
                if (context.getResources().getConfiguration().orientation == 2) {
                    this.mViewHolder.getActionButton(i4, true).setLayoutParams(layoutParams);
                    if (this.mType.getSupportBudsButton()) {
                        this.mBudsButtonCollapsed.setLayoutParams(layoutParams);
                    }
                }
                i3++;
                str4 = str3;
                i2 = 5;
                z3 = false;
            }
            str2 = str4;
            LinearLayout linearLayout2 = this.mViewHolder.expandedActionButtonsContainer;
            if (linearLayout2 == null) {
                linearLayout2 = null;
            }
            if (context.getResources().getConfiguration().orientation == 2) {
                linearLayout2.setPadding(0, 0, 0, 0);
                z = false;
            } else {
                Resources resources = context.getResources();
                int i5 = this.mActionButtonNumExpand;
                int dimensionPixelSize = i5 != 2 ? i5 != 3 ? i5 != 4 ? i5 != 5 ? 0 : resources.getDimensionPixelSize(R.dimen.qs_media_action_button_side_padding_5) : resources.getDimensionPixelSize(R.dimen.qs_media_action_button_side_padding_4) : resources.getDimensionPixelSize(R.dimen.qs_media_action_button_side_padding_3) : resources.getDimensionPixelSize(R.dimen.qs_media_action_button_side_padding_2);
                for (int i6 = 0; i6 < this.mActionButtonNumExpand && i6 < 5; i6++) {
                    ImageButton actionButton2 = this.mViewHolder.getActionButton(iArr[i6], true);
                    LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) actionButton2.getLayoutParams();
                    if (QpRune.QUICK_TABLET) {
                        layoutParams2.width = context.getResources().getDimensionPixelSize(R.dimen.sec_qs_media_player_action_button_size_collapsed_tablet);
                        layoutParams2.height = context.getResources().getDimensionPixelSize(R.dimen.sec_qs_media_player_action_button_size_collapsed_tablet);
                    }
                    if (i6 != 0) {
                        layoutParams2.leftMargin = dimensionPixelSize;
                    }
                    actionButton2.setLayoutParams(layoutParams2);
                    if (this.mType.getSupportBudsButton()) {
                        this.mBudsButtonExpanded.setLayoutParams(layoutParams2);
                    }
                }
                z = false;
                linearLayout2.setPadding(0, context.getResources().getDimensionPixelSize(R.dimen.sec_qs_media_player_expanded_action_icon_padding_top), 0, context.getResources().getDimensionPixelSize(R.dimen.sec_qs_media_player_expanded_action_icon_padding_bottom));
            }
        } else {
            z = false;
            str2 = ">";
        }
        char c = 5;
        int min = Math.min(r14.size(), 5);
        int count = (int) list.stream().filter(new SecMediaControlPanel$$ExternalSyntheticLambda11()).count();
        ?? r4 = z;
        while (r4 < c) {
            int i7 = iArr[r4];
            boolean z5 = r4 < r14.size() ? true : z;
            boolean contains = list.contains(Integer.valueOf((int) r4));
            MediaAction mediaAction = z5 ? (MediaAction) r14.get(r4) : null;
            List list2 = list;
            char c2 = c;
            int i8 = r4;
            int i9 = min;
            Icon icon3 = icon2;
            int i10 = count;
            bindButtonAndVisible(i7, mediaAction, z5, min, mediaData.packageName, true);
            bindButtonAndVisible(i7, mediaAction, z5 && contains, i10, mediaData.packageName, false);
            count = i10;
            list = list2;
            c = c2;
            min = i9;
            icon2 = icon3;
            z = false;
            r4 = i8 + 1;
        }
        Icon icon4 = icon2;
        int i11 = 2;
        final MediaController mediaController = this.mController;
        Runnable runnable = new Runnable() { // from class: com.android.systemui.media.SecMediaControlPanel$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                boolean z6;
                SecMediaControlPanel secMediaControlPanel = SecMediaControlPanel.this;
                MediaController mediaController2 = mediaController;
                SecSeekBarViewModel secSeekBarViewModel = secMediaControlPanel.mSeekBarViewModel;
                secSeekBarViewModel.setController(mediaController2);
                MediaController mediaController3 = secSeekBarViewModel.controller;
                secSeekBarViewModel.playbackState = mediaController3 != null ? mediaController3.getPlaybackState() : null;
                MediaController mediaController4 = secSeekBarViewModel.controller;
                MediaMetadata metadata = mediaController4 != null ? mediaController4.getMetadata() : null;
                PlaybackState playbackState = secSeekBarViewModel.playbackState;
                boolean z7 = ((playbackState != null ? playbackState.getActions() : 0L) & 256) != 0;
                PlaybackState playbackState2 = secSeekBarViewModel.playbackState;
                Integer valueOf = playbackState2 != null ? Integer.valueOf((int) playbackState2.getPosition()) : null;
                int i12 = metadata != null ? (int) metadata.getLong("android.media.metadata.DURATION") : 0;
                PlaybackState playbackState3 = secSeekBarViewModel.playbackState;
                boolean isPlayingState = NotificationMediaManager.isPlayingState(playbackState3 != null ? playbackState3.getState() : 0);
                PlaybackState playbackState4 = secSeekBarViewModel.playbackState;
                if (playbackState4 != null) {
                    if (!(playbackState4.getState() == 0) && i12 > 0) {
                        z6 = true;
                        secSeekBarViewModel.set_data(new SecSeekBarViewModel.Progress(z6, z7, isPlayingState, secSeekBarViewModel.scrubbing, valueOf, i12));
                        secSeekBarViewModel.checkIfPollingNeeded();
                    }
                }
                z6 = false;
                secSeekBarViewModel.set_data(new SecSeekBarViewModel.Progress(z6, z7, isPlayingState, secSeekBarViewModel.scrubbing, valueOf, i12));
                secSeekBarViewModel.checkIfPollingNeeded();
            }
        };
        Executor executor = this.mBackgroundExecutor;
        executor.execute(runnable);
        if (mediaController != null && this.mCoverMusicCapsuleController != null && this.mType.getSupportCapsule()) {
            CoverMusicCapsuleController coverMusicCapsuleController = this.mCoverMusicCapsuleController;
            MediaMetadata metadata = mediaController.getMetadata();
            coverMusicCapsuleController.getClass();
            coverMusicCapsuleController.isLiveStreaming = (metadata != null ? (int) metadata.getLong("android.media.metadata.DURATION") : 0) <= 0;
        }
        if (this.mType.getSupportGuts()) {
            SecPlayerViewHolder secPlayerViewHolder2 = this.mViewHolder;
            final LinearLayout linearLayout3 = secPlayerViewHolder2.player;
            final View view = secPlayerViewHolder2.options;
            if (linearLayout3 != null && view != null) {
                view.setMinimumHeight(linearLayout3.getHeight());
                view.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.android.systemui.media.SecMediaControlPanel$$ExternalSyntheticLambda8
                    @Override // android.view.View.OnLongClickListener
                    public final boolean onLongClick(View view2) {
                        SecMediaControlPanel secMediaControlPanel = SecMediaControlPanel.this;
                        View view3 = view;
                        View view4 = linearLayout3;
                        secMediaControlPanel.getClass();
                        view3.setVisibility(8);
                        view4.setVisibility(0);
                        secMediaControlPanel.setBackgroundColor(secMediaControlPanel.mBackgroundColor);
                        return true;
                    }
                });
                TextView textView5 = this.mViewHolder.removeText;
                if (textView5 != null) {
                    textView5.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.media.SecMediaControlPanel$$ExternalSyntheticLambda9
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view2) {
                            SecMediaControlPanel secMediaControlPanel = SecMediaControlPanel.this;
                            MediaData mediaData2 = mediaData;
                            secMediaControlPanel.getClass();
                            SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.sCurrentScreenID, "QPNE0004", "app", mediaData2.packageName);
                            ((MediaLoggerImpl) secMediaControlPanel.mLogger).onRemoveClicked(secMediaControlPanel.mPlayerKey);
                            secMediaControlPanel.removePlayer();
                        }
                    });
                    updateFontSize(R.dimen.sec_qs_media_panel_options_remove_text_size, textView5);
                }
                TextView textView6 = this.mViewHolder.cancelText;
                if (textView6 != null) {
                    textView6.setOnClickListener(new SecMediaControlPanel$$ExternalSyntheticLambda7(this, view, i11, linearLayout3));
                    updateFontSize(R.dimen.sec_qs_media_panel_options_cancel_text_size, textView6);
                }
                ImageView imageView2 = this.mViewHolder.optionsAppIcon;
                if (imageView2 != null) {
                    if (icon4 != null) {
                        imageView2.setImageDrawable(icon4.loadDrawableAsUser(context, i));
                    } else {
                        imageView2.setImageResource(R.drawable.ic_music_note);
                    }
                }
                TextView textView7 = this.mViewHolder.optionsAppTitle;
                if (textView7 != null) {
                    textView7.setText(str5);
                    updateFontSize(R.dimen.sec_qs_media_panel_options_app_name_text_size, textView7);
                }
                view.setVisibility(8);
                linearLayout3.setVisibility(0);
                View view2 = this.mViewHolder.playerView;
                if (view2 == null) {
                    view2 = null;
                }
                view2.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.android.systemui.media.SecMediaControlPanel$$ExternalSyntheticLambda10
                    @Override // android.view.View.OnLongClickListener
                    public final boolean onLongClick(View view3) {
                        SecMediaControlPanel secMediaControlPanel = SecMediaControlPanel.this;
                        if (secMediaControlPanel.isPlaying()) {
                            return true;
                        }
                        LinearLayout linearLayout4 = secMediaControlPanel.mViewHolder.player;
                        if (linearLayout4 != null) {
                            linearLayout4.setVisibility(8);
                        }
                        View view4 = secMediaControlPanel.mViewHolder.options;
                        if (view4 != null) {
                            view4.setVisibility(0);
                        }
                        secMediaControlPanel.setBackgroundColor(secMediaControlPanel.mGutsBackgroundColor);
                        return true;
                    }
                });
            }
        }
        if (this.mType.getSupportExpandable()) {
            this.mGutsBackgroundColor = context.getColor(R.color.sec_qs_media_player_guts_background_color);
            int color = context.getColor(R.color.sec_qs_media_player_background_color);
            this.mBackgroundColor = color;
            setBackgroundColor(color);
            View view3 = this.mViewHolder.playerView;
            if (view3 == null) {
                view3 = null;
            }
            view3.setOnClickListener(new SecMediaControlPanel$$ExternalSyntheticLambda7(this, mediaData.clickIntent, 0, mediaData));
            ImageView imageView3 = this.mViewHolder.albumThumbnail;
            if (imageView3 != null) {
                Icon icon5 = mediaData.artwork;
                if (icon5 != null) {
                    if (icon5 == null) {
                        loadDrawable = null;
                    } else {
                        loadDrawable = icon5.loadDrawable(context);
                        float intrinsicHeight = loadDrawable.getIntrinsicHeight() / loadDrawable.getIntrinsicWidth();
                        if (intrinsicHeight > 1.0f) {
                            int i12 = this.mAlbumArtSize;
                            rect = new Rect(0, 0, i12, (int) (i12 * intrinsicHeight));
                        } else {
                            int i13 = this.mAlbumArtSize;
                            rect = new Rect(0, 0, (int) (i13 / intrinsicHeight), i13);
                        }
                        if (rect.width() > this.mAlbumArtSize || rect.height() > this.mAlbumArtSize) {
                            rect.offset((int) (-((rect.width() - this.mAlbumArtSize) / 2.0f)), (int) (-((rect.height() - this.mAlbumArtSize) / 2.0f)));
                        }
                        loadDrawable.setBounds(rect);
                    }
                    imageView3.setImageDrawable(loadDrawable);
                    imageView3.setScaleType(ImageView.ScaleType.FIT_CENTER);
                } else {
                    imageView3.setImageDrawable(context.getResources().getDrawable(R.drawable.stat_notify_music));
                    imageView3.setScaleType(ImageView.ScaleType.CENTER);
                }
            }
            int i14 = this.mBackgroundColor;
            ImageView imageView4 = this.mViewHolder.albumThumbnail;
            if (imageView4 != null) {
                float[] fArr = new float[3];
                Color.colorToHSV(i14, fArr);
                float f = fArr[2];
                if (0.0f > f || f >= 0.5d) {
                    imageView4.setBackground(context.getDrawable(R.drawable.sec_qs_media_player_album_stroke_dark));
                } else {
                    imageView4.setBackground(context.getDrawable(R.drawable.sec_qs_media_player_album_stroke_light));
                }
            }
            ImageView imageView5 = this.mViewHolder.expandIcon;
            if (imageView5 != null) {
                imageView5.setVisibility(expandIconNeedToShow() ? 0 : 4);
                imageView5.setOnClickListener(viewOnClickListenerC17811);
            }
        }
        updateExpandAnimator();
        updateResources();
        if (this.mType.getSupportArtwork()) {
            final int hashCode = mediaData.hashCode();
            final String str6 = "MediaControlPanel#bindArtworkAndColors<" + str + str2;
            Trace.beginAsyncSection(str6, hashCode);
            final int i15 = this.mArtworkNextBindRequestId;
            this.mArtworkNextBindRequestId = i15 + 1;
            if (z4) {
                this.mIsArtworkBound = false;
            }
            final int i16 = this.mWidth;
            ((GradientDrawable) this.mViewHolder.getAlbumView().getForeground()).setSize(i16, i16);
            final boolean z6 = z4;
            executor.execute(new Runnable() { // from class: com.android.systemui.media.SecMediaControlPanel$$ExternalSyntheticLambda12
                /* JADX WARN: Can't wrap try/catch for region: R(14:0|1|(3:3|(1:30)(1:7)|(8:9|(3:12|(1:14)(1:16)|15)|17|(1:29)(1:21)|22|(1:24)(1:28)|25|26))|31|32|33|35|36|37|38|(0)(0)|25|26|(1:(0))) */
                /* JADX WARN: Code restructure failed: missing block: B:40:0x00c7, code lost:
                
                    r0 = e;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:41:0x00cc, code lost:
                
                    android.util.Log.w("MediaControlPanel", "Cannot find icon for package " + r1, r0);
                    r15 = null;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:42:0x00c9, code lost:
                
                    r0 = e;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:43:0x00ca, code lost:
                
                    r7 = null;
                    r11 = null;
                 */
                /* JADX WARN: Removed duplicated region for block: B:24:0x00e6  */
                /* JADX WARN: Removed duplicated region for block: B:28:0x00ee  */
                @Override // java.lang.Runnable
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final void run() {
                    ColorScheme colorScheme;
                    final Drawable drawable;
                    final Drawable drawable2;
                    final ColorScheme colorScheme2;
                    final boolean z7;
                    final SecMediaControlPanel secMediaControlPanel = SecMediaControlPanel.this;
                    final MediaData mediaData2 = mediaData;
                    final int i17 = i16;
                    final int i18 = i16;
                    final int i19 = i15;
                    final String str7 = str6;
                    final int i20 = hashCode;
                    final boolean z8 = z6;
                    secMediaControlPanel.getClass();
                    Icon icon6 = mediaData2.artwork;
                    String str8 = mediaData2.packageName;
                    Context context2 = secMediaControlPanel.mContext;
                    if (icon6 != null) {
                        if (icon6.getType() == 1 || icon6.getType() == 5) {
                            Bitmap bitmap = icon6.getBitmap();
                            WallpaperColors fromBitmap = WallpaperColors.fromBitmap(bitmap);
                            ColorScheme colorScheme3 = new ColorScheme(fromBitmap, true, Style.CONTENT);
                            colorScheme = new ColorScheme(fromBitmap, true, Style.VIBRANT);
                            Drawable loadDrawable2 = icon6.loadDrawable(context2);
                            int intrinsicHeight2 = loadDrawable2.getIntrinsicHeight();
                            int intrinsicWidth = loadDrawable2.getIntrinsicWidth();
                            if (intrinsicHeight2 <= i17 && intrinsicWidth <= i17) {
                                Bitmap bitmap2 = ((BitmapDrawable) loadDrawable2).getBitmap();
                                int height = bitmap2.getHeight();
                                int width = bitmap2.getWidth();
                                loadDrawable2 = new BitmapDrawable(context2.getResources(), Bitmap.createScaledBitmap(height > width ? Bitmap.createBitmap(bitmap2, 0, (height - width) / 2, width, width) : Bitmap.createBitmap(bitmap2, (width - height) / 2, 0, height, height), i17, i18, true));
                            }
                            if (secMediaControlPanel.mIsArtworkBound && !bitmap.sameAs(secMediaControlPanel.mPrevBitmap)) {
                                secMediaControlPanel.mIsArtworkBound = false;
                            }
                            secMediaControlPanel.mPrevBitmap = bitmap;
                            drawable2 = loadDrawable2;
                            drawable = drawable2;
                            colorScheme2 = colorScheme3;
                            z7 = true;
                            final int s700 = colorScheme2 != null ? colorScheme.accent1.getS700() : 0;
                            ((ExecutorImpl) secMediaControlPanel.mMainExecutor).execute(new Runnable() { // from class: com.android.systemui.media.SecMediaControlPanel$$ExternalSyntheticLambda13
                                @Override // java.lang.Runnable
                                public final void run() {
                                    String str9;
                                    int i21;
                                    ColorScheme colorScheme4;
                                    int i22;
                                    boolean z9;
                                    int i23;
                                    int i24;
                                    CoverMusicCapsuleController coverMusicCapsuleController2;
                                    int i25;
                                    Bitmap bitmap3;
                                    Bitmap bitmap4;
                                    ColorScheme colorScheme5;
                                    int intValue;
                                    SecMediaControlPanel secMediaControlPanel2 = SecMediaControlPanel.this;
                                    int i26 = i19;
                                    String str10 = str7;
                                    int i27 = i20;
                                    ColorScheme colorScheme6 = colorScheme2;
                                    MediaData mediaData3 = mediaData2;
                                    Drawable drawable3 = drawable2;
                                    boolean z10 = z8;
                                    boolean z11 = z7;
                                    Drawable drawable4 = drawable;
                                    int i28 = s700;
                                    int i29 = i17;
                                    int i30 = i18;
                                    if (i26 < secMediaControlPanel2.mArtworkBoundId) {
                                        Trace.endAsyncSection(str10, i27);
                                        return;
                                    }
                                    if (colorScheme6 != null) {
                                        String str11 = mediaData3.packageName;
                                        TonalPalette tonalPalette = colorScheme6.accent2;
                                        int s800 = tonalPalette.getS800();
                                        str9 = str10;
                                        TonalPalette tonalPalette2 = colorScheme6.accent1;
                                        i21 = i27;
                                        colorScheme4 = colorScheme6;
                                        PluginFaceWidgetColorScheme pluginFaceWidgetColorScheme = new PluginFaceWidgetColorScheme(s800, tonalPalette2.getS100(), ((Number) ((ArrayList) tonalPalette2.allShades).get(3)).intValue(), tonalPalette.getS700(), tonalPalette2.getS700());
                                        boolean z12 = z10 || (!secMediaControlPanel2.mIsArtworkBound && z11);
                                        Iterator it = ((ArrayList) secMediaControlPanel2.mFaceWidgetColorSchemeController.mCallbackList).iterator();
                                        while (it.hasNext()) {
                                            ((PluginFaceWidgetColorSchemeCallback) it.next()).onColorSchemeUpdated(str11, drawable3, pluginFaceWidgetColorScheme, z12);
                                        }
                                    } else {
                                        str9 = str10;
                                        i21 = i27;
                                        colorScheme4 = colorScheme6;
                                    }
                                    secMediaControlPanel2.mArtworkBoundId = i26;
                                    ImageView albumView = secMediaControlPanel2.mViewHolder.getAlbumView();
                                    albumView.setPadding(0, 0, 0, 0);
                                    if (z10 || (!secMediaControlPanel2.mIsArtworkBound && z11)) {
                                        if (!secMediaControlPanel2.mType.getSupportCapsule() || (coverMusicCapsuleController2 = secMediaControlPanel2.mCoverMusicCapsuleController) == null) {
                                            i22 = i30;
                                            z9 = z11;
                                            i23 = i29;
                                        } else {
                                            String str12 = mediaData3.packageName;
                                            MediaController mediaController2 = secMediaControlPanel2.mController;
                                            Bundle bundle = coverMusicCapsuleController2.bundle;
                                            bundle.putString("capsule_action_pkg", str12);
                                            bundle.putParcelable("capsule_action", mediaData3.clickIntent);
                                            if (drawable4 != null) {
                                                RemoteViews remoteViews = coverMusicCapsuleController2.capsule;
                                                BitmapDrawable bitmapDrawable = drawable4 instanceof BitmapDrawable ? (BitmapDrawable) drawable4 : null;
                                                if (bitmapDrawable == null || (bitmap4 = bitmapDrawable.getBitmap()) == null) {
                                                    Bitmap createBitmap = Bitmap.createBitmap(40, 40, Bitmap.Config.ARGB_8888);
                                                    Canvas canvas = new Canvas(createBitmap);
                                                    i25 = 0;
                                                    drawable4.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                                                    drawable4.draw(canvas);
                                                    bitmap3 = createBitmap;
                                                } else {
                                                    bitmap3 = bitmap4;
                                                    i25 = 0;
                                                }
                                                Bitmap createBitmap2 = Bitmap.createBitmap(40, 40, Bitmap.Config.ARGB_8888);
                                                Bitmap createScaledBitmap = Bitmap.createScaledBitmap(bitmap3, 40, 40, true);
                                                z9 = z11;
                                                Rect rect2 = new Rect(i25, i25, createScaledBitmap.getWidth(), createScaledBitmap.getHeight());
                                                Paint paint = new Paint();
                                                paint.setAntiAlias(true);
                                                paint.setColor(-12434878);
                                                Canvas canvas2 = new Canvas(createBitmap2);
                                                canvas2.drawARGB(i25, i25, i25, i25);
                                                i22 = i30;
                                                i23 = i29;
                                                canvas2.drawCircle(createScaledBitmap.getWidth() / 2, createScaledBitmap.getHeight() / 2, createScaledBitmap.getWidth() / 2, paint);
                                                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                                                Unit unit = Unit.INSTANCE;
                                                canvas2.drawBitmap(createScaledBitmap, rect2, rect2, paint);
                                                remoteViews.setImageViewBitmap(R.id.sec_media_capsule_album_art, createBitmap2);
                                            } else {
                                                i22 = i30;
                                                z9 = z11;
                                                i23 = i29;
                                            }
                                            bundle.putInt("bg_startColor", i28);
                                            float[] fArr2 = new float[3];
                                            Color.colorToHSV(i28, fArr2);
                                            float f2 = fArr2[0];
                                            fArr2[0] = f2 < 150.0f ? f2 + 40.0f : f2 - 60.0f;
                                            bundle.putInt("bg_endColor", Color.HSVToColor(fArr2));
                                            coverMusicCapsuleController2.updateEqualizerState(mediaController2 != null ? mediaController2.getPlaybackState() : null);
                                        }
                                        if (secMediaControlPanel2.mPrevArtwork == null) {
                                            albumView.setImageDrawable(drawable3);
                                            i24 = 0;
                                        } else {
                                            TransitionDrawable transitionDrawable = new TransitionDrawable(new Drawable[]{secMediaControlPanel2.mPrevArtwork, drawable3});
                                            int i31 = i22;
                                            int i32 = i23;
                                            i24 = 0;
                                            SecMediaControlPanel.scaleTransitionDrawableLayer(transitionDrawable, 0, i32, i31);
                                            SecMediaControlPanel.scaleTransitionDrawableLayer(transitionDrawable, 1, i32, i31);
                                            transitionDrawable.setLayerGravity(0, 17);
                                            transitionDrawable.setLayerGravity(1, 17);
                                            transitionDrawable.setCrossFadeEnabled(true);
                                            albumView.setImageDrawable(transitionDrawable);
                                            transitionDrawable.startTransition(z9 ? 333 : 80);
                                        }
                                        secMediaControlPanel2.mPrevArtwork = drawable3;
                                        secMediaControlPanel2.mIsArtworkBound = z9;
                                    } else {
                                        i24 = 0;
                                    }
                                    SecColorSchemeTransition secColorSchemeTransition = secMediaControlPanel2.mColorSchemeTransition;
                                    secColorSchemeTransition.isGradientEnabled = secMediaControlPanel2.mIsArtworkBound;
                                    SecAnimatingColorTransition[] secAnimatingColorTransitionArr = secColorSchemeTransition.colorTransitions;
                                    int length = secAnimatingColorTransitionArr.length;
                                    int i33 = i24;
                                    while (i33 < length) {
                                        SecAnimatingColorTransition secAnimatingColorTransition = secAnimatingColorTransitionArr[i33];
                                        if (colorScheme4 == null) {
                                            intValue = secAnimatingColorTransition.defaultColor;
                                            colorScheme5 = colorScheme4;
                                        } else {
                                            colorScheme5 = colorScheme4;
                                            intValue = ((Number) secAnimatingColorTransition.extractColor.invoke(colorScheme5)).intValue();
                                        }
                                        if (intValue != secAnimatingColorTransition.targetColor) {
                                            secAnimatingColorTransition.sourceColor = secAnimatingColorTransition.currentColor;
                                            secAnimatingColorTransition.targetColor = intValue;
                                            secAnimatingColorTransition.valueAnimator.cancel();
                                            secAnimatingColorTransition.valueAnimator.start();
                                        }
                                        i33++;
                                        colorScheme4 = colorScheme5;
                                    }
                                    Trace.endAsyncSection(str9, i21);
                                }
                            });
                        }
                    }
                    ColorDrawable colorDrawable = new ColorDrawable(0);
                    Drawable drawable3 = context2.getPackageManager().getApplicationIcon(str8);
                    ColorScheme colorScheme4 = new ColorScheme(WallpaperColors.fromDrawable(drawable3), true, Style.CONTENT);
                    colorScheme = new ColorScheme(WallpaperColors.fromDrawable(drawable3), true, Style.VIBRANT);
                    drawable = drawable3;
                    drawable2 = colorDrawable;
                    colorScheme2 = colorScheme4;
                    z7 = false;
                    if (colorScheme2 != null) {
                    }
                    ((ExecutorImpl) secMediaControlPanel.mMainExecutor).execute(new Runnable() { // from class: com.android.systemui.media.SecMediaControlPanel$$ExternalSyntheticLambda13
                        @Override // java.lang.Runnable
                        public final void run() {
                            String str9;
                            int i21;
                            ColorScheme colorScheme42;
                            int i22;
                            boolean z9;
                            int i23;
                            int i24;
                            CoverMusicCapsuleController coverMusicCapsuleController2;
                            int i25;
                            Bitmap bitmap3;
                            Bitmap bitmap4;
                            ColorScheme colorScheme5;
                            int intValue;
                            SecMediaControlPanel secMediaControlPanel2 = SecMediaControlPanel.this;
                            int i26 = i19;
                            String str10 = str7;
                            int i27 = i20;
                            ColorScheme colorScheme6 = colorScheme2;
                            MediaData mediaData3 = mediaData2;
                            Drawable drawable32 = drawable2;
                            boolean z10 = z8;
                            boolean z11 = z7;
                            Drawable drawable4 = drawable;
                            int i28 = s700;
                            int i29 = i17;
                            int i30 = i18;
                            if (i26 < secMediaControlPanel2.mArtworkBoundId) {
                                Trace.endAsyncSection(str10, i27);
                                return;
                            }
                            if (colorScheme6 != null) {
                                String str11 = mediaData3.packageName;
                                TonalPalette tonalPalette = colorScheme6.accent2;
                                int s800 = tonalPalette.getS800();
                                str9 = str10;
                                TonalPalette tonalPalette2 = colorScheme6.accent1;
                                i21 = i27;
                                colorScheme42 = colorScheme6;
                                PluginFaceWidgetColorScheme pluginFaceWidgetColorScheme = new PluginFaceWidgetColorScheme(s800, tonalPalette2.getS100(), ((Number) ((ArrayList) tonalPalette2.allShades).get(3)).intValue(), tonalPalette.getS700(), tonalPalette2.getS700());
                                boolean z12 = z10 || (!secMediaControlPanel2.mIsArtworkBound && z11);
                                Iterator it = ((ArrayList) secMediaControlPanel2.mFaceWidgetColorSchemeController.mCallbackList).iterator();
                                while (it.hasNext()) {
                                    ((PluginFaceWidgetColorSchemeCallback) it.next()).onColorSchemeUpdated(str11, drawable32, pluginFaceWidgetColorScheme, z12);
                                }
                            } else {
                                str9 = str10;
                                i21 = i27;
                                colorScheme42 = colorScheme6;
                            }
                            secMediaControlPanel2.mArtworkBoundId = i26;
                            ImageView albumView = secMediaControlPanel2.mViewHolder.getAlbumView();
                            albumView.setPadding(0, 0, 0, 0);
                            if (z10 || (!secMediaControlPanel2.mIsArtworkBound && z11)) {
                                if (!secMediaControlPanel2.mType.getSupportCapsule() || (coverMusicCapsuleController2 = secMediaControlPanel2.mCoverMusicCapsuleController) == null) {
                                    i22 = i30;
                                    z9 = z11;
                                    i23 = i29;
                                } else {
                                    String str12 = mediaData3.packageName;
                                    MediaController mediaController2 = secMediaControlPanel2.mController;
                                    Bundle bundle = coverMusicCapsuleController2.bundle;
                                    bundle.putString("capsule_action_pkg", str12);
                                    bundle.putParcelable("capsule_action", mediaData3.clickIntent);
                                    if (drawable4 != null) {
                                        RemoteViews remoteViews = coverMusicCapsuleController2.capsule;
                                        BitmapDrawable bitmapDrawable = drawable4 instanceof BitmapDrawable ? (BitmapDrawable) drawable4 : null;
                                        if (bitmapDrawable == null || (bitmap4 = bitmapDrawable.getBitmap()) == null) {
                                            Bitmap createBitmap = Bitmap.createBitmap(40, 40, Bitmap.Config.ARGB_8888);
                                            Canvas canvas = new Canvas(createBitmap);
                                            i25 = 0;
                                            drawable4.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                                            drawable4.draw(canvas);
                                            bitmap3 = createBitmap;
                                        } else {
                                            bitmap3 = bitmap4;
                                            i25 = 0;
                                        }
                                        Bitmap createBitmap2 = Bitmap.createBitmap(40, 40, Bitmap.Config.ARGB_8888);
                                        Bitmap createScaledBitmap = Bitmap.createScaledBitmap(bitmap3, 40, 40, true);
                                        z9 = z11;
                                        Rect rect2 = new Rect(i25, i25, createScaledBitmap.getWidth(), createScaledBitmap.getHeight());
                                        Paint paint = new Paint();
                                        paint.setAntiAlias(true);
                                        paint.setColor(-12434878);
                                        Canvas canvas2 = new Canvas(createBitmap2);
                                        canvas2.drawARGB(i25, i25, i25, i25);
                                        i22 = i30;
                                        i23 = i29;
                                        canvas2.drawCircle(createScaledBitmap.getWidth() / 2, createScaledBitmap.getHeight() / 2, createScaledBitmap.getWidth() / 2, paint);
                                        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                                        Unit unit = Unit.INSTANCE;
                                        canvas2.drawBitmap(createScaledBitmap, rect2, rect2, paint);
                                        remoteViews.setImageViewBitmap(R.id.sec_media_capsule_album_art, createBitmap2);
                                    } else {
                                        i22 = i30;
                                        z9 = z11;
                                        i23 = i29;
                                    }
                                    bundle.putInt("bg_startColor", i28);
                                    float[] fArr2 = new float[3];
                                    Color.colorToHSV(i28, fArr2);
                                    float f2 = fArr2[0];
                                    fArr2[0] = f2 < 150.0f ? f2 + 40.0f : f2 - 60.0f;
                                    bundle.putInt("bg_endColor", Color.HSVToColor(fArr2));
                                    coverMusicCapsuleController2.updateEqualizerState(mediaController2 != null ? mediaController2.getPlaybackState() : null);
                                }
                                if (secMediaControlPanel2.mPrevArtwork == null) {
                                    albumView.setImageDrawable(drawable32);
                                    i24 = 0;
                                } else {
                                    TransitionDrawable transitionDrawable = new TransitionDrawable(new Drawable[]{secMediaControlPanel2.mPrevArtwork, drawable32});
                                    int i31 = i22;
                                    int i32 = i23;
                                    i24 = 0;
                                    SecMediaControlPanel.scaleTransitionDrawableLayer(transitionDrawable, 0, i32, i31);
                                    SecMediaControlPanel.scaleTransitionDrawableLayer(transitionDrawable, 1, i32, i31);
                                    transitionDrawable.setLayerGravity(0, 17);
                                    transitionDrawable.setLayerGravity(1, 17);
                                    transitionDrawable.setCrossFadeEnabled(true);
                                    albumView.setImageDrawable(transitionDrawable);
                                    transitionDrawable.startTransition(z9 ? 333 : 80);
                                }
                                secMediaControlPanel2.mPrevArtwork = drawable32;
                                secMediaControlPanel2.mIsArtworkBound = z9;
                            } else {
                                i24 = 0;
                            }
                            SecColorSchemeTransition secColorSchemeTransition = secMediaControlPanel2.mColorSchemeTransition;
                            secColorSchemeTransition.isGradientEnabled = secMediaControlPanel2.mIsArtworkBound;
                            SecAnimatingColorTransition[] secAnimatingColorTransitionArr = secColorSchemeTransition.colorTransitions;
                            int length = secAnimatingColorTransitionArr.length;
                            int i33 = i24;
                            while (i33 < length) {
                                SecAnimatingColorTransition secAnimatingColorTransition = secAnimatingColorTransitionArr[i33];
                                if (colorScheme42 == null) {
                                    intValue = secAnimatingColorTransition.defaultColor;
                                    colorScheme5 = colorScheme42;
                                } else {
                                    colorScheme5 = colorScheme42;
                                    intValue = ((Number) secAnimatingColorTransition.extractColor.invoke(colorScheme5)).intValue();
                                }
                                if (intValue != secAnimatingColorTransition.targetColor) {
                                    secAnimatingColorTransition.sourceColor = secAnimatingColorTransition.currentColor;
                                    secAnimatingColorTransition.targetColor = intValue;
                                    secAnimatingColorTransition.valueAnimator.cancel();
                                    secAnimatingColorTransition.valueAnimator.start();
                                }
                                i33++;
                                colorScheme42 = colorScheme5;
                            }
                            Trace.endAsyncSection(str9, i21);
                        }
                    });
                }
            });
        }
        Trace.endSection();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void bindButtonAndVisible(int i, MediaAction mediaAction, boolean z, final int i2, final String str, boolean z2) {
        Drawable.ConstantState constantState;
        final ImageButton actionButton = this.mViewHolder.getActionButton(i, z2);
        if (z) {
            Consumer consumer = new Consumer() { // from class: com.android.systemui.media.SecMediaControlPanel$$ExternalSyntheticLambda14
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    SecMediaControlPanel secMediaControlPanel = SecMediaControlPanel.this;
                    int i3 = i2;
                    String str2 = str;
                    ImageButton imageButton = actionButton;
                    secMediaControlPanel.getClass();
                    SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.sCurrentScreenID, "QPNE0003", "type", Integer.toString(i3), "app", str2);
                    String str3 = secMediaControlPanel.mPlayerKey;
                    int id = imageButton.getId();
                    ((MediaLoggerImpl) secMediaControlPanel.mLogger).onActionClicked(imageButton.getContentDescription(), str3, id);
                }
            };
            if (mediaAction != null) {
                Drawable drawable = mediaAction.icon;
                if (drawable != null && (constantState = drawable.getConstantState()) != null) {
                    Drawable newDrawable = constantState.newDrawable();
                    if (newDrawable instanceof Animatable) {
                        ((Animatable) newDrawable).start();
                    }
                    actionButton.setImageDrawable(newDrawable);
                }
                actionButton.setContentDescription(mediaAction.contentDescription);
                Runnable runnable = mediaAction.action;
                if (runnable == null) {
                    actionButton.setEnabled(false);
                } else {
                    actionButton.setEnabled(true);
                    actionButton.setOnClickListener(new SecMediaControlPanel$$ExternalSyntheticLambda7(consumer, actionButton, 3, runnable));
                }
            }
        }
        actionButton.setVisibility(z ? 0 : 8);
    }

    public final void calculateSongTitleWidth() {
        int dimensionPixelSize;
        int dimensionPixelSize2;
        boolean z = QpRune.QUICK_TABLET;
        Context context = this.mContext;
        if (z) {
            dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.sec_qs_media_player_action_button_size_collapsed_tablet);
            dimensionPixelSize2 = (int) (context.getResources().getFloat(R.dimen.sec_qs_media_player_action_button_collapsed_margin_size_tablet) * this.mWidth);
        } else {
            dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.sec_qs_media_player_action_button_size_collapsed);
            dimensionPixelSize2 = context.getResources().getDimensionPixelSize(R.dimen.sec_qs_media_player_action_button_collapsed_margin_size);
        }
        int i = this.mBudsEnabled ? this.mActionButtonNumCollapsed + 1 : this.mActionButtonNumCollapsed;
        int dimensionPixelSize3 = context.getResources().getDimensionPixelSize(R.dimen.sec_qs_media_button_left_padding) + ((i - 1) * dimensionPixelSize2) + (dimensionPixelSize * i);
        int dimensionPixelSize4 = (context.getResources().getDimensionPixelSize(R.dimen.sec_media_view_output_switcher_padding_end) * 2) + context.getResources().getDimensionPixelSize(R.dimen.sec_qs_media_player_padding_start);
        StringBuilder sb = new StringBuilder("budsEnabled = ");
        sb.append(this.mBudsEnabled);
        sb.append(", mActionButtonNumCollapsed = ");
        sb.append(this.mActionButtonNumCollapsed);
        sb.append(", mActionButtonNumExpand = ");
        RecyclerView$$ExternalSyntheticOutline0.m46m(sb, this.mActionButtonNumExpand, "MediaControlPanel");
        this.mRemainWidthCollapsed = this.mWidth - ((context.getResources().getDimensionPixelSize(R.dimen.sec_qs_media_album_size) + dimensionPixelSize3) + dimensionPixelSize4);
        this.mRemainWidthExpand = this.mWidth - (context.getResources().getDimensionPixelSize(R.dimen.sec_media_expanded_view_start_end_padding) * 2);
    }

    public final boolean expandIconNeedToShow() {
        int value = this.mLastConfigurationState.getValue(ConfigurationState.ConfigurationField.ORIENTATION);
        if (value == 1) {
            return true;
        }
        if (value != 2) {
            if (this.mContext.getResources().getConfiguration().orientation == 1 || QpRune.QUICK_TABLET) {
                return true;
            }
        } else if (QpRune.QUICK_TABLET) {
            return true;
        }
        return false;
    }

    public final float getPlayerExpandedFraction() {
        MediaType mediaType = this.mType;
        if (mediaType != null && !mediaType.getSupportExpandable()) {
            return 1.0f;
        }
        this.mResourcePicker.getClass();
        Context context = this.mContext;
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.sec_qs_media_player_height_expanded);
        int dimensionPixelSize2 = context.getResources().getDimensionPixelSize(R.dimen.sec_qs_media_player_height_collapsed);
        return (this.mHeight - dimensionPixelSize2) / (dimensionPixelSize - dimensionPixelSize2);
    }

    public final boolean isDisabledPlayer() {
        return this.mContext.getResources().getConfiguration().orientation == 2 && this.mFullyExpanded;
    }

    public final boolean isPlaying() {
        PlaybackState playbackState;
        MediaController mediaController = this.mController;
        return (mediaController == null || (playbackState = mediaController.getPlaybackState()) == null || playbackState.getState() != 3) ? false : true;
    }

    public final void removePlayer() {
        StringBuilder sb = new StringBuilder("Removing player from QSPanel : ");
        TextView textView = this.mViewHolder.appName;
        if (textView == null) {
            textView = null;
        }
        sb.append((Object) textView.getText());
        Log.d("MediaControlPanel", sb.toString());
        SecMediaHost.C17871 c17871 = this.mQSMediaPlayerBarCallback;
        if (c17871 != null) {
            String str = this.mPlayerKey;
            SecMediaHost secMediaHost = SecMediaHost.this;
            secMediaHost.mMediaDataManager.onMediaDataRemoved(str);
            if (c17871.val$playerData.getSortedMediaPlayersSize() <= 0) {
                secMediaHost.onMediaVisibilityChanged(Boolean.FALSE);
                BarController.C20874 c20874 = secMediaHost.mMediaBarCallback;
                if (c20874 == null) {
                    return;
                }
                c20874.onBarHeightChanged();
            }
        }
    }

    public final void setBackgroundColor(int i) {
        if (this.mIsEmptyPlayer) {
            return;
        }
        View view = this.mViewHolder.playerView;
        if (view == null) {
            view = null;
        }
        view.setBackgroundColor(i);
    }

    public final void setFraction(float f) {
        if (f == 0.0f || f == 1.0f) {
            updateExpandAnimator();
            updateActionButtonEnabled(f);
        }
        this.mFraction = f;
        updateSeekBarVisibility();
        calculateSongTitleWidth();
        setSongTitleWidth(f);
        TouchAnimator touchAnimator = this.mOutputSwitcherAlphaAnimator;
        if (touchAnimator != null) {
            touchAnimator.setPosition(f);
        }
        TouchAnimator touchAnimator2 = this.mExpandIconRotationAnimator;
        if (touchAnimator2 != null) {
            touchAnimator2.setPosition(f);
        }
        Iterator it = this.mActionXListAnimator.iterator();
        while (it.hasNext()) {
            ((TouchAnimator) it.next()).setPosition(f);
        }
        TouchAnimator touchAnimator3 = this.mExpandContentsAnimator;
        if (touchAnimator3 != null) {
            touchAnimator3.setPosition(f);
        }
        TouchAnimator touchAnimator4 = this.mExpandGutsAnimator;
        if (touchAnimator4 != null) {
            touchAnimator4.setPosition(f);
        }
        TouchAnimator touchAnimator5 = this.mAlbumArtAnimator;
        if (touchAnimator5 != null) {
            touchAnimator5.setPosition(f);
        }
        TouchAnimator touchAnimator6 = this.mActionButtonsAnimator;
        if (touchAnimator6 != null) {
            touchAnimator6.setPosition(f);
        }
        ValueAnimator valueAnimator = this.mPrimaryTextColorAnimator;
        if (valueAnimator != null) {
            valueAnimator.setCurrentFraction(f);
        }
        ValueAnimator valueAnimator2 = this.mSecondaryTextColorAnimator;
        if (valueAnimator2 != null) {
            valueAnimator2.setCurrentFraction(f);
        }
        ValueAnimator valueAnimator3 = this.mTertiaryTextColorAnimator;
        if (valueAnimator3 != null) {
            valueAnimator3.setCurrentFraction(f);
        }
        ValueAnimator valueAnimator4 = this.mExpandButtonColorAnimator;
        if (valueAnimator4 != null) {
            valueAnimator4.setCurrentFraction(f);
        }
        ValueAnimator valueAnimator5 = this.mRemoveButtonColorAnimator;
        if (valueAnimator5 != null) {
            valueAnimator5.setCurrentFraction(f);
        }
    }

    public final void setListening(boolean z) {
        if (this.mType.getSupportExpandable()) {
            SecSeekBarViewModel secSeekBarViewModel = this.mSeekBarViewModel;
            secSeekBarViewModel.getClass();
            ((RepeatableExecutorImpl) secSeekBarViewModel.bgExecutor).execute(new SecSeekBarViewModel$listening$1(secSeekBarViewModel, z));
            SecPlayerViewHolder secPlayerViewHolder = this.mViewHolder;
            if (secPlayerViewHolder != null) {
                LinearLayout linearLayout = secPlayerViewHolder.player;
                if (linearLayout != null) {
                    linearLayout.setVisibility(0);
                }
                View view = this.mViewHolder.options;
                if (view != null) {
                    view.setVisibility(8);
                }
                setBackgroundColor(this.mBackgroundColor);
            }
            if (z) {
                updateExpandAnimator();
                updateOutputSwitcherVisibility();
                calculateSongTitleWidth();
                setSongTitleWidth(this.mExpanded ? 1.0f : 0.0f);
            }
        }
    }

    public final void setSongTitleWidth(float f) {
        LinearLayout linearLayout;
        if (this.mRemainWidthCollapsed <= 0 || this.mRemainWidthExpand <= 0 || (linearLayout = this.mViewHolder.titleArtistView) == null) {
            return;
        }
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) linearLayout.getLayoutParams();
        if (f > 0.8d) {
            layoutParams.width = this.mRemainWidthExpand;
        } else {
            layoutParams.width = this.mRemainWidthCollapsed;
        }
        this.mViewHolder.titleArtistView.setLayoutParams(layoutParams);
    }

    public final String toString() {
        return "SecMediaControlPanel{mPlayerKey='" + this.mPlayerKey + "', this=" + super.toString() + '}';
    }

    public final void updateActionButtonEnabled(float f) {
        boolean isDisabledPlayer = isDisabledPlayer();
        int[] iArr = ACTION_IDS;
        if (isDisabledPlayer) {
            for (int i = 0; i < 5; i++) {
                int i2 = iArr[i];
                this.mViewHolder.getActionButton(i2, false).setEnabled(false);
                this.mViewHolder.getActionButton(i2, true).setEnabled(false);
            }
            return;
        }
        for (int i3 = 0; i3 < 5; i3++) {
            int i4 = iArr[i3];
            this.mViewHolder.getActionButton(i4, false).setEnabled(true);
            this.mViewHolder.getActionButton(i4, true).setEnabled(f != 0.0f);
        }
    }

    public final void updateBudsButton() {
        if (!this.mType.getSupportBudsButton() || this.mBudsButtonCollapsed == null || this.mBudsButtonExpanded == null) {
            return;
        }
        this.mBudsEnabled = this.mSettingsHelper.getBudsEnable();
        ActionBarContextView$$ExternalSyntheticOutline0.m9m(new StringBuilder("onChanged(): buds enabled: "), this.mBudsEnabled, "MediaControlPanel");
        if (!this.mBudsEnabled) {
            this.mBudsDetailCloseRunnable.run();
        }
        this.mBudsButtonCollapsed.setVisibility(this.mBudsEnabled ? 0 : 8);
        this.mBudsButtonExpanded.setVisibility(this.mBudsEnabled ? 0 : 8);
        this.mBudsButtonCollapsed.setEnabled(this.mBudsEnabled);
        this.mBudsButtonExpanded.setEnabled(this.mBudsEnabled);
    }

    public final void updateDeviceName() {
        TextView textView = this.mViewHolder.seamlessText;
        if (textView == null) {
            textView = null;
        }
        StringBuilder sb = new StringBuilder("updateDeviceName() : deviceName = ");
        sb.append((Object) this.mDeviceName);
        sb.append(", mDualPlayModeEnabled = ");
        ActionBarContextView$$ExternalSyntheticOutline0.m9m(sb, this.mDualPlayModeEnabled, "MediaControlPanel");
        boolean z = this.mDualPlayModeEnabled;
        Context context = this.mContext;
        textView.setText(z ? context.getString(R.string.sec_qs_media_player_dual_play_mode_information) : TextUtils.isEmpty(this.mDeviceName) ? context.getString(R.string.sec_media_output_device) : this.mDeviceName);
        updateFontSize(R.dimen.sec_qs_media_player_device_name_text_size, textView);
        textView.setVisibility(0);
        if (this.mType.getSupportBudsButton()) {
            this.mBudsButtonCollapsed.setContentDescription(context.getString(R.string.sec_qs_buds_button_content_description, this.mDeviceName));
            this.mBudsButtonExpanded.setContentDescription(context.getString(R.string.sec_qs_buds_button_content_description, this.mDeviceName));
        }
    }

    public final void updateExpandAnimator() {
        if (this.mViewHolder == null || !this.mType.getSupportExpandable()) {
            return;
        }
        this.mActionXListAnimator.clear();
        ImageView imageView = this.mViewHolder.albumThumbnail;
        final int i = 1;
        final int i2 = 0;
        Context context = this.mContext;
        final int i3 = 2;
        if (imageView != null) {
            boolean z = imageView.getVisibility() != 8;
            int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.sec_media_animation_translation_x);
            if ((context.getResources().getConfiguration().screenLayout & 192) == 128) {
                dimensionPixelSize = -dimensionPixelSize;
            }
            TouchAnimator.Builder builder = new TouchAnimator.Builder();
            builder.addFloat(imageView, "alpha", 1.0f, 0.0f);
            ImageView albumView = this.mViewHolder.getAlbumView();
            float[] fArr = new float[2];
            fArr[0] = 0.0f;
            fArr[1] = this.mIsEmptyPlayer ? 0.0f : 1.0f;
            builder.addFloat(albumView, "alpha", fArr);
            ImageView albumView2 = this.mViewHolder.getAlbumView();
            float[] fArr2 = new float[2];
            fArr2[0] = this.mIsEmptyPlayer ? 0.0f : -50.0f;
            fArr2[1] = 0.0f;
            builder.addFloat(albumView2, "translationY", fArr2);
            if (z) {
                float f = -dimensionPixelSize;
                builder.addFloat(this.mViewHolder.titleArtistView, "translationX", 0.0f, f);
                ImageView imageView2 = this.mViewHolder.appIcon;
                if (imageView2 == null) {
                    imageView2 = null;
                }
                builder.addFloat(imageView2, "translationX", 0.0f, f);
                TextView textView = this.mViewHolder.seamlessText;
                if (textView == null) {
                    textView = null;
                }
                builder.addFloat(textView, "translationX", 0.0f, f);
                builder.build();
            }
            this.mAlbumArtAnimator = builder.build();
        }
        TouchAnimator.Builder builder2 = new TouchAnimator.Builder();
        TextView textView2 = this.mViewHolder.mediaOutputText;
        if (textView2 == null) {
            textView2 = null;
        }
        builder2.addFloat(textView2, "alpha", 0.0f, 1.0f);
        TextView textView3 = this.mViewHolder.mediaOutputText;
        builder2.addFloat(textView3 != null ? textView3 : null, "translationX", 30.0f, 0.0f);
        builder2.mListener = new TouchAnimator.Listener() { // from class: com.android.systemui.media.SecMediaControlPanel.5
            @Override // com.android.systemui.qs.TouchAnimator.Listener
            public final void onAnimationAtEnd() {
                SecMediaControlPanel.this.updateOutputSwitcherVisibility();
            }

            @Override // com.android.systemui.qs.TouchAnimator.Listener
            public final void onAnimationAtStart() {
                SecMediaControlPanel.this.updateOutputSwitcherVisibility();
            }

            @Override // com.android.systemui.qs.TouchAnimator.Listener
            public final void onAnimationStarted() {
                SecMediaControlPanel.this.updateOutputSwitcherVisibility();
            }
        };
        this.mOutputSwitcherAlphaAnimator = builder2.build();
        final ImageView imageView3 = this.mViewHolder.expandIcon;
        if (imageView3 != null) {
            TouchAnimator.Builder builder3 = new TouchAnimator.Builder();
            builder3.addFloat(imageView3, "rotation", 360.0f, 180.0f);
            this.mExpandIconRotationAnimator = builder3.build();
        }
        TouchAnimator.Builder builder4 = new TouchAnimator.Builder();
        builder4.addFloat(this.mViewHolder.getCollapsedActionButtonsContainer(), "alpha", 1.0f, 0.0f);
        builder4.mListener = new TouchAnimator.Listener() { // from class: com.android.systemui.media.SecMediaControlPanel.6
            @Override // com.android.systemui.qs.TouchAnimator.Listener
            public final void onAnimationAtEnd() {
                SecMediaControlPanel.this.mViewHolder.getCollapsedActionButtonsContainer().setVisibility(8);
            }

            @Override // com.android.systemui.qs.TouchAnimator.Listener
            public final void onAnimationAtStart() {
                SecMediaControlPanel.this.mViewHolder.getCollapsedActionButtonsContainer().setVisibility(0);
            }

            @Override // com.android.systemui.qs.TouchAnimator.Listener
            public final void onAnimationStarted() {
                SecMediaControlPanel.this.mViewHolder.getCollapsedActionButtonsContainer().setVisibility(0);
            }
        };
        this.mActionButtonsAnimator = builder4.build();
        int color = context.getColor(R.color.media_primary_text);
        int color2 = context.getColor(R.color.media_secondary_text);
        int color3 = context.getColor(R.color.media_tertiary_text);
        int color4 = context.getColor(R.color.media_expand_primary_text);
        int color5 = context.getColor(R.color.media_expand_secondary_text);
        int color6 = context.getColor(R.color.media_expand_tertiary_text);
        ValueAnimator ofObject = ValueAnimator.ofObject(new ArgbEvaluator(), Integer.valueOf(color), Integer.valueOf(color4));
        this.mPrimaryTextColorAnimator = ofObject;
        ofObject.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.media.SecMediaControlPanel$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                switch (i2) {
                    case 0:
                        SecMediaControlPanel secMediaControlPanel = (SecMediaControlPanel) this;
                        secMediaControlPanel.getClass();
                        int intValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                        TextView textView4 = secMediaControlPanel.mViewHolder.titleText;
                        (textView4 != null ? textView4 : null).setTextColor(intValue);
                        TextView textView5 = secMediaControlPanel.mViewHolder.removeText;
                        if (textView5 != null) {
                            textView5.setTextColor(intValue);
                        }
                        TextView textView6 = secMediaControlPanel.mViewHolder.cancelText;
                        if (textView6 != null) {
                            textView6.setTextColor(intValue);
                            break;
                        }
                        break;
                    case 1:
                        TextView textView7 = ((SecMediaControlPanel) this).mViewHolder.artistText;
                        (textView7 != null ? textView7 : null).setTextColor(((Integer) valueAnimator.getAnimatedValue()).intValue());
                        break;
                    case 2:
                        SecMediaControlPanel secMediaControlPanel2 = (SecMediaControlPanel) this;
                        secMediaControlPanel2.getClass();
                        int intValue2 = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                        ImageView imageView4 = secMediaControlPanel2.mViewHolder.appIcon;
                        if (imageView4 == null) {
                            imageView4 = null;
                        }
                        imageView4.setColorFilter(intValue2);
                        TextView textView8 = secMediaControlPanel2.mViewHolder.seamlessText;
                        (textView8 != null ? textView8 : null).setTextColor(intValue2);
                        TextView textView9 = secMediaControlPanel2.mViewHolder.optionsAppTitle;
                        if (textView9 != null) {
                            textView9.setTextColor(intValue2);
                        }
                        ImageView imageView5 = secMediaControlPanel2.mViewHolder.optionsAppIcon;
                        if (imageView5 != null) {
                            imageView5.setColorFilter(intValue2);
                            break;
                        }
                        break;
                    case 3:
                        TextView textView10 = ((SecMediaControlPanel) this).mViewHolder.removeText;
                        if (textView10 != null) {
                            Drawable background = textView10.getBackground();
                            if (background instanceof RippleDrawable) {
                                ((GradientDrawable) ((RippleDrawable) background).getDrawable(0)).setColor(((Integer) valueAnimator.getAnimatedValue()).intValue());
                                break;
                            }
                        }
                        break;
                    default:
                        ((ImageView) this).setColorFilter(((Integer) valueAnimator.getAnimatedValue()).intValue());
                        break;
                }
            }
        });
        ValueAnimator ofObject2 = ValueAnimator.ofObject(new ArgbEvaluator(), Integer.valueOf(color2), Integer.valueOf(color5));
        this.mSecondaryTextColorAnimator = ofObject2;
        ofObject2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.media.SecMediaControlPanel$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                switch (i) {
                    case 0:
                        SecMediaControlPanel secMediaControlPanel = (SecMediaControlPanel) this;
                        secMediaControlPanel.getClass();
                        int intValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                        TextView textView4 = secMediaControlPanel.mViewHolder.titleText;
                        (textView4 != null ? textView4 : null).setTextColor(intValue);
                        TextView textView5 = secMediaControlPanel.mViewHolder.removeText;
                        if (textView5 != null) {
                            textView5.setTextColor(intValue);
                        }
                        TextView textView6 = secMediaControlPanel.mViewHolder.cancelText;
                        if (textView6 != null) {
                            textView6.setTextColor(intValue);
                            break;
                        }
                        break;
                    case 1:
                        TextView textView7 = ((SecMediaControlPanel) this).mViewHolder.artistText;
                        (textView7 != null ? textView7 : null).setTextColor(((Integer) valueAnimator.getAnimatedValue()).intValue());
                        break;
                    case 2:
                        SecMediaControlPanel secMediaControlPanel2 = (SecMediaControlPanel) this;
                        secMediaControlPanel2.getClass();
                        int intValue2 = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                        ImageView imageView4 = secMediaControlPanel2.mViewHolder.appIcon;
                        if (imageView4 == null) {
                            imageView4 = null;
                        }
                        imageView4.setColorFilter(intValue2);
                        TextView textView8 = secMediaControlPanel2.mViewHolder.seamlessText;
                        (textView8 != null ? textView8 : null).setTextColor(intValue2);
                        TextView textView9 = secMediaControlPanel2.mViewHolder.optionsAppTitle;
                        if (textView9 != null) {
                            textView9.setTextColor(intValue2);
                        }
                        ImageView imageView5 = secMediaControlPanel2.mViewHolder.optionsAppIcon;
                        if (imageView5 != null) {
                            imageView5.setColorFilter(intValue2);
                            break;
                        }
                        break;
                    case 3:
                        TextView textView10 = ((SecMediaControlPanel) this).mViewHolder.removeText;
                        if (textView10 != null) {
                            Drawable background = textView10.getBackground();
                            if (background instanceof RippleDrawable) {
                                ((GradientDrawable) ((RippleDrawable) background).getDrawable(0)).setColor(((Integer) valueAnimator.getAnimatedValue()).intValue());
                                break;
                            }
                        }
                        break;
                    default:
                        ((ImageView) this).setColorFilter(((Integer) valueAnimator.getAnimatedValue()).intValue());
                        break;
                }
            }
        });
        ValueAnimator ofObject3 = ValueAnimator.ofObject(new ArgbEvaluator(), Integer.valueOf(color3), Integer.valueOf(color6));
        this.mTertiaryTextColorAnimator = ofObject3;
        ofObject3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.media.SecMediaControlPanel$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                switch (i3) {
                    case 0:
                        SecMediaControlPanel secMediaControlPanel = (SecMediaControlPanel) this;
                        secMediaControlPanel.getClass();
                        int intValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                        TextView textView4 = secMediaControlPanel.mViewHolder.titleText;
                        (textView4 != null ? textView4 : null).setTextColor(intValue);
                        TextView textView5 = secMediaControlPanel.mViewHolder.removeText;
                        if (textView5 != null) {
                            textView5.setTextColor(intValue);
                        }
                        TextView textView6 = secMediaControlPanel.mViewHolder.cancelText;
                        if (textView6 != null) {
                            textView6.setTextColor(intValue);
                            break;
                        }
                        break;
                    case 1:
                        TextView textView7 = ((SecMediaControlPanel) this).mViewHolder.artistText;
                        (textView7 != null ? textView7 : null).setTextColor(((Integer) valueAnimator.getAnimatedValue()).intValue());
                        break;
                    case 2:
                        SecMediaControlPanel secMediaControlPanel2 = (SecMediaControlPanel) this;
                        secMediaControlPanel2.getClass();
                        int intValue2 = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                        ImageView imageView4 = secMediaControlPanel2.mViewHolder.appIcon;
                        if (imageView4 == null) {
                            imageView4 = null;
                        }
                        imageView4.setColorFilter(intValue2);
                        TextView textView8 = secMediaControlPanel2.mViewHolder.seamlessText;
                        (textView8 != null ? textView8 : null).setTextColor(intValue2);
                        TextView textView9 = secMediaControlPanel2.mViewHolder.optionsAppTitle;
                        if (textView9 != null) {
                            textView9.setTextColor(intValue2);
                        }
                        ImageView imageView5 = secMediaControlPanel2.mViewHolder.optionsAppIcon;
                        if (imageView5 != null) {
                            imageView5.setColorFilter(intValue2);
                            break;
                        }
                        break;
                    case 3:
                        TextView textView10 = ((SecMediaControlPanel) this).mViewHolder.removeText;
                        if (textView10 != null) {
                            Drawable background = textView10.getBackground();
                            if (background instanceof RippleDrawable) {
                                ((GradientDrawable) ((RippleDrawable) background).getDrawable(0)).setColor(((Integer) valueAnimator.getAnimatedValue()).intValue());
                                break;
                            }
                        }
                        break;
                    default:
                        ((ImageView) this).setColorFilter(((Integer) valueAnimator.getAnimatedValue()).intValue());
                        break;
                }
            }
        });
        if (imageView3 != null) {
            ValueAnimator ofObject4 = ValueAnimator.ofObject(new ArgbEvaluator(), Integer.valueOf(context.getColor(R.color.media_toggle_button_collapsed_color)), Integer.valueOf(context.getColor(R.color.media_toggle_button_expanded_color)));
            this.mExpandButtonColorAnimator = ofObject4;
            final int i4 = 4;
            ofObject4.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.media.SecMediaControlPanel$$ExternalSyntheticLambda0
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    switch (i4) {
                        case 0:
                            SecMediaControlPanel secMediaControlPanel = (SecMediaControlPanel) imageView3;
                            secMediaControlPanel.getClass();
                            int intValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                            TextView textView4 = secMediaControlPanel.mViewHolder.titleText;
                            (textView4 != null ? textView4 : null).setTextColor(intValue);
                            TextView textView5 = secMediaControlPanel.mViewHolder.removeText;
                            if (textView5 != null) {
                                textView5.setTextColor(intValue);
                            }
                            TextView textView6 = secMediaControlPanel.mViewHolder.cancelText;
                            if (textView6 != null) {
                                textView6.setTextColor(intValue);
                                break;
                            }
                            break;
                        case 1:
                            TextView textView7 = ((SecMediaControlPanel) imageView3).mViewHolder.artistText;
                            (textView7 != null ? textView7 : null).setTextColor(((Integer) valueAnimator.getAnimatedValue()).intValue());
                            break;
                        case 2:
                            SecMediaControlPanel secMediaControlPanel2 = (SecMediaControlPanel) imageView3;
                            secMediaControlPanel2.getClass();
                            int intValue2 = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                            ImageView imageView4 = secMediaControlPanel2.mViewHolder.appIcon;
                            if (imageView4 == null) {
                                imageView4 = null;
                            }
                            imageView4.setColorFilter(intValue2);
                            TextView textView8 = secMediaControlPanel2.mViewHolder.seamlessText;
                            (textView8 != null ? textView8 : null).setTextColor(intValue2);
                            TextView textView9 = secMediaControlPanel2.mViewHolder.optionsAppTitle;
                            if (textView9 != null) {
                                textView9.setTextColor(intValue2);
                            }
                            ImageView imageView5 = secMediaControlPanel2.mViewHolder.optionsAppIcon;
                            if (imageView5 != null) {
                                imageView5.setColorFilter(intValue2);
                                break;
                            }
                            break;
                        case 3:
                            TextView textView10 = ((SecMediaControlPanel) imageView3).mViewHolder.removeText;
                            if (textView10 != null) {
                                Drawable background = textView10.getBackground();
                                if (background instanceof RippleDrawable) {
                                    ((GradientDrawable) ((RippleDrawable) background).getDrawable(0)).setColor(((Integer) valueAnimator.getAnimatedValue()).intValue());
                                    break;
                                }
                            }
                            break;
                        default:
                            ((ImageView) imageView3).setColorFilter(((Integer) valueAnimator.getAnimatedValue()).intValue());
                            break;
                    }
                }
            });
        }
        TouchAnimator.Builder builder5 = new TouchAnimator.Builder();
        LinearLayout linearLayout = this.mViewHolder.player;
        if (linearLayout != null) {
            View findViewById = linearLayout.findViewById(R.id.action_buttons_expanded);
            builder5.addFloat(findViewById, "translationY", -50.0f, 0.0f);
            builder5.addFloat(findViewById, "alpha", 0.0f, 1.0f);
            LinearLayout linearLayout2 = this.mViewHolder.progressInfo;
            if (linearLayout2 != null) {
                builder5.addFloat(linearLayout2, "translationY", -50.0f, 0.0f);
                builder5.addFloat(linearLayout2, "alpha", 0.0f, 1.0f);
            }
        }
        builder5.mStartDelay = 0.6f;
        this.mExpandContentsAnimator = builder5.build();
        TouchAnimator.Builder builder6 = new TouchAnimator.Builder();
        View view = this.mViewHolder.remove;
        if (view != null) {
            builder6.addFloat(view, "translationY", this.mRemoveButtonCollapsed, this.mRemoveButtonExpanded);
        }
        View view2 = this.mViewHolder.optionButtons;
        if (view2 != null) {
            builder6.addFloat(view2, "translationY", this.mOptionButtonCollapsed, this.mOptionButtonExpanded);
        }
        this.mExpandGutsAnimator = builder6.build();
        ValueAnimator ofObject5 = ValueAnimator.ofObject(new ArgbEvaluator(), Integer.valueOf(context.getColor(R.color.sec_qs_media_player_guts_remove_background_color)), Integer.valueOf(context.getColor(R.color.sec_qs_media_player_guts_remove_background_color_expanded)));
        this.mRemoveButtonColorAnimator = ofObject5;
        final int i5 = 3;
        ofObject5.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.media.SecMediaControlPanel$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                switch (i5) {
                    case 0:
                        SecMediaControlPanel secMediaControlPanel = (SecMediaControlPanel) this;
                        secMediaControlPanel.getClass();
                        int intValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                        TextView textView4 = secMediaControlPanel.mViewHolder.titleText;
                        (textView4 != null ? textView4 : null).setTextColor(intValue);
                        TextView textView5 = secMediaControlPanel.mViewHolder.removeText;
                        if (textView5 != null) {
                            textView5.setTextColor(intValue);
                        }
                        TextView textView6 = secMediaControlPanel.mViewHolder.cancelText;
                        if (textView6 != null) {
                            textView6.setTextColor(intValue);
                            break;
                        }
                        break;
                    case 1:
                        TextView textView7 = ((SecMediaControlPanel) this).mViewHolder.artistText;
                        (textView7 != null ? textView7 : null).setTextColor(((Integer) valueAnimator.getAnimatedValue()).intValue());
                        break;
                    case 2:
                        SecMediaControlPanel secMediaControlPanel2 = (SecMediaControlPanel) this;
                        secMediaControlPanel2.getClass();
                        int intValue2 = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                        ImageView imageView4 = secMediaControlPanel2.mViewHolder.appIcon;
                        if (imageView4 == null) {
                            imageView4 = null;
                        }
                        imageView4.setColorFilter(intValue2);
                        TextView textView8 = secMediaControlPanel2.mViewHolder.seamlessText;
                        (textView8 != null ? textView8 : null).setTextColor(intValue2);
                        TextView textView9 = secMediaControlPanel2.mViewHolder.optionsAppTitle;
                        if (textView9 != null) {
                            textView9.setTextColor(intValue2);
                        }
                        ImageView imageView5 = secMediaControlPanel2.mViewHolder.optionsAppIcon;
                        if (imageView5 != null) {
                            imageView5.setColorFilter(intValue2);
                            break;
                        }
                        break;
                    case 3:
                        TextView textView10 = ((SecMediaControlPanel) this).mViewHolder.removeText;
                        if (textView10 != null) {
                            Drawable background = textView10.getBackground();
                            if (background instanceof RippleDrawable) {
                                ((GradientDrawable) ((RippleDrawable) background).getDrawable(0)).setColor(((Integer) valueAnimator.getAnimatedValue()).intValue());
                                break;
                            }
                        }
                        break;
                    default:
                        ((ImageView) this).setColorFilter(((Integer) valueAnimator.getAnimatedValue()).intValue());
                        break;
                }
            }
        });
        updateSeekBarVisibility();
    }

    public final void updateFontSize(int i, TextView textView) {
        if (this.mType.getSupportFixedFontSize()) {
            return;
        }
        FontSizeUtils.updateFontSize(textView, i, 0.8f, 1.3f);
    }

    public final void updateOutputSwitcherVisibility() {
        if (this.mIsEmptyPlayer) {
            return;
        }
        boolean z = !(this.mType.getSupportExpandable() && this.mMediaDevicesAvailableOnTop) && this.mFraction > 0.0f;
        TextView textView = this.mViewHolder.mediaOutputText;
        if (textView == null) {
            textView = null;
        }
        textView.setVisibility(z ? 0 : 4);
    }

    public final void updateResources() {
        if (this.mType.getSupportGuts()) {
            Context context = this.mContext;
            int color = context.getColor(R.color.media_primary_text);
            int color2 = context.getColor(R.color.media_secondary_text);
            int color3 = context.getColor(R.color.media_tertiary_text);
            int color4 = context.getColor(R.color.media_expand_primary_text);
            int color5 = context.getColor(R.color.media_expand_secondary_text);
            int color6 = context.getColor(R.color.media_expand_tertiary_text);
            SecPlayerViewHolder secPlayerViewHolder = this.mViewHolder;
            SparseArray sparseArray = (SparseArray) secPlayerViewHolder.collapsedActionButtons$delegate.getValue();
            int size = sparseArray.size();
            for (int i = 0; i < size; i++) {
                sparseArray.keyAt(i);
                ((ImageButton) sparseArray.valueAt(i)).setColorFilter(color);
            }
            ImageButton imageButton = secPlayerViewHolder.budsButtonCollapsed;
            if (imageButton == null) {
                imageButton = null;
            }
            imageButton.setColorFilter(color);
            ColorStateList valueOf = ColorStateList.valueOf(color);
            SeekBar seekBar = secPlayerViewHolder.getSeekBar();
            seekBar.setThumbTintList(valueOf);
            seekBar.setProgressTintList(valueOf);
            seekBar.setProgressBackgroundTintList(valueOf.withAlpha(76));
            SecPlayerViewHolder secPlayerViewHolder2 = this.mViewHolder;
            boolean z = this.mExpanded;
            if (z) {
                color = color4;
            }
            if (z) {
                color2 = color5;
            }
            if (z) {
                color3 = color6;
            }
            TextView textView = secPlayerViewHolder2.titleText;
            if (textView == null) {
                textView = null;
            }
            textView.setTextColor(color);
            TextView textView2 = secPlayerViewHolder2.artistText;
            if (textView2 == null) {
                textView2 = null;
            }
            textView2.setTextColor(color2);
            ImageView imageView = secPlayerViewHolder2.appIcon;
            if (imageView == null) {
                imageView = null;
            }
            imageView.setColorFilter(color3);
            TextView textView3 = secPlayerViewHolder2.seamlessText;
            if (textView3 == null) {
                textView3 = null;
            }
            textView3.setTextColor(color3);
            this.mGutsBackgroundColor = context.getColor(R.color.sec_qs_media_player_guts_background_color);
            Resources resources = context.getResources();
            boolean z2 = QpRune.QUICK_TABLET;
            this.mOptionButtonCollapsed = resources.getDimensionPixelSize(z2 ? R.dimen.sec_qs_media_player_guts_option_button_location_collapsed_tablet : R.dimen.sec_qs_media_player_guts_option_button_location_collapsed);
            this.mOptionButtonExpanded = context.getResources().getDimensionPixelSize(z2 ? R.dimen.sec_qs_media_player_guts_option_button_location_expanded_tablet : R.dimen.sec_qs_media_player_guts_option_button_location_expanded);
            this.mRemoveButtonCollapsed = context.getResources().getDimensionPixelSize(z2 ? R.dimen.sec_qs_media_player_guts_remove_button_location_collapsed_tablet : R.dimen.sec_qs_media_player_guts_remove_button_location_collapsed);
            this.mRemoveButtonExpanded = context.getResources().getDimensionPixelSize(z2 ? R.dimen.sec_qs_media_player_guts_remove_button_location_expanded_tablet : R.dimen.sec_qs_media_player_guts_remove_button_location_expanded);
            TextView textView4 = this.mViewHolder.optionsAppTitle;
            if (textView4 != null) {
                textView4.setTextColor(context.getColor(R.color.sec_qs_media_player_guts_header_color));
            }
            int color7 = context.getColor(R.color.sec_qs_media_player_guts_buttons_color);
            TextView textView5 = this.mViewHolder.removeText;
            if (textView5 != null) {
                textView5.setTextColor(color7);
                textView5.setBackground(context.getDrawable(R.drawable.sec_qs_media_player_guts_button_background));
            }
            TextView textView6 = this.mViewHolder.cancelText;
            if (textView6 != null) {
                textView6.setTextColor(color7);
                textView6.setBackground(context.getDrawable(R.drawable.notification_guts_button_bg));
            }
            int color8 = context.getColor(R.color.sec_qs_media_player_background_color);
            this.mBackgroundColor = color8;
            setBackgroundColor(color8);
            ImageView imageView2 = this.mViewHolder.expandIcon;
            if (imageView2 != null) {
                imageView2.setColorFilter(this.mExpanded ? context.getColor(R.color.media_toggle_button_expanded_color) : context.getColor(R.color.media_toggle_button_collapsed_color), PorterDuff.Mode.SRC_IN);
            }
            TextView textView7 = this.mViewHolder.mediaOutputText;
            (textView7 != null ? textView7 : null).setBackground(context.getDrawable(R.drawable.sec_media_output_button_ripple_background));
            TextView textView8 = this.mViewHolder.cancelText;
            if (textView8 != null) {
                textView8.semSetButtonShapeEnabled(true);
            }
        }
    }

    public final void updateSeekBarVisibility() {
        if (this.mViewHolder == null) {
            return;
        }
        int i = 8;
        if (isDisabledPlayer()) {
            this.mViewHolder.getSeekBar().setVisibility(8);
            return;
        }
        SeekBar seekBar = this.mViewHolder.getSeekBar();
        if (this.mFraction != 0.0f && !this.mIsEmptyPlayer) {
            i = 0;
        }
        seekBar.setVisibility(i);
        int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.sec_media_view_seek_bar_padding);
        this.mViewHolder.getSeekBar().setPadding(dimensionPixelSize, 0, dimensionPixelSize, 0);
    }

    public final void updateWidth() {
        int panelWidth;
        MediaType mediaType = this.mType;
        if (mediaType == null || mediaType.getSupportCarousel()) {
            this.mResourcePicker.getClass();
            boolean z = QpRune.QUICK_TABLET;
            Context context = this.mContext;
            panelWidth = SecQSPanelResourcePicker.getPanelWidth(context) - ((z ? context.getResources().getDimensionPixelSize(R.dimen.sec_media_player_side_padding_tablet) : SecQSPanelResourcePicker.getNotificationSidePadding(context)) * 2);
        } else {
            panelWidth = 748;
        }
        this.mWidth = panelWidth;
    }
}
