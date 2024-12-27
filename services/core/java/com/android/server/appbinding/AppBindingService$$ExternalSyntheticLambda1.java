package com.android.server.appbinding;

import com.android.server.appbinding.finders.CarrierMessagingClientServiceFinder;

import java.io.PrintWriter;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class AppBindingService$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PrintWriter f$0;

    public /* synthetic */ AppBindingService$$ExternalSyntheticLambda1(
            int i, PrintWriter printWriter) {
        this.$r8$classId = i;
        this.f$0 = printWriter;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        PrintWriter printWriter = this.f$0;
        CarrierMessagingClientServiceFinder carrierMessagingClientServiceFinder =
                (CarrierMessagingClientServiceFinder) obj;
        switch (i) {
            case 0:
                carrierMessagingClientServiceFinder.getClass();
                printWriter.print("    ");
                printWriter.print("App type: ");
                printWriter.print("[Default SMS app]");
                printWriter.println();
                synchronized (carrierMessagingClientServiceFinder.mLock) {
                    for (int i2 = 0;
                            i2 < carrierMessagingClientServiceFinder.mTargetPackages.size();
                            i2++) {
                        try {
                            int keyAt =
                                    carrierMessagingClientServiceFinder.mTargetPackages.keyAt(i2);
                            printWriter.print("    ");
                            printWriter.print("  User: ");
                            printWriter.print(keyAt);
                            printWriter.println();
                            printWriter.print("    ");
                            printWriter.print("    Package: ");
                            printWriter.print(
                                    (String)
                                            carrierMessagingClientServiceFinder.mTargetPackages.get(
                                                    keyAt));
                            printWriter.println();
                            printWriter.print("    ");
                            printWriter.print("    Service: ");
                            printWriter.print(
                                    carrierMessagingClientServiceFinder.mTargetServices.get(keyAt));
                            printWriter.println();
                            printWriter.print("    ");
                            printWriter.print("    Message: ");
                            printWriter.print(
                                    (String)
                                            carrierMessagingClientServiceFinder.mLastMessages.get(
                                                    keyAt));
                            printWriter.println();
                        } finally {
                        }
                    }
                }
                return;
            default:
                synchronized (carrierMessagingClientServiceFinder.mLock) {
                    for (int i3 = 0;
                            i3 < carrierMessagingClientServiceFinder.mTargetPackages.size();
                            i3++) {
                        try {
                            int keyAt2 =
                                    carrierMessagingClientServiceFinder.mTargetPackages.keyAt(i3);
                            printWriter.print("finder,");
                            printWriter.print("[Default SMS app]");
                            printWriter.print(",");
                            printWriter.print(keyAt2);
                            printWriter.print(",");
                            printWriter.print(
                                    (String)
                                            carrierMessagingClientServiceFinder.mTargetPackages.get(
                                                    keyAt2));
                            printWriter.print(",");
                            printWriter.print(
                                    carrierMessagingClientServiceFinder.mTargetServices.get(
                                            keyAt2));
                            printWriter.print(",");
                            printWriter.print(
                                    (String)
                                            carrierMessagingClientServiceFinder.mLastMessages.get(
                                                    keyAt2));
                            printWriter.println();
                        } finally {
                        }
                    }
                }
                return;
        }
    }
}
