package android.updatabledriver;

import com.android.framework.protobuf.AbstractMessageLite;
import com.android.framework.protobuf.ByteString;
import com.android.framework.protobuf.CodedInputStream;
import com.android.framework.protobuf.ExtensionRegistryLite;
import com.android.framework.protobuf.GeneratedMessageLite;
import com.android.framework.protobuf.Internal;
import com.android.framework.protobuf.InvalidProtocolBufferException;
import com.android.framework.protobuf.MessageLiteOrBuilder;
import com.android.framework.protobuf.Parser;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;

/* loaded from: classes4.dex */
public final class UpdatableDriverProto {

    public interface DenylistOrBuilder extends MessageLiteOrBuilder {
        String getPackageNames(int i);

        ByteString getPackageNamesBytes(int i);

        int getPackageNamesCount();

        List<String> getPackageNamesList();

        long getVersionCode();

        boolean hasVersionCode();
    }

    public interface DenylistsOrBuilder extends MessageLiteOrBuilder {
        Denylist getDenylists(int i);

        int getDenylistsCount();

        List<Denylist> getDenylistsList();
    }

    public static void registerAllExtensions(ExtensionRegistryLite extensionRegistryLite) {
    }

    private UpdatableDriverProto() {
    }

    public static final class Denylist extends GeneratedMessageLite<Denylist, Builder> implements DenylistOrBuilder {
        private static final Denylist DEFAULT_INSTANCE;
        public static final int PACKAGE_NAMES_FIELD_NUMBER = 2;
        private static volatile Parser<Denylist> PARSER = null;
        public static final int VERSION_CODE_FIELD_NUMBER = 1;
        private int bitField0_;
        private Internal.ProtobufList<String> packageNames_ = GeneratedMessageLite.emptyProtobufList();
        private long versionCode_;

        private Denylist() {
        }

        @Override // android.updatabledriver.UpdatableDriverProto.DenylistOrBuilder
        public boolean hasVersionCode() {
            return (this.bitField0_ & 1) != 0;
        }

        @Override // android.updatabledriver.UpdatableDriverProto.DenylistOrBuilder
        public long getVersionCode() {
            return this.versionCode_;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setVersionCode(long j) {
            this.bitField0_ |= 1;
            this.versionCode_ = j;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearVersionCode() {
            this.bitField0_ &= -2;
            this.versionCode_ = 0L;
        }

        @Override // android.updatabledriver.UpdatableDriverProto.DenylistOrBuilder
        public List<String> getPackageNamesList() {
            return this.packageNames_;
        }

        @Override // android.updatabledriver.UpdatableDriverProto.DenylistOrBuilder
        public int getPackageNamesCount() {
            return this.packageNames_.size();
        }

        @Override // android.updatabledriver.UpdatableDriverProto.DenylistOrBuilder
        public String getPackageNames(int i) {
            return this.packageNames_.get(i);
        }

        @Override // android.updatabledriver.UpdatableDriverProto.DenylistOrBuilder
        public ByteString getPackageNamesBytes(int i) {
            return ByteString.copyFromUtf8(this.packageNames_.get(i));
        }

        private void ensurePackageNamesIsMutable() {
            Internal.ProtobufList<String> protobufList = this.packageNames_;
            if (protobufList.isModifiable()) {
                return;
            }
            this.packageNames_ = GeneratedMessageLite.mutableCopy(protobufList);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setPackageNames(int i, String str) {
            str.getClass();
            ensurePackageNamesIsMutable();
            this.packageNames_.set(i, str);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addPackageNames(String str) {
            str.getClass();
            ensurePackageNamesIsMutable();
            this.packageNames_.add(str);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addAllPackageNames(Iterable<String> iterable) {
            ensurePackageNamesIsMutable();
            AbstractMessageLite.addAll((Iterable) iterable, (List) this.packageNames_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearPackageNames() {
            this.packageNames_ = GeneratedMessageLite.emptyProtobufList();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addPackageNamesBytes(ByteString byteString) {
            ensurePackageNamesIsMutable();
            this.packageNames_.add(byteString.toStringUtf8());
        }

        public static Denylist parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (Denylist) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteBuffer);
        }

        public static Denylist parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (Denylist) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
        }

        public static Denylist parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (Denylist) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString);
        }

        public static Denylist parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (Denylist) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static Denylist parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (Denylist) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr);
        }

        public static Denylist parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (Denylist) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static Denylist parseFrom(InputStream inputStream) throws IOException {
            return (Denylist) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static Denylist parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Denylist) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static Denylist parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (Denylist) parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static Denylist parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Denylist) parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static Denylist parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (Denylist) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream);
        }

        public static Denylist parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Denylist) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(Denylist denylist) {
            return DEFAULT_INSTANCE.createBuilder(denylist);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<Denylist, Builder> implements DenylistOrBuilder {
            /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
                this();
            }

            private Builder() {
                super(Denylist.DEFAULT_INSTANCE);
            }

            @Override // android.updatabledriver.UpdatableDriverProto.DenylistOrBuilder
            public boolean hasVersionCode() {
                return ((Denylist) this.instance).hasVersionCode();
            }

            @Override // android.updatabledriver.UpdatableDriverProto.DenylistOrBuilder
            public long getVersionCode() {
                return ((Denylist) this.instance).getVersionCode();
            }

            public Builder setVersionCode(long j) {
                copyOnWrite();
                ((Denylist) this.instance).setVersionCode(j);
                return this;
            }

            public Builder clearVersionCode() {
                copyOnWrite();
                ((Denylist) this.instance).clearVersionCode();
                return this;
            }

            @Override // android.updatabledriver.UpdatableDriverProto.DenylistOrBuilder
            public List<String> getPackageNamesList() {
                return Collections.unmodifiableList(((Denylist) this.instance).getPackageNamesList());
            }

            @Override // android.updatabledriver.UpdatableDriverProto.DenylistOrBuilder
            public int getPackageNamesCount() {
                return ((Denylist) this.instance).getPackageNamesCount();
            }

            @Override // android.updatabledriver.UpdatableDriverProto.DenylistOrBuilder
            public String getPackageNames(int i) {
                return ((Denylist) this.instance).getPackageNames(i);
            }

            @Override // android.updatabledriver.UpdatableDriverProto.DenylistOrBuilder
            public ByteString getPackageNamesBytes(int i) {
                return ((Denylist) this.instance).getPackageNamesBytes(i);
            }

            public Builder setPackageNames(int i, String str) {
                copyOnWrite();
                ((Denylist) this.instance).setPackageNames(i, str);
                return this;
            }

            public Builder addPackageNames(String str) {
                copyOnWrite();
                ((Denylist) this.instance).addPackageNames(str);
                return this;
            }

            public Builder addAllPackageNames(Iterable<String> iterable) {
                copyOnWrite();
                ((Denylist) this.instance).addAllPackageNames(iterable);
                return this;
            }

            public Builder clearPackageNames() {
                copyOnWrite();
                ((Denylist) this.instance).clearPackageNames();
                return this;
            }

            public Builder addPackageNamesBytes(ByteString byteString) {
                copyOnWrite();
                ((Denylist) this.instance).addPackageNamesBytes(byteString);
                return this;
            }
        }

        @Override // com.android.framework.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            Parser defaultInstanceBasedParser;
            int i = AnonymousClass1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[methodToInvoke.ordinal()];
            AnonymousClass1 anonymousClass1 = null;
            switch (i) {
                case 1:
                    return new Denylist();
                case 2:
                    return new Builder(anonymousClass1);
                case 3:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0001\u0000\u0001ဂ\u0000\u0002\u001a", new Object[]{"bitField0_", "versionCode_", "packageNames_"});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<Denylist> parser = PARSER;
                    if (parser != null) {
                        return parser;
                    }
                    synchronized (Denylist.class) {
                        defaultInstanceBasedParser = PARSER;
                        if (defaultInstanceBasedParser == null) {
                            defaultInstanceBasedParser = new GeneratedMessageLite.DefaultInstanceBasedParser(DEFAULT_INSTANCE);
                            PARSER = defaultInstanceBasedParser;
                        }
                    }
                    return defaultInstanceBasedParser;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            Denylist denylist = new Denylist();
            DEFAULT_INSTANCE = denylist;
            GeneratedMessageLite.registerDefaultInstance(Denylist.class, denylist);
        }

        public static Denylist getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<Denylist> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    /* renamed from: android.updatabledriver.UpdatableDriverProto$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke;

        static {
            int[] iArr = new int[GeneratedMessageLite.MethodToInvoke.values().length];
            $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke = iArr;
            try {
                iArr[GeneratedMessageLite.MethodToInvoke.NEW_MUTABLE_INSTANCE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[GeneratedMessageLite.MethodToInvoke.NEW_BUILDER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[GeneratedMessageLite.MethodToInvoke.BUILD_MESSAGE_INFO.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[GeneratedMessageLite.MethodToInvoke.GET_DEFAULT_INSTANCE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[GeneratedMessageLite.MethodToInvoke.GET_PARSER.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[GeneratedMessageLite.MethodToInvoke.GET_MEMOIZED_IS_INITIALIZED.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[GeneratedMessageLite.MethodToInvoke.SET_MEMOIZED_IS_INITIALIZED.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    public static final class Denylists extends GeneratedMessageLite<Denylists, Builder> implements DenylistsOrBuilder {
        private static final Denylists DEFAULT_INSTANCE;
        public static final int DENYLISTS_FIELD_NUMBER = 1;
        private static volatile Parser<Denylists> PARSER;
        private Internal.ProtobufList<Denylist> denylists_ = emptyProtobufList();

        private Denylists() {
        }

        @Override // android.updatabledriver.UpdatableDriverProto.DenylistsOrBuilder
        public List<Denylist> getDenylistsList() {
            return this.denylists_;
        }

        public List<? extends DenylistOrBuilder> getDenylistsOrBuilderList() {
            return this.denylists_;
        }

        @Override // android.updatabledriver.UpdatableDriverProto.DenylistsOrBuilder
        public int getDenylistsCount() {
            return this.denylists_.size();
        }

        @Override // android.updatabledriver.UpdatableDriverProto.DenylistsOrBuilder
        public Denylist getDenylists(int i) {
            return this.denylists_.get(i);
        }

        public DenylistOrBuilder getDenylistsOrBuilder(int i) {
            return this.denylists_.get(i);
        }

        private void ensureDenylistsIsMutable() {
            Internal.ProtobufList<Denylist> protobufList = this.denylists_;
            if (protobufList.isModifiable()) {
                return;
            }
            this.denylists_ = GeneratedMessageLite.mutableCopy(protobufList);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setDenylists(int i, Denylist denylist) {
            denylist.getClass();
            ensureDenylistsIsMutable();
            this.denylists_.set(i, denylist);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addDenylists(Denylist denylist) {
            denylist.getClass();
            ensureDenylistsIsMutable();
            this.denylists_.add(denylist);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addDenylists(int i, Denylist denylist) {
            denylist.getClass();
            ensureDenylistsIsMutable();
            this.denylists_.add(i, denylist);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addAllDenylists(Iterable<? extends Denylist> iterable) {
            ensureDenylistsIsMutable();
            AbstractMessageLite.addAll((Iterable) iterable, (List) this.denylists_);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearDenylists() {
            this.denylists_ = emptyProtobufList();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void removeDenylists(int i) {
            ensureDenylistsIsMutable();
            this.denylists_.remove(i);
        }

        public static Denylists parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (Denylists) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteBuffer);
        }

        public static Denylists parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (Denylists) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
        }

        public static Denylists parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (Denylists) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString);
        }

        public static Denylists parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (Denylists) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static Denylists parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (Denylists) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr);
        }

        public static Denylists parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (Denylists) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static Denylists parseFrom(InputStream inputStream) throws IOException {
            return (Denylists) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static Denylists parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Denylists) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static Denylists parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (Denylists) parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static Denylists parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Denylists) parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static Denylists parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (Denylists) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream);
        }

        public static Denylists parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (Denylists) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(Denylists denylists) {
            return DEFAULT_INSTANCE.createBuilder(denylists);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<Denylists, Builder> implements DenylistsOrBuilder {
            /* synthetic */ Builder(AnonymousClass1 anonymousClass1) {
                this();
            }

            private Builder() {
                super(Denylists.DEFAULT_INSTANCE);
            }

            @Override // android.updatabledriver.UpdatableDriverProto.DenylistsOrBuilder
            public List<Denylist> getDenylistsList() {
                return Collections.unmodifiableList(((Denylists) this.instance).getDenylistsList());
            }

            @Override // android.updatabledriver.UpdatableDriverProto.DenylistsOrBuilder
            public int getDenylistsCount() {
                return ((Denylists) this.instance).getDenylistsCount();
            }

            @Override // android.updatabledriver.UpdatableDriverProto.DenylistsOrBuilder
            public Denylist getDenylists(int i) {
                return ((Denylists) this.instance).getDenylists(i);
            }

            public Builder setDenylists(int i, Denylist denylist) {
                copyOnWrite();
                ((Denylists) this.instance).setDenylists(i, denylist);
                return this;
            }

            public Builder setDenylists(int i, Denylist.Builder builder) {
                copyOnWrite();
                ((Denylists) this.instance).setDenylists(i, builder.build());
                return this;
            }

            public Builder addDenylists(Denylist denylist) {
                copyOnWrite();
                ((Denylists) this.instance).addDenylists(denylist);
                return this;
            }

            public Builder addDenylists(int i, Denylist denylist) {
                copyOnWrite();
                ((Denylists) this.instance).addDenylists(i, denylist);
                return this;
            }

            public Builder addDenylists(Denylist.Builder builder) {
                copyOnWrite();
                ((Denylists) this.instance).addDenylists(builder.build());
                return this;
            }

            public Builder addDenylists(int i, Denylist.Builder builder) {
                copyOnWrite();
                ((Denylists) this.instance).addDenylists(i, builder.build());
                return this;
            }

            public Builder addAllDenylists(Iterable<? extends Denylist> iterable) {
                copyOnWrite();
                ((Denylists) this.instance).addAllDenylists(iterable);
                return this;
            }

            public Builder clearDenylists() {
                copyOnWrite();
                ((Denylists) this.instance).clearDenylists();
                return this;
            }

            public Builder removeDenylists(int i) {
                copyOnWrite();
                ((Denylists) this.instance).removeDenylists(i);
                return this;
            }
        }

        @Override // com.android.framework.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            Parser defaultInstanceBasedParser;
            int i = AnonymousClass1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[methodToInvoke.ordinal()];
            AnonymousClass1 anonymousClass1 = null;
            switch (i) {
                case 1:
                    return new Denylists();
                case 2:
                    return new Builder(anonymousClass1);
                case 3:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001b", new Object[]{"denylists_", Denylist.class});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<Denylists> parser = PARSER;
                    if (parser != null) {
                        return parser;
                    }
                    synchronized (Denylists.class) {
                        defaultInstanceBasedParser = PARSER;
                        if (defaultInstanceBasedParser == null) {
                            defaultInstanceBasedParser = new GeneratedMessageLite.DefaultInstanceBasedParser(DEFAULT_INSTANCE);
                            PARSER = defaultInstanceBasedParser;
                        }
                    }
                    return defaultInstanceBasedParser;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            Denylists denylists = new Denylists();
            DEFAULT_INSTANCE = denylists;
            GeneratedMessageLite.registerDefaultInstance(Denylists.class, denylists);
        }

        public static Denylists getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<Denylists> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }
}
