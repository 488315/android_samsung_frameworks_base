package com.android.systemui.pluginlock.component;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.picker.adapter.AbsAdapter$1$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.pluginlock.PluginLockInstanceData;
import com.android.systemui.pluginlock.PluginLockInstanceState;
import com.android.systemui.pluginlock.PluginLockMediator;
import com.android.systemui.pluginlock.model.DynamicLockData;
import com.android.systemui.util.SettingsHelper;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class PluginLockShortcut extends AbstractPluginLockItem {
    private static final String KEY_SHORTCUT_ENABLED = "lockscreen_show_shortcut";
    private static final String KEY_SHORTCUT_VISIBILITY = "shortcut_visibility";
    private static final String TAG = "PluginLockShortcut";
    private static final String UPDATE_LOCKSTAR_DATA = "update_lockstar_data";
    private static final String UPDATE_LOCKSTAR_DATA_ITEM = "update_lockstar_data_item";
    private final SettingsHelper.OnChangedCallback mCallback;
    private boolean mIsDlsData;
    private final PluginLockMediator mMediator;
    private int mShortcutVisibility;

    public PluginLockShortcut(Context context, PluginLockInstanceState pluginLockInstanceState, SettingsHelper settingsHelper, PluginLockMediator pluginLockMediator) {
        super(context, pluginLockInstanceState, settingsHelper);
        this.mIsDlsData = true;
        this.mShortcutVisibility = -1;
        this.mCallback = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.pluginlock.component.PluginLockShortcut$$ExternalSyntheticLambda0
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                PluginLockShortcut.this.lambda$new$0(uri);
            }
        };
        this.mMediator = pluginLockMediator;
    }

    private int getShortcutBackup() {
        PluginLockInstanceData.Data.RecoverData recoverData;
        PluginLockInstanceState pluginLockInstanceState = this.mInstanceState;
        if (pluginLockInstanceState == null || (recoverData = pluginLockInstanceState.getRecoverData()) == null) {
            return -1;
        }
        return recoverData.getShortcutBackupValue().intValue();
    }

    private int getShortcutState() {
        PluginLockInstanceData.Data.RecoverData recoverData;
        PluginLockInstanceState pluginLockInstanceState = this.mInstanceState;
        if (pluginLockInstanceState == null || (recoverData = pluginLockInstanceState.getRecoverData()) == null) {
            return -1;
        }
        return recoverData.getShortcutState().intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(Uri uri) {
        if (uri != null) {
            Log.d(TAG, "onChange() uri: " + uri);
            if (this.mCallbackRegisterTime == 0 || this.mCallbackValue == -1) {
                Log.w(TAG, "onChange() wrong state");
            } else if (System.currentTimeMillis() - this.mCallbackRegisterTime < 8000 && this.mCallbackValue == getSettingsInt("lockscreen_show_shortcut", 1)) {
                Log.d(TAG, "onChange() ignored");
            } else {
                updateLockStarStoredData(uri);
                recover();
            }
        }
    }

    private void loadData(DynamicLockData dynamicLockData) {
        this.mIsDlsData = dynamicLockData.isDlsData();
        this.mShortcutVisibility = dynamicLockData.getShortcutData().getVisibility().intValue();
        RecyclerView$$ExternalSyntheticOutline0.m(this.mShortcutVisibility, TAG, new StringBuilder("loadData() mShortcutVisibility: "));
    }

    private void registerCallback(int i) {
        ListPopupWindow$$ExternalSyntheticOutline0.m(i, "registerCallback() value: ", TAG);
        this.mCallbackValue = i;
        this.mCallbackRegisterTime = System.currentTimeMillis();
        this.mSettingsHelper.registerCallback(this.mCallback, Settings.System.getUriFor("lockscreen_show_shortcut"));
    }

    private void setShortcutBackup(int i) {
        PluginLockInstanceData.Data.RecoverData recoverData;
        PluginLockInstanceState pluginLockInstanceState = this.mInstanceState;
        if (pluginLockInstanceState == null || (recoverData = pluginLockInstanceState.getRecoverData()) == null) {
            return;
        }
        recoverData.setShortcutBackup(i);
        if (i >= 0) {
            recoverData.setShortcutState(-3);
        } else {
            recoverData.setShortcutState(-1);
        }
        this.mInstanceState.updateDb();
    }

    private void setShortcutState(int i) {
        PluginLockInstanceData.Data.RecoverData recoverData;
        PluginLockInstanceState pluginLockInstanceState = this.mInstanceState;
        if (pluginLockInstanceState == null || (recoverData = pluginLockInstanceState.getRecoverData()) == null) {
            return;
        }
        recoverData.setShortcutState(i);
        this.mInstanceState.updateDb();
    }

    private void setShortcutVisibility(int i) {
        ListPopupWindow$$ExternalSyntheticOutline0.m(i, "setShortcutVisibility : ", TAG);
        putSettingsSystem("lockscreen_show_shortcut", i);
    }

    private void unregisterCallback() {
        Log.d(TAG, "unregisterCallback() ");
        this.mCallbackValue = -1;
        this.mCallbackRegisterTime = 0L;
        this.mSettingsHelper.unregisterCallback(this.mCallback);
    }

    private void updateLockStarStoredData(Uri uri) {
        if (uri == null || !uri.equals(Settings.System.getUriFor("lockscreen_show_shortcut"))) {
            Log.w(TAG, "updateLockStarStoredData, uri is null or shortcut isn't enabled");
            return;
        }
        Bundle m = AbsAdapter$1$$ExternalSyntheticOutline0.m("action", UPDATE_LOCKSTAR_DATA);
        Bundle m2 = AbsAdapter$1$$ExternalSyntheticOutline0.m(UPDATE_LOCKSTAR_DATA_ITEM, KEY_SHORTCUT_VISIBILITY);
        m2.putInt(KEY_SHORTCUT_VISIBILITY, getSettingsInt("lockscreen_show_shortcut", 1));
        m.putBundle("extras", m2);
        Log.d(TAG, "updateLockStarStoredData() bundle" + m.toString());
        this.mMediator.onEventReceived(m);
    }

    @Override // com.android.systemui.pluginlock.component.AbstractPluginLockItem
    public void apply(DynamicLockData dynamicLockData, DynamicLockData dynamicLockData2) {
        loadData(dynamicLockData2);
        int shortcutState = getShortcutState();
        ListPopupWindow$$ExternalSyntheticOutline0.m(shortcutState, "apply() state:", TAG);
        if (shortcutState == -2) {
            Log.d(TAG, "apply() skip!");
            return;
        }
        if (shortcutState == -3) {
            update(dynamicLockData, dynamicLockData2);
            return;
        }
        if (this.mShortcutVisibility != -1) {
            int settingsInt = getSettingsInt("lockscreen_show_shortcut", 1);
            ListPopupWindow$$ExternalSyntheticOutline0.m(settingsInt, "apply() curValue: ", TAG);
            setShortcutBackup(settingsInt);
            int i = this.mShortcutVisibility != 0 ? 0 : 1;
            ListPopupWindow$$ExternalSyntheticOutline0.m(i, "apply() dlsVisible visibility: ", TAG);
            setShortcutVisibility(i);
            registerCallback(i);
        }
    }

    @Override // com.android.systemui.pluginlock.component.AbstractPluginLockItem
    public void recover() {
        Log.d(TAG, "recover()");
        this.mShortcutVisibility = -1;
        setShortcutBackup(-1);
        setShortcutState(-2);
    }

    @Override // com.android.systemui.pluginlock.component.AbstractPluginLockItem
    public void reset(boolean z) {
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("reset() reconnectReq: ", TAG, z);
        this.mShortcutVisibility = -1;
        unregisterCallback();
        if (z) {
            return;
        }
        int shortcutState = getShortcutState();
        ListPopupWindow$$ExternalSyntheticOutline0.m(shortcutState, "reset() state: ", TAG);
        if (shortcutState != -1 && shortcutState != -2) {
            int shortcutBackup = getShortcutBackup();
            ListPopupWindow$$ExternalSyntheticOutline0.m(shortcutBackup, "reset() original: ", TAG);
            setShortcutVisibility(shortcutBackup);
        }
        setShortcutBackup(-1);
    }

    @Override // com.android.systemui.pluginlock.component.AbstractPluginLockItem
    public /* bridge */ /* synthetic */ void setInstanceState(int i, PluginLockInstanceState pluginLockInstanceState) {
        super.setInstanceState(i, pluginLockInstanceState);
    }

    @Override // com.android.systemui.pluginlock.component.AbstractPluginLockItem
    public void update(DynamicLockData dynamicLockData, DynamicLockData dynamicLockData2) {
        loadData(dynamicLockData2);
        int shortcutState = getShortcutState();
        ListPopupWindow$$ExternalSyntheticOutline0.m(shortcutState, "update() state: ", TAG);
        if (shortcutState == -2 && this.mIsDlsData) {
            Log.d(TAG, "update() skip!");
            return;
        }
        if (shortcutState == -1) {
            apply(dynamicLockData, dynamicLockData2);
            return;
        }
        unregisterCallback();
        int intValue = dynamicLockData2.getShortcutData().getVisibility().intValue();
        ListPopupWindow$$ExternalSyntheticOutline0.m(intValue, "update() visibility: ", TAG);
        if (intValue == -1) {
            setShortcutVisibility(getShortcutBackup());
            setShortcutBackup(-1);
        } else {
            int i = intValue == 0 ? 1 : 0;
            setShortcutVisibility(i);
            registerCallback(i);
        }
    }

    @Override // com.android.systemui.pluginlock.component.AbstractPluginLockItem
    public /* bridge */ /* synthetic */ void setInstanceState(PluginLockInstanceState pluginLockInstanceState) {
        super.setInstanceState(pluginLockInstanceState);
    }
}
