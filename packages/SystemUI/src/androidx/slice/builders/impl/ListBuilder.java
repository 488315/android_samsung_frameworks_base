package androidx.slice.builders.impl;

import androidx.slice.builders.ListBuilder;
import java.time.Duration;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface ListBuilder {
    void addRow(ListBuilder.RowBuilder rowBuilder);

    void setHeader(ListBuilder.HeaderBuilder headerBuilder);

    void setTtl(long j);

    void setTtl(Duration duration);
}
