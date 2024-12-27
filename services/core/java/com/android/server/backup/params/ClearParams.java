package com.android.server.backup.params;

import android.content.pm.PackageInfo;
import com.android.server.backup.internal.OnTaskFinishedListener;
import com.android.server.backup.transport.TransportConnection;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ClearParams {
    public OnTaskFinishedListener listener;
    public TransportConnection mTransportConnection;
    public PackageInfo packageInfo;
}
