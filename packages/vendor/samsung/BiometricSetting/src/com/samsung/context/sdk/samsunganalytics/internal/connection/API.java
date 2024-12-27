package com.samsung.context.sdk.samsunganalytics.internal.connection;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'DATA_DELETE' uses external variables
	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:451)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByRegister(EnumVisitor.java:395)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:324)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:262)
	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:151)
	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:100)
 */
public final class API {
    public static final /* synthetic */ API[] $VALUES;
    public static final API DATA_DELETE;
    public static final API GET_POLICY;
    public static final API SEND_BUFFERED_LOG;
    public static final API SEND_LOG;
    Directory directory;
    Domain domain;
    HttpMethod method;

    static {
        Domain domain = Domain.REGISTRATION;
        Directory directory = Directory.DATA_DELETE;
        HttpMethod httpMethod = HttpMethod.POST;
        API api = new API("DATA_DELETE", 0, domain, directory, httpMethod);
        DATA_DELETE = api;
        API api2 =
                new API(
                        "GET_POLICY",
                        1,
                        Domain.POLICY,
                        Directory.DEVICE_CONTROLLER_DIR,
                        HttpMethod.GET);
        GET_POLICY = api2;
        Domain domain2 = Domain.DLS;
        API api3 = new API("SEND_LOG", 2, domain2, Directory.DLS_DIR, httpMethod);
        SEND_LOG = api3;
        API api4 = new API("SEND_BUFFERED_LOG", 3, domain2, Directory.DLS_DIR_BAT, httpMethod);
        SEND_BUFFERED_LOG = api4;
        $VALUES = new API[] {api, api2, api3, api4};
    }

    public API(String str, int i, Domain domain, Directory directory, HttpMethod httpMethod) {
        this.domain = domain;
        this.directory = directory;
        this.method = httpMethod;
    }

    public static API valueOf(String str) {
        return (API) Enum.valueOf(API.class, str);
    }

    public static API[] values() {
        return (API[]) $VALUES.clone();
    }

    public final String getMethod() {
        return this.method.method;
    }

    public final String getUrl() {
        return this.domain.domain + this.directory.directory;
    }
}
