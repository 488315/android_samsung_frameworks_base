package com.samsung.android.server.corescpm;

import com.samsung.android.server.packagefeature.core.PackageFeatureManagerService;

import java.util.Objects;

public interface ScpmController {

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
            return Objects.hash(
                    this.mPackageName, this.mReceiverPackageName, this.mAppId, this.mVersion);
        }
    }
}
