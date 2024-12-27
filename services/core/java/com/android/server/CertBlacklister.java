package com.android.server;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.FileUtils;
import android.provider.Settings;
import android.util.Slog;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import libcore.io.IoUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class CertBlacklister extends Binder {
    public static final String PUBKEY_PATH;
    public static final String SERIAL_PATH;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class BlacklistObserver extends ContentObserver {
        public final ContentResolver mContentResolver;
        public final String mKey;
        public final String mPath;
        public final File mTmpDir;

        public BlacklistObserver(ContentResolver contentResolver, String str, String str2) {
            super(null);
            this.mKey = str;
            this.mPath = str2;
            this.mTmpDir = new File(str2).getParentFile();
            this.mContentResolver = contentResolver;
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            super.onChange(z);
            new Thread() { // from class: com.android.server.CertBlacklister.BlacklistObserver.1
                @Override // java.lang.Thread, java.lang.Runnable
                public final void run() {
                    synchronized (BlacklistObserver.this.mTmpDir) {
                        BlacklistObserver blacklistObserver = BlacklistObserver.this;
                        String string = Settings.Secure.getString(blacklistObserver.mContentResolver, blacklistObserver.mKey);
                        if (string != null) {
                            Slog.i("CertBlacklister", "Certificate blacklist changed, updating...");
                            FileOutputStream fileOutputStream = null;
                            try {
                                try {
                                    File createTempFile = File.createTempFile("journal", "", BlacklistObserver.this.mTmpDir);
                                    createTempFile.setReadable(true, false);
                                    FileOutputStream fileOutputStream2 = new FileOutputStream(createTempFile);
                                    try {
                                        fileOutputStream2.write(string.getBytes());
                                        FileUtils.sync(fileOutputStream2);
                                        createTempFile.renameTo(new File(BlacklistObserver.this.mPath));
                                        Slog.i("CertBlacklister", "Certificate blacklist updated");
                                        IoUtils.closeQuietly(fileOutputStream2);
                                    } catch (IOException e) {
                                        e = e;
                                        fileOutputStream = fileOutputStream2;
                                        Slog.e("CertBlacklister", "Failed to write blacklist", e);
                                        IoUtils.closeQuietly(fileOutputStream);
                                    } catch (Throwable th) {
                                        th = th;
                                        fileOutputStream = fileOutputStream2;
                                        IoUtils.closeQuietly(fileOutputStream);
                                        throw th;
                                    }
                                } catch (IOException e2) {
                                    e = e2;
                                }
                            } catch (Throwable th2) {
                                th = th2;
                            }
                        }
                    }
                }
            }.start();
        }
    }

    static {
        String str = System.getenv("ANDROID_DATA") + "/misc/keychain/";
        PUBKEY_PATH = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str, "pubkey_blacklist.txt");
        SERIAL_PATH = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str, "serial_blacklist.txt");
    }

    public CertBlacklister(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        contentResolver.registerContentObserver(Settings.Secure.getUriFor("pubkey_blacklist"), true, new BlacklistObserver(contentResolver, "pubkey_blacklist", PUBKEY_PATH));
        contentResolver.registerContentObserver(Settings.Secure.getUriFor("serial_blacklist"), true, new BlacklistObserver(contentResolver, "serial_blacklist", SERIAL_PATH));
    }
}
