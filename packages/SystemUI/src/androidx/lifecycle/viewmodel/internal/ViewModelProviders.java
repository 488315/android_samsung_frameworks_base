package androidx.lifecycle.viewmodel.internal;

import androidx.lifecycle.viewmodel.CreationExtras;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ViewModelProviders {
    public static final ViewModelProviders INSTANCE = new ViewModelProviders();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ViewModelKey implements CreationExtras.Key {
        public static final ViewModelKey INSTANCE = new ViewModelKey();

        private ViewModelKey() {
        }
    }

    private ViewModelProviders() {
    }
}
