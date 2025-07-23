package com.android.modules.utils;

import android.os.Binder;
import com.android.internal.content.NativeLibraryHelper;
import java.io.BufferedInputStream;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/* loaded from: classes6.dex */
public abstract class BasicShellCommandHandler {
    protected static final boolean DEBUG = false;
    protected static final String TAG = "ShellCommand";
    private int mArgPos;
    private String[] mArgs;
    private String mCmd;
    private String mCurArgData;
    private FileDescriptor mErr;
    private PrintWriter mErrPrintWriter;
    private FileOutputStream mFileErr;
    private FileInputStream mFileIn;
    private FileOutputStream mFileOut;
    private FileDescriptor mIn;
    private InputStream mInputStream;
    private FileDescriptor mOut;
    private PrintWriter mOutPrintWriter;
    private Binder mTarget;

    public abstract int onCommand(String str);

    public abstract void onHelp();

    public void init(Binder binder, FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, FileDescriptor fileDescriptor3, String[] strArr, int i) {
        this.mTarget = binder;
        this.mIn = fileDescriptor;
        this.mOut = fileDescriptor2;
        this.mErr = fileDescriptor3;
        this.mArgs = strArr;
        this.mCmd = null;
        this.mArgPos = i;
        this.mCurArgData = null;
        this.mFileIn = null;
        this.mFileOut = null;
        this.mFileErr = null;
        this.mOutPrintWriter = null;
        this.mErrPrintWriter = null;
        this.mInputStream = null;
    }

    public int exec(Binder binder, FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, FileDescriptor fileDescriptor3, String[] strArr) {
        int i;
        String str;
        if (strArr == null || strArr.length <= 0) {
            i = 0;
            str = null;
        } else {
            str = strArr[0];
            i = 1;
        }
        init(binder, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, i);
        this.mCmd = str;
        try {
            int onCommand = onCommand(str);
            PrintWriter printWriter = this.mOutPrintWriter;
            if (printWriter != null) {
                printWriter.flush();
            }
            PrintWriter printWriter2 = this.mErrPrintWriter;
            if (printWriter2 != null) {
                printWriter2.flush();
            }
            return onCommand;
        } catch (Throwable th) {
            try {
                PrintWriter errPrintWriter = getErrPrintWriter();
                errPrintWriter.println();
                errPrintWriter.println("Exception occurred while executing '" + this.mCmd + "':");
                th.printStackTrace(errPrintWriter);
                PrintWriter printWriter3 = this.mOutPrintWriter;
                if (printWriter3 != null) {
                    printWriter3.flush();
                }
                PrintWriter printWriter4 = this.mErrPrintWriter;
                if (printWriter4 == null) {
                    return -1;
                }
                printWriter4.flush();
                return -1;
            } finally {
            }
        }
    }

    public FileDescriptor getOutFileDescriptor() {
        return this.mOut;
    }

    public OutputStream getRawOutputStream() {
        if (this.mFileOut == null) {
            this.mFileOut = new FileOutputStream(this.mOut);
        }
        return this.mFileOut;
    }

    public PrintWriter getOutPrintWriter() {
        if (this.mOutPrintWriter == null) {
            this.mOutPrintWriter = new PrintWriter(getRawOutputStream());
        }
        return this.mOutPrintWriter;
    }

    public FileDescriptor getErrFileDescriptor() {
        return this.mErr;
    }

    public OutputStream getRawErrorStream() {
        if (this.mFileErr == null) {
            this.mFileErr = new FileOutputStream(this.mErr);
        }
        return this.mFileErr;
    }

    public PrintWriter getErrPrintWriter() {
        if (this.mErr == null) {
            return getOutPrintWriter();
        }
        if (this.mErrPrintWriter == null) {
            this.mErrPrintWriter = new PrintWriter(getRawErrorStream());
        }
        return this.mErrPrintWriter;
    }

    public FileDescriptor getInFileDescriptor() {
        return this.mIn;
    }

    public InputStream getRawInputStream() {
        if (this.mFileIn == null) {
            this.mFileIn = new FileInputStream(this.mIn);
        }
        return this.mFileIn;
    }

    public InputStream getBufferedInputStream() {
        if (this.mInputStream == null) {
            this.mInputStream = new BufferedInputStream(getRawInputStream());
        }
        return this.mInputStream;
    }

    public String getNextOption() {
        if (this.mCurArgData != null) {
            throw new IllegalArgumentException("No argument expected after \"" + this.mArgs[this.mArgPos - 1] + "\"");
        }
        int i = this.mArgPos;
        String[] strArr = this.mArgs;
        if (i >= strArr.length) {
            return null;
        }
        String str = strArr[i];
        if (!str.startsWith(NativeLibraryHelper.CLEAR_ABI_OVERRIDE)) {
            return null;
        }
        this.mArgPos++;
        if (str.equals("--")) {
            return null;
        }
        if (str.length() > 1 && str.charAt(1) != '-') {
            if (str.length() > 2) {
                this.mCurArgData = str.substring(2);
                return str.substring(0, 2);
            }
            this.mCurArgData = null;
            return str;
        }
        this.mCurArgData = null;
        return str;
    }

    public String getNextArg() {
        String str = this.mCurArgData;
        if (str != null) {
            this.mCurArgData = null;
            return str;
        }
        int i = this.mArgPos;
        String[] strArr = this.mArgs;
        if (i >= strArr.length) {
            return null;
        }
        this.mArgPos = i + 1;
        return strArr[i];
    }

    public String peekNextArg() {
        String str = this.mCurArgData;
        if (str != null) {
            return str;
        }
        int i = this.mArgPos;
        String[] strArr = this.mArgs;
        if (i < strArr.length) {
            return strArr[i];
        }
        return null;
    }

    public String[] peekRemainingArgs() {
        String[] strArr = new String[getRemainingArgsCount()];
        int i = this.mArgPos;
        while (true) {
            String[] strArr2 = this.mArgs;
            if (i >= strArr2.length) {
                return strArr;
            }
            strArr[i - this.mArgPos] = strArr2[i];
            i++;
        }
    }

    public int getRemainingArgsCount() {
        int i = this.mArgPos;
        String[] strArr = this.mArgs;
        if (i >= strArr.length) {
            return 0;
        }
        return strArr.length - i;
    }

    public String getNextArgRequired() {
        String nextArg = getNextArg();
        if (nextArg != null) {
            return nextArg;
        }
        throw new IllegalArgumentException("Argument expected after \"" + this.mArgs[this.mArgPos - 1] + "\"");
    }

    public int handleDefaultCommands(String str) {
        if (str == null || "help".equals(str) || "-h".equals(str)) {
            onHelp();
            return -1;
        }
        getOutPrintWriter().println("Unknown command: " + str);
        return -1;
    }

    public Binder getTarget() {
        return this.mTarget;
    }

    public String[] getAllArgs() {
        return this.mArgs;
    }
}
