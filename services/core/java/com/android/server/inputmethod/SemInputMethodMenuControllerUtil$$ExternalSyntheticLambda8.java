package com.android.server.inputmethod;

import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class SemInputMethodMenuControllerUtil$$ExternalSyntheticLambda8
        implements Runnable {
    public final /* synthetic */ String f$0;
    public final /* synthetic */ String f$1 = "switch_checked";
    public final /* synthetic */ Boolean f$2;
    public final /* synthetic */ Context f$3;

    public /* synthetic */ SemInputMethodMenuControllerUtil$$ExternalSyntheticLambda8(
            String str, Boolean bool, Context context) {
        this.f$0 = str;
        this.f$2 = bool;
        this.f$3 = context;
    }

    @Override // java.lang.Runnable
    public final void run() {
        String str = this.f$0;
        String str2 = this.f$1;
        Boolean bool = this.f$2;
        Context context = this.f$3;
        Intent intent = new Intent(str);
        intent.addFlags(536870912);
        intent.putExtra(str2, bool);
        context.sendBroadcastAsUser(intent, UserHandle.CURRENT);
    }
}
