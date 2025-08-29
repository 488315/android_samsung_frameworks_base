package com.android.systemui.media;

import android.content.Context;
import android.content.res.Configuration;
import android.media.session.MediaController;
import android.os.Debug;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.core.graphics.ColorUtils;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.systemui.R;
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
import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.media.controls.shared.model.MediaData;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qp.media.QSCoverPlayLastSongHelper;
import com.android.systemui.qs.QSImpl$$ExternalSyntheticLambda2;
import com.android.systemui.qs.SecPageIndicator;
import com.android.systemui.qs.bar.BarController;
import com.android.systemui.qs.bar.ColoredBGHelper;
import com.android.systemui.qs.bar.QSMediaPlayerBar$$ExternalSyntheticLambda3;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.subscreen.SubScreenManager;
import com.android.systemui.subscreen.SubScreenQuickPanelWindowController$$ExternalSyntheticLambda15;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.Utils;
import com.samsung.android.media.SemSoundAssistantManager;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.stream.IntStream;
import javax.inject.Provider;
import kotlin.Pair;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public class SecMediaHost implements StatusBarStateController.StateListener, ConfigurationController.ConfigurationListener {
    public int mBarState;
    public final ColoredBGHelper mBgColorHelper;
    public QSMediaPlayerBar$$ExternalSyntheticLambda3 mBudsDetailCloseRunnable;
    public QSMediaPlayerBar$$ExternalSyntheticLambda3 mBudsDetailOpenRunnable;
    public CarouselHelper mCarouselHelper;
    public final Context mContext;
    public Context mCoverContext;
    public SubScreenQuickPanelWindowController$$ExternalSyntheticLambda15 mCoverQSClickConsumer;
    public MediaDataFormat mCurrentMediaData;
    public int mCurrentScreenLayout;
    public int mIsRTL;
    public boolean mLocalListening;
    public final MediaLogger mLogger;
    public BarController.AnonymousClass4 mMediaBarCallback;
    public final MediaBluetoothHelper mMediaBluetoothHelper;
    public final Provider mMediaControlPanelProvider;
    public final AnonymousClass2 mMediaDataListener;
    public final MediaDataManager mMediaDataManager;
    public final ConcurrentHashMap mMediaFrames = new ConcurrentHashMap();
    public final ConcurrentHashMap mMediaPlayerData;
    public final Provider mMediaPlayerDataProvider;
    public int mOrientation;
    public int mPagerMargin;
    public PlayLastSongHelper mPlayLastSongHelper;
    public boolean mPlayerNeedForceUpdate;
    public QSCoverPlayLastSongHelper mQSCoverPlayLastSongHelper;
    private SettingsHelper mSettingsHelper;
    public final StatusBarStateController mStatusBarStateController;
    public final SubScreenManager mSubScreenManager;
    public final ViewPagerHelper mViewPagerHelper;
    public final ArrayList mVisibilityListeners;
    public final WakefulnessLifecycle mWakefulnessLifeCycle;
    public CoverMusicWidgetController mWidgetController;

    /* renamed from: com.android.systemui.media.SecMediaHost$1, reason: invalid class name */
    public class AnonymousClass1 {
        public final /* synthetic */ SecMediaPlayerData val$playerData;

        public AnonymousClass1(SecMediaPlayerData secMediaPlayerData) {
            this.val$playerData = secMediaPlayerData;
        }
    }

    /* renamed from: com.android.systemui.media.SecMediaHost$2, reason: invalid class name */
    public class AnonymousClass2 implements MediaDataManager.Listener {
        public AnonymousClass2() {
        }

        @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager.Listener
        public final void onMediaDataLoaded(final String str, final String str2, final MediaData mediaData, final boolean z) {
            MediaDataFormat mediaDataFormat = new MediaDataFormat(str, str2, mediaData, z, 0, false);
            SecMediaHost secMediaHost = SecMediaHost.this;
            secMediaHost.mCurrentMediaData = mediaDataFormat;
            secMediaHost.mMediaFrames.forEach(new BiConsumer(str, str2, mediaData, z) { // from class: com.android.systemui.media.SecMediaHost$2$$ExternalSyntheticLambda1
                public final /* synthetic */ String f$1;
                public final /* synthetic */ String f$2;
                public final /* synthetic */ MediaData f$3;

                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    SecMediaHost.this.updateMediaPlayer(this.f$1, this.f$2, this.f$3, (MediaType) obj);
                }
            });
        }

        @Override // com.android.systemui.media.controls.domain.pipeline.MediaDataManager.Listener
        public final void onMediaDataRemoved(final String str, boolean z) {
            SecMediaHost secMediaHost = SecMediaHost.this;
            MediaLogger mediaLogger = secMediaHost.mLogger;
            String callers = Debug.getCallers(8, "  ");
            MediaLogWriter mediaLogWriter = ((MediaLoggerImpl) mediaLogger).writer;
            mediaLogWriter.getClass();
            LogLevel logLevel = LogLevel.DEBUG;
            MediaLogWriter$$ExternalSyntheticLambda0 mediaLogWriter$$ExternalSyntheticLambda0 = new MediaLogWriter$$ExternalSyntheticLambda0(4);
            LogBuffer logBuffer = mediaLogWriter.buffer;
            LogMessage logMessageObtain = logBuffer.obtain("MediaLogger", logLevel, mediaLogWriter$$ExternalSyntheticLambda0, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
            logMessageImpl.str1 = str;
            logMessageImpl.str2 = callers;
            logBuffer.commit(logMessageObtain);
            Log.d("MediaLogger", "Media data removed [" + str + "]");
            secMediaHost.mMediaPlayerData.forEach(new BiConsumer() { // from class: com.android.systemui.media.SecMediaHost$2$$ExternalSyntheticLambda0
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    BarController.AnonymousClass3 anonymousClass3;
                    MediaDataFormat mediaDataFormat;
                    QSCoverPlayLastSongHelper qSCoverPlayLastSongHelper;
                    MediaDataFormat mediaDataFormat2;
                    PlayLastSongHelper playLastSongHelper;
                    SecMediaHost.AnonymousClass2 anonymousClass2 = this.f$0;
                    String str2 = str;
                    MediaType mediaType = (MediaType) obj;
                    SecMediaPlayerData secMediaPlayerData = (SecMediaPlayerData) obj2;
                    anonymousClass2.getClass();
                    boolean supportPlayLastSong = mediaType.getSupportPlayLastSong();
                    SecMediaHost secMediaHost2 = SecMediaHost.this;
                    if (supportPlayLastSong && (mediaDataFormat2 = secMediaHost2.mCurrentMediaData) != null && (playLastSongHelper = secMediaHost2.mPlayLastSongHelper) != null) {
                        playLastSongHelper.lastMediaPlayerKey = mediaDataFormat2.data.packageName;
                    }
                    if (mediaType.getSupportPlayLastSong() && (mediaDataFormat = secMediaHost2.mCurrentMediaData) != null && (qSCoverPlayLastSongHelper = secMediaHost2.mQSCoverPlayLastSongHelper) != null) {
                        qSCoverPlayLastSongHelper.lastMediaPlayerKey = mediaDataFormat.data.packageName;
                    }
                    secMediaPlayerData.m2627getMediaData().remove(str2);
                    secMediaHost2.removePlayer(str2, mediaType);
                    int size = secMediaPlayerData.m2627getMediaData().size();
                    if (size == 0) {
                        secMediaHost2.onMediaVisibilityChanged(Boolean.FALSE);
                    } else {
                        ViewPagerHelper viewPagerHelper = secMediaHost2.mViewPagerHelper;
                        if (viewPagerHelper.getCurrentPage(mediaType) > size) {
                            viewPagerHelper.setCurrentPage(size, true, mediaType);
                        }
                        if (mediaType.getSupportCapsule()) {
                            secMediaHost2.updateCapsule(true, secMediaPlayerData.getMediaPlayerFromSortedMediaPlayers(0));
                        }
                        if (mediaType.getSupportOAChip()) {
                            secMediaHost2.updateOAChip(true, secMediaPlayerData.getMediaPlayerFromSortedMediaPlayers(0));
                        }
                        if (mediaType.getSupportCarousel()) {
                            CarouselHelper carouselHelper = secMediaHost2.mCarouselHelper;
                            int color = ((Context) carouselHelper.contextSupplier.get()).getColor(R.color.qs_page_indicator_tint_color_selected);
                            int alphaComponent = ColorUtils.setAlphaComponent(color, 180);
                            SecPageIndicator secPageIndicator = carouselHelper.indicator;
                            secPageIndicator.mSelectedColor = color;
                            secPageIndicator.mUnselectedColor = alphaComponent;
                            secMediaHost2.mCarouselHelper.updatePageIndicatorNumberPages();
                            secMediaHost2.mCarouselHelper.indicator.reset(viewPagerHelper.getCurrentPage(mediaType));
                        }
                    }
                    if (mediaType.getSupportCarousel()) {
                        CarouselHelper carouselHelper2 = secMediaHost2.mCarouselHelper;
                        carouselHelper2.indicator.setVisibility(((Number) carouselHelper2.getNumberOfPlayersFunction.apply(Boolean.TRUE, carouselHelper2.type)).intValue() <= 1 ? 8 : 0);
                    }
                    BarController.AnonymousClass4 anonymousClass4 = secMediaHost2.mMediaBarCallback;
                    if (anonymousClass4 == null || (anonymousClass3 = BarController.this.mBarListener) == null) {
                        return;
                    }
                    QSImpl$$ExternalSyntheticLambda2 qSImpl$$ExternalSyntheticLambda2 = BarController.this.mQSLastExpansionInitializer;
                    if (qSImpl$$ExternalSyntheticLambda2 != null) {
                        qSImpl$$ExternalSyntheticLambda2.run();
                    }
                    anonymousClass3.val$animatorRunner.run();
                }
            });
            secMediaHost.mCurrentMediaData = null;
        }
    }

    public interface MediaPanelVisibilityListener {
        void onMediaVisibilityChanged(boolean z);
    }

    public SecMediaHost(Context context, MediaDataManager mediaDataManager, ConfigurationController configurationController, Provider provider, Provider provider2, StatusBarStateController statusBarStateController, MediaBluetoothHelper mediaBluetoothHelper, MediaLogger mediaLogger, SubScreenManager subScreenManager, WakefulnessLifecycle wakefulnessLifecycle, ColoredBGHelper coloredBGHelper, SettingsHelper settingsHelper) {
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        this.mMediaPlayerData = concurrentHashMap;
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
        this.mViewPagerHelper = new ViewPagerHelper(new SecMediaHost$$ExternalSyntheticLambda4(this), new SecMediaHost$$ExternalSyntheticLambda5(this, 1), new SecMediaHost$$ExternalSyntheticLambda0(this, 1), new SecMediaHost$$ExternalSyntheticLambda6(concurrentHashMap));
        ((ConfigurationControllerImpl) configurationController).addCallback(this);
        this.mMediaControlPanelProvider = provider;
        this.mMediaDataManager = mediaDataManager;
        mediaDataManager.addListener(anonymousClass2);
        this.mBgColorHelper = coloredBGHelper;
        this.mSettingsHelper = settingsHelper;
        this.mPagerMargin = context.getResources().getDimensionPixelSize(R.dimen.sec_qs_media_side_padding);
    }

    public static void iteratePlayers(SecMediaPlayerData secMediaPlayerData, Consumer consumer) {
        if (secMediaPlayerData == null) {
            return;
        }
        secMediaPlayerData.getMediaPlayers().values().forEach(consumer);
    }

    public final void addMediaFrame(MediaType mediaType, View view) {
        MediaType mediaType2;
        final View view2;
        MediaData mediaData;
        String str;
        Log.d("SecMediaHost", mediaType + " addMediaFrame");
        if (mediaType.getSupportCoverQuickPanelMedia()) {
            Context context = view.getContext();
            this.mCoverContext = context;
            LayoutInflater.from(context).inflate(R.layout.sec_media_carousel, (ViewGroup) view);
        } else {
            LayoutInflater.from(this.mContext).inflate(R.layout.sec_media_carousel, (ViewGroup) view);
        }
        this.mMediaFrames.put(mediaType, view);
        this.mMediaPlayerData.put(mediaType, (SecMediaPlayerData) this.mMediaPlayerDataProvider.get());
        ViewPagerHelper viewPagerHelper = this.mViewPagerHelper;
        ViewPager viewPager = viewPagerHelper.getViewPager(mediaType);
        if (viewPager == null) {
            return;
        }
        if (!mediaType.getSupportCarousel()) {
            ((CustomViewPager) viewPager).touchable = false;
        }
        viewPager.setAdapter(new ViewPagerHelper$createPageAdapter$1(viewPagerHelper, mediaType));
        if (mediaType.getSupportCarousel()) {
            int i = this.mPagerMargin;
            SecMediaHost$$ExternalSyntheticLambda0 secMediaHost$$ExternalSyntheticLambda0 = new SecMediaHost$$ExternalSyntheticLambda0(this, 0);
            SecMediaHost$$ExternalSyntheticLambda4 secMediaHost$$ExternalSyntheticLambda4 = new SecMediaHost$$ExternalSyntheticLambda4(this);
            SecMediaHost$$ExternalSyntheticLambda5 secMediaHost$$ExternalSyntheticLambda5 = new SecMediaHost$$ExternalSyntheticLambda5(this, 0);
            ConcurrentHashMap concurrentHashMap = this.mMediaPlayerData;
            Objects.requireNonNull(concurrentHashMap);
            mediaType2 = mediaType;
            view2 = view;
            this.mCarouselHelper = new CarouselHelper(view2, i, secMediaHost$$ExternalSyntheticLambda0, secMediaHost$$ExternalSyntheticLambda4, secMediaHost$$ExternalSyntheticLambda5, new SecMediaHost$$ExternalSyntheticLambda6(concurrentHashMap), mediaType2, viewPager);
        } else {
            mediaType2 = mediaType;
            view2 = view;
        }
        if (mediaType2.getSupportWidgetTimer()) {
            CoverMusicWidgetController coverMusicWidgetController = new CoverMusicWidgetController(new SecMediaHost$$ExternalSyntheticLambda7(this, 0), new SecMediaHost$$ExternalSyntheticLambda7(this, 2), this.mSubScreenManager, this.mWakefulnessLifeCycle);
            this.mWidgetController = coverMusicWidgetController;
            Log.d("CoverMusicWidgetController", "init");
            coverMusicWidgetController.enableWidget(false);
            coverMusicWidgetController.addVisibilityListenerConsumer.accept((MediaPanelVisibilityListener) coverMusicWidgetController.onPlayerVisibilityListener$delegate.getValue());
            coverMusicWidgetController.lifecycle.addObserver(coverMusicWidgetController.observer);
        }
        if (mediaType2.getSupportPlayLastSong()) {
            if (mediaType2 == MediaType.COVER_QS) {
                Context context2 = this.mContext;
                Context context3 = this.mCoverContext;
                QSCoverPlayLastSongHelper qSCoverPlayLastSongHelper = new QSCoverPlayLastSongHelper(view2, context3 != null ? context3 : context2, new SecMediaHost$$ExternalSyntheticLambda7(this, 0), new SecMediaHost$$ExternalSyntheticLambda7(this, 2), this.mBgColorHelper);
                this.mQSCoverPlayLastSongHelper = qSCoverPlayLastSongHelper;
                qSCoverPlayLastSongHelper.addVisibilityListenerConsumer.accept((MediaPanelVisibilityListener) qSCoverPlayLastSongHelper.onPlayerVisibilityListener$delegate.getValue());
                qSCoverPlayLastSongHelper.updateRestartViewVisibility();
                qSCoverPlayLastSongHelper.soundAssistantManager.addOnMediaKeyEventSessionChangedListener((SemSoundAssistantManager.OnMediaKeyEventSessionChangedListener) qSCoverPlayLastSongHelper.onMediaKeyEventSessionChangeListener$delegate.getValue());
                TextView textView = qSCoverPlayLastSongHelper.playLastSongText;
                if (textView != null) {
                    textView.setSelected(true);
                    textView.setPaintFlags(textView.getPaintFlags() | 192);
                }
            } else {
                PlayLastSongHelper playLastSongHelper = new PlayLastSongHelper(view2, this.mContext, new SecMediaHost$$ExternalSyntheticLambda7(this, 0), new SecMediaHost$$ExternalSyntheticLambda7(this, 2), this.mBgColorHelper);
                this.mPlayLastSongHelper = playLastSongHelper;
                playLastSongHelper.addVisibilityListenerConsumer.accept((MediaPanelVisibilityListener) playLastSongHelper.onPlayerVisibilityListener$delegate.getValue());
                playLastSongHelper.updateRestartViewVisibility();
                playLastSongHelper.soundAssistantManager.addOnMediaKeyEventSessionChangedListener((SemSoundAssistantManager.OnMediaKeyEventSessionChangedListener) playLastSongHelper.onMediaKeyEventSessionChangeListener$delegate.getValue());
            }
        }
        if (!mediaType2.getSupportRoundedCorner()) {
            IntStream.range(0, ((ViewGroup) view2).getChildCount()).mapToObj(new IntFunction() { // from class: com.android.systemui.media.SecMediaHost$$ExternalSyntheticLambda1
                @Override // java.util.function.IntFunction
                public final Object apply(int i2) {
                    return ((ViewGroup) view2).getChildAt(i2);
                }
            }).filter(new SecMediaHost$$ExternalSyntheticLambda2()).forEach(new SecMediaHost$$ExternalSyntheticLambda3(0));
        }
        MediaType mediaType3 = MediaType.QS;
        if (mediaType2 == mediaType3) {
            MediaDataFormat mediaDataFormat = this.mCurrentMediaData;
            if (mediaDataFormat == null) {
                Log.d("SecMediaHost", "addMediaFrame. There is no current media data, exit");
                return;
            } else {
                updateMediaPlayer(mediaDataFormat.key, mediaDataFormat.oldKey, mediaDataFormat.data, mediaType2);
                return;
            }
        }
        SecMediaPlayerData secMediaPlayerData = (SecMediaPlayerData) this.mMediaPlayerData.get(mediaType3);
        if (secMediaPlayerData == null || secMediaPlayerData.getSortedMediaPlayersSize() <= 0) {
            mediaData = null;
            str = null;
        } else {
            str = secMediaPlayerData.getMediaPlayerFromSortedMediaPlayers(0).mPlayerKey;
            mediaData = str != null ? (MediaData) secMediaPlayerData.m2627getMediaData().get(str) : null;
        }
        if (str == null || mediaData == null) {
            Log.d("SecMediaHost", "addMediaFrame. There is no current media data on QS, exit");
        } else {
            updateMediaPlayer(str, null, mediaData, mediaType2);
        }
    }

    public final void addOrUpdatePlayer(String str, String str2, MediaData mediaData, MediaType mediaType) {
        SecMediaPlayerData secMediaPlayerData;
        boolean z;
        final SecMediaControlPanel secMediaControlPanel;
        PagerAdapter adapter;
        String str3;
        String str4;
        String str5;
        MediaLogger mediaLogger;
        ViewPager viewPager;
        PagerAdapter adapter2;
        SecMediaControlPanel secMediaControlPanel2;
        MediaType mediaType2 = mediaType;
        int i = this.mContext.getResources().getConfiguration().orientation;
        if (this.mOrientation != i) {
            this.mOrientation = i;
            this.mPlayerNeedForceUpdate = true;
        }
        ViewPagerHelper viewPagerHelper = this.mViewPagerHelper;
        ViewPager viewPager2 = viewPagerHelper.getViewPager(mediaType2);
        if (viewPager2 == null || (secMediaPlayerData = (SecMediaPlayerData) this.mMediaPlayerData.get(mediaType2)) == null) {
            return;
        }
        int mediaPlayerNum = getMediaPlayerNum(mediaType2);
        if (((SecMediaControlPanel) secMediaPlayerData.getMediaPlayers().get(str2)) != null && (secMediaControlPanel2 = (SecMediaControlPanel) secMediaPlayerData.getMediaPlayers().remove(str2)) != null) {
            secMediaPlayerData.getMediaPlayers().put(str, secMediaControlPanel2);
        }
        SecMediaControlPanel secMediaControlPanel3 = (SecMediaControlPanel) secMediaPlayerData.getMediaPlayers().get(str);
        MediaLogger mediaLogger2 = this.mLogger;
        if (secMediaControlPanel3 == null) {
            secMediaControlPanel = (SecMediaControlPanel) this.mMediaControlPanelProvider.get();
            secMediaControlPanel.mType = mediaType2;
            if (!mediaType2.getSupportCoverQuickPanelMedia() || this.mCoverContext == null) {
                str3 = "] isPlaying[";
                str4 = "[";
                str5 = "MediaLogger";
                mediaLogger = mediaLogger2;
                mediaType2 = mediaType;
                secMediaControlPanel.attach(new SecPlayerViewHolder(this.mContext, viewPager2, false, mediaType2, this.mSettingsHelper));
            } else {
                str3 = "] isPlaying[";
                mediaLogger = mediaLogger2;
                str4 = "[";
                str5 = "MediaLogger";
                secMediaControlPanel.attach(new SecPlayerViewHolder(this.mCoverContext, viewPager2, false, mediaType2, this.mSettingsHelper));
                secMediaControlPanel.mCoverContext = this.mCoverContext;
                SubScreenQuickPanelWindowController$$ExternalSyntheticLambda15 subScreenQuickPanelWindowController$$ExternalSyntheticLambda15 = this.mCoverQSClickConsumer;
                if (subScreenQuickPanelWindowController$$ExternalSyntheticLambda15 != null) {
                    secMediaControlPanel.mCoverQSClickConsumer = subScreenQuickPanelWindowController$$ExternalSyntheticLambda15;
                }
                mediaType2 = mediaType;
            }
            QSMediaPlayerBar$$ExternalSyntheticLambda3 qSMediaPlayerBar$$ExternalSyntheticLambda3 = this.mBudsDetailOpenRunnable;
            QSMediaPlayerBar$$ExternalSyntheticLambda3 qSMediaPlayerBar$$ExternalSyntheticLambda32 = this.mBudsDetailCloseRunnable;
            if (secMediaControlPanel.mType.getSupportBudsButton()) {
                ImageButton imageButton = secMediaControlPanel.mViewHolder.budsButtonExpanded;
                if (imageButton == null) {
                    imageButton = null;
                }
                secMediaControlPanel.mBudsButtonExpanded = imageButton;
                secMediaControlPanel.mBudsDetailOpenRunnable = qSMediaPlayerBar$$ExternalSyntheticLambda3;
                secMediaControlPanel.mBudsDetailCloseRunnable = qSMediaPlayerBar$$ExternalSyntheticLambda32;
                if (secMediaControlPanel.mType.getSupportExpandable()) {
                    secMediaControlPanel.mBudsButtonExpanded.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.media.SecMediaControlPanel$$ExternalSyntheticLambda4
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            secMediaControlPanel.mBudsDetailOpenRunnable.run();
                            SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_QPNE_MEDIA_BUDS_BUTTON);
                        }
                    });
                    ((View) secMediaControlPanel.mBudsButtonExpanded.getParent()).setOnTouchListener(new SecMediaControlPanel$$ExternalSyntheticLambda5(secMediaControlPanel, 0));
                }
                secMediaControlPanel.updateBudsButton();
            }
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(secMediaPlayerData);
            secMediaControlPanel.mPlayerKey = str;
            secMediaControlPanel.mQSMediaPlayerBarCallback = anonymousClass1;
            secMediaControlPanel.updateBudsButton();
            secMediaControlPanel.bind(mediaData, str);
            secMediaControlPanel.setListening(this.mLocalListening);
            secMediaPlayerData.getMediaPlayers().put(str, secMediaControlPanel);
            if (secMediaControlPanel.isPlaying() || secMediaPlayerData.getSortedMediaPlayersSize() == 0) {
                secMediaPlayerData.getSortedMediaPlayers().add(0, secMediaControlPanel);
                z = true;
                viewPager = viewPagerHelper.getViewPager(mediaType2);
                if (viewPager != null) {
                    adapter2.notifyDataSetChanged();
                }
                String strName = mediaType2.name();
                boolean zIsPlaying = secMediaControlPanel.isPlaying();
                MediaLogWriter mediaLogWriter = ((MediaLoggerImpl) mediaLogger).writer;
                mediaLogWriter.getClass();
                LogLevel logLevel = LogLevel.DEBUG;
                MediaLogWriter$$ExternalSyntheticLambda0 mediaLogWriter$$ExternalSyntheticLambda0 = new MediaLogWriter$$ExternalSyntheticLambda0(1);
                LogBuffer logBuffer = mediaLogWriter.buffer;
                String str6 = str5;
                LogMessage logMessageObtain = logBuffer.obtain(str6, logLevel, mediaLogWriter$$ExternalSyntheticLambda0, null);
                LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
                logMessageImpl.str1 = str;
                logMessageImpl.bool1 = zIsPlaying;
                logBuffer.commit(logMessageObtain);
                Log.d(str6, str4 + strName + "] Media player added [" + str + str3 + zIsPlaying + "]");
            } else {
                secMediaPlayerData.getSortedMediaPlayers().add(secMediaControlPanel);
                z = false;
                viewPager = viewPagerHelper.getViewPager(mediaType2);
                if (viewPager != null && (adapter2 = viewPager.getAdapter()) != null) {
                    adapter2.notifyDataSetChanged();
                }
                String strName2 = mediaType2.name();
                boolean zIsPlaying2 = secMediaControlPanel.isPlaying();
                MediaLogWriter mediaLogWriter2 = ((MediaLoggerImpl) mediaLogger).writer;
                mediaLogWriter2.getClass();
                LogLevel logLevel2 = LogLevel.DEBUG;
                MediaLogWriter$$ExternalSyntheticLambda0 mediaLogWriter$$ExternalSyntheticLambda02 = new MediaLogWriter$$ExternalSyntheticLambda0(1);
                LogBuffer logBuffer2 = mediaLogWriter2.buffer;
                String str62 = str5;
                LogMessage logMessageObtain2 = logBuffer2.obtain(str62, logLevel2, mediaLogWriter$$ExternalSyntheticLambda02, null);
                LogMessageImpl logMessageImpl2 = (LogMessageImpl) logMessageObtain2;
                logMessageImpl2.str1 = str;
                logMessageImpl2.bool1 = zIsPlaying2;
                logBuffer2.commit(logMessageObtain2);
                Log.d(str62, str4 + strName2 + "] Media player added [" + str + str3 + zIsPlaying2 + "]");
            }
        } else {
            secMediaControlPanel3.bind(mediaData, str);
            if (!Objects.equals(secMediaControlPanel3.mPlayerKey, str)) {
                secMediaControlPanel3.mPlayerKey = str;
            }
            if (!secMediaControlPanel3.isPlaying() || secMediaPlayerData.getMediaPlayerFromSortedMediaPlayers(0) == secMediaControlPanel3) {
                z = false;
            } else {
                secMediaPlayerData.getSortedMediaPlayers().remove(secMediaControlPanel3);
                secMediaPlayerData.getSortedMediaPlayers().add(0, secMediaControlPanel3);
                z = true;
            }
            String strName3 = mediaType2.name();
            boolean zIsPlaying3 = secMediaControlPanel3.isPlaying();
            MediaLogWriter mediaLogWriter3 = ((MediaLoggerImpl) mediaLogger2).writer;
            mediaLogWriter3.getClass();
            LogLevel logLevel3 = LogLevel.DEBUG;
            MediaLogWriter$$ExternalSyntheticLambda0 mediaLogWriter$$ExternalSyntheticLambda03 = new MediaLogWriter$$ExternalSyntheticLambda0(0);
            LogBuffer logBuffer3 = mediaLogWriter3.buffer;
            LogMessage logMessageObtain3 = logBuffer3.obtain("MediaLogger", logLevel3, mediaLogWriter$$ExternalSyntheticLambda03, null);
            LogMessageImpl logMessageImpl3 = (LogMessageImpl) logMessageObtain3;
            logMessageImpl3.str1 = str;
            logMessageImpl3.bool1 = zIsPlaying3;
            logBuffer3.commit(logMessageObtain3);
            Log.d("MediaLogger", "[" + strName3 + "] Media player updated [" + str + "] isPlaying[" + zIsPlaying3 + "]");
            secMediaControlPanel = secMediaControlPanel3;
        }
        boolean z2 = z;
        if (mediaType2.getSupportCapsule()) {
            updateCapsule(z2, secMediaControlPanel);
        }
        if (mediaType2.getSupportOAChip()) {
            updateOAChip(z2, secMediaControlPanel);
        }
        int sortedMediaPlayersSize = secMediaPlayerData.getSortedMediaPlayersSize();
        ViewPager viewPager3 = viewPagerHelper.getViewPager(mediaType2);
        if (viewPager3 != null && (adapter = viewPager3.getAdapter()) != null) {
            adapter.notifyDataSetChanged();
        }
        if (sortedMediaPlayersSize > 0) {
            onMediaVisibilityChanged(Boolean.TRUE);
        }
        if (mediaType2.getSupportCarousel() && mediaPlayerNum != getMediaPlayerNum(mediaType2)) {
            this.mCarouselHelper.updatePageIndicatorNumberPages();
        }
        int currentPage = viewPagerHelper.getCurrentPage(mediaType2);
        if (z2 || currentPage == 0) {
            viewPagerHelper.setCurrentPage(0, false, mediaType2);
        }
        if (secMediaPlayerData.getMediaPlayerSize$1() != sortedMediaPlayersSize) {
            Log.d("SecMediaHost", "Size of players and number of views in carousel are out of sync");
        }
    }

    public final int getMediaPlayerNum(MediaType mediaType) {
        SecMediaPlayerData secMediaPlayerData = (SecMediaPlayerData) this.mMediaPlayerData.get(mediaType);
        if (secMediaPlayerData == null) {
            return 0;
        }
        return secMediaPlayerData.getSortedMediaPlayersSize();
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onConfigChanged(final Configuration configuration) {
        final boolean z;
        final boolean z2;
        final boolean z3;
        int i = configuration.orientation;
        if (this.mOrientation != i) {
            this.mOrientation = i;
            z = true;
        } else {
            z = false;
        }
        int i2 = configuration.screenLayout;
        if (this.mCurrentScreenLayout != i2) {
            this.mCurrentScreenLayout = i2;
            z2 = true;
        } else {
            z2 = false;
        }
        int layoutDirection = configuration.getLayoutDirection();
        if (this.mIsRTL != layoutDirection) {
            this.mIsRTL = layoutDirection;
            z3 = true;
        } else {
            z3 = false;
        }
        this.mMediaFrames.forEach(new BiConsumer(z, z2, configuration, z3) { // from class: com.android.systemui.media.SecMediaHost$$ExternalSyntheticLambda12
            public final /* synthetic */ boolean f$1;
            public final /* synthetic */ Configuration f$3;
            public final /* synthetic */ boolean f$4;

            {
                this.f$3 = configuration;
                this.f$4 = z3;
            }

            /* JADX WARN: Removed duplicated region for block: B:14:0x0088  */
            @Override // java.util.function.BiConsumer
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void accept(Object obj, Object obj2) {
                boolean z4;
                SecMediaHost secMediaHost = this.f$0;
                boolean z5 = this.f$1;
                Configuration configuration2 = this.f$3;
                boolean z6 = this.f$4;
                MediaType mediaType = (MediaType) obj;
                View view = (View) obj2;
                ViewPagerHelper viewPagerHelper = secMediaHost.mViewPagerHelper;
                ViewPager viewPager = viewPagerHelper.getViewPager(mediaType);
                if (viewPager == null) {
                    return;
                }
                SecMediaPlayerData secMediaPlayerData = (SecMediaPlayerData) secMediaHost.mMediaPlayerData.get(mediaType);
                int mediaPlayerSize$1 = secMediaPlayerData != null ? secMediaPlayerData.getMediaPlayerSize$1() : 0;
                if (mediaPlayerSize$1 <= 0) {
                    secMediaHost.onMediaVisibilityChanged(Boolean.FALSE);
                }
                String strName = mediaType.name();
                MediaLogWriter mediaLogWriter = ((MediaLoggerImpl) secMediaHost.mLogger).writer;
                mediaLogWriter.getClass();
                LogLevel logLevel = LogLevel.DEBUG;
                MediaLogWriter$$ExternalSyntheticLambda0 mediaLogWriter$$ExternalSyntheticLambda0 = new MediaLogWriter$$ExternalSyntheticLambda0(5);
                LogBuffer logBuffer = mediaLogWriter.buffer;
                LogMessage logMessageObtain = logBuffer.obtain("MediaLogger", logLevel, mediaLogWriter$$ExternalSyntheticLambda0, null);
                ((LogMessageImpl) logMessageObtain).int1 = mediaPlayerSize$1;
                logBuffer.commit(logMessageObtain);
                StringBuilder sb = new StringBuilder("[");
                sb.append(strName);
                sb.append("] Media config changed data size is [");
                EmergencyButtonController$$ExternalSyntheticOutline0.m(sb, mediaPlayerSize$1, "]", "MediaLogger");
                secMediaHost.mPagerMargin = secMediaHost.mContext.getResources().getDimensionPixelSize(R.dimen.sec_qs_media_side_padding);
                view.invalidateOutline();
                int currentItem = viewPager.getCurrentItem();
                if (secMediaHost.mPlayerNeedForceUpdate || z5) {
                    SecMediaPlayerData secMediaPlayerData2 = (SecMediaPlayerData) secMediaHost.mMediaPlayerData.get(mediaType);
                    SecMediaHost$$ExternalSyntheticLambda13 secMediaHost$$ExternalSyntheticLambda13 = new SecMediaHost$$ExternalSyntheticLambda13(secMediaHost, mediaType, 1);
                    if (secMediaPlayerData2 == null) {
                        z4 = z6;
                    } else {
                        ArrayList sortedMediaPlayers = secMediaPlayerData2.getSortedMediaPlayers();
                        int iMapCapacity = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(sortedMediaPlayers, 10));
                        if (iMapCapacity < 16) {
                            iMapCapacity = 16;
                        }
                        LinkedHashMap linkedHashMap = new LinkedHashMap(iMapCapacity);
                        int size = sortedMediaPlayers.size();
                        int i3 = 0;
                        while (i3 < size) {
                            Object obj3 = sortedMediaPlayers.get(i3);
                            i3++;
                            SecMediaControlPanel secMediaControlPanel = (SecMediaControlPanel) obj3;
                            Pair pair = new Pair(secMediaControlPanel.mPlayerKey, secMediaPlayerData2.m2627getMediaData().get(secMediaControlPanel.mPlayerKey));
                            linkedHashMap.put(pair.getFirst(), pair.getSecond());
                            z6 = z6;
                        }
                        z4 = z6;
                        linkedHashMap.entrySet().forEach(secMediaHost$$ExternalSyntheticLambda13);
                    }
                }
                SecMediaHost.iteratePlayers(secMediaPlayerData, new SecMediaHost$$ExternalSyntheticLambda17(configuration2, 0));
                if (z4) {
                    if (mediaType.getSupportCarousel()) {
                        CarouselHelper carouselHelper = secMediaHost.mCarouselHelper;
                        carouselHelper.indicator.setLayoutDirection(carouselHelper.isRTLSupplier.getAsInt());
                    }
                    viewPager.setAdapter(new ViewPagerHelper$createPageAdapter$1(viewPagerHelper, mediaType));
                    currentItem = (secMediaHost.getMediaPlayerNum(mediaType) - 1) - currentItem;
                }
                viewPager.setCurrentItem(currentItem);
            }
        });
    }

    public final void onMediaVisibilityChanged(Boolean bool) {
        this.mVisibilityListeners.forEach(new SecMediaHost$$ExternalSyntheticLambda17(bool));
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public final void onStateChanged(int i) {
        if (this.mBarState == i) {
            return;
        }
        this.mBarState = i;
        boolean z = this.mLocalListening && i != 1;
        this.mLocalListening = z;
        setListening(z, MediaType.QS);
        if (this.mBarState == 1) {
            setListening(false, MediaType.ENR);
        }
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onUiModeChanged() {
        this.mMediaPlayerData.values().forEach(new SecMediaHost$$ExternalSyntheticLambda7(this, 1));
    }

    public final void removeMediaFrame(MediaType mediaType) {
        CoverMusicWidgetController coverMusicWidgetController;
        Log.d("SecMediaHost", mediaType + " removeMediaFrame");
        if (mediaType.getSupportCoverQuickPanelMedia()) {
            this.mCoverContext = null;
        }
        SecMediaPlayerData secMediaPlayerData = (SecMediaPlayerData) this.mMediaPlayerData.get(mediaType);
        SecMediaHost$$ExternalSyntheticLambda13 secMediaHost$$ExternalSyntheticLambda13 = new SecMediaHost$$ExternalSyntheticLambda13(this, mediaType, 0);
        if (secMediaPlayerData != null) {
            secMediaPlayerData.getMediaData().forEach(secMediaHost$$ExternalSyntheticLambda13);
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
            if (mediaType.getSupportCoverQuickPanelMedia()) {
                QSCoverPlayLastSongHelper qSCoverPlayLastSongHelper = this.mQSCoverPlayLastSongHelper;
                if (qSCoverPlayLastSongHelper != null) {
                    TextView textView = qSCoverPlayLastSongHelper.playLastSongText;
                    if (textView != null) {
                        textView.setSelected(false);
                    }
                    qSCoverPlayLastSongHelper.soundAssistantManager.removeOnMediaKeyEventSessionChangedListener((SemSoundAssistantManager.OnMediaKeyEventSessionChangedListener) qSCoverPlayLastSongHelper.onMediaKeyEventSessionChangeListener$delegate.getValue());
                    qSCoverPlayLastSongHelper.removeVisibilityListenerConsumer.accept((MediaPanelVisibilityListener) qSCoverPlayLastSongHelper.onPlayerVisibilityListener$delegate.getValue());
                    this.mQSCoverPlayLastSongHelper = null;
                }
            } else {
                PlayLastSongHelper playLastSongHelper = this.mPlayLastSongHelper;
                if (playLastSongHelper != null) {
                    playLastSongHelper.soundAssistantManager.removeOnMediaKeyEventSessionChangedListener((SemSoundAssistantManager.OnMediaKeyEventSessionChangedListener) playLastSongHelper.onMediaKeyEventSessionChangeListener$delegate.getValue());
                    playLastSongHelper.removeVisibilityListenerConsumer.accept((MediaPanelVisibilityListener) playLastSongHelper.onPlayerVisibilityListener$delegate.getValue());
                    this.mPlayLastSongHelper = null;
                }
            }
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
        if (mediaType.getSupportCoverQuickPanelMedia() && secMediaControlPanel != null) {
            secMediaControlPanel.mCoverContext = null;
        }
        MediaLogger mediaLogger = this.mLogger;
        if (secMediaControlPanel == null) {
            String strName = mediaType.name();
            MediaLogWriter mediaLogWriter = ((MediaLoggerImpl) mediaLogger).writer;
            mediaLogWriter.getClass();
            LogLevel logLevel = LogLevel.DEBUG;
            MediaLogWriter$$ExternalSyntheticLambda0 mediaLogWriter$$ExternalSyntheticLambda0 = new MediaLogWriter$$ExternalSyntheticLambda0(3);
            LogBuffer logBuffer = mediaLogWriter.buffer;
            LogMessage logMessageObtain = logBuffer.obtain("MediaLogger", logLevel, mediaLogWriter$$ExternalSyntheticLambda0, null);
            ((LogMessageImpl) logMessageObtain).str1 = str;
            logBuffer.commit(logMessageObtain);
            StringBuilder sb = new StringBuilder("[");
            sb.append(strName);
            sb.append("] Media player removed error [");
            ExifInterface$$ExternalSyntheticOutline0.m(sb, str, "]", "MediaLogger");
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
        String strName2 = mediaType.name();
        MediaLogWriter mediaLogWriter2 = ((MediaLoggerImpl) mediaLogger).writer;
        mediaLogWriter2.getClass();
        LogLevel logLevel2 = LogLevel.DEBUG;
        MediaLogWriter$$ExternalSyntheticLambda0 mediaLogWriter$$ExternalSyntheticLambda02 = new MediaLogWriter$$ExternalSyntheticLambda0(2);
        LogBuffer logBuffer2 = mediaLogWriter2.buffer;
        LogMessage logMessageObtain2 = logBuffer2.obtain("MediaLogger", logLevel2, mediaLogWriter$$ExternalSyntheticLambda02, null);
        ((LogMessageImpl) logMessageObtain2).str1 = str;
        logBuffer2.commit(logMessageObtain2);
        StringBuilder sb2 = new StringBuilder("[");
        sb2.append(strName2);
        sb2.append("] Media player removed [");
        ExifInterface$$ExternalSyntheticOutline0.m(sb2, str, "]", "MediaLogger");
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x001d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setListening(boolean z, MediaType mediaType) {
        final boolean z2;
        if (mediaType.getSupportExpandable()) {
            MediaType mediaType2 = MediaType.QS;
            if (mediaType == mediaType2 && this.mLocalListening == z) {
                return;
            }
            if (z) {
                z2 = this.mStatusBarStateController.getState() != 1;
            }
            if (getMediaPlayerNum(mediaType) > 0) {
                iteratePlayers((SecMediaPlayerData) this.mMediaPlayerData.get(mediaType), new Consumer() { // from class: com.android.systemui.media.SecMediaHost$$ExternalSyntheticLambda9
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((SecMediaControlPanel) obj).setListening(z2);
                    }
                });
                if (!z2) {
                    this.mViewPagerHelper.setCurrentPage(0, false, mediaType);
                }
            }
            if (mediaType == mediaType2) {
                this.mLocalListening = z2;
            }
        }
    }

    public final void updateCapsule(boolean z, SecMediaControlPanel secMediaControlPanel) {
        CoverMusicCapsuleController coverMusicCapsuleController;
        MediaController mediaController;
        EmergencyButtonController$$ExternalSyntheticOutline0.m("Change Cover Played State : ", "SecMediaHost", z);
        if (z) {
            iteratePlayers((SecMediaPlayerData) this.mMediaPlayerData.get(MediaType.COVER), new SecMediaHost$$ExternalSyntheticLambda3(1));
            if (secMediaControlPanel.mType.getSupportCapsule() && true != secMediaControlPanel.mIsPlayerCoverPlayed) {
                secMediaControlPanel.mIsPlayerCoverPlayed = true;
            }
            if (!secMediaControlPanel.mIsPlayerCoverPlayed || (coverMusicCapsuleController = secMediaControlPanel.mCoverMusicCapsuleController) == null || (mediaController = secMediaControlPanel.mController) == null) {
                return;
            }
            coverMusicCapsuleController.updateEqualizerState(mediaController.getPlaybackState());
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:44:0x0135  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateMediaPlayer(String str, String str2, MediaData mediaData, MediaType mediaType) {
        String string;
        BarController.AnonymousClass3 anonymousClass3;
        Boolean bool;
        CharSequence charSequenceSubSequence = mediaData.song;
        if (charSequenceSubSequence != null && charSequenceSubSequence.length() > 5) {
            charSequenceSubSequence = charSequenceSubSequence.subSequence(0, 4);
        }
        boolean z = mediaData.active;
        final String callers = Debug.getCallers(8, "  ");
        MediaLoggerImpl mediaLoggerImpl = (MediaLoggerImpl) this.mLogger;
        if (charSequenceSubSequence != null) {
            mediaLoggerImpl.getClass();
            string = charSequenceSubSequence.toString();
        } else {
            string = null;
        }
        MediaLogWriter mediaLogWriter = mediaLoggerImpl.writer;
        mediaLogWriter.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        Function1 function1 = new Function1() { // from class: com.android.systemui.log.MediaLogWriter$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                String str1 = logMessage.getStr1();
                String str22 = logMessage.getStr2();
                String str3 = logMessage.getStr3();
                boolean bool1 = logMessage.getBool1();
                StringBuilder sbM = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m(" Media data loaded key[", str1, "] oldKey[", str22, "] title[");
                sbM.append(str3);
                sbM.append("] active?[");
                sbM.append(bool1);
                sbM.append("]\n callStack[");
                return TransitionKt$$ExternalSyntheticOutline0.m(sbM, callers, "]");
            }
        };
        LogBuffer logBuffer = mediaLogWriter.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("MediaLogger", logLevel, function1, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.str1 = str;
        logMessageImpl.str2 = str2;
        logMessageImpl.str3 = string;
        logMessageImpl.bool1 = z;
        logBuffer.commit(logMessageObtain);
        Log.d("MediaLogger", "Media data loaded key[" + str + "] oldKey[" + str2 + "] title[" + ((Object) charSequenceSubSequence) + "] isActive[" + z + "]");
        SecMediaPlayerData secMediaPlayerData = (SecMediaPlayerData) this.mMediaPlayerData.get(mediaType);
        if (secMediaPlayerData != null && str2 != null) {
            secMediaPlayerData.m2627getMediaData().remove(str2);
        }
        if (mediaData.active || Utils.useMediaResumption(this.mContext)) {
            if (secMediaPlayerData != null) {
                secMediaPlayerData.m2627getMediaData().put(str, mediaData);
            }
            addOrUpdatePlayer(str, str2, mediaData, mediaType);
        } else {
            this.mMediaDataListener.onMediaDataRemoved(str, false);
        }
        if (mediaType.getSupportCarousel() && secMediaPlayerData != null && secMediaPlayerData.getSortedMediaPlayersSize() > 0) {
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
            boolean zBooleanValue = bool.booleanValue();
            Handler handler = coverMusicWidgetController.mediaPauseTimerHandler;
            if (zBooleanValue) {
                Log.d("CoverMusicWidgetController", "callback has been removed");
                coverMusicWidgetController.enableWidget(true);
                coverMusicWidgetController.pauseTimerStartedTime = 0L;
                handler.removeCallbacksAndMessages(null);
            } else if (!zBooleanValue) {
                CoverMusicWidgetController$widgetDisableRunnable$1 coverMusicWidgetController$widgetDisableRunnable$1 = coverMusicWidgetController.widgetDisableRunnable;
                if (handler.hasCallbacks(coverMusicWidgetController$widgetDisableRunnable$1)) {
                    Log.d("CoverMusicWidgetController", "is not playing but already has callback");
                } else {
                    Log.d("CoverMusicWidgetController", "callback has been added");
                    coverMusicWidgetController.pauseTimerStartedTime = System.currentTimeMillis();
                    handler.postDelayed(coverMusicWidgetController$widgetDisableRunnable$1, 120000L);
                }
            }
        }
        BarController.AnonymousClass4 anonymousClass4 = this.mMediaBarCallback;
        if (anonymousClass4 == null || (anonymousClass3 = BarController.this.mBarListener) == null) {
            return;
        }
        QSImpl$$ExternalSyntheticLambda2 qSImpl$$ExternalSyntheticLambda2 = BarController.this.mQSLastExpansionInitializer;
        if (qSImpl$$ExternalSyntheticLambda2 != null) {
            qSImpl$$ExternalSyntheticLambda2.run();
        }
        anonymousClass3.val$animatorRunner.run();
    }

    public final void updateOAChip(boolean z, SecMediaControlPanel secMediaControlPanel) {
        OAMusicChipController oAMusicChipController;
        MediaController mediaController;
        EmergencyButtonController$$ExternalSyntheticOutline0.m("Change OA Played State : ", "SecMediaHost", z);
        if (z) {
            iteratePlayers((SecMediaPlayerData) this.mMediaPlayerData.get(MediaType.OA), new SecMediaHost$$ExternalSyntheticLambda3(2));
            if (secMediaControlPanel.mType.getSupportOAChip() && true != secMediaControlPanel.mIsPlayerOAPlayed) {
                secMediaControlPanel.mIsPlayerOAPlayed = true;
            }
            if (!secMediaControlPanel.mIsPlayerOAPlayed || (oAMusicChipController = secMediaControlPanel.mOAMusicChipController) == null || (mediaController = secMediaControlPanel.mController) == null) {
                return;
            }
            oAMusicChipController.updatePlaybackState(mediaController.getPlaybackState());
        }
    }
}
