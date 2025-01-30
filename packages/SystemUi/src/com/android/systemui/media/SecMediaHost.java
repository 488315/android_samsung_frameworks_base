package com.android.systemui.media;

import android.content.Context;
import android.content.res.Configuration;
import android.media.session.MediaController;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import androidx.core.graphics.ColorUtils;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.log.MediaLogger;
import com.android.systemui.log.MediaLoggerImpl;
import com.android.systemui.media.controls.models.player.MediaData;
import com.android.systemui.media.controls.models.recommendation.SmartspaceMediaData;
import com.android.systemui.media.controls.pipeline.MediaDataManager;
import com.android.systemui.p016qs.SecPageIndicator;
import com.android.systemui.p016qs.bar.BarController;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.subscreen.SubScreenManager;
import com.android.systemui.util.Utils;
import com.android.systemui.util.concurrency.RepeatableExecutorImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import javax.inject.Provider;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SecMediaHost implements StatusBarStateController.StateListener, ConfigurationController.ConfigurationListener {
    public int mBarState;
    public CarouselHelper mCarouselHelper;
    public final Context mContext;
    public MediaDataFormat mCurrentMediaData;
    public int mIsRTL;
    public boolean mLocalListening;
    public final MediaLogger mLogger;
    public BarController.C20874 mMediaBarCallback;
    public final MediaBluetoothHelper mMediaBluetoothHelper;
    public final Provider mMediaControlPanelProvider;
    public final C17882 mMediaDataListener;
    public final MediaDataManager mMediaDataManager;
    public final HashMap mMediaPlayerData;
    public final Provider mMediaPlayerDataProvider;
    public int mOrientation;
    public int mPagerMargin;
    public MediaPlayerBarExpandHelper mPlayerBarExpandHelper;
    public boolean mPlayerNeedForceUpdate;
    public final StatusBarStateController mStatusBarStateController;
    public final SubScreenManager mSubScreenManager;
    public boolean mUpdatePlayers;
    public final ViewPagerHelper mViewPagerHelper;
    public final ArrayList mVisibilityListeners;
    public final WakefulnessLifecycle mWakefulnessLifeCycle;
    public CoverMusicWidgetController mWidgetController;
    public final HashMap mMediaFrames = new HashMap();
    public final HashMap mOnLayoutChangeListeners = new HashMap();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.media.SecMediaHost$1 */
    public final class C17871 {
        public final /* synthetic */ SecMediaPlayerData val$playerData;

        public C17871(SecMediaPlayerData secMediaPlayerData) {
            this.val$playerData = secMediaPlayerData;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface MediaPanelVisibilityListener {
        void onMediaVisibilityChanged(boolean z);
    }

    public SecMediaHost(Context context, MediaDataManager mediaDataManager, ConfigurationController configurationController, Provider provider, Provider provider2, StatusBarStateController statusBarStateController, MediaBluetoothHelper mediaBluetoothHelper, MediaLogger mediaLogger, SubScreenManager subScreenManager, WakefulnessLifecycle wakefulnessLifecycle) {
        HashMap hashMap = new HashMap();
        this.mMediaPlayerData = hashMap;
        this.mVisibilityListeners = new ArrayList();
        this.mUpdatePlayers = false;
        this.mPlayerNeedForceUpdate = false;
        C17882 c17882 = new C17882();
        this.mMediaDataListener = c17882;
        this.mContext = context;
        this.mLogger = mediaLogger;
        this.mMediaBluetoothHelper = mediaBluetoothHelper;
        this.mStatusBarStateController = statusBarStateController;
        statusBarStateController.addCallback(this);
        this.mMediaPlayerDataProvider = provider2;
        this.mSubScreenManager = subScreenManager;
        this.mWakefulnessLifeCycle = wakefulnessLifecycle;
        this.mViewPagerHelper = new ViewPagerHelper(new SecMediaHost$$ExternalSyntheticLambda1(this, 0), new SecMediaHost$$ExternalSyntheticLambda2(this, 0), new SecMediaHost$$ExternalSyntheticLambda3(this, 0), new SecMediaHost$$ExternalSyntheticLambda4(0, hashMap));
        ((ConfigurationControllerImpl) configurationController).addCallback(this);
        this.mMediaControlPanelProvider = provider;
        this.mMediaDataManager = mediaDataManager;
        mediaDataManager.mediaDataFilter._listeners.add(c17882);
        this.mPagerMargin = context.getResources().getDimensionPixelSize(R.dimen.sec_qs_media_side_padding);
    }

    public static void iteratePlayers(SecMediaPlayerData secMediaPlayerData, Consumer consumer) {
        if (secMediaPlayerData == null) {
            return;
        }
        secMediaPlayerData.getMediaPlayers().values().forEach(consumer);
    }

    public final void addMediaFrame(final View view, MediaType mediaType) {
        ViewGroup viewGroup;
        int i;
        Context context = this.mContext;
        ViewGroup viewGroup2 = (ViewGroup) view;
        LayoutInflater.from(context).inflate(R.layout.sec_media_carousel, viewGroup2);
        this.mMediaFrames.put(mediaType, view);
        SecMediaPlayerData secMediaPlayerData = (SecMediaPlayerData) this.mMediaPlayerDataProvider.get();
        HashMap hashMap = this.mMediaPlayerData;
        hashMap.put(mediaType, secMediaPlayerData);
        ViewPagerHelper viewPagerHelper = this.mViewPagerHelper;
        ViewPager viewPager = viewPagerHelper.getViewPager(mediaType);
        if (viewPager == null) {
            return;
        }
        viewPager.setAdapter(new ViewPagerHelper$createPageAdapter$1(mediaType, viewPagerHelper));
        final int i2 = 0;
        if (mediaType.getSupportExpandable()) {
            this.mPlayerBarExpandHelper = new MediaPlayerBarExpandHelper(context, secMediaPlayerData, new SecMediaHost$$ExternalSyntheticLambda3(this, 1), new Supplier() { // from class: com.android.systemui.media.SecMediaHost$$ExternalSyntheticLambda10
                @Override // java.util.function.Supplier
                public final Object get() {
                    switch (i2) {
                        case 0:
                            return (View) view;
                        default:
                            return (SecMediaControlPanel) ((Provider) view).get();
                    }
                }
            });
        }
        if (mediaType.getSupportCarousel()) {
            int i3 = this.mPagerMargin;
            SecMediaHost$$ExternalSyntheticLambda3 secMediaHost$$ExternalSyntheticLambda3 = new SecMediaHost$$ExternalSyntheticLambda3(this, 2);
            SecMediaHost$$ExternalSyntheticLambda1 secMediaHost$$ExternalSyntheticLambda1 = new SecMediaHost$$ExternalSyntheticLambda1(this, 1);
            SecMediaHost$$ExternalSyntheticLambda2 secMediaHost$$ExternalSyntheticLambda2 = new SecMediaHost$$ExternalSyntheticLambda2(this, 1);
            MediaLogger mediaLogger = this.mLogger;
            MediaDataManager mediaDataManager = this.mMediaDataManager;
            SecMediaHost$$ExternalSyntheticLambda4 secMediaHost$$ExternalSyntheticLambda4 = new SecMediaHost$$ExternalSyntheticLambda4(1, hashMap);
            Runnable runnable = new Runnable() { // from class: com.android.systemui.media.SecMediaHost$$ExternalSyntheticLambda11
                @Override // java.lang.Runnable
                public final void run() {
                    BarController.C20874 c20874 = SecMediaHost.this.mMediaBarCallback;
                    if (c20874 == null) {
                        return;
                    }
                    c20874.onBarHeightChanged();
                }
            };
            SecMediaHost$$ExternalSyntheticLambda0 secMediaHost$$ExternalSyntheticLambda0 = new SecMediaHost$$ExternalSyntheticLambda0(this, 4);
            MediaPlayerBarExpandHelper mediaPlayerBarExpandHelper = this.mPlayerBarExpandHelper;
            final Provider provider = this.mMediaControlPanelProvider;
            Objects.requireNonNull(provider);
            final int i4 = 1;
            viewGroup = viewGroup2;
            final int i5 = 0;
            this.mCarouselHelper = new CarouselHelper(view, i3, secMediaHost$$ExternalSyntheticLambda3, secMediaHost$$ExternalSyntheticLambda1, secMediaHost$$ExternalSyntheticLambda2, mediaLogger, mediaDataManager, secMediaHost$$ExternalSyntheticLambda4, runnable, secMediaHost$$ExternalSyntheticLambda0, mediaPlayerBarExpandHelper, new Supplier() { // from class: com.android.systemui.media.SecMediaHost$$ExternalSyntheticLambda10
                @Override // java.util.function.Supplier
                public final Object get() {
                    switch (i4) {
                        case 0:
                            return (View) provider;
                        default:
                            return (SecMediaControlPanel) ((Provider) provider).get();
                    }
                }
            }, new BiConsumer(this) { // from class: com.android.systemui.media.SecMediaHost$$ExternalSyntheticLambda12
                public final /* synthetic */ SecMediaHost f$0;

                {
                    this.f$0 = this;
                }

                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    switch (i5) {
                        case 0:
                            this.f$0.removePlayer((MediaType) obj2, (String) obj);
                            break;
                        default:
                            SecMediaHost secMediaHost = this.f$0;
                            secMediaHost.getClass();
                            int intValue = ((Integer) obj).intValue();
                            secMediaHost.mViewPagerHelper.setCurrentPage(intValue, true, (MediaType) obj2);
                            break;
                    }
                }
            }, new BiConsumer(this) { // from class: com.android.systemui.media.SecMediaHost$$ExternalSyntheticLambda12
                public final /* synthetic */ SecMediaHost f$0;

                {
                    this.f$0 = this;
                }

                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    switch (i4) {
                        case 0:
                            this.f$0.removePlayer((MediaType) obj2, (String) obj);
                            break;
                        default:
                            SecMediaHost secMediaHost = this.f$0;
                            secMediaHost.getClass();
                            int intValue = ((Integer) obj).intValue();
                            secMediaHost.mViewPagerHelper.setCurrentPage(intValue, true, (MediaType) obj2);
                            break;
                    }
                }
            }, mediaType, new BooleanSupplier() { // from class: com.android.systemui.media.SecMediaHost$$ExternalSyntheticLambda7
                @Override // java.util.function.BooleanSupplier
                public final boolean getAsBoolean() {
                    return SecMediaHost.this.mUpdatePlayers;
                }
            }, viewPager);
        } else {
            viewGroup = viewGroup2;
        }
        if (mediaType.getSupportWidgetTimer()) {
            CoverMusicWidgetController coverMusicWidgetController = new CoverMusicWidgetController(new SecMediaHost$$ExternalSyntheticLambda0(this, 2), new SecMediaHost$$ExternalSyntheticLambda0(this, 3), this.mSubScreenManager, this.mWakefulnessLifeCycle);
            this.mWidgetController = coverMusicWidgetController;
            Log.d("CoverMusicWidgetController", "init");
            i = 0;
            coverMusicWidgetController.enableWidget(false);
            coverMusicWidgetController.addVisibilityListenerConsumer.accept((MediaPanelVisibilityListener) coverMusicWidgetController.onPlayerVisibilityListener$delegate.getValue());
            coverMusicWidgetController.lifecycle.addObserver(coverMusicWidgetController.observer);
        } else {
            i = 0;
        }
        if (!mediaType.getSupportRoundedCorner()) {
            IntStream.range(i, viewGroup.getChildCount()).mapToObj(new IntFunction() { // from class: com.android.systemui.media.SecMediaHost$$ExternalSyntheticLambda8
                @Override // java.util.function.IntFunction
                public final Object apply(int i6) {
                    return ((ViewGroup) view).getChildAt(i6);
                }
            }).filter(new SecMediaHost$$ExternalSyntheticLambda9()).forEach(new SecMediaHost$$ExternalSyntheticLambda14(1));
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
        Context context = this.mContext;
        int i = context.getResources().getConfiguration().orientation;
        final int i2 = 1;
        if (this.mOrientation != i) {
            this.mOrientation = i;
            this.mPlayerNeedForceUpdate = true;
        }
        ViewPagerHelper viewPagerHelper = this.mViewPagerHelper;
        ViewPager viewPager = viewPagerHelper.getViewPager(mediaType);
        if (viewPager == null || (secMediaPlayerData = (SecMediaPlayerData) this.mMediaPlayerData.get(mediaType)) == null) {
            return;
        }
        final int i3 = 0;
        int mediaPlayerNum = getMediaPlayerNum(false, mediaType);
        if (((SecMediaControlPanel) secMediaPlayerData.getMediaPlayers().get(str2)) != null && (secMediaControlPanel = (SecMediaControlPanel) secMediaPlayerData.getMediaPlayers().remove(str2)) != null) {
            secMediaPlayerData.getMediaPlayers().put(str, secMediaControlPanel);
        }
        final SecMediaControlPanel secMediaControlPanel2 = (SecMediaControlPanel) secMediaPlayerData.getMediaPlayers().get(str);
        MediaLogger mediaLogger = this.mLogger;
        if (secMediaControlPanel2 == null) {
            secMediaControlPanel2 = (SecMediaControlPanel) this.mMediaControlPanelProvider.get();
            secMediaControlPanel2.mType = mediaType;
            secMediaControlPanel2.attach(new SecPlayerViewHolder(context, viewPager, false, mediaType));
            if (secMediaControlPanel2.mType.getSupportBudsButton()) {
                SecPlayerViewHolder secPlayerViewHolder = secMediaControlPanel2.mViewHolder;
                ImageButton imageButton = secPlayerViewHolder.budsButtonCollapsed;
                if (imageButton == null) {
                    imageButton = null;
                }
                secMediaControlPanel2.mBudsButtonCollapsed = imageButton;
                ImageButton imageButton2 = secPlayerViewHolder.budsButtonExpanded;
                if (imageButton2 == null) {
                    imageButton2 = null;
                }
                secMediaControlPanel2.mBudsButtonExpanded = imageButton2;
                secMediaControlPanel2.mBudsDetailOpenRunnable = null;
                secMediaControlPanel2.mBudsDetailCloseRunnable = null;
                if (secMediaControlPanel2.mType.getSupportExpandable()) {
                    secMediaControlPanel2.mBudsButtonCollapsed.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.media.SecMediaControlPanel$$ExternalSyntheticLambda6
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            switch (i3) {
                                case 0:
                                    secMediaControlPanel2.mBudsDetailOpenRunnable.run();
                                    break;
                                default:
                                    secMediaControlPanel2.mBudsDetailOpenRunnable.run();
                                    break;
                            }
                        }
                    });
                    secMediaControlPanel2.mBudsButtonExpanded.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.media.SecMediaControlPanel$$ExternalSyntheticLambda6
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            switch (i2) {
                                case 0:
                                    secMediaControlPanel2.mBudsDetailOpenRunnable.run();
                                    break;
                                default:
                                    secMediaControlPanel2.mBudsDetailOpenRunnable.run();
                                    break;
                            }
                        }
                    });
                }
                secMediaControlPanel2.updateBudsButton();
            }
            C17871 c17871 = new C17871(secMediaPlayerData);
            secMediaControlPanel2.mPlayerKey = str;
            secMediaControlPanel2.mQSMediaPlayerBarCallback = c17871;
            secMediaControlPanel2.bind(mediaData, str);
            secMediaControlPanel2.setListening(this.mLocalListening);
            secMediaPlayerData.getMediaPlayers().put(str, secMediaControlPanel2);
            if (secMediaControlPanel2.isPlaying()) {
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
            ((MediaLoggerImpl) mediaLogger).addPlayer(str, secMediaControlPanel2.isPlaying());
        } else {
            secMediaControlPanel2.bind(mediaData, str);
            if (!Objects.equals(secMediaControlPanel2.mPlayerKey, str)) {
                secMediaControlPanel2.mPlayerKey = str;
            }
            if (!secMediaControlPanel2.isPlaying() || ((SecMediaControlPanel) secMediaPlayerData.getSortedMediaPlayers().get(0)) == secMediaControlPanel2) {
                z = false;
            } else {
                secMediaPlayerData.getSortedMediaPlayers().remove(secMediaControlPanel2);
                secMediaPlayerData.getSortedMediaPlayers().add(0, secMediaControlPanel2);
                z = true;
            }
            ((MediaLoggerImpl) mediaLogger).updatePlayer(str, secMediaControlPanel2.isPlaying());
        }
        if (mediaType.getSupportCapsule()) {
            updateCapsule(secMediaControlPanel2, z);
        }
        int sortedMediaPlayersSize = secMediaPlayerData.getSortedMediaPlayersSize();
        if (mediaType.getSupportCarousel() && sortedMediaPlayersSize > 0) {
            CarouselHelper carouselHelper = this.mCarouselHelper;
            carouselHelper.getClass();
            if (secMediaPlayerData.firstPageView == null) {
                secMediaPlayerData.firstPageView = CarouselHelper.addOrUpdateSentinels$addSentinel(carouselHelper);
            }
            if (secMediaPlayerData.lastPageView == null) {
                secMediaPlayerData.lastPageView = CarouselHelper.addOrUpdateSentinels$addSentinel(carouselHelper);
            }
            SecMediaControlPanel secMediaControlPanel3 = secMediaPlayerData.firstPageView;
            if (secMediaControlPanel3 != null) {
                CarouselHelper.addOrUpdateSentinels$updateSentinel(secMediaPlayerData, secMediaControlPanel3, "first_page", true);
            }
            SecMediaControlPanel secMediaControlPanel4 = secMediaPlayerData.lastPageView;
            if (secMediaControlPanel4 != null) {
                CarouselHelper.addOrUpdateSentinels$updateSentinel(secMediaPlayerData, secMediaControlPanel4, "last_page", false);
            }
        }
        ViewPager viewPager3 = viewPagerHelper.getViewPager(mediaType);
        if (viewPager3 != null && (adapter = viewPager3.getAdapter()) != null) {
            adapter.notifyDataSetChanged();
        }
        if (mediaType.getSupportExpandable()) {
            secMediaPlayerData.getSortedMediaPlayers().forEach(new SecMediaHost$$ExternalSyntheticLambda0(this, 6));
            this.mPlayerBarExpandHelper.setPlayerBarExpansion();
        }
        if (sortedMediaPlayersSize > 0) {
            onMediaVisibilityChanged(Boolean.TRUE);
        }
        if (mediaType.getSupportCarousel() && mediaPlayerNum != getMediaPlayerNum(false, mediaType)) {
            this.mCarouselHelper.updatePageIndicatorNumberPages();
        }
        int currentPage = viewPagerHelper.getCurrentPage(mediaType);
        if (z || currentPage == 0 || currentPage == getMediaPlayerNum(false, mediaType) - 1) {
            viewPagerHelper.setCurrentPage(1, false, mediaType);
        }
        if (secMediaPlayerData.getMediaPlayers().size() != sortedMediaPlayersSize) {
            Log.d("SecMediaHost", "Size of players and number of views in carousel are out of sync");
        }
    }

    public final int getMediaPlayerNum(boolean z, MediaType mediaType) {
        SecMediaPlayerData secMediaPlayerData = (SecMediaPlayerData) this.mMediaPlayerData.get(mediaType);
        if (secMediaPlayerData == null) {
            return 0;
        }
        int sortedMediaPlayersSize = secMediaPlayerData.getSortedMediaPlayersSize();
        return sortedMediaPlayersSize > 12 ? z ? 10 : 12 : (!z || sortedMediaPlayersSize <= 2) ? sortedMediaPlayersSize : sortedMediaPlayersSize - 2;
    }

    public final int getMediaPlayerSize(MediaType mediaType) {
        SecMediaPlayerData secMediaPlayerData = (SecMediaPlayerData) this.mMediaPlayerData.get(mediaType);
        if (secMediaPlayerData != null) {
            return secMediaPlayerData.getMediaPlayers().size();
        }
        return 0;
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onConfigChanged(Configuration configuration) {
        this.mMediaFrames.forEach(new SecMediaHost$$ExternalSyntheticLambda5(this, configuration, 0));
    }

    public final void onMediaVisibilityChanged(Boolean bool) {
        this.mVisibilityListeners.forEach(new SecMediaHost$$ExternalSyntheticLambda15(bool, 1));
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public final void onStateChanged(int i) {
        if (this.mBarState == i) {
            return;
        }
        this.mBarState = i;
        int i2 = 0;
        this.mLocalListening = i != 1;
        this.mMediaPlayerData.values().forEach(new SecMediaHost$$ExternalSyntheticLambda0(this, i2));
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onUiModeChanged() {
        this.mMediaPlayerData.values().forEach(new SecMediaHost$$ExternalSyntheticLambda0(this, 1));
    }

    public final void removeMediaFrame(MediaType mediaType) {
        CoverMusicWidgetController coverMusicWidgetController;
        HashMap hashMap = this.mMediaPlayerData;
        SecMediaPlayerData secMediaPlayerData = (SecMediaPlayerData) hashMap.get(mediaType);
        SecMediaHost$$ExternalSyntheticLambda6 secMediaHost$$ExternalSyntheticLambda6 = new SecMediaHost$$ExternalSyntheticLambda6(this, mediaType, 0);
        if (secMediaPlayerData != null) {
            secMediaPlayerData.getMediaData().entrySet().forEach(secMediaHost$$ExternalSyntheticLambda6);
        }
        if (mediaType.getSupportCarousel() && secMediaPlayerData != null && secMediaPlayerData.getSortedMediaPlayersSize() > 0) {
            this.mCarouselHelper.removeSentinels(secMediaPlayerData);
        }
        if (mediaType.getSupportWidgetTimer() && (coverMusicWidgetController = this.mWidgetController) != null) {
            Log.d("CoverMusicWidgetController", "destroyed");
            coverMusicWidgetController.removeVisibilityListenerConsumer.accept((MediaPanelVisibilityListener) coverMusicWidgetController.onPlayerVisibilityListener$delegate.getValue());
            coverMusicWidgetController.mediaPauseTimerHandler.removeCallbacksAndMessages(null);
            coverMusicWidgetController.enableWidget(false);
            coverMusicWidgetController.lifecycle.removeObserver(coverMusicWidgetController.observer);
            this.mWidgetController = null;
        }
        if (mediaType.getSupportCarousel()) {
            this.mCarouselHelper = null;
        }
        boolean supportExpandable = mediaType.getSupportExpandable();
        HashMap hashMap2 = this.mMediaFrames;
        if (supportExpandable) {
            this.mPlayerBarExpandHelper = null;
        } else {
            HashMap hashMap3 = this.mOnLayoutChangeListeners;
            View.OnLayoutChangeListener onLayoutChangeListener = (View.OnLayoutChangeListener) hashMap3.get(mediaType);
            if (onLayoutChangeListener != null) {
                View view = (View) hashMap2.get(mediaType);
                if (view != null) {
                    view.removeOnLayoutChangeListener(onLayoutChangeListener);
                }
                hashMap3.remove(mediaType);
            }
        }
        ViewPager viewPager = this.mViewPagerHelper.getViewPager(mediaType);
        if (viewPager != null) {
            viewPager.setAdapter(null);
        }
        hashMap.remove(mediaType);
        hashMap2.remove(mediaType);
    }

    public final void removePlayer(MediaType mediaType, String str) {
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
        SecSeekBarObserver secSeekBarObserver = secMediaControlPanel.mSeekBarObserver;
        final SecSeekBarViewModel secSeekBarViewModel = secMediaControlPanel.mSeekBarViewModel;
        if (secSeekBarObserver != null) {
            secSeekBarViewModel._progress.removeObserver(secSeekBarObserver);
        }
        secSeekBarViewModel.getClass();
        ((RepeatableExecutorImpl) secSeekBarViewModel.bgExecutor).execute(new Runnable() { // from class: com.android.systemui.media.SecSeekBarViewModel$onDestroy$1
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
        if (Intrinsics.areEqual(secMediaControlPanel.mSessionDestroyCallback, secSeekBarViewModel.sessionDestroyCallback)) {
            secSeekBarViewModel.sessionDestroyCallback = null;
        }
        try {
            secMediaControlPanel.mBroadcastDispatcher.unregisterReceiver(secMediaControlPanel.mDualPlayModeReceiver);
        } catch (Exception unused) {
        }
        secMediaControlPanel.mTunerService.removeTunable(secMediaControlPanel.mTunable);
        if (secMediaControlPanel.mType.getSupportCapsule()) {
            CoverMusicCapsuleController coverMusicCapsuleController = secMediaControlPanel.mCoverMusicCapsuleController;
            coverMusicCapsuleController.getClass();
            Log.d("CoverMusicCapsuleController", "capsule destroyed");
            Bundle bundle = coverMusicCapsuleController.bundle;
            bundle.putBoolean("visible", false);
            bundle.putParcelable("capsule_layout", coverMusicCapsuleController.capsule);
            bundle.putString("capsule_priority", "low");
            coverMusicCapsuleController.updateCapsule();
        }
        if (secMediaControlPanel.mType.getSupportWidgetTimer()) {
            secMediaControlPanel.mWakefulnessLifecycle.removeObserver(secMediaControlPanel.mObserver);
        }
        secMediaControlPanel.mSettingsHelper.unregisterCallback(secMediaControlPanel.mSettingsListener);
        if (mediaType.getSupportCarousel()) {
            this.mCarouselHelper.updatePageIndicatorNumberPages();
        }
        ((MediaLoggerImpl) mediaLogger).removePlayer(str);
    }

    public final void updateCapsule(SecMediaControlPanel secMediaControlPanel, boolean z) {
        CoverMusicCapsuleController coverMusicCapsuleController;
        MediaController mediaController;
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("Change Cover Played State : ", z, "SecMediaHost");
        if (z) {
            iteratePlayers((SecMediaPlayerData) this.mMediaPlayerData.get(MediaType.COVER), new SecMediaHost$$ExternalSyntheticLambda14(2));
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
            this.mMediaDataListener.onMediaDataRemoved(str);
        }
        if (mediaType.getSupportCarousel() && secMediaPlayerData != null && secMediaPlayerData.getSortedMediaPlayersSize() > 0) {
            this.mCarouselHelper.updatePageIndicatorVisibility();
            CarouselHelper carouselHelper = this.mCarouselHelper;
            int color = ((Context) carouselHelper.contextSupplier.get()).getColor(R.color.media_primary_text);
            int alphaComponent = ColorUtils.setAlphaComponent(color, 180);
            SecPageIndicator secPageIndicator = carouselHelper.indicator;
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
        BarController.C20874 c20874 = this.mMediaBarCallback;
        if (c20874 == null) {
            return;
        }
        c20874.onBarHeightChanged();
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.media.SecMediaHost$2 */
    public final class C17882 implements MediaDataManager.Listener {
        public C17882() {
        }

        @Override // com.android.systemui.media.controls.pipeline.MediaDataManager.Listener
        public final void onMediaDataLoaded(final String str, final String str2, final MediaData mediaData, final boolean z, final int i, final boolean z2) {
            MediaDataFormat mediaDataFormat = new MediaDataFormat(str, str2, mediaData, z, i, z2);
            SecMediaHost secMediaHost = SecMediaHost.this;
            secMediaHost.mCurrentMediaData = mediaDataFormat;
            secMediaHost.mMediaFrames.forEach(new BiConsumer() { // from class: com.android.systemui.media.SecMediaHost$2$$ExternalSyntheticLambda0
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    SecMediaHost.this.updateMediaPlayer(str, str2, mediaData, (MediaType) obj);
                }
            });
        }

        @Override // com.android.systemui.media.controls.pipeline.MediaDataManager.Listener
        public final void onMediaDataRemoved(String str) {
            SecMediaHost secMediaHost = SecMediaHost.this;
            ((MediaLoggerImpl) secMediaHost.mLogger).onMediaDataRemoved(str, Debug.getCallers(8, "  "));
            secMediaHost.mMediaPlayerData.forEach(new SecMediaHost$$ExternalSyntheticLambda5(this, str, 1));
            secMediaHost.mCurrentMediaData = null;
        }

        @Override // com.android.systemui.media.controls.pipeline.MediaDataManager.Listener
        public final void onSmartspaceMediaDataLoaded(String str, SmartspaceMediaData smartspaceMediaData) {
        }

        @Override // com.android.systemui.media.controls.pipeline.MediaDataManager.Listener
        public final void onSmartspaceMediaDataRemoved(String str, boolean z) {
        }
    }
}
