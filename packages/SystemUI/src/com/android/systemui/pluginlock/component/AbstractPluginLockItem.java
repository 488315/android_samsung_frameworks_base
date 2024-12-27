package com.android.systemui.pluginlock.component;

import android.content.ContentResolver;
import android.content.Context;
import com.android.systemui.pluginlock.PluginLockInstanceData;
import com.android.systemui.pluginlock.PluginLockInstanceState;
import com.android.systemui.pluginlock.model.DynamicLockData;
import com.android.systemui.util.SettingsHelper;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
abstract class AbstractPluginLockItem {
    protected static final String KEY_ACTION = "action";
    protected static final String KEY_EXTRAS = "extras";
    static final int TIME_DELAY_CALLBACK = 8000;
    static final int VALUE_CHANGED_BY_DYNAMIC = -3;
    static final int VALUE_CHANGED_BY_USER = -2;
    static final int VALUE_DEFAULT = -1;
    final Context mContext;
    final ContentResolver mCr;
    PluginLockInstanceState mInstanceState;
    protected final SettingsHelper mSettingsHelper;
    int mCallbackValue = -1;
    long mCallbackRegisterTime = 0;

    public AbstractPluginLockItem(Context context, PluginLockInstanceState pluginLockInstanceState, SettingsHelper settingsHelper) {
        this.mContext = context;
        this.mCr = context.getContentResolver();
        this.mInstanceState = pluginLockInstanceState;
        this.mSettingsHelper = settingsHelper;
    }

    public abstract void apply(DynamicLockData dynamicLockData, DynamicLockData dynamicLockData2);

    public int getClockBackupValue() {
        PluginLockInstanceData.Data.RecoverData recoverData;
        PluginLockInstanceState pluginLockInstanceState = this.mInstanceState;
        if (pluginLockInstanceState == null || (recoverData = pluginLockInstanceState.getRecoverData()) == null) {
            return -1;
        }
        return recoverData.getClock().intValue();
    }

    public int getClockState() {
        PluginLockInstanceData.Data.RecoverData recoverData;
        PluginLockInstanceState pluginLockInstanceState = this.mInstanceState;
        if (pluginLockInstanceState == null || (recoverData = pluginLockInstanceState.getRecoverData()) == null) {
            return -1;
        }
        return recoverData.getClockState().intValue();
    }

    public int getNotificationBackupType() {
        PluginLockInstanceData.Data.RecoverData recoverData;
        PluginLockInstanceState pluginLockInstanceState = this.mInstanceState;
        if (pluginLockInstanceState == null || (recoverData = pluginLockInstanceState.getRecoverData()) == null) {
            return -1;
        }
        return recoverData.getNotificationBackupType().intValue();
    }

    public int getNotificationBackupVisibility() {
        PluginLockInstanceData.Data.RecoverData recoverData;
        PluginLockInstanceState pluginLockInstanceState = this.mInstanceState;
        if (pluginLockInstanceState == null || (recoverData = pluginLockInstanceState.getRecoverData()) == null) {
            return -1;
        }
        return recoverData.getNotificationBackupVisibility().intValue();
    }

    public int getNotificationState() {
        PluginLockInstanceData.Data.RecoverData recoverData;
        PluginLockInstanceState pluginLockInstanceState = this.mInstanceState;
        if (pluginLockInstanceState == null || (recoverData = pluginLockInstanceState.getRecoverData()) == null) {
            return -1;
        }
        return recoverData.getNotificationState().intValue();
    }

    public int getSettingsInt(String str, int i) {
        return this.mSettingsHelper.getInt(str, i);
    }

    public int getWallpaperDynamicBackupValue(int i) {
        PluginLockInstanceData.Data.RecoverData recoverData;
        PluginLockInstanceState pluginLockInstanceState = this.mInstanceState;
        if (pluginLockInstanceState == null || (recoverData = pluginLockInstanceState.getRecoverData()) == null) {
            return -2;
        }
        return recoverData.getWallpaperDynamic(i).intValue();
    }

    public int getWallpaperSourceBackupValue(int i) {
        PluginLockInstanceData.Data.RecoverData recoverData;
        PluginLockInstanceState pluginLockInstanceState = this.mInstanceState;
        if (pluginLockInstanceState == null || (recoverData = pluginLockInstanceState.getRecoverData()) == null) {
            return -1;
        }
        return recoverData.getWallpaperSource(i).intValue();
    }

    public int getWallpaperTypeBackupValue(int i) {
        PluginLockInstanceData.Data.RecoverData recoverData;
        PluginLockInstanceState pluginLockInstanceState = this.mInstanceState;
        if (pluginLockInstanceState == null || (recoverData = pluginLockInstanceState.getRecoverData()) == null) {
            return -1;
        }
        return recoverData.getWallpaperType(i).intValue();
    }

    public void putSettingsSecure(String str, int i) {
        this.mSettingsHelper.setSecureInt(str, i);
    }

    public void putSettingsSystem(String str, int i) {
        this.mSettingsHelper.setSystemInt(str, i);
    }

    public abstract void recover();

    public abstract void reset(boolean z);

    public void setClockBackupValue(int i) {
        PluginLockInstanceData.Data.RecoverData recoverData;
        PluginLockInstanceState pluginLockInstanceState = this.mInstanceState;
        if (pluginLockInstanceState == null || (recoverData = pluginLockInstanceState.getRecoverData()) == null) {
            return;
        }
        recoverData.setClock(i);
        if (i >= 0) {
            recoverData.setClockState(-3);
        } else {
            recoverData.setClockState(-1);
        }
        this.mInstanceState.updateDb();
    }

    public void setClockState(int i) {
        PluginLockInstanceData.Data.RecoverData recoverData;
        PluginLockInstanceState pluginLockInstanceState = this.mInstanceState;
        if (pluginLockInstanceState == null || (recoverData = pluginLockInstanceState.getRecoverData()) == null) {
            return;
        }
        recoverData.setClockState(i);
        this.mInstanceState.updateDb();
    }

    public void setInstanceState(PluginLockInstanceState pluginLockInstanceState) {
        this.mInstanceState = pluginLockInstanceState;
    }

    public void setNotificationBackup(int i, int i2) {
        PluginLockInstanceData.Data.RecoverData recoverData;
        PluginLockInstanceState pluginLockInstanceState = this.mInstanceState;
        if (pluginLockInstanceState == null || (recoverData = pluginLockInstanceState.getRecoverData()) == null) {
            return;
        }
        recoverData.setNotificationBackupType(Integer.valueOf(i));
        recoverData.setNotificationBackupVisibility(Integer.valueOf(i2));
        if (i < 0 || i2 < 0) {
            recoverData.setNotificationState(-1);
        } else {
            recoverData.setNotificationState(-3);
        }
        this.mInstanceState.updateDb();
    }

    public void setNotificationState(int i) {
        PluginLockInstanceData.Data.RecoverData recoverData;
        PluginLockInstanceState pluginLockInstanceState = this.mInstanceState;
        if (pluginLockInstanceState == null || (recoverData = pluginLockInstanceState.getRecoverData()) == null) {
            return;
        }
        recoverData.setNotificationState(i);
        this.mInstanceState.updateDb();
    }

    public void setWallpaperBackup(int i, int i2, int i3) {
        PluginLockInstanceData.Data.RecoverData recoverData;
        PluginLockInstanceState pluginLockInstanceState = this.mInstanceState;
        if (pluginLockInstanceState == null || (recoverData = pluginLockInstanceState.getRecoverData()) == null) {
            return;
        }
        recoverData.setWallpaperSource(i, i2);
        recoverData.setWallpaperType(i, i3);
        this.mInstanceState.updateDb();
    }

    public void setWallpaperBackupValue(int i, int i2, int i3, int i4) {
        PluginLockInstanceData.Data.RecoverData recoverData;
        PluginLockInstanceState pluginLockInstanceState = this.mInstanceState;
        if (pluginLockInstanceState == null || (recoverData = pluginLockInstanceState.getRecoverData()) == null) {
            return;
        }
        recoverData.setWallpaperDynamic(i, i2);
        recoverData.setWallpaperSource(i, i3);
        recoverData.setWallpaperType(i, i4);
        this.mInstanceState.updateDb();
    }

    public void setWallpaperDynamicBackupValue(int i, int i2) {
        PluginLockInstanceData.Data.RecoverData recoverData;
        PluginLockInstanceState pluginLockInstanceState = this.mInstanceState;
        if (pluginLockInstanceState == null || (recoverData = pluginLockInstanceState.getRecoverData()) == null) {
            return;
        }
        recoverData.setWallpaperDynamic(i, i2);
        this.mInstanceState.updateDb();
    }

    public void setWallpaperSourceBackupValue(int i, int i2) {
        PluginLockInstanceData.Data.RecoverData recoverData;
        PluginLockInstanceState pluginLockInstanceState = this.mInstanceState;
        if (pluginLockInstanceState == null || (recoverData = pluginLockInstanceState.getRecoverData()) == null || recoverData.getWallpaperSource(i).intValue() == i2) {
            return;
        }
        recoverData.setWallpaperSource(i, i2);
        this.mInstanceState.updateDb();
    }

    public void setWallpaperTypeBackupValue(int i, int i2) {
        PluginLockInstanceData.Data.RecoverData recoverData;
        PluginLockInstanceState pluginLockInstanceState = this.mInstanceState;
        if (pluginLockInstanceState == null || (recoverData = pluginLockInstanceState.getRecoverData()) == null || recoverData.getWallpaperType(i).intValue() == i2) {
            return;
        }
        recoverData.setWallpaperType(i, i2);
        this.mInstanceState.updateDb();
    }

    public abstract void update(DynamicLockData dynamicLockData, DynamicLockData dynamicLockData2);

    public void setInstanceState(int i, PluginLockInstanceState pluginLockInstanceState) {
        this.mInstanceState = pluginLockInstanceState;
    }

    public void setWallpaperDynamicBackupValue(int i) {
        PluginLockInstanceData.Data.RecoverData recoverData;
        PluginLockInstanceState pluginLockInstanceState = this.mInstanceState;
        if (pluginLockInstanceState == null || (recoverData = pluginLockInstanceState.getRecoverData()) == null) {
            return;
        }
        recoverData.setWallpaperDynamic(i);
        this.mInstanceState.updateDb();
    }

    public void setWallpaperBackup(int i, int i2) {
        PluginLockInstanceData.Data.RecoverData recoverData;
        PluginLockInstanceState pluginLockInstanceState = this.mInstanceState;
        if (pluginLockInstanceState == null || (recoverData = pluginLockInstanceState.getRecoverData()) == null) {
            return;
        }
        recoverData.setWallpaperSource(i);
        recoverData.setWallpaperType(i2);
        this.mInstanceState.updateDb();
    }

    public void setWallpaperSourceBackupValue(int i) {
        PluginLockInstanceData.Data.RecoverData recoverData;
        PluginLockInstanceState pluginLockInstanceState = this.mInstanceState;
        if (pluginLockInstanceState == null || (recoverData = pluginLockInstanceState.getRecoverData()) == null) {
            return;
        }
        recoverData.setWallpaperSource(i);
        this.mInstanceState.updateDb();
    }

    public void setWallpaperTypeBackupValue(int i) {
        PluginLockInstanceData.Data.RecoverData recoverData;
        PluginLockInstanceState pluginLockInstanceState = this.mInstanceState;
        if (pluginLockInstanceState == null || (recoverData = pluginLockInstanceState.getRecoverData()) == null) {
            return;
        }
        recoverData.setWallpaperType(i);
        this.mInstanceState.updateDb();
    }

    public void setWallpaperBackupValue(int i, int i2, int i3) {
        PluginLockInstanceData.Data.RecoverData recoverData;
        PluginLockInstanceState pluginLockInstanceState = this.mInstanceState;
        if (pluginLockInstanceState == null || (recoverData = pluginLockInstanceState.getRecoverData()) == null) {
            return;
        }
        recoverData.setWallpaperDynamic(i);
        recoverData.setWallpaperSource(i2);
        recoverData.setWallpaperType(i3);
        this.mInstanceState.updateDb();
    }

    public void resetAll() {
    }
}
