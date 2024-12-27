package com.android.server;

import android.os.Bundle;
import android.os.RemoteCallback;
import android.service.watchdog.ExplicitHealthCheckService;
import android.text.TextUtils;
import android.util.Slog;

import java.util.ArrayList;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class ExplicitHealthCheckController$$ExternalSyntheticLambda3
        implements RemoteCallback.OnResultListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ ExplicitHealthCheckController$$ExternalSyntheticLambda3(
            int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    public /* synthetic */ ExplicitHealthCheckController$$ExternalSyntheticLambda3(
            ExplicitHealthCheckController$$ExternalSyntheticLambda2
                    explicitHealthCheckController$$ExternalSyntheticLambda2) {
        this.$r8$classId = 1;
        this.f$0 = explicitHealthCheckController$$ExternalSyntheticLambda2;
    }

    public final void onResult(Bundle bundle) {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ArrayList parcelableArrayList =
                        bundle.getParcelableArrayList(
                                "android.service.watchdog.extra.supported_packages",
                                ExplicitHealthCheckService.PackageConfig.class);
                Slog.i(
                        "ExplicitHealthCheckController",
                        "Explicit health check supported packages " + parcelableArrayList);
                ((Consumer) obj).accept(parcelableArrayList);
                break;
            case 1:
                ArrayList<String> stringArrayList =
                        bundle.getStringArrayList(
                                "android.service.watchdog.extra.requested_packages");
                Slog.i(
                        "ExplicitHealthCheckController",
                        "Explicit health check requested packages " + stringArrayList);
                ((Consumer) obj).accept(stringArrayList);
                break;
            default:
                ExplicitHealthCheckController explicitHealthCheckController =
                        (ExplicitHealthCheckController) obj;
                explicitHealthCheckController.getClass();
                String string =
                        bundle.getString(
                                "android.service.watchdog.extra.health_check_passed_package");
                if (!TextUtils.isEmpty(string)) {
                    Consumer consumer = explicitHealthCheckController.mPassedConsumer;
                    if (consumer != null) {
                        consumer.accept(string);
                        break;
                    } else {
                        Slog.wtf(
                                "ExplicitHealthCheckController",
                                "Health check passed for package "
                                        + string
                                        + "but no consumer registered.");
                        break;
                    }
                } else {
                    Slog.wtf(
                            "ExplicitHealthCheckController",
                            "Empty package passed explicit health check?");
                    break;
                }
        }
    }
}
