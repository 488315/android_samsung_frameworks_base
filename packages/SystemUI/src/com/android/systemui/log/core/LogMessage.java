package com.android.systemui.log.core;

import java.io.PrintWriter;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public interface LogMessage {
    default void dump(PrintWriter printWriter) {
        PrintWriter printWriter2;
        String str = LogMessageKt.DATE_FORMAT.format(Long.valueOf(getTimestamp()));
        String shortString = getLevel().getShortString();
        String str2 = (String) getMessagePrinter().mo781invoke(this);
        if (getThreadId() > 0 || getTagSeparator() != null) {
            str.getClass();
            printWriter2 = printWriter;
            LogMessageKt.printLikeLogcat(printWriter2, str, shortString, getTag(), str2, getThreadId(), getTagSeparator());
        } else {
            str.getClass();
            LogMessageKt.printLikeLogcat(printWriter, str, shortString, getTag(), str2);
            printWriter2 = printWriter;
        }
        Throwable exception = getException();
        if (exception != null) {
            exception.printStackTrace(printWriter2);
        }
    }

    boolean getBool1();

    boolean getBool2();

    boolean getBool3();

    boolean getBool4();

    boolean getBool5();

    double getDouble1();

    Throwable getException();

    int getInt1();

    int getInt2();

    LogLevel getLevel();

    long getLong1();

    long getLong2();

    Function1 getMessagePrinter();

    String getStr1();

    String getStr2();

    String getStr3();

    String getTag();

    Character getTagSeparator();

    long getThreadId();

    long getTimestamp();

    void setBool1(boolean z);

    void setBool2(boolean z);

    void setBool3(boolean z);

    void setBool4(boolean z);

    void setBool5(boolean z);

    void setDouble1(double d);

    void setInt1(int i);

    void setInt2(int i);

    void setLong1(long j);

    void setLong2(long j);

    void setStr1(String str);

    void setStr2(String str);

    void setStr3(String str);

    void setTagSeparator(Character ch);

    void setThreadId(long j);
}
