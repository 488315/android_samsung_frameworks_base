package com.android.systemui.log;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Locale;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.StringCompanionObject;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public interface LogMessage {
    default void dump(PrintWriter printWriter) {
        String format = LogMessageKt.DATE_FORMAT.format(Long.valueOf(getTimestamp()));
        String shortString = getLevel().getShortString();
        String str = (String) getMessagePrinter().invoke(this);
        if (getThreadId() > 0 || getTagSeparator() != null) {
            String tag = getTag();
            long threadId = getThreadId();
            Character tagSeparator = getTagSeparator();
            printWriter.print(format);
            printWriter.print(" ");
            if (threadId > 0) {
                int i = StringCompanionObject.$r8$clinit;
                printWriter.print(String.format(Locale.US, "%5d ", Arrays.copyOf(new Object[]{Long.valueOf(threadId)}, 1)));
            }
            printWriter.print(shortString);
            printWriter.print(" ");
            printWriter.print(tag);
            if (tagSeparator != null) {
                printWriter.print(tagSeparator.charValue());
            } else {
                printWriter.print(":");
            }
            printWriter.print(" ");
            printWriter.println(str);
        } else {
            String tag2 = getTag();
            printWriter.print(format);
            printWriter.print(" ");
            printWriter.print(shortString);
            printWriter.print(" ");
            printWriter.print(tag2);
            printWriter.print(": ");
            printWriter.println(str);
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
