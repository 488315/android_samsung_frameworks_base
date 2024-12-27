package com.android.server.location.contexthub;

import android.content.Context;
import android.os.ShellCommand;
import android.util.Log;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ContextHubShellCommand extends ShellCommand {
    public final Context mContext;
    public final ContextHubService mInternal;

    public ContextHubShellCommand(Context context, ContextHubService contextHubService) {
        this.mInternal = contextHubService;
        this.mContext = context;
    }

    public final int onCommand(String str) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.ACCESS_CONTEXT_HUB", "ContextHubShellCommand");
        if (!"deny".equals(str)) {
            return handleDefaultCommands(str);
        }
        int intValue = Integer.decode(getNextArgRequired()).intValue();
        final String nextArgRequired = getNextArgRequired();
        final long longValue = Long.decode(getNextArgRequired()).longValue();
        ContextHubService contextHubService = this.mInternal;
        contextHubService.getClass();
        Log.i("ContextHubService", "Denying " + nextArgRequired + " access to " + Long.toHexString(longValue) + " on context hub # " + intValue);
        contextHubService.mClientManager.forEachClientOfHub(intValue, new Consumer() { // from class: com.android.server.location.contexthub.ContextHubService$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                String str2 = nextArgRequired;
                long j = longValue;
                ContextHubClientBroker contextHubClientBroker = (ContextHubClientBroker) obj;
                if (contextHubClientBroker.mPackage.equals(str2)) {
                    contextHubClientBroker.updateNanoAppAuthState(j, Collections.emptyList(), false, true);
                }
            }
        });
        return 0;
    }

    public final void onHelp() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("ContextHub commands:");
        outPrintWriter.println("  help");
        outPrintWriter.println("      Print this help text.");
        outPrintWriter.println("  deny [contextHubId] [packageName] [nanoAppId]");
        outPrintWriter.println("    Immediately transitions the package's authentication state to denied so");
        outPrintWriter.println("    can no longer communciate with the nanoapp.");
    }
}
