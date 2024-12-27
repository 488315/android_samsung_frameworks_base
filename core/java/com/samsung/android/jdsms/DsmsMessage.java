package com.samsung.android.jdsms;

/* loaded from: classes6.dex */
final class DsmsMessage {
    private static final String SUBTAG = "[DsmsMessage] ";
    private final String mDetail;
    private final String mFeatureCode;
    private final Long mValue;

    public DsmsMessage(String featureCode) {
        validateFeatureCode(featureCode);
        this.mFeatureCode = featureCode;
        this.mDetail = null;
        this.mValue = null;
    }

    public DsmsMessage(String featureCode, String detail) {
        validateFeatureCode(featureCode);
        validateDetail(detail);
        this.mFeatureCode = featureCode;
        this.mDetail = detail;
        this.mValue = null;
    }

    public DsmsMessage(String featureCode, Long value) {
        validateFeatureCode(featureCode);
        validateValue(value);
        this.mFeatureCode = featureCode;
        this.mDetail = null;
        this.mValue = value;
    }

    public DsmsMessage(String featureCode, String detail, Long value) {
        validateFeatureCode(featureCode);
        validateDetail(detail);
        validateValue(value);
        this.mFeatureCode = featureCode;
        this.mDetail = detail;
        this.mValue = value;
    }

    public String getFeatureCode() {
        return this.mFeatureCode;
    }

    public String getDetail() {
        return this.mDetail;
    }

    public Long getValue() {
        return this.mValue;
    }

    public String toString() {
        return "{'" + this.mFeatureCode + "', '" + this.mDetail + "', " + this.mValue + "}";
    }

    private static void validateDetail(String detail) {
        if (detail == null) {
            throw new IllegalArgumentException("DSMS-FRAMEWORK[DsmsMessage] Detail field is null");
        }
    }

    private static void validateValue(Long value) {
        if (value == null) {
            throw new IllegalArgumentException("DSMS-FRAMEWORK[DsmsMessage] Value field is null");
        }
    }

    private static void validateFeatureCode(String featureCode) {
        if (featureCode == null) {
            throw new IllegalArgumentException("DSMS-FRAMEWORK[DsmsMessage] Identifier is null");
        }
    }
}
