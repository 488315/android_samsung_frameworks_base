package com.android.systemui.media;

import android.app.PendingIntent;
import android.content.Context;
import android.graphics.drawable.Icon;
import android.util.Log;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityController;
import java.util.function.BooleanSupplier;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class OAMusicChipController {
    public Icon appIcon;
    public Integer bgColor;
    public PendingIntent clickIntent;
    public final BooleanSupplier isPlayerOAPlayedSupplier;
    public Boolean isPlaying;
    public final OngoingActivityController ongoingActivityController;
    public String songTitle;

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

    public OAMusicChipController(Context context, OngoingActivityController ongoingActivityController, BooleanSupplier booleanSupplier) {
        this.ongoingActivityController = ongoingActivityController;
        this.isPlayerOAPlayedSupplier = booleanSupplier;
        Log.d("OAMusicChipController", "OA created");
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x006e, code lost:
    
        if (kotlin.text.StringsKt__StringsJVMKt.equals(r6, r8, false) != false) goto L32;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updatePlaybackState(android.media.session.PlaybackState r10) {
        /*
            Method dump skipped, instructions count: 263
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.OAMusicChipController.updatePlaybackState(android.media.session.PlaybackState):void");
    }
}
