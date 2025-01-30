package com.android.p038wm.shell.pip.p039tv;

import com.android.p038wm.shell.protolog.ShellProtoLogCache;
import com.android.p038wm.shell.protolog.ShellProtoLogGroup;
import com.android.p038wm.shell.protolog.ShellProtoLogImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class TvPipMenuEduTextDrawer$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ TvPipMenuEduTextDrawer f$0;

    public /* synthetic */ TvPipMenuEduTextDrawer$$ExternalSyntheticLambda0(TvPipMenuEduTextDrawer tvPipMenuEduTextDrawer, int i) {
        this.$r8$classId = i;
        this.f$0 = tvPipMenuEduTextDrawer;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                TvPipMenuEduTextDrawer.$r8$lambda$2GDlmZOpGSSk_mkyIVJnZDt_4Mk(this.f$0);
                break;
            default:
                TvPipMenuEduTextDrawer tvPipMenuEduTextDrawer = this.f$0;
                tvPipMenuEduTextDrawer.getClass();
                if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
                    ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 35922217, 4, "%s: startScrollEduText(), repeat=%d", "TvPipMenuEduTextDrawer", Long.valueOf(tvPipMenuEduTextDrawer.mEduTextView.getMarqueeRepeatLimit()));
                }
                tvPipMenuEduTextDrawer.mEduTextView.setSelected(true);
                break;
        }
    }
}
