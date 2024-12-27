package com.samsung.accessory.manager.authentication;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.Debug;
import android.provider.Settings;
import android.util.Log;

import com.samsung.accessory.manager.authentication.cover.CoverAuthenticator;

import java.io.File;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class CertBlocklister extends Binder {
    public static final String PUBKEY_PATH;
    public static BlocklistObserver mBlocklistObserver;
    public static CertBlocklistListener mCertBlocklistListener;
    public static boolean mIsBlocked;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AuthenticationSettingObserver extends ContentObserver {
        public final ContentResolver mContentResolver;
        public final String mKey;

        public AuthenticationSettingObserver(ContentResolver contentResolver) {
            super(null);
            this.mKey = "cover_authentication_blocked";
            this.mContentResolver = contentResolver;
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            super.onChange(z);
            String string = Settings.Secure.getString(this.mContentResolver, this.mKey);
            if ("true".equals(string)) {
                CertBlocklister.mIsBlocked = true;
                ((CoverAuthenticator) CertBlocklister.mCertBlocklistListener)
                        .mCoverAuthHandler.sendEmptyMessage(6);
            } else if ("false".equals(string)) {
                CertBlocklister.mIsBlocked = false;
                ((CoverAuthenticator) CertBlocklister.mCertBlocklistListener)
                        .startAuthentication(0L, true);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class BlocklistObserver extends ContentObserver {
        public static final boolean DBG = Debug.semIsProductDev();
        public String mBlocklist;
        public final ContentResolver mContentResolver;
        public final String mKey;
        public final String mPath;
        public final File mTmpDir;

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        /* renamed from: com.samsung.accessory.manager.authentication.CertBlocklister$BlocklistObserver$1, reason: invalid class name */
        public final class AnonymousClass1 extends Thread {
            public final /* synthetic */ int $r8$classId;
            public final /* synthetic */ BlocklistObserver this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(BlocklistObserver blocklistObserver, int i) {
                super("BlocklistReader");
                this.$r8$classId = i;
                switch (i) {
                    case 1:
                        this.this$0 = blocklistObserver;
                        super("BlocklistUpdater");
                        break;
                    default:
                        this.this$0 = blocklistObserver;
                        break;
                }
            }

            /* JADX WARN: Removed duplicated region for block: B:33:0x0099 A[Catch: all -> 0x002c, TryCatch #5 {all -> 0x002c, blocks: (B:6:0x000a, B:8:0x0016, B:10:0x0020, B:12:0x0024, B:13:0x002f, B:18:0x0034, B:20:0x003c, B:30:0x0084, B:31:0x0095, B:33:0x0099, B:41:0x00aa, B:42:0x00ad, B:37:0x0092, B:45:0x00ae), top: B:5:0x000a }] */
            @Override // java.lang.Thread, java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final void run() {
                /*
                    Method dump skipped, instructions count: 262
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException(
                        "Method not decompiled:"
                            + " com.samsung.accessory.manager.authentication.CertBlocklister.BlocklistObserver.AnonymousClass1.run():void");
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public BlocklistObserver(ContentResolver contentResolver) {
            super(null);
            String str = CertBlocklister.PUBKEY_PATH;
            this.mKey = "cover_pubkey_blocklist";
            this.mPath = str;
            this.mTmpDir = new File(str).getParentFile();
            this.mContentResolver = contentResolver;
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            super.onChange(z);
            new AnonymousClass1(this, 1).start();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface CertBlocklistListener {}

    static {
        Debug.semIsProductDev();
        PUBKEY_PATH =
                ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(
                        System.getenv("ANDROID_DATA") + "/misc/saccessory_manager/keychain/",
                        "cover_pubkey_blocklist.txt");
        mIsBlocked = false;
    }

    public static boolean isThisKeyBlocklisted(String str) {
        boolean contains;
        BlocklistObserver blocklistObserver = mBlocklistObserver;
        synchronized (blocklistObserver.mTmpDir) {
            if (str != null) {
                try {
                    if (blocklistObserver.mBlocklist != null) {
                        Log.i("SAccessoryManager_CertBlocklister", "This key is in blocklist");
                        contains = blocklistObserver.mBlocklist.contains(str.concat(","));
                    }
                } finally {
                }
            }
            contains = false;
        }
        return contains;
    }
}
