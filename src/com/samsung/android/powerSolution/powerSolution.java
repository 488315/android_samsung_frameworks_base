package com.samsung.android.powerSolution;

import android.content.Context;
import android.util.Log;
import com.samsung.android.powerSolution.IpowerSolution;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.PrintWriter;

/* loaded from: classes6.dex */
public class powerSolution extends IpowerSolution.Stub {
    private static final String TAG = "powerSolutinoService";
    Context mContext;
    private SOCJump mSOCjump;

    public powerSolution(Context context) {
        this.mContext = context;
        this.mSOCjump = SOCJump.getInstance(context);
        Log.d(TAG, "onCreate: powerSolution Service Constructor called..");
    }

    @Override // android.os.Binder
    protected void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println("\n[powerSolution Dump]");
        try {
            this.mSOCjump.dump(printWriter, strArr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
