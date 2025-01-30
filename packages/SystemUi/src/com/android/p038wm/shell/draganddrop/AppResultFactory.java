package com.android.p038wm.shell.draganddrop;

import android.content.Context;
import com.android.p038wm.shell.draganddrop.ExecutableAppHolder;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class AppResultFactory {
    public final ArrayList mResolvers;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ResultExtra {
        public CharSequence mAppLabel;
        public boolean mNonResizeableAppOnly;
    }

    public AppResultFactory(Context context, ExecutableAppHolder.MultiInstanceBlockList multiInstanceBlockList, ExecutableAppHolder.MultiInstanceAllowList multiInstanceAllowList) {
        ArrayList arrayList = new ArrayList();
        this.mResolvers = arrayList;
        arrayList.add(new IntentResolver(context, multiInstanceBlockList));
        arrayList.add(new UriResolver(context, multiInstanceBlockList));
        arrayList.add(new TextClassifierResolver(context, multiInstanceBlockList));
        arrayList.add(new PlainTextResolver(context, multiInstanceBlockList));
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ((BaseResolver) ((Resolver) it.next())).mMultiInstanceAllowList = multiInstanceAllowList;
        }
    }
}
