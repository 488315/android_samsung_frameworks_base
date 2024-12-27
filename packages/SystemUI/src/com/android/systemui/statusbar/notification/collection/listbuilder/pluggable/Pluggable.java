package com.android.systemui.statusbar.notification.collection.listbuilder.pluggable;

import android.os.Trace;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public abstract class Pluggable {
    private PluggableListener mListener;
    private final String mName;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface PluggableListener {
        void onPluggableInvalidated(Object obj, String str);
    }

    public Pluggable(String str) {
        this.mName = str;
    }

    public final String getName() {
        return this.mName;
    }

    public final void invalidateList(String str) {
        if (this.mListener != null) {
            if (Trace.isEnabled()) {
                Trace.traceBegin(4096L, "Pluggable<" + this.mName + ">.invalidateList");
            }
            this.mListener.onPluggableInvalidated(this, str);
            Trace.endSection();
        }
    }

    public final void setInvalidationListener(PluggableListener pluggableListener) {
        this.mListener = pluggableListener;
    }

    public void onCleanup() {
    }
}
