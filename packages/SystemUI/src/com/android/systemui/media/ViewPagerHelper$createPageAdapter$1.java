package com.android.systemui.media;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import java.util.ArrayList;
import java.util.function.Function;
import kotlin.Pair;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.TransformingIndexedSequence;
import kotlin.sequences.TransformingIndexedSequence.AnonymousClass1;

/* loaded from: classes2.dex */
public final class ViewPagerHelper$createPageAdapter$1 extends PagerAdapter {
    public final /* synthetic */ MediaType $type;
    public final /* synthetic */ ViewPagerHelper this$0;

    public ViewPagerHelper$createPageAdapter$1(ViewPagerHelper viewPagerHelper, MediaType mediaType) {
        this.this$0 = viewPagerHelper;
        this.$type = mediaType;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public final void destroyItem(ViewPager viewPager, int i, Object obj) {
        viewPager.removeView((View) obj);
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public final int getCount() {
        int i = ViewPagerHelper.$r8$clinit;
        return this.this$0.getPlayersCount(this.$type);
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public final int getItemPosition(Object obj) {
        ArrayList sortedMediaPlayers;
        Object next;
        ViewPagerHelper viewPagerHelper = this.this$0;
        Function function = viewPagerHelper.mediaPlayerDataFunction;
        MediaType mediaType = this.$type;
        SecMediaPlayerData secMediaPlayerData = (SecMediaPlayerData) function.apply(mediaType);
        if (secMediaPlayerData == null || (sortedMediaPlayers = secMediaPlayerData.getSortedMediaPlayers()) == null) {
            return -2;
        }
        TransformingIndexedSequence.AnonymousClass1 anonymousClass1 = new TransformingIndexedSequence(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(sortedMediaPlayers), new ViewPagerHelper$createPageAdapter$1$$ExternalSyntheticLambda0()).new AnonymousClass1();
        while (true) {
            if (!anonymousClass1.iterator.hasNext()) {
                next = null;
                break;
            }
            next = anonymousClass1.next();
            if (Intrinsics.areEqual(((Pair) next).getSecond(), obj)) {
                break;
            }
        }
        Pair pair = (Pair) next;
        if (pair == null) {
            return -2;
        }
        int iIntValue = ((Number) pair.getFirst()).intValue();
        int i = ViewPagerHelper.$r8$clinit;
        return viewPagerHelper.isRTLSupplier.getAsInt() == 1 ? (viewPagerHelper.getPlayersCount(mediaType) - 1) - iIntValue : iIntValue;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public final Object instantiateItem(ViewPager viewPager, int i) {
        View view;
        SecMediaControlPanel mediaPlayerFromSortedMediaPlayers;
        int i2 = ViewPagerHelper.$r8$clinit;
        ViewPagerHelper viewPagerHelper = this.this$0;
        int asInt = viewPagerHelper.isRTLSupplier.getAsInt();
        MediaType mediaType = this.$type;
        if (asInt == 1) {
            i = (viewPagerHelper.getPlayersCount(mediaType) - 1) - i;
        }
        SecMediaPlayerData secMediaPlayerData = (SecMediaPlayerData) viewPagerHelper.mediaPlayerDataFunction.apply(mediaType);
        if (secMediaPlayerData == null || (mediaPlayerFromSortedMediaPlayers = secMediaPlayerData.getMediaPlayerFromSortedMediaPlayers(i)) == null || (view = mediaPlayerFromSortedMediaPlayers.mViewHolder.playerView) == null) {
            view = null;
        }
        if (view == null) {
            super.instantiateItem(viewPager, i);
            throw null;
        }
        ViewParent parent = view.getParent();
        if (parent != null) {
            Log.d("ViewPagerHelper", "- instantiateItem - parent: " + parent + ", container: " + viewPager);
            viewPager.removeView(view);
            if (parent != viewPager) {
                ((ViewGroup) parent).removeView(view);
            }
        }
        viewPager.addView(view);
        return view;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public final boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }
}
