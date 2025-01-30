package com.samsung.android.knoxguard.service.utils;

import android.net.Uri;
import android.os.SystemProperties;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public abstract class Constants {
    public static final boolean IS_SUPPORT_KGTA;
    public static final List PROTECTED_APP_OPS_LIST;
    public static final Uri KG_LOG_URI = Uri.parse("content://com.samsung.android.kgclient.statusprovider/CONTENT_LOG");
    public static final String[] strState = {"Prenormal", "Checking", "Active", "Locked", "Completed", "Error"};

    static {
        IS_SUPPORT_KGTA = SystemProperties.getInt("ro.product.first_api_level", 0) >= 30;
        ArrayList arrayList = new ArrayList();
        PROTECTED_APP_OPS_LIST = arrayList;
        arrayList.add(63);
        arrayList.add(70);
        arrayList.add(40);
        arrayList.add(11);
        arrayList.add(119);
        arrayList.add(107);
        arrayList.add(125);
    }
}
