package com.android.systemui.screenshot;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.android.systemui.R;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;

public final class ActionIntentCreator {
    public static final ActionIntentCreator INSTANCE = new ActionIntentCreator();

    private ActionIntentCreator() {
    }

    public static Intent createEdit(Context context, Uri uri) {
        Uri uriWithoutUserId = ContentProvider.getUriWithoutUserId(uri);
        Intent intent = new Intent("android.intent.action.EDIT");
        String string = context.getString(R.string.config_screenshotEditor);
        if (string.length() > 0) {
            intent.setComponent(ComponentName.unflattenFromString(string));
        }
        return intent.setDataAndType(uriWithoutUserId, "image/png").putExtra("edit_source", "screenshot").addFlags(1).addFlags(2).addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE).addFlags(32768);
    }

    public static Intent createShare(Uri uri, String str, String str2) {
        Uri uriWithoutUserId = ContentProvider.getUriWithoutUserId(uri);
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setDataAndType(uriWithoutUserId, "image/png");
        intent.putExtra("android.intent.extra.STREAM", uriWithoutUserId);
        intent.setClipData(new ClipData(new ClipDescription("content", new String[]{"text/plain"}), new ClipData.Item(uriWithoutUserId)));
        if (str != null) {
            intent.putExtra("android.intent.extra.SUBJECT", str);
        }
        if (str2 != null) {
            intent.putExtra("android.intent.extra.TEXT", str2);
        }
        intent.addFlags(1);
        intent.addFlags(2);
        return Intent.createChooser(intent, null).addFlags(32768).addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE).addFlags(1);
    }
}
