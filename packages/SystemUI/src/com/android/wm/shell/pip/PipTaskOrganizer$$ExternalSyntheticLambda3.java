package com.android.wm.shell.pip;

import android.app.RemoteAction;
import com.android.wm.shell.pip.PipTaskOrganizer;
import java.util.StringJoiner;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class PipTaskOrganizer$$ExternalSyntheticLambda3 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ PipTaskOrganizer$$ExternalSyntheticLambda3(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                int i2 = PipTaskOrganizer.EXTRA_CONTENT_OVERLAY_FADE_OUT_DELAY_MS;
                ((StringJoiner) obj2).add(((RemoteAction) obj).getTitle());
                break;
            default:
                ((PipTaskOrganizer.AnonymousClass1) obj2).getClass();
                break;
        }
    }
}
