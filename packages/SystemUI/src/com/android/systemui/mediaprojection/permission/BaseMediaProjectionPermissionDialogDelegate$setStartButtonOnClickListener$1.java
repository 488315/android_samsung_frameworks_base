package com.android.systemui.mediaprojection.permission;

import android.view.View;

public final class BaseMediaProjectionPermissionDialogDelegate$setStartButtonOnClickListener$1 implements View.OnClickListener {
    public final /* synthetic */ View.OnClickListener $listener;
    public final /* synthetic */ BaseMediaProjectionPermissionDialogDelegate this$0;

    public BaseMediaProjectionPermissionDialogDelegate$setStartButtonOnClickListener$1(BaseMediaProjectionPermissionDialogDelegate baseMediaProjectionPermissionDialogDelegate, View.OnClickListener onClickListener) {
        this.this$0 = baseMediaProjectionPermissionDialogDelegate;
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
