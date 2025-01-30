package com.android.server.wm;

import android.view.InsetsState;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class TaskbarController {
    public final DisplayPolicy mDisplayPolicy;
    public final WindowManagerService mService;
    public WindowState mTaskbarWin;

    public ArrayList adjustInsetsControlForTaskbar(ArrayList arrayList) {
        return arrayList;
    }

    public InsetsState adjustInsetsForTaskbar(InsetsState insetsState) {
        return insetsState;
    }

    public boolean isHiddenBar(WindowState windowState) {
        return false;
    }

    public TaskbarController(DisplayPolicyExt displayPolicyExt) {
        this.mService = displayPolicyExt.mService;
        this.mDisplayPolicy = displayPolicyExt.mDisplayPolicy;
    }

    public void onTaskbarAddedLw(WindowState windowState) {
        this.mTaskbarWin = windowState;
    }

    public void onTaskbarRemovedLw() {
        this.mTaskbarWin = null;
        if (getNavigationBarProvider() == null || getNavigationBarProvider().getControlTarget() == null) {
            return;
        }
        this.mDisplayPolicy.mDisplayContent.getInsetsStateController().notifyControlChanged(getNavigationBarProvider().getControlTarget());
    }

    public boolean isTaskbar(WindowState windowState) {
        return this.mTaskbarWin == windowState;
    }

    public boolean isTaskbarToken(WindowToken windowToken) {
        WindowState windowState = this.mTaskbarWin;
        return windowState != null && windowState.mToken == windowToken;
    }

    public boolean hasTaskbar() {
        WindowState windowState = this.mTaskbarWin;
        return windowState != null && windowState.mHasSurface;
    }

    public boolean isTaskbarVisible() {
        return hasTaskbar() && this.mTaskbarWin.isVisible();
    }

    public final InsetsSourceProvider getNavigationBarProvider() {
        if (this.mDisplayPolicy.getNavigationBar() == null) {
            return null;
        }
        return this.mDisplayPolicy.getNavigationBar().getControllableInsetProvider();
    }
}
