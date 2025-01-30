package com.android.systemui.media;

import android.content.res.Configuration;
import android.widget.ImageView;
import com.android.systemui.media.SecMediaHost;
import com.android.systemui.util.ConfigurationState;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class SecMediaHost$$ExternalSyntheticLambda15 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Comparable f$0;

    public /* synthetic */ SecMediaHost$$ExternalSyntheticLambda15(Comparable comparable, int i) {
        this.$r8$classId = i;
        this.f$0 = comparable;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ImageView imageView;
        switch (this.$r8$classId) {
            case 0:
                Configuration configuration = (Configuration) this.f$0;
                SecMediaControlPanel secMediaControlPanel = (SecMediaControlPanel) obj;
                secMediaControlPanel.updateWidth();
                ConfigurationState configurationState = secMediaControlPanel.mLastConfigurationState;
                if (configurationState.needToUpdate(configuration)) {
                    configurationState.update(configuration);
                    SecPlayerViewHolder secPlayerViewHolder = secMediaControlPanel.mViewHolder;
                    if (secPlayerViewHolder != null && (imageView = secPlayerViewHolder.expandIcon) != null) {
                        imageView.setVisibility((!secMediaControlPanel.expandIconNeedToShow() || secMediaControlPanel.mIsEmptyPlayer) ? 4 : 0);
                    }
                    secMediaControlPanel.calculateSongTitleWidth();
                    secMediaControlPanel.updateExpandAnimator();
                    secMediaControlPanel.mExpanded = secMediaControlPanel.mFraction != 0.0f;
                    break;
                }
                break;
            default:
                ((SecMediaHost.MediaPanelVisibilityListener) obj).onMediaVisibilityChanged(((Boolean) this.f$0).booleanValue());
                break;
        }
    }
}
