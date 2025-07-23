package com.android.systemui.qs.bar;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.android.systemui.R;
import com.android.systemui.media.MediaOutputHelper;
import com.android.systemui.media.MediaType;
import com.android.systemui.media.SecMediaHost;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.SecQSDetailController;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.bar.BarController;
import com.android.systemui.shade.data.repository.ShadeRepository;
import com.android.systemui.shade.data.repository.ShadeRepositoryImpl;
import dagger.Lazy;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                QSMediaPlayerBar.this.updateBar();
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
            View inflate = LayoutInflater.from(this.mContext).inflate(R.layout.qs_media_player_bar, viewGroup, false);
            this.mBarRootView = inflate;
            this.mMediaOutputHelper.mediaRootView = inflate;
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
                    public final void onClick(View view) {
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
                    public final void onClick(View view) {
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
        View inflate = LayoutInflater.from(this.mContext).inflate(R.layout.qs_customizer_mediabar_dummy, (ViewGroup) null);
        this.mClonedBarView = inflate;
        if (inflate != null) {
            ColoredBGHelper coloredBGHelper = this.mBGColorHelper;
            if (coloredBGHelper != null) {
                coloredBGHelper.addBarBackground(inflate.requireViewById(R.id.sec_album_art), false);
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
        View findViewById = view.findViewById(R.id.sec_album_art);
        ColoredBGHelper coloredBGHelper = this.mBGColorHelper;
        if (coloredBGHelper == null || findViewById == null) {
            return;
        }
        coloredBGHelper.removeFromBarBackground(findViewById);
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

    /* JADX WARN: Code restructure failed: missing block: B:6:0x001d, code lost:
    
        if ((r2 != null ? r2.m2612getMediaData().size() : 0) > 0) goto L12;
     */
    /* JADX WARN: Removed duplicated region for block: B:10:0x005f  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00d1  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00d8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateBar() {
        /*
            r5 = this;
            r0 = 0
            com.android.systemui.qs.SecQSPanelResourcePicker r1 = r5.mResourcePicker
            com.android.systemui.media.SecMediaHost r2 = r5.mMediaHost
            if (r2 == 0) goto L20
            com.android.systemui.media.MediaType r3 = com.android.systemui.media.MediaType.QS
            java.util.concurrent.ConcurrentHashMap r2 = r2.mMediaPlayerData
            java.lang.Object r2 = r2.get(r3)
            com.android.systemui.media.SecMediaPlayerData r2 = (com.android.systemui.media.SecMediaPlayerData) r2
            if (r2 == 0) goto L1c
            java.util.concurrent.ConcurrentHashMap r2 = r2.m2612getMediaData()
            int r2 = r2.size()
            goto L1d
        L1c:
            r2 = r0
        L1d:
            if (r2 <= 0) goto L20
            goto L26
        L20:
            boolean r2 = r5.isLandscapeView()
            if (r2 == 0) goto L3e
        L26:
            android.content.Context r2 = r5.mContext
            com.android.systemui.qs.panelresource.SecQSPanelResourcePickHelper r1 = r1.resourcePickHelper
            com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker r1 = r1.getTargetPicker()
            r1.getClass()
            com.android.systemui.qs.panelresource.SecQSPanelResourceCommon$Companion r1 = com.android.systemui.qs.panelresource.SecQSPanelResourceCommon.Companion
            r1.getClass()
            r1 = 2131169739(0x7f0711cb, float:1.7953817E38)
            int r1 = com.android.systemui.qs.panelresource.SecQSPanelResourceCommon.Companion.dp(r1, r2)
            goto L4a
        L3e:
            android.content.Context r2 = r5.mContext
            com.android.systemui.qs.panelresource.SecQSPanelResourcePickHelper r1 = r1.resourcePickHelper
            com.android.systemui.qs.panelresource.SecQSPanelResourceNormalPicker r1 = r1.getTargetPicker()
            int r1 = r1.getMediaPlayerCollapsedHeight(r2)
        L4a:
            r5.mBarHeight = r1
            android.content.Context r1 = r5.mContext
            android.content.res.Resources r1 = r1.getResources()
            r2 = 2131165347(0x7f0700a3, float:1.7944909E38)
            int r1 = r1.getDimensionPixelSize(r2)
            r5.mBarTopMargin = r1
            androidx.constraintlayout.widget.ConstraintLayout r1 = r5.mPlayLastSongView
            if (r1 == 0) goto Lbf
            androidx.constraintlayout.widget.ConstraintSet r1 = new androidx.constraintlayout.widget.ConstraintSet
            r1.<init>()
            androidx.constraintlayout.widget.ConstraintLayout r2 = r5.mPlayLastSongView
            r1.clone(r2)
            boolean r2 = r5.isLandscapeView()
            if (r2 == 0) goto L73
            r2 = 2131364707(0x7f0a0b63, float:1.8349259E38)
            goto L74
        L73:
            r2 = r0
        L74:
            r3 = 2131364218(0x7f0a097a, float:1.8348267E38)
            r4 = 4
            r1.connect(r3, r4, r2, r4)
            androidx.constraintlayout.widget.ConstraintLayout r2 = r5.mPlayLastSongView
            r1.applyTo(r2)
            androidx.constraintlayout.widget.ConstraintLayout r1 = r5.mPlayLastSongView
            r2 = 2131364217(0x7f0a0979, float:1.8348265E38)
            android.view.View r1 = r1.findViewById(r2)
            boolean r2 = r5.isLandscapeView()
            r3 = 8
            if (r2 == 0) goto L93
            r2 = r0
            goto L94
        L93:
            r2 = r3
        L94:
            r1.setVisibility(r2)
            androidx.constraintlayout.widget.ConstraintLayout r1 = r5.mPlayLastSongView
            r2 = 2131364692(0x7f0a0b54, float:1.8349228E38)
            android.view.View r1 = r1.findViewById(r2)
            boolean r2 = r5.isLandscapeView()
            if (r2 != 0) goto La8
            r2 = r0
            goto La9
        La8:
            r2 = r3
        La9:
            r1.setVisibility(r2)
            androidx.constraintlayout.widget.ConstraintLayout r1 = r5.mPlayLastSongView
            r2 = 2131364691(0x7f0a0b53, float:1.8349226E38)
            android.view.View r1 = r1.findViewById(r2)
            boolean r2 = r5.isLandscapeView()
            if (r2 == 0) goto Lbc
            r3 = r0
        Lbc:
            r1.setVisibility(r3)
        Lbf:
            android.view.View r1 = r5.mBarRootView
            android.view.ViewGroup$LayoutParams r1 = r1.getLayoutParams()
            android.widget.LinearLayout$LayoutParams r1 = (android.widget.LinearLayout.LayoutParams) r1
            int r2 = r5.mBarHeight
            r1.height = r2
            boolean r2 = r5.isLandscapeView()
            if (r2 == 0) goto Ld8
            r2 = 1073741824(0x40000000, float:2.0)
            r1.weight = r2
            r1.width = r0
            goto Lde
        Ld8:
            r2 = -1
            r1.width = r2
            r1.setMarginEnd(r0)
        Lde:
            android.view.View r5 = r5.mBarRootView
            r5.setLayoutParams(r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.bar.QSMediaPlayerBar.updateBar():void");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0046  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0053  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0048  */
    /* JADX WARN: Type inference failed for: r2v3, types: [java.lang.CharSequence] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateCloneMediaInfo() {
        /*
            Method dump skipped, instructions count: 338
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.bar.QSMediaPlayerBar.updateCloneMediaInfo():void");
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void updateClonedBar() {
        updateCloneMediaInfo();
    }
}
