package com.android.systemui.qs;

import android.content.res.Configuration;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SecDarkModeEasel {
    public int mAssetSeq;
    public final PictureSubject mPictureSubject;
    public int mThemeSeq;
    public int mUIMode;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface PictureSubject {
        void drawDarkModelPicture();
    }

    public SecDarkModeEasel(PictureSubject pictureSubject) {
        this.mPictureSubject = pictureSubject;
    }

    public final void updateColors(Configuration configuration) {
        int i = this.mThemeSeq;
        int i2 = configuration.themeSeq;
        if (i == i2 && this.mAssetSeq == configuration.assetsSeq && this.mUIMode == configuration.uiMode) {
            return;
        }
        this.mThemeSeq = i2;
        this.mAssetSeq = configuration.assetsSeq;
        this.mUIMode = configuration.uiMode;
        this.mPictureSubject.drawDarkModelPicture();
    }
}
