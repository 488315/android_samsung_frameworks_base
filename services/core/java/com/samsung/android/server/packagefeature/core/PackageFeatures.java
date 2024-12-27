package com.samsung.android.server.packagefeature.core;

import android.content.Context;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.os.Handler;

import com.samsung.android.server.packagefeature.PackageFeature;
import com.samsung.android.server.packagefeature.PackageFeatureGroup;
import com.samsung.android.server.util.CoreLogger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class PackageFeatures {
    public boolean mAllFeaturesDisabled;
    public final Map mGroups = new ConcurrentHashMap();

    /* JADX WARN: Type inference failed for: r9v1, types: [com.samsung.android.server.packagefeature.core.PackageFeatures$$ExternalSyntheticLambda1] */
    public PackageFeatures(Context context, Handler handler, CoreLogger coreLogger) {
        for (PackageFeatureGroup packageFeatureGroup : PackageFeatureGroup.values()) {
            if (packageFeatureGroup.mEnabled) {
                ((ConcurrentHashMap) this.mGroups)
                        .put(
                                packageFeatureGroup.mName,
                                packageFeatureGroup.mUnformatted
                                        ? new UnformattedPackageFeatureGroupRecord(
                                                context,
                                                handler,
                                                coreLogger,
                                                packageFeatureGroup,
                                                new Supplier() { // from class:
                                                                 // com.samsung.android.server.packagefeature.core.PackageFeatures$$ExternalSyntheticLambda1
                                                    @Override // java.util.function.Supplier
                                                    public final Object get() {
                                                        return Boolean.valueOf(
                                                                PackageFeatures.this
                                                                        .mAllFeaturesDisabled);
                                                    }
                                                })
                                        : new PackageFeatureGroupRecord(
                                                context,
                                                handler,
                                                coreLogger,
                                                packageFeatureGroup,
                                                new Supplier() { // from class:
                                                                 // com.samsung.android.server.packagefeature.core.PackageFeatures$$ExternalSyntheticLambda1
                                                    @Override // java.util.function.Supplier
                                                    public final Object get() {
                                                        return Boolean.valueOf(
                                                                PackageFeatures.this
                                                                        .mAllFeaturesDisabled);
                                                    }
                                                }));
            } else {
                coreLogger.log(
                        3,
                        AudioOffloadInfo$$ExternalSyntheticOutline0.m(
                                new StringBuilder("PackageFeatureGroup("),
                                packageFeatureGroup.mName,
                                ") is not enabled."),
                        null);
            }
        }
        for (PackageFeature packageFeature : PackageFeature.values()) {
            packageFeature.getClass();
        }
    }

    public final void forAllGroups(final Consumer consumer) {
        ((ConcurrentHashMap) this.mGroups)
                .values()
                .forEach(
                        new Consumer() { // from class:
                                         // com.samsung.android.server.packagefeature.core.PackageFeatures$$ExternalSyntheticLambda2
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                consumer.accept((PackageFeatureGroupRecord) obj);
                            }
                        });
    }
}
