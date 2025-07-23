package androidx.window.embedding;

import android.os.IBinder;
import androidx.window.WindowSdkExtensions;
import androidx.window.WindowSdkExtensions$Companion$getInstance$1;
import androidx.window.extensions.embedding.SplitInfo;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SplitInfo {
    public final IBinder binder;
    public final ActivityStack primaryActivityStack;
    public final ActivityStack secondaryActivityStack;
    public final SplitAttributes splitAttributes;
    public final SplitInfo.Token token;

    private SplitInfo(ActivityStack activityStack, ActivityStack activityStack2, SplitAttributes splitAttributes, IBinder iBinder, SplitInfo.Token token) {
        this.primaryActivityStack = activityStack;
        this.secondaryActivityStack = activityStack2;
        this.splitAttributes = splitAttributes;
        this.binder = iBinder;
        this.token = token;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SplitInfo)) {
            return false;
        }
        SplitInfo splitInfo = (SplitInfo) obj;
        return Intrinsics.areEqual(this.primaryActivityStack, splitInfo.primaryActivityStack) && Intrinsics.areEqual(this.secondaryActivityStack, splitInfo.secondaryActivityStack) && Intrinsics.areEqual(this.splitAttributes, splitInfo.splitAttributes) && Intrinsics.areEqual(this.token, splitInfo.token) && Intrinsics.areEqual(this.binder, splitInfo.binder);
    }

    public final int hashCode() {
        int hashCode = (this.splitAttributes.hashCode() + ((this.secondaryActivityStack.hashCode() + (this.primaryActivityStack.hashCode() * 31)) * 31)) * 31;
        SplitInfo.Token token = this.token;
        int hashCode2 = (hashCode + (token != null ? token.hashCode() : 0)) * 31;
        IBinder iBinder = this.binder;
        return hashCode2 + (iBinder != null ? iBinder.hashCode() : 0);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("SplitInfo:{");
        sb.append("primaryActivityStack=" + this.primaryActivityStack + ", ");
        sb.append("secondaryActivityStack=" + this.secondaryActivityStack + ", ");
        sb.append("splitAttributes=" + this.splitAttributes + ", ");
        if (this.token != null) {
            sb.append("token=" + this.token);
        }
        if (this.binder != null) {
            sb.append("binder=" + this.binder);
        }
        sb.append("}");
        return sb.toString();
    }

    public SplitInfo(ActivityStack activityStack, ActivityStack activityStack2, SplitAttributes splitAttributes, SplitInfo.Token token) {
        this(activityStack, activityStack2, splitAttributes, null, token);
    }

    public SplitInfo(ActivityStack activityStack, ActivityStack activityStack2, SplitAttributes splitAttributes, IBinder iBinder) {
        this(activityStack, activityStack2, splitAttributes, iBinder, null);
        WindowSdkExtensions.Companion.getClass();
        WindowSdkExtensions$Companion$getInstance$1 companion = WindowSdkExtensions.Companion.getInstance();
        IntRange intRange = new IntRange(3, 4);
        int i = intRange.first;
        int i2 = intRange.last;
        int i3 = companion.extensionVersion;
        if (i > i3 || i3 > i2) {
            throw new UnsupportedOperationException("This API requires extension version " + intRange + ", but the device is on " + i3);
        }
    }

    public SplitInfo(ActivityStack activityStack, ActivityStack activityStack2, SplitAttributes splitAttributes) {
        this(activityStack, activityStack2, splitAttributes, null, null);
    }
}
