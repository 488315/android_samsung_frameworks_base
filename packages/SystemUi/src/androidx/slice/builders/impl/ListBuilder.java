package androidx.slice.builders.impl;

import androidx.slice.builders.ListBuilder;
import java.time.Duration;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public interface ListBuilder {
    void addRow(ListBuilder.RowBuilder rowBuilder);

    void setHeader(ListBuilder.HeaderBuilder headerBuilder);

    void setTtl(long j);

    void setTtl(Duration duration);
}
