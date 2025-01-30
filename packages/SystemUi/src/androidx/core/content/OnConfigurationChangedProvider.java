package androidx.core.content;

import androidx.core.util.Consumer;
import androidx.fragment.app.FragmentManager$$ExternalSyntheticLambda0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public interface OnConfigurationChangedProvider {
    void addOnConfigurationChangedListener(Consumer consumer);

    void removeOnConfigurationChangedListener(FragmentManager$$ExternalSyntheticLambda0 fragmentManager$$ExternalSyntheticLambda0);
}
