package com.samsung.android.service.ProtectedATCommand.list;

import android.content.Context;

/* loaded from: classes5.dex */
public class CPCommand extends ICmdList {
    private Context mContext;

    public CPCommand(Context context) {
        try {
            this.cmdType = 162;
            addATCommands();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // com.samsung.android.service.ProtectedATCommand.list.ICmdList
    protected void addATCommands() {
        putAtCommands("AT+ENGMODES=8,0,3", this.cmdType);
        putAtCommands("AT+VERSNAME=*", this.cmdType);
        putAtCommands("AT+CALIDATE=*", this.cmdType);
        putAtCommands("AT+FACTORST=*", this.cmdType);
        putAtCommands("AT+MSLSECUR=*", this.cmdType);
        putAtCommands("AT+TEMPTEST=*", this.cmdType);
        putAtCommands("AT+SYSSLEEP=0,0", this.cmdType);
        putAtCommands("AT+SYSSLEEP=0,1", this.cmdType);
        putAtCommands("AT+SYSSLEEP=0,4", this.cmdType);
        putAtCommands("AT+LOCKREAD=*", this.cmdType);
        putAtCommands("AT+MAXPOWER=*", this.cmdType);
        putAtCommands("AT+SIMDETEC=*", this.cmdType);
        putAtCommands("AT+MODECHAN=*", this.cmdType);
        putAtCommands("AT+IMEICERT=*", this.cmdType);
        putAtCommands("AT+IMEISIGN=*", this.cmdType);
        putAtCommands("AT+AKSEEDNO=*", this.cmdType);
        putAtCommands("AT+BANSELCT=*", this.cmdType);
        putAtCommands("AT+SIMLOCKU=*", this.cmdType);
        putAtCommands("AT+HWINDICK=*", this.cmdType);
        putAtCommands("AT+LIFETIME=*", this.cmdType);
        putAtCommands("AT+CHNSELCT=*", this.cmdType);
        putAtCommands("AT+MEIDAUTH=*", this.cmdType);
        putAtCommands("AT+FAILDUMP=*", this.cmdType);
        putAtCommands("AT+READRSSI=*", this.cmdType);
        putAtCommands("AT+NAMCHECK=*", this.cmdType);
        putAtCommands("AT+PRLVERIF=*", this.cmdType);
        putAtCommands("AT+RECONDIT=*", this.cmdType);
        putAtCommands("AT+ERITTEST=*", this.cmdType);
        putAtCommands("AT+SVCBANDB=*", this.cmdType);
        putAtCommands("AT+PARALLEL=*", this.cmdType);
        putAtCommands("AT+OBDMTEST=*", this.cmdType);
        putAtCommands("AT+AOTKEYWR=*", this.cmdType);
        putAtCommands("AT+LOCKINFO=*", this.cmdType);
        putAtCommands("AT+RFBKOFFC=*", this.cmdType);
        putAtCommands("AT+SECNRSSI=*", this.cmdType);
        putAtCommands("AT+CALLCONN=*", this.cmdType);
        putAtCommands("AT+RFNVCHKS=*", this.cmdType);
        putAtCommands("AT+RFBYCODE=*", this.cmdType);
        putAtCommands("AT+PROVCASS=*", this.cmdType);
        putAtCommands("AT+CPMGCASS=*", this.cmdType);
    }
}
