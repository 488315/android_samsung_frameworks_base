package com.samsung.android.service.ProtectedATCommand.list;

import android.content.Context;

/* loaded from: classes5.dex */
public class UserOpenCommand extends ICmdList {
    private Context mContext;

    public UserOpenCommand(Context context) {
        try {
            this.cmdType = 161;
            addATCommands();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // com.samsung.android.service.ProtectedATCommand.list.ICmdList
    protected void addATCommands() {
        putAtCommands("AT+ENGMODES=8,0,0", this.cmdType);
        putAtCommands("AT+ENGMODES=7,0,1,0,0|SLO", this.cmdType);
        putAtCommands("AT+ENGMODES=7,1,0,0,0|CRO(VZW,EUX,EUR,XME)", this.cmdType);
        putAtCommands("AT+ENGMODES=7,1,1,0,0|SLO|CRO(VZW,EUX,EUR,XME)", this.cmdType);
        putAtCommands("AT+ENGMODES=7,2,0,0,0|CRB(VZW,EUX,EUR,XME)", this.cmdType);
        putAtCommands("AT+ENGMODES=7,2,1,0,0|SLO|CRB(VZW,EUX,EUR,XME)", this.cmdType);
        putAtCommands("AT+ENGMODES=7,0,0,1,0|SBB", this.cmdType);
        putAtCommands("AT+ENGMODES=7,0,0,2,0|FBOAD", this.cmdType);
        putAtCommands("AT+ENGMODES=7,0,0,3,0|FBOA", this.cmdType);
        putAtCommands("AT+ENGMODES=7,0,0,4,0|FBOD", this.cmdType);
        putAtCommands("AT+ENGMODES=7,0,0,5,0|CSO", this.cmdType);
        putAtCommands("AT+ENGMODES=7,0,0,0,1|ABO", this.cmdType);
        putAtCommands("AT+DISPTEST=0,0", this.cmdType);
        putAtCommands("AT+DISPTEST=0,1", this.cmdType);
        putAtCommands("AT+DISPTEST=0,2", this.cmdType);
        putAtCommands("AT+DISPTEST=0,3", this.cmdType);
        putAtCommands("AT+DISPTEST=0,4", this.cmdType);
        putAtCommands("AT+DISPTEST=0,5", this.cmdType);
        putAtCommands("AT+DISPTEST=0,6", this.cmdType);
        putAtCommands("AT+DISPTEST=0,7", this.cmdType);
        putAtCommands("AT+DISPTEST=0,9", this.cmdType);
        putAtCommands("AT+DISPTEST=1,0", this.cmdType);
        putAtCommands("AT+DISPTEST=1,1", this.cmdType);
        putAtCommands("AT+DISPTEST=1,5", this.cmdType);
        putAtCommands("AT+DISPTEST=3,0", this.cmdType);
        putAtCommands("AT+DISPTEST=3,1", this.cmdType);
        putAtCommands("AT+DISPTEST=3,2", this.cmdType);
        putAtCommands("AT+DISPTEST=3,3", this.cmdType);
        putAtCommands("AT+DISPTEST=3,5", this.cmdType);
        putAtCommands("AT+DISPTEST=3,6", this.cmdType);
        putAtCommands("AT+DISPTEST=3,7", this.cmdType);
        putAtCommands("AT+DISPTEST=3,8", this.cmdType);
        putAtCommands("AT+DISPTEST=3,9", this.cmdType);
        putAtCommands("AT+DISPTEST=4,0", this.cmdType);
        putAtCommands("AT+DISPTEST=4,1", this.cmdType);
        putAtCommands("AT+DISPTEST=4,2", this.cmdType);
        putAtCommands("AT+DISPTEST=5,0", this.cmdType);
        putAtCommands("AT+DISPTEST=5,1", this.cmdType);
        putAtCommands("AT+DISPTEST=5,2", this.cmdType);
        putAtCommands("AT+DISPTEST=5,3", this.cmdType);
        putAtCommands("AT+DISPTEST=5,4", this.cmdType);
        putAtCommands("AT+DISPTEST=5,5", this.cmdType);
        putAtCommands("AT+IMEITEST=*|SLO|ABO", this.cmdType);
        putAtCommands("AT+IMEMTEST=*", this.cmdType);
        putAtCommands("AT+BATTTEST=1,2", this.cmdType);
        putAtCommands("AT+BATTTEST=1,6", this.cmdType);
        putAtCommands("AT+BATTTEST=3,2", this.cmdType);
        putAtCommands("AT+BATTTEST=4,0", this.cmdType);
        putAtCommands("AT+BATTTEST=4,1", this.cmdType);
        putAtCommands("AT+BATTTEST=1,7", this.cmdType);
        putAtCommands("AT+BATTTEST=1,8", this.cmdType);
        putAtCommands("AT+BATTTEST=2,1", this.cmdType);
        putAtCommands("AT+BATTTEST=3,0", this.cmdType);
        putAtCommands("AT+BATTTEST=3,1", this.cmdType);
        putAtCommands("AT+BATTTEST=3,3", this.cmdType);
        putAtCommands("AT+BATTTEST=3,4", this.cmdType);
        putAtCommands("AT+BTIDTEST=*", this.cmdType);
        putAtCommands("AT+WIFIIDRW=*", this.cmdType);
        putAtCommands("AT+FUELGAIC=*", this.cmdType);
        putAtCommands("AT+SPKSTEST=3,3,0", this.cmdType);
        putAtCommands("AT+SPKSTEST=3,3,1", this.cmdType);
        putAtCommands("AT+SPKSTEST=3,3,2", this.cmdType);
        putAtCommands("AT+SPKSTEST=3,3,3", this.cmdType);
        putAtCommands("AT+EMEMTEST=*", this.cmdType);
        putAtCommands("AT+HMACMISM=*", this.cmdType);
        putAtCommands("AT+IFPMICCN=0,0,5,0", this.cmdType);
        putAtCommands("AT+IFPMICCN=0,0,5,1", this.cmdType);
        putAtCommands("AT+CAMETEST=0,0,0,1", this.cmdType);
        putAtCommands("AT+CAMETEST=0,1,0,0", this.cmdType);
        putAtCommands("AT+CAMETEST=0,1,0,1", this.cmdType);
        putAtCommands("AT+CAMETEST=0,1,0,2", this.cmdType);
        putAtCommands("AT+CAMETEST=0,1,0,4", this.cmdType);
        putAtCommands("AT+CAMETEST=0,1,0,5", this.cmdType);
        putAtCommands("AT+CAMETEST=0,1,1,0", this.cmdType);
        putAtCommands("AT+CAMETEST=0,1,2,0", this.cmdType);
        putAtCommands("AT+CAMETEST=0,1,2,2", this.cmdType);
        putAtCommands("AT+CAMETEST=0,1,2,3", this.cmdType);
        putAtCommands("AT+CAMETEST=0,2,2,1", this.cmdType);
        putAtCommands("AT+CAMETEST=0,2,2,3", this.cmdType);
        putAtCommands("AT+CAMETEST=0,3,2,0", this.cmdType);
        putAtCommands("AT+CAMETEST=0,3,3,0", this.cmdType);
        putAtCommands("AT+CAMETEST=0,9,0,0", this.cmdType);
        putAtCommands("AT+CAMETEST=0,9,1,0", this.cmdType);
        putAtCommands("AT+CAMETEST=1,2,0,0", this.cmdType);
        putAtCommands("AT+UARTSWIT=*", this.cmdType);
        putAtCommands("AT+FCBTTEST=*", this.cmdType);
        putAtCommands("AT+BTLETEST=*", this.cmdType);
        putAtCommands("AT+FCEPTEST=0,0,0,1", this.cmdType);
        putAtCommands("AT+FCEPTEST=0,0,1,0", this.cmdType);
        putAtCommands("AT+LOOPTEST=0,0,2", this.cmdType);
        putAtCommands("AT+LOOPTEST=0,0,5", this.cmdType);
        putAtCommands("AT+LOOPTEST=0,1,0", this.cmdType);
        putAtCommands("AT+LOOPTEST=0,1,5", this.cmdType);
        putAtCommands("AT+FAILHIST=*", this.cmdType);
        putAtCommands("AT+RAPPLIST=*", this.cmdType);
        putAtCommands("AT+RAMSIZEC=*", this.cmdType);
        putAtCommands("AT+SECUREBT=*", this.cmdType);
        putAtCommands("AT+WPROTECT=*", this.cmdType);
        putAtCommands("AT+POWRESET=*", this.cmdType);
        putAtCommands("AT+WIFITEST=*", this.cmdType);
        putAtCommands("AT+PAYMENTS=*", this.cmdType);
        putAtCommands("AT+FIRMVERS=*", this.cmdType);
        putAtCommands("AT+EWRITECK=0,0", this.cmdType);
        putAtCommands("AT+EWRITECK=0,1", this.cmdType);
        putAtCommands("AT+EWRITECK=0,2", this.cmdType);
        putAtCommands("AT+EWRITECK=0,3", this.cmdType);
        putAtCommands("AT+EWRITECK=0,4", this.cmdType);
        putAtCommands("AT+EWRITECK=0,5", this.cmdType);
        putAtCommands("AT+EWRITECK=0,6", this.cmdType);
        putAtCommands("AT+EWRITECK=1,0", this.cmdType);
        putAtCommands("AT+EWRITECK=1,1", this.cmdType);
        putAtCommands("AT+EWRITECK=1,2", this.cmdType);
        putAtCommands("AT+EWRITECK=1,6", this.cmdType);
        putAtCommands("AT+EWRITECK=1,9", this.cmdType);
        putAtCommands("AT+EWRITECK=3,6", this.cmdType);
        putAtCommands("AT+SYSSCOPE=*|SLO", this.cmdType);
        putAtCommands("AT+FLCRFCAL=0,0", this.cmdType);
        putAtCommands("AT+FLCRFCAL=0,1", this.cmdType);
        putAtCommands("AT+FLCRFCAL=1,*", this.cmdType);
        putAtCommands("AT+FLCRFCAL=2,0", this.cmdType);
        putAtCommands("AT+FLCRFCAL=2,1", this.cmdType);
        putAtCommands("AT+FLCRFCAL=3,0", this.cmdType);
        putAtCommands("AT+FLCRFCAL=4,1", this.cmdType);
        putAtCommands("AT+FLCRFCAL=4,2", this.cmdType);
        putAtCommands("AT+BAROMETE=*", this.cmdType);
        putAtCommands("AT+KSTRINGB=*", this.cmdType);
        putAtCommands("AT+IDCHIPTT=0,1,0", this.cmdType);
        putAtCommands("AT+IDCHIPTT=0,2,0", this.cmdType);
        putAtCommands("AT+IDCHIPTT=0,2,1", this.cmdType);
        putAtCommands("AT+IDCHIPTT=1,0,0", this.cmdType);
        putAtCommands("AT+UENCRYPT=*", this.cmdType);
        putAtCommands("AT+GPSSTEST=*", this.cmdType);
        putAtCommands("AT+NFCMTEST=*", this.cmdType);
        putAtCommands("AT+SENSORHB=*", this.cmdType);
        putAtCommands("AT+ACSENSOR=*", this.cmdType);
        putAtCommands("AT+GYROSCOP=*", this.cmdType);
        putAtCommands("AT+GEOMAGSS=*", this.cmdType);
        putAtCommands("AT+HRMOSENS=*", this.cmdType);
        putAtCommands("AT+UVSENSOR=*", this.cmdType);
        putAtCommands("AT+DEBUGLVC=*", this.cmdType);
        putAtCommands("AT+WCOLORID=*", this.cmdType);
        putAtCommands("AT+GRIPSENS=*", this.cmdType);
        putAtCommands("AT+TSPPTEST=0,6,0", this.cmdType);
        putAtCommands("AT+TSPPTEST=0,6,5", this.cmdType);
        putAtCommands("AT+TSPPTEST=3,2,3", this.cmdType);
        putAtCommands("AT+TSPPTEST=3,6,0", this.cmdType);
        putAtCommands("AT+TSPPTEST=3,7,*", this.cmdType);
        putAtCommands("AT+LEDLAMPT=0,0", this.cmdType);
        putAtCommands("AT+LEDLAMPT=0,1", this.cmdType);
        putAtCommands("AT+LEDLAMPT=0,2", this.cmdType);
        putAtCommands("AT+LEDLAMPT=0,3", this.cmdType);
        putAtCommands("AT+LEDLAMPT=0,4", this.cmdType);
        putAtCommands("AT+COUNTRST=*", this.cmdType);
        putAtCommands("AT+AIRPMODE=*", this.cmdType);
        putAtCommands("AT+HWPARAMD=1,0,2,0", this.cmdType);
        putAtCommands("AT+USERDATA=*", this.cmdType);
        putAtCommands("AT+COFPDATA=1,0,0,0", this.cmdType);
        putAtCommands("AT+COFPDATA=2,0,0,*", this.cmdType);
        putAtCommands("AT+OQCSBFTT=*", this.cmdType);
        putAtCommands("AT+ACTTDATA=*", this.cmdType);
        putAtCommands("AT+RGBPDISP=0,0,0", this.cmdType);
        putAtCommands("AT+NEEDCMDT=*", this.cmdType);
        putAtCommands("AT+UBCTTEST=*|FBOD", this.cmdType);
        putAtCommands("AT+USBDEBUG=2|SLO", this.cmdType);
        putAtCommands("AT+USBDEBUG=*", this.cmdType);
        putAtCommands("AT+SYSDUMP=*|CRB(USC,VZW)", this.cmdType);
        putAtCommands("AT+ACTIVATE=0,0,0|ABO", this.cmdType);
        putAtCommands("AT+CAMETEST=0,1,0,7", this.cmdType);
        putAtCommands("AT+CAMETEST=0,1,3,0", this.cmdType);
        putAtCommands("AT+CAMETEST=0,1,3,1", this.cmdType);
        putAtCommands("AT+CAMETEST=0,1,5,*", this.cmdType);
        putAtCommands("AT+CAMETEST=0,4,1,1", this.cmdType);
        putAtCommands("AT+CAMETEST=0,5,1,2", this.cmdType);
        putAtCommands("AT+CAMETEST=2,0,0", this.cmdType);
        putAtCommands("AT+GETDOTESTNV", this.cmdType);
        putAtCommands("AT+HEADINFO=1,0|ABO", this.cmdType);
        putAtCommands("AT+HEADINFO=1,1,0", this.cmdType);
        putAtCommands("AT+SETDOTESTNV", this.cmdType);
        putAtCommands("AT+SETTESTNV=*", this.cmdType);
        putAtCommands("AT+ACLTESTT=*", this.cmdType);
        putAtCommands("AT+AIRPLANEVALUE=*|CRO(ATT,AIO)", this.cmdType);
        putAtCommands("AT+ASDIVTES=*", this.cmdType);
        putAtCommands("AT", this.cmdType);
        putAtCommands("ATE", this.cmdType);
        putAtCommands("ATE0", this.cmdType);
        putAtCommands("ATE1", this.cmdType);
        putAtCommands("ATI|CRO(ATT,AIO,SPR,USC,VZW)", this.cmdType);
        putAtCommands("ATI1", this.cmdType);
        putAtCommands("ATQ0E0V1", this.cmdType);
        putAtCommands("ATZ", this.cmdType);
        putAtCommands("AT+BAKUPCHK=*", this.cmdType);
        putAtCommands("AT+BATGETLEVEL?=*|SLO", this.cmdType);
        putAtCommands("AT+BTVALUE=*|CRO(ATT,AIO)", this.cmdType);
        putAtCommands("AT+CALRM=*|CRO(ATT,AIO)", this.cmdType);
        putAtCommands("AT+CBCAST=*|CRO(ATT,AIO)", this.cmdType);
        putAtCommands("AT+CBLKFTDF=*", this.cmdType);
        putAtCommands("AT+CBLTH=*|CRO(ATT,AIO)", this.cmdType);
        putAtCommands("AT+CCALD=*|CRO(ATT,AIO)", this.cmdType);
        putAtCommands("AT+CCLGS=*|CRO(ATT,AIO)", this.cmdType);
        putAtCommands("AT+CDCONT=*|CRO(ATT,AIO)", this.cmdType);
        putAtCommands("AT+CDUR=*|CRO(ATT,AIO)", this.cmdType);
        putAtCommands("AT+CDVOL=*|CRO(ATT,AIO)", this.cmdType);
        putAtCommands("AT+CEMAIL=*|CRO(ATT,AIO)", this.cmdType);
        putAtCommands("AT+CFUN=*", this.cmdType);
        putAtCommands("AT+CGMI=*", this.cmdType);
        putAtCommands("AT+CGMM=*|CRO(ATT,AIO,SPR,USC,VZW)", this.cmdType);
        putAtCommands("AT+CGMR=*", this.cmdType);
        putAtCommands("AT+CGSN=*|CRO(ATT,AIO,SPR,USC,VZW)", this.cmdType);
        putAtCommands("AT+CIMI=*|CRO(ATT,AIO,SPR,USC,VZW)", this.cmdType);
        putAtCommands("AT+CIMSG=*|CRO(ATT,AIO)", this.cmdType);
        putAtCommands("AT+CKPD=*", this.cmdType);
        putAtCommands("AT+CLOGIN=*|CRO(ATT,AIO)", this.cmdType);
        putAtCommands("AT+CMSG=*|CRO(ATT,AIO)", this.cmdType);
        putAtCommands("AT+CNPAD=*|CRO(ATT,AIO)", this.cmdType);
        putAtCommands("AT+CNUM=*|CRO(ATT,AIO)", this.cmdType);
        putAtCommands("AT+CPICTR=*|CRO(ATT,AIO)", this.cmdType);
        putAtCommands("AT+CPRMTEST=*", this.cmdType);
        putAtCommands("AT+CRST=*|CRO(ATT,AIO)", this.cmdType);
        putAtCommands("AT+CSHM=*|CRO(ATT,AIO)", this.cmdType);
        putAtCommands("AT+CSMCT=*|CRO(ATT,AIO)", this.cmdType);
        putAtCommands("AT+CSS=*", this.cmdType);
        putAtCommands("AT+CSYNC=*|CRO(ATT,AIO)", this.cmdType);
        putAtCommands("AT+CTACT=*|CRO(ATT,AIO)", this.cmdType);
        putAtCommands("AT+CTASK=*|CRO(ATT,AIO)", this.cmdType);
        putAtCommands("AT+CTBCPS=*|CRO(ATT,AIO)", this.cmdType);
        putAtCommands("AT+CTMRV=*|CRO(ATT,AIO)", this.cmdType);
        putAtCommands("AT+CTSA=*", this.cmdType);
        putAtCommands("AT+CVCMD=*|CRO(ATT,AIO)", this.cmdType);
        putAtCommands("AT+CVRCD=*|CRO(ATT,AIO)", this.cmdType);
        putAtCommands("AT+CWAP=*|CRO(ATT,AIO)", this.cmdType);
        putAtCommands("AT+DEVCONINFO=*|SLO", this.cmdType);
        putAtCommands("AT+DEVROOTK=*|SBB", this.cmdType);
        putAtCommands("AT+DTCPTEST=*", this.cmdType);
        putAtCommands("AT+DUMPCTRL=*", this.cmdType);
        putAtCommands("AT+EFSSYNCC=*", this.cmdType);
        putAtCommands("AT+FENRIRCN=*|SLO", this.cmdType);
        putAtCommands("AT+FUS?", this.cmdType);
        putAtCommands("AT+GETFULLHISTNV=*", this.cmdType);
        putAtCommands("AT+GETTESTNV=*", this.cmdType);
        putAtCommands("AT+GMM=*|SLO", this.cmdType);
        putAtCommands("AT+GPSVALUE=*|CRO(ATT,AIO)", this.cmdType);
        putAtCommands("AT+GSN=*", this.cmdType);
        putAtCommands("AT+HDCPTEST=*", this.cmdType);
        putAtCommands("AT+ICCCINFO=*|SLO", this.cmdType);
        putAtCommands("AT+IMSVAL=*|CRO(ATT,AIO)", this.cmdType);
        putAtCommands("AT+LOGERASE=*", this.cmdType);
        putAtCommands("AT+MITSRFTS=*", this.cmdType);
        putAtCommands("AT+NFCVALUE=*|CRO(ATT,AIO)", this.cmdType);
        putAtCommands("AT+NSRI=*|SLO|CRO(SKC,KTC)", this.cmdType);
        putAtCommands("AT+RTCCTEST=*", this.cmdType);
        putAtCommands("AT+SCMMONIT=*", this.cmdType);
        putAtCommands("AT+SCODCHAN=*", this.cmdType);
        putAtCommands("AT+SECUREOS=*", this.cmdType);
        putAtCommands("AT+SETFULLTESTNV=*", this.cmdType);
        putAtCommands("AT+SIZECHECK=*|SLO", this.cmdType);
        putAtCommands("AT+SOCFIOTK=0,0,0,0|SBB", this.cmdType);
        putAtCommands("AT+SOCFIOTK=1,0,0,0|SBB", this.cmdType);
        putAtCommands("AT+SUDDLMOD=*", this.cmdType);
        putAtCommands("AT+SVCIFPGM=*|SLO", this.cmdType);
        putAtCommands("AT+USBMODEM=*|CRO(VZW)", this.cmdType);
        putAtCommands("AT+WIFIVALUE=*|CRO(ATT,AIO)", this.cmdType);
        putAtCommands("AT+COPS?", this.cmdType);
        putAtCommands("AT+COPS=*", this.cmdType);
        putAtCommands("AT+CGTFT=*", this.cmdType);
        putAtCommands("AT+CCFC=*", this.cmdType);
        putAtCommands("AT+CCWA=*", this.cmdType);
        putAtCommands("AT+CDV=*", this.cmdType);
        putAtCommands("AT+CEER=*", this.cmdType);
        putAtCommands("AT+CEMODE=*", this.cmdType);
        putAtCommands("AT+CGACT=*", this.cmdType);
        putAtCommands("AT+CGATT=*", this.cmdType);
        putAtCommands("AT+CGCMOD=*", this.cmdType);
        putAtCommands("AT+CGDATA=*", this.cmdType);
        putAtCommands("AT+CGDCONT=*", this.cmdType);
        putAtCommands("AT+CGDSCONT=*", this.cmdType);
        putAtCommands("AT+CGEQMIN=*", this.cmdType);
        putAtCommands("AT+CGEQOS=*", this.cmdType);
        putAtCommands("AT+CGEQREQ=*", this.cmdType);
        putAtCommands("AT+CHLD=*", this.cmdType);
        putAtCommands("AT+CHUP=*", this.cmdType);
        putAtCommands("AT+CLCC=*", this.cmdType);
        putAtCommands("AT+CLCK=*", this.cmdType);
        putAtCommands("AT+CMEC=*", this.cmdType);
        putAtCommands("AT+CMEE=*", this.cmdType);
        putAtCommands("AT+CMER=*", this.cmdType);
        putAtCommands("AT+CMGD=*", this.cmdType);
        putAtCommands("AT+CMGF=*", this.cmdType);
        putAtCommands("AT+CMGS=*", this.cmdType);
        putAtCommands("AT+CMGW=*", this.cmdType);
        putAtCommands("AT+CMOD=*", this.cmdType);
        putAtCommands("AT+CMSS=*", this.cmdType);
        putAtCommands("AT+CNMPSD=*", this.cmdType);
        putAtCommands("AT+CPIN=*", this.cmdType);
        putAtCommands("AT+CPMS=*", this.cmdType);
        putAtCommands("AT+CPWD=*", this.cmdType);
        putAtCommands("AT+CREG=*", this.cmdType);
        putAtCommands("AT+CRSM=*", this.cmdType);
        putAtCommands("AT+CSCA=*", this.cmdType);
        putAtCommands("AT+CSCS=*", this.cmdType);
        putAtCommands("AT+CSIM=*", this.cmdType);
        putAtCommands("AT+CSMP=*", this.cmdType);
        putAtCommands("AT+CSMS=*", this.cmdType);
        putAtCommands("AT+CUSD=*", this.cmdType);
        putAtCommands("AT+CVHU=*", this.cmdType);
        putAtCommands("AT+CVMOD=*", this.cmdType);
        putAtCommands("AT+VTS=*", this.cmdType);
        putAtCommands("AT+XDATACHANNEL=*", this.cmdType);
        putAtCommands("ATA", this.cmdType);
        putAtCommands("ATD", this.cmdType);
        putAtCommands("ATH", this.cmdType);
        putAtCommands("CHLD", this.cmdType);
        putAtCommands("AT#CGSEND=*", this.cmdType);
        putAtCommands("AT$QCDGEN=*", this.cmdType);
        putAtCommands("AT^ATDVIDEO=*", this.cmdType);
        putAtCommands("AT$QCMIP=*", this.cmdType);
        putAtCommands("AT$QCMIPEP=*", this.cmdType);
        putAtCommands("AT$QCMIPHA=*", this.cmdType);
        putAtCommands("AT$QCMIPMASPI=*", this.cmdType);
        putAtCommands("AT$QCMIPMHSS=*", this.cmdType);
        putAtCommands("AT$QCMIPMASS=*", this.cmdType);
        putAtCommands("AT$QCMIPMHSPI=*", this.cmdType);
        putAtCommands("AT$QCMIPNAI=*", this.cmdType);
        putAtCommands("AT$QCMIPPHA=*", this.cmdType);
        putAtCommands("AT$QCMIPRT=*", this.cmdType);
        putAtCommands("AT$QCMIPSHA=*", this.cmdType);
        putAtCommands("AT$QCMIPT=*", this.cmdType);
        putAtCommands("AT$QCMRUC=*", this.cmdType);
        putAtCommands("AT$CRM=*", this.cmdType);
        putAtCommands("AT$QCPDPCFGE?", this.cmdType);
        putAtCommands("AT$QCPDPCFGE=*", this.cmdType);
        putAtCommands("AT+BAUDRATE=0,0,1,1", this.cmdType);
        putAtCommands("AT+BAUDRATE=0,0,1,0", this.cmdType);
        putAtCommands("AT+ENGMODES=9,0|SLO|ABO", this.cmdType);
        putAtCommands("AT+ENGMODES=0,*|SLO|ABO", this.cmdType);
        putAtCommands("AT+ENGMODES=1,*|SLO|ABO", this.cmdType);
        putAtCommands("AT+ENGMODES=2,2,*|SLO|ABO", this.cmdType);
        putAtCommands("AT+MGRTCASS=*", this.cmdType);
        putAtCommands("AT+HEADINFO=1,3", this.cmdType);
        putAtCommands("AT+HEADINFO=1,4", this.cmdType);
        putAtCommands("AT+HEADINFO=1,5", this.cmdType);
        putAtCommands("AT+FCFMTEST=0,0,0,*", this.cmdType);
        putAtCommands("AT+FCFMTEST=0,0,1,*", this.cmdType);
        putAtCommands("AT+FCFMTEST=0,1,0,0", this.cmdType);
        putAtCommands("AT+FCFMTEST=1,0,0,*", this.cmdType);
        putAtCommands("AT+SPKSTEST=3,3,4", this.cmdType);
        putAtCommands("AT+CAMETEST=0,1,0,3", this.cmdType);
        putAtCommands("AT+CAMETEST=0,9,3,0", this.cmdType);
        putAtCommands("AT+LOOPTEST=3,0,0", this.cmdType);
        putAtCommands("AT+LOOPTEST=3,0,1", this.cmdType);
        putAtCommands("AT+LOOPTEST=3,0,2", this.cmdType);
        putAtCommands("AT+BATTTEST=1,3", this.cmdType);
        putAtCommands("AT+BATTTEST=1,4", this.cmdType);
        putAtCommands("AT+LIGHTEST=0,2", this.cmdType);
        putAtCommands("AT+LIGHTEST=0,3", this.cmdType);
        putAtCommands("AT+LIGHTEST=0,4", this.cmdType);
        putAtCommands("AT+LIGHTEST=1,3", this.cmdType);
        putAtCommands("AT+LIGHTEST=1,4", this.cmdType);
        putAtCommands("AT+RSTVERIF=0,0", this.cmdType);
        putAtCommands("AT+TSPPTEST=3,4,0", this.cmdType);
        putAtCommands("AT+TSPPTEST=3,5,0", this.cmdType);
        putAtCommands("AT+DEVICEPN=*", this.cmdType);
        putAtCommands("AT+ATSCTEST=*", this.cmdType);
        putAtCommands("AT+ETHERNET=1,0,0", this.cmdType);
        putAtCommands("AT+ETHERNET=1,1,0", this.cmdType);
        putAtCommands("AT+LOCKUTIL=0,1,0", this.cmdType);
        putAtCommands("AT+LOCKUTIL=1,1,0", this.cmdType);
        putAtCommands("AT+BGLUCOSE=*", this.cmdType);
        putAtCommands("AT+SKUCODEC=1,0,0", this.cmdType);
        putAtCommands("AT+SKUCODEC=2,0,*", this.cmdType);
        putAtCommands("AT+VOLTECON=0,0,0,0", this.cmdType);
        putAtCommands("AT+VOLTECON=1,0,1,0", this.cmdType);
        putAtCommands("AT+IFPMICCN=0,0,3,0", this.cmdType);
        putAtCommands("AT+IFPMICCN=0,0,4,0", this.cmdType);
        putAtCommands("AT+IFPMICCN=0,0,6,0|CRO(DCM,KDI,RKT)", this.cmdType);
        putAtCommands("AT+IFPMICCN=0,0,7,*", this.cmdType);
        putAtCommands("AT+HOPATHCK=1,0,0", this.cmdType);
        putAtCommands("AT+MIPITEST=0", this.cmdType);
        putAtCommands("AT+MIPITEST=1", this.cmdType);
        putAtCommands("AT+CHIPIDTT=*", this.cmdType);
        putAtCommands("AT+NEEDCMDT=0,0,0,*", this.cmdType);
        putAtCommands("AT+RFEVTAIT=*", this.cmdType);
        putAtCommands("AT+RGBPDISP=0,0,0,*", this.cmdType);
        putAtCommands("AT+BLOBSIGN=*", this.cmdType);
        putAtCommands("AT+PRXDRXCK=*", this.cmdType);
        putAtCommands("AT+ARPCHECK=*", this.cmdType);
        putAtCommands("AT+CESP=*", this.cmdType);
        putAtCommands("AT+CSDH=*", this.cmdType);
        putAtCommands("AT+CSCB=*", this.cmdType);
        putAtCommands("AT+CSAS=*", this.cmdType);
        putAtCommands("AT+CRES=*", this.cmdType);
        putAtCommands("AT+CMGL=*", this.cmdType);
        putAtCommands("AT+CNMA=*", this.cmdType);
        putAtCommands("AT+CMGC=*", this.cmdType);
        putAtCommands("AT+CMMS=*", this.cmdType);
        putAtCommands("AT+CBST=*", this.cmdType);
        putAtCommands("AT+CPAS=*", this.cmdType);
        putAtCommands("AT+CGQREQ=*", this.cmdType);
        putAtCommands("AT+CNMI=*", this.cmdType);
        putAtCommands("AT+CMGR=*", this.cmdType);
        putAtCommands("AT+CPOL=*", this.cmdType);
        putAtCommands("AT+GMR=*", this.cmdType);
        putAtCommands("AT+GMI=*", this.cmdType);
        putAtCommands("AT+CSQ=*", this.cmdType);
        putAtCommands("AT+CPBS=*", this.cmdType);
        putAtCommands("AT+CPBR=*", this.cmdType);
        putAtCommands("AT+CPBW=*", this.cmdType);
        putAtCommands("AT+CLIP=*", this.cmdType);
        putAtCommands("AT+CLIR=*", this.cmdType);
        putAtCommands("AT+CBKLT=*", this.cmdType);
        putAtCommands("AT+CGSMS=*", this.cmdType);
        putAtCommands("AT$ARMEE=*", this.cmdType);
        putAtCommands("AT$ARME=*", this.cmdType);
        putAtCommands("ATV1", this.cmdType);
        putAtCommands("ATX4", this.cmdType);
        putAtCommands("AT+PACSP=*", this.cmdType);
        putAtCommands("AT$CSQ=*", this.cmdType);
        putAtCommands("AT$CREG=*", this.cmdType);
        putAtCommands("AT$CCLK=*", this.cmdType);
        putAtCommands("AT*CNTI=*", this.cmdType);
        putAtCommands("AT+RSRP=*", this.cmdType);
        putAtCommands("AT+RSRQ=*", this.cmdType);
        putAtCommands("AT+RSCP=*", this.cmdType);
        putAtCommands("AT+ECNO=*", this.cmdType);
        putAtCommands("AT+CEINFO=*", this.cmdType);
        putAtCommands("AT+NCELL=*", this.cmdType);
        putAtCommands("AT+SCELL=*", this.cmdType);
        putAtCommands("AT+CCHC=*", this.cmdType);
        putAtCommands("AT+CCHO=*", this.cmdType);
        putAtCommands("AT+CGLA=*", this.cmdType);
        putAtCommands("AT+CCLK=*", this.cmdType);
        putAtCommands("AT+CEREQ=*", this.cmdType);
        putAtCommands("AT+CGCONTRDP=*", this.cmdType);
        putAtCommands("AT+CGEQOSRDP=*", this.cmdType);
        putAtCommands("AT+CGEREP=*", this.cmdType);
        putAtCommands("AT+CGPADDR=*", this.cmdType);
        putAtCommands("AT+CGSCONTRDP=*", this.cmdType);
        putAtCommands("AT+CGTFTRDP=*", this.cmdType);
        putAtCommands("AT+CIND=*", this.cmdType);
        putAtCommands("AT+COPN=*", this.cmdType);
        putAtCommands("AT+CPLS=*", this.cmdType);
        putAtCommands("AT+CRM=*", this.cmdType);
        putAtCommands("AT+CSTF=*", this.cmdType);
        putAtCommands("AT+GCAP=*", this.cmdType);
        putAtCommands("AT+CLAC=*", this.cmdType);
        putAtCommands("AT+CEREG=*", this.cmdType);
        putAtCommands("AT+WS46=*", this.cmdType);
        putAtCommands("AT+CGPIAF=*", this.cmdType);
        putAtCommands("AT+CESQ=*", this.cmdType);
        putAtCommands("AT+VZWAPNE=*", this.cmdType);
        putAtCommands("AT+VZWRSRP=*", this.cmdType);
        putAtCommands("AT+VZWRSRQ=*", this.cmdType);
        putAtCommands("AT+SWATD=1|ABO", this.cmdType);
        putAtCommands("AT+SWATD=0|ABO", this.cmdType);
        putAtCommands("AT+NCAMTEST=1,9,0,*", this.cmdType);
        putAtCommands("AT+NCAMTEST=1,9,1,*", this.cmdType);
        putAtCommands("AT+TOUCH=*", this.cmdType);
        putAtCommands("AT+FPSENSOR=1,2,0", this.cmdType);
        putAtCommands("AT+PRECONFG=0,0", this.cmdType);
        putAtCommands("AT+PRECONFG=1,0", this.cmdType);
        putAtCommands("AT+PRECONFG=*|CRB(LGT,LUC,LUO,SKT,SKC,SKO,KTT,KTC,KTO,ANY,KOO)", this.cmdType);
        putAtCommands("AT+SERIALNO=*|SLO|ABO", this.cmdType);
        putAtCommands("AT+APCHIPTT=*", this.cmdType);
        putAtCommands("AT+SWVER=*|SLO", this.cmdType);
        putAtCommands("AT+IMEINUM=*|SLO", this.cmdType);
        putAtCommands("AT+FEEDBACK=*", this.cmdType);
        putAtCommands("AT+SKT=*", this.cmdType);
        putAtCommands("AT+LTPUT=*", this.cmdType);
        putAtCommands("AT+LOCKCODE=*", this.cmdType);
        putAtCommands("AT+LVOFLOCK=*", this.cmdType);
        putAtCommands("AT+DETALOCK=*", this.cmdType);
        putAtCommands("AT+PRODCODE=2,*|CSO", this.cmdType);
        putAtCommands("AT+PRODCODE=*", this.cmdType);
        putAtCommands("AT+GRDMTEST=0,1", this.cmdType);
        putAtCommands("AT+GRDMTEST=0,2", this.cmdType);
        putAtCommands("AT+TSPPTEST=0,9,*|CSO", this.cmdType);
        putAtCommands("AT+FAILDUMP=*|FBOD", this.cmdType);
        putAtCommands("AT+NADCHECK=*|FBOD", this.cmdType);
        putAtCommands("AT+BANSELCT=*|SLO", this.cmdType);
        putAtCommands("AT+LIFETIME=*|SLO", this.cmdType);
        putAtCommands("AT+MAXPOWER=*|SLO", this.cmdType);
        putAtCommands("AT+IMEITEST=0,*|SLO", this.cmdType);
        putAtCommands("AT+IMEITEST=1,*|SLO", this.cmdType);
        putAtCommands("AT+VERSNAME=*|SLO|ABO", this.cmdType);
        putAtCommands("AT+REACTIVE=1,0,0|SLO", this.cmdType);
        putAtCommands("AT+REACTIVE=2,0,*|SLO", this.cmdType);
        putAtCommands("AT+REACTIVE=2,1,*|SLO ", this.cmdType);
        putAtCommands("AT+REACTIVE=2,2,*|SLO", this.cmdType);
        putAtCommands("AT+GRDMTEST=0,3", this.cmdType);
        putAtCommands("AT+HAICTEST=0,*|CSO", this.cmdType);
        putAtCommands("AT+HAICTEST=1,0|CSO", this.cmdType);
        putAtCommands("AT+HAICTEST=1,1|CSO", this.cmdType);
        putAtCommands("AT+RSTCOMPC=0,0,0", this.cmdType);
        putAtCommands("AT+ACTIVEID=1,0,0", this.cmdType);
        putAtCommands("AT+FRPUNLCK=1,0,*", this.cmdType);
        putAtCommands("AT+FRPUNLCK=1,1,*", this.cmdType);
        putAtCommands("AT+BIASENSO=0,2,0|CSO", this.cmdType);
        putAtCommands("AT+BIASENSO=1,2,0|CSO", this.cmdType);
        putAtCommands("AT+MTKEDLBK=0,0|CRO(TFN)", this.cmdType);
        putAtCommands("AT+MTKEDLBK=1,0|CRO(TFN)", this.cmdType);
        putAtCommands("AT+DDPRO=*|SLO", this.cmdType);
        putAtCommands("AT+VIBRTEST=1,0|CSO", this.cmdType);
        putAtCommands("AT+VIBRTEST=0,10|CSO", this.cmdType);
        putAtCommands("AT+VIBRTEST=0,11|CSO", this.cmdType);
        putAtCommands("AT+VIBRTEST=1,2|CSO", this.cmdType);
        putAtCommands("AT+VIBRTEST=1,3|CSO", this.cmdType);
        putAtCommands("AT+VERSNAME=1,1,3", this.cmdType);
        putAtCommands("AT+BATTTEST=4,7|CRO(DCM,KDI,RKT)", this.cmdType);
        putAtCommands("AT+SIMDETEC=4,8|SLO", this.cmdType);
        putAtCommands("AT+SIMDETEC=6,0,*|CRO(ATT,AIO,XAU,VZW,CCT,CHA,DSH,USC,XAA)", this.cmdType);
        putAtCommands("AT+SIMDETEC=*", this.cmdType);
        putAtCommands("AT+ABSTACHK=0,0|SLO|ABO", this.cmdType);
        putAtCommands("AT+ABSTACHK=1,0|SLO|ABO", this.cmdType);
        putAtCommands("AT+FACTOLOG=0,7,1,2|ABO", this.cmdType);
        putAtCommands("AT+CONTROLN=1,1,0|ABO", this.cmdType);
        putAtCommands("AT+GETFULLTESTNV=*|ABO", this.cmdType);
        putAtCommands("AT+URDEVICE=1,0,0,0|ABO", this.cmdType);
        putAtCommands("AT+CPLDUCFG=1,0|ABO", this.cmdType);
        putAtCommands("AT+CONTROLN=*", this.cmdType);
        putAtCommands("AT+FACTOLOG=*", this.cmdType);
        putAtCommands("AT+URDEVICE=*", this.cmdType);
        putAtCommands("AT+CPLDUCFG=*", this.cmdType);
        putAtCommands("AT+SWDLMODE=0|ABO", this.cmdType);
        putAtCommands("AT+SWDLMODE=*", this.cmdType);
        putAtCommands("AT+XOCALCHK=0,1", this.cmdType);
        putAtCommands("AT+XOCALCHK=1,0", this.cmdType);
        putAtCommands("AT+XOCALCHK=1,1", this.cmdType);
        putAtCommands("AT+INITTEST=1,0,0|SLO", this.cmdType);
    }
}
