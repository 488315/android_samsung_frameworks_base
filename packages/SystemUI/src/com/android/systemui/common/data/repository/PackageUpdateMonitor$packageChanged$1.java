package com.android.systemui.common.data.repository;

import android.os.UserHandle;
import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import com.android.systemui.common.shared.model.PackageChangeModel;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.AdaptedFunctionReference;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
final /* synthetic */ class PackageUpdateMonitor$packageChanged$1 extends AdaptedFunctionReference implements Function2 {
    public PackageUpdateMonitor$packageChanged$1(Object obj) {
        super(2, obj, PackageUpdateLogger.class, "logChange", "logChange(Lcom/android/systemui/common/shared/model/PackageChangeModel;)V", 4);
    }

    @Override // kotlin.jvm.functions.Function2
    public final /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
        return invoke((PackageChangeModel) obj);
    }

    public final Unit invoke(PackageChangeModel packageChangeModel) {
        String str;
        PackageUpdateLogger packageUpdateLogger = (PackageUpdateLogger) this.receiver;
        int i = PackageUpdateMonitor.$r8$clinit;
        packageUpdateLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        PackageUpdateLogger$logChange$2 packageUpdateLogger$logChange$2 = new Function1() { // from class: com.android.systemui.common.data.repository.PackageUpdateLogger$logChange$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                UserHandle userHandleForUid = UserHandle.getUserHandleForUid(logMessage.getInt1());
                String str1 = logMessage.getStr1();
                int int1 = logMessage.getInt1();
                String str2 = logMessage.getStr2();
                StringBuilder m = ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m(int1, "Package ", str1, " (", ") ");
                m.append(str2);
                m.append(" on user ");
                m.append(userHandleForUid);
                return m.toString();
            }
        };
        LogBuffer logBuffer = packageUpdateLogger.buffer;
        LogMessage obtain = logBuffer.obtain("PackageChangeRepoLog", logLevel, packageUpdateLogger$logChange$2, null);
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
