package com.android.app.viewcapture.data;

import com.google.protobuf.AbstractProtobufList;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Internal;
import com.google.protobuf.MessageLiteOrBuilder;
import com.google.protobuf.Parser;
import com.google.protobuf.ProtobufArrayList;
import com.google.protobuf.RawMessageInfo;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ViewNode extends GeneratedMessageLite implements MessageLiteOrBuilder {
    public static final int ALPHA_FIELD_NUMBER = 15;
    public static final int CHILDREN_FIELD_NUMBER = 3;
    public static final int CLASSNAME_INDEX_FIELD_NUMBER = 1;
    public static final int CLIPCHILDREN_FIELD_NUMBER = 17;
    private static final ViewNode DEFAULT_INSTANCE;
    public static final int ELEVATION_FIELD_NUMBER = 19;
    public static final int HASHCODE_FIELD_NUMBER = 2;
    public static final int HEIGHT_FIELD_NUMBER = 8;
    public static final int ID_FIELD_NUMBER = 4;
    public static final int LEFT_FIELD_NUMBER = 5;
    private static volatile Parser PARSER = null;
    public static final int SCALEX_FIELD_NUMBER = 13;
    public static final int SCALEY_FIELD_NUMBER = 14;
    public static final int SCROLLX_FIELD_NUMBER = 9;
    public static final int SCROLLY_FIELD_NUMBER = 10;
    public static final int TOP_FIELD_NUMBER = 6;
    public static final int TRANSLATIONX_FIELD_NUMBER = 11;
    public static final int TRANSLATIONY_FIELD_NUMBER = 12;
    public static final int VISIBILITY_FIELD_NUMBER = 18;
    public static final int WIDTH_FIELD_NUMBER = 7;
    public static final int WILLNOTDRAW_FIELD_NUMBER = 16;
    private int bitField0_;
    private int classnameIndex_;
    private boolean clipChildren_;
    private float elevation_;
    private int hashcode_;
    private int height_;
    private int left_;
    private int scrollX_;
    private int scrollY_;
    private int top_;
    private float translationX_;
    private float translationY_;
    private int visibility_;
    private int width_;
    private boolean willNotDraw_;
    private Internal.ProtobufList children_ = ProtobufArrayList.EMPTY_LIST;
    private String id_ = "";
    private float scaleX_ = 1.0f;
    private float scaleY_ = 1.0f;
    private float alpha_ = 1.0f;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.app.viewcapture.data.ViewNode$1 */
    public abstract /* synthetic */ class AbstractC06271 {

        /* renamed from: $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke */
        public static final /* synthetic */ int[] f203xa1df5c61;

        static {
            int[] iArr = new int[GeneratedMessageLite.MethodToInvoke.values().length];
            f203xa1df5c61 = iArr;
            try {
                iArr[GeneratedMessageLite.MethodToInvoke.NEW_MUTABLE_INSTANCE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f203xa1df5c61[GeneratedMessageLite.MethodToInvoke.NEW_BUILDER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f203xa1df5c61[GeneratedMessageLite.MethodToInvoke.BUILD_MESSAGE_INFO.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f203xa1df5c61[GeneratedMessageLite.MethodToInvoke.GET_DEFAULT_INSTANCE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f203xa1df5c61[GeneratedMessageLite.MethodToInvoke.GET_PARSER.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f203xa1df5c61[GeneratedMessageLite.MethodToInvoke.GET_MEMOIZED_IS_INITIALIZED.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f203xa1df5c61[GeneratedMessageLite.MethodToInvoke.SET_MEMOIZED_IS_INITIALIZED.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Builder extends GeneratedMessageLite.Builder implements MessageLiteOrBuilder {
        public /* synthetic */ Builder(AbstractC06271 abstractC06271) {
            this();
        }

        private Builder() {
            super(ViewNode.DEFAULT_INSTANCE);
        }
    }

    static {
        ViewNode viewNode = new ViewNode();
        DEFAULT_INSTANCE = viewNode;
        GeneratedMessageLite.registerDefaultInstance(ViewNode.class, viewNode);
    }

    private ViewNode() {
    }

    public static void access$100(ViewNode viewNode, int i) {
        viewNode.bitField0_ |= 1;
        viewNode.classnameIndex_ = i;
    }

    public static void access$1100(ViewNode viewNode, String str) {
        viewNode.getClass();
        str.getClass();
        viewNode.bitField0_ |= 4;
        viewNode.id_ = str;
    }

    public static void access$1400(ViewNode viewNode, int i) {
        viewNode.bitField0_ |= 8;
        viewNode.left_ = i;
    }

    public static void access$1600(ViewNode viewNode, int i) {
        viewNode.bitField0_ |= 16;
        viewNode.top_ = i;
    }

    public static void access$1800(ViewNode viewNode, int i) {
        viewNode.bitField0_ |= 32;
        viewNode.width_ = i;
    }

    public static void access$2000(ViewNode viewNode, int i) {
        viewNode.bitField0_ |= 64;
        viewNode.height_ = i;
    }

    public static void access$2200(ViewNode viewNode, int i) {
        viewNode.bitField0_ |= 128;
        viewNode.scrollX_ = i;
    }

    public static void access$2400(ViewNode viewNode, int i) {
        viewNode.bitField0_ |= 256;
        viewNode.scrollY_ = i;
    }

    public static void access$2600(ViewNode viewNode, float f) {
        viewNode.bitField0_ |= 512;
        viewNode.translationX_ = f;
    }

    public static void access$2800(ViewNode viewNode, float f) {
        viewNode.bitField0_ |= 1024;
        viewNode.translationY_ = f;
    }

    public static void access$300(ViewNode viewNode, int i) {
        viewNode.bitField0_ |= 2;
        viewNode.hashcode_ = i;
    }

    public static void access$3000(ViewNode viewNode, float f) {
        viewNode.bitField0_ |= 2048;
        viewNode.scaleX_ = f;
    }

    public static void access$3200(ViewNode viewNode, float f) {
        viewNode.bitField0_ |= 4096;
        viewNode.scaleY_ = f;
    }

    public static void access$3400(ViewNode viewNode, float f) {
        viewNode.bitField0_ |= 8192;
        viewNode.alpha_ = f;
    }

    public static void access$3600(ViewNode viewNode, boolean z) {
        viewNode.bitField0_ |= 16384;
        viewNode.willNotDraw_ = z;
    }

    public static void access$3800(ViewNode viewNode, boolean z) {
        viewNode.bitField0_ |= 32768;
        viewNode.clipChildren_ = z;
    }

    public static void access$4000(ViewNode viewNode, int i) {
        viewNode.bitField0_ |= 65536;
        viewNode.visibility_ = i;
    }

    public static void access$4200(ViewNode viewNode, float f) {
        viewNode.bitField0_ |= 131072;
        viewNode.elevation_ = f;
    }

    public static void access$600(ViewNode viewNode, ViewNode viewNode2) {
        viewNode.getClass();
        Internal.ProtobufList protobufList = viewNode.children_;
        if (!((AbstractProtobufList) protobufList).isMutable) {
            viewNode.children_ = GeneratedMessageLite.mutableCopy(protobufList);
        }
        viewNode.children_.add(viewNode2);
    }

    public static Builder newBuilder() {
        return (Builder) DEFAULT_INSTANCE.createBuilder();
    }

    @Override // com.google.protobuf.GeneratedMessageLite
    public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke) {
        AbstractC06271 abstractC06271 = null;
        switch (AbstractC06271.f203xa1df5c61[methodToInvoke.ordinal()]) {
            case 1:
                return new ViewNode();
            case 2:
                return new Builder(abstractC06271);
            case 3:
                return new RawMessageInfo(DEFAULT_INSTANCE, "\u0001\u0013\u0000\u0001\u0001\u0013\u0013\u0000\u0001\u0000\u0001င\u0000\u0002င\u0001\u0003\u001b\u0004ဈ\u0002\u0005င\u0003\u0006င\u0004\u0007င\u0005\bင\u0006\tင\u0007\nင\b\u000bခ\t\fခ\n\rခ\u000b\u000eခ\f\u000fခ\r\u0010ဇ\u000e\u0011ဇ\u000f\u0012င\u0010\u0013ခ\u0011", new Object[]{"bitField0_", "classnameIndex_", "hashcode_", "children_", ViewNode.class, "id_", "left_", "top_", "width_", "height_", "scrollX_", "scrollY_", "translationX_", "translationY_", "scaleX_", "scaleY_", "alpha_", "willNotDraw_", "clipChildren_", "visibility_", "elevation_"});
            case 4:
                return DEFAULT_INSTANCE;
            case 5:
                Parser parser = PARSER;
                if (parser == null) {
                    synchronized (ViewNode.class) {
                        parser = PARSER;
                        if (parser == null) {
                            parser = new GeneratedMessageLite.DefaultInstanceBasedParser(DEFAULT_INSTANCE);
                            PARSER = parser;
                        }
                    }
                }
                return parser;
            case 6:
                return (byte) 1;
            case 7:
                return null;
            default:
                throw new UnsupportedOperationException();
        }
    }
}
