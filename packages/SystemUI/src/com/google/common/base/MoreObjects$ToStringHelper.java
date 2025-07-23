package com.google.common.base;

import java.util.Arrays;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class MoreObjects$ToStringHelper {
    public final String className;
    public final ValueHolder holderHead;
    public ValueHolder holderTail;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class UnconditionalValueHolder extends ValueHolder {
        private UnconditionalValueHolder() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class ValueHolder {
        public ValueHolder next;
        public Object value;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder(32);
        sb.append(this.className);
        sb.append('{');
        ValueHolder valueHolder = this.holderHead.next;
        String str = "";
        while (valueHolder != null) {
            Object obj = valueHolder.value;
            boolean z = valueHolder instanceof UnconditionalValueHolder;
            sb.append(str);
            if (obj == null || !obj.getClass().isArray()) {
                sb.append(obj);
            } else {
                String deepToString = Arrays.deepToString(new Object[]{obj});
                sb.append((CharSequence) deepToString, 1, deepToString.length() - 1);
            }
            valueHolder = valueHolder.next;
            str = ", ";
        }
        sb.append('}');
        return sb.toString();
    }

    private MoreObjects$ToStringHelper(String str) {
        ValueHolder valueHolder = new ValueHolder();
        this.holderHead = valueHolder;
        this.holderTail = valueHolder;
        str.getClass();
        this.className = str;
    }
}
