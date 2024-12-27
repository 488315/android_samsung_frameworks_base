package com.android.systemui.media;

import android.content.Context;
import android.graphics.Typeface;
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
import com.android.systemui.util.RecoilEffectUtil;
import com.sec.ims.volte2.data.VolteConstants;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class SecPlayerViewHolder {
    public final ImageView albumView;
    public final ImageView appIcon;
    public final TextView artistText;
    public final ImageButton budsButtonExpanded;
    public final TextView cancelText;
    public final LayerDrawable dummyProgressDrawable;
    public final TextView elapsedTimeView;
    public final Lazy expandedActionButtons$delegate;
    public final LinearLayout expandedActionButtonsContainer;
    public final TextView mediaOutputText;
    public final View optionButtons;
    public final View options;
    public final ImageView optionsAppIcon;
    public final TextView optionsAppTitle;
    public final LinearLayout player;
    public final View playerView;
    public int progressBarPrimaryColor;
    public int progressBarSecondaryColor;
    public final View remove;
    public final TextView removeText;
    public final TextView seamlessText;
    public final SeekBar seekBar;
    public final TextView titleText;
    public final TextView totalTimeView;

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

    public SecPlayerViewHolder(Context context, ViewGroup viewGroup, boolean z, MediaType mediaType) {
        ColorPresetProvider.INSTANCE.getClass();
        this.progressBarPrimaryColor = ColorPresetProvider.uxPrimaryColor;
        this.progressBarSecondaryColor = ColorPresetProvider.uxSecondaryColor;
        ArrayList arrayListOf = CollectionsKt__CollectionsKt.arrayListOf(Integer.valueOf(R.id.sec_action0), Integer.valueOf(R.id.sec_action1), Integer.valueOf(R.id.sec_action2), Integer.valueOf(R.id.sec_action3), Integer.valueOf(R.id.sec_action4));
        this.expandedActionButtons$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.media.SecPlayerViewHolder$expandedActionButtons$2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return new SparseArray();
            }
        });
        View inflate = LayoutInflater.from(context).inflate(mediaType.getLayout(), viewGroup, z);
        Intrinsics.checkNotNull(inflate);
        this.playerView = inflate.requireViewById(R.id.sec_qs_media_controls);
        this.albumView = (ImageView) inflate.requireViewById(R.id.sec_album_art);
        this.appIcon = (ImageView) inflate.requireViewById(R.id.sec_icon);
        this.artistText = (TextView) inflate.requireViewById(R.id.sec_header_artist);
        this.titleText = (TextView) inflate.requireViewById(R.id.sec_header_title);
        this.mediaOutputText = (TextView) inflate.requireViewById(R.id.sec_media_output_text);
        this.seamlessText = (TextView) inflate.requireViewById(R.id.sec_device_name);
        this.elapsedTimeView = (TextView) inflate.requireViewById(R.id.sec_media_elapsed_time);
        this.seekBar = (SeekBar) inflate.requireViewById(R.id.sec_media_progress_bar);
        this.totalTimeView = (TextView) inflate.requireViewById(R.id.sec_media_total_time);
        LinearLayout linearLayout = (LinearLayout) inflate.requireViewById(R.id.action_buttons_expanded);
        this.expandedActionButtonsContainer = linearLayout;
        this.budsButtonExpanded = (ImageButton) linearLayout.requireViewById(R.id.buds_action);
        Iterator it = arrayListOf.iterator();
        while (true) {
            LinearLayout linearLayout2 = null;
            if (!it.hasNext()) {
                break;
            }
            int intValue = ((Number) it.next()).intValue();
            SparseArray sparseArray = (SparseArray) this.expandedActionButtons$delegate.getValue();
            LinearLayout linearLayout3 = this.expandedActionButtonsContainer;
            if (linearLayout3 != null) {
                linearLayout2 = linearLayout3;
            }
            sparseArray.set(intValue, linearLayout2.requireViewById(intValue));
        }
        if (mediaType.getSupportSquiggly()) {
            int color = inflate.getContext().getColor(R.color.sec_cover_media_player_seekbar_thumb_background_color);
            SparseArray sparseArray2 = (SparseArray) this.expandedActionButtons$delegate.getValue();
            int size = sparseArray2.size();
            for (int i = 0; i < size; i++) {
                sparseArray2.keyAt(i);
                ImageButton imageButton = (ImageButton) sparseArray2.valueAt(i);
                imageButton.setColorFilter(color);
                imageButton.setStateListAnimator(RecoilEffectUtil.getSecRecoilSmallAnimator(inflate.getContext()));
            }
            ImageButton imageButton2 = this.budsButtonExpanded;
            (imageButton2 == null ? null : imageButton2).setColorFilter(color);
        }
        if (mediaType.getSupportExpandable()) {
            this.player = (LinearLayout) inflate.requireViewById(R.id.sec_qs_media_player);
        }
        if (mediaType.getSupportSettings()) {
            this.cancelText = (TextView) inflate.requireViewById(R.id.sec_cancel_text);
            this.options = inflate.requireViewById(R.id.qs_media_controls_options);
            this.optionsAppIcon = (ImageView) inflate.requireViewById(R.id.sec_option_app_icon);
            this.optionsAppTitle = (TextView) inflate.requireViewById(R.id.sec_option_app_text);
            this.optionButtons = inflate.requireViewById(R.id.sec_option_buttons);
            this.remove = inflate.requireViewById(R.id.sec_option_remove_button);
            TextView textView = (TextView) inflate.requireViewById(R.id.sec_remove_text);
            if (textView != null) {
                textView.setTypeface(Typeface.create(Typeface.create("sec", 0), VolteConstants.ErrorCode.BUSY_EVERYWHERE, false));
                textView.setStateListAnimator(RecoilEffectUtil.getSecRecoilSmallAnimator(textView.getContext()));
            } else {
                textView = null;
            }
            this.removeText = textView;
        }
        if (mediaType.getSupportSquiggly()) {
            SeekBar seekBar = this.seekBar;
            seekBar = seekBar == null ? null : seekBar;
            seekBar.setProgress(50);
            SeekBar seekBar2 = this.seekBar;
            this.dummyProgressDrawable = (LayerDrawable) (seekBar2 != null ? seekBar2 : null).getProgressDrawable();
            seekBar.setProgressDrawable(new AudioVisSeekBarProgressDrawable(seekBar));
            seekBar.setThumb(seekBar.getContext().getDrawable(R.drawable.sec_media_thumb_jr));
        }
    }
}
