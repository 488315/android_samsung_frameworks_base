package com.android.internal.os;

import com.android.modules.utils.BasicShellCommandHandler;
import java.io.PrintStream;

/* loaded from: classes5.dex */
public abstract class BaseCommand {
    public static final String FATAL_ERROR_CODE = "Error type 1";
    public static final String NO_CLASS_ERROR_CODE = "Error type 3";
    public static final String NO_SYSTEM_ERROR_CODE = "Error type 2";
    protected final BasicShellCommandHandler mArgs = new BasicShellCommandHandler(this) { // from class: com.android.internal.os.BaseCommand.1
        @Override // com.android.modules.utils.BasicShellCommandHandler
        public int onCommand(String str) {
            return 0;
        }

        @Override // com.android.modules.utils.BasicShellCommandHandler
        public void onHelp() {
        }
    };
    private String[] mRawArgs;

    public abstract void onRun() throws Exception;

    public abstract void onShowUsage(PrintStream printStream);

    public void run(String[] strArr) {
        int i = 1;
        if (strArr.length < 1) {
            onShowUsage(System.out);
            return;
        }
        this.mRawArgs = strArr;
        this.mArgs.init(null, null, null, null, strArr, 0);
        try {
            try {
                try {
                    onRun();
                } catch (IllegalArgumentException e) {
                    onShowUsage(System.err);
                    System.err.println();
                    System.err.println("Error: " + e.getMessage());
                }
                System.out.flush();
                System.err.flush();
                i = 0;
            } catch (Exception e2) {
                e2.printStackTrace(System.err);
                System.out.flush();
                System.err.flush();
            }
            if (i != 0) {
                System.exit(i);
            }
        } catch (Throwable th) {
            System.out.flush();
            System.err.flush();
            throw th;
        }
    }

    public void showUsage() {
        onShowUsage(System.err);
    }

    public void showError(String str) {
        onShowUsage(System.err);
        System.err.println();
        System.err.println(str);
    }

    public String nextOption() {
        return this.mArgs.getNextOption();
    }

    public String nextArg() {
        return this.mArgs.getNextArg();
    }

    public String peekNextArg() {
        return this.mArgs.peekNextArg();
    }

    public String nextArgRequired() {
        return this.mArgs.getNextArgRequired();
    }

    public String[] getRawArgs() {
        return this.mRawArgs;
    }
}
