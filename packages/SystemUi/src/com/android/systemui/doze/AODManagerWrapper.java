package com.android.systemui.doze;

import android.content.Context;
import com.samsung.android.aod.AODManager;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AODManagerWrapper {
    public static final Companion Companion = new Companion(null);
    public static AODManagerWrapper sInstance;
    public final Context mContext;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public /* synthetic */ AODManagerWrapper(Context context, DefaultConstructorMarker defaultConstructorMarker) {
        this(context);
    }

    public final AODManager getService() {
        return AODManager.getInstance(this.mContext);
    }

    public final void writeAODCommand(String str, String str2) {
        if (getService() != null) {
            AODManager service = getService();
            Intrinsics.checkNotNull(service);
            service.writeAODCommand(str, str2, (String) null, (String) null, (String) null);
        }
    }

    private AODManagerWrapper(Context context) {
        this.mContext = context;
    }
}
