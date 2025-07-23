package com.samsung.vekit.Common.Object;

import android.util.Log;
import com.samsung.vekit.Common.Type.ElementType;
import com.samsung.vekit.Common.Type.LogProfileType;
import com.samsung.vekit.Common.VEContext;
import java.io.File;
import java.util.ArrayList;

/* loaded from: classes6.dex */
public class LogProfile extends Element {
    private LogProfileType logProfileType;
    private ArrayList<String> pathList;

    public LogProfile(VEContext vEContext, int i, String str, String str2) {
        super(vEContext, ElementType.LOG_PROFILE, i, str);
        this.TAG = getClass().getSimpleName();
        this.id = i;
        this.name = str;
        this.pathList = new ArrayList<>();
        this.logProfileType = LogProfileType.COMMON;
        setPath(str2);
    }

    public LogProfile(VEContext vEContext, int i, String str, String str2, String str3, String str4) {
        super(vEContext, ElementType.LOG_PROFILE, i, str);
        this.TAG = getClass().getSimpleName();
        this.id = i;
        this.name = str;
        this.pathList = new ArrayList<>();
        this.logProfileType = LogProfileType.ADAPTIVE;
        setPath(str2, str3, str4);
    }

    private void setPath(String str) {
        if (str.isEmpty()) {
            Log.e(this.TAG, "Path is Empty.");
        } else {
            if (!new File(str).exists()) {
                Log.e(this.TAG, "filterPath doesn't exist.");
                return;
            }
            this.pathList.clear();
            this.pathList.add(str);
            this.logProfileType = LogProfileType.COMMON;
        }
    }

    private void setPath(String str, String str2, String str3) {
        if (str.isEmpty() || str2.isEmpty() || str3.isEmpty()) {
            Log.e(this.TAG, "Some Path is Empty.");
            return;
        }
        if (!new File(str).exists()) {
            Log.e(this.TAG, "sdrPath doesn't exist.");
            return;
        }
        if (!new File(str2).exists()) {
            Log.e(this.TAG, "hdrPath doesn't exist.");
            return;
        }
        if (!new File(str3).exists()) {
            Log.e(this.TAG, "hlgPath doesn't exist.");
            return;
        }
        this.pathList.clear();
        this.pathList.add(str);
        this.pathList.add(str2);
        this.pathList.add(str3);
        this.logProfileType = LogProfileType.ADAPTIVE;
    }

    public ArrayList<String> getPathList() {
        return this.pathList;
    }

    public LogProfileType getLogProfileType() {
        return this.logProfileType;
    }

    public void setLogProfileType(LogProfileType logProfileType) {
        this.logProfileType = logProfileType;
    }
}
