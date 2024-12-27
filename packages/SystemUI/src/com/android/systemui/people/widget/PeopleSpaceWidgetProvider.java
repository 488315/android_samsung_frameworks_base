package com.android.systemui.people.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.UserHandle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import com.android.systemui.people.PeopleSpaceUtils;
import com.android.systemui.people.SharedPreferencesHelper;
import com.android.systemui.people.widget.PeopleSpaceWidgetManager;
import com.android.systemui.popup.util.PopupUIUtil;
import com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class PeopleSpaceWidgetProvider extends AppWidgetProvider {
    public PeopleSpaceWidgetManager mPeopleSpaceWidgetManager;

    public PeopleSpaceWidgetProvider(PeopleSpaceWidgetManager peopleSpaceWidgetManager) {
        this.mPeopleSpaceWidgetManager = peopleSpaceWidgetManager;
    }

    public final void ensurePeopleSpaceWidgetManagerInitialized() {
        PeopleSpaceWidgetManager peopleSpaceWidgetManager = this.mPeopleSpaceWidgetManager;
        synchronized (peopleSpaceWidgetManager.mLock) {
            try {
                if (!peopleSpaceWidgetManager.mRegisteredReceivers) {
                    IntentFilter intentFilter = new IntentFilter();
                    intentFilter.addAction("android.app.action.INTERRUPTION_FILTER_CHANGED");
                    intentFilter.addAction(PopupUIUtil.ACTION_BOOT_COMPLETED);
                    intentFilter.addAction("android.intent.action.LOCALE_CHANGED");
                    intentFilter.addAction("android.intent.action.MANAGED_PROFILE_AVAILABLE");
                    intentFilter.addAction("android.intent.action.PACKAGES_SUSPENDED");
                    intentFilter.addAction("android.intent.action.PACKAGES_UNSUSPENDED");
                    intentFilter.addAction("android.intent.action.MANAGED_PROFILE_AVAILABLE");
                    intentFilter.addAction("android.intent.action.MANAGED_PROFILE_UNAVAILABLE");
                    intentFilter.addAction("android.intent.action.USER_UNLOCKED");
                    peopleSpaceWidgetManager.mBroadcastDispatcher.registerReceiver(peopleSpaceWidgetManager.mBaseBroadcastReceiver, intentFilter, null, UserHandle.ALL);
                    IntentFilter intentFilter2 = new IntentFilter("android.intent.action.PACKAGE_REMOVED");
                    intentFilter2.addAction("android.intent.action.PACKAGE_ADDED");
                    intentFilter2.addDataScheme("package");
                    peopleSpaceWidgetManager.mContext.registerReceiver(peopleSpaceWidgetManager.mBaseBroadcastReceiver, intentFilter2);
                    IntentFilter intentFilter3 = new IntentFilter(PopupUIUtil.ACTION_BOOT_COMPLETED);
                    intentFilter3.setPriority(1000);
                    peopleSpaceWidgetManager.mContext.registerReceiver(peopleSpaceWidgetManager.mBaseBroadcastReceiver, intentFilter3);
                    peopleSpaceWidgetManager.mRegisteredReceivers = true;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // android.appwidget.AppWidgetProvider
    public final void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int i, Bundle bundle) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, i, bundle);
        ensurePeopleSpaceWidgetManagerInitialized();
        PeopleSpaceWidgetManager peopleSpaceWidgetManager = this.mPeopleSpaceWidgetManager;
        peopleSpaceWidgetManager.getClass();
        String string = bundle.getString("package_name", "");
        PeopleTileKey peopleTileKey = new PeopleTileKey(bundle.getString("shortcut_id", ""), bundle.getInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_USER_ID, -1), string);
        if (PeopleTileKey.isValid(peopleTileKey)) {
            AppWidgetManager appWidgetManager2 = peopleSpaceWidgetManager.mAppWidgetManager;
            PeopleTileKey peopleTileKey2 = PeopleSpaceUtils.EMPTY_KEY;
            Bundle appWidgetOptions = appWidgetManager2.getAppWidgetOptions(i);
            appWidgetOptions.putString("shortcut_id", peopleTileKey2.mShortcutId);
            appWidgetOptions.putInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_USER_ID, peopleTileKey2.mUserId);
            appWidgetOptions.putString("package_name", peopleTileKey2.mPackageName);
            appWidgetManager2.updateAppWidgetOptions(i, appWidgetOptions);
            peopleSpaceWidgetManager.addNewWidget(i, peopleTileKey);
        }
        peopleSpaceWidgetManager.mBgExecutor.execute(new PeopleSpaceWidgetManager$$ExternalSyntheticLambda5(0, peopleSpaceWidgetManager, new int[]{i}));
    }

    @Override // android.appwidget.AppWidgetProvider
    public final void onDeleted(Context context, int[] iArr) {
        super.onDeleted(context, iArr);
        ensurePeopleSpaceWidgetManagerInitialized();
        this.mPeopleSpaceWidgetManager.deleteWidgets(iArr);
    }

    @Override // android.appwidget.AppWidgetProvider
    public final void onRestored(Context context, int[] iArr, int[] iArr2) {
        super.onRestored(context, iArr, iArr2);
        ensurePeopleSpaceWidgetManagerInitialized();
        PeopleSpaceWidgetManager peopleSpaceWidgetManager = this.mPeopleSpaceWidgetManager;
        peopleSpaceWidgetManager.getClass();
        HashMap hashMap = new HashMap();
        for (int i = 0; i < iArr.length; i++) {
            hashMap.put(String.valueOf(iArr[i]), String.valueOf(iArr2[i]));
        }
        HashMap hashMap2 = new HashMap();
        for (Map.Entry entry : hashMap.entrySet()) {
            String valueOf = String.valueOf(entry.getKey());
            String valueOf2 = String.valueOf(entry.getValue());
            if (!valueOf.equals(valueOf2)) {
                SharedPreferences sharedPreferences = peopleSpaceWidgetManager.mContext.getSharedPreferences(valueOf, 0);
                PeopleTileKey peopleTileKey = new PeopleTileKey(sharedPreferences.getString("shortcut_id", null), sharedPreferences.getInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_USER_ID, -1), sharedPreferences.getString("package_name", null));
                if (PeopleTileKey.isValid(peopleTileKey)) {
                    hashMap2.put(valueOf2, peopleTileKey);
                    SharedPreferences.Editor edit = sharedPreferences.edit();
                    edit.clear();
                    edit.apply();
                }
            }
        }
        for (Map.Entry entry2 : hashMap2.entrySet()) {
            SharedPreferencesHelper.setPeopleTileKey(peopleSpaceWidgetManager.mContext.getSharedPreferences((String) entry2.getKey(), 0), (PeopleTileKey) entry2.getValue());
        }
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(peopleSpaceWidgetManager.mContext);
        SharedPreferences.Editor edit2 = defaultSharedPreferences.edit();
        for (Map.Entry<String, ?> entry3 : defaultSharedPreferences.getAll().entrySet()) {
            String key = entry3.getKey();
            int i2 = PeopleSpaceWidgetManager.AnonymousClass4.$SwitchMap$com$android$systemui$people$widget$PeopleBackupHelper$SharedFileEntryType[PeopleBackupHelper.getEntryType(entry3).ordinal()];
            if (i2 == 1) {
                String str = (String) hashMap.get(key);
                if (TextUtils.isEmpty(str)) {
                    MotionLayout$$ExternalSyntheticOutline0.m("Key is widget id without matching new id, skipping: ", key, "PeopleSpaceWidgetMgr");
                } else {
                    try {
                        edit2.putString(str, (String) entry3.getValue());
                    } catch (Exception e) {
                        Log.e("PeopleSpaceWidgetMgr", "malformed entry value: " + entry3.getValue(), e);
                    }
                    edit2.remove(key);
                }
            } else if (i2 == 2 || i2 == 3) {
                try {
                    edit2.putStringSet(key, PeopleSpaceWidgetManager.getNewWidgets((Set) entry3.getValue(), hashMap));
                } catch (Exception e2) {
                    Log.e("PeopleSpaceWidgetMgr", "malformed entry value: " + entry3.getValue(), e2);
                    edit2.remove(key);
                }
            } else if (i2 == 4) {
                Log.e("PeopleSpaceWidgetMgr", "Key not identified:" + key);
            }
        }
        edit2.apply();
        SharedPreferences sharedPreferences2 = peopleSpaceWidgetManager.mContext.getSharedPreferences("shared_follow_up", 0);
        SharedPreferences.Editor edit3 = sharedPreferences2.edit();
        for (Map.Entry<String, ?> entry4 : sharedPreferences2.getAll().entrySet()) {
            String key2 = entry4.getKey();
            try {
                edit3.putStringSet(key2, PeopleSpaceWidgetManager.getNewWidgets((Set) entry4.getValue(), hashMap));
            } catch (Exception e3) {
                Log.e("PeopleSpaceWidgetMgr", "malformed entry value: " + entry4.getValue(), e3);
                edit3.remove(key2);
            }
        }
        edit3.apply();
        int[] appWidgetIds = peopleSpaceWidgetManager.mAppWidgetManager.getAppWidgetIds(new ComponentName(peopleSpaceWidgetManager.mContext, (Class<?>) PeopleSpaceWidgetProvider.class));
        Bundle bundle = new Bundle();
        bundle.putBoolean("appWidgetRestoreCompleted", true);
        for (int i3 : appWidgetIds) {
            peopleSpaceWidgetManager.mAppWidgetManager.updateAppWidgetOptions(i3, bundle);
        }
        peopleSpaceWidgetManager.mBgExecutor.execute(new PeopleSpaceWidgetManager$$ExternalSyntheticLambda5(0, peopleSpaceWidgetManager, appWidgetIds));
    }

    @Override // android.appwidget.AppWidgetProvider
    public final void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] iArr) {
        super.onUpdate(context, appWidgetManager, iArr);
        ensurePeopleSpaceWidgetManagerInitialized();
        PeopleSpaceWidgetManager peopleSpaceWidgetManager = this.mPeopleSpaceWidgetManager;
        peopleSpaceWidgetManager.mBgExecutor.execute(new PeopleSpaceWidgetManager$$ExternalSyntheticLambda5(0, peopleSpaceWidgetManager, iArr));
    }

    public void setPeopleSpaceWidgetManager(PeopleSpaceWidgetManager peopleSpaceWidgetManager) {
        this.mPeopleSpaceWidgetManager = peopleSpaceWidgetManager;
    }
}
