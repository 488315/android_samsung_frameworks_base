package androidx.compose.runtime.saveable;

/* loaded from: classes.dex */
public interface Saver<Original, Saveable> {
    Object restore(Object obj);

    Object save(SaverScope saverScope, Object obj);
}
