package com.android.systemui.media.dialog;

import android.app.AlertDialog;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothLeAudioContentMetadata;
import android.bluetooth.BluetoothLeBroadcastAssistant;
import android.bluetooth.BluetoothLeBroadcastChannel;
import android.bluetooth.BluetoothLeBroadcastMetadata;
import android.bluetooth.BluetoothLeBroadcastReceiveState;
import android.bluetooth.BluetoothLeBroadcastSubgroup;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaMetadata;
import android.media.session.MediaController;
import android.os.Bundle;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.core.graphics.PathParser$$ExternalSyntheticOutline0;
import androidx.core.graphics.drawable.IconCompat;
import androidx.picker.adapter.AbsAdapter$1$$ExternalSyntheticOutline0;
import com.android.settingslib.bluetooth.BluetoothLeBroadcastMetadataExt;
import com.android.settingslib.bluetooth.LocalBluetoothLeBroadcast;
import com.android.settingslib.bluetooth.LocalBluetoothLeBroadcastAssistant;
import com.android.settingslib.bluetooth.LocalBluetoothLeBroadcastMetadata;
import com.android.settingslib.media.BluetoothMediaDevice;
import com.android.settingslib.media.MediaDevice;
import com.android.settingslib.qrcode.QrCodeGenerator;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastSender;
import com.android.systemui.media.dialog.MediaOutputController;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.google.zxing.WriterException;
import com.sec.ims.settings.ImsProfile;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import kotlin.Pair;
import kotlin.UInt;
import kotlin.text.Charsets;
import kotlin.text.StringsKt__StringsJVMKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MediaOutputBroadcastDialog extends MediaOutputBaseDialog {
    static final int BROADCAST_CODE_MAX_LENGTH = 16;
    static final int BROADCAST_CODE_MIN_LENGTH = 4;
    static final int BROADCAST_NAME_MAX_LENGTH = 254;
    AlertDialog mAlertDialog;
    public final C18233 mBroadcastAssistantCallback;
    public TextView mBroadcastCode;
    public final C18211 mBroadcastCodeTextWatcher;
    public TextView mBroadcastErrorMessage;
    public TextView mBroadcastName;
    public final C18222 mBroadcastNameTextWatcher;
    public ImageView mBroadcastQrCodeView;
    public String mCurrentBroadcastCode;
    public String mCurrentBroadcastName;
    public boolean mIsLeBroadcastAssistantCallbackRegistered;
    public Boolean mIsPasswordHide;
    public boolean mIsStopbyUpdateBroadcastCode;
    public int mRetryCount;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.media.dialog.MediaOutputBroadcastDialog$3 */
    public final class C18233 implements BluetoothLeBroadcastAssistant.Callback {
        public C18233() {
        }

        public final void onReceiveStateChanged(BluetoothDevice bluetoothDevice, int i, BluetoothLeBroadcastReceiveState bluetoothLeBroadcastReceiveState) {
            Log.d("MediaOutputBroadcastDialog", "Assistant-onReceiveStateChanged:");
        }

        public final void onSearchStartFailed(int i) {
            ListPopupWindow$$ExternalSyntheticOutline0.m10m("Assistant-onSearchStartFailed: ", i, "MediaOutputBroadcastDialog");
        }

        public final void onSearchStarted(int i) {
            ListPopupWindow$$ExternalSyntheticOutline0.m10m("Assistant-onSearchStarted: ", i, "MediaOutputBroadcastDialog");
        }

        public final void onSearchStopFailed(int i) {
            ListPopupWindow$$ExternalSyntheticOutline0.m10m("Assistant-onSearchStopFailed: ", i, "MediaOutputBroadcastDialog");
        }

        public final void onSearchStopped(int i) {
            ListPopupWindow$$ExternalSyntheticOutline0.m10m("Assistant-onSearchStopped: ", i, "MediaOutputBroadcastDialog");
        }

        public final void onSourceAddFailed(BluetoothDevice bluetoothDevice, BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata, int i) {
            Log.d("MediaOutputBroadcastDialog", "Assistant-onSourceAddFailed: Device: " + bluetoothDevice);
        }

        public final void onSourceAdded(BluetoothDevice bluetoothDevice, int i, int i2) {
            Log.d("MediaOutputBroadcastDialog", "Assistant-onSourceAdded: Device: " + bluetoothDevice + ", sourceId: " + i);
            MediaOutputBroadcastDialog.this.mMainThreadHandler.post(new Runnable() { // from class: com.android.systemui.media.dialog.MediaOutputBroadcastDialog$3$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    MediaOutputBroadcastDialog mediaOutputBroadcastDialog = MediaOutputBroadcastDialog.this;
                    int i3 = MediaOutputBroadcastDialog.BROADCAST_CODE_MAX_LENGTH;
                    mediaOutputBroadcastDialog.refreshUi();
                }
            });
        }

        public final void onSourceFound(BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata) {
            Log.d("MediaOutputBroadcastDialog", "Assistant-onSourceFound:");
        }

        public final void onSourceModified(BluetoothDevice bluetoothDevice, int i, int i2) {
            Log.d("MediaOutputBroadcastDialog", "Assistant-onSourceModified:");
        }

        public final void onSourceModifyFailed(BluetoothDevice bluetoothDevice, int i, int i2) {
            Log.d("MediaOutputBroadcastDialog", "Assistant-onSourceModifyFailed:");
        }

        public final void onSourceRemoveFailed(BluetoothDevice bluetoothDevice, int i, int i2) {
            Log.d("MediaOutputBroadcastDialog", "Assistant-onSourceRemoveFailed:");
        }

        public final void onSourceRemoved(BluetoothDevice bluetoothDevice, int i, int i2) {
            Log.d("MediaOutputBroadcastDialog", "Assistant-onSourceRemoved:");
        }
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.media.dialog.MediaOutputBroadcastDialog$1] */
    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.media.dialog.MediaOutputBroadcastDialog$2] */
    public MediaOutputBroadcastDialog(Context context, boolean z, BroadcastSender broadcastSender, MediaOutputController mediaOutputController) {
        super(context, broadcastSender, mediaOutputController);
        this.mIsPasswordHide = Boolean.TRUE;
        this.mRetryCount = 0;
        this.mIsStopbyUpdateBroadcastCode = false;
        this.mBroadcastCodeTextWatcher = new TextWatcher() { // from class: com.android.systemui.media.dialog.MediaOutputBroadcastDialog.1
            @Override // android.text.TextWatcher
            public final void afterTextChanged(Editable editable) {
                MediaOutputBroadcastDialog mediaOutputBroadcastDialog = MediaOutputBroadcastDialog.this;
                if (mediaOutputBroadcastDialog.mAlertDialog == null || mediaOutputBroadcastDialog.mBroadcastErrorMessage == null) {
                    return;
                }
                boolean z2 = editable.length() > 0 && editable.length() < 4;
                boolean z3 = editable.length() > 16;
                boolean z4 = z2 || z3;
                if (z2) {
                    MediaOutputBroadcastDialog.this.mBroadcastErrorMessage.setText(R.string.media_output_broadcast_code_hint_no_less_than_min);
                } else if (z3) {
                    MediaOutputBroadcastDialog mediaOutputBroadcastDialog2 = MediaOutputBroadcastDialog.this;
                    mediaOutputBroadcastDialog2.mBroadcastErrorMessage.setText(((MediaOutputBaseDialog) mediaOutputBroadcastDialog2).mContext.getResources().getString(R.string.media_output_broadcast_edit_hint_no_more_than_max, 16));
                }
                MediaOutputBroadcastDialog.this.mBroadcastErrorMessage.setVisibility(z4 ? 0 : 4);
                Button button = MediaOutputBroadcastDialog.this.mAlertDialog.getButton(-1);
                if (button != null) {
                    button.setEnabled(!z4);
                }
            }

            @Override // android.text.TextWatcher
            public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }
        };
        this.mBroadcastNameTextWatcher = new TextWatcher() { // from class: com.android.systemui.media.dialog.MediaOutputBroadcastDialog.2
            @Override // android.text.TextWatcher
            public final void afterTextChanged(Editable editable) {
                MediaOutputBroadcastDialog mediaOutputBroadcastDialog = MediaOutputBroadcastDialog.this;
                if (mediaOutputBroadcastDialog.mAlertDialog == null || mediaOutputBroadcastDialog.mBroadcastErrorMessage == null) {
                    return;
                }
                boolean z2 = editable.length() > 254;
                boolean z3 = z2 || editable.length() == 0;
                if (z2) {
                    MediaOutputBroadcastDialog mediaOutputBroadcastDialog2 = MediaOutputBroadcastDialog.this;
                    mediaOutputBroadcastDialog2.mBroadcastErrorMessage.setText(((MediaOutputBaseDialog) mediaOutputBroadcastDialog2).mContext.getResources().getString(R.string.media_output_broadcast_edit_hint_no_more_than_max, 254));
                }
                MediaOutputBroadcastDialog.this.mBroadcastErrorMessage.setVisibility(z2 ? 0 : 4);
                Button button = MediaOutputBroadcastDialog.this.mAlertDialog.getButton(-1);
                if (button != null) {
                    button.setEnabled(!z3);
                }
            }

            @Override // android.text.TextWatcher
            public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }
        };
        this.mBroadcastAssistantCallback = new C18233();
        this.mAdapter = new MediaOutputAdapter(this.mMediaOutputController);
        if (z) {
            return;
        }
        getWindow().setType(2038);
    }

    @Override // com.android.systemui.media.dialog.MediaOutputBaseDialog
    public final IconCompat getAppSourceIcon() {
        return this.mMediaOutputController.getNotificationSmallIcon();
    }

    public final String getBroadcastMetadataInfo(int i) {
        if (i == 0) {
            LocalBluetoothLeBroadcast localBluetoothLeBroadcast = this.mMediaOutputController.mLocalBluetoothManager.mProfileManager.mLeAudioBroadcast;
            if (localBluetoothLeBroadcast != null) {
                return localBluetoothLeBroadcast.mProgramInfo;
            }
            Log.d("MediaOutputController", "getBroadcastName: LE Audio Broadcast is null");
            return "";
        }
        if (i != 1) {
            return "";
        }
        LocalBluetoothLeBroadcast localBluetoothLeBroadcast2 = this.mMediaOutputController.mLocalBluetoothManager.mProfileManager.mLeAudioBroadcast;
        if (localBluetoothLeBroadcast2 != null) {
            return new String(localBluetoothLeBroadcast2.mBroadcastCode, StandardCharsets.UTF_8);
        }
        Log.d("MediaOutputController", "getBroadcastCode: LE Audio Broadcast is null");
        return "";
    }

    @Override // com.android.systemui.media.dialog.MediaOutputBaseDialog
    public final IconCompat getHeaderIcon() {
        return this.mMediaOutputController.getHeaderIcon();
    }

    @Override // com.android.systemui.media.dialog.MediaOutputBaseDialog
    public final int getHeaderIconSize() {
        return ((MediaOutputBaseDialog) this).mContext.getResources().getDimensionPixelSize(R.dimen.media_output_dialog_header_album_icon_size);
    }

    @Override // com.android.systemui.media.dialog.MediaOutputBaseDialog
    public final CharSequence getHeaderSubtitle() {
        MediaMetadata metadata;
        MediaController mediaController = this.mMediaOutputController.mMediaController;
        if (mediaController == null || (metadata = mediaController.getMetadata()) == null) {
            return null;
        }
        return metadata.getDescription().getSubtitle();
    }

    @Override // com.android.systemui.media.dialog.MediaOutputBaseDialog
    public final CharSequence getHeaderText() {
        return this.mMediaOutputController.getHeaderTitle();
    }

    public int getRetryCount() {
        return this.mRetryCount;
    }

    @Override // com.android.systemui.media.dialog.MediaOutputBaseDialog
    public final int getStopButtonVisibility() {
        return 0;
    }

    @Override // com.android.systemui.media.dialog.MediaOutputBaseDialog
    public final void handleLeBroadcastMetadataChanged() {
        Log.d("MediaOutputBroadcastDialog", "handleLeBroadcastMetadataChanged:");
        refreshUi();
    }

    @Override // com.android.systemui.media.dialog.MediaOutputBaseDialog
    public final void handleLeBroadcastStartFailed() {
        this.mMediaOutputController.setBroadcastCode(this.mCurrentBroadcastCode);
        this.mRetryCount++;
        handleUpdateFailedUi();
    }

    @Override // com.android.systemui.media.dialog.MediaOutputBaseDialog
    public final void handleLeBroadcastStarted() {
        this.mRetryCount = 0;
        AlertDialog alertDialog = this.mAlertDialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
        refreshUi();
    }

    @Override // com.android.systemui.media.dialog.MediaOutputBaseDialog
    public final void handleLeBroadcastStopFailed() {
        this.mMediaOutputController.setBroadcastCode(this.mCurrentBroadcastCode);
        this.mRetryCount++;
        handleUpdateFailedUi();
    }

    @Override // com.android.systemui.media.dialog.MediaOutputBaseDialog
    public final void handleLeBroadcastStopped() {
        if (!this.mIsStopbyUpdateBroadcastCode) {
            dismiss();
            return;
        }
        this.mIsStopbyUpdateBroadcastCode = false;
        this.mRetryCount = 0;
        if (this.mMediaOutputController.startBluetoothLeBroadcast()) {
            return;
        }
        handleLeBroadcastStartFailed();
    }

    @Override // com.android.systemui.media.dialog.MediaOutputBaseDialog
    public final void handleLeBroadcastUpdateFailed() {
        MediaOutputController mediaOutputController = this.mMediaOutputController;
        String str = this.mCurrentBroadcastName;
        LocalBluetoothLeBroadcast localBluetoothLeBroadcast = mediaOutputController.mLocalBluetoothManager.mProfileManager.mLeAudioBroadcast;
        if (localBluetoothLeBroadcast == null) {
            Log.d("MediaOutputController", "setBroadcastName: LE Audio Broadcast is null");
        } else {
            localBluetoothLeBroadcast.setProgramInfo(str, true);
        }
        this.mRetryCount++;
        handleUpdateFailedUi();
    }

    @Override // com.android.systemui.media.dialog.MediaOutputBaseDialog
    public final void handleLeBroadcastUpdated() {
        this.mRetryCount = 0;
        AlertDialog alertDialog = this.mAlertDialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
        refreshUi();
    }

    public final void handleUpdateFailedUi() {
        int i;
        boolean z;
        AlertDialog alertDialog = this.mAlertDialog;
        if (alertDialog == null) {
            Log.d("MediaOutputBroadcastDialog", "handleUpdateFailedUi: mAlertDialog is null");
            return;
        }
        if (this.mRetryCount < 3) {
            i = R.string.media_output_broadcast_update_error;
            z = true;
        } else {
            this.mRetryCount = 0;
            i = R.string.media_output_broadcast_last_update_error;
            z = false;
        }
        Button button = alertDialog.getButton(-1);
        if (button != null && z) {
            button.setEnabled(true);
        }
        TextView textView = this.mBroadcastErrorMessage;
        if (textView != null) {
            textView.setVisibility(0);
            this.mBroadcastErrorMessage.setText(i);
        }
    }

    @Override // com.android.systemui.media.dialog.MediaOutputBaseDialog
    public final boolean isBroadcastSupported() {
        return (this.mMediaOutputController.mLocalBluetoothManager.mProfileManager.mLeAudioBroadcast != null) && (this.mMediaOutputController.getCurrentConnectedMediaDevice() != null ? this.mMediaOutputController.getCurrentConnectedMediaDevice().isBLEDevice() : false);
    }

    public final void launchBroadcastUpdatedDialog(String str, final boolean z) {
        View inflate = LayoutInflater.from(((MediaOutputBaseDialog) this).mContext).inflate(R.layout.media_output_broadcast_update_dialog, (ViewGroup) null);
        final EditText editText = (EditText) inflate.requireViewById(R.id.broadcast_edit_text);
        editText.setText(str);
        editText.addTextChangedListener(z ? this.mBroadcastCodeTextWatcher : this.mBroadcastNameTextWatcher);
        this.mBroadcastErrorMessage = (TextView) inflate.requireViewById(R.id.broadcast_error_message);
        AlertDialog create = new AlertDialog.Builder(((MediaOutputBaseDialog) this).mContext).setTitle(z ? R.string.media_output_broadcast_code : R.string.media_output_broadcast_name).setView(inflate).setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null).setPositiveButton(R.string.media_output_broadcast_dialog_save, new DialogInterface.OnClickListener() { // from class: com.android.systemui.media.dialog.MediaOutputBroadcastDialog$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                MediaOutputBroadcastDialog mediaOutputBroadcastDialog = MediaOutputBroadcastDialog.this;
                boolean z2 = z;
                EditText editText2 = editText;
                mediaOutputBroadcastDialog.getClass();
                mediaOutputBroadcastDialog.updateBroadcastInfo(z2, editText2.getText().toString());
            }
        }).create();
        this.mAlertDialog = create;
        create.getWindow().setType(2009);
        SystemUIDialog.setShowForAllUsers(this.mAlertDialog);
        SystemUIDialog.registerDismissListener(this.mAlertDialog, null);
        this.mAlertDialog.show();
    }

    @Override // com.android.systemui.media.dialog.MediaOutputBaseDialog, com.android.systemui.statusbar.phone.SystemUIDialog, android.app.AlertDialog, android.app.Dialog
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ((ViewStub) this.mDialogView.requireViewById(R.id.broadcast_qrcode)).inflate();
        this.mBroadcastQrCodeView = (ImageView) this.mDialogView.requireViewById(R.id.qrcode_view);
        final int i = 0;
        ((ImageView) this.mDialogView.requireViewById(R.id.broadcast_info)).setOnClickListener(new View.OnClickListener(this) { // from class: com.android.systemui.media.dialog.MediaOutputBroadcastDialog$$ExternalSyntheticLambda0
            public final /* synthetic */ MediaOutputBroadcastDialog f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                switch (i) {
                    case 0:
                        this.f$0.mMediaOutputController.launchLeBroadcastNotifyDialog(MediaOutputController.BroadcastNotifyDialog.ACTION_BROADCAST_INFO_ICON, null);
                        break;
                    case 1:
                        MediaOutputBroadcastDialog mediaOutputBroadcastDialog = this.f$0;
                        mediaOutputBroadcastDialog.launchBroadcastUpdatedDialog(mediaOutputBroadcastDialog.mBroadcastName.getText().toString(), false);
                        break;
                    case 2:
                        MediaOutputBroadcastDialog mediaOutputBroadcastDialog2 = this.f$0;
                        mediaOutputBroadcastDialog2.mBroadcastCode.setTransformationMethod(mediaOutputBroadcastDialog2.mIsPasswordHide.booleanValue() ? HideReturnsTransformationMethod.getInstance() : PasswordTransformationMethod.getInstance());
                        mediaOutputBroadcastDialog2.mIsPasswordHide = Boolean.valueOf(!mediaOutputBroadcastDialog2.mIsPasswordHide.booleanValue());
                        break;
                    default:
                        MediaOutputBroadcastDialog mediaOutputBroadcastDialog3 = this.f$0;
                        mediaOutputBroadcastDialog3.launchBroadcastUpdatedDialog(mediaOutputBroadcastDialog3.mBroadcastCode.getText().toString(), true);
                        break;
                }
            }
        });
        this.mBroadcastName = (TextView) this.mDialogView.requireViewById(R.id.broadcast_name_summary);
        final int i2 = 1;
        ((ImageView) this.mDialogView.requireViewById(R.id.broadcast_name_edit)).setOnClickListener(new View.OnClickListener(this) { // from class: com.android.systemui.media.dialog.MediaOutputBroadcastDialog$$ExternalSyntheticLambda0
            public final /* synthetic */ MediaOutputBroadcastDialog f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                switch (i2) {
                    case 0:
                        this.f$0.mMediaOutputController.launchLeBroadcastNotifyDialog(MediaOutputController.BroadcastNotifyDialog.ACTION_BROADCAST_INFO_ICON, null);
                        break;
                    case 1:
                        MediaOutputBroadcastDialog mediaOutputBroadcastDialog = this.f$0;
                        mediaOutputBroadcastDialog.launchBroadcastUpdatedDialog(mediaOutputBroadcastDialog.mBroadcastName.getText().toString(), false);
                        break;
                    case 2:
                        MediaOutputBroadcastDialog mediaOutputBroadcastDialog2 = this.f$0;
                        mediaOutputBroadcastDialog2.mBroadcastCode.setTransformationMethod(mediaOutputBroadcastDialog2.mIsPasswordHide.booleanValue() ? HideReturnsTransformationMethod.getInstance() : PasswordTransformationMethod.getInstance());
                        mediaOutputBroadcastDialog2.mIsPasswordHide = Boolean.valueOf(!mediaOutputBroadcastDialog2.mIsPasswordHide.booleanValue());
                        break;
                    default:
                        MediaOutputBroadcastDialog mediaOutputBroadcastDialog3 = this.f$0;
                        mediaOutputBroadcastDialog3.launchBroadcastUpdatedDialog(mediaOutputBroadcastDialog3.mBroadcastCode.getText().toString(), true);
                        break;
                }
            }
        });
        TextView textView = (TextView) this.mDialogView.requireViewById(R.id.broadcast_code_summary);
        this.mBroadcastCode = textView;
        textView.setTransformationMethod(PasswordTransformationMethod.getInstance());
        final int i3 = 2;
        ((ImageView) this.mDialogView.requireViewById(R.id.broadcast_code_eye)).setOnClickListener(new View.OnClickListener(this) { // from class: com.android.systemui.media.dialog.MediaOutputBroadcastDialog$$ExternalSyntheticLambda0
            public final /* synthetic */ MediaOutputBroadcastDialog f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                switch (i3) {
                    case 0:
                        this.f$0.mMediaOutputController.launchLeBroadcastNotifyDialog(MediaOutputController.BroadcastNotifyDialog.ACTION_BROADCAST_INFO_ICON, null);
                        break;
                    case 1:
                        MediaOutputBroadcastDialog mediaOutputBroadcastDialog = this.f$0;
                        mediaOutputBroadcastDialog.launchBroadcastUpdatedDialog(mediaOutputBroadcastDialog.mBroadcastName.getText().toString(), false);
                        break;
                    case 2:
                        MediaOutputBroadcastDialog mediaOutputBroadcastDialog2 = this.f$0;
                        mediaOutputBroadcastDialog2.mBroadcastCode.setTransformationMethod(mediaOutputBroadcastDialog2.mIsPasswordHide.booleanValue() ? HideReturnsTransformationMethod.getInstance() : PasswordTransformationMethod.getInstance());
                        mediaOutputBroadcastDialog2.mIsPasswordHide = Boolean.valueOf(!mediaOutputBroadcastDialog2.mIsPasswordHide.booleanValue());
                        break;
                    default:
                        MediaOutputBroadcastDialog mediaOutputBroadcastDialog3 = this.f$0;
                        mediaOutputBroadcastDialog3.launchBroadcastUpdatedDialog(mediaOutputBroadcastDialog3.mBroadcastCode.getText().toString(), true);
                        break;
                }
            }
        });
        final int i4 = 3;
        ((ImageView) this.mDialogView.requireViewById(R.id.broadcast_code_edit)).setOnClickListener(new View.OnClickListener(this) { // from class: com.android.systemui.media.dialog.MediaOutputBroadcastDialog$$ExternalSyntheticLambda0
            public final /* synthetic */ MediaOutputBroadcastDialog f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                switch (i4) {
                    case 0:
                        this.f$0.mMediaOutputController.launchLeBroadcastNotifyDialog(MediaOutputController.BroadcastNotifyDialog.ACTION_BROADCAST_INFO_ICON, null);
                        break;
                    case 1:
                        MediaOutputBroadcastDialog mediaOutputBroadcastDialog = this.f$0;
                        mediaOutputBroadcastDialog.launchBroadcastUpdatedDialog(mediaOutputBroadcastDialog.mBroadcastName.getText().toString(), false);
                        break;
                    case 2:
                        MediaOutputBroadcastDialog mediaOutputBroadcastDialog2 = this.f$0;
                        mediaOutputBroadcastDialog2.mBroadcastCode.setTransformationMethod(mediaOutputBroadcastDialog2.mIsPasswordHide.booleanValue() ? HideReturnsTransformationMethod.getInstance() : PasswordTransformationMethod.getInstance());
                        mediaOutputBroadcastDialog2.mIsPasswordHide = Boolean.valueOf(!mediaOutputBroadcastDialog2.mIsPasswordHide.booleanValue());
                        break;
                    default:
                        MediaOutputBroadcastDialog mediaOutputBroadcastDialog3 = this.f$0;
                        mediaOutputBroadcastDialog3.launchBroadcastUpdatedDialog(mediaOutputBroadcastDialog3.mBroadcastCode.getText().toString(), true);
                        break;
                }
            }
        });
        refreshUi();
    }

    @Override // com.android.systemui.media.dialog.MediaOutputBaseDialog
    public final void onStopButtonClick() {
        this.mMediaOutputController.stopBluetoothLeBroadcast();
        dismiss();
    }

    /* JADX WARN: Code restructure failed: missing block: B:71:0x01d9, code lost:
    
        if (r0 == null) goto L50;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void refreshUi() {
        LocalBluetoothLeBroadcastMetadata localBluetoothLeBroadcastMetadata;
        BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata;
        String m29m;
        LocalBluetoothLeBroadcast localBluetoothLeBroadcast = this.mMediaOutputController.mLocalBluetoothManager.mProfileManager.mLeAudioBroadcast;
        if (localBluetoothLeBroadcast == null) {
            Log.d("MediaOutputController", "getBroadcastMetadata: LE Audio Broadcast is null");
        } else {
            BluetoothLeBroadcastMetadata latestBluetoothLeBroadcastMetadata = localBluetoothLeBroadcast.getLatestBluetoothLeBroadcastMetadata();
            if (latestBluetoothLeBroadcastMetadata == null) {
                Log.d("LocalBluetoothLeBroadcast", "The BluetoothLeBroadcastMetadata is null.");
                localBluetoothLeBroadcastMetadata = null;
            } else {
                localBluetoothLeBroadcastMetadata = new LocalBluetoothLeBroadcastMetadata(latestBluetoothLeBroadcastMetadata);
            }
            if (localBluetoothLeBroadcastMetadata != null && (bluetoothLeBroadcastMetadata = localBluetoothLeBroadcastMetadata.metadata) != null) {
                BluetoothLeBroadcastMetadataExt.INSTANCE.getClass();
                ArrayList arrayList = new ArrayList();
                arrayList.add(new Pair("R", "65536"));
                arrayList.add(new Pair("T", String.valueOf(bluetoothLeBroadcastMetadata.getSourceAddressType())));
                arrayList.add(new Pair(ImsProfile.TIMER_NAME_D, StringsKt__StringsJVMKt.replace$default(bluetoothLeBroadcastMetadata.getSourceDevice().getAddress(), ":", "-")));
                arrayList.add(new Pair("AS", String.valueOf(bluetoothLeBroadcastMetadata.getSourceAdvertisingSid())));
                arrayList.add(new Pair(ImsProfile.TIMER_NAME_B, String.valueOf(bluetoothLeBroadcastMetadata.getBroadcastId())));
                if (bluetoothLeBroadcastMetadata.getBroadcastName() != null) {
                    String broadcastName = bluetoothLeBroadcastMetadata.getBroadcastName();
                    arrayList.add(new Pair("BN", Base64.encodeToString(broadcastName != null ? broadcastName.getBytes(Charsets.UTF_8) : null, 2)));
                }
                if (bluetoothLeBroadcastMetadata.getPublicBroadcastMetadata() != null) {
                    BluetoothLeAudioContentMetadata publicBroadcastMetadata = bluetoothLeBroadcastMetadata.getPublicBroadcastMetadata();
                    arrayList.add(new Pair("PM", Base64.encodeToString(publicBroadcastMetadata != null ? publicBroadcastMetadata.getRawMetadata() : null, 2)));
                }
                arrayList.add(new Pair("SI", String.valueOf(bluetoothLeBroadcastMetadata.getPaSyncInterval())));
                if (bluetoothLeBroadcastMetadata.getBroadcastCode() != null) {
                    arrayList.add(new Pair(ImsProfile.TIMER_NAME_C, Base64.encodeToString(bluetoothLeBroadcastMetadata.getBroadcastCode(), 2)));
                }
                for (BluetoothLeBroadcastSubgroup bluetoothLeBroadcastSubgroup : bluetoothLeBroadcastMetadata.getSubgroups()) {
                    BluetoothLeBroadcastMetadataExt.INSTANCE.getClass();
                    ArrayList arrayList2 = new ArrayList();
                    int i = 0;
                    for (BluetoothLeBroadcastChannel bluetoothLeBroadcastChannel : bluetoothLeBroadcastSubgroup.getChannels()) {
                        if (bluetoothLeBroadcastChannel.isSelected() && bluetoothLeBroadcastChannel.getChannelIndex() > 0) {
                            int channelIndex = 1 << (bluetoothLeBroadcastChannel.getChannelIndex() - 1);
                            int i2 = UInt.$r8$clinit;
                            i |= channelIndex;
                        }
                    }
                    if (i == 0) {
                        i = -1;
                    }
                    arrayList2.add(new Pair("BS", Integer.toUnsignedString(i)));
                    int i3 = 0;
                    for (BluetoothLeBroadcastChannel bluetoothLeBroadcastChannel2 : bluetoothLeBroadcastSubgroup.getChannels()) {
                        if (bluetoothLeBroadcastChannel2.getChannelIndex() > 0) {
                            int channelIndex2 = 1 << (bluetoothLeBroadcastChannel2.getChannelIndex() - 1);
                            int i4 = UInt.$r8$clinit;
                            i3 |= channelIndex2;
                        }
                    }
                    arrayList2.add(new Pair("BM", Integer.toUnsignedString(i3)));
                    arrayList2.add(new Pair("AC", Base64.encodeToString(bluetoothLeBroadcastSubgroup.getContentMetadata().getRawMetadata(), 2)));
                    arrayList.add(new Pair("SG", BluetoothLeBroadcastMetadataExt.toQrCodeString(",", arrayList2)));
                }
                arrayList.add(new Pair("VN", "U"));
                m29m = PathParser$$ExternalSyntheticOutline0.m29m("BT:", BluetoothLeBroadcastMetadataExt.toQrCodeString(";", arrayList), ";;");
                AbstractC0000x2c234b15.m3m("Generated QR string : ", m29m, "BtLeBroadcastMetadataExt");
            }
        }
        m29m = "";
        if (!m29m.isEmpty()) {
            try {
                this.mBroadcastQrCodeView.setImageBitmap(QrCodeGenerator.encodeQrCode(getContext().getResources().getDimensionPixelSize(R.dimen.media_output_qrcode_size), m29m));
            } catch (WriterException e) {
                Log.e("MediaOutputBroadcastDialog", "Error generatirng QR code bitmap " + e);
            }
        }
        this.mCurrentBroadcastName = getBroadcastMetadataInfo(0);
        this.mCurrentBroadcastCode = getBroadcastMetadataInfo(1);
        this.mBroadcastName.setText(this.mCurrentBroadcastName);
        this.mBroadcastCode.setText(this.mCurrentBroadcastCode);
        refresh(false);
    }

    @Override // com.android.systemui.media.dialog.MediaOutputBaseDialog, com.android.systemui.statusbar.phone.SystemUIDialog
    public final void start() {
        BluetoothLeBroadcastMetadata latestBluetoothLeBroadcastMetadata;
        List allSources;
        boolean isEmpty;
        super.start();
        boolean z = true;
        if (!this.mIsLeBroadcastAssistantCallbackRegistered) {
            this.mIsLeBroadcastAssistantCallbackRegistered = true;
            MediaOutputController mediaOutputController = this.mMediaOutputController;
            Executor executor = this.mExecutor;
            C18233 c18233 = this.mBroadcastAssistantCallback;
            LocalBluetoothLeBroadcastAssistant localBluetoothLeBroadcastAssistant = mediaOutputController.mLocalBluetoothManager.mProfileManager.mLeAudioBroadcastAssistant;
            if (localBluetoothLeBroadcastAssistant == null) {
                Log.d("MediaOutputController", "The broadcast assistant profile is null");
            } else {
                Log.d("MediaOutputController", "Register LE broadcast assistant callback");
                BluetoothLeBroadcastAssistant bluetoothLeBroadcastAssistant = localBluetoothLeBroadcastAssistant.mService;
                if (bluetoothLeBroadcastAssistant == null) {
                    Log.d("LocalBluetoothLeBroadcastAssistant", "The BluetoothLeBroadcast is null.");
                } else {
                    try {
                        bluetoothLeBroadcastAssistant.registerCallback(executor, c18233);
                    } catch (Exception e) {
                        AbsAdapter$1$$ExternalSyntheticOutline0.m39m("registerServiceCallBack : Failed to register callback. ", e, "LocalBluetoothLeBroadcastAssistant");
                    }
                }
            }
        }
        LocalBluetoothLeBroadcast localBluetoothLeBroadcast = this.mMediaOutputController.mLocalBluetoothManager.mProfileManager.mLeAudioBroadcast;
        if (localBluetoothLeBroadcast == null) {
            Log.d("MediaOutputController", "getBroadcastMetadata: LE Audio Broadcast is null");
            latestBluetoothLeBroadcastMetadata = null;
        } else {
            latestBluetoothLeBroadcastMetadata = localBluetoothLeBroadcast.getLatestBluetoothLeBroadcastMetadata();
        }
        if (latestBluetoothLeBroadcastMetadata == null) {
            Log.e("MediaOutputBroadcastDialog", "Error: There is no broadcastMetadata.");
            return;
        }
        MediaDevice currentConnectedMediaDevice = this.mMediaOutputController.getCurrentConnectedMediaDevice();
        if (currentConnectedMediaDevice == null || !(currentConnectedMediaDevice instanceof BluetoothMediaDevice) || !currentConnectedMediaDevice.isBLEDevice()) {
            Log.e("MediaOutputBroadcastDialog", "Error: There is no active BT LE device.");
            return;
        }
        BluetoothDevice bluetoothDevice = ((BluetoothMediaDevice) currentConnectedMediaDevice).mCachedDevice.mDevice;
        Log.d("MediaOutputBroadcastDialog", "The broadcastMetadata broadcastId: " + latestBluetoothLeBroadcastMetadata.getBroadcastId() + ", the device: " + bluetoothDevice.getAnonymizedAddress());
        LocalBluetoothLeBroadcastAssistant localBluetoothLeBroadcastAssistant2 = this.mMediaOutputController.mLocalBluetoothManager.mProfileManager.mLeAudioBroadcastAssistant;
        if (localBluetoothLeBroadcastAssistant2 == null) {
            Log.d("MediaOutputController", "The broadcast assistant profile is null");
            isEmpty = false;
        } else {
            Log.d("LocalBluetoothLeBroadcastAssistant", "getAllSources()");
            BluetoothLeBroadcastAssistant bluetoothLeBroadcastAssistant2 = localBluetoothLeBroadcastAssistant2.mService;
            if (bluetoothLeBroadcastAssistant2 == null) {
                Log.d("LocalBluetoothLeBroadcastAssistant", "The BluetoothLeBroadcastAssistant is null");
                allSources = new ArrayList();
            } else {
                allSources = bluetoothLeBroadcastAssistant2.getAllSources(bluetoothDevice);
            }
            Log.d("MediaOutputController", "isThereAnyBroadcastSourceIntoSinkDevice: List size: " + allSources.size());
            isEmpty = allSources.isEmpty() ^ true;
        }
        if (isEmpty) {
            Log.d("MediaOutputBroadcastDialog", "The sink device has the broadcast source now.");
            return;
        }
        LocalBluetoothLeBroadcastAssistant localBluetoothLeBroadcastAssistant3 = this.mMediaOutputController.mLocalBluetoothManager.mProfileManager.mLeAudioBroadcastAssistant;
        if (localBluetoothLeBroadcastAssistant3 == null) {
            Log.d("MediaOutputController", "The broadcast assistant profile is null");
            z = false;
        } else {
            BluetoothLeBroadcastAssistant bluetoothLeBroadcastAssistant3 = localBluetoothLeBroadcastAssistant3.mService;
            if (bluetoothLeBroadcastAssistant3 == null) {
                Log.d("LocalBluetoothLeBroadcastAssistant", "The BluetoothLeBroadcastAssistant is null");
            } else {
                bluetoothLeBroadcastAssistant3.addSource(bluetoothDevice, latestBluetoothLeBroadcastMetadata, true);
            }
        }
        if (z) {
            return;
        }
        Log.e("MediaOutputBroadcastDialog", "Error: Source add failed");
    }

    @Override // com.android.systemui.media.dialog.MediaOutputBaseDialog, com.android.systemui.statusbar.phone.SystemUIDialog
    public final void stop() {
        super.stop();
        if (this.mIsLeBroadcastAssistantCallbackRegistered) {
            this.mIsLeBroadcastAssistantCallbackRegistered = false;
            MediaOutputController mediaOutputController = this.mMediaOutputController;
            C18233 c18233 = this.mBroadcastAssistantCallback;
            LocalBluetoothLeBroadcastAssistant localBluetoothLeBroadcastAssistant = mediaOutputController.mLocalBluetoothManager.mProfileManager.mLeAudioBroadcastAssistant;
            if (localBluetoothLeBroadcastAssistant == null) {
                Log.d("MediaOutputController", "The broadcast assistant profile is null");
                return;
            }
            Log.d("MediaOutputController", "Unregister LE broadcast assistant callback");
            BluetoothLeBroadcastAssistant bluetoothLeBroadcastAssistant = localBluetoothLeBroadcastAssistant.mService;
            if (bluetoothLeBroadcastAssistant == null) {
                Log.d("LocalBluetoothLeBroadcastAssistant", "The BluetoothLeBroadcast is null.");
                return;
            }
            try {
                bluetoothLeBroadcastAssistant.unregisterCallback(c18233);
            } catch (Exception e) {
                AbsAdapter$1$$ExternalSyntheticOutline0.m39m("unregisterServiceCallBack : Failed to unregister callback. ", e, "LocalBluetoothLeBroadcastAssistant");
            }
        }
    }

    public void updateBroadcastInfo(boolean z, String str) {
        Button button = this.mAlertDialog.getButton(-1);
        boolean z2 = false;
        if (button != null) {
            button.setEnabled(false);
        }
        if (z) {
            this.mIsStopbyUpdateBroadcastCode = true;
            this.mMediaOutputController.setBroadcastCode(str);
            if (this.mMediaOutputController.stopBluetoothLeBroadcast()) {
                return;
            }
            handleLeBroadcastStopFailed();
            return;
        }
        LocalBluetoothLeBroadcast localBluetoothLeBroadcast = this.mMediaOutputController.mLocalBluetoothManager.mProfileManager.mLeAudioBroadcast;
        if (localBluetoothLeBroadcast == null) {
            Log.d("MediaOutputController", "setBroadcastName: LE Audio Broadcast is null");
        } else {
            localBluetoothLeBroadcast.setProgramInfo(str, true);
        }
        MediaOutputController mediaOutputController = this.mMediaOutputController;
        LocalBluetoothLeBroadcast localBluetoothLeBroadcast2 = mediaOutputController.mLocalBluetoothManager.mProfileManager.mLeAudioBroadcast;
        if (localBluetoothLeBroadcast2 == null) {
            Log.d("MediaOutputController", "The broadcast profile is null");
        } else {
            String appSourceName = mediaOutputController.getAppSourceName();
            if (localBluetoothLeBroadcast2.mService == null) {
                Log.d("LocalBluetoothLeBroadcast", "The BluetoothLeBroadcast is null when updating the broadcast.");
            } else {
                String str2 = localBluetoothLeBroadcast2.mProgramInfo;
                AbstractC0000x2c234b15.m3m("updateBroadcast: language = null ,programInfo = ", str2, "LocalBluetoothLeBroadcast");
                localBluetoothLeBroadcast2.mNewAppSourceName = appSourceName;
                localBluetoothLeBroadcast2.mService.updateBroadcast(localBluetoothLeBroadcast2.mBroadcastId, localBluetoothLeBroadcast2.mBuilder.setProgramInfo(str2).build());
            }
            z2 = true;
        }
        if (z2) {
            return;
        }
        handleLeBroadcastUpdateFailed();
    }

    @Override // com.android.systemui.media.dialog.MediaOutputBaseDialog
    public final void getHeaderIconRes() {
    }
}
