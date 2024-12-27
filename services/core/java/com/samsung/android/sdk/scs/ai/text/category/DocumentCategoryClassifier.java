package com.samsung.android.sdk.scs.ai.text.category;

import android.content.Context;

import com.samsung.android.sdk.scs.ai.text.TextServiceExecutor;
import com.samsung.android.sdk.scs.base.feature.Feature;
import com.samsung.android.sdk.scs.base.utils.Log;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class DocumentCategoryClassifier {
    public final boolean isDocumentCategoryClassifierSupported;
    public final TextServiceExecutor mServiceExecutor;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ClassifyOptions {
        public String country;
        public String language;
        public String requestType;
    }

    static {
    }

    public DocumentCategoryClassifier(Context context) {
        this.isDocumentCategoryClassifierSupported = Feature.checkFeature(context) == 0;
        Log.d("ScsApi@DocumentCategoryClassifier", "initConnection");
        this.mServiceExecutor = new TextServiceExecutor(context);
    }
}
