package com.android.systemui.qs;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;
import com.android.systemui.qs.pipeline.data.repository.DefaultTilesRepository;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class TileFeatureChecker {
    public final Context context;
    public final DefaultTilesRepository defaultTilesRepository;
    public final UserTracker userTracker;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public TileFeatureChecker(Context context, UserTracker userTracker, DefaultTilesRepository defaultTilesRepository) {
        this.context = context;
        this.userTracker = userTracker;
        this.defaultTilesRepository = defaultTilesRepository;
    }

    /* JADX WARN: Removed duplicated region for block: B:41:0x024a A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isAvailableCustomTile(com.android.systemui.qs.pipeline.shared.TileSpec r8) {
        /*
            Method dump skipped, instructions count: 626
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.TileFeatureChecker.isAvailableCustomTile(com.android.systemui.qs.pipeline.shared.TileSpec):boolean");
    }

    public final boolean isPackageAvailable(String str) {
        try {
            this.context.getPackageManager().getPackageInfoAsUser(str, 0, ((UserTrackerImpl) this.userTracker).getUserId());
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("TileFeatureChecker", "Package not available: " + str, e);
            return false;
        }
    }
}
