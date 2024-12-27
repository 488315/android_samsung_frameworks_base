package com.android.systemui.navigationbar.store;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.android.systemui.navigationbar.model.NavBarStates;
import com.android.systemui.plank.command.PlankCommandDispatcher;
import java.util.HashMap;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class NavBarCommandDispatcher implements PlankCommandDispatcher {
    public final boolean enabled;
    public final NavBarStore navBarStore;
    public final HashMap originNavState = new HashMap();
    public final Handler handler = new Handler(Looper.getMainLooper());

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

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:39:0x015e A[Catch: Exception -> 0x0086, TRY_LEAVE, TryCatch #0 {Exception -> 0x0086, blocks: (B:3:0x0012, B:8:0x003c, B:11:0x004d, B:15:0x0059, B:18:0x0060, B:19:0x0089, B:21:0x0099, B:25:0x00a5, B:28:0x00ac, B:29:0x00c9, B:31:0x00d9, B:34:0x00e3, B:36:0x00f1, B:39:0x015e, B:42:0x00fc, B:43:0x0112, B:47:0x011d, B:50:0x0124, B:52:0x0130, B:53:0x0148), top: B:2:0x0012 }] */
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
