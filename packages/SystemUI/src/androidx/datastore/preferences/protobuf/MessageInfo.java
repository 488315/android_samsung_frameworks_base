package androidx.datastore.preferences.protobuf;

/* loaded from: classes.dex */
public interface MessageInfo {
    MessageLite getDefaultInstance();

    ProtoSyntax getSyntax();

    boolean isMessageSetWireFormat();
}
