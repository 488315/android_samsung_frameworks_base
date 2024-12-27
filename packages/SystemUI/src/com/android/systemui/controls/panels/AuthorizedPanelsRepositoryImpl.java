package com.android.systemui.controls.panels;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.UserHandle;
import com.android.systemui.R;
import com.android.systemui.settings.UserFileManager;
import com.android.systemui.settings.UserFileManagerImpl;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.util.SystemUIAnalytics;
import java.util.Set;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.EmptySet;
import kotlin.collections.SetsKt___SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class AuthorizedPanelsRepositoryImpl implements AuthorizedPanelsRepository {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Context context;
    public final UserFileManager userFileManager;
    public final UserTracker userTracker;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public AuthorizedPanelsRepositoryImpl(Context context, UserFileManager userFileManager, UserTracker userTracker) {
        this.context = context;
        this.userFileManager = userFileManager;
        this.userTracker = userTracker;
    }

    public final void addAuthorizedPanels(Set set) {
        SharedPreferences instantiateSharedPrefs = instantiateSharedPrefs(((UserTrackerImpl) this.userTracker).getUserHandle());
        Set<String> stringSet = instantiateSharedPrefs.getStringSet("authorized_panels", EmptySet.INSTANCE);
        Intrinsics.checkNotNull(stringSet);
        instantiateSharedPrefs.edit().putStringSet("authorized_panels", SetsKt___SetsKt.plus((Set) stringSet, (Iterable) set)).apply();
    }

    public final SharedPreferences instantiateSharedPrefs(UserHandle userHandle) {
        SharedPreferences sharedPreferences$1 = ((UserFileManagerImpl) this.userFileManager).getSharedPreferences$1(userHandle.getIdentifier(), SystemUIAnalytics.CONTROL_PREF_NAME);
        if (sharedPreferences$1.getStringSet("authorized_panels", null) == null) {
            sharedPreferences$1.edit().putStringSet("authorized_panels", ArraysKt___ArraysKt.toSet(this.context.getResources().getStringArray(R.array.config_controlsPreferredPackages))).apply();
        }
        return sharedPreferences$1;
    }
}
