package io.reactivex;

/* loaded from: classes4.dex */
public abstract class Flowable {
    public static final int BUFFER_SIZE = Math.max(1, Integer.getInteger("rx2.buffer-size", 128).intValue());
}
