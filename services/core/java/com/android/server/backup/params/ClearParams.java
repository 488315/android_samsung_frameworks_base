package com.android.server.backup.params;

import android.content.pm.PackageInfo;

import com.android.server.backup.internal.OnTaskFinishedListener;
import com.android.server.backup.transport.TransportConnection;

public final class ClearParams {
    public OnTaskFinishedListener listener;
    public TransportConnection mTransportConnection;
    public PackageInfo packageInfo;
}
