package com.android.systemui.mediaprojection.permission;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.android.systemui.R;
import java.util.function.Consumer;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class MediaProjectionPermissionDialogDelegate extends BaseMediaProjectionPermissionDialogDelegate {
    public static final Companion Companion = new Companion(null);
    public final String appName;
    public final Runnable onCancelClicked;
    public final Consumer onStartRecordingClicked;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public MediaProjectionPermissionDialogDelegate(android.content.Context r14, android.media.projection.MediaProjectionConfig r15, java.util.function.Consumer<com.android.systemui.mediaprojection.permission.MediaProjectionPermissionDialogDelegate> r16, java.lang.Runnable r17, java.lang.String r18, boolean r19, int r20, com.android.systemui.mediaprojection.MediaProjectionMetricsLogger r21) {
        /*
            r13 = this;
            r10 = r13
            r11 = r18
            com.android.systemui.mediaprojection.permission.MediaProjectionPermissionDialogDelegate$Companion r0 = com.android.systemui.mediaprojection.permission.MediaProjectionPermissionDialogDelegate.Companion
            r0.getClass()
            if (r11 != 0) goto Le
            r0 = 2131954656(0x7f130be0, float:1.9545817E38)
            goto L11
        Le:
            r0 = 2131954652(0x7f130bdc, float:1.954581E38)
        L11:
            if (r11 != 0) goto L18
            r1 = 2131954655(0x7f130bdf, float:1.9545815E38)
        L16:
            r5 = r1
            goto L1c
        L18:
            r1 = 2131954651(0x7f130bdb, float:1.9545807E38)
            goto L16
        L1c:
            r1 = 0
            if (r11 == 0) goto L2c
            if (r19 != 0) goto L2c
            if (r15 == 0) goto L2c
            int r2 = r15.getRegionToCapture()
            r3 = 1
            if (r2 != r3) goto L2c
            r9 = r3
            goto L2d
        L2c:
            r9 = r1
        L2d:
            if (r9 == 0) goto L3c
            r2 = 2131954649(0x7f130bd9, float:1.9545803E38)
            java.lang.Object[] r3 = new java.lang.Object[]{r18}
            r4 = r14
            java.lang.String r2 = r14.getString(r2, r3)
            goto L3d
        L3c:
            r2 = 0
        L3d:
            com.android.systemui.mediaprojection.permission.ScreenShareOption r12 = new com.android.systemui.mediaprojection.permission.ScreenShareOption
            r3 = 2131955641(0x7f130fb9, float:1.9547815E38)
            r12.<init>(r1, r3, r0, r2)
            com.android.systemui.mediaprojection.permission.ScreenShareOption r0 = new com.android.systemui.mediaprojection.permission.ScreenShareOption
            r7 = 8
            r8 = 0
            r3 = 1
            r4 = 2131955640(0x7f130fb8, float:1.9547813E38)
            r6 = 0
            r2 = r0
            r2.<init>(r3, r4, r5, r6, r7, r8)
            com.android.systemui.mediaprojection.permission.ScreenShareOption[] r0 = new com.android.systemui.mediaprojection.permission.ScreenShareOption[]{r12, r0}
            java.util.List r0 = kotlin.collections.CollectionsKt__CollectionsKt.listOf(r0)
            if (r9 == 0) goto L63
            java.lang.Iterable r0 = (java.lang.Iterable) r0
            java.util.List r0 = kotlin.collections.CollectionsKt___CollectionsKt.reversed(r0)
        L63:
            r1 = r0
            r8 = 112(0x70, float:1.57E-43)
            r9 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            r0 = r13
            r2 = r18
            r3 = r20
            r4 = r21
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8, r9)
            r0 = r16
            r10.onStartRecordingClicked = r0
            r0 = r17
            r10.onCancelClicked = r0
            r10.appName = r11
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.mediaprojection.permission.MediaProjectionPermissionDialogDelegate.<init>(android.content.Context, android.media.projection.MediaProjectionConfig, java.util.function.Consumer, java.lang.Runnable, java.lang.String, boolean, int, com.android.systemui.mediaprojection.MediaProjectionMetricsLogger):void");
    }

    @Override // com.android.systemui.mediaprojection.permission.BaseMediaProjectionPermissionDialogDelegate, com.android.systemui.statusbar.phone.DialogDelegate
    public final void onCreate(final AlertDialog alertDialog, Bundle bundle) {
        super.onCreate(alertDialog, bundle);
        if (this.appName == null) {
            setDialogTitle(R.string.media_projection_entry_cast_permission_dialog_title);
            TextView textView = this.startButton;
            if (textView == null) {
                textView = null;
            }
            textView.setText(R.string.media_projection_entry_cast_permission_dialog_continue);
        } else {
            setDialogTitle(R.string.media_projection_entry_app_permission_dialog_title);
            TextView textView2 = this.startButton;
            if (textView2 == null) {
                textView2 = null;
            }
            textView2.setText(R.string.media_projection_entry_app_permission_dialog_continue);
        }
        View.OnClickListener onClickListener = new View.OnClickListener() { // from class: com.android.systemui.mediaprojection.permission.MediaProjectionPermissionDialogDelegate$onCreate$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MediaProjectionPermissionDialogDelegate mediaProjectionPermissionDialogDelegate = MediaProjectionPermissionDialogDelegate.this;
                mediaProjectionPermissionDialogDelegate.onStartRecordingClicked.accept(mediaProjectionPermissionDialogDelegate);
                alertDialog.dismiss();
            }
        };
        TextView textView3 = this.startButton;
        if (textView3 == null) {
            textView3 = null;
        }
        textView3.setOnClickListener(new BaseMediaProjectionPermissionDialogDelegate$setStartButtonOnClickListener$1(this, onClickListener));
        View.OnClickListener onClickListener2 = new View.OnClickListener() { // from class: com.android.systemui.mediaprojection.permission.MediaProjectionPermissionDialogDelegate$onCreate$2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MediaProjectionPermissionDialogDelegate.this.onCancelClicked.run();
                alertDialog.dismiss();
            }
        };
        TextView textView4 = this.cancelButton;
        (textView4 != null ? textView4 : null).setOnClickListener(onClickListener2);
    }
}
