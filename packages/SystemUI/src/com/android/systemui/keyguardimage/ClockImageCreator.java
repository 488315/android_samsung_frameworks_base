package com.android.systemui.keyguardimage;

import android.content.Context;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.cover.CoverScreenManager;
import com.android.systemui.facewidget.plugin.ExternalClockProvider;
import com.android.systemui.facewidget.plugin.PluginFaceWidgetManager;
import com.android.systemui.subscreen.SubScreenManager;

public final class ClockImageCreator implements ImageCreator {
    public final Context mContext;
    public final CoverScreenManager mCoverScreenManager;
    public final SubScreenManager mSubScreenManager;
    public final ExternalClockProvider mClockProvider = (ExternalClockProvider) Dependency.sDependency.getDependencyInner(ExternalClockProvider.class);
    public final PluginFaceWidgetManager mPluginFaceWidget = (PluginFaceWidgetManager) Dependency.sDependency.getDependencyInner(PluginFaceWidgetManager.class);

    public ClockImageCreator(Context context) {
        this.mContext = context;
        this.mSubScreenManager = LsRune.SUBSCREEN_UI ? (SubScreenManager) Dependency.sDependency.getDependencyInner(SubScreenManager.class) : null;
        this.mCoverScreenManager = LsRune.COVER_SUPPORTED ? (CoverScreenManager) Dependency.sDependency.getDependencyInner(CoverScreenManager.class) : null;
    }

    /* JADX WARN: Removed duplicated region for block: B:128:0x00d6  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x00de  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x006c  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0074  */
    @Override // com.android.systemui.keyguardimage.ImageCreator
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.graphics.Bitmap createImage(com.android.systemui.keyguardimage.ImageOptionCreator.ImageOption r17, android.graphics.Point r18) {
        /*
            Method dump skipped, instructions count: 838
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguardimage.ClockImageCreator.createImage(com.android.systemui.keyguardimage.ImageOptionCreator$ImageOption, android.graphics.Point):android.graphics.Bitmap");
    }
}
