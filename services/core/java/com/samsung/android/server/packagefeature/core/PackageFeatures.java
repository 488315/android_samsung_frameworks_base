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

public final class PackageFeatures {
    public boolean mAllFeaturesDisabled;
    public final Map mGroups = new ConcurrentHashMap();

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
