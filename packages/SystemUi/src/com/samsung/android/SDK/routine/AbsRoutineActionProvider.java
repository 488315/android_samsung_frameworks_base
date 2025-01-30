package com.samsung.android.SDK.routine;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class AbsRoutineActionProvider extends ContentProvider {
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.content.ContentProvider
    public final Bundle call(String str, String str2, Bundle bundle) {
        char c;
        Binder.clearCallingIdentity();
        Bundle bundle2 = new Bundle();
        str.getClass();
        switch (str.hashCode()) {
            case -1961649681:
                if (str.equals("getLabelParam")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -1213955830:
                if (str.equals("getCurrentParam")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 105853491:
                if (str.equals("onAct")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 2073378034:
                if (str.equals("isValid")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        if (c == 0) {
            bundle.getString("tag");
            String string = bundle.getString("param");
            bundle.getBoolean("is_negative");
            bundle2.putString("label_params", getLabelParam(string));
            return bundle2;
        }
        if (c == 1) {
            bundle.getString("tag");
            getCurrentParam();
            bundle2.putString("return", null);
            return bundle2;
        }
        if (c != 2) {
            if (c != 3) {
                return super.call(str, str2, bundle);
            }
            bundle.getString("tag");
            bundle.getString("param");
            bundle.getBoolean("is_negative");
            bundle2.putString("return", String.valueOf(0));
            return bundle2;
        }
        String string2 = bundle.getString("tag");
        String string3 = bundle.getString("param");
        bundle.getBoolean("is_negative");
        bundle.getBoolean("is_recovery");
        if (bundle.getStringArrayList("all_param") != null) {
            bundle2.putString("return", String.valueOf(1));
        } else {
            onAct(string2, string3);
            bundle2.putString("return", String.valueOf(0));
        }
        return bundle2;
    }

    @Override // android.content.ContentProvider
    public final int delete(Uri uri, String str, String[] strArr) {
        return 0;
    }

    public abstract void getCurrentParam();

    public String getLabelParam(String str) {
        return null;
    }

    @Override // android.content.ContentProvider
    public final String getType(Uri uri) {
        return null;
    }

    @Override // android.content.ContentProvider
    public final Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    public abstract void onAct(String str, String str2);

    @Override // android.content.ContentProvider
    public final boolean onCreate() {
        return true;
    }

    @Override // android.content.ContentProvider
    public final Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        return null;
    }

    @Override // android.content.ContentProvider
    public final int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        return 0;
    }
}
