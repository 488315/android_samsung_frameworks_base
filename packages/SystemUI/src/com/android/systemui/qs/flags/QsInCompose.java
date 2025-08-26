package com.android.systemui.qs.flags;

import com.android.systemui.flags.FlagToken;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;

/* loaded from: classes2.dex */
public final class QsInCompose {
    public static final /* synthetic */ int $r8$clinit = 0;

    static {
        new QsInCompose();
        int i = SceneContainerFlag.$r8$clinit;
        new FlagToken("com.android.systemui.scene_container", false);
    }

    private QsInCompose() {
    }
}
