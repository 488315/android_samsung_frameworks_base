package com.android.server.broadcastradio.hal2;

import android.hardware.broadcastradio.V2_0.DabTableEntry;
import android.hardware.broadcastradio.V2_0.ProgramIdentifier;
import android.hardware.radio.ProgramSelector;

import java.util.Objects;
import java.util.function.Function;

public final /* synthetic */ class Convert$$ExternalSyntheticLambda0 implements Function {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ProgramSelector.Identifier programIdentifierFromHal =
                        Convert.programIdentifierFromHal((ProgramIdentifier) obj);
                Objects.requireNonNull(programIdentifierFromHal);
                return programIdentifierFromHal;
            case 1:
                return Convert.programIdentifierFromHal((ProgramIdentifier) obj);
            case 2:
                ProgramSelector.Identifier identifier = (ProgramSelector.Identifier) obj;
                Objects.requireNonNull(identifier);
                return identifier;
            case 3:
                ProgramSelector.Identifier identifier2 = (ProgramSelector.Identifier) obj;
                ProgramIdentifier programIdentifier = new ProgramIdentifier();
                programIdentifier.type = identifier2.getType();
                programIdentifier.value = identifier2.getValue();
                return programIdentifier;
            case 4:
                return ((DabTableEntry) obj).label;
            default:
                return Integer.valueOf(((DabTableEntry) obj).frequency);
        }
    }
}
