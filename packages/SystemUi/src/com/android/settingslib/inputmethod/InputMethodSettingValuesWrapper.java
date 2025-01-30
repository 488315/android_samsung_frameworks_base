package com.android.settingslib.inputmethod;

import android.content.ContentResolver;
import android.content.Context;
import android.util.SparseArray;
import android.view.inputmethod.InputMethodManager;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class InputMethodSettingValuesWrapper {
    public static final Object sInstanceMapLock = new Object();
    public static final SparseArray sInstanceMap = new SparseArray();

    private InputMethodSettingValuesWrapper(Context context) {
        ArrayList arrayList = new ArrayList();
        ContentResolver contentResolver = context.getContentResolver();
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(InputMethodManager.class);
        arrayList.clear();
        arrayList.addAll(inputMethodManager.getInputMethodListAsUser(contentResolver.getUserId(), 1));
    }

    public static void getInstance(Context context) {
        int userId = context.getUserId();
        synchronized (sInstanceMapLock) {
            SparseArray sparseArray = sInstanceMap;
            if (sparseArray.size() == 0) {
                sparseArray.put(userId, new InputMethodSettingValuesWrapper(context));
            } else if (sparseArray.indexOfKey(userId) >= 0) {
            } else {
                sparseArray.put(context.getUserId(), new InputMethodSettingValuesWrapper(context));
            }
        }
    }
}
