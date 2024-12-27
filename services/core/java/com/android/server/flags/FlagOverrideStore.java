package com.android.server.flags;

import android.database.Cursor;
import android.provider.Settings;

import com.android.server.BootReceiver$$ExternalSyntheticOutline0;

import java.util.HashMap;
import java.util.Map;

public class FlagOverrideStore {
    public DynamicFlagBinderDelegate$$ExternalSyntheticLambda1 mCallback;
    public final GlobalSettingsProxy mSettingsProxy;

    public FlagOverrideStore(GlobalSettingsProxy globalSettingsProxy) {
        this.mSettingsProxy = globalSettingsProxy;
    }

    public static String getPropName(String str, String str2) {
        return BootReceiver$$ExternalSyntheticOutline0.m("flag|", str, ".", str2);
    }

    public void erase(String str, String str2) {
        set(str, str2, null);
    }

    public String get(String str, String str2) {
        String propName = getPropName(str, str2);
        GlobalSettingsProxy globalSettingsProxy = this.mSettingsProxy;
        return Settings.Global.getStringForUser(
                globalSettingsProxy.mContentResolver,
                propName,
                globalSettingsProxy.mContentResolver.getUserId());
    }

    public final Map getFlagsForNamespace(String str) {
        String string;
        Cursor query =
                this.mSettingsProxy.mContentResolver.query(
                        Settings.Global.CONTENT_URI,
                        new String[] {"name", "value"},
                        null,
                        null,
                        null);
        if (query == null) {
            return Map.of();
        }
        HashMap hashMap = new HashMap();
        while (query.moveToNext()) {
            String string2 = query.getString(0);
            if (string2.startsWith("flag|")
                    && string2.indexOf(".", 5) >= 0
                    && (string = query.getString(1)) != null
                    && !string.isEmpty()) {
                String substring = string2.substring(5, string2.indexOf("."));
                if (str == null || str.equals(substring)) {
                    String substring2 = string2.substring(string2.indexOf(".") + 1);
                    hashMap.putIfAbsent(substring, new HashMap());
                    ((Map) hashMap.get(substring)).put(substring2, string);
                }
            }
        }
        query.close();
        return hashMap;
    }

    public void set(String str, String str2, String str3) {
        String propName = getPropName(str, str2);
        GlobalSettingsProxy globalSettingsProxy = this.mSettingsProxy;
        Settings.Global.putStringForUser(
                globalSettingsProxy.mContentResolver,
                propName,
                str3,
                globalSettingsProxy.mContentResolver.getUserId());
        this.mCallback.onFlagChanged(str, str2, str3);
    }
}
