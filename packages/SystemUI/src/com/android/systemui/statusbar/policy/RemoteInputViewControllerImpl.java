package com.android.systemui.statusbar.policy;

import android.app.ActivityOptions;
import android.app.INotificationManager;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Intent;
import android.content.pm.ShortcutManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import android.view.ContentInfo;
import android.view.View;
import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.NotiRune;
import com.android.systemui.R;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.plugins.subscreen.SubRoom;
import com.android.systemui.statusbar.NotificationRemoteInputManager$$ExternalSyntheticLambda1;
import com.android.systemui.statusbar.RemoteInputController;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.policy.RemoteInputView;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;
import kotlin.collections.CollectionsKt___CollectionsKt;

public final class RemoteInputViewControllerImpl implements RemoteInputViewController {
    public final NotificationRemoteInputManager$$ExternalSyntheticLambda1 bouncerChecker;
    public final NotificationEntry entry;
    public boolean isBound;
    public final INotificationManager notifManager;
    public final RemoteInputViewControllerImpl$onFocusChangeListener$1 onFocusChangeListener;
    public final ArraySet onSendListeners = new ArraySet();
    public final RemoteInputViewControllerImpl$onSendRemoteInputListener$1 onSendRemoteInputListener;
    public PendingIntent pendingIntent;
    public RemoteInput remoteInput;
    public final RemoteInputController remoteInputController;
    public final RemoteInputQuickSettingsDisabler remoteInputQuickSettingsDisabler;
    public RemoteInput[] remoteInputs;
    public final ShortcutManager shortcutManager;
    public final UiEventLogger uiEventLogger;
    public final RemoteInputView view;

    public RemoteInputViewControllerImpl(RemoteInputView remoteInputView, NotificationEntry notificationEntry, RemoteInputQuickSettingsDisabler remoteInputQuickSettingsDisabler, RemoteInputController remoteInputController, ShortcutManager shortcutManager, UiEventLogger uiEventLogger, FeatureFlags featureFlags) {
        this.view = remoteInputView;
        this.entry = notificationEntry;
        this.remoteInputQuickSettingsDisabler = remoteInputQuickSettingsDisabler;
        this.remoteInputController = remoteInputController;
        this.shortcutManager = shortcutManager;
        this.uiEventLogger = uiEventLogger;
        this.notifManager = NotiRune.NOTI_SUBSCREEN_SUPPORT_NOTIFICATION_HISTORY ? INotificationManager.Stub.asInterface(ServiceManager.getService(SubRoom.EXTRA_VALUE_NOTIFICATION)) : null;
        this.onFocusChangeListener = new View.OnFocusChangeListener() { // from class: com.android.systemui.statusbar.policy.RemoteInputViewControllerImpl$onFocusChangeListener$1
            @Override // android.view.View.OnFocusChangeListener
            public final void onFocusChange(View view, boolean z) {
                RemoteInputQuickSettingsDisabler remoteInputQuickSettingsDisabler2 = RemoteInputViewControllerImpl.this.remoteInputQuickSettingsDisabler;
                if (remoteInputQuickSettingsDisabler2.remoteInputActive != z) {
                    remoteInputQuickSettingsDisabler2.remoteInputActive = z;
                }
            }
        };
        this.onSendRemoteInputListener = new Runnable() { // from class: com.android.systemui.statusbar.policy.RemoteInputViewControllerImpl$onSendRemoteInputListener$1
            @Override // java.lang.Runnable
            public final void run() {
                Intent intent;
                INotificationManager iNotificationManager;
                RemoteInputViewControllerImpl remoteInputViewControllerImpl = RemoteInputViewControllerImpl.this;
                RemoteInput remoteInput = remoteInputViewControllerImpl.remoteInput;
                if (remoteInput == null) {
                    Log.e("RemoteInput", "cannot send remote input, RemoteInput data is null");
                    return;
                }
                PendingIntent pendingIntent = remoteInputViewControllerImpl.pendingIntent;
                if (pendingIntent == null) {
                    Log.e("RemoteInput", "cannot send remote input, PendingIntent is null");
                    return;
                }
                NotificationEntry notificationEntry2 = remoteInputViewControllerImpl.entry;
                ContentInfo contentInfo = notificationEntry2.remoteInputAttachment;
                RemoteInputView remoteInputView2 = remoteInputViewControllerImpl.view;
                if (contentInfo == null) {
                    Bundle bundle = new Bundle();
                    bundle.putString(remoteInput.getResultKey(), remoteInputView2.mEditText.getText().toString());
                    Intent addFlags = new Intent().addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
                    RemoteInput.addResultsToIntent(remoteInputViewControllerImpl.remoteInputs, addFlags, bundle);
                    notificationEntry2.remoteInputText = remoteInputView2.mEditText.getText();
                    remoteInputView2.setAttachment(null);
                    notificationEntry2.remoteInputUri = null;
                    notificationEntry2.remoteInputMimeType = null;
                    RemoteInput.setResultsSource(addFlags, notificationEntry2.editedSuggestionInfo != null ? 1 : 0);
                    intent = addFlags;
                } else {
                    String str = notificationEntry2.remoteInputMimeType;
                    Uri uri = notificationEntry2.remoteInputUri;
                    HashMap hashMap = new HashMap();
                    hashMap.put(str, uri);
                    StatusBarNotification statusBarNotification = notificationEntry2.mSbn;
                    RemoteInputUriController remoteInputUriController = remoteInputViewControllerImpl.remoteInputController.mRemoteInputUriController;
                    remoteInputUriController.getClass();
                    try {
                        remoteInputUriController.mStatusBarManagerService.grantInlineReplyUriPermission(statusBarNotification.getKey(), uri, statusBarNotification.getUser(), statusBarNotification.getPackageName());
                    } catch (Exception e) {
                        Log.e("RemoteInputUriController", "Failed to grant URI permissions:" + e.getMessage(), e);
                    }
                    Intent addFlags2 = new Intent().addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
                    RemoteInput.addDataResultToIntent(remoteInput, addFlags2, hashMap);
                    Bundle bundle2 = new Bundle();
                    bundle2.putString(remoteInput.getResultKey(), remoteInputView2.mEditText.getText().toString());
                    RemoteInput.addResultsToIntent(remoteInputViewControllerImpl.remoteInputs, addFlags2, bundle2);
                    CharSequence label = notificationEntry2.remoteInputAttachment.getClip().getDescription().getLabel();
                    if (TextUtils.isEmpty(label)) {
                        label = remoteInputView2.getResources().getString(R.string.remote_input_image_insertion_text);
                    }
                    if (!TextUtils.isEmpty(remoteInputView2.mEditText.getText())) {
                        label = "\"" + ((Object) label) + "\" " + ((Object) remoteInputView2.mEditText.getText());
                    }
                    notificationEntry2.remoteInputText = label;
                    RemoteInput.setResultsSource(addFlags2, notificationEntry2.editedSuggestionInfo != null ? 1 : 0);
                    intent = addFlags2;
                }
                RemoteInputViewControllerImpl remoteInputViewControllerImpl2 = RemoteInputViewControllerImpl.this;
                NotificationRemoteInputManager$$ExternalSyntheticLambda1 notificationRemoteInputManager$$ExternalSyntheticLambda1 = remoteInputViewControllerImpl2.bouncerChecker;
                RemoteInputView remoteInputView3 = remoteInputViewControllerImpl2.view;
                remoteInputView3.mEditText.setEnabled(false);
                remoteInputView3.mSending = true;
                remoteInputView3.mSendButton.setVisibility(4);
                remoteInputView3.mProgressBar.setVisibility(0);
                remoteInputView3.mEditText.mShowImeOnInputConnection = false;
                long elapsedRealtime = SystemClock.elapsedRealtime();
                NotificationEntry notificationEntry3 = remoteInputViewControllerImpl2.entry;
                notificationEntry3.lastRemoteInputSent = elapsedRealtime;
                notificationEntry3.mRemoteEditImeAnimatingAway = true;
                Object obj = remoteInputView3.mToken;
                RemoteInputController remoteInputController2 = remoteInputViewControllerImpl2.remoteInputController;
                remoteInputController2.getClass();
                String str2 = notificationEntry3.mKey;
                Objects.requireNonNull(str2);
                Objects.requireNonNull(obj);
                remoteInputController2.mSpinning.put(str2, obj);
                remoteInputController2.removeRemoteInput(notificationEntry3, remoteInputView3.mToken, "RemoteInputViewController#sendRemoteInput");
                int size = remoteInputController2.mCallbacks.size();
                for (int i = 0; i < size; i++) {
                    ((RemoteInputController.Callback) remoteInputController2.mCallbacks.get(i)).onRemoteInputSent(notificationEntry3);
                }
                notificationEntry3.hasSentReply = true;
                Iterator it = CollectionsKt___CollectionsKt.toList(remoteInputViewControllerImpl2.onSendListeners).iterator();
                if (it.hasNext()) {
                    ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(it.next());
                    throw null;
                }
                remoteInputViewControllerImpl2.shortcutManager.onApplicationActive(notificationEntry3.mSbn.getPackageName(), notificationEntry3.mSbn.getUser().getIdentifier());
                remoteInputViewControllerImpl2.uiEventLogger.logWithInstanceId(RemoteInputView.NotificationRemoteInputEvent.NOTIFICATION_REMOTE_INPUT_SEND, notificationEntry3.mSbn.getUid(), notificationEntry3.mSbn.getPackageName(), notificationEntry3.mSbn.getInstanceId());
                try {
                    if (NotiRune.NOTI_SUBSCREEN_SUPPORT_NOTIFICATION_HISTORY && (iNotificationManager = remoteInputViewControllerImpl2.notifManager) != null) {
                        iNotificationManager.addReplyHistory(1, notificationEntry3.mKey, notificationEntry3.mSbn.getPackageName(), notificationEntry3.mSbn.getUser().getIdentifier(), "NOUI_2023", remoteInputView3.mEditText.getText().toString());
                    }
                    ActivityOptions makeBasic = ActivityOptions.makeBasic();
                    makeBasic.setPendingIntentBackgroundActivityStartMode(1);
                    pendingIntent.send(remoteInputView3.getContext(), 0, intent, null, null, null, makeBasic.toBundle());
                } catch (PendingIntent.CanceledException e2) {
                    Log.i("RemoteInput", "Unable to send remote input result", e2);
                    remoteInputViewControllerImpl2.uiEventLogger.logWithInstanceId(RemoteInputView.NotificationRemoteInputEvent.NOTIFICATION_REMOTE_INPUT_FAILURE, notificationEntry3.mSbn.getUid(), notificationEntry3.mSbn.getPackageName(), notificationEntry3.mSbn.getInstanceId());
                } catch (Exception unused) {
                }
                remoteInputView3.setAttachment(null);
                RemoteInputQuickSettingsDisabler remoteInputQuickSettingsDisabler2 = remoteInputViewControllerImpl2.remoteInputQuickSettingsDisabler;
                boolean z = remoteInputQuickSettingsDisabler2.remoteInputActive;
                if (z && z) {
                    remoteInputQuickSettingsDisabler2.remoteInputActive = false;
                }
            }
        };
    }

    public final void bind() {
        if (this.isBound) {
            return;
        }
        this.isBound = true;
        RemoteInput remoteInput = this.remoteInput;
        RemoteInputView remoteInputView = this.view;
        if (remoteInput != null) {
            remoteInputView.mEditText.setHint(remoteInput.getLabel());
            remoteInputView.setSupportedMimeTypes(remoteInput.getAllowedDataTypes());
            remoteInputView.updateRemoteInputLimitToastResources(remoteInput);
        }
        remoteInputView.mEditTextFocusChangeListeners.add(this.onFocusChangeListener);
        remoteInputView.mOnSendListeners.add(this.onSendRemoteInputListener);
    }

    public final void setRemoteInput(RemoteInput remoteInput) {
        this.remoteInput = remoteInput;
        if (remoteInput != null) {
            if (!this.isBound) {
                remoteInput = null;
            }
            if (remoteInput != null) {
                CharSequence label = remoteInput.getLabel();
                RemoteInputView remoteInputView = this.view;
                remoteInputView.mEditText.setHint(label);
                remoteInputView.setSupportedMimeTypes(remoteInput.getAllowedDataTypes());
                remoteInputView.updateRemoteInputLimitToastResources(remoteInput);
            }
        }
    }

    public final void unbind() {
        if (this.isBound) {
            this.isBound = false;
            RemoteInputView remoteInputView = this.view;
            remoteInputView.mEditTextFocusChangeListeners.remove(this.onFocusChangeListener);
            remoteInputView.mOnSendListeners.remove(this.onSendRemoteInputListener);
        }
    }
}
