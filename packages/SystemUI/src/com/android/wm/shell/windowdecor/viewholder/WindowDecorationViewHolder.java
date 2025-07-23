package com.android.wm.shell.windowdecor.viewholder;

import android.content.Context;
import android.view.View;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class WindowDecorationViewHolder implements AutoCloseable {
    public final Context context;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class Data {
    }

    public WindowDecorationViewHolder(View view) {
        this.context = view.getContext();
    }

    public abstract void onHandleMenuClosed();

    public abstract void onHandleMenuOpened();
}
