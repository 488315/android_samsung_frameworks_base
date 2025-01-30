package com.samsung.android.powerSolution;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.format.DateFormat;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/* loaded from: classes2.dex */
public class SOCJump {
    public static SOC mCurrentSoc;
    public static File mFileObject;
    public static SOC mPreviousSoc = new SOC(-1);
    public static SOCJump mSocjump = null;
    public Context mContext;
    public IntentFilter mSocFilter;
    public BroadcastReceiver mSocJumpReciver = new MyBroadcastReceiver();

    public class SOC {
        public int battery;

        public SOC(int i) {
            this.battery = i;
        }

        public boolean socJumpcheck(SOC soc) {
            return soc != null && Math.abs(soc.battery - this.battery) > 1;
        }
    }

    public static String dateFormat(long j) {
        return (String) DateFormat.format("yyyy-MM-dd kk:mm:ss", j);
    }

    public class MyBroadcastReceiver extends BroadcastReceiver {
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            try {
                onEventRun(intent.getIntExtra("level", -1));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:47:0x0161, code lost:
        
            if (r2 == 0) goto L75;
         */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:44:0x0159  */
        /* JADX WARN: Removed duplicated region for block: B:46:0x015e  */
        /* JADX WARN: Removed duplicated region for block: B:51:0x016a  */
        /* JADX WARN: Removed duplicated region for block: B:53:0x016f  */
        /* JADX WARN: Removed duplicated region for block: B:55:0x0174  */
        /* JADX WARN: Type inference failed for: r2v1 */
        /* JADX WARN: Type inference failed for: r2v10 */
        /* JADX WARN: Type inference failed for: r2v12, types: [boolean] */
        /* JADX WARN: Type inference failed for: r2v17, types: [java.io.FileOutputStream, java.io.OutputStream] */
        /* JADX WARN: Type inference failed for: r2v7 */
        /* JADX WARN: Type inference failed for: r2v8 */
        /* JADX WARN: Type inference failed for: r2v9 */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void onEventRun(int i) {
            FileOutputStream fileOutputStream;
            OutputStreamWriter outputStreamWriter;
            FileOutputStream fileOutputStream2;
            BufferedWriter bufferedWriter;
            SOCJump.mCurrentSoc = new SOC(i);
            if (SOCJump.mPreviousSoc.battery != -1 && SOCJump.mCurrentSoc.socJumpcheck(SOCJump.mPreviousSoc)) {
                long currentTimeMillis = System.currentTimeMillis();
                BufferedWriter bufferedWriter2 = 0;
                r11 = 0;
                r11 = 0;
                bufferedWriter2 = 0;
                OutputStreamWriter outputStreamWriter2 = null;
                bufferedWriter2 = 0;
                BufferedWriter bufferedWriter3 = 0;
                try {
                    try {
                        File file = new File("/data/log/eSOC.txt");
                        SOCJump.mFileObject = file;
                        if (!file.exists() || SOCJump.mFileObject.length() <= 1024) {
                            outputStreamWriter = null;
                            bufferedWriter = null;
                        } else {
                            fileOutputStream2 = new FileOutputStream("/data/log/eSOC.txt", false);
                            try {
                                outputStreamWriter = new OutputStreamWriter((OutputStream) fileOutputStream2, StandardCharsets.UTF_8);
                                try {
                                    bufferedWriter = new BufferedWriter(outputStreamWriter);
                                } catch (Exception e) {
                                    e = e;
                                    System.out.println("An error occurred.");
                                    e.printStackTrace();
                                    if (bufferedWriter3 != 0) {
                                        bufferedWriter3.close();
                                    }
                                    if (outputStreamWriter != null) {
                                        outputStreamWriter.close();
                                    }
                                }
                                try {
                                    bufferedWriter.write("Time\t\t\t\t|SOC jump from\t|SOC jump to\n");
                                    bufferedWriter.close();
                                    outputStreamWriter.close();
                                    fileOutputStream2.close();
                                    outputStreamWriter2 = fileOutputStream2;
                                } catch (Exception e2) {
                                    e = e2;
                                    bufferedWriter3 = bufferedWriter;
                                    System.out.println("An error occurred.");
                                    e.printStackTrace();
                                    if (bufferedWriter3 != 0) {
                                    }
                                    if (outputStreamWriter != null) {
                                    }
                                } catch (Throwable th) {
                                    th = th;
                                    bufferedWriter2 = bufferedWriter;
                                    fileOutputStream = fileOutputStream2;
                                    if (bufferedWriter2 != 0) {
                                    }
                                    if (outputStreamWriter != null) {
                                    }
                                    if (fileOutputStream != null) {
                                    }
                                    throw th;
                                }
                            } catch (Exception e3) {
                                e = e3;
                                outputStreamWriter = null;
                            } catch (Throwable th2) {
                                th = th2;
                                outputStreamWriter = null;
                                fileOutputStream = fileOutputStream2;
                                if (bufferedWriter2 != 0) {
                                }
                                if (outputStreamWriter != null) {
                                }
                                if (fileOutputStream != null) {
                                }
                                throw th;
                            }
                        }
                        try {
                            fileOutputStream2 = SOCJump.mFileObject.createNewFile();
                            try {
                                try {
                                } catch (Exception e4) {
                                    outputStreamWriter = outputStreamWriter2;
                                    bufferedWriter3 = "/data/log/eSOC.txt";
                                    e = e4;
                                    System.out.println("An error occurred.");
                                    e.printStackTrace();
                                    if (bufferedWriter3 != 0) {
                                    }
                                    if (outputStreamWriter != null) {
                                    }
                                } catch (Throwable th3) {
                                    outputStreamWriter = outputStreamWriter2;
                                    bufferedWriter2 = "/data/log/eSOC.txt";
                                    th = th3;
                                    fileOutputStream = fileOutputStream2;
                                    if (bufferedWriter2 != 0) {
                                        bufferedWriter2.close();
                                    }
                                    if (outputStreamWriter != null) {
                                        outputStreamWriter.close();
                                    }
                                    if (fileOutputStream != null) {
                                        fileOutputStream.close();
                                    }
                                    throw th;
                                }
                            } catch (Exception e5) {
                                e = e5;
                                outputStreamWriter = outputStreamWriter2;
                                bufferedWriter3 = bufferedWriter;
                                System.out.println("An error occurred.");
                                e.printStackTrace();
                                if (bufferedWriter3 != 0) {
                                }
                                if (outputStreamWriter != null) {
                                }
                            } catch (Throwable th4) {
                                th = th4;
                                outputStreamWriter = outputStreamWriter2;
                                bufferedWriter2 = bufferedWriter;
                                fileOutputStream = fileOutputStream2;
                                if (bufferedWriter2 != 0) {
                                }
                                if (outputStreamWriter != null) {
                                }
                                if (fileOutputStream != null) {
                                }
                                throw th;
                            }
                        } catch (Exception e6) {
                            e = e6;
                            fileOutputStream2 = outputStreamWriter2;
                        } catch (Throwable th5) {
                            th = th5;
                            fileOutputStream2 = outputStreamWriter2;
                        }
                    } catch (Throwable th6) {
                        th = th6;
                    }
                } catch (Exception e7) {
                    e = e7;
                    fileOutputStream2 = 0;
                    outputStreamWriter = null;
                } catch (Throwable th7) {
                    th = th7;
                    fileOutputStream = null;
                    outputStreamWriter = null;
                }
                if (fileOutputStream2 != 0) {
                    FileOutputStream fileOutputStream3 = new FileOutputStream("/data/log/eSOC.txt", true);
                    OutputStreamWriter outputStreamWriter3 = new OutputStreamWriter(fileOutputStream3, StandardCharsets.UTF_8);
                    BufferedWriter bufferedWriter4 = new BufferedWriter(outputStreamWriter3);
                    System.out.println("powerSolution_SOCJump_ File created: " + SOCJump.mFileObject.getName());
                    bufferedWriter4.write("Time\t\t\t\t|SOC jump from\t|SOC jump to\n");
                    bufferedWriter4.write(SOCJump.dateFormat(currentTimeMillis) + "\t|\t" + SOCJump.mPreviousSoc.battery + "\t\t\t|\t" + SOCJump.mCurrentSoc.battery + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
                    SOCJump.mPreviousSoc = SOCJump.mCurrentSoc;
                    bufferedWriter4.close();
                    outputStreamWriter3.close();
                    fileOutputStream3.close();
                    bufferedWriter4.close();
                    outputStreamWriter3.close();
                    fileOutputStream3.close();
                    return;
                }
                fileOutputStream2 = new FileOutputStream("/data/log/eSOC.txt", true);
                OutputStreamWriter outputStreamWriter4 = new OutputStreamWriter(fileOutputStream2, StandardCharsets.UTF_8);
                BufferedWriter bufferedWriter5 = new BufferedWriter(outputStreamWriter4);
                bufferedWriter5.write(SOCJump.dateFormat(currentTimeMillis) + "\t|\t" + SOCJump.mPreviousSoc.battery + "\t\t\t|\t" + SOCJump.mCurrentSoc.battery + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
                bufferedWriter5.close();
                outputStreamWriter4.close();
                fileOutputStream2.close();
            }
            SOCJump.mPreviousSoc = SOCJump.mCurrentSoc;
        }
    }

    public static SOCJump getInstance(Context context) {
        if (mSocjump == null) {
            mSocjump = new SOCJump(context);
        }
        return mSocjump;
    }

    public SOCJump(Context context) {
        this.mContext = context;
        IntentFilter intentFilter = new IntentFilter();
        this.mSocFilter = intentFilter;
        intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
        this.mContext.registerReceiver(this.mSocJumpReciver, this.mSocFilter);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:22:0x004c  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0051  */
    /* JADX WARN: Removed duplicated region for block: B:26:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x005d  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0067  */
    /* JADX WARN: Type inference failed for: r1v0 */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v3, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r1v4 */
    /* JADX WARN: Type inference failed for: r1v5, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r1v7 */
    /* JADX WARN: Type inference failed for: r1v8 */
    /* JADX WARN: Type inference failed for: r1v9, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r4v0, types: [java.lang.String[]] */
    /* JADX WARN: Type inference failed for: r4v12 */
    /* JADX WARN: Type inference failed for: r4v13 */
    /* JADX WARN: Type inference failed for: r4v5, types: [java.io.FileInputStream] */
    /* JADX WARN: Type inference failed for: r4v6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void dump(PrintWriter printWriter, String[] strArr) {
        FileInputStream fileInputStream;
        InputStreamReader inputStreamReader;
        FileInputStream fileInputStream2;
        ?? r1;
        ?? r12;
        FileInputStream fileInputStream3;
        IOException e;
        printWriter.println("[SOCJump]");
        try {
            try {
                fileInputStream3 = new FileInputStream("/data/log/eSOC.txt");
            } catch (Throwable th) {
                th = th;
                if (r1 != 0) {
                    r1.close();
                }
                if (strArr != 0) {
                    strArr.close();
                }
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
                throw th;
            }
            try {
                inputStreamReader = new InputStreamReader(fileInputStream3, StandardCharsets.UTF_8);
                try {
                    r12 = new BufferedReader(inputStreamReader);
                    try {
                        for (String readLine = r12.readLine(); readLine != null; readLine = r12.readLine()) {
                            printWriter.println(readLine);
                        }
                        r12.close();
                        fileInputStream3.close();
                    } catch (IOException e2) {
                        e = e2;
                        e.printStackTrace();
                        if (r12 != 0) {
                            r12.close();
                        }
                        if (fileInputStream3 != null) {
                            fileInputStream3.close();
                        }
                        if (inputStreamReader == null) {
                            return;
                        }
                        inputStreamReader.close();
                    }
                } catch (IOException e3) {
                    e = e3;
                    r12 = 0;
                    fileInputStream3 = fileInputStream3;
                    e = e;
                    e.printStackTrace();
                    if (r12 != 0) {
                    }
                    if (fileInputStream3 != null) {
                    }
                    if (inputStreamReader == null) {
                    }
                    inputStreamReader.close();
                } catch (Throwable th2) {
                    th = th2;
                    r1 = 0;
                    strArr = fileInputStream3;
                    th = th;
                    if (r1 != 0) {
                    }
                    if (strArr != 0) {
                    }
                    if (inputStreamReader != null) {
                    }
                    throw th;
                }
            } catch (IOException e4) {
                e = e4;
                inputStreamReader = null;
                fileInputStream2 = fileInputStream3;
                r12 = inputStreamReader;
                fileInputStream3 = fileInputStream2;
                e = e;
                e.printStackTrace();
                if (r12 != 0) {
                }
                if (fileInputStream3 != null) {
                }
                if (inputStreamReader == null) {
                }
                inputStreamReader.close();
            } catch (Throwable th3) {
                th = th3;
                inputStreamReader = null;
                fileInputStream = fileInputStream3;
                r1 = inputStreamReader;
                strArr = fileInputStream;
                th = th;
                if (r1 != 0) {
                }
                if (strArr != 0) {
                }
                if (inputStreamReader != null) {
                }
                throw th;
            }
        } catch (IOException e5) {
            e = e5;
            fileInputStream2 = null;
            inputStreamReader = null;
        } catch (Throwable th4) {
            th = th4;
            fileInputStream = null;
            inputStreamReader = null;
        }
        inputStreamReader.close();
    }
}
