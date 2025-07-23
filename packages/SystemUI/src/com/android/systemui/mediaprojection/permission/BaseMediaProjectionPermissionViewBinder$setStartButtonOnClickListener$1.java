package com.android.systemui.mediaprojection.permission;

import android.view.View;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class BaseMediaProjectionPermissionViewBinder$setStartButtonOnClickListener$1 implements View.OnClickListener {
    public final /* synthetic */ View.OnClickListener $listener;
    public final /* synthetic */ BaseMediaProjectionPermissionViewBinder this$0;

    public BaseMediaProjectionPermissionViewBinder$setStartButtonOnClickListener$1(BaseMediaProjectionPermissionViewBinder baseMediaProjectionPermissionViewBinder, View.OnClickListener onClickListener) {
        this.this$0 = baseMediaProjectionPermissionViewBinder;
        this.$listener = onClickListener;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        this.this$0.shouldLogCancel = false;
        View.OnClickListener onClickListener = this.$listener;
        if (onClickListener != null) {
            onClickListener.onClick(view);
        }
    }
}
