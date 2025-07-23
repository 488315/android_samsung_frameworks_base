package com.android.wm.shell.apptoweb;

import android.content.Intent;
import android.net.Uri;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class AppToWebUtils {
    public static final Intent GenericBrowserIntent = new Intent().setAction("android.intent.action.VIEW").addCategory("android.intent.category.BROWSABLE").setData(Uri.parse("http:"));
}
