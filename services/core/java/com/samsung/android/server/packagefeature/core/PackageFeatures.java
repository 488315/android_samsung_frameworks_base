package com.samsung.android.server.packagefeature.core;

import android.content.Context;
import android.os.Handler;
import com.samsung.android.server.packagefeature.PackageFeature;
import com.samsung.android.server.packagefeature.PackageFeatureController;
import com.samsung.android.server.packagefeature.PackageFeatureGroup;
import com.samsung.android.server.util.CoreLogger;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* loaded from: classes2.dex */
public class PackageFeatures {
    public boolean mAllFeaturesDisabled;
    public final Context mContext;
    public final Map mGroups = new ConcurrentHashMap();
    public final Handler mHandler;

    public PackageFeatures(Context context, Handler handler, final PackageFeatureController packageFeatureController, CoreLogger coreLogger) {
        Object packageFeatureGroupRecord;
        this.mContext = context;
        this.mHandler = handler;
        for (PackageFeatureGroup packageFeatureGroup : PackageFeatureGroup.values()) {
            if (packageFeatureGroup.mEnabled) {
                if (packageFeatureGroup.mUnformatted) {
                    packageFeatureGroupRecord = new UnformattedPackageFeatureGroupRecord(this.mContext, this.mHandler, coreLogger, packageFeatureGroup, new Supplier() { // from class: com.samsung.android.server.packagefeature.core.PackageFeatures$$ExternalSyntheticLambda2
                        @Override // java.util.function.Supplier
                        public final Object get() {
                            boolean isAllFeaturesDisabled;
                            isAllFeaturesDisabled = PackageFeatures.this.isAllFeaturesDisabled();
                            return Boolean.valueOf(isAllFeaturesDisabled);
                        }
                    });
                } else {
                    packageFeatureGroupRecord = new PackageFeatureGroupRecord(this.mContext, this.mHandler, coreLogger, packageFeatureGroup, new Supplier() { // from class: com.samsung.android.server.packagefeature.core.PackageFeatures$$ExternalSyntheticLambda2
                        @Override // java.util.function.Supplier
                        public final Object get() {
                            boolean isAllFeaturesDisabled;
                            isAllFeaturesDisabled = PackageFeatures.this.isAllFeaturesDisabled();
                            return Boolean.valueOf(isAllFeaturesDisabled);
                        }
                    });
                }
                this.mGroups.put(packageFeatureGroup.mName, packageFeatureGroupRecord);
            } else {
                coreLogger.log(3, "PackageFeatureGroup(" + packageFeatureGroup.mName + ") is not enabled.");
            }
        }
        handler.post(new Runnable() { // from class: com.samsung.android.server.packagefeature.core.PackageFeatures$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                PackageFeatures.lambda$new$0(PackageFeatureController.this);
            }
        });
    }

    public static /* synthetic */ void lambda$new$0(PackageFeatureController packageFeatureController) {
        for (PackageFeature packageFeature : PackageFeature.values()) {
            packageFeature.setPackageFeatureController(packageFeatureController);
        }
    }

    public void forAllGroups(final Consumer consumer) {
        this.mGroups.values().forEach(new Consumer() { // from class: com.samsung.android.server.packagefeature.core.PackageFeatures$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                consumer.accept((PackageFeatureGroupRecord) obj);
            }
        });
    }

    public boolean updateAllFeaturesDisabled(boolean z) {
        if (this.mAllFeaturesDisabled == z) {
            return false;
        }
        this.mAllFeaturesDisabled = z;
        forAllGroups(new Consumer() { // from class: com.samsung.android.server.packagefeature.core.PackageFeatures$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((PackageFeatureGroupRecord) obj).propagateToCallbacks();
            }
        });
        return true;
    }

    public final boolean isAllFeaturesDisabled() {
        return this.mAllFeaturesDisabled;
    }

    public Set getGroupNames() {
        return this.mGroups.keySet();
    }

    public PackageFeatureGroupRecord getGroup(String str) {
        return (PackageFeatureGroupRecord) this.mGroups.get(str);
    }

    public void dump(final PrintWriter printWriter, final String str) {
        printWriter.println(str + "mAllFeaturesDisabled=" + this.mAllFeaturesDisabled);
        forAllGroups(new Consumer() { // from class: com.samsung.android.server.packagefeature.core.PackageFeatures$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((PackageFeatureGroupRecord) obj).dump(printWriter, str);
            }
        });
    }
}
