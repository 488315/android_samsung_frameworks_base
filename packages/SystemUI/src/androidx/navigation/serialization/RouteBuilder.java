package androidx.navigation.serialization;

import kotlinx.serialization.KSerializer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class RouteBuilder {
    public final String path;
    public String pathArgs = "";
    public String queryArgs = "";
    public final KSerializer serializer;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    enum ParamType {
        PATH,
        QUERY
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ParamType.values().length];
            try {
                iArr[ParamType.PATH.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ParamType.QUERY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public RouteBuilder(KSerializer kSerializer) {
        this.serializer = kSerializer;
        this.path = kSerializer.getDescriptor().getSerialName();
    }

    public final void addQuery(String str, String str2) {
        this.queryArgs += (this.queryArgs.length() == 0 ? "?" : "&") + str + '=' + str2;
    }

    public RouteBuilder(String str, KSerializer kSerializer) {
        this.serializer = kSerializer;
        this.path = str;
    }
}
