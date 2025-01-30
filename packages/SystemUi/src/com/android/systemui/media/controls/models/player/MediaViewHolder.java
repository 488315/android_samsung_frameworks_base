package com.android.systemui.media.controls.models.player;

import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.R;
import com.android.systemui.media.controls.models.GutsViewHolder;
import java.util.Set;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MediaViewHolder {
    public static final Companion Companion = new Companion(null);
    public static final Set genericButtonIds;
    public final ViewGroup seamless;
    public final View seamlessButton;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        Integer valueOf = Integer.valueOf(R.id.icon);
        Integer valueOf2 = Integer.valueOf(R.id.app_name);
        Integer valueOf3 = Integer.valueOf(R.id.header_title);
        Integer valueOf4 = Integer.valueOf(R.id.header_artist);
        Integer valueOf5 = Integer.valueOf(R.id.media_explicit_indicator);
        Integer valueOf6 = Integer.valueOf(R.id.media_seamless);
        Integer valueOf7 = Integer.valueOf(R.id.media_progress_bar);
        Integer valueOf8 = Integer.valueOf(R.id.actionPlayPause);
        Integer valueOf9 = Integer.valueOf(R.id.actionNext);
        Integer valueOf10 = Integer.valueOf(R.id.actionPrev);
        Integer valueOf11 = Integer.valueOf(R.id.action0);
        Integer valueOf12 = Integer.valueOf(R.id.action1);
        Integer valueOf13 = Integer.valueOf(R.id.action2);
        Integer valueOf14 = Integer.valueOf(R.id.action3);
        Integer valueOf15 = Integer.valueOf(R.id.action4);
        Integer valueOf16 = Integer.valueOf(R.id.media_scrubbing_elapsed_time);
        Integer valueOf17 = Integer.valueOf(R.id.media_scrubbing_total_time);
        SetsKt__SetsKt.setOf(valueOf, valueOf2, valueOf3, valueOf4, valueOf5, valueOf6, valueOf7, valueOf8, valueOf9, valueOf10, valueOf11, valueOf12, valueOf13, valueOf14, valueOf15, valueOf, valueOf16, valueOf17);
        genericButtonIds = SetsKt__SetsKt.setOf(valueOf11, valueOf12, valueOf13, valueOf14, valueOf15);
        SetsKt__SetsKt.setOf(valueOf7, valueOf10, valueOf9, valueOf11, valueOf12, valueOf13, valueOf14, valueOf15, valueOf16, valueOf17);
        SetsKt__SetsKt.setOf(valueOf3, valueOf4, valueOf5, valueOf8);
        SetsKt__SetsKt.setOf(Integer.valueOf(R.id.album_art), Integer.valueOf(R.id.turbulence_noise_view), Integer.valueOf(R.id.touch_ripple_view));
    }

    public MediaViewHolder(View view) {
        view.requireViewById(R.id.media_explicit_indicator);
        this.seamless = (ViewGroup) view.requireViewById(R.id.media_seamless);
        this.seamlessButton = view.requireViewById(R.id.media_seamless_button);
        new GutsViewHolder(view);
    }
}
