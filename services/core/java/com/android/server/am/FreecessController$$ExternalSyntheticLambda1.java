package com.android.server.am;

import android.util.Slog;
import com.android.server.am.FreecessController;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class FreecessController$$ExternalSyntheticLambda1 implements FreecessController.checkResultCallback {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ FreecessController$$ExternalSyntheticLambda1(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // com.android.server.am.FreecessController.checkResultCallback
    public final void freezeSkipFrozen(String str) {
        switch (this.$r8$classId) {
            case 0:
                ((StringBuilder) this.f$0).append(str);
                break;
            case 1:
                ((StringBuilder) this.f$0).append(str);
                break;
            default:
                StringBuilder sb = new StringBuilder("freeze calm ");
                FreecessPkgStatus freecessPkgStatus = (FreecessPkgStatus) this.f$0;
                sb.append(freecessPkgStatus.uid);
                sb.append(" ");
                sb.append(freecessPkgStatus.name);
                sb.append(" skip reason ");
                sb.append(str);
                Slog.d("FreecessController", sb.toString());
                break;
        }
    }
}
