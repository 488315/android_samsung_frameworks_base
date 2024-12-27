package com.samsung.android.server.corescpm;

import com.samsung.android.server.packagefeature.core.PackageFeatureManagerService;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public interface ScpmController {

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class ConsumerInfo {
        public final String mAppId;
        public final String mPackageName;
        public final String mReceiverPackageName;
        public final String mVersion;

        public ConsumerInfo() {
            String str = PackageFeatureManagerService.ScpmConsumerInfo.VERSION;
            this.mPackageName = "android";
            this.mReceiverPackageName = "android";
            this.mAppId = "hz6wdikdtw";
            this.mVersion = str;
        }

        public final int hashCode() {
            return Objects.hash(this.mPackageName, this.mReceiverPackageName, this.mAppId, this.mVersion);
        }
    }
}
