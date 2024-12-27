package com.samsung.android.server.packagefeature.core;

import android.content.Context;
import android.hardware.soundtrigger.V2_3.OptionalModelParameterRange$$ExternalSyntheticOutline0;
import android.os.Handler;

import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;

import com.samsung.android.server.packagefeature.PackageFeatureCallback;
import com.samsung.android.server.packagefeature.PackageFeatureData;
import com.samsung.android.server.packagefeature.PackageFeatureGroup;
import com.samsung.android.server.util.CoreLogger;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class UnformattedPackageFeatureGroupRecord extends PackageFeatureGroupRecord {
    public Function mGetFileDescriptor;
    public final String mGroupName;

    public UnformattedPackageFeatureGroupRecord(
            Context context,
            Handler handler,
            CoreLogger coreLogger,
            PackageFeatureGroup packageFeatureGroup,
            PackageFeatures$$ExternalSyntheticLambda1 packageFeatures$$ExternalSyntheticLambda1) {
        super(
                context,
                handler,
                coreLogger,
                packageFeatureGroup,
                packageFeatures$$ExternalSyntheticLambda1);
        this.mGroupName = packageFeatureGroup.mName;
    }

    @Override // com.samsung.android.server.packagefeature.core.PackageFeatureGroupRecord
    public final void initialize$1() {
        this.mGetFileDescriptor = null;
        super.initialize$1();
    }

    @Override // com.samsung.android.server.packagefeature.core.PackageFeatureGroupRecord
    public final void propagateToCallback(
            final String str,
            final PackageFeatureCallback packageFeatureCallback,
            PackageFeatureData packageFeatureData,
            final int i,
            final int i2,
            final String str2) {
        if (this.mGetFileDescriptor == null) {
            return;
        }
        this.mHandler.post(
                new PackageFeatureGroupRecord$$ExternalSyntheticLambda3(
                        this,
                        new Supplier() { // from class:
                                         // com.samsung.android.server.packagefeature.core.UnformattedPackageFeatureGroupRecord$$ExternalSyntheticLambda0
                            @Override // java.util.function.Supplier
                            public final Object get() {
                                UnformattedPackageFeatureGroupRecord
                                        unformattedPackageFeatureGroupRecord =
                                                UnformattedPackageFeatureGroupRecord.this;
                                String str3 = str;
                                int i3 = i2;
                                int i4 = i;
                                String str4 = str2;
                                unformattedPackageFeatureGroupRecord.getClass();
                                StringBuilder sb = new StringBuilder("to propagate to ");
                                sb.append(str3);
                                sb.append(" callback");
                                sb.append(
                                        i3 > 1
                                                ? DualAppManagerService$$ExternalSyntheticOutline0
                                                        .m(i4, i3, "(", "/", ")")
                                                : "");
                                sb.append(" for ");
                                return OptionalModelParameterRange$$ExternalSyntheticOutline0.m(
                                        sb,
                                        unformattedPackageFeatureGroupRecord.mGroupName,
                                        ", reason=",
                                        str4,
                                        ", Unformatted");
                            }
                        },
                        new Consumer() { // from class:
                                         // com.samsung.android.server.packagefeature.core.UnformattedPackageFeatureGroupRecord$$ExternalSyntheticLambda1
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                UnformattedPackageFeatureGroupRecord
                                        unformattedPackageFeatureGroupRecord =
                                                UnformattedPackageFeatureGroupRecord.this;
                                packageFeatureCallback.onUnformattedPackageFeatureFileChanged(
                                        unformattedPackageFeatureGroupRecord.mGroupName,
                                        unformattedPackageFeatureGroupRecord.mGetFileDescriptor);
                            }
                        }));
    }

    @Override // com.samsung.android.server.packagefeature.core.PackageFeatureGroupRecord
    public final void propagateToCallbacks() {
        if (this.mGetFileDescriptor == null) {
            return;
        }
        super.propagateToCallbacks();
    }

    @Override // com.samsung.android.server.packagefeature.core.PackageFeatureGroupRecord
    public final void updateGroupDataFromScpm(
            PackageFeatureManagerService$$ExternalSyntheticLambda1
                    packageFeatureManagerService$$ExternalSyntheticLambda1) {
        this.mGetFileDescriptor = packageFeatureManagerService$$ExternalSyntheticLambda1;
        if (this.mGroupDataDummy == null) {
            this.mGroupDataDummy = new PackageFeatureGroupData(0);
        }
        updateGroupData(this.mGroupDataDummy, PackageFeatureGroupRecord.GroupDataSource.SCPM);
        propagateToCallbacks();
    }
}
