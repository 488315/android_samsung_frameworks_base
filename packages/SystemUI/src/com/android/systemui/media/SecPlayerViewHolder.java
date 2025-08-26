package com.android.systemui.media;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.LayerDrawable;
import android.util.Log;
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
import com.android.systemui.util.SettingsHelper;
import com.sec.ims.volte2.data.VolteConstants;
import java.util.ArrayList;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Reflection;

/* loaded from: classes2.dex */
public final class SecPlayerViewHolder {
    public static final boolean DEBUG;
    public static final String TAG;
    public final ImageView albumView;
    public final ImageView appIcon;
    public final TextView artistText;
    public final ImageButton budsButtonExpanded;
    public final TextView cancelText;
    public final LayerDrawable dummyProgressDrawable;
    public final TextView elapsedTimeView;
    public final Lazy expandedActionButtons$delegate;
    public final LinearLayout expandedActionButtonsContainer;
    public final LinearLayout header;
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
    private final SettingsHelper settingsHelper;
    public final TextView titleText;
    public final TextView totalTimeView;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        TAG = Reflection.getOrCreateKotlinClass(SecPlayerViewHolder.class).getSimpleName();
        DEBUG = true;
    }

    public SecPlayerViewHolder(Context context, ViewGroup viewGroup, boolean z, MediaType mediaType, SettingsHelper settingsHelper) {
        this.settingsHelper = settingsHelper;
        ColorPresetProvider.INSTANCE.getClass();
        this.progressBarPrimaryColor = ColorPresetProvider.uxPrimaryColor;
        this.progressBarSecondaryColor = ColorPresetProvider.uxSecondaryColor;
        ArrayList arrayListArrayListOf = CollectionsKt__CollectionsKt.arrayListOf(Integer.valueOf(R.id.sec_action0), Integer.valueOf(R.id.sec_action1), Integer.valueOf(R.id.sec_action2), Integer.valueOf(R.id.sec_action3), Integer.valueOf(R.id.sec_action4));
        this.expandedActionButtons$delegate = LazyKt__LazyJVMKt.lazy(new SecPlayerViewHolder$$ExternalSyntheticLambda0());
        String str = TAG;
        boolean z2 = DEBUG;
        if (z2) {
            Log.d(str, "init SecPlayerViewHolder");
        }
        View viewInflate = LayoutInflater.from(context).inflate(mediaType.getLayout(), viewGroup, z);
        viewInflate.getClass();
        this.playerView = viewInflate.requireViewById(R.id.sec_qs_media_controls);
        this.albumView = (ImageView) viewInflate.requireViewById(R.id.sec_album_art);
        this.appIcon = (ImageView) viewInflate.requireViewById(R.id.sec_icon);
        this.artistText = (TextView) viewInflate.requireViewById(R.id.sec_header_artist);
        this.header = (LinearLayout) viewInflate.requireViewById(R.id.media_header);
        this.titleText = (TextView) viewInflate.requireViewById(R.id.sec_header_title);
        this.mediaOutputText = (TextView) viewInflate.requireViewById(R.id.sec_media_output_text);
        this.seamlessText = (TextView) viewInflate.requireViewById(R.id.sec_device_name);
        this.elapsedTimeView = (TextView) viewInflate.requireViewById(R.id.sec_media_elapsed_time);
        this.seekBar = (SeekBar) viewInflate.requireViewById(R.id.sec_media_progress_bar);
        this.totalTimeView = (TextView) viewInflate.requireViewById(R.id.sec_media_total_time);
        LinearLayout linearLayout = (LinearLayout) viewInflate.requireViewById(R.id.action_buttons_expanded);
        this.expandedActionButtonsContainer = linearLayout;
        this.budsButtonExpanded = (ImageButton) (linearLayout == null ? null : linearLayout).requireViewById(R.id.buds_action);
        int size = arrayListArrayListOf.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayListArrayListOf.get(i);
            i++;
            int iIntValue = ((Number) obj).intValue();
            SparseArray sparseArray = (SparseArray) this.expandedActionButtons$delegate.getValue();
            LinearLayout linearLayout2 = this.expandedActionButtonsContainer;
            if (linearLayout2 == null) {
                linearLayout2 = null;
            }
            sparseArray.set(iIntValue, linearLayout2.requireViewById(iIntValue));
        }
        if (mediaType.getSupportSquiggly() || mediaType.getSupportCoverQuickPanelMedia()) {
            int color = viewInflate.getContext().getColor(R.color.sec_cover_media_player_seekbar_thumb_background_color);
            SparseArray sparseArray2 = (SparseArray) this.expandedActionButtons$delegate.getValue();
            int size2 = sparseArray2.size();
            for (int i2 = 0; i2 < size2; i2++) {
                sparseArray2.keyAt(i2);
                ImageButton imageButton = (ImageButton) sparseArray2.valueAt(i2);
                imageButton.setColorFilter(color);
                imageButton.setStateListAnimator(RecoilEffectUtil.getSecRecoilSmallAnimator(viewInflate.getContext()));
            }
            ImageButton imageButton2 = this.budsButtonExpanded;
            (imageButton2 == null ? null : imageButton2).setColorFilter(color);
        }
        if (mediaType.getSupportExpandable()) {
            this.player = (LinearLayout) viewInflate.requireViewById(R.id.sec_qs_media_player);
        }
        if (mediaType.getSupportSettings()) {
            this.cancelText = (TextView) viewInflate.requireViewById(R.id.sec_cancel_text);
            this.options = viewInflate.requireViewById(R.id.qs_media_controls_options);
            this.optionsAppIcon = (ImageView) viewInflate.requireViewById(R.id.sec_option_app_icon);
            this.optionsAppTitle = (TextView) viewInflate.requireViewById(R.id.sec_option_app_text);
            this.optionButtons = viewInflate.requireViewById(R.id.sec_option_buttons);
            this.remove = viewInflate.requireViewById(R.id.sec_option_remove_button);
            TextView textView = (TextView) viewInflate.requireViewById(R.id.sec_remove_text);
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
            if (z2) {
                Log.d(str, "set AudioVisSeekBarProgressDrawable on seekBar.progressDrawable ");
            }
            seekBar.setProgressDrawable(new AudioVisSeekBarProgressDrawable(seekBar));
            seekBar.setThumb(seekBar.getContext().getDrawable(R.drawable.sec_media_thumb_jr));
        }
    }

    public final boolean isRemoveAnimation() {
        SettingsHelper settingsHelper = this.settingsHelper;
        if (settingsHelper != null) {
            return settingsHelper.isRemoveAnimation();
        }
        return false;
    }

    public /* synthetic */ SecPlayerViewHolder(Context context, ViewGroup viewGroup, boolean z, MediaType mediaType, SettingsHelper settingsHelper, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, viewGroup, z, mediaType, (i & 16) != 0 ? null : settingsHelper);
    }
}
