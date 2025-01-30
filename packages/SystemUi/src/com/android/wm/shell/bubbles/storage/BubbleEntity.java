package com.android.wm.shell.bubbles.storage;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.picker.model.AppInfo$$ExternalSyntheticOutline0;
import androidx.picker.model.viewdata.AppInfoViewData$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class BubbleEntity {
    public final int desiredHeight;
    public final int desiredHeightResId;
    public final boolean isDismissable;
    public final String key;
    public final String locus;
    public final String packageName;
    public final String shortcutId;
    public final int taskId;
    public final String title;
    public final int userId;

    public BubbleEntity(int i, String str, String str2, String str3, int i2, int i3, String str4, int i4, String str5, boolean z) {
        this.userId = i;
        this.packageName = str;
        this.shortcutId = str2;
        this.key = str3;
        this.desiredHeight = i2;
        this.desiredHeightResId = i3;
        this.title = str4;
        this.taskId = i4;
        this.locus = str5;
        this.isDismissable = z;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BubbleEntity)) {
            return false;
        }
        BubbleEntity bubbleEntity = (BubbleEntity) obj;
        return this.userId == bubbleEntity.userId && Intrinsics.areEqual(this.packageName, bubbleEntity.packageName) && Intrinsics.areEqual(this.shortcutId, bubbleEntity.shortcutId) && Intrinsics.areEqual(this.key, bubbleEntity.key) && this.desiredHeight == bubbleEntity.desiredHeight && this.desiredHeightResId == bubbleEntity.desiredHeightResId && Intrinsics.areEqual(this.title, bubbleEntity.title) && this.taskId == bubbleEntity.taskId && Intrinsics.areEqual(this.locus, bubbleEntity.locus) && this.isDismissable == bubbleEntity.isDismissable;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        int m42m = AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.desiredHeightResId, AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.desiredHeight, AppInfo$$ExternalSyntheticOutline0.m41m(this.key, AppInfo$$ExternalSyntheticOutline0.m41m(this.shortcutId, AppInfo$$ExternalSyntheticOutline0.m41m(this.packageName, Integer.hashCode(this.userId) * 31, 31), 31), 31), 31), 31);
        String str = this.title;
        int m42m2 = AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.taskId, (m42m + (str == null ? 0 : str.hashCode())) * 31, 31);
        String str2 = this.locus;
        int hashCode = (m42m2 + (str2 != null ? str2.hashCode() : 0)) * 31;
        boolean z = this.isDismissable;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        return hashCode + i;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("BubbleEntity(userId=");
        sb.append(this.userId);
        sb.append(", packageName=");
        sb.append(this.packageName);
        sb.append(", shortcutId=");
        sb.append(this.shortcutId);
        sb.append(", key=");
        sb.append(this.key);
        sb.append(", desiredHeight=");
        sb.append(this.desiredHeight);
        sb.append(", desiredHeightResId=");
        sb.append(this.desiredHeightResId);
        sb.append(", title=");
        sb.append(this.title);
        sb.append(", taskId=");
        sb.append(this.taskId);
        sb.append(", locus=");
        sb.append(this.locus);
        sb.append(", isDismissable=");
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m6m(sb, this.isDismissable, ")");
    }

    public /* synthetic */ BubbleEntity(int i, String str, String str2, String str3, int i2, int i3, String str4, int i4, String str5, boolean z, int i5, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, str, str2, str3, i2, i3, (i5 & 64) != 0 ? null : str4, i4, (i5 & 256) != 0 ? null : str5, (i5 & 512) != 0 ? false : z);
    }
}
