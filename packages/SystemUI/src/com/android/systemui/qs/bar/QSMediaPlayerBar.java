package com.android.systemui.qs.bar;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import com.android.systemui.R;
import com.android.systemui.media.MediaOutputHelper;
import com.android.systemui.media.MediaType;
import com.android.systemui.media.SecMediaHost;
import com.android.systemui.media.SecMediaPlayerData;
import com.android.systemui.media.controls.shared.model.MediaData;
import com.android.systemui.media.controls.shared.model.MediaDeviceData;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.SecQSDetailController;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.bar.BarController;
import com.android.systemui.qs.panelresource.SecQSPanelResourceCommon;
import com.android.systemui.shade.data.repository.ShadeRepository;
import com.android.systemui.shade.data.repository.ShadeRepositoryImpl;
import dagger.Lazy;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes2.dex */
public class QSMediaPlayerBar extends BarItemImpl implements StatusBarStateController.StateListener {
    public int mBarHeight;
    public int mBarTopMargin;
    public int mCurrentOrientation;
    public final SecMediaHost mMediaHost;
    public final MediaOutputHelper mMediaOutputHelper;
    public final QSMediaPlayerBar$$ExternalSyntheticLambda0 mMediaPanelVisibilityListener;
    public ConstraintLayout mPlayLastSongView;
    public final SecQSPanelResourcePicker mResourcePicker;
    public final SecQSDetailController mSecQSDetailController;
    public final ShadeRepository mShadeRepository;
    public final Lazy mSoundCraftQpDetailAdapterLazy;

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.qs.bar.QSMediaPlayerBar$$ExternalSyntheticLambda0] */
    public QSMediaPlayerBar(Context context, SecMediaHost secMediaHost, SecQSPanelResourcePicker secQSPanelResourcePicker, SecQSDetailController secQSDetailController, Lazy lazy, MediaOutputHelper mediaOutputHelper, ShadeRepository shadeRepository) {
        super(context);
        this.mMediaHost = secMediaHost;
        this.mResourcePicker = secQSPanelResourcePicker;
        this.mMediaPanelVisibilityListener = new SecMediaHost.MediaPanelVisibilityListener() { // from class: com.android.systemui.qs.bar.QSMediaPlayerBar$$ExternalSyntheticLambda0
            @Override // com.android.systemui.media.SecMediaHost.MediaPanelVisibilityListener
            public final void onMediaVisibilityChanged(boolean z) {
                this.f$0.updateBar();
            }
        };
        this.mSecQSDetailController = secQSDetailController;
        this.mSoundCraftQpDetailAdapterLazy = lazy;
        this.mMediaOutputHelper = mediaOutputHelper;
        this.mShadeRepository = shadeRepository;
        this.mCurrentOrientation = this.mContext.getResources().getConfiguration().orientation;
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void destroy() {
        this.mCallback = null;
        SecMediaHost secMediaHost = this.mMediaHost;
        if (secMediaHost != null) {
            secMediaHost.removeMediaFrame(MediaType.QS);
            secMediaHost.mVisibilityListeners.remove(this.mMediaPanelVisibilityListener);
        }
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final int getBarHeight() {
        return this.mBarHeight + this.mBarTopMargin;
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final int getBarLayout() {
        return R.layout.qs_media_player_bar;
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final int getBarWidthWeight(Context context) {
        return (this.mSecQsUiDisplayModeInteractor.isTablet() || context.getResources().getConfiguration().orientation != 2) ? 4 : 2;
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void inflateViews(ViewGroup viewGroup) {
        if (this.mBarRootView == null) {
            View viewInflate = LayoutInflater.from(this.mContext).inflate(R.layout.qs_media_player_bar, viewGroup, false);
            this.mBarRootView = viewInflate;
            this.mMediaOutputHelper.mediaRootView = viewInflate;
            SecMediaHost secMediaHost = this.mMediaHost;
            if (secMediaHost != null) {
                secMediaHost.mVisibilityListeners.add(this.mMediaPanelVisibilityListener);
                secMediaHost.addMediaFrame(MediaType.QS, this.mBarRootView.findViewById(R.id.media_player_container));
                this.mPlayLastSongView = (ConstraintLayout) this.mBarRootView.findViewById(R.id.sec_play_last_song_view);
                final int i = 0;
                this.mBarRootView.findViewById(R.id.sec_media_output_text_portrait).setOnClickListener(new View.OnClickListener(this) { // from class: com.android.systemui.qs.bar.QSMediaPlayerBar$$ExternalSyntheticLambda1
                    public final /* synthetic */ QSMediaPlayerBar f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) throws Resources.NotFoundException {
                        int i2 = i;
                        QSMediaPlayerBar qSMediaPlayerBar = this.f$0;
                        switch (i2) {
                            case 0:
                                qSMediaPlayerBar.mMediaOutputHelper.showDetail();
                                break;
                            default:
                                qSMediaPlayerBar.mMediaOutputHelper.showDetail();
                                break;
                        }
                    }
                });
                final int i2 = 1;
                this.mBarRootView.findViewById(R.id.sec_media_output_text_landscape).setOnClickListener(new View.OnClickListener(this) { // from class: com.android.systemui.qs.bar.QSMediaPlayerBar$$ExternalSyntheticLambda1
                    public final /* synthetic */ QSMediaPlayerBar f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) throws Resources.NotFoundException {
                        int i22 = i2;
                        QSMediaPlayerBar qSMediaPlayerBar = this.f$0;
                        switch (i22) {
                            case 0:
                                qSMediaPlayerBar.mMediaOutputHelper.showDetail();
                                break;
                            default:
                                qSMediaPlayerBar.mMediaOutputHelper.showDetail();
                                break;
                        }
                    }
                });
                QSMediaPlayerBar$$ExternalSyntheticLambda3 qSMediaPlayerBar$$ExternalSyntheticLambda3 = new QSMediaPlayerBar$$ExternalSyntheticLambda3(this, 0);
                QSMediaPlayerBar$$ExternalSyntheticLambda3 qSMediaPlayerBar$$ExternalSyntheticLambda32 = new QSMediaPlayerBar$$ExternalSyntheticLambda3(this, i2);
                secMediaHost.mBudsDetailOpenRunnable = qSMediaPlayerBar$$ExternalSyntheticLambda3;
                secMediaHost.mBudsDetailCloseRunnable = qSMediaPlayerBar$$ExternalSyntheticLambda32;
            }
            updateBar();
        }
    }

    public final boolean isLandscapeView() {
        return !this.mSecQsUiDisplayModeInteractor.isTablet() && this.mCurrentOrientation == 2;
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void makeCloneBar() {
        View viewInflate = LayoutInflater.from(this.mContext).inflate(R.layout.qs_customizer_mediabar_dummy, (ViewGroup) null);
        this.mClonedBarView = viewInflate;
        if (viewInflate != null) {
            ColoredBGHelper coloredBGHelper = this.mBGColorHelper;
            if (coloredBGHelper != null) {
                coloredBGHelper.addBarBackground(viewInflate.requireViewById(R.id.sec_album_art), false);
            }
            this.mClonedBarView.measure(View.MeasureSpec.makeMeasureSpec(0, 1073741824), View.MeasureSpec.makeMeasureSpec(0, 1073741824));
            View view = this.mClonedBarView;
            view.layout(0, 0, view.getMeasuredWidth(), this.mClonedBarView.getMeasuredHeight());
        }
        updateCloneMediaInfo();
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void onConfigChanged(Configuration configuration) {
        int i = this.mCurrentOrientation;
        int i2 = configuration.orientation;
        if (i != i2) {
            this.mCurrentOrientation = i2;
            if (this.mBarRootView == null) {
                return;
            }
            updateBar();
        }
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public final void onStateChanged(int i) {
        SecMediaHost secMediaHost = this.mMediaHost;
        if (secMediaHost != null) {
            secMediaHost.onStateChanged(i);
        }
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void removeCloneTileBG() {
        View view = this.mClonedBarView;
        if (view == null) {
            return;
        }
        View viewFindViewById = view.findViewById(R.id.sec_album_art);
        ColoredBGHelper coloredBGHelper = this.mBGColorHelper;
        if (coloredBGHelper == null || viewFindViewById == null) {
            return;
        }
        coloredBGHelper.removeFromBarBackground(viewFindViewById);
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void setCallback(BarController.AnonymousClass4 anonymousClass4) {
        this.mMediaHost.mMediaBarCallback = anonymousClass4;
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void setListening(boolean z) {
        this.mListening = z;
        SecMediaHost secMediaHost = this.mMediaHost;
        if (secMediaHost != null) {
            secMediaHost.setListening(z && ((Boolean) ((ShadeRepositoryImpl) this.mShadeRepository).legacyIsQsExpanded.$$delegate_0.getValue()).booleanValue(), MediaType.QS);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0020  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0026  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateBar() {
        int mediaPlayerCollapsedHeight;
        SecQSPanelResourcePicker secQSPanelResourcePicker = this.mResourcePicker;
        SecMediaHost secMediaHost = this.mMediaHost;
        if (secMediaHost != null) {
            SecMediaPlayerData secMediaPlayerData = (SecMediaPlayerData) secMediaHost.mMediaPlayerData.get(MediaType.QS);
            if ((secMediaPlayerData != null ? secMediaPlayerData.m2627getMediaData().size() : 0) > 0) {
                Context context = this.mContext;
                secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getClass();
                SecQSPanelResourceCommon.Companion.getClass();
                mediaPlayerCollapsedHeight = SecQSPanelResourceCommon.Companion.dp(R.dimen.sec_qs_media_player_height_expanded, context);
            } else if (!isLandscapeView()) {
                mediaPlayerCollapsedHeight = secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getMediaPlayerCollapsedHeight(this.mContext);
            }
        }
        this.mBarHeight = mediaPlayerCollapsedHeight;
        this.mBarTopMargin = this.mContext.getResources().getDimensionPixelSize(R.dimen.bar_top_margin);
        if (this.mPlayLastSongView != null) {
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(this.mPlayLastSongView);
            constraintSet.connect(R.id.play_last_song_header, 4, isLandscapeView() ? R.id.sec_play_last_song_text_landscape_guide_line : 0, 4);
            constraintSet.applyTo(this.mPlayLastSongView);
            this.mPlayLastSongView.findViewById(R.id.play_last_song_button).setVisibility(isLandscapeView() ? 0 : 8);
            this.mPlayLastSongView.findViewById(R.id.sec_media_output_text_portrait).setVisibility(!isLandscapeView() ? 0 : 8);
            this.mPlayLastSongView.findViewById(R.id.sec_media_output_text_landscape).setVisibility(isLandscapeView() ? 0 : 8);
        }
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mBarRootView.getLayoutParams();
        layoutParams.height = this.mBarHeight;
        if (isLandscapeView()) {
            layoutParams.weight = 2.0f;
            layoutParams.width = 0;
        } else {
            layoutParams.width = -1;
            layoutParams.setMarginEnd(0);
        }
        this.mBarRootView.setLayoutParams(layoutParams);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0043  */
    /* JADX WARN: Type inference failed for: r2v3, types: [java.lang.CharSequence] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateCloneMediaInfo() {
        MediaData mediaData;
        int currentPage;
        SecMediaHost secMediaHost = this.mMediaHost;
        if (secMediaHost != null) {
            ConcurrentHashMap concurrentHashMap = secMediaHost.mMediaPlayerData;
            MediaType mediaType = MediaType.QS;
            SecMediaPlayerData secMediaPlayerData = (SecMediaPlayerData) concurrentHashMap.get(mediaType);
            if (secMediaPlayerData == null) {
                mediaData = null;
            } else {
                SecMediaPlayerData secMediaPlayerData2 = (SecMediaPlayerData) secMediaHost.mMediaPlayerData.get(mediaType);
                if ((secMediaPlayerData2 != null ? secMediaPlayerData2.getMediaPlayerSize$1() : 0) > 0 && (currentPage = secMediaHost.mViewPagerHelper.getCurrentPage(mediaType)) >= 0 && currentPage < secMediaPlayerData.getSortedMediaPlayersSize()) {
                    mediaData = (MediaData) secMediaPlayerData.m2627getMediaData().get(secMediaPlayerData.getMediaPlayerFromSortedMediaPlayers(currentPage).mPlayerKey);
                }
            }
        }
        boolean z = mediaData != null;
        View view = this.mClonedBarView;
        if (view != null) {
            if (z) {
                view.requireViewById(R.id.sec_album_art).setForeground(new ColorDrawable(1946157056));
            } else {
                view.requireViewById(R.id.sec_album_art).setForeground(null);
            }
            this.mClonedBarView.requireViewById(R.id.media_playing_info).setVisibility(z ? 0 : 8);
            View viewRequireViewById = this.mClonedBarView.requireViewById(R.id.media_player_container);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) viewRequireViewById.getLayoutParams();
            layoutParams.height = this.mBarHeight;
            viewRequireViewById.setLayoutParams(layoutParams);
        }
        if (!z || this.mClonedBarView == null) {
            return;
        }
        Icon icon = mediaData.artwork;
        Drawable drawableLoadDrawable = icon != null ? icon.loadDrawable(this.mContext) : null;
        if (drawableLoadDrawable == null) {
            drawableLoadDrawable = new ColorDrawable(0);
        }
        ((ImageView) this.mClonedBarView.requireViewById(R.id.sec_album_art)).setImageDrawable(drawableLoadDrawable);
        Drawable drawableLoadDrawable2 = mediaData.artwork != null ? mediaData.appIcon.loadDrawable(this.mContext) : null;
        this.mClonedBarView.requireViewById(R.id.sec_icon_no_playing).setVisibility(8);
        this.mClonedBarView.requireViewById(R.id.sec_icon).setVisibility(0);
        ((ImageView) this.mClonedBarView.requireViewById(R.id.sec_icon)).setImageDrawable(drawableLoadDrawable2);
        MediaDeviceData mediaDeviceData = mediaData.device;
        String str = mediaDeviceData != null ? mediaDeviceData.name : "";
        this.mClonedBarView.requireViewById(R.id.no_media_playing).setVisibility(8);
        this.mClonedBarView.requireViewById(R.id.sec_device_name).setVisibility(0);
        ((TextView) this.mClonedBarView.requireViewById(R.id.sec_device_name)).setText(str);
        CharSequence charSequence = mediaData.song;
        if (charSequence == null) {
            charSequence = "";
        }
        ((TextView) this.mClonedBarView.requireViewById(R.id.sec_header_title)).setText(charSequence);
        ?? r2 = mediaData.artist;
        ((TextView) this.mClonedBarView.requireViewById(R.id.sec_header_artist)).setText(r2 != 0 ? r2 : "");
        this.mClonedBarView.requireViewById(R.id.sec_media_output_text).setVisibility(0);
        this.mClonedBarView.requireViewById(R.id.sec_media_output_text_with_no_media).setVisibility(8);
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void updateClonedBar() {
        updateCloneMediaInfo();
    }
}
