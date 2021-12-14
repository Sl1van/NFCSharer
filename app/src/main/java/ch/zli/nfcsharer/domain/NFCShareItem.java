package ch.zli.nfcsharer.domain;

import java.io.Serializable;
import java.util.Objects;

public class NFCShareItem implements Serializable {
    private final NFCShareItemType type;

    private String name;
    private String URI;
    private boolean enabled;

    public NFCShareItem(String name, String URI,boolean enabled) {
        this.type = NFCShareItemType.URI;
        this.URI = URI;
        this.name = name;
        this.enabled = enabled;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        if(type == NFCShareItemType.URI){
            return "URI: " + getURI();
        } else{
            throw new UnsupportedOperationException("Not yet implemented");
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NFCShareItem that = (NFCShareItem) o;
        return enabled == that.enabled && type == that.type && name.equals(that.name) && Objects.equals(URI, that.URI);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, name, URI, enabled);
    }
}
