package com.android.systemui.media;

import android.content.Context;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.media.session.MediaController;
import android.os.Debug;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import androidx.core.graphics.ColorUtils;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.log.MediaLogger;
import com.android.systemui.log.MediaLoggerImpl;
import com.android.systemui.media.SecSeekBarViewModel;
import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.media.controls.shared.model.MediaData;
import com.android.systemui.media.controls.shared.model.SmartspaceMediaData;
import com.android.systemui.media.controls.ui.SecColorSchemeTransition;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.SecPageIndicator;
import com.android.systemui.qs.bar.BarController;
import com.android.systemui.qs.bar.ColoredBGHelper;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.subscreen.SubScreenManager;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.Utils;
import com.samsung.android.media.SemSoundAssistantManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.stream.IntStream;
import javax.inject.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class SecMediaHost implements StatusBarStateController.StateListener, ConfigurationController.ConfigurationListener {
    public int mBarState;
    public final ColoredBGHelper mBgColorHelper;
    public Runnable mBudsDetailCloseRunnable;
    public Runnable mBudsDetailOpenRunnable;
    public CarouselHelper mCarouselHelper;
    public final Context mContext;
    public MediaDataFormat mCurrentMediaData;
    public int mIsRTL;
    public boolean mLocalListening;
    public final MediaLogger mLogger;
    public BarController.AnonymousClass4 mMediaBarCallback;
    public final MediaBluetoothHelper mMediaBluetoothHelper;
    public final Provider mMediaControlPanelProvider;
    public final AnonymousClass2 mMediaDataListener;
    public final MediaDataManager mMediaDataManager;
    public final HashMap mMediaFrames = new HashMap();
    public final HashMap mMediaPlayerData;
    public final Provider mMediaPlayerDataProvider;
    public int mOrientation;
    public int mPagerMargin;
    public PlayLastSongHelper mPlayLastSongHelper;
    public boolean mPlayerNeedForceUpdate;
    public final StatusBarStateController mStatusBarStateController;
    public final SubScreenManager mSubScreenManager;
    public final ViewPagerHelper mViewPagerHelper;
    public final ArrayList mVisibilityListeners;
    public final WakefulnessLifecycle mWakefulnessLifeCycle;
    public CoverMusicWidgetController mWidgetController;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.media.SecMediaHost$1, reason: invalid class name */
    public final class AnonymousClass1 {
        public final /* synthetic */ SecMediaPlayerData val$playerData;

        public AnonymousClass1(SecMediaPlayerData secMediaPlayerData) {
            this.val$playerData = secMediaPlayerData;
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface MediaPanelVisibilityListener {
        void onMediaVisibilityChanged(boolean z);
    }

    public SecMediaHost(Context context, MediaDataManager mediaDataManager, ConfigurationController configurationController, Provider provider, Provider provider2, StatusBarStateController statusBarStateController, MediaBluetoothHelper mediaBluetoothHelper, MediaLogger mediaLogger, SubScreenManager subScreenManager, WakefulnessLifecycle wakefulnessLifecycle, ColoredBGHelper coloredBGHelper) {
        HashMap hashMap = new HashMap();
        this.mMediaPlayerData = hashMap;
        this.mVisibilityListeners = new ArrayList();
        this.mPlayerNeedForceUpdate = false;
        AnonymousClass2 anonymousClass2 = new AnonymousClass2();
        this.mMediaDataListener = anonymousClass2;
        this.mContext = context;
        this.mLogger = mediaLogger;
        this.mMediaBluetoothHelper = mediaBluetoothHelper;
        this.mStatusBarStateController = statusBarStateController;
        statusBarStateController.addCallback(this);
        this.mMediaPlayerDataProvider = provider2;
        this.mSubScreenManager = subScreenManager;
        this.mWakefulnessLifeCycle = wakefulnessLifecycle;
        this.mViewPagerHelper = new ViewPagerHelper(new SecMediaHost$$ExternalSyntheticLambda1(this), new SecMediaHost$$ExternalSyntheticLambda2(this, 0), new SecMediaHost$$ExternalSyntheticLambda3(this, 0), new SecMediaHost$$ExternalSyntheticLambda4(hashMap));
        ((ConfigurationControllerImpl) configurationController).addCallback(this);
        this.mMediaControlPanelProvider = provider;
        this.mMediaDataManager = mediaDataManager;
        mediaDataManager.addListener(anonymousClass2);
        this.mBgColorHelper = coloredBGHelper;
        updateResources$2();
    }

    public static void iteratePlayers(SecMediaPlayerData secMediaPlayerData, Consumer consumer) {
        if (secMediaPlayerData == null) {
            return;
        }
        secMediaPlayerData.getMediaPlayers().values().forEach(consumer);
    }

    public final void addMediaFrame(MediaType mediaType, final View view) {
        ViewGroup viewGroup = (ViewGroup) view;
        LayoutInflater.from(this.mContext).inflate(R.layout.sec_media_carousel, viewGroup);
        this.mMediaFrames.put(mediaType, view);
        this.mMediaPlayerData.put(mediaType, (SecMediaPlayerData) this.mMediaPlayerDataProvider.get());
        ViewPagerHelper viewPagerHelper = this.mViewPagerHelper;
        ViewPager viewPager = viewPagerHelper.getViewPager(mediaType);
        if (viewPager == null) {
            return;
        }
        viewPager.setAdapter(new ViewPagerHelper$createPageAdapter$1(mediaType, viewPagerHelper));
        if (mediaType.getSupportCarousel()) {
            int i = this.mPagerMargin;
            SecMediaHost$$ExternalSyntheticLambda3 secMediaHost$$ExternalSyntheticLambda3 = new SecMediaHost$$ExternalSyntheticLambda3(this, 1);
            SecMediaHost$$ExternalSyntheticLambda1 secMediaHost$$ExternalSyntheticLambda1 = new SecMediaHost$$ExternalSyntheticLambda1(this);
            SecMediaHost$$ExternalSyntheticLambda2 secMediaHost$$ExternalSyntheticLambda2 = new SecMediaHost$$ExternalSyntheticLambda2(this, 1);
            HashMap hashMap = this.mMediaPlayerData;
            Objects.requireNonNull(hashMap);
            this.mCarouselHelper = new CarouselHelper(view, i, secMediaHost$$ExternalSyntheticLambda3, secMediaHost$$ExternalSyntheticLambda1, secMediaHost$$ExternalSyntheticLambda2, new SecMediaHost$$ExternalSyntheticLambda4(hashMap), mediaType, viewPager);
        }
        if (mediaType.getSupportWidgetTimer()) {
            CoverMusicWidgetController coverMusicWidgetController = new CoverMusicWidgetController(new SecMediaHost$$ExternalSyntheticLambda5(this, 4), new SecMediaHost$$ExternalSyntheticLambda5(this, 1), this.mSubScreenManager, this.mWakefulnessLifeCycle);
            this.mWidgetController = coverMusicWidgetController;
            Log.d("CoverMusicWidgetController", "init");
            coverMusicWidgetController.enableWidget(false);
            coverMusicWidgetController.addVisibilityListenerConsumer.accept((MediaPanelVisibilityListener) coverMusicWidgetController.onPlayerVisibilityListener$delegate.getValue());
            coverMusicWidgetController.lifecycle.addObserver(coverMusicWidgetController.observer);
        }
        if (mediaType.getSupportPlayLastSong()) {
            PlayLastSongHelper playLastSongHelper = new PlayLastSongHelper(view, this.mContext, new SecMediaHost$$ExternalSyntheticLambda5(this, 4), new SecMediaHost$$ExternalSyntheticLambda5(this, 1), this.mBgColorHelper);
            this.mPlayLastSongHelper = playLastSongHelper;
            playLastSongHelper.addVisibilityListenerConsumer.accept((MediaPanelVisibilityListener) playLastSongHelper.onPlayerVisibilityListener$delegate.getValue());
            playLastSongHelper.updateRestartViewVisibility();
            playLastSongHelper.soundAssistantManager.addOnMediaKeyEventSessionChangedListener((SemSoundAssistantManager.OnMediaKeyEventSessionChangedListener) playLastSongHelper.onMediaKeyEventSessionChangeListener$delegate.getValue());
        }
        if (!mediaType.getSupportRoundedCorner()) {
            IntStream.range(0, viewGroup.getChildCount()).mapToObj(new IntFunction() { // from class: com.android.systemui.media.SecMediaHost$$ExternalSyntheticLambda11
                @Override // java.util.function.IntFunction
                public final Object apply(int i2) {
                    return ((ViewGroup) view).getChildAt(i2);
                }
            }).filter(new SecMediaHost$$ExternalSyntheticLambda12()).forEach(new SecMediaHost$$ExternalSyntheticLambda7(0));
        }
        MediaDataFormat mediaDataFormat = this.mCurrentMediaData;
        if (mediaDataFormat != null) {
            updateMediaPlayer(mediaDataFormat.key, mediaDataFormat.oldKey, mediaDataFormat.data, mediaType);
        }
    }

    public final void addOrUpdatePlayer(String str, String str2, MediaData mediaData, MediaType mediaType) {
        SecMediaPlayerData secMediaPlayerData;
        boolean z;
        PagerAdapter adapter;
        PagerAdapter adapter2;
        SecMediaControlPanel secMediaControlPanel;
        int i = this.mContext.getResources().getConfiguration().orientation;
        if (this.mOrientation != i) {
            this.mOrientation = i;
            this.mPlayerNeedForceUpdate = true;
        }
        ViewPagerHelper viewPagerHelper = this.mViewPagerHelper;
        ViewPager viewPager = viewPagerHelper.getViewPager(mediaType);
        if (viewPager == null || (secMediaPlayerData = (SecMediaPlayerData) this.mMediaPlayerData.get(mediaType)) == null) {
            return;
        }
        int mediaPlayerNum = getMediaPlayerNum(mediaType);
        if (((SecMediaControlPanel) secMediaPlayerData.getMediaPlayers().get(str2)) != null && (secMediaControlPanel = (SecMediaControlPanel) secMediaPlayerData.getMediaPlayers().remove(str2)) != null) {
            secMediaPlayerData.getMediaPlayers().put(str, secMediaControlPanel);
        }
        final SecMediaControlPanel secMediaControlPanel2 = (SecMediaControlPanel) secMediaPlayerData.getMediaPlayers().get(str);
        MediaLogger mediaLogger = this.mLogger;
        if (secMediaControlPanel2 == null) {
            secMediaControlPanel2 = (SecMediaControlPanel) this.mMediaControlPanelProvider.get();
            secMediaControlPanel2.mType = mediaType;
            SecPlayerViewHolder secPlayerViewHolder = new SecPlayerViewHolder(this.mContext, viewPager, false, mediaType);
            secMediaControlPanel2.mViewHolder = secPlayerViewHolder;
            SecSeekBarObserver secSeekBarObserver = new SecSeekBarObserver(secPlayerViewHolder);
            secMediaControlPanel2.mSeekBarObserver = secSeekBarObserver;
            SecSeekBarViewModel secSeekBarViewModel = secMediaControlPanel2.mSeekBarViewModel;
            secSeekBarViewModel._progress.observeForever(secSeekBarObserver);
            SeekBar seekBar = secPlayerViewHolder.seekBar;
            if (seekBar == null) {
                seekBar = null;
            }
            seekBar.setOnSeekBarChangeListener(new SecSeekBarViewModel.SeekBarChangeListener(secSeekBarViewModel));
            seekBar.setOnTouchListener(new SecSeekBarViewModel.SeekBarTouchListener(secSeekBarViewModel, seekBar));
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("com.samsung.bluetooth.a2dp.intent.action.DUAL_PLAY_MODE_ENABLED");
            secMediaControlPanel2.mBroadcastDispatcher.registerReceiver(intentFilter, secMediaControlPanel2.mDualPlayModeReceiver);
            secMediaControlPanel2.updateWidth();
            if (secMediaControlPanel2.mType.getSupportColorSchemeTransition()) {
                secMediaControlPanel2.mColorSchemeTransition = new SecColorSchemeTransition(secMediaControlPanel2.mContext, secMediaControlPanel2.mViewHolder);
            }
            if (secMediaControlPanel2.mType.getSupportCapsule()) {
                CoverMusicCapsuleController coverMusicCapsuleController = new CoverMusicCapsuleController(secMediaControlPanel2.mContext, secMediaControlPanel2.mSubScreenManager, new BooleanSupplier() { // from class: com.android.systemui.media.SecMediaControlPanel$$ExternalSyntheticLambda2
                    @Override // java.util.function.BooleanSupplier
                    public final boolean getAsBoolean() {
                        return SecMediaControlPanel.this.mIsPlayerCoverPlayed;
                    }
                });
                secMediaControlPanel2.mCoverMusicCapsuleController = coverMusicCapsuleController;
                secSeekBarViewModel.coverMusicCapsuleController = coverMusicCapsuleController;
            }
            if (secMediaControlPanel2.mType.getSupportWidgetTimer()) {
                secMediaControlPanel2.mWakefulnessLifecycle.addObserver(secMediaControlPanel2.mObserver);
            }
            Runnable runnable = this.mBudsDetailOpenRunnable;
            Runnable runnable2 = this.mBudsDetailCloseRunnable;
            if (secMediaControlPanel2.mType.getSupportBudsButton()) {
                ImageButton imageButton = secMediaControlPanel2.mViewHolder.budsButtonExpanded;
                secMediaControlPanel2.mBudsButtonExpanded = imageButton != null ? imageButton : null;
                secMediaControlPanel2.mBudsDetailOpenRunnable = runnable;
                secMediaControlPanel2.mBudsDetailCloseRunnable = runnable2;
                if (secMediaControlPanel2.mType.getSupportExpandable()) {
                    secMediaControlPanel2.mBudsButtonExpanded.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.media.SecMediaControlPanel$$ExternalSyntheticLambda3
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            SecMediaControlPanel.this.mBudsDetailOpenRunnable.run();
                            SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_QPNE_MEDIA_BUDS_BUTTON);
                        }
                    });
                    ((View) secMediaControlPanel2.mBudsButtonExpanded.getParent()).setOnTouchListener(new SecMediaControlPanel$$ExternalSyntheticLambda4(secMediaControlPanel2, 0));
                }
                secMediaControlPanel2.updateBudsButton();
            }
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(secMediaPlayerData);
            secMediaControlPanel2.mPlayerKey = str;
            secMediaControlPanel2.mQSMediaPlayerBarCallback = anonymousClass1;
            secMediaControlPanel2.bind(mediaData, str);
            secMediaControlPanel2.setListening(this.mLocalListening);
            secMediaPlayerData.getMediaPlayers().put(str, secMediaControlPanel2);
            if (secMediaControlPanel2.isPlaying$1()) {
                secMediaPlayerData.getSortedMediaPlayers().add(0, secMediaControlPanel2);
                z = true;
            } else {
                secMediaPlayerData.getSortedMediaPlayers().add(secMediaControlPanel2);
                z = false;
            }
            ViewPager viewPager2 = viewPagerHelper.getViewPager(mediaType);
            if (viewPager2 != null && (adapter2 = viewPager2.getAdapter()) != null) {
                adapter2.notifyDataSetChanged();
            }
            ((MediaLoggerImpl) mediaLogger).addPlayer(str, secMediaControlPanel2.isPlaying$1());
        } else {
            secMediaControlPanel2.bind(mediaData, str);
            if (!Objects.equals(secMediaControlPanel2.mPlayerKey, str)) {
                secMediaControlPanel2.mPlayerKey = str;
            }
            if (!secMediaControlPanel2.isPlaying$1() || ((SecMediaControlPanel) secMediaPlayerData.getSortedMediaPlayers().get(0)) == secMediaControlPanel2) {
                z = false;
            } else {
                secMediaPlayerData.getSortedMediaPlayers().remove(secMediaControlPanel2);
                secMediaPlayerData.getSortedMediaPlayers().add(0, secMediaControlPanel2);
                z = true;
            }
            ((MediaLoggerImpl) mediaLogger).updatePlayer(str, secMediaControlPanel2.isPlaying$1());
        }
        if (mediaType.getSupportCapsule()) {
            updateCapsule(z, secMediaControlPanel2);
        }
        int size = secMediaPlayerData.getSortedMediaPlayers().size();
        ViewPager viewPager3 = viewPagerHelper.getViewPager(mediaType);
        if (viewPager3 != null && (adapter = viewPager3.getAdapter()) != null) {
            adapter.notifyDataSetChanged();
        }
        if (size > 0) {
            onMediaVisibilityChanged(Boolean.TRUE);
        }
        if (mediaType.getSupportCarousel() && mediaPlayerNum != getMediaPlayerNum(mediaType)) {
            this.mCarouselHelper.updatePageIndicatorNumberPages();
        }
        int currentPage = viewPagerHelper.getCurrentPage(mediaType);
        if (z || currentPage == 0) {
            viewPagerHelper.setCurrentPage(0, false, mediaType);
        }
        if (secMediaPlayerData.getMediaPlayerSize$1() != size) {
            Log.d("SecMediaHost", "Size of players and number of views in carousel are out of sync");
        }
    }

    public final int getMediaPlayerNum(MediaType mediaType) {
        SecMediaPlayerData secMediaPlayerData = (SecMediaPlayerData) this.mMediaPlayerData.get(mediaType);
        if (secMediaPlayerData == null) {
            return 0;
        }
        return secMediaPlayerData.getSortedMediaPlayers().size();
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onConfigChanged(Configuration configuration) {
        this.mMediaFrames.forEach(new SecMediaHost$$ExternalSyntheticLambda0(this, configuration));
    }

    public final void onMediaVisibilityChanged(Boolean bool) {
        this.mVisibilityListeners.forEach(new SecMediaHost$$ExternalSyntheticLambda16(bool));
    }

    /* JADX WARN: Code restructure failed: missing block: B:8:0x000c, code lost:
    
        if (r3 != 1) goto L11;
     */
    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onStateChanged(int r3) {
        /*
            r2 = this;
            int r0 = r2.mBarState
            if (r0 != r3) goto L5
            return
        L5:
            r2.mBarState = r3
            boolean r0 = r2.mLocalListening
            if (r0 == 0) goto Lf
            r0 = 1
            if (r3 == r0) goto Lf
            goto L10
        Lf:
            r0 = 0
        L10:
            r2.mLocalListening = r0
            java.util.HashMap r3 = r2.mMediaPlayerData
            java.util.Collection r3 = r3.values()
            com.android.systemui.media.SecMediaHost$$ExternalSyntheticLambda5 r0 = new com.android.systemui.media.SecMediaHost$$ExternalSyntheticLambda5
            r1 = 0
            r0.<init>(r2, r1)
            r3.forEach(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.SecMediaHost.onStateChanged(int):void");
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onUiModeChanged() {
        this.mMediaPlayerData.values().forEach(new SecMediaHost$$ExternalSyntheticLambda5(this, 3));
    }

    public final void removeMediaFrame(MediaType mediaType) {
        CoverMusicWidgetController coverMusicWidgetController;
        SecMediaPlayerData secMediaPlayerData = (SecMediaPlayerData) this.mMediaPlayerData.get(mediaType);
        SecMediaHost$$ExternalSyntheticLambda15 secMediaHost$$ExternalSyntheticLambda15 = new SecMediaHost$$ExternalSyntheticLambda15(this, mediaType, 0);
        if (secMediaPlayerData != null) {
            secMediaPlayerData.getMediaData().entrySet().forEach(secMediaHost$$ExternalSyntheticLambda15);
        }
        if (mediaType.getSupportWidgetTimer() && (coverMusicWidgetController = this.mWidgetController) != null) {
            Log.d("CoverMusicWidgetController", "destroyed");
            coverMusicWidgetController.removeVisibilityListenerConsumer.accept((MediaPanelVisibilityListener) coverMusicWidgetController.onPlayerVisibilityListener$delegate.getValue());
            coverMusicWidgetController.mediaPauseTimerHandler.removeCallbacksAndMessages(null);
            coverMusicWidgetController.enableWidget(false);
            coverMusicWidgetController.lifecycle.removeObserver(coverMusicWidgetController.observer);
            this.mWidgetController = null;
        }
        if (mediaType.getSupportPlayLastSong()) {
            PlayLastSongHelper playLastSongHelper = this.mPlayLastSongHelper;
            playLastSongHelper.soundAssistantManager.removeOnMediaKeyEventSessionChangedListener((SemSoundAssistantManager.OnMediaKeyEventSessionChangedListener) playLastSongHelper.onMediaKeyEventSessionChangeListener$delegate.getValue());
            playLastSongHelper.removeVisibilityListenerConsumer.accept((MediaPanelVisibilityListener) playLastSongHelper.onPlayerVisibilityListener$delegate.getValue());
            this.mPlayLastSongHelper = null;
        }
        if (mediaType.getSupportCarousel()) {
            this.mCarouselHelper = null;
        }
        ViewPager viewPager = this.mViewPagerHelper.getViewPager(mediaType);
        if (viewPager != null) {
            viewPager.setAdapter(null);
        }
        this.mMediaPlayerData.remove(mediaType);
        this.mMediaFrames.remove(mediaType);
    }

    public final void removePlayer(String str, MediaType mediaType) {
        PagerAdapter adapter;
        SecMediaPlayerData secMediaPlayerData = (SecMediaPlayerData) this.mMediaPlayerData.get(mediaType);
        if (secMediaPlayerData == null) {
            return;
        }
        SecMediaControlPanel secMediaControlPanel = (SecMediaControlPanel) secMediaPlayerData.getMediaPlayers().remove(str);
        MediaLogger mediaLogger = this.mLogger;
        if (secMediaControlPanel == null) {
            ((MediaLoggerImpl) mediaLogger).removePlayerError(str);
            return;
        }
        secMediaPlayerData.getSortedMediaPlayers().remove(secMediaControlPanel);
        ViewPager viewPager = this.mViewPagerHelper.getViewPager(mediaType);
        if (viewPager != null && (adapter = viewPager.getAdapter()) != null) {
            adapter.notifyDataSetChanged();
        }
        secMediaControlPanel.onDestroy();
        if (mediaType.getSupportCarousel()) {
            this.mCarouselHelper.updatePageIndicatorNumberPages();
        }
        ((MediaLoggerImpl) mediaLogger).removePlayer(str);
    }

    public final void updateCapsule(boolean z, SecMediaControlPanel secMediaControlPanel) {
        CoverMusicCapsuleController coverMusicCapsuleController;
        MediaController mediaController;
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("Change Cover Played State : ", "SecMediaHost", z);
        if (z) {
            iteratePlayers((SecMediaPlayerData) this.mMediaPlayerData.get(MediaType.COVER), new SecMediaHost$$ExternalSyntheticLambda7(1));
            if (secMediaControlPanel.mType.getSupportCapsule() && true != secMediaControlPanel.mIsPlayerCoverPlayed) {
                secMediaControlPanel.mIsPlayerCoverPlayed = true;
            }
            if (!secMediaControlPanel.mIsPlayerCoverPlayed || (coverMusicCapsuleController = secMediaControlPanel.mCoverMusicCapsuleController) == null || (mediaController = secMediaControlPanel.mController) == null) {
                return;
            }
            coverMusicCapsuleController.updateEqualizerState(mediaController.getPlaybackState());
        }
    }

    public final void updateMediaPlayer(String str, String str2, MediaData mediaData, MediaType mediaType) {
        BarController.AnonymousClass3 anonymousClass3;
        Boolean bool;
        CharSequence charSequence = mediaData.song;
        if (charSequence != null && charSequence.length() > 5) {
            charSequence = charSequence.subSequence(0, 4);
        }
        ((MediaLoggerImpl) this.mLogger).onMediaDataLoaded(str, str2, charSequence, mediaData.active, Debug.getCallers(8, "  "));
        SecMediaPlayerData secMediaPlayerData = (SecMediaPlayerData) this.mMediaPlayerData.get(mediaType);
        if (secMediaPlayerData != null && str2 != null) {
            secMediaPlayerData.getMediaData().remove(str2);
        }
        if (mediaData.active || Utils.useMediaResumption(this.mContext)) {
            if (secMediaPlayerData != null) {
                secMediaPlayerData.getMediaData().put(str, mediaData);
            }
            addOrUpdatePlayer(str, str2, mediaData, mediaType);
        } else {
            this.mMediaDataListener.onMediaDataRemoved(str, false);
        }
        if (mediaType.getSupportCarousel() && secMediaPlayerData != null && secMediaPlayerData.getSortedMediaPlayers().size() > 0) {
            CarouselHelper carouselHelper = this.mCarouselHelper;
            carouselHelper.indicator.setVisibility(((Number) carouselHelper.getNumberOfPlayersFunction.apply(Boolean.TRUE, carouselHelper.type)).intValue() <= 1 ? 8 : 0);
            CarouselHelper carouselHelper2 = this.mCarouselHelper;
            int color = ((Context) carouselHelper2.contextSupplier.get()).getColor(R.color.qs_page_indicator_tint_color_selected);
            int alphaComponent = ColorUtils.setAlphaComponent(color, 180);
            SecPageIndicator secPageIndicator = carouselHelper2.indicator;
            secPageIndicator.mSelectedColor = color;
            secPageIndicator.mUnselectedColor = alphaComponent;
        }
        if (mediaType.getSupportWidgetTimer() && (bool = mediaData.isPlaying) != null) {
            CoverMusicWidgetController coverMusicWidgetController = this.mWidgetController;
            boolean booleanValue = bool.booleanValue();
            Handler handler = coverMusicWidgetController.mediaPauseTimerHandler;
            if (booleanValue) {
                Log.d("CoverMusicWidgetController", "callback has been removed");
                coverMusicWidgetController.enableWidget(true);
                coverMusicWidgetController.pauseTimerStartedTime = 0L;
                handler.removeCallbacksAndMessages(null);
            } else {
                if (!booleanValue) {
                    CoverMusicWidgetController$widgetDisableRunnable$1 coverMusicWidgetController$widgetDisableRunnable$1 = coverMusicWidgetController.widgetDisableRunnable;
                    if (!handler.hasCallbacks(coverMusicWidgetController$widgetDisableRunnable$1)) {
                        Log.d("CoverMusicWidgetController", "callback has been added");
                        coverMusicWidgetController.pauseTimerStartedTime = System.currentTimeMillis();
                        handler.postDelayed(coverMusicWidgetController$widgetDisableRunnable$1, 120000L);
                    }
                }
                Log.d("CoverMusicWidgetController", "is not playing but already has callback");
            }
        }
        BarController.AnonymousClass4 anonymousClass4 = this.mMediaBarCallback;
        if (anonymousClass4 == null || (anonymousClass3 = BarController.this.mBarListener) == null) {
            return;
        }
        Runnable runnable = BarController.this.mQSLastExpansionInitializer;
        if (runnable != null) {
            runnable.run();
        }
        anonymousClass3.val$animatorRunner.run();
    }

    public final void updateResources$2() {
        this.mPagerMargin = this.mContext.getResources().getDimensionPixelSize(R.dimen.sec_qs_media_side_padding);
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.media.SecMediaHost$2, reason: invalid class name */
    public final class AnonymousClass2 implements MediaDataManager.Listener {
        public AnonymousClass2() {
        }

        @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager.Listener
        public final void onMediaDataLoaded(final String str, final String str2, final MediaData mediaData, final boolean z, final int i, final boolean z2) {
            MediaDataFormat mediaDataFormat = new MediaDataFormat(str, str2, mediaData, z, i, z2);
            SecMediaHost secMediaHost = SecMediaHost.this;
            secMediaHost.mCurrentMediaData = mediaDataFormat;
            secMediaHost.mMediaFrames.forEach(new BiConsumer(str, str2, mediaData, z, i, z2) { // from class: com.android.systemui.media.SecMediaHost$2$$ExternalSyntheticLambda1
                public final /* synthetic */ String f$1;
                public final /* synthetic */ String f$2;
                public final /* synthetic */ MediaData f$3;
                public final /* synthetic */ boolean f$4;
                public final /* synthetic */ int f$5;
                public final /* synthetic */ boolean f$6;

                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    SecMediaHost.this.updateMediaPlayer(this.f$1, this.f$2, this.f$3, (MediaType) obj);
                }
            });
        }

        @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager.Listener
        public final void onMediaDataRemoved(String str, boolean z) {
            SecMediaHost secMediaHost = SecMediaHost.this;
            ((MediaLoggerImpl) secMediaHost.mLogger).onMediaDataRemoved(str, Debug.getCallers(8, "  "));
            secMediaHost.mMediaPlayerData.forEach(new SecMediaHost$$ExternalSyntheticLambda0(this, str));
            secMediaHost.mCurrentMediaData = null;
        }

        @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager.Listener
        public final void onSmartspaceMediaDataLoaded(String str, SmartspaceMediaData smartspaceMediaData) {
        }

        @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager.Listener
        public final void onSmartspaceMediaDataRemoved(String str, boolean z) {
        }
    }
}
