package com.google.common.collect;

import com.google.common.base.Function;
import com.google.common.base.Objects;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class Tables {
    public static final /* synthetic */ int $r8$clinit = 0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class AbstractCell {
        public final boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof AbstractCell)) {
                return false;
            }
            AbstractCell abstractCell = (AbstractCell) obj;
            return Objects.equal(getRowKey(), abstractCell.getRowKey()) && Objects.equal(getColumnKey(), abstractCell.getColumnKey()) && Objects.equal(getValue(), abstractCell.getValue());
        }

        public abstract Object getColumnKey();

        public abstract Object getRowKey();

        public abstract Object getValue();

        public final int hashCode() {
            return Arrays.hashCode(new Object[]{getRowKey(), getColumnKey(), getValue()});
        }

        public final String toString() {
            return "(" + getRowKey() + "," + getColumnKey() + ")=" + getValue();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class ImmutableCell<R, C, V> extends AbstractCell implements Serializable {
        private static final long serialVersionUID = 0;
        private final C columnKey;
        private final R rowKey;
        private final V value;

        public ImmutableCell(R r, C c, V v) {
            this.rowKey = r;
            this.columnKey = c;
            this.value = v;
        }

        @Override // com.google.common.collect.Tables.AbstractCell
        public final Object getColumnKey() {
            return this.columnKey;
        }

        @Override // com.google.common.collect.Tables.AbstractCell
        public final Object getRowKey() {
            return this.rowKey;
        }

        @Override // com.google.common.collect.Tables.AbstractCell
        public final Object getValue() {
            return this.value;
        }
    }

    static {
        new Function() { // from class: com.google.common.collect.Tables.1
            @Override // com.google.common.base.Function
            public final Object apply(Object obj) {
                return Collections.unmodifiableMap((Map) obj);
            }
        };
    }

    private Tables() {
    }
}
