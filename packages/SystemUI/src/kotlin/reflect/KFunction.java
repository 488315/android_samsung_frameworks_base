package kotlin.reflect;

import kotlin.Function;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface KFunction extends KCallable, Function {
    boolean isExternal();

    boolean isInfix();

    boolean isInline();

    boolean isOperator();

    boolean isSuspend();
}
