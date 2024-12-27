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
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
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
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.core.graphics.drawable.IconCompat;
import com.android.settingslib.bluetooth.BluetoothLeBroadcastMetadataExt;
import com.android.settingslib.bluetooth.LocalBluetoothLeBroadcast;
import com.android.settingslib.bluetooth.LocalBluetoothLeBroadcastAssistant;
import com.android.settingslib.bluetooth.LocalBluetoothLeBroadcastMetadata;
import com.android.settingslib.flags.Flags;
import com.android.settingslib.qrcode.QrCodeGenerator;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastSender;
import com.android.systemui.media.dialog.MediaOutputController;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.google.zxing.WriterException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import kotlin.Pair;
import kotlin.UInt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.Charsets;
import kotlin.text.StringsKt__StringsJVMKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class MediaOutputBroadcastDialog extends MediaOutputBaseDialog {
    static final int BROADCAST_CODE_MAX_LENGTH = 16;
    static final int BROADCAST_CODE_MIN_LENGTH = 4;
    static final int BROADCAST_NAME_MAX_LENGTH = 254;
    AlertDialog mAlertDialog;
    public final AnonymousClass3 mBroadcastAssistantCallback;
    public TextView mBroadcastCode;
    public final AnonymousClass1 mBroadcastCodeTextWatcher;
    public TextView mBroadcastErrorMessage;
    public TextView mBroadcastName;
    public final AnonymousClass2 mBroadcastNameTextWatcher;
    public ImageView mBroadcastQrCodeView;
    public String mCurrentBroadcastCode;
    public String mCurrentBroadcastName;
    public boolean mIsLeBroadcastAssistantCallbackRegistered;
    public Boolean mIsPasswordHide;
    public boolean mIsStopbyUpdateBroadcastCode;
    public int mRetryCount;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.media.dialog.MediaOutputBroadcastDialog$3, reason: invalid class name */
    public final class AnonymousClass3 implements BluetoothLeBroadcastAssistant.Callback {
        public AnonymousClass3() {
        }

        public final void onReceiveStateChanged(BluetoothDevice bluetoothDevice, int i, BluetoothLeBroadcastReceiveState bluetoothLeBroadcastReceiveState) {
            Log.d("MediaOutputBroadcastDialog", "Assistant-onReceiveStateChanged:");
        }

        public final void onSearchStartFailed(int i) {
            ListPopupWindow$$ExternalSyntheticOutline0.m(i, "Assistant-onSearchStartFailed: ", "MediaOutputBroadcastDialog");
        }

        public final void onSearchStarted(int i) {
            ListPopupWindow$$ExternalSyntheticOutline0.m(i, "Assistant-onSearchStarted: ", "MediaOutputBroadcastDialog");
        }

        public final void onSearchStopFailed(int i) {
            ListPopupWindow$$ExternalSyntheticOutline0.m(i, "Assistant-onSearchStopFailed: ", "MediaOutputBroadcastDialog");
        }

        public final void onSearchStopped(int i) {
            ListPopupWindow$$ExternalSyntheticOutline0.m(i, "Assistant-onSearchStopped: ", "MediaOutputBroadcastDialog");
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

    /* JADX WARN: Type inference failed for: r2v3, types: [com.android.systemui.media.dialog.MediaOutputBroadcastDialog$1] */
    /* JADX WARN: Type inference failed for: r2v4, types: [com.android.systemui.media.dialog.MediaOutputBroadcastDialog$2] */
    public MediaOutputBroadcastDialog(Context context, boolean z, BroadcastSender broadcastSender, MediaOutputController mediaOutputController) {
        super(context, broadcastSender, mediaOutputController, true);
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
        this.mBroadcastAssistantCallback = new AnonymousClass3();
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
        MediaMetadata metadata;
        MediaOutputController mediaOutputController = this.mMediaOutputController;
        MediaController mediaController = mediaOutputController.mMediaController;
        return (mediaController == null || (metadata = mediaController.getMetadata()) == null) ? mediaOutputController.mContext.getText(R.string.controls_media_title) : metadata.getDescription().getTitle();
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
        MediaOutputController mediaOutputController = this.mMediaOutputController;
        LocalBluetoothLeBroadcast localBluetoothLeBroadcast = mediaOutputController.mLocalBluetoothManager.mProfileManager.mLeAudioBroadcast;
        if (localBluetoothLeBroadcast != null) {
            localBluetoothLeBroadcast.startBroadcast(mediaOutputController.getAppSourceName());
        } else {
            Log.d("MediaOutputController", "The broadcast profile is null");
            handleLeBroadcastStartFailed();
        }
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
    public final void isBroadcastSupported() {
        Flags.legacyLeAudioSharing();
    }

    public final void launchBroadcastUpdatedDialog(String str, final boolean z) {
        View inflate = LayoutInflater.from(((MediaOutputBaseDialog) this).mContext).inflate(R.layout.media_output_broadcast_update_dialog, (ViewGroup) null);
        final EditText editText = (EditText) inflate.requireViewById(R.id.broadcast_edit_text);
        editText.setText(str);
        editText.addTextChangedListener(z ? this.mBroadcastCodeTextWatcher : this.mBroadcastNameTextWatcher);
        this.mBroadcastErrorMessage = (TextView) inflate.requireViewById(R.id.broadcast_error_message);
        AlertDialog create = new AlertDialog.Builder(((MediaOutputBaseDialog) this).mContext).setTitle(z ? R.string.media_output_broadcast_code : R.string.media_output_broadcast_name).setView(inflate).setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null).setPositiveButton(R.string.media_output_broadcast_dialog_save, new DialogInterface.OnClickListener() { // from class: com.android.systemui.media.dialog.MediaOutputBroadcastDialog$$ExternalSyntheticLambda4
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
        SystemUIDialog.registerDismissListener(this.mAlertDialog);
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
                int i2 = i;
                MediaOutputBroadcastDialog mediaOutputBroadcastDialog = this.f$0;
                switch (i2) {
                    case 0:
                        MediaOutputController mediaOutputController = mediaOutputBroadcastDialog.mMediaOutputController;
                        MediaOutputController.BroadcastNotifyDialog broadcastNotifyDialog = MediaOutputController.BroadcastNotifyDialog.ACTION_BROADCAST_INFO_ICON;
                        mediaOutputController.getClass();
                        AlertDialog.Builder builder = new AlertDialog.Builder(mediaOutputController.mContext);
                        int ordinal = broadcastNotifyDialog.ordinal();
                        if (ordinal == 0) {
                            builder.setTitle(R.string.media_output_first_broadcast_title);
                            builder.setMessage(R.string.media_output_first_notify_broadcast_message);
                            builder.setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null);
                            builder.setPositiveButton(R.string.media_output_broadcast, (DialogInterface.OnClickListener) null);
                        } else if (ordinal == 1) {
                            builder.setTitle(R.string.media_output_broadcast);
                            builder.setMessage(R.string.media_output_broadcasting_message);
                            builder.setPositiveButton(android.R.string.ok, (DialogInterface.OnClickListener) null);
                        }
                        AlertDialog create = builder.create();
                        create.getWindow().setType(2009);
                        SystemUIDialog.setShowForAllUsers(create);
                        SystemUIDialog.registerDismissListener(create);
                        create.show();
                        break;
                    case 1:
                        mediaOutputBroadcastDialog.launchBroadcastUpdatedDialog(mediaOutputBroadcastDialog.mBroadcastName.getText().toString(), false);
                        break;
                    case 2:
                        mediaOutputBroadcastDialog.mBroadcastCode.setTransformationMethod(mediaOutputBroadcastDialog.mIsPasswordHide.booleanValue() ? HideReturnsTransformationMethod.getInstance() : PasswordTransformationMethod.getInstance());
                        mediaOutputBroadcastDialog.mIsPasswordHide = Boolean.valueOf(!mediaOutputBroadcastDialog.mIsPasswordHide.booleanValue());
                        break;
                    default:
                        mediaOutputBroadcastDialog.launchBroadcastUpdatedDialog(mediaOutputBroadcastDialog.mBroadcastCode.getText().toString(), true);
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
                int i22 = i2;
                MediaOutputBroadcastDialog mediaOutputBroadcastDialog = this.f$0;
                switch (i22) {
                    case 0:
                        MediaOutputController mediaOutputController = mediaOutputBroadcastDialog.mMediaOutputController;
                        MediaOutputController.BroadcastNotifyDialog broadcastNotifyDialog = MediaOutputController.BroadcastNotifyDialog.ACTION_BROADCAST_INFO_ICON;
                        mediaOutputController.getClass();
                        AlertDialog.Builder builder = new AlertDialog.Builder(mediaOutputController.mContext);
                        int ordinal = broadcastNotifyDialog.ordinal();
                        if (ordinal == 0) {
                            builder.setTitle(R.string.media_output_first_broadcast_title);
                            builder.setMessage(R.string.media_output_first_notify_broadcast_message);
                            builder.setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null);
                            builder.setPositiveButton(R.string.media_output_broadcast, (DialogInterface.OnClickListener) null);
                        } else if (ordinal == 1) {
                            builder.setTitle(R.string.media_output_broadcast);
                            builder.setMessage(R.string.media_output_broadcasting_message);
                            builder.setPositiveButton(android.R.string.ok, (DialogInterface.OnClickListener) null);
                        }
                        AlertDialog create = builder.create();
                        create.getWindow().setType(2009);
                        SystemUIDialog.setShowForAllUsers(create);
                        SystemUIDialog.registerDismissListener(create);
                        create.show();
                        break;
                    case 1:
                        mediaOutputBroadcastDialog.launchBroadcastUpdatedDialog(mediaOutputBroadcastDialog.mBroadcastName.getText().toString(), false);
                        break;
                    case 2:
                        mediaOutputBroadcastDialog.mBroadcastCode.setTransformationMethod(mediaOutputBroadcastDialog.mIsPasswordHide.booleanValue() ? HideReturnsTransformationMethod.getInstance() : PasswordTransformationMethod.getInstance());
                        mediaOutputBroadcastDialog.mIsPasswordHide = Boolean.valueOf(!mediaOutputBroadcastDialog.mIsPasswordHide.booleanValue());
                        break;
                    default:
                        mediaOutputBroadcastDialog.launchBroadcastUpdatedDialog(mediaOutputBroadcastDialog.mBroadcastCode.getText().toString(), true);
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
                int i22 = i3;
                MediaOutputBroadcastDialog mediaOutputBroadcastDialog = this.f$0;
                switch (i22) {
                    case 0:
                        MediaOutputController mediaOutputController = mediaOutputBroadcastDialog.mMediaOutputController;
                        MediaOutputController.BroadcastNotifyDialog broadcastNotifyDialog = MediaOutputController.BroadcastNotifyDialog.ACTION_BROADCAST_INFO_ICON;
                        mediaOutputController.getClass();
                        AlertDialog.Builder builder = new AlertDialog.Builder(mediaOutputController.mContext);
                        int ordinal = broadcastNotifyDialog.ordinal();
                        if (ordinal == 0) {
                            builder.setTitle(R.string.media_output_first_broadcast_title);
                            builder.setMessage(R.string.media_output_first_notify_broadcast_message);
                            builder.setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null);
                            builder.setPositiveButton(R.string.media_output_broadcast, (DialogInterface.OnClickListener) null);
                        } else if (ordinal == 1) {
                            builder.setTitle(R.string.media_output_broadcast);
                            builder.setMessage(R.string.media_output_broadcasting_message);
                            builder.setPositiveButton(android.R.string.ok, (DialogInterface.OnClickListener) null);
                        }
                        AlertDialog create = builder.create();
                        create.getWindow().setType(2009);
                        SystemUIDialog.setShowForAllUsers(create);
                        SystemUIDialog.registerDismissListener(create);
                        create.show();
                        break;
                    case 1:
                        mediaOutputBroadcastDialog.launchBroadcastUpdatedDialog(mediaOutputBroadcastDialog.mBroadcastName.getText().toString(), false);
                        break;
                    case 2:
                        mediaOutputBroadcastDialog.mBroadcastCode.setTransformationMethod(mediaOutputBroadcastDialog.mIsPasswordHide.booleanValue() ? HideReturnsTransformationMethod.getInstance() : PasswordTransformationMethod.getInstance());
                        mediaOutputBroadcastDialog.mIsPasswordHide = Boolean.valueOf(!mediaOutputBroadcastDialog.mIsPasswordHide.booleanValue());
                        break;
                    default:
                        mediaOutputBroadcastDialog.launchBroadcastUpdatedDialog(mediaOutputBroadcastDialog.mBroadcastCode.getText().toString(), true);
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
                int i22 = i4;
                MediaOutputBroadcastDialog mediaOutputBroadcastDialog = this.f$0;
                switch (i22) {
                    case 0:
                        MediaOutputController mediaOutputController = mediaOutputBroadcastDialog.mMediaOutputController;
                        MediaOutputController.BroadcastNotifyDialog broadcastNotifyDialog = MediaOutputController.BroadcastNotifyDialog.ACTION_BROADCAST_INFO_ICON;
                        mediaOutputController.getClass();
                        AlertDialog.Builder builder = new AlertDialog.Builder(mediaOutputController.mContext);
                        int ordinal = broadcastNotifyDialog.ordinal();
                        if (ordinal == 0) {
                            builder.setTitle(R.string.media_output_first_broadcast_title);
                            builder.setMessage(R.string.media_output_first_notify_broadcast_message);
                            builder.setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null);
                            builder.setPositiveButton(R.string.media_output_broadcast, (DialogInterface.OnClickListener) null);
                        } else if (ordinal == 1) {
                            builder.setTitle(R.string.media_output_broadcast);
                            builder.setMessage(R.string.media_output_broadcasting_message);
                            builder.setPositiveButton(android.R.string.ok, (DialogInterface.OnClickListener) null);
                        }
                        AlertDialog create = builder.create();
                        create.getWindow().setType(2009);
                        SystemUIDialog.setShowForAllUsers(create);
                        SystemUIDialog.registerDismissListener(create);
                        create.show();
                        break;
                    case 1:
                        mediaOutputBroadcastDialog.launchBroadcastUpdatedDialog(mediaOutputBroadcastDialog.mBroadcastName.getText().toString(), false);
                        break;
                    case 2:
                        mediaOutputBroadcastDialog.mBroadcastCode.setTransformationMethod(mediaOutputBroadcastDialog.mIsPasswordHide.booleanValue() ? HideReturnsTransformationMethod.getInstance() : PasswordTransformationMethod.getInstance());
                        mediaOutputBroadcastDialog.mIsPasswordHide = Boolean.valueOf(!mediaOutputBroadcastDialog.mIsPasswordHide.booleanValue());
                        break;
                    default:
                        mediaOutputBroadcastDialog.launchBroadcastUpdatedDialog(mediaOutputBroadcastDialog.mBroadcastCode.getText().toString(), true);
                        break;
                }
            }
        });
        refreshUi();
    }

    @Override // com.android.systemui.media.dialog.MediaOutputBaseDialog
    public final void onStopButtonClick() {
        LocalBluetoothLeBroadcast localBluetoothLeBroadcast = this.mMediaOutputController.mLocalBluetoothManager.mProfileManager.mLeAudioBroadcast;
        if (localBluetoothLeBroadcast == null) {
            Log.d("MediaOutputController", "The broadcast profile is null");
        } else {
            int i = localBluetoothLeBroadcast.mBroadcastId;
            if (localBluetoothLeBroadcast.mServiceBroadcast == null) {
                Log.d("LocalBluetoothLeBroadcast", "The BluetoothLeBroadcast is null when stopping the broadcast.");
            } else {
                Log.d("LocalBluetoothLeBroadcast", "stopBroadcast()");
                localBluetoothLeBroadcast.mServiceBroadcast.stopBroadcast(i);
            }
        }
        dismiss();
    }

    public final void refreshUi() {
        LocalBluetoothLeBroadcastMetadata localBluetoothLeBroadcastMetadata;
        BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata;
        BluetoothLeAudioContentMetadata publicBroadcastMetadata;
        byte[] rawMetadata;
        LocalBluetoothLeBroadcast localBluetoothLeBroadcast = this.mMediaOutputController.mLocalBluetoothManager.mProfileManager.mLeAudioBroadcast;
        String str = "";
        if (localBluetoothLeBroadcast == null) {
            Log.d("MediaOutputController", "getLocalBroadcastMetadataQrCodeString: LE Audio Broadcast is null");
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
                if (bluetoothLeBroadcastMetadata.getBroadcastName() == null) {
                    throw new IllegalArgumentException("Broadcast name is mandatory for QR code".toString());
                }
                String broadcastName = bluetoothLeBroadcastMetadata.getBroadcastName();
                arrayList.add(new Pair("BN", Base64.encodeToString(broadcastName != null ? broadcastName.getBytes(Charsets.UTF_8) : null, 2)));
                arrayList.add(new Pair("AT", String.valueOf(bluetoothLeBroadcastMetadata.getSourceAddressType())));
                arrayList.add(new Pair("AD", StringsKt__StringsJVMKt.replace$default(bluetoothLeBroadcastMetadata.getSourceDevice().getAddress(), ":", "")));
                int i = StringCompanionObject.$r8$clinit;
                arrayList.add(new Pair("BI", String.format("%X", Arrays.copyOf(new Object[]{Long.valueOf(bluetoothLeBroadcastMetadata.getBroadcastId())}, 1))));
                if (bluetoothLeBroadcastMetadata.getBroadcastCode() != null) {
                    arrayList.add(new Pair("BC", Base64.encodeToString(bluetoothLeBroadcastMetadata.getBroadcastCode(), 2)));
                }
                if (bluetoothLeBroadcastMetadata.getPublicBroadcastMetadata() != null && ((publicBroadcastMetadata = bluetoothLeBroadcastMetadata.getPublicBroadcastMetadata()) == null || (rawMetadata = publicBroadcastMetadata.getRawMetadata()) == null || rawMetadata.length != 0)) {
                    BluetoothLeAudioContentMetadata publicBroadcastMetadata2 = bluetoothLeBroadcastMetadata.getPublicBroadcastMetadata();
                    arrayList.add(new Pair("MD", Base64.encodeToString(publicBroadcastMetadata2 != null ? publicBroadcastMetadata2.getRawMetadata() : null, 2)));
                }
                if ((bluetoothLeBroadcastMetadata.getAudioConfigQuality() & 1) != 0) {
                    arrayList.add(new Pair("SQ", "1"));
                }
                if ((bluetoothLeBroadcastMetadata.getAudioConfigQuality() & 2) != 0) {
                    arrayList.add(new Pair("HQ", "1"));
                }
                arrayList.add(new Pair("AS", String.format("%X", Arrays.copyOf(new Object[]{Long.valueOf(bluetoothLeBroadcastMetadata.getSourceAdvertisingSid())}, 1))));
                arrayList.add(new Pair("PI", String.format("%X", Arrays.copyOf(new Object[]{Long.valueOf(bluetoothLeBroadcastMetadata.getPaSyncInterval())}, 1))));
                arrayList.add(new Pair("NS", String.format("%X", Arrays.copyOf(new Object[]{Long.valueOf(bluetoothLeBroadcastMetadata.getSubgroups().size())}, 1))));
                for (BluetoothLeBroadcastSubgroup bluetoothLeBroadcastSubgroup : bluetoothLeBroadcastMetadata.getSubgroups()) {
                    List<BluetoothLeBroadcastChannel> channels = bluetoothLeBroadcastSubgroup.getChannels();
                    BluetoothLeBroadcastMetadataExt.INSTANCE.getClass();
                    int i2 = 0;
                    int i3 = 0;
                    for (BluetoothLeBroadcastChannel bluetoothLeBroadcastChannel : channels) {
                        if (bluetoothLeBroadcastChannel.getChannelIndex() > 0) {
                            i3++;
                            int i4 = UInt.$r8$clinit;
                            if (bluetoothLeBroadcastChannel.isSelected()) {
                                i2 |= 1 << (bluetoothLeBroadcastChannel.getChannelIndex() - 1);
                            }
                        }
                    }
                    Pair pair = i2 == 0 ? new Pair(UInt.m2530boximpl(-1), UInt.m2530boximpl(i3)) : new Pair(UInt.m2530boximpl(i2), UInt.m2530boximpl(i3));
                    int i5 = ((UInt) pair.component1()).data;
                    int i6 = ((UInt) pair.component2()).data;
                    int i7 = StringCompanionObject.$r8$clinit;
                    arrayList.add(new Pair("BS", String.format("%X", Arrays.copyOf(new Object[]{Long.valueOf(i5 & 4294967295L)}, 1))));
                    if (Integer.compareUnsigned(i6, 0) > 0) {
                        arrayList.add(new Pair("NB", String.format("%X", Arrays.copyOf(new Object[]{Long.valueOf(i6 & 4294967295L)}, 1))));
                    }
                    if (bluetoothLeBroadcastSubgroup.getContentMetadata().getRawMetadata().length != 0) {
                        arrayList.add(new Pair("SM", Base64.encodeToString(bluetoothLeBroadcastSubgroup.getContentMetadata().getRawMetadata(), 2)));
                    }
                }
                ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList, 10));
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    Pair pair2 = (Pair) it.next();
                    arrayList2.add(pair2.getFirst() + ":" + pair2.getSecond());
                }
                String m = ContentInViewNode$Request$$ExternalSyntheticOutline0.m("BLUETOOTH:UUID:184F;", CollectionsKt___CollectionsKt.joinToString$default(arrayList2, ";", null, null, null, 62), ";;");
                MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("Generated QR string : ", m, "BtLeBroadcastMetadataExt");
                if (m != null) {
                    str = m;
                }
            }
        }
        if (!str.isEmpty()) {
            try {
                this.mBroadcastQrCodeView.setImageBitmap(QrCodeGenerator.encodeQrCode(getContext().getResources().getDimensionPixelSize(R.dimen.media_output_qrcode_size), str));
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
        boolean z;
        super.start();
        if (!this.mIsLeBroadcastAssistantCallbackRegistered) {
            this.mIsLeBroadcastAssistantCallbackRegistered = true;
            MediaOutputController mediaOutputController = this.mMediaOutputController;
            Executor executor = this.mExecutor;
            AnonymousClass3 anonymousClass3 = this.mBroadcastAssistantCallback;
            LocalBluetoothLeBroadcastAssistant localBluetoothLeBroadcastAssistant = mediaOutputController.mLocalBluetoothManager.mProfileManager.mLeAudioBroadcastAssistant;
            if (localBluetoothLeBroadcastAssistant == null) {
                Log.d("MediaOutputController", "registerLeBroadcastAssistantServiceCallback: The broadcast assistant profile is null");
            } else {
                Log.d("MediaOutputController", "Register LE broadcast assistant callback");
                localBluetoothLeBroadcastAssistant.registerServiceCallBack(executor, anonymousClass3);
            }
        }
        LocalBluetoothLeBroadcast localBluetoothLeBroadcast = this.mMediaOutputController.mLocalBluetoothManager.mProfileManager.mLeAudioBroadcast;
        List<BluetoothDevice> list = null;
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
        LocalBluetoothLeBroadcastAssistant localBluetoothLeBroadcastAssistant2 = this.mMediaOutputController.mLocalBluetoothManager.mProfileManager.mLeAudioBroadcastAssistant;
        if (localBluetoothLeBroadcastAssistant2 == null) {
            Log.d("MediaOutputController", "getConnectedBroadcastSinkDevices: The broadcast assistant profile is null");
        } else {
            BluetoothLeBroadcastAssistant bluetoothLeBroadcastAssistant = localBluetoothLeBroadcastAssistant2.mService;
            list = bluetoothLeBroadcastAssistant == null ? new ArrayList(0) : bluetoothLeBroadcastAssistant.getDevicesMatchingConnectionStates(new int[]{2, 1, 3});
        }
        for (BluetoothDevice bluetoothDevice : list) {
            Log.d("MediaOutputBroadcastDialog", "The broadcastMetadata broadcastId: " + latestBluetoothLeBroadcastMetadata.getBroadcastId() + ", the device: " + bluetoothDevice.getAnonymizedAddress());
            LocalBluetoothLeBroadcastAssistant localBluetoothLeBroadcastAssistant3 = this.mMediaOutputController.mLocalBluetoothManager.mProfileManager.mLeAudioBroadcastAssistant;
            if (localBluetoothLeBroadcastAssistant3 == null) {
                Log.d("MediaOutputController", "isThereAnyBroadcastSourceIntoSinkDevice: The broadcast assistant profile is null");
                isEmpty = false;
            } else {
                Log.d("LocalBluetoothLeBroadcastAssistant", "getAllSources()");
                BluetoothLeBroadcastAssistant bluetoothLeBroadcastAssistant2 = localBluetoothLeBroadcastAssistant3.mService;
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
            LocalBluetoothLeBroadcastAssistant localBluetoothLeBroadcastAssistant4 = this.mMediaOutputController.mLocalBluetoothManager.mProfileManager.mLeAudioBroadcastAssistant;
            if (localBluetoothLeBroadcastAssistant4 == null) {
                Log.d("MediaOutputController", "addSourceIntoSinkDeviceWithBluetoothLeAssistant: The broadcast assistant profile is null");
                z = false;
            } else {
                BluetoothLeBroadcastAssistant bluetoothLeBroadcastAssistant3 = localBluetoothLeBroadcastAssistant4.mService;
                if (bluetoothLeBroadcastAssistant3 == null) {
                    Log.d("LocalBluetoothLeBroadcastAssistant", "The BluetoothLeBroadcastAssistant is null");
                } else {
                    bluetoothLeBroadcastAssistant3.addSource(bluetoothDevice, latestBluetoothLeBroadcastMetadata, false);
                }
                z = true;
            }
            if (!z) {
                Log.e("MediaOutputBroadcastDialog", "Error: Source add failed");
            }
        }
    }

    @Override // com.android.systemui.media.dialog.MediaOutputBaseDialog, com.android.systemui.statusbar.phone.SystemUIDialog
    public final void stop() {
        super.stop();
        if (this.mIsLeBroadcastAssistantCallbackRegistered) {
            this.mIsLeBroadcastAssistantCallbackRegistered = false;
            MediaOutputController mediaOutputController = this.mMediaOutputController;
            AnonymousClass3 anonymousClass3 = this.mBroadcastAssistantCallback;
            LocalBluetoothLeBroadcastAssistant localBluetoothLeBroadcastAssistant = mediaOutputController.mLocalBluetoothManager.mProfileManager.mLeAudioBroadcastAssistant;
            if (localBluetoothLeBroadcastAssistant == null) {
                Log.d("MediaOutputController", "unregisterLeBroadcastAssistantServiceCallback: The broadcast assistant profile is null");
                return;
            }
            Log.d("MediaOutputController", "Unregister LE broadcast assistant callback");
            ((ConcurrentHashMap) localBluetoothLeBroadcastAssistant.mCachedCallbackExecutorMap).remove(anonymousClass3);
            BluetoothLeBroadcastAssistant bluetoothLeBroadcastAssistant = localBluetoothLeBroadcastAssistant.mService;
            if (bluetoothLeBroadcastAssistant == null) {
                Log.d("LocalBluetoothLeBroadcastAssistant", "unregisterServiceCallBack failed, the BluetoothLeBroadcastAssistant is null.");
                return;
            }
            try {
                bluetoothLeBroadcastAssistant.unregisterCallback(anonymousClass3);
            } catch (IllegalArgumentException e) {
                Log.w("LocalBluetoothLeBroadcastAssistant", "unregisterServiceCallBack failed. " + e.getMessage());
            }
        }
    }

    public void updateBroadcastInfo(boolean z, String str) {
        Button button = this.mAlertDialog.getButton(-1);
        if (button != null) {
            button.setEnabled(false);
        }
        if (z) {
            this.mIsStopbyUpdateBroadcastCode = true;
            this.mMediaOutputController.setBroadcastCode(str);
            LocalBluetoothLeBroadcast localBluetoothLeBroadcast = this.mMediaOutputController.mLocalBluetoothManager.mProfileManager.mLeAudioBroadcast;
            if (localBluetoothLeBroadcast == null) {
                Log.d("MediaOutputController", "The broadcast profile is null");
                handleLeBroadcastStopFailed();
                return;
            }
            int i = localBluetoothLeBroadcast.mBroadcastId;
            if (localBluetoothLeBroadcast.mServiceBroadcast == null) {
                Log.d("LocalBluetoothLeBroadcast", "The BluetoothLeBroadcast is null when stopping the broadcast.");
                return;
            } else {
                Log.d("LocalBluetoothLeBroadcast", "stopBroadcast()");
                localBluetoothLeBroadcast.mServiceBroadcast.stopBroadcast(i);
                return;
            }
        }
        LocalBluetoothLeBroadcast localBluetoothLeBroadcast2 = this.mMediaOutputController.mLocalBluetoothManager.mProfileManager.mLeAudioBroadcast;
        if (localBluetoothLeBroadcast2 == null) {
            Log.d("MediaOutputController", "setBroadcastName: LE Audio Broadcast is null");
        } else {
            localBluetoothLeBroadcast2.setProgramInfo(str, true);
        }
        MediaOutputController mediaOutputController = this.mMediaOutputController;
        LocalBluetoothLeBroadcast localBluetoothLeBroadcast3 = mediaOutputController.mLocalBluetoothManager.mProfileManager.mLeAudioBroadcast;
        if (localBluetoothLeBroadcast3 == null) {
            Log.d("MediaOutputController", "The broadcast profile is null");
            handleLeBroadcastUpdateFailed();
            return;
        }
        String appSourceName = mediaOutputController.getAppSourceName();
        if (localBluetoothLeBroadcast3.mServiceBroadcast == null) {
            Log.d("LocalBluetoothLeBroadcast", "The BluetoothLeBroadcast is null when updating the broadcast.");
            return;
        }
        String str2 = localBluetoothLeBroadcast3.mProgramInfo;
        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("updateBroadcast: language = null ,programInfo = ", str2, "LocalBluetoothLeBroadcast");
        localBluetoothLeBroadcast3.mNewAppSourceName = appSourceName;
        localBluetoothLeBroadcast3.mServiceBroadcast.updateBroadcast(localBluetoothLeBroadcast3.mBroadcastId, localBluetoothLeBroadcast3.mBuilder.setProgramInfo(str2).build());
    }
}
