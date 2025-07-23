package com.android.systemui.mediaprojection.permission;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.android.systemui.R;
import java.util.function.Consumer;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SystemCastPermissionDialogDelegate extends BaseMediaProjectionPermissionDialogDelegate {
    public static final Companion Companion = new Companion(null);
    public final Runnable onCancelClicked;
    public final Consumer onStartRecordingClicked;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public SystemCastPermissionDialogDelegate(android.content.Context r19, android.media.projection.MediaProjectionConfig r20, java.util.function.Consumer<com.android.systemui.mediaprojection.permission.BaseMediaProjectionPermissionDialogDelegate> r21, java.lang.Runnable r22, java.lang.String r23, boolean r24, int r25, com.android.systemui.mediaprojection.MediaProjectionMetricsLogger r26) {
        /*
            r18 = this;
            com.android.systemui.mediaprojection.permission.SystemCastPermissionDialogDelegate$Companion r0 = com.android.systemui.mediaprojection.permission.SystemCastPermissionDialogDelegate.Companion
            r0.getClass()
            com.android.systemui.mediaprojection.permission.MediaProjectionPermissionUtils r0 = com.android.systemui.mediaprojection.permission.MediaProjectionPermissionUtils.INSTANCE
            r0.getClass()
            if (r24 != 0) goto L24
            if (r20 == 0) goto L24
            int r0 = r20.getRegionToCapture()
            r1 = 1
            if (r0 != r1) goto L24
            r0 = 2131954951(0x7f130d07, float:1.9546416E38)
            java.lang.Object[] r1 = new java.lang.Object[]{r23}
            r2 = r19
            java.lang.String r0 = r2.getString(r0, r1)
        L22:
            r7 = r0
            goto L26
        L24:
            r0 = 0
            goto L22
        L26:
            com.android.systemui.mediaprojection.permission.ScreenShareOption r1 = new com.android.systemui.mediaprojection.permission.ScreenShareOption
            r6 = 0
            r8 = 0
            r2 = 0
            r3 = 2131954959(0x7f130d0f, float:1.9546432E38)
            r4 = 2131954962(0x7f130d12, float:1.9546438E38)
            r5 = 2131954964(0x7f130d14, float:1.9546442E38)
            r9 = 80
            r10 = 0
            r1.<init>(r2, r3, r4, r5, r6, r7, r8, r9, r10)
            com.android.systemui.mediaprojection.permission.ScreenShareOption r8 = new com.android.systemui.mediaprojection.permission.ScreenShareOption
            r14 = 0
            r15 = 0
            r9 = 1
            r10 = 2131954958(0x7f130d0e, float:1.954643E38)
            r11 = 2131954961(0x7f130d11, float:1.9546436E38)
            r12 = 2131954957(0x7f130d0d, float:1.9546428E38)
            r13 = 0
            r16 = 112(0x70, float:1.57E-43)
            r17 = 0
            r8.<init>(r9, r10, r11, r12, r13, r14, r15, r16, r17)
            com.android.systemui.mediaprojection.permission.ScreenShareOption[] r0 = new com.android.systemui.mediaprojection.permission.ScreenShareOption[]{r1, r8}
            java.util.List r0 = java.util.Arrays.asList(r0)
            if (r7 == 0) goto L60
            java.lang.Iterable r0 = (java.lang.Iterable) r0
            java.util.List r0 = kotlin.collections.CollectionsKt___CollectionsKt.reversed(r0)
        L60:
            r1 = r0
            r0 = 2131232876(0x7f08086c, float:1.8081874E38)
            java.lang.Integer r5 = java.lang.Integer.valueOf(r0)
            r6 = 0
            r7 = 0
            r8 = 96
            r9 = 0
            r0 = r18
            r2 = r23
            r3 = r25
            r4 = r26
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8, r9)
            r1 = r21
            r0.onStartRecordingClicked = r1
            r1 = r22
            r0.onCancelClicked = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.mediaprojection.permission.SystemCastPermissionDialogDelegate.<init>(android.content.Context, android.media.projection.MediaProjectionConfig, java.util.function.Consumer, java.lang.Runnable, java.lang.String, boolean, int, com.android.systemui.mediaprojection.MediaProjectionMetricsLogger):void");
    }

    @Override // com.android.systemui.mediaprojection.permission.BaseMediaProjectionPermissionDialogDelegate, com.android.systemui.statusbar.phone.DialogDelegate
    public final void onCreate(final AlertDialog alertDialog, Bundle bundle) {
        super.onCreate(alertDialog, bundle);
        setDialogTitle(R.string.media_projection_entry_cast_permission_dialog_title);
        View.OnClickListener onClickListener = new View.OnClickListener() { // from class: com.android.systemui.mediaprojection.permission.SystemCastPermissionDialogDelegate$onCreate$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SystemCastPermissionDialogDelegate systemCastPermissionDialogDelegate = SystemCastPermissionDialogDelegate.this;
                systemCastPermissionDialogDelegate.onStartRecordingClicked.accept(systemCastPermissionDialogDelegate);
                alertDialog.dismiss();
            }
        };
        BaseMediaProjectionPermissionViewBinder baseMediaProjectionPermissionViewBinder = this.viewBinder;
        if (baseMediaProjectionPermissionViewBinder == null) {
            baseMediaProjectionPermissionViewBinder = null;
        }
        TextView textView = baseMediaProjectionPermissionViewBinder.startButton;
        if (textView == null) {
            textView = null;
        }
        textView.setOnClickListener(new BaseMediaProjectionPermissionViewBinder$setStartButtonOnClickListener$1(baseMediaProjectionPermissionViewBinder, onClickListener));
        View.OnClickListener onClickListener2 = new View.OnClickListener() { // from class: com.android.systemui.mediaprojection.permission.SystemCastPermissionDialogDelegate$onCreate$2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SystemCastPermissionDialogDelegate.this.onCancelClicked.run();
                alertDialog.dismiss();
            }
        };
        TextView textView2 = this.cancelButton;
        (textView2 != null ? textView2 : null).setOnClickListener(onClickListener2);
    }
}
