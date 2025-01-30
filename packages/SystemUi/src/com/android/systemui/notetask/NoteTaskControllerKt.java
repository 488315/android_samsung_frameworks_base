package com.android.systemui.notetask;

import android.content.Intent;
import com.android.systemui.notetask.NoteTaskLaunchMode;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class NoteTaskControllerKt {
    public static final Intent access$createNoteTaskIntent(NoteTaskInfo noteTaskInfo) {
        Intent intent = new Intent("android.intent.action.CREATE_NOTE");
        intent.setPackage(noteTaskInfo.packageName);
        intent.putExtra("android.intent.extra.USE_STYLUS_MODE", noteTaskInfo.entryPoint != NoteTaskEntryPoint.KEYBOARD_SHORTCUT);
        intent.addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
        if (Intrinsics.areEqual(noteTaskInfo.launchMode, NoteTaskLaunchMode.Activity.INSTANCE)) {
            intent.addFlags(134217728);
            intent.addFlags(524288);
        }
        return intent;
    }
}
