package com.android.systemui.log.core;

import android.icu.text.SimpleDateFormat;
import java.io.PrintWriter;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface LogMessage {
    default void dump(PrintWriter printWriter) {
        SimpleDateFormat simpleDateFormat;
        PrintWriter printWriter2;
        simpleDateFormat = LogMessageKt.DATE_FORMAT;
        String format = simpleDateFormat.format(Long.valueOf(getTimestamp()));
        String shortString = getLevel().getShortString();
        String str = (String) getMessagePrinter().mo779invoke(this);
        if (getThreadId() > 0 || getTagSeparator() != null) {
            format.getClass();
            printWriter2 = printWriter;
            LogMessageKt.printLikeLogcat(printWriter2, format, shortString, getTag(), str, getThreadId(), getTagSeparator());
        } else {
            format.getClass();
            LogMessageKt.printLikeLogcat(printWriter, format, shortString, getTag(), str);
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
