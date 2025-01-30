package com.android.server.pm;

/* loaded from: classes3.dex */
public interface PackageSessionProvider {
  PackageInstallerSession getSession(int i);

  PackageSessionVerifier getSessionVerifier();
}
