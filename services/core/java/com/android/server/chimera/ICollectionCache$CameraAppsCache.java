package com.android.server.chimera;

import android.content.SharedPreferences;
import android.util.ArraySet;

import java.util.Arrays;
import java.util.Set;

public final class ICollectionCache$CameraAppsCache {
    public final /* synthetic */ int $r8$classId;
    public final Set apps = new ArraySet();
    public SharedPreferences mSharedPreferences;
    public final SystemRepository mSystemRepository;

    public ICollectionCache$CameraAppsCache(SystemRepository systemRepository, int i) {
        this.$r8$classId = i;
        this.mSystemRepository = systemRepository;
        init();
    }

    public final boolean contains(String str) {
        init();
        return ((ArraySet) this.apps).contains(str);
    }

    public final String getKey() {
        switch (this.$r8$classId) {
            case 0:
                return "CameraAppsCache";
            default:
                return "BigGameAppsCache";
        }
    }

    public final boolean init() {
        SharedPreferences sharedPreferences;
        if (this.mSharedPreferences != null) {
            return true;
        }
        SystemRepository systemRepository = this.mSystemRepository;
        synchronized (systemRepository) {
            if (systemRepository.mSharedPreferences == null) {
                try {
                    systemRepository.mSharedPreferences =
                            systemRepository.mContext.getSharedPreferences(
                                    SystemRepository.convertToChimeraTag("SystemRepositoryDefault"),
                                    0);
                } catch (Exception e) {
                    SystemRepository.logDebug("SystemRepositoryDefault", e.toString());
                }
            }
            sharedPreferences = systemRepository.mSharedPreferences;
        }
        this.mSharedPreferences = sharedPreferences;
        if (sharedPreferences == null) {
            return false;
        }
        String string = sharedPreferences.getString(getKey(), "");
        if (!string.isEmpty()) {
            String[] split = string.split(",");
            ((ArraySet) this.apps).addAll(Arrays.asList(split));
        }
        return true;
    }

    public final String toString() {
        return ((ArraySet) this.apps).toString();
    }

    public final void update(String str) {
        if (((ArraySet) this.apps).contains(str)) {
            return;
        }
        ((ArraySet) this.apps).add(str);
        if (init()) {
            String key = getKey();
            if (!this.mSharedPreferences.getString(key, "").isEmpty()) {
                str = String.join(",", this.apps);
            }
            SharedPreferences.Editor edit = this.mSharedPreferences.edit();
            edit.putString(key, str);
            edit.commit();
        }
    }
}
