package com.samsung.android.sdk.aisuggestion.schema.google;

import android.app.appsearch.AppSearchSchema;
import android.app.appsearch.GenericDocument;

public final class ContextualInsightDataDocument extends GenericDocument {
    public static final AppSearchSchema schema =
            new AppSearchSchema.Builder("ContextualInsightData:ContextualInsight")
                    .addProperty(
                            new AppSearchSchema.LongPropertyConfig.Builder("schemaVersion")
                                    .setCardinality(2)
                                    .setIndexingType(0)
                                    .build())
                    .addProperty(
                            new AppSearchSchema.StringPropertyConfig.Builder("currentLocale")
                                    .setCardinality(2)
                                    .setTokenizerType(1)
                                    .setIndexingType(2)
                                    .setJoinableValueType(0)
                                    .build())
                    .addProperty(
                            new AppSearchSchema.DocumentPropertyConfig.Builder(
                                            "tapAction", "ContextualInsightData:PotentialAction")
                                    .setCardinality(2)
                                    .setShouldIndexNestedProperties(true)
                                    .build())
                    .addProperty(
                            new AppSearchSchema.DocumentPropertyConfig.Builder(
                                            "dismissAction",
                                            "ContextualInsightData:PotentialAction")
                                    .setCardinality(2)
                                    .setShouldIndexNestedProperties(true)
                                    .build())
                    .addProperty(
                            new AppSearchSchema.DocumentPropertyConfig.Builder(
                                            "suggestedActions",
                                            "ContextualInsightData:PotentialAction")
                                    .setCardinality(2)
                                    .setShouldIndexNestedProperties(true)
                                    .build())
                    .addProperty(
                            new AppSearchSchema.DocumentPropertyConfig.Builder(
                                            "appDomain",
                                            "ContextualInsightData:TimeToLeaveSuggestion")
                                    .setCardinality(2)
                                    .setShouldIndexNestedProperties(true)
                                    .build())
                    .build();

    public final class Builder extends GenericDocument.Builder {
        @Override // android.app.appsearch.GenericDocument.Builder
        public final ContextualInsightDataDocument build() {
            return new ContextualInsightDataDocument(super.build());
        }
    }

    public ContextualInsightDataDocument(GenericDocument genericDocument) {
        super(genericDocument);
    }
}
