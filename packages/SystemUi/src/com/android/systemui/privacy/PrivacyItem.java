package com.android.systemui.privacy;

import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import com.android.app.motiontool.TraceMetadata$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PrivacyItem {
    public final PrivacyApplication application;
    public final String log;
    public final boolean paused;
    public final PrivacyType privacyType;
    public final long timeStampElapsed;

    public PrivacyItem(PrivacyType privacyType, PrivacyApplication privacyApplication, long j, boolean z) {
        this.privacyType = privacyType;
        this.application = privacyApplication;
        this.timeStampElapsed = j;
        this.paused = z;
        StringBuilder m4m = ActivityResultRegistry$$ExternalSyntheticOutline0.m4m("(", privacyType.getLogName(), ", ");
        m4m.append(privacyApplication.packageName);
        m4m.append("(");
        m4m.append(privacyApplication.uid);
        m4m.append("), ");
        m4m.append(j);
        m4m.append(", paused=");
        m4m.append(z);
        m4m.append(")");
        this.log = m4m.toString();
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PrivacyItem)) {
            return false;
        }
        PrivacyItem privacyItem = (PrivacyItem) obj;
        return this.privacyType == privacyItem.privacyType && Intrinsics.areEqual(this.application, privacyItem.application) && this.timeStampElapsed == privacyItem.timeStampElapsed && this.paused == privacyItem.paused;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        int m51m = TraceMetadata$$ExternalSyntheticOutline0.m51m(this.timeStampElapsed, (this.application.hashCode() + (this.privacyType.hashCode() * 31)) * 31, 31);
        boolean z = this.paused;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        return m51m + i;
    }

    public final String toString() {
        return "PrivacyItem(privacyType=" + this.privacyType + ", application=" + this.application + ", timeStampElapsed=" + this.timeStampElapsed + ", paused=" + this.paused + ")";
    }

    public /* synthetic */ PrivacyItem(PrivacyType privacyType, PrivacyApplication privacyApplication, long j, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(privacyType, privacyApplication, (i & 4) != 0 ? -1L : j, (i & 8) != 0 ? false : z);
    }
}
