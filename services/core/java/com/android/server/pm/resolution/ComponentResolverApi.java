package com.android.server.pm.resolution;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ProviderInfo;
import com.android.internal.pm.pkg.component.ParsedActivity;
import com.android.internal.pm.pkg.component.ParsedProvider;
import com.android.internal.pm.pkg.component.ParsedService;
import com.android.server.pm.Computer;
import com.android.server.pm.DumpState;
import java.io.PrintWriter;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public interface ComponentResolverApi {
    boolean componentExists(ComponentName componentName);

    void dumpActivityResolvers(DumpState dumpState, PrintWriter printWriter, String str);

    void dumpContentProviders(Computer computer, PrintWriter printWriter, DumpState dumpState, String str);

    void dumpProviderResolvers(DumpState dumpState, PrintWriter printWriter, String str);

    void dumpReceiverResolvers(DumpState dumpState, PrintWriter printWriter, String str);

    void dumpServicePermissions(PrintWriter printWriter, DumpState dumpState);

    void dumpServiceResolvers(DumpState dumpState, PrintWriter printWriter, String str);

    ParsedActivity getActivity(ComponentName componentName);

    ParsedProvider getProvider(ComponentName componentName);

    ParsedActivity getReceiver(ComponentName componentName);

    ParsedService getService(ComponentName componentName);

    List queryActivities(Computer computer, Intent intent, String str, long j, int i);

    List queryActivities(Computer computer, Intent intent, String str, long j, List list, int i);

    ProviderInfo queryProvider(Computer computer, String str, long j, int i);

    List queryProviders(Computer computer, Intent intent, String str, long j, int i);

    List queryProviders(Computer computer, Intent intent, String str, long j, List list, int i);

    List queryProviders(Computer computer, String str, String str2, int i, long j, int i2);

    List queryReceivers(Computer computer, Intent intent, String str, long j, int i);

    List queryReceivers(Computer computer, Intent intent, String str, long j, List list, int i);

    List queryServices(Computer computer, Intent intent, String str, long j, int i);

    List queryServices(Computer computer, Intent intent, String str, long j, List list, int i);

    void querySyncProviders(Computer computer, List list, List list2, boolean z, int i);
}
