package com.android.systemui.notetask;

import android.content.Intent;
import com.android.systemui.notetask.NoteTaskLaunchMode;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class NoteTaskControllerKt {
    public static final Intent access$createNoteTaskIntent(NoteTaskInfo noteTaskInfo, boolean z) {
        Intent intent = new Intent("android.intent.action.CREATE_NOTE");
        intent.setPackage(noteTaskInfo.packageName);
        intent.putExtra("android.intent.extra.USE_STYLUS_MODE", z);
        intent.addFlags(268435456);
        if (Intrinsics.areEqual(noteTaskInfo.launchMode, NoteTaskLaunchMode.Activity.INSTANCE)) {
            intent.addFlags(134217728);
            intent.addFlags(NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME);
        }
        return intent;
    }
}
