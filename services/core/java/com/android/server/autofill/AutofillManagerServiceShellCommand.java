package com.android.server.autofill;

import android.os.Bundle;
import android.os.RemoteCallback;
import android.os.ShellCommand;
import android.service.autofill.AutofillFieldClassificationService;
import android.util.Slog;
import android.view.autofill.AutofillValue;
import com.android.internal.os.IResultReceiver;
import com.android.server.accounts.AccountManagerServiceShellCommand$$ExternalSyntheticOutline0;
import com.android.server.am.ActiveServices$$ExternalSyntheticOutline0;
import com.android.server.infra.FrameworkResourcesServiceNameResolver;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class AutofillManagerServiceShellCommand extends ShellCommand {
    public final AutofillManagerService mService;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.autofill.AutofillManagerServiceShellCommand$1, reason: invalid class name */
    public final class AnonymousClass1 extends IResultReceiver.Stub {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ CountDownLatch val$latch;
        public final /* synthetic */ PrintWriter val$pw;

        public /* synthetic */ AnonymousClass1(PrintWriter printWriter, CountDownLatch countDownLatch, int i) {
            this.$r8$classId = i;
            this.val$pw = printWriter;
            this.val$latch = countDownLatch;
        }

        public final void send(int i, Bundle bundle) {
            switch (this.$r8$classId) {
                case 0:
                    AccountManagerServiceShellCommand$$ExternalSyntheticOutline0.m(this.val$pw, "resultCode=", i);
                    if (i == 0 && bundle != null) {
                        this.val$pw.println("value=" + bundle.getInt(KnoxCustomManagerService.SPCM_KEY_RESULT));
                    }
                    this.val$latch.countDown();
                    break;
                default:
                    Iterator<String> it = bundle.getStringArrayList("sessions").iterator();
                    while (it.hasNext()) {
                        this.val$pw.println(it.next());
                    }
                    this.val$latch.countDown();
                    break;
            }
        }
    }

    public AutofillManagerServiceShellCommand(AutofillManagerService autofillManagerService) {
        this.mService = autofillManagerService;
    }

    public static int waitForLatch(PrintWriter printWriter, CountDownLatch countDownLatch) {
        try {
            if (countDownLatch.await(5L, TimeUnit.SECONDS)) {
                return 0;
            }
            printWriter.println("Timed out after 5 seconds");
            return -1;
        } catch (InterruptedException unused) {
            printWriter.println("System call interrupted");
            Thread.currentThread().interrupt();
            return -1;
        }
    }

    public final void getLogLevel(PrintWriter printWriter) {
        int i;
        AutofillManagerService autofillManagerService = this.mService;
        autofillManagerService.enforceCallingPermissionForManagement();
        synchronized (autofillManagerService.mLock) {
            try {
                i = Helper.sVerbose ? 4 : Helper.sDebug ? 2 : 0;
            } finally {
            }
        }
        if (i == 0) {
            printWriter.println("off");
            return;
        }
        if (i == 2) {
            printWriter.println("debug");
        } else if (i != 4) {
            ActiveServices$$ExternalSyntheticOutline0.m(i, printWriter, "unknow (", ")");
        } else {
            printWriter.println("verbose");
        }
    }

    public final int getNextIntArgRequired() {
        return Integer.parseInt(getNextArgRequired());
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:164:0x042f, code lost:
    
        if (r0.equals("default") == false) goto L181;
     */
    /* JADX WARN: Code restructure failed: missing block: B:186:0x049b, code lost:
    
        if (r0.equals("debug") == false) goto L200;
     */
    /* JADX WARN: Code restructure failed: missing block: B:197:0x01ba, code lost:
    
        if (r11.equals("max_visible_datasets") == false) goto L92;
     */
    /* JADX WARN: Type inference failed for: r0v54, types: [com.android.server.autofill.AutofillManagerServiceShellCommand$2] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int onCommand(java.lang.String r11) {
        /*
            Method dump skipped, instructions count: 1374
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.autofill.AutofillManagerServiceShellCommand.onCommand(java.lang.String):int");
    }

    public final void onHelp() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        try {
            outPrintWriter.println("AutoFill Service (autofill) commands:");
            outPrintWriter.println("  help");
            outPrintWriter.println("    Prints this help text.");
            outPrintWriter.println("");
            outPrintWriter.println("  get log_level ");
            outPrintWriter.println("    Gets the Autofill log level (off | debug | verbose).");
            outPrintWriter.println("");
            outPrintWriter.println("  get max_partitions");
            outPrintWriter.println("    Gets the maximum number of partitions per session.");
            outPrintWriter.println("");
            outPrintWriter.println("  get max_visible_datasets");
            outPrintWriter.println("    Gets the maximum number of visible datasets in the UI.");
            outPrintWriter.println("");
            outPrintWriter.println("  get full_screen_mode");
            outPrintWriter.println("    Gets the Fill UI full screen mode");
            outPrintWriter.println("");
            outPrintWriter.println("  get fc_score [--algorithm ALGORITHM] value1 value2");
            outPrintWriter.println("    Gets the field classification score for 2 fields.");
            outPrintWriter.println("");
            outPrintWriter.println("  get bind-instant-service-allowed");
            outPrintWriter.println("    Gets whether binding to services provided by instant apps is allowed");
            outPrintWriter.println("");
            outPrintWriter.println("  get saved-password-count");
            outPrintWriter.println("    Gets the number of saved passwords in the current service.");
            outPrintWriter.println("");
            outPrintWriter.println("  set log_level [off | debug | verbose]");
            outPrintWriter.println("    Sets the Autofill log level.");
            outPrintWriter.println("");
            outPrintWriter.println("  set max_partitions number");
            outPrintWriter.println("    Sets the maximum number of partitions per session.");
            outPrintWriter.println("");
            outPrintWriter.println("  set max_visible_datasets number");
            outPrintWriter.println("    Sets the maximum number of visible datasets in the UI.");
            outPrintWriter.println("");
            outPrintWriter.println("  set full_screen_mode [true | false | default]");
            outPrintWriter.println("    Sets the Fill UI full screen mode");
            outPrintWriter.println("");
            outPrintWriter.println("  set bind-instant-service-allowed [true | false]");
            outPrintWriter.println("    Sets whether binding to services provided by instant apps is allowed");
            outPrintWriter.println("");
            outPrintWriter.println("  set temporary-augmented-service USER_ID [COMPONENT_NAME DURATION]");
            outPrintWriter.println("    Temporarily (for DURATION ms) changes the augmented autofill service implementation.");
            outPrintWriter.println("    To reset, call with just the USER_ID argument.");
            outPrintWriter.println("");
            outPrintWriter.println("  set default-augmented-service-enabled USER_ID [true|false]");
            outPrintWriter.println("    Enable / disable the default augmented autofill service for the user.");
            outPrintWriter.println("");
            outPrintWriter.println("  set temporary-detection-service USER_ID [COMPONENT_NAME DURATION]");
            outPrintWriter.println("    Temporarily (for DURATION ms) changes the autofill detection service implementation.");
            outPrintWriter.println("    To reset, call with [COMPONENT_NAME 0].");
            outPrintWriter.println("");
            outPrintWriter.println("  get default-augmented-service-enabled USER_ID");
            outPrintWriter.println("    Checks whether the default augmented autofill service is enabled for the user.");
            outPrintWriter.println("");
            outPrintWriter.println("  list sessions [--user USER_ID]");
            outPrintWriter.println("    Lists all pending sessions.");
            outPrintWriter.println("");
            outPrintWriter.println("  destroy sessions [--user USER_ID]");
            outPrintWriter.println("    Destroys all pending sessions.");
            outPrintWriter.println("");
            outPrintWriter.println("  reset");
            outPrintWriter.println("    Resets all pending sessions and cached service connections.");
            outPrintWriter.println("");
            outPrintWriter.println("  flags");
            outPrintWriter.println("    Prints out all autofill related flags.");
            outPrintWriter.println("");
            outPrintWriter.close();
        } catch (Throwable th) {
            if (outPrintWriter != null) {
                try {
                    outPrintWriter.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final int requestGet(final PrintWriter printWriter) {
        char c;
        String str;
        boolean isPccClassificationEnabled;
        boolean z;
        boolean z2;
        int i;
        int i2;
        String nextArgRequired = getNextArgRequired();
        nextArgRequired.getClass();
        int i3 = 0;
        switch (nextArgRequired.hashCode()) {
            case -2124387184:
                if (nextArgRequired.equals("fc_score")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -2006901047:
                if (nextArgRequired.equals("log_level")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -1298810906:
                if (nextArgRequired.equals("full_screen_mode")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -633247282:
                if (nextArgRequired.equals("field-detection-service-enabled")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -255918237:
                if (nextArgRequired.equals("saved-password-count")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 809633044:
                if (nextArgRequired.equals("bind-instant-service-allowed")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 852405952:
                if (nextArgRequired.equals("default-augmented-service-enabled")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 1393110435:
                if (nextArgRequired.equals("max_visible_datasets")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 1772188804:
                if (nextArgRequired.equals("max_partitions")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                String nextArgRequired2 = getNextArgRequired();
                if ("--algorithm".equals(nextArgRequired2)) {
                    str = getNextArgRequired();
                    nextArgRequired2 = getNextArgRequired();
                } else {
                    str = null;
                }
                String nextArgRequired3 = getNextArgRequired();
                final CountDownLatch countDownLatch = new CountDownLatch(1);
                AutofillManagerService autofillManagerService = this.mService;
                RemoteCallback remoteCallback = new RemoteCallback(new RemoteCallback.OnResultListener() { // from class: com.android.server.autofill.AutofillManagerServiceShellCommand$$ExternalSyntheticLambda0
                    public final void onResult(Bundle bundle) {
                        PrintWriter printWriter2 = printWriter;
                        CountDownLatch countDownLatch2 = countDownLatch;
                        AutofillFieldClassificationService.Scores scores = (AutofillFieldClassificationService.Scores) bundle.getParcelable("scores", AutofillFieldClassificationService.Scores.class);
                        if (scores == null) {
                            printWriter2.println("no score");
                        } else {
                            printWriter2.println(scores.scores[0][0]);
                        }
                        countDownLatch2.countDown();
                    }
                });
                autofillManagerService.enforceCallingPermissionForManagement();
                new FieldClassificationStrategy(autofillManagerService.getContext(), -2).calculateScores(remoteCallback, Arrays.asList(AutofillValue.forText(nextArgRequired2)), new String[]{nextArgRequired3}, new String[]{null}, str, null, null, null);
                return waitForLatch(printWriter, countDownLatch);
            case 1:
                getLogLevel(printWriter);
                return 0;
            case 2:
                this.mService.enforceCallingPermissionForManagement();
                Boolean bool = Helper.sFullScreenMode;
                if (bool == null) {
                    printWriter.println("default");
                } else if (bool.booleanValue()) {
                    printWriter.println("true");
                } else {
                    printWriter.println("false");
                }
                return 0;
            case 3:
                int nextIntArgRequired = getNextIntArgRequired();
                AutofillManagerService autofillManagerService2 = this.mService;
                autofillManagerService2.enforceCallingPermissionForManagement();
                synchronized (autofillManagerService2.mLock) {
                    try {
                        AutofillManagerServiceImpl autofillManagerServiceImpl = (AutofillManagerServiceImpl) autofillManagerService2.getServiceForUserLocked(nextIntArgRequired);
                        isPccClassificationEnabled = autofillManagerServiceImpl != null ? autofillManagerServiceImpl.isPccClassificationEnabled() : false;
                    } finally {
                    }
                }
                printWriter.println(isPccClassificationEnabled);
                return 0;
            case 4:
                int nextIntArgRequired2 = getNextIntArgRequired();
                CountDownLatch countDownLatch2 = new CountDownLatch(1);
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(printWriter, countDownLatch2, i3);
                AutofillManagerService autofillManagerService3 = this.mService;
                autofillManagerService3.enforceCallingPermissionForManagement();
                synchronized (autofillManagerService3.mLock) {
                    AutofillManagerServiceImpl autofillManagerServiceImpl2 = (AutofillManagerServiceImpl) autofillManagerService3.peekServiceForUserLocked(nextIntArgRequired2);
                    if (autofillManagerServiceImpl2 != null) {
                        autofillManagerServiceImpl2.requestSavedPasswordCount(anonymousClass1);
                        waitForLatch(printWriter, countDownLatch2);
                    } else if (Helper.sVerbose) {
                        Slog.v("AutofillManagerService", "requestSavedPasswordCount(): no service for " + nextIntArgRequired2);
                    }
                }
                return 0;
            case 5:
                AutofillManagerService autofillManagerService4 = this.mService;
                autofillManagerService4.enforceCallingPermissionForManagement();
                synchronized (autofillManagerService4.mLock) {
                    z = autofillManagerService4.mAllowInstantService;
                }
                if (z) {
                    printWriter.println("true");
                } else {
                    printWriter.println("false");
                }
                return 0;
            case 6:
                int nextIntArgRequired3 = getNextIntArgRequired();
                AutofillManagerService autofillManagerService5 = this.mService;
                autofillManagerService5.enforceCallingPermissionForManagement();
                FrameworkResourcesServiceNameResolver frameworkResourcesServiceNameResolver = autofillManagerService5.mAugmentedAutofillResolver;
                synchronized (frameworkResourcesServiceNameResolver.mLock) {
                    z2 = !frameworkResourcesServiceNameResolver.mDefaultServicesDisabled.get(nextIntArgRequired3);
                }
                printWriter.println(z2);
                return 0;
            case 7:
                this.mService.getClass();
                synchronized (AutofillManagerService.class) {
                    i = AutofillManagerService.sVisibleDatasetsMaxCount;
                }
                printWriter.println(i);
                return 0;
            case '\b':
                synchronized (this.mService.mLock) {
                    i2 = AutofillManagerService.sPartitionMaxCount;
                }
                printWriter.println(i2);
                return 0;
            default:
                printWriter.println("Invalid set: ".concat(nextArgRequired));
                return -1;
        }
    }
}
