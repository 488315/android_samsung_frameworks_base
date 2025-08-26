package com.android.systemui.notetask;

import android.os.UserHandle;
import com.android.systemui.notetask.NoteTaskLaunchMode;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class NoteTaskInfo {
    public final NoteTaskEntryPoint entryPoint;
    public final boolean isKeyguardLocked;
    public final NoteTaskLaunchMode launchMode;
    public final String packageName;
    public final int uid;
    public final UserHandle user;

    public NoteTaskInfo(String str, int i, UserHandle userHandle, NoteTaskEntryPoint noteTaskEntryPoint, boolean z) {
        this.packageName = str;
        this.uid = i;
        this.user = userHandle;
        this.entryPoint = noteTaskEntryPoint;
        this.isKeyguardLocked = z;
        this.launchMode = (z || noteTaskEntryPoint == NoteTaskEntryPoint.WIDGET_PICKER_SHORTCUT_IN_MULTI_WINDOW_MODE) ? NoteTaskLaunchMode.Activity.INSTANCE : noteTaskEntryPoint == NoteTaskEntryPoint.QS_NOTES_TILE ? new NoteTaskLaunchMode.AppBubble(NoteTaskBubbleExpandBehavior.KEEP_IF_EXPANDED) : new NoteTaskLaunchMode.AppBubble(NoteTaskBubbleExpandBehavior.DEFAULT);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof NoteTaskInfo)) {
            return false;
        }
        NoteTaskInfo noteTaskInfo = (NoteTaskInfo) obj;
        return Intrinsics.areEqual(this.packageName, noteTaskInfo.packageName) && this.uid == noteTaskInfo.uid && Intrinsics.areEqual(this.user, noteTaskInfo.user) && this.entryPoint == noteTaskInfo.entryPoint && this.isKeyguardLocked == noteTaskInfo.isKeyguardLocked;
    }

    public final int hashCode() {
        int iHashCode = (this.user.hashCode() + ReorderTile$$ExternalSyntheticOutline0.m(this.uid, this.packageName.hashCode() * 31, 31)) * 31;
        NoteTaskEntryPoint noteTaskEntryPoint = this.entryPoint;
        return Boolean.hashCode(this.isKeyguardLocked) + ((iHashCode + (noteTaskEntryPoint == null ? 0 : noteTaskEntryPoint.hashCode())) * 31);
    }

    public final String toString() {
        UserHandle userHandle = this.user;
        StringBuilder sb = new StringBuilder("NoteTaskInfo(packageName=");
        sb.append(this.packageName);
        sb.append(", uid=");
        sb.append(this.uid);
        sb.append(", user=");
        sb.append(userHandle);
        sb.append(", entryPoint=");
        sb.append(this.entryPoint);
        sb.append(", isKeyguardLocked=");
        return MoveResult$$ExternalSyntheticOutline0.m(sb, this.isKeyguardLocked, ")");
    }

    public /* synthetic */ NoteTaskInfo(String str, int i, UserHandle userHandle, NoteTaskEntryPoint noteTaskEntryPoint, boolean z, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, i, userHandle, (i2 & 8) != 0 ? null : noteTaskEntryPoint, (i2 & 16) != 0 ? false : z);
    }
}
