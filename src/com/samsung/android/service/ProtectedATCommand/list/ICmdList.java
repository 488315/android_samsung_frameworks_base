package com.samsung.android.service.ProtectedATCommand.list;

import android.util.NtpTrustedTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

/* loaded from: classes6.dex */
public abstract class ICmdList {
    public static final boolean ALLOWED_DATA_0_TO_9 = false;
    public static final boolean NOT_ALLOWED_DATA_0_TO_9 = true;
    protected int cmdType;
    private LinkedHashSet<ATCommands> mCmdList = new LinkedHashSet<>();

    protected abstract void addATCommands();

    protected final void putAtCommands(String str, int i, boolean z) {
        ATCommands aTCommands;
        String trim = str.trim();
        String upperCase = trim.toUpperCase();
        String[] strArr = {"+", "$", "^", "#"};
        int i2 = 0;
        while (true) {
            if (i2 >= 4) {
                break;
            }
            String str2 = strArr[i2];
            if (upperCase.contains(str2)) {
                upperCase = upperCase.split("\\" + str2)[1];
                break;
            }
            i2++;
        }
        if (upperCase.contains("=")) {
            upperCase = upperCase.split("=")[0];
        }
        String str3 = upperCase;
        try {
            if (trim.contains(NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER)) {
                aTCommands = new ATCommands(str3, trim.toUpperCase().getBytes("UTF-8"), z, i, true);
            } else {
                aTCommands = new ATCommands(str3, trim.toUpperCase().getBytes("UTF-8"), z, i);
            }
            this.mCmdList.add(aTCommands);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected final void putAtCommands(String str, int i) {
        putAtCommands(str, i, false);
    }

    public final LinkedHashSet<ATCommands> getCmdSet() {
        return this.mCmdList;
    }

    public final List<ATCommands> getList() {
        return new ArrayList(this.mCmdList);
    }
}
