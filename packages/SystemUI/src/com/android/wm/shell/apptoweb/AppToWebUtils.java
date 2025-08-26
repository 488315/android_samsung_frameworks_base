package com.android.wm.shell.apptoweb;

import android.content.Intent;
import android.net.Uri;

/* loaded from: classes3.dex */
public abstract class AppToWebUtils {
    public static final Intent GenericBrowserIntent = new Intent().setAction("android.intent.action.VIEW").addCategory("android.intent.category.BROWSABLE").setData(Uri.parse("http:"));
}
