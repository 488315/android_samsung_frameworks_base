package com.android.systemui.media;

import android.content.Context;
import android.content.res.Configuration;
import android.view.View;
import androidx.core.graphics.ColorUtils;
import androidx.viewpager.widget.ViewPager;
import com.android.systemui.R;
import com.android.systemui.log.MediaLoggerImpl;
import com.android.systemui.media.SecMediaHost;
import com.android.systemui.qs.SecPageIndicator;
import com.android.systemui.qs.bar.BarController;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.function.BiConsumer;
import kotlin.Pair;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.MapsKt__MapsJVMKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class SecMediaHost$$ExternalSyntheticLambda0 implements BiConsumer {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ SecMediaHost$$ExternalSyntheticLambda0(SecMediaHost.AnonymousClass2 anonymousClass2, String str) {
        this.f$0 = anonymousClass2;
        this.f$1 = str;
    }

    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        boolean z;
        BarController.AnonymousClass3 anonymousClass3;
        MediaDataFormat mediaDataFormat;
        PlayLastSongHelper playLastSongHelper;
        switch (this.$r8$classId) {
            case 0:
                SecMediaHost secMediaHost = (SecMediaHost) this.f$0;
                Configuration configuration = (Configuration) this.f$1;
                MediaType mediaType = (MediaType) obj;
                View view = (View) obj2;
                ViewPagerHelper viewPagerHelper = secMediaHost.mViewPagerHelper;
                ViewPager viewPager = viewPagerHelper.getViewPager(mediaType);
                if (viewPager != null) {
                    SecMediaPlayerData secMediaPlayerData = (SecMediaPlayerData) secMediaHost.mMediaPlayerData.get(mediaType);
                    int mediaPlayerSize$1 = secMediaPlayerData != null ? secMediaPlayerData.getMediaPlayerSize$1() : 0;
                    if (mediaPlayerSize$1 <= 0) {
                        secMediaHost.onMediaVisibilityChanged(Boolean.FALSE);
                    }
                    ((MediaLoggerImpl) secMediaHost.mLogger).onConfigChanged(mediaPlayerSize$1);
                    secMediaHost.updateResources$2();
                    view.invalidateOutline();
                    int i = configuration.orientation;
                    if (secMediaHost.mOrientation != i || secMediaHost.mPlayerNeedForceUpdate) {
                        secMediaHost.mOrientation = i;
                        SecMediaPlayerData secMediaPlayerData2 = (SecMediaPlayerData) secMediaHost.mMediaPlayerData.get(mediaType);
                        SecMediaHost$$ExternalSyntheticLambda15 secMediaHost$$ExternalSyntheticLambda15 = new SecMediaHost$$ExternalSyntheticLambda15(secMediaHost, mediaType, 1);
                        if (secMediaPlayerData2 != null) {
                            ArrayList<SecMediaControlPanel> sortedMediaPlayers = secMediaPlayerData2.getSortedMediaPlayers();
                            int mapCapacity = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(sortedMediaPlayers, 10));
                            if (mapCapacity < 16) {
                                mapCapacity = 16;
                            }
                            LinkedHashMap linkedHashMap = new LinkedHashMap(mapCapacity);
                            for (SecMediaControlPanel secMediaControlPanel : sortedMediaPlayers) {
                                Pair pair = new Pair(secMediaControlPanel.mPlayerKey, secMediaPlayerData2.getMediaData().get(secMediaControlPanel.mPlayerKey));
                                linkedHashMap.put(pair.getFirst(), pair.getSecond());
                            }
                            linkedHashMap.entrySet().forEach(secMediaHost$$ExternalSyntheticLambda15);
                        }
                    }
                    SecMediaHost.iteratePlayers(secMediaPlayerData, new SecMediaHost$$ExternalSyntheticLambda16(configuration));
                    int layoutDirection = configuration.getLayoutDirection();
                    if (secMediaHost.mIsRTL != layoutDirection) {
                        secMediaHost.mIsRTL = layoutDirection;
                        z = true;
                    } else {
                        z = false;
                    }
                    if (z) {
                        if (mediaType.getSupportCarousel()) {
                            CarouselHelper carouselHelper = secMediaHost.mCarouselHelper;
                            carouselHelper.indicator.setLayoutDirection(carouselHelper.isRTLSupplier.getAsInt());
                        }
                        viewPager.setAdapter(new ViewPagerHelper$createPageAdapter$1(mediaType, viewPagerHelper));
                    }
                    int currentItem = viewPager.getCurrentItem();
                    if (z && secMediaHost.mIsRTL != 1) {
                        currentItem = (secMediaHost.getMediaPlayerNum(mediaType) - 1) - currentItem;
                    }
                    viewPagerHelper.setCurrentPage(currentItem, false, mediaType);
                    break;
                }
                break;
            default:
                SecMediaHost.AnonymousClass2 anonymousClass2 = (SecMediaHost.AnonymousClass2) this.f$0;
                String str = (String) this.f$1;
                MediaType mediaType2 = (MediaType) obj;
                SecMediaPlayerData secMediaPlayerData3 = (SecMediaPlayerData) obj2;
                anonymousClass2.getClass();
                boolean supportPlayLastSong = mediaType2.getSupportPlayLastSong();
                SecMediaHost secMediaHost2 = SecMediaHost.this;
                if (supportPlayLastSong && (mediaDataFormat = secMediaHost2.mCurrentMediaData) != null && (playLastSongHelper = secMediaHost2.mPlayLastSongHelper) != null) {
                    playLastSongHelper.lastMediaPlayerKey = mediaDataFormat.data.packageName;
                }
                secMediaPlayerData3.getMediaData().remove(str);
                secMediaHost2.removePlayer(str, mediaType2);
                int mediaDataSize$1 = secMediaPlayerData3.getMediaDataSize$1();
                if (mediaDataSize$1 == 0) {
                    secMediaHost2.onMediaVisibilityChanged(Boolean.FALSE);
                } else {
                    ViewPagerHelper viewPagerHelper2 = secMediaHost2.mViewPagerHelper;
                    if (viewPagerHelper2.getCurrentPage(mediaType2) > mediaDataSize$1) {
                        viewPagerHelper2.setCurrentPage(mediaDataSize$1, true, mediaType2);
                    }
                    if (mediaType2.getSupportCapsule()) {
                        secMediaHost2.updateCapsule(true, (SecMediaControlPanel) secMediaPlayerData3.getSortedMediaPlayers().get(0));
                    }
                    if (mediaType2.getSupportCarousel()) {
                        CarouselHelper carouselHelper2 = secMediaHost2.mCarouselHelper;
                        int color = ((Context) carouselHelper2.contextSupplier.get()).getColor(R.color.qs_page_indicator_tint_color_selected);
                        int alphaComponent = ColorUtils.setAlphaComponent(color, 180);
                        SecPageIndicator secPageIndicator = carouselHelper2.indicator;
                        secPageIndicator.mSelectedColor = color;
                        secPageIndicator.mUnselectedColor = alphaComponent;
                        secMediaHost2.mCarouselHelper.updatePageIndicatorNumberPages();
                        secMediaHost2.mCarouselHelper.indicator.reset(viewPagerHelper2.getCurrentPage(mediaType2));
                    }
                }
                if (mediaType2.getSupportCarousel()) {
                    CarouselHelper carouselHelper3 = secMediaHost2.mCarouselHelper;
                    carouselHelper3.indicator.setVisibility(((Number) carouselHelper3.getNumberOfPlayersFunction.apply(Boolean.TRUE, carouselHelper3.type)).intValue() <= 1 ? 8 : 0);
                }
                BarController.AnonymousClass4 anonymousClass4 = secMediaHost2.mMediaBarCallback;
                if (anonymousClass4 != null && (anonymousClass3 = BarController.this.mBarListener) != null) {
                    Runnable runnable = BarController.this.mQSLastExpansionInitializer;
                    if (runnable != null) {
                        runnable.run();
                    }
                    anonymousClass3.val$animatorRunner.run();
                    break;
                }
                break;
        }
    }

    public /* synthetic */ SecMediaHost$$ExternalSyntheticLambda0(SecMediaHost secMediaHost, Configuration configuration) {
        this.f$0 = secMediaHost;
        this.f$1 = configuration;
    }
}
