package com.android.systemui.media;

import android.app.ActivityOptions;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.WallpaperColors;
import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
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
import android.util.Pair;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.compose.runtime.OpaqueKey$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecSimPinViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.CarrierTextManagerLogger$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.systemui.Dependency;
import com.android.systemui.FontSizeUtils;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.audio.soundcraft.utils.SystemServiceExtension;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.MediaLogWriter;
import com.android.systemui.log.MediaLogWriter$$ExternalSyntheticLambda0;
import com.android.systemui.log.MediaLogger;
import com.android.systemui.log.MediaLoggerImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.media.SecMediaHost;
import com.android.systemui.media.SecSeekBarViewModel;
import com.android.systemui.media.controls.domain.pipeline.MediaActionsKt;
import com.android.systemui.media.controls.shared.model.MediaAction;
import com.android.systemui.media.controls.shared.model.MediaButton;
import com.android.systemui.media.controls.shared.model.MediaData;
import com.android.systemui.media.controls.shared.model.MediaDeviceData;
import com.android.systemui.media.controls.ui.SecAnimatingColorTransition;
import com.android.systemui.media.controls.ui.SecColorSchemeTransition;
import com.android.systemui.monet.ColorScheme;
import com.android.systemui.monet.TonalPalette;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.qs.QSImpl$$ExternalSyntheticLambda2;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.bar.BarController;
import com.android.systemui.qs.bar.QSMediaPlayerBar$$ExternalSyntheticLambda3;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityController;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityDataHelper;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityLayoutUtil;
import com.android.systemui.subscreen.SubScreenManager;
import com.android.systemui.subscreen.SubScreenQuickPanelWindowController$$ExternalSyntheticLambda15;
import com.android.systemui.util.ConfigurationState;
import com.android.systemui.util.RecoilEffectUtil;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.samsung.android.knox.zt.config.securelog.SignalSeverity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.BooleanSupplier;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.Intrinsics;

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
            public final void onReceive(Context context2, Intent intent) throws Resources.NotFoundException {
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
                SecMediaControlPanel secMediaControlPanel = this.f$0;
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

    public final void attach(SecPlayerViewHolder secPlayerViewHolder) throws Resources.NotFoundException {
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
    /* JADX WARN: Removed duplicated region for block: B:214:0x040c  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x010f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void bind(final MediaData mediaData, final String str) throws Resources.NotFoundException {
        int dimensionPixelSize;
        boolean z;
        ImageButton imageButton;
        int i;
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
        int i2 = mediaData.userId;
        if (icon != null) {
            imageView.setImageDrawable(icon.loadDrawableAsUser(this.mContext, i2));
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
        boolean z3 = this.mType.getSupportCoverQuickPanelMedia() ? !textView.getText().equals(makeTitleWithArtist(mediaData.song, mediaData.artist)) : (textView.getText().equals(mediaData.song) && textView2.getText().equals(mediaData.artist)) ? false : true;
        if (z3) {
            if (this.mType.getSupportCoverQuickPanelMedia()) {
                textView.setText(Notification.safeCharSequence(makeTitleWithArtist(mediaData.song, mediaData.artist)));
            } else {
                textView.setText(Notification.safeCharSequence(mediaData.song));
                textView2.setText(Notification.safeCharSequence(mediaData.artist));
            }
        }
        String str2 = mediaData.app;
        this.mAppName = str2;
        if (!this.mType.getSupportCoverQuickPanelMedia()) {
            MediaDeviceData mediaDeviceData = mediaData.device;
            if (mediaDeviceData != null) {
                this.mDeviceName = mediaDeviceData.name;
                this.mPlaybackLocation = mediaData.playbackLocation;
                if (mediaDeviceData.customMediaDeviceData.deviceType == null || (anonymousClass1 = this.mQSMediaPlayerBarCallback) == null) {
                    z2 = false;
                    this.mDualPlayModeEnabled = z2;
                } else {
                    BluetoothA2dp bluetoothA2dp = SecMediaHost.this.mMediaBluetoothHelper.a2dp;
                    if (bluetoothA2dp != null ? bluetoothA2dp.semIsDualPlayMode() : false) {
                        z2 = true;
                    }
                    this.mDualPlayModeEnabled = z2;
                }
            } else {
                Log.w("MediaControlPanel", "device is null");
                this.mDeviceName = this.mContext.getString(R.string.phone_speaker);
            }
            if (this.mType.getSupportCoverQuickPanelMedia()) {
                TextView textView3 = this.mViewHolder.mediaOutputText;
                if (textView3 == null) {
                    textView3 = null;
                }
                textView3.setVisibility(8);
            } else if (!this.mType.getSupportMediaOutput() || this.mPlaybackLocation == 2) {
                TextView textView4 = this.mViewHolder.mediaOutputText;
                if (textView4 == null) {
                    textView4 = null;
                }
                textView4.setOnClickListener(null);
                TextView textView5 = this.mViewHolder.mediaOutputText;
                if (textView5 == null) {
                    textView5 = null;
                }
                textView5.setVisibility(4);
            } else {
                TextView textView6 = this.mViewHolder.mediaOutputText;
                if (textView6 == null) {
                    textView6 = null;
                }
                textView6.setVisibility(0);
                TextView textView7 = this.mViewHolder.mediaOutputText;
                if (textView7 == null) {
                    textView7 = null;
                }
                final int i3 = 0;
                textView7.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.systemui.media.SecMediaControlPanel$$ExternalSyntheticLambda6
                    public final /* synthetic */ SecMediaControlPanel f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) throws Resources.NotFoundException {
                        BarController.AnonymousClass3 anonymousClass3;
                        switch (i3) {
                            case 0:
                                SecMediaControlPanel secMediaControlPanel = this.f$0;
                                MediaData mediaData2 = mediaData;
                                secMediaControlPanel.getClass();
                                SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_QPNE_OUTPUT_SWITCHER, SystemUIAnalytics.QPNE_KEY_APP, mediaData2.packageName);
                                Log.d("MediaControlPanel", "MEDIA_OUTPUT_OPEN");
                                boolean supportDetailView = secMediaControlPanel.mType.getSupportDetailView();
                                MediaOutputHelper mediaOutputHelper = secMediaControlPanel.mMediaOutputHelper;
                                if (!supportDetailView) {
                                    Context context = secMediaControlPanel.mContext;
                                    mediaOutputHelper.getClass();
                                    Intent intent = new Intent("com.android.systemui.action.OPEN_MEDIA_OUTPUT");
                                    intent.setPackage(context.getPackageName());
                                    intent.setFlags(335544320);
                                    String str3 = mediaData2.packageName;
                                    if (str3 != null) {
                                        intent.putExtra("android.intent.extra.PACKAGE_NAME", str3);
                                    }
                                    context.startActivity(intent, ActivityOptions.makeBasic().setLaunchDisplayId(1).toBundle());
                                    break;
                                } else {
                                    mediaOutputHelper.showDetail();
                                    break;
                                }
                            default:
                                SecMediaControlPanel secMediaControlPanel2 = this.f$0;
                                MediaData mediaData3 = mediaData;
                                secMediaControlPanel2.getClass();
                                SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_QPNE_REMOVE_MEDIA, SystemUIAnalytics.QPNE_KEY_APP, mediaData3.packageName);
                                String strName = secMediaControlPanel2.mType.name();
                                String str4 = secMediaControlPanel2.mPlayerKey;
                                MediaLogWriter mediaLogWriter = ((MediaLoggerImpl) secMediaControlPanel2.mLogger).writer;
                                mediaLogWriter.getClass();
                                LogLevel logLevel = LogLevel.DEBUG;
                                MediaLogWriter$$ExternalSyntheticLambda0 mediaLogWriter$$ExternalSyntheticLambda0 = new MediaLogWriter$$ExternalSyntheticLambda0(6);
                                LogBuffer logBuffer = mediaLogWriter.buffer;
                                LogMessage logMessageObtain = logBuffer.obtain("MediaLogger", logLevel, mediaLogWriter$$ExternalSyntheticLambda0, null);
                                ((LogMessageImpl) logMessageObtain).str1 = str4;
                                logBuffer.commit(logMessageObtain);
                                Log.d("MediaLogger", "[" + strName + "] Media remove clicked [" + str4 + "]");
                                ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder("Removing player from QSPanel : "), secMediaControlPanel2.mAppName, "MediaControlPanel");
                                SecMediaHost.AnonymousClass1 anonymousClass12 = secMediaControlPanel2.mQSMediaPlayerBarCallback;
                                if (anonymousClass12 != null) {
                                    String str5 = secMediaControlPanel2.mPlayerKey;
                                    SecMediaHost secMediaHost = SecMediaHost.this;
                                    secMediaHost.mMediaDataManager.dismissMediaData(str5, 100L, true);
                                    if (anonymousClass12.val$playerData.getSortedMediaPlayersSize() <= 0) {
                                        secMediaHost.onMediaVisibilityChanged(Boolean.FALSE);
                                        BarController.AnonymousClass4 anonymousClass4 = secMediaHost.mMediaBarCallback;
                                        if (anonymousClass4 != null && (anonymousClass3 = BarController.this.mBarListener) != null) {
                                            QSImpl$$ExternalSyntheticLambda2 qSImpl$$ExternalSyntheticLambda2 = BarController.this.mQSLastExpansionInitializer;
                                            if (qSImpl$$ExternalSyntheticLambda2 != null) {
                                                qSImpl$$ExternalSyntheticLambda2.run();
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
                TextView textView8 = this.mViewHolder.mediaOutputText;
                if (textView8 == null) {
                    textView8 = null;
                }
                ((View) textView8.getParent()).setOnTouchListener(new SecMediaControlPanel$$ExternalSyntheticLambda5(this, 1));
            }
            updateDeviceName();
        }
        List list = mediaData.actions;
        List list2 = mediaData.actionsToShowInCompact;
        StringBuilder sb = new StringBuilder("bindActionButtons semanticActions=");
        MediaButton mediaButton = mediaData.semanticActions;
        sb.append(mediaButton);
        Log.d("MediaControlPanel", sb.toString());
        Log.d("MediaControlPanel", "bindActionButtons actionsWhenCollapsed=" + list2 + ", actionIcons=" + list);
        ArrayList arrayList = new ArrayList();
        boolean supportCoverQuickPanelMedia = this.mType.getSupportCoverQuickPanelMedia();
        ActivityStarter activityStarter = this.mActivityStarter;
        if (supportCoverQuickPanelMedia) {
            if (mediaButton != null) {
                MediaAction mediaAction = mediaButton.prevOrCustom;
                if (mediaAction != null) {
                    arrayList.add(mediaAction);
                }
                MediaAction mediaAction2 = mediaButton.playOrPause;
                if (mediaAction2 != null) {
                    arrayList.add(mediaAction2);
                }
                MediaAction mediaAction3 = mediaButton.nextOrCustom;
                if (mediaAction3 != null) {
                    arrayList.add(mediaAction3);
                }
            } else {
                List notificationActions = MediaActionsKt.getNotificationActions(list, activityStarter);
                Iterator it = list2.iterator();
                while (it.hasNext()) {
                    int iIntValue = ((Integer) it.next()).intValue();
                    if (iIntValue >= 0) {
                        ArrayList arrayList2 = (ArrayList) notificationActions;
                        if (iIntValue < arrayList2.size() && arrayList2.get(iIntValue) != null) {
                            arrayList.add((MediaAction) arrayList2.get(iIntValue));
                        }
                    }
                }
            }
        } else if (mediaButton != null) {
            MediaAction mediaAction4 = mediaButton.custom0;
            if (mediaAction4 != null) {
                arrayList.add(mediaAction4);
            }
            MediaAction mediaAction5 = mediaButton.prevOrCustom;
            if (mediaAction5 != null) {
                arrayList.add(mediaAction5);
            }
            MediaAction mediaAction6 = mediaButton.playOrPause;
            if (mediaAction6 != null) {
                arrayList.add(mediaAction6);
            }
            MediaAction mediaAction7 = mediaButton.nextOrCustom;
            if (mediaAction7 != null) {
                arrayList.add(mediaAction7);
            }
            MediaAction mediaAction8 = mediaButton.custom1;
            if (mediaAction8 != null) {
                arrayList.add(mediaAction8);
            }
        } else {
            arrayList.addAll(MediaActionsKt.getNotificationActions(list, activityStarter));
        }
        this.mActionButtonNumExpand = arrayList.size();
        boolean supportExpandable = this.mType.getSupportExpandable();
        int[] iArr = ACTION_IDS;
        if (supportExpandable) {
            if (this.mType.getSupportCoverQuickPanelMedia()) {
                Context context = this.mContext;
                Context context2 = this.mCoverContext;
                if (context2 != null) {
                    context = context2;
                }
                dimensionPixelSize = this.mActionButtonNumExpand == 3 ? context.getResources().getDimensionPixelSize(R.dimen.sec_cover_qs_media_player_action_button_margin) : context.getResources().getDimensionPixelSize(R.dimen.sec_cover_qs_media_player_action_button_size_expand);
            } else {
                Resources resources = this.mContext.getResources();
                int i4 = this.mActionButtonNumExpand;
                dimensionPixelSize = i4 != 2 ? i4 != 3 ? i4 != 4 ? i4 != 5 ? 0 : resources.getDimensionPixelSize(R.dimen.qs_media_action_button_side_padding_5) : resources.getDimensionPixelSize(R.dimen.qs_media_action_button_side_padding_4) : resources.getDimensionPixelSize(R.dimen.qs_media_action_button_side_padding_3) : resources.getDimensionPixelSize(R.dimen.qs_media_action_button_side_padding_2);
            }
            int dimensionPixelSize2 = this.mContext.getResources().getDimensionPixelSize((QpRune.QUICK_TABLET || ((SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class)).isTablet()) ? R.dimen.sec_qs_media_player_action_button_size_collapsed_tablet : R.dimen.sec_qs_media_player_action_button_size_expand);
            if (this.mType.getSupportCoverQuickPanelMedia()) {
                Context context3 = this.mContext;
                Context context4 = this.mCoverContext;
                if (context4 != null) {
                    context3 = context4;
                }
                dimensionPixelSize2 = context3.getResources().getDimensionPixelSize(R.dimen.sec_cover_qs_media_player_action_button_size_expand);
            }
            int i5 = 0;
            for (int i6 = 5; i5 < this.mActionButtonNumExpand && i5 < i6; i6 = 5) {
                ImageButton imageButton2 = (ImageButton) ((SparseArray) this.mViewHolder.expandedActionButtons$delegate.getValue()).get(iArr[i5]);
                if (imageButton2 == null) {
                    throw new IllegalArgumentException();
                }
                LinearLayout linearLayout = (LinearLayout) imageButton2.getParent();
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) imageButton2.getLayoutParams();
                layoutParams.width = dimensionPixelSize2;
                int i7 = dimensionPixelSize;
                LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
                layoutParams2.width = dimensionPixelSize2 + i7;
                imageButton2.setLayoutParams(layoutParams);
                linearLayout.setLayoutParams(layoutParams2);
                i5++;
                z3 = z3;
                dimensionPixelSize = i7;
            }
            int i8 = dimensionPixelSize;
            z = z3;
            if (this.mType.getSupportBudsButton() && (imageButton = this.mBudsButtonExpanded) != null) {
                LinearLayout linearLayout2 = (LinearLayout) imageButton.getParent();
                LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) this.mBudsButtonExpanded.getLayoutParams();
                layoutParams3.width = dimensionPixelSize2;
                LinearLayout.LayoutParams layoutParams4 = (LinearLayout.LayoutParams) linearLayout2.getLayoutParams();
                layoutParams4.width = dimensionPixelSize2 + i8;
                this.mBudsButtonExpanded.setLayoutParams(layoutParams3);
                linearLayout2.setLayoutParams(layoutParams4);
            }
        } else {
            z = z3;
        }
        int iMin = Math.min(arrayList.size(), 5);
        int i9 = 0;
        for (int i10 = 5; i9 < i10; i10 = 5) {
            int i11 = iArr[i9];
            boolean z4 = i9 < arrayList.size();
            MediaAction mediaAction9 = z4 ? (MediaAction) arrayList.get(i9) : null;
            final ImageButton imageButton3 = (ImageButton) ((SparseArray) this.mViewHolder.expandedActionButtons$delegate.getValue()).get(i11);
            if (imageButton3 == null) {
                throw new IllegalArgumentException();
            }
            if (z4) {
                final SecMediaControlPanel$$ExternalSyntheticLambda8 secMediaControlPanel$$ExternalSyntheticLambda8 = new SecMediaControlPanel$$ExternalSyntheticLambda8(this, iMin, mediaData.packageName, imageButton3);
                if (mediaAction9 == null) {
                    i = iMin;
                } else {
                    Drawable drawable = mediaAction9.icon;
                    if (drawable == null || (constantState = drawable.getConstantState()) == null) {
                        i = iMin;
                    } else {
                        Drawable drawableNewDrawable = constantState.newDrawable();
                        i = iMin;
                        if (drawableNewDrawable instanceof Animatable) {
                            ((Animatable) drawableNewDrawable).start();
                        }
                        imageButton3.setImageDrawable(drawableNewDrawable);
                    }
                    imageButton3.setContentDescription(mediaAction9.contentDescription);
                    final Runnable runnable = mediaAction9.action;
                    if (runnable == null) {
                        imageButton3.setEnabled(false);
                    } else {
                        imageButton3.setEnabled(true);
                        final int i12 = 2;
                        imageButton3.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.media.SecMediaControlPanel$$ExternalSyntheticLambda11
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                SubScreenQuickPanelWindowController$$ExternalSyntheticLambda15 subScreenQuickPanelWindowController$$ExternalSyntheticLambda15;
                                switch (i12) {
                                    case 0:
                                        SecMediaControlPanel secMediaControlPanel = (SecMediaControlPanel) secMediaControlPanel$$ExternalSyntheticLambda8;
                                        View view2 = (View) imageButton3;
                                        View view3 = (View) runnable;
                                        secMediaControlPanel.getClass();
                                        view2.setVisibility(8);
                                        view3.setVisibility(0);
                                        secMediaControlPanel.setBackgroundColor();
                                        break;
                                    case 1:
                                        SecMediaControlPanel secMediaControlPanel2 = (SecMediaControlPanel) secMediaControlPanel$$ExternalSyntheticLambda8;
                                        PendingIntent pendingIntent = (PendingIntent) imageButton3;
                                        MediaData mediaData2 = (MediaData) runnable;
                                        if (pendingIntent != null) {
                                            if (!secMediaControlPanel2.mType.getSupportCoverQuickPanelMedia()) {
                                                SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_QPNE_OPEN_APP_MEDIA, SystemUIAnalytics.QPNE_KEY_APP, mediaData2.packageName);
                                                secMediaControlPanel2.mActivityStarter.postStartActivityDismissingKeyguard(pendingIntent, true);
                                                break;
                                            } else {
                                                SubScreenQuickPanelWindowController$$ExternalSyntheticLambda15 subScreenQuickPanelWindowController$$ExternalSyntheticLambda152 = secMediaControlPanel2.mCoverQSClickConsumer;
                                                if (subScreenQuickPanelWindowController$$ExternalSyntheticLambda152 != null) {
                                                    subScreenQuickPanelWindowController$$ExternalSyntheticLambda152.accept(new Pair(pendingIntent, mediaData2.packageName));
                                                    break;
                                                }
                                            }
                                        } else {
                                            secMediaControlPanel2.getClass();
                                            Log.d("MediaControlPanel", "click intent is null");
                                            if (secMediaControlPanel2.mType.getSupportCoverQuickPanelMedia() && (subScreenQuickPanelWindowController$$ExternalSyntheticLambda15 = secMediaControlPanel2.mCoverQSClickConsumer) != null) {
                                                subScreenQuickPanelWindowController$$ExternalSyntheticLambda15.accept(new Pair(null, null));
                                                break;
                                            }
                                        }
                                        break;
                                    default:
                                        SecMediaControlPanel$$ExternalSyntheticLambda8 secMediaControlPanel$$ExternalSyntheticLambda82 = (SecMediaControlPanel$$ExternalSyntheticLambda8) secMediaControlPanel$$ExternalSyntheticLambda8;
                                        ImageButton imageButton4 = (ImageButton) imageButton3;
                                        Runnable runnable2 = (Runnable) runnable;
                                        secMediaControlPanel$$ExternalSyntheticLambda82.accept(imageButton4);
                                        runnable2.run();
                                        break;
                                }
                            }
                        });
                        if (this.mType.getSupportExpandable()) {
                            ((View) imageButton3.getParent()).setOnTouchListener(new SecMediaControlPanel$$ExternalSyntheticLambda5(imageButton3, 2));
                        }
                    }
                }
            }
            if (this.mType.getSupportExpandable()) {
                ((View) imageButton3.getParent()).setVisibility(z4 ? 0 : 8);
            }
            imageButton3.setVisibility(z4 ? 0 : 8);
            i9++;
            iMin = i;
        }
        final MediaController mediaController = this.mController;
        this.mBackgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.media.SecMediaControlPanel$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                SecMediaControlPanel secMediaControlPanel = this.f$0;
                MediaController mediaController2 = mediaController;
                String strName = secMediaControlPanel.mType.name();
                SecSeekBarViewModel secSeekBarViewModel = secMediaControlPanel.mSeekBarViewModel;
                secSeekBarViewModel.setController(mediaController2);
                MediaController mediaController3 = secSeekBarViewModel.controller;
                secSeekBarViewModel.playbackState = mediaController3 != null ? mediaController3.getPlaybackState() : null;
                MediaController mediaController4 = secSeekBarViewModel.controller;
                kotlin.Pair enabledStateAndDuration = secSeekBarViewModel.getEnabledStateAndDuration(mediaController4 != null ? mediaController4.getMetadata() : null);
                boolean zBooleanValue = ((Boolean) enabledStateAndDuration.component1()).booleanValue();
                int iIntValue2 = ((Number) enabledStateAndDuration.component2()).intValue();
                PlaybackState playbackState = secSeekBarViewModel.playbackState;
                boolean z5 = ((playbackState != null ? playbackState.getActions() : 0L) & 256) != 0;
                PlaybackState playbackState2 = secSeekBarViewModel.playbackState;
                Integer numValueOf = playbackState2 != null ? Integer.valueOf((int) playbackState2.getPosition()) : null;
                PlaybackState playbackState3 = secSeekBarViewModel.playbackState;
                boolean zIsPlayingState = NotificationMediaManager.isPlayingState(playbackState3 != null ? playbackState3.getState() : 0);
                secSeekBarViewModel.set_data(new SecSeekBarViewModel.Progress(zBooleanValue, z5, zIsPlayingState, secSeekBarViewModel.scrubbing, numValueOf, iIntValue2, secSeekBarViewModel.listening));
                boolean z6 = secSeekBarViewModel.scrubbing;
                boolean z7 = secSeekBarViewModel.listening;
                StringBuilder sbM = CarrierTextManagerLogger$$ExternalSyntheticOutline0.m("[", strName, "] updateController enabled ", " seekAvailable ", zBooleanValue);
                KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sbM, z5, " playing ", zIsPlayingState, " scrubbing ");
                sbM.append(z6);
                sbM.append(" position ");
                sbM.append(numValueOf);
                sbM.append(" duration ");
                sbM.append(iIntValue2);
                sbM.append(" listening ");
                sbM.append(z7);
                android.util.secutil.Log.d("SecSeekBarViewModel", sbM.toString());
                secSeekBarViewModel.checkIfPollingNeeded(false);
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
                view.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.android.systemui.media.SecMediaControlPanel$$ExternalSyntheticLambda9
                    @Override // android.view.View.OnLongClickListener
                    public final boolean onLongClick(View view2) {
                        SecMediaControlPanel secMediaControlPanel = this.f$0;
                        View view3 = view;
                        View view4 = linearLayout3;
                        secMediaControlPanel.getClass();
                        view3.setVisibility(8);
                        view4.setVisibility(0);
                        secMediaControlPanel.setBackgroundColor();
                        return true;
                    }
                });
                TextView textView9 = this.mViewHolder.removeText;
                if (textView9 != null) {
                    final int i13 = 1;
                    textView9.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.systemui.media.SecMediaControlPanel$$ExternalSyntheticLambda6
                        public final /* synthetic */ SecMediaControlPanel f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view2) throws Resources.NotFoundException {
                            BarController.AnonymousClass3 anonymousClass3;
                            switch (i13) {
                                case 0:
                                    SecMediaControlPanel secMediaControlPanel = this.f$0;
                                    MediaData mediaData2 = mediaData;
                                    secMediaControlPanel.getClass();
                                    SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_QPNE_OUTPUT_SWITCHER, SystemUIAnalytics.QPNE_KEY_APP, mediaData2.packageName);
                                    Log.d("MediaControlPanel", "MEDIA_OUTPUT_OPEN");
                                    boolean supportDetailView = secMediaControlPanel.mType.getSupportDetailView();
                                    MediaOutputHelper mediaOutputHelper = secMediaControlPanel.mMediaOutputHelper;
                                    if (!supportDetailView) {
                                        Context context5 = secMediaControlPanel.mContext;
                                        mediaOutputHelper.getClass();
                                        Intent intent = new Intent("com.android.systemui.action.OPEN_MEDIA_OUTPUT");
                                        intent.setPackage(context5.getPackageName());
                                        intent.setFlags(335544320);
                                        String str3 = mediaData2.packageName;
                                        if (str3 != null) {
                                            intent.putExtra("android.intent.extra.PACKAGE_NAME", str3);
                                        }
                                        context5.startActivity(intent, ActivityOptions.makeBasic().setLaunchDisplayId(1).toBundle());
                                        break;
                                    } else {
                                        mediaOutputHelper.showDetail();
                                        break;
                                    }
                                default:
                                    SecMediaControlPanel secMediaControlPanel2 = this.f$0;
                                    MediaData mediaData3 = mediaData;
                                    secMediaControlPanel2.getClass();
                                    SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_QPNE_REMOVE_MEDIA, SystemUIAnalytics.QPNE_KEY_APP, mediaData3.packageName);
                                    String strName = secMediaControlPanel2.mType.name();
                                    String str4 = secMediaControlPanel2.mPlayerKey;
                                    MediaLogWriter mediaLogWriter = ((MediaLoggerImpl) secMediaControlPanel2.mLogger).writer;
                                    mediaLogWriter.getClass();
                                    LogLevel logLevel = LogLevel.DEBUG;
                                    MediaLogWriter$$ExternalSyntheticLambda0 mediaLogWriter$$ExternalSyntheticLambda0 = new MediaLogWriter$$ExternalSyntheticLambda0(6);
                                    LogBuffer logBuffer = mediaLogWriter.buffer;
                                    LogMessage logMessageObtain = logBuffer.obtain("MediaLogger", logLevel, mediaLogWriter$$ExternalSyntheticLambda0, null);
                                    ((LogMessageImpl) logMessageObtain).str1 = str4;
                                    logBuffer.commit(logMessageObtain);
                                    Log.d("MediaLogger", "[" + strName + "] Media remove clicked [" + str4 + "]");
                                    ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder("Removing player from QSPanel : "), secMediaControlPanel2.mAppName, "MediaControlPanel");
                                    SecMediaHost.AnonymousClass1 anonymousClass12 = secMediaControlPanel2.mQSMediaPlayerBarCallback;
                                    if (anonymousClass12 != null) {
                                        String str5 = secMediaControlPanel2.mPlayerKey;
                                        SecMediaHost secMediaHost = SecMediaHost.this;
                                        secMediaHost.mMediaDataManager.dismissMediaData(str5, 100L, true);
                                        if (anonymousClass12.val$playerData.getSortedMediaPlayersSize() <= 0) {
                                            secMediaHost.onMediaVisibilityChanged(Boolean.FALSE);
                                            BarController.AnonymousClass4 anonymousClass4 = secMediaHost.mMediaBarCallback;
                                            if (anonymousClass4 != null && (anonymousClass3 = BarController.this.mBarListener) != null) {
                                                QSImpl$$ExternalSyntheticLambda2 qSImpl$$ExternalSyntheticLambda2 = BarController.this.mQSLastExpansionInitializer;
                                                if (qSImpl$$ExternalSyntheticLambda2 != null) {
                                                    qSImpl$$ExternalSyntheticLambda2.run();
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
                    updateFontSize(textView9, R.dimen.sec_qs_media_panel_options_remove_text_size);
                }
                TextView textView10 = this.mViewHolder.cancelText;
                if (textView10 != null) {
                    final int i14 = 0;
                    textView10.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.media.SecMediaControlPanel$$ExternalSyntheticLambda11
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view2) {
                            SubScreenQuickPanelWindowController$$ExternalSyntheticLambda15 subScreenQuickPanelWindowController$$ExternalSyntheticLambda15;
                            switch (i14) {
                                case 0:
                                    SecMediaControlPanel secMediaControlPanel = (SecMediaControlPanel) this;
                                    View view22 = (View) view;
                                    View view3 = (View) linearLayout3;
                                    secMediaControlPanel.getClass();
                                    view22.setVisibility(8);
                                    view3.setVisibility(0);
                                    secMediaControlPanel.setBackgroundColor();
                                    break;
                                case 1:
                                    SecMediaControlPanel secMediaControlPanel2 = (SecMediaControlPanel) this;
                                    PendingIntent pendingIntent = (PendingIntent) view;
                                    MediaData mediaData2 = (MediaData) linearLayout3;
                                    if (pendingIntent != null) {
                                        if (!secMediaControlPanel2.mType.getSupportCoverQuickPanelMedia()) {
                                            SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_QPNE_OPEN_APP_MEDIA, SystemUIAnalytics.QPNE_KEY_APP, mediaData2.packageName);
                                            secMediaControlPanel2.mActivityStarter.postStartActivityDismissingKeyguard(pendingIntent, true);
                                            break;
                                        } else {
                                            SubScreenQuickPanelWindowController$$ExternalSyntheticLambda15 subScreenQuickPanelWindowController$$ExternalSyntheticLambda152 = secMediaControlPanel2.mCoverQSClickConsumer;
                                            if (subScreenQuickPanelWindowController$$ExternalSyntheticLambda152 != null) {
                                                subScreenQuickPanelWindowController$$ExternalSyntheticLambda152.accept(new Pair(pendingIntent, mediaData2.packageName));
                                                break;
                                            }
                                        }
                                    } else {
                                        secMediaControlPanel2.getClass();
                                        Log.d("MediaControlPanel", "click intent is null");
                                        if (secMediaControlPanel2.mType.getSupportCoverQuickPanelMedia() && (subScreenQuickPanelWindowController$$ExternalSyntheticLambda15 = secMediaControlPanel2.mCoverQSClickConsumer) != null) {
                                            subScreenQuickPanelWindowController$$ExternalSyntheticLambda15.accept(new Pair(null, null));
                                            break;
                                        }
                                    }
                                    break;
                                default:
                                    SecMediaControlPanel$$ExternalSyntheticLambda8 secMediaControlPanel$$ExternalSyntheticLambda82 = (SecMediaControlPanel$$ExternalSyntheticLambda8) this;
                                    ImageButton imageButton4 = (ImageButton) view;
                                    Runnable runnable2 = (Runnable) linearLayout3;
                                    secMediaControlPanel$$ExternalSyntheticLambda82.accept(imageButton4);
                                    runnable2.run();
                                    break;
                            }
                        }
                    });
                    updateFontSize(textView10, R.dimen.sec_qs_media_panel_options_cancel_text_size);
                }
                ImageView imageView2 = this.mViewHolder.optionsAppIcon;
                if (imageView2 != null) {
                    Icon icon2 = mediaData.appIcon;
                    if (icon2 != null) {
                        imageView2.setImageDrawable(icon2.loadDrawableAsUser(this.mContext, i2));
                    } else {
                        imageView2.setImageResource(R.drawable.ic_music_note);
                    }
                }
                TextView textView11 = this.mViewHolder.optionsAppTitle;
                if (textView11 != null) {
                    textView11.setText(str2);
                    updateFontSize(textView11, R.dimen.sec_qs_media_panel_options_app_name_text_size);
                }
                if (view.getVisibility() != 0 || isPlaying()) {
                    view.setVisibility(8);
                    linearLayout3.setVisibility(0);
                }
                View view2 = this.mViewHolder.playerView;
                if (view2 == null) {
                    view2 = null;
                }
                view2.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.android.systemui.media.SecMediaControlPanel$$ExternalSyntheticLambda12
                    @Override // android.view.View.OnLongClickListener
                    public final boolean onLongClick(View view3) {
                        SecMediaControlPanel secMediaControlPanel = this.f$0;
                        LinearLayout linearLayout4 = secMediaControlPanel.mViewHolder.player;
                        if (linearLayout4 != null) {
                            linearLayout4.setVisibility(8);
                        }
                        View view4 = secMediaControlPanel.mViewHolder.options;
                        if (view4 != null) {
                            view4.setVisibility(0);
                        }
                        secMediaControlPanel.setBackgroundColor();
                        return true;
                    }
                });
            }
        }
        if (this.mType.getSupportExpandable()) {
            if (!this.mType.getSupportCoverQuickPanelMedia()) {
                this.mContext.getColor(R.color.sec_qs_media_player_guts_background_color);
                this.mContext.getColor(R.color.sec_qs_media_player_background_color);
                setBackgroundColor();
            }
            if (this.mType.getSupportClick()) {
                final PendingIntent pendingIntent = mediaData.clickIntent;
                View view3 = this.mViewHolder.playerView;
                if (view3 == null) {
                    view3 = null;
                }
                final int i15 = 1;
                view3.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.media.SecMediaControlPanel$$ExternalSyntheticLambda11
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view22) {
                        SubScreenQuickPanelWindowController$$ExternalSyntheticLambda15 subScreenQuickPanelWindowController$$ExternalSyntheticLambda15;
                        switch (i15) {
                            case 0:
                                SecMediaControlPanel secMediaControlPanel = (SecMediaControlPanel) this;
                                View view222 = (View) pendingIntent;
                                View view32 = (View) mediaData;
                                secMediaControlPanel.getClass();
                                view222.setVisibility(8);
                                view32.setVisibility(0);
                                secMediaControlPanel.setBackgroundColor();
                                break;
                            case 1:
                                SecMediaControlPanel secMediaControlPanel2 = (SecMediaControlPanel) this;
                                PendingIntent pendingIntent2 = (PendingIntent) pendingIntent;
                                MediaData mediaData2 = (MediaData) mediaData;
                                if (pendingIntent2 != null) {
                                    if (!secMediaControlPanel2.mType.getSupportCoverQuickPanelMedia()) {
                                        SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_QPNE_OPEN_APP_MEDIA, SystemUIAnalytics.QPNE_KEY_APP, mediaData2.packageName);
                                        secMediaControlPanel2.mActivityStarter.postStartActivityDismissingKeyguard(pendingIntent2, true);
                                        break;
                                    } else {
                                        SubScreenQuickPanelWindowController$$ExternalSyntheticLambda15 subScreenQuickPanelWindowController$$ExternalSyntheticLambda152 = secMediaControlPanel2.mCoverQSClickConsumer;
                                        if (subScreenQuickPanelWindowController$$ExternalSyntheticLambda152 != null) {
                                            subScreenQuickPanelWindowController$$ExternalSyntheticLambda152.accept(new Pair(pendingIntent2, mediaData2.packageName));
                                            break;
                                        }
                                    }
                                } else {
                                    secMediaControlPanel2.getClass();
                                    Log.d("MediaControlPanel", "click intent is null");
                                    if (secMediaControlPanel2.mType.getSupportCoverQuickPanelMedia() && (subScreenQuickPanelWindowController$$ExternalSyntheticLambda15 = secMediaControlPanel2.mCoverQSClickConsumer) != null) {
                                        subScreenQuickPanelWindowController$$ExternalSyntheticLambda15.accept(new Pair(null, null));
                                        break;
                                    }
                                }
                                break;
                            default:
                                SecMediaControlPanel$$ExternalSyntheticLambda8 secMediaControlPanel$$ExternalSyntheticLambda82 = (SecMediaControlPanel$$ExternalSyntheticLambda8) this;
                                ImageButton imageButton4 = (ImageButton) pendingIntent;
                                Runnable runnable2 = (Runnable) mediaData;
                                secMediaControlPanel$$ExternalSyntheticLambda82.accept(imageButton4);
                                runnable2.run();
                                break;
                        }
                    }
                });
            }
        }
        if (this.mType.getSupportOAChip()) {
            OAMusicChipController oAMusicChipController = this.mOAMusicChipController;
            PendingIntent pendingIntent2 = mediaData.clickIntent;
            MediaController mediaController2 = this.mController;
            if (!Intrinsics.areEqual(oAMusicChipController.clickIntent, pendingIntent2)) {
                oAMusicChipController.clickIntent = pendingIntent2;
                oAMusicChipController.updatePlaybackState(mediaController2 != null ? mediaController2.getPlaybackState() : null);
            }
        }
        if (this.mViewHolder != null) {
            if (this.mType.getSupportCoverQuickPanelMedia()) {
                SeekBar seekBar = this.mViewHolder.seekBar;
                if (seekBar == null) {
                    seekBar = null;
                }
                seekBar.setVisibility(8);
            } else {
                SeekBar seekBar2 = this.mViewHolder.seekBar;
                if (seekBar2 == null) {
                    seekBar2 = null;
                }
                seekBar2.setVisibility(0);
                int dimensionPixelSize3 = this.mContext.getResources().getDimensionPixelSize(R.dimen.sec_media_view_seek_bar_padding);
                SeekBar seekBar3 = this.mViewHolder.seekBar;
                if (seekBar3 == null) {
                    seekBar3 = null;
                }
                seekBar3.setPadding(dimensionPixelSize3, 0, dimensionPixelSize3, 0);
            }
        }
        updateResources();
        Log.d("MediaControlPanel", "ColorUpdate bindArtworkAndColors key " + str);
        if (this.mType.getSupportArtwork()) {
            final int iHashCode = mediaData.hashCode();
            final String str3 = "MediaControlPanel#bindArtworkAndColors<" + str + ">";
            Trace.beginAsyncSection(str3, iHashCode);
            final int i16 = this.mArtworkNextBindRequestId;
            this.mArtworkNextBindRequestId = i16 + 1;
            if (z) {
                this.mIsArtworkBound = false;
            }
            final int i17 = this.mWidth;
            if (!this.mType.getSupportCoverQuickPanelMedia()) {
                ImageView imageView3 = this.mViewHolder.albumView;
                if (imageView3 == null) {
                    imageView3 = null;
                }
                ((GradientDrawable) imageView3.getForeground()).setSize(i17, i17);
            }
            final boolean z5 = z;
            this.mBackgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.media.SecMediaControlPanel$$ExternalSyntheticLambda14
                /* JADX WARN: Removed duplicated region for block: B:46:0x012f  */
                /* JADX WARN: Removed duplicated region for block: B:48:0x0137  */
                @Override // java.lang.Runnable
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final void run() throws PackageManager.NameNotFoundException {
                    final Drawable colorDrawable;
                    final Drawable drawable2;
                    final ColorScheme colorScheme;
                    final boolean z6;
                    Drawable drawable3;
                    final int i18;
                    int s700;
                    Drawable drawableLoadDrawable;
                    final SecMediaControlPanel secMediaControlPanel = this.f$0;
                    final MediaData mediaData2 = mediaData;
                    final String str4 = str;
                    final int i19 = i17;
                    final int i20 = i17;
                    final int i21 = i16;
                    final String str5 = str3;
                    final int i22 = iHashCode;
                    final boolean z7 = z5;
                    secMediaControlPanel.getClass();
                    Icon icon3 = mediaData2.artwork;
                    String str6 = mediaData2.packageName;
                    if (icon3 == null || !(icon3.getType() == 1 || icon3.getType() == 5)) {
                        Log.d("MediaControlPanel", "ColorUpdate bindArtworkAndColors no artwork key " + str4);
                        colorDrawable = new ColorDrawable(0);
                        try {
                            Drawable applicationIcon = secMediaControlPanel.mContext.getPackageManager().getApplicationIcon(str6);
                            if (secMediaControlPanel.mType.getSupportCoverQuickPanelMedia()) {
                                Context context5 = secMediaControlPanel.mContext;
                                Context context6 = secMediaControlPanel.mCoverContext;
                                if (context6 != null) {
                                    context5 = context6;
                                }
                                drawable3 = context5.getDrawable(R.drawable.cover_quick_panel_no_albumart_icon);
                            } else {
                                drawable3 = colorDrawable;
                            }
                            try {
                                colorScheme = new ColorScheme(WallpaperColors.fromDrawable(applicationIcon), true, 6);
                                z6 = false;
                                colorDrawable = drawable3;
                                drawable2 = applicationIcon;
                            } catch (PackageManager.NameNotFoundException e) {
                                e = e;
                                colorDrawable = drawable3;
                                Log.w("MediaControlPanel", "Cannot find icon for package " + str6, e);
                                drawable2 = null;
                                colorScheme = null;
                                z6 = false;
                                if (colorScheme == null) {
                                }
                                i18 = s700;
                                secMediaControlPanel.mMainExecutor.execute(new Runnable() { // from class: com.android.systemui.media.SecMediaControlPanel$$ExternalSyntheticLambda15
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        ColorScheme colorScheme2;
                                        String str7;
                                        String str8;
                                        int i23;
                                        boolean z8;
                                        Drawable drawable4;
                                        String str9;
                                        int i24;
                                        Drawable drawable5;
                                        int i25;
                                        CoverMusicCapsuleController coverMusicCapsuleController2;
                                        Bitmap bitmap;
                                        SecMediaControlPanel secMediaControlPanel2 = secMediaControlPanel;
                                        String str10 = str4;
                                        int i26 = i21;
                                        String str11 = str5;
                                        int i27 = i22;
                                        boolean z9 = z7;
                                        boolean z10 = z6;
                                        MediaData mediaData3 = mediaData2;
                                        Drawable drawable6 = drawable2;
                                        int i28 = i18;
                                        Drawable drawable7 = colorDrawable;
                                        int i29 = i19;
                                        int i30 = i20;
                                        ColorScheme colorScheme3 = colorScheme;
                                        secMediaControlPanel2.getClass();
                                        Log.d("MediaControlPanel", "ColorUpdate bindArtworkAndColors update artwork key " + str10);
                                        if (i26 < secMediaControlPanel2.mArtworkBoundId) {
                                            Trace.endAsyncSection(str11, i27);
                                            return;
                                        }
                                        secMediaControlPanel2.mArtworkBoundId = i26;
                                        ImageView imageView4 = secMediaControlPanel2.mViewHolder.albumView;
                                        if (imageView4 == null) {
                                            imageView4 = null;
                                        }
                                        imageView4.setPadding(0, 0, 0, 0);
                                        if (secMediaControlPanel2.mIsWidthUpdated || z9 || (!secMediaControlPanel2.mIsArtworkBound && z10)) {
                                            if (!secMediaControlPanel2.mType.getSupportCapsule() || (coverMusicCapsuleController2 = secMediaControlPanel2.mCoverMusicCapsuleController) == null) {
                                                colorScheme2 = colorScheme3;
                                                str7 = str10;
                                                str8 = str11;
                                                i23 = i27;
                                                z8 = z10;
                                                drawable4 = drawable7;
                                                str9 = "MediaControlPanel";
                                                i24 = 2;
                                            } else {
                                                i24 = 2;
                                                String str12 = mediaData3.packageName;
                                                str8 = str11;
                                                PendingIntent pendingIntent3 = mediaData3.clickIntent;
                                                i23 = i27;
                                                MediaController mediaController3 = secMediaControlPanel2.mController;
                                                Bundle bundle = coverMusicCapsuleController2.bundle;
                                                str9 = "MediaControlPanel";
                                                bundle.putString("capsule_action_pkg", str12);
                                                bundle.putParcelable("capsule_action", pendingIntent3);
                                                if (drawable6 != null) {
                                                    RemoteViews remoteViews = coverMusicCapsuleController2.capsule;
                                                    BitmapDrawable bitmapDrawable = drawable6 instanceof BitmapDrawable ? (BitmapDrawable) drawable6 : null;
                                                    if (bitmapDrawable == null || (bitmap = bitmapDrawable.getBitmap()) == null) {
                                                        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(40, 40, Bitmap.Config.ARGB_8888);
                                                        bitmapCreateBitmap.getClass();
                                                        Canvas canvas = new Canvas(bitmapCreateBitmap);
                                                        str7 = str10;
                                                        drawable6.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                                                        drawable6.draw(canvas);
                                                        bitmap = bitmapCreateBitmap;
                                                    } else {
                                                        str7 = str10;
                                                    }
                                                    Bitmap bitmapCreateBitmap2 = Bitmap.createBitmap(40, 40, Bitmap.Config.ARGB_8888);
                                                    bitmapCreateBitmap2.getClass();
                                                    Bitmap bitmapCreateScaledBitmap = Bitmap.createScaledBitmap(bitmap, 40, 40, true);
                                                    colorScheme2 = colorScheme3;
                                                    Rect rect = new Rect(0, 0, bitmapCreateScaledBitmap.getWidth(), bitmapCreateScaledBitmap.getHeight());
                                                    Paint paint = new Paint();
                                                    paint.setAntiAlias(true);
                                                    paint.setColor(-12434878);
                                                    Canvas canvas2 = new Canvas(bitmapCreateBitmap2);
                                                    canvas2.drawARGB(0, 0, 0, 0);
                                                    z8 = z10;
                                                    drawable4 = drawable7;
                                                    canvas2.drawCircle(bitmapCreateScaledBitmap.getWidth() / 2, bitmapCreateScaledBitmap.getHeight() / 2, bitmapCreateScaledBitmap.getWidth() / 2, paint);
                                                    paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                                                    Unit unit = Unit.INSTANCE;
                                                    canvas2.drawBitmap(bitmapCreateScaledBitmap, rect, rect, paint);
                                                    remoteViews.setImageViewBitmap(R.id.sec_media_capsule_album_art, bitmapCreateBitmap2);
                                                } else {
                                                    colorScheme2 = colorScheme3;
                                                    str7 = str10;
                                                    z8 = z10;
                                                    drawable4 = drawable7;
                                                }
                                                Bundle bundle2 = coverMusicCapsuleController2.bundle;
                                                bundle2.putInt("bg_startColor", i28);
                                                float[] fArr = new float[3];
                                                Color.colorToHSV(i28, fArr);
                                                float f = fArr[0];
                                                fArr[0] = f < 150.0f ? f + 40.0f : f - 60.0f;
                                                bundle2.putInt("bg_endColor", Color.HSVToColor(fArr));
                                                coverMusicCapsuleController2.updateEqualizerState(mediaController3 != null ? mediaController3.getPlaybackState() : null);
                                            }
                                            if (secMediaControlPanel2.mType.getSupportOAChip() && secMediaControlPanel2.mOAMusicChipController != null) {
                                                CharSequence charSequence = mediaData3.song;
                                                String string = charSequence != null ? charSequence.toString() : "";
                                                OAMusicChipController oAMusicChipController2 = secMediaControlPanel2.mOAMusicChipController;
                                                Icon icon4 = mediaData3.appIcon;
                                                Integer numValueOf = Integer.valueOf(i28);
                                                PendingIntent pendingIntent4 = mediaData3.clickIntent;
                                                MediaController mediaController4 = secMediaControlPanel2.mController;
                                                oAMusicChipController2.appIcon = icon4;
                                                oAMusicChipController2.songTitle = string;
                                                oAMusicChipController2.bgColor = numValueOf;
                                                oAMusicChipController2.clickIntent = pendingIntent4;
                                                oAMusicChipController2.updatePlaybackState(mediaController4 != null ? mediaController4.getPlaybackState() : null);
                                            }
                                            if (secMediaControlPanel2.mPrevArtwork == null || secMediaControlPanel2.mType.getSupportCoverQuickPanelMedia()) {
                                                drawable5 = drawable4;
                                                imageView4.setImageDrawable(drawable5);
                                            } else {
                                                Drawable[] drawableArr = new Drawable[i24];
                                                drawableArr[0] = secMediaControlPanel2.mPrevArtwork;
                                                drawableArr[1] = drawable4;
                                                TransitionDrawable transitionDrawable = new TransitionDrawable(drawableArr);
                                                SecMediaControlPanel.scaleTransitionDrawableLayer(transitionDrawable, 0, i29, i30);
                                                SecMediaControlPanel.scaleTransitionDrawableLayer(transitionDrawable, 1, i29, i30);
                                                transitionDrawable.setLayerGravity(0, 17);
                                                transitionDrawable.setLayerGravity(1, 17);
                                                transitionDrawable.setCrossFadeEnabled(true);
                                                imageView4.setImageDrawable(transitionDrawable);
                                                transitionDrawable.startTransition(z8 ? 333 : 80);
                                                drawable5 = drawable4;
                                            }
                                            secMediaControlPanel2.mPrevArtwork = drawable5;
                                            secMediaControlPanel2.mIsArtworkBound = z8;
                                            i25 = 0;
                                            secMediaControlPanel2.mIsWidthUpdated = false;
                                        } else {
                                            colorScheme2 = colorScheme3;
                                            str7 = str10;
                                            str8 = str11;
                                            i23 = i27;
                                            str9 = "MediaControlPanel";
                                            i25 = 0;
                                        }
                                        if (!secMediaControlPanel2.mType.getSupportCoverQuickPanelMedia()) {
                                            ColorScheme colorScheme4 = colorScheme2;
                                            SecColorSchemeTransition secColorSchemeTransition = secMediaControlPanel2.mColorSchemeTransition;
                                            boolean z11 = secMediaControlPanel2.mIsArtworkBound;
                                            if (secColorSchemeTransition.DEBUG) {
                                                Log.d(secColorSchemeTransition.TAG, KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("ColorUpdate updateColorScheme enableGradient ", z11));
                                            }
                                            secColorSchemeTransition.isGradientEnabled = z11;
                                            SecAnimatingColorTransition[] secAnimatingColorTransitionArr = secColorSchemeTransition.colorTransitions;
                                            int length = secAnimatingColorTransitionArr.length;
                                            for (int i31 = i25; i31 < length; i31++) {
                                                SecAnimatingColorTransition secAnimatingColorTransition = secAnimatingColorTransitionArr[i31];
                                                int iIntValue2 = colorScheme4 == null ? secAnimatingColorTransition.defaultColor : ((Number) secAnimatingColorTransition.extractColor.mo781invoke(colorScheme4)).intValue();
                                                if (iIntValue2 != secAnimatingColorTransition.targetColor) {
                                                    secAnimatingColorTransition.sourceColor = secAnimatingColorTransition.currentColor;
                                                    secAnimatingColorTransition.targetColor = iIntValue2;
                                                    secAnimatingColorTransition.valueAnimator.cancel();
                                                    secAnimatingColorTransition.valueAnimator.start();
                                                }
                                            }
                                        } else if (colorScheme2 != null) {
                                            TonalPalette tonalPalette = colorScheme2.mAccent1;
                                            int s100 = tonalPalette.getS100();
                                            int i32 = (int) 127.5f;
                                            int iArgb = Color.argb(i32, Color.red(s100), Color.green(s100), Color.blue(s100));
                                            int s200 = tonalPalette.getS200();
                                            int[] iArr2 = {iArgb, Color.argb(i32, Color.red(s200), Color.green(s200), Color.blue(s200))};
                                            View view4 = secMediaControlPanel2.mViewHolder.playerView;
                                            (view4 != null ? view4 : null).setBackground(new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, iArr2));
                                        } else {
                                            SecColorSchemeTransition secColorSchemeTransition2 = secMediaControlPanel2.mColorSchemeTransition;
                                            if (secColorSchemeTransition2 != null) {
                                                View view5 = secMediaControlPanel2.mViewHolder.playerView;
                                                (view5 != null ? view5 : null).setBackgroundColor(secColorSchemeTransition2.bgColor);
                                            } else {
                                                View view6 = secMediaControlPanel2.mViewHolder.playerView;
                                                (view6 != null ? view6 : null).setBackgroundColor(-16777216);
                                            }
                                        }
                                        Log.d(str9, "ColorUpdate bindArtworkAndColors update artwork end key " + str7);
                                        Trace.endAsyncSection(str8, i23);
                                    }
                                });
                            }
                        } catch (PackageManager.NameNotFoundException e2) {
                            e = e2;
                        }
                    } else {
                        Log.d("MediaControlPanel", "ColorUpdate bindArtworkAndColors add artwork key " + str4);
                        Bitmap bitmap = icon3.getBitmap();
                        bitmap.setDensity(secMediaControlPanel.mContext.getResources().getDisplayMetrics().densityDpi);
                        ColorScheme colorScheme2 = new ColorScheme(WallpaperColors.fromBitmap(bitmap), true, 6);
                        if (secMediaControlPanel.mType.getSupportCoverQuickPanelMedia()) {
                            Context context7 = secMediaControlPanel.mContext;
                            Context context8 = secMediaControlPanel.mCoverContext;
                            if (context8 != null) {
                                context7 = context8;
                            }
                            drawableLoadDrawable = icon3.loadDrawable(context7);
                        } else {
                            drawableLoadDrawable = icon3.loadDrawable(secMediaControlPanel.mContext);
                            int intrinsicHeight = drawableLoadDrawable.getIntrinsicHeight();
                            int intrinsicWidth = drawableLoadDrawable.getIntrinsicWidth();
                            if (intrinsicHeight <= i19 && intrinsicWidth <= i19) {
                                Bitmap bitmap2 = ((BitmapDrawable) drawableLoadDrawable).getBitmap();
                                int height = bitmap2.getHeight();
                                int width = bitmap2.getWidth();
                                drawableLoadDrawable = new BitmapDrawable(secMediaControlPanel.mContext.getResources(), Bitmap.createScaledBitmap(height > width ? Bitmap.createBitmap(bitmap2, 0, (height - width) / 2, width, width) : Bitmap.createBitmap(bitmap2, (width - height) / 2, 0, height, height), i19, i20, true));
                            }
                        }
                        if (secMediaControlPanel.mIsArtworkBound && !bitmap.sameAs(secMediaControlPanel.mPrevBitmap)) {
                            secMediaControlPanel.mIsArtworkBound = false;
                        }
                        secMediaControlPanel.mPrevBitmap = bitmap;
                        drawable2 = drawableLoadDrawable;
                        z6 = true;
                        colorScheme = colorScheme2;
                        colorDrawable = drawable2;
                    }
                    if (colorScheme == null) {
                        s700 = colorScheme.mAccent1.getS700();
                    } else {
                        SecColorSchemeTransition secColorSchemeTransition = secMediaControlPanel.mColorSchemeTransition;
                        if (secColorSchemeTransition == null) {
                            i18 = 0;
                            secMediaControlPanel.mMainExecutor.execute(new Runnable() { // from class: com.android.systemui.media.SecMediaControlPanel$$ExternalSyntheticLambda15
                                @Override // java.lang.Runnable
                                public final void run() {
                                    ColorScheme colorScheme22;
                                    String str7;
                                    String str8;
                                    int i23;
                                    boolean z8;
                                    Drawable drawable4;
                                    String str9;
                                    int i24;
                                    Drawable drawable5;
                                    int i25;
                                    CoverMusicCapsuleController coverMusicCapsuleController2;
                                    Bitmap bitmap3;
                                    SecMediaControlPanel secMediaControlPanel2 = secMediaControlPanel;
                                    String str10 = str4;
                                    int i26 = i21;
                                    String str11 = str5;
                                    int i27 = i22;
                                    boolean z9 = z7;
                                    boolean z10 = z6;
                                    MediaData mediaData3 = mediaData2;
                                    Drawable drawable6 = drawable2;
                                    int i28 = i18;
                                    Drawable drawable7 = colorDrawable;
                                    int i29 = i19;
                                    int i30 = i20;
                                    ColorScheme colorScheme3 = colorScheme;
                                    secMediaControlPanel2.getClass();
                                    Log.d("MediaControlPanel", "ColorUpdate bindArtworkAndColors update artwork key " + str10);
                                    if (i26 < secMediaControlPanel2.mArtworkBoundId) {
                                        Trace.endAsyncSection(str11, i27);
                                        return;
                                    }
                                    secMediaControlPanel2.mArtworkBoundId = i26;
                                    ImageView imageView4 = secMediaControlPanel2.mViewHolder.albumView;
                                    if (imageView4 == null) {
                                        imageView4 = null;
                                    }
                                    imageView4.setPadding(0, 0, 0, 0);
                                    if (secMediaControlPanel2.mIsWidthUpdated || z9 || (!secMediaControlPanel2.mIsArtworkBound && z10)) {
                                        if (!secMediaControlPanel2.mType.getSupportCapsule() || (coverMusicCapsuleController2 = secMediaControlPanel2.mCoverMusicCapsuleController) == null) {
                                            colorScheme22 = colorScheme3;
                                            str7 = str10;
                                            str8 = str11;
                                            i23 = i27;
                                            z8 = z10;
                                            drawable4 = drawable7;
                                            str9 = "MediaControlPanel";
                                            i24 = 2;
                                        } else {
                                            i24 = 2;
                                            String str12 = mediaData3.packageName;
                                            str8 = str11;
                                            PendingIntent pendingIntent3 = mediaData3.clickIntent;
                                            i23 = i27;
                                            MediaController mediaController3 = secMediaControlPanel2.mController;
                                            Bundle bundle = coverMusicCapsuleController2.bundle;
                                            str9 = "MediaControlPanel";
                                            bundle.putString("capsule_action_pkg", str12);
                                            bundle.putParcelable("capsule_action", pendingIntent3);
                                            if (drawable6 != null) {
                                                RemoteViews remoteViews = coverMusicCapsuleController2.capsule;
                                                BitmapDrawable bitmapDrawable = drawable6 instanceof BitmapDrawable ? (BitmapDrawable) drawable6 : null;
                                                if (bitmapDrawable == null || (bitmap3 = bitmapDrawable.getBitmap()) == null) {
                                                    Bitmap bitmapCreateBitmap = Bitmap.createBitmap(40, 40, Bitmap.Config.ARGB_8888);
                                                    bitmapCreateBitmap.getClass();
                                                    Canvas canvas = new Canvas(bitmapCreateBitmap);
                                                    str7 = str10;
                                                    drawable6.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                                                    drawable6.draw(canvas);
                                                    bitmap3 = bitmapCreateBitmap;
                                                } else {
                                                    str7 = str10;
                                                }
                                                Bitmap bitmapCreateBitmap2 = Bitmap.createBitmap(40, 40, Bitmap.Config.ARGB_8888);
                                                bitmapCreateBitmap2.getClass();
                                                Bitmap bitmapCreateScaledBitmap = Bitmap.createScaledBitmap(bitmap3, 40, 40, true);
                                                colorScheme22 = colorScheme3;
                                                Rect rect = new Rect(0, 0, bitmapCreateScaledBitmap.getWidth(), bitmapCreateScaledBitmap.getHeight());
                                                Paint paint = new Paint();
                                                paint.setAntiAlias(true);
                                                paint.setColor(-12434878);
                                                Canvas canvas2 = new Canvas(bitmapCreateBitmap2);
                                                canvas2.drawARGB(0, 0, 0, 0);
                                                z8 = z10;
                                                drawable4 = drawable7;
                                                canvas2.drawCircle(bitmapCreateScaledBitmap.getWidth() / 2, bitmapCreateScaledBitmap.getHeight() / 2, bitmapCreateScaledBitmap.getWidth() / 2, paint);
                                                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                                                Unit unit = Unit.INSTANCE;
                                                canvas2.drawBitmap(bitmapCreateScaledBitmap, rect, rect, paint);
                                                remoteViews.setImageViewBitmap(R.id.sec_media_capsule_album_art, bitmapCreateBitmap2);
                                            } else {
                                                colorScheme22 = colorScheme3;
                                                str7 = str10;
                                                z8 = z10;
                                                drawable4 = drawable7;
                                            }
                                            Bundle bundle2 = coverMusicCapsuleController2.bundle;
                                            bundle2.putInt("bg_startColor", i28);
                                            float[] fArr = new float[3];
                                            Color.colorToHSV(i28, fArr);
                                            float f = fArr[0];
                                            fArr[0] = f < 150.0f ? f + 40.0f : f - 60.0f;
                                            bundle2.putInt("bg_endColor", Color.HSVToColor(fArr));
                                            coverMusicCapsuleController2.updateEqualizerState(mediaController3 != null ? mediaController3.getPlaybackState() : null);
                                        }
                                        if (secMediaControlPanel2.mType.getSupportOAChip() && secMediaControlPanel2.mOAMusicChipController != null) {
                                            CharSequence charSequence = mediaData3.song;
                                            String string = charSequence != null ? charSequence.toString() : "";
                                            OAMusicChipController oAMusicChipController2 = secMediaControlPanel2.mOAMusicChipController;
                                            Icon icon4 = mediaData3.appIcon;
                                            Integer numValueOf = Integer.valueOf(i28);
                                            PendingIntent pendingIntent4 = mediaData3.clickIntent;
                                            MediaController mediaController4 = secMediaControlPanel2.mController;
                                            oAMusicChipController2.appIcon = icon4;
                                            oAMusicChipController2.songTitle = string;
                                            oAMusicChipController2.bgColor = numValueOf;
                                            oAMusicChipController2.clickIntent = pendingIntent4;
                                            oAMusicChipController2.updatePlaybackState(mediaController4 != null ? mediaController4.getPlaybackState() : null);
                                        }
                                        if (secMediaControlPanel2.mPrevArtwork == null || secMediaControlPanel2.mType.getSupportCoverQuickPanelMedia()) {
                                            drawable5 = drawable4;
                                            imageView4.setImageDrawable(drawable5);
                                        } else {
                                            Drawable[] drawableArr = new Drawable[i24];
                                            drawableArr[0] = secMediaControlPanel2.mPrevArtwork;
                                            drawableArr[1] = drawable4;
                                            TransitionDrawable transitionDrawable = new TransitionDrawable(drawableArr);
                                            SecMediaControlPanel.scaleTransitionDrawableLayer(transitionDrawable, 0, i29, i30);
                                            SecMediaControlPanel.scaleTransitionDrawableLayer(transitionDrawable, 1, i29, i30);
                                            transitionDrawable.setLayerGravity(0, 17);
                                            transitionDrawable.setLayerGravity(1, 17);
                                            transitionDrawable.setCrossFadeEnabled(true);
                                            imageView4.setImageDrawable(transitionDrawable);
                                            transitionDrawable.startTransition(z8 ? 333 : 80);
                                            drawable5 = drawable4;
                                        }
                                        secMediaControlPanel2.mPrevArtwork = drawable5;
                                        secMediaControlPanel2.mIsArtworkBound = z8;
                                        i25 = 0;
                                        secMediaControlPanel2.mIsWidthUpdated = false;
                                    } else {
                                        colorScheme22 = colorScheme3;
                                        str7 = str10;
                                        str8 = str11;
                                        i23 = i27;
                                        str9 = "MediaControlPanel";
                                        i25 = 0;
                                    }
                                    if (!secMediaControlPanel2.mType.getSupportCoverQuickPanelMedia()) {
                                        ColorScheme colorScheme4 = colorScheme22;
                                        SecColorSchemeTransition secColorSchemeTransition2 = secMediaControlPanel2.mColorSchemeTransition;
                                        boolean z11 = secMediaControlPanel2.mIsArtworkBound;
                                        if (secColorSchemeTransition2.DEBUG) {
                                            Log.d(secColorSchemeTransition2.TAG, KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("ColorUpdate updateColorScheme enableGradient ", z11));
                                        }
                                        secColorSchemeTransition2.isGradientEnabled = z11;
                                        SecAnimatingColorTransition[] secAnimatingColorTransitionArr = secColorSchemeTransition2.colorTransitions;
                                        int length = secAnimatingColorTransitionArr.length;
                                        for (int i31 = i25; i31 < length; i31++) {
                                            SecAnimatingColorTransition secAnimatingColorTransition = secAnimatingColorTransitionArr[i31];
                                            int iIntValue2 = colorScheme4 == null ? secAnimatingColorTransition.defaultColor : ((Number) secAnimatingColorTransition.extractColor.mo781invoke(colorScheme4)).intValue();
                                            if (iIntValue2 != secAnimatingColorTransition.targetColor) {
                                                secAnimatingColorTransition.sourceColor = secAnimatingColorTransition.currentColor;
                                                secAnimatingColorTransition.targetColor = iIntValue2;
                                                secAnimatingColorTransition.valueAnimator.cancel();
                                                secAnimatingColorTransition.valueAnimator.start();
                                            }
                                        }
                                    } else if (colorScheme22 != null) {
                                        TonalPalette tonalPalette = colorScheme22.mAccent1;
                                        int s100 = tonalPalette.getS100();
                                        int i32 = (int) 127.5f;
                                        int iArgb = Color.argb(i32, Color.red(s100), Color.green(s100), Color.blue(s100));
                                        int s200 = tonalPalette.getS200();
                                        int[] iArr2 = {iArgb, Color.argb(i32, Color.red(s200), Color.green(s200), Color.blue(s200))};
                                        View view4 = secMediaControlPanel2.mViewHolder.playerView;
                                        (view4 != null ? view4 : null).setBackground(new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, iArr2));
                                    } else {
                                        SecColorSchemeTransition secColorSchemeTransition22 = secMediaControlPanel2.mColorSchemeTransition;
                                        if (secColorSchemeTransition22 != null) {
                                            View view5 = secMediaControlPanel2.mViewHolder.playerView;
                                            (view5 != null ? view5 : null).setBackgroundColor(secColorSchemeTransition22.bgColor);
                                        } else {
                                            View view6 = secMediaControlPanel2.mViewHolder.playerView;
                                            (view6 != null ? view6 : null).setBackgroundColor(-16777216);
                                        }
                                    }
                                    Log.d(str9, "ColorUpdate bindArtworkAndColors update artwork end key " + str7);
                                    Trace.endAsyncSection(str8, i23);
                                }
                            });
                        }
                        s700 = secColorSchemeTransition.bgColor;
                    }
                    i18 = s700;
                    secMediaControlPanel.mMainExecutor.execute(new Runnable() { // from class: com.android.systemui.media.SecMediaControlPanel$$ExternalSyntheticLambda15
                        @Override // java.lang.Runnable
                        public final void run() {
                            ColorScheme colorScheme22;
                            String str7;
                            String str8;
                            int i23;
                            boolean z8;
                            Drawable drawable4;
                            String str9;
                            int i24;
                            Drawable drawable5;
                            int i25;
                            CoverMusicCapsuleController coverMusicCapsuleController2;
                            Bitmap bitmap3;
                            SecMediaControlPanel secMediaControlPanel2 = secMediaControlPanel;
                            String str10 = str4;
                            int i26 = i21;
                            String str11 = str5;
                            int i27 = i22;
                            boolean z9 = z7;
                            boolean z10 = z6;
                            MediaData mediaData3 = mediaData2;
                            Drawable drawable6 = drawable2;
                            int i28 = i18;
                            Drawable drawable7 = colorDrawable;
                            int i29 = i19;
                            int i30 = i20;
                            ColorScheme colorScheme3 = colorScheme;
                            secMediaControlPanel2.getClass();
                            Log.d("MediaControlPanel", "ColorUpdate bindArtworkAndColors update artwork key " + str10);
                            if (i26 < secMediaControlPanel2.mArtworkBoundId) {
                                Trace.endAsyncSection(str11, i27);
                                return;
                            }
                            secMediaControlPanel2.mArtworkBoundId = i26;
                            ImageView imageView4 = secMediaControlPanel2.mViewHolder.albumView;
                            if (imageView4 == null) {
                                imageView4 = null;
                            }
                            imageView4.setPadding(0, 0, 0, 0);
                            if (secMediaControlPanel2.mIsWidthUpdated || z9 || (!secMediaControlPanel2.mIsArtworkBound && z10)) {
                                if (!secMediaControlPanel2.mType.getSupportCapsule() || (coverMusicCapsuleController2 = secMediaControlPanel2.mCoverMusicCapsuleController) == null) {
                                    colorScheme22 = colorScheme3;
                                    str7 = str10;
                                    str8 = str11;
                                    i23 = i27;
                                    z8 = z10;
                                    drawable4 = drawable7;
                                    str9 = "MediaControlPanel";
                                    i24 = 2;
                                } else {
                                    i24 = 2;
                                    String str12 = mediaData3.packageName;
                                    str8 = str11;
                                    PendingIntent pendingIntent3 = mediaData3.clickIntent;
                                    i23 = i27;
                                    MediaController mediaController3 = secMediaControlPanel2.mController;
                                    Bundle bundle = coverMusicCapsuleController2.bundle;
                                    str9 = "MediaControlPanel";
                                    bundle.putString("capsule_action_pkg", str12);
                                    bundle.putParcelable("capsule_action", pendingIntent3);
                                    if (drawable6 != null) {
                                        RemoteViews remoteViews = coverMusicCapsuleController2.capsule;
                                        BitmapDrawable bitmapDrawable = drawable6 instanceof BitmapDrawable ? (BitmapDrawable) drawable6 : null;
                                        if (bitmapDrawable == null || (bitmap3 = bitmapDrawable.getBitmap()) == null) {
                                            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(40, 40, Bitmap.Config.ARGB_8888);
                                            bitmapCreateBitmap.getClass();
                                            Canvas canvas = new Canvas(bitmapCreateBitmap);
                                            str7 = str10;
                                            drawable6.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                                            drawable6.draw(canvas);
                                            bitmap3 = bitmapCreateBitmap;
                                        } else {
                                            str7 = str10;
                                        }
                                        Bitmap bitmapCreateBitmap2 = Bitmap.createBitmap(40, 40, Bitmap.Config.ARGB_8888);
                                        bitmapCreateBitmap2.getClass();
                                        Bitmap bitmapCreateScaledBitmap = Bitmap.createScaledBitmap(bitmap3, 40, 40, true);
                                        colorScheme22 = colorScheme3;
                                        Rect rect = new Rect(0, 0, bitmapCreateScaledBitmap.getWidth(), bitmapCreateScaledBitmap.getHeight());
                                        Paint paint = new Paint();
                                        paint.setAntiAlias(true);
                                        paint.setColor(-12434878);
                                        Canvas canvas2 = new Canvas(bitmapCreateBitmap2);
                                        canvas2.drawARGB(0, 0, 0, 0);
                                        z8 = z10;
                                        drawable4 = drawable7;
                                        canvas2.drawCircle(bitmapCreateScaledBitmap.getWidth() / 2, bitmapCreateScaledBitmap.getHeight() / 2, bitmapCreateScaledBitmap.getWidth() / 2, paint);
                                        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                                        Unit unit = Unit.INSTANCE;
                                        canvas2.drawBitmap(bitmapCreateScaledBitmap, rect, rect, paint);
                                        remoteViews.setImageViewBitmap(R.id.sec_media_capsule_album_art, bitmapCreateBitmap2);
                                    } else {
                                        colorScheme22 = colorScheme3;
                                        str7 = str10;
                                        z8 = z10;
                                        drawable4 = drawable7;
                                    }
                                    Bundle bundle2 = coverMusicCapsuleController2.bundle;
                                    bundle2.putInt("bg_startColor", i28);
                                    float[] fArr = new float[3];
                                    Color.colorToHSV(i28, fArr);
                                    float f = fArr[0];
                                    fArr[0] = f < 150.0f ? f + 40.0f : f - 60.0f;
                                    bundle2.putInt("bg_endColor", Color.HSVToColor(fArr));
                                    coverMusicCapsuleController2.updateEqualizerState(mediaController3 != null ? mediaController3.getPlaybackState() : null);
                                }
                                if (secMediaControlPanel2.mType.getSupportOAChip() && secMediaControlPanel2.mOAMusicChipController != null) {
                                    CharSequence charSequence = mediaData3.song;
                                    String string = charSequence != null ? charSequence.toString() : "";
                                    OAMusicChipController oAMusicChipController2 = secMediaControlPanel2.mOAMusicChipController;
                                    Icon icon4 = mediaData3.appIcon;
                                    Integer numValueOf = Integer.valueOf(i28);
                                    PendingIntent pendingIntent4 = mediaData3.clickIntent;
                                    MediaController mediaController4 = secMediaControlPanel2.mController;
                                    oAMusicChipController2.appIcon = icon4;
                                    oAMusicChipController2.songTitle = string;
                                    oAMusicChipController2.bgColor = numValueOf;
                                    oAMusicChipController2.clickIntent = pendingIntent4;
                                    oAMusicChipController2.updatePlaybackState(mediaController4 != null ? mediaController4.getPlaybackState() : null);
                                }
                                if (secMediaControlPanel2.mPrevArtwork == null || secMediaControlPanel2.mType.getSupportCoverQuickPanelMedia()) {
                                    drawable5 = drawable4;
                                    imageView4.setImageDrawable(drawable5);
                                } else {
                                    Drawable[] drawableArr = new Drawable[i24];
                                    drawableArr[0] = secMediaControlPanel2.mPrevArtwork;
                                    drawableArr[1] = drawable4;
                                    TransitionDrawable transitionDrawable = new TransitionDrawable(drawableArr);
                                    SecMediaControlPanel.scaleTransitionDrawableLayer(transitionDrawable, 0, i29, i30);
                                    SecMediaControlPanel.scaleTransitionDrawableLayer(transitionDrawable, 1, i29, i30);
                                    transitionDrawable.setLayerGravity(0, 17);
                                    transitionDrawable.setLayerGravity(1, 17);
                                    transitionDrawable.setCrossFadeEnabled(true);
                                    imageView4.setImageDrawable(transitionDrawable);
                                    transitionDrawable.startTransition(z8 ? 333 : 80);
                                    drawable5 = drawable4;
                                }
                                secMediaControlPanel2.mPrevArtwork = drawable5;
                                secMediaControlPanel2.mIsArtworkBound = z8;
                                i25 = 0;
                                secMediaControlPanel2.mIsWidthUpdated = false;
                            } else {
                                colorScheme22 = colorScheme3;
                                str7 = str10;
                                str8 = str11;
                                i23 = i27;
                                str9 = "MediaControlPanel";
                                i25 = 0;
                            }
                            if (!secMediaControlPanel2.mType.getSupportCoverQuickPanelMedia()) {
                                ColorScheme colorScheme4 = colorScheme22;
                                SecColorSchemeTransition secColorSchemeTransition2 = secMediaControlPanel2.mColorSchemeTransition;
                                boolean z11 = secMediaControlPanel2.mIsArtworkBound;
                                if (secColorSchemeTransition2.DEBUG) {
                                    Log.d(secColorSchemeTransition2.TAG, KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("ColorUpdate updateColorScheme enableGradient ", z11));
                                }
                                secColorSchemeTransition2.isGradientEnabled = z11;
                                SecAnimatingColorTransition[] secAnimatingColorTransitionArr = secColorSchemeTransition2.colorTransitions;
                                int length = secAnimatingColorTransitionArr.length;
                                for (int i31 = i25; i31 < length; i31++) {
                                    SecAnimatingColorTransition secAnimatingColorTransition = secAnimatingColorTransitionArr[i31];
                                    int iIntValue2 = colorScheme4 == null ? secAnimatingColorTransition.defaultColor : ((Number) secAnimatingColorTransition.extractColor.mo781invoke(colorScheme4)).intValue();
                                    if (iIntValue2 != secAnimatingColorTransition.targetColor) {
                                        secAnimatingColorTransition.sourceColor = secAnimatingColorTransition.currentColor;
                                        secAnimatingColorTransition.targetColor = iIntValue2;
                                        secAnimatingColorTransition.valueAnimator.cancel();
                                        secAnimatingColorTransition.valueAnimator.start();
                                    }
                                }
                            } else if (colorScheme22 != null) {
                                TonalPalette tonalPalette = colorScheme22.mAccent1;
                                int s100 = tonalPalette.getS100();
                                int i32 = (int) 127.5f;
                                int iArgb = Color.argb(i32, Color.red(s100), Color.green(s100), Color.blue(s100));
                                int s200 = tonalPalette.getS200();
                                int[] iArr2 = {iArgb, Color.argb(i32, Color.red(s200), Color.green(s200), Color.blue(s200))};
                                View view4 = secMediaControlPanel2.mViewHolder.playerView;
                                (view4 != null ? view4 : null).setBackground(new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, iArr2));
                            } else {
                                SecColorSchemeTransition secColorSchemeTransition22 = secMediaControlPanel2.mColorSchemeTransition;
                                if (secColorSchemeTransition22 != null) {
                                    View view5 = secMediaControlPanel2.mViewHolder.playerView;
                                    (view5 != null ? view5 : null).setBackgroundColor(secColorSchemeTransition22.bgColor);
                                } else {
                                    View view6 = secMediaControlPanel2.mViewHolder.playerView;
                                    (view6 != null ? view6 : null).setBackgroundColor(-16777216);
                                }
                            }
                            Log.d(str9, "ColorUpdate bindArtworkAndColors update artwork end key " + str7);
                            Trace.endAsyncSection(str8, i23);
                        }
                    });
                }
            });
        }
        if (this.mType.getSupportCoverQuickPanelMedia()) {
            LinearLayout linearLayout4 = this.mViewHolder.header;
            (linearLayout4 != null ? linearLayout4 : null).setVisibility(8);
        }
        Trace.endSection();
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
                secSeekBarViewModel.setController(null);
                SecSeekBarViewModel secSeekBarViewModel2 = secSeekBarViewModel;
                secSeekBarViewModel2.playbackState = null;
                SecSeekBarViewModel.AnonymousClass1 anonymousClass1 = secSeekBarViewModel2.cancel;
                if (anonymousClass1 != null) {
                    anonymousClass1.run();
                }
                secSeekBarViewModel.cancel = null;
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

    public final void updateBudsButton() {
        ImageButton imageButton;
        List activeDevices;
        String address;
        CachedBluetoothDevice cachedBluetoothDeviceFindDevice;
        if (!this.mType.getSupportBudsButton() || this.mBudsButtonExpanded == null) {
            return;
        }
        this.mBudsEnabled = this.mSettingsHelper.getBudsEnable();
        ActionBarContextView$$ExternalSyntheticOutline0.m(new StringBuilder("onChanged(): buds enabled: "), this.mBudsEnabled, "MediaControlPanel");
        if (!this.mBudsEnabled) {
            this.mBudsDetailCloseRunnable.run();
        }
        SecMediaHost.AnonymousClass1 anonymousClass1 = this.mQSMediaPlayerBarCallback;
        Drawable drawable = null;
        if (anonymousClass1 != null) {
            String str = this.mLastBluetoothDeviceAddress;
            MediaBluetoothHelper mediaBluetoothHelper = SecMediaHost.this.mMediaBluetoothHelper;
            kotlin.Pair pair = mediaBluetoothHelper.lastBudsDrawable;
            if (pair == null || !Intrinsics.areEqual(str, pair.getFirst())) {
                try {
                    SystemServiceExtension systemServiceExtension = SystemServiceExtension.INSTANCE;
                    Context context = mediaBluetoothHelper.context;
                    systemServiceExtension.getClass();
                    Object systemService = context.getSystemService((Class<Object>) BluetoothManager.class);
                    systemService.getClass();
                    activeDevices = ((BluetoothManager) systemService).getAdapter().getActiveDevices(2);
                } catch (Exception e) {
                    KeyguardSecSimPinViewController$$ExternalSyntheticOutline0.m("Fail to get connectedDevices ", e, "MediaBluetoothHelper");
                    activeDevices = EmptyList.INSTANCE;
                }
                BluetoothDevice bluetoothDevice = (BluetoothDevice) CollectionsKt___CollectionsKt.firstOrNull(activeDevices);
                if (bluetoothDevice != null) {
                    address = bluetoothDevice.getAddress();
                    try {
                        cachedBluetoothDeviceFindDevice = LocalBluetoothManager.getInstance(mediaBluetoothHelper.context, BluetoothUtils.mOnInitCallback).mCachedDeviceManager.findDevice(bluetoothDevice);
                    } catch (Exception e2) {
                        KeyguardSecSimPinViewController$$ExternalSyntheticOutline0.m("Fail to getIconDrawableForSolid ", e2, "MediaBluetoothHelper");
                    }
                    Drawable iconDrawableForSolid = cachedBluetoothDeviceFindDevice != null ? cachedBluetoothDeviceFindDevice.getIconDrawableForSolid() : null;
                    if (iconDrawableForSolid != null) {
                        drawable = iconDrawableForSolid;
                    } else if (MediaBluetoothHelper.semIconIndex(bluetoothDevice) == 5381) {
                        drawable = mediaBluetoothHelper.context.getDrawable(R.drawable.ic_buds3_left);
                    } else {
                        List listAsList = Arrays.asList(5379, 5380);
                        if (!(listAsList instanceof Collection) || !listAsList.isEmpty()) {
                            Iterator it = listAsList.iterator();
                            while (true) {
                                if (!it.hasNext()) {
                                    break;
                                } else if (((Number) it.next()).shortValue() == MediaBluetoothHelper.semIconIndex(bluetoothDevice)) {
                                    drawable = mediaBluetoothHelper.context.getDrawable(R.drawable.ic_buds2_left);
                                    break;
                                }
                            }
                        }
                    }
                } else {
                    address = "";
                }
                if (drawable != null && address.length() > 0) {
                    mediaBluetoothHelper.lastBudsDrawable = new kotlin.Pair(address, drawable);
                }
            } else {
                drawable = (Drawable) pair.getSecond();
            }
        }
        if (this.mType.getSupportCoverQuickPanelMedia()) {
            this.mBudsButtonExpanded.setVisibility(8);
        } else {
            boolean z = drawable != null && this.mBudsEnabled;
            ((View) this.mBudsButtonExpanded.getParent()).setVisibility(z ? 0 : 8);
            this.mBudsButtonExpanded.setVisibility(z ? 0 : 8);
        }
        if (!this.mType.getSupportBudsButton() || drawable == null || (imageButton = this.mBudsButtonExpanded) == null) {
            return;
        }
        imageButton.setImageDrawable(drawable);
        this.mBudsButtonExpanded.setEnabled(this.mBudsEnabled);
    }

    public final void updateDeviceName() throws Resources.NotFoundException {
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

    public final void updateFontSize(TextView textView, int i) throws Resources.NotFoundException {
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
            ColorStateList colorStateListValueOf = ColorStateList.valueOf(color);
            SeekBar seekBar = secPlayerViewHolder.seekBar;
            if (seekBar == null) {
                seekBar = null;
            }
            seekBar.setThumbTintList(colorStateListValueOf);
            seekBar.setProgressTintList(colorStateListValueOf);
            seekBar.setProgressBackgroundTintList(colorStateListValueOf.withAlpha(76));
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

    public final void updateWidth() throws Resources.NotFoundException {
        int dimensionPixelSize;
        int i = this.mWidth;
        MediaType mediaType = this.mType;
        if (mediaType == MediaType.OA) {
            OngoingActivityLayoutUtil ongoingActivityLayoutUtil = OngoingActivityLayoutUtil.INSTANCE;
            Context context = this.mContext;
            ongoingActivityLayoutUtil.getClass();
            dimensionPixelSize = OngoingActivityLayoutUtil.getOngoingCardWidth(context);
        } else {
            MediaType mediaType2 = MediaType.ENR;
            SecQSPanelResourcePicker secQSPanelResourcePicker = this.mResourcePicker;
            if (mediaType == mediaType2) {
                dimensionPixelSize = secQSPanelResourcePicker.getPanelWidth(this.mContext) - (secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getNotificationSidePadding(this.mContext, true) * 2);
            } else if (mediaType == MediaType.QS) {
                dimensionPixelSize = secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getAlbumArtWidth(this.mContext);
            } else if (mediaType == MediaType.COVER) {
                dimensionPixelSize = 748;
            } else if (mediaType == MediaType.COVER_QS) {
                Context context2 = this.mContext;
                Context context3 = this.mCoverContext;
                if (context3 != null) {
                    context2 = context3;
                }
                dimensionPixelSize = context2.getResources().getDimensionPixelSize(R.dimen.subscreen_qs_media_panel_width);
            } else {
                dimensionPixelSize = 0;
            }
        }
        this.mWidth = dimensionPixelSize;
        if (i != dimensionPixelSize) {
            this.mIsWidthUpdated = true;
        }
    }
}
