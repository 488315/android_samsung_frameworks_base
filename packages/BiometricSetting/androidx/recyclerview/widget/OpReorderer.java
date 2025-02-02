package androidx.recyclerview.widget;

/* loaded from: classes.dex */
final class OpReorderer {
    final Callback mCallback;

    interface Callback {
    }

    OpReorderer(Callback callback) {
        this.mCallback = callback;
    }
}
