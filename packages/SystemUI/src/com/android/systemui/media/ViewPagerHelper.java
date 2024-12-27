package com.android.systemui.media;

import android.view.View;
import androidx.viewpager.widget.ViewPager;
import com.android.systemui.R;
import java.util.HashMap;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class ViewPagerHelper {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final BiFunction getNumberOfPlayersFunction;
    public final IntSupplier isRTLSupplier;
    public final Supplier mediaFramesSupplier;
    public final Function mediaPlayerDataFunction;

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

    public ViewPagerHelper(BiFunction<Boolean, MediaType, Integer> biFunction, IntSupplier intSupplier, Supplier<HashMap<MediaType, View>> supplier, Function<MediaType, SecMediaPlayerData> function) {
        this.getNumberOfPlayersFunction = biFunction;
        this.isRTLSupplier = intSupplier;
        this.mediaFramesSupplier = supplier;
        this.mediaPlayerDataFunction = function;
    }

    public final int getCurrentPage(MediaType mediaType) {
        ViewPager viewPager = getViewPager(mediaType);
        if (viewPager != null) {
            return this.isRTLSupplier.getAsInt() == 1 ? (getPlayersCount(mediaType) - 1) - viewPager.getCurrentItem() : viewPager.getCurrentItem();
        }
        return 0;
    }

    public final int getPlayersCount(MediaType mediaType) {
        return ((Number) this.getNumberOfPlayersFunction.apply(Boolean.FALSE, mediaType)).intValue();
    }

    public final ViewPager getViewPager(MediaType mediaType) {
        View view;
        HashMap hashMap = (HashMap) this.mediaFramesSupplier.get();
        if (hashMap.isEmpty() || (view = (View) hashMap.get(mediaType)) == null) {
            return null;
        }
        return (ViewPager) view.findViewById(R.id.sec_media_carousel_view_pager);
    }

    public final void setCurrentPage(int i, boolean z, MediaType mediaType) {
        if (this.isRTLSupplier.getAsInt() == 1) {
            i = (getPlayersCount(mediaType) - 1) - i;
        }
        ViewPager viewPager = getViewPager(mediaType);
        if (viewPager != null) {
            viewPager.setCurrentItem(i, z);
        }
    }
}
