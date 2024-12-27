package com.android.systemui.navigationbar.store;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.android.systemui.navigationbar.model.NavBarStates;
import com.android.systemui.plank.command.PlankCommandDispatcher;
import java.util.HashMap;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class NavBarCommandDispatcher implements PlankCommandDispatcher {
    public final boolean enabled;
    public final NavBarStore navBarStore;
    public final HashMap originNavState = new HashMap();
    public final Handler handler = new Handler(Looper.getMainLooper());

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

    public NavBarCommandDispatcher(NavBarStore navBarStore) {
        this.navBarStore = navBarStore;
        if (Build.TYPE.equals("user")) {
            return;
        }
        Log.d("NaBarCommandDispatcher", "init()");
        this.enabled = true;
    }

    public final void copyPrevStatesIfNeeded(int i) {
        if (this.originNavState.get(Integer.valueOf(i)) == null) {
            Log.d("NaBarCommandDispatcher", "copyPrevStates()");
            this.originNavState.put(Integer.valueOf(i), NavBarStates.copy$default(((NavBarStateManagerImpl) ((NavBarStoreImpl) this.navBarStore).getNavStateManager(i)).states));
        }
    }

    @Override // com.android.systemui.plank.command.PlankCommandDispatcher
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.os.Bundle dispatch(android.os.Bundle r18, java.lang.String r19) {
        /*
            Method dump skipped, instructions count: 392
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.navigationbar.store.NavBarCommandDispatcher.dispatch(android.os.Bundle, java.lang.String):android.os.Bundle");
    }
}
