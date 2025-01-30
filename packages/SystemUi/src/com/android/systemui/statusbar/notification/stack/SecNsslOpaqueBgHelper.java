package com.android.systemui.statusbar.notification.stack;

import android.content.Context;
import com.android.systemui.Dependency;
import com.android.systemui.NotiRune;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.util.SecPanelOpaqueBgHelper;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SecNsslOpaqueBgHelper extends SecPanelOpaqueBgHelper {
    public final Context mContext;
    public boolean mQsExpandedImmediate;

    public SecNsslOpaqueBgHelper(Context context) {
        this.mContext = context;
    }

    public final boolean needOpaqueBg() {
        return (!NotiRune.NOTI_STYLE_TABLET_BG || ((StatusBarStateController) Dependency.get(StatusBarStateController.class)).getState() == 1 || this.mQsExpandedImmediate) ? false : true;
    }
}
