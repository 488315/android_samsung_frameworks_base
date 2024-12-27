package com.android.server.flags;

import android.content.Context;
import android.flags.FeatureFlags;
import android.flags.IFeatureFlags;
import android.util.Slog;
import com.android.server.SystemService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class FeatureFlagsService extends SystemService {
    public final FlagOverrideStore mFlagStore;
    public final FlagsShellCommand mShellCommand;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class PermissionsChecker {
        public final Context mContext;

        public PermissionsChecker(Context context) {
            this.mContext = context;
        }

        public void assertSyncPermission() {
            if (this.mContext.checkCallingOrSelfPermission("android.permission.SYNC_FLAGS") != 0) {
                throw new SecurityException("Non-core flag queried. Requires SYNC_FLAGS permission!");
            }
        }

        public void assertWritePermission() {
            if (this.mContext.checkCallingPermission("android.permission.WRITE_FLAGS") != 0) {
                throw new SecurityException("Requires WRITE_FLAGS permission!");
            }
        }
    }

    public FeatureFlagsService(Context context) {
        super(context);
        FlagOverrideStore flagOverrideStore = new FlagOverrideStore(new GlobalSettingsProxy(context.getContentResolver()));
        this.mFlagStore = flagOverrideStore;
        this.mShellCommand = new FlagsShellCommand(flagOverrideStore);
    }

    @Override // com.android.server.SystemService
    public final void onBootPhase(int i) {
        super.onBootPhase(i);
        if (i == 500) {
            FeatureFlags.getInstance().sync();
        }
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        Slog.d("FeatureFlagsService", "Started Feature Flag Service");
        IFeatureFlags.Stub featureFlagsBinder = new FeatureFlagsBinder(this.mFlagStore, this.mShellCommand, new PermissionsChecker(getContext()));
        publishBinderService("feature_flags", featureFlagsBinder);
        publishLocalService(FeatureFlags.class, new FeatureFlags(featureFlagsBinder));
    }
}
