package com.samsung.android.sdk.aisuggestion.schema.google;

import android.app.appsearch.AppSearchSchema;
import android.app.appsearch.GenericDocument;

import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knoxguard.service.SystemIntentProcessor;

public final class PotentialActionDataDocument extends GenericDocument {
    public static final AppSearchSchema schema =
            new AppSearchSchema.Builder("ContextualInsightData:PotentialAction")
                    .addProperty(
                            new AppSearchSchema.StringPropertyConfig.Builder("name")
                                    .setCardinality(2)
                                    .setTokenizerType(1)
                                    .setIndexingType(2)
                                    .setJoinableValueType(0)
                                    .build())
                    .addProperty(
                            new AppSearchSchema.StringPropertyConfig.Builder("description")
                                    .setCardinality(2)
                                    .setTokenizerType(1)
                                    .setIndexingType(2)
                                    .setJoinableValueType(0)
                                    .build())
                    .addProperty(
                            new AppSearchSchema.StringPropertyConfig.Builder("shortName")
                                    .setCardinality(2)
                                    .setTokenizerType(1)
                                    .setIndexingType(2)
                                    .setJoinableValueType(0)
                                    .build())
                    .addProperty(
                            new AppSearchSchema.StringPropertyConfig.Builder("shortDescription")
                                    .setCardinality(2)
                                    .setTokenizerType(1)
                                    .setIndexingType(2)
                                    .setJoinableValueType(0)
                                    .build())
                    .addProperty(
                            new AppSearchSchema.StringPropertyConfig.Builder(
                                            SystemIntentProcessor.KEY_URI)
                                    .setCardinality(2)
                                    .setTokenizerType(1)
                                    .setIndexingType(2)
                                    .setJoinableValueType(0)
                                    .build())
                    .addProperty(
                            new AppSearchSchema.DocumentPropertyConfig.Builder(
                                            "image", "ContextualInsightData:ImageResource")
                                    .setCardinality(2)
                                    .setShouldIndexNestedProperties(true)
                                    .build())
                    .addProperty(
                            new AppSearchSchema.DocumentPropertyConfig.Builder(
                                            KnoxCustomManagerService.ICON,
                                            "ContextualInsightData:ImageResource")
                                    .setCardinality(2)
                                    .setShouldIndexNestedProperties(true)
                                    .build())
                    .build();

    public final class Builder extends GenericDocument.Builder {
        public Builder() {
            super("ContextualInsightData", "", "ContextualInsightData:PotentialAction");
        }

        @Override // android.app.appsearch.GenericDocument.Builder
        public final PotentialActionDataDocument build() {
            return new PotentialActionDataDocument(super.build());
        }
    }

    public PotentialActionDataDocument(GenericDocument genericDocument) {
        super(genericDocument);
    }
}
