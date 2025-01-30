package com.android.wm.shell.bubbles;

import android.content.Context;
import android.content.LocusId;
import android.content.pm.LauncherApps;
import com.android.wm.shell.bubbles.Bubbles;
import com.android.wm.shell.bubbles.storage.BubbleEntity;
import com.android.wm.shell.bubbles.storage.BubblePersistentRepository;
import com.android.wm.shell.bubbles.storage.BubbleVolatileRepository;
import com.android.wm.shell.common.ShellExecutor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.internal.ContextScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class BubbleDataRepository {
    public Bubbles.BubbleMetadataFlagListener bubbleMetadataFlagListener;
    public final ContextScope ioScope = CoroutineScopeKt.CoroutineScope(Dispatchers.f667IO);
    public StandaloneCoroutine job;
    public final LauncherApps launcherApps;
    public final ShellExecutor mainExecutor;
    public final BubblePersistentRepository persistentRepository;
    public final BubbleVolatileRepository volatileRepository;

    public BubbleDataRepository(Context context, LauncherApps launcherApps, ShellExecutor shellExecutor) {
        this.launcherApps = launcherApps;
        this.mainExecutor = shellExecutor;
        this.volatileRepository = new BubbleVolatileRepository(launcherApps);
        this.persistentRepository = new BubblePersistentRepository(context);
    }

    public static List transform(List list) {
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Bubble bubble = (Bubble) it.next();
            int identifier = bubble.mUser.getIdentifier();
            String str = bubble.mPackageName;
            String str2 = bubble.mMetadataShortcutId;
            BubbleEntity bubbleEntity = null;
            if (str2 != null) {
                String str3 = bubble.mKey;
                int i = bubble.mDesiredHeight;
                int i2 = bubble.mDesiredHeightResId;
                String str4 = bubble.mTitle;
                int taskId = bubble.getTaskId();
                LocusId locusId = bubble.mLocusId;
                bubbleEntity = new BubbleEntity(identifier, str, str2, str3, i, i2, str4, taskId, locusId != null ? locusId.getId() : null, bubble.mIsDismissable);
            }
            if (bubbleEntity != null) {
                arrayList.add(bubbleEntity);
            }
        }
        return arrayList;
    }

    public final void persistToDisk() {
        this.job = BuildersKt.launch$default(this.ioScope, null, null, new BubbleDataRepository$persistToDisk$1(this.job, this, null), 3);
    }
}
