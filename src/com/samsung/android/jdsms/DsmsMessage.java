package com.samsung.android.jdsms;

/* loaded from: classes6.dex */
final class DsmsMessage {
    private static final String SUBTAG = "[DsmsMessage] ";
    private final String mDetail;
    private final String mFeatureCode;
    private final Long mValue;

    public DsmsMessage(String str) {
        validateFeatureCode(str);
        this.mFeatureCode = str;
        this.mDetail = null;
        this.mValue = null;
    }

    public DsmsMessage(String str, String str2) {
        validateFeatureCode(str);
        validateDetail(str2);
        this.mFeatureCode = str;
        this.mDetail = str2;
        this.mValue = null;
    }

    public DsmsMessage(String str, Long l) {
        validateFeatureCode(str);
        validateValue(l);
        this.mFeatureCode = str;
        this.mDetail = null;
        this.mValue = l;
    }

    public DsmsMessage(String str, String str2, Long l) {
        validateFeatureCode(str);
        validateDetail(str2);
        validateValue(l);
        this.mFeatureCode = str;
        this.mDetail = str2;
        this.mValue = l;
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

    private static void validateDetail(String str) {
        if (str == null) {
            throw new IllegalArgumentException("DSMS-FRAMEWORK[DsmsMessage] Detail field is null");
        }
    }

    private static void validateValue(Long l) {
        if (l == null) {
            throw new IllegalArgumentException("DSMS-FRAMEWORK[DsmsMessage] Value field is null");
        }
    }

    private static void validateFeatureCode(String str) {
        if (str == null) {
            throw new IllegalArgumentException("DSMS-FRAMEWORK[DsmsMessage] Identifier is null");
        }
    }
}
