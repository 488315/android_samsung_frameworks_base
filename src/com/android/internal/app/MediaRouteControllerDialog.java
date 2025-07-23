package com.android.internal.app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import com.android.internal.R;
import com.android.internal.app.MediaRouteControllerContentManager;

/* loaded from: classes5.dex */
public class MediaRouteControllerDialog extends AlertDialog implements MediaRouteControllerContentManager.Delegate {
    private final MediaRouteControllerContentManager mContentManager;

    public MediaRouteControllerDialog(Context context, int i) {
        super(context, i);
        this.mContentManager = new MediaRouteControllerContentManager(context, this);
    }

    @Override // android.app.AlertDialog, android.app.Dialog
    protected void onCreate(Bundle bundle) {
        setButton(-2, getContext().getResources().getString(R.string.media_route_controller_disconnect), new DialogInterface.OnClickListener() { // from class: com.android.internal.app.MediaRouteControllerDialog$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                MediaRouteControllerDialog.this.lambda$onCreate$0(dialogInterface, i);
            }
        });
        View inflate = getLayoutInflater().inflate(R.layout.media_route_controller_dialog, (ViewGroup) null);
        setView(inflate, 0, 0, 0, 0);
        this.mContentManager.bindViews(inflate);
        super.onCreate(bundle);
        View findViewById = getWindow().findViewById(R.id.customPanel);
        if (findViewById != null) {
            findViewById.setMinimumHeight(0);
        }
        this.mContentManager.update();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(DialogInterface dialogInterface, int i) {
        this.mContentManager.onDisconnectButtonClick();
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mContentManager.onAttachedToWindow();
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public void onDetachedFromWindow() {
        this.mContentManager.onDetachedFromWindow();
        super.onDetachedFromWindow();
    }

    @Override // android.app.AlertDialog, android.app.Dialog, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 25 || i == 24) {
            this.mContentManager.requestUpdateRouteVolume(i == 25 ? -1 : 1);
            return true;
        }
        return super.onKeyDown(i, keyEvent);
    }

    @Override // android.app.AlertDialog, android.app.Dialog, android.view.KeyEvent.Callback
    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (i == 25 || i == 24) {
            return true;
        }
        return super.onKeyUp(i, keyEvent);
    }

    @Override // com.android.internal.app.MediaRouteControllerContentManager.Delegate
    public void setMediaRouteDeviceTitle(CharSequence charSequence) {
        setTitle(charSequence);
    }

    @Override // com.android.internal.app.MediaRouteControllerContentManager.Delegate
    public void setMediaRouteDeviceIcon(Drawable drawable) {
        setIcon(drawable);
    }

    @Override // com.android.internal.app.MediaRouteControllerContentManager.Delegate
    public void dismissView() {
        dismiss();
    }
}
