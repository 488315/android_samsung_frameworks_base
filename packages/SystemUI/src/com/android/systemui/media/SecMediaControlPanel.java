package com.android.systemui.media;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.compose.runtime.OpaqueKey$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.systemui.FontSizeUtils;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.log.MediaLogger;
import com.android.systemui.media.SecMediaHost;
import com.android.systemui.media.SecSeekBarViewModel;
import com.android.systemui.media.controls.ui.SecColorSchemeTransition;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.bar.QSMediaPlayerBar$$ExternalSyntheticLambda3;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityController;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityDataHelper;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityLayoutUtil;
import com.android.systemui.subscreen.SubScreenManager;
import com.android.systemui.subscreen.SubScreenQuickPanelWindowController$$ExternalSyntheticLambda15;
import com.android.systemui.util.ConfigurationState;
import com.android.systemui.util.RecoilEffectUtil;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.samsung.android.knox.zt.config.securelog.SignalSeverity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.function.BooleanSupplier;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class SecMediaControlPanel {
    public static final int[] ACTION_IDS = {R.id.sec_action0, R.id.sec_action1, R.id.sec_action2, R.id.sec_action3, R.id.sec_action4};
    public int mActionButtonNumExpand;
    public final ActivityStarter mActivityStarter;
    public String mAppName;
    public int mArtworkBoundId;
    public int mArtworkNextBindRequestId;
    public final Executor mBackgroundExecutor;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public ImageButton mBudsButtonExpanded;
    public QSMediaPlayerBar$$ExternalSyntheticLambda3 mBudsDetailCloseRunnable;
    public QSMediaPlayerBar$$ExternalSyntheticLambda3 mBudsDetailOpenRunnable;
    public boolean mBudsEnabled;
    public SecColorSchemeTransition mColorSchemeTransition;
    public final Context mContext;
    public MediaController mController;
    public Context mCoverContext;
    public CoverMusicCapsuleController mCoverMusicCapsuleController;
    public SubScreenQuickPanelWindowController$$ExternalSyntheticLambda15 mCoverQSClickConsumer;
    public CharSequence mDeviceName;
    public boolean mDualPlayModeEnabled;
    public boolean mIsArtworkBound;
    public boolean mIsPlayerCoverPlayed;
    public boolean mIsPlayerOAPlayed;
    public boolean mIsWidthUpdated;
    public String mLastBluetoothDeviceAddress;
    public final ConfigurationState mLastConfigurationState;
    public final MediaLogger mLogger;
    public final DelayableExecutor mMainExecutor;
    public final AnonymousClass1 mMediaBluetoothReceiver;
    public final MediaOutputHelper mMediaOutputHelper;
    public OAMusicChipController mOAMusicChipController;
    public final AnonymousClass3 mObserver;
    public final OngoingActivityController mOngoingActivityController;
    public final AnonymousClass2 mOngoingActivityObserver;
    public int mPlaybackLocation;
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.media.SecMediaControlPanel$2, reason: invalid class name */
    public class AnonymousClass2 {
        public AnonymousClass2() {
        }
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.systemui.media.SecMediaControlPanel$1] */
    /* JADX WARN: Type inference failed for: r0v6, types: [com.android.systemui.media.SecMediaControlPanel$3] */
    public SecMediaControlPanel(Context context, Executor executor, DelayableExecutor delayableExecutor, ActivityStarter activityStarter, SecSeekBarViewModel secSeekBarViewModel, MediaOutputHelper mediaOutputHelper, BroadcastDispatcher broadcastDispatcher, SubScreenManager subScreenManager, MediaLogger mediaLogger, SecQSPanelResourcePicker secQSPanelResourcePicker, SettingsHelper settingsHelper, WakefulnessLifecycle wakefulnessLifecycle, OngoingActivityController ongoingActivityController) {
        new Rect();
        this.mLastConfigurationState = new ConfigurationState(Arrays.asList(ConfigurationState.ConfigurationField.ORIENTATION, ConfigurationState.ConfigurationField.SCREEN_HEIGHT_DP));
        this.mPrevBitmap = null;
        this.mPrevArtwork = null;
        this.mIsArtworkBound = false;
        this.mIsWidthUpdated = false;
        this.mArtworkBoundId = 0;
        this.mArtworkNextBindRequestId = 0;
        this.mActionButtonNumExpand = 0;
        this.mPlaybackLocation = 0;
        this.mLastBluetoothDeviceAddress = null;
        this.mMediaBluetoothReceiver = new BroadcastReceiver() { // from class: com.android.systemui.media.SecMediaControlPanel.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                if (!"android.bluetooth.a2dp.profile.action.ACTIVE_DEVICE_CHANGED".equals(intent.getAction())) {
                    if ("com.samsung.bluetooth.a2dp.intent.action.DUAL_PLAY_MODE_ENABLED".equals(intent.getAction())) {
                        boolean booleanExtra = intent.getBooleanExtra("enable", false);
                        ExifInterface$$ExternalSyntheticOutline0.m(RowView$$ExternalSyntheticOutline0.m("MediaBluetoothReceiver dual play onReceive = ", ",  mViewHolder.getAppName() = ", booleanExtra), SecMediaControlPanel.this.mAppName, "MediaControlPanel");
                        SecMediaControlPanel secMediaControlPanel = SecMediaControlPanel.this;
                        secMediaControlPanel.mDualPlayModeEnabled = booleanExtra;
                        secMediaControlPanel.updateDeviceName();
                        return;
                    }
                    return;
                }
                BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE", BluetoothDevice.class);
                if (bluetoothDevice == null) {
                    Log.d("MediaControlPanel", "ACTION_ACTIVE_DEVICE_CHANGED device is null");
                    return;
                }
                String str = SecMediaControlPanel.this.mLastBluetoothDeviceAddress;
                if (str == null || !str.equals(bluetoothDevice.getAddress())) {
                    SecMediaControlPanel.this.mLastBluetoothDeviceAddress = bluetoothDevice.getAddress();
                    ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder("MediaBluetoothReceiver device changed onReceive,  mViewHolder.getAppName() = "), SecMediaControlPanel.this.mAppName, "MediaControlPanel");
                    SecMediaControlPanel.this.updateBudsButton();
                }
            }
        };
        this.mWidth = 0;
        this.mIsPlayerCoverPlayed = false;
        this.mIsPlayerOAPlayed = false;
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
        this.mOngoingActivityObserver = new AnonymousClass2();
        this.mObserver = new WakefulnessLifecycle.Observer() { // from class: com.android.systemui.media.SecMediaControlPanel.3
            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onFinishedGoingToSleep() {
                SecMediaControlPanel secMediaControlPanel = SecMediaControlPanel.this;
                SecSeekBarViewModel secSeekBarViewModel2 = secMediaControlPanel.mSeekBarViewModel;
                secSeekBarViewModel2.getClass();
                secSeekBarViewModel2.bgExecutor.execute(new SecSeekBarViewModel$listening$1(secSeekBarViewModel2, false));
                secMediaControlPanel.setTitleAndArtistMarquee(false);
            }

            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public final void onFinishedWakingUp() {
                SecMediaControlPanel secMediaControlPanel = SecMediaControlPanel.this;
                SecSeekBarViewModel secSeekBarViewModel2 = secMediaControlPanel.mSeekBarViewModel;
                secSeekBarViewModel2.getClass();
                secSeekBarViewModel2.bgExecutor.execute(new SecSeekBarViewModel$listening$1(secSeekBarViewModel2, true));
                secMediaControlPanel.setTitleAndArtistMarquee(true);
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
        this.mOngoingActivityController = ongoingActivityController;
        settingsHelper.registerCallback(this.mSettingsListener, Settings.System.getUriFor(SettingsHelper.INDEX_BUDS_ENABLE));
    }

    public static CharSequence makeTitleWithArtist(CharSequence charSequence, CharSequence charSequence2) {
        String str = "";
        if (charSequence == null) {
            charSequence = "";
        }
        if (charSequence2 == null) {
            charSequence2 = "";
        }
        if (!charSequence.isEmpty() && !charSequence2.isEmpty()) {
            str = " • ";
        }
        return ((Object) charSequence) + str + ((Object) charSequence2);
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
        this.mViewHolder = secPlayerViewHolder;
        SecSeekBarObserver secSeekBarObserver = new SecSeekBarObserver(secPlayerViewHolder);
        this.mSeekBarObserver = secSeekBarObserver;
        SecSeekBarViewModel secSeekBarViewModel = this.mSeekBarViewModel;
        secSeekBarViewModel._progress.observeForever(secSeekBarObserver);
        SeekBar seekBar = secPlayerViewHolder.seekBar;
        if (seekBar == null) {
            seekBar = null;
        }
        seekBar.setOnSeekBarChangeListener(new SecSeekBarViewModel.SeekBarChangeListener(secSeekBarViewModel));
        seekBar.setOnTouchListener(new SecSeekBarViewModel.SeekBarTouchListener(secSeekBarViewModel, seekBar));
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.samsung.bluetooth.a2dp.intent.action.DUAL_PLAY_MODE_ENABLED");
        intentFilter.addAction("android.bluetooth.a2dp.profile.action.ACTIVE_DEVICE_CHANGED");
        this.mBroadcastDispatcher.registerReceiver(intentFilter, this.mMediaBluetoothReceiver);
        updateWidth();
        if (this.mType.getSupportColorSchemeTransition()) {
            this.mColorSchemeTransition = new SecColorSchemeTransition(this.mContext, this.mViewHolder);
        }
        if (this.mType.getSupportCapsule()) {
            final int i = 0;
            CoverMusicCapsuleController coverMusicCapsuleController = new CoverMusicCapsuleController(this.mContext, this.mSubScreenManager, new BooleanSupplier(this) { // from class: com.android.systemui.media.SecMediaControlPanel$$ExternalSyntheticLambda2
                public final /* synthetic */ SecMediaControlPanel f$0;

                {
                    this.f$0 = this;
                }

                @Override // java.util.function.BooleanSupplier
                public final boolean getAsBoolean() {
                    int i2 = i;
                    SecMediaControlPanel secMediaControlPanel = this.f$0;
                    switch (i2) {
                        case 0:
                            return secMediaControlPanel.mIsPlayerCoverPlayed;
                        default:
                            return secMediaControlPanel.mIsPlayerOAPlayed;
                    }
                }
            });
            this.mCoverMusicCapsuleController = coverMusicCapsuleController;
            secSeekBarViewModel.coverMusicCapsuleController = coverMusicCapsuleController;
        }
        if (this.mType.getSupportOAChip()) {
            final int i2 = 1;
            OAMusicChipController oAMusicChipController = new OAMusicChipController(this.mContext, this.mOngoingActivityController, new BooleanSupplier(this) { // from class: com.android.systemui.media.SecMediaControlPanel$$ExternalSyntheticLambda2
                public final /* synthetic */ SecMediaControlPanel f$0;

                {
                    this.f$0 = this;
                }

                @Override // java.util.function.BooleanSupplier
                public final boolean getAsBoolean() {
                    int i22 = i2;
                    SecMediaControlPanel secMediaControlPanel = this.f$0;
                    switch (i22) {
                        case 0:
                            return secMediaControlPanel.mIsPlayerCoverPlayed;
                        default:
                            return secMediaControlPanel.mIsPlayerOAPlayed;
                    }
                }
            });
            this.mOAMusicChipController = oAMusicChipController;
            secSeekBarViewModel.oaMusicChipController = oAMusicChipController;
        }
        if (this.mType.getSupportWidgetTimer()) {
            this.mWakefulnessLifecycle.addObserver(this.mObserver);
        }
        if (this.mType == MediaType.OA) {
            OngoingActivityDataHelper.INSTANCE.getClass();
            ((ArrayList) OngoingActivityDataHelper.mediaOngoingActivityObserver).add(this.mOngoingActivityObserver);
        }
        if (this.mType.getSupportRecoilAnimation()) {
            View view = this.mViewHolder.playerView;
            (view != null ? view : null).setStateListAnimator(RecoilEffectUtil.getRecoilLargeAnimator(this.mContext));
        }
        setTitleAndArtistMarquee(true);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:117:0x0416  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x0427  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x0429  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void bind(final com.android.systemui.media.controls.shared.model.MediaData r19, final java.lang.String r20) {
        /*
            Method dump skipped, instructions count: 1589
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.SecMediaControlPanel.bind(com.android.systemui.media.controls.shared.model.MediaData, java.lang.String):void");
    }

    public final boolean isPlaying() {
        PlaybackState playbackState;
        MediaController mediaController = this.mController;
        return (mediaController == null || (playbackState = mediaController.getPlaybackState()) == null || playbackState.getState() != 3) ? false : true;
    }

    public final void onDestroy() {
        if (this.mType == MediaType.OA) {
            OngoingActivityDataHelper.INSTANCE.getClass();
            ((ArrayList) OngoingActivityDataHelper.mediaOngoingActivityObserver).remove(this.mOngoingActivityObserver);
        }
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
                SecSeekBarViewModel$checkIfPollingNeeded$1 secSeekBarViewModel$checkIfPollingNeeded$1 = secSeekBarViewModel2.cancel;
                if (secSeekBarViewModel$checkIfPollingNeeded$1 != null) {
                    secSeekBarViewModel$checkIfPollingNeeded$1.run();
                }
                SecSeekBarViewModel.this.cancel = null;
            }
        });
        try {
            this.mBroadcastDispatcher.unregisterReceiver(this.mMediaBluetoothReceiver);
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
        if (this.mType.getSupportOAChip()) {
            this.mOAMusicChipController.getClass();
            Log.d("OAMusicChipController", "OA destroyed");
        }
        if (this.mType.getSupportWidgetTimer()) {
            this.mWakefulnessLifecycle.removeObserver(this.mObserver);
        }
        setTitleAndArtistMarquee(false);
        this.mSettingsHelper.unregisterCallback(this.mSettingsListener);
    }

    public final void setBackgroundColor() {
        this.mType.getClass();
    }

    public final void setListening(boolean z) {
        if (this.mType.getSupportExpandable()) {
            MediaType mediaType = this.mType;
            MediaType mediaType2 = MediaType.ENR;
            SecSeekBarViewModel secSeekBarViewModel = this.mSeekBarViewModel;
            if (mediaType == mediaType2 && secSeekBarViewModel.listening == z) {
                return;
            }
            secSeekBarViewModel.getClass();
            secSeekBarViewModel.bgExecutor.execute(new SecSeekBarViewModel$listening$1(secSeekBarViewModel, z));
            if (!this.mType.getSupportCoverQuickPanelMedia()) {
                setTitleAndArtistMarquee(z);
            }
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
                setBackgroundColor();
            }
        }
    }

    public final void setTitleAndArtistMarquee(boolean z) {
        SecPlayerViewHolder secPlayerViewHolder = this.mViewHolder;
        if (secPlayerViewHolder == null) {
            return;
        }
        if (!z) {
            TextView textView = secPlayerViewHolder.titleText;
            if (textView == null) {
                textView = null;
            }
            textView.setSelected(false);
            if (this.mType.getSupportCoverQuickPanelMedia()) {
                return;
            }
            TextView textView2 = this.mViewHolder.artistText;
            (textView2 != null ? textView2 : null).setSelected(false);
            return;
        }
        TextView textView3 = secPlayerViewHolder.titleText;
        if (textView3 == null) {
            textView3 = null;
        }
        textView3.setSelected(true);
        TextView textView4 = this.mViewHolder.titleText;
        if (textView4 == null) {
            textView4 = null;
        }
        int paintFlags = textView4.getPaintFlags();
        TextView textView5 = this.mViewHolder.titleText;
        if (textView5 == null) {
            textView5 = null;
        }
        textView5.setPaintFlags(paintFlags | 192);
        if (this.mType.getSupportCoverQuickPanelMedia()) {
            return;
        }
        TextView textView6 = this.mViewHolder.artistText;
        if (textView6 == null) {
            textView6 = null;
        }
        textView6.setSelected(true);
        TextView textView7 = this.mViewHolder.artistText;
        if (textView7 == null) {
            textView7 = null;
        }
        int paintFlags2 = textView7.getPaintFlags();
        TextView textView8 = this.mViewHolder.artistText;
        (textView8 != null ? textView8 : null).setPaintFlags(paintFlags2 | 192);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("SecMediaControlPanel{mPlayerKey='");
        sb.append(this.mPlayerKey);
        sb.append("', this=");
        return OpaqueKey$$ExternalSyntheticOutline0.m(sb, super.toString(), '}');
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x00a2  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00fc  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateBudsButton() {
        /*
            Method dump skipped, instructions count: 346
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.SecMediaControlPanel.updateBudsButton():void");
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
        sb.append(this.mDualPlayModeEnabled);
        sb.append(", mPlaybackLocation = ");
        RecyclerView$$ExternalSyntheticOutline0.m(this.mPlaybackLocation, "MediaControlPanel", sb);
        textView.setText((this.mDualPlayModeEnabled && this.mPlaybackLocation == 0) ? this.mContext.getString(R.string.sec_qs_media_player_dual_play_mode_information) : TextUtils.isEmpty(this.mDeviceName) ? this.mContext.getString(R.string.phone_speaker) : this.mDeviceName);
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
        if (this.mType.getSupportExpandable()) {
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
            this.mContext.getColor(R.color.sec_qs_media_player_guts_background_color);
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
            this.mContext.getColor(R.color.sec_qs_media_player_background_color);
            setBackgroundColor();
            TextView textView8 = this.mViewHolder.cancelText;
            if (textView8 != null) {
                textView8.semSetButtonShapeEnabled(true);
            }
        }
    }

    public final void updateWidth() {
        int i;
        int i2 = this.mWidth;
        MediaType mediaType = this.mType;
        if (mediaType == MediaType.OA) {
            OngoingActivityLayoutUtil ongoingActivityLayoutUtil = OngoingActivityLayoutUtil.INSTANCE;
            Context context = this.mContext;
            ongoingActivityLayoutUtil.getClass();
            i = OngoingActivityLayoutUtil.getOngoingCardWidth(context);
        } else {
            MediaType mediaType2 = MediaType.ENR;
            SecQSPanelResourcePicker secQSPanelResourcePicker = this.mResourcePicker;
            if (mediaType == mediaType2) {
                i = secQSPanelResourcePicker.getPanelWidth(this.mContext) - (secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getNotificationSidePadding(this.mContext, true) * 2);
            } else if (mediaType == MediaType.QS) {
                i = secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getAlbumArtWidth(this.mContext);
            } else if (mediaType == MediaType.COVER) {
                i = 748;
            } else if (mediaType == MediaType.COVER_QS) {
                Context context2 = this.mContext;
                Context context3 = this.mCoverContext;
                if (context3 != null) {
                    context2 = context3;
                }
                i = context2.getResources().getDimensionPixelSize(R.dimen.subscreen_qs_media_panel_width);
            } else {
                i = 0;
            }
        }
        this.mWidth = i;
        if (i2 != i) {
            this.mIsWidthUpdated = true;
        }
    }
}
