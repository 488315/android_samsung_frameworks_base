package com.samsung.android.server.packagefeature.core;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class PackageFeatureSettings extends ContentObserver {
    public static final Uri URI_PACKAGE_POLICY_DISABLED =
            Settings.Global.getUriFor("package_policy_disabled");
    public final Callback mCallback;
    public final Context mContext;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface Callback {}

    public PackageFeatureSettings(Context context, Handler handler, Callback callback, boolean z) {
        super(handler);
        this.mContext = context;
        this.mCallback = callback;
        if (z) {
            Settings.Global.putInt(context.getContentResolver(), "package_policy_disabled", 0);
        }
        context.getContentResolver()
                .registerContentObserver(
                        Settings.Global.getUriFor("package_policy_disabled"), false, this, -1);
    }

    @Override // android.database.ContentObserver
    public final void onChange(boolean z, Uri uri) {
        if (uri == null || !URI_PACKAGE_POLICY_DISABLED.equals(uri)) {
            return;
        }
        PackageFeatureController packageFeatureController =
                (PackageFeatureController) this.mCallback;
        final boolean z2 =
                Settings.Global.getInt(
                                packageFeatureController.mSettings.mContext.getContentResolver(),
                                "package_policy_disabled",
                                0)
                        == 1;
        synchronized (packageFeatureController.mLock) {
            PackageFeatures packageFeatures = packageFeatureController.mPackageFeatures;
            if (packageFeatures.mAllFeaturesDisabled != z2) {
                packageFeatures.mAllFeaturesDisabled = z2;
                packageFeatures.forAllGroups(
                        new Consumer(
                                z2) { // from class:
                                      // com.samsung.android.server.packagefeature.core.PackageFeatures$$ExternalSyntheticLambda0
                            public final /* synthetic */ boolean f$0;

                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                PackageFeatureGroupRecord packageFeatureGroupRecord =
                                        (PackageFeatureGroupRecord) obj;
                                packageFeatureGroupRecord.getClass();
                                packageFeatureGroupRecord.propagateToCallbacks();
                            }
                        });
                packageFeatureController.mLogger.log(
                        3, "onSettingsChanged, mAllFeaturesDisabled=" + z2, null);
            }
        }
    }
}
