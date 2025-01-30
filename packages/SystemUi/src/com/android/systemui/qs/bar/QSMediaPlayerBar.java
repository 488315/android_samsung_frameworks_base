package com.android.systemui.qs.bar;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.android.systemui.R;
import com.android.systemui.media.MediaPlayerBarExpandHelper;
import com.android.systemui.media.MediaType;
import com.android.systemui.media.SecMediaHost;
import com.android.systemui.media.SecMediaHost$$ExternalSyntheticLambda13;
import com.android.systemui.media.SecMediaPlayerData;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.bar.BarController;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import dagger.Lazy;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class QSMediaPlayerBar extends BarItemImpl implements StatusBarStateController.StateListener {
    public int mBarBottomMargin;
    public int mBarHeight;
    public int mCurrentOrientation;
    public int mExpandedHeight;
    public final SecMediaHost mMediaHost;
    public final QSMediaPlayerBar$$ExternalSyntheticLambda0 mMediaPanelVisibilityListener;
    public final SecQSPanelResourcePicker mResourcePicker;

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.qs.bar.QSMediaPlayerBar$$ExternalSyntheticLambda0] */
    public QSMediaPlayerBar(Context context, SecMediaHost secMediaHost, SecQSPanelResourcePicker secQSPanelResourcePicker, Lazy lazy, Lazy lazy2) {
        super(context);
        this.mMediaHost = secMediaHost;
        this.mResourcePicker = secQSPanelResourcePicker;
        this.mMediaPanelVisibilityListener = new SecMediaHost.MediaPanelVisibilityListener() { // from class: com.android.systemui.qs.bar.QSMediaPlayerBar$$ExternalSyntheticLambda0
            @Override // com.android.systemui.media.SecMediaHost.MediaPanelVisibilityListener
            public final void onMediaVisibilityChanged(boolean z) {
                QSMediaPlayerBar.this.showBar(z);
            }
        };
        updateBarHeight();
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
        int i = 0;
        SecMediaHost secMediaHost = this.mMediaHost;
        if (secMediaHost == null || !this.mShowing) {
            return 0;
        }
        MediaPlayerBarExpandHelper mediaPlayerBarExpandHelper = secMediaHost.mPlayerBarExpandHelper;
        if (mediaPlayerBarExpandHelper != null) {
            i = (int) (mediaPlayerBarExpandHelper.mediaPlayerData.getSortedMediaPlayersSize() > 0 ? mediaPlayerBarExpandHelper.playerExpansionHeight : 0.0f);
        }
        int i2 = i + this.mBarBottomMargin;
        if (this.mBarHeight != i2) {
            this.mBarHeight = i2;
        }
        return this.mBarHeight;
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final int getBarLayout() {
        return R.layout.qs_media_player_bar;
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void inflateViews(ViewGroup viewGroup) {
        if (this.mBarRootView == null) {
            View inflate = LayoutInflater.from(this.mContext).inflate(R.layout.qs_media_player_bar, viewGroup, false);
            this.mBarRootView = inflate;
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) inflate.getLayoutParams();
            layoutParams.height = this.mExpandedHeight;
            layoutParams.bottomMargin = this.mBarBottomMargin;
            this.mBarRootView.setLayoutParams(layoutParams);
            SecMediaHost secMediaHost = this.mMediaHost;
            if (secMediaHost != null) {
                secMediaHost.mVisibilityListeners.add(this.mMediaPanelVisibilityListener);
                MediaType mediaType = MediaType.QS;
                secMediaHost.addMediaFrame(this.mBarRootView.findViewById(R.id.media_player_container), mediaType);
                showBar(secMediaHost.getMediaPlayerSize(mediaType) > 0);
            }
        }
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
            updateBarHeight();
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mBarRootView.getLayoutParams();
            layoutParams.height = this.mExpandedHeight;
            layoutParams.bottomMargin = this.mBarBottomMargin;
            this.mBarRootView.setLayoutParams(layoutParams);
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
    public final void setCallback(BarController.C20874 c20874) {
        this.mMediaHost.mMediaBarCallback = c20874;
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void setListening(boolean z) {
        this.mListening = z;
        SecMediaHost secMediaHost = this.mMediaHost;
        if (secMediaHost != null) {
            MediaType mediaType = MediaType.QS;
            if (mediaType.getSupportExpandable()) {
                if (secMediaHost.mLocalListening == z) {
                    secMediaHost.mPlayerBarExpandHelper.setPlayerBarExpansion();
                    return;
                }
                int i = 1;
                boolean z2 = z && secMediaHost.mStatusBarStateController.getState() != 1;
                if (secMediaHost.getMediaPlayerNum(false, mediaType) > 0) {
                    SecMediaHost.iteratePlayers((SecMediaPlayerData) secMediaHost.mMediaPlayerData.get(mediaType), new SecMediaHost$$ExternalSyntheticLambda13(z2, i));
                    if (!z2) {
                        secMediaHost.mViewPagerHelper.setCurrentPage(1, false, mediaType);
                    }
                }
                secMediaHost.mLocalListening = z2;
            }
        }
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void setQsFullyExpanded(boolean z) {
        SecMediaHost secMediaHost = this.mMediaHost;
        if (secMediaHost != null) {
            SecMediaHost.iteratePlayers((SecMediaPlayerData) secMediaHost.mMediaPlayerData.get(MediaType.QS), new SecMediaHost$$ExternalSyntheticLambda13(z, 0));
        }
    }

    public final void updateBarHeight() {
        Context context = this.mContext;
        this.mResourcePicker.getClass();
        this.mExpandedHeight = context.getResources().getDimensionPixelSize(R.dimen.sec_qs_media_player_height_expanded);
        this.mBarBottomMargin = this.mContext.getResources().getDimensionPixelSize(R.dimen.sec_qs_media_player_bar_bottom_margin);
    }
}
