package com.android.settingslib.inputmethod;

import android.content.ContentResolver;
import android.content.Context;
import android.util.SparseArray;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class InputMethodSettingValuesWrapper {
    public final ArrayList mMethodList;
    public static final Object sInstanceMapLock = new Object();
    public static final SparseArray sInstanceMap = new SparseArray();

    public InputMethodSettingValuesWrapper(Context context) {
        ArrayList arrayList = new ArrayList();
        this.mMethodList = arrayList;
        ContentResolver contentResolver = context.getContentResolver();
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(InputMethodManager.class);
        arrayList.clear();
        List inputMethodListAsUser = inputMethodManager.getInputMethodListAsUser(contentResolver.getUserId(), 1);
        for (int i = 0; i < inputMethodListAsUser.size(); i++) {
            InputMethodInfo inputMethodInfo = (InputMethodInfo) inputMethodListAsUser.get(i);
            if (!inputMethodInfo.isVirtualDeviceOnly()) {
                this.mMethodList.add(inputMethodInfo);
            }
        }
    }
}
