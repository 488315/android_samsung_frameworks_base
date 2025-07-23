package com.android.systemui.navigationbar.gestural;

import android.util.ArraySet;
import com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class EdgeBackGestureHandler$8$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ EdgeBackGestureHandler.AnonymousClass8 f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ EdgeBackGestureHandler$8$$ExternalSyntheticLambda0(EdgeBackGestureHandler.AnonymousClass8 anonymousClass8, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = anonymousClass8;
        this.f$1 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                EdgeBackGestureHandler.AnonymousClass8 anonymousClass8 = this.f$0;
                int i = this.f$1;
                ((ArraySet) anonymousClass8.this$0.mTrackpadsConnected).remove(Integer.valueOf(i));
                if (((ArraySet) anonymousClass8.this$0.mTrackpadsConnected).isEmpty()) {
                    EdgeBackGestureHandler edgeBackGestureHandler = anonymousClass8.this$0;
                    if (!edgeBackGestureHandler.mIsEnabled || ((ArraySet) edgeBackGestureHandler.mTrackpadsConnected).isEmpty()) {
                        anonymousClass8.this$0.updateIsEnabled();
                        anonymousClass8.this$0.updateCurrentUserResources();
                        break;
                    }
                }
                break;
            default:
                EdgeBackGestureHandler.AnonymousClass8 anonymousClass82 = this.f$0;
                int i2 = this.f$1;
                boolean isEmpty = ((ArraySet) anonymousClass82.this$0.mTrackpadsConnected).isEmpty();
                ((ArraySet) anonymousClass82.this$0.mTrackpadsConnected).add(Integer.valueOf(i2));
                if (isEmpty) {
                    EdgeBackGestureHandler edgeBackGestureHandler2 = anonymousClass82.this$0;
                    if (!edgeBackGestureHandler2.mIsEnabled || ((ArraySet) edgeBackGestureHandler2.mTrackpadsConnected).isEmpty()) {
                        anonymousClass82.this$0.updateIsEnabled();
                        anonymousClass82.this$0.updateCurrentUserResources();
                        break;
                    }
                }
                break;
        }
    }
}
