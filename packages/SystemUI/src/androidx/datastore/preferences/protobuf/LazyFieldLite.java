package androidx.datastore.preferences.protobuf;

import androidx.datastore.preferences.protobuf.GeneratedMessageLite;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class LazyFieldLite {
    public ByteString delayedBytes;
    public final ExtensionRegistryLite extensionRegistry;
    public volatile ByteString memoizedBytes;
    public volatile MessageLite value;

    public LazyFieldLite(ExtensionRegistryLite extensionRegistryLite, ByteString byteString) {
        if (extensionRegistryLite == null) {
            throw new NullPointerException("found null ExtensionRegistry");
        }
        if (byteString == null) {
            throw new NullPointerException("found null ByteString");
        }
        this.extensionRegistry = extensionRegistryLite;
        this.delayedBytes = byteString;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof LazyFieldLite)) {
            return false;
        }
        LazyFieldLite lazyFieldLite = (LazyFieldLite) obj;
        MessageLite messageLite = this.value;
        MessageLite messageLite2 = lazyFieldLite.value;
        return (messageLite == null && messageLite2 == null) ? toByteString().equals(lazyFieldLite.toByteString()) : (messageLite == null || messageLite2 == null) ? messageLite != null ? messageLite.equals(lazyFieldLite.getValue(messageLite.getDefaultInstanceForType$1())) : getValue(messageLite2.getDefaultInstanceForType$1()).equals(messageLite2) : messageLite.equals(messageLite2);
    }

    public final MessageLite getValue(MessageLite messageLite) {
        if (this.value == null) {
            synchronized (this) {
                if (this.value == null) {
                    try {
                        if (this.delayedBytes != null) {
                            GeneratedMessageLite generatedMessageLite = (GeneratedMessageLite) messageLite;
                            generatedMessageLite.getClass();
                            Parser parser = (Parser) generatedMessageLite.dynamicMethod(GeneratedMessageLite.MethodToInvoke.GET_PARSER);
                            AbstractParser abstractParser = (AbstractParser) parser;
                            this.value = abstractParser.parseFrom(this.extensionRegistry, this.delayedBytes);
                            this.memoizedBytes = this.delayedBytes;
                        } else {
                            this.value = messageLite;
                            this.memoizedBytes = ByteString.EMPTY;
                        }
                    } catch (InvalidProtocolBufferException unused) {
                        this.value = messageLite;
                        this.memoizedBytes = ByteString.EMPTY;
                    }
                }
            }
        }
        return this.value;
    }

    public int hashCode() {
        return 1;
    }

    public final ByteString toByteString() {
        if (this.memoizedBytes != null) {
            return this.memoizedBytes;
        }
        ByteString byteString = this.delayedBytes;
        if (byteString != null) {
            return byteString;
        }
        synchronized (this) {
            try {
                if (this.memoizedBytes != null) {
                    return this.memoizedBytes;
                }
                if (this.value == null) {
                    this.memoizedBytes = ByteString.EMPTY;
                } else {
                    this.memoizedBytes = ((AbstractMessageLite) this.value).toByteString();
                }
                return this.memoizedBytes;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public LazyFieldLite() {
    }
}
