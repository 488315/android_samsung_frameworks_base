package com.android.systemui.media;

import android.content.Context;
import android.graphics.drawable.LayerDrawable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.media.audiovisseekbar.AudioVisSeekBarProgressDrawable;
import com.android.systemui.media.audiovisseekbar.utils.color.ColorPresetProvider;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SecPlayerViewHolder {
    public ImageView albumThumbnail;
    public ImageView albumView;
    public ImageView appIcon;
    public TextView appName;
    public TextView artistText;
    public ImageButton budsButtonCollapsed;
    public ImageButton budsButtonExpanded;
    public TextView cancelText;
    public final Lazy collapsedActionButtons$delegate;
    public LinearLayout collapsedActionButtonsContainer;
    public LayerDrawable dummyProgressDrawable;
    public TextView elapsedTimeView;
    public ImageView expandIcon;
    public final Lazy expandedActionButtons$delegate;
    public LinearLayout expandedActionButtonsContainer;
    public LinearLayout header;
    public TextView mediaOutputText;
    public View optionButtons;
    public View options;
    public ImageView optionsAppIcon;
    public TextView optionsAppTitle;
    public LinearLayout player;
    public View playerView;
    public int progressBarPrimaryColor;
    public int progressBarSecondaryColor;
    public LinearLayout progressInfo;
    public View remove;
    public TextView removeText;
    public TextView seamlessText;
    public SeekBar seekBar;
    public LinearLayout titleArtistView;
    public TextView titleText;
    public TextView totalTimeView;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    public SecPlayerViewHolder(Context context, ViewGroup viewGroup, boolean z, MediaType mediaType) {
        ColorPresetProvider.INSTANCE.getClass();
        this.progressBarPrimaryColor = ColorPresetProvider.uxPrimaryColor;
        this.progressBarSecondaryColor = ColorPresetProvider.uxSecondaryColor;
        ArrayList arrayListOf = CollectionsKt__CollectionsKt.arrayListOf(Integer.valueOf(R.id.sec_action0), Integer.valueOf(R.id.sec_action1), Integer.valueOf(R.id.sec_action2), Integer.valueOf(R.id.sec_action3), Integer.valueOf(R.id.sec_action4));
        this.collapsedActionButtons$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.media.SecPlayerViewHolder$collapsedActionButtons$2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return new SparseArray();
            }
        });
        this.expandedActionButtons$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.media.SecPlayerViewHolder$expandedActionButtons$2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return new SparseArray();
            }
        });
        View inflate = LayoutInflater.from(context).inflate(mediaType.getLayout(), viewGroup, z);
        this.playerView = inflate.findViewById(R.id.sec_qs_media_controls);
        this.albumView = (ImageView) inflate.findViewById(R.id.sec_album_art);
        this.appIcon = (ImageView) inflate.findViewById(R.id.sec_icon);
        this.appName = (TextView) inflate.findViewById(R.id.sec_app_name);
        this.artistText = (TextView) inflate.findViewById(R.id.sec_header_artist);
        this.header = (LinearLayout) inflate.findViewById(R.id.media_header);
        this.titleText = (TextView) inflate.findViewById(R.id.sec_header_title);
        this.mediaOutputText = (TextView) inflate.findViewById(R.id.sec_media_output_text);
        this.seamlessText = (TextView) inflate.findViewById(R.id.sec_device_name);
        this.elapsedTimeView = (TextView) inflate.findViewById(R.id.sec_media_elapsed_time);
        this.seekBar = (SeekBar) inflate.findViewById(R.id.sec_media_progress_bar);
        this.totalTimeView = (TextView) inflate.findViewById(R.id.sec_media_total_time);
        this.collapsedActionButtonsContainer = (LinearLayout) inflate.findViewById(R.id.action_buttons_collapsed);
        this.expandedActionButtonsContainer = (LinearLayout) inflate.findViewById(R.id.action_buttons_expanded);
        this.budsButtonCollapsed = (ImageButton) getCollapsedActionButtonsContainer().findViewById(R.id.buds_action);
        LinearLayout linearLayout = this.expandedActionButtonsContainer;
        this.budsButtonExpanded = (ImageButton) (linearLayout == null ? null : linearLayout).findViewById(R.id.buds_action);
        Iterator it = arrayListOf.iterator();
        while (it.hasNext()) {
            int intValue = ((Number) it.next()).intValue();
            ((SparseArray) this.collapsedActionButtons$delegate.getValue()).set(intValue, getCollapsedActionButtonsContainer().findViewById(intValue));
            SparseArray sparseArray = (SparseArray) this.expandedActionButtons$delegate.getValue();
            LinearLayout linearLayout2 = this.expandedActionButtonsContainer;
            if (linearLayout2 == null) {
                linearLayout2 = null;
            }
            sparseArray.set(intValue, linearLayout2.findViewById(intValue));
        }
        if (mediaType.getSupportSquiggly()) {
            int color = inflate.getContext().getColor(R.color.sec_cover_media_player_seekbar_thumb_background_color);
            SparseArray sparseArray2 = (SparseArray) this.expandedActionButtons$delegate.getValue();
            int size = sparseArray2.size();
            for (int i = 0; i < size; i++) {
                sparseArray2.keyAt(i);
                ((ImageButton) sparseArray2.valueAt(i)).setColorFilter(color);
            }
            ImageButton imageButton = this.budsButtonExpanded;
            (imageButton != null ? imageButton : null).setColorFilter(color);
        }
        if (mediaType.getSupportExpandable()) {
            this.albumThumbnail = (ImageView) inflate.findViewById(R.id.sec_album_thumbnail);
            this.expandIcon = (ImageView) inflate.findViewById(R.id.media_expansion_toggle);
            this.player = (LinearLayout) inflate.findViewById(R.id.sec_qs_media_player);
            this.progressInfo = (LinearLayout) inflate.findViewById(R.id.sec_progress_info);
            this.titleArtistView = (LinearLayout) inflate.findViewById(R.id.sec_title_artist_container);
        }
        if (mediaType.getSupportSettings()) {
            this.cancelText = (TextView) inflate.findViewById(R.id.sec_cancel_text);
            this.options = inflate.findViewById(R.id.qs_media_controls_options);
            this.optionsAppIcon = (ImageView) inflate.findViewById(R.id.sec_option_app_icon);
            this.optionsAppTitle = (TextView) inflate.findViewById(R.id.sec_option_app_text);
            this.optionButtons = inflate.findViewById(R.id.sec_option_buttons);
            this.remove = inflate.findViewById(R.id.sec_option_remove_button);
            this.removeText = (TextView) inflate.findViewById(R.id.sec_remove_text);
        }
        if (mediaType.getSupportSquiggly()) {
            SeekBar seekBar = getSeekBar();
            seekBar.setProgress(50);
            this.dummyProgressDrawable = (LayerDrawable) getSeekBar().getProgressDrawable();
            seekBar.setProgressDrawable(new AudioVisSeekBarProgressDrawable(seekBar));
            seekBar.setThumb(seekBar.getContext().getDrawable(R.drawable.sec_media_thumb_jr));
        }
    }

    public final ImageButton getActionButton(int i, boolean z) {
        ImageButton imageButton = (ImageButton) (z ? (SparseArray) this.expandedActionButtons$delegate.getValue() : (SparseArray) this.collapsedActionButtons$delegate.getValue()).get(i);
        if (imageButton != null) {
            return imageButton;
        }
        throw new IllegalArgumentException();
    }

    public final ImageView getAlbumView() {
        ImageView imageView = this.albumView;
        if (imageView != null) {
            return imageView;
        }
        return null;
    }

    public final LinearLayout getCollapsedActionButtonsContainer() {
        LinearLayout linearLayout = this.collapsedActionButtonsContainer;
        if (linearLayout != null) {
            return linearLayout;
        }
        return null;
    }

    public final SeekBar getSeekBar() {
        SeekBar seekBar = this.seekBar;
        if (seekBar != null) {
            return seekBar;
        }
        return null;
    }
}
