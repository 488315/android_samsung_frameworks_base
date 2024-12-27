package com.android.systemui.statusbar.notification.collection.listbuilder.pluggable;

import android.os.Trace;

public abstract class Pluggable {
    private PluggableListener mListener;
    private final String mName;

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
