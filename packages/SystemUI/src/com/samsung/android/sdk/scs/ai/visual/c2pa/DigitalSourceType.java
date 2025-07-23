package com.samsung.android.sdk.scs.ai.visual.c2pa;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public enum DigitalSourceType {
    DIGITAL_CAPTURE("http://cv.iptc.org/newscodes/digitalsourcetype/digitalCapture"),
    NEGATIVE_FILM("http://cv.iptc.org/newscodes/digitalsourcetype/negativeFilm"),
    POSITIVE_FILM("http://cv.iptc.org/newscodes/digitalsourcetype/positiveFilm"),
    PRINT("http://cv.iptc.org/newscodes/digitalsourcetype/print"),
    MINOR_HUMAN_EDITS("http://cv.iptc.org/newscodes/digitalsourcetype/minorHumanEdits"),
    COMPOSITE_CAPTURE("http://cv.iptc.org/newscodes/digitalsourcetype/compositeCapture"),
    ALGORITHMICALLY_ENHANCED("http://cv.iptc.org/newscodes/digitalsourcetype/algorithmicallyEnhanced"),
    DATA_DRIVEN_MEDIA("http://cv.iptc.org/newscodes/digitalsourcetype/dataDrivenMedia"),
    DIGITAL_ART("http://cv.iptc.org/newscodes/digitalsourcetype/digitalArt"),
    VIRTUAL_RECORDING("http://cv.iptc.org/newscodes/digitalsourcetype/virtualRecording"),
    COMPOSITE_SYNTHETIC("http://cv.iptc.org/newscodes/digitalsourcetype/compositeSynthetic"),
    TRAINED_ALGORITHMIC_MEDIA("http://cv.iptc.org/newscodes/digitalsourcetype/trainedAlgorithmicMedia"),
    COMPOSITE_WITH_TRAINED_ALGORITHMIC_MEDIA("http://cv.iptc.org/newscodes/digitalsourcetype/compositeWithTrainedAlgorithmicMedia"),
    ALGORITHMIC_MEDIA("http://cv.iptc.org/newscodes/digitalsourcetype/algorithmicMedia");

    private final String uri;

    DigitalSourceType(String str) {
        this.uri = str;
    }

    public final String getUri() {
        return this.uri;
    }
}
