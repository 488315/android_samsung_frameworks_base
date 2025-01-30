package com.android.wm.shell.protolog;

import com.android.internal.protolog.common.IProtoLogGroup;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public enum ShellProtoLogGroup implements IProtoLogGroup {
    WM_SHELL_INIT(true, true, true, "WindowManagerShell"),
    WM_SHELL_TASK_ORG(true, true, false, "WindowManagerShell"),
    WM_SHELL_TRANSITIONS(true, true, true, "WindowManagerShell"),
    WM_SHELL_RECENTS_TRANSITION(true, true, true, "ShellRecents"),
    WM_SHELL_DRAG_AND_DROP(true, true, true, "WindowManagerShell"),
    WM_SHELL_STARTING_WINDOW(true, true, false, "ShellStartingWindow"),
    WM_SHELL_BACK_PREVIEW(true, true, true, "ShellBackPreview"),
    WM_SHELL_RECENT_TASKS(true, true, false, "WindowManagerShell"),
    WM_SHELL_PICTURE_IN_PICTURE(true, true, true, "WindowManagerShell"),
    WM_SHELL_SPLIT_SCREEN(true, true, true, "ShellSplitScreen"),
    WM_SHELL_SYSUI_EVENTS(true, true, false, "WindowManagerShell"),
    WM_SHELL_DESKTOP_MODE(true, true, false, "WindowManagerShell"),
    WM_SHELL_FLOATING_APPS(true, true, false, "WindowManagerShell"),
    WM_SHELL_FOLDABLE(true, true, false, "WindowManagerShell"),
    TEST_GROUP(true, true, false, "WindowManagerShellProtoLogTest");

    private final boolean mEnabled;
    private volatile boolean mLogToLogcat;
    private volatile boolean mLogToProto;
    private final String mTag;

    ShellProtoLogGroup(boolean z, boolean z2, boolean z3, String str) {
        this.mEnabled = z;
        this.mLogToProto = z2;
        this.mLogToLogcat = z3;
        this.mTag = str;
    }

    @Override // com.android.internal.protolog.common.IProtoLogGroup
    public String getTag() {
        return this.mTag;
    }

    public boolean isEnabled() {
        return this.mEnabled;
    }

    public boolean isLogToAny() {
        return this.mLogToLogcat || this.mLogToProto;
    }

    @Override // com.android.internal.protolog.common.IProtoLogGroup
    public boolean isLogToLogcat() {
        return this.mLogToLogcat;
    }

    @Override // com.android.internal.protolog.common.IProtoLogGroup
    public boolean isLogToProto() {
        return this.mLogToProto;
    }

    public void setLogToLogcat(boolean z) {
        this.mLogToLogcat = z;
    }

    public void setLogToProto(boolean z) {
        this.mLogToProto = z;
    }
}
