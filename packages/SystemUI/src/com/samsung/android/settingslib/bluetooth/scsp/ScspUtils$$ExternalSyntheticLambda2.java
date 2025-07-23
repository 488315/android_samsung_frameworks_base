package com.samsung.android.settingslib.bluetooth.scsp;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import java.util.ArrayList;
import java.util.Collection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final /* synthetic */ class ScspUtils$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ ArrayList f$0;
    public final /* synthetic */ Context f$1;
    public final /* synthetic */ Collection f$2;
    public final /* synthetic */ Handler f$3;

    public /* synthetic */ ScspUtils$$ExternalSyntheticLambda2(ArrayList arrayList, Context context, Collection collection, Handler handler) {
        this.f$0 = arrayList;
        this.f$1 = context;
        this.f$2 = collection;
        this.f$3 = handler;
    }

    @Override // java.lang.Runnable
    public final void run() {
        ArrayList arrayList = this.f$0;
        Context context = this.f$1;
        Collection collection = this.f$2;
        Handler handler = this.f$3;
        String str = ScspUtils.FILE_PATH_ROOT;
        if (arrayList == null || arrayList.size() <= 0) {
            Log.d("ScspUtils", "saveAllResources: uri is null");
            return;
        }
        int size = arrayList.size();
        boolean z = false;
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            Uri uri = (Uri) obj;
            Log.d("ScspUtils", "saveAllResources: uri = " + uri.toString());
            z = ScspUtils.makeAllResourceData(context, uri);
        }
        if (!z || collection == null) {
            return;
        }
        handler.post(new ScspUtils$$ExternalSyntheticLambda1(collection, 2));
    }
}
