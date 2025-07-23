package com.google.protobuf.nano;

import java.io.IOException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class InvalidProtocolBufferNanoException extends IOException {
    private static final long serialVersionUID = -1616151763072450476L;

    public InvalidProtocolBufferNanoException(String str) {
        super(str);
    }

    public static InvalidProtocolBufferNanoException truncatedMessage() {
        return new InvalidProtocolBufferNanoException("While parsing a protocol message, the input ended unexpectedly in the middle of a field.  This could mean either that the input has been truncated or that an embedded message misreported its own length.");
    }
}
