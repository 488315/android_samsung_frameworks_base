package com.android.systemui.mediaprojection.permission;

import android.app.AlertDialog;
import android.content.Context;
import android.media.projection.MediaProjectionConfig;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.TextView;
import com.android.systemui.BasicRune;
import com.android.systemui.R;
import com.android.systemui.mediaprojection.MediaProjectionMetricsLogger;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class ShareToAppPermissionDialogDelegate extends BaseMediaProjectionPermissionDialogDelegate {
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

    public ShareToAppPermissionDialogDelegate(Context context, MediaProjectionConfig mediaProjectionConfig, Consumer<BaseMediaProjectionPermissionDialogDelegate> consumer, Runnable runnable, String str, boolean z, int i, MediaProjectionMetricsLogger mediaProjectionMetricsLogger) {
        Context context2;
        String string;
        Companion.getClass();
        MediaProjectionPermissionUtils.INSTANCE.getClass();
        if (z || mediaProjectionConfig == null || mediaProjectionConfig.getRegionToCapture() != 1) {
            context2 = context;
            string = null;
        } else {
            context2 = context;
            string = context2.getString(R.string.media_projection_entry_app_permission_dialog_single_app_disabled, str);
        }
        String str2 = string;
        Display display = context2.getDisplay();
        ScreenShareOption screenShareOption = new ScreenShareOption(0, R.string.media_projection_entry_app_permission_dialog_option_text_single_app, R.string.media_projection_entry_app_permission_dialog_warning_single_app, R.string.media_projection_entry_generic_permission_dialog_continue_single_app, display != null ? display.getDisplayId() : 0, str2, null, 64, null);
        Display display2 = context2.getDisplay();
        List listAsList = Arrays.asList(screenShareOption, new ScreenShareOption(1, R.string.media_projection_entry_app_permission_dialog_option_text_entire_screen, R.string.media_projection_entry_app_permission_dialog_warning_entire_screen, R.string.media_projection_entry_app_permission_dialog_continue_entire_screen, display2 != null ? display2.getDisplayId() : 0, null, null, 96, null));
        super(str2 != null ? CollectionsKt___CollectionsKt.reversed(listAsList) : listAsList, str, i, mediaProjectionMetricsLogger, Integer.valueOf(R.drawable.ic_present_to_all), null, 0, 96, null);
        this.onStartRecordingClicked = consumer;
        this.onCancelClicked = runnable;
    }

    @Override // com.android.systemui.mediaprojection.permission.BaseMediaProjectionPermissionDialogDelegate, com.android.systemui.statusbar.phone.DialogDelegate
    public final void onCreate(final AlertDialog alertDialog, Bundle bundle) {
        super.onCreate(alertDialog, bundle);
        setDialogTitle(BasicRune.MEDIA_PROJECTION_PERMISSION_CLAIM_CAPTURE ? R.string.media_projection_dialog_title_chn : R.string.media_projection_entry_app_permission_dialog_title);
        View.OnClickListener onClickListener = new View.OnClickListener() { // from class: com.android.systemui.mediaprojection.permission.ShareToAppPermissionDialogDelegate.onCreate.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ShareToAppPermissionDialogDelegate shareToAppPermissionDialogDelegate = ShareToAppPermissionDialogDelegate.this;
                shareToAppPermissionDialogDelegate.onStartRecordingClicked.accept(shareToAppPermissionDialogDelegate);
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
        View.OnClickListener onClickListener2 = new View.OnClickListener() { // from class: com.android.systemui.mediaprojection.permission.ShareToAppPermissionDialogDelegate.onCreate.2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ShareToAppPermissionDialogDelegate.this.onCancelClicked.run();
                alertDialog.dismiss();
            }
        };
        TextView textView2 = this.cancelButton;
        (textView2 != null ? textView2 : null).setOnClickListener(onClickListener2);
    }
}
