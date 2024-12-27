package com.samsung.android.sdk.aisuggestion.schema.google;

import android.app.appsearch.AppSearchSchema;
import android.app.appsearch.GenericDocument;

public final class TimeToLeaveSuggestionDataDocument extends GenericDocument {
    public static final AppSearchSchema schema =
            new AppSearchSchema.Builder("ContextualInsightData:TimeToLeaveSuggestion")
                    .addProperty(
                            new AppSearchSchema.LongPropertyConfig.Builder("schemaVersion")
                                    .setCardinality(2)
                                    .setIndexingType(0)
                                    .build())
                    .addProperty(
                            new AppSearchSchema.StringPropertyConfig.Builder("reasonDescription")
                                    .setCardinality(2)
                                    .setTokenizerType(1)
                                    .setIndexingType(2)
                                    .setJoinableValueType(0)
                                    .build())
                    .addProperty(
                            new AppSearchSchema.StringPropertyConfig.Builder("insightMessageTitle")
                                    .setCardinality(2)
                                    .setTokenizerType(1)
                                    .setIndexingType(2)
                                    .setJoinableValueType(0)
                                    .build())
                    .addProperty(
                            new AppSearchSchema.StringPropertyConfig.Builder(
                                            "insightMessageDescription")
                                    .setCardinality(2)
                                    .setTokenizerType(1)
                                    .setIndexingType(2)
                                    .setJoinableValueType(0)
                                    .build())
                    .addProperty(
                            new AppSearchSchema.StringPropertyConfig.Builder("ttlInfo")
                                    .setCardinality(2)
                                    .setTokenizerType(1)
                                    .setIndexingType(2)
                                    .setJoinableValueType(0)
                                    .build())
                    .addProperty(
                            new AppSearchSchema.StringPropertyConfig.Builder("scheduleName")
                                    .setCardinality(2)
                                    .setTokenizerType(1)
                                    .setIndexingType(2)
                                    .setJoinableValueType(0)
                                    .build())
                    .addProperty(
                            new AppSearchSchema.DocumentPropertyConfig.Builder(
                                            "directionsAction",
                                            "ContextualInsightData:PotentialAction")
                                    .setCardinality(2)
                                    .setShouldIndexNestedProperties(true)
                                    .build())
                    .build();

    public final class Builder extends GenericDocument.Builder {
        @Override // android.app.appsearch.GenericDocument.Builder
        public final TimeToLeaveSuggestionDataDocument build() {
            return new TimeToLeaveSuggestionDataDocument(super.build());
        }
    }

    public TimeToLeaveSuggestionDataDocument(GenericDocument genericDocument) {
        super(genericDocument);
    }
}
