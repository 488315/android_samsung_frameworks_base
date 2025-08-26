package com.android.systemui.mediaprojection.permission;

import android.app.AlertDialog;
import android.content.Context;
import android.media.projection.MediaProjectionConfig;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.mediaprojection.MediaProjectionMetricsLogger;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class SystemCastPermissionDialogDelegate extends BaseMediaProjectionPermissionDialogDelegate {
    public static final Companion Companion = new Companion(null);
    public final Runnable onCancelClicked;
    public final Consumer onStartRecordingClicked;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public SystemCastPermissionDialogDelegate(Context context, MediaProjectionConfig mediaProjectionConfig, Consumer<BaseMediaProjectionPermissionDialogDelegate> consumer, Runnable runnable, String str, boolean z, int i, MediaProjectionMetricsLogger mediaProjectionMetricsLogger) {
        Companion.getClass();
        MediaProjectionPermissionUtils.INSTANCE.getClass();
        String string = (z || mediaProjectionConfig == null || mediaProjectionConfig.getRegionToCapture() != 1) ? null : context.getString(R.string.media_projection_entry_app_permission_dialog_single_app_disabled, str);
        List listAsList = Arrays.asList(new ScreenShareOption(0, R.string.media_projection_entry_cast_permission_dialog_option_text_single_app, R.string.media_projection_entry_cast_permission_dialog_warning_single_app, R.string.media_projection_entry_generic_permission_dialog_continue_single_app, 0, string, null, 80, null), new ScreenShareOption(1, R.string.media_projection_entry_cast_permission_dialog_option_text_entire_screen, R.string.media_projection_entry_cast_permission_dialog_warning_entire_screen, R.string.media_projection_entry_cast_permission_dialog_continue_entire_screen, 0, null, null, 112, null));
        super(string != null ? CollectionsKt___CollectionsKt.reversed(listAsList) : listAsList, str, i, mediaProjectionMetricsLogger, Integer.valueOf(R.drawable.ic_cast_connected), null, 0, 96, null);
        this.onStartRecordingClicked = consumer;
        this.onCancelClicked = runnable;
    }

    @Override // com.android.systemui.mediaprojection.permission.BaseMediaProjectionPermissionDialogDelegate, com.android.systemui.statusbar.phone.DialogDelegate
    public final void onCreate(final AlertDialog alertDialog, Bundle bundle) {
        super.onCreate(alertDialog, bundle);
        setDialogTitle(R.string.media_projection_entry_cast_permission_dialog_title);
        View.OnClickListener onClickListener = new View.OnClickListener() { // from class: com.android.systemui.mediaprojection.permission.SystemCastPermissionDialogDelegate.onCreate.1
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
        View.OnClickListener onClickListener2 = new View.OnClickListener() { // from class: com.android.systemui.mediaprojection.permission.SystemCastPermissionDialogDelegate.onCreate.2
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
