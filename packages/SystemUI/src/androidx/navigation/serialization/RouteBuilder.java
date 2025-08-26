package androidx.navigation.serialization;

import kotlinx.serialization.KSerializer;

/* loaded from: classes.dex */
public final class RouteBuilder {
    public final String path;
    public String pathArgs = "";
    public String queryArgs = "";
    public final KSerializer serializer;

    enum ParamType {
        PATH,
        QUERY
    }

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
