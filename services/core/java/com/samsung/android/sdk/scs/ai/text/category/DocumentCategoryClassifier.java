package com.samsung.android.sdk.scs.ai.text.category;

import android.content.Context;

import com.samsung.android.sdk.scs.ai.text.TextServiceExecutor;
import com.samsung.android.sdk.scs.base.feature.Feature;
import com.samsung.android.sdk.scs.base.utils.Log;

public final class DocumentCategoryClassifier {
    public final boolean isDocumentCategoryClassifierSupported;
    public final TextServiceExecutor mServiceExecutor;

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
