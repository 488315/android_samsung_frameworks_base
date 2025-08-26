package com.android.wm.shell.windowdecor;

import android.content.Intent;
import com.android.wm.shell.windowdecor.DesktopModeWindowDecoration;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final /* synthetic */ class DesktopModeWindowDecoration$$ExternalSyntheticLambda12 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DesktopModeWindowDecoration f$0;

    public /* synthetic */ DesktopModeWindowDecoration$$ExternalSyntheticLambda12(DesktopModeWindowDecoration desktopModeWindowDecoration, int i) {
        this.$r8$classId = i;
        this.f$0 = desktopModeWindowDecoration;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                DesktopModeWindowDecoration desktopModeWindowDecoration = this.f$0;
                desktopModeWindowDecoration.mOpenInBrowserClickListener.accept((Intent) obj);
                DesktopModeWindowDecoration.CapturedLink capturedLink = desktopModeWindowDecoration.mCapturedLink;
                if (capturedLink != null) {
                    capturedLink.mUsed = true;
                }
                return Unit.INSTANCE;
            default:
                DesktopModeWindowDecoration desktopModeWindowDecoration2 = this.f$0;
                desktopModeWindowDecoration2.getClass();
                desktopModeWindowDecoration2.mIsMaximizeMenuHovered = ((Boolean) obj).booleanValue();
                desktopModeWindowDecoration2.onMaximizeHoverStateChanged();
                return null;
        }
    }
}
