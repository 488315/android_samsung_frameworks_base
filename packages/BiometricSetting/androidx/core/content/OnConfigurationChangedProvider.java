package androidx.core.content;

import android.content.res.Configuration;
import androidx.core.util.Consumer;
import androidx.fragment.app.FragmentManager$$ExternalSyntheticLambda0;

/* loaded from: classes.dex */
public interface OnConfigurationChangedProvider {
    void addOnConfigurationChangedListener(Consumer<Configuration> consumer);

    void removeOnConfigurationChangedListener(FragmentManager$$ExternalSyntheticLambda0 fragmentManager$$ExternalSyntheticLambda0);
}
