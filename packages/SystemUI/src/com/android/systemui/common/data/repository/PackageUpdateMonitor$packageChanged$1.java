package com.android.systemui.common.data.repository;

import com.android.systemui.common.shared.model.PackageChangeModel;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.AdaptedFunctionReference;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final /* synthetic */ class PackageUpdateMonitor$packageChanged$1 extends AdaptedFunctionReference implements Function2 {
    public PackageUpdateMonitor$packageChanged$1(Object obj) {
        super(2, obj, PackageUpdateLogger.class, "logChange", "logChange(Lcom/android/systemui/common/shared/model/PackageChangeModel;)V", 4);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        String str;
        PackageChangeModel packageChangeModel = (PackageChangeModel) obj;
        PackageUpdateLogger packageUpdateLogger = (PackageUpdateLogger) this.receiver;
        int i = PackageUpdateMonitor.$r8$clinit;
        packageUpdateLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        PackageUpdateLogger$$ExternalSyntheticLambda0 packageUpdateLogger$$ExternalSyntheticLambda0 = new PackageUpdateLogger$$ExternalSyntheticLambda0();
        LogBuffer logBuffer = packageUpdateLogger.buffer;
        LogMessage obtain = logBuffer.obtain("PackageChangeRepoLog", logLevel, packageUpdateLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = packageChangeModel.getPackageName();
        if (packageChangeModel instanceof PackageChangeModel.Installed) {
            str = "installed";
        } else if (packageChangeModel instanceof PackageChangeModel.Uninstalled) {
            str = "uninstalled";
        } else if (packageChangeModel instanceof PackageChangeModel.UpdateStarted) {
            str = "started updating";
        } else if (packageChangeModel instanceof PackageChangeModel.UpdateFinished) {
            str = "finished updating";
        } else {
            if (!(packageChangeModel instanceof PackageChangeModel.Changed)) {
                if (!(packageChangeModel instanceof PackageChangeModel.Empty)) {
                    throw new NoWhenBranchMatchedException();
                }
                throw new IllegalStateException("Unexpected empty value: " + packageChangeModel);
            }
            str = "changed";
        }
        logMessageImpl.str2 = str;
        logMessageImpl.int1 = packageChangeModel.getPackageUid();
        logBuffer.commit(obtain);
        return Unit.INSTANCE;
    }
}
