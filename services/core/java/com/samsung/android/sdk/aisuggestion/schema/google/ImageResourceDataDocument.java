package com.samsung.android.sdk.aisuggestion.schema.google;

import android.app.appsearch.AppSearchSchema;
import android.app.appsearch.GenericDocument;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class ImageResourceDataDocument extends GenericDocument {
    public static final AppSearchSchema schema =
            new AppSearchSchema.Builder("ContextualInsightData:ImageResource")
                    .addProperty(
                            new AppSearchSchema.StringPropertyConfig.Builder("url")
                                    .setCardinality(2)
                                    .setTokenizerType(1)
                                    .setIndexingType(2)
                                    .setJoinableValueType(0)
                                    .build())
                    .build();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Builder extends GenericDocument.Builder {
        public Builder() {
            super("ContextualInsightData", "", "ContextualInsightData:ImageResource");
        }

        @Override // android.app.appsearch.GenericDocument.Builder
        public final ImageResourceDataDocument build() {
            return new ImageResourceDataDocument(super.build());
        }
    }

    public ImageResourceDataDocument(GenericDocument genericDocument) {
        super(genericDocument);
    }
}
