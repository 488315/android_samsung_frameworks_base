package androidx.core.app;

import android.content.Context;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class TaskStackBuilder implements Iterable {
    public final ArrayList mIntents = new ArrayList();
    public final Context mSourceContext;

    private TaskStackBuilder(Context context) {
        this.mSourceContext = context;
    }

    public static TaskStackBuilder create(Context context) {
        return new TaskStackBuilder(context);
    }

    @Override // java.lang.Iterable
    public final Iterator iterator() {
        return this.mIntents.iterator();
    }
}
