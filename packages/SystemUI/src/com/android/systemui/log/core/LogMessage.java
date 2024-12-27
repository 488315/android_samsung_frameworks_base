package com.android.systemui.log.core;

import android.icu.text.SimpleDateFormat;
import java.io.PrintWriter;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

public interface LogMessage {
    default void dump(PrintWriter printWriter) {
        SimpleDateFormat simpleDateFormat;
        simpleDateFormat = LogMessageKt.DATE_FORMAT;
        String format = simpleDateFormat.format(Long.valueOf(getTimestamp()));
        String shortString = getLevel().getShortString();
        String str = (String) getMessagePrinter().invoke(this);
        if (getThreadId() > 0 || getTagSeparator() != null) {
            Intrinsics.checkNotNull(format);
            LogMessageKt.printLikeLogcat(printWriter, format, shortString, getTag(), str, getThreadId(), getTagSeparator());
        } else {
            Intrinsics.checkNotNull(format);
            LogMessageKt.printLikeLogcat(printWriter, format, shortString, getTag(), str);
        }
        Throwable exception = getException();
        if (exception != null) {
            exception.printStackTrace(printWriter);
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
