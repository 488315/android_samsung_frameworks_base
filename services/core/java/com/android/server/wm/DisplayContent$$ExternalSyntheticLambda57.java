package com.android.server.wm;

import android.view.WindowManager;

import com.samsung.android.view.SemWindowManager;

import java.util.ArrayList;
import java.util.function.Consumer;

public final /* synthetic */ class DisplayContent$$ExternalSyntheticLambda57 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DisplayContent f$0;
    public final /* synthetic */ ArrayList f$1;

    public /* synthetic */ DisplayContent$$ExternalSyntheticLambda57(
            DisplayContent displayContent, ArrayList arrayList, int i) {
        this.$r8$classId = i;
        this.f$0 = displayContent;
        this.f$1 = arrayList;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i;
        switch (this.$r8$classId) {
            case 0:
                DisplayContent displayContent = this.f$0;
                ArrayList arrayList = this.f$1;
                WindowState windowState = (WindowState) obj;
                displayContent.getClass();
                if ((windowState.mAttrs.privateFlags & 1048576) == 0
                        && !windowState.hasRelativeLayer()
                        && windowState.isVisible()
                        && !windowState.mIsWallpaper
                        && (i = windowState.mAttrs.type) >= 2000
                        && i <= 2999) {
                    arrayList.add(windowState.mSurfaceControl);
                    break;
                }
                break;
            default:
                DisplayContent displayContent2 = this.f$0;
                ArrayList arrayList2 = this.f$1;
                WindowState windowState2 = (WindowState) obj;
                displayContent2.getClass();
                if (windowState2.isVisible()) {
                    SemWindowManager.VisibleWindowInfo visibleWindowInfo =
                            new SemWindowManager.VisibleWindowInfo();
                    WindowManager.LayoutParams layoutParams = windowState2.mAttrs;
                    visibleWindowInfo.packageName = layoutParams.packageName;
                    visibleWindowInfo.name = layoutParams.getTitle().toString();
                    visibleWindowInfo.type = windowState2.mAttrs.type;
                    visibleWindowInfo.focused = windowState2.equals(displayContent2.mCurrentFocus);
                    visibleWindowInfo.lastFocused = windowState2.equals(displayContent2.mOldFocus);
                    arrayList2.add(visibleWindowInfo);
                    break;
                }
                break;
        }
    }
}
