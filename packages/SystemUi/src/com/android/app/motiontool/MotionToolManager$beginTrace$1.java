package com.android.app.motiontool;

import android.media.permission.SafeCloseable;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
final /* synthetic */ class MotionToolManager$beginTrace$1 extends FunctionReferenceImpl implements Function0 {
    public MotionToolManager$beginTrace$1(Object obj) {
        super(0, obj, SafeCloseable.class, "close", "close()V", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        ((SafeCloseable) this.receiver).close();
        return Unit.INSTANCE;
    }
}
