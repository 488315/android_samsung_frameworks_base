package com.android.server.ambientcontext;

import android.app.ambientcontext.AmbientContextEventRequest;
import android.os.RemoteCallback;
import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public interface RemoteAmbientDetectionService {
    void dump(String str, PrintWriter printWriter);

    void queryServiceStatus(int[] iArr, String str, RemoteCallback remoteCallback);

    void startDetection(AmbientContextEventRequest ambientContextEventRequest, String str, RemoteCallback remoteCallback, RemoteCallback remoteCallback2);

    void stopDetection(String str);

    void unbind();
}
