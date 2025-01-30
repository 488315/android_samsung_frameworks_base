package com.samsung.android.server.audio;

import java.util.ArrayList;
import java.util.Hashtable;

/* loaded from: classes2.dex */
public class AppCategorizer {
    public final Hashtable appList;
    public AudioSettingsHelper mSettingsHelper;

    public AppCategorizer(AudioSettingsHelper audioSettingsHelper) {
        Hashtable hashtable = new Hashtable();
        this.appList = hashtable;
        this.mSettingsHelper = audioSettingsHelper;
        synchronized (hashtable) {
            hashtable.putAll(this.mSettingsHelper.getPackageList());
        }
    }

    public void putPackage(int i, String str) {
        if (checkExist(str)) {
            return;
        }
        this.appList.put(Integer.valueOf(i), str);
        this.mSettingsHelper.putPackage(i, str);
    }

    public void removePackage(int i) {
        synchronized (this.appList) {
            this.appList.remove(Integer.valueOf(i));
            this.mSettingsHelper.removePackage(i);
        }
    }

    public String[] getSelectedPackages() {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(this.appList.values());
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    public boolean checkExist(String str) {
        return this.appList.containsValue(str);
    }
}
