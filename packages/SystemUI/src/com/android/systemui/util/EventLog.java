package com.android.systemui.util;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public interface EventLog {
    int writeEvent(int i, float f);

    int writeEvent(int i, int i2);

    int writeEvent(int i, long j);

    int writeEvent(int i, String str);

    int writeEvent(int i, Object... objArr);
}
