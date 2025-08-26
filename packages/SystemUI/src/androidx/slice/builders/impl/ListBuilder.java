package androidx.slice.builders.impl;

import androidx.slice.builders.ListBuilder;
import java.time.Duration;

/* loaded from: classes.dex */
public interface ListBuilder {
    void addRow(ListBuilder.RowBuilder rowBuilder);

    void setHeader(ListBuilder.HeaderBuilder headerBuilder);

    void setTtl(long j);

    void setTtl(Duration duration);
}
