package com.android.systemui.privacy;

import android.support.v4.media.session.MediaSessionCompat$QueueItem$$ExternalSyntheticOutline0;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class PrivacyItem {
    public final PrivacyApplication application;
    public final String log;
    public final boolean paused;
    public final PrivacyType privacyType;
    public final long timeStampElapsed;
    public long timeStampRemoved;

    public PrivacyItem(PrivacyType privacyType, PrivacyApplication privacyApplication, long j, boolean z, long j2) {
        this.privacyType = privacyType;
        this.application = privacyApplication;
        this.timeStampElapsed = j;
        this.paused = z;
        this.timeStampRemoved = j2;
        String logName = privacyType.getLogName();
        String str = privacyApplication.packageName;
        int i = privacyApplication.uid;
        long j3 = this.timeStampRemoved;
        StringBuilder sbM = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("(", logName, ", ", str, "(");
        sbM.append(i);
        sbM.append("), ");
        sbM.append(j);
        sbM.append(", paused=");
        sbM.append(z);
        sbM.append(", removed=");
        this.log = MediaSessionCompat$QueueItem$$ExternalSyntheticOutline0.m(j3, ")", sbM);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PrivacyItem)) {
            return false;
        }
        PrivacyItem privacyItem = (PrivacyItem) obj;
        return this.privacyType == privacyItem.privacyType && Intrinsics.areEqual(this.application, privacyItem.application) && this.timeStampElapsed == privacyItem.timeStampElapsed && this.paused == privacyItem.paused && this.timeStampRemoved == privacyItem.timeStampRemoved;
    }

    public final int hashCode() {
        return Long.hashCode(this.timeStampRemoved) + TransitionData$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m((this.application.hashCode() + (this.privacyType.hashCode() * 31)) * 31, 31, this.timeStampElapsed), 31, this.paused);
    }

    public final String toString() {
        return "PrivacyItem(privacyType=" + this.privacyType + ", application=" + this.application + ", timeStampElapsed=" + this.timeStampElapsed + ", paused=" + this.paused + ", timeStampRemoved=" + this.timeStampRemoved + ")";
    }

    public /* synthetic */ PrivacyItem(PrivacyType privacyType, PrivacyApplication privacyApplication, long j, boolean z, long j2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(privacyType, privacyApplication, (i & 4) != 0 ? -1L : j, (i & 8) != 0 ? false : z, (i & 16) != 0 ? -1L : j2);
    }
}
