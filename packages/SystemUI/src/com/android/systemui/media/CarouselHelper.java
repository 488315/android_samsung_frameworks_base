package com.android.systemui.media;

import android.content.Context;
import android.view.View;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.viewpager.widget.ViewPager;
import com.android.systemui.R;
import com.android.systemui.qs.SecPageIndicator;
import com.android.systemui.util.SystemUIAnalytics;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class CarouselHelper {
    public final Supplier contextSupplier;
    public final BiFunction getNumberOfPlayersFunction;
    public final SecPageIndicator indicator;
    public final IntSupplier isRTLSupplier;
    public final Function mediaPlayerDataFunction;
    public float pageIndicatorPosition;
    public final MediaType type;
    public boolean userTouch;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public CarouselHelper(View view, int i, Supplier<Context> supplier, BiFunction<Boolean, MediaType, Integer> biFunction, IntSupplier intSupplier, Function<MediaType, SecMediaPlayerData> function, MediaType mediaType, ViewPager viewPager) {
        this.contextSupplier = supplier;
        this.getNumberOfPlayersFunction = biFunction;
        this.isRTLSupplier = intSupplier;
        this.mediaPlayerDataFunction = function;
        this.type = mediaType;
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() { // from class: com.android.systemui.media.CarouselHelper$createOnPageChangeListener$1
            @Override // androidx.viewpager.widget.ViewPager.SimpleOnPageChangeListener, androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public final void onPageScrollStateChanged(int i2) {
                CarouselHelper carouselHelper = CarouselHelper.this;
                if (i2 == 0) {
                    carouselHelper.userTouch = false;
                } else {
                    if (i2 != 1) {
                        return;
                    }
                    carouselHelper.userTouch = true;
                }
            }

            @Override // androidx.viewpager.widget.ViewPager.SimpleOnPageChangeListener, androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public final void onPageScrolled(float f, int i2) {
                float f2 = i2 + f;
                CarouselHelper carouselHelper = CarouselHelper.this;
                carouselHelper.pageIndicatorPosition = f2;
                carouselHelper.indicator.setLocation(f2);
            }

            @Override // androidx.viewpager.widget.ViewPager.SimpleOnPageChangeListener, androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public final void onPageSelected(int i2) {
                ListPopupWindow$$ExternalSyntheticOutline0.m(i2, "onPageSelected position=", "CarouselHelper");
                CarouselHelper carouselHelper = CarouselHelper.this;
                if (carouselHelper.userTouch) {
                    SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_QPNE_SWIPE_MEDIA);
                }
                SecMediaPlayerData secMediaPlayerData = (SecMediaPlayerData) carouselHelper.mediaPlayerDataFunction.apply(carouselHelper.type);
                if (secMediaPlayerData == null) {
                    return;
                }
                secMediaPlayerData.currentPosition = i2;
            }
        });
        viewPager.setPageMargin(i);
        SecPageIndicator secPageIndicator = (SecPageIndicator) view.requireViewById(R.id.sec_media_page_indicator);
        secPageIndicator.setLocation(this.pageIndicatorPosition);
        secPageIndicator.mSelectedColorResId = R.color.qs_page_indicator_tint_color_selected;
        secPageIndicator.mUnselectedColorResId = R.color.sec_qs_page_indicator_tint_color_unselected;
        int color = secPageIndicator.mContext.getResources().getColor(secPageIndicator.mSelectedColorResId);
        int color2 = secPageIndicator.mContext.getResources().getColor(secPageIndicator.mUnselectedColorResId);
        secPageIndicator.mSelectedColor = color;
        secPageIndicator.mUnselectedColor = color2;
        this.indicator = secPageIndicator;
        updatePageIndicatorNumberPages();
    }

    public final void updatePageIndicatorNumberPages() {
        this.indicator.setNumPages(((Number) this.getNumberOfPlayersFunction.apply(Boolean.TRUE, this.type)).intValue());
    }
}
