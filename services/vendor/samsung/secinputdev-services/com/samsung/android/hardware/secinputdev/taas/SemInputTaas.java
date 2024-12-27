package com.samsung.android.hardware.secinputdev.taas;

import android.content.Context;

import com.samsung.android.hardware.secinputdev.external.SemInputExternal;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes.dex */
public class SemInputTaas {
    private static final String TAG = "SemInputTaas";
    private final Context context;
    private final ExternalApi externalApi;
    private final StringBuilder bootingDump = new StringBuilder();
    private ArrayList<SemInputTaasTestCase> mList = new ArrayList<>();
    private SemInputTaasCaseFactory mSemInputTaasCaseFactory = new SemInputTaasCaseFactory();

    public SemInputTaas(
            Context context, SemInputExternal.IExternalEventRegister externalEventRegister) {
        this.context = context;
        this.externalApi = new ExternalApi(context, externalEventRegister);
        if (this.externalApi.getInHouse()) {
            this.mList.add(this.mSemInputTaasCaseFactory.create("CASA"));
            this.mList.add(this.mSemInputTaasCaseFactory.create("CASB"));
            Iterator<SemInputTaasTestCase> it = this.mList.iterator();
            while (it.hasNext()) {
                SemInputTaasTestCase taasCase = it.next();
                taasCase.create(context, this.externalApi);
            }
        }
    }

    public void dump(PrintWriter pw) {
        pw.println("dumping SemInputTaas");
        pw.print(this.bootingDump.toString());
    }

    public void destroy() {
        Iterator<SemInputTaasTestCase> it = this.mList.iterator();
        while (it.hasNext()) {
            SemInputTaasTestCase taasCase = it.next();
            taasCase.destroy();
        }
        this.externalApi.destroy();
    }
}
