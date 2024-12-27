package com.android.server.notification;

import android.content.Context;

import com.samsung.android.sdk.scs.ai.text.category.DocumentCategoryClassifier;

public final class NotificationClassifier {
    public final Context mContext;
    public boolean mInitialized = false;
    public boolean mClassifierSupported = false;
    public DocumentCategoryClassifier mClassifier = null;
    public DocumentCategoryClassifier.ClassifyOptions mOptions = null;

    public NotificationClassifier(Context context) {
        this.mContext = context;
    }
}
