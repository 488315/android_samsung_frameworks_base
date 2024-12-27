package com.android.systemui.media.controls.ui.view;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.media.controls.ui.drawable.IlluminationDrawable;
import com.android.systemui.util.animation.TransitionLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class RecommendationViewHolder {
    public static final Companion Companion = new Companion(null);
    public static final int backgroundId;
    public static final Set controlsIds;
    public static final Set mediaContainersIds;
    public static final Set mediaTitlesAndSubtitlesIds;
    public final TextView cardTitle;
    public final GutsViewHolder gutsViewHolder;
    public final List mediaAppIcons;
    public final List mediaCoverContainers;
    public final List mediaCoverItems;
    public final List mediaProgressBars;
    public final List mediaSubtitles;
    public final List mediaTitles;
    public final TransitionLayout recommendations;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        Integer valueOf = Integer.valueOf(R.id.media_rec_title);
        Integer valueOf2 = Integer.valueOf(R.id.media_cover);
        Integer valueOf3 = Integer.valueOf(R.id.media_cover1_container);
        Integer valueOf4 = Integer.valueOf(R.id.media_cover2_container);
        Integer valueOf5 = Integer.valueOf(R.id.media_cover3_container);
        Integer valueOf6 = Integer.valueOf(R.id.media_title);
        Integer valueOf7 = Integer.valueOf(R.id.media_subtitle);
        controlsIds = SetsKt__SetsKt.setOf(valueOf, valueOf2, valueOf3, valueOf4, valueOf5, valueOf6, valueOf7);
        mediaTitlesAndSubtitlesIds = SetsKt__SetsKt.setOf(valueOf6, valueOf7);
        mediaContainersIds = SetsKt__SetsKt.setOf(valueOf3, valueOf4, valueOf5);
        backgroundId = R.id.sizing_view;
    }

    public /* synthetic */ RecommendationViewHolder(View view, DefaultConstructorMarker defaultConstructorMarker) {
        this(view);
    }

    private RecommendationViewHolder(View view) {
        this.recommendations = (TransitionLayout) view;
        List listOf = CollectionsKt__CollectionsKt.listOf(view.requireViewById(R.id.media_cover1_container), view.requireViewById(R.id.media_cover2_container), view.requireViewById(R.id.media_cover3_container));
        this.mediaCoverContainers = listOf;
        List list = listOf;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((ViewGroup) it.next()).requireViewById(R.id.media_rec_app_icon));
        }
        List list2 = this.mediaCoverContainers;
        ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
        Iterator it2 = list2.iterator();
        while (it2.hasNext()) {
            arrayList2.add((TextView) ((ViewGroup) it2.next()).requireViewById(R.id.media_title));
        }
        List list3 = this.mediaCoverContainers;
        ArrayList arrayList3 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list3, 10));
        Iterator it3 = list3.iterator();
        while (it3.hasNext()) {
            arrayList3.add((TextView) ((ViewGroup) it3.next()).requireViewById(R.id.media_subtitle));
        }
        List list4 = this.mediaCoverContainers;
        ArrayList arrayList4 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list4, 10));
        Iterator it4 = list4.iterator();
        while (it4.hasNext()) {
            SeekBar seekBar = (SeekBar) ((ViewGroup) it4.next()).requireViewById(R.id.media_progress_bar);
            seekBar.setLayoutDirection(0);
            arrayList4.add(seekBar);
        }
        List list5 = this.mediaCoverContainers;
        ArrayList arrayList5 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list5, 10));
        Iterator it5 = list5.iterator();
        while (it5.hasNext()) {
            arrayList5.add((ImageView) ((ViewGroup) it5.next()).requireViewById(R.id.media_cover));
        }
        this.gutsViewHolder = new GutsViewHolder(view);
        IlluminationDrawable illuminationDrawable = (IlluminationDrawable) this.recommendations.getBackground();
        Iterator it6 = this.mediaCoverContainers.iterator();
        while (it6.hasNext()) {
            illuminationDrawable.registerLightSource((ViewGroup) it6.next());
        }
        illuminationDrawable.registerLightSource(this.gutsViewHolder.cancel);
        illuminationDrawable.registerLightSource(this.gutsViewHolder.dismiss);
        illuminationDrawable.registerLightSource(this.gutsViewHolder.settings);
    }
}
