package com.google.android.mms.pdu;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/* loaded from: classes6.dex */
public class PduBody {
    private Map<String, PduPart> mPartMapByContentId;
    private Map<String, PduPart> mPartMapByContentLocation;
    private Map<String, PduPart> mPartMapByFileName;
    private Map<String, PduPart> mPartMapByName;
    private Vector<PduPart> mParts;

    public PduBody() {
        this.mParts = null;
        this.mPartMapByContentId = null;
        this.mPartMapByContentLocation = null;
        this.mPartMapByName = null;
        this.mPartMapByFileName = null;
        this.mParts = new Vector<>();
        this.mPartMapByContentId = new HashMap();
        this.mPartMapByContentLocation = new HashMap();
        this.mPartMapByName = new HashMap();
        this.mPartMapByFileName = new HashMap();
    }

    private void putPartToMaps(PduPart pduPart) {
        byte[] contentId = pduPart.getContentId();
        if (contentId != null) {
            this.mPartMapByContentId.put(new String(contentId), pduPart);
        }
        byte[] contentLocation = pduPart.getContentLocation();
        if (contentLocation != null) {
            this.mPartMapByContentLocation.put(new String(contentLocation), pduPart);
        }
        byte[] name = pduPart.getName();
        if (name != null) {
            this.mPartMapByName.put(new String(name), pduPart);
        }
        byte[] filename = pduPart.getFilename();
        if (filename != null) {
            this.mPartMapByFileName.put(new String(filename), pduPart);
        }
    }

    public boolean addPart(PduPart pduPart) {
        if (pduPart == null) {
            throw null;
        }
        putPartToMaps(pduPart);
        return this.mParts.add(pduPart);
    }

    public void addPart(int i, PduPart pduPart) {
        if (pduPart == null) {
            throw null;
        }
        putPartToMaps(pduPart);
        this.mParts.add(i, pduPart);
    }

    public PduPart removePart(int i) {
        return this.mParts.remove(i);
    }

    public void removeAll() {
        this.mParts.clear();
    }

    public PduPart getPart(int i) {
        return this.mParts.get(i);
    }

    public int getPartIndex(PduPart pduPart) {
        return this.mParts.indexOf(pduPart);
    }

    public int getPartsNum() {
        return this.mParts.size();
    }

    public PduPart getPartByContentId(String str) {
        return this.mPartMapByContentId.get(str);
    }

    public PduPart getPartByContentLocation(String str) {
        return this.mPartMapByContentLocation.get(str);
    }

    public PduPart getPartByName(String str) {
        return this.mPartMapByName.get(str);
    }

    public PduPart getPartByFileName(String str) {
        return this.mPartMapByFileName.get(str);
    }
}
