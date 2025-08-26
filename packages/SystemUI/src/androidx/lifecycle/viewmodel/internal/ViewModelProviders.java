package androidx.lifecycle.viewmodel.internal;

import androidx.lifecycle.viewmodel.CreationExtras;

/* loaded from: classes.dex */
public final class ViewModelProviders {
    public static final ViewModelProviders INSTANCE = new ViewModelProviders();

    public final class ViewModelKey implements CreationExtras.Key {
        public static final ViewModelKey INSTANCE = new ViewModelKey();

        private ViewModelKey() {
        }
    }

    private ViewModelProviders() {
    }
}
