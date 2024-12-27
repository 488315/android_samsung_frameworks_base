package com.android.systemui.volume.view.warnings;

import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertController;
import com.android.systemui.R;
import com.android.systemui.volume.store.StoreInteractor;
import com.android.systemui.volume.util.ToastWrapper;
import com.android.systemui.volume.view.expand.VolumePanelExpandView$adjustTouchEventForOutsideTouch$1$$ExternalSyntheticOutline0;
import com.samsung.systemui.splugins.volume.VolumeObserver;
import com.samsung.systemui.splugins.volume.VolumePanelAction;
import com.samsung.systemui.splugins.volume.VolumePanelState;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class VolumeLimiterWarningDialog extends VolumeWarningDialog implements DialogInterface.OnDismissListener, DialogInterface.OnClickListener, VolumeObserver, DialogInterface.OnKeyListener {
    public final StoreInteractor storeInteractor;
    public ToastWrapper toastWrapper;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    @Override // com.android.systemui.volume.view.warnings.VolumeWarningDialog
    public final void initDialog() {
        super.initDialog();
        setCancelable(true);
        setOnDismissListener(this);
        setOnKeyListener(this);
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeObserver
    public final void onChanged(Object obj) {
        ToastWrapper toastWrapper;
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
            ToastWrapper toastWrapper2 = this.toastWrapper;
            toastWrapper = toastWrapper2 != null ? toastWrapper2 : null;
            Context context = getContext();
            String string = getContext().getString(R.string.volume_limiter_open_cover_toast);
            toastWrapper.getClass();
            Toast.makeText(context, string, 0).show();
            return;
        }
        if (volumePanelState.isShowingSubDisplayVolumePanel() && volumePanelState.isLockscreen()) {
            ToastWrapper toastWrapper3 = this.toastWrapper;
            toastWrapper = toastWrapper3 != null ? toastWrapper3 : null;
            Context context2 = getContext();
            String string2 = getContext().getString(R.string.sub_display_volume_warning_toast_text);
            toastWrapper.getClass();
            Toast.makeText(context2, string2, 0).show();
        }
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        if (i == -1) {
            VolumePanelExpandView$adjustTouchEventForOutsideTouch$1$$ExternalSyntheticOutline0.m(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_VOLUME_LIMITER_DIALOG_SETTINS_CLICKED), true, this.storeInteractor, false);
        } else {
            VolumePanelExpandView$adjustTouchEventForOutsideTouch$1$$ExternalSyntheticOutline0.m(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_VOLUME_LIMITER_DIALOG_CANCEL_CLICKED), true, this.storeInteractor, false);
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
            VolumePanelExpandView$adjustTouchEventForOutsideTouch$1$$ExternalSyntheticOutline0.m(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_VOLUME_LIMITER_DIALOG_VOLUME_DOWN), true, this.storeInteractor, false);
        }
        return false;
    }
}
