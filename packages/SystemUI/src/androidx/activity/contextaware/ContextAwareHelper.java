package androidx.activity.contextaware;

import androidx.activity.ComponentActivity;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/* loaded from: classes.dex */
public final class ContextAwareHelper {
    public volatile ComponentActivity context;
    public final Set listeners = new CopyOnWriteArraySet();
}
