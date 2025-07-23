package com.android.keyguard;

import android.content.DialogInterface;
import com.samsung.android.bio.face.SemBioFaceManager;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda61 implements DialogInterface.OnClickListener {
    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        SemBioFaceManager semBioFaceManager = KeyguardSecUpdateMonitorImpl.sFaceManager;
        dialogInterface.dismiss();
    }
}
