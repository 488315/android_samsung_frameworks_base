package com.android.systemui.media;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.viewpager.widget.PagerAdapter;
import java.util.ArrayList;
import kotlin.Pair;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.TransformingIndexedSequence;
import kotlin.sequences.TransformingIndexedSequence$iterator$1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ViewPagerHelper$createPageAdapter$1 extends PagerAdapter {
    public final /* synthetic */ MediaType $type;
    public final /* synthetic */ ViewPagerHelper this$0;

    public ViewPagerHelper$createPageAdapter$1(MediaType mediaType, ViewPagerHelper viewPagerHelper) {
        this.$type = mediaType;
        this.this$0 = viewPagerHelper;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public final void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        viewGroup.removeView((View) obj);
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public final int getCount() {
        int i = ViewPagerHelper.$r8$clinit;
        ViewPagerHelper viewPagerHelper = this.this$0;
        MediaType mediaType = this.$type;
        int playersCount = viewPagerHelper.getPlayersCount(mediaType);
        if (!mediaType.getSupportCarousel() && 1 <= playersCount) {
            return 1;
        }
        return playersCount;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public final int getItemPosition(Object obj) {
        ArrayList sortedMediaPlayers;
        Object obj2;
        ViewPagerHelper viewPagerHelper = this.this$0;
        SecMediaPlayerData secMediaPlayerData = (SecMediaPlayerData) viewPagerHelper.mediaPlayerDataFunction.apply(this.$type);
        if (secMediaPlayerData != null && (sortedMediaPlayers = secMediaPlayerData.getSortedMediaPlayers()) != null) {
            TransformingIndexedSequence$iterator$1 transformingIndexedSequence$iterator$1 = new TransformingIndexedSequence$iterator$1(new TransformingIndexedSequence(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(sortedMediaPlayers), new Function2() { // from class: com.android.systemui.media.ViewPagerHelper$createPageAdapter$1$getItemPosition$1
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj3, Object obj4) {
                    Integer valueOf = Integer.valueOf(((Number) obj3).intValue());
                    View view = ((SecMediaControlPanel) obj4).mViewHolder.playerView;
                    if (view == null) {
                        view = null;
                    }
                    return new Pair(valueOf, view);
                }
            }));
            while (true) {
                if (!transformingIndexedSequence$iterator$1.hasNext()) {
                    obj2 = null;
                    break;
                }
                obj2 = transformingIndexedSequence$iterator$1.next();
                if (Intrinsics.areEqual(((Pair) obj2).getSecond(), obj)) {
                    break;
                }
            }
            Pair pair = (Pair) obj2;
            if (pair != null) {
                int intValue = ((Number) pair.getFirst()).intValue();
                int i = ViewPagerHelper.$r8$clinit;
                return viewPagerHelper.isRTLSupplier.getAsInt() == 1 ? (viewPagerHelper.getPlayersCount(r4) - 1) - intValue : intValue;
            }
        }
        return -2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x0087, code lost:
    
        if (r0 != null) goto L28;
     */
    @Override // androidx.viewpager.widget.PagerAdapter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object instantiateItem(ViewGroup viewGroup, int i) {
        View view;
        int i2 = ViewPagerHelper.$r8$clinit;
        ViewPagerHelper viewPagerHelper = this.this$0;
        boolean z = viewPagerHelper.isRTLSupplier.getAsInt() == 1;
        MediaType mediaType = this.$type;
        if (z) {
            i = (viewPagerHelper.getPlayersCount(mediaType) - 1) - i;
        }
        if (mediaType.getSupportCarousel()) {
            SecMediaPlayerData secMediaPlayerData = (SecMediaPlayerData) viewPagerHelper.mediaPlayerDataFunction.apply(mediaType);
            if (secMediaPlayerData == null || (view = ((SecMediaControlPanel) secMediaPlayerData.getSortedMediaPlayers().get(i)).mViewHolder.playerView) == null) {
                view = null;
            }
            if (view != null) {
                ViewParent parent = view.getParent();
                if (parent != null) {
                    Log.d("ViewPagerHelper", "- instantiateItem - parent: " + parent + ", container: " + viewGroup);
                    viewGroup.removeView(view);
                    if (parent != viewGroup) {
                        ((ViewGroup) parent).removeView(view);
                    }
                }
            }
            view = null;
        } else {
            SecMediaPlayerData secMediaPlayerData2 = (SecMediaPlayerData) viewPagerHelper.mediaPlayerDataFunction.apply(mediaType);
            if (secMediaPlayerData2 != null) {
                view = ((SecMediaControlPanel) secMediaPlayerData2.getSortedMediaPlayers().get(0)).mViewHolder.playerView;
            }
            view = null;
        }
        viewGroup.addView(view);
        if (view != null) {
            return view;
        }
        super.instantiateItem(viewGroup, i);
        throw null;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public final boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }
}
