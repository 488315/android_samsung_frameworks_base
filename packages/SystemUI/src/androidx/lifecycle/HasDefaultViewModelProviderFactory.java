package androidx.lifecycle;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface HasDefaultViewModelProviderFactory {
    default CreationExtras getDefaultViewModelCreationExtras() {
        return CreationExtras.Empty.INSTANCE;
    }

    ViewModelProvider.Factory getDefaultViewModelProviderFactory();
}
