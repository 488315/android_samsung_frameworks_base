package com.android.server.inputmethod;

import android.util.Printer;
import android.view.inputmethod.InputMethodInfo;

import java.io.PrintWriter;
import java.util.function.Consumer;

public final /* synthetic */ class InputMethodManagerService$$ExternalSyntheticLambda19
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ InputMethodManagerService$$ExternalSyntheticLambda19(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                ((PrintWriter) obj2).println("   Enabled: " + ((InputMethodInfo) obj).getId());
                break;
            default:
                Printer printer = (Printer) obj2;
                ClientState clientState = (ClientState) obj;
                printer.println("  " + clientState + ":");
                StringBuilder sb = new StringBuilder("    client=");
                sb.append(clientState.mClient);
                printer.println(sb.toString());
                printer.println(
                        "    fallbackInputConnection=" + clientState.mFallbackInputConnection);
                printer.println("    sessionRequested=" + clientState.mSessionRequested);
                printer.println(
                        "    sessionRequestedForAccessibility="
                                + clientState.mSessionRequestedForAccessibility);
                printer.println("    curSession=" + clientState.mCurSession);
                printer.println("    selfReportedDisplayId=" + clientState.mSelfReportedDisplayId);
                printer.println("    uid=" + clientState.mUid);
                printer.println("    pid=" + clientState.mPid);
                break;
        }
    }
}
