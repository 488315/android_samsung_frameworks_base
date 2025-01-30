package com.samsung.accessory.manager.authentication;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.os.Binder;
import android.os.Debug;
import android.os.FileUtils;
import android.provider.Settings;
import android.util.Log;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import libcore.io.IoUtils;

/* loaded from: classes.dex */
public class CertBlocklister extends Binder {
    public static final String BLOCKLIST_ROOT;
    public static final boolean DBG = Debug.semIsProductDev();
    public static final String PUBKEY_PATH;
    public static BlocklistObserver mBlocklistObserver;
    public static CertBlocklistListener mCertBlocklistListener;
    public static boolean mIsBlocked;

    public interface CertBlocklistListener {
        void onAuthenticationBlocked(boolean z);

        void onCertBlocklistChanged();
    }

    static {
        String str = System.getenv("ANDROID_DATA") + "/misc/saccessory_manager/keychain/";
        BLOCKLIST_ROOT = str;
        PUBKEY_PATH = str + "cover_pubkey_blocklist.txt";
        mIsBlocked = false;
    }

    public void setCertBlocklistListener(CertBlocklistListener certBlocklistListener) {
        mCertBlocklistListener = certBlocklistListener;
    }

    public boolean isAuthenticationBlocked() {
        return mIsBlocked;
    }

    public boolean isThisKeyBlocklisted(String str) {
        return mBlocklistObserver.isThisKeyBlocklisted(str);
    }

    public void readFile() {
        mBlocklistObserver.readFile();
    }

    public class BlocklistObserver extends ContentObserver {
        public static final boolean DBG = Debug.semIsProductDev();
        public String mBlocklist;
        public final ContentResolver mContentResolver;
        public final String mKey;
        public final String mName;
        public final String mPath;
        public final File mTmpDir;

        public BlocklistObserver(String str, String str2, String str3, ContentResolver contentResolver) {
            super(null);
            this.mKey = str;
            this.mName = str2;
            this.mPath = str3;
            this.mTmpDir = new File(str3).getParentFile();
            this.mContentResolver = contentResolver;
        }

        public boolean isThisKeyBlocklisted(String str) {
            synchronized (this.mTmpDir) {
                if (str != null) {
                    if (this.mBlocklist != null) {
                        Log.i("SAccessoryManager_CertBlocklister", "This key is in blocklist");
                        return this.mBlocklist.contains(str + ",");
                    }
                }
                return false;
            }
        }

        public void readFile() {
            new Thread("BlocklistReader") { // from class: com.samsung.accessory.manager.authentication.CertBlocklister.BlocklistObserver.1
                @Override // java.lang.Thread, java.lang.Runnable
                public void run() {
                    synchronized (BlocklistObserver.this.mTmpDir) {
                        if (BlocklistObserver.DBG) {
                            Log.d("SAccessoryManager_CertBlocklister", "Read a blocklist from file..");
                        }
                        try {
                            try {
                                BlocklistObserver.this.mBlocklist = IoUtils.readFileAsString(CertBlocklister.PUBKEY_PATH);
                            } catch (FileNotFoundException unused) {
                                Log.i("SAccessoryManager_CertBlocklister", "File does not exist");
                            }
                        } catch (IOException e) {
                            Log.e("SAccessoryManager_CertBlocklister", "Failed to read list", e);
                        }
                        if (BlocklistObserver.DBG) {
                            Log.d("SAccessoryManager_CertBlocklister", "mBlocklist = " + BlocklistObserver.this.mBlocklist);
                        }
                    }
                }
            }.start();
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            super.onChange(z);
            writeBlocklist();
        }

        public String getValue() {
            return Settings.Secure.getString(this.mContentResolver, this.mKey);
        }

        public final void writeBlocklist() {
            new Thread("BlocklistUpdater") { // from class: com.samsung.accessory.manager.authentication.CertBlocklister.BlocklistObserver.2
                /* JADX WARN: Removed duplicated region for block: B:30:0x00a0 A[Catch: all -> 0x00ae, TryCatch #4 {, blocks: (B:4:0x0007, B:6:0x000f, B:8:0x001b, B:10:0x0021, B:11:0x0028, B:15:0x002c, B:17:0x0037, B:27:0x0083, B:28:0x009a, B:30:0x00a0, B:38:0x00a8, B:39:0x00ab, B:34:0x0097, B:42:0x00ac), top: B:3:0x0007 }] */
                @Override // java.lang.Thread, java.lang.Runnable
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public void run() {
                    File createTempFile;
                    FileOutputStream fileOutputStream;
                    synchronized (BlocklistObserver.this.mTmpDir) {
                        String value = BlocklistObserver.this.getValue();
                        if (value != null && value.equals(BlocklistObserver.this.mBlocklist)) {
                            if (BlocklistObserver.DBG) {
                                Log.d("SAccessoryManager_CertBlocklister", "Certificate blocklist not changed, return...");
                            }
                            return;
                        }
                        if (value != null) {
                            BlocklistObserver.this.mBlocklist = value;
                            if (BlocklistObserver.DBG) {
                                Log.d("SAccessoryManager_CertBlocklister", "Certificate blocklist changed, updating...");
                            }
                            FileOutputStream fileOutputStream2 = null;
                            try {
                                try {
                                    createTempFile = File.createTempFile("journal", "", BlocklistObserver.this.mTmpDir);
                                    createTempFile.setReadable(true, false);
                                    fileOutputStream = new FileOutputStream(createTempFile);
                                } catch (IOException e) {
                                    e = e;
                                }
                            } catch (Throwable th) {
                                th = th;
                            }
                            try {
                                fileOutputStream.write(BlocklistObserver.this.mBlocklist.getBytes());
                                FileUtils.sync(fileOutputStream);
                                createTempFile.renameTo(new File(BlocklistObserver.this.mPath));
                                if (BlocklistObserver.DBG) {
                                    Log.d("SAccessoryManager_CertBlocklister", "Certificate blocklist updated");
                                }
                                IoUtils.closeQuietly(fileOutputStream);
                            } catch (IOException e2) {
                                e = e2;
                                fileOutputStream2 = fileOutputStream;
                                Log.e("SAccessoryManager_CertBlocklister", "Failed to write list", e);
                                IoUtils.closeQuietly(fileOutputStream2);
                                if (CertBlocklister.mCertBlocklistListener != null) {
                                }
                            } catch (Throwable th2) {
                                th = th2;
                                fileOutputStream2 = fileOutputStream;
                                IoUtils.closeQuietly(fileOutputStream2);
                                throw th;
                            }
                            if (CertBlocklister.mCertBlocklistListener != null) {
                                CertBlocklister.mCertBlocklistListener.onCertBlocklistChanged();
                            }
                        }
                    }
                }
            }.start();
        }
    }

    public class AuthenticationSettingObserver extends ContentObserver {
        public final ContentResolver mContentResolver;
        public final String mKey;

        public AuthenticationSettingObserver(String str, ContentResolver contentResolver) {
            super(null);
            this.mKey = str;
            this.mContentResolver = contentResolver;
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            super.onChange(z);
            String value = getValue();
            if ("true".equals(value)) {
                CertBlocklister.mIsBlocked = true;
                CertBlocklister.mCertBlocklistListener.onAuthenticationBlocked(true);
            } else if ("false".equals(value)) {
                CertBlocklister.mIsBlocked = false;
                CertBlocklister.mCertBlocklistListener.onAuthenticationBlocked(false);
            }
        }

        public String getValue() {
            return Settings.Secure.getString(this.mContentResolver, this.mKey);
        }
    }

    public CertBlocklister(Context context) {
        registerObservers(context.getContentResolver());
    }

    public final BlocklistObserver buildPubkeyObserver(ContentResolver contentResolver) {
        return new BlocklistObserver("cover_pubkey_blocklist", "pubkey", PUBKEY_PATH, contentResolver);
    }

    public final AuthenticationSettingObserver buildAuthenticationSettingObserver(ContentResolver contentResolver) {
        return new AuthenticationSettingObserver("cover_authentication_blocked", contentResolver);
    }

    public final void registerObservers(ContentResolver contentResolver) {
        mBlocklistObserver = buildPubkeyObserver(contentResolver);
        contentResolver.registerContentObserver(Settings.Secure.getUriFor("cover_pubkey_blocklist"), true, mBlocklistObserver);
        contentResolver.registerContentObserver(Settings.Secure.getUriFor("cover_authentication_blocked"), true, buildAuthenticationSettingObserver(contentResolver));
    }
}
