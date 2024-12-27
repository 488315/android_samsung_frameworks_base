package com.android.systemui.mediaprojection.permission;

import android.view.View;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
