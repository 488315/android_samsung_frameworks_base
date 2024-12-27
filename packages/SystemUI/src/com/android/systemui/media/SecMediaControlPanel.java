package com.android.systemui.media;

import android.app.Notification;
import android.app.PendingIntent;
import android.bluetooth.BluetoothA2dp;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.Icon;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.compose.runtime.OpaqueKey$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.systemui.FontSizeUtils;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.log.MediaLogger;
import com.android.systemui.log.MediaLoggerImpl;
import com.android.systemui.media.SecMediaHost;
import com.android.systemui.media.SecSeekBarViewModel;
import com.android.systemui.media.controls.shared.model.MediaAction;
import com.android.systemui.media.controls.shared.model.MediaData;
import com.android.systemui.media.controls.shared.model.MediaDeviceData;
import com.android.systemui.media.controls.ui.SecColorSchemeTransition;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.bar.BarController;
import com.android.systemui.statusbar.NotificationMediaManager;
import com.android.systemui.subscreen.SubScreenManager;
import com.android.systemui.util.ConfigurationState;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.samsung.android.knox.zt.config.securelog.SignalSeverity;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

public final class SecMediaControlPanel {
    public static final int[] ACTION_IDS = {R.id.sec_action0, R.id.sec_action1, R.id.sec_action2, R.id.sec_action3, R.id.sec_action4};
    public int mActionButtonNumExpand;
    public final ActivityStarter mActivityStarter;
    public String mAppName;
    public int mArtworkBoundId;
    public int mArtworkNextBindRequestId;
    public int mBackgroundColor;
    public final Executor mBackgroundExecutor;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public ImageButton mBudsButtonExpanded;
    public Runnable mBudsDetailCloseRunnable;
    public Runnable mBudsDetailOpenRunnable;
    public boolean mBudsEnabled;
    public SecColorSchemeTransition mColorSchemeTransition;
    public final Context mContext;
    public MediaController mController;
    public CoverMusicCapsuleController mCoverMusicCapsuleController;
    public CharSequence mDeviceName;
    public boolean mDualPlayModeEnabled;
    public final AnonymousClass1 mDualPlayModeReceiver;
    public int mGutsBackgroundColor;
    public boolean mIsArtworkBound;
    public boolean mIsPlayerCoverPlayed;
    public final ConfigurationState mLastConfigurationState;
    public final MediaLogger mLogger;
    public final DelayableExecutor mMainExecutor;
    public final MediaOutputHelper mMediaOutputHelper;
    public final AnonymousClass2 mObserver;
    public String mPlayerKey;
    public Drawable mPrevArtwork;
    public Bitmap mPrevBitmap;
    public SecMediaHost.AnonymousClass1 mQSMediaPlayerBarCallback;
    public final SecQSPanelResourcePicker mResourcePicker;
    public SecSeekBarObserver mSeekBarObserver;
    public final SecSeekBarViewModel mSeekBarViewModel;
    private final SettingsHelper mSettingsHelper;
    private SettingsHelper.OnChangedCallback mSettingsListener;
    public final SubScreenManager mSubScreenManager;
    public MediaSession.Token mToken;
    public MediaType mType;
    public SecPlayerViewHolder mViewHolder;
    public final WakefulnessLifecycle mWakefulnessLifecycle;
    public int mWidth;

    public SecMediaControlPanel(Context context, Executor executor, DelayableExecutor delayableExecutor, ActivityStarter activityStarter, SecSeekBarViewModel secSeekBarViewModel, MediaOutputHelper mediaOutputHelper, BroadcastDispatcher broadcastDispatcher, SubScreenManager subScreenManager, MediaLogger mediaLogger, SecQSPanelResourcePicker secQSPanelResourcePicker, SettingsHelper settingsHelper, WakefulnessLifecycle wakefulnessLifecycle) {
        new Rect();
        this.mLastConfigurationState = new ConfigurationState(Arrays.asList(ConfigurationState.ConfigurationField.ORIENTATION, ConfigurationState.ConfigurationField.SCREEN_HEIGHT_DP));
        this.mPrevBitmap = null;
        this.mPrevArtwork = null;
        this.mIsArtworkBound = false;
        this.mArtworkBoundId = 0;
        this.mArtworkNextBindRequestId = 0;
        this.mActionButtonNumExpand = 0;
        this.mDualPlayModeReceiver = new BroadcastReceiver() { // from class: com.android.systemui.media.SecMediaControlPanel.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                boolean booleanExtra = intent.getBooleanExtra("enable", false);
                ExifInterface$$ExternalSyntheticOutline0.m(RowView$$ExternalSyntheticOutline0.m("DualPlayModeReceiver.onReceive() = ", ",  mViewHolder.getAppName() = ", booleanExtra), SecMediaControlPanel.this.mAppName, "MediaControlPanel");
                SecMediaControlPanel secMediaControlPanel = SecMediaControlPanel.this;
                secMediaControlPanel.mDualPlayModeEnabled = booleanExtra;
                secMediaControlPanel.updateDeviceName();
            }
        };
        this.mWidth = 0;
        this.mIsPlayerCoverPlayed = false;
        this.mBudsEnabled = false;
        this.mSettingsListener = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.media.SecMediaControlPanel$$ExternalSyntheticLambda0
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                SecMediaControlPanel secMediaControlPanel = SecMediaControlPanel.this;
                if (uri == null) {
                    secMediaControlPanel.getClass();
                } else if (secMediaControlPanel.mType.getSupportBudsButton() && uri.equals(Settings.System.getUriFor(SettingsHelper.INDEX_BUDS_ENABLE))) {
                    secMediaControlPanel.updateBudsButton();
                }
            }
        };
        this.mObserver = new WakefulnessLifecycle.Observer() { // from class: com.android.systemui.media.SecMediaControlPanel.2
            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onFinishedGoingToSleep() {
                SecSeekBarViewModel secSeekBarViewModel2 = SecMediaControlPanel.this.mSeekBarViewModel;
                secSeekBarViewModel2.getClass();
                secSeekBarViewModel2.bgExecutor.execute(new SecSeekBarViewModel$listening$1(secSeekBarViewModel2, false));
            }

            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onFinishedWakingUp() {
                SecSeekBarViewModel secSeekBarViewModel2 = SecMediaControlPanel.this.mSeekBarViewModel;
                secSeekBarViewModel2.getClass();
                secSeekBarViewModel2.bgExecutor.execute(new SecSeekBarViewModel$listening$1(secSeekBarViewModel2, true));
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
        this.mResourcePicker = secQSPanelResourcePicker;
        this.mSettingsHelper = settingsHelper;
        this.mWakefulnessLifecycle = wakefulnessLifecycle;
        settingsHelper.registerCallback(this.mSettingsListener, Settings.System.getUriFor(SettingsHelper.INDEX_BUDS_ENABLE));
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

    public final void bind(final MediaData mediaData, String str) {
        boolean z;
        ImageButton imageButton;
        Drawable.ConstantState constantState;
        boolean z2;
        SecMediaHost.AnonymousClass1 anonymousClass1;
        if (this.mViewHolder == null) {
            return;
        }
        Trace.beginSection("MediaControlPanel#bindPlayer<" + str + ">");
        updateWidth();
        MediaSession.Token token = mediaData.token;
        MediaSession.Token token2 = this.mToken;
        if (token2 == null || !token2.equals(token)) {
            this.mToken = token;
        }
        if (this.mToken != null) {
            this.mController = new MediaController(this.mContext, this.mToken);
        } else {
            this.mController = null;
        }
        ImageView imageView = this.mViewHolder.appIcon;
        if (imageView == null) {
            imageView = null;
        }
        Icon icon = mediaData.appIcon;
        int i = mediaData.userId;
        if (icon != null) {
            imageView.setImageDrawable(icon.loadDrawableAsUser(this.mContext, i));
        } else {
            imageView.setImageResource(R.drawable.ic_musicnote_small);
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
        boolean z3 = (textView.getText().equals(mediaData.song) && textView2.getText().equals(mediaData.artist)) ? false : true;
        textView.setText(Notification.safeCharSequence(mediaData.song));
        textView2.setText(Notification.safeCharSequence(mediaData.artist));
        String str2 = mediaData.app;
        this.mAppName = str2;
        MediaDeviceData mediaDeviceData = mediaData.device;
        if (mediaDeviceData != null) {
            this.mDeviceName = mediaDeviceData.name;
            if (mediaDeviceData.customMediaDeviceData.deviceType != null && (anonymousClass1 = this.mQSMediaPlayerBarCallback) != null) {
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
            this.mDeviceName = this.mContext.getString(R.string.phone_speaker);
        }
        TextView textView3 = this.mViewHolder.mediaOutputText;
        if (textView3 == null) {
            textView3 = null;
        }
        final int i2 = 0;
        textView3.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.systemui.media.SecMediaControlPanel$$ExternalSyntheticLambda5
            public final /* synthetic */ SecMediaControlPanel f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                BarController.AnonymousClass3 anonymousClass3;
                switch (i2) {
                    case 0:
                        SecMediaControlPanel secMediaControlPanel = this.f$0;
                        MediaData mediaData2 = mediaData;
                        secMediaControlPanel.getClass();
                        SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_QPNE_OUTPUT_SWITCHER, SystemUIAnalytics.QPNE_KEY_APP, mediaData2.packageName);
                        Log.d("MediaControlPanel", "MEDIA_OUTPUT_OPEN");
                        secMediaControlPanel.mMediaOutputHelper.showDetail();
                        break;
                    default:
                        SecMediaControlPanel secMediaControlPanel2 = this.f$0;
                        MediaData mediaData3 = mediaData;
                        secMediaControlPanel2.getClass();
                        SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_QPNE_REMOVE_MEDIA, SystemUIAnalytics.QPNE_KEY_APP, mediaData3.packageName);
                        ((MediaLoggerImpl) secMediaControlPanel2.mLogger).onRemoveClicked(secMediaControlPanel2.mPlayerKey);
                        ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder("Removing player from QSPanel : "), secMediaControlPanel2.mAppName, "MediaControlPanel");
                        SecMediaHost.AnonymousClass1 anonymousClass12 = secMediaControlPanel2.mQSMediaPlayerBarCallback;
                        if (anonymousClass12 != null) {
                            String str3 = secMediaControlPanel2.mPlayerKey;
                            SecMediaHost secMediaHost = SecMediaHost.this;
                            secMediaHost.mMediaDataManager.dismissMediaData(str3, 100L, true);
                            if (anonymousClass12.val$playerData.getSortedMediaPlayers().size() <= 0) {
                                secMediaHost.onMediaVisibilityChanged(Boolean.FALSE);
                                BarController.AnonymousClass4 anonymousClass4 = secMediaHost.mMediaBarCallback;
                                if (anonymousClass4 != null && (anonymousClass3 = BarController.this.mBarListener) != null) {
                                    Runnable runnable = BarController.this.mQSLastExpansionInitializer;
                                    if (runnable != null) {
                                        runnable.run();
                                    }
                                    anonymousClass3.val$animatorRunner.run();
                                    break;
                                }
                            }
                        }
                        break;
                }
            }
        });
        TextView textView4 = this.mViewHolder.mediaOutputText;
        if (textView4 == null) {
            textView4 = null;
        }
        ((View) textView4.getParent()).setOnTouchListener(new SecMediaControlPanel$$ExternalSyntheticLambda4(this, 1));
        updateDeviceName();
        List list = mediaData.actions;
        List list2 = mediaData.actionsToShowInCompact;
        Log.d("MediaControlPanel", "bindActionButtons semanticActions=" + mediaData.semanticActions);
        Log.d("MediaControlPanel", "bindActionButtons actionsWhenCollapsed=" + list2 + ", actionIcons=" + list);
        this.mActionButtonNumExpand = list.size();
        boolean supportExpandable = this.mType.getSupportExpandable();
        int[] iArr = ACTION_IDS;
        if (supportExpandable) {
            Resources resources = this.mContext.getResources();
            int i3 = this.mActionButtonNumExpand;
            int dimensionPixelSize = i3 != 2 ? i3 != 3 ? i3 != 4 ? i3 != 5 ? 0 : resources.getDimensionPixelSize(R.dimen.qs_media_action_button_side_padding_5) : resources.getDimensionPixelSize(R.dimen.qs_media_action_button_side_padding_4) : resources.getDimensionPixelSize(R.dimen.qs_media_action_button_side_padding_3) : resources.getDimensionPixelSize(R.dimen.qs_media_action_button_side_padding_2);
            int dimensionPixelSize2 = this.mContext.getResources().getDimensionPixelSize(QpRune.QUICK_TABLET ? R.dimen.sec_qs_media_player_action_button_size_collapsed_tablet : R.dimen.sec_qs_media_player_action_button_size_expand);
            int i4 = 0;
            for (int i5 = 5; i4 < this.mActionButtonNumExpand && i4 < i5; i5 = 5) {
                ImageButton imageButton2 = (ImageButton) ((SparseArray) this.mViewHolder.expandedActionButtons$delegate.getValue()).get(iArr[i4]);
                if (imageButton2 == null) {
                    throw new IllegalArgumentException();
                }
                LinearLayout linearLayout = (LinearLayout) imageButton2.getParent();
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) imageButton2.getLayoutParams();
                layoutParams.width = dimensionPixelSize2;
                LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
                layoutParams2.width = dimensionPixelSize2 + dimensionPixelSize;
                imageButton2.setLayoutParams(layoutParams);
                linearLayout.setLayoutParams(layoutParams2);
                i4++;
                z3 = z3;
            }
            z = z3;
            if (this.mType.getSupportBudsButton() && (imageButton = this.mBudsButtonExpanded) != null) {
                LinearLayout linearLayout2 = (LinearLayout) imageButton.getParent();
                LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) this.mBudsButtonExpanded.getLayoutParams();
                layoutParams3.width = dimensionPixelSize2;
                LinearLayout.LayoutParams layoutParams4 = (LinearLayout.LayoutParams) linearLayout2.getLayoutParams();
                layoutParams4.width = dimensionPixelSize2 + dimensionPixelSize;
                this.mBudsButtonExpanded.setLayoutParams(layoutParams3);
                linearLayout2.setLayoutParams(layoutParams4);
            }
        } else {
            z = z3;
        }
        final int min = Math.min(list.size(), 5);
        int i6 = 0;
        for (int i7 = 5; i6 < i7; i7 = 5) {
            int i8 = iArr[i6];
            boolean z4 = i6 < list.size();
            MediaAction mediaAction = z4 ? (MediaAction) list.get(i6) : null;
            final ImageButton imageButton3 = (ImageButton) ((SparseArray) this.mViewHolder.expandedActionButtons$delegate.getValue()).get(i8);
            if (imageButton3 == null) {
                throw new IllegalArgumentException();
            }
            if (z4) {
                final String str3 = mediaData.packageName;
                final Consumer consumer = new Consumer() { // from class: com.android.systemui.media.SecMediaControlPanel$$ExternalSyntheticLambda7
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        SecMediaControlPanel secMediaControlPanel = SecMediaControlPanel.this;
                        int i9 = min;
                        String str4 = str3;
                        ImageButton imageButton4 = imageButton3;
                        secMediaControlPanel.getClass();
                        SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_QPNE_BUTTONS_MEDIA, "type", Integer.toString(i9), SystemUIAnalytics.QPNE_KEY_APP, str4);
                        String str5 = secMediaControlPanel.mPlayerKey;
                        int id = imageButton4.getId();
                        ((MediaLoggerImpl) secMediaControlPanel.mLogger).onActionClicked(imageButton4.getContentDescription(), str5, id);
                    }
                };
                if (mediaAction != null) {
                    Drawable drawable = mediaAction.icon;
                    if (drawable != null && (constantState = drawable.getConstantState()) != null) {
                        Drawable newDrawable = constantState.newDrawable();
                        if (newDrawable instanceof Animatable) {
                            ((Animatable) newDrawable).start();
                        }
                        imageButton3.setImageDrawable(newDrawable);
                    }
                    imageButton3.setContentDescription(mediaAction.contentDescription);
                    final Runnable runnable = mediaAction.action;
                    if (runnable == null) {
                        imageButton3.setEnabled(false);
                    } else {
                        imageButton3.setEnabled(true);
                        final int i9 = 2;
                        imageButton3.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.media.SecMediaControlPanel$$ExternalSyntheticLambda10
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                switch (i9) {
                                    case 0:
                                        SecMediaControlPanel secMediaControlPanel = (SecMediaControlPanel) consumer;
                                        View view2 = (View) imageButton3;
                                        View view3 = (View) runnable;
                                        secMediaControlPanel.getClass();
                                        view2.setVisibility(8);
                                        view3.setVisibility(0);
                                        secMediaControlPanel.setBackgroundColor(secMediaControlPanel.mBackgroundColor);
                                        break;
                                    case 1:
                                        SecMediaControlPanel secMediaControlPanel2 = (SecMediaControlPanel) consumer;
                                        PendingIntent pendingIntent = (PendingIntent) imageButton3;
                                        MediaData mediaData2 = (MediaData) runnable;
                                        secMediaControlPanel2.getClass();
                                        if (pendingIntent != null) {
                                            SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_QPNE_OPEN_APP_MEDIA, SystemUIAnalytics.QPNE_KEY_APP, mediaData2.packageName);
                                            secMediaControlPanel2.mActivityStarter.postStartActivityDismissingKeyguard(pendingIntent, true);
                                            break;
                                        } else {
                                            Log.d("MediaControlPanel", "click intent is null");
                                            break;
                                        }
                                    default:
                                        Consumer consumer2 = (Consumer) consumer;
                                        ImageButton imageButton4 = (ImageButton) imageButton3;
                                        Runnable runnable2 = (Runnable) runnable;
                                        consumer2.accept(imageButton4);
                                        runnable2.run();
                                        break;
                                }
                            }
                        });
                        if (this.mType.getSupportExpandable()) {
                            ((View) imageButton3.getParent()).setOnTouchListener(new SecMediaControlPanel$$ExternalSyntheticLambda4(imageButton3, 2));
                        }
                    }
                }
            }
            if (this.mType.getSupportExpandable()) {
                ((View) imageButton3.getParent()).setVisibility(z4 ? 0 : 8);
            }
            imageButton3.setVisibility(z4 ? 0 : 8);
            i6++;
        }
        final MediaController mediaController = this.mController;
        this.mBackgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.media.SecMediaControlPanel$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                SecMediaControlPanel secMediaControlPanel = SecMediaControlPanel.this;
                MediaController mediaController2 = mediaController;
                SecSeekBarViewModel secSeekBarViewModel = secMediaControlPanel.mSeekBarViewModel;
                secSeekBarViewModel.setController(mediaController2);
                MediaController mediaController3 = secSeekBarViewModel.controller;
                secSeekBarViewModel.playbackState = mediaController3 != null ? mediaController3.getPlaybackState() : null;
                MediaController mediaController4 = secSeekBarViewModel.controller;
                MediaMetadata metadata = mediaController4 != null ? mediaController4.getMetadata() : null;
                PlaybackState playbackState = secSeekBarViewModel.playbackState;
                boolean z5 = ((playbackState != null ? playbackState.getActions() : 0L) & 256) != 0;
                PlaybackState playbackState2 = secSeekBarViewModel.playbackState;
                Integer valueOf = playbackState2 != null ? Integer.valueOf((int) playbackState2.getPosition()) : null;
                int i10 = metadata != null ? (int) metadata.getLong("android.media.metadata.DURATION") : 0;
                PlaybackState playbackState3 = secSeekBarViewModel.playbackState;
                boolean isPlayingState = NotificationMediaManager.isPlayingState(playbackState3 != null ? playbackState3.getState() : 0);
                PlaybackState playbackState4 = secSeekBarViewModel.playbackState;
                secSeekBarViewModel.set_data(new SecSeekBarViewModel.Progress((playbackState4 == null || playbackState4.getState() == 0 || i10 <= 0) ? false : true, z5, isPlayingState, secSeekBarViewModel.scrubbing, valueOf, i10, secSeekBarViewModel.listening));
                secSeekBarViewModel.checkIfPollingNeeded();
            }
        });
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
                    final int i10 = 1;
                    textView5.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.systemui.media.SecMediaControlPanel$$ExternalSyntheticLambda5
                        public final /* synthetic */ SecMediaControlPanel f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view2) {
                            BarController.AnonymousClass3 anonymousClass3;
                            switch (i10) {
                                case 0:
                                    SecMediaControlPanel secMediaControlPanel = this.f$0;
                                    MediaData mediaData2 = mediaData;
                                    secMediaControlPanel.getClass();
                                    SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_QPNE_OUTPUT_SWITCHER, SystemUIAnalytics.QPNE_KEY_APP, mediaData2.packageName);
                                    Log.d("MediaControlPanel", "MEDIA_OUTPUT_OPEN");
                                    secMediaControlPanel.mMediaOutputHelper.showDetail();
                                    break;
                                default:
                                    SecMediaControlPanel secMediaControlPanel2 = this.f$0;
                                    MediaData mediaData3 = mediaData;
                                    secMediaControlPanel2.getClass();
                                    SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_QPNE_REMOVE_MEDIA, SystemUIAnalytics.QPNE_KEY_APP, mediaData3.packageName);
                                    ((MediaLoggerImpl) secMediaControlPanel2.mLogger).onRemoveClicked(secMediaControlPanel2.mPlayerKey);
                                    ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder("Removing player from QSPanel : "), secMediaControlPanel2.mAppName, "MediaControlPanel");
                                    SecMediaHost.AnonymousClass1 anonymousClass12 = secMediaControlPanel2.mQSMediaPlayerBarCallback;
                                    if (anonymousClass12 != null) {
                                        String str32 = secMediaControlPanel2.mPlayerKey;
                                        SecMediaHost secMediaHost = SecMediaHost.this;
                                        secMediaHost.mMediaDataManager.dismissMediaData(str32, 100L, true);
                                        if (anonymousClass12.val$playerData.getSortedMediaPlayers().size() <= 0) {
                                            secMediaHost.onMediaVisibilityChanged(Boolean.FALSE);
                                            BarController.AnonymousClass4 anonymousClass4 = secMediaHost.mMediaBarCallback;
                                            if (anonymousClass4 != null && (anonymousClass3 = BarController.this.mBarListener) != null) {
                                                Runnable runnable2 = BarController.this.mQSLastExpansionInitializer;
                                                if (runnable2 != null) {
                                                    runnable2.run();
                                                }
                                                anonymousClass3.val$animatorRunner.run();
                                                break;
                                            }
                                        }
                                    }
                                    break;
                            }
                        }
                    });
                    updateFontSize(textView5, R.dimen.sec_qs_media_panel_options_remove_text_size);
                }
                TextView textView6 = this.mViewHolder.cancelText;
                if (textView6 != null) {
                    final int i11 = 0;
                    textView6.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.media.SecMediaControlPanel$$ExternalSyntheticLambda10
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view2) {
                            switch (i11) {
                                case 0:
                                    SecMediaControlPanel secMediaControlPanel = (SecMediaControlPanel) this;
                                    View view22 = (View) view;
                                    View view3 = (View) linearLayout3;
                                    secMediaControlPanel.getClass();
                                    view22.setVisibility(8);
                                    view3.setVisibility(0);
                                    secMediaControlPanel.setBackgroundColor(secMediaControlPanel.mBackgroundColor);
                                    break;
                                case 1:
                                    SecMediaControlPanel secMediaControlPanel2 = (SecMediaControlPanel) this;
                                    PendingIntent pendingIntent = (PendingIntent) view;
                                    MediaData mediaData2 = (MediaData) linearLayout3;
                                    secMediaControlPanel2.getClass();
                                    if (pendingIntent != null) {
                                        SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_QPNE_OPEN_APP_MEDIA, SystemUIAnalytics.QPNE_KEY_APP, mediaData2.packageName);
                                        secMediaControlPanel2.mActivityStarter.postStartActivityDismissingKeyguard(pendingIntent, true);
                                        break;
                                    } else {
                                        Log.d("MediaControlPanel", "click intent is null");
                                        break;
                                    }
                                default:
                                    Consumer consumer2 = (Consumer) this;
                                    ImageButton imageButton4 = (ImageButton) view;
                                    Runnable runnable2 = (Runnable) linearLayout3;
                                    consumer2.accept(imageButton4);
                                    runnable2.run();
                                    break;
                            }
                        }
                    });
                    updateFontSize(textView6, R.dimen.sec_qs_media_panel_options_cancel_text_size);
                }
                ImageView imageView2 = this.mViewHolder.optionsAppIcon;
                if (imageView2 != null) {
                    Icon icon2 = mediaData.appIcon;
                    if (icon2 != null) {
                        imageView2.setImageDrawable(icon2.loadDrawableAsUser(this.mContext, i));
                    } else {
                        imageView2.setImageResource(R.drawable.ic_music_note);
                    }
                }
                TextView textView7 = this.mViewHolder.optionsAppTitle;
                if (textView7 != null) {
                    textView7.setText(str2);
                    updateFontSize(textView7, R.dimen.sec_qs_media_panel_options_app_name_text_size);
                }
                view.setVisibility(8);
                linearLayout3.setVisibility(0);
                View view2 = this.mViewHolder.playerView;
                if (view2 == null) {
                    view2 = null;
                }
                view2.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.android.systemui.media.SecMediaControlPanel$$ExternalSyntheticLambda11
                    @Override // android.view.View.OnLongClickListener
                    public final boolean onLongClick(View view3) {
                        SecMediaControlPanel secMediaControlPanel = SecMediaControlPanel.this;
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
            this.mGutsBackgroundColor = this.mContext.getColor(R.color.sec_qs_media_player_guts_background_color);
            int color = this.mContext.getColor(R.color.sec_qs_media_player_background_color);
            this.mBackgroundColor = color;
            setBackgroundColor(color);
            final PendingIntent pendingIntent = mediaData.clickIntent;
            View view3 = this.mViewHolder.playerView;
            if (view3 == null) {
                view3 = null;
            }
            final int i12 = 1;
            view3.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.media.SecMediaControlPanel$$ExternalSyntheticLambda10
                @Override // android.view.View.OnClickListener
                public final void onClick(View view22) {
                    switch (i12) {
                        case 0:
                            SecMediaControlPanel secMediaControlPanel = (SecMediaControlPanel) this;
                            View view222 = (View) pendingIntent;
                            View view32 = (View) mediaData;
                            secMediaControlPanel.getClass();
                            view222.setVisibility(8);
                            view32.setVisibility(0);
                            secMediaControlPanel.setBackgroundColor(secMediaControlPanel.mBackgroundColor);
                            break;
                        case 1:
                            SecMediaControlPanel secMediaControlPanel2 = (SecMediaControlPanel) this;
                            PendingIntent pendingIntent2 = (PendingIntent) pendingIntent;
                            MediaData mediaData2 = (MediaData) mediaData;
                            secMediaControlPanel2.getClass();
                            if (pendingIntent2 != null) {
                                SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_QPNE_OPEN_APP_MEDIA, SystemUIAnalytics.QPNE_KEY_APP, mediaData2.packageName);
                                secMediaControlPanel2.mActivityStarter.postStartActivityDismissingKeyguard(pendingIntent2, true);
                                break;
                            } else {
                                Log.d("MediaControlPanel", "click intent is null");
                                break;
                            }
                        default:
                            Consumer consumer2 = (Consumer) this;
                            ImageButton imageButton4 = (ImageButton) pendingIntent;
                            Runnable runnable2 = (Runnable) mediaData;
                            consumer2.accept(imageButton4);
                            runnable2.run();
                            break;
                    }
                }
            });
        }
        SecPlayerViewHolder secPlayerViewHolder3 = this.mViewHolder;
        if (secPlayerViewHolder3 != null) {
            SeekBar seekBar = secPlayerViewHolder3.seekBar;
            if (seekBar == null) {
                seekBar = null;
            }
            seekBar.setVisibility(0);
            int dimensionPixelSize3 = this.mContext.getResources().getDimensionPixelSize(R.dimen.sec_media_view_seek_bar_padding);
            SeekBar seekBar2 = this.mViewHolder.seekBar;
            if (seekBar2 == null) {
                seekBar2 = null;
            }
            seekBar2.setPadding(dimensionPixelSize3, 0, dimensionPixelSize3, 0);
        }
        updateResources();
        if (this.mType.getSupportArtwork()) {
            final int hashCode = mediaData.hashCode();
            final String str4 = "MediaControlPanel#bindArtworkAndColors<" + str + ">";
            Trace.beginAsyncSection(str4, hashCode);
            final int i13 = this.mArtworkNextBindRequestId;
            this.mArtworkNextBindRequestId = i13 + 1;
            if (z) {
                this.mIsArtworkBound = false;
            }
            final int i14 = this.mWidth;
            ImageView imageView3 = this.mViewHolder.albumView;
            if (imageView3 == null) {
                imageView3 = null;
            }
            ((GradientDrawable) imageView3.getForeground()).setSize(i14, i14);
            final boolean z5 = z;
            this.mBackgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.media.SecMediaControlPanel$$ExternalSyntheticLambda13
                @Override // java.lang.Runnable
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final void run() {
                    /*
                        Method dump skipped, instructions count: 246
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.SecMediaControlPanel$$ExternalSyntheticLambda13.run():void");
                }
            });
        }
        Trace.endSection();
    }

    public final boolean isPlaying$1() {
        PlaybackState playbackState;
        MediaController mediaController = this.mController;
        return (mediaController == null || (playbackState = mediaController.getPlaybackState()) == null || playbackState.getState() != 3) ? false : true;
    }

    public final void onDestroy() {
        SecSeekBarObserver secSeekBarObserver = this.mSeekBarObserver;
        final SecSeekBarViewModel secSeekBarViewModel = this.mSeekBarViewModel;
        if (secSeekBarObserver != null) {
            secSeekBarViewModel._progress.removeObserver(secSeekBarObserver);
        }
        secSeekBarViewModel.getClass();
        secSeekBarViewModel.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.media.SecSeekBarViewModel$onDestroy$1
            @Override // java.lang.Runnable
            public final void run() {
                SecSeekBarViewModel.this.setController(null);
                SecSeekBarViewModel secSeekBarViewModel2 = SecSeekBarViewModel.this;
                secSeekBarViewModel2.playbackState = null;
                Runnable runnable = secSeekBarViewModel2.cancel;
                if (runnable != null) {
                    runnable.run();
                }
                SecSeekBarViewModel.this.cancel = null;
            }
        });
        try {
            this.mBroadcastDispatcher.unregisterReceiver(this.mDualPlayModeReceiver);
        } catch (Exception unused) {
        }
        if (this.mType.getSupportCapsule()) {
            CoverMusicCapsuleController coverMusicCapsuleController = this.mCoverMusicCapsuleController;
            coverMusicCapsuleController.getClass();
            Log.d("CoverMusicCapsuleController", "capsule destroyed");
            Bundle bundle = coverMusicCapsuleController.bundle;
            bundle.putBoolean("visible", false);
            bundle.putParcelable("capsule_layout", coverMusicCapsuleController.capsule);
            bundle.putString("capsule_priority", SignalSeverity.LOW);
            coverMusicCapsuleController.updateCapsule();
        }
        if (this.mType.getSupportWidgetTimer()) {
            this.mWakefulnessLifecycle.removeObserver(this.mObserver);
        }
        this.mSettingsHelper.unregisterCallback(this.mSettingsListener);
    }

    public final void setBackgroundColor(int i) {
        View view = this.mViewHolder.playerView;
        if (view == null) {
            view = null;
        }
        view.setBackgroundColor(i);
    }

    public final void setListening(boolean z) {
        if (this.mType.getSupportExpandable()) {
            SecSeekBarViewModel secSeekBarViewModel = this.mSeekBarViewModel;
            secSeekBarViewModel.getClass();
            secSeekBarViewModel.bgExecutor.execute(new SecSeekBarViewModel$listening$1(secSeekBarViewModel, z));
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
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("SecMediaControlPanel{mPlayerKey='");
        sb.append(this.mPlayerKey);
        sb.append("', this=");
        return OpaqueKey$$ExternalSyntheticOutline0.m(sb, super.toString(), '}');
    }

    public final void updateBudsButton() {
        if (!this.mType.getSupportBudsButton() || this.mBudsButtonExpanded == null) {
            return;
        }
        this.mBudsEnabled = this.mSettingsHelper.getBudsEnable();
        ActionBarContextView$$ExternalSyntheticOutline0.m(new StringBuilder("onChanged(): buds enabled: "), this.mBudsEnabled, "MediaControlPanel");
        if (!this.mBudsEnabled) {
            this.mBudsDetailCloseRunnable.run();
        }
        ((View) this.mBudsButtonExpanded.getParent()).setVisibility(this.mBudsEnabled ? 0 : 8);
        this.mBudsButtonExpanded.setVisibility(this.mBudsEnabled ? 0 : 8);
        this.mBudsButtonExpanded.setImageResource(R.drawable.ic_buds3f);
        this.mBudsButtonExpanded.setEnabled(this.mBudsEnabled);
    }

    public final void updateDeviceName() {
        ImageButton imageButton;
        TextView textView = this.mViewHolder.seamlessText;
        if (textView == null) {
            textView = null;
        }
        StringBuilder sb = new StringBuilder("updateDeviceName() : deviceName = ");
        sb.append((Object) this.mDeviceName);
        sb.append(", mDualPlayModeEnabled = ");
        ActionBarContextView$$ExternalSyntheticOutline0.m(sb, this.mDualPlayModeEnabled, "MediaControlPanel");
        textView.setText(this.mDualPlayModeEnabled ? this.mContext.getString(R.string.sec_qs_media_player_dual_play_mode_information) : TextUtils.isEmpty(this.mDeviceName) ? this.mContext.getString(R.string.phone_speaker) : this.mDeviceName);
        updateFontSize(textView, R.dimen.sec_qs_media_player_device_name_text_size);
        textView.setVisibility(0);
        if (!this.mType.getSupportBudsButton() || (imageButton = this.mBudsButtonExpanded) == null) {
            return;
        }
        imageButton.setContentDescription(this.mContext.getString(R.string.sec_qs_buds_button_content_description, this.mDeviceName));
    }

    public final void updateFontSize(TextView textView, int i) {
        if (this.mType.getSupportFixedFontSize()) {
            return;
        }
        FontSizeUtils.updateFontSize(textView, i, 0.8f, 1.3f);
    }

    public final void updateResources() {
        if (this.mType.getSupportGuts()) {
            int color = this.mContext.getColor(R.color.media_primary_text);
            int color2 = this.mContext.getColor(R.color.media_expand_primary_text);
            int color3 = this.mContext.getColor(R.color.media_expand_secondary_text);
            int color4 = this.mContext.getColor(R.color.media_expand_tertiary_text);
            SecPlayerViewHolder secPlayerViewHolder = this.mViewHolder;
            secPlayerViewHolder.getClass();
            ColorStateList valueOf = ColorStateList.valueOf(color);
            SeekBar seekBar = secPlayerViewHolder.seekBar;
            if (seekBar == null) {
                seekBar = null;
            }
            seekBar.setThumbTintList(valueOf);
            seekBar.setProgressTintList(valueOf);
            seekBar.setProgressBackgroundTintList(valueOf.withAlpha(76));
            SecPlayerViewHolder secPlayerViewHolder2 = this.mViewHolder;
            TextView textView = secPlayerViewHolder2.titleText;
            if (textView == null) {
                textView = null;
            }
            textView.setTextColor(color2);
            TextView textView2 = secPlayerViewHolder2.artistText;
            if (textView2 == null) {
                textView2 = null;
            }
            textView2.setTextColor(color3);
            ImageView imageView = secPlayerViewHolder2.appIcon;
            if (imageView == null) {
                imageView = null;
            }
            imageView.setColorFilter(color4);
            TextView textView3 = secPlayerViewHolder2.seamlessText;
            (textView3 != null ? textView3 : null).setTextColor(color4);
            ImageView imageView2 = secPlayerViewHolder2.optionsAppIcon;
            if (imageView2 != null) {
                imageView2.setColorFilter(color4);
            }
            TextView textView4 = secPlayerViewHolder2.optionsAppTitle;
            if (textView4 != null) {
                textView4.setTextColor(color4);
            }
            this.mGutsBackgroundColor = this.mContext.getColor(R.color.sec_qs_media_player_guts_background_color);
            TextView textView5 = this.mViewHolder.optionsAppTitle;
            if (textView5 != null) {
                textView5.setTextColor(color4);
            }
            TextView textView6 = this.mViewHolder.removeText;
            if (textView6 != null) {
                textView6.setTextColor(color2);
                textView6.setBackground(this.mContext.getDrawable(R.drawable.sec_qs_media_player_guts_button_background));
            }
            TextView textView7 = this.mViewHolder.cancelText;
            if (textView7 != null) {
                textView7.setTextColor(color2);
                textView7.setBackground(this.mContext.getDrawable(R.drawable.notification_guts_button_bg));
            }
            int color5 = this.mContext.getColor(R.color.sec_qs_media_player_background_color);
            this.mBackgroundColor = color5;
            setBackgroundColor(color5);
            TextView textView8 = this.mViewHolder.cancelText;
            if (textView8 != null) {
                textView8.semSetButtonShapeEnabled(true);
            }
        }
    }

    public final void updateWidth() {
        int albumArtWidth;
        MediaType mediaType = this.mType;
        if (mediaType == null || mediaType.getSupportCarousel()) {
            albumArtWidth = this.mResourcePicker.resourcePickHelper.getTargetPicker().getAlbumArtWidth(this.mContext);
        } else {
            albumArtWidth = 748;
        }
        this.mWidth = albumArtWidth;
    }
}
