package com.android.systemui.recordissue;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.List;
import kotlin.Pair;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;

/* loaded from: classes2.dex */
public final class TagsHandler extends Handler {
    public final IssueRecordingState state;

    public TagsHandler(Looper looper, IssueRecordingState issueRecordingState) {
        super(looper);
        this.state = issueRecordingState;
    }

    @Override // android.os.Handler
    public final void handleMessage(Message message) {
        if (3 != message.what) {
            throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(message.what, "received unknown msg.what: "));
        }
        ArrayList<String> stringArrayList = message.getData().getStringArrayList("com.android.traceur.tags");
        ArrayList<String> stringArrayList2 = message.getData().getStringArrayList("com.android.traceur.tag_descriptions");
        if (stringArrayList == null || stringArrayList2 == null) {
            throw new IllegalArgumentException("Neither keys: " + stringArrayList + ", nor values: " + stringArrayList2 + " can be null");
        }
        List<Pair> listZip = CollectionsKt___CollectionsKt.zip(stringArrayList, stringArrayList2);
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(listZip, 10));
        for (Pair pair : listZip) {
            arrayList.add(pair.getFirst() + ": " + pair.getSecond());
        }
        this.state.getPrefs().edit().putStringSet("key_tagTitles", CollectionsKt___CollectionsKt.toSet(arrayList)).apply();
    }
}
