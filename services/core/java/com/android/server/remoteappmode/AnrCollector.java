package com.android.server.remoteappmode;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.ParcelFileDescriptor;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public class AnrCollector {
    public final Context mContext;
    public SharedPreferences mPrefs;

    public AnrCollector(Context context) {
        this.mContext = context;
    }

    public void getLastAnr(String str, ParcelFileDescriptor parcelFileDescriptor) {
        File lastAnrFile = getLastAnrFile(str);
        Log.m83i("AnrCollector", "gatLastAnr - lastAnrFile : " + lastAnrFile);
        new WriteANRInfoThread(lastAnrFile, parcelFileDescriptor).start();
    }

    public final File getLastAnrFile(final String str) {
        File file = new File("/data/anr");
        if (file.exists() && file.isDirectory()) {
            File[] listFiles = file.listFiles();
            r2 = listFiles != null ? (File) Arrays.stream(listFiles).filter(new Predicate() { // from class: com.android.server.remoteappmode.AnrCollector$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean isAnrFileModifiedLater;
                    isAnrFileModifiedLater = AnrCollector.this.isAnrFileModifiedLater((File) obj);
                    return isAnrFileModifiedLater;
                }
            }).filter(new Predicate() { // from class: com.android.server.remoteappmode.AnrCollector$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean lambda$getLastAnrFile$0;
                    lambda$getLastAnrFile$0 = AnrCollector.this.lambda$getLastAnrFile$0(str, (File) obj);
                    return lambda$getLastAnrFile$0;
                }
            }).findFirst().orElse(null) : null;
            setLastGettingAnrTime(System.currentTimeMillis());
        }
        return r2;
    }

    public final void setLastGettingAnrTime(long j) {
        try {
            if (this.mPrefs == null) {
                this.mPrefs = this.mContext.getSharedPreferences("remote_app_mode_prefs", 0);
            }
            SharedPreferences.Editor edit = this.mPrefs.edit();
            edit.putLong("ltw_get_anr_time", j);
            edit.commit();
        } catch (Exception e) {
            Log.m83i("AnrCollector", e.toString());
        }
    }

    public final long getLastGettingAnrTime() {
        try {
            if (this.mPrefs == null) {
                this.mPrefs = this.mContext.getSharedPreferences("remote_app_mode_prefs", 0);
            }
            return this.mPrefs.getLong("ltw_get_anr_time", 0L);
        } catch (Exception e) {
            Log.m83i("AnrCollector", e.toString());
            return 0L;
        }
    }

    public final boolean isAnrFileModifiedLater(File file) {
        if (file.isDirectory()) {
            return false;
        }
        long lastGettingAnrTime = getLastGettingAnrTime();
        Log.m83i("AnrCollector", "isAnrFileModifiedLater - " + file.getName() + "lastModified : " + file.lastModified() + ", lastTimestamp : " + lastGettingAnrTime);
        return file.lastModified() > lastGettingAnrTime;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v13 */
    /* JADX WARN: Type inference failed for: r1v7 */
    /* JADX WARN: Type inference failed for: r1v8, types: [int] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:50:0x008d -> B:27:0x0090). Please report as a decompilation issue!!! */
    /* renamed from: isAnrFileFromPackage, reason: merged with bridge method [inline-methods] */
    public final boolean lambda$getLastAnrFile$0(File file, String str) {
        Log.m83i("AnrCollector", "isAnrFileFromPackage started - target file : " + file.getName());
        BufferedReader bufferedReader = null;
        bufferedReader = null;
        bufferedReader = null;
        try {
            try {
                try {
                    BufferedReader bufferedReader2 = new BufferedReader(new FileReader(file));
                    ?? r1 = 0;
                    while (true) {
                        try {
                            String readLine = bufferedReader2.readLine();
                            if (readLine == null) {
                                break;
                            }
                            int i = r1 + 1;
                            if (r1 >= 30) {
                                break;
                            }
                            if (readLine.toLowerCase().contains("cmd")) {
                                Log.m83i("AnrCollector", "isAnrFileFromPackage - " + file + " : " + readLine);
                                if (readLine.toLowerCase().contains(str)) {
                                    Log.m83i("AnrCollector", "isAnrFileFromPackage - return true");
                                    try {
                                        bufferedReader2.close();
                                        return true;
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                        return true;
                                    }
                                }
                            }
                            r1 = i;
                        } catch (IOException e2) {
                            e = e2;
                            bufferedReader = bufferedReader2;
                            e.printStackTrace();
                            if (bufferedReader != null) {
                                bufferedReader.close();
                                bufferedReader = bufferedReader;
                            }
                            Log.m83i("AnrCollector", "isAnrFileFromPackage - return false");
                            return false;
                        } catch (Throwable th) {
                            th = th;
                            bufferedReader = bufferedReader2;
                            if (bufferedReader != null) {
                                try {
                                    bufferedReader.close();
                                } catch (IOException e3) {
                                    e3.printStackTrace();
                                }
                            }
                            throw th;
                        }
                    }
                    bufferedReader2.close();
                    bufferedReader = r1;
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (IOException e4) {
                e = e4;
            }
        } catch (IOException e5) {
            e5.printStackTrace();
            bufferedReader = bufferedReader;
        }
        Log.m83i("AnrCollector", "isAnrFileFromPackage - return false");
        return false;
    }

    public class WriteANRInfoThread extends Thread {
        public final File mLastAnrFile;
        public final ParcelFileDescriptor.AutoCloseOutputStream mOut;

        public WriteANRInfoThread(File file, ParcelFileDescriptor parcelFileDescriptor) {
            super("WriteANRInfoThread");
            this.mLastAnrFile = file;
            this.mOut = new ParcelFileDescriptor.AutoCloseOutputStream(parcelFileDescriptor);
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            BufferedInputStream bufferedInputStream;
            Throwable th;
            IOException e;
            if (this.mLastAnrFile == null) {
                closeOutStream();
                return;
            }
            try {
                byte[] bArr = new byte[16384];
                bufferedInputStream = new BufferedInputStream(new FileInputStream(this.mLastAnrFile));
                while (true) {
                    try {
                        try {
                            int read = bufferedInputStream.read(bArr, 0, 16384);
                            if (read == -1) {
                                break;
                            } else {
                                this.mOut.write(bArr, 0, read);
                            }
                        } catch (IOException e2) {
                            e = e2;
                            e.printStackTrace();
                            closeFileStream(bufferedInputStream);
                            closeOutStream();
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        closeFileStream(bufferedInputStream);
                        closeOutStream();
                        throw th;
                    }
                }
            } catch (IOException e3) {
                bufferedInputStream = null;
                e = e3;
            } catch (Throwable th3) {
                bufferedInputStream = null;
                th = th3;
                closeFileStream(bufferedInputStream);
                closeOutStream();
                throw th;
            }
            closeFileStream(bufferedInputStream);
            closeOutStream();
        }

        public final void closeFileStream(BufferedInputStream bufferedInputStream) {
            if (bufferedInputStream != null) {
                try {
                    Log.m83i("AnrCollector", "buf.close()");
                    bufferedInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public final void closeOutStream() {
            try {
                Log.m83i("AnrCollector", "write : 0");
                this.mOut.write(0);
                this.mOut.flush();
                Log.m83i("AnrCollector", "mOut.close()");
                this.mOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
