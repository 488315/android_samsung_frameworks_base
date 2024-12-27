package com.android.systemui.qs.bar;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.audio.soundcraft.view.SoundCraftQpDetailAdapter;
import com.android.systemui.qs.SecQSDetailController;
import com.android.systemui.qs.SecQSPanelControllerBase;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.shade.domain.interactor.SecQSExpansionStateChangeEvent;
import com.android.systemui.shade.domain.interactor.SecQSExpansionStateInteractor;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.volume.util.ColorUtils;
import com.android.systemui.volume.view.icon.QPVolumeIcon;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class VolumeBar extends BarItemImpl implements TileHostable {
    public final Context mContext;
    public final SecQSPanelResourcePicker mResourcePicker;
    public SecQSDetailController mSecQSDetailController;
    public VolumeToggleSeekBar mSlider;
    public RelativeLayout mSliderContainer;
    public SoundCraftQpDetailAdapter mSoundCraftQpDetailAdapter;
    public ImageView mStatusIcon;
    public LinearLayout mTileLayout;
    public final ArrayList mTiles;
    public QPVolumeIcon mVolumeIcon;
    public VolumeSeekBar mVolumeSeekBar;
    public VolumeSeekBar mVolumeSeekBarForEditMode;

    public VolumeBar(Context context) {
        super(context);
        this.mTiles = new ArrayList();
        this.mContext = context;
        this.mResourcePicker = (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
    }

    @Override // com.android.systemui.qs.bar.TileHostable
    public final void addTile(SecQSPanelControllerBase.TileRecord tileRecord) {
        this.mTiles.add(tileRecord);
        LinearLayout linearLayout = this.mTileLayout;
        if (linearLayout != null) {
            linearLayout.addView(tileRecord.tileView);
            updateTileLayoutSizeMargins$1(this.mTileLayout);
        }
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void destroy() {
        Log.i(this.TAG, "destroy");
        this.mCallback = null;
        removeAllTiles();
        VolumeSeekBar volumeSeekBar = this.mVolumeSeekBar;
        if (volumeSeekBar != null) {
            Log.d("VolumeSeekBar", "[QP volume] unregister");
            volumeSeekBar.volumeManager.qpVolumeModelCallbacks.clear();
            ((List) volumeSeekBar.qsExpansionStateInteractor.expansionStateListeners$delegate.getValue()).remove(volumeSeekBar);
        }
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final int getBarHeight() {
        if (this.mShowing) {
            return this.mResourcePicker.getBrightnessBarContainerHeight(this.mContext);
        }
        return 0;
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final int getBarLayout() {
        return R.layout.sec_volume_bar;
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void inflateViews(ViewGroup viewGroup) {
        Log.i(this.TAG, "inflateViews");
        View inflate = LayoutInflater.from(this.mContext).inflate(R.layout.sec_volume_bar, viewGroup, false);
        this.mBarRootView = inflate;
        this.mTileLayout = (LinearLayout) inflate.findViewById(R.id.volume_tile_layout);
        this.mSliderContainer = (RelativeLayout) this.mBarRootView.findViewById(R.id.slider_container);
        this.mSlider = (VolumeToggleSeekBar) this.mBarRootView.findViewById(R.id.slider);
        this.mVolumeIcon = (QPVolumeIcon) this.mBarRootView.findViewById(R.id.qs_volume_button);
        this.mStatusIcon = (ImageView) this.mBarRootView.findViewById(R.id.qs_volume_panel_status_icon);
        VolumeSeekBar volumeSeekBar = this.mVolumeSeekBar;
        if (volumeSeekBar != null) {
            Log.d("VolumeSeekBar", "[QP volume] register");
            SecQSExpansionStateInteractor secQSExpansionStateInteractor = volumeSeekBar.qsExpansionStateInteractor;
            ((List) secQSExpansionStateInteractor.expansionStateListeners$delegate.getValue()).add(volumeSeekBar);
            new SecQSExpansionStateChangeEvent(secQSExpansionStateInteractor.getRepository().currentExpanded);
            this.mVolumeSeekBar.initialize(this.mSlider, this.mVolumeIcon, this.mStatusIcon);
        }
        this.mBarRootView.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.android.systemui.qs.bar.VolumeBar$$ExternalSyntheticLambda0
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                VolumeBar volumeBar = VolumeBar.this;
                volumeBar.mSecQSDetailController.showTargetDetail(volumeBar.mSoundCraftQpDetailAdapter);
                SystemUIAnalytics.sendRunstoneEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_BRIGHTNESS_EXPAND, SystemUIAnalytics.RUNESTONE_LABEL_QP_LAYOUT);
                SystemUIAnalytics.sendScreenViewLog(SystemUIAnalytics.SID_BRIGHTNESS_DETAIL);
                return true;
            }
        });
        int size = this.mTiles.size();
        for (int i = 0; i < size; i++) {
            this.mTileLayout.addView(((SecQSPanelControllerBase.TileRecord) this.mTiles.get(i)).tileView);
        }
        updateHeightMargins();
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void makeCloneBar() {
        View inflate = LayoutInflater.from(this.mContext).inflate(R.layout.sec_volume_bar, (ViewGroup) null);
        this.mClonedBarView = inflate;
        LinearLayout linearLayout = (LinearLayout) inflate.findViewById(R.id.volume_tile_layout);
        ViewGroup viewGroup = (ViewGroup) this.mClonedBarView.findViewById(R.id.slider_container);
        VolumeToggleSeekBar volumeToggleSeekBar = (VolumeToggleSeekBar) this.mClonedBarView.findViewById(R.id.slider);
        volumeToggleSeekBar.setImportantForAccessibility(2);
        this.mVolumeSeekBarForEditMode.initialize(volumeToggleSeekBar, (QPVolumeIcon) this.mClonedBarView.findViewById(R.id.qs_volume_button), (ImageView) this.mClonedBarView.findViewById(R.id.qs_volume_panel_status_icon));
        createTilesViewAndDistribute(linearLayout, this.mTiles, this.mResourcePicker, Boolean.FALSE, null);
        updateLayout(this.mClonedBarView, linearLayout, viewGroup, volumeToggleSeekBar);
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void onConfigChanged(Configuration configuration) {
        updateHeightMargins();
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void onUiModeChanged() {
        VolumeToggleSeekBar volumeToggleSeekBar;
        Context context;
        VolumeSeekBar volumeSeekBar = this.mVolumeSeekBar;
        if (volumeSeekBar == null || (volumeToggleSeekBar = volumeSeekBar.slider) == null) {
            return;
        }
        TransitionDrawable transitionDrawable = (TransitionDrawable) ((LayerDrawable) volumeToggleSeekBar.getProgressDrawable()).findDrawableByLayerId(android.R.id.progress);
        int[] iArr = {volumeToggleSeekBar.getContext().getColor(R.color.tw_progress_color_control_activated_start), volumeToggleSeekBar.getContext().getColor(R.color.tw_progress_color_control_activated_end)};
        Drawable drawable = transitionDrawable.getDrawable(0);
        Drawable drawable2 = transitionDrawable.getDrawable(1);
        if ((drawable instanceof ScaleDrawable) && (drawable2 instanceof ScaleDrawable)) {
            ((GradientDrawable) ((ScaleDrawable) drawable).getDrawable()).setColors(iArr);
            Drawable drawable3 = ((ScaleDrawable) drawable2).getDrawable();
            if (drawable3 != null) {
                VolumeToggleSeekBar volumeToggleSeekBar2 = volumeSeekBar.slider;
                drawable3.setTintList((volumeToggleSeekBar2 == null || (context = volumeToggleSeekBar2.getContext()) == null) ? null : ColorUtils.getSingleColorStateList(R.color.tw_progress_color_control_activated_end, context));
            }
        }
        volumeSeekBar.isEarShockWarned = false;
        volumeSeekBar.setProgressChanged(volumeToggleSeekBar.getProgress());
        volumeToggleSeekBar.getThumb().setTintList(ColorUtils.getSingleColorStateList(R.color.tw_progress_color_thumb, volumeToggleSeekBar.getContext()));
    }

    @Override // com.android.systemui.qs.bar.TileHostable
    public final void removeAllTiles() {
        if (this.mTileLayout != null) {
            int size = this.mTiles.size();
            for (int i = 0; i < size; i++) {
                this.mTileLayout.removeView(((SecQSPanelControllerBase.TileRecord) this.mTiles.get(i)).tileView);
                ((SecQSPanelControllerBase.TileRecord) this.mTiles.get(i)).tile.setListening(this, false);
            }
        }
        this.mTiles.clear();
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void setListening(boolean z) {
        this.mListening = z;
        int size = this.mTiles.size();
        for (int i = 0; i < size; i++) {
            ((SecQSPanelControllerBase.TileRecord) this.mTiles.get(i)).tile.setListening(this, this.mListening);
        }
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void updateClonedBar() {
        VolumeToggleSeekBar volumeToggleSeekBar = (VolumeToggleSeekBar) this.mClonedBarView.findViewById(R.id.slider);
        volumeToggleSeekBar.setMax(this.mSlider.getMax());
        volumeToggleSeekBar.setProgress(this.mSlider.getProgress());
        this.mVolumeSeekBarForEditMode.initialize(volumeToggleSeekBar, (QPVolumeIcon) this.mClonedBarView.findViewById(R.id.qs_volume_button), (ImageView) this.mClonedBarView.findViewById(R.id.qs_volume_panel_status_icon));
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void updateHeightMargins() {
        updateLayout(this.mBarRootView, this.mTileLayout, this.mSliderContainer, this.mSlider);
    }

    public final void updateLayout(View view, LinearLayout linearLayout, ViewGroup viewGroup, VolumeToggleSeekBar volumeToggleSeekBar) {
        if (view == null) {
            return;
        }
        ((LinearLayout) view).setOrientation(0);
        Context context = this.mContext;
        SecQSPanelResourcePicker secQSPanelResourcePicker = this.mResourcePicker;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, secQSPanelResourcePicker.getBrightnessBarContainerHeight(context));
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) viewGroup.getLayoutParams();
        viewGroup.setPadding(0, 0, 0, 0);
        int brightnessBarExpandedHeight = secQSPanelResourcePicker.getBrightnessBarExpandedHeight(this.mContext);
        layoutParams2.height = brightnessBarExpandedHeight;
        secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getClass();
        layoutParams2.width = -1;
        layoutParams2.weight = 1.0f;
        viewGroup.setLayoutParams(layoutParams2);
        volumeToggleSeekBar.setMaxHeight(brightnessBarExpandedHeight);
        volumeToggleSeekBar.setPaddingRelative(0, 0, 0, 0);
        updateTileLayoutSizeMargins$1(linearLayout);
        view.setLayoutParams(layoutParams);
    }

    public final void updateTileLayoutSizeMargins$1(LinearLayout linearLayout) {
        if (linearLayout == null) {
            return;
        }
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
        layoutParams.weight = 0.0f;
        layoutParams.setMarginStart(this.mContext.getResources().getDimensionPixelSize(R.dimen.brightness_slider_end_margin));
        Context context = this.mContext;
        SecQSPanelResourcePicker secQSPanelResourcePicker = this.mResourcePicker;
        layoutParams.width = secQSPanelResourcePicker.getBrightnessBarExpandedHeight(context);
        layoutParams.height = secQSPanelResourcePicker.getBrightnessBarExpandedHeight(this.mContext);
        linearLayout.setLayoutParams(layoutParams);
        int brightnessTileLayoutBetweenMargin = secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getBrightnessTileLayoutBetweenMargin(this.mContext);
        linearLayout.setGravity(17);
        int size = this.mTiles.size();
        for (int i = 0; i < size - 1; i++) {
            View childAt = linearLayout.getChildAt(i);
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) childAt.getLayoutParams();
            layoutParams2.setMarginEnd(brightnessTileLayoutBetweenMargin);
            childAt.setLayoutParams(layoutParams2);
        }
    }
}
