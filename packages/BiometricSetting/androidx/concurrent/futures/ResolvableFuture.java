package androidx.concurrent.futures;

import androidx.profileinstaller.ProfileVerifier;

/* loaded from: classes.dex */
public final class ResolvableFuture<V> extends AbstractResolvableFuture<V> {
    public static <V> ResolvableFuture<V> create() {
        return new ResolvableFuture<>();
    }

    @Override // androidx.concurrent.futures.AbstractResolvableFuture
    public final boolean set(ProfileVerifier.CompilationStatus compilationStatus) {
        return super.set(compilationStatus);
    }
}
