package com.android.systemui.bluetooth;

import android.app.Dialog;
import android.bluetooth.BluetoothLeBroadcast;
import android.bluetooth.BluetoothLeBroadcastMetadata;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import com.android.internal.logging.UiEventLogger;
import com.android.settingslib.bluetooth.LocalBluetoothLeBroadcast;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastSender;
import com.android.systemui.media.controls.util.MediaDataUtils;
import com.android.systemui.media.dialog.MediaOutputDialogManager;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class BroadcastDialogDelegate implements SystemUIDialog.Delegate {
    public static final boolean DEBUG = Log.isLoggable("BroadcastDialog", 3);
    public final Executor mBgExecutor;
    public final BroadcastSender mBroadcastSender;
    public final Context mContext;
    public final String mCurrentBroadcastApp;
    public final LocalBluetoothManager mLocalBluetoothManager;
    public final MediaOutputDialogManager mMediaOutputDialogManager;
    public final String mOutputPackageName;
    public boolean mShouldLaunchLeBroadcastDialog;
    public Button mSwitchBroadcast;
    public final SystemUIDialog.Factory mSystemUIDialogFactory;
    public final UiEventLogger mUiEventLogger;
    public final Handler mMainThreadHandler = new Handler(Looper.getMainLooper());
    public final Set mDialogs = new HashSet();
    public final AnonymousClass1 mBroadcastCallback = new AnonymousClass1();

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public enum BroadcastDialogEvent implements UiEventLogger.UiEventEnum {
        BROADCAST_DIALOG_SHOW(1062);

        private final int mId;

        BroadcastDialogEvent(int i) {
            this.mId = i;
        }

        public final int getId() {
            return this.mId;
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Factory {
        BroadcastDialogDelegate create(String str, String str2);
    }

    public BroadcastDialogDelegate(Context context, MediaOutputDialogManager mediaOutputDialogManager, LocalBluetoothManager localBluetoothManager, UiEventLogger uiEventLogger, Executor executor, BroadcastSender broadcastSender, SystemUIDialog.Factory factory, String str, String str2) {
        this.mContext = context;
        this.mMediaOutputDialogManager = mediaOutputDialogManager;
        this.mLocalBluetoothManager = localBluetoothManager;
        this.mSystemUIDialogFactory = factory;
        this.mCurrentBroadcastApp = str;
        this.mOutputPackageName = str2;
        this.mUiEventLogger = uiEventLogger;
        this.mBgExecutor = executor;
        this.mBroadcastSender = broadcastSender;
        if (DEBUG) {
            Log.d("BroadcastDialog", "Init BroadcastDialog");
        }
    }

    @Override // com.android.systemui.statusbar.phone.SystemUIDialog.Delegate
    public final SystemUIDialog createDialog() {
        SystemUIDialog.Factory factory = this.mSystemUIDialogFactory;
        return factory.create(this, factory.mContext);
    }

    public void handleLeBroadcastStopped() {
        this.mShouldLaunchLeBroadcastDialog = false;
        LocalBluetoothLeBroadcast localBluetoothLeBroadcast = this.mLocalBluetoothManager.mProfileManager.mLeAudioBroadcast;
        if (localBluetoothLeBroadcast != null) {
            Context context = this.mContext;
            localBluetoothLeBroadcast.startBroadcast(MediaDataUtils.getAppLabel(context, this.mOutputPackageName, context.getString(R.string.bt_le_audio_broadcast_dialog_unknown_name)));
        } else {
            Log.d("BroadcastDialog", "The broadcast profile is null");
            this.mSwitchBroadcast.setText(R.string.media_output_broadcast_start_failed);
            this.mSwitchBroadcast.setEnabled(false);
            refreshSwitchBroadcastButton();
        }
    }

    @Override // com.android.systemui.statusbar.phone.DialogDelegate
    public final void onCreate(Dialog dialog, Bundle bundle) {
        final SystemUIDialog systemUIDialog = (SystemUIDialog) dialog;
        if (DEBUG) {
            Log.d("BroadcastDialog", "onCreate");
        }
        this.mUiEventLogger.log(BroadcastDialogEvent.BROADCAST_DIALOG_SHOW);
        View inflate = systemUIDialog.getLayoutInflater().inflate(R.layout.broadcast_dialog, (ViewGroup) null);
        systemUIDialog.getWindow().setContentView(inflate);
        TextView textView = (TextView) inflate.requireViewById(R.id.dialog_title);
        TextView textView2 = (TextView) inflate.requireViewById(R.id.dialog_subtitle);
        textView.setText(this.mContext.getString(R.string.bt_le_audio_broadcast_dialog_title, this.mCurrentBroadcastApp));
        Context context = this.mContext;
        String appLabel = MediaDataUtils.getAppLabel(context, this.mOutputPackageName, context.getString(R.string.bt_le_audio_broadcast_dialog_unknown_name));
        textView2.setText(this.mContext.getString(R.string.bt_le_audio_broadcast_dialog_sub_title, appLabel));
        this.mSwitchBroadcast = (Button) inflate.requireViewById(R.id.switch_broadcast);
        Button button = (Button) inflate.requireViewById(R.id.change_output);
        Button button2 = (Button) inflate.requireViewById(R.id.cancel);
        this.mSwitchBroadcast.setText(this.mContext.getString(R.string.bt_le_audio_broadcast_dialog_switch_app, appLabel), (TextView.BufferType) null);
        final int i = 0;
        this.mSwitchBroadcast.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.bluetooth.BroadcastDialogDelegate$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                int i2 = i;
                Object obj = this;
                switch (i2) {
                    case 0:
                        BroadcastDialogDelegate broadcastDialogDelegate = (BroadcastDialogDelegate) obj;
                        if (BroadcastDialogDelegate.DEBUG) {
                            broadcastDialogDelegate.getClass();
                            Log.d("BroadcastDialog", "startSwitchBroadcast");
                        }
                        broadcastDialogDelegate.mSwitchBroadcast.setText(R.string.media_output_broadcast_starting);
                        broadcastDialogDelegate.mSwitchBroadcast.setEnabled(false);
                        LocalBluetoothLeBroadcast localBluetoothLeBroadcast = broadcastDialogDelegate.mLocalBluetoothManager.mProfileManager.mLeAudioBroadcast;
                        if (localBluetoothLeBroadcast != null) {
                            int i3 = localBluetoothLeBroadcast.mBroadcastId;
                            if (localBluetoothLeBroadcast.mServiceBroadcast != null) {
                                Log.d("LocalBluetoothLeBroadcast", "stopBroadcast()");
                                localBluetoothLeBroadcast.mServiceBroadcast.stopBroadcast(i3);
                                break;
                            } else {
                                Log.d("LocalBluetoothLeBroadcast", "The BluetoothLeBroadcast is null when stopping the broadcast.");
                                break;
                            }
                        } else {
                            Log.d("BroadcastDialog", "The broadcast profile is null");
                            broadcastDialogDelegate.mSwitchBroadcast.setText(R.string.media_output_broadcast_start_failed);
                            broadcastDialogDelegate.mSwitchBroadcast.setEnabled(false);
                            broadcastDialogDelegate.refreshSwitchBroadcastButton();
                            break;
                        }
                    default:
                        SystemUIDialog systemUIDialog2 = (SystemUIDialog) obj;
                        if (BroadcastDialogDelegate.DEBUG) {
                            Log.d("BroadcastDialog", "BroadcastDialog dismiss.");
                        }
                        systemUIDialog2.dismiss();
                        break;
                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.bluetooth.BroadcastDialogDelegate$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                BroadcastDialogDelegate broadcastDialogDelegate = BroadcastDialogDelegate.this;
                SystemUIDialog systemUIDialog2 = systemUIDialog;
                broadcastDialogDelegate.mMediaOutputDialogManager.createAndShow(broadcastDialogDelegate.mOutputPackageName, true, null, true, null, null);
                systemUIDialog2.dismiss();
            }
        });
        final int i2 = 1;
        button2.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.bluetooth.BroadcastDialogDelegate$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                int i22 = i2;
                Object obj = systemUIDialog;
                switch (i22) {
                    case 0:
                        BroadcastDialogDelegate broadcastDialogDelegate = (BroadcastDialogDelegate) obj;
                        if (BroadcastDialogDelegate.DEBUG) {
                            broadcastDialogDelegate.getClass();
                            Log.d("BroadcastDialog", "startSwitchBroadcast");
                        }
                        broadcastDialogDelegate.mSwitchBroadcast.setText(R.string.media_output_broadcast_starting);
                        broadcastDialogDelegate.mSwitchBroadcast.setEnabled(false);
                        LocalBluetoothLeBroadcast localBluetoothLeBroadcast = broadcastDialogDelegate.mLocalBluetoothManager.mProfileManager.mLeAudioBroadcast;
                        if (localBluetoothLeBroadcast != null) {
                            int i3 = localBluetoothLeBroadcast.mBroadcastId;
                            if (localBluetoothLeBroadcast.mServiceBroadcast != null) {
                                Log.d("LocalBluetoothLeBroadcast", "stopBroadcast()");
                                localBluetoothLeBroadcast.mServiceBroadcast.stopBroadcast(i3);
                                break;
                            } else {
                                Log.d("LocalBluetoothLeBroadcast", "The BluetoothLeBroadcast is null when stopping the broadcast.");
                                break;
                            }
                        } else {
                            Log.d("BroadcastDialog", "The broadcast profile is null");
                            broadcastDialogDelegate.mSwitchBroadcast.setText(R.string.media_output_broadcast_start_failed);
                            broadcastDialogDelegate.mSwitchBroadcast.setEnabled(false);
                            broadcastDialogDelegate.refreshSwitchBroadcastButton();
                            break;
                        }
                    default:
                        SystemUIDialog systemUIDialog2 = (SystemUIDialog) obj;
                        if (BroadcastDialogDelegate.DEBUG) {
                            Log.d("BroadcastDialog", "BroadcastDialog dismiss.");
                        }
                        systemUIDialog2.dismiss();
                        break;
                }
            }
        });
    }

    @Override // com.android.systemui.statusbar.phone.DialogDelegate
    public final void onStart(Dialog dialog) {
        ((HashSet) this.mDialogs).add((SystemUIDialog) dialog);
        Executor executor = this.mBgExecutor;
        LocalBluetoothLeBroadcast localBluetoothLeBroadcast = this.mLocalBluetoothManager.mProfileManager.mLeAudioBroadcast;
        if (localBluetoothLeBroadcast == null) {
            Log.d("BroadcastDialog", "The broadcast profile is null");
        } else {
            localBluetoothLeBroadcast.registerServiceCallBack(executor, this.mBroadcastCallback);
        }
    }

    @Override // com.android.systemui.statusbar.phone.DialogDelegate
    public final void onStop(Dialog dialog) {
        SystemUIDialog systemUIDialog = (SystemUIDialog) dialog;
        LocalBluetoothLeBroadcast localBluetoothLeBroadcast = this.mLocalBluetoothManager.mProfileManager.mLeAudioBroadcast;
        if (localBluetoothLeBroadcast == null) {
            Log.d("BroadcastDialog", "The broadcast profile is null");
        } else {
            localBluetoothLeBroadcast.unregisterServiceCallBack(this.mBroadcastCallback);
        }
        ((HashSet) this.mDialogs).remove(systemUIDialog);
    }

    @Override // com.android.systemui.statusbar.phone.DialogDelegate
    public final void onWindowFocusChanged(Dialog dialog, boolean z) {
        SystemUIDialog systemUIDialog = (SystemUIDialog) dialog;
        if (z || !systemUIDialog.isShowing()) {
            return;
        }
        systemUIDialog.dismiss();
    }

    public final void refreshSwitchBroadcastButton() {
        Context context = this.mContext;
        this.mSwitchBroadcast.setText(this.mContext.getString(R.string.bt_le_audio_broadcast_dialog_switch_app, MediaDataUtils.getAppLabel(context, this.mOutputPackageName, context.getString(R.string.bt_le_audio_broadcast_dialog_unknown_name))), (TextView.BufferType) null);
        this.mSwitchBroadcast.setEnabled(true);
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.bluetooth.BroadcastDialogDelegate$1, reason: invalid class name */
    public final class AnonymousClass1 implements BluetoothLeBroadcast.Callback {
        public AnonymousClass1() {
        }

        public final void onBroadcastMetadataChanged(int i, BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata) {
            if (BroadcastDialogDelegate.DEBUG) {
                Log.d("BroadcastDialog", "onBroadcastMetadataChanged(), broadcastId = " + i + ", metadata = " + bluetoothLeBroadcastMetadata);
            }
            BroadcastDialogDelegate.this.mMainThreadHandler.post(new BroadcastDialogDelegate$1$$ExternalSyntheticLambda0(this, 0));
        }

        public final void onBroadcastStartFailed(int i) {
            if (BroadcastDialogDelegate.DEBUG) {
                ListPopupWindow$$ExternalSyntheticOutline0.m(i, "onBroadcastStartFailed(), reason = ", "BroadcastDialog");
            }
            BroadcastDialogDelegate.this.mMainThreadHandler.postDelayed(new BroadcastDialogDelegate$1$$ExternalSyntheticLambda0(this, 4), 3000L);
        }

        public final void onBroadcastStarted(int i, int i2) {
            if (BroadcastDialogDelegate.DEBUG) {
                SuggestionsAdapter$$ExternalSyntheticOutline0.m(i, i2, "onBroadcastStarted(), reason = ", ", broadcastId = ", "BroadcastDialog");
            }
            BroadcastDialogDelegate.this.mMainThreadHandler.post(new BroadcastDialogDelegate$1$$ExternalSyntheticLambda0(this, 3));
        }

        public final void onBroadcastStopFailed(int i) {
            if (BroadcastDialogDelegate.DEBUG) {
                ListPopupWindow$$ExternalSyntheticOutline0.m(i, "onBroadcastStopFailed(), reason = ", "BroadcastDialog");
            }
            BroadcastDialogDelegate.this.mMainThreadHandler.postDelayed(new BroadcastDialogDelegate$1$$ExternalSyntheticLambda0(this, 2), 3000L);
        }

        public final void onBroadcastStopped(int i, int i2) {
            if (BroadcastDialogDelegate.DEBUG) {
                SuggestionsAdapter$$ExternalSyntheticOutline0.m(i, i2, "onBroadcastStopped(), reason = ", ", broadcastId = ", "BroadcastDialog");
            }
            BroadcastDialogDelegate.this.mMainThreadHandler.post(new BroadcastDialogDelegate$1$$ExternalSyntheticLambda0(this, 1));
        }

        public final void onBroadcastUpdateFailed(int i, int i2) {
        }

        public final void onBroadcastUpdated(int i, int i2) {
        }

        public final void onPlaybackStarted(int i, int i2) {
        }

        public final void onPlaybackStopped(int i, int i2) {
        }
    }
}
