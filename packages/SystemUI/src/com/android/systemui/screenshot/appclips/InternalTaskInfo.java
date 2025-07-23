package com.android.systemui.screenshot.appclips;

import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class InternalTaskInfo {
    public final PackageManager packageManager;
    public final int taskId;
    public final Lazy topActivityAppIcon$delegate;
    public final Lazy topActivityAppName$delegate;
    public final ActivityInfo topActivityInfo;
    public final String topActivityPackageName;
    public final int userId;

    public InternalTaskInfo(ActivityInfo activityInfo, int i, int i2, PackageManager packageManager) {
        this.topActivityInfo = activityInfo;
        this.taskId = i;
        this.userId = i2;
        this.packageManager = packageManager;
        String str = activityInfo.name;
        this.topActivityPackageName = activityInfo.packageName;
        final int i3 = 0;
        this.topActivityAppName$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.screenshot.appclips.InternalTaskInfo$$ExternalSyntheticLambda0
            public final /* synthetic */ InternalTaskInfo f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i3) {
                    case 0:
                        InternalTaskInfo internalTaskInfo = this.f$0;
                        return internalTaskInfo.topActivityInfo.loadLabel(internalTaskInfo.packageManager).toString();
                    default:
                        InternalTaskInfo internalTaskInfo2 = this.f$0;
                        return internalTaskInfo2.topActivityInfo.loadIcon(internalTaskInfo2.packageManager);
                }
            }
        });
        final int i4 = 1;
        this.topActivityAppIcon$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.screenshot.appclips.InternalTaskInfo$$ExternalSyntheticLambda0
            public final /* synthetic */ InternalTaskInfo f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i4) {
                    case 0:
                        InternalTaskInfo internalTaskInfo = this.f$0;
                        return internalTaskInfo.topActivityInfo.loadLabel(internalTaskInfo.packageManager).toString();
                    default:
                        InternalTaskInfo internalTaskInfo2 = this.f$0;
                        return internalTaskInfo2.topActivityInfo.loadIcon(internalTaskInfo2.packageManager);
                }
            }
        });
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof InternalTaskInfo)) {
            return false;
        }
        InternalTaskInfo internalTaskInfo = (InternalTaskInfo) obj;
        return Intrinsics.areEqual(this.topActivityInfo, internalTaskInfo.topActivityInfo) && this.taskId == internalTaskInfo.taskId && this.userId == internalTaskInfo.userId && Intrinsics.areEqual(this.packageManager, internalTaskInfo.packageManager);
    }

    public final int hashCode() {
        return this.packageManager.hashCode() + ReorderTile$$ExternalSyntheticOutline0.m(this.userId, ReorderTile$$ExternalSyntheticOutline0.m(this.taskId, this.topActivityInfo.hashCode() * 31, 31), 31);
    }

    public final String toString() {
        return "InternalTaskInfo(topActivityInfo=" + this.topActivityInfo + ", taskId=" + this.taskId + ", userId=" + this.userId + ", packageManager=" + this.packageManager + ")";
    }
}
