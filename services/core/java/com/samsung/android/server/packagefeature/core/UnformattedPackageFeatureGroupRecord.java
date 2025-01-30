package com.samsung.android.server.packagefeature.core;

import android.content.Context;
import android.os.Handler;
import com.samsung.android.server.packagefeature.PackageFeatureCallback;
import com.samsung.android.server.packagefeature.PackageFeatureData;
import com.samsung.android.server.packagefeature.PackageFeatureGroup;
import com.samsung.android.server.util.CoreLogger;
import java.util.function.Function;
import java.util.function.Supplier;

/* loaded from: classes2.dex */
public class UnformattedPackageFeatureGroupRecord extends PackageFeatureGroupRecord {
    public Function mGetFileDescriptor;
    public final String mGroupName;

    public UnformattedPackageFeatureGroupRecord(Context context, Handler handler, CoreLogger coreLogger, PackageFeatureGroup packageFeatureGroup, Supplier supplier) {
        super(context, handler, coreLogger, packageFeatureGroup, supplier);
        this.mGroupName = packageFeatureGroup.mName;
    }

    @Override // com.samsung.android.server.packagefeature.core.PackageFeatureGroupRecord
    public void initialize() {
        this.mGetFileDescriptor = null;
        super.initialize();
    }

    @Override // com.samsung.android.server.packagefeature.core.PackageFeatureGroupRecord
    public void updateGroupDataFromScpm(Function function) {
        this.mGetFileDescriptor = function;
        updateGroupDataDummyFromScpm();
        propagateToCallbacks();
    }

    @Override // com.samsung.android.server.packagefeature.core.PackageFeatureGroupRecord
    public void propagateToCallbacks() {
        if (this.mGetFileDescriptor == null) {
            return;
        }
        super.propagateToCallbacks();
    }

    @Override // com.samsung.android.server.packagefeature.core.PackageFeatureGroupRecord
    public void propagateToCallback(String str, PackageFeatureCallback packageFeatureCallback, PackageFeatureData packageFeatureData, int i, int i2, String str2) {
        String str3;
        if (this.mGetFileDescriptor == null) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("to propagate to ");
        sb.append(str);
        sb.append(" callback");
        if (i2 > 1) {
            str3 = "(" + i + "/" + i2 + ")";
        } else {
            str3 = "";
        }
        sb.append(str3);
        sb.append(" for ");
        sb.append(this.mGroupName);
        sb.append(", reason=");
        sb.append(str2);
        sb.append(", Unformatted");
        dispatchUnformattedPackageFeatureFileChanged(sb.toString(), packageFeatureCallback, this.mGroupName, this.mGetFileDescriptor);
    }
}
