package android.nfc;

/* loaded from: classes3.dex */
public final class NfcEvent {
  public final NfcAdapter nfcAdapter;
  public final int peerLlcpMajorVersion;
  public final int peerLlcpMinorVersion;

  NfcEvent(NfcAdapter nfcAdapter, byte peerLlcpVersion) {
    this.nfcAdapter = nfcAdapter;
    this.peerLlcpMajorVersion = (peerLlcpVersion & 240) >> 4;
    this.peerLlcpMinorVersion = peerLlcpVersion & 15;
  }
}
