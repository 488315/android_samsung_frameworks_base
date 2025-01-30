package com.android.systemui.volume.view.warnings;

import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertController;
import com.android.systemui.R;
import com.android.systemui.volume.store.StoreInteractor;
import com.android.systemui.volume.util.ToastWrapper;
import com.android.systemui.volume.view.expand.AbstractC3626xb6ef341d;
import com.samsung.systemui.splugins.volume.VolumeObserver;
import com.samsung.systemui.splugins.volume.VolumePanelAction;
import com.samsung.systemui.splugins.volume.VolumePanelState;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class VolumeLimiterWarningDialog extends VolumeWarningDialog implements DialogInterface.OnDismissListener, DialogInterface.OnClickListener, VolumeObserver, DialogInterface.OnKeyListener {
    public final StoreInteractor storeInteractor;
    public ToastWrapper toastWrapper;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[VolumePanelState.StateType.values().length];
            try {
                iArr[VolumePanelState.StateType.STATE_COVER_STATE_CHANGED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_DISMISS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_VOLUME_LIMITER_DIALOG_VOLUME_DOWN.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_VOLUME_LIMITER_DIALOG_CANCEL_CLICKED.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_VOLUME_LIMITER_DIALOG_SETTINS_CLICKED.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public VolumeLimiterWarningDialog(Context context) {
        super(context);
        this.storeInteractor = new StoreInteractor(this, null);
        initDialog();
        String string = getContext().getString(R.string.volume_limiter_notice_toast);
        AlertController alertController = this.mAlert;
        alertController.mMessage = string;
        TextView textView = alertController.mMessageView;
        if (textView != null) {
            textView.setText(string);
        }
        setTitle(getContext().getString(R.string.volume_limiter_toast_title));
        setButton(-1, getContext().getString(R.string.volume_limiter_button_settings), this);
        setButton(-2, getContext().getString(android.R.string.cancel), this);
    }

    @Override // com.android.systemui.volume.view.warnings.VolumeWarningDialog, android.app.Dialog
    public final Window getWindow() {
        return super.getWindow();
    }

    @Override // com.android.systemui.volume.view.warnings.VolumeWarningDialog
    public final void initDialog() {
        super.initDialog();
        setCancelable(true);
        setOnDismissListener(this);
        setOnKeyListener(this);
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeObserver
    public final void onChanged(Object obj) {
        VolumePanelState volumePanelState = (VolumePanelState) obj;
        int i = WhenMappings.$EnumSwitchMapping$0[volumePanelState.getStateType().ordinal()];
        if (i == 1) {
            if (volumePanelState.getCoverType() != 8) {
                dismiss();
                return;
            }
            return;
        }
        if (i == 2 || i == 3 || i == 4) {
            dismiss();
            return;
        }
        if (i != 5) {
            return;
        }
        dismiss();
        if (volumePanelState.isCoverClosed()) {
            ToastWrapper toastWrapper = this.toastWrapper;
            if (toastWrapper == null) {
                toastWrapper = null;
            }
            Context context = getContext();
            String string = getContext().getString(R.string.volume_limiter_open_cover_toast);
            toastWrapper.getClass();
            Toast.makeText(context, string, 0).show();
        }
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        if (i == -1) {
            AbstractC3626xb6ef341d.m227m(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_VOLUME_LIMITER_DIALOG_SETTINS_CLICKED), true, this.storeInteractor, false);
        } else {
            AbstractC3626xb6ef341d.m227m(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_VOLUME_LIMITER_DIALOG_CANCEL_CLICKED), true, this.storeInteractor, false);
        }
    }

    @Override // android.content.DialogInterface.OnDismissListener
    public final void onDismiss(DialogInterface dialogInterface) {
        this.storeInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_DISMISS_VOLUME_LIMITER_DIALOG).build(), false);
        this.storeInteractor.dispose();
    }

    @Override // android.content.DialogInterface.OnKeyListener
    public final boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
        if (i == 25) {
            AbstractC3626xb6ef341d.m227m(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_VOLUME_LIMITER_DIALOG_VOLUME_DOWN), true, this.storeInteractor, false);
        }
        return false;
    }
}
