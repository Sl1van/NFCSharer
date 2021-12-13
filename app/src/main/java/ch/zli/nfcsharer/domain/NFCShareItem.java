package ch.zli.nfcsharer.domain;

public class NFCShareItem {
    private final NFCShareItemType type;

    private String URI;

    public NFCShareItem(NFCShareItemType type, String URI) {
        if(type == NFCShareItemType.WLAN){
            throw new UnsupportedOperationException("Wlan is not yet implemented");
        }
        this.type = type;
        this.URI = URI;
    }

    public NFCShareItemType getType() {
        return type;
    }

    public String getURI() {
        return URI;
    }

    public void setURI(String URI) {
        this.URI = URI;
    }
}
