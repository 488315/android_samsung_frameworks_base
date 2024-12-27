package com.android.systemui.qs.bar;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.media.MediaOutputHelper;
import com.android.systemui.media.MediaType;
import com.android.systemui.media.SecMediaHost;
import com.android.systemui.media.SecMediaPlayerData;
import com.android.systemui.plugins.qs.DetailAdapter;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.SecQSDetailController;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.bar.BarController;
import com.android.systemui.shade.data.repository.ShadeRepository;
import com.android.systemui.shade.data.repository.ShadeRepositoryImpl;
import dagger.Lazy;
import java.util.function.Consumer;

public final class QSMediaPlayerBar extends BarItemImpl implements StatusBarStateController.StateListener {
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
        return (QpRune.QUICK_TABLET || context.getResources().getConfiguration().orientation != 2) ? 4 : 2;
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
                final int i3 = 0;
                Runnable runnable = new Runnable(this) { // from class: com.android.systemui.qs.bar.QSMediaPlayerBar$$ExternalSyntheticLambda3
                    public final /* synthetic */ QSMediaPlayerBar f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        int i4 = i3;
                        QSMediaPlayerBar qSMediaPlayerBar = this.f$0;
                        switch (i4) {
                            case 0:
                                qSMediaPlayerBar.mSecQSDetailController.showTargetDetail((DetailAdapter) qSMediaPlayerBar.mSoundCraftQpDetailAdapterLazy.get());
                                break;
                            default:
                                qSMediaPlayerBar.mSecQSDetailController.closeTargetDetail((DetailAdapter) qSMediaPlayerBar.mSoundCraftQpDetailAdapterLazy.get());
                                break;
                        }
                    }
                };
                Runnable runnable2 = new Runnable(this) { // from class: com.android.systemui.qs.bar.QSMediaPlayerBar$$ExternalSyntheticLambda3
                    public final /* synthetic */ QSMediaPlayerBar f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        int i4 = i2;
                        QSMediaPlayerBar qSMediaPlayerBar = this.f$0;
                        switch (i4) {
                            case 0:
                                qSMediaPlayerBar.mSecQSDetailController.showTargetDetail((DetailAdapter) qSMediaPlayerBar.mSoundCraftQpDetailAdapterLazy.get());
                                break;
                            default:
                                qSMediaPlayerBar.mSecQSDetailController.closeTargetDetail((DetailAdapter) qSMediaPlayerBar.mSoundCraftQpDetailAdapterLazy.get());
                                break;
                        }
                    }
                };
                secMediaHost.mBudsDetailOpenRunnable = runnable;
                secMediaHost.mBudsDetailCloseRunnable = runnable2;
            }
            updateBar();
        }
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void makeCloneBar() {
        View inflate = LayoutInflater.from(this.mContext).inflate(R.layout.qs_customizer_mediabar_dummy, (ViewGroup) null);
        this.mClonedBarView = inflate;
        ColoredBGHelper coloredBGHelper = this.mBGColorHelper;
        if (coloredBGHelper != null) {
            coloredBGHelper.addBarBackground(inflate.requireViewById(R.id.sec_album_art), false);
        }
        this.mClonedBarView.measure(View.MeasureSpec.makeMeasureSpec(0, 1073741824), View.MeasureSpec.makeMeasureSpec(0, 1073741824));
        View view = this.mClonedBarView;
        view.layout(0, 0, view.getMeasuredWidth(), this.mClonedBarView.getMeasuredHeight());
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
    public final void setCallback(BarController.AnonymousClass4 anonymousClass4) {
        this.mMediaHost.mMediaBarCallback = anonymousClass4;
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void setListening(boolean z) {
        this.mListening = z;
        SecMediaHost secMediaHost = this.mMediaHost;
        if (secMediaHost != null) {
            boolean z2 = z && ((Boolean) ((ShadeRepositoryImpl) this.mShadeRepository).legacyIsQsExpanded.$$delegate_0.getValue()).booleanValue();
            MediaType mediaType = MediaType.QS;
            if (mediaType.getSupportExpandable() && secMediaHost.mLocalListening != z2) {
                final boolean z3 = z2 && secMediaHost.mStatusBarStateController.getState() != 1;
                if (secMediaHost.getMediaPlayerNum(mediaType) > 0) {
                    SecMediaHost.iteratePlayers((SecMediaPlayerData) secMediaHost.mMediaPlayerData.get(mediaType), new Consumer() { // from class: com.android.systemui.media.SecMediaHost$$ExternalSyntheticLambda13
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            ((SecMediaControlPanel) obj).setListening(z3);
                        }
                    });
                    if (!z3) {
                        secMediaHost.mViewPagerHelper.setCurrentPage(0, false, mediaType);
                    }
                }
                secMediaHost.mLocalListening = z3;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0060  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateBar() {
        /*
            Method dump skipped, instructions count: 248
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.bar.QSMediaPlayerBar.updateBar():void");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0026  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x004d  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x006b  */
    /* JADX WARN: Removed duplicated region for block: B:40:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:41:0x004f  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0037  */
    /* JADX WARN: Type inference failed for: r0v6, types: [java.lang.CharSequence] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateCloneMediaInfo() {
        /*
            Method dump skipped, instructions count: 273
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.bar.QSMediaPlayerBar.updateCloneMediaInfo():void");
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void updateClonedBar() {
        updateCloneMediaInfo();
    }
}
