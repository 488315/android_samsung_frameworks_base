package com.android.server.accounts;

import android.content.ContentValues;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Slog;

import java.io.File;

public abstract /* synthetic */ class AccountManagerService$$ExternalSyntheticOutline0 {
    public static ContentValues m(String str, String str2) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(str, str2);
        return contentValues;
    }

    /* renamed from: m, reason: collision with other method in class */
    public static Bundle m142m(String str, String str2) {
        Bundle bundle = new Bundle();
        bundle.putString(str, str2);
        return bundle;
    }

    public static String m(int i, String str, String str2, String str3, String str4) {
        return str + i + str2 + str3 + str4;
    }

    public static String m(File file, String str) {
        return str + file;
    }

    public static void m(String str, RemoteException remoteException, String str2) {
        Slog.w(str2, str + remoteException);
    }
}
