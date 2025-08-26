package com.android.wm.shell.windowdecor.viewholder;

import android.content.Context;
import android.view.View;

/* loaded from: classes3.dex */
public abstract class WindowDecorationViewHolder implements AutoCloseable {
    public final Context context;

    public abstract class Data {
    }

    public WindowDecorationViewHolder(View view) {
        this.context = view.getContext();
    }

    public abstract void onHandleMenuClosed();

    public abstract void onHandleMenuOpened();
}
