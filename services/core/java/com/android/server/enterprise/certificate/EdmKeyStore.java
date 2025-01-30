package com.android.server.enterprise.certificate;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import com.android.internal.org.bouncycastle.asn1.ASN1InputStream;
import com.android.internal.org.bouncycastle.asn1.x509.BasicConstraints;
import com.android.server.enterprise.adapter.AdapterRegistry;
import com.android.server.enterprise.adapter.IPersonaManagerAdapter;
import com.android.server.enterprise.utils.Utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.security.auth.x500.X500Principal;

/* loaded from: classes2.dex */
public class EdmKeyStore {
  public static final String NATIVE_KEYSTORE_PATH;
  public static final String TRUSTED_KEYSTORE_PATH;
  public static final String UNTRUSTED_KEYSTORE_PATH;
  public static final String USER_KEYSTORE_PATH;
  public static EdmKeyStore mInstanceNative;
  public static EdmKeyStore mInstanceTrusted;
  public static EdmKeyStore mInstanceUntrusted;
  public static EdmKeyStore mInstanceUser;
  public KeyStore mKeyStore;
  public Object mKeyStoreLock;
  public String mPath;
  public int mType;

  static {
    StringBuilder sb = new StringBuilder();
    sb.append(Environment.getDataSystemDirectory());
    String str = File.separator;
    sb.append(str);
    sb.append("enterprise_cacerts.bks");
    TRUSTED_KEYSTORE_PATH = sb.toString();
    UNTRUSTED_KEYSTORE_PATH =
        Environment.getDataSystemDirectory() + str + "enterprise_untrustedcerts.bks";
    USER_KEYSTORE_PATH = Environment.getDataSystemDirectory() + str + "enterprise_usercerts.bks";
    NATIVE_KEYSTORE_PATH =
        Environment.getDataSystemDirectory() + str + "enterprise_nativecerts.bks";
  }

  public static synchronized EdmKeyStore getInstance(int i) {
    synchronized (EdmKeyStore.class) {
      if (i == 0) {
        if (mInstanceTrusted == null) {
          try {
            mInstanceTrusted = new EdmKeyStore(TRUSTED_KEYSTORE_PATH, i);
          } catch (Exception e) {
            Log.e("EdmKeyStore", "Should not happen! ", e);
            mInstanceTrusted = null;
          }
        }
        return mInstanceTrusted;
      }
      if (i == 1) {
        if (mInstanceUser == null) {
          try {
            mInstanceUser = new EdmKeyStore(USER_KEYSTORE_PATH, i);
          } catch (Exception e2) {
            Log.e("EdmKeyStore", "Should not happen! ", e2);
            mInstanceUser = null;
          }
        }
        return mInstanceUser;
      }
      if (i == 2) {
        if (mInstanceNative == null) {
          try {
            mInstanceNative = new EdmKeyStore(NATIVE_KEYSTORE_PATH, i);
          } catch (Exception e3) {
            Log.e("EdmKeyStore", "Should not happen! ", e3);
            mInstanceNative = null;
          }
        }
        return mInstanceNative;
      }
      if (i != 3) {
        return null;
      }
      if (mInstanceUntrusted == null) {
        try {
          mInstanceUntrusted = new EdmKeyStore(UNTRUSTED_KEYSTORE_PATH, i);
        } catch (Exception e4) {
          Log.e("EdmKeyStore", "Should not happen! ", e4);
          mInstanceUntrusted = null;
        }
      }
      return mInstanceUntrusted;
    }
  }

  /* JADX WARN: Removed duplicated region for block: B:35:0x0076 A[Catch: all -> 0x007a, TRY_ENTER, TryCatch #9 {, blocks: (B:12:0x0057, B:13:0x005a, B:35:0x0076, B:36:0x0079), top: B:4:0x0010 }] */
  /* JADX WARN: Removed duplicated region for block: B:44:0x0064 A[EXC_TOP_SPLITTER, SYNTHETIC] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public EdmKeyStore(String str, int i) {
    FileInputStream fileInputStream;
    FileInputStream fileInputStream2;
    Object obj = new Object();
    this.mKeyStoreLock = obj;
    this.mPath = str;
    this.mType = i;
    synchronized (obj) {
      FileInputStream fileInputStream3 = null;
      r6 = null;
      FileOutputStream fileOutputStream = null;
      try {
        this.mKeyStore = KeyStore.getInstance("BKS");
        try {
          fileInputStream = new FileInputStream(str);
          try {
            try {
              this.mKeyStore.load(fileInputStream, null);
            } catch (IOException unused) {
              if (fileInputStream != null) {
                fileInputStream.close();
                fileInputStream = null;
              }
              this.mKeyStore.load(null, null);
              FileOutputStream fileOutputStream2 = new FileOutputStream(str);
              try {
                this.mKeyStore.store(fileOutputStream2, null);
                fileInputStream2 = new FileInputStream(str);
              } catch (Throwable th) {
                th = th;
                fileOutputStream = fileOutputStream2;
              }
              try {
                this.mKeyStore.load(fileInputStream2, null);
                try {
                  try {
                    fileOutputStream2.close();
                  } catch (IOException unused2) {
                    Log.d("EdmKeyStore", "fos close failed");
                    fileInputStream = fileInputStream2;
                    fileInputStream.close();
                  }
                  fileInputStream = fileInputStream2;
                  fileInputStream.close();
                } catch (Throwable th2) {
                  th = th2;
                  fileInputStream3 = fileInputStream2;
                  if (fileInputStream3 != null) {}
                  throw th;
                }
              } catch (Throwable th3) {
                th = th3;
                fileOutputStream = fileOutputStream2;
                fileInputStream = fileInputStream2;
                try {
                  if (fileOutputStream != null) {}
                  throw th;
                } catch (Throwable th4) {
                  th = th4;
                  fileInputStream3 = fileInputStream;
                  if (fileInputStream3 != null) {
                    fileInputStream3.close();
                  }
                  throw th;
                }
              }
            }
          } catch (Throwable th5) {
            th = th5;
            if (fileOutputStream != null) {
              try {
                fileOutputStream.close();
              } catch (IOException unused3) {
                Log.d("EdmKeyStore", "fos close failed");
              }
            }
            throw th;
          }
        } catch (IOException unused4) {
          fileInputStream = null;
        } catch (Throwable th6) {
          th = th6;
          fileInputStream = null;
          if (fileOutputStream != null) {}
          throw th;
        }
        fileInputStream.close();
      } catch (Throwable th7) {
        th = th7;
      }
    }
  }

  public List installCertificates(List list, int i) {
    ArrayList arrayList = new ArrayList();
    Iterator it = list.iterator();
    while (it.hasNext()) {
      X509Certificate x509Certificate = (X509Certificate) it.next();
      String generateAlias = generateAlias(x509Certificate, i);
      try {
        synchronized (this.mKeyStoreLock) {
          this.mKeyStore.setCertificateEntry(generateAlias, x509Certificate);
          if (this.mKeyStore.isCertificateEntry(generateAlias)) {
            arrayList.add(removeUserIdFromAlias(generateAlias));
          }
        }
      } catch (KeyStoreException e) {
        Log.d("EdmKeyStore", "Exception with keystore " + e);
      }
    }
    saveKeyStore();
    return arrayList;
  }

  public List installCertificates(Map map, int i) {
    ArrayList arrayList = new ArrayList();
    for (Map.Entry entry : map.entrySet()) {
      try {
        synchronized (this.mKeyStoreLock) {
          String addUserIdToAlias = addUserIdToAlias((String) entry.getKey(), i);
          this.mKeyStore.setCertificateEntry(addUserIdToAlias, (Certificate) entry.getValue());
          if (this.mKeyStore.isCertificateEntry(addUserIdToAlias)) {
            arrayList.add((String) entry.getKey());
          }
        }
      } catch (KeyStoreException e) {
        Log.d("EdmKeyStore", "Exception with keystore " + e);
      }
    }
    saveKeyStore();
    return arrayList;
  }

  public List removeCertificates(List list, int i) {
    ArrayList arrayList = new ArrayList();
    Iterator it = list.iterator();
    while (it.hasNext()) {
      String str = (String) it.next();
      try {
        synchronized (this.mKeyStoreLock) {
          String addUserIdToAlias = addUserIdToAlias(str, i);
          this.mKeyStore.deleteEntry(addUserIdToAlias);
          if (!this.mKeyStore.isCertificateEntry(addUserIdToAlias)) {
            arrayList.add(str);
          }
        }
      } catch (KeyStoreException e) {
        Log.d("EdmKeyStore", "Exception with keystore " + e);
      }
    }
    saveKeyStore();
    return arrayList;
  }

  public Map getCertificates(List list, int i) {
    HashMap hashMap = new HashMap();
    Iterator it = list.iterator();
    while (it.hasNext()) {
      String str = (String) it.next();
      try {
        synchronized (this.mKeyStoreLock) {
          X509Certificate x509Certificate =
              (X509Certificate) this.mKeyStore.getCertificate(addUserIdToAlias(str, i));
          if (x509Certificate != null) {
            hashMap.put(str, x509Certificate);
          }
        }
      } catch (KeyStoreException e) {
        Log.d("EdmKeyStore", "Exception with keystore " + e);
      }
    }
    return hashMap;
  }

  public final void saveKeyStore() {
    String str;
    String str2;
    FileOutputStream fileOutputStream;
    synchronized (this.mKeyStoreLock) {
      FileOutputStream fileOutputStream2 = null;
      try {
        try {
          fileOutputStream = new FileOutputStream(this.mPath);
        } catch (Throwable th) {
          th = th;
        }
      } catch (Exception e) {
        e = e;
      }
      try {
        this.mKeyStore.store(fileOutputStream, null);
        try {
          fileOutputStream.close();
        } catch (IOException unused) {
          str = "EdmKeyStore";
          str2 = "fos close failed";
          Log.d(str, str2);
        }
      } catch (Exception e2) {
        e = e2;
        fileOutputStream2 = fileOutputStream;
        Log.e("EdmKeyStore", "save error" + e);
        if (fileOutputStream2 != null) {
          try {
            fileOutputStream2.close();
          } catch (IOException unused2) {
            str = "EdmKeyStore";
            str2 = "fos close failed";
            Log.d(str, str2);
          }
        }
      } catch (Throwable th2) {
        th = th2;
        fileOutputStream2 = fileOutputStream;
        if (fileOutputStream2 != null) {
          try {
            fileOutputStream2.close();
          } catch (IOException unused3) {
            Log.d("EdmKeyStore", "fos close failed");
          }
        }
        throw th;
      }
    }
  }

  public String generateAlias(X509Certificate x509Certificate) {
    return generateAlias(x509Certificate.getSubjectX500Principal());
  }

  public final String generateAlias(X500Principal x500Principal) {
    try {
      byte[] digest = MessageDigest.getInstance("SHA1").digest(x500Principal.getEncoded());
      return Utils.intToHexString(
          ((digest[3] & 255) << 24)
              | ((digest[0] & 255) << 0)
              | ((digest[1] & 255) << 8)
              | ((digest[2] & 255) << 16),
          8);
    } catch (NoSuchAlgorithmException e) {
      throw new AssertionError(e);
    }
  }

  public final String generateAlias(X509Certificate x509Certificate, int i) {
    return addUserIdToAlias(generateAlias(x509Certificate), i);
  }

  public final List generateAllAliasesForUser(
      CertificateCache certificateCache, X500Principal x500Principal, int i, int i2) {
    ArrayList arrayList = new ArrayList();
    String generateAlias = generateAlias(x500Principal);
    arrayList.add(addUserIdToAlias(generateAlias, i));
    if (getPersonaManagerAdapter().isValidKnoxId(i)) {
      if (isFromContainerOwner(certificateCache, generateAlias, i2)) {
        arrayList.add(addUserIdToAlias(generateAlias, 0));
      }
    } else if (i != 0) {
      arrayList.add(addUserIdToAlias(generateAlias, 0));
    }
    return arrayList;
  }

  public final IPersonaManagerAdapter getPersonaManagerAdapter() {
    return (IPersonaManagerAdapter) AdapterRegistry.getAdapter(IPersonaManagerAdapter.class);
  }

  public boolean containsCertificateOrChain(
      Context context,
      CertificateCache certificateCache,
      X509Certificate x509Certificate,
      int i,
      int i2) {
    X509Certificate findIssuerInAndroidKeystore;
    if (x509Certificate == null) {
      return false;
    }
    String str = this.mPath;
    String str2 = TRUSTED_KEYSTORE_PATH;
    if (!str.equals(str2) && !this.mPath.equals(UNTRUSTED_KEYSTORE_PATH)) {
      return false;
    }
    if (findCertificateOrIssuer(certificateCache, x509Certificate, i, i2) != null) {
      return true;
    }
    if (!this.mPath.equals(str2)
        || isSelfSigned(x509Certificate)
        || (findIssuerInAndroidKeystore =
                CertificatePolicy.findIssuerInAndroidKeystore(context, x509Certificate, i))
            == null) {
      return false;
    }
    return containsCertificateOrChain(
        context, certificateCache, findIssuerInAndroidKeystore, i, i2);
  }

  public final X509Certificate findCertificateOrIssuer(
      CertificateCache certificateCache, X509Certificate x509Certificate, int i, int i2) {
    try {
      Iterator it =
          generateAllAliasesForUser(
                  certificateCache, x509Certificate.getSubjectX500Principal(), i, i2)
              .iterator();
      while (it.hasNext()) {
        X509Certificate x509Certificate2 =
            (X509Certificate) this.mKeyStore.getCertificate((String) it.next());
        if (x509Certificate2 != null && areEqual(x509Certificate2, x509Certificate)) {
          return x509Certificate2;
        }
      }
      Iterator it2 =
          generateAllAliasesForUser(
                  certificateCache, x509Certificate.getIssuerX500Principal(), i, i2)
              .iterator();
      while (it2.hasNext()) {
        X509Certificate x509Certificate3 =
            (X509Certificate) this.mKeyStore.getCertificate((String) it2.next());
        if (x509Certificate3 != null && matchPublicKey(x509Certificate3, x509Certificate)) {
          return x509Certificate3;
        }
      }
      Iterator it3 = getAliasesForUser(certificateCache, i, i2).iterator();
      while (it3.hasNext()) {
        X509Certificate x509Certificate4 =
            (X509Certificate) this.mKeyStore.getCertificate((String) it3.next());
        if (x509Certificate4 != null
            && (matchPublicKey(x509Certificate4, x509Certificate)
                || areEqual(x509Certificate4, x509Certificate))) {
          return x509Certificate4;
        }
      }
      return null;
    } catch (KeyStoreException e) {
      Log.e("EdmKeyStore", "get error" + e);
      return null;
    }
  }

  public final boolean matchPublicKey(
      X509Certificate x509Certificate, X509Certificate x509Certificate2) {
    if (x509Certificate == null || x509Certificate2 == null) {
      return false;
    }
    try {
      x509Certificate2.verify(x509Certificate.getPublicKey());
      return true;
    } catch (Exception unused) {
      return false;
    }
  }

  public static boolean isSelfSigned(X509Certificate x509Certificate) {
    if (!x509Certificate
        .getSubjectX500Principal()
        .equals(x509Certificate.getIssuerX500Principal())) {
      return false;
    }
    try {
      x509Certificate.verify(x509Certificate.getPublicKey());
      return true;
    } catch (InvalidKeyException
        | NoSuchAlgorithmException
        | NoSuchProviderException
        | SignatureException
        | CertificateException e) {
      Log.i("EdmKeyStore", "Verifying self-signed certificate: " + e.getMessage());
      return false;
    }
  }

  public static boolean isCa(X509Certificate x509Certificate) {
    try {
      byte[] extensionValue = x509Certificate.getExtensionValue("2.5.29.19");
      if (extensionValue == null) {
        return false;
      }
      return BasicConstraints.getInstance(
              new ASN1InputStream(new ASN1InputStream(extensionValue).readObject().getOctets())
                  .readObject())
          .isCA();
    } catch (IOException unused) {
      return false;
    }
  }

  public static boolean areEqual(
      X509Certificate x509Certificate, X509Certificate x509Certificate2) {
    if (x509Certificate == null || x509Certificate2 == null) {
      return false;
    }
    try {
      return Arrays.equals(x509Certificate.getEncoded(), x509Certificate2.getEncoded());
    } catch (CertificateEncodingException unused) {
      return false;
    }
  }

  public final List getAliasesForUser(CertificateCache certificateCache, int i, int i2) {
    ArrayList arrayList = new ArrayList();
    boolean isValidKnoxId = getPersonaManagerAdapter().isValidKnoxId(i);
    try {
      Enumeration<String> aliases = this.mKeyStore.aliases();
      while (aliases.hasMoreElements()) {
        String nextElement = aliases.nextElement();
        if (!nextElement.startsWith(String.valueOf(0) + "_")) {
          if (nextElement.startsWith(String.valueOf(i) + "_") && i != 0) {
            arrayList.add(nextElement);
          }
        } else if (isValidKnoxId) {
          if (isFromContainerOwner(certificateCache, nextElement, i2)) {
            arrayList.add(nextElement);
          }
        } else {
          arrayList.add(nextElement);
        }
      }
    } catch (KeyStoreException e) {
      Log.d("EdmKeyStore", "Exception with keystore " + e);
    }
    return arrayList;
  }

  public final boolean isFromContainerOwner(CertificateCache certificateCache, String str, int i) {
    return certificateCache.isInAdminList(0, str, i);
  }

  public final String removeUserIdFromAlias(String str) {
    return str.indexOf("_") == -1 ? str : str.substring(str.indexOf("_") + 1);
  }

  public final String addUserIdToAlias(String str, int i) {
    return String.valueOf(i) + "_" + str;
  }

  /* JADX WARN: Code restructure failed: missing block: B:8:0x000f, code lost:

     if (r1 != 3) goto L25;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public void performKeystoreUpgrade() {
    List<String> aliases = getAliases();
    int i = this.mType;
    if (i != 0 && i != 1) {
      if (i == 2) {
        for (String str : aliases) {
          if (str.length() >= 1 && Character.isLetter(str.charAt(0))) {
            changeAlias(str, "1010_" + str);
          }
        }
      }
      saveKeyStore();
    }
    for (String str2 : aliases) {
      if (!str2.contains("_")) {
        changeAlias(str2, "0_" + str2);
      }
    }
    saveKeyStore();
  }

  public final void changeAlias(String str, String str2) {
    try {
      X509Certificate x509Certificate = (X509Certificate) this.mKeyStore.getCertificate(str);
      this.mKeyStore.deleteEntry(str);
      this.mKeyStore.setCertificateEntry(str2, x509Certificate);
    } catch (KeyStoreException e) {
      Log.d("EdmKeyStore", "Exception with keystore " + e);
    }
  }

  public List getAliases() {
    ArrayList arrayList = new ArrayList();
    try {
      Enumeration<String> aliases = this.mKeyStore.aliases();
      while (aliases.hasMoreElements()) {
        arrayList.add(aliases.nextElement());
      }
    } catch (KeyStoreException e) {
      Log.d("EdmKeyStore", "Exception with keystore " + e);
    }
    return arrayList;
  }

  public List cleanUid(int i) {
    ArrayList arrayList = new ArrayList();
    for (String str : getAliases()) {
      try {
        synchronized (this.mKeyStoreLock) {
          if (str.startsWith(i + "_")) {
            this.mKeyStore.deleteEntry(str);
          }
        }
      } catch (KeyStoreException e) {
        Log.d("EdmKeyStore", "Exception with keystore " + e);
      }
    }
    saveKeyStore();
    return arrayList;
  }

  public void dump(PrintWriter printWriter, String str) {
    StringBuilder sb = new StringBuilder();
    sb.append(str);
    sb.append("Certificate aliases {");
    try {
      Enumeration<String> aliases = this.mKeyStore.aliases();
      while (aliases.hasMoreElements()) {
        sb.append(aliases.nextElement());
        if (aliases.hasMoreElements()) {
          sb.append(", ");
        }
      }
    } catch (KeyStoreException e) {
      sb.append("Could not dump alias from keystore ");
      sb.append(e.getMessage());
    }
    sb.append("}");
    sb.append(System.lineSeparator());
    sb.append(System.lineSeparator());
    printWriter.write(sb.toString());
  }
}
