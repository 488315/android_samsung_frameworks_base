package com.android.systemui.pluginlock.component;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecPinBasedInputViewController$$ExternalSyntheticOutline0;
import com.android.systemui.pluginlock.PluginLockInstanceState;
import com.android.systemui.pluginlock.PluginLockMediator;
import com.android.systemui.pluginlock.model.DynamicLockData;
import com.android.systemui.util.LogUtil;
import com.android.systemui.util.SettingsHelper;

/* loaded from: classes2.dex */
public class PluginLockNotification extends AbstractPluginLockItem {
    private static final String KEY_ICON_ONLY = "lockscreen_minimizing_notification";
    private static final String KEY_NOTIFICATION_TYPE = "notification_type";
    private static final String KEY_NOTIFICATION_VISIBILITY = "notification_visibility";
    private static final String KEY_VISIBILITY = "lock_screen_show_notifications";
    private static final String TAG = "PluginLockNotification";
    private static final String UPDATE_LOCKSTAR_DATA = "update_lockstar_data";
    private static final String UPDATE_LOCKSTAR_DATA_ITEM = "update_lockstar_data_item";
    private static final int VALUE_CARD = 0;
    private static final int VALUE_ICON_ONLY = 1;
    private final SettingsHelper.OnChangedCallback mCallBack;
    private boolean mIsDlsData;
    private final PluginLockMediator mMediator;
    private final Uri[] mUris;
    private int mVisibility;

    public PluginLockNotification(Context context, PluginLockInstanceState pluginLockInstanceState, SettingsHelper settingsHelper, PluginLockMediator pluginLockMediator) {
        super(context, pluginLockInstanceState, settingsHelper);
        this.mIsDlsData = true;
        this.mVisibility = -1;
        this.mUris = new Uri[]{Settings.System.getUriFor("lockscreen_minimizing_notification"), Settings.Secure.getUriFor("lock_screen_show_notifications")};
        this.mCallBack = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.pluginlock.component.PluginLockNotification$$ExternalSyntheticLambda0
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                this.f$0.lambda$new$0(uri);
            }
        };
        this.mMediator = pluginLockMediator;
    }

    private int getCurrentNotificationType() {
        int settingsInt = getSettingsInt("lockscreen_minimizing_notification", 1);
        StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(settingsInt, "getCurrentNotificationType() getSettings: ", ", Type : ");
        int i = settingsInt + 1;
        RecyclerView$$ExternalSyntheticOutline0.m(i, TAG, sbM);
        return i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(Uri uri) {
        Log.d(TAG, "onChange() uri: " + uri);
        if (uri != null) {
            StringBuilder sb = new StringBuilder("onChange() mCallbackValue: ");
            sb.append(this.mCallbackValue);
            sb.append(" , mVisibility: ");
            RecyclerView$$ExternalSyntheticOutline0.m(this.mVisibility, TAG, sb);
            if (this.mCallbackRegisterTime == 0 || (this.mCallbackValue == -1 && this.mVisibility == -1)) {
                Log.w(TAG, "onChange() wrong state");
                return;
            }
            if (System.currentTimeMillis() - this.mCallbackRegisterTime < 8000 && (this.mCallbackValue == getCurrentNotificationType() || this.mVisibility == getSettingsInt("lock_screen_show_notifications", 1))) {
                Log.d(TAG, "onChange() ignored");
            } else {
                updateLockStarStoredData(uri);
                recover();
            }
        }
    }

    private void loadData(DynamicLockData dynamicLockData) {
        this.mIsDlsData = dynamicLockData.isDlsData();
    }

    private void registerCallback(int i, int i2) {
        SuggestionsAdapter$$ExternalSyntheticOutline0.m(i, i2, "registerCallback() type: ", ", visibility: ", TAG);
        this.mVisibility = i2;
        this.mCallbackValue = i;
        this.mCallbackRegisterTime = System.currentTimeMillis();
        this.mSettingsHelper.registerCallback(this.mCallBack, this.mUris);
    }

    private void setNotificationType(int i) {
        StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "setNotificationType() value: ", ", putSettings : ");
        int i2 = i - 1;
        RecyclerView$$ExternalSyntheticOutline0.m(i2, TAG, sbM);
        putSettingsSystem("lockscreen_minimizing_notification", i2);
    }

    private void setNotificationVisibility(int i) {
        if (i != -1) {
            LogUtil.d(TAG, MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "setNotificationVisibility: "), new Object[0]);
            putSettingsSecure("lock_screen_show_notifications", i);
        }
    }

    private void unregisterCallback() {
        Log.d(TAG, "unregisterCallback()");
        this.mVisibility = -1;
        this.mCallbackValue = -1;
        this.mCallbackRegisterTime = 0L;
        this.mSettingsHelper.unregisterCallback(this.mCallBack);
    }

    private void updateLockStarStoredData(Uri uri) {
        Bundle bundle = new Bundle();
        if (uri.equals(Settings.Secure.getUriFor("lock_screen_show_notifications"))) {
            bundle.putString("action", UPDATE_LOCKSTAR_DATA);
            Bundle bundle2 = new Bundle();
            bundle2.putString(UPDATE_LOCKSTAR_DATA_ITEM, KEY_NOTIFICATION_VISIBILITY);
            bundle2.putInt(KEY_NOTIFICATION_VISIBILITY, getSettingsInt("lock_screen_show_notifications", 1));
            bundle.putBundle("extras", bundle2);
        } else if (uri.equals(Settings.System.getUriFor("lockscreen_minimizing_notification"))) {
            bundle.putString("action", UPDATE_LOCKSTAR_DATA);
            Bundle bundle3 = new Bundle();
            bundle3.putString(UPDATE_LOCKSTAR_DATA_ITEM, KEY_NOTIFICATION_TYPE);
            bundle3.putInt(KEY_NOTIFICATION_TYPE, getCurrentNotificationType());
            bundle.putBundle("extras", bundle3);
        }
        Log.d(TAG, "updateLockStarStoredData() bundle" + bundle.toString());
        this.mMediator.onEventReceived(bundle);
    }

    @Override // com.android.systemui.pluginlock.component.AbstractPluginLockItem
    public void apply(DynamicLockData dynamicLockData, DynamicLockData dynamicLockData2) {
        Log.d(TAG, "apply()");
        loadData(dynamicLockData2);
        int iIntValue = dynamicLockData2.getNotificationData().getNotiType().intValue();
        int iIntValue2 = dynamicLockData2.getNotificationData().getVisibility().intValue();
        int notificationState = getNotificationState();
        RecyclerView$$ExternalSyntheticOutline0.m(iIntValue2, TAG, MutableObjectList$$ExternalSyntheticOutline0.m(notificationState, iIntValue, "apply() state:", ", notiType:", ", notiVisibility:"));
        if (notificationState == -2) {
            Log.d(TAG, "apply() skip!");
            return;
        }
        if (notificationState == -3) {
            update(dynamicLockData, dynamicLockData2);
            return;
        }
        if (iIntValue2 == -1 || iIntValue == 0) {
            return;
        }
        int currentNotificationType = getCurrentNotificationType();
        int settingsInt = getSettingsInt("lock_screen_show_notifications", 1);
        setNotificationBackup(currentNotificationType, settingsInt);
        KeyguardSecPinBasedInputViewController$$ExternalSyntheticOutline0.m(MutableObjectList$$ExternalSyntheticOutline0.m(currentNotificationType, settingsInt, "apply() Backup curType: ", ", curVisibility: ", ", Set notiType: "), iIntValue, ", notiVisibility: ", iIntValue2, TAG);
        setNotificationVisibility(iIntValue2);
        setNotificationType(iIntValue);
        registerCallback(iIntValue, iIntValue2);
    }

    @Override // com.android.systemui.pluginlock.component.AbstractPluginLockItem
    public void recover() {
        Log.d(TAG, "recover()");
        this.mVisibility = -1;
        setNotificationBackup(-1, -1);
        setNotificationState(-2);
    }

    @Override // com.android.systemui.pluginlock.component.AbstractPluginLockItem
    public void reset(boolean z) {
        Log.d(TAG, "reset()");
        unregisterCallback();
        if (z) {
            return;
        }
        int notificationState = getNotificationState();
        ListPopupWindow$$ExternalSyntheticOutline0.m(notificationState, "reset() state: ", TAG);
        if (notificationState != -1 && notificationState != -2) {
            int notificationBackupVisibility = getNotificationBackupVisibility();
            int notificationBackupType = getNotificationBackupType();
            setNotificationVisibility(notificationBackupVisibility);
            setNotificationType(notificationBackupType);
        }
        setNotificationBackup(-1, -1);
    }

    @Override // com.android.systemui.pluginlock.component.AbstractPluginLockItem
    public /* bridge */ /* synthetic */ void setInstanceState(int i, PluginLockInstanceState pluginLockInstanceState) {
        super.setInstanceState(i, pluginLockInstanceState);
    }

    @Override // com.android.systemui.pluginlock.component.AbstractPluginLockItem
    public void update(DynamicLockData dynamicLockData, DynamicLockData dynamicLockData2) {
        Log.d(TAG, "update()");
        loadData(dynamicLockData2);
        int notificationState = getNotificationState();
        ListPopupWindow$$ExternalSyntheticOutline0.m(notificationState, "update() state: ", TAG);
        if (notificationState == -2 && this.mIsDlsData) {
            Log.d(TAG, "update() skip!");
            return;
        }
        if (notificationState == -1) {
            apply(dynamicLockData, dynamicLockData2);
            return;
        }
        unregisterCallback();
        int iIntValue = dynamicLockData2.getNotificationData().getVisibility().intValue();
        int iIntValue2 = dynamicLockData2.getNotificationData().getNotiType().intValue();
        SuggestionsAdapter$$ExternalSyntheticOutline0.m(iIntValue2, iIntValue, "update() notiType: ", ", visibility: ", TAG);
        if (iIntValue != -1 && iIntValue2 != 0) {
            setNotificationVisibility(iIntValue);
            setNotificationType(iIntValue2);
            registerCallback(iIntValue2, iIntValue);
        } else {
            Log.d(TAG, "disable & restore backup value ");
            setNotificationVisibility(getNotificationBackupVisibility());
            setNotificationType(getNotificationBackupType());
            setNotificationBackup(-1, -1);
        }
    }

    @Override // com.android.systemui.pluginlock.component.AbstractPluginLockItem
    public /* bridge */ /* synthetic */ void setInstanceState(PluginLockInstanceState pluginLockInstanceState) {
        super.setInstanceState(pluginLockInstanceState);
    }
}
