package com.android.server.notification;

import android.content.Context;

import com.samsung.android.sdk.scs.ai.text.category.DocumentCategoryClassifier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
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
