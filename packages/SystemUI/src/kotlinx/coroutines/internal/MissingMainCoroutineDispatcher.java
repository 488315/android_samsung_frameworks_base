package kotlinx.coroutines.internal;

import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.notification.row.RowInflaterTask$$ExternalSyntheticOutline0;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.Delay;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.MainCoroutineDispatcher;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class MissingMainCoroutineDispatcher extends MainCoroutineDispatcher implements Delay {
    public final Throwable cause;
    public final String errorHint;

    public /* synthetic */ MissingMainCoroutineDispatcher(Throwable th, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(th, (i & 2) != 0 ? null : str);
    }

    @Override // kotlinx.coroutines.CoroutineDispatcher
    public final void dispatch(CoroutineContext coroutineContext, Runnable runnable) {
        missing();
        throw null;
    }

    @Override // kotlinx.coroutines.Delay
    public final DisposableHandle invokeOnTimeout(long j, Runnable runnable, CoroutineContext coroutineContext) {
        missing();
        throw null;
    }

    @Override // kotlinx.coroutines.CoroutineDispatcher
    public final boolean isDispatchNeeded(CoroutineContext coroutineContext) {
        missing();
        throw null;
    }

    @Override // kotlinx.coroutines.MainCoroutineDispatcher, kotlinx.coroutines.CoroutineDispatcher
    public final CoroutineDispatcher limitedParallelism(int i) {
        missing();
        throw null;
    }

    public final void missing() {
        String str;
        if (this.cause == null) {
            throw new IllegalStateException("Module with the Main dispatcher is missing. Add dependency providing the Main dispatcher, e.g. 'kotlinx-coroutines-android' and ensure it has the same version as 'kotlinx-coroutines-core'");
        }
        String str2 = this.errorHint;
        if (str2 == null || (str = ". ".concat(str2)) == null) {
            str = "";
        }
        throw new IllegalStateException("Module with the Main dispatcher had failed to initialize".concat(str), this.cause);
    }

    @Override // kotlinx.coroutines.Delay
    public final void scheduleResumeAfterDelay(long j, CancellableContinuationImpl cancellableContinuationImpl) {
        missing();
        throw null;
    }

    @Override // kotlinx.coroutines.MainCoroutineDispatcher, kotlinx.coroutines.CoroutineDispatcher
    public final String toString() {
        Throwable th = this.cause;
        return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Dispatchers.Main[missing", th != null ? RowInflaterTask$$ExternalSyntheticOutline0.m(", cause=", th) : "", "]");
    }

    public MissingMainCoroutineDispatcher(Throwable th, String str) {
        this.cause = th;
        this.errorHint = str;
    }

    @Override // kotlinx.coroutines.MainCoroutineDispatcher
    public final MainCoroutineDispatcher getImmediate() {
        return this;
    }
}
