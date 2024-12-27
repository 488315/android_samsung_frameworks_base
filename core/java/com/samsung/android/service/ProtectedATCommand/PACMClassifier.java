package com.samsung.android.service.ProtectedATCommand;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.util.Slog;

import com.samsung.android.service.ProtectedATCommand.list.ATCommands;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.StringTokenizer;

/* loaded from: classes6.dex */
public class PACMClassifier {
    public static final int NOK = 0;
    public static final int OK = 1;
    protected static final String TAG = "PACMClassifier";

    private static boolean checkNullParameter(Object... args) {
        int idx = 1;
        boolean isNull = false;
        for (Object arg : args) {
            if (arg == null) {
                StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
                Slog.e(
                        TAG,
                        NavigationBarInflaterView.SIZE_MOD_START
                                + stackTrace[2].getMethodName()
                                + "] Parameter("
                                + idx
                                + ") is null.");
                isNull = true;
            }
            idx++;
        }
        return isNull;
    }

    private static ATCommands findATCommands(
            LinkedHashMap<String, LinkedHashSet<ATCommands>> atMap, String name, ATCommands atCmd) {
        if (checkNullParameter(atMap, name, atCmd)) {
            return null;
        }
        LinkedHashSet<ATCommands> cmdSet = atMap.get(name);
        Iterator<ATCommands> it = cmdSet.iterator();
        while (it.hasNext()) {
            ATCommands CmdRet = it.next();
            if (atCmd.equals(CmdRet)) {
                return CmdRet;
            }
        }
        Slog.i(TAG, "findATCommands Failed to find command.");
        return null;
    }

    private static String getName(String cmd, String token) {
        if (cmd == null) {
            Slog.e(TAG, "getName cmd is null.");
            return null;
        }
        try {
            StringTokenizer st = new StringTokenizer(cmd);
            st.nextToken(token);
            if (st.hasMoreTokens()) {
                String tmp = st.nextToken();
                String name = tmp.split("=")[0];
                if (name != null) {
                    return name;
                }
            }
        } catch (Exception e) {
            Slog.e(TAG, "getName error occured.");
            e.printStackTrace();
        }
        return null;
    }

    public static ATCommands getCommand(
            LinkedHashMap<String, LinkedHashSet<ATCommands>> atMap, String cmd) {
        Slog.i(TAG, "getCommand() is called.");
        if (checkNullParameter(atMap, cmd)) {
            return new ATCommands();
        }
        String cmdName = cmd;
        try {
            String[] tokens = {"+", "$", "^", "#"};
            int length = tokens.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                String token = tokens[i];
                if (cmd.indexOf("AT" + token) != 0) {
                    i++;
                } else {
                    cmdName = getName(cmd, token);
                    break;
                }
            }
            if (cmdName == null) {
                Slog.e(
                        TAG,
                        "Failed to get cmd name(" + cmd + NavigationBarInflaterView.KEY_CODE_END);
                return new ATCommands();
            }
            LinkedHashSet<ATCommands> cmdPool = atMap.get(cmdName);
            if (cmdPool == null) {
                Slog.i(TAG, "This cmd(" + cmdName + ") is not registered");
                return new ATCommands();
            }
            ATCommands atCmd = new ATCommands(cmdName, cmd.getBytes());
            if (!cmdPool.contains(atCmd)) {
                Slog.i(TAG, "This cmd(" + cmdName + ") is not registered.");
                return new ATCommands();
            }
            ATCommands foundedRet = findATCommands(atMap, cmdName, atCmd);
            if (foundedRet == null) {
                Slog.e(TAG, "Failed to find AT Commands");
                return new ATCommands();
            }
            return foundedRet;
        } catch (Exception e) {
            e.printStackTrace();
            return new ATCommands();
        }
    }

    public static int putCommandList(
            LinkedHashMap<String, LinkedHashSet<ATCommands>> atMap,
            String cmdName,
            ATCommands atCmd) {
        if (checkNullParameter(atMap, cmdName, atCmd)) {
            return -268435456;
        }
        LinkedHashSet<ATCommands> base = null;
        if (atMap.containsKey(cmdName)) {
            LinkedHashSet<ATCommands> base2 = atMap.get(cmdName);
            base = base2;
            atMap.remove(cmdName);
        }
        if (base == null) {
            base = new LinkedHashSet<>();
        }
        base.add(atCmd);
        atMap.put(cmdName, base);
        return 1;
    }

    public static int putCommandList(
            LinkedHashMap<String, LinkedHashSet<ATCommands>> atMap, List<ATCommands> list) {
        if (checkNullParameter(atMap, list)) {
            return -268435456;
        }
        for (ATCommands CmdRet : list) {
            putCommandList(atMap, CmdRet.getName(), CmdRet);
        }
        return 1;
    }
}
