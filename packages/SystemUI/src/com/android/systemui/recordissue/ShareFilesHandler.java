package com.android.systemui.recordissue;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemProperties;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.util.Patterns;
import com.android.systemui.settings.UserContextProvider;
import com.android.systemui.settings.UserTrackerImpl;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public final class ShareFilesHandler extends Handler {
    public final List screenRecordingUris;
    public final UserContextProvider userContextProvider;

    public ShareFilesHandler(List<? extends Uri> list, UserContextProvider userContextProvider, Looper looper) {
        super(looper);
        this.screenRecordingUris = list;
        this.userContextProvider = userContextProvider;
    }

    @Override // android.os.Handler
    public final void handleMessage(Message message) {
        if (2 != message.what) {
            throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(message.what, "received unknown msg.what: "));
        }
        Uri uri = (Uri) message.getData().getParcelable("com.android.traceur.PERFETTO", Uri.class);
        Uri uri2 = (Uri) message.getData().getParcelable("com.android.traceur.WINSCOPE_ZIP", Uri.class);
        ArrayList arrayList = new ArrayList();
        if (uri != null) {
            arrayList.add(uri);
        }
        if (uri2 != null) {
            arrayList.add(uri2);
        }
        Iterator it = this.screenRecordingUris.iterator();
        while (it.hasNext()) {
            arrayList.add((Uri) it.next());
        }
        UserTrackerImpl userTrackerImpl = (UserTrackerImpl) this.userContextProvider;
        Context userContext = userTrackerImpl.getUserContext();
        String str = Build.FINGERPRINT;
        Intent intent = new Intent("android.intent.action.SEND_MULTIPLE");
        intent.addFlags(1);
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setType("application/vnd.android.systrace");
        Account account = null;
        if (arrayList.isEmpty()) {
            Log.e("Traceur", "There are no URIs to attach to this send intent. An error may have occurred while tracing or retrieving trace files.");
        } else {
            intent.putExtra("android.intent.extra.SUBJECT", ((Uri) arrayList.get(0)).getLastPathSegment());
            intent.putExtra("android.intent.extra.STREAM", new ArrayList(arrayList));
            ArrayList arrayList2 = new ArrayList();
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                arrayList2.add(new ClipData.Item(Build.FINGERPRINT, null, (Uri) obj));
            }
            intent.setClipData(new ClipData(new ClipDescription(null, new String[]{"application/vnd.android.systrace"}), arrayList2));
        }
        intent.putExtra("android.intent.extra.TEXT", (CharSequence) str);
        AccountManager accountManager = (AccountManager) userContext.getSystemService("account");
        String strConcat = SystemProperties.get("sendbug.preferred.domain");
        if (!strConcat.startsWith("@")) {
            strConcat = "@".concat(strConcat);
        }
        for (Account account2 : accountManager.getAccounts()) {
            if (Patterns.EMAIL_ADDRESS.matcher(account2.name).matches()) {
                if (strConcat.isEmpty() || account2.name.endsWith(strConcat)) {
                    account = account2;
                    break;
                }
                account = account2;
            }
        }
        if (account != null) {
            intent.putExtra("android.intent.extra.EMAIL", new String[]{account.name});
        }
        userTrackerImpl.getUserContext().startActivity(intent.addFlags(272629760));
    }
}
