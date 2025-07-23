package com.android.systemui.statusbar.notification.collection.listbuilder.pluggable;

import android.os.Trace;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class Pluggable {
    private PluggableListener mListener;
    private final String mName;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface PluggableListener {
        void onPluggableInvalidated(Pluggable pluggable, String str);
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
