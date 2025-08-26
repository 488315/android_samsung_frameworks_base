package com.android.wm.shell.draganddrop;

import android.content.Context;
import com.android.wm.shell.draganddrop.ExecutableAppHolder;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class AppResultFactory {
    public final ArrayList mResolvers;

    public class ResultExtra {
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
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((BaseResolver) obj).mMultiInstanceAllowList = multiInstanceAllowList;
        }
    }
}
