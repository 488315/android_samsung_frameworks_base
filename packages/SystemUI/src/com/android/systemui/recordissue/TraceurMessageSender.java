package com.android.systemui.recordissue;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.SystemProperties;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.util.Patterns;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class TraceurMessageSender {
    public final Looper backgroundLooper;
    public Messenger binder;
    public boolean isBound;
    public final TraceurMessageSender$traceurConnection$1 traceurConnection = new ServiceConnection() { // from class: com.android.systemui.recordissue.TraceurMessageSender$traceurConnection$1
        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            TraceurMessageSender.this.binder = new Messenger(iBinder);
            TraceurMessageSender.this.isBound = true;
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
            TraceurMessageSender traceurMessageSender = TraceurMessageSender.this;
            traceurMessageSender.binder = null;
            traceurMessageSender.isBound = false;
        }
    };

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class TraceurMessageHandler extends Handler {
        public final Context context;
        public final Uri screenRecord;

        public TraceurMessageHandler(Context context, Uri uri, Looper looper) {
            super(looper);
            this.context = context;
            this.screenRecord = uri;
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            Account account;
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
            Uri uri3 = this.screenRecord;
            if (uri3 != null) {
                arrayList.add(uri3);
            }
            Context context = this.context;
            String str = Build.FINGERPRINT;
            Intent intent = new Intent("android.intent.action.SEND_MULTIPLE");
            intent.addFlags(1);
            intent.addCategory("android.intent.category.DEFAULT");
            intent.setType("application/vnd.android.systrace");
            intent.putExtra("android.intent.extra.SUBJECT", ((Uri) arrayList.get(0)).getLastPathSegment());
            intent.putExtra("android.intent.extra.TEXT", (CharSequence) str);
            intent.putExtra("android.intent.extra.STREAM", new ArrayList(arrayList));
            ArrayList arrayList2 = new ArrayList();
            Iterator it = arrayList.iterator();
            while (true) {
                account = null;
                if (!it.hasNext()) {
                    break;
                }
                arrayList2.add(new ClipData.Item(Build.FINGERPRINT, null, (Uri) it.next()));
            }
            intent.setClipData(new ClipData(new ClipDescription(null, new String[]{"application/vnd.android.systrace"}), arrayList2));
            AccountManager accountManager = (AccountManager) context.getSystemService("account");
            String str2 = SystemProperties.get("sendbug.preferred.domain");
            if (!str2.startsWith("@")) {
                str2 = "@".concat(str2);
            }
            for (Account account2 : accountManager.getAccounts()) {
                if (Patterns.EMAIL_ADDRESS.matcher(account2.name).matches()) {
                    if (str2.isEmpty() || account2.name.endsWith(str2)) {
                        account = account2;
                        break;
                    }
                    account = account2;
                }
            }
            if (account != null) {
                intent.putExtra("android.intent.extra.EMAIL", new String[]{account.name});
            }
            this.context.startActivity(intent.addFlags(272629760));
        }
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.recordissue.TraceurMessageSender$traceurConnection$1] */
    public TraceurMessageSender(Looper looper) {
        this.backgroundLooper = looper;
    }

    public static void notifyTraceur$default(TraceurMessageSender traceurMessageSender, int i, Bundle bundle, Messenger messenger, int i2) {
        if ((i2 & 2) != 0) {
            bundle = new Bundle();
        }
        if ((i2 & 4) != 0) {
            messenger = null;
        }
        traceurMessageSender.getClass();
        try {
            Messenger messenger2 = traceurMessageSender.binder;
            Intrinsics.checkNotNull(messenger2);
            Message obtain = Message.obtain();
            obtain.what = i;
            obtain.setData(bundle);
            obtain.replyTo = messenger;
            messenger2.send(obtain);
        } catch (Exception e) {
            Log.e("TraceurMessageSender", "failed to notify Traceur", e);
        }
    }
}
