package com.samsung.android.settingslib.bluetooth.scsp;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

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
    public final void run() throws IOException {
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
        boolean zMakeAllResourceData = false;
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            Uri uri = (Uri) obj;
            Log.d("ScspUtils", "saveAllResources: uri = " + uri.toString());
            zMakeAllResourceData = ScspUtils.makeAllResourceData(context, uri);
        }
        if (!zMakeAllResourceData || collection == null) {
            return;
        }
        handler.post(new ScspUtils$$ExternalSyntheticLambda1(collection, 2));
    }
}
