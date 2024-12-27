package com.android.server.enterprise;

import android.R;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.widget.Toast;

import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class RestrictionToastManager {
    public static RestrictionToastHandler mRestrictionToastHandler;
    public static final ArrayList mRecentlyDisplayedMsgQueue = new ArrayList();
    public static Context mContext = null;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class RestrictionToastHandler extends Handler {
        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            String string;
            int i = message.what;
            if (i == 1) {
                RestrictionToastManager.mRecentlyDisplayedMsgQueue.remove(message.obj);
                Log.d(
                        "RestrictionToastManager",
                        "Removed message from recently displayed queue: " + message);
                return;
            }
            if (i != 2
                    || (string = message.getData().getString("message")) == null
                    || RestrictionToastManager.mContext == null) {
                return;
            }
            try {
                Toast.makeText(
                                new ContextThemeWrapper(
                                        RestrictionToastManager.mContext,
                                        R.style.Theme.DeviceDefault.Light),
                                string,
                                1)
                        .show();
            } catch (Exception e) {
                RestrictionToastManager$RestrictionToastHandler$$ExternalSyntheticOutline0.m(
                        e, new StringBuilder("displayToast fail : "), "RestrictionToastManager");
            }
        }
    }

    public static void show(int i) {
        Context context;
        if (i < 0 || (context = mContext) == null) {
            return;
        }
        try {
            show(context.getString(i));
        } catch (Resources.NotFoundException unused) {
            Log.e(
                    "RestrictionToastManager",
                    "Resource Id not found: will not display any restriction message toast");
        }
    }

    public static synchronized void show(String str) {
        synchronized (RestrictionToastManager.class) {
            if (str != null) {
                if (mContext != null) {
                    ArrayList arrayList = mRecentlyDisplayedMsgQueue;
                    if (arrayList.contains(str)) {
                        return;
                    }
                    Message message = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putString("message", str);
                    message.setData(bundle);
                    message.what = 2;
                    mRestrictionToastHandler.sendMessage(message);
                    arrayList.add(str);
                    Log.d(
                            "RestrictionToastManager",
                            "Added message to recently displayed queue: ".concat(str));
                    mRestrictionToastHandler.sendMessageDelayed(
                            Message.obtain(mRestrictionToastHandler, 1, str), 3500L);
                }
            }
        }
    }
}
