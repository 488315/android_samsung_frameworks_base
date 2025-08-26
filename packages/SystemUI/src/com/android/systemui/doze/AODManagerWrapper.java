package com.android.systemui.doze;

import android.content.Context;
import com.samsung.android.aod.AODManager;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class AODManagerWrapper {
    public static final Companion Companion = new Companion(null);
    public static AODManagerWrapper sInstance;
    public final Context mContext;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public /* synthetic */ AODManagerWrapper(Context context, DefaultConstructorMarker defaultConstructorMarker) {
        this(context);
    }

    public final AODManager getService() {
        return AODManager.getInstance(this.mContext);
    }

    private AODManagerWrapper(Context context) {
        this.mContext = context;
    }
}
