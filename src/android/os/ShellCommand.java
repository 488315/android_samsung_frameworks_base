package android.os;

import com.android.modules.utils.BasicShellCommandHandler;
import java.io.FileDescriptor;

/* loaded from: classes3.dex */
public abstract class ShellCommand extends BasicShellCommandHandler {
    private ResultReceiver mResultReceiver;
    private ShellCallback mShellCallback;

    public int exec(Binder binder, FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, FileDescriptor fileDescriptor3, String[] strArr, ShellCallback shellCallback, ResultReceiver resultReceiver) {
        this.mShellCallback = shellCallback;
        this.mResultReceiver = resultReceiver;
        int iExec = super.exec(binder, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr);
        ResultReceiver resultReceiver2 = this.mResultReceiver;
        if (resultReceiver2 != null) {
            resultReceiver2.send(iExec, null);
        }
        return iExec;
    }

    public ResultReceiver adoptResultReceiver() {
        ResultReceiver resultReceiver = this.mResultReceiver;
        this.mResultReceiver = null;
        return resultReceiver;
    }

    public ParcelFileDescriptor openFileForSystem(String str, String str2) {
        try {
            ParcelFileDescriptor parcelFileDescriptorOpenFile = getShellCallback().openFile(str, "u:r:system_server:s0", str2);
            if (parcelFileDescriptorOpenFile != null) {
                return parcelFileDescriptorOpenFile;
            }
        } catch (RuntimeException e) {
            getErrPrintWriter().println("Failure opening file: " + e.getMessage());
        }
        getErrPrintWriter().println("Error: Unable to open file: " + str);
        if (str != null && str.startsWith("/data/local/tmp/")) {
            return null;
        }
        getErrPrintWriter().println("Consider using a file under /data/local/tmp/");
        return null;
    }

    @Override // com.android.modules.utils.BasicShellCommandHandler
    public int handleDefaultCommands(String str) {
        if ("dump".equals(str)) {
            String[] strArr = new String[getAllArgs().length - 1];
            System.arraycopy(getAllArgs(), 1, strArr, 0, getAllArgs().length - 1);
            getTarget().doDump(getOutFileDescriptor(), getOutPrintWriter(), strArr);
            return 0;
        }
        return super.handleDefaultCommands(str);
    }

    @Override // com.android.modules.utils.BasicShellCommandHandler
    public String peekNextArg() {
        return super.peekNextArg();
    }

    public ShellCallback getShellCallback() {
        return this.mShellCallback;
    }
}
