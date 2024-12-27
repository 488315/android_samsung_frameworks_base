package com.samsung.android.sdk.aisuggestion.schema.google;

import android.app.appsearch.AppSearchSchema;
import android.app.appsearch.GenericDocument;

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
