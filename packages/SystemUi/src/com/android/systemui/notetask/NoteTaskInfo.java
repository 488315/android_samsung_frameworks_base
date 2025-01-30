package com.android.systemui.notetask;

import android.os.UserHandle;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.picker.model.viewdata.AppInfoViewData$$ExternalSyntheticOutline0;
import com.android.systemui.notetask.NoteTaskLaunchMode;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        this.launchMode = (z || noteTaskEntryPoint == NoteTaskEntryPoint.WIDGET_PICKER_SHORTCUT_IN_MULTI_WINDOW_MODE) ? NoteTaskLaunchMode.Activity.INSTANCE : NoteTaskLaunchMode.AppBubble.INSTANCE;
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

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        int hashCode = (this.user.hashCode() + AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.uid, this.packageName.hashCode() * 31, 31)) * 31;
        NoteTaskEntryPoint noteTaskEntryPoint = this.entryPoint;
        int hashCode2 = (hashCode + (noteTaskEntryPoint == null ? 0 : noteTaskEntryPoint.hashCode())) * 31;
        boolean z = this.isKeyguardLocked;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        return hashCode2 + i;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("NoteTaskInfo(packageName=");
        sb.append(this.packageName);
        sb.append(", uid=");
        sb.append(this.uid);
        sb.append(", user=");
        sb.append(this.user);
        sb.append(", entryPoint=");
        sb.append(this.entryPoint);
        sb.append(", isKeyguardLocked=");
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m6m(sb, this.isKeyguardLocked, ")");
    }

    public /* synthetic */ NoteTaskInfo(String str, int i, UserHandle userHandle, NoteTaskEntryPoint noteTaskEntryPoint, boolean z, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, i, userHandle, (i2 & 8) != 0 ? null : noteTaskEntryPoint, (i2 & 16) != 0 ? false : z);
    }
}
