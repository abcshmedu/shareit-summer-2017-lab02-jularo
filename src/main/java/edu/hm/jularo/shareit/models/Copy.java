package edu.hm.jularo.shareit.models;

public class Copy {
    private Medium medium;
    private String owner;

    public Copy(String owner, Medium medium) {

        if (owner == null || medium == null) {
            throw new IllegalArgumentException("owner and medium must not be null");
        }

        this.owner = owner;
        this.medium = medium;
    }

    public Medium getMedium() {
        return medium;
    }

    public String getOwner() {
        return owner;
    }
}
