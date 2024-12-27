package com.android.systemui.media;

import android.view.View;
import android.view.ViewGroup;
import androidx.viewpager.widget.PagerAdapter;
import java.util.ArrayList;
import java.util.function.Function;
import kotlin.Pair;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.TransformingIndexedSequence;
import kotlin.sequences.TransformingIndexedSequence$iterator$1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
        Function function = viewPagerHelper.mediaPlayerDataFunction;
        MediaType mediaType = this.$type;
        SecMediaPlayerData secMediaPlayerData = (SecMediaPlayerData) function.apply(mediaType);
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
                if (!transformingIndexedSequence$iterator$1.iterator.hasNext()) {
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
                return viewPagerHelper.isRTLSupplier.getAsInt() == 1 ? (viewPagerHelper.getPlayersCount(mediaType) - 1) - intValue : intValue;
            }
        }
        return -2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x0082, code lost:
    
        if (r0 != null) goto L24;
     */
    @Override // androidx.viewpager.widget.PagerAdapter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object instantiateItem(int r6, android.view.ViewGroup r7) {
        /*
            r5 = this;
            int r0 = com.android.systemui.media.ViewPagerHelper.$r8$clinit
            com.android.systemui.media.ViewPagerHelper r0 = r5.this$0
            java.util.function.IntSupplier r1 = r0.isRTLSupplier
            int r1 = r1.getAsInt()
            com.android.systemui.media.MediaType r2 = r5.$type
            r3 = 1
            if (r1 != r3) goto L16
            int r1 = r0.getPlayersCount(r2)
            int r1 = r1 - r3
            int r6 = r1 - r6
        L16:
            boolean r1 = r2.getSupportCarousel()
            r3 = 0
            if (r1 == 0) goto L69
            java.util.function.Function r0 = r0.mediaPlayerDataFunction
            java.lang.Object r0 = r0.apply(r2)
            com.android.systemui.media.SecMediaPlayerData r0 = (com.android.systemui.media.SecMediaPlayerData) r0
            if (r0 == 0) goto L38
            java.util.ArrayList r0 = r0.getSortedMediaPlayers()
            java.lang.Object r0 = r0.get(r6)
            com.android.systemui.media.SecMediaControlPanel r0 = (com.android.systemui.media.SecMediaControlPanel) r0
            com.android.systemui.media.SecPlayerViewHolder r0 = r0.mViewHolder
            android.view.View r0 = r0.playerView
            if (r0 == 0) goto L38
            goto L39
        L38:
            r0 = r3
        L39:
            if (r0 == 0) goto L67
            android.view.ViewParent r1 = r0.getParent()
            if (r1 == 0) goto L84
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r4 = "- instantiateItem - parent: "
            r2.<init>(r4)
            r2.append(r1)
            java.lang.String r4 = ", container: "
            r2.append(r4)
            r2.append(r7)
            java.lang.String r2 = r2.toString()
            java.lang.String r4 = "ViewPagerHelper"
            android.util.Log.d(r4, r2)
            r7.removeView(r0)
            if (r1 == r7) goto L84
            android.view.ViewGroup r1 = (android.view.ViewGroup) r1
            r1.removeView(r0)
            goto L84
        L67:
            r0 = r3
            goto L84
        L69:
            java.util.function.Function r0 = r0.mediaPlayerDataFunction
            java.lang.Object r0 = r0.apply(r2)
            com.android.systemui.media.SecMediaPlayerData r0 = (com.android.systemui.media.SecMediaPlayerData) r0
            if (r0 == 0) goto L67
            java.util.ArrayList r0 = r0.getSortedMediaPlayers()
            r1 = 0
            java.lang.Object r0 = r0.get(r1)
            com.android.systemui.media.SecMediaControlPanel r0 = (com.android.systemui.media.SecMediaControlPanel) r0
            com.android.systemui.media.SecPlayerViewHolder r0 = r0.mViewHolder
            android.view.View r0 = r0.playerView
            if (r0 == 0) goto L67
        L84:
            r7.addView(r0)
            if (r0 == 0) goto L8a
            return r0
        L8a:
            super.instantiateItem(r6, r7)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.ViewPagerHelper$createPageAdapter$1.instantiateItem(int, android.view.ViewGroup):java.lang.Object");
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public final boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }
}
