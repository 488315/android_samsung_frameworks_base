package com.android.server;

import java.util.Hashtable;

/* compiled from: ServiceKeeper.java */
/* loaded from: classes.dex */
public class ServiceObject {
    public PermissionPackage servicePermissions = new PermissionPackage();
    public Hashtable serviceMethods = new Hashtable();
    public boolean isSterileService = true;
}
