package com.samsung.android.powerSolution;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.format.DateFormat;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/* loaded from: classes6.dex */
public class SOCJump {
    protected static SOC mCurrentSoc = null;
    protected static File mFileObject = null;
    protected static final String mSocFilePath = "/data/log/eSOC.txt";
    Context mContext;
    IntentFilter mSocFilter;
    BroadcastReceiver mSocJumpReciver = new MyBroadcastReceiver();
    protected static SOC mPreviousSoc = new SOC(-1);
    private static SOCJump mSocjump = null;

    private static class SOC {
        int battery;

        SOC(int i) {
            this.battery = i;
        }

        public boolean socJumpcheck(SOC soc) {
            return soc != null && Math.abs(soc.battery - this.battery) > 1;
        }

        public void setBatteryLevel(int i) {
            this.battery = i;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String dateFormat(long j) {
        return (String) DateFormat.format("yyyy-MM-dd kk:mm:ss", j);
    }

    public static class MyBroadcastReceiver extends BroadcastReceiver {
        private static final String TAG = "powerSolution_SOCJump_";

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) throws Throwable {
            try {
                onEventRun(intent.getIntExtra("level", -1));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:74:0x017a  */
        /* JADX WARN: Removed duplicated region for block: B:76:0x017f  */
        /* JADX WARN: Removed duplicated region for block: B:78:0x0184  */
        /* JADX WARN: Removed duplicated region for block: B:81:0x018b  */
        /* JADX WARN: Removed duplicated region for block: B:83:0x0190  */
        /* JADX WARN: Removed duplicated region for block: B:85:0x0195  */
        /* JADX WARN: Type inference failed for: r12v10 */
        /* JADX WARN: Type inference failed for: r12v11 */
        /* JADX WARN: Type inference failed for: r12v12 */
        /* JADX WARN: Type inference failed for: r12v13, types: [java.io.OutputStreamWriter, java.io.Writer] */
        /* JADX WARN: Type inference failed for: r12v19 */
        /* JADX WARN: Type inference failed for: r12v22 */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private void onEventRun(int i) throws Throwable {
            FileOutputStream fileOutputStream;
            OutputStreamWriter outputStreamWriter;
            BufferedWriter bufferedWriter;
            SOCJump.mCurrentSoc = new SOC(i);
            if (SOCJump.mPreviousSoc.battery != -1 && SOCJump.mCurrentSoc.socJumpcheck(SOCJump.mPreviousSoc)) {
                long jCurrentTimeMillis = System.currentTimeMillis();
                BufferedWriter bufferedWriter2 = null;
                bufferedWriter = null;
                bufferedWriter = null;
                bufferedWriter2 = null;
                ?? outputStreamWriter2 = 0;
                bufferedWriter2 = null;
                BufferedWriter bufferedWriter3 = null;
                try {
                    try {
                        SOCJump.mFileObject = new File(SOCJump.mSocFilePath);
                        if (!SOCJump.mFileObject.exists() || SOCJump.mFileObject.length() <= 1024) {
                            outputStreamWriter = null;
                            bufferedWriter = null;
                        } else {
                            fileOutputStream = new FileOutputStream(SOCJump.mSocFilePath, false);
                            try {
                                outputStreamWriter = new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8);
                                try {
                                    bufferedWriter = new BufferedWriter(outputStreamWriter);
                                } catch (Exception e) {
                                    e = e;
                                    System.out.println("An error occurred.");
                                    e.printStackTrace();
                                    if (bufferedWriter3 != null) {
                                        bufferedWriter3.close();
                                    }
                                    if (outputStreamWriter != null) {
                                        outputStreamWriter.close();
                                    }
                                    if (fileOutputStream != null) {
                                        fileOutputStream.close();
                                    }
                                    SOCJump.mPreviousSoc = SOCJump.mCurrentSoc;
                                }
                            } catch (Exception e2) {
                                e = e2;
                                outputStreamWriter = null;
                            } catch (Throwable th) {
                                th = th;
                                outputStreamWriter = null;
                                if (bufferedWriter2 != null) {
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
                            try {
                                bufferedWriter.write("Time\t\t\t\t|SOC jump from\t|SOC jump to\n");
                                bufferedWriter.close();
                                outputStreamWriter.close();
                                fileOutputStream.close();
                                outputStreamWriter2 = fileOutputStream;
                            } catch (Exception e3) {
                                e = e3;
                                bufferedWriter3 = bufferedWriter;
                                System.out.println("An error occurred.");
                                e.printStackTrace();
                                if (bufferedWriter3 != null) {
                                }
                                if (outputStreamWriter != null) {
                                }
                                if (fileOutputStream != null) {
                                }
                                SOCJump.mPreviousSoc = SOCJump.mCurrentSoc;
                            } catch (Throwable th2) {
                                th = th2;
                                bufferedWriter2 = bufferedWriter;
                                if (bufferedWriter2 != null) {
                                }
                                if (outputStreamWriter != null) {
                                }
                                if (fileOutputStream != null) {
                                }
                                throw th;
                            }
                        }
                        try {
                            if (SOCJump.mFileObject.createNewFile()) {
                                fileOutputStream = new FileOutputStream(SOCJump.mSocFilePath, true);
                                OutputStreamWriter outputStreamWriter3 = new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8);
                                try {
                                    BufferedWriter bufferedWriter4 = new BufferedWriter(outputStreamWriter3);
                                    try {
                                        System.out.println("powerSolution_SOCJump_ File created: " + SOCJump.mFileObject.getName());
                                        bufferedWriter4.write("Time\t\t\t\t|SOC jump from\t|SOC jump to\n");
                                        bufferedWriter4.write(SOCJump.dateFormat(jCurrentTimeMillis) + "\t|\t" + SOCJump.mPreviousSoc.battery + "\t\t\t|\t" + SOCJump.mCurrentSoc.battery + ShaderAssembler.NEWLINE);
                                        SOCJump.mPreviousSoc = SOCJump.mCurrentSoc;
                                        bufferedWriter4.close();
                                        outputStreamWriter3.close();
                                        fileOutputStream.close();
                                        bufferedWriter4.close();
                                        outputStreamWriter3.close();
                                        fileOutputStream.close();
                                        return;
                                    } catch (Exception e4) {
                                        outputStreamWriter = outputStreamWriter3;
                                        bufferedWriter3 = bufferedWriter4;
                                        e = e4;
                                        System.out.println("An error occurred.");
                                        e.printStackTrace();
                                        if (bufferedWriter3 != null) {
                                        }
                                        if (outputStreamWriter != null) {
                                        }
                                        if (fileOutputStream != null) {
                                        }
                                        SOCJump.mPreviousSoc = SOCJump.mCurrentSoc;
                                    } catch (Throwable th3) {
                                        outputStreamWriter = outputStreamWriter3;
                                        bufferedWriter2 = bufferedWriter4;
                                        th = th3;
                                        if (bufferedWriter2 != null) {
                                        }
                                        if (outputStreamWriter != null) {
                                        }
                                        if (fileOutputStream != null) {
                                        }
                                        throw th;
                                    }
                                } catch (Exception e5) {
                                    e = e5;
                                    outputStreamWriter = outputStreamWriter3;
                                    bufferedWriter3 = bufferedWriter;
                                    System.out.println("An error occurred.");
                                    e.printStackTrace();
                                    if (bufferedWriter3 != null) {
                                    }
                                    if (outputStreamWriter != null) {
                                    }
                                    if (fileOutputStream != null) {
                                    }
                                    SOCJump.mPreviousSoc = SOCJump.mCurrentSoc;
                                } catch (Throwable th4) {
                                    th = th4;
                                    outputStreamWriter = outputStreamWriter3;
                                    bufferedWriter2 = bufferedWriter;
                                    if (bufferedWriter2 != null) {
                                    }
                                    if (outputStreamWriter != null) {
                                    }
                                    if (fileOutputStream != null) {
                                    }
                                    throw th;
                                }
                            }
                            FileOutputStream fileOutputStream2 = new FileOutputStream(SOCJump.mSocFilePath, true);
                            try {
                                outputStreamWriter2 = new OutputStreamWriter(fileOutputStream2, StandardCharsets.UTF_8);
                                try {
                                    BufferedWriter bufferedWriter5 = new BufferedWriter(outputStreamWriter2);
                                    try {
                                        bufferedWriter5.write(SOCJump.dateFormat(jCurrentTimeMillis) + "\t|\t" + SOCJump.mPreviousSoc.battery + "\t\t\t|\t" + SOCJump.mCurrentSoc.battery + ShaderAssembler.NEWLINE);
                                        bufferedWriter5.close();
                                        outputStreamWriter2.close();
                                        fileOutputStream2.close();
                                    } catch (Exception e6) {
                                        outputStreamWriter = outputStreamWriter2;
                                        fileOutputStream = fileOutputStream2;
                                        bufferedWriter3 = bufferedWriter5;
                                        e = e6;
                                        System.out.println("An error occurred.");
                                        e.printStackTrace();
                                        if (bufferedWriter3 != null) {
                                        }
                                        if (outputStreamWriter != null) {
                                        }
                                        if (fileOutputStream != null) {
                                        }
                                        SOCJump.mPreviousSoc = SOCJump.mCurrentSoc;
                                    } catch (Throwable th5) {
                                        outputStreamWriter = outputStreamWriter2;
                                        fileOutputStream = fileOutputStream2;
                                        bufferedWriter2 = bufferedWriter5;
                                        th = th5;
                                        if (bufferedWriter2 != null) {
                                        }
                                        if (outputStreamWriter != null) {
                                        }
                                        if (fileOutputStream != null) {
                                        }
                                        throw th;
                                    }
                                } catch (Exception e7) {
                                    e = e7;
                                    outputStreamWriter = outputStreamWriter2;
                                    fileOutputStream = fileOutputStream2;
                                    bufferedWriter3 = bufferedWriter;
                                    System.out.println("An error occurred.");
                                    e.printStackTrace();
                                    if (bufferedWriter3 != null) {
                                    }
                                    if (outputStreamWriter != null) {
                                    }
                                    if (fileOutputStream != null) {
                                    }
                                    SOCJump.mPreviousSoc = SOCJump.mCurrentSoc;
                                } catch (Throwable th6) {
                                    th = th6;
                                    outputStreamWriter = outputStreamWriter2;
                                    fileOutputStream = fileOutputStream2;
                                    bufferedWriter2 = bufferedWriter;
                                    if (bufferedWriter2 != null) {
                                    }
                                    if (outputStreamWriter != null) {
                                    }
                                    if (fileOutputStream != null) {
                                    }
                                    throw th;
                                }
                            } catch (Exception e8) {
                                e = e8;
                            } catch (Throwable th7) {
                                th = th7;
                            }
                        } catch (Exception e9) {
                            e = e9;
                            fileOutputStream = outputStreamWriter2;
                        } catch (Throwable th8) {
                            th = th8;
                            fileOutputStream = outputStreamWriter2;
                        }
                    } catch (Throwable th9) {
                        th = th9;
                    }
                } catch (Exception e10) {
                    e = e10;
                    fileOutputStream = null;
                    outputStreamWriter = null;
                } catch (Throwable th10) {
                    th = th10;
                    fileOutputStream = null;
                    outputStreamWriter = null;
                }
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
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        this.mContext.registerReceiver(this.mSocJumpReciver, this.mSocFilter);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:32:0x004f  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0054  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0059  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0060  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0065  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x006a  */
    /* JADX WARN: Removed duplicated region for block: B:57:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r1v0 */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v10, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r1v2 */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v4, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r1v5 */
    /* JADX WARN: Type inference failed for: r1v6, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r1v8 */
    /* JADX WARN: Type inference failed for: r1v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void dump(PrintWriter printWriter, String[] strArr) throws Throwable {
        FileInputStream fileInputStream;
        InputStreamReader inputStreamReader;
        ?? bufferedReader;
        Throwable th;
        IOException e;
        printWriter.println("[SOCJump]");
        try {
            fileInputStream = new FileInputStream(mSocFilePath);
        } catch (IOException e2) {
            e = e2;
            fileInputStream = null;
            inputStreamReader = null;
        } catch (Throwable th2) {
            th = th2;
            fileInputStream = null;
            inputStreamReader = null;
        }
        try {
            inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
            try {
                bufferedReader = new BufferedReader(inputStreamReader);
                try {
                    try {
                        for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                            printWriter.println(line);
                        }
                        bufferedReader.close();
                        fileInputStream.close();
                        inputStreamReader.close();
                    } catch (IOException e3) {
                        e = e3;
                        e.printStackTrace();
                        if (bufferedReader != 0) {
                            bufferedReader.close();
                        }
                        if (fileInputStream != null) {
                            fileInputStream.close();
                        }
                        if (inputStreamReader != null) {
                            inputStreamReader.close();
                        }
                    }
                } catch (Throwable th3) {
                    th = th3;
                    if (bufferedReader != 0) {
                        bufferedReader.close();
                    }
                    if (fileInputStream != null) {
                        fileInputStream.close();
                    }
                    if (inputStreamReader != null) {
                        inputStreamReader.close();
                    }
                    throw th;
                }
            } catch (IOException e4) {
                e = e4;
                bufferedReader = 0;
                e = e;
                e.printStackTrace();
                if (bufferedReader != 0) {
                }
                if (fileInputStream != null) {
                }
                if (inputStreamReader != null) {
                }
            } catch (Throwable th4) {
                th = th4;
                bufferedReader = 0;
                th = th;
                if (bufferedReader != 0) {
                }
                if (fileInputStream != null) {
                }
                if (inputStreamReader != null) {
                }
                throw th;
            }
        } catch (IOException e5) {
            e = e5;
            inputStreamReader = null;
            bufferedReader = inputStreamReader;
            e = e;
            e.printStackTrace();
            if (bufferedReader != 0) {
            }
            if (fileInputStream != null) {
            }
            if (inputStreamReader != null) {
            }
        } catch (Throwable th5) {
            th = th5;
            inputStreamReader = null;
            bufferedReader = inputStreamReader;
            th = th;
            if (bufferedReader != 0) {
            }
            if (fileInputStream != null) {
            }
            if (inputStreamReader != null) {
            }
            throw th;
        }
    }
}
