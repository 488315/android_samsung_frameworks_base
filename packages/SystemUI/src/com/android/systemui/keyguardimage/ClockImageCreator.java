package com.android.systemui.keyguardimage;

import android.content.Context;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.cover.CoverScreenManager;
import com.android.systemui.facewidget.plugin.ExternalClockProvider;
import com.android.systemui.facewidget.plugin.PluginFaceWidgetManager;
import com.android.systemui.subscreen.SubScreenManager;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class ClockImageCreator implements ImageCreator {
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:127:0x00d8  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x00e0  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x006f  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0077  */
    @Override // com.android.systemui.keyguardimage.ImageCreator
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.graphics.Bitmap createImage(com.android.systemui.keyguardimage.ImageOptionCreator.ImageOption r19, android.graphics.Point r20) {
        /*
            Method dump skipped, instructions count: 845
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguardimage.ClockImageCreator.createImage(com.android.systemui.keyguardimage.ImageOptionCreator$ImageOption, android.graphics.Point):android.graphics.Bitmap");
    }
}
