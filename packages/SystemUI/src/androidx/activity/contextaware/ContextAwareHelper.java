package androidx.activity.contextaware;

import androidx.activity.ComponentActivity;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ContextAwareHelper {
    public volatile ComponentActivity context;
    public final Set listeners = new CopyOnWriteArraySet();
}
