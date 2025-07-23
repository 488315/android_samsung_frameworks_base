package com.android.systemui.biometrics;

import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class FaceHelpMessageDebouncer {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final String TAG;
    public final List helpFaceAuthStatuses;
    public Integer lastMessageIdShown;
    public final int shownFaceMessageFrequencyBoost;
    public long startTime;
    public final long startWindow;
    public final float threshold;
    public final long window;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public FaceHelpMessageDebouncer() {
        this(0L, 0L, 0, 0.0f, 15, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x010e  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x011c  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0115  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.systemui.deviceentry.shared.model.HelpFaceAuthenticationStatus getMessageToShow(long r10) {
        /*
            Method dump skipped, instructions count: 311
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.FaceHelpMessageDebouncer.getMessageToShow(long):com.android.systemui.deviceentry.shared.model.HelpFaceAuthenticationStatus");
    }

    public final void startNewFaceAuthSession(long j) {
        Log.d(this.TAG, "startNewFaceAuthSession at startTime=" + this.startTime);
        this.startTime = j;
        ((ArrayList) this.helpFaceAuthStatuses).clear();
        this.lastMessageIdShown = null;
    }

    public FaceHelpMessageDebouncer(long j, long j2, int i, float f) {
        this.window = j;
        this.startWindow = j2;
        this.shownFaceMessageFrequencyBoost = i;
        this.threshold = f;
        this.TAG = "FaceHelpMessageDebouncer";
        this.helpFaceAuthStatuses = new ArrayList();
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ FaceHelpMessageDebouncer(long r8, long r10, int r12, float r13, int r14, kotlin.jvm.internal.DefaultConstructorMarker r15) {
        /*
            r7 = this;
            r15 = r14 & 1
            if (r15 == 0) goto L6
            r8 = 200(0xc8, double:9.9E-322)
        L6:
            r1 = r8
            r8 = r14 & 2
            if (r8 == 0) goto Ld
            r3 = r1
            goto Le
        Ld:
            r3 = r10
        Le:
            r8 = r14 & 4
            if (r8 == 0) goto L13
            r12 = 4
        L13:
            r5 = r12
            r8 = r14 & 8
            if (r8 == 0) goto L19
            r13 = 0
        L19:
            r0 = r7
            r6 = r13
            r0.<init>(r1, r3, r5, r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.FaceHelpMessageDebouncer.<init>(long, long, int, float, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }
}
