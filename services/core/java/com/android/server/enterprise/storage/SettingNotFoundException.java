package com.android.server.enterprise.storage;

import android.util.AndroidException;

public class SettingNotFoundException extends AndroidException {
    public SettingNotFoundException(String str) {
        super(str);
    }
}
